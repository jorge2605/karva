package pruebas;

import Conexiones.Conexion;
import java.awt.Color;
import java.awt.Font;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

public class Prov extends javax.swing.JFrame {

    
    
    public void insertarTabla(JTable tabla){
        initComponents();
        DefaultTableModel miModelo = (DefaultTableModel) tabla.getModel();
        Tabla1.setModel(miModelo);
        
        Tabla1.getTableHeader().setFont(new Font("Roboto", Font.PLAIN, 14));
        Tabla1.getTableHeader().setOpaque(false);
        Tabla1.getTableHeader().setBackground(new Color(0, 0, 0, 0));
        Tabla1.getTableHeader().setForeground(Color.black);
        Tabla1.setRowHeight(25);
        
        Tabla1.removeColumn(Tabla1.getColumnModel().getColumn(0));
        Tabla1.removeColumn(Tabla1.getColumnModel().getColumn(0));
        Tabla1.removeColumn(Tabla1.getColumnModel().getColumn(0));
        Tabla1.removeColumn(Tabla1.getColumnModel().getColumn(3));
//        Tabla1.removeColumn(Tabla1.getColumnModel().getColumn(3));
        llenar();
    }
    public void llenar(){
    try{
        cmbProveedores.removeAllItems();
        cmbProveedores.addItem("SELECCIONAR PROVEEDOR");
        Connection con = null;
        Conexion con1 = new Conexion();
        con = con1.getConnection();
        Statement st = con.createStatement();
        String sql = "select * from registroprov_compras";
        ResultSet rs = st.executeQuery(sql);
        String datos[] = new String[5];
        while(rs.next()){
        datos[0] = rs.getString("Nombre");
        cmbProveedores.addItem(datos[0]);
        }
    }catch(SQLException E){
    JOptionPane.showMessageDialog(this,"ERROR AL VER PROVEEDORES "+E);
    }
    }
    
    public Prov() {
    }
    
    

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        btnSalir = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        Tabla1 = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        Tabla2 = new javax.swing.JTable();
        cmbProveedores = new javax.swing.JComboBox<>();
        btnGuardarProveedores = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setType(java.awt.Window.Type.POPUP);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnSalir.setBackground(new java.awt.Color(255, 255, 255));

        jLabel2.setFont(new java.awt.Font("Roboto", 0, 36)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("X");
        jLabel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel2MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel2MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel2MouseExited(evt);
            }
        });

        javax.swing.GroupLayout btnSalirLayout = new javax.swing.GroupLayout(btnSalir);
        btnSalir.setLayout(btnSalirLayout);
        btnSalirLayout.setHorizontalGroup(
            btnSalirLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
        );
        btnSalirLayout.setVerticalGroup(
            btnSalirLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnSalirLayout.createSequentialGroup()
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 1, Short.MAX_VALUE))
        );

        jPanel1.add(btnSalir, new org.netbeans.lib.awtextra.AbsoluteConstraints(1020, 0, 50, 50));

        Tabla1.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
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
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, true, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        Tabla1.setRowHeight(25);
        Tabla1.setSelectionBackground(new java.awt.Color(0, 153, 204));
        Tabla1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Tabla1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(Tabla1);
        if (Tabla1.getColumnModel().getColumnCount() > 0) {
            Tabla1.getColumnModel().getColumn(0).setResizable(false);
            Tabla1.getColumnModel().getColumn(1).setResizable(false);
            Tabla1.getColumnModel().getColumn(2).setResizable(false);
            Tabla1.getColumnModel().getColumn(3).setResizable(false);
        }

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 70, 480, -1));

        jPanel2.setBackground(new java.awt.Color(0, 165, 252));

        jLabel1.setFont(new java.awt.Font("Roboto", 0, 30)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("PROVEEDOR-MATERIAL");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 740, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
        );

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 0, 740, 50));

        Tabla2.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        Tabla2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "NO PARTE", "PROVEEDOR", "ID"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        Tabla2.setRowHeight(25);
        Tabla2.setSelectionBackground(new java.awt.Color(0, 153, 204));
        Tabla2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Tabla2MouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(Tabla2);

        jPanel1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 70, 420, -1));

        cmbProveedores.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        cmbProveedores.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanel1.add(cmbProveedores, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 180, 160, -1));

        btnGuardarProveedores.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ImgAnimacion/send_48.png"))); // NOI18N
        btnGuardarProveedores.setContentAreaFilled(false);
        btnGuardarProveedores.setFocusPainted(false);
        btnGuardarProveedores.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnGuardarProveedores.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/ImgAnimacion/send_48.png"))); // NOI18N
        btnGuardarProveedores.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/ImgAnimacion/send_64.png"))); // NOI18N
        btnGuardarProveedores.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        btnGuardarProveedores.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarProveedoresActionPerformed(evt);
            }
        });
        jPanel1.add(btnGuardarProveedores, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 400, 70, 70));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 498, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel2MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseEntered
        btnSalir.setBackground(Color.red);
    }//GEN-LAST:event_jLabel2MouseEntered

    private void jLabel2MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseExited
        btnSalir.setBackground(Color.white);
    }//GEN-LAST:event_jLabel2MouseExited

    private void jLabel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseClicked
        dispose();
    }//GEN-LAST:event_jLabel2MouseClicked

    private void Tabla1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Tabla1MouseClicked
        if(cmbProveedores.getSelectedItem().equals("SELECCIONAR PROVEEDOR")){
            JOptionPane.showMessageDialog(this, "DEBES SELECCIONAR UN PROVEEDOR","ADVERTENCIA",JOptionPane.WARNING_MESSAGE);
        }else{
        DefaultTableModel miModelo = (DefaultTableModel) Tabla2.getModel();
        String datos[] = new String[4];
        boolean v = false;
        int cont = 1;
        int fila = Tabla1.getSelectedRow();
        datos[0] = Tabla1.getValueAt(fila, 1).toString();
        datos[1] = cmbProveedores.getSelectedItem().toString();
        datos[2] = Tabla1.getValueAt(fila, 3).toString();
        
        if(Tabla2.getRowCount() == 0){
        miModelo.addRow(datos);
        }else if(Tabla2.getRowCount() >= 1){
        do{ 
         if(datos[0].equals(Tabla2.getValueAt(cont - 1, 0).toString())){
        JOptionPane.showMessageDialog(this, "ESTE ELEMENTO YA ESTA SELECCIONADO");
        v = true;
        }
         cont = cont+1;
        }while(cont == Tabla2.getRowCount() || v == true);
        
        if(v == false){
        miModelo.addRow(datos);
        }
        
        }
        }
    }//GEN-LAST:event_Tabla1MouseClicked

    public void limpiarTabla2(){
        Tabla2.setModel(new javax.swing.table.DefaultTableModel(
    new Object [][] {

    },
    new String [] {
        "NO PARTE", "PROVEEDOR", "ID"
    }
    ) {
    boolean[] canEdit = new boolean [] {
        false, false, false
    };

    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return canEdit [columnIndex];
    }
    });
    }
    
    private void Tabla2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Tabla2MouseClicked
        DefaultTableModel miModelo = (DefaultTableModel) Tabla2.getModel();
        int fila = Tabla2.getSelectedRow();
        miModelo.removeRow(fila);
    
    }//GEN-LAST:event_Tabla2MouseClicked

    private void btnGuardarProveedoresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarProveedoresActionPerformed
        try{
            Connection con = null;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            
            
            String sql = "update requisiciones set Proveedor = ? where Id = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            
            int n = 0, n1 = 0;
            
            for (int i = 0; i < Tabla2.getRowCount(); i++) {
                pst.setString(1, Tabla2.getValueAt(i, 1).toString());
                pst.setString(2, Tabla2.getValueAt(i, 2).toString());
                n = pst.executeUpdate();
            }
            
            String sql2 = "update inventario set Proveedor = ? where NumeroDeParte = ?";
            PreparedStatement pst2 = con.prepareStatement(sql2);
            
            for (int i = 0; i < Tabla2.getRowCount(); i++) {
                pst2.setString(1, Tabla2.getValueAt(i, 1).toString());
                pst2.setString(2, Tabla2.getValueAt(i, 0).toString());
                n1 = pst2.executeUpdate();
            }
            
            if (n > 0 && n1 > 0){
                JOptionPane.showMessageDialog(null, "DATOS GUARDADOS CORRECTAMENTE");
                DefaultTableModel miModelo = (DefaultTableModel) Tabla2.getModel();
                int tot = Tabla2.getRowCount();
                limpiarTabla2();
                
                
            }else{
                JOptionPane.showMessageDialog(null, "DATOS NO GUARDADOS pst1: "+n+" pst2: "+n1);
            }
            
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "ERROR: "+e,"ERROR",JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnGuardarProveedoresActionPerformed

    public static void main(String args[]) {
        
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
            java.util.logging.Logger.getLogger(Prov.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Prov.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Prov.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Prov.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Prov().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable Tabla1;
    private javax.swing.JTable Tabla2;
    public javax.swing.JButton btnGuardarProveedores;
    private javax.swing.JPanel btnSalir;
    private javax.swing.JComboBox<String> cmbProveedores;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    // End of variables declaration//GEN-END:variables
}
