package VentanaEmergente.Inicio1;

import Conexiones.Conexion;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class ReporteError extends javax.swing.JDialog {

    String numEmpleado;

    public void verDatos() {
        try {
            Connection con = null;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            DefaultTableModel miModelo = (DefaultTableModel) Tabla1.getModel();
            String sql = "select * from reporteerror where NumEmpleado like '" + numEmpleado + "'";
            ResultSet rs = st.executeQuery(sql);
            String datos[] = new String[10];

            while (rs.next()) {
                datos[0] = rs.getString("Id");
                datos[1] = rs.getString("Fecha");
                miModelo.addRow(datos);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "ERROR: " + e, "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void limpiar() {
        txtAsunto.setText("");
        txtComentario.setText("");
    }

    public void limpiarTabla() {
        Tabla1.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{},
                new String[]{
                    "No. Error", "Fecha"
                }
        ) {
            boolean[] canEdit = new boolean[]{
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });

        Tabla1.setMaximumSize(new java.awt.Dimension(2147483647, 800));

        Tabla1.setMinimumSize(new java.awt.Dimension(200, 800));

        Tabla1.setPreferredSize(new java.awt.Dimension(200, 800));

        jScrollPane3.setViewportView(Tabla1);

        if (Tabla1.getColumnModel().getColumnCount() > 0) {
            Tabla1.getColumnModel().getColumn(0).setMinWidth(50);
            Tabla1.getColumnModel().getColumn(0).setPreferredWidth(50);
            Tabla1.getColumnModel().getColumn(0).setMaxWidth(50);
        }

    }

    public ReporteError(java.awt.Frame parent, boolean modal, String numEmpleado) {
        super(parent, modal);
        initComponents();
        this.numEmpleado = numEmpleado;
        verDatos();
        txtSolucion.setVisible(false);
        lblSolucion.setVisible(false);
        btnAgregar.setVisible(false);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        Tabla1 = new javax.swing.JTable();
        jPanel6 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtAsunto = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtComentario = new javax.swing.JTextArea();
        jLabel4 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        btnEnviar = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        lblSolucion = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtSolucion = new javax.swing.JTextArea();
        btnAgregar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new java.awt.BorderLayout());

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jPanel3.setBackground(new java.awt.Color(0, 165, 252));
        jPanel3.setMaximumSize(new java.awt.Dimension(300, 32767));
        jPanel3.setMinimumSize(new java.awt.Dimension(300, 63));
        jPanel3.setPreferredSize(new java.awt.Dimension(606, 70));

        jLabel12.setFont(new java.awt.Font("Roboto Light", 1, 45)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("REPORTE DE ERRORES");
        jPanel3.add(jLabel12);

        jPanel2.add(jPanel3);

        jPanel1.add(jPanel2, java.awt.BorderLayout.PAGE_START);

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setPreferredSize(new java.awt.Dimension(200, 537));
        jPanel4.setLayout(new java.awt.GridBagLayout());

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setMaximumSize(new java.awt.Dimension(32767, 50));
        jPanel5.setMinimumSize(new java.awt.Dimension(200, 30));
        jPanel5.setName(""); // NOI18N
        jPanel5.setPreferredSize(new java.awt.Dimension(200, 50));
        jPanel5.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 5, 1));

        jLabel1.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        jLabel1.setText("MIS REPORTES");
        jPanel5.add(jLabel1);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        jPanel4.add(jPanel5, gridBagConstraints);

        jScrollPane3.setMinimumSize(new java.awt.Dimension(200, 500));
        jScrollPane3.setPreferredSize(new java.awt.Dimension(300, 800));

        Tabla1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "No. Error", "Fecha"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        Tabla1.setMaximumSize(new java.awt.Dimension(2147483647, 800));
        Tabla1.setMinimumSize(new java.awt.Dimension(200, 800));
        Tabla1.setPreferredSize(new java.awt.Dimension(300, 800));
        Tabla1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Tabla1MouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(Tabla1);
        if (Tabla1.getColumnModel().getColumnCount() > 0) {
            Tabla1.getColumnModel().getColumn(0).setMinWidth(50);
            Tabla1.getColumnModel().getColumn(0).setPreferredWidth(50);
            Tabla1.getColumnModel().getColumn(0).setMaxWidth(50);
        }

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        jPanel4.add(jScrollPane3, gridBagConstraints);

        jPanel1.add(jPanel4, java.awt.BorderLayout.LINE_START);

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));

        jLabel2.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        jLabel2.setText("ASUNTO");

        txtAsunto.setBackground(new java.awt.Color(255, 255, 255));
        txtAsunto.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtAsunto.setBorder(null);
        txtAsunto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtAsuntoKeyPressed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        jLabel3.setText("COMENTARIO");

        txtComentario.setBackground(new java.awt.Color(255, 255, 255));
        txtComentario.setColumns(20);
        txtComentario.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtComentario.setLineWrap(true);
        txtComentario.setRows(5);
        txtComentario.setWrapStyleWord(true);
        jScrollPane1.setViewportView(txtComentario);

        jLabel4.setForeground(new java.awt.Color(204, 204, 204));
        jLabel4.setText("NOTA: Al precionar este boton se  guardara una captura de pantalla de lo que estara atras de esta pantalla");

        jPanel7.setBackground(new java.awt.Color(0, 140, 210));

        btnEnviar.setBackground(new java.awt.Color(255, 255, 255));
        btnEnviar.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        btnEnviar.setForeground(new java.awt.Color(255, 255, 255));
        btnEnviar.setText("ENVIAR");
        btnEnviar.setBorder(null);
        btnEnviar.setBorderPainted(false);
        btnEnviar.setContentAreaFilled(false);
        btnEnviar.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnEnviar.setFocusPainted(false);
        btnEnviar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnEnviarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnEnviarMouseExited(evt);
            }
        });
        btnEnviar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEnviarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnEnviar, javax.swing.GroupLayout.DEFAULT_SIZE, 81, Short.MAX_VALUE)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnEnviar, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
        );

        lblSolucion.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblSolucion.setText("SOLUCION:");

        txtSolucion.setBackground(new java.awt.Color(255, 255, 255));
        txtSolucion.setColumns(20);
        txtSolucion.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtSolucion.setLineWrap(true);
        txtSolucion.setRows(5);
        txtSolucion.setWrapStyleWord(true);
        jScrollPane2.setViewportView(txtSolucion);

        btnAgregar.setBackground(new java.awt.Color(255, 255, 255));
        btnAgregar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ImgAnimacion/agregar_32.png"))); // NOI18N
        btnAgregar.setBorder(null);
        btnAgregar.setBorderPainted(false);
        btnAgregar.setContentAreaFilled(false);
        btnAgregar.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnAgregar.setFocusPainted(false);
        btnAgregar.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnAgregar.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/ImgAnimacion/agregar_32.png"))); // NOI18N
        btnAgregar.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/ImgAnimacion/agregar_48.png"))); // NOI18N
        btnAgregar.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        btnAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(85, 85, 85)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnAgregar, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(lblSolucion)
                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel2)
                        .addComponent(txtAsunto)
                        .addComponent(jLabel3)
                        .addComponent(jScrollPane1)
                        .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 556, Short.MAX_VALUE)
                        .addComponent(jSeparator1)
                        .addComponent(jScrollPane2)))
                .addContainerGap(245, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addComponent(btnAgregar, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtAsunto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(9, 9, 9)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35)
                .addComponent(jLabel4)
                .addGap(18, 18, 18)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lblSolucion)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel6, java.awt.BorderLayout.CENTER);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 1086, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnEnviarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEnviarActionPerformed
        if (txtAsunto.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "DEBES AGREGAR EL ASUNTO", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
        } else if (txtComentario.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "DEBES AGREGAR UN COMENTARIO", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
        } else {
            this.setVisible(false);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(ReporteError.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(this, ex);
            }
            try {
                Robot robot = new Robot();
                Rectangle rect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());

                BufferedImage screesnShot = robot.createScreenCapture(rect);

                ImageIO.write(screesnShot, "JPG",
                        new File("BD/img/Screen.jpg"));

                Connection con = null;
                Conexion con1 = new Conexion();
                con = con1.getConnection();

                String sql = "insert into reporteerror (Asunto, Comentarios, Imagen, Solucion, NumEmpleado, Fecha) values(?,?,?,?,?,?)";
                PreparedStatement pst = con.prepareStatement(sql);
                File file = new File("BD/img/Screen.jpg");
                byte pe[] = new byte[(int) file.length()];

                try {
                    InputStream input = new FileInputStream(file);
                    input.read(pe);
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(this, "Error:" + e);
                }

                Date d = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat();
                String fecha = sdf.format(d);

                pst.setString(1, txtAsunto.getText());
                pst.setString(2, txtComentario.getText());
                pst.setBytes(3, pe);
                pst.setString(4, "");
                pst.setString(5, this.numEmpleado);
                pst.setString(6, fecha);

                int n = pst.executeUpdate();

                if (n > 0) {
                    JOptionPane.showMessageDialog(this, "REPORTE ENVIADO");
                    limpiar();
                    limpiarTabla();
                    verDatos();
                    txtSolucion.setVisible(false);
                    lblSolucion.setVisible(false);
                    btnAgregar.setVisible(false);
                }

            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, e);
            }
            this.setVisible(true);
        }
    }//GEN-LAST:event_btnEnviarActionPerformed

    private void btnEnviarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEnviarMouseEntered
        jPanel7.setBackground(new Color(0, 93, 139));
    }//GEN-LAST:event_btnEnviarMouseEntered

    private void btnEnviarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEnviarMouseExited
        jPanel7.setBackground(new Color(0, 140, 210));
    }//GEN-LAST:event_btnEnviarMouseExited

    private void Tabla1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Tabla1MouseClicked
        try {
            Connection con = null;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            String sql = "select * from reporteerror where Id like '" + Tabla1.getValueAt(Tabla1.getSelectedRow(), 0) + "'";
            ResultSet rs = st.executeQuery(sql);
            String datos[] = new String[10];

            while (rs.next()) {
                datos[0] = rs.getString("Asunto");
                datos[1] = rs.getString("Comentarios");
                datos[2] = rs.getString("Solucion");
            }
            if (datos[2].equals("")) {
                txtSolucion.setVisible(false);
                lblSolucion.setVisible(false);
            } else {
                txtSolucion.setVisible(true);
                lblSolucion.setVisible(true);
                txtSolucion.setText(datos[2]);
            }
            txtAsunto.setText(datos[0]);
            txtComentario.setText(datos[1]);
            btnEnviar.setEnabled(false);
            btnAgregar.setVisible(true);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error: " + e, "error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_Tabla1MouseClicked

    private void btnAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarActionPerformed
        btnAgregar.setVisible(false);
        btnEnviar.setEnabled(true);
        limpiar();
        lblSolucion.setVisible(false);
        txtSolucion.setVisible(false);
    }//GEN-LAST:event_btnAgregarActionPerformed

    private void txtAsuntoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAsuntoKeyPressed

    }//GEN-LAST:event_txtAsuntoKeyPressed

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ReporteError.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ReporteError.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ReporteError.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ReporteError.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                ReporteError dialog = new ReporteError(new javax.swing.JFrame(), true, "");
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
    private javax.swing.JTable Tabla1;
    private javax.swing.JButton btnAgregar;
    private javax.swing.JButton btnEnviar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lblSolucion;
    private javax.swing.JTextField txtAsunto;
    private javax.swing.JTextArea txtComentario;
    private javax.swing.JTextArea txtSolucion;
    // End of variables declaration//GEN-END:variables
}
