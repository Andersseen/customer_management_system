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

    private boolean statusExport;

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
