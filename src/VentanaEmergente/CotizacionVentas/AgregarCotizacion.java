package VentanaEmergente.CotizacionVentas;

import Conexiones.Conexion;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.mxrck.autocompleter.TextAutoCompleter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import static java.lang.Thread.sleep;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class AgregarCotizacion extends javax.swing.JDialog {

    private String numEmpleado;
    private TextAutoCompleter ai;

    public String extraerIDCliente() {
        try {
            Connection con = new Conexion().getConnection();
            Statement st = con.createStatement();
            String sql = "select * from clientes where Nombre like '" + txtCliente.getText() + "'";
            ResultSet rs = st.executeQuery(sql);
            String id = "";
            while (rs.next()) {
                id = rs.getString("idclientes");
            }
            System.out.println(id);
            return id;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error: " + e, "Error", JOptionPane.ERROR_MESSAGE);
        }
        return null;
    }

    public final void agregarClientes() {
        try {
            Connection con = new Conexion().getConnection();
            Statement st = con.createStatement();
            String sql = "select * from clientes order by Nombre";
            ResultSet rs = st.executeQuery(sql);
            if (ai != null) {
                ai.removeAllItems();
            }
            ai = new TextAutoCompleter(txtCliente);
            while (rs.next()) {
                ai.addItem(rs.getString("Nombre"));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error: " + e, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public final void verGuardado() {
        btnDatos.setVisible(true);
        Thread hilo = new Thread(() -> {
            try {
                sleep(3000);
                btnDatos.setVisible(false);
            } catch (InterruptedException ex) {
                Logger.getLogger(AgregarCotizacion.class.getName()).log(Level.SEVERE, null, ex);
                btnDatos.setVisible(false);
            }
        });
        hilo.start();
    }

    public final void agregarCotizacion(boolean item) {
        try {
            Connection con = new Conexion().getConnection();
            if (txtCotizacion.getText().equals("")) {
                String sql = "insert into cotizacion (fecha, NumEmpleado, requisitor, descripcion, idcliente, estado) values (?,?,?,?,?,?)";
                PreparedStatement pst = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date d = new Date();

                pst.setString(1, sdf.format(d));
                pst.setString(2, this.numEmpleado);
                pst.setString(3, txtProyecto.getText());
                pst.setString(4, txtDescripcion.getText());
                pst.setString(5, extraerIDCliente());
                pst.setString(6, cmbEstado.getSelectedItem().toString());

                pst.executeUpdate();
                ResultSet rs = pst.getGeneratedKeys();

                if (rs.next()) {
                    txtCotizacion.setText(String.valueOf(rs.getInt(1)));
                    verGuardado();
                } else {
                    JOptionPane.showMessageDialog(this, "Error al guardar cotizacion");
                }

            } else {
                String sql = "update cotizacion set requisitor = ?, descripcion = ?, idcliente = ?, estado = ? where idcotizacion = ?";
                PreparedStatement pst = con.prepareStatement(sql);

                pst.setString(1, txtProyecto.getText());
                pst.setString(2, txtDescripcion.getText());
                pst.setString(3, extraerIDCliente());
                pst.setString(4, cmbEstado.getSelectedItem().toString());
                pst.setString(5, txtCotizacion.getText());

                int n = pst.executeUpdate();

                if (n < 1) {
                    JOptionPane.showMessageDialog(this, "Error al gudardar datos");
                } else {
                    verGuardado();
                }

            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error: " + e, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public final void nombre() {
        try {
            Connection con = new Conexion().getConnection();
            Statement st = con.createStatement();
            String sql = "select * from registroempleados where NumEmpleado like '" + this.numEmpleado + "'";
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                txtVendedor.setText(rs.getString("Nombre") + " " + rs.getString("Apellido"));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error: " + e, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public AgregarCotizacion(java.awt.Frame parent, boolean modal, String numEmpleado) {
        super(parent, modal);
        initComponents();
        this.numEmpleado = numEmpleado;
        nombre();
        agregarClientes();
        btnDatos.setVisible(false);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        btnGuardar = new javax.swing.JButton();
        btnClientes = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        txtCotizacion = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtVendedor = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtCliente = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtProyecto = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtDescripcion = new javax.swing.JTextArea();
        jLabel11 = new javax.swing.JLabel();
        cmbEstado = new javax.swing.JComboBox<>();
        btnDatos = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(1147, 750));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new java.awt.BorderLayout());

        jLabel1.setFont(new java.awt.Font("Bahnschrift", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 102, 204));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Nueva cotizacion");
        jPanel1.add(jLabel1, java.awt.BorderLayout.PAGE_START);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        java.awt.GridBagLayout jPanel2Layout = new java.awt.GridBagLayout();
        jPanel2Layout.columnWeights = new double[] {1.0, 1.0};
        jPanel2.setLayout(jPanel2Layout);

        btnGuardar.setBackground(new java.awt.Color(255, 255, 255));
        btnGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/guardar_16.png"))); // NOI18N
        btnGuardar.setBorder(null);
        btnGuardar.setBorderPainted(false);
        btnGuardar.setContentAreaFilled(false);
        btnGuardar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnGuardar.setFocusPainted(false);
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(0, 50, 0, 0);
        jPanel2.add(btnGuardar, gridBagConstraints);

        btnClientes.setBackground(new java.awt.Color(255, 255, 255));
        btnClientes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/editar (1).png"))); // NOI18N
        btnClientes.setBorder(null);
        btnClientes.setBorderPainted(false);
        btnClientes.setContentAreaFilled(false);
        btnClientes.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnClientes.setFocusPainted(false);
        btnClientes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClientesActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 50);
        jPanel2.add(btnClientes, gridBagConstraints);

        jLabel2.setFont(new java.awt.Font("Bahnschrift", 1, 14)); // NOI18N
        jLabel2.setText("# de solucitud de cotizacion");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(18, 65, 0, 65);
        jPanel2.add(jLabel2, gridBagConstraints);

        txtCotizacion.setEditable(false);
        txtCotizacion.setBackground(new java.awt.Color(255, 255, 255));
        txtCotizacion.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N
        txtCotizacion.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 50, 0, 50);
        jPanel2.add(txtCotizacion, gridBagConstraints);

        jLabel3.setFont(new java.awt.Font("Bahnschrift", 1, 14)); // NOI18N
        jLabel3.setText("Venta de ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(18, 65, 0, 65);
        jPanel2.add(jLabel3, gridBagConstraints);

        txtVendedor.setEditable(false);
        txtVendedor.setBackground(new java.awt.Color(255, 255, 255));
        txtVendedor.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N
        txtVendedor.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 50, 0, 50);
        jPanel2.add(txtVendedor, gridBagConstraints);

        jLabel4.setFont(new java.awt.Font("Bahnschrift", 1, 14)); // NOI18N
        jLabel4.setText("Cliente");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(18, 65, 0, 65);
        jPanel2.add(jLabel4, gridBagConstraints);

        txtCliente.setBackground(new java.awt.Color(255, 255, 255));
        txtCliente.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 50, 0, 10);
        jPanel2.add(txtCliente, gridBagConstraints);

        jLabel5.setFont(new java.awt.Font("Bahnschrift", 1, 14)); // NOI18N
        jLabel5.setText("Responsable");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(18, 65, 0, 65);
        jPanel2.add(jLabel5, gridBagConstraints);

        txtProyecto.setBackground(new java.awt.Color(255, 255, 255));
        txtProyecto.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 50, 0, 50);
        jPanel2.add(txtProyecto, gridBagConstraints);

        jLabel6.setFont(new java.awt.Font("Bahnschrift", 1, 14)); // NOI18N
        jLabel6.setText("Descripcion");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(18, 65, 0, 65);
        jPanel2.add(jLabel6, gridBagConstraints);

        txtDescripcion.setBackground(new java.awt.Color(255, 255, 255));
        txtDescripcion.setColumns(20);
        txtDescripcion.setFont(new java.awt.Font("Bahnschrift", 0, 14)); // NOI18N
        txtDescripcion.setRows(5);
        jScrollPane3.setViewportView(txtDescripcion);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 50, 0, 50);
        jPanel2.add(jScrollPane3, gridBagConstraints);

        jLabel11.setFont(new java.awt.Font("Bahnschrift", 1, 14)); // NOI18N
        jLabel11.setText("Estado");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(18, 65, 0, 65);
        jPanel2.add(jLabel11, gridBagConstraints);

        cmbEstado.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Borrador", "Enviado", "Vendido", "Cancelado" }));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 50, 0, 50);
        jPanel2.add(cmbEstado, gridBagConstraints);

        btnDatos.setBackground(new java.awt.Color(255, 255, 255));
        btnDatos.setFont(new java.awt.Font("Bahnschrift", 1, 12)); // NOI18N
        btnDatos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IconoC/cheque_16.png"))); // NOI18N
        btnDatos.setText("Datos guardados correctamente");
        btnDatos.setBorder(null);
        btnDatos.setBorderPainted(false);
        btnDatos.setContentAreaFilled(false);
        btnDatos.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnDatos.setFocusPainted(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(0, 50, 0, 0);
        jPanel2.add(btnDatos, gridBagConstraints);

        jPanel1.add(jPanel2, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        if (txtProyecto.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Debes ingresar el nombre de requisitor", "Advertencia", JOptionPane.WARNING_MESSAGE);
        } else if (txtCliente.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Debes ingresar el nombre del cliente", "Advertencia", JOptionPane.WARNING_MESSAGE);
        } else if (txtDescripcion.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Debes ingresar alguna descripcion de proyecto", "Advertencia", JOptionPane.WARNING_MESSAGE);
        } else {
            agregarCotizacion(false);

        }
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnClientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClientesActionPerformed
        JFrame f = (JFrame) JOptionPane.getFrameForComponent(this);
        AgregarEmpresaVentas add = new AgregarEmpresaVentas(f, true);
        add.setLocationRelativeTo(f);
        add.setVisible(true);
        agregarClientes();
    }//GEN-LAST:event_btnClientesActionPerformed

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                AgregarCotizacion dialog = new AgregarCotizacion(new javax.swing.JFrame(), true, null);
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
    private javax.swing.JButton btnClientes;
    private javax.swing.JButton btnDatos;
    public javax.swing.JButton btnGuardar;
    public javax.swing.JComboBox<String> cmbEstado;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane3;
    public javax.swing.JTextField txtCliente;
    public javax.swing.JTextField txtCotizacion;
    public javax.swing.JTextArea txtDescripcion;
    public javax.swing.JTextField txtProyecto;
    public javax.swing.JTextField txtVendedor;
    // End of variables declaration//GEN-END:variables
}
