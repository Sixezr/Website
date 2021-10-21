package semestrovka.controllers;

import semestrovka.module.helpers.Constants;
import semestrovka.module.services.ICartService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/cart")
public class CartServlet extends HttpServlet {

    private ServletContext context;
    private ICartService cartService;

    @Override
    public void init(ServletConfig config) {
        context = config.getServletContext();
        cartService = (ICartService) context.getAttribute(Constants.CART_SERVICE);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        context.getRequestDispatcher("/WEB-INF/views/cart.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String action = req.getParameter("action");
        if (action != null) {
            switch (action) {
                case "clearAll":
                    cartService.clearCart(req);
                    break;
                case "add":
                    cartService.addProductToCart(req);
                    break;
                case "remove":
                    cartService.removeProductFromCart(req);
                    break;
                default:
                    return;
            }
        }
        context.getRequestDispatcher("/WEB-INF/views/cart.jsp").forward(req, resp);
    }
}
