package utilities;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.javafaker.Faker;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class JsonUtils {

    public static JsonNode readJson(String fileName) {
        try {
            return new ObjectMapper().readTree(new File(System.getProperty("user.dir") + "\\src\\test\\java\\json_files\\" + fileName + ".json"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void setJson(JsonNode json, String key, String value) {
        ((ObjectNode) json).put(key, value);

    }

    public static void setJsonRenameKey(JsonNode json, String oldKey, String newKey) {
        if (json.isObject()) {
            ObjectNode obj = (ObjectNode) json;
            JsonNode value = obj.get(oldKey);

            if (value != null) {
                obj.remove(oldKey);
                obj.set(newKey, value);
            }
        } else {
            throw new IllegalArgumentException("Provided JsonNode is not an ObjectNode");
        }
    }

    public static void setJsonDeleteKey(JsonNode json, String key) {
        if (json.isObject()) {
            ObjectNode obj = (ObjectNode) json;
            obj.remove(key);
        } else {
            throw new IllegalArgumentException("Provided JsonNode is not an ObjectNode");
        }
    }
}
