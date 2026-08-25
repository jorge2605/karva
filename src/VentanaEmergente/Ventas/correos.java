package VentanaEmergente.Ventas;

import Conexiones.Conexion;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class correos extends javax.swing.JDialog implements ActionListener{
 
    addCorreo add;
    String depa;
    
    public String agregarBD(String depa, String correo, String id, String Ubi){
        String key = "";
        try{
            Connection con;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            if(id == null || id.equals("")){
                String sql = "insert into enviocorreos (Departamento, Correo, Ubi) values(?,?,?)";
                PreparedStatement pst = con.prepareStatement(sql,PreparedStatement.RETURN_GENERATED_KEYS);

                pst.setString(1, depa);
                pst.setString(2, correo);
                pst.setString(3, Ubi);

                int n = pst.executeUpdate();
                
                ResultSet generatedKeys = pst.getGeneratedKeys();
                if (generatedKeys.next()) {
                    long idGenerado = generatedKeys.getLong(1);
                    key = idGenerado+"";
                }
                
                if(n < 1){
                    JOptionPane.showMessageDialog(this, "No se guardo esta fila","Error",JOptionPane.ERROR_MESSAGE);
                }
            }else{
                String sql = "update enviocorreos set Departamento = ?, Correo = ?, Ubi = ? where Id = ?";
                PreparedStatement pst = con.prepareStatement(sql);

                pst.setString(1, depa);
                pst.setString(2, correo);
                pst.setString(3, Ubi);
                pst.setString(4, id);

                int n = pst.executeUpdate();
                
                key = id;
                
                if(n < 1){
                    JOptionPane.showMessageDialog(this, "No se actualizo la fila","Error",JOptionPane.ERROR_MESSAGE);
                }
            }
            
            
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "ERROR: "+e,"ERROR",JOptionPane.ERROR_MESSAGE);
        }
        return key;
    }
    
    public void verDatos(){
        try{
            Connection con;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            String sql = "select * from enviocorreos where Departamento like '"+depa+"'";
            ResultSet rs = st.executeQuery(sql);
            String datos[] = new String[4];
            DefaultTableModel miModelo = (DefaultTableModel) Tabla1.getModel();
            while(rs.next()){
                datos[0] = rs.getString("Id");
                datos[1] = rs.getString("Departamento");
                datos[2] = rs.getString("Correo");
                datos[3] = rs.getString("Ubi");
                miModelo.addRow(datos);
            }
            
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "ERROR: "+e,"ERROR",JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void limpiarTabla(){
        Tabla1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
            },
            new String [] {
                "ID", "Departamento", "Correo", "CC o TO"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
    }
    
    public void borrar(String id){
        try{
            Connection con;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            String sql = "delete from enviocorreos where Id = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            
            pst.setString(1, id);
            
            int n = pst.executeUpdate();
            
            if(n > 0){
                JOptionPane.showMessageDialog(this, "Correo eliminado");
            }
            
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "Error: "+e, "error",JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public correos(java.awt.Frame parent, boolean modal, String depa) {
        super(parent, modal);
        initComponents();
        this.depa = depa;
        Tabla1.getTableHeader().setFont(new Font("Roboto", Font.BOLD, 14));
        Tabla1.getTableHeader().setOpaque(false);
        Tabla1.getTableHeader().setBackground(new Color(0, 78, 171));
        Tabla1.getTableHeader().setForeground(Color.white);
        Tabla1.setRowHeight(25);
        Tabla1.setShowVerticalLines(false);
        jScrollPane1.getViewport().setBackground(new Color(255,255,255));
        verDatos();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupMenu1 = new javax.swing.JPopupMenu();
        Actualizar = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        Eliminar = new javax.swing.JMenuItem();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        Tabla1 = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();

        jPopupMenu1.setToolTipText("");

        Actualizar.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        Actualizar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/editar.png"))); // NOI18N
        Actualizar.setText("Actualizar                          ");
        Actualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ActualizarActionPerformed(evt);
            }
        });
        jPopupMenu1.add(Actualizar);
        jPopupMenu1.add(jSeparator1);

        Eliminar.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        Eliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/error.png"))); // NOI18N
        Eliminar.setText("Eliminar                          ");
        Eliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EliminarActionPerformed(evt);
            }
        });
        jPopupMenu1.add(Eliminar);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new java.awt.BorderLayout(15, 15));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jLabel1.setText("Edicion de correos");
        jPanel2.add(jLabel1);

        jPanel1.add(jPanel2, java.awt.BorderLayout.PAGE_START);

        jScrollPane1.setBorder(null);

        Tabla1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Departamento", "Correo", "CC o TO"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        Tabla1.setComponentPopupMenu(jPopupMenu1);
        jScrollPane1.setViewportView(Tabla1);

        jPanel1.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jButton1.setBackground(new java.awt.Color(255, 255, 255));
        jButton1.setFont(new java.awt.Font("Roboto", 1, 16)); // NOI18N
        jButton1.setForeground(new java.awt.Color(51, 51, 51));
        jButton1.setText("Añadir Correo");
        jButton1.setBorder(null);
        jButton1.setBorderPainted(false);
        jButton1.setContentAreaFilled(false);
        jButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton1.setFocusPainted(false);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton1);

        jPanel1.add(jPanel3, java.awt.BorderLayout.PAGE_END);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        JFrame f = (JFrame) JOptionPane.getFrameForComponent(this);
        add = new addCorreo(f, true);
        add.setLocationRelativeTo(null);
        add.btnAdd.addActionListener(this);
        add.txtDepartamento.setText(depa);
        add.setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void ActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ActualizarActionPerformed
        JFrame f = (JFrame) JOptionPane.getFrameForComponent(this);
        add = new addCorreo(f, true);
        add.setLocationRelativeTo(null);
        add.btnAdd.addActionListener(this);
        add.txtDepartamento.setText(depa);
        add.txtId.setText(Tabla1.getValueAt(Tabla1.getSelectedRow(), 0).toString());
        add.txtCorreo.setText(Tabla1.getValueAt(Tabla1.getSelectedRow(), 2).toString());
        add.setVisible(true);
    }//GEN-LAST:event_ActualizarActionPerformed

    private void EliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EliminarActionPerformed
        borrar(Tabla1.getValueAt(Tabla1.getSelectedRow(), 0).toString());
        limpiarTabla();
        verDatos();
    }//GEN-LAST:event_EliminarActionPerformed

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
            java.util.logging.Logger.getLogger(correos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(correos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(correos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(correos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                correos dialog = new correos(new javax.swing.JFrame(), true,"");
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
    private javax.swing.JMenuItem Actualizar;
    private javax.swing.JMenuItem Eliminar;
    private javax.swing.JTable Tabla1;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    // End of variables declaration//GEN-END:variables

    @Override
    public void actionPerformed(ActionEvent e) {
        if(add != null){
            if(e.getSource() == add.btnAdd){
                agregarBD(add.txtDepartamento.getText(), add.txtCorreo.getText(), add.txtId.getText(), add.cmbTo.getSelectedItem().toString());
                limpiarTabla();
                verDatos();
                add.dispose();
            }
        }
    }
}
