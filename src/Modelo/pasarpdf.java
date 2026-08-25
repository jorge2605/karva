package Modelo;

import Conexiones.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

public class pasarpdf {
    public static void main(String[] args) {
        try{
            Connection con;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            String sql = "select Plano,Pdf from Planos";
            String sql2 = "update pdfplanos set Plano = ?, Pdf = ? where Id = ?";
            PreparedStatement pst = con.prepareStatement(sql2);
            ResultSet rs = st.executeQuery(sql);
            byte[] b;
            int cont = 677;
            while(rs.next()){
                b = rs.getBytes("Pdf");
                String plano = rs.getString("Plano");
                System.out.println(plano);
                pst.setString(1, plano);
                pst.setBytes(2, b);
                pst.setInt(3, cont);
                pst.executeUpdate();
                cont++;
                System.out.println(cont);
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "ERROR: "+e,"ERROR",JOptionPane.ERROR_MESSAGE);
        }
    }
}
