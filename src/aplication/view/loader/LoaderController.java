package aplication.view.loader;

import aplication.controller.FileController;
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
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoaderController {

    private static Stage stage;

    @FXML
    private Label message;

    @FXML
    private ProgressBar progressBar;

    @FXML
    private Label title;

//    public LoaderController( ProgressBar progressBar, Label title,Label message) {
//        this.message = message;
//        this.progressBar = progressBar;
//        this.title = title;
//    }

    public void index() throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader();
            FileInputStream fileInputStream = new FileInputStream(new File("src/aplication/view/loader/Loader.fxml"));
            Parent root = loader.load(fileInputStream);
            stage = new Stage();
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setScene(new Scene(root, 500, 200));
            stage.show();
        }
        catch (IOException ex) {
            Logger.getLogger(FileController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void finish(){
        stage.close();
    }
}
