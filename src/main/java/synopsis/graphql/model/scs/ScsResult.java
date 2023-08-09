package synopsis.graphql.model.scs;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@RequiredArgsConstructor
public class ScsResult {
    @JsonProperty("IF")
    public final String iF;
    public final String ver;
    public final String ui_name;
    public final String svc_name;
    public final String result;
    public final String reason;
    public final String stb_id;
    public final Object mobile_id;
    public final String is_bookmark;
    public final String yn_season_watch_all;
    public final List<PpvProduct> ppv_products;
    public final List<PpsProduct> pps_products;
    public final Object last_watch_info;
}


