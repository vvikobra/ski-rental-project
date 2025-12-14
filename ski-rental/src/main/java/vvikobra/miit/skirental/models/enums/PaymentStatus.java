package vvikobra.miit.skirental.models.enums;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import vvikobra.miit.skirental.models.entities.BaseEntity;
import vvikobra.miit.skirental.models.entities.Payment;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "payment_statuses")
public class PaymentStatus extends BaseEntity {

    private String name;
    private Set<Payment> payments;

    public PaymentStatus(String name) {
        this.name = name;
        this.payments = new HashSet<>();
    }

    protected PaymentStatus() {
    }

    @Column(nullable = false, unique = true)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @OneToMany(mappedBy = "status", targetEntity = Payment.class)
    public Set<Payment> getPayments() {
        return payments;
    }

    public void setPayments(Set<Payment> payments) {
        this.payments = payments;
    }
}
