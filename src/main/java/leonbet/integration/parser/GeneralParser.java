package leonbet.integration.parser;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

public class GeneralParser {
    private static final ObjectMapper mapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    public static <T> List<T> parseList(String json, TypeReference<List<T>> typeRef) throws JsonProcessingException {
        return mapper.readValue(json, typeRef);
    }

    public static <T> T parseObject(String json, TypeReference<T> typeRef) throws JsonProcessingException {
        return mapper.readValue(json, typeRef);
    }
}