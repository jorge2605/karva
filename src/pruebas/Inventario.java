package pruebas;

import Conexiones.Conexion;
import java.awt.Color;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.Image;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

public class Inventario extends javax.swing.JInternalFrame {
    
    ResultSet res;
    private Image data;
    FileInputStream Cargar_Archivo;
    
    public void limpiarTabla()
    {
    DefaultTableModel miModelo = (DefaultTableModel) Tabla1.getModel();
    String titulos[] = {"ID","NUMERO DE PARTE","DESCRIPCION","CANTIDAD","URL","MARCA","MODELO","CODIGO","UM","PROVEEDOR","COMENTARIOS"};
    miModelo = new DefaultTableModel(null, titulos);
    Tabla1.setModel(miModelo);
    }
     
    public void habilitar(){
    txtId.setEnabled(true);
    txtParte.setEnabled(true);
    txtDescripcion.setEnabled(true);
    txtCantidad.setEnabled(true);
    btnGuardar.setEnabled(true);
    btnAñadir.setEnabled(true);
    btnNuevo.setEnabled(true);
    }
    
    public void deshabilitar(){
    txtId.setEnabled(false);
    txtParte.setEnabled(false);
    txtDescripcion.setEnabled(false);
    txtCantidad.setEnabled(false);
    btnGuardar.setEnabled(false);
    btnAñadir.setEnabled(false);
    }
    
        
    public void verDatos(){
    try{
    DefaultTableModel miModelo = (DefaultTableModel) Tabla1.getModel();
    Connection con = null;
    Conexion con1 = new Conexion();
    con = con1.getConnection();
    Statement st = con.createStatement();
    String sql = "select * from Inventario";
    ResultSet rs = st.executeQuery(sql);
    String datos[] = new String[15];
    while (rs.next()){
    datos[0] = rs.getString("Id");
    datos[1] = rs.getString("NumeroDeParte");
    datos[2] = rs.getString("Descripcion");
    datos[3] = rs.getString("Cantidad");
    datos[4] = rs.getString("url");
    datos[5] = rs.getString("Modelo");
    datos[6] = rs.getString("Marca");
    datos[7] = rs.getString("Codigo");
    datos[8] = rs.getString("UM");
    datos[9] = rs.getString("Proveedor");
    datos[10] = rs.getString("Comentarios");
    miModelo.addRow(datos);
    }
    }catch(SQLException e){
    JOptionPane.showMessageDialog(this, "ERROR AL CARGAR DATOS"+e,"ERROR",JOptionPane.ERROR_MESSAGE);
    }
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
               Image newimg = img.getScaledInstance(399, 371, java.awt.Image.SCALE_SMOOTH);
               ImageIcon newIcon = new ImageIcon(newimg);
               if (newIcon == null) 
                   {
                       JOptionPane.showMessageDialog(null, "EL EQUIPO NO TIENE FOTO", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
                   } 
               else 
                   {
                       imagen.setIcon(newIcon);
                       imagen.setSize(imagen.getWidth(), imagen.getWidth()); 
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
 
        public void guardarDatos(){
        }
        
        
 
    public Inventario() {
        initComponents();
        verDatos();
        Tabla1.getTableHeader().setFont(new Font("Roboto", Font.PLAIN, 14));
        Tabla1.getTableHeader().setOpaque(false);
        Tabla1.getTableHeader().setBackground(new Color(0,0,0,0));
        Tabla1.getTableHeader().setForeground(Color.black);
        Tabla1.setRowHeight(25);
        
        deshabilitar();
        btnGuardar.setEnabled(false);
        btnEditar.setEnabled(false);
        btnEliminar.setEnabled(false);
        btnAñadir.setEnabled(true);
        
        ((javax.swing.plaf.basic.BasicInternalFrameUI) this.getUI()).setNorthPane(null);
    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        txtProveedor = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jButton6 = new javax.swing.JButton();
        txtComentarios = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        Tabla1 = new javax.swing.JTable();
        btnGuardar = new javax.swing.JButton();
        imagen = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        txtDescripcion = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        direccion = new javax.swing.JLabel();
        txtId = new javax.swing.JTextField();
        txtCantidad = new javax.swing.JTextField();
        txtUM = new javax.swing.JTextField();
        txtModelo = new javax.swing.JTextField();
        btnNuevo = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        btnEliminar = new javax.swing.JButton();
        btnAñadir = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtParte = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtCodigo = new javax.swing.JTextField();
        txtMarca = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();

        setBorder(null);
        setFrameIcon(null);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txtProveedor.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtProveedor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtProveedorKeyTyped(evt);
            }
        });
        jPanel1.add(txtProveedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(179, 305, 720, -1));

        jLabel3.setFont(new java.awt.Font("Roboto", 0, 16)); // NOI18N
        jLabel3.setText("NUMERO DE PARTE");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 110, -1, -1));

        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/cerca (2).png"))); // NOI18N
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton6, new org.netbeans.lib.awtextra.AbsoluteConstraints(1323, 28, 45, -1));

        txtComentarios.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtComentarios.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtComentariosKeyTyped(evt);
            }
        });
        jPanel1.add(txtComentarios, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 333, 720, -1));

        jLabel8.setFont(new java.awt.Font("Roboto", 0, 16)); // NOI18N
        jLabel8.setText("MODELO");
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 225, -1, -1));

        Tabla1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "NUMERO DE PARTE", "DESCRIPCION", "CANTIDAD", "URL", "MARCA", "MODELO", "CODIGO", "U.M", "PROVEEDOR", "COMENTARIOS"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        Tabla1.setFocusable(false);
        Tabla1.setSelectionBackground(new java.awt.Color(250, 0, 217));
        Tabla1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Tabla1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(Tabla1);
        if (Tabla1.getColumnModel().getColumnCount() > 0) {
            Tabla1.getColumnModel().getColumn(0).setResizable(false);
            Tabla1.getColumnModel().getColumn(0).setPreferredWidth(30);
            Tabla1.getColumnModel().getColumn(1).setResizable(false);
            Tabla1.getColumnModel().getColumn(2).setResizable(false);
            Tabla1.getColumnModel().getColumn(2).setPreferredWidth(300);
            Tabla1.getColumnModel().getColumn(3).setResizable(false);
            Tabla1.getColumnModel().getColumn(3).setPreferredWidth(80);
            Tabla1.getColumnModel().getColumn(4).setResizable(false);
            Tabla1.getColumnModel().getColumn(5).setResizable(false);
            Tabla1.getColumnModel().getColumn(6).setResizable(false);
            Tabla1.getColumnModel().getColumn(7).setResizable(false);
            Tabla1.getColumnModel().getColumn(8).setResizable(false);
            Tabla1.getColumnModel().getColumn(9).setResizable(false);
            Tabla1.getColumnModel().getColumn(10).setResizable(false);
        }

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(178, 373, 720, 189));

        btnGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/disquete.png"))); // NOI18N
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });
        jPanel1.add(btnGuardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 570, -1, -1));

        imagen.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 3, true));
        jPanel1.add(imagen, new org.netbeans.lib.awtextra.AbsoluteConstraints(946, 78, 399, 371));

        jLabel11.setFont(new java.awt.Font("Roboto", 0, 16)); // NOI18N
        jLabel11.setText("PROVEEDOR");
        jPanel1.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(75, 306, -1, -1));

        txtDescripcion.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtDescripcion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDescripcionActionPerformed(evt);
            }
        });
        txtDescripcion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDescripcionKeyTyped(evt);
            }
        });
        jPanel1.add(txtDescripcion, new org.netbeans.lib.awtextra.AbsoluteConstraints(179, 140, 720, -1));

        jLabel7.setFont(new java.awt.Font("Roboto", 0, 16)); // NOI18N
        jLabel7.setText("MARCA");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 200, -1, -1));

        jLabel9.setFont(new java.awt.Font("Roboto", 0, 16)); // NOI18N
        jLabel9.setText("CODIGO");
        jPanel1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 250, -1, -1));

        jLabel10.setFont(new java.awt.Font("Roboto", 0, 16)); // NOI18N
        jLabel10.setText("U.M");
        jPanel1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(139, 278, -1, -1));
        jPanel1.add(direccion, new org.netbeans.lib.awtextra.AbsoluteConstraints(950, 460, 399, 17));

        txtId.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtId.setEnabled(false);
        txtId.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtIdKeyTyped(evt);
            }
        });
        jPanel1.add(txtId, new org.netbeans.lib.awtextra.AbsoluteConstraints(179, 78, 720, -1));

        txtCantidad.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCantidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCantidadActionPerformed(evt);
            }
        });
        txtCantidad.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCantidadKeyTyped(evt);
            }
        });
        jPanel1.add(txtCantidad, new org.netbeans.lib.awtextra.AbsoluteConstraints(179, 168, 720, -1));

        txtUM.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtUM.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtUMKeyTyped(evt);
            }
        });
        jPanel1.add(txtUM, new org.netbeans.lib.awtextra.AbsoluteConstraints(179, 277, 720, -1));

        txtModelo.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtModelo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtModeloKeyTyped(evt);
            }
        });
        jPanel1.add(txtModelo, new org.netbeans.lib.awtextra.AbsoluteConstraints(179, 224, 720, -1));

        btnNuevo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/agregar-archivo.png"))); // NOI18N
        btnNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoActionPerformed(evt);
            }
        });
        jPanel1.add(btnNuevo, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 570, -1, -1));

        btnEditar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/lapiz.png"))); // NOI18N
        btnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarActionPerformed(evt);
            }
        });
        jPanel1.add(btnEditar, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 570, -1, -1));

        jLabel12.setFont(new java.awt.Font("Roboto", 0, 16)); // NOI18N
        jLabel12.setText("COMENTARIOS");
        jPanel1.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 333, -1, -1));

        btnEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/eliminar.png"))); // NOI18N
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });
        jPanel1.add(btnEliminar, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 570, -1, -1));

        btnAñadir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/adjuntar.png"))); // NOI18N
        btnAñadir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAñadirActionPerformed(evt);
            }
        });
        jPanel1.add(btnAñadir, new org.netbeans.lib.awtextra.AbsoluteConstraints(1220, 490, -1, -1));

        jLabel5.setFont(new java.awt.Font("Roboto", 0, 16)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel5.setText("CANTIDAD");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(85, 170, 80, -1));

        jLabel2.setFont(new java.awt.Font("Roboto", 0, 16)); // NOI18N
        jLabel2.setText("ID");
        jLabel2.setPreferredSize(new java.awt.Dimension(15, 19));
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 80, -1, -1));

        txtParte.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtParte.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtParteActionPerformed(evt);
            }
        });
        txtParte.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtParteKeyTyped(evt);
            }
        });
        jPanel1.add(txtParte, new org.netbeans.lib.awtextra.AbsoluteConstraints(179, 112, 720, -1));

        jLabel4.setFont(new java.awt.Font("Roboto", 0, 16)); // NOI18N
        jLabel4.setText("DESCRIPCION");
        jLabel4.setPreferredSize(new java.awt.Dimension(100, 19));
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 140, -1, -1));

        txtCodigo.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCodigo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCodigoKeyTyped(evt);
            }
        });
        jPanel1.add(txtCodigo, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 249, 719, -1));

        txtMarca.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtMarca.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtMarcaKeyTyped(evt);
            }
        });
        jPanel1.add(txtMarca, new org.netbeans.lib.awtextra.AbsoluteConstraints(179, 196, 720, -1));

        jPanel2.setBackground(new java.awt.Color(0, 165, 252));

        jLabel1.setFont(new java.awt.Font("Century Gothic", 1, 48)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("INVENTARIO");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(188, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(181, 181, 181))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 10, 650, -1));

        jMenu1.setText("File");

        jMenuItem1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/max.png"))); // NOI18N
        jMenuItem1.setText("Maximos y minimos");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        int n1 = 0;
        if(txtParte.getText().equals("")){
            JOptionPane.showMessageDialog(this, "CAMPO NUMERO DE VACIO","ADVERTENCIA",JOptionPane.WARNING_MESSAGE);
        }else if(txtDescripcion.getText().equals("")){
            JOptionPane.showMessageDialog(this, "CAMPO ID VACIO","ADVERTENCIA",JOptionPane.WARNING_MESSAGE);
        }else if(txtCantidad.getText().equals("")){
            JOptionPane.showMessageDialog(this, "CAMPO CANTIDAD ESTA VACIO","ADVERTENCIA",JOptionPane.WARNING_MESSAGE);
        }if (direccion.getText().isEmpty()){
            JOptionPane.showMessageDialog(this, "FALTA INGRESAR IMAGEN","ADVERTENCIA",JOptionPane.WARNING_MESSAGE);
        }else if (txtMarca.getText().isEmpty()){
            JOptionPane.showMessageDialog(this, "CAMPO MARCA VACIO","ADVERTENCIA",JOptionPane.WARNING_MESSAGE);
        } else if (txtModelo.getText().isEmpty()){
            JOptionPane.showMessageDialog(this, "CAMPO MODELO VACIO","ADVERTENCIA",JOptionPane.WARNING_MESSAGE);
        }else if (txtCodigo.getText().isEmpty()){
            JOptionPane.showMessageDialog(this, "CAMPO CODIGO VACIO","ADVERTENCIA",JOptionPane.WARNING_MESSAGE);
        }else if (txtUM.getText().isEmpty()){
            JOptionPane.showMessageDialog(this, "CAMPO UM VACIO","ADVERTENCIA",JOptionPane.WARNING_MESSAGE);
        }else if (txtProveedor.getText().isEmpty()){
            JOptionPane.showMessageDialog(this, "CAMPO PROVEEDOR VACIO","ADVERTENCIA",JOptionPane.WARNING_MESSAGE);
        }else if (txtComentarios.getText().isEmpty()){
            JOptionPane.showMessageDialog(this, "CAMPO COMENTARIOS VACIO","ADVERTENCIA",JOptionPane.WARNING_MESSAGE);
        }else{
            try{
                Connection con = null;
                Conexion con1 = new Conexion();
                con = con1.getConnection();
                Statement st = con.createStatement();
                String sql = "insert into Inventario (NumeroDeParte,Descripcion,Cantidad,Imagen,url,Marca,Modelo,Codigo,UM,Proveedor,Comentarios)values(?,?,?,?,?,?,?,?,?,?,?)";
                String ver = "select * from Inventario where NumeroDeParte like '"+txtParte.getText()+"'";
                ResultSet rs = st.executeQuery(ver);
                String datos[] = new String[8];
                while(rs.next()){
                datos[1] = rs.getString("NumeroDeParte");
                }
                if ((txtParte.getText().equals(datos[1]))){
                n1 = JOptionPane.showConfirmDialog(this, "YA EXISTE ESTE NUMERO DE PARTE, ¿DESEAS AÑADIR OTRO ESTE ARTICULO?","ADVERTENCIA",JOptionPane.WARNING_MESSAGE);
                } if (n1 == 0){
                PreparedStatement pst = con.prepareStatement(sql);
                pst.setString(1, txtParte.getText());
                pst.setString(2, txtDescripcion.getText());
                pst.setInt(3, Integer.parseInt(txtCantidad.getText()));
                Cargar_Archivo = new FileInputStream(direccion.getText());
                pst.setBinaryStream(4, Cargar_Archivo);
                pst.setString(5, direccion.getText());
                pst.setString(6, txtMarca.getText());
                pst.setString(7, txtModelo.getText());
                pst.setString(8, txtCodigo.getText());
                pst.setString(9, txtUM.getText());
                pst.setString(10, txtProveedor.getText());
                pst.setString(11, txtComentarios.getText());
                int n = pst.executeUpdate();
                if (n > 0){

                    JOptionPane.showMessageDialog(this, "DATOS GUARDADOS CORRECTAMENTE","GUARDADO",JOptionPane.INFORMATION_MESSAGE);
                    txtId.setText("");
                    txtParte.setText("");
                    txtDescripcion.setText("");
                    txtCantidad.setText("");
                    direccion.setText("");
                    txtMarca.setText("");
                    txtModelo.setText("");
                    txtCodigo.setText("");
                    imagen.setIcon(null);
                    txtMarca.setText("");
                    txtModelo.setText("");
                    txtCodigo.setText("");
                    txtUM.setText("");
                    txtProveedor.setText("");
                    txtComentarios.setText("");
                    limpiarTabla();
                    verDatos();
                }
              }
            }catch(SQLException | HeadlessException e){
                JOptionPane.showMessageDialog(this, "LOS DATOS NO HAN SIDO GUARDADOS"+e, "Error", JOptionPane.ERROR_MESSAGE);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Inventario.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnAñadirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAñadirActionPerformed
        FileFilter filter = new FileNameExtensionFilter("JPG","jpg");
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Seleccionar Imagen De Entrada");
        chooser.setFileFilter(filter);
        int Open = chooser.showOpenDialog(null);
        if (Open == JFileChooser.APPROVE_OPTION)
        {
            File Archivo = chooser.getSelectedFile();
            direccion.setText(String.valueOf(Archivo));
            Image Imagen = getToolkit().getImage(direccion.getText());
            Imagen = Imagen.getScaledInstance(399, 371, Image.SCALE_SMOOTH);
            imagen.setIcon(new ImageIcon(Imagen));
        }
    }//GEN-LAST:event_btnAñadirActionPerformed

    private void txtIdKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtIdKeyTyped
        char c = evt.getKeyChar();
        if(Character.isLowerCase(c))
        {
            String cad = (""+c).toUpperCase();
            c = cad.charAt(0);
            evt.setKeyChar(c);
        }
    }//GEN-LAST:event_txtIdKeyTyped

    private void txtParteKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtParteKeyTyped
        char c = evt.getKeyChar();
        if(Character.isLowerCase(c))
        {
            String cad = (""+c).toUpperCase();
            c = cad.charAt(0);
            evt.setKeyChar(c);
        }
        try{
        Connection con = null;
        Conexion con1 = new Conexion();
        con = con1.getConnection();
        Statement st = con.createStatement();
        String sql = "select * from Inventario where NumeroDeParte like '"+txtParte.getText()+"'";
        ResultSet rs = st.executeQuery(sql);
        String datos[] = new String[8];
        while(rs.next()){
        datos[1] = rs.getString("NumeroDeParte");
        }
        if(txtParte.getText().equals(datos[1])){
        txtParte.setForeground(Color.red);
        }else{
        txtParte.setForeground(Color.black);
        }
        }catch(SQLException e){
        JOptionPane.showMessageDialog(this, "ERROR AL BUSCAR ARTICULO"+e,"ERROR",JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_txtParteKeyTyped

    private void txtDescripcionKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDescripcionKeyTyped
        char c = evt.getKeyChar();
        if(Character.isLowerCase(c))
        {
            String cad = (""+c).toUpperCase();
            c = cad.charAt(0);
            evt.setKeyChar(c);
        }
    }//GEN-LAST:event_txtDescripcionKeyTyped

    private void txtCantidadKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCantidadKeyTyped
        char c = evt.getKeyChar();
        if(c<'0'||c>'9') evt.consume();
    }//GEN-LAST:event_txtCantidadKeyTyped

    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed
        habilitar();
        btnNuevo.setEnabled(false);
        btnAñadir.setEnabled(true);
        txtId.setEnabled(false);
        imagen.setIcon(null);
        txtId.setText("");
        txtParte.setText("");
        txtDescripcion.setText("");
        direccion.setText("");
        txtCantidad.setText("");
        txtMarca.setText("");
        txtModelo.setText("");
        txtCodigo.setText("");
        txtUM.setText("");
        txtProveedor.setText("");
        txtComentarios.setText("");
        btnEditar.setEnabled(false);
        btnEliminar.setEnabled(false);
    }//GEN-LAST:event_btnNuevoActionPerformed

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        if(txtParte.getText().equals("")){
            JOptionPane.showMessageDialog(this, "CAMPO NUMERO DE VACIO","ADVERTENCIA",JOptionPane.WARNING_MESSAGE);
        }else if(txtDescripcion.getText().equals("")){
            JOptionPane.showMessageDialog(this, "CAMPO ID VACIO","ADVERTENCIA",JOptionPane.WARNING_MESSAGE);
        }else if(txtCantidad.getText().equals("")){
            JOptionPane.showMessageDialog(this, "CAMPO CANTIDAD ESTA VACIO","ADVERTENCIA",JOptionPane.WARNING_MESSAGE);
        }if (direccion.getText().isEmpty()){
            JOptionPane.showMessageDialog(this, "FALTA INGRESAR IMAGEN","ADVERTENCIA",JOptionPane.WARNING_MESSAGE);
        }else if (txtMarca.getText().isEmpty()){
            JOptionPane.showMessageDialog(this, "CAMPO MARCA VACIO","ADVERTENCIA",JOptionPane.WARNING_MESSAGE);
        } else if (txtModelo.getText().isEmpty()){
            JOptionPane.showMessageDialog(this, "CAMPO MODELO VACIO","ADVERTENCIA",JOptionPane.WARNING_MESSAGE);
        }else if (txtCodigo.getText().isEmpty()){
            JOptionPane.showMessageDialog(this, "CAMPO CODIGO VACIO","ADVERTENCIA",JOptionPane.WARNING_MESSAGE);
        }else if (txtUM.getText().isEmpty()){
            JOptionPane.showMessageDialog(this, "CAMPO UM VACIO","ADVERTENCIA",JOptionPane.WARNING_MESSAGE);
        }else if (txtProveedor.getText().isEmpty()){
            JOptionPane.showMessageDialog(this, "CAMPO PROVEEDOR VACIO","ADVERTENCIA",JOptionPane.WARNING_MESSAGE);
        }else if (txtComentarios.getText().isEmpty()){
            JOptionPane.showMessageDialog(this, "CAMPO COMENTARIOS VACIO","ADVERTENCIA",JOptionPane.WARNING_MESSAGE);
        }else{
            try{
                Connection con = null;
                Conexion con1 = new Conexion();
                con = con1.getConnection();
                Statement st = con.createStatement();
                String sql = "update Inventario set NumeroDeParte = ?,Descripcion = ?,Cantidad = ?,Imagen = ?,url = ?,Marca = ?,Modelo = ?, Codigo = ?, UM = ?, Proveedor = ?, Comentarios = ? where NumeroDeParte = ?";
                PreparedStatement pst = con.prepareStatement(sql);
                pst.setString(1, txtParte.getText());
                pst.setString(2, txtDescripcion.getText());
                pst.setInt(3, Integer.parseInt(txtCantidad.getText()));
                Cargar_Archivo = new FileInputStream(direccion.getText());
                pst.setBinaryStream(4, Cargar_Archivo);
                pst.setString(5, direccion.getText());
                pst.setString(6, txtMarca.getText());
                pst.setString(7, txtModelo.getText());
                pst.setString(8, txtCodigo.getText());
                pst.setString(9, txtCodigo.getText());
                pst.setString(10, txtCodigo.getText());
                pst.setString(11, txtCodigo.getText());
                pst.setString(12, txtParte.getText());
                int n = pst.executeUpdate();
                if (n > 0){

                    JOptionPane.showMessageDialog(this, "DATOS ACTUALIZADOS CORRECTAMENTE","GUARDADO",JOptionPane.INFORMATION_MESSAGE);
                    txtId.setText("");
                    txtParte.setText("");
                    txtDescripcion.setText("");
                    txtCantidad.setText("");
                    direccion.setText("");
                    txtMarca.setText("");
                    txtModelo.setText("");
                    txtCodigo.setText("");
                    imagen.setIcon(null);
                    txtMarca.setText("");
                    txtModelo.setText("");
                    txtCodigo.setText("");
                    txtUM.setText("");
                    txtProveedor.setText("");
                    txtComentarios.setText("");
                    limpiarTabla();
                    verDatos();
                }
              
            }catch(SQLException | HeadlessException e){
                JOptionPane.showMessageDialog(this, "LOS DATOS NO HAN SIDO ACTUALIZADOS CORRECTAMENTE"+e, "Error", JOptionPane.ERROR_MESSAGE);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Inventario.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }//GEN-LAST:event_btnEditarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        this.dispose();
    }//GEN-LAST:event_jButton6ActionPerformed

    private void Tabla1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Tabla1MouseClicked
        try{
            txtParte.setEnabled(true);
            txtDescripcion.setEnabled(true);
            txtCantidad.setEnabled(true);
            txtMarca.setEnabled(true);
            txtModelo.setEnabled(true);
            int fila = Tabla1.getSelectedRow();
            String id = Tabla1.getValueAt(fila, 0).toString();
            txtId.setText(id);
            txtParte.setText(Tabla1.getValueAt(fila, 1).toString());
            txtDescripcion.setText(Tabla1.getValueAt(fila, 2).toString());
            txtCantidad.setText(Tabla1.getValueAt(fila, 3).toString());
            direccion.setText(Tabla1.getValueAt(fila, 4).toString());
            txtMarca.setText(Tabla1.getValueAt(fila, 5).toString());
            txtModelo.setText(Tabla1.getValueAt(fila, 6).toString());
            txtCodigo.setText(Tabla1.getValueAt(fila, 7).toString());
            txtUM.setText(Tabla1.getValueAt(fila, 8).toString());
            txtProveedor.setText(Tabla1.getValueAt(fila, 9).toString());
            txtComentarios.setText(Tabla1.getValueAt(fila, 10).toString());
            cargarfoto(id);
            btnNuevo.setEnabled(true);
            btnGuardar.setEnabled(true);
            btnEliminar.setEnabled(true);
            btnEditar.setEnabled(true);
            btnAñadir.setEnabled(true);
        }catch(Exception e){
        JOptionPane.showMessageDialog(this, "ERROR AL ESCOGER ARTICULO "+e,"ERROR",JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_Tabla1MouseClicked

    private void txtMarcaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtMarcaKeyTyped
        char c = evt.getKeyChar();
        if(Character.isLowerCase(c))
        {
            String cad = (""+c).toUpperCase();
            c = cad.charAt(0);
            evt.setKeyChar(c);
        }
    }//GEN-LAST:event_txtMarcaKeyTyped

    private void txtModeloKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtModeloKeyTyped
        char c = evt.getKeyChar();
        if(Character.isLowerCase(c))
        {
            String cad = (""+c).toUpperCase();
            c = cad.charAt(0);
            evt.setKeyChar(c);
        }
    }//GEN-LAST:event_txtModeloKeyTyped

    private void txtDescripcionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDescripcionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDescripcionActionPerformed

    private void txtCodigoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCodigoKeyTyped

    private void txtUMKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtUMKeyTyped
        char c = evt.getKeyChar();
        if(Character.isLowerCase(c))
        {
            String cad = (""+c).toUpperCase();
            c = cad.charAt(0);
            evt.setKeyChar(c);
        }
    }//GEN-LAST:event_txtUMKeyTyped

    private void txtProveedorKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtProveedorKeyTyped
       char c = evt.getKeyChar();
        if(Character.isLowerCase(c))
        {
            String cad = (""+c).toUpperCase();
            c = cad.charAt(0);
            evt.setKeyChar(c);
        }
    }//GEN-LAST:event_txtProveedorKeyTyped

    private void txtComentariosKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtComentariosKeyTyped
       char c = evt.getKeyChar();
        if(Character.isLowerCase(c))
        {
            String cad = (""+c).toUpperCase();
            c = cad.charAt(0);
            evt.setKeyChar(c);
        }
        
    }//GEN-LAST:event_txtComentariosKeyTyped

    private void txtParteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtParteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtParteActionPerformed

    private void txtCantidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCantidadActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCantidadActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        JFrame j = (JFrame) JOptionPane.getFrameForComponent(this);
        MaximosYMinimos m = new MaximosYMinimos(j,true);
        m.setVisible(true);
    }//GEN-LAST:event_jMenuItem1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable Tabla1;
    private javax.swing.JButton btnAñadir;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnNuevo;
    private javax.swing.JLabel direccion;
    private javax.swing.JLabel imagen;
    private javax.swing.JButton jButton6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField txtCantidad;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JTextField txtComentarios;
    private javax.swing.JTextField txtDescripcion;
    private javax.swing.JTextField txtId;
    private javax.swing.JTextField txtMarca;
    private javax.swing.JTextField txtModelo;
    private javax.swing.JTextField txtParte;
    private javax.swing.JTextField txtProveedor;
    private javax.swing.JTextField txtUM;
    // End of variables declaration//GEN-END:variables
}
