package semestrovka.controllers;

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

@WebServlet("/account")
public class AccountServlet extends HttpServlet {

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
        req.setCharacterEncoding("UTF-8");
        String action = req.getParameter("action");
        if (action != null) {
            switch (action) {
                case "change":
                    resp.sendRedirect(context.getContextPath()+"/account/change");
                    return;
                case "quit":
                    sessionManager.signOut(req, resp);
                    resp.sendRedirect(context.getContextPath()+"/login");
                    return;
            }
        }
        context.getRequestDispatcher("/WEB-INF/views/account.jsp").forward(req, resp);
    }
}
