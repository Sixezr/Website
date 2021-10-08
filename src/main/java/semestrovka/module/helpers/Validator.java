package semestrovka.module.helpers;

import semestrovka.module.exceptions.FileSystemManagerException;
import semestrovka.module.entities.ProductModel;
import semestrovka.module.entities.UserModel;
import semestrovka.module.managers.*;
import semestrovka.module.repositories.UserRepository;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public class Validator {

    private static final String ERROR = "error";
    private static final String OK = "ok";
    private final UserRepository userRepository;
    private final AbstractFileSystemManager fileSystemManager;
    private final ITokenManager tokenManager;

    public Validator(UserRepository userRepository, AbstractFileSystemManager fileSystemManager, ITokenManager tokenManager) {
        this.userRepository = userRepository;
        this.fileSystemManager = fileSystemManager;
        this.tokenManager = tokenManager;
    }

    public ProductModel validateAddingForm(HttpServletRequest req) {
        String name = req.getParameter("name");
        String stringPrice = req.getParameter("price");

        if (name == null) {
            req.setAttribute(ERROR, "Название не может быть пустым");
            return null;
        } else if (stringPrice == null) {
            req.setAttribute(ERROR, "Цента не может быть пустой");
            return null;
        }

        name = name.trim();
        stringPrice = stringPrice.trim();
        double price;

        try {
            price = Double.parseDouble(stringPrice.replaceAll(",", "."));
        } catch (NumberFormatException e) {
            req.setAttribute(ERROR, "Цена не должна содержать ничего, кроме цифр");
            return null;
        }
        String photo;

        try {
            photo = fileSystemManager.downloadFile(req);
        } catch (FileSystemManagerException e) {
            req.setAttribute(ERROR, "Проблема с загрузкой файла, повторите попытку");
            return null;
        }

        req.setAttribute(OK, "Товар добавлен");
        return new ProductModel(name, price, photo);
    }

    public  UserModel validateChangingDataForm(HttpServletRequest req, UserModel user) {
        String name = req.getParameter("name");
        String secondName = req.getParameter("second-name");
        String phoneNumber = req.getParameter("phone-number");
        String pass = req.getParameter("pass");

        if (name == null) {
            req.setAttribute(ERROR, "Имя не может быть пустым");
            return null;
        } else if (secondName == null) {
            req.setAttribute(ERROR, "Фамилия не может быть пустым");
            return null;
        } else if (phoneNumber == null) {
            req.setAttribute(ERROR, "Номер телефона не может быть пустым");
            return null;
        } else if (pass == null) {
            req.setAttribute(ERROR, "Password не может быть пустым");
            return null;
        }

        name = name.trim();
        secondName = secondName.trim();
        phoneNumber = phoneNumber.trim();
        pass = pass.trim();

        if (user.getName().equals(name) && user.getSecondName().equals(secondName)
                && user.getPhoneNumber().equals(phoneNumber) && user.getPass().equals(pass)) {
            req.setAttribute(ERROR, "Нечего изменить");
            return null;
        }
        if (!isValidName(name)) {
            req.setAttribute(ERROR, "Имя должно содержать только буквы, а размер не более 12 и не менее 2");
            return null;
        }
        if (!isValidName(secondName)) {
            req.setAttribute(ERROR, "Фамилия должна содержать только буквы, а размер не более 12 и не менее 2");
            return null;
        }
        if (!isValidPhoneNumber(phoneNumber)) {
            req.setAttribute(ERROR, "Неверный формат номера телефона");
            return null;
        }
        if (pass.length() < 8) {
            req.setAttribute(ERROR, "Минимальная длина пароля - 8 символов");
            return null;
        }

        user.setName(name);
        user.setSecondName(secondName);
        user.setPhoneNumber(phoneNumber);
        user.setPass(pass);
        req.setAttribute(OK,"Ваши данные изменены");
        return user;
    }

    public UserModel validateSignInForm(HttpServletRequest req) {
        String email = req.getParameter("email");
        String pass = req.getParameter("pass");

        req.setAttribute("repeated_email", email);
        if (email == null) {
            req.setAttribute(ERROR, "Email не может быть пустым");
            return null;
        } else if (pass == null) {
            req.setAttribute(ERROR, "Password не может быть пустым");
            return null;
        }

        email = email.trim();
        pass = pass.trim();

        UserModel user = isRightData(email, pass);
        if (user == null) {
            req.setAttribute(ERROR, "Неверный пароль или email");
            return null;
        }
        return user;
    }

    public UserModel validateRegisterForm(HttpServletRequest req) {
        String name = req.getParameter("name");
        String secondName = req.getParameter("second-name");
        String phoneNumber = req.getParameter("phone-number");
        String email = req.getParameter("email");
        String pass = req.getParameter("pass");

        req.setAttribute("repeated_name", name);
        req.setAttribute("repeated_s_name", secondName);
        req.setAttribute("repeated_phone", phoneNumber);
        req.setAttribute("repeated_email", email);

        if (name == null) {
            req.setAttribute(ERROR, "Имя не может быть пустым");
            return null;
        } else if (secondName == null) {
            req.setAttribute(ERROR, "Фамилия не может быть пустым");
            return null;
        } else if (phoneNumber == null) {
            req.setAttribute(ERROR, "Номер телефона не может быть пустым");
            return null;
        } else if (email == null) {
            req.setAttribute(ERROR, "Email не может быть пустым");
            return null;
        } else if (pass == null) {
            req.setAttribute(ERROR, "Password не может быть пустым");
            return null;
        }

        name = name.trim();
        secondName = secondName.trim();
        phoneNumber = phoneNumber.trim();
        email = email.trim();
        pass = pass.trim();

        if (!isValidName(name)) {
            req.setAttribute(ERROR, "Имя должно содержать только буквы, а размер не более 12 и не менее 2");
            return null;
        }
        if (!isValidName(secondName)) {
            req.setAttribute(ERROR, "Фамилия должна содержать только буквы, а размер не более 12 и не менее 2");
            return null;
        }
        if (!isValidPhoneNumber(phoneNumber)) {
            req.setAttribute(ERROR, "Неверный формат номера телефона");
            return null;
        }
        if (pass.length() < 8) {
            req.setAttribute(ERROR, "Минимальная длина пароля - 8 символов");
            return null;
        }
        if (isEmailUnvailable(email)) {
            req.setAttribute(ERROR, "Данный email уже ипользуется");
            return null;
        }
        UserModel user = new UserModel(name, secondName, email, pass, phoneNumber.replaceAll("\\p{Punct}", ""));
        user.setToken(tokenManager.generateToken());
        return user;
    }

    private boolean isValidName(String name) {
        if (name.length() < 2 || name.length() > 12) {
            return false;
        }
        return name.matches("^[A-Za-zа-яА-Я]+$");
    }

    private boolean isValidPhoneNumber(String phone) {
        String newPhone = phone.replaceAll("\\p{Punct}", "");
        if (newPhone.length() != 11) {
            return false;
        }
        return newPhone.matches("^\\p{Digit}+$");
    }

    private boolean isEmailUnvailable(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    private UserModel isRightData(String email, String pass) {
        Optional<UserModel> optionalUser = userRepository.findByEmail(email);
        if (!optionalUser.isPresent()) {
            return null;
        }
        UserModel user = optionalUser.get();
        if (user.getEmail().equals(email) && user.getPass().equals(pass)) {
            return user;
        }
        return null;
    }
}
