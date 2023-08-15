package synopsis.graphql.model.scs;

import lombok.Data;

import java.util.List;

@Data
public class ScsResult {
    public String IF;
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


