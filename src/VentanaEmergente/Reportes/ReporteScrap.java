package VentanaEmergente.Reportes;

import Conexiones.Conexion;
import Modelo.TablaHoras;
import com.mxrck.autocompleter.TextAutoCompleter;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;

public class ReporteScrap extends java.awt.Dialog {

    TextAutoCompleter au;
    
    public final void verProyectos() {
        try { 
            Connection con;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            String sql = "select proyecto from proyectos";
            ResultSet rs = st.executeQuery(sql);
            au = new TextAutoCompleter(jTextField1);
            while (rs.next()) {
                au.addItem(rs.getString("proyecto"));
            }
        } catch(SQLException e) {
            JOptionPane.showMessageDialog(this, "Error: " + e,"Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public final void limpiarTabla() {
        Tabla1.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        Tabla1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
            },
            new String [] {
                "Plano", "Empleado", "Fecha", "Proyecto", "Razon", "Comentarios", "Estacion"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        Tabla1.getTableHeader().setFont(new Font("Roboto", Font.BOLD, 14));
        Tabla1.getTableHeader().setBackground(new Color(0,102,153));
        Tabla1.getTableHeader().setForeground(Color.white);
    }
    
    public final void verDatos(String sql) {
        try {
            limpiarTabla();
            Connection con;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            String datos[] = new String[10];
            DefaultTableModel miModelo = (DefaultTableModel) Tabla1.getModel();
            while(rs.next()) {
                datos[0] = rs.getString("Proyecto");
                datos[1] = rs.getString("NumeroEmpleado");
                datos[2] = rs.getString("Fecha");
                datos[3] = rs.getString("Plano");
                datos[4] = rs.getString("Razon");
                datos[5] = rs.getString("Comentarios");
                datos[6] = rs.getString("Desde");
                miModelo.addRow(datos);
            }
        } catch(SQLException e) {
            JOptionPane.showMessageDialog(this, "Error: " + e, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public ReporteScrap(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setLocationRelativeTo(parent);
        verProyectos();
        verDatos("select * from scrap order by id desc");
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        btnRefrescar = new javax.swing.JButton();
        btnPeriodo = new javax.swing.JButton();
        jTextField1 = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        Tabla1 = new javax.swing.JTable();

        setPreferredSize(new java.awt.Dimension(1100, 700));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                closeDialog(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new java.awt.BorderLayout(0, 10));

        jLabel9.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 102, 153));
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("Reporte Scrap / Retrabajos");
        jPanel1.add(jLabel9, java.awt.BorderLayout.NORTH);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new java.awt.BorderLayout(10, 10));

        jPanel3.setBackground(new java.awt.Color(240, 240, 240));
        jPanel3.setLayout(new java.awt.BorderLayout());

        jPanel6.setBackground(new java.awt.Color(250, 250, 250));
        jPanel6.setForeground(new java.awt.Color(240, 240, 240));

        btnRefrescar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/recargar_16.png"))); // NOI18N
        btnRefrescar.setFocusPainted(false);
        btnRefrescar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefrescarActionPerformed(evt);
            }
        });
        jPanel6.add(btnRefrescar);

        btnPeriodo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/calendario.png"))); // NOI18N
        btnPeriodo.setFocusPainted(false);
        btnPeriodo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPeriodoActionPerformed(evt);
            }
        });
        jPanel6.add(btnPeriodo);

        jTextField1.setBackground(new java.awt.Color(250, 250, 250));
        jTextField1.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        jTextField1.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(204, 204, 204)));
        jTextField1.setPreferredSize(new java.awt.Dimension(250, 25));
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });
        jPanel6.add(jTextField1);

        jPanel3.add(jPanel6, java.awt.BorderLayout.CENTER);

        jPanel2.add(jPanel3, java.awt.BorderLayout.PAGE_START);

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setLayout(new java.awt.BorderLayout());

        jScrollPane1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));

        Tabla1.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        Tabla1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Plano", "Empleado", "Fecha", "Proyecto", "Razon", "Comentarios", "Estacion"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        Tabla1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Tabla1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(Tabla1);

        jPanel4.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jPanel2.add(jPanel4, java.awt.BorderLayout.CENTER);

        jPanel1.add(jPanel2, java.awt.BorderLayout.CENTER);

        add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void closeDialog(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_closeDialog
        setVisible(false);
        dispose();
    }//GEN-LAST:event_closeDialog

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        verDatos("select * from scrap where Plano like '" + jTextField1.getText() + "' order by id desc");
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void Tabla1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Tabla1MouseClicked
        if (Tabla1.getSelectedColumn() == 1) {
            if (evt.getClickCount() == 2) {
                try {
                    Connection con = new Conexion().getConnection();
                    Statement st = con.createStatement();
                    String num = Tabla1.getValueAt(Tabla1.getSelectedRow(), 1).toString();
                    String sql = "select NumEmpleado, Nombre, Apellido from registroempleados where NumEmpleado like '" + num + "'";
                    ResultSet rs = st.executeQuery(sql);
                    String empleado = null;
                    while (rs.next()) {
                        empleado = rs.getString("Nombre") + " " + rs.getString("Apellido");

                    }
                    if (empleado != null) {
                        for (int i = 0; i < Tabla1.getRowCount(); i++) {
                            if (num.equals(Tabla1.getValueAt(i, 1).toString())) {
                                Tabla1.setValueAt(empleado, i, 1);
                            }
                        }
                    }
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(this, "error: " + e,"Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }//GEN-LAST:event_Tabla1MouseClicked

    private void btnPeriodoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPeriodoActionPerformed
        // 1. Crear el panel con los JDateChooser
        JPanel panel = new JPanel(new GridLayout(2, 2, 10, 10));
        com.toedter.calendar.JDateChooser jdInicio = new com.toedter.calendar.JDateChooser();
        com.toedter.calendar.JDateChooser jdFin = new com.toedter.calendar.JDateChooser();

        panel.add(new JLabel("Fecha Inicial:"));
        panel.add(jdInicio);
        panel.add(new JLabel("Fecha Final:"));
        panel.add(jdFin);

        // 2. Mostrar el diálogo
        int result = JOptionPane.showConfirmDialog(this, panel, "Seleccione Periodo",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        // 3. Validar y Procesar
        if (result == JOptionPane.OK_OPTION) {
            Date f1 = jdInicio.getDate();
            Date f2 = jdFin.getDate();

            if (f1 == null || f2 == null) {
                JOptionPane.showMessageDialog(this, "Debe seleccionar ambas fechas.", "Error", JOptionPane.WARNING_MESSAGE);
            } else if (f1.after(f2)) {
                JOptionPane.showMessageDialog(this, "La fecha final no puede ser mayor a la inicial.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                filtrarPorPeriodo(f1, f2);
            }
        }
    }//GEN-LAST:event_btnPeriodoActionPerformed

    private void btnRefrescarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefrescarActionPerformed
        verDatos("select * from scrap order by id desc");
    }//GEN-LAST:event_btnRefrescarActionPerformed
   
    public void filtrarPorPeriodo(Date fechaInicio, Date fechaFin) {
        java.sql.Date sqlInicio = new java.sql.Date(fechaInicio.getTime());
        java.sql.Date sqlFin = new java.sql.Date(fechaFin.getTime());

        String sql = "SELECT * FROM scrap WHERE Fecha BETWEEN ? AND ? ORDER BY id DESC";

        try {
            limpiarTabla();
            Conexion con1 = new Conexion();
            Connection con = con1.getConnection();

            // Usamos PreparedStatement para manejar las fechas correctamente
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setDate(1, sqlInicio);
            ps.setDate(2, sqlFin);

            ResultSet rs = ps.executeQuery();
            DefaultTableModel miModelo = (DefaultTableModel) Tabla1.getModel();
            String datos[] = new String[7]; // Ajustado a las 7 columnas de tu limpiarTabla()

            while(rs.next()) {
                datos[0] = rs.getString("Plano");
                datos[1] = rs.getString("NumeroEmpleado");
                datos[2] = rs.getString("Fecha");
                datos[3] = rs.getString("Proyecto");
                datos[4] = rs.getString("Razon");
                datos[5] = rs.getString("Comentarios");
                datos[6] = rs.getString("Desde");
                miModelo.addRow(datos);
            }
        } catch(SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al filtrar: " + e.getMessage());
        }
    }
    
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                ReporteScrap dialog = new ReporteScrap(new java.awt.Frame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable Tabla1;
    private javax.swing.JButton btnPeriodo;
    private javax.swing.JButton btnRefrescar;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    public javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
