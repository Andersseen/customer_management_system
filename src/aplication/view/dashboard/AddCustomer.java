package aplication.view.dashboard;

import aplication.controller.CustomerController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

public class AddCustomer implements Initializable {

    CustomerController customerCL;

    @FXML
    private Button addBtn;
    @FXML
    private Button cancelBtn;

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

    public void onClickAddCustomer( ActionEvent e){
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

    }

}
