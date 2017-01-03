package exception;

/**
 * User:Anton_Iehorov
 * Date: 1/3/2017
 * Time: 3:24 PM
 */
public class ConnectionException extends  RuntimeException {
    public ConnectionException(String message) {
        super(message);
    }
}
