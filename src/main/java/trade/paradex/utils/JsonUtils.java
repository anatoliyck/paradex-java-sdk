package trade.paradex.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

@UtilityClass
public class JsonUtils {

    private static final ObjectMapper MAPPER = new ObjectMapper()
            .disable(SerializationFeature.FAIL_ON_EMPTY_BEANS)
            .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

    @SneakyThrows
    public static JsonNode readTree(String json) {
        return MAPPER.readTree(json);
    }

    @SneakyThrows
    public static <T> T treeToValue(JsonNode node, Class<T> clazz) {
        return MAPPER.treeToValue(node, clazz);
    }

    @SneakyThrows
    public static String serialize(Object obj) {
        return MAPPER.writeValueAsString(obj);
    }

    @SneakyThrows
    public static <T> T deserialize(String json, TypeReference<T> typeReference) {
        if (json == null || json.isEmpty()) {
            return null;
        }

        return MAPPER.readValue(json, typeReference);
    }

    public static JsonNode findPath(JsonNode root, String... fieldNames) {
        if (root == null) {
            return null;
        }
        JsonNode currentNode = root;
        for (String fieldName : fieldNames) {
            currentNode = currentNode.get(fieldName);
            if (currentNode == null || currentNode.isNull()) {
                return null;
            }
        }
        return currentNode;
    }
}
