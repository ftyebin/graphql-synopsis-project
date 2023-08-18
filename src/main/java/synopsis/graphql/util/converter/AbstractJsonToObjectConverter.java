package synopsis.graphql.util.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import lombok.extern.slf4j.Slf4j;
import synopsis.graphql.config.ObjectMapperHolder;
import synopsis.graphql.excpetion.JsonPropertyException;
import synopsis.graphql.excpetion.JsonToObjectException;

import java.util.Optional;

@Slf4j
public class AbstractJsonToObjectConverter<T> implements  JsonToObjectConverter<T> {

    private final Class<T> type;
    private final String typeName;

    public AbstractJsonToObjectConverter(Class<T> type, String typeName) {
        this.type = type;
        this.typeName = typeName;
    }

    @Override
    public Optional<T> convert(String jsonData) {

        ObjectMapper objectMapper = ObjectMapperHolder.getMapper();

        try {
            return Optional.ofNullable(objectMapper.readValue(jsonData, type));
        } catch (UnrecognizedPropertyException e) {
            throw new JsonPropertyException(typeName + " result에서 예상치 못한 json field 발견 " + e.getMessage());
        } catch (JsonMappingException e) {
            throw new RuntimeException(e);
        } catch (JsonProcessingException e) {
            throw new JsonToObjectException("Error found at " + e.getLocation() + " | " + typeName + " Json Object Mapping Error : " + e.getMessage());
        }
    }
}


