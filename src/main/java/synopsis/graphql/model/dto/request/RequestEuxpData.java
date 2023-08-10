package synopsis.graphql.model.dto.request;

import lombok.Data;

@Data
public class RequestEuxpData {

    private String stbId;
    private String synopsisSearchType; // search_type
    private String lookupType; // yn_recent
    private String menuStbServiceId;
    private String episodeId;

    public RequestEuxpData() { }

    public RequestEuxpData(RequestData requestData){
        this.stbId =  requestData.getStbId();
        this.synopsisSearchType = requestData.getSynopsisSearchType();
        this.lookupType = requestData.getLookupType();
        this.menuStbServiceId = requestData.getMenuStbServiceId();
        this.episodeId = requestData.getEpisodeId();
    }
}
