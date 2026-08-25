package pruebas;

import VentanaEmergente.Cotizacion.Nuevo;
import Conexiones.Conexion;
import java.sql.Connection;
import java.awt.Color;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import jdk.internal.net.http.common.Log;

public class Cotizaciones extends javax.swing.JInternalFrame {

    String numEmpleado;

    public void llenarBd() {
        try {
            Connection con;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            String datosMat[] = {"ALUMINIO", "CRS", "A2", "DELRIN", "DELRRIN", "INOX", "INOXIDABLE", "NYLON", "TEFLON", "POLICARBONATO", "PERFIL", "TOOLING", "DELRIN NATURAL", "DELRIN NEGRO", "4140"};
            String datosTipo[] = {"SOLERA", "BARRA", "REDONDO", "CUADRADO", "CUAD", "ANGULO", "TUBULAR", "TUBO", "PLACA", "EXTRUIDO"};

            int cont = 0;
            String sql = "select Descripcion, Proveedor, Precio from requisiciones where Descripcion like '%aluminio%' or Descripcion like '%crs%'  or Descripcion like '%a2%'"
                    + " or Descripcion like '%delrin%'  or Descripcion like '%delrrin%' or Descripcion like '%inoxidable%' or Descripcion like '%nylon%'"
                    + " or Descripcion like '%teflon%'  or Descripcion like '%policarbonato%' or Descripcion like '%extruido%' or Descripcion like '%tooling%'"
                    + " or Descripcion like '%delrin negro%' or Descripcion like '%delrin natural%' or Descripcion like '%4140%'";
            ResultSet rs = st.executeQuery(sql);
            String sql2 = "DROP TABLE IF EXISTS `cotizaciones`";
            PreparedStatement pst2 = con.prepareStatement(sql2);
            pst2.executeUpdate();
            String sql3 = "CREATE TABLE `cotizaciones` ("
                    + "  `Id` int NOT NULL AUTO_INCREMENT,"
                    + "  `Material` varchar(45) DEFAULT NULL,"
                    + "  `Tipo` varchar(45) DEFAULT NULL,"
                    + "  `Proveedor` varchar(45) DEFAULT NULL,"
                    + "  `Precio` varchar(45) DEFAULT NULL,"
                    + "  `Ancho` varchar(45) DEFAULT NULL,"
                    + "  `Alto` varchar(45) DEFAULT NULL,"
                    + "  `Largo` varchar(45) DEFAULT NULL,"
                    + "  `Extra` varchar(45) DEFAULT NULL,"
                    + "  PRIMARY KEY (`Id`)"
                    + ") ENGINE=InnoDB AUTO_INCREMENT=722 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci";
            PreparedStatement pst3 = con.prepareStatement(sql3);
            pst3.executeUpdate();
            while (rs.next()) {
                String mat = "", tip = "", med = "";
                String nombre = rs.getString("Descripcion");
                if (nombre.contains("TORNILLO") || nombre.contains("DOWEL") || nombre.contains("OPRESOR") || nombre.contains("PIN")) {

                } else {
                    //-----------------------------GET MATERIAL----------------------------
                    for (int i = 0; i < datosMat.length; i++) {
                        String material = datosMat[i];
                        nombre = nombre.toUpperCase();
                        StringTokenizer tokens = new StringTokenizer(nombre);
                        while (tokens.hasMoreTokens()) {
                            String token = tokens.nextToken();
                            if (mat.equals("")) {
                                if (token.equals(material)) {
                                    mat = material;
                                }
                            }
                        }
                    }
                    //------------------------------GET TIPO----------------------------------
                    for (int i = 0; i < datosTipo.length; i++) {
                        String tipo = datosTipo[i];
                        nombre = nombre.toUpperCase();
                        StringTokenizer tokens = new StringTokenizer(nombre);
                        int cont1 = 0;
                        while (tokens.hasMoreTokens()) {
                            cont1++;
                            String token = tokens.nextToken();
                            cont++;
                            if (tip.equals("")) {
                                if (token.equals(tipo)) {
                                    tip = tipo;
                                } else if (i == 8) {
                                    tip = "SOLERA";
                                }
                            }
                        }
                    }
                    //-----------------------------GET MEDIDAS------------------------------
                    nombre = nombre.toUpperCase();
                    StringTokenizer tokens = new StringTokenizer(nombre);
                    boolean x1 = false;
                    boolean x2 = false;
                    String medida1 = "";
                    String medida2 = "";
                    String medida3 = "";
                    String medida4 = "";
                    String anterior = "";
                    String siguiente = "";
                    while (tokens.hasMoreTokens()) {
                        String token = tokens.nextToken();
                        siguiente = token;
                        cont++;
                        if (x2) {
                            if (medida2.equals("")) {
                                medida2 = siguiente;
                            } else if (medida3.equals("")) {
                                medida3 = siguiente;
                            } else if (medida4.equals("")) {
                                medida4 = siguiente;
                            }
                            med = med + " X " + siguiente;
                            x2 = false;
                        }
                        if (token.equals("X")) {
                            if (x1 == false) {
                                medida1 = anterior;
                                med = med + anterior;
                                x1 = true;
                                x2 = true;
                            } else if (x2 == false) {
                                x2 = true;
                            }
                        }

                        anterior = token;
                    }
                    if (!medida1.equals("")) {
                        String precio = rs.getString("Precio");
                        if (precio != null) {
                            if (!precio.equals("")) {
                                PreparedStatement pst = con.prepareStatement("insert into cotizaciones (Material, Tipo, Proveedor, Precio, Ancho, Alto, Largo, Extra) values(?,?,?,?,?,?,?,?)");
                                pst.setString(1, mat);
                                pst.setString(2, tip);
                                pst.setString(3, rs.getString("Proveedor"));
                                pst.setString(4, rs.getString("Precio"));
                                pst.setString(5, medida1);
                                pst.setString(6, medida2);
                                pst.setString(7, medida3);
                                pst.setString(8, medida4);
                                pst.executeUpdate();
//                System.out.println("|"+mat+"|"+tip+"|"+rs.getString("Proveedor")+"|$-"+rs.getString("Precio")+" |"+medida1+"|"+medida2+"|"+medida3+"|"+medida4+"|");
//                System.out.println("------------------------------------------------------------------------------------------------");
                            }
                        }

                    }
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR: " + e);
        }

    }

    public void limpiarTabla() {
        Tabla1.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{},
                new String[]{
                    "Material", "Tipo", "Precio", "Ancho", "Alto", "Largo", "Extra", "Cantidad", "Total", "Proveedor"
                }
        ) {
            boolean[] canEdit = new boolean[]{
                false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
        Tabla1.setBackgoundHead(new java.awt.Color(51, 51, 51));
        Tabla1.setColorBorderHead(new java.awt.Color(255, 255, 255));
        Tabla1.setColorBorderRows(new java.awt.Color(255, 255, 255));
        Tabla1.setColorSecondary(new java.awt.Color(255, 255, 255));
        Tabla1.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        Tabla1.setFontHead(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        Tabla1.setFontRowHover(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        Tabla1.setFontRowSelect(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        Tabla1.setGridColor(new java.awt.Color(255, 255, 255));
    }

    public void buscar() {
        if (txtMaterial.getSelectedItem().toString().equals("")) {
            JOptionPane.showMessageDialog(this, "Debes llenar el campo de material", "Advertencia", JOptionPane.WARNING_MESSAGE);
        } else {
            try {
                limpiarTabla();
                Connection con;
                Conexion con1 = new Conexion();
                con = con1.getConnection();
                Statement st = con.createStatement();
                String material = txtMaterial.getSelectedItem().toString();
                String ancho = txtAncho.getText();
                String largo = txtLargo.getText();
                String alto = txtAlto.getText();
                String extra = txtExtra.getText();
                String tipo = txtTipo.getText();

                if (tipo.equals("")) {
                    tipo = " ";
                } else {
                    tipo = " and Tipo like '%" + tipo + "%'";
                }
                if (ancho.equals("")) {
                    ancho = " ";
                } else {
                    ancho = " and Ancho like '%" + ancho + "%'";
                }
                if (alto.equals("")) {
                    alto = " ";
                } else {
                    alto = " and Alto like '%" + alto + "%'";
                }
                if (largo.equals("")) {
                    largo = " ";
                } else {
                    largo = " and Largo like '%" + largo + "%'";
                }
                if (extra.equals("")) {
                    extra = " ";
                } else {
                    extra = " and Extra like '%" + extra + "%'";
                }
                String sql = "select * from cotizaciones where Material like '%" + material + "%' " + tipo + ancho + alto + largo + extra;
                ResultSet rs = st.executeQuery(sql);
                String datos[] = new String[15];
                DefaultTableModel miModelo = (DefaultTableModel) Tabla1.getModel();
                while (rs.next()) {
                    datos[0] = rs.getString("Material");
                    datos[1] = rs.getString("Tipo");
                    datos[2] = rs.getString("Precio");
                    datos[3] = rs.getString("Ancho");
                    datos[4] = rs.getString("Alto");
                    datos[5] = rs.getString("Largo");
                    datos[6] = rs.getString("Extra");
                    datos[9] = rs.getString("Proveedor");
//                datos[7] = rs.getString("");
//                datos[8] = rs.getString("");
//                datos[9] = rs.getString("");
                    miModelo.addRow(datos);
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "ERROR: " + e, "ERROR", JOptionPane.ERROR_MESSAGE);
                Logger.getLogger(Inicio1.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }

    public Cotizaciones(String numEmpleado) {
        initComponents();
        ((javax.swing.plaf.basic.BasicInternalFrameUI) this.getUI()).setNorthPane(null);
        Tabla1.setFillsViewportHeight(true);
        jScrollPane1.getViewport().setBackground(Color.white);
        llenarBd();
        this.numEmpleado = numEmpleado;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jPanel13 = new javax.swing.JPanel();
        btnSalir = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        txtMaterial = new RSMaterialComponent.RSComboBoxMaterial();
        txtTipo = new rojeru_san.RSMTextFull();
        txtAncho = new rojeru_san.RSMTextFull();
        txtAlto = new rojeru_san.RSMTextFull();
        txtLargo = new rojeru_san.RSMTextFull();
        txtExtra = new rojeru_san.RSMTextFull();
        rSButtonRoundRipple3 = new rojeru_san.rsbutton.RSButtonRoundRipple();
        jPanel10 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        Tabla1 = new rojerusan.RSTableMetro();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        agregar = new javax.swing.JMenuItem();

        setBorder(null);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new java.awt.BorderLayout());

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new java.awt.BorderLayout());

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jLabel9.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 48)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 165, 252));
        jLabel9.setText("Cotizaciones");
        jPanel3.add(jLabel9);

        jPanel2.add(jPanel3, java.awt.BorderLayout.CENTER);

        jPanel13.setBackground(new java.awt.Color(255, 255, 255));

        btnSalir.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("  X  ");
        jLabel1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
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
        btnSalir.add(jLabel1);

        jPanel13.add(btnSalir);

        jPanel2.add(jPanel13, java.awt.BorderLayout.EAST);

        jPanel1.add(jPanel2, java.awt.BorderLayout.PAGE_START);

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setLayout(new java.awt.BorderLayout());

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setLayout(new java.awt.BorderLayout());

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));
        jPanel8.setLayout(new java.awt.BorderLayout());

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));

        txtMaterial.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "ALUMINIO", "CRS", "A2", "DELRIN", "DELRRIN", "INOX", "INOXIDABLE", "NYLON", "TEFLON", "POLICARBONATO", "PERFIL", "TOOLING", "DELRIN NEGRO", "DELRIN NATURAL", "4140" }));
        jPanel9.add(txtMaterial);

        txtTipo.setBackground(new java.awt.Color(255, 255, 255));
        txtTipo.setForeground(new java.awt.Color(51, 51, 51));
        txtTipo.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        txtTipo.setNextFocusableComponent(txtAncho);
        txtTipo.setPlaceholder("Tipo");
        txtTipo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTipoActionPerformed(evt);
            }
        });
        jPanel9.add(txtTipo);

        txtAncho.setBackground(new java.awt.Color(255, 255, 255));
        txtAncho.setForeground(new java.awt.Color(51, 51, 51));
        txtAncho.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        txtAncho.setNextFocusableComponent(txtAlto);
        txtAncho.setPlaceholder("Ancho");
        txtAncho.setPreferredSize(new java.awt.Dimension(100, 42));
        txtAncho.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtAnchoActionPerformed(evt);
            }
        });
        jPanel9.add(txtAncho);

        txtAlto.setBackground(new java.awt.Color(255, 255, 255));
        txtAlto.setForeground(new java.awt.Color(51, 51, 51));
        txtAlto.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        txtAlto.setNextFocusableComponent(txtLargo);
        txtAlto.setPlaceholder("Alto");
        txtAlto.setPreferredSize(new java.awt.Dimension(100, 42));
        txtAlto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtAltoActionPerformed(evt);
            }
        });
        jPanel9.add(txtAlto);

        txtLargo.setBackground(new java.awt.Color(255, 255, 255));
        txtLargo.setForeground(new java.awt.Color(51, 51, 51));
        txtLargo.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        txtLargo.setNextFocusableComponent(txtExtra);
        txtLargo.setPlaceholder("Largo");
        txtLargo.setPreferredSize(new java.awt.Dimension(100, 42));
        txtLargo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtLargoActionPerformed(evt);
            }
        });
        jPanel9.add(txtLargo);

        txtExtra.setBackground(new java.awt.Color(255, 255, 255));
        txtExtra.setForeground(new java.awt.Color(51, 51, 51));
        txtExtra.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        txtExtra.setNextFocusableComponent(rSButtonRoundRipple3);
        txtExtra.setPlaceholder("Extra");
        txtExtra.setPreferredSize(new java.awt.Dimension(100, 42));
        txtExtra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtExtraActionPerformed(evt);
            }
        });
        jPanel9.add(txtExtra);

        rSButtonRoundRipple3.setBackground(new java.awt.Color(204, 204, 204));
        rSButtonRoundRipple3.setText("Buscar");
        rSButtonRoundRipple3.setColorHover(new java.awt.Color(51, 51, 51));
        rSButtonRoundRipple3.setPreferredSize(new java.awt.Dimension(80, 40));
        rSButtonRoundRipple3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rSButtonRoundRipple3ActionPerformed(evt);
            }
        });
        jPanel9.add(rSButtonRoundRipple3);

        jPanel8.add(jPanel9, java.awt.BorderLayout.PAGE_START);

        jPanel10.setBackground(new java.awt.Color(255, 255, 255));
        jPanel10.setLayout(new java.awt.BorderLayout());

        jScrollPane1.setBorder(null);

        Tabla1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Material", "Tipo", "Precio", "Ancho", "Alto", "Largo", "Extra", "Cantidad", "Total", "Proveedor"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, true, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        Tabla1.setBackgoundHead(new java.awt.Color(51, 51, 51));
        Tabla1.setColorBorderHead(new java.awt.Color(255, 255, 255));
        Tabla1.setColorBorderRows(new java.awt.Color(255, 255, 255));
        Tabla1.setColorSecondary(new java.awt.Color(255, 255, 255));
        Tabla1.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        Tabla1.setFontHead(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        Tabla1.setFontRowHover(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        Tabla1.setFontRowSelect(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        Tabla1.setGridColor(new java.awt.Color(255, 255, 255));
        jScrollPane1.setViewportView(Tabla1);

        jPanel10.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jPanel8.add(jPanel10, java.awt.BorderLayout.CENTER);

        jPanel6.add(jPanel8, java.awt.BorderLayout.CENTER);

        jPanel4.add(jPanel6, java.awt.BorderLayout.CENTER);

        jPanel1.add(jPanel4, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        jMenu1.setText("File");

        agregar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/add.png"))); // NOI18N
        agregar.setText("    Agregar  material");
        agregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                agregarActionPerformed(evt);
            }
        });
        jMenu1.add(agregar);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseClicked
        dispose();
    }//GEN-LAST:event_jLabel1MouseClicked

    private void jLabel1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseEntered
        btnSalir.setBackground(java.awt.Color.red);
    }//GEN-LAST:event_jLabel1MouseEntered

    private void jLabel1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseExited
        btnSalir.setBackground(java.awt.Color.white);
    }//GEN-LAST:event_jLabel1MouseExited

    private void rSButtonRoundRipple3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rSButtonRoundRipple3ActionPerformed
        buscar();
    }//GEN-LAST:event_rSButtonRoundRipple3ActionPerformed

    private void txtTipoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTipoActionPerformed
        buscar();
    }//GEN-LAST:event_txtTipoActionPerformed

    private void txtAnchoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtAnchoActionPerformed
        buscar();
    }//GEN-LAST:event_txtAnchoActionPerformed

    private void txtAltoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtAltoActionPerformed
        buscar();
    }//GEN-LAST:event_txtAltoActionPerformed

    private void txtLargoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtLargoActionPerformed
        buscar();
    }//GEN-LAST:event_txtLargoActionPerformed

    private void txtExtraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtExtraActionPerformed
        buscar();
    }//GEN-LAST:event_txtExtraActionPerformed

    private void agregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_agregarActionPerformed
        JFrame f = (JFrame) JOptionPane.getFrameForComponent(this);
        Nuevo n = new Nuevo(f, true, numEmpleado);
        n.setVisible(true);
    }//GEN-LAST:event_agregarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private rojerusan.RSTableMetro Tabla1;
    private javax.swing.JMenuItem agregar;
    private javax.swing.JPanel btnSalir;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private rojeru_san.rsbutton.RSButtonRoundRipple rSButtonRoundRipple3;
    private rojeru_san.RSMTextFull txtAlto;
    private rojeru_san.RSMTextFull txtAncho;
    private rojeru_san.RSMTextFull txtExtra;
    private rojeru_san.RSMTextFull txtLargo;
    private RSMaterialComponent.RSComboBoxMaterial txtMaterial;
    private rojeru_san.RSMTextFull txtTipo;
    // End of variables declaration//GEN-END:variables
}
