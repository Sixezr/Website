package ru.sixzr.module.repositories;

import ru.sixzr.module.entities.UserModel;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    List<UserModel> findAll();
    Optional<UserModel> findById(Long id);
    Optional<UserModel> findByEmail(String email);
    void save(UserModel entity);
    void update(UserModel entity);
}

