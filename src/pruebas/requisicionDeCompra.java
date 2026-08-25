package pruebas;

import Conexiones.Conexion;
import Conexiones.ConexionChat;
import VentanaEmergente.Inicio1.Espera;
import VentanaEmergente.Reportes.EditarArticuloRequisicion;
import VentanaEmergente.Requisiciones.Escoger;
import VentanaEmergente.Requisiciones.IDRequisicion;
import VentanaEmergente.Requisiciones.Material;
import com.mxrck.autocompleter.TextAutoCompleter;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import scrollPane.ScrollBarCustom;

public class requisicionDeCompra extends javax.swing.JInternalFrame implements ActionListener {

    public String parte = "";
    public String numeroEmpleado;
    public String noSe = "";
    public TextAutoCompleter ac;
    public TextAutoCompleter ac1;
    public String um = "";
    public String proveedor = "";
    public String requisitor = "";
    JFileChooser seleccionar, archivoExcel;
    File archivo = null, archivoEx;
    Escoger esc;
    Material mat;
    Espera espera = new Espera();
    String numEmpleado;
    JTable TablaReal = new JTable();

    public void agregarMaterial(String requi) {
        try {
            Connection con;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            String sql = "insert into materialrequisiciones (Codigo,Planos,Fecha, NumRequisicion, NumEmpleado) values(?,?,?,?,?)";
            PreparedStatement pst = con.prepareStatement(sql);
            Date d = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String fecha = sdf.format(d);
            for (int i = 0; i < Tabla1.getRowCount(); i++) {
                String planos = (Tabla1.getValueAt(i, 7) == null) ? "" : (Tabla1.getValueAt(i, 7).toString());
                String codigo = (Tabla1.getValueAt(i, 2) == null) ? "" : (Tabla1.getValueAt(i, 2).toString());
                if (!"".equals(planos)) {
                    pst.setString(1, codigo);
                    pst.setString(2, planos);
                    pst.setString(3, fecha);
                    pst.setString(4, requi);
                    pst.setString(5, numEmpleado);

                    pst.executeUpdate();
                }
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "ERROR: " + e, "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    public boolean comprobar() {
        boolean comprobar = true;
        for (int i = 0; i < Tabla1.getRowCount(); i++) {
            if (Tabla1.getValueAt(i, 1) == null) {
                comprobar = false;
            } else if (Tabla1.getValueAt(i, 2) == null) {
                comprobar = false;
            } else if (Tabla1.getValueAt(i, 3) == null) {
                comprobar = false;
            }
        }

        return comprobar;
    }

    public boolean buscarTornillos() {
        boolean ban = false;
        String cadenaDondeBuscar;
        String pal = "";
        try {

            Connection con;
            ConexionChat con1 = new ConexionChat();
            con = con1.getConnection();
            Statement st = con.createStatement();
            String sql = "select * from palabras";
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                cadenaDondeBuscar = rs.getString("palabra");
                String loQueQuieroBuscar = txtDescripcion.getText().toUpperCase();
                String[] palabras = loQueQuieroBuscar.split("\\W+");
                for (String palabra : palabras) {
                    if (cadenaDondeBuscar.equals(palabra)) {
                        ban = true;
                        pal = cadenaDondeBuscar;

                    }
                }
            }
            Conexion c = new Conexion();
            Connection con2 = c.getConnection();
            String sql2 = "select * from registroempleados where NumEmpleado like '" + numEmpleado + "'";
            Statement st2 = con2.createStatement();
            ResultSet rs2 = st2.executeQuery(sql2);
            String numero = "";
            while (rs2.next()) {
                numero = rs2.getString("Almacen");
            }
            if (numero.equals("SI")) {
                ban = false;
            }
            if (ban == true) {
                JOptionPane.showMessageDialog(this, "EL ARTICULO '" + pal + "' ESTA BLOQUEADO", "ADVERTENCIA", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            System.out.println("ERROR: " + e);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(requisicionDeCompra.class.getName()).log(Level.SEVERE, null, ex);
        }

        return ban;
    }

    public void empleado(String numEmpleado) {
        try {
            Connection con = null;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            String sql = "select * from registroEmpleados where NumEmpleado like '" + numEmpleado + "'";
            ResultSet rs = st.executeQuery(sql);
            String datos[] = new String[7];
            while (rs.next()) {
                datos[0] = rs.getString("NumEmpleado");
                datos[1] = rs.getString("Nombre");
                datos[2] = rs.getString("Apellido");
            }
            if (numEmpleado.equals(datos[0])) {
                numeroEmpleado = numEmpleado;
                txtNumeroEmpleado.setText(datos[1] + " " + datos[2]);
                txtNumeroEmpleado.setForeground(java.awt.Color.BLUE);
                txtNumeroEmpleado.setEditable(false);
                requisitor = datos[1] + " " + datos[2];
            } else {
                JOptionPane.showMessageDialog(this, "NO SE ENCONTRO EMPLEADO", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "ERROR AL ENCONTRAR DATOS" + e, "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void autoCompletarProyecto() {
        ac1 = new TextAutoCompleter(txtProyecto);
        try {
            Connection con = null;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            String sql = "select Proyecto from proyectos";
            String datos[] = new String[10];
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                datos[0] = rs.getString("Proyecto");
                ac1.addItem(datos[0]);
            }
            ac1.addItem("MATERIAL DE LIMPIEZA");
            ac1.addItem("MATERIAL DE OFICINA");
            ac1.addItem("MATERIAL DE MANTENIMIENTO");
            ac1.addItem("HERRAMIENTAS");
            ac1.addItem("SEGURIDAD");
            ac1.addItem("VENTAS");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "ERROR AL AUTOCOMPLETAR" + e, "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void autoCompletar() {
        ac = new TextAutoCompleter(txtCodigo);

        try {
            Connection con = null;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            String sql = "select NumeroDeParte from inventario";
            String datos[] = new String[10];
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                datos[0] = rs.getString("NumeroDeParte");

                ac.addItem(datos[0]);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "ERROR AL AUTOCOMPLETAR" + e, "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void extraer() {
        try {
            Connection con = null;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            String sql = "select Id from requisicion";
            ResultSet rs = st.executeQuery(sql);
            String datos[] = new String[5];
            while (rs.next()) {
                datos[0] = rs.getString("Id");
            }
            int d = Integer.parseInt(datos[0]);
            d = d + 1;
//            txtNumero.setText("" + d);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "ERROR AL INICIAR REQUISICIONES" + e);
        }
    }

    public double getInventario(String parte) {
        double cantidad;
        try {
            Connection con;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            String sql = "select NumeroDeParte, Cantidad, Id from inventario where NumeroDeParte like '" + parte + "' order by Id desc";
            ResultSet rs = st.executeQuery(sql);
            int cont = 0;
//            String codigo
            while (rs.next()) {
                if (cont == 0) {

                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "ERROR: " + e, "ERROR", JOptionPane.ERROR);
        }
        return 0;
    }
    
    public void limpiarTabla() {
        Tabla1.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{},
                new String[]{
                    "Parte", "Cantidad", "Codigo", "Descripcion", "Proyecto", "UM", "Proveedor", "Material", "Fecha Esperada"
                }
        ) {
            boolean[] canEdit = new boolean[]{
                false, false, false, false, false, true, true, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
        Tabla1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Tabla1MouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(Tabla1);
        if (Tabla1.getColumnModel().getColumnCount() > 0) {
            Tabla1.getColumnModel().getColumn(0).setResizable(false);
            Tabla1.getColumnModel().getColumn(1).setResizable(false);
            Tabla1.getColumnModel().getColumn(2).setResizable(false);
            Tabla1.getColumnModel().getColumn(3).setResizable(false);
            Tabla1.getColumnModel().getColumn(4).setResizable(false);
            Tabla1.getColumnModel().getColumn(5).setResizable(false);
            Tabla1.getColumnModel().getColumn(6).setResizable(false);
            Tabla1.getColumnModel().getColumn(7).setMinWidth(0);
            Tabla1.getColumnModel().getColumn(7).setPreferredWidth(0);
            Tabla1.getColumnModel().getColumn(7).setMaxWidth(0);
        }

        txtSubir.setText("");
    }

    public void limpiarTabla2() {
        TablaReal.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{},
                new String[]{
                    "PARTE", "CANTIDAD", "CODIGO", "DESCRIPCION", "PROYECTO", "UM", "PROVEEDOR", "MATERIAL"
                }
        ) {
            boolean[] canEdit = new boolean[]{
                false, false, false, false, false, true, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
    }

    public final void agregarArticulo(JTable tabla, String cantidad, int fila) {
        int cant = Integer.parseInt(cantidad);
        int cantTabla = Integer.parseInt(tabla.getValueAt(fila, 1).toString());
        cant += cantTabla;
        tabla.setValueAt(cant, fila, 1);
    }
    
    public requisicionDeCompra(String numero, String nombre) {
        initComponents();
        Date fechaIn = new Date();
        this.numEmpleado = numero;
        SimpleDateFormat fec = new SimpleDateFormat("dd/MM/yyyy");
        String fechaInicio = fec.format(fechaIn);
        txtFecha.setText(fechaInicio);
        btnX2.setVisible(false);
        noSe = "INTERNA";
        extraer();
        txtDescripcion.setLineWrap(true);
        txtDescripcion.setWrapStyleWord(true);
        ((javax.swing.plaf.basic.BasicInternalFrameUI) this.getUI()).setNorthPane(null);

        empleado(numero);
        autoCompletarProyecto();
        autoCompletar();

        limpiarTabla2();
        Tabla1.getTableHeader().setFont(new Font("Roboto", Font.BOLD, 14));
        Tabla1.getTableHeader().setOpaque(false);
        Tabla1.getTableHeader().setBackground(new Color(0, 78, 171));
        Tabla1.getTableHeader().setForeground(Color.white);
        Tabla1.setRowHeight(25);
        Tabla1.setShowGrid(false);

        jScrollPane2.getViewport().setBackground(new Color(255, 255, 255));
        jScrollPane2.setVerticalScrollBar(new ScrollBarCustom(new Color(0, 165, 255)));
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        jPopupMenu1 = new javax.swing.JPopupMenu();
        Editar = new javax.swing.JMenuItem();
        Eliminar = new javax.swing.JMenuItem();
        jPanel16 = new javax.swing.JPanel();
        panelNorte = new javax.swing.JPanel();
        pan = new javax.swing.JPanel();
        panelSalir = new javax.swing.JPanel();
        lblSalir = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel21 = new javax.swing.JPanel();
        jPanel22 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        jPanel17 = new javax.swing.JPanel();
        panelEstados = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtFecha = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        jPanel7 = new javax.swing.JPanel();
        rbtCompra = new javax.swing.JRadioButton();
        rbtCotizar = new javax.swing.JRadioButton();
        rbtEditar = new javax.swing.JRadioButton();
        jPanel18 = new javax.swing.JPanel();
        panelHerram = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        txtNumeroEmpleado = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        txtProyecto = new javax.swing.JTextField();
        btnX2 = new javax.swing.JButton();
        jPanel8 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        txtSubir = new javax.swing.JTextField();
        btnSubir = new javax.swing.JButton();
        jPanel19 = new javax.swing.JPanel();
        panelArticulos = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        txtCodigo = new javax.swing.JTextField();
        jPanel10 = new javax.swing.JPanel();
        jPanel24 = new javax.swing.JPanel();
        rbFecha = new javax.swing.JRadioButton();
        jLabel1 = new javax.swing.JLabel();
        fecha = new rojeru_san.rsdate.RSDateChooser();
        jPanel25 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtDescripcion = new javax.swing.JTextArea();
        jLabel7 = new javax.swing.JLabel();
        jPanel14 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        txtUM = new javax.swing.JTextField();
        jPanel12 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        txtCantidad = new javax.swing.JTextField();
        jPanel13 = new javax.swing.JPanel();
        jPanel20 = new javax.swing.JPanel();
        btnAgregar = new javax.swing.JButton();
        jPanel23 = new javax.swing.JPanel();
        btnAgregar1 = new javax.swing.JButton();
        jLabel16 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        panelTabla = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        Tabla1 = new javax.swing.JTable();
        jPanel15 = new javax.swing.JPanel();
        btnGuardar = new javax.swing.JButton();
        btnLimpiar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();
        importarExcel = new javax.swing.JMenuItem();

        Editar.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        Editar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/edit.png"))); // NOI18N
        Editar.setText("Editar                                         ");
        jPopupMenu1.add(Editar);

        Eliminar.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        Eliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/x.png"))); // NOI18N
        Eliminar.setText("Eliminar");
        jPopupMenu1.add(Eliminar);

        setBorder(null);
        getContentPane().setLayout(new java.awt.BorderLayout(10, 10));

        jPanel16.setBackground(new java.awt.Color(255, 255, 255));
        jPanel16.setLayout(new java.awt.BorderLayout(10, 10));

        panelNorte.setBackground(new java.awt.Color(255, 255, 255));
        panelNorte.setLayout(new java.awt.BorderLayout());

        pan.setBackground(new java.awt.Color(255, 255, 255));

        panelSalir.setBackground(new java.awt.Color(255, 255, 255));

        lblSalir.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
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

        panelNorte.add(pan, java.awt.BorderLayout.EAST);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new java.awt.BorderLayout());

        jPanel21.setBackground(new java.awt.Color(255, 255, 255));

        jPanel22.setBackground(new java.awt.Color(255, 255, 255));

        jLabel17.setFont(new java.awt.Font("Roboto", 1, 48)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(0, 165, 252));
        jLabel17.setText("Requisicion");
        jPanel22.add(jLabel17);

        jPanel21.add(jPanel22);

        jPanel2.add(jPanel21, java.awt.BorderLayout.CENTER);

        panelNorte.add(jPanel2, java.awt.BorderLayout.CENTER);

        jPanel16.add(panelNorte, java.awt.BorderLayout.NORTH);

        jPanel17.setBackground(new java.awt.Color(255, 255, 255));
        jPanel17.setLayout(new java.awt.BorderLayout(10, 10));

        panelEstados.setBackground(new java.awt.Color(255, 255, 255));
        panelEstados.setLayout(new java.awt.GridLayout(1, 3, 10, 0));

        jPanel4.setBackground(new java.awt.Color(217, 225, 240));
        jPanel4.setLayout(new java.awt.GridLayout(2, 0));

        jLabel2.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Fecha");
        jPanel4.add(jLabel2);

        txtFecha.setBackground(new java.awt.Color(204, 204, 204));
        txtFecha.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        txtFecha.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jPanel4.add(txtFecha);

        panelEstados.add(jPanel4);

        jPanel6.setLayout(new java.awt.GridLayout(1, 2));

        jRadioButton1.setBackground(new java.awt.Color(180, 198, 231));
        buttonGroup1.add(jRadioButton1);
        jRadioButton1.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        jRadioButton1.setText("Urgente");
        jPanel6.add(jRadioButton1);

        jRadioButton2.setBackground(new java.awt.Color(180, 198, 231));
        buttonGroup1.add(jRadioButton2);
        jRadioButton2.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        jRadioButton2.setText("Normal");
        jPanel6.add(jRadioButton2);

        panelEstados.add(jPanel6);

        jPanel7.setLayout(new java.awt.GridLayout(1, 2));

        rbtCompra.setBackground(new java.awt.Color(142, 169, 219));
        buttonGroup2.add(rbtCompra);
        rbtCompra.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        rbtCompra.setText("Compra");
        rbtCompra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtCompraActionPerformed(evt);
            }
        });
        jPanel7.add(rbtCompra);

        rbtCotizar.setBackground(new java.awt.Color(142, 169, 219));
        buttonGroup2.add(rbtCotizar);
        rbtCotizar.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        rbtCotizar.setText("Solo cotizar");
        jPanel7.add(rbtCotizar);

        panelEstados.add(jPanel7);

        rbtEditar.setBackground(new java.awt.Color(48, 84, 150));
        rbtEditar.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        rbtEditar.setForeground(new java.awt.Color(255, 255, 255));
        rbtEditar.setSelected(true);
        rbtEditar.setText("Editar requisicion");
        rbtEditar.setToolTipText("Al seleccionar esta opcion se dara permiso a compras para poder realizar cualquier cambio al momento de hacer la compra");
        rbtEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtEditarActionPerformed(evt);
            }
        });
        panelEstados.add(rbtEditar);

        jPanel17.add(panelEstados, java.awt.BorderLayout.NORTH);

        jPanel18.setBackground(new java.awt.Color(255, 255, 255));
        jPanel18.setLayout(new java.awt.BorderLayout(10, 10));

        panelHerram.setBackground(new java.awt.Color(255, 255, 255));
        panelHerram.setLayout(new java.awt.GridLayout(1, 3));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setLayout(new java.awt.GridLayout(2, 0));

        jLabel4.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Requisitor");
        jPanel3.add(jLabel4);

        txtNumeroEmpleado.setBackground(new java.awt.Color(255, 255, 255));
        txtNumeroEmpleado.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        txtNumeroEmpleado.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtNumeroEmpleado.setBorder(null);
        txtNumeroEmpleado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNumeroEmpleadoActionPerformed(evt);
            }
        });
        jPanel3.add(txtNumeroEmpleado);

        panelHerram.add(jPanel3);

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setLayout(new java.awt.BorderLayout());

        jLabel10.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("Proyecto");
        jLabel10.setMaximumSize(new java.awt.Dimension(74, 18));
        jLabel10.setMinimumSize(new java.awt.Dimension(74, 18));
        jLabel10.setName(""); // NOI18N
        jLabel10.setPreferredSize(new java.awt.Dimension(74, 18));
        jPanel5.add(jLabel10, java.awt.BorderLayout.NORTH);

        txtProyecto.setBackground(new java.awt.Color(255, 255, 255));
        txtProyecto.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        txtProyecto.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtProyecto.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(204, 204, 204)));
        txtProyecto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtProyectoActionPerformed(evt);
            }
        });
        jPanel5.add(txtProyecto, java.awt.BorderLayout.CENTER);

        btnX2.setBackground(new java.awt.Color(255, 255, 255));
        btnX2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnX2.setForeground(new java.awt.Color(153, 0, 0));
        btnX2.setMnemonic('q');
        btnX2.setText("    X    ");
        btnX2.setBorder(null);
        btnX2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnX2ActionPerformed(evt);
            }
        });
        jPanel5.add(btnX2, java.awt.BorderLayout.EAST);

        panelHerram.add(jPanel5);

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));
        jPanel8.setLayout(new java.awt.BorderLayout());

        jLabel8.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("Archivo de cotizacion");
        jLabel8.setMaximumSize(new java.awt.Dimension(74, 18));
        jLabel8.setMinimumSize(new java.awt.Dimension(74, 18));
        jLabel8.setName(""); // NOI18N
        jLabel8.setPreferredSize(new java.awt.Dimension(74, 18));
        jPanel8.add(jLabel8, java.awt.BorderLayout.NORTH);

        txtSubir.setEditable(false);
        txtSubir.setBackground(new java.awt.Color(255, 255, 255));
        txtSubir.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        txtSubir.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtSubir.setBorder(null);
        txtSubir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSubirActionPerformed(evt);
            }
        });
        jPanel8.add(txtSubir, java.awt.BorderLayout.CENTER);

        btnSubir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ImgAnimacion/subir_32.png"))); // NOI18N
        btnSubir.setBorder(null);
        btnSubir.setBorderPainted(false);
        btnSubir.setContentAreaFilled(false);
        btnSubir.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSubir.setFocusPainted(false);
        btnSubir.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnSubir.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/ImgAnimacion/subir_32.png"))); // NOI18N
        btnSubir.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/ImgAnimacion/subir_48.png"))); // NOI18N
        btnSubir.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        btnSubir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSubirActionPerformed(evt);
            }
        });
        jPanel8.add(btnSubir, java.awt.BorderLayout.EAST);

        panelHerram.add(jPanel8);

        jPanel18.add(panelHerram, java.awt.BorderLayout.NORTH);

        jPanel19.setLayout(new java.awt.BorderLayout());

        panelArticulos.setBackground(new java.awt.Color(255, 255, 255));
        panelArticulos.setForeground(new java.awt.Color(255, 255, 255));
        panelArticulos.setLayout(new java.awt.BorderLayout(10, 10));

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));
        jPanel9.setLayout(new java.awt.GridLayout(2, 0));

        jLabel6.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("          Numero de parte          ");
        jPanel9.add(jLabel6);

        txtCodigo.setBackground(new java.awt.Color(255, 255, 255));
        txtCodigo.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        txtCodigo.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCodigo.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(204, 204, 204)));
        txtCodigo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodigoActionPerformed(evt);
            }
        });
        jPanel9.add(txtCodigo);

        panelArticulos.add(jPanel9, java.awt.BorderLayout.WEST);

        jPanel10.setBackground(new java.awt.Color(255, 255, 255));
        jPanel10.setLayout(new java.awt.BorderLayout(15, 0));

        jPanel24.setBackground(new java.awt.Color(255, 255, 255));
        jPanel24.setLayout(new java.awt.BorderLayout());

        rbFecha.setBackground(new java.awt.Color(255, 255, 255));
        rbFecha.setSelected(true);
        rbFecha.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbFechaActionPerformed(evt);
            }
        });
        jPanel24.add(rbFecha, java.awt.BorderLayout.WEST);

        jLabel1.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Fecha esperada");
        jPanel24.add(jLabel1, java.awt.BorderLayout.CENTER);

        fecha.setFormatoFecha("dd-MM-yyyy");
        fecha.setFuente(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        fecha.setPreferredSize(new java.awt.Dimension(130, 40));
        jPanel24.add(fecha, java.awt.BorderLayout.PAGE_END);

        jPanel10.add(jPanel24, java.awt.BorderLayout.EAST);

        jPanel25.setBackground(new java.awt.Color(255, 255, 255));
        jPanel25.setLayout(new java.awt.BorderLayout());

        jScrollPane1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 2, true));

        txtDescripcion.setBackground(new java.awt.Color(255, 255, 255));
        txtDescripcion.setColumns(20);
        txtDescripcion.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        txtDescripcion.setLineWrap(true);
        txtDescripcion.setRows(5);
        txtDescripcion.setWrapStyleWord(true);
        txtDescripcion.setAutoscrolls(false);
        txtDescripcion.setBorder(null);
        txtDescripcion.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        txtDescripcion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDescripcionKeyTyped(evt);
            }
        });
        jScrollPane1.setViewportView(txtDescripcion);

        jPanel25.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jLabel7.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("Descripcion");
        jPanel25.add(jLabel7, java.awt.BorderLayout.NORTH);

        jPanel10.add(jPanel25, java.awt.BorderLayout.CENTER);

        panelArticulos.add(jPanel10, java.awt.BorderLayout.CENTER);

        jPanel14.setLayout(new java.awt.GridLayout(1, 3));

        jPanel11.setBackground(new java.awt.Color(255, 255, 255));
        jPanel11.setLayout(new java.awt.GridLayout(2, 0));

        jLabel11.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("U.M.");
        jPanel11.add(jLabel11);

        txtUM.setBackground(new java.awt.Color(255, 255, 255));
        txtUM.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        txtUM.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtUM.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(204, 204, 204)));
        txtUM.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtUMKeyTyped(evt);
            }
        });
        jPanel11.add(txtUM);

        jPanel14.add(jPanel11);

        jPanel12.setBackground(new java.awt.Color(255, 255, 255));
        jPanel12.setLayout(new java.awt.GridLayout(2, 0));

        jLabel9.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("Cantidad");
        jPanel12.add(jLabel9);

        txtCantidad.setBackground(new java.awt.Color(255, 255, 255));
        txtCantidad.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        txtCantidad.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCantidad.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(204, 204, 204)));
        txtCantidad.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCantidadKeyTyped(evt);
            }
        });
        jPanel12.add(txtCantidad);

        jPanel14.add(jPanel12);

        jPanel13.setBackground(new java.awt.Color(255, 255, 255));
        jPanel13.setLayout(new java.awt.BorderLayout());

        jPanel20.setBackground(new java.awt.Color(255, 255, 255));
        jPanel20.setLayout(new javax.swing.BoxLayout(jPanel20, javax.swing.BoxLayout.LINE_AXIS));

        btnAgregar.setBackground(new java.awt.Color(255, 255, 255));
        btnAgregar.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnAgregar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/mas.png"))); // NOI18N
        btnAgregar.setToolTipText("Agregar");
        btnAgregar.setBorder(null);
        btnAgregar.setBorderPainted(false);
        btnAgregar.setContentAreaFilled(false);
        btnAgregar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarActionPerformed(evt);
            }
        });
        jPanel20.add(btnAgregar);

        jPanel13.add(jPanel20, java.awt.BorderLayout.WEST);

        jPanel23.setBackground(new java.awt.Color(255, 255, 255));
        jPanel23.setLayout(new javax.swing.BoxLayout(jPanel23, javax.swing.BoxLayout.LINE_AXIS));

        btnAgregar1.setBackground(new java.awt.Color(255, 255, 255));
        btnAgregar1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnAgregar1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/agregar.png"))); // NOI18N
        btnAgregar1.setToolTipText("Agregar con varios proyectos");
        btnAgregar1.setBorder(null);
        btnAgregar1.setBorderPainted(false);
        btnAgregar1.setContentAreaFilled(false);
        btnAgregar1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAgregar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregar1ActionPerformed(evt);
            }
        });
        jPanel23.add(btnAgregar1);

        jPanel13.add(jPanel23, java.awt.BorderLayout.CENTER);

        jPanel14.add(jPanel13);

        panelArticulos.add(jPanel14, java.awt.BorderLayout.EAST);

        jLabel16.setText("   ");
        panelArticulos.add(jLabel16, java.awt.BorderLayout.SOUTH);

        jPanel19.add(panelArticulos, java.awt.BorderLayout.PAGE_START);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new java.awt.BorderLayout());

        panelTabla.setBackground(new java.awt.Color(255, 255, 255));
        panelTabla.setLayout(new java.awt.BorderLayout());

        jScrollPane2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));

        Tabla1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Parte", "Cantidad", "Codigo", "Descripcion", "Proyecto", "UM", "Proveedor", "Material", "Fecha Esperada"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, true, true, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        Tabla1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Tabla1MouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(Tabla1);
        if (Tabla1.getColumnModel().getColumnCount() > 0) {
            Tabla1.getColumnModel().getColumn(0).setResizable(false);
            Tabla1.getColumnModel().getColumn(1).setResizable(false);
            Tabla1.getColumnModel().getColumn(2).setResizable(false);
            Tabla1.getColumnModel().getColumn(3).setResizable(false);
            Tabla1.getColumnModel().getColumn(4).setResizable(false);
            Tabla1.getColumnModel().getColumn(5).setResizable(false);
            Tabla1.getColumnModel().getColumn(6).setResizable(false);
            Tabla1.getColumnModel().getColumn(7).setMinWidth(0);
            Tabla1.getColumnModel().getColumn(7).setPreferredWidth(0);
            Tabla1.getColumnModel().getColumn(7).setMaxWidth(0);
        }

        panelTabla.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        jPanel15.setBackground(new java.awt.Color(255, 255, 255));
        java.awt.GridBagLayout jPanel15Layout = new java.awt.GridBagLayout();
        jPanel15Layout.rowWeights = new double[] {1.0, 1.0, 1.0, 1.0};
        jPanel15.setLayout(jPanel15Layout);

        btnGuardar.setBackground(new java.awt.Color(255, 255, 255));
        btnGuardar.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/guardar_32.png"))); // NOI18N
        btnGuardar.setBorder(null);
        btnGuardar.setBorderPainted(false);
        btnGuardar.setContentAreaFilled(false);
        btnGuardar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 23, 0, 23);
        jPanel15.add(btnGuardar, gridBagConstraints);

        btnLimpiar.setBackground(new java.awt.Color(255, 255, 255));
        btnLimpiar.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnLimpiar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IconoC/cancelar.png"))); // NOI18N
        btnLimpiar.setToolTipText("Eliminar tabla");
        btnLimpiar.setBorder(null);
        btnLimpiar.setBorderPainted(false);
        btnLimpiar.setContentAreaFilled(false);
        btnLimpiar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnLimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimpiarActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 23, 0, 23);
        jPanel15.add(btnLimpiar, gridBagConstraints);

        btnEliminar.setBackground(new java.awt.Color(255, 255, 255));
        btnEliminar.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/eliminar_1.png"))); // NOI18N
        btnEliminar.setToolTipText("Eliminar articulo");
        btnEliminar.setBorder(null);
        btnEliminar.setBorderPainted(false);
        btnEliminar.setContentAreaFilled(false);
        btnEliminar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 23, 0, 23);
        jPanel15.add(btnEliminar, gridBagConstraints);

        btnEditar.setBackground(new java.awt.Color(255, 255, 255));
        btnEditar.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnEditar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/edit_32.png"))); // NOI18N
        btnEditar.setToolTipText("Eliminar articulo");
        btnEditar.setBorder(null);
        btnEditar.setBorderPainted(false);
        btnEditar.setContentAreaFilled(false);
        btnEditar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 23, 0, 23);
        jPanel15.add(btnEditar, gridBagConstraints);

        panelTabla.add(jPanel15, java.awt.BorderLayout.EAST);

        jPanel1.add(panelTabla, java.awt.BorderLayout.CENTER);

        jPanel19.add(jPanel1, java.awt.BorderLayout.CENTER);

        jPanel18.add(jPanel19, java.awt.BorderLayout.CENTER);

        jPanel17.add(jPanel18, java.awt.BorderLayout.CENTER);

        jPanel16.add(jPanel17, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel16, java.awt.BorderLayout.CENTER);

        jMenu1.setText("Arcvhivo");
        jMenuBar1.add(jMenu1);

        jMenu2.setText("Opciones");

        importarExcel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/excel_1.png"))); // NOI18N
        importarExcel.setText("Importar Excel");
        importarExcel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                importarExcelActionPerformed(evt);
            }
        });
        jMenu2.add(importarExcel);

        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtCodigoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodigoActionPerformed
        try {
            Connection con = null;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            String sql = "select * from Inventario where NumeroDeParte like '" + txtCodigo.getText() + "' or Codigo like '" + txtCodigo.getText() + "'";
            ResultSet rs = st.executeQuery(sql);
            String datos[] = new String[10];
            while (rs.next()) {
                datos[0] = rs.getString("Id");
                datos[1] = rs.getString("NumeroDeParte");
                datos[2] = rs.getString("Descripcion");
                datos[3] = rs.getString("UM");
                datos[4] = rs.getString("Proveedor");
                datos[5] = rs.getString("Codigo");
            }
            if (txtCodigo.getText().equals(datos[1]) || txtCodigo.getText().equals(datos[5])) {
                txtDescripcion.setText(datos[2]);
                parte = datos[0];
                um = datos[3];
                txtUM.setText(um);
                proveedor = datos[4];
                if (txtCodigo.getText().equals("4") || txtCodigo.getText().equals("5") || txtCodigo.getText().equals("6")) {
                    txtDescripcion.setEditable(true);
                }
            } else {
                JOptionPane.showMessageDialog(this, "NO SE ENCONTRO ARTICULO", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "NO SE PUEDE MOSTRAR INFORMACION", "ERROR", JOptionPane.ERROR_MESSAGE);
        }

    }//GEN-LAST:event_txtCodigoActionPerformed

    private void txtNumeroEmpleadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNumeroEmpleadoActionPerformed


    }//GEN-LAST:event_txtNumeroEmpleadoActionPerformed

    private void btnAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarActionPerformed
        boolean band = false;
        if (buscarTornillos() == false) {
            for (int i = 0; i < TablaReal.getRowCount(); i++) {
                if (txtCodigo.getText().equals(TablaReal.getValueAt(i, 2).toString())) {
                    band = true;
                    agregarArticulo(TablaReal, txtCantidad.getText(), i);
                }
            }
            for (int i = 0; i < Tabla1.getRowCount(); i++) {
                if (txtCodigo.getText().equals(Tabla1.getValueAt(i, 2).toString())) {
                    band = true;
                    agregarArticulo(Tabla1, txtCantidad.getText(), i);
                }
            }
            
            if (!band) {
                if (fecha.isVisible() && fecha.getDatoFecha() == null) {
                    JOptionPane.showConfirmDialog(this, "Debes seleccionar la fecha esperada", "Error", JOptionPane.ERROR_MESSAGE);
                } else if (txtCodigo.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "DEBES LLENAR EL CAMPO DE CODIGO", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
                } else if (txtDescripcion.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "DEBES LLENAR EL CAMPO DE DESCRIPCION", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
                } else if (!btnX2.isVisible()) {
                    JOptionPane.showMessageDialog(this, "DEBES LLENAR EL CAMPO DE PROYECTYO", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
                } else if (txtCantidad.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "DEBES LLENAR EL CAMPO DE CANTIDAD", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
                } else if (txtUM.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "DEBES LLENAR EL CAMPO DE UM", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
                } else {
                    String datos[] = new String[20];
                    DefaultTableModel miModelo = (DefaultTableModel) Tabla1.getModel();
                    DefaultTableModel Modelo = (DefaultTableModel) TablaReal.getModel();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

                    datos[0] = parte;
                    datos[1] = txtCantidad.getText();
                    datos[2] = txtCodigo.getText();
                    datos[3] = txtDescripcion.getText();
                    datos[4] = txtProyecto.getText();
                    datos[5] = txtUM.getText();
                    datos[6] = proveedor;
                    datos[7] = "";
                    if (fecha.isVisible()) {
                        datos[8] = sdf.format(fecha.getDatoFecha());
                    } else {
                        datos[8] = null;
                    }
                    try {
                        Connection con;
                        Conexion con1 = new Conexion();
                        con = con1.getConnection();
                        Statement st = con.createStatement();
                        String sql = "select * from inventario where NumeroDeParte like '" + txtCodigo.getText() + "'";
                        ResultSet rs = st.executeQuery(sql);
                        String datos1[] = new String[10];
                        while (rs.next()) {
                            datos1[1] = rs.getString("NumeroDeParte");
                        }
                        if (!txtCodigo.getText().equals(datos1[1])) {

                            String sql1 = "insert into inventario (NumeroDeParte, Descripcion, Cantidad) values (?,?,?)";
                            PreparedStatement pst = con.prepareStatement(sql1);

                            pst.setString(1, txtCodigo.getText());
                            pst.setString(2, txtDescripcion.getText());
                            pst.setString(3, "0");

                            pst.executeUpdate();
                        }
                    } catch (SQLException e) {
                        JOptionPane.showMessageDialog(this, "ERROR: " + e);
                    }
                    miModelo.addRow(datos);
                    Modelo.addRow(datos);
                    txtCantidad.setText("");
                    txtDescripcion.setText("");
                    parte = "";
                    txtCodigo.setText("");
                    fecha.setDatoFecha(null);
                }
            } else {
                txtCantidad.setText("");
                txtDescripcion.setText("");
                parte = "";
                txtCodigo.setText("");
                fecha.setDatoFecha(null);
            }
        }
    }//GEN-LAST:event_btnAgregarActionPerformed

    private void btnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarActionPerformed
        limpiarTabla();
        limpiarTabla2();
    }//GEN-LAST:event_btnLimpiarActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        espera.activar();
        espera.setVisible(true);
        String estado = "";
        String compra = "";
        boolean band = false;
        for (int i = 0; i < Tabla1.getRowCount(); i++) {
            if (Tabla1.getValueAt(i, 4).toString().equals("DIFERENTES")) {
                band = true;
            }
        }

        if (Tabla1.getRowCount() <= 0) {
            JOptionPane.showMessageDialog(this, "DEBES AGREGAR POR LO MENOS 1 ARTICULO", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
            espera.dispose();
        } else if (comprobar() == false) {
            JOptionPane.showMessageDialog(this, "ELEMENTOS DE LA TABLA NO ESTAN LLENOS, FAVOR DE LLENARLOS CORRECTAMENTE", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
            espera.dispose();
        } else {
            if (rbtCompra.isSelected()) {
                compra = "SI";
            } else if (rbtCotizar.isSelected()) {
                compra = "NO";
            }

            if (jRadioButton1.isSelected()) {
                estado = "URGENTE";
            } else if (jRadioButton2.isSelected()) {
                estado = "NORMAL";
            }
            if (jRadioButton1.isSelected() || jRadioButton2.isSelected()) {
                if (rbtCotizar.isSelected() || rbtCompra.isSelected()) {
                    try {
                        Connection con;
                        Conexion con1 = new Conexion();
                        con = con1.getConnection();
                        Statement st = con.createStatement();
                        String sql = "insert into Requisiciones (NumRequisicion,Codigo,Descripcion,Proyecto,Cantidad,NumParte,Requisitor,UM,Proveedor, FechaEsperada) values(?,?,?,?,?,?,?,?,?,?)";
                        String sql1 = "insert into Requisicion (NumeroEmpleado,Estatus,Estado,Progreso,Completado,Costo,NumeroCotizacion,Fecha,Comprar,Modificar) values(?,?,?,?,?,?,?,?,?,?)";
                        String sql4 = "insert into requisicionpdf (NumRequisicion, Pdf, Fecha, Nombre) values(?,?,?,?)";
                        PreparedStatement pst = con.prepareStatement(sql);
                        PreparedStatement pst1 = con.prepareStatement(sql1);
                        PreparedStatement pst4 = con.prepareStatement(sql4);

                        String slq3 = "insert into RequisicionesMuestra (Cantidad, Codigo, Descripcion, Proyecto, UM, Proveedor, NumRequisicion, Requisitor) values(?,?,?,?,?,?,?,?)";
                        PreparedStatement pst3 = con.prepareStatement(slq3);

                        byte[] pe = null;
                        if (archivo == null) {
                        } else {
                            pe = new byte[(int) archivo.length()];
                            try {
                                InputStream input = new FileInputStream(archivo);
                                input.read(pe);
                            } catch (IOException e) {

                            }
                        }

                        boolean editar = false;
                        if (rbtEditar.isSelected()) {
                            editar = true;
                        }

                        String coti;
                        if(pe == null){
                            coti = null;
                        }else{
                            coti = archivo.getName();
                        }
                        
                        pst1.setString(1, numeroEmpleado);
                        pst1.setString(2, "PEDIDO");
                        pst1.setString(3, estado);
                        pst1.setString(4, "EVALUACION");
                        pst1.setString(5, "NO");
                        pst1.setString(6, "");
                        pst1.setString(7, coti);
                        pst1.setString(8, txtFecha.getText());
                        pst1.setString(9, compra);
                        pst1.setBoolean(10, editar);

                        int n1 = pst1.executeUpdate();

                        if (n1 > 0) {
                            extraer();

                            String sql2 = "select Id from Requisicion";
                            ResultSet rs = st.executeQuery(sql2);
                            String datos[] = new String[3];
                            while (rs.next()) {
                                datos[1] = rs.getString("Id");
                            }
                            String id = datos[1];
                            
                            if(archivo != null){
                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                                Date d = new Date();
                                pst4.setString(1, id);
                                pst4.setBytes(2, pe);
                                pst4.setString(3, sdf.format(d));
                                pst4.setString(4, coti);

                                pst4.executeUpdate();
                            }
                            
                            int n = 0;
                            int n2 = 0;

                            if (band) {
                                //REQUISICIONES MUESTRA
                                for (int i = 0; i < TablaReal.getRowCount(); i++) {
                                    String proveedor = "";
                                    String um = "";
                                    String codigo = "";
                                    if ((TablaReal.getValueAt(i, 6)) == null) {
                                        proveedor = "";
                                    } else {
                                        proveedor = (TablaReal.getValueAt(i, 6).toString());
                                    }
                                    if ((TablaReal.getValueAt(i, 5)) == null) {
                                        um = "";
                                    } else {
                                        um = (TablaReal.getValueAt(i, 5).toString());
                                    }

                                    if ((TablaReal.getValueAt(i, 0)) == null) {
                                        codigo = "";
                                    } else {
                                        codigo = (TablaReal.getValueAt(i, 0).toString());
                                    }
                                    pst3.setString(1, id);
                                    pst3.setString(2, (TablaReal.getValueAt(i, 2).toString()));
                                    pst3.setString(3, (TablaReal.getValueAt(i, 3).toString()));
                                    pst3.setString(4, (TablaReal.getValueAt(i, 4).toString()));
                                    pst3.setString(5, (TablaReal.getValueAt(i, 1).toString()));
                                    pst3.setString(6, codigo);
                                    pst3.setString(7, requisitor);
                                    pst3.setString(8, um);
                                    pst3.setString(9, proveedor);

                                    n = pst3.executeUpdate();

                                }

                                for (int i = 0; i < Tabla1.getRowCount(); i++) {
                                    String proveedor = "";
                                    String um = "";
                                    String codigo = "";
                                    String fech;
                                    if ((Tabla1.getValueAt(i, 6)) == null) {
                                        proveedor = "";
                                    } else {
                                        proveedor = (Tabla1.getValueAt(i, 6).toString());
                                    }
                                    if ((Tabla1.getValueAt(i, 5)) == null) {
                                        um = "";
                                    } else {
                                        um = (Tabla1.getValueAt(i, 5).toString());
                                    }

                                    if ((Tabla1.getValueAt(i, 0)) == null) {
                                        codigo = "";
                                    } else {
                                        codigo = (Tabla1.getValueAt(i, 0).toString());
                                    }

                                    if ((Tabla1.getValueAt(i, 8)) == null) {
                                        fech = null;
                                    } else {
                                        fech = (Tabla1.getValueAt(i, 8).toString());
                                    }

                                    pst.setString(1, (Tabla1.getValueAt(i, 1).toString()));
                                    pst.setString(2, (Tabla1.getValueAt(i, 2).toString()));
                                    pst.setString(3, (Tabla1.getValueAt(i, 3).toString()));
                                    pst.setString(4, (Tabla1.getValueAt(i, 4).toString()));
                                    pst.setString(5, um);
                                    pst.setString(6, proveedor);
                                    pst.setString(7, id);
                                    pst.setString(8, requisitor);
                                    pst.setString(9, fech);
                                    n2 = pst.executeUpdate();

                                }
                            } else {
                                for (int i = 0; i < Tabla1.getRowCount(); i++) {
                                    String proveedor = "";
                                    String um = "";
                                    String codigo = "";
                                    String fech;
                                    if ((Tabla1.getValueAt(i, 6)) == null) {
                                        proveedor = "";
                                    } else {
                                        proveedor = (Tabla1.getValueAt(i, 6).toString());
                                    }
                                    if ((Tabla1.getValueAt(i, 5)) == null) {
                                        um = "";
                                    } else {
                                        um = (Tabla1.getValueAt(i, 5).toString());
                                    }

                                    if ((Tabla1.getValueAt(i, 0)) == null) {
                                        codigo = "";
                                    } else {
                                        codigo = (Tabla1.getValueAt(i, 0).toString());
                                    }

                                    if ((Tabla1.getValueAt(i, 8)) == null) {
                                        fech = null;
                                    } else {
                                        fech = (Tabla1.getValueAt(i, 8).toString());
                                    }
                                    pst.setString(1, id);
                                    pst.setString(2, (Tabla1.getValueAt(i, 2).toString()));
                                    pst.setString(3, (Tabla1.getValueAt(i, 3).toString()));
                                    pst.setString(4, (Tabla1.getValueAt(i, 4).toString()));
                                    pst.setString(5, (Tabla1.getValueAt(i, 1).toString()));
                                    pst.setString(6, codigo);
                                    pst.setString(7, requisitor);
                                    pst.setString(8, um);
                                    pst.setString(9, proveedor);
                                    pst.setString(10, fech);

                                    n = pst.executeUpdate();
                                }
                            }

                            agregarMaterial(id);

                            if (n > 0) {
                                espera.band = false;
                                espera.dispose();
                                JFrame f = (JFrame) JOptionPane.getFrameForComponent(this);
                                IDRequisicion requi = new IDRequisicion(f, true);
                                requi.setLocationRelativeTo(f);
                                requi.lblID.setText(id);
                                requi.setVisible(true);
                            } else {
                                JOptionPane.showMessageDialog(null, "ERROR AL INSERTAR DATOS", "ERROR", JOptionPane.ERROR_MESSAGE);
                            }
                            limpiarTabla();
                        }
                    } catch (SQLException e) {
                        JOptionPane.showMessageDialog(this, "ERROR AL MOSTRAR INFORMACION" + e, "ERROR", JOptionPane.ERROR_MESSAGE);
                        espera.dispose();
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "DEBES SELECCIONAR COMPRA O SOLO COTIZAR", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
                    espera.dispose();
                }
            } else {
                JOptionPane.showMessageDialog(this, "DEBES SELECCIONAR ESTADO", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
                espera.dispose();
            }
        }

    }//GEN-LAST:event_btnGuardarActionPerformed

    private void txtProyectoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtProyectoActionPerformed
        try {
            Connection con = null;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            String sql = "select * from Proyectos where Proyecto like '" + txtProyecto.getText() + "'";
            ResultSet rs = st.executeQuery(sql);
            String datos[] = new String[10];
            while (rs.next()) {
                datos[0] = rs.getString("Proyecto");
            }
            if (txtProyecto.getText().equals(datos[0]) || txtProyecto.getText().equals("MATERIAL DE OFICINA") || txtProyecto.getText().equals("MATERIAL DE LIMPIEZA") || txtProyecto.getText().equals("MATERIAL DE MANTENIMIENTO") || txtProyecto.getText().equals("HERRAMIENTAS") || txtProyecto.getText().equals("SEGURIDAD") || txtProyecto.getText().equals("VENTAS")) {
                txtProyecto.setEditable(false);
                txtProyecto.setForeground(java.awt.Color.blue);
                btnX2.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "ESTE PROYECTO NO ESTA EN LA BASE DE DATOS");
                txtProyecto.setText("");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "ERROR: " + e);
        }
    }//GEN-LAST:event_txtProyectoActionPerformed

    private void btnX2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnX2ActionPerformed
        txtProyecto.setEditable(true);
        txtProyecto.setText("");
        txtProyecto.setForeground(java.awt.Color.black);
        btnX2.setVisible(false);
    }//GEN-LAST:event_btnX2ActionPerformed

    private void txtDescripcionKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDescripcionKeyTyped
        char c = evt.getKeyChar();
        if (Character.isLowerCase(c)) {
            String cad = ("" + c);
            c = cad.charAt(0);
            evt.setKeyChar(c);
        }
    }//GEN-LAST:event_txtDescripcionKeyTyped

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        int fila = Tabla1.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(this, "DEBES SELECCIONAR UNA FILA", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
        } else {
            DefaultTableModel miModelo = (DefaultTableModel) Tabla1.getModel();
            DefaultTableModel Modelo = (DefaultTableModel) TablaReal.getModel();
            String Num = Tabla1.getValueAt(fila, 2).toString();
            miModelo.removeRow(fila);
            for (int i = TablaReal.getRowCount() - 1; i >= 0; i--) {
                if (TablaReal.getValueAt(i, 2).toString().equals(Num)) {
                    Modelo.removeRow(i);
                }
            }

        }
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnSubirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSubirActionPerformed
        seleccionar = new JFileChooser();
        archivo = null;
        seleccionar.setFileFilter(new FileNameExtensionFilter("PDF (*.pdf)", "pdf"));
        if (seleccionar.showDialog(null, "SELECCIONAR ARCHIVO") == JFileChooser.APPROVE_OPTION) {
            archivo = seleccionar.getSelectedFile();
            txtSubir.setText(archivo.getName());
            if (archivo.getName().endsWith("pdf")) {
//                ImageIcon icono = new ImageIcon(getClass().getResource("/Imagenes/pdf_32.png"));
//                lblIcono.setIcon(icono);
            }
        }
    }//GEN-LAST:event_btnSubirActionPerformed

    private void txtSubirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSubirActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSubirActionPerformed

    private void importarExcelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_importarExcelActionPerformed
         if (txtProyecto.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "DEBES LLENAR EL CAMPO DE PROYECTO", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
        } else {
            archivoExcel = new JFileChooser();
            archivoEx = null;
            Workbook book = null;
            DefaultTableModel modelo = (DefaultTableModel) Tabla1.getModel();
            archivoExcel.setFileFilter(new FileNameExtensionFilter("Excel (*.xlsx)", "xlsx"));
            if (archivoExcel.showDialog(null, "Abrir") == JFileChooser.APPROVE_OPTION) {
                archivoEx = archivoExcel.getSelectedFile();
                String arch = archivoEx.getAbsolutePath();
                try {
                    book = WorkbookFactory.create(new FileInputStream(arch));
                    Sheet hoja = book.getSheetAt(0);
                    Iterator FilaIterator = hoja.rowIterator();
                    int IndiceFila = -1;
                    int cont = -1;
                    while (FilaIterator.hasNext()) {
                        IndiceFila++;
                        Row fila = (Row) FilaIterator.next();
                        if (IndiceFila > 0) {

                            cont++;
                            Iterator ColumnaIterator = fila.cellIterator();
                            Object[] ListaColumna = new Object[9999];

                            modelo.addRow(ListaColumna);
                            int IndiceColumna = -1;
                            while (ColumnaIterator.hasNext()) {
                                IndiceColumna++;
                                Cell celda = (Cell) ColumnaIterator.next();
                                if (IndiceColumna >= 0 && IndiceColumna < 4) {
                                    String a = "";

                                    switch (celda.getCellType()) {
                                        case NUMERIC:
                                            ListaColumna[IndiceColumna] = (celda.getNumericCellValue());
                                            break;
                                        case STRING:
                                            ListaColumna[IndiceColumna] = celda.getStringCellValue();
                                            break;
                                        case BOOLEAN:
                                            ListaColumna[IndiceColumna] = celda.getBooleanCellValue();
                                            break;
                                        default:
                                            break;
                                    }
                                    System.out.println(IndiceColumna + ", " + ListaColumna[IndiceColumna]);
                                    switch (IndiceColumna) {
                                        case 0:
                                            //CODIGO
                                            Tabla1.setValueAt(ListaColumna[IndiceColumna], cont, 2);
                                            break;
                                        case 1:
                                            //DESCRIPCION
                                            Tabla1.setValueAt(ListaColumna[IndiceColumna], cont, 3);
                                            break;
                                        case 2:
                                            //CANTIDAD
                                            Tabla1.setValueAt(ListaColumna[IndiceColumna], cont, 1);
                                            break;
                                        case 4:
                                            //PROVEEDOR
                                            Tabla1.setValueAt(ListaColumna[IndiceColumna], cont, 6);
                                            break;
                                        case 3:
                                            //PROVEEDOR
                                            Tabla1.setValueAt(ListaColumna[IndiceColumna], cont, 5);
                                            break;
                                        default:
                                            break;
                                    }
                                }
                            }

                        }
                    }

                    book.close();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, "ERROR AL CREAR LIBRO" + e, "ERROR", JOptionPane.ERROR_MESSAGE);
                    Logger.getLogger(OrdenDeCompra.class.getName()).log(Level.SEVERE, null, e);

                }

            }

        }
        for (int i = 0; i < Tabla1.getRowCount(); i++) {
            Tabla1.setValueAt(txtProyecto.getText(), i, 4);
        }
    }//GEN-LAST:event_importarExcelActionPerformed

    private void txtCantidadKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCantidadKeyTyped
        char c = evt.getKeyChar();
        if (c < '0' || c > '9')
            evt.consume();
    }//GEN-LAST:event_txtCantidadKeyTyped

    private void btnAgregar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregar1ActionPerformed
        boolean band = false;
        for (int i = 0; i < TablaReal.getRowCount(); i++) {
            if (txtCodigo.getText().equals(TablaReal.getValueAt(i, 2).toString())) {
                band = true;
            }
        }
        for (int i = 0; i < Tabla1.getRowCount(); i++) {
            if (txtCodigo.getText().equals(TablaReal.getValueAt(i, 2).toString())) {
                band = true;
            }
        }

        if (band == true) {
            JOptionPane.showMessageDialog(this, "ESTE NUMERO DE PARTE YA ESTA INCLUIDO", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
            txtCodigo.setText("");
            txtDescripcion.setText("");
            txtCantidad.setText("");
        } else if (txtCodigo.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "DEBES SELECCIONAR UN ARTICULO", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
        } else if (txtDescripcion.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "DEBES LLENAR LA DESCRIPCION", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
        } else {
            JFrame frame = (JFrame) JOptionPane.getFrameForComponent(this);
            esc = new Escoger(frame, true, txtCodigo.getText());
            esc.btnGuardar.addActionListener(this);
            esc.setVisible(true);
        }
    }//GEN-LAST:event_btnAgregar1ActionPerformed

    private void txtUMKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtUMKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtUMKeyTyped

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

    private void rbtCompraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtCompraActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rbtCompraActionPerformed

    private void rbtEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtEditarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rbtEditarActionPerformed

    private void Tabla1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Tabla1MouseClicked
        if (evt.getClickCount() == 2) {
            if (Tabla1.getSelectedColumn() > -1) {
                JFrame f = (JFrame) JOptionPane.getFrameForComponent(this);
                mat = new Material(f, true, Tabla1.getSelectedRow());
                DefaultTableModel miModelo = (DefaultTableModel) mat.Tabla1.getModel();
                String text = Tabla1.getValueAt(Tabla1.getSelectedRow(), 7).toString();
                String buscar = "-";
                boolean band = true;
                int aux = 0;
                int aux2;
                char arreglo[] = text.toCharArray();
                for (int j = 0; j < text.length(); j++) {
                    String letra = String.valueOf(arreglo[j]);
                    if (buscar.equalsIgnoreCase(letra)) {
                        aux2 = j;
                        String ad = (text.substring(aux, aux2));
                        aux = j + 1;
                        String dat[] = new String[3];
                        dat[0] = ad;
                        dat[1] = Tabla1.getValueAt(Tabla1.getSelectedRow(), 2).toString();
                        miModelo.addRow(dat);
                    }
                }

                mat.lblMaterial.setText(Tabla1.getValueAt(Tabla1.getSelectedRow(), 2).toString());
                mat.btnGuardar.addActionListener(this);
                mat.setVisible(true);
            } else {
            }
        }
    }//GEN-LAST:event_Tabla1MouseClicked

    private void rbFechaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbFechaActionPerformed
        if (rbFecha.isSelected()) {
            fecha.setVisible(true);
        } else {
            fecha.setVisible(false);
        }
    }//GEN-LAST:event_rbFechaActionPerformed

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        if (Tabla1.getSelectedRow() < 0) {
            JOptionPane.showMessageDialog(this, "Debes seleccionar un articulo de la tabla", "Advertencia", JOptionPane.WARNING_MESSAGE);
        } else {
            JFrame f = (JFrame) JOptionPane.getFrameForComponent(this);
            EditarArticuloRequisicion editar = new EditarArticuloRequisicion(f, true, ac1);
            editar.setLocationRelativeTo(f);
            int fila = Tabla1.getSelectedRow();
            editar.txtCantidad.setText(Tabla1.getValueAt(fila, 1).toString());
            editar.txtCodigo.setText(Tabla1.getValueAt(fila, 2).toString());
            editar.txtDescripcion.setText(Tabla1.getValueAt(fila, 3).toString());
            editar.txtProyecto.setText(Tabla1.getValueAt(fila, 4).toString());
            editar.txtUM.setText(Tabla1.getValueAt(fila, 5).toString());
            editar.txtProveedor.setText(Tabla1.getValueAt(fila, 6).toString());
            if (Tabla1.getValueAt(fila, 8) != null) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    editar.txtFecha.setDatoFecha(sdf.parse(Tabla1.getValueAt(fila, 8).toString()));
                } catch (ParseException ex) {
                    Logger.getLogger(requisicionDeCompra.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            String edit[] = editar.getEdit();
            if (edit != null) {
                Tabla1.setValueAt(edit[1], fila, 1);
                Tabla1.setValueAt(edit[2], fila, 2);
                Tabla1.setValueAt(edit[3], fila, 3);
                Tabla1.setValueAt(edit[4], fila, 4);
                Tabla1.setValueAt(edit[5], fila, 5);
                Tabla1.setValueAt(edit[6], fila, 6);
                Tabla1.setValueAt(edit[8], fila, 8);
            }
        }
    }//GEN-LAST:event_btnEditarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem Editar;
    private javax.swing.JMenuItem Eliminar;
    private javax.swing.JTable Tabla1;
    private javax.swing.JButton btnAgregar;
    private javax.swing.JButton btnAgregar1;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnLimpiar;
    private javax.swing.JButton btnSubir;
    private javax.swing.JButton btnX2;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private rojeru_san.rsdate.RSDateChooser fecha;
    private javax.swing.JMenuItem importarExcel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel24;
    private javax.swing.JPanel jPanel25;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblSalir;
    private javax.swing.JPanel pan;
    private javax.swing.JPanel panelArticulos;
    private javax.swing.JPanel panelEstados;
    private javax.swing.JPanel panelHerram;
    private javax.swing.JPanel panelNorte;
    private javax.swing.JPanel panelSalir;
    private javax.swing.JPanel panelTabla;
    private javax.swing.JRadioButton rbFecha;
    private javax.swing.JRadioButton rbtCompra;
    private javax.swing.JRadioButton rbtCotizar;
    private javax.swing.JRadioButton rbtEditar;
    private javax.swing.JTextField txtCantidad;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JTextArea txtDescripcion;
    private javax.swing.JLabel txtFecha;
    private javax.swing.JTextField txtNumeroEmpleado;
    private javax.swing.JTextField txtProyecto;
    private javax.swing.JTextField txtSubir;
    private javax.swing.JTextField txtUM;
    // End of variables declaration//GEN-END:variables

    @Override
    public void actionPerformed(ActionEvent e) {

        if (esc != null) {
            if (e.getSource() == esc.btnGuardar) {
                boolean band = false;
                boolean can = false;
                String a = "", b = "";
                try {
                    Connection con = null;
                    Conexion con1 = new Conexion();
                    con = con1.getConnection();

                    for (int i = 0; i < esc.Tabla1.getRowCount(); i++) {
                        Statement st = con.createStatement();
                        if (esc.Tabla1.getValueAt(i, 1).toString().equals("")) {

                        }
                        String sql = "select * from Proyectos where Proyecto like '" + esc.Tabla1.getValueAt(i, 1).toString() + "'";
                        ResultSet rs = st.executeQuery(sql);
                        String d = "";
                        while (rs.next()) {
                            d = rs.getString("Proyecto");
                        }

                        if (esc.Tabla1.getValueAt(i, 1).equals("")) {
                            band = true;
                            a = a + String.valueOf(i + 1) + ", ";
                        } else {
                            if (esc.Tabla1.getValueAt(i, 1).equals(" ") && !esc.Tabla1.getValueAt(i, 1).toString().equals(d) && !esc.Tabla1.getValueAt(i, 1).toString().equals("MATERIAL DE LIMPIEZA") && !esc.Tabla1.getValueAt(i, 1).toString().equals("MATERIAL DE OFICINA") && !esc.Tabla1.getValueAt(i, 1).toString().equals("MATERIAL DE MANTENIMIENTO") && !esc.Tabla1.getValueAt(i, 1).toString().equals("HERRAMIENTAS") && !esc.Tabla1.getValueAt(i, 1).toString().equals("SEGURIDAD")) {
                                band = true;
                                a = a + String.valueOf(i + 1) + ", ";
                            }
                        }

                        if (esc.Tabla1.getValueAt(i, 2) == null) {
                            can = true;
                            b = b + String.valueOf(i + 1) + ", ";
                        } else {
                            if (esc.Tabla1.getValueAt(i, 2).equals("")) {
                                can = true;
                                b = b + String.valueOf(i + 1) + ", ";
                            }
                        }
                    }

                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(this, "ERROR: " + ex, "ERROR", JOptionPane.ERROR_MESSAGE);
                }

                if (band == true) {
                    JOptionPane.showMessageDialog(this, "LA FILA NO. " + (a) + " NO COINCIDE CON LOS PROYECTOS");
                } else if (can == true) {
                    JOptionPane.showMessageDialog(this, "LA FILA NO. " + (b) + " NO HAY CANTIDAD");
                } else {
                    int tot = 0;
                    for (int i = 0; i < esc.Tabla1.getRowCount(); i++) {
                        tot = tot + (Integer.parseInt(esc.Tabla1.getValueAt(i, 2).toString()));
                    }
                    esc.lblCantidad.setText(String.valueOf(tot));

                    DefaultTableModel miModelo = (DefaultTableModel) Tabla1.getModel();
                    DefaultTableModel Modelo = (DefaultTableModel) TablaReal.getModel();

                    String datos[] = new String[10];
                    datos[0] = "";
                    datos[1] = this.esc.lblCantidad.getText();
                    datos[2] = this.esc.lblParte.getText();
                    datos[3] = txtDescripcion.getText();
                    datos[4] = "DIFERENTES";
                    datos[5] = um;
                    datos[6] = proveedor;

                    miModelo.addRow(datos);
                    for (int i = 0; i < esc.Tabla1.getRowCount(); i++) {
                        datos[0] = "";
                        datos[1] = this.esc.Tabla1.getValueAt(i, 2).toString();
                        datos[2] = this.esc.lblParte.getText();
                        datos[3] = txtDescripcion.getText();
                        datos[4] = this.esc.Tabla1.getValueAt(i, 1).toString();
                        datos[5] = um;
                        datos[6] = proveedor;

                        Modelo.addRow(datos);
                    }
                    esc.dispose();
                    txtCodigo.setText("");
                    txtDescripcion.setText("");
                    txtCantidad.setText("");
                }
            }
        }
        if (mat != null) {
            if (e.getSource() == mat.btnGuardar) {
                String cadena = "";
                for (int i = 0; i < mat.Tabla1.getRowCount(); i++) {
                    if (Tabla1.getValueAt(mat.fila, 7).toString().equals("") && mat.Tabla1.getRowCount() == 0) {
                        cadena = mat.Tabla1.getValueAt(i, 0).toString() + "-";
                    } else {
                        cadena = cadena + mat.Tabla1.getValueAt(i, 0).toString() + "-";
                    }
                }
                Tabla1.setValueAt(cadena, mat.fila, 7);
                mat.dispose();
            }
        }
    }
}
