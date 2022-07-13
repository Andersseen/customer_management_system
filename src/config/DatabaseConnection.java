package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    public Connection conexion;

    public Connection getConnection(){
        String DBName = "db_management";
        String DBUser = "root";
        String DBPass = "root";
        String url = "jdbc:mysql://localhost:3306/" + DBName;

            try {
                Class.forName("com.mysql.jdbc.Driver");
                conexion = DriverManager.getConnection(url, DBUser, DBPass);
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }

        return conexion;

    }

    public void desconnect(){
        conexion=null;
    }
}
