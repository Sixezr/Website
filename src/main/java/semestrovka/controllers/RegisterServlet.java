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

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    private ServletContext context;
    private ISecurityService securityService;

    @Override
    public void init(ServletConfig config) {
        context = config.getServletContext();
        securityService = (ISecurityService) context.getAttribute(Constants.SECURITY_SERVICE);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        context.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            securityService.register(req, resp);
            resp.sendRedirect(context.getContextPath() + "/account");
            return;
        } catch (ValidationException e) {
            req.getSession().setAttribute(Constants.ERROR, e.getMessage());
        }
        resp.sendRedirect(context.getContextPath() + "/register");
    }
}
