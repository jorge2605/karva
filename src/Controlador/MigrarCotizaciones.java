package Controlador;

import Conexiones.Conexion;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;

public class MigrarCotizaciones {
    public static void main(String[] args) {
        try{
            Connection con; 
            Conexion con1= new Conexion();
            con = con1.getConnection();
            String sql = "select Id, Cotizacion from requisicion where Cotizacion is not null";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            
//            String sql2 = "update into requisicionpdf (NumRequisicion, Pdf, Fecha, Nombre) values (?,?,?,?)";
//            PreparedStatement pst = con.prepareStatement(sql2);
            
            String sql3 = "update requisicion set NumeroCotizacion = ? where Id = ?";
            PreparedStatement pst = con.prepareStatement(sql3);

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date d = new Date();
            
            while(rs.next()){
                pst.setString(1, "pdf.pdf");
                pst.setString(2, rs.getString("Id"));
                pst.executeUpdate();
//                String requi = rs.getString("Id");
//                InputStream pdf = rs.getBinaryStream("Cotizacion");
//                
//                pst.setString(1, requi);
//                pst.setBinaryStream(2, pdf);
//                pst.setString(3, sdf.format(d));
//                pst.setString(4, "pdf.pdf");
//                
//                pst.executeUpdate();
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Error "+e);
        }
    }
}
