package vvikobra.miit.skirental.services.impl;

import com.example.skirentalcontracts.api.dto.payment.PaymentResponse;
import com.example.skirentalcontracts.api.exception.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vvikobra.events.BookingPaidEvent;
import vvikobra.events.SkipassActivatedEvent;
import vvikobra.miit.skirental.config.RabbitMQConfig;
import vvikobra.miit.skirental.models.entities.*;
import vvikobra.miit.skirental.models.enums.PaymentStatus;
import vvikobra.miit.skirental.repositories.Impl.*;
import vvikobra.miit.skirental.services.PaymentService;
import vvikobra.miit.skirental.util.Mapper;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class PaymentServiceImpl implements PaymentService {

    private PaymentRepositoryImpl paymentRepository;
    private BookingRepositoryImpl bookingRepository;
    private PaymentStatusRepositoryImpl paymentStatusRepository;
    private SkipassRepositoryImpl skipassRepository;
    private Mapper mapper;
    private final RabbitTemplate rabbitTemplate;

    public PaymentServiceImpl(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Autowired
    public void setPaymentRepository(PaymentRepositoryImpl paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Autowired
    public void setBookingRepository(BookingRepositoryImpl bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    @Autowired
    public void setPaymentStatusRepository(PaymentStatusRepositoryImpl paymentStatusRepository) {
        this.paymentStatusRepository = paymentStatusRepository;
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
    public PaymentResponse getPayment(UUID bookingId) {
        Payment payment = paymentRepository.findByBookingId(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Payment for booking", bookingId));
        return mapper.mapToPaymentResponse(payment);
    }

    @Override
    public PaymentResponse payBooking(UUID bookingId) {
        Booking booking = findBookingOrThrow(bookingId);
        Payment payment = getPaymentOrThrow(booking);

        validatePaymentEligibility(booking, payment);

        processPayment(payment);
        activateSkipass(booking.getSkipass());
        updateBooking(booking);

        Skipass skipass = booking.getSkipass();
        SkipassActivatedEvent skipassActivatedEvent = new SkipassActivatedEvent(skipass.getId().toString(), skipass.getType().getName(), skipass.getTotalLifts(), booking.getStartDate(), booking.getEndDate());

        BookingPaidEvent bookingPaidEvent = new BookingPaidEvent(booking.getId().toString());

        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, RabbitMQConfig.ROUTING_KEY_BOOKING_PAID, bookingPaidEvent);
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, RabbitMQConfig.ROUTING_KEY_SKIPASS_ACTIVATED, skipassActivatedEvent);

        return mapper.mapToPaymentResponse(payment);
    }

    private Booking findBookingOrThrow(UUID bookingId) {
        return bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Booking", bookingId));
    }

    private Payment getPaymentOrThrow(Booking booking) {
        Payment payment = booking.getPayment();
        if (payment == null) {
            throw new ResourceNotFoundException("Payment", booking.getId());
        }
        return payment;
    }

    private void validatePaymentEligibility(Booking booking, Payment payment) {
        if (booking.getCreatedAt().plusMinutes(15).isBefore(LocalDateTime.now())) {
            bookingRepository.cancelBooking(booking.getId(), LocalDateTime.now());
            throw new PaymentTimeoutException(booking.getId());
        }

        if ("PAID".equalsIgnoreCase(payment.getStatus().getName())) {
            throw new PaymentAlreadyProcessedException(booking.getId());
        }
    }

    private void processPayment(Payment payment) {
        PaymentStatus paidStatus = paymentStatusRepository.findByName("PAID")
                .orElseThrow(() -> new ResourceNotFoundException("Payment status", "PAID"));
        payment.setStatus(paidStatus);
        payment.setPaidAt(LocalDateTime.now());
        paymentRepository.update(payment);
    }

    private void activateSkipass(Skipass skipass) {
        if (skipass == null) return;
        skipass.setActive(true);
        skipassRepository.update(skipass);
    }

    private void updateBooking(Booking booking) {
        bookingRepository.update(booking);
    }
}
