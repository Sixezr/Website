package ru.sixzr.module.repositories;

import ru.sixzr.module.entities.UserModel;

import java.util.Optional;

public interface UserRepository extends Repository<UserModel> {

    Optional<UserModel> findByEmail(String email);

}
