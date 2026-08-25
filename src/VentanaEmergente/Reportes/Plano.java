package VentanaEmergente.Reportes;

import Conexiones.Conexion;
import java.awt.Color;
import java.awt.Font;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;

public class Plano extends javax.swing.JFrame {

    JPanel[] panelCortes1;
    JPanel[] panelFresa1;
    JPanel[] panelCnc1;
    JPanel[] panelTorno1;
    JPanel[] panelAcabados1;
    
    JPanel[] panelCortes2;
    JPanel[] panelFresa2;
    JPanel[] panelCnc2;
    JPanel[] panelTorno2;
    JPanel[] panelAcabados2;
    
    com.bolivia.label.CLabel[] lblNombre;
    com.bolivia.label.CLabel[] lblNombre1;
    com.bolivia.label.CLabel[] lblNombre2;
    
    JLabel[] lblNoEmpleado;
    JLabel[] lblFechaInicio;
    JLabel[] lblFechaFinal;
    
    JLabel[] lblNoEmpleado1;
    JLabel[] lblFechaInicio1;
    JLabel[] lblFechaFinal1;
    JLabel[] lblNom1;
    
    JLabel[] lblNoEmpleado2;
    JLabel[] lblFechaInicio2;
    JLabel[] lblFechaFinal2;
    JLabel[] lblNom2;
    
    public void limpiarPantalla(){
        Cortes = new javax.swing.JPanel();
        Cortes.setBackground(new java.awt.Color(255, 255, 255));
        Cortes.setBorder(javax.swing.BorderFactory.createTitledBorder("CORTES"));
        Cortes.setLayout(new javax.swing.BoxLayout(Cortes, javax.swing.BoxLayout.Y_AXIS));
        jScrollPane1.setViewportView(Cortes);
        revalidate();
        repaint();
    }
    
    public void add(String nombre, String numero, String inicio, String fin, JPanel[] panel1, JPanel[] panel2, JPanel panelPadre, boolean band){
        
        panel1 = new JPanel[2];
        panel2 = new JPanel[2];
        
        lblNombre = new com.bolivia.label.CLabel[2];
    
        lblNoEmpleado = new JLabel[2];
        lblFechaInicio = new JLabel[2];
        lblFechaFinal = new JLabel[2];
        lblNom1 = new JLabel[2];
        
        lblNombre1 = new com.bolivia.label.CLabel[2];
    
        lblNoEmpleado1 = new JLabel[2];
        lblFechaInicio1 = new JLabel[2];
        lblFechaFinal1 = new JLabel[2];
        lblNom2 = new JLabel[2];
        
        for (int i = 0; i < 1; i++) {
        JSeparator s = new JSeparator();
        s.setBackground(Color.black);
        
        if(band == true){
        panel1[i] = new JPanel();
        panel1[i].setLayout(new BoxLayout(panel1[i], BoxLayout.Y_AXIS));
        panel1[i].setBorder(BorderFactory.createTitledBorder("EMPEZADO POR:"));
        panel1[i].setBackground(Color.white);
        
        
        
        lblNombre[i] = new com.bolivia.label.CLabel();
        lblNombre[i].setBackground(new java.awt.Color(255, 255, 255));
        lblNombre[i].setBorder(null);
        lblNombre[i].setForeground(new java.awt.Color(51, 153, 255));
        lblNombre[i].setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/usuario (1).png"))); // NOI18N
//        lblNombre[i].setText(nombre);
        lblNombre[i].setFont(new java.awt.Font("Roboto Light", 0, 14)); 
        lblNombre[i].setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        lblNombre[i].setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        panel1[i].add(lblNombre[i]);
        
        lblNom1[i] = new JLabel(nombre);
        lblNom1[i].setForeground(Color.black);
        lblNom1[i].setFont(new Font("Roboto",Font.PLAIN,14));
        panel1[i].add(lblNom1[i]);
        
        lblNoEmpleado[i] = new JLabel(numero);
        lblNoEmpleado[i].setForeground(Color.black);
        lblNoEmpleado[i].setFont(new Font("Roboto",Font.PLAIN,14));
        panel1[i].add(lblNoEmpleado[i]);
        
        lblFechaInicio[i] = new JLabel(inicio);
        lblFechaInicio[i].setForeground(Color.black);
        lblFechaInicio[i].setFont(new Font("Roboto",Font.PLAIN,14));
        panel1[i].add(lblFechaInicio[i]);
        
        panelPadre.add(panel1[i]);
        }else{
        panel2[i] = new JPanel();
        panel2[i].setLayout(new BoxLayout(panel2[i], BoxLayout.Y_AXIS));
        panel2[i].setBorder(BorderFactory.createTitledBorder("TERMINADO POR:"));
        panel2[i].setBackground(Color.white);
        
        lblNombre1[i] = new com.bolivia.label.CLabel();
        lblNombre1[i].setBackground(new java.awt.Color(255, 255, 255));
        lblNombre1[i].setBorder(null);
        lblNombre1[i].setForeground(new java.awt.Color(51, 153, 255));
        lblNombre1[i].setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/usuario (1).png"))); // NOI18N
//        lblNombre1[i].setText(nombre);
        lblNombre1[i].setFont(new java.awt.Font("Roboto Light", 0, 14)); 
        lblNombre1[i].setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        lblNombre1[i].setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        panel2[i].add(lblNombre1[i]);
        
        lblNom2[i] = new JLabel(nombre);
        lblNom2[i].setForeground(Color.black);
        lblNom2[i].setFont(new Font("Roboto",Font.PLAIN,14));
        panel2[i].add(lblNom2[i]);
        
        lblNoEmpleado1[i] = new JLabel(numero);
        lblNoEmpleado1[i].setForeground(Color.black);
        lblNoEmpleado1[i].setFont(new Font("Roboto",Font.PLAIN,14));
        panel2[i].add(lblNoEmpleado1[i]);
        
        lblFechaInicio1[i] = new JLabel(fin);
        lblFechaInicio1[i].setForeground(Color.black);
        lblFechaInicio1[i].setFont(new Font("Roboto",Font.PLAIN,14));
        panel2[i].add(lblFechaInicio1[i]);
        panelPadre.add(panel2[i]);
        panelPadre.add(s);
        }
        }
        
        revalidate();
        repaint();
    }
    
    
    public Plano() {
        initComponents();
        this.setExtendedState(MAXIMIZED_BOTH);
        jScrollPane1.getVerticalScrollBar().setUnitIncrement(16);
        
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtPlano = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        Cortes = new javax.swing.JPanel();
        Fresa = new javax.swing.JPanel();
        Cnc = new javax.swing.JPanel();
        Torno = new javax.swing.JPanel();
        Acabados = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new java.awt.GridLayout(1, 0));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new java.awt.BorderLayout());

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jPanel7.setBackground(new java.awt.Color(0, 165, 252));

        jLabel2.setFont(new java.awt.Font("Roboto Medium", 0, 48)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("     REPORTE DE PLANOS     ");
        jPanel7.add(jLabel2);

        jPanel2.add(jPanel7);

        jPanel1.add(jPanel2, java.awt.BorderLayout.PAGE_START);

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel1.setText("INGRESA NO. DE PLANO");

        txtPlano.setBackground(new java.awt.Color(255, 255, 255));
        txtPlano.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtPlano.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        txtPlano.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPlanoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(txtPlano, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtPlano, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(409, 409, 409))
        );

        jPanel1.add(jPanel4, java.awt.BorderLayout.WEST);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setLayout(new java.awt.GridLayout(1, 5));

        jScrollPane1.setBorder(null);

        Cortes.setBackground(new java.awt.Color(255, 255, 255));
        Cortes.setBorder(javax.swing.BorderFactory.createTitledBorder("CORTES"));
        Cortes.setLayout(new javax.swing.BoxLayout(Cortes, javax.swing.BoxLayout.Y_AXIS));
        jScrollPane1.setViewportView(Cortes);

        jPanel3.add(jScrollPane1);

        Fresa.setBackground(new java.awt.Color(255, 255, 255));
        Fresa.setBorder(javax.swing.BorderFactory.createTitledBorder("FRESADORA"));
        jPanel3.add(Fresa);

        Cnc.setBackground(new java.awt.Color(255, 255, 255));
        Cnc.setBorder(javax.swing.BorderFactory.createTitledBorder("CNC"));
        jPanel3.add(Cnc);

        Torno.setBackground(new java.awt.Color(255, 255, 255));
        Torno.setBorder(javax.swing.BorderFactory.createTitledBorder("TORNO"));
        jPanel3.add(Torno);

        Acabados.setBackground(new java.awt.Color(255, 255, 255));
        Acabados.setBorder(javax.swing.BorderFactory.createTitledBorder("ACABADOS"));
        jPanel3.add(Acabados);

        jPanel1.add(jPanel3, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtPlanoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPlanoActionPerformed
        try{
            limpiarPantalla();
            Connection con = null;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            
            for (int i = 0; i < 5; i++) {
                
                    String bd = "";
                    JPanel panelPadre = null;
                    JPanel[] panel1 = null;
                    JPanel[] panel2 = null;
                    
                switch (i) {
                    case 0:
                        bd = "datos";
                        panelPadre = Cortes;
                        panel1 = panelCortes1;
                        panel2 = panelCortes2;
                        break;
                    case 1:
                        bd = "fresadora";
                        panelPadre = Fresa;
                        panel1 = panelFresa1;
                        panel2 = panelFresa2;
                        break;
                    case 2:
                        bd = "cnc";
                        panelPadre = Cnc;
                        panel1 = panelCnc1;
                        panel2 = panelCnc2;
                        break;
                    case 3:
                        bd = "torno";
                        panelPadre = Torno;
                        panel1 = panelTorno1;
                        panel2 = panelTorno2;
                        break;
                    case 4:
                        bd = "acabados";
                        panelPadre = Acabados;
                        panel1 = panelAcabados1;
                        panel2 = panelAcabados2;
                        break;
                    default:
                        break;
                }
                
                Statement st = con.createStatement();
                String sql = "select * from "+bd+" where Proyecto like '"+txtPlano.getText()+"'";
            ResultSet rs = st.executeQuery(sql);
            String datos[] = new String[10];
            
            while(rs.next()){
                datos[0] = rs.getString("FechaInicio");
                datos[1] = rs.getString("FechaFinal");
                datos[2] = rs.getString("Empleado");
                datos[3] = rs.getString("Proyecto");
            }
            if(datos[2] == null){
                datos[2] = "";
            }
            if(datos[3] == null){
                JLabel imagen = new JLabel();
                imagen = new javax.swing.JLabel();
                imagen.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/sin plano.png"))); // NOI18N
                imagen.setToolTipText("SIN PLANO");
                panelPadre.add(imagen);
                revalidate();
                repaint();
            }else if(datos[2].equals("")){
            JLabel imagen = new JLabel();
                imagen = new javax.swing.JLabel();
                imagen.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/sin empleado.png"))); // NOI18N
                imagen.setToolTipText("SIN EMPLEADO");
                panelPadre.add(imagen);
                revalidate();
                repaint();
            }else{
            
                String buscar = ",";
                boolean band = true;
                int aux = 0;
                int cont = 0;
                char arreglo[] = datos[2].toCharArray();
                for (int j = 0; j < datos[2].length(); j++) {
                    String letra = String.valueOf(arreglo[j]);
                    if (buscar.equalsIgnoreCase(letra)) {
                        String sql2 = "select * from registroempleados where NumEmpleado like '"+datos[2].substring(aux,j)+"'";
                        Statement st2 = con.createStatement();
                        ResultSet rs2 = st2.executeQuery(sql2);
                        String nombre = "";
                        String ape = "";
                        while(rs2.next()){
                            nombre = rs2.getString("Nombre");
                            ape = rs2.getString("Apellido");
                        }
                        nombre = nombre +" "+ ape;
                        add(nombre,datos[2].substring(aux,j),datos[0],datos[1],panel1,panel2,panelPadre,band);
                        aux = j+1;
                        cont++;
                        if(band == true){
                            band = false;
                        }else{
                            band = true;
                        }
                        nombre = "";
                    }
                    if(j == datos[2].length()-1){
                        String sql2 = "select * from registroempleados where NumEmpleado like '"+datos[2].substring(aux,(j+1))+"'";
                        Statement st2 = con.createStatement();
                        ResultSet rs2 = st2.executeQuery(sql2);
                        String nombre = "";
                        String ape = "";
                        while(rs2.next()){
                            nombre = rs2.getString("Nombre");
                            ape = rs2.getString("Apellido");
                        }
                        nombre = nombre + ape;
                        if(cont%2 == 0){
                            band = true;
                        }else{
                            band = false;
                        }
                        add(nombre,datos[2].substring(aux,j+1),datos[0],datos[1],panel1,panel2,panelPadre,band);
                        aux = j+1;
                        nombre = "";
                    }
                }
            }
            }
            
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "ERROR: "+e,"ERROR",JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_txtPlanoActionPerformed

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
            java.util.logging.Logger.getLogger(Plano.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Plano.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Plano.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Plano.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Plano().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Acabados;
    private javax.swing.JPanel Cnc;
    private javax.swing.JPanel Cortes;
    private javax.swing.JPanel Fresa;
    private javax.swing.JPanel Torno;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField txtPlano;
    // End of variables declaration//GEN-END:variables
}
