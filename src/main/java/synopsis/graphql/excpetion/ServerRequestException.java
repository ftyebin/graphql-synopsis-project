package synopsis.graphql.excpetion;

public class ServerRequestException extends RuntimeException {
    public ServerRequestException(String message) {
        super(message);
    }
}
