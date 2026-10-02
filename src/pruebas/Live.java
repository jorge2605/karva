package pruebas;

import Conexiones.Conexion;
import grafica.PieChartPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import org.jdesktop.swingx.JXTable;
import org.knowm.xchart.PieChart;

public class Live extends javax.swing.JInternalFrame {

    public String numEmpleado;

    public final void limpiarTabla(JXTable tabla) {
        DefaultTableModel miModelo = (DefaultTableModel) tabla.getModel();
        for (int i = tabla.getRowCount() - 1; i >= 0; i--) {
            miModelo.removeRow(i);
        }
        tabla.setRowHeight(20);
        tabla.setFont(new Font("Roboto", Font.PLAIN, 12));
        tabla.setColumnControlVisible(false);
        tabla.setShowGrid(false);
        tabla.setIntercellSpacing(new java.awt.Dimension(0, 1));
        tabla.setFillsViewportHeight(true);
        tabla.setBorder(BorderFactory.createEmptyBorder());
        tabla.getTableHeader().setFont(new Font("Roboto", Font.BOLD, 14));
        tabla.getTableHeader().setPreferredSize(new java.awt.Dimension(0, 25));
        tabla.getTableHeader().setReorderingAllowed(false);
        tabla.setSelectionBackground(new Color(0, 112, 192));
        tabla.setSelectionForeground(Color.WHITE);
        DefaultTableCellRenderer centro = new DefaultTableCellRenderer();
        centro.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < tabla.getColumnCount(); i++) {
            tabla.getColumnModel().getColumn(i).setCellRenderer(centro);
        }
    }

    public final void verDatos() {
        try {
            Connection con = new Conexion().getConnection();
            Statement st = con.createStatement();
            String sql = "select plano, id, estado from planos order by id desc limit 500";
            ResultSet rs = st.executeQuery(sql);
            String datos[] = new String[10];
            DefaultTableModel miModelo = (DefaultTableModel) tabla1.getModel();
            while (rs.next()) {
                datos[0] = rs.getString("Plano");
//                datos[1] = rs.getString("");
//                datos[2] = rs.getString("");
                datos[3] = rs.getString("estado");
                miModelo.addRow(datos);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al ver datos: " + e, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public final void agregarBubbleChart() {
        PieChartPanel grafica = new PieChartPanel();
        grafica.agregarRebanada("Retrazo: 15", 30, new Color(255, 39, 39));
        grafica.agregarRebanada("A tiempo: 60", 70, new Color(42, 167, 63));
        grafica.agregarRebanada("Activo: 60", 70, new Color(177, 195, 41));
        jPanel7.add(grafica);
        jPanel7.revalidate();
        jPanel7.repaint();
    }

    public Live(String numEmpleado) {
        initComponents();
        this.numEmpleado = numEmpleado;
        limpiarTabla(tabla1);
        limpiarTabla(tabla2);
        verDatos();
        agregarBubbleChart();
        ((javax.swing.plaf.basic.BasicInternalFrameUI) this.getUI()).setNorthPane(null);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jPanel13 = new javax.swing.JPanel();
        btnSalir = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabla1 = new org.jdesktop.swingx.JXTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        tabla2 = new org.jdesktop.swingx.JXTable();
        jPanel5 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        jRadioButton3 = new javax.swing.JRadioButton();
        jRadioButton4 = new javax.swing.JRadioButton();
        jRadioButton5 = new javax.swing.JRadioButton();
        jRadioButton6 = new javax.swing.JRadioButton();
        jRadioButton7 = new javax.swing.JRadioButton();
        jRadioButton8 = new javax.swing.JRadioButton();
        jPanel7 = new javax.swing.JPanel();

        setBorder(null);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new java.awt.BorderLayout());

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new java.awt.BorderLayout());

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jLabel15.setFont(new java.awt.Font("Lexend", 1, 14)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(0, 102, 204));
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel15.setText("Dashboard");
        jPanel3.add(jLabel15);

        jPanel2.add(jPanel3, java.awt.BorderLayout.CENTER);

        jPanel13.setBackground(new java.awt.Color(255, 255, 255));

        btnSalir.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("  X  ");
        jLabel1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel1MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel1MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel1MouseExited(evt);
            }
        });
        btnSalir.add(jLabel1);

        jPanel13.add(btnSalir);

        jPanel2.add(jPanel13, java.awt.BorderLayout.EAST);

        jPanel1.add(jPanel2, java.awt.BorderLayout.PAGE_START);

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        java.awt.GridBagLayout jPanel4Layout = new java.awt.GridBagLayout();
        jPanel4Layout.columnWeights = new double[] {1.0, 0.0};
        jPanel4Layout.rowWeights = new double[] {0.0, 0.0, 1.0};
        jPanel4.setLayout(jPanel4Layout);

        jLabel4.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 102, 204));
        jLabel4.setText("Activos");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 10, 0);
        jPanel4.add(jLabel4, gridBagConstraints);

        jScrollPane2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));

        tabla1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Numero de parte", "Proyecto", "Fecha de requerimiento", "Fecha de aprobacion", "Fecha de entrega", "Dias restantes"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(tabla1);
        if (tabla1.getColumnModel().getColumnCount() > 0) {
            tabla1.getColumnModel().getColumn(4).setHeaderValue("Fecha de entrega");
            tabla1.getColumnModel().getColumn(5).setHeaderValue("Dias restantes");
        }

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 5);
        jPanel4.add(jScrollPane2, gridBagConstraints);

        jScrollPane3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));

        tabla2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Fecha", "Inicio", "Fin", "Tiempo"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane3.setViewportView(tabla2);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = -100;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 5);
        jPanel4.add(jScrollPane3, gridBagConstraints);

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setLayout(new java.awt.BorderLayout());

        jLabel5.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 102, 204));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Departamento");
        jPanel5.add(jLabel5, java.awt.BorderLayout.NORTH);

        jPanel6.setBackground(new java.awt.Color(245, 245, 245));

        buttonGroup1.add(jRadioButton1);
        jPanel6.add(jRadioButton1);

        buttonGroup1.add(jRadioButton2);
        jPanel6.add(jRadioButton2);

        buttonGroup1.add(jRadioButton3);
        jPanel6.add(jRadioButton3);

        buttonGroup1.add(jRadioButton4);
        jPanel6.add(jRadioButton4);

        buttonGroup1.add(jRadioButton5);
        jPanel6.add(jRadioButton5);

        buttonGroup1.add(jRadioButton6);
        jPanel6.add(jRadioButton6);

        buttonGroup1.add(jRadioButton7);
        jPanel6.add(jRadioButton7);

        buttonGroup1.add(jRadioButton8);
        jPanel6.add(jRadioButton8);

        jPanel5.add(jPanel6, java.awt.BorderLayout.CENTER);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 1;
        jPanel4.add(jPanel5, gridBagConstraints);

        jPanel1.add(jPanel4, java.awt.BorderLayout.CENTER);

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setPreferredSize(new java.awt.Dimension(100, 350));
        jPanel7.setLayout(new java.awt.BorderLayout());
        jPanel1.add(jPanel7, java.awt.BorderLayout.PAGE_END);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseClicked
        dispose();
    }//GEN-LAST:event_jLabel1MouseClicked

    private void jLabel1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseEntered
        btnSalir.setBackground(java.awt.Color.red);
    }//GEN-LAST:event_jLabel1MouseEntered

    private void jLabel1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseExited
        btnSalir.setBackground(java.awt.Color.white);
    }//GEN-LAST:event_jLabel1MouseExited


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel btnSalir;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JRadioButton jRadioButton3;
    private javax.swing.JRadioButton jRadioButton4;
    private javax.swing.JRadioButton jRadioButton5;
    private javax.swing.JRadioButton jRadioButton6;
    private javax.swing.JRadioButton jRadioButton7;
    private javax.swing.JRadioButton jRadioButton8;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private org.jdesktop.swingx.JXTable tabla1;
    private org.jdesktop.swingx.JXTable tabla2;
    // End of variables declaration//GEN-END:variables
}
