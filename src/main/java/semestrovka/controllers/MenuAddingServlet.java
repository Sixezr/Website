package semestrovka.controllers;

import semestrovka.module.entities.ProductModel;
import semestrovka.module.exceptions.ValidationException;
import semestrovka.module.helpers.Constants;
import semestrovka.module.helpers.Validator;
import semestrovka.module.managers.ISessionManager;
import semestrovka.module.managers.SessionManager;
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


@WebServlet("/menu/add")
@MultipartConfig
public class MenuAddingServlet extends HttpServlet {

    private ServletContext context;
    private ISessionManager securityManager;
    private Validator validator;
    private ProductRepository productRepository;

    @Override
    public void init(ServletConfig config) {
        context = config.getServletContext();
        productRepository = (ProductRepositoryJdbcImpl) context.getAttribute(Constants.PRODUCT_REPOSITORY);
        securityManager = (SessionManager) context.getAttribute(Constants.SESSION_MANAGER);
        validator = (Validator) context.getAttribute(Constants.VALIDATOR);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (securityManager.isAdmin(req)) {
            context.getRequestDispatcher("/WEB-INF/views/adding.jsp").forward(req, resp);
        } else {
            context.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        if (securityManager.isAdmin(req)) {
            try {
                ProductModel productModel = validator.validateAddingForm(req);
                productRepository.save(productModel);
            } catch (ValidationException e) {
                req.setAttribute(Constants.ERROR, e.getMessage());
            }
            context.getRequestDispatcher("/WEB-INF/views/adding.jsp").forward(req, resp);
        }
    }
}
