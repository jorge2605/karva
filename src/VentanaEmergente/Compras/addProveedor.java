package VentanaEmergente.Compras;

import Conexiones.Conexion;
import com.mxrck.autocompleter.TextAutoCompleter;
import java.awt.Color;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

public class addProveedor extends javax.swing.JDialog {

    TextAutoCompleter au;
    
    public void completar(){
        au = new TextAutoCompleter(txtProv);
        try {
            Connection con = null;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            String sql = "select Nombre from registroprov_compras";
            String datos[] = new String[10];
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                datos[0] = rs.getString("Nombre");
                au.addItem(datos[0]);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "ERROR AL AUTOCOMPLETAR" + e, "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void guardar(){
        if(txtProv.getText().equals("")){
        JOptionPane.showMessageDialog(this,"CAMPO PROVEEDOR VACIO","ADVERTENCIA",JOptionPane.WARNING_MESSAGE);
            }else if(txtContacto.getText().equals("")){
            JOptionPane.showMessageDialog(this, "CAMPO CONTACTO VACIO","ADVERTENCIA",JOptionPane.WARNING_MESSAGE);
                }else if(txtIva.getText().equals("")){
                JOptionPane.showMessageDialog(this, "CAMPO DIRECCION VACIO","ADVERTENCIA",JOptionPane.WARNING_MESSAGE);
                    }else if(txtTelefono.getText().equals("")){
                    JOptionPane.showMessageDialog(this, "CAMPO TELEFONO VACIO","ADVERTENCIA",JOptionPane.WARNING_MESSAGE);
                        } else if(txtIva.getText().equals("")){
                    JOptionPane.showMessageDialog(this, "CAMPO IVA VACIO","ADVERTENCIA",JOptionPane.WARNING_MESSAGE);
                            }else if(cmbCondicion.getSelectedItem().toString().equals("SELECCIONAR CONDICION")){
                        JOptionPane.showMessageDialog(this, "DEBES SELECCIONAR UN CAMPO DE CONDICION DE PAGO","ADVERTENCIA",JOptionPane.WARNING_MESSAGE);
                                }else if(cmbMoneda.getSelectedItem().toString().equals("SELECCIONAR MONEDA")){
                            JOptionPane.showMessageDialog(this, "DEBES SELECCIONAR UN TIPO DE MONEDA","ADVERTENCIA",JOptionPane.WARNING_MESSAGE);
                                }   
                    else{
                        if(btnGuardar.getText().equals("Guardar")){
                            try{
                            Connection con = null;
                            Conexion con1 = new Conexion();
                            con = con1.getConnection();
                            Statement st = con.createStatement();
                            String sql = "insert into registroProv_Compras (Nombre,Contacto,Direccion,Telefono,Condiciones,"
                                    + "Iva,Moneda,Correo,Celular,CorreoCopia, Isr) values(?,?,?,?,?,?,?,?,?,?,?)";
                            PreparedStatement pst = con.prepareStatement(sql);

                            pst.setString(1, txtProv.getText());
                            pst.setString(2, txtContacto.getText());
                            pst.setString(3, txtDireccion1.getText());
                            pst.setString(4, txtTelefono.getText());
                            pst.setString(5, cmbCondicion.getSelectedItem().toString());
                            pst.setString(6, txtIva.getText());
                            pst.setString(7, cmbMoneda.getSelectedItem().toString());
                            pst.setString(8, txtCorreo.getText());
                            pst.setString(9, txtTelefonoPersonal.getText());
                            pst.setString(10, txtCorreoCopia.getText());
                            pst.setString(11, txtIsr.getText());

                            int n = pst.executeUpdate();
                            if(n > 0){
                                txtProv.setText("");
                                txtContacto.setText("");
                                txtIva.setText("");
                                txtTelefono.setText("");
                                txtCorreo.setText("");
                                txtTelefonoPersonal.setText("");
                                txtCorreoCopia.setText("");
                                txtIsr.setText("");
                                JOptionPane.showMessageDialog(this, "DATOS GUARDADOS CORRECTAMENTE");
                            }

                            }catch(SQLException e){
                            JOptionPane.showMessageDialog(this, "ERROR AL GUARDAR DATOS" + e,"ERROR",JOptionPane.ERROR_MESSAGE);
                            }
                        }else{
                            try{
                            Connection con = null;
                            Conexion con1 = new Conexion();
                            con = con1.getConnection();
                            Statement st = con.createStatement();
                            String sql = "update registroProv_Compras set Nombre = ?,Contacto = ?,Direccion = ?,Telefono = ?,Condiciones = ?,Iva = ?"
                                    + ",Moneda = ?, Correo = ?, Celular = ?, CorreoCopia = ?, Isr = ? where Nombre = ?";
                            PreparedStatement pst = con.prepareStatement(sql);

                            pst.setString(1, txtProv.getText());
                            pst.setString(2, txtContacto.getText());
                            pst.setString(3, txtDireccion1.getText());
                            pst.setString(4, txtTelefono.getText());
                            pst.setString(5, cmbCondicion.getSelectedItem().toString());
                            pst.setString(6, txtIva.getText());
                            pst.setString(7, cmbMoneda.getSelectedItem().toString());
                            pst.setString(8,txtCorreo.getText());
                            pst.setString(9,txtTelefonoPersonal.getText());
                            pst.setString(10,txtCorreoCopia.getText());
                            pst.setString(11,txtIsr.getText());
                            pst.setString(12,txtProv.getText());

                            int n = pst.executeUpdate();
                            if(n > 0){
                                txtProv.setText("");
                                txtContacto.setText("");
                                txtIva.setText("");
                                txtTelefono.setText("");
                                txtTelefonoPersonal.setText("");
                                txtCorreo.setText("");
                                txtIsr.setText("");
                                txtCorreoCopia.setText("");
                                JOptionPane.showMessageDialog(this, "DATOS ACTUALIZADOS CORRECTAMENTE");
                            }

                            }catch(SQLException e){
                            JOptionPane.showMessageDialog(this, "ERROR AL GUARDAR DATOS" + e,"ERROR",JOptionPane.ERROR_MESSAGE);
                            }
                        }
                 }
    }
    
    public void verProveedor(){
        try{
            Connection con;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            String sql = "select * from registroprov_compras where Nombre like '"+txtProv.getText()+"'";
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
                txtProv.setText(rs.getString("Nombre"));
                txtContacto.setText(rs.getString("Contacto"));
                txtDireccion1.setText(rs.getString("Direccion"));
                txtTelefono.setText(rs.getString("Telefono"));
                txtIva.setText(rs.getString("Iva"));
                txtCorreo.setText(rs.getString("Correo"));
                txtCorreoCopia.setText(rs.getString("CorreoCopia"));
                cmbCondicion.setSelectedItem(rs.getString("Condiciones"));
                cmbMoneda.setSelectedItem(rs.getString("Moneda"));
                txtIsr.setText(rs.getString("Isr"));
                txtTelefonoPersonal.setText(rs.getString("Celular"));
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "ERROR: "+e,"ERROR",JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public addProveedor(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        completar();
        this.setBackground(new Color(0,0,0,0));
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        panelRound1 = new scrollPane.PanelRound();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        panelX = new javax.swing.JPanel();
        lblX = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        txtProv = new rojeru_san.RSMTextFull();
        txtContacto = new rojeru_san.RSMTextFull();
        cmbMoneda = new RSMaterialComponent.RSComboBoxMaterial();
        cmbCondicion = new RSMaterialComponent.RSComboBoxMaterial();
        txtIva = new rojeru_san.RSMTextFull();
        txtIsr = new rojeru_san.RSMTextFull();
        jLabel3 = new javax.swing.JLabel();
        txtTelefono = new RSComponentShade.RSFormatFieldShade();
        txtDireccion1 = new rojeru_san.RSMTextFull();
        txtCorreo = new rojeru_san.RSMTextFull();
        jLabel2 = new javax.swing.JLabel();
        txtTelefonoPersonal = new RSComponentShade.RSFormatFieldShade();
        txtCorreoCopia = new rojeru_san.RSMTextFull();
        jPanel3 = new javax.swing.JPanel();
        btnGuardar = new rojeru_san.rsbutton.RSButtonRoundRipple();
        btnLimpiar = new rojeru_san.rsbutton.RSButtonRoundRipple();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(477, 688));
        setUndecorated(true);
        setPreferredSize(new java.awt.Dimension(436, 667));

        panelRound1.setBackground(new java.awt.Color(51, 51, 51));
        panelRound1.setRoundBottomLeft(100);
        panelRound1.setRoundBottomRight(100);
        panelRound1.setRoundTopLeft(100);
        panelRound1.setRoundTopRight(100);
        panelRound1.setLayout(new java.awt.BorderLayout());

        jPanel1.setBackground(new java.awt.Color(51, 51, 51));
        jPanel1.setLayout(new java.awt.BorderLayout());

        jLabel1.setFont(new java.awt.Font("Lexend", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("AGREGAR PROVEEDOR");
        jPanel1.add(jLabel1, java.awt.BorderLayout.CENTER);

        panelX.setBackground(new java.awt.Color(51, 51, 51));

        lblX.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        lblX.setForeground(new java.awt.Color(255, 255, 255));
        lblX.setText(" X ");
        lblX.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblXMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblXMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblXMouseExited(evt);
            }
        });
        panelX.add(lblX);

        jPanel1.add(panelX, java.awt.BorderLayout.EAST);

        panelRound1.add(jPanel1, java.awt.BorderLayout.NORTH);

        jPanel2.setBackground(new java.awt.Color(51, 51, 51));
        java.awt.GridBagLayout jPanel2Layout = new java.awt.GridBagLayout();
        jPanel2Layout.columnWeights = new double[] {1.0};
        jPanel2.setLayout(jPanel2Layout);

        txtProv.setBackground(new java.awt.Color(51, 51, 51));
        txtProv.setForeground(new java.awt.Color(255, 255, 255));
        txtProv.setBordeColorFocus(new java.awt.Color(255, 255, 255));
        txtProv.setBordeColorNoFocus(new java.awt.Color(102, 102, 102));
        txtProv.setBotonColor(new java.awt.Color(51, 51, 51));
        txtProv.setCaretColor(new java.awt.Color(255, 255, 255));
        txtProv.setDisabledTextColor(new java.awt.Color(204, 204, 204));
        txtProv.setFont(new java.awt.Font("Lexend", 0, 14)); // NOI18N
        txtProv.setMayusculas(true);
        txtProv.setModoMaterial(true);
        txtProv.setNextFocusableComponent(txtContacto);
        txtProv.setPlaceholder("Nombre de proveedor");
        txtProv.setSelectionColor(new java.awt.Color(255, 255, 255));
        txtProv.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtProvFocusLost(evt);
            }
        });
        txtProv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtProvActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(24, 44, 4, 44);
        jPanel2.add(txtProv, gridBagConstraints);

        txtContacto.setBackground(new java.awt.Color(51, 51, 51));
        txtContacto.setForeground(new java.awt.Color(255, 255, 255));
        txtContacto.setBordeColorFocus(new java.awt.Color(255, 255, 255));
        txtContacto.setBordeColorNoFocus(new java.awt.Color(102, 102, 102));
        txtContacto.setBotonColor(new java.awt.Color(51, 51, 51));
        txtContacto.setCaretColor(new java.awt.Color(255, 255, 255));
        txtContacto.setDisabledTextColor(new java.awt.Color(204, 204, 204));
        txtContacto.setFont(new java.awt.Font("Lexend", 0, 14)); // NOI18N
        txtContacto.setMayusculas(true);
        txtContacto.setModoMaterial(true);
        txtContacto.setNextFocusableComponent(cmbMoneda);
        txtContacto.setPlaceholder("Contacto");
        txtContacto.setSelectionColor(new java.awt.Color(255, 255, 255));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(4, 44, 4, 44);
        jPanel2.add(txtContacto, gridBagConstraints);

        cmbMoneda.setBackground(new java.awt.Color(51, 51, 51));
        cmbMoneda.setForeground(new java.awt.Color(255, 255, 255));
        cmbMoneda.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "SELECCIONAR MONEDA", "MXN", "DLLS" }));
        cmbMoneda.setColorMaterial(new java.awt.Color(255, 255, 255));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(4, 44, 4, 44);
        jPanel2.add(cmbMoneda, gridBagConstraints);

        cmbCondicion.setBackground(new java.awt.Color(51, 51, 51));
        cmbCondicion.setForeground(new java.awt.Color(255, 255, 255));
        cmbCondicion.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "SELECCIONAR CONDICION", "CREDITO", "CONTADO" }));
        cmbCondicion.setColorMaterial(new java.awt.Color(255, 255, 255));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(4, 44, 4, 44);
        jPanel2.add(cmbCondicion, gridBagConstraints);

        txtIva.setBackground(new java.awt.Color(51, 51, 51));
        txtIva.setForeground(new java.awt.Color(255, 255, 255));
        txtIva.setBordeColorFocus(new java.awt.Color(255, 255, 255));
        txtIva.setBordeColorNoFocus(new java.awt.Color(102, 102, 102));
        txtIva.setBotonColor(new java.awt.Color(51, 51, 51));
        txtIva.setCaretColor(new java.awt.Color(255, 255, 255));
        txtIva.setDisabledTextColor(new java.awt.Color(204, 204, 204));
        txtIva.setFont(new java.awt.Font("Lexend", 0, 14)); // NOI18N
        txtIva.setModoMaterial(true);
        txtIva.setNextFocusableComponent(txtIsr);
        txtIva.setPlaceholder("Iva");
        txtIva.setSelectionColor(new java.awt.Color(255, 255, 255));
        txtIva.setSoloNumeros(true);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(4, 44, 4, 44);
        jPanel2.add(txtIva, gridBagConstraints);

        txtIsr.setBackground(new java.awt.Color(51, 51, 51));
        txtIsr.setForeground(new java.awt.Color(255, 255, 255));
        txtIsr.setBordeColorFocus(new java.awt.Color(255, 255, 255));
        txtIsr.setBordeColorNoFocus(new java.awt.Color(102, 102, 102));
        txtIsr.setBotonColor(new java.awt.Color(51, 51, 51));
        txtIsr.setCaretColor(new java.awt.Color(255, 255, 255));
        txtIsr.setDisabledTextColor(new java.awt.Color(204, 204, 204));
        txtIsr.setFont(new java.awt.Font("Lexend", 0, 14)); // NOI18N
        txtIsr.setModoMaterial(true);
        txtIsr.setNextFocusableComponent(txtTelefono);
        txtIsr.setPlaceholder("Isr");
        txtIsr.setSelectionColor(new java.awt.Color(255, 255, 255));
        txtIsr.setSoloNumeros(true);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(4, 44, 4, 44);
        jPanel2.add(txtIsr, gridBagConstraints);

        jLabel3.setFont(new java.awt.Font("Lexend", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(204, 204, 204));
        jLabel3.setText("Oficina");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(4, 44, 4, 44);
        jPanel2.add(jLabel3, gridBagConstraints);

        txtTelefono.setBackground(new java.awt.Color(51, 51, 51));
        txtTelefono.setForeground(new java.awt.Color(255, 255, 255));
        txtTelefono.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtTelefono.setBgShade(new java.awt.Color(204, 204, 204));
        txtTelefono.setBgShadeHover(new java.awt.Color(255, 255, 255));
        txtTelefono.setCaretColor(new java.awt.Color(255, 255, 255));
        txtTelefono.setDisabledTextColor(new java.awt.Color(255, 255, 255));
        txtTelefono.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        try {
            txtTelefono.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("###-###-####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txtTelefono.setPlaceholder("Phone number");
        txtTelefono.setPreferredSize(new java.awt.Dimension(300, 45));
        txtTelefono.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtTelefonoKeyTyped(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipady = 21;
        gridBagConstraints.insets = new java.awt.Insets(4, 44, 4, 44);
        jPanel2.add(txtTelefono, gridBagConstraints);

        txtDireccion1.setBackground(new java.awt.Color(51, 51, 51));
        txtDireccion1.setForeground(new java.awt.Color(255, 255, 255));
        txtDireccion1.setBordeColorFocus(new java.awt.Color(255, 255, 255));
        txtDireccion1.setBordeColorNoFocus(new java.awt.Color(102, 102, 102));
        txtDireccion1.setBotonColor(new java.awt.Color(51, 51, 51));
        txtDireccion1.setCaretColor(new java.awt.Color(255, 255, 255));
        txtDireccion1.setDisabledTextColor(new java.awt.Color(204, 204, 204));
        txtDireccion1.setFont(new java.awt.Font("Lexend", 0, 14)); // NOI18N
        txtDireccion1.setMayusculas(true);
        txtDireccion1.setModoMaterial(true);
        txtDireccion1.setNextFocusableComponent(txtCorreo);
        txtDireccion1.setPlaceholder("Direccion");
        txtDireccion1.setSelectionColor(new java.awt.Color(255, 255, 255));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(4, 44, 4, 44);
        jPanel2.add(txtDireccion1, gridBagConstraints);

        txtCorreo.setBackground(new java.awt.Color(51, 51, 51));
        txtCorreo.setForeground(new java.awt.Color(255, 255, 255));
        txtCorreo.setBordeColorFocus(new java.awt.Color(255, 255, 255));
        txtCorreo.setBordeColorNoFocus(new java.awt.Color(102, 102, 102));
        txtCorreo.setBotonColor(new java.awt.Color(51, 51, 51));
        txtCorreo.setCaretColor(new java.awt.Color(255, 255, 255));
        txtCorreo.setDisabledTextColor(new java.awt.Color(204, 204, 204));
        txtCorreo.setFont(new java.awt.Font("Lexend", 0, 14)); // NOI18N
        txtCorreo.setModoMaterial(true);
        txtCorreo.setNextFocusableComponent(txtTelefonoPersonal);
        txtCorreo.setPlaceholder("Correo");
        txtCorreo.setSelectionColor(new java.awt.Color(255, 255, 255));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(4, 44, 4, 44);
        jPanel2.add(txtCorreo, gridBagConstraints);

        jLabel2.setFont(new java.awt.Font("Lexend", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(204, 204, 204));
        jLabel2.setText("Personal");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(4, 44, 4, 44);
        jPanel2.add(jLabel2, gridBagConstraints);

        txtTelefonoPersonal.setBackground(new java.awt.Color(51, 51, 51));
        txtTelefonoPersonal.setForeground(new java.awt.Color(255, 255, 255));
        txtTelefonoPersonal.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtTelefonoPersonal.setBgShade(new java.awt.Color(204, 204, 204));
        txtTelefonoPersonal.setBgShadeHover(new java.awt.Color(255, 255, 255));
        txtTelefonoPersonal.setCaretColor(new java.awt.Color(255, 255, 255));
        txtTelefonoPersonal.setDisabledTextColor(new java.awt.Color(255, 255, 255));
        txtTelefonoPersonal.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        try {
            txtTelefonoPersonal.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("###-###-####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txtTelefonoPersonal.setNextFocusableComponent(txtCorreoCopia);
        txtTelefonoPersonal.setPlaceholder("Phone number");
        txtTelefonoPersonal.setPreferredSize(new java.awt.Dimension(300, 45));
        txtTelefonoPersonal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtTelefonoPersonalKeyTyped(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipady = 21;
        gridBagConstraints.insets = new java.awt.Insets(4, 44, 4, 44);
        jPanel2.add(txtTelefonoPersonal, gridBagConstraints);

        txtCorreoCopia.setBackground(new java.awt.Color(51, 51, 51));
        txtCorreoCopia.setForeground(new java.awt.Color(255, 255, 255));
        txtCorreoCopia.setBordeColorFocus(new java.awt.Color(255, 255, 255));
        txtCorreoCopia.setBordeColorNoFocus(new java.awt.Color(102, 102, 102));
        txtCorreoCopia.setBotonColor(new java.awt.Color(51, 51, 51));
        txtCorreoCopia.setCaretColor(new java.awt.Color(255, 255, 255));
        txtCorreoCopia.setDisabledTextColor(new java.awt.Color(204, 204, 204));
        txtCorreoCopia.setFont(new java.awt.Font("Lexend", 0, 14)); // NOI18N
        txtCorreoCopia.setModoMaterial(true);
        txtCorreoCopia.setPlaceholder("Correo Copia");
        txtCorreoCopia.setSelectionColor(new java.awt.Color(255, 255, 255));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(4, 44, 24, 44);
        jPanel2.add(txtCorreoCopia, gridBagConstraints);

        panelRound1.add(jPanel2, java.awt.BorderLayout.CENTER);

        jPanel3.setBackground(new java.awt.Color(51, 51, 51));

        btnGuardar.setText("Guardar");
        btnGuardar.setNextFocusableComponent(btnLimpiar);
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });
        jPanel3.add(btnGuardar);

        btnLimpiar.setText("Limpiar");
        btnLimpiar.setNextFocusableComponent(txtProv);
        btnLimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimpiarActionPerformed(evt);
            }
        });
        jPanel3.add(btnLimpiar);

        panelRound1.add(jPanel3, java.awt.BorderLayout.PAGE_END);

        getContentPane().add(panelRound1, java.awt.BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void txtTelefonoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTelefonoKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTelefonoKeyTyped

    private void lblXMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblXMouseEntered
        panelX.setBackground(Color.red);
    }//GEN-LAST:event_lblXMouseEntered

    private void lblXMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblXMouseExited
        panelX.setBackground(new Color(51,51,51));
    }//GEN-LAST:event_lblXMouseExited

    private void lblXMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblXMouseClicked
        dispose();
    }//GEN-LAST:event_lblXMouseClicked

    private void btnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarActionPerformed
        txtProv.setText("");
        txtContacto.setText("");
        txtIva.setText("");
        txtTelefonoPersonal.setText("");
        txtTelefono.setText("");
        txtIsr.setText("");
        cmbCondicion.setSelectedIndex(0);
        cmbMoneda.setSelectedIndex(0);
    }//GEN-LAST:event_btnLimpiarActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
       guardar();
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void txtProvFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtProvFocusLost
        try{
            Connection con;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            String sql = "select * from registroprov_compras where Nombre like '"+txtProv.getText()+"'";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            String prov = null;
            while(rs.next()){
                prov = rs.getString("Nombre");
            }
            if(prov == null){
                btnGuardar.setText("Guardar");
            }else{
                btnGuardar.setText("Actualizar");
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "ERROR: "+e,"ERROR",JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_txtProvFocusLost

    private void txtProvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtProvActionPerformed
        verProveedor();
    }//GEN-LAST:event_txtProvActionPerformed

    private void txtTelefonoPersonalKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTelefonoPersonalKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTelefonoPersonalKeyTyped

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
            java.util.logging.Logger.getLogger(addProveedor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(addProveedor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(addProveedor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(addProveedor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                addProveedor dialog = new addProveedor(new javax.swing.JFrame(), true);
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
    public rojeru_san.rsbutton.RSButtonRoundRipple btnGuardar;
    private rojeru_san.rsbutton.RSButtonRoundRipple btnLimpiar;
    private RSMaterialComponent.RSComboBoxMaterial cmbCondicion;
    private RSMaterialComponent.RSComboBoxMaterial cmbMoneda;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JLabel lblX;
    private scrollPane.PanelRound panelRound1;
    private javax.swing.JPanel panelX;
    private rojeru_san.RSMTextFull txtContacto;
    private rojeru_san.RSMTextFull txtCorreo;
    private rojeru_san.RSMTextFull txtCorreoCopia;
    private rojeru_san.RSMTextFull txtDireccion1;
    private rojeru_san.RSMTextFull txtIsr;
    private rojeru_san.RSMTextFull txtIva;
    public rojeru_san.RSMTextFull txtProv;
    private RSComponentShade.RSFormatFieldShade txtTelefono;
    private RSComponentShade.RSFormatFieldShade txtTelefonoPersonal;
    // End of variables declaration//GEN-END:variables
}
