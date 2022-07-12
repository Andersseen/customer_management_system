package aplication.login;

import aplication.dashboard.DashboardController;
import config.DatabaseConnection;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class LoginController  {

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private Button cancelButton;
    @FXML
    private Button accessButton;

    @FXML
    private Button dashboard;


    @FXML
    private Label errorText;
    @FXML
    private TextField usernameInput;
    @FXML
    private PasswordField passInput;
    @FXML
    private TextField passTextHidden;
    @FXML
    private CheckBox checkboxPass;


    public void onClickCloseLogin( ActionEvent e){
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    public void onClickAccessUser( ActionEvent e){

        if(usernameInput.getText().isBlank() == false && passInput.getText().isBlank() == false){
            validate();
        }else{
            errorText.setText("Los campos son obligatorios");
        }

    }

    public void changeVisibility(ActionEvent e){
        if(checkboxPass.isSelected()){
            passInput.setVisible(false);
            passTextHidden.setDisable(false);
            passTextHidden.setVisible(true);
            passTextHidden.setText(passInput.getText());
            return;
        }
        passInput.setText(passTextHidden.getText());
        passTextHidden.setVisible(false);
        passTextHidden.setDisable(true);
        passInput.setVisible(true);
    }

    public void validate(){
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection conexion = connectNow.getConnection();

//        SELECT count(1) FROM users WHERE username = 'sadada' AND password = 'asda'

        String verifyLogin = "SELECT count(1) FROM users WHERE username ='" +usernameInput.getText()+"' AND password ='"+ passInput.getText() +"'";
        try{
            Statement statetment = conexion.createStatement();
            ResultSet queryResult = statetment.executeQuery(verifyLogin);

            while(queryResult.next()){
                if(queryResult.getInt(1) == 1){
                    errorText.setText("Bienvenido");
                }else{
                    errorText.setText("Prueba otra vez");
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void switchToDashboard(ActionEvent event) throws Exception {
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.close();
        DashboardController dashboard = new DashboardController();
        dashboard.index();
    }
}
