package semestrovka.module.helpers;

import semestrovka.module.entities.UserModel;
import semestrovka.module.exceptions.UncorrectDataException;
import semestrovka.module.managers.AbstractFileSystemManager;
import semestrovka.module.managers.ITokenManager;
import semestrovka.module.repositories.UserRepository;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

public abstract class AbstractValidator implements IValidator {
    protected static final String OK = "ok";
    protected static final String NAME_PARAMETER = "name";
    protected static final String SECOND_NAME_PARAMETER = "second-name";
    protected static final String PHONE_PARAMETER = "phone-number";
    protected static final String EMAIL_PARAMETER = "email";
    protected static final String PASS_PARAMETER = "pass";
    protected static final String PRICE_PARAMETER = "price";
    protected static final String PICTURE_NAME_PARAMETER = "picture";
    protected static final String ID_PARAMETER = "id";

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

    protected boolean isEmailUnavailable(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    protected UserModel isRightData(String email, String pass) {
        Optional<UserModel> optionalUser = userRepository.findByEmail(email);
        if (!optionalUser.isPresent()) {
            throw new UncorrectDataException("Неверный пароль или email");
        }
        UserModel user = optionalUser.get();
        if (user.getEmail().equals(email) && user.getPass().equals(md5Custom(pass))) {
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

    protected String md5Custom(String st) {
        MessageDigest messageDigest;
        byte[] digest = new byte[0];

        try {
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(st.getBytes());
            digest = messageDigest.digest();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        BigInteger bigInt = new BigInteger(1, digest);
        String md5Hex = bigInt.toString(16);

        while (md5Hex.length() < 32) {
            md5Hex = "0" + md5Hex;
        }

        return md5Hex;
    }
}
