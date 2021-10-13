package semestrovka.module.managers;

import semestrovka.module.helpers.Constants;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

public final class TokenManager implements ITokenManager {

    private final int maxAge = 365 * 24 * 60 * 60;

    public String generateToken() {
        return String.valueOf(UUID.randomUUID());
    }

    public void saveToken(HttpServletResponse resp, String token) {
        Cookie cookie = new Cookie(Constants.TOKEN, token);
        cookie.setMaxAge(maxAge);
        resp.addCookie(cookie);
    }

    public void removeToken(HttpServletRequest req, HttpServletResponse resp) {
        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for (Cookie c : cookies) {
                if (c.getName().equals(Constants.TOKEN)) {
                    c.setMaxAge(0);
                    resp.addCookie(c);
                    return;
                }
            }
        }

    }
}
