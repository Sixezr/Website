package ru.sixzr.module.managers;

import ru.sixzr.module.entities.UserModel;

import javax.servlet.http.HttpServletRequest;

public class SessionManager {

    public static boolean isSigned(HttpServletRequest req) {
        return req.getSession().getAttribute("user") != null;
    }

    public static void signIn(HttpServletRequest req, UserModel user) {
        req.getSession().setAttribute("user", user);
    }

    public static void signOut(HttpServletRequest req) {
        req.getSession().removeAttribute("user");
    }
}
