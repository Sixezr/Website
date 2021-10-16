package semestrovka.module.services;

import semestrovka.module.exceptions.ValidationException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface IProfileService {
    void save(HttpServletRequest req) throws ValidationException;

    void signOut(HttpServletRequest req, HttpServletResponse resp);
}
