package ru.sixzr.module.managers;

import ru.sixzr.module.entities.UserModel;

import javax.servlet.http.HttpServletRequest;

public class SecurityManager {

    public boolean isAdmin(HttpServletRequest req) {
        return ((UserModel)req.getSession().getAttribute("user")).isAdmin();
    }
}
