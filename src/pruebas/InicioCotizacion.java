package pruebas;

import Conexiones.Conexion;
import VentanaEmergente.Cotizacion.AgregarCotizacion;
import VentanaEmergente.Cotizacion.ConfUsuario;
import VentanaEmergente.Cotizacion.TablaCoti;
import com.formdev.flatlaf.themes.FlatMacLightLaf;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Window;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;

public class InicioCotizacion extends javax.swing.JInternalFrame {

    public final String numEmpleado;

    public final void insertarDatos(String cliente, String vendedor, String total, String estado) {
        String sql = "INSERT INTO cotizacion(fecha, cliente, vendedor, total, estado) VALUES (?,?,?,?,?)";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try (Connection conn = new Conexion().getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, sdf.format(new Date()));
            ps.setString(2, cliente);
            ps.setString(3, vendedor);
            ps.setString(4, total);
            ps.setString(5, estado);
            ps.executeUpdate();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al insetar datos: " + ex, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public final void buscarDatos() {
        try {
            Connection con = new Conexion().getConnection();
            Statement st = con.createStatement();
            String sql = "select * from cotizacion as co "
                    + "inner join clientes_cotizacion as cl on cl.idcliente = co.idcliente "
                    + "inner join registroempleados as re on re.NumEmpleado = co.NumEmpleado "
                    + "order by idcotizacion desc";
            ResultSet rs = st.executeQuery(sql);
            String datos[] = new String[10];
            DefaultTableModel miModelo = (DefaultTableModel) Tabla1.getModel();
            DecimalFormat df = new DecimalFormat("$ #,###.##");
            while (rs.next()) {
                datos[0] = rs.getString("idcotizacion");
                datos[1] = rs.getString("fecha");
                datos[2] = rs.getString("cl.nombre");
                datos[3] = rs.getString("re.nombre") + rs.getString("re.apellido");
                datos[4] = df.format(rs.getFloat("total"));
                datos[5] = rs.getString("estado");
                datos[6] = rs.getString("iva");
                miModelo.addRow(datos);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al buscar datos: " + e, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public final void verCotizacion() {
        Window ventana = SwingUtilities.getWindowAncestor(this);
        JFrame f = (JFrame) ventana;
        AgregarCotizacion add = new AgregarCotizacion(f, true, numEmpleado, false);
        add.limpiarTabla();
        int fila = Tabla1.getSelectedRow();
        add.verItems(Tabla1.getValueAt(fila, 0).toString());
        add.verCliente(Tabla1.getValueAt(fila, 2).toString());
        add.agregarClientes();
        add.cmbIva.setSelectedItem(Tabla1.getValueAt(fila, 6).toString());
        add.txtCreacion.setText(Tabla1.getValueAt(fila, 1).toString());
        add.setVisible(true);
        limpiarTabla();
        buscarDatos();
    }

    public final void limpiarTabla() {
        Tabla1 = new TablaCoti();
        Tabla1.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{},
                new String[]{
                    "#", "Fecha de creacion", "Cliente", "Vendedor", "Total", "Estado", "Iva"
                }
        ) {
            boolean[] canEdit = new boolean[]{
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });

        if (Tabla1.getColumnModel().getColumnCount() > 0) {
            Tabla1.getColumnModel().getColumn(0).setMinWidth(50);
            Tabla1.getColumnModel().getColumn(0).setPreferredWidth(50);
            Tabla1.getColumnModel().getColumn(0).setMaxWidth(50);
            Tabla1.getColumnModel().getColumn(4).setMinWidth(150);
            Tabla1.getColumnModel().getColumn(4).setPreferredWidth(150);
            Tabla1.getColumnModel().getColumn(4).setMaxWidth(150);
            Tabla1.getColumnModel().getColumn(5).setMinWidth(100);
            Tabla1.getColumnModel().getColumn(5).setPreferredWidth(100);
            Tabla1.getColumnModel().getColumn(5).setMaxWidth(100);
            Tabla1.getColumnModel().getColumn(6).setMinWidth(100);
            Tabla1.getColumnModel().getColumn(6).setPreferredWidth(100);
            Tabla1.getColumnModel().getColumn(6).setMaxWidth(100);
        }

        Tabla1.getTableHeader().setFont(new Font("Trebuchet MS", Font.BOLD, 14));
        Tabla1.setFont(new Font("Trebuchet MS", Font.PLAIN, 12));
        jScrollPane1.setViewportView(Tabla1);
        Tabla1.setComponentPopupMenu(jPopupMenu1);
        Tabla1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    verCotizacion();
                }
            }
        });
    }

    public InicioCotizacion(String numEmpleado) {
        initComponents();
        try {
            UIManager.setLookAndFeel(new FlatMacLightLaf());
        } catch (Exception e) {
            e.printStackTrace();
        }
        initComponents();
        ((javax.swing.plaf.basic.BasicInternalFrameUI) this.getUI()).setNorthPane(null);
        limpiarTabla();
        buscarDatos();
        this.numEmpleado = numEmpleado;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPopupMenu1 = new javax.swing.JPopupMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();
        jPanel1 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        pan = new javax.swing.JPanel();
        panelSalir = new javax.swing.JPanel();
        lblSalir = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        btnAgregar = new javax.swing.JButton();
        btnConfig = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        Tabla1 = new javax.swing.JTable();

        jMenuItem1.setText("Ver cotizacion                                ");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jPopupMenu1.add(jMenuItem1);

        jMenuItem2.setText("Imprimir cotizacion");
        jPopupMenu1.add(jMenuItem2);

        jMenuItem3.setText("Enviar cotizacion");
        jPopupMenu1.add(jMenuItem3);

        jMenuItem4.setText("Cancelar cotizacion");
        jPopupMenu1.add(jMenuItem4);

        setBorder(null);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new java.awt.BorderLayout());

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setLayout(new java.awt.BorderLayout());

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 50, 5));

        jLabel12.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(0, 165, 252));
        jLabel12.setText("Cotizaciones");
        jPanel5.add(jLabel12);

        jPanel4.add(jPanel5, java.awt.BorderLayout.CENTER);

        pan.setBackground(new java.awt.Color(255, 255, 255));

        panelSalir.setBackground(new java.awt.Color(255, 255, 255));

        lblSalir.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
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

        jPanel4.add(pan, java.awt.BorderLayout.EAST);

        jPanel1.add(jPanel4, java.awt.BorderLayout.NORTH);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new java.awt.BorderLayout());

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setLayout(new java.awt.BorderLayout());

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setLayout(new java.awt.BorderLayout());
        jPanel6.add(jLabel1, java.awt.BorderLayout.WEST);

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 25, 5));

        btnAgregar.setBackground(new java.awt.Color(0, 102, 204));
        btnAgregar.setFont(new java.awt.Font("Trebuchet MS", 1, 12)); // NOI18N
        btnAgregar.setForeground(new java.awt.Color(255, 255, 255));
        btnAgregar.setText("Agregar");
        btnAgregar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarActionPerformed(evt);
            }
        });
        jPanel7.add(btnAgregar);

        btnConfig.setBackground(new java.awt.Color(51, 51, 51));
        btnConfig.setFont(new java.awt.Font("Trebuchet MS", 1, 12)); // NOI18N
        btnConfig.setForeground(new java.awt.Color(255, 255, 255));
        btnConfig.setText("Configurar");
        btnConfig.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnConfig.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConfigActionPerformed(evt);
            }
        });
        jPanel7.add(btnConfig);

        jPanel6.add(jPanel7, java.awt.BorderLayout.CENTER);

        jLabel2.setFont(new java.awt.Font("Trebuchet MS", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 153, 255));
        jLabel2.setText("J S  ");
        jPanel6.add(jLabel2, java.awt.BorderLayout.EAST);

        jPanel3.add(jPanel6, java.awt.BorderLayout.PAGE_START);

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));
        java.awt.GridBagLayout jPanel8Layout = new java.awt.GridBagLayout();
        jPanel8Layout.columnWeights = new double[] {1.0};
        jPanel8Layout.rowWeights = new double[] {1.0};
        jPanel8.setLayout(jPanel8Layout);

        jScrollPane1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));

        Tabla1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Numero", "Fecha de creacion", "Cliente", "Vendedor", "Total", "Estado"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, true, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        Tabla1.setComponentPopupMenu(jPopupMenu1);
        jScrollPane1.setViewportView(Tabla1);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(27, 27, 27, 27);
        jPanel8.add(jScrollPane1, gridBagConstraints);

        jPanel3.add(jPanel8, java.awt.BorderLayout.CENTER);

        jPanel2.add(jPanel3, java.awt.BorderLayout.CENTER);

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

    private void btnAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarActionPerformed
        JFrame f = (JFrame) JOptionPane.getFrameForComponent(this);
        AgregarCotizacion add = new AgregarCotizacion(f, true, numEmpleado, true);
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        add.txtCreacion.setText(sdf.format(new Date()));
        add.setVisible(true);
        limpiarTabla();
        buscarDatos();
    }//GEN-LAST:event_btnAgregarActionPerformed

    private void btnConfigActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConfigActionPerformed
        JFrame f = (JFrame) JOptionPane.getFrameForComponent(this);
        ConfUsuario conf = new ConfUsuario(f, true);
        conf.setPreferredSize(new Dimension(491, 672));
        conf.setLocationRelativeTo(this);
        conf.setVisible(true);
    }//GEN-LAST:event_btnConfigActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        verCotizacion();
    }//GEN-LAST:event_jMenuItem1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable Tabla1;
    private javax.swing.JButton btnAgregar;
    private javax.swing.JButton btnConfig;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblSalir;
    private javax.swing.JPanel pan;
    private javax.swing.JPanel panelSalir;
    // End of variables declaration//GEN-END:variables
}
