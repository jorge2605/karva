package VentanaEmergente.inventarioNew;

import Conexiones.Conexion;
import com.mxrck.autocompleter.TextAutoCompleter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Stack;
import javax.swing.JOptionPane;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import pruebas.InventarioNew;

public class infoInventario extends javax.swing.JDialog {

    Stack<String> proyectos;
    TextAutoCompleter au;
    public final int RESTA = 1;
    public final int SUMA = 2;
    public final int ACTUALIZAR = 3;
    
    private static class DoubleFilter extends DocumentFilter {
        @Override
        public void insertString(DocumentFilter.FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
            String newStr = fb.getDocument().getText(0, fb.getDocument().getLength()) + string;

            try {
                Double.parseDouble(newStr);
                super.insertString(fb, offset, string, attr);
            } catch (NumberFormatException e) {
                // Ignorar la inserción si no es un número double válido
            }
        }

        @Override
        public void replace(DocumentFilter.FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
            String newStr = fb.getDocument().getText(0, fb.getDocument().getLength()) + text;

            try {
                Double.parseDouble(newStr);
                super.replace(fb, offset, length, text, attrs);
            } catch (NumberFormatException e) {
                // Ignorar la sustitución si no es un número double válido
            }
        }
    }
    
    public final void addProyectos(){
        au = new TextAutoCompleter(txtProyecto);
        for (int i = 0; i < proyectos.size(); i++) {
            au.addItem(proyectos.get(i));
        }
        au.addItem("TALLER");
    }
    
    public final void addPrecio(String codigo){
        try{
            Connection con;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            String sql = "select Precio, Codigo, Proveedor from requisiciones where Codigo like '"+codigo+"'";
            ResultSet rs = st.executeQuery(sql);
            String proveedor = null;
            while(rs.next()){
                txtPrecio.setText(rs.getString("Precio"));
                proveedor = rs.getString("Proveedor");
            }
            String sql2 = "select * from registroprov_compras where Nombre like '"+proveedor+"'";
            Statement st2 = con.createStatement();
            ResultSet rs2 = st2.executeQuery(sql2);
            while(rs2.next()){
                txtPrecio.setText(txtPrecio.getText() + " " + rs2.getString("Moneda"));
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "ERROR: "+e,"ERROR",JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void transaccionBD(String codigo, int transaccion, double cantA){
        try{
            Connection con;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            String sql = "select * from inventario where NumeroDeParte like '"+codigo+"'";
            ResultSet rs = st.executeQuery(sql);
            double cant = 0;
            while(rs.next()){
                try{cant = Double.parseDouble(rs.getString("Cantidad"));}catch(Exception e){cant = 0;}
            }
            switch (transaccion) {
                case RESTA:
                    cant = cant - cantA;
                    break;
                case SUMA:
                    cant = cant + cantA;
                    break;
                default:
                    cant = cantA;
                    break;
            }
            if(transaccion == ACTUALIZAR){
                String sql2 = "update inventario set Cantidad = ?, Ubicacion = ? where NumeroDeParte = ?";
                PreparedStatement pst = con.prepareStatement(sql2);

                pst.setString(1, String.valueOf(cant));
                pst.setString(2, txtLocalizacion.getText());
                pst.setString(3, codigo);

                int n = pst.executeUpdate();

                if(n < 1){
                    JOptionPane.showMessageDialog(this, "DATOS NO GUARDADOS","ERROR",JOptionPane.ERROR_MESSAGE);
                }
            }else{
                String sql2 = "update inventario set Cantidad = ? where NumeroDeParte = ?";
                PreparedStatement pst = con.prepareStatement(sql2);

                pst.setString(1, String.valueOf(cant));
                pst.setString(2, codigo);

                int n = pst.executeUpdate();

                if(n < 1){
                    JOptionPane.showMessageDialog(this, "DATOS NO GUARDADOS","ERROR",JOptionPane.ERROR_MESSAGE);
                }
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "ERROR: "+e,"ERROR",JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public infoInventario(java.awt.Frame parent, boolean modal, Stack proyectos) {
        super(parent, modal);
        initComponents();
        this.proyectos = proyectos;
        addProyectos();
        ((AbstractDocument) txtCantRetirar.getDocument()).setDocumentFilter(new infoInventario.DoubleFilter());
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        lblTitulo = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtAlmacenista = new javax.swing.JTextField();
        txtEmpleado = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtCodigo = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtDescripcion = new javax.swing.JTextArea();
        txtPrecio = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtCantStock = new javax.swing.JTextField();
        lblCantidad = new javax.swing.JLabel();
        txtCantRetirar = new javax.swing.JTextField();
        txtProyecto = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtLocalizacion = new javax.swing.JFormattedTextField();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        btnGuardar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new java.awt.BorderLayout());

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));

        lblTitulo.setFont(new java.awt.Font("Lexend", 1, 18)); // NOI18N
        lblTitulo.setForeground(new java.awt.Color(0, 165, 252));
        lblTitulo.setText("Almacen");
        jPanel6.add(lblTitulo);

        jPanel5.add(jPanel6);

        jPanel1.add(jPanel5, java.awt.BorderLayout.PAGE_START);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Lexend", 1, 14)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Almacenista:");

        txtAlmacenista.setEditable(false);
        txtAlmacenista.setBackground(new java.awt.Color(255, 255, 255));
        txtAlmacenista.setFont(new java.awt.Font("Lexend", 0, 12)); // NOI18N
        txtAlmacenista.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtAlmacenista.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(153, 153, 153)));
        txtAlmacenista.setEnabled(false);

        txtEmpleado.setBackground(new java.awt.Color(255, 255, 255));
        txtEmpleado.setFont(new java.awt.Font("Lexend", 0, 12)); // NOI18N
        txtEmpleado.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtEmpleado.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(153, 153, 153)));
        txtEmpleado.setEnabled(false);
        txtEmpleado.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtEmpleadoFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtEmpleadoFocusLost(evt);
            }
        });
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

        jLabel2.setFont(new java.awt.Font("Lexend", 1, 14)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Empleado:");

        jLabel3.setFont(new java.awt.Font("Lexend", 1, 14)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Codigo:");

        txtCodigo.setEditable(false);
        txtCodigo.setBackground(new java.awt.Color(255, 255, 255));
        txtCodigo.setFont(new java.awt.Font("Lexend", 0, 12)); // NOI18N
        txtCodigo.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCodigo.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(153, 153, 153)));
        txtCodigo.setEnabled(false);

        jLabel4.setFont(new java.awt.Font("Lexend", 1, 14)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Descripcion:");

        jScrollPane1.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(102, 102, 102)));

        txtDescripcion.setEditable(false);
        txtDescripcion.setBackground(new java.awt.Color(255, 255, 255));
        txtDescripcion.setColumns(20);
        txtDescripcion.setFont(new java.awt.Font("Lexend", 0, 12)); // NOI18N
        txtDescripcion.setLineWrap(true);
        txtDescripcion.setRows(5);
        txtDescripcion.setWrapStyleWord(true);
        txtDescripcion.setEnabled(false);
        jScrollPane1.setViewportView(txtDescripcion);

        txtPrecio.setEditable(false);
        txtPrecio.setBackground(new java.awt.Color(255, 255, 255));
        txtPrecio.setFont(new java.awt.Font("Lexend", 0, 12)); // NOI18N
        txtPrecio.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtPrecio.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(153, 153, 153)));
        txtPrecio.setEnabled(false);

        jLabel5.setFont(new java.awt.Font("Lexend", 1, 14)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Precio:");

        jLabel6.setFont(new java.awt.Font("Lexend", 1, 14)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Cantidad en stock:");

        txtCantStock.setEditable(false);
        txtCantStock.setBackground(new java.awt.Color(255, 255, 255));
        txtCantStock.setFont(new java.awt.Font("Lexend", 0, 12)); // NOI18N
        txtCantStock.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCantStock.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(153, 153, 153)));
        txtCantStock.setEnabled(false);

        lblCantidad.setFont(new java.awt.Font("Lexend", 1, 14)); // NOI18N
        lblCantidad.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblCantidad.setText("Cantidad a retirar:");

        txtCantRetirar.setBackground(new java.awt.Color(255, 255, 255));
        txtCantRetirar.setFont(new java.awt.Font("Lexend", 0, 12)); // NOI18N
        txtCantRetirar.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCantRetirar.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(153, 153, 153)));
        txtCantRetirar.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCantRetirarFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCantRetirarFocusLost(evt);
            }
        });

        txtProyecto.setBackground(new java.awt.Color(255, 255, 255));
        txtProyecto.setFont(new java.awt.Font("Lexend", 0, 12)); // NOI18N
        txtProyecto.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtProyecto.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(153, 153, 153)));
        txtProyecto.setEnabled(false);
        txtProyecto.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtProyectoFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtProyectoFocusLost(evt);
            }
        });
        txtProyecto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtProyectoMouseClicked(evt);
            }
        });
        txtProyecto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtProyectoActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Lexend", 1, 14)); // NOI18N
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("Proyecto:");

        jLabel9.setFont(new java.awt.Font("Lexend", 1, 14)); // NOI18N
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("Localizacion:");

        txtLocalizacion.setBackground(new java.awt.Color(255, 255, 255));
        txtLocalizacion.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(153, 153, 153)));
        try {
            txtLocalizacion.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("U-#-#")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txtLocalizacion.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtLocalizacion.setFont(new java.awt.Font("Lexend", 0, 14)); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtLocalizacion)
                    .addComponent(txtCodigo)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtPrecio)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtProyecto)
                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtAlmacenista)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 277, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtEmpleado)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtCantStock)
                            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 259, Short.MAX_VALUE))
                        .addGap(47, 47, 47)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtCantRetirar)
                            .addComponent(lblCantidad, javax.swing.GroupLayout.DEFAULT_SIZE, 260, Short.MAX_VALUE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtAlmacenista, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(lblCantidad))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCantStock, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCantRetirar, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtProyecto, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtLocalizacion, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 25, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel2, java.awt.BorderLayout.CENTER);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jPanel4.setBackground(new java.awt.Color(0, 102, 204));
        jPanel4.setPreferredSize(new java.awt.Dimension(200, 35));

        btnGuardar.setFont(new java.awt.Font("Lexend", 1, 18)); // NOI18N
        btnGuardar.setForeground(new java.awt.Color(255, 255, 255));
        btnGuardar.setText("Guardar");
        btnGuardar.setBorder(null);
        btnGuardar.setBorderPainted(false);
        btnGuardar.setContentAreaFilled(false);
        btnGuardar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnGuardar.setFocusPainted(false);
        btnGuardar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });
        jPanel4.add(btnGuardar);

        jPanel3.add(jPanel4);

        jPanel1.add(jPanel3, java.awt.BorderLayout.PAGE_END);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtEmpleadoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtEmpleadoMouseClicked
        txtEmpleado.setText("");
        txtEmpleado.setEnabled(true);
    }//GEN-LAST:event_txtEmpleadoMouseClicked

    private void txtEmpleadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEmpleadoActionPerformed
        try{
            Connection con;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            String slq = "select * from registroempleados where NumEmpleado like '"+txtEmpleado.getText()+"'";
            ResultSet rs = st.executeQuery(slq);
            String empleado = null;
            while(rs.next()){
                empleado = rs.getString("Nombre") + " " + rs.getString("Apellido");
            }
            if(empleado != null){
                txtEmpleado.setText(empleado);
                txtEmpleado.setEnabled(false);
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "ERROR: "+e,"ERROR",JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_txtEmpleadoActionPerformed

    private void txtEmpleadoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtEmpleadoFocusGained
        txtEmpleado.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 3, 0, new java.awt.Color(0,102,204)));
    }//GEN-LAST:event_txtEmpleadoFocusGained

    private void txtEmpleadoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtEmpleadoFocusLost
        txtEmpleado.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(153, 153, 153)));
    }//GEN-LAST:event_txtEmpleadoFocusLost

    private void txtCantRetirarFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCantRetirarFocusGained
        txtCantRetirar.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 3, 0, new java.awt.Color(0,102,204)));
    }//GEN-LAST:event_txtCantRetirarFocusGained

    private void txtCantRetirarFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCantRetirarFocusLost
        txtCantRetirar.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(153, 153, 153)));
    }//GEN-LAST:event_txtCantRetirarFocusLost

    private void txtProyectoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtProyectoFocusGained
        txtProyecto.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 3, 0, new java.awt.Color(0,102,204)));
    }//GEN-LAST:event_txtProyectoFocusGained

    private void txtProyectoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtProyectoFocusLost
        txtProyecto.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(153, 153, 153)));
    }//GEN-LAST:event_txtProyectoFocusLost

    private void txtProyectoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtProyectoActionPerformed
        if(proyectos.contains(txtProyecto.getText())){
            txtProyecto.setEnabled(false);
        }
    }//GEN-LAST:event_txtProyectoActionPerformed

    private void txtProyectoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtProyectoMouseClicked
        txtProyecto.setText("");
        txtProyecto.setEnabled(true);
    }//GEN-LAST:event_txtProyectoMouseClicked

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        try{
            Connection con;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            if(txtCantRetirar.getText().equals("")){
                    JOptionPane.showMessageDialog(this, "Debes llenar el campo de Cantidad","Advertencia",JOptionPane.WARNING_MESSAGE);
            }else{
                if(lblTitulo.getText().equals("Salida")){
                    if(txtEmpleado.getText().equals("")){
                        JOptionPane.showMessageDialog(this, "Debes llenar el campo de Empleado","Advertencia",JOptionPane.WARNING_MESSAGE);
                    }else if(txtProyecto.getText().equals("")){
                        JOptionPane.showMessageDialog(this, "Debes llenar el campo de Proyecto","Advertencia",JOptionPane.WARNING_MESSAGE);
                    }else{
                        String sql = "insert into pedidos(Almacenista, NumParte, Cantidad, Proyecto, Requisitor, FechaSalida) values(?,?,?,?,?,?)";
                        PreparedStatement pst = con.prepareStatement(sql);

                        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
                        Date d = new Date();
                        String fecha2 = sdf2.format(d);

                        pst.setString(1, txtAlmacenista.getText());
                        pst.setString(2, txtCodigo.getText());
                        pst.setString(3, txtCantRetirar.getText());
                        pst.setString(4, txtProyecto.getText());
                        pst.setString(5, txtEmpleado.getText());
                        pst.setString(6, fecha2);

                        int n = pst.executeUpdate();

                        if(n > 0){
                            transaccionBD(txtCodigo.getText(), RESTA, Double.parseDouble(txtCantRetirar.getText()));
                            JOptionPane.showMessageDialog(this, "Datos guardados en Salida de material");
                            dispose();
                        }
                    }
                }else if(lblTitulo.getText().equals("Entrada")){
                    String sql = "insert into entrada(Almacenista, NumParte, Cantidad, FechaEntrada) values(?,?,?,?)";
                    PreparedStatement pst = con.prepareStatement(sql);

                    SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
                    Date d = new Date();
                    String fecha2 = sdf2.format(d);

                    pst.setString(1, txtAlmacenista.getText());
                    pst.setString(2, txtCodigo.getText());
                    pst.setString(3, txtCantRetirar.getText());
                    pst.setString(4, fecha2);

                    int n = pst.executeUpdate();

                    if(n > 0){
                        transaccionBD(txtCodigo.getText(), SUMA, Double.parseDouble(txtCantRetirar.getText()));
                        JOptionPane.showMessageDialog(this, "Datos guardados en Entrada de material");
                        dispose();
                    }
                }else if(lblTitulo.getText().equals("Actualizar")){
                    String sql = "insert into actualizar(Almacenista, NumParte, Cantidad, FechaActualizacion) values(?,?,?,?)";
                    PreparedStatement pst = con.prepareStatement(sql);

                    SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
                    Date d = new Date();
                    String fecha2 = sdf2.format(d);

                    pst.setString(1, txtAlmacenista.getText());
                    pst.setString(2, txtCodigo.getText());
                    pst.setString(3, txtCantRetirar.getText());
                    pst.setString(4, fecha2);

                    int n = pst.executeUpdate();

                    if(n > 0){
                        transaccionBD(txtCodigo.getText(), ACTUALIZAR, Double.parseDouble(txtCantRetirar.getText()));
                        JOptionPane.showMessageDialog(this, "Datos guardados en Actualizacion de material");
                        dispose();
                    }
                }else{
                    JOptionPane.showMessageDialog(this, "Error al seleccionar estado","ERROR",JOptionPane.ERROR_MESSAGE);
                }
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "ERROR: "+e,"ERROR",JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnGuardarActionPerformed

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
            java.util.logging.Logger.getLogger(infoInventario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(infoInventario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(infoInventario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(infoInventario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                infoInventario dialog = new infoInventario(new javax.swing.JFrame(), true,null);
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
    public javax.swing.JButton btnGuardar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    public javax.swing.JLabel lblCantidad;
    public javax.swing.JLabel lblTitulo;
    public javax.swing.JTextField txtAlmacenista;
    public javax.swing.JTextField txtCantRetirar;
    public javax.swing.JTextField txtCantStock;
    public javax.swing.JTextField txtCodigo;
    public javax.swing.JTextArea txtDescripcion;
    public javax.swing.JTextField txtEmpleado;
    private javax.swing.JFormattedTextField txtLocalizacion;
    public javax.swing.JTextField txtPrecio;
    public javax.swing.JTextField txtProyecto;
    // End of variables declaration//GEN-END:variables
}
