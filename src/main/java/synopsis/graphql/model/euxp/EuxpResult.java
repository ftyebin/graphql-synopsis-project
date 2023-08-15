package synopsis.graphql.model.euxp;

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
    public String IF;
    public List<Object> banners;
}