package semestrovka.module.managers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface ITokenManager {
    String generateToken();
    void saveToken(HttpServletResponse resp, String token);
    void removeToken(HttpServletRequest req, HttpServletResponse resp);
}
