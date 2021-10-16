package semestrovka.controllers;


import semestrovka.module.exceptions.ValidationException;
import semestrovka.module.helpers.Constants;
import semestrovka.module.services.IProfileService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/account/change")
public class ChangeDataAccountServlet extends HttpServlet {

    private ServletContext context;
    private IProfileService profileService;

    @Override
    public void init(ServletConfig config) {
        context = config.getServletContext();
        profileService = (IProfileService) context.getAttribute(Constants.PROFILE_SERVICE);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        context.getRequestDispatcher("/WEB-INF/views/account_change.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action != null) {
            switch (action) {
                case "save":
                    try {
                        profileService.save(req);
                    } catch (ValidationException e) {
                        req.setAttribute(Constants.ERROR, e.getMessage());
                    }
                    break;
                case "cancel":
                    resp.sendRedirect(context.getContextPath() + "/account");
                    return;
            }
        }
        context.getRequestDispatcher("/WEB-INF/views/account_change.jsp").forward(req, resp);
    }
}
