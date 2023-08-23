package synopsis.graphql.model.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class RequestScsData {

    private String stbId;
    private String hashId;
    private String seriesId;
    private String synopsisSearchType;
    private String uiName;
    private List<RequestScsPpvProduct> ppvProducts;

    public RequestScsData(RequestData requestData) {
        this.stbId = requestData.getStbId();
        this.hashId = requestData.getHashId();
        this.uiName = requestData.getUiName();
        this.seriesId = requestData.getSeriesId();
        this.synopsisSearchType = requestData.getSynopsisSearchType();
        this.ppvProducts = requestData.getPpvProducts();
    }
}
