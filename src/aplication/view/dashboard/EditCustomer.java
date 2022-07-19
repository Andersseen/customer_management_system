package aplication.view.dashboard;

import aplication.controller.CustomerController;
import aplication.module.VO.CustomerVO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.ResourceBundle;

public class EditCustomer implements Initializable {

    CustomerController customerCL;

    @FXML
    private Button edittBtn;
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

    public void index(CustomerVO customer){

        LocalDate lclBirthday = customer.getBirthday().toLocalDate();
        birthdayInput.setValue(lclBirthday);

        LocalDate lclDate = customer.getDate().toLocalDate();
        dateInput.setValue(lclDate);

        emailInput.setText(customer.getEmail());
        lastNameInput.setText(customer.getLastName());
        nameInput.setText(customer.getName());
        noteInput.setText(customer.getNote());
        phoneInput.setText(customer.getPhone());
        sexInput.setValue(customer.getSex());
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

    public void onClickEditCustomer( ActionEvent e){

        Date birthday = Date.valueOf(birthdayInput.getValue());
        Date date = Date.valueOf(dateInput.getValue());
        String email = emailInput.getText();
        String lastName = lastNameInput.getText();
        String name = nameInput.getText();
        String note =noteInput.getText();
        String phone = phoneInput.getText();
        String sex = sexInput.getValue();


        customerCL.editClient( name,  lastName,  sex,  birthday,  phone,  email,  note, date);
    }

}
