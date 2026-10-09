package pruebas;

import Conexiones.Conexion;
import Conexiones.ConexionChat;
import VentanaEmergente.Inicio1.Espera;
import VentanaEmergente.InicioSesion.Informacion;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import java.awt.Color;
import java.awt.Component;
import static java.lang.Thread.sleep;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class InicioSesion extends javax.swing.JFrame {

    int x, y;
    Espera espera = new Espera();
//    boolean avanzar;
//    public int ERROR_AL_CONECTARSE = 1;
    boolean avanzar = true;
    public int ERROR_AL_CONECTARSE = 0;

    public void Acceso() throws ClassNotFoundException {
        if (ERROR_AL_CONECTARSE == 1) {
            JOptionPane.showMessageDialog(this, "Error al conectarse a la base de datos", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            if (avanzar) {
                try {
                    espera.activar();

                    espera.setSize(870, 477);
                    espera.setLocationRelativeTo(null);
                    espera.setVisible(true);
                    Connection con;
                    Conexion con1 = new Conexion();
                    con = con1.getConnection();
                    Statement st = con.createStatement();
                    String datos[] = new String[50];
                    String sql = "select AES_DECRYPT(Contraseña,'mi_llave'),NumEmpleado,Nombre,Diseño,Cambio,Reportes,Carga,Ventas,Cortes,Fresa,"
                            + "Cnc,Torno,Acabados,Calidad,Tratamiento,Electrico,CrearEmpleado,VerEmpleado,Inventario,Ensamble,InventarioPlanos,"
                            + "Requisiciones,Orden,Aprobacion,Recibo,Prestamo,Cotizacion,VerRequisiciones,Cotizar,Apellido,ProyectMan, Remisiones,"
                            + " Checador, Puesto, RH from registroEmpleados where NumEmpleado like '" + Usuario.getText() + "'";
                    ResultSet rs = st.executeQuery(sql);
                    String puesto = "";
                    while (rs.next()) {
                        datos[0] = rs.getString("NumEmpleado");
                        datos[1] = rs.getString("Nombre");
                        datos[2] = rs.getString("Diseño");
                        datos[3] = rs.getString("Cambio");
                        datos[4] = rs.getString("Reportes");
                        datos[5] = rs.getString("Carga");
                        datos[6] = rs.getString("Ventas");
                        datos[7] = rs.getString("AES_DECRYPT(Contraseña,'mi_llave')");
                        datos[8] = rs.getString("Cortes");
                        datos[9] = rs.getString("Fresa");
                        datos[10] = rs.getString("Cnc");
                        datos[11] = rs.getString("Torno");
                        datos[12] = rs.getString("Acabados");
                        datos[13] = rs.getString("Calidad");
                        datos[14] = rs.getString("Tratamiento");
                        datos[15] = rs.getString("Electrico");
                        datos[16] = rs.getString("CrearEmpleado");
                        datos[17] = rs.getString("VerEmpleado");
                        datos[18] = rs.getString("Inventario");
                        datos[19] = rs.getString("Ensamble");
                        datos[20] = rs.getString("InventarioPlanos");
                        datos[21] = rs.getString("Requisiciones");
                        datos[22] = rs.getString("Orden");
                        datos[23] = rs.getString("Aprobacion");
                        datos[24] = rs.getString("Recibo");
                        datos[25] = rs.getString("Prestamo");
                        datos[26] = rs.getString("Cotizacion");
                        datos[27] = rs.getString("VerRequisiciones");
                        datos[28] = rs.getString("Cotizar");
                        datos[29] = rs.getString("Apellido");
                        datos[30] = rs.getString("ProyectMan");
                        datos[31] = rs.getString("Remisiones");
                        datos[32] = rs.getString("Checador");
                        datos[33] = rs.getString("RH");
                        puesto = rs.getString("Puesto");
                    }
                    System.out.println(datos[7]);
                    if (Usuario.getText().equals(datos[0]) && Contra.getText().equals(datos[7])) {
                        String dir = "";
                        try {
                            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
                            while (interfaces.hasMoreElements()) {
                                NetworkInterface interfaz = interfaces.nextElement();
                                Enumeration<InetAddress> direcciones = interfaz.getInetAddresses();
                                while (direcciones.hasMoreElements()) {
                                    InetAddress direccion = direcciones.nextElement();
                                    if (direccion instanceof Inet4Address
                                            && !direccion.isLoopbackAddress()) {

                                        dir = direccion.toString();
                                    }
                                }
                            }
                        } catch (Exception e) {
                            JOptionPane.showMessageDialog(this, "ERROR: " + e, "ERROR", JOptionPane.ERROR_MESSAGE);
                            espera.dispose();
                        }

                        espera.band = false;
                        espera.dispose();
                        Inicio1 i = new Inicio1(datos[0], datos[1] + " " + datos[29], puesto);

                        try {
                            i.iniciarServidor();
                        } catch (Exception e) {
                            espera.dispose();
                        }
//                        String sql4 = "select * from actualizacion";
//                        Connection con2 = null;
//                        ConexionChat c = new ConexionChat();
//                        con2 = c.getConnection();
//                        Statement st4 = con2.createStatement();
//                        ResultSet rs4 = st4.executeQuery(sql4);
//                        String ver = "";
//                        while (rs4.next()) {
//                            ver = rs4.getString("Version");
//                        }

                        i.setVisible(true);
                        //       Inicio.lblId.setText(datos[0]);
                        //       Inicio.lblNombre.setText(datos[1]+" "+datos[8]);
                        Guardar g = new Guardar(datos[1] + " " + datos[29], datos[0]);
                        g.nombre = datos[1] + " " + datos[29];
                        g.usuario = datos[0];

                        if (datos[2].equals("1")) {
                            i.btnDisenio.setEnabled(true);
                        } else {
                            i.btnDisenio.setEnabled(false);
                        }

                        if (datos[3].equals("1")) {
                            i.btnEstado.setEnabled(true);
                        } else {
                            i.btnEstado.setEnabled(false);
                        }

                        if (datos[4].equals("1")) {
                            i.btnEstado1.setEnabled(true);
                        } else {
                            i.btnEstado1.setEnabled(false);
                        }

                        if (datos[5].equals("1")) {
                            i.btnEstado2.setEnabled(true);
                        } else {
                            i.btnEstado2.setEnabled(false);
                        }

                        if (datos[6].equals("1")) {
                            i.btnEstado3.setEnabled(true);
                        } else {
                            i.btnEstado3.setEnabled(false);
                        }

                        if (datos[8].equals("1")) {
                            i.btnCorte.setEnabled(true);
                        } else {
                            i.btnCorte.setEnabled(false);
                        }

                        if (datos[13].equals("1")) {
                            i.btnCalidad.setEnabled(true);
                        } else {
                            i.btnCalidad.setEnabled(false);
                        }

                        if (datos[14].equals("1")) {
                            i.btnTrata.setEnabled(true);
                        } else {
                            i.btnTrata.setEnabled(false);
                        }

                        if (datos[15].equals("1")) {
                            i.btnElec.setEnabled(true);
                        } else {
                            i.btnElec.setEnabled(false);
                        }

                        if (datos[16].equals("1")) {
                            i.btnRegistro.setEnabled(true);
                        } else {
                            i.btnRegistro.setEnabled(false);
                        }

                        if (datos[17].equals("1")) {
                            i.btnEmpleado.setEnabled(true);
                        } else {
                            i.btnEmpleado.setEnabled(false);
                        }

                        if (datos[18].equals("1")) {
                            i.btnInventario.setEnabled(true);
                        } else {
                            i.btnInventario.setEnabled(false);
                        }

                        if (datos[19].equals("1")) {
                            i.btnInventario1.setEnabled(true);
                        } else {
                            i.btnInventario1.setEnabled(false);
                        }

                        if (datos[20].equals("1")) {
                            i.btnInventario2.setEnabled(true);
                        } else {
                            i.btnInventario2.setEnabled(false);
                        }

                        if (datos[21].equals("1")) {
                            i.btnRequisicion.setEnabled(true);
                        } else {
                            i.btnRequisicion.setEnabled(false);
                        }

                        if (datos[22].equals("1")) {
                            i.btnOrden.setEnabled(true);
                        } else {
                            i.btnOrden.setEnabled(false);
                        }

                        if (datos[23].equals("1")) {
                            i.btnOrden1.setEnabled(true);
                        } else {
                            i.btnOrden1.setEnabled(false);
                        }

                        if (datos[24].equals("1")) {
                            i.btnRecibos.setEnabled(true);
                        } else {
                            i.btnRecibos.setEnabled(false);
                        }

                        if (datos[25].equals("1")) {
                            i.btnPrestamos.setEnabled(true);
                        } else {
                            i.btnPrestamos.setEnabled(false);
                        }

                        if (datos[26].equals("1")) {
                            i.btnCotizacion.setEnabled(true);
                        } else {
                            i.btnCotizacion.setEnabled(false);
                        }

                        if (datos[27].equals("1")) {
                            i.btnVer.setEnabled(true);
                        } else {
                            i.btnVer.setEnabled(false);
                        }

                        if (datos[28].equals("1")) {
                            i.btnCotizacionVentas.setEnabled(true);
                        } else {
                            i.btnCotizacionVentas.setEnabled(false);
                        }

                        if (datos[30].equals("1")) {
                            i.btnEstado5.setEnabled(true);
                        } else {
                            i.btnEstado5.setEnabled(false);
                        }

                        if (datos[31].equals("1")) {
                            i.btnRemisiones.setEnabled(true);
                        } else {
                            i.btnRemisiones.setEnabled(false);
                        }

                        if (datos[32].equals("1")) {
                            i.btnChecador.setEnabled(true);
                        } else {
                            i.btnChecador.setEnabled(false);
                        }
                        
                        if (datos[33].equals("1")) {
                            i.btnRH.setEnabled(true);
                        } else {
                            i.btnRH.setEnabled(false);
                        }

                        dispose();
                    } else if (Usuario.getText().equals(datos[0]) && Contra.getText() != (datos[7])) {
                        espera.band = false;
                        espera.dispose();
                        JOptionPane.showMessageDialog(this, "CONTASEÑA INCORRECTA", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);

                    } else {
                        espera.band = false;
                        espera.dispose();
                        JOptionPane.showMessageDialog(this, "EL USUARIO NO EXISTE", "ERROR", JOptionPane.ERROR_MESSAGE);

                    }
                } catch (SQLException e) {
                    espera.band = false;
                    espera.dispose();
                    JOptionPane.showMessageDialog(this, "ERROR" + e, "", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "NO PUEDES AVANZAR HASTA ACTUALIZAR TOWI", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    public void intermitente() {
        Thread hilo = new Thread(() -> {
            try {
                while (true) {
                    sleep(500);
                    if (panelVersion.isVisible()) {
                        panelVersion.setVisible(false);
                    } else {
                        panelVersion.setVisible(true);
                    }
                }
            } catch (InterruptedException ex) {
                Logger.getLogger(InicioSesion.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        hilo.start();
    }

    public final void verificarVersion() {
        Thread hilo = new Thread(new Runnable() {
            public void run() {
                boolean band = false;
//                try {
//                    Connection con;
//                    ConexionChat con1 = new ConexionChat();
//                    con = con1.getConnection();
//                    Statement st = con.createStatement();
//                    String sql = "select * from actualizacion";
//                    ResultSet rs = st.executeQuery(sql);
//                    String version = "";
//                    while (rs.next()) {
//                        version = rs.getString("Version");
//                        avanzar = rs.getBoolean("Avanzar");
//                    }
//                    if (version.equals(lblVersion.getText())) {
//                        band = true;
//                        avanzar = true;
//                    }
//                    ERROR_AL_CONECTARSE = 0;
//                } catch (SQLException e) {
//                    JOptionPane.showMessageDialog(null, "Error al conectarse a la base de datos", "Error", JOptionPane.ERROR_MESSAGE);
//                    ERROR_AL_CONECTARSE = 1;
//                } catch (ClassNotFoundException ex) {
//                    JOptionPane.showMessageDialog(null, "Error al conectarse a la base de datos", "Error", JOptionPane.ERROR_MESSAGE);
//                    ERROR_AL_CONECTARSE = 1;
//                    Logger.getLogger(InicioSesion.class.getName()).log(Level.SEVERE, null, ex);
//                }
                if (ERROR_AL_CONECTARSE != 1) {
                    if (band == false) {
                        panelVersion.setVisible(true);
                        panelVersion.setBackground(new Color(0, 0, 0, 0));
                        intermitente();
                    } else {
                        panelVersion.setVisible(false);
                    }
                }
            }
        });
        hilo.start();
    }

    public void animarArriba(Component cb, Component cm) {
        try {
            Thread hilo = new Thread(() -> {
                for (int i = cb.getBounds().y; i > cb.getBounds().y - 20; i--) {
                    int ax = i;
                    SwingUtilities.invokeLater(() -> {
                        panelP.add(cm, new org.netbeans.lib.awtextra.AbsoluteConstraints(cm.getBounds().x, ax, 70, 20));
                        panelP.revalidate();
                        panelP.repaint();
                    });
                    try {
                        sleep(7);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(InicioSesion.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    revalidate();
                    repaint();
                }
            });
            hilo.start();
        } catch (Exception e) {
            System.out.println("error: " + e);
        }
    }

    public void animarAbajo(Component cb, Component cm) {
        try {
            int mitad = cb.getHeight() / 3;
            Thread thread = new Thread(() -> {
                for (int i = cb.getBounds().y - 20; i < cb.getBounds().y + mitad; i++) {
                    int ax = i;
                    SwingUtilities.invokeLater(() -> {
                        panelP.add(cm, new org.netbeans.lib.awtextra.AbsoluteConstraints(cm.getBounds().x, ax, 70, 20));
                        panelP.revalidate();
                        panelP.repaint();
                    });
                    try {
                        sleep(7);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(InicioSesion.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
            thread.start();
        } catch (Exception e) {
            System.out.println("error: " + e);
        }
    }

    public InicioSesion() {
        initComponents();
        this.setTitle("SERVICIOS INDUSTRIALES 3i");
        this.setIconImage(new ImageIcon(getClass().getResource("/Img/Icono Towi.png")).getImage());
        this.setBackground(new Color(0, 0, 0, 0));
        Usuario.setBackground(new Color(0, 0, 0, 0));
        Contra.setBackground(new Color(0, 0, 0, 0));
        panelVersion.setBackground(new Color(0, 0, 0, 0));
        try {
            verificarVersion();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al conectarse a al base de datos");
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelP = new scrollPane.PanelRound();
        lblVersion1 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        lblVersion = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        panelVersion = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        btnEntrar = new javax.swing.JPanel();
        lblEntrar = new javax.swing.JLabel();
        panelM = new scrollPane.PanelRound();
        btnSalir = new javax.swing.JPanel();
        lblSalir = new javax.swing.JLabel();
        lblUsuario = new javax.swing.JLabel();
        Usuario = new javax.swing.JTextField();
        lblContrasena = new javax.swing.JLabel();
        Contra = new javax.swing.JPasswordField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panelP.setBackground(new java.awt.Color(255, 255, 255));
        panelP.setRoundBottomLeft(50);
        panelP.setRoundBottomRight(150);
        panelP.setRoundTopLeft(50);
        panelP.setRoundTopRight(500);
        panelP.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblVersion1.setBackground(new java.awt.Color(204, 204, 204));
        lblVersion1.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        lblVersion1.setForeground(new java.awt.Color(204, 204, 204));
        lblVersion1.setText("V");
        panelP.add(lblVersion1, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 420, -1, -1));

        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/karva-logo.png"))); // NOI18N
        panelP.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 30, 170, 70));

        lblVersion.setBackground(new java.awt.Color(204, 204, 204));
        lblVersion.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        lblVersion.setForeground(new java.awt.Color(204, 204, 204));
        lblVersion.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblVersion.setText("2.5.6.1");
        lblVersion.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblVersion.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblVersionMouseClicked(evt);
            }
        });
        panelP.add(lblVersion, new org.netbeans.lib.awtextra.AbsoluteConstraints(797, 420, 50, -1));

        jLabel6.setFont(new java.awt.Font("Roboto Medium", 0, 30)); // NOI18N
        jLabel6.setText("Inicio de sesion");
        panelP.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 70, 290, 40));

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Img/syms-solo.png"))); // NOI18N
        panelP.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 0, 300, 450));

        panelVersion.setBackground(new java.awt.Color(255, 255, 255));
        panelVersion.setBorder(javax.swing.BorderFactory.createMatteBorder(4, 4, 4, 4, new java.awt.Color(102, 0, 0)));
        panelVersion.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        panelVersion.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panelVersionMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout panelVersionLayout = new javax.swing.GroupLayout(panelVersion);
        panelVersion.setLayout(panelVersionLayout);
        panelVersionLayout.setHorizontalGroup(
            panelVersionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 62, Short.MAX_VALUE)
        );
        panelVersionLayout.setVerticalGroup(
            panelVersionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 22, Short.MAX_VALUE)
        );

        panelP.add(panelVersion, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 410, 70, 30));

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/fondo_1.png"))); // NOI18N
        panelP.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 0, 300, 450));

        btnEntrar.setBackground(new java.awt.Color(0, 165, 252));
        btnEntrar.setLayout(new java.awt.BorderLayout());

        lblEntrar.setBackground(new java.awt.Color(0, 124, 249));
        lblEntrar.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        lblEntrar.setForeground(new java.awt.Color(255, 255, 255));
        lblEntrar.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblEntrar.setText("ENTRAR");
        lblEntrar.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        lblEntrar.setNextFocusableComponent(Usuario);
        lblEntrar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblEntrarMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblEntrarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblEntrarMouseExited(evt);
            }
        });
        btnEntrar.add(lblEntrar, java.awt.BorderLayout.CENTER);

        panelP.add(btnEntrar, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 320, 490, 30));

        panelM.setBackground(new java.awt.Color(255, 255, 255));
        panelM.setRoundBottomLeft(600);
        panelM.setRoundBottomRight(800);
        panelM.setRoundTopLeft(700);
        panelM.setRoundTopRight(600);
        panelM.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                panelMMouseDragged(evt);
            }
        });
        panelM.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                panelMMousePressed(evt);
            }
        });

        btnSalir.setBackground(new java.awt.Color(255, 255, 255));
        btnSalir.setForeground(new java.awt.Color(255, 255, 255));
        btnSalir.setLayout(new java.awt.BorderLayout());

        lblSalir.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        lblSalir.setForeground(new java.awt.Color(0, 0, 0));
        lblSalir.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblSalir.setText("X");
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
        btnSalir.add(lblSalir, java.awt.BorderLayout.CENTER);

        javax.swing.GroupLayout panelMLayout = new javax.swing.GroupLayout(panelM);
        panelM.setLayout(panelMLayout);
        panelMLayout.setHorizontalGroup(
            panelMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelMLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(btnSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(769, Short.MAX_VALUE))
        );
        panelMLayout.setVerticalGroup(
            panelMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelMLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnSalir, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
                .addContainerGap())
        );

        panelP.add(panelM, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 820, 40));

        lblUsuario.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        lblUsuario.setForeground(new java.awt.Color(0, 102, 204));
        lblUsuario.setText("Usuario:");
        panelP.add(lblUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 200, 70, 20));

        Usuario.setBackground(new java.awt.Color(255, 255, 255));
        Usuario.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        Usuario.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(204, 204, 204)));
        Usuario.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                UsuarioFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                UsuarioFocusLost(evt);
            }
        });
        Usuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UsuarioActionPerformed(evt);
            }
        });
        Usuario.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                UsuarioKeyTyped(evt);
            }
        });
        panelP.add(Usuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 180, 490, 40));

        lblContrasena.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        lblContrasena.setForeground(new java.awt.Color(0, 102, 204));
        lblContrasena.setText("Contraseña:");
        panelP.add(lblContrasena, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 270, 70, 20));

        Contra.setBackground(new java.awt.Color(255, 255, 255));
        Contra.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(204, 204, 204)));
        Contra.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                ContraFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                ContraFocusLost(evt);
            }
        });
        Contra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ContraActionPerformed(evt);
            }
        });
        Contra.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                ContraKeyTyped(evt);
            }
        });
        panelP.add(Contra, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 250, 490, 40));

        getContentPane().add(panelP, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 870, 450));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void lblSalirMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSalirMouseClicked
        System.exit(0);
    }//GEN-LAST:event_lblSalirMouseClicked

    private void lblSalirMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSalirMouseEntered
        btnSalir.setBackground(Color.red);
        lblSalir.setForeground(Color.white);
    }//GEN-LAST:event_lblSalirMouseEntered

    private void lblSalirMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSalirMouseExited
        btnSalir.setBackground(Color.white);
        lblSalir.setForeground(Color.black);
    }//GEN-LAST:event_lblSalirMouseExited

    private void lblEntrarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblEntrarMouseEntered
        Color alto = new Color(0, 124, 249);
        btnEntrar.setBackground(alto);
    }//GEN-LAST:event_lblEntrarMouseEntered

    private void lblEntrarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblEntrarMouseExited
        Color bajo = new Color(0, 165, 252);
        btnEntrar.setBackground(bajo);
    }//GEN-LAST:event_lblEntrarMouseExited

    private void lblEntrarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblEntrarMouseClicked
        try {
            Acceso();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(InicioSesion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_lblEntrarMouseClicked

    private void panelMMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelMMousePressed
        x = evt.getX();
        y = evt.getY();
    }//GEN-LAST:event_panelMMousePressed

    private void panelMMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelMMouseDragged
        int xx = evt.getXOnScreen();
        int yy = evt.getYOnScreen();
        this.setLocation(xx - (x), yy - y);
    }//GEN-LAST:event_panelMMouseDragged

    private void lblVersionMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblVersionMouseClicked
        if (ERROR_AL_CONECTARSE != 1) {
            int opc = JOptionPane.showConfirmDialog(this, "Towi se cerrara completamente, ¿Esta de acuerdo?");
            if (opc == 0) {
                try {
                    String jarPath = "C:\\Pruebas\\BD\\Actualizar\\Descarga.jar";

                    // Ejecutar el JAR usando Runtime
                    Runtime.getRuntime().exec("java -jar " + jarPath);
                    System.exit(0);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }//GEN-LAST:event_lblVersionMouseClicked

    private void panelVersionMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelVersionMouseClicked
        if (ERROR_AL_CONECTARSE != 1) {
            int opc = JOptionPane.showConfirmDialog(this, "Towi se cerrara completamente, ¿Esta de acuerdo?");
            if (opc == 0) {
                try {
                    String jarPath = "C:\\Pruebas\\BD\\Actualizar\\Descarga.jar";

                    // Ejecutar el JAR usando Runtime
                    Runtime.getRuntime().exec("java -jar " + jarPath);
                    System.exit(0);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }//GEN-LAST:event_panelVersionMouseClicked

    private void UsuarioFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_UsuarioFocusGained
        Usuario.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(0, 153, 255)));
        if (Usuario.getText().equals("")) {
            animarArriba(Usuario, lblUsuario);
        }
    }//GEN-LAST:event_UsuarioFocusGained

    private void UsuarioFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_UsuarioFocusLost
        Usuario.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(204, 204, 204)));
        if (Usuario.getText().equals("")) {
            animarAbajo(Usuario, lblUsuario);
        }
    }//GEN-LAST:event_UsuarioFocusLost

    private void UsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UsuarioActionPerformed
        try {
            Acceso();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(InicioSesion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_UsuarioActionPerformed

    private void ContraFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_ContraFocusGained
        Contra.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(0, 153, 255)));
        if (Contra.getText().equals("")) {
            animarArriba(Contra, lblContrasena);
        }
    }//GEN-LAST:event_ContraFocusGained

    private void ContraFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_ContraFocusLost
        Contra.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(204, 204, 204)));
        if (Contra.getText().equals("")) {
            animarAbajo(Contra, lblContrasena);
        }
    }//GEN-LAST:event_ContraFocusLost

    private void ContraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ContraActionPerformed
        try {
            Acceso();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(InicioSesion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_ContraActionPerformed

    private void UsuarioKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_UsuarioKeyTyped
        revalidate();
        repaint();
    }//GEN-LAST:event_UsuarioKeyTyped

    private void ContraKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ContraKeyTyped
        revalidate();
        repaint();
    }//GEN-LAST:event_ContraKeyTyped

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(InicioSesion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(InicioSesion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(InicioSesion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(InicioSesion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new InicioSesion().setVisible(false);
                InicioSesion i = new InicioSesion();
                i.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPasswordField Contra;
    private javax.swing.JTextField Usuario;
    private javax.swing.JPanel btnEntrar;
    private javax.swing.JPanel btnSalir;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel lblContrasena;
    private javax.swing.JLabel lblEntrar;
    private javax.swing.JLabel lblSalir;
    private javax.swing.JLabel lblUsuario;
    private javax.swing.JLabel lblVersion;
    private javax.swing.JLabel lblVersion1;
    private scrollPane.PanelRound panelM;
    private scrollPane.PanelRound panelP;
    private javax.swing.JPanel panelVersion;
    // End of variables declaration//GEN-END:variables
}
