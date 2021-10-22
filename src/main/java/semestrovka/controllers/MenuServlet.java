package semestrovka.controllers;

import semestrovka.module.exceptions.ValidationException;
import semestrovka.module.helpers.Constants;
import semestrovka.module.services.ICartService;
import semestrovka.module.services.ISecurityService;

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
    private ICartService cartService;
    private ISecurityService securityService;

    @Override
    public void init(ServletConfig config) {
        context = config.getServletContext();
        cartService = (ICartService) context.getAttribute(Constants.CART_SERVICE);
        securityService = (ISecurityService) context.getAttribute(Constants.SECURITY_SERVICE);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getSession().setAttribute("products", cartService.findAll(context.getContextPath()));
        context.getRequestDispatcher("/WEB-INF/views/menu.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        if (securityService.isAuthenticated(req)) {
            try {
                cartService.addProductToCart(req);
            } catch (ValidationException e) {
                return;
            }
            context.getRequestDispatcher("/WEB-INF/views/menu.jsp").forward(req, resp);
        } else {
            resp.sendRedirect(context.getContextPath() + "/login");
        }
    }
}
