package semestrovka.module.managers;

import semestrovka.module.exceptions.UncorrectDataException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;

public final class NetworkManager implements INetworkManager {
    private static final String APP_ID = "7981542";
    private static final String PROTECTED_KEY = "1eee8i514XfC5qUVmLg7";
    private static final String CONTEXT = "sem";

    private static final String VK_TOKEN_URL = "https://oauth.vk.com/access_token?client_id=" + APP_ID + "&client_secret=" + PROTECTED_KEY + "&redirect_uri=http://localhost:8080/" + CONTEXT + "/vk&code=";
    private static final String VK_DATA_URL = "https://api.vk.com/method/users.get?user_ids=%s&&access_token=%s&v=5.131";

    public String getTokenFromServer(String code) {
        try {
            URLConnection conn = new URL(VK_TOKEN_URL + code).openConnection();
            conn.connect();
            StringBuilder sb = new StringBuilder();
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            reader.close();
            return sb.toString();
        } catch (IOException e) {
            throw new UncorrectDataException("Неправильный код", e);
        }
    }

    public String getDataFromServer(String token, String id) {
        try {
            URLConnection conn = new URL(String.format(VK_DATA_URL, id, token)).openConnection();
            conn.connect();
            StringBuilder sb = new StringBuilder();
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            reader.close();
            return sb.toString();
        } catch (IOException e) {
            throw new UncorrectDataException("Неправильный токен", e);
        }
    }
}
