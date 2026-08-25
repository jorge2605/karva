package VentanaEmergente.Checador;

import Conexiones.Conexion;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumn;
import rojerusan.RSTableMetro;
import scrollPane.ScrollBarCustom;

public final class editarEmpleado extends javax.swing.JDialog implements ActionListener {

    String numEmpleado;
    selectHora s;
    int row, col;
    verEmpleados ver;
    int opc;
    
    
    public void verEmpl(int opc){
        try{
            this.opc = opc;
            Connection con;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st  = con.createStatement();
            String sql = "select * from empleadoscheck where NumSupervisor is null order by Nombre";
            if(opc == 1){
                sql = "select * from empleadoscheck where NumSupervisor is not null and NumSupervisor like '"+numEmpleado+"' order by Nombre";
            }
            ResultSet rs = st.executeQuery(sql);
            Object datos[] = new Object[15];
            DefaultTableModel miModelo = (DefaultTableModel) ver.Tabla1.getModel();
            while(rs.next()){
                datos[0] = rs.getString("Nombre");
                datos[1] = rs.getString("NumEmpleado");
                datos[2] = false;
                miModelo.addRow(datos);
            }
        }catch(SQLException e ){
            JOptionPane.showMessageDialog(this, "ERROR: "+e,"ERROR",JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void limpiarTabla(){
        Tabla1.setBackground(new java.awt.Color(51, 51, 51));
        Tabla1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
            },
            new String [] {
                "No. Empleado", "Nombre", "Entrada", "Salida", "Horas diarias", "Turno", "Horas sabado", "Entrada sabado", "Salida sabado","Total horas"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, true, false, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        Tabla1.setPreferredSize(new java.awt.Dimension(1200, 550));
    }
    
    public void verEmpleados(){
        limpiarTabla();
        try{
            Connection con;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            String sql = "select * from empleadoscheck where NumSupervisor like '"+numEmpleado+"'";
            ResultSet rs = st.executeQuery(sql);
            String datos[] = new String[10];
            DefaultTableModel miModelo = (DefaultTableModel) Tabla1.getModel();
            JComboBox jcb = new JComboBox();
            jcb.addItem("MATUTINO");
            jcb.addItem("VESPERTINO");
            TableColumn tc = Tabla1.getColumnModel().getColumn(5);
            TableCellEditor tce = new DefaultCellEditor(jcb);
            tc.setCellEditor(tce);
            while(rs.next()){
                datos[0] = rs.getString("NumEmpleado");
                datos[1] = rs.getString("Nombre");
                datos[2] = rs.getString("Entrada");
                datos[3] = rs.getString("Salida");
                datos[4] = rs.getString("HorasDiarias");
                datos[5] = rs.getString("Turno");
                datos[6] = rs.getString("HoraSabado");
                datos[7] = rs.getString("EntradaSabado");
                datos[8] = rs.getString("SalidaSabado");
                datos[9] = rs.getString("TotalHoras");
                miModelo.addRow(datos);
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "ERROR: "+e,"ERROR",JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public editarEmpleado(java.awt.Frame parent, boolean modal, String numEmpleado) {
        super(parent, modal);
        initComponents();
        this.numEmpleado = numEmpleado;
        jScrollPane1.getViewport().setBackground(new Color(51,51,51));
        jScrollPane1.setVerticalScrollBar(new ScrollBarCustom(new Color(0,165,255)));
        verEmpleados();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        btnGuardar = new rojeru_san.RSButtonRiple();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        Tabla1 = new rojerusan.RSTableMetro();
        jPanel3 = new javax.swing.JPanel();
        rSButtonRoundRipple1 = new rojeru_san.rsbutton.RSButtonRoundRipple();
        rSButtonRoundRipple2 = new rojeru_san.rsbutton.RSButtonRoundRipple();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(1250, 500));
        getContentPane().setLayout(new java.awt.BorderLayout(15, 15));

        jPanel1.setBackground(new java.awt.Color(51, 51, 51));
        jPanel1.setLayout(new java.awt.BorderLayout());

        jLabel9.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 48)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 165, 252));
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("Editar mis empleados");
        jPanel1.add(jLabel9, java.awt.BorderLayout.NORTH);

        btnGuardar.setText("Guardar configuracion");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });
        jPanel1.add(btnGuardar, java.awt.BorderLayout.PAGE_END);

        jPanel2.setLayout(new java.awt.BorderLayout());

        Tabla1.setBackground(new java.awt.Color(51, 51, 51));
        Tabla1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "No. Empleado", "Nombre", "Entrada", "Salida", "Horas diarias", "Turno", "Horas sabado", "Entrada sabado", "Salida sabado", "Total horas"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        Tabla1.setPreferredSize(new java.awt.Dimension(1200, 550));
        Tabla1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Tabla1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(Tabla1);

        jPanel2.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jPanel3.setBackground(new java.awt.Color(51, 51, 51));
        jPanel3.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 25, 5));

        rSButtonRoundRipple1.setText("Añadir empleado");
        rSButtonRoundRipple1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rSButtonRoundRipple1ActionPerformed(evt);
            }
        });
        jPanel3.add(rSButtonRoundRipple1);

        rSButtonRoundRipple2.setText("Borrar empleado");
        rSButtonRoundRipple2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rSButtonRoundRipple2ActionPerformed(evt);
            }
        });
        jPanel3.add(rSButtonRoundRipple2);

        jPanel2.add(jPanel3, java.awt.BorderLayout.PAGE_START);

        jPanel1.add(jPanel2, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void Tabla1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Tabla1MouseClicked
            if(Tabla1.getSelectedColumn() == 2 || Tabla1.getSelectedColumn() == 3 || Tabla1.getSelectedColumn() == 4 || Tabla1.getSelectedColumn() == 6 || Tabla1.getSelectedColumn() == 7 || Tabla1.getSelectedColumn() == 8){
                JFrame f = (JFrame) JOptionPane.getFrameForComponent(this);
                row = Tabla1.getSelectedRow();
                col = Tabla1.getSelectedColumn();
                s = new selectHora(f, true);
                s.jButton1.addActionListener(this);
                s.jButton2.addActionListener(this);
                s.setLocation(evt.getLocationOnScreen());
                s.setVisible(true);
            }
    }//GEN-LAST:event_Tabla1MouseClicked

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        try{
            Connection con;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            String sql = "update empleadoscheck set Entrada = ?, Salida = ?, HorasDiarias = ?, Turno = ?, HoraSabado = ?, EntradaSabado = ?, SalidaSabado = ?,"
                    + "TotalHoras = ? where NumEmpleado = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            int n = 0;
            for (int i = 0; i < Tabla1.getRowCount(); i++) {
                pst.setString(1, Tabla1.getValueAt(i, 2).toString());
                pst.setString(2, Tabla1.getValueAt(i, 3).toString());
                pst.setString(3, Tabla1.getValueAt(i, 4).toString());
                pst.setString(4, Tabla1.getValueAt(i, 5).toString());
                pst.setString(5, Tabla1.getValueAt(i, 6).toString());
                pst.setString(6, Tabla1.getValueAt(i, 7).toString());
                pst.setString(7, Tabla1.getValueAt(i, 8).toString());
                pst.setString(8, Tabla1.getValueAt(i, 9).toString());
                pst.setString(9, Tabla1.getValueAt(i, 0).toString());
                n = pst.executeUpdate();
            }
            
            if(n > 0){
                JOptionPane.showMessageDialog(this, "DATOS GUARDADOS");
            }
            
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "ERROR"+e,"ERROR",JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void rSButtonRoundRipple1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rSButtonRoundRipple1ActionPerformed
        JFrame f = (JFrame) JOptionPane.getFrameForComponent(this);
        ver = new verEmpleados(f, true, numEmpleado);
        verEmpl(0);
        ver.btnGuardar.addActionListener(this);
        ver.setVisible(true);
    }//GEN-LAST:event_rSButtonRoundRipple1ActionPerformed

    private void rSButtonRoundRipple2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rSButtonRoundRipple2ActionPerformed
        JFrame f = (JFrame) JOptionPane.getFrameForComponent(this);
        ver = new verEmpleados(f, true, numEmpleado);
        verEmpl(1);
        ver.btnGuardar.addActionListener(this);
        ver.setVisible(true);
    }//GEN-LAST:event_rSButtonRoundRipple2ActionPerformed

    /**
     * @param args the command line arguments
     */
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
            java.util.logging.Logger.getLogger(editarEmpleado.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(editarEmpleado.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(editarEmpleado.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(editarEmpleado.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                editarEmpleado dialog = new editarEmpleado(new javax.swing.JFrame(), true,"");
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
    private rojerusan.RSTableMetro Tabla1;
    public rojeru_san.RSButtonRiple btnGuardar;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private rojeru_san.rsbutton.RSButtonRoundRipple rSButtonRoundRipple1;
    private rojeru_san.rsbutton.RSButtonRoundRipple rSButtonRoundRipple2;
    // End of variables declaration//GEN-END:variables

    @Override
    public void actionPerformed(ActionEvent e) {
        if(s != null){
            if(e.getSource() == s.jButton1){
                String hora = (String) s.rSComboBoxMaterial1.getSelectedItem() + ":" + s.rSComboBoxMaterial2.getSelectedItem() + ":00";
                Tabla1.setValueAt(hora, row, col);
                s.dispose();
                s.dispose();
                s.dispose();
                s = null;
            }
        }
        if(ver != null){
            if(e.getSource() == ver.btnGuardar){
                try{
                    Connection con;
                    Conexion con1 = new Conexion();
                    con = con1.getConnection();
                    String sql = "update empleadoscheck set NumSupervisor = ? where NumEmpleado = ?";
                    PreparedStatement pst = con.prepareStatement(sql);
                    int n = 0;
                    for (int i = 0; i < ver.Tabla1.getRowCount(); i++) {
                        if(ver.Tabla1.getValueAt(i, 2).equals(true)){
                            if(opc == 1){
                                pst.setString(1, null);
                            }else{
                            pst.setString(1, numEmpleado);
                            }
                            pst.setString(2, ver.Tabla1.getValueAt(i, 1).toString()); 
                            n = pst.executeUpdate();
                        }
                    }
                    
                    if(n > 0){
                        JOptionPane.showMessageDialog(this, "DATOS GUARDADOS");
                        ver.dispose();
                        limpiarTabla();
                        verEmpleados();
                    }
                    
                }catch(SQLException ex){
                    JOptionPane.showMessageDialog(this, "ERROR: "+ex,"ERROR",JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }
}
