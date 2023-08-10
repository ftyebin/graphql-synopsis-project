package synopsis.graphql.model.dto.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class RequestData {
    private String stbId;
    private String mac;
    private String hashId;
    private String menuStbServiceId;
    private String seriesId;
    private String episodeId;
    private String lookupType;
    private String synopsisSearchType;
    private String uiName;
    private List<RequestScsPpvProduct> ppvProducts;
}
