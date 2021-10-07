package semestrovka.module.repositories;

import semestrovka.module.entities.UserModel;

import java.util.Optional;

public interface UserRepository extends Repository<UserModel> {

    Optional<UserModel> findByEmail(String email);
    Optional<UserModel> findByToken(String token);
}
