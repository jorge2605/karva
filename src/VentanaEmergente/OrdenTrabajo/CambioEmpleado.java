package VentanaEmergente.OrdenTrabajo;

import Conexiones.Conexion;
import com.mxrck.autocompleter.TextAutoCompleter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

public class CambioEmpleado extends javax.swing.JDialog {

    public TextAutoCompleter au;
    public String empleado = null;
    
    public String getEmpleado() {
        setVisible(true);
        empleado = txtEmpleado.getText();
        return empleado;
    }
    
    public final void agregarEmpleados() {
        try {
            Connection con = new Conexion().getConnection();
            String sql = "select * from registroempleados order by Nombre";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            au = new TextAutoCompleter(txtEmpleado);
            while (rs.next()) {
                au.addItem(rs.getString("Nombre") + " " + rs.getString("Apellido") + " - " + rs.getString("NumEmpleado"));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al agregar nombres de empleados: " + e, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public CambioEmpleado(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        agregarEmpleados();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtEmpleado = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(492, 100));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new java.awt.BorderLayout(0, 10));

        jLabel1.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 102, 204));
        jLabel1.setText("Ingresar nombre de empleado");
        jPanel1.add(jLabel1, java.awt.BorderLayout.PAGE_START);

        txtEmpleado.setBackground(new java.awt.Color(255, 255, 255));
        txtEmpleado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEmpleadoActionPerformed(evt);
            }
        });
        jPanel1.add(txtEmpleado, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        txtEmpleado.setText("");
    }//GEN-LAST:event_formWindowClosing

    private void txtEmpleadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEmpleadoActionPerformed
        dispose();
    }//GEN-LAST:event_txtEmpleadoActionPerformed

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                CambioEmpleado dialog = new CambioEmpleado(new javax.swing.JFrame(), true);
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
    private javax.swing.JTextField txtEmpleado;
    // End of variables declaration//GEN-END:variables
}
