package ru.sixzr.controllers;

import ru.sixzr.module.entities.UserModel;
import ru.sixzr.module.helpers.Validator;
import ru.sixzr.module.jdbc.SimpleDataSource;
import ru.sixzr.module.managers.SessionManager;
import ru.sixzr.module.managers.UserRepositoryJdbcImp;
import ru.sixzr.module.repositories.UserRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    private UserRepository repositoryJdbc;
    private Validator validator;

    @Override
    public void init() throws ServletException {
        super.init();
        repositoryJdbc = new UserRepositoryJdbcImp(new SimpleDataSource());
        validator = new Validator(repositoryJdbc);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        getServletContext().getRequestDispatcher("/WEB-INF/views/register.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        UserModel user = validator.validateRegisterForm(req);
        if (user != null) {
            repositoryJdbc.save(user);
            SessionManager.signIn(req, repositoryJdbc.findByEmail(user.getEmail()).get());
            resp.sendRedirect(getServletContext().getContextPath() + "/account");
        } else {
            getServletContext().getRequestDispatcher("/WEB-INF/views/register.jsp").forward(req, resp);
        }
    }
}
