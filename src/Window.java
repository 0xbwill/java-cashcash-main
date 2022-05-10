import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import com.itextpdf.text.DocumentException;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class Window implements ActionListener {

    // Déclaration des éléments
    JFrame frame;
    JPanel panel, contratPanel;
    JTextField textbox, textContrat, t1, t2, t3, t4, t5, t7, t8, t9, t11, pdfInput;
    JLabel label, labelContrat, titreCreationContrat, l1, l2, l3, l4, l5, l7, l8, l9, sectionContratTitle,
            sectionPDFTitle,
            pdfInputLabel;
    JButton button, buttonXml, buttonContrat, submitContrat, buttonPDF;
    static JTable table, tableContrat;

    // Connexion BDD
    String driverName = "com.mysql.jdbc.Driver";
    String url = "jdbc:mysql://localhost:3306/laravel-ap";
    String userName = "laravel";
    String password = "r-UgAizH[T.y@qKS";

    // Création tableau Client
    String[] columnNames = { "numeroClient", "nom", "prenom", "email", "tel", "adresse", "siren", "numeroAgence",
            "codeApe", "raisonSociale", "dureeDeplacement", "distanceKm" };

    // Création tableau Contrat
    String[] columnContrat = { "numeroDeContrat", "dateDeSignature", "dateEcheance", "numeroClient", "refTypeContrat" };

    // Méthode chargé de la création de la fenêtre | createUI()
    public void createUI() {
        frame = new JFrame("CashCash App");
        frame.setLayout(null);

        // Création du champ de texte pour afficher un client avec son numéroClient
        textbox = new JTextField();
        label = new JLabel("Entrez un numéro client :");
        label.setBounds(30, 30, 300, 20);
        textbox.setBounds(220, 30, 170, 20);

        sectionContratTitle = new JLabel("Section contrat de maintenance");
        sectionContratTitle.setBounds(30, 220, 300, 20);
        sectionContratTitle.setFont(new Font("Arial", Font.BOLD, 18));
        frame.add(sectionContratTitle);

        sectionPDFTitle = new JLabel("Section génération PDF");
        sectionPDFTitle.setBounds(30, 460, 300, 20);
        sectionPDFTitle.setFont(new Font("Arial", Font.BOLD, 18));
        frame.add(sectionPDFTitle);

        // Création du champ de texte pour afficher un matériel avec son numéroSerie
        textContrat = new JTextField();
        labelContrat = new JLabel("Entrez un numéro de contrat : ");
        labelContrat.setBounds(30, 260, 250, 20);
        textContrat.setBounds(220, 260, 170, 20);

        // Création Button
        button = new JButton("Rechercher");
        button.setBounds(220, 60, 170, 20);
        button.addActionListener(this);

        // Création buttonContrat
        buttonContrat = new JButton("Rechercher");
        buttonContrat.setBounds(220, 290, 170, 20);
        buttonContrat.addActionListener(this);

        JLabel titreCreationContrat = new JLabel("Création d'un contrat");
        titreCreationContrat.setBounds(770, 240, 200, 14);
        titreCreationContrat.setFont(new Font("Arial", Font.BOLD, 15));
        frame.add(titreCreationContrat);

        // Colonne gauche
        JLabel l1 = new JLabel("Numero de contrat");
        l1.setBounds(550, 290, 130, 14);
        frame.add(l1);

        JLabel l4 = new JLabel("Numero client");
        l4.setBounds(550, 330, 130, 14);
        frame.add(l4);

        JLabel l5 = new JLabel("Référence contrat");
        l5.setBounds(550, 370, 130, 14);
        frame.add(l5);

        // Colonne droite
        JLabel l7 = new JLabel("Numéro de série");
        l7.setBounds(900, 290, 130, 14);
        frame.add(l7);

        JLabel l8 = new JLabel("Prix de vente");
        l8.setBounds(900, 330, 130, 14);
        frame.add(l8);

        JLabel l9 = new JLabel("Emplacement");
        l9.setBounds(900, 370, 130, 14);
        frame.add(l9);

        // Input colone gauche
        t1 = new JTextField();
        t1.setBounds(700, 290, 96, 20);
        frame.add(t1);
        t1.setColumns(10);

        t4 = new JTextField();
        t4.setBounds(700, 330, 96, 20);
        frame.add(t4);
        t4.setColumns(10);

        t5 = new JTextField();
        t5.setBounds(700, 370, 96, 20);
        frame.add(t5);
        t5.setColumns(10);

        // Input colone droite
        t7 = new JTextField();
        t7.setBounds(1050, 290, 96, 20);
        frame.add(t7);
        t7.setColumns(10);

        t8 = new JTextField();
        t8.setBounds(1050, 330, 96, 20);
        frame.add(t8);
        t8.setColumns(10);

        t9 = new JTextField();
        t9.setBounds(1050, 370, 96, 20);
        frame.add(t9);
        t9.setColumns(10);

        submitContrat = new JButton("Générer contrat");
        submitContrat.setBounds(740, 430, 200, 20);
        submitContrat.addActionListener(this);

        pdfInput = new JTextField();
        pdfInput.setBounds(220, 500, 170, 20);

        pdfInputLabel = new JLabel("Entrez un numéro de contrat : ");
        pdfInputLabel.setBounds(30, 500, 250, 20);

        // Création buttonPDF
        buttonPDF = new JButton("Générer PDF");
        buttonPDF.setBounds(220, 530, 170, 20);
        buttonPDF.addActionListener(this);
        frame.add(buttonPDF);

        frame.add(pdfInput);
        frame.add(pdfInputLabel);
        frame.add(submitContrat);
        frame.add(buttonContrat);
        frame.add(textContrat);
        frame.add(labelContrat);
        frame.add(button);
        frame.add(textbox);
        frame.add(label);
        frame.setVisible(true);
        frame.setSize(1200, 640);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setUndecorated(true);

    }
    // Fin méthode createUI()

    // Début EventListener
    public void actionPerformed(ActionEvent e) {

        // Si le bouton 'button' est pressé
        if (e.getSource() == button) {
            System.out.println("Affichage des données en cours...");
            showTableData();
        }
        // Si le bouton 'buttonXml' est pressé
        if (e.getSource() == buttonXml) {
            System.out.println("Génération du fichier XML en cours...");
            createXML();
        }
        if (e.getSource() == buttonContrat) {
            System.out.println("Affichage des contrats en cours");
            afficherContrat();
        }
        if (e.getSource() == submitContrat) {
            System.out.println("Création du contrat en cours..");
            insertionContrat();
        }
        if (e.getSource() == buttonPDF) {
            System.out.println("Génération du PDF en cours..");
            String numeroContratPDF = pdfInput.getText();
            try {
                PDF.generatePDF(numeroContratPDF);
            } catch (FileNotFoundException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            } catch (DocumentException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }
    }
    // Fin EventListener

    // Méthode chargé de l'affichage des données d'un client | showTableData()
    public String showTableData() {
        // Création de la page résultat de la recherche
        JPanel panel = new JPanel();
        frame.add(panel);

        // Création button XML
        buttonXml = new JButton("Génerer un fichier XML");
        buttonXml.setBounds(30, 100, 200, 20);
        buttonXml.addActionListener(this);

        // TableModel tm = new TableModel();
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(columnNames);

        // création du tableau pour les résultats
        table = new JTable();
        table.setModel(model);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        table.setFillsViewportHeight(true);

        // Permet de récuper la valeur de l'input de recherche
        String textvalue = textbox.getText();
        // Initialisation des valeurs
        String numeroClient = "";
        String nom = "";
        String prenom = "";
        String email = "";
        String tel = "";
        String adresse = "";
        String siren = "";
        Integer numeroAgence;
        String codeApe = "";
        String raisonSociale = "";
        Time dureeDeplacement;
        Integer distanceKm;

        // Requête SQL
        try {
            Class.forName(driverName);
            Connection con = DriverManager.getConnection(url, userName, password);
            String sql = "select * from clients where numeroClient = " + textvalue;
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            int i = 0;
            if (rs.next()) {
                // Récupérer les données de la base données
                numeroClient = rs.getString("numeroClient");
                nom = rs.getString("nom");
                prenom = rs.getString("prenom");
                email = rs.getString("email");
                tel = rs.getString("tel");
                adresse = rs.getString("adresse");
                siren = rs.getString("siren");
                numeroAgence = rs.getInt("numeroAgence");
                codeApe = rs.getString("codeApe");
                raisonSociale = rs.getString("raisonSociale");
                dureeDeplacement = rs.getTime("dureeDeplacement");
                distanceKm = rs.getInt("distanceKm");

                model.addRow(new Object[] { numeroClient, nom, prenom, email, tel, adresse, siren, numeroAgence,
                        codeApe, raisonSociale, dureeDeplacement, distanceKm });
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

        // Appel des éléments
        panel.add(buttonXml);
        panel.add(table);
        panel.setBounds(0, 100, 900, 70);
        frame.setVisible(true);

        return textvalue;
    }
    // Fin méthode showTableData()

    // Méthode chargé de la création du fichier XML | createXML()
    public void createXML() {
        String nom = "";
        String prenom = "";
        String email = "";
        String tel = "";
        String adresse = "";
        String siren = "";
        Integer numeroAgence;
        String codeApe = "";
        String raisonSociale = "";
        Time dureeDeplacement;
        Integer distanceKm;

        int idForXml = Integer.parseInt(this.showTableData());

        try {

            Class.forName(driverName);
            Connection con = DriverManager.getConnection(url, userName, password);
            String sql = "select * from clients where numeroClient = " + idForXml;
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                // Récupérer les données de la base données
                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder docBuilder = dbFactory.newDocumentBuilder();

                // élément de racine
                Document doc = docBuilder.newDocument();
                Element racine = doc.createElement("repertoire");
                doc.appendChild(racine);

                // l'élément contact
                Element contact = doc.createElement("contact");
                racine.appendChild(contact);

                // attributs de l'élément contact
                Attr attr = doc.createAttribute("id");
                attr.setValue(String.valueOf(idForXml));
                contact.setAttributeNode(attr);

                // le nom
                Element nomUser = doc.createElement("nom");
                nomUser.setTextContent(rs.getString("nom"));
                contact.appendChild(nomUser);

                // le prénom
                Element prenomUser = doc.createElement("prenom");
                prenomUser.setTextContent(rs.getString("prenom"));
                contact.appendChild(prenomUser);

                // email
                Element emailUser = doc.createElement("email");
                emailUser.setTextContent(rs.getString("email"));
                contact.appendChild(emailUser);

                // tel
                Element telUser = doc.createElement("tel");
                telUser.setTextContent(rs.getString("tel"));
                contact.appendChild(telUser);

                // adresse
                Element adresseUser = doc.createElement("adresse");
                adresseUser.setTextContent(rs.getString("adresse"));
                contact.appendChild(adresseUser);

                // siren
                Element sirenUser = doc.createElement("siren");
                sirenUser.setTextContent(rs.getString("siren"));
                contact.appendChild(sirenUser);

                // numeroAgence
                Element numeroAgenceUser = doc.createElement("numeroAgence");
                numeroAgenceUser.setTextContent(rs.getString("numeroAgence"));
                contact.appendChild(numeroAgenceUser);

                // codeApe
                Element codeApeUser = doc.createElement("codeApe");
                codeApeUser.setTextContent(rs.getString("codeApe"));
                contact.appendChild(codeApeUser);

                // raisonSociale
                Element raisonSocialeUser = doc.createElement("raisonSociale");
                raisonSocialeUser.setTextContent(rs.getString("raisonSociale"));
                contact.appendChild(raisonSocialeUser);

                // dureeDeplacement
                Element dureeDeplacementUser = doc.createElement("dureeDeplacement");
                dureeDeplacementUser.setTextContent(rs.getString("dureeDeplacement"));
                contact.appendChild(dureeDeplacementUser);

                // distanceKm
                Element distanceKmUser = doc.createElement("distanceKm");
                distanceKmUser.setTextContent(rs.getString("distanceKm"));
                contact.appendChild(distanceKmUser);

                // write the content into xml file
                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                DOMSource source = new DOMSource(doc);
                StreamResult resultat = new StreamResult(new File("monFichier.xml"));

                transformer.transform(source, resultat);

                System.out.println("Fichier sauvegardé avec succès!");
            }

        } catch (
                ParserConfigurationException | TransformerException pce) {
            pce.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    // Fin méthode createXML()

    // Méthode pour afficher un nouveau contrat | afficherContrat()
    public String afficherContrat() {
        JPanel contratPanel = new JPanel();
        frame.add(contratPanel);

        // TableModel tm = new TableModel();
        DefaultTableModel modelContrat = new DefaultTableModel();
        modelContrat.setColumnIdentifiers(columnContrat);

        // création du tableau pour les résultats
        tableContrat = new JTable();
        tableContrat.setModel(modelContrat);
        tableContrat.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        tableContrat.setFillsViewportHeight(true);

        String textvalueContrat = textContrat.getText();

        try {
            Class.forName(driverName);
            Connection con = DriverManager.getConnection(url, userName, password);
            String sql = "select * from contrat_de_maintenance where numeroDeContrat = " + textvalueContrat;
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            Integer numeroDeContrat;
            Date dateDeSignature;
            Date dateEcheance;
            Integer numeroClient;
            Integer refTypeContrat;

            int i = 0;
            if (rs.next()) {
                // Récupérer les données de la base données
                numeroDeContrat = rs.getInt("numeroDeContrat");
                dateDeSignature = rs.getDate("dateDeSignature");
                dateEcheance = rs.getDate("dateEcheance");
                numeroClient = rs.getInt("numeroClient");
                refTypeContrat = rs.getInt("refTypeContrat");

                modelContrat.addRow(
                        new Object[] { numeroDeContrat, dateDeSignature, dateEcheance, numeroClient, refTypeContrat });
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
        // Appel des éléments
        contratPanel.add(tableContrat);
        contratPanel.setBounds(0, 320, 500, 40);
        frame.setVisible(true);

        return textvalueContrat;

    }
    // Fin méthode afficherContrat()

    // Méthode pour insérer un nouveau contrat | insertionContrat()
    public String insertionContrat() {

        String numeroDeContrat = t1.getText();
        String numeroClient = t4.getText();
        String refTypeContrat = t5.getText();
        String numeroSerie = t7.getText();
        String prixVente = t8.getText();
        String emplacement = t9.getText();

        // Formatage de la date du jour pour insérer dans la BDD
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        LocalDate dateDeSignature = LocalDate.now();
        LocalDate dateEcheance = LocalDate.now().plusYears(1);
        LocalDate dateInstallation = LocalDate.now().plusDays(3);

        try {
            Class.forName(driverName);
            Connection con = DriverManager.getConnection(url, userName, password);
            String sql = "INSERT INTO contrat_de_maintenance (numeroDeContrat,dateDeSignature,dateEcheance,numeroClient,refTypeContrat) VALUES ("
                    + numeroDeContrat + ",'" + dateDeSignature.format(format).toString() + "','"
                    + dateEcheance.format(format).toString() + "'," + numeroClient + "," + refTypeContrat + ");";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.executeUpdate(sql);

            String insertMateriel = "INSERT INTO materiel (numeroDeSerie,dateDeVente,dateInstallation,prixVente,emplacement,refInterne,numeroClient,numeroContrat) VALUES ("
                    + numeroSerie + ",'" + dateDeSignature.format(format).toString() + "','"
                    + dateInstallation.format(format).toString() + "'," + prixVente + ",'" + emplacement + "',"
                    + refTypeContrat + "," + numeroClient
                    + "," + numeroDeContrat + ");";

            PreparedStatement materielPs = con.prepareStatement(insertMateriel);
            materielPs.executeUpdate(insertMateriel);

            System.out.println("Création du contrat réussie !");
        } catch (Exception e) {
            System.out.println(e);
        }
        return driverName;
    }
    // Fin méthode insertionContrat()

}