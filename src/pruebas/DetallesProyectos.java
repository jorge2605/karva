package pruebas;

import Conexiones.Conexion;
import com.mxrck.autocompleter.TextAutoCompleter;
import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import org.apache.pdfbox.util.Hex;
import org.apache.poi.hssf.usermodel.HSSFCell;
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
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public final class DetallesProyectos extends java.awt.Dialog {

    TextAutoCompleter ta;
    DecimalFormatSymbols separador = new DecimalFormatSymbols();
    DecimalFormat formato = new DecimalFormat("#,###.##",separador);
    String req;
    
    public void limpiarTabla3(){
        DefaultTableModel miModelo;
        String titulos[] = {"NO. REQUISICION","NO. PARTE","DESCRIPCION","LLEGO","CANTIDAD","PRECIO","TOTAL"};
        miModelo = (new DefaultTableModel(null,titulos){
        boolean[] canEdit = new boolean [] {
            false, false, false, false, false, false, false
        };

        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return canEdit [columnIndex];
        }
        });
            Tabla3.setModel(miModelo);
            Tabla3.setShowHorizontalLines(false);
            Tabla3.setShowVerticalLines(false);
        }
    
    public void limpiarTabla1(){
        DefaultTableModel miModelo;
        String titulos[] = {"NO. REQUISICION","ESTADO","REQUISITOR"};
        miModelo = (new DefaultTableModel(null,titulos){
        boolean[] canEdit = new boolean [] {
            false, false, false
        };

        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return canEdit [columnIndex];
        }
        });
            Tabla1.setModel(miModelo);
            Tabla1.setShowHorizontalLines(false);
            Tabla1.setShowVerticalLines(false);
    }
    public void limpiarTabla2(){
        DefaultTableModel miModelo;
        String titulos[] = {"PO","ESTADO","LLEGO"};
        miModelo = (new DefaultTableModel(null,titulos){
        boolean[] canEdit = new boolean [] {
            false, false, false
        };

        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return canEdit [columnIndex];
        }
        });
            Tabla2.setModel(miModelo);
            Tabla2.setShowHorizontalLines(false);
            Tabla2.setShowVerticalLines(false);
    }
    
    public void autocompletar(){
        try{
            Connection con = null;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            String sql = "select Proyecto from Proyectos";
            ResultSet rs = st.executeQuery(sql);
            String datos[] = new String[10];
            ta = new TextAutoCompleter(txtProyecto);
            while(rs.next()){
                datos[0] = rs.getString("Proyecto");
                ta.addItem(datos[0]);
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this,"ERROR: "+e,"ERROR",JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void calcularRequisiciones(){
        for (int i = 0; i < Tabla3.getRowCount(); i++) {
            
            double Precio = 0, Cantidad = 0, Total = 0;
            
            if(Tabla3.getValueAt(i, 4) != null){
                
                Cantidad = Double.parseDouble(Tabla3.getValueAt(i, 4).toString());
                
                }else{
                    Tabla3.setValueAt("0", i, 5);
                    Tabla3.setValueAt("0", i, 6);
                }
                
                if(Tabla3.getValueAt(i, 5) != null){
                    if(Tabla3.getValueAt(i, 5).toString().equals("")){
                        Tabla3.setValueAt("0", i, 5);
                    }
                Precio = Double.parseDouble(Tabla3.getValueAt(i, 5).toString());
                Total = Cantidad * Precio;
                Tabla3.setValueAt(Total, i, 6);
                }else{
                    Tabla3.setValueAt("0", i, 5);
                    Tabla3.setValueAt("0", i, 6);
                }
        }
        double total = 0;
        for (int i = 0; i < Tabla3.getRowCount(); i++) {
            double tot ;
                tot = Double.parseDouble(Tabla3.getValueAt(i, 6).toString());
                total = total + tot;
        }
        req = total+"";
        formato.format(total);
        txtRequisiciones.setText(formato.format(total));
    }
    
    public void calcularHorasMaquinadas(){
        try{
            Connection con = null;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st1 = con.createStatement();
            Statement st2 = con.createStatement();
            Statement st3 = con.createStatement();
            String sql1 = "select * from torno where Plano like '"+txtProyecto.getText()+"'";
            String sql2 = "select * from fresadora where Plano like '"+txtProyecto.getText()+"'";
            String sql3 = "select * from cnc where Plano like '"+txtProyecto.getText()+"'";
            ResultSet rs1 = st1.executeQuery(sql1);
            ResultSet rs2 = st2.executeQuery(sql2);
            ResultSet rs3 = st3.executeQuery(sql3);
            String datos1[] = new String[10];
            String datos2[] = new String[10];
            String datos3[] = new String[10];
            
            double H = 0, M = 0, Horas = 0, Minutos = 0;
            String crono,crono2;
            while(rs1.next()){
                datos1[0] = rs1.getString("Cronometro");
                crono = datos1[0].substring(0,2);
                Horas = Horas + Double.parseDouble(crono);
                crono2 = datos1[0].substring(3,5);
                Minutos = Minutos + Double.parseDouble(crono2);
            }
            while(rs2.next()){
                datos2[0] = rs2.getString("Cronometro");
                crono = datos2[0].substring(0,2);
                Horas = Horas + Double.parseDouble(crono);
                crono2 = datos2[0].substring(3,5);
                Minutos = Minutos + Double.parseDouble(crono2);
            }
            while(rs3.next()){
                datos3[0] = rs3.getString("Cronometro");
                crono = datos3[0].substring(0,2);
                Horas = Horas + Double.parseDouble(crono);
                crono2 = datos3[0].substring(3,5);
                Minutos = Minutos + Double.parseDouble(crono2);
            }
            
            Minutos = Minutos / 60;
            
            Horas = (Horas + Minutos + 1) * 265;
            
            String maqu = ""+Horas;
            
            
            formato.format(Horas);
            txtMaquinados.setText((formato.format(Horas)));
            
            double tot = Double.parseDouble(maqu) + Double.parseDouble(req);
            
            String gas = tot+"";
            formato.format(tot);
            txtGasto.setText(formato.format(tot));
            
            String sql4 = "select * from Proyectos where Proyecto like '"+txtProyecto.getText()+"'";
            Statement st4 = con.createStatement();
            ResultSet rs4 = st4.executeQuery(sql4);
            String datos[] = new String[10];
            double cotizado = 0;
            while(rs4.next()){
                datos[0] = rs4.getString("Costo");
                datos[1] = rs4.getString("Moneda");
            }
            if(datos[1].equals("DLLS")){
                cotizado = Double.parseDouble(datos[0]) * 21;
            }else{
                cotizado = Double.parseDouble(datos[0]);
            }
            String cot = cotizado+"";
            formato.format(cotizado);
            txtCotizado.setText(formato.format(cotizado));
            
            double ganancia = 0;
            
            ganancia = Double.parseDouble(cot) - Double.parseDouble(gas);
            
            if(ganancia < 0){
                txtGanancia.setBackground(Color.red);
            }else{
                txtGanancia.setBackground(Color.green);
            }
            
            formato.format(ganancia);
            txtGanancia.setText(formato.format(ganancia));
            
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "ERROR: "+e,"ERROR",JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public DetallesProyectos(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        autocompletar();
        separador.setDecimalSeparator('.');
        revalidate();
        repaint();
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        txtProyecto = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jScrollPane1 = new javax.swing.JScrollPane();
        Tabla1 = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        Tabla2 = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        Tabla3 = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        txtRequisiciones = new javax.swing.JTextField();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtMaquinados = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        jLabel6 = new javax.swing.JLabel();
        txtGasto = new javax.swing.JTextField();
        jSeparator4 = new javax.swing.JSeparator();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtCotizado = new javax.swing.JTextField();
        jSeparator5 = new javax.swing.JSeparator();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jSeparator6 = new javax.swing.JSeparator();
        jLabel12 = new javax.swing.JLabel();
        txtGanancia = new javax.swing.JFormattedTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        btnSalir = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();

        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel3.setBackground(new java.awt.Color(0, 165, 252));

        jLabel7.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 60)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("PROYECTO");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(137, Short.MAX_VALUE)
                .addComponent(jLabel7)
                .addGap(99, 99, 99))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(0, 10, Short.MAX_VALUE)
                .addComponent(jLabel7))
        );

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 20, 590, 80));

        txtProyecto.setBackground(new java.awt.Color(255, 255, 255));
        txtProyecto.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        txtProyecto.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtProyecto.setBorder(null);
        txtProyecto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtProyectoActionPerformed(evt);
            }
        });
        jPanel1.add(txtProyecto, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 130, 220, 20));

        jLabel1.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        jLabel1.setText("BUSCAR PROYECTO");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 110, -1, -1));
        jPanel1.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 150, 220, 10));

        Tabla1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "NO. REQUISICION", "ESTADO", "REQUISITOR"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        Tabla1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Tabla1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(Tabla1);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 170, 470, 290));

        Tabla2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "PO", "ESTADO", "LLEGO"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        Tabla2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Tabla2MouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(Tabla2);

        jPanel1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 170, 440, 290));

        Tabla3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "NO. REQUISICION", "NO. PARTE", "DESCRIPCION", "LLEGO", "CANTIDAD", "PRECIO", "TOTAL"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane3.setViewportView(Tabla3);

        jPanel1.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 470, 920, 260));

        jLabel2.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        jLabel2.setText("TOTAL REQUISICIONES");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(970, 310, -1, -1));

        txtRequisiciones.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        txtRequisiciones.setForeground(new java.awt.Color(0, 0, 0));
        txtRequisiciones.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtRequisiciones.setBorder(null);
        jPanel1.add(txtRequisiciones, new org.netbeans.lib.awtextra.AbsoluteConstraints(970, 330, 130, 20));
        jPanel1.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(970, 350, 130, 10));

        jLabel3.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        jLabel3.setText("$");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(1100, 330, 20, 20));

        jLabel4.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        jLabel4.setText("TOTAL HORAS MAQUINADAS");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(970, 390, -1, -1));

        txtMaquinados.setEditable(false);
        txtMaquinados.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        txtMaquinados.setForeground(new java.awt.Color(0, 0, 0));
        txtMaquinados.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtMaquinados.setBorder(null);
        jPanel1.add(txtMaquinados, new org.netbeans.lib.awtextra.AbsoluteConstraints(970, 410, 130, 20));

        jLabel5.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        jLabel5.setText("$");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(1100, 410, 20, 20));
        jPanel1.add(jSeparator3, new org.netbeans.lib.awtextra.AbsoluteConstraints(970, 430, 130, 10));

        jLabel6.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        jLabel6.setText("GASTO TOTAL");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(970, 460, -1, -1));

        txtGasto.setEditable(false);
        txtGasto.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        txtGasto.setForeground(new java.awt.Color(0, 0, 0));
        txtGasto.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtGasto.setBorder(null);
        jPanel1.add(txtGasto, new org.netbeans.lib.awtextra.AbsoluteConstraints(970, 480, 130, 20));
        jPanel1.add(jSeparator4, new org.netbeans.lib.awtextra.AbsoluteConstraints(970, 500, 130, 10));

        jLabel8.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        jLabel8.setText("$");
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(1100, 480, 20, 20));

        jLabel9.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        jLabel9.setText("COSTO COTIZDO");
        jPanel1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(970, 520, -1, -1));

        txtCotizado.setEditable(false);
        txtCotizado.setBackground(new java.awt.Color(255, 255, 255));
        txtCotizado.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        txtCotizado.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtCotizado.setBorder(null);
        jPanel1.add(txtCotizado, new org.netbeans.lib.awtextra.AbsoluteConstraints(970, 540, 130, 20));
        jPanel1.add(jSeparator5, new org.netbeans.lib.awtextra.AbsoluteConstraints(970, 560, 130, 10));

        jLabel10.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        jLabel10.setText("$");
        jPanel1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(1100, 540, 20, 20));

        jLabel11.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        jLabel11.setText("GANANCIA");
        jPanel1.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(970, 580, -1, -1));
        jPanel1.add(jSeparator6, new org.netbeans.lib.awtextra.AbsoluteConstraints(970, 620, 130, 10));

        jLabel12.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        jLabel12.setText("$");
        jPanel1.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(1100, 600, 20, 20));

        txtGanancia.setBackground(new java.awt.Color(255, 255, 255));
        txtGanancia.setBorder(null);
        txtGanancia.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,###.00"))));
        txtGanancia.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jPanel1.add(txtGanancia, new org.netbeans.lib.awtextra.AbsoluteConstraints(970, 600, 130, 20));

        jButton1.setBackground(new java.awt.Color(255, 255, 255));
        jButton1.setToolTipText("Reporte PO");
        jButton1.setBorder(null);
        jButton1.setBorderPainted(false);
        jButton1.setContentAreaFilled(false);
        jButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jButton1.setFocusPainted(false);
        jButton1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton1.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/ImgAnimacion/Bom_48.png"))); // NOI18N
        jButton1.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/ImgAnimacion/Bom_64.png"))); // NOI18N
        jButton1.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(1050, 650, 70, 70));

        jButton2.setBackground(new java.awt.Color(255, 255, 255));
        jButton2.setToolTipText("Reporte PO");
        jButton2.setBorder(null);
        jButton2.setBorderPainted(false);
        jButton2.setContentAreaFilled(false);
        jButton2.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jButton2.setFocusPainted(false);
        jButton2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton2.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/ImgAnimacion/Excel-grafica_48.png"))); // NOI18N
        jButton2.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/ImgAnimacion/Excel-grafica_64.png"))); // NOI18N
        jButton2.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(970, 650, 70, 70));

        btnSalir.setBackground(new java.awt.Color(255, 255, 255));

        jLabel13.setFont(new java.awt.Font("Roboto", 0, 36)); // NOI18N
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setText("X");
        jLabel13.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jLabel13.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel13MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel13MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel13MouseExited(evt);
            }
        });

        javax.swing.GroupLayout btnSalirLayout = new javax.swing.GroupLayout(btnSalir);
        btnSalir.setLayout(btnSalirLayout);
        btnSalirLayout.setHorizontalGroup(
            btnSalirLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, btnSalirLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        btnSalirLayout.setVerticalGroup(
            btnSalirLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, btnSalirLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel1.add(btnSalir, new org.netbeans.lib.awtextra.AbsoluteConstraints(1090, 10, 50, 50));

        add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    
    private void txtProyectoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtProyectoActionPerformed
        try{
            limpiarTabla3();
            limpiarTabla1();
            limpiarTabla2();
            DefaultTableModel miModelo = (DefaultTableModel) Tabla3.getModel();
            DefaultTableModel miModelo2 = (DefaultTableModel) Tabla1.getModel();
            DefaultTableModel miModelo3 = (DefaultTableModel) Tabla2.getModel();
            Connection con = null;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            Statement st2 = con.createStatement();
            String sql = "select * from Requisiciones where Proyecto like '"+txtProyecto.getText()+"' order by NumRequisicion";
            ResultSet rs = st.executeQuery(sql);
            
            String datos[] = new String[10];
            String datos2[] = new String[10];
            
            
            while(rs.next()){
                datos[0] = rs.getString("NumRequisicion");
                datos[1] = rs.getString("Codigo");
                datos[2] = rs.getString("Descripcion");
                datos[3] = rs.getString("Llego");
                datos[4] = rs.getString("Cantidad");
                datos[5] = rs.getString("Precio");
                miModelo.addRow(datos);
            
                int ta1 = Tabla1.getRowCount();
                int cont = 0;
                boolean band = true;
                if(Tabla1.getRowCount() == 0){
                    String sql2 = "select * from Requisicion where Id like '"+datos[0]+"'";
                        ResultSet rs2 = st2.executeQuery(sql2);
                        while(rs2.next()){
                        datos2[0] = rs2.getString("Id");
                        datos2[1] = rs2.getString("Progreso");
                        datos2[2] = rs2.getString("NumeroEmpleado");
                        miModelo2.addRow(datos2);
                        }
                }
                else if(Tabla1.getRowCount() >= 1){
                        do{
                        if((Tabla1.getValueAt(cont, 0)).equals(datos[0])){
                        band = false;
                        break;
                        }
                        cont = cont+1;
                        }
                        while(cont < (Tabla1.getRowCount()));
                }
                
                if(band == true){
                    String sql2 = "select * from Requisicion where Id like '"+datos[0]+"'";
                        ResultSet rs2 = st2.executeQuery(sql2);
                        while(rs2.next()){
                        datos2[0] = rs2.getString("Id");
                        datos2[1] = rs2.getString("Progreso");
                        datos2[2] = rs2.getString("NumeroEmpleado");
                        miModelo2.addRow(datos2);
                            }
                }
            }
        miModelo2.removeRow(0);
        
            for (int i = 0; i < Tabla1.getRowCount(); i++) {
                String sql3 = "select * from ordencompra where RequisicionNo like '"+Tabla1.getValueAt(i, 0).toString()+"'";
                Statement st3 = con.createStatement();
                ResultSet rs3 = st3.executeQuery(sql3);
                String datos1[] = new String[10];
                while(rs3.next()){
                    datos1[0] = rs3.getString("OrdenNo");
                    datos1[1] = rs3.getString("Status");
                    datos1[2] = rs3.getString("Llego");
                    miModelo3.addRow(datos1);
                    
                }
            }
            calcularRequisiciones();
            calcularHorasMaquinadas();
            
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "ERROR: "+e,"ERROR",JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_txtProyectoActionPerformed

    private void Tabla1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Tabla1MouseClicked
        try{
            limpiarTabla3();
            Connection con = null;
            DefaultTableModel miModelo = (DefaultTableModel) Tabla3.getModel();
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            String sql = "select * from Requisiciones where NumRequisicion like '"+Tabla1.getValueAt(Tabla1.getSelectedRow(), 0)+"'";
            ResultSet rs = st.executeQuery(sql);
            String datos[] = new String[10];
            while(rs.next()){
                datos[0] = rs.getString("NumRequisicion");
                datos[1] = rs.getString("Codigo");
                datos[2] = rs.getString("Descripcion");
                datos[3] = rs.getString("Llego");
                datos[4] = rs.getString("Cantidad");
                datos[5] = rs.getString("Precio");
                datos[6] = ("");
                miModelo.addRow(datos);
            }
            
            for (int i = 0; i < Tabla3.getRowCount(); i++) {
            
            double Precio = 0, Cantidad = 0, Total = 0;
            
            if(Tabla3.getValueAt(i, 4) != null){
                Cantidad = Double.parseDouble(Tabla3.getValueAt(i, 4).toString());
                }else{
                    Tabla3.setValueAt("0", i, 5);
                    Tabla3.setValueAt("0", i, 6);
                }
                
                if(Tabla3.getValueAt(i, 5) != null){
                Precio = Double.parseDouble(Tabla3.getValueAt(i, 5).toString());
                Total = Cantidad * Precio;
                Tabla3.setValueAt(Total, i, 6);
                }else{
                    Tabla3.setValueAt("0", i, 5);
                    Tabla3.setValueAt("0", i, 6);
                }
        }
            
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "ERROR: "+e,"ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_Tabla1MouseClicked

    private void Tabla2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Tabla2MouseClicked
        try{
            limpiarTabla3();
            Connection con = null;
            DefaultTableModel miModelo = (DefaultTableModel) Tabla3.getModel();
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            
            Statement st2 = con.createStatement();
            String sql2 = "select * from Requisiciones where OC like '"+Tabla2.getValueAt(Tabla2.getSelectedRow(), 0)+"'";
            ResultSet rs2 = st2.executeQuery(sql2);
            String datos[] = new String[10];
            String datos2[] = new String[10];
            
            while(rs2.next()){
                datos2[0] = rs2.getString("NumRequisicion");
                datos2[1] = rs2.getString("Codigo");
                datos2[2] = rs2.getString("Descripcion");
                datos2[3] = rs2.getString("Llego");
                datos2[4] = rs2.getString("Cantidad");
                datos2[5] = rs2.getString("Precio");
                datos2[6] = ("");
                miModelo.addRow(datos2);
            }
            
            for (int i = 0; i < Tabla3.getRowCount(); i++) {
            
            double Precio = 0, Cantidad = 0, Total = 0;
            
            if(Tabla3.getValueAt(i, 4) != null){
                Cantidad = Double.parseDouble(Tabla3.getValueAt(i, 4).toString());
                }else{
                    Tabla3.setValueAt("0", i, 5);
                    Tabla3.setValueAt("0", i, 6);
                }
                
                if(Tabla3.getValueAt(i, 5) != null){
                Precio = Double.parseDouble(Tabla3.getValueAt(i, 5).toString());
                Total = Cantidad * Precio;
                Tabla3.setValueAt(Total, i, 6);
                }else{
                    Tabla3.setValueAt("0", i, 5);
                    Tabla3.setValueAt("0", i, 6);
                }
        }
            
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "ERROR: "+e,"ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_Tabla2MouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
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
        
        Sheet hoja = book.createSheet("REPORTE DE MATERIAL " +txtProyecto.getText());
        Row fila = hoja.createRow(2);
        Cell col = fila.createCell(2);
        
        Row fila1 = hoja.createRow(4);
        Cell col1 = fila1.createCell(2);
        
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
        
        hoja.setColumnWidth(3, 6500);
        hoja.setColumnWidth(4, 6500); 
        hoja.setColumnWidth(5, 8200); 
        
        Font font1 = book.createFont();
        XSSFCellStyle style = (XSSFCellStyle) book.createCellStyle();
        
        font1.setBold(true);
        font1.setColor(IndexedColors.WHITE.getIndex());
        font1.setFontHeightInPoints((short)18);
        style.setFont(font1);
        
        String rgbS = "833c0c";
        byte[] rgbB = Hex.decodeHex(rgbS); // get byte array from hex string
        XSSFColor color = new XSSFColor(rgbB, null);
        
        style.setFillBackgroundColor(color);
        style.setFillForegroundColor(color);
        style.setFillPattern(SOLID_FOREGROUND);
        style.setVerticalAlignment(VerticalAlignment.BOTTOM);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setWrapText(true);
        
        hoja.addMergedRegion(new CellRangeAddress (
        2,
        2,
        2,
        8
        ));
        
        hoja.addMergedRegion(new CellRangeAddress (
        4,
        4,
        2,
        4
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
        col.setCellValue("MATERIAL PEDIDO PARA PROYECTO");
        
        col1.setCellStyle(estilo1);
        col1.setCellValue("PROYECTO: " + txtProyecto.getText());
        
            
        for (int i = -1; i < Tabla3.getRowCount(); i++) {
                Row fila10=hoja.createRow(i+7);
                for (int j = 0; j < 7; j++) {
                    Cell celda=fila10.createCell(j+2);
                    if(i == -1 && (j >= 0 && j <=7)){
                        XSSFCellStyle s = (XSSFCellStyle) book.createCellStyle();
                        Font f = book.createFont();
                        f.setBold(true);
                        f.setColor(IndexedColors.WHITE.getIndex());
                        s.setFont(f);
                        
                        String rgbS2 = "c65911";
                        byte[] rgbB2 = Hex.decodeHex(rgbS2); // get byte array from hex string
                        XSSFColor color2 = new XSSFColor(rgbB2, null);
                        
                        s.setFillForegroundColor(color2);
                        s.setFillPattern(SOLID_FOREGROUND);
                        celda.setCellStyle(s);
                    }
                    if(i > -1 && (j > -1 && j <= 7) && (i%2 == 0)){
                        XSSFCellStyle s = (XSSFCellStyle) book.createCellStyle();
                        
                        String rgbS2 = "fce4d6";
                        byte[] rgbB2 = Hex.decodeHex(rgbS2); // get byte array from hex string
                        XSSFColor color2 = new XSSFColor(rgbB2, null);
                        
                        s.setFillForegroundColor(color2);
                        s.setFillPattern(SOLID_FOREGROUND);
                        celda.setCellStyle(s);
                    }
                    
                    if(i==-1){
                        celda.setCellValue(String.valueOf(Tabla3.getColumnName(j)));
//                        CellUtil.setCellStyleProperties(celda, properties);
                    }else{
                        if(j == 3){
                        XSSFCellStyle ss = (XSSFCellStyle) book.createCellStyle();
                        
                        String rgbS2 = "fce4d6";
                        byte[] rgbB2 = Hex.decodeHex(rgbS2); // get byte array from hex string
                        XSSFColor color2 = new XSSFColor(rgbB2, null);
                        
                        ss.setWrapText(true);
                        
                            if(i%2 == 0){
                            ss.setFillForegroundColor(color2);
                            ss.setFillPattern(SOLID_FOREGROUND);

                            }
                            celda.setCellStyle(ss);
                        }
                        celda.setCellValue(String.valueOf(Tabla3.getValueAt(i, j)));
//                        CellUtil.setCellStyleProperties(celda, properties);
                        
                        
                       
                    }
                    File ad = new File(a);
                    book.write(new FileOutputStream(a));                }
            }
            
        
    
        
        book.close();
    } catch (FileNotFoundException ex) {
        Logger.getLogger(CambiarEstado.class.getName()).log(Level.SEVERE, null, ex);
    } catch (IOException ex) {
        Logger.getLogger(CambiarEstado.class.getName()).log(Level.SEVERE, null, ex);
    }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
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
        
        Sheet hoja = book.createSheet("REPORTE PO " +txtProyecto.getText());
        Row fila = hoja.createRow(2);
        Cell col = fila.createCell(2);
        
        Row fila1 = hoja.createRow(4);
        Cell col1 = fila1.createCell(2);
        
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
        
        hoja.setColumnWidth(2, 6500);
        hoja.setColumnWidth(3, 6500);
        hoja.setColumnWidth(4, 6500); 
        hoja.setColumnWidth(5, 8200); 
        
        Font font1 = book.createFont();
        XSSFCellStyle style = (XSSFCellStyle) book.createCellStyle();
        
        font1.setBold(true);
        font1.setColor(IndexedColors.WHITE.getIndex());
        font1.setFontHeightInPoints((short)18);
        style.setFont(font1);
        
        String rgbS = "203764";
        byte[] rgbB = Hex.decodeHex(rgbS); // get byte array from hex string
        XSSFColor color = new XSSFColor(rgbB, null);
        
        style.setFillBackgroundColor(color);
        style.setFillForegroundColor(color);
        style.setFillPattern(SOLID_FOREGROUND);
        style.setVerticalAlignment(VerticalAlignment.BOTTOM);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setWrapText(true);
        
        hoja.addMergedRegion(new CellRangeAddress (
        2,
        2,
        2,
        5
        ));
        
        hoja.addMergedRegion(new CellRangeAddress (
        4,
        4,
        2,
        4
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
        col.setCellValue("REPORTE PO");
        
        col1.setCellStyle(estilo1);
        col1.setCellValue("PROYECTO: " + txtProyecto.getText());
        
            
        for (int i = -1; i < Tabla2.getRowCount(); i++) {
                Row fila10=hoja.createRow(i+7);
                int art = 0, rec = 0, total = 0;
                if(i != -1){
                try{
                        Connection con = null;
                        Conexion con1 = new Conexion();
                        con = con1.getConnection();
                        Statement st = con.createStatement();
                        String sql ="select * from Requisiciones where OC like '"+Tabla2.getValueAt(i, 0).toString()+"'";
                        ResultSet rs = st.executeQuery(sql);
                        
                        String reci;
                        while(rs.next()){
                            reci = rs.getString("Llego");
                            art++;
                            if(reci != null){
                                rec++;
                            }
                        }
                        if(art != 0){
                            total = (rec*100)/art;
                        }
                        
                    }catch(SQLException e){
                        JOptionPane.showMessageDialog(this, "ERROR: "+e,"ERROR",JOptionPane.ERROR_MESSAGE);
                    }
                }
                for (int j = 0; j < 4; j++) {
                    Cell celda=fila10.createCell(j+2);                    
                    if(i == -1 && (j >= 0 && j <=4)){
                        XSSFCellStyle s = (XSSFCellStyle) book.createCellStyle();
                        Font f = book.createFont();
                        f.setBold(true);
                        f.setColor(IndexedColors.WHITE.getIndex());
                        s.setFont(f);
                        
                        String rgbS2 = "305496";
                        byte[] rgbB2 = Hex.decodeHex(rgbS2);
                        XSSFColor color2 = new XSSFColor(rgbB2, null);
                        
                        s.setFillForegroundColor(color2);
                        s.setFillPattern(SOLID_FOREGROUND);
                        celda.setCellStyle(s);
                    }
                    if(i > -1 && (j > -1 && j <= 4) && (i%2 == 0)){
                        XSSFCellStyle s = (XSSFCellStyle) book.createCellStyle();
                        
                        String rgbS2 = "d9e1f2";
                        byte[] rgbB2 = Hex.decodeHex(rgbS2); 
                        XSSFColor color2 = new XSSFColor(rgbB2, null);
                        
                        s.setFillForegroundColor(color2);
                        s.setFillPattern(SOLID_FOREGROUND);
                        celda.setCellStyle(s);
                    }
                    
                    if(i==-1){
                        if(j == 0){
                            celda.setCellValue("PO generadas");
                        } else if(j == 1){
                            celda.setCellValue("Total articulos");
                        }else if(j == 2){
                            celda.setCellValue("Articulos recibidos");
                        }else if(j == 3){
                            celda.setCellValue("Porcentaje");
                        }
                        
                    }else{
                        if(j == 3){
                        XSSFCellStyle ss = (XSSFCellStyle) book.createCellStyle();
                        
                        String rgbS2 = "d9e1f2";
                        byte[] rgbB2 = Hex.decodeHex(rgbS2);
                        XSSFColor color2 = new XSSFColor(rgbB2, null);
                        
                        ss.setWrapText(true);
                        
                            if(i%2 == 0){
                            ss.setFillForegroundColor(color2);
                            ss.setFillPattern(SOLID_FOREGROUND);

                            }
                            celda.setCellStyle(ss);
                        }
                        if(j == 0){
                            celda.setCellValue(String.valueOf(Tabla2.getValueAt(i, j)));
                        }else if(j == 1){
                            celda.setCellValue(art);
                        }else if(j == 2){
                            celda.setCellValue(rec);
                        }else if(j == 3){
                            celda.setCellValue(total);
                        }
                        Row fila11=hoja.createRow(i+7+1);
                        if(i == Tabla2.getRowCount()-1 && j == 2){
                            
                            Cell cel = fila11.createCell(4);
                            cel.setCellValue("Porcentaje total de recibidos:");
                        }
                        if(i == Tabla2.getRowCount()-1 && j == 3){
                            String strFormula= "SUMA(F8:F"+String.valueOf(Tabla2.getRowCount()+7)+")/("+String.valueOf(Tabla2.getRowCount())+")";
                            String form = "SUMA(F8:F10)";
                            XSSFCell cell = (XSSFCell) fila11.createCell(5);
                            cell.setCellFormula(form);
                        }
                        
                       
                    }
                    File ad = new File(a);
                    book.write(new FileOutputStream(a));                
                }
            }
            
        
    
        
        book.close();
    } catch (FileNotFoundException ex) {
        Logger.getLogger(CambiarEstado.class.getName()).log(Level.SEVERE, null, ex);
    } catch (IOException ex) {
        Logger.getLogger(CambiarEstado.class.getName()).log(Level.SEVERE, null, ex);
    }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jLabel13MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel13MouseClicked
        dispose();
    }//GEN-LAST:event_jLabel13MouseClicked

    private void jLabel13MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel13MouseEntered
        btnSalir.setBackground(Color.red);
    }//GEN-LAST:event_jLabel13MouseEntered

    private void jLabel13MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel13MouseExited
        btnSalir.setBackground(Color.white);
    }//GEN-LAST:event_jLabel13MouseExited

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        dispose();
    }//GEN-LAST:event_formWindowClosing

    
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                DetallesProyectos dialog = new DetallesProyectos(new java.awt.Frame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
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
    private javax.swing.JTable Tabla2;
    private javax.swing.JTable Tabla3;
    private javax.swing.JPanel btnSalir;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JTextField txtCotizado;
    private javax.swing.JFormattedTextField txtGanancia;
    private javax.swing.JTextField txtGasto;
    private javax.swing.JTextField txtMaquinados;
    private javax.swing.JTextField txtProyecto;
    private javax.swing.JTextField txtRequisiciones;
    // End of variables declaration//GEN-END:variables
}
