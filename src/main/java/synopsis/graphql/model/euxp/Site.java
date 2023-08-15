package synopsis.graphql.model.euxp;

import java.util.List;

public class Site {
    public String site_cd;
    public String site_nm;
    public int bas_pnt;
    public double avg_pnt;
    public int review_cnt;
    public List<Object> reviews;
    public List<DistInfo> dist_info;
}
