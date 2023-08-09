package synopsis.graphql.model.scs;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class ScsResult {
    @JsonProperty("IF")
    public String iF;
    public String ver;
    public String ui_name;
    public String svc_name;
    public String result;
    public String reason;
    public String stb_id;
    public Object mobile_id;
    public String is_bookmark;
    public String yn_season_watch_all;
    public List<PpvProduct> ppv_products;
    public List<PpsProduct> pps_products;
    public Object last_watch_info;
}


