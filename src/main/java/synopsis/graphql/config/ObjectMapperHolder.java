package synopsis.graphql.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ObjectMapperHolder {

    private static ObjectMapper objectMapper;

    @Autowired
    protected ObjectMapperHolder(ObjectMapper objectMapper) {
        ObjectMapperHolder.objectMapper = objectMapper;
    }

    public static ObjectMapper getMapper() {
        return objectMapper;
    }
}
