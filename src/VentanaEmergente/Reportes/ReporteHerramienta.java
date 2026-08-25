package VentanaEmergente.Reportes;

import Conexiones.Conexion;
import com.mxrck.autocompleter.TextAutoCompleter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
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

public class ReporteHerramienta extends javax.swing.JDialog {

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
                    "CODIGO", "CANTIDAD", "PROYECTO", "REQUISITOR", "PRECIO MXN", "PRECIO USD", "TOTAL MXN", "TOTAL USD", "FECHA"
                }
            ) {
                boolean[] canEdit = new boolean [] {
                    false, false, false, false, false, false, false, false,false
                };

                public boolean isCellEditable(int rowIndex, int columnIndex) {
                    return canEdit [columnIndex];
                }
            });

            Tabla1.setShowGrid(false);
        }
    
    public ReporteHerramienta(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        autoCompletarProyecto();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        txtProyecto = new RSMaterialComponent.RSTextFieldMaterial();
        txt1 = new rojeru_san.rsdate.RSDateChooser();
        jLabel2 = new javax.swing.JLabel();
        txt2 = new rojeru_san.rsdate.RSDateChooser();
        jLabel3 = new javax.swing.JLabel();
        rSButton1 = new rojeru_san.RSButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        Tabla1 = new javax.swing.JTable();
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
        lblTotalUsd = new javax.swing.JFormattedTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        lblTotalProy = new javax.swing.JFormattedTextField();
        jLabel11 = new javax.swing.JLabel();
        lblMoneda = new javax.swing.JLabel();
        jPanel14 = new javax.swing.JPanel();
        jPanel15 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        btnExcel = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new java.awt.BorderLayout());

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Roboto Black", 0, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 102, 204));
        jLabel1.setText("Reporte salidas de almacen");
        jPanel3.add(jLabel1);

        jPanel2.add(jPanel3);

        jPanel1.add(jPanel2, java.awt.BorderLayout.PAGE_START);

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setLayout(new java.awt.BorderLayout());

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 15, 5));

        txtProyecto.setForeground(new java.awt.Color(51, 51, 51));
        txtProyecto.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtProyecto.setPlaceholder("Proyecto");
        jPanel5.add(txtProyecto);
        jPanel5.add(txt1);

        jLabel2.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 112, 192));
        jLabel2.setText("&");
        jPanel5.add(jLabel2);
        jPanel5.add(txt2);

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

        Tabla1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "CODIGO", "CANTIDAD", "PROYECTO", "REQUISITOR", "PRECIO MXN", "PRECIO USD", "TOTAL MXN", "TOTAL USD", "FECHA"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        Tabla1.setShowGrid(false);
        jScrollPane1.setViewportView(Tabla1);

        jPanel4.add(jScrollPane1, java.awt.BorderLayout.CENTER);

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
        lblTotalMxn.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter()));
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

        lblTotalUsd.setEditable(false);
        lblTotalUsd.setBackground(new java.awt.Color(255, 255, 255));
        lblTotalUsd.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(240, 240, 240)));
        lblTotalUsd.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter()));
        lblTotalUsd.setPreferredSize(new java.awt.Dimension(110, 17));
        jPanel11.add(lblTotalUsd, java.awt.BorderLayout.CENTER);

        jLabel9.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("Total USD");
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

        lblTotalProy.setEditable(false);
        lblTotalProy.setBackground(new java.awt.Color(255, 255, 255));
        lblTotalProy.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(240, 240, 240)));
        lblTotalProy.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter()));
        lblTotalProy.setPreferredSize(new java.awt.Dimension(110, 17));
        jPanel13.add(lblTotalProy, java.awt.BorderLayout.CENTER);

        jLabel11.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("Costo Proyecto");
        jPanel13.add(jLabel11, java.awt.BorderLayout.PAGE_START);

        lblMoneda.setBackground(new java.awt.Color(255, 255, 255));
        lblMoneda.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        lblMoneda.setText("$");
        jPanel13.add(lblMoneda, java.awt.BorderLayout.WEST);

        jPanel12.add(jPanel13);

        jPanel7.add(jPanel12);

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

        jPanel7.add(jPanel14);

        jPanel6.add(jPanel7);

        jPanel4.add(jPanel6, java.awt.BorderLayout.LINE_END);

        jPanel1.add(jPanel4, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void rSButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rSButton1ActionPerformed
        limpiarTabla();
        try{
            DefaultTableModel miModelo = (DefaultTableModel) Tabla1.getModel();
            Connection con ;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String add = "";
            if(!txtProyecto.getText().equals("")){
                add = "Proyecto like '"+txtProyecto.getText()+"' and";
                Statement st7 = con.createStatement();
                String sql7 = "select Proyecto, Costo, Moneda from Proyectos where Proyecto like '"+txtProyecto.getText()+"'";
                ResultSet rs7 = st7.executeQuery(sql7);
                while(rs7.next()){
                    lblMoneda.setText(rs7.getString("Moneda"));
                    lblTotalProy.setText(rs7.getString("Costo"));
                }
            }
            String sql = "SELECT * FROM pedidos WHERE "+add+" FechaSalida between '"+sdf.format(txt1.getDatoFecha())+"' and '"+sdf.format(txt2.getDatoFecha())+"' order by Id desc";
            ResultSet rs = st.executeQuery(sql);
            String datos[] = new String[15];
            String d[] = new String[15];
            double totalMxn = 0;
            double totalUsd = 0;
            while(rs.next()){
                //RESULTADO DE ORDENES DE COMPRAS
                double prec = 0;
                datos[0] = rs.getString("NumParte");
                datos[1] = rs.getString("Cantidad");
                datos[2] = rs.getString("Proyecto");
                datos[3] = rs.getString("Requisitor");
                datos[8] = rs.getString("FechaSalida");
                String sql2 = "select * from requisiciones where Codigo like '"+datos[0]+"'";
                Statement st2 = con.createStatement();
                ResultSet rs2 = st2.executeQuery(sql2);
                double precioMxn = 0, precioUsd = 0, precioU;
                
                while(rs2.next()){
                    //RESULTADO DE REQUISICIONES DE COMPRA
                    datos[9] = rs2.getString("Proveedor");
                    datos[10] = rs2.getString("Precio");
                }
                String sql3 = "SELECT * FROM registroprov_compras where Nombre like '"+datos[9]+"'";
                Statement st3 = con.createStatement();
                ResultSet rs3 = st3.executeQuery(sql3);
                String moneda = "";
                while(rs3.next()){
                    //RESULTADO DE LOS PROVEEDORES
                    moneda = rs3.getString("Moneda");
                }
                if(datos[10] == null){
                    precioU = 0;
                }else{
                    if(datos[10].equals("")){
                        precioU = 0;
                    }else{
                    precioU = Double.parseDouble(datos[10]);
                }
                }

                if(moneda.equals("MXN")){
                    precioMxn = (precioU * Double.parseDouble(datos[1]));
                    totalMxn += precioMxn;
                    d[4] = String.valueOf(precioU);//PRECIO MXN
                    d[5] = String.valueOf("0");//PRECIO DLS
                }else{
                    String sql4 = "SELECT * FROM preciodolar";
                    Statement st4 = con.createStatement();
                    ResultSet rs4 = st4.executeQuery(sql4);
                    double da = 0;
                    while(rs4.next()){
                        //RESULTADO DEL PRECIO ACTUAL DEL DOLAR
                        da = Double.parseDouble(rs4.getString("Precio"));
                    }
//                        precioUsd = (precioU * Double.parseDouble(datos[1]) * da);
                    precioUsd = (precioU * Double.parseDouble(datos[1]));
                    totalUsd += precioUsd;
                    d[4] = String.valueOf("0");//PRECIO MXN
                    d[5] = String.valueOf(precioU);//PRECIO DLS
                }
                
                
                d[0] = datos[0];//CODIGO
                d[1] = datos[1];//CANTIDAD
                d[2] = datos[2];//PROYECTO
                d[3] = datos[3];//REQUISITOR
                d[6] = String.valueOf(precioMxn);//TOTAL MXN
                d[7] = String.valueOf(precioUsd);//TOTAL USD
                d[8] = datos[8];//TOTAL USD
                miModelo.addRow(d);
                btnExcel.setEnabled(true);

            }

            lblTotalMxn.setValue(totalMxn);
            lblTotalUsd.setValue(totalUsd);
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "ERROR: "+e,"ERROR",JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_rSButton1ActionPerformed

    private void btnExcelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcelActionPerformed
        if(Tabla1.getRowCount() == 0){
            JOptionPane.showMessageDialog(this, "LA TABLA DEBE CONTENER REGISTROS");
        }else{
            Workbook book;
        try {
        JFileChooser fc = new JFileChooser();
        File archivo = null;
        fc.setFileFilter(new FileNameExtensionFilter("EXCEL (*.xlsx)", "xlsx"));
        int n = fc.showSaveDialog(this);
           
        if(n == JFileChooser.APPROVE_OPTION){
        archivo = fc.getSelectedFile();
        }
        String a = ""+archivo;
        if(a.endsWith("xls")){
        book = new  HSSFWorkbook();
        }else {
        book = new XSSFWorkbook();
        a = archivo + ".xlsx";
        }
        
        Sheet hoja = book.createSheet("Reporte de salida de material");
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
        hoja.setColumnWidth(5, 8200); 
        hoja.setColumnWidth(6, 4100); 
        hoja.setColumnWidth(7, 4100); 
        hoja.setColumnWidth(8, 4100); 
        hoja.setColumnWidth(9, 4100); 
        hoja.setColumnWidth(10, 4100); 
        
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
        10
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
        col.setCellValue("Reporte de salida de material");
        //-----------------------------------------------------------------
        Row fila2 = hoja.createRow(4);
        Cell col2 = fila2.createCell(1);
        
        col2.setCellStyle(style2);
        col2.setCellValue("Total MXN:");
        //--------------------------------------------------------------
        Row fila3 = hoja.createRow(5);
        Cell col3 = fila3.createCell(1);
        
        col3.setCellStyle(style2);
        col3.setCellValue("Total USD:");
        //-----------------------------------------------------------------
        Row fila4 = hoja.createRow(6);
        Cell col4 = fila4.createCell(1);
        
        col4.setCellStyle(style2);
        col4.setCellValue("Precio venta:");
//        ---------------------------------------------------
        Row fila5 = hoja.createRow(4);
        Cell col5 = fila5.createCell(3);
        
        col5.setCellStyle(style4);
        col5.setCellValue(lblTotalMxn.getText());
//        ---------------------------------------------------
        Row fila6 = hoja.createRow(5);
        Cell col6 = fila6.createCell(3);
        
        col6.setCellStyle(style5);
        col6.setCellValue(lblTotalUsd.getText());
//        ---------------------------------------------------
        Row fila7 = hoja.createRow(6);
        Cell col7 = fila7.createCell(3);
        
        col7.setCellStyle(style6);
        col7.setCellValue(lblTotalProy.getText());
        
        for (int i = -1; i < Tabla1.getRowCount(); i++) {
                Row fila10=hoja.createRow(i+9);
                for (int j = 0; j < 9; j++) {
                    Cell celda=fila10.createCell(j+2);
                    if(i == -1 && (j >= 0 && j <=8)){
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
                    
                    if(i > -1 && (j > -1 && j <= 8) && (i%2 == 0)){
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
                        celda.setCellValue(String.valueOf(Tabla1.getValueAt(i, j)));
//                        CellUtil.setCellStyleProperties(celda, properties);
                        
                        
                       
                    }
                    try{
                        book.write(new FileOutputStream(a));  
                    }catch(Exception e){
                        JOptionPane.showMessageDialog(this, "Error: "+e,"ERROR",JOptionPane.ERROR_MESSAGE);
                    }
                                  
                }
            }
            
        
    
        
        book.close();
    } catch (FileNotFoundException ex) {
        Logger.getLogger(CambiarEstado.class.getName()).log(Level.SEVERE, null, ex);
    } catch (IOException ex) {
        Logger.getLogger(CambiarEstado.class.getName()).log(Level.SEVERE, null, ex);
    }
        }
    }//GEN-LAST:event_btnExcelActionPerformed

    /**
     * @param args the command line arguments
     */
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
            java.util.logging.Logger.getLogger(ReporteHerramienta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ReporteHerramienta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ReporteHerramienta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ReporteHerramienta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                ReporteHerramienta dialog = new ReporteHerramienta(new javax.swing.JFrame(), true);
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
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblMoneda;
    private javax.swing.JFormattedTextField lblTotalMxn;
    private javax.swing.JFormattedTextField lblTotalProy;
    private javax.swing.JFormattedTextField lblTotalUsd;
    private rojeru_san.RSButton rSButton1;
    private rojeru_san.rsdate.RSDateChooser txt1;
    private rojeru_san.rsdate.RSDateChooser txt2;
    private RSMaterialComponent.RSTextFieldMaterial txtProyecto;
    // End of variables declaration//GEN-END:variables
}
