package aplication.view.dashboard.loader;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ProgressBar;
import java.net.URL;
import java.util.ResourceBundle;


public class LoaderController implements Initializable {

    @FXML
    private ProgressBar progressBar;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        progressBar.setStyle("-fx-accent:  #6A1B9A;");
    }
}
