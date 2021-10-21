package semestrovka.module.services;

import semestrovka.module.exceptions.ValidationException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface ISecurityService {
    boolean isAuthenticated(HttpServletRequest req);

    boolean authenticate(HttpServletRequest req);

    boolean isAdmin(HttpServletRequest req);

    void signIn(HttpServletRequest req, HttpServletResponse resp) throws ValidationException;

    void signInWithoutToken(HttpServletRequest req) throws ValidationException;

    void register(HttpServletRequest req, HttpServletResponse resp) throws ValidationException;
}
