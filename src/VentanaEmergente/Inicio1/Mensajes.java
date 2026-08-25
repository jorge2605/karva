package VentanaEmergente.Inicio1;

import Conexiones.Conexion;
import Conexiones.ConexionChat;
import com.app.sockets.chat.Cliente;
import com.app.sockets.chat.Servidor;
import com.app.sockets.dao.ConversacionImpl;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Mensajes extends javax.swing.JFrame implements Observer,ActionListener{

    String miNumero;
    ConversacionImpl conversacionDAO = new ConversacionImpl();
    JButton btnUsuarios[];
    String usuarios[];
    int cont;
    
    public void limpiarUsuarios(){
        panelUs.removeAll();
        revalidate();
        repaint();
    }
    
    public void mostrarContactos(){
        try{
            btnUsuarios = new JButton[1000];
            usuarios = new String[1000];
            limpiarUsuarios();
            Connection con = null;
            ConexionChat con1 = new ConexionChat();
            con = con1.getConnection();
            Statement st = con.createStatement();
            if(tableExist("chat"+miNumero) == false){
                    String crear = "CREATE TABLE `chat`.`chat"+miNumero+"` (\n" +
            "  `Id` INT NOT NULL AUTO_INCREMENT,\n" +
            "  `Contactos` VARCHAR(300) NULL,\n" +
            "  `Mostrar` VARCHAR(300) NULL,\n" +
            "  PRIMARY KEY (`Id`));";
            PreparedStatement pst = con.prepareStatement(crear);
            pst.execute();
            }
            
            String sql ="select * from chat"+miNumero+" where Mostrar like 'SI'";
            ResultSet rs = st.executeQuery(sql);
            cont = 0;
            while(rs.next()){
                String nombre = rs.getString("Contactos");
                
                if(nombre!= null){
                btnUsuarios[cont] = new JButton("");
                btnUsuarios[cont].setFont(new java.awt.Font("Roboto", 1, 14));
                btnUsuarios[cont].setText("<html><p style='padding: 5px; width:120px; margin: 2px; background-color: rgb(222,222,222);'>"+nombre+"</p></html>");
                btnUsuarios[cont].setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 1, new java.awt.Color(204, 204, 204)));
                btnUsuarios[cont].setBorderPainted(false);
                btnUsuarios[cont].setContentAreaFilled(false);
                btnUsuarios[cont].setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
                btnUsuarios[cont].setFocusPainted(false);
                btnUsuarios[cont].setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
                btnUsuarios[cont].addActionListener(this);
                usuarios[cont] = new String();
                usuarios[cont] = nombre;
                panelUs.add(btnUsuarios[cont]);
                cont++;
                }
                }
            
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "ERROR: "+e,"ERROR",JOptionPane.ERROR_MESSAGE);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Mensajes.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void iniciarServidor(){
        int port = 0;
        try{
            Connection con = null;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            String sql = "select * from registroempleados where NumEmpleado like '"+miNumero+"'";
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
                port = Integer.parseInt(rs.getString("Puerto"));
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "ERROR: "+e,"ERROR",JOptionPane.ERROR_MESSAGE);
        }
        if(port == 0){
            JOptionPane.showMessageDialog(this, "NO SE PUEDE PUDO INICIAR EL SERVIDOR","ERROR",JOptionPane.ERROR_MESSAGE);
        }else{
        Servidor servidor = new Servidor(port);
        servidor.addObserver(this);
        Thread hilo = new Thread(servidor);
        hilo.start();
        }
    }
    
    public void limpiarConversacion(){
        jPanel8.removeAll();
        revalidate();
        repaint();
    }
    
    public static boolean tableExist(String tableName) throws SQLException, ClassNotFoundException { 
        Connection con = null;
        ConexionChat con1 = new ConexionChat();
        con = con1.getConnection();
        DatabaseMetaData dbm = con.getMetaData();
        ResultSet tables = dbm.getTables(null, null, tableName, null); 
        if (tables.next()) { 
            return true;
        }else {
                    return false;
            }
    }
    
    public void addLabel(String mensaje, int turno){
        JPanel panel = new javax.swing.JPanel();
        panel.setBackground(new java.awt.Color(255, 255, 255));
        panel.setLayout(new javax.swing.BoxLayout(panel, javax.swing.BoxLayout.LINE_AXIS));
        
        JLabel label = new javax.swing.JLabel();
        label.setFont(new Font("Roboto",Font.PLAIN,14));
        if (turno == 1){
            label.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
            label.setText("<html><p style='width:300px; margin: 5px; padding: 10px;"
                    + "background-color: rgb(166, 166, 166); border-radius: 10px;'>"+
                    mensaje
                    +"</p></html>");
        }else{
            label.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
            label.setText("<html><p style='text-align: left; width:300px; margin: 5px 10px;padding: 10px; "
                + "background-color: rgb(43, 146, 255); border-radius: 10px;'>"+""
                + mensaje
                +"</p></html>");
        }
        panel.add(label);
        jPanel8.add(panel);
        revalidate();
        repaint();
    }
    
    public void agregarConversacion(String tableName, String a, String b){
        try{
            Connection con = null;
            ConexionChat con1 = new ConexionChat();
            con = con1.getConnection();
            Statement st = con.createStatement();
            String sql = "select * from " +tableName;
            ResultSet rs = st.executeQuery(sql);
            String ab,cd;
            while(rs.next()){
                ab = rs.getString(a);
                cd = rs.getString(b);
                if(cd == null){
                    addLabel(ab,1);
                }else{
                    addLabel(cd,2);
                }
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "ERROR: "+e,"ERROR",JOptionPane.ERROR_MESSAGE);
            System.out.println("aqui es el error");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Mensajes.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void verificarUsuario(String you){
        String my = miNumero;
        lblNumeroEmpleado.setText(you);
        int m = Integer.parseInt(my);
        int y = Integer.parseInt(you);
        String fin;
        if(m > y){
            fin = you + "chat" + my;
        }else{
            fin = my + "chat" + you;
        }
        String f = fin;
        try {
            if(tableExist(f)){
                agregarConversacion(f, my, you);
            }else{
            }
        } catch (SQLException ex) {
            Logger.getLogger(Mensajes.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Mensajes.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public Mensajes(String numero) {
        initComponents();
        this.getRootPane().setDefaultButton(jButton1);
        miNumero = numero;
        lblUsuarios.setText("<html><p style='padding: 10px;'>"+"USUARIOS"+"</p></html>");
        lblNuevo.setText("<html><p style='padding: 10px;'>"+"NUEVO MENSAJE"+"</p></html>");
        jScrollPane1.getVerticalScrollBar().setUnitIncrement(10);
        iniciarServidor();
        mostrarContactos();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        cLabel1 = new com.bolivia.label.CLabel();
        jLabel4 = new javax.swing.JLabel();
        lblNombreEmpleado = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        lblNumeroEmpleado = new javax.swing.JLabel();
        panelEnviar = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        txtTexto = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jPanel11 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel8 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        panelUsuarios = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        lblUsuarios = new javax.swing.JLabel();
        lblNuevo = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        panelUs = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(1250, 700));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setBackground(new java.awt.Color(0, 102, 204));
        jLabel1.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 102, 204));
        jLabel1.setText("          MENSAJERIA 3i          ");
        jLabel1.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 102, 204)));
        jPanel3.add(jLabel1);

        jPanel1.add(jPanel3);

        getContentPane().add(jPanel1, java.awt.BorderLayout.PAGE_START);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new java.awt.BorderLayout());

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setLayout(new java.awt.BorderLayout());

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setLayout(new javax.swing.BoxLayout(jPanel7, javax.swing.BoxLayout.LINE_AXIS));

        cLabel1.setBackground(new java.awt.Color(255, 255, 255));
        cLabel1.setBorder(null);
        cLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/usuario.png"))); // NOI18N
        cLabel1.setText("");
        jPanel7.add(cLabel1);

        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("ASD");
        jPanel7.add(jLabel4);

        lblNombreEmpleado.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        lblNombreEmpleado.setText("NOMBRE");
        jPanel7.add(lblNombreEmpleado);

        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("ASD");
        jPanel7.add(jLabel6);

        lblNumeroEmpleado.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        lblNumeroEmpleado.setText("NUMERO DE EMPLEADO");
        jPanel7.add(lblNumeroEmpleado);

        jPanel6.add(jPanel7, java.awt.BorderLayout.PAGE_START);

        panelEnviar.setLayout(new java.awt.BorderLayout());

        jPanel10.setLayout(new java.awt.BorderLayout());

        txtTexto.setBackground(new java.awt.Color(255, 255, 255));
        txtTexto.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        txtTexto.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 1, 0, 1, new java.awt.Color(204, 204, 204)));
        jPanel10.add(txtTexto, java.awt.BorderLayout.CENTER);

        jButton1.setBackground(new java.awt.Color(255, 255, 255));
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ImgAnimacion/send_48.png"))); // NOI18N
        jButton1.setBorder(null);
        jButton1.setBorderPainted(false);
        jButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jButton1.setFocusPainted(false);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel10.add(jButton1, java.awt.BorderLayout.EAST);

        panelEnviar.add(jPanel10, java.awt.BorderLayout.CENTER);

        jPanel11.setBackground(new java.awt.Color(255, 255, 255));
        jPanel11.setLayout(new java.awt.GridLayout(1, 0));
        panelEnviar.add(jPanel11, java.awt.BorderLayout.EAST);

        jPanel6.add(panelEnviar, java.awt.BorderLayout.PAGE_END);

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));
        jPanel8.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(204, 204, 204)));
        jPanel8.setLayout(new javax.swing.BoxLayout(jPanel8, javax.swing.BoxLayout.Y_AXIS));
        jScrollPane1.setViewportView(jPanel8);

        jPanel6.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jPanel2.add(jPanel6, java.awt.BorderLayout.CENTER);

        jScrollPane2.setBorder(null);

        panelUsuarios.setBackground(new java.awt.Color(255, 255, 255));
        panelUsuarios.setLayout(new javax.swing.BoxLayout(panelUsuarios, javax.swing.BoxLayout.Y_AXIS));

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setLayout(new javax.swing.BoxLayout(jPanel5, javax.swing.BoxLayout.Y_AXIS));

        lblUsuarios.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        lblUsuarios.setText("USUARIOS");
        jPanel5.add(lblUsuarios);

        lblNuevo.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        lblNuevo.setForeground(new java.awt.Color(0, 102, 204));
        lblNuevo.setText("NUEVO MENSAJE");
        lblNuevo.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblNuevo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblNuevoMouseClicked(evt);
            }
        });
        jPanel5.add(lblNuevo);

        panelUsuarios.add(jPanel5);

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setLayout(new javax.swing.BoxLayout(jPanel4, javax.swing.BoxLayout.Y_AXIS));
        panelUsuarios.add(jPanel4);

        panelUs.setBackground(new java.awt.Color(255, 255, 255));
        panelUs.setLayout(new javax.swing.BoxLayout(panelUs, javax.swing.BoxLayout.Y_AXIS));
        panelUsuarios.add(panelUs);

        jScrollPane2.setViewportView(panelUsuarios);

        jPanel2.add(jScrollPane2, java.awt.BorderLayout.WEST);

        getContentPane().add(jPanel2, java.awt.BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        String mensaje = this.txtTexto.getText();
        addLabel(mensaje,1);
        int puerto = 0;
        String host = "";
        try{
            Connection con = null;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            String sql = "select * from registroempleados where NumEmpleado like '"+lblNumeroEmpleado.getText()+"'";
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
                puerto = Integer.parseInt(rs.getString("Puerto"));
                host = rs.getString("Ip");
                
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "ERROR: "+e,"ERROR",JOptionPane.ERROR_MESSAGE);
        }
        if(host == null || host.equals("") || puerto == 0){
            JOptionPane.showMessageDialog(this, "NO SE PUDO ENVIAR MENSAJE","ERROR",JOptionPane.WARNING_MESSAGE);
        }else{
        int n1, n2;
        n1 = Integer.parseInt(miNumero);
        n2 = Integer.parseInt(lblNumeroEmpleado.getText());
        String chat;
        if(n1 < n2){
            chat = miNumero + "chat" + lblNumeroEmpleado.getText();
        }else{
            chat = lblNumeroEmpleado.getText() + "chat" + miNumero;
        }
        conversacionDAO.registrarConversacionA(mensaje, chat, miNumero);
        mensaje = miNumero + "-" + mensaje;
        Cliente cliente = new Cliente(puerto, mensaje,host);
        Thread hilo = new Thread(cliente);
        hilo.start();
        jScrollPane1.getVerticalScrollBar().setValue(jScrollPane1.getVerticalScrollBar().getMaximum());
        
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void lblNuevoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblNuevoMouseClicked
        
        String num = JOptionPane.showInputDialog(this, "INGRESA NUMERO DE EMPLEADO CON EL QUE DESEAS HABLAR");
        try{
            Connection con = null;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            String sql = "select * from registroempleados where NumEmpleado like '"+num+"'";
            ResultSet rs = st.executeQuery(sql);
            String n = "";
            String nombre = "";
            while(rs.next()){
                n = rs.getString("NumEmpleado");
                if(n != null){
                nombre = rs.getString("Nombre") + " " + rs.getString("Apellido");
                lblNombreEmpleado.setText(nombre);
                }
            }
            if(n == null || n.equals("")){
                JOptionPane.showMessageDialog(this, "ESTE USUARIO NO EXISTE","ADVERTENCIA",JOptionPane.WARNING_MESSAGE);
            }else{
                int n1 = Integer.parseInt(miNumero);
                int n2 = Integer.parseInt(num);
                String chat;
                String menor,mayor;
                if(n1 < n2){
                    chat = n1 + "chat" + n2;
                    menor = n1+"";
                    mayor = n2+"";
                }else{
                    chat = n2 + "chat" + n1;
                    mayor = n1+"";
                    menor = n2+"";
                }
                if(tableExist(chat)){
                    verificarUsuario(num);
                    jScrollPane1.getVerticalScrollBar().setValue(jScrollPane1.getVerticalScrollBar().getMaximum());
                }else{
                    String crear = "CREATE TABLE `chat`.`"+chat+"` (\n" +
                    "  `Id` INT NOT NULL AUTO_INCREMENT,\n" +
                    "  `"+menor+"` VARCHAR(500) NULL,\n" +
                    "  `"+mayor+"` VARCHAR(500) NULL,\n" +
                    "  PRIMARY KEY (`Id`));";
                    Connection con2 = null;
                    ConexionChat c = new ConexionChat();
                    con2 = c.getConnection();
                    PreparedStatement pst = con2.prepareStatement(crear);
                    pst.execute();
                        String add = "insert into chat"+miNumero+" (Contactos, Mostrar) values(?,?)";
                        PreparedStatement pst2 = con2.prepareStatement(add);
                        pst2.setString(1, n);
                        pst2.setString(2, "SI");
                        pst2.execute();
                    if(tableExist("chat"+n) == false){
                    String crear2 = "CREATE TABLE `chat`.`chat"+miNumero+"` (\n" +
                    "  `Id` INT NOT NULL AUTO_INCREMENT,\n" +
                    "  `Contactos` VARCHAR(300) NULL,\n" +
                    "  `Mostrar` VARCHAR(300) NULL,\n" +
                    "  PRIMARY KEY (`Id`));";
                    PreparedStatement pst3 = con2.prepareStatement(crear2);
                    pst3.execute();
                    
                    
                        String add5 = "insert into chat"+miNumero+" (Contactos, Mostrar) values(?,?)";
                        PreparedStatement pst5 = con2.prepareStatement(add5);
                        pst5.setString(1, n);
                        pst5.setString(2, "SI");
                        pst5.execute();
                    }
                    limpiarUsuarios();
                    mostrarContactos();
                }
            }
            
            
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "ERROR: "+e,"ERROR",JOptionPane.ERROR_MESSAGE);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Mensajes.class.getName()).log(Level.SEVERE, null, ex);
        }
        verificarUsuario(num);
        jScrollPane1.getVerticalScrollBar().setValue(jScrollPane1.getVerticalScrollBar().getMaximum());
    }//GEN-LAST:event_lblNuevoMouseClicked

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
            java.util.logging.Logger.getLogger(Mensajes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Mensajes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Mensajes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Mensajes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Mensajes("").setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.bolivia.label.CLabel cLabel1;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblNombreEmpleado;
    private javax.swing.JLabel lblNuevo;
    private javax.swing.JLabel lblNumeroEmpleado;
    private javax.swing.JLabel lblUsuarios;
    private javax.swing.JPanel panelEnviar;
    private javax.swing.JPanel panelUs;
    private javax.swing.JPanel panelUsuarios;
    private javax.swing.JTextField txtTexto;
    // End of variables declaration//GEN-END:variables

    @Override
    public void update(Observable o, Object arg) {
        String mensaje = (String)arg;
        String buscar = "-";
        int aux = 0;
        String nombre = "";
        boolean band = true;
                char arreglo[] = mensaje.toCharArray();
                for (int j = 0; j < mensaje.length(); j++) {
                    if(band == true){
                    String letra = String.valueOf(arreglo[j]);
                        if (buscar.equalsIgnoreCase(letra)) {
                            aux = j;
                            nombre = mensaje.substring(0,j);
                            band = false;
                        }
                    }
                }
            Clip clip;
        try {
            clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(new File("/Gif/notificacion.wav")));
                        clip.start();
        } catch (LineUnavailableException | UnsupportedAudioFileException | IOException ex) {
            Logger.getLogger(Mensajes.class.getName()).log(Level.SEVERE, null, ex);
        }
                        
        if(lblNumeroEmpleado.getText().equals(mensaje.substring(0,aux))){
            addLabel(mensaje.substring(aux+1,mensaje.length()),2);
            jScrollPane1.getVerticalScrollBar().setValue(jScrollPane1.getVerticalScrollBar().getMaximum());
        } else{
            for (int i = 0; i < cont; i++) {
                if(mensaje.substring(0,aux).equals(usuarios[i])){
                    btnUsuarios[i].setText("<html><p style='padding: 5px; border-left: 10px solid  red ; width:120px; margin: 2px; background-color: rgb(222,222,222);'>"+nombre+"</p></html>");
                }
            }
        }
        
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for (int i = 0; i < cont; i++) {
            if(e.getSource() == btnUsuarios[i]){
                    limpiarConversacion();
                    verificarUsuario(usuarios[i]);
                    if(btnUsuarios[i].getText().substring(0,60).equals("<html><p style='padding: 5px; border-left: 10px solid  red ;")){
                        btnUsuarios[i].setText("<html><p style='padding: 5px; width:120px; margin: 2px; background-color: rgb(222,222,222);'>"+usuarios[i]+"</p></html>");
                    }
                    jScrollPane1.getVerticalScrollBar().setValue(jScrollPane1.getVerticalScrollBar().getMaximum());
            }
        }
    }
}
