package semestrovka.controllers;

import semestrovka.module.helpers.Constants;
import semestrovka.module.helpers.Validator;
import semestrovka.module.managers.IAuthManager;
import semestrovka.module.managers.AuthManager;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/account")
public class AccountServlet extends HttpServlet {

    private ServletContext context;
    private Validator validator;
    private IAuthManager authManager;

    @Override
    public void init(ServletConfig config) {
        context = config.getServletContext();
        validator = (Validator) context.getAttribute(Constants.VALIDATOR);
        authManager = (AuthManager) context.getAttribute(Constants.AUTH_MANAGER);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action != null) {
            switch (action) {
                case "change":
                    resp.sendRedirect(context.getContextPath() + "/account/change");
                    return;
                case "quit":
                    authManager.signOut(req, resp);
                    resp.sendRedirect(context.getContextPath() + "/login");
                    return;
            }
        }
        context.getRequestDispatcher("/WEB-INF/views/account.jsp").forward(req, resp);
    }
}
