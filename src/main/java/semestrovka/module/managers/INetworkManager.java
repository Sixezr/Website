package semestrovka.module.managers;

public interface INetworkManager {
    String getTokenFromServer(String code);

    String getDataFromServer(String token, String id);
}
