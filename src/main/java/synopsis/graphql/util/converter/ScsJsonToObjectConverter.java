package synopsis.graphql.util.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import synopsis.graphql.model.scs.ScsResult;

@Service
public class ScsJsonToObjectConverter extends AbstractJsonToObjectConverter<ScsResult> {

    public ScsJsonToObjectConverter(ObjectMapper objectMapper) {
        super(ScsResult.class, "SCS", objectMapper);
    }
}
