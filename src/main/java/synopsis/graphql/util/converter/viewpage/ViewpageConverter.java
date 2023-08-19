package synopsis.graphql.util.converter.viewpage;

public interface ViewpageConverter<T> {
    T convert(Object... sources);
}
