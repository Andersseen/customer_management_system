module system.management {
    requires javafx.graphics;
    requires javafx.fxml;
    requires javafx.controls;
    requires mysql.connector.java;
    requires java.sql;
    requires de.jensd.fx.glyphs.commons;
    requires de.jensd.fx.glyphs.fontawesome;
    requires org.apache.poi.poi;
    requires org.apache.poi.ooxml;



    opens aplication;
    opens aplication.view.login;
    opens aplication.view.dashboard;
    opens aplication.view.dashboard.listPage;
    opens aplication.view.dashboard.addPage;
    opens aplication.view.dashboard.editPage;
    opens aplication.view.dashboard.historicalPage;
    opens aplication.view.dashboard.loader;
    opens aplication.module.VO ;
    opens aplication.module.DAO ;

}