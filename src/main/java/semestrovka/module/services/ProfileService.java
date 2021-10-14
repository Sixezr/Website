package semestrovka.module.services;

import semestrovka.module.entities.UserModel;
import semestrovka.module.exceptions.ValidationException;
import semestrovka.module.helpers.AbstractValidator;
import semestrovka.module.helpers.Constants;
import semestrovka.module.managers.IAuthManager;
import semestrovka.module.repositories.UserRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public final class ProfileService implements IProfileService {
    private final static String ACTION_NAME = "action";

    private final IAuthManager authManager;
    private final AbstractValidator validator;
    private final UserRepository userRepository;

    public ProfileService(IAuthManager authManager, AbstractValidator validator, UserRepository userRepository) {
        this.authManager = authManager;
        this.validator = validator;
        this.userRepository = userRepository;
    }

    @Override
    public boolean processGetRequest(HttpServletRequest req, HttpServletResponse resp, String contextPath) throws IOException {
        String action = req.getParameter(ACTION_NAME);
        if (action != null) {
            switch (action) {
                case "change":
                    resp.sendRedirect(contextPath + "/account/change");
                    return false;
                case "quit":
                    authManager.signOut(req, resp);
                    resp.sendRedirect(contextPath + "/login");
                    return false;
            }
        }
        return true;
    }

    @Override
    public boolean processChangeDataRequest(HttpServletRequest req, HttpServletResponse resp, String contextPath) throws IOException {
        String action = req.getParameter(ACTION_NAME);
        if (action != null) {
            switch (action) {
                case "save":
                    try {
                        UserModel user = validator.validateChangeDataForm(req, authManager.getUser(req));
                        userRepository.update(user);
                    } catch (ValidationException e) {
                        req.setAttribute(Constants.ERROR, e.getMessage());
                    }
                    return true;
                case "cancel":
                    resp.sendRedirect(contextPath+ "/account");
                    return false;
            }
        }
        return true;
    }
}
