package pruebas;

import Conexiones.Conexion;
import java.awt.Component;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;

public class MaximosYMinimos extends java.awt.Dialog {

    public void verDatos(){
        try{
            DefaultTableModel miModelo = (DefaultTableModel) Tabla1.getModel();
            Connection con = null;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            String sql = "select * from inventario order by NumeroDeParte";
            ResultSet rs = st.executeQuery(sql);
            String datos[] = new String[10];
            while(rs.next()){
                datos[0] = rs.getString("NumeroDeParte");
                datos[1] = rs.getString("Descripcion");
                datos[2] = rs.getString("Cantidad");
                datos[3] = rs.getString("Maximos");
                datos[4] = rs.getString("Minimos");
                miModelo.addRow(datos);
            }
            
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "ERROR: "+e,"ERROR",JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void limpiarTabla(){
        DefaultTableModel miModelo = (DefaultTableModel) Tabla1.getModel();
        String titulos[] = {"NO PARTE","DESCRIPCION","CANTIDAD","MAXIMOS","MINIMOS"};
        miModelo = new DefaultTableModel(null,titulos);
        Tabla1.setModel(miModelo);
    }
    
    public MaximosYMinimos(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        verDatos();
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        Tabla1 = new Maximos();
        jLabel2 = new javax.swing.JLabel();
        txtParte = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel3 = new javax.swing.JLabel();
        txtCantidad = new javax.swing.JTextField();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel4 = new javax.swing.JLabel();
        txtMaximos = new javax.swing.JTextField();
        jSeparator3 = new javax.swing.JSeparator();
        jLabel5 = new javax.swing.JLabel();
        txtMinimos = new javax.swing.JTextField();
        jSeparator4 = new javax.swing.JSeparator();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtDescripcion = new javax.swing.JTextArea();
        btnGuardar = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();

        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                closeDialog(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(0, 165, 252));

        jLabel1.setFont(new java.awt.Font("Roboto Light", 0, 60)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("MAXIMOS Y MINIMOS");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(jLabel1)
                .addContainerGap(35, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 20, 660, 70));

        Tabla1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "NO PARTE", "DESCRIPCION", "CANTIDAD", "MAXIMOS", "MINIMOS"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
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

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 110, 1040, -1));

        jLabel2.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        jLabel2.setText("DESCRIPCION");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 550, -1, -1));

        txtParte.setEditable(false);
        txtParte.setBackground(new java.awt.Color(255, 255, 255));
        txtParte.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        txtParte.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtParte.setBorder(null);
        jPanel1.add(txtParte, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 570, 180, 30));
        jPanel1.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 600, 180, 10));

        jLabel3.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        jLabel3.setText("CANTIDAD");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 550, -1, -1));

        txtCantidad.setEditable(false);
        txtCantidad.setBackground(new java.awt.Color(255, 255, 255));
        txtCantidad.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        txtCantidad.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCantidad.setBorder(null);
        jPanel1.add(txtCantidad, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 570, 110, 30));
        jPanel1.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 600, 110, 10));

        jLabel4.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        jLabel4.setText("MAXIMOS");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 550, -1, -1));

        txtMaximos.setBackground(new java.awt.Color(255, 255, 255));
        txtMaximos.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        txtMaximos.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtMaximos.setBorder(null);
        jPanel1.add(txtMaximos, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 570, 100, 30));
        jPanel1.add(jSeparator3, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 600, 100, 10));

        jLabel5.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        jLabel5.setText("MINIMOS");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(930, 550, -1, -1));

        txtMinimos.setBackground(new java.awt.Color(255, 255, 255));
        txtMinimos.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        txtMinimos.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtMinimos.setBorder(null);
        jPanel1.add(txtMinimos, new org.netbeans.lib.awtextra.AbsoluteConstraints(910, 570, 100, 30));
        jPanel1.add(jSeparator4, new org.netbeans.lib.awtextra.AbsoluteConstraints(910, 600, 100, 10));

        jLabel6.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        jLabel6.setText("NUMERO DE PARTE");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 550, -1, -1));

        txtDescripcion.setEditable(false);
        txtDescripcion.setBackground(new java.awt.Color(255, 255, 255));
        txtDescripcion.setColumns(20);
        txtDescripcion.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        txtDescripcion.setLineWrap(true);
        txtDescripcion.setRows(5);
        txtDescripcion.setWrapStyleWord(true);
        txtDescripcion.setBorder(null);
        txtDescripcion.setCaretColor(new java.awt.Color(255, 255, 255));
        jScrollPane2.setViewportView(txtDescripcion);

        jPanel1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 570, 430, -1));

        btnGuardar.setBackground(new java.awt.Color(255, 255, 255));
        btnGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ImgAnimacion/editar_32.png"))); // NOI18N
        btnGuardar.setBorder(null);
        btnGuardar.setBorderPainted(false);
        btnGuardar.setContentAreaFilled(false);
        btnGuardar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnGuardar.setFocusPainted(false);
        btnGuardar.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnGuardar.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/ImgAnimacion/editar_32.png"))); // NOI18N
        btnGuardar.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/ImgAnimacion/editar_48.png"))); // NOI18N
        btnGuardar.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });
        jPanel1.add(btnGuardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(1020, 570, 55, 55));

        jButton1.setText("extraer");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(1100, 240, -1, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 1262, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 670, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void closeDialog(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_closeDialog
        setVisible(false);
        dispose();
    }//GEN-LAST:event_closeDialog

    private void Tabla1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Tabla1MouseClicked
        int fila = Tabla1.getSelectedRow();
        txtParte.setText(Tabla1.getValueAt(fila, 0).toString());
        txtDescripcion.setText(Tabla1.getValueAt(fila, 1).toString());
        txtCantidad.setText(Tabla1.getValueAt(fila, 2).toString());
        txtMaximos.setText(Tabla1.getValueAt(fila, 3).toString());
        txtMinimos.setText(Tabla1.getValueAt(fila, 4).toString());
    }//GEN-LAST:event_Tabla1MouseClicked

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        
        int max = Integer.parseInt(txtMaximos.getText());
        int min = Integer.parseInt(txtMinimos.getText());
        
        if(txtMaximos.getText().equals("")){
            JOptionPane.showMessageDialog(this, "DEBES LLENAR EL CAMPO DE MAXIMO","ADVERTENCIA",JOptionPane.WARNING_MESSAGE);
        }else if(txtMinimos.getText().equals("")){
            JOptionPane.showMessageDialog(this, "DEBES LLENAR EL CAMPO DE MINIMOS","ADVERTENCIA",JOptionPane.WARNING_MESSAGE);
        }else if(max < min){
            JOptionPane.showMessageDialog(this, "EL MAXIMO NO PUEDE ESTAR POR DEBAJO AL MINIMO FAVOR DE CAMBIAR","ADVERTENCIA",JOptionPane.WARNING_MESSAGE);
        }else{
        try{
            Connection con = null;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            String sql = "update inventario set Maximos = ?, Minimos = ? where NumeroDeParte = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            
            pst.setString(1, txtMaximos.getText());
            pst.setString(2, txtMinimos.getText());
            pst.setString(3, txtParte.getText());
            
            int n = pst.executeUpdate();
            
            if (n > 0){
                JOptionPane.showMessageDialog(this, "DATOS GUARDADOS");
                limpiarTabla();
                verDatos();
            }
            
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "ERROR: "+e,"ERROR",JOptionPane.ERROR_MESSAGE);
        }
        }
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        DefaultTableModel miModelo = (DefaultTableModel) Tabla1.getModel();
        int total = Tabla1.getRowCount();
        
        
        for (int i = Tabla1.getRowCount() - 1; i >= 0; i--) {
            double cant;
            double min;
            boolean c  = false;
            if(Tabla1.getValueAt(i, 4) == null){
                min = 0;
                c = true;
            }else{
                min = Double.parseDouble(Tabla1.getValueAt(i, 4).toString());
            }
            
            if(Tabla1.getValueAt(i, 2) == null){
                cant = 1000;
                c = true;
            }else{
                try{cant = Double.parseDouble(Tabla1.getValueAt(i, 2).toString());}catch(Exception e){cant = 0;}
            }
            
            if(cant > min || c == true){
                miModelo.removeRow(i);
                c = false;
            }
            
        }
        
    }//GEN-LAST:event_jButton1ActionPerformed

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                MaximosYMinimos dialog = new MaximosYMinimos(new java.awt.Frame(), true);
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
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JTextField txtCantidad;
    private javax.swing.JTextArea txtDescripcion;
    private javax.swing.JTextField txtMaximos;
    private javax.swing.JTextField txtMinimos;
    private javax.swing.JTextField txtParte;
    // End of variables declaration//GEN-END:variables
}
