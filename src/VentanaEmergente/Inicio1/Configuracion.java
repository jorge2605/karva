package VentanaEmergente.Inicio1;

import Conexiones.Conexion;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import Modelo.TextPrompt;
import java.awt.Color;
import java.awt.Dimension;
import java.sql.Blob;
import java.sql.PreparedStatement;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.AuthenticationFailedException;
import javax.mail.Authenticator;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.sql.rowset.serial.SerialBlob;

public class Configuracion extends java.awt.Dialog {

    String id;
    
    public void llenarDatos(){
        try{
            Connection con = null;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            String sql = "select * from registroempleados where NumEmpleado like '"+id+"'";
            ResultSet rs = st.executeQuery(sql);
            String datos[] = new String[10];
            while(rs.next()){
                datos[0] = rs.getString("Direccion");
                datos[1] = rs.getString("Puesto");
                datos[2] = rs.getString("Telefono");
                datos[3] = rs.getString("NumEmpleado");
                datos[4] = rs.getString("Correo");
                
            }
            
            lblDireccion.setText(datos[0]);
            lblPuesto.setText(datos[1]);
            lblTelefono.setText(datos[2]);
            lblNumero.setText(datos[3]);
            lblDireccion.setEditable(false);
            lblPuesto.setEditable(false);
            lblTelefono.setEditable(false);
            lblNumero.setEditable(false);
            
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "ERROR: "+e,"ERROR",JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public Configuracion(java.awt.Frame parent, boolean modal, String Id, String nombre) {
        super(parent, modal);
        initComponents();
        this.id = Id;
        lblNombre.setText(nombre);
        lblNom.setText(nombre);
        llenarDatos();
        panelPass.setVisible(false);
        panelCorreo.setVisible(false);
        btnEditar.setPreferredSize(new Dimension(16,jPanel4.getHeight()-10));
        TextPrompt p = new TextPrompt("**********",txtAnterior);
        TextPrompt p1 = new TextPrompt("**********",txtNueva);
        TextPrompt p2 = new TextPrompt("**********",txtRepetir);
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        lblNombre = new com.bolivia.label.CLabel();
        btnEditar1 = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        lblNumero = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        lblNom = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        lblPuesto = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        lblTelefono = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        lblDireccion = new javax.swing.JTextField();
        panelPass = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtAnterior = new javax.swing.JPasswordField();
        jLabel4 = new javax.swing.JLabel();
        txtNueva = new javax.swing.JPasswordField();
        jLabel6 = new javax.swing.JLabel();
        txtRepetir = new javax.swing.JPasswordField();
        jLabel7 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        btnGuardar = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        lblCambio = new javax.swing.JLabel();
        lblCambio1 = new javax.swing.JLabel();
        panelCorreo = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        txtCorreo = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        txtPass = new javax.swing.JPasswordField();
        btnVerificar = new javax.swing.JButton();

        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                closeDialog(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new java.awt.BorderLayout());

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));

        lblNombre.setBackground(new java.awt.Color(255, 255, 255));
        lblNombre.setForeground(new java.awt.Color(255, 255, 255));
        lblNombre.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/usuario (1).png"))); // NOI18N
        jPanel4.add(lblNombre);

        btnEditar1.setBackground(new java.awt.Color(255, 255, 255));
        btnEditar1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/photo.png"))); // NOI18N
        btnEditar1.setBorder(null);
        btnEditar1.setBorderPainted(false);
        btnEditar1.setContentAreaFilled(false);
        btnEditar1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnEditar1.setFocusPainted(false);
        btnEditar1.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        btnEditar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditar1ActionPerformed(evt);
            }
        });
        jPanel4.add(btnEditar1);

        btnEditar.setBackground(new java.awt.Color(255, 255, 255));
        btnEditar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/editar (1).png"))); // NOI18N
        btnEditar.setBorder(null);
        btnEditar.setBorderPainted(false);
        btnEditar.setContentAreaFilled(false);
        btnEditar.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnEditar.setFocusPainted(false);
        btnEditar.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        btnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarActionPerformed(evt);
            }
        });
        jPanel4.add(btnEditar);

        jPanel2.add(jPanel4, java.awt.BorderLayout.PAGE_START);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setLayout(new java.awt.GridLayout(6, 2));

        jLabel1.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setText("NUMERO DE EMPLEADO:");
        jPanel3.add(jLabel1);

        lblNumero.setEditable(false);
        lblNumero.setBackground(new java.awt.Color(255, 255, 255));
        lblNumero.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        lblNumero.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        lblNumero.setBorder(null);
        jPanel3.add(lblNumero);

        jLabel3.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText("NOMBRE:");
        jPanel3.add(jLabel3);

        lblNom.setEditable(false);
        lblNom.setBackground(new java.awt.Color(255, 255, 255));
        lblNom.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        lblNom.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        lblNom.setBorder(null);
        jPanel3.add(lblNom);

        jLabel11.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel11.setText("PUESTO:");
        jPanel3.add(jLabel11);

        lblPuesto.setEditable(false);
        lblPuesto.setBackground(new java.awt.Color(255, 255, 255));
        lblPuesto.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        lblPuesto.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        lblPuesto.setBorder(null);
        jPanel3.add(lblPuesto);

        jLabel5.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel5.setText("TELEFONO:");
        jPanel3.add(jLabel5);

        lblTelefono.setEditable(false);
        lblTelefono.setBackground(new java.awt.Color(255, 255, 255));
        lblTelefono.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        lblTelefono.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        lblTelefono.setBorder(null);
        jPanel3.add(lblTelefono);

        jLabel9.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel9.setText("DIRECCION:");
        jPanel3.add(jLabel9);

        lblDireccion.setEditable(false);
        lblDireccion.setBackground(new java.awt.Color(255, 255, 255));
        lblDireccion.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        lblDireccion.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        lblDireccion.setBorder(null);
        jPanel3.add(lblDireccion);

        jPanel2.add(jPanel3, java.awt.BorderLayout.CENTER);

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 490, 480));

        panelPass.setBackground(new java.awt.Color(255, 255, 255));
        panelPass.setLayout(new java.awt.GridLayout(6, 0));

        jLabel2.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel2.setText("CONTRASEÑA ANTERIOR:");
        panelPass.add(jLabel2);

        txtAnterior.setBackground(new java.awt.Color(255, 255, 255));
        txtAnterior.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtAnterior.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        txtAnterior.setPreferredSize(new java.awt.Dimension(350, 22));
        txtAnterior.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtAnteriorActionPerformed(evt);
            }
        });
        panelPass.add(txtAnterior);

        jLabel4.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel4.setText("CONTRASEÑA NUEVA:");
        panelPass.add(jLabel4);

        txtNueva.setBackground(new java.awt.Color(255, 255, 255));
        txtNueva.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtNueva.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        txtNueva.setPreferredSize(new java.awt.Dimension(350, 22));
        panelPass.add(txtNueva);

        jLabel6.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel6.setText("REPETIR CONTRASEÑA:");
        panelPass.add(jLabel6);

        txtRepetir.setBackground(new java.awt.Color(255, 255, 255));
        txtRepetir.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtRepetir.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        txtRepetir.setPreferredSize(new java.awt.Dimension(350, 22));
        panelPass.add(txtRepetir);

        jPanel1.add(panelPass, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 60, 330, 200));

        jLabel7.setFont(new java.awt.Font("Roboto Medium", 0, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(51, 153, 255));
        jLabel7.setText("INFORMACION");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 10, -1, -1));

        jPanel6.setBackground(new java.awt.Color(0, 165, 252));

        btnGuardar.setBackground(new java.awt.Color(255, 255, 255));
        btnGuardar.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        btnGuardar.setForeground(new java.awt.Color(255, 255, 255));
        btnGuardar.setText("GUARDAR CAMBIOS");
        btnGuardar.setBorder(null);
        btnGuardar.setBorderPainted(false);
        btnGuardar.setContentAreaFilled(false);
        btnGuardar.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnGuardar.setFocusPainted(false);
        btnGuardar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnGuardarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnGuardarMouseExited(evt);
            }
        });
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel1.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 520, 150, 40));

        jLabel8.setFont(new java.awt.Font("Roboto Medium", 0, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(51, 153, 255));
        jLabel8.setText("CAMBIO DE CONTRASEÑA");
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 10, -1, -1));

        lblCambio.setFont(new java.awt.Font("Roboto", 3, 12)); // NOI18N
        lblCambio.setForeground(new java.awt.Color(0, 0, 204));
        lblCambio.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblCambio.setText("CAMBIAR CONTRASEÑA");
        lblCambio.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 204)));
        lblCambio.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        lblCambio.setDoubleBuffered(true);
        lblCambio.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblCambioMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblCambioMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblCambioMouseExited(evt);
            }
        });
        jPanel1.add(lblCambio, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 40, 140, -1));

        lblCambio1.setFont(new java.awt.Font("Roboto", 3, 12)); // NOI18N
        lblCambio1.setForeground(new java.awt.Color(0, 0, 204));
        lblCambio1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblCambio1.setText("CONFIGURAR CORREO");
        lblCambio1.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 204)));
        lblCambio1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        lblCambio1.setDoubleBuffered(true);
        lblCambio1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblCambio1MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblCambio1MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblCambio1MouseExited(evt);
            }
        });
        jPanel1.add(lblCambio1, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 290, 130, 20));

        panelCorreo.setBackground(new java.awt.Color(255, 255, 255));
        panelCorreo.setLayout(new java.awt.BorderLayout());

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setLayout(new java.awt.GridLayout(2, 2));

        jLabel10.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel10.setText("CORREO:  ");
        jPanel7.add(jLabel10);

        txtCorreo.setBackground(new java.awt.Color(255, 255, 255));
        txtCorreo.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        txtCorreo.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCorreo.setBorder(null);
        jPanel7.add(txtCorreo);

        jLabel13.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel13.setText("CONTRASEÑA:  ");
        jPanel7.add(jLabel13);

        txtPass.setBackground(new java.awt.Color(255, 255, 255));
        txtPass.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtPass.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        txtPass.setPreferredSize(new java.awt.Dimension(350, 22));
        txtPass.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPassActionPerformed(evt);
            }
        });
        jPanel7.add(txtPass);

        panelCorreo.add(jPanel7, java.awt.BorderLayout.CENTER);

        btnVerificar.setBackground(new java.awt.Color(255, 255, 255));
        btnVerificar.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        btnVerificar.setForeground(new java.awt.Color(0, 102, 204));
        btnVerificar.setText("Guardar");
        btnVerificar.setBorder(null);
        btnVerificar.setBorderPainted(false);
        btnVerificar.setContentAreaFilled(false);
        btnVerificar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnVerificar.setFocusPainted(false);
        btnVerificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVerificarActionPerformed(evt);
            }
        });
        panelCorreo.add(btnVerificar, java.awt.BorderLayout.SOUTH);

        jPanel1.add(panelCorreo, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 330, 300, 120));

        add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void closeDialog(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_closeDialog
        setVisible(false);
        dispose();
    }//GEN-LAST:event_closeDialog

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        lblTelefono.setEditable(true);
        lblDireccion.setEditable(true);
        
        lblTelefono.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        lblDireccion.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        
    }//GEN-LAST:event_btnEditarActionPerformed

    private void txtAnteriorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtAnteriorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAnteriorActionPerformed

    private void btnGuardarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGuardarMouseEntered
        Color alto = new Color(0,124,249);
        jPanel6.setBackground(alto);
    }//GEN-LAST:event_btnGuardarMouseEntered

    private void btnGuardarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGuardarMouseExited
        Color bajo = new Color(0,165,252);
        jPanel6.setBackground(bajo);
    }//GEN-LAST:event_btnGuardarMouseExited

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        
        try{
            Connection con = null;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            
            if(panelPass.isVisible()){
                String sql = "select AES_DECRYPT(Contraseña,'mi_llave'),NumEmpleado from registroempleados where NumEmpleado like '"+id+"'";
                ResultSet rs = st.executeQuery(sql);
                String contra = "";
                while(rs.next()){
                    contra = rs.getString("AES_DECRYPT(Contraseña,'mi_llave')");
                }
                if(contra.equals(txtAnterior.getText())){
                    if(txtNueva.getText().equals(txtRepetir.getText())){
                        String sql1 = "";
                        sql1 = "update registroempleados set Contraseña = AES_ENCRYPT(?,'mi_llave'), Direccion = ?, Telefono = ? where NumEmpleado = ?";
                        PreparedStatement pst = con.prepareStatement(sql1);

                        byte dato[] = txtNueva.getText().getBytes();
                        Blob blob= new SerialBlob(dato);

                        pst.setBlob(1, blob);
                        pst.setString(2, lblDireccion.getText());
                        pst.setString(3, lblTelefono.getText());
                        pst.setString(4, id);

                        int n = pst.executeUpdate();

                        if(n > 0){
                            JOptionPane.showMessageDialog(this, "DATOS GUARDADOS");
                        }else{
                         JOptionPane.showMessageDialog(this, "no se guarda");
                        }
                    }else{
                    JOptionPane.showMessageDialog(this, "LA CONTRASEÑA NO COINCIDE","ADVERTENCIA",JOptionPane.WARNING_MESSAGE);
                }
                }else{
                    JOptionPane.showMessageDialog(this, "no se guarda");
                }
            }else{
                String sql1;
                sql1 = "update registroempleados set Direccion = ?, Telefono = ? where NumEmpleado = ?";
                PreparedStatement pst = con.prepareStatement(sql1);

                pst.setString(1, lblDireccion.getText());
                pst.setString(2, lblTelefono.getText());
                pst.setString(3, id);

                int n = pst.executeUpdate();

                if(n > 0){
                    JOptionPane.showMessageDialog(this, "DATOS GUARDADOS");
                }else{
                 JOptionPane.showMessageDialog(this, "no se guarda");
                }
            }
            
           
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "ERROR: "+e,"ERROR",JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnEditar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditar1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnEditar1ActionPerformed

    private void lblCambioMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblCambioMouseEntered
        lblCambio.setForeground(new Color(0,102,255));
    }//GEN-LAST:event_lblCambioMouseEntered

    private void lblCambioMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblCambioMouseExited
        lblCambio.setForeground(new Color(0,0,204));
    }//GEN-LAST:event_lblCambioMouseExited

    private void lblCambioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblCambioMouseClicked
        if(lblCambio.getText().equals("CAMBIAR CONTRASEÑA")){
        panelPass.setVisible(true);
        lblCambio.setText("OCULTAR");
        }else{
          panelPass.setVisible(false);
            lblCambio.setText("CAMBIAR CONTRASEÑA");  
        }
    }//GEN-LAST:event_lblCambioMouseClicked

    private void lblCambio1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblCambio1MouseClicked
        if(lblCambio1.getText().equals("CONFIGURAR CORREO")){
            panelCorreo.setVisible(true);
            lblCambio1.setText("OCULTAR");
        }else{
            panelCorreo.setVisible(false);
            lblCambio1.setText("CONFIGURAR CORREO");
        }
    }//GEN-LAST:event_lblCambio1MouseClicked

    private void lblCambio1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblCambio1MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_lblCambio1MouseEntered

    private void lblCambio1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblCambio1MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_lblCambio1MouseExited

    private void txtPassActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPassActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPassActionPerformed

    private void btnVerificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVerificarActionPerformed
        String host = "mail.si3i.com"; // Ejemplo: "smtp.gmail.com" para Gmail
        int puerto = 587; // Puerto para TLS (587 para Gmail)
        String correoUsuario = txtCorreo.getText();
        String contrasena = txtPass.getText();

        Properties props = new Properties();
        props.put("mail.smtp.host", "mail.si3i.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        // Crear sesión con el servidor de correo
        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(correoUsuario, contrasena);
            }
        });

        try {
            // Intentar conectar y autenticar con el servidor de correo
            Transport transport = session.getTransport("smtp");
            transport.connect(host, puerto, correoUsuario, contrasena);
            Connection con;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            String sql = "update registroempleados set Pass = AES_ENCRYPT(?,'mi_llave'), Correo = ? where NumEmpleado = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            
            byte dato[] = txtPass.getText().getBytes();
            Blob blob= new SerialBlob(dato);

            pst.setBlob(1, blob);
            pst.setString(2, txtCorreo.getText());
            pst.setString(3, id);
            
            int n = pst.executeUpdate();
            
            if(n > 0){
                JOptionPane.showMessageDialog(this, "DATOS GUARDADOS");
            }
            transport.close();
        } catch (AuthenticationFailedException e) {
            JOptionPane.showMessageDialog(null,"Credenciales inválidas: El correo o contraseña son incorrectos.");
        } catch (MessagingException e) {
            JOptionPane.showMessageDialog(null,"Error al conectar con el servidor de correo: " + e.getMessage());
        } catch (SQLException ex) {
            Logger.getLogger(Configuracion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnVerificarActionPerformed

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Configuracion dialog = new Configuracion(new java.awt.Frame(), true,"","");
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
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnEditar1;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnVerificar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JLabel lblCambio;
    private javax.swing.JLabel lblCambio1;
    private javax.swing.JTextField lblDireccion;
    private javax.swing.JTextField lblNom;
    private com.bolivia.label.CLabel lblNombre;
    private javax.swing.JTextField lblNumero;
    private javax.swing.JTextField lblPuesto;
    private javax.swing.JTextField lblTelefono;
    private javax.swing.JPanel panelCorreo;
    private javax.swing.JPanel panelPass;
    private javax.swing.JPasswordField txtAnterior;
    private javax.swing.JTextField txtCorreo;
    private javax.swing.JPasswordField txtNueva;
    private javax.swing.JPasswordField txtPass;
    private javax.swing.JPasswordField txtRepetir;
    // End of variables declaration//GEN-END:variables
}
