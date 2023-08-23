package synopsis.graphql.model.viewpage;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ContentsEpisode {
    private String imagePath;
    private String episodeNumber;
    // private Integer watchedProgress;
    private String isNotBroadcasted;
}
