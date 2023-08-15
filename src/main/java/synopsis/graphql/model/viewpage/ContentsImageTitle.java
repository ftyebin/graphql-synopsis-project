package synopsis.graphql.model.viewpage;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ContentsImageTitle {
    private boolean isDark;
    private String imagePath;
}
