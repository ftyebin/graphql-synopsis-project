package synopsis.graphql.util.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import lombok.extern.slf4j.Slf4j;
import synopsis.graphql.excpetion.CustomJsonPropertyException;
import synopsis.graphql.excpetion.JsonToObjectException;
import synopsis.graphql.model.smd.SmdResult;

@Slf4j
public
class SmdJsonToObjectConverter {

    private SmdJsonToObjectConverter() {
        throw new IllegalStateException("Smd Json to Object Converter");
    }

    public static SmdResult convert(String jsonData) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            return objectMapper.readValue(jsonData, SmdResult.class);
        } catch (UnrecognizedPropertyException e) {
            throw  new CustomJsonPropertyException("SMD result에서 예상치 못한 json field 발견 " + e.getMessage());
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            throw new JsonToObjectException("Error found at " + e.getLocation() + " | SMD Json Object Mapping Error : " + e.getMessage());
        }
        return null;
    }
}
