package synopsis.graphql.model.dto.response;

import lombok.Builder;
import lombok.Data;
import synopsis.graphql.model.viewpage.ViewPage;

import java.util.List;

@Data
@Builder
public class ViewPageFetchResult {
    private ViewPage viewPage;
    private List<Error> errors;
}
