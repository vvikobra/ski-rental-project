package com.example.skirentalcontracts.api.exception;

import java.time.LocalDateTime;

public class InvalidDateRangeException extends RuntimeException {
    public InvalidDateRangeException(LocalDateTime timeFrom, LocalDateTime timeTo) {
        super(String.format("Дата окончания (%s) не может быть раньше даты начала (%s)", timeTo, timeFrom));
    }
}