package aplication.controller;


import aplication.module.VO.CustomerVO;
import aplication.service.ExportTaskService;
import aplication.service.ImportTaskService;
import aplication.view.dashboard.DashboardController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import org.apache.poi.ss.usermodel.*;
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



    public Font setFontToFile(XSSFWorkbook workbook){
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setFontName("Nunito");
        headerFont.setFontHeightInPoints((short) 16);
        headerFont.setColor(IndexedColors.VIOLET.getIndex());
        return headerFont;
    }


    public void exportFile() throws SQLException {
        FeedbackController feedback = new FeedbackController();
        XSSFWorkbook workbook = new XSSFWorkbook();
        CustomerController customerCL = new CustomerController();

        if(customerCL != null){
            ArrayList<CustomerVO> clients =customerCL.getClients();
//            sheet = this.printSheetWithCustomers(workbook, clients);

            ExportTaskService exportService = new ExportTaskService(clients,workbook);

            exportService.setOnScheduled(event -> {
                System.out.println("Done");
            });

            exportService.setOnFailed( event -> {
                System.out.println("Failed");
            });


            exportService.start();
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

    public void importFile() throws SQLException, IOException {

//        DashboardController dashboardController = new DashboardController();
//        dashboardController.loadingDashboard(dashboardController,btnImport);
//        this.loaderPage();
        FeedbackController feedback = new FeedbackController();
        File file = feedback.windowOpenFile();

        //If file is not null, write to file using output stream.
        if (file != null) {

            try (FileInputStream fileInput = new FileInputStream(file.getAbsolutePath())) {
                XSSFWorkbook workbook = new XSSFWorkbook(fileInput);
                XSSFSheet sheet = workbook.getSheetAt(0);

                ImportTaskService service  = new ImportTaskService(sheet);
//                service.setOnScheduled(event -> {
//
//                });
                service.setOnSucceeded(event -> {
//                    DashboardController dashboardCL = new DashboardController();
//                    dashboardCL.returnToRefreshDashboard( dashboardCL,btnImport);
                    message = "Se ha terminado el proceso de importacion con exito";
                    feedback.alertInformation(message);
                });
                service.setOnFailed( event ->{
                    message = "No se puede importar archivo";
                    feedback.alertInformation(message);
                });
                service.start();
            }
            catch (IOException ex) {
                Logger.getLogger(FileController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }
    
}
