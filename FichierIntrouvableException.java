/**
 * Exception levée lorsque le fichier n'est pas trouvé
 */
public class FichierIntrouvableException extends Exception {
    public FichierIntrouvableException(String message) {
        super(message);
    }

    public FichierIntrouvableException(String message, Throwable cause) {
        super(message, cause);
    }
}

