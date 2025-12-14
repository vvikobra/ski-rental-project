package vvikobra.miit.skirental.models.enums;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import vvikobra.miit.skirental.models.entities.BaseEntity;
import vvikobra.miit.skirental.models.entities.Equipment;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "equipment_types")
public class EquipmentType extends BaseEntity {
    private String name;
    private Set<Equipment> equipments;

    public EquipmentType(String name) {
        this.name = name;
        this.equipments = new HashSet<>();
    }

    protected EquipmentType() {
    }

    @Column(nullable = false, unique = true)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @OneToMany(mappedBy = "type", targetEntity = Equipment.class)
    public Set<Equipment> getEquipments() {
        return equipments;
    }

    public void setEquipments(Set<Equipment> equipments) {
        this.equipments = equipments;
    }
}
