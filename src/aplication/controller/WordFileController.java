package aplication.controller;

import aplication.module.VO.CustomerVO;
import aplication.module.VO.HistoricalVO;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import java.io.*;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class WordFileController {

    private final CustomerVO customer;
    private String historicalText;

    public WordFileController(CustomerVO customer) {
        this.customer = customer;
    }

    public WordFileController(CustomerVO customer, String historicalText) {
        this.customer = customer;
        this.historicalText = historicalText;
    }

    public void exportWord(){
        try (XWPFDocument doc = new XWPFDocument()) {

            // create a paragraph
            XWPFParagraph p1 = doc.createParagraph();
            p1.setAlignment(ParagraphAlignment.CENTER);

            // set font
            XWPFRun r1 = p1.createRun();
            r1.setBold(true);
            r1.setFontSize(20);
            r1.setFontFamily("Nunito");
            r1.setText("Historico de paciente " + this.customer.getName() + " " + this.customer.getLastName());

            // create a paragraph
            XWPFParagraph p2 = doc.createParagraph();
            p2.setAlignment(ParagraphAlignment.LEFT);

            // set font
            XWPFRun r2 = p2.createRun();
            r2.setFontSize(16);
            r2.setFontFamily("Nunito");
            r2.setText(this.historicalText);

            // save it to .docx file
            FeedbackController feedback = new FeedbackController();
            File file = feedback.windowSaveFile(false);
            String message;
            if(file != null) {
                try (FileOutputStream out = new FileOutputStream(file.getAbsolutePath())) {
                    doc.write(out);
                    doc.close();
                    message = "Has exportado el archivo con exito!";
                    feedback.alertInformation(message);
                }catch (Exception ignored){
                    message = "Ha pasado un error mientras el proceso";
                    feedback.alertInformation(message);
                }
            }else{
                message = "Ha pasado un error mientras el proceso";
                feedback.alertInformation(message);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void importWord ()  {
        FeedbackController feedback = new FeedbackController();
        File file = feedback.windowOpenFile(false);
        String message;
        if (file != null) {
            try (FileInputStream fileInput = new FileInputStream(file.getAbsolutePath())) {
                XWPFDocument doc = new XWPFDocument(fileInput);
                List<XWPFParagraph> paragraphs = doc.getParagraphs();
                doc.close();
                HistoricalController historicalCL = new HistoricalController();
                HistoricalVO historical = historicalCL.controlGettingHistorical(customer.getId());

                if(historical != null){
                    String textBefore = historical.getHistorical();
                    AtomicReference<String> textHistorical = new AtomicReference<>("");
                    paragraphs.forEach(paragraph -> textHistorical.updateAndGet(v -> v + paragraph.getText() + "\n" ));

                    historicalCL = new HistoricalController();
                    historical.setHistorical(textBefore + "\n" + "(**Texto importado**)\n" + textHistorical);
                    historicalCL.controlUpdatingHistorical(historical);
                }else {
                    AtomicReference<String> textHistorical = new AtomicReference<>("");
                    paragraphs.forEach(paragraph -> textHistorical.updateAndGet(v -> v + paragraph.getText() + "\n" ));

                    historicalCL = new HistoricalController();
                    historicalText = "(**Texto importado**)\n" + textHistorical;
                    historicalCL.controlAddingHistorical(this.customer.getId(),this.customer.getName(), this.customer.getLastName(), historicalText );
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            message = "Ha pasado un error mientras el proceso";
            feedback.alertInformation(message);
        }
    }
}
