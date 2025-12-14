package vvikobra.payment;

import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class PaymentServiceImpl extends PaymentServiceGrpc.PaymentServiceImplBase {

    @Override
    public void calculateTotalPrice(
            BookingPriceRequest request,
            StreamObserver<BookingPriceResponse> responseObserver
    ) {

        long hours = request.getRentalHours();

        double equipmentAmount = request.getEquipmentList().stream()
                .mapToDouble(eq -> eq.getHourlyRate() * hours)
                .sum();

        double skipassAmount = calculateSkipassCost(
                request.getSkipassType(),
                hours,
                request.getLiftsCount()
        );

        long bookingCount = request.getUserPreviousBookings();
        boolean isDiscounted = (bookingCount + 1) % 5 == 0;
        double discount = isDiscounted ? 0.10 : 0.0;

        double total = (equipmentAmount + skipassAmount) * (1 - discount);

        BookingPriceResponse response = BookingPriceResponse.newBuilder()
                .setFinalPrice(total)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    private double calculateSkipassCost(String type, long hours, int liftsCount) {
        return switch (type) {
            case "HOURS" -> hours * 10.0;
            case "LIFTS" -> liftsCount * 2.5;
            default -> 0.0;
        };
    }
}
