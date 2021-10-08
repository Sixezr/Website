package semestrovka.module.helpers;

import semestrovka.module.entities.ProductModel;
import semestrovka.module.entities.UserModel;

import javax.servlet.http.HttpServletRequest;

public interface IValidator {
    ProductModel validateAddingForm(HttpServletRequest req);
    UserModel validateChangingDataForm(HttpServletRequest req, UserModel user);
    UserModel validateSignInForm(HttpServletRequest req);
    UserModel validateRegisterForm(HttpServletRequest req);
}
