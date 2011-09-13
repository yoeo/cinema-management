package org.cinema.utils;

import javax.swing.JFileChooser;
import java.io.FileOutputStream;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;


public class PdfGenerator {

    private static String fichier = "";
    private static Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18,
            Font.BOLD);
    private static Font normalFont = new Font(Font.FontFamily.TIMES_ROMAN, 12,
            Font.NORMAL);
    private static Font subFont = new Font(Font.FontFamily.TIMES_ROMAN, 16,
            Font.BOLD);
    private static Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12,
            Font.UNDERLINE, BaseColor.LIGHT_GRAY);
    private static String contenu = "";
    private static String titre = "";

    public PdfGenerator(String text, String titr) {
        titre = titr;
        contenu = text;
        JFileChooser dialogue = new JFileChooser();
        dialogue.setDialogTitle("Choix emplacement ");
        if (dialogue.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            fichier = dialogue.getSelectedFile().getPath();
            if (fichier.contains(".")) {
                String extention = fichier.substring(fichier.indexOf(".") + 1);
                System.out.println(extention);
                if (!extention.equals("pdf")) {
                    fichier = fichier.subSequence(0, fichier.indexOf("."))
                            + ".pdf";

                }
            } else {
                fichier = fichier + ".pdf";
            }
        }
        if (fichier.equals("")) {
            fichier = "./imprimer.pdf";
        }
        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(fichier));
            document.open();
            addMetaData(document);

            addTitlePage(document);
            document.close();
        } catch (Exception e) {
            Log.log("Erreur de creattion du fichier pdf");
            e.printStackTrace();
        }
       // System.exit(0);
    }

    private static void addMetaData(Document document) {
        document.addTitle("Gestion du cinema");
        document.addAuthor("S2L-CRT");
        document.addCreator("Groupe 2");
    }

    private static void addTitlePage(Document document)
            throws DocumentException {
        Paragraph Paragraphe = new Paragraph();
        // We add one empty line
        addEmptyLine(Paragraphe, 1);
        // Lets write a big header
        Paragraphe.add(new Paragraph("                                    CRT-S2L", catFont));
        addEmptyLine(Paragraphe, 6);
        Paragraphe.add(new Paragraph("                                        " + titre, subFont));
        addEmptyLine(Paragraphe, 9);
        Paragraphe.add(new Paragraph(contenu, normalFont));
        addEmptyLine(Paragraphe, 22);
        Paragraphe.add(new Paragraph("Courriel : s2l.crt@gmail.com", smallBold));
        document.add(Paragraphe);

    }

    private static void addEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }
}