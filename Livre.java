
/**
 * Classe représentant un livre dans la bibliothèque
 */
public class Livre {
    private String titre;
    private String auteur;
    private int annee;
    private String isbn;

    // Constructeur
    public Livre(String titre, String auteur, int annee, String isbn) {
        this.titre = titre;
        this.auteur = auteur;
        this.annee = annee;
        this.isbn = isbn;
    }

    // Getters
    public String getTitre() {
        return titre;
    }

    public String getAuteur() {
        return auteur;
    }

    public int getAnnee() {
        return annee;
    }

    public String getIsbn() {
        return isbn;
    }

    // Setters
    public void setTitre(String titre) {
        this.titre = titre;
    }

    public void setAuteur(String auteur) {
        this.auteur = auteur;
    }

    public void setAnnee(int annee) {
        this.annee = annee;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    /**
     * Convertit le livre en format texte pour sauvegarde
     * Format: Titre|Auteur|Année|ISBN
     */
    public String toFormatTexte() {
        return titre + "|" + auteur + "|" + annee + "|" + isbn;
    }

    /**
     * Crée un livre à partir d'une ligne de texte
     * Format attendu: Titre|Auteur|Année|ISBN
     */
    public static Livre fromFormatTexte(String ligne) throws FormatLigneException {
        if (ligne == null || ligne.trim().isEmpty() || ligne.trim().startsWith("#")) {
            throw new FormatLigneException("Ligne vide ou commentaire");
        }

        String[] parties = ligne.split("\\|");
        if (parties.length != 4) {
            throw new FormatLigneException("Format incorrect. Attendu: Titre|Auteur|Année|ISBN");
        }

        try {
            String titre = parties[0].trim();
            String auteur = parties[1].trim();
            int annee = Integer.parseInt(parties[2].trim());
            String isbn = parties[3].trim();

            return new Livre(titre, auteur, annee, isbn);
        } catch (NumberFormatException e) {
            throw new FormatLigneException("L'année doit être un nombre entier");
        }
    }

    @Override
    public String toString() {
        return "Livre{" +
                "titre='" + titre + '\'' +
                ", auteur='" + auteur + '\'' +
                ", annee=" + annee +
                ", isbn='" + isbn + '\'' +
                '}';
    }
}

