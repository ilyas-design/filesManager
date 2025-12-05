package filesmanager3;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.util.List;

/**
 * Interface graphique pour la gestion de bibliothèque
 */
public class BibliothequeGUI extends JFrame {
    private GestionnaireBibliotheque bibliotheque;
    private DefaultTableModel tableModel;
    private JTable tableLivres;
    private JTextField champTitre, champAuteur, champAnnee, champISBN;
    private JTextField champFichier;
    private JLabel labelStatut;
    private String cheminFichierActuel;

    public BibliothequeGUI() {
        cheminFichierActuel = "bibliotheque.txt";
        bibliotheque = new GestionnaireBibliotheque(cheminFichierActuel);
        initialiserInterface();
        chargerLivres();
    }

    private void initialiserInterface() {
        setTitle("Gestion de Bibliothèque");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel principal
        JPanel panelPrincipal = new JPanel(new BorderLayout(5, 5));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Panel du haut : Boutons
        JPanel panelBoutonsHaut = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton btnOuvrir = new JButton("Ouvrir");
        JButton btnCreerNouveau = new JButton("Créer Nouveau");
        JButton btnAjouterLivre = new JButton("+ Ajouter un Livre");
        JButton btnSupprimer = new JButton("- Supprimer");
        JButton btnSauvegarder = new JButton("Sauvegarder");
        JButton btnActualiser = new JButton("Actualiser");

        btnOuvrir.addActionListener(e -> ouvrirFichier());
        btnCreerNouveau.addActionListener(e -> creerNouveauFichier());
        btnAjouterLivre.addActionListener(e -> ajouterLivre());
        btnSupprimer.addActionListener(e -> supprimerLivre());
        btnSauvegarder.addActionListener(e -> sauvegarderLivres());
        btnActualiser.addActionListener(e -> actualiser());

        panelBoutonsHaut.add(btnOuvrir);
        panelBoutonsHaut.add(btnCreerNouveau);
        panelBoutonsHaut.add(btnAjouterLivre);
        panelBoutonsHaut.add(btnSupprimer);
        panelBoutonsHaut.add(btnSauvegarder);
        panelBoutonsHaut.add(btnActualiser);

        // Champ du chemin du fichier
        JPanel panelFichier = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelFichier.add(new JLabel("Fichier:"));
        champFichier = new JTextField(50);
        champFichier.setEditable(false);
        champFichier.setText(new File(cheminFichierActuel).getAbsolutePath());
        panelFichier.add(champFichier);

        // Panel du chemin de fichier et boutons
        JPanel panelHaut = new JPanel(new BorderLayout());
        panelHaut.add(panelBoutonsHaut, BorderLayout.NORTH);
        panelHaut.add(panelFichier, BorderLayout.SOUTH);

        // Tableau des livres
        String[] colonnes = {"Titre", "Auteur", "Année", "ISBN"};
        tableModel = new DefaultTableModel(colonnes, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tableLivres = new JTable(tableModel);
        tableLivres.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        // Personnaliser l'en-tête du tableau avec fond bleu sur toute la surface
        javax.swing.table.JTableHeader header = tableLivres.getTableHeader();
        header.setBackground(new Color(0, 0, 255)); // Bleu
        header.setForeground(Color.WHITE);
        header.setFont(header.getFont().deriveFont(Font.BOLD));
        header.setOpaque(true);
        
        // Utiliser un renderer personnalisé pour chaque colonne de l'en-tête
        for (int i = 0; i < tableLivres.getColumnCount(); i++) {
            tableLivres.getColumnModel().getColumn(i).setHeaderRenderer(new javax.swing.table.DefaultTableCellRenderer() {
                @Override
                public Component getTableCellRendererComponent(JTable table, Object value,
                        boolean isSelected, boolean hasFocus, int row, int column) {
                    JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                    label.setBackground(new Color(0, 0, 255)); // Bleu
                    label.setForeground(Color.WHITE);
                    label.setOpaque(true);
                    label.setHorizontalAlignment(JLabel.CENTER);
                    return label;
                }
            });
        }
        
        JScrollPane scrollPane = new JScrollPane(tableLivres);

        // Panel du bas : Formulaire d'ajout
        JPanel panelBas = new JPanel(new BorderLayout());
        panelBas.setBorder(BorderFactory.createTitledBorder("Ajouter un livre"));

        JPanel panelFormulaire = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelFormulaire.add(new JLabel("Titre:"));
        champTitre = new JTextField(15);
        panelFormulaire.add(champTitre);

        panelFormulaire.add(new JLabel("Auteur:"));
        champAuteur = new JTextField(15);
        panelFormulaire.add(champAuteur);

        panelFormulaire.add(new JLabel("Année:"));
        champAnnee = new JTextField(10);
        panelFormulaire.add(champAnnee);

        panelFormulaire.add(new JLabel("ISBN:"));
        champISBN = new JTextField(15);
        panelFormulaire.add(champISBN);

        JButton btnAjouterTableau = new JButton("Ajouter au Tableau");
        btnAjouterTableau.addActionListener(e -> ajouterLivreAuTableau());
        panelFormulaire.add(btnAjouterTableau);

        // Label de statut
        labelStatut = new JLabel("Fichier ouvert: 0 livre(s) chargé(s)");
        labelStatut.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        panelBas.add(panelFormulaire, BorderLayout.CENTER);
        panelBas.add(labelStatut, BorderLayout.SOUTH);

        // Assemblage
        panelPrincipal.add(panelHaut, BorderLayout.NORTH);
        panelPrincipal.add(scrollPane, BorderLayout.CENTER);
        panelPrincipal.add(panelBas, BorderLayout.SOUTH);

        add(panelPrincipal);
    }

    private void ouvrirFichier() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Ouvrir un fichier de bibliothèque");
        fileChooser.setFileFilter(new FileNameExtensionFilter("Fichiers texte", "txt"));
        
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File fichierSelectionne = fileChooser.getSelectedFile();
            cheminFichierActuel = fichierSelectionne.getAbsolutePath();
            champFichier.setText(cheminFichierActuel);
            bibliotheque = new GestionnaireBibliotheque(cheminFichierActuel);
            chargerLivres();
        }
    }

    private void creerNouveauFichier() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Créer un nouveau fichier");
        fileChooser.setFileFilter(new FileNameExtensionFilter("Fichiers texte", "txt"));
        
        int result = fileChooser.showSaveDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File fichierSelectionne = fileChooser.getSelectedFile();
            String chemin = fichierSelectionne.getAbsolutePath();
            if (!chemin.endsWith(".txt")) {
                chemin += ".txt";
            }
            cheminFichierActuel = chemin;
            champFichier.setText(cheminFichierActuel);
            bibliotheque = new GestionnaireBibliotheque(cheminFichierActuel);
            tableModel.setRowCount(0);
            mettreAJourStatut();
        }
    }

    private void chargerLivres() {
        try {
            bibliotheque.chargerDepuisFichier();
            afficherTousLesLivres();
            mettreAJourStatut();
        } catch (FichierIntrouvableException e) {
            tableModel.setRowCount(0);
            mettreAJourStatut();
            JOptionPane.showMessageDialog(this, 
                "Le fichier n'existe pas encore.\nIl sera créé lors de la sauvegarde.",
                "Information", JOptionPane.INFORMATION_MESSAGE);
        } catch (ErreurLectureException e) {
            JOptionPane.showMessageDialog(this, 
                "Erreur de lecture: " + e.getMessage(),
                "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void afficherTousLesLivres() {
        tableModel.setRowCount(0);
        List<Livre> livres = bibliotheque.getTousLesLivres();
        for (Livre livre : livres) {
            tableModel.addRow(new Object[]{
                livre.getTitre(),
                livre.getAuteur(),
                livre.getAnnee(),
                livre.getIsbn()
            });
        }
        mettreAJourStatut();
    }

    private void ajouterLivre() {
        String titre = champTitre.getText().trim();
        String auteur = champAuteur.getText().trim();
        String anneeStr = champAnnee.getText().trim();
        String isbn = champISBN.getText().trim();

        if (titre.isEmpty() || auteur.isEmpty() || anneeStr.isEmpty() || isbn.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Veuillez remplir tous les champs !",
                "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            int annee = Integer.parseInt(anneeStr);
            Livre livre = new Livre(titre, auteur, annee, isbn);
            bibliotheque.ajouterLivre(livre);
            
            // Vider les champs
            champTitre.setText("");
            champAuteur.setText("");
            champAnnee.setText("");
            champISBN.setText("");
            
            afficherTousLesLivres();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, 
                "L'année doit être un nombre entier !",
                "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void ajouterLivreAuTableau() {
        ajouterLivre();
    }

    private void supprimerLivre() {
        int ligneSelectionnee = tableLivres.getSelectedRow();
        if (ligneSelectionnee == -1) {
            JOptionPane.showMessageDialog(this, 
                "Veuillez sélectionner un livre à supprimer !",
                "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String isbn = (String) tableModel.getValueAt(ligneSelectionnee, 3);
        int reponse = JOptionPane.showConfirmDialog(this,
            "Êtes-vous sûr de vouloir supprimer ce livre ?",
            "Confirmation", JOptionPane.YES_NO_OPTION);

        if (reponse == JOptionPane.YES_OPTION) {
            bibliotheque.supprimerLivre(isbn);
            afficherTousLesLivres();
        }
    }

    private void sauvegarderLivres() {
        try {
            bibliotheque.sauvegarderDansFichier();
            mettreAJourStatut();
            JOptionPane.showMessageDialog(this, 
                "Sauvegarde réussie !\n" + bibliotheque.getNombreLivres() + " livre(s) sauvegardé(s).",
                "Succès", JOptionPane.INFORMATION_MESSAGE);
        } catch (ErreurEcritureException e) {
            JOptionPane.showMessageDialog(this, 
                "Erreur d'écriture: " + e.getMessage(),
                "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void actualiser() {
        chargerLivres();
    }

    private void mettreAJourStatut() {
        int nombreLivres = bibliotheque.getNombreLivres();
        labelStatut.setText("Fichier ouvert: " + nombreLivres + " livre(s) chargé(s)");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            new BibliothequeGUI().setVisible(true);
        });
    }
}
