package ru.sixzr.controllers;

import ru.sixzr.module.entities.UserModel;
import ru.sixzr.module.helpers.Constants;
import ru.sixzr.module.helpers.Validator;
import ru.sixzr.module.managers.SessionManager;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private ServletContext context;
    private Validator validator;
    private SessionManager sessionManager;

    @Override
    public void init(ServletConfig config) {
        context = config.getServletContext();
        validator = (Validator) context.getAttribute(Constants.validator);
        sessionManager = (SessionManager) context.getAttribute(Constants.sessionManager);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (sessionManager.isAuthenticated(req)) {
            resp.sendRedirect(context.getContextPath()+"/account");
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
            sessionManager.signIn(req, user);
            resp.sendRedirect(context.getContextPath()+"/account");
        }
    }
}
