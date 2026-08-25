package VentanaEmergente.Compras;

import Conexiones.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JButton;
import javax.swing.JOptionPane;

public class TransferirRequisicion extends javax.swing.JDialog {

    public void transferir(String requisicion, String numEmpleado){
        try{
            Connection con;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            String sql = "update requisicion set Comprador = ? where Id = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            
            pst.setString(1, numEmpleado);
            pst.setString(2, requisicion);
            
            int n = pst.executeUpdate();
            
            if(n > 0){
                JOptionPane.showMessageDialog(this, "Datos Actualizador correctamente");
            }
            
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "Error: " + e,"Error",JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void crearBoton(String nombre, String numEmpleado){
        JButton boton = new javax.swing.JButton();
        boton.setBackground(new java.awt.Color(255, 255, 255));
        boton.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        boton.setForeground(new java.awt.Color(51, 51, 51));
        boton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/editarEmpleado_16.png"))); // NOI18N
        boton.setText(nombre);
        boton.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 204)));
        boton.setContentAreaFilled(false);
        boton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        boton.setFocusPainted(false);
        boton.setPreferredSize(new java.awt.Dimension(150, 25));
        
        boton.addActionListener((java.awt.event.ActionEvent evt) -> {
            transferir(lblRequisicion.getText(), numEmpleado);
        });
        
        jPanel4.add(boton);
    }
    
    public String getEmpleado(String numEmpleado, Connection con) throws SQLException{
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery("select * from registroempleados where NumEmpleado like '" + numEmpleado + "'");
        String nombre = null;
        while(rs.next()){
            nombre = rs.getString("Nombre") + " " + rs.getString("Apellido");
        }
        return nombre;
    }
    
    public final void setCompradores(){
        try{
            Connection con;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            String sql = "select * from registroempleados where Puesto like 'COMPRAS'";
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
                crearBoton(rs.getString("Nombre") + " " + rs.getString("Apellido"), rs.getString("NumEmpleado"));
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "error: " + e,"Error",JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public final void verRequisicion() {
        try{
            Connection con;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            String sql = "select Id, Comprador from requisicion where Id like '" + txtCodigo.getText() + "'";
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
                lblNombre.setText(getEmpleado(rs.getString("Comprador"), con));
                lblRequisicion.setText(rs.getString("Id"));
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "Error: "+e,"Error",JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public TransferirRequisicion(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setCompradores();
   }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        lblCodigo = new javax.swing.JLabel();
        txtCodigo = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        lblCodigo1 = new javax.swing.JLabel();
        lblNombre = new javax.swing.JLabel();
        lblCodigo4 = new javax.swing.JLabel();
        lblRequisicion = new javax.swing.JLabel();
        lblCodigo3 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(992, 343));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new java.awt.BorderLayout());

        jLabel1.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 51, 51));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Tranferir Requsicion");
        jPanel1.add(jLabel1, java.awt.BorderLayout.PAGE_START);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        java.awt.GridBagLayout jPanel2Layout = new java.awt.GridBagLayout();
        jPanel2Layout.columnWeights = new double[] {1.0};
        jPanel2Layout.rowWeights = new double[] {0.0, 0.0, 1.0};
        jPanel2.setLayout(jPanel2Layout);

        lblCodigo.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        lblCodigo.setForeground(new java.awt.Color(153, 153, 153));
        lblCodigo.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblCodigo.setText("Numero de requisicion");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 181;
        gridBagConstraints.ipady = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 60, 3, 60);
        jPanel2.add(lblCodigo, gridBagConstraints);

        txtCodigo.setBackground(new java.awt.Color(255, 255, 255));
        txtCodigo.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        txtCodigo.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(204, 204, 204)));
        txtCodigo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodigoActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 156;
        gridBagConstraints.ipady = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(2, 60, 10, 60);
        jPanel2.add(txtCodigo, gridBagConstraints);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        java.awt.GridBagLayout jPanel3Layout = new java.awt.GridBagLayout();
        jPanel3Layout.columnWeights = new double[] {1.0, 1.0};
        jPanel3.setLayout(jPanel3Layout);

        lblCodigo1.setBackground(new java.awt.Color(51, 51, 51));
        lblCodigo1.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        lblCodigo1.setForeground(new java.awt.Color(51, 51, 51));
        lblCodigo1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblCodigo1.setText("Esta requisicion pertenece a:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel3.add(lblCodigo1, gridBagConstraints);

        lblNombre.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        lblNombre.setForeground(new java.awt.Color(204, 204, 204));
        lblNombre.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblNombre.setText("Nombre");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel3.add(lblNombre, gridBagConstraints);

        lblCodigo4.setBackground(new java.awt.Color(51, 51, 51));
        lblCodigo4.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        lblCodigo4.setForeground(new java.awt.Color(51, 51, 51));
        lblCodigo4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblCodigo4.setText("Requisicion: ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel3.add(lblCodigo4, gridBagConstraints);

        lblRequisicion.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        lblRequisicion.setForeground(new java.awt.Color(204, 204, 204));
        lblRequisicion.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblRequisicion.setText("#");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel3.add(lblRequisicion, gridBagConstraints);

        lblCodigo3.setBackground(new java.awt.Color(51, 51, 51));
        lblCodigo3.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        lblCodigo3.setForeground(new java.awt.Color(51, 51, 51));
        lblCodigo3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblCodigo3.setText("Transferir a:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel3.add(lblCodigo3, gridBagConstraints);

        jPanel4.setBackground(new java.awt.Color(235, 235, 235));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        jPanel3.add(jPanel4, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        jPanel2.add(jPanel3, gridBagConstraints);

        jPanel1.add(jPanel2, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtCodigoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodigoActionPerformed
        verRequisicion();
    }//GEN-LAST:event_txtCodigoActionPerformed

    public static void main(String args[]) {
    
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                TransferirRequisicion dialog = new TransferirRequisicion(new javax.swing.JFrame(), true);
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
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JLabel lblCodigo;
    private javax.swing.JLabel lblCodigo1;
    private javax.swing.JLabel lblCodigo3;
    private javax.swing.JLabel lblCodigo4;
    private javax.swing.JLabel lblNombre;
    private javax.swing.JLabel lblRequisicion;
    public javax.swing.JTextField txtCodigo;
    // End of variables declaration//GEN-END:variables
}
