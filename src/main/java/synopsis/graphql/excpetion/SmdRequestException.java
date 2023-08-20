package synopsis.graphql.excpetion;

public class SmdRequestException extends RuntimeException {
    public SmdRequestException(String message) {
        super(message);
    }
}
