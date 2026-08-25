package VentanaEmergente.CalidadNew;

import java.awt.Color;
import java.awt.Frame;
import java.awt.Point;
import java.util.prefs.Preferences;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import pruebas.Inicio1;

public class Enviar extends javax.swing.JDialog {

    private static void cargarUbicacionVentana(Frame ventana) {
        Preferences prefs = Preferences.userNodeForPackage(Inicio1.class);
        int x = prefs.getInt("posX", 100);
        int y = prefs.getInt("posY", 100);
        ventana.setLocation(x, y);
    }

    private static void guardarUbicacionVentana(Frame ventana) {
        Preferences prefs = Preferences.userNodeForPackage(Inicio1.class);
        Point ubicacion = ventana.getLocation();
        prefs.putInt("posX", ubicacion.x);
        prefs.putInt("posY", ubicacion.y);
    }
    
    public Enviar(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        JFrame f = (JFrame) JOptionPane.getFrameForComponent(this);
        cargarUbicacionVentana(f);
    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        PanelFresa = new javax.swing.JPanel();
        btnFresa = new javax.swing.JButton();
        PanelAcabados = new javax.swing.JPanel();
        btnAcabados = new javax.swing.JButton();
        PanelTorno = new javax.swing.JPanel();
        btnTorno = new javax.swing.JButton();
        PanelCnc = new javax.swing.JPanel();
        btnCnc = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        PanelCortes = new javax.swing.JPanel();
        btnCortes = new javax.swing.JButton();
        PanelTrata = new javax.swing.JPanel();
        btnTrata = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(0, 165, 252));

        jLabel1.setFont(new java.awt.Font("Century Gothic", 1, 48)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("CALIDAD - RECHAZO - RETRABAJO");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(107, 107, 107))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 10, 990, -1));

        jLabel2.setFont(new java.awt.Font("Roboto Medium", 0, 24)); // NOI18N
        jLabel2.setText("SELECCIONA A DONDE SE ENVIRA EL PLANO");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 90, -1, -1));

        jLabel3.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N
        jLabel3.setText("RECHAZO");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 290, -1, -1));

        PanelFresa.setBackground(new java.awt.Color(255, 255, 255));

        btnFresa.setBackground(new java.awt.Color(255, 255, 255));
        btnFresa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/fresadora (2).png"))); // NOI18N
        btnFresa.setBorder(null);
        btnFresa.setBorderPainted(false);
        btnFresa.setContentAreaFilled(false);
        btnFresa.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnFresa.setFocusPainted(false);
        btnFresa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnFresaMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnFresaMouseExited(evt);
            }
        });
        btnFresa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFresaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout PanelFresaLayout = new javax.swing.GroupLayout(PanelFresa);
        PanelFresa.setLayout(PanelFresaLayout);
        PanelFresaLayout.setHorizontalGroup(
            PanelFresaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelFresaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnFresa, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        PanelFresaLayout.setVerticalGroup(
            PanelFresaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelFresaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnFresa, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.add(PanelFresa, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 170, 90, -1));

        PanelAcabados.setBackground(new java.awt.Color(255, 255, 255));

        btnAcabados.setBackground(new java.awt.Color(255, 255, 255));
        btnAcabados.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/mecanico.png"))); // NOI18N
        btnAcabados.setBorder(null);
        btnAcabados.setBorderPainted(false);
        btnAcabados.setContentAreaFilled(false);
        btnAcabados.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnAcabados.setFocusPainted(false);
        btnAcabados.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnAcabadosMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnAcabadosMouseExited(evt);
            }
        });

        javax.swing.GroupLayout PanelAcabadosLayout = new javax.swing.GroupLayout(PanelAcabados);
        PanelAcabados.setLayout(PanelAcabadosLayout);
        PanelAcabadosLayout.setHorizontalGroup(
            PanelAcabadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelAcabadosLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnAcabados, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        PanelAcabadosLayout.setVerticalGroup(
            PanelAcabadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelAcabadosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnAcabados, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.add(PanelAcabados, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 170, -1, -1));

        PanelTorno.setBackground(new java.awt.Color(255, 255, 255));

        btnTorno.setBackground(new java.awt.Color(255, 255, 255));
        btnTorno.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/torno_1.png"))); // NOI18N
        btnTorno.setBorder(null);
        btnTorno.setBorderPainted(false);
        btnTorno.setContentAreaFilled(false);
        btnTorno.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnTorno.setFocusPainted(false);
        btnTorno.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnTornoMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnTornoMouseExited(evt);
            }
        });

        javax.swing.GroupLayout PanelTornoLayout = new javax.swing.GroupLayout(PanelTorno);
        PanelTorno.setLayout(PanelTornoLayout);
        PanelTornoLayout.setHorizontalGroup(
            PanelTornoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelTornoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnTorno, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        PanelTornoLayout.setVerticalGroup(
            PanelTornoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelTornoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnTorno, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.add(PanelTorno, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 170, -1, -1));

        PanelCnc.setBackground(new java.awt.Color(255, 255, 255));

        btnCnc.setBackground(new java.awt.Color(255, 255, 255));
        btnCnc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/perforacion.png"))); // NOI18N
        btnCnc.setBorder(null);
        btnCnc.setBorderPainted(false);
        btnCnc.setContentAreaFilled(false);
        btnCnc.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnCnc.setFocusPainted(false);
        btnCnc.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnCncMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnCncMouseExited(evt);
            }
        });

        javax.swing.GroupLayout PanelCncLayout = new javax.swing.GroupLayout(PanelCnc);
        PanelCnc.setLayout(PanelCncLayout);
        PanelCncLayout.setHorizontalGroup(
            PanelCncLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelCncLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnCnc, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        PanelCncLayout.setVerticalGroup(
            PanelCncLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelCncLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnCnc, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.add(PanelCnc, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 170, -1, -1));

        jLabel4.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N
        jLabel4.setText("RE-TRABAJO");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 130, -1, -1));

        PanelCortes.setBackground(new java.awt.Color(255, 255, 255));

        btnCortes.setBackground(new java.awt.Color(255, 255, 255));
        btnCortes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/corte (1).png"))); // NOI18N
        btnCortes.setBorder(null);
        btnCortes.setBorderPainted(false);
        btnCortes.setContentAreaFilled(false);
        btnCortes.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnCortes.setFocusPainted(false);
        btnCortes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnCortesMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnCortesMouseExited(evt);
            }
        });

        javax.swing.GroupLayout PanelCortesLayout = new javax.swing.GroupLayout(PanelCortes);
        PanelCortes.setLayout(PanelCortesLayout);
        PanelCortesLayout.setHorizontalGroup(
            PanelCortesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelCortesLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnCortes, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        PanelCortesLayout.setVerticalGroup(
            PanelCortesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelCortesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnCortes, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.add(PanelCortes, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 330, -1, -1));

        PanelTrata.setBackground(new java.awt.Color(255, 255, 255));

        btnTrata.setBackground(new java.awt.Color(255, 255, 255));
        btnTrata.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/quimico.png"))); // NOI18N
        btnTrata.setBorder(null);
        btnTrata.setBorderPainted(false);
        btnTrata.setContentAreaFilled(false);
        btnTrata.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnTrata.setFocusPainted(false);
        btnTrata.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnTrataMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnTrataMouseExited(evt);
            }
        });

        javax.swing.GroupLayout PanelTrataLayout = new javax.swing.GroupLayout(PanelTrata);
        PanelTrata.setLayout(PanelTrataLayout);
        PanelTrataLayout.setHorizontalGroup(
            PanelTrataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelTrataLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnTrata, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        PanelTrataLayout.setVerticalGroup(
            PanelTrataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelTrataLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnTrata, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.add(PanelTrata, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 170, -1, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 1060, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 521, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnFresaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnFresaMouseEntered
        PanelFresa.setBackground(new Color(202,202,202));
        
        
    }//GEN-LAST:event_btnFresaMouseEntered

    private void btnFresaMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnFresaMouseExited
        PanelFresa.setBackground(new Color(255,255,255));
        
    }//GEN-LAST:event_btnFresaMouseExited

    private void btnCncMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCncMouseEntered
        PanelCnc.setBackground(new Color(202,202,202));
    }//GEN-LAST:event_btnCncMouseEntered

    private void btnCncMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCncMouseExited
        PanelCnc.setBackground(new Color(255,255,255));
    }//GEN-LAST:event_btnCncMouseExited

    private void btnTornoMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnTornoMouseEntered
        PanelTorno.setBackground(new Color(202,202,202));
    }//GEN-LAST:event_btnTornoMouseEntered

    private void btnTornoMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnTornoMouseExited
        PanelTorno.setBackground(new Color(255,255,255));
    }//GEN-LAST:event_btnTornoMouseExited

    private void btnAcabadosMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAcabadosMouseEntered
        PanelAcabados.setBackground(new Color(202,202,202));
    }//GEN-LAST:event_btnAcabadosMouseEntered

    private void btnAcabadosMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAcabadosMouseExited
        PanelAcabados.setBackground(new Color(255,255,255));
    }//GEN-LAST:event_btnAcabadosMouseExited

    private void btnCortesMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCortesMouseEntered
        PanelCortes.setBackground(new Color(202,202,202));
    }//GEN-LAST:event_btnCortesMouseEntered

    private void btnCortesMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCortesMouseExited
        PanelCortes.setBackground(new Color(255,255,255));
    }//GEN-LAST:event_btnCortesMouseExited

    private void btnTrataMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnTrataMouseEntered
        PanelTrata.setBackground(new Color(202,202,202));
    }//GEN-LAST:event_btnTrataMouseEntered

    private void btnTrataMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnTrataMouseExited
        PanelTrata.setBackground(new Color(255,255,255));
    }//GEN-LAST:event_btnTrataMouseExited

    private void btnFresaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFresaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnFresaActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        JFrame f = (JFrame) JOptionPane.getFrameForComponent(this);
        guardarUbicacionVentana(f);
    }//GEN-LAST:event_formWindowClosing

   
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
            java.util.logging.Logger.getLogger(Enviar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Enviar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Enviar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Enviar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Enviar dialog = new Enviar(new javax.swing.JFrame(), true);
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
    private javax.swing.JPanel PanelAcabados;
    private javax.swing.JPanel PanelCnc;
    private javax.swing.JPanel PanelCortes;
    private javax.swing.JPanel PanelFresa;
    private javax.swing.JPanel PanelTorno;
    private javax.swing.JPanel PanelTrata;
    public javax.swing.JButton btnAcabados;
    public javax.swing.JButton btnCnc;
    public javax.swing.JButton btnCortes;
    public javax.swing.JButton btnFresa;
    public javax.swing.JButton btnTorno;
    public javax.swing.JButton btnTrata;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    // End of variables declaration//GEN-END:variables
}
