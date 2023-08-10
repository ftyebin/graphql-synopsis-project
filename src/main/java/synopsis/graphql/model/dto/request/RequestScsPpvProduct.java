package synopsis.graphql.model.dto.request;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class RequestScsPpvProduct {

    private String productPriceId;
    private String isNScreen;
    private String productTypeCode;
    private  String purchasePreferenceRank;
    private String isPossession;
    private String episodeId;
}
