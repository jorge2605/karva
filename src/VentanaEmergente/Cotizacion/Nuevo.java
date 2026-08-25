package VentanaEmergente.Cotizacion;

import Conexiones.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

public class Nuevo extends javax.swing.JDialog {

    String numEmpleado;
    
    public Nuevo(java.awt.Frame parent, boolean modal, String numEmpleado) {
        super(parent, modal);
        initComponents();
        this.numEmpleado = numEmpleado;
    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        txtMaterial = new RSMaterialComponent.RSComboBoxMaterial();
        txtTipo = new RSMaterialComponent.RSComboBoxMaterial();
        txtAncho = new rojeru_san.RSMTextFull();
        txtAlto = new rojeru_san.RSMTextFull();
        txtLargo = new rojeru_san.RSMTextFull();
        txtExtra = new rojeru_san.RSMTextFull();
        jPanel3 = new javax.swing.JPanel();
        txtPrecio = new RSComponentShade.RSFormatFieldShade();
        jPanel4 = new javax.swing.JPanel();
        rSButtonRoundRipple3 = new rojeru_san.rsbutton.RSButtonRoundRipple();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new java.awt.BorderLayout());

        jLabel1.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Agregar material");
        jPanel1.add(jLabel1, java.awt.BorderLayout.PAGE_START);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        txtMaterial.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "ALUMINIO", "CRS", "A2", "DELRIN", "DELRRIN", "INOX", "INOXIDABLE", "NYLON", "TEFLON", "POLICARBONATO", "PERFIL" }));
        jPanel2.add(txtMaterial);

        txtTipo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "SOLERA", "BARRA", "REDONDO", "CUADRADO", "CUAD", "ANGULO", "TUBULAR", "TUBO", "PLACA", "EXTRUIDO" }));
        jPanel2.add(txtTipo);

        txtAncho.setBackground(new java.awt.Color(255, 255, 255));
        txtAncho.setForeground(new java.awt.Color(51, 51, 51));
        txtAncho.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        txtAncho.setNextFocusableComponent(txtAlto);
        txtAncho.setPlaceholder("Ancho");
        txtAncho.setPreferredSize(new java.awt.Dimension(100, 42));
        txtAncho.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtAnchoActionPerformed(evt);
            }
        });
        jPanel2.add(txtAncho);

        txtAlto.setBackground(new java.awt.Color(255, 255, 255));
        txtAlto.setForeground(new java.awt.Color(51, 51, 51));
        txtAlto.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        txtAlto.setNextFocusableComponent(txtLargo);
        txtAlto.setPlaceholder("Alto");
        txtAlto.setPreferredSize(new java.awt.Dimension(100, 42));
        txtAlto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtAltoActionPerformed(evt);
            }
        });
        jPanel2.add(txtAlto);

        txtLargo.setBackground(new java.awt.Color(255, 255, 255));
        txtLargo.setForeground(new java.awt.Color(51, 51, 51));
        txtLargo.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        txtLargo.setNextFocusableComponent(txtExtra);
        txtLargo.setPlaceholder("Largo");
        txtLargo.setPreferredSize(new java.awt.Dimension(100, 42));
        txtLargo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtLargoActionPerformed(evt);
            }
        });
        jPanel2.add(txtLargo);

        txtExtra.setBackground(new java.awt.Color(255, 255, 255));
        txtExtra.setForeground(new java.awt.Color(51, 51, 51));
        txtExtra.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        txtExtra.setNextFocusableComponent(txtPrecio);
        txtExtra.setPlaceholder("Extra");
        txtExtra.setPreferredSize(new java.awt.Dimension(100, 42));
        txtExtra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtExtraActionPerformed(evt);
            }
        });
        jPanel2.add(txtExtra);

        jPanel1.add(jPanel2, java.awt.BorderLayout.CENTER);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setLayout(new java.awt.BorderLayout(15, 15));

        txtPrecio.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        txtPrecio.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter()));
        txtPrecio.setPlaceholder("Precio");
        jPanel3.add(txtPrecio, java.awt.BorderLayout.PAGE_START);

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        rSButtonRoundRipple3.setBackground(new java.awt.Color(204, 204, 204));
        rSButtonRoundRipple3.setText("Guardar");
        rSButtonRoundRipple3.setColorHover(new java.awt.Color(51, 51, 51));
        rSButtonRoundRipple3.setPreferredSize(new java.awt.Dimension(80, 40));
        rSButtonRoundRipple3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rSButtonRoundRipple3ActionPerformed(evt);
            }
        });
        jPanel4.add(rSButtonRoundRipple3);

        jPanel3.add(jPanel4, java.awt.BorderLayout.CENTER);

        jPanel1.add(jPanel3, java.awt.BorderLayout.PAGE_END);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtAnchoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtAnchoActionPerformed
        
    }//GEN-LAST:event_txtAnchoActionPerformed

    private void txtAltoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtAltoActionPerformed
        
    }//GEN-LAST:event_txtAltoActionPerformed

    private void txtLargoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtLargoActionPerformed
        
    }//GEN-LAST:event_txtLargoActionPerformed

    private void txtExtraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtExtraActionPerformed
        
    }//GEN-LAST:event_txtExtraActionPerformed

    private void rSButtonRoundRipple3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rSButtonRoundRipple3ActionPerformed
        if(txtPrecio.getText().equals("")){
            JOptionPane.showMessageDialog(this, "Debes llenar el precio");
        }
        try{
            Connection con;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            String sql = "insert into requisiciones (NumRequisicion, Codigo, Descripcion, Cantidad, Requisitor, UM, Proveedor, "
                    + "Precio, OC) values(?,?,?,?,?,?,?,?,?)";
            PreparedStatement pst = con.prepareStatement(sql);
            
            String descripcion = txtMaterial.getSelectedItem().toString() + " " + txtTipo.getSelectedItem().toString()
                    + " " + txtAncho.getText() + " " + txtAlto.getText() + " " + txtLargo.getText() + " " + txtExtra.getText();
            
            pst.setString(1, "");
            pst.setString(2, descripcion);
            pst.setString(3, descripcion);
            pst.setString(4, "");
            pst.setString(5, numEmpleado);
            pst.setString(6, "");
            pst.setString(7, "");
            pst.setString(8, txtPrecio.getText());
            pst.setString(9, "");
            
            int n = pst.executeUpdate();
            
            String sql2 = "insert into cotizaciones (Material, Tipo, Precio, Ancho, Alto, Largo, Extra) values(?,?,?,?,?,?,?)";
            PreparedStatement pst2 = con.prepareStatement(sql2);
            
            pst2.setString(1, txtMaterial.getSelectedItem().toString());
            pst2.setString(2, txtTipo.getSelectedItem().toString());
            pst2.setString(3, txtPrecio.getText());
            pst2.setString(4, txtAncho.getText());
            pst2.setString(5, txtAlto.getText());
            pst2.setString(6, txtLargo.getText());
            pst2.setString(7, txtExtra.getText());
            
            int n2 = pst2.executeUpdate();
            
            if(n > 0 && n2 > 0){
                JOptionPane.showMessageDialog(this, "Guardado");
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "ERROR: "+e,"ERROR",JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_rSButtonRoundRipple3ActionPerformed

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
            java.util.logging.Logger.getLogger(Nuevo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Nuevo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Nuevo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Nuevo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Nuevo dialog = new Nuevo(new javax.swing.JFrame(), true,"");
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
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private rojeru_san.rsbutton.RSButtonRoundRipple rSButtonRoundRipple3;
    private rojeru_san.RSMTextFull txtAlto;
    private rojeru_san.RSMTextFull txtAncho;
    private rojeru_san.RSMTextFull txtExtra;
    private rojeru_san.RSMTextFull txtLargo;
    private RSMaterialComponent.RSComboBoxMaterial txtMaterial;
    private RSComponentShade.RSFormatFieldShade txtPrecio;
    private RSMaterialComponent.RSComboBoxMaterial txtTipo;
    // End of variables declaration//GEN-END:variables
}
