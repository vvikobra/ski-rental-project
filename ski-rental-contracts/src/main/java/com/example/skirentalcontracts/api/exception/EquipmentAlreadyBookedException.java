package com.example.skirentalcontracts.api.exception;

import java.util.UUID;

public class EquipmentAlreadyBookedException extends RuntimeException {
    public EquipmentAlreadyBookedException(UUID equipmentId) {
        super("Экипировка " + equipmentId + " уже забронирована");
    }
}