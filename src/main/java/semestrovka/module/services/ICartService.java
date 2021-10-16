package semestrovka.module.services;

import semestrovka.module.entities.CartModel;
import semestrovka.module.entities.ProductModel;
import semestrovka.module.exceptions.ValidationException;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

public interface ICartService {
    List<ProductModel> findAll(String servletContext);

    CartModel getCart(HttpServletRequest req);

    Optional<ProductModel> findProductByI(int id);

    void setUpDataOfProduct(HttpServletRequest req) throws ValidationException;

    void addProductToCart(HttpServletRequest req, int id);

    void updateProduct(HttpServletRequest req) throws ValidationException;

    void saveProduct(HttpServletRequest req) throws ValidationException;

    void clearCart(HttpServletRequest req);
}
