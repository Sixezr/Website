package semestrovka.controllers;

import semestrovka.module.exceptions.ValidationException;
import semestrovka.module.helpers.Constants;
import semestrovka.module.services.ICartService;
import semestrovka.module.services.ISecurityService;

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
    private ISecurityService securityService;
    private ICartService cartService;

    @Override
    public void init(ServletConfig config) {
        context = config.getServletContext();
        securityService = (ISecurityService) context.getAttribute(Constants.SECURITY_SERVICE);
        cartService = (ICartService) context.getAttribute(Constants.CART_SERVICE);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (securityService.isAdmin(req)) {
            context.getRequestDispatcher("/WEB-INF/views/adding.jsp").forward(req, resp);
        } else {
            context.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if (securityService.isAdmin(req)) {
            try {
                cartService.saveProduct(req);
            } catch (ValidationException e) {
                req.getSession().setAttribute(Constants.ERROR, e.getMessage());
            }
            resp.sendRedirect(context.getContextPath() + "/menu/add");
        }
    }
}
