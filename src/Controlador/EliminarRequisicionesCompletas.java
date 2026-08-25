package Controlador;

import Conexiones.Conexion;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Stack;
import javax.swing.JOptionPane;
import org.apache.poi.ss.usermodel.Workbook;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class EliminarRequisicionesCompletas {
    
    public static void extraer(){
        try{
            Connection con;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            try (FileInputStream fis = new FileInputStream(new File("C:\\Users\\USUARIO 1\\Documents\\Jorge\\3i\\Id actualizados.xlsx"));
                 Workbook workbook = new XSSFWorkbook(fis)) {

                Sheet sheet = workbook.getSheetAt(0);

                for (Row row : sheet) {
                    for (Cell cell : row) {
                        String cellValue = cell.toString();
    //                    System.out.print(cellValue.substring(0,cellValue.indexOf(".0")) + "\t");
//                        actualizarId(cellValue.substring(0,cellValue.indexOf(".0")));
    //                        System.out.println(cellValue.substring(0,cellValue.indexOf(".0")));
                        String id = cellValue.substring(0,cellValue.indexOf(".0"));
                        String sql2 = "select * from requisiciones where Id like '"+id+"'";
                        ResultSet rs = st.executeQuery(sql2);
                        String oc = null;
                        while(rs.next()){
                            oc = rs.getString("OC");
                        }
                        if(oc != null){
                            if(oc.equals("PENDIENTE")){
                                oc = null;
                            }
                        }
                        String sql = "update requisiciones set Llego = ?, OC = ? where Id = ?";
                        PreparedStatement pst = con.prepareStatement(sql);

                        pst.setString(1, null);
                        pst.setString(2, oc);
                        pst.setString(3, id);

                        int n = pst.executeUpdate();

                        if(n > 0){
                            System.out.println("Se actualizo-"+id);
                        }else{
                            System.err.println("No se actualizo-"+id);
                        }
                    }
    //                System.out.println(); // Nueva línea después de cada fila
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }catch(SQLException e){
            
        }
    }
    
    public static void actualizar(){
        try{
            Connection con;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            String sql = "select Id, Proyecto,Facturado, Estatus  from Proyectos where Estatus like 'EN PROCESO'";
            ResultSet rs = st.executeQuery(sql);
//            Stack<String> pila = new Stack<>();
            String anidado = "";
            int cont = 0;
            while(rs.next()){
                String proy = rs.getString("Proyecto");
                if(cont == 0){
                    anidado += " Proyecto != '"+proy+"'";
                }else{
                    anidado += " and Proyecto != '"+proy+"'";
                }
                cont++;
            }
            String sql2 = "select * from requisiciones where"+anidado+" and Llego is null and Proyecto != 'SEGURIDAD' and Proyecto != 'MATERIAL DE OFICINA'"
                    + " and Proyecto != 'MATERIAL DE MANTENIMIENTO' and Proyecto != 'MATERIAL DE LIMPIEZA'  and Proyecto != 'HERRAMIENTAS' ";
            Statement st2 = con.createStatement();
            ResultSet rs2 = st2.executeQuery(sql2);
            cont = 0;
            while(rs2.next()){
//                System.out.println(rs2.getString("Id")+"-"+rs2.getString("Codigo") + " - "+rs2.getString("OC"));
                String id = rs2.getString("Id");
                String oc = rs2.getString("OC");
                String sql3 = "update requisiciones set Llego = ?, OC = ? where Id = ?";
                PreparedStatement pst = con.prepareStatement(sql3);
                String orden;
                if(oc == null){
                    orden = "PENDIENTE";
                }else{
                    orden = oc;
                }
                
                pst.setString(1, "SI");
                pst.setString(2, orden);
                pst.setString(3, id);
                
                int n = pst.executeUpdate();
                if(n <= 0){
                    System.err.println("- no se actualizo"+id);
                }else{
                    System.out.println("- Se actualizo"+id);
                }
                
                cont++;
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "ERROR: "+e,"ERROR",JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public static void main(String[] args) {
        actualizar();
    }
}
