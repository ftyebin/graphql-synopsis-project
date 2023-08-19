package synopsis.graphql.excpetion;

public class SmdRequestException extends RuntimeException implements ServerRequestException {
    public SmdRequestException(String message) {
        super(message);
    }
}
