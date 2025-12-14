package vvikobra.miit.skirental.repositories.Impl;

import org.springframework.stereotype.Repository;
import vvikobra.miit.skirental.models.enums.EquipmentType;
import vvikobra.miit.skirental.repositories.BaseRepository;

import java.util.UUID;

@Repository
public class EquipmentTypeRepositoryImpl extends BaseRepository<EquipmentType, UUID> {
    protected EquipmentTypeRepositoryImpl() {
        super(EquipmentType.class);
    }
}
