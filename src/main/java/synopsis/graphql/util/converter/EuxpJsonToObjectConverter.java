package synopsis.graphql.util.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import synopsis.graphql.model.euxp.EuxpResult;

@Service
public class EuxpJsonToObjectConverter extends AbstractJsonToObjectConverter<EuxpResult> {

    public EuxpJsonToObjectConverter(ObjectMapper objectMapper) {
        super(EuxpResult.class, "EUXP", objectMapper);
    }

}