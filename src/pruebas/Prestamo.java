package pruebas;

import Conexiones.Conexion;
import VentanaEmergente.Prestamo.Bloqueos;
import VentanaEmergente.Prestamo.EntregaRequisicion;
import VentanaEmergente.Prestamo.PrestamoMaterial;
import VentanaEmergente.Prestamo.cambiarCantidad;
import com.mxrck.autocompleter.TextAutoCompleter;
import java.awt.Color;
import java.awt.Image;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

public class Prestamo extends InternalFrameImagen {
    ResultSet res;
    private Image data;
    FileInputStream Cargar_Archivo;
    TextAutoCompleter auto, ac1, ac2;
    Inicio1 inicio;
    String numeroEmpleado;
    boolean jor;
    
    public void buscar(String arti){
        try{
            Connection con = null;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            String sql = "select * from inventario where NumeroDeParte like '"+arti+"'";
            ResultSet rs = st.executeQuery(sql);
            String datos[] = new String[15];
            while(rs.next()){
                datos[0] = rs.getString("NumeroDeParte");
                datos[1] = rs.getString("Descripcion");
                datos[2] = rs.getString("Cantidad");
            }
            String sql2 = "select precio, Codigo, Proveedor from Requisiciones where Codigo like '"+arti+"' and Precio != '' and Precio is not null";
            Statement st2 = con.createStatement();
            ResultSet rs2 = st2.executeQuery(sql2);
            while(rs2.next()){
                datos[3] = "$ " + rs2.getString("Precio");
                datos[4] = rs2.getString("Proveedor");
            }
            if(datos[3] == null){
                txtPrecio.setEditable(true);
            }
            String sql3 = "select Moneda,Nombre from registroprov_compras where Nombre like '"+datos[4]+"'";
            Statement st3 = con.createStatement();
            ResultSet rs3 = st3.executeQuery(sql3);
            while(rs3.next()){
                datos[5] = rs3.getString("Moneda");
            }
            
            if(datos[0] == null){
                JOptionPane.showMessageDialog(this, "NO SE ENCONTRO NUMERO DE PARTE","ADVERTENCIA",JOptionPane.WARNING_MESSAGE);
            }else{
            txtParte.setText(datos[0]);
            txtDescripcion.setText(datos[1]);
            txtCantidad.setText(datos[2]);
            txtPrecio.setText(datos[3] + " " + datos[5]);
            }
            
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "ERROR: "+e,"ERROR",JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void autoCompletar() {
        ac1 = new TextAutoCompleter(txtArticulo);
        try {
            Connection con = null;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            String sql = "select NumeroDeParte from Inventario";
            String datos[] = new String[10];
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                datos[0] = rs.getString("NumeroDeParte");
                ac1.addItem(datos[0]);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "ERROR AL AUTOCOMPLETAR" + e, "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void restarCantidad(){
        try{
            Connection con = null;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            String sql = "select * from inventario where NumeroDeParte like '"+txtParte.getText()+"'";
            ResultSet rs = st.executeQuery(sql);
            String cantidad = "";
            double can1, can2, total;
            while(rs.next()){
                cantidad = rs.getString("Cantidad");
            }
            
            can1 = Double.parseDouble(cantidad);
            can2 = Double.parseDouble(txtPedido.getText());
            
            if(can2 > can1){
                JOptionPane.showMessageDialog(this, "NO HAY PIEZAS PEDIDAS","ADVERTENCIA",JOptionPane.WARNING_MESSAGE);
            }else{
            total = can1 - can2;
            String sql2 = "update inventario set Cantidad = ? where NumeroDeParte = ?";
            PreparedStatement pst = con.prepareStatement(sql2);
            
            pst.setString(1, ""+total);
            pst.setString(2, txtParte.getText());
            
            pst.executeUpdate();
            
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "ERROR: "+e,"ERROR",JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void verCantidad(){
        try{
            Connection con = null;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            String sql = "select Cantidad from inventario where NumeroDeParte like '"+txtParte.getText()+"'";
            ResultSet rs = st.executeQuery(sql);
            String datos = "";
            while(rs.next()){
                datos = rs.getString("Cantidad");
            }
            
            txtCantidad.setText(datos);
            
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "ERROR: "+e,"ERROR",JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void borrar(){
        txtPedido.setText("");
        txtProyecto.setText("");
    }
    
    
    private Image ConvertirImagen(byte[] bytes) throws IOException {
        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        Iterator readers = ImageIO.getImageReadersByFormatName("jpeg");
        ImageReader reader = (ImageReader) readers.next();
        Object source = bis; 
        ImageInputStream iis = ImageIO.createImageInputStream(source);
        reader.setInput(iis, true);
        ImageReadParam param = reader.getDefaultReadParam();
        return reader.read(0, param);
    }
 public void cargarfoto(String id) {
        Image dtCat = recuperarfotos(id);
        ImageIcon icon = new ImageIcon(dtCat);
        Image img = icon.getImage();
        Image newimg = img.getScaledInstance(290, 294, java.awt.Image.SCALE_SMOOTH);
        ImageIcon newIcon = new ImageIcon(newimg);
        if (newIcon == null) 
            {
                JOptionPane.showMessageDialog(null, "EL EQUIPO NO TIENE FOTO", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
            } 
        else 
            {
            }
    }
 public Image recuperarfotos(String usuario) {
        try {
                Connection con = null;
                Conexion conect = new Conexion();
                con = conect.getConnection();
                Statement st = con.createStatement();
                PreparedStatement pst = con.prepareStatement("Select Inventario.Imagen From Inventario where Inventario.Id = ? ");
                pst.setString(1, usuario);
                res = pst.executeQuery();
                int i = 0;
                    while (res.next()) 
                        {
                            byte[] b = res.getBytes("Imagen");
                            data = ConvertirImagen(b);       
                            i++;
                        }
                    res.close();
            } catch (IOException | SQLException ex) 
                {
                    Logger.getLogger(pruebas.Inventario.class.getName()).log(Level.SEVERE, null, ex);
                }
        return data;
        
        
        
    }
    public void cargarProyecto(){
        auto = new TextAutoCompleter(txtProyecto);
        try{
            Connection con = null;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            String sql = "select Proyecto from proyectos";
            ResultSet rs = st.executeQuery(sql);
            String datos = "";
            
            while(rs.next()){
                datos = rs.getString("Proyecto");
                auto.addItem(datos);
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "ERROR: "+e,"ERROR",JOptionPane.ERROR_MESSAGE);
        }
        
    }
    
    public void cargar(String numero){
        try{
            Connection con = null;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            String sql = "select * from registroempleados where NumEmpleado like '"+numero+"'";
            ResultSet rs = st.executeQuery(sql);
            String datos[] = new String[6];
            while(rs.next()){
                datos[0] = rs.getString("Nombre");
                datos[1] = rs.getString("Apellido");
            }
            
            txtNombre.setText(datos[0]+" "+datos[1]);
            
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "ERROR: "+ e,"ERROR",JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public Prestamo(String numero, Inicio1 inicio) {
        initComponents();
        Date fecha1 = new Date();
        SimpleDateFormat f = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        lblFecha.setText(f.format(fecha1));
        cargarProyecto();
        cargar(numero);
        numeroEmpleado = numero;
        autoCompletar();
        this.inicio = inicio;
        ((javax.swing.plaf.basic.BasicInternalFrameUI) this.getUI()).setNorthPane(null);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupMenu1 = new javax.swing.JPopupMenu();
        cambiarCantidad = new javax.swing.JMenuItem();
        jPanel1 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        pan = new javax.swing.JPanel();
        panelSalir = new javax.swing.JPanel();
        lblSalir = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        lblFecha = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        cmbOperacion = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        txtArticulo = new javax.swing.JTextField();
        jPanel9 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        txtNumero = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtParte = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        txtEmpleado = new javax.swing.JTextField();
        jPanel11 = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        btnPrestamo = new javax.swing.JButton();
        btnEntrega = new javax.swing.JButton();
        btnPedidos = new javax.swing.JButton();
        btnCaptura = new javax.swing.JButton();
        jPanel13 = new javax.swing.JPanel();
        jPanel14 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtDescripcion = new javax.swing.JTextArea();
        jPanel15 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        txtPrecio = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txtCantidad = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtPedido = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtProyecto = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();

        cambiarCantidad.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        cambiarCantidad.setText("Cambiar cantidad de stock                         ");
        cambiarCantidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cambiarCantidadActionPerformed(evt);
            }
        });
        jPopupMenu1.add(cambiarCantidad);

        setBorder(null);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new java.awt.BorderLayout());

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setLayout(new java.awt.BorderLayout());

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));

        jLabel17.setFont(new java.awt.Font("Roboto", 1, 40)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(0, 165, 252));
        jLabel17.setText("Prestamos");
        jPanel6.add(jLabel17);

        jPanel5.add(jPanel6);

        jPanel4.add(jPanel5, java.awt.BorderLayout.CENTER);

        pan.setBackground(new java.awt.Color(255, 255, 255));

        panelSalir.setBackground(new java.awt.Color(255, 255, 255));

        lblSalir.setFont(new java.awt.Font("Roboto", 1, 20)); // NOI18N
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
        jPanel2.setLayout(new java.awt.BorderLayout());

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jLabel4.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        jLabel4.setText("FECHA:  ");
        jPanel3.add(jLabel4);

        lblFecha.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        lblFecha.setText(" ");
        jPanel3.add(lblFecha);

        jPanel2.add(jPanel3, java.awt.BorderLayout.PAGE_START);

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setLayout(new java.awt.BorderLayout(0, 15));

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));
        jPanel8.setLayout(new java.awt.GridLayout(3, 0, 10, 10));

        cmbOperacion.setBackground(new java.awt.Color(255, 255, 255));
        cmbOperacion.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        cmbOperacion.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "SELECCIONAR OPERARCION", "PRESTAMO", "ENTREGA", "PEDIDO", "CAPTURA" }));
        cmbOperacion.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbOperacionItemStateChanged(evt);
            }
        });
        jPanel8.add(cmbOperacion);

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setText("BUSCAR");
        jPanel8.add(jLabel3);

        txtArticulo.setBackground(new java.awt.Color(255, 255, 255));
        txtArticulo.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtArticulo.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        txtArticulo.setNextFocusableComponent(txtNumero);
        txtArticulo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtArticuloActionPerformed(evt);
            }
        });
        jPanel8.add(txtArticulo);

        jPanel7.add(jPanel8, java.awt.BorderLayout.PAGE_START);

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));
        jPanel9.setLayout(new java.awt.BorderLayout(0, 15));

        jPanel10.setBackground(new java.awt.Color(255, 255, 255));
        jPanel10.setLayout(new java.awt.GridLayout(2, 0, 10, 10));

        jLabel5.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel5.setText("NOMBRE ALMACENISTA:  ");
        jPanel10.add(jLabel5);

        txtNombre.setEditable(false);
        txtNombre.setBackground(new java.awt.Color(255, 255, 255));
        txtNombre.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        txtNombre.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtNombre.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        jPanel10.add(txtNombre);

        jLabel13.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel13.setText("NUMERO DE EMPLEADO:  ");
        jPanel10.add(jLabel13);

        txtNumero.setBackground(new java.awt.Color(255, 255, 255));
        txtNumero.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        txtNumero.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtNumero.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        txtNumero.setNextFocusableComponent(txtPedido);
        txtNumero.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNumeroActionPerformed(evt);
            }
        });
        jPanel10.add(txtNumero);

        jLabel9.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel9.setText("NUMERO DE PARTE:  ");
        jPanel10.add(jLabel9);

        txtParte.setEditable(false);
        txtParte.setBackground(new java.awt.Color(255, 255, 255));
        txtParte.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        txtParte.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtParte.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        jPanel10.add(txtParte);

        jLabel12.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel12.setText("NOMBRE EMPLEADO:  ");
        jPanel10.add(jLabel12);

        txtEmpleado.setEditable(false);
        txtEmpleado.setBackground(new java.awt.Color(255, 255, 255));
        txtEmpleado.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        txtEmpleado.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtEmpleado.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        jPanel10.add(txtEmpleado);

        jPanel9.add(jPanel10, java.awt.BorderLayout.PAGE_START);

        jPanel11.setBackground(new java.awt.Color(255, 255, 255));
        jPanel11.setLayout(new java.awt.BorderLayout());

        jPanel12.setBackground(new java.awt.Color(255, 255, 255));
        jPanel12.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 40, 20));

        btnPrestamo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ImgAnimacion/prestamo_32.png"))); // NOI18N
        btnPrestamo.setBorder(null);
        btnPrestamo.setBorderPainted(false);
        btnPrestamo.setContentAreaFilled(false);
        btnPrestamo.setEnabled(false);
        btnPrestamo.setFocusPainted(false);
        btnPrestamo.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnPrestamo.setPreferredSize(new java.awt.Dimension(64, 64));
        btnPrestamo.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/ImgAnimacion/prestamo_32.png"))); // NOI18N
        btnPrestamo.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/ImgAnimacion/prestamo_48.png"))); // NOI18N
        btnPrestamo.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        btnPrestamo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrestamoActionPerformed(evt);
            }
        });
        jPanel12.add(btnPrestamo);

        btnEntrega.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ImgAnimacion/entrega_32.png"))); // NOI18N
        btnEntrega.setBorder(null);
        btnEntrega.setBorderPainted(false);
        btnEntrega.setContentAreaFilled(false);
        btnEntrega.setEnabled(false);
        btnEntrega.setFocusPainted(false);
        btnEntrega.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnEntrega.setPreferredSize(new java.awt.Dimension(64, 64));
        btnEntrega.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/ImgAnimacion/entrega_32.png"))); // NOI18N
        btnEntrega.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/ImgAnimacion/entrega_48.png"))); // NOI18N
        btnEntrega.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        btnEntrega.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEntregaActionPerformed(evt);
            }
        });
        jPanel12.add(btnEntrega);

        btnPedidos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ImgAnimacion/pedidos_32.png"))); // NOI18N
        btnPedidos.setBorder(null);
        btnPedidos.setBorderPainted(false);
        btnPedidos.setContentAreaFilled(false);
        btnPedidos.setEnabled(false);
        btnPedidos.setFocusPainted(false);
        btnPedidos.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnPedidos.setNextFocusableComponent(txtArticulo);
        btnPedidos.setPreferredSize(new java.awt.Dimension(64, 64));
        btnPedidos.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/ImgAnimacion/pedidos_32.png"))); // NOI18N
        btnPedidos.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/ImgAnimacion/pedidos_48.png"))); // NOI18N
        btnPedidos.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        btnPedidos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPedidosActionPerformed(evt);
            }
        });
        jPanel12.add(btnPedidos);

        btnCaptura.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ImgAnimacion/capturar_32.png"))); // NOI18N
        btnCaptura.setBorder(null);
        btnCaptura.setBorderPainted(false);
        btnCaptura.setContentAreaFilled(false);
        btnCaptura.setEnabled(false);
        btnCaptura.setFocusPainted(false);
        btnCaptura.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnCaptura.setPreferredSize(new java.awt.Dimension(64, 64));
        btnCaptura.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/ImgAnimacion/capturar_32.png"))); // NOI18N
        btnCaptura.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/ImgAnimacion/capturar_48.png"))); // NOI18N
        btnCaptura.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        btnCaptura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCapturaActionPerformed(evt);
            }
        });
        jPanel12.add(btnCaptura);

        jPanel11.add(jPanel12, java.awt.BorderLayout.PAGE_END);

        jPanel13.setBackground(new java.awt.Color(255, 255, 255));
        jPanel13.setLayout(new java.awt.BorderLayout());

        jPanel14.setBackground(new java.awt.Color(255, 255, 255));
        jPanel14.setLayout(new java.awt.BorderLayout(10, 10));

        jLabel10.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        jLabel10.setText("DESCRIPCION");
        jPanel14.add(jLabel10, java.awt.BorderLayout.WEST);

        jScrollPane2.setBorder(null);

        txtDescripcion.setEditable(false);
        txtDescripcion.setBackground(new java.awt.Color(255, 255, 255));
        txtDescripcion.setColumns(20);
        txtDescripcion.setFont(new java.awt.Font("Roboto Black", 0, 12)); // NOI18N
        txtDescripcion.setLineWrap(true);
        txtDescripcion.setRows(5);
        txtDescripcion.setWrapStyleWord(true);
        txtDescripcion.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(204, 204, 204)));
        jScrollPane2.setViewportView(txtDescripcion);

        jPanel14.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        jPanel13.add(jPanel14, java.awt.BorderLayout.PAGE_START);

        jPanel15.setBackground(new java.awt.Color(255, 255, 255));
        jPanel15.setLayout(new java.awt.GridLayout(4, 0));

        jLabel14.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel14.setText("PRECIO:  ");
        jPanel15.add(jLabel14);

        txtPrecio.setEditable(false);
        txtPrecio.setBackground(new java.awt.Color(255, 255, 255));
        txtPrecio.setFont(new java.awt.Font("Roboto", 1, 24)); // NOI18N
        txtPrecio.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtPrecio.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        txtPrecio.setComponentPopupMenu(jPopupMenu1);
        jPanel15.add(txtPrecio);

        jLabel11.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel11.setText("CANTIDAD EN STOCK:  ");
        jPanel15.add(jLabel11);

        txtCantidad.setEditable(false);
        txtCantidad.setBackground(new java.awt.Color(255, 255, 255));
        txtCantidad.setFont(new java.awt.Font("Roboto", 1, 24)); // NOI18N
        txtCantidad.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCantidad.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        txtCantidad.setComponentPopupMenu(jPopupMenu1);
        jPanel15.add(txtCantidad);

        jLabel7.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel7.setText("CANTIDAD:  ");
        jPanel15.add(jLabel7);

        txtPedido.setEditable(false);
        txtPedido.setBackground(new java.awt.Color(255, 255, 255));
        txtPedido.setFont(new java.awt.Font("Roboto", 1, 24)); // NOI18N
        txtPedido.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtPedido.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        txtPedido.setNextFocusableComponent(txtProyecto);
        jPanel15.add(txtPedido);
        ((AbstractDocument) txtPedido.getDocument()).setDocumentFilter(new DocumentFilter() {
            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                // Filtrar solo caracteres no alfabéticos (números y otros caracteres)
                for (int i = 0; i < text.length(); i++) {
                    if (Character.isLetter(text.charAt(i))) {
                        return; // Ignorar el reemplazo si hay letras
                    }
                }
                super.replace(fb, offset, length, text, attrs);
            }
        });

        jLabel8.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel8.setText("PROYECTO:  ");
        jPanel15.add(jLabel8);

        txtProyecto.setEditable(false);
        txtProyecto.setBackground(new java.awt.Color(255, 255, 255));
        txtProyecto.setFont(new java.awt.Font("Roboto", 1, 24)); // NOI18N
        txtProyecto.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtProyecto.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        jPanel15.add(txtProyecto);

        jPanel13.add(jPanel15, java.awt.BorderLayout.CENTER);

        jPanel11.add(jPanel13, java.awt.BorderLayout.CENTER);

        jPanel9.add(jPanel11, java.awt.BorderLayout.CENTER);

        jPanel7.add(jPanel9, java.awt.BorderLayout.CENTER);

        jPanel2.add(jPanel7, java.awt.BorderLayout.CENTER);

        jPanel1.add(jPanel2, java.awt.BorderLayout.CENTER);

        jLabel1.setText("                                 ");
        jPanel1.add(jLabel1, java.awt.BorderLayout.WEST);

        jLabel2.setText("                                 ");
        jPanel1.add(jLabel2, java.awt.BorderLayout.LINE_END);

        jMenu1.setText("File");

        jMenuItem1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/entrega-rapida.png"))); // NOI18N
        jMenuItem1.setText("Entrega de requisicion");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuItem2.setText("Prestamo de herramienta");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);

        jMenuItem3.setText("Bloqueo de articulos");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem3);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");
        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 1313, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 719, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cmbOperacionItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbOperacionItemStateChanged
        switch (cmbOperacion.getSelectedIndex()) {
            case 1:
                txtPedido.setEditable(true);
                txtProyecto.setEditable(true);
                btnPrestamo.setEnabled(true);
                btnEntrega.setEnabled(false);
                btnPedidos.setEnabled(false);
                btnCaptura.setEnabled(false);
                txtNumero.setEditable(true);
                txtEmpleado.setEditable(true);
                break;
            case 2:
                txtPedido.setEditable(true);
                txtProyecto.setEditable(true);
                btnEntrega.setEnabled(true);
                btnPedidos.setEnabled(false);
                btnPrestamo.setEnabled(false);
                btnCaptura.setEnabled(false);
                txtNumero.setEditable(true);
                txtEmpleado.setEditable(true);
                break;
            case 3:
                txtPedido.setEditable(true);
                txtProyecto.setEditable(true);
                btnPedidos.setEnabled(true);
                btnPrestamo.setEnabled(false);
                btnEntrega.setEnabled(false);
                btnCaptura.setEnabled(false);
                txtNumero.setEditable(true);
                txtEmpleado.setEditable(true);
                break;
            case 4:
                txtPedido.setEditable(true);
                txtProyecto.setEditable(true);
                btnCaptura.setEnabled(true);
                btnPrestamo.setEnabled(false);
                btnEntrega.setEnabled(false);
                btnPedidos.setEnabled(false);
                break;
            default:
                break;
        }
    }//GEN-LAST:event_cmbOperacionItemStateChanged

    private void txtNumeroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNumeroActionPerformed
        try{
            Connection con = null;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            String sql = "select * from registroempleados where NumEmpleado like '"+txtNumero.getText()+"'";
            ResultSet rs = st.executeQuery(sql);
            String datos[] = new String[10];
            while(rs.next()){
                datos[0] = rs.getString("NumEmpleado");
                datos[1] = rs.getString("Nombre");
                datos[2] = rs.getString("Apellido");
            }
            if((txtNumero.getText()).equals(datos[0])){
                txtEmpleado.setText(datos[1]+" "+datos[2]);
                txtNumero.setText("");
            }else{
                JOptionPane.showMessageDialog(this, "EL NUMERO DE EMPLEADO NO EXISTE","ADVERTENCIA",JOptionPane.WARNING_MESSAGE);
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "ERROR: "+e,"ERROR",JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_txtNumeroActionPerformed

    private void btnPrestamoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrestamoActionPerformed
        txtPrecio.setEditable(false);
        int c = Integer.parseInt(txtCantidad.getText());
        int can = Integer.parseInt(txtCantidad.getText());
        int can2 = Integer.parseInt(txtPedido.getText());
        if(txtNombre.getText().equals("")){
            JOptionPane.showMessageDialog(this, "DEBES DARTE DE ALTA","ADVERTENCIA",JOptionPane.WARNING_MESSAGE);
        }else if(txtParte.getText().equals("")){
            JOptionPane.showMessageDialog(this, "DEBES SELECCIONAR UN ARTICULO","ADVERTENCIA",JOptionPane.WARNING_MESSAGE);
        }else if(c <= 0){
            JOptionPane.showMessageDialog(this, "NO PUEDES QUITARLE CANTIDAD A 0","ADVERTENCIA",JOptionPane.WARNING_MESSAGE);
        }else if(txtPedido.getText().equals("")){
            JOptionPane.showMessageDialog(this, "DEBES INTRODUCIR CANTIDAD","ADVERTENCIA",JOptionPane.WARNING_MESSAGE);
        }else if (txtProyecto.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "DEBES SELECCIONAR UN PROYETO","ADVERTENCIA",JOptionPane.WARNING_MESSAGE);
        }else if(txtEmpleado.getText().equals("")){
            JOptionPane.showMessageDialog(this, "DEBES INTRODUCIR EL EMPLEADO REQUISITOR","ADVERTENCIA",JOptionPane.WARNING_MESSAGE);
        }else if(can < can2){
            JOptionPane.showMessageDialog(this, "LA CANTIDAD PEDIDA REBASA LA CANTIDAD EN STOCK","ADVERTENCIA",JOptionPane.WARNING_MESSAGE);
        }else {
        try{
            Connection con = null;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            String sql = "insert into prestamos (Almacenista,Requisitor,NumeroParte,CantidadPedida,Proyecto,FechaSalida,FechaEntrada,Estado) values(?,?,?,?,?,?,?,?)";
            PreparedStatement pst = con.prepareStatement(sql);
            
            Date d = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            String fecha;
            fecha = sdf.format(d);
            
            pst.setString(1, txtNombre.getText());
            pst.setString(2, txtEmpleado.getText());
            pst.setString(3, txtParte.getText());
            pst.setString(4, txtPedido.getText());
            pst.setString(5, txtProyecto.getText());
            pst.setString(6, fecha);
            pst.setString(7, "");
            pst.setString(8, "PRESTADO");
            
            int n = pst.executeUpdate();
            
            if(n > 0){
                JOptionPane.showMessageDialog(this, "DATOS GUARDADOS");
                restarCantidad();
                verCantidad();
                borrar();
            }
            
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "ERROR: "+e,"ERROR",JOptionPane.ERROR_MESSAGE);
        }
        }
    }//GEN-LAST:event_btnPrestamoActionPerformed

    private void btnPedidosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPedidosActionPerformed
        txtPrecio.setEditable(false);
        double c = Double.parseDouble(txtCantidad.getText());
        double can = Double.parseDouble(txtCantidad.getText());
        double can2 = Double.parseDouble(txtPedido.getText());
        if(txtNombre.getText().equals("")){
            JOptionPane.showMessageDialog(this, "DEBES DARTE DE ALTA","ADVERTENCIA",JOptionPane.WARNING_MESSAGE);
        }else if(txtParte.getText().equals("")){
            JOptionPane.showMessageDialog(this, "DEBES SELECCIONAR UN ARTICULO","ADVERTENCIA",JOptionPane.WARNING_MESSAGE);
        }else if(c <= 0){
            JOptionPane.showMessageDialog(this, "NO PUEDES QUITARLE CANTIDAD A 0","ADVERTENCIA",JOptionPane.WARNING_MESSAGE);
        }else if(txtPedido.getText().equals("")){
            JOptionPane.showMessageDialog(this, "DEBES INTRODUCIR CANTIDAD","ADVERTENCIA",JOptionPane.WARNING_MESSAGE);
        }else if (txtProyecto.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "DEBES SELECCIONAR UN PROYETO","ADVERTENCIA",JOptionPane.WARNING_MESSAGE);
        }else if(txtEmpleado.getText().equals("")){
            JOptionPane.showMessageDialog(this, "DEBES INTRODUCIR EL EMPLEADO REQUISITOR","ADVERTENCIA",JOptionPane.WARNING_MESSAGE);
        }else if(can < can2){
            JOptionPane.showMessageDialog(this, "LA CANTIDAD PEDIDA REBASA LA CANTIDAD EN STOCK","ADVERTENCIA",JOptionPane.WARNING_MESSAGE);
        }else {
        try{
            Date f = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM/dd");
            String fecha;
            fecha = sdf.format(f);
            Connection con = null;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            String sql = "insert into pedidos (Almacenista,NumParte,Cantidad,Proyecto,Requisitor,FechaSalida) values(?,?,?,?,?,?)";
            PreparedStatement pst = con.prepareStatement(sql);
            
            pst.setString(1, txtNombre.getText());
            pst.setString(2, txtParte.getText());
            pst.setString(3, txtPedido.getText());
            pst.setString(4, txtProyecto.getText());
            pst.setString(5, txtEmpleado.getText());
            pst.setString(6, fecha);
            
            int n = pst.executeUpdate();
            
            if(n > 0){
                JOptionPane.showMessageDialog(this, "DATOS GUARDADOS");
                restarCantidad();
                verCantidad();
                borrar();
                txtArticulo.setText("");
            }
            
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "ERROR: "+e,"ERROR",JOptionPane.ERROR_MESSAGE);
        }
        }
    }//GEN-LAST:event_btnPedidosActionPerformed

    private void btnCapturaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCapturaActionPerformed
        txtPrecio.setEditable(false);
        if(txtNombre.getText().equals("")){
            JOptionPane.showMessageDialog(this, "DEBES ABRIR LA CUENTA CON TU NUMERO DE EMPLEADO","ADVERTENCIA",JOptionPane.WARNING_MESSAGE);
        }else if(txtParte.getText().equals("")){
            JOptionPane.showMessageDialog(this, "DEBES SELECCIONAR UN ARTICULO","ADVERTENCIA",JOptionPane.WARNING_MESSAGE);
        }else if(txtPedido.getText().equals("")){
            JOptionPane.showMessageDialog(this, "DEBES LLENAR LA CANTIDAD","ADVERTENCIA",JOptionPane.WARNING_MESSAGE);
        }else if(txtProyecto.getText().equals("")){
            JOptionPane.showMessageDialog(this, "DEBES SELECCIONAR UN PROYECTO","ADVERTENCIA",JOptionPane.WARNING_MESSAGE);
        }else{
        try{
            Connection con = null;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            String sql = "select * from inventario where NumeroDeParte like '"+txtParte.getText()+"'";
            ResultSet rs = st.executeQuery(sql);
            String cantidad = "";
            while(rs.next()){
                cantidad = rs.getString("Cantidad");
            }
            int can1, can2, total;
            
            can1 = Integer.parseInt(cantidad);
            can2 = Integer.parseInt(txtPedido.getText());
            total = can1 + can2;
            
            String sql2 = "update inventario set Cantidad = ? where NumeroDeParte = ?";
            PreparedStatement pst = con.prepareStatement(sql2);
            
            pst.setString(1, ""+total);
            pst.setString(2, txtParte.getText());
            
            int n = pst.executeUpdate();
            
            if(n > 0){
                JOptionPane.showMessageDialog(this, "DATOS GUARDADOS");
                verCantidad();
                borrar();
            }
            
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "ERROR: "+e,"ERROR",JOptionPane.ERROR_MESSAGE);
        }
        }
    }//GEN-LAST:event_btnCapturaActionPerformed

    private void btnEntregaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEntregaActionPerformed
        
    }//GEN-LAST:event_btnEntregaActionPerformed

    private void txtArticuloActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtArticuloActionPerformed
        buscar(txtArticulo.getText());
    }//GEN-LAST:event_txtArticuloActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        JFrame f = (JFrame) JOptionPane.getFrameForComponent(this);
        EntregaRequisicion e = new EntregaRequisicion(f, true);
        e.setVisible(true);
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        JFrame f = (JFrame) JOptionPane.getFrameForComponent(this);
        PrestamoMaterial m = new PrestamoMaterial(f,true,numeroEmpleado);
        m.setVisible(true);
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        JFrame f = (JFrame) JOptionPane.getFrameForComponent(this);
        Bloqueos b = new Bloqueos(f, true);
        b.setVisible(true);
    }//GEN-LAST:event_jMenuItem3ActionPerformed

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

    private void cambiarCantidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cambiarCantidadActionPerformed
        JFrame f = (JFrame) JOptionPane.getFrameForComponent(this);
        cambiarCantidad cam = new cambiarCantidad(f,true);
        cam.txtDescripcion1.setText(txtDescripcion.getText());
        cam.txtNum.setText(txtParte.getText());
        cam.setVisible(true);
        buscar(txtParte.getText());
    }//GEN-LAST:event_cambiarCantidadActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCaptura;
    private javax.swing.JButton btnEntrega;
    private javax.swing.JButton btnPedidos;
    private javax.swing.JButton btnPrestamo;
    private javax.swing.JMenuItem cambiarCantidad;
    private javax.swing.JComboBox<String> cmbOperacion;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblFecha;
    private javax.swing.JLabel lblSalir;
    private javax.swing.JPanel pan;
    private javax.swing.JPanel panelSalir;
    private javax.swing.JTextField txtArticulo;
    private javax.swing.JTextField txtCantidad;
    private javax.swing.JTextArea txtDescripcion;
    private javax.swing.JTextField txtEmpleado;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtNumero;
    private javax.swing.JTextField txtParte;
    private javax.swing.JTextField txtPedido;
    private javax.swing.JTextField txtPrecio;
    private javax.swing.JTextField txtProyecto;
    // End of variables declaration//GEN-END:variables
}
