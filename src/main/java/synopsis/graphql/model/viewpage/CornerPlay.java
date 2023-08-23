package synopsis.graphql.model.viewpage;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CornerPlay {
    private String cornerBottomName;
    private String cornerGroupName;
    private String cornerId;
    private String cornerName;
    private String episodeResolutionId;
    private String imagePath;
}
