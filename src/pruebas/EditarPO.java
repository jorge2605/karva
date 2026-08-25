package pruebas;

import Conexiones.Conexion;
import java.awt.Color;
import java.awt.Font;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumn;

public class EditarPO extends javax.swing.JDialog {

    String po = null;
    String numEmpleado;

    public boolean getValor() {
        boolean band = true;
        for (int i = 0; i < Tabla1.getRowCount(); i++) {
            if (Tabla1.getValueAt(i, 1) == null || Tabla1.getValueAt(i, 2) == null || Tabla1.getValueAt(i, 3) == null
                    || Tabla1.getValueAt(i, 4) == null || Tabla1.getValueAt(i, 5) == null) {
                band = false;
            }
        }
        return band;
    }

    public void editarOrden() {
        if (getValor()) {
            try {
                Connection con = null;
                Conexion con1 = new Conexion();
                con = con1.getConnection();
                Statement st = con.createStatement();

                String sql = "insert into detallesedicionpo (Requisitor,Cantidad,UM,Descripcion,NumParte,Proveedor,Proyecto,IdArticulo,Precio,NumRequisicion,PO) values(?,?,?,?,?,?,?,?,?,?,?)";
                PreparedStatement pst = con.prepareStatement(sql);

                String sql3 = "insert into edicionpo (PO, Estado,FechaCreacion,NumRequisicion,NumEmpleado) values(?,?,?,?,?)";
                PreparedStatement pst3 = con.prepareStatement(sql3);

                String datos[] = new String[10];
                int n = 0, n1 = 0;
                for (int i = 0; i < Tabla1.getRowCount(); i++) {
                    String sql2 = "select * from requisiciones where Id like '" + Tabla1.getValueAt(i, 0).toString() + "'";
                    ResultSet rs = st.executeQuery(sql2);
                    while (rs.next()) {
                        datos[0] = rs.getString("Requisitor");
                        datos[1] = rs.getString("UM");
                        datos[2] = rs.getString("Proyecto");
                        datos[3] = rs.getString("NumRequisicion");
                        datos[4] = rs.getString("OC");
                        datos[5] = rs.getString("Codigo");
                        datos[6] = rs.getString("Descripcion");
                        datos[7] = rs.getString("Cantidad");
                        datos[8] = rs.getString("Precio");
                        datos[9] = rs.getString("Proveedor");
                    }

                    pst.setString(1, datos[0]);//Requisitor
                    pst.setString(2, datos[7]);//Cantidad
                    pst.setString(3, datos[1]);//UM
                    pst.setString(4, datos[6]);//Descripcion
                    pst.setString(5, datos[5]);//NumParte
                    pst.setString(6, datos[9]);//Proveedor
                    pst.setString(7, datos[2]);//Proyecto
                    pst.setString(8, Tabla1.getValueAt(i, 0).toString());//IdArticulo
                    pst.setString(9, datos[8]);//Precio
                    pst.setString(10, datos[3]);//NumRequisicion
                    pst.setString(11, po);//PO

                    n = pst.executeUpdate();
                }

                for (int i = 0; i < Tabla1.getRowCount(); i++) {
                    if (Tabla1.getValueAt(i, 0).toString().equals("")) {
                        String sql4 = "insert into requisiciones (Requisitor,Cantidad,UM,Descripcion,Codigo,Proveedor,Proyecto,Precio,NumRequisicion,OC,Estado) values(?,?,?,?,?,?,?,?,?,?,?,?)";
                        PreparedStatement pst4 = con.prepareStatement(sql4);

                        pst4.setString(1, datos[0]);//Requisitor
                        pst4.setString(2, Tabla1.getValueAt(i, 3).toString());//Cantidad
                        pst4.setString(3, datos[1]);//UM
                        pst4.setString(4, datos[6]);//Descripcion
                        pst4.setString(5, Tabla1.getValueAt(i, 2).toString());//Codigo
                        pst4.setString(6, Tabla1.getValueAt(i, 5).toString());//Proveedor
                        pst4.setString(7, datos[2]);//Proyecto
                        pst4.setString(8, Tabla1.getValueAt(i, 4).toString());//Precio
                        pst4.setString(9, datos[3]);//NumRequisicion
                        pst4.setString(10, po);//PO
                        pst4.setString(11, "EDITADO");//PO

                        n1 += pst4.executeUpdate();

                    } else {
                        String sql2 = "update requisiciones set Descripcion = ?, Codigo = ?, Cantidad = ?,Precio = ?, Proveedor = ?, Estado = ? where Id = ?";
                        PreparedStatement pst2 = con.prepareStatement(sql2);

                        pst2.setString(1, Tabla1.getValueAt(i, 1).toString());
                        pst2.setString(2, Tabla1.getValueAt(i, 2).toString());
                        pst2.setString(3, Tabla1.getValueAt(i, 3).toString());
                        pst2.setString(4, Tabla1.getValueAt(i, 4).toString());
                        pst2.setString(5, Tabla1.getValueAt(i, 5).toString());
                        pst2.setString(6, "EDITADO");
                        pst2.setString(7, Tabla1.getValueAt(i, 0).toString());

                        n1 += pst2.executeUpdate();
                    }
                }

                Date fe = new Date();
                SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                String fecha = f.format(fe);

                pst3.setString(1, po);
                pst3.setString(2, "EDITADO");
                pst3.setString(3, fecha);
                pst3.setString(4, datos[3]);
                pst3.setString(5, this.numEmpleado);

                int n2 = pst3.executeUpdate();

                if (n > 0 && n2 > 0 && n1 > 0) {
                    JOptionPane.showMessageDialog(this, "ORDEN DE COMPRA MANDADA A APROBACION PARA SU REVISION");
                    limpiarTabla1();
                    po = null;
                } else {
                    JOptionPane.showMessageDialog(this, "ALGO SALIO MAL AL INTENTAR EDITAR ESTA PO", "ERROR", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "ERROR: " + e, "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "ERROR: ");
        }
    }

    public void limpiarTabla1() {
        Tabla1.setFont(new java.awt.Font("Roboto", 0, 12));

        Tabla1.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{},
                new String[]{
                    "ID", "DESCRIPCION", "NUMERO DE PARTE", "CANTIDAD", "PRECIO", "PROVEEDOR"

                }) {
            boolean[] canEdit = new boolean[]{
                false, true, true, true, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
        Tabla1.getTableHeader().setFont(new Font("Lexend", Font.BOLD, 14));
        Tabla1.getTableHeader().setOpaque(false);
        Tabla1.getTableHeader().setBackground(new Color(0, 78, 171));
        Tabla1.getTableHeader().setForeground(Color.white);
        Tabla1.setRowHeight(25);
        Tabla1.setShowVerticalLines(false);
        Tabla1.setGridColor(new Color(240, 240, 240));

        jScrollPane1.getViewport().setBackground(new Color(255, 255, 255));
    }
    
    public final void agregarFila() {
        DefaultTableModel miModelo = (DefaultTableModel) Tabla1.getModel();
        miModelo.addRow(new String[]{"","","","","",""});
    }

    public EditarPO(java.awt.Frame parent, boolean modal, String NumEmpleado) {
        super(parent, modal);
        initComponents();
        limpiarTabla1();
        this.numEmpleado = NumEmpleado;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupMenu1 = new javax.swing.JPopupMenu();
        eliminarPartida = new javax.swing.JMenuItem();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtPO = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        Tabla1 = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        botonRedondo1 = new scrollPane.BotonRedondo();
        botonRedondo2 = new scrollPane.BotonRedondo();

        eliminarPartida.setText("Agregar partida                ");
        eliminarPartida.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eliminarPartidaActionPerformed(evt);
            }
        });
        jPopupMenu1.add(eliminarPartida);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Lexend", 1, 12)); // NOI18N
        jLabel1.setText("INGRESA PO:");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 130, -1, -1));

        txtPO.setBackground(new java.awt.Color(255, 255, 255));
        txtPO.setFont(new java.awt.Font("Lexend", 0, 12)); // NOI18N
        txtPO.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtPO.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(204, 204, 204)));
        txtPO.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPOActionPerformed(evt);
            }
        });
        txtPO.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPOKeyTyped(evt);
            }
        });
        jPanel1.add(txtPO, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 150, 170, 20));

        jScrollPane1.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(255, 255, 255)));

        Tabla1.setBackground(new java.awt.Color(255, 255, 255));
        Tabla1.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        Tabla1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "DESCRIPCION", "NUMERO DE PARTE", "CANTIDAD", "PRECIO", "PROVEEDOR"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        Tabla1.setComponentPopupMenu(jPopupMenu1);
        Tabla1.setRowHeight(25);
        Tabla1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Tabla1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(Tabla1);
        if (Tabla1.getColumnModel().getColumnCount() > 0) {
            Tabla1.getColumnModel().getColumn(0).setPreferredWidth(100);
            Tabla1.getColumnModel().getColumn(0).setMaxWidth(100);
            Tabla1.getColumnModel().getColumn(1).setPreferredWidth(400);
        }

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 190, 1130, 310));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jLabel9.setFont(new java.awt.Font("Lexend", 1, 24)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 165, 252));
        jLabel9.setText("Modificacion de PO");
        jPanel3.add(jLabel9);

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1160, -1));

        botonRedondo1.setBackground(new java.awt.Color(255, 255, 255));
        botonRedondo1.setForeground(new java.awt.Color(0, 153, 0));
        botonRedondo1.setText("Agregar Partida");
        botonRedondo1.setBorderColor(new java.awt.Color(0, 153, 0));
        botonRedondo1.setBorderColorSelected(new java.awt.Color(0, 102, 0));
        botonRedondo1.setColor(new java.awt.Color(0, 153, 0));
        botonRedondo1.setFont(new java.awt.Font("Lexend", 0, 14)); // NOI18N
        botonRedondo1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonRedondo1ActionPerformed(evt);
            }
        });
        jPanel1.add(botonRedondo1, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 150, -1, -1));

        botonRedondo2.setBackground(new java.awt.Color(255, 255, 255));
        botonRedondo2.setForeground(new java.awt.Color(0, 153, 255));
        botonRedondo2.setText("Enviar PO");
        botonRedondo2.setBorderColor(new java.awt.Color(0, 153, 255));
        botonRedondo2.setBorderColorSelected(new java.awt.Color(0, 102, 204));
        botonRedondo2.setColor(new java.awt.Color(0, 153, 255));
        botonRedondo2.setFont(new java.awt.Font("Lexend", 0, 14)); // NOI18N
        botonRedondo2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonRedondo2ActionPerformed(evt);
            }
        });
        jPanel1.add(botonRedondo2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 530, -1, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 1176, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 616, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void txtPOActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPOActionPerformed
        try {
            limpiarTabla1();
            Connection con = null;
            DefaultTableModel miModelo = (DefaultTableModel) Tabla1.getModel();
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            Statement st2 = con.createStatement();
            Statement st3 = con.createStatement();
            String sql = "select * from requisiciones where OC like '" + txtPO.getText() + "'";
            ResultSet rs = st.executeQuery(sql);
            String datos[] = new String[10];

            String sql3 = "select * from edicionpo where PO like '" + txtPO.getText() + "'";
            ResultSet rs3 = st3.executeQuery(sql3);
            String datos3[] = new String[10];

            while (rs3.next()) {
                datos3[0] = rs3.getString("PO");
            }

            if (datos3[0] != null) {
                if (datos3[0].equals(txtPO.getText())) {
                    JOptionPane.showMessageDialog(this, "NO SE PUEDE EDITAR OTRA VES ESTA ORDEN DE COMPRA", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
                }
            } else {
                String sql2 = "select * from registroprov_compras order by Nombre";
                ResultSet rs2 = st2.executeQuery(sql2);
                String proveedor;

                JComboBox jcb = new JComboBox();

                po = txtPO.getText();
                while (rs2.next()) {
                    proveedor = rs2.getString("Nombre");
                    jcb.addItem(proveedor);
                }

                TableColumn tc = Tabla1.getColumnModel().getColumn(5);
                TableCellEditor tce = new DefaultCellEditor(jcb);
                tc.setCellEditor(tce);

                while (rs.next()) {
                    datos[0] = rs.getString("Id");
                    datos[1] = rs.getString("Descripcion");
                    datos[2] = rs.getString("Codigo");
                    datos[3] = rs.getString("Cantidad");
                    datos[4] = rs.getString("Precio");
                    datos[5] = rs.getString("Proveedor");
                    miModelo.addRow(datos);
                }
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "ERROR: " + e, "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_txtPOActionPerformed

    private void txtPOKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPOKeyTyped
        char c = evt.getKeyChar();
        if (Character.isLowerCase(c)) {
            String cad = ("" + c).toUpperCase();
            c = cad.charAt(0);
            evt.setKeyChar(c);
        }
    }//GEN-LAST:event_txtPOKeyTyped

    private void Tabla1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Tabla1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_Tabla1MouseClicked

    private void eliminarPartidaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_eliminarPartidaActionPerformed
        if (Tabla1.getRowCount() > 0) {
            DefaultTableModel miModelo = (DefaultTableModel) Tabla1.getModel();
            miModelo.removeRow(Tabla1.getSelectedRow());
        }
    }//GEN-LAST:event_eliminarPartidaActionPerformed

    private void botonRedondo1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonRedondo1ActionPerformed
        agregarFila();
    }//GEN-LAST:event_botonRedondo1ActionPerformed

    private void botonRedondo2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonRedondo2ActionPerformed
        editarOrden();
    }//GEN-LAST:event_botonRedondo2ActionPerformed

    public static void main(String args[]) {

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
            java.util.logging.Logger.getLogger(EditarPO.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(EditarPO.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(EditarPO.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(EditarPO.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                EditarPO dialog = new EditarPO(new javax.swing.JFrame(), true, "");
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
    private scrollPane.BotonRedondo botonRedondo1;
    private scrollPane.BotonRedondo botonRedondo2;
    private javax.swing.JMenuItem eliminarPartida;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField txtPO;
    // End of variables declaration//GEN-END:variables
}
