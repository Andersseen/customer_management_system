package aplication.view.dashboard;

import aplication.module.VO.CustomerVO;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
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

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DashboardController implements Initializable  {

    double x,y = 0;

    private static Stage stage;

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

    public void PageThird(CustomerVO customer ) throws IOException {
        FXMLLoader child = new FXMLLoader ();
        child.setLocation(getClass().getResource("EditCustomer.fxml"));
        Parent root = child.load();
//            Parent root = FXMLLoader.load(getClass().getResource("EditCustomer.fxml"));
            contentSwicher.getChildren().removeAll();
            contentSwicher.getChildren().setAll(root);
        EditCustomer editCL = child.getController();
        editCL.index(customer);
    }

    public void onClickCloseDashboard( ActionEvent e){
        stage = (Stage) close.getScene().getWindow();
        stage.close();
    }
}
