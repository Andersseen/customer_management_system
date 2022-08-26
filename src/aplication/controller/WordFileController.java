package aplication.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import java.io.*;
import java.util.List;

public class WordFileController {

    private File file;
    private String message;

    @FXML
    private Button btnExport;
    @FXML
    private Button btnImport;
    public WordFileController() {}
    
    public WordFileController(Button btnExport, Button btnImport) {
        this.btnExport = btnExport;
        this.btnImport = btnImport;
    }
    
    public void printWord(){
        try (XWPFDocument doc = new XWPFDocument()) {

            // create a paragraph
            XWPFParagraph p1 = doc.createParagraph();
            p1.setAlignment(ParagraphAlignment.LEFT);

            // set font
            XWPFRun r1 = p1.createRun();
            r1.setBold(true);
            r1.setItalic(true);
            r1.setFontSize(22);
            r1.setFontFamily("Nunito");
            r1.setText("this is export Word.");

            // save it to .docx file
            FeedbackController feedback = new FeedbackController();
            file = feedback.windowSaveFile(false);
            if(file != null) {
                try (FileOutputStream out = new FileOutputStream(file.getAbsolutePath())) {
                    doc.write(out);
                    doc.close();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void importWord ()  {
        FeedbackController feedback = new FeedbackController();
        File file = feedback.windowOpenFile(false);
        if (file != null) {
            try (FileInputStream fileInput = new FileInputStream(file.getAbsolutePath())) {
                XWPFDocument doc = new XWPFDocument(fileInput);
                List<XWPFParagraph> paragraphs = doc.getParagraphs();
                doc.close();
                paragraphs.forEach(paragraph ->{
                    System.out.println(paragraph.getText());
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



}
