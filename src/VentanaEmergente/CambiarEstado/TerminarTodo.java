package VentanaEmergente.CambiarEstado;

import Conexiones.Conexion;
import com.mxrck.autocompleter.TextAutoCompleter;
import java.awt.Color;
import java.awt.Font;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import pruebas.CambiarEstado;

public final class TerminarTodo extends javax.swing.JDialog {

    TextAutoCompleter ac;
    Stack<String> pila;
    
    public void addProy(){
        ac = new TextAutoCompleter(txtProyecto);
        pila = new Stack<>();
        try{
            Connection con;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            String sql = "select Proyecto from Proyectos";
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
                ac.addItem(rs.getString("Proyecto"));
                pila.push(rs.getString("Proyecto"));
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "ERROR: " + e,"ERROR",JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public boolean checar(String proyecto){
        boolean band = true;
        if(Tabla1.getRowCount() == 0){
            return true;
        }else{
            for (int i = 0; i < Tabla1.getRowCount(); i++) {
                if(Tabla1.getValueAt(i, 0).toString().equals(proyecto)){
                    band = false;
                }
            }
            return band;
        }
    }
    
    public boolean verificarProyecto(String proyecto){
        return pila.search(proyecto) >= 0;
    }
    
    public boolean existePlano(String plano){
        boolean existe = false;
        try{
            Connection con;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            String sql = "select Proyecto from calidad where Proyecto like '"+plano+"'";
            String pl = null;
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
                pl = rs.getString("Proyecto");
            }
            
            if(pl == null){
                existe = false;
            }else{
                existe = true;
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "ERROR: "+e,"ERROR",JOptionPane.ERROR_MESSAGE);
        }
        
        return existe;
    }
    
    public void BD(String bd, String proyectos){
        try{
            Connection con;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            String sql = "select Terminado, Plano, Proyecto,Prioridad from "+bd+" where Terminado like 'NO'" + proyectos;
            ResultSet rs = st.executeQuery(sql);
            String sql2 = "update " + bd +" set Terminado = ? where Proyecto = ?";
            PreparedStatement pst2 = con.prepareStatement(sql2);
            while(rs.next()){
                int n = 0;
                int n1 = 0;
                int n2 = 0;
                String plano = rs.getString("Proyecto");
                String proyecto = rs.getString("Plano");
                String prioridad = rs.getString("Prioridad");
                
                pst2.setString(1, "SI");
                pst2.setString(2, plano);
                n = pst2.executeUpdate();
                
                if(existePlano(plano)){
                    String sql3 = "update calidad set Terminado = ?, Tratamiento = ? where Proyecto = ?";
                    PreparedStatement pst3 = con.prepareStatement(sql3);
                    
                    pst3.setString(1, "SI");
                    pst3.setString(2, "NO");
                    
                    n1 = pst3.executeUpdate();
                    
                }else{
                    String sql3 = "insert into calidad (Proyecto, Plano, FechaInicio,FechaFinal, Terminado, Estado, Tratamiento, Cronometro, Prioridad) values(?,?,?,?,?,?,?,?,?)";
                    PreparedStatement pst3 = con.prepareStatement(sql3);
                    
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                    Date d = new Date();
                    String fecha = sdf.format(d);
                    
                    pst3.setString(1, plano);
                    pst3.setString(2, proyecto);
                    pst3.setString(3, fecha);
                    pst3.setString(4, fecha);
                    pst3.setString(5, "SI");
                    pst3.setString(6, "NO");
                    pst3.setString(7, "");
                    pst3.setString(8, "00:00");
                    pst3.setString(9, prioridad);
                    
                    n2 = pst3.executeUpdate();
                }
                if(n > 0){
                    txtConsola.setText(txtConsola.getText() + "<p class='content'>Trabajando en Plano "+plano+"</p>");
                }
                if(n1 > 0){
                    txtConsola.setText(txtConsola.getText() + "<p class='content-act'>El Plano "+plano+" se actualizo a Terminado</p>");
                }else if(n2 > 0){
                    txtConsola.setText(txtConsola.getText() + "<p class='content-term'>El Plano "+plano+" se movio a terminado</p>");
                }
                jScrollPane2.getVerticalScrollBar().setValue(jScrollPane2.getVerticalScrollBar().getMaximum());
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "ERROR: "+e,"ERROR",JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(CambiarEstado.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
    public void pasarBD(){
        try{
            Connection con;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            String sql = "";
            String proyectos = "";
            for (int i = 0; i < Tabla1.getRowCount(); i++) {
                proyectos += " and Plano !=" + "'" + Tabla1.getValueAt(i, 0).toString() + "' ";
            }
            Stack<String> pila = new Stack<>();
            pila.push("datos");
            pila.push("fresadora");
            pila.push("cnc");
            pila.push("torno");
            pila.push("acabados");
            pila.push("calidad");
            for (int i = 0; i < pila.size(); i++) {
                txtConsola.setText(txtConsola.getText() + "<p class='inicio'>Inicio "+pila.get(i)+"</p>");
                BD(pila.get(i),proyectos);
                txtConsola.setText(txtConsola.getText() + "<p class='final'>Final "+pila.get(i)+"</p>");
                txtConsola.setText(txtConsola.getText() + "<p class='final'></p>");
            }
            btnEmpezar.setEnabled(true);
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "ERROR:" + e,"ERROR", JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(CambiarEstado.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
    public TerminarTodo(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        addProy();
        Tabla1.getTableHeader().setFont(new Font("Roboto", Font.PLAIN, 14));
        Tabla1.getTableHeader().setOpaque(false);
        Tabla1.getTableHeader().setBackground(new Color(0, 78, 171));
        Tabla1.getTableHeader().setForeground(Color.white);
        Tabla1.setRowHeight(25);
        Tabla1.setShowGrid(false);
        jScrollPane1.getViewport().setBackground(Color.white);
        jScrollPane2.getViewport().setBackground(Color.white);
        jScrollPane2.getVerticalScrollBar().setUnitIncrement(15);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPopupMenu1 = new javax.swing.JPopupMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jPanel1 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtProyecto = new RSMaterialComponent.RSTextFieldMaterial();
        jScrollPane1 = new javax.swing.JScrollPane();
        Tabla1 = new javax.swing.JTable();
        jPanel7 = new javax.swing.JPanel();
        btnEmpezar = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtConsola = new javax.swing.JLabel();

        jMenuItem1.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        jMenuItem1.setText("Eliminar fila(s)                     ");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jPopupMenu1.add(jMenuItem1);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new java.awt.BorderLayout());

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 165, 252));
        jLabel1.setText("Terminar proyectos en produccion");
        jPanel6.add(jLabel1);

        jPanel1.add(jPanel6, java.awt.BorderLayout.NORTH);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new java.awt.GridLayout(1, 2, 15, 15));

        jPanel3.setBackground(new java.awt.Color(250, 250, 250));
        jPanel3.setLayout(new java.awt.BorderLayout(10, 10));

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setLayout(new java.awt.GridBagLayout());

        jLabel2.setFont(new java.awt.Font("Roboto", 1, 16)); // NOI18N
        jLabel2.setText("Introduce proyecto que NO SE TERMINARA");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 5, 0);
        jPanel5.add(jLabel2, gridBagConstraints);

        txtProyecto.setForeground(new java.awt.Color(51, 51, 51));
        txtProyecto.setCaretColor(new java.awt.Color(153, 153, 153));
        txtProyecto.setColorMaterial(new java.awt.Color(153, 153, 153));
        txtProyecto.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        txtProyecto.setPhColor(new java.awt.Color(153, 204, 255));
        txtProyecto.setPlaceholder("Introduce proyecto");
        txtProyecto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtProyectoActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(6, 0, 6, 0);
        jPanel5.add(txtProyecto, gridBagConstraints);

        jPanel3.add(jPanel5, java.awt.BorderLayout.PAGE_START);

        jScrollPane1.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(255, 255, 255)));

        Tabla1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "PROYECTOS ACTIVOS"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        Tabla1.setComponentPopupMenu(jPopupMenu1);
        Tabla1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Tabla1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(Tabla1);

        jPanel3.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));

        btnEmpezar.setBackground(new java.awt.Color(255, 255, 255));
        btnEmpezar.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        btnEmpezar.setForeground(new java.awt.Color(51, 51, 51));
        btnEmpezar.setText("EMPEZAR");
        btnEmpezar.setBorder(null);
        btnEmpezar.setBorderPainted(false);
        btnEmpezar.setContentAreaFilled(false);
        btnEmpezar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEmpezar.setFocusPainted(false);
        btnEmpezar.setPreferredSize(new java.awt.Dimension(100, 30));
        btnEmpezar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEmpezarActionPerformed(evt);
            }
        });
        jPanel7.add(btnEmpezar);

        jPanel3.add(jPanel7, java.awt.BorderLayout.PAGE_END);

        jPanel2.add(jPanel3);

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setLayout(new java.awt.BorderLayout());

        jLabel3.setText("   ");
        jPanel4.add(jLabel3, java.awt.BorderLayout.PAGE_START);

        jLabel4.setText("      ");
        jPanel4.add(jLabel4, java.awt.BorderLayout.LINE_START);

        jLabel5.setText("      ");
        jPanel4.add(jLabel5, java.awt.BorderLayout.LINE_END);

        jLabel6.setText("   ");
        jPanel4.add(jLabel6, java.awt.BorderLayout.SOUTH);

        jScrollPane2.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane2.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(255, 255, 255)));

        txtConsola.setBackground(new java.awt.Color(255, 255, 255));
        txtConsola.setFont(new java.awt.Font("Consolas", 0, 14)); // NOI18N
        txtConsola.setText("<html> \n<style type=\"text/css\">\n .inicio{\n   color: green;\n   font-weight: 700;\n}\n.final{\n  color: blue;\n  font-weight: 700;\n}\n.content{\ncolor:gray;\nfont-size:10px;\n}\n.content-act{\ncolor:  rgb(182, 134, 11);\nfont-size:10px;\n}\n.content-term{\ncolor: rgb(1, 1, 140);\nfont-size:10px;\n}\n</style>\n}");
        txtConsola.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jScrollPane2.setViewportView(txtConsola);

        jPanel4.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        jPanel2.add(jPanel4);

        jPanel1.add(jPanel2, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void txtProyectoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtProyectoActionPerformed
        if(checar(txtProyecto.getText())){
            if(verificarProyecto(txtProyecto.getText())){
                DefaultTableModel miModelo = (DefaultTableModel) Tabla1.getModel();
                String datos[] = new String[2];
                datos[0] = txtProyecto.getText();
                miModelo.addRow(datos);
                txtProyecto.setText("");
            }else{
                JOptionPane.showMessageDialog(this, "Este proyecto no existe","Advertencia",JOptionPane.WARNING_MESSAGE);
                txtProyecto.setText("");
            }
        }else{
            JOptionPane.showMessageDialog(this, "Este proyecto ya esta en la tabla","Advertencia",JOptionPane.WARNING_MESSAGE);
            txtProyecto.setText("");
        }
    }//GEN-LAST:event_txtProyectoActionPerformed

    private void Tabla1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Tabla1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_Tabla1MouseClicked

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        int opc = JOptionPane.showConfirmDialog(this, "Estas seguro de eliminar la(s) fila(s)");
        if(opc == 0){
            DefaultTableModel miModelo = (DefaultTableModel) Tabla1.getModel();
            for (int i = Tabla1.getSelectedRows().length-1; i >= 0; i--) {
                miModelo.removeRow(Tabla1.getSelectedRows()[i]);
            }
        }
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void btnEmpezarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEmpezarActionPerformed
        int opc = JOptionPane.showConfirmDialog(this, "Estas seguro de continuar?");
        if(opc == 0){
            Thread hilo = new Thread() {
            public void run() {
                btnEmpezar.setEnabled(false);
                pasarBD();
            }
            };
            hilo.start();
           
        }
    }//GEN-LAST:event_btnEmpezarActionPerformed

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
            java.util.logging.Logger.getLogger(TerminarTodo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TerminarTodo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TerminarTodo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TerminarTodo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                TerminarTodo dialog = new TerminarTodo(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnEmpezar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel txtConsola;
    private RSMaterialComponent.RSTextFieldMaterial txtProyecto;
    // End of variables declaration//GEN-END:variables
}
