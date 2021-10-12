package semestrovka.module.helpers;

import semestrovka.module.entities.ProductModel;
import semestrovka.module.entities.UserModel;

import javax.servlet.http.HttpServletRequest;

public interface IValidator {
    ProductModel validateAddingForm(HttpServletRequest req);
    ProductModel validateChangeProductForm(HttpServletRequest req);
    int validateChangeProductRequest(HttpServletRequest req);
    UserModel validateChangeDataForm(HttpServletRequest req, UserModel user);
    UserModel validateSignInForm(HttpServletRequest req);
    UserModel validateRegisterForm(HttpServletRequest req);
}
