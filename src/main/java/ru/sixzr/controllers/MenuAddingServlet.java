package ru.sixzr.controllers;

import ru.sixzr.module.entities.ProductModel;
import ru.sixzr.module.helpers.Validator;
import ru.sixzr.module.managers.SecurityManager;
import ru.sixzr.module.repositories.ProductRepository;
import ru.sixzr.module.repositories.ProductRepositoryJdbcImpl;

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
    private SecurityManager securityManager;
    private Validator validator;
    private ProductRepository productRepository;

    @Override
    public void init(ServletConfig config) {
        context = config.getServletContext();
        productRepository = (ProductRepositoryJdbcImpl) context.getAttribute("productRepository");
        securityManager = (SecurityManager) context.getAttribute("securityManager");
        validator = (Validator) context.getAttribute("validator");
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
        ProductModel productModel = validator.validateAddingForm(req);
        if (productModel != null) {
            productRepository.save(productModel);
        }
        context.getRequestDispatcher("/WEB-INF/views/adding.jsp").forward(req, resp);
    }
}
