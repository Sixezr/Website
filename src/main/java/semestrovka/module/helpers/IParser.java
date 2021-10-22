package semestrovka.module.helpers;

import java.util.Map;

public interface IParser {
    Map<String, String> parseTokenRequest(String json);

    Map<String, String> parseDataRequest(String json);
}
