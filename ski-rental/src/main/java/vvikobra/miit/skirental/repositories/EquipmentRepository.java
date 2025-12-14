package vvikobra.miit.skirental.repositories;

import vvikobra.miit.skirental.models.entities.Equipment;

import java.time.LocalDateTime;
import java.util.List;

public interface EquipmentRepository {
    List<Equipment> findAvailable(LocalDateTime from, LocalDateTime to);
}
