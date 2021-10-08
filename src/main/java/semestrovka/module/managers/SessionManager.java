package semestrovka.module.managers;

import semestrovka.module.entities.UserModel;
import semestrovka.module.helpers.Constants;
import semestrovka.module.repositories.UserRepository;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

public final class SessionManager implements ISessionManager {

    private final UserRepository userRepository;
    private final ITokenManager tokenManager;

    public SessionManager(UserRepository userRepository, ITokenManager tokenManager) {
        this.userRepository = userRepository;
        this.tokenManager = tokenManager;
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
                if(c.getName().equals(Constants.TOKEN)){
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
        return  (UserModel) req.getSession().getAttribute(Constants.USER);
    }

    public void signIn(HttpServletRequest req, HttpServletResponse resp, UserModel user) {
        req.getSession().setAttribute("user", user);
        tokenManager.saveToken(resp, user.getToken());
    }

    public void signOut(HttpServletRequest req, HttpServletResponse resp) {
        req.getSession().removeAttribute("user");
        tokenManager.removeToken(req, resp);
    }
}
