package synopsis.graphql.model.euxp;

import lombok.Data;

import java.util.List;


@Data
public class EuxpResult {
    public String result;
    public String reason;
    public String request_time;
    public Contents contents;
    public List<Purchare> purchares;
    public List<Series> series;
    public int total_banner_count;
    public String response_time;
    public String IF;
    public List<Banner> banners;
}
