package synopsis.graphql.model.dto.request;

import lombok.Data;

@Data
public class RequestSmdData {
    private String mac;
    private String stbId;
    private String seriesId;

    public RequestSmdData(RequestData requestData) {
        this.mac = requestData.getMac();
        this.stbId = requestData.getStbId();
        this.seriesId = requestData.getSeriesId();
    }
}
