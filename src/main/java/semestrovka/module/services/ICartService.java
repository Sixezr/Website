package semestrovka.module.services;

import semestrovka.module.entities.ProductModel;
import semestrovka.module.exceptions.ValidationException;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface ICartService {
    List<ProductModel> findAll(String servletContext);

    void setUpDataOfProduct(HttpServletRequest req) throws ValidationException;

    void addProductToCart(HttpServletRequest req);

    void removeProductFromCart(HttpServletRequest req);

    void updateProduct(HttpServletRequest req) throws ValidationException;

    void saveProduct(HttpServletRequest req) throws ValidationException;

    void clearCart(HttpServletRequest req);
}
