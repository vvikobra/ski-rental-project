package vvikobra.miit.skirental.repositories;

import java.util.UUID;

public interface SkipassRepository {
    void decrementLift(UUID skipassId);
    boolean isActive(UUID skipassId);
}
