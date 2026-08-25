package pruebas;

import Conexiones.Conexion;
import VentanaEmergente.Almacen.Texto;
import VentanaEmergente.Almacen.guardar;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Stack;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;

public class Almacen extends javax.swing.JInternalFrame implements ActionListener, MouseListener{

    public Stack<JButton> btnPedido;
    public Stack<JPanel> panelPedido;
    public Stack<String> requi;
    public Stack<String> color;
    public String numEmpleado;
    
    public final  void addBotonesPedido(){
        try{
            Connection con;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            String sql = "select * from requisicion where LiberacionAlmacen is null order by Id desc";
            ResultSet rs = st.executeQuery(sql);
            String datos[] = new String[10];
            
            btnPedido = new Stack<>();
            panelPedido = new Stack<>();
            requi = new Stack<>();
            color = new Stack<>();
            
            while(rs.next()){
                datos[0] = rs.getString("Id");
                requi.push(datos[0]);
                datos[1] = rs.getString("Fecha");
                datos[2] = rs.getString("Progreso");
                
                JPanel panel = new JPanel();
                panel.setBackground(new Color(153,204,255));
                
                JButton boton = new JButton();
                
                boton.setFont(new java.awt.Font("Roboto", 0, 12));
                boton.setBorder(null);
                boton.setBorderPainted(false);
                boton.setContentAreaFilled(false);
                boton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
                boton.setFocusPainted(false);
                
                String color ="153,204,255";
                if(datos[2].equals("NUEVO")){
                    color = "157,154,210";
                    panel.setBackground(new Color(157,154,210));
                }else if(datos[2].equals("COTIZANDO")){
                    color = "200,39,4";
                    panel.setBackground(new Color(200,39,4));
                }
                this.color.push(color);
                boton.setText(
                              "<html>"
                            + "<div style='width: 190px; background-color: rgb("+color+"); padding: 5px; margin: 5px;'>"
                            + "<p style='font-size:14px;  text-align: center; font-weight: 700;'>"+datos[0]+"</p>"
                            + "<p style='font-size:10px;'>"+datos[2]+"</p>"
                            + "<P style='font-size:8px;'>"+datos[1]+"</P>"
                            + "</div>"
                            + "</html>"
                );
                
                boton.addActionListener(this);
                boton.addMouseListener(this);
                panelPedido.push(panel);
                btnPedido.push(boton);
                int can = panelPedido.size()-1;
                panelPedido.get(can).add(btnPedido.get(can));
                
                java.awt.GridBagConstraints gridBagConstraints = new java.awt.GridBagConstraints();
                gridBagConstraints.gridx = 0;
                gridBagConstraints.gridy = can;
                gridBagConstraints.insets = new java.awt.Insets(15, 0, 15, 0);
                
                PanelPedidos.add(panelPedido.get(can), gridBagConstraints);
                revalidate();
                repaint();
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "ERROR: "+e,"ERROR",JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public final void limpiarTabla(){
        Tabla1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                
            },
            new String [] {
                "Codigo", "Descripcion", "Cantidad", "Proyecto", "Cantidad Encontrada", "Localizacion", "Id"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, true,false, false
            };
            
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        
        Tabla1.getTableHeader().setFont(new Font("Lexend", Font.BOLD, 14));
        Tabla1.getTableHeader().setOpaque(false);
        Tabla1.getTableHeader().setBackground(new Color(0, 78, 171));
        Tabla1.getTableHeader().setForeground(Color.white);
        Tabla1.setRowHeight(25);
        Tabla1.setShowVerticalLines(false);
        Tabla1.setGridColor(new Color(240,240,240));
        
        jScrollPane2.getViewport().setBackground(new Color(255,255,255));
        
        if (Tabla1.getColumnModel().getColumnCount() > 0) {
            Tabla1.getColumnModel().getColumn(2).setMinWidth(100);
            Tabla1.getColumnModel().getColumn(2).setPreferredWidth(100);
            Tabla1.getColumnModel().getColumn(2).setMaxWidth(100);
            Tabla1.getColumnModel().getColumn(4).setMinWidth(150);
            Tabla1.getColumnModel().getColumn(4).setPreferredWidth(150);
            Tabla1.getColumnModel().getColumn(4).setMaxWidth(150);
            Tabla1.getColumnModel().getColumn(3).setMinWidth(150);
            Tabla1.getColumnModel().getColumn(3).setPreferredWidth(150);
            Tabla1.getColumnModel().getColumn(3).setMaxWidth(150);
            Tabla1.getColumnModel().getColumn(5).setMinWidth(150);
            Tabla1.getColumnModel().getColumn(5).setPreferredWidth(150);
            Tabla1.getColumnModel().getColumn(5).setMaxWidth(150);
        }
    }
    
    public void verRequisicion(String requisicion){
        try{
            limpiarTabla();
            Connection con;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            String sql = "select Codigo, Descripcion, Cantidad, Proyecto, Id from requisiciones where NumRequisicion like '" + requisicion + "'";
            ResultSet rs = st.executeQuery(sql);
            String datos[] = new String[10];
            DefaultTableModel miModelo = (DefaultTableModel) Tabla1.getModel();
            while(rs.next()){
                datos[0] = rs.getString("Codigo");
                datos[1] = rs.getString("Descripcion");
                datos[2] = rs.getString("Cantidad");
                datos[3] = rs.getString("Proyecto");
                datos[6] = rs.getString("Id");
                String sql2 = "select NumeroDeParte,Ubicacion from inventario where NumeroDeParte like '"+datos[0]+"'";
                Statement st2 = con.createStatement();
                ResultSet rs2 = st2.executeQuery(sql2);
                String ubicacion = null;
                while(rs2.next()){
                    ubicacion = rs2.getString("Ubicacion");
                }
                datos[5] = ubicacion;
                miModelo.addRow(datos);
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "ERROR: "+e,"ERROR",JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public boolean verificarCantidades(){
        boolean band = false;
        for (int i = 0; i < Tabla1.getRowCount(); i++) {
            String cantidad;
            try{cantidad = Tabla1.getValueAt(i, 4).toString();}catch(Exception e){cantidad = null;}
            if(cantidad == null){
                band = true;
            }else if(cantidad.equals("")){
                band = true;
            }
        }
        return band;
    }
    
    public void actualizarInventario(String codigo,String cantidad, Connection con) throws SQLException{
        Statement st = con.createStatement();
        String sql = "select * from inventario where NumeroDeParte like '"+codigo+"'";
        ResultSet rs = st.executeQuery(sql);
        String cant = null;
        while(rs.next()){
            cant = rs.getString("Cantidad");
        }
        if(cant != null){
            String sql2 = "update inventario set Cantidad = ? where NumeroDeParte = ?";
            PreparedStatement pst = con.prepareStatement(sql2);
            
            double c1,c2;
            
            try{c1 = Double.parseDouble(cantidad);}catch(Exception e){c1 = 0;}
            try{c2 = Double.parseDouble(cant);}catch(Exception e){c2 = 0;}
            
            double total = c2 - c1;
            
            pst.setString(1, String.valueOf(total));
            pst.setString(2, codigo);
            
            int n = pst.executeUpdate();
            
            if(n <= 0){
                JOptionPane.showMessageDialog(this, "No se pudo actualizar el inventario: "+ codigo, "Error",JOptionPane.ERROR_MESSAGE);
            }
            
        }else{
            JOptionPane.showMessageDialog(this, "Error al actualizar inventario: "+codigo,"Error",JOptionPane.ERROR_MESSAGE);
        }
    }
    
    //Código final
    public void actualizarRequisiciones(String requi, String codigo, String cantidad, Connection con, String cantidadTabla) throws SQLException {

        double cantIngresada = Double.parseDouble(cantidad); //Cantidad que ingresa almacén
        double cantPedida = Double.parseDouble(cantidadTabla); // Cantidad que se solicitó en la requisición

        boolean completo = Double.compare(cantIngresada, cantPedida) >= 0; //Si la cantidad que ingresa almacén es mayor o igual a la cantidad que se solicitó, completo se vuelve verdadero

        String sql = "UPDATE requisiciones SET cantRecibida = ?, cantidadStock = ?"; // Inicio de la consulta

        if (completo) { //Si completo sí fue verdadero...
            sql += ", Llego = ?, OC = ?"; //Se agrega esta parte al inicio de la consulta
        }

        sql += " WHERE NumRequisicion = ? AND Codigo = ?";

        PreparedStatement pst = con.prepareStatement(sql);

        int idx = 1;
        pst.setDouble(idx++, cantIngresada); //Cantidad que ingresa almacén para almacenar en cantRecibida
        pst.setDouble(idx++, cantIngresada); //Cantidad que ingresa almacén para almacenar en cantidadStock

        if (completo) { //Si completo es verdadero...
            pst.setString(idx++, "SI"); //Se almacena "SI" en Llego
            pst.setString(idx++, "N/A"); //Se almacena "N/A" en "OC"
        }

        pst.setString(idx++, requi); //Se almacena el número de requisición en NumRequisicion
        pst.setString(idx++, codigo); //Se almacena el código en Codigo

        int n = pst.executeUpdate(); //Se ejecuta la consulta

        if (n <= 0) { //Si no se ejecutó correctamente la consulta se lanza un error
            JOptionPane.showMessageDialog(
                this,
                "Error al guardar cantidad en requisiciones: " + codigo,
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }
    
    public boolean verificarTabla(){
        boolean band = true;
        for (int i = 0; i < Tabla1.getRowCount(); i++) {
            double cantidad;
            double cantidadAgregada;
            try{cantidad = Double.parseDouble(Tabla1.getValueAt(i, 2).toString());}catch(Exception e){cantidad = 0;}
            try{cantidadAgregada = Double.parseDouble(Tabla1.getValueAt(i, 4).toString());}catch(Exception e){cantidadAgregada = -1;}
            if(cantidad > cantidadAgregada){
                band = false;
            }
        }
        return band;
    }
    
    public void limpiarBotones(){
        PanelPedidos.removeAll();
        revalidate();
        repaint();
    }
    
    public Almacen(String numEmpelado) {
        initComponents();
        addBotonesPedido();
        limpiarTabla();
        this.numEmpleado = numEmpelado;
        jScrollPane1.getVerticalScrollBar().setUnitIncrement(15);
        ((javax.swing.plaf.basic.BasicInternalFrameUI) this.getUI()).setNorthPane(null);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        lblRequisicion = new javax.swing.JLabel();
        pan = new javax.swing.JPanel();
        panelSalir = new javax.swing.JPanel();
        lblSalir = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        PanelPedidos = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        panelTerminar = new javax.swing.JPanel();
        btnTerminar = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        Tabla1 = new javax.swing.JTable();
        jPanel8 = new javax.swing.JPanel();
        panelIniciar = new javax.swing.JPanel();
        btnIniciar = new javax.swing.JButton();
        panelSi = new javax.swing.JPanel();
        btnSi = new javax.swing.JButton();
        panelNo = new javax.swing.JPanel();
        btnNo = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 255));
        setBorder(null);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new java.awt.BorderLayout(10, 10));

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setLayout(new java.awt.BorderLayout());

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 50, 5));

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));

        jLabel12.setFont(new java.awt.Font("Lexend", 1, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(0, 165, 252));
        jLabel12.setText("Revision de requisiciones Almacen");
        jPanel6.add(jLabel12);

        jPanel5.add(jPanel6);

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 51, 51));
        jLabel1.setText("Requisicion: ");
        jPanel9.add(jLabel1);

        lblRequisicion.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        lblRequisicion.setForeground(new java.awt.Color(51, 51, 51));
        jPanel9.add(lblRequisicion);

        jPanel5.add(jPanel9);

        jPanel4.add(jPanel5, java.awt.BorderLayout.CENTER);

        pan.setBackground(new java.awt.Color(255, 255, 255));

        panelSalir.setBackground(new java.awt.Color(255, 255, 255));

        lblSalir.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        lblSalir.setForeground(new java.awt.Color(0, 0, 0));
        lblSalir.setText(" X ");
        lblSalir.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblSalir.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblSalirMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblSalirMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblSalirMouseExited(evt);
            }
        });
        panelSalir.add(lblSalir);

        pan.add(panelSalir);

        jPanel4.add(pan, java.awt.BorderLayout.EAST);

        jPanel1.add(jPanel4, java.awt.BorderLayout.NORTH);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new java.awt.BorderLayout(10, 0));

        jScrollPane1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 8));
        jScrollPane1.setPreferredSize(new java.awt.Dimension(300, 26));

        PanelPedidos.setBackground(new java.awt.Color(255, 255, 255));
        PanelPedidos.setLayout(new java.awt.GridBagLayout());
        jScrollPane1.setViewportView(PanelPedidos);

        jPanel2.add(jScrollPane1, java.awt.BorderLayout.LINE_START);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setLayout(new java.awt.BorderLayout(10, 10));

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));

        panelTerminar.setBackground(new java.awt.Color(51, 204, 0));

        btnTerminar.setFont(new java.awt.Font("Lexend", 1, 12)); // NOI18N
        btnTerminar.setForeground(new java.awt.Color(255, 255, 255));
        btnTerminar.setText("Terminar");
        btnTerminar.setBorderPainted(false);
        btnTerminar.setContentAreaFilled(false);
        btnTerminar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnTerminar.setFocusPainted(false);
        btnTerminar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnTerminarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnTerminarMouseExited(evt);
            }
        });
        btnTerminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTerminarActionPerformed(evt);
            }
        });
        panelTerminar.add(btnTerminar);

        jPanel7.add(panelTerminar);

        jPanel3.add(jPanel7, java.awt.BorderLayout.PAGE_END);

        jScrollPane2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));

        Tabla1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Codigo", "Descripcion", "Cantidad", "Proyecto", "Id"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(Tabla1);
        if (Tabla1.getColumnModel().getColumnCount() > 0) {
            Tabla1.getColumnModel().getColumn(2).setMinWidth(150);
            Tabla1.getColumnModel().getColumn(2).setPreferredWidth(150);
            Tabla1.getColumnModel().getColumn(2).setMaxWidth(150);
            Tabla1.getColumnModel().getColumn(3).setMinWidth(300);
            Tabla1.getColumnModel().getColumn(3).setPreferredWidth(300);
            Tabla1.getColumnModel().getColumn(3).setMaxWidth(300);
        }

        jPanel3.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));
        jPanel8.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 15, 5));

        panelIniciar.setBackground(new java.awt.Color(51, 153, 255));

        btnIniciar.setFont(new java.awt.Font("Lexend", 1, 12)); // NOI18N
        btnIniciar.setForeground(new java.awt.Color(255, 255, 255));
        btnIniciar.setText("Iniciar");
        btnIniciar.setBorderPainted(false);
        btnIniciar.setContentAreaFilled(false);
        btnIniciar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnIniciar.setFocusPainted(false);
        btnIniciar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnIniciarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnIniciarMouseExited(evt);
            }
        });
        btnIniciar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIniciarActionPerformed(evt);
            }
        });
        panelIniciar.add(btnIniciar);

        jPanel8.add(panelIniciar);

        panelSi.setBackground(new java.awt.Color(51, 153, 255));

        btnSi.setFont(new java.awt.Font("Lexend", 1, 12)); // NOI18N
        btnSi.setForeground(new java.awt.Color(255, 255, 255));
        btnSi.setText("Se encontro Todo");
        btnSi.setBorderPainted(false);
        btnSi.setContentAreaFilled(false);
        btnSi.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSi.setFocusPainted(false);
        btnSi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnSiMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnSiMouseExited(evt);
            }
        });
        btnSi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSiActionPerformed(evt);
            }
        });
        panelSi.add(btnSi);

        jPanel8.add(panelSi);

        panelNo.setBackground(new java.awt.Color(51, 153, 255));

        btnNo.setFont(new java.awt.Font("Lexend", 1, 12)); // NOI18N
        btnNo.setForeground(new java.awt.Color(255, 255, 255));
        btnNo.setText("No se encontro Nada");
        btnNo.setBorderPainted(false);
        btnNo.setContentAreaFilled(false);
        btnNo.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnNo.setFocusPainted(false);
        btnNo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnNoMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnNoMouseExited(evt);
            }
        });
        btnNo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNoActionPerformed(evt);
            }
        });
        panelNo.add(btnNo);

        jPanel8.add(panelNo);

        jPanel3.add(jPanel8, java.awt.BorderLayout.PAGE_START);

        jPanel2.add(jPanel3, java.awt.BorderLayout.CENTER);

        jPanel1.add(jPanel2, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void lblSalirMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSalirMouseClicked
        dispose();
    }//GEN-LAST:event_lblSalirMouseClicked

    private void lblSalirMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSalirMouseEntered
        panelSalir.setBackground(Color.red);
        lblSalir.setForeground(Color.white);
    }//GEN-LAST:event_lblSalirMouseEntered

    private void lblSalirMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSalirMouseExited
        panelSalir.setBackground(Color.white);
        lblSalir.setForeground(Color.black);
    }//GEN-LAST:event_lblSalirMouseExited

    private void btnSiMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSiMouseEntered
        panelSi.setBackground(new Color(0,102,204));
    }//GEN-LAST:event_btnSiMouseEntered

    private void btnSiMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSiMouseExited
        panelSi.setBackground(new Color(51,153,255));
    }//GEN-LAST:event_btnSiMouseExited

    private void btnNoMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnNoMouseEntered
        panelNo.setBackground(new Color(0,102,204));
    }//GEN-LAST:event_btnNoMouseEntered

    private void btnNoMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnNoMouseExited
        panelNo.setBackground(new Color(51,153,255));
    }//GEN-LAST:event_btnNoMouseExited

    private void btnIniciarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnIniciarMouseEntered
        panelIniciar.setBackground(new Color(0,102,204));
    }//GEN-LAST:event_btnIniciarMouseEntered

    private void btnIniciarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnIniciarMouseExited
        panelIniciar.setBackground(new Color(51,153,255));
    }//GEN-LAST:event_btnIniciarMouseExited

    private void btnSiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSiActionPerformed
        for (int i = 0; i < Tabla1.getRowCount(); i++) {
            String cantidad = Tabla1.getValueAt(i, 2).toString();
            Tabla1.setValueAt(cantidad, i, 4);
        }
    }//GEN-LAST:event_btnSiActionPerformed

    private void btnIniciarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIniciarActionPerformed
        JFrame f = (JFrame) JOptionPane.getFrameForComponent(this);
        for (int i = 0; i < Tabla1.getRowCount(); i++) {
            Texto texto = new Texto(f,true);
            double cant;
            try{cant = Double.parseDouble(Tabla1.getValueAt(i, 2).toString());}catch(Exception e){cant = 0;}
            texto.cant = cant;
            texto.lblCodigo.setText(Tabla1.getValueAt(i, 0).toString());
            String cantidad = texto.getText();
            if(cantidad != null){
                Tabla1.setValueAt(cantidad, i, 4);
            }else{
                i = Tabla1.getRowCount();
            }
        }
    }//GEN-LAST:event_btnIniciarActionPerformed

    private void btnNoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNoActionPerformed
        for (int i = 0; i < Tabla1.getRowCount(); i++) {
            Tabla1.setValueAt("0", i, 4);
        }
    }//GEN-LAST:event_btnNoActionPerformed

    private void btnTerminarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnTerminarMouseEntered
        panelTerminar.setBackground(new Color(0,102,0));
    }//GEN-LAST:event_btnTerminarMouseEntered

    private void btnTerminarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnTerminarMouseExited
        panelTerminar.setBackground(new Color(51,204,0));
    }//GEN-LAST:event_btnTerminarMouseExited

    private void btnTerminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTerminarActionPerformed
        if(verificarCantidades()){
            JOptionPane.showMessageDialog(this, "Debes llenar correctamente las cantidades", "Advertencia", JOptionPane.WARNING_MESSAGE);
        }else{
            try{
                Connection con;
                Conexion con1 = new Conexion();
                con = con1.getConnection();
                Statement st = con.createStatement();
                String sql = "insert into cantidad_requisiciones (Cantidad, Requisicion, Codigo, Almacenista, Fecha,Ubicacion) values(?,?,?,?,?,?)";
                PreparedStatement pst = con.prepareStatement(sql);
                
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date d = new Date();
                String fecha = sdf.format(d);
                int n = 0;
                
                Stack<String> pila = new Stack<>();
                Stack<String> id = new Stack<>();
                for (int i = 0; i < Tabla1.getRowCount(); i++) {
                    String cantidad;
                    try{cantidad = Tabla1.getValueAt(i, 4).toString();}catch(Exception e){cantidad = "";}
                    String cantidadTabla;
                    try{cantidadTabla = Tabla1.getValueAt(i, 2).toString();}catch(Exception e){cantidadTabla = "";}
                    String codigo;
                    try{codigo = Tabla1.getValueAt(i, 0).toString();}catch(Exception e){codigo = "";}
                    String ubicacion;
                    try{ubicacion = Tabla1.getValueAt(i, 5).toString();}catch(Exception e){ubicacion = "";}
                    String ide;
                    try{ide = Tabla1.getValueAt(i, 6).toString();}catch(Exception e){ide = "";}
                    
                    if(!cantidad.equals("0")){
                        pila.push(codigo);
                        id.push(ide);
                    }
                    
                    pst.setString(1, cantidad);
                    pst.setString(2, lblRequisicion.getText());
                    pst.setString(3, codigo);
                    pst.setString(4, numEmpleado);
                    pst.setString(5, fecha);
                    pst.setString(6, ubicacion);
                    
                    n += pst.executeUpdate();
                    
                    actualizarInventario(codigo, cantidad, con);
                    actualizarRequisiciones(lblRequisicion.getText(), codigo, cantidad, con,cantidadTabla);
                }
                
                if(n <= 0){
                    JOptionPane.showMessageDialog(this, "Datos no guardados");
                }
                
                String sql2 = "update requisicion set LiberacionAlmacen = ? ";
                boolean band = verificarTabla();
                if(band){
                    sql2 += ", Progreso = ? ";
                }
                sql2 += "where Id = ?";
                PreparedStatement pst2 = con.prepareStatement(sql2);
                
                pst2.setBoolean(1, true);
                if(band){
                    pst2.setString(2, "LLEGO, COMPLETO");
                    pst2.setString(3, lblRequisicion.getText());
                }else{
                    pst2.setString(2, lblRequisicion.getText());
                }
                
                int n2 = pst2.executeUpdate();
                
                if(n2 > 0){
                    JOptionPane.showMessageDialog(this, "DATOS GUARDADOS");
                    limpiarTabla();
                    limpiarBotones();
                    addBotonesPedido();
                    JFrame f = (JFrame) JOptionPane.getFrameForComponent(this);
                    guardar guardar = new guardar(f,true,pila,lblRequisicion.getText(), id);
                    guardar.setLocationRelativeTo(f);
                    guardar.setVisible(true);
                }
                
            }catch(SQLException e){
                JOptionPane.showMessageDialog(this, "Error: "+e,"error",JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_btnTerminarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PanelPedidos;
    private javax.swing.JTable Tabla1;
    private javax.swing.JButton btnIniciar;
    private javax.swing.JButton btnNo;
    private javax.swing.JButton btnSi;
    private javax.swing.JButton btnTerminar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JPanel jPanel1;
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
    private javax.swing.JLabel lblRequisicion;
    private javax.swing.JLabel lblSalir;
    private javax.swing.JPanel pan;
    private javax.swing.JPanel panelIniciar;
    private javax.swing.JPanel panelNo;
    private javax.swing.JPanel panelSalir;
    private javax.swing.JPanel panelSi;
    private javax.swing.JPanel panelTerminar;
    // End of variables declaration//GEN-END:variables

    @Override
    public void actionPerformed(ActionEvent e) {
        for (int i = 0; i < btnPedido.size(); i++) {
            if(e.getSource() == btnPedido.get(i)){
                lblRequisicion.setText(requi.get(i));
                verRequisicion(requi.get(i));
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
        
    }

    @Override
    public void mouseExited(MouseEvent e) {
        
    }
}
