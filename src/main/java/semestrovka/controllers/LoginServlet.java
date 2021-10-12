package semestrovka.controllers;

import semestrovka.module.entities.UserModel;
import semestrovka.module.exceptions.ValidationException;
import semestrovka.module.helpers.Constants;
import semestrovka.module.helpers.Validator;
import semestrovka.module.managers.ISessionManager;
import semestrovka.module.managers.SessionManager;

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
    private ISessionManager sessionManager;

    @Override
    public void init(ServletConfig config) {
        context = config.getServletContext();
        validator = (Validator) context.getAttribute(Constants.VALIDATOR);
        sessionManager = (SessionManager) context.getAttribute(Constants.SESSION_MANAGER);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (sessionManager.isAuthenticated(req) || sessionManager.authenticate(req)) {
            resp.sendRedirect(context.getContextPath()+"/account");
        } else {
            context.getRequestDispatcher("/WEB-INF/views/signin.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        try {
            UserModel user = validator.validateSignInForm(req);
            sessionManager.signIn(req, resp, user);
            resp.sendRedirect(context.getContextPath()+"/account");
        } catch (ValidationException e) {
            req.setAttribute(Constants.ERROR, e.getMessage());
            context.getRequestDispatcher("/WEB-INF/views/signin.jsp").forward(req, resp);
        }
    }
}
