package VentanaEmergente.Cotizacion;

import Conexiones.Conexion;
import Modelo.javamail;
import VentanaEmergente.Ventas.correos;
import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class EnviarCorreoCotizaciones extends javax.swing.JDialog {
    
    public String correo;
    public String pass;
    public String coti;
    public File cotizacion;
    
    public final void verDatosCorreos(){
        try{
            Connection con;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            String sql = "select * from enviocorreos where Departamento like 'VENTAS'";
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
                txtUsuarios.setText(txtUsuarios.getText() + rs.getString("Correo") + ",");
            }
            
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "ERROR: "+e,"ERROR",JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public final void getCorreo(String numEmpleado) {
        try {
            Connection con;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            String sql = "select AES_DECRYPT(pass,'mi_llave'),correo,NumEmpleado from registroempleados where NumEmpleado like '" + numEmpleado + "'";
            ResultSet rs = st.executeQuery(sql);
            correo = "";
            pass = "";
            while (rs.next()) {
                pass = rs.getString("AES_DECRYPT(Pass,'mi_llave')");
                correo = rs.getString("Correo");
            }
            if (!pass.equals("") && !correo.equals("")) {
                btnCheck.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IconoC/cheque_16.png")));
                txtCorreo.setText(correo);
            }else {
                btnCheck.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/cerrar sesion.png")));
                btnEnviarCorreo.setEnabled(false);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "ERROR: " + e, "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public final void verGuardado() {
        Thread hilo = new Thread(() -> {
            try {
                lblguardado.setVisible(true);
                Thread.sleep(2000);
                lblguardado.setVisible(false);
            } catch (InterruptedException ex) {
                lblguardado.setVisible(false);
                Logger.getLogger(ConfUsuario.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        hilo.start();
    }
    
    public final void enviarCorreo(String coti) {
        javamail mail = new javamail();
        String copias[] = txtUsuarios.getText().split(",");
        mail.sendCotizacion(copias, lblCorreoCliente.getText(), "Cotizacion " + coti, coti, cotizacion, correo, pass, lblNombreCliente.getText(), txtNotas.getText());
    }
    
    public EnviarCorreoCotizaciones(java.awt.Frame parent, boolean modal, String numEmpleado) {
        super(parent, modal);
        initComponents();
        getCorreo(numEmpleado);
        verDatosCorreos();
        lblguardado.setVisible(false);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel1 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        lblCorreoCliente = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        lblNombreCliente = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtCorreo = new javax.swing.JTextField();
        btnCheck = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtNotas = new javax.swing.JTextArea();
        jLabel8 = new javax.swing.JLabel();
        txtUsuarios = new javax.swing.JTextField();
        btnUsuarios = new javax.swing.JButton();
        btnEnviarCorreo = new javax.swing.JButton();
        lblguardado = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new java.awt.BorderLayout());

        jLabel12.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(0, 165, 252));
        jLabel12.setText("            Configuracion de envio de correo");
        jPanel1.add(jLabel12, java.awt.BorderLayout.NORTH);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        java.awt.GridBagLayout jPanel2Layout = new java.awt.GridBagLayout();
        jPanel2Layout.columnWeights = new double[] {1.0, 1.0, 1.0};
        jPanel2.setLayout(jPanel2Layout);

        jLabel1.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 51, 51));
        jLabel1.setText("Correo cliente:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(10, 12, 10, 12);
        jPanel2.add(jLabel1, gridBagConstraints);

        lblCorreoCliente.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblCorreoCliente.setForeground(new java.awt.Color(51, 51, 51));
        lblCorreoCliente.setText("cliente");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        jPanel2.add(lblCorreoCliente, gridBagConstraints);

        jLabel3.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(51, 51, 51));
        jLabel3.setText("Empresa:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(10, 12, 10, 12);
        jPanel2.add(jLabel3, gridBagConstraints);

        lblNombreCliente.setText("cliente");
        lblNombreCliente.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblNombreCliente.setForeground(new java.awt.Color(51, 51, 51));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        jPanel2.add(lblNombreCliente, gridBagConstraints);

        jLabel5.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(51, 51, 51));
        jLabel5.setText("Correo de usuario:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(10, 12, 10, 12);
        jPanel2.add(jLabel5, gridBagConstraints);

        txtCorreo.setEditable(false);
        txtCorreo.setBackground(new java.awt.Color(255, 255, 255));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        jPanel2.add(txtCorreo, gridBagConstraints);

        btnCheck.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IconoC/cheque_16.png"))); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jPanel2.add(btnCheck, gridBagConstraints);

        jLabel7.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(51, 51, 51));
        jLabel7.setText("Agregar notas:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 3;
        gridBagConstraints.insets = new java.awt.Insets(10, 12, 10, 12);
        jPanel2.add(jLabel7, gridBagConstraints);

        txtNotas.setBackground(new java.awt.Color(255, 255, 255));
        txtNotas.setColumns(20);
        txtNotas.setRows(5);
        jScrollPane1.setViewportView(txtNotas);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 2;
        jPanel2.add(jScrollPane1, gridBagConstraints);

        jLabel8.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(51, 51, 51));
        jLabel8.setText("Usuarios CC:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 4;
        gridBagConstraints.insets = new java.awt.Insets(10, 12, 10, 12);
        jPanel2.add(jLabel8, gridBagConstraints);

        txtUsuarios.setBackground(new java.awt.Color(255, 255, 255));
        txtUsuarios.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        jPanel2.add(txtUsuarios, gridBagConstraints);

        btnUsuarios.setBackground(new java.awt.Color(255, 255, 255));
        btnUsuarios.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        btnUsuarios.setForeground(new java.awt.Color(51, 51, 51));
        btnUsuarios.setText("...");
        btnUsuarios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUsuariosActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 4;
        jPanel2.add(btnUsuarios, gridBagConstraints);

        btnEnviarCorreo.setText("Enviar correo");
        btnEnviarCorreo.setBackground(new java.awt.Color(255, 255, 255));
        btnEnviarCorreo.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        btnEnviarCorreo.setForeground(new java.awt.Color(0, 102, 204));
        btnEnviarCorreo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEnviarCorreoActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 10, 0);
        jPanel2.add(btnEnviarCorreo, gridBagConstraints);

        lblguardado.setFont(new java.awt.Font("Trebuchet MS", 1, 12)); // NOI18N
        lblguardado.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IconoC/cheque_16.png"))); // NOI18N
        lblguardado.setText("Datos guardados correctamente");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 3;
        jPanel2.add(lblguardado, gridBagConstraints);

        jPanel1.add(jPanel2, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnUsuariosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUsuariosActionPerformed
        JFrame f = (JFrame) JOptionPane.getFrameForComponent(this);
        correos co = new correos(f, true, "VENTAS");
        co.setLocationRelativeTo(f);
        co.setVisible(true);
    }//GEN-LAST:event_btnUsuariosActionPerformed

    private void btnEnviarCorreoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEnviarCorreoActionPerformed
        enviarCorreo(coti);
    }//GEN-LAST:event_btnEnviarCorreoActionPerformed

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                EnviarCorreoCotizaciones dialog = new EnviarCorreoCotizaciones(new javax.swing.JFrame(), true, null);
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
    private javax.swing.JLabel btnCheck;
    private javax.swing.JButton btnEnviarCorreo;
    private javax.swing.JButton btnUsuarios;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    public javax.swing.JLabel lblCorreoCliente;
    public javax.swing.JLabel lblNombreCliente;
    private javax.swing.JLabel lblguardado;
    public javax.swing.JTextField txtCorreo;
    public javax.swing.JTextArea txtNotas;
    public javax.swing.JTextField txtUsuarios;
    // End of variables declaration//GEN-END:variables
}
