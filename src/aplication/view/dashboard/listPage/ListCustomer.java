package aplication.view.dashboard.listPage;

import aplication.controller.CustomerController;
import aplication.controller.FeedbackController;
import aplication.controller.HistoricalController;
import aplication.module.VO.CustomerVO;
import aplication.module.VO.HistoricalVO;
import aplication.view.dashboard.DashboardController;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Paint;
import javafx.util.Callback;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ListCustomer implements Initializable {

    private CustomerController customerCL;
    private DashboardController dashboardCL;
    private FeedbackController feedback;
    private String message;

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

                    } else {
                        FontAwesomeIconView deleteIcon = new FontAwesomeIconView(FontAwesomeIcon.TRASH);
                        FontAwesomeIconView editIcon = new FontAwesomeIconView(FontAwesomeIcon.PENCIL_SQUARE);
                        FontAwesomeIconView historicalIcon = new FontAwesomeIconView(FontAwesomeIcon.FILE_TEXT);

//                        deleteIcon
                        deleteIcon.setFill(Paint.valueOf("#611156"));
                        deleteIcon.setCursor(Cursor.cursor("hand"));
                        deleteIcon.setSize("20");
//                        editIcon
                        editIcon.setCursor(Cursor.cursor("hand"));
                        editIcon.setFill(Paint.valueOf("#611156"));
                        editIcon.setSize("20");
//                        historicalIcon
                        historicalIcon.setCursor(Cursor.cursor("hand"));
                        historicalIcon.setFill(Paint.valueOf("#611156"));
                        historicalIcon.setSize("20");
//                    ACCIONES
                        //**************
                        historicalIcon.setOnMouseClicked((MouseEvent event) -> {
                            CustomerVO customer = table.getSelectionModel().getSelectedItem();

                            HistoricalController historicalCL = new HistoricalController();
                            HistoricalVO historicalVO = historicalCL.controlGettingHistorical(customer.getId());

                            feedback = new FeedbackController();
                            dashboardCL = new DashboardController();
                            if(historicalVO != null){
                                message = "¿Quieres editar este historico?";
                                if(feedback.alertConfirmation(message)){
                                    dashboardCL.switchDashboardWithCustomer(dashboardCL, historicalIcon, customer,"HistoricalPageTrue");
                                }else {
                                    dashboardCL.returnToRefreshDashboard(dashboardCL, historicalIcon);
                                }
                            }else{
                                message = "Este paciente no tiene historico. ¿Quieres añadir nuevo?";
                                if(feedback.alertConfirmation(message)){
                                    dashboardCL.switchDashboardWithCustomer(dashboardCL, historicalIcon, customer,"HistoricalPageFalse");
                                }else {
                                    dashboardCL.returnToRefreshDashboard(dashboardCL, historicalIcon);
                                }
                            }
                        });
                        //**************
                        deleteIcon.setOnMouseClicked((MouseEvent event) -> {
                            CustomerVO customer = table.getSelectionModel().getSelectedItem();
                            CustomerController customerController = new CustomerController();
                            customerController.deleteClient(customer.getId());

                            String message= "Estas seguro que quieres eliminar este cliente?";
                            FeedbackController feedback = new FeedbackController();
                            if(feedback.alertConfirmation(message)){
                                DashboardController dashboardCL = new DashboardController();
                                dashboardCL.returnToRefreshDashboard(dashboardCL,deleteIcon );
                            }
                        });
                        //**************
                        editIcon.setOnMouseClicked((MouseEvent event) -> {
                           CustomerVO customer = table.getSelectionModel().getSelectedItem();
                            DashboardController dashboardCL = new DashboardController();
                            dashboardCL.switchDashboardWithCustomer(dashboardCL, editIcon, customer,"EditCustomer");

                        });
                        //**************
                        HBox managebtn = new HBox(historicalIcon,editIcon, deleteIcon);
                        managebtn.setStyle("-fx-alignment:center");
                        HBox.setMargin(historicalIcon, new Insets(2, 2, 0, 2));
                        HBox.setMargin(deleteIcon, new Insets(2, 2, 0, 2));
                        HBox.setMargin(editIcon, new Insets(2, 2, 0, 2));
                        setGraphic(managebtn);
                    }
                    setText(null);
                }
            };
            return cell;
        };
        action.setCellFactory(cellFoctory);
        table.setItems(list);
    }

}
