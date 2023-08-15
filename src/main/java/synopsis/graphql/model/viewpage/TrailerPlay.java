package synopsis.graphql.model.viewpage;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TrailerPlay {
    private String episodeId;
    private String seriesId;
    private String productPriceId;

}
