package synopsis.graphql.model.dto.response;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Error {
    private final String message;
}
