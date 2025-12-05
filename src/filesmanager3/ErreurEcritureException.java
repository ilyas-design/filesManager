package filesmanager3;

/**
 * Exception levée lors d'une erreur d'écriture de fichier
 */
public class ErreurEcritureException extends Exception {
    public ErreurEcritureException(String message) {
        super(message);
    }

    public ErreurEcritureException(String message, Throwable cause) {
        super(message, cause);
    }
}
