package semestrovka.controllers;

import semestrovka.module.exceptions.ValidationException;
import semestrovka.module.helpers.Constants;
import semestrovka.module.services.ISecurityService;

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
    private ISecurityService securityService;

    @Override
    public void init(ServletConfig config) {
        context = config.getServletContext();
        securityService = (ISecurityService) context.getAttribute(Constants.SECURITY_SERVICE);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (securityService.isAuthenticated(req)) {
            resp.sendRedirect(context.getContextPath() + "/account");
        } else {
            context.getRequestDispatcher("/WEB-INF/views/signin.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            if (req.getParameter("remember") != null) {
                securityService.signIn(req, resp);
            } else {
                securityService.signInWithoutToken(req);
            }
            String redirect = req.getParameter(Constants.REDIRECT);
            if (redirect != null && !redirect.isEmpty()) {
                resp.sendRedirect(redirect);
            } else {
                resp.sendRedirect(context.getContextPath() + "/account");
            }
            return;
        } catch (ValidationException e) {
            req.getSession().setAttribute(Constants.ERROR, e.getMessage());
        }
        resp.sendRedirect(req.getContextPath() + "/login");
    }
}
