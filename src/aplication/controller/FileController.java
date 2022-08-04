package aplication.controller;


import aplication.module.VO.CustomerVO;
import aplication.view.dashboard.DashboardController;
import aplication.view.loader.LoaderController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.xmlbeans.SystemProperties;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileController {
    private File file;
    private XSSFSheet sheet;
    private String message;
    final private String[] excelColumns = {"ID",  "Nombre", "Apellido", "Sexo", "Cumpleaños", "Email", "Telefono", "Nota", "Fecha"};

    static Font setFontToFile(XSSFWorkbook workbook){
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setFontName("Nunito");
        headerFont.setFontHeightInPoints((short) 16);
        headerFont.setColor(IndexedColors.VIOLET.getIndex());
        return headerFont;
    }

    private XSSFSheet printSheetWithCustomers(XSSFWorkbook wb, ArrayList<CustomerVO> list) {
        XSSFSheet sheet = wb.createSheet();
        Row headerRow = sheet.createRow(0);
        Font headerFont = this.setFontToFile(wb);

        // Create a CellStyle with the font
        CellStyle headerCellStyle = wb.createCellStyle();
        headerCellStyle.setFont(headerFont);

        for(int i =0; i < this.excelColumns.length; i++){
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(this.excelColumns[i]);
            cell.setCellStyle(headerCellStyle);
        }

        AtomicInteger index = new AtomicInteger(1);

        list.forEach(client -> {
            String birthday = "";
            String date = "";
            //Converting the Date object to String format
            if (client.getDate() != null) {
                Date dateObj = client.getDate();
                date = dateObj.toString();
            }
            if (client.getBirthday() != null) {
                Date birthdayObj = client.getBirthday();
                birthday = birthdayObj.toString();
            }
            Row row = sheet.createRow(index.get());
            row.createCell(0).setCellValue(client.getId());
            row.createCell(1).setCellValue(client.getName());
            row.createCell(2).setCellValue(client.getLastName());
            row.createCell(3).setCellValue(client.getSex());
            row.createCell(4).setCellValue(birthday);
            row.createCell(5).setCellValue(client.getEmail());
            row.createCell(6).setCellValue(client.getPhone());
            row.createCell(7).setCellValue(client.getNote());
            row.createCell(8).setCellValue(date);

            index.getAndIncrement();
        });
        // Resize all columns to fit the content size
        for(int i = 0; i < excelColumns.length; i++) {
            sheet.autoSizeColumn(i);
        }
        return sheet;
    }

    private void makeCustomersFromSheet(XSSFWorkbook wb) {
        XSSFSheet sheet = wb.getSheetAt(0);
        Row row;
        for(int i = 5; i <= sheet.getLastRowNum(); i++){
            row = sheet.getRow(i);

            java.sql.Date birthday = null;
            java.sql.Date date = null;
            String sex = "";

            if(row.getCell(3) != null) {
                try{
                    String sexInput = String.valueOf(row.getCell(3));
                    // capitalize first letter
                    String sexOutput = sexInput.substring(0, 1).toUpperCase() + sexInput.substring(1);
                    if (sexOutput.equals("Hombre") || sexOutput.equals("Mujer")) {
                        sex = sexOutput;
                    }else{
                        sex = "";
                    }
                }catch (Exception ex) {
                    Logger.getLogger(FileController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if(row.getCell(4) != null) {
                String dateString = row.getCell(4).toString();
                if(dateString != ""){
                    try{
                        DateController dateController = new DateController();
                        birthday = dateController.tryParse(dateString);
                    }catch (Exception ex) {
                        birthday = null;
                        Logger.getLogger(FileController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            if(row.getCell(8) != null) {
                String dateString = row.getCell(8).toString();
                if(dateString != ""){
                    try{
                        DateController dateController = new DateController();
                        date = dateController.tryParse(dateString);
                    }catch (Exception ex) {
                        date = null;
                        Logger.getLogger(FileController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }

            String name = String.valueOf(row.getCell(1));
            String lastName = String.valueOf(row.getCell(2));
            String phone = String.valueOf(row.getCell(5));
            String email = String.valueOf(row.getCell(6));
            String note = String.valueOf(row.getCell(7));
            CustomerController customerCL = new CustomerController();
            try{
                customerCL.addClient( name,  lastName,  sex,  birthday,  phone,  email,  note, date);
            }catch(Exception ex){
                Logger.getLogger(FileController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void exportFile() throws SQLException {
        FeedbackController feedback = new FeedbackController();
        XSSFWorkbook workbook = new XSSFWorkbook();
        CustomerController customerCL = new CustomerController();

        if(customerCL != null){
            ArrayList<CustomerVO> clients =customerCL.getClients();
            sheet = this.printSheetWithCustomers(workbook, clients);
        }else{
            message = "No se puede exportar archivo";
            feedback.alertInformation(message);
        }

        file = feedback.windowSaveFile();
        //If file is not null, write to file using output stream.
        if (file != null) {
            try (FileOutputStream outputStream = new FileOutputStream(file.getAbsolutePath())) {
                workbook.write(outputStream);
                // Closing the workbook
                outputStream.close();
                workbook.close(); }
            catch (IOException ex) {
                Logger.getLogger(FileController.class.getName()).log(Level.SEVERE, null, ex);
            }
            message = "El proceso de exportacion archivo ha completado";

        }else{
            message = "Ha ocurrido error en exportar archivo";
        }
        feedback.alertInformation(message);
    }
    
//    public void importFile() throws IOException {
//        FeedbackController feedback = new FeedbackController();
//        file = feedback.windowOpenFile();
//        //If file is not null, write to file using output stream.
//        if (file != null) {
//            LoaderController loader = new LoaderController();
//            loader.index();
//
//            try (FileInputStream fileInput = new FileInputStream(file.getAbsolutePath())) {
//                XSSFWorkbook workbook = new XSSFWorkbook(fileInput);
//                XSSFSheet sheet = workbook.getSheetAt(0);
//                TaskController taskController = new TaskController(sheet);
//                taskController.valueProperty().addListener((observable, oldValue, newValue) -> message = String.valueOf(newValue));
//                feedback.alertInformation(message);
//                if(taskController.isRunning()){
//                    loader.finish();
//                    System.out.println("aaa");
//                    System.out.println("aaa");
//                    System.out.println("aaa");
//                    System.out.println("aaa");
//                    System.out.println("aaa");
//                    System.out.println("aaa");
//                    System.out.println("aaa");
//                    System.out.println("aaa");
//                    System.out.println("aaa");
//                    System.out.println("aaa");
//                    System.out.println("aaa");
//                    System.out.println("aaa");
//                    System.out.println("aaa");
//                    System.out.println("aaa");
//                    System.out.println("aaa");
//                    System.out.println("aaa");
//                    System.out.println("aaa");
//                    System.out.println("aaa");
//                    System.out.println("aaa");
//                }
//
//                Thread th = new Thread(taskController);
//                th.setDaemon(true);
//                th.start();
//
//            }
//            catch (IOException ex) {
//                Logger.getLogger(FileController.class.getName()).log(Level.SEVERE, null, ex);
//            }
//
//            message = "Se ha terminado el proceso de importacion con exito";
//
//        }else{
//            message = "No se puede importar archivo";
//        }
//        feedback.alertInformation(message);
//    }
}
