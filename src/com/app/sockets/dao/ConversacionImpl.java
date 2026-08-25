package com.app.sockets.dao;

import Conexiones.ConexionChat;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConversacionImpl implements ConversacionDAO {

    ConexionChat con1 = new ConexionChat();
    Connection con = null;
    
   
    @Override
    public void registrarConversacionA(String mensaje,String chat,String usuario) {
        try {
            con = con1.getConnection();
            String sql = "insert into `chat`.`" + chat + "` (`" + usuario + "`) values('"+mensaje+"')";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.executeUpdate();
        } catch (SQLException error) {
            Logger.getLogger(ConversacionImpl.class.getName()).log(Level.SEVERE, null, error);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ConversacionImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void registrarConversacionB(String mensaje,String chat,String usuario) {
        try {
            con = con1.getConnection();
            String sql = "insert into " + chat + "(" + usuario + ") values(?)";
            PreparedStatement pst = con.prepareStatement(sql);

            pst.setString(1, mensaje);
            pst.executeUpdate();
        } catch (SQLException error) {
            System.out.println(error);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ConversacionImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
