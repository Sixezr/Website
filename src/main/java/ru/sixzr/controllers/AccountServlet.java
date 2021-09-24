package ru.sixzr.controllers;

import ru.sixzr.module.entities.UserModel;
import ru.sixzr.module.helpers.Validator;
import ru.sixzr.module.jdbc.SimpleDataSource;
import ru.sixzr.module.managers.SessionManager;
import ru.sixzr.module.repositories.UserRepositoryJdbcImp;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/account")
public class AccountServlet extends HttpServlet {

    private Validator validator;

    @Override
    public void init() throws ServletException {
        super.init();
        this.validator = new Validator(new UserRepositoryJdbcImp(new SimpleDataSource()));
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String action = req.getParameter("action");
        if (action != null) {
            switch (action) {
                case "change":
                    System.out.println(action);
                    break;
                case "quit":
                    SessionManager.signOut(req);
                    break;
            }
        }
        if (SessionManager.isSigned(req)) {
            getServletContext().getRequestDispatcher("/WEB-INF/views/account.jsp").forward(req, resp);
        } else {
            getServletContext().getRequestDispatcher("/WEB-INF/views/signin.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        UserModel user = validator.validateSignInForm(req);
        if (user == null) {
            getServletContext().getRequestDispatcher("/WEB-INF/views/signin.jsp").forward(req, resp);
        } else {
            SessionManager.signIn(req, user);
            getServletContext().getRequestDispatcher("/WEB-INF/views/account.jsp").forward(req, resp);
        }
    }
}
