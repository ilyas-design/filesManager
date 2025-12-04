/**
 * Exception levée lors d'une erreur de lecture du fichier
 */
public class ErreurLectureException extends Exception {
    public ErreurLectureException(String message) {
        super(message);
    }

    public ErreurLectureException(String message, Throwable cause) {
        super(message, cause);
    }
}

