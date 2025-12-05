package filesmanager3;


/**
 * Classe représentant un livre dans la bibliothèque
 */
public class Livre {
    private String titre;
    private String auteur;
    private int annee;
    private String isbn;

    public Livre(String titre, String auteur, int annee, String isbn) {
        this.titre = titre;
        this.auteur = auteur;
        this.annee = annee;
        this.isbn = isbn;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getAuteur() {
        return auteur;
    }

    public void setAuteur(String auteur) {
        this.auteur = auteur;
    }

    public int getAnnee() {
        return annee;
    }

    public void setAnnee(int annee) {
        this.annee = annee;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    @Override
    public String toString() {
        return titre + "|" + auteur + "|" + annee + "|" + isbn;
    }
}

