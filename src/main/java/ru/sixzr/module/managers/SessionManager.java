package ru.sixzr.module.managers;

import ru.sixzr.module.entities.UserModel;
import ru.sixzr.module.helpers.Constants;
import ru.sixzr.module.repositories.UserRepository;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public class SessionManager {

    private static final String TOKEN = "auth_token";
    private final UserRepository userRepository;

    public SessionManager(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean isAdmin(HttpServletRequest req) {
        UserModel user = (UserModel) req.getSession().getAttribute("user");
        if (user == null) {
            return false;
        }
        return user.isAdmin();
    }

    public boolean isAuthenticated(HttpServletRequest req) {
        return req.getSession().getAttribute("user") != null;
    }

    public boolean authenticate(HttpServletRequest req) {
        Cookie[] cookies = req.getCookies();
        if(cookies != null){
            for (Cookie c : cookies){
                if(c.getName().equals(TOKEN)){
                    Optional<UserModel> user = userRepository.findByToken(c.getValue());
                    if(user.isPresent()){
                        req.getSession().setAttribute("user", user.get());
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public UserModel getUser(HttpServletRequest req) {
        return  (UserModel) req.getSession().getAttribute(Constants.user);
    }

    public void signIn(HttpServletRequest req, UserModel user) {
        req.getSession().setAttribute("user", user);
    }

    public void signOut(HttpServletRequest req) {
        req.getSession().removeAttribute("user");
    }
}
