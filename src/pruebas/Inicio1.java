package pruebas;

import Conexiones.Conexion;
import Conexiones.ConexionChat;
import VentanaEmergente.CalidadNew.inicioCalidad;
import VentanaEmergente.Costos.Costeo;
import VentanaEmergente.Cotizacion.AgregarCotizacion;
import VentanaEmergente.Diseño.InicioDiseño;
import VentanaEmergente.Inicio1.Backups;
import VentanaEmergente.Inicio1.Configuracion;
import VentanaEmergente.Inicio1.InicioAlmacen;
import VentanaEmergente.Inicio1.InicioCostos;
import VentanaEmergente.Inicio1.Mensajes;
import VentanaEmergente.Inicio1.ModificacionesTowi;
import VentanaEmergente.Inicio1.Notificaciones;
import VentanaEmergente.Inicio1.ReporteError;
import VentanaEmergente.Inicio1.precioDolar;
import VentanaEmergente.Inicio1.verErrores;
import com.app.sockets.chat.Servidor;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.beans.PropertyVetoException;
import java.io.IOException;
import static java.lang.Thread.sleep;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import java.util.prefs.Preferences;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.SwingUtilities;
import com.formdev.flatlaf.themes.FlatMacLightLaf;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.function.Supplier;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.SwingConstants;
import scrollPane.PanelRound;

public final class Inicio1 extends javax.swing.JFrame implements Observer, ActionListener {

    String num;
    String depa;
    Pedidos c;
    boolean pedido = true;
    InicioAlmacen inicioAlmacen;
    InicioCostos inicioCostos;
    int notiCostos;
    int notiRequis;
    boolean requis;
    Evaluacion evaluacion;
    public int HOME = 1;
    public int COSTOS = 2;
    public Backups backup;
    private boolean activarNombres = true;
    private PanelRound menuSeleccionado = null;

    private final Color COLOR_NORMAL = Color.WHITE;
    private final Color COLOR_HOVER = new Color(234, 244, 255);
    private final Color COLOR_SELECCIONADO = new Color(123, 192, 255);

    public void activar() {
        Thread hilo = new Thread() {
            public void run() {
                for (;;) {
                    if (pedido) {
                        try {
                            sleep(500);
                            if (panelPedidos.getBackground().equals(Color.white)) {
                                panelPedidos.setBackground(new Color(255, 133, 133));
                            } else {
                                panelPedidos.setBackground(Color.white);
                            }

                        } catch (Exception e) {
                            JOptionPane.showMessageDialog(null, "ERROR EN CRONOMETRO");
                        }
                    } else {
                        break;
                    }
                }
            }
        };
        hilo.start();
    }

    public void visto() {
        try {
            Connection con = null;
            ConexionChat con1 = new ConexionChat();
            con = con1.getConnection();
            Statement st = con.createStatement();
            String not = "noti" + num;
            String sql = "select * from " + not + " where Visto2 is null";
            ResultSet rs = st.executeQuery(sql);
            String id;
            while (rs.next()) {
                id = rs.getString("Id");
                String sql2 = "update " + not + " set Visto2 = ? where Id = ?";
                PreparedStatement pst2 = con.prepareStatement(sql2);

                pst2.setString(1, "SI");
                pst2.setString(2, id);
                pst2.executeUpdate();
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "ERROR: " + e, "ERROR", JOptionPane.ERROR_MESSAGE);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Inicio1.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void actualizar() {
        try {
            Connection con;
            ConexionChat con1 = new ConexionChat();
            con = con1.getConnection();
            Statement st = con.createStatement();
            String sql = "select * from noti" + num + " where Departamento like '4'";
            ResultSet rs = st.executeQuery(sql);
            String depa = "";
            String visto = "";
            int cont = 0;
            while (rs.next()) {
                visto = rs.getString("Visto2");
                depa = rs.getString("Departamento");
                if (visto == null) {
                    cont++;
                }
            }
            if (cont > 0) {

                pedido = true;
                if (this.c != null) {
                    if (this.c.isVisible()) {
                        System.out.println("entra en visible");
                        pedido = false;
                        c.limpiarPanelPedidos();
                        int opc;
                        if (c.nuevos.isSelected()) {
                            opc = 0;
                        } else {
                            opc = 1;
                        }
                        c.addBotonesPedido(opc);
                    } else {
                        System.out.println("entra a no visible");
                        pedido = true;
                    }
                } else {
                    System.out.println("entra a null");
                    pedido = true;
                }

            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "ERROR: " + e, "ERROR", JOptionPane.ERROR_MESSAGE);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Inicio1.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void verNotificaciones() {
        try {
            Connection con = null;
            ConexionChat con1 = new ConexionChat();
            con = con1.getConnection();
            Statement st = con.createStatement();
            String numero = "noti" + num;
            String sql = "select * from " + numero;
            ResultSet rs = st.executeQuery(sql);
            String visto = "";
            String depa = "";
            int cont = 0;
            while (rs.next()) {
                visto = rs.getString("Visto2");
                depa = rs.getString("Departamento");
                if (visto == null) {
                    cont++;
                }
            }

            if (cont == 0) {
                lblCont.setVisible(false);
            } else {
                lblCont.setVisible(true);
                lblCont.setText(String.valueOf(cont));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "ERROR: " + e, "ERROR", JOptionPane.ERROR_MESSAGE);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Inicio1.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void iniciarServidor() {
        int port = 0;
        try {
            Connection con = null;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            String sql = "select * from registroempleados where NumEmpleado like '" + num + "'";
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                port = Integer.parseInt(rs.getString("Puerto"));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "ERROR: " + e, "ERROR", JOptionPane.ERROR_MESSAGE);

        }
        if (port == 0) {
            JOptionPane.showMessageDialog(this, "NO SE PUEDE PUDO INICIAR EL SERVIDOR", "ERROR", JOptionPane.ERROR_MESSAGE);
        } else {
            Servidor servidor = new Servidor(port + 1);
            servidor.addObserver(this);
            Thread hilo = new Thread(servidor);
            hilo.start();
        }
    }

    private static void cargarUbicacionVentana(Frame ventana) {
        Preferences prefs = Preferences.userNodeForPackage(Inicio1.class);
        int x = prefs.getInt("posX", 100);
        int y = prefs.getInt("posY", 100);
        ventana.setLocation(x, y);
    }

    private static void guardarUbicacionVentana(Frame ventana) {
        Preferences prefs = Preferences.userNodeForPackage(Inicio1.class);
        Point ubicacion = ventana.getLocation();
        prefs.putInt("posX", ubicacion.x);
        prefs.putInt("posY", ubicacion.y);
    }

    public void getPrecioDolar() {
        Thread hilo = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Connection con;
                    Conexion con1 = new Conexion();
                    con = con1.getConnection();
                    String sql = "insert into preciodolar (Precio, Fecha) values (?,?)";
                    PreparedStatement pst = con.prepareStatement(sql);

                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    Date d = new Date();
                    String fecha = sdf.format(d);

                    String sql2 = "select * from preciodolar where Fecha like '" + fecha + "'";
                    Statement st = con.createStatement();
                    ResultSet rs = st.executeQuery(sql2);

                    String fec = null;
                    while (rs.next()) {
                        fec = rs.getString("Precio");
                    }

                    if (fec == null) {
                        precioDolar precio = new precioDolar();
                        if (precio.getPrecio() != 0) {
                            pst.setString(1, String.valueOf(precio.getPrecio()));
                            pst.setString(2, fecha);

                            pst.executeUpdate();
                        }
                    }

                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null, "Error al extraer precio del dolar: " + e, "ERROR", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        hilo.start();
    }

    public final int getCostos(String numEmpleado, int seleccion) {
        try {
            Connection con;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            String sql = "select * from requisicion where Progreso like 'EVALUACION'";
            ResultSet rs = st.executeQuery(sql);
            int cont = 0;
            while (rs.next()) {
                cont++;
            }
            if (seleccion == COSTOS) {
                cont--;
            }
            return cont;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error: " + e, "Error", JOptionPane.ERROR_MESSAGE);
        }
        return 0;
    }

    public String getEmpleadoCostos(String numEmpleado) {
        try {
            Connection con;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st2 = con.createStatement();
            String sql2 = "select * from registroempleados where NumEmpleado like '" + numEmpleado + "'";
            ResultSet rs2 = st2.executeQuery(sql2);
            String costos = null;
            while (rs2.next()) {
                costos = rs2.getString("Costos");
                requis = rs2.getBoolean("VerRequisiciones");
            }
            return costos;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error: " + e, "error", JOptionPane.ERROR_MESSAGE);
        }
        return null;
    }

    public void checkCostos() {
        boolean band = true;
        Thread hilo = new Thread(new Runnable() {
            public void run() {
                while (band) {
                    try {
                        notiCostos = getCostos(lblId.getText(), HOME);
                        if (notiCostos == 0) {
                            lblNotiCostos.setVisible(false);
                        } else {
                            lblNotiCostos.setVisible(true);
                            lblNotiCostos.setText(String.valueOf(notiCostos));
                        }
                        sleep(90000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Inicio1.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
        hilo.start();
    }

    public int extraerRequisiciones() {
        try {
            Connection con;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            LocalDate fechaActual = LocalDate.now();
            LocalDate nuevaFecha = fechaActual.minusDays(3);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String nuevaFechaFormateada = nuevaFecha.format(formatter);
            String sql = "select Progreso, Id, NumeroEmpleado, NumeroCotizacion, Estatus, Estado, Progreso, Costo, Fecha from requisicion where "
                    + "(Progreso like 'COMPRADO' or Progreso like 'COTIZANDO' "
                    + "or Progreso like 'COTIZADO' or Progreso like 'APROBADO' or Progreso like 'NUEVO' or Progreso like 'LLEGO, INCOMPETO'"
                    + "or Progreso like 'EVALUACION') and FechaNew < '" + nuevaFechaFormateada + "' and NumeroEmpleado like '" + lblId.getText() + "'";
            ResultSet rs = st.executeQuery(sql);
            int cont = 0;
            while (rs.next()) {
                cont++;
            }
            return cont;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error: " + e, "Error", JOptionPane.ERROR_MESSAGE);
        }
        return 0;
    }

    public void checkRequi() {
        boolean band = true;
        Thread hilo = new Thread(new Runnable() {
            public void run() {
                while (band) {
                    try {
                        notiRequis = extraerRequisiciones();
                        if (notiRequis == 0) {
                            lblNotiRequis.setVisible(false);
                        } else {
                            lblNotiRequis.setVisible(true);
                            lblNotiRequis.setText(String.valueOf(notiRequis));
                        }
                        sleep(900000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Inicio1.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
        hilo.start();
    }

    public void inicioCostos(JInternalFrame c) {
        JInternalFrame cla = c;
        jDesktopPane1.add(cla);
        cla.toFront();
        cla.setLocation(jDesktopPane1.getWidth() / 2 - cla.getWidth() / 2, jDesktopPane1.getHeight() / 2 - cla.getHeight() / 2);
        try {
            cla.setMaximum(true);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(Inicio1.class.getName()).log(Level.SEVERE, null, ex);
        }
        cla.setVisible(true);
    }

    public final void iniciarBackup() {
        try {
            backup = new Backups();
            backup.iniciar();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error " + e);
        }
    }

    public final void agregarMenuProduccion(String depa, int y, String ruta, Supplier<JInternalFrame> frameSupplier) {
        GridBagConstraints gbc = new GridBagConstraints();

        PanelRound pnlBoton = new PanelRound();
        pnlBoton.setBackground(Color.WHITE);
        pnlBoton.setRoundBottomLeft(10);
        pnlBoton.setRoundBottomRight(10);
        pnlBoton.setRoundTopLeft(10);
        pnlBoton.setRoundTopRight(10);
        pnlBoton.setLayout(new BorderLayout());

        JButton btnDepartamento = new JButton();

        btnDepartamento.setBackground(Color.WHITE);
        btnDepartamento.setFont(new Font("Roboto", Font.PLAIN, 12));
        btnDepartamento.setForeground(new Color(50, 50, 50));

        btnDepartamento.setText(activarNombres ? depa : "      ");

        btnDepartamento.setHorizontalAlignment(SwingConstants.LEFT);
        btnDepartamento.setBorder(null);
        btnDepartamento.setIcon(new ImageIcon(getClass().getResource(ruta)));
        btnDepartamento.setBorderPainted(false);
        btnDepartamento.setContentAreaFilled(false);
        btnDepartamento.setFocusPainted(false);

        btnDepartamento.addActionListener(e -> {
            if (menuSeleccionado != null) {
                menuSeleccionado.setBackground(COLOR_NORMAL);
            }
            menuSeleccionado = pnlBoton;
            menuSeleccionado.setBackground(COLOR_SELECCIONADO);
            verFrame(frameSupplier.get());
        });

        btnDepartamento.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent evt) {
                pnlBoton.setBackground(COLOR_HOVER);
            }

            @Override
            public void mouseExited(MouseEvent evt) {
                if (menuSeleccionado != pnlBoton) {
                    pnlBoton.setBackground(COLOR_NORMAL);
                }
            }
        });

        pnlBoton.add(btnDepartamento, BorderLayout.CENTER);

        gbc.gridy = y;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.ipadx = activarNombres ? 10 : 0;
        gbc.ipady = 15;
        gbc.insets = new Insets(3, 10, 3, 0);

        jPanel2.add(pnlBoton, gbc);
    }

    public final void limpiarPanel() {
        jPanel2.removeAll();
        jPanel2.revalidate();
        jPanel2.repaint();

        jScrollPane1.revalidate();
        jScrollPane1.repaint();

        jScrollPane1.getViewport().revalidate();
        jScrollPane1.getViewport().repaint();
    }

    public final void agregarLabel(String depa, int i) {
        javax.swing.JLabel lbl = new javax.swing.JLabel();
        lbl.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        lbl.setForeground(new java.awt.Color(204, 204, 204));
        lbl.setText(depa);
        java.awt.GridBagConstraints gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = i;
        gridBagConstraints.insets = new Insets(3, 5, 3, 0);
        jPanel2.add(lbl, gridBagConstraints);
    }

    public final void agregarMenu() {
        limpiarPanel();
        agregarLabel("Produccion", 0);
        agregarMenuProduccion("Dashboard", 1, "/Img/dashboard_16.png", () -> new Live(lblId.getText()));
        agregarMenuProduccion("Diseno", 2, "/Img/diseno_16.png", () -> new InicioDiseño(this));
        agregarMenuProduccion("Proyectos", 3, "/Img/estados_16.png", () -> new CambiarEstado(lblId.getText()));
        agregarMenuProduccion("Carga de trabajo", 4, "/Img/carga_16.png", () -> new CargaTrabajo(this));
        agregarMenuProduccion("Reportes", 5, "/Img/reportes_16.png", () -> new Reportes(lblId.getText()));
        agregarMenuProduccion("Cortes", 6, "/Img/cortes_16.png", () -> new Cortes(lblId.getText()));
        agregarMenuProduccion("Maquinados", 7, "/Img/maquinado_16.png", () -> new MaquinadosNew(lblId.getText()));
        agregarMenuProduccion("Ensamble", 8, "/Img/ensamble_16.png", () -> new Ensamble());
        agregarMenuProduccion("Calidad", 9, "/Img/calidad_16.png", () -> new CalidadDebug(lblNombre.getText(), lblId.getText()));
        agregarLabel("Compras", 10);
        agregarMenuProduccion("Requisiciones", 11, "/Img/requisiciones_16.png", () -> new requisicionDeCompra(lblId.getText(), lblNombre.getText()));
        agregarMenuProduccion("Compras", 12, "/Img/compras_16.png", () -> new OrdenDeCompra(lblId.getText()));
        agregarMenuProduccion("Estado requisiciones", 13, "/Img/ver_requi_16.png", () -> new VerRequisiciones(lblId.getText()));
        agregarMenuProduccion("Aprobacion", 14, "/Img/aprobacion_16.png", () -> new Aprobacion(lblId.getText()));
        agregarMenuProduccion("Evaluacion", 15, "/Img/evaluacion_16.png", () -> new Evaluacion(lblId.getText()));
        agregarLabel("Administracion", 16);
        agregarMenuProduccion("Cotizaciones", 17, "/Img/cotizacion_16.png", () -> new InicioCotizacion(lblId.getText(), this));
        agregarMenuProduccion("Ventas", 18, "/Img/ventas_16.png", () -> new Ventas());
        agregarMenuProduccion("Remisiones", 19, "/Img/remisiones_16.png", () -> new Remisiones(lblId.getText()));
        agregarMenuProduccion("CXP", 20, "/Img/cxp_16.png", () -> new cxp());
        agregarMenuProduccion("Costos", 21, "/Img/costos_16.png", () -> new Costos(lblId.getText()));
        agregarMenuProduccion("RH", 22, "/Img/rh_16.png", () -> new RH(lblId.getText()));
        agregarMenuProduccion("Agregar empleado", 23, "/Img/agregar_empleado_16.png", () -> new AgregarEmpleado());
        agregarMenuProduccion("Project manager", 24, "/Img/proyect_16.png", () -> new ProyectManager(lblId.getText()));
        agregarMenuProduccion("Checador", 25, "/Img/huella_16.png", () -> new Checador(lblId.getText()));
        agregarLabel("Almacen", 26);
//      Pendiente  
//      agregarMenuProduccion("Almacen", 26, "/Img/.png", () -> new InicioAlmacen(this, true));
        agregarMenuProduccion("Inventario", 28, "/Img/inventario_16.png", () -> new InventarioNew(lblId.getText()));
        agregarMenuProduccion("Recibos", 29, "/Img/recibos_16.png", () -> new Recibos(lblId.getText()));
        agregarMenuProduccion("Prestamos", 30, "/Img/prestamos_16.png", () -> new Prestamo(lblId.getText(), this));
        agregarMenuProduccion("Pedidos", 31, "/Img/pedidos_16.png", () -> new Pedidos(lblId.getText()));
        agregarMenuProduccion("Entregas", 32, "/Img/entregas_16.png", () -> new EntregaRequisicion(lblId.getText()));
        agregarMenuProduccion("Liberacion", 33, "/Img/liberacion_16.png", () -> new Almacen(lblId.getText()));
    }

    public final void verFrame(javax.swing.JInternalFrame c) {
        JInternalFrame frame = jDesktopPane1.getSelectedFrame();

        if (frame != null) {
            frame.dispose();
        }
        jDesktopPane1.add(c);
        c.toFront();
        c.setLocation(jDesktopPane1.getWidth() / 2 - c.getWidth() / 2, jDesktopPane1.getHeight() / 2 - c.getHeight() / 2);
        try {
            c.setMaximum(true);
        } catch (PropertyVetoException e) {
            Logger.getLogger(Inicio1.class.getName()).log(Level.SEVERE, null, e);
        }
        c.setVisible(true);
    }

    public Inicio1(String numero, String nombre, String depa) {
        try {
            UIManager.setLookAndFeel(new FlatMacLightLaf());
        } catch (Exception e) {
            e.printStackTrace();
        }
        initComponents();
        lblId.setText(numero);
        this.depa = depa;
        num = numero;
        lblNombre.setText(nombre);
        setLocationRelativeTo(null);
        setTitle("Servicios Industriales 3i");
        this.setIconImage(new ImageIcon(getClass().getResource("/Img/Icono Towi.png")).getImage());
        btnCalidad.setComponentPopupMenu(jPopupMenu1);
        pedido = false;
        cargarUbicacionVentana(this);
        this.setExtendedState(Inicio1.MAXIMIZED_BOTH);
        getPrecioDolar();
        if (getEmpleadoCostos(numero) != null) {
            checkCostos();
        } else {
            lblNotiCostos.setVisible(false);
        }
        if (requis) {
            checkRequi();
        } else {
            lblNotiRequis.setVisible(false);
        }
        iniciarBackup();
        agregarMenu();
        jPanel3.setVisible(false);
        jScrollPane1.getVerticalScrollBar().setUnitIncrement(15);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPopupMenu1 = new javax.swing.JPopupMenu();
        miReporte = new javax.swing.JMenuItem();
        jPanel1 = new javax.swing.JPanel();
        lblNombre = new javax.swing.JLabel();
        lblId1 = new javax.swing.JLabel();
        lblId2 = new javax.swing.JLabel();
        lblId = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jPanel37 = new javax.swing.JPanel();
        jButton3 = new javax.swing.JButton();
        lblCont = new javax.swing.JLabel();
        jButton5 = new javax.swing.JButton();
        btnModificar1 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jDesktopPane1 = new javax.swing.JDesktopPane();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        panel11 = new javax.swing.JPanel();
        rSPanelRound1 = new rojeru_san.rspanel.RSPanelRound();
        btnDisenio = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        panel12 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        rSPanelRound3 = new rojeru_san.rspanel.RSPanelRound();
        btnEstado = new javax.swing.JButton();
        panel13 = new javax.swing.JPanel();
        rSPanelRound4 = new rojeru_san.rspanel.RSPanelRound();
        btnEstado2 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        panel14 = new javax.swing.JPanel();
        rSPanelRound5 = new rojeru_san.rspanel.RSPanelRound();
        btnEstado1 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        panel48 = new javax.swing.JPanel();
        rSPanelRound30 = new rojeru_san.rspanel.RSPanelRound();
        btnCotizacionVentas = new javax.swing.JButton();
        jLabel29 = new javax.swing.JLabel();
        panel15 = new javax.swing.JPanel();
        rSPanelRound6 = new rojeru_san.rspanel.RSPanelRound();
        btnEstado3 = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        panel16 = new javax.swing.JPanel();
        rSPanelRound7 = new rojeru_san.rspanel.RSPanelRound();
        btnEstado5 = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        panel17 = new javax.swing.JPanel();
        rSPanelRound8 = new rojeru_san.rspanel.RSPanelRound();
        btnChecador = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jPanel40 = new javax.swing.JPanel();
        rSPanelRound28 = new rojeru_san.rspanel.RSPanelRound();
        btnHtpp = new javax.swing.JButton();
        jLabel30 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jPanel22 = new javax.swing.JPanel();
        rSPanelRound37 = new rojeru_san.rspanel.RSPanelRound();
        btnIntegracion1 = new javax.swing.JButton();
        jLabel36 = new javax.swing.JLabel();
        panel21 = new javax.swing.JPanel();
        rSPanelRound9 = new rojeru_san.rspanel.RSPanelRound();
        btnCorte = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        jPanel20 = new javax.swing.JPanel();
        rSPanelRound32 = new rojeru_san.rspanel.RSPanelRound();
        btnElec = new javax.swing.JButton();
        jLabel31 = new javax.swing.JLabel();
        jPanel23 = new javax.swing.JPanel();
        panel28 = new javax.swing.JPanel();
        rSPanelRound38 = new rojeru_san.rspanel.RSPanelRound();
        btnCalidad1 = new javax.swing.JButton();
        jLabel37 = new javax.swing.JLabel();
        panel26 = new javax.swing.JPanel();
        rSPanelRound14 = new rojeru_san.rspanel.RSPanelRound();
        btnCalidad = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();
        panel27 = new javax.swing.JPanel();
        rSPanelRound15 = new rojeru_san.rspanel.RSPanelRound();
        btnTrata = new javax.swing.JButton();
        jLabel14 = new javax.swing.JLabel();
        jPanel21 = new javax.swing.JPanel();
        rSPanelRound36 = new rojeru_san.rspanel.RSPanelRound();
        btnIntegracion = new javax.swing.JButton();
        jLabel35 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        panel31 = new javax.swing.JPanel();
        rSPanelRound16 = new rojeru_san.rspanel.RSPanelRound();
        btnRegistro = new javax.swing.JButton();
        jLabel15 = new javax.swing.JLabel();
        panel38 = new javax.swing.JPanel();
        rSPanelRound39 = new rojeru_san.rspanel.RSPanelRound();
        btnRH = new javax.swing.JButton();
        jLabel38 = new javax.swing.JLabel();
        panel32 = new javax.swing.JPanel();
        rSPanelRound17 = new rojeru_san.rspanel.RSPanelRound();
        btnEmpleado = new javax.swing.JButton();
        jLabel16 = new javax.swing.JLabel();
        panel33 = new javax.swing.JPanel();
        rSPanelRound18 = new rojeru_san.rspanel.RSPanelRound();
        btnInventario = new javax.swing.JButton();
        jLabel17 = new javax.swing.JLabel();
        panel34 = new javax.swing.JPanel();
        rSPanelRound19 = new rojeru_san.rspanel.RSPanelRound();
        btnInventario1 = new javax.swing.JButton();
        jLabel18 = new javax.swing.JLabel();
        panel35 = new javax.swing.JPanel();
        rSPanelRound20 = new rojeru_san.rspanel.RSPanelRound();
        btnInventario2 = new javax.swing.JButton();
        jLabel19 = new javax.swing.JLabel();
        panel36 = new javax.swing.JPanel();
        rSPanelRound21 = new rojeru_san.rspanel.RSPanelRound();
        btnRemisiones = new javax.swing.JButton();
        jLabel20 = new javax.swing.JLabel();
        panel37 = new javax.swing.JPanel();
        rSPanelRound22 = new rojeru_san.rspanel.RSPanelRound();
        btnEntrega = new javax.swing.JButton();
        jLabel21 = new javax.swing.JLabel();
        jPanel39 = new javax.swing.JPanel();
        panelPedidos = new scrollPane.PanelRound();
        rSPanelRound33 = new rojeru_san.rspanel.RSPanelRound();
        btnPedidos = new javax.swing.JButton();
        jLabel32 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        panel41 = new javax.swing.JPanel();
        rSPanelRound23 = new rojeru_san.rspanel.RSPanelRound();
        btnRequisicion = new javax.swing.JButton();
        jLabel22 = new javax.swing.JLabel();
        panel42 = new javax.swing.JPanel();
        rSPanelRound24 = new rojeru_san.rspanel.RSPanelRound();
        btnOrden = new javax.swing.JButton();
        jLabel23 = new javax.swing.JLabel();
        panel43 = new javax.swing.JPanel();
        rSPanelRound25 = new rojeru_san.rspanel.RSPanelRound();
        btnOrden1 = new javax.swing.JButton();
        jLabel24 = new javax.swing.JLabel();
        panel44 = new javax.swing.JPanel();
        rSPanelRound26 = new rojeru_san.rspanel.RSPanelRound();
        btnRecibos = new javax.swing.JButton();
        jLabel25 = new javax.swing.JLabel();
        panel45 = new javax.swing.JPanel();
        rSPanelRound27 = new rojeru_san.rspanel.RSPanelRound();
        btnPrestamos = new javax.swing.JButton();
        jLabel26 = new javax.swing.JLabel();
        panel46 = new javax.swing.JPanel();
        rSPanelRound31 = new rojeru_san.rspanel.RSPanelRound();
        btnCotizacion = new javax.swing.JButton();
        jLabel27 = new javax.swing.JLabel();
        panel47 = new javax.swing.JPanel();
        rSPanelRound29 = new rojeru_san.rspanel.RSPanelRound();
        btnVer = new javax.swing.JButton();
        jLabel28 = new javax.swing.JLabel();
        lblNotiRequis = new javax.swing.JLabel();
        jPanel41 = new javax.swing.JPanel();
        rSPanelRound34 = new rojeru_san.rspanel.RSPanelRound();
        btnCostos = new javax.swing.JButton();
        jLabel33 = new javax.swing.JLabel();
        lblNotiCostos = new javax.swing.JLabel();
        panel49 = new javax.swing.JPanel();
        rSPanelRound35 = new rojeru_san.rspanel.RSPanelRound();
        btnCalendario = new javax.swing.JButton();
        jLabel34 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel2 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();

        miReporte.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/excel_1.png"))); // NOI18N
        miReporte.setText("REPORTES DE CALIDAD");
        miReporte.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miReporteActionPerformed(evt);
            }
        });
        jPopupMenu1.add(miReporte);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                formKeyPressed(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        lblNombre.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N

        lblId1.setFont(new java.awt.Font("Roboto Black", 1, 12)); // NOI18N
        lblId1.setText("NO EMPLEADO:");
        lblId1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblId1MouseClicked(evt);
            }
        });

        lblId2.setFont(new java.awt.Font("Roboto Black", 1, 12)); // NOI18N
        lblId2.setText("NOMBRE:");

        lblId.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/ajustes.png"))); // NOI18N
        jButton2.setBorder(null);
        jButton2.setBorderPainted(false);
        jButton2.setContentAreaFilled(false);
        jButton2.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jButton2.setFocusPainted(false);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/correo-electronico.png"))); // NOI18N
        jButton4.setBorder(null);
        jButton4.setBorderPainted(false);
        jButton4.setContentAreaFilled(false);
        jButton4.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jButton4.setFocusPainted(false);
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jPanel37.setBackground(new java.awt.Color(255, 255, 255));

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/noti_16.png"))); // NOI18N
        jButton3.setBorder(null);
        jButton3.setBorderPainted(false);
        jButton3.setContentAreaFilled(false);
        jButton3.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jButton3.setFocusPainted(false);
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        lblCont.setFont(new java.awt.Font("Roboto", 1, 10)); // NOI18N
        lblCont.setForeground(new java.awt.Color(204, 0, 0));
        lblCont.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblCont.setText("1");
        lblCont.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        javax.swing.GroupLayout jPanel37Layout = new javax.swing.GroupLayout(jPanel37);
        jPanel37.setLayout(jPanel37Layout);
        jPanel37Layout.setHorizontalGroup(
            jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel37Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(8, 8, 8))
            .addGroup(jPanel37Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(lblCont, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel37Layout.setVerticalGroup(
            jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(lblCont, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/cerrar sesion.png"))); // NOI18N
        jButton5.setBorder(null);
        jButton5.setBorderPainted(false);
        jButton5.setContentAreaFilled(false);
        jButton5.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jButton5.setFocusPainted(false);
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        btnModificar1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/subir_16.png"))); // NOI18N
        btnModificar1.setBorder(null);
        btnModificar1.setBorderPainted(false);
        btnModificar1.setContentAreaFilled(false);
        btnModificar1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnModificar1.setFocusPainted(false);
        btnModificar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificar1ActionPerformed(evt);
            }
        });

        jButton1.setText("jButton1");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel37, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblId1, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblId, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lblId2, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 323, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(212, 212, 212)
                .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnModificar1)
                .addContainerGap(235, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jButton4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblId, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblId2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblNombre, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblId1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton1))
                    .addComponent(jPanel37, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnModificar1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel1, java.awt.BorderLayout.NORTH);

        jDesktopPane1.setBackground(new java.awt.Color(255, 255, 255));
        jDesktopPane1.setLayout(new java.awt.BorderLayout());

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setLayout(new java.awt.GridLayout(4, 1));

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setLayout(new java.awt.GridLayout(1, 8));

        panel11.setBackground(new java.awt.Color(255, 255, 255));
        panel11.setLayout(new java.awt.GridBagLayout());

        rSPanelRound1.setBackground(new java.awt.Color(235, 235, 235));
        rSPanelRound1.setColorBackground(new java.awt.Color(245, 245, 245));
        rSPanelRound1.setColorBorde(new java.awt.Color(245, 245, 245));
        rSPanelRound1.setPreferredSize(new java.awt.Dimension(90, 90));
        rSPanelRound1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 10, 10));

        btnDisenio.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        btnDisenio.setForeground(new java.awt.Color(0, 153, 255));
        btnDisenio.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/diseño_64.png"))); // NOI18N
        btnDisenio.setBorder(null);
        btnDisenio.setContentAreaFilled(false);
        btnDisenio.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnDisenio.setFocusPainted(false);
        btnDisenio.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnDisenio.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        btnDisenio.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnDisenio.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnDisenioMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnDisenioMouseExited(evt);
            }
        });
        btnDisenio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDisenioActionPerformed(evt);
            }
        });
        rSPanelRound1.add(btnDisenio);

        panel11.add(rSPanelRound1, new java.awt.GridBagConstraints());

        jLabel1.setFont(new java.awt.Font("Roboto Medium", 0, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 51, 51));
        jLabel1.setText("Diseño");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        panel11.add(jLabel1, gridBagConstraints);

        jPanel4.add(panel11);

        panel12.setBackground(new java.awt.Color(255, 255, 255));
        panel12.setLayout(new java.awt.GridBagLayout());

        jLabel2.setFont(new java.awt.Font("Roboto Medium", 0, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(51, 51, 51));
        jLabel2.setText("Estados");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        panel12.add(jLabel2, gridBagConstraints);

        rSPanelRound3.setBackground(new java.awt.Color(235, 235, 235));
        rSPanelRound3.setColorBackground(new java.awt.Color(245, 245, 245));
        rSPanelRound3.setColorBorde(new java.awt.Color(245, 245, 245));
        rSPanelRound3.setPreferredSize(new java.awt.Dimension(90, 90));
        rSPanelRound3.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 10, 10));

        btnEstado.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        btnEstado.setForeground(new java.awt.Color(0, 153, 255));
        btnEstado.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Estados_64.png"))); // NOI18N
        btnEstado.setBorder(null);
        btnEstado.setContentAreaFilled(false);
        btnEstado.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEstado.setFocusPainted(false);
        btnEstado.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnEstado.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        btnEstado.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnEstado.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnEstadoMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnEstadoMouseExited(evt);
            }
        });
        btnEstado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEstadoActionPerformed(evt);
            }
        });
        rSPanelRound3.add(btnEstado);

        panel12.add(rSPanelRound3, new java.awt.GridBagConstraints());

        jPanel4.add(panel12);

        panel13.setBackground(new java.awt.Color(255, 255, 255));
        panel13.setLayout(new java.awt.GridBagLayout());

        rSPanelRound4.setBackground(new java.awt.Color(235, 235, 235));
        rSPanelRound4.setColorBackground(new java.awt.Color(245, 245, 245));
        rSPanelRound4.setColorBorde(new java.awt.Color(245, 245, 245));
        rSPanelRound4.setPreferredSize(new java.awt.Dimension(90, 90));
        rSPanelRound4.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 10, 10));

        btnEstado2.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        btnEstado2.setForeground(new java.awt.Color(0, 153, 255));
        btnEstado2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/carga_64.png"))); // NOI18N
        btnEstado2.setBorder(null);
        btnEstado2.setContentAreaFilled(false);
        btnEstado2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEstado2.setFocusPainted(false);
        btnEstado2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnEstado2.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        btnEstado2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnEstado2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnEstado2MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnEstado2MouseExited(evt);
            }
        });
        btnEstado2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEstado2ActionPerformed(evt);
            }
        });
        rSPanelRound4.add(btnEstado2);

        panel13.add(rSPanelRound4, new java.awt.GridBagConstraints());

        jLabel3.setFont(new java.awt.Font("Roboto Medium", 0, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(51, 51, 51));
        jLabel3.setText("Carga de trabajo");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        panel13.add(jLabel3, gridBagConstraints);

        jPanel4.add(panel13);

        panel14.setBackground(new java.awt.Color(255, 255, 255));
        panel14.setLayout(new java.awt.GridBagLayout());

        rSPanelRound5.setBackground(new java.awt.Color(235, 235, 235));
        rSPanelRound5.setColorBackground(new java.awt.Color(245, 245, 245));
        rSPanelRound5.setColorBorde(new java.awt.Color(245, 245, 245));
        rSPanelRound5.setPreferredSize(new java.awt.Dimension(90, 90));
        rSPanelRound5.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 10, 10));

        btnEstado1.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        btnEstado1.setForeground(new java.awt.Color(0, 153, 255));
        btnEstado1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/reportes_64.png"))); // NOI18N
        btnEstado1.setBorder(null);
        btnEstado1.setContentAreaFilled(false);
        btnEstado1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEstado1.setFocusPainted(false);
        btnEstado1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnEstado1.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        btnEstado1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnEstado1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnEstado1MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnEstado1MouseExited(evt);
            }
        });
        btnEstado1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEstado1ActionPerformed(evt);
            }
        });
        rSPanelRound5.add(btnEstado1);

        panel14.add(rSPanelRound5, new java.awt.GridBagConstraints());

        jLabel4.setFont(new java.awt.Font("Roboto Medium", 0, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(51, 51, 51));
        jLabel4.setText("Reportes");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        panel14.add(jLabel4, gridBagConstraints);

        jPanel4.add(panel14);

        panel48.setBackground(new java.awt.Color(255, 255, 255));
        panel48.setLayout(new java.awt.GridBagLayout());

        rSPanelRound30.setBackground(new java.awt.Color(235, 235, 235));
        rSPanelRound30.setColorBackground(new java.awt.Color(245, 245, 245));
        rSPanelRound30.setColorBorde(new java.awt.Color(245, 245, 245));
        rSPanelRound30.setPreferredSize(new java.awt.Dimension(90, 90));
        rSPanelRound30.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 10, 10));

        btnCotizacionVentas.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        btnCotizacionVentas.setForeground(new java.awt.Color(0, 153, 255));
        btnCotizacionVentas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/cotizar_64.png"))); // NOI18N
        btnCotizacionVentas.setBorder(null);
        btnCotizacionVentas.setContentAreaFilled(false);
        btnCotizacionVentas.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCotizacionVentas.setFocusPainted(false);
        btnCotizacionVentas.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnCotizacionVentas.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        btnCotizacionVentas.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnCotizacionVentas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnCotizacionVentasMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnCotizacionVentasMouseExited(evt);
            }
        });
        btnCotizacionVentas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCotizacionVentasActionPerformed(evt);
            }
        });
        rSPanelRound30.add(btnCotizacionVentas);

        panel48.add(rSPanelRound30, new java.awt.GridBagConstraints());

        jLabel29.setFont(new java.awt.Font("Roboto Medium", 0, 14)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(51, 51, 51));
        jLabel29.setText("Cotizar");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        panel48.add(jLabel29, gridBagConstraints);

        jPanel4.add(panel48);

        panel15.setBackground(new java.awt.Color(255, 255, 255));
        panel15.setLayout(new java.awt.GridBagLayout());

        rSPanelRound6.setBackground(new java.awt.Color(235, 235, 235));
        rSPanelRound6.setColorBackground(new java.awt.Color(245, 245, 245));
        rSPanelRound6.setColorBorde(new java.awt.Color(245, 245, 245));
        rSPanelRound6.setPreferredSize(new java.awt.Dimension(90, 90));
        rSPanelRound6.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 10, 10));

        btnEstado3.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        btnEstado3.setForeground(new java.awt.Color(0, 153, 255));
        btnEstado3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/ventas_64_1.png"))); // NOI18N
        btnEstado3.setBorder(null);
        btnEstado3.setContentAreaFilled(false);
        btnEstado3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEstado3.setFocusPainted(false);
        btnEstado3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnEstado3.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        btnEstado3.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnEstado3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnEstado3MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnEstado3MouseExited(evt);
            }
        });
        btnEstado3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEstado3ActionPerformed(evt);
            }
        });
        rSPanelRound6.add(btnEstado3);

        panel15.add(rSPanelRound6, new java.awt.GridBagConstraints());

        jLabel5.setFont(new java.awt.Font("Roboto Medium", 0, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(51, 51, 51));
        jLabel5.setText("Ventas");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        panel15.add(jLabel5, gridBagConstraints);

        jPanel4.add(panel15);

        panel16.setBackground(new java.awt.Color(255, 255, 255));
        panel16.setLayout(new java.awt.GridBagLayout());

        rSPanelRound7.setBackground(new java.awt.Color(235, 235, 235));
        rSPanelRound7.setColorBackground(new java.awt.Color(245, 245, 245));
        rSPanelRound7.setColorBorde(new java.awt.Color(245, 245, 245));
        rSPanelRound7.setPreferredSize(new java.awt.Dimension(90, 90));
        rSPanelRound7.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 10, 10));

        btnEstado5.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        btnEstado5.setForeground(new java.awt.Color(0, 153, 255));
        btnEstado5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/proyect_64.png"))); // NOI18N
        btnEstado5.setBorder(null);
        btnEstado5.setContentAreaFilled(false);
        btnEstado5.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEstado5.setFocusPainted(false);
        btnEstado5.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnEstado5.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        btnEstado5.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnEstado5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnEstado5MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnEstado5MouseExited(evt);
            }
        });
        btnEstado5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEstado5ActionPerformed(evt);
            }
        });
        rSPanelRound7.add(btnEstado5);

        panel16.add(rSPanelRound7, new java.awt.GridBagConstraints());

        jLabel6.setFont(new java.awt.Font("Roboto Medium", 0, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(51, 51, 51));
        jLabel6.setText("Project manager");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        panel16.add(jLabel6, gridBagConstraints);

        jPanel4.add(panel16);

        panel17.setBackground(new java.awt.Color(255, 255, 255));
        panel17.setLayout(new java.awt.GridBagLayout());

        rSPanelRound8.setBackground(new java.awt.Color(235, 235, 235));
        rSPanelRound8.setColorBackground(new java.awt.Color(245, 245, 245));
        rSPanelRound8.setColorBorde(new java.awt.Color(245, 245, 245));
        rSPanelRound8.setPreferredSize(new java.awt.Dimension(90, 90));
        rSPanelRound8.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 10, 10));

        btnChecador.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        btnChecador.setForeground(new java.awt.Color(0, 153, 255));
        btnChecador.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/checador_64_1.png"))); // NOI18N
        btnChecador.setBorder(null);
        btnChecador.setContentAreaFilled(false);
        btnChecador.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnChecador.setFocusPainted(false);
        btnChecador.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnChecador.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        btnChecador.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnChecador.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnChecadorMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnChecadorMouseExited(evt);
            }
        });
        btnChecador.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChecadorActionPerformed(evt);
            }
        });
        rSPanelRound8.add(btnChecador);

        panel17.add(rSPanelRound8, new java.awt.GridBagConstraints());

        jLabel7.setFont(new java.awt.Font("Roboto Medium", 0, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(51, 51, 51));
        jLabel7.setText("Checador");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        panel17.add(jLabel7, gridBagConstraints);

        jPanel4.add(panel17);

        jPanel40.setBackground(new java.awt.Color(255, 255, 255));
        jPanel40.setLayout(new java.awt.GridBagLayout());

        rSPanelRound28.setBackground(new java.awt.Color(235, 235, 235));
        rSPanelRound28.setColorBackground(new java.awt.Color(245, 245, 245));
        rSPanelRound28.setColorBorde(new java.awt.Color(245, 245, 245));
        rSPanelRound28.setPreferredSize(new java.awt.Dimension(90, 90));
        rSPanelRound28.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 10, 10));

        btnHtpp.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        btnHtpp.setForeground(new java.awt.Color(0, 153, 255));
        btnHtpp.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/http_64.png"))); // NOI18N
        btnHtpp.setBorder(null);
        btnHtpp.setContentAreaFilled(false);
        btnHtpp.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnHtpp.setFocusPainted(false);
        btnHtpp.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnHtpp.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        btnHtpp.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnHtpp.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnHtppMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnHtppMouseExited(evt);
            }
        });
        btnHtpp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHtppActionPerformed(evt);
            }
        });
        rSPanelRound28.add(btnHtpp);

        jPanel40.add(rSPanelRound28, new java.awt.GridBagConstraints());

        jLabel30.setFont(new java.awt.Font("Roboto Medium", 0, 14)); // NOI18N
        jLabel30.setForeground(new java.awt.Color(51, 51, 51));
        jLabel30.setText("HTTP");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        jPanel40.add(jLabel30, gridBagConstraints);

        jPanel4.add(jPanel40);

        jPanel3.add(jPanel4);

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setLayout(new java.awt.GridLayout(1, 8));

        jPanel22.setBackground(new java.awt.Color(255, 255, 255));
        jPanel22.setLayout(new java.awt.GridBagLayout());

        rSPanelRound37.setBackground(new java.awt.Color(235, 235, 235));
        rSPanelRound37.setColorBackground(new java.awt.Color(245, 245, 245));
        rSPanelRound37.setColorBorde(new java.awt.Color(245, 245, 245));
        rSPanelRound37.setPreferredSize(new java.awt.Dimension(90, 90));
        rSPanelRound37.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 10, 10));

        btnIntegracion1.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        btnIntegracion1.setForeground(new java.awt.Color(0, 153, 255));
        btnIntegracion1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/live.png"))); // NOI18N
        btnIntegracion1.setBorder(null);
        btnIntegracion1.setContentAreaFilled(false);
        btnIntegracion1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnIntegracion1.setFocusPainted(false);
        btnIntegracion1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnIntegracion1.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        btnIntegracion1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnIntegracion1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnIntegracion1MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnIntegracion1MouseExited(evt);
            }
        });
        btnIntegracion1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIntegracion1ActionPerformed(evt);
            }
        });
        rSPanelRound37.add(btnIntegracion1);

        jPanel22.add(rSPanelRound37, new java.awt.GridBagConstraints());

        jLabel36.setFont(new java.awt.Font("Roboto Medium", 0, 14)); // NOI18N
        jLabel36.setForeground(new java.awt.Color(51, 51, 51));
        jLabel36.setText("Live");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        jPanel22.add(jLabel36, gridBagConstraints);

        jPanel6.add(jPanel22);

        panel21.setBackground(new java.awt.Color(255, 255, 255));
        panel21.setLayout(new java.awt.GridBagLayout());

        rSPanelRound9.setBackground(new java.awt.Color(235, 235, 235));
        rSPanelRound9.setColorBackground(new java.awt.Color(245, 245, 245));
        rSPanelRound9.setColorBorde(new java.awt.Color(245, 245, 245));
        rSPanelRound9.setPreferredSize(new java.awt.Dimension(90, 90));
        rSPanelRound9.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 10, 10));

        btnCorte.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        btnCorte.setForeground(new java.awt.Color(0, 153, 255));
        btnCorte.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/cortes_64.png"))); // NOI18N
        btnCorte.setBorder(null);
        btnCorte.setContentAreaFilled(false);
        btnCorte.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCorte.setFocusPainted(false);
        btnCorte.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnCorte.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        btnCorte.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnCorte.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnCorteMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnCorteMouseExited(evt);
            }
        });
        btnCorte.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCorteActionPerformed(evt);
            }
        });
        rSPanelRound9.add(btnCorte);

        panel21.add(rSPanelRound9, new java.awt.GridBagConstraints());

        jLabel8.setFont(new java.awt.Font("Roboto Medium", 0, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(51, 51, 51));
        jLabel8.setText("Cortes");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        panel21.add(jLabel8, gridBagConstraints);

        jPanel6.add(panel21);

        jPanel20.setBackground(new java.awt.Color(255, 255, 255));
        jPanel20.setLayout(new java.awt.GridBagLayout());

        rSPanelRound32.setBackground(new java.awt.Color(235, 235, 235));
        rSPanelRound32.setColorBackground(new java.awt.Color(245, 245, 245));
        rSPanelRound32.setColorBorde(new java.awt.Color(245, 245, 245));
        rSPanelRound32.setPreferredSize(new java.awt.Dimension(90, 90));
        rSPanelRound32.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 10, 10));

        btnElec.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        btnElec.setForeground(new java.awt.Color(0, 153, 255));
        btnElec.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/maquinados_64.png"))); // NOI18N
        btnElec.setBorder(null);
        btnElec.setContentAreaFilled(false);
        btnElec.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnElec.setFocusPainted(false);
        btnElec.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnElec.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        btnElec.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnElec.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnElecMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnElecMouseExited(evt);
            }
        });
        btnElec.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnElecActionPerformed(evt);
            }
        });
        rSPanelRound32.add(btnElec);

        jPanel20.add(rSPanelRound32, new java.awt.GridBagConstraints());

        jLabel31.setFont(new java.awt.Font("Roboto Medium", 0, 14)); // NOI18N
        jLabel31.setForeground(new java.awt.Color(51, 51, 51));
        jLabel31.setText("Maquinados");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        jPanel20.add(jLabel31, gridBagConstraints);

        jPanel6.add(jPanel20);

        jPanel23.setBackground(new java.awt.Color(255, 255, 255));
        jPanel23.setLayout(new java.awt.GridBagLayout());

        panel28.setBackground(new java.awt.Color(255, 255, 255));
        panel28.setLayout(new java.awt.GridBagLayout());

        rSPanelRound38.setBackground(new java.awt.Color(235, 235, 235));
        rSPanelRound38.setColorBackground(new java.awt.Color(245, 245, 245));
        rSPanelRound38.setColorBorde(new java.awt.Color(245, 245, 245));
        rSPanelRound38.setPreferredSize(new java.awt.Dimension(90, 90));
        rSPanelRound38.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 10, 10));

        btnCalidad1.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        btnCalidad1.setForeground(new java.awt.Color(0, 153, 255));
        btnCalidad1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/Acabados_64.png"))); // NOI18N
        btnCalidad1.setBorder(null);
        btnCalidad1.setContentAreaFilled(false);
        btnCalidad1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCalidad1.setFocusPainted(false);
        btnCalidad1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnCalidad1.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        btnCalidad1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnCalidad1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnCalidad1MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnCalidad1MouseExited(evt);
            }
        });
        btnCalidad1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCalidad1ActionPerformed(evt);
            }
        });
        rSPanelRound38.add(btnCalidad1);

        panel28.add(rSPanelRound38, new java.awt.GridBagConstraints());

        jLabel37.setFont(new java.awt.Font("Roboto Medium", 0, 14)); // NOI18N
        jLabel37.setForeground(new java.awt.Color(51, 51, 51));
        jLabel37.setText("Acabados");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        panel28.add(jLabel37, gridBagConstraints);

        jPanel23.add(panel28, new java.awt.GridBagConstraints());

        jPanel6.add(jPanel23);

        panel26.setBackground(new java.awt.Color(255, 255, 255));
        panel26.setLayout(new java.awt.GridBagLayout());

        rSPanelRound14.setBackground(new java.awt.Color(235, 235, 235));
        rSPanelRound14.setColorBackground(new java.awt.Color(245, 245, 245));
        rSPanelRound14.setColorBorde(new java.awt.Color(245, 245, 245));
        rSPanelRound14.setPreferredSize(new java.awt.Dimension(90, 90));
        rSPanelRound14.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 10, 10));

        btnCalidad.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        btnCalidad.setForeground(new java.awt.Color(0, 153, 255));
        btnCalidad.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/calidad_64.png"))); // NOI18N
        btnCalidad.setBorder(null);
        btnCalidad.setContentAreaFilled(false);
        btnCalidad.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCalidad.setFocusPainted(false);
        btnCalidad.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnCalidad.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        btnCalidad.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnCalidad.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnCalidadMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnCalidadMouseExited(evt);
            }
        });
        btnCalidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCalidadActionPerformed(evt);
            }
        });
        rSPanelRound14.add(btnCalidad);

        panel26.add(rSPanelRound14, new java.awt.GridBagConstraints());

        jLabel13.setFont(new java.awt.Font("Roboto Medium", 0, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(51, 51, 51));
        jLabel13.setText("Calidad");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        panel26.add(jLabel13, gridBagConstraints);

        jPanel6.add(panel26);

        panel27.setBackground(new java.awt.Color(255, 255, 255));
        panel27.setLayout(new java.awt.GridBagLayout());

        rSPanelRound15.setBackground(new java.awt.Color(235, 235, 235));
        rSPanelRound15.setColorBackground(new java.awt.Color(245, 245, 245));
        rSPanelRound15.setColorBorde(new java.awt.Color(245, 245, 245));
        rSPanelRound15.setPreferredSize(new java.awt.Dimension(90, 90));
        rSPanelRound15.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 10, 10));

        btnTrata.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        btnTrata.setForeground(new java.awt.Color(0, 153, 255));
        btnTrata.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/tratamientos_64.png"))); // NOI18N
        btnTrata.setBorder(null);
        btnTrata.setContentAreaFilled(false);
        btnTrata.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnTrata.setFocusPainted(false);
        btnTrata.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnTrata.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        btnTrata.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnTrata.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnTrataMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnTrataMouseExited(evt);
            }
        });
        btnTrata.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTrataActionPerformed(evt);
            }
        });
        rSPanelRound15.add(btnTrata);

        panel27.add(rSPanelRound15, new java.awt.GridBagConstraints());

        jLabel14.setFont(new java.awt.Font("Roboto Medium", 0, 14)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(51, 51, 51));
        jLabel14.setText("Tratamientos");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        panel27.add(jLabel14, gridBagConstraints);

        jPanel6.add(panel27);

        jPanel21.setBackground(new java.awt.Color(255, 255, 255));
        jPanel21.setLayout(new java.awt.GridBagLayout());

        rSPanelRound36.setBackground(new java.awt.Color(235, 235, 235));
        rSPanelRound36.setColorBackground(new java.awt.Color(245, 245, 245));
        rSPanelRound36.setColorBorde(new java.awt.Color(245, 245, 245));
        rSPanelRound36.setPreferredSize(new java.awt.Dimension(90, 90));
        rSPanelRound36.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 10, 10));

        btnIntegracion.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        btnIntegracion.setForeground(new java.awt.Color(0, 153, 255));
        btnIntegracion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/Integracion_64.png"))); // NOI18N
        btnIntegracion.setBorder(null);
        btnIntegracion.setContentAreaFilled(false);
        btnIntegracion.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnIntegracion.setFocusPainted(false);
        btnIntegracion.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnIntegracion.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        btnIntegracion.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnIntegracion.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnIntegracionMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnIntegracionMouseExited(evt);
            }
        });
        btnIntegracion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIntegracionActionPerformed(evt);
            }
        });
        rSPanelRound36.add(btnIntegracion);

        jPanel21.add(rSPanelRound36, new java.awt.GridBagConstraints());

        jLabel35.setFont(new java.awt.Font("Roboto Medium", 0, 14)); // NOI18N
        jLabel35.setForeground(new java.awt.Color(51, 51, 51));
        jLabel35.setText("Integracion");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        jPanel21.add(jLabel35, gridBagConstraints);

        jPanel6.add(jPanel21);

        jPanel3.add(jPanel6);

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setLayout(new java.awt.GridLayout(1, 8));

        panel31.setBackground(new java.awt.Color(255, 255, 255));
        panel31.setLayout(new java.awt.GridBagLayout());

        rSPanelRound16.setBackground(new java.awt.Color(235, 235, 235));
        rSPanelRound16.setColorBackground(new java.awt.Color(245, 245, 245));
        rSPanelRound16.setColorBorde(new java.awt.Color(245, 245, 245));
        rSPanelRound16.setPreferredSize(new java.awt.Dimension(90, 90));
        rSPanelRound16.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 10, 10));

        btnRegistro.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        btnRegistro.setForeground(new java.awt.Color(0, 153, 255));
        btnRegistro.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/add_64_1.png"))); // NOI18N
        btnRegistro.setBorder(null);
        btnRegistro.setContentAreaFilled(false);
        btnRegistro.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnRegistro.setFocusPainted(false);
        btnRegistro.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnRegistro.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        btnRegistro.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnRegistro.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnRegistroMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnRegistroMouseExited(evt);
            }
        });
        btnRegistro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistroActionPerformed(evt);
            }
        });
        rSPanelRound16.add(btnRegistro);

        panel31.add(rSPanelRound16, new java.awt.GridBagConstraints());

        jLabel15.setFont(new java.awt.Font("Roboto Medium", 0, 14)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(51, 51, 51));
        jLabel15.setText("Agregar");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        panel31.add(jLabel15, gridBagConstraints);

        jPanel5.add(panel31);

        panel38.setBackground(new java.awt.Color(255, 255, 255));
        panel38.setLayout(new java.awt.GridBagLayout());

        rSPanelRound39.setBackground(new java.awt.Color(235, 235, 235));
        rSPanelRound39.setColorBackground(new java.awt.Color(245, 245, 245));
        rSPanelRound39.setColorBorde(new java.awt.Color(245, 245, 245));
        rSPanelRound39.setPreferredSize(new java.awt.Dimension(90, 90));
        rSPanelRound39.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 10, 10));

        btnRH.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        btnRH.setForeground(new java.awt.Color(0, 153, 255));
        btnRH.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/rh.png"))); // NOI18N
        btnRH.setBorder(null);
        btnRH.setContentAreaFilled(false);
        btnRH.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnRH.setFocusPainted(false);
        btnRH.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnRH.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        btnRH.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnRH.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnRHMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnRHMouseExited(evt);
            }
        });
        btnRH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRHActionPerformed(evt);
            }
        });
        rSPanelRound39.add(btnRH);

        panel38.add(rSPanelRound39, new java.awt.GridBagConstraints());

        jLabel38.setFont(new java.awt.Font("Roboto Medium", 0, 14)); // NOI18N
        jLabel38.setForeground(new java.awt.Color(51, 51, 51));
        jLabel38.setText("RH");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        panel38.add(jLabel38, gridBagConstraints);

        jPanel5.add(panel38);

        panel32.setBackground(new java.awt.Color(255, 255, 255));
        panel32.setLayout(new java.awt.GridBagLayout());

        rSPanelRound17.setBackground(new java.awt.Color(235, 235, 235));
        rSPanelRound17.setColorBackground(new java.awt.Color(245, 245, 245));
        rSPanelRound17.setColorBorde(new java.awt.Color(245, 245, 245));
        rSPanelRound17.setPreferredSize(new java.awt.Dimension(90, 90));
        rSPanelRound17.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 10, 10));

        btnEmpleado.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        btnEmpleado.setForeground(new java.awt.Color(0, 153, 255));
        btnEmpleado.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/cxp.png"))); // NOI18N
        btnEmpleado.setBorder(null);
        btnEmpleado.setContentAreaFilled(false);
        btnEmpleado.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEmpleado.setFocusPainted(false);
        btnEmpleado.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnEmpleado.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        btnEmpleado.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnEmpleado.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnEmpleadoMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnEmpleadoMouseExited(evt);
            }
        });
        btnEmpleado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEmpleadoActionPerformed(evt);
            }
        });
        rSPanelRound17.add(btnEmpleado);

        panel32.add(rSPanelRound17, new java.awt.GridBagConstraints());

        jLabel16.setFont(new java.awt.Font("Roboto Medium", 0, 14)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(51, 51, 51));
        jLabel16.setText("CXP");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        panel32.add(jLabel16, gridBagConstraints);

        jPanel5.add(panel32);

        panel33.setBackground(new java.awt.Color(255, 255, 255));
        panel33.setLayout(new java.awt.GridBagLayout());

        rSPanelRound18.setBackground(new java.awt.Color(235, 235, 235));
        rSPanelRound18.setColorBackground(new java.awt.Color(245, 245, 245));
        rSPanelRound18.setColorBorde(new java.awt.Color(245, 245, 245));
        rSPanelRound18.setPreferredSize(new java.awt.Dimension(90, 90));
        rSPanelRound18.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 10, 10));

        btnInventario.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        btnInventario.setForeground(new java.awt.Color(0, 153, 255));
        btnInventario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/almacen_64.png"))); // NOI18N
        btnInventario.setBorder(null);
        btnInventario.setContentAreaFilled(false);
        btnInventario.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnInventario.setFocusPainted(false);
        btnInventario.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnInventario.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        btnInventario.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnInventario.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnInventarioMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnInventarioMouseExited(evt);
            }
        });
        btnInventario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInventarioActionPerformed(evt);
            }
        });
        rSPanelRound18.add(btnInventario);

        panel33.add(rSPanelRound18, new java.awt.GridBagConstraints());

        jLabel17.setFont(new java.awt.Font("Roboto Medium", 0, 14)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(51, 51, 51));
        jLabel17.setText("Almacen");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        panel33.add(jLabel17, gridBagConstraints);

        jPanel5.add(panel33);

        panel34.setBackground(new java.awt.Color(255, 255, 255));
        panel34.setLayout(new java.awt.GridBagLayout());

        rSPanelRound19.setBackground(new java.awt.Color(235, 235, 235));
        rSPanelRound19.setColorBackground(new java.awt.Color(245, 245, 245));
        rSPanelRound19.setColorBorde(new java.awt.Color(245, 245, 245));
        rSPanelRound19.setPreferredSize(new java.awt.Dimension(90, 90));
        rSPanelRound19.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 10, 10));

        btnInventario1.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        btnInventario1.setForeground(new java.awt.Color(0, 153, 255));
        btnInventario1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IconoC/ensamblador.png"))); // NOI18N
        btnInventario1.setBorder(null);
        btnInventario1.setContentAreaFilled(false);
        btnInventario1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnInventario1.setFocusPainted(false);
        btnInventario1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnInventario1.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        btnInventario1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnInventario1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnInventario1MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnInventario1MouseExited(evt);
            }
        });
        btnInventario1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInventario1ActionPerformed(evt);
            }
        });
        rSPanelRound19.add(btnInventario1);

        panel34.add(rSPanelRound19, new java.awt.GridBagConstraints());

        jLabel18.setFont(new java.awt.Font("Roboto Medium", 0, 14)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(51, 51, 51));
        jLabel18.setText("Ensambles");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        panel34.add(jLabel18, gridBagConstraints);

        jPanel5.add(panel34);

        panel35.setBackground(new java.awt.Color(255, 255, 255));
        panel35.setLayout(new java.awt.GridBagLayout());

        rSPanelRound20.setBackground(new java.awt.Color(235, 235, 235));
        rSPanelRound20.setColorBackground(new java.awt.Color(245, 245, 245));
        rSPanelRound20.setColorBorde(new java.awt.Color(245, 245, 245));
        rSPanelRound20.setPreferredSize(new java.awt.Dimension(90, 90));
        rSPanelRound20.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 10, 10));

        btnInventario2.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        btnInventario2.setForeground(new java.awt.Color(0, 153, 255));
        btnInventario2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/inv_planos_64.png"))); // NOI18N
        btnInventario2.setBorder(null);
        btnInventario2.setContentAreaFilled(false);
        btnInventario2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnInventario2.setFocusPainted(false);
        btnInventario2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnInventario2.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        btnInventario2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnInventario2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnInventario2MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnInventario2MouseExited(evt);
            }
        });
        btnInventario2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInventario2ActionPerformed(evt);
            }
        });
        rSPanelRound20.add(btnInventario2);

        panel35.add(rSPanelRound20, new java.awt.GridBagConstraints());

        jLabel19.setFont(new java.awt.Font("Roboto Medium", 0, 14)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(51, 51, 51));
        jLabel19.setText("Inv. Planos");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        panel35.add(jLabel19, gridBagConstraints);

        jPanel5.add(panel35);

        panel36.setBackground(new java.awt.Color(255, 255, 255));
        panel36.setLayout(new java.awt.GridBagLayout());

        rSPanelRound21.setBackground(new java.awt.Color(235, 235, 235));
        rSPanelRound21.setColorBackground(new java.awt.Color(245, 245, 245));
        rSPanelRound21.setColorBorde(new java.awt.Color(245, 245, 245));
        rSPanelRound21.setPreferredSize(new java.awt.Dimension(90, 90));
        rSPanelRound21.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 10, 10));

        btnRemisiones.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        btnRemisiones.setForeground(new java.awt.Color(0, 153, 255));
        btnRemisiones.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/remisiones_64.png"))); // NOI18N
        btnRemisiones.setBorder(null);
        btnRemisiones.setContentAreaFilled(false);
        btnRemisiones.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnRemisiones.setFocusPainted(false);
        btnRemisiones.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnRemisiones.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        btnRemisiones.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnRemisiones.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnRemisionesMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnRemisionesMouseExited(evt);
            }
        });
        btnRemisiones.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemisionesActionPerformed(evt);
            }
        });
        rSPanelRound21.add(btnRemisiones);

        panel36.add(rSPanelRound21, new java.awt.GridBagConstraints());

        jLabel20.setFont(new java.awt.Font("Roboto Medium", 0, 14)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(51, 51, 51));
        jLabel20.setText("Remisiones");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        panel36.add(jLabel20, gridBagConstraints);

        jPanel5.add(panel36);

        panel37.setBackground(new java.awt.Color(255, 255, 255));
        panel37.setLayout(new java.awt.GridBagLayout());

        rSPanelRound22.setBackground(new java.awt.Color(235, 235, 235));
        rSPanelRound22.setColorBackground(new java.awt.Color(245, 245, 245));
        rSPanelRound22.setColorBorde(new java.awt.Color(245, 245, 245));
        rSPanelRound22.setPreferredSize(new java.awt.Dimension(90, 90));
        rSPanelRound22.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 10, 10));

        btnEntrega.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        btnEntrega.setForeground(new java.awt.Color(0, 153, 255));
        btnEntrega.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Pedidos.png"))); // NOI18N
        btnEntrega.setBorder(null);
        btnEntrega.setContentAreaFilled(false);
        btnEntrega.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEntrega.setFocusPainted(false);
        btnEntrega.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnEntrega.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        btnEntrega.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnEntrega.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnEntregaMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnEntregaMouseExited(evt);
            }
        });
        btnEntrega.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEntregaActionPerformed(evt);
            }
        });
        rSPanelRound22.add(btnEntrega);

        panel37.add(rSPanelRound22, new java.awt.GridBagConstraints());

        jLabel21.setFont(new java.awt.Font("Roboto Medium", 0, 14)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(51, 51, 51));
        jLabel21.setText("Pedidos");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        panel37.add(jLabel21, gridBagConstraints);

        jPanel5.add(panel37);

        jPanel39.setBackground(new java.awt.Color(255, 255, 255));
        jPanel39.setLayout(new java.awt.GridBagLayout());

        panelPedidos.setBackground(new java.awt.Color(255, 255, 255));
        panelPedidos.setRoundBottomLeft(50);
        panelPedidos.setRoundBottomRight(50);
        panelPedidos.setRoundTopLeft(50);
        panelPedidos.setRoundTopRight(50);
        jPanel39.add(panelPedidos, new java.awt.GridBagConstraints());

        rSPanelRound33.setBackground(new java.awt.Color(235, 235, 235));
        rSPanelRound33.setColorBackground(new java.awt.Color(245, 245, 245));
        rSPanelRound33.setColorBorde(new java.awt.Color(245, 245, 245));
        rSPanelRound33.setPreferredSize(new java.awt.Dimension(90, 90));
        rSPanelRound33.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 10, 10));

        btnPedidos.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        btnPedidos.setForeground(new java.awt.Color(0, 153, 255));
        btnPedidos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/entragas_64.png"))); // NOI18N
        btnPedidos.setBorder(null);
        btnPedidos.setContentAreaFilled(false);
        btnPedidos.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnPedidos.setFocusPainted(false);
        btnPedidos.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnPedidos.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        btnPedidos.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnPedidos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnPedidosMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnPedidosMouseExited(evt);
            }
        });
        btnPedidos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPedidosActionPerformed(evt);
            }
        });
        rSPanelRound33.add(btnPedidos);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        jPanel39.add(rSPanelRound33, gridBagConstraints);

        jLabel32.setFont(new java.awt.Font("Roboto Medium", 0, 14)); // NOI18N
        jLabel32.setForeground(new java.awt.Color(51, 51, 51));
        jLabel32.setText("Entregas");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        jPanel39.add(jLabel32, gridBagConstraints);

        jPanel5.add(jPanel39);

        jPanel3.add(jPanel5);

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setLayout(new java.awt.GridLayout(1, 0));

        panel41.setBackground(new java.awt.Color(255, 255, 255));
        panel41.setLayout(new java.awt.GridBagLayout());

        rSPanelRound23.setBackground(new java.awt.Color(102, 0, 153));
        rSPanelRound23.setColorBackground(new java.awt.Color(245, 245, 245));
        rSPanelRound23.setColorBorde(new java.awt.Color(245, 245, 245));
        rSPanelRound23.setPreferredSize(new java.awt.Dimension(90, 90));
        rSPanelRound23.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 10, 10));

        btnRequisicion.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        btnRequisicion.setForeground(new java.awt.Color(0, 153, 255));
        btnRequisicion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/requisiciones_64.png"))); // NOI18N
        btnRequisicion.setBorder(null);
        btnRequisicion.setContentAreaFilled(false);
        btnRequisicion.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnRequisicion.setFocusPainted(false);
        btnRequisicion.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnRequisicion.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        btnRequisicion.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnRequisicion.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnRequisicionMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnRequisicionMouseExited(evt);
            }
        });
        btnRequisicion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRequisicionActionPerformed(evt);
            }
        });
        rSPanelRound23.add(btnRequisicion);

        panel41.add(rSPanelRound23, new java.awt.GridBagConstraints());

        jLabel22.setFont(new java.awt.Font("Roboto Medium", 0, 14)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(51, 51, 51));
        jLabel22.setText("Requisiciones");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        panel41.add(jLabel22, gridBagConstraints);

        jPanel7.add(panel41);

        panel42.setBackground(new java.awt.Color(255, 255, 255));
        panel42.setLayout(new java.awt.GridBagLayout());

        rSPanelRound24.setBackground(new java.awt.Color(235, 235, 235));
        rSPanelRound24.setColorBackground(new java.awt.Color(245, 245, 245));
        rSPanelRound24.setColorBorde(new java.awt.Color(245, 245, 245));
        rSPanelRound24.setPreferredSize(new java.awt.Dimension(90, 90));
        rSPanelRound24.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 10, 10));

        btnOrden.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        btnOrden.setForeground(new java.awt.Color(0, 153, 255));
        btnOrden.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/compras_64.png"))); // NOI18N
        btnOrden.setBorder(null);
        btnOrden.setContentAreaFilled(false);
        btnOrden.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnOrden.setFocusPainted(false);
        btnOrden.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnOrden.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        btnOrden.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnOrden.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnOrdenMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnOrdenMouseExited(evt);
            }
        });
        btnOrden.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOrdenActionPerformed(evt);
            }
        });
        rSPanelRound24.add(btnOrden);

        panel42.add(rSPanelRound24, new java.awt.GridBagConstraints());

        jLabel23.setFont(new java.awt.Font("Roboto Medium", 0, 14)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(51, 51, 51));
        jLabel23.setText("Compras");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        panel42.add(jLabel23, gridBagConstraints);

        jPanel7.add(panel42);

        panel43.setBackground(new java.awt.Color(255, 255, 255));
        panel43.setLayout(new java.awt.GridBagLayout());

        rSPanelRound25.setBackground(new java.awt.Color(235, 235, 235));
        rSPanelRound25.setColorBackground(new java.awt.Color(245, 245, 245));
        rSPanelRound25.setColorBorde(new java.awt.Color(245, 245, 245));
        rSPanelRound25.setPreferredSize(new java.awt.Dimension(90, 90));
        rSPanelRound25.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 10, 10));

        btnOrden1.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        btnOrden1.setForeground(new java.awt.Color(0, 153, 255));
        btnOrden1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/aprobacion_64_1.png"))); // NOI18N
        btnOrden1.setBorder(null);
        btnOrden1.setContentAreaFilled(false);
        btnOrden1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnOrden1.setFocusPainted(false);
        btnOrden1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnOrden1.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        btnOrden1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnOrden1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnOrden1MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnOrden1MouseExited(evt);
            }
        });
        btnOrden1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOrden1ActionPerformed(evt);
            }
        });
        rSPanelRound25.add(btnOrden1);

        panel43.add(rSPanelRound25, new java.awt.GridBagConstraints());

        jLabel24.setFont(new java.awt.Font("Roboto Medium", 0, 14)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(51, 51, 51));
        jLabel24.setText("Aprobacion");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        panel43.add(jLabel24, gridBagConstraints);

        jPanel7.add(panel43);

        panel44.setBackground(new java.awt.Color(255, 255, 255));
        panel44.setLayout(new java.awt.GridBagLayout());

        rSPanelRound26.setBackground(new java.awt.Color(235, 235, 235));
        rSPanelRound26.setColorBackground(new java.awt.Color(245, 245, 245));
        rSPanelRound26.setColorBorde(new java.awt.Color(245, 245, 245));
        rSPanelRound26.setPreferredSize(new java.awt.Dimension(90, 90));
        rSPanelRound26.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 10, 10));

        btnRecibos.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        btnRecibos.setForeground(new java.awt.Color(0, 153, 255));
        btnRecibos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/recibos_64.png"))); // NOI18N
        btnRecibos.setBorder(null);
        btnRecibos.setContentAreaFilled(false);
        btnRecibos.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnRecibos.setFocusPainted(false);
        btnRecibos.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnRecibos.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        btnRecibos.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnRecibos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnRecibosMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnRecibosMouseExited(evt);
            }
        });
        btnRecibos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRecibosActionPerformed(evt);
            }
        });
        rSPanelRound26.add(btnRecibos);

        panel44.add(rSPanelRound26, new java.awt.GridBagConstraints());

        jLabel25.setFont(new java.awt.Font("Roboto Medium", 0, 14)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(51, 51, 51));
        jLabel25.setText("Recibos");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        panel44.add(jLabel25, gridBagConstraints);

        jPanel7.add(panel44);

        panel45.setBackground(new java.awt.Color(255, 255, 255));
        panel45.setLayout(new java.awt.GridBagLayout());

        rSPanelRound27.setBackground(new java.awt.Color(235, 235, 235));
        rSPanelRound27.setColorBackground(new java.awt.Color(245, 245, 245));
        rSPanelRound27.setColorBorde(new java.awt.Color(245, 245, 245));
        rSPanelRound27.setPreferredSize(new java.awt.Dimension(90, 90));
        rSPanelRound27.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 10, 10));

        btnPrestamos.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        btnPrestamos.setForeground(new java.awt.Color(0, 153, 255));
        btnPrestamos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/prestamos_64.png"))); // NOI18N
        btnPrestamos.setBorder(null);
        btnPrestamos.setContentAreaFilled(false);
        btnPrestamos.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnPrestamos.setFocusPainted(false);
        btnPrestamos.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnPrestamos.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        btnPrestamos.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnPrestamos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnPrestamosMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnPrestamosMouseExited(evt);
            }
        });
        btnPrestamos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrestamosActionPerformed(evt);
            }
        });
        rSPanelRound27.add(btnPrestamos);

        panel45.add(rSPanelRound27, new java.awt.GridBagConstraints());

        jLabel26.setFont(new java.awt.Font("Roboto Medium", 0, 14)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(51, 51, 51));
        jLabel26.setText("Prestamos");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        panel45.add(jLabel26, gridBagConstraints);

        jPanel7.add(panel45);

        panel46.setBackground(new java.awt.Color(255, 255, 255));
        panel46.setLayout(new java.awt.GridBagLayout());

        rSPanelRound31.setBackground(new java.awt.Color(235, 235, 235));
        rSPanelRound31.setColorBackground(new java.awt.Color(245, 245, 245));
        rSPanelRound31.setColorBorde(new java.awt.Color(245, 245, 245));
        rSPanelRound31.setPreferredSize(new java.awt.Dimension(90, 90));
        rSPanelRound31.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 10, 10));

        btnCotizacion.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        btnCotizacion.setForeground(new java.awt.Color(0, 153, 255));
        btnCotizacion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/cotizacion_64.png"))); // NOI18N
        btnCotizacion.setBorder(null);
        btnCotizacion.setContentAreaFilled(false);
        btnCotizacion.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCotizacion.setFocusPainted(false);
        btnCotizacion.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnCotizacion.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        btnCotizacion.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnCotizacion.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnCotizacionMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnCotizacionMouseExited(evt);
            }
        });
        btnCotizacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCotizacionActionPerformed(evt);
            }
        });
        rSPanelRound31.add(btnCotizacion);

        panel46.add(rSPanelRound31, new java.awt.GridBagConstraints());

        jLabel27.setFont(new java.awt.Font("Roboto Medium", 0, 14)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(51, 51, 51));
        jLabel27.setText("Cotizaciones");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        panel46.add(jLabel27, gridBagConstraints);

        jPanel7.add(panel46);

        panel47.setBackground(new java.awt.Color(255, 255, 255));
        panel47.setLayout(new java.awt.GridBagLayout());

        rSPanelRound29.setBackground(new java.awt.Color(235, 235, 235));
        rSPanelRound29.setColorBackground(new java.awt.Color(245, 245, 245));
        rSPanelRound29.setColorBorde(new java.awt.Color(245, 245, 245));
        rSPanelRound29.setPreferredSize(new java.awt.Dimension(90, 90));
        rSPanelRound29.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 10, 10));

        btnVer.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        btnVer.setForeground(new java.awt.Color(0, 153, 255));
        btnVer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/ver_requi_64.png"))); // NOI18N
        btnVer.setBorder(null);
        btnVer.setBorderPainted(false);
        btnVer.setContentAreaFilled(false);
        btnVer.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnVer.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnVer.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        btnVer.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnVer.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnVerMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnVerMouseExited(evt);
            }
        });
        btnVer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVerActionPerformed(evt);
            }
        });
        rSPanelRound29.add(btnVer);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        panel47.add(rSPanelRound29, gridBagConstraints);

        jLabel28.setFont(new java.awt.Font("Roboto Medium", 0, 14)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(51, 51, 51));
        jLabel28.setText("Ver requisiciones");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        panel47.add(jLabel28, gridBagConstraints);

        lblNotiRequis.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        lblNotiRequis.setForeground(new java.awt.Color(255, 255, 255));
        lblNotiRequis.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblNotiRequis.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/CR.png"))); // NOI18N
        lblNotiRequis.setText("0");
        lblNotiRequis.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        panel47.add(lblNotiRequis, gridBagConstraints);

        jPanel7.add(panel47);

        jPanel41.setBackground(new java.awt.Color(255, 255, 255));
        jPanel41.setLayout(new java.awt.GridBagLayout());

        rSPanelRound34.setBackground(new java.awt.Color(235, 235, 235));
        rSPanelRound34.setColorBackground(new java.awt.Color(245, 245, 245));
        rSPanelRound34.setColorBorde(new java.awt.Color(245, 245, 245));
        rSPanelRound34.setPreferredSize(new java.awt.Dimension(90, 90));
        rSPanelRound34.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 10, 10));

        btnCostos.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        btnCostos.setForeground(new java.awt.Color(0, 153, 255));
        btnCostos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/costos_64.png"))); // NOI18N
        btnCostos.setBorder(null);
        btnCostos.setContentAreaFilled(false);
        btnCostos.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCostos.setFocusPainted(false);
        btnCostos.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnCostos.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        btnCostos.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnCostos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnCostosMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnCostosMouseExited(evt);
            }
        });
        btnCostos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCostosActionPerformed(evt);
            }
        });
        rSPanelRound34.add(btnCostos);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        jPanel41.add(rSPanelRound34, gridBagConstraints);

        jLabel33.setFont(new java.awt.Font("Roboto Medium", 0, 14)); // NOI18N
        jLabel33.setForeground(new java.awt.Color(51, 51, 51));
        jLabel33.setText("Costos");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        jPanel41.add(jLabel33, gridBagConstraints);

        lblNotiCostos.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        lblNotiCostos.setForeground(new java.awt.Color(255, 255, 255));
        lblNotiCostos.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblNotiCostos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/CR.png"))); // NOI18N
        lblNotiCostos.setText("0");
        lblNotiCostos.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        jPanel41.add(lblNotiCostos, gridBagConstraints);

        jPanel7.add(jPanel41);

        panel49.setBackground(new java.awt.Color(255, 255, 255));
        panel49.setLayout(new java.awt.GridBagLayout());

        rSPanelRound35.setBackground(new java.awt.Color(235, 235, 235));
        rSPanelRound35.setColorBackground(new java.awt.Color(245, 245, 245));
        rSPanelRound35.setColorBorde(new java.awt.Color(245, 245, 245));
        rSPanelRound35.setPreferredSize(new java.awt.Dimension(90, 90));
        rSPanelRound35.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 10, 10));

        btnCalendario.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        btnCalendario.setForeground(new java.awt.Color(0, 153, 255));
        btnCalendario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/calendario_64.png"))); // NOI18N
        btnCalendario.setBorder(null);
        btnCalendario.setContentAreaFilled(false);
        btnCalendario.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCalendario.setFocusPainted(false);
        btnCalendario.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnCalendario.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        btnCalendario.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnCalendario.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnCalendarioMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnCalendarioMouseExited(evt);
            }
        });
        btnCalendario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCalendarioActionPerformed(evt);
            }
        });
        rSPanelRound35.add(btnCalendario);

        panel49.add(rSPanelRound35, new java.awt.GridBagConstraints());

        jLabel34.setFont(new java.awt.Font("Roboto Medium", 0, 14)); // NOI18N
        jLabel34.setForeground(new java.awt.Color(51, 51, 51));
        jLabel34.setText("Calendario");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        panel49.add(jLabel34, gridBagConstraints);

        jPanel7.add(panel49);

        jPanel3.add(jPanel7);

        jDesktopPane1.add(jPanel3, java.awt.BorderLayout.SOUTH);

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));
        jPanel8.setLayout(new java.awt.GridLayout(1, 0));
        jDesktopPane1.add(jPanel8, java.awt.BorderLayout.PAGE_START);

        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/si3i.png"))); // NOI18N
        jDesktopPane1.add(jLabel12, java.awt.BorderLayout.CENTER);

        getContentPane().add(jDesktopPane1, java.awt.BorderLayout.CENTER);

        jScrollPane1.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 1, true));
        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new java.awt.GridBagLayout());

        jLabel9.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(204, 204, 204));
        jLabel9.setText("Produccion");
        jPanel2.add(jLabel9, new java.awt.GridBagConstraints());

        jLabel10.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(204, 204, 204));
        jLabel10.setText("Compras");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 10;
        jPanel2.add(jLabel10, gridBagConstraints);

        jLabel11.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(204, 204, 204));
        jLabel11.setText("Administracion");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 15;
        jPanel2.add(jLabel11, gridBagConstraints);

        jScrollPane1.setViewportView(jPanel2);

        getContentPane().add(jScrollPane1, java.awt.BorderLayout.WEST);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        Configuracion e = new Configuracion(this, true, lblId.getText(), lblNombre.getText());
        e.setVisible(true);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void miReporteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miReporteActionPerformed

    }//GEN-LAST:event_miReporteActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        visto();
        verNotificaciones();
        Notificaciones n = new Notificaciones(this, lblId.getText(), lblNombre.getText(), lblId.getText());
        n.setBounds(jButton3.getX(), jButton3.getY() + 50, n.getWidth(), n.getHeight());
        n.setVisible(true);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        Mensajes n = new Mensajes(lblId.getText());
        n.setVisible(true);
    }//GEN-LAST:event_jButton4ActionPerformed

    private void lblId1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblId1MouseClicked
        try {
            Clip clip;
            clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(getClass().getResourceAsStream("/Gif/campana.wav")));
            clip.start();
        } catch (Exception e) {
            System.out.println("NO SE EJECUTO EL SISTEMA DE AUDIO: " + e);
        }
    }//GEN-LAST:event_lblId1MouseClicked

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        guardarUbicacionVentana(this);
        System.exit(0);
    }//GEN-LAST:event_formWindowClosing

    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed
        int modifer = evt.getModifiersEx();
        if (modifer == (KeyEvent.ALT_DOWN_MASK | KeyEvent.CTRL_DOWN_MASK) && evt.getKeyCode() == KeyEvent.VK_M) {
            JOptionPane.showMessageDialog(this, "SE ARMO");
        } else {
            System.out.println("no");
        }
    }//GEN-LAST:event_formKeyPressed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        int opc = JOptionPane.showConfirmDialog(this, "¿Estas seguro de cerrar sesion?");
        if (opc == 0) {
            InicioSesion inicio = new InicioSesion();
            inicio.setLocationRelativeTo(this);
            inicio.setVisible(true);
            this.dispose();
        }
    }//GEN-LAST:event_jButton5ActionPerformed

    private void btnModificar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificar1ActionPerformed
        try {
            backup.setSize(1112, 691);
            backup.setLocationRelativeTo(this);
            backup.setVisible(true);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnModificar1ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        activarNombres = !activarNombres;
        agregarMenu();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void btnCalendarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCalendarioActionPerformed
        Calendario c = new Calendario(lblId.getText(), this);
        jDesktopPane1.add(c);
        c.toFront();
        c.setLocation(jDesktopPane1.getWidth() / 2 - c.getWidth() / 2, jDesktopPane1.getHeight() / 2 - c.getHeight() / 2);
        try {
            c.setMaximum(true);
        } catch (PropertyVetoException e) {
            Logger.getLogger(Inicio1.class.getName()).log(Level.SEVERE, null, e);
        }
        c.setVisible(true);
    }//GEN-LAST:event_btnCalendarioActionPerformed

    private void btnCalendarioMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCalendarioMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_btnCalendarioMouseExited

    private void btnCalendarioMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCalendarioMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_btnCalendarioMouseEntered

    private void btnCostosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCostosActionPerformed
        JFrame f = (JFrame) JOptionPane.getFrameForComponent(this);
        inicioCostos = new InicioCostos(f, true);
        if (notiCostos == 0) {
            inicioCostos.lblNotiCostos.setVisible(false);
        } else {
            inicioCostos.lblNotiCostos.setVisible(true);
            inicioCostos.lblNotiCostos.setText(String.valueOf(notiCostos));
        }
        inicioCostos.setLocationRelativeTo(f);
        inicioCostos.btnCostos.addActionListener(this);
        inicioCostos.btnEvaluacion.addActionListener(this);
        inicioCostos.btnCosteo.addActionListener(this);
        inicioCostos.setVisible(true);
    }//GEN-LAST:event_btnCostosActionPerformed

    private void btnCostosMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCostosMouseExited
        //        btnCostos.setText("");
    }//GEN-LAST:event_btnCostosMouseExited

    private void btnCostosMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCostosMouseEntered
        //        btnCostos.setText("Costos");
    }//GEN-LAST:event_btnCostosMouseEntered

    private void btnVerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVerActionPerformed
        VerRequisiciones c = new VerRequisiciones(lblId.getText());
        jDesktopPane1.add(c);
        c.toFront();
        c.setLocation(jDesktopPane1.getWidth() / 2 - c.getWidth() / 2, jDesktopPane1.getHeight() / 2 - c.getHeight() / 2);
        try {
            c.setMaximum(true);
        } catch (PropertyVetoException e) {
            Logger.getLogger(Inicio1.class.getName()).log(Level.SEVERE, null, e);
        }
        c.setVisible(true);
    }//GEN-LAST:event_btnVerActionPerformed

    private void btnVerMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnVerMouseExited
        //        btnVer.setText("");
    }//GEN-LAST:event_btnVerMouseExited

    private void btnVerMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnVerMouseEntered
        //        btnVer.setText("VER REQUISICION");
    }//GEN-LAST:event_btnVerMouseEntered

    private void btnCotizacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCotizacionActionPerformed
        InicioCotizacion c = new InicioCotizacion(lblId.getText(), this);
        jDesktopPane1.add(c);
        c.toFront();
        c.setLocation(jDesktopPane1.getWidth() / 2 - c.getWidth() / 2, jDesktopPane1.getHeight() / 2 - c.getHeight() / 2);
        try {
            c.setMaximum(true);
        } catch (PropertyVetoException e) {
            Logger.getLogger(Inicio1.class.getName()).log(Level.SEVERE, null, e);
        }
        c.setVisible(true);
    }//GEN-LAST:event_btnCotizacionActionPerformed

    private void btnCotizacionMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCotizacionMouseExited
        //        btnCotizacion.setText("");
    }//GEN-LAST:event_btnCotizacionMouseExited

    private void btnCotizacionMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCotizacionMouseEntered
        //        btnCotizacion.setText("COTIZACION");
    }//GEN-LAST:event_btnCotizacionMouseEntered

    private void btnPrestamosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrestamosActionPerformed
        Prestamo c = new Prestamo(lblId.getText(), this);
        jDesktopPane1.add(c);
        c.toFront();
        c.setLocation(jDesktopPane1.getWidth() / 2 - c.getWidth() / 2, jDesktopPane1.getHeight() / 2 - c.getHeight() / 2);
        try {
            c.setMaximum(true);
        } catch (PropertyVetoException e) {
            Logger.getLogger(Inicio1.class.getName()).log(Level.SEVERE, null, e);
        }
        c.setVisible(true);
    }//GEN-LAST:event_btnPrestamosActionPerformed

    private void btnPrestamosMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPrestamosMouseExited
        //        btnPrestamos.setText("");
    }//GEN-LAST:event_btnPrestamosMouseExited

    private void btnPrestamosMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPrestamosMouseEntered
        //        btnPrestamos.setText("PRESTAMOS");
    }//GEN-LAST:event_btnPrestamosMouseEntered

    private void btnRecibosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRecibosActionPerformed
        Recibos c = new Recibos(lblId.getText());
        jDesktopPane1.add(c);
        c.toFront();
        c.setLocation(jDesktopPane1.getWidth() / 2 - c.getWidth() / 2, jDesktopPane1.getHeight() / 2 - c.getHeight() / 2);
        try {
            c.setMaximum(true);
        } catch (PropertyVetoException e) {
            Logger.getLogger(Inicio1.class.getName()).log(Level.SEVERE, null, e);
        }
        c.setVisible(true);
    }//GEN-LAST:event_btnRecibosActionPerformed

    private void btnRecibosMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnRecibosMouseExited
        //        btnRecibos.setText("");
    }//GEN-LAST:event_btnRecibosMouseExited

    private void btnRecibosMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnRecibosMouseEntered
        //        btnRecibos.setText("RECIBOS");
    }//GEN-LAST:event_btnRecibosMouseEntered

    private void btnOrden1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOrden1ActionPerformed
        Aprobacion c = new Aprobacion(lblId.getText());
        jDesktopPane1.add(c);
        c.toFront();
        c.setLocation(jDesktopPane1.getWidth() / 2 - c.getWidth() / 2, jDesktopPane1.getHeight() / 2 - c.getHeight() / 2);
        try {
            c.setMaximum(true);
        } catch (PropertyVetoException e) {
            Logger.getLogger(Inicio1.class.getName()).log(Level.SEVERE, null, e);
        }
        c.setVisible(true);
    }//GEN-LAST:event_btnOrden1ActionPerformed

    private void btnOrden1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnOrden1MouseExited
        //        btnOrden1.setText("");
    }//GEN-LAST:event_btnOrden1MouseExited

    private void btnOrden1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnOrden1MouseEntered
        //        btnOrden1.setText("APROBACION");
    }//GEN-LAST:event_btnOrden1MouseEntered

    private void btnOrdenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOrdenActionPerformed
        OrdenDeCompra c = new OrdenDeCompra(lblId.getText());
        jDesktopPane1.add(c);
        c.toFront();
        c.setLocation(jDesktopPane1.getWidth() / 2 - c.getWidth() / 2, jDesktopPane1.getHeight() / 2 - c.getHeight() / 2);
        try {
            c.setMaximum(true);
        } catch (PropertyVetoException e) {
            Logger.getLogger(Inicio1.class.getName()).log(Level.SEVERE, null, e);
        }
        c.setVisible(true);
    }//GEN-LAST:event_btnOrdenActionPerformed

    private void btnOrdenMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnOrdenMouseExited
        //        btnOrden.setText("");
    }//GEN-LAST:event_btnOrdenMouseExited

    private void btnOrdenMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnOrdenMouseEntered
        //        btnOrden.setText("COMPRAS");
    }//GEN-LAST:event_btnOrdenMouseEntered

    private void btnRequisicionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRequisicionActionPerformed
        requisicionDeCompra c = new requisicionDeCompra(lblId.getText(), lblNombre.getText());
        jDesktopPane1.add(c);
        c.toFront();
        c.setLocation(jDesktopPane1.getWidth() / 2 - c.getWidth() / 2, jDesktopPane1.getHeight() / 2 - c.getHeight() / 2);
        try {
            c.setMaximum(true);
        } catch (PropertyVetoException e) {
            Logger.getLogger(Inicio1.class.getName()).log(Level.SEVERE, null, e);
        }
        c.setVisible(true);
    }//GEN-LAST:event_btnRequisicionActionPerformed

    private void btnRequisicionMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnRequisicionMouseExited
        //        btnRequisicion.setText("");
    }//GEN-LAST:event_btnRequisicionMouseExited

    private void btnRequisicionMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnRequisicionMouseEntered
        //        btnRequisicion.setText("REQUISICIONES");
    }//GEN-LAST:event_btnRequisicionMouseEntered

    private void btnPedidosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPedidosActionPerformed
        c = new Pedidos(lblId.getText());
        jDesktopPane1.add(c);
        c.toFront();
        c.setLocation(jDesktopPane1.getWidth() / 2 - c.getWidth() / 2, jDesktopPane1.getHeight() / 2 - c.getHeight() / 2);
        try {
            c.setMaximum(true);
        } catch (PropertyVetoException e) {
            Logger.getLogger(Inicio1.class.getName()).log(Level.SEVERE, null, e);
        }
        c.setVisible(true);
        actualizar();
    }//GEN-LAST:event_btnPedidosActionPerformed

    private void btnPedidosMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPedidosMouseExited
        //        btnPedidos.setText("");
    }//GEN-LAST:event_btnPedidosMouseExited

    private void btnPedidosMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPedidosMouseEntered
        //        btnPedidos.setText("PEDIDOS");
    }//GEN-LAST:event_btnPedidosMouseEntered

    private void btnEntregaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEntregaActionPerformed
        EntregaRequisicion c = new EntregaRequisicion(lblId.getText());
        jDesktopPane1.add(c);
        c.toFront();
        c.setLocation(jDesktopPane1.getWidth() / 2 - c.getWidth() / 2, jDesktopPane1.getHeight() / 2 - c.getHeight() / 2);
        try {
            c.setMaximum(true);
        } catch (PropertyVetoException e) {
            Logger.getLogger(Inicio1.class.getName()).log(Level.SEVERE, null, e);
        }
        c.setVisible(true);
    }//GEN-LAST:event_btnEntregaActionPerformed

    private void btnEntregaMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEntregaMouseExited
        //        btnEntrega.setText("");
    }//GEN-LAST:event_btnEntregaMouseExited

    private void btnEntregaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEntregaMouseEntered
        //        btnEntrega.setText("ENTREGA");
    }//GEN-LAST:event_btnEntregaMouseEntered

    private void btnRemisionesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemisionesActionPerformed
        Remisiones c = new Remisiones(lblId.getText());
        jDesktopPane1.add(c);
        c.toFront();
        c.setLocation(jDesktopPane1.getWidth() / 2 - c.getWidth() / 2, jDesktopPane1.getHeight() / 2 - c.getHeight() / 2);
        try {
            c.setMaximum(true);
        } catch (PropertyVetoException e) {
            Logger.getLogger(Inicio1.class.getName()).log(Level.SEVERE, null, e);
        }
        c.setVisible(true);
    }//GEN-LAST:event_btnRemisionesActionPerformed

    private void btnRemisionesMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnRemisionesMouseExited
        //        btnRemisiones.setText("");
    }//GEN-LAST:event_btnRemisionesMouseExited

    private void btnRemisionesMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnRemisionesMouseEntered
        //        btnRemisiones.setText("REMISIONES");
    }//GEN-LAST:event_btnRemisionesMouseEntered

    private void btnInventario2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInventario2ActionPerformed
        InventarioPlanos c = new InventarioPlanos();
        jDesktopPane1.add(c);
        c.toFront();
        c.setLocation(jDesktopPane1.getWidth() / 2 - c.getWidth() / 2, jDesktopPane1.getHeight() / 2 - c.getHeight() / 2);
        try {
            c.setMaximum(true);
        } catch (PropertyVetoException e) {
            Logger.getLogger(Inicio1.class.getName()).log(Level.SEVERE, null, e);
        }
        c.setVisible(true);
    }//GEN-LAST:event_btnInventario2ActionPerformed

    private void btnInventario2MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnInventario2MouseExited
        //        btnInventario2.setText("");
    }//GEN-LAST:event_btnInventario2MouseExited

    private void btnInventario2MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnInventario2MouseEntered
        //        btnInventario2.setText("INV. PLANOS");
    }//GEN-LAST:event_btnInventario2MouseEntered

    private void btnInventario1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInventario1ActionPerformed
        Ensamble c = new Ensamble();
        jDesktopPane1.add(c);
        c.toFront();
        c.setLocation(jDesktopPane1.getWidth() / 2 - c.getWidth() / 2, jDesktopPane1.getHeight() / 2 - c.getHeight() / 2);
        try {
            c.setMaximum(true);
        } catch (PropertyVetoException e) {
            Logger.getLogger(Inicio1.class.getName()).log(Level.SEVERE, null, e);
        }
        c.setVisible(true);
    }//GEN-LAST:event_btnInventario1ActionPerformed

    private void btnInventario1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnInventario1MouseExited
        //        btnInventario1.setText("");
    }//GEN-LAST:event_btnInventario1MouseExited

    private void btnInventario1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnInventario1MouseEntered
        //        btnInventario1.setText("ENSAMBLE");
    }//GEN-LAST:event_btnInventario1MouseEntered

    private void btnInventarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInventarioActionPerformed
        inicioAlmacen = new InicioAlmacen(this, true);
        inicioAlmacen.setLocationRelativeTo(this);
        inicioAlmacen.btnInventario.addActionListener(this);
        inicioAlmacen.btnRevisar.addActionListener(this);
        inicioAlmacen.setVisible(true);
    }//GEN-LAST:event_btnInventarioActionPerformed

    private void btnInventarioMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnInventarioMouseExited
        //        btnInventario.setText("");
    }//GEN-LAST:event_btnInventarioMouseExited

    private void btnInventarioMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnInventarioMouseEntered
        //        btnInventario.setText("INVENTARIO");
    }//GEN-LAST:event_btnInventarioMouseEntered

    private void btnEmpleadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEmpleadoActionPerformed
        cxp c = new cxp();
        jDesktopPane1.add(c);
        c.toFront();
        c.setLocation(jDesktopPane1.getWidth() / 2 - c.getWidth() / 2, jDesktopPane1.getHeight() / 2 - c.getHeight() / 2);
        try {
            c.setMaximum(true);
        } catch (PropertyVetoException e) {
            Logger.getLogger(Inicio1.class.getName()).log(Level.SEVERE, null, e);
        }
        c.setVisible(true);
    }//GEN-LAST:event_btnEmpleadoActionPerformed

    private void btnEmpleadoMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEmpleadoMouseExited
        //        btnEmpleado.setText("");
    }//GEN-LAST:event_btnEmpleadoMouseExited

    private void btnEmpleadoMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEmpleadoMouseEntered
        //        btnEmpleado.setText("VER");
    }//GEN-LAST:event_btnEmpleadoMouseEntered

    private void btnRHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRHActionPerformed
        RH c = new RH(lblId.getText());
        jDesktopPane1.add(c);
        c.toFront();
        c.setLocation(jDesktopPane1.getWidth() / 2 - c.getWidth() / 2, jDesktopPane1.getHeight() / 2 - c.getHeight() / 2);
        try {
            c.setMaximum(true);
        } catch (PropertyVetoException e) {
            Logger.getLogger(Inicio1.class.getName()).log(Level.SEVERE, null, e);
        }
        c.setVisible(true);
    }//GEN-LAST:event_btnRHActionPerformed

    private void btnRHMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnRHMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_btnRHMouseExited

    private void btnRHMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnRHMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_btnRHMouseEntered

    private void btnRegistroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistroActionPerformed
        RegistrarEmpleado c = new RegistrarEmpleado();
        c.setVisible(true);
    }//GEN-LAST:event_btnRegistroActionPerformed

    private void btnRegistroMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnRegistroMouseExited
        //        btnRegistro.setText("");
    }//GEN-LAST:event_btnRegistroMouseExited

    private void btnRegistroMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnRegistroMouseEntered
        //        btnRegistro.setText("AÑADIR");
    }//GEN-LAST:event_btnRegistroMouseEntered

    private void btnIntegracionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIntegracionActionPerformed
        Integracion c = new Integracion(lblNombre.getText(), lblId.getText());
        jDesktopPane1.add(c);
        c.toFront();
        c.setLocation(jDesktopPane1.getWidth() / 2 - c.getWidth() / 2, jDesktopPane1.getHeight() / 2 - c.getHeight() / 2);
        try {
            c.setMaximum(true);
        } catch (PropertyVetoException e) {
            Logger.getLogger(Inicio1.class.getName()).log(Level.SEVERE, null, e);
        }
        c.setVisible(true);
    }//GEN-LAST:event_btnIntegracionActionPerformed

    private void btnIntegracionMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnIntegracionMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_btnIntegracionMouseExited

    private void btnIntegracionMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnIntegracionMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_btnIntegracionMouseEntered

    private void btnTrataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTrataActionPerformed
        Tratamiento c = new Tratamiento();
        jDesktopPane1.add(c);
        c.toFront();
        c.setLocation(jDesktopPane1.getWidth() / 2 - c.getWidth() / 2, jDesktopPane1.getHeight() / 2 - c.getHeight() / 2);
        try {
            c.setMaximum(true);
        } catch (PropertyVetoException e) {
            Logger.getLogger(Inicio1.class.getName()).log(Level.SEVERE, null, e);
        }
        c.setVisible(true);
    }//GEN-LAST:event_btnTrataActionPerformed

    private void btnTrataMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnTrataMouseExited
        //        btnTrata.setText("");
    }//GEN-LAST:event_btnTrataMouseExited

    private void btnTrataMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnTrataMouseEntered
        //        btnTrata.setText("TRATAMIENTO");
    }//GEN-LAST:event_btnTrataMouseEntered

    private void btnCalidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCalidadActionPerformed
        //        CalidadNew c = new CalidadNew(lblNombre.getText(), lblId.getText());
        CalidadDebug c = new CalidadDebug(lblNombre.getText(), lblId.getText());
        jDesktopPane1.add(c);
        c.toFront();
        c.setLocation(jDesktopPane1.getWidth() / 2 - c.getWidth() / 2, jDesktopPane1.getHeight() / 2 - c.getHeight() / 2);
        try {
            c.setMaximum(true);
        } catch (PropertyVetoException e) {
            Logger.getLogger(Inicio1.class.getName()).log(Level.SEVERE, null, e);
        }
        c.setVisible(true);
    }//GEN-LAST:event_btnCalidadActionPerformed

    private void btnCalidadMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCalidadMouseExited
        //        btnCalidad.setText("");
    }//GEN-LAST:event_btnCalidadMouseExited

    private void btnCalidadMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCalidadMouseEntered
        //        btnCalidad.setText("CALIDAD");
    }//GEN-LAST:event_btnCalidadMouseEntered

    private void btnCalidad1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCalidad1ActionPerformed
        Acabados c = new Acabados(lblId.getText());
        jDesktopPane1.add(c);
        c.toFront();
        c.setLocation(jDesktopPane1.getWidth() / 2 - c.getWidth() / 2, jDesktopPane1.getHeight() / 2 - c.getHeight() / 2);
        try {
            c.setMaximum(true);
        } catch (PropertyVetoException e) {
            Logger.getLogger(Inicio1.class.getName()).log(Level.SEVERE, null, e);
        }
        c.setVisible(true);
    }//GEN-LAST:event_btnCalidad1ActionPerformed

    private void btnCalidad1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCalidad1MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_btnCalidad1MouseExited

    private void btnCalidad1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCalidad1MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_btnCalidad1MouseEntered

    private void btnElecActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnElecActionPerformed
        MaquinadosNew c = new MaquinadosNew(lblId.getText());
        jDesktopPane1.add(c);
        c.toFront();
        c.setLocation(jDesktopPane1.getWidth() / 2 - c.getWidth() / 2, jDesktopPane1.getHeight() / 2 - c.getHeight() / 2);
        try {
            c.setMaximum(true);
        } catch (PropertyVetoException e) {
            Logger.getLogger(Inicio1.class.getName()).log(Level.SEVERE, null, e);
        }
        c.setVisible(true);
    }//GEN-LAST:event_btnElecActionPerformed

    private void btnElecMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnElecMouseExited
        //        btnElec.setText("");
    }//GEN-LAST:event_btnElecMouseExited

    private void btnElecMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnElecMouseEntered
        //        btnElec.setText("ELECT..");
    }//GEN-LAST:event_btnElecMouseEntered

    private void btnCorteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCorteActionPerformed
        Cortes c = new Cortes(lblId.getText());
        jDesktopPane1.add(c);
        c.toFront();
        c.setLocation(jDesktopPane1.getWidth() / 2 - c.getWidth() / 2, jDesktopPane1.getHeight() / 2 - c.getHeight() / 2);
        try {
            c.setMaximum(true);
        } catch (PropertyVetoException e) {
            Logger.getLogger(Inicio1.class.getName()).log(Level.SEVERE, null, e);
        }
        c.setVisible(true);
    }//GEN-LAST:event_btnCorteActionPerformed

    private void btnCorteMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCorteMouseExited
        //        btnCorte.setText("");
    }//GEN-LAST:event_btnCorteMouseExited

    private void btnCorteMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCorteMouseEntered
        //        btnCorte.setText("CORTES");
    }//GEN-LAST:event_btnCorteMouseEntered

    private void btnIntegracion1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIntegracion1ActionPerformed
        Live c = new Live(lblId.getText());
        jDesktopPane1.add(c);
        c.toFront();
        c.setLocation(jDesktopPane1.getWidth() / 2 - c.getWidth() / 2, jDesktopPane1.getHeight() / 2 - c.getHeight() / 2);
        try {
            c.setMaximum(true);
        } catch (PropertyVetoException e) {
            Logger.getLogger(Inicio1.class.getName()).log(Level.SEVERE, null, e);
        }
        c.setVisible(true);
    }//GEN-LAST:event_btnIntegracion1ActionPerformed

    private void btnIntegracion1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnIntegracion1MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_btnIntegracion1MouseExited

    private void btnIntegracion1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnIntegracion1MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_btnIntegracion1MouseEntered

    private void btnHtppActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHtppActionPerformed
        HTPP c = new HTPP(lblId.getText());
        jDesktopPane1.add(c);
        c.toFront();
        c.setLocation(jDesktopPane1.getWidth() / 2 - c.getWidth() / 2, jDesktopPane1.getHeight() / 2 - c.getHeight() / 2);
        try {
            c.setMaximum(true);
        } catch (PropertyVetoException e) {
            Logger.getLogger(Inicio1.class.getName()).log(Level.SEVERE, null, e);
        }
        //        c.insertarSemanas();
        c.setVisible(true);
        //        c.verDatos();
    }//GEN-LAST:event_btnHtppActionPerformed

    private void btnHtppMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnHtppMouseExited
        //        btnHtpp.setText("");
    }//GEN-LAST:event_btnHtppMouseExited

    private void btnHtppMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnHtppMouseEntered
        //        btnHtpp.setText("HTPP");
    }//GEN-LAST:event_btnHtppMouseEntered

    private void btnChecadorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChecadorActionPerformed
        Checador c = new Checador(lblId.getText());
        jDesktopPane1.add(c);
        c.toFront();
        c.setLocation(jDesktopPane1.getWidth() / 2 - c.getWidth() / 2, jDesktopPane1.getHeight() / 2 - c.getHeight() / 2);
        try {
            c.setMaximum(true);
        } catch (PropertyVetoException e) {
            Logger.getLogger(Inicio1.class.getName()).log(Level.SEVERE, null, e);
        }
        c.setVisible(true);
    }//GEN-LAST:event_btnChecadorActionPerformed

    private void btnChecadorMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnChecadorMouseExited
        //        btnChecador.setText("");
    }//GEN-LAST:event_btnChecadorMouseExited

    private void btnChecadorMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnChecadorMouseEntered
        //        btnChecador.setText("Checador");
    }//GEN-LAST:event_btnChecadorMouseEntered

    private void btnEstado5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEstado5ActionPerformed
        ProyectManager c = new ProyectManager(lblId.getText());
        jDesktopPane1.add(c);
        c.toFront();
        c.setLocation(jDesktopPane1.getWidth() / 2 - c.getWidth() / 2, jDesktopPane1.getHeight() / 2 - c.getHeight() / 2);
        try {
            c.setMaximum(true);
        } catch (PropertyVetoException e) {
            Logger.getLogger(Inicio1.class.getName()).log(Level.SEVERE, null, e);
        }
        c.setVisible(true);
    }//GEN-LAST:event_btnEstado5ActionPerformed

    private void btnEstado5MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEstado5MouseExited
        //        btnEstado5.setText("");
    }//GEN-LAST:event_btnEstado5MouseExited

    private void btnEstado5MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEstado5MouseEntered
        //        btnEstado5.setText("PROYECTOS");
    }//GEN-LAST:event_btnEstado5MouseEntered

    private void btnEstado3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEstado3ActionPerformed
        Ventas c = new Ventas();
        jDesktopPane1.add(c);
        c.toFront();
        c.setLocation(jDesktopPane1.getWidth() / 2 - c.getWidth() / 2, jDesktopPane1.getHeight() / 2 - c.getHeight() / 2);
        try {
            c.setMaximum(true);
        } catch (PropertyVetoException e) {
            Logger.getLogger(Inicio1.class.getName()).log(Level.SEVERE, null, e);
        }
        c.setVisible(true);
    }//GEN-LAST:event_btnEstado3ActionPerformed

    private void btnEstado3MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEstado3MouseExited
        //        btnEstado3.setText("");
    }//GEN-LAST:event_btnEstado3MouseExited

    private void btnEstado3MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEstado3MouseEntered
        //        btnEstado3.setText("VENTAS");
    }//GEN-LAST:event_btnEstado3MouseEntered

    private void btnCotizacionVentasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCotizacionVentasActionPerformed
        InicioCotizacion c = new InicioCotizacion(lblId.getText(), this);
        jDesktopPane1.add(c);
        c.toFront();
        c.setLocation(jDesktopPane1.getWidth() / 2 - c.getWidth() / 2, jDesktopPane1.getHeight() / 2 - c.getHeight() / 2);
        try {
            c.setMaximum(true);
        } catch (PropertyVetoException e) {
            Logger.getLogger(Inicio1.class.getName()).log(Level.SEVERE, null, e);
        }
        c.setVisible(true);
    }//GEN-LAST:event_btnCotizacionVentasActionPerformed

    private void btnCotizacionVentasMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCotizacionVentasMouseExited
        //        btnCotizacionVentas.setText("");
    }//GEN-LAST:event_btnCotizacionVentasMouseExited

    private void btnCotizacionVentasMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCotizacionVentasMouseEntered
        //        btnCotizacionVentas.setText("COTIZACION");
    }//GEN-LAST:event_btnCotizacionVentasMouseEntered

    private void btnEstado1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEstado1ActionPerformed
        Reportes c = new Reportes(lblId.getText());
        jDesktopPane1.add(c);
        c.toFront();
        c.setLocation(jDesktopPane1.getWidth() / 2 - c.getWidth() / 2, jDesktopPane1.getHeight() / 2 - c.getHeight() / 2);
        try {
            c.setMaximum(true);
        } catch (PropertyVetoException e) {
            Logger.getLogger(Inicio1.class.getName()).log(Level.SEVERE, null, e);
        }
        c.setVisible(true);
    }//GEN-LAST:event_btnEstado1ActionPerformed

    private void btnEstado1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEstado1MouseExited
        //        btnEstado1.setText("");
    }//GEN-LAST:event_btnEstado1MouseExited

    private void btnEstado1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEstado1MouseEntered
        //        btnEstado1.setText("REPORTES");
    }//GEN-LAST:event_btnEstado1MouseEntered

    private void btnEstado2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEstado2ActionPerformed
        CargaTrabajo c = new CargaTrabajo(this);
        jDesktopPane1.add(c);
        c.toFront();
        c.setLocation(jDesktopPane1.getWidth() / 2 - c.getWidth() / 2, jDesktopPane1.getHeight() / 2 - c.getHeight() / 2);
        try {
            c.setMaximum(true);
        } catch (PropertyVetoException e) {
            Logger.getLogger(Inicio1.class.getName()).log(Level.SEVERE, null, e);
        }
        c.setVisible(true);
    }//GEN-LAST:event_btnEstado2ActionPerformed

    private void btnEstado2MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEstado2MouseExited
        //        btnEstado2.setText("");
    }//GEN-LAST:event_btnEstado2MouseExited

    private void btnEstado2MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEstado2MouseEntered
        //        btnEstado2.setText("CARGA");
    }//GEN-LAST:event_btnEstado2MouseEntered

    private void btnEstadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEstadoActionPerformed
        CambiarEstado VistaEXe = new CambiarEstado(lblId.getText());
        jDesktopPane1.add(VistaEXe);
        VistaEXe.toFront();
        VistaEXe.setLocation(jDesktopPane1.getWidth() / 2 - VistaEXe.getWidth() / 2, jDesktopPane1.getHeight() / 2 - VistaEXe.getHeight() / 2);
        try {
            VistaEXe.setMaximum(true);
        } catch (PropertyVetoException e) {
            Logger.getLogger(Inicio1.class.getName()).log(Level.SEVERE, null, e);
        }
        VistaEXe.setVisible(true);
    }//GEN-LAST:event_btnEstadoActionPerformed

    private void btnEstadoMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEstadoMouseExited
        //        btnEstado.setText("");
    }//GEN-LAST:event_btnEstadoMouseExited

    private void btnEstadoMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEstadoMouseEntered
        //        btnEstado.setText("ESTADOS");
    }//GEN-LAST:event_btnEstadoMouseEntered

    private void btnDisenioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDisenioActionPerformed
        InicioDiseño c = new InicioDiseño(this);
        jDesktopPane1.add(c);
        c.toFront();
        c.setLocation(jDesktopPane1.getWidth() / 2 - c.getWidth() / 2, jDesktopPane1.getHeight() / 2 - c.getHeight() / 2);
        try {
            c.setMaximum(true);
        } catch (PropertyVetoException e) {
            Logger.getLogger(Inicio1.class.getName()).log(Level.SEVERE, null, e);
        }
        c.setVisible(true);
    }//GEN-LAST:event_btnDisenioActionPerformed

    private void btnDisenioMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnDisenioMouseExited
        //        btnDisenio.setText("");
    }//GEN-LAST:event_btnDisenioMouseExited

    private void btnDisenioMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnDisenioMouseEntered
        //        btnDisenio.setText("DISEÑO");
    }//GEN-LAST:event_btnDisenioMouseEntered

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new InicioSesion().setVisible(true);
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
                    Logger.getLogger(InicioSesion.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton btnCalendario;
    public javax.swing.JButton btnCalidad;
    public javax.swing.JButton btnCalidad1;
    public javax.swing.JButton btnChecador;
    public javax.swing.JButton btnCorte;
    public javax.swing.JButton btnCostos;
    public javax.swing.JButton btnCotizacion;
    public javax.swing.JButton btnCotizacionVentas;
    public javax.swing.JButton btnDisenio;
    public javax.swing.JButton btnElec;
    public javax.swing.JButton btnEmpleado;
    public javax.swing.JButton btnEntrega;
    public javax.swing.JButton btnEstado;
    public javax.swing.JButton btnEstado1;
    public javax.swing.JButton btnEstado2;
    public javax.swing.JButton btnEstado3;
    public javax.swing.JButton btnEstado5;
    public javax.swing.JButton btnHtpp;
    public javax.swing.JButton btnIntegracion;
    public javax.swing.JButton btnIntegracion1;
    public javax.swing.JButton btnInventario;
    public javax.swing.JButton btnInventario1;
    public javax.swing.JButton btnInventario2;
    private javax.swing.JButton btnModificar1;
    public javax.swing.JButton btnOrden;
    public javax.swing.JButton btnOrden1;
    public javax.swing.JButton btnPedidos;
    public javax.swing.JButton btnPrestamos;
    public javax.swing.JButton btnRH;
    public javax.swing.JButton btnRecibos;
    public javax.swing.JButton btnRegistro;
    public javax.swing.JButton btnRemisiones;
    public javax.swing.JButton btnRequisicion;
    public javax.swing.JButton btnTrata;
    public javax.swing.JButton btnVer;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    public javax.swing.JDesktopPane jDesktopPane1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel37;
    private javax.swing.JPanel jPanel39;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel40;
    private javax.swing.JPanel jPanel41;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblCont;
    public javax.swing.JLabel lblId;
    private javax.swing.JLabel lblId1;
    private javax.swing.JLabel lblId2;
    public javax.swing.JLabel lblNombre;
    private javax.swing.JLabel lblNotiCostos;
    private javax.swing.JLabel lblNotiRequis;
    private javax.swing.JMenuItem miReporte;
    private javax.swing.JPanel panel11;
    private javax.swing.JPanel panel12;
    private javax.swing.JPanel panel13;
    private javax.swing.JPanel panel14;
    private javax.swing.JPanel panel15;
    private javax.swing.JPanel panel16;
    private javax.swing.JPanel panel17;
    private javax.swing.JPanel panel21;
    private javax.swing.JPanel panel26;
    private javax.swing.JPanel panel27;
    private javax.swing.JPanel panel28;
    private javax.swing.JPanel panel31;
    private javax.swing.JPanel panel32;
    private javax.swing.JPanel panel33;
    private javax.swing.JPanel panel34;
    private javax.swing.JPanel panel35;
    private javax.swing.JPanel panel36;
    private javax.swing.JPanel panel37;
    private javax.swing.JPanel panel38;
    private javax.swing.JPanel panel41;
    private javax.swing.JPanel panel42;
    private javax.swing.JPanel panel43;
    private javax.swing.JPanel panel44;
    private javax.swing.JPanel panel45;
    private javax.swing.JPanel panel46;
    private javax.swing.JPanel panel47;
    private javax.swing.JPanel panel48;
    private javax.swing.JPanel panel49;
    public scrollPane.PanelRound panelPedidos;
    private rojeru_san.rspanel.RSPanelRound rSPanelRound1;
    private rojeru_san.rspanel.RSPanelRound rSPanelRound14;
    private rojeru_san.rspanel.RSPanelRound rSPanelRound15;
    private rojeru_san.rspanel.RSPanelRound rSPanelRound16;
    private rojeru_san.rspanel.RSPanelRound rSPanelRound17;
    private rojeru_san.rspanel.RSPanelRound rSPanelRound18;
    private rojeru_san.rspanel.RSPanelRound rSPanelRound19;
    private rojeru_san.rspanel.RSPanelRound rSPanelRound20;
    private rojeru_san.rspanel.RSPanelRound rSPanelRound21;
    private rojeru_san.rspanel.RSPanelRound rSPanelRound22;
    private rojeru_san.rspanel.RSPanelRound rSPanelRound23;
    private rojeru_san.rspanel.RSPanelRound rSPanelRound24;
    private rojeru_san.rspanel.RSPanelRound rSPanelRound25;
    private rojeru_san.rspanel.RSPanelRound rSPanelRound26;
    private rojeru_san.rspanel.RSPanelRound rSPanelRound27;
    private rojeru_san.rspanel.RSPanelRound rSPanelRound28;
    private rojeru_san.rspanel.RSPanelRound rSPanelRound29;
    private rojeru_san.rspanel.RSPanelRound rSPanelRound3;
    private rojeru_san.rspanel.RSPanelRound rSPanelRound30;
    private rojeru_san.rspanel.RSPanelRound rSPanelRound31;
    private rojeru_san.rspanel.RSPanelRound rSPanelRound32;
    private rojeru_san.rspanel.RSPanelRound rSPanelRound33;
    private rojeru_san.rspanel.RSPanelRound rSPanelRound34;
    private rojeru_san.rspanel.RSPanelRound rSPanelRound35;
    private rojeru_san.rspanel.RSPanelRound rSPanelRound36;
    private rojeru_san.rspanel.RSPanelRound rSPanelRound37;
    private rojeru_san.rspanel.RSPanelRound rSPanelRound38;
    private rojeru_san.rspanel.RSPanelRound rSPanelRound39;
    private rojeru_san.rspanel.RSPanelRound rSPanelRound4;
    private rojeru_san.rspanel.RSPanelRound rSPanelRound5;
    private rojeru_san.rspanel.RSPanelRound rSPanelRound6;
    private rojeru_san.rspanel.RSPanelRound rSPanelRound7;
    private rojeru_san.rspanel.RSPanelRound rSPanelRound8;
    private rojeru_san.rspanel.RSPanelRound rSPanelRound9;
    // End of variables declaration//GEN-END:variables

    @Override
    public void update(Observable o, Object arg) {
        String mensaje = (String) arg;
        verNotificaciones();
//        Notifi n = new Notifi(mensaje,"1",this);
        try {
            Clip clip;
            clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(getClass().getResourceAsStream("/Gif/campana.wav")));
            clip.start();
        } catch (IOException | LineUnavailableException | UnsupportedAudioFileException e) {
            System.out.println("NO SE EJECUTO EL SISTEMA DE AUDIO: " + e);
        }
        verNotificaciones();
        verNotificaciones();
        verNotificaciones();
        verNotificaciones();
        verNotificaciones();
        actualizar();
        revalidate();
        repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (inicioAlmacen != null) {
            if (e.getSource() == inicioAlmacen.btnInventario) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        InventarioNew c = new InventarioNew(lblNombre.getText());
                        jDesktopPane1.add(c);
                        c.toFront();
                        c.setLocation(jDesktopPane1.getWidth() / 2 - c.getWidth() / 2, jDesktopPane1.getHeight() / 2 - c.getHeight() / 2);
                        try {
                            c.setMaximum(true);
                        } catch (PropertyVetoException e) {
                            Logger.getLogger(Inicio1.class.getName()).log(Level.SEVERE, null, e);
                        }
                        inicioAlmacen.dispose();
                        c.setVisible(true);
                    }
                });
            } else if (e.getSource() == inicioAlmacen.btnRevisar) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        Almacen c = new Almacen(lblNombre.getText());
                        jDesktopPane1.add(c);
                        c.toFront();
                        c.setLocation(jDesktopPane1.getWidth() / 2 - c.getWidth() / 2, jDesktopPane1.getHeight() / 2 - c.getHeight() / 2);
                        try {
                            c.setMaximum(true);
                        } catch (PropertyVetoException e) {
                            Logger.getLogger(Inicio1.class.getName()).log(Level.SEVERE, null, e);
                        }
                        inicioAlmacen.dispose();
                        c.setVisible(true);
                    }
                });
            }
        }
        if (inicioCostos != null) {
            if (e.getSource() == inicioCostos.btnCostos) {
                inicioCostos.dispose();
                Costos c = new Costos(lblId.getText());
                inicioCostos(c);
            } else if (e.getSource() == inicioCostos.btnEvaluacion) {
                inicioCostos.dispose();
                evaluacion = new Evaluacion(lblId.getText());
                inicioCostos(evaluacion);
            } else if (e.getSource() == inicioCostos.btnCosteo) {
                inicioCostos.dispose();
                Costeo c = new Costeo(lblId.getText());
                inicioCostos(c);
            }
        }

        if (evaluacion != null) {
            if (e.getSource() == evaluacion.btnAceptar || e.getSource() == evaluacion.btnRechazar) {
                notiCostos = getCostos(lblId.getText(), COSTOS);
                if (notiCostos == 0) {
                    lblNotiCostos.setVisible(false);
                    this.revalidate();
                    this.repaint();
                } else {
                    System.err.println("se activa uno de los 2 botones");
                    lblNotiCostos.setText(String.valueOf(notiCostos));
                    lblNotiCostos.setVisible(true);
                    this.revalidate();
                    this.repaint();
                }
            }
        }
    }
}
