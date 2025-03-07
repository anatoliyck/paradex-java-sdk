package trade.paradex.exception;

import lombok.Getter;

@Getter
public class ParadexAPIException extends RuntimeException {

    private final String error;
    private final String errorMessage;

    public ParadexAPIException(String error, String errorMessage) {
        super("error: " + error + ", message: " + errorMessage);
        this.error = error;
        this.errorMessage = errorMessage;
    }
}
