package ru.sixzr.controllers;

import ru.sixzr.module.calculator.Calculator;
import ru.sixzr.module.helpers.Parser;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/calculator")
public class CalculatorServlet extends HttpServlet {

    private final String FIRST_PARAMETER = "first";
    private final String SECOND_PARAMETER = "second";

    private final Calculator calculator = new Calculator();

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String first = req.getParameter(FIRST_PARAMETER);
        String second = req.getParameter(SECOND_PARAMETER);

        if (first == null || second == null) {
            resp.getWriter().write("Empty");
            return;
        }

        double firstDigit;
        double secondDigit;

        try {
            firstDigit = Parser.parse(first);
            secondDigit = Parser.parse(second);

            resp.getWriter().print(calculator.divide(firstDigit, secondDigit));
        } catch (IllegalArgumentException e) {
            resp.getWriter().print(e.getLocalizedMessage());
        }
    }

}
