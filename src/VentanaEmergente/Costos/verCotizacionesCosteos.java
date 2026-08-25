package VentanaEmergente.Costos;

import Conexiones.Conexion;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JButton;
import javax.swing.JOptionPane;

public class verCotizacionesCosteos extends javax.swing.JDialog {

    private int buttonCount = 0;
    private String idCoti = null;
    
    public String getCotizacion(){
        setVisible(true);
        return idCoti;
    }
    
    public void limpiarPanel(){
        buttonPanel.removeAll();
        revalidate();
        repaint();
    }
    
    public final void agregarCotizaciones(String opc){
        try{
            Connection con;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            String sql = "select * from costeoparte " + opc + " order by idcosteoparte desc";
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
                String id = rs.getString("idcosteoparte");
                String cliente = rs.getString("Empresa");
                String fecha = rs.getString("Fecha");
                addButton(id,cliente,fecha);
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "Error: "+e,"Error",JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void addButton(String id, String cliente, String fecha) {
        buttonCount++;
        JButton button = new JButton();
        button.setBackground(new java.awt.Color(255, 255, 255));
        button.setContentAreaFilled(false);
        button.setBorder(javax.swing.BorderFactory.createCompoundBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 5, true), new javax.swing.border.LineBorder(new java.awt.Color(230, 230, 230), 1, true)));
        button.setForeground(new java.awt.Color(51, 153, 255));
        button.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        button.setFont(new java.awt.Font("Lexend", 0, 14));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setName(id);
        button.setText(
                  "<html>"
                          + "<style>"
                          + "p{"
                          + "padding-left:9px;"
                          + "padding-bottom: 5px;"
                          + "}"
                          + "</style>"
                          + "<p style='color: rgb(79, 79, 79);'>"
                          + "#" + id
                          + "</p>"
                          + "<p style='color: rgb(48, 91, 133); width: 300px;'>"
                          + cliente
                          + "</p>"
                          + "<p style='color: rgb(200,200,200);'>"
                          + fecha
                          + "</p>");
        
        // Configurar el tamaño máximo horizontal del botón para que se extienda al ancho del panel
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, button.getPreferredSize().height));
        
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                idCoti = button.getName();
                dispose();
            }
        });
        
        buttonPanel.add(button);
        
        buttonPanel.revalidate();
        buttonPanel.repaint();
    }
    
    public verCotizacionesCosteos(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        jScrollPane2.getVerticalScrollBar().setUnitIncrement(15);
        txtBuscar.requestFocus();
        agregarCotizaciones("");
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        txtBuscar = new javax.swing.JTextField();
        btnGuardar = new scrollPane.BotonRedondo();
        jLabel1 = new javax.swing.JLabel();
        btnGuardar1 = new scrollPane.BotonRedondo();
        jScrollPane2 = new javax.swing.JScrollPane();
        buttonPanel = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(457, 571));
        getContentPane().setLayout(new java.awt.BorderLayout(10, 10));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new java.awt.BorderLayout(10, 10));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new java.awt.BorderLayout(20, 5));

        txtBuscar.setBackground(new java.awt.Color(255, 255, 255));
        txtBuscar.setFont(new java.awt.Font("Lexend", 0, 14)); // NOI18N
        txtBuscar.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(204, 204, 204)));
        txtBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBuscarActionPerformed(evt);
            }
        });
        jPanel2.add(txtBuscar, java.awt.BorderLayout.CENTER);

        btnGuardar.setBackground(new java.awt.Color(255, 255, 255));
        btnGuardar.setForeground(new java.awt.Color(51, 153, 255));
        btnGuardar.setText("Buscar");
        btnGuardar.setBorderColor(new java.awt.Color(51, 153, 255));
        btnGuardar.setBorderColorSelected(new java.awt.Color(0, 102, 204));
        btnGuardar.setColor(new java.awt.Color(51, 153, 255));
        btnGuardar.setFont(new java.awt.Font("Lexend", 0, 14)); // NOI18N
        btnGuardar.setPreferredSize(new java.awt.Dimension(100, 35));
        btnGuardar.setThickness(3);
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });
        jPanel2.add(btnGuardar, java.awt.BorderLayout.EAST);

        jLabel1.setFont(new java.awt.Font("Lexend", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 102, 204));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Buscar");
        jPanel2.add(jLabel1, java.awt.BorderLayout.PAGE_START);

        btnGuardar1.setBackground(new java.awt.Color(255, 255, 255));
        btnGuardar1.setForeground(new java.awt.Color(255, 255, 255));
        btnGuardar1.setText("Buscar");
        btnGuardar1.setBorderColor(new java.awt.Color(255, 255, 255));
        btnGuardar1.setBorderColorSelected(new java.awt.Color(255, 255, 255));
        btnGuardar1.setColor(new java.awt.Color(255, 255, 255));
        btnGuardar1.setFont(new java.awt.Font("Lexend", 0, 14)); // NOI18N
        btnGuardar1.setPreferredSize(new java.awt.Dimension(100, 35));
        btnGuardar1.setThickness(3);
        btnGuardar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardar1ActionPerformed(evt);
            }
        });
        jPanel2.add(btnGuardar1, java.awt.BorderLayout.WEST);

        jPanel1.add(jPanel2, java.awt.BorderLayout.PAGE_START);

        jScrollPane2.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 1, true));

        buttonPanel.setBackground(new java.awt.Color(255, 255, 255));
        buttonPanel.setLayout(new javax.swing.BoxLayout(buttonPanel, javax.swing.BoxLayout.Y_AXIS));
        jScrollPane2.setViewportView(buttonPanel);

        jPanel1.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        limpiarPanel();
        agregarCotizaciones("where idcosteoparte like '" + txtBuscar.getText() +"'");
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnGuardar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardar1ActionPerformed
        limpiarPanel();
        agregarCotizaciones("where idcosteoparte like '" + txtBuscar.getText() +"'");
    }//GEN-LAST:event_btnGuardar1ActionPerformed

    private void txtBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBuscarActionPerformed
        limpiarPanel();
        agregarCotizaciones("where idcosteoparte like '" + txtBuscar.getText() +"'");
    }//GEN-LAST:event_txtBuscarActionPerformed

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
            java.util.logging.Logger.getLogger(verCotizacionesCosteos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(verCotizacionesCosteos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(verCotizacionesCosteos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(verCotizacionesCosteos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                verCotizacionesCosteos dialog = new verCotizacionesCosteos(new javax.swing.JFrame(), true);
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
    private scrollPane.BotonRedondo btnGuardar;
    private scrollPane.BotonRedondo btnGuardar1;
    private javax.swing.JPanel buttonPanel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField txtBuscar;
    // End of variables declaration//GEN-END:variables
}