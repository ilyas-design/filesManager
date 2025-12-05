package filesmanager3;

//Source code is decompiled from a .class file using FernFlower decompiler (from Intellij IDEA).
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GestionnaireRessources {
private String nomFichier;

public GestionnaireRessources(String var1) {
   this.nomFichier = var1;
}

public List<Livre> chargerLivres() throws FichierIntrouvableException, ErreurLectureException {
   ArrayList var1 = new ArrayList();
   File var2 = new File(this.nomFichier);
   if (!var2.exists()) {
      throw new FichierIntrouvableException("Le fichier " + this.nomFichier + " n'existe pas");
   } else {
      try {
         BufferedReader var3 = new BufferedReader(new FileReader(var2));

         try {
            int var5 = 0;

            String var4;
            while((var4 = var3.readLine()) != null) {
               ++var5;
               var4 = var4.trim();
               if (!var4.isEmpty() && !var4.startsWith("#")) {
            	   try {
            		    Livre var6 = Livre.fromFormatTexte(var4);
            		    var1.add(var6);
            		} catch (FormatLigneException var8) {
            		    System.err.println("Erreur ligne " + var5 + ": " + var8.getMessage());
            		}

               }
            }
         } catch (Throwable var9) {
            try {
               var3.close();
            } catch (Throwable var7) {
               var9.addSuppressed(var7);
            }

            throw var9;
         }

         var3.close();
         return var1;
      } catch (FileNotFoundException var10) {
         throw new FichierIntrouvableException("Le fichier " + this.nomFichier + " n'a pas pu Ãªtre trouvÃ©", var10);
      } catch (IOException var11) {
         throw new ErreurLectureException("Erreur lors de la lecture du fichier " + this.nomFichier, var11);
      }
   }
}

public void sauvegarderLivres(List<Livre> var1) throws ErreurEcritureException {
   try {
      BufferedWriter var2 = new BufferedWriter(new FileWriter(this.nomFichier));

      try {
         var2.write("# BibliothÃ¨que - Format: Titre|Auteur|AnnÃ©e|ISBN");
         var2.newLine();
         var2.write("# Les lignes commenÃ§ant par # sont des commentaires");
         var2.newLine();
         var2.newLine();
         Iterator var3 = var1.iterator();

         while(var3.hasNext()) {
            Livre var4 = (Livre)var3.next();
            var2.write(var4.toFormatTexte());
            var2.newLine();
         }
      } catch (Throwable var6) {
         try {
            var2.close();
         } catch (Throwable var5) {
            var6.addSuppressed(var5);
         }

         throw var6;
      }

      var2.close();
   } catch (IOException var7) {
      throw new ErreurEcritureException("Erreur lors de l'Ã©criture dans le fichier " + this.nomFichier, var7);
   }
}

public String getNomFichier() {
   return this.nomFichier;
}

public void setNomFichier(String var1) {
   this.nomFichier = var1;
}
}
