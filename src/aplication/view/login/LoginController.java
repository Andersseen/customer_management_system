package aplication.view.login;

import aplication.controller.UserController;
import aplication.view.dashboard.DashboardController;
import aplication.view.loader.LoaderController;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import java.sql.SQLException;

public class LoginController  {

    private UserController userCL;

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private Button cancelButton;
    @FXML
    private Button accessButton;


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
        userCL = new UserController();
        String user = this.usernameInput.getText();
        String pass = this.passInput.getText();

        if(!user.equals("") && !pass.equals("")) {
            try {
                if(userCL.checkLogin(user, pass)) {
                    stage = (Stage) accessButton.getScene().getWindow();
                    stage.close();
                    DashboardController dashboard = new DashboardController();
                    dashboard.index();
                }else {
                    errorText.setText("Usuario o contraseña invalido");
                }
            } catch ( SQLException e1) {
                e1.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void switchToDashboard(ActionEvent event) throws Exception {
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.close();
        DashboardController dashboard = new DashboardController();
        dashboard.index();
    }
}
