package VentanaEmergente.CambiarEstado;

import Conexiones.Conexion;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class InformacionProyectos extends javax.swing.JDialog {

    public int verificarTabla(String planoVer){
        for (int i = 0; i < Tabla1.getRowCount(); i += 2) {
            String plano = Tabla1.getValueAt(i, 0).toString();
            if(planoVer.equals(plano)){
                return i;
            }
        }
        return -1;
    }
    
    public String nombreEmpleado(String numEmpleado, Connection con) throws SQLException{
        Statement st = con.createStatement();
        String sql = "select * from registroempleados where NumEmpleado like '" + numEmpleado + "'";
        ResultSet rs = st.executeQuery(sql);
        while(rs.next()){
            return rs.getString("Nombre") + " " + rs.getString("Apellido");
        }
        return null;
    }
    
    public void agregarEstacion(String estacion, String proyecto, Connection con,int col) throws SQLException{
        DefaultTableModel miModelo = (DefaultTableModel) Tabla1.getModel();
        Statement st = con.createStatement();
        String sql = "select * from " + estacion + " where Plano like '" + proyecto + "%'";
        ResultSet rs = st.executeQuery(sql);
        String datos[] = new String[10];
        while(rs.next()){
            datos[0] = rs.getString("Proyecto");
            datos[col] = rs.getString("Cronometro");
            datos[8] = nombreEmpleado(rs.getString("Empleado"), con);
            int verificar = verificarTabla(datos[0]);
            if(verificar == -1){
                miModelo.addRow(datos);
                datos[0] = "Empleado";
                datos[1] = "";
                datos[2] = "";
                datos[col] = datos[8];
                miModelo.addRow(datos);
            }else{
                Tabla1.setValueAt(datos[col], verificar, col);
                Tabla1.setValueAt(datos[8], verificar + 1, col);
            }
        }
    }
    
    public final void verDatos(String proyecto){
        try{
            Connection con;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            agregarEstacion("torno", proyecto, con, 1);
            agregarEstacion("fresadora", proyecto, con, 2);
            agregarEstacion("cnc", proyecto, con, 3);
            agregarEstacion("rectificado", proyecto, con, 4);
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "Error: " + e, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public InformacionProyectos(java.awt.Frame parent, boolean modal, String proyecto) {
        super(parent, modal);
        initComponents();
        verDatos(proyecto);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        Tabla1 = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(809, 411));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new java.awt.BorderLayout());

        jLabel1.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 51, 51));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Estado de planos");
        jPanel1.add(jLabel1, java.awt.BorderLayout.PAGE_START);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new java.awt.BorderLayout());

        Tabla1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Plano", "Torno", "Fresa", "Cnc", "Rectificado", "Empleado"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(Tabla1);

        jPanel2.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jPanel1.add(jPanel2, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public static void main(String args[]) {
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                InformacionProyectos dialog = new InformacionProyectos(new javax.swing.JFrame(), true,"1012");
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
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
