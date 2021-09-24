package ru.sixzr.controllers;

import ru.sixzr.module.jdbc.SimpleDataSource;
import ru.sixzr.module.repositories.UserRepositoryJdbcImp;
import ru.sixzr.module.repositories.UserRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/cart")
public class CartServlet extends HttpServlet {

    private UserRepository userRepository;

    @Override
    public void init() throws ServletException {
        userRepository = new UserRepositoryJdbcImp(new SimpleDataSource());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("users", userRepository.findAll());
        getServletContext().getRequestDispatcher("/WEB-INF/views/cart.jsp").forward(req, resp);
    }
}
