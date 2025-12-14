package vvikobra.miit.skirental.services.impl;

import com.example.skirentalcontracts.api.dto.booking.BookingRequest;
import com.example.skirentalcontracts.api.dto.booking.BookingResponse;
import com.example.skirentalcontracts.api.exception.*;
import jakarta.transaction.Transactional;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vvikobra.events.BookingCreatedEvent;
import vvikobra.events.PaymentCalculatedEvent;
import vvikobra.miit.skirental.config.RabbitMQConfig;
import vvikobra.miit.skirental.models.entities.*;
import vvikobra.miit.skirental.models.enums.BookingStatus;
import vvikobra.miit.skirental.models.enums.PaymentStatus;
import vvikobra.miit.skirental.models.enums.SkipassType;
import vvikobra.miit.skirental.repositories.Impl.*;
import vvikobra.miit.skirental.services.BookingService;
import vvikobra.miit.skirental.util.Mapper;
import vvikobra.miit.skirental.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class BookingServiceImpl implements BookingService {

    private final RabbitTemplate rabbitTemplate;
    private UserRepositoryImpl userRepository;
    private EquipmentRepositoryImpl equipmentRepository;
    private BookingRepositoryImpl bookingRepository;
    private BookingStatusRepositoryImpl bookingStatusRepository;
    private PaymentStatusRepositoryImpl paymentStatusRepository;
    private PaymentRepositoryImpl paymentRepository;
    private SkipassTypeRepositoryImpl skipassTypeRepository;
    private SkipassRepositoryImpl skipassRepository;
    private Mapper mapper;

    @GrpcClient("payment-service")
    private PaymentServiceGrpc.PaymentServiceBlockingStub paymentStub;

    @Autowired
    public BookingServiceImpl(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Autowired
    public void setUserRepository(UserRepositoryImpl userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setEquipmentRepository(EquipmentRepositoryImpl equipmentRepository) {
        this.equipmentRepository = equipmentRepository;
    }

    @Autowired
    public void setBookingRepository(BookingRepositoryImpl bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    @Autowired
    public void setPaymentRepository(PaymentRepositoryImpl paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Autowired
    public void setBookingStatusRepository(BookingStatusRepositoryImpl bookingStatusRepository) {
        this.bookingStatusRepository = bookingStatusRepository;
    }

    @Autowired
    public void setPaymentStatusRepository(PaymentStatusRepositoryImpl paymentStatusRepository) {
        this.paymentStatusRepository = paymentStatusRepository;
    }

    @Autowired
    public void setSkipassTypeRepository(SkipassTypeRepositoryImpl skipassTypeRepository) {
        this.skipassTypeRepository = skipassTypeRepository;
    }

    @Autowired
    public void setSkipassRepository(SkipassRepositoryImpl skipassRepository) {
        this.skipassRepository = skipassRepository;
    }

    @Autowired
    public void setMapper(Mapper mapper) {
        this.mapper = mapper;
    }

    @Override
    @Transactional
    public BookingResponse createBooking(BookingRequest request) {

        validateDateRange(request);

        User user = findUser(request.userId());
        List<Equipment> equipment = findEquipment(request.equipmentItems());
        BookingStatus createdStatus = findBookingStatus("CREATED");
        PaymentStatus pendingStatus = findPaymentStatus("PENDING");
        SkipassType skipassType = findSkipassType(request.skipass().type());

        Booking booking = buildBooking(request, user, equipment, createdStatus);

        long hours = Duration.between(request.timeFrom(), request.timeTo()).toHours();

        BookingPriceRequest.Builder grpcReq = BookingPriceRequest.newBuilder()
                .setUserId(user.getId().toString())
                .setRentalHours(hours)
                .setSkipassType(skipassType.getName())
                .setUserPreviousBookings(bookingRepository.countByUserId(user.getId()));

        Integer liftsCount = request.skipass().liftsCount();

        switch (skipassType.getName()) {

            case "LIFTS" -> {
                if (liftsCount == null) {
                    throw new InvalidSkipassException(
                            "Для skipass типа LIFTS необходимо указать количество подъемов."
                    );
                }
                grpcReq.setLiftsCount(liftsCount);
            }

            case "HOURS" -> {
                if (liftsCount != null) {
                    throw new InvalidSkipassException(
                            "Для skipass типа HOURS количество подъемов передавать нельзя."
                    );
                }
            }

            default -> throw new InvalidSkipassException(
                    "Неизвестный тип skipass: " + skipassType.getName()
            );
        }

        for (Equipment eq : equipment) {
            grpcReq.addEquipment(
                    EquipmentItem.newBuilder()
                            .setId(eq.getId().toString())
                            .setHourlyRate(eq.getHourlyRate())
                            .build()
            );
        }

        BookingPriceResponse grpcResponse = paymentStub.calculateTotalPrice(grpcReq.build());
        double totalAmount = grpcResponse.getFinalPrice();

        PaymentCalculatedEvent paymentEvent = new PaymentCalculatedEvent(
                totalAmount
        );
        rabbitTemplate.convertAndSend(RabbitMQConfig.PAYMENT_FANOUT, "", paymentEvent);

        Payment payment = buildPayment(booking, pendingStatus, totalAmount);
        Skipass skipass = buildSkipass(request, booking, skipassType);

        persistBookingData(booking, payment, skipass);

        List<String> equipmentTypes = equipment.stream()
                .map(e -> e.getType().getName())
                .toList();

        BookingCreatedEvent event = new BookingCreatedEvent(
                booking.getId().toString(),
                equipmentTypes,
                booking.getStartDate(),
                booking.getEndDate(),
                payment.getAmount()
        );

        rabbitTemplate.convertAndSend(
                RabbitMQConfig.EXCHANGE_NAME,
                RabbitMQConfig.ROUTING_KEY_BOOKING_CREATED,
                event
        );

        return mapper.mapToBookingResponse(booking);
    }

    @Override
    public BookingResponse getBooking(UUID id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Booking", id));
        return mapper.mapToBookingResponse(booking);
    }

    @Override
    @Transactional
    public BookingResponse cancelBooking(UUID id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Booking", id));

        if ("PAID".equals(booking.getStatus().getName())) {
            throw new BookingCancellationException("Нельзя отменить уже оплаченное бронирование");
        }

        Skipass skipass = booking.getSkipass();
        skipass.setActive(false);
        skipassRepository.update(skipass);

        bookingRepository.cancelBooking(id, LocalDateTime.now());
        return mapper.mapToBookingResponse(booking);
    }

    private void validateDateRange(BookingRequest request) {
        if (request.timeFrom().isAfter(request.timeTo())) {
            throw new InvalidDateRangeException(request.timeFrom(), request.timeTo());
        }
    }

    private User findUser(UUID userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", userId));
    }

    private List<Equipment> findEquipment(List<UUID> ids) {
        return ids.stream()
                .map(id -> equipmentRepository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("Equipment", id)))
                .toList();
    }

    private BookingStatus findBookingStatus(String name) {
        return bookingStatusRepository.findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("Booking status", name));
    }

    private PaymentStatus findPaymentStatus(String name) {
        return paymentStatusRepository.findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("Payment status", name));
    }

    private SkipassType findSkipassType(String name) {
        return skipassTypeRepository.findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("Skipass type", name));
    }

    private Booking buildBooking(BookingRequest request, User user,
                                 List<Equipment> equipment, BookingStatus status) {
        Booking booking = new Booking(user, status, request.timeFrom(), request.timeTo());
        booking.getEquipmentItems().addAll(equipment);
        return booking;
    }

    private Payment buildPayment(Booking booking, PaymentStatus status, double totalAmount) {
        Payment payment = new Payment(booking, status, totalAmount, null);
        payment.setBooking(booking);
        booking.setPayment(payment);
        return payment;
    }

    private Skipass buildSkipass(BookingRequest req, Booking booking, SkipassType type) {
        Skipass skipass = new Skipass(
                booking,
                type,
                booking.getStartDate(),
                booking.getEndDate(),
                req.skipass().liftsCount()
        );
        skipass.setBooking(booking);
        booking.setSkipass(skipass);
        return skipass;
    }

    private void persistBookingData(Booking booking, Payment payment, Skipass skipass) {
        bookingRepository.create(booking);
        skipassRepository.create(skipass);
        paymentRepository.create(payment);
    }
}