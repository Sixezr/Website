package semestrovka.module.services;

import semestrovka.module.entities.CartModel;
import semestrovka.module.entities.ProductModel;
import semestrovka.module.exceptions.ValidationException;
import semestrovka.module.helpers.AbstractValidator;
import semestrovka.module.managers.AbstractFileSystemManager;
import semestrovka.module.managers.IAuthManager;
import semestrovka.module.repositories.CartRepository;
import semestrovka.module.repositories.ProductRepository;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

public final class CartService implements ICartService {
    private final IAuthManager authManager;
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final AbstractFileSystemManager fileSystemManager;
    private final AbstractValidator validator;

    public CartService(IAuthManager authManager, CartRepository cartRepository, ProductRepository productRepository, AbstractFileSystemManager fileSystemManager, AbstractValidator validator) {
        this.authManager = authManager;
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.fileSystemManager = fileSystemManager;
        this.validator = validator;
    }

    @Override
    public List<ProductModel> findAll(String servletContext) {
        fileSystemManager.copyFilesToWeb(servletContext);
        return productRepository.findAll();
    }

    @Override
    public void setUpDataOfProduct(HttpServletRequest req) throws ValidationException {
        int id = validator.validateIdOfProductReq(req);
        Optional<ProductModel> productModel = productRepository.findById(id);
        if (!productModel.isPresent()) {
            throw new ValidationException();
        }
        req.setAttribute("id", productModel.get().getId());
        req.setAttribute("name", productModel.get().getName());
        req.setAttribute("price", productModel.get().getPrice());
        req.setAttribute("picture", productModel.get().getPicture());
    }

    @Override
    public void addProductToCart(HttpServletRequest req) {
        int id = validator.validateIdOfProductReq(req);
        Optional<ProductModel> productModel = productRepository.findById(id);
        if (productModel.isPresent()) {
            CartModel cartModel = authManager.getCart(req);
            cartModel.addProduct(productModel.get());
            cartRepository.addProduct(authManager.getUser(req).getId(), id);
        }
    }

    @Override
    public void removeProductFromCart(HttpServletRequest req) {
        int id = validator.validateIdOfProductReq(req);
        Optional<ProductModel> productModel = productRepository.findById(id);
        if (productModel.isPresent()) {
            CartModel cartModel = authManager.getCart(req);
            cartModel.removeProduct(productModel.get());
            cartRepository.removeProduct(authManager.getUser(req).getId(), id);
        }
    }

    @Override
    public void updateProduct(HttpServletRequest req) throws ValidationException {
        ProductModel productModel = validator.validateChangeProductForm(req);
        productRepository.update(productModel);
    }

    @Override
    public void saveProduct(HttpServletRequest req) throws ValidationException {
        ProductModel productModel = validator.validateAddingForm(req);
        productRepository.save(productModel);
    }

    @Override
    public void clearCart(HttpServletRequest req) {
        authManager.getCart(req).clearCart();
        cartRepository.removeAll(authManager.getUser(req).getId());
    }
}
