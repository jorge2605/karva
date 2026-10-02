package VentanaEmergente.Maquinados;

import Conexiones.Conexion;
import java.awt.Frame;
import java.awt.KeyboardFocusManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class empleado extends javax.swing.JDialog {

    int x,y;
    String numero;
    Frame frame;
    
    public String getEmpleado(){
        txtEmpleado.requestFocusInWindow();
        setVisible(true);
        return numero;
    }
    
    public empleado(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        frame = parent;
    }
    
    private void validarYEnviar() {
        String input = txtEmpleado.getText().trim();

        // 1. Validación: Que no esté vacío
        if (input.equals("")) {
            JOptionPane.showMessageDialog(this, 
                    "Por favor, ingresa tu número de empleado.", 
                    "Campo vacío", 
                    JOptionPane.WARNING_MESSAGE);
            txtEmpleado.requestFocusInWindow();
            return;
        }

        int numeroInt;

        // 2. Validación intentar convertir a entero
        try {
            numeroInt = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            // Si el usuario metió letras, saltará aquí
            JOptionPane.showMessageDialog(this, 
                    "El número de empleado solo debe contener dígitos numéricos.", 
                    "Formato incorrecto", 
                    JOptionPane.ERROR_MESSAGE);
            txtEmpleado.selectAll();
            txtEmpleado.requestFocusInWindow();
            return; // Detiene la ejecución para que no intente ir a la BD
        }

        // 3. Verificar la existencia en la base de datos
        Conexion con1 = new Conexion();
        try (Connection con = con1.getConnection()) {

            String sql = "SELECT COUNT(*) FROM registroempleados WHERE numempleado = ?"; 

            try (PreparedStatement pst = con.prepareStatement(sql)) {
                pst.setInt(1, numeroInt);

                try (ResultSet rs = pst.executeQuery()) {
                    if (rs.next() && rs.getInt(1) > 0) {
                        // Si existe, guardamos el valor en el String 'numero' para Maquinados
                        numero = String.valueOf(numeroInt);
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(this, 
                                "El número de empleado " + input + " no existe.", 
                                "Empleado no encontrado", 
                                JOptionPane.ERROR_MESSAGE);
                        txtEmpleado.selectAll();
                        txtEmpleado.requestFocusInWindow();
                    }
                }
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, 
                    "Error al conectar con la base de datos: " + e.getMessage(), 
                    "Error de Conexión", 
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtEmpleado = new javax.swing.JTextField();
        btnIngresar = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        pnlX = new javax.swing.JPanel();
        btnX = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setPreferredSize(new java.awt.Dimension(529, 167));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.setLayout(new java.awt.BorderLayout(10, 10));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        java.awt.GridBagLayout jPanel2Layout = new java.awt.GridBagLayout();
        jPanel2Layout.columnWeights = new double[] {1.0};
        jPanel2Layout.rowWeights = new double[] {0.0, 0.0, 1.0};
        jPanel2.setLayout(jPanel2Layout);

        jLabel1.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 102, 204));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("<html>\n<div style = 'width:300px; text-align:center;'>\nIngresa tu numero de empleado\n</div>");
        jLabel1.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                jLabel1MouseDragged(evt);
            }
        });
        jLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabel1MousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jLabel1MouseReleased(evt);
            }
        });
        jPanel2.add(jLabel1, new java.awt.GridBagConstraints());

        txtEmpleado.setBackground(new java.awt.Color(255, 255, 255));
        txtEmpleado.setFont(new java.awt.Font("Lexend", 1, 24)); // NOI18N
        txtEmpleado.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtEmpleado.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(204, 204, 204)));
        txtEmpleado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEmpleadoActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 50, 0, 50);
        jPanel2.add(txtEmpleado, gridBagConstraints);

        btnIngresar.setBackground(new java.awt.Color(0, 102, 204));
        btnIngresar.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        btnIngresar.setForeground(new java.awt.Color(255, 255, 255));
        btnIngresar.setText("         Ingresar         ");
        btnIngresar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnIngresar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIngresarActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 2;
        jPanel2.add(btnIngresar, gridBagConstraints);

        jPanel1.add(jPanel2, java.awt.BorderLayout.CENTER);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setLayout(new java.awt.BorderLayout());

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        pnlX.setBackground(new java.awt.Color(255, 255, 255));

        btnX.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        btnX.setText(" X ");
        btnX.setBorder(null);
        btnX.setBorderPainted(false);
        btnX.setContentAreaFilled(false);
        btnX.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnX.setFocusPainted(false);
        btnX.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXActionPerformed(evt);
            }
        });
        pnlX.add(btnX);

        jPanel4.add(pnlX);

        jPanel3.add(jPanel4, java.awt.BorderLayout.PAGE_START);

        jPanel1.add(jPanel3, java.awt.BorderLayout.NORTH);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void txtEmpleadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEmpleadoActionPerformed
        validarYEnviar();
    }//GEN-LAST:event_txtEmpleadoActionPerformed

    private void btnIngresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIngresarActionPerformed
        validarYEnviar();
    }//GEN-LAST:event_btnIngresarActionPerformed

    private void jLabel1MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel1MouseReleased

    private void jLabel1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MousePressed
        x = evt.getX();
        y = evt.getY();
    }//GEN-LAST:event_jLabel1MousePressed

    private void jLabel1MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseDragged
        int xx = evt.getXOnScreen();
        int yy = evt.getYOnScreen();
        this.setLocation(xx - (x), yy - y);
    }//GEN-LAST:event_jLabel1MouseDragged

    private void btnXActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXActionPerformed
        
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
            java.util.logging.Logger.getLogger(empleado.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(empleado.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(empleado.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(empleado.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                empleado dialog = new empleado(new javax.swing.JFrame(), true);
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
    public javax.swing.JButton btnIngresar;
    public javax.swing.JButton btnX;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel pnlX;
    public javax.swing.JTextField txtEmpleado;
    // End of variables declaration//GEN-END:variables
}
