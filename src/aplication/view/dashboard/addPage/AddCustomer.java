package aplication.view.dashboard.addPage;

import aplication.controller.CustomerController;
import aplication.controller.FeedbackController;
import aplication.controller.HistoricalController;
import aplication.view.dashboard.DashboardController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;


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
    private TextArea historicalTextarea;
    @FXML
    private Label historicalLabel;
    @FXML
    private CheckBox historicalCheckBox;
    @FXML
    private Label msgName;
    @FXML
    private Label msgLastName;
    @FXML
    private Label msgSex;
    @FXML
    private Label msgPhone;

    @FXML
    private ChoiceBox<String> sexInput;
    private String[] sex = {"","Hombre",  "Mujer"};

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        sexInput.getItems().addAll(sex);
        sexInput.setValue("");

        historicalLabel.setVisible(false);
        historicalTextarea.setVisible(false);

        historicalCheckBox.setOnMouseClicked((MouseEvent event) -> {
            if(historicalCheckBox.isSelected()){
                historicalLabel.setVisible(true);
                historicalTextarea.setVisible(true);
                return;
            }
            historicalLabel.setVisible(false);
            historicalTextarea.setVisible(false);
        });
    }

    public void onClickClearFields( ActionEvent e){
        birthdayInput.setValue(null);
        dateInput.setValue(null);
        emailInput.setText("");
        lastNameInput.setText("");
        nameInput.setText("");
        noteInput.setText("");
        phoneInput.setText("");
        sexInput.setValue("");

        msgName.setVisible(false);
        msgLastName.setVisible(false);
        msgSex.setVisible(false);
        msgPhone.setVisible(false);
    }

    public void onClickAddCustomer( ActionEvent event) throws IOException {
        Date birthday = null;
        Date date = null;
        String sex = null;
        if(birthdayInput.getValue() != null){
            birthday = Date.valueOf(birthdayInput.getValue());
        }
        if (dateInput.getValue() != null){
            date = Date.valueOf(dateInput.getValue());
        }
        if (sexInput.getValue() != null){
            sex = sexInput.getValue();
        }
        String email = emailInput.getText();
        String lastName = lastNameInput.getText();
        String name = nameInput.getText();
        String note =noteInput.getText();
        String phone = phoneInput.getText();
        String historical = historicalTextarea.getText();

        FeedbackController feedback = new FeedbackController();

        if(!name.isEmpty() && !lastName.isEmpty() && !sex.isEmpty() && !phone.isEmpty()){
            message = "Estas seguro que quieres agregar este cliente?";
            if(feedback.alertConfirmation(message)){
                customerCL = new CustomerController();
                int aaa = customerCL.addClient( name,  lastName,  sex,  birthday,  phone,  email,  note, date);

                if(!historical.isEmpty()){
                    HistoricalController historicalCL = new HistoricalController();
                    historicalCL.controlAddingHistorical(aaa, name, lastName, historical);
                }
                dashboardCL = new DashboardController();
                dashboardCL.returnToRefreshDashboard( dashboardCL,addBtn);
            }
        }else{
            message = "Hay que rellenar los campos obligatorios";
            feedback.alertInformation(message);
            msgName.setVisible(true);
            msgLastName.setVisible(true);
            msgSex.setVisible(true);
            msgPhone.setVisible(true);
        }
    }

}
