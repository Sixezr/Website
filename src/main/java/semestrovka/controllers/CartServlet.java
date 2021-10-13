package semestrovka.controllers;

import semestrovka.module.helpers.Constants;
import semestrovka.module.managers.IAuthManager;
import semestrovka.module.managers.AuthManager;
import semestrovka.module.repositories.CartRepository;
import semestrovka.module.repositories.CartRepositoryJdbcImpl;
import semestrovka.module.repositories.UserRepositoryJdbcImp;
import semestrovka.module.repositories.UserRepository;

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
    private UserRepository userRepository;
    private CartRepository cartRepository;
    private IAuthManager authManager;

    @Override
    public void init(ServletConfig config) {
        context = config.getServletContext();
        userRepository = (UserRepositoryJdbcImp) context.getAttribute(Constants.USER_REPOSITORY);
        cartRepository = (CartRepositoryJdbcImpl) context.getAttribute(Constants.CART_REPOSITORY);
        authManager = (AuthManager) context.getAttribute(Constants.AUTH_MANAGER);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        context.getRequestDispatcher("/WEB-INF/views/cart.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String action = req.getParameter("action");
        if (action.equals("clearAll")) {
            authManager.getCart(req).clearCart();
            cartRepository.removeAll(authManager.getUser(req).getId());
        }
        context.getRequestDispatcher("/WEB-INF/views/cart.jsp").forward(req, resp);
    }
}
