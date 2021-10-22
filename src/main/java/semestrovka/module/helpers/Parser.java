package semestrovka.module.helpers;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import semestrovka.module.exceptions.UncorrectDataException;

import java.util.HashMap;
import java.util.Map;

public class Parser implements IParser {
    private final JSONParser parser;

    public Parser() {
        parser = new JSONParser();
    }

    @Override
    public Map<String, String> parseTokenRequest(String json) {
        Map<String, String> map = new HashMap<>();
        try {
            Object object = parser.parse(json);
            JSONObject jsonObject = (JSONObject) object;
            map.put("access_token", (String) jsonObject.get("access_token"));
            map.put("user_id", String.valueOf(jsonObject.get("user_id")));
            map.put("email", (String) jsonObject.get("email"));
        } catch (ParseException e) {
            throw new UncorrectDataException("Неверный json", e);
        }
        return map;
    }

    @Override
    public Map<String, String> parseDataRequest(String json) {
        Map<String, String> map = new HashMap<>();
        try {
            Object object = parser.parse(json);
            JSONObject jsonObject = (JSONObject) object;
            JSONArray request = (JSONArray) jsonObject.get("response");
            for (Object o : request) {
                JSONObject currentObject = (JSONObject) o;
                map.put("first_name", (String) currentObject.get("first_name"));
                map.put("last_name", (String) currentObject.get("last_name"));
            }
        } catch (ParseException e) {
            throw new UncorrectDataException("Неверный json", e);
        }
        return map;
    }
}
