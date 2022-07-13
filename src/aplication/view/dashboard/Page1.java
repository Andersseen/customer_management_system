package aplication.view.dashboard;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class Page1 implements Initializable {
    @FXML
    private TableView<Customer> table;


    @FXML
    private TableColumn<Customer, Integer> id;
    @FXML
    private TableColumn<Customer, String> name;
    @FXML
    private TableColumn<Customer, String> lastName;

    ObservableList<Customer> list = FXCollections.observableArrayList(
            new Customer(1, "Andrey", "Pap"),
            new Customer(2, "Jhon", "Doe")
            );

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        id.setCellValueFactory(new PropertyValueFactory<Customer, Integer>("id"));
        name.setCellValueFactory(new PropertyValueFactory<Customer, String>("name"));
        lastName.setCellValueFactory(new PropertyValueFactory<Customer, String>("lastName"));

        table.setItems(list);
    }
}
