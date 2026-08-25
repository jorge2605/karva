package VentanaEmergente.Reportes;

import Conexiones.Conexion;
import com.mxrck.autocompleter.TextAutoCompleter;
import java.awt.Color;
import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import static org.apache.poi.ss.usermodel.FillPatternType.SOLID_FOREGROUND;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellUtil;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import pruebas.CambiarEstado;

public final class ReporteMensual extends javax.swing.JDialog {

    public TextAutoCompleter ac1;
    
    public void autoCompletarProyecto() {
        ac1 = new TextAutoCompleter(txtProyecto);
        try {
            Connection con = null;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            String sql = "select Proyecto from proyectos";
            String datos[] = new String[10];
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                datos[0] = rs.getString("Proyecto");
                ac1.addItem(datos[0]);
            }
            ac1.addItem("MATERIAL DE LIMPIEZA");
            ac1.addItem("MATERIAL DE OFICINA");
            ac1.addItem("MATERIAL DE MANTENIMIENTO");
            ac1.addItem("HERRAMIENTAS");
            ac1.addItem("SEGURIDAD");
            ac1.addItem("VENTAS");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "ERROR AL AUTOCOMPLETAR" + e, "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void limpiarTabla(){
        Tabla1.setModel(new javax.swing.table.DefaultTableModel(
        new Object [][] {

        },
        new String [] {
            "Requisicion", "PO", "N.P", "Descripcion", "Cantidad", "Moneda", "P.U", "Total", "Precio recibido", "Precio faltante", "Fecha requisicion", "Fecha recibo", "Fecha O.C.", "Fecha T.E.", "Proveedor", "Proyecto"
        }
    ) {
        boolean[] canEdit = new boolean [] {
            false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false
        };

        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return canEdit [columnIndex];
        }
    });
        Tabla1.getTableHeader().setFont(new java.awt.Font("Roboto", java.awt.Font.PLAIN, 14));
        Tabla1.getTableHeader().setOpaque(false);
        Tabla1.getTableHeader().setBackground(new Color(0, 78, 171));
        Tabla1.getTableHeader().setForeground(Color.white);
        Tabla1.setRowHeight(25);
        Tabla1.setShowGrid(false);
        jScrollPane1.getViewport().setBackground(Color.white);
        Tabla1.setShowGrid(false);
    }
    
    public void completar(){
        try{
            Connection con;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            String sql = "select Id, Fecha, FechaNew from requisicion where FechaNew is null";
            ResultSet rs = st.executeQuery(sql);
            String sql2 = "update requisicion set FechaNew = ? where Id = ?";
            PreparedStatement pst = con.prepareStatement(sql2);
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
            int n = 0;
            while(rs.next()){
                String id, fecha,fechanew;
                id = rs.getString("Id");
                fecha = rs.getString("Fecha");
                Date d = sdf.parse(fecha);
                fechanew = sdf2.format(d);
                pst.setString(1, fechanew);
                pst.setString(2, id);
                
                n = pst.executeUpdate();
            }
            
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "ERROR"+e,"ERROR",JOptionPane.ERROR_MESSAGE);
        } catch (ParseException ex) {
            Logger.getLogger(ReporteMensual.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void verPorFecha(){
        try{
            limpiarTabla();
            completar();
            DefaultTableModel miModelo = (DefaultTableModel) Tabla1.getModel();
            Connection con ;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            Statement st5 = con.createStatement();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String add = "";
            if(!txtProyecto.getText().equals("")){
                add = " Proyecto like '"+txtProyecto.getText()+"' and ";
                Statement st7 = con.createStatement();
                String sql7 = "select Proyecto, Costo, Moneda from Proyectos where Proyecto like '"+txtProyecto.getText()+"'";
                ResultSet rs7 = st7.executeQuery(sql7);
                while(rs7.next()){
                    lblMoneda.setText(rs7.getString("Moneda"));
                    lblCostoProyecto.setText(rs7.getString("Costo"));
                }
            }
            
            String sql5 = "select * from requisicion where FechaNew between '"+sdf.format(txt1.getDatoFecha())
                    +"' and '"+sdf.format(txt2.getDatoFecha())+"'";
            ResultSet rs5 = st5.executeQuery(sql5);
            String inicio = "", fin = "";
            double totalMxn = 0;
            double totalUsd = 0;
            double recibidoMxn = 0;
            double faltanteMxn = 0;
            double recibidoUsd = 0;
            double faltanteUsd = 0;
            boolean tr;
            if(txtProyecto.getText().equals("")){
                tr = true;
            }else{
                tr = false;
            }
            int co = 0;
            while(rs5.next()){
                if(co == 0){
                    inicio = rs5.getString("Id");
                }
                fin = rs5.getString("Id");
                co++;
            }
            String sql = "SELECT * FROM ordencompra WHERE RequisicionNo between '"+inicio+"' and '"+fin+"' order by Id desc;";
            ResultSet rs = st.executeQuery(sql);
            String datos[] = new String[20];
            double total = 0;
            int ini = Integer.parseInt(inicio);
            while(rs.next()){
                //RESULTADO DE ORDENES DE COMPRAS
                double precio = 0;
                double precioTotal = 0, precioRecibido = 0, precioFaltante = 0;
                datos[0] = rs.getString("OrdenNo");
                datos[8] = rs.getString("RequisicionNo");
                
                if(Integer.parseInt(datos[8]) >= ini){
                    
                String sql2;
                if(tr){
                    sql2 = "select * from requisiciones where OC like '"+datos[0]+"'";
                }else{
                    sql2 = "select * from requisiciones where OC like '"+datos[0]+"' and Proyecto like '"+txtProyecto.getText()+"'";
                }
                Statement st2 = con.createStatement();
                ResultSet rs2 = st2.executeQuery(sql2);
                String d[] = new String[20];
                double t1 = 0,t2 = 0;
                double total2 = 0;
                while(rs2.next()){
                    //RESULTADO DE REQUISICIONES DE COMPRA
                    try{
                    datos[9] = rs2.getString("Proveedor");
                    datos[10] = rs2.getString("Proyecto");
                    datos[11] = rs2.getString("Codigo");
                    datos[12] = rs2.getString("Descripcion");
                    datos[13] = rs2.getString("Cantidad");
                    datos[14] = rs2.getString("Precio");
                    datos[15] = rs2.getString("Precio");
                    datos[16] = rs2.getString("FechaRecibo");
                    datos[17] = rs2.getString("NumRequisicion");
                    datos[18] = rs2.getString("TE");
                    String sql3 = "SELECT * FROM registroprov_compras where Nombre like '"+datos[9]+"'";
                    Statement st3 = con.createStatement();
                    ResultSet rs3 = st3.executeQuery(sql3);
                    String moneda = "";
                    
                    while(rs3.next()){
                        //RESULTADO DE LOS PROVEEDORES
                        moneda = rs3.getString("Moneda");
                    }
                    
                    String sql6 = "select Id,Fecha from requisicion where Id like '"+datos[17]+"'";
                    Statement st6 = con.createStatement();
                    ResultSet rs6 = st6.executeQuery(sql6);
                    String numrequi = "";
                    while(rs6.next()){
                        numrequi = rs6.getString("Fecha");
                    }
                    
                    String sql8 = "select Fecha,OrdenNo from ordencompra where OrdenNo like '"+datos[0]+"'";
                    Statement st8 = con.createStatement();
                    ResultSet rs8 = st8.executeQuery(sql8);
                    String fechaOC = "";
                    while(rs8.next()){
                        fechaOC = rs8.getString("Fecha");

                    }
                    if(moneda.equals("MXN")){
                            totalMxn += (Double.parseDouble(rs2.getString("Precio")) * Double.parseDouble(rs2.getString("Cantidad")));
                            precio = (Double.parseDouble(rs2.getString("Precio")) * Double.parseDouble(rs2.getString("Cantidad")));
                            d[5] = "MXN";
                            datos[2] = rs2.getString("Llego");
                            if(datos[2] != null){
                                recibidoMxn += precio;
                                t1 += precio;
                                
                            }else{
                                faltanteMxn += precio;
                                t2 += precio;
                            }
                        }else{
                            totalUsd += ((Double.parseDouble(rs2.getString("Precio")) * Double.parseDouble(rs2.getString("Cantidad"))));
                            precio = (Double.parseDouble(rs2.getString("Precio")) * Double.parseDouble(rs2.getString("Cantidad")));
                            d[5] = "USD";
                            datos[2] = rs2.getString("Llego");
                            if(datos[2] != null){
                                recibidoUsd += precio;
                                t1 += precio;
                            }else{
                                faltanteUsd += precio;
                                t2 += precio;
                            }
                    }
                    //     0           1     2       3               4       5         6                     7               8
                    //"REQUISICION", "PO", "N.P", "DESCRIPCION",Cantidad "MONEDA", "PRECIO TOTAL", "PRECIO RECIBIDO", "PRECIO FALTANTE", "fecha requi", "fecha recibo"
                    total2+=precio;
                    d[0] = datos[8];
                    d[1] = datos[0];
                    d[2] = datos[11];
                    d[3] = datos[12];
                    d[4] = datos[13];
                    d[6] = datos[14];
                    d[7] = String.valueOf(precio);
                    d[8] = String.valueOf(t1);
                    d[9] = String.valueOf(t2);
                    d[10] = numrequi;
                    d[11] = datos[16];
                    d[12] = fechaOC;
                    d[13] = datos[18];
                    d[14] = datos[9];
                    d[15] = datos[10];
                
                total += precioTotal;
                if(total2 != 0){
                    miModelo.addRow(d); 
                }
                    }catch(Exception e){
                        System.out.println("error "+e);
                        e.printStackTrace();
                    }
                }
            }
            }
            DecimalFormat formato = new DecimalFormat("#,##0.00");
            lblTotalMxn.setText(formato.format(totalMxn));
            lblRecibidoMxn.setText(formato.format(recibidoMxn));
            lblFaltanteMxn.setText(formato.format(faltanteMxn));
            lblTotalUsd.setText(formato.format(totalUsd));
            lblRecibidoUsd.setText(formato.format(recibidoUsd));
            lblFaltanteUsd.setText(formato.format(faltanteUsd));
            btnExcel.setEnabled(true);
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "ERROR: "+e,"ERROR",JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void verPorProyecto(){
        try{
            limpiarTabla();
            completar();
            DefaultTableModel miModelo = (DefaultTableModel) Tabla1.getModel();
            Connection con ;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            Statement st5 = con.createStatement();
            Statement st7 = con.createStatement();
            
            String sql7 = "select Proyecto, Costo, Moneda from Proyectos where Proyecto like '"+txtProyecto.getText()+"'";
            ResultSet rs7 = st7.executeQuery(sql7);
            while(rs7.next()){
                lblMoneda.setText(rs7.getString("Moneda"));
                lblCostoProyecto.setText(rs7.getString("Costo"));
            }
            
            double totalMxn = 0;
            double totalUsd = 0;
            double recibidoMxn = 0;
            double faltanteMxn = 0;
            double recibidoUsd = 0;
            double faltanteUsd = 0;
            double total = 0;
            
            String datos[] = new String[18];
            
            String sql = "select * from requisiciones where Proyecto like '"+txtProyecto.getText()+"'";
            ResultSet rs = st.executeQuery(sql);
            
            while(rs.next()){
                //RESULTADO DE REQUISICIONES DE COMPRA
                try{
                datos[0] = rs.getString("OC");
                datos[8] = rs.getString("NumRequisicion");
                datos[9] = rs.getString("Proveedor");
                datos[10] = rs.getString("Proyecto");
                datos[11] = rs.getString("Codigo");
                datos[12] = rs.getString("Descripcion");
                datos[13] = rs.getString("Cantidad");
                datos[14] = rs.getString("Precio");
                datos[15] = rs.getString("Precio");
                datos[16] = rs.getString("FechaRecibo");
                datos[17] = rs.getString("NumRequisicion");
                datos[18] = rs.getString("TE");
                if(datos[0] != null){
                    if(!datos[0].equals("")){
                        String val;
                        try{
                            val = datos[0].substring(0,2);
                        }catch(Exception e){
                            val = "";
                        }
                        if(val.equals("OC")){
                            String sql3 = "SELECT * FROM registroprov_compras where Nombre like '"+datos[9]+"'";
                            Statement st3 = con.createStatement();
                            ResultSet rs3 = st3.executeQuery(sql3);
                            String moneda = "";

                            while(rs3.next()){
                                //RESULTADO DE LOS PROVEEDORES
                                moneda = rs3.getString("Moneda");
                            }

                            String sql6 = "select Id,Fecha,Progreso from requisicion where Id like '"+datos[17]+"'";
                            Statement st6 = con.createStatement();
                            ResultSet rs6 = st6.executeQuery(sql6);
                            String numrequi = "";
                            String progreso = "";
                            while(rs6.next()){
                                numrequi = rs6.getString("Fecha");
                                progreso = rs6.getString("Progreso");

                            }

                            String sql8 = "select Fecha,OrdenNo from ordencompra where OrdenNo like '"+datos[0]+"'";
                            Statement st8 = con.createStatement();
                            ResultSet rs8 = st8.executeQuery(sql8);
                            String fechaOC = "";
                            while(rs8.next()){
                                fechaOC = rs8.getString("Fecha");

                            }
                            if(!progreso.equals("CANCELADO")){
                                double precio = 0;
                                String d[] = new String[15];
                                double t1 = 0;
                                double t2 = 0;
                                double total2 = 0;
                                if(moneda.equals("MXN")){
                                        totalMxn += (Double.parseDouble(rs.getString("Precio")) * Double.parseDouble(rs.getString("Cantidad")));
                                        precio = (Double.parseDouble(rs.getString("Precio")) * Double.parseDouble(rs.getString("Cantidad")));
                                        d[5] = "MXN";
                                        datos[2] = rs.getString("Llego");
                                        if(datos[2] != null){
                                            recibidoMxn += precio;
                                            t1 += precio;

                                        }else{
                                            faltanteMxn += precio;
                                            t2 += precio;
                                        }
                                    }else{
                                        totalUsd += ((Double.parseDouble(rs.getString("Precio")) * Double.parseDouble(rs.getString("Cantidad"))));
                                        precio = (Double.parseDouble(rs.getString("Precio")) * Double.parseDouble(rs.getString("Cantidad")));
                                        d[5] = "USD";
                                        datos[2] = rs.getString("Llego");
                                        if(datos[2] != null){
                                            recibidoUsd += precio;
                                            t1 += precio;
                                        }else{
                                            faltanteUsd += precio;
                                            t2 += precio;
                                        }
                                }
                                //     0           1     2       3               4       5         6                     7               8
                                //"REQUISICION", "PO", "N.P", "DESCRIPCION",Cantidad "MONEDA", "PRECIO TOTAL", "PRECIO RECIBIDO", "PRECIO FALTANTE", "fecha requi", "fecha recibo"
                                total2+=precio;
                                d[0] = datos[8];
                                d[1] = datos[0];
                                d[2] = datos[11];
                                d[3] = datos[12];
                                d[4] = datos[13];
                                d[6] = datos[14];
                                d[7] = String.valueOf(precio);
                                d[8] = String.valueOf(t1);
                                d[9] = String.valueOf(t2);
                                d[10] = numrequi;
                                d[11] = datos[16];
                                d[12] = fechaOC;
                                d[13] = datos[18];
                                d[14] = datos[9];
                                d[15] = datos[10];

                //            total += precioTotal;
                                if(total2 != 0){
                                    miModelo.addRow(d); 
                                }
                            }
                        }
                    }
                }
                }catch(Exception e){
                    System.out.println("error "+e);
                }
            }
            
            DecimalFormat formato = new DecimalFormat("#,##0.00");
            lblTotalMxn.setText(formato.format(totalMxn));
            lblRecibidoMxn.setText(formato.format(recibidoMxn));
            lblFaltanteMxn.setText(formato.format(faltanteMxn));
            lblTotalUsd.setText(formato.format(totalUsd));
            lblRecibidoUsd.setText(formato.format(recibidoUsd));
            lblFaltanteUsd.setText(formato.format(faltanteUsd));
            btnExcel.setEnabled(true);
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "ERROR: "+e,"ERROR",JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public ReporteMensual(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        autoCompletarProyecto();
        limpiarTabla();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jPanel25 = new javax.swing.JPanel();
        txtProyecto = new RSMaterialComponent.RSTextFieldMaterial();
        rbProyecto = new javax.swing.JRadioButton();
        jPanel27 = new javax.swing.JPanel();
        jPanel26 = new javax.swing.JPanel();
        txt1 = new rojeru_san.rsdate.RSDateChooser();
        jLabel2 = new javax.swing.JLabel();
        txt2 = new rojeru_san.rsdate.RSDateChooser();
        rbFecha = new javax.swing.JRadioButton();
        jLabel3 = new javax.swing.JLabel();
        rSButton1 = new rojeru_san.RSButton();
        jPanel6 = new javax.swing.JPanel();
        jPanel16 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        lblTotalMxn = new javax.swing.JFormattedTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        lblRecibidoMxn = new javax.swing.JFormattedTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        lblFaltanteMxn = new javax.swing.JFormattedTextField();
        jLabel11 = new javax.swing.JLabel();
        lblMoneda = new javax.swing.JLabel();
        jPanel17 = new javax.swing.JPanel();
        jPanel18 = new javax.swing.JPanel();
        lblTotalUsd = new javax.swing.JFormattedTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jPanel19 = new javax.swing.JPanel();
        jPanel20 = new javax.swing.JPanel();
        lblRecibidoUsd = new javax.swing.JFormattedTextField();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jPanel21 = new javax.swing.JPanel();
        jPanel22 = new javax.swing.JPanel();
        lblFaltanteUsd = new javax.swing.JFormattedTextField();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jPanel23 = new javax.swing.JPanel();
        jPanel24 = new javax.swing.JPanel();
        lblCostoProyecto = new javax.swing.JFormattedTextField();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jPanel14 = new javax.swing.JPanel();
        jPanel15 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        btnExcel = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        Tabla1 = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new java.awt.BorderLayout());

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 102, 204));
        jLabel1.setText("           REPORTE MENSUAL DE GASTOS           ");
        jLabel1.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 0, 0, new java.awt.Color(0, 102, 204)));
        jPanel3.add(jLabel1);

        jPanel2.add(jPanel3);

        jPanel1.add(jPanel2, java.awt.BorderLayout.PAGE_START);

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setLayout(new java.awt.BorderLayout());

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));

        jPanel25.setBackground(new java.awt.Color(255, 255, 255));
        jPanel25.setLayout(new java.awt.BorderLayout());

        txtProyecto.setForeground(new java.awt.Color(51, 51, 51));
        txtProyecto.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtProyecto.setPlaceholder("Proyecto");
        txtProyecto.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtProyectoFocusLost(evt);
            }
        });
        jPanel25.add(txtProyecto, java.awt.BorderLayout.CENTER);

        rbProyecto.setBackground(new java.awt.Color(255, 255, 255));
        buttonGroup1.add(rbProyecto);
        rbProyecto.setSelected(true);
        rbProyecto.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        rbProyecto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbProyectoActionPerformed(evt);
            }
        });
        jPanel25.add(rbProyecto, java.awt.BorderLayout.PAGE_START);

        jPanel5.add(jPanel25);

        jPanel27.setBackground(new java.awt.Color(255, 255, 255));
        jPanel27.setLayout(new java.awt.BorderLayout());

        jPanel26.setBackground(new java.awt.Color(255, 255, 255));

        txt1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txt1FocusLost(evt);
            }
        });
        jPanel26.add(txt1);

        jLabel2.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 112, 192));
        jLabel2.setText("   &   ");
        jPanel26.add(jLabel2);

        txt2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txt2FocusLost(evt);
            }
        });
        jPanel26.add(txt2);

        jPanel27.add(jPanel26, java.awt.BorderLayout.CENTER);

        rbFecha.setBackground(new java.awt.Color(255, 255, 255));
        buttonGroup1.add(rbFecha);
        rbFecha.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        rbFecha.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbFechaActionPerformed(evt);
            }
        });
        jPanel27.add(rbFecha, java.awt.BorderLayout.PAGE_START);

        jPanel5.add(jPanel27);

        jLabel3.setText("               ");
        jPanel5.add(jLabel3);

        rSButton1.setText("BUSCAR");
        rSButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rSButton1ActionPerformed(evt);
            }
        });
        jPanel5.add(rSButton1);

        jPanel4.add(jPanel5, java.awt.BorderLayout.NORTH);

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setLayout(new javax.swing.BoxLayout(jPanel6, javax.swing.BoxLayout.Y_AXIS));

        jPanel16.setBackground(new java.awt.Color(255, 255, 255));

        jLabel4.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jLabel4.setText("Totales");
        jPanel16.add(jLabel4);

        jPanel6.add(jPanel16);

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setLayout(new javax.swing.BoxLayout(jPanel7, javax.swing.BoxLayout.Y_AXIS));

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));

        jPanel10.setBackground(new java.awt.Color(255, 255, 255));
        jPanel10.setLayout(new java.awt.BorderLayout(15, 15));

        lblTotalMxn.setEditable(false);
        lblTotalMxn.setBackground(new java.awt.Color(255, 255, 255));
        lblTotalMxn.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(240, 240, 240)));
        lblTotalMxn.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.##"))));
        lblTotalMxn.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        lblTotalMxn.setPreferredSize(new java.awt.Dimension(110, 17));
        jPanel10.add(lblTotalMxn, java.awt.BorderLayout.CENTER);

        jLabel5.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Total MXN");
        jPanel10.add(jLabel5, java.awt.BorderLayout.PAGE_START);

        jLabel7.setBackground(new java.awt.Color(255, 255, 255));
        jLabel7.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel7.setText("$");
        jPanel10.add(jLabel7, java.awt.BorderLayout.WEST);

        jPanel8.add(jPanel10);

        jPanel7.add(jPanel8);

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));

        jPanel11.setBackground(new java.awt.Color(255, 255, 255));
        jPanel11.setLayout(new java.awt.BorderLayout(15, 15));

        lblRecibidoMxn.setEditable(false);
        lblRecibidoMxn.setBackground(new java.awt.Color(255, 255, 255));
        lblRecibidoMxn.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(240, 240, 240)));
        lblRecibidoMxn.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.##"))));
        lblRecibidoMxn.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        lblRecibidoMxn.setPreferredSize(new java.awt.Dimension(110, 17));
        jPanel11.add(lblRecibidoMxn, java.awt.BorderLayout.CENTER);

        jLabel9.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("Total recibido Mxn");
        jPanel11.add(jLabel9, java.awt.BorderLayout.PAGE_START);

        jLabel10.setBackground(new java.awt.Color(255, 255, 255));
        jLabel10.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel10.setText("$");
        jPanel11.add(jLabel10, java.awt.BorderLayout.WEST);

        jPanel9.add(jPanel11);

        jPanel7.add(jPanel9);

        jPanel12.setBackground(new java.awt.Color(255, 255, 255));

        jPanel13.setBackground(new java.awt.Color(255, 255, 255));
        jPanel13.setLayout(new java.awt.BorderLayout(15, 15));

        lblFaltanteMxn.setEditable(false);
        lblFaltanteMxn.setBackground(new java.awt.Color(255, 255, 255));
        lblFaltanteMxn.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(240, 240, 240)));
        lblFaltanteMxn.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.##"))));
        lblFaltanteMxn.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        lblFaltanteMxn.setPreferredSize(new java.awt.Dimension(110, 17));
        jPanel13.add(lblFaltanteMxn, java.awt.BorderLayout.CENTER);

        jLabel11.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("Total faltante Mxn");
        jPanel13.add(jLabel11, java.awt.BorderLayout.PAGE_START);

        lblMoneda.setBackground(new java.awt.Color(255, 255, 255));
        lblMoneda.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        lblMoneda.setText("$");
        jPanel13.add(lblMoneda, java.awt.BorderLayout.WEST);

        jPanel12.add(jPanel13);

        jPanel7.add(jPanel12);

        jPanel17.setBackground(new java.awt.Color(255, 255, 255));

        jPanel18.setBackground(new java.awt.Color(255, 255, 255));
        jPanel18.setLayout(new java.awt.BorderLayout(15, 15));

        lblTotalUsd.setEditable(false);
        lblTotalUsd.setBackground(new java.awt.Color(255, 255, 255));
        lblTotalUsd.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(240, 240, 240)));
        lblTotalUsd.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.##"))));
        lblTotalUsd.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        lblTotalUsd.setPreferredSize(new java.awt.Dimension(110, 17));
        jPanel18.add(lblTotalUsd, java.awt.BorderLayout.CENTER);

        jLabel6.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Total USD");
        jPanel18.add(jLabel6, java.awt.BorderLayout.PAGE_START);

        jLabel8.setBackground(new java.awt.Color(255, 255, 255));
        jLabel8.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel8.setText("$");
        jPanel18.add(jLabel8, java.awt.BorderLayout.WEST);

        jPanel17.add(jPanel18);

        jPanel7.add(jPanel17);

        jPanel6.add(jPanel7);

        jPanel19.setBackground(new java.awt.Color(255, 255, 255));

        jPanel20.setBackground(new java.awt.Color(255, 255, 255));
        jPanel20.setLayout(new java.awt.BorderLayout(15, 15));

        lblRecibidoUsd.setEditable(false);
        lblRecibidoUsd.setBackground(new java.awt.Color(255, 255, 255));
        lblRecibidoUsd.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(240, 240, 240)));
        lblRecibidoUsd.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.##"))));
        lblRecibidoUsd.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        lblRecibidoUsd.setPreferredSize(new java.awt.Dimension(110, 17));
        jPanel20.add(lblRecibidoUsd, java.awt.BorderLayout.CENTER);

        jLabel13.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setText("Total recibido Usd");
        jPanel20.add(jLabel13, java.awt.BorderLayout.PAGE_START);

        jLabel14.setBackground(new java.awt.Color(255, 255, 255));
        jLabel14.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel14.setText("$");
        jPanel20.add(jLabel14, java.awt.BorderLayout.WEST);

        jPanel19.add(jPanel20);

        jPanel6.add(jPanel19);

        jPanel21.setBackground(new java.awt.Color(255, 255, 255));

        jPanel22.setBackground(new java.awt.Color(255, 255, 255));
        jPanel22.setLayout(new java.awt.BorderLayout(15, 15));

        lblFaltanteUsd.setEditable(false);
        lblFaltanteUsd.setBackground(new java.awt.Color(255, 255, 255));
        lblFaltanteUsd.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(240, 240, 240)));
        lblFaltanteUsd.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.##"))));
        lblFaltanteUsd.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        lblFaltanteUsd.setPreferredSize(new java.awt.Dimension(110, 17));
        jPanel22.add(lblFaltanteUsd, java.awt.BorderLayout.CENTER);

        jLabel15.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel15.setText("Total faltante Usd");
        jPanel22.add(jLabel15, java.awt.BorderLayout.PAGE_START);

        jLabel16.setBackground(new java.awt.Color(255, 255, 255));
        jLabel16.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel16.setText("$");
        jPanel22.add(jLabel16, java.awt.BorderLayout.WEST);

        jPanel21.add(jPanel22);

        jPanel6.add(jPanel21);

        jPanel23.setBackground(new java.awt.Color(255, 255, 255));

        jPanel24.setBackground(new java.awt.Color(255, 255, 255));
        jPanel24.setLayout(new java.awt.BorderLayout(15, 15));

        lblCostoProyecto.setEditable(false);
        lblCostoProyecto.setBackground(new java.awt.Color(255, 255, 255));
        lblCostoProyecto.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(240, 240, 240)));
        lblCostoProyecto.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.##"))));
        lblCostoProyecto.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        lblCostoProyecto.setPreferredSize(new java.awt.Dimension(110, 17));
        jPanel24.add(lblCostoProyecto, java.awt.BorderLayout.CENTER);

        jLabel17.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel17.setText("Costo proyecto");
        jPanel24.add(jLabel17, java.awt.BorderLayout.PAGE_START);

        jLabel18.setBackground(new java.awt.Color(255, 255, 255));
        jLabel18.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel18.setText("$");
        jPanel24.add(jLabel18, java.awt.BorderLayout.WEST);

        jPanel23.add(jPanel24);

        jPanel6.add(jPanel23);

        jPanel14.setBackground(new java.awt.Color(255, 255, 255));

        jPanel15.setBackground(new java.awt.Color(255, 255, 255));
        jPanel15.setLayout(new java.awt.BorderLayout(15, 15));

        jLabel12.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setText("Exportar a Excel");
        jPanel15.add(jLabel12, java.awt.BorderLayout.PAGE_START);

        btnExcel.setBackground(new java.awt.Color(255, 255, 255));
        btnExcel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/excel.png"))); // NOI18N
        btnExcel.setBorder(null);
        btnExcel.setBorderPainted(false);
        btnExcel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnExcel.setEnabled(false);
        btnExcel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExcelActionPerformed(evt);
            }
        });
        jPanel15.add(btnExcel, java.awt.BorderLayout.CENTER);

        jPanel14.add(jPanel15);

        jPanel6.add(jPanel14);

        jPanel4.add(jPanel6, java.awt.BorderLayout.LINE_END);

        Tabla1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Requisicion", "PO", "N.P", "Descripcion", "Cantidad", "Moneda", "P.U", "Total", "Precio recibido", "Precio faltante", "Fecha requisicion", "Fecha recibo", "Fecha O.C.", "Fecha T.E.", "Proveedor", "Proyecto"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        Tabla1.setShowGrid(false);
        jScrollPane1.setViewportView(Tabla1);

        jPanel4.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jPanel1.add(jPanel4, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void rSButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rSButton1ActionPerformed
        if(rbProyecto.isSelected()){
            verPorProyecto();
        }else{
            verPorFecha();
        }
    }//GEN-LAST:event_rSButton1ActionPerformed

    private void btnExcelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcelActionPerformed
        if(Tabla1.getRowCount() == 0){
            JOptionPane.showMessageDialog(this, "LA TABLA DEBE CONTENER REGISTROS");
        }else{
            JFrame f = (JFrame) JOptionPane.getFrameForComponent(this);
            Thread hilo = new Thread() {
                public void run(){
                    Workbook book;
                    try {
                        btnExcel.setEnabled(false);
                        JFileChooser fc = new JFileChooser();
                        File archivo = null;
                        fc.setFileFilter(new FileNameExtensionFilter("EXCEL (*.xlsx)", "xlsx"));
                        int n = fc.showSaveDialog(f);

                        if(n == JFileChooser.APPROVE_OPTION){
                            archivo = fc.getSelectedFile();
                            String a = ""+archivo;
                            if(a.endsWith("xls")){
                                book = new  HSSFWorkbook();
                            }else if(a.endsWith("xlsx")){
                                book = new XSSFWorkbook();
                            }else{
                                a = archivo + ".xlsx";
                                book = new XSSFWorkbook();
                            }

                            Sheet hoja = book.createSheet("Reporte de ordenes de compra");
                            Row fila = hoja.createRow(2);
                            Cell col = fila.createCell(2);

                            //-------------------------------ESTILOS
                            Font font = book.createFont();
                            CellStyle estilo1 = book.createCellStyle();

                            Font font3 = book.createFont();
                            CellStyle estilo3 = book.createCellStyle();

                            font.setBold(true);

                            font.setColor(IndexedColors.BLACK.getIndex());
                            font.setFontHeightInPoints((short)12);
                            estilo1.setFont(font);

                            estilo1.setAlignment(HorizontalAlignment.LEFT);

                            font3.setBold(false);
                            font3.setColor(IndexedColors.BLACK.getIndex());
                            font3.setFontHeightInPoints((short)15);
                            estilo3.setFont(font3);

                            estilo3.setAlignment(HorizontalAlignment.CENTER);
                            estilo3.setWrapText(true);

                            //--------------------------------------
                            //        hoja.setColumnWidth(2, 5000);
                            //---------------------------------------

                            hoja.setColumnWidth(2, 8200);
                            hoja.setColumnWidth(3, 3300);
                            hoja.setColumnWidth(4, 3600);
                            hoja.setColumnWidth(5, 14000);
                            hoja.setColumnWidth(6, 4100);
                            hoja.setColumnWidth(7, 4100);

                            Font font1 = book.createFont();
                            XSSFCellStyle style = (XSSFCellStyle) book.createCellStyle();

                            XSSFColor color = new XSSFColor(new java.awt.Color(32, 55, 99)); // RGB: (255, 0, 0) - Rojo
                            style.setFillForegroundColor(color);
                            style.setFillPattern(SOLID_FOREGROUND);
                            style.setAlignment(HorizontalAlignment.CENTER);

                            font1.setBold(true);
                            font1.setColor(IndexedColors.WHITE.getIndex());
                            font1.setFontHeightInPoints((short)16);
                            style.setFont(font1);

                            Font font2 = book.createFont();
                            XSSFCellStyle style2 = (XSSFCellStyle) book.createCellStyle();

                            style2.setVerticalAlignment(VerticalAlignment.BOTTOM);
                            style2.setAlignment(HorizontalAlignment.CENTER);
                            style2.setWrapText(true);

                            font2.setBold(true);
                            font2.setColor(IndexedColors.BLACK.getIndex());
                            font2.setFontHeightInPoints((short)12);
                            style2.setFont(font2);
                            //        -----------------------------------------------------
                            Font font4 = book.createFont();
                            XSSFCellStyle style4 = (XSSFCellStyle) book.createCellStyle();

                            XSSFColor color4 = new XSSFColor(new java.awt.Color(216, 225, 242)); // RGB: (255, 0, 0) - Rojo
                            style4.setFillForegroundColor(color4);
                            style4.setFillPattern(SOLID_FOREGROUND);
                            style4.setAlignment(HorizontalAlignment.CENTER);

                            font4.setBold(true);
                            font4.setColor(IndexedColors.BLACK.getIndex());
                            font4.setFontHeightInPoints((short)12);
                            style4.setFont(font4);
                            //        -----------------------------------------------------
                            XSSFCellStyle style5 = (XSSFCellStyle) book.createCellStyle();

                            XSSFColor color5 = new XSSFColor(new java.awt.Color(188, 199, 231)); // RGB: (255, 0, 0) - Rojo
                            style5.setFillForegroundColor(color5);
                            style5.setFillPattern(SOLID_FOREGROUND);
                            style5.setAlignment(HorizontalAlignment.CENTER);

                            style5.setFont(font4);
                            //        -----------------------------------------------------
                            XSSFCellStyle style6 = (XSSFCellStyle) book.createCellStyle();

                            XSSFColor color6 = new XSSFColor(new java.awt.Color(142, 168, 216)); // RGB: (255, 0, 0) - Rojo
                            style6.setFillForegroundColor(color6);
                            style6.setFillPattern(SOLID_FOREGROUND);
                            style6.setAlignment(HorizontalAlignment.CENTER);

                            style6.setFont(font4);

                            hoja.addMergedRegion(new CellRangeAddress (
                                2,
                                2,
                                2,
                                17
                            ));

                            hoja.addMergedRegion(new CellRangeAddress (
                                4,
                                4,
                                3,
                                5
                            ));

                            hoja.addMergedRegion(new CellRangeAddress (
                                5,
                                5,
                                3,
                                5
                            ));
                            hoja.addMergedRegion(new CellRangeAddress (
                                6,
                                6,
                                3,
                                5
                            ));

                            Map<String, Object> properties = new HashMap<String, Object>();
                            properties.put(CellUtil.BORDER_TOP, BorderStyle.MEDIUM);
                            properties.put(CellUtil.BORDER_BOTTOM, BorderStyle.MEDIUM);
                            properties.put(CellUtil.BORDER_LEFT, BorderStyle.MEDIUM);
                            properties.put(CellUtil.BORDER_RIGHT, BorderStyle.MEDIUM);

                            properties.put(CellUtil.TOP_BORDER_COLOR, IndexedColors.BLACK.getIndex());
                            properties.put(CellUtil.BOTTOM_BORDER_COLOR, IndexedColors.BLACK.getIndex());
                            properties.put(CellUtil.LEFT_BORDER_COLOR, IndexedColors.BLACK.getIndex());
                            properties.put(CellUtil.RIGHT_BORDER_COLOR, IndexedColors.BLACK.getIndex());

                            col.setCellStyle(style);
                            col.setCellValue("Reporte de ordenes de compra");

                            //-----------------------------------------------------------------
                            //-----------------------------------------------------------------
                            //-----------------------------------------------------------------
                            Row fila2 = hoja.createRow(4);
                            Cell col2 = fila2.createCell(2);

                            col2.setCellStyle(style2);
                            col2.setCellValue("Total MXN:");
                            //--------------------------------------------------------------
                            Cell col5 = fila2.createCell(3);
                            DecimalFormat formato = new DecimalFormat("#,##0.00");

                            Number ad = 0;
                            try{
                                ad = formato.parse(lblTotalMxn.getText());
                            }catch(Exception e){
                                ad = 0;
                            }
                            double t = ad.doubleValue();

                            col5.setCellStyle(style4);
                            col5.setCellValue(t);
                            //        ---------------------------------------------------
                            Row fila3 = hoja.createRow(5);
                            Cell col3 = fila3.createCell(2);

                            col3.setCellStyle(style2);
                            col3.setCellValue("Total USD:");

                            //-----------------------------------------------------------------
                            //-----------------------------------------------------------------
                            //-----------------------------------------------------------------
                            Cell col6 = fila3.createCell(3);

                            try{
                            ad = formato.parse(lblTotalUsd.getText());
                            }catch(Exception e){
                                ad = 0;
                            }
                            t = ad.doubleValue();
                            col6.setCellStyle(style5);
                            col6.setCellValue(t);
                            //--------------------------------------------------------------
                            Row fila4 = hoja.createRow(6);
                            Cell col4 = fila4.createCell(2);

                            col4.setCellStyle(style2);
                            col4.setCellValue("Precio venta:");
                            //--------------------------------------------
                            Cell col7 = fila4.createCell(3);

                            try{
                            ad = formato.parse(lblCostoProyecto.getText());
                            }catch(Exception e){
                                ad = 0;
                            }
                            t = ad.doubleValue();
                            col7.setCellStyle(style6);
                            col7.setCellValue(t);

                            //-----------------------------------------------------------------

                            for (int i = -1; i < Tabla1.getRowCount(); i++) {
                                Row fila10=hoja.createRow(i+9);
                                for (int j = 0; j < Tabla1.getColumnCount(); j++) {
                                    Cell celda=fila10.createCell(j+2);
                                    if(i == -1 && (j >= 0 && j < Tabla1.getColumnCount())){
                                        XSSFCellStyle s = (XSSFCellStyle) book.createCellStyle();
                                        Font f = book.createFont();
                                        XSSFColor color1 = new XSSFColor(new java.awt.Color(49, 84, 151)); // RGB: (255, 0, 0) - Rojo
                                        s.setFillForegroundColor(color1);
                                        s.setFillPattern(SOLID_FOREGROUND);
                                        f.setBold(true);

                                        f.setColor(IndexedColors.WHITE.getIndex());
                                        s.setFont(f);
                                        s.setAlignment(HorizontalAlignment.CENTER);
                                        celda.setCellStyle(s);
                                    }

                                    if(i > -1 && (j > -1 && j < Tabla1.getColumnCount()) && (i%2 == 0)){
                                        XSSFCellStyle s = (XSSFCellStyle) book.createCellStyle();
                                        XSSFColor color1 = new XSSFColor(new java.awt.Color(216, 225, 242));
                                        s.setFillForegroundColor(color1);
                                        s.setFillPattern(SOLID_FOREGROUND);
                                        celda.setCellStyle(s);
                                    }

                                    if(i==-1){
                                        celda.setCellValue(String.valueOf(Tabla1.getColumnName(j)));
                                        //                        CellUtil.setCellStyleProperties(celda, properties);
                                    }else{
                                        if(j == 3){
                                            XSSFCellStyle ss = (XSSFCellStyle) book.createCellStyle();
                                            ss.setWrapText(true);

                                            if(i%2 == 0){
                                                XSSFColor color1 = new XSSFColor(new java.awt.Color(216, 225, 242));
                                                ss.setFillForegroundColor(color1);
                                                ss.setFillPattern(SOLID_FOREGROUND);

                                            }
                                            celda.setCellStyle(ss);
                                        }
                                        if(j >= 6 && j < 10){
                                            double d = Double.parseDouble((String) Tabla1.getValueAt(i, j));
                                            String ni = d + "";
                                            ad = formato.parse(ni);
                                            t = ad.doubleValue();
                                            celda.setCellValue(t);
                                        }else{
                                            celda.setCellValue(String.valueOf(Tabla1.getValueAt(i, j)));
                                        }
                                    }
                                    book.write(new FileOutputStream(a));
                                }
                            }
                            btnExcel.setEnabled(true);
                            book.close();
                            Desktop.getDesktop().open(new File(a));
                        }
                        btnExcel.setEnabled(true);
                    } catch (FileNotFoundException ex) {
                        btnExcel.setEnabled(true);
                        Logger.getLogger(CambiarEstado.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex) {
                        btnExcel.setEnabled(true);
                        Logger.getLogger(CambiarEstado.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (ParseException ex) {
                        btnExcel.setEnabled(true);
                        Logger.getLogger(ReporteMensual.class.getName()).log(Level.SEVERE, null, ex);
                    }
            }
        };
        hilo.start();
        }
    }//GEN-LAST:event_btnExcelActionPerformed

    private void txtProyectoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtProyectoFocusLost
        
    }//GEN-LAST:event_txtProyectoFocusLost

    private void txt1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt1FocusLost
        
    }//GEN-LAST:event_txt1FocusLost

    private void txt2FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt2FocusLost
        
    }//GEN-LAST:event_txt2FocusLost

    private void rbProyectoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbProyectoActionPerformed
        txtProyecto.setEnabled(true);
        txt1.setEnabled(false);
        txt2.setEnabled(false);
    }//GEN-LAST:event_rbProyectoActionPerformed

    private void rbFechaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbFechaActionPerformed
        txtProyecto.setEnabled(false);
        txt1.setEnabled(true);
        txt2.setEnabled(true);
    }//GEN-LAST:event_rbFechaActionPerformed

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ReporteMensual.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ReporteMensual.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ReporteMensual.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ReporteMensual.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                ReporteMensual dialog = new ReporteMensual(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable Tabla1;
    private javax.swing.JButton btnExcel;
    private javax.swing.ButtonGroup buttonGroup1;
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
    private javax.swing.JLabel jLabel2;
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
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel24;
    private javax.swing.JPanel jPanel25;
    private javax.swing.JPanel jPanel26;
    private javax.swing.JPanel jPanel27;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JFormattedTextField lblCostoProyecto;
    private javax.swing.JFormattedTextField lblFaltanteMxn;
    private javax.swing.JFormattedTextField lblFaltanteUsd;
    private javax.swing.JLabel lblMoneda;
    private javax.swing.JFormattedTextField lblRecibidoMxn;
    private javax.swing.JFormattedTextField lblRecibidoUsd;
    private javax.swing.JFormattedTextField lblTotalMxn;
    private javax.swing.JFormattedTextField lblTotalUsd;
    private rojeru_san.RSButton rSButton1;
    private javax.swing.JRadioButton rbFecha;
    private javax.swing.JRadioButton rbProyecto;
    private rojeru_san.rsdate.RSDateChooser txt1;
    private rojeru_san.rsdate.RSDateChooser txt2;
    private RSMaterialComponent.RSTextFieldMaterial txtProyecto;
    // End of variables declaration//GEN-END:variables
}
