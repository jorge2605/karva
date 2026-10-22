package VentanaEmergente.Ventas;

import Conexiones.Conexion;
import VentanaEmergente.Cotizacion.ConfUsuario;
import com.mxrck.autocompleter.TextAutoCompleter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class CrearOrdenTrabajo extends javax.swing.JDialog {

    public String numEmpleado;
    public TextAutoCompleter au;
    public TextAutoCompleter auProyectos;

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

    public final void agregarProyecto() {
        try {
            Connection con;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            auProyectos = new TextAutoCompleter(txtProyecto);
            String sql = "select Proyecto, Id from Proyectos order by Id desc";
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                auProyectos.addItem(rs.getString("Proyecto"));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "ERROR: " + e, "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    public final void agregarEmpleados() {
        try {
            Connection con = new Conexion().getConnection();
            String sql = "select * from registroempleados order by Nombre";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            au = new TextAutoCompleter(txtResponsable);
            while (rs.next()) {
                au.addItem(rs.getString("Nombre") + " " + rs.getString("Apellido") + " - " + rs.getString("NumEmpleado"));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al agregar nombres de empleados: " + e, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public final void crearOrden() {
        if (!auProyectos.itemExists(txtProyecto.getText())) {
            JOptionPane.showMessageDialog(this, "Debes seleccionar un proyecto existente", "Advertencia", JOptionPane.WARNING_MESSAGE);
        } else if (!au.itemExists(txtResponsable.getText())) {
            JOptionPane.showMessageDialog(this, "Debes seleccionar un usuario existente", "Advertencia", JOptionPane.WARNING_MESSAGE);
        } else {
            try {
                Connection con = new Conexion().getConnection();
                String sql = "insert into ordentrabajo (departamento, responsable, inicio, termino, proyecto, creador, fecha) values(?,?,?,?,?,?,?)";
                PreparedStatement pst = con.prepareStatement(sql);

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

                pst.setString(1, cmbDepa.getSelectedItem().toString());
                pst.setString(2, txtResponsable.getText().split("-")[1].trim());
                pst.setString(3, null);
                pst.setString(4, null);
                pst.setString(5, txtProyecto.getText());
                pst.setString(6, this.numEmpleado);
                pst.setString(7, sdf.format(new Date()));

                int n = pst.executeUpdate();

                if (n > 0) {
                    verGuardado();
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Error al crear orden de trabajo: " + e, "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public CrearOrdenTrabajo(java.awt.Frame parent, boolean modal, String numEmpleado) {
        super(parent, modal);
        initComponents();
        this.numEmpleado = numEmpleado;
        lblguardado.setVisible(false);
        agregarEmpleados();
        agregarProyecto();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtProyecto = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtResponsable = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        cmbDepa = new javax.swing.JComboBox<>();
        jButton1 = new javax.swing.JButton();
        lblguardado = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(527, 530));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new java.awt.BorderLayout());

        jLabel1.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 102, 255));
        jLabel1.setText("Crear ordenes de trabajo");
        jPanel1.add(jLabel1, java.awt.BorderLayout.PAGE_START);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        java.awt.GridBagLayout jPanel2Layout = new java.awt.GridBagLayout();
        jPanel2Layout.columnWeights = new double[] {1.0, 1.0};
        jPanel2.setLayout(jPanel2Layout);

        jLabel2.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(51, 51, 51));
        jLabel2.setText("Proyecto");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.insets = new java.awt.Insets(20, 0, 0, 0);
        jPanel2.add(jLabel2, gridBagConstraints);

        txtProyecto.setEditable(false);
        txtProyecto.setBackground(new java.awt.Color(255, 255, 255));
        txtProyecto.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 30, 0, 30);
        jPanel2.add(txtProyecto, gridBagConstraints);

        jLabel3.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(51, 51, 51));
        jLabel3.setText("Responsable");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.insets = new java.awt.Insets(20, 0, 0, 0);
        jPanel2.add(jLabel3, gridBagConstraints);

        txtResponsable.setEditable(false);
        txtResponsable.setBackground(new java.awt.Color(255, 255, 255));
        txtResponsable.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 30, 0, 30);
        jPanel2.add(txtResponsable, gridBagConstraints);

        jLabel4.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(51, 51, 51));
        jLabel4.setText("Departamento");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.insets = new java.awt.Insets(20, 0, 0, 0);
        jPanel2.add(jLabel4, gridBagConstraints);

        cmbDepa.setBackground(new java.awt.Color(255, 255, 255));
        cmbDepa.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        cmbDepa.setForeground(new java.awt.Color(51, 51, 51));
        cmbDepa.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Diseno" }));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 30, 0, 30);
        jPanel2.add(cmbDepa, gridBagConstraints);

        jButton1.setBackground(new java.awt.Color(0, 102, 204));
        jButton1.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Guardar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 8;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.ipadx = 50;
        gridBagConstraints.ipady = 10;
        gridBagConstraints.insets = new java.awt.Insets(20, 20, 0, 20);
        jPanel2.add(jButton1, gridBagConstraints);

        lblguardado.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IconoC/cheque_16.png"))); // NOI18N
        lblguardado.setText("Datos guardados correctamente");
        lblguardado.setFont(new java.awt.Font("Trebuchet MS", 1, 12)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.PAGE_END;
        jPanel2.add(lblguardado, gridBagConstraints);

        jPanel1.add(jPanel2, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        crearOrden();
    }//GEN-LAST:event_jButton1ActionPerformed

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                CrearOrdenTrabajo dialog = new CrearOrdenTrabajo(new javax.swing.JFrame(), true, "");
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
    private javax.swing.JComboBox<String> cmbDepa;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel lblguardado;
    public javax.swing.JTextField txtProyecto;
    public javax.swing.JTextField txtResponsable;
    // End of variables declaration//GEN-END:variables
}
