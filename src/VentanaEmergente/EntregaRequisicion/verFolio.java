package VentanaEmergente.EntregaRequisicion;

import Conexiones.Conexion;
import java.awt.Color;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

public class verFolio extends javax.swing.JDialog {

    public verFolio(java.awt.Frame parent, boolean modal,String foli) {
        super(parent, modal);
        initComponents();
        panel1.setBackground(new Color(92, 195, 255, 125));
        panel3.setBackground(new Color(92, 195, 255, 125));
        panel5.setBackground(new Color(92, 195, 255, 125));
        panel7.setBackground(new Color(92, 195, 255, 125));
        
        panel2.setBackground(new Color(228, 245, 255, 10));
        panel4.setBackground(new Color(228, 245, 255, 10));
        panel6.setBackground(new Color(228, 245, 255, 10));
        panel8.setBackground(new Color(228, 245, 255, 10));
        
        try{
            Connection con;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            String sql = "select * from foliorequisiciones where  Id like '"+foli+"'";
            ResultSet rs = st.executeQuery(sql);
            System.out.println(foli);
            String d[] = new String[10];
            while(rs.next()){
                d[0] = rs.getString("Id");
                d[1] = rs.getString("NombreEmpleado");
                d[2] = rs.getString("NumRequisicion");
                d[3] = rs.getString("FechaFolio");
            }
            if(folio.getText() == null ){
                dispose();
            }else{
                folio.setText(d[0]);
                empleado.setText(d[1]);
                requi.setText(d[2]);
                fecha.setText(d[3]);
                revalidate();
                repaint();
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "error: "+e,"Error",JOptionPane.ERROR_MESSAGE);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel8 = new javax.swing.JPanel();
        panel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        panel2 = new javax.swing.JPanel();
        folio = new javax.swing.JLabel();
        panel3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        panel4 = new javax.swing.JPanel();
        empleado = new javax.swing.JLabel();
        panel5 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        panel6 = new javax.swing.JPanel();
        requi = new javax.swing.JLabel();
        panel7 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        panel8 = new javax.swing.JPanel();
        fecha = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(455, 750));

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));
        jPanel8.setLayout(new javax.swing.BoxLayout(jPanel8, javax.swing.BoxLayout.Y_AXIS));

        panel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Roboto", 1, 48)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 153, 255));
        jLabel1.setText("# FOLIO");
        panel1.add(jLabel1);

        jPanel8.add(panel1);

        panel2.setBackground(new java.awt.Color(255, 255, 255));

        folio.setFont(new java.awt.Font("Roboto", 1, 36)); // NOI18N
        folio.setForeground(new java.awt.Color(153, 153, 153));
        folio.setText("No");
        panel2.add(folio);

        jPanel8.add(panel2);

        panel3.setBackground(new java.awt.Color(255, 255, 255));

        jLabel3.setFont(new java.awt.Font("Roboto", 1, 48)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(51, 153, 255));
        jLabel3.setText("EMPLEADO");
        panel3.add(jLabel3);

        jPanel8.add(panel3);

        panel4.setBackground(new java.awt.Color(255, 255, 255));

        empleado.setFont(new java.awt.Font("Roboto", 1, 36)); // NOI18N
        empleado.setForeground(new java.awt.Color(153, 153, 153));
        empleado.setText("Empleado");
        panel4.add(empleado);

        jPanel8.add(panel4);

        panel5.setBackground(new java.awt.Color(255, 255, 255));

        jLabel5.setFont(new java.awt.Font("Roboto", 1, 48)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(51, 153, 255));
        jLabel5.setText("REQUISICION");
        panel5.add(jLabel5);

        jPanel8.add(panel5);

        panel6.setBackground(new java.awt.Color(255, 255, 255));

        requi.setFont(new java.awt.Font("Roboto", 1, 36)); // NOI18N
        requi.setForeground(new java.awt.Color(153, 153, 153));
        requi.setText("Requi");
        panel6.add(requi);

        jPanel8.add(panel6);

        panel7.setBackground(new java.awt.Color(255, 255, 255));

        jLabel7.setFont(new java.awt.Font("Roboto", 1, 48)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(51, 153, 255));
        jLabel7.setText("FECHA SALIDA");
        panel7.add(jLabel7);

        jPanel8.add(panel7);

        panel8.setBackground(new java.awt.Color(255, 255, 255));

        fecha.setFont(new java.awt.Font("Roboto", 1, 36)); // NOI18N
        fecha.setForeground(new java.awt.Color(153, 153, 153));
        fecha.setText("Fecha");
        panel8.add(fecha);

        jPanel8.add(panel8);

        getContentPane().add(jPanel8, java.awt.BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

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
            java.util.logging.Logger.getLogger(verFolio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(verFolio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(verFolio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(verFolio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                verFolio dialog = new verFolio(new javax.swing.JFrame(), true,"");
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
    private javax.swing.JLabel empleado;
    private javax.swing.JLabel fecha;
    private javax.swing.JLabel folio;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel panel1;
    private javax.swing.JPanel panel2;
    private javax.swing.JPanel panel3;
    private javax.swing.JPanel panel4;
    private javax.swing.JPanel panel5;
    private javax.swing.JPanel panel6;
    private javax.swing.JPanel panel7;
    private javax.swing.JPanel panel8;
    private javax.swing.JLabel requi;
    // End of variables declaration//GEN-END:variables
}
