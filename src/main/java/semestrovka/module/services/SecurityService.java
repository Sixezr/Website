package semestrovka.module.services;

import semestrovka.module.entities.UserModel;
import semestrovka.module.exceptions.ValidationException;
import semestrovka.module.helpers.IValidator;
import semestrovka.module.managers.IAuthManager;
import semestrovka.module.repositories.UserRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
    public boolean isAuthenticated(HttpServletRequest req) {
        return authManager.isAuthenticated(req) || authManager.authenticate(req);
    }

    @Override
    public boolean authenticate(HttpServletRequest req) {
        return authManager.authenticate(req);
    }

    @Override
    public boolean isAdmin(HttpServletRequest req) {
        return authManager.isAdmin(req);
    }

    @Override
    public void signIn(HttpServletRequest req, HttpServletResponse resp) throws ValidationException {
        UserModel user = validator.validateSignInForm(req);
        authManager.signIn(req, resp, user);
    }

    @Override
    public void signInWithoutToken(HttpServletRequest req) throws ValidationException {
        UserModel user = validator.validateSignInForm(req);
        authManager.signInWithoutToken(req, user);
    }

    @Override
    public void register(HttpServletRequest req, HttpServletResponse resp) throws ValidationException {
        UserModel user = validator.validateRegisterForm(req);
        userRepository.save(user);
        authManager.signIn(req, resp, userRepository.findByEmail(user.getEmail()).get());
    }
}
