package aplication.view.dashboard.editPage;

import aplication.controller.CustomerController;
import aplication.controller.FeedbackController;
import aplication.module.VO.CustomerVO;
import aplication.view.dashboard.DashboardController;
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
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EditCustomer implements Initializable {

    CustomerController customerCL;
    DashboardController dashboardCL;

    private int id;

    private String message;

    @FXML
    private Button editBtn;

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

        id = customer.getId();
        if(customer.getBirthday() != null){
            LocalDate lclBirthday = customer.getBirthday().toLocalDate();
            birthdayInput.setValue(lclBirthday);
        }
        if(customer.getDate() != null){
            LocalDate lclDate = customer.getDate().toLocalDate();
            dateInput.setValue(lclDate);
        }
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

    public void onClickEditCustomer( ActionEvent event){
        Date birthday = Date.valueOf(birthdayInput.getValue());
        Date date = Date.valueOf(dateInput.getValue());

        CustomerVO customer = new CustomerVO();
        customer.setId(this.id);
        customer.setName(nameInput.getText());
        customer.setLastName(lastNameInput.getText());
        customer.setSex(sexInput.getValue());
        customer.setBirthday(birthday);
        customer.setPhone(phoneInput.getText());
        customer.setEmail(emailInput.getText());
        customer.setNote(noteInput.getText());
        customer.setDate(date);

        message = "Estas seguro que quieres editar este cliente?";
        dashboardCL = new DashboardController();
        FeedbackController feedback = new FeedbackController();

        if (feedback.alertConfirmation(message)){
            customerCL = new CustomerController();
            customerCL.editClient(customer);

            FXMLLoader editLoader = new FXMLLoader ();
            editLoader.setLocation(getClass().getResource("Dashboard.fxml"));
            try {
                editLoader.load();
            } catch (IOException ex) {
                Logger.getLogger(EditCustomer.class.getName()).log(Level.SEVERE, null, ex);
            }

            dashboardCL = editLoader.getController();
            try {
                dashboardCL.listPage();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Parent parent = editLoader.getRoot();
            Stage stage = (Stage) editBtn.getScene().getWindow();
            stage.setScene(new Scene(parent));
            stage.show();
        }
    }

}
