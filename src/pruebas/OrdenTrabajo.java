package pruebas;

import Conexiones.Conexion;
import Controlador.maquinados.razon;
import VentanaEmergente.Cotizacion.TablaCoti;
import VentanaEmergente.OrdenTrabajo.CambioEmpleado;
import VentanaEmergente.Ventas.CrearOrdenTrabajo;
import java.awt.Color;
import java.awt.Font;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class OrdenTrabajo extends javax.swing.JInternalFrame {

    public String numEmpleado;

    public static String calcular(String fechaInicio, String fechaFinal, String tiempoCronometro) {
        DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime inicio = LocalDateTime.parse(fechaInicio, formatoFecha);
        LocalDateTime fin = LocalDateTime.parse(fechaFinal, formatoFecha);
        Duration diferencia = Duration.between(inicio, fin);
        String[] partes = tiempoCronometro.split(":");
        long horas = Long.parseLong(partes[0]);
        long minutos = Long.parseLong(partes[1]);
        long segundos = Long.parseLong(partes[2]);
        Duration cronometro = Duration.ofHours(horas).plusMinutes(minutos).plusSeconds(segundos);
        Duration total = diferencia.plus(cronometro);
        long totalSegundos = total.getSeconds();
        long h = totalSegundos / 3600;
        long m = (totalSegundos % 3600) / 60;
        long s = totalSegundos % 60;
        return String.format("%02d:%02d:%02d", h, m, s);
    }

    public final void limpiarTabla() {
        Tabla1 = new TablaCoti();
        Tabla1.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{},
                new String[]{
                    "Id", "Departamento", "Responsable", "Inicio", "Termino", "Proyecto", "Estado", "Tiempo"
                }
        ) {
            boolean[] canEdit = new boolean[]{
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });

        if (Tabla1.getColumnModel().getColumnCount() > 0) {
            Tabla1.getColumnModel().getColumn(0).setMinWidth(50);
            Tabla1.getColumnModel().getColumn(0).setPreferredWidth(50);
            Tabla1.getColumnModel().getColumn(0).setMaxWidth(50);
        }

        Tabla1.setComponentPopupMenu(jPopupMenu1);
        Tabla1.getTableHeader().setFont(new Font("Trebuchet MS", Font.BOLD, 14));
        Tabla1.setFont(new Font("Trebuchet MS", Font.PLAIN, 12));
        jScrollPane1.setViewportView(Tabla1);
    }

    public final void verDatos() {
        try {
            Connection con = new Conexion().getConnection();
            String slq = "select * from ordentrabajo as ot "
                    + "inner join registroempleados as re on ot.responsable = re.numEmpleado "
                    + "order by idordentrabajo desc";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(slq);
            String datos[] = new String[10];
            DefaultTableModel miModelo = (DefaultTableModel) Tabla1.getModel();
            while (rs.next()) {
                datos[0] = rs.getString("idordentrabajo");
                datos[1] = rs.getString("departamento");
                datos[2] = rs.getString("nombre") + " " + rs.getString("apellido");
                datos[3] = rs.getString("inicio");
                datos[4] = rs.getString("termino");
                datos[5] = rs.getString("proyecto");
                datos[6] = rs.getString("estado");
                datos[7] = rs.getString("tiempo");
                miModelo.addRow(datos);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al ver datos: " + e, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public final void renaudarOrden(String inicio, String termino, String estado, String tiempo, String id, String comentarios) {
        try {
            Connection con = new Conexion().getConnection();
            String sql = "update ordentrabajo set inicio = ?, termino = ?, estado = ?, tiempo = ?, comentarios = CONCAT(COALESCE(comentarios, ''), ?) where idordentrabajo = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            String time = calcular(inicio, termino, tiempo);
            if (estado.equals("Pausado")) {
                inicio = null;
                termino = null;
            }

            pst.setString(1, inicio);
            pst.setString(2, termino);
            pst.setString(3, estado);
            pst.setString(4, time);
            pst.setString(5, comentarios);
            pst.setString(6, id);

            int n = pst.executeUpdate();

            if (n > 0) {
                JOptionPane.showMessageDialog(this, "Datos guardados correctamente");
                limpiarTabla();
                verDatos();
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al cambiar estado: " + e, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public String convertirNull(Object obj) {
        try {
            return obj.toString();
        } catch (Exception e) {
            return "";
        }
    }

    public final void cambiarResponsable(String id, String responsable, String de) {
        try {
            Connection con = new Conexion().getConnection();
            String sql = "update ordentrabajo set responsable = ?, comentarios = CONCAT(COALESCE(comentarios, ''), ?) where idordentrabajo = ?";
            PreparedStatement pst = con.prepareStatement(sql);

            pst.setString(1, responsable);
            pst.setString(2, "cambio de responsable de " + de + ", ");
            pst.setString(3, id);

            int n = pst.executeUpdate();

            if (n > 0) {
                JOptionPane.showMessageDialog(this, "Datos guardados correctamente");
                limpiarTabla();
                verDatos();
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al cambiar responsable: " + e, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public final void crearOT() {
        JFrame f = (JFrame) JOptionPane.getFrameForComponent(this);
        CrearOrdenTrabajo crear = new CrearOrdenTrabajo(f, true, this.numEmpleado);
        crear.txtProyecto.setEditable(true);
        crear.txtResponsable.setEditable(true);
        crear.setLocationRelativeTo(f);
        crear.setVisible(true);
    }

    public OrdenTrabajo(String numEmpleado) {
        initComponents();
        this.numEmpleado = numEmpleado;
        limpiarTabla();
        verDatos();
        ((javax.swing.plaf.basic.BasicInternalFrameUI) this.getUI()).setNorthPane(null);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupMenu1 = new javax.swing.JPopupMenu();
        btnCancelar = new javax.swing.JMenuItem();
        btnRenaudar = new javax.swing.JMenuItem();
        btnPausar = new javax.swing.JMenuItem();
        btnCambio = new javax.swing.JMenuItem();
        jPanel1 = new javax.swing.JPanel();
        jPanel24 = new javax.swing.JPanel();
        jPanel25 = new javax.swing.JPanel();
        jPanel26 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        pan = new javax.swing.JPanel();
        panelSalir = new javax.swing.JPanel();
        lblSalir = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        Tabla1 = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();

        jPopupMenu1.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
                jPopupMenu1PopupMenuWillBecomeVisible(evt);
            }
        });

        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/stop.png"))); // NOI18N
        btnCancelar.setText("Cancelar orden de trabajo               ");
        btnCancelar.setEnabled(false);
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });
        jPopupMenu1.add(btnCancelar);

        btnRenaudar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/play.png"))); // NOI18N
        btnRenaudar.setText("Renaudar orden de trabajo");
        btnRenaudar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRenaudarActionPerformed(evt);
            }
        });
        jPopupMenu1.add(btnRenaudar);

        btnPausar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/pause.png"))); // NOI18N
        btnPausar.setText("Pausar orden de trabajo");
        btnPausar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPausarActionPerformed(evt);
            }
        });
        jPopupMenu1.add(btnPausar);

        btnCambio.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/empleado.png"))); // NOI18N
        btnCambio.setText("Cambiar usuario responsable                     ");
        btnCambio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCambioActionPerformed(evt);
            }
        });
        jPopupMenu1.add(btnCambio);

        setBackground(new java.awt.Color(255, 255, 255));
        setBorder(null);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new java.awt.BorderLayout(0, 10));

        jPanel24.setBackground(new java.awt.Color(255, 255, 255));
        jPanel24.setLayout(new java.awt.BorderLayout());

        jPanel25.setBackground(new java.awt.Color(255, 255, 255));
        jPanel25.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jPanel26.setBackground(new java.awt.Color(255, 255, 255));

        jLabel12.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(0, 102, 204));
        jLabel12.setText("Ordenes de trabajo");
        jPanel26.add(jLabel12);

        jPanel25.add(jPanel26);

        jPanel24.add(jPanel25, java.awt.BorderLayout.CENTER);

        pan.setBackground(new java.awt.Color(255, 255, 255));

        panelSalir.setBackground(new java.awt.Color(255, 255, 255));

        lblSalir.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        lblSalir.setForeground(new java.awt.Color(0, 0, 0));
        lblSalir.setText(" X ");
        lblSalir.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblSalir.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblSalirMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblSalirMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblSalirMouseExited(evt);
            }
        });
        panelSalir.add(lblSalir);

        pan.add(panelSalir);

        jPanel24.add(pan, java.awt.BorderLayout.EAST);

        jPanel1.add(jPanel24, java.awt.BorderLayout.NORTH);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new java.awt.BorderLayout(0, 10));

        jScrollPane1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));

        Tabla1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        Tabla1.setComponentPopupMenu(jPopupMenu1);
        jScrollPane1.setViewportView(Tabla1);

        jPanel2.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jPanel3.setBackground(new java.awt.Color(204, 204, 204));
        jPanel3.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jButton1.setBackground(new java.awt.Color(0, 102, 204));
        jButton1.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Crear OT");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton1);

        jPanel2.add(jPanel3, java.awt.BorderLayout.PAGE_START);

        jPanel1.add(jPanel2, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void lblSalirMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSalirMouseClicked
        dispose();
    }//GEN-LAST:event_lblSalirMouseClicked

    private void lblSalirMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSalirMouseEntered
        panelSalir.setBackground(Color.red);
        lblSalir.setForeground(Color.white);
    }//GEN-LAST:event_lblSalirMouseEntered

    private void lblSalirMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSalirMouseExited
        panelSalir.setBackground(Color.white);
        lblSalir.setForeground(Color.black);
    }//GEN-LAST:event_lblSalirMouseExited

    private void btnRenaudarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRenaudarActionPerformed

    }//GEN-LAST:event_btnRenaudarActionPerformed

    private void jPopupMenu1PopupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_jPopupMenu1PopupMenuWillBecomeVisible
        int row = Tabla1.getSelectedRow();
        if (row >= 0) {
            if (convertirNull(Tabla1.getValueAt(row, 3)).equals("") || convertirNull(Tabla1.getValueAt(row, 4)).equals("")) {
                btnRenaudar.setEnabled(false);
            } else {
                btnRenaudar.setEnabled(true);
            }
            if (convertirNull(Tabla1.getValueAt(row, 3)).equals("") || convertirNull(Tabla1.getValueAt(row, 4)).equals("")) {
                btnPausar.setEnabled(false);
                if (!convertirNull(Tabla1.getValueAt(row, 3)).equals("") && convertirNull(Tabla1.getValueAt(row, 4)).equals("")) {
                    btnPausar.setEnabled(true);
                }
            } else {
                btnPausar.setEnabled(false);
            }
            if (convertirNull(Tabla1.getValueAt(row, 3)).equals("") || convertirNull(Tabla1.getValueAt(row, 4)).equals("")) {
                btnCancelar.setEnabled(true);
            } else {
                btnCancelar.setEnabled(false);
            }
        } else {
            btnCancelar.setEnabled(false);
            btnRenaudar.setEnabled(false);
            btnPausar.setEnabled(false);
            btnCambio.setEnabled(false);
        }
    }//GEN-LAST:event_jPopupMenu1PopupMenuWillBecomeVisible

    private void btnPausarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPausarActionPerformed
        JFrame f = (JFrame) JOptionPane.getFrameForComponent(this);
        razon raz = new razon(f, true, null);
        raz.setLocationRelativeTo(f);
        raz.jRadioButton1.setText("Cliente");
        raz.jRadioButton2.setText("Empleado");
        raz.jRadioButton3.setText("Empresa");
        raz.jRadioButton4.setText("Otro");
        String ra[] = raz.getRazon();
        if (!ra[0].equals("")) {
            int row = Tabla1.getSelectedRow();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            renaudarOrden(convertirNull(Tabla1.getValueAt(row, 3)), sdf.format(new Date()), "Pausado", convertirNull(Tabla1.getValueAt(row, 7)), convertirNull(Tabla1.getValueAt(row, 0)), ra[1] + " - " + ra[0] + ", ");
        } else {
            JOptionPane.showMessageDialog(this, "Debes llenar correctamente el reporte para pausar esta operacion", "Advertencia", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnPausarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        JFrame f = (JFrame) JOptionPane.getFrameForComponent(this);
        razon raz = new razon(f, true, null);
        raz.setLocationRelativeTo(f);
        raz.jRadioButton1.setText("Cliente");
        raz.jRadioButton2.setText("Empleado");
        raz.jRadioButton3.setText("Empresa");
        raz.jRadioButton4.setText("Otro");
        String ra[] = raz.getRazon();
        if (!ra[0].equals("")) {
            int row = Tabla1.getSelectedRow();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String fecha = sdf.format(new Date());
            String fechaInicio = convertirNull(Tabla1.getValueAt(row, 3));
            String fechaFin = convertirNull(Tabla1.getValueAt(row, 4));
            String tiempo = convertirNull(Tabla1.getValueAt(row, 7));
            if (fechaInicio.equals("")) {
                fechaInicio = fecha;
                fechaFin = fecha;
            }
            if (fechaFin.equals("")) {
                fechaFin = fecha;
            }
            renaudarOrden(fechaInicio, fechaFin, "Cancelado", calcular(fechaInicio, fechaFin, tiempo), convertirNull(Tabla1.getValueAt(row, 0)), ra[1] + " - " + ra[0] + ", ");
        } else {
            JOptionPane.showMessageDialog(this, "Debes llenar correctamente el reporte para cancelar esta operacion", "Advertencia", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnCambioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCambioActionPerformed
        JFrame f = (JFrame) JOptionPane.getFrameForComponent(this);
        CambioEmpleado cambio = new CambioEmpleado(f, true);
        cambio.setLocationRelativeTo(f);
        String empleado = cambio.getEmpleado();
        if (empleado != null && !empleado.equals("")) {
            cambiarResponsable(Tabla1.getValueAt(Tabla1.getSelectedRow(), 0).toString(), empleado.split("-")[1].trim(), Tabla1.getValueAt(Tabla1.getSelectedRow(), 2).toString());
        }
    }//GEN-LAST:event_btnCambioActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        crearOT();
    }//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable Tabla1;
    private javax.swing.JMenuItem btnCambio;
    private javax.swing.JMenuItem btnCancelar;
    private javax.swing.JMenuItem btnPausar;
    private javax.swing.JMenuItem btnRenaudar;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel24;
    private javax.swing.JPanel jPanel25;
    private javax.swing.JPanel jPanel26;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblSalir;
    private javax.swing.JPanel pan;
    private javax.swing.JPanel panelSalir;
    // End of variables declaration//GEN-END:variables
}
