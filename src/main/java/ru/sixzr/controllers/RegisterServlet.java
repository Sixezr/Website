package ru.sixzr.controllers;

import ru.sixzr.module.entities.UserModel;
import ru.sixzr.module.helpers.Constants;
import ru.sixzr.module.helpers.Validator;
import ru.sixzr.module.managers.SessionManager;
import ru.sixzr.module.repositories.UserRepository;
import ru.sixzr.module.repositories.UserRepositoryJdbcImp;

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
            sessionManager.signIn(req, repositoryJdbc.findByEmail(user.getEmail()).get());
            resp.sendRedirect(context.getContextPath() + "/account");
        } else {
            context.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(req, resp);
        }
    }
}
