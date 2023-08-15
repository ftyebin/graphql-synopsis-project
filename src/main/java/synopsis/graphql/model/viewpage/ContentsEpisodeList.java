package synopsis.graphql.model.viewpage;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ContentsEpisodeList {
    private List<ContentsEpisode> list;
}
