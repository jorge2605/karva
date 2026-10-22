package pruebas;

import Conexiones.Conexion;
import VO.whatsappMessage;
import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import Controlador.FileTransferHandler;
import Modelo.javamail;
import VentanaEmergente.Cotizacion.AgregarCliente;
import VentanaEmergente.Ventas.CrearOrdenTrabajo;
import VentanaEmergente.Ventas.correos;
import com.mxrck.autocompleter.TextAutoCompleter;
import java.awt.Window;
import java.time.format.DateTimeFormatter;
import java.util.Stack;
import javax.swing.DropMode;
import javax.swing.SwingUtilities;

public class Ventas extends javax.swing.JInternalFrame {

    public JFileChooser seleccionar;
    public JFileChooser selec;
    public JFileChooser sel;
    public File archivo = null;
    public File cotizacion = null;
    public File oc = null;
    public FileTransferHandler trOC;
    public FileTransferHandler trSpe;
    public FileTransferHandler trCot;
    private TextAutoCompleter auResponsables;
    public String numEmpleado;

    public final String aumento() {
        int in = 0;
        String fin = "";
        try {
            Date fecha = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("ddMMyy");
            String fecha1 = sdf.format(fecha);
            Connection con = null;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            String sql = "select Proyecto from proyectos";
            ResultSet rs = st.executeQuery(sql);
            String datos[] = new String[10];
            while (rs.next()) {
                datos[0] = rs.getString("Proyecto");
            }
            int pos = datos[0].indexOf(' ');
            String aco = datos[0].substring(0, pos);
            in = Integer.parseInt(aco);
            in++;
            fin = in + " " + fecha1;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "ERROR: " + e);
        }
        return fin;
    }

    public final void verNumero() {
        try {
            Connection con = null;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            String sql = "select Id from ventas";
            ResultSet rs = st.executeQuery(sql);
            String datos[] = new String[10];
            while (rs.next()) {
                datos[1] = rs.getString("Id");
            }
            int s = Integer.parseInt(datos[1]);
        } catch (SQLException E) {
            JOptionPane.showMessageDialog(this, "ERROR AL INICIAR SIGUIENTE VENTA " + E);
        }
    }

    public final void addTransfer() {
        trOC = new FileTransferHandler();
        txtOC.setDragEnabled(true);
        txtOC.setDropMode(DropMode.INSERT);
        trOC.setComponent(txtOC);
        txtOC.setTransferHandler(trOC);

        trSpe = new FileTransferHandler();
        txtSpec.setDragEnabled(true);
        txtSpec.setDropMode(DropMode.INSERT);
        trSpe.setComponent(txtSpec);
        txtSpec.setTransferHandler(trSpe);

        trCot = new FileTransferHandler();
        txtCoti.setDragEnabled(true);
        txtCoti.setDropMode(DropMode.INSERT);
        trCot.setComponent(txtCoti);
        txtCoti.setTransferHandler(trCot);
    }

    public final String revisarProyecto(String proyecto) {
        String proy = null;
        try {
            Connection con;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            String sql = "select * from proyectos where Proyecto like '" + proyecto + "'";
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                proy = rs.getString("Proyecto");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "ERROR: " + e, "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        return proy;
    }

    public final void agregarClientes() {
        try {
            Connection con = new Conexion().getConnection();
            Statement st = con.createStatement();
            String sql = "select * from clientes order by Nombre";
            ResultSet rs = st.executeQuery(sql);
            cmbCliente.removeAllItems();
            cmbCliente.addItem("Seleccionar");
            while (rs.next()) {
                cmbCliente.addItem(rs.getString("Nombre"));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error: " + e, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public final void addCliente() {
        try {
            try (Connection con = new Conexion().getConnection()) {
                Statement st = con.createStatement();
                String sql = "select cl.domicilio, cl.rfc, cl.idcliente, cl.email, cl.nombre as nom, co.nombre as contacto from clientes_cotizacion as cl "
                        + " inner join contacto_cotizacion as co"
                        + " on cl.idCliente = co.idcliente";
//                        + " where cl.nombre like '" + cliente + "'";
                ResultSet rs = st.executeQuery(sql);
                cmbCliente.removeAllItems();
                cmbCliente.addItem("Seleccionar");
                while (rs.next()) {
                    cmbCliente.addItem(rs.getString("nom"));
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al ver cliente: " + e, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public final void agregarUsuarioResponsables() {
        try {
            Connection con = new Conexion().getConnection();
            String sql = "select nombre, apellido, numempleado from registroempleados order by nombre";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            if (auResponsables != null) {
                auResponsables.removeAllItems();
            }
            auResponsables = new TextAutoCompleter(txtResponsable);
            while (rs.next()) {
                auResponsables.addItem(rs.getString("nombre") + " " + rs.getString("apellido") + " - " + rs.getString("numempleado"));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al agregar responsables de proyecto: " + e, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public final void limpiarFormulario() {
        int opc = JOptionPane.showConfirmDialog(this, "Estas seguro de limpiar el formulario?", "Advertencia", JOptionPane.WARNING_MESSAGE);
        if (opc == JOptionPane.OK_OPTION) {
            txtProyecto.setText("");
            txtCoti.setText("");
            txtCotizacion.setText("");
            txtOrden.setText("");
            txtOC.setText("");
            txtSpec.setText("");
            txtValor.setText("");
            txtDescripcion.setText("");
            jcbMoneda.setSelectedIndex(0);
            cmbCliente.setSelectedIndex(0);
            txtFecha.setText(null);
            txtProyecto.setText("" + aumento());
            txtResponsable.setText("");
            txtResponsable.setEnabled(true);
            archivo = null;
            this.oc = null;
            cotizacion = null;
            btnX.setVisible(false);
        }
    } 

    public final void guardarDatos() {
        if (txtCotizacion.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Debes ingresar el campo de cotizacion", "ERROR", JOptionPane.ERROR_MESSAGE);
        } else if (txtResponsable.isEnabled()) {
            JOptionPane.showMessageDialog(this, "Debes presionar enter para verificar responsable", "ERROR", JOptionPane.ERROR_MESSAGE);
        } else if (txtProyecto.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Debes ingresar numero de proyecto", "ERROR", JOptionPane.ERROR_MESSAGE);
        } else if (txtOrden.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Debes ingresar la orden de compra", "ERROR", JOptionPane.ERROR_MESSAGE);
        } else if (txtDescripcion.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Debes ingresar el campo de descripcion", "ERROR", JOptionPane.ERROR_MESSAGE);
        } else if (cmbCliente.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(this, "Debes ingresar el campo de cliente", "ERROR", JOptionPane.ERROR_MESSAGE);
        } else if (txtValor.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Debes ingresar el campo de valor", "ERROR", JOptionPane.ERROR_MESSAGE);
        } else if (txtFecha.getDate() == null) {
            JOptionPane.showMessageDialog(this, "Debes ingresar la fecha de entrega", "ERROR", JOptionPane.ERROR_MESSAGE);
        } else if (jcbMoneda.getSelectedItem().equals("SELECCIONAR")) {
            JOptionPane.showMessageDialog(this, "Debes ingresar seleccionar la moneda", "ERROR", JOptionPane.ERROR_MESSAGE);
        } else if (txtCoti.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Debes seleccionar el archivo de cotizacion", "ERROR", JOptionPane.ERROR_MESSAGE);
        } else {
            Date fe = new Date();
            DateTimeFormatter sdf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String feca = sdf.format(txtFecha.getDate());
            if (revisarProyecto(txtProyecto.getText()) != null) {
                txtProyecto.setText(aumento());
            }
            try {
                Connection con = null;
                Conexion con1 = new Conexion();
                con = con1.getConnection();
                Statement st = con.createStatement();
                String sql1 = "select id from ventas";
                ResultSet rs = st.executeQuery(sql1);
                String datos[] = new String[10];
                while (rs.next()) {
                    datos[1] = rs.getString("Id");
                }

                byte[] pe = null;
                byte[] cotiz = null;
                byte[] orden = null;

                if (archivo == null) {
                    File archivo = null;
                    if (trSpe.getRuta() != null) {
                        archivo = new File(trSpe.getRuta());
                    } else {
                        archivo = new File("");
                    }
                    pe = new byte[(int) archivo.length()];

                    try {
                        InputStream input = new FileInputStream(archivo);
                        input.read(pe);
                    } catch (IOException e) {
                        System.out.println("Error: " + e);
                    }
                } else {
                    pe = new byte[(int) archivo.length()];

                    try {
                        InputStream input = new FileInputStream(archivo);
                        input.read(pe);
                    } catch (IOException e) {
                        System.out.println("Error: " + e);
                    }
                }

                File cotizacion = null;
                if (this.cotizacion == null) {
                    cotizacion = new File(trCot.getRuta());
                    cotiz = new byte[(int) cotizacion.length()];

                    try {
                        InputStream input = new FileInputStream(cotizacion);
                        input.read(cotiz);
                    } catch (IOException e) {
                        System.out.println("Error: " + e);
                    }
                } else {
                    cotiz = new byte[(int) this.cotizacion.length()];

                    try {
                        InputStream input = new FileInputStream(this.cotizacion);
                        input.read(cotiz);
                    } catch (IOException e) {
                        System.out.println("Error: " + e);
                    }
                }

                File oc = null;
                if (this.oc == null && trOC.getRuta() != null) {
                    oc = new File(trOC.getRuta());
                    orden = new byte[(int) oc.length()];

                    try {
                        InputStream input = new FileInputStream(oc);
                        input.read(orden);
                    } catch (IOException e) {
                        System.out.println("Error: " + e);
                    }
                } else {
                    if (oc != null) {
                        orden = new byte[(int) this.oc.length()];

                        try {
                            InputStream input = new FileInputStream(this.oc);
                            input.read(orden);
                        } catch (IOException e) {
                            System.out.println("Error: " + e);
                        }
                    }
                }

                String sql2 = "insert into archivosproyectos (NombreArchivo, Fecha, Proyecto, Documento, Archivo) values (?,?,?,?,?)";
                PreparedStatement pst2 = con.prepareStatement(sql2);
                Date d = new Date();
                SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");
                String fecha2 = sdf2.format(d);
                int n1 = 0;
                for (int i = 0; i < 3; i++) {
                    String nombre;
                    String doc;
                    byte[] arch;
                    switch (i) {
                        case 0:
                            arch = pe;
                            doc = "0";
                            nombre = txtSpec.getText();
                            break;
                        case 1:
                            arch = cotiz;
                            doc = "1";
                            nombre = txtCoti.getText();
                            break;
                        default:
                            arch = orden;
                            doc = "2";
                            nombre = txtOC.getText();
                            break;
                    }
                    pst2.setString(1, nombre);
                    pst2.setString(2, fecha2);
                    pst2.setString(3, txtProyecto.getText());
                    pst2.setString(4, doc);
                    pst2.setBytes(5, arch);

                    n1 = pst2.executeUpdate();
                }

                int s = Integer.parseInt(datos[1]);
                String sql = "insert into proyectos (Proyecto,FechaCreacion,Planta,Ingeniero,FechaEntrega,Prioridad,"
                        + "Descripcion,Mostrar,Liberado,NumCotizacion,Costo,OC,Moneda,Estatus, Facturado) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                PreparedStatement pst = con.prepareStatement(sql);
                int i = Integer.parseInt(datos[1]);

                SimpleDateFormat sdf3 = new SimpleDateFormat("dd/MM/yyyy");
                Date da = new Date();

                pst.setString(1, txtProyecto.getText());
                pst.setString(2, sdf3.format(da));
                pst.setString(3, cmbCliente.getSelectedItem().toString());
                pst.setString(4, txtResponsable.getText().split("-")[1].trim());
                pst.setString(5, feca);
                pst.setString(6, "");
                pst.setString(7, txtDescripcion.getText());
                pst.setString(8, "SI");
                pst.setString(9, "NO");
                pst.setString(10, txtCotizacion.getText());
                pst.setString(11, txtValor.getText());
                pst.setString(12, txtOrden.getText());
                pst.setString(13, "" + jcbMoneda.getSelectedItem());
                pst.setString(14, "DETENIDO");
                pst.setString(15, "NO");

                DateTimeFormatter sdf1 = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                String fecha1 = sdf1.format(txtFecha.getDate());

                int n = pst.executeUpdate();
                if (n1 == 0) {
                    JOptionPane.showMessageDialog(this, "ARCHIVOS NO GUARDADOS", "ADVERTENCIA", JOptionPane.ERROR_MESSAGE);
                }
                if (n > 0) {

                    javamail enviar = new javamail();
                    String asunto = "Proyecto: " + txtProyecto.getText() + " " + txtDescripcion.getText();
                    String descripcion = "<p>Proyecto No. " + txtProyecto.getText() + "</p><p>Descripcion: " + txtDescripcion.getText() + "</p>"
                            + "\n"
                            + "<p>Cotizacion: " + txtCotizacion.getText() + "</p>"
                            + "<p>Orden de compra: " + txtOrden.getText() + "</p>"
                            + "<p>Cliente: " + cmbCliente.getSelectedItem().toString() + "</p>"
                            + "<p>Fecha de entrega: " + fecha1 + "</p>"
                            + "<p>Importe: " + txtValor.getText() + "</p>"
                            + "<p>Moneda: " + jcbMoneda.getSelectedItem() + "</p>";
                    String sql3 = "select * from enviocorreos where Departamento like 'VENTAS'";
                    Statement st3 = con.createStatement();
                    ResultSet rs3 = st3.executeQuery(sql3);
                    Stack<String> copia = new Stack<>();
                    String to = "";
                    int cont = 0;
                    while (rs3.next()) {
                        String c = rs3.getString("Correo");
                        String ubi = rs3.getString("Ubi");
                        if (ubi.equals("CC")) {
                            if (cont == 0) {
                                copia.push(c);
                            } else {
                                copia.push(c);
                            }
                        } else {
                            to += "," + to;
                        }
                        cont++;
                    }
                    String mns = "";
                    try {
//                        enviar.sendVentas(copia, "juan.rayos@si3i.com"+to, asunto, descripcion, oc,cotizacion.getAbsolutePath());
                    } catch (Exception e) {
                        mns = ", El correo no fue enviado: \n" + e;
                    }
                    JOptionPane.showMessageDialog(this, "Datos guardados correctamente" + mns);
                }
                JFrame f = (JFrame) JOptionPane.getFrameForComponent(this);
                CrearOrdenTrabajo crear = new CrearOrdenTrabajo(f, true, this.numEmpleado);
                crear.txtProyecto.setText(txtProyecto.getText());
                crear.txtResponsable.setText(txtResponsable.getText());
                crear.setLocationRelativeTo(f);
                crear.setVisible(true);
                limpiarFormulario();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Error al crear proyecto: " + e);
            }
        }
    }

    public final void verificarUsuarioResponsable() {
        if (auResponsables.itemExists(txtResponsable.getText())) {
            txtResponsable.setEnabled(false);
            btnX.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "El usuario que ingresaste no existe", "Advertencia", JOptionPane.WARNING_MESSAGE);
        }
    }

    public Ventas(String numEmpleado) {
        initComponents();
        this.numEmpleado = numEmpleado;
        txtProyecto.setText("" + aumento());
        verNumero();
        addTransfer();
        agregarClientes();
        ((javax.swing.plaf.basic.BasicInternalFrameUI) this.getUI()).setNorthPane(null);
        addCliente();
        agregarUsuarioResponsables();
        btnX.setVisible(false);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel23 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        txtProyecto = new RSMaterialComponent.RSTextFieldMaterial();
        jPanel22 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        txtCotizacion = new RSMaterialComponent.RSTextFieldMaterial();
        jPanel8 = new javax.swing.JPanel();
        jLabel22 = new javax.swing.JLabel();
        txtCoti = new RSMaterialComponent.RSTextFieldMaterial();
        btnCoti = new javax.swing.JButton();
        jPanel21 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        txtOrden = new RSMaterialComponent.RSTextFieldMaterial();
        jLabel21 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        txtOC = new RSMaterialComponent.RSTextFieldMaterial();
        btnOC = new javax.swing.JButton();
        jPanel10 = new javax.swing.JPanel();
        txtSpec = new RSMaterialComponent.RSTextFieldMaterial();
        btnSubir = new javax.swing.JButton();
        jPanel20 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jLabel23 = new javax.swing.JLabel();
        cmbCliente = new javax.swing.JComboBox<>();
        jButton4 = new javax.swing.JButton();
        jLabel18 = new javax.swing.JLabel();
        jPanel13 = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        txtFecha = new com.github.lgooddatepicker.components.DatePicker();
        jPanel19 = new javax.swing.JPanel();
        jPanel14 = new javax.swing.JPanel();
        txtValor = new RSComponentShade.RSFormatFieldShade();
        jLabel24 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jPanel16 = new javax.swing.JPanel();
        jPanel15 = new javax.swing.JPanel();
        jLabel26 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jcbMoneda = new RSMaterialComponent.RSComboBoxMaterial();
        jPanel18 = new javax.swing.JPanel();
        jPanel17 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jPanel27 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        txtResponsable = new RSMaterialComponent.RSTextFieldMaterial();
        jPanel6 = new javax.swing.JPanel();
        btnX = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtDescripcion = new javax.swing.JTextArea();
        jPanel24 = new javax.swing.JPanel();
        jPanel25 = new javax.swing.JPanel();
        jPanel26 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        pan = new javax.swing.JPanel();
        panelSalir = new javax.swing.JPanel();
        lblSalir = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        jMenuItem2 = new javax.swing.JMenuItem();

        setBorder(null);
        setFrameIcon(null);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new java.awt.BorderLayout());

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new java.awt.BorderLayout());

        jPanel23.setBackground(new java.awt.Color(255, 255, 255));
        java.awt.GridBagLayout jPanel23Layout = new java.awt.GridBagLayout();
        jPanel23Layout.columnWeights = new double[] {1.0};
        jPanel23Layout.rowWeights = new double[] {1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 0.0, 1.0};
        jPanel23.setLayout(jPanel23Layout);

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setLayout(new java.awt.BorderLayout(10, 0));

        jLabel9.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(204, 0, 0));
        jLabel9.setText("*");
        jLabel9.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jPanel7.add(jLabel9, java.awt.BorderLayout.WEST);

        txtProyecto.setEditable(false);
        txtProyecto.setBackground(new java.awt.Color(255, 255, 255));
        txtProyecto.setColorMaterial(new java.awt.Color(153, 204, 255));
        txtProyecto.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        txtProyecto.setForeground(new java.awt.Color(51, 51, 51));
        txtProyecto.setPhColor(new java.awt.Color(153, 153, 153));
        txtProyecto.setPlaceholder("Numero de proyecto");
        txtProyecto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtProyectoMouseClicked(evt);
            }
        });
        jPanel7.add(txtProyecto, java.awt.BorderLayout.CENTER);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(20, 100, 20, 100);
        jPanel23.add(jPanel7, gridBagConstraints);

        jPanel22.setBackground(new java.awt.Color(255, 255, 255));
        jPanel22.setLayout(new java.awt.GridLayout(1, 0, 20, 0));

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setLayout(new java.awt.BorderLayout(10, 0));

        jLabel20.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(204, 0, 0));
        jLabel20.setText("*");
        jLabel20.setToolTipText("");
        jLabel20.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jPanel4.add(jLabel20, java.awt.BorderLayout.WEST);

        txtCotizacion.setColorMaterial(new java.awt.Color(153, 204, 255));
        txtCotizacion.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        txtCotizacion.setForeground(new java.awt.Color(51, 51, 51));
        txtCotizacion.setPhColor(new java.awt.Color(153, 153, 153));
        txtCotizacion.setPlaceholder("Numero de cotizacion");
        jPanel4.add(txtCotizacion, java.awt.BorderLayout.CENTER);

        jPanel22.add(jPanel4);

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));
        jPanel8.setLayout(new java.awt.BorderLayout(10, 10));

        jLabel22.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(204, 0, 0));
        jLabel22.setText("*");
        jLabel22.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jLabel22.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jPanel8.add(jLabel22, java.awt.BorderLayout.WEST);

        txtCoti.setEditable(false);
        txtCoti.setBackground(new java.awt.Color(255, 255, 255));
        txtCoti.setColorMaterial(new java.awt.Color(153, 204, 255));
        txtCoti.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        txtCoti.setForeground(new java.awt.Color(51, 51, 51));
        txtCoti.setPhColor(new java.awt.Color(153, 153, 153));
        txtCoti.setPlaceholder("Subir cotizacion");
        txtCoti.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCotiActionPerformed(evt);
            }
        });
        jPanel8.add(txtCoti, java.awt.BorderLayout.CENTER);

        btnCoti.setBackground(new java.awt.Color(255, 255, 255));
        btnCoti.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        btnCoti.setForeground(new java.awt.Color(51, 51, 51));
        btnCoti.setText("Subir");
        btnCoti.setBorder(null);
        btnCoti.setBorderPainted(false);
        btnCoti.setContentAreaFilled(false);
        btnCoti.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnCoti.setFocusPainted(false);
        btnCoti.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        btnCoti.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnCotiMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnCotiMouseExited(evt);
            }
        });
        btnCoti.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCotiActionPerformed(evt);
            }
        });
        jPanel8.add(btnCoti, java.awt.BorderLayout.EAST);

        jPanel22.add(jPanel8);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(20, 100, 20, 100);
        jPanel23.add(jPanel22, gridBagConstraints);

        jPanel21.setBackground(new java.awt.Color(255, 255, 255));
        jPanel21.setLayout(new java.awt.GridLayout(1, 0, 20, 0));

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setLayout(new java.awt.BorderLayout(10, 0));

        txtOrden.setForeground(new java.awt.Color(51, 51, 51));
        txtOrden.setColorMaterial(new java.awt.Color(153, 204, 255));
        txtOrden.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        txtOrden.setPhColor(new java.awt.Color(153, 153, 153));
        txtOrden.setPlaceholder("Orden de compra");
        txtOrden.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtOrdenKeyTyped(evt);
            }
        });
        jPanel5.add(txtOrden, java.awt.BorderLayout.CENTER);

        jLabel21.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(204, 0, 0));
        jLabel21.setText("*");
        jLabel21.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jPanel5.add(jLabel21, java.awt.BorderLayout.WEST);

        jPanel21.add(jPanel5);

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));
        jPanel9.setLayout(new java.awt.BorderLayout(10, 0));

        txtOC.setEditable(false);
        txtOC.setBackground(new java.awt.Color(255, 255, 255));
        txtOC.setForeground(new java.awt.Color(51, 51, 51));
        txtOC.setColorMaterial(new java.awt.Color(153, 204, 255));
        txtOC.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        txtOC.setPhColor(new java.awt.Color(153, 153, 153));
        txtOC.setPlaceholder("Subir Orden");
        jPanel9.add(txtOC, java.awt.BorderLayout.CENTER);

        btnOC.setBackground(new java.awt.Color(255, 255, 255));
        btnOC.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        btnOC.setForeground(new java.awt.Color(51, 51, 51));
        btnOC.setText("Subir");
        btnOC.setBorder(null);
        btnOC.setBorderPainted(false);
        btnOC.setContentAreaFilled(false);
        btnOC.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnOC.setFocusPainted(false);
        btnOC.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        btnOC.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnOC.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnOCMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnOCMouseExited(evt);
            }
        });
        btnOC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOCActionPerformed(evt);
            }
        });
        jPanel9.add(btnOC, java.awt.BorderLayout.EAST);

        jPanel21.add(jPanel9);

        jPanel10.setBackground(new java.awt.Color(255, 255, 255));
        jPanel10.setLayout(new java.awt.BorderLayout(10, 0));

        txtSpec.setEditable(false);
        txtSpec.setBackground(new java.awt.Color(255, 255, 255));
        txtSpec.setForeground(new java.awt.Color(51, 51, 51));
        txtSpec.setColorMaterial(new java.awt.Color(153, 204, 255));
        txtSpec.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        txtSpec.setPhColor(new java.awt.Color(153, 153, 153));
        txtSpec.setPlaceholder("Subir SPEC");
        jPanel10.add(txtSpec, java.awt.BorderLayout.CENTER);

        btnSubir.setBackground(new java.awt.Color(255, 255, 255));
        btnSubir.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        btnSubir.setForeground(new java.awt.Color(51, 51, 51));
        btnSubir.setText("Subir");
        btnSubir.setBorder(null);
        btnSubir.setBorderPainted(false);
        btnSubir.setContentAreaFilled(false);
        btnSubir.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnSubir.setFocusPainted(false);
        btnSubir.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        btnSubir.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnSubirMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnSubirMouseExited(evt);
            }
        });
        btnSubir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSubirActionPerformed(evt);
            }
        });
        jPanel10.add(btnSubir, java.awt.BorderLayout.EAST);

        jPanel21.add(jPanel10);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(20, 100, 20, 100);
        jPanel23.add(jPanel21, gridBagConstraints);

        jPanel20.setBackground(new java.awt.Color(255, 255, 255));
        jPanel20.setLayout(new java.awt.GridLayout(1, 2, 20, 0));

        jPanel11.setBackground(new java.awt.Color(255, 255, 255));
        java.awt.GridBagLayout jPanel11Layout = new java.awt.GridBagLayout();
        jPanel11Layout.columnWeights = new double[] {0.0, 2.0, 1.0};
        jPanel11Layout.rowWeights = new double[] {0.0};
        jPanel11.setLayout(jPanel11Layout);

        jLabel23.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(204, 0, 0));
        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel23.setText("*");
        jPanel11.add(jLabel23, new java.awt.GridBagConstraints());

        cmbCliente.setBackground(new java.awt.Color(255, 255, 255));
        cmbCliente.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        cmbCliente.setForeground(new java.awt.Color(51, 51, 51));
        cmbCliente.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccionar" }));
        cmbCliente.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(204, 204, 204)));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipady = 10;
        jPanel11.add(cmbCliente, gridBagConstraints);

        jButton4.setBackground(new java.awt.Color(51, 51, 51));
        jButton4.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        jButton4.setForeground(new java.awt.Color(255, 255, 255));
        jButton4.setText("+");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jPanel11.add(jButton4, gridBagConstraints);

        jLabel18.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(0, 102, 204));
        jLabel18.setText("Cliente");
        jLabel18.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(0, 8, 0, 8);
        jPanel11.add(jLabel18, gridBagConstraints);

        jPanel20.add(jPanel11);

        jPanel13.setBackground(new java.awt.Color(255, 255, 255));
        jPanel13.setLayout(new java.awt.BorderLayout(10, 10));

        jPanel12.setBackground(new java.awt.Color(255, 255, 255));
        jPanel12.setLayout(new java.awt.BorderLayout(10, 0));

        jLabel17.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(0, 102, 204));
        jLabel17.setText("Fecha de entrega");
        jLabel17.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jPanel12.add(jLabel17, java.awt.BorderLayout.CENTER);

        jLabel25.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(204, 0, 0));
        jLabel25.setText("*");
        jLabel25.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jPanel12.add(jLabel25, java.awt.BorderLayout.WEST);

        jPanel13.add(jPanel12, java.awt.BorderLayout.NORTH);
        jPanel13.add(txtFecha, java.awt.BorderLayout.CENTER);

        jPanel20.add(jPanel13);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(20, 100, 20, 100);
        jPanel23.add(jPanel20, gridBagConstraints);

        jPanel19.setBackground(new java.awt.Color(255, 255, 255));
        jPanel19.setLayout(new java.awt.GridLayout(1, 0, 20, 0));

        jPanel14.setBackground(new java.awt.Color(255, 255, 255));
        jPanel14.setLayout(new java.awt.BorderLayout(10, 0));

        txtValor.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        txtValor.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter()));
        txtValor.setPlaceholder("Valor");
        jPanel14.add(txtValor, java.awt.BorderLayout.CENTER);

        jLabel24.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(204, 0, 0));
        jLabel24.setText("*");
        jLabel24.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jPanel14.add(jLabel24, java.awt.BorderLayout.WEST);

        jLabel1.setText(" ");
        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jPanel14.add(jLabel1, java.awt.BorderLayout.PAGE_START);

        jPanel19.add(jPanel14);

        jPanel16.setBackground(new java.awt.Color(255, 255, 255));
        jPanel16.setLayout(new java.awt.BorderLayout(0, 10));

        jPanel15.setBackground(new java.awt.Color(255, 255, 255));
        jPanel15.setLayout(new java.awt.BorderLayout(10, 0));

        jLabel26.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(204, 0, 0));
        jLabel26.setText("*");
        jPanel15.add(jLabel26, java.awt.BorderLayout.WEST);

        jLabel16.setText("Moneda");
        jLabel16.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(0, 102, 204));
        jPanel15.add(jLabel16, java.awt.BorderLayout.CENTER);

        jPanel16.add(jPanel15, java.awt.BorderLayout.NORTH);

        jcbMoneda.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Seleccionar", "MXN", "USD" }));
        jPanel16.add(jcbMoneda, java.awt.BorderLayout.CENTER);

        jPanel19.add(jPanel16);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(20, 100, 20, 100);
        jPanel23.add(jPanel19, gridBagConstraints);

        jPanel18.setBackground(new java.awt.Color(255, 255, 255));
        jPanel18.setLayout(new java.awt.BorderLayout(10, 10));

        jPanel17.setBackground(new java.awt.Color(255, 255, 255));
        jPanel17.setLayout(new java.awt.BorderLayout(10, 0));

        jLabel4.setText("*");
        jLabel4.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(204, 0, 0));
        jPanel17.add(jLabel4, java.awt.BorderLayout.WEST);

        jLabel7.setText("Descripcion");
        jLabel7.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 102, 204));
        jPanel17.add(jLabel7, java.awt.BorderLayout.CENTER);

        jPanel18.add(jPanel17, java.awt.BorderLayout.NORTH);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(20, 100, 0, 100);
        jPanel23.add(jPanel18, gridBagConstraints);

        jPanel27.setBackground(new java.awt.Color(255, 255, 255));
        jPanel27.setLayout(new java.awt.BorderLayout(10, 0));

        jLabel10.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(204, 0, 0));
        jLabel10.setText("*");
        jLabel10.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jPanel27.add(jLabel10, java.awt.BorderLayout.WEST);

        txtResponsable.setColorMaterial(new java.awt.Color(153, 204, 255));
        txtResponsable.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        txtResponsable.setForeground(new java.awt.Color(51, 51, 51));
        txtResponsable.setPhColor(new java.awt.Color(153, 153, 153));
        txtResponsable.setPlaceholder("Responsable");
        txtResponsable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtResponsableMouseClicked(evt);
            }
        });
        txtResponsable.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtResponsableActionPerformed(evt);
            }
        });
        jPanel27.add(txtResponsable, java.awt.BorderLayout.CENTER);

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setLayout(new java.awt.BorderLayout());

        btnX.setBackground(new java.awt.Color(204, 0, 0));
        btnX.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        btnX.setForeground(new java.awt.Color(255, 255, 255));
        btnX.setText("x");
        btnX.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXActionPerformed(evt);
            }
        });
        jPanel6.add(btnX, java.awt.BorderLayout.SOUTH);

        jPanel27.add(jPanel6, java.awt.BorderLayout.EAST);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(20, 100, 20, 100);
        jPanel23.add(jPanel27, gridBagConstraints);

        txtDescripcion.setColumns(20);
        txtDescripcion.setLineWrap(true);
        txtDescripcion.setRows(5);
        txtDescripcion.setWrapStyleWord(true);
        txtDescripcion.setBackground(new java.awt.Color(255, 255, 255));
        txtDescripcion.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtDescripcion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDescripcionKeyTyped(evt);
            }
        });
        jScrollPane1.setViewportView(txtDescripcion);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipady = 20;
        gridBagConstraints.insets = new java.awt.Insets(10, 100, 10, 100);
        jPanel23.add(jScrollPane1, gridBagConstraints);

        jPanel2.add(jPanel23, java.awt.BorderLayout.CENTER);

        jPanel24.setBackground(new java.awt.Color(255, 255, 255));
        jPanel24.setLayout(new java.awt.BorderLayout());

        jPanel25.setBackground(new java.awt.Color(255, 255, 255));
        jPanel25.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jPanel26.setBackground(new java.awt.Color(255, 255, 255));

        jLabel12.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(0, 102, 204));
        jLabel12.setText("Ventas");
        jPanel26.add(jLabel12);

        jPanel25.add(jPanel26);

        jPanel24.add(jPanel25, java.awt.BorderLayout.CENTER);

        pan.setBackground(new java.awt.Color(255, 255, 255));

        panelSalir.setBackground(new java.awt.Color(255, 255, 255));

        lblSalir.setText(" X ");
        lblSalir.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblSalir.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        lblSalir.setForeground(new java.awt.Color(0, 0, 0));
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

        jPanel24.add(pan, java.awt.BorderLayout.EAST);

        jPanel2.add(jPanel24, java.awt.BorderLayout.NORTH);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jButton1.setBackground(new java.awt.Color(0, 102, 204));
        jButton1.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Guardar");
        jButton1.setPreferredSize(new java.awt.Dimension(200, 40));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton1);

        jButton2.setBackground(new java.awt.Color(51, 51, 51));
        jButton2.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("Limpiar formulario");
        jButton2.setPreferredSize(new java.awt.Dimension(200, 40));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton2);

        jPanel2.add(jPanel3, java.awt.BorderLayout.SOUTH);

        jPanel1.add(jPanel2, java.awt.BorderLayout.CENTER);

        jMenu1.setText("File");

        jMenuItem1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/editar (1).png"))); // NOI18N
        jMenuItem1.setText("Ver ventas");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);
        jMenu1.add(jSeparator1);

        jMenuItem2.setText("Editar envio de correos                                            ");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 802, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 674, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        JFrame f = (JFrame) JOptionPane.getFrameForComponent(this);
        dialog v = new dialog(f, true);
        v.setVisible(true);
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void btnOCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOCActionPerformed
        selec = new JFileChooser();
        oc = null;
        selec.setFileFilter(new FileNameExtensionFilter("PDF (*.pdf)", "pdf"));
        if (selec.showDialog(null, "SELECCIONAR ARCHIVO") == JFileChooser.APPROVE_OPTION) {
            oc = selec.getSelectedFile();
            txtOC.setText(oc.getName());
        }
    }//GEN-LAST:event_btnOCActionPerformed

    private void btnOCMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnOCMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_btnOCMouseExited

    private void btnOCMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnOCMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_btnOCMouseEntered

    private void btnCotiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCotiActionPerformed
        sel = new JFileChooser();
        cotizacion = null;
        sel.setFileFilter(new FileNameExtensionFilter("PDF (*.pdf)", "pdf"));
        if (sel.showDialog(null, "SELECCIONAR ARCHIVO") == JFileChooser.APPROVE_OPTION) {
            cotizacion = sel.getSelectedFile();
            txtCoti.setText(cotizacion.getName());

        }
    }//GEN-LAST:event_btnCotiActionPerformed

    private void btnCotiMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCotiMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_btnCotiMouseExited

    private void btnCotiMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCotiMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_btnCotiMouseEntered

    private void btnSubirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSubirActionPerformed
        seleccionar = new JFileChooser();
        archivo = null;
        seleccionar.setFileFilter(new FileNameExtensionFilter("PDF (*.pdf)", "pdf"));
        if (seleccionar.showDialog(null, "SELECCIONAR ARCHIVO") == JFileChooser.APPROVE_OPTION) {
            archivo = seleccionar.getSelectedFile();
            txtSpec.setText(archivo.getName());
        }
    }//GEN-LAST:event_btnSubirActionPerformed

    private void btnSubirMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSubirMouseExited
        btnSubir.setForeground(new java.awt.Color(0, 153, 255));
    }//GEN-LAST:event_btnSubirMouseExited

    private void btnSubirMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSubirMouseEntered
        btnSubir.setForeground(new java.awt.Color(0, 0, 204));
    }//GEN-LAST:event_btnSubirMouseEntered

    private void txtDescripcionKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDescripcionKeyTyped
        char c = evt.getKeyChar();
        if (Character.isLowerCase(c)) {
            String cad = ("" + c).toUpperCase();
            c = cad.charAt(0);
            evt.setKeyChar(c);
        }
    }//GEN-LAST:event_txtDescripcionKeyTyped

    private void txtOrdenKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtOrdenKeyTyped
        char c = evt.getKeyChar();
        if (Character.isLowerCase(c)) {
            String cad = ("" + c).toUpperCase();
            c = cad.charAt(0);
            evt.setKeyChar(c);
        }
    }//GEN-LAST:event_txtOrdenKeyTyped

    private void txtProyectoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtProyectoMouseClicked

    }//GEN-LAST:event_txtProyectoMouseClicked

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        JFrame f = (JFrame) JOptionPane.getFrameForComponent(this);
        correos c = new correos(f, true, "VENTAS");
        c.setLocationRelativeTo(null);
        c.setVisible(true);
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void txtCotiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCotiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCotiActionPerformed

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

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        Window ventana = SwingUtilities.getWindowAncestor(this);
        JFrame f = (JFrame) ventana;
        AgregarCliente add = new AgregarCliente(f, true);
        add.setLocationRelativeTo(f);
        add.verCliente(cmbCliente.getSelectedItem().toString());
        add.verContactos();
        add.setVisible(true);
        addCliente();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        guardarDatos();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        limpiarFormulario();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void txtResponsableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtResponsableMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_txtResponsableMouseClicked

    private void txtResponsableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtResponsableActionPerformed
        verificarUsuarioResponsable();
    }//GEN-LAST:event_txtResponsableActionPerformed

    private void btnXActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXActionPerformed
        txtResponsable.setText("");
        txtResponsable.setEnabled(true);
        btnX.setVisible(false);
    }//GEN-LAST:event_btnXActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCoti;
    private javax.swing.JButton btnOC;
    private javax.swing.JButton btnSubir;
    private javax.swing.JButton btnX;
    public javax.swing.JComboBox<String> cmbCliente;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
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
    private javax.swing.JPanel jPanel26;
    private javax.swing.JPanel jPanel27;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    public RSMaterialComponent.RSComboBoxMaterial jcbMoneda;
    private javax.swing.JLabel lblSalir;
    private javax.swing.JPanel pan;
    private javax.swing.JPanel panelSalir;
    public RSMaterialComponent.RSTextFieldMaterial txtCoti;
    public RSMaterialComponent.RSTextFieldMaterial txtCotizacion;
    private javax.swing.JTextArea txtDescripcion;
    private com.github.lgooddatepicker.components.DatePicker txtFecha;
    private RSMaterialComponent.RSTextFieldMaterial txtOC;
    private RSMaterialComponent.RSTextFieldMaterial txtOrden;
    private RSMaterialComponent.RSTextFieldMaterial txtProyecto;
    private RSMaterialComponent.RSTextFieldMaterial txtResponsable;
    private RSMaterialComponent.RSTextFieldMaterial txtSpec;
    public RSComponentShade.RSFormatFieldShade txtValor;
    // End of variables declaration//GEN-END:variables

}
