package com.example.skirentalcontracts.api.dto.skipass;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.time.LocalDateTime;
import java.util.UUID;

@Relation(collectionRelation = "skipasses", itemRelation = "skipass")
public class SkipassResponse extends RepresentationModel<SkipassResponse> {

    private final UUID id;
    private final String type;
    private final LocalDateTime validFrom;
    private final LocalDateTime validUntil;
    private final Integer totalLifts;
    private final Integer remainingLifts;
    private final Boolean isActive;

    public SkipassResponse(UUID id,
                           String type,
                           LocalDateTime validFrom,
                           LocalDateTime validUntil,
                           Integer totalLifts,
                           Integer remainingLifts, Boolean isActive) {
        this.id = id;
        this.type = type;
        this.validFrom = validFrom;
        this.validUntil = validUntil;
        this.totalLifts = totalLifts;
        this.remainingLifts = remainingLifts;
        this.isActive = isActive;
    }

    public UUID getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public LocalDateTime getValidFrom() {
        return validFrom;
    }

    public LocalDateTime getValidUntil() {
        return validUntil;
    }

    public Integer getTotalLifts() {
        return totalLifts;
    }

    public Integer getRemainingLifts() {
        return remainingLifts;
    }

    public Boolean getActive() {
        return isActive;
    }
}