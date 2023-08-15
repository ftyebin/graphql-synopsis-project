package synopsis.graphql.model.dto.response;

import lombok.Builder;
import lombok.Data;
import synopsis.graphql.model.euxp.EuxpResult;
import synopsis.graphql.model.scs.ScsResult;
import synopsis.graphql.model.smd.SmdResult;

@Data
@Builder
public class SynopsisData {
    private final EuxpResult euxpResult;
    private final SmdResult smdResult;
    private final ScsResult scsResult;
}
