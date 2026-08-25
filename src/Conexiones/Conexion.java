package Conexiones;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
    
    Connection con = null;
    
    public Connection getConnection() throws SQLException {
    con = null;
//        con = (Connection) DriverManager.getConnection("jdbc:mysql://192.168.100.40:3306/towi?autoReconnect=true&useSSL=false","Jorge","123456789Aa.");
//        con = (Connection) DriverManager.getConnection("jdbc:sqlite:C:\\Users\\Jorge Santacruz\\Documents\\towi.db");
        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/towi?useSSL=false&autoReconnect=true&allowPublicKeyRetrieval=true","Jorge","123456789Aa.");
    
    return con;
    }
}

