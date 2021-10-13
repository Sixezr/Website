package semestrovka.controllers;

import semestrovka.module.entities.UserModel;
import semestrovka.module.exceptions.ValidationException;
import semestrovka.module.helpers.Constants;
import semestrovka.module.helpers.Validator;
import semestrovka.module.managers.IAuthManager;
import semestrovka.module.managers.AuthManager;
import semestrovka.module.repositories.UserRepository;
import semestrovka.module.repositories.UserRepositoryJdbcImp;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/account/change")
public class ChangeDataServlet extends HttpServlet {

    private ServletContext context;
    private Validator validator;
    private UserRepository repositoryJdbc;
    private IAuthManager authManager;

    @Override
    public void init(ServletConfig config) {
        context = config.getServletContext();
        validator = (Validator) context.getAttribute(Constants.VALIDATOR);
        repositoryJdbc = (UserRepositoryJdbcImp) context.getAttribute(Constants.USER_REPOSITORY);
        authManager = (AuthManager) context.getAttribute(Constants.AUTH_MANAGER);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (authManager.isAuthenticated(req)) {
            context.getRequestDispatcher("/WEB-INF/views/account_change.jsp").forward(req, resp);
        } else {
            resp.sendRedirect(context.getContextPath() + "/login");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!authManager.isAuthenticated(req)) {
            resp.sendRedirect(context.getContextPath() + "/login");
        }
        String action = req.getParameter("action");
        if (action != null) {
            switch (action) {
                case "save":
                    try {
                        UserModel user = validator.validateChangeDataForm(req, authManager.getUser(req));
                        repositoryJdbc.update(user);
                    } catch (ValidationException e) {
                        req.setAttribute(Constants.ERROR, e.getMessage());
                    }
                    context.getRequestDispatcher("/WEB-INF/views/account_change.jsp").forward(req, resp);
                    break;
                case "cancel":
                    resp.sendRedirect(context.getContextPath() + "/account");
                    break;
            }
        }
    }
}
