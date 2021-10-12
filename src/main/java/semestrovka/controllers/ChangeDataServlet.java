package semestrovka.controllers;

import semestrovka.module.entities.UserModel;
import semestrovka.module.exceptions.ValidationException;
import semestrovka.module.helpers.Constants;
import semestrovka.module.helpers.Validator;
import semestrovka.module.managers.ISessionManager;
import semestrovka.module.managers.SessionManager;
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
    private ISessionManager sessionManager;

    @Override
    public void init(ServletConfig config) {
        context = config.getServletContext();
        validator = (Validator) context.getAttribute(Constants.VALIDATOR);
        repositoryJdbc = (UserRepositoryJdbcImp) context.getAttribute(Constants.USER_REPOSITORY);
        sessionManager = (SessionManager) context.getAttribute(Constants.SESSION_MANAGER);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (sessionManager.isAuthenticated(req)) {
            context.getRequestDispatcher("/WEB-INF/views/account_change.jsp").forward(req, resp);
        } else {
            resp.sendRedirect(context.getContextPath()+"/login");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        if (!sessionManager.isAuthenticated(req)) {
            resp.sendRedirect(context.getContextPath()+"/login");
        }
        String action = req.getParameter("action");
        if (action != null) {
            switch (action) {
                case "save":
                    try {
                        UserModel user = validator.validateChangingDataForm(req, sessionManager.getUser(req));
                        repositoryJdbc.update(user);
                    } catch (ValidationException e) {
                        req.setAttribute(Constants.ERROR, e.getMessage());
                    }
                    context.getRequestDispatcher("/WEB-INF/views/account_change.jsp").forward(req, resp);
                    break;
                case "cancel":
                    resp.sendRedirect(context.getContextPath()+"/account");
                    break;
            }
        }
    }
}
