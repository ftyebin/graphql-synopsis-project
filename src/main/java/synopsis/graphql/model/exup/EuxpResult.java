package synopsis.graphql.model.exup;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;


@Data
public class EuxpResult {
    private String result;
    public String reason;
    public String request_time;
    public Contents contents;
    public List<Purchare> purchares;
    public List<Object> series;
    public int total_banner_count;
    public String response_time;
    @JsonProperty("IF")
    public String iF;
    public List<Object> banners;
}
