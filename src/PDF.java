import java.io.File;
import java.io.FileOutputStream;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

public class PDF {
    public static void generatePDF() {

    String FILE_NAME = "./myPDF.pdf";
    Document document = new Document();try
    {
        PdfWriter.getInstance(document, new FileOutputStream(new File(FILE_NAME)));
        document.open();
        Paragraph p = new Paragraph();
        p.add("Courrier automatique de relance");
        p.setAlignment(Element.ALIGN_CENTER);
        document.add(p);
        Paragraph p2 = new Paragraph();
        p2.add("Matériel : n°1515615"); // no alignment
        document.add(p2);
        Font f = new Font();
        f.setStyle(Font.BOLD);
        f.setSize(8);
        document.add(new Paragraph("Client : RACHADZER", f));
        // document.add(Image.getInstance("E:\\java_pdf\\chillyfacts.png"));
        document.close();
        System.out.println("PDF généré avec succès !");
    }catch(
    Exception e)
    {
        e.printStackTrace();
    }
}
}
