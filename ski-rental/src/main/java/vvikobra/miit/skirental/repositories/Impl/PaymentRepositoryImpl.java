package vvikobra.miit.skirental.repositories.Impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import vvikobra.miit.skirental.models.entities.Payment;
import vvikobra.miit.skirental.repositories.BaseRepository;
import vvikobra.miit.skirental.repositories.PaymentRepository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class PaymentRepositoryImpl extends BaseRepository<Payment, UUID> implements PaymentRepository {
    protected PaymentRepositoryImpl() {
        super(Payment.class);
    }

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Payment> findByBookingId(UUID bookingId) {
        return entityManager.createQuery("""
                        SELECT p FROM Payment p WHERE p.booking.id = :bookingId
                        """, Payment.class)
                .setParameter("bookingId", bookingId)
                .getResultStream()
                .findFirst();
    }
}
