package synopsis.graphql.model.dto.request;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString
public class CustomScsPpvProducts {
    private String prd_prc_id;
    private String yn_prd_nscreen;
    private String prd_typ_cd;
    private String purc_pref_rank;
    private String possn_yn;
    private String epsd_id;

}
