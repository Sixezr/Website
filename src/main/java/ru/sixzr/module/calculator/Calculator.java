package ru.sixzr.module.calculator;

public class Calculator {

    public double divide(double a, double b) {
        if (b == 0) {
            return -1;
        }
        return a / b;
    }
}
