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
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class Electromecanico extends javax.swing.JInternalFrame {

    TextAutoCompleter ac;

    public void limpiarTabla(){
    DefaultTableModel miModelo = (DefaultTableModel) Tabla1.getModel();
    String titulos[] = {"COMPONENTE","CANTIDAD"};
    miModelo = new DefaultTableModel(null,titulos);
    Tabla1.setModel(miModelo);
    }
    
    public void Llenar() {
        try {
            cmbProyecto.removeAllItems();
            cmbProyecto.addItem("SELECCIONA PROYECTO");
            Connection con = null;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            String sql = "select * from Proyectos";
            ResultSet rs = st.executeQuery(sql);
            String datos[] = new String[10];
            while (rs.next()) {
                datos[0] = rs.getString("Proyecto");
                cmbProyecto.addItem(datos[0]);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "ERROR AL VER PROYECTOS" + e);
        }
    }

    public void autocompletar() {
        ac = new TextAutoCompleter(txtCodigo);
        try {
            Connection con = null;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            String sql = "select * from inventario";
            ResultSet rs = st.executeQuery(sql);
            String datos[] = new String[10];
            while (rs.next()) {
                datos[0] = rs.getString("Codigo");
                ac.addItem(datos[0]);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "ERROR AL VER INVENTARIO");
        }
    }

    public Electromecanico() {
        initComponents();
        Llenar();
        autocompletar();

        Tabla1.getTableHeader().setFont(new Font("Roboto", Font.PLAIN, 14));
        Tabla1.getTableHeader().setOpaque(false);
        Tabla1.setShowVerticalLines(false);
        ((javax.swing.plaf.basic.BasicInternalFrameUI) this.getUI()).setNorthPane(null);

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        btnSalir = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        cmbProyecto = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        txtCodigo = new javax.swing.JTextField();
        btnAdd = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        jSeparator4 = new javax.swing.JSeparator();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        sliPruebas = new javax.swing.JSlider();
        sliEnsamble = new javax.swing.JSlider();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        lblPorcentaje = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        lblPorcentaje1 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        txtCantidad = new javax.swing.JTextField();
        jSeparator5 = new javax.swing.JSeparator();
        jLabel32 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        Tabla1 = new javax.swing.JTable();

        setBorder(null);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnSalir.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Roboto", 0, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("X");
        jLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel1MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel1MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel1MouseExited(evt);
            }
        });

        javax.swing.GroupLayout btnSalirLayout = new javax.swing.GroupLayout(btnSalir);
        btnSalir.setLayout(btnSalirLayout);
        btnSalirLayout.setHorizontalGroup(
            btnSalirLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        btnSalirLayout.setVerticalGroup(
            btnSalirLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnSalirLayout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel1.add(btnSalir, new org.netbeans.lib.awtextra.AbsoluteConstraints(1320, 0, 60, -1));

        jPanel2.setBackground(new java.awt.Color(0, 153, 255));

        jLabel2.setFont(new java.awt.Font("Roboto", 0, 48)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("ELECTROMECANICO");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE)
        );

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 0, 660, 60));

        jLabel3.setFont(new java.awt.Font("Roboto", 0, 16)); // NOI18N
        jLabel3.setText("COMPONENTES ELECTROMECANICOS A USAR");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 150, 340, -1));

        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jPanel1.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 140, 20, 450));

        cmbProyecto.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        cmbProyecto.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "SELECCIONA PROYECTO" }));
        cmbProyecto.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbProyectoItemStateChanged(evt);
            }
        });
        cmbProyecto.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                cmbProyectoPropertyChange(evt);
            }
        });
        jPanel1.add(cmbProyecto, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 350, 190, -1));

        jLabel4.setFont(new java.awt.Font("Roboto", 0, 16)); // NOI18N
        jLabel4.setText("SELECCIONA PROYECTO");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 150, -1, -1));

        jSeparator2.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jPanel1.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 143, 20, 450));

        txtCodigo.setBackground(new java.awt.Color(255, 255, 255));
        txtCodigo.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        txtCodigo.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCodigo.setBorder(null);
        txtCodigo.setEnabled(false);
        txtCodigo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodigoActionPerformed(evt);
            }
        });
        jPanel1.add(txtCodigo, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 200, 260, -1));

        btnAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ImgAnimacion/add_48.png"))); // NOI18N
        btnAdd.setBorder(null);
        btnAdd.setContentAreaFilled(false);
        btnAdd.setEnabled(false);
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
        jPanel1.add(btnAdd, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 230, 64, 64));

        jLabel5.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        jLabel5.setText("CANTIDAD");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 180, -1, -1));
        jPanel1.add(jSeparator3, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 220, 260, 20));
        jPanel1.add(jSeparator4, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 380, 690, 20));

        jLabel6.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N
        jLabel6.setText("PORCENTAJE DE PRUEBAS Y AJUSTES");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 420, 410, -1));

        jLabel7.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N
        jLabel7.setText("PORCENTAJE DE ENSAMBLE DE MECANISMOS");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 170, 410, -1));

        sliPruebas.setBackground(new java.awt.Color(255, 255, 255));
        sliPruebas.setForeground(new java.awt.Color(0, 0, 0));
        sliPruebas.setValue(0);
        sliPruebas.setEnabled(false);
        sliPruebas.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                sliPruebasStateChanged(evt);
            }
        });
        jPanel1.add(sliPruebas, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 500, 620, -1));

        sliEnsamble.setBackground(new java.awt.Color(255, 255, 255));
        sliEnsamble.setValue(0);
        sliEnsamble.setEnabled(false);
        sliEnsamble.setOpaque(true);
        sliEnsamble.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                sliEnsambleStateChanged(evt);
            }
        });
        jPanel1.add(sliEnsamble, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 250, 620, -1));

        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("100");
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(1290, 230, 20, -1));

        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("0");
        jPanel1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 230, 20, -1));

        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("20");
        jPanel1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 230, 20, -1));

        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("30");
        jPanel1.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 230, 20, -1));

        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setText("40");
        jPanel1.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(930, 230, 20, -1));

        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setText("60");
        jPanel1.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(1050, 230, 20, -1));

        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel14.setText("70");
        jPanel1.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(1110, 230, 20, -1));

        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel15.setText("80");
        jPanel1.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(1170, 230, 20, -1));

        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel16.setText("50");
        jPanel1.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(990, 230, 20, -1));

        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel17.setText("90");
        jPanel1.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(1230, 230, 20, -1));

        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel18.setText("10");
        jPanel1.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 230, 20, -1));

        jLabel19.setFont(new java.awt.Font("Roboto", 0, 36)); // NOI18N
        jLabel19.setText("%");
        jPanel1.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(1060, 290, 30, 50));

        lblPorcentaje.setFont(new java.awt.Font("Roboto", 0, 36)); // NOI18N
        lblPorcentaje.setText("0");
        jPanel1.add(lblPorcentaje, new org.netbeans.lib.awtextra.AbsoluteConstraints(980, 290, 60, 50));

        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel20.setText("0");
        jPanel1.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 480, 20, -1));

        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel21.setText("10");
        jPanel1.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 480, 20, -1));

        jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel22.setText("20");
        jPanel1.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 480, 20, -1));

        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel23.setText("30");
        jPanel1.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 480, 20, -1));

        jLabel24.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel24.setText("40");
        jPanel1.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(930, 480, 20, -1));

        jLabel25.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel25.setText("50");
        jPanel1.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(990, 480, 20, -1));

        jLabel26.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel26.setText("60");
        jPanel1.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(1050, 480, 20, -1));

        jLabel27.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel27.setText("70");
        jPanel1.add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(1110, 480, 20, -1));

        jLabel28.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel28.setText("80");
        jPanel1.add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(1170, 480, 20, -1));

        jLabel29.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel29.setText("90");
        jPanel1.add(jLabel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(1230, 480, 20, -1));

        jLabel30.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel30.setText("100");
        jPanel1.add(jLabel30, new org.netbeans.lib.awtextra.AbsoluteConstraints(1290, 480, 20, -1));

        lblPorcentaje1.setFont(new java.awt.Font("Roboto", 0, 36)); // NOI18N
        lblPorcentaje1.setText("0");
        jPanel1.add(lblPorcentaje1, new org.netbeans.lib.awtextra.AbsoluteConstraints(980, 540, 60, 50));

        jLabel31.setFont(new java.awt.Font("Roboto", 0, 36)); // NOI18N
        jLabel31.setText("%");
        jPanel1.add(jLabel31, new org.netbeans.lib.awtextra.AbsoluteConstraints(1060, 540, 30, 50));

        txtCantidad.setBackground(new java.awt.Color(255, 255, 255));
        txtCantidad.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        txtCantidad.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCantidad.setBorder(null);
        txtCantidad.setEnabled(false);
        txtCantidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCantidadActionPerformed(evt);
            }
        });
        jPanel1.add(txtCantidad, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 200, 60, -1));
        jPanel1.add(jSeparator5, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 220, 60, 20));

        jLabel32.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        jLabel32.setText("INTRODUCE CODIGO DE COMPONENTE:");
        jPanel1.add(jLabel32, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 180, -1, -1));

        Tabla1.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        Tabla1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "COMPONENTE", "CANTIDAD"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        Tabla1.setRowHeight(25);
        Tabla1.setShowHorizontalLines(true);
        Tabla1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Tabla1MouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(Tabla1);
        if (Tabla1.getColumnModel().getColumnCount() > 0) {
            Tabla1.getColumnModel().getColumn(0).setResizable(false);
            Tabla1.getColumnModel().getColumn(1).setResizable(false);
        }

        jPanel1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 300, 430, 330));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 1380, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 651, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseClicked
        dispose();
    }//GEN-LAST:event_jLabel1MouseClicked

    private void jLabel1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseEntered
        btnSalir.setBackground(Color.red);
    }//GEN-LAST:event_jLabel1MouseEntered

    private void jLabel1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseExited
        btnSalir.setBackground(Color.white);
    }//GEN-LAST:event_jLabel1MouseExited

    private void txtCodigoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodigoActionPerformed

    }//GEN-LAST:event_txtCodigoActionPerformed

    private void sliEnsambleStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_sliEnsambleStateChanged
        lblPorcentaje.setText("" + sliEnsamble.getValue());
        try{
        Connection con = null;
        Conexion con1 = new Conexion();
        con = con1.getConnection();
        Statement st = con.createStatement();
        String sql = "select * from electromecanico where Proyecto like '"+cmbProyecto.getSelectedItem().toString()+"'";
        ResultSet rs = st.executeQuery(sql);
        String datos[] = new String[10];
        while(rs.next()){
        datos[0] = rs.getString("Proyecto");
        }
        
        if(datos[0] == null){
        String sql2 = "insert into electromecanico (Proyecto,NoRequisicion,PorcentajeEnsamble,PorcentajePruebas) values (?,?,?,?)";
        PreparedStatement pst = con.prepareStatement(sql2);
        pst.setString(1, cmbProyecto.getSelectedItem().toString());
        pst.setString(2, "");
        pst.setInt(3, sliEnsamble.getValue());
        pst.setInt(4, sliPruebas.getValue());
        
        pst.executeUpdate();
        }else{
        String sql2 = "update electromecanico set Proyecto = ?, NoRequisicion = ?, PorcentajeEnsamble = ?, PorcentajePruebas = ? where Proyecto = ?";
        PreparedStatement pst = con.prepareStatement(sql2);
        pst.setString(1, cmbProyecto.getSelectedItem().toString());
        pst.setString(2, "");
        pst.setInt(3, sliEnsamble.getValue());
        pst.setInt(4, sliPruebas.getValue());
        pst.setString(5, cmbProyecto.getSelectedItem().toString());
        
        pst.executeUpdate();
        }
        
        }catch(SQLException e){
        JOptionPane.showMessageDialog(this, "ERROR AL GUARDAR PORCENTAJE: "+e);
        }
    }//GEN-LAST:event_sliEnsambleStateChanged

    private void sliPruebasStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_sliPruebasStateChanged
        lblPorcentaje1.setText("" + sliPruebas.getValue());
        try{
        Connection con = null;
        Conexion con1 = new Conexion();
        con = con1.getConnection();
        Statement st = con.createStatement();
        String sql = "select * from electromecanico where Proyecto like '"+cmbProyecto.getSelectedItem().toString()+"'";
        ResultSet rs = st.executeQuery(sql);
        String datos[] = new String[10];
        while(rs.next()){
        datos[0] = rs.getString("Proyecto");
        }
        
        if(datos[0] == null){
        String sql2 = "insert into electromecanico (Proyecto,NoRequisicion,PorcentajeEnsamble,PorcentajePruebas) values (?,?,?,?)";
        PreparedStatement pst = con.prepareStatement(sql2);
        pst.setString(1, cmbProyecto.getSelectedItem().toString());
        pst.setString(2, "");
        pst.setInt(3, sliEnsamble.getValue());
        pst.setInt(4, sliPruebas.getValue());
        
        pst.executeUpdate();
        }else{
        String sql2 = "update electromecanico set Proyecto = ?, NoRequisicion = ?, PorcentajeEnsamble = ?, PorcentajePruebas = ? where Proyecto = ?";
        PreparedStatement pst = con.prepareStatement(sql2);
        pst.setString(1, cmbProyecto.getSelectedItem().toString());
        pst.setString(2, "");
        pst.setInt(3, sliEnsamble.getValue());
        pst.setInt(4, sliPruebas.getValue());
        pst.setString(5, cmbProyecto.getSelectedItem().toString());
        
        pst.executeUpdate();
        }
        
        }catch(SQLException e){
        JOptionPane.showMessageDialog(this, "ERROR AL GUARDAR PORCENTAJE: "+e);
        }
    }//GEN-LAST:event_sliPruebasStateChanged

    private void txtCantidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCantidadActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCantidadActionPerformed

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        try {
            DefaultTableModel miLista = (DefaultTableModel) Tabla1.getModel();
            Connection con = null;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            String sql = "select * from inventario where Codigo like '" + txtCodigo.getText() + "'";
            ResultSet rs = st.executeQuery(sql);
            String datos[] = new String[4];

            while (rs.next()) {
                datos[0] = rs.getString("Descripcion");
                datos[1] = txtCantidad.getText();
            }
            if (datos[0].equals("") || txtCantidad.getText().equals("")) {
                JOptionPane.showMessageDialog(this, "DEBES SELECCIONAR UN NUMERO DE PARTE");
            } else {
                Statement st1 = con.createStatement();
                String sql1 = "select * from compelectromecanico where NoProyecto like '" + cmbProyecto.getSelectedItem() + "'";
                ResultSet rs1 = st1.executeQuery(sql1);
                String datos1[] = new String[10];
                while (rs1.next()) {
                    datos1[0] = rs1.getString("NoProyecto");
                }

                String sql2 = "insert into compelectromecanico (NoParte,Cantidad,NoProyecto,Descripcion) values (?,?,?,?)";
                PreparedStatement pst = con.prepareStatement(sql2);
                pst.setString(1, txtCodigo.getText());
                pst.setString(2, datos[1]);
                pst.setString(3, (String) cmbProyecto.getSelectedItem());
                pst.setString(4, datos[0]);

                pst.executeUpdate();

                
                miLista.addRow(datos);
                Tabla1.setModel(miLista);
                txtCodigo.setText("");
                txtCantidad.setText("");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "ERROR AL AGREGAR ELEMENTOS:" + e);
        }
    }//GEN-LAST:event_btnAddActionPerformed

    private void cmbProyectoItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbProyectoItemStateChanged
        
    }//GEN-LAST:event_cmbProyectoItemStateChanged

    private void Tabla1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Tabla1MouseClicked
        try{
        Connection con = null;
        Conexion con1 = new Conexion();
        con = con1.getConnection();
        int fila = Tabla1.getSelectedRow();
        String sql = "delete from compelectromecanico where NoProyecto = ? and Descripcion = ?";
        PreparedStatement pst = con.prepareStatement(sql);
        int opc = JOptionPane.showConfirmDialog(this, "¿ESTAS SEGURO QUE DESEAS BORRAR ESTE COMPONENTE DE LA LISTA?");
        if(opc == 0){
        pst.setString(1, (String) cmbProyecto.getSelectedItem());
        pst.setString(2, Tabla1.getValueAt(fila, 0).toString());
        pst.executeUpdate();
        }
        }catch(SQLException e){
        JOptionPane.showMessageDialog(this, "ERROR AL BORRAR COMPONENTE: "+e);
        }
    }//GEN-LAST:event_Tabla1MouseClicked

    private void cmbProyectoPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_cmbProyectoPropertyChange
       limpiarTabla();
        DefaultTableModel miModelo = (DefaultTableModel) Tabla1.getModel();
        if (cmbProyecto.getSelectedIndex() == 0) {
            txtCodigo.setEnabled(false);
            txtCantidad.setEnabled(false);
            sliEnsamble.setEnabled(false);
            sliPruebas.setEnabled(false);
            btnAdd.setEnabled(false);
        } else {
            txtCodigo.setEnabled(true);
            txtCantidad.setEnabled(true);
            sliEnsamble.setEnabled(true);
            sliPruebas.setEnabled(true);
            btnAdd.setEnabled(true);
            try{
            Connection con = null;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            String sql = "select * from compelectromecanico where NoProyecto like '"+cmbProyecto.getSelectedItem().toString()+"'";
            ResultSet rs = st.executeQuery(sql);
            String datos[] = new String[14];
            while(rs.next()){
            datos[0] = rs.getString("Descripcion");
            datos[1] = rs.getString("Cantidad");
            miModelo.addRow(datos);
            }
            Statement st2 = con.createStatement();
            String sql2 = "select * from electromecanico where Proyecto like '"+cmbProyecto.getSelectedItem().toString()+"'";
            ResultSet rs2 = st2.executeQuery(sql2);
            int datos1[] = new int[10];
            while(rs2.next()){
            datos1[0] = rs2.getInt("PorcentajeEnsamble");
            datos1[1] = rs2.getInt("PorcentajePruebas");
            }
            sliEnsamble.setValue(datos1[0]);
            sliPruebas.setValue(datos1[1]);
            }catch(SQLException e ){
            JOptionPane.showMessageDialog(this, "ERROR AL VER DATOS: "+ e);
            }
            
        }
    }//GEN-LAST:event_cmbProyectoPropertyChange


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable Tabla1;
    private javax.swing.JButton btnAdd;
    private javax.swing.JPanel btnSalir;
    private javax.swing.JComboBox<String> cmbProyecto;
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
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JLabel lblPorcentaje;
    private javax.swing.JLabel lblPorcentaje1;
    private javax.swing.JSlider sliEnsamble;
    private javax.swing.JSlider sliPruebas;
    private javax.swing.JTextField txtCantidad;
    private javax.swing.JTextField txtCodigo;
    // End of variables declaration//GEN-END:variables
}
