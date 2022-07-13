module system.management {
    requires javafx.graphics;
    requires javafx.fxml;
    requires javafx.controls;
    requires mysql.connector.java;
    requires java.sql;

    opens aplication;
    opens aplication.view.login;
    opens aplication.view.dashboard;
}