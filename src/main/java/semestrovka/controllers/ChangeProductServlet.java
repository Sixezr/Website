package semestrovka.controllers;

import semestrovka.module.entities.ProductModel;
import semestrovka.module.exceptions.ValidationException;
import semestrovka.module.helpers.Constants;
import semestrovka.module.helpers.Validator;
import semestrovka.module.managers.IAuthManager;
import semestrovka.module.managers.AuthManager;
import semestrovka.module.repositories.ProductRepository;
import semestrovka.module.repositories.ProductRepositoryJdbcImpl;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@WebServlet("/menu/change")
@MultipartConfig
public class ChangeProductServlet extends HttpServlet {

    private ServletContext context;
    private IAuthManager authManager;
    private Validator validator;
    private ProductRepository productRepository;

    @Override
    public void init(ServletConfig config) {
        context = config.getServletContext();
        productRepository = (ProductRepositoryJdbcImpl) context.getAttribute(Constants.PRODUCT_REPOSITORY);
        authManager = (AuthManager) context.getAttribute(Constants.AUTH_MANAGER);
        validator = (Validator) context.getAttribute(Constants.VALIDATOR);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (authManager.isAdmin(req)) {
            try {
                int id = validator.validateChangeProductRequest(req);
                Optional<ProductModel> productModel = productRepository.findById(id);
                if (!productModel.isPresent()) {
                    throw new ValidationException();
                }
                req.setAttribute("id", productModel.get().getId());
                req.setAttribute("name", productModel.get().getName());
                req.setAttribute("price", productModel.get().getPrice());
                req.setAttribute("picture", productModel.get().getPicture());
                context.getRequestDispatcher("/WEB-INF/views/change_product.jsp").forward(req, resp);
            } catch (ValidationException e) {
                context.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(req, resp);
            }
        } else {
            context.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (authManager.isAdmin(req)) {
            try {
                ProductModel productModel = validator.validateChangeProductForm(req);
                productRepository.update(productModel);
            } catch (ValidationException e) {
                req.setAttribute(Constants.ERROR, e.getMessage());
            }
            context.getRequestDispatcher("/WEB-INF/views/change_product.jsp").forward(req, resp);
        } else {
            context.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(req, resp);
        }
    }
}
