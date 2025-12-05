import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Classe pour gérer la collection de livres de la bibliothèque
 */
public class GestionnaireBibliotheque {
    private List<Livre> livres;
    private GestionnaireRessources gestionnaireRessources;

    public GestionnaireBibliotheque(String nomFichier) {
        this.livres = new ArrayList<>();
        this.gestionnaireRessources = new GestionnaireRessources(nomFichier);
    }

    /**
     * Charge les livres depuis le fichier
     */
    public void chargerDepuisFichier() throws FichierIntrouvableException, ErreurLectureException {
        this.livres = gestionnaireRessources.chargerLivres();
    }

    /**
     * Sauvegarde les livres dans le fichier
     */
    public void sauvegarderDansFichier() throws ErreurEcritureException {
        gestionnaireRessources.sauvegarderLivres(livres);
    }

    /**
     * Ajoute un livre à la bibliothèque
     */
    public void ajouterLivre(Livre livre) {
        if (livre != null) {
            livres.add(livre);
        }
    }

    /**
     * Supprime un livre par son ISBN
     */
    public boolean supprimerLivre(String isbn) {
        return livres.removeIf(livre -> livre.getIsbn().equals(isbn));
    }

    /**
     * Recherche un livre par son titre
     */
    public List<Livre> rechercherParTitre(String titre) {
        return livres.stream()
                .filter(livre -> livre.getTitre().toLowerCase().contains(titre.toLowerCase()))
                .collect(Collectors.toList());
    }

    /**
     * Recherche un livre par son auteur
     */
    public List<Livre> rechercherParAuteur(String auteur) {
        return livres.stream()
                .filter(livre -> livre.getAuteur().toLowerCase().contains(auteur.toLowerCase()))
                .collect(Collectors.toList());
    }

    /**
     * Recherche un livre par son ISBN
     */
    public Livre rechercherParIsbn(String isbn) {
        return livres.stream()
                .filter(livre -> livre.getIsbn().equals(isbn))
                .findFirst()
                .orElse(null);
    }

    /**
     * Retourne tous les livres
     */
    public List<Livre> getTousLesLivres() {
        return new ArrayList<>(livres);
    }

    /**
     * Retourne le nombre de livres
     */
    public int getNombreLivres() {
        return livres.size();
    }

    /**
     * Affiche tous les livres
     */
    public void afficherTousLesLivres() {
        if (livres.isEmpty()) {
            System.out.println("Aucun livre dans la bibliothèque.");
        } else {
            System.out.println("\n=== Liste des livres ===");
            for (int i = 0; i < livres.size(); i++) {
                System.out.println((i + 1) + ". " + livres.get(i));
            }
        }
    }
}

