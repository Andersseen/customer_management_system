package config;

import java.io.*;
import java.net.MalformedURLException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


public class DatabaseConnection {
    public Connection connexion;

    public Connection getConnection(){
        String DBUser;
        String DBPass;
        String url;
        Properties properties = new Properties();
            try {
                InputStream file = new FileInputStream("src/config/.dbconfig.properties");
                properties.load(file);

                 DBUser = properties.getProperty("db.username");
                 DBPass = properties.getProperty("db.password");
                 url = properties.getProperty("db.url");

                Class.forName(properties.getProperty("db.driver"));
                connexion = DriverManager.getConnection(url, DBUser, DBPass);
            } catch (ClassNotFoundException | SQLException | MalformedURLException | FileNotFoundException e) {
                e.printStackTrace();
                connexion =null;
            } catch (IOException e) {
                e.printStackTrace();
            }
        return connexion;
    }

    public void disconnect(){
        connexion =null;
    }
}
