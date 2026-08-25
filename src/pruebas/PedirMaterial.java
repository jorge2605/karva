package pruebas;

import Conexiones.Conexion;
import com.mxrck.autocompleter.TextAutoCompleter;
import java.awt.Color;
import java.awt.Font;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.ImageIcon;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class PedirMaterial extends javax.swing.JDialog {

    private TextAutoCompleter ac, ac1;
    private String numEmpleado;

    public void addEmpleado() {
        try {
            Connection con = null;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            String sql = "select * from registroempleados where NumEmpleado like '" + numEmpleado + "'";
            ResultSet rs = st.executeQuery(sql);
            String datos[] = new String[10];
            while (rs.next()) {
                datos[0] = rs.getString("NumEmpleado");
                datos[1] = rs.getString("Nombre");
                datos[2] = rs.getString("Apellido");
            }
            txtNumeroEmpleado.setText(datos[1] + " " + datos[2]);
            txtNumeroEmpleado.setEditable(false);
            txtNumeroEmpleado.setEnabled(false);
            txtNumeroEmpleado.setForeground(Color.blue);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "ERROR AL VER EMPLEADO " + e);
        }
    }

    public void autoCompletar() {
        ac = new TextAutoCompleter(txtCodigo);
        try {
            Connection con = null;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            String sql = "select NumeroDeParte from Inventario";
            String datos[] = new String[10];
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                datos[0] = rs.getString("NumeroDeParte");
                if (datos[0] != null) {
                    ac.addItem(datos[0]);
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "ERROR AL AUTOCOMPLETAR " + e);
        }
    }

    public void autoCompletarProyecto() {
        ac1 = new TextAutoCompleter(txtProyecto);
        try {
            Connection con = null;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            String sql = "select Proyecto from Proyectos";
            String datos[] = new String[10];
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                datos[0] = rs.getString("Proyecto");
                if (datos[0] != null) {
                    ac1.addItem(datos[0]);
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "ERROR AL AUTOCOMPLETAR " + e);
        }
    }

    public String extraer() {
        String datos[] = new String[10];
        try {
            Connection con = null;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            String sql = "select Id from requisicion";
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                datos[0] = rs.getString("Id");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "ERROR AL EXTRAER NUMERO");
        }
        return datos[0];
    }

    public void cambiarEstado() {
        try {
            Connection con;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            for (int i = 0; i < Tabla1.getRowCount(); i++) {
                String sql = "update datos set Estado = ? where Proyecto = ?";
                PreparedStatement pst = con.prepareStatement(sql);

                pst.setString(1, "SIN MATERIAL");
                pst.setString(2, Tabla1.getValueAt(i, 0).toString());

                pst.executeUpdate();

            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "ERROR: " + e, "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void limpiarTabla3() {
        jTable3.setBackground(new java.awt.Color(255, 255, 255));
        jTable3.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        jTable3.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{},
                new String[]{
                    "CANTIDAD", "DESCRICPION", "PROYECTO", "NO. PARTE", "U.M.", "PROVEEDOR"
                }
        ) {
            boolean[] canEdit = new boolean[]{
                false, false, false, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
        jScrollPane3.setViewportView(jTable3);
        if (jTable3.getColumnModel().getColumnCount() > 0) {
            jTable3.getColumnModel().getColumn(0).setResizable(false);
            jTable3.getColumnModel().getColumn(1).setResizable(false);
            jTable3.getColumnModel().getColumn(2).setResizable(false);
            jTable3.getColumnModel().getColumn(3).setResizable(false);
            jTable3.getColumnModel().getColumn(4).setResizable(false);
        }

    }

    public PedirMaterial(JInternalFrame jj, boolean men, String numEmpleado) {
        super.setTitle("PEDIR MATERIAL");
        initComponents();
        this.setIconImage(new ImageIcon(getClass().getResource("/Iconos/Towi 2.3.png")).getImage());

        Tabla1.getTableHeader().setFont(new Font("Roboto", Font.PLAIN, 14));
        Tabla1.getTableHeader().setForeground(Color.black);
        Tabla1.getTableHeader().setBackground(new Color(0, 0, 0, 0));

        jTable3.getTableHeader().setFont(new Font("Roboto", Font.PLAIN, 14));
        jTable3.getTableHeader().setForeground(Color.black);
        jTable3.getTableHeader().setBackground(new Color(0, 0, 0, 0));
        autoCompletar();
        autoCompletarProyecto();
        this.numEmpleado = numEmpleado;
        addEmpleado();
        lblRequi.setText(extraer());

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        Tabla1 = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        txtNumeroEmpleado = new javax.swing.JTextField();
        txtCodigo = new javax.swing.JTextField();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        btnGuardar = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        txtProyecto = new javax.swing.JTextField();
        jSeparator3 = new javax.swing.JSeparator();
        btnBorrar = new javax.swing.JButton();
        btnBorrarTabla = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        txtDescripcion = new javax.swing.JTextArea();
        jLabel5 = new javax.swing.JLabel();
        btnAdd = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        lblRequi = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtPlano = new rojeru_san.RSMTextFull();
        jLabel8 = new javax.swing.JLabel();
        txtUM = new javax.swing.JTextField();
        jSeparator4 = new javax.swing.JSeparator();
        txtCantidad = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jSeparator5 = new javax.swing.JSeparator();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setModal(true);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Tabla1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "NUMERO DE PLANO"
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
        jScrollPane2.setViewportView(Tabla1);
        if (Tabla1.getColumnModel().getColumnCount() > 0) {
            Tabla1.getColumnModel().getColumn(0).setResizable(false);
        }

        jPanel1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(288, 60, 270, 458));

        jLabel1.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        jLabel1.setText("DESCRIPCION");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(1010, 150, -1, -1));

        jSeparator1.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator1.setForeground(new java.awt.Color(0, 0, 0));
        jSeparator1.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N
        jPanel1.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 120, 323, 20));

        txtNumeroEmpleado.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        txtNumeroEmpleado.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtNumeroEmpleado.setBorder(null);
        txtNumeroEmpleado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNumeroEmpleadoActionPerformed(evt);
            }
        });
        jPanel1.add(txtNumeroEmpleado, new org.netbeans.lib.awtextra.AbsoluteConstraints(641, 89, 323, 26));

        txtCodigo.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        txtCodigo.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCodigo.setBorder(null);
        txtCodigo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodigoActionPerformed(evt);
            }
        });
        txtCodigo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCodigoKeyTyped(evt);
            }
        });
        jPanel1.add(txtCodigo, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 180, 200, 26));

        jSeparator2.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator2.setForeground(new java.awt.Color(0, 0, 0));
        jSeparator2.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N
        jPanel1.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 210, 200, 20));

        jLabel3.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        jLabel3.setText("REQUISITOR");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(641, 60, -1, -1));

        jTable3.setBackground(new java.awt.Color(255, 255, 255));
        jTable3.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        jTable3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "CANTIDAD", "DESCRICPION", "PROYECTO", "NO. PARTE", "U.M.", "PROVEEDOR"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane3.setViewportView(jTable3);
        if (jTable3.getColumnModel().getColumnCount() > 0) {
            jTable3.getColumnModel().getColumn(0).setResizable(false);
            jTable3.getColumnModel().getColumn(1).setResizable(false);
            jTable3.getColumnModel().getColumn(2).setResizable(false);
            jTable3.getColumnModel().getColumn(3).setResizable(false);
            jTable3.getColumnModel().getColumn(4).setResizable(false);
        }

        jPanel1.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 320, 680, 230));

        btnGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ImgAnimacion/save_48.png"))); // NOI18N
        btnGuardar.setBorder(null);
        btnGuardar.setContentAreaFilled(false);
        btnGuardar.setFocusPainted(false);
        btnGuardar.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnGuardar.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/ImgAnimacion/save_48.png"))); // NOI18N
        btnGuardar.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/ImgAnimacion/save_64.png"))); // NOI18N
        btnGuardar.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });
        jPanel1.add(btnGuardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 570, 64, 64));

        jLabel4.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        jLabel4.setText("PROYECTO");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(1010, 60, -1, -1));

        txtProyecto.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        txtProyecto.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtProyecto.setBorder(null);
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
        txtProyecto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtProyectoKeyTyped(evt);
            }
        });
        jPanel1.add(txtProyecto, new org.netbeans.lib.awtextra.AbsoluteConstraints(1010, 90, 323, 26));

        jSeparator3.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator3.setForeground(new java.awt.Color(0, 0, 0));
        jSeparator3.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N
        jPanel1.add(jSeparator3, new org.netbeans.lib.awtextra.AbsoluteConstraints(1010, 120, 323, 20));

        btnBorrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ImgAnimacion/borrra_48.png"))); // NOI18N
        btnBorrar.setBorder(null);
        btnBorrar.setContentAreaFilled(false);
        btnBorrar.setFocusPainted(false);
        btnBorrar.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnBorrar.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/ImgAnimacion/borrra_48.png"))); // NOI18N
        btnBorrar.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/ImgAnimacion/borrar_64.png"))); // NOI18N
        btnBorrar.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        btnBorrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBorrarActionPerformed(evt);
            }
        });
        jPanel1.add(btnBorrar, new org.netbeans.lib.awtextra.AbsoluteConstraints(1250, 570, 64, 64));

        btnBorrarTabla.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ImgAnimacion/eraser_48.png"))); // NOI18N
        btnBorrarTabla.setBorder(null);
        btnBorrarTabla.setContentAreaFilled(false);
        btnBorrarTabla.setFocusPainted(false);
        btnBorrarTabla.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnBorrarTabla.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/ImgAnimacion/eraser_48.png"))); // NOI18N
        btnBorrarTabla.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/ImgAnimacion/eraser_64.png"))); // NOI18N
        btnBorrarTabla.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        btnBorrarTabla.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBorrarTablaActionPerformed(evt);
            }
        });
        jPanel1.add(btnBorrarTabla, new org.netbeans.lib.awtextra.AbsoluteConstraints(950, 570, 64, 64));

        txtDescripcion.setBackground(new java.awt.Color(255, 255, 255));
        txtDescripcion.setColumns(20);
        txtDescripcion.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtDescripcion.setLineWrap(true);
        txtDescripcion.setRows(5);
        txtDescripcion.setWrapStyleWord(true);
        txtDescripcion.setBorder(null);
        txtDescripcion.setOpaque(false);
        jScrollPane4.setViewportView(txtDescripcion);

        jPanel1.add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(1010, 180, 320, 130));

        jLabel5.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        jLabel5.setText("CODIGO");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 150, 70, -1));

        btnAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ImgAnimacion/add_48.png"))); // NOI18N
        btnAdd.setBorder(null);
        btnAdd.setContentAreaFilled(false);
        btnAdd.setFocusPainted(false);
        btnAdd.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnAdd.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/ImgAnimacion/add_48.png"))); // NOI18N
        btnAdd.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/ImgAnimacion/add_64.png"))); // NOI18N
        btnAdd.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });
        jPanel1.add(btnAdd, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 250, 64, 64));

        jLabel6.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel6.setText("REQUISICION NO.");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(1130, 20, -1, -1));

        lblRequi.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        lblRequi.setText("DIA");
        jPanel1.add(lblRequi, new org.netbeans.lib.awtextra.AbsoluteConstraints(1250, 20, -1, -1));

        jLabel7.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jLabel7.setText("PLANOS EN ESTACION");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 30, -1, -1));

        jLabel9.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jLabel9.setText("PLANOS A PEDIR");
        jPanel1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 30, -1, -1));

        txtPlano.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtPlano.setPlaceholder("Introduce numero de plano");
        txtPlano.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPlanoActionPerformed(evt);
            }
        });
        jPanel1.add(txtPlano, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 260, 280, 40));

        jLabel8.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        jLabel8.setText("UM");
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(950, 150, -1, -1));

        txtUM.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        txtUM.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtUM.setBorder(null);
        txtUM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtUMActionPerformed(evt);
            }
        });
        txtUM.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtUMKeyTyped(evt);
            }
        });
        jPanel1.add(txtUM, new org.netbeans.lib.awtextra.AbsoluteConstraints(930, 180, 60, 26));

        jSeparator4.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator4.setForeground(new java.awt.Color(0, 0, 0));
        jSeparator4.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N
        jPanel1.add(jSeparator4, new org.netbeans.lib.awtextra.AbsoluteConstraints(930, 210, 60, 20));

        txtCantidad.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        txtCantidad.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCantidad.setBorder(null);
        txtCantidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCantidadActionPerformed(evt);
            }
        });
        txtCantidad.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCantidadKeyTyped(evt);
            }
        });
        jPanel1.add(txtCantidad, new org.netbeans.lib.awtextra.AbsoluteConstraints(840, 180, 70, 26));

        jLabel10.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        jLabel10.setText("CANTIDAD");
        jPanel1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(840, 150, -1, -1));

        jSeparator5.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator5.setForeground(new java.awt.Color(0, 0, 0));
        jSeparator5.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N
        jPanel1.add(jSeparator5, new org.netbeans.lib.awtextra.AbsoluteConstraints(840, 210, 70, 20));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 646, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        if (txtCodigo.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "DEBES LLENAR EL NUMERO DE PARTE", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
        } else if (txtProyecto.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "DEBES LLENAR EL NUMERO DE PROYECTO", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
        } else if (txtUM.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "DEBES LLENAR EL NUMERO DE UM", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
        } else if (txtDescripcion.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "DEBES LLENAR EL NUMERO DE DESCRIPCION", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
        } else if (txtCantidad.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "DEBES LLENAR EL NUMERO DE CANTIDAD", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
        } else {
            try {
                DefaultTableModel miModelo = (DefaultTableModel) jTable3.getModel();
                Connection con = null;
                Conexion con1 = new Conexion();
                con = con1.getConnection();
                Statement st = con.createStatement();
                String sql = "select Codigo, NumeroDeParte, UM, Proveedor from inventario where NumeroDeParte like '" + txtCodigo.getText() + "' or Codigo like '" + txtCodigo.getText() + "'";
                ResultSet rs = st.executeQuery(sql);
                String datos[] = new String[10];
                datos[0] = txtCantidad.getText();
                datos[1] = txtDescripcion.getText();
                datos[2] = txtProyecto.getText();
                datos[3] = txtCodigo.getText();
                datos[4] = txtUM.getText();
                while (rs.next()) {

                    datos[5] = rs.getString("Proveedor");

                }
                miModelo.addRow(datos);
                txtCodigo.setText("");
                txtDescripcion.setText("");
                txtUM.setText("");
                txtCantidad.setText("");

            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "ERROR AL VER BD " + e);
            }
        }
    }//GEN-LAST:event_btnAddActionPerformed

    private void txtProyectoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtProyectoKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtProyectoKeyTyped

    private void txtProyectoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtProyectoActionPerformed
        try {
            Connection con;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            String sql = "select * from proyectos where Proyecto like '" + txtProyecto.getText() + "'";
            ResultSet rs = st.executeQuery(sql);
            String pro = null;
            while (rs.next()) {
                pro = rs.getString("Proyecto");
            }
            if (pro == null) {
                JOptionPane.showMessageDialog(this, "ESTE PROYECTO NO EXISTE", "ADVERTENCIA", JOptionPane.ERROR_MESSAGE);
                txtProyecto.setEditable(true);
                txtProyecto.setEnabled(true);
                txtProyecto.setForeground(Color.black);
            } else {
                txtProyecto.setEditable(false);
                txtProyecto.setEnabled(false);
                txtProyecto.setForeground(Color.blue);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "ERROR: ", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_txtProyectoActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        if (txtCodigo.isEditable() == true) {
            if (Tabla1.getRowCount() < 1) {
                JOptionPane.showMessageDialog(this, "DEBES SELECCIONAR DATOS DE LA TABLA 2", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
            } else if (jTable3.getRowCount() < 1) {
                JOptionPane.showMessageDialog(this, "DEBES SELECCIONAR DATOS DE LA TABLA 3", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
            } else if (txtNumeroEmpleado.getText().equals("")) {
                JOptionPane.showMessageDialog(this, "DEBES LLENAR EL CAMPO DE RQUISITOR", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
            } else if (txtProyecto.getText().equals("")) {
                JOptionPane.showMessageDialog(this, "DEBES LLENAR EL CAMPO DE PROYECTO", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
            } else {
                try {
                    int n = 0;
                    Connection con = null;
                    Conexion con1 = new Conexion();
                    con = con1.getConnection();
                    Statement st = con.createStatement();
                    String datos[] = new String[10];
                    for (int i = 0; i < jTable3.getRowCount(); i++) {
                        String sql1 = "select Codigo, NumeroDeParte, UM, Proveedor from Inventario where Codigo like '" + jTable3.getValueAt(i, 0).toString() + "'";
                        ResultSet rs = st.executeQuery(sql1);
                        String datos1[] = new String[10];
                        while (rs.next()) {
                            datos1[0] = rs.getString("Codigo");
                            datos1[1] = rs.getString("NumeroDeParte");
                            datos1[2] = rs.getString("UM");
                            datos1[3] = rs.getString("Proveedor");
                        }
                        String sql2 = "insert into Requisiciones (NumRequisicion,Codigo,Descripcion,Proyecto,Cantidad,NumParte,Requisitor,UM,Proveedor) values(?,?,?,?,?,?,?,?,?)";
                        PreparedStatement pst = con.prepareStatement(sql2);
                        int a = Integer.parseInt(extraer());
                        a = a + 1;
                        pst.setString(1, "" + a);
                        pst.setString(2, jTable3.getValueAt(i, 3).toString());
                        pst.setString(3, jTable3.getValueAt(i, 1).toString());
                        pst.setString(4, jTable3.getValueAt(i, 2).toString());
                        pst.setString(5, jTable3.getValueAt(i, 0).toString());
                        pst.setString(6, "");
                        pst.setString(7, txtNumeroEmpleado.getText());
                        pst.setString(8, jTable3.getValueAt(i, 4).toString());
                        pst.setString(9, jTable3.getValueAt(i, 5).toString());
                        n = pst.executeUpdate();

                    }

                    String sql4 = "update datos set Estado = ? where Proyecto = ?";
                    PreparedStatement pst4 = con.prepareStatement(sql4);
                    int n4 = 0;
                    for (int i = 0; i < Tabla1.getRowCount(); i++) {
                        pst4.setString(1, "SIN MATERIAL");
                        pst4.setString(2, Tabla1.getValueAt(i, 0).toString());
                        n4 = pst4.executeUpdate();
                    }
                    Date date = new Date();
                    SimpleDateFormat fecha = new SimpleDateFormat("dd/MM/yyyy");
                    String fec = fecha.format(date);

                    String sql3 = "insert into Requisicion (NumeroEmpleado,Estatus,Estado,Progreso,Completado,Costo,NumeroCotizacion,Fecha,Comentarios) values (?,?,?,?,?,?,?,?,?)";
                    PreparedStatement pst1 = con.prepareStatement(sql3);
                    pst1.setString(1, numEmpleado);
                    pst1.setString(2, "PEDIDO");
                    pst1.setString(3, "NORMAL");
                    pst1.setString(4, "NUEVO");
                    pst1.setString(5, "NO");
                    pst1.setString(6, "");
                    pst1.setString(7, "");
                    pst1.setString(8, fec);
                    pst1.setString(9, "");

                    int n1 = pst1.executeUpdate();

                    if (n > 0 && n4 > 0 && n1 > 0) {

                        limpiarTabla3();
                        JOptionPane.showMessageDialog(this, "DATOS GUARDADOS CORRECTAMENTE");
                        lblRequi.setText(extraer());
                        txtNumeroEmpleado.setText("");
                        txtProyecto.setText("");
                        txtCodigo.setText("");
                        txtDescripcion.setText("");

                    }

                } catch (SQLException E) {
                    JOptionPane.showMessageDialog(this, "ERROR AL GUARDAR TABLA " + E);
                }

            }
        } else {
            Connection con = null;
            Conexion con1 = new Conexion();
            try {
                con = con1.getConnection();
                Statement st = con.createStatement();
                String sql4 = "update datos set Estado = ? where Proyecto = ?";
                PreparedStatement pst4 = con.prepareStatement(sql4);
                int n4 = 0;
                for (int i = 0; i < Tabla1.getRowCount(); i++) {
                    pst4.setString(1, "");
                    pst4.setString(2, Tabla1.getValueAt(i, 0).toString());
                    n4 = pst4.executeUpdate();
                }
                if (n4 > 0) {
                    JOptionPane.showMessageDialog(this, "DATOS GUARDADOS CORRECTAMENTE");
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "ERROR AL GUARDAR DATOS: " + ex);
            }
        }
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void txtCodigoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoKeyTyped
        char c = evt.getKeyChar();
        if (Character.isLowerCase(c)) {
            String cad = ("" + c).toUpperCase();
            c = cad.charAt(0);
            evt.setKeyChar(c);
        }
    }//GEN-LAST:event_txtCodigoKeyTyped

    private void txtCodigoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodigoActionPerformed
        try {

            Connection con = null;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            String sql = "select Descripcion from inventario where NumeroDeParte like '" + txtCodigo.getText() + "' or Codigo like '" + txtCodigo.getText() + "'";
            ResultSet rs = st.executeQuery(sql);
            String datos[] = new String[10];
            while (rs.next()) {
                datos[0] = rs.getString("Descripcion");
            }
            txtDescripcion.setText(datos[0]);
        } catch (SQLException E) {
            JOptionPane.showMessageDialog(this, "ERROR AL BUSCAR INVENTARIO " + E);
        }
    }//GEN-LAST:event_txtCodigoActionPerformed

    private void txtNumeroEmpleadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNumeroEmpleadoActionPerformed

    }//GEN-LAST:event_txtNumeroEmpleadoActionPerformed

    private void btnBorrarTablaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBorrarTablaActionPerformed
        DefaultTableModel miModelo = (DefaultTableModel) jTable3.getModel();
        miModelo.removeRow(jTable3.getSelectedRow());
    }//GEN-LAST:event_btnBorrarTablaActionPerformed

    private void btnBorrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBorrarActionPerformed
        limpiarTabla3();
    }//GEN-LAST:event_btnBorrarActionPerformed

    private void txtPlanoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPlanoActionPerformed

        try {
            Connection con;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            String sql = "select * from datos where Proyecto like '" + txtPlano.getText() + "'";
            String datos[] = new String[10];
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                datos[0] = rs.getString("Terminado");
                datos[1] = rs.getString("Estado");
                datos[2] = rs.getString("Proyecto");
            }

            if (datos[2] == null) {
                JOptionPane.showMessageDialog(this, "ESTE PLANO NO EXISTE", "ERROR", JOptionPane.ERROR_MESSAGE);
            } else {
                if (datos[0].equals("SI")) {
                    JOptionPane.showMessageDialog(this, "ESTE PLANO NO SE ENCUENTRA EN CORTES");
                } else {
                    boolean band = true;
                    for (int i = 0; i < Tabla1.getRowCount(); i++) {
                        if (Tabla1.getValueAt(i, 0).toString().equals(txtPlano.getText())) {
                            band = false;
                            break;
                        }
                    }

                    if (band == true) {
                        DefaultTableModel miModelo = (DefaultTableModel) Tabla1.getModel();
                        String d[] = new String[2];
                        d[0] = txtPlano.getText();
                        miModelo.addRow(d);
                    } else {
                        JOptionPane.showMessageDialog(this, "ESTE PLANO YA ESTA INCLUIDO", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
                    }
                    txtPlano.setText("");
                }
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "ERROR" + e, "ERROR", JOptionPane.ERROR_MESSAGE);
        }

    }//GEN-LAST:event_txtPlanoActionPerformed

    private void txtProyectoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtProyectoMouseClicked
        txtProyecto.setText("");
        txtProyecto.setEditable(true);
        txtProyecto.setEnabled(true);
        txtProyecto.setForeground(Color.black);
    }//GEN-LAST:event_txtProyectoMouseClicked

    private void txtUMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtUMActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtUMActionPerformed

    private void txtUMKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtUMKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtUMKeyTyped

    private void txtCantidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCantidadActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCantidadActionPerformed

    private void txtCantidadKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCantidadKeyTyped
        char c = evt.getKeyChar();
        if (c < '0' || c > '9')
            evt.consume();
    }//GEN-LAST:event_txtCantidadKeyTyped

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
            java.util.logging.Logger.getLogger(PedirMaterial.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PedirMaterial.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PedirMaterial.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PedirMaterial.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                PedirMaterial dialog = new PedirMaterial(new javax.swing.JInternalFrame(), true, "");
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
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnBorrar;
    private javax.swing.JButton btnBorrarTabla;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JTable jTable3;
    private javax.swing.JLabel lblRequi;
    public javax.swing.JTextField txtCantidad;
    public javax.swing.JTextField txtCodigo;
    public javax.swing.JTextArea txtDescripcion;
    public javax.swing.JTextField txtNumeroEmpleado;
    private rojeru_san.RSMTextFull txtPlano;
    public javax.swing.JTextField txtProyecto;
    public javax.swing.JTextField txtUM;
    // End of variables declaration//GEN-END:variables
}
