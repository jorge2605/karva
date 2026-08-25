package VentanaEmergente.Recibos;

import Conexiones.Conexion;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class CancelarOrden extends javax.swing.JDialog implements ActionListener{

    elementosSeleccionados es;
    
    public void verificar(String campo, String requi){
        try{
            Connection con;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            String sql = "select * from requisiciones where "+campo+" like '"+requi+"'";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            String oc;
            int comp = 0;
            int can = 0;
            while(rs.next()){
                comp++;
                oc = rs.getString("OC");
                if(oc.equals("CANDELADO")){
                    can++;
                }
            }
            if(comp == can){
                String sql2 = "update requisicion set Estado = ?, Estatus = ?, Progreso = ? Completo = ? where Id = ?";
                PreparedStatement pst2 = con.prepareStatement(sql2);
                
                pst2.setString(1, "CANCELADO");
                pst2.setString(2, "CANCELADO");
                pst2.setString(3, "CANCELADO");
                pst2.setString(4, "NO");
                pst2.setString(5, requi);
                
                pst2.executeUpdate();
                
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "ERROR: "+e,"ERROR",JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void limpiarTabla(){
        Tabla1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
            },
            new String [] {
                "ID", "No. Requisicion", "Codigo", "Descripcion", "Cantidad", "Orden de compra"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, true, false
            };
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        Tabla1.setColorBorderHead(new java.awt.Color(153,204,255));
        Tabla1.setColorBorderRows(new java.awt.Color(255, 255, 255));
        Tabla1.setColorPrimaryText(new java.awt.Color(51, 51, 51));
        Tabla1.setColorSecondary(new java.awt.Color(240, 240, 240));
        Tabla1.setColorSecundaryText(new java.awt.Color(51, 51, 51));
        Tabla1.setModelSelection(rojerusan.RSTableMetro.SELECTION_ROWS.MULTIPLE_INTERVAL_SELECTION);
        Tabla1.setName("");
    }
    
    public final void verOrden() {
        try{
            limpiarTabla();
            Connection con;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            String campo = "";
            String oc = txtOrden.getText();
            if(orden.isSelected()){
                campo = "OC";
            }else if(requisicion.isSelected()){
                campo = "NumRequisicion";
            }
            String sql = "select * from requisiciones where "+campo+" like '" + oc + "'";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            String datos[] = new String[15];
            DefaultTableModel miModelo = (DefaultTableModel) Tabla1.getModel();
            while(rs.next()){
                datos[0] = rs.getString("Id");
                datos[1] = rs.getString("NumRequisicion");
                datos[2] = rs.getString("Codigo");
                datos[3] = rs.getString("Descripcion");
                datos[4] = rs.getString("Cantidad");
                datos[5] = rs.getString("OC");
                miModelo.addRow(datos);
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "ERROR: "+e,"ERROR",JOptionPane.ERROR_MESSAGE);  
        }
    }
    
    public CancelarOrden(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        jScrollPane1.getViewport().setBackground(Color.white);
        Tabla1.setShowGrid(false);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPopupMenu1 = new javax.swing.JPopupMenu();
        cancelar = new javax.swing.JMenuItem();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        panelX = new javax.swing.JPanel();
        lblX = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        orden = new RSMaterialComponent.RSRadioButtonMaterial();
        requisicion = new RSMaterialComponent.RSRadioButtonMaterial();
        jPanel5 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        txtOrden = new RSMaterialComponent.RSTextFieldIconDos();
        jScrollPane1 = new javax.swing.JScrollPane();
        Tabla1 = new rojerusan.RSTableMetro();

        jPopupMenu1.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
                jPopupMenu1PopupMenuWillBecomeVisible(evt);
            }
        });

        cancelar.setText("Cancelar articulos seleccionados");
        cancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelarActionPerformed(evt);
            }
        });
        jPopupMenu1.add(cancelar);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setPreferredSize(new java.awt.Dimension(1062, 578));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(220, 220, 220)));
        jPanel1.setLayout(new java.awt.BorderLayout());

        jPanel2.setLayout(new java.awt.BorderLayout());

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jLabel2.setFont(new java.awt.Font("Roboto", 1, 20)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 165, 252));
        jLabel2.setText("Cancelar ordenes de compra");
        jPanel3.add(jLabel2);

        jPanel2.add(jPanel3, java.awt.BorderLayout.CENTER);

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        panelX.setBackground(new java.awt.Color(255, 255, 255));

        lblX.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblX.setText(" X ");
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

        jPanel4.add(panelX);

        jPanel2.add(jPanel4, java.awt.BorderLayout.EAST);

        jPanel1.add(jPanel2, java.awt.BorderLayout.PAGE_START);

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setLayout(new java.awt.BorderLayout());

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel1.setText("Seleccionar: ");
        jPanel7.add(jLabel1);

        buttonGroup1.add(orden);
        orden.setForeground(new java.awt.Color(51, 51, 51));
        orden.setSelected(true);
        orden.setText("Orden de compra");
        orden.setColorCheck(new java.awt.Color(0, 102, 204));
        orden.setColorUnCheck(new java.awt.Color(204, 204, 204));
        orden.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        orden.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ordenMouseClicked(evt);
            }
        });
        orden.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ordenActionPerformed(evt);
            }
        });
        jPanel7.add(orden);

        buttonGroup1.add(requisicion);
        requisicion.setForeground(new java.awt.Color(51, 51, 51));
        requisicion.setText("Requisicion");
        requisicion.setColorCheck(new java.awt.Color(0, 102, 204));
        requisicion.setColorUnCheck(new java.awt.Color(204, 204, 204));
        requisicion.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        requisicion.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                requisicionMouseClicked(evt);
            }
        });
        requisicion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                requisicionActionPerformed(evt);
            }
        });
        jPanel7.add(requisicion);

        jPanel6.add(jPanel7, java.awt.BorderLayout.PAGE_START);

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setLayout(new java.awt.BorderLayout());

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));

        txtOrden.setForeground(new java.awt.Color(51, 51, 51));
        txtOrden.setBorderColor(new java.awt.Color(153, 153, 153));
        txtOrden.setColorIcon(new java.awt.Color(255, 255, 255));
        txtOrden.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtOrden.setPhColor(new java.awt.Color(51, 51, 51));
        txtOrden.setPlaceholder("Orden de compra");
        txtOrden.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtOrdenActionPerformed(evt);
            }
        });
        jPanel8.add(txtOrden);

        jPanel5.add(jPanel8, java.awt.BorderLayout.PAGE_START);

        jScrollPane1.setBorder(null);

        Tabla1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "No. Requisicion", "Codigo", "Descripcion", "Cantidad", "Orden de compra"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, true, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        Tabla1.setBackgoundHead(new java.awt.Color(153, 204, 255));
        Tabla1.setColorBorderHead(new java.awt.Color(153, 204, 255));
        Tabla1.setColorBorderRows(new java.awt.Color(255, 255, 255));
        Tabla1.setColorPrimaryText(new java.awt.Color(51, 51, 51));
        Tabla1.setColorSecondary(new java.awt.Color(240, 240, 240));
        Tabla1.setColorSecundaryText(new java.awt.Color(51, 51, 51));
        Tabla1.setComponentPopupMenu(jPopupMenu1);
        Tabla1.setModelSelection(rojerusan.RSTableMetro.SELECTION_ROWS.MULTIPLE_INTERVAL_SELECTION);
        Tabla1.setName(""); // NOI18N
        jScrollPane1.setViewportView(Tabla1);

        jPanel5.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jPanel6.add(jPanel5, java.awt.BorderLayout.CENTER);

        jPanel1.add(jPanel6, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

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

    private void txtOrdenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtOrdenActionPerformed
        verOrden();
    }//GEN-LAST:event_txtOrdenActionPerformed

    private void ordenMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ordenMouseClicked
        
    }//GEN-LAST:event_ordenMouseClicked

    private void requisicionMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_requisicionMouseClicked
        
    }//GEN-LAST:event_requisicionMouseClicked

    private void ordenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ordenActionPerformed
        txtOrden.setPlaceholder("Orden de compra");
    }//GEN-LAST:event_ordenActionPerformed

    private void requisicionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_requisicionActionPerformed
        txtOrden.setPlaceholder("Requisicion");
    }//GEN-LAST:event_requisicionActionPerformed

    private void jPopupMenu1PopupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_jPopupMenu1PopupMenuWillBecomeVisible
        if(Tabla1.getSelectedRows().length > 0){
            cancelar.setEnabled(true);
        }else{
            cancelar.setEnabled(false);
        }
    }//GEN-LAST:event_jPopupMenu1PopupMenuWillBecomeVisible

    private void cancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelarActionPerformed
        JFrame f = (JFrame) JOptionPane.getFrameForComponent(this);
        es = new elementosSeleccionados(f, true);
        String dat[] = new String[10];
        DefaultTableModel miModelo = (DefaultTableModel) es.Tabla1.getModel();
        for (int i = 0; i < Tabla1.getSelectedRows().length; i++) {
            if(Tabla1.getValueAt(Tabla1.getSelectedRows()[i], 0) != null){
                dat[0] = Tabla1.getValueAt(Tabla1.getSelectedRows()[i], 0).toString();
            }else{
                dat[0] = "";
            }
            if(Tabla1.getValueAt(Tabla1.getSelectedRows()[i], 2) != null){
                dat[1] = Tabla1.getValueAt(Tabla1.getSelectedRows()[i], 2).toString();
            }else{
                dat[1] = "";
            }
            if(Tabla1.getValueAt(Tabla1.getSelectedRows()[i], 3) != null){
                dat[2] = Tabla1.getValueAt(Tabla1.getSelectedRows()[i], 3).toString();
            }else{
                dat[2] = "";
            }
            miModelo.addRow(dat);
        }
        es.btnCancelar.addActionListener(this);
        es.setVisible(true);
    }//GEN-LAST:event_cancelarActionPerformed

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
            java.util.logging.Logger.getLogger(CancelarOrden.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CancelarOrden.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CancelarOrden.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CancelarOrden.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                CancelarOrden dialog = new CancelarOrden(new javax.swing.JFrame(), true);
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
    private rojerusan.RSTableMetro Tabla1;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JMenuItem cancelar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblX;
    private RSMaterialComponent.RSRadioButtonMaterial orden;
    private javax.swing.JPanel panelX;
    private RSMaterialComponent.RSRadioButtonMaterial requisicion;
    public RSMaterialComponent.RSTextFieldIconDos txtOrden;
    // End of variables declaration//GEN-END:variables

    @Override
    public void actionPerformed(ActionEvent e) {
        if(es != null){
            if(e.getSource() == es.btnCancelar){
                int opc = JOptionPane.showConfirmDialog(this, "¿Estas seguro de cancelar estos articulos?");
                if(opc == 0){
                    try{
                        Connection con;
                        Conexion con1 = new Conexion();
                        con = con1.getConnection();
                        int n = 0;
                        for (int i = 0; i < Tabla1.getSelectedRows().length; i++) {
                            String sql = "update requisiciones set OC = ?, Notas = ? where Id = ?";
                            PreparedStatement pst = con.prepareStatement(sql);

                            pst.setString(1, "CANCELADO");
                            pst.setString(2, "CANCELADO");
                            pst.setString(3, Tabla1.getValueAt(Tabla1.getSelectedRows()[i], 0).toString());

                            n += pst.executeUpdate();
                        }

                        if(n > 0){
                            JOptionPane.showMessageDialog(this, "Articulos cancelados");
                            es.dispose();
                            dispose();
                        }

                    }catch(SQLException ex){
                        JOptionPane.showMessageDialog(this, "ERROR: "+ex,"ERROR",JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        }
    }
}
