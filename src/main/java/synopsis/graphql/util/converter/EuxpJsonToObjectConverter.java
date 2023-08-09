package synopsis.graphql.util.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import synopsis.graphql.model.exup.EuxpResult;

@Slf4j
public
class EuxpJsonToObjectConverter {

    private EuxpJsonToObjectConverter(){
        throw new IllegalStateException("Euxp Json to Object Converter");
    }

    public static EuxpResult convert(String jsonData) throws RuntimeException {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            EuxpResult euxpResult = objectMapper.readValue(jsonData, EuxpResult.class);

            log.info("euxpResult = " + euxpResult);
            return euxpResult;

        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
