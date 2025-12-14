package vvikobra.miit.skirental.repositories.Impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;
import vvikobra.miit.skirental.models.entities.Booking;
import vvikobra.miit.skirental.repositories.BaseRepository;
import vvikobra.miit.skirental.repositories.BookingRepository;

import java.time.LocalDateTime;
import java.util.UUID;

@Repository
public class BookingRepositoryImpl extends BaseRepository<Booking, UUID> implements BookingRepository {
    protected BookingRepositoryImpl() {
        super(Booking.class);
    }

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public long countByUserId(UUID userId) {
        return entityManager.createQuery(
                        "SELECT COUNT(b) FROM Booking b WHERE b.user.id = :userId",
                        Long.class)
                .setParameter("userId", userId)
                .getSingleResult();
    }

    @Override
    @Transactional
    public void cancelBooking(UUID bookingId, LocalDateTime cancelledAt) {
        entityManager.createQuery(
                        "UPDATE Booking b " +
                                "SET b.status = (SELECT s FROM BookingStatus s WHERE s.name = :cancelledStatus), " +
                                "b.cancelledAt = :cancelledAt " +
                                "WHERE b.id = :id")
                .setParameter("cancelledStatus", "CANCELLED")
                .setParameter("cancelledAt", cancelledAt)
                .setParameter("id", bookingId)
                .executeUpdate();
    }
}
