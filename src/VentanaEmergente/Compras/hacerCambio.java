package VentanaEmergente.Compras;

import Conexiones.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class hacerCambio extends javax.swing.JFrame {

    public void verTablaRequi(){
        try{
            DefaultTableModel miModelo = (DefaultTableModel) TablaRequi.getModel();
            DefaultTableModel miModelo2 = (DefaultTableModel) TablaEdicion.getModel();
            Connection con;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            String sql = "select * from detallesedicionpo";
            ResultSet rs = st.executeQuery(sql);
            String[] datos = new String[10];
            String[] datos2 = new String[10];
            while(rs.next()){
                datos[0] = rs.getString("IdArticulo");
                datos[1] = rs.getString("Cantidad");
                datos[2] = rs.getString("Descripcion");
                datos[3] = rs.getString("NumParte");
                datos[4] = rs.getString("Proveedor");
                datos[5] = rs.getString("Proyecto");
                datos[6] = rs.getString("Precio");
                miModelo2.addRow(datos);
                String sql2 = "select * from requisiciones where Id like '"+datos[0]+"'";
                Statement st2 = con.createStatement();
                ResultSet rs2 = st2.executeQuery(sql2);
                while(rs2.next()){
                    datos2[0] = rs2.getString("Id");
                    datos2[1] = rs2.getString("Codigo");
                    datos2[2] = rs2.getString("Descripcion");
                    datos2[3] = rs2.getString("Proyecto");
                    datos2[4] = rs2.getString("Cantidad");
                    datos2[5] = rs2.getString("Precio");
                    datos2[6] = rs2.getString("Proveedor");
                    miModelo.addRow(datos2);
                }
            }
            
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "ERROR: "+e,"ERROR",JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public hacerCambio() {
        initComponents();
        verTablaRequi();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        TablaRequi = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        TablaEdicion = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setLayout(new java.awt.BorderLayout());

        jLabel1.setText("HACER EL CAMBIO DE UNA VES");
        jPanel2.add(jLabel1);

        jPanel1.add(jPanel2, java.awt.BorderLayout.PAGE_START);

        jPanel3.setLayout(new java.awt.GridLayout(1, 2, 10, 10));

        TablaRequi.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "iD", "Codigo", "Descripcion", "Proyecto", "Cantidad", "Precio", "Proveedor"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(TablaRequi);

        jPanel3.add(jScrollPane1);

        TablaEdicion.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "Cantidad", "Descripcion", "NumParte", "Proveedor", "Proyecto", "Precio"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(TablaEdicion);

        jPanel3.add(jScrollPane2);

        jPanel1.add(jPanel3, java.awt.BorderLayout.CENTER);

        jButton1.setText("GUARDAR");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1, java.awt.BorderLayout.PAGE_END);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        try{
            Connection con;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            int n = 0, n1 = 0;
            for (int i = 0; i < TablaEdicion.getRowCount(); i++) {
                String sql = "update requisiciones set Codigo = ?, Descripcion = ?, Proyecto = ?, Cantidad = ?, Precio = ?, Proveedor = ? where Id = ?";
                PreparedStatement pst = con.prepareStatement(sql);

                pst.setString(1, TablaEdicion.getValueAt(i, 3).toString());//CODIGO
                pst.setString(2, TablaEdicion.getValueAt(i, 2).toString());//DESCRIPCION
                pst.setString(3, TablaEdicion.getValueAt(i, 5).toString());//PROYECTO
                pst.setString(4, TablaEdicion.getValueAt(i, 1).toString());//CANTIDAD
                pst.setString(5, TablaEdicion.getValueAt(i, 6).toString());//PRECIO
                pst.setString(6, TablaEdicion.getValueAt(i, 4).toString());//PROVEEDOR
                pst.setString(7, TablaEdicion.getValueAt(i, 0).toString());//ID
                n = pst.executeUpdate();
            }
            
            for (int i = 0; i < TablaRequi.getRowCount(); i++) {
                String sql = "update detallesedicionpo set Cantidad = ?, Descripcion = ?, NumParte = ?, Proveedor = ?, Proyecto = ?, Precio = ? where IdArticulo = ?";
                PreparedStatement pst = con.prepareStatement(sql);

                pst.setString(1, TablaRequi.getValueAt(i, 3).toString());//CANTIDAD
                pst.setString(2, TablaRequi.getValueAt(i, 2).toString());//DESCRIPCION
                pst.setString(3, TablaRequi.getValueAt(i, 1).toString());//NUMERO DE PARTE
                pst.setString(4, TablaRequi.getValueAt(i, 6).toString());//PROVEEDOR
                pst.setString(5, TablaRequi.getValueAt(i, 5).toString());//PROYECTO
                pst.setString(6, TablaRequi.getValueAt(i, 4).toString());//PRECIO
                pst.setString(7, TablaRequi.getValueAt(i, 0).toString());//ID
                n1 = pst.executeUpdate();
            }
            
            if(n > 0 && n1 > 0){
                JOptionPane.showMessageDialog(this, "DATOS COMPLETADOS CON EXITO :)");
            }
            
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "ERROR: "+e,"ERROR",JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

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
            java.util.logging.Logger.getLogger(hacerCambio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(hacerCambio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(hacerCambio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(hacerCambio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new hacerCambio().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable TablaEdicion;
    private javax.swing.JTable TablaRequi;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    // End of variables declaration//GEN-END:variables
}
