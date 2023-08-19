package synopsis.graphql.excpetion;

public class ScsRequestException extends RuntimeException implements ServerRequestException {
    public ScsRequestException(String message) {
        super(message);
    }
}
