package VentanaEmergente.Costos;

import Conexiones.Conexion;
import java.awt.Color;
import java.awt.Font;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumn;

public class EditarEmpleados extends javax.swing.JDialog {

    public final void limpiarTabla(){
        Tabla1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][]{},
            new String [] {
                "No. Empleado", "Nombre", "Departamento", "Sueldo P/Mes", "Activo"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Boolean.class
            };
            boolean[] canEdit = new boolean [] {
                false, true, true, true, true
            };
            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        Tabla1.getTableHeader().setFont(new Font("Lexend", Font.BOLD, 14));
        Tabla1.setFont(new Font("Lexend", Font.PLAIN, 12));
        Tabla1.getTableHeader().setOpaque(false);
        Tabla1.getTableHeader().setBackground(new Color(0, 78, 171));
        Tabla1.getTableHeader().setForeground(Color.white);
        Tabla1.setRowHeight(25);
        Tabla1.setShowVerticalLines(false);
        Tabla1.setGridColor(new Color(240,240,240));
        
        jScrollPane1.getViewport().setBackground(new Color(255,255,255));
        jScrollPane1.getViewport().setForeground(new Color(255,255,255));
    }
    
    public final void verEmpleados(){
        try{
            Connection con;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            String sql = "select * from empleadoscheck";
            ResultSet rs = st.executeQuery(sql);
            Object datos[] = new Object[10];
            DefaultTableModel miModelo = (DefaultTableModel) Tabla1.getModel();
            JComboBox jcb = new JComboBox();
            jcb.addItem("CI ADMINITRACION");
            jcb.addItem("MOD DISENADOR");
            jcb.addItem("MOD ELECTROMECANICO");
            jcb.addItem("MOD HERRAMENTISTA");
            jcb.addItem("MOI ALMACEN");
            jcb.addItem("MOI DISENO");
            jcb.addItem("MOI MANSAJERIA");
            jcb.addItem("MOI PROGRAMADOR");
            jcb.addItem("MOI TECNICO CALIDAD INDIRECTO");
            TableColumn tc = Tabla1.getColumnModel().getColumn(2);
            TableCellEditor tce = new DefaultCellEditor(jcb);
            tc.setCellEditor(tce);
            while(rs.next()){
                datos[0] = rs.getString("NumEmpleado");
                datos[1] = rs.getString("Nombre");
                datos[2] = rs.getString("Departamento");
                datos[3] = rs.getString("Sueldo");
                datos[4] = rs.getString("Activo");
                if(datos[4] == null){
                    datos[4] = false;
                }else if(datos[4].equals("false") || datos[4].equals("")){
                    datos[4] = false;
                }else{
                    datos[4] = true;
                }
                miModelo.addRow(datos);
            }
        }catch(SQLException e){
         JOptionPane.showMessageDialog(this, "ERROR: "+e,"ERROR",JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public final void verEmpleado(String numero){
        try{
            Connection con;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            String sql = "select * from empleadoscheck where NumEmpleado like '"+numero+"'";
            ResultSet rs = st.executeQuery(sql);
            Object datos[] = new Object[10];
            DefaultTableModel miModelo = (DefaultTableModel) Tabla1.getModel();
            JComboBox jcb = new JComboBox();
            jcb.addItem("CI ADMINITRACION");
            jcb.addItem("MOD DISENADOR");
            jcb.addItem("MOD ELECTROMECANICO");
            jcb.addItem("MOD HERRAMENTISTA");
            jcb.addItem("MOI ALMACEN");
            jcb.addItem("MOI DISENO");
            jcb.addItem("MOI MANSAJERIA");
            jcb.addItem("MOI PROGRAMADOR");
            jcb.addItem("MOI TECNICO CALIDAD INDIRECTO");
            TableColumn tc = Tabla1.getColumnModel().getColumn(2);
            TableCellEditor tce = new DefaultCellEditor(jcb);
            tc.setCellEditor(tce);
            while(rs.next()){
                datos[0] = rs.getString("NumEmpleado");
                datos[1] = rs.getString("Nombre");
                datos[2] = rs.getString("Departamento");
                datos[3] = rs.getString("Sueldo");
                datos[4] = rs.getString("Activo");
                if(datos[4] == null){
                    datos[4] = false;
                }else if(datos[4].equals("false") || datos[4].equals("")){
                    datos[4] = false;
                }else{
                    datos[4] = true;
                }
                miModelo.addRow(datos);
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "Error"+e,"Error",JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public EditarEmpleados(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        limpiarTabla();
        verEmpleados();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        Tabla1 = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        btnGuardar = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(1168, 660));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new java.awt.BorderLayout());

        jLabel1.setFont(new java.awt.Font("Lexend", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 153, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Edicion de empleados");
        jPanel1.add(jLabel1, java.awt.BorderLayout.PAGE_START);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new java.awt.BorderLayout());

        jScrollPane1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));

        Tabla1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "No. Empleado", "Nombre", "Departamento", "Sueldo P/Mes", "Activo"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Boolean.class
            };
            boolean[] canEdit = new boolean [] {
                false, true, true, true, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(Tabla1);

        jPanel2.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        btnGuardar.setBackground(new java.awt.Color(255, 255, 255));
        btnGuardar.setFont(new java.awt.Font("Lexend", 1, 14)); // NOI18N
        btnGuardar.setForeground(new java.awt.Color(0, 78, 171));
        btnGuardar.setText("Guardar");
        btnGuardar.setBorder(null);
        btnGuardar.setBorderPainted(false);
        btnGuardar.setContentAreaFilled(false);
        btnGuardar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnGuardar.setFocusPainted(false);
        btnGuardar.setPreferredSize(new java.awt.Dimension(80, 25));
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });
        jPanel3.add(btnGuardar);

        jPanel2.add(jPanel3, java.awt.BorderLayout.PAGE_END);

        jLabel2.setText("          ");
        jPanel2.add(jLabel2, java.awt.BorderLayout.LINE_START);

        jLabel3.setText("          ");
        jPanel2.add(jLabel3, java.awt.BorderLayout.EAST);

        jLabel4.setText("          ");
        jPanel2.add(jLabel4, java.awt.BorderLayout.NORTH);

        jPanel1.add(jPanel2, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        try{
            Connection con;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            String sql = "update empleadoscheck set Nombre = ?, Departamento = ?, Sueldo = ?, Activo = ? where NumEmpleado = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            
            int n = 0;
            for (int i = 0; i < Tabla1.getRowCount(); i++) {
                String nombre;
                String depa;
                String sueldo;
                String activo;
                try{nombre = Tabla1.getValueAt(i, 1).toString();}catch(Exception e){nombre = "";}
                try{depa = Tabla1.getValueAt(i, 2).toString();}catch(Exception e){depa = "";}
                try{sueldo = Tabla1.getValueAt(i, 3).toString();}catch(Exception e){sueldo = "";}
                try{activo = Tabla1.getValueAt(i, 4).toString();}catch(Exception e){activo = "";}
                pst.setString(1, nombre);
                pst.setString(2, depa);
                pst.setString(3, sueldo);
                pst.setString(4, activo);
                pst.setString(5, Tabla1.getValueAt(i, 0).toString());
                
                n += pst.executeUpdate();
            }
            
            if(n > 0){
                JOptionPane.showMessageDialog(this, "Datos guardados");
            }else{
                JOptionPane.showMessageDialog(this, "Error al guardadr datos","Error",JOptionPane.ERROR_MESSAGE);
            }
            
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "ERROR: "+e,"ERROR",JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnGuardarActionPerformed

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
            java.util.logging.Logger.getLogger(EditarEmpleados.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(EditarEmpleados.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(EditarEmpleados.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(EditarEmpleados.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                EditarEmpleados dialog = new EditarEmpleados(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnGuardar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
