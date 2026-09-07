package VentanaEmergente.Rh;

import Conexiones.Conexion;
import VentanaEmergente.Cotizacion.ConfUsuario;
import com.github.lgooddatepicker.components.DatePickerSettings;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class EditarEmpleado extends javax.swing.JDialog {

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

    public final void formato() {
        DatePickerSettings settings = new DatePickerSettings();
        settings.setFormatForDatesCommonEra("yyyy-MM-dd");
        fecha.setSettings(settings);
    }

    public final void agregarListener() {
        fecha.addDateChangeListener(event -> {
            getAntiguedad(fecha.getDateStringOrEmptyString());
        });
    }

    public final void getAntiguedad(String antiguedad) {
        LocalDate fechaIngreso = fecha.getDate();
        if (fechaIngreso != null) {
            LocalDate fechaActual = LocalDate.now();
            Period periodo = Period.between(fechaIngreso, fechaActual);
            double anos = periodo.getYears();
            double dias = periodo.getMonths();
            double ant = anos + (dias / 10);
            System.out.println(ant);
            txtAntiguedad.setText(String.format("%.2f", ant));
        }
    }

    public final void guardarDatos() {
        if (fecha.getDate() == null) {
            JOptionPane.showMessageDialog(this, "Debes capturar la fecha de ingreso", "Advertencia", JOptionPane.ERROR_MESSAGE);
        } else {
            if (txtSalario.getText().equals("")) {
                JOptionPane.showMessageDialog(this, "Debes capturar el salario", "Advertencia", JOptionPane.ERROR_MESSAGE);
            } else {
                try {
                    Connection con = new Conexion().getConnection();
                    String sql = "update empleadoscheck set Nombre = ?, curp = ?, rfc = ?, nss = ?, fechaingreso = ?, "
                            + "antiguedad = ?, contrato = ?, salario = ?, tipopago = ?, activo = ?, horadoble = ?, "
                            + "horatriple = ? "
                            + "where NumEmpleado = ?";
                    PreparedStatement pst = con.prepareStatement(sql);

                    pst.setString(1, txtNombre.getText());
                    pst.setString(2, txtCurp.getText());
                    pst.setString(3, txtRfc.getText());
                    pst.setString(4, txtNss.getText());
                    pst.setString(5, fecha.getText());
                    pst.setString(6, txtAntiguedad.getText());
                    pst.setString(7, jcbContrato.getSelectedItem().toString());
                    pst.setString(8, txtSalario.getText().replace(",", ""));
                    pst.setString(9, jcbPeriodicidad.getSelectedItem().toString());
                    pst.setInt(10, jcbEstatus.getSelectedIndex());
                    pst.setString(11, txtHorasDobles.getText().replace(",", ""));
                    pst.setString(12, txtHorasTriples.getText().replace(",", ""));
                    pst.setString(13, txtEmpleado.getText());

                    int n = pst.executeUpdate();

                    if (n > 0) {
                        verGuardado();
                    }
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(this, "Error al guardar datos de empleado: " + e, "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    public EditarEmpleado(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        formato();
        lblguardado.setVisible(false);
        agregarListener();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel1 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtEmpleado = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtCurp = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtRfc = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtNss = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        fecha = new com.github.lgooddatepicker.components.DatePicker();
        jPanel3 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        txtSalario = new javax.swing.JFormattedTextField();
        jLabel13 = new javax.swing.JLabel();
        txtHorasTriples = new javax.swing.JFormattedTextField();
        jLabel14 = new javax.swing.JLabel();
        txtHorasDobles = new javax.swing.JFormattedTextField();
        jLabel7 = new javax.swing.JLabel();
        txtAntiguedad = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jcbContrato = new javax.swing.JComboBox<>();
        jLabel10 = new javax.swing.JLabel();
        jcbPeriodicidad = new javax.swing.JComboBox<>();
        jLabel11 = new javax.swing.JLabel();
        jcbEstatus = new javax.swing.JComboBox<>();
        btnGuardar = new javax.swing.JButton();
        lblguardado = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(602, 802));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new java.awt.BorderLayout());

        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setText("Editar empleado");
        jLabel12.setFont(new java.awt.Font("Lexend", 1, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(0, 165, 252));
        jPanel1.add(jLabel12, java.awt.BorderLayout.NORTH);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        java.awt.GridBagLayout jPanel2Layout = new java.awt.GridBagLayout();
        jPanel2Layout.columnWeights = new double[] {1.0};
        jPanel2.setLayout(jPanel2Layout);

        jLabel2.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(51, 51, 51));
        jLabel2.setText("Numero de empleado");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(8, 50, 2, 50);
        jPanel2.add(jLabel2, gridBagConstraints);

        txtEmpleado.setEditable(false);
        txtEmpleado.setBackground(new java.awt.Color(255, 255, 255));
        txtEmpleado.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 50, 0, 50);
        jPanel2.add(txtEmpleado, gridBagConstraints);

        jLabel1.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 51, 51));
        jLabel1.setText("Nombre");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(8, 50, 2, 50);
        jPanel2.add(jLabel1, gridBagConstraints);

        txtNombre.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        txtNombre.setBackground(new java.awt.Color(255, 255, 255));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 50, 0, 50);
        jPanel2.add(txtNombre, gridBagConstraints);

        jLabel3.setText("CURP");
        jLabel3.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(51, 51, 51));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(8, 50, 2, 50);
        jPanel2.add(jLabel3, gridBagConstraints);

        txtCurp.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        txtCurp.setBackground(new java.awt.Color(255, 255, 255));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 50, 0, 50);
        jPanel2.add(txtCurp, gridBagConstraints);

        jLabel4.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(51, 51, 51));
        jLabel4.setText("RFC");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(8, 50, 2, 50);
        jPanel2.add(jLabel4, gridBagConstraints);

        txtRfc.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        txtRfc.setBackground(new java.awt.Color(255, 255, 255));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 50, 0, 50);
        jPanel2.add(txtRfc, gridBagConstraints);

        jLabel5.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(51, 51, 51));
        jLabel5.setText("NSS");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(8, 50, 2, 50);
        jPanel2.add(jLabel5, gridBagConstraints);

        txtNss.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        txtNss.setBackground(new java.awt.Color(255, 255, 255));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 50, 0, 50);
        jPanel2.add(txtNss, gridBagConstraints);

        jLabel6.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(51, 51, 51));
        jLabel6.setText("Fecha de ingreso");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(8, 50, 2, 50);
        jPanel2.add(jLabel6, gridBagConstraints);

        fecha.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        fecha.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                fechaFocusLost(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 50, 0, 50);
        jPanel2.add(fecha, gridBagConstraints);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        java.awt.GridBagLayout jPanel3Layout = new java.awt.GridBagLayout();
        jPanel3Layout.columnWeights = new double[] {1.0, 1.0, 1.0};
        jPanel3.setLayout(jPanel3Layout);

        jLabel9.setText("Salario");
        jLabel9.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(51, 51, 51));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(8, 10, 2, 10);
        jPanel3.add(jLabel9, gridBagConstraints);

        txtSalario.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,###.00"))));
        txtSalario.setBackground(new java.awt.Color(255, 255, 255));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 10);
        jPanel3.add(txtSalario, gridBagConstraints);

        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setText("Horas triples");
        jLabel13.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(51, 51, 51));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(8, 10, 2, 10);
        jPanel3.add(jLabel13, gridBagConstraints);

        txtHorasTriples.setBackground(new java.awt.Color(255, 255, 255));
        txtHorasTriples.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,###.00"))));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 10);
        jPanel3.add(txtHorasTriples, gridBagConstraints);

        jLabel14.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(51, 51, 51));
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel14.setText("Horas Dobles");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(8, 10, 2, 10);
        jPanel3.add(jLabel14, gridBagConstraints);

        txtHorasDobles.setBackground(new java.awt.Color(255, 255, 255));
        txtHorasDobles.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,###.00"))));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 10);
        jPanel3.add(txtHorasDobles, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(8, 50, 2, 50);
        jPanel2.add(jPanel3, gridBagConstraints);

        jLabel7.setText("Antigüedad");
        jLabel7.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(51, 51, 51));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(8, 50, 2, 50);
        jPanel2.add(jLabel7, gridBagConstraints);

        txtAntiguedad.setEditable(false);
        txtAntiguedad.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        txtAntiguedad.setBackground(new java.awt.Color(255, 255, 255));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 50, 0, 50);
        jPanel2.add(txtAntiguedad, gridBagConstraints);

        jLabel8.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(51, 51, 51));
        jLabel8.setText("Tipo de contrato");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(8, 50, 2, 50);
        jPanel2.add(jLabel8, gridBagConstraints);

        jcbContrato.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        jcbContrato.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Prueba", "Planta", "Temporal" }));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 50, 0, 50);
        jPanel2.add(jcbContrato, gridBagConstraints);

        jLabel10.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(51, 51, 51));
        jLabel10.setText("Periodicidad de pago");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(8, 50, 2, 50);
        jPanel2.add(jLabel10, gridBagConstraints);

        jcbPeriodicidad.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Semanal" }));
        jcbPeriodicidad.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 50, 0, 50);
        jPanel2.add(jcbPeriodicidad, gridBagConstraints);

        jLabel11.setText("Estatus de empleado");
        jLabel11.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(51, 51, 51));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(8, 50, 2, 50);
        jPanel2.add(jLabel11, gridBagConstraints);

        jcbEstatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Inactivo", "Activo" }));
        jcbEstatus.setSelectedIndex(1);
        jcbEstatus.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 50, 0, 50);
        jPanel2.add(jcbEstatus, gridBagConstraints);

        btnGuardar.setText("Guardar");
        btnGuardar.setBackground(new java.awt.Color(0, 165, 252));
        btnGuardar.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        btnGuardar.setForeground(new java.awt.Color(255, 255, 255));
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipady = 8;
        gridBagConstraints.insets = new java.awt.Insets(8, 50, 2, 50);
        jPanel2.add(btnGuardar, gridBagConstraints);

        lblguardado.setFont(new java.awt.Font("Trebuchet MS", 1, 12)); // NOI18N
        lblguardado.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IconoC/cheque_16.png"))); // NOI18N
        lblguardado.setText("Datos guardados correctamente");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.PAGE_END;
        jPanel2.add(lblguardado, gridBagConstraints);

        jPanel1.add(jPanel2, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void fechaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_fechaFocusLost
        getAntiguedad(fecha.getText());
    }//GEN-LAST:event_fechaFocusLost

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        guardarDatos();
    }//GEN-LAST:event_btnGuardarActionPerformed

    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                EditarEmpleado dialog = new EditarEmpleado(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnGuardar;
    public com.github.lgooddatepicker.components.DatePicker fecha;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    public javax.swing.JComboBox<String> jcbContrato;
    public javax.swing.JComboBox<String> jcbEstatus;
    public javax.swing.JComboBox<String> jcbPeriodicidad;
    private javax.swing.JLabel lblguardado;
    public javax.swing.JTextField txtAntiguedad;
    public javax.swing.JTextField txtCurp;
    public javax.swing.JTextField txtEmpleado;
    public javax.swing.JFormattedTextField txtHorasDobles;
    public javax.swing.JFormattedTextField txtHorasTriples;
    public javax.swing.JTextField txtNombre;
    public javax.swing.JTextField txtNss;
    public javax.swing.JTextField txtRfc;
    public javax.swing.JFormattedTextField txtSalario;
    // End of variables declaration//GEN-END:variables
}
