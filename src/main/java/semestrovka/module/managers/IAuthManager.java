package semestrovka.module.managers;

import semestrovka.module.entities.CartModel;
import semestrovka.module.entities.UserModel;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface IAuthManager {
    boolean isAdmin(HttpServletRequest req);

    boolean isAuthenticated(HttpServletRequest req);

    boolean authenticate(HttpServletRequest req);

    UserModel getUser(HttpServletRequest req);

    CartModel getCart(HttpServletRequest req);

    void signIn(HttpServletRequest req, HttpServletResponse resp, UserModel user);

    void signInWithoutToken(HttpServletRequest req, UserModel user);

    void signOut(HttpServletRequest req, HttpServletResponse resp);
}
