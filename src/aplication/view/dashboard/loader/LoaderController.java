package aplication.view.dashboard.loader;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;


public class LoaderController {

    private static Stage stage;

    @FXML
    private Label message;

    @FXML
    private ProgressBar progressBar;

    @FXML
    private Label title;



    public void index() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        FileInputStream fileInputStream = new FileInputStream(new File("src/aplication/view/loader/Loader.fxml"));
        Parent root = loader.load(fileInputStream);
        stage = new Stage();
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(new Scene(root, 500, 200));
        stage.show();
    }

    public void finish(){
        stage.close();
    }
}
