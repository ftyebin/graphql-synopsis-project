package synopsis.graphql.model.viewpage;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Cast {
    private String birth;
    private String imagePath;
    private String id;
    private String actorName;
    private String castingName;
    private String roleCode;
    private String roleName;
}
