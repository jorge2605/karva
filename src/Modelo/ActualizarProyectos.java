package Modelo;

import Conexiones.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

public class ActualizarProyectos {
    
    public static void update(String id, Connection con, String proyectoFinal) throws SQLException{
        PreparedStatement pst = con.prepareStatement("update htpp set proyecto = ? where id = ?");
        pst.setString(1, proyectoFinal);
        pst.setString(2, id);
        
        int n = pst.executeUpdate();
        
        if(n < 1){
            JOptionPane.showMessageDialog(null, "no se actualizo " + id);
        }
    }
    
    public static void main(String[] args) {
        try {
            Connection con;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            String sql = "select * from htpp where Departamento like '1'";
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
                String proyecto = rs.getString("proyecto");
                System.out.println(proyecto);
//                String id = rs.getString("id");
//                String sql2 = "select proyecto, id from proyectos where proyecto like '" + proyecto + "%' order by id desc";
//                Statement st2 = con.createStatement();
//                ResultSet rs2 = st2.executeQuery(sql2);
//                while(rs2.next()){
//                    String proyectoFinal = rs2.getString("proyecto");
//                    System.out.println("Proyecto real: " + proyectoFinal + " - Proyecto: " + proyecto);
//                    System.out.println("--------------------------------------------------------------");
////                    update(id,con, proyectoFinal);
//                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error " + e);
        }
    }
}
