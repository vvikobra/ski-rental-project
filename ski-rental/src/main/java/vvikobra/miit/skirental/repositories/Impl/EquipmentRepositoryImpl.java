package vvikobra.miit.skirental.repositories.Impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import vvikobra.miit.skirental.models.entities.Equipment;
import vvikobra.miit.skirental.repositories.BaseRepository;
import vvikobra.miit.skirental.repositories.EquipmentRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public class EquipmentRepositoryImpl extends BaseRepository<Equipment, UUID> implements EquipmentRepository {
    protected EquipmentRepositoryImpl() {
        super(Equipment.class);
    }

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Equipment> findAvailable(LocalDateTime from, LocalDateTime to) {
        return entityManager.createQuery("""
                        SELECT e FROM Equipment e
                        WHERE NOT EXISTS (
                            SELECT 1
                            FROM Booking b
                            JOIN b.equipmentItems eq
                            JOIN b.status s
                            WHERE eq = e
                              AND s.name IN ('CREATED', 'PENDING_PAYMENT', 'PAID')
                              AND (b.startDate < :to AND b.endDate > :from)
                        )
                        """, Equipment.class)
                .setParameter("from", from)
                .setParameter("to", to)
                .getResultList();
    }
}
