package VentanaEmergente.Costos;
import Conexiones.Conexion;
import com.mxrck.autocompleter.TextAutoCompleter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.swing.JOptionPane;
/**
 *
 * @author jesparza
 */
public class EditarMateriales extends javax.swing.JFrame {
    TextAutoCompleter ma;
    String Fecha;
    boolean band =  false;
    public EditarMateriales() {
        initComponents();
        this.setDefaultCloseOperation(EditarMateriales.DISPOSE_ON_CLOSE);
        autoCompletar();
        Fecha();
        filtro();
    }
    public final void autoCompletar(){
        ma = new TextAutoCompleter(txtMaterial);
        try{
            Connection con;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            String sql = "select Distinct Material from Materiales";
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
                ma.addItem(rs.getString("Material"));
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "ERROR: "+e,"ERROR",JOptionPane.ERROR_MESSAGE);
        }
    }
    public void Fecha(){
        LocalDate fecha=LocalDate.now();
        DateTimeFormatter ft=DateTimeFormatter.ofPattern("yyyy/MM/dd");
        Fecha=fecha.format(ft);
    }
    private void filtro() {
        txtDensidad.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                char c = evt.getKeyChar();
                String textoActual = txtDensidad.getText();
                if (!(Character.isDigit(c) || (c == '.' && !textoActual.contains(".")))) {
                    evt.consume(); // Ignorar el carácter si no es válido
                }
            }
        });
         txtPrecio.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                char c = evt.getKeyChar();
                String texto = txtPrecio.getText();
                if (!(Character.isDigit(c) || (c == '.' && !texto.contains(".")))) {
                    evt.consume(); // Ignorar el carácter si no es válido
                }
            }
        });
    }
    public void Borrar(){
        txtMaterial.setText("");
        txtDensidad.setText("");
        txtPrecio.setText("");
    }
    public void Guardar(){
        if(txtMaterial.getText().isEmpty()){
            JOptionPane.showMessageDialog(this, "EL CAMPO MATERIAL ESTA VACIO","ADVERTENCIA",JOptionPane.WARNING_MESSAGE);
        }
        else if(txtDensidad.getText().isEmpty()){
            JOptionPane.showMessageDialog(this, "EL CAMPO DENSIDAD ESTA VACIO","ADVERTENCIA",JOptionPane.WARNING_MESSAGE);
        }
        else if(txtPrecio.getText().isEmpty()){
            JOptionPane.showMessageDialog(this, "EL CAMPO PRECIO ESTA VACIO","ADVERTENCIA",JOptionPane.WARNING_MESSAGE);
        }
        else{
            try{
                Connection con2 = null;
                Conexion c = new Conexion();
                con2 = c.getConnection();
                Fecha();
                String sql = "INSERT INTO MATERIALES(Material,Precio,Densidad,Fecha) VALUES (?,?,?,?)";
                PreparedStatement ps=con2.prepareStatement(sql);
                ps.setString(1, txtMaterial.getText());
                ps.setString(2,txtPrecio.getText());
                ps.setString(3,txtDensidad.getText());
                ps.setString(4, Fecha);
                int n = ps.executeUpdate();
                if(n>0){
                    JOptionPane.showMessageDialog(this, "DATOS GUARDADOS");
                    Borrar();
                }
            }
            catch(SQLException e){
              JOptionPane.showMessageDialog(this, "ERROR AL GUARDAR DATOS"+e);
            }
        }
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel1 = new javax.swing.JPanel();
        txtMaterial = new RSMaterialComponent.RSTextFieldMaterial();
        txtPrecio = new RSMaterialComponent.RSTextFieldMaterial();
        txtDensidad = new RSMaterialComponent.RSTextFieldMaterial();
        jLabel1 = new javax.swing.JLabel();
        btnGuardar =  new scrollPane.BotonRedondo();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setLayout(new java.awt.GridBagLayout());

        txtMaterial.setForeground(new java.awt.Color(51, 51, 51));
        txtMaterial.setCaretColor(new java.awt.Color(102, 102, 102));
        txtMaterial.setColorMaterial(new java.awt.Color(204, 204, 204));
        txtMaterial.setFont(new java.awt.Font("Lexend", 1, 14)); // NOI18N
        txtMaterial.setPhColor(new java.awt.Color(15,80,232));
        txtMaterial.setPlaceholder("Material");
        txtMaterial.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMaterialActionPerformed(evt);
            }
        });
        txtMaterial.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtMaterialKeyTyped(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.ipadx = 237;
        gridBagConstraints.ipady = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(37, 81, 0, 67);
        jPanel1.add(txtMaterial, gridBagConstraints);

        txtPrecio.setForeground(new java.awt.Color(51, 51, 51));
        txtPrecio.setCaretColor(new java.awt.Color(102, 102, 102));
        txtPrecio.setColorMaterial(new java.awt.Color(204, 204, 204));
        txtPrecio.setFont(new java.awt.Font("Lexend", 1, 14)); // NOI18N
        txtPrecio.setPhColor(new java.awt.Color(15,80,232));
        txtPrecio.setPlaceholder("Precio del Material");
        txtPrecio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPrecioActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.ipadx = 239;
        gridBagConstraints.ipady = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(43, 81, 0, 67);
        jPanel1.add(txtPrecio, gridBagConstraints);

        txtDensidad.setForeground(new java.awt.Color(51, 51, 51));
        txtDensidad.setCaretColor(new java.awt.Color(102, 102, 102));
        txtDensidad.setColorMaterial(new java.awt.Color(204, 204, 204));
        txtDensidad.setFont(new java.awt.Font("Lexend", 1, 14)); // NOI18N
        txtDensidad.setPhColor(new java.awt.Color(15,80,232));
        txtDensidad.setPlaceholder("Densidad del Material");
        txtDensidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDensidadActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.ipadx = 239;
        gridBagConstraints.ipady = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(42, 81, 0, 67);
        jPanel1.add(txtDensidad, gridBagConstraints);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 51, 255));
        jLabel1.setText("Editar Materiales");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(20, 131, 0, 0);
        jPanel1.add(jLabel1, gridBagConstraints);

        btnGuardar.setForeground(new java.awt.Color(250, 0, 0));
        btnGuardar.setText("Guardar");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(42, 150, 40, 0);
        jPanel1.add(btnGuardar, gridBagConstraints);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 487, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 434, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtMaterialActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMaterialActionPerformed
        
        Thread hilo = new Thread() {
            public void run() {
                if(band){
                    if(txtMaterial.getText().equals("")){
                        JOptionPane.showMessageDialog(null, "Debes introducir el Material","Advertencia",JOptionPane.WARNING_MESSAGE);
                    }
                }
            }
        };
        hilo.start();
    }//GEN-LAST:event_txtMaterialActionPerformed

    private void txtPrecioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPrecioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPrecioActionPerformed

    private void txtDensidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDensidadActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDensidadActionPerformed

    private void txtMaterialKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtMaterialKeyTyped
        char c = evt.getKeyChar();
        if ((c < 'a' || c > 'z') && (c < 'A') && (c > 'Z')) {
            evt.consume();//Solo dejo ingresar letras minúsculas y  mayusculas (no numeros ni caracteres)
        }//Todo lo que ingresa se pone em mayúscula
        String cad = ("" + c).toUpperCase();
        c = cad.charAt(0);
        evt.setKeyChar(c);
    }//GEN-LAST:event_txtMaterialKeyTyped

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        Guardar();
    }//GEN-LAST:event_btnGuardarActionPerformed

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
            java.util.logging.Logger.getLogger(EditarMateriales.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(EditarMateriales.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(EditarMateriales.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(EditarMateriales.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new EditarMateriales().setVisible(true);
            }
        });
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnGuardar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private RSMaterialComponent.RSTextFieldMaterial txtDensidad;
    private RSMaterialComponent.RSTextFieldMaterial txtMaterial;
    private RSMaterialComponent.RSTextFieldMaterial txtPrecio;
    // End of variables declaration//GEN-END:variables
}