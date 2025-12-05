import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe pour gérer la lecture et l'écriture des fichiers de bibliothèque
 */
public class GestionnaireRessources {
    private String nomFichier;

    public GestionnaireRessources(String nomFichier) {
        this.nomFichier = nomFichier;
    }

    /**
     * Charge les livres depuis le fichier texte
     * @return Liste des livres chargés
     * @throws FichierIntrouvableException si le fichier n'existe pas
     * @throws ErreurLectureException si une erreur survient lors de la lecture
     */
    public List<Livre> chargerLivres() throws FichierIntrouvableException, ErreurLectureException {
        List<Livre> livres = new ArrayList<>();
        File fichier = new File(nomFichier);

        if (!fichier.exists()) {
            throw new FichierIntrouvableException("Le fichier " + nomFichier + " n'existe pas");
        }

        try (BufferedReader br = new BufferedReader(new FileReader(fichier))) {
            String ligne;
            int numeroLigne = 0;

            while ((ligne = br.readLine()) != null) {
                numeroLigne++;
                ligne = ligne.trim();

                // Ignorer les lignes vides et les commentaires
                if (ligne.isEmpty() || ligne.startsWith("#")) {
                    continue;
                }

                try {
                    Livre livre = Livre.fromFormatTexte(ligne);
                    livres.add(livre);
                } catch (FormatLigneException e) {
                    System.err.println("Erreur ligne " + numeroLigne + ": " + e.getMessage());
                    // On continue malgré l'erreur pour charger les autres livres
                }
            }
        } catch (FileNotFoundException e) {
            throw new FichierIntrouvableException("Le fichier " + nomFichier + " n'a pas pu être trouvé", e);
        } catch (IOException e) {
            throw new ErreurLectureException("Erreur lors de la lecture du fichier " + nomFichier, e);
        }

        return livres;
    }

    /**
     * Sauvegarde les livres dans le fichier texte
     * @param livres Liste des livres à sauvegarder
     * @throws ErreurEcritureException si une erreur survient lors de l'écriture
     */
    public void sauvegarderLivres(List<Livre> livres) throws ErreurEcritureException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(nomFichier))) {
            // Écrire un en-tête commenté
            bw.write("# Bibliothèque - Format: Titre|Auteur|Année|ISBN");
            bw.newLine();
            bw.write("# Les lignes commençant par # sont des commentaires");
            bw.newLine();
            bw.newLine();

            // Écrire chaque livre
            for (Livre livre : livres) {
                bw.write(livre.toFormatTexte());
                bw.newLine();
            }
        } catch (IOException e) {
            throw new ErreurEcritureException("Erreur lors de l'écriture dans le fichier " + nomFichier, e);
        }
    }

    public String getNomFichier() {
        return nomFichier;
    }

    public void setNomFichier(String nomFichier) {
        this.nomFichier = nomFichier;
    }
}

