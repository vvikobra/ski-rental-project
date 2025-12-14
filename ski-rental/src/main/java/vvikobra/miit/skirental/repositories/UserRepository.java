package vvikobra.miit.skirental.repositories;

import vvikobra.miit.skirental.models.entities.User;

import java.util.Optional;

public interface UserRepository {
    Optional<User> findByEmail(String email);
}
