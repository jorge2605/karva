package VentanaEmergente.EntregaRequisicion;

import Conexiones.Conexion;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import pruebas.Inicio1;

public class verPedidos extends javax.swing.JDialog implements ActionListener,MouseListener{

    JButton[] btnPedido;
    JButton[] btnArticulos;
    JPanel[] panelPedido;
    JPanel[] panelArticulo;
    JPanel[] panelSeccion;
    JLabel relleno;
    int contBtnP = 0;
    String requi[];
    
    public void limpiarArti(){
        panelArti.removeAll();
        revalidate();
        repaint();
    }
    
    public void paneles(String requisicion){
        limpiarArti();
        try{
        Connection con = null;
        Conexion con1 = new Conexion();
        con = con1.getConnection();
        
        int cont1 = 0;
                Statement st = con.createStatement();
                String sql = "select Id from detrequisicion where IdPedido like '"+requisicion+"'";
                ResultSet rs = st.executeQuery(sql);

                while(rs.next()){
                    cont1++;
                }
            
        JButton botones[] = new JButton[cont1];
        JPanel pseccion[] = new JPanel[cont1];
        JPanel panel[] = new JPanel[cont1];
        String parte[] = new String[cont1];
        String cantidad[] = new String[cont1];
        String id[] = new String[cont1];
        String folio[] = new String[cont1];
        
        int cont = 0;
       
            int col = 0;
            int i = 0;
                String sql2 = "select * from detrequisicion where IdPedido like "+requisicion+"";
                Statement st2 = con.createStatement();
                ResultSet rs2 = st2.executeQuery(sql2);
                boolean ban = true;
                int ac = 0;
                double ancho = (this.getWidth() - panelArti.getWidth()) / 3 ;
                JLabel l = new javax.swing.JLabel();
                    l.setText("    ");
                while(rs2.next()){
                    
                    if(ac%5 == 0 || ban == true)
                    {
                     pseccion[cont] = new JPanel(new FlowLayout()); 
                     pseccion[cont].setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 10, 5));
                     pseccion[cont].setBackground(Color.white);
                     cont++;
                    }
                    String opc = rs2.getString("Cantidad");
                    
                    id[i] = rs2.getString("IdArticulo");
                    
                    String sql3 = "select * from requisiciones where Id like '"+id[i]+"'";
                    Statement st3 = con.createStatement();
                    ResultSet rs3 = st3.executeQuery(sql3);
                    String dat[] = new String[10];
                    while(rs3.next()){
                        dat[0] = rs3.getString("Codigo");
                        dat[1] = rs3.getString("Descripcion");
                        dat[2] = rs3.getString("Proyecto");
                        dat[3] = rs3.getString("Ubicacion");
                    }
                    
                    cantidad[i] = rs2.getString("Cantidad");
                    botones[i]= new JButton(
                              "<html>"
                            + "<div style='width: "+ancho+"px;'>"
                            + "<p style='font-size:14px;  text-align: center; font-weight: 700;'>"+dat[0]+"</p>"
                            + "<p style='font-size:10px;'>"+dat[1]+"</p>"
                            + "<P style='font-size:8px;'>"+opc+"</P>"
                            + "<P style='font-size:10px; font-weight: 700;'>"+dat[2]+"</P>"
                            + "<P style='font-size:10px; font-weight: 700;'>"+dat[3]+"</P>"
                            + "</div>"
                            + "</html>");
                    botones[i].setBackground(new java.awt.Color(255, 255, 255));
                    botones[i].setFont(new java.awt.Font("Roboto", 0, 12));
                    botones[i].setBorder(null);
                    botones[i].setBorderPainted(false);
                    botones[i].setContentAreaFilled(false);
                    botones[i].setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
                    botones[i].setFocusPainted(false);
                    botones[i].addActionListener(this);
                    panel[i] = new JPanel();
                    panel[i].setBackground(Color.white);
                    
                    panel[i].add(botones[i]);
                    pseccion[cont-1].add(panel[i]);
                    panelArti.add(pseccion[cont-1]);
                    
                    i++;
                    ac++;
                    ban = false;
                    }
        panelArti.add(l);
        col++;
        
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "ERROR: "+e,"ERROR",JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(verPedidos.class.getName()).log(Level.SEVERE,null,e);
        }
    }
    
    public String addColor(String texto, String color){
        
        String textoF;
        
        String ti, tf;
        
        ti = texto.substring(0, 54);
        tf = texto.substring(65, texto.length());
        
        textoF = ti + color + tf;
        return textoF;
    }
    
    public void addBotonesPedido(String numEmpleado){
        try{
            Connection con;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            String sql = "select * from pedrequisicion where NumEmpleado like '"+numEmpleado+"'";
            ResultSet rs = st.executeQuery(sql);
            String datos[] = new String[10];
            String sql2 = "select * from pedRequisicion where NumEmpleado like '"+numEmpleado+"'";
            Statement st2 = con.createStatement();
            ResultSet rs2 = st2.executeQuery(sql2);
            int cont = 0;
            while(rs2.next()){
                cont++;
            }
            
            btnPedido = new JButton[cont];
            panelPedido = new JPanel[cont];
            requi = new String[cont];
            
            contBtnP = 0;
            while(rs.next()){
                datos[0] = rs.getString("Id");
                requi[contBtnP] = datos[0];
                datos[1] = rs.getString("Fecha");
                datos[2] = rs.getString("Estado");
                panelPedido[contBtnP] = new JPanel();
                panelPedido[contBtnP].setBackground(new Color(153,204,255));
                
                btnPedido[contBtnP] = new JButton();
                char c = '"';
                btnPedido[contBtnP]= new JButton();
//                btnPedido[contBtnP].setBackground(new java.awt.Color(123,204,255));
                btnPedido[contBtnP].setFont(new java.awt.Font("Roboto", 0, 12));
                btnPedido[contBtnP].setBorder(null);
                btnPedido[contBtnP].setBorderPainted(false);
                btnPedido[contBtnP].setContentAreaFilled(false);
                btnPedido[contBtnP].setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
                btnPedido[contBtnP].setFocusPainted(false);
                btnPedido[contBtnP].setText(
                              "<html>"
                            + "<div style='width: 220px; background-color: rgb(153,204,255); padding: 5px;'>"
                            + "<p style='font-size:14px;  text-align: center; font-weight: 700;'>"+datos[0]+"</p>"
                            + "<p style='font-size:10px;'>"+datos[2]+"</p>"
                            + "<P style='font-size:8px;'>"+datos[1]+"</P>"
                            + "</div>"
                            + "</html>"
                );
                btnPedido[contBtnP].addActionListener(this);
                btnPedido[contBtnP].addMouseListener(this);
                panelPedido[contBtnP].add(btnPedido[contBtnP]);
                panelPedidos.add(btnPedido[contBtnP]);
                contBtnP++;
                revalidate();
                repaint();
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "ERROR: "+e,"ERROR",JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public verPedidos(java.awt.Frame parent, boolean modal, String numEmpleado) {
        super(parent, modal);
        initComponents();
        addBotonesPedido(numEmpleado);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jPanel13 = new javax.swing.JPanel();
        btnSalir = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        jTextField1 = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        panelPedidos = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        panelArti = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(1258, 676));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new java.awt.BorderLayout());

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new java.awt.BorderLayout());

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jLabel9.setFont(new java.awt.Font("Roboto", 1, 48)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 165, 252));
        jLabel9.setText("ESTADO DE PEDIDOS");
        jPanel3.add(jLabel9);

        jPanel2.add(jPanel3, java.awt.BorderLayout.CENTER);

        jPanel13.setBackground(new java.awt.Color(255, 255, 255));

        btnSalir.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("  X  ");
        jLabel1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel1MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel1MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel1MouseExited(evt);
            }
        });
        btnSalir.add(jLabel1);

        jPanel13.add(btnSalir);

        jPanel2.add(jPanel13, java.awt.BorderLayout.EAST);

        jPanel1.add(jPanel2, java.awt.BorderLayout.PAGE_START);

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setLayout(new java.awt.BorderLayout());

        jPanel5.setBackground(new java.awt.Color(153, 204, 255));

        jPanel6.setBackground(new java.awt.Color(153, 204, 255));

        jLabel2.setBackground(new java.awt.Color(255, 255, 255));
        jLabel2.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("MIS PEDIDOS");
        jPanel6.add(jLabel2);

        jPanel5.add(jPanel6);

        jPanel4.add(jPanel5, java.awt.BorderLayout.PAGE_START);

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setLayout(new java.awt.BorderLayout());

        jPanel8.setBackground(new java.awt.Color(153, 204, 255));
        jPanel8.setPreferredSize(new java.awt.Dimension(300, 567));
        jPanel8.setLayout(new java.awt.BorderLayout(5, 5));

        jPanel10.setBackground(new java.awt.Color(102, 153, 255));
        jPanel10.setLayout(new java.awt.BorderLayout());

        jTextField1.setBackground(new java.awt.Color(102, 153, 255));
        jTextField1.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        jTextField1.setForeground(new java.awt.Color(255, 255, 255));
        jTextField1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField1.setBorder(null);
        jTextField1.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        jPanel10.add(jTextField1, java.awt.BorderLayout.CENTER);

        jLabel3.setFont(new java.awt.Font("Roboto", 1, 16)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("BUSCAR:");
        jPanel10.add(jLabel3, java.awt.BorderLayout.LINE_START);

        jPanel8.add(jPanel10, java.awt.BorderLayout.PAGE_START);

        jScrollPane1.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane1.setBorder(null);

        panelPedidos.setBackground(new java.awt.Color(153, 204, 255));
        panelPedidos.setLayout(new javax.swing.BoxLayout(panelPedidos, javax.swing.BoxLayout.Y_AXIS));
        jScrollPane1.setViewportView(panelPedidos);

        jPanel8.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jPanel7.add(jPanel8, java.awt.BorderLayout.LINE_START);

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));
        jPanel9.setLayout(new java.awt.BorderLayout());

        jPanel11.setBackground(new java.awt.Color(255, 255, 255));

        jLabel4.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel4.setText("ID PEDIDO:");
        jPanel11.add(jLabel4);

        jLabel5.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel5.setText("SIN SELECCIONAR");
        jPanel11.add(jLabel5);

        jPanel9.add(jPanel11, java.awt.BorderLayout.PAGE_START);

        jScrollPane2.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane2.setBorder(null);

        panelArti.setBackground(new java.awt.Color(255, 255, 255));
        panelArti.setLayout(new javax.swing.BoxLayout(panelArti, javax.swing.BoxLayout.Y_AXIS));
        jScrollPane2.setViewportView(panelArti);

        jPanel9.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        jPanel7.add(jPanel9, java.awt.BorderLayout.CENTER);

        jPanel4.add(jPanel7, java.awt.BorderLayout.CENTER);

        jPanel1.add(jPanel4, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseClicked
        dispose();
    }//GEN-LAST:event_jLabel1MouseClicked

    private void jLabel1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseEntered
        btnSalir.setBackground(java.awt.Color.red);
        jLabel1.setForeground(Color.white);
    }//GEN-LAST:event_jLabel1MouseEntered

    private void jLabel1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseExited
        btnSalir.setBackground(java.awt.Color.white);
        jLabel1.setForeground(Color.black);
    }//GEN-LAST:event_jLabel1MouseExited

    public static void main(String args[]) {
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
            java.util.logging.Logger.getLogger(verPedidos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(verPedidos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(verPedidos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(verPedidos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                verPedidos dialog = new verPedidos(new javax.swing.JFrame(), true,"");
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
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JPanel panelArti;
    private javax.swing.JPanel panelPedidos;
    // End of variables declaration//GEN-END:variables

    @Override
    public void actionPerformed(ActionEvent e) {
        for (int i = 0; i < contBtnP; i++) {
            if(e.getSource() == btnPedido[i]){
                limpiarArti();
                paneles(requi[i]);
                jLabel5.setText(requi[i]);
                revalidate();
                repaint();
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        
    }

    @Override
    public void mousePressed(MouseEvent e) {
        
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        for (int i = 0; i < contBtnP; i++) {
            if(e.getSource() == btnPedido[i]){
                btnPedido[i].setText(addColor(btnPedido[i].getText(),"102,153,255"));
            }
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        for (int i = 0; i < contBtnP; i++) {
            if(e.getSource() == btnPedido[i]){
                btnPedido[i].setText(addColor(btnPedido[i].getText(),"153,204,255"));
            }
        }
    }

}
