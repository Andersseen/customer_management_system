package aplication.controller;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.FileChooser;
import org.apache.xmlbeans.SystemProperties;
import java.io.File;
import java.util.Optional;

public class FeedbackController {

    public boolean alertConfirmation(String message){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Diálogo de información");
        alert.setHeaderText(null);
        alert.setContentText(message);
        Optional<ButtonType> result = alert.showAndWait();
        return result.get() == ButtonType.OK;
    }

    public void alertInformation(String message){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Diálogo de información");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public File windowSaveFile(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Exporta archivo");
        fileChooser.setInitialDirectory(new File(SystemProperties.getProperty("user.home")));
        //Set extension filter to .xlsx files
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Excel files (*.xlsx)", "*.xlsx");
        fileChooser.getExtensionFilters().add(extFilter);
        //Show save file dialog
        return fileChooser.showSaveDialog(null);
    }

    public File windowOpenFile(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Importa archivo");
        fileChooser.setInitialDirectory(new File(SystemProperties.getProperty("user.home")));
        //Set extension filter to .xlsx files
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Excel files (*.xlsx)", "*.xlsx", "*.xls");
        fileChooser.getExtensionFilters().add(extFilter);

        //Show save file dialog
        return fileChooser.showOpenDialog(null);
    }

}
