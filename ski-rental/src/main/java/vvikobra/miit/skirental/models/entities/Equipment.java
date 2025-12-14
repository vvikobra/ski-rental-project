package vvikobra.miit.skirental.models.entities;

import jakarta.persistence.*;
import vvikobra.miit.skirental.models.enums.EquipmentType;
import vvikobra.miit.skirental.models.enums.SkillLevel;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "equipments")
public class Equipment extends BaseEntity {
    private EquipmentType type;
    private String model;
    private Double minHeight;
    private Double maxHeight;
    private Double minWeight;
    private Double maxWeight;
    private Integer shoeSize;
    private SkillLevel level;
    private Double hourlyRate;
    private Set<Booking> bookings;

    public Equipment(EquipmentType type, String model, Double minHeight, Double maxHeight, Double minWeight, Double maxWeight, Integer shoeSize, SkillLevel skillLevel, Double hourlyRate) {
        this.type = type;
        this.model = model;
        this.minHeight = minHeight;
        this.maxHeight = maxHeight;
        this.minWeight = minWeight;
        this.maxWeight = maxWeight;
        this.shoeSize = shoeSize;
        this.level = skillLevel;
        this.hourlyRate = hourlyRate;
        this.bookings = new HashSet<>();
    }

    protected Equipment() {
    }

    @ManyToOne(optional = false)
    @JoinColumn(name = "type_id", referencedColumnName = "id")
    public EquipmentType getType() {
        return type;
    }

    public void setType(EquipmentType type) {
        this.type = type;
    }

    @Column(nullable = false)
    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    @Column
    public Double getMinHeight() {
        return minHeight;
    }

    public void setMinHeight(Double minHeight) {
        this.minHeight = minHeight;
    }

    @Column
    public Double getMaxHeight() {
        return maxHeight;
    }

    public void setMaxHeight(Double maxHeight) {
        this.maxHeight = maxHeight;
    }

    @Column
    public Double getMinWeight() {
        return minWeight;
    }

    public void setMinWeight(Double minWeight) {
        this.minWeight = minWeight;
    }

    @Column
    public Double getMaxWeight() {
        return maxWeight;
    }

    public void setMaxWeight(Double maxWeight) {
        this.maxWeight = maxWeight;
    }

    @Column
    public Integer getShoeSize() {
        return shoeSize;
    }

    public void setShoeSize(Integer shoeSize) {
        this.shoeSize = shoeSize;
    }

    @ManyToOne(optional = false)
    @JoinColumn(name = "skill_id", referencedColumnName = "id")
    public SkillLevel getLevel() {
        return level;
    }

    public void setLevel(SkillLevel level) {
        this.level = level;
    }

    @Column(nullable = false)
    public Double getHourlyRate() {
        return hourlyRate;
    }

    public void setHourlyRate(Double hourlyRate) {
        this.hourlyRate = hourlyRate;
    }

    @ManyToMany(mappedBy = "equipmentItems")
    public Set<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(Set<Booking> bookings) {
        this.bookings = bookings;
    }
}
