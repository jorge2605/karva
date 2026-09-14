package VentanaEmergente.Cotizacion;

import Conexiones.Conexion;
import java.awt.Image;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

public class ConfUsuario extends javax.swing.JDialog {

    private Preferences prefs = Preferences.userNodeForPackage(getClass());
    private static final String KEY_DIRECTORIO = "ultimoDirectorio";

    public final void guardarDatos() {
        try {
            Connection con = new Conexion().getConnection();
            String sql = "insert into conf_cotizacion (urlimg, empresa, rfc, correo, direccion, vendedor, urldireccion) values(?,?,?,?,?,?,?)";
            PreparedStatement pst = con.prepareStatement(sql);

            pst.setString(1, txtUrl.getText());
            pst.setString(2, txtEmpresa.getText());
            pst.setString(3, txtRfc.getText());
            pst.setString(4, txtCorreo.getText());
            pst.setString(5, txtDireccion.getText());
            pst.setString(6, txtVendedor.getText());
            pst.setString(7, txtGuardado.getText());

            int n = pst.executeUpdate();

            if (n > 0) {
                verGuardado();
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error: " + e, "Error", JOptionPane.ERROR_MESSAGE);
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

    public final void verDatos() {
        try {
            Connection con = new Conexion().getConnection();
            Statement st = con.createStatement();
            String sql = "select * from conf_cotizacion";
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                txtUrl.setText(rs.getString("urlimg"));
                txtEmpresa.setText(rs.getString("empresa"));
                txtRfc.setText(rs.getString("rfc"));
                txtCorreo.setText(rs.getString("correo"));
                txtDireccion.setText(rs.getString("direccion"));
                txtVendedor.setText(rs.getString("vendedor"));
                txtGuardado.setText(rs.getString("urldireccion"));
            }
            if (!txtUrl.getText().equals("")) {
                lblImg.setIcon(new ImageIcon(txtUrl.getText()));
            }
            icono(txtUrl.getText());
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al ver datos de configuracion: " + e, "error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public final void icono(String ruta) {
        ImageIcon icono = new ImageIcon((ruta));
        float an = icono.getIconWidth();
        float alt = icono.getIconHeight();
        float porcentaje = (200 * 100) / an;
        Image imgEscalada = icono.getImage().getScaledInstance((int)(an * porcentaje) / 100, (int)(alt * porcentaje) / 100, Image.SCALE_SMOOTH);
        lblImg.setIcon(new ImageIcon(imgEscalada));
        lblguardado.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconoC/cheque_16.png")));
    }
    
    public final void verChose(JTextField txt) {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileFilter(new FileNameExtensionFilter("Imágenes (PNG, JPG)", "png", "jpg", "jpeg"));
        chooser.setAcceptAllFileFilterUsed(false);

        String ultimoDir = prefs.get(KEY_DIRECTORIO, null);
        if (ultimoDir != null) {
            chooser.setCurrentDirectory(new File(ultimoDir));
        }

        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File archivo = chooser.getSelectedFile();

            prefs.put(KEY_DIRECTORIO, archivo.getParent());
            txt.setText(archivo.getAbsolutePath());
            lblImg.setIcon(new ImageIcon(archivo.getAbsolutePath()));
            icono(txt.getText());
        }
    }
    
    public final void seleccionarCarpeta() {
        JFileChooser choos = new JFileChooser();
        choos.setAcceptAllFileFilterUsed(false);
        choos.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        
        String ultimodir = prefs.get(KEY_DIRECTORIO, null);
        
        if (ultimodir != null) {
            choos.setCurrentDirectory(new File(ultimodir));
        }
        
        if (choos.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File ar = choos.getSelectedFile();
            
            prefs.put(KEY_DIRECTORIO, ar.getParent());
            txtGuardado.setText(ar.getAbsolutePath());
        }
    }
    
    public ConfUsuario(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        lblguardado.setVisible(false);
        verDatos();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel1 = new javax.swing.JPanel();
        lblImg = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        txtUrl = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        txtGuardado = new javax.swing.JTextField();
        jButton3 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        txtEmpresa = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtRfc = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtCorreo = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtDireccion = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtVendedor = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        lblguardado = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(500, 800));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        java.awt.GridBagLayout jPanel1Layout = new java.awt.GridBagLayout();
        jPanel1Layout.columnWeights = new double[] {1.0};
        jPanel1.setLayout(jPanel1Layout);

        lblImg.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblImg.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridheight = 15;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 200;
        gridBagConstraints.ipady = 200;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.BASELINE;
        gridBagConstraints.insets = new java.awt.Insets(0, 50, 0, 50);
        jPanel1.add(lblImg, gridBagConstraints);

        jLabel1.setText("URL:");
        jLabel1.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(10, 65, 2, 15);
        jPanel1.add(jLabel1, gridBagConstraints);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new java.awt.BorderLayout(5, 0));

        txtUrl.setEditable(false);
        txtUrl.setFont(new java.awt.Font("Trebuchet MS", 0, 14)); // NOI18N
        txtUrl.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.add(txtUrl, java.awt.BorderLayout.CENTER);

        jButton1.setText("...");
        jButton1.setBackground(new java.awt.Color(255, 255, 255));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton1, java.awt.BorderLayout.LINE_END);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 50, 0, 50);
        jPanel1.add(jPanel2, gridBagConstraints);

        jLabel7.setText("Direcion de guardado:");
        jLabel7.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(10, 65, 2, 15);
        jPanel1.add(jLabel7, gridBagConstraints);

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setLayout(new java.awt.BorderLayout(5, 0));

        txtGuardado.setEditable(false);
        txtGuardado.setFont(new java.awt.Font("Trebuchet MS", 0, 14)); // NOI18N
        txtGuardado.setBackground(new java.awt.Color(255, 255, 255));
        txtGuardado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtGuardadoActionPerformed(evt);
            }
        });
        jPanel4.add(txtGuardado, java.awt.BorderLayout.CENTER);

        jButton3.setText("...");
        jButton3.setBackground(new java.awt.Color(255, 255, 255));
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel4.add(jButton3, java.awt.BorderLayout.LINE_END);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 50, 0, 50);
        jPanel1.add(jPanel4, gridBagConstraints);

        jLabel2.setText("Empresa:");
        jLabel2.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(10, 65, 2, 15);
        jPanel1.add(jLabel2, gridBagConstraints);

        txtEmpresa.setBackground(new java.awt.Color(255, 255, 255));
        txtEmpresa.setFont(new java.awt.Font("Trebuchet MS", 0, 14)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 50, 0, 50);
        jPanel1.add(txtEmpresa, gridBagConstraints);

        jLabel3.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        jLabel3.setText("RFC:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(10, 65, 2, 15);
        jPanel1.add(jLabel3, gridBagConstraints);

        txtRfc.setBackground(new java.awt.Color(255, 255, 255));
        txtRfc.setFont(new java.awt.Font("Trebuchet MS", 0, 14)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 50, 0, 50);
        jPanel1.add(txtRfc, gridBagConstraints);

        jLabel4.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        jLabel4.setText("Correo:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(10, 65, 2, 15);
        jPanel1.add(jLabel4, gridBagConstraints);

        txtCorreo.setBackground(new java.awt.Color(255, 255, 255));
        txtCorreo.setFont(new java.awt.Font("Trebuchet MS", 0, 14)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 50, 0, 50);
        jPanel1.add(txtCorreo, gridBagConstraints);

        jLabel5.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        jLabel5.setText("Direccion");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(10, 65, 2, 15);
        jPanel1.add(jLabel5, gridBagConstraints);

        txtDireccion.setBackground(new java.awt.Color(255, 255, 255));
        txtDireccion.setFont(new java.awt.Font("Trebuchet MS", 0, 14)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 50, 0, 50);
        jPanel1.add(txtDireccion, gridBagConstraints);

        jLabel6.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        jLabel6.setText("Vendedor");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(10, 65, 2, 15);
        jPanel1.add(jLabel6, gridBagConstraints);

        txtVendedor.setBackground(new java.awt.Color(255, 255, 255));
        txtVendedor.setFont(new java.awt.Font("Trebuchet MS", 0, 14)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 50, 0, 50);
        jPanel1.add(txtVendedor, gridBagConstraints);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jButton2.setBackground(new java.awt.Color(51, 153, 255));
        jButton2.setFont(new java.awt.Font("Trebuchet MS", 1, 12)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("Guardar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton2);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 10, 0);
        jPanel1.add(jPanel3, gridBagConstraints);

        lblguardado.setFont(new java.awt.Font("Trebuchet MS", 1, 12)); // NOI18N
        lblguardado.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IconoC/cheque_16.png"))); // NOI18N
        lblguardado.setText("Datos guardados correctamente");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        jPanel1.add(lblguardado, gridBagConstraints);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        verChose(txtUrl);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        guardarDatos();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        seleccionarCarpeta();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void txtGuardadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtGuardadoActionPerformed
        verChose(txtGuardado);
    }//GEN-LAST:event_txtGuardadoActionPerformed

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                ConfUsuario dialog = new ConfUsuario(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JLabel lblImg;
    private javax.swing.JLabel lblguardado;
    private javax.swing.JTextField txtCorreo;
    private javax.swing.JTextField txtDireccion;
    private javax.swing.JTextField txtEmpresa;
    private javax.swing.JTextField txtGuardado;
    private javax.swing.JTextField txtRfc;
    private javax.swing.JTextField txtUrl;
    private javax.swing.JTextField txtVendedor;
    // End of variables declaration//GEN-END:variables
}
