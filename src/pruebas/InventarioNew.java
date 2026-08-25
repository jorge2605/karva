package pruebas;

import Conexiones.Conexion;
import VentanaEmergente.cxp.info;
import VentanaEmergente.inventarioNew.infoInventario;
import com.mxrck.autocompleter.TextAutoCompleter;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

public class InventarioNew extends javax.swing.JInternalFrame implements ActionListener{

    public int seleccionado;
    TextAutoCompleter au;
    TextAutoCompleter ap;
    Stack<String> proyectos;
    public String empleado;
    
    public CardLayout card;
    
    private static class DoubleFilter extends DocumentFilter {
        @Override
        public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
            String newStr = fb.getDocument().getText(0, fb.getDocument().getLength()) + string;

            try {
                Double.parseDouble(newStr);
                super.insertString(fb, offset, string, attr);
            } catch (NumberFormatException e) {
                // Ignorar la inserción si no es un número double válido
            }
        }

        @Override
        public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
            String newStr = fb.getDocument().getText(0, fb.getDocument().getLength()) + text;

            try {
                Double.parseDouble(newStr);
                super.replace(fb, offset, length, text, attrs);
            } catch (NumberFormatException e) {
                // Ignorar la sustitución si no es un número double válido
            }
        }
    }
    
    public final void limpiarTabla(){
        Tabla1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "CODIGO", "DESCRIPCION", "CANTIDAD", "PROVEEDOR", "UBICACION", "SAL.", "ENT.", "ACT."
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false,true,true,true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        
        TableColumn col;
        col = Tabla1.getColumnModel().getColumn(5);
        col.setCellEditor(new myeditor(Tabla1,this,1));
        col.setCellRenderer(new renderer(false,1));
        
        col = Tabla1.getColumnModel().getColumn(6);
        col.setCellEditor(new myeditor(Tabla1,this,2));
        col.setCellRenderer(new renderer(false,2));
        
        col = Tabla1.getColumnModel().getColumn(7);
        col.setCellEditor(new myeditor(Tabla1,this,3));
        col.setCellRenderer(new renderer(false,3));
        
        Tabla1.getTableHeader().setFont(new Font("Lexend", Font.BOLD, 14));
        Tabla1.getTableHeader().setOpaque(false);
        Tabla1.getTableHeader().setBackground(new Color(0, 78, 171));
        Tabla1.getTableHeader().setForeground(Color.white);
        Tabla1.setRowHeight(25);
        Tabla1.setShowVerticalLines(false);
        Tabla1.setGridColor(new Color(240,240,240));
        
        jScrollPane1.getViewport().setBackground(new Color(255,255,255));
        
        if (Tabla1.getColumnModel().getColumnCount() > 0) {
            Tabla1.getColumnModel().getColumn(1).setMinWidth(300);
            Tabla1.getColumnModel().getColumn(1).setPreferredWidth(600);
            Tabla1.getColumnModel().getColumn(5).setMinWidth(50);
            Tabla1.getColumnModel().getColumn(5).setPreferredWidth(50);
            Tabla1.getColumnModel().getColumn(5).setMaxWidth(50);
            Tabla1.getColumnModel().getColumn(6).setMinWidth(50);
            Tabla1.getColumnModel().getColumn(6).setPreferredWidth(50);
            Tabla1.getColumnModel().getColumn(6).setMaxWidth(50);
            Tabla1.getColumnModel().getColumn(7).setMinWidth(50);
            Tabla1.getColumnModel().getColumn(7).setPreferredWidth(50);
            Tabla1.getColumnModel().getColumn(7).setMaxWidth(50);
        }
    }
    
    public final void limpiarTablaSalida(){
        Tabla1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "CODIGO", "CANTDAD", "PROYECTO", "REQUISITOR", "ALMACENISTA", "FECHA SALIDA"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        
        Tabla1.getTableHeader().setFont(new Font("Lexend", Font.BOLD, 14));
        Tabla1.getTableHeader().setOpaque(false);
        Tabla1.getTableHeader().setBackground(new Color(0, 78, 171));
        Tabla1.getTableHeader().setForeground(Color.white);
        Tabla1.setRowHeight(25);
        Tabla1.setShowVerticalLines(false);
        Tabla1.setGridColor(new Color(240,240,240));
        
        jScrollPane1.getViewport().setBackground(new Color(255,255,255));
        
        if (Tabla1.getColumnModel().getColumnCount() > 0) {
            Tabla1.getColumnModel().getColumn(0).setMinWidth(300);
            Tabla1.getColumnModel().getColumn(0).setPreferredWidth(600);
        }
    }
    
    public final void limpiarTablaEntrada(){
        Tabla1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "CODIGO", "CANTDAD", "ALMACENISTA", "FECHA ENTRADA"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        
        Tabla1.getTableHeader().setFont(new Font("Lexend", Font.BOLD, 14));
        Tabla1.getTableHeader().setOpaque(false);
        Tabla1.getTableHeader().setBackground(new Color(0, 78, 171));
        Tabla1.getTableHeader().setForeground(Color.white);
        Tabla1.setRowHeight(25);
        Tabla1.setShowVerticalLines(false);
        Tabla1.setGridColor(new Color(240,240,240));
        
        jScrollPane1.getViewport().setBackground(new Color(255,255,255));
        
        if (Tabla1.getColumnModel().getColumnCount() > 0) {
            Tabla1.getColumnModel().getColumn(0).setMinWidth(300);
            Tabla1.getColumnModel().getColumn(0).setPreferredWidth(600);
        }
    }
    
    public final void limpiarTablaActualizar(){
        Tabla1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "CODIGO", "CANTDAD", "ALMACENISTA", "FECHA ACTUALIZACION"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        
        Tabla1.getTableHeader().setFont(new Font("Lexend", Font.BOLD, 14));
        Tabla1.getTableHeader().setOpaque(false);
        Tabla1.getTableHeader().setBackground(new Color(0, 78, 171));
        Tabla1.getTableHeader().setForeground(Color.white);
        Tabla1.setRowHeight(25);
        Tabla1.setShowVerticalLines(false);
        Tabla1.setGridColor(new Color(240,240,240));
        
        jScrollPane1.getViewport().setBackground(new Color(255,255,255));
        
        if (Tabla1.getColumnModel().getColumnCount() > 0) {
            Tabla1.getColumnModel().getColumn(0).setMinWidth(300);
            Tabla1.getColumnModel().getColumn(0).setPreferredWidth(600);
        }
    }
    
    public final void verDatosSalida(String sql){
        try{
            Connection con;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            String datos[] = new String[10];
            ResultSet rs = st.executeQuery(sql);
            DefaultTableModel miModelo = (DefaultTableModel) Tabla1.getModel();
            while(rs.next()){
                datos[0] = rs.getString("NumParte");
                datos[1] = rs.getString("Cantidad");
                datos[2] = rs.getString("Proyecto");
                datos[3] = rs.getString("Requisitor");
                datos[4] = rs.getString("Almacenista");
                datos[5] = rs.getString("FechaSalida");
                miModelo.addRow(datos);
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "ERROR: "+e,"ERROR",JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public final void verDatosEntrada(String sql){
        try{
            Connection con;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            String datos[] = new String[10];
            ResultSet rs = st.executeQuery(sql);
            DefaultTableModel miModelo = (DefaultTableModel) Tabla1.getModel();
            while(rs.next()){
                datos[0] = rs.getString("NumParte");
                datos[1] = rs.getString("Cantidad");
                datos[2] = rs.getString("Almacenista");
                datos[3] = rs.getString("FechaSalida");
                miModelo.addRow(datos);
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "ERROR: "+e,"ERROR",JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public final void verDatosActualizacion(String sql){
        try{
            Connection con;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            String datos[] = new String[10];
            ResultSet rs = st.executeQuery(sql);
            DefaultTableModel miModelo = (DefaultTableModel) Tabla1.getModel();
            while(rs.next()){
                datos[0] = rs.getString("NumParte");
                datos[1] = rs.getString("Cantidad");
                datos[2] = rs.getString("Almacenista");
                datos[3] = rs.getString("FechaSalida");
                miModelo.addRow(datos);
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "ERROR: "+e,"ERROR",JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void limpiarBotones(){
        panelActualizar.setBackground(Color.white);
        panelEntrada.setBackground(Color.white);
        panelSalida.setBackground(Color.white);
        panelGeneral.setBackground(Color.white);
        btnActualizar.setForeground(new Color(0,102,204));
        btnEntrada.setForeground(new Color(0,102,204));
        btnSalida.setForeground(new Color(0,102,204));
        btnGeneral.setForeground(new Color(0,102,204));
    }
    
    public final void agregar(String numEmpleado){
        txtAlmacenista.setText(numEmpleado);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date d = new Date();
        txtFecha.setText(sdf.format(d));
        agregarProyecto();
        agregarNumero();
        limpiarTabla();
        agregarInventario("select NumeroDeParte, Cantidad, Proveedor, Ubicacion,Descripcion from inventario order by NumeroDeParte asc");
    }
    
    public final void agregarInventario(String sql){
        try{
            Connection con;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            String datos[] = new String[10];
            ResultSet rs = st.executeQuery(sql);
            DefaultTableModel miModelo = (DefaultTableModel) Tabla1.getModel();
            while(rs.next()){
                datos[0] = rs.getString("NumeroDeParte");
                datos[1] = rs.getString("Descripcion");
                datos[2] = rs.getString("Cantidad");
                datos[3] = rs.getString("Proveedor");
                datos[4] = rs.getString("Ubicacion");
                if(datos[0] != null){
                    if(!datos[0].equals("")){
                        miModelo.addRow(datos);
                    }
                }
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "ERROR: "+e,"ERROR",JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public final void agregarNumero(){
        au = new TextAutoCompleter(txtCodigo);
        try{
            Connection con;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            String sql = "select NumeroDeParte from inventario";
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
                au.addItem(rs.getString("NumeroDeParte"));
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "ERROR: "+e,"ERROR",JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public final void agregarProyecto(){
        ap = new TextAutoCompleter(txtProyecto);
        proyectos = new Stack<>();
        try{
            Connection con;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            String sql = "select Proyecto from Proyectos";
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
                ap.addItem(rs.getString("Proyecto"));
                proyectos.push(rs.getString("Proyecto"));
            }
            ap.addItem("TALLER");
            proyectos.push("TALLER");
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "ERROR: "+e,"ERROR",JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void pedidos(){
        try{
            Connection con;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            String sql = "insert into entrada(Almacenista, NumParte, Cantidad, FechaEntrada) values(?,?,?,?)";
            PreparedStatement pst = con.prepareStatement(sql);

            SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
            Date d = new Date();
            String fecha2 = sdf2.format(d);

            pst.setString(1, txtAlmacenista.getText());
            pst.setString(2, txtCodigo.getText());
            pst.setString(3, txtCantidad.getText());
            pst.setString(4, fecha2);

            int n = pst.executeUpdate();

            if(n > 0){
                JOptionPane.showMessageDialog(this, "Datos guardados en Entrada de material");
                dispose();
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "ERROR: "+e,"ERROR",JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public InventarioNew(String numEmpleado) {
        initComponents();
        this.empleado = numEmpleado;
        card = new CardLayout();
        panelCard.setLayout(card);
        panelCard.add(Salida,"panelSalida");
        panelCard.add(Entrada,"panelEntrada");
        agregar(numEmpleado);
//        insertarSemanas(0,0);
        ((javax.swing.plaf.basic.BasicInternalFrameUI) this.getUI()).setNorthPane(null);
        SwingUtilities.invokeLater(() -> txtRequisitor.requestFocusInWindow());
        scrollEntrada.getVerticalScrollBar().setUnitIncrement(15);
        ((AbstractDocument) txtCantidad.getDocument()).setDocumentFilter(new DoubleFilter());
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel1 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        pan = new javax.swing.JPanel();
        panelSalir = new javax.swing.JPanel();
        lblSalir = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        panelGeneral = new javax.swing.JPanel();
        btnGeneral = new javax.swing.JButton();
        panelSalida = new javax.swing.JPanel();
        btnSalida = new javax.swing.JButton();
        panelEntrada = new javax.swing.JPanel();
        btnEntrada = new javax.swing.JButton();
        panelActualizar = new javax.swing.JPanel();
        btnActualizar = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        panelCard = new javax.swing.JPanel();
        Salida = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtAlmacenista = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtFecha = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtRequisitor = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jPanel11 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        txtProyecto = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtCodigo = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        txtDesc = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        Entrada = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        scrollEntrada = new javax.swing.JScrollPane();
        jPanel8 = new javax.swing.JPanel();
        jPanel14 = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        txtParte = new javax.swing.JTextField();
        jPanel15 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtDescripcion = new javax.swing.JTextArea();
        jPanel16 = new javax.swing.JPanel();
        jPanel17 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        txtCantidad = new javax.swing.JTextField();
        jPanel18 = new javax.swing.JPanel();
        jPanel19 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        txtUbicacion = new javax.swing.JFormattedTextField();
        jPanel22 = new javax.swing.JPanel();
        panelGuardar = new javax.swing.JPanel();
        btnGuardar = new javax.swing.JButton();
        jPanel9 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        Tabla1 = new javax.swing.JTable();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();

        setBorder(null);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new java.awt.BorderLayout());

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setLayout(new java.awt.BorderLayout());

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));

        jLabel12.setFont(new java.awt.Font("Lexend", 1, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(0, 165, 252));
        jLabel12.setText("ALMACEN");
        jPanel6.add(jLabel12);

        jPanel5.add(jPanel6);

        jPanel4.add(jPanel5, java.awt.BorderLayout.CENTER);

        pan.setBackground(new java.awt.Color(255, 255, 255));

        panelSalir.setBackground(new java.awt.Color(255, 255, 255));

        lblSalir.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        lblSalir.setForeground(new java.awt.Color(0, 0, 0));
        lblSalir.setText(" X ");
        lblSalir.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblSalir.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblSalirMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblSalirMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblSalirMouseExited(evt);
            }
        });
        panelSalir.add(lblSalir);

        pan.add(panelSalir);

        jPanel4.add(pan, java.awt.BorderLayout.EAST);

        jPanel1.add(jPanel4, java.awt.BorderLayout.NORTH);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new java.awt.BorderLayout(10, 10));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        panelGeneral.setBackground(new java.awt.Color(0, 102, 204));

        btnGeneral.setFont(new java.awt.Font("Lexend", 1, 12)); // NOI18N
        btnGeneral.setForeground(new java.awt.Color(255, 255, 255));
        btnGeneral.setText("General");
        btnGeneral.setBorder(null);
        btnGeneral.setBorderPainted(false);
        btnGeneral.setContentAreaFilled(false);
        btnGeneral.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnGeneral.setFocusPainted(false);
        btnGeneral.setPreferredSize(new java.awt.Dimension(92, 25));
        btnGeneral.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnGeneralMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnGeneralMouseExited(evt);
            }
        });
        btnGeneral.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGeneralActionPerformed(evt);
            }
        });
        panelGeneral.add(btnGeneral);

        jPanel3.add(panelGeneral);

        panelSalida.setBackground(new java.awt.Color(255, 255, 255));

        btnSalida.setFont(new java.awt.Font("Lexend", 1, 12)); // NOI18N
        btnSalida.setForeground(new java.awt.Color(0, 102, 204));
        btnSalida.setText("Salida");
        btnSalida.setBorder(null);
        btnSalida.setBorderPainted(false);
        btnSalida.setContentAreaFilled(false);
        btnSalida.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSalida.setFocusPainted(false);
        btnSalida.setPreferredSize(new java.awt.Dimension(50, 25));
        btnSalida.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnSalidaMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnSalidaMouseExited(evt);
            }
        });
        btnSalida.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalidaActionPerformed(evt);
            }
        });
        panelSalida.add(btnSalida);

        jPanel3.add(panelSalida);

        panelEntrada.setBackground(new java.awt.Color(255, 255, 255));

        btnEntrada.setFont(new java.awt.Font("Lexend", 1, 12)); // NOI18N
        btnEntrada.setForeground(new java.awt.Color(0, 102, 204));
        btnEntrada.setText("Entrada");
        btnEntrada.setBorder(null);
        btnEntrada.setBorderPainted(false);
        btnEntrada.setContentAreaFilled(false);
        btnEntrada.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEntrada.setFocusPainted(false);
        btnEntrada.setPreferredSize(new java.awt.Dimension(60, 25));
        btnEntrada.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnEntradaMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnEntradaMouseExited(evt);
            }
        });
        btnEntrada.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEntradaActionPerformed(evt);
            }
        });
        panelEntrada.add(btnEntrada);

        jPanel3.add(panelEntrada);

        panelActualizar.setBackground(new java.awt.Color(255, 255, 255));

        btnActualizar.setFont(new java.awt.Font("Lexend", 1, 12)); // NOI18N
        btnActualizar.setForeground(new java.awt.Color(0, 102, 204));
        btnActualizar.setText("Actualizacion");
        btnActualizar.setBorder(null);
        btnActualizar.setBorderPainted(false);
        btnActualizar.setContentAreaFilled(false);
        btnActualizar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnActualizar.setFocusPainted(false);
        btnActualizar.setPreferredSize(new java.awt.Dimension(92, 25));
        btnActualizar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnActualizarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnActualizarMouseExited(evt);
            }
        });
        btnActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarActionPerformed(evt);
            }
        });
        panelActualizar.add(btnActualizar);

        jPanel3.add(panelActualizar);

        jPanel2.add(jPanel3, java.awt.BorderLayout.PAGE_START);

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setLayout(new java.awt.BorderLayout(10, 10));

        panelCard.setBackground(new java.awt.Color(255, 255, 255));
        panelCard.setPreferredSize(new java.awt.Dimension(1173, 150));
        panelCard.setLayout(new java.awt.CardLayout());

        Salida.setBackground(new java.awt.Color(255, 255, 255));
        Salida.setLayout(new java.awt.GridLayout(1, 0));

        jPanel10.setBackground(new java.awt.Color(255, 255, 255));
        jPanel10.setLayout(new java.awt.GridLayout(4, 0));

        jLabel1.setFont(new java.awt.Font("Lexend", 1, 12)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setText("Almacenista:  ");
        jPanel10.add(jLabel1);

        txtAlmacenista.setEditable(false);
        txtAlmacenista.setBackground(new java.awt.Color(255, 255, 255));
        txtAlmacenista.setFont(new java.awt.Font("Lexend", 1, 12)); // NOI18N
        txtAlmacenista.setForeground(new java.awt.Color(51, 51, 51));
        txtAlmacenista.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtAlmacenista.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(204, 204, 204)));
        jPanel10.add(txtAlmacenista);

        jLabel2.setFont(new java.awt.Font("Lexend", 1, 12)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText("Fecha:  ");
        jPanel10.add(jLabel2);

        txtFecha.setEditable(false);
        txtFecha.setBackground(new java.awt.Color(255, 255, 255));
        txtFecha.setFont(new java.awt.Font("Lexend", 1, 12)); // NOI18N
        txtFecha.setForeground(new java.awt.Color(51, 51, 51));
        txtFecha.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtFecha.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(204, 204, 204)));
        jPanel10.add(txtFecha);

        jLabel3.setFont(new java.awt.Font("Lexend", 1, 12)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText("Requisitor:  ");
        jPanel10.add(jLabel3);

        txtRequisitor.setBackground(new java.awt.Color(255, 255, 255));
        txtRequisitor.setFont(new java.awt.Font("Lexend", 0, 12)); // NOI18N
        txtRequisitor.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtRequisitor.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(204, 204, 204)));
        txtRequisitor.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtRequisitorFocusGained(evt);
            }
        });
        txtRequisitor.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtRequisitorMouseClicked(evt);
            }
        });
        txtRequisitor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtRequisitorActionPerformed(evt);
            }
        });
        jPanel10.add(txtRequisitor);
        jPanel10.add(jLabel15);
        jPanel10.add(jLabel16);

        Salida.add(jPanel10);

        jPanel11.setBackground(new java.awt.Color(255, 255, 255));
        jPanel11.setLayout(new java.awt.GridLayout(4, 0));

        jLabel6.setFont(new java.awt.Font("Lexend", 1, 12)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel6.setText("Proyecto:  ");
        jPanel11.add(jLabel6);

        txtProyecto.setBackground(new java.awt.Color(255, 255, 255));
        txtProyecto.setFont(new java.awt.Font("Lexend", 0, 12)); // NOI18N
        txtProyecto.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtProyecto.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(204, 204, 204)));
        txtProyecto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtProyectoMouseClicked(evt);
            }
        });
        txtProyecto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtProyectoActionPerformed(evt);
            }
        });
        jPanel11.add(txtProyecto);

        jLabel7.setFont(new java.awt.Font("Lexend", 1, 12)); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel7.setText("Codigo:  ");
        jPanel11.add(jLabel7);

        txtCodigo.setBackground(new java.awt.Color(255, 255, 255));
        txtCodigo.setFont(new java.awt.Font("Lexend", 0, 12)); // NOI18N
        txtCodigo.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtCodigo.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(204, 204, 204)));
        txtCodigo.setNextFocusableComponent(txtRequisitor);
        txtCodigo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodigoActionPerformed(evt);
            }
        });
        jPanel11.add(txtCodigo);

        jLabel18.setFont(new java.awt.Font("Lexend", 1, 12)); // NOI18N
        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel18.setText("Descripcion:  ");
        jPanel11.add(jLabel18);

        txtDesc.setBackground(new java.awt.Color(255, 255, 255));
        txtDesc.setFont(new java.awt.Font("Lexend", 0, 12)); // NOI18N
        txtDesc.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtDesc.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(204, 204, 204)));
        txtDesc.setNextFocusableComponent(txtRequisitor);
        txtDesc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDescActionPerformed(evt);
            }
        });
        jPanel11.add(txtDesc);
        jPanel11.add(jLabel11);
        jPanel11.add(jLabel14);

        Salida.add(jPanel11);

        panelCard.add(Salida, "card2");

        Entrada.setBackground(new java.awt.Color(255, 255, 255));
        Entrada.setLayout(new java.awt.BorderLayout());

        jPanel12.setBackground(new java.awt.Color(255, 255, 255));
        jPanel12.setLayout(new java.awt.BorderLayout());

        jLabel17.setFont(new java.awt.Font("Lexend", 1, 14)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(0, 165, 252));
        jLabel17.setText("Entrada nuevo material");
        jPanel12.add(jLabel17, java.awt.BorderLayout.NORTH);

        scrollEntrada.setBackground(new java.awt.Color(255, 255, 255));
        scrollEntrada.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(255, 255, 255)));

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));
        jPanel8.setLayout(new java.awt.GridBagLayout());

        jPanel14.setBackground(new java.awt.Color(255, 255, 255));

        jPanel13.setBackground(new java.awt.Color(255, 255, 255));
        jPanel13.setLayout(new java.awt.BorderLayout(20, 10));

        jLabel4.setFont(new java.awt.Font("Lexend", 1, 12)); // NOI18N
        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/circulo rojo.png"))); // NOI18N
        jLabel4.setText("Numero de parte");
        jPanel13.add(jLabel4, java.awt.BorderLayout.PAGE_START);

        txtParte.setBackground(new java.awt.Color(255, 255, 255));
        txtParte.setFont(new java.awt.Font("Lexend", 0, 12)); // NOI18N
        txtParte.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(187, 187, 187)));
        txtParte.setPreferredSize(new java.awt.Dimension(300, 22));
        jPanel13.add(txtParte, java.awt.BorderLayout.CENTER);

        jPanel14.add(jPanel13);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        jPanel8.add(jPanel14, gridBagConstraints);

        jPanel15.setBackground(new java.awt.Color(255, 255, 255));
        jPanel15.setLayout(new java.awt.BorderLayout(0, 10));

        jLabel5.setFont(new java.awt.Font("Lexend", 1, 12)); // NOI18N
        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/circulo rojo.png"))); // NOI18N
        jLabel5.setText("Descripcion:");
        jLabel5.setPreferredSize(new java.awt.Dimension(400, 15));
        jPanel15.add(jLabel5, java.awt.BorderLayout.PAGE_START);

        jScrollPane3.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 2, true));

        txtDescripcion.setBackground(new java.awt.Color(255, 255, 255));
        txtDescripcion.setColumns(20);
        txtDescripcion.setFont(new java.awt.Font("Lexend", 0, 12)); // NOI18N
        txtDescripcion.setLineWrap(true);
        txtDescripcion.setRows(5);
        txtDescripcion.setWrapStyleWord(true);
        txtDescripcion.setBorder(null);
        jScrollPane3.setViewportView(txtDescripcion);

        jPanel15.add(jScrollPane3, java.awt.BorderLayout.CENTER);

        jPanel8.add(jPanel15, new java.awt.GridBagConstraints());

        jPanel16.setBackground(new java.awt.Color(255, 255, 255));

        jPanel17.setBackground(new java.awt.Color(255, 255, 255));
        jPanel17.setLayout(new java.awt.BorderLayout(20, 10));

        jLabel9.setFont(new java.awt.Font("Lexend", 1, 12)); // NOI18N
        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/circulo rojo.png"))); // NOI18N
        jLabel9.setText("Cantidad");
        jPanel17.add(jLabel9, java.awt.BorderLayout.PAGE_START);

        txtCantidad.setBackground(new java.awt.Color(255, 255, 255));
        txtCantidad.setFont(new java.awt.Font("Lexend", 0, 12)); // NOI18N
        txtCantidad.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(187, 187, 187)));
        txtCantidad.setPreferredSize(new java.awt.Dimension(150, 22));
        jPanel17.add(txtCantidad, java.awt.BorderLayout.CENTER);

        jPanel16.add(jPanel17);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 13;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        jPanel8.add(jPanel16, gridBagConstraints);

        jPanel18.setBackground(new java.awt.Color(255, 255, 255));

        jPanel19.setBackground(new java.awt.Color(255, 255, 255));
        jPanel19.setLayout(new java.awt.BorderLayout(20, 10));

        jLabel10.setFont(new java.awt.Font("Lexend", 1, 12)); // NOI18N
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/circulo rojo.png"))); // NOI18N
        jLabel10.setText("Ubicacion");
        jPanel19.add(jLabel10, java.awt.BorderLayout.PAGE_START);

        txtUbicacion.setBackground(new java.awt.Color(255, 255, 255));
        txtUbicacion.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(187, 187, 187)));
        try {
            txtUbicacion.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("U-#-#")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txtUbicacion.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtUbicacion.setFont(new java.awt.Font("Lexend", 0, 12)); // NOI18N
        txtUbicacion.setPreferredSize(new java.awt.Dimension(150, 22));
        jPanel19.add(txtUbicacion, java.awt.BorderLayout.CENTER);

        jPanel18.add(jPanel19);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 14;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        jPanel8.add(jPanel18, gridBagConstraints);

        jPanel22.setBackground(new java.awt.Color(255, 255, 255));

        panelGuardar.setBackground(new java.awt.Color(255, 255, 255));

        btnGuardar.setBackground(new java.awt.Color(255, 255, 255));
        btnGuardar.setFont(new java.awt.Font("Lexend", 1, 14)); // NOI18N
        btnGuardar.setForeground(new java.awt.Color(0, 102, 204));
        btnGuardar.setText("Guardar");
        btnGuardar.setBorder(null);
        btnGuardar.setBorderPainted(false);
        btnGuardar.setContentAreaFilled(false);
        btnGuardar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnGuardar.setFocusPainted(false);
        btnGuardar.setPreferredSize(new java.awt.Dimension(100, 22));
        btnGuardar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnGuardarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnGuardarMouseExited(evt);
            }
        });
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });
        panelGuardar.add(btnGuardar);

        jPanel22.add(panelGuardar);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 16;
        jPanel8.add(jPanel22, gridBagConstraints);

        scrollEntrada.setViewportView(jPanel8);

        jPanel12.add(scrollEntrada, java.awt.BorderLayout.CENTER);

        Entrada.add(jPanel12, java.awt.BorderLayout.CENTER);

        panelCard.add(Entrada, "card3");

        jPanel7.add(panelCard, java.awt.BorderLayout.PAGE_START);

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));
        jPanel9.setLayout(new java.awt.BorderLayout());

        Tabla1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "CODIGO", "DESCRIPCION", "CANTIDAD", "PROVEEDOR", "UBICACION", "Salida", "Entrada", "Act."
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, true, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(Tabla1);
        if (Tabla1.getColumnModel().getColumnCount() > 0) {
            Tabla1.getColumnModel().getColumn(5).setMinWidth(100);
            Tabla1.getColumnModel().getColumn(5).setPreferredWidth(100);
            Tabla1.getColumnModel().getColumn(5).setMaxWidth(100);
            Tabla1.getColumnModel().getColumn(6).setMinWidth(100);
            Tabla1.getColumnModel().getColumn(6).setPreferredWidth(100);
            Tabla1.getColumnModel().getColumn(6).setMaxWidth(100);
            Tabla1.getColumnModel().getColumn(7).setMinWidth(100);
            Tabla1.getColumnModel().getColumn(7).setPreferredWidth(100);
            Tabla1.getColumnModel().getColumn(7).setMaxWidth(100);
        }

        jPanel9.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jPanel7.add(jPanel9, java.awt.BorderLayout.CENTER);

        jPanel2.add(jPanel7, java.awt.BorderLayout.CENTER);

        jPanel1.add(jPanel2, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        jMenu1.setText("File");

        jMenuItem1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/max.png"))); // NOI18N
        jMenuItem1.setText("Maximos y minimos");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void lblSalirMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSalirMouseClicked
        dispose();
    }//GEN-LAST:event_lblSalirMouseClicked

    private void lblSalirMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSalirMouseEntered
        panelSalir.setBackground(Color.red);
        lblSalir.setForeground(Color.white);
    }//GEN-LAST:event_lblSalirMouseEntered

    private void lblSalirMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSalirMouseExited
        panelSalir.setBackground(Color.white);
        lblSalir.setForeground(Color.black);
    }//GEN-LAST:event_lblSalirMouseExited

    private void btnSalidaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSalidaMouseEntered
        panelSalida.setBackground(new Color(0,102,204));
        btnSalida.setForeground(Color.white);
    }//GEN-LAST:event_btnSalidaMouseEntered

    private void btnSalidaMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSalidaMouseExited
        if(seleccionado != 1){
            panelSalida.setBackground(Color.white);
            btnSalida.setForeground(new Color(0,102,204));
        }
    }//GEN-LAST:event_btnSalidaMouseExited

    private void btnEntradaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEntradaMouseEntered
        panelEntrada.setBackground(new Color(0,102,204));
        btnEntrada.setForeground(Color.white);
    }//GEN-LAST:event_btnEntradaMouseEntered

    private void btnEntradaMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEntradaMouseExited
        if(seleccionado != 2){
            panelEntrada.setBackground(Color.white);
            btnEntrada.setForeground(new Color(0,102,204));
        }
    }//GEN-LAST:event_btnEntradaMouseExited

    private void btnActualizarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnActualizarMouseEntered
        panelActualizar.setBackground(new Color(0,102,204));
        btnActualizar.setForeground(Color.white);
    }//GEN-LAST:event_btnActualizarMouseEntered

    private void btnActualizarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnActualizarMouseExited
        if(seleccionado != 3){
            panelActualizar.setBackground(Color.white);
            btnActualizar.setForeground(new Color(0,102,204));
        }
    }//GEN-LAST:event_btnActualizarMouseExited

    private void btnSalidaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalidaActionPerformed
        limpiarBotones();
        card.show(panelCard, "panelEntrada");
        panelSalida.setBackground(new Color(0,102,204));
        btnSalida.setForeground(Color.white);
        seleccionado = 1;
        limpiarTablaSalida();
        verDatosSalida("select * from pedidos order by id desc");
    }//GEN-LAST:event_btnSalidaActionPerformed

    private void btnEntradaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEntradaActionPerformed
        limpiarBotones();
        card.show(panelCard, "panelEntrada");
        panelEntrada.setBackground(new Color(0,102,204));
        btnEntrada.setForeground(Color.white);
        seleccionado = 2;
        limpiarTablaEntrada();
        verDatosEntrada("select * from entrada order by idEntrada desc");
    }//GEN-LAST:event_btnEntradaActionPerformed

    private void btnActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarActionPerformed
        limpiarBotones();
        card.show(panelCard, "panelEntrada");
        panelActualizar.setBackground(new Color(0,102,204));
        btnActualizar.setForeground(Color.white);
        seleccionado = 3;
        limpiarTablaActualizar();
        verDatosActualizacion("select * from actualizar order by idActualizar desc");
    }//GEN-LAST:event_btnActualizarActionPerformed

    private void btnGeneralMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGeneralMouseEntered
        panelGeneral.setBackground(new Color(0,102,204));
        btnGeneral.setForeground(Color.white);
    }//GEN-LAST:event_btnGeneralMouseEntered

    private void btnGeneralMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGeneralMouseExited
        if(seleccionado != 0){
            panelGeneral.setBackground(Color.white);
            btnGeneral.setForeground(new Color(0,102,204));
        }
    }//GEN-LAST:event_btnGeneralMouseExited

    private void btnGeneralActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGeneralActionPerformed
        limpiarBotones();
        panelGeneral.setBackground(new Color(0,102,204));
        btnGeneral.setForeground(Color.white);
        seleccionado = 0;
        limpiarTabla();
        card.show(panelCard, "panelSalida");
        agregarInventario("select NumeroDeParte, Cantidad, Proveedor, Ubicacion,Descripcion from inventario order by NumeroDeParte asc");
    }//GEN-LAST:event_btnGeneralActionPerformed

    private void txtCodigoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodigoActionPerformed
        limpiarTabla();
        agregarInventario("select NumeroDeParte, Cantidad, Proveedor, Ubicacion,Descripcion from inventario where NumeroDeParte like '" + txtCodigo.getText() + "' order by NumeroDeParte asc");
        if(Tabla1.getRowCount() == 0){
            DefaultTableModel miModelo = (DefaultTableModel) Tabla1.getModel();
            String datos[] = new String[10];
            datos[0] = txtCodigo.getText();
            datos[1] = "Nuevo";
            datos[2] = "Nuevo";
            datos[3] = "Nuevo";
            datos[4] = "Nuevo";
            miModelo.addRow(datos);
        }
    }//GEN-LAST:event_txtCodigoActionPerformed

    private void txtRequisitorFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtRequisitorFocusGained
        
    }//GEN-LAST:event_txtRequisitorFocusGained

    private void txtRequisitorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtRequisitorActionPerformed
        try{
            Connection con;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            String slq = "select * from registroempleados where NumEmpleado like '"+txtRequisitor.getText()+"'";
            ResultSet rs = st.executeQuery(slq);
            String empleado = null;
            while(rs.next()){
                empleado = rs.getString("Nombre") + " " + rs.getString("Apellido");
            }
            if(empleado != null){
                txtRequisitor.setText(empleado);
                txtRequisitor.setEnabled(false);
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "ERROR: "+e,"ERROR",JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_txtRequisitorActionPerformed

    private void txtRequisitorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtRequisitorMouseClicked
        txtRequisitor.setText("");
        txtRequisitor.setEnabled(true);
    }//GEN-LAST:event_txtRequisitorMouseClicked

    private void txtProyectoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtProyectoActionPerformed
        boolean band = false;
        for (int i = 0; i < proyectos.size(); i++) {
            if(proyectos.get(i).equals(txtProyecto.getText())){
                band = true;
            }
        }
        if(band){
            txtProyecto.setEnabled(false);
        }else{
            JOptionPane.showMessageDialog(this, "Este proyecto no existe","Error",JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_txtProyectoActionPerformed

    private void txtProyectoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtProyectoMouseClicked
        txtProyecto.setEnabled(true);
        txtProyecto.setText("");
    }//GEN-LAST:event_txtProyectoMouseClicked

    private void btnGuardarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGuardarMouseEntered
        panelGuardar.setBackground(new Color(0,102,204));
        btnGuardar.setForeground(Color.white);
    }//GEN-LAST:event_btnGuardarMouseEntered

    private void btnGuardarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGuardarMouseExited
        panelGuardar.setBackground(Color.white);
        btnGuardar.setForeground(new Color(0,102,204));
    }//GEN-LAST:event_btnGuardarMouseExited

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        try{
            Connection con;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            String sql = "select NumeroDeParte from inventario where NumeroDeParte like '"+txtParte.getText()+"'";
            ResultSet rs = st.executeQuery(sql);
            String part = null;
            while(rs.next()){
                part = rs.getString("NumeroDeParte");
            }
            if(part != null){
                String sql1 = "update inventario set Descripcion = ?, Cantidad = ?, Ubicacion = ? where NumeroDeParte = ?";
                PreparedStatement pst = con.prepareStatement(sql1);

                pst.setString(1, txtDescripcion.getText());
                pst.setString(2, txtCantidad.getText());
                pst.setString(3, txtUbicacion.getText());
                pst.setString(4, txtParte.getText());

                int n = pst.executeUpdate();
                if(n < 1){
                    JOptionPane.showMessageDialog(this,"No se Actualizo: "+txtParte.getText());
                }else{
                    pedidos();
                    txtParte.setText("");
                    txtDescripcion.setText("");
                    txtCantidad.setText("");
                    txtUbicacion.setText("");
                }
            }else{
                String sql1 = "insert into inventario (Descripcion, Cantidad, Ubicacion, NumeroDeParte) values(?,?,?,?)";
                PreparedStatement pst = con.prepareStatement(sql1);

                pst.setString(1, txtDescripcion.getText());
                pst.setString(2, txtCantidad.getText());
                pst.setString(3, txtUbicacion.getText());
                pst.setString(4, txtParte.getText());

                int n = pst.executeUpdate();
                if(n < 1){
                    JOptionPane.showMessageDialog(this,"No se guardo: "+txtParte.getText());
                }else{
                    pedidos();
                    txtParte.setText("");
                    txtDescripcion.setText("");
                    txtCantidad.setText("");
                    txtUbicacion.setText("");
                }
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "ERROR: "+e,"ERROR",JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void txtDescActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDescActionPerformed
        limpiarTabla();
        agregarInventario("select NumeroDeParte, Cantidad, Proveedor, Ubicacion,Descripcion from inventario where Descripcion like '%" + txtDesc.getText() + "%' order by NumeroDeParte asc");
        if(Tabla1.getRowCount() == 0){
            DefaultTableModel miModelo = (DefaultTableModel) Tabla1.getModel();
            String datos[] = new String[10];
            datos[0] = txtCodigo.getText();
            datos[1] = "Nuevo";
            datos[2] = "Nuevo";
            datos[3] = "Nuevo";
            datos[4] = "Nuevo";
            miModelo.addRow(datos);
        }
    }//GEN-LAST:event_txtDescActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        JFrame j = (JFrame) JOptionPane.getFrameForComponent(this);
        MaximosYMinimos m = new MaximosYMinimos(j,true);
        m.setVisible(true);
    }//GEN-LAST:event_jMenuItem1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Entrada;
    private javax.swing.JPanel Salida;
    public javax.swing.JTable Tabla1;
    private javax.swing.JButton btnActualizar;
    private javax.swing.JButton btnEntrada;
    private javax.swing.JButton btnGeneral;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnSalida;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
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
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
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
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lblSalir;
    private javax.swing.JPanel pan;
    private javax.swing.JPanel panelActualizar;
    private javax.swing.JPanel panelCard;
    private javax.swing.JPanel panelEntrada;
    private javax.swing.JPanel panelGeneral;
    private javax.swing.JPanel panelGuardar;
    private javax.swing.JPanel panelSalida;
    private javax.swing.JPanel panelSalir;
    private javax.swing.JScrollPane scrollEntrada;
    public javax.swing.JTextField txtAlmacenista;
    private javax.swing.JTextField txtCantidad;
    public javax.swing.JTextField txtCodigo;
    public javax.swing.JTextField txtDesc;
    private javax.swing.JTextArea txtDescripcion;
    public javax.swing.JTextField txtFecha;
    private javax.swing.JTextField txtParte;
    public javax.swing.JTextField txtProyecto;
    public javax.swing.JTextField txtRequisitor;
    private javax.swing.JFormattedTextField txtUbicacion;
    // End of variables declaration//GEN-END:variables

    @Override
    public void actionPerformed(ActionEvent e) {
        txtRequisitor.setEnabled(true);
        txtRequisitor.setText("");
        SwingUtilities.invokeLater(() -> txtRequisitor.requestFocusInWindow());
        
        txtProyecto.setEnabled(true);
        txtProyecto.setText("");
        
        txtCodigo.setText("");
        
        limpiarTabla();
        agregarInventario("select NumeroDeParte, Cantidad, Proveedor, Ubicacion,Descripcion from inventario order by NumeroDeParte asc");
    }
}
class renderer extends JLabel implements TableCellRenderer {

    boolean isBordered = true;
    int seleccionado;

    public renderer(boolean isBordered, int sel) {
        this.isBordered = isBordered;
        setOpaque(true);
        seleccionado = sel;
    }

    public Component getTableCellRendererComponent(JTable table, Object color, boolean isSelected, boolean hasFocus, int row, int column) {
            JButton boton = new JButton();
            boton.setBackground(new java.awt.Color(255, 255, 255));
        switch (seleccionado) {
            case 1:
                boton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/salida_16.png"))); // NOI18N
                break;
            case 2:
                boton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/entrada_16.png"))); // NOI18N
                break;
            case 3:
                boton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/act_16.png"))); // NOI18N
                break;
            default:
                break;
        }
            boton.setBorder(null);
            boton.setBorderPainted(false);
            boton.setContentAreaFilled(false);
            boton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
            boton.setFocusPainted(false);
            return boton;
    }
}

class myeditor extends AbstractCellEditor implements TableCellEditor, ActionListener {

    Boolean currentValue;
    JButton button;
    InventarioNew cxp;
    protected static final String EDIT = "edit";
    private JTable tabla1;
    int seleccionado;

    public myeditor(JTable jTable1, InventarioNew cxp, int sel) {
        button = new JButton();
        button.setActionCommand(EDIT);
        button.addActionListener(this);
        button.setBorderPainted(false);
        this.tabla1 = jTable1;
        this.cxp = cxp;
        seleccionado = sel;
    }

    public void actionPerformed(ActionEvent e) {
        JFrame f = (JFrame) JOptionPane.getFrameForComponent(cxp);
        infoInventario info = new infoInventario(f,true,cxp.proyectos);
        info.setLocationRelativeTo(cxp);
        switch (seleccionado) {
            case 1:
                info.lblTitulo.setText("Salida");
                break;
            case 2:
                info.lblTitulo.setText("Entrada");
                info.lblCantidad.setText("Cantidad a ingresar:");
                break;
            case 3:
                info.lblTitulo.setText("Actualizar");
                info.lblCantidad.setText("Cantidad:");
                break;
            default:
                break;
        }
        info.txtAlmacenista.setText(cxp.txtAlmacenista.getText());
        info.txtEmpleado.setText(cxp.txtRequisitor.getText());
        int row = cxp.Tabla1.getSelectedRow();
        info.txtCodigo.setText(cxp.Tabla1.getValueAt(row, 0).toString());
        info.txtDescripcion.setText(cxp.Tabla1.getValueAt(row, 1).toString());
        info.txtCantStock.setText(cxp.Tabla1.getValueAt(row, 2).toString());
        info.txtProyecto.setText(cxp.txtProyecto.getText());
        info.btnGuardar.addActionListener(cxp);
        info.addPrecio(cxp.Tabla1.getValueAt(row, 0).toString());
        if(cxp.txtProyecto.getText().equals("")){
            info.txtProyecto.setEnabled(true);
            info.txtEmpleado.setEnabled(true);
        }
        info.setVisible(true);
        fireEditingStopped();
    }

    //Implement the one CellEditor method that AbstractCellEditor doesn't.
    public Object getCellEditorValue() {
        return currentValue;
    }

    //Implement the one method defined by TableCellEditor.
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            currentValue = (Boolean) value;
            return button;
    }
}
