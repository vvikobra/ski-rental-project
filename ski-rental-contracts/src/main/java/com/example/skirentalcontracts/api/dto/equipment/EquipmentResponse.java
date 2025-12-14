package com.example.skirentalcontracts.api.dto.equipment;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.util.UUID;

@Relation(collectionRelation = "equipments", itemRelation = "equipment")
public class EquipmentResponse extends RepresentationModel<EquipmentResponse> {

    private final UUID id;
    private final String type;
    private final String model;
    private final Integer size;
    private final String skillLevel;
    private final Double hourlyRate;

    public EquipmentResponse(UUID id,
                             String type,
                             String model,
                             Integer size,
                             String skillLevel,
                             Double hourlyRate) {
        this.id = id;
        this.type = type;
        this.model = model;
        this.size = size;
        this.skillLevel = skillLevel;
        this.hourlyRate = hourlyRate;
    }

    public UUID getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getModel() {
        return model;
    }

    public Integer getSize() {
        return size;
    }

    public String getSkillLevel() {
        return skillLevel;
    }

    public Double getHourlyRate() {
        return hourlyRate;
    }
}