package synopsis.graphql.model.viewpage;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Prize {
    private String name;
    private String description;
    private String year;
}
