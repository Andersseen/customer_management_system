package aplication.controller;


import aplication.module.VO.CustomerVO;
import aplication.service.ExportTaskService;
import aplication.service.ImportTaskService;
import aplication.view.dashboard.DashboardController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellUtil;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileController {
    private File file;
    private String message;

    private boolean statusExport;

    final private String[] excelColumns = {"Nº Cliente",  "Nombre", "Apellido", "Sexo", "Cumpleaños", "Email", "Telefono", "Nota", "Fecha"};

    @FXML
    private Button btnExport;
    @FXML
    private Button btnImport;

    public FileController() {

    }
    public FileController(Button btnExport, Button btnImport) {
        this.btnExport = btnExport;
        this.btnImport = btnImport;
    }

    public Font setFontToHeader(XSSFWorkbook workbook){
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setFontName("Calibri");
        headerFont.setFontHeightInPoints((short) 11);
        headerFont.setColor(IndexedColors.BLACK.getIndex());
        return headerFont;
    }

    public Font setFontToTitle(XSSFWorkbook workbook){
        Font titleFont = workbook.createFont();
        titleFont.setBold(true);
        titleFont.setFontName("Calibri");
//        titleFont.setFontHeight((short) 20);
        titleFont.setFontHeightInPoints((short) 20);
        titleFont.setColor(IndexedColors.WHITE.getIndex());
        return titleFont;
    }

    public void makeHeaderRow(XSSFWorkbook workbook, Row headerRow){
        Font headerFont = this.setFontToHeader(workbook);

        CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFont(headerFont);

        for(int i =0; i < excelColumns.length; i++){
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(excelColumns[i]);

            if(i == 0){
                CellStyle firstCellStyle = workbook.createCellStyle();
                firstCellStyle.setBorderLeft(BorderStyle.MEDIUM);
                firstCellStyle.setBorderTop(BorderStyle.MEDIUM);
                firstCellStyle.setBorderBottom(BorderStyle.MEDIUM);
                cell.setCellStyle(firstCellStyle);
            }
            if(i == 8){
                CellStyle lastCellStyle = workbook.createCellStyle();
                lastCellStyle.setBorderRight(BorderStyle.MEDIUM);
                lastCellStyle.setBorderTop(BorderStyle.MEDIUM);
                lastCellStyle.setBorderBottom(BorderStyle.MEDIUM);
                cell.setCellStyle(lastCellStyle);
            }
            headerCellStyle.setBorderTop(BorderStyle.MEDIUM);
            headerCellStyle.setBorderBottom(BorderStyle.MEDIUM);
            cell.setCellStyle(headerCellStyle);
        }

    }

    public void printTitle(XSSFWorkbook workbook, Sheet sheet, Row row){
        String title = "BASE DE DATOS PACIENTES CEMERESI";

        Font titleFont = this.setFontToTitle(workbook);

        CellStyle titleCellStyle = workbook.createCellStyle();
        titleCellStyle.setFillBackgroundColor((short) 2);
        titleCellStyle.setAlignment(HorizontalAlignment.CENTER);
        titleCellStyle.setFillForegroundColor(IndexedColors.BLACK.getIndex());
        titleCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        titleCellStyle.setFont(titleFont);

        CellRangeAddress range = new CellRangeAddress(1, 1, 0, excelColumns.length);

        // Creates the cell
        CellUtil.createCell(row,sheet.addMergedRegion(range), title, titleCellStyle );
    }

    public void exportFile() throws SQLException {
        FeedbackController feedback = new FeedbackController();
        XSSFWorkbook workbook = new XSSFWorkbook();
        CustomerController customerCL = new CustomerController();

        if(customerCL != null){
            ArrayList<CustomerVO> clients =customerCL.getClients();
            ExportTaskService exportService = new ExportTaskService(clients,workbook);
            exportService.setOnSucceeded(event -> {
                System.out.println("Done");
                statusExport = true;
            });
            exportService.setOnFailed( event -> {
                System.out.println("Failed");
                statusExport = false;
            });

            exportService.start();
        }else{
            message = "No se puede exportar archivo";
        }
        file = feedback.windowSaveFile();
        DashboardController dashboardCL = new DashboardController();
        //If file is not null, write to file using output stream.
        if (file != null && statusExport) {
            try (FileOutputStream outputStream = new FileOutputStream(file.getAbsolutePath())) {
                workbook.write(outputStream);
                // Closing the workbook
                outputStream.close();
                workbook.close();
            }
            catch (IOException ex) {
                Logger.getLogger(FileController.class.getName()).log(Level.SEVERE, null, ex);
            }
            message = "El proceso de exportacion archivo ha completado";
        }else{
            message = "Ha ocurrido error en exportar archivo";
        }
        feedback.alertInformation(message);
        dashboardCL.returnToRefreshDashboard( dashboardCL,btnExport);
    }

    public void importFile(){
        FeedbackController feedback = new FeedbackController();
        File file = feedback.windowOpenFile();
        //If file is not null, write to file using output stream.
        DashboardController dashboardCL = new DashboardController();
        if (file != null) {
            try (FileInputStream fileInput = new FileInputStream(file.getAbsolutePath())) {
                XSSFWorkbook workbook = new XSSFWorkbook(fileInput);
                XSSFSheet sheet = workbook.getSheetAt(0);

                ImportTaskService service  = new ImportTaskService(sheet);

                service.setOnSucceeded(event -> {
                    message = "Se ha terminado el proceso de importacion con exito";
                });
                service.setOnFailed( event ->{
                    message = "No se puede importar archivo";
                });
                service.start();
            }
            catch (IOException ex) {
                Logger.getLogger(FileController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            message = "Ha ocurrido error en importacion archivo";
        }
        feedback.alertInformation(message);
        dashboardCL.returnToRefreshDashboard( dashboardCL,btnImport);
    }
}
