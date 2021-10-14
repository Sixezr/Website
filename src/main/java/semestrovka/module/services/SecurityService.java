package semestrovka.module.services;

import semestrovka.module.entities.UserModel;
import semestrovka.module.exceptions.ValidationException;
import semestrovka.module.helpers.Constants;
import semestrovka.module.helpers.IValidator;
import semestrovka.module.managers.IAuthManager;
import semestrovka.module.repositories.UserRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public final class SecurityService implements ISecurityService {
    private final IAuthManager authManager;
    private final IValidator validator;
    private final UserRepository userRepository;

    public SecurityService(IAuthManager authManager, IValidator validator, UserRepository userRepository) {
        this.authManager = authManager;
        this.validator = validator;
        this.userRepository = userRepository;
    }

    @Override
    public boolean processGetLoginRequest(HttpServletRequest req, HttpServletResponse resp, String contextPath) throws IOException {
        if (authManager.isAuthenticated(req) || authManager.authenticate(req)) {
            resp.sendRedirect(contextPath + "/account");
            return false;
        } else {
            return true;
        }
    }

    @Override
    public boolean processLoginRequest(HttpServletRequest req, HttpServletResponse resp, String contextPath) throws IOException {
        try {
            UserModel user = validator.validateSignInForm(req);
            authManager.signIn(req, resp, user);
            resp.sendRedirect(contextPath + "/account");
            return false;
        } catch (ValidationException e) {
            req.setAttribute(Constants.ERROR, e.getMessage());
            return true;
        }
    }

    @Override
    public boolean processRegisterRequest(HttpServletRequest req, HttpServletResponse resp, String contextPath) throws IOException {
        try {
            UserModel user = validator.validateRegisterForm(req);
            userRepository.save(user);
            authManager.signIn(req, resp, userRepository.findByEmail(user.getEmail()).get());
            resp.sendRedirect(contextPath + "/account");
            return false;
        } catch (ValidationException e) {
            req.setAttribute(Constants.ERROR, e.getMessage());
            return true;
        }
    }
}
