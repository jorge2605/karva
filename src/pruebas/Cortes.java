package pruebas;

import Conexiones.Conexion;
import Controlador.maquinados.reportarPlano;
import Controlador.maquinados.revisarPlanos;
import VentanaEmergente.Maquinados.elegirRevision;
import com.mxrck.autocompleter.TextAutoCompleter;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Font;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import static java.lang.Thread.sleep;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

public final class Cortes extends JInternalFrame {

    int mili = 0;
    int segundos;
    int minutos = 0;
    int horas = 0;
    boolean estado = false;
    public String fechaInicio;
    public String pl;
    private boolean ver = false;
    TableRowSorter<TableModel> elQueOrdena;
    String numEmpleado;
    int contReporte;
    private TextAutoCompleter au;

    elegirRevision elegirRevision;

    public void activar() {
        estado = true;
        String datos[] = new String[10];
        try {
            Connection con = null;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            String sql = "select * from datos where Proyecto like '" + pl + "'";
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                datos[1] = rs.getString("FechaInicio");
                datos[2] = rs.getString("FechaFinal");
                datos[3] = rs.getString("Cronometro");
            }
        } catch (SQLException E) {
            JOptionPane.showMessageDialog(this, "ERROR" + E);
        }
        int hora1, hora2, minuto1, minuto2, dia1, dia2, seg1, seg2, dia = 0;
        int fecha9 = 0, fecha10 = 0, cr1, cr2;
        String d1, d2, f1, f2, f4, f5, f6, f7, fin = "";
        String c1, c2;
        if (datos[2] == null || datos[2].equals("")) {
            Date fe = new Date();
            SimpleDateFormat nuevo = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            String fec = nuevo.format(fe);
            datos[2] = fec;
        }
        f1 = datos[1].substring(11, 13);
        f2 = datos[1].substring(14, 16);
        f4 = datos[2].substring(11, 13);
        f5 = datos[2].substring(14, 16);
        f6 = datos[1].substring(17, 19);
        f7 = datos[2].substring(17, 19);
        d1 = datos[1].substring(0, 2);
        d2 = datos[2].substring(0, 2);

        hora1 = Integer.parseInt(f1);
        minuto1 = Integer.parseInt(f2);
        hora2 = Integer.parseInt(f4);
        minuto2 = Integer.parseInt(f5);
        dia1 = Integer.parseInt(d1);
        dia2 = Integer.parseInt(d2);
        seg1 = Integer.parseInt(f6);
        seg2 = Integer.parseInt(f7);

        if (dia2 > dia1) {
            dia = (dia2 - dia1);
        }
        hora2 = (dia * 24) + hora2;
        horas = hora2 - hora1;

        if (minuto2 < minuto1) {
            minutos = (60 + minuto2) - minuto1;
            horas--;
        } else {
            minutos = minuto2 - minuto1;
        }

        if (seg2 < seg1) {
            segundos = (seg2 + 60) - seg1;
            minutos--;
        } else {
            segundos = seg2 - seg1;
        }

        c1 = datos[3].substring(0, 2);
        c2 = datos[3].substring(3, 5);
        if (c1 != "00" || c2 != "00") {
            cr1 = Integer.parseInt(c1);
            cr2 = Integer.parseInt(c2);
            horas = horas + cr1;
            minutos = minutos + cr2;
        }

        Thread hilo = new Thread() {
            public void run() {
                for (;;) {
                    if (estado == true) {
                        try {
                            sleep(1);
                            if (mili >= 750) {
                                mili = 0;
                                segundos++;
                            }
                            if (segundos >= 60) {
                                mili = 0;
                                segundos = 0;
                                minutos++;
                            }
                            if (minutos >= 60) {
                                mili = 0;
                                segundos = 0;
                                minutos = 0;
                                horas++;
                            }
                            String ho = "", mi = "", se = "";
                            if (horas < 10) {
                                ho = "0" + horas;
                            } else {
                                ho = "" + horas;
                            }
                            if (minutos < 10) {
                                mi = "0" + minutos;
                            } else {
                                mi = "" + minutos;
                            }
                            if (segundos < 10) {
                                se = "0" + segundos;
                            } else {
                                se = "" + segundos;
                            }

                            lblCrono.setText(ho + ":" + mi + ":" + se);
                            lblCrono1.setText("" + mili);
                            mili++;
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

    public void datos(String plano) {
        Date fe = new Date();
        SimpleDateFormat nuevo = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String fec = nuevo.format(fe);
        JOptionPane.showMessageDialog(this, "PLANO EMPEZADO");
        limpiarTabla();
        verDatos("select Estado, id, plano, proyecto from planos where Estado like 'CORTES' order by id desc");
        txtPlano.setText(plano);
        txtCodigo.setText("");
        txtProyecto.setText(fec);
        lblEstado.setText("EN CURSO");
        lblEstado.setForeground(Color.green);

    }

    public void limpiarTabla() {
        Tabla1 = new javax.swing.JTable();
        Tabla1.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{},
                new String[]{
                    "Numero de plano", "Proyecto"
                }
        ) {
            boolean[] canEdit = new boolean[]{
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
        Tabla1.setComponentPopupMenu(jPopupMenu1);
        Tabla1.getTableHeader().setFont(new Font("Roboto", Font.BOLD, 14));
        Tabla1.setFont(new java.awt.Font("Roboto", Font.PLAIN, 12));
        Tabla1.getTableHeader().setOpaque(false);
        Tabla1.getTableHeader().setBackground(new Color(254, 254, 254));
        Tabla1.getTableHeader().setForeground(new Color(0, 78, 171));
        Tabla1.setRowHeight(25);
        Tabla1.setShowGrid(false);

        jScrollPane1.getViewport().setBackground(Color.white);
        jScrollPane1.setForeground(Color.white);
        jScrollPane1.getViewport().setForeground(Color.white);
        jScrollPane1.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(254, 254, 254)));
        jScrollPane1.setBackground(Color.white);
        jScrollPane1.setViewportView(Tabla1);
        if (Tabla1.getColumnModel().getColumnCount() > 0) {
            Tabla1.getColumnModel().getColumn(0).setResizable(false);
            Tabla1.getColumnModel().getColumn(1).setResizable(false);
        }
    }

    public void tabla() {
        JOptionPane.showMessageDialog(this, "DATOS GUARDADOS CORRECTAMENTE");
        txtProyecto.setText("");
        limpiarTabla();
        verDatos("");
        panelPiezas.setVisible(false);
        txtPlano.setText("");
        txtProyecto.setText("");
    }

    public void borrar() {
        txtPlano.setText("");
        txtProyecto.setText("");
        lblEstado.setText("SIN SELECCIONAR");
        lblEstado.setForeground(Color.red);
    }

    public void actualizarRevision() {
        try {
            Connection con;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            String sql = "select * from datos where Revision is null";
            ResultSet rs = st.executeQuery(sql);
            String sql2 = "update datos set Revision = ? where Proyecto = ?";
            PreparedStatement pst = con.prepareStatement(sql2);
            while (rs.next()) {
                String plano = rs.getString("Proyecto");
                String sql3 = "select Plano, Revision from Planos where Plano like '" + plano + "'";
                Statement st2 = con.createStatement();
                ResultSet rs2 = st2.executeQuery(sql3);
                String revision = "";
                while (rs2.next()) {
                    revision = rs2.getString("Revision");
                }

                pst.setString(1, revision);
                pst.setString(2, plano);
                int n = pst.executeUpdate();

                if (n < 1) {
                    JOptionPane.showMessageDialog(this, "Datos no actualizados: Revision - Plano: " + plano, "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "ERROR: " + e, "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    public String obtenerDepartamento() {
        switch (cmbEnviar.getSelectedIndex()) {
            case 1:
                return "maquinados";
            case 2:
                return "calidad";
            case 3:
                return "integracion";
            case 4:
                return "datos";
            default:
                return null;
        }
    }
    
    public int enviarCortes(Connection con, String plano, String proyecto) throws SQLException {
        String sql = "insert into datos (Proyecto, Plano, FechaInicio, FechaFinal, Terminado, Estado, Cronometro, Prioridad, Empleado, Revision) values(?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement pst = con.prepareStatement(sql);

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date d = new Date();
        String fecha = sdf.format(d);

        pst.setString(1, plano);
        pst.setString(2, proyecto);
        pst.setString(3, fecha);
        pst.setString(4, fecha);
        pst.setString(5, "NO");
        pst.setString(6, "SIN MATERIAL");
        pst.setString(7, "00:00");
        pst.setString(8, "10");
        pst.setString(9, numEmpleado);
        pst.setString(10, "");

        int n = pst.executeUpdate();
        
        if (n  > 0) {
            JOptionPane.showMessageDialog(this, "Datos guardados");
        } else {
            JOptionPane.showMessageDialog(this, "Error al guardar datos", "Error", JOptionPane.ERROR_MESSAGE);
        }
        
        return 0;
    }

    public void enviarPlano(String plano, String proyecto) {
        try {
            Connection con;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            revisarPlanos rev = new revisarPlanos();
            String estacionSeleccionada = obtenerDepartamento();
            if (cmbEnviar.getSelectedIndex() == 4) {
                rev.actualizarPlanos(con, plano, "SIN MATERIAL");
                rev.transaccionTerminarPlano(con, plano, proyecto, "00:00", "datos", numEmpleado, estacionSeleccionada);
            } else {
                rev.actualizarPlanos(con, plano, estacionSeleccionada.toUpperCase());
                rev.transaccionTerminarPlano(con, plano, proyecto, "00:00", "datos", numEmpleado, estacionSeleccionada);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error: " + e, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public final void verDatos(String sql) {
        try {
            limpiarTabla();
            Connection con;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            String datos[] = new String[10];
            DefaultTableModel miModelo = (DefaultTableModel) Tabla1.getModel();
            int cont = 0;
            while (rs.next()) {
                datos[0] = rs.getString("Proyecto");
                datos[1] = rs.getString("Plano");
                miModelo.addRow(datos);
                cont++;
            }
            lblConteo.setText("Cantidad de Planos: " + cont);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al ver datos calidad: " + e, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public final void verProyectos() {
        try {
            Connection con;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            String sql = "select proyecto from proyectos";
            ResultSet rs = st.executeQuery(sql);
            au = new TextAutoCompleter(jTextField1);
            while (rs.next()) {
                au.addItem(rs.getString("proyecto"));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al ver proyectos: " + e, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public String getProyecto(String plano, Connection con) throws SQLException {
        Statement st = con.createStatement();
        String sql = "select proyecto from proyectos where proyecto like '" + plano + "%'";
        ResultSet rs = st.executeQuery(sql);
        while (rs.next()) {
            return rs.getString("Proyecto");
        }
        return null;
    }
    
    public String obtenerCaracter(String plano) {
        String texto = plano;
        
        Pattern pattern = Pattern.compile("[^0-9a-zA-Z ]");
        Matcher matcher = pattern.matcher(texto);

        while (matcher.find()) {
            return  matcher.group();
        }
        return null;
    }
    
    public String formatear(String plano, Connection con) throws SQLException {
        String caracter = obtenerCaracter(plano);
        String spl[] = plano.split(" ");
        if (caracter != null && plano.contains(caracter)) {
            return plano.substring(0, plano.indexOf(caracter));
        } else if (spl.length == 3) {
            return plano;
        } else if (spl.length == 2) {
            return getProyecto(spl[0], con) + " " + spl[1];
        } else {
            return null;
        }
    }
    
    public String validarPlano(Connection con, String proyecto) throws SQLException{
        Statement st = con.createStatement();
        String sql = "select proyecto from proyectos where proyecto like '" + proyecto + "%'";
        ResultSet rs = st.executeQuery(sql);
        String proyectoRet = null;
        while (rs.next()) {
            proyectoRet = rs.getString("proyecto");
        }
        return proyectoRet;
    }
    
    public void limpiarFormulario() {
        txtPlano.setText("");
        txtProyecto.setText("");
    }
    
    public Cortes(String numEmpleado) {
        initComponents();
        limpiarTabla();
        verDatos("select Estado, id, plano, proyecto from planos where Estado like 'CORTES' order by id desc");
        verProyectos();
        actualizarRevision();
        DefaultTableModel Modelo = (DefaultTableModel) Tabla1.getModel();
        elQueOrdena = new TableRowSorter<TableModel>(Modelo);
        Tabla1.setRowSorter(elQueOrdena);

        txtNumero.setEnabled(false);
        txtNombre.setEnabled(false);
        txtPuesto.setEnabled(false);
        lblEstado.setText("SIN SELECCIONAR");
        lblEstado.setForeground(Color.red);

        ((javax.swing.plaf.basic.BasicInternalFrameUI) this.getUI()).setNorthPane(null);
        this.numEmpleado = numEmpleado;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupMenu1 = new javax.swing.JPopupMenu();
        terminarPlanos = new javax.swing.JMenuItem();
        jPanel1 = new javax.swing.JPanel();
        panelCentral = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        txtEmpleado = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        txtCodigo = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        jPanel24 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtPlano = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtProyecto = new javax.swing.JLabel();
        rSPanelRound1 = new rojeru_san.rspanel.RSPanelRound();
        jLabel20 = new javax.swing.JLabel();
        panelTipo = new rojeru_san.rspanel.RSPanelRound();
        lblTipo = new javax.swing.JLabel();
        lblRevision = new javax.swing.JLabel();
        panelPiezas = new rojeru_san.rspanel.RSPanelRound();
        lblPiezas = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        lblCrono = new javax.swing.JLabel();
        lblCrono1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtNumero = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtPuesto = new javax.swing.JTextField();
        panelEste = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jPanel22 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        middle = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        cmbEnviar = new RSMaterialComponent.RSComboBoxMaterial();
        jPanel16 = new javax.swing.JPanel();
        btnActualizar = new javax.swing.JButton();
        jPanel20 = new javax.swing.JPanel();
        btnPausa = new javax.swing.JButton();
        jPanel21 = new javax.swing.JPanel();
        btnGuardar = new javax.swing.JButton();
        jPanel23 = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        lblEstado = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        Tabla1 = new javax.swing.JTable();
        jPanel13 = new javax.swing.JPanel();
        btnPedir = new javax.swing.JButton();
        jPanel14 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jPanel15 = new javax.swing.JPanel();
        jPanel25 = new javax.swing.JPanel();
        jLabel22 = new javax.swing.JLabel();
        lblTitulo = new javax.swing.JLabel();
        jPanel26 = new javax.swing.JPanel();
        jLabel21 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        lblConteo = new javax.swing.JLabel();
        jPanel17 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jPanel18 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jPanel11 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        jPanel19 = new javax.swing.JPanel();
        btnSalir = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();

        terminarPlanos.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        terminarPlanos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/entrega-rapida.png"))); // NOI18N
        terminarPlanos.setText("Seleccionar estacion                          ");
        terminarPlanos.setEnabled(false);
        terminarPlanos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                terminarPlanosActionPerformed(evt);
            }
        });
        jPopupMenu1.add(terminarPlanos);

        setBackground(new java.awt.Color(255, 255, 255));
        setBorder(null);
        setFrameIcon(null);
        setVisible(true);
        getContentPane().setLayout(new java.awt.BorderLayout(5, 5));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new java.awt.BorderLayout(20, 0));

        panelCentral.setBackground(new java.awt.Color(255, 255, 255));
        panelCentral.setLayout(new javax.swing.BoxLayout(panelCentral, javax.swing.BoxLayout.Y_AXIS));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new java.awt.GridLayout(6, 0, 20, 20));

        jLabel9.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("DATOS DE PLANO");
        jPanel2.add(jLabel9);

        jLabel12.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setText("NUMERO DE EMPLEADO");
        jPanel2.add(jLabel12);

        txtEmpleado.setBackground(new java.awt.Color(255, 255, 255));
        txtEmpleado.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtEmpleado.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtEmpleado.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(204, 204, 204)));
        txtEmpleado.setNextFocusableComponent(txtCodigo);
        txtEmpleado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEmpleadoActionPerformed(evt);
            }
        });
        jPanel2.add(txtEmpleado);

        jLabel16.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel16.setText("CODIGO DE BARRAS");
        jPanel2.add(jLabel16);

        txtCodigo.setBackground(new java.awt.Color(255, 255, 255));
        txtCodigo.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtCodigo.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCodigo.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(204, 204, 204)));
        txtCodigo.setNextFocusableComponent(btnGuardar);
        txtCodigo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodigoActionPerformed(evt);
            }
        });
        jPanel2.add(txtCodigo);

        panelCentral.add(jPanel2);

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setLayout(new java.awt.BorderLayout());

        jPanel24.setBackground(new java.awt.Color(255, 255, 255));
        jPanel24.setLayout(new java.awt.GridLayout(3, 0));

        jLabel2.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText("NUMERO DE PLANO:  ");
        jPanel24.add(jLabel2);

        txtPlano.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        txtPlano.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jPanel24.add(txtPlano);

        jLabel4.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setText("Proyecto:");
        jPanel24.add(jLabel4);

        txtProyecto.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        txtProyecto.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jPanel24.add(txtProyecto);

        rSPanelRound1.setColorBackground(new java.awt.Color(0, 102, 204));
        rSPanelRound1.setColorBorde(new java.awt.Color(0, 102, 204));

        jLabel20.setFont(new java.awt.Font("Roboto", 1, 20)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(255, 255, 255));
        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel20.setText("TIPO:  ");
        rSPanelRound1.add(jLabel20);

        jPanel24.add(rSPanelRound1);

        panelTipo.setColorBackground(new java.awt.Color(0, 102, 204));
        panelTipo.setColorBorde(new java.awt.Color(0, 102, 204));

        lblTipo.setFont(new java.awt.Font("Roboto", 1, 20)); // NOI18N
        lblTipo.setForeground(new java.awt.Color(255, 255, 255));
        lblTipo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTipo.setText("Revision: ");
        lblTipo.setToolTipText("");
        panelTipo.add(lblTipo);

        lblRevision.setFont(new java.awt.Font("Roboto", 1, 20)); // NOI18N
        lblRevision.setForeground(new java.awt.Color(255, 255, 255));
        lblRevision.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        panelTipo.add(lblRevision);

        jPanel24.add(panelTipo);

        jPanel4.add(jPanel24, java.awt.BorderLayout.CENTER);

        panelPiezas.setColorBackground(new java.awt.Color(255, 102, 0));
        panelPiezas.setColorBorde(new java.awt.Color(255, 102, 0));

        lblPiezas.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        lblPiezas.setForeground(new java.awt.Color(255, 255, 255));
        lblPiezas.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblPiezas.setText("Pieza realizada 3 veces");
        panelPiezas.add(lblPiezas);

        jPanel4.add(panelPiezas, java.awt.BorderLayout.SOUTH);

        panelCentral.add(jPanel4);

        jPanel5.setLayout(new java.awt.BorderLayout());

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setLayout(new java.awt.GridLayout(1, 0));

        jLabel13.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IconoC/letra-h.png"))); // NOI18N
        jPanel6.add(jLabel13);

        jLabel14.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IconoC/letra-m.png"))); // NOI18N
        jPanel6.add(jLabel14);

        jLabel15.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IconoC/letra-s.png"))); // NOI18N
        jPanel6.add(jLabel15);

        jPanel5.add(jPanel6, java.awt.BorderLayout.PAGE_START);

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setLayout(new java.awt.BorderLayout());

        lblCrono.setFont(new java.awt.Font("Roboto", 1, 72)); // NOI18N
        lblCrono.setForeground(new java.awt.Color(0, 102, 102));
        lblCrono.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblCrono.setText("00:00:00");
        jPanel7.add(lblCrono, java.awt.BorderLayout.CENTER);

        lblCrono1.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        lblCrono1.setForeground(new java.awt.Color(0, 102, 102));
        lblCrono1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblCrono1.setText("0000");
        jPanel7.add(lblCrono1, java.awt.BorderLayout.SOUTH);

        jPanel5.add(jPanel7, java.awt.BorderLayout.CENTER);

        panelCentral.add(jPanel5);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setLayout(new java.awt.GridLayout(7, 0, 0, 20));

        jLabel3.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("DATOS DE EMPLEADO");
        jPanel3.add(jLabel3);

        jLabel7.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("NUMERO EMPLEADO");
        jPanel3.add(jLabel7);

        txtNumero.setBackground(new java.awt.Color(255, 255, 255));
        txtNumero.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtNumero.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtNumero.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(204, 204, 204)));
        jPanel3.add(txtNumero);

        jLabel6.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("NOMBRE");
        jPanel3.add(jLabel6);

        txtNombre.setBackground(new java.awt.Color(255, 255, 255));
        txtNombre.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtNombre.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtNombre.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(204, 204, 204)));
        jPanel3.add(txtNombre);

        jLabel8.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("PUESTO");
        jPanel3.add(jLabel8);

        txtPuesto.setBackground(new java.awt.Color(255, 255, 255));
        txtPuesto.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtPuesto.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtPuesto.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(204, 204, 204)));
        jPanel3.add(txtPuesto);

        panelCentral.add(jPanel3);

        jPanel1.add(panelCentral, java.awt.BorderLayout.CENTER);

        panelEste.setBackground(new java.awt.Color(255, 255, 255));
        panelEste.setLayout(new java.awt.BorderLayout());

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));
        jPanel8.setLayout(new java.awt.BorderLayout());

        jPanel22.setBackground(new java.awt.Color(255, 255, 255));

        jLabel18.setFont(new java.awt.Font("Segoe UI", 0, 48)); // NOI18N
        jLabel18.setText("  ");
        jPanel22.add(jLabel18);

        jPanel8.add(jPanel22, java.awt.BorderLayout.NORTH);

        middle.setBackground(new java.awt.Color(255, 255, 255));
        middle.setLayout(new java.awt.GridLayout(4, 0));

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));

        cmbEnviar.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "SELECCIONAR", "MAQUINADOS", "CALIDAD", "INTEGRACION", "SIN MATERIAL" }));
        cmbEnviar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbEnviarActionPerformed(evt);
            }
        });
        jPanel9.add(cmbEnviar);

        middle.add(jPanel9);

        jPanel16.setBackground(new java.awt.Color(255, 255, 255));

        btnActualizar.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        btnActualizar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ImgAnimacion/actualizar_48.png"))); // NOI18N
        btnActualizar.setBorder(null);
        btnActualizar.setContentAreaFilled(false);
        btnActualizar.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnActualizar.setFocusPainted(false);
        btnActualizar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnActualizar.setMaximumSize(new java.awt.Dimension(70, 70));
        btnActualizar.setPreferredSize(new java.awt.Dimension(70, 70));
        btnActualizar.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/ImgAnimacion/actualizar_48.png"))); // NOI18N
        btnActualizar.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/ImgAnimacion/actualizar_64.png"))); // NOI18N
        btnActualizar.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        btnActualizar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarActionPerformed(evt);
            }
        });
        jPanel16.add(btnActualizar);

        middle.add(jPanel16);

        jPanel20.setBackground(new java.awt.Color(255, 255, 255));

        btnPausa.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        btnPausa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ImgAnimacion/pausa_48.png"))); // NOI18N
        btnPausa.setBorder(null);
        btnPausa.setContentAreaFilled(false);
        btnPausa.setEnabled(false);
        btnPausa.setFocusPainted(false);
        btnPausa.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnPausa.setPreferredSize(new java.awt.Dimension(70, 70));
        btnPausa.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/ImgAnimacion/pausa_48.png"))); // NOI18N
        btnPausa.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/ImgAnimacion/pausa_64.png"))); // NOI18N
        btnPausa.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        btnPausa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPausaActionPerformed(evt);
            }
        });
        jPanel20.add(btnPausa);

        middle.add(jPanel20);

        jPanel21.setBackground(new java.awt.Color(255, 255, 255));

        btnGuardar.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        btnGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ImgAnimacion/send_48.png"))); // NOI18N
        btnGuardar.setBorder(null);
        btnGuardar.setContentAreaFilled(false);
        btnGuardar.setFocusPainted(false);
        btnGuardar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnGuardar.setNextFocusableComponent(txtCodigo);
        btnGuardar.setPreferredSize(new java.awt.Dimension(70, 70));
        btnGuardar.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/ImgAnimacion/send_48.png"))); // NOI18N
        btnGuardar.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/ImgAnimacion/send_64.png"))); // NOI18N
        btnGuardar.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        btnGuardar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });
        jPanel21.add(btnGuardar);

        middle.add(jPanel21);

        jPanel8.add(middle, java.awt.BorderLayout.CENTER);

        jPanel23.setBackground(new java.awt.Color(255, 255, 255));

        jLabel19.setFont(new java.awt.Font("Segoe UI", 0, 48)); // NOI18N
        jLabel19.setText("  ");
        jPanel23.add(jLabel19);

        jPanel8.add(jPanel23, java.awt.BorderLayout.SOUTH);

        panelEste.add(jPanel8, java.awt.BorderLayout.CENTER);

        jPanel10.setBackground(new java.awt.Color(255, 255, 255));
        jPanel10.setLayout(new java.awt.BorderLayout());

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel11.setText("ESTADO");
        jPanel10.add(jLabel11, java.awt.BorderLayout.CENTER);

        lblEstado.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jPanel10.add(lblEstado, java.awt.BorderLayout.EAST);

        panelEste.add(jPanel10, java.awt.BorderLayout.NORTH);

        jPanel1.add(panelEste, java.awt.BorderLayout.EAST);

        jPanel12.setBackground(new java.awt.Color(255, 255, 255));
        jPanel12.setLayout(new java.awt.BorderLayout(20, 5));

        jScrollPane1.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane1.setBorder(null);
        jScrollPane1.setForeground(new java.awt.Color(255, 255, 255));

        Tabla1.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        Tabla1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "PRIORIDAD", "NUMERO DE PLANO", "PROYECTO", "Revision"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        Tabla1.setComponentPopupMenu(jPopupMenu1);
        Tabla1.setFocusable(false);
        Tabla1.setRowHeight(25);
        Tabla1.setSelectionBackground(new java.awt.Color(110, 201, 255));
        Tabla1.getTableHeader().setReorderingAllowed(false);
        Tabla1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Tabla1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(Tabla1);
        if (Tabla1.getColumnModel().getColumnCount() > 0) {
            Tabla1.getColumnModel().getColumn(0).setResizable(false);
            Tabla1.getColumnModel().getColumn(1).setResizable(false);
            Tabla1.getColumnModel().getColumn(2).setResizable(false);
            Tabla1.getColumnModel().getColumn(3).setResizable(false);
        }

        jPanel12.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jPanel13.setBackground(new java.awt.Color(255, 255, 255));
        jPanel13.setLayout(new java.awt.BorderLayout());

        btnPedir.setBackground(new java.awt.Color(255, 255, 255));
        btnPedir.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        btnPedir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ImgAnimacion/metal_48.png"))); // NOI18N
        btnPedir.setBorder(null);
        btnPedir.setContentAreaFilled(false);
        btnPedir.setFocusPainted(false);
        btnPedir.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnPedir.setNextFocusableComponent(txtCodigo);
        btnPedir.setPreferredSize(new java.awt.Dimension(70, 70));
        btnPedir.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/ImgAnimacion/metal_48.png"))); // NOI18N
        btnPedir.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/ImgAnimacion/metal_64.png"))); // NOI18N
        btnPedir.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        btnPedir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPedirActionPerformed(evt);
            }
        });
        jPanel13.add(btnPedir, java.awt.BorderLayout.PAGE_START);

        jPanel14.setBackground(new java.awt.Color(255, 255, 255));
        jPanel14.setLayout(new java.awt.BorderLayout());

        jButton1.setBackground(new java.awt.Color(255, 255, 255));
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ImgAnimacion/ver_48.png"))); // NOI18N
        jButton1.setBorder(null);
        jButton1.setContentAreaFilled(false);
        jButton1.setFocusPainted(false);
        jButton1.setPreferredSize(new java.awt.Dimension(70, 70));
        jButton1.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/ImgAnimacion/ver_48.png"))); // NOI18N
        jButton1.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/ImgAnimacion/ver_64.png"))); // NOI18N
        jButton1.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel14.add(jButton1, java.awt.BorderLayout.PAGE_START);

        jPanel15.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 122, Short.MAX_VALUE)
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 517, Short.MAX_VALUE)
        );

        jPanel14.add(jPanel15, java.awt.BorderLayout.CENTER);

        jPanel13.add(jPanel14, java.awt.BorderLayout.LINE_END);

        jPanel12.add(jPanel13, java.awt.BorderLayout.LINE_END);

        jPanel25.setBackground(new java.awt.Color(255, 255, 255));
        jPanel25.setLayout(new java.awt.BorderLayout(0, 10));

        jLabel22.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(0, 102, 204));
        jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel22.setText("Planos en estacion de cortes");
        jPanel25.add(jLabel22, java.awt.BorderLayout.NORTH);

        lblTitulo.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        lblTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitulo.setText("PLANOS CON MATERIAL");
        jPanel25.add(lblTitulo, java.awt.BorderLayout.SOUTH);

        jPanel26.setBackground(new java.awt.Color(255, 255, 255));
        jPanel26.setLayout(new java.awt.BorderLayout());

        jLabel21.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(0, 102, 204));
        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel21.setText("Ver por proyecto");
        jPanel26.add(jLabel21, java.awt.BorderLayout.CENTER);

        jTextField1.setBackground(new java.awt.Color(255, 255, 255));
        jTextField1.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        jTextField1.setForeground(new java.awt.Color(51, 51, 51));
        jTextField1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField1.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(204, 204, 204)));
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });
        jPanel26.add(jTextField1, java.awt.BorderLayout.SOUTH);

        jPanel25.add(jPanel26, java.awt.BorderLayout.CENTER);

        jPanel12.add(jPanel25, java.awt.BorderLayout.NORTH);

        lblConteo.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        lblConteo.setForeground(new java.awt.Color(51, 51, 51));
        lblConteo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblConteo.setText("Cantidad de Planos: ");
        jPanel12.add(lblConteo, java.awt.BorderLayout.PAGE_END);

        jPanel1.add(jPanel12, java.awt.BorderLayout.WEST);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        jPanel17.setBackground(new java.awt.Color(255, 255, 255));

        jLabel5.setText("       ");
        jPanel17.add(jLabel5);

        getContentPane().add(jPanel17, java.awt.BorderLayout.EAST);

        jPanel18.setBackground(new java.awt.Color(255, 255, 255));

        jLabel10.setText("       ");
        jPanel18.add(jLabel10);

        getContentPane().add(jPanel18, java.awt.BorderLayout.WEST);

        jPanel11.setBackground(new java.awt.Color(255, 255, 255));
        jPanel11.setLayout(new java.awt.BorderLayout());

        jLabel17.setFont(new java.awt.Font("Roboto", 1, 40)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(0, 165, 252));
        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel17.setText("Cortes");
        jPanel11.add(jLabel17, java.awt.BorderLayout.CENTER);

        jPanel19.setBackground(new java.awt.Color(255, 255, 255));

        btnSalir.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("X");
        jLabel1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel1MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel1MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel1MouseExited(evt);
            }
        });
        btnSalir.add(jLabel1);

        jPanel19.add(btnSalir);

        jPanel11.add(jPanel19, java.awt.BorderLayout.EAST);

        getContentPane().add(jPanel11, java.awt.BorderLayout.NORTH);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void Tabla1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Tabla1MouseClicked

    }//GEN-LAST:event_Tabla1MouseClicked

    private void txtEmpleadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEmpleadoActionPerformed
        try {
            int fila = Tabla1.getSelectedRow();
            Connection con = null;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            String datos[] = new String[7];
            String sql = "select * from registroEmpleados where NumEmpleado like '" + txtEmpleado.getText() + "'";
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                datos[0] = rs.getString("NumEmpleado");
                datos[1] = rs.getString("Nombre");
                datos[2] = rs.getString("Apellido");
                datos[3] = rs.getString("Direccion");
                datos[4] = rs.getString("Telefono");
                datos[5] = rs.getString("Puesto");
            }

            if (txtEmpleado.getText().equals(datos[0])) {

                txtNumero.setText(datos[0]);
                txtNombre.setText(datos[1]);
                txtPuesto.setText(datos[5]);
                txtEmpleado.setText("");
                txtCodigo.transferFocus();
            }

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(this, "", "", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_txtEmpleadoActionPerformed

    private void txtCodigoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodigoActionPerformed
        try {
            Connection con;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            String form = formatear(txtCodigo.getText(), con);
            String sql = "select Proyecto, Plano, Cantidad, Material from planos where Plano like '" + form + "'";
            ResultSet rs = st.executeQuery(sql);
            String plano = null;
            limpiarFormulario();
            while (rs.next()) {
                plano = rs.getString("Plano");
                txtPlano.setText(plano);
                txtProyecto.setText(rs.getString("Proyecto"));
            }
            txtCodigo.setText("");
            if (plano == null) {
                int prim = form.indexOf(" ");
                String proyecto = validarPlano(con, form.substring(0, prim));
                if (proyecto != null) {
                    txtPlano.setText(form);
                    txtProyecto.setText(proyecto);
                } else {
                    JOptionPane.showMessageDialog(this, "El plano que ingresaste no existe", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error: " + e, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_txtCodigoActionPerformed

    private void jLabel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseClicked
        dispose();
    }//GEN-LAST:event_jLabel1MouseClicked

    private void jLabel1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseEntered
        btnSalir.setBackground(Color.red);
        jLabel1.setForeground(Color.white);
    }//GEN-LAST:event_jLabel1MouseEntered

    private void jLabel1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseExited
        btnSalir.setBackground(Color.white);
        jLabel1.setForeground(Color.black);
    }//GEN-LAST:event_jLabel1MouseExited

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        enviarPlano(txtPlano.getText(), txtProyecto.getText());
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnPausaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPausaActionPerformed
        Date fecha = new Date();
        SimpleDateFormat fec1 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String fec = fec1.format(fecha);
        String f = "";
        estado = false;
        try {
            Connection con = null;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            String sql = "UPDATE Datos SET Proyecto = ?, Plano = ?, FechaInicio = ?, FechaFinal = ?, Terminado = ?, Estado = ?, Cronometro = ?, Prioridad = ? WHERE Proyecto = ?";
            String ver = "select * from Datos where Proyecto like '" + txtPlano.getText() + "'";
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = st.executeQuery(ver);
            String datos[] = new String[10];
            while (rs.next()) {
                datos[1] = rs.getString("Proyecto");
                datos[2] = rs.getString("Plano");
                datos[3] = rs.getString("FechaInicio");
                datos[4] = rs.getString("FechaFinal");
                datos[5] = rs.getString("Terminado");
                datos[6] = rs.getString("Estado");
                datos[7] = rs.getString("Cronometro");
                datos[8] = rs.getString("Prioridad");
                datos[9] = rs.getString("Revision");
            }
            if (datos[1] != null) {

                if (datos[7] != null) {
                    String fecha1 = datos[3];
                    String fecha2 = fec;

                    int hora1, hora2, minuto1, minuto2, dia1, dia2;
                    int fecha9 = 0, fecha10 = 0;
                    String d1, d2, f1, f2, f4, f5, fin = "";

                    f1 = fecha1.substring(11, 13);
                    f2 = fecha1.substring(14, 16);
                    f4 = fecha2.substring(11, 13);
                    f5 = fecha2.substring(14, 16);
                    d1 = fecha1.substring(0, 2);
                    d2 = fecha2.substring(0, 2);

                    hora1 = Integer.parseInt(f1);
                    minuto1 = Integer.parseInt(f2);
                    hora2 = Integer.parseInt(f4);
                    minuto2 = Integer.parseInt(f5);
                    dia1 = Integer.parseInt(d1);
                    dia2 = Integer.parseInt(d2);

                    int aux;
                    if (dia2 > dia1) {
                        aux = dia2 - dia1;
                        aux = aux * 24;
                        fecha9 = (aux - hora1) + hora2;
                        hora2 = fecha9;
                    } else {
                        fecha9 = (hora2 - hora1);
                    }
                    if (hora2 >= hora1 && minuto2 >= minuto1) {
                        if ((fecha9) < 10 && (minuto2 - minuto2) < 10) {
                            fin = "0" + (fecha9) + ":" + "0" + (minuto2 - minuto1);
                        } else if ((fecha9) < 10) {
                            fin = "0" + (fecha9) + ":" + (minuto2 - minuto1);
                        } else if ((minuto2 - minuto1) < 10) {
                            fin = (fecha9) + ":" + "0" + (minuto2 - minuto1);
                        }

                    } else {
                        if (minuto2 < minuto1) {
                            fecha10 = (60 - minuto1) + minuto2;
                            fecha9 = fecha9 - 1;
                            if (fecha9 < 10 && (fecha10) < 10) {
                                fin = "0" + fecha9 + ":" + "0" + fecha10;
                            } else if ((fecha9) < 10) {
                                fin = "0" + (fecha9) + ":" + (fecha10);
                            } else if ((fecha10) < 10) {
                                fin = (fecha9) + ":" + "0" + (fecha10);
                            } else if ((fecha10) > 10) {
                                fin = (fecha9) + ":" + (fecha10);
                            }
                        } else if (minuto2 > minuto1) {
                            fecha10 = (minuto2 - minuto1);
                            if (fecha9 < 10 && (fecha10) < 10) {
                                fin = "0" + fecha9 + ":" + "0" + fecha10;
                            } else if ((fecha9) < 10) {
                                fin = "0" + (fecha9) + ":" + (fecha10);
                            } else if ((fecha10) < 10) {
                                fin = (fecha9) + ":" + "0" + (fecha10);
                            }
                        } else if ((fecha10) > 10) {
                            fin = (fecha9) + ":" + (fecha10);
                        }
                    }
                    String a = datos[7].substring(0, 2);
                    String b = datos[7].substring(3, 5);
                    String c = fin.substring(0, 2);
                    String d = fin.substring(3, 5);

                    int h1, m1, h2, m2;
                    h1 = Integer.parseInt(a);
                    m1 = Integer.parseInt(b);
                    h2 = Integer.parseInt(c);
                    m2 = Integer.parseInt(d);

                    int sumaH, sumaM;
                    sumaH = h1 + h2;
                    sumaM = m1 + m2;

                    if (sumaM >= 60) {
                        sumaM = (sumaM - 60);
                        sumaH = sumaH + 1;
                    }

                    if ((sumaH) < 10 && (sumaM) < 10) {
                        f = "0" + (sumaH) + ":" + "0" + (sumaM);
                    } else if (sumaH >= 10 && sumaM >= 10) {
                        f = sumaH + ":" + (sumaM);
                    } else if ((sumaH) < 10) {
                        f = "0" + (sumaH) + ":" + (sumaM);
                    } else if ((sumaM) < 10) {
                        f = (sumaH) + ":" + "0" + (sumaM);
                    }
                }
                pst.setString(1, txtPlano.getText());
                pst.setString(2, datos[2]);
                pst.setString(3, "");
                pst.setString(4, "");
                pst.setString(5, datos[5]);
                pst.setString(6, datos[6]);
                pst.setString(7, f);
                pst.setString(8, datos[8]);
                pst.setString(9, txtPlano.getText());

                int n = pst.executeUpdate();

                if (n > 0) {
                    limpiarTabla();
                    verDatos("select Estado, id, plano, proyecto from planos where Estado like 'CORTES' order by id desc");
                    txtProyecto.setText("");
                    txtPlano.setText("");
                    panelPiezas.setVisible(false);
                    JOptionPane.showMessageDialog(this, "PLANO PAUSADO", "INFO", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "ERROR AL PAUSAR " + e, "ERROR", JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(Inicio1.class.getName()).log(Level.SEVERE, null, e);
        }
    }//GEN-LAST:event_btnPausaActionPerformed

    private void btnActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarActionPerformed
        limpiarTabla();
        verDatos("select Estado, id, plano, proyecto from planos where Estado like 'CORTES' order by id desc");
    }//GEN-LAST:event_btnActualizarActionPerformed

    private void btnPedirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPedirActionPerformed
        PedirMaterial p = new PedirMaterial(this, true, numEmpleado);
        if (lblTitulo.getText().equals("PLANOS CON MATERIAL")) {
            p.txtCodigo.setEditable(true);
            p.txtDescripcion.setEditable(true);
            p.txtNumeroEmpleado.setEditable(true);
            p.txtProyecto.setEditable(true);
        } else {
            p.txtCodigo.setEditable(false);
            p.txtDescripcion.setEditable(false);
            p.txtNumeroEmpleado.setEditable(false);
            p.txtProyecto.setEditable(false);
        }
        p.setVisible(true);
    }//GEN-LAST:event_btnPedirActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if (ver == false) {
            ver = true;
            lblTitulo.setText("PLANOS SIN MATERIAL");
            limpiarTabla();
            verDatos("select Estado, id, plano, proyecto from planos where Estado like 'SIN MATERIAL' order by id desc");
        } else {
            ver = false;
            limpiarTabla();
            verDatos("select Estado, id, plano, proyecto from planos where Estado like 'CORTES' order by id desc");
            lblTitulo.setText("PLANOS CON MATERIAL");
        }

    }//GEN-LAST:event_jButton1ActionPerformed

    private void terminarPlanosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_terminarPlanosActionPerformed
        for (int i = 0; i < Tabla1.getSelectedRows().length; i++) {
            int fila = Tabla1.getSelectedRows()[i];
            enviarPlano(Tabla1.getValueAt(fila, 1).toString(), Tabla1.getValueAt(fila, 0).toString());
        }
    }//GEN-LAST:event_terminarPlanosActionPerformed

    private void cmbEnviarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbEnviarActionPerformed
        if (cmbEnviar.getSelectedIndex() == 0) {
            terminarPlanos.setEnabled(false);
        } else {
            terminarPlanos.setEnabled(true);
            terminarPlanos.setText("Enviar planos a: " + cmbEnviar.getSelectedItem().toString() + "                          ");
        }
    }//GEN-LAST:event_cmbEnviarActionPerformed

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        if (jTextField1.getText().equals("")) {
            verDatos("select Estado, id, plano, proyecto from planos where Estado like 'CORTES' order by id desc");
        } else {
            verDatos("select Estado, id, plano, proyecto from planos where Estado like 'CORTES' and Proyecto like '" + jTextField1.getText() + "%' order by id desc");
        }
    }//GEN-LAST:event_jTextField1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable Tabla1;
    private javax.swing.JButton btnActualizar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnPausa;
    private javax.swing.JButton btnPedir;
    private javax.swing.JPanel btnSalir;
    private RSMaterialComponent.RSComboBoxMaterial cmbEnviar;
    private javax.swing.JButton jButton1;
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
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
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
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JLabel lblConteo;
    private javax.swing.JLabel lblCrono;
    private javax.swing.JLabel lblCrono1;
    private javax.swing.JLabel lblEstado;
    private javax.swing.JLabel lblPiezas;
    private javax.swing.JLabel lblRevision;
    private javax.swing.JLabel lblTipo;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JPanel middle;
    private javax.swing.JPanel panelCentral;
    private javax.swing.JPanel panelEste;
    private rojeru_san.rspanel.RSPanelRound panelPiezas;
    private rojeru_san.rspanel.RSPanelRound panelTipo;
    private rojeru_san.rspanel.RSPanelRound rSPanelRound1;
    private javax.swing.JMenuItem terminarPlanos;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JTextField txtEmpleado;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtNumero;
    private javax.swing.JLabel txtPlano;
    private javax.swing.JLabel txtProyecto;
    private javax.swing.JTextField txtPuesto;
    // End of variables declaration//GEN-END:variables
}
