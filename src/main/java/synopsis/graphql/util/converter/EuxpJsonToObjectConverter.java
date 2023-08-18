package synopsis.graphql.util.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import lombok.extern.slf4j.Slf4j;
import synopsis.graphql.config.ObjectMapperHolder;
import synopsis.graphql.excpetion.JsonPropertyException;
import synopsis.graphql.excpetion.JsonToObjectException;
import synopsis.graphql.model.euxp.EuxpResult;

import java.util.Optional;

@Slf4j
public class EuxpJsonToObjectConverter {

    private EuxpJsonToObjectConverter(){
        throw new IllegalStateException("Euxp Json to Object Converter");
    }

    public static Optional<EuxpResult> convert(String jsonData) {
        ObjectMapper objectMapper = ObjectMapperHolder.getMapper();

        try {
            return Optional.ofNullable(objectMapper.readValue(jsonData, EuxpResult.class));
        } catch (UnrecognizedPropertyException e) {
            throw  new JsonPropertyException("EUXP result에서 예상치 못한 json field 발견 " + e.getMessage());
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            throw new JsonToObjectException("Error found at " + e.getLocation() + " | EUXP Json Object Mapping Error : " + e.getMessage());
        }
        return Optional.empty();
    }
}
