package synopsis.graphql.util.converter;

import synopsis.graphql.model.smd.SmdResult;

public class SmdJsonToObjectConverter extends AbstractJsonToObjectConverter<SmdResult> {

    public SmdJsonToObjectConverter() {
        super(SmdResult.class, "SMD");
    }
}
