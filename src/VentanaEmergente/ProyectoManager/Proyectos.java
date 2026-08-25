package VentanaEmergente.ProyectoManager;

import Conexiones.Conexion;
import com.mxrck.autocompleter.TextAutoCompleter;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

public class Proyectos extends javax.swing.JDialog {

    TextAutoCompleter ac;
    
    public void autocompletar(){
        ac = new TextAutoCompleter(txtProyecto);
        try{
            Connection con;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            String sql = "select Proyecto from proyectos";
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
                ac.addItem(rs.getString("Proyecto"));
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "ERROR: ","ERROR",JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public Proyectos(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setBackground(new Color(0,0,0,0));
        autocompletar();
        txtProyecto.requestFocus();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelRound1 = new scrollPane.PanelRound();
        txtProyecto = new rojeru_san.RSMTextFull();
        jPanel1 = new javax.swing.JPanel();
        btnSelec = new javax.swing.JButton();
        btnX = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setPreferredSize(new java.awt.Dimension(500, 50));
        addWindowFocusListener(new java.awt.event.WindowFocusListener() {
            public void windowGainedFocus(java.awt.event.WindowEvent evt) {
            }
            public void windowLostFocus(java.awt.event.WindowEvent evt) {
                formWindowLostFocus(evt);
            }
        });

        panelRound1.setRoundBottomLeft(20);
        panelRound1.setRoundBottomRight(20);
        panelRound1.setRoundTopLeft(20);
        panelRound1.setRoundTopRight(20);
        panelRound1.setLayout(new java.awt.BorderLayout());

        txtProyecto.setBackground(new java.awt.Color(255, 255, 255));
        txtProyecto.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtProyecto.setPlaceholder("INGRESA PROYECTO");
        txtProyecto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtProyectoActionPerformed(evt);
            }
        });
        txtProyecto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtProyectoKeyReleased(evt);
            }
        });
        panelRound1.add(txtProyecto, java.awt.BorderLayout.CENTER);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new java.awt.BorderLayout());

        btnSelec.setBackground(new java.awt.Color(255, 255, 255));
        btnSelec.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        btnSelec.setForeground(new java.awt.Color(0, 0, 0));
        btnSelec.setText("Marcar como SIN SELECCIONAR");
        btnSelec.setBorder(null);
        btnSelec.setBorderPainted(false);
        btnSelec.setContentAreaFilled(false);
        btnSelec.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSelec.setFocusPainted(false);
        btnSelec.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnSelecMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnSelecMouseExited(evt);
            }
        });
        jPanel1.add(btnSelec, java.awt.BorderLayout.WEST);

        btnX.setBackground(new java.awt.Color(255, 255, 255));
        btnX.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        btnX.setForeground(new java.awt.Color(0, 0, 0));
        btnX.setText("X");
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
        jPanel1.add(btnX, java.awt.BorderLayout.EAST);

        panelRound1.add(jPanel1, java.awt.BorderLayout.PAGE_START);

        getContentPane().add(panelRound1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowLostFocus(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowLostFocus
        dispose();
    }//GEN-LAST:event_formWindowLostFocus

    private void txtProyectoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtProyectoKeyReleased
        if(evt.getKeyCode() == KeyEvent.VK_ESCAPE){
            dispose();
        }
    }//GEN-LAST:event_txtProyectoKeyReleased

    private void txtProyectoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtProyectoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtProyectoActionPerformed

    private void btnSelecMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSelecMouseEntered
        btnSelec.setForeground(Color.red);
    }//GEN-LAST:event_btnSelecMouseEntered

    private void btnSelecMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSelecMouseExited
        btnSelec.setForeground(Color.black);
    }//GEN-LAST:event_btnSelecMouseExited

    private void btnXMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnXMouseEntered
        btnX.setForeground(Color.red);
    }//GEN-LAST:event_btnXMouseEntered

    private void btnXMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnXMouseExited
        btnX.setForeground(Color.black);
    }//GEN-LAST:event_btnXMouseExited

    private void btnXActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXActionPerformed
        dispose();
    }//GEN-LAST:event_btnXActionPerformed

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
            java.util.logging.Logger.getLogger(Proyectos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Proyectos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Proyectos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Proyectos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Proyectos dialog = new Proyectos(new javax.swing.JFrame(), true);
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
    public javax.swing.JButton btnSelec;
    private javax.swing.JButton btnX;
    private javax.swing.JPanel jPanel1;
    private scrollPane.PanelRound panelRound1;
    public rojeru_san.RSMTextFull txtProyecto;
    // End of variables declaration//GEN-END:variables
}
