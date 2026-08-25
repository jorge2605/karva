package VentanaEmergente.Cotizacion;

import Conexiones.Conexion;
import com.mxrck.autocompleter.TextAutoCompleter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class AgregarCliente extends javax.swing.JDialog {

    HashMap<Integer, Integer> clientes;
    private TextAutoCompleter au;
    private String contacto;
    
    public final void agregarClientes() {
        try (Connection con = new Conexion().getConnection()) {
            Statement st = con.createStatement();
            String sql = "select * from clientes_cotizacion order by nombre asc";
            ResultSet rs = st.executeQuery(sql);
            if (au != null) {
                au.removeAllItems();
            }
            au = new TextAutoCompleter(txtCliente);
            while (rs.next()) {
                au.addItem(rs.getString("Nombre"));
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
    
    public final void verContactos() {
        try {
            Connection con = new Conexion().getConnection();
            Statement st = con.createStatement();
            String sql = "select * from contacto_cotizacion where idCliente like '" + lblId.getText() + "'";
            ResultSet rs = st.executeQuery(sql);
            jcbContacto.removeAllItems();
            jcbContacto.addItem("Seleccionar");
            clientes = new HashMap<>();
            int cont = 1;
            while (rs.next()) {
                jcbContacto.addItem(rs.getString("Nombre"));
                clientes.put(cont, rs.getInt("idcontacto_cotizacion"));
                cont++;
            }
            
            if (jcbContacto.getItemCount() > 1) {
                jcbContacto.setSelectedItem(contacto);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al ver contactos: " + e, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public final void agregarCliente () {
        try {
            Connection con = new Conexion().getConnection();
            String sql = "insert into clientes_cotizacion (nombre, domicilio, rfc) values(?,?,?)";
            if (!lblId.getText().equals("")) {
                sql = "update clientes_cotizacion set nombre = ?, domicilio = ?, rfc = ? where idCliente = ?";
            }
            PreparedStatement pst = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            
            pst.setString(1, txtCliente.getText());
            pst.setString(2, txtDomicilio.getText());
            pst.setString(3, txtRFC.getText());
            if (!lblId.getText().equals("")) {
                pst.setString(4, lblId.getText());
            }
            
            int n = pst.executeUpdate();
            
            if (n > 0) {
                ResultSet rs = pst.getGeneratedKeys();
                if (rs.next()) {
                    int id = rs.getInt(1);
                    lblId.setText(String.valueOf(id));
                    verGuardado();
                }
            }
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al agregar Cliente: " + e, "Error", JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(AgregarCliente.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
    public final void actualizarContacto() {
        try {
            Connection con = new Conexion().getConnection();
            String sql = "update clientes_cotizacion set idContacto = ? where idCliente = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            
            pst.setInt(1, clientes.get(jcbContacto.getSelectedIndex()));
            pst.setString(2, lblId.getText());
            
            int n = pst.executeUpdate();
            
            if (n > 0) {
                verGuardado();
            }
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al actualizar contacto: " + e, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public final void verCliente(String cliente) {
        try {
            try (Connection con = new Conexion().getConnection()) {
                Statement st = con.createStatement();
                txtCliente.setText(cliente);
                String sql = "select cl.domicilio, cl.rfc, cl.idcliente, co.nombre from clientes_cotizacion as cl "
                        + "inner join contacto_cotizacion as co on cl.idcontacto = co.idcontacto_cotizacion "
                        + "where cl.nombre = '" + cliente + "'";
                ResultSet rs = st.executeQuery(sql);
                while (rs.next()) {
                    txtDomicilio.setText(rs.getString("cl.domicilio"));
                    txtRFC.setText(rs.getString("cl.rfc"));
                    lblId.setText(rs.getString("cl.idCliente"));
                    contacto = rs.getString("co.Nombre");
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al ver cliente: " + e, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public AgregarCliente(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        lblguardado.setVisible(false);
        verContactos();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtCliente = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtDomicilio = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtRFC = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jcbContacto = new javax.swing.JComboBox<>();
        btnGuardar = new javax.swing.JButton();
        btnAgregarContacto = new javax.swing.JButton();
        lblId = new javax.swing.JLabel();
        lblguardado = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(402, 466));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        java.awt.GridBagLayout jPanel1Layout = new java.awt.GridBagLayout();
        jPanel1Layout.columnWeights = new double[] {1.0};
        jPanel1Layout.rowWeights = new double[] {1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0};
        jPanel1.setLayout(jPanel1Layout);

        jLabel1.setFont(new java.awt.Font("Trebuchet MS", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 51, 51));
        jLabel1.setText("Clientes");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = 2;
        jPanel1.add(jLabel1, gridBagConstraints);

        jLabel2.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        jLabel2.setText("Nombre de cliente");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(10, 50, 2, 50);
        jPanel1.add(jLabel2, gridBagConstraints);

        txtCliente.setBackground(new java.awt.Color(255, 255, 255));
        txtCliente.setFont(new java.awt.Font("Trebuchet MS", 0, 12)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(2, 50, 10, 50);
        jPanel1.add(txtCliente, gridBagConstraints);

        jLabel3.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        jLabel3.setText("Domicilio de empresa");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(10, 50, 2, 50);
        jPanel1.add(jLabel3, gridBagConstraints);

        txtDomicilio.setBackground(new java.awt.Color(255, 255, 255));
        txtDomicilio.setFont(new java.awt.Font("Trebuchet MS", 0, 12)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(2, 50, 10, 50);
        jPanel1.add(txtDomicilio, gridBagConstraints);

        jLabel4.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        jLabel4.setText("RFC");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(10, 50, 2, 50);
        jPanel1.add(jLabel4, gridBagConstraints);

        txtRFC.setBackground(new java.awt.Color(255, 255, 255));
        txtRFC.setFont(new java.awt.Font("Trebuchet MS", 0, 12)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(2, 50, 10, 50);
        jPanel1.add(txtRFC, gridBagConstraints);

        jLabel5.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        jLabel5.setText("Contacto");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(10, 50, 2, 50);
        jPanel1.add(jLabel5, gridBagConstraints);

        jcbContacto.setBackground(new java.awt.Color(255, 255, 255));
        jcbContacto.setFont(new java.awt.Font("Trebuchet MS", 0, 12)); // NOI18N
        jcbContacto.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccionar" }));
        jcbContacto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbContactoActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(2, 50, 10, 5);
        jPanel1.add(jcbContacto, gridBagConstraints);

        btnGuardar.setBackground(new java.awt.Color(0, 102, 204));
        btnGuardar.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        btnGuardar.setForeground(new java.awt.Color(255, 255, 255));
        btnGuardar.setText("Guardar");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 8;
        gridBagConstraints.ipady = 8;
        gridBagConstraints.insets = new java.awt.Insets(10, 50, 10, 50);
        jPanel1.add(btnGuardar, gridBagConstraints);

        btnAgregarContacto.setBackground(new java.awt.Color(51, 51, 51));
        btnAgregarContacto.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        btnAgregarContacto.setForeground(new java.awt.Color(255, 255, 255));
        btnAgregarContacto.setText("+");
        btnAgregarContacto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarContactoActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.insets = new java.awt.Insets(2, 0, 10, 50);
        jPanel1.add(btnAgregarContacto, gridBagConstraints);

        lblId.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 0);
        jPanel1.add(lblId, gridBagConstraints);

        lblguardado.setFont(new java.awt.Font("Trebuchet MS", 1, 12)); // NOI18N
        lblguardado.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IconoC/cheque_16.png"))); // NOI18N
        lblguardado.setText("Datos guardados correctamente");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.PAGE_END;
        jPanel1.add(lblguardado, gridBagConstraints);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        agregarCliente();
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnAgregarContactoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarContactoActionPerformed
        agregarCliente();
        JFrame f = (JFrame) JOptionPane.getFrameForComponent(this);
        AgregarContacto add = new AgregarContacto(f, true, lblId.getText());
        add.setLocationRelativeTo(f);
        add.setVisible(true);
        verContactos();
    }//GEN-LAST:event_btnAgregarContactoActionPerformed

    private void jcbContactoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbContactoActionPerformed
        if (jcbContacto.getItemCount() > 1) {
            if (this.isVisible()) {
                actualizarContacto();
            }
        }
    }//GEN-LAST:event_jcbContactoActionPerformed

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                AgregarCliente dialog = new AgregarCliente(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnAgregarContacto;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JComboBox<String> jcbContacto;
    private javax.swing.JLabel lblId;
    private javax.swing.JLabel lblguardado;
    private javax.swing.JTextField txtCliente;
    private javax.swing.JTextField txtDomicilio;
    private javax.swing.JTextField txtRFC;
    // End of variables declaration//GEN-END:variables
}
