package Conexiones;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;

public class ConexionChat {

    Connection con = null;

    public Connection getConnection() throws ClassNotFoundException, SQLException {
        con = null;
//        con = (Connection) DriverManager.getConnection("jdbc:mysql://192.168.100.40:3306/chat?autoReconnect=true&useSSL=false","Jorge","123456789Aa.");
        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/chat?useSSL=false&autoReconnect=true&allowPublicKeyRetrieval=true", "Jorge", "123456789Aa.");
        return con;
    }
}
