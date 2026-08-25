package pruebas;
import Conexiones.Conexion;
import Controlador.maquinados.reportarPlano;
import Controlador.maquinados.revisarPlanos;
import VentanaEmergente.Maquinados.elegirRevision;
import java.awt.Color;
import java.awt.Font;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Stack;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
public final class Torno extends JInternalFrame {
    int mili = 0;
    int segundos;
    int minutos = 0;
    int horas = 0;
    boolean estado = false;
    public String fechaInicio;
    public String pl;
   int contReporte;
   
   elegirRevision elegirRevision;
    
    public void activar() {
        estado = true;
        String datos[] = new String[10];
        try{
        Connection con = null;
        Conexion con1 = new Conexion();
        con = con1.getConnection();
        Statement st = con.createStatement();
        String sql = "select * from torno where Proyecto like '"+pl+"'";
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

    public String buscar(){
        DefaultTableModel miModelo = (DefaultTableModel) Tabla1.getModel();
        String datos[] = new String[1000];
        datos[3] = null;
        try{
        int fila = Tabla1.getSelectedRow();
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
        Statement st7 = con.createStatement();
        
        String sql = "select * from Planos where Plano like '"+txtCodigo.getText()+"'";
        ResultSet rs = st.executeQuery(sql);
        while(rs.next())
        {
        int cont = 0;
        datos[0] = rs.getString("Prioridad");
        datos[1] = rs.getString("Plano");
        datos[2] = rs.getString("Proyecto");
        String acabados[] = new String[10];
            String calidad[] = new String[10];
            String cnc[] = new String [10];
            String fresa[] = new String [10];
            String cortes[] = new String [10];
            String torno[] = new String [10];
            String trata[] = new String [10];
            String id = datos[1];
            
            String sq = "select * from Calidad where Proyecto like '%"+datos[1]+"%'";
            ResultSet r = st1.executeQuery(sq);
            String sql1 = "select * from Acabados where Proyecto like '%"+datos[1]+"%'";
            ResultSet rs1 = st2.executeQuery(sql1);
            String sql2 = "select * from CNC where Proyecto like '%"+datos[1]+"%'";
            ResultSet rs2 = st3.executeQuery(sql2);
            String sql3 = "select * from Fresadora where Proyecto like '%"+datos[1]+"%'";
            ResultSet rs3 = st4.executeQuery(sql3);
            String sql5 = "select * from Torno where Proyecto like '%"+datos[1]+"%'";
            ResultSet rs5 = st5.executeQuery(sql5);
            String sql4 = "select * from Datos where Proyecto like '%"+datos[1]+"%'";
            ResultSet rs4 = st6.executeQuery(sql4);
            String sql6 = "select * from Trata where Proyecto like '%"+datos[1]+"%'";
            ResultSet rs6 = st7.executeQuery(sql6);
            while(r.next()){
                calidad[0] = r.getString("Id");
                calidad[1] = r.getString("Proyecto");
                calidad[2] = r.getString("Plano");
                calidad[3] = r.getString("Terminado");
                calidad[4] = r.getString("Tratamiento");
                calidad[5] = r.getString("Prioridad");
            }

            while(rs1.next()){
                acabados[0] = rs1.getString("Id");
                acabados[1] = rs1.getString("Proyecto");
                acabados[2] = rs1.getString("Plano");
                acabados[3] = rs1.getString("Terminado");
                acabados[5] = rs1.getString("Prioridad");
            }
            while(rs2.next()){
                cnc[0] = rs2.getString("Id");
                cnc[1] = rs2.getString("Proyecto");
                cnc[2] = rs2.getString("Plano");
                cnc[3] = rs2.getString("Terminado");
                cnc[5] = rs2.getString("Prioridad");
            }
            while(rs3.next()){
                fresa[0] = rs3.getString("Id");
                fresa[1] = rs3.getString("Proyecto");
                fresa[2] = rs3.getString("Plano");
                fresa[3] = rs3.getString("Terminado");
                fresa[5] = rs3.getString("Prioridad");
            }
            while(rs4.next()){
                cortes[0] = rs4.getString("Id");
                cortes[1] = rs4.getString("Proyecto");
                cortes[2] = rs4.getString("Plano");
                cortes[3] = rs4.getString("Terminado");
                cortes[5] = rs4.getString("Prioridad");
            }
            while(rs5.next()){
                torno[0] = rs5.getString("Id");
                torno[1] = rs5.getString("Proyecto");
                torno[2] = rs5.getString("Plano");
                torno[3] = rs5.getString("Terminado");
                torno[5] = rs5.getString("Prioridad");
            }
            while(rs6.next()){
                trata[0] = rs6.getString("Id");
                trata[1] = rs6.getString("Proyecto");
                trata[2] = rs6.getString("Plano");
                trata[3] = rs6.getString("Terminado");
                trata[5] = rs6.getString("Prioridad");
            }
            
            if(id.equals(cortes[1]) && cortes[3].equals("NO")){
                datos[3] = "CORTES";
            }else if(id.equals(cnc[1]) && cnc[3].equals("NO")){
                datos[3] = "CNC";
            }else if(id.equals(fresa[1]) && fresa[3].equals("NO")){
                datos[3] = "FRESADORA";
            }else if(id.equals(torno[1]) && torno[3].equals("NO")){
                datos[3] = "TORNO";
            }else if(id.equals(acabados[1]) && acabados[3].equals("NO")){
                datos[3] = "ACABADOS";
            }else if(id.equals(trata[1]) && trata[3].equals("NO")){
               datos[3] = "TRATAMIENTO";
            }else if(id.equals(trata[1]) && trata[3].equals("TERMINADO")){
                datos[3] = "TERMINADO TRATAMIENTO";
            }else if(id.equals(calidad[1]) && calidad[3].equals("NO")){
                datos[3] = "CALIDAD";
            }else if(id.equals(calidad[1]) && calidad[3].equals("SI") &&"NO".equals(calidad[4])){
                datos[3] = "TERMINADO CALIDAD";
            }else{
                datos[3] = "LIBERACION";
            }
            
        cont = cont+1;
        }
        
        }catch(SQLException e){
        JOptionPane.showMessageDialog(this, "NO SE PUEDEN MOSTRAR LOS DATOS"+" "+e,"ERROR",JOptionPane.ERROR_MESSAGE);
        }
        return datos[3];
    }

    public void datos(String plano){
        Date fe = new Date();
        SimpleDateFormat nuevo = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String fec = nuevo.format(fe);
        JOptionPane.showMessageDialog(this, "PLANO EMPEZADO");
        limpiarTabla();
        verDatos();
        activar();
        txtProyecto.setText(plano);
        txtCodigo.setText("");
        txtFecha.setText(fec);
        lblEstado.setText("EN CURSO");
        lblEstado.setForeground(Color.green);
    }

    public String cronometro(String fecha1, String fecha2){
   int hora1,hora2,minuto1,minuto2;
   int fecha9 = 0,fecha10 = 0;
   String f1,f2,f4,f5,fin = "";
   
   
   f1 = fecha1.substring(11,13);
   f2 = fecha1.substring(14,16);
   f4 = fecha2.substring(11,13);
   f5 = fecha2.substring(14,16);
   
   hora1 = Integer.parseInt(f1);
   minuto1 = Integer.parseInt(f2);
   hora2= Integer.parseInt(f4);
   minuto2 = Integer.parseInt(f5);
   
   fecha9 = (hora2 - hora1);
   if(hora2 >= hora1 && minuto2 >= minuto1){
       if((fecha9) < 10 && (minuto2 - minuto2) < 10){
       fin = "0"+(fecha9)+":"+"0"+(minuto2-minuto1);
       }else if((fecha9) < 10){
       fin = "0"+(fecha9)+":"+(minuto2-minuto1);
       }else if((minuto2 - minuto1) < 10){
       fin = (fecha9)+":"+"0"+(minuto2-minuto1);
       }
       
   }else{
   if(minuto2 < minuto1){
   fecha10 = (60 - minuto1) + minuto2;
   fecha9 = fecha9-1;
   if(fecha9 < 10 && (fecha10) < 10){
       fin = "0"+fecha9+":"+"0"+fecha10;
       }else if((fecha9) < 10){
       fin = "0"+(fecha9)+":"+(fecha10);
       }else if((fecha10) < 10){
       fin = (fecha9)+":"+"0"+(fecha10);
       }
   }else if(minuto2 > minuto1){
   fecha10 = (minuto2 - minuto1);
   if(fecha9 < 10 && (fecha10) < 10){
       fin = "0"+fecha9+":"+"0"+fecha10;
       }else if((fecha9) < 10){
       fin = "0"+(fecha9)+":"+(fecha10);
       }else if((fecha10) < 10){
       fin = (fecha9)+":"+"0"+(fecha10);
       }
   }
   }
   return fin;
   }
   
    public void limpiarTabla(){
        Tabla1 = new ColorRojo();
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
        Tabla1.getTableHeader().setFont(new Font("Roboto", Font.BOLD, 16));
        Tabla1.setFont(new java.awt.Font("Tahoma", 0, 16));
        Tabla1.getTableHeader().setOpaque(false);
        Tabla1.getTableHeader().setBackground(new Color(254,254,254));
        Tabla1.getTableHeader().setForeground(new Color(0, 78, 171));
        Tabla1.setRowHeight(25);
        Tabla1.setShowGrid(false);
        
        jScrollPane1.getViewport().setBackground(Color.white);
        jScrollPane1.setForeground(Color.white);
        jScrollPane1.getViewport().setForeground(Color.white);
        jScrollPane1.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(254, 254, 254)));
        jScrollPane1.setBackground(Color.white);
        Tabla1.setRowHeight(25);
        Tabla1.setShowGrid(false);
        jScrollPane1.setViewportView(Tabla1);
        if (Tabla1.getColumnModel().getColumnCount() > 0) {
            Tabla1.getColumnModel().getColumn(0).setResizable(false);
            Tabla1.getColumnModel().getColumn(1).setResizable(false);
            Tabla1.getColumnModel().getColumn(2).setResizable(false);
        }
    }

    public void verDatos(){
        DefaultTableModel miModelo = (DefaultTableModel) Tabla1.getModel();
            try{
            Connection con = null;

            Conexion con1 = new Conexion();
            con = con1.getConnection();

            String datos[] = new String[6];
            String sql = "select * from Torno where Terminado like 'NO'";

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
        panelPiezas.setVisible(false);
        txtProyecto.setText("");
        txtFecha.setText("");
    }
    
    public void borrar(){
    
        txtProyecto.setText("");
        txtFecha.setText("");
        lblEstado.setText("SIN SELECCIONAR");
        lblEstado.setForeground(Color.red);
    
    }
    
    public void verPlano()  {
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
                    Date fe = new Date();
                    SimpleDateFormat nuevo = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                    String fec = nuevo.format(fe);
                    pl =  plano;
                    if(txtNumero.getText().equals("")){
                        JOptionPane.showMessageDialog(this, "FAVOR DE LLENAR NUMERO DE EMPLEADO","ADVERTENCIA",JOptionPane.WARNING_MESSAGE);
                    }else{
                        try{
                            Connection con = null;
                            Conexion con1 = new Conexion();
                            con = con1.getConnection();
                            String datos[] = new String[10];
                            Statement st = con.createStatement();
                            Statement st1 = con.createStatement();
                            Statement st2 = con.createStatement();
                            String sql = "UPDATE Torno SET Proyecto = ?, Plano = ?, FechaInicio = ?, FechaFinal = ?, Terminado = ?, Estado = ?, Cronometro = ?, Prioridad = ?, Empleado = ? WHERE Proyecto = ? and Revision = ?";
                            String sql1 = "select * from Torno where Proyecto like '"+(plano)+"' and Revision like '"+noRevision+"'";
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
                                txtProyecto.setText(plano);
                                txtCodigo.setText("");
                                String a;
                                if(datos[8] == null || datos[8].equals("")){
                                    a = txtNumero.getText();
                                }else{
                                    a = datos[8] + "," + txtNumero.getText();
                                }
                                if(datos[3].equals("")){
                                    pst.setString(1, txtProyecto.getText());
                                    pst.setString(2, datos[2]);
                                    pst.setString(3, fec);
                                    pst.setString(4, "");
                                    pst.setString(5, "NO");
                                    pst.setString(6, datos[0]);
                                    pst.setString(7, datos[6]);
                                    pst.setString(8, datos[7]);
                                    pst.setString(9, a);
                                    pst.setString(10, txtProyecto.getText());
                                    pst.setString(11, noRevision);
                                    int n1 = pst.executeUpdate();
                                    if (n1 > 0){
                                        limpiarTabla();
                                        verDatos();
                                        lblEstado.setText("EN CURSO");
                                        lblEstado.setForeground(Color.green);
                                        txtFecha.setText(fec);
                                        activar();
                                    }
                                }else if(datos[3] != (null) && datos[5].equals("NO")){
                                    txtFecha.setText(datos[3]);
                                    txtCodigo.setText("");
                                    lblEstado.setText("EN CURSO");
                                    lblEstado.setForeground(Color.green);
                                    activar();
                                }
                                contReporte = 0;
                            }else {
                                revisarPlanos report = new revisarPlanos();
                                String b = report.buscar(plano, con);
                                if(b== (null)){
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
                                    int opc = JOptionPane.showConfirmDialog(this, "EL PLANO "+plano+" ESTA EN "+b+", DESEAS TRAERLO A TORNO?","INFO",JOptionPane.YES_NO_OPTION);
                                    if(opc == 0){
                                        String acC = "update Datos set Terminado = ? where Proyecto = ? and Revision = ?";
                                        String acTorno = "update Torno set Terminado = ?,FechaInicio = ?,FechaFinal = ? where Proyecto = ? and Revision = ?";
                                        String acFresa = "update Fresadora set Terminado = ? where Proyecto = ? and Revision = ?";
                                        String acCnc = "update CNC set Terminado = ? where Proyecto = ? and Revision = ?";
                                        String acAcabados = "update Acabados set Terminado = ? where Proyecto = ? and Revision = ?";
                                        String acTrata = "update Trata set Terminado = ? where Proyecto = ? and Revision = ?";
                                        String acCalidad = "update Calidad set Terminado = ?, Tratamiento = ? where Proyecto = ? and Revision = ?";
                                        String insertar = "insert into Torno (Proyecto, Plano, FechaInicio,FechaFinal,Terminado,Estado,Cronometro,Prioridad,Empleado,Revision) values (?,?,?,?,?,?,?,?,?,?)";
                                        PreparedStatement pst1 = con.prepareStatement(acC);
                                        PreparedStatement pst2 = con.prepareStatement(acTorno);
                                        PreparedStatement pst3 = con.prepareStatement(acFresa);
                                        PreparedStatement pst4 = con.prepareStatement(acCnc);
                                        PreparedStatement pst5 = con.prepareStatement(acAcabados);
                                        PreparedStatement pst6 = con.prepareStatement(acTrata);
                                        PreparedStatement pst7 = con.prepareStatement(acCalidad);
                                        PreparedStatement pst8 = con.prepareCall(insertar);

                                        String ver1 = "select * from Torno where Proyecto like '"+plano+"' and Revision like '"+noRevision+"'";
                                        String ver2 = "select * from Planos where Plano like '"+plano+"' and Revision like '"+noRevision+"'";
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
                                            pst1.setString(2, plano);
                                            pst1.setString(3, noRevision);

                                            int m = pst1.executeUpdate();
                                            if(n > 0 && m > 0){
                                                datos(plano);
                                            }
                                        }else if(b.equals("FRESADORA")){
                                            pst3.setString(1, "SI");
                                            pst3.setString(2, plano);
                                            pst3.setString(3, noRevision);

                                            int m = pst3.executeUpdate();
                                            if(n > 0 && m > 0){
                                                datos(plano);
                                            }
                                        }else if(b.equals("CNC")){
                                            pst4.setString(1, "SI");
                                            pst4.setString(2, plano);
                                            pst4.setString(3, noRevision);

                                            int m = pst4.executeUpdate();
                                            if(n > 0 && m > 0){
                                                datos(plano);
                                            }
                                        }else if(b.equals("ACABADOS")){
                                            pst5.setString(1, "SI");
                                            pst5.setString(2, plano);
                                            pst5.setString(3, noRevision);

                                            int m = pst5.executeUpdate();
                                            if(n > 0 && m > 0){
                                                datos(plano);
                                            }
                                        }else if(b.equals("TRATAMIENTO")){
                                            pst6.setString(1, "SI");
                                            pst6.setString(2, plano);
                                            pst6.setString(3, noRevision);

                                            int m = pst6.executeUpdate();
                                            if(n > 0 && m > 0){
                                                datos(plano);
                                            }
                                        }else if(b.equals("CALIDAD")){
                                            pst7.setString(1, "SI");
                                            pst7.setString(2, "SI");
                                            pst7.setString(3, plano);
                                            pst7.setString(4, noRevision);

                                            int m = pst7.executeUpdate();
                                            if(n > 0 && m > 0){
                                                datos(plano);
                                            }
                                        }else if(b.equals("TERMINADO CALIDAD")){
                                            pst7.setString(1, "SI");
                                            pst7.setString(2, "SI");
                                            pst7.setString(3, plano);
                                            pst7.setString(4, noRevision);

                                            int m = pst7.executeUpdate();
                                            if(n > 0 && m > 0){
                                                datos(plano);
                                            }
                                        }else if(b.equals("TERMINADO TRATAMIENTO")){
                                            pst7.setString(1, "SI");
                                            pst7.setString(2, "SI");
                                            pst7.setString(3, plano);
                                            pst7.setString(4, noRevision);

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
    
    public void pausar(){
        Date fecha = new Date();
        SimpleDateFormat fec1 = new SimpleDateFormat ("dd/MM/yyyy HH:mm:ss");
        String fec = fec1.format(fecha);
        String f = "";
        try{
            Connection con = null;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            String sql = "UPDATE Torno SET Proyecto = ?, Plano = ?, FechaInicio = ?, FechaFinal = ?, Terminado = ?, Estado = ?, Cronometro = ?, Prioridad = ? WHERE Proyecto = ?";
            String ver = "select * from Torno where Proyecto like '"+txtProyecto.getText()+"'";
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = st.executeQuery(ver);
            String datos[] = new String[10];
            while(rs.next()){
                datos[1] = rs.getString("Proyecto");
                datos[2] = rs.getString("Plano");
                datos[3] = rs.getString("FechaInicio");
                datos[4] = rs.getString("FechaFinal");
                datos[5] = rs.getString("Terminado");
                datos[6] = rs.getString("Estado");
                datos[7] = rs.getString("Cronometro");
                datos[8] = rs.getString("Prioridad");
            }
            if(datos[1] != null){

                if(datos[7] != null){
                    String fecha1 = txtFecha.getText();
                    String fecha2 = fec;

                    int hora1,hora2,minuto1,minuto2;
                    int fecha9 = 0,fecha10 = 0;
                    String f1,f2,f4,f5,fin = "";

                    f1 = fecha1.substring(11,13);
                    f2 = fecha1.substring(14,16);
                    f4 = fecha2.substring(11,13);
                    f5 = fecha2.substring(14,16);

                    hora1 = Integer.parseInt(f1);
                    minuto1 = Integer.parseInt(f2);
                    hora2= Integer.parseInt(f4);
                    minuto2 = Integer.parseInt(f5);

                    fecha9 = (hora2 - hora1);
                    if(hora2 >= hora1 && minuto2 >= minuto1){
                        if((fecha9) < 10 && (minuto2 - minuto2) < 10){
                            fin = "0"+(fecha9)+":"+"0"+(minuto2-minuto1);
                        }else if((fecha9) < 10){
                            fin = "0"+(fecha9)+":"+(minuto2-minuto1);
                        }else if((minuto2 - minuto1) < 10){
                            fin = (fecha9)+":"+"0"+(minuto2-minuto1);
                        }

                    }else{
                        if(minuto2 < minuto1){
                            fecha10 = (60 - minuto1) + minuto2;
                            fecha9 = fecha9-1;
                            if(fecha9 < 10 && (fecha10) < 10){
                                fin = "0"+fecha9+":"+"0"+fecha10;
                            }else if((fecha9) < 10){
                                fin = "0"+(fecha9)+":"+(fecha10);
                            }else if((fecha10) < 10){
                                fin = (fecha9)+":"+"0"+(fecha10);
                            }
                        }else if(minuto2 > minuto1){
                            fecha10 = (minuto2 - minuto1);
                            if(fecha9 < 10 && (fecha10) < 10){
                                fin = "0"+fecha9+":"+"0"+fecha10;
                            }else if((fecha9) < 10){
                                fin = "0"+(fecha9)+":"+(fecha10);
                            }else if((fecha10) < 10){
                                fin = (fecha9)+":"+"0"+(fecha10);
                            }
                        }
                    }
                    String a = datos[7].substring(0,2);
                    String b = datos[7].substring(3,5);
                    String c = fin.substring(0,2);
                    String d = fin.substring(3,5);

                    int h1,m1,h2,m2;
                    h1 = Integer.parseInt(a);
                    m1 = Integer.parseInt(b);
                    h2 = Integer.parseInt(c);
                    m2 = Integer.parseInt(d);

                    int sumaH,sumaM;
                    sumaH = h1+h2;
                    sumaM = m1+m2;

                    if(sumaM >= 60){
                        sumaM = (sumaM - 60);
                        sumaH = sumaH + 1;
                    }

                    if((sumaH) < 10 && (sumaM) < 10){
                        f = "0"+(sumaH)+":"+"0"+(sumaM);
                    }else if((sumaH) < 10){
                        f = "0"+(sumaH)+":"+(sumaM);
                    } else if((sumaM) < 10){
                        f = (sumaH)+":"+"0"+(sumaM);
                    }
                }
                pst.setString(1, txtProyecto.getText());
                pst.setString(2, datos[2]);
                pst.setString(3, "");
                pst.setString(4, "");
                pst.setString(5, datos[5]);
                pst.setString(6, datos[6]);
                pst.setString(7, f);
                pst.setString(8, datos[8]);
                pst.setString(9, txtProyecto.getText());

                int n = pst.executeUpdate();

                if(n > 0){
                    limpiarTabla();
                    verDatos();
                    estado = false;
                    txtFecha.setText("");
                    txtProyecto.setText("");
                    panelPiezas.setVisible(false);
                    JOptionPane.showMessageDialog(this, "PLANO PAUSADO","EXITO",JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "ERROR AL PAUSAR "+e,"ERROR",JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void enviar(String revision){
        Date fecha = new Date();
        int fila = Tabla1.getSelectedRow();
        SimpleDateFormat fec1 = new SimpleDateFormat ("dd/MM/yyyy HH:mm:ss");
        String fec = fec1.format(fecha);
        if(txtNumero.getText().equals("")){
            JOptionPane.showMessageDialog(this, "DEBES LLENAR EL NUMERO DE EMPLEADO","ERROR",JOptionPane.ERROR_MESSAGE);
        }else if(txtProyecto.getText().equals("")){
            JOptionPane.showMessageDialog(this, "DEBES SELECCIONAR UN PROYECTO","ERROR",JOptionPane.ERROR_MESSAGE);
        }else{
            try{
                Connection con = null;
                Conexion con1 = new Conexion();
                con = con1.getConnection();
                estado = false;
                Statement st = con.createStatement();
                Statement st1 = con.createStatement();
                Statement st2 = con.createStatement();
                Statement st3 = con.createStatement();
                Statement st4 = con.createStatement();
                String sql =  "UPDATE Torno SET Proyecto = ?, Plano = ?, FechaInicio = ?, FechaFinal = ?, Terminado = ?, Estado = ?, Cronometro = ?, Prioridad = ?, Empleado = ? WHERE Proyecto = ? and Revision = ?";
                String sql1 = "insert into CNC (Proyecto,Plano,FechaInicio,FechaFinal,Terminado,Estado,Cronometro,Prioridad,Revision) values(?,?,?,?,?,?,?,?,?)";
                String sql2 = "insert into Datos (Proyecto,Plano,FechaInicio,FechaFinal,Terminado, Estado,Cronometro,Prioridad,Revision) values(?,?,?,?,?,?,?,?,?)";
                String sql4 = "insert into Fresadora (Proyecto,Plano,FechaInicio,FechaFinal,Terminado, Estado,Cronometro,Prioridad,Revision) values(?,?,?,?,?,?,?,?,?)";
                String sql5 = "insert into Acabados (Proyecto,Plano,FechaInicio,FechaFinal,Terminado, Estado,Cronometro,Prioridad,Revision) values(?,?,?,?,?,?,?,?,?)";
                PreparedStatement pst = con.prepareStatement(sql);
                PreparedStatement pst1 = con.prepareStatement(sql1);
                PreparedStatement pst2 = con.prepareStatement(sql2);
                PreparedStatement pst4 = con.prepareStatement(sql4);
                PreparedStatement pst5 = con.prepareStatement(sql5);

                String ver = "select * from CNC where Proyecto like '"+txtProyecto.getText()+"' and Revision like '"+revision+"'";
                String ver1 = "select * from Datos where Proyecto like '"+txtProyecto.getText()+"' and Revision like '"+revision+"'";
                String ver2 = "select * from Fresadora where Proyecto like '"+txtProyecto.getText()+"' and Revision like '"+revision+"'";
                String ver3 = "select * from Acabados where Proyecto like '"+txtProyecto.getText()+"' and Revision like '"+revision+"'";

                ResultSet rs = st.executeQuery(ver);
                ResultSet rs1 = st1.executeQuery(ver1);
                ResultSet rs2 = st2.executeQuery(ver2);
                ResultSet rs3 = st3.executeQuery(ver3);

                String ac = "update CNC set Proyecto = ?, Plano = ?, FechaInicio = ?, FechaFinal = ?, Terminado = ?, Estado = ?, Cronometro = ?, Prioridad = ? where Proyecto = ? and Revision = ?";
                String ac1 = "update Datos set Proyecto = ?, Plano = ?, FechaInicio = ?, FechaFinal = ?, Terminado = ?, Estado = ?, Cronometro = ?, Prioridad = ? where Proyecto = ? and Revision = ?";
                String ac2 = "update Fresadora set Proyecto = ?, Plano = ?, FechaInicio = ?, FechaFinal = ?, Terminado = ?, Estado = ?, Cronometro = ?, Prioridad = ? where Proyecto = ? and Revision = ?";
                String ac3 = "update Acabados set Proyecto = ?, Plano = ?, FechaInicio = ?, FechaFinal = ?, Terminado = ?, Estado = ?, Cronometro = ?, Prioridad = ? where Proyecto = ? and Revision = ?";
                PreparedStatement act = con.prepareStatement(ac);
                PreparedStatement act1 = con.prepareStatement(ac1);
                PreparedStatement act2 = con.prepareStatement(ac2);
                PreparedStatement act3 = con.prepareStatement(ac3);

                String ecnc = "select * from Torno where Proyecto like '"+txtProyecto.getText()+"' and Revision like '"+revision+"'";
                ResultSet eC = st4.executeQuery(ecnc);
                String cnc[] = new String[10];
                String acabados[] = new String[10];
                String fresa[] = new String[10];
                String torno[] = new String[10];
                String cortes[] = new String[10];
                while(eC.next()){
                    torno[1] = eC.getString("Estado");
                    torno[2] = eC.getString("FechaInicio");
                    torno[3] = eC.getString("FechaFinal");
                    torno[4] = eC.getString("Cronometro");
                    torno[5] = eC.getString("Plano");
                    torno[6] = eC.getString("Prioridad");
                    torno[7] = eC.getString("Empleado");
                }
                while(rs.next()){
                    cnc[1] = rs.getString("Proyecto");
                    cnc[2] = rs.getString("Estado");
                    cnc[3] = rs.getString("Cronometro");
                }
                while(rs3.next()){
                    acabados[1] = rs3.getString("Proyecto");
                    acabados[3] = rs3.getString("Cronometro");
                }
                while(rs2.next()){
                    fresa[1] = rs2.getString("Proyecto");
                    fresa[3] = rs2.getString("Cronometro");
                }
                while(rs1.next()){
                    cortes[1] = rs1.getString("Proyecto");
                    cortes[3] = rs1.getString("Cronometro");
                }

                if(torno[4] != "00:00"){
                    String fecha1 = txtFecha.getText();
                    String fecha2 = fec;

                    int hora1,hora2,minuto1,minuto2;
                    int fecha9 = 0,fecha10 = 0;
                    String f1,f2,f4,f5,fin = "";

                    f1 = fecha1.substring(11,13);
                    f2 = fecha1.substring(14,16);
                    f4 = fecha2.substring(11,13);
                    f5 = fecha2.substring(14,16);

                    hora1 = Integer.parseInt(f1);
                    minuto1 = Integer.parseInt(f2);
                    hora2= Integer.parseInt(f4);
                    minuto2 = Integer.parseInt(f5);

                    fecha9 = (hora2 - hora1);
                    if(hora2 >= hora1 && minuto2 >= minuto1){
                        if((fecha9) < 10 && (minuto2 - minuto2) < 10){
                            fin = "0"+(fecha9)+":"+"0"+(minuto2-minuto1);
                        }else if((fecha9) < 10){
                            fin = "0"+(fecha9)+":"+(minuto2-minuto1);
                        }else if((minuto2 - minuto1) < 10){
                            fin = (fecha9)+":"+"0"+(minuto2-minuto1);
                        }

                    }else{
                        if(minuto2 < minuto1){
                            fecha10 = (60 - minuto1) + minuto2;
                            fecha9 = fecha9-1;
                            if(fecha9 < 10 && (fecha10) < 10){
                                fin = "0"+fecha9+":"+"0"+fecha10;
                            }else if((fecha9) < 10){
                                fin = "0"+(fecha9)+":"+(fecha10);
                            }else if((fecha10) < 10){
                                fin = (fecha9)+":"+"0"+(fecha10);
                            }
                        }else if(minuto2 > minuto1){
                            fecha10 = (minuto2 - minuto1);
                            if(fecha9 < 10 && (fecha10) < 10){
                                fin = "0"+fecha9+":"+"0"+fecha10;
                            }else if((fecha9) < 10){
                                fin = "0"+(fecha9)+":"+(fecha10);
                            }else if((fecha10) < 10){
                                fin = (fecha9)+":"+"0"+(fecha10);
                            }
                        }
                    }
                    String a = torno[4].substring(0,2);
                    String b = torno[4].substring(3,5);
                    String c = fin.substring(0,2);
                    String d = fin.substring(3,5);

                    int h1,m1,h2,m2;
                    String f = "";
                    h1 = Integer.parseInt(a);
                    m1 = Integer.parseInt(b);
                    h2 = Integer.parseInt(c);
                    m2 = Integer.parseInt(d);

                    int sumaH,sumaM;
                    sumaH = h1+h2;
                    sumaM = m1+m2;

                    if(sumaM >= 60){
                        sumaM = (sumaM - 60);
                        sumaH = sumaH + 1;
                    }

                    if((sumaH) < 10 && (sumaM) < 10){
                        f = "0"+(sumaH)+":"+"0"+(sumaM);
                    }else if((sumaH) < 10){
                        f = "0"+(sumaH)+":"+(sumaM);
                    } else if((sumaM) < 10){
                        f = (sumaH)+":"+"0"+(sumaM);
                    }
                    String s;
                    if(torno[7] == null || torno[7].equals("")){
                    s = txtNumero.getText();
                    }else{
                    s = torno[7] + "," + txtNumero.getText();
                    }
                    pst.setString(1, txtProyecto.getText());
                    pst.setString(2, torno[5]);
                    pst.setString(3, txtFecha.getText());
                    pst.setString(4, fec);
                    pst.setString(5, "SI");
                    pst.setString(6, torno[1]);
                    pst.setString(7, f);
                    pst.setString(8, torno[6]);
                    pst.setString(9, s);
                    pst.setString(10, txtProyecto.getText());
                    pst.setString(11, revision);

                }else{
                    String s;
                    if(torno[7] == null || torno[7].equals("")){
                    s = txtNumero.getText();
                    }else{
                    s = torno[7] + "," + txtNumero.getText();
                    }
                    
                    pst.setString(1, txtProyecto.getText());
                    pst.setString(2, torno[5]);
                    pst.setString(3, txtFecha.getText());
                    pst.setString(4, fec);
                    pst.setString(5, "SI");
                    pst.setString(6, torno[1]);
                    pst.setString(7, cronometro(txtFecha.getText(),fec));
                    pst.setString(8, torno[6]);
                    pst.setString(9, s);
                    pst.setString(10, txtProyecto.getText());
                    pst.setString(11, revision);
                }

                switch (cmbEnviar.getSelectedIndex()) {
                    case 0:
                        JOptionPane.showMessageDialog(this, "DEBE ESCOGER UNA OPCION","",JOptionPane.ERROR_MESSAGE);
                        break;
                    case 1:
                        if(acabados[1] == (null)){
                            pst5.setString(1, txtProyecto.getText());
                            pst5.setString(2, torno[5]);
                            pst5.setString(3, "");
                            pst5.setString(4, "");
                            pst5.setString(5, "NO");
                            pst5.setString(6, torno[1]);
                            pst5.setString(7, "00:00");
                            pst5.setString(8, torno[6]);
                            pst5.setString(9, revision);
                            int n = pst5.executeUpdate();
                            int n1 = pst.executeUpdate();
                            if (n1 > 0 && n > 0 )
                            {
                                tabla();
                                borrar();
                            }
                        }else{
                            act3.setString(1, txtProyecto.getText());
                            act3.setString(2, torno[5]);
                            act3.setString(3, "");
                            act3.setString(4, "");
                            act3.setString(5, "NO");
                            act3.setString(6, torno[1]);
                            act3.setString(7, acabados[3]);
                            act3.setString(8, torno[6]);
                            act3.setString(9, txtProyecto.getText());
                            act3.setString(10, revision);
                            int n = act3.executeUpdate();
                            int n1 = pst.executeUpdate();
                            if (n > 0 && n1 >0){
                                tabla();
                                borrar();
                            }
                            
                        }   break;
                    case 2:
                        if(cnc[1] == (null)){
                            pst1.setString(1, txtProyecto.getText());
                            pst1.setString(2, torno[5]);
                            pst1.setString(3, "");
                            pst1.setString(4, "");
                            pst1.setString(5, "NO");
                            pst1.setString(6, torno[1]);
                            pst1.setString(7, "00:00");
                            pst1.setString(8, torno[6]);
                            pst1.setString(9, revision);
                            int n = pst1.executeUpdate();
                            int n1 = pst.executeUpdate();
                            if (n > 0 && n1 > 0)
                            {
                                tabla();
                                borrar();
                            }
                        }else{
                            act.setString(1, txtProyecto.getText());
                            act.setString(2, torno[5]);
                            act.setString(3, "");
                            act.setString(4, "");
                            act.setString(5, "NO");
                            act.setString(6, torno[1]);
                            act.setString(7, cnc[3]);
                            act.setString(8, torno[6]);
                            act.setString(9, txtProyecto.getText());
                            act.setString(10, revision);
                            int n = act.executeUpdate();
                            int n1 = pst.executeUpdate();
                            if (n1 > 0 && n > 0){
                                tabla();
                                borrar();
                            }                    }
                        break;
                    case 3:
                        if(fresa[1] == (null)){
                            pst4.setString(1, txtProyecto.getText());
                            pst4.setString(2, torno[5]);
                            pst4.setString(3, "");
                            pst4.setString(4, "");
                            pst4.setString(5, "NO");
                            pst4.setString(6, torno[1]);
                            pst4.setString(7, "00:00");
                            pst4.setString(8, torno[6]);
                            pst4.setString(9, revision);
                            int n = pst4.executeUpdate();
                            int n1 = pst.executeUpdate();
                            if (n1 > 0 && n > 0)
                            {
                                tabla();
                                borrar();
                            }
                        }else{
                            act2.setString(1, txtProyecto.getText());
                            act2.setString(2, torno[5]);
                            act2.setString(3, "");
                            act2.setString(4, "");
                            act2.setString(5, "NO");
                            act2.setString(6, torno[1]);
                            act2.setString(7, fresa[3]);
                            act2.setString(8, torno[6]);
                            act2.setString(9, txtProyecto.getText());
                            act2.setString(10, revision);
                            int n = act2.executeUpdate();
                            int n1 = pst.executeUpdate();
                            if (n1 > 0 && n > 0){
                                tabla();
                                borrar();
                            }
                        }
                        break;
                    case 4:
                        if(cortes[1] == (null)){
                            revisarPlanos rev = new revisarPlanos();
                            rev.enviarCortes("torno", txtProyecto.getText(), txtNumero.getText(), torno[5],revision);
                            pst2.setString(1, txtProyecto.getText());
                            pst2.setString(2, torno[5]);
                            pst2.setString(3, "");
                            pst2.setString(4, "");
                            pst2.setString(5, "NO");
                            pst2.setString(6, torno[1]);
                            pst2.setString(7, "00:00");
                            pst2.setString(8, torno[6]);
                            pst2.setString(9, revision);
                            int n = pst2.executeUpdate();
                            int n1 = pst.executeUpdate();
                            if (n1 > 0 && n > 0)
                            {
                                tabla();
                                borrar();
                            }
                        }else{
                            revisarPlanos rev = new revisarPlanos();
                            rev.enviarCortes("torno", txtProyecto.getText(), txtNumero.getText(), torno[5],revision);
                            act1.setString(1, txtProyecto.getText());
                            act1.setString(2, torno[5]);
                            act1.setString(3, "");
                            act1.setString(4, "");
                            act1.setString(5, "NO");
                            act1.setString(6, torno[1]);
                            act1.setString(7, cortes[3]);
                            act1.setString(8, torno[6]);
                            act1.setString(9, txtProyecto.getText());
                            act1.setString(10, revision);
                            int n = act1.executeUpdate();
                            int n1 = pst.executeUpdate();
                            if (n1 > 0 && n > 0){
                                tabla();
                                borrar();
                            }
                        }
                        break;
                    default:
                        break;
                }

                }catch(SQLException e){

                    JOptionPane.showMessageDialog(null, "NO SE PUEDE ENVIAR INFORMACION"+e,"ERROR",JOptionPane.ERROR_MESSAGE);
                }
            }
    }
    
    public Torno() {
        initComponents();
        fechaFinal();
        limpiarTabla();
        verDatos(); 
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

        PanelNorth = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        jPanel19 = new javax.swing.JPanel();
        btnSalir = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        jPanel16 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        Tabla1 = new ColorRojo();
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
        txtProyecto = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtFecha = new javax.swing.JLabel();
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
        jPanel18 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        jPanel17 = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();

        setBorder(null);

        PanelNorth.setBackground(new java.awt.Color(255, 255, 255));
        PanelNorth.setLayout(new java.awt.BorderLayout());

        jLabel17.setFont(new java.awt.Font("Roboto", 1, 40)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(0, 165, 252));
        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel17.setText("Torno");
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

        getContentPane().add(PanelNorth, java.awt.BorderLayout.NORTH);

        jPanel16.setBackground(new java.awt.Color(255, 255, 255));
        jPanel16.setLayout(new java.awt.BorderLayout(60, 40));

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
        Tabla1.setFocusable(false);
        Tabla1.setOpaque(false);
        Tabla1.setRowHeight(25);
        Tabla1.setSelectionBackground(new java.awt.Color(110, 201, 255));
        Tabla1.getTableHeader().setReorderingAllowed(false);
        Tabla1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Tabla1MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                Tabla1MouseEntered(evt);
            }
        });
        jScrollPane1.setViewportView(Tabla1);
        if (Tabla1.getColumnModel().getColumnCount() > 0) {
            Tabla1.getColumnModel().getColumn(0).setResizable(false);
            Tabla1.getColumnModel().getColumn(0).setPreferredWidth(20);
            Tabla1.getColumnModel().getColumn(1).setResizable(false);
            Tabla1.getColumnModel().getColumn(2).setResizable(false);
        }

        jPanel16.add(jScrollPane1, java.awt.BorderLayout.LINE_START);

        panelCentral.setLayout(new java.awt.BorderLayout(40, 40));

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
        jLabel2.setText("NUMERO DE PLANO:   ");
        jPanel21.add(jLabel2);

        txtProyecto.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        txtProyecto.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txtProyecto.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        jPanel21.add(txtProyecto);

        jLabel4.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setText("FECHA INICIO:   ");
        jPanel21.add(jLabel4);

        txtFecha.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        txtFecha.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txtFecha.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        jPanel21.add(txtFecha);

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
        jPanel6.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 80, 5));

        jLabel13.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IconoC/letra-h.png"))); // NOI18N
        jPanel6.add(jLabel13);

        jLabel14.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IconoC/letra-m.png"))); // NOI18N
        jPanel6.add(jLabel14);

        jLabel15.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
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

        cmbEnviar.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "SELECCIONAR", "ACABADOS", "CNC", "FRESADORA", "CORTES" }));
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
          Runtime.getRuntime().exec("cmd /c start \\\\100.100.200.10\\bd\\OC\\PDF_Planos\\"+comillas+Tabla1.getValueAt(fila, 2)+comillas+"\\"+comillas+Tabla1.getValueAt(fila, 1)+".pdf"+comillas);
          
          }catch(IOException  e){
              JOptionPane.showMessageDialog(this, "PDF NO ENCONTRADO");
          }
    }//GEN-LAST:event_Tabla1MouseClicked

    private void Tabla1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Tabla1MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_Tabla1MouseEntered

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
        verPlano();
    }//GEN-LAST:event_txtCodigoActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        limpiarTabla();
        verDatos();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void btnPausaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPausaActionPerformed
        pausar();
    }//GEN-LAST:event_btnPausaActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        enviar(lblRevision.getText());
    }//GEN-LAST:event_btnGuardarActionPerformed


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
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
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
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JTextField txtEmpleado;
    private javax.swing.JLabel txtFecha;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtNumero;
    private javax.swing.JLabel txtProyecto;
    private javax.swing.JTextField txtPuesto;
    // End of variables declaration//GEN-END:variables
}
