package synopsis.graphql.model.viewpage;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SynopsisBanner {
    private SynopsisImageBanner imageBanner;
    private SynopsisTextBanner textBanner;
    private SynopsisPlccBanner plccBanner;
}
