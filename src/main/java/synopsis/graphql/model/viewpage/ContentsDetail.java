package synopsis.graphql.model.viewpage;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ContentsDetail {
    // private MetaInfo metaInfo;
    private String summary;
    private List<Cast> castInfos;
    private List<Prize> prizeInfos;
}
