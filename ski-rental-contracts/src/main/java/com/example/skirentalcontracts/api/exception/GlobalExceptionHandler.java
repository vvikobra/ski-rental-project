package com.example.skirentalcontracts.api.exception;

import com.example.skirentalcontracts.api.dto.StatusResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<StatusResponse> handleResourceNotFound(ResourceNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new StatusResponse("error", ex.getMessage()));
    }

    @ExceptionHandler(EquipmentAlreadyBookedException.class)
    public ResponseEntity<StatusResponse> handleEquipmentAlreadyBooked(EquipmentAlreadyBookedException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new StatusResponse("error", ex.getMessage()));
    }

    @ExceptionHandler(InvalidSkipassException.class)
    public ResponseEntity<StatusResponse> handleInvalidSkipassException(InvalidSkipassException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new StatusResponse("error", ex.getMessage()));
    }

    @ExceptionHandler(InvalidDateRangeException.class)
    public ResponseEntity<StatusResponse> handleInvalidDateRange(InvalidDateRangeException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new StatusResponse("error", ex.getMessage()));
    }

    @ExceptionHandler(BookingCancellationException.class)
    public ResponseEntity<StatusResponse> handleInvalidDateRange(BookingCancellationException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new StatusResponse("error", ex.getMessage()));
    }

    @ExceptionHandler(PaymentTimeoutException.class)
    public ResponseEntity<String> handlePaymentTimeout(PaymentTimeoutException ex) {
        return ResponseEntity.status(HttpStatus.REQUEST_TIMEOUT).body(ex.getMessage());
    }

    @ExceptionHandler(PaymentAlreadyProcessedException.class)
    public ResponseEntity<String> handlePaymentAlreadyProcessed(PaymentAlreadyProcessedException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }
}
