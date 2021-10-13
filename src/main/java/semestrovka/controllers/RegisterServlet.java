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

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    private ServletContext context;
    private UserRepository repositoryJdbc;
    private Validator validator;
    private IAuthManager authManager;

    @Override
    public void init(ServletConfig config) {
        context = config.getServletContext();
        repositoryJdbc = (UserRepositoryJdbcImp) context.getAttribute(Constants.USER_REPOSITORY);
        validator = (Validator) context.getAttribute(Constants.VALIDATOR);
        authManager = (AuthManager) context.getAttribute(Constants.AUTH_MANAGER);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        context.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            UserModel user = validator.validateRegisterForm(req);
            repositoryJdbc.save(user);
            authManager.signIn(req, resp, repositoryJdbc.findByEmail(user.getEmail()).get());
            resp.sendRedirect(context.getContextPath() + "/account");
        } catch (ValidationException e) {
            req.setAttribute(Constants.ERROR, e.getMessage());
            context.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(req, resp);
        }
    }
}
