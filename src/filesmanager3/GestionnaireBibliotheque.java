package filesmanager3;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe pour gérer la collection de livres de la bibliothèque
 */
public class GestionnaireBibliotheque {
    private List<Livre> livres;
    private String cheminFichier;

    public GestionnaireBibliotheque(String cheminFichier) {
        this.cheminFichier = cheminFichier;
        this.livres = new ArrayList<>();
    }

    /**
     * Charge les livres depuis le fichier
     * @throws FichierIntrouvableException si le fichier n'existe pas
     * @throws ErreurLectureException si une erreur survient lors de la lecture
     */
    public void chargerDepuisFichier() throws FichierIntrouvableException, ErreurLectureException {
        livres.clear();
        File fichier = new File(cheminFichier);
        
        if (!fichier.exists()) {
            throw new FichierIntrouvableException("Le fichier " + cheminFichier + " n'existe pas.");
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(fichier))) {
            String ligne;
            while ((ligne = reader.readLine()) != null) {
                ligne = ligne.trim();
                if (!ligne.isEmpty()) {
                    String[] parties = ligne.split("\\|");
                    if (parties.length == 4) {
                        try {
                            String titre = parties[0].trim();
                            String auteur = parties[1].trim();
                            int annee = Integer.parseInt(parties[2].trim());
                            String isbn = parties[3].trim();
                            livres.add(new Livre(titre, auteur, annee, isbn));
                        } catch (NumberFormatException e) {
                            // Ignorer les lignes mal formatées
                        }
                    }
                }
            }
        } catch (FileNotFoundException e) {
            throw new FichierIntrouvableException("Le fichier " + cheminFichier + " n'existe pas.", e);
        } catch (IOException e) {
            throw new ErreurLectureException("Erreur lors de la lecture du fichier: " + e.getMessage(), e);
        }
    }

    /**
     * Sauvegarde les livres dans le fichier
     * @throws ErreurEcritureException si une erreur survient lors de l'écriture
     */
    public void sauvegarderDansFichier() throws ErreurEcritureException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(cheminFichier))) {
            for (Livre livre : livres) {
                writer.write(livre.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            throw new ErreurEcritureException("Erreur lors de l'écriture du fichier: " + e.getMessage(), e);
        }
    }

    /**
     * Ajoute un livre à la bibliothèque
     * @param livre le livre à ajouter
     */
    public void ajouterLivre(Livre livre) {
        if (livre != null) {
            livres.add(livre);
        }
    }

    /**
     * Supprime un livre de la bibliothèque par son ISBN
     * @param isbn l'ISBN du livre à supprimer
     */
    public void supprimerLivre(String isbn) {
        livres.removeIf(livre -> livre.getIsbn().equals(isbn));
    }

    /**
     * Retourne tous les livres de la bibliothèque
     * @return la liste de tous les livres
     */
    public List<Livre> getTousLesLivres() {
        return new ArrayList<>(livres);
    }

    /**
     * Retourne le nombre de livres dans la bibliothèque
     * @return le nombre de livres
     */
    public int getNombreLivres() {
        return livres.size();
    }

    /**
     * Retourne le chemin du fichier
     * @return le chemin du fichier
     */
    public String getCheminFichier() {
        return cheminFichier;
    }

    /**
     * Définit le chemin du fichier
     * @param cheminFichier le nouveau chemin du fichier
     */
    public void setCheminFichier(String cheminFichier) {
        this.cheminFichier = cheminFichier;
    }
}
