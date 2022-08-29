package aplication.view.login;

import aplication.controller.UserController;
import aplication.view.dashboard.DashboardController;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class LoginController  {

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


    public void onClickCloseLogin(){
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    public void onClickAccessUser(){

        if(!usernameInput.getText().isBlank() && !passInput.getText().isBlank()){
            validate();
        }else{
            errorText.setText("Los campos son obligatorios");
        }

    }

    public void changeVisibility(){
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
        UserController userCL = new UserController();
        String user = this.usernameInput.getText();
        String pass = this.passInput.getText();

        if(!user.equals("") && !pass.equals("")) {
            try {
                if(userCL.checkLogin(user, pass)) {
                    Stage stage = (Stage) accessButton.getScene().getWindow();
                    stage.close();
                    DashboardController dashboard = new DashboardController();
                    dashboard.index();
                }else {
                    errorText.setText("Usuario o contraseña invalido");
                }
            } catch ( Exception e1) {
                e1.printStackTrace();
            }
        }
    }

}
