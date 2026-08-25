package VentanaEmergente.Inicio1;

import java.awt.Color;

public class InicioCostos extends javax.swing.JDialog {

    public InicioCostos(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel9 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        pnlX = new javax.swing.JPanel();
        btnX = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        btnCostos = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        btnEvaluacion = new javax.swing.JButton();
        lblNotiCostos = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        btnCosteo = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);

        jPanel9.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(222, 222, 222), 3, true));
        jPanel9.setLayout(new java.awt.BorderLayout());

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        pnlX.setBackground(new java.awt.Color(255, 255, 255));

        btnX.setBackground(new java.awt.Color(255, 255, 255));
        btnX.setFont(new java.awt.Font("Lexend", 1, 12)); // NOI18N
        btnX.setText(" X ");
        btnX.setBorder(null);
        btnX.setBorderPainted(false);
        btnX.setContentAreaFilled(false);
        btnX.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnX.setFocusPainted(false);
        btnX.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnXMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnXMouseExited(evt);
            }
        });
        btnX.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXActionPerformed(evt);
            }
        });
        pnlX.add(btnX);

        jPanel4.add(pnlX);

        jPanel9.add(jPanel4, java.awt.BorderLayout.PAGE_START);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new java.awt.BorderLayout());

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));

        jLabel12.setFont(new java.awt.Font("Lexend", 1, 18)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(0, 153, 0));
        jLabel12.setText("Selecciona una opcion");
        jPanel6.add(jLabel12);

        jPanel5.add(jPanel6);

        jPanel1.add(jPanel5, java.awt.BorderLayout.PAGE_START);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new java.awt.GridBagLayout());

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));

        btnCostos.setBackground(new java.awt.Color(255, 255, 255));
        btnCostos.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        btnCostos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/costos_64.png"))); // NOI18N
        btnCostos.setText("Costos");
        btnCostos.setBorder(null);
        btnCostos.setBorderPainted(false);
        btnCostos.setContentAreaFilled(false);
        btnCostos.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCostos.setFocusPainted(false);
        btnCostos.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnCostos.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        btnCostos.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jPanel7.add(btnCostos);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(17, 40, 17, 40);
        jPanel2.add(jPanel7, gridBagConstraints);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setLayout(new java.awt.BorderLayout());

        btnEvaluacion.setBackground(new java.awt.Color(255, 255, 255));
        btnEvaluacion.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        btnEvaluacion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/evaluacion_64_1.png"))); // NOI18N
        btnEvaluacion.setText("Evaluacion");
        btnEvaluacion.setBorder(null);
        btnEvaluacion.setBorderPainted(false);
        btnEvaluacion.setContentAreaFilled(false);
        btnEvaluacion.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEvaluacion.setFocusPainted(false);
        btnEvaluacion.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnEvaluacion.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        btnEvaluacion.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jPanel3.add(btnEvaluacion, java.awt.BorderLayout.CENTER);

        lblNotiCostos.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        lblNotiCostos.setForeground(new java.awt.Color(255, 255, 255));
        lblNotiCostos.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblNotiCostos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/CR.png"))); // NOI18N
        lblNotiCostos.setText("1");
        lblNotiCostos.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel3.add(lblNotiCostos, java.awt.BorderLayout.PAGE_START);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(17, 40, 17, 40);
        jPanel2.add(jPanel3, gridBagConstraints);

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));

        btnCosteo.setBackground(new java.awt.Color(255, 255, 255));
        btnCosteo.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        btnCosteo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/cotizaciones_64.png"))); // NOI18N
        btnCosteo.setText("Costeo");
        btnCosteo.setBorder(null);
        btnCosteo.setBorderPainted(false);
        btnCosteo.setContentAreaFilled(false);
        btnCosteo.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCosteo.setFocusPainted(false);
        btnCosteo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnCosteo.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        btnCosteo.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnCosteo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCosteoActionPerformed(evt);
            }
        });
        jPanel8.add(btnCosteo);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(17, 40, 17, 40);
        jPanel2.add(jPanel8, gridBagConstraints);

        jPanel1.add(jPanel2, java.awt.BorderLayout.CENTER);

        jPanel9.add(jPanel1, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel9, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnXActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXActionPerformed
        dispose();
    }//GEN-LAST:event_btnXActionPerformed

    private void btnXMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnXMouseEntered
        pnlX.setBackground(Color.red);
        btnX.setForeground(Color.white);
    }//GEN-LAST:event_btnXMouseEntered

    private void btnXMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnXMouseExited
        pnlX.setBackground(Color.white);
        btnX.setForeground(Color.black);
    }//GEN-LAST:event_btnXMouseExited

    private void btnCosteoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCosteoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnCosteoActionPerformed

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
            java.util.logging.Logger.getLogger(InicioCostos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(InicioCostos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(InicioCostos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(InicioCostos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                InicioCostos dialog = new InicioCostos(new javax.swing.JFrame(), true);
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
    public javax.swing.JButton btnCosteo;
    public javax.swing.JButton btnCostos;
    public javax.swing.JButton btnEvaluacion;
    public javax.swing.JButton btnX;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    public javax.swing.JLabel lblNotiCostos;
    private javax.swing.JPanel pnlX;
    // End of variables declaration//GEN-END:variables
}
