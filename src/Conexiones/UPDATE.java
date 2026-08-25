
package Conexiones;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

public class UPDATE {
    public static void main(String[] args) {
       try{
         Connection con;
         Conexion con1 = new Conexion();
         con = con1.getConnection();
         Statement st = con.createStatement();
         String sql = "select * from requisiciones where Folio is not null";
         ResultSet rs = st.executeQuery(sql);
         String datos[] = new String[10];
         int n = 0;
         while(rs.next()){
             datos[0] = rs.getString("Id");
             String sql2 = "update requisiciones set Entregado = ? where Id = ?";
             PreparedStatement pst2 = con.prepareStatement(sql2);
             
             pst2.setString(1, "SI");
             pst2.setString(2, datos[0]);
             
             n = pst2.executeUpdate();
         }
           System.out.println("Agregados: "+n);
       }catch(SQLException e){
        JOptionPane.showMessageDialog(null, "ERROR: "+e,"ERROR",JOptionPane.ERROR_MESSAGE);
    }
    }
}
