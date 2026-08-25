package pruebas;

import Conexiones.Conexion;
import VentanaEmergente.Empleado.verInfo;
import java.awt.Color;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public final class verEmpleado extends javax.swing.JFrame {

    public void limpiarTabla(){
        Tabla1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "Nombre", "Apellido", "Puesto", "Telefono", "Almacen", "Correo", "Supervisor"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        Tabla1.setColorBorderHead(new java.awt.Color(0, 112, 192));
        Tabla1.setColorBorderRows(new java.awt.Color(255, 255, 255));
        Tabla1.setColorPrimaryText(new java.awt.Color(51, 51, 51));
        Tabla1.setColorSecondary(new java.awt.Color(245, 245, 245));
        Tabla1.setColorSecundaryText(new java.awt.Color(51, 51, 51));
        jScrollPane1.getViewport().setBackground(Color.white);
        jScrollPane1.getVerticalScrollBar().setUnitIncrement(30);
    }
    
    public void verDatos(){
        try{
            Connection con;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            String sql = "select * from registroempleados";
            ResultSet rs = st.executeQuery(sql);
            String datos[] = new String[10];
            DefaultTableModel miModelo = (DefaultTableModel) Tabla1.getModel();
            while(rs.next()){
                datos[0] = rs.getString("NumEmpleado");
                datos[1] = rs.getString("Nombre");
                datos[2] = rs.getString("Apellido");
                datos[3] = rs.getString("Puesto");
                datos[4] = rs.getString("Telefono");
                datos[5] = rs.getString("Almacen");
                datos[6] = rs.getString("Correo");
                datos[7] = rs.getString("Supervisor");
                miModelo.addRow(datos);
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "ERROR: "+e,"ERROR",JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public verEmpleado() {
        initComponents();
        limpiarTabla();
        verDatos();
        this.setExtendedState(MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        PanelNorth = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        jPanel19 = new javax.swing.JPanel();
        btnSalir = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        Tabla1 = new rojerusan.RSTableMetro();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("EMPLEADOS");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new java.awt.BorderLayout());

        PanelNorth.setBackground(new java.awt.Color(255, 255, 255));
        PanelNorth.setLayout(new java.awt.BorderLayout());

        jLabel17.setFont(new java.awt.Font("Roboto", 1, 40)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(0, 165, 252));
        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel17.setText("Ver empleados");
        PanelNorth.add(jLabel17, java.awt.BorderLayout.CENTER);

        jPanel19.setBackground(new java.awt.Color(255, 255, 255));

        btnSalir.setBackground(new java.awt.Color(255, 255, 255));

        jLabel16.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel16.setText("X");
        jLabel16.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jLabel16.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel16MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel16MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel16MouseExited(evt);
            }
        });
        btnSalir.add(jLabel16);

        jPanel19.add(btnSalir);

        PanelNorth.add(jPanel19, java.awt.BorderLayout.EAST);

        jPanel1.add(PanelNorth, java.awt.BorderLayout.NORTH);

        jScrollPane1.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(255, 255, 255)));

        Tabla1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "Nombre", "Apellido", "Puesto", "Telefono", "Almacen", "Correo", "Supervisor"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        Tabla1.setColorBorderHead(new java.awt.Color(0, 112, 192));
        Tabla1.setColorBorderRows(new java.awt.Color(255, 255, 255));
        Tabla1.setColorPrimaryText(new java.awt.Color(51, 51, 51));
        Tabla1.setColorSecondary(new java.awt.Color(245, 245, 245));
        Tabla1.setColorSecundaryText(new java.awt.Color(51, 51, 51));
        Tabla1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Tabla1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(Tabla1);

        jPanel1.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setText("     ");
        jPanel2.add(jLabel1);

        jPanel1.add(jPanel2, java.awt.BorderLayout.PAGE_END);

        jLabel2.setText("             ");
        jPanel1.add(jLabel2, java.awt.BorderLayout.LINE_END);

        jLabel3.setText("             ");
        jPanel1.add(jLabel3, java.awt.BorderLayout.WEST);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel16MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel16MouseClicked
        dispose();
    }//GEN-LAST:event_jLabel16MouseClicked

    private void jLabel16MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel16MouseEntered
        btnSalir.setBackground(Color.red);
        jLabel16.setForeground(Color.white);
    }//GEN-LAST:event_jLabel16MouseEntered

    private void jLabel16MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel16MouseExited
        btnSalir.setBackground(Color.white);
        jLabel16.setForeground(Color.black);
    }//GEN-LAST:event_jLabel16MouseExited

    private void Tabla1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Tabla1MouseClicked
        JFrame f = (JFrame) JOptionPane.getFrameForComponent(this);
        verInfo ver = new verInfo(f,true);
        ver.setLocationRelativeTo(null);
        ver.txtId.setText(Tabla1.getValueAt(Tabla1.getSelectedRow(), 0).toString());
        ver.txtNombre.setText(Tabla1.getValueAt(Tabla1.getSelectedRow(), 1).toString());
        ver.txtApellido.setText(Tabla1.getValueAt(Tabla1.getSelectedRow(), 2).toString());
        ver.cmbPuesto.setSelectedItem(Tabla1.getValueAt(Tabla1.getSelectedRow(), 3).toString());
        try{
        ver.txtTelefono.setText(Tabla1.getValueAt(Tabla1.getSelectedRow(), 4).toString());
        }catch(Exception e){}
        try{
        ver.cmbAlmacen.setSelectedItem(Tabla1.getValueAt(Tabla1.getSelectedRow(), 5).toString());
        }catch(Exception e){}
        try{
        ver.txtCorreo.setText(Tabla1.getValueAt(Tabla1.getSelectedRow(), 6).toString());
        }catch(Exception e){}
        try{
        ver.cmbSupervisor.setSelectedItem(Tabla1.getValueAt(Tabla1.getSelectedRow(), 7).toString());
        }catch(Exception e){}
        ver.setVisible(true);
        limpiarTabla();
        verDatos();
    }//GEN-LAST:event_Tabla1MouseClicked

    
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
            java.util.logging.Logger.getLogger(verEmpleado.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(verEmpleado.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(verEmpleado.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(verEmpleado.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new verEmpleado().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PanelNorth;
    private rojerusan.RSTableMetro Tabla1;
    private javax.swing.JPanel btnSalir;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
