package VentanaEmergente.Almacen;

import Conexiones.Conexion;
import java.awt.Color;
import java.awt.GridLayout;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Stack;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class guardar extends javax.swing.JDialog {

    public JLabel lblCodigo[];
    public Stack<String> id;
    public JFormattedTextField txtFormato[];
    public String requisicion;
    
    public void getLabel(int i, JPanel panel, String text){
        lblCodigo[i] = new javax.swing.JLabel();
        lblCodigo[i].setFont(new java.awt.Font("Lexend", 1, 12)); // NOI18N
        lblCodigo[i].setForeground(new java.awt.Color(51, 51, 51));
        lblCodigo[i].setText(text + ": ");
        panel.add(lblCodigo[i]);
    }
    
    public void getFormated(int i, JPanel panel){
        txtFormato[i] = new javax.swing.JFormattedTextField();
        txtFormato[i].setBackground(new java.awt.Color(255, 255, 255));
        txtFormato[i].setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(204, 204, 204)));
        txtFormato[i].setForeground(new java.awt.Color(51, 51, 51));
        try {
            txtFormato[i].setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("U-#-#")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txtFormato[i].setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtFormato[i].setFont(new java.awt.Font("Lexend", 0, 12)); // NOI18N
        panel.add(txtFormato[i]);
    }
    
    public final void addFormato(Stack<String> codigo){
        lblCodigo = new JLabel[codigo.size()];
        txtFormato = new JFormattedTextField[codigo.size()];
        for (int i = 0; i < codigo.size(); i++) {
            JPanel panel = new JPanel();
            panel.setBackground(new java.awt.Color(255, 255, 255));
            panel.setPreferredSize(new java.awt.Dimension(600, 30));
            panel.setLayout(new GridLayout());
            getLabel(i, panel, codigo.get(i));
            getFormated(i, panel);
            java.awt.GridBagConstraints gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = i;
            gridBagConstraints.insets = new java.awt.Insets(12, 10, 12, 10);
            panelCodigos.add(panel, gridBagConstraints);
        }
    }
    
    public boolean verificarText(){
        boolean band = false;
        for (JFormattedTextField txtFormato1 : txtFormato) {
            if (txtFormato1.getText().equals(" - - ")) {
                band = true;
            }
        }
        return band;
    }
    
    public guardar(java.awt.Frame parent, boolean modal, Stack<String> codigo, String requisicion, Stack<String> id) {
        super(parent, modal);
        initComponents();
        addFormato(codigo);
        this.id = id;
        this.requisicion = requisicion;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        panelGuardar = new javax.swing.JPanel();
        btnGuardar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        panelCodigos = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        panelAsignar = new javax.swing.JPanel();
        btnAsignar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(700, 800));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new java.awt.BorderLayout());

        jLabel12.setFont(new java.awt.Font("Lexend", 1, 18)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(51, 153, 255));
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setText("Informacion requisicion");
        jPanel1.add(jLabel12, java.awt.BorderLayout.NORTH);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new java.awt.BorderLayout());

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        panelGuardar.setBackground(new java.awt.Color(51, 153, 255));

        btnGuardar.setFont(new java.awt.Font("Lexend", 1, 12)); // NOI18N
        btnGuardar.setForeground(new java.awt.Color(255, 255, 255));
        btnGuardar.setText("Guardar");
        btnGuardar.setBorderPainted(false);
        btnGuardar.setContentAreaFilled(false);
        btnGuardar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnGuardar.setFocusPainted(false);
        btnGuardar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnGuardarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnGuardarMouseExited(evt);
            }
        });
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });
        panelGuardar.add(btnGuardar);

        jPanel3.add(panelGuardar);

        jPanel2.add(jPanel3, java.awt.BorderLayout.PAGE_END);

        jScrollPane1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 1, true));

        panelCodigos.setBackground(new java.awt.Color(255, 255, 255));
        panelCodigos.setLayout(new java.awt.GridBagLayout());
        jScrollPane1.setViewportView(panelCodigos);

        jPanel2.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));

        panelAsignar.setBackground(new java.awt.Color(51, 153, 255));

        btnAsignar.setFont(new java.awt.Font("Lexend", 1, 12)); // NOI18N
        btnAsignar.setForeground(new java.awt.Color(255, 255, 255));
        btnAsignar.setText("Asignar a todos");
        btnAsignar.setBorderPainted(false);
        btnAsignar.setContentAreaFilled(false);
        btnAsignar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAsignar.setFocusPainted(false);
        btnAsignar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnAsignarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnAsignarMouseExited(evt);
            }
        });
        btnAsignar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAsignarActionPerformed(evt);
            }
        });
        panelAsignar.add(btnAsignar);

        jPanel6.add(panelAsignar);

        jPanel2.add(jPanel6, java.awt.BorderLayout.PAGE_START);

        jPanel1.add(jPanel2, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnGuardarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGuardarMouseEntered
        panelGuardar.setBackground(new Color(0,102,204));
    }//GEN-LAST:event_btnGuardarMouseEntered

    private void btnGuardarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGuardarMouseExited
        panelGuardar.setBackground(new Color(51,153,255));
    }//GEN-LAST:event_btnGuardarMouseExited

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        if(verificarText()){
            JOptionPane.showMessageDialog(this, "Deben estar llenos todos los campos de ubicacion","Advertencia",JOptionPane.ERROR_MESSAGE);
        }else{
            try{
                Connection con;
                Conexion con1 = new Conexion();
                con = con1.getConnection();
                String sql = "update requisiciones set Ubicacion = ? where Id = ?";
                PreparedStatement pst = con.prepareStatement(sql);
                
                int n = 0;
                for (int i = 0; i < txtFormato.length; i++) {
                    pst.setString(1, txtFormato[i].getText());
                    pst.setString(2, id.get(i));

                    n += pst.executeLargeUpdate();
                }

                if(n > 0){
                    JOptionPane.showMessageDialog(this, "DATOS GUARDADOS");
                    this.dispose();
                }else{
                    JOptionPane.showMessageDialog(this, "DATOS NO GUARDADOS","ERROR",JOptionPane.ERROR_MESSAGE);
                }

            }catch(SQLException e){
                JOptionPane.showMessageDialog(this, "ERROR: "+e,"ERROR",JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnAsignarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAsignarMouseEntered
        panelAsignar.setBackground(new Color(0,102,204));
    }//GEN-LAST:event_btnAsignarMouseEntered

    private void btnAsignarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAsignarMouseExited
       panelAsignar.setBackground(new Color(51,153,255));
    }//GEN-LAST:event_btnAsignarMouseExited

    private void btnAsignarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAsignarActionPerformed
        
    }//GEN-LAST:event_btnAsignarActionPerformed

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
            java.util.logging.Logger.getLogger(guardar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(guardar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(guardar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(guardar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                guardar dialog = new guardar(new javax.swing.JFrame(), true, null, "4272", null);
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
    private javax.swing.JButton btnAsignar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel panelAsignar;
    private javax.swing.JPanel panelCodigos;
    private javax.swing.JPanel panelGuardar;
    // End of variables declaration//GEN-END:variables


}
