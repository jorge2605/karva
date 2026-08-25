package VentanaEmergente.Compras;

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
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import scrollPane.ScrollBarCustom;

public class Agrupar extends javax.swing.JDialog {

    String numEmpleado;
    
    public void existe(String numRequi){
        try{
            Connection con;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            String sql = "select * from agrupacion where NumRequisicion like '"+numRequi+"'";
            ResultSet rs = st.executeQuery(sql);
            DefaultTableModel miModelo = (DefaultTableModel) Tabla1.getModel();
            String dat[] = new String[2];
            String id = "";
            while(rs.next()){
                id = rs.getString("IdAgrupar");
                dat[0] = rs.getString("NumRequisicion");
            }
            String sql2 = "select * from agrupacion where IdAgrupar like '"+id+"'";
            Statement st2 = con.createStatement();
            ResultSet rs2 = st2.executeQuery(sql2);
            while(rs2.next()){
                dat[0] = rs2.getString("NumRequisicion");
                miModelo.addRow(dat);
            }
            if(!id.equals("")){
                panelId.setVisible(true);
                lblId.setText(id);
            }else{
                panelId.setVisible(false);
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "ERROR: "+e,"ERROR",JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public Agrupar(java.awt.Frame parent, boolean modal, String numEmpleado) {
        super(parent, modal);
        initComponents();
        Tabla1.getTableHeader().setFont(new Font("Roboto", Font.BOLD, 14));
        Tabla1.getTableHeader().setOpaque(false);
        Tabla1.getTableHeader().setBackground(new Color(102,153,255));
        Tabla1.getTableHeader().setForeground(Color.white);
        Tabla1.setRowHeight(25);
        Tabla1.setShowGrid(false);
        
        this.numEmpleado = numEmpleado;
        
        jScrollPane2.getViewport().setBackground(new Color(255,255,255));
        jScrollPane2.setVerticalScrollBar(new ScrollBarCustom(new Color(0,165,255)));
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupMenu1 = new javax.swing.JPopupMenu();
        Eliminar = new javax.swing.JMenuItem();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        Tabla1 = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        panelId = new javax.swing.JPanel();
        lblId = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        btnGuardar = new RSMaterialComponent.RSButtonMaterialRipple();

        Eliminar.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        Eliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/eliminar (1).png"))); // NOI18N
        Eliminar.setText("        Eliminar requisicion      ");
        Eliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EliminarActionPerformed(evt);
            }
        });
        jPopupMenu1.add(Eliminar);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new java.awt.BorderLayout(15, 15));

        Tabla1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Requisicion"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        Tabla1.setComponentPopupMenu(jPopupMenu1);
        Tabla1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Tabla1MouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(Tabla1);

        jPanel1.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new java.awt.BorderLayout(0, 15));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jLabel9.setFont(new java.awt.Font("Roboto", 1, 24)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 102, 204));
        jLabel9.setText("Agrupar requisiciones");
        jPanel3.add(jLabel9);

        jPanel2.add(jPanel3, java.awt.BorderLayout.CENTER);

        panelId.setBackground(new java.awt.Color(153, 204, 255));

        lblId.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        lblId.setForeground(new java.awt.Color(255, 255, 255));
        lblId.setText("ID");
        panelId.add(lblId);

        jPanel2.add(panelId, java.awt.BorderLayout.SOUTH);

        jPanel1.add(jPanel2, java.awt.BorderLayout.NORTH);

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        btnGuardar.setText("Guardar");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });
        jPanel4.add(btnGuardar);

        jPanel1.add(jPanel4, java.awt.BorderLayout.PAGE_END);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void Tabla1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Tabla1MouseClicked

    }//GEN-LAST:event_Tabla1MouseClicked

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        try{
            Connection con;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            
            if(panelId.isVisible()){
                String sql = "insert into agrupacion (NumRequisicion, IdAgrupar) values(?,?)";
                PreparedStatement pst = con.prepareStatement(sql);
                int n = 0;
                for (int i = 0; i < Tabla1.getRowCount(); i++) {
                    String requi = Tabla1.getValueAt(i, 0).toString();
                    String sql2 = "select * from agrupacion where NumRequisicion like '"+requi+"'";
                    ResultSet rs = st.executeQuery(sql2);
                    String value = null;
                    while(rs.next()){
                        value = rs.getString("NumRequisicion");
                    }
                    if(value == null){
                        pst.setString(1, requi);
                        pst.setString(2, lblId.getText());
                        
                        n = pst.executeUpdate();
                    }
                }
                
                if(n > 0){
                    JOptionPane.showMessageDialog(this, "Datos guardados");
                    dispose();
                }
            }else{
                String sql = "insert into agrupar (Fecha, Estado, NumEmpleado) values(?,?,?)";
                PreparedStatement pst = con.prepareStatement(sql);
                
                Date d = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                
                pst.setString(1, sdf.format(d));
                pst.setString(2, "ACTIVO");
                pst.setString(3, numEmpleado);
                
                int n = pst.executeUpdate();
                
                String sql4 = "select * from agrupar";
                ResultSet rs4 = st.executeQuery(sql4);
                String id = "";
                while(rs4.next()){
                    id = rs4.getString("Id");
                }
                
                String sql2 = "insert into agrupacion (NumRequisicion, IdAgrupar) values(?,?)";
                PreparedStatement pst2 = con.prepareStatement(sql2);
                int n2 = 0;
                for (int i = 0; i < Tabla1.getRowCount(); i++) {
                    String requi = Tabla1.getValueAt(i, 0).toString();
                    String sql3 = "select * from agrupacion where NumRequisicion like '"+requi+"'";
                    ResultSet rs = st.executeQuery(sql3);
                    String value = null;
                    while(rs.next()){
                        value = rs.getString("NumRequisicion");
                    }
                    if(value == null){
                        pst2.setString(1, requi);
                        pst2.setString(2, id);
                        
                        n2 = pst2.executeUpdate();
                    }
                }
                if(n > 0 && n2 > 0){
                    JOptionPane.showMessageDialog(this,"Datos Guardados","Guardado",JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                }
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "ERROR: "+e,"ERROR",JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void EliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EliminarActionPerformed
        String requis = "";
        for (int i = 0; i < Tabla1.getSelectedRows().length; i++) {
            requis = requis + Tabla1.getValueAt(Tabla1.getSelectedRows()[i], 0).toString() + "\n";
        }
        int opc = JOptionPane.showConfirmDialog(this, "¿Estas seguro de eliminar requisicion(es):\n"
                + "\n"
                + ""+requis+"?");
        if(opc == 0){
            try{
                Connection con;
                Conexion con1 = new Conexion();
                con = con1.getConnection();
                String sql = "delete from agrupacion where NumRequisicion = ?";
                PreparedStatement pst = con.prepareStatement(sql);
                
                int n = 0;
                
                for (int i = 0; i < Tabla1.getSelectedRows().length; i++) {
                    pst.setString(1, Tabla1.getValueAt(Tabla1.getSelectedRows()[i], 0).toString());
                    n = pst.executeUpdate();
                }

                if(n > 0){
                    JOptionPane.showMessageDialog(this, "Requisicion eliminada de grupo");
                }
                
                DefaultTableModel miModelo = (DefaultTableModel) Tabla1.getModel();
                for (int i = Tabla1.getSelectedRows().length-1; i >= 0 ; i--) {
                    miModelo.removeRow(Tabla1.getSelectedRows()[i]);
                }
                
            }catch(SQLException e){
                JOptionPane.showMessageDialog(this, "ERROR: "+e,"ERROR",JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_EliminarActionPerformed

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
            java.util.logging.Logger.getLogger(Agrupar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Agrupar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Agrupar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Agrupar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Agrupar dialog = new Agrupar(new javax.swing.JFrame(), true,"");
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
    private javax.swing.JMenuItem Eliminar;
    public javax.swing.JTable Tabla1;
    public RSMaterialComponent.RSButtonMaterialRipple btnGuardar;
    public javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JScrollPane jScrollPane2;
    public javax.swing.JLabel lblId;
    private javax.swing.JPanel panelId;
    // End of variables declaration//GEN-END:variables
}
