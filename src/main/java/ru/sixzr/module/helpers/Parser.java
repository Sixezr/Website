package ru.sixzr.module.helpers;

public class Parser {
    public static double parse(String value) throws IllegalArgumentException {
        try {
            double digit = Double.parseDouble(value);
            return digit;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Can't parse");
        }
    }
}
