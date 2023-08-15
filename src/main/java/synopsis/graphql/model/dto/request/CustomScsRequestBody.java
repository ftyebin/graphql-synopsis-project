package synopsis.graphql.model.dto.request;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CustomScsRequestBody {
    private String response_format;
    private String ver;
    private String stb_id;
    private String hash_id;
    private String ui_name;
    private String sris_id;
    private String synopsis_type;
    private List<CustomScsPpvProducts> ppv_products;
}
