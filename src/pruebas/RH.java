package pruebas;

import Conexiones.Conexion;
import VentanaEmergente.Inicio1.Espera;
import VentanaEmergente.Rh.EditarEmpleado;
import VentanaEmergente.Rh.GuardarEmpleados;
import VentanaEmergente.Rh.HorarioEmpleado;
import VentanaEmergente.Rh.Semanas;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Stack;
import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

public class RH extends javax.swing.JInternalFrame implements ActionListener {

    public String numEmpleado;
    public Stack<Semanas> semanas;
    private HashMap<String, GuardarEmpleados> empleados;
    private String deducciones;
    private String extra;

    public final void verEmpleado() {
        try {
            Connection con = new Conexion().getConnection();
            Statement st = con.createStatement();
            String sql = "select * from empleadoscheck";
            ResultSet rs = st.executeQuery(sql);
            empleados = new HashMap<>();
            while (rs.next()) {
                String numero = rs.getString("NumEmpleado");
                String nombre = rs.getString("Nombre");
                String supervisor = rs.getString("NumSupervisor");
                String entrada = rs.getString("Entrada");
                String salida = rs.getString("Salida");
                String turno = rs.getString("Turno");
                String departamento = rs.getString("Departamento");
                String admin = rs.getString("Administrador");
                String entradaSabado = rs.getString("EntradaSabado");
                String horaDoble = rs.getString("horadoble");
                String horaTriple = rs.getString("horatriple");
                String salidaSabado = rs.getString("salidaSabado");
                String horasDiarias = rs.getString("horasDiarias");
                String totalHoras = rs.getString("totalHoras");
                empleados.put(numero, new GuardarEmpleados(numero, nombre, supervisor, entrada, salida,
                        turno, departamento, admin, entradaSabado, horaDoble, horaTriple, salidaSabado, horasDiarias,
                        totalHoras));
            }
            Statement st2 = con.createStatement();
            String sql2 = "select * from registroempleados where Supervisor is not null";
            ResultSet rs2 = st2.executeQuery(sql2);
            HashMap<String, String> supervisores = new HashMap<>();
            while (rs2.next()) {
                supervisores.put(rs2.getString("NumEmpleado"), rs2.getString("Nombre") + " " + rs2.getString("Apellido"));
            }
            for (String numEmpleado : empleados.keySet()) {
                GuardarEmpleados empleado = empleados.get(numEmpleado);
                String num = empleado.getNombreSupervisor();
                if (supervisores.containsKey(num)) {
                    empleados.get(numEmpleado).setNombreSupervisor(supervisores.get(num));
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al ver datos de empleado: " + ex, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public String retraso(String entrada, String hora, String anterior, int numeroDia) {
        if (entrada != null && hora != null) {
            LocalTime horaInicio = LocalTime.parse(entrada);
            LocalTime horaFin = LocalTime.parse(hora);
            long minutosTotales = ChronoUnit.MINUTES.between(horaInicio, horaFin);
            if (minutosTotales > 0) {
                long horas = minutosTotales / 60;
                long minutos = minutosTotales % 60;
                String res = String.format("%02d:%02d", horas, minutos);
                if (!anterior.equals("")) {
                    LocalTime ant = LocalTime.parse(anterior);
                    LocalTime nuevo = LocalTime.parse(res);
                    int totalMinutos = ant.getHour() * 60 + ant.getMinute() + nuevo.getHour() * 60 + nuevo.getMinute();
                    horas = totalMinutos / 60;
                    minutos = totalMinutos % 60;
                    return String.format("%02d:%02d", horas, minutos);
                }
                return res;
            }
        }
        return anterior;
    }

    public String over(String entrada, String hora, String anterior, int numeroDia) {
        try {
            if (entrada == null || entrada.isBlank() || hora == null || hora.isBlank()) {
                return anterior;
            }
            LocalTime horaInicio = LocalTime.parse(entrada);
            LocalTime horaFin = LocalTime.parse(hora);
            if (horaFin.isBefore(horaInicio)) {
                return anterior;
            }
            long minutosTotales = ChronoUnit.MINUTES.between(horaInicio, horaFin);

            if (minutosTotales <= 0) {
                return anterior;
            }
            long horas = minutosTotales / 60;
            long minutos = minutosTotales % 60;
            if (anterior != null && !anterior.isBlank()) {
                String[] partes = anterior.split(":");
                long horasAnteriores = Long.parseLong(partes[0]);
                long minutosAnteriores = Long.parseLong(partes[1]);
                long totalMinutos = (horasAnteriores * 60) + minutosAnteriores + minutosTotales;
                horas = totalMinutos / 60;
                minutos = totalMinutos % 60;
            }
            return String.format("%02d:%02d", horas, minutos);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al calcular tiempo: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return anterior;
        }
    }

    public String[] analizarEmpleado(String numSemana, String numEmpleado, GuardarEmpleados empleado, Connection con) throws SQLException {
        Statement st = con.createStatement();
        String sql = "select * from dias where Inicio = (select max(Inicio) from dias where NumSemana like '" + numSemana + "') and NumEmpleado like '" + numEmpleado + "' ORDER BY ID DESC";
        ResultSet rs = st.executeQuery(sql);
        String entrada = empleado.getEntrada();
        String salida = empleado.getSalida();
        String retraso = "00:00";
        String over = "00:00";
        while (rs.next()) {
            String lunes = rs.getString("lunes");
            try {
                retraso = retraso(entrada, lunes, retraso, 0);
            } catch (Exception e) {
            }
            String martes = rs.getString("martes");
            try {
                retraso = retraso(entrada, martes, retraso, 1);
            } catch (Exception e) {
            }
            String miercoles = rs.getString("miercoles");
            try {
                retraso = retraso(entrada, miercoles, retraso, 2);
            } catch (Exception e) {
            }
            String jueves = rs.getString("jueves");
            try {
                retraso = retraso(entrada, jueves, retraso, 3);
            } catch (Exception e) {
            }
            String viernes = rs.getString("viernes");
            try {
                retraso = retraso(entrada, viernes, retraso, 4);
            } catch (Exception e) {
            }
            String sabado = rs.getString("sabado");
            try {
                retraso = retraso(entrada, sabado, retraso, 5);
            } catch (Exception e) {
            }
            String domingo = rs.getString("domingo");
            try {
                retraso = retraso(entrada, domingo, retraso, 6);
            } catch (Exception e) {
            }
            String slunes = rs.getString("slunes");
            try {
                over = over(salida, slunes, over, 0);
                if (slunes == null || lunes == null) {
                    retraso = retraso(entrada, salida, retraso, 0);
                    empleado.setFaltas("lunes\n");
                }
            } catch (Exception e) {
            }
            String smartes = rs.getString("smartes");
            try {
                over = over(salida, smartes, over, 1);
                if (smartes == null || martes == null) {
                    retraso = retraso(entrada, salida, retraso, 1);
                    empleado.setFaltas("martes\n");
                }
            } catch (Exception e) {
            }
            String smiercoles = rs.getString("smiercoles");
            try {
                over = over(salida, smiercoles, over, 2);
                if (smiercoles == null || miercoles == null) {
                    retraso = retraso(entrada, salida, retraso, 2);
                    empleado.setFaltas("miercoles\n");
                }
            } catch (Exception e) {
            }
            String sjueves = rs.getString("sjueves");
            try {
                over = over(salida, sjueves, over, 3);
                if (sjueves == null || jueves == null) {
                    retraso = retraso(entrada, salida, retraso, 3);
                    empleado.setFaltas("jueves\n");
                }
            } catch (Exception e) {
            }
            String sviernes = rs.getString("sviernes");
            try {
                over = over(salida, sviernes, over, 4);
                if (sviernes == null || viernes == null) {
                    retraso = retraso(entrada, salida, retraso, 4);
                    empleado.setFaltas("viernes\n");
                }
            } catch (Exception e) {
            }
            String ssabado = rs.getString("ssabado");
            if (empleados.get(numEmpleado).getEntradaSabado().equals("00:00:00")) {
                over = over(sabado, ssabado, over, 5);
            } else {
                try {
                    over = over(salida, ssabado, over, 5);
                    if (ssabado == null || sabado == null) {
                        retraso = retraso(empleado.getEntradaSabado(), empleado.getSalidaSabado(), retraso, 5);
                        empleado.setFaltas("sabado\n");
                    }
                } catch (Exception e) {
                }
            }
            String sdomingo = rs.getString("sdomingo");
            if (empleados.get(numEmpleado).getEntradaSabado().equals("00:00:00")) {
                over = over(domingo, sdomingo, over, 6);
            } else {
                try {
                    over = over(salida, sdomingo, over, 6);
                } catch (Exception e) {
                }
            }
            return new String[]{retraso.replace("-", ""), over};
        }
        return null;
    }

    public final void limpiarTabla() {
        Tabla1 = new javax.swing.JTable();
        Tabla1.setFont(new java.awt.Font("Roboto", 0, 12));
        Tabla1.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{},
                new String[]{
                    "Nombre", "Numero de empleado", "CURP", "RFC", "NSS", "Fecha de ingreso", "Antigüedad",
                    "Tipo de contrato", "Salario", "Pago", "Estatus", "Editar", "Hora doble", "Hora triple"
                }
        ) {
            Class[] types = new Class[]{
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class,
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class,
                java.lang.Object.class, java.lang.Object.class, java.lang.Boolean.class, java.lang.Object.class,
                java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean[]{
                false, false, false, false, false, false, false, false, false, false, false, true, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
        TableColumn col;
        col = Tabla1.getColumnModel().getColumn(11);
        col.setCellEditor(new editor(Tabla1, this));
        col.setCellRenderer(new renderer(false));

        Tabla1.getTableHeader().setFont(new Font("Roboto", java.awt.Font.BOLD, 12));
        Tabla1.setRowHeight(25);
        jScrollPane1.setViewportView(Tabla1);
    }

    public final void verDatos(boolean todos) {
        try {
            limpiarTabla();
            Connection con = new Conexion().getConnection();
            Statement st = con.createStatement();
            String sql = "select * from empleadoscheck order by NumEmpleado desc";
            if (todos) {
                sql = "select * from empleadoscheck where Nombre like '%" + txtBuscar.getText() + "%' or NumEmpleado like '" + txtBuscar.getText() + "' order by NumEmpleado desc";
            }
            ResultSet rs = st.executeQuery(sql);
            Object datos[] = new Object[15];
            DefaultTableModel miModelo = (DefaultTableModel) Tabla1.getModel();
            //"Nombre", "Numero de empleado", "CURP", "RFC", "NSS", "Fecha de ingreso", "Antigüedad", "Tipo de contrato", "Salario", "Pago", "Estatus"
            while (rs.next()) {
                datos[0] = rs.getString("Nombre");
                datos[1] = rs.getString("NumEmpleado");
                datos[2] = rs.getString("Curp");
                datos[3] = rs.getString("Rfc");
                datos[4] = rs.getString("Nss");
                datos[5] = rs.getString("FechaIngreso");
                datos[6] = rs.getString("Antiguedad");
                datos[7] = rs.getString("Contrato");
                datos[8] = rs.getString("Salario");
                datos[9] = rs.getString("TipoPago");
                datos[10] = rs.getBoolean("Activo");
                datos[12] = rs.getString("HoraDoble");
                datos[13] = rs.getString("HoraTriple");
                miModelo.addRow(datos);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al ver datos de empleados: " + e, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public final void verSemanas() {
        try {
            Connection con = new Conexion().getConnection();
            Statement st = con.createStatement();
            String sql = "select * from semanas order by id desc";
            ResultSet rs = st.executeQuery(sql);
            jcmSemana.removeAllItems();
            semanas = new Stack<>();
            while (rs.next()) {
                String sem = rs.getString("NumSemana");
                LocalDate inicio = rs.getDate("Inicio").toLocalDate();
                Semanas semana = new Semanas(sem, inicio);
                semanas.add(semana);
                jcmSemana.addItem(sem);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al ver semanas: " + e, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public final void limpiarTablaPagos() {
        TablaPagos = new javax.swing.JTable();
        TablaPagos.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{},
                new String[]{
                    "Nombre", "# de empleado", "Pago semanal", "Retardos", "Horas extra", "Total", "Detalles"
                }
        ) {
            boolean[] canEdit = new boolean[]{
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
        TablaPagos.getTableHeader().setFont(new Font("Roboto", java.awt.Font.BOLD, 12));
        TablaPagos.setRowHeight(25);
        TablaPagos.setComponentPopupMenu(jPopupMenu1);

//        TableColumn col;
//        col = TablaPagos.getColumnModel().getColumn(7);
//        col.setCellEditor(new editor(TablaPagos, this));
//        col.setCellRenderer(new renderer(false));
        jScrollPane2.setViewportView(TablaPagos);
    }

    private static BigDecimal convertirHoras(String tiempo) {
        if (tiempo == null || tiempo.trim().isEmpty()) {
            return BigDecimal.ZERO;
        }
        String[] partes = tiempo.trim().split(":");
        int horas = Integer.parseInt(partes[0]);
        int minutos = Integer.parseInt(partes[1]);
        return BigDecimal.valueOf(horas).add(BigDecimal.valueOf(minutos).divide(BigDecimal.valueOf(60), 10, RoundingMode.HALF_UP));
    }

    public BigDecimal calcularPagoSemanal(String salarioStr, String deduccionesStr, String tiempoExtraStr, GuardarEmpleados empleado) {
        BigDecimal horasSemana = new BigDecimal(empleado.getTotalHoras());
        String salarioLimpio = salarioStr.replace("$", "").replace(",", "").trim();
        BigDecimal salario = new BigDecimal(salarioLimpio);
        BigDecimal deduccionDiaria = convertirHoras(deduccionesStr);
        BigDecimal tiempoExtra = convertirHoras(tiempoExtraStr);
        BigDecimal valorHora = salario.divide(horasSemana, 10, RoundingMode.HALF_UP);
        BigDecimal valorDoble = new BigDecimal(empleado.getHoraDoble());
        BigDecimal valorTriple = new BigDecimal(empleado.getHoraTriple());
        BigDecimal descuento = valorHora.multiply(deduccionDiaria);
        BigDecimal horasDobles = tiempoExtra.min(new BigDecimal("9"));
        BigDecimal horasTriples = tiempoExtra.subtract(horasDobles);
        BigDecimal pagoHorasDobles = valorDoble.multiply(horasDobles);
        BigDecimal pagoHorasTriples = valorTriple.multiply(horasTriples);
        BigDecimal pagoExtra = pagoHorasDobles.add(pagoHorasTriples);
        BigDecimal total = salario.subtract(descuento).add(pagoExtra);
        this.deducciones = "-" + descuento.setScale(2, RoundingMode.HALF_UP).toString();
        this.extra = "+" + pagoExtra.setScale(2, RoundingMode.HALF_UP).toString();
        return total.setScale(2, RoundingMode.HALF_UP);
    }

    public final void calcularPagos() {
        Espera espera = new Espera();
        try {
            espera.setVisible(true);
            espera.activar();
            limpiarTablaPagos();
            verEmpleado();
            Connection con = new Conexion().getConnection();
            String sql = "select d.numEmpleado, em.Nombre, em.nombre, em.salario from dias as d "
                    + "inner join empleadoscheck as em on d.numEmpleado = em.numEmpleado "
                    + "where d.NumSemana like '" + jcmSemana.getSelectedItem().toString() + "' "
                    + "and d.inicio like '" + (semanas.get(jcmSemana.getSelectedIndex()).getInicio()) + "' "
                    + "order by d.numEmpleado";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            DefaultTableModel miModelo = (DefaultTableModel) TablaPagos.getModel();
            while (rs.next()) {
                String datos[] = new String[15];
                datos[0] = rs.getString("Nombre");
                datos[1] = rs.getString("NumEmpleado");
                datos[2] = rs.getString("Salario");
                String ana[] = analizarEmpleado(jcmSemana.getSelectedItem().toString(), datos[1], empleados.get(datos[1]), con);
                try {
                    datos[3] = ana[0];
                } catch (Exception e) {
                }
                try {
                    datos[4] = ana[1];
                } catch (Exception e) {
                }
                try {
                    datos[5] = calcularPagoSemanal(datos[2], datos[3], datos[4], empleados.get(datos[1])).toEngineeringString();
                } catch (Exception e) {
//                    System.out.println(e);
                }
                datos[6] = this.extra + this.deducciones;
                this.extra = "";
                this.deducciones = "";
                miModelo.addRow(datos);
            }
            espera.setVisible(false);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al ver datos de pago: " + e, "Error", JOptionPane.ERROR_MESSAGE);
            espera.setVisible(false);
        }
        espera.setVisible(false);
    }

    public final void verDatosEmpleado() {
        JFrame f = (JFrame) JOptionPane.getFrameForComponent(this);
        HorarioEmpleado horario = new HorarioEmpleado(f, true, numEmpleado);
        horario.setLocationRelativeTo(f);
        horario.lblEmpleado.setText((String) TablaPagos.getValueAt(TablaPagos.getSelectedRow(), 0));
        horario.lblPeriodo.setText(txtPeriodo.getText());
        horario.lblSemana.setText(jcmSemana.getSelectedItem().toString());
        horario.verHorario(TablaPagos.getValueAt(TablaPagos.getSelectedRow(), 1).toString(), txtPeriodo.getText().split(" ")[1], empleados.get(TablaPagos.getValueAt(TablaPagos.getSelectedRow(), 1).toString()));
        horario.setVisible(true);
    }

    public static int calcularVacaciones(LocalDate fechaIngreso) {
        LocalDate hoy = LocalDate.now();
        int años = Period.between(fechaIngreso, hoy).getYears();
        if (años < 1) {
            return 0;
        }
        if (años <= 5) {
            return 10 + (años * 2);
        }
        return 20 + ((años - 5) / 5) * 2;
    }

    public final void verVacaciones() {
        try {
            Connection con = new Conexion().getConnection();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al ver vacaciones: " + e, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public RH(String numEmpleado) {
        initComponents();
        ((javax.swing.plaf.basic.BasicInternalFrameUI) this.getUI()).setNorthPane(null);
        this.numEmpleado = numEmpleado;
        limpiarTabla();
        verDatos(false);
        verSemanas();
        limpiarTablaPagos();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPopupMenu1 = new javax.swing.JPopupMenu();
        verEmpleado = new javax.swing.JMenuItem();
        btnVacaciones = new javax.swing.JMenuItem();
        btnFaltas = new javax.swing.JMenuItem();
        jPanel1 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        pan = new javax.swing.JPanel();
        panelSalir = new javax.swing.JPanel();
        lblSalir = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        Tabla1 = new javax.swing.JTable();
        jPanel8 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtBuscar = new javax.swing.JTextField();
        btnBuscar = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtPeriodo = new javax.swing.JTextField();
        btnCalcular = new javax.swing.JButton();
        jcmSemana = new javax.swing.JComboBox<>();
        jScrollPane2 = new javax.swing.JScrollPane();
        TablaPagos = new javax.swing.JTable();
        jPanel11 = new javax.swing.JPanel();
        jButton3 = new javax.swing.JButton();
        jPanel9 = new javax.swing.JPanel();

        verEmpleado.setText("Ver datos de empleado      ");
        verEmpleado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                verEmpleadoActionPerformed(evt);
            }
        });
        jPopupMenu1.add(verEmpleado);

        btnVacaciones.setText("Ver vacaciones de empleado                   ");
        jPopupMenu1.add(btnVacaciones);

        btnFaltas.setText("Ver faltas de empleado");
        btnFaltas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFaltasActionPerformed(evt);
            }
        });
        jPopupMenu1.add(btnFaltas);

        setBorder(null);

        jPanel1.setLayout(new java.awt.BorderLayout());

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setLayout(new java.awt.BorderLayout());

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));

        jLabel12.setFont(new java.awt.Font("Lexend", 1, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(0, 165, 252));
        jLabel12.setText("Recursos Humanos");
        jPanel6.add(jLabel12);

        jPanel5.add(jPanel6);

        jPanel4.add(jPanel5, java.awt.BorderLayout.CENTER);

        pan.setBackground(new java.awt.Color(255, 255, 255));

        panelSalir.setBackground(new java.awt.Color(255, 255, 255));

        lblSalir.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
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

        jTabbedPane1.setBackground(new java.awt.Color(255, 255, 255));
        jTabbedPane1.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setLayout(new java.awt.BorderLayout());

        jScrollPane1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 20));

        Tabla1.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        Tabla1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Nombre", "Numero de empleado", "CURP", "RFC", "NSS", "Fecha de ingreso", "Antigüedad", "Tipo de contrato", "Salario", "Pago", "Estatus", "Hora Doble", "Hora Triple"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, true, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(Tabla1);

        jPanel3.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));
        java.awt.GridBagLayout jPanel8Layout = new java.awt.GridBagLayout();
        jPanel8Layout.columnWeights = new double[] {1.0};
        jPanel8.setLayout(jPanel8Layout);

        jLabel1.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 51, 51));
        jLabel1.setText("Buscar empleado");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 20, 0, 20);
        jPanel8.add(jLabel1, gridBagConstraints);

        txtBuscar.setBackground(new java.awt.Color(255, 255, 255));
        txtBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBuscarActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 20, 0, 10);
        jPanel8.add(txtBuscar, gridBagConstraints);

        btnBuscar.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        btnBuscar.setText("Buscar");
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 20);
        jPanel8.add(btnBuscar, gridBagConstraints);

        jPanel3.add(jPanel8, java.awt.BorderLayout.PAGE_START);

        jTabbedPane1.addTab("Empleados", jPanel3);

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setLayout(new java.awt.BorderLayout());

        jPanel10.setBackground(new java.awt.Color(250, 250, 250));
        java.awt.GridBagLayout jPanel10Layout = new java.awt.GridBagLayout();
        jPanel10Layout.columnWeights = new double[] {1.0, 1.0, 0.0};
        jPanel10.setLayout(jPanel10Layout);

        jLabel2.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(51, 51, 51));
        jLabel2.setText("Periodo");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(0, 19, 0, 19);
        jPanel10.add(jLabel2, gridBagConstraints);

        jLabel3.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(51, 51, 51));
        jLabel3.setText("Semana");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(0, 19, 0, 19);
        jPanel10.add(jLabel3, gridBagConstraints);

        txtPeriodo.setEditable(false);
        txtPeriodo.setBackground(new java.awt.Color(250, 250, 250));
        txtPeriodo.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 19, 0, 19);
        jPanel10.add(txtPeriodo, gridBagConstraints);

        btnCalcular.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        btnCalcular.setText("Calcular");
        btnCalcular.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCalcularActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.insets = new java.awt.Insets(0, 19, 0, 19);
        jPanel10.add(btnCalcular, gridBagConstraints);

        jcmSemana.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        jcmSemana.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jcmSemana.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcmSemanaActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 19, 0, 19);
        jPanel10.add(jcmSemana, gridBagConstraints);

        jPanel7.add(jPanel10, java.awt.BorderLayout.PAGE_START);

        TablaPagos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Nombre", "# de empleado", "Pago semanal", "Deducciones", "Horas extra", "Total", "Detalles"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        TablaPagos.setComponentPopupMenu(jPopupMenu1);
        jScrollPane2.setViewportView(TablaPagos);

        jPanel7.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        jPanel11.setBackground(new java.awt.Color(250, 250, 250));

        jButton3.setText("Imprimir");
        jPanel11.add(jButton3);

        jPanel7.add(jPanel11, java.awt.BorderLayout.PAGE_END);

        jTabbedPane1.addTab("Pagos", jPanel7);

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1027, Short.MAX_VALUE)
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 532, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Historial de pagos", jPanel9);

        jPanel2.add(jTabbedPane1, java.awt.BorderLayout.CENTER);

        jPanel1.add(jPanel2, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

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

    private void txtBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBuscarActionPerformed
        verDatos(true);
    }//GEN-LAST:event_txtBuscarActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        verDatos(false);
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void jcmSemanaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcmSemanaActionPerformed
        if (semanas != null && !semanas.isEmpty()) {
            txtPeriodo.setText(semanas.get(jcmSemana.getSelectedIndex()).toString());
        }
    }//GEN-LAST:event_jcmSemanaActionPerformed

    private void btnCalcularActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCalcularActionPerformed
        calcularPagos();
    }//GEN-LAST:event_btnCalcularActionPerformed

    private void verEmpleadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_verEmpleadoActionPerformed
        verDatosEmpleado();
    }//GEN-LAST:event_verEmpleadoActionPerformed

    private void btnFaltasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFaltasActionPerformed
        System.out.println("------------------------------------");
        System.out.println(empleados.get(TablaPagos.getValueAt(TablaPagos.getSelectedRow(), 1).toString()).getFaltas());
    }//GEN-LAST:event_btnFaltasActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable Tabla1;
    private javax.swing.JTable TablaPagos;
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnCalcular;
    private javax.swing.JMenuItem btnFaltas;
    private javax.swing.JMenuItem btnVacaciones;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JComboBox<String> jcmSemana;
    private javax.swing.JLabel lblSalir;
    private javax.swing.JPanel pan;
    private javax.swing.JPanel panelSalir;
    private javax.swing.JTextField txtBuscar;
    private javax.swing.JTextField txtPeriodo;
    private javax.swing.JMenuItem verEmpleado;
    // End of variables declaration//GEN-END:variables

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    class renderer extends JLabel implements TableCellRenderer {

        boolean isBordered = true;

        public renderer(boolean isBordered) {
            this.isBordered = isBordered;
            setOpaque(true);
        }

        public Component getTableCellRendererComponent(JTable table, Object color, boolean isSelected, boolean hasFocus, int row, int column) {
            JButton boton = new JButton();
            boton.setBackground(new java.awt.Color(255, 255, 255));
            boton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/modificar_16.png")));
//            boton.setBorder(null);
//            boton.setBorderPainted(false);
            boton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
            return boton;
        }
    }

    class editor extends AbstractCellEditor implements TableCellEditor, ActionListener {

        Boolean currentValue;
        JButton button;
        RH cxp;
        protected static final String EDIT = "edit";
        private JTable tabla1;
        int seleccionado;

        public editor(JTable jTable1, RH cxp) {
            button = new JButton();
            button.setActionCommand(EDIT);
            button.addActionListener(this);
            button.setBorderPainted(false);
            this.tabla1 = jTable1;
            this.cxp = cxp;
        }

        public String extraerDatos(Object dato) {
            String string;
            try {
                string = String.valueOf(dato);
            } catch (Exception e) {
                string = "";
            }
            if (string.equals("null")) {
                return "";
            }
            return string;
        }

        public void actionPerformed(ActionEvent e) {
            if (tabla1 == Tabla1) {
                JFrame f = (JFrame) JOptionPane.getFrameForComponent(cxp);
                EditarEmpleado editar = new EditarEmpleado(f, true);
                editar.setLocationRelativeTo(f);
                editar.txtNombre.setText(extraerDatos(tabla1.getValueAt(tabla1.getSelectedRow(), 0)));
                editar.txtEmpleado.setText(extraerDatos(tabla1.getValueAt(tabla1.getSelectedRow(), 1)));
                editar.txtCurp.setText(extraerDatos(tabla1.getValueAt(tabla1.getSelectedRow(), 2)));
                editar.txtRfc.setText(extraerDatos(tabla1.getValueAt(tabla1.getSelectedRow(), 3)));
                editar.txtNss.setText(extraerDatos(tabla1.getValueAt(tabla1.getSelectedRow(), 4)));
                editar.fecha.setText(extraerDatos(tabla1.getValueAt(tabla1.getSelectedRow(), 5)));
                editar.txtAntiguedad.setText(extraerDatos(tabla1.getValueAt(tabla1.getSelectedRow(), 6)));
                editar.jcbContrato.setSelectedItem(extraerDatos(tabla1.getValueAt(tabla1.getSelectedRow(), 7)));
                editar.txtSalario.setText(extraerDatos(tabla1.getValueAt(tabla1.getSelectedRow(), 8)));
                editar.txtHorasDobles.setText(extraerDatos(tabla1.getValueAt(tabla1.getSelectedRow(), 12)));
                editar.txtHorasTriples.setText(extraerDatos(tabla1.getValueAt(tabla1.getSelectedRow(), 13)));
                editar.jcbPeriodicidad.setSelectedItem(extraerDatos(tabla1.getValueAt(tabla1.getSelectedRow(), 9)));
                editar.setVisible(true);
                cxp.limpiarTabla();
                cxp.verDatos(false);
            } else if (tabla1 == TablaPagos) {

            }

        }

        public Object getCellEditorValue() {
            return currentValue;
        }

        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            currentValue = (Boolean) value;
            return button;
        }
    }

}
