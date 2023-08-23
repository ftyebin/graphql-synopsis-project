package synopsis.graphql.util.converter;

import java.util.Optional;

public interface JsonToObjectConverter<T> {
    Optional<T> convert(String jsonData);
}
