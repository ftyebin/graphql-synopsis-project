package synopsis.graphql.model.viewpage;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PlayInfo {
    private MainPreviewPlay mainPreviewPlay;
    private TrailerPlay trailerPlay;
}
