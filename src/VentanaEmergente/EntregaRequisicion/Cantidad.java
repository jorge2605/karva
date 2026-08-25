package VentanaEmergente.EntregaRequisicion;

import Conexiones.Conexion;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class Cantidad extends javax.swing.JDialog {

    public String cantidadEntregada(){
        return lblCantidad.getText();
    }
    
    public void verCantidad(String id){
        Connection con;
        Conexion con1 = new Conexion();
        try {
            con = con1.getConnection();
            Statement st = con.createStatement();
            String sql = "SELECT * FROM towi.requisiciones where Id like '"+id+"'";
            ResultSet rs = st.executeQuery(sql);
            String cR = "", cE = "", cantidad = "";
            while(rs.next()){
                cR = rs.getString("CantRecibida");
                cE = rs.getString("CantidadEntregada");
                cantidad = rs.getString("Cantidad");
            }
            if(cR == null){
                cR = cantidad;
            }
            if(cE == null){
                cE = "0";
            }
            
            String sql2 = "select * from detrequisicion where IdArticulo like '"+id+"' and Encontrado != 'SI '";
            Statement st2 = con.createStatement();
            ResultSet rs2 = st2.executeQuery(sql2);
            double cP = 0;
            String cp = null;
            while(rs2.next()){
                cp = rs2.getString("Cantidad");
                if(cp != null){
                cP += Double.parseDouble(cp);
            }
            }
            
            lblCR.setText(cR);
            lblCE.setText(cE);
            lblCP.setText(String.valueOf(cP));
            
        } catch (SQLException ex) {
            Logger.getLogger(Cantidad.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public Cantidad(java.awt.Frame parent, boolean modal, String id) {
        super(parent, modal);
        initComponents();
        verCantidad(id);
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
        jPanel10 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jPanel13 = new javax.swing.JPanel();
        lblCR = new javax.swing.JLabel();
        jPanel16 = new javax.swing.JPanel();
        jPanel17 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jPanel18 = new javax.swing.JPanel();
        lblCP = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jPanel14 = new javax.swing.JPanel();
        lblCE = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jPanel15 = new javax.swing.JPanel();
        lblCantidad = new javax.swing.JFormattedTextField();
        jPanel8 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(1053, 725));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new java.awt.BorderLayout());

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setBackground(new java.awt.Color(0, 165, 252));
        jLabel1.setFont(new java.awt.Font("Roboto", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 153, 255));
        jLabel1.setText("INSERTAR CANTIDAD ENTREGADA");
        jPanel3.add(jLabel1);

        jPanel2.add(jPanel3);

        jPanel1.add(jPanel2, java.awt.BorderLayout.PAGE_START);

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setLayout(new java.awt.GridLayout(1, 3, 5, 10));

        jPanel5.setBackground(new java.awt.Color(228, 241, 255));
        jPanel5.setLayout(new java.awt.BorderLayout());

        jPanel10.setBackground(new java.awt.Color(228, 241, 255));
        jPanel10.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 10, 15));

        jLabel2.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(51, 153, 255));
        jLabel2.setText("CANTIDAD RECIBIDA");
        jPanel10.add(jLabel2);

        jPanel5.add(jPanel10, java.awt.BorderLayout.PAGE_START);

        jPanel13.setBackground(new java.awt.Color(228, 241, 255));
        jPanel13.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 5, 50));

        lblCR.setBackground(new java.awt.Color(0, 0, 0));
        lblCR.setFont(new java.awt.Font("Roboto", 1, 48)); // NOI18N
        lblCR.setForeground(new java.awt.Color(51, 153, 255));
        lblCR.setText("0");
        jPanel13.add(lblCR);

        jPanel5.add(jPanel13, java.awt.BorderLayout.CENTER);

        jPanel4.add(jPanel5);

        jPanel16.setBackground(new java.awt.Color(228, 241, 255));
        jPanel16.setLayout(new java.awt.BorderLayout());

        jPanel17.setBackground(new java.awt.Color(186, 219, 255));
        jPanel17.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 10, 15));

        jLabel5.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(51, 153, 255));
        jLabel5.setText("CANTIDAD PEDIDA");
        jPanel17.add(jLabel5);

        jPanel16.add(jPanel17, java.awt.BorderLayout.PAGE_START);

        jPanel18.setBackground(new java.awt.Color(186, 219, 255));
        jPanel18.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 5, 50));

        lblCP.setBackground(new java.awt.Color(0, 0, 0));
        lblCP.setFont(new java.awt.Font("Roboto", 1, 48)); // NOI18N
        lblCP.setForeground(new java.awt.Color(51, 153, 255));
        lblCP.setText("0");
        jPanel18.add(lblCP);

        jPanel16.add(jPanel18, java.awt.BorderLayout.CENTER);

        jPanel4.add(jPanel16);

        jPanel6.setBackground(new java.awt.Color(153, 204, 255));
        jPanel6.setLayout(new java.awt.BorderLayout());

        jPanel11.setBackground(new java.awt.Color(118, 184, 249));
        jPanel11.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 10, 15));

        jLabel3.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(228, 241, 255));
        jLabel3.setText("CANTIDAD ENTREGADA");
        jPanel11.add(jLabel3);

        jPanel6.add(jPanel11, java.awt.BorderLayout.PAGE_START);

        jPanel14.setBackground(new java.awt.Color(118, 184, 249));
        jPanel14.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 5, 50));

        lblCE.setBackground(new java.awt.Color(0, 0, 0));
        lblCE.setFont(new java.awt.Font("Roboto", 1, 48)); // NOI18N
        lblCE.setForeground(new java.awt.Color(228, 241, 255));
        lblCE.setText("0");
        jPanel14.add(lblCE);

        jPanel6.add(jPanel14, java.awt.BorderLayout.CENTER);

        jPanel4.add(jPanel6);

        jPanel7.setBackground(new java.awt.Color(51, 153, 255));
        jPanel7.setLayout(new java.awt.BorderLayout());

        jPanel12.setBackground(new java.awt.Color(51, 153, 255));
        jPanel12.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 10, 15));

        jLabel4.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("CANTIDAD A ENTREGAR");
        jPanel12.add(jLabel4);

        jPanel7.add(jPanel12, java.awt.BorderLayout.PAGE_START);

        jPanel15.setBackground(new java.awt.Color(51, 153, 255));
        jPanel15.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 5, 50));

        lblCantidad.setBackground(new java.awt.Color(51, 153, 255));
        lblCantidad.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(255, 255, 255)));
        lblCantidad.setForeground(new java.awt.Color(255, 255, 255));
        lblCantidad.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter()));
        lblCantidad.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        lblCantidad.setFont(new java.awt.Font("Roboto", 1, 48)); // NOI18N
        lblCantidad.setPreferredSize(new java.awt.Dimension(120, 60));
        lblCantidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lblCantidadActionPerformed(evt);
            }
        });
        jPanel15.add(lblCantidad);

        jPanel7.add(jPanel15, java.awt.BorderLayout.CENTER);

        jPanel4.add(jPanel7);

        jPanel1.add(jPanel4, java.awt.BorderLayout.CENTER);

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));
        jPanel9.setForeground(new java.awt.Color(0, 165, 252));

        jButton1.setBackground(new java.awt.Color(255, 255, 255));
        jButton1.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jButton1.setForeground(new java.awt.Color(0, 165, 252));
        jButton1.setText("AGREGAR");
        jButton1.setBorder(null);
        jButton1.setBorderPainted(false);
        jButton1.setContentAreaFilled(false);
        jButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton1.setFocusPainted(false);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel9.add(jButton1);

        jPanel8.add(jPanel9);

        jPanel1.add(jPanel8, java.awt.BorderLayout.PAGE_END);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        try{
            double cant, cantE;
            cant = Double.parseDouble(lblCR.getText()) + Double.parseDouble(lblCE.getText());
            cantE = Double.parseDouble(lblCantidad.getText());
            if(cantE > cant){
                JOptionPane.showMessageDialog(this, "LA CANTIDAD INGRESADA NO DEBE SER MAYOR A LA REAL", "ADVERTENCIA", JOptionPane.ERROR_MESSAGE);
                lblCantidad.setText("");
            }else if(cantE < 1){
                JOptionPane.showMessageDialog(this, "LA CANTIDAD INGRESADA NO DEBE SER MENOR A 0", "ADVERTENCIA", JOptionPane.ERROR_MESSAGE);
                lblCantidad.setText("");
            }else{
                dispose();
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(this, "INGRESA CANTIDAD CORRECTA","ADVERTENCIA",JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void lblCantidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lblCantidadActionPerformed
        if(Double.parseDouble(lblCantidad.getText()) == 0){
            JOptionPane.showMessageDialog(this, "LA CANTIDAD NO PUEDE SER 0", "ADVERTENCIA",JOptionPane.WARNING_MESSAGE);
            lblCantidad.setText("");
        }else{
        try{
            double cant, cantE;
            cant = Double.parseDouble(lblCR.getText())  + Double.parseDouble(lblCE.getText());
            cantE = Double.parseDouble(lblCantidad.getText()) + Double.parseDouble(lblCP.getText());
            if(cantE > cant){
                JOptionPane.showMessageDialog(this, "LA CANTIDAD INGRESADA NO ES CORRECTA", "ADVERTENCIA", JOptionPane.ERROR_MESSAGE);
                lblCantidad.setText("");
            }else if(cantE < 1){
                JOptionPane.showMessageDialog(this, "LA CANTIDAD INGRESADA NO DEBE SER MENOR O IGUAL A 0", "ADVERTENCIA", JOptionPane.ERROR_MESSAGE);
                lblCantidad.setText("");
            }else{
                dispose();
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(this, "INGRESA CANTIDAD CORRECTA","ADVERTENCIA",JOptionPane.WARNING_MESSAGE);
        }
        }
    }//GEN-LAST:event_lblCantidadActionPerformed

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
            java.util.logging.Logger.getLogger(Cantidad.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Cantidad.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Cantidad.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Cantidad.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Cantidad dialog = new Cantidad(new javax.swing.JFrame(), true, "");
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
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
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
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JLabel lblCE;
    private javax.swing.JLabel lblCP;
    private javax.swing.JLabel lblCR;
    private javax.swing.JFormattedTextField lblCantidad;
    // End of variables declaration//GEN-END:variables
}
