package VentanaEmergente.Diseño;

import Conexiones.Conexion;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Image;
import java.beans.PropertyVetoException;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import pruebas.Disenio1;
import pruebas.Inicio1;
import toggle.ToggleListener;

public class InicioDiseño extends javax.swing.JInternalFrame {

    Inicio1 inicio;
    public Stack<TempleteDiseno> templetes;
    public boolean activar = false;
    public String numEmpleado;

    private final javax.swing.Timer timer = new javax.swing.Timer(100, e -> actualizarCronometros());
    private final long[] inicioCrono = new long[4];
    private final long[] acumulado = new long[4];
    private final boolean[] corriendo = new boolean[4];
    private final Stack<JButton> pilaCronos;

    private void iniciarCronometroDesdeFecha(int numero, String fechaBD, String tiempoAnterior) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime fechaInicio = LocalDateTime.parse(fechaBD, formatter);
            inicioCrono[numero] = fechaInicio.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();

            String[] partes = tiempoAnterior.split(":");

            long horas = Long.parseLong(partes[0]);
            long minutos = Long.parseLong(partes[1]);
            long segundos = Long.parseLong(partes[2]);

            acumulado[numero] = (horas * 3600L + minutos * 60L + segundos) * 1000L;
            corriendo[numero] = true;

            if (!timer.isRunning()) {
                timer.start();
            }

        } catch (DateTimeParseException ex) {
            JOptionPane.showMessageDialog(this, "No se pudo convertir la fecha: " + fechaBD, "Error", JOptionPane.ERROR_MESSAGE);
            corriendo[numero] = false;
        } catch (RuntimeException ex) {
            JOptionPane.showMessageDialog(this, "No se pudo convertir el tiempo anterior: " + tiempoAnterior, "Error", JOptionPane.ERROR_MESSAGE);
            corriendo[numero] = false;
        }
    }

    private void actualizarCronometros() {
        long ahora = System.currentTimeMillis();
        for (int i = 0; i < pilaCronos.size(); i++) {
            if (corriendo[i]) {
                long tiempo = acumulado[i] + (ahora - inicioCrono[i]);
                pilaCronos.get(i).setText(formatearTiempo(tiempo));
            }
        }
    }

    private String formatearTiempo(long milisegundos) {
        long segundos = milisegundos / 1000;
        long horas = segundos / 3600;
        long minutos = (segundos % 3600) / 60;
        long segundosRestantes = segundos % 60;
        return String.format("%02d:%02d:%02d", horas, minutos, segundosRestantes);
    }

    public void entrar(TempleteDiseno templete, String ruta) {
        Disenio1 c = new Disenio1(templete, inicio, ruta, this.numEmpleado);
        inicio.jDesktopPane1.add(c);
        c.toFront();
        c.setLocation(inicio.jDesktopPane1.getWidth() / 2 - c.getWidth() / 2, inicio.jDesktopPane1.getHeight() / 2 - c.getHeight() / 2);
        try {
            c.setMaximum(true);
        } catch (PropertyVetoException e) {
            Logger.getLogger(Inicio1.class.getName()).log(Level.SEVERE, null, e);
        }
        c.setVisible(true);
        this.dispose();
    }

    public final void insertarImagen(String url, JButton lbl) {
        ImageIcon icon = new ImageIcon(url);
        Image imagen = icon.getImage();
        Image imagenEscalada = imagen.getScaledInstance(60, 60, Image.SCALE_SMOOTH);

        lbl.setIcon(new ImageIcon(imagenEscalada));
    }

    public final void activarEmpresas(Connection con, boolean activo, int id) {
        try {
            String sql = "update templetediseno set activo = ? where idtempletediseno = ?";
            PreparedStatement pst = con.prepareStatement(sql);

            pst.setBoolean(1, activo);
            pst.setInt(2, id);

            int n = pst.executeUpdate();

            if (n < 1) {
                JOptionPane.showMessageDialog(this, "Error al trater de activar/desactivar empresa", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al tratar de activar/desactivar empresa: " + e, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public final void crearBotones(TempleteDiseno templete, String ruta, int id, Connection con, boolean activo) {
        rojeru_san.rspanel.RSPanelRound pnl = new rojeru_san.rspanel.RSPanelRound();
        pnl.setColorBackground(new java.awt.Color(255, 255, 255));
        pnl.setColorBorde(new java.awt.Color(255, 255, 255));
        pnl.setPreferredSize(new java.awt.Dimension(100, 100));
        pnl.setLayout(new java.awt.BorderLayout());
        java.awt.GridBagConstraints gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);

        JPanel pnlDesactivar = new javax.swing.JPanel();
        toggle.ToggleButton tgDesactivar = new toggle.ToggleButton();
        tgDesactivar.setForeground(new java.awt.Color(0, 112, 192));
        tgDesactivar.setSelected(activo);

        ToggleListener tog = new ToggleListener() {
            @Override
            public void onSelected(boolean selected) {
                activarEmpresas(con, selected, id);
            }

            @Override
            public void onAnimated(float animated) {
            }
        };
        tgDesactivar.addEventToggleSelected(tog);
        if (activar) {
            pnlDesactivar.setVisible(true);
        } else {
            pnlDesactivar.setVisible(false);
        }
        pnlDesactivar.add(tgDesactivar);

        javax.swing.JButton lbl = new javax.swing.JButton();
        lbl.setFont(new java.awt.Font("Roboto", 1, 14));
        lbl.setForeground(new java.awt.Color(0, 153, 255));
        insertarImagen(ruta, lbl);
        lbl.setBorder(null);
        lbl.setText(templete.getEmpresa());
        lbl.setContentAreaFilled(false);
        lbl.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lbl.setFocusPainted(false);
        lbl.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        lbl.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        lbl.addActionListener((java.awt.event.ActionEvent evt) -> {
            entrar(templete, ruta);
        });
        pnl.add(pnlDesactivar, java.awt.BorderLayout.NORTH);
        pnl.add(lbl, java.awt.BorderLayout.CENTER);

        pnlEmpresas.add(pnl, gridBagConstraints);
    }

    public String guardarImagenBD(Connection conexion, int id, String nombreArchivo) throws Exception {
        String documentos = System.getProperty("user.home") + File.separator + "Documents";
        Path carpeta = Paths.get(documentos, "karva");
        Files.createDirectories(carpeta);
        Path archivo = carpeta.resolve(nombreArchivo);
        if (Files.exists(archivo)) {
            return archivo.toAbsolutePath().toString();
        }
        String sql = "select * from archivostemplete where idtempletediseno like '" + id + "' and doc like 'img'";
        Statement st = conexion.createStatement();
        ResultSet rs = st.executeQuery(sql);
        if (!rs.next()) {
            throw new Exception("No se encontró la imagen para el ID: " + id);
        }
        byte[] datos = rs.getBytes("archivo");

        if (datos == null || datos.length == 0) {
            throw new Exception("El campo doc está vacío.");
        }
        Files.write(archivo, datos);

        return archivo.toAbsolutePath().toString();
    }

    public final void limpiarPanel() {
        pnlEmpresas.removeAll();
        revalidate();
        repaint();
    }

    public final void agregarEmpresas() {
        try {
            limpiarPanel();
            Connection con = new Conexion().getConnection();
            String sql = "select * from templetediseno where activo like true order by empresa asc";
            if (activar) {
                sql = "select * from templetediseno order by empresa asc";
            }
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            templetes = new Stack<>();
            while (rs.next()) {
                int id = rs.getInt("idtempletediseno");
                String empresa = rs.getString("empresa");
                String desenador = rs.getString("disenador");
                String cliente = rs.getString("cliente");
                String revision = rs.getString("revision");
                String parte = rs.getString("parte");
                String descripcion = rs.getString("descripcion");
                String material = rs.getString("material");
                String dureza = rs.getString("dureza");
                String ensamble = rs.getString("ensamble");
                String tratamiento = rs.getString("tratamiento");
                String cantidad = rs.getString("cantidad");
                String codigo = rs.getString("codigo");
                String bom = rs.getString("bom");
                boolean activo = rs.getBoolean("activo");
                templetes.add(new TempleteDiseno(empresa, desenador, cliente, revision, parte, descripcion, material, dureza, ensamble, tratamiento, cantidad, codigo, bom));
                String ruta = guardarImagenBD(con, id, empresa + ".jpg");
                crearBotones(templetes.lastElement(), ruta, id, con, activo);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al agregar templetes: " + e, "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            Logger.getLogger(InicioDiseño.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String insertarInicioHora(String id) {
        try {
            Connection con = new Conexion().getConnection();
            String sql = "update ordentrabajo set inicio = ?, estado = ? where idordentrabajo = ?";
            PreparedStatement pst = con.prepareStatement(sql);

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            pst.setString(1, sdf.format(new Date()));
            pst.setString(2, "Iniciado");
            pst.setString(3, id);

            int n = pst.executeUpdate();

            if (n < 0) {
                JOptionPane.showMessageDialog(this, "Error al iniciar tiempo", "Error", JOptionPane.ERROR_MESSAGE);
            }
            return sdf.format(new Date());
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al insertar fecha de inicio: " + e, "Error", JOptionPane.ERROR_MESSAGE);
        }
        return null;
    }

    public final void crearBotonesOT(int i, String id, String proyecto, String cronometro, String inicio, String termino) {
        java.awt.GridBagConstraints gridBagConstraints = new java.awt.GridBagConstraints();
        scrollPane.PanelRound pnlOT = new scrollPane.PanelRound();
        pnlOT.setBackground(new java.awt.Color(204, 204, 204));
        pnlOT.setRoundBottomLeft(10);
        pnlOT.setRoundBottomRight(10);
        pnlOT.setRoundTopLeft(10);
        pnlOT.setRoundTopRight(10);

        javax.swing.JLabel lblId = new javax.swing.JLabel();
        lblId.setFont(new java.awt.Font("Roboto", 1, 12));
        lblId.setForeground(new java.awt.Color(51, 51, 51));
        lblId.setText(id);
        pnlOT.add(lblId);

        javax.swing.JLabel lblProyecto = new javax.swing.JLabel();
        lblProyecto.setFont(new java.awt.Font("Roboto", 0, 12));
        lblProyecto.setForeground(new java.awt.Color(51, 51, 51));
        lblProyecto.setText(proyecto);
        pnlOT.add(lblProyecto);

        javax.swing.JButton btnInicio = new javax.swing.JButton();
        pilaCronos.add(btnInicio);
        btnInicio.setBackground(new java.awt.Color(153, 153, 153));
        if (inicio == null) {
            btnInicio.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/play.png")));
            btnInicio.addActionListener((e) -> {
                String fecha = insertarInicioHora(id);
                iniciarCronometroDesdeFecha(i, fecha, cronometro);
                btnInicio.setIcon(null);
                btnInicio.setEnabled(false);
            });
        } else {
            iniciarCronometroDesdeFecha(i, inicio, cronometro);
            btnInicio.setEnabled(false);
        }
        btnInicio.setText(cronometro);
        pnlOT.add(btnInicio);

        gridBagConstraints.gridy = i;
        gridBagConstraints.insets = new java.awt.Insets(3, 5, 3, 5);
        jPanel2.add(pnlOT, gridBagConstraints);
    }

    public final void agregarOT() {
        try {
            Connection con = new Conexion().getConnection();
            String sql = "select * from ordentrabajo where responsable like '" + numEmpleado + "' and (estado != 'Cancelado' and estado != 'Cerrado') ";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            int i = 0;
            while (rs.next()) {
                String id = rs.getString("idordentrabajo");
                String proyecto = rs.getString("proyecto");
                String cronometro = rs.getString("tiempo");
                String inicio = rs.getString("inicio");
                String termino = rs.getString("termino");
                crearBotonesOT(i, id, proyecto, cronometro, inicio, termino);
                i++;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al ver ordenes de trabajo: " + e, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public InicioDiseño(Inicio1 ini, String numEmpleado) {
        this.pilaCronos = new Stack<>();
        initComponents();
        inicio = ini;
        this.numEmpleado = numEmpleado;
        ((javax.swing.plaf.basic.BasicInternalFrameUI) this.getUI()).setNorthPane(null);
        agregarEmpresas();
        agregarOT();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlEmpresas = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        btnSalir = new javax.swing.JPanel();
        lblX = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();

        setBorder(null);

        pnlEmpresas.setBackground(new java.awt.Color(245, 245, 245));
        pnlEmpresas.setPreferredSize(new java.awt.Dimension(150, 150));
        pnlEmpresas.setLayout(new java.awt.GridBagLayout());
        getContentPane().add(pnlEmpresas, java.awt.BorderLayout.CENTER);

        jPanel5.setBackground(new java.awt.Color(245, 245, 245));
        jPanel5.setLayout(new java.awt.BorderLayout());

        jPanel7.setBackground(new java.awt.Color(245, 245, 245));

        btnSalir.setBackground(new java.awt.Color(245, 245, 245));

        lblX.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblX.setForeground(new java.awt.Color(0, 0, 0));
        lblX.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblX.setText(" x ");
        lblX.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
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
        btnSalir.add(lblX);

        jPanel7.add(btnSalir);

        jPanel5.add(jPanel7, java.awt.BorderLayout.EAST);

        getContentPane().add(jPanel5, java.awt.BorderLayout.PAGE_START);

        jPanel1.setBackground(new java.awt.Color(245, 245, 245));
        jPanel1.setLayout(new java.awt.BorderLayout());

        jLabel1.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(102, 102, 102));
        jLabel1.setText("Ordenes de trabajo");
        jPanel1.add(jLabel1, java.awt.BorderLayout.NORTH);

        jPanel2.setBackground(new java.awt.Color(245, 245, 245));
        jPanel2.setLayout(new java.awt.GridBagLayout());
        jPanel1.add(jPanel2, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel1, java.awt.BorderLayout.WEST);

        jMenu1.setText("File");

        jMenuItem1.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        jMenuItem1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/add.png"))); // NOI18N
        jMenuItem1.setText("Agregar nuevo templete                       ");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuItem2.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        jMenuItem2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/ajustes.png"))); // NOI18N
        jMenuItem2.setText("Activar / Desactivar templete");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void lblXMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblXMouseClicked
        dispose();
    }//GEN-LAST:event_lblXMouseClicked

    private void lblXMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblXMouseEntered
        btnSalir.setBackground(Color.red);
        lblX.setForeground(Color.white);
    }//GEN-LAST:event_lblXMouseEntered

    private void lblXMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblXMouseExited
        btnSalir.setBackground(new Color(245, 245, 245));
        lblX.setForeground(Color.black);
    }//GEN-LAST:event_lblXMouseExited

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        JFrame f = (JFrame) JOptionPane.getFrameForComponent(this);
        AgregarPlano add = new AgregarPlano(f, true);
        add.setLocationRelativeTo(f);
        add.setVisible(true);
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        activar = !activar;
        agregarEmpresas();
    }//GEN-LAST:event_jMenuItem2ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel btnSalir;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JLabel lblX;
    private javax.swing.JPanel pnlEmpresas;
    // End of variables declaration//GEN-END:variables
}
