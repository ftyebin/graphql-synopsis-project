package synopsis.graphql.util.converter.viewpage;

import org.springframework.stereotype.Service;
import synopsis.graphql.model.scs.ScsResult;
import synopsis.graphql.model.smd.SmdResult;
import synopsis.graphql.model.viewpage.LikeInfo;
import synopsis.graphql.model.viewpage.UserContentsPreference;

import java.util.Objects;

@Service
public class UserContentsPreferenceConverter implements ViewpageConverter<UserContentsPreference> {

    private static final String LIKE_INFO_CHOSEN = "1";
    private static final String YES = "y";

    @Override
    public UserContentsPreference convert(Object... sources) {

        SmdResult smdResult = (SmdResult) sources[0];
        ScsResult scsResult = (ScsResult) sources[1];

        UserContentsPreference userPreference = new UserContentsPreference();

        if (Objects.equals(smdResult.like, LIKE_INFO_CHOSEN)) {
            userPreference.setLikeInfo(LikeInfo.LIKE);
        } else if (Objects.equals(smdResult.dislike, LIKE_INFO_CHOSEN)) {
            userPreference.setLikeInfo(LikeInfo.DISLIKE);
        } else {
            userPreference.setLikeInfo(LikeInfo.NONE);
        }

        userPreference.setBookmark(Objects.equals(scsResult.is_bookmark, YES));

        return userPreference;
    }
}
