package VentanaEmergente.Calendario;

import Conexiones.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class HabilitarCierre extends java.awt.Dialog {
    
    String idAgenda;

    public HabilitarCierre(java.awt.Frame parent, boolean modal, String idAgenda) {
        super(parent, modal);
        initComponents();
        this.idAgenda = idAgenda;
        this.setLocationRelativeTo(parent);
        jLabel1.setText(idAgenda);
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel1 = new javax.swing.JPanel();
        habilitar = new javax.swing.JCheckBox();
        jLabel1 = new javax.swing.JLabel();
        btnGuardar = new javax.swing.JButton();

        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                closeDialog(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new java.awt.GridBagLayout());

        habilitar.setBackground(new java.awt.Color(255, 255, 255));
        habilitar.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        habilitar.setForeground(new java.awt.Color(51, 51, 51));
        habilitar.setText("Habilitar fecha de cierre para usuario");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(8, 0, 8, 0);
        jPanel1.add(habilitar, gridBagConstraints);

        jLabel1.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 102, 255));
        jLabel1.setText("idAgenda");
        jPanel1.add(jLabel1, new java.awt.GridBagConstraints());

        btnGuardar.setBackground(new java.awt.Color(255, 102, 0));
        btnGuardar.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        btnGuardar.setForeground(new java.awt.Color(255, 255, 255));
        btnGuardar.setText("Guardar");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        jPanel1.add(btnGuardar, gridBagConstraints);

        add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void closeDialog(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_closeDialog
        setVisible(false);
        dispose();
    }//GEN-LAST:event_closeDialog

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        try{
            Connection con;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            String sql = "update agenda set HabilitarCierre = ? where idAgenda = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            
            pst.setBoolean(1, habilitar.isSelected());
            pst.setString(2, idAgenda);
            
            int n = pst.executeUpdate();
            
            if (n > 0) {
                JOptionPane.showMessageDialog(this, "Datos actualizados correctamente");
                dispose();
            }
            
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "Error: " + e,"Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnGuardarActionPerformed

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                HabilitarCierre dialog = new HabilitarCierre(new java.awt.Frame(), true, "");
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
    private javax.swing.JButton btnGuardar;
    private javax.swing.JCheckBox habilitar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
