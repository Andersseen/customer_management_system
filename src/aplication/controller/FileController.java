package aplication.controller;


import aplication.module.VO.CustomerVO;
import aplication.view.dashboard.DashboardController;
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
    final private String[] excelColumns = {"ID",  "Nombre", "Apellido", "Sexo", "Cumpleaños", "Email", "Telefono", "Nota", "Fecha"};

    public void exportFile() throws SQLException {

        FeedbackController feedback = new FeedbackController();
        file = feedback.windowSaveFile();

        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet();
//        Create a Font for styling header cells
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setFontName("Nunito");
        headerFont.setFontHeightInPoints((short) 16);
        headerFont.setColor(IndexedColors.VIOLET.getIndex());

        // Create a CellStyle with the font
        CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFont(headerFont);

        Row headerRow = sheet.createRow(0);

        for(int i =0; i < excelColumns.length; i++){
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(excelColumns[i]);
            cell.setCellStyle(headerCellStyle);
        }


        CustomerController customerCL = new CustomerController();
        AtomicInteger index = new AtomicInteger(1);
        if(customerCL != null){
            ArrayList<CustomerVO> clients =customerCL.getClients();
            clients.forEach(client ->{
                String birthday  = "";
                String date = "";
                //Converting the Date object to String format
                if(client.getDate() != null){
                    Date dateObj = client.getDate();
                    date = dateObj.toString();
                }
                if(client.getBirthday() != null){
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
        }


        // Resize all columns to fit the content size
        for(int i = 0; i < excelColumns.length; i++) {
            sheet.autoSizeColumn(i);
        }
        //If file is not null, write to file using output stream.
        if (file != null) {
            try (FileOutputStream outputStream = new FileOutputStream(file.getAbsolutePath())) {
                workbook.write(outputStream);
                // Closing the workbook
                outputStream.close();
                workbook.close(); }
            catch (IOException ex) {
                Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void importFile(){
        FeedbackController feedback = new FeedbackController();
        file = feedback.windowOpenFile();

        //If file is not null, write to file using output stream.
        if (file != null) {
            try (FileInputStream fileInput = new FileInputStream(file.getAbsolutePath())) {
                XSSFWorkbook workbook = new XSSFWorkbook(fileInput);
                XSSFSheet sheet = workbook.getSheetAt(0);

                CreationHelper createHelper = workbook.getCreationHelper();

                // Create Cell Style for formatting Date
                CellStyle dateCellStyle = workbook.createCellStyle();
                dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("yyyy-mm-dd"));

                Row row;
                for(int i = 5; i <= sheet.getLastRowNum(); i++){
                    row = sheet.getRow(i);

                    java.sql.Date birthday = null;
                    java.sql.Date date = null;
                    if(row.getCell(8) != null) {

                        String dateString = row.getCell(8).toString();
                        if(dateString != ""){
                            try{
                                DateController dateController = new DateController();
                                date = dateController.tryParse(dateString);
                            }catch (Exception ex) {
                                System.out.println(ex);
                                Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }

                    }
                    if(row.getCell(4) != null) {
                        String dateString = row.getCell(4).toString();
                        if(dateString != ""){
                            try{
                                DateController dateController = new DateController();
                                birthday = dateController.tryParse(dateString);
                            }catch (Exception ex) {
                                System.out.println(ex);
                                Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }
                    String name = String.valueOf(row.getCell(1));
                    String lastName = String.valueOf(row.getCell(2));
                    String sex = String.valueOf(row.getCell(3));

                    String phone = String.valueOf(row.getCell(5));
                    String email = String.valueOf(row.getCell(6));
                    String note = String.valueOf(row.getCell(7));


                    row.getCell(2);
                    CustomerController customerCL = new CustomerController();
                    customerCL.addClient( name,  lastName,  sex,  birthday,  phone,  email,  note, date);
                }
            }
            catch (IOException ex) {
                Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
