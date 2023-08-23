package synopsis.graphql.model.viewpage;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Product {
    private String productTypeCode;
    private String episodeId;
    private String episodeResolutionId;
    private String discountTypeCode;
    private String languageCaptionTypeCode;
    private Boolean isNscreen;
    private Boolean isPossession;
    private Boolean isPpmFreeJoin;
    private String ppmGridIconImagePath;
    private String ppmProductName;
    private String productPriceId;
    private Integer productPriceVat;
    private Integer salePrice;
    private Integer salePriceVat;
    private String purchasePreferenceRank;
    private String purchasedWatchedCount;
    private String resolutionTypeCode;
    private Boolean isUsed;
    private Boolean isReservation;
}
