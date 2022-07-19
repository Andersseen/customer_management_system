package aplication.view.dashboard;

import aplication.controller.CustomerController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AddCustomer implements Initializable {

    CustomerController customerCL;
    DashboardController dashboardCL;

    private String message;

    @FXML
    private Button addBtn;

    @FXML
    private DatePicker birthdayInput;
    @FXML
    private DatePicker dateInput;

    @FXML
    private TextField emailInput;

    @FXML
    private TextField lastNameInput;

    @FXML
    private TextField nameInput;

    @FXML
    private TextField noteInput;

    @FXML
    private TextField phoneInput;

    @FXML
    private ChoiceBox<String> sexInput;
    private String[] sex = {"Hombre",  "Mujer"};

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        sexInput.getItems().addAll(sex);
    }

    public void onClickClearFields( ActionEvent e){
        birthdayInput.setValue(null);
        dateInput.setValue(null);
        emailInput.setText("");
        lastNameInput.setText("");
        nameInput.setText("");
        noteInput.setText("");
        phoneInput.setText("");
        sexInput.setValue(null);
    }

    public void onClickAddCustomer( ActionEvent event) throws IOException {
        Date birthday = Date.valueOf(birthdayInput.getValue());
        Date date = Date.valueOf(dateInput.getValue());
        String email = emailInput.getText();
        String lastName = lastNameInput.getText();
        String name = nameInput.getText();
        String note =noteInput.getText();
        String phone = phoneInput.getText();
        String sex = sexInput.getValue();

        customerCL = new CustomerController();
        customerCL.addClient( name,  lastName,  sex,  birthday,  phone,  email,  note, date);

        message = "Estas seguro que quieres agregar este cliente?";
        dashboardCL = new DashboardController();
        if(dashboardCL.alertDialog(message)){
            FXMLLoader editLoader = new FXMLLoader ();
            editLoader.setLocation(getClass().getResource("Dashboard.fxml"));
            try {
                editLoader.load();
            } catch (IOException ex) {
                Logger.getLogger(ListCustomer.class.getName()).log(Level.SEVERE, null, ex);
            }

            dashboardCL = editLoader.getController();
            try {
                dashboardCL.listPage();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Parent parent = editLoader.getRoot();
            Stage stage = (Stage) addBtn.getScene().getWindow();
            stage.setScene(new Scene(parent));
            stage.show();
        }
    }

}
