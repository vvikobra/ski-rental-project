package vvikobra.miit.skirental.repositories.Impl;

import org.springframework.stereotype.Repository;
import vvikobra.miit.skirental.models.enums.PaymentStatus;
import vvikobra.miit.skirental.repositories.BaseRepository;

import java.util.UUID;

@Repository
public class PaymentStatusRepositoryImpl extends BaseRepository<PaymentStatus, UUID> {
    protected PaymentStatusRepositoryImpl() {
        super(PaymentStatus.class);
    }
}
