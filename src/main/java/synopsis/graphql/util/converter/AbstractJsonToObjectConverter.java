package synopsis.graphql.util.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import lombok.extern.slf4j.Slf4j;
import synopsis.graphql.config.ObjectMapperHolder;
import synopsis.graphql.excpetion.JsonConversionException;
import synopsis.graphql.excpetion.JsonPropertyException;
import synopsis.graphql.excpetion.JsonToObjectException;

import java.util.Optional;

@Slf4j
public abstract class AbstractJsonToObjectConverter<T> implements JsonToObjectConverter<T> {

    private final Class<T> type;
    private final String typeName;

    protected AbstractJsonToObjectConverter(Class<T> type, String typeName) {
        this.type = type;
        this.typeName = typeName;
    }

    @Override
    public Optional<T> convert(String jsonData) {

        ObjectMapper objectMapper = ObjectMapperHolder.getMapper();

        String errorMessage;

        try {
            return Optional.ofNullable(objectMapper.readValue(jsonData, type));
        } catch (UnrecognizedPropertyException e) {
            errorMessage = String.format("%s result 에서 예상치 못한 json field 발견 : %s", typeName, e.getMessage());
            throw new JsonPropertyException(errorMessage);
        } catch (JsonProcessingException e) {
            throw new JsonToObjectException("Error found at " + e.getLocation() + " | " + typeName + " Json Object Mapping Error : " + e.getMessage());
        } catch (Exception e) {
            errorMessage = String.format("%s 에서 Json 데이터 변환 실패 : %s", typeName, e);
            log.error(errorMessage);
            throw new JsonConversionException(errorMessage);
        }
    }
}


