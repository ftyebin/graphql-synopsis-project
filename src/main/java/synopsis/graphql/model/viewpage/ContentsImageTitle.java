package synopsis.graphql.model.viewpage;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ContentsImageTitle {
    private Boolean isDark;
    private String imagePath;
}
