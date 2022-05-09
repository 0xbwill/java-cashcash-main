import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Time;

import javax.swing.JOptionPane;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

public class PDF {

    // Connexion BDD
    static String driverName = "com.mysql.jdbc.Driver";
    static String url = "jdbc:mysql://localhost:3306/laravel-ap";
    static String userName = "laravel";
    static String password = "r-UgAizH[T.y@qKS";
    // Fin connexion BDD

    public static void generatePDF(String numeroContratPDF) throws FileNotFoundException, DocumentException {

        System.out.println(numeroContratPDF);

        // Informations du client
        String numeroClient = "";
        String nom = "";
        String prenom = "";
        String email = "";
        String tel = "";
        String adresse = "";
        String siren = "";
        String codeApe = "";
        String numeroAgence = "";

        // Informations du contrat de maintenance
        String dateEcheance = "";
        String dateDeSignature = "";
        String refTypeContrat = "";

        // Informations du matériel vendu et couvert par le contrat
        String numeroSerie = "";
        String dateVente = "";
        String dateInstallation = "";
        String prixVente = "";
        String emplacement = "";

        String FILE_NAME = "./myPDF.pdf";
        Document document = new Document();

        try {

            // Connexion BDD
            Class.forName(driverName);
            Connection con = DriverManager.getConnection(url, userName, password);
            String sql = "SELECT * FROM contrat_de_maintenance INNER JOIN clients ON contrat_de_maintenance.numeroClient = clients.numeroClient INNER JOIN materiel ON contrat_de_maintenance.numeroDeContrat = materiel.numeroContrat WHERE numeroDeContrat =" + numeroContratPDF;
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            int i = 0;
            if (rs.next()) {
                // Assignation informations du client
                numeroClient = rs.getString("numeroClient");
                nom = rs.getString("nom");
                prenom = rs.getString("prenom");
                email = rs.getString("email");
                tel = rs.getString("tel");
                adresse = rs.getString("adresse");
                siren = rs.getString("siren");
                numeroAgence = rs.getString("numeroAgence");
                codeApe = rs.getString("codeApe");

                // Assignation informations du contrat de maintenance
                dateDeSignature = rs.getString("dateDeSignature");
                dateEcheance = rs.getString("dateEcheance");
                refTypeContrat = rs.getString("refTypeContrat");

                // Assignation informations du matériel vendu
                numeroSerie = rs.getString("numeroDeSerie");
                dateVente = rs.getString("dateDeVente");
                dateInstallation = rs.getString("dateInstallation");
                prixVente = rs.getString("prixVente");
                emplacement = rs.getString("emplacement");

                i++;
            }
            if (i < 1) {
                JOptionPane.showMessageDialog(null, "Aucun résultat trouvé", "Erreur",
                        JOptionPane.ERROR_MESSAGE);
            }
            if (i == 1) {
                System.out.println(i + " résultat trouvé");
            } else {
                System.out.println(i + " résultats trouvés");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }

        PdfWriter.getInstance(document, new FileOutputStream(new File(FILE_NAME)));
        document.open();
        Paragraph p = new Paragraph();
        p.add("Courrier automatique de relance");
        p.setAlignment(Element.ALIGN_CENTER);
        document.add(p);

        Font bold = new Font();
        bold.setStyle(Font.BOLD);
        bold.setSize(15);

        Paragraph cashcash = new Paragraph();
        cashcash.add("Entreprise CashCash");
        cashcash.setAlignment(Element.ALIGN_CENTER);
        document.add(cashcash);

        document.add(new Paragraph("\n"));
        document.add(new Paragraph("\n"));

        Paragraph p14 = new Paragraph();
        p14.add("Madame, Monsieur " + nom + ",");
        document.add(p14);

        Paragraph p15 = new Paragraph();
        p15.add("Nous vous informons que votre contrat de maintenance numéro " + numeroContratPDF
                + " arrive très prochainement à expiration.");
        document.add(p15);

        document.add(new Paragraph("\n"));
        document.add(new Paragraph("\n"));

        document.add(new Paragraph("Informations client", bold));

        Paragraph p2 = new Paragraph();
        p2.add("Numéro Client : " + numeroClient);
        document.add(p2);

        Paragraph p3 = new Paragraph();
        p3.add("Nom & prénom : " + nom + " " + prenom);
        document.add(p3);

        Paragraph p4 = new Paragraph();
        p4.add("Email : " + email);
        document.add(p4);

        Paragraph p5 = new Paragraph();
        p5.add("Téléphone : " + tel);
        document.add(p5);

        Paragraph p6 = new Paragraph();
        p6.add("Adresse : " + adresse);
        document.add(p6);

        Paragraph p7 = new Paragraph();
        p7.add("SIREN : " + siren);
        document.add(p7);

        Paragraph p8 = new Paragraph();
        p8.add("Code APE : " + codeApe);
        document.add(p8);

        Paragraph p9 = new Paragraph();
        p9.add("Numéro agence : " + numeroAgence);
        document.add(p9);

        document.add(new Paragraph("\n"));

        document.add(new Paragraph("Informations du contrat", bold));

        Paragraph p10 = new Paragraph();
        p10.add("Numéro de contrat : " + numeroContratPDF);
        document.add(p10);

        Paragraph p11 = new Paragraph();
        p11.add("Date de signature du contrat : " + dateDeSignature);
        document.add(p11);

        Paragraph p12 = new Paragraph();
        p12.add("Date d'échéance du contrat : " + dateEcheance);
        document.add(p12);

        Paragraph p13 = new Paragraph();
        p13.add("Référence du contrat : " + refTypeContrat);
        document.add(p13);

        document.add(new Paragraph("\n"));

        document.add(new Paragraph("Informations du matériel", bold));

        Paragraph p20 = new Paragraph();
        p20.add("Numéro de série : " + numeroSerie);
        document.add(p20);

        Paragraph p21 = new Paragraph();
        p21.add("Date de vente : " + dateVente);
        document.add(p21);

        Paragraph p22 = new Paragraph();
        p22.add("Date installation : " + dateInstallation);
        document.add(p22);

        Paragraph p23 = new Paragraph();
        p23.add("Prix de vente : " + prixVente + "€");
        document.add(p23);

        Paragraph p24 = new Paragraph();
        p24.add("Emplacement : " + emplacement);
        document.add(p24);

        document.close();
        System.out.println("PDF généré avec succès !");

    }
}
