package ru.sixzr.controllers;

import ru.sixzr.module.entities.UserModel;
import ru.sixzr.module.helpers.Validator;
import ru.sixzr.module.managers.SessionManager;
import ru.sixzr.module.repositories.UserRepositoryJdbcImp;

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

    private Validator validator;
    private ServletContext context;

    @Override
    public void init(ServletConfig config) {
        context = config.getServletContext();
        validator = (Validator) context.getAttribute("validator");
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
            context.getRequestDispatcher("/WEB-INF/views/account.jsp").forward(req, resp);
        } else {
            context.getRequestDispatcher("/WEB-INF/views/signin.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        UserModel user = validator.validateSignInForm(req);
        if (user == null) {
            context.getRequestDispatcher("/WEB-INF/views/signin.jsp").forward(req, resp);
        } else {
            SessionManager.signIn(req, user);
            context.getRequestDispatcher("/WEB-INF/views/account.jsp").forward(req, resp);
        }
    }
}
