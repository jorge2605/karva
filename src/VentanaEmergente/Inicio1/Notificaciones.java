package VentanaEmergente.Inicio1;
import Conexiones.ConexionChat;
import java.awt.Color;
import pruebas.Inicio1;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import pruebas.Aprobacion;
import pruebas.VerRequisiciones;
import scrollPane.ScrollBarCustom;

public class Notificaciones extends javax.swing.JFrame implements ActionListener{
    
    Inicio1 inicio;
    String numero, nombre;
    JButton botones[];
    JPanel panel[];
    int cont, num;
    String selec[], depa[];
    String texto[];
    String numEmpleado;
    
    public void agregarBotones(int con, String titulo, String texto, String fecha,String visto){
        panel[cont] = new JPanel();
        panel[cont].setBackground(new java.awt.Color(255, 255, 255));
        botones[cont] = new JButton();
        botones[cont].setBackground(new java.awt.Color(204, 204, 204));
        String rgb;
        String bol;
        String borde;
        if(visto == null){
            rgb = "rgb(255, 230, 198)";
            bol = "bold";
            borde = "border-left: 10px solid  orange ;";
//            botones[cont].setBorder(javax.swing.BorderFactory.createMatteBorder(0, 5, 0, 0, new java.awt.Color(255,102,0)));
        }else{
            rgb = "rgb(255,255,255)";
            bol = "normal";
            botones[cont].setBorder(null);
            borde = "";
        }
        botones[cont].setBorderPainted(false);
        botones[cont].setContentAreaFilled(false);
        botones[cont].setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        botones[cont].setFocusPainted(false);
        botones[cont].setText(
                     "<html>"
                   + "<div style = '"+borde+" width:200px; background-color: "+rgb+" ; padding: 10px; font-weight:"+bol+";'>"
                   + "<b><style= 'text-align: center; font-weight:"+bol+"; font-size: 14; color: rgb(255, 102, 0);'><p>"+titulo+"</p></font></b>"
                   + "<font style = 'font-size: 10; font-weight:"+bol+"; text-align: left;'><p>"+texto+"</p></font>"
                   + "<font style= 'font-size:8; font-weight:"+bol+";'><p>"+fecha+"</p></font>"
                   + "</div>"
                   + "</html>");
        botones[cont].addActionListener(this);
        panel[cont].add(botones[cont]);
        jPanel3.add(panel[cont]);
    }
    
    public void verNotificaciones(){
        try{
            botones = new JButton[100];
            panel = new JPanel[100];
            depa = new String[100];
            selec = new String[100];
            texto = new String[100];
            Connection con = null;
            ConexionChat con1 = new ConexionChat();
            con = con1.getConnection();
            Statement st = con.createStatement();
            String not = "noti"+numero;
            String cont = "select * from chat."+not;
            Statement st1 = con.createStatement();
            ResultSet rs1 = st1.executeQuery(cont);
            int inicio = 0;
            int fin = 0;
            while(rs1.next()){
                fin++;
            }
            inicio = fin - 10;
            String sql = "select * from "+not+" order by Id desc limit "+0+","+15;
            ResultSet rs = st.executeQuery(sql);
            String datos[] = new String[10];
            int co = -1;
            this.cont = -1;
            while(rs.next()){
                co++;
                this.cont++;
                datos[0] = rs.getString("Titulo");
                datos[1] = rs.getString("Texto");
                datos[2] = rs.getString("Fecha");
                datos[3] = rs.getString("Visto");
                datos[4] = rs.getString("Id");
                datos[5] = rs.getString("Departamento");
                depa[this.cont] = datos[5];
                selec[this.cont] = datos[4];
                texto[this.cont] = datos[1];
                agregarBotones(co, datos[0],datos[1],datos[2],datos[3]);
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "ERROR: "+e,"ERROR",JOptionPane.ERROR_MESSAGE);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Notificaciones.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public Notificaciones(Inicio1 i,String num, String nom, String numEmpleado) {
        initComponents();
        this.numEmpleado = numEmpleado;
        inicio = i;
        numero = num;
        nombre = nom;
        verNotificaciones();
        Color color = new Color(255,102,0);
        scroll1.setVerticalScrollBar(new ScrollBarCustom(color));
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        scroll1 = new javax.swing.JScrollPane();
        jPanel3 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setPreferredSize(new java.awt.Dimension(323, 500));
        addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                formFocusLost(evt);
            }
        });
        addWindowFocusListener(new java.awt.event.WindowFocusListener() {
            public void windowGainedFocus(java.awt.event.WindowEvent evt) {
            }
            public void windowLostFocus(java.awt.event.WindowEvent evt) {
                formWindowLostFocus(evt);
            }
        });
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(204, 204, 204)));
        jPanel1.setLayout(new java.awt.BorderLayout());

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setBackground(new java.awt.Color(0, 102, 204));
        jLabel1.setFont(new java.awt.Font("Roboto", 1, 22)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 102, 0));
        jLabel1.setText("        NOTIFICACIONES        ");
        jLabel1.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(255, 102, 0)));
        jPanel2.add(jLabel1);

        jPanel1.add(jPanel2, java.awt.BorderLayout.PAGE_START);

        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("JORGE SANTACRUZ");
        jPanel1.add(jLabel3, java.awt.BorderLayout.PAGE_END);

        scroll1.setBorder(null);
        scroll1.setForeground(new java.awt.Color(255, 102, 0));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setLayout(new javax.swing.BoxLayout(jPanel3, javax.swing.BoxLayout.Y_AXIS));
        scroll1.setViewportView(jPanel3);

        jPanel1.add(scroll1, java.awt.BorderLayout.CENTER);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 323, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 521, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_formFocusLost
        dispose();
    }//GEN-LAST:event_formFocusLost

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        dispose();
    }//GEN-LAST:event_formWindowClosed

    private void formWindowLostFocus(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowLostFocus
        dispose();
    }//GEN-LAST:event_formWindowLostFocus

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
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Notificaciones.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Notificaciones.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Notificaciones.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Notificaciones.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Notificaciones(null,"","","").setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane scroll1;
    // End of variables declaration//GEN-END:variables

    @Override
    public void actionPerformed(ActionEvent e) {
        for (int i = 0; i < cont+1; i++) {
            if(e.getSource() == botones[i]){
                try{
                    Connection con = null;
                    ConexionChat con1 = new ConexionChat();
                    con = con1.getConnection();
                    Statement st = con.createStatement();
                    String not = "noti"+numero;
                    String sql = "update "+not+" set Visto = ?, Visto2 = ? where Id like ?";
                    PreparedStatement pst = con.prepareStatement(sql);
                    
                    pst.setString(1, "SI");
                    pst.setString(2, "SI");
                    pst.setString(3, selec[i]);
                    int n = pst.executeUpdate();
                }catch(SQLException ex){
                    JOptionPane.showMessageDialog(this, "ERROR: "+ex,"ERROR",JOptionPane.ERROR_MESSAGE);
                    System.out.println(ex);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(Notificaciones.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                switch (depa[i]) {
                    case "1":
                        {
                            Aprobacion c = new Aprobacion(this.numEmpleado);
                            inicio.jDesktopPane1.add(c);
                            c.toFront();
                            c.setLocation(inicio.jDesktopPane1.getWidth() / 2 - c.getWidth() / 2, inicio.jDesktopPane1.getHeight() / 2 - c.getHeight() / 2);
                            try{
                                c.setMaximum(true);
                            }catch(PropertyVetoException ex){
                                Logger.getLogger(Inicio1.class.getName()).log(Level.SEVERE,null,ex);
                            }       c.setVisible(true);
                            break;
                        }
                    case "2":
                        {
                            VerRequisiciones c = new VerRequisiciones(numero);
                            inicio.jDesktopPane1.add(c);
                            c.toFront();
                            c.setLocation(inicio.jDesktopPane1.getWidth() / 2 - c.getWidth() / 2, inicio.jDesktopPane1.getHeight() / 2 - c.getHeight() / 2);
                            try {
                                c.setMaximum(true);
                            } catch (PropertyVetoException ex) {
                                Logger.getLogger(Notificaciones.class.getName()).log(Level.SEVERE, null, ex);
                            }       c.seleccionarTabla();
                            String buscar = "*";
                            int aux = 0, aux2 = 0;
                            String nombre = "";
                            boolean band = true;
                            char arreglo[] = texto[i].toCharArray();
                            for (int j = 0; j < texto[i].length(); j++) {
                                if(band == true){
                                    String letra = String.valueOf(arreglo[j]);
                                    if (buscar.equalsIgnoreCase(letra)) {
                                        if(aux == 0){
                                            aux = j;
                                        }else if(aux2 == 0){
                                            aux2 = j;
                                            nombre = texto[i].substring(aux+1,aux2);
                                            band = false;
                                        }
                                    }
                                }
                            }
                            c.seleccionarRequisicion(nombre);
                            c.setVisible(true);
                            break;
                        }
                    case "3":
                        {
                            String buscar = "*";
                            int aux = 0, aux2 = 0;
                            String nombre = "";
                            boolean band = true;
                            char arreglo[] = texto[i].toCharArray();
                            for (int j = 0; j < texto[i].length(); j++) {
                                if(band == true){
                                    String letra = String.valueOf(arreglo[j]);
                                    if (buscar.equalsIgnoreCase(letra)) {
                                        if(aux == 0){
                                            aux = j;
                                        }else if(aux2 == 0){
                                            aux2 = j;
                                            nombre = texto[i].substring(aux+1,aux2);
                                            band = false;
                                        }
                                    }
                                }
                            }
                            verFolio c = new verFolio(this.inicio,true,nombre);
                            c.setVisible(true);
                            break;
                        }
                    default:
                        break;
                }
            }
        }
    }
}
