module system.management {
    requires javafx.graphics;
    requires javafx.fxml;
    requires javafx.controls;
    requires mysql.connector.java;
    requires java.sql;

    opens aplication;
    opens aplication.login;
    opens aplication.dashboard;
}