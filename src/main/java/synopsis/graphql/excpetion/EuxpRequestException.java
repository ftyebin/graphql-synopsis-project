package synopsis.graphql.excpetion;

public class EuxpRequestException extends RuntimeException implements ServerRequestException {
    public EuxpRequestException(String message) {
        super(message);
    }
}
