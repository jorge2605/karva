package pruebas;

import Conexiones.Conexion;
import Modelo.colorAprobacion;
import java.awt.Color;
import java.awt.Font;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Arrays;
import java.util.Stack;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public final class Aprobacion extends javax.swing.JInternalFrame {
    String id;
    int cont = 0;
    String estado;
    int inicio;
    public String numEmpleado;
    public Stack<String[]> proveedores;
    
    public void limpiarTabla(){
        Tabla1.setModel(new javax.swing.table.DefaultTableModel(
        new Object [][] {

        },
        new String [] {
            "NUMERO DE REQUISICION"
        }
    ) {
        boolean[] canEdit = new boolean [] {
            false
        };

        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return canEdit [columnIndex];
        }
    });

    Tabla1.getTableHeader().setFont(new Font("Roboto", Font.BOLD, 16));
    Tabla1.setFont(new java.awt.Font("Tahoma", 0, 14));
    Tabla1.getTableHeader().setOpaque(false);
    Tabla1.getTableHeader().setBackground(new Color(254,254,254));
    Tabla1.getTableHeader().setForeground(new Color(0, 78, 171));
    Tabla1.setRowHeight(25);
    Tabla1.setShowGrid(false);

    jScrollPane1.getViewport().setBackground(Color.white);
    jScrollPane1.setForeground(Color.white);
    jScrollPane1.getViewport().setForeground(Color.white);

    jScrollPane1.setViewportView(Tabla1);

    if (Tabla1.getColumnModel().getColumnCount() > 0) {
        Tabla1.getColumnModel().getColumn(0).setResizable(false);
    }
    }
    
    public void limpiarTabla2(){
        Tabla2.setModel(new javax.swing.table.DefaultTableModel(
        new Object [][] {

        },
        new String [] {
            "Descripcion", "Codigo", "Cantidad", "Precio", "Proveedor", "Moneda"
        }
    ) {
        boolean[] canEdit = new boolean [] {
            false, false, false, false,false,false
        };

        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return canEdit [columnIndex];
        }
    });

    Tabla2.getTableHeader().setFont(new Font("Roboto", Font.BOLD, 16));
    Tabla2.setFont(new java.awt.Font("Tahoma", 0, 14));
    Tabla2.getTableHeader().setOpaque(false);
    Tabla2.getTableHeader().setBackground(new Color(254,254,254));
    Tabla2.getTableHeader().setForeground(new Color(0, 78, 171));
    Tabla2.setRowHeight(25);
    Tabla2.setShowGrid(false);

    jScrollPane3.getViewport().setBackground(Color.white);
    jScrollPane3.setForeground(Color.white);
    jScrollPane3.getViewport().setForeground(Color.white);
        
    jScrollPane3.setViewportView(Tabla2);
    }
    
    public void limpiar(){
    txtNumero.setText("");
    txtNombre.setText("");
    txtFecha.setText("");
    txtComentarios.setText("");
    }
    
    public void verDatos(){
        DefaultTableModel miModelo = (DefaultTableModel) Tabla1.getModel();
    try{
        
    Connection con = null;
    Conexion con1 = new Conexion();
    con = con1.getConnection();
    Statement st = con.createStatement();
    String sql = "select * from Requisicion where Progreso like 'APROBACION'";
    String datos[] = new String[10];
    
    Statement st2 = con.createStatement();
    String sql2 = "select * from edicionpo where Estado like 'EDITADO'";
    ResultSet rs2 = st2.executeQuery(sql2);
    String datos2;
    
    Statement st3 = con.createStatement();
    String sql3 = "select * from edicionpo where Estado like 'EDITADO'";
    ResultSet rs3 = st3.executeQuery(sql3);
    String datos3[] = new String[10];
    cont = 0;
    while(rs2.next()){
        datos2 = rs2.getString("PO");
        cont++;
    }
    
    
    ResultSet rs = st.executeQuery(sql);
    while(rs.next()){
    datos[0] = rs.getString("Id");
    miModelo.addRow(datos);
    }
    
    if(cont > 0){
        String d[] = new String[10];
        d[0] = "ORDENES EDITADAS";
        miModelo.addRow(d);
        while(rs3.next()){
        datos3[0] = rs3.getString("PO");
        miModelo.addRow(datos3);
    }
    }
    
    }catch(SQLException e){
    JOptionPane.showMessageDialog(this, "ERROR AL MOSTRAR DATOS "+e,"ERROR",JOptionPane.ERROR_MESSAGE);
    }
    }
    
    public void calcularTotal(){
        double precio, total = 0;
        for (int i = 0; i < Tabla2.getRowCount(); i++) {
            
            
            if(Tabla2.getValueAt(i, 3) == null){
                precio = 0.0;
                Tabla2.setValueAt("0", i, 3);
            }else if (Tabla2.getValueAt(i, 3).toString().equals("")){
            precio = 0.0;
            Tabla2.setValueAt("0", i, 3);
            }else{
                precio = Double.parseDouble(Tabla2.getValueAt(i, 3).toString());
            }
            total = total + (precio * (Double.parseDouble(Tabla2.getValueAt(i, 2).toString())));
            
            DecimalFormatSymbols separador = new DecimalFormatSymbols();
            separador.setDecimalSeparator('.');
            DecimalFormat formato = new DecimalFormat("#,###.##",separador);
          
            txtTotal.setText((formato.format(total)));
            
        }
    }
    
    public void verProyecto(String proyecto){
        try{
            Connection con;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            String sql = "select Proyecto, Descripcion from proyectos where Proyecto like '" + proyecto + "'";
            ResultSet rs = st.executeQuery(sql);
            String descripcion = "";
            while(rs.next()){
                descripcion = rs.getString("Descripcion");
            }
            txtProyecto.setText(proyecto + ": " + descripcion);
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "ERROR: "+e,"ERROR",JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public final void descargarProveedores(){
        try{
            Connection con;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            String sql = "select Nombre, Moneda, Iva, Isr from registroprov_compras";
            ResultSet rs = st.executeQuery(sql);
            proveedores = new Stack<>();
            while(rs.next()){
                String datos[] = new String[4];
                datos[0] = rs.getString("Nombre");
                datos[1] = rs.getString("Moneda");
                datos[2] = rs.getString("Iva");
                datos[3] = rs.getString("Isr");
                proveedores.push(datos);
                cont++;
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "ERROR: "+e,"ERROR",JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public String[] getArray(String proveedor){
         for (int i = 0; i < proveedores.size(); i++) {
            String dat[] = proveedores.get(i);
            for (int j = 0; j < dat.length; j++) {
                if(dat[0].equals(proveedor)){
                    return(dat);
                }
            }
        }
        return null;
    }
    
    public String[] getCalculos(int inicio, int fin, String[] datosProveedor){
        String datos[] = new String[5];
        double subTotal = 0;
        for (int i = inicio; i <= fin; i++) {
            double precio;
            double cantidad;
            try{precio = Double.parseDouble(Tabla2.getValueAt(i, 3).toString());}catch(Exception e){precio  = 0;}
            try{cantidad = Double.parseDouble(Tabla2.getValueAt(i, 2).toString());}catch(Exception e){cantidad  = 0;}
            subTotal += (precio * cantidad);
        }
        double iva;
        double isr;
        double total;
        try{iva = Double.parseDouble(datosProveedor[2]);}catch(Exception e){iva  = 0;}
        try{isr = Double.parseDouble(datosProveedor[3]);}catch(Exception e){isr  = 0;}
        iva = (subTotal * iva) / 100;
        isr = (subTotal * isr) / 100;
        total = subTotal + iva - isr;
        DecimalFormat df = new DecimalFormat("#,###.##");
        datos[0] = String.valueOf(df.format(subTotal));
        datos[1] = String.valueOf(df.format(iva));
        datos[2] = String.valueOf(df.format(isr));
        datos[3] = String.valueOf(df.format(total));
        return datos;
    }
    
    public Aprobacion(String numEmpleado) {
        initComponents();
        ((javax.swing.plaf.basic.BasicInternalFrameUI) this.getUI()).setNorthPane(null);
        limpiarTabla();
        limpiarTabla2();
        descargarProveedores();
        estado = "requi";
        verDatos();
        revalidate();
        repaint();
        this.numEmpleado = numEmpleado;
    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel4 = new javax.swing.JPanel();
        titulo = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        panelX = new javax.swing.JPanel();
        lblX = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        Tabla1 = new javax.swing.JTable();
        jPanel8 = new javax.swing.JPanel();
        btnRequi = new rojeru_san.rsbutton.RSButtonRoundRipple();
        btnProyecto = new rojeru_san.rsbutton.RSButtonRoundRipple();
        jLabel9 = new javax.swing.JLabel();
        panelRound1 = new scrollPane.PanelRound();
        panelRound2 = new scrollPane.PanelRound();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        txtProyecto = new javax.swing.JTextArea();
        panelRound3 = new scrollPane.PanelRound();
        btnAceptar = new javax.swing.JButton();
        btnRechazar = new javax.swing.JButton();
        panelRound4 = new scrollPane.PanelRound();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtComentarios = new javax.swing.JTextArea();
        jPanel5 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        txtNumero = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtFecha = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        panelRound5 = new scrollPane.PanelRound();
        panelRound6 = new scrollPane.PanelRound();
        jLabel1 = new javax.swing.JLabel();
        txtTotal = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        Tabla2 = new colorAprobacion();
        jLabel10 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));
        setBorder(null);
        getContentPane().setLayout(new java.awt.BorderLayout(10, 10));

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setLayout(new java.awt.BorderLayout());

        titulo.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 40)); // NOI18N
        titulo.setForeground(new java.awt.Color(0, 165, 252));
        titulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        titulo.setText("APROBACIONES");
        titulo.setPreferredSize(new java.awt.Dimension(190, 29));
        titulo.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                tituloMouseDragged(evt);
            }
        });
        titulo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tituloMousePressed(evt);
            }
        });
        jPanel4.add(titulo, java.awt.BorderLayout.CENTER);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        panelX.setBackground(new java.awt.Color(255, 255, 255));

        lblX.setFont(new java.awt.Font("Roboto", 1, 16)); // NOI18N
        lblX.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblX.setText("  X  ");
        lblX.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblX.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblXMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblXMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblXMouseExited(evt);
            }
        });
        panelX.add(lblX);

        jPanel3.add(panelX);

        jPanel4.add(jPanel3, java.awt.BorderLayout.EAST);

        getContentPane().add(jPanel4, java.awt.BorderLayout.NORTH);

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setLayout(new javax.swing.BoxLayout(jPanel6, javax.swing.BoxLayout.LINE_AXIS));

        jLabel11.setText("    ");
        jPanel6.add(jLabel11);

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setLayout(new java.awt.BorderLayout());

        jScrollPane1.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(255, 255, 255)));

        Tabla1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "NUMERO DE REQUISICION"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        Tabla1.setRowHeight(25);
        Tabla1.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                Tabla1MouseDragged(evt);
            }
        });
        Tabla1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Tabla1MouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                Tabla1MousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(Tabla1);
        if (Tabla1.getColumnModel().getColumnCount() > 0) {
            Tabla1.getColumnModel().getColumn(0).setResizable(false);
        }

        jPanel7.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));
        jPanel8.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 15, 5));

        btnRequi.setBackground(new java.awt.Color(255, 102, 0));
        btnRequi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/Requi.png"))); // NOI18N
        btnRequi.setText("Requisiciones");
        btnRequi.setColorHover(new java.awt.Color(255, 139, 62));
        btnRequi.setFocusPainted(false);
        btnRequi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRequiActionPerformed(evt);
            }
        });
        jPanel8.add(btnRequi);

        btnProyecto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/Proyecto.png"))); // NOI18N
        btnProyecto.setText("Proyectos");
        btnProyecto.setFocusPainted(false);
        btnProyecto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProyectoActionPerformed(evt);
            }
        });
        jPanel8.add(btnProyecto);

        jPanel7.add(jPanel8, java.awt.BorderLayout.PAGE_START);

        jPanel6.add(jPanel7);

        jLabel9.setText("    ");
        jPanel6.add(jLabel9);

        panelRound1.setBackground(new java.awt.Color(255, 255, 255));
        panelRound1.setMaximumSize(new java.awt.Dimension(400, 2147483647));
        panelRound1.setMinimumSize(new java.awt.Dimension(400, 206));
        panelRound1.setPreferredSize(new java.awt.Dimension(400, 500));
        panelRound1.setRoundBottomLeft(100);
        panelRound1.setRoundBottomRight(100);
        panelRound1.setRoundTopLeft(100);
        panelRound1.setRoundTopRight(100);
        panelRound1.setLayout(new java.awt.BorderLayout());

        panelRound2.setBackground(new java.awt.Color(255, 255, 255));
        panelRound2.setMaximumSize(new java.awt.Dimension(400, 32767));
        panelRound2.setMinimumSize(new java.awt.Dimension(400, 39));
        panelRound2.setPreferredSize(new java.awt.Dimension(400, 100));
        panelRound2.setRoundTopLeft(50);
        panelRound2.setRoundTopRight(50);
        panelRound2.setLayout(new java.awt.BorderLayout());

        jLabel8.setBackground(new java.awt.Color(0, 0, 0));
        jLabel8.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 0, 0));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("PROYECTO:");
        panelRound2.add(jLabel8, java.awt.BorderLayout.NORTH);

        jScrollPane4.setBorder(null);

        txtProyecto.setEditable(false);
        txtProyecto.setBackground(new java.awt.Color(255, 255, 255));
        txtProyecto.setColumns(20);
        txtProyecto.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtProyecto.setForeground(new java.awt.Color(0, 0, 0));
        txtProyecto.setLineWrap(true);
        txtProyecto.setRows(5);
        txtProyecto.setWrapStyleWord(true);
        txtProyecto.setBorder(null);
        jScrollPane4.setViewportView(txtProyecto);

        panelRound2.add(jScrollPane4, java.awt.BorderLayout.CENTER);

        panelRound1.add(panelRound2, java.awt.BorderLayout.PAGE_START);

        panelRound3.setBackground(new java.awt.Color(255, 255, 255));
        panelRound3.setMaximumSize(new java.awt.Dimension(400, 32767));
        panelRound3.setMinimumSize(new java.awt.Dimension(400, 82));
        panelRound3.setPreferredSize(new java.awt.Dimension(400, 120));
        panelRound3.setRoundBottomLeft(100);
        panelRound3.setRoundBottomRight(100);
        panelRound3.setRoundTopLeft(100);
        panelRound3.setRoundTopRight(100);
        panelRound3.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 100, 5));

        btnAceptar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ImgAnimacion/aceptar_72.png"))); // NOI18N
        btnAceptar.setBorderPainted(false);
        btnAceptar.setContentAreaFilled(false);
        btnAceptar.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnAceptar.setFocusPainted(false);
        btnAceptar.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnAceptar.setMinimumSize(new java.awt.Dimension(72, 72));
        btnAceptar.setPreferredSize(new java.awt.Dimension(100, 120));
        btnAceptar.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/ImgAnimacion/aceptar_72.png"))); // NOI18N
        btnAceptar.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/ImgAnimacion/aceptar_96.png"))); // NOI18N
        btnAceptar.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        btnAceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAceptarActionPerformed(evt);
            }
        });
        panelRound3.add(btnAceptar);

        btnRechazar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ImgAnimacion/rechazo_72.png"))); // NOI18N
        btnRechazar.setBorderPainted(false);
        btnRechazar.setContentAreaFilled(false);
        btnRechazar.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnRechazar.setFocusPainted(false);
        btnRechazar.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnRechazar.setMinimumSize(new java.awt.Dimension(72, 72));
        btnRechazar.setPreferredSize(new java.awt.Dimension(100, 120));
        btnRechazar.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/ImgAnimacion/rechazo_72.png"))); // NOI18N
        btnRechazar.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/ImgAnimacion/rechazo_96.png"))); // NOI18N
        btnRechazar.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        btnRechazar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRechazarActionPerformed(evt);
            }
        });
        panelRound3.add(btnRechazar);

        panelRound1.add(panelRound3, java.awt.BorderLayout.PAGE_END);

        panelRound4.setBackground(new java.awt.Color(255, 255, 255));
        panelRound4.setMaximumSize(new java.awt.Dimension(400, 2147483647));
        panelRound4.setPreferredSize(new java.awt.Dimension(400, 420));
        panelRound4.setLayout(new java.awt.BorderLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new java.awt.BorderLayout());

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setPreferredSize(new java.awt.Dimension(78, 200));
        jPanel2.setLayout(new javax.swing.BoxLayout(jPanel2, javax.swing.BoxLayout.LINE_AXIS));

        jLabel5.setBackground(new java.awt.Color(0, 0, 0));
        jLabel5.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 0, 0));
        jLabel5.setText("COMENTARIOS:");
        jPanel2.add(jLabel5);

        jScrollPane2.setBorder(null);

        txtComentarios.setBackground(new java.awt.Color(255, 255, 255));
        txtComentarios.setColumns(20);
        txtComentarios.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtComentarios.setForeground(new java.awt.Color(0, 0, 0));
        txtComentarios.setLineWrap(true);
        txtComentarios.setRows(5);
        txtComentarios.setWrapStyleWord(true);
        txtComentarios.setBorder(null);
        jScrollPane2.setViewportView(txtComentarios);

        jPanel2.add(jScrollPane2);

        jPanel1.add(jPanel2, java.awt.BorderLayout.PAGE_END);

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setLayout(new java.awt.GridLayout(4, 2));

        jLabel6.setBackground(new java.awt.Color(0, 0, 0));
        jLabel6.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 0, 0));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("NO.REQUISICION:");
        jPanel5.add(jLabel6);

        txtNumero.setEditable(false);
        txtNumero.setBackground(new java.awt.Color(255, 255, 255));
        txtNumero.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        txtNumero.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtNumero.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        txtNumero.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNumeroActionPerformed(evt);
            }
        });
        jPanel5.add(txtNumero);

        jLabel3.setBackground(new java.awt.Color(0, 0, 0));
        jLabel3.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("REQUISITOR:");
        jPanel5.add(jLabel3);

        txtNombre.setEditable(false);
        txtNombre.setBackground(new java.awt.Color(255, 255, 255));
        txtNombre.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        txtNombre.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtNombre.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        jPanel5.add(txtNombre);

        jLabel4.setBackground(new java.awt.Color(0, 0, 0));
        jLabel4.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 0));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("FECHA DE REQUISICION:");
        jPanel5.add(jLabel4);

        txtFecha.setEditable(false);
        txtFecha.setBackground(new java.awt.Color(255, 255, 255));
        txtFecha.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        txtFecha.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtFecha.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        jPanel5.add(txtFecha);

        jPanel1.add(jPanel5, java.awt.BorderLayout.CENTER);

        panelRound4.add(jPanel1, java.awt.BorderLayout.CENTER);

        panelRound1.add(panelRound4, java.awt.BorderLayout.CENTER);

        jPanel6.add(panelRound1);

        jLabel7.setText("    ");
        jPanel6.add(jLabel7);

        panelRound5.setLayout(new java.awt.BorderLayout());

        panelRound6.setBackground(new java.awt.Color(255, 255, 255));
        panelRound6.setLayout(new java.awt.GridLayout(1, 0));

        jLabel1.setFont(new java.awt.Font("Roboto", 1, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setText("Precio total: ");
        panelRound6.add(jLabel1);

        txtTotal.setEditable(false);
        txtTotal.setBackground(new java.awt.Color(255, 255, 255));
        txtTotal.setFont(new java.awt.Font("Roboto", 1, 24)); // NOI18N
        txtTotal.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtTotal.setBorder(null);
        panelRound6.add(txtTotal);

        panelRound5.add(panelRound6, java.awt.BorderLayout.PAGE_END);

        jScrollPane3.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(255, 255, 255)));

        Tabla2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Descripcion", "Codigo", "Cantidad", "Precio", "Proveedor", "Moneda"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane3.setViewportView(Tabla2);
        if (Tabla2.getColumnModel().getColumnCount() > 0) {
            Tabla2.getColumnModel().getColumn(0).setPreferredWidth(250);
        }

        panelRound5.add(jScrollPane3, java.awt.BorderLayout.CENTER);

        jPanel6.add(panelRound5);

        jLabel10.setText("    ");
        jPanel6.add(jLabel10);

        getContentPane().add(jPanel6, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void Tabla1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Tabla1MouseClicked
        try{
            int fila = Tabla1.getSelectedRow();
            id = (String) Tabla1.getValueAt(fila, 0);
            limpiarTabla2();
            DefaultTableModel miModelo = (DefaultTableModel) Tabla2.getModel();
            Connection con = null;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            
            if(estado.equals("proyecto")){
                
                String sql = "select * from requisiciones where Id like '"+Tabla1.getValueAt(Tabla1.getSelectedRow(), 0).toString()+"' and Activo like true order by Proveedor asc";
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery(sql);
                String datos[] = new String[10];
                while(rs.next()){
                    datos[0] = rs.getString("Descripcion");
                    datos[1] = rs.getString("Codigo");
                    datos[2] = rs.getString("Cantidad");
                    datos[3] = rs.getString("Precio");
                    datos[4] = rs.getString("Proveedor");
                    datos[7] = rs.getString("NumRequisicion");
                    datos[8] = rs.getString("Requisitor");
                    datos[9] = rs.getString("Proyecto");
                    miModelo.addRow(datos);
                }
                txtNumero.setText(datos[7]);
                txtNombre.setText(datos[8]);
                verProyecto(datos[9]);
                calcularTotal();
            }else{
            int tab = Tabla1.getRowCount() - cont;
            if(cont > 0){
                tab = Tabla1.getRowCount() - cont -1;
            }
            if(Tabla1.getSelectedRow() > tab){ 
            //SELECCIONA ORDENES EDITADAS X
            Statement st = con.createStatement();
            String sql = "select * from requisiciones where OC like '"+Tabla1.getValueAt(Tabla1.getSelectedRow(), 0).toString()+"'";
            ResultSet rs = st.executeQuery(sql);
            
            Statement st2 = con.createStatement();
            String sql2 = "select * from edicionpo where PO like '"+Tabla1.getValueAt(Tabla1.getSelectedRow(), 0).toString()+"'";
            ResultSet rs2 = st2.executeQuery(sql2);
            
            String fecha = "";
            String datos[] = new String[10];
            
            while(rs2.next()){
                fecha = rs2.getString("FechaCreacion");
            }
            
            while(rs.next()){
               datos[0] = rs.getString("Descripcion");
               datos[1] = rs.getString("Codigo");
               datos[2] = rs.getString("Cantidad");
               datos[3] = rs.getString("Precio");
               datos[4] = rs.getString("Proveedor");
               datos[8] = rs.getString("Requisitor");
               datos[9] = rs.getString("Proyecto");
               miModelo.addRow(datos);
            }
                txtNumero.setText(id);
                txtNombre.setText(datos[8]);
                txtFecha.setText(fecha);
                txtProyecto.setText(datos[9]);
                verProyecto(datos[9]);
                calcularTotal();
            }else if(Tabla1.getSelectedRow() == tab){
                //TITULO ORDENES EDITADAS
                txtNumero.setText("");
                txtNombre.setText("");
                txtFecha.setText("");
                txtProyecto.setText("");
                txtTotal.setText("");
            }else{
            //SELECCIONA REQUISICIONES PARTE DE ARRIBA
            
                Statement st = con.createStatement();
                String sql = "select * from Requisiciones where NumRequisicion like '"+Tabla1.getValueAt(Tabla1.getSelectedRow(), 0).toString()+"' and Activo like true and OC is null  order by Proveedor asc";
                ResultSet rs = st.executeQuery(sql);
                String datos[] = new String[10];
                String prov = "";
                boolean band = true;
                int inicio = 0;
                int cont = 0;
                String arr[] = new String[5];
                while(rs.next()){
                    band = true;
                    datos[0] = rs.getString("Descripcion");
                    datos[1] = rs.getString("NumParte");
                    datos[2] = rs.getString("Cantidad");
                    datos[3] = rs.getString("Precio");
                    datos[4] = rs.getString("Proveedor");
                    datos[6] = rs.getString("Proyecto");
                    datos[8] = rs.getString("Requisitor");
                    if(datos[3] != null){
                        if(!datos[3].equals("")){
                            String array[] = getArray(datos[4]);
                            arr = array;
                            if(Tabla2.getRowCount() < 1){
                                prov = datos[4];
                                inicio = cont;
                            }else if(!prov.equals(datos[4])){
                                prov = datos[4];
                                String calculos[] = getCalculos(inicio, cont, array);
                                inicio = cont;
                                String da[] = new String[6];
                                da[0] = "Calculos:";
                                da[1] = "";
                                da[2] = "SUB: "+calculos[0];
                                da[3] = "IVA: "+calculos[1];
                                da[4] = "ISR:"+calculos[2];
                                da[5] = "TOT: "+calculos[3];
                                miModelo.addRow(da);
                                band = false;
                            }
                            try{
                                datos[5] = array[1];
                            }catch(Exception e){}

                            if(datos[0] != null){
                                miModelo.addRow(datos);
                                band = true;
                            }
                            verProyecto(datos[6]);
                            txtNumero.setText(id);
                            txtNombre.setText(datos[8]);
                            cont++;
                        }
                    }
                }
                if(band){
                    String calculos[] = getCalculos(inicio, cont, arr);
                    String da[] = new String[6];
                    da[0] = "Calculos:";
                    da[1] = "";
                    da[2] = "SUB: "+calculos[0];
                    da[3] = "IVA: "+calculos[1];
                    da[4] = "ISR:"+calculos[2];
                    da[5] = "TOT: "+calculos[3];
                    miModelo.addRow(da);
                }
//                calcularTotal();
            }
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "ERROR: "+e,"ERROR",JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_Tabla1MouseClicked

    private void btnAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAceptarActionPerformed
        if(txtNumero.getText().equals("")){
            JOptionPane.showMessageDialog(this, "DEBES SELECCIONAR POR LO MENOS UNA REQUISICION U ORDEN DE COMPRA");
        }else{
        try{
        Connection con = null;
        Conexion con1 = new Conexion();
        con = con1.getConnection();
        
        if(estado.equals("proyecto")){
            int s = 0;
            for (int i = 0; i < Tabla1.getSelectedRows().length; i++) {
                String sql = "update requisiciones set Aux = ?, Notas = ? where Id = ?";
                PreparedStatement pst = con.prepareStatement(sql);

                pst.setString(1, "APROBADO");
                pst.setString(2, txtComentarios.getText());
                pst.setString(3, Tabla1.getValueAt(i, 0).toString());

                s = pst.executeUpdate();
            }
            if(s > 0){
                limpiarTabla();
                verDatos();
                limpiar();
                limpiarTabla2();
                txtProyecto.setText("");
                txtTotal.setText("");
                JOptionPane.showMessageDialog(this,"DATOS GUARDADOS","INFO",JOptionPane.INFORMATION_MESSAGE);
            }
        }else{
            int tab = Tabla1.getRowCount() - cont;
            if(cont > 0){
                tab = Tabla1.getRowCount() - cont -1;
            }
            if(Tabla1.getSelectedRow() > tab){
                String sql = "update edicionpo set Estado = ?, Comentarios = ?, AprobadoPor = ? where PO = ?";
                PreparedStatement pst = con.prepareStatement(sql);

                pst.setString(1, "APROBADO");
                pst.setString(2, txtComentarios.getText());
                pst.setString(3, this.numEmpleado);
                pst.setString(4, id);

                int s = pst.executeUpdate();

                if(s > 0){
                limpiarTabla();
                verDatos();
                limpiar();
                limpiarTabla2();
                txtProyecto.setText("");
                txtTotal.setText("");
                JOptionPane.showMessageDialog(this,"DATOS GUARDADOS","INFO",JOptionPane.INFORMATION_MESSAGE);
                }
            }else if(Tabla1.getSelectedRow() == tab){
                
            }else{ 
                Statement st = con.createStatement();
                String sql = "update Requisicion set Progreso = ?, Comentarios = ?, AprobadoPor = ? where Id = ?";
                PreparedStatement pst = con.prepareStatement(sql);

                pst.setString(1, "APROBADO");
                pst.setString(2, txtComentarios.getText());
                pst.setString(3, this.numEmpleado);
                pst.setString(4, id);

                int s = pst.executeUpdate();

                if(s > 0){
                limpiarTabla();
                verDatos();
                limpiar();
                limpiarTabla2();
                txtProyecto.setText("");
                txtTotal.setText("");
                JOptionPane.showMessageDialog(this,"DATOS GUARDADOS","INFO",JOptionPane.INFORMATION_MESSAGE);
                }
            }  
        }
        }catch(SQLException e){
        JOptionPane.showMessageDialog(this, "ERROR AL GUARDAR DATOS"+e,"ERR0R",JOptionPane.ERROR_MESSAGE);
        }
        }
    }//GEN-LAST:event_btnAceptarActionPerformed

    private void btnRechazarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRechazarActionPerformed
        if(txtNumero.getText().equals("")){
            JOptionPane.showMessageDialog(this, "DEBES SELECCIONAR POR LO MENOS UNA REQUISICION U ORDEN DE COMPRA");
        }else{
        try{
        Connection con = null;
        Conexion con1 = new Conexion();
        con = con1.getConnection();
        Statement st = con.createStatement();
        
                int tab = Tabla1.getRowCount() - cont;
            if(cont > 0){
                tab = Tabla1.getRowCount() - cont -1;
            }
                if(Tabla1.getSelectedRow() > tab){
                String sql = "update edicionpo set Estado = ?, Comentarios = ? where PO = ?";
                PreparedStatement pst = con.prepareStatement(sql);

                pst.setString(1, "RECHAZADO");
                pst.setString(2, txtComentarios.getText());
                pst.setString(3, id);

                int s = pst.executeUpdate();

                if(s > 0){
                limpiarTabla();
                verDatos();
                limpiar();
                limpiarTabla2();
                txtProyecto.setText("");
                txtTotal.setText("");
                JOptionPane.showMessageDialog(this,"DATOS GUARDADOS","INFO",JOptionPane.INFORMATION_MESSAGE);
                }
            }else if(Tabla1.getSelectedRow() == tab){
                
            }else{ 
                String sql = "update Requisicion set Progreso = ?, Comentarios = ? where Id = ?";
                PreparedStatement pst = con.prepareStatement(sql);

                pst.setString(1, "RECHAZADO");
                pst.setString(2, txtComentarios.getText());
                pst.setString(3, id);

                int s = pst.executeUpdate();

                if(s > 0){
                limpiarTabla();
                verDatos();
                limpiar();
                limpiarTabla2();
                txtProyecto.setText("");
                txtTotal.setText("");
                JOptionPane.showMessageDialog(this,"DATOS GUARDADOS","INFO",JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }catch(SQLException e){
        JOptionPane.showMessageDialog(this, "ERROR AL GUARDAR DATOS"+e,"ERR0R",JOptionPane.ERROR_MESSAGE);
        }
        }
    }//GEN-LAST:event_btnRechazarActionPerformed

    private void tituloMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tituloMouseDragged

    }//GEN-LAST:event_tituloMouseDragged

    private void tituloMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tituloMousePressed

    }//GEN-LAST:event_tituloMousePressed

    private void lblXMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblXMouseClicked
        dispose();
    }//GEN-LAST:event_lblXMouseClicked

    private void lblXMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblXMouseEntered
        panelX.setBackground(Color.red);
        lblX.setForeground(Color.white);
    }//GEN-LAST:event_lblXMouseEntered

    private void lblXMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblXMouseExited
        panelX.setBackground(Color.white);
        lblX.setForeground(Color.black);
    }//GEN-LAST:event_lblXMouseExited

    private void txtNumeroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNumeroActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNumeroActionPerformed

    private void btnProyectoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProyectoActionPerformed
        estado = "proyecto";
        try{
            Connection con;
            limpiarTabla();
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            String sql = "select * from requisiciones where Aux like 'APROBACION'";
            ResultSet rs = st.executeQuery(sql);
            String datos[] = new String[10];
            DefaultTableModel miModelo = (DefaultTableModel) Tabla1.getModel();
            while(rs.next()){
                datos[0] = rs.getString("Id");
                miModelo.addRow(datos);
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "ERROR: "+e,"ERROR",JOptionPane.ERROR_MESSAGE );
        }
    }//GEN-LAST:event_btnProyectoActionPerformed

    private void btnRequiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRequiActionPerformed
        estado = "requi";
        limpiarTabla();
        verDatos();
    }//GEN-LAST:event_btnRequiActionPerformed

    private void Tabla1MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Tabla1MouseDragged
        if(estado.equals("proyecto")){
            try{
                limpiarTabla2();
                Connection con;
                Conexion con1 = new Conexion();
                con = con1.getConnection();
                DefaultTableModel miModelo = (DefaultTableModel) Tabla2.getModel();
                String requi = "", nombre = "", proyecto = "";
                for (int i = 0; i < Tabla1.getSelectedRows().length; i++) {
                    Statement st = con.createStatement();
                    String sql = "select * from requisiciones where Id like '"+Tabla1.getValueAt(Tabla1.getSelectedRows()[i], 0).toString()+"'";
                    ResultSet rs = st.executeQuery(sql);
                    String datos[] = new String[10];
                    while(rs.next()){
                        datos[0] = rs.getString("Descripcion");
                        datos[1] = rs.getString("Codigo");
                        datos[2] = rs.getString("Cantidad");
                        datos[3] = rs.getString("Precio");
                        datos[7] = rs.getString("NumRequisicion");
                        datos[8] = rs.getString("Requisitor");
                        datos[9] = rs.getString("Proyecto");
                        miModelo.addRow(datos);
                    }
                    requi = requi + ", " + datos[7];
                    nombre = nombre + ", " +  datos[8];
                    proyecto = proyecto + ", " +  datos[9];
                }
                    txtNumero.setText(requi);
                    txtNombre.setText(nombre);
                    txtProyecto.setText(proyecto);
                    calcularTotal();

            }catch(SQLException e){
                JOptionPane.showMessageDialog(this, "ERROR: "+e,"ERROR",JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_Tabla1MouseDragged

    private void Tabla1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Tabla1MousePressed
        inicio = Tabla1.getSelectedRow();
    }//GEN-LAST:event_Tabla1MousePressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable Tabla1;
    private javax.swing.JTable Tabla2;
    private javax.swing.JButton btnAceptar;
    private rojeru_san.rsbutton.RSButtonRoundRipple btnProyecto;
    private javax.swing.JButton btnRechazar;
    private rojeru_san.rsbutton.RSButtonRoundRipple btnRequi;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JLabel lblX;
    private scrollPane.PanelRound panelRound1;
    private scrollPane.PanelRound panelRound2;
    private scrollPane.PanelRound panelRound3;
    private scrollPane.PanelRound panelRound4;
    private scrollPane.PanelRound panelRound5;
    private scrollPane.PanelRound panelRound6;
    private javax.swing.JPanel panelX;
    public javax.swing.JLabel titulo;
    private javax.swing.JTextArea txtComentarios;
    private javax.swing.JTextField txtFecha;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtNumero;
    private javax.swing.JTextArea txtProyecto;
    private javax.swing.JTextField txtTotal;
    // End of variables declaration//GEN-END:variables
}
