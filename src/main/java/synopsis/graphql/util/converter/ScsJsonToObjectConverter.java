package synopsis.graphql.util.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import synopsis.graphql.model.scs.ScsResult;

@Slf4j
public
class ScsJsonToObjectConverter {

    private ScsJsonToObjectConverter() {
        throw new IllegalStateException("Scs Json To Object Converter");
    }

    public static ScsResult convert(String jsonData) throws RuntimeException {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            return objectMapper.readValue(jsonData, ScsResult.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
