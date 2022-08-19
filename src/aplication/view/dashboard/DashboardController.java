package aplication.view.dashboard;

import aplication.controller.FeedbackController;
import aplication.controller.FileController;
import aplication.module.VO.CustomerVO;
import java.io.*;
import aplication.view.dashboard.editPage.EditCustomer;
import aplication.view.dashboard.listPage.ListCustomer;
import aplication.view.dashboard.loader.LoaderController;
import javafx.event.ActionEvent;
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
    boolean windowStatus = true;

    @FXML
    private StackPane contentSwicher;

    @FXML
    private AnchorPane bord;
    @FXML
    private Button close;
    @FXML
    private Button minimize;
    @FXML
    private Button maximize;
    @FXML
    private Button btnExport;
    @FXML
    private Button btnImport;

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
            Parent root = FXMLLoader.load(ListCustomer.class.getResource("ListCustomer.fxml"));

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
        Parent root = FXMLLoader.load(getClass().getResource("listPage/ListCustomer.fxml"));
        contentSwicher.getChildren().removeAll();
        contentSwicher.getChildren().setAll(root);
    }
    public void addPage() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("addPage/AddCustomer.fxml"));
        contentSwicher.getChildren().removeAll();
        contentSwicher.getChildren().setAll(root);
    }

    public void editPage(CustomerVO customer ) throws IOException {
        FXMLLoader child = new FXMLLoader ();
        child.setLocation(getClass().getResource("editPage/EditCustomer.fxml"));
        Parent root = child.load();
            contentSwicher.getChildren().removeAll();
            contentSwicher.getChildren().setAll(root);
        EditCustomer editCL = child.getController();
        editCL.index(customer);
    }
    public void loaderPage() throws IOException {
        Parent root = FXMLLoader.load(LoaderController.class.getResource("Loader.fxml"));
        contentSwicher.getChildren().removeAll();
        contentSwicher.getChildren().setAll(root);
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

    public void onClickToMaximizeDashboard( ActionEvent e){

        stage = (Stage) maximize.getScene().getWindow();
        stage.setMaximized(windowStatus);
        windowStatus = !windowStatus;
    }

    public void onClickToMinimizeDashboard( ActionEvent e){
        stage = (Stage) minimize.getScene().getWindow();
        stage.setIconified(true);
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

    public void onClickExportExcel( ActionEvent e) throws SQLException, IOException {
        this.loaderPage();

        FileController fileController = new FileController(btnExport ,btnImport);
        fileController.exportFile();
    }
    public void onClickImportExcel( ActionEvent e) throws IOException {
        this.loaderPage();

        FileController fileController = new FileController(btnExport ,btnImport);
        fileController.importFile();
    }
}
