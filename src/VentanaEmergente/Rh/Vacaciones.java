package VentanaEmergente.Rh;

import Conexiones.Conexion;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.Period;
import javax.swing.JOptionPane;

public class Vacaciones extends javax.swing.JDialog {

    public final void verVacaciones(String numEmpleado) {
        try {
            Connection con = new Conexion().getConnection();
            String sql = "select count(idvacaciones) from vacacionestomadas where numEmpleado like '" + numEmpleado + "'";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()) {
                lblVacacionesTomadas.setText(String.valueOf(rs.getInt(1)));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error: " + e);
        }
    }

    private int calcularDiasPorAntiguedad(int anos) {
        if (anos <= 0) {
            return 0;
        }
        if (anos == 1) {
            return 12;
        }
        if (anos == 2) {
            return 14;
        }
        if (anos == 3) {
            return 16;
        }
        if (anos == 4) {
            return 18;
        }
        if (anos == 5) {
            return 20;
        }
        return 20 + ((anos - 5) / 5) * 2;
    }

    public final void calcularVacaciones(String fechaIngreso) {
        LocalDate ingreso = LocalDate.parse(fechaIngreso);
        LocalDate actual = LocalDate.now();
        Period periodo = Period.between(ingreso, actual);
        int anos = periodo.getYears();
        int meses = periodo.getMonths();
        int diasVacaciones;
        if (anos == 0) {
            diasVacaciones = meses;
        } else {
            diasVacaciones = calcularDiasPorAntiguedad(anos);
            int diasSiguienteAno = calcularDiasPorAntiguedad(anos + 1);
            int diasAnteriores = calcularDiasPorAntiguedad(anos);
            int incremento = diasSiguienteAno - diasAnteriores;
            diasVacaciones += (((incremento * meses) / 12) + meses) - Integer.parseInt(lblVacacionesTomadas.getText());
        }
        lblVacacionesDisponibles.setText(String.valueOf(diasVacaciones));
    }

    public Vacaciones(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel1 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        lblEmpleado = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        lblFecha = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        lblVacacionesTomadas = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        lblVacacionesDisponibles = new javax.swing.JLabel();
        lblAntiguedad = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        lblFechaActual = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new java.awt.BorderLayout());

        jLabel12.setFont(new java.awt.Font("Lexend", 1, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(0, 165, 252));
        jLabel12.setText("             Vacaciones");
        jLabel12.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 10));
        jPanel1.add(jLabel12, java.awt.BorderLayout.NORTH);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new java.awt.GridBagLayout());

        jLabel1.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 51, 51));
        jLabel1.setText("Empleado:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(0, 15, 0, 15);
        jPanel2.add(jLabel1, gridBagConstraints);

        lblEmpleado.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblEmpleado.setForeground(new java.awt.Color(51, 51, 51));
        lblEmpleado.setText("emp");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 18, 0);
        jPanel2.add(lblEmpleado, gridBagConstraints);

        jLabel3.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(51, 51, 51));
        jLabel3.setText("Fecha de ingreso:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(0, 15, 0, 15);
        jPanel2.add(jLabel3, gridBagConstraints);

        lblFecha.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblFecha.setForeground(new java.awt.Color(51, 51, 51));
        lblFecha.setText("emp");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 18, 0);
        jPanel2.add(lblFecha, gridBagConstraints);

        jLabel4.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(51, 51, 51));
        jLabel4.setText("Antiguedad:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(0, 15, 0, 15);
        jPanel2.add(jLabel4, gridBagConstraints);

        jLabel5.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(51, 51, 51));
        jLabel5.setText("Vacaciones tomadas:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.insets = new java.awt.Insets(18, 15, 2, 2);
        jPanel2.add(jLabel5, gridBagConstraints);

        lblVacacionesTomadas.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblVacacionesTomadas.setForeground(new java.awt.Color(51, 51, 51));
        lblVacacionesTomadas.setText("emp");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        jPanel2.add(lblVacacionesTomadas, gridBagConstraints);

        jButton1.setBackground(new java.awt.Color(255, 255, 255));
        jButton1.setFont(new java.awt.Font("Trebuchet MS", 1, 12)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/ver_todo_16.png"))); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(0, 2, 0, 15);
        jPanel2.add(jButton1, gridBagConstraints);

        jLabel7.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(51, 51, 51));
        jLabel7.setText("Vacaciones disponibles:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.insets = new java.awt.Insets(18, 15, 2, 15);
        jPanel2.add(jLabel7, gridBagConstraints);

        lblVacacionesDisponibles.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblVacacionesDisponibles.setForeground(new java.awt.Color(51, 51, 51));
        lblVacacionesDisponibles.setText("emp");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 5;
        jPanel2.add(lblVacacionesDisponibles, gridBagConstraints);

        lblAntiguedad.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblAntiguedad.setForeground(new java.awt.Color(51, 51, 51));
        lblAntiguedad.setText("emp");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 18, 0);
        jPanel2.add(lblAntiguedad, gridBagConstraints);

        jLabel2.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(51, 51, 51));
        jLabel2.setText("Fecha:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(0, 15, 0, 15);
        jPanel2.add(jLabel2, gridBagConstraints);

        lblFechaActual.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblFechaActual.setForeground(new java.awt.Color(51, 51, 51));
        lblFechaActual.setText("emp");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 18, 0);
        jPanel2.add(lblFechaActual, gridBagConstraints);

        jPanel1.add(jPanel2, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Vacaciones dialog = new Vacaciones(new javax.swing.JFrame(), true);
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
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    public javax.swing.JLabel lblAntiguedad;
    public javax.swing.JLabel lblEmpleado;
    public javax.swing.JLabel lblFecha;
    public javax.swing.JLabel lblFechaActual;
    public javax.swing.JLabel lblVacacionesDisponibles;
    public javax.swing.JLabel lblVacacionesTomadas;
    // End of variables declaration//GEN-END:variables
}
