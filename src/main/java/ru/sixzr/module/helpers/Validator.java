package ru.sixzr.module.helpers;

import ru.sixzr.exceptions.FileException;
import ru.sixzr.module.entities.ProductModel;
import ru.sixzr.module.entities.UserModel;
import ru.sixzr.module.managers.FileSystemManager;
import ru.sixzr.module.repositories.UserRepository;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public class Validator {

    private static final String ERROR = "error";
    private UserRepository userRepository;
    private FileSystemManager fileSystemManager;

    public Validator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Validator() {
        this.fileSystemManager = new FileSystemManager();
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
        } catch (FileException e) {
            req.setAttribute(ERROR, "Проблема с загрузкой файла, повторите попытку");
            return null;
        }

        return new ProductModel(name, price, photo);
    }

    public UserModel validateSignInForm(HttpServletRequest req) {
        String email = req.getParameter("email");
        String pass = req.getParameter("pass");
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
        return new UserModel(name, secondName, email, pass, phoneNumber.replaceAll("\\p{Punct}", ""));
    }

    private static boolean isValidName(String name) {
        if (name.length() < 2 || name.length() > 12) {
            return false;
        }
        return name.matches("^[A-Za-zа-яА-Я]+$");
    }

    private static boolean isValidPhoneNumber(String phone) {
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
