package semestrovka.module.helpers;

import semestrovka.module.exceptions.*;
import semestrovka.module.entities.ProductModel;
import semestrovka.module.entities.UserModel;
import semestrovka.module.managers.*;
import semestrovka.module.repositories.UserRepository;

import javax.servlet.http.HttpServletRequest;


public class Validator extends AbstractValidator {

    public Validator(UserRepository userRepository, AbstractFileSystemManager fileSystemManager, ITokenManager tokenManager) {
        super(userRepository, fileSystemManager, tokenManager);
    }

    public ProductModel validateAddingForm(HttpServletRequest req) {
        String name = req.getParameter(NAME_PARAMETR);
        String stringPrice = req.getParameter(PRICE_PARAMETR);

        if (name == null) {
            throw new EmptyParametrException("Название не может быть пустым");
        } else if (stringPrice == null) {
            throw new EmptyParametrException("Цена не может быть пустой");
        }

        name = name.trim();
        stringPrice = stringPrice.trim();
        double price;

        try {
            price = Double.parseDouble(stringPrice.replaceAll(",", "."));
        } catch (NumberFormatException e) {
            throw new InvalidPriceException("Цена не должна содержать ничего, кроме цифр", e);
        }
        String photo = fileSystemManager.downloadFile(req);

        req.setAttribute(OK, "Товар добавлен");
        return new ProductModel(name, price, photo);
    }

    @Override
    public ProductModel validateChangeProductForm(HttpServletRequest req) {
        String stringId = req.getParameter(ID_PARAMETR);
        String name = req.getParameter(NAME_PARAMETR);
        String stringPrice = req.getParameter(PRICE_PARAMETR);
        String pictureName = req.getParameter(PICTURE_NAME_PARAMETR);

        req.setAttribute(PRICE_PARAMETR, stringPrice);
        req.setAttribute(NAME_PARAMETR, name);
        req.setAttribute(ID_PARAMETR, stringId);
        req.setAttribute(PICTURE_NAME_PARAMETR, pictureName);

        if (name == null) {
            throw new EmptyParametrException("Название не может быть пустым");
        } else if (stringPrice == null) {
            throw new EmptyParametrException("Цена не может быть пустой");
        } else if (pictureName == null) {
            throw new EmptyParametrException("Название картинки не может быть пустым");
        } else if (stringId == null) {
            throw new EmptyParametrException("ID не может быть пустым");
        }

        stringId = stringId.trim();
        name = name.trim();
        stringPrice = stringPrice.trim();
        double price;
        int id;

        try {
            price = Double.parseDouble(stringPrice.replaceAll(",", "."));
        } catch (NumberFormatException e) {
            throw new InvalidPriceException("Цена не должна содержать ничего, кроме цифр", e);
        }
        try {
            id = Integer.parseInt(stringId);
        } catch (NumberFormatException e) {
            throw new ValidationException("ID должно быть числом", e);
        }
        fileSystemManager.downloadNewFile(req, pictureName);

        ProductModel productModel = new ProductModel(id, name, price, pictureName);
        req.setAttribute(OK, "Товар обновлен");
        return productModel;
    }

    @Override
    public int validateIdOfProductReq(HttpServletRequest req) {
        String stringId = req.getParameter("product_id");
        if (stringId == null) {
            throw new EmptyParametrException("Пустой id");
        }
        try {
            return Integer.parseInt(stringId);
        } catch (NumberFormatException e) {
            throw new UncorrectDataException("Id может быть только числом", e);
        }
    }

    public UserModel validateChangeDataForm(HttpServletRequest req, UserModel user) {
        String name = req.getParameter(NAME_PARAMETR);
        String secondName = req.getParameter(SECOND_NAME_PARAMETR);
        String phoneNumber = req.getParameter(PHONE_PARAMETR);
        String pass = req.getParameter(PASS_PARAMETR);

        if (name == null) {
            throw new EmptyParametrException("Имя не может быть пустым");
        } else if (secondName == null) {
            throw new EmptyParametrException("Фамилия не может быть пустой");
        } else if (phoneNumber == null) {
            throw new EmptyParametrException("Номер телефона не может быть пустым");
        } else if (pass == null) {
            throw new EmptyParametrException("Password не может быть пустым");
        }

        name = name.trim();
        secondName = secondName.trim();
        phoneNumber = phoneNumber.trim();
        pass = pass.trim();

        if (user.getName().equals(name) && user.getSecondName().equals(secondName)
                && user.getPhoneNumber().equals(phoneNumber) && user.getPass().equals(pass)) {
            throw new EqualDataException("Нечего изменить");
        }
        if (!isValidName(name)) {
            throw new InvalidNameException("Имя должно содержать только буквы, а размер не более 12 и не менее 2");
        }
        if (!isValidName(secondName)) {
            throw new InvalidNameException("Фамилия должна содержать только буквы, а размер не более 12 и не менее 2");
        }
        if (!isValidPhoneNumber(phoneNumber)) {
            throw new InvalidPhoneNumberException("Неверный формат номера телефона");
        }
        if (!isValidPass(pass)) {
            throw new WeakPasswordExceptions("Минимальная длина пароля - 8 символов");
        }

        user.setName(name);
        user.setSecondName(secondName);
        user.setPhoneNumber(phoneNumber);
        user.setPass(pass);
        req.setAttribute(OK, "Ваши данные изменены");
        return user;
    }

    public UserModel validateSignInForm(HttpServletRequest req) {
        String email = req.getParameter(EMAIL_PARAMETR);
        String pass = req.getParameter(PASS_PARAMETR);

        req.setAttribute("repeated_email", email);
        if (email == null) {
            throw new EmptyParametrException("Email не может быть пустым");
        } else if (pass == null) {
            throw new EmptyParametrException("Password не может быть пустым");
        }

        email = email.trim();
        pass = pass.trim();

        if (!isEmailValid(email)) {
            throw new InvalidEmailException("Неверный формат email");
        }

        return isRightData(email, pass);
    }

    public UserModel validateRegisterForm(HttpServletRequest req) {
        String name = req.getParameter(NAME_PARAMETR);
        String secondName = req.getParameter(SECOND_NAME_PARAMETR);
        String phoneNumber = req.getParameter(PHONE_PARAMETR);
        String email = req.getParameter(EMAIL_PARAMETR);
        String pass = req.getParameter(PASS_PARAMETR);

        req.setAttribute("repeated_name", name);
        req.setAttribute("repeated_s_name", secondName);
        req.setAttribute("repeated_phone", phoneNumber);
        req.setAttribute("repeated_email", email);

        if (name == null) {
            throw new EmptyParametrException("Имя не может быть пустым");
        } else if (secondName == null) {
            throw new EmptyParametrException("Фамилия не может быть пустой");
        } else if (phoneNumber == null) {
            throw new EmptyParametrException("Номер телефона не может быть пустым");
        } else if (email == null) {
            throw new EmptyParametrException("Email не может быть пустым");
        } else if (pass == null) {
            throw new EmptyParametrException("Password не может быть пустым");
        }

        name = name.trim();
        secondName = secondName.trim();
        phoneNumber = phoneNumber.trim().replaceAll("\\p{Punct}", "");
        email = email.trim();
        pass = pass.trim();

        if (!isValidName(name)) {
            throw new InvalidNameException("Имя должно содержать только буквы, а размер не более 12 и не менее 2");
        }
        if (!isValidName(secondName)) {
            throw new InvalidNameException("Фамилия должна содержать только буквы, а размер не более 12 и не менее 2");
        }
        if (!isValidPhoneNumber(phoneNumber)) {
            throw new InvalidPhoneNumberException("Неверный формат номера телефона");
        }
        if (!isValidPass(pass)) {
            throw new WeakPasswordExceptions("Минимальная длина пароля - 8 символов");
        }
        if (!isEmailValid(email)) {
            throw new InvalidEmailException("Неверный формат email");
        }
        if (isEmailUnvailable(email)) {
            throw new UnvailableEmailException("Данный email уже ипользуется");
        }

        UserModel user = new UserModel(name, secondName, email, pass, phoneNumber);
        user.setToken(tokenManager.generateToken());
        return user;
    }
}
