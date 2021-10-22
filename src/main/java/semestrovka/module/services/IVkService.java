package semestrovka.module.services;

import semestrovka.module.entities.UserModel;

public interface IVkService {
    UserModel getUser(String code);
}
