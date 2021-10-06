package ru.sixzr.module.helpers;

public class Parser {
    public static double parse(String value) throws IllegalArgumentException {
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Can't parse");
        }
    }
}
