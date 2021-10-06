package ru.sixzr.controllers;

import ru.sixzr.module.entities.UserModel;
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

@WebServlet("/account/change")
public class ChangeDataServlet extends HttpServlet {

    private ServletContext context;
    private Validator validator;
    private UserRepository repositoryJdbc;

    @Override
    public void init(ServletConfig config) {
        context = config.getServletContext();
        validator = (Validator) context.getAttribute("validator");
        repositoryJdbc = (UserRepositoryJdbcImp) context.getAttribute("userRepository");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (SessionManager.isSigned(req)) {
            context.getRequestDispatcher("/WEB-INF/views/account_change.jsp").forward(req, resp);
        } else {
            resp.sendRedirect(context.getContextPath()+"/account");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!SessionManager.isSigned(req)) {
            resp.sendRedirect(context.getContextPath()+"/account");
        }
        req.setCharacterEncoding("UTF-8");
        String action = req.getParameter("action");
        if (action != null) {
            switch (action) {
                case "save":
                    UserModel user = validator.validateChangingDataForm(req, (UserModel) req.getSession().getAttribute("user"));
                    //todo добавь сохранение в бд
                    context.getRequestDispatcher("/WEB-INF/views/account_change.jsp").forward(req, resp);
                    break;
                case "cancel":
                    resp.sendRedirect(context.getContextPath()+"/account");
                    break;
            }
        }
    }
}
