package semestrovka.module.helpers;

import semestrovka.module.entities.UserModel;
import semestrovka.module.exceptions.UncorrectDataException;
import semestrovka.module.managers.AbstractFileSystemManager;
import semestrovka.module.managers.ITokenManager;
import semestrovka.module.repositories.UserRepository;

import java.util.Optional;

public abstract class AbstractValidator implements IValidator {
    protected static final String OK = "ok";
    protected static final String NAME_PARAMETR = "name";
    protected static final String SECOND_NAME_PARAMETR = "second-name";
    protected static final String PHONE_PARAMETR = "phone-number";
    protected static final String EMAIL_PARAMETR = "email";
    protected static final String PASS_PARAMETR = "pass";
    protected static final String PRICE_PARAMETR = "price";

    private static final String EMAIL_REG = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9]))\\.){3}(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9])|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)])";

    protected final UserRepository userRepository;
    protected final AbstractFileSystemManager fileSystemManager;
    protected final ITokenManager tokenManager;

    public AbstractValidator(UserRepository userRepository, AbstractFileSystemManager fileSystemManager, ITokenManager tokenManager) {
        this.userRepository = userRepository;
        this.fileSystemManager = fileSystemManager;
        this.tokenManager = tokenManager;
    }

    protected boolean isValidName(String name) {
        if (name.length() < 2 || name.length() > 12) {
            return false;
        }
        return name.matches("^[A-Za-zа-яА-Я]+$");
    }

    protected boolean isValidPhoneNumber(String phone) {
        if (phone.length() != 11) {
            return false;
        }
        return phone.matches("^\\p{Digit}+$");
    }

    protected boolean isEmailUnvailable(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    protected UserModel isRightData(String email, String pass) {
        Optional<UserModel> optionalUser = userRepository.findByEmail(email);
        if (!optionalUser.isPresent()) {
            throw new UncorrectDataException("Неверный пароль или email");
        }
        UserModel user = optionalUser.get();
        if (user.getEmail().equals(email) && user.getPass().equals(pass)) {
            return user;
        }
        throw new UncorrectDataException("Неверный пароль или email");
    }

    protected boolean isValidPass(String pass) {
        return pass.length() >= 8;
    }

    protected boolean isEmailValid(String email) {
        return email.matches(EMAIL_REG);
    }
}
