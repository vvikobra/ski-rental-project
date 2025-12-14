package vvikobra.miit.skirental.util;

import com.example.skirentalcontracts.api.dto.booking.BookingResponse;
import com.example.skirentalcontracts.api.dto.equipment.EquipmentResponse;
import com.example.skirentalcontracts.api.dto.payment.PaymentResponse;
import com.example.skirentalcontracts.api.dto.skipass.SkipassResponse;
import org.springframework.stereotype.Component;
import vvikobra.miit.skirental.models.entities.Booking;
import vvikobra.miit.skirental.models.entities.Equipment;
import vvikobra.miit.skirental.models.entities.Payment;
import vvikobra.miit.skirental.models.entities.Skipass;

@Component
public class Mapper {
    public BookingResponse mapToBookingResponse(Booking booking) {
        return new BookingResponse(
                booking.getId(),
                booking.getUser().getId(),
                booking.getEquipmentItems().stream()
                        .map(this::mapToEquipmentResponse)
                        .toList(),
                booking.getStartDate(),
                booking.getEndDate(),
                booking.getSkipass() != null ? mapToSkipassResponse(booking.getSkipass()) : null,
                booking.getStatus().getName(),
                booking.getPayment() != null ? booking.getPayment().getStatus().getName() : null,
                booking.getPayment() != null ? booking.getPayment().getAmount() : 0.0,
                booking.getCreatedAt(),
                booking.getCancelledAt()
        );
    }

    public EquipmentResponse mapToEquipmentResponse(Equipment equipment) {
        return new EquipmentResponse(
                equipment.getId(),
                equipment.getType().getName(),
                equipment.getModel(),
                equipment.getShoeSize(),
                equipment.getLevel().getName(),
                equipment.getHourlyRate()
        );
    }

    public SkipassResponse mapToSkipassResponse(Skipass skipass) {
        return new SkipassResponse(
                skipass.getId(),
                skipass.getType().getName(),
                skipass.getValidFrom(),
                skipass.getValidUntil(),
                skipass.getTotalLifts(),
                skipass.getRemainingLifts(),
                skipass.isActive()
        );
    }

    public PaymentResponse mapToPaymentResponse(Payment payment) {
        return new PaymentResponse(
                payment.getBooking().getId(),
                payment.getStatus().getName(),
                payment.getBooking().getSkipass() != null ? mapToSkipassResponse(payment.getBooking().getSkipass()) : null,
                payment.getPaidAt()
        );
    }

}
