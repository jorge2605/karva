package VentanaEmergente.Costos;

import Conexiones.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;

public class CostoHoras extends javax.swing.JDialog {

    public void guardar(){
        if(txtMOD.getText().equals("")){
            JOptionPane.showMessageDialog(this, "El campo MOD DISEÑO NO TIENE QUE ESTAR VACIO","ADVERTENCIA",JOptionPane.WARNING_MESSAGE);
        }else if(txtModElec.getText().equals("")){
            JOptionPane.showMessageDialog(this, "El campo MOD ELECTROMECANICO NO TIENE QUE ESTAR VACIO","ADVERTENCIA",JOptionPane.WARNING_MESSAGE);
        }else if(txtMODHerr.getText().equals("")){
            JOptionPane.showMessageDialog(this, "El campo MOD HERRAMENTISTA NO TIENE QUE ESTAR VACIO","ADVERTENCIA",JOptionPane.WARNING_MESSAGE);
        }else if(txtMOI.getText().equals("")){
            JOptionPane.showMessageDialog(this, "El campo MOI NO TIENE QUE ESTAR VACIO","ADVERTENCIA",JOptionPane.WARNING_MESSAGE);
        }else if(txtCI.getText().equals("")){
            JOptionPane.showMessageDialog(this, "El campo CI DISEÑO NO TIENE QUE ESTAR VACIO","ADVERTENCIA",JOptionPane.WARNING_MESSAGE);
        }else{
            try{
                Connection con;
                Conexion con1 = new Conexion(); 
                con = con1.getConnection();
                String sql = "insert into costohoras(MODDiseño,MODElectromecanico,MODHerramentista,MOI,CI, Fecha) values(?,?,?,?,?,?)";
                PreparedStatement pst = con.prepareStatement(sql);

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date d = new Date();
                
                pst.setString(1, txtMOD.getText());
                pst.setString(2, txtModElec.getText());
                pst.setString(3, txtMODHerr.getText());
                pst.setString(4, txtMOI.getText());
                pst.setString(5, txtCI.getText());
                pst.setString(6, sdf.format(d));

                int n = pst.executeUpdate();

                if(n > 0){
                    JOptionPane.showMessageDialog(this, "Datos Guardados");
                    dispose();
                }

            }catch(SQLException e){
                JOptionPane.showMessageDialog(this, "ERROR: "+e,"ERROR",JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    public final void verDatos(){
        try{
            Connection con;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            String sql = "select * from costohoras";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
                txtMOD.setText(rs.getString("MODDiseño"));
                txtModElec.setText(rs.getString("MODElectromecanico"));
                txtMODHerr.setText(rs.getString("MODHerramentista"));
                txtMOI.setText(rs.getString("MOI"));
                txtCI.setText(rs.getString("CI"));
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "ERROR: "+e,"error",JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public CostoHoras(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        verDatos();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        txtMOD = new javax.swing.JTextField();
        txtModElec = new javax.swing.JTextField();
        txtMODHerr = new javax.swing.JTextField();
        txtMOI = new javax.swing.JTextField();
        txtCI = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        btnGuardar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(754, 283));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new java.awt.BorderLayout());

        jLabel1.setFont(new java.awt.Font("Lexend", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 153, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Costo por departamento");
        jPanel1.add(jLabel1, java.awt.BorderLayout.PAGE_START);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new java.awt.BorderLayout(20, 0));

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setLayout(new java.awt.GridLayout(5, 5, 5, 20));

        jLabel2.setFont(new java.awt.Font("Lexend", 1, 12)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("MOD DISEÑO");
        jPanel4.add(jLabel2);

        jLabel3.setFont(new java.awt.Font("Lexend", 1, 12)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("MOD ELECROMECANICO");
        jPanel4.add(jLabel3);

        jLabel4.setFont(new java.awt.Font("Lexend", 1, 12)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("MOD HERRAMENTISTA");
        jPanel4.add(jLabel4);

        jLabel5.setFont(new java.awt.Font("Lexend", 1, 12)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("MOI");
        jPanel4.add(jLabel5);

        jLabel6.setFont(new java.awt.Font("Lexend", 1, 12)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("CI");
        jPanel4.add(jLabel6);

        jPanel2.add(jPanel4, java.awt.BorderLayout.LINE_START);

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setLayout(new java.awt.GridLayout(5, 5, 5, 20));

        txtMOD.setBackground(new java.awt.Color(255, 255, 255));
        txtMOD.setFont(new java.awt.Font("Lexend", 0, 12)); // NOI18N
        txtMOD.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(204, 204, 204)));
        jPanel5.add(txtMOD);

        txtModElec.setBackground(new java.awt.Color(255, 255, 255));
        txtModElec.setFont(new java.awt.Font("Lexend", 0, 12)); // NOI18N
        txtModElec.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(204, 204, 204)));
        jPanel5.add(txtModElec);

        txtMODHerr.setBackground(new java.awt.Color(255, 255, 255));
        txtMODHerr.setFont(new java.awt.Font("Lexend", 0, 12)); // NOI18N
        txtMODHerr.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(204, 204, 204)));
        jPanel5.add(txtMODHerr);

        txtMOI.setBackground(new java.awt.Color(255, 255, 255));
        txtMOI.setFont(new java.awt.Font("Lexend", 0, 12)); // NOI18N
        txtMOI.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(204, 204, 204)));
        jPanel5.add(txtMOI);

        txtCI.setBackground(new java.awt.Color(255, 255, 255));
        txtCI.setFont(new java.awt.Font("Lexend", 0, 12)); // NOI18N
        txtCI.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(204, 204, 204)));
        jPanel5.add(txtCI);

        jPanel2.add(jPanel5, java.awt.BorderLayout.CENTER);

        jPanel1.add(jPanel2, java.awt.BorderLayout.CENTER);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        btnGuardar.setFont(new java.awt.Font("Lexend", 1, 14)); // NOI18N
        btnGuardar.setForeground(new java.awt.Color(0, 102, 255));
        btnGuardar.setText("Guardar");
        btnGuardar.setBorder(null);
        btnGuardar.setBorderPainted(false);
        btnGuardar.setContentAreaFilled(false);
        btnGuardar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnGuardar.setFocusPainted(false);
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });
        jPanel3.add(btnGuardar);

        jPanel1.add(jPanel3, java.awt.BorderLayout.PAGE_END);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        guardar();
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
            java.util.logging.Logger.getLogger(CostoHoras.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CostoHoras.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CostoHoras.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CostoHoras.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                CostoHoras dialog = new CostoHoras(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnGuardar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JTextField txtCI;
    private javax.swing.JTextField txtMOD;
    private javax.swing.JTextField txtMODHerr;
    private javax.swing.JTextField txtMOI;
    private javax.swing.JTextField txtModElec;
    // End of variables declaration//GEN-END:variables
}
