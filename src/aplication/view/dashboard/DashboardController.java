package aplication.view.dashboard;

import aplication.controller.CustomerController;
import aplication.module.VO.CustomerVO;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;

public class DashboardController implements Initializable  {

    double x,y = 0;

    private static Stage stage;
    private String message;
    final private String[] excelColumns = {"ID",  "Nombre", "Apellido", "Sexo", "Cumpleaños", "Email", "Telefono", "Nota", "Fecha"};

    private CustomerController customerCL;

    @FXML
    private StackPane contentSwicher;

    @FXML
    private AnchorPane bord;
    @FXML
    private Button close;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        bord.setOnMousePressed(mouseEvent -> {
            x =mouseEvent.getSceneX();
            y = mouseEvent.getSceneY();
        });

        bord.setOnMouseDragged(mouseEvent -> {
            stage.setX(mouseEvent.getScreenX() - x);
            stage.setY(mouseEvent.getScreenY() - y);
        });
        try{
            Parent root = FXMLLoader.load(getClass().getResource("ListCustomer.fxml"));

            contentSwicher.getChildren().removeAll();
            contentSwicher.getChildren().setAll(root);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void index() throws Exception{
        FXMLLoader loader = new FXMLLoader();
        FileInputStream fileInputStream = new FileInputStream(new File("src/aplication/view/dashboard/Dashboard.fxml"));
        Parent root = loader.load(fileInputStream);
        stage = new Stage();
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(new Scene(root, 1200, 600));
        stage.show();

    }

    public void listPage() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("ListCustomer.fxml"));
        contentSwicher.getChildren().removeAll();
        contentSwicher.getChildren().setAll(root);
    }
    public void addPage() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("AddCustomer.fxml"));
        contentSwicher.getChildren().removeAll();
        contentSwicher.getChildren().setAll(root);
    }

    public void editPage(CustomerVO customer ) throws IOException {
        FXMLLoader child = new FXMLLoader ();
        child.setLocation(getClass().getResource("EditCustomer.fxml"));
        Parent root = child.load();
            contentSwicher.getChildren().removeAll();
            contentSwicher.getChildren().setAll(root);
        EditCustomer editCL = child.getController();
        editCL.index(customer);
    }

    public void onClickCloseDashboard( ActionEvent e){
        message = "Estas seguro que quieres salir de la aplicacion?";
        boolean itsOk = this.alertDialog(message);
        if(itsOk){
            stage = (Stage) close.getScene().getWindow();
            stage.close();
        }
    }

    public void onClickExportExcel( ActionEvent e) throws IOException, SQLException {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Test");

//        CreationHelper createHelper = workbook.getCreationHelper();

        // Create a Font for styling header cells
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setFontName("Nunito");
        headerFont.setFontHeightInPoints((short) 16);
        headerFont.setColor(IndexedColors.VIOLET.getIndex());

        XSSFRow header = sheet.createRow(0);

        // Create a CellStyle with the font
        CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFont(headerFont);

        Row headerRow = sheet.createRow(0);

        for(int i =0; i < excelColumns.length; i++){
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(excelColumns[i]);
            cell.setCellStyle(headerCellStyle);
        }

        // Create Cell Style for formatting Date
//        CellStyle dateCellStyle = workbook.createCellStyle();
//        dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("yyyy-MM-dd"));

//        header.createCell(0).setCellValue("ID");
//        header.createCell(1).setCellValue("Name");
//        header.createCell(2).setCellValue("Last Name");
//        header.createCell(3).setCellValue("Sexo");
//        header.createCell(4).setCellValue("Cumpleaños");
//        header.createCell(5).setCellValue("Email");
//        header.createCell(6).setCellValue("Telefono");
//        header.createCell(7).setCellValue("Nota");
//        header.createCell(8).setCellValue("Fecha");

        customerCL = new CustomerController();
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
//                Cell dateOfBirthCell = row.createCell(4);
//                dateOfBirthCell.setCellValue(client.getBirthday());
//                dateOfBirthCell.setCellStyle(dateCellStyle);

                row.createCell(5).setCellValue(client.getEmail());
                row.createCell(6).setCellValue(client.getPhone());
                row.createCell(7).setCellValue(client.getNote());
                row.createCell(8).setCellValue(date);
//                Cell dateOfDate= row.createCell(8);
//                dateOfDate.setCellValue(client.getDate());
//                dateOfDate.setCellStyle(dateCellStyle);
                index.getAndIncrement();
            });

        }
        // Resize all columns to fit the content size
        for(int i = 0; i < excelColumns.length; i++) {
            sheet.autoSizeColumn(i);
        }
        // Write the output to a file
        FileOutputStream fileOut = new FileOutputStream("Test.xlsx");
        workbook.write(fileOut);
        fileOut.close();
        // Closing the workbook
        workbook.close();

    }
    public void onClickImportExcel( ActionEvent e) throws IOException, SQLException, ParseException {


        FileInputStream fileInput = new FileInputStream(new File("BASE DE DATOS DE PACIENTES.xls"));
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
                SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy");
                String dateString = row.getCell(8).toString();

                if(dateString != ""){
                    java.util.Date parsed = format.parse(dateString);
                    java.sql.Date sql = new java.sql.Date(parsed.getTime());
                    birthday = sql;
                }

            }
            if(row.getCell(4) != null) {
                SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy");
                String dateString = row.getCell(4).toString();


                if(dateString != ""){
                    java.util.Date parsed = format.parse(dateString);
                    java.sql.Date sql = new java.sql.Date(parsed.getTime());
                    date =  sql;
                }

            }

            String name = String.valueOf(row.getCell(1));
            String lastName = String.valueOf(row.getCell(2));
            String sex = String.valueOf(row.getCell(3));

            String phone = String.valueOf(row.getCell(5));
            String email = String.valueOf(row.getCell(6));
            String note = String.valueOf(row.getCell(7));


            row.getCell(2);
            customerCL = new CustomerController();
            customerCL.addClient( name,  lastName,  sex,  birthday,  phone,  email,  note, date);
        }
    }

    public boolean alertDialog(String message){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Diálogo de información");
        alert.setHeaderText(null);
        alert.setContentText(message);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            return true;
        }
        return false;
    }

    public void infoDialog(String message){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Diálogo de información");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();

    }
}
