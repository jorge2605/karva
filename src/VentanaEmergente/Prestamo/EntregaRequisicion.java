package VentanaEmergente.Prestamo;

import Conexiones.Conexion;
import Conexiones.ConexionChat;
import VentanaEmergente.EntregaRequisicion.EntregaCantidad;
import com.app.sockets.chat.Cliente;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;
import pruebas.Inicio1;

public class EntregaRequisicion extends java.awt.Dialog implements ActionListener{
    JButton botones[];
    JPanel panel[];
    Articulos ar;
    String id[];
    String quantity[];
    String requi[], aviso[];
    int con = 0;
    String numEmpleado;

    public void verEmpleados(){
        try{
            Connection con = null;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            for (int i = 0; i < this.con; i++) {
                Statement st = con.createStatement();
                String sql = "select * from Requisicion where Id like '"+requi[i]+"'";
                ResultSet rs = st.executeQuery(sql);
                while(rs.next()){
                    aviso[i] = rs.getString("NumeroEmpleado");
                }
            }

        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "ERROR: "+e,"ERROR",JOptionPane.ERROR_MESSAGE);
        }
    }

    public void crearNotificacion(String folio,String numEmpleado){
        verEmpleados();
        try{
            Connection con = null;
            ConexionChat con1 = new ConexionChat();
            con = con1.getConnection();
            
            Connection con2 = null;
            Conexion con3 = new Conexion();
            con2 = con3.getConnection();
            
            for (int i = 0; i < aviso.length; i++) {
              Statement st = con.createStatement();
            Date d = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            String FEC = sdf.format(d);
            String sql2 = "select * from registroempleados where NumEmpleado like '"+aviso[i]+"'";
            Statement st2 = con2.prepareCall(sql2);
            ResultSet rs2 = st2.executeQuery(sql2);
            String ip;
            int port;
            String empleado;
            while(rs2.next()){
                ip = rs2.getString("Ip");
                port = rs2.getInt("Puerto");
                empleado = rs2.getString("NumEmpleado");
                
                
                String not = "noti"+empleado;
                String sql = "insert into "+not+" (Departamento,Titulo,Texto,Fecha) values (?,?,?,?)";
                PreparedStatement pst = con.prepareStatement(sql);
                pst.setString(1, "3");
                pst.setString(2, "ENTREGA DE REQUISICION");
                pst.setString(3, "ENTREGA DE MATERIAL CON EL FOLIO: *"+folio+"*, CLIC PARA MAS DETALLES");
                pst.setString(4, FEC);

                pst.executeUpdate();
                Cliente cliente = new Cliente(port+1, "ENTREGA DE REQUISICION",ip);
                Thread hilo = new Thread(cliente);
                hilo.start();
            }  
            }
            
            
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "ERROR: "+e,"ERROR",JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(Inicio1.class.getName()).log(Level.SEVERE,null,e);
        }   catch (ClassNotFoundException ex) {
                Logger.getLogger(EntregaRequisicion.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
        
    public boolean aut(){
        boolean aux = false;
        try{
            Connection con = null;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            String sql = "select * from requisicion where Id like '"+txtRequisicion.getText()+"'";
            ResultSet rs = st.executeQuery(sql);
            String req = "";
            while(rs.next()){
                req = rs.getString("Id");
            }
            if(req == null){
               aux = false; 
            }else if(req.equals("")){
                aux = false;
            }else{
                aux = true;
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "ERROR: "+e, "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        return aux;
    }
        
    public boolean check(){
        boolean aux = false;
        
        for (int i = 0; i < con; i++) {
            if(txtRequisicion.getText().equals(requi[i])){
                aux = true;
            }
        }
        
        return aux;
    }
        
    public String addRequi(){
        String aux = "";
        for (int i = 0; i < con; i++) {
            if(i == 0){
                aux = requi[0];
            }else{
                aux = aux + ","+requi[i];
            }
        }
        return aux;
    }    
    
    public void limpiarTabla(){
        Tabla1.setFont(new java.awt.Font("Roboto", 0, 12));
        Tabla1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
            },
            new String [] {
                "ID", "CANTIDAD", "DESCRIPCION", "NUMERO DE PARTE", "COMENTARIOS", "SELECCIONAR", "CANT. ENTR.", "CANTIDAD ENT."
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.String.class, java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, true, true, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        if (Tabla1.getColumnModel().getColumnCount() > 0) {
            Tabla1.getColumnModel().getColumn(0).setMinWidth(100);
            Tabla1.getColumnModel().getColumn(0).setPreferredWidth(100);
            Tabla1.getColumnModel().getColumn(0).setMaxWidth(100);
            Tabla1.getColumnModel().getColumn(1).setMinWidth(100);
            Tabla1.getColumnModel().getColumn(1).setPreferredWidth(100);
            Tabla1.getColumnModel().getColumn(1).setMaxWidth(100);
        }
    }
    
    public String getTablaNull(Object ret){
        try{
           return ret.toString();
        }catch(Exception e){
            return "0";
        }
    }
    
    public EntregaRequisicion(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        Tabla1.getTableHeader().setFont(new Font("Roboto", Font.PLAIN, 14));
        Tabla1.getTableHeader().setOpaque(false);
        Tabla1.getTableHeader().setBackground(new Color(0, 0, 0, 0));
        Tabla1.getTableHeader().setForeground(Color.black);
        Tabla1.setRowHeight(25);
        Tabla1.setShowGrid(false);
        requi = new String[10];
        aviso = new String[10];
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        txtProyecto = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        txtRequisicion = new javax.swing.JTextField();
        jPanel8 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        txtEmpleado = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        fecha = new com.toedter.calendar.JDateChooser();
        jScrollPane1 = new javax.swing.JScrollPane();
        Tabla1 = new javax.swing.JTable();
        lblRequisiciones = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jPanel9 = new javax.swing.JPanel();
        btnGuardar = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        btnLimpiar = new javax.swing.JButton();

        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                closeDialog(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new java.awt.BorderLayout());

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jLabel9.setFont(new java.awt.Font("Lexend", 1, 24)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 165, 252));
        jLabel9.setText("Entrega de requisiciones");
        jPanel2.add(jLabel9);

        jPanel1.add(jPanel2, java.awt.BorderLayout.PAGE_START);

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setLayout(new java.awt.BorderLayout());

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 0, 0, new java.awt.Color(204, 204, 204)), "BUSCAR POR:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Roboto", 0, 14)), "", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Roboto", 0, 18))); // NOI18N

        jLabel3.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        jLabel3.setText("PROYECTO:");
        jPanel5.add(jLabel3);

        txtProyecto.setBackground(new java.awt.Color(255, 255, 255));
        txtProyecto.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtProyecto.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        txtProyecto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtProyectoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(txtProyecto, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(txtProyecto, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel5.add(jPanel6);

        jLabel4.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        jLabel4.setText("NO. REQUISICION:");
        jPanel5.add(jLabel4);

        txtRequisicion.setBackground(new java.awt.Color(255, 255, 255));
        txtRequisicion.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtRequisicion.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        txtRequisicion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtRequisicionActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addComponent(txtRequisicion, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addComponent(txtRequisicion, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel5.add(jPanel7);

        jPanel4.add(jPanel5, java.awt.BorderLayout.PAGE_START);

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));

        jLabel6.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        jLabel6.setText("NO. EMPLEADO");

        txtEmpleado.setBackground(new java.awt.Color(255, 255, 255));
        txtEmpleado.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        txtEmpleado.setForeground(new java.awt.Color(0, 0, 255));
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

        jLabel7.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        jLabel7.setText("FECHA");

        fecha.setBackground(new java.awt.Color(255, 255, 255));
        fecha.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N

        Tabla1.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        Tabla1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "CANTIDAD", "DESCRIPCION", "NUMERO DE PARTE", "COMENTARIOS", "SELECCIONAR", "CANT. ENT.", "CANTIDAD ENT."
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.String.class, java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, true, true, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        Tabla1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Tabla1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(Tabla1);
        if (Tabla1.getColumnModel().getColumnCount() > 0) {
            Tabla1.getColumnModel().getColumn(0).setMinWidth(100);
            Tabla1.getColumnModel().getColumn(0).setPreferredWidth(100);
            Tabla1.getColumnModel().getColumn(0).setMaxWidth(100);
            Tabla1.getColumnModel().getColumn(1).setMinWidth(100);
            Tabla1.getColumnModel().getColumn(1).setPreferredWidth(100);
            Tabla1.getColumnModel().getColumn(1).setMaxWidth(100);
        }

        lblRequisiciones.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        lblRequisiciones.setText("REQUISICIONES AGREGADAS");

        jButton1.setBackground(new java.awt.Color(255, 255, 255));
        jButton1.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        jButton1.setText("ELIMINAR");
        jButton1.setBorder(null);
        jButton1.setBorderPainted(false);
        jButton1.setContentAreaFilled(false);
        jButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jButton1.setFocusPainted(false);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtEmpleado)
                            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 475, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(fecha, javax.swing.GroupLayout.DEFAULT_SIZE, 476, Short.MAX_VALUE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblRequisiciones, javax.swing.GroupLayout.PREFERRED_SIZE, 432, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addGap(8, 8, 8)
                                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtEmpleado, javax.swing.GroupLayout.DEFAULT_SIZE, 96, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel8Layout.createSequentialGroup()
                        .addGap(38, 38, 38)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(fecha, javax.swing.GroupLayout.DEFAULT_SIZE, 87, Short.MAX_VALUE)
                            .addComponent(lblRequisiciones, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 448, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel4.add(jPanel8, java.awt.BorderLayout.CENTER);

        jPanel1.add(jPanel4, java.awt.BorderLayout.CENTER);

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));

        btnGuardar.setBackground(new java.awt.Color(255, 255, 255));
        btnGuardar.setFont(new java.awt.Font("Roboto", 1, 16)); // NOI18N
        btnGuardar.setForeground(new java.awt.Color(0, 102, 204));
        btnGuardar.setText("GUARDAR");
        btnGuardar.setBorder(null);
        btnGuardar.setBorderPainted(false);
        btnGuardar.setContentAreaFilled(false);
        btnGuardar.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnGuardar.setFocusPainted(false);
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });
        jPanel9.add(btnGuardar);

        jLabel2.setText("               ");
        jPanel9.add(jLabel2);

        btnLimpiar.setBackground(new java.awt.Color(255, 255, 255));
        btnLimpiar.setFont(new java.awt.Font("Roboto", 1, 16)); // NOI18N
        btnLimpiar.setForeground(new java.awt.Color(204, 204, 204));
        btnLimpiar.setText("LIMPIAR TABLA");
        btnLimpiar.setBorder(null);
        btnLimpiar.setBorderPainted(false);
        btnLimpiar.setContentAreaFilled(false);
        btnLimpiar.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnLimpiar.setFocusPainted(false);
        btnLimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimpiarActionPerformed(evt);
            }
        });
        jPanel9.add(btnLimpiar);

        jPanel1.add(jPanel9, java.awt.BorderLayout.SOUTH);

        add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void closeDialog(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_closeDialog
        setVisible(false);
        dispose();
    }//GEN-LAST:event_closeDialog

    private void txtProyectoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtProyectoActionPerformed
        try{
            limpiarTabla();
            DefaultTableModel miModelo = (DefaultTableModel) Tabla1.getModel();
            Connection con = null;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            String sql = "select * from requisiciones where Proyecto like '"+txtProyecto.getText()+"%'";
            ResultSet rs = st.executeQuery(sql);
            String datos[] = new String[15];
            while(rs.next()){
                datos[0] = rs.getString("Id");
                datos[1] = rs.getString("Cantidad");
                datos[2] = rs.getString("Descripcion");
                datos[3] = rs.getString("Codigo");
                datos[8] = rs.getString("Folio");
                datos[7] = rs.getString("Folio");
                if(datos[7] == null){
                    miModelo.addRow(datos);
                }
                
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "ERROR: "+e,"ERROR",JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_txtProyectoActionPerformed

    private void txtRequisicionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtRequisicionActionPerformed
        
        if(check() == true){
            JOptionPane.showMessageDialog(this, "ESTA REQUISICION YA ESTA EN LA LISTA","ADVERTENCIA",JOptionPane.WARNING_MESSAGE);
        }else if(con == 9){
            JOptionPane.showMessageDialog(this, "EXEDISTE EL NUMERO DE REQUISICIONES","ADVERTENCIA",JOptionPane.WARNING_MESSAGE);
        }else if(aut() == false){
            JOptionPane.showMessageDialog(this, "ESTA REQUISICION NO EXISTE","ADVERTENCIA",JOptionPane.WARNING_MESSAGE);
        }else{
        con++;
        requi[con-1] = txtRequisicion.getText();
        lblRequisiciones.setText(addRequi());
        try{
            
            DefaultTableModel miModelo = (DefaultTableModel) Tabla1.getModel();
            Connection con = null;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            String sql = "select * from requisiciones where NumRequisicion like '"+txtRequisicion.getText()+"'";
            ResultSet rs = st.executeQuery(sql);
            String datos[] = new String[15];
            while(rs.next()){
                datos[0] = rs.getString("Id");
                datos[1] = rs.getString("Cantidad");
                datos[7] = rs.getString("CantidadEntregada");
                datos[2] = rs.getString("Descripcion");
                datos[3] = rs.getString("Codigo");
                datos[8] = rs.getString("Folio");
                double cant = 0;
                try{
                    double can1 = Double.parseDouble(datos[1]);
                    double can2 = Double.parseDouble(datos[7]);
                    cant = can1 - can2;
                }catch(Exception e){
                    
                }
                if(datos[8] == null || cant != 0){
                    miModelo.addRow(datos);
                }
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "ERROR: "+e,"ERROR",JOptionPane.ERROR_MESSAGE);
        }
        }
    }//GEN-LAST:event_txtRequisicionActionPerformed

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

    private void Tabla1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Tabla1MouseClicked
        if(Tabla1.getSelectedColumn() == 5){
            if(Tabla1.getValueAt(Tabla1.getSelectedRow(), 5).equals(true)){
                JFrame f = (JFrame) JOptionPane.getFrameForComponent(this);
                String cantEntregada = getTablaNull(Tabla1.getValueAt(Tabla1.getSelectedRow(), 7));
                String cant = getTablaNull(Tabla1.getValueAt(Tabla1.getSelectedRow(), 1));
                EntregaCantidad entrega = new EntregaCantidad(f,true, cant, cantEntregada);
                entrega.setLocationRelativeTo(f);
                double cantidad = entrega.getCantidad();
                Tabla1.setValueAt(cantidad, Tabla1.getSelectedRow(), 6);
            }else{
                Tabla1.setValueAt("", Tabla1.getSelectedRow(), 6);
            }
        }
    }//GEN-LAST:event_Tabla1MouseClicked

    private void txtEmpleadoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtEmpleadoMouseClicked
        if(evt.getClickCount() == 2){
            txtEmpleado.setEditable(true);
            txtEmpleado.setFont(new java.awt.Font("Roboto", 0, 12));
            txtEmpleado.setForeground(Color.black);
            txtEmpleado.setText("");
        }
    }//GEN-LAST:event_txtEmpleadoMouseClicked

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        if(txtEmpleado.getText().equals("")){
            JOptionPane.showMessageDialog(this, "DEBES LLENAR EL NUMERO DE EMPLEADO","ADVERTENCIA",JOptionPane.WARNING_MESSAGE);
        }else if(fecha.getDate() == null){
                        JOptionPane.showMessageDialog(this, "DEBES LLENAR FECHA DE RECIBO","ADVERTENCIA",JOptionPane.WARNING_MESSAGE);
        }else{
        JFrame f = (JFrame) JOptionPane.getFrameForComponent(this);
        ar = new Articulos(f,true);
        ar.btnGuardar.addActionListener(this);
        int cont = 0;
        for (int i = 0; i < Tabla1.getRowCount(); i++) {
            if(Tabla1.getValueAt(i, 5) != null){
                if(Tabla1.getValueAt(i, 5).equals(true)){
                    cont++;
                }
            }
        }
        
        botones = new JButton[cont];
        panel = new JPanel[cont];
        id = new String[cont];
        quantity = new String[cont];
        int aux = 0;
        for (int i = 0; i < Tabla1.getRowCount(); i++) {
            if(Tabla1.getValueAt(i, 5) != null){
                if(Tabla1.getValueAt(i, 5).equals(true)){
                    panel[aux] = new JPanel();
                    botones[aux] = new JButton(String.valueOf(
                            "<html>"
                           + "<b><font SIZE = 5><p>"+Tabla1.getValueAt(i, 3).toString()+"</p></font></b>"
                           + "<b><font SIZE = 2><p>"+Tabla1.getValueAt(i, 0).toString()+"</p></font></b>"
                           + "<b><font SIZE = 2><p>"+Tabla1.getValueAt(i, 1).toString()+"</p></font></b>"
                           + "<b><font SIZE = 3><p>"+Tabla1.getValueAt(i, 6).toString()+"</p></font></b>"
                            + "</html>"));
                    id[aux] = Tabla1.getValueAt(i, 0).toString();
                    quantity[aux] = Tabla1.getValueAt(i, 1).toString();
                    botones[aux].addActionListener(this);
                    botones[aux].setBackground(new java.awt.Color(255, 255, 255));
                    botones[aux].setFont(new java.awt.Font("Roboto", 0, 16));
                    botones[aux].setBorder(null);
                    botones[aux].setName(Tabla1.getValueAt(i, 6).toString() + "*" + getTablaNull(Tabla1.getValueAt(i, 7)));
                    botones[aux].setBorderPainted(false);
                    botones[aux].setContentAreaFilled(false);
                    botones[aux].setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
                    botones[aux].setFocusPainted(false);

                    if(aux%2 == 0){
                        panel[aux].setBackground(new Color(238,238,238));
                    }else{
                        panel[aux].setBackground(Color.white);
                    }
                    panel[aux].add(botones[aux]);
                    ar.PanelArticulos.add(panel[aux]);
                    aux++;
                }
            }
        }
        ar.setLocationRelativeTo(null);
        ar.setPreferredSize(new Dimension(200,600));
        ar.setVisible(true);
        }
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarActionPerformed
        limpiarTabla();
    }//GEN-LAST:event_btnLimpiarActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        lblRequisiciones.setText("REQUISICIONES AGREGADAS");
        requi = new String[10];
        con = 0;
        limpiarTabla();
    }//GEN-LAST:event_jButton1ActionPerformed

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                EntregaRequisicion dialog = new EntregaRequisicion(new java.awt.Frame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
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
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnLimpiar;
    private com.toedter.calendar.JDateChooser fecha;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblRequisiciones;
    private javax.swing.JTextField txtEmpleado;
    private javax.swing.JTextField txtProyecto;
    private javax.swing.JTextField txtRequisicion;
    // End of variables declaration//GEN-END:variables

    @Override
    public void actionPerformed(ActionEvent e) {
        for (int i = 0; i < botones.length; i++) {
            if(e.getSource() == botones[i]){
                int opc  = JOptionPane.showConfirmDialog(this, "¿ESTAS SEGURO QUE DESEAS ELIMINAR ESTE ARTICULO?");
                if(opc == 0){
                this.ar.PanelArticulos.remove(panel[i]);
                botones[i] = null;
                id[i] = null;
                this.ar.revalidate();
                this.ar.repaint();
                }
            }
        }
        
        if(ar != null){
        if(e.getSource() == this.ar.btnGuardar){
            int numero = 0;
            int n = 0, n2 = 0;
            try{
                Connection con = null;
                Conexion con1 = new Conexion();
                con = con1.getConnection();
                Statement st = con.createStatement();
                String sql = "select * from foliorequisiciones";
                ResultSet rs = st.executeQuery(sql);
                while(rs.next()){
                    numero = rs.getInt("Id");
                }
                numero++;
                
                String sql2 = "insert into foliorequisiciones (NombreEmpleado,NumRequisicion,FechaFolio, FechaSalida) values(?,?,?,?)";
                PreparedStatement pst2 = con.prepareStatement(sql2);
                        
                Date f = new Date();
                java.sql.Date date2 = new java.sql.Date(f.getTime());
                java.sql.Date date3 = new java.sql.Date(fecha.getDate().getTime());
                        
                pst2.setString(1, txtEmpleado.getText());
                pst2.setString(2, lblRequisiciones.getText());
                pst2.setDate(3, date2);
                pst2.setDate(4, date3);
                
                n2 = pst2.executeUpdate();
            }catch(SQLException ex){
                JOptionPane.showMessageDialog(this, "ERROR: "+ex,"ERROR",JOptionPane.ERROR_MESSAGE);
            }
            if(n2 > 0){
                
            crearNotificacion(String.valueOf(numero), numEmpleado);  
                
            for (int i = 0; i < panel.length; i++) {
                if(id[i] != null){
                    try{
                        Connection con;  
                        Conexion con1 = new Conexion();
                        con = con1.getConnection();
                        double cantidad = Double.parseDouble(botones[i].getName().substring(0,botones[i].getName().indexOf("*"))) + 
                                Double.parseDouble(botones[i].getName().substring(botones[i].getName().indexOf("*") + 1, botones[i].getName().length()));
                        double cant = Double.parseDouble(quantity[i]);
                        String sql = "update requisiciones set Folio = ?, CantidadEntregada = ? where Id = ?";
                        if(cant == cantidad){
                            sql = "update requisiciones set Folio = ?, CantidadEntregada = ?, Entregado = ? where Id = ?";
                        }
                        PreparedStatement pst = con.prepareStatement(sql);
                        
                        pst.setInt(1, numero);
                        pst.setString(2, String.valueOf(cantidad));
                        if(cant == cantidad){
                            pst.setString(3, "SI");
                            pst.setString(4, id[i]);
                        }else{
                            pst.setString(3, id[i]);
                        }
                        
                        n = pst.executeUpdate();
                    }catch(SQLException ex){
                        JOptionPane.showMessageDialog(this, "ERROR: "+e,"ERROR",JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
                    if(n > 0){
                    JFrame f = (JFrame) JOptionPane.getFrameForComponent(this);
                    verFolio v = new verFolio(f, true, String.valueOf(numero));
                    v.setVisible(true);
                    ar.dispose();
                    lblRequisiciones.setText("REQUISICIONES AGREGADAS");
                    requi = new String[10];
                    con = 0;
                    limpiarTabla();
                    txtEmpleado.setEditable(true);
                    txtEmpleado.setFont(new java.awt.Font("Roboto", 0, 12));
                    txtEmpleado.setForeground(Color.black);
                    txtEmpleado.setText("");
                    }
                }else{
                JOptionPane.showMessageDialog(this, "DATOS NO GUARDADOS","ERROR",JOptionPane.ERROR_MESSAGE);
            }
            }
        }
    }
}
