package ru.sixzr.controllers;

import ru.sixzr.module.jdbc.SimpleDataSource;
import ru.sixzr.module.managers.FileSystemManager;
import ru.sixzr.module.repositories.ProductRepository;
import ru.sixzr.module.repositories.ProductRepositoryJdbcImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/menu")
public class MenuServlet extends HttpServlet {

    private ProductRepository productRepository;
    private FileSystemManager fileSystemManager;

    @Override
    public void init() {
        productRepository = new ProductRepositoryJdbcImpl(new SimpleDataSource());
        fileSystemManager = new FileSystemManager();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        fileSystemManager.copyFilesToWeb(getServletContext().getContextPath());
        req.setAttribute("products", productRepository.findAll());
        getServletContext().getRequestDispatcher("/WEB-INF/views/menu.jsp").forward(req, resp);
    }
}
