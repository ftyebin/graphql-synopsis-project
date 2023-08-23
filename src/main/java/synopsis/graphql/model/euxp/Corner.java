package synopsis.graphql.model.euxp;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Corner {
    public String cnr_id;
    public String cnr_nm;
    public String epsd_rslu_id;
    public String img_path;
    public String wat_fr_byte_val;
    public String tmtag_fr_tmsc;
    public String cnr_grp_id;
    public String cnr_btm_nm;
    public String cnr_typ_cd;
}
