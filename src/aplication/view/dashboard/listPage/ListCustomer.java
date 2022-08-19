package aplication.view.dashboard.listPage;

import aplication.controller.CustomerController;
import aplication.controller.FeedbackController;
import aplication.module.VO.CustomerVO;
import aplication.view.dashboard.DashboardController;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.util.Callback;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ListCustomer implements Initializable {

    private CustomerController customerCL;
    private DashboardController dashboardCL;

    @FXML
    private TableView<CustomerVO> table;

    @FXML
    private TableColumn<CustomerVO, Integer> id;
    @FXML
    private TableColumn<CustomerVO, String> name;
    @FXML
    private TableColumn<CustomerVO, String> lastName;

    @FXML
    private TableColumn<CustomerVO, String> birthday;

    @FXML
    private TableColumn<CustomerVO, String> date;

    @FXML
    private TableColumn<CustomerVO, String> email;

    @FXML
    private TableColumn<CustomerVO, String> note;

    @FXML
    private TableColumn<CustomerVO, String> phone;

    @FXML
    private TableColumn<CustomerVO, String> sex;


    @FXML
    private TableColumn<CustomerVO, String> action;

    ObservableList<CustomerVO> list = FXCollections.observableArrayList();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try{
            customerCL = new CustomerController();
            ArrayList<CustomerVO> clients =  customerCL.getClients();
            if(clients != null){
                clients.forEach(client -> list.add(client));
            }
        } catch (SQLException throwables) {
            list = null;
            throwables.printStackTrace();
        }
        id.setCellValueFactory(new PropertyValueFactory<CustomerVO, Integer>("id"));
        name.setCellValueFactory(new PropertyValueFactory<CustomerVO, String>("name"));
        lastName.setCellValueFactory(new PropertyValueFactory<CustomerVO, String>("lastName"));
        sex.setCellValueFactory(new PropertyValueFactory<CustomerVO, String>("sex"));
        birthday.setCellValueFactory(new PropertyValueFactory<CustomerVO, String>("birthday"));
        phone.setCellValueFactory(new PropertyValueFactory<CustomerVO, String>("phone"));
        email.setCellValueFactory(new PropertyValueFactory<CustomerVO, String>("email"));
        note.setCellValueFactory(new PropertyValueFactory<CustomerVO, String>("note"));
        date.setCellValueFactory(new PropertyValueFactory<CustomerVO, String>("date"));

        Callback<TableColumn<CustomerVO, String>, TableCell<CustomerVO, String>> cellFoctory = (TableColumn<CustomerVO, String> param) -> {

            final TableCell<CustomerVO, String> cell = new TableCell<CustomerVO, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);

                    if (empty) {
                        setGraphic(null);
                        setText(null);

                    } else {
                        FontAwesomeIconView deleteIcon = new FontAwesomeIconView(FontAwesomeIcon.TRASH);
                        FontAwesomeIconView editIcon = new FontAwesomeIconView(FontAwesomeIcon.PENCIL_SQUARE);
//                        deleteIcon
                        deleteIcon.setFill(Paint.valueOf("#611156"));
                        deleteIcon.setCursor(Cursor.cursor("hand"));
                        deleteIcon.setSize("20");
//                        editIcon
                        editIcon.setCursor(Cursor.cursor("hand"));
                        editIcon.setFill(Paint.valueOf("#611156"));
                        editIcon.setSize("20");
//                    ACCIONES
                        deleteIcon.setOnMouseClicked((MouseEvent event) -> {
                            CustomerVO customer = table.getSelectionModel().getSelectedItem();
                            CustomerController customerController = new CustomerController();
                            customerController.deleteClient(customer.getId());

                            String message= "Estas seguro que quieres eliminar este cliente?";
                            FeedbackController feedback = new FeedbackController();
                            if(feedback.alertConfirmation(message)){
                                FXMLLoader editLoader = new FXMLLoader ();
                                editLoader.setLocation(DashboardController.class.getResource("Dashboard.fxml"));
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
                                Stage stage = (Stage) editIcon.getScene().getWindow();
                                stage.setScene(new Scene(parent));
                                stage.show();
                            }

                        });
                        editIcon.setOnMouseClicked((MouseEvent event) -> {
                           CustomerVO customer = table.getSelectionModel().getSelectedItem();

                            FXMLLoader editLoader = new FXMLLoader ();
                            editLoader.setLocation(DashboardController.class.getResource("Dashboard.fxml"));
                            try {
                                editLoader.load();
                            } catch (IOException ex) {
                                Logger.getLogger(ListCustomer.class.getName()).log(Level.SEVERE, null, ex);
                            }

                            dashboardCL = editLoader.getController();
                            try {
                                dashboardCL.editPage(customer);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            Parent parent = editLoader.getRoot();
                            Stage stage = (Stage) editIcon.getScene().getWindow();
                            stage.setScene(new Scene(parent));
                            stage.show();
                        });
                        HBox managebtn = new HBox(editIcon, deleteIcon);
                        managebtn.setStyle("-fx-alignment:center");
                        HBox.setMargin(deleteIcon, new Insets(2, 2, 0, 3));
                        HBox.setMargin(editIcon, new Insets(2, 3, 0, 2));
                        setGraphic(managebtn);
                        setText(null);
                    }
                }
            };
            return cell;
        };
        action.setCellFactory(cellFoctory);
        table.setItems(list);
    }
}
