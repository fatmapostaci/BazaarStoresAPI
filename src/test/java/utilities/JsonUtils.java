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

    public static JsonNode readJson(String fileName){
        try {
            return new ObjectMapper().readTree(new File( System.getProperty("user.dir") + "\\src\\test\\java\\json_files\\"  +fileName + ".json"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void setJson(JsonNode json, String key, String value) {
        ((ObjectNode) json).put(key, value);
        ;

    }


}
