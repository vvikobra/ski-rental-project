package vvikobra.miit.skirental.repositories.Impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import vvikobra.miit.skirental.models.entities.User;
import vvikobra.miit.skirental.repositories.BaseRepository;
import vvikobra.miit.skirental.repositories.UserRepository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class UserRepositoryImpl extends BaseRepository<User, UUID> implements UserRepository {
    protected UserRepositoryImpl() {
        super(User.class);
    }

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<User> findByEmail(String email) {
        return entityManager.createQuery("SELECT u FROM User u WHERE u.email = :email", User.class)
                .setParameter("email", email)
                .getResultStream()
                .findFirst();
    }
}
