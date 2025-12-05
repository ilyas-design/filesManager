/**
 * Exception levée lorsque le format d'une ligne est incorrect
 */
public class FormatLigneException extends Exception {
    public FormatLigneException(String message) {
        super(message);
    }

    public FormatLigneException(String message, Throwable cause) {
        super(message, cause);
    }
}

