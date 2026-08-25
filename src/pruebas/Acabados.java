package pruebas;
import Conexiones.Conexion;
import Controlador.maquinados.reportarPlano;
import Controlador.maquinados.revisarPlanos;
import VentanaEmergente.Maquinados.elegirRevision;
import com.mxrck.autocompleter.TextAutoCompleter;
import java.awt.Color;
import java.awt.Font;
import java.awt.KeyboardFocusManager;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
public class Acabados extends javax.swing.JInternalFrame {

    int mili = 0;
    int segundos;
    int minutos = 0;
    int horas = 0;
    boolean estado = false;
    public String fechaInicio;
    public String pl;
    int contReporte;
    public TextAutoCompleter au;
    public String numEmpleado;
    
    elegirRevision elegirRevision;
    
    public void activar() {
        estado = true;
        String datos[] = new String[10];
        try{
        Connection con = null;
        Conexion con1 = new Conexion();
        con = con1.getConnection();
        Statement st = con.createStatement();
        String sql = "select * from acabados where Proyecto like '"+pl+"'";
        ResultSet rs = st.executeQuery(sql);
        
        while(rs.next()){
        datos[1] = rs.getString("FechaInicio");
        datos[2] = rs.getString("FechaFinal");
        datos[3] = rs.getString("Cronometro");
        }
        }catch(SQLException E){
        JOptionPane.showMessageDialog(this, "ERROR"+E);
        }
        int hora1, hora2, minuto1, minuto2, dia1, dia2,seg1,seg2,dia = 0;
        int fecha9 = 0, fecha10 = 0,cr1,cr2;
        String d1, d2, f1, f2, f4, f5,f6,f7, fin = "";
        String c1,c2;
        if(datos[2] == null || datos[2].equals("")){
        Date fe = new Date();
        SimpleDateFormat nuevo = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String fec = nuevo.format(fe);
        datos[2] = fec;
        }
        f1 = datos[1].substring(11, 13);
        f2 = datos[1].substring(14, 16);
        f4 = datos[2].substring(11, 13);
        f5 = datos[2].substring(14, 16);
        f6 = datos[1].substring(17,19);
        f7 = datos[2].substring(17,19);
        d1 = datos[1].substring(0, 2);
        d2 = datos[2].substring(0, 2);

        hora1 = Integer.parseInt(f1);
        minuto1 = Integer.parseInt(f2);
        hora2 = Integer.parseInt(f4);
        minuto2 = Integer.parseInt(f5);
        dia1 = Integer.parseInt(d1);
        dia2 = Integer.parseInt(d2);
        seg1 = Integer.parseInt(f6);
        seg2 = Integer.parseInt(f7);
        
        if(dia2 > dia1){
        dia = (dia2 - dia1);
        }
        hora2 = (dia * 24) + hora2;
        horas = hora2 - hora1; 
        
        if(minuto2 < minuto1){
        minutos = (60 + minuto2) - minuto1;
        horas --;
        }else{
        minutos = minuto2 - minuto1;
        }
        
        if(seg2 < seg1){
        segundos = (seg2 + 60) - seg1;
        minutos--;
        }else{
        segundos = seg2 - seg1;
        }
        
        c1 = datos[3].substring(0,2);
        c2 = datos[3].substring(3,5);
        if(c1 != "00" || c2 != "00"){
        cr1 = Integer.parseInt(c1);
        cr2 = Integer.parseInt(c2);
        horas = horas + cr1;
        minutos = minutos + cr2;
        }
        
        Thread hilo = new Thread() {
            public void run() {
                for (;;) {
                    if (estado == true) {
                        try {
                            sleep(1);
                            if (mili >= 750) {
                                mili = 0;
                                segundos++;
                            }
                            if (segundos >= 60) {
                                mili = 0;
                                segundos = 0;
                                minutos++;
                            }
                            if (minutos >= 60) {
                                mili = 0;
                                segundos = 0;
                                minutos = 0;
                                horas++;
                            }
                            String ho="",mi="",se="";
                            if(horas < 10){
                            ho = "0"+horas;
                            }else{
                            ho = ""+horas;
                            }
                            if(minutos < 10){
                            mi = "0"+minutos;
                            }else{
                            mi = ""+minutos;
                            }
                            if(segundos < 10){
                            se = "0"+segundos;
                            }else{
                            se = ""+segundos;
                            }
                            
                            lblCrono.setText(ho+":"+mi+":"+se);
                            lblCrono1.setText(""+mili);
                            mili++;
                        } catch (Exception e) {
                            JOptionPane.showMessageDialog(null, "ERROR EN CRONOMETRO");
                        }
                    } else {
                        break;
                    }
                }
            }
        };
        hilo.start();
    }
    
    public void datos(String plano){
        Date fe = new Date();
        SimpleDateFormat nuevo = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String fec = nuevo.format(fe);
        JOptionPane.showMessageDialog(this, "PLANO EMPEZADO");
        limpiarTabla();
        verDatos();
        activar();
        txtPlano.setText(plano);
        txtCodigo.setText("");
        txtProyecto.setText(fec);
        lblEstado.setText("EN CURSO");
        lblEstado.setForeground(Color.green);
    }

    public void limpiarTabla() {
        Tabla1 = new javax.swing.JTable();
        Tabla1.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{},
                new String[]{
                    "Plano", "Proyecto"
                }
        ) {
            boolean[] canEdit = new boolean[]{
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
        Tabla1.setComponentPopupMenu(jPopupMenu1);
        Tabla1.getTableHeader().setFont(new Font("Roboto", Font.BOLD, 14));
        Tabla1.setFont(new java.awt.Font("Roboto", Font.PLAIN, 12));
        Tabla1.getTableHeader().setOpaque(false);
        Tabla1.getTableHeader().setBackground(new Color(254, 254, 254));
        Tabla1.getTableHeader().setForeground(new Color(0, 78, 171));
        Tabla1.setRowHeight(25);
        Tabla1.setShowGrid(false);

        jScrollPane1.getViewport().setBackground(Color.white);
        jScrollPane1.setForeground(Color.white);
        jScrollPane1.getViewport().setForeground(Color.white);
        jScrollPane1.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(254, 254, 254)));
        jScrollPane1.setBackground(Color.white);
        jScrollPane1.setViewportView(Tabla1);
        if (Tabla1.getColumnModel().getColumnCount() > 0) {
            Tabla1.getColumnModel().getColumn(0).setResizable(false);
            Tabla1.getColumnModel().getColumn(1).setResizable(false);
        }
    }

    public final void verDatos(){
            DefaultTableModel miModelo = (DefaultTableModel) Tabla1.getModel();
            try{
            Connection con = null;

            Conexion con1 = new Conexion();
            con = con1.getConnection();

            String datos[] = new String[6];
            String sql = "select Estado, Proyecto, Plano from planos where Estado like 'ACABADOS%'";

            Statement st = con.createStatement(); 
            ResultSet rs = st.executeQuery(sql);

                while(rs.next()){
                datos[0] = rs.getString("Plano");
                datos[1] = rs.getString("Proyecto");

                 miModelo.addRow(datos);
                }

            }catch(SQLException e){
                JOptionPane.showMessageDialog(this, "NO SE PUEDEN MOSTRAR LOS DATOS" + e,"ADVERTENCIA",JOptionPane.WARNING_MESSAGE);

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

       txtProyecto.setText(fec);

    }
    
    public void tabla(){
        JOptionPane.showMessageDialog(this, "DATOS GUARDADOS CORRECTAMENTE");
        fecha();
        txtProyecto.setText(""); 
        limpiarTabla();
        verDatos();
        panelPiezas.setVisible(false);
        txtPlano.setText("");
        txtProyecto.setText("");
    }
    
    public void borrar(){
            txtPlano.setText("");
            txtProyecto.setText("");
            lblEstado.setText("SIN SELECCIONAR");
            lblEstado.setForeground(Color.red);

        }
    
    public void verPlanoNew(String plano) {
        try{
            Connection con;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            String plan = plano;
            if(plano.contains("/")){
                plan = plan.substring(0,plan.indexOf("/"));
            }
            String sql = "select Plano, Proyecto, Cantidad, Material from planos where Plano like '" + plan + "'";
            ResultSet rs = st.executeQuery(sql);
//            String plano = null;
//            while(rs.next()){
//                plan = rs.getString("Plano");
//                txtPlano.setText(plano);
//                txtProyecto.setText(rs.getString("Proyecto"));
//                txtCantidad.setText(rs.getString("Cantidad"));
//                txtMaterial.setText(rs.getString("Material"));
//            }
//            KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
//            manager.focusNextComponent();
//            if(plano == null){
//                plano = verificarNomenclatura(plan,PLANO );
//                verPlano(plano);
//            }
//            txtPlano.setText("");
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "Error: "+e,"ERROR",JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void verPlano(){
        if(txtNombre.getText().equals("")){
            JOptionPane.showMessageDialog(this, "DEBES INGRESAR TU NUMERO DE EMPLEADO","ADVERTENCIA",JOptionPane.WARNING_MESSAGE);
        }else{
            revisarPlanos revisar = new revisarPlanos();
            Stack<String> revision = revisar.checarRevisionPlano(txtCodigo.getText());
            String noRevision = "";
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
                int intentos = revisar.intentosRealizadorPorPieza(plano,noRevision);
                if(intentos > 1){
                    panelPiezas.setVisible(true);
                    lblPiezas.setText("Pieza realizada "+intentos+" veces");
                }
                if(!noRevision.equals("")){
                    lblRevision.setText(noRevision);
                    if(txtCodigo.getText().contains("/")){
                            plano = txtCodigo.getText().substring(0, txtCodigo.getText().lastIndexOf("/"));
                        }else{
                            plano = txtCodigo.getText();
                        }
                    Date fe = new Date();
                    SimpleDateFormat nuevo = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                    String fec = nuevo.format(fe);
                    pl = plano;
                    if(txtNumero.getText().equals("")){
                    JOptionPane.showMessageDialog(this, "FAVOR DE LLENAR NUMERO DE EMPLEADO","ADVERTENCIA",JOptionPane.WARNING_MESSAGE);
                    }else{
                    try{
                        Connection con = null;
                        Conexion con1 = new Conexion();
                        con = con1.getConnection();
                        String datos[] = new String[15];
                        Statement st = con.createStatement();
                        Statement st1 = con.createStatement();
                        Statement st2 = con.createStatement();
                        String sql = "UPDATE acabados SET Proyecto = ?, Plano = ?, FechaInicio = ?, FechaFinal = ?, Terminado = ?, Estado = ?, Cronometro = ?, Prioridad = ?,Empleado = ? WHERE Proyecto = ? and Revision = ?";
                        String sql1 = "select * from acabados where Proyecto like '"+(plano)+"' and revision like '" + noRevision + "'";
                        PreparedStatement pst = con.prepareStatement(sql);
                        ResultSet rs = st.executeQuery(sql1);
                        while(rs.next()){
                            datos[0] = rs.getString("Estado");
                            datos[1] = rs.getString("Proyecto");
                            datos[2] = rs.getString("Plano");
                            datos[3] = rs.getString("FechaInicio");
                            datos[4] = rs.getString("FechaFinal");
                            datos[5] = rs.getString("Terminado");
                            datos[6] = rs.getString("Cronometro");
                            datos[7] = rs.getString("Prioridad");
                            datos[8] = rs.getString("Empleado");
                        }

                        if(plano.equals(datos[1]) && datos[5].equals("NO")){
                            txtPlano.setText(plano);
                            txtCodigo.setText("");

                            String as;
                                if(datos[8] == null || datos[8].equals("")){
                                as = txtNumero.getText();
                                }else{
                                as = datos[8]+","+txtNumero.getText();
                                }
                            if(datos[3].equals("") || datos[3] == null){
                                pst.setString(1, txtPlano.getText());
                                pst.setString(2, datos[2]);
                                pst.setString(3, fec);
                                pst.setString(4, "");
                                pst.setString(5, "NO");
                                pst.setString(6, datos[0]);
                                pst.setString(7, datos[6]);
                                pst.setString(8, datos[7]);
                                pst.setString(9, as);
                                pst.setString(10, txtPlano.getText());
                                pst.setString(11, noRevision);
                                int n1 = pst.executeUpdate();
                                if (n1 > 0){
                                    limpiarTabla();
                                    verDatos();
                                    lblEstado.setText("EN CURSO");
                                    lblEstado.setForeground(Color.green);
                                    txtProyecto.setText(fec);
                                    activar();
                                }
                            }else if(datos[3] != (null) && datos[5].equals("NO")){
                                txtProyecto.setText(datos[3]);
                                txtCodigo.setText("");
                                lblEstado.setText("EN CURSO");
                                lblEstado.setForeground(Color.green);
                                activar();
                            }
                            contReporte = 0;
                        }else {
                            revisarPlanos report = new revisarPlanos();
                            String b = report.buscar(plano,noRevision);
                          if(b == (null)){
                            JOptionPane.showMessageDialog(this, "NO SE A ENCONTRADO ESTE PLANO","ADVERTENCIA",JOptionPane.WARNING_MESSAGE);
                            contReporte++;
                            if(contReporte == 2){
                                reportarPlano rep = new reportarPlano();
                                rep.enviarPlano("Torno", txtNombre.getText(), plano, "");
                                contReporte = 0;
                            }
                            txtCodigo.setText("");
                            }else if(b.equals("LIBERACION")){
                                JOptionPane.showMessageDialog(this, "EL PLANO ESTA EN LIBERACION FAVOR DE DECIRLE AL ENCARGADO PARA LIBERAR PLANOS","ADVERTENCIA",JOptionPane.WARNING_MESSAGE);
                                txtCodigo.setText("");
                            }else{
                                contReporte = 0;
                                int opc = JOptionPane.showConfirmDialog(this, "EL PLANO "+plano+" ESTA EN "+b+", DESEAS TRAERLO A ACABADOS?","INFO",JOptionPane.YES_NO_OPTION);
                                if(opc == 0){
                                    String acC = "update datos set Terminado = ?,FechaInicio = ?, FechaFinal = ? where Proyecto = ? and Revision = ?";
                                    String acAcabados = "update acabados set Terminado = ?,FechaInicio = ?,FechaFinal = ? where Proyecto = ? and Revision = ?";
                                    String acFresa = "update fresadora set Terminado = ?,FechaInicio = ?, FechaFinal = ? where Proyecto = ? and Revision = ?";
                                    String acTorno = "update torno set Terminado = ?,FechaInicio = ?,FechaFinal = ? where Proyecto = ? and Revision = ?";
                                    String acCnc = "update cnc set Terminado = ?,FechaInicio = ?,FechaFinal = ? where Proyecto = ? and Revision = ?";
                                    String acTrata = "update trata set Terminado = ?,FechaInicio = ?,FechaFinal = ? where Proyecto = ? and Revision = ?";
                                    String acCalidad = "update calidad set Terminado = ?, Tratamiento = ?,FechaInicio = ?,FechaFinal = ? where Proyecto = ? and Revision = ?";
                                    String insertar = "insert into acabados (Proyecto, Plano, FechaInicio,FechaFinal,Terminado,Estado,Cronometro,Prioridad,Empleado,Revision) values (?,?,?,?,?,?,?,?,?,?)";
                                    PreparedStatement pst1 = con.prepareStatement(acC); 
                                    PreparedStatement pst2 = con.prepareStatement(acAcabados);
                                    PreparedStatement pst3 = con.prepareStatement(acFresa);
                                    PreparedStatement pst4 = con.prepareStatement(acTorno);
                                    PreparedStatement pst5 = con.prepareStatement(acCnc);
                                    PreparedStatement pst6 = con.prepareStatement(acTrata);
                                    PreparedStatement pst7 = con.prepareStatement(acCalidad);
                                    PreparedStatement pst8 = con.prepareCall(insertar);

                                    String ver1 = "select * from acabados where Proyecto like '"+plano+"' and Revision like '"+noRevision+"'";
                                    String ver2 = "select * from planos where Plano like '"+plano+"' and Revision like '"+noRevision+"'";
                                    ResultSet rs1 = st1.executeQuery(ver1);
                                    ResultSet rs2 = st2.executeQuery(ver2);
                                    String da1[] = new String[10];
                                    String da2[] = new String[10];
                                    while(rs1.next()){
                                    da1[0] = rs1.getString("Proyecto");
                                    da1[1] = rs1.getString("Plano");
                                    da1[2] = rs1.getString("Cronometro");
                                    da1[3] = rs1.getString("Prioridad");
                                    }
                                    while(rs2.next()){
                                    da2[0] = rs2.getString("Plano");
                                    da2[1] = rs2.getString("Proyecto");
                                    da2[2] = rs2.getString("Prioridad");
                                    }
                                    int n;
                                    if(plano.equals(da1[0])){
                                        pst2.setString(1, "NO");
                                        pst2.setString(2, fec);
                                        pst2.setString(3, ""); 
                                        pst2.setString(4, plano); 
                                        pst2.setString(5, noRevision);
                                        n = pst2.executeUpdate();
                                    }else{
                                        pst8.setString(1, da2[0]);
                                        pst8.setString(2, da2[1]);
                                        pst8.setString(3, fec);
                                        pst8.setString(4, "");
                                        pst8.setString(5, "NO");
                                        pst8.setString(6, "");
                                        pst8.setString(7, "00:00");
                                        pst8.setString(8, da2[2]);
                                        pst8.setString(9, txtNumero.getText());
                                        pst8.setString(10, noRevision);
                                        n = pst8.executeUpdate();
                                    }

                                    if(b.equals("CORTES")){
                                        pst1.setString(1, "SI");                  
                                        pst1.setString(2, fec);                  
                                        pst1.setString(3, fec);                  
                                        pst1.setString(4, plano);
                                        pst1.setString(3, noRevision);
                                        int m = pst1.executeUpdate();
                                        if(n > 0 && m > 0){
                                        datos(plano);
                                        }
                                    }else if(b.equals("FRESADORA")){
                                        pst3.setString(1, "SI");                  
                                        pst3.setString(2, fec);                  
                                        pst3.setString(3, fec);                  
                                        pst3.setString(4, plano);
                                        pst3.setString(5,noRevision);
                                        int m = pst3.executeUpdate();
                                        if(n > 0 && m > 0){
                                        datos(plano);
                                    }
                                    }else if(b.equals("TORNO")){
                                        pst4.setString(1, "SI");                  
                                        pst4.setString(2, fec);                  
                                        pst4.setString(3, fec);                  
                                        pst4.setString(4, plano);
                                        pst4.setString(5, noRevision);
                                        int m = pst4.executeUpdate();
                                        if(n > 0 && m > 0){
                                        datos(plano);
                                        }
                                    }else if(b.equals("CNC")){
                                        pst5.setString(1, "SI");                  
                                        pst5.setString(2, fec);                  
                                        pst5.setString(3, fec);                  
                                        pst5.setString(4, plano);
                                        pst5.setString(5, noRevision);
                                        int m = pst5.executeUpdate();
                                        if(n > 0 && m > 0){
                                        datos(plano);
                                        }
                                    }else if(b.equals("TRATAMIENTO")){
                                        pst6.setString(1, "SI");                  
                                        pst6.setString(2, fec);                  
                                        pst6.setString(3, fec);                  
                                        pst6.setString(4, plano);
                                        pst6.setString(5, noRevision);
                                        int m = pst6.executeUpdate();
                                        if(n > 0 && m > 0){
                                        datos(plano);
                                        }
                                    }else if(b.equals("CALIDAD")){
                                        pst7.setString(1, "SI");
                                        pst7.setString(2, "SI");                    
                                        pst7.setString(3, fec);                    
                                        pst7.setString(4, fec);                    
                                        pst7.setString(5, plano);
                                        pst7.setString(6, noRevision);
                                        int m = pst7.executeUpdate();
                                        if(n > 0 && m > 0){
                                        datos(plano);
                                        }
                                    }else if(b.equals("TERMINADO CALIDAD")){
                                        pst7.setString(1, "SI");
                                        pst7.setString(2, "SI");                    
                                        pst7.setString(3, fec);                    
                                        pst7.setString(4, fec);                    
                                        pst7.setString(5, plano);
                                        pst7.setString(6, noRevision);
                                        int m = pst7.executeUpdate();
                                        if(n > 0 && m > 0){
                                        datos(plano);
                                        }
                                    }else if(b.equals("TERMINADO TRATAMIENTO")){
                                        pst7.setString(1, "SI");
                                        pst7.setString(2, "SI");                    
                                        pst7.setString(3, fec);                    
                                        pst7.setString(4, fec);                    
                                        pst7.setString(5, plano);
                                        pst7.setString(6, noRevision);
                                        int m = pst7.executeUpdate();
                                        if(n > 0 && m > 0){
                                        datos(plano);
                                        }
                                    }
                                }
                            }
                        }
                    } catch (SQLException ex)
                    {
                        JOptionPane.showMessageDialog(this,"ERROR AL SELECCIONAR UN EQUIPO : " + ex.getMessage());
                    }
                    }
                }
            }
        }
    }
    
    public String obtenerDepartamento() {
        switch (cmbEnviar.getSelectedIndex()) {
            case 1:
                return "maquinados";
            case 2:
                return "calidad";
            case 3:
                return "datos";
            default:
                return null;
        }
    }

    public void enviarPlano(String plano, String proyecto) {
        try {
            Connection con;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            revisarPlanos rev = new revisarPlanos();
            String estacionSeleccionada = obtenerDepartamento();
            if (cmbEnviar.getSelectedIndex() == 1 || cmbEnviar.getSelectedIndex() == 3){
                if (cmbEnviar.getSelectedIndex() == 1) {
                    rev.retrabajo = true;
                }
                rev.enviarCortes("acabados", plano, numEmpleado, proyecto, "00");
                rev.actualizarPlanos(con, plano, estacionSeleccionada.toUpperCase());
                rev.transaccionTerminarPlano(con, plano, proyecto, "00:00", "acabados", numEmpleado, estacionSeleccionada);
            } else {
                rev.actualizarPlanos(con, plano, estacionSeleccionada.toUpperCase());
                rev.transaccionTerminarPlano(con, plano, proyecto, "00:00", "acabados", numEmpleado, estacionSeleccionada);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error: " + e, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public final void verDatos(String sql) {
        try {
            limpiarTabla();
            Connection con;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            String datos[] = new String[10];
            DefaultTableModel miModelo = (DefaultTableModel) Tabla1.getModel();
            int cont = 0;
            while (rs.next()) {
                datos[0] = rs.getString("Plano");
                datos[1] = rs.getString("Proyecto");
                miModelo.addRow(datos);
                cont++;
            }
            lblConteo.setText("Cantidad de Planos: " + cont);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al ver datos calidad: " + e, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public String getProyecto(String plano, Connection con) throws SQLException {
        Statement st = con.createStatement();
        String sql = "select proyecto from proyectos where proyecto like '" + plano + "%'";
        ResultSet rs = st.executeQuery(sql);
        while (rs.next()) {
            return rs.getString("Proyecto");
        }
        return null;
    }

    public final void verProyectos() {
        try {
            Connection con;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            String sql = "select proyecto from proyectos";
            ResultSet rs = st.executeQuery(sql);
            au = new TextAutoCompleter(jTextField1);
            while (rs.next()) {
                au.addItem(rs.getString("proyecto"));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al ver proyectos: " + e, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public String obtenerCaracter(String plano) {
        String texto = plano;
        
        Pattern pattern = Pattern.compile("[^0-9a-zA-Z ]");
        Matcher matcher = pattern.matcher(texto);

        while (matcher.find()) {
            return  matcher.group();
        }
        return null;
    }
    
    public String formatear(String plano, Connection con) throws SQLException {
        String caracter = obtenerCaracter(plano);
        String spl[] = plano.split(" ");
        if (caracter != null && plano.contains(caracter)) {
            return plano.substring(0, plano.indexOf(caracter));
        } else if (spl.length == 3) {
            return plano;
        } else if (spl.length == 2) {
            return getProyecto(spl[0], con) + " " + spl[1];
        } else {
            return null;
        }
    }
    
    public String validarPlano(Connection con, String proyecto) throws SQLException{
        Statement st = con.createStatement();
        String sql = "select proyecto from proyectos where proyecto like '" + proyecto + "%'";
        ResultSet rs = st.executeQuery(sql);
        String proyectoRet = null;
        while (rs.next()) {
            proyectoRet = rs.getString("proyecto");
        }
        return proyectoRet;
    }
    
    public void limpiarFormulario() {
        txtPlano.setText("");
        txtProyecto.setText("");
    }
    
    public Acabados(String numEmpleado) {
        initComponents();
        this.numEmpleado = numEmpleado;
        fechaFinal();
        limpiarTabla();
        verDatos();
        verProyectos();
        
        txtNumero.setEnabled(false);
        txtNombre.setEnabled(false);
        txtPuesto.setEnabled(false);
        panelPiezas.setVisible(false);
        lblEstado.setText("SIN SELECCIONAR");
        lblEstado.setForeground(Color.red);
        
        ((javax.swing.plaf.basic.BasicInternalFrameUI) this.getUI()).setNorthPane(null);
    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupMenu1 = new javax.swing.JPopupMenu();
        terminarPlanos = new javax.swing.JMenuItem();
        jPanel16 = new javax.swing.JPanel();
        PanelNorth = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        jPanel19 = new javax.swing.JPanel();
        btnSalir = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        panelCentral = new javax.swing.JPanel();
        jPanel20 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        txtEmpleado = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtCodigo = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jPanel21 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtPlano = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtProyecto = new javax.swing.JLabel();
        rSPanelRound1 = new rojeru_san.rspanel.RSPanelRound();
        jLabel20 = new javax.swing.JLabel();
        panelTipo = new rojeru_san.rspanel.RSPanelRound();
        lblTipo = new javax.swing.JLabel();
        lblRevision = new javax.swing.JLabel();
        panelPiezas = new rojeru_san.rspanel.RSPanelRound();
        lblPiezas = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        lblCrono = new javax.swing.JLabel();
        lblCrono1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtNumero = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtPuesto = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        lblEstado = new javax.swing.JLabel();
        panelEste = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jPanel14 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        cmbEnviar = new RSMaterialComponent.RSComboBoxMaterial();
        jPanel10 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jPanel11 = new javax.swing.JPanel();
        btnPausa = new javax.swing.JButton();
        jPanel8 = new javax.swing.JPanel();
        btnGuardar = new javax.swing.JButton();
        jPanel15 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jPanel22 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        Tabla1 = new ColorRojo();
        jPanel25 = new javax.swing.JPanel();
        jLabel23 = new javax.swing.JLabel();
        jPanel26 = new javax.swing.JPanel();
        jLabel24 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        lblConteo = new javax.swing.JLabel();
        jPanel18 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        jPanel17 = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();

        terminarPlanos.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        terminarPlanos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/entrega-rapida.png"))); // NOI18N
        terminarPlanos.setText("Seleccionar estacion                          ");
        terminarPlanos.setEnabled(false);
        terminarPlanos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                terminarPlanosActionPerformed(evt);
            }
        });
        jPopupMenu1.add(terminarPlanos);

        setBackground(new java.awt.Color(255, 255, 255));
        setBorder(null);
        getContentPane().setLayout(new java.awt.BorderLayout(0, 20));

        jPanel16.setBackground(new java.awt.Color(255, 255, 255));
        jPanel16.setLayout(new java.awt.BorderLayout());

        PanelNorth.setBackground(new java.awt.Color(255, 255, 255));
        PanelNorth.setLayout(new java.awt.BorderLayout());

        jLabel17.setFont(new java.awt.Font("Roboto", 1, 40)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(0, 165, 252));
        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel17.setText("Acabados");
        PanelNorth.add(jLabel17, java.awt.BorderLayout.CENTER);

        jPanel19.setBackground(new java.awt.Color(255, 255, 255));

        btnSalir.setBackground(new java.awt.Color(255, 255, 255));

        jLabel16.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel16.setText("X");
        jLabel16.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jLabel16.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel16MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel16MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel16MouseExited(evt);
            }
        });
        btnSalir.add(jLabel16);

        jPanel19.add(btnSalir);

        PanelNorth.add(jPanel19, java.awt.BorderLayout.EAST);

        jPanel16.add(PanelNorth, java.awt.BorderLayout.NORTH);

        panelCentral.setBackground(new java.awt.Color(255, 255, 255));
        panelCentral.setLayout(new java.awt.BorderLayout());

        jPanel20.setBackground(new java.awt.Color(255, 255, 255));
        jPanel20.setLayout(new javax.swing.BoxLayout(jPanel20, javax.swing.BoxLayout.Y_AXIS));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new java.awt.GridLayout(5, 0, 20, 20));

        jLabel9.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("DATOS DE PLANO");
        jPanel1.add(jLabel9);

        jLabel1.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("NUMERO DE EMPLEADO");
        jPanel1.add(jLabel1);

        txtEmpleado.setBackground(new java.awt.Color(255, 255, 255));
        txtEmpleado.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtEmpleado.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtEmpleado.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        txtEmpleado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEmpleadoActionPerformed(evt);
            }
        });
        jPanel1.add(txtEmpleado);

        jLabel10.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("CODIGO DE BARRAS");
        jPanel1.add(jLabel10);

        txtCodigo.setBackground(new java.awt.Color(255, 255, 255));
        txtCodigo.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtCodigo.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCodigo.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        txtCodigo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodigoActionPerformed(evt);
            }
        });
        jPanel1.add(txtCodigo);

        jPanel20.add(jPanel1);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new java.awt.BorderLayout());

        jPanel21.setBackground(new java.awt.Color(255, 255, 255));
        jPanel21.setLayout(new java.awt.GridLayout(3, 0));

        jLabel2.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText("Numero de plano:");
        jPanel21.add(jLabel2);

        txtPlano.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        txtPlano.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txtPlano.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        jPanel21.add(txtPlano);

        jLabel4.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setText("Proyecto:");
        jPanel21.add(jLabel4);

        txtProyecto.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        txtProyecto.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txtProyecto.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        jPanel21.add(txtProyecto);

        rSPanelRound1.setColorBackground(new java.awt.Color(0, 102, 204));
        rSPanelRound1.setColorBorde(new java.awt.Color(0, 102, 204));

        jLabel20.setFont(new java.awt.Font("Roboto", 1, 20)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(255, 255, 255));
        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel20.setText("TIPO:  ");
        rSPanelRound1.add(jLabel20);

        jPanel21.add(rSPanelRound1);

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

        jPanel21.add(panelTipo);

        jPanel2.add(jPanel21, java.awt.BorderLayout.CENTER);

        panelPiezas.setColorBackground(new java.awt.Color(255, 102, 0));
        panelPiezas.setColorBorde(new java.awt.Color(255, 102, 0));

        lblPiezas.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        lblPiezas.setForeground(new java.awt.Color(255, 255, 255));
        lblPiezas.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblPiezas.setText("Pieza realizada 3 veces");
        panelPiezas.add(lblPiezas);

        jPanel2.add(panelPiezas, java.awt.BorderLayout.SOUTH);

        jPanel20.add(jPanel2);

        jPanel5.setLayout(new java.awt.BorderLayout());

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setLayout(new java.awt.GridLayout(1, 0));

        jLabel13.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IconoC/letra-h.png"))); // NOI18N
        jPanel6.add(jLabel13);

        jLabel14.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IconoC/letra-m.png"))); // NOI18N
        jPanel6.add(jLabel14);

        jLabel15.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IconoC/letra-s.png"))); // NOI18N
        jPanel6.add(jLabel15);

        jPanel5.add(jPanel6, java.awt.BorderLayout.PAGE_START);

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setLayout(new java.awt.BorderLayout());

        lblCrono.setFont(new java.awt.Font("Roboto", 1, 72)); // NOI18N
        lblCrono.setForeground(new java.awt.Color(0, 102, 102));
        lblCrono.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblCrono.setText("00:00:00");
        jPanel7.add(lblCrono, java.awt.BorderLayout.CENTER);

        lblCrono1.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        lblCrono1.setForeground(new java.awt.Color(0, 102, 102));
        lblCrono1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblCrono1.setText("0000");
        jPanel7.add(lblCrono1, java.awt.BorderLayout.SOUTH);

        jPanel5.add(jPanel7, java.awt.BorderLayout.CENTER);

        jPanel20.add(jPanel5);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setLayout(new java.awt.GridLayout(7, 0, 20, 20));

        jLabel3.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("DATOS DE EMPLEADO");
        jPanel3.add(jLabel3);

        jLabel7.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("NUMERO DE EMPLEADO");
        jPanel3.add(jLabel7);

        txtNumero.setBackground(new java.awt.Color(255, 255, 255));
        txtNumero.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtNumero.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        jPanel3.add(txtNumero);

        jLabel6.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("NOMBRE");
        jPanel3.add(jLabel6);

        txtNombre.setBackground(new java.awt.Color(255, 255, 255));
        txtNombre.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtNombre.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        jPanel3.add(txtNombre);

        jLabel8.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("PUESTO");
        jPanel3.add(jLabel8);

        txtPuesto.setBackground(new java.awt.Color(255, 255, 255));
        txtPuesto.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtPuesto.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        jPanel3.add(txtPuesto);

        jPanel20.add(jPanel3);

        panelCentral.add(jPanel20, java.awt.BorderLayout.CENTER);

        jLabel21.setText("                 ");
        panelCentral.add(jLabel21, java.awt.BorderLayout.WEST);

        jLabel22.setText("                 ");
        panelCentral.add(jLabel22, java.awt.BorderLayout.EAST);

        jPanel16.add(panelCentral, java.awt.BorderLayout.CENTER);

        jPanel12.setBackground(new java.awt.Color(255, 255, 255));
        jPanel12.setLayout(new java.awt.BorderLayout());

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setLayout(new java.awt.BorderLayout());

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel11.setText("ESTADO");
        jPanel4.add(jLabel11, java.awt.BorderLayout.CENTER);

        lblEstado.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jPanel4.add(lblEstado, java.awt.BorderLayout.LINE_END);

        jPanel12.add(jPanel4, java.awt.BorderLayout.NORTH);

        panelEste.setBackground(new java.awt.Color(255, 255, 255));
        panelEste.setLayout(new java.awt.BorderLayout());

        jPanel13.setBackground(new java.awt.Color(255, 255, 255));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        jLabel5.setText("  ");
        jPanel13.add(jLabel5);

        panelEste.add(jPanel13, java.awt.BorderLayout.NORTH);

        jPanel14.setBackground(new java.awt.Color(255, 255, 255));
        jPanel14.setLayout(new java.awt.GridLayout(4, 0));

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));

        cmbEnviar.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Seleccionar", "Maquinados", "Calidad", "Cortes" }));
        cmbEnviar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbEnviarActionPerformed(evt);
            }
        });
        jPanel9.add(cmbEnviar);

        jPanel14.add(jPanel9);

        jPanel10.setBackground(new java.awt.Color(255, 255, 255));

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
        jPanel10.add(jButton1);

        jPanel14.add(jPanel10);

        jPanel11.setBackground(new java.awt.Color(255, 255, 255));

        btnPausa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ImgAnimacion/pausa_48.png"))); // NOI18N
        btnPausa.setBorder(null);
        btnPausa.setBorderPainted(false);
        btnPausa.setContentAreaFilled(false);
        btnPausa.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnPausa.setEnabled(false);
        btnPausa.setFocusPainted(false);
        btnPausa.setPreferredSize(new java.awt.Dimension(70, 70));
        btnPausa.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/ImgAnimacion/pausa_48.png"))); // NOI18N
        btnPausa.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/ImgAnimacion/pausa_64.png"))); // NOI18N
        btnPausa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPausaActionPerformed(evt);
            }
        });
        jPanel11.add(btnPausa);

        jPanel14.add(jPanel11);

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));

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
        jPanel8.add(btnGuardar);

        jPanel14.add(jPanel8);

        panelEste.add(jPanel14, java.awt.BorderLayout.CENTER);

        jPanel15.setBackground(new java.awt.Color(255, 255, 255));

        jLabel12.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        jLabel12.setText("  ");
        jPanel15.add(jLabel12);

        panelEste.add(jPanel15, java.awt.BorderLayout.SOUTH);

        jPanel12.add(panelEste, java.awt.BorderLayout.CENTER);

        jPanel16.add(jPanel12, java.awt.BorderLayout.EAST);

        jPanel22.setBackground(new java.awt.Color(255, 255, 255));
        jPanel22.setLayout(new java.awt.BorderLayout(0, 5));

        Tabla1.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
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
        Tabla1.setComponentPopupMenu(jPopupMenu1);
        Tabla1.setFocusable(false);
        Tabla1.setRowHeight(25);
        Tabla1.setSelectionBackground(new java.awt.Color(110, 201, 255));
        Tabla1.getTableHeader().setReorderingAllowed(false);
        Tabla1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Tabla1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(Tabla1);

        jPanel22.add(jScrollPane1, java.awt.BorderLayout.WEST);

        jPanel25.setBackground(new java.awt.Color(255, 255, 255));
        jPanel25.setLayout(new java.awt.BorderLayout(0, 10));

        jLabel23.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(0, 102, 204));
        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel23.setText("Planos en estacion de acabados");
        jPanel25.add(jLabel23, java.awt.BorderLayout.NORTH);

        jPanel26.setBackground(new java.awt.Color(255, 255, 255));
        jPanel26.setLayout(new java.awt.BorderLayout());

        jLabel24.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(0, 102, 204));
        jLabel24.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel24.setText("Ver por proyecto");
        jPanel26.add(jLabel24, java.awt.BorderLayout.CENTER);

        jTextField1.setBackground(new java.awt.Color(255, 255, 255));
        jTextField1.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        jTextField1.setForeground(new java.awt.Color(51, 51, 51));
        jTextField1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField1.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(204, 204, 204)));
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });
        jPanel26.add(jTextField1, java.awt.BorderLayout.SOUTH);

        jPanel25.add(jPanel26, java.awt.BorderLayout.CENTER);

        jPanel22.add(jPanel25, java.awt.BorderLayout.NORTH);

        lblConteo.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        lblConteo.setForeground(new java.awt.Color(51, 51, 51));
        lblConteo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblConteo.setText("Cantidad de Planos: ");
        jPanel22.add(lblConteo, java.awt.BorderLayout.PAGE_END);

        jPanel16.add(jPanel22, java.awt.BorderLayout.WEST);

        getContentPane().add(jPanel16, java.awt.BorderLayout.CENTER);

        jPanel18.setBackground(new java.awt.Color(255, 255, 255));

        jLabel18.setText("       ");
        jPanel18.add(jLabel18);

        getContentPane().add(jPanel18, java.awt.BorderLayout.WEST);

        jPanel17.setBackground(new java.awt.Color(255, 255, 255));

        jLabel19.setText("       ");
        jPanel17.add(jLabel19);

        getContentPane().add(jPanel17, java.awt.BorderLayout.EAST);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void Tabla1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Tabla1MouseClicked
        int fila = Tabla1.getSelectedRow();
        try{
          char comillas = '"';
          Runtime.getRuntime().exec("cmd /c start \\\\192.168.100.40\\01 Project\\04 DISENO\\PDF_Planos\\"+comillas+Tabla1.getValueAt(fila, 2)+comillas+"\\"+comillas+Tabla1.getValueAt(fila, 1)+".pdf"+comillas);
          
          }catch(IOException  e){
              JOptionPane.showMessageDialog(this, "PDF NO ENCONTRADO");
          }
    }//GEN-LAST:event_Tabla1MouseClicked

    private void jLabel16MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel16MouseClicked
        dispose();
    }//GEN-LAST:event_jLabel16MouseClicked

    private void jLabel16MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel16MouseEntered
        btnSalir.setBackground(Color.red);
        jLabel16.setForeground(Color.white);
    }//GEN-LAST:event_jLabel16MouseEntered

    private void jLabel16MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel16MouseExited
        btnSalir.setBackground(Color.white);
        jLabel16.setForeground(Color.black);
    }//GEN-LAST:event_jLabel16MouseExited

    private void txtEmpleadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEmpleadoActionPerformed
        try{
            int fila = Tabla1.getSelectedRow();
            Connection con = null;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            String datos[] = new String[7];
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

            }

        }catch(SQLException e){

            JOptionPane.showMessageDialog(this, "","",JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_txtEmpleadoActionPerformed

    private void txtCodigoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodigoActionPerformed
//        verPlano();
        try {
            Connection con;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            String form = formatear(txtCodigo.getText(), con);
            String sql = "select Proyecto, Plano, Cantidad, Material from planos where Plano like '" + form + "'";
            ResultSet rs = st.executeQuery(sql);
            String plano = null;
            limpiarFormulario();
            while (rs.next()) {
                plano = rs.getString("Plano");
                txtPlano.setText(plano);
                txtProyecto.setText(rs.getString("Proyecto"));
            }
            txtCodigo.setText("");
            if (plano == null) {
                int prim = form.indexOf(" ");
                String proyecto = validarPlano(con, form.substring(0, prim));
                if (proyecto != null) {
                    txtPlano.setText(form);
                    txtProyecto.setText(proyecto);
                } else {
                    JOptionPane.showMessageDialog(this, "El plano que ingresaste no existe", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error: " + e, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_txtCodigoActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        limpiarTabla();
        verDatos();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        enviarPlano(txtPlano.getText(), txtProyecto.getText());
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void terminarPlanosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_terminarPlanosActionPerformed
        for (int i = 0; i < Tabla1.getSelectedRows().length; i++) {
            int fila = Tabla1.getSelectedRows()[i];
            enviarPlano(Tabla1.getValueAt(fila, 0).toString(), Tabla1.getValueAt(fila, 1).toString());
        }
    }//GEN-LAST:event_terminarPlanosActionPerformed

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        if (jTextField1.getText().equals("")) {
            verDatos("select Estado, id, plano, proyecto from planos where Estado like 'ACABADOS%' order by id desc");
        } else {
            verDatos("select Estado, id, plano, proyecto from planos where Estado like 'ACABADOS%' and Proyecto like '" + jTextField1.getText() + "%' order by id desc");
        }
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void cmbEnviarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbEnviarActionPerformed
        if (cmbEnviar.getSelectedIndex() == 0) {
            terminarPlanos.setEnabled(false);
        } else {
            terminarPlanos.setEnabled(true);
            terminarPlanos.setText("Enviar planos a: " + cmbEnviar.getSelectedItem().toString() + "                          ");
        }
    }//GEN-LAST:event_cmbEnviarActionPerformed

    private void btnPausaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPausaActionPerformed

    }//GEN-LAST:event_btnPausaActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PanelNorth;
    private javax.swing.JTable Tabla1;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnPausa;
    private javax.swing.JPanel btnSalir;
    private RSMaterialComponent.RSComboBoxMaterial cmbEnviar;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
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
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel25;
    private javax.swing.JPanel jPanel26;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JLabel lblConteo;
    private javax.swing.JLabel lblCrono;
    private javax.swing.JLabel lblCrono1;
    private javax.swing.JLabel lblEstado;
    private javax.swing.JLabel lblPiezas;
    private javax.swing.JLabel lblRevision;
    private javax.swing.JLabel lblTipo;
    private javax.swing.JPanel panelCentral;
    private javax.swing.JPanel panelEste;
    private rojeru_san.rspanel.RSPanelRound panelPiezas;
    private rojeru_san.rspanel.RSPanelRound panelTipo;
    private rojeru_san.rspanel.RSPanelRound rSPanelRound1;
    private javax.swing.JMenuItem terminarPlanos;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JTextField txtEmpleado;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtNumero;
    private javax.swing.JLabel txtPlano;
    private javax.swing.JLabel txtProyecto;
    private javax.swing.JTextField txtPuesto;
    // End of variables declaration//GEN-END:variables
}
