package synopsis.graphql.model.viewpage;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PlayInfo {
    private MainPreviewPlay mainPreviewPlay;
    private AIHighlightPlay aiHighlightPlay;
    private TrailerPlay trailerPlay;
    private List<CornerPlay> cornerPlays;
    private List<SpecialPlay> specialPlays;
}
