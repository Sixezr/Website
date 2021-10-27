package semestrovka.module.services;

import semestrovka.module.entities.UserModel;
import semestrovka.module.helpers.IParser;
import semestrovka.module.managers.INetworkManager;
import semestrovka.module.managers.ITokenManager;
import semestrovka.module.repositories.UserRepository;

import java.util.Map;
import java.util.Optional;

public final class VkService implements IVkService {
    private final INetworkManager networkManager;
    private final IParser parser;
    private final UserRepository userRepository;
    private final ITokenManager tokenManager;

    public VkService(INetworkManager networkManager, IParser parser, UserRepository userRepository, ITokenManager tokenManager) {
        this.networkManager = networkManager;
        this.parser = parser;
        this.userRepository = userRepository;
        this.tokenManager = tokenManager;
    }

    @Override
    public UserModel getUser(String code) {
        UserModel user = new UserModel();
        Map<String, String> userData = parser.parseTokenRequest(networkManager.getTokenFromServer(code));

        Optional<UserModel> optionalUserModel = userRepository.findByEmail(userData.get("email"));
        if (optionalUserModel.isPresent()) {
            return optionalUserModel.get();
        }
        user.setEmail(userData.get("email"));

        Map<String, String> userName = parser.parseDataRequest(networkManager.getDataFromServer(userData.get("access_token"), userData.get("user_id")));
        user.setName(userName.get("first_name"));
        user.setSecondName(userName.get("last_name"));
        user.setToken(tokenManager.generateToken());
        userRepository.save(user);
        user.setId(userRepository.findByEmail(user.getEmail()).get().getId());
        return user;
    }
}
