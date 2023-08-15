package synopsis.graphql.model.viewpage;

import lombok.Data;

@Data
public class UserContentsPreference {
    private LikeInfo likeInfo;
    private boolean bookmark;
}
