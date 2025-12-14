package vvikobra.miit.skirental.repositories.Impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;
import vvikobra.miit.skirental.models.entities.Skipass;
import vvikobra.miit.skirental.repositories.BaseRepository;
import vvikobra.miit.skirental.repositories.SkipassRepository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class SkipassRepositoryImpl extends BaseRepository<Skipass, UUID> implements SkipassRepository {
    protected SkipassRepositoryImpl() {
        super(Skipass.class);
    }

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public void decrementLift(UUID skipassId) {
        entityManager.createQuery(
                        "UPDATE Skipass s " +
                                "SET s.remainingLifts = s.remainingLifts - 1 " +
                                "WHERE s.id = :id AND s.remainingLifts > 0")
                .setParameter("id", skipassId)
                .executeUpdate();
    }

    @Override
    public boolean isActive(UUID skipassId) {
        Long count = entityManager.createQuery(
                        "SELECT COUNT(s) FROM Skipass s " +
                                "WHERE s.id = :id " +
                                "  AND ( (s.type.name = :timeName AND CURRENT_TIMESTAMP BETWEEN s.validFrom AND s.validUntil) " +
                                "     OR (s.type.name = :liftsName AND s.remainingLifts > 0) )", Long.class)
                .setParameter("id", skipassId)
                .setParameter("timeName", "TIME")
                .setParameter("liftsName", "LIFTS")
                .getSingleResult();

        return count != null && count > 0;
    }
}