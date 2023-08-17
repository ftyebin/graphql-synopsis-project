package synopsis.graphql.model.viewpage;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ViewPage {
    private SynopsisBanner banners;
    private ContentsTitle title;
    private ContentsDetail details;
    private ContentsAdditional contentsAdditional;
    private UserContentsPreference userPreference;
    private ContentsEpisodeList episodeList;
    private PurchaseInfo purchaseInfo;
    private PlayInfo playInfo;
}
