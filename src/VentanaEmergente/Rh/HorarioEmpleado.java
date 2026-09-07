package VentanaEmergente.Rh;

import Conexiones.Conexion;
import VentanaEmergente.Cotizacion.ConfUsuario;
import java.awt.event.FocusListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import jdk.internal.org.jline.reader.Parser;

public class HorarioEmpleado extends javax.swing.JDialog {

    private GuardarEmpleados empleados;
    private String inicio;
    public String numEmpleado;

    public String calcularTiempo(String horaInicio, String horaFin) {
        if (horaInicio != null & horaFin != null & !horaInicio.equals("") & !horaFin.equals("")) {
            if (horaInicio.equals("00:01"))
                return "00:01";
            DateTimeFormatter formato = DateTimeFormatter.ofPattern("HH:mm");
            LocalTime inicio = LocalTime.parse(horaInicio, formato);
            LocalTime fin = LocalTime.parse(horaFin, formato);
            Duration duracion = Duration.between(inicio, fin);
            long horas = duracion.toHours();
            long minutos = duracion.toMinutes() % 60;
            return String.format("%02d:%02d", horas, minutos);
        }
        return null;
    }

    public boolean evaluarDia(JTextField txt1, JTextField txt2) {
        return (txt1.getText().equals("") || txt1.getText().equals("00:00"))  && (txt2.getText().equals("") || txt2.getText().equals("00:00"));
    }

    public final void esconderDias() {
        btnVL.setVisible(false);
        btnVM.setVisible(false);
        btnVX.setVisible(false);
        btnVJ.setVisible(false);
        btnVV.setVisible(false);
        btnVS.setVisible(false);
    }

    public final void verHorario(String numEmpleado, String inicio, GuardarEmpleados empleado) {
        try {
            this.empleados = empleado;
            this.inicio = inicio;
            esconderDias();
            txtEntrada.setText(empleados.getEntrada());
            txtSalida.setText(empleados.getSalida());
            txtSabado.setText(empleados.getEntradaSabado());
            txtSSabado.setText(empleados.getSalidaSabado());
            Connection con = new Conexion().getConnection();
            Statement st = con.createStatement();
            String sql = "select * from dias where numEmpleado like '" + numEmpleado + "' and Inicio like '" + inicio + "'";
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                lblId.setText(rs.getString("id"));
                try {
                    el.setText(rs.getString("lunes").substring(0, 5));
                    sl.setText(rs.getString("slunes").substring(0, 5));
                    tl.setText(calcularTiempo(el.getText(), sl.getText()));
                } catch (Exception e) {
                    if (evaluarDia(el, sl)) {
                        btnVL.setVisible(true);
                    }
                }

                try {
                    em.setText(rs.getString("martes").substring(0, 5));
                    sm.setText(rs.getString("smartes").substring(0, 5));
                    tm.setText(calcularTiempo(em.getText(), sm.getText()));
                } catch (Exception e) {
                    if (evaluarDia(em, sm)) {
                        btnVM.setVisible(true);
                    }
                }

                try {
                    ex.setText(rs.getString("miercoles").substring(0, 5));
                    sx.setText(rs.getString("smiercoles").substring(0, 5));
                    tx.setText(calcularTiempo(ex.getText(), sx.getText()));
                } catch (Exception e) {
                    if (evaluarDia(ex, sx)) {
                        btnVX.setVisible(true);
                    }
                }

                try {
                    ej.setText(rs.getString("jueves").substring(0, 5));
                    sj.setText(rs.getString("sjueves").substring(0, 5));
                    tj.setText(calcularTiempo(ej.getText(), sj.getText()));
                } catch (Exception e) {
                    if (evaluarDia(ej, sj)) {
                        btnVJ.setVisible(true);
                    }
                }

                try {
                    ev.setText(rs.getString("viernes").substring(0, 5));
                    sv.setText(rs.getString("sviernes").substring(0, 5));
                    tv.setText(calcularTiempo(ev.getText(), sv.getText()));
                } catch (Exception e) {
                    if (evaluarDia(ev, sv)) {
                        btnVV.setVisible(true);
                    }
                }

                try {
                    es.setText(rs.getString("sabado").substring(0, 5));
                    ss.setText(rs.getString("ssabado").substring(0, 5));
                    ts.setText(calcularTiempo(es.getText(), ss.getText()));
                } catch (Exception e) {
                    if (evaluarDia(es, ss)) {
                        if (!txtSabado.getText().equals("00:00:00"))
                            btnVS.setVisible(true);
                    }
                }

                try {
                    ed.setText(rs.getString("domingo").substring(0, 5));
                    sd.setText(rs.getString("sdomingo").substring(0, 5));
                    td.setText(calcularTiempo(ed.getText(), sd.getText()));
                } catch (Exception e) {
                }

            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al ver horario de empleado: " + e, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public final void agregarFocus() {
        JTextField textos[] = new JTextField[21];
        textos[0] = el;
        textos[1] = sl;
        textos[2] = tl;
        textos[3] = em;
        textos[4] = sm;
        textos[5] = tm;
        textos[6] = ex;
        textos[7] = sx;
        textos[8] = tx;
        textos[9] = ej;
        textos[10] = sj;
        textos[11] = tj;
        textos[12] = ev;
        textos[13] = sv;
        textos[14] = tv;
        textos[15] = es;
        textos[16] = ss;
        textos[17] = ts;
        textos[18] = ed;
        textos[19] = sd;
        textos[20] = td;

        for (int i = 0; i < textos.length; i++) {
            int in = i;
            textos[i].addFocusListener(new java.awt.event.FocusAdapter() {
                public void focusLost(java.awt.event.FocusEvent evt) {
                    String inicio;
                    String fin;
                    if (in % 3 == 0) {
                        inicio = textos[in].getText();
                        fin = textos[in + 1].getText();
                        textos[in + 2].setText(calcularTiempo(inicio, fin));
                    } else if (in % 3 == 1) {
                        inicio = textos[in - 1].getText();
                        fin = textos[in].getText();
                        textos[in + 1].setText(calcularTiempo(inicio, fin));
                    }
                }
            });
        }
    }

    public String formatoCeldasHorario(JTextField txt1) {
        try {
            LocalTime d1 = LocalTime.parse(txt1.getText());
            return txt1.getText().equals("") ? null : txt1.getText();
        } catch (Exception e) {
        }
        return null;
    }
    
    public final void asignarVacaciones(String dia, JTextField txt1, JTextField txt2) {
        try {
            Connection con = new Conexion().getConnection();
            String sql = "insert into vacacionestomadas (NumEmpleado, idDia, dia) values(?,?,?)";
            PreparedStatement pst = con.prepareStatement(sql);
            
            pst.setString(1, numEmpleado);
            pst.setString(2, lblId.getText());
            pst.setString(3, dia);
            
            int n = pst.executeUpdate();
            
            if (n > 0) {
                verGuardado();
                txt1.setText("00:01");
                txt2.setText("00:01");
                actualizarDatos();
            }
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al guardar vacaciones: " + e, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public final void actualizarDatos() {
        try {
            esconderDias();
            Connection con = new Conexion().getConnection();
            String sql = "update dias set lunes = ?, slunes = ?, martes = ?, smartes = ?, miercoles = ?, smiercoles = ?, jueves = ?, "
                    + "sjueves = ?, viernes = ?, sviernes = ?, sabado = ?, ssabado = ?, domingo = ?, sdomingo = ? where numSemana = ? and Inicio = ?";
            PreparedStatement pst = con.prepareStatement(sql);

            pst.setString(1, formatoCeldasHorario(el));
            pst.setString(2, formatoCeldasHorario(sl));
            pst.setString(3, formatoCeldasHorario(em));
            pst.setString(4, formatoCeldasHorario(sm));
            pst.setString(5, formatoCeldasHorario(ex));
            pst.setString(6, formatoCeldasHorario(sx));
            pst.setString(7, formatoCeldasHorario(ej));
            pst.setString(8, formatoCeldasHorario(sj));
            pst.setString(9, formatoCeldasHorario(ev));
            pst.setString(10, formatoCeldasHorario(sv));
            pst.setString(11, formatoCeldasHorario(es));
            pst.setString(12, formatoCeldasHorario(ss));
            pst.setString(13, formatoCeldasHorario(ed));
            pst.setString(14, formatoCeldasHorario(sd));
            pst.setString(15, lblSemana.getText());
            pst.setString(16, this.inicio);

            int n = pst.executeUpdate();

            if (n > 0) {
                verGuardado();
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al actualizar datos: " + e, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public final void verGuardado() {
        Thread hilo = new Thread(() -> {
            try {
                lblguardado.setVisible(true);
                Thread.sleep(2000);
                lblguardado.setVisible(false);
            } catch (InterruptedException ex) {
                lblguardado.setVisible(false);
                Logger.getLogger(ConfUsuario.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        hilo.start();
    }

    public HorarioEmpleado(java.awt.Frame parent, boolean modal, String numEmpleado) {
        super(parent, modal);
        initComponents();
        agregarFocus();
        lblguardado.setVisible(false);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel1 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        lblPeriodo = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        lblSemana = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        lblEmpleado = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        lblId = new javax.swing.JLabel();
        el = new javax.swing.JTextField();
        sl = new javax.swing.JTextField();
        tl = new javax.swing.JTextField();
        em = new javax.swing.JTextField();
        sm = new javax.swing.JTextField();
        tm = new javax.swing.JTextField();
        ex = new javax.swing.JTextField();
        sx = new javax.swing.JTextField();
        tx = new javax.swing.JTextField();
        ej = new javax.swing.JTextField();
        sj = new javax.swing.JTextField();
        tj = new javax.swing.JTextField();
        ev = new javax.swing.JTextField();
        sv = new javax.swing.JTextField();
        tv = new javax.swing.JTextField();
        es = new javax.swing.JTextField();
        ss = new javax.swing.JTextField();
        ts = new javax.swing.JTextField();
        ed = new javax.swing.JTextField();
        sd = new javax.swing.JTextField();
        td = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        txtSalida = new javax.swing.JTextField();
        txtSabado = new javax.swing.JTextField();
        txtSSabado = new javax.swing.JTextField();
        txtEntrada = new javax.swing.JTextField();
        lblguardado = new javax.swing.JLabel();
        btnVL = new javax.swing.JButton();
        btnVM = new javax.swing.JButton();
        btnVX = new javax.swing.JButton();
        btnVJ = new javax.swing.JButton();
        btnVS = new javax.swing.JButton();
        btnVV = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new java.awt.BorderLayout());

        jLabel12.setFont(new java.awt.Font("Lexend", 1, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(0, 165, 252));
        jLabel12.setText("                 Editar horario");
        jPanel1.add(jLabel12, java.awt.BorderLayout.NORTH);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new java.awt.GridBagLayout());

        jLabel4.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        jLabel4.setText("Lunes");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.insets = new java.awt.Insets(0, 4, 0, 4);
        jPanel2.add(jLabel4, gridBagConstraints);

        jLabel5.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        jLabel5.setText("Martes");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.insets = new java.awt.Insets(0, 4, 0, 4);
        jPanel2.add(jLabel5, gridBagConstraints);

        jLabel6.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        jLabel6.setText("Miercoles");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.insets = new java.awt.Insets(0, 4, 0, 4);
        jPanel2.add(jLabel6, gridBagConstraints);

        jLabel7.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        jLabel7.setText("Jueves");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 9;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.insets = new java.awt.Insets(0, 4, 0, 4);
        jPanel2.add(jLabel7, gridBagConstraints);

        jLabel8.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        jLabel8.setText("Salida sabado");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 9;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.insets = new java.awt.Insets(0, 4, 0, 4);
        jPanel2.add(jLabel8, gridBagConstraints);

        jLabel9.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        jLabel9.setText("Sabado");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.insets = new java.awt.Insets(0, 4, 0, 4);
        jPanel2.add(jLabel9, gridBagConstraints);

        jLabel10.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        jLabel10.setText("Domingo");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.insets = new java.awt.Insets(0, 4, 0, 4);
        jPanel2.add(jLabel10, gridBagConstraints);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setLayout(new java.awt.GridBagLayout());

        jLabel1.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        jLabel1.setText("Periodo:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 10);
        jPanel3.add(jLabel1, gridBagConstraints);

        lblPeriodo.setFont(new java.awt.Font("Trebuchet MS", 0, 14)); // NOI18N
        lblPeriodo.setText("periodo");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        jPanel3.add(lblPeriodo, gridBagConstraints);

        jLabel3.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        jLabel3.setText("Semana:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 10);
        jPanel3.add(jLabel3, gridBagConstraints);

        lblSemana.setFont(new java.awt.Font("Trebuchet MS", 0, 14)); // NOI18N
        lblSemana.setText("semana");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        jPanel3.add(lblSemana, gridBagConstraints);

        jLabel11.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        jLabel11.setText("Empleado:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 10);
        jPanel3.add(jLabel11, gridBagConstraints);

        lblEmpleado.setFont(new java.awt.Font("Trebuchet MS", 0, 14)); // NOI18N
        lblEmpleado.setText("empleado");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        jPanel3.add(lblEmpleado, gridBagConstraints);

        jLabel17.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        jLabel17.setText("Id:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 10);
        jPanel3.add(jLabel17, gridBagConstraints);

        lblId.setFont(new java.awt.Font("Trebuchet MS", 0, 14)); // NOI18N
        lblId.setText("empleado");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        jPanel3.add(lblId, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 19;
        jPanel2.add(jPanel3, gridBagConstraints);

        el.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                svFocusLost(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.ipadx = -20;
        gridBagConstraints.insets = new java.awt.Insets(0, 2, 0, 2);
        jPanel2.add(el, gridBagConstraints);

        sl.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                svFocusLost(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 2;
        gridBagConstraints.ipadx = -20;
        gridBagConstraints.insets = new java.awt.Insets(0, 2, 0, 2);
        jPanel2.add(sl, gridBagConstraints);

        tl.setEditable(false);
        tl.setBackground(new java.awt.Color(204, 102, 0));
        tl.setFont(new java.awt.Font("Trebuchet MS", 1, 12)); // NOI18N
        tl.setForeground(new java.awt.Color(255, 255, 255));
        tl.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 2;
        gridBagConstraints.ipadx = -10;
        gridBagConstraints.insets = new java.awt.Insets(0, 2, 0, 2);
        jPanel2.add(tl, gridBagConstraints);

        em.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                svFocusLost(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 2;
        gridBagConstraints.ipadx = -20;
        gridBagConstraints.insets = new java.awt.Insets(0, 2, 0, 2);
        jPanel2.add(em, gridBagConstraints);

        sm.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                svFocusLost(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 2;
        gridBagConstraints.ipadx = -20;
        gridBagConstraints.insets = new java.awt.Insets(0, 2, 0, 2);
        jPanel2.add(sm, gridBagConstraints);

        tm.setEditable(false);
        tm.setBackground(new java.awt.Color(204, 102, 0));
        tm.setFont(new java.awt.Font("Trebuchet MS", 1, 12)); // NOI18N
        tm.setForeground(new java.awt.Color(255, 255, 255));
        tm.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 2;
        gridBagConstraints.ipadx = -10;
        gridBagConstraints.insets = new java.awt.Insets(0, 2, 0, 2);
        jPanel2.add(tm, gridBagConstraints);

        ex.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                svFocusLost(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 2;
        gridBagConstraints.ipadx = -20;
        gridBagConstraints.insets = new java.awt.Insets(0, 2, 0, 2);
        jPanel2.add(ex, gridBagConstraints);

        sx.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                svFocusLost(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 2;
        gridBagConstraints.ipadx = -20;
        gridBagConstraints.insets = new java.awt.Insets(0, 2, 0, 2);
        jPanel2.add(sx, gridBagConstraints);

        tx.setEditable(false);
        tx.setBackground(new java.awt.Color(204, 102, 0));
        tx.setFont(new java.awt.Font("Trebuchet MS", 1, 12)); // NOI18N
        tx.setForeground(new java.awt.Color(255, 255, 255));
        tx.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 2;
        gridBagConstraints.ipadx = -10;
        gridBagConstraints.insets = new java.awt.Insets(0, 2, 0, 2);
        jPanel2.add(tx, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 2;
        gridBagConstraints.ipadx = -20;
        gridBagConstraints.insets = new java.awt.Insets(0, 2, 0, 2);
        jPanel2.add(ej, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 2;
        gridBagConstraints.ipadx = -20;
        gridBagConstraints.insets = new java.awt.Insets(0, 2, 0, 2);
        jPanel2.add(sj, gridBagConstraints);

        tj.setEditable(false);
        tj.setBackground(new java.awt.Color(204, 102, 0));
        tj.setFont(new java.awt.Font("Trebuchet MS", 1, 12)); // NOI18N
        tj.setForeground(new java.awt.Color(255, 255, 255));
        tj.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 2;
        gridBagConstraints.ipadx = -10;
        gridBagConstraints.insets = new java.awt.Insets(0, 2, 0, 2);
        jPanel2.add(tj, gridBagConstraints);

        ev.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                svFocusLost(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.ipadx = -20;
        gridBagConstraints.insets = new java.awt.Insets(0, 2, 0, 2);
        jPanel2.add(ev, gridBagConstraints);

        sv.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                svFocusLost(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.ipadx = -20;
        gridBagConstraints.insets = new java.awt.Insets(0, 2, 0, 2);
        jPanel2.add(sv, gridBagConstraints);

        tv.setEditable(false);
        tv.setBackground(new java.awt.Color(204, 102, 0));
        tv.setFont(new java.awt.Font("Trebuchet MS", 1, 12)); // NOI18N
        tv.setForeground(new java.awt.Color(255, 255, 255));
        tv.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.ipadx = -10;
        gridBagConstraints.insets = new java.awt.Insets(0, 2, 0, 2);
        jPanel2.add(tv, gridBagConstraints);

        es.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                svFocusLost(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.ipadx = -20;
        gridBagConstraints.insets = new java.awt.Insets(0, 2, 0, 2);
        jPanel2.add(es, gridBagConstraints);

        ss.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                svFocusLost(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.ipadx = -20;
        gridBagConstraints.insets = new java.awt.Insets(0, 2, 0, 2);
        jPanel2.add(ss, gridBagConstraints);

        ts.setEditable(false);
        ts.setBackground(new java.awt.Color(204, 102, 0));
        ts.setFont(new java.awt.Font("Trebuchet MS", 1, 12)); // NOI18N
        ts.setForeground(new java.awt.Color(255, 255, 255));
        ts.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.ipadx = -10;
        gridBagConstraints.insets = new java.awt.Insets(0, 2, 0, 2);
        jPanel2.add(ts, gridBagConstraints);

        ed.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                svFocusLost(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.ipadx = -20;
        gridBagConstraints.insets = new java.awt.Insets(0, 2, 0, 2);
        jPanel2.add(ed, gridBagConstraints);

        sd.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                svFocusLost(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.ipadx = -20;
        gridBagConstraints.insets = new java.awt.Insets(0, 2, 0, 2);
        jPanel2.add(sd, gridBagConstraints);

        td.setEditable(false);
        td.setBackground(new java.awt.Color(204, 102, 0));
        td.setFont(new java.awt.Font("Trebuchet MS", 1, 12)); // NOI18N
        td.setForeground(new java.awt.Color(255, 255, 255));
        td.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.ipadx = -10;
        gridBagConstraints.insets = new java.awt.Insets(0, 2, 0, 2);
        jPanel2.add(td, gridBagConstraints);

        jLabel13.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        jLabel13.setText("Viernes");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.insets = new java.awt.Insets(0, 4, 0, 4);
        jPanel2.add(jLabel13, gridBagConstraints);

        jLabel14.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        jLabel14.setText("Sabado");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.insets = new java.awt.Insets(0, 4, 0, 4);
        jPanel2.add(jLabel14, gridBagConstraints);

        jLabel15.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        jLabel15.setText("Entrada");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.insets = new java.awt.Insets(0, 4, 0, 4);
        jPanel2.add(jLabel15, gridBagConstraints);

        jLabel16.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        jLabel16.setText("Salida");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.insets = new java.awt.Insets(0, 4, 0, 4);
        jPanel2.add(jLabel16, gridBagConstraints);

        txtSalida.setFont(new java.awt.Font("Trebuchet MS", 1, 12)); // NOI18N
        txtSalida.setEnabled(false);
        txtSalida.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtSalidasvFocusLost(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 13, 0, 13);
        jPanel2.add(txtSalida, gridBagConstraints);

        txtSabado.setFont(new java.awt.Font("Trebuchet MS", 1, 12)); // NOI18N
        txtSabado.setEnabled(false);
        txtSabado.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtSabadosvFocusLost(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 13, 0, 13);
        jPanel2.add(txtSabado, gridBagConstraints);

        txtSSabado.setFont(new java.awt.Font("Trebuchet MS", 1, 12)); // NOI18N
        txtSSabado.setEnabled(false);
        txtSSabado.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtSSabadosvFocusLost(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 9;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 13, 0, 13);
        jPanel2.add(txtSSabado, gridBagConstraints);

        txtEntrada.setFont(new java.awt.Font("Trebuchet MS", 1, 12)); // NOI18N
        txtEntrada.setEnabled(false);
        txtEntrada.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtEntradasvFocusLost(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 13, 0, 13);
        jPanel2.add(txtEntrada, gridBagConstraints);

        lblguardado.setFont(new java.awt.Font("Trebuchet MS", 1, 12)); // NOI18N
        lblguardado.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IconoC/cheque_16.png"))); // NOI18N
        lblguardado.setText("Datos guardados correctamente");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridwidth = 12;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.PAGE_END;
        jPanel2.add(lblguardado, gridBagConstraints);

        btnVL.setBackground(new java.awt.Color(255, 255, 255));
        btnVL.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/add.png"))); // NOI18N
        btnVL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVLActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        jPanel2.add(btnVL, gridBagConstraints);

        btnVM.setBackground(new java.awt.Color(255, 255, 255));
        btnVM.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/add.png"))); // NOI18N
        btnVM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVMActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 1;
        jPanel2.add(btnVM, gridBagConstraints);

        btnVX.setBackground(new java.awt.Color(255, 255, 255));
        btnVX.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/add.png"))); // NOI18N
        btnVX.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVXActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 1;
        jPanel2.add(btnVX, gridBagConstraints);

        btnVJ.setBackground(new java.awt.Color(255, 255, 255));
        btnVJ.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/add.png"))); // NOI18N
        btnVJ.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVJActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 11;
        gridBagConstraints.gridy = 1;
        jPanel2.add(btnVJ, gridBagConstraints);

        btnVS.setBackground(new java.awt.Color(255, 255, 255));
        btnVS.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/add.png"))); // NOI18N
        btnVS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVSActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 3;
        jPanel2.add(btnVS, gridBagConstraints);

        btnVV.setBackground(new java.awt.Color(255, 255, 255));
        btnVV.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/add.png"))); // NOI18N
        btnVV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVVActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        jPanel2.add(btnVV, gridBagConstraints);

        jPanel1.add(jPanel2, java.awt.BorderLayout.CENTER);

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        jButton1.setBackground(new java.awt.Color(0, 153, 255));
        jButton1.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Guardar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel4.add(jButton1);

        jPanel1.add(jPanel4, java.awt.BorderLayout.PAGE_END);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void svFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_svFocusLost
//        calcularTiempo(horaInicio, horaFin)
    }//GEN-LAST:event_svFocusLost

    private void txtSalidasvFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtSalidasvFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSalidasvFocusLost

    private void txtSabadosvFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtSabadosvFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSabadosvFocusLost

    private void txtSSabadosvFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtSSabadosvFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSSabadosvFocusLost

    private void txtEntradasvFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtEntradasvFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtEntradasvFocusLost

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        actualizarDatos();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void btnVLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVLActionPerformed
        asignarVacaciones("1", el, sl);
    }//GEN-LAST:event_btnVLActionPerformed

    private void btnVMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVMActionPerformed
        asignarVacaciones("2", em, sm);
    }//GEN-LAST:event_btnVMActionPerformed

    private void btnVXActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVXActionPerformed
        asignarVacaciones("3", ex, sx);
    }//GEN-LAST:event_btnVXActionPerformed

    private void btnVJActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVJActionPerformed
        asignarVacaciones("4", ej, sj);
    }//GEN-LAST:event_btnVJActionPerformed

    private void btnVSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVSActionPerformed
        asignarVacaciones("6", es, ss);
    }//GEN-LAST:event_btnVSActionPerformed

    private void btnVVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVVActionPerformed
        asignarVacaciones("5", ev, sv);
    }//GEN-LAST:event_btnVVActionPerformed

    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                HorarioEmpleado dialog = new HorarioEmpleado(new javax.swing.JFrame(), true, "");
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
    private javax.swing.JButton btnVJ;
    private javax.swing.JButton btnVL;
    private javax.swing.JButton btnVM;
    private javax.swing.JButton btnVS;
    private javax.swing.JButton btnVV;
    private javax.swing.JButton btnVX;
    private javax.swing.JTextField ed;
    private javax.swing.JTextField ej;
    private javax.swing.JTextField el;
    private javax.swing.JTextField em;
    private javax.swing.JTextField es;
    private javax.swing.JTextField ev;
    private javax.swing.JTextField ex;
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
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    public javax.swing.JLabel lblEmpleado;
    public javax.swing.JLabel lblId;
    public javax.swing.JLabel lblPeriodo;
    public javax.swing.JLabel lblSemana;
    private javax.swing.JLabel lblguardado;
    private javax.swing.JTextField sd;
    private javax.swing.JTextField sj;
    private javax.swing.JTextField sl;
    private javax.swing.JTextField sm;
    private javax.swing.JTextField ss;
    private javax.swing.JTextField sv;
    private javax.swing.JTextField sx;
    private javax.swing.JTextField td;
    private javax.swing.JTextField tj;
    private javax.swing.JTextField tl;
    private javax.swing.JTextField tm;
    private javax.swing.JTextField ts;
    private javax.swing.JTextField tv;
    private javax.swing.JTextField tx;
    public javax.swing.JTextField txtEntrada;
    public javax.swing.JTextField txtSSabado;
    public javax.swing.JTextField txtSabado;
    public javax.swing.JTextField txtSalida;
    // End of variables declaration//GEN-END:variables
}
