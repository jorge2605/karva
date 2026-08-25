package VentanaEmergente.Compras;

import Conexiones.Conexion;
import Modelo.javamail;
import com.mxrck.autocompleter.TextAutoCompleter;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Stack;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;

public class enviarCorreo extends javax.swing.JDialog implements ActionListener, MouseListener{

    public JButton partes[];
    public JPanel panel[];
    public JLabel label[];
    public String id[];
    public String numRequi;
    TextAutoCompleter ac;
    seleccionarProveedor s;
    int selec;
    Stack<String> pila;
    public JTable tabla;
    public String correo;
    public String correoCopia;
    public String contra;
    int sel = 0;
    public String transaccion;
    public String ordenReal;
    public String proyecto;
    public boolean BD;
    public boolean modalidad;
    
    public String getProveedor(String prov){
        String correoProv =  null;
        try{
            Connection con;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            String sql = "select * from registroprov_compras where Nombre like '"+prov+"'";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            correoCopia = "";
            while(rs.next()){
                correoProv = rs.getString("Correo");
                correoCopia = rs.getString("CorreoCopia");
            }
            if(correoCopia == null){
                correoCopia = "";
            } else {
                correoCopia = "," + correoCopia;
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "Error: "+e,"error",JOptionPane.ERROR_MESSAGE);
        }
        return correoProv;
    }
    
    public void BD(){
        pila = new Stack<>();
        try{
            Connection con;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            String sql = "select * from registroprov_compras";
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
                pila.push(rs.getString("Nombre"));
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "ERROR: "+e,"ERROR",JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public JButton setBonito(JButton boton, JPanel panel, String texto, JLabel label, String prov){
        boton.setBackground(new java.awt.Color(255, 255, 255));
        boton.setFont(new java.awt.Font("Roboto", 1, 12));
        boton.setText("<html>"
                + "<p style='width: 160px; text-align:center; padding: 0 15px;'>"+texto+""
                + "</html>");
        boton.setBorder(null);
        boton.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        boton.setBorderPainted(false);
        boton.setContentAreaFilled(false);
        boton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        boton.setFocusPainted(false);
        boton.addActionListener(this);
        boton.addMouseListener(this);
        
        panel.setBackground(new Color(255,255,255));
        panel.setLayout(new BorderLayout());
        panel.add(boton, BorderLayout.WEST);
        
        
        String text = prov;
        if(!text.equals("")){
            text += "|";
        }
        label.setText(text);
        label.setFont(new Font("Roboto",Font.BOLD,12));
        label.setForeground(Color.black);
        label.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        label.addMouseListener(this);
        
        panel.add(label, BorderLayout.CENTER);
        return boton;
    }
    
    public void llenarBotones(Stack<String> pila, Stack<String> pilaProv, Stack<String> id){
        partes = new JButton[pila.size()];
        panel = new JPanel[pila.size()];
        label  = new JLabel[pila.size()];
        this.id = new String[pila.size()];
        
        for (int i = 0; i < pila.size(); i++) {
            partes[i] = new JButton();
            panel[i] = new JPanel();
            label[i] = new JLabel();
            this.id[i] = id.get(i);
            
            setBonito(partes[i], panel[i], pila.get(i),label[i], pilaProv.get(i));
            panelPartes.add(panel[i]);
        }
        
        for (int i = pila.size(); i < 8; i++) {
            JPanel p = new JPanel();
            p.setBackground(Color.white);
            p.setLayout(new BorderLayout());
            p.add(new JLabel(" "));
            panelPartes.add(p);
        }
    }
    
    public Stack<String> setear(String text){
        Stack<String> pila = new Stack<>();
        String buscar = "|";
        int aux = 0;
        int aux2;
        char arreglo[] = text.toCharArray();
        for (int j = 0; j < text.length(); j++) {
            String letra = String.valueOf(arreglo[j]);
            if (buscar.equalsIgnoreCase(letra)) {
                aux2 = j;
                String ad = (text.substring(aux,aux2));
                aux = j+1;
                pila.push(ad);
            }
        }
        return pila;
    }
        
    public String mostrarDialogoEmergente(String proveedor, Stack<String> material) {
        JTextArea textField = new JTextArea();
        textField.setBackground(new java.awt.Color(255, 255, 255));
        textField.setColumns(20);
        textField.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        textField.setLineWrap(true);
        textField.setRows(5);
        textField.setWrapStyleWord(true);
        textField.setBorder(null);
        JScrollPane scroll = new JScrollPane();
        scroll.setBorder(null);
        scroll.setViewportView(textField);

        String mat = "";
        try{
            Connection con;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            for (int i = 0; i < material.size(); i++) {
                Statement st = con.createStatement();
                String sql = "select * from requisiciones where Id like '"+material.get(i)+"'";
                if(BD){
                    sql = "select * from requisicionesmuestra where Id like '"+material.get(i)+"'";
                }
                ResultSet rs = st.executeQuery(sql);
                String mate = null;
                while(rs.next()){
                    mate = rs.getString("Codigo");
                }
                mat += "-"+mate + "\n";
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "ERROR EN DIALOGO EMERGENTE: "+e,"ERROR",JOptionPane.ERROR_MESSAGE);
        }
        Object[] mensaje = {"Ingrese Notas:\n"
                + mat, scroll};
        ImageIcon icono = new ImageIcon(getClass().getResource("/Iconos/publicalo.png"));
        int opcion = JOptionPane.showOptionDialog(
                null,
                mensaje,
                "Ingrese notas para " + proveedor,
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                icono,
                null,
                null
        );if (opcion == JOptionPane.OK_OPTION) {
            return textField.getText();
        } else {
            return null;
        }
    }
    
    public void agregarTitulos(){
        Font font = new Font("Roboto",Font.PLAIN,16);
        JLabel izq = new JLabel("<html>"
                + "<p style='width: 160px; text-align:center; padding: 0 15px; color: rgb(0,102,204)'><b>Numero De Parte</b>"
                + "</html>");
        JLabel der = new JLabel("<html>"
                + "<p style='text-align:center; padding: 0 15px; color: rgb(0,102,204)'><b>Proveedor</b>"
                + "</html>");
        izq.setFont(font);
        der.setFont(font);
        panelTitulos.setLayout(new BorderLayout());
        panelTitulos.add(izq, BorderLayout.WEST);
        panelTitulos.add(der);
    }
    
    public void clicAgregar(){
        boolean band = true;
        for (JPanel panel1 : panel) {
            if (panel1.getBackground().equals(Color.green)) {
                band = false;
            }
        }
        if(band){
            //ES PARA SELECCIOANR TODOS LOS LABEL
            for (int i = 0; i < label.length; i++) {
                selec = i;
                boolean fa = false;
                for (int j = 0; j < pila.size(); j++) {
                    if(s.txtProveedor.getText().equals(pila.get(j))){
                        fa = true;
                    }
                }
                if(fa){
                    label[i].setText(label[i].getText() + s.txtProveedor.getText() + "|");
                    sel = 0;
                    s.dispose();
                }else{
                    JOptionPane.showMessageDialog(this, "El proveedor es incorrecto","Advertencia",JOptionPane.WARNING_MESSAGE);
                    s.txtProveedor.setText("");
                }
            }
            limpiarPanel();
        }else{
            //ES SOOLO EL LABEL SELECCIONADO
            for (int i = 0; i < panel.length; i++) {
                if(panel[i].getBackground().equals(Color.green)){
                    boolean f = false;
                    for (int j = 0; j < pila.size(); j++) {
                        if(s.txtProveedor.getText().equals(pila.get(j))){
                            f = true;
                        }
                    }
                    if(f){
                        label[i].setText(label[i].getText() + s.txtProveedor.getText() + "|");
                        s.dispose();
                    }else{
                        JOptionPane.showMessageDialog(this, "El proveedor es incorrecto","Advertencia",JOptionPane.WARNING_MESSAGE);
                        s.txtProveedor.setText("");
                    }
                }
            }
            limpiarPanel();
        }
    }
    
    public void limpiarPanel(){
        for (JPanel panel1 : panel) {
            panel1.setBackground(Color.white);
        }
    }
    
    public String getRequisitor(String orden){
        try {
            Connection con;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            String sql = "select * from requisiciones where oc like '" + orden + "'";
            ResultSet rs = st.executeQuery(sql);
            String requi = "";
            while (rs.next()) {
                requi = rs.getString("NumRequisicion");
            }
            String sql2 = "select * from requisicion where id = '" + requi +"'";
            Statement st2 = con.createStatement();
            ResultSet rs2 = st2.executeQuery(sql2);
            String numEmpleado = "";
            while (rs2.next()) {
                numEmpleado = rs2.getString("NumeroEmpleado");
            }
            String sql3 = "select * from registroempleados where NumEmpleado like '" + numEmpleado + "'";
            Statement st3 = con.createStatement();
            ResultSet rs3 = st3.executeQuery(sql3);
            while (rs3.next()) {
                String co = rs3.getString("Correo");
                return (co == null) ? "" : "," + co;
            }
        } catch(SQLException e) {
            JOptionPane.showMessageDialog(this, "Error: " + e, "Error", JOptionPane.ERROR_MESSAGE);
        }
        return "";
    }
    
    public enviarCorreo(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        jScrollPane1.getViewport().setBackground(new Color(255,255,255));
        jScrollPane1.getVerticalScrollBar().setUnitIncrement(15);
        agregarTitulos();
        BD();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        panelPartes = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        btnEnviar = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        panelTitulos = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        btnAgregar = new javax.swing.JButton();
        btnAgregar1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(850, 500));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new java.awt.BorderLayout());

        jScrollPane1.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane1.setBorder(null);

        panelPartes.setBackground(new java.awt.Color(153, 204, 255));
        panelPartes.setLayout(new javax.swing.BoxLayout(panelPartes, javax.swing.BoxLayout.Y_AXIS));
        jScrollPane1.setViewportView(panelPartes);

        jPanel1.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        btnEnviar.setBackground(new java.awt.Color(255, 255, 255));
        btnEnviar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/sobre.png"))); // NOI18N
        btnEnviar.setBorder(null);
        btnEnviar.setBorderPainted(false);
        btnEnviar.setContentAreaFilled(false);
        btnEnviar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEnviar.setFocusPainted(false);
        btnEnviar.setPreferredSize(new java.awt.Dimension(50, 32));
        btnEnviar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEnviarActionPerformed(evt);
            }
        });
        jPanel2.add(btnEnviar);

        jPanel1.add(jPanel2, java.awt.BorderLayout.SOUTH);

        jPanel3.setLayout(new java.awt.BorderLayout());

        panelTitulos.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.add(panelTitulos, java.awt.BorderLayout.PAGE_START);

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 20, 5));

        btnAgregar.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        btnAgregar.setForeground(new java.awt.Color(0, 0, 0));
        btnAgregar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/mas.png"))); // NOI18N
        btnAgregar.setBorder(null);
        btnAgregar.setBorderPainted(false);
        btnAgregar.setContentAreaFilled(false);
        btnAgregar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAgregar.setFocusPainted(false);
        btnAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarActionPerformed(evt);
            }
        });
        jPanel4.add(btnAgregar);

        btnAgregar1.setFont(new java.awt.Font("Roboto", 1, 26)); // NOI18N
        btnAgregar1.setForeground(new java.awt.Color(255, 255, 255));
        btnAgregar1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/notificacion1.png"))); // NOI18N
        btnAgregar1.setText("-");
        btnAgregar1.setBorder(null);
        btnAgregar1.setBorderPainted(false);
        btnAgregar1.setContentAreaFilled(false);
        btnAgregar1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAgregar1.setFocusPainted(false);
        btnAgregar1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnAgregar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregar1ActionPerformed(evt);
            }
        });
        jPanel4.add(btnAgregar1);

        jPanel3.add(jPanel4, java.awt.BorderLayout.CENTER);

        jPanel1.add(jPanel3, java.awt.BorderLayout.NORTH);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnEnviarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEnviarActionPerformed
        Stack<String> pil = new Stack<>();
        Stack<String> get;
        for (int i = 0; i < label.length; i++) {
            get = (setear(label[i].getText()));
            for (int k = 0; k < get.size(); k++) {
                boolean esta = true;
                String prov = get.get(k);
                for (int j = 0; j < pil.size(); j++) {
                    if(pil.get(j).equals(prov)){
                        esta = false;
                    }
                }
                if(esta){
                    pil.push(prov);
                }
            }
        }
        for (int i = 0; i < pil.size(); i++) {
            Stack<String> material = new Stack<>();
            for (int j = 0; j < label.length; j++) {
                Stack<String> lbl = setear(label[j].getText());
                for (int k = 0; k < lbl.size(); k++) {
                    if(lbl.get(k).equals(pil.get(i))){
                        material.push(id[j]);
                    }
                }
            }
            String proveedor = getProveedor(pil.get(i));
            if(proveedor == null){
                JFrame j = (JFrame) JOptionPane.getFrameForComponent(this);
                VentanaEmergente.Compras.addProveedor a = new VentanaEmergente.Compras.addProveedor(j,true);
                a.txtProv.setText(pil.get(i));
                a.txtProv.setEnabled(false);
                a.verProveedor();
                a.btnGuardar.setText("Actualizar");
                a.setVisible(true);
            }
            javamail mail = new javamail();
            mail.nuevamod = modalidad;
            String add;
            try{
                add = "/ "+proyecto.substring(0,3);
            }catch(Exception e){
                add = "";
            }
            String asunto = "Solicitud de cotizacion Requisicion: "+numRequi + add;
            if(transaccion.equals("orden")){
                asunto = "Orden de compra: "+ordenReal;
            }
            String nota  = mostrarDialogoEmergente(pil.get(i), material);
            try{
                if(transaccion.equals("orden")){
//                    String ruta = numRequi + ".pdf";
                    String ruta = "\\\\192.168.100.40\\bd\\OC\\Orden_de_compra\\"+numRequi+".pdf";
                    mail.sendOC(nota, asunto, getProveedor(pil.get(i)), correo, contra, ruta,ordenReal,correoCopia + getRequisitor(ordenReal));
                }else{
                    mail.jorge(material, nota, tabla,asunto,getProveedor(pil.get(i)),correo,contra, correoCopia);
                }
            }catch(Exception e){
                JOptionPane.showMessageDialog(this, "No se envio el correo a "+ pil.get(i) + e,"Error",JOptionPane.ERROR_MESSAGE);
                java.util.logging.Logger.getLogger(enviarCorreo.class.getName()).log(java.util.logging.Level.SEVERE, null, e);
            }
        }
        
    }//GEN-LAST:event_btnEnviarActionPerformed

    private void btnAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarActionPerformed
        sel = 1;
        JFrame f = (JFrame) JOptionPane.getFrameForComponent(this);
        s = new seleccionarProveedor(f, true);
        ac = new TextAutoCompleter(s.txtProveedor);
        for (int j = 0; j < pila.size(); j++) {
            ac.addItem(pila.get(j));
        }
        s.setLocationRelativeTo(null);
        s.txtProveedor.addActionListener(this);
        s.setVisible(true);
    }//GEN-LAST:event_btnAgregarActionPerformed

    private void btnAgregar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregar1ActionPerformed
        int opc = JOptionPane.showConfirmDialog(this, "Estas seguro de eliminar proveedores?");
        if(opc == 0){
            boolean band = true;
            for (JPanel panel1 : panel) {
                if (panel1.getBackground().equals(Color.green)) {
                    band = false;
                }
            }
            if(band){
                for (JLabel label1 : label) {
                    label1.setText("");
                }
            }else{
                for (int i = 0; i < panel.length; i++) {
                    if(panel[i].getBackground().equals(Color.green)){
                        label[i].setText("");
                    }
                }
            }
            sel = 1;
            limpiarPanel();
        }
    }//GEN-LAST:event_btnAgregar1ActionPerformed

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
            java.util.logging.Logger.getLogger(enviarCorreo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(enviarCorreo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(enviarCorreo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(enviarCorreo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                enviarCorreo dialog = new enviarCorreo(new javax.swing.JFrame(), true);
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
    public javax.swing.JButton btnAgregar;
    public javax.swing.JButton btnAgregar1;
    private javax.swing.JButton btnEnviar;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel panelPartes;
    private javax.swing.JPanel panelTitulos;
    // End of variables declaration//GEN-END:variables

    @Override
    public void actionPerformed(ActionEvent e) {
        if(s != null){
            if(e.getSource() == s.txtProveedor){
                clicAgregar();
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        for (int i = 0; i < partes.length; i++) {
//            if(e.getSource() == partes[i]){
//                if(!transaccion.equals("orden")){
//                    selec = i;
//                    JFrame f = (JFrame) JOptionPane.getFrameForComponent(this);
//                    s = new seleccionarProveedor(f, true);
//                    ac = new TextAutoCompleter(s.txtProveedor);
//                    for (int j = 0; j < pila.size(); j++) {
//                        ac.addItem(pila.get(j));
//                    }
//                    s.setLocation(e.getLocationOnScreen().x - 10, e.getLocationOnScreen().y + 10);
//                    s.txtProveedor.addActionListener(this);
//                    s.setVisible(true);
//                }
//            }
            if(e.getSource() == label[i]){
                if(!transaccion.equals("orden")){
                    int opc = JOptionPane.showConfirmDialog(this, "¿Estas seguro de borrar proveedores?");
                    if(opc == 0){
                        label[i].setText("");
                    }
                }
            }
            if(e.getSource() == partes[i]){
                if(panel[i].getBackground().equals(Color.green)){
                    panel[i].setBackground(Color.white);
                }else{
                    panel[i].setBackground(Color.green);
                }
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        for (int i = 0; i < partes.length; i++) {
            if (e.getSource() == partes[i]) {
                if (panel[i].getBackground().equals(new Color(255,255,255))) {
                    panel[i].setBackground(new Color(235,235,235));
                }
            }
            if (e.getSource() == label[i]) {
                if (label[i].getForeground().equals(Color.black)) {
                    label[i].setForeground(Color.red);
                }
            }
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        for (int i = 0; i < partes.length; i++) {
            if (e.getSource() == partes[i]) {
                if (panel[i].getBackground().equals(new Color(235,235,235))) {
                    panel[i].setBackground(new Color(255,255,255));
                }
            }
            if (e.getSource() == label[i]) {
                if (label[i].getForeground().equals(Color.red)) {
                    label[i].setForeground(Color.black);
                }
            }
        }
    }
}
