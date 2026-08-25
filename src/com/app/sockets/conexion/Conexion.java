package com.app.sockets.conexion;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import java.sql.DriverManager;

public class Conexion {
    
    Connection con = null;
    
    public Connection getConnection(){
    con = null;
    try{
    Class.forName("com.mysql.jdbc.Driver");
    con = (Connection) DriverManager.getConnection("jdbc:mysql://100.100.200.10:3306/chat?autoReconnect=true&useSSL=false","Jorge","123456789Aa.");
    
    }catch(Exception e){
        System.out.println("ERROR AL CONECTARSE"+e);
    }
    return con;
    }
    Statement createStatement(){
    throw new UnsupportedOperationException("NO SOPORTADO");
    
    }
}

