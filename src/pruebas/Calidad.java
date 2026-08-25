package pruebas;
import Conexiones.Conexion;
import Controlador.maquinados.reportarPlano;
import Controlador.maquinados.revisarPlanos;
import VentanaEmergente.Maquinados.elegirRevision;
import java.awt.Color;
import java.awt.Font;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

public final class Calidad extends InternalFrameImagen {
    public  String fechaInicio;
    public String o;
    String pl;
    int contador = 0;
    int contReporte;
    
    elegirRevision elegirRevision;
    
    public void limpiarTabla(){
        Tabla1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
            },
            new String [] {
                "PRIORIDAD", "NUMERO DE PLANO", "PROYECTO"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        Tabla1.setRowHeight(25);
        Tabla1.getTableHeader().setFont(new Font("Roboto", Font.BOLD, 16));
        Tabla1.setFont(new java.awt.Font("Roboto", 0, 14));
        Tabla1.getTableHeader().setOpaque(false);
        Tabla1.getTableHeader().setBackground(new Color(0,102,204));
        Tabla1.getTableHeader().setForeground(new Color(255, 255, 255));
        Tabla1.setRowHeight(25);
        Tabla1.setShowGrid(false);
        
        jScrollPane1.getViewport().setBackground(Color.white);
        jScrollPane1.setForeground(Color.white);
        jScrollPane1.getViewport().setForeground(Color.white);
        jScrollPane1.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(254, 254, 254)));
        jScrollPane1.setBackground(Color.white);
    }
    
    public void limpiarTabla2(){
        Tabla2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
            },
            new String [] {
                "PROYECTO", "PLANO"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        Tabla2.setRowHeight(25);
        Tabla2.getTableHeader().setFont(new Font("Roboto", Font.BOLD, 16));
        Tabla2.setFont(new java.awt.Font("Roboto", 0, 14));
        Tabla2.getTableHeader().setOpaque(false);
        Tabla2.getTableHeader().setBackground(new Color(0,102,204));
        Tabla2.getTableHeader().setForeground(new Color(255, 255, 255));
        Tabla2.setRowHeight(25);
        Tabla2.setShowGrid(false);
        
        jScrollPane2.getViewport().setBackground(Color.white);
        jScrollPane2.setForeground(Color.white);
        jScrollPane2.getViewport().setForeground(Color.white);
        jScrollPane2.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(254, 254, 254)));
        jScrollPane2.setBackground(Color.white);
    }

    public void verDatos(){
        DefaultTableModel miModelo = (DefaultTableModel) Tabla1.getModel();
        try{
            Connection con;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            String datos[] = new String[8];
            String sql = "select * from Calidad where Terminado like 'NO'";
            Statement st = con.createStatement(); 
            ResultSet rs = st.executeQuery(sql);

            while(rs.next()){
                datos[0]=rs.getString("Prioridad");
                datos[1]=rs.getString("Proyecto");
                datos[2]=rs.getString("Plano");
                miModelo.addRow(datos);
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "NO SE PUEDEN MOSTRAR LOS DATOS","ADVERTENCIA",JOptionPane.WARNING_MESSAGE);
        }
        TableRowSorter<TableModel> elQueOrdena = new TableRowSorter<TableModel>(miModelo);
        Tabla1.setRowSorter(elQueOrdena);
    }

    public void fechaFinal(){
        Date fechaIn = new Date();
        SimpleDateFormat fec = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        fechaInicio = fec.format(fechaIn);
    }

    public void fecha(){
        Date fe = new Date();
        SimpleDateFormat nuevo = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String fec = nuevo.format(fe);
        txtFecha.setText(fec);
    }
    
    public void tabla(){
        JOptionPane.showMessageDialog(this, "DATOS GUARDADOS CORRECTAMENTE");
        fecha();
        txtFecha.setText(""); 
        limpiarTabla();
        verDatos();

        txtProyecto.setText("");
        txtFecha.setText("");
    }
    
    public void borrar(){
        txtProyecto.setText("");
        txtFecha.setText("");
    }
    
    public void datos(String plano) {
        Date fe = new Date();
        SimpleDateFormat nuevo = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String fec = nuevo.format(fe);
        JOptionPane.showMessageDialog(this, "PLANO EMPEZADO");
        limpiarTabla();
        verDatos();
        txtProyecto.setText(plano);
        txtCodigo.setText("");
        txtFecha.setText(fec);
    }
    
    public void guardar(String revision){
        Date fecha = new Date();
        SimpleDateFormat fec1 = new SimpleDateFormat ("dd/MM/yyyy HH:mm:ss");
        String fec = fec1.format(fecha);
        
        if(Tabla2.getRowCount() < 0){
            JOptionPane.showMessageDialog(this, "DEBES SELECCIONAR PLANO(S)","ERROR",JOptionPane.ERROR_MESSAGE);
        }else{
            try{
                Connection con = null;
                Conexion con1 = new Conexion();
                con = con1.getConnection();

                Statement st = con.createStatement();
                Statement st1 = con.createStatement();
                Statement st2 = con.createStatement();
                Statement st3 = con.createStatement();
                Statement st4 = con.createStatement();
                Statement st5 = con.createStatement();
                Statement st6 = con.createStatement();
                String sql =  "UPDATE Calidad SET Proyecto = ?, Plano = ?, FechaInicio = ?, FechaFinal = ?, Terminado = ?, Estado = ?, Tratamiento = ?, Cronometro = ?, Prioridad = ?,Empleado = ? WHERE Proyecto = ?";
                String sq8 =  "UPDATE Calidad SET Terminado = ?, Tratamiento = ? WHERE Proyecto = ?";
                String sql1 = "insert into CNC (Proyecto,Plano,FechaInicio,FechaFinal,Terminado,Estado,Cronometro,Prioridad) values(?,?,?,?,?,?,?,?)";
                String sql2 = "insert into Fresadora (Proyecto,Plano,FechaInicio,FechaFinal,Terminado,Estado,Cronometro,Prioridad) values(?,?,?,?,?,?,?,?)";
                String sql4 = "insert into Torno (Proyecto,Plano,FechaInicio,FechaFinal,Terminado,Estado,Cronometro,Prioridad) values(?,?,?,?,?,?,?,?)";
                String sql5 = "insert into Scrap (Proyecto,NumeroEmpleado,Fecha) values (?,?,?)";
                String sql6 = "insert into Acabados (Proyecto,Plano,FechaInicio,FechaFinal,Terminado,Estado,Cronometro,Prioridad) values(?,?,?,?,?,?,?,?)";
                String sql7 = "insert into Trata (Proyecto,Plano,FechaInicio,FechaFinal,Terminado,Estado,Prioridad) values(?,?,?,?,?,?,?)";
                PreparedStatement pst = con.prepareStatement(sql);
                PreparedStatement pst1 = con.prepareStatement(sql1);
                PreparedStatement pst2 = con.prepareStatement(sql2);
                PreparedStatement pst4 = con.prepareStatement(sql4);
                PreparedStatement pst5 = con.prepareStatement(sql5);
                PreparedStatement pst6 = con.prepareStatement(sql6);
                PreparedStatement pst7 = con.prepareStatement(sql7);
                PreparedStatement ps8 = con.prepareStatement(sq8);

                String borrarTorno = "delete from Calidad where Proyecto = ?";
                String borrarCnc = "delete from CNC where Proyecto = ?";
                String borrarFresa = "delete from Fresadora where Proyecto = ?";
                String borrarAcabados = "delete from Torno where Proyecto = ?";
                String borrarCortes = "UPDATE Datos SET Proyecto = ?, Plano = ?, FechaInicio = ?, FechaFinal = ?, Terminado = ?, Estado = ?,Cronometro = ?, Prioridad = ? WHERE Proyecto = ?";
                String borrarCalidad = "delete from Acabados where Proyecto = ?";
                String borrarTratamiento = "delete from Trata where Proyecto = ?";
                
                PreparedStatement ps1 = con.prepareStatement(borrarTorno);
                PreparedStatement ps2 = con.prepareStatement(borrarCnc);
                PreparedStatement ps3 = con.prepareStatement(borrarFresa);
                PreparedStatement ps4 = con.prepareStatement(borrarAcabados);
                PreparedStatement ps5 = con.prepareStatement(borrarCortes);
                PreparedStatement ps6 = con.prepareStatement(borrarCalidad);
                PreparedStatement ps7 = con.prepareStatement(borrarTratamiento);

                String ver = "select * from CNC where Proyecto like '"+txtProyecto.getText()+"'";
                String ver1 = "select * from Fresadora where Proyecto like '"+txtProyecto.getText()+"'";
                String ver2 = "select * from Torno where Proyecto like '"+txtProyecto.getText()+"'";
                String ver3 = "select * from Acabados where Proyecto like '"+txtProyecto.getText()+"'";
                String ver4 = "select * from Trata where Proyecto like '"+txtProyecto.getText()+"'";
                
                ResultSet rs = st.executeQuery(ver);
                ResultSet rs1 = st1.executeQuery(ver1);
                ResultSet rs2 = st2.executeQuery(ver2);
                ResultSet rs3 = st3.executeQuery(ver3);
                ResultSet rs4 = st4.executeQuery(ver4);
                
                String ac = "update CNC set Proyecto = ?, Plano = ?, FechaInicio = ?, FechaFinal = ?, Terminado = ?, Estado = ?, Cronometro = ?,Prioridad = ? where Proyecto = ?";
                String ac1 = "update Fresadora set Proyecto = ?, Plano = ?, FechaInicio = ?, FechaFinal = ?, Terminado = ?, Estado = ?, Cronometro = ?,Prioridad = ? where Proyecto = ?";
                String ac2 = "update Torno set Proyecto = ?, Plano = ?, FechaInicio = ?, FechaFinal = ?, Terminado = ?, Estado = ?, Cronometro = ?,Prioridad = ? where Proyecto = ?";
                String ac3 = "update Acabados set Proyecto = ?, Plano = ?, FechaInicio = ?, FechaFinal = ?, Terminado = ?, Estado = ?, Cronometro = ?,Prioridad = ? where Proyecto = ?";
                String ac4 = "update Trata set Proyecto = ?, Plano = ?, FechaInicio = ?, FechaFinal = ?, Terminado = ?, Estado = ?,Prioridad = ? where Proyecto = ?";
                
                PreparedStatement act = con.prepareStatement(ac);
                PreparedStatement act1 = con.prepareStatement(ac1);
                PreparedStatement act2 = con.prepareStatement(ac2);
                PreparedStatement act3 = con.prepareStatement(ac3);
                PreparedStatement act4 = con.prepareStatement(ac4);
                String eCalidad = "select * from Calidad where Proyecto like '"+txtProyecto.getText()+"'";
                String eCortes = "select * from Datos where Proyecto like '"+txtProyecto.getText()+"'";
                ResultSet eC = st5.executeQuery(eCalidad);
                ResultSet eCo = st6.executeQuery(eCortes);
                String acabados[] = new String[10];
                String cnc[] = new String[10];
                String fresa[] = new String[10];
                String torno[] = new String[10];
                String calidad[] = new String[10];
                String trata[] = new String[10];
                String cortes[] = new String[10];
                while(eCo.next()){
                cortes[3] = eCo.getString("Cronometro");
                }
                while(eC.next()){
                    calidad[1] = eC.getString("Estado");
                    calidad[2] = eC.getString("Tratamiento");
                    calidad[3] = eC.getString("Cronometro");
                    calidad[4] = eC.getString("Plano");
                    calidad[5] = eC.getString("Prioridad");
                    calidad[6] = eC.getString("FechaInicio");
                }
                while(rs.next()){
                    cnc[1] = rs.getString("Proyecto");
                    cnc[3] = rs.getString("Cronometro");
                }
                while(rs1.next()){
                    fresa[1] = rs1.getString("Proyecto");
                    fresa[3] = rs1.getString("Cronometro");
                }
                while(rs2.next()){
                    torno[1] = rs2.getString("Proyecto");
                    torno[3] = rs2.getString("Cronometro");
                }
                while(rs3.next()){
                    acabados[1] = rs3.getString("Proyecto");
                    acabados[3] = rs3.getString("Cronometro");
                }
                while(rs4.next()){
                    trata[1] = rs4.getString("Proyecto");
                    trata[2] = rs4.getString("FechaInicio");
                    
                }

                pst.setString(1, txtProyecto.getText());
                pst.setString(2, calidad[4]);
                pst.setString(3, txtFecha.getText());
                pst.setString(4, fec);
                pst.setString(5, "SI");
                pst.setString(6, calidad[1]);
                pst.setString(7, calidad[2]);
                pst.setString(8, calidad[3]);
                pst.setString(9, calidad[5]);
                pst.setString(10, txtNumero.getText());
                pst.setString(11, txtProyecto.getText());

                if(cmbEnviar.getSelectedItem() == "SELECCIONAR"){
                    JOptionPane.showMessageDialog(this, "DEBE ESCOGER UNA OPCION","",JOptionPane.ERROR_MESSAGE);
                }else{
                    switch (cmbEnviar.getSelectedIndex()) {
                        case 1:
                            for (int i = 0; i < Tabla2.getRowCount(); i++) {
                            if(fresa[1] == (null)){
                                
                                
                                pst2.setString(1, Tabla2.getValueAt(i, 1).toString());
                                pst2.setString(2, calidad[4]);
                                pst2.setString(3, "");
                                pst2.setString(4, "");
                                pst2.setString(5, "NO");
                                pst2.setString(6, calidad[1]);
                                pst2.setString(7, fresa[3]);
                                pst2.setString(8, calidad[5]);
                                
                                int n = pst2.executeUpdate();
                                int n1 = pst.executeUpdate();
                                if (n1 > 0 && n > 0 )
                                {
                                    tabla();
                                    borrar();
                                }
                                
                            }else{
                                
                                act1.setString(1, Tabla2.getValueAt(i, 1).toString());
                                act1.setString(2, calidad[4]);
                                act1.setString(3, "");
                                act1.setString(4, "");
                                act1.setString(5, "NO");
                                act1.setString(6, calidad[1]);
                                act1.setString(7, fresa[3]);
                                act1.setString(8, calidad[5]);
                                act1.setString(9, Tabla2.getValueAt(i, 1).toString());
                                int n = act1.executeUpdate();
                                int n1 = pst.executeUpdate();
                                if (n > 0 && n1 >0){
                                    tabla();
                                    borrar();
                                }
                                
                            }
                            break;
                            }
                        case 2:
                            for (int i = 0; i < Tabla2.getRowCount(); i++) {
                            if(cnc[1] == (null)){
                                pst1.setString(1, Tabla2.getValueAt(i, 1).toString());
                                pst1.setString(2, calidad[4]);
                                pst1.setString(3, "");
                                pst1.setString(4, "");
                                pst1.setString(5, "NO");
                                pst1.setString(6, calidad[1]);
                                pst1.setString(7, cnc[3]);
                                pst1.setString(8, calidad[5]);
                                int n = pst1.executeUpdate();
                                int n1 = pst.executeUpdate();
                                if (n > 0 && n1 > 0)
                                {
                                    tabla();
                                    borrar();
                                }
                            }else{
                                act.setString(1, Tabla2.getValueAt(i, 1).toString());
                                act.setString(2, calidad[4]);
                                act.setString(3, "");
                                act.setString(4, "");
                                act.setString(5, "NO");
                                act.setString(6, calidad[1]);
                                act.setString(7, cnc[3]);
                                act.setString(8, calidad[5]);
                                act.setString(9, Tabla2.getValueAt(i, 1).toString());
                                int n = act.executeUpdate();
                                int n1 = pst.executeUpdate();
                                if (n1 > 0 && n > 0){
                                    tabla();
                                    borrar();
                                }
                            }       
                            break;
                            }
                        case 3:
                            for (int i = 0; i < Tabla2.getRowCount(); i++) {
                                
                            
                            if(torno[1] == (null)){
                                pst4.setString(1, Tabla2.getValueAt(i, 1).toString());
                                pst4.setString(2, calidad[4]);
                                pst4.setString(3, "");
                                pst4.setString(4, "");
                                pst4.setString(5, "NO");
                                pst4.setString(6, calidad[1]);
                                pst4.setString(7, torno[3]);
                                pst4.setString(8, calidad[5]);
                                int n = pst4.executeUpdate();
                                int n1 = pst.executeUpdate();
                                if (n1 > 0 && n > 0)
                                {
                                    tabla();
                                    borrar();
                                }
                            }else{
                                act2.setString(1, Tabla2.getValueAt(i, 1).toString());
                                act2.setString(2, calidad[4]);
                                act2.setString(3, "");
                                act2.setString(4, "");
                                act2.setString(5, "NO");
                                act2.setString(6, calidad[1]);
                                act2.setString(7, torno[3]);
                                act2.setString(8, calidad[5]);
                                act2.setString(9, Tabla2.getValueAt(i, 1).toString());
                                int n = act2.executeUpdate();
                                int n1 = pst.executeUpdate();
                                if (n1 > 0 && n > 0){
                                    tabla();
                                    borrar();
                                }
                            }       
                            break;
                            }
                        case 4:
                            ps1.setString(1, (txtProyecto.getText()));
                            ps2.setString(1, (txtProyecto.getText()));
                            ps3.setString(1, (txtProyecto.getText()));
                            ps4.setString(1, (txtProyecto.getText()));
                            ps6.setString(1, (txtProyecto.getText()));
                            ps7.setString(1, (txtProyecto.getText()));
                            ps5.setString(1, txtProyecto.getText());
                            ps5.setString(2, calidad[4]);
                            ps5.setString(3, "");
                            ps5.setString(4, "");
                            ps5.setString(5, "NO");
                            ps5.setString(6, calidad[1]);
                            ps5.setString(7, cortes[3]);
                            ps5.setString(8, calidad[5]);
                            ps5.setString(9, txtProyecto.getText());
                            pst5.setString(1, txtProyecto.getText());
                            pst5.setString(2, txtNumero.getText());
                            pst5.setString(3, fec);
                            int n4 = pst.executeUpdate();
                            int n5 = ps1.executeUpdate();
                            int n6 = ps2.executeUpdate();
                            int n7 = ps3.executeUpdate();
                            int n8 = ps4.executeUpdate();
                            int n9 = ps5.executeUpdate();
                            int n10 = ps6.executeUpdate();
                            int n11 = pst5.executeUpdate();
                            int n12 = ps7.executeUpdate();
                            JOptionPane.showMessageDialog(this, "DATOS GUARDADOS","",JOptionPane.INFORMATION_MESSAGE);
                            limpiarTabla();
                            verDatos();
                            if (n4 > 0 && n5 > 0 && n6 > 0 && n7 > 0 && n8 > 0 && n9 > 0 && n10 > 0 && n11 > 0 && n12 > 0)
                            {
                                limpiarTabla();
                                verDatos();
                            }       break;
                        case 5:
                            if(acabados[1] == (null)){
                                pst6.setString(1, txtProyecto.getText());
                                pst6.setString(2, calidad[4]);
                                pst6.setString(3, "");
                                pst6.setString(4, "");
                                pst6.setString(5, "NO");
                                pst6.setString(6, calidad[1]);
                                pst6.setString(7, calidad[5]);
                                int n = pst6.executeUpdate();
                                int n1 = pst.executeUpdate();
                                if (n1 > 0 && n > 0)
                                {
                                    tabla();
                                    borrar();
                                }
                            }else{
                                act3.setString(1, txtProyecto.getText());
                                act3.setString(2, calidad[4]);
                                act3.setString(3, "");
                                act3.setString(4, "");
                                act3.setString(5, "NO");
                                act3.setString(6, calidad[1]);
                                act3.setString(7, calidad[5]);
                                act3.setString(8, txtProyecto.getText());
                                int n = act3.executeUpdate();
                                int n1 = pst.executeUpdate();
                                if (n1 > 0 && n > 0){
                                    tabla();
                                    borrar();
                                }
                            }       break;
                        case 6:
                            if(trata[1] == (null)){
                                for (int i = 0; i <= Tabla2.getRowCount(); i++) {
                                    pst7.setString(1, Tabla2.getValueAt(i, 1).toString());
                                    pst7.setString(2, calidad[4]);
                                    pst7.setString(3, "");
                                    pst7.setString(4, "");
                                    pst7.setString(5, "NO");
                                    pst7.setString(6, calidad[1]);
                                    pst7.setString(7, calidad[5]);
                                    int n = pst.executeUpdate();
                                    int n1 = pst7.executeUpdate();
                                    if (n > 0 && n1 > 0)
                                    {
                                        tabla();
                                        borrar();
                                    }
                                }
                                
                            }else{
                                for (int i = 0; i <= Tabla2.getRowCount(); i++) {
                                    act4.setString(1, Tabla2.getValueAt(i, 1).toString());
                                    act4.setString(2, calidad[4]);
                                    act4.setString(3, "");
                                    act4.setString(4, "");
                                    act4.setString(5, "NO");
                                    act4.setString(6, calidad[1]);
                                    act4.setString(7, calidad[5]);
                                    act4.setString(8, Tabla2.getValueAt(i, 1).toString());
                                    int n = act4.executeUpdate();
                                    int n1 = pst.executeUpdate();
                                    if (n1 > 0 && n > 0){
                                        tabla();
                                        borrar();
                                    }
                                }
                                
                            }       break;
                        case 7:
                            int n = 0;
                            for (int i = 0; i <= Tabla2.getRowCount(); i++) {
                                
                                ps8.setString(1, "SI");
                                ps8.setString(2, "NO");
                                ps8.setString(3, Tabla2.getValueAt(i, 1).toString());
                                
                                n = ps8.executeUpdate();
                                if (n > 0){
                                  tabla();
                                borrar();  
                                    
                                }
                            }   
                            
                                
                                DefaultTableModel miModelo = (DefaultTableModel) Tabla2.getModel();
                                String titulos[] = {"PROYECTO","PLANO"};
                                miModelo = new DefaultTableModel(null,titulos);
                                Tabla2.setModel(miModelo);
                              
                            break;
                        default:
                            break;
                    }
                }
            }catch(SQLException e){
                JOptionPane.showMessageDialog(null, "NO SE PUEDE ENVIAR INFORMACION"+e,"ERROR",JOptionPane.ERROR_MESSAGE);
            }
        

        if(cmbEnviar.getSelectedItem().equals("TRATAMIENTO")){
            if(cmbProv.getSelectedItem().equals("PROVEEDOR")){
            JOptionPane.showMessageDialog(this, "DEBES SELECCIONAR UN PROVEEDOR","ADVERTENCIA",JOptionPane.WARNING_MESSAGE);
            }else{
                try{
                Connection con = null;
                Conexion con1 = new Conexion();
                con = con1.getConnection();
                Statement st = con.createStatement();
                String sql = "select * from Tratas";
                String sql1 = "insert into Tratas (Fecha,Cantidad,Peso,Proveedor,Entregado) values (?,?,?,?,?)";
                PreparedStatement pst = con.prepareStatement(sql1);
                
                pst.setString(1, fec);
                pst.setString(2, "");
                pst.setString(3, "");
                pst.setString(4, (String) cmbProv.getSelectedItem());
                pst.setString(5, "NO");
                
                int n = pst.executeUpdate();
                if(n > 0){
                    String verTratas = "select * from Tratas";
                    ResultSet rs1 = st.executeQuery(verTratas);
                    String datos1[] = new String[10]; 
                    while(rs1.next()){
                        datos1[0] = rs1.getString("Id");
                    }
                    int id = Integer.parseInt(datos1[0]);
                    id = id + 1;
                    String sql2 = "insert into Trata (Proyecto,Plano,FechaInicio,FechaFinal,Terminado,Estado,Prioridad,Proveedor,numTrata) values(?,?,?,?,?,?,?,?,?)";
                    String sql3 = "update Calidad set Proyecto = ?, Plano = ?, FechaInicio = ?, FechaFinal = ?, Terminado = ?, Estado = ?, Tratamiento = ?, Cronometro = ?, Prioridad = ? where Proyecto = ?";
                    PreparedStatement pst1 = con.prepareStatement(sql2);
                    PreparedStatement pst2 = con.prepareStatement(sql3);
                    int cont = 0;
                    boolean band = false;
                    int cuenta = Tabla2.getRowCount();
                        do{
                        String verCalidad = "select * from Calidad where Proyecto like '"+Tabla2.getValueAt(cont, 1).toString()+"'";
                        String datos[] = new String[10];
                        ResultSet rs = st.executeQuery(verCalidad);
                        while(rs.next()){
                        datos[0] = rs.getString("Estado");
                        datos[1] = rs.getString("Prioridad");
                        datos[2] = rs.getString("Cronometro");
                        }
                        pst1.setString(1, Tabla2.getValueAt(cont, 1).toString());
                        pst1.setString(2, Tabla2.getValueAt(cont, 0).toString());
                        pst1.setString(3, fec);
                        pst1.setString(4, fec);
                        pst1.setString(5, "NO");
                        pst1.setString(6, datos[0]);
                        pst1.setString(7, datos[1]);
                        pst1.setString(8, (String) cmbProv.getSelectedItem());
                        pst1.setString(9, ""+id);
                        
                        pst2.setString(1, Tabla2.getValueAt(cont, 1).toString());
                        pst2.setString(2, Tabla2.getValueAt(cont, 0).toString());
                        pst2.setString(3, fec);
                        pst2.setString(4, fec);
                        pst2.setString(5, "SI");
                        pst2.setString(6, datos[0]);
                        pst2.setString(7, "SI");
                        pst2.setString(8, datos[2]);
                        pst2.setString(9, datos[1]);
                        pst2.setString(10, Tabla2.getValueAt(cont, 1).toString());
                        
                        int n2 = pst1.executeUpdate();
                        int n3 = pst2.executeUpdate();
                        if(n2 > 0 && n3 > 0){
                        JOptionPane.showMessageDialog(this, "DATOS GUARDADOS CORRECTAMENTE","GUARDADO",JOptionPane.INFORMATION_MESSAGE);
                        limpiarTabla();
                        limpiarTabla2();
                        verDatos();
                        }
                        
                        cont = cont + 1;
                        JOptionPane.showMessageDialog(this, cont+","+cuenta);
                        }while(cont < cuenta);
                
                
                }
                }catch(SQLException e){
                JOptionPane.showMessageDialog(this, "ERROR AL MANDAR INFORMACION"+e,"ERROR",JOptionPane.ERROR_MESSAGE);
                }
            }
        }
        }
    }
    
    public Calidad() {
        initComponents();
        fechaFinal();
        limpiarTabla();
        limpiarTabla2();
        verDatos();
        ((javax.swing.plaf.basic.BasicInternalFrameUI) this.getUI()).setNorthPane(null);
    }

   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel15 = new javax.swing.JPanel();
        jPanel14 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        txtEmpleado = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        txtCodigo = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtProyecto = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtFecha = new javax.swing.JLabel();
        rSPanelRound1 = new rojeru_san.rspanel.RSPanelRound();
        jLabel20 = new javax.swing.JLabel();
        panelTipo = new rojeru_san.rspanel.RSPanelRound();
        lblTipo = new javax.swing.JLabel();
        lblRevision = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        panelAgregar = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        panelBorrar = new javax.swing.JPanel();
        jButton3 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        Tabla2 = new javax.swing.JTable();
        jPanel11 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        jPanel19 = new javax.swing.JPanel();
        btnSalir = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        Tabla1 = new ColorRojo();
        jPanel13 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtNumero = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtPuesto = new javax.swing.JTextField();
        jPanel7 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        cmbEnviar = new RSMaterialComponent.RSComboBoxMaterial();
        jPanel9 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jPanel10 = new javax.swing.JPanel();
        btnGuardar = new javax.swing.JButton();
        jPanel12 = new javax.swing.JPanel();
        cmbProv = new RSMaterialComponent.RSComboBoxMaterial();

        setBackground(new java.awt.Color(255, 255, 255));
        setBorder(null);
        setFrameIcon(null);
        setNextFocusableComponent(txtNumero);
        setVisible(true);
        getContentPane().setLayout(new java.awt.GridLayout());

        jPanel15.setBackground(new java.awt.Color(255, 255, 255));
        jPanel15.setLayout(new java.awt.BorderLayout(30, 0));

        jPanel14.setBackground(new java.awt.Color(255, 255, 255));
        jPanel14.setLayout(new javax.swing.BoxLayout(jPanel14, javax.swing.BoxLayout.Y_AXIS));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new java.awt.GridLayout(6, 0, 20, 20));

        jLabel9.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("DATOS DE PLANO");
        jPanel2.add(jLabel9);

        jLabel12.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setText("NUMERO DE EMPLEADO");
        jPanel2.add(jLabel12);

        txtEmpleado.setBackground(new java.awt.Color(255, 255, 255));
        txtEmpleado.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtEmpleado.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtEmpleado.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(204, 204, 204)));
        txtEmpleado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEmpleadoActionPerformed(evt);
            }
        });
        jPanel2.add(txtEmpleado);

        jLabel16.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel16.setText("CODIGO DE BARRAS");
        jPanel2.add(jLabel16);

        txtCodigo.setBackground(new java.awt.Color(255, 255, 255));
        txtCodigo.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtCodigo.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCodigo.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(204, 204, 204)));
        txtCodigo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodigoActionPerformed(evt);
            }
        });
        jPanel2.add(txtCodigo);

        jPanel14.add(jPanel2);

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setLayout(new java.awt.GridLayout(3, 0, 10, 10));

        jLabel2.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText("NUMERO DE PLANO:  ");
        jPanel4.add(jLabel2);

        txtProyecto.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        txtProyecto.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jPanel4.add(txtProyecto);

        jLabel4.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setText("FECHA INICIO:  ");
        jPanel4.add(jLabel4);

        txtFecha.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        txtFecha.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jPanel4.add(txtFecha);

        rSPanelRound1.setColorBackground(new java.awt.Color(0, 102, 204));
        rSPanelRound1.setColorBorde(new java.awt.Color(0, 102, 204));

        jLabel20.setFont(new java.awt.Font("Roboto", 1, 20)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(255, 255, 255));
        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel20.setText("TIPO:  ");
        rSPanelRound1.add(jLabel20);

        jPanel4.add(rSPanelRound1);

        panelTipo.setColorBackground(new java.awt.Color(0, 102, 204));
        panelTipo.setColorBorde(new java.awt.Color(0, 102, 204));

        lblTipo.setFont(new java.awt.Font("Roboto", 1, 20)); // NOI18N
        lblTipo.setForeground(new java.awt.Color(255, 255, 255));
        lblTipo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTipo.setText("Revision: ");
        lblTipo.setToolTipText("");
        panelTipo.add(lblTipo);

        lblRevision.setFont(new java.awt.Font("Roboto", 1, 20)); // NOI18N
        lblRevision.setForeground(new java.awt.Color(255, 255, 255));
        lblRevision.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        panelTipo.add(lblRevision);

        jPanel4.add(panelTipo);

        jPanel14.add(jPanel4);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setLayout(new java.awt.BorderLayout());

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));

        panelAgregar.setBackground(new java.awt.Color(255, 255, 255));

        jButton2.setBackground(new java.awt.Color(255, 255, 255));
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/add-user.png"))); // NOI18N
        jButton2.setToolTipText("AGREGAR PROVEEDOR");
        jButton2.setBorder(null);
        jButton2.setBorderPainted(false);
        jButton2.setContentAreaFilled(false);
        jButton2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton2.setFocusPainted(false);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        panelAgregar.add(jButton2);

        jPanel5.add(panelAgregar);

        panelBorrar.setBackground(new java.awt.Color(255, 255, 255));

        jButton3.setBackground(new java.awt.Color(255, 255, 255));
        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IconoC/borrador.png"))); // NOI18N
        jButton3.setToolTipText("BORRAR TABLA");
        jButton3.setBorder(null);
        jButton3.setBorderPainted(false);
        jButton3.setContentAreaFilled(false);
        jButton3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton3.setFocusPainted(false);
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        panelBorrar.add(jButton3);

        jPanel5.add(panelBorrar);

        jPanel3.add(jPanel5, java.awt.BorderLayout.PAGE_START);

        Tabla2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        Tabla2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "PROYECTO", "PLANO"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        Tabla2.setRowHeight(25);
        Tabla2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Tabla2MouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(Tabla2);
        if (Tabla2.getColumnModel().getColumnCount() > 0) {
            Tabla2.getColumnModel().getColumn(0).setResizable(false);
            Tabla2.getColumnModel().getColumn(0).setPreferredWidth(2);
            Tabla2.getColumnModel().getColumn(1).setResizable(false);
        }

        jPanel3.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        jPanel14.add(jPanel3);

        jPanel15.add(jPanel14, java.awt.BorderLayout.CENTER);

        jPanel11.setBackground(new java.awt.Color(255, 255, 255));
        jPanel11.setLayout(new java.awt.BorderLayout());

        jLabel17.setFont(new java.awt.Font("Roboto", 1, 40)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(0, 165, 252));
        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel17.setText("Calidad");
        jPanel11.add(jLabel17, java.awt.BorderLayout.CENTER);

        jPanel19.setBackground(new java.awt.Color(255, 255, 255));

        btnSalir.setBackground(new java.awt.Color(255, 255, 255));

        jLabel11.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("X");
        jLabel11.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jLabel11.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel11MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel11MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel11MouseExited(evt);
            }
        });
        btnSalir.add(jLabel11);

        jPanel19.add(btnSalir);

        jPanel11.add(jPanel19, java.awt.BorderLayout.EAST);

        jPanel15.add(jPanel11, java.awt.BorderLayout.NORTH);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new java.awt.BorderLayout());

        Tabla1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        Tabla1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "PRIORIDAD", "NUMERO DE PLANO", "PROYECTO"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        Tabla1.setRowHeight(25);
        Tabla1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Tabla1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(Tabla1);
        if (Tabla1.getColumnModel().getColumnCount() > 0) {
            Tabla1.getColumnModel().getColumn(0).setResizable(false);
            Tabla1.getColumnModel().getColumn(1).setResizable(false);
            Tabla1.getColumnModel().getColumn(2).setResizable(false);
        }

        jPanel1.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jPanel15.add(jPanel1, java.awt.BorderLayout.WEST);

        jPanel13.setBackground(new java.awt.Color(255, 255, 255));
        jPanel13.setLayout(new java.awt.BorderLayout(30, 40));

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setLayout(new java.awt.GridLayout(7, 0, 0, 20));

        jLabel3.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("DATOS DE EMPLEADO");
        jPanel6.add(jLabel3);

        jLabel7.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("NUMERO EMPLEADO");
        jPanel6.add(jLabel7);

        txtNumero.setEditable(false);
        txtNumero.setBackground(new java.awt.Color(255, 255, 255));
        txtNumero.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtNumero.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtNumero.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(204, 204, 204)));
        jPanel6.add(txtNumero);

        jLabel6.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("NOMBRE");
        jPanel6.add(jLabel6);

        txtNombre.setEditable(false);
        txtNombre.setBackground(new java.awt.Color(255, 255, 255));
        txtNombre.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtNombre.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtNombre.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(204, 204, 204)));
        jPanel6.add(txtNombre);

        jLabel8.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("PUESTO");
        jPanel6.add(jLabel8);

        txtPuesto.setEditable(false);
        txtPuesto.setBackground(new java.awt.Color(255, 255, 255));
        txtPuesto.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtPuesto.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtPuesto.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(204, 204, 204)));
        jPanel6.add(txtPuesto);

        jPanel13.add(jPanel6, java.awt.BorderLayout.NORTH);

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setLayout(new javax.swing.BoxLayout(jPanel7, javax.swing.BoxLayout.Y_AXIS));

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));

        cmbEnviar.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "SELECCIONAR", "FRESADORA", "CNC", "TORNO", "CORTES", "ACABADOS", "TRATAMIENTO", "LIBERAR" }));
        cmbEnviar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbEnviarActionPerformed(evt);
            }
        });
        jPanel8.add(cmbEnviar);

        jPanel7.add(jPanel8);

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ImgAnimacion/actualizar_48.png"))); // NOI18N
        jButton1.setBorder(null);
        jButton1.setBorderPainted(false);
        jButton1.setContentAreaFilled(false);
        jButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton1.setFocusPainted(false);
        jButton1.setPreferredSize(new java.awt.Dimension(70, 70));
        jButton1.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/ImgAnimacion/actualizar_48.png"))); // NOI18N
        jButton1.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/ImgAnimacion/actualizar_64.png"))); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel9.add(jButton1);

        jPanel7.add(jPanel9);

        jPanel10.setBackground(new java.awt.Color(255, 255, 255));

        btnGuardar.setBackground(new java.awt.Color(255, 255, 255));
        btnGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ImgAnimacion/send_48.png"))); // NOI18N
        btnGuardar.setBorder(null);
        btnGuardar.setBorderPainted(false);
        btnGuardar.setContentAreaFilled(false);
        btnGuardar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnGuardar.setFocusPainted(false);
        btnGuardar.setPreferredSize(new java.awt.Dimension(70, 70));
        btnGuardar.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/ImgAnimacion/send_48.png"))); // NOI18N
        btnGuardar.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/ImgAnimacion/send_64.png"))); // NOI18N
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });
        jPanel10.add(btnGuardar);

        jPanel7.add(jPanel10);

        jPanel12.setBackground(new java.awt.Color(255, 255, 255));

        cmbProv.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "PROVEEDOR" }));
        jPanel12.add(cmbProv);

        jPanel7.add(jPanel12);

        jPanel13.add(jPanel7, java.awt.BorderLayout.CENTER);

        jPanel15.add(jPanel13, java.awt.BorderLayout.EAST);

        getContentPane().add(jPanel15);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        guardar(lblRevision.getText());
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void txtEmpleadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEmpleadoActionPerformed
        try{
            Connection con = null;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            String datos[] = new String[6];
            String sql = "select * from registroEmpleados where NumEmpleado like '"+txtEmpleado.getText()+"'";
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()){
                datos[0] = rs.getString("NumEmpleado");
                datos[1] = rs.getString("Nombre");
                datos[2] = rs.getString("Apellido");
                datos[3] = rs.getString("Direccion");
                datos[4] = rs.getString("Telefono");
                datos[5] = rs.getString("Puesto");
            }

            if(txtEmpleado.getText().equals(datos[0])){
                txtNumero.setText(datos[0]);
                txtNombre.setText(datos[1]);
                txtPuesto.setText(datos[5]);
                txtEmpleado.setText("");
            }else{
                JOptionPane.showMessageDialog(this, "EL numero que ingresaste no existe","ERROR",JOptionPane.ERROR_MESSAGE);
            }

        }catch(SQLException e){

            JOptionPane.showMessageDialog(this, "","",JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_txtEmpleadoActionPerformed

    private void txtCodigoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodigoActionPerformed
        if(txtNombre.getText().equals("")){
            JOptionPane.showMessageDialog(this, "DEBES INGRESAR TU NUMERO DE EMPLEADO","ADVERTENCIA",JOptionPane.WARNING_MESSAGE);
        }else{
            revisarPlanos revisar = new revisarPlanos();
            Stack<String> revision = revisar.checarRevisionPlano(txtCodigo.getText());
            String noRevision;
            if(revision == null){
                txtCodigo.setText("");
            }else{
                if(revision.size() > 1){
                    JFrame f = (JFrame) JOptionPane.getFrameForComponent(this);
                    elegirRevision = new elegirRevision(f,true);
                    noRevision = elegirRevision.showDialog(revision);
                }else{
                    noRevision = revision.firstElement();
                }
                String plano;
                if(txtCodigo.getText().contains("/")){
                    plano = txtCodigo.getText().substring(0, txtCodigo.getText().lastIndexOf("/"));
                }else{
                    plano = txtCodigo.getText();
                }
                if(!noRevision.equals("")){
                    lblRevision.setText(noRevision);
                    Date fe = new Date();
                    pl = plano;
                    SimpleDateFormat nuevo = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                    String fec = nuevo.format(fe);
                    String datos[] = new String[10];
                    try {
                        
                        Connection con = null;
                        Conexion con1 = new Conexion();
                        con = con1.getConnection();
                        Statement st = con.createStatement();
                        Statement st1 = con.createStatement();
                        Statement st2 = con.createStatement();
                        //Cambiar para que se actualice conforme al plano y a su revision
                        String sql = "UPDATE calidad SET Proyecto = ?, Plano = ?, FechaInicio = ?, FechaFinal = ?, Terminado = ?, Estado = ?,Cronometro = ?, Prioridad = ?,Empleado = ?, Tratamiento = ? WHERE Proyecto = ? and Revision = ?";
                        //Seleccionar el plano pero que coincida con su revision
                        String sql1 = "select * from calidad where Proyecto like '" + plano + "' and Revision like '" + noRevision + "'";
                        PreparedStatement pst = con.prepareStatement(sql);
                        ResultSet rs = st.executeQuery(sql1);
                        while (rs.next()) {
                            datos[0] = rs.getString("Estado");
                            datos[1] = rs.getString("Proyecto");
                            datos[2] = rs.getString("Plano");
                            datos[3] = rs.getString("FechaInicio");
                            datos[4] = rs.getString("FechaFinal");
                            datos[5] = rs.getString("Terminado");
                            datos[6] = rs.getString("Cronometro");
                            datos[7] = rs.getString("Prioridad");
                        }
                        String a;
                        if (plano.equals(datos[1]) && datos[5].equals("NO")) {
                            txtProyecto.setText(plano);
                            txtCodigo.setText("");

                            if(datos[7] == null || datos[7].equals("")){
                            a = txtNumero.getText();
                            }else{
                            a = datos[7]+","+txtNumero.getText();
                            }

                            if (datos[3].equals("")) {
                                pst.setString(1, txtProyecto.getText());
                                pst.setString(2, datos[2]);
                                pst.setString(3, fec);
                                pst.setString(4, "");
                                pst.setString(5, "NO");
                                pst.setString(6, datos[0]);
                                pst.setString(7, datos[6]);
                                pst.setString(8, datos[7]);
                                pst.setString(9, a);
                                pst.setString(10, "NO");
                                pst.setString(11, txtProyecto.getText());
                                pst.setString(12, noRevision);
                                
                                int n1 = pst.executeUpdate();
                                if (n1 > 0) {
                                    limpiarTabla();
                                    verDatos();
                                    txtFecha.setText(fec);
                                }
                            } else if (datos[3] != (null) && datos[5].equals("NO")) {

                                txtFecha.setText(datos[3]);
                                txtCodigo.setText("");
                            }
                            contReporte = 0;
                        } else {
                            //Verificar para buscar el plano y su revision
                            revisarPlanos report = new revisarPlanos();
                            String b = report.buscar(plano, noRevision);
                            if (b == (null)) {
                                JOptionPane.showMessageDialog(this, "NO SE A ENCONTRADO ESTE PLANO", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);

                                contReporte++;
                                if(contReporte == 2){
                                    reportarPlano rep = new reportarPlano();
                                    rep.enviarPlano("Cortes", txtNombre.getText(), plano, "");
                                    contReporte = 0;
                                }
                                txtCodigo.setText("");
                            }else if (b.equals("LIBERACION")) {
                                JOptionPane.showMessageDialog(this, "EL PLANO ESTA EN LIBERACION FAVOR DE DECIRLE AL ENCARGADO PARA LIBERAR PLANOS", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
                                txtCodigo.setText("");
                            } else {
                                contReporte = 0;
                                int opc = JOptionPane.showConfirmDialog(this, "EL PLANO " + plano + " ESTA EN " + b + ", DESEAS TRAERLO A CORTES?", "INFO", JOptionPane.YES_NO_OPTION);
                                if (opc == 0) {
                                    //cambiar todas estas para que se actualicen igual con su revision
                                    String acCnc = "update cnc set Terminado = ? where Proyecto = ? and Revision = ?";
                                    String acC = "update calidad set Terminado = ?,FechaInicio = ?,FechaFinal = ?, Tratamiento = ? where Proyecto = ? and Revision = ?";
                                    String acFresa = "update Fresadora set Terminado = ? where Proyecto = ? and Revision = ?";
                                    String acTorno = "update Torno set Terminado = ? where Proyecto = ? and Revision = ?";
                                    String acAcabados = "update Acabados set Terminado = ? where Proyecto = ? and Revision = ?";
                                    String acTrata = "update Trata set Terminado = ? where Proyecto = ? and Revision = ?";
                                    String acCalidad = "update datos set Terminado = ? where Proyecto = ? and Revision = ?";
                                    String insertar = "insert into calidad (Proyecto, Plano, FechaInicio,FechaFinal,Terminado,Estado,Cronometro,Prioridad,Empleado,Tratamiento,Revision) values (?,?,?,?,?,?,?,?,?,?,?)";
                                    PreparedStatement pst1 = con.prepareStatement(acCnc);
                                    PreparedStatement pst2 = con.prepareStatement(acC);
                                    PreparedStatement pst3 = con.prepareStatement(acFresa);
                                    PreparedStatement pst4 = con.prepareStatement(acTorno);
                                    PreparedStatement pst5 = con.prepareStatement(acAcabados);
                                    PreparedStatement pst6 = con.prepareStatement(acTrata);
                                    PreparedStatement pst7 = con.prepareStatement(acCalidad);
                                    PreparedStatement pst8 = con.prepareCall(insertar);

                                    //seleccionar por su revision
                                    String ver1 = "select * from Datos where Proyecto like '" + plano + "' and Revision like '"+noRevision+"'";
                                    String ver2 = "select * from Planos where Plano like '" + plano + "' and Revision like '"+noRevision+"'";
                                    ResultSet rs1 = st1.executeQuery(ver1);
                                    ResultSet rs2 = st2.executeQuery(ver2);
                                    String da1[] = new String[10];
                                    String da2[] = new String[10];
                                    while (rs1.next()) {
                                        da1[0] = rs1.getString("Proyecto");
                                        da1[1] = rs1.getString("Plano");
                                        da1[2] = rs1.getString("Cronometro");
                                        da1[3] = rs1.getString("Prioridad");
                                    }
                                    while (rs2.next()) {
                                        da2[0] = rs2.getString("Plano");
                                        da2[1] = rs2.getString("Proyecto");
                                        da2[2] = rs2.getString("Prioridad");
                                    }
                                    int n;
                                    if (plano.equals(da1[0])) {
                                        pst2.setString(1, "NO");
                                        pst2.setString(2, fec);
                                        pst2.setString(3, "");
                                        pst2.setString(4, "NO");
                                        pst2.setString(5, plano); 
                                        pst2.setString(6, noRevision); 
                                        n = pst2.executeUpdate();
                                    } else {
                                        pst8.setString(1, da2[0]);
                                        pst8.setString(2, da2[1]);
                                        pst8.setString(3, fec);
                                        pst8.setString(4, "");
                                        pst8.setString(5, "NO");
                                        pst8.setString(6, "");
                                        pst8.setString(7, "00:00");
                                        pst8.setString(8, da2[2]);
                                        pst8.setString(9, txtNumero.getText());
                                        pst8.setString(10, "NO");
                                        pst8.setString(11, noRevision);
                                        n = pst8.executeUpdate();
                                    }

                                    if (b.equals("CNC")) {
                                        pst1.setString(1, "SI");
                                        pst1.setString(2, plano);
                                        pst1.setString(3, noRevision);

                                        int m = pst1.executeUpdate();
                                        if (n > 0 && m > 0) {
                                            datos(plano);
                                        }
                                    } else if (b.equals("FRESADORA")) {
                                        pst3.setString(1, "SI");
                                        pst3.setString(2, plano);
                                        pst3.setString(3, noRevision);

                                        int m = pst3.executeUpdate();
                                        if (n > 0 && m > 0) {
                                            datos(plano);
                                        }
                                    } else if (b.equals("TORNO")) {
                                        pst4.setString(1, "SI");
                                        pst4.setString(2, plano);
                                        pst4.setString(3, noRevision);

                                        int m = pst4.executeUpdate();
                                        if (n > 0 && m > 0) {
                                            datos(plano);
                                        }
                                    } else if (b.equals("ACABADOS")) {
                                        pst5.setString(1, "SI");
                                        pst5.setString(2, plano);
                                        pst5.setString(3, noRevision);

                                        int m = pst5.executeUpdate();
                                        if (n > 0 && m > 0) {
                                            datos(plano);
                                        }
                                    } else if (b.equals("TRATAMIENTO")) {
                                        pst6.setString(1, "SI");
                                        pst6.setString(2, plano);
                                        pst6.setString(3, noRevision);

                                        int m = pst6.executeUpdate();
                                        if (n > 0 && m > 0) {
                                            datos(plano);
                                        }
                                    } else if (b.equals("CALIDAD")) {
                                        pst7.setString(1, "SI");
                                        pst7.setString(2, "SI");
                                        pst7.setString(3, plano);
                                        pst7.setString(4, noRevision);

                                        int m = pst7.executeUpdate();
                                        if (n > 0 && m > 0) {
                                            datos(plano);
                                        }
                                    } else if (b.equals("CORTES")) {
                                        pst7.setString(1, "SI");
                                        pst7.setString(2, plano);
                                        pst7.setString(3, noRevision);

                                        int m = pst7.executeUpdate();
                                        if (n > 0 && m > 0) {
                                            datos(plano);
                                        }
                                    } else if (b.equals("TERMINADO TRATAMIENTO")) {
                                        pst7.setString(1, "SI");
                                        pst7.setString(3, plano);
                                        pst7.setString(4, noRevision);

                                        int m = pst7.executeUpdate();
                                        if (n > 0 && m > 0) {
                                            datos(plano);
                                        }
                                    }
                                }
                            }
                        }
                    } catch (SQLException ex) {
                        Logger.getLogger(Inicio1.class.getName()).log(Level.SEVERE,null,ex);
                        JOptionPane.showMessageDialog(this, "ERROR AL SELECCIONAR UN EQUIPO : " + ex.getMessage());
                    }
                }
            }
        }
    }//GEN-LAST:event_txtCodigoActionPerformed

    private void Tabla1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Tabla1MouseClicked
        DefaultTableModel miModelo = (DefaultTableModel) Tabla2.getModel();
        int fila = Tabla1.getSelectedRow();
        int cont = 1;
        boolean band = false;
        
        String datos[] = new String[8];
        datos[0] = Tabla1.getValueAt(fila, 2).toString();
        datos[1] = Tabla1.getValueAt(fila, 1).toString();
        
        if((Tabla2.getRowCount()) == 0){
        miModelo.addRow(datos);
        }else if(Tabla2.getRowCount() >= 1){
        do{
        if(datos[1].equals(Tabla2.getValueAt((cont-1), 1).toString())){
        JOptionPane.showMessageDialog(this,"PLANO YA SELECCIONADO","ADVERTENCIA",JOptionPane.WARNING_MESSAGE);
        band = true;
        }
        cont = cont+1;
        }
        while(cont <= Tabla2.getRowCount() || band == true);
        if(band == false){
        miModelo.addRow(datos);
        }
        }
    }//GEN-LAST:event_Tabla1MouseClicked

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        limpiarTabla2();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        addProveedor n = new addProveedor();
        n.setVisible(true);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void Tabla2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Tabla2MouseClicked
       int fila = Tabla2.getSelectedRow();
       Tabla2.remove(fila);
    }//GEN-LAST:event_Tabla2MouseClicked

    private void jLabel11MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel11MouseClicked
        dispose();
    }//GEN-LAST:event_jLabel11MouseClicked

    private void jLabel11MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel11MouseEntered
        btnSalir.setBackground(Color.red);
        jLabel11.setForeground(Color.white);
    }//GEN-LAST:event_jLabel11MouseEntered

    private void jLabel11MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel11MouseExited
        btnSalir.setBackground(Color.white);
        jLabel11.setForeground(Color.black);
    }//GEN-LAST:event_jLabel11MouseExited

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        limpiarTabla();
        verDatos();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void cmbEnviarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbEnviarActionPerformed
        if(cmbEnviar.getSelectedItem().equals("TRATAMIENTO")){
       cmbProv.setVisible(true);
       this.cmbProv.removeAllItems();
        this.cmbProv.addItem("PROVEEDOR");
       try{
            ResultSet rs = null;
            Connection con3 = null;
            Conexion conect3 = new Conexion();
            con3 = conect3.getConnection();
            Statement Sent = con3.createStatement();
            rs = Sent.executeQuery("select * from registroProv");
            while(rs.next()){
                this.cmbProv.addItem(rs.getString("Compania"));
            }
            contador++;
        }catch (SQLException e){
        }
       }else{
       cmbProv.setVisible(false);
       }
    }//GEN-LAST:event_cmbEnviarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable Tabla1;
    private javax.swing.JTable Tabla2;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JPanel btnSalir;
    private RSMaterialComponent.RSComboBoxMaterial cmbEnviar;
    private RSMaterialComponent.RSComboBoxMaterial cmbProv;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblRevision;
    private javax.swing.JLabel lblTipo;
    private javax.swing.JPanel panelAgregar;
    private javax.swing.JPanel panelBorrar;
    private rojeru_san.rspanel.RSPanelRound panelTipo;
    private rojeru_san.rspanel.RSPanelRound rSPanelRound1;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JTextField txtEmpleado;
    private javax.swing.JLabel txtFecha;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtNumero;
    private javax.swing.JLabel txtProyecto;
    private javax.swing.JTextField txtPuesto;
    // End of variables declaration//GEN-END:variables
}
