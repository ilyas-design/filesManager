package filesmanager3;


/**
 * Exception levée lorsque le fichier demandé n'est pas trouvé
 */
public class FichierIntrouvableException extends Exception {
    public FichierIntrouvableException(String message) {
        super(message);
    }

    public FichierIntrouvableException(String message, Throwable cause) {
        super(message, cause);
    }
}

