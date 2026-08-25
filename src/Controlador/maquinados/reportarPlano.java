package Controlador.maquinados;

import Conexiones.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;

public class reportarPlano {
    
    public String plano;

    public reportarPlano() {
        
    }
    
    public void enviarPlano(String departamento, String empleado, String plano, String proyecto){
        try{
            Connection con;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            String sql = "insert into reporteplanos (Departamento, Empleado, Fecha,Plano, Proyecto) values(?,?,?,?,?)";
            PreparedStatement pst = con.prepareStatement(sql);
            
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date d = new Date();
            String fecha = sdf.format(d);
            
            pst.setString(1, departamento);
            pst.setString(2, empleado);
            pst.setString(3, fecha);
            pst.setString(4, plano);
            pst.setString(5, proyecto);
            
            int n = pst.executeUpdate();
            
            if(n > 0){
                JOptionPane.showMessageDialog(null, "Plano reportado");
            }
            
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
}
