package synopsis.graphql.model.viewpage;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AIHighlightPlay {
    private String episodeId;
    private String seriesId;
    private Integer startTime;
    private Integer previewTime;
}
