package semestrovka.controllers;

import semestrovka.module.entities.UserModel;
import semestrovka.module.helpers.Constants;
import semestrovka.module.helpers.Validator;
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

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    private ServletContext context;
    private UserRepository repositoryJdbc;
    private Validator validator;
    private SessionManager sessionManager;

    @Override
    public void init(ServletConfig config) {
        context = config.getServletContext();
        repositoryJdbc = (UserRepositoryJdbcImp) context.getAttribute(Constants.userRepository);
        validator = (Validator) context.getAttribute(Constants.validator);
        sessionManager = (SessionManager) context.getAttribute(Constants.sessionManager);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        context.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        UserModel user = validator.validateRegisterForm(req);
        if (user != null) {
            repositoryJdbc.save(user);
            sessionManager.signIn(req, resp, repositoryJdbc.findByEmail(user.getEmail()).get());
            resp.sendRedirect(context.getContextPath() + "/account");
        } else {
            context.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(req, resp);
        }
    }
}
