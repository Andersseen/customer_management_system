package aplication.view.dashboard;

import aplication.controller.FeedbackController;
import aplication.controller.FileController;
import aplication.controller.TaskController;
import aplication.controller.TaskService;
import aplication.module.VO.CustomerVO;
import java.io.*;

import javafx.concurrent.Worker;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;


public class DashboardController implements Initializable  {
    double x,y = 0;
    private static Stage stage;

    @FXML
    private StackPane contentSwicher;

    @FXML
    private AnchorPane bord;
    @FXML
    private Button close;
    @FXML
    private Button btnImport;
    private String message;

//    @FXML
//    private Label label;


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

    public void returnToRefreshDashboard(DashboardController dc, Button btn){
        FXMLLoader dashLoader = new FXMLLoader ();
        dashLoader.setLocation(getClass().getResource("Dashboard.fxml"));
        try {
            dashLoader.load();
            dc = dashLoader.getController();
            dc.listPage();
        } catch (IOException ex) {
            Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, ex);
        }
        Parent parent = dashLoader.getRoot();
        Stage stage = (Stage) btn.getScene().getWindow();
        stage.setScene(new Scene(parent));
        stage.show();
    }

    public void onClickCloseDashboard( ActionEvent e){
        String message = "Estas seguro que quieres salir de la aplicacion?";
        FeedbackController feedback = new FeedbackController();
        boolean itsOk = feedback.alertConfirmation(message);
        if(itsOk){
            stage = (Stage) close.getScene().getWindow();
            stage.close();
        }
    }

    public void onClickExportExcel( ActionEvent e) throws SQLException {
        FileController fileController = new FileController();
        fileController.exportFile();
    }
    public void onClickImportExcel( ActionEvent e) throws IOException {
//        FileController fileController = new FileController();
//        fileController.importFile();

        FeedbackController feedback = new FeedbackController();
        File file = feedback.windowOpenFile();
        //If file is not null, write to file using output stream.
        if (file != null) {
            TaskController taskController = null;



            try (FileInputStream fileInput = new FileInputStream(file.getAbsolutePath())) {
                XSSFWorkbook workbook = new XSSFWorkbook(fileInput);
                XSSFSheet sheet = workbook.getSheetAt(0);

//                TaskService service = new TaskService(sheet);
//                Region veil = new Region();
//                veil.setStyle("-fx-background-color: rgba(0, 0, 0, 0.4)");
//                veil.setPrefSize(400, 440);
//                ProgressIndicator p = new ProgressIndicator();
//                p.setMaxSize(140, 140);
//                p.setStyle(" -fx-progress-color: orange;");
//                p.progressProperty().bind(service.progressProperty());
//                veil.visibleProperty().bind(service.runningProperty());
//                p.visibleProperty().bind(service.runningProperty());
//                contentSwicher.getChildren().removeAll();
//                contentSwicher.getChildren().addAll(veil, p);
//                service.start();

//                taskController = new TaskController(sheet);

//                System.out.println(taskController.isDone());
//                System.out.println("AAAAAA");
//                System.out.println(taskController.getValue());
//                System.out.println("bbbbbb");
//                System.out.println(taskController.isRunning());
//                System.out.println(taskController.isCancelled());
//                System.out.println("bbbbbb");
//                System.out.println(taskController.getState());
//
//
//                taskController.setOnSucceeded((event) ->{
//                    System.out.println("YeAH");
//                });



                TaskService service  = new TaskService(sheet);

                service.setOnSucceeded(event -> {
                    System.out.println("Done!");
                    System.out.println("Done!");
                    System.out.println("Done!");
                    System.out.println("Done!");
                    System.out.println("Done!");

                });
                service.setOnRunning(event -> {
                    System.out.println("Running!");
                    System.out.println("Running!");
                    System.out.println("Running!");
                    System.out.println("Running!");
                    System.out.println("Running!");

                });

                service.setOnFailed(new EventHandler<WorkerStateEvent>() {

                    @Override
                    public void handle(WorkerStateEvent arg0) {
                        Throwable throwable = service.getException();
                        throwable.printStackTrace();
                    }
                });

                service.start();

//                Thread th = new Thread(taskController);
//                th.setDaemon(true);
//                th.start();
            }
            catch (IOException ex) {
                Logger.getLogger(FileController.class.getName()).log(Level.SEVERE, null, ex);
            }

            message = "Se ha terminado el proceso de importacion con exito";

        }else{
            message = "No se puede importar archivo";
        }
        feedback.alertInformation(message);

        DashboardController dashboardCL = new DashboardController();
        dashboardCL.returnToRefreshDashboard( dashboardCL,btnImport);
    }


}
