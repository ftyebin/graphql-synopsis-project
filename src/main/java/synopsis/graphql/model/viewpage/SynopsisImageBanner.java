package synopsis.graphql.model.viewpage;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SynopsisImageBanner {
    private String imagePath;
    private String callTypeCode;
    private String callUrl;
    private String vasId;
    private String vasServiceId;
}
