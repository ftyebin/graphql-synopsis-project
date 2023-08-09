package synopsis.graphql.util.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import synopsis.graphql.model.smd.SmdResult;

@Slf4j
public
class SmdJsonToObjectConverter {

    private SmdJsonToObjectConverter() {
        throw new IllegalStateException("Smd Json to Object Converter");
    }

    public static SmdResult convert(String jsonData) throws RuntimeException {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            SmdResult smdResult = objectMapper.readValue(jsonData, SmdResult.class);

            log.info("scsResult = " + smdResult);
            return smdResult;

        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
