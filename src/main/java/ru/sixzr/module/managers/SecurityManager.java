package ru.sixzr.module.managers;

import javax.servlet.http.HttpServletRequest;

public class SecurityManager {

    public boolean isAdmin(HttpServletRequest req) {
        return true;
    }
}
