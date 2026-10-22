package VentanaEmergente.Cotizacion;

import Conexiones.Conexion;
import java.awt.Font;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class ConfiguracionCodigos extends javax.swing.JDialog {

    public final void limpiarTabla() {
        Tabla1 = new TablaCoti();
        Tabla1.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{},
                new String[]{
                    "Numero de parte", "Descripcion", "Precio real", "Margen", "Activo", "ID"
                }
        ) {
            Class[] types = new Class[]{
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Integer.class, java.lang.Boolean.class,
                java.lang.Object.class
            };
            boolean[] canEdit = new boolean[]{
                false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
        Tabla1.setComponentPopupMenu(jPopupMenu1);
        Tabla1.getTableHeader().setFont(new Font("Roboto", Font.BOLD, 12));
        Tabla1.setFont(new Font("Roboto", Font.PLAIN, 12));
        jScrollPane1.setViewportView(Tabla1);

    }

    public final void verDatos() {
        try {
            limpiarTabla();
            Connection con = new Conexion().getConnection();
            String sql = "select * from codigoscotizaciones order by codigo asc";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            Object[] datos = new Object[10];
            DefaultTableModel miModelo = (DefaultTableModel) Tabla1.getModel();
            while (rs.next()) {
                datos[0] = rs.getString("codigo");
                datos[1] = rs.getString("descripcion");
                datos[2] = rs.getString("precio_real");
                datos[3] = rs.getString("margen");
                datos[4] = rs.getBoolean("activo");
                datos[5] = rs.getString("idcodigoscotizaciones");
                miModelo.addRow(datos);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al ver datos: " + e, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public String conNull(Object obj) {
        try {
            return obj.toString();
        } catch (Exception e) {
            return "";
        }
    }

    public final void abrirEditar(boolean compras) {
        int fila = Tabla1.getSelectedRow();
        if (fila >= 0) {
            JFrame f = (JFrame) JOptionPane.getFrameForComponent(this);
            EditarCodigo edit = new EditarCodigo(f, true);
            edit.txtCodigo.setText(conNull(Tabla1.getValueAt(fila, 0)));
            edit.txtDescripcion.setText(conNull(Tabla1.getValueAt(fila, 1)));
            edit.txtPrecio.setText(conNull(Tabla1.getValueAt(fila, 2)));
            edit.txtMargen.setText(conNull(Tabla1.getValueAt(fila, 3)));
            edit.activo.setSelected((boolean) Tabla1.getValueAt(fila, 4));
            edit.lblId.setText(conNull(Tabla1.getValueAt(fila, 5)));
            edit.setLocationRelativeTo(f);
            if (compras) {
                edit.agregarCompras();
            }
            edit.setVisible(true);
            verDatos();
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

    public final void desactivarCodigo() {
        try {
            Connection con = new Conexion().getConnection();
            String sql = "update codigoscotizaciones set activo = ? where idcodigoscotizaciones = ?";
            PreparedStatement pst = con.prepareStatement(sql);

            int n = 0;
            for (int i = 0; i < Tabla1.getSelectedRows().length; i++) {
                pst.setBoolean(1, !(boolean) Tabla1.getValueAt(Tabla1.getSelectedRows()[i], 4));
                pst.setString(2, Tabla1.getValueAt(Tabla1.getSelectedRows()[i], 5).toString());
                
                n += pst.executeUpdate();
            }

            if (n > 0) {
                verGuardado();
                limpiarTabla();
                verDatos();
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al descativar numero de parte: " + e, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public ConfiguracionCodigos(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        verDatos();
        lblguardado.setVisible(false);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupMenu1 = new javax.swing.JPopupMenu();
        btnEditar = new javax.swing.JMenuItem();
        btnDesactivar = new javax.swing.JMenuItem();
        jPanel1 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        btnConfig1 = new javax.swing.JButton();
        btnConfig2 = new javax.swing.JButton();
        lblguardado = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        Tabla1 = new javax.swing.JTable();

        jPopupMenu1.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
                jPopupMenu1PopupMenuWillBecomeVisible(evt);
            }
        });

        btnEditar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/modificar_16.png"))); // NOI18N
        btnEditar.setText("Editar numero de parte");
        btnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarActionPerformed(evt);
            }
        });
        jPopupMenu1.add(btnEditar);

        btnDesactivar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/circulo rojo.png"))); // NOI18N
        btnDesactivar.setText("Activar / Desactivar numero de parte                ");
        btnDesactivar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDesactivarActionPerformed(evt);
            }
        });
        jPopupMenu1.add(btnDesactivar);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(1000, 700));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new java.awt.BorderLayout());

        jLabel12.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(0, 102, 204));
        jLabel12.setText("      Configuracion cotizaciones");
        jPanel1.add(jLabel12, java.awt.BorderLayout.NORTH);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new java.awt.BorderLayout());

        jPanel3.setBackground(new java.awt.Color(245, 245, 245));
        jPanel3.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        btnConfig1.setBackground(new java.awt.Color(0, 102, 204));
        btnConfig1.setFont(new java.awt.Font("Trebuchet MS", 1, 12)); // NOI18N
        btnConfig1.setForeground(new java.awt.Color(255, 255, 255));
        btnConfig1.setText("Agregar numero de parte");
        btnConfig1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnConfig1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConfig1ActionPerformed(evt);
            }
        });
        jPanel3.add(btnConfig1);

        btnConfig2.setBackground(new java.awt.Color(0, 102, 204));
        btnConfig2.setFont(new java.awt.Font("Trebuchet MS", 1, 12)); // NOI18N
        btnConfig2.setForeground(new java.awt.Color(255, 255, 255));
        btnConfig2.setText("Importar numero de parte de compras");
        btnConfig2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnConfig2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConfig2ActionPerformed(evt);
            }
        });
        jPanel3.add(btnConfig2);

        lblguardado.setFont(new java.awt.Font("Trebuchet MS", 1, 12)); // NOI18N
        lblguardado.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IconoC/cheque_16.png"))); // NOI18N
        lblguardado.setText("Datos guardados correctamente");
        jPanel3.add(lblguardado);

        jPanel2.add(jPanel3, java.awt.BorderLayout.PAGE_START);

        Tabla1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Numero de parte", "Descripcion", "Precio real", "Margen", "Activo"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Integer.class, java.lang.Boolean.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        Tabla1.setComponentPopupMenu(jPopupMenu1);
        jScrollPane1.setViewportView(Tabla1);

        jPanel2.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jPanel1.add(jPanel2, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnConfig1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConfig1ActionPerformed
        JFrame f = (JFrame) JOptionPane.getFrameForComponent(this);
        EditarCodigo edit = new EditarCodigo(f, true);
        edit.setLocationRelativeTo(f);
        edit.setVisible(true);
    }//GEN-LAST:event_btnConfig1ActionPerformed

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        abrirEditar(false);
    }//GEN-LAST:event_btnEditarActionPerformed

    private void btnConfig2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConfig2ActionPerformed
        JFrame f = (JFrame) JOptionPane.getFrameForComponent(this);
        EditarCodigo edit = new EditarCodigo(f, true);
        edit.setLocationRelativeTo(f);
        edit.agregarCompras();
        edit.setVisible(true);
    }//GEN-LAST:event_btnConfig2ActionPerformed

    private void btnDesactivarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDesactivarActionPerformed
        desactivarCodigo();
    }//GEN-LAST:event_btnDesactivarActionPerformed

    private void jPopupMenu1PopupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_jPopupMenu1PopupMenuWillBecomeVisible
        if ((boolean) Tabla1.getValueAt(Tabla1.getSelectedRow(), 4)) {
            btnDesactivar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/circulo rojo.png"))); // NOI18N
            btnDesactivar.setText("Desactivar numero de parte                ");
        } else {
            btnDesactivar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/circulo verde.png"))); // NOI18N
            btnDesactivar.setText("Activar numero de parte                ");
        }
    }//GEN-LAST:event_jPopupMenu1PopupMenuWillBecomeVisible

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            ConfiguracionCodigos dialog = new ConfiguracionCodigos(new javax.swing.JFrame(), true);
            dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosing(java.awt.event.WindowEvent e) {
                    System.exit(0);
                }
            });
            dialog.setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable Tabla1;
    private javax.swing.JButton btnConfig1;
    private javax.swing.JButton btnConfig2;
    private javax.swing.JMenuItem btnDesactivar;
    private javax.swing.JMenuItem btnEditar;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblguardado;
    // End of variables declaration//GEN-END:variables
}
