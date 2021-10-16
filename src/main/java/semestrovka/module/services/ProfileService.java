package semestrovka.module.services;

import semestrovka.module.entities.UserModel;
import semestrovka.module.exceptions.ValidationException;
import semestrovka.module.helpers.AbstractValidator;
import semestrovka.module.managers.IAuthManager;
import semestrovka.module.repositories.UserRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public final class ProfileService implements IProfileService {
    private final IAuthManager authManager;
    private final AbstractValidator validator;
    private final UserRepository userRepository;

    public ProfileService(IAuthManager authManager, AbstractValidator validator, UserRepository userRepository) {
        this.authManager = authManager;
        this.validator = validator;
        this.userRepository = userRepository;
    }

    @Override
    public void save(HttpServletRequest req) throws ValidationException {
        UserModel user = validator.validateChangeDataForm(req, authManager.getUser(req));
        userRepository.update(user);
    }

    @Override
    public void signOut(HttpServletRequest req, HttpServletResponse resp) {
        authManager.signOut(req, resp);
    }
}
