package synopsis.graphql.util.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import lombok.extern.slf4j.Slf4j;
import synopsis.graphql.config.ObjectMapperHolder;
import synopsis.graphql.excpetion.JsonPropertyException;
import synopsis.graphql.excpetion.JsonToObjectException;
import synopsis.graphql.model.smd.SmdResult;

import java.util.Optional;

@Slf4j
public class SmdJsonToObjectConverter {

    private SmdJsonToObjectConverter() {
        throw new IllegalStateException("Smd Json to Object Converter");
    }

    public static Optional<SmdResult> convert(String jsonData) {

        ObjectMapper objectMapper = ObjectMapperHolder.getMapper();

        try {
            return Optional.ofNullable(objectMapper.readValue(jsonData, SmdResult.class));
        } catch (UnrecognizedPropertyException e) {
            throw  new JsonPropertyException("SMD result에서 예상치 못한 json field 발견 " + e.getMessage());
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            throw new JsonToObjectException("Error found at " + e.getLocation() + " | SMD Json Object Mapping Error : " + e.getMessage());
        }
        return Optional.empty();
    }
}
