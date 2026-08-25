package VentanaEmergente.Pedidos;

import java.awt.Color;
import javax.swing.JOptionPane;
import pruebas.Pedidos;

public class cantidad extends javax.swing.JDialog {

    Pedidos pedidos;
    int con;
    
    public void insertar(){
        if(txtCantidad.getText().equals("")){
            
        }else{
            double f = Double.parseDouble(txtCantidad.getText());
            pedidos.cantidadNew[con] = f+"";
            if(f == 0.0){
                    pedidos.panel[con].setBackground(Color.red);
                    pedidos.botones[con].setText(pedidos.addCantidad(pedidos.botones[con].getText(), String.valueOf(f)));
                    
                    dispose();
                    pedidos.botones[con].setForeground(Color.white);
            } else if(f > 0 && f < Double.parseDouble(pedidos.cantidad[con])){
                    pedidos.panel[con].setBackground(Color.yellow);
                    pedidos.botones[con].setForeground(Color.black);
                    pedidos.botones[con].setText(pedidos.addCantidad(pedidos.botones[con].getText(), String.valueOf(f)));
                    dispose();
            }else if(Double.parseDouble(pedidos.cantidad[con]) == f){
                    pedidos.panel[con].setBackground(Color.green);
                    pedidos.botones[con].setForeground(Color.black);pedidos.botones[con].setText(pedidos.addCantidad(pedidos.botones[con].getText(), String.valueOf(f)));
                    dispose();
                    
            }else{
                JOptionPane.showMessageDialog(null, "CANTIDAD FUERA DE RANGO");
            }
            
        }
        dispose();
    }
    
    public cantidad(java.awt.Frame parent, boolean modal, int pos, Pedidos p) {
        super(parent, modal);
        initComponents();
        pedidos = p;
        con = pos;
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelSalir = new javax.swing.JPanel();
        lblSalir = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        panelSalir1 = new javax.swing.JPanel();
        lblSalir1 = new javax.swing.JLabel();
        txtCantidad = new rojeru_san.RSMTextFull();

        panelSalir.setBackground(new java.awt.Color(255, 255, 255));

        lblSalir.setFont(new java.awt.Font("Roboto Black", 1, 18)); // NOI18N
        lblSalir.setText("  X  ");
        lblSalir.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblSalir.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblSalirMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblSalirMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblSalirMouseExited(evt);
            }
        });
        panelSalir.add(lblSalir);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setPreferredSize(new java.awt.Dimension(278, 150));
        addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                formFocusLost(evt);
            }
        });
        addWindowStateListener(new java.awt.event.WindowStateListener() {
            public void windowStateChanged(java.awt.event.WindowEvent evt) {
                formWindowStateChanged(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(153, 153, 153)));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panelSalir1.setBackground(new java.awt.Color(255, 255, 255));

        lblSalir1.setFont(new java.awt.Font("Roboto Black", 1, 18)); // NOI18N
        lblSalir1.setText("  X  ");
        lblSalir1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblSalir1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblSalir1MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblSalir1MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblSalir1MouseExited(evt);
            }
        });
        panelSalir1.add(lblSalir1);

        jPanel1.add(panelSalir1, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 10, 39, 34));

        txtCantidad.setBackground(new java.awt.Color(255, 255, 255));
        txtCantidad.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCantidad.setFont(new java.awt.Font("Roboto Bold", 1, 18)); // NOI18N
        txtCantidad.setPlaceholder("INTRODUCE CANTIDAD");
        txtCantidad.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCantidadFocusLost(evt);
            }
        });
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
        jPanel1.add(txtCantidad, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 240, -1));

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void lblSalirMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSalirMouseClicked
        dispose();
    }//GEN-LAST:event_lblSalirMouseClicked

    private void lblSalirMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSalirMouseEntered
        panelSalir.setBackground(Color.red);
        lblSalir.setForeground(Color.white);
    }//GEN-LAST:event_lblSalirMouseEntered

    private void lblSalirMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSalirMouseExited
        panelSalir.setBackground(Color.white);
        lblSalir.setForeground(Color.black);
    }//GEN-LAST:event_lblSalirMouseExited

    private void lblSalir1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSalir1MouseClicked
        dispose();
    }//GEN-LAST:event_lblSalir1MouseClicked

    private void lblSalir1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSalir1MouseEntered
        panelSalir.setBackground(Color.red);
        lblSalir.setForeground(Color.white);
    }//GEN-LAST:event_lblSalir1MouseEntered

    private void lblSalir1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSalir1MouseExited
        panelSalir.setBackground(Color.white);
        lblSalir.setForeground(Color.black);
    }//GEN-LAST:event_lblSalir1MouseExited

    private void formWindowStateChanged(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowStateChanged
        dispose();
    }//GEN-LAST:event_formWindowStateChanged

    private void formFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_formFocusLost
        insertar();
    }//GEN-LAST:event_formFocusLost

    private void txtCantidadFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCantidadFocusLost
        insertar();
        
    }//GEN-LAST:event_txtCantidadFocusLost

    private void txtCantidadKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCantidadKeyTyped
        char c = evt.getKeyChar();
        if(c<'0'||c>'9') evt.consume();
    }//GEN-LAST:event_txtCantidadKeyTyped

    private void txtCantidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCantidadActionPerformed
        insertar();
    }//GEN-LAST:event_txtCantidadActionPerformed

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
            java.util.logging.Logger.getLogger(cantidad.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(cantidad.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(cantidad.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(cantidad.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                cantidad dialog = new cantidad(new javax.swing.JFrame(), true,0,null);
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
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblSalir;
    private javax.swing.JLabel lblSalir1;
    private javax.swing.JPanel panelSalir;
    private javax.swing.JPanel panelSalir1;
    private rojeru_san.RSMTextFull txtCantidad;
    // End of variables declaration//GEN-END:variables
}
