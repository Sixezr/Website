package semestrovka.controllers;

import semestrovka.module.helpers.Constants;
import semestrovka.module.managers.AbstractFileSystemManager;
import semestrovka.module.managers.FileSystemManager;
import semestrovka.module.repositories.ProductRepository;
import semestrovka.module.repositories.ProductRepositoryJdbcImpl;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/menu")
public class MenuServlet extends HttpServlet {

    private ServletContext context;
    private ProductRepository productRepository;
    private AbstractFileSystemManager fileSystemManager;

    @Override
    public void init(ServletConfig config) {
        context = config.getServletContext();
        productRepository = (ProductRepositoryJdbcImpl) context.getAttribute(Constants.PRODUCT_REPOSITORY);
        fileSystemManager = (FileSystemManager) context.getAttribute(Constants.FILE_SYSTEM_MANAGER);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        fileSystemManager.copyFilesToWeb(context.getContextPath());
        req.setAttribute("products", productRepository.findAll());
        context.getRequestDispatcher("/WEB-INF/views/menu.jsp").forward(req, resp);
    }
}
