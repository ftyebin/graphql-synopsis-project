package synopsis.graphql.excpetion;

public class ResultDataNotFoundException extends RuntimeException {
    public ResultDataNotFoundException(String message) {
        super(message);
    }
}
