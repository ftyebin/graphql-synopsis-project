package synopsis.graphql.util.converter;

import synopsis.graphql.model.scs.ScsResult;

public class ScsJsonToObjectConverter extends AbstractJsonToObjectConverter<ScsResult> {

    public ScsJsonToObjectConverter() {
        super(ScsResult.class, "SCS");
    }
}
