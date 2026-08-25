package pruebas;

import Conexiones.Conexion;
import VentanaEmergente.EntregaRequisicion.Cantidad;
import VentanaEmergente.EntregaRequisicion.Info;
import VentanaEmergente.EntregaRequisicion.verFolio;
import VentanaEmergente.EntregaRequisicion.verPedidos;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import scrollPane.ScrollBarCustom;

public class EntregaRequisicion extends javax.swing.JInternalFrame implements ActionListener{

    JButton[] botones;
    JPanel[] panel;
    JButton[] btnLateral;
    JPanel[] panelLateral;
    JPanel[] pseccion;
    public JLabel[] requisiciones;
    String[] parte, cantidad, folio, parteSelecccionados, cantidadSeleccionados, id, idSeleccionados;
    public int conBtn = 0, contLabel = 0;
    String requisicion;
    int colors[];
    String numEmpleado;
    
    public Color color(int lab){
        Color color = null;
        switch(lab){
            case 0:
                color = Color.BLUE;
                break;
            case 1:
                color = Color.GREEN;
                break;
            case 2:
                color = Color.ORANGE;
                break;
            case 3:
                color = Color.MAGENTA;
                break;
            case 4:
                color = Color.RED;
                break;
            case 5:
                color = Color.black;
                break;
            case 6:
                color = Color.CYAN;
                break;
            case 7:
                color = Color.PINK;
                break;
            case 8:
                color = Color.DARK_GRAY;
                break;
            case 9:
                color = Color.YELLOW;
                break;
        }
        
        return color;
    }
    
    public Color getColor(int pos){
        Color c = new Color(0,0,0);
        
        int aux = 0;
        for (int i = 0; i < colors.length+1; i++) {
            if(pos >= aux && pos < colors[i]){
                c = color(i);
                break;
            }
            aux = colors[i];
        }
        
        return c;
    }
    
    public void limpiarPantalla(){
        panelArti.removeAll();
        panelArticulos.removeAll();
        panelRequi.removeAll();
        btnCancelar = new javax.swing.JButton();
        btnCancelar.setBackground(new java.awt.Color(255, 255, 255));
        btnCancelar.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        btnCancelar.setForeground(new java.awt.Color(153, 0, 0));
        btnCancelar.setText(" Cancelar ");
        btnCancelar.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 1, new java.awt.Color(153, 0, 0)));
        btnCancelar.setBorderPainted(false);
        btnCancelar.setContentAreaFilled(false);
        btnCancelar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCancelar.setFocusPainted(false);
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });
        panelRequi.add(btnCancelar);
        jLabel6 = new javax.swing.JLabel();
        jLabel6.setText("    ");
        panelRequi.add(jLabel6);
        btnLateral = new JButton[50];
        panelLateral = new JPanel[50];
        parteSelecccionados = new String[50];
        cantidadSeleccionados = new String[50];
        idSeleccionados = new String[50];
        folio = new String[50];
        requisiciones = new JLabel[10];
        conBtn = 0;
        contLabel = 0;
        revalidate();
        repaint();
    }
    
    public void addBotonesAgregados(){
        panelArticulos.removeAll();
        for (int i = 0; i < conBtn; i++) {
            panelArticulos.add(btnLateral[i]);
        }
    }
    
    public void borrarBoton(int btn){
        int real = 0;
        for (int i = 0; i < conBtn; i++) {
            if(parte[btn].equals(parteSelecccionados[i])){
                real = i;
            }
        }
        
        btnLateral[real] = null;
            
        for (int i = real; i < conBtn; i++) {
            btnLateral[i] = btnLateral[i+1];
            parteSelecccionados[i] = parteSelecccionados[i+1];
        }
        conBtn--;
        addBotonesAgregados();
        revalidate();
        repaint();
    }
    
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
        colors = new int[contLabel];
            for (int i = 0; i < contLabel; i++) {
                Statement st = con.createStatement();
                String sql = "select Codigo from requisiciones where NumRequisicion like '"+requisiciones[i].getText()+"'";
                ResultSet rs = st.executeQuery(sql);

                while(rs.next()){
                    cont1++;
                }
                colors[i] = cont1;
            }
        botones= new JButton[cont1];
        pseccion = new JPanel[cont1];
        panel = new JPanel[cont1];
        parte = new String[cont1];
        cantidad = new String[cont1];
        id = new String[cont1];
        folio = new String[cont1];
        
        int cont = 0;
        
//        pseccion[cont] = new JPanel(new FlowLayout());
//        pseccion[cont].setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 10, 5));
//        pseccion[cont].setBackground(Color.white);
//        cont++;
            int col = 0;
            int i = 0;
            for (int a = 0; a < contLabel; a++) {
                String sql2 = "select * from requisiciones where NumRequisicion like "+requisiciones[a].getText()+"";
                Statement st2 = con.createStatement();
                ResultSet rs2 = st2.executeQuery(sql2);
                boolean ban = true;
                int ac = 0;
                double ancho = (this.getWidth() - jScrollPane2.getWidth()) / 7.5 ;
                JLabel l = new javax.swing.JLabel();
                    l.setText("    ");
                while(rs2.next()){
                    
                    if(ac%5 == 0 || ban == true)
                    {
                     pseccion[cont] = new JPanel(new FlowLayout()); 
                     pseccion[cont].setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 10, 5));
                     pseccion[cont].setBackground(Color.white);
                     pseccion[cont].setBorder(javax.swing.BorderFactory.createMatteBorder(0, 10, 0, 0, color(col)));
                     cont++;
                    }
                    String opc = rs2.getString("Cantidad");
                    
                    id[i] = rs2.getString("Id");
                    if(rs2.getString("Folio") != null){
                    opc = "FOLIO-"+rs2.getString("Folio");
                    folio[i] = rs2.getString("Folio");
                    }
                    parte[i] = rs2.getString("Codigo");
                    cantidad[i] = rs2.getString("Cantidad");
                    botones[i]= new JButton(
                              "<html>"
                            + "<div style='width: "+ancho+"px;'>"
                            + "<p style='font-size:14px; text-align: center; font-weight: 700;'>"+rs2.getString("Codigo")+"</p>"
                            + "<p style='font-size:10px;'>"+rs2.getString("Descripcion")+"</p>"
                            + "<P style='font-size:8px;'>"+opc+"</P>"
                            + "<P style='font-size:10px; font-weight: 700;'>"+rs2.getString("Proyecto")+"</P>"
                            + "<P style='font-size:10px; font-weight: 700;'>"+rs2.getString("Ubicacion")+"</P>"
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
                    String cantRec = rs2.getString("CantRecibida");
                    double can = 0;
                    double canR = 0;
                    if(cantRec == null){
                        
                    }else {
                        can  = Double.parseDouble(cantRec);
                        canR = Double.parseDouble(rs2.getString("Cantidad"));
                        if(can == canR){
                            botones[i].setBackground(new java.awt.Color(255, 255, 255));
                        }else{
                            botones[i].setBackground(new java.awt.Color(0, 161, 236));
                            botones[i].setForeground(Color.white);
                        }
                    }
                    if(rs2.getString("Entregado") != null){
                        panel[i].setBackground(Color.red);
                        botones[i].setForeground(Color.white);
                    }
                    if(rs2.getString("Llego") == null){
                        panel[i].setBackground(new Color(157,70,0));
                        botones[i].setForeground(Color.white);
                    }
                    if(btnLateral[0] != null){
                        for (int j = 0; j < conBtn; j++) {
                            if(idSeleccionados[j].equals(id[i])){
                                panel[i].setBackground(Color.green);
                            }
                        }
                    }
                    panel[i].add(botones[i]);
                    pseccion[cont-1].add(panel[i]);
                    panelArti.add(pseccion[cont-1]);
                    
                    i++;
                    ac++;
                    ban = false;
                    }
        panelArti.add(l);
        col++;
        }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "ERROR: "+e,"ERROR",JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(Inicio1.class.getName()).log(Level.SEVERE,null,e);
        }
    }
    
    public void insertarLateral(String parte, String cantidad, int pos){
        
        btnLateral[conBtn] = new JButton();
        btnLateral[conBtn].setBackground(new java.awt.Color(255, 255, 255));
        btnLateral[conBtn].setFont(new java.awt.Font("Roboto", Font.BOLD, 14)); // NOI18N
        Color c = getColor(pos);
        String co = c.getRed() + "," + c.getGreen() + "," + c.getBlue();
        btnLateral[conBtn].setText("<html>"
                + "<div style = 'border-left: solid 4px rgb("+co+"); margin: 0 0 10px; 0'>"
                + "<p style='width: 250px; text-align: left; margin: 10px; '> "+parte+" </p>"
                + "<p style='width: 250px; font-size:10px; text-align: left; border-bottom: solid 2px rgb(235, 235, 235); color: rgb(150,150,150);padding: 5px;'> Cantidad................"+cantidad+"</p>"
                + "</div>"
                + "</html>");
        btnLateral[conBtn].setBorder(javax.swing.BorderFactory.createMatteBorder(0, 5, 0, 0, new java.awt.Color(153, 0, 0)));
        btnLateral[conBtn].setBorderPainted(false);
        btnLateral[conBtn].setContentAreaFilled(false);
        btnLateral[conBtn].setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnLateral[conBtn].setFocusPainted(false);
        btnLateral[conBtn].addActionListener(this);
        
        panelLateral[conBtn] = new JPanel();
        panelLateral[conBtn].setBackground(Color.white);
        panelLateral[conBtn].add(btnLateral[conBtn]);
        panelArticulos.add(btnLateral[conBtn]);
        parteSelecccionados[conBtn] = parte;
        
        conBtn++;
        
        revalidate();
        repaint();
        
    }
    
    public EntregaRequisicion(String Id) {
        initComponents();
        numEmpleado = Id;
        ((javax.swing.plaf.basic.BasicInternalFrameUI) this.getUI()).setNorthPane(null);
        jScrollPane1.setVerticalScrollBar(new ScrollBarCustom(new Color(0,165,255)));
        jScrollPane1.setHorizontalScrollBar(new ScrollBarCustom(new Color(0,165,255)));
        jScrollPane2.setVerticalScrollBar(new ScrollBarCustom(new Color(0,165,255)));
        jScrollPane2.setHorizontalScrollBar(new ScrollBarCustom(new Color(0,165,255)));
        
        btnLateral = new JButton[50];
        panelLateral = new JPanel[50];
        parteSelecccionados = new String[50];
        cantidadSeleccionados = new String[50];
        idSeleccionados = new String[50];
        folio = new String[50];
        requisiciones = new JLabel[10];
        
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
        txtRequi = new javax.swing.JTextField();
        jPanel8 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        panelArticulos = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        panelArti = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        panelRequi = new javax.swing.JPanel();
        btnCancelar = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem2 = new javax.swing.JMenuItem();

        setBorder(null);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new java.awt.BorderLayout());

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new java.awt.BorderLayout());

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jLabel9.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 60)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 165, 252));
        jLabel9.setText("    ENTREGA DE REQUISICION    ");
        jPanel3.add(jLabel9);

        jPanel2.add(jPanel3, java.awt.BorderLayout.CENTER);

        jPanel13.setBackground(new java.awt.Color(255, 255, 255));

        btnSalir.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("  X  ");
        jLabel1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
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
        jPanel4.setLayout(new java.awt.BorderLayout(10, 10));

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setLayout(new java.awt.GridLayout(2, 0));

        jLabel2.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 102, 255));
        jLabel2.setText("INGRESA NUMERO DE REQUISICION");
        jPanel6.add(jLabel2);

        txtRequi.setBackground(new java.awt.Color(255, 255, 255));
        txtRequi.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        txtRequi.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtRequi.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(102, 153, 255)));
        txtRequi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtRequiActionPerformed(evt);
            }
        });
        jPanel6.add(txtRequi);

        jPanel5.add(jPanel6);

        jPanel4.add(jPanel5, java.awt.BorderLayout.PAGE_START);

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));
        jPanel8.setPreferredSize(new java.awt.Dimension(250, 591));
        jPanel8.setLayout(new java.awt.BorderLayout());

        jButton1.setBackground(new java.awt.Color(255, 255, 255));
        jButton1.setFont(new java.awt.Font("Roboto", 1, 24)); // NOI18N
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
        jPanel8.add(jButton1, java.awt.BorderLayout.PAGE_END);

        jScrollPane2.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane2.setBorder(null);

        panelArticulos.setBackground(new java.awt.Color(255, 255, 255));
        panelArticulos.setLayout(new javax.swing.BoxLayout(panelArticulos, javax.swing.BoxLayout.Y_AXIS));
        jScrollPane2.setViewportView(panelArticulos);

        jPanel8.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        jPanel4.add(jPanel8, java.awt.BorderLayout.LINE_END);

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));
        jPanel9.setLayout(new java.awt.BorderLayout(10, 10));

        jScrollPane1.setBorder(null);

        panelArti.setBackground(new java.awt.Color(255, 255, 255));
        panelArti.setLayout(new javax.swing.BoxLayout(panelArti, javax.swing.BoxLayout.Y_AXIS));
        jScrollPane1.setViewportView(panelArti);

        jPanel9.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jPanel10.setBackground(new java.awt.Color(255, 255, 255));
        jPanel10.setLayout(new java.awt.BorderLayout());

        jPanel11.setBackground(new java.awt.Color(255, 255, 255));

        jLabel3.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jLabel3.setText("REQUISICIONES AGREGADAS");
        jPanel11.add(jLabel3);

        jPanel10.add(jPanel11, java.awt.BorderLayout.CENTER);

        panelRequi.setBackground(new java.awt.Color(255, 255, 255));

        btnCancelar.setBackground(new java.awt.Color(255, 255, 255));
        btnCancelar.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        btnCancelar.setForeground(new java.awt.Color(153, 0, 0));
        btnCancelar.setText(" Cancelar ");
        btnCancelar.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 1, new java.awt.Color(153, 0, 0)));
        btnCancelar.setBorderPainted(false);
        btnCancelar.setContentAreaFilled(false);
        btnCancelar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCancelar.setFocusPainted(false);
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });
        panelRequi.add(btnCancelar);

        jLabel6.setText("    ");
        panelRequi.add(jLabel6);

        jPanel10.add(panelRequi, java.awt.BorderLayout.SOUTH);

        jPanel9.add(jPanel10, java.awt.BorderLayout.PAGE_START);

        jPanel4.add(jPanel9, java.awt.BorderLayout.CENTER);

        jPanel1.add(jPanel4, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        jMenu1.setText("File");

        jMenuItem1.setText("Ver detalles de entrega");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");

        jMenuItem2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/Estado.png"))); // NOI18N
        jMenuItem2.setText("        INFO    ");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem2);

        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseClicked
        dispose();
    }//GEN-LAST:event_jLabel1MouseClicked

    private void jLabel1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseEntered
        btnSalir.setBackground(java.awt.Color.red);
    }//GEN-LAST:event_jLabel1MouseEntered

    private void jLabel1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseExited
        btnSalir.setBackground(java.awt.Color.white);
    }//GEN-LAST:event_jLabel1MouseExited

    private void txtRequiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtRequiActionPerformed
        try{
            Connection con;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            String sql = "select * from requisiciones where NumRequisicion like '"+txtRequi.getText()+"'";
            ResultSet rs = st.executeQuery(sql);
            String re = "";
            while(rs.next()){
                re = rs.getString("NumRequisicion");
            }
            boolean f = false;
            for (int i = 0; i < contLabel; i++) {
                if(txtRequi.getText().equals(requisiciones[i].getText())){
                    f = true;
                }
            }
            if(re == null || re.equals("")){
                JOptionPane.showMessageDialog(this, "NO EXISTE ESTA REQUISICION","ADVERTENCIA",JOptionPane.WARNING_MESSAGE);
            }else if(f){
                JOptionPane.showMessageDialog(this, "ESTA REQUISICION YA ESTA INCLUIDA","ADVERTENCIA",JOptionPane.WARNING_MESSAGE);
                txtRequi.setText("");
            }else{
                requisicion = txtRequi.getText();
                requisiciones[contLabel] = new JLabel();
                requisiciones[contLabel].setFont(new java.awt.Font("Roboto", 1, 14));
                requisiciones[contLabel].setForeground(color(contLabel));
                requisiciones[contLabel].setText(txtRequi.getText());
                panelRequi.add(requisiciones[contLabel]);
                contLabel++;
                txtRequi.setText("");
                paneles(txtRequi.getText());
                revalidate();
                repaint();
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "ERROR: "+e,"ERROR",JOptionPane.ERROR_MESSAGE);
        }
        
    }//GEN-LAST:event_txtRequiActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        limpiarPantalla();
    }//GEN-LAST:event_btnCancelarActionPerformed
public void li(){
    JOptionPane.showMessageDialog(this, "si se va cerrar");
}
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        JFrame f = (JFrame) JOptionPane.getFrameForComponent(this);
        //parteSelecccionados, cantidadSeleccionados, idSeleccionados
        EntregaRequisicion e = this;
        VentanaEmergente.EntregaRequisicion.Guardar g = new VentanaEmergente.EntregaRequisicion.Guardar(f, true, parteSelecccionados, cantidadSeleccionados, idSeleccionados, conBtn, numEmpleado,e);
        g.setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        JFrame f = (JFrame) JOptionPane.getFrameForComponent(this);
        verPedidos v = new verPedidos(f, true,numEmpleado);
        v.setVisible(true);
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        JFrame f = (JFrame) JOptionPane.getFrameForComponent(this);
        Info i = new Info(f,true);
        i.setVisible(true);
    }//GEN-LAST:event_jMenuItem2ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;
    private javax.swing.JPanel btnSalir;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JPanel panelArti;
    private javax.swing.JPanel panelArticulos;
    private javax.swing.JPanel panelRequi;
    private javax.swing.JTextField txtRequi;
    // End of variables declaration//GEN-END:variables

    @Override
    public void actionPerformed(ActionEvent e) {
        
        for (int i = 0; i < botones.length; i++) {
            if(e.getSource() == botones[i]){
                    if(panel[i].getBackground().equals(Color.white)){
                        JFrame f = (JFrame) JOptionPane.getFrameForComponent(this);
                        Cantidad c = new Cantidad(f, true, id[i]);
                        c.setVisible(true);
                        String a = c.cantidadEntregada();
                        if(a.equals("")){
                            
                        }else{
                            panel[i].setBackground(Color.green);
                            cantidadSeleccionados[conBtn] = a;
                            idSeleccionados[conBtn] = id[i];
                            insertarLateral(parte[i],c.cantidadEntregada(),i);
                        }
                        
                    }else if(panel[i].getBackground().equals(Color.green)){
                        panel[i].setBackground(Color.white);
                        borrarBoton(i);
                    }else if(panel[i].getBackground().equals(Color.red)){
                        JFrame f = (JFrame) JOptionPane.getFrameForComponent(this);
                        verFolio v = new verFolio(f,true,folio[i]);
                        v.setVisible(true);
                    }
                    
                }
            jScrollPane2.getVerticalScrollBar().setValue(jScrollPane2.getVerticalScrollBar().getMaximum());
            }
        
        for (int i = 0; i < conBtn; i++) {
            if(e.getSource() == btnLateral[i]){
                JOptionPane.showMessageDialog(this, "NUMERO DE PARTE: "+parteSelecccionados[i]+"\n"
                                                               + "ID: "+idSeleccionados[i]+"\n"
                                                               + "CANTIDAD: "+cantidadSeleccionados[i]);
            }
        }
        
    }
}
