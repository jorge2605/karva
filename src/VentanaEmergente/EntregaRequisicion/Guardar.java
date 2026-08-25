package VentanaEmergente.EntregaRequisicion;

import Conexiones.Conexion;
import Conexiones.ConexionChat;
import com.app.sockets.chat.Cliente;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import static java.lang.Thread.sleep;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import pruebas.EntregaRequisicion;
import pruebas.Inicio1;

public class Guardar extends javax.swing.JDialog {

    String numEmpleado = "";
    String[] parte, cantidad, id;
    int cont = 0;
    EntregaRequisicion e;
    
    public void crearNotificacion(String requi){
        
        try{
            Connection con = null;
            ConexionChat con1 = new ConexionChat();
            con = con1.getConnection();
            
            Connection con2 = null;
            Conexion con3 = new Conexion();
            con2 = con3.getConnection();
            
            Statement st = con.createStatement();
            Date d = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            String fecha = sdf.format(d);
            String sql2 = "select * from registroempleados where Almacen like 'SI'";
            Statement st2 = con2.createStatement();
            ResultSet rs2 = st2.executeQuery(sql2);
            String ip;
            int port;
            String empleado;
            System.out.println(numEmpleado);
            while(rs2.next()){
                ip = rs2.getString("Ip");
                port = rs2.getInt("Puerto");
                empleado = rs2.getString("NumEmpleado");
                
                
                String not = "noti"+empleado;
                String sql = "insert into "+not+" (Departamento,Titulo,Texto,Fecha) values (?,?,?,?)";
                PreparedStatement pst = con.prepareStatement(sql);
                pst.setString(1, "4");
                pst.setString(2, "NUEVO PEDIDO");
                pst.setString(3, "NUEVO PEDIDO PARA ALMACEN *"+requi+"*, CLIC PARA MAS DETALLES");
                pst.setString(4, fecha);

                pst.executeUpdate();
                Cliente cliente = new Cliente(port+1, "NUEVO PEDIDO",ip);
                Thread hilo = new Thread(cliente);
                hilo.start();
            }
            
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "ERROR: "+e,"ERROR",JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(Inicio1.class.getName()).log(Level.SEVERE,null,e);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Guardar.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void activar(){
        
        Thread hilo = new Thread() {
            public void run() {
                for (;;) {
                    if (true) {
                        try {
                            sleep(150);
                            if(jPanel13.getBackground().equals(new Color(255,120,0))){
                                jPanel13.setBackground(Color.white);
                                jPanel14.setBackground(new Color(255,120,0));
                                jPanel15.setBackground(Color.white);
                                jPanel16.setBackground(Color.white);
                            }else if(jPanel14.getBackground().equals(new Color(255,120,0))){
                                jPanel13.setBackground(Color.white);
                                jPanel14.setBackground(Color.white);
                                jPanel16.setBackground(new Color(255,120,0));
                                jPanel15.setBackground(Color.white);
                            }else if(jPanel15.getBackground().equals(new Color(255,120,0))){
                                jPanel16.setBackground(Color.white);
                                jPanel14.setBackground(Color.white);
                                jPanel15.setBackground(Color.white);
                                jPanel13.setBackground(new Color(255,120,0));
                            }else if(jPanel16.getBackground().equals(new Color(255,120,0))){
                                jPanel15.setBackground(new Color(255,120,0));
                                jPanel14.setBackground(Color.white);
                                jPanel13.setBackground(Color.white);
                                jPanel16.setBackground(Color.white);
                            }
                        } catch (Exception e) {
                            JOptionPane.showMessageDialog(null, "ERROR EN CRONOMETRO");
                        }
                    } else {
                        break;
                    }
                }
            }
        };
        hilo.start();
        
    }
    
    public String verParte(){
        String cadena = "";
        for (int i = 0; i < cont; i++) {
            cadena = cadena  + parte[i] +": " + cantidad[i] + "\n";
        }
        return cadena;
    }
    
    public Guardar(java.awt.Frame parent, boolean modal, String[] parte, String[] cantidad, String[] id, int cont, String numEmpleado, EntregaRequisicion en) {
        super(parent, modal);
        initComponents();
        this.numEmpleado = numEmpleado;
        this.parte = parte;
        this.cantidad = cantidad;
        this.id = id;
        this.cont = cont;
        this.e = en;
        fecha.getCalendarButton().setBackground(Color.white);
        fecha.getCalendarButton().setBorder(null);
        fecha.getCalendarButton().setContentAreaFilled(false);
        fecha.getCalendarButton().setCursor(new Cursor(Cursor.HAND_CURSOR));
        fecha.getComponent(0).setBackground(Color.white);
        fecha.setIcon(new ImageIcon(getClass().getResource("/Iconos/calendario.png")));
        jPanel13.setBackground(new Color(255,120,0));
        this.setBackground(new Color(0,0,0,0));
        jPanel1.setBorder(javax.swing.BorderFactory.createMatteBorder(4, 4, 4, 4, new java.awt.Color(0, 0, 0,15)));
        jPanel2.setBorder(javax.swing.BorderFactory.createMatteBorder(4, 4, 4, 4, new java.awt.Color(0, 0, 0,0)));
        jPanel4.setBorder(javax.swing.BorderFactory.createMatteBorder(4, 4, 4, 4, new java.awt.Color(0, 0, 0,0)));
        txtArticulos.setText(verParte());
        activar();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jPanel14 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jPanel15 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jPanel16 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jPanel17 = new javax.swing.JPanel();
        btnSalir = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        txtEmpleado = new javax.swing.JTextField();
        jPanel6 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        fecha = new com.toedter.calendar.JDateChooser();
        jPanel8 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtArticulos = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setPreferredSize(new java.awt.Dimension(750, 400));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(0, 0, 0)));
        jPanel1.setLayout(new java.awt.BorderLayout());

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new java.awt.BorderLayout());

        jPanel11.setBackground(new java.awt.Color(255, 255, 255));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Roboto", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(102, 102, 102));
        jLabel1.setText("   Enviar requisicion   ");
        jPanel3.add(jLabel1);

        jPanel11.add(jPanel3);

        jPanel2.add(jPanel11, java.awt.BorderLayout.CENTER);

        jPanel12.setBackground(new java.awt.Color(255, 255, 255));
        jPanel12.setLayout(new java.awt.GridLayout(2, 2));

        jPanel13.setBackground(new java.awt.Color(255, 255, 255));

        jLabel2.setText("    ");
        jPanel13.add(jLabel2);

        jPanel12.add(jPanel13);

        jPanel14.setBackground(new java.awt.Color(255, 255, 255));

        jLabel3.setText("    ");
        jPanel14.add(jLabel3);

        jPanel12.add(jPanel14);

        jPanel15.setBackground(new java.awt.Color(255, 255, 255));

        jLabel4.setText("    ");
        jPanel15.add(jLabel4);

        jPanel12.add(jPanel15);

        jPanel16.setBackground(new java.awt.Color(255, 255, 255));

        jLabel5.setText("    ");
        jPanel16.add(jLabel5);

        jPanel12.add(jPanel16);

        jPanel2.add(jPanel12, java.awt.BorderLayout.WEST);

        jPanel17.setBackground(new java.awt.Color(255, 255, 255));

        btnSalir.setBackground(new java.awt.Color(255, 255, 255));

        jLabel8.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("  X  ");
        jLabel8.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel8MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel8MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel8MouseExited(evt);
            }
        });
        btnSalir.add(jLabel8);

        jPanel17.add(btnSalir);

        jPanel2.add(jPanel17, java.awt.BorderLayout.EAST);

        jPanel1.add(jPanel2, java.awt.BorderLayout.PAGE_START);

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setLayout(new java.awt.BorderLayout(10, 10));

        jPanel7.setLayout(new javax.swing.BoxLayout(jPanel7, javax.swing.BoxLayout.LINE_AXIS));

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setLayout(new javax.swing.BoxLayout(jPanel5, javax.swing.BoxLayout.Y_AXIS));

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));

        jLabel6.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(204, 204, 204));
        jLabel6.setText("NO. EMPLEADO");
        jPanel9.add(jLabel6);

        jPanel5.add(jPanel9);

        txtEmpleado.setBackground(new java.awt.Color(255, 255, 255));
        txtEmpleado.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        txtEmpleado.setForeground(new java.awt.Color(0, 0, 255));
        txtEmpleado.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtEmpleado.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        txtEmpleado.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtEmpleadoMouseClicked(evt);
            }
        });
        txtEmpleado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEmpleadoActionPerformed(evt);
            }
        });
        jPanel5.add(txtEmpleado);

        jPanel7.add(jPanel5);

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setLayout(new javax.swing.BoxLayout(jPanel6, javax.swing.BoxLayout.Y_AXIS));

        jPanel10.setBackground(new java.awt.Color(255, 255, 255));

        jLabel7.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(204, 204, 204));
        jLabel7.setText("FECHA");
        jPanel10.add(jLabel7);

        jPanel6.add(jPanel10);

        fecha.setBackground(new java.awt.Color(255, 255, 255));
        fecha.setForeground(new java.awt.Color(0, 0, 0));
        fecha.setDateFormatString("dd/MMMM/yyyy");
        fecha.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        jPanel6.add(fecha);

        jPanel7.add(jPanel6);

        jPanel4.add(jPanel7, java.awt.BorderLayout.NORTH);

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));
        jPanel8.setLayout(new java.awt.BorderLayout());

        jButton1.setBackground(new java.awt.Color(255, 255, 255));
        jButton1.setFont(new java.awt.Font("Roboto", 1, 22)); // NOI18N
        jButton1.setText("GUARDAR");
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
        jPanel8.add(jButton1, java.awt.BorderLayout.SOUTH);

        jScrollPane1.setBorder(null);

        txtArticulos.setEditable(false);
        txtArticulos.setBackground(new java.awt.Color(255, 255, 255));
        txtArticulos.setColumns(20);
        txtArticulos.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtArticulos.setLineWrap(true);
        txtArticulos.setRows(5);
        txtArticulos.setWrapStyleWord(true);
        txtArticulos.setBorder(null);
        jScrollPane1.setViewportView(txtArticulos);

        jPanel8.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jPanel4.add(jPanel8, java.awt.BorderLayout.CENTER);

        jPanel1.add(jPanel4, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void txtEmpleadoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtEmpleadoMouseClicked
        if(evt.getClickCount() == 2){
            txtEmpleado.setEditable(true);
            txtEmpleado.setFont(new java.awt.Font("Roboto", 0, 12));
            txtEmpleado.setForeground(Color.black);
            txtEmpleado.setText("");
        }
    }//GEN-LAST:event_txtEmpleadoMouseClicked

    private void txtEmpleadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEmpleadoActionPerformed
        if(txtEmpleado.isEditable()){
            try{
                Connection con = null;
                Conexion con1 = new Conexion();
                con = con1.getConnection();
                Statement st = con.createStatement();
                String sql ="select * from registroempleados where NumEmpleado like '"+txtEmpleado.getText()+"'";
                ResultSet rs = st.executeQuery(sql);
                String num = "";
                String nombre = "";
                while(rs.next()){
                    num = rs.getString("NumEmpleado");
                    nombre = rs.getString("Nombre") + " " + rs.getString("Apellido");
                }

                if(num == null){
                    JOptionPane.showMessageDialog(this, "NO EXISTE USUARIO","ADVERTENCIA",JOptionPane.WARNING_MESSAGE);
                    txtEmpleado.setText("");
                }else{
                    if(num.equals("")){
                        JOptionPane.showMessageDialog(this, "NO EXISTE USUARIO","ADVERTENCIA",JOptionPane.WARNING_MESSAGE);
                        txtEmpleado.setText("");
                    }else{
                        numEmpleado = txtEmpleado.getText();
                        txtEmpleado.setText(nombre);
                        txtEmpleado.setEditable(false);
                        txtEmpleado.setForeground(Color.blue);
                        txtEmpleado.setFont(new Font("Roboto",Font.BOLD,12));
                    }
                }

            }catch(SQLException e){
                JOptionPane.showMessageDialog(this, "ERROR: "+e,"ERROR",JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_txtEmpleadoActionPerformed

    private void jLabel8MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel8MouseClicked
        dispose();
    }//GEN-LAST:event_jLabel8MouseClicked

    private void jLabel8MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel8MouseEntered
        btnSalir.setBackground(java.awt.Color.red);
        jLabel8.setForeground(Color.white);
    }//GEN-LAST:event_jLabel8MouseEntered

    private void jLabel8MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel8MouseExited
        btnSalir.setBackground(java.awt.Color.white);
        jLabel8.setForeground(Color.black);
    }//GEN-LAST:event_jLabel8MouseExited

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        try{
            Connection con;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st2 = con.createStatement();
            String sql = "insert into pedrequisicion (NumEmpleado, Fecha, Estado, FechaVisto, FechaTerminado, FechaEntrega, Almacenista, Comentarios, Requisicion) values(?,?,?,?,?,?,?,?,?)";
            PreparedStatement pst = con.prepareStatement(sql);
            
            String requisicion = "";
            for (int i = 0; i < e.contLabel; i++) {
                if(i == 0){
                    requisicion = e.requisiciones[i].getText();
                }else{
                    requisicion = requisicion + "," + e.requisiciones[i].getText();
                }
                
            }
            
            Date d = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            String fec = sdf.format(d);
            pst.setString(1, numEmpleado);
            pst.setString(2, fec);
            pst.setString(3, "NUEVO");
            pst.setString(4, "");
            pst.setString(5, "");
            pst.setString(6, "");
            pst.setString(7, "");
            pst.setString(8, "");
            pst.setString(9, requisicion);
            
            int n = pst.executeUpdate();
            
            String sql2 = "select * from pedrequisicion";
            ResultSet rs2 = st2.executeQuery(sql2);
            String id = "";
            while(rs2.next()){
                id = rs2.getString("Id");
            }
            
            int n1 = 0;
            
            for (int i = 0; i < cont; i++) {
               String sql3 = "insert into detrequisicion (IdPedido, IdArticulo, Departamento, Encontrado, Cantidad, Estado) values(?,?,?,?,?,?)";
               PreparedStatement pst3 = con.prepareStatement(sql3);
               
               pst3.setString(1, id);
               pst3.setString(2, this.id[i]);
               pst3.setString(3, "REQUISICIONES");
               pst3.setString(4, "");
               pst3.setString(5, this.cantidad[i]);
               pst3.setString(6, "NUEVO");
               
               n1 = pst3.executeUpdate();
            }
            
            if(n > 0 && n1 > 0){
                JOptionPane.showMessageDialog(this,"DATOS GUARDADOS");
                crearNotificacion(id);
                e.limpiarPantalla();
                dispose();
            }
            
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "ERROR: "+e, "ERROR",JOptionPane.ERROR_MESSAGE);
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
            java.util.logging.Logger.getLogger(Guardar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Guardar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Guardar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Guardar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Guardar dialog = new Guardar(new javax.swing.JFrame(), true, null,null,null,0,"",null);
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
    private javax.swing.JPanel btnSalir;
    private com.toedter.calendar.JDateChooser fecha;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea txtArticulos;
    private javax.swing.JTextField txtEmpleado;
    // End of variables declaration//GEN-END:variables
}
