package synopsis.graphql.util.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import synopsis.graphql.model.smd.SmdResult;

@Service
public class SmdJsonToObjectConverter extends AbstractJsonToObjectConverter<SmdResult> {

    public SmdJsonToObjectConverter(ObjectMapper objectMapper) {
        super(SmdResult.class, "SMD", objectMapper);
    }
}
