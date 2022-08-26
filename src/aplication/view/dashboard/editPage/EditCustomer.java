package aplication.view.dashboard.editPage;

import aplication.controller.CustomerController;
import aplication.controller.FeedbackController;
import aplication.module.VO.CustomerVO;
import aplication.view.dashboard.DashboardController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class EditCustomer implements Initializable {

    private CustomerController customerCL;
    private DashboardController dashboardCL;
    private int id;
    private String message;

    @FXML
    private AnchorPane historicalPane;
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
        historicalPane.setVisible(false);
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

    public void onClickClearFields( ){
        birthdayInput.setValue(null);
        dateInput.setValue(null);
        emailInput.setText("");
        lastNameInput.setText("");
        nameInput.setText("");
        noteInput.setText("");
        phoneInput.setText("");
        sexInput.setValue(null);
    }

    public void onClickEditCustomer( ){
        Date birthday = null;
        Date date = null;
        if(birthdayInput.getValue() != null){
            birthday = Date.valueOf(birthdayInput.getValue());
        }
        if(dateInput.getValue() != null){
            date = Date.valueOf(dateInput.getValue());
        }

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
            try {
                customerCL.editClient(customer);
            }catch(Exception ex){
                feedback.alertInformation("Ha pasado un error!");
            }
            dashboardCL.returnToRefreshDashboard(dashboardCL, editBtn);

        }
    }

}
