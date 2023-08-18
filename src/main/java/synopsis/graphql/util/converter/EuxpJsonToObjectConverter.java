package synopsis.graphql.util.converter;

import synopsis.graphql.model.euxp.EuxpResult;

public class EuxpJsonToObjectConverter extends AbstractJsonToObjectConverter<EuxpResult> {

    public EuxpJsonToObjectConverter() {
        super(EuxpResult.class, "EUXP");
    }

}