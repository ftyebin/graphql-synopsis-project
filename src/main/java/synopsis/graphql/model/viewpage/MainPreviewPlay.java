package synopsis.graphql.model.viewpage;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MainPreviewPlay {
    private String episodeId;
    private String seriesId;
    private String episodeResolutionId;
    private int startTime;
    private int previewTime;
    private int totalTime;
}
