package aplication.view.dashboard;

import aplication.controller.UserController;
import aplication.module.DAO.CustomerDAO;
import aplication.module.VO.CustomerVO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ListCustomer implements Initializable {

    private UserController userCL;
    private CustomerDAO customerDao;

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

    ObservableList<CustomerVO> list = FXCollections.observableArrayList();
    

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try{
            customerDao = new CustomerDAO();
            ArrayList<CustomerVO> clients =  customerDao.getCustomers();
            clients.forEach(client -> list.add(client));

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        id.setCellValueFactory(new PropertyValueFactory<CustomerVO, Integer>("id"));
        name.setCellValueFactory(new PropertyValueFactory<CustomerVO, String>("name"));
        lastName.setCellValueFactory(new PropertyValueFactory<CustomerVO, String>("lastName"));
        sex.setCellValueFactory(new PropertyValueFactory<CustomerVO, String>("sex"));
        birthday.setCellValueFactory(new PropertyValueFactory<CustomerVO, String>("birthday"));
        email.setCellValueFactory(new PropertyValueFactory<CustomerVO, String>("email"));
        phone.setCellValueFactory(new PropertyValueFactory<CustomerVO, String>("lastName"));
        note.setCellValueFactory(new PropertyValueFactory<CustomerVO, String>("note"));
        date.setCellValueFactory(new PropertyValueFactory<CustomerVO, String>("date"));

        table.setItems(list);
    }
}
