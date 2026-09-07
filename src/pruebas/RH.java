package pruebas;

import Conexiones.Conexion;
import Controlador.Configuracion;
import VentanaEmergente.Costos.TablaNominas;
import VentanaEmergente.Inicio1.Espera;
import VentanaEmergente.Rh.EditarEmpleado;
import VentanaEmergente.Rh.GuardarEmpleados;
import VentanaEmergente.Rh.HorarioEmpleado;
import VentanaEmergente.Rh.ReciboNominaPDF;
import VentanaEmergente.Rh.Semanas;
import VentanaEmergente.Rh.Vacaciones;
import VentanaEmergente.Rh.VaciadoNomina;
import java.awt.Color;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
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
    public Stack<Semanas> semanasHistorial;
    private HashMap<String, GuardarEmpleados> empleados;
    private String deducciones = "";
    private String extra = "";

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

                if (anterior != null && !anterior.isEmpty() && !anterior.equals("00:00")) {
                    String[] partesAnt = anterior.split(":");
                    int antHoras = Integer.parseInt(partesAnt[0]);
                    int antMinutos = Integer.parseInt(partesAnt[1]);

                    int totalMinutos = (antHoras * 60 + antMinutos) + (int) minutosTotales;

                    long horasTotales = totalMinutos / 60;
                    long minutosRestantes = totalMinutos % 60;

                    return String.format("%d:%02d", horasTotales, minutosRestantes);
                }
                return res;
            }
        }
        return (anterior != null && !anterior.isEmpty()) ? anterior : "00:00";
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

    public String[] analizarEmpleado(String numSemana, String numEmpleado, GuardarEmpleados empleado, Connection con, String inicio) throws SQLException {
        Statement st = con.createStatement();
        String sql = "select * from dias where "
                + "inicio = '" + inicio + "' and NumEmpleado like '" + numEmpleado + "' ORDER BY ID DESC";
        ResultSet rs = st.executeQuery(sql);
        String entrada = empleado.getEntrada();
        String salida = empleado.getSalida();
        String retraso = "00:00";
        String over = "00:00";
        while (rs.next()) {
            String lunes = rs.getString("lunes");
            try {
                if (lunes == null || !lunes.equals("00:01")) {
                    retraso = retraso(entrada, lunes, retraso, 0);
                }
            } catch (Exception e) {
            }
            String martes = rs.getString("martes");
            try {
                if (martes == null || !martes.equals("00:01")) {
                    retraso = retraso(entrada, martes, retraso, 1);
                }
            } catch (Exception e) {
            }
            String miercoles = rs.getString("miercoles");
            try {
                if (miercoles == null || !miercoles.equals("00:01")) {
                    retraso = retraso(entrada, miercoles, retraso, 2);
                }
            } catch (Exception e) {
            }
            String jueves = rs.getString("jueves");
            try {
                if (jueves == null || !jueves.equals("00:01")) {
                    retraso = retraso(entrada, jueves, retraso, 3);
                }
            } catch (Exception e) {
            }
            String viernes = rs.getString("viernes");
            try {
                if (viernes == null || !viernes.equals("00:01")) {
                    retraso = retraso(entrada, viernes, retraso, 4);
                }
            } catch (Exception e) {
            }
            String sabado = rs.getString("sabado");
            try {
                if (sabado == null || !sabado.equals("00:01")) {
                    retraso = retraso(entrada, sabado, retraso, 5);
                }
            } catch (Exception e) {
            }
            String domingo = rs.getString("domingo");
            try {
                if (domingo == null || !domingo.equals("00:01")) {
                    retraso = retraso(entrada, domingo, retraso, 6);
                }
            } catch (Exception e) {
            }
            String slunes = rs.getString("slunes");
            try {
                if (slunes == null || !slunes.equals("00:01")) {
                    if (slunes == null || lunes == null) {
                        retraso = retraso(entrada, salida, retraso, 0);
                        empleado.setFaltas("lunes\n");
                    }
                    over = over(salida, slunes, over, 0);
                }
            } catch (Exception e) {
            }
            String smartes = rs.getString("smartes");
            try {
                if (smartes == null || !smartes.equals("00:01")) {
                    if (smartes == null || martes == null) {
                        retraso = retraso(entrada, salida, retraso, 1);
                        empleado.setFaltas("martes\n");
                    }
                    over = over(salida, smartes, over, 1);
                }
            } catch (Exception e) {
            }
            String smiercoles = rs.getString("smiercoles");
            try {
                if (smiercoles == null || !smiercoles.equals("00:01")) {
                    if (smiercoles == null || miercoles == null) {
                        retraso = retraso(entrada, salida, retraso, 2);
                        empleado.setFaltas("miercoles\n");
                    }
                    over = over(salida, smiercoles, over, 2);
                }
            } catch (Exception e) {
            }
            String sjueves = rs.getString("sjueves");
            try {
                if (sjueves == null || !sjueves.equals("00:01")) {
                    if (sjueves == null || jueves == null) {
                        retraso = retraso(entrada, salida, retraso, 3);
                        empleado.setFaltas("jueves\n");
                    }
                    over = over(salida, sjueves, over, 3);
                }
            } catch (Exception e) {
            }
            String sviernes = rs.getString("sviernes");
            try {
                if (sviernes == null || !sviernes.equals("00:01")) {
                    if (sviernes == null || viernes == null) {
                        retraso = retraso(entrada, salida, retraso, 4);
                        empleado.setFaltas("viernes\n");
                    }
                    over = over(salida, sviernes, over, 4);
                }
            } catch (Exception e) {
            }
            String ssabado = rs.getString("ssabado");
            if (empleados.get(numEmpleado).getEntradaSabado().equals("00:00:00")) {
                over = over(sabado, ssabado, over, 5);
            } else {
                try {
                    if (ssabado == null || !ssabado.equals("00:01")) {
                        if (ssabado == null || sabado == null) {
                            retraso = retraso(empleado.getEntradaSabado(), empleado.getSalidaSabado(), retraso, 5);
                            empleado.setFaltas("sabado\n");
                        }
                        over = over(salida, ssabado, over, 5);
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

    public final void verSemanas(JComboBox jsem) {
        try {
            Connection con = new Conexion().getConnection();
            Statement st = con.createStatement();
            String sql = "select * from semanas order by id desc";
            ResultSet rs = st.executeQuery(sql);
            jsem.removeAllItems();
            semanas = new Stack<>();
            while (rs.next()) {
                String sem = rs.getString("NumSemana");
                LocalDate inicio = rs.getDate("Inicio").toLocalDate();
                Semanas semana = new Semanas(sem, inicio);
                semanas.add(semana);
                jsem.addItem(sem);
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
                    "Nombre", "# de empleado", "Pago semanal", "Retardos", "Horas extra", "Total", "Detalles", "Vacaciones", "Id"
                }
        ) {
            boolean[] canEdit = new boolean[]{
                false, false, false, false, false, false, false, false, false
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

    public BigDecimal calcularPagoSemanal(String salarioStr, String deduccionesStr, String tiempoExtraStr, GuardarEmpleados empleado, int vacaciones) {
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
        // Prima vacacional
        BigDecimal salarioDiario = salario.divide(new BigDecimal("7"), 10, RoundingMode.HALF_UP);
        BigDecimal porcentajePrima = new BigDecimal("0.25");
        BigDecimal primaVacacional = salarioDiario.multiply(new BigDecimal(vacaciones)).multiply(porcentajePrima);

        BigDecimal total = salario.subtract(descuento).add(pagoExtra).add(primaVacacional);
        this.deducciones = "-" + descuento.setScale(2, RoundingMode.HALF_UP).toString();
        this.extra = "+" + pagoExtra.setScale(2, RoundingMode.HALF_UP).toString();
        if (vacaciones > 0) {
            this.extra += " ++" + primaVacacional.setScale(2, RoundingMode.HALF_UP).toString();
        }
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
            String inicio = (semanas.get(jcmSemana.getSelectedIndex()).getInicio()).toString();
            String sql = "select d.numEmpleado, em.Nombre, em.nombre, em.salario, count(vt.dia), d.id from dias as d "
                    + "inner join empleadoscheck as em on d.numEmpleado = em.numEmpleado "
                    + "left join vacacionestomadas as vt on vt.idDia = d.id "
                    + "where d.NumSemana like '" + jcmSemana.getSelectedItem().toString() + "' "
                    + "and d.inicio like '" + inicio + "' "
                    + "group by d.id, em.nombre, em.salario order by d.numEmpleado";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            DefaultTableModel miModelo = (DefaultTableModel) TablaPagos.getModel();
            while (rs.next()) {
                Object datos[] = new Object[15];
                datos[0] = rs.getString("Nombre");
                datos[1] = rs.getString("NumEmpleado");
                datos[2] = rs.getString("Salario");
                datos[7] = rs.getInt("count(vt.dia)");
                datos[8] = rs.getInt("id");
                String ana[] = analizarEmpleado(jcmSemana.getSelectedItem().toString(), (String) datos[1], empleados.get((String) datos[1]), con, inicio);
                try {
                    datos[3] = ana[0];
                } catch (Exception e) {
                }
                try {
                    datos[4] = ana[1];
                } catch (Exception e) {
                }
                try {
                    datos[5] = calcularPagoSemanal((String) datos[2], (String) datos[3], (String) datos[4], empleados.get((String) datos[1]), (int) datos[7]);
                } catch (Exception e) {
//                    System.out.println(e);
                }
                datos[6] = this.deducciones + " " + this.extra;
                this.extra = "";
                this.deducciones = "";
                miModelo.addRow(datos);
            }
            espera.setVisible(false);
            btnNomina.setEnabled(true);
            btnVaciado.setEnabled(true);
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
        horario.numEmpleado = ((String) TablaPagos.getValueAt(TablaPagos.getSelectedRow(), 1));
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

    public int buscarEnTabla(JTable tabla, String texto, int columna) {
        DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
        for (int fila = 0; fila < modelo.getRowCount(); fila++) {
            Object valor = modelo.getValueAt(fila, columna);
            if (valor != null && valor.toString().equalsIgnoreCase(texto.trim())) {
                tabla.setRowSelectionInterval(fila, fila);
                tabla.scrollRectToVisible(tabla.getCellRect(fila, columna, true));

                return fila;
            }
        }
        tabla.clearSelection();
        JOptionPane.showMessageDialog(this, "No se encontró: " + texto, "Error", JOptionPane.ERROR_MESSAGE);
        return -1;
    }

    public String getAntiguedad(String antiguedad) {
        LocalDate fechaIngreso = LocalDate.parse(antiguedad);
        if (fechaIngreso != null) {
            LocalDate fechaActual = LocalDate.now();
            Period periodo = Period.between(fechaIngreso, fechaActual);
            double anos = periodo.getYears();
            double dias = periodo.getMonths();
            double ant = anos + (dias / 100);
            return (String.format("%.2f", ant));
        }
        return null;
    }

    public String getRuta() {
        JFileChooser selector = new JFileChooser();
        selector.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int resultado = selector.showOpenDialog(this);
        if (resultado == JFileChooser.APPROVE_OPTION) {
            File carpeta = selector.getSelectedFile();
            String ruta = carpeta.getAbsolutePath();
            return ruta;
        }
        return null;
    }

    public final void agregarConfiguracion() {
        try {
            Configuracion conf = new Configuracion();
            txtIndividual.setText(conf.leer("NominaIndividual"));
            txtVaciado.setText(conf.leer("VaciadoNomina"));
        } catch (IOException ex) {
            Logger.getLogger(RH.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public final void guardarHistorialNomina(String numEmpleado, String idDia, String pago, String deducciones, String extra, String total, String detalles, String vacaciones) {
        try {
            Connection con = new Conexion().getConnection();
            String sql = "insert into historialnominas (NumEmpleado, IdDia, PagoSemanal, Deducciones, HorasExtra, Total, Detalles, Vacaciones) values(?,?,?,?,?,?,?,?)";
            PreparedStatement pst = con.prepareStatement(sql);

            pst.setString(1, numEmpleado);
            pst.setString(2, idDia);
            pst.setString(3, pago);
            pst.setString(4, deducciones);
            pst.setString(5, extra);
            pst.setString(6, total);
            pst.setString(7, detalles);
            pst.setString(8, vacaciones);

            int n = pst.executeUpdate();

            if (n < 1) {
                JOptionPane.showMessageDialog(this, "Error al guardar historial de " + numEmpleado);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al guardar historial: " + e, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public final void limpiarTablaNominas() {
        TablaNomina.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{},
                new String[]{
                    "# empleado", "Semana", "Pago Semanal", "Retardos", "Horas extra", "Total", "Detalles", "Vacaciones"
                }
        ));
        TablaNomina.getTableHeader().setFont(new Font("Roboto", java.awt.Font.BOLD, 12));
        TablaNomina.setRowHeight(25);
        jScrollPane3.setViewportView(TablaNomina);
    }

    public final void calcularDouble() {
        double semanal = 0;
        double ded = 0;
        double ext = 0;
        double total = 0;
        double vacaciones = 0;
        for (int i = 0; i < TablaNomina.getRowCount(); i++) {
            String detalles[] = null;
            try {
                detalles = TablaNomina.getValueAt(i, 6).toString().split(" ");
            } catch (Exception e) {
            }
            try {
                semanal += Double.parseDouble(TablaNomina.getValueAt(i, 2).toString());
            } catch (Exception e) {
            }
            try {
                ded += Double.parseDouble(detalles[0].replace("-", ""));
            } catch (Exception e) {
            }
            try {
                ext += Double.parseDouble(detalles[1].replace("+", ""));
            } catch (Exception e) {
            }
            try {
                total += Double.parseDouble(TablaNomina.getValueAt(i, 5).toString());
            } catch (Exception e) {
            }
            try {
                vacaciones += Double.parseDouble(detalles[2].replace("++", ""));
            } catch (Exception e) {
            }
        }
        lblSemanal.setText(String.valueOf(semanal));
        lblRetardos.setText(String.valueOf(ded));
        lblExtra.setText(String.valueOf(ext));
        lblTotal.setText(String.valueOf(total));
        lblVacaciones.setText(String.valueOf(vacaciones));
    }

    public final void verHistorial(String semana) {
        try {
            limpiarTablaNominas();
            Connection con = new Conexion().getConnection();
            String sql = "SELECT h.idnominas, h.numempleado, h.pagosemanal, h.deducciones, h.horasextra, h.total, h.detalles, h.vacaciones, d.NumSemana, d.inicio "
                    + "FROM towi.historialnominas AS h "
                    + "INNER JOIN dias AS d ON h.IdDia = d.id "
                    + "WHERE h.idnominas IN ( SELECT MAX(h2.idnominas) FROM towi.historialnominas AS h2 INNER JOIN dias AS d2 ON h2.IdDia = d2.id "
                    + "where numsemana like '" + semana + "' and inicio like '" + semanas.get(jcbSemanaHistorial.getSelectedIndex()).getInicio() + "' GROUP BY h2.numempleado, d2.NumSemana) "
                    + "ORDER BY d.NumSemana;";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            String datos[] = new String[15];
            DefaultTableModel miModelo = (DefaultTableModel) TablaNomina.getModel();
            while (rs.next()) {
                datos[0] = rs.getString("numempleado");
                datos[1] = rs.getString("numsemana");
                datos[2] = rs.getString("pagosemanal");
                datos[3] = rs.getString("deducciones");
                datos[4] = rs.getString("horasextra");
                datos[5] = rs.getString("total");
                datos[6] = rs.getString("detalles");
                datos[7] = rs.getString("vacaciones");
                miModelo.addRow(datos);
            }
            calcularDouble();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al ver historial: " + e, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public final void guardarNominaEmpleado() {
        try {
            ReciboNominaPDF nomina = new ReciboNominaPDF();
            int row = TablaPagos.getSelectedRow();
            int buscar = buscarEnTabla(Tabla1, TablaPagos.getValueAt(row, 1).toString(), 1);
            Configuracion conf = new Configuracion();
            String fec = txtPeriodo.getText().split(" ")[1];
            String ano = fec.split("-")[0];
            String sem = jcmSemana.getSelectedItem().toString();
            String nombre = (String) Tabla1.getValueAt(buscar, 0);
            String ruta = conf.leer("NominaIndividual") + "\\" + ano + "\\" + sem + "\\nomina individual " + nombre + ".pdf";
            String rfc = (String) Tabla1.getValueAt(buscar, 3);
            String curp = (String) Tabla1.getValueAt(buscar, 2);
            String relacionLaborarl = (String) Tabla1.getValueAt(buscar, 5);
            String nss = (String) Tabla1.getValueAt(buscar, 4);
            String periodo = txtPeriodo.getText();
            String fechaPago = "hoy()";
            String puesto = "Admin";
            String semana = jcmSemana.getSelectedItem().toString();
            String sueldo = (String) TablaPagos.getValueAt(row, 2);
            String empleado = (String) TablaPagos.getValueAt(row, 1);
            String detalles[] = ((String) TablaPagos.getValueAt(row, 6)).split(" ");
            String horasDobles = detalles[1].replace("+", "");
            String horasTriples = "0";
            String faltas = detalles[0].replace("-", "");
            String prima = detalles.length > 2 ? detalles[2].replace("++", "") : "";
            nomina.generarPDF(ruta, nombre, rfc, curp, relacionLaborarl, nss, periodo, fechaPago, puesto, semana, sueldo, horasDobles, horasTriples, faltas, prima, empleado);
            Desktop.getDesktop().open(new File(conf.leer("NominaIndividual") + "\\" + ano + "\\" + sem + "\\nomina individual " + nombre + ".pdf"));
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error al guardar nomina de empleado: " + ex, "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            Logger.getLogger(RH.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public final void guardarHistorial() {
        Configuracion conf = new Configuracion();
        String fec = txtPeriodo.getText().split(" ")[1];
        String ano = fec.split("-")[0];
        String sem = jcmSemana.getSelectedItem().toString();
        for (int i = 0; i < TablaPagos.getRowCount(); i++) {
            try {
                if (!TablaPagos.getValueAt(i, 5).toString().equals("")) {
                    String empleado = (String) TablaPagos.getValueAt(i, 1);
                    guardarHistorialNomina(empleado, formatearTabla(TablaPagos.getValueAt(i, 8)),
                            formatearTabla(TablaPagos.getValueAt(i, 2)), formatearTabla(TablaPagos.getValueAt(i, 3)),
                            formatearTabla(TablaPagos.getValueAt(i, 4)), formatearTabla(TablaPagos.getValueAt(i, 5)),
                            formatearTabla(TablaPagos.getValueAt(i, 6)), formatearTabla(TablaPagos.getValueAt(i, 7)));
                }
            } catch (Exception e) {
            }
        }
        try {
            Desktop.getDesktop().open(new File(conf.leer("NominaIndividual") + "\\" + ano + "\\" + sem));
        } catch (IOException ex) {
            Logger.getLogger(RH.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public RH(String numEmpleado) {
        initComponents();
        ((javax.swing.plaf.basic.BasicInternalFrameUI) this.getUI()).setNorthPane(null);
        this.numEmpleado = numEmpleado;
        limpiarTabla();
        verDatos(false);
        verSemanas(jcmSemana);
        verSemanas(jcbSemanaHistorial);
        limpiarTablaPagos();
        limpiarTablaNominas();
        agregarConfiguracion();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPopupMenu1 = new javax.swing.JPopupMenu();
        verEmpleado = new javax.swing.JMenuItem();
        btnVacaciones = new javax.swing.JMenuItem();
        btnFaltas = new javax.swing.JMenuItem();
        btnNominaEmpleado = new javax.swing.JMenuItem();
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
        btnNomina = new javax.swing.JButton();
        btnVaciado = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();
        jPanel9 = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jcbSemanaHistorial = new javax.swing.JComboBox<>();
        jButton3 = new javax.swing.JButton();
        jPanel14 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        TablaNomina = new javax.swing.JTable();
        jPanel15 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        lblSemanal = new javax.swing.JLabel();
        lblRetardos = new javax.swing.JLabel();
        lblExtra = new javax.swing.JLabel();
        lblTotal = new javax.swing.JLabel();
        lblDetalles = new javax.swing.JLabel();
        lblVacaciones = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        txtVaciado = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        txtIndividual = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();

        verEmpleado.setText("Ver datos de empleado      ");
        verEmpleado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                verEmpleadoActionPerformed(evt);
            }
        });
        jPopupMenu1.add(verEmpleado);

        btnVacaciones.setText("Ver vacaciones de empleado                   ");
        btnVacaciones.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVacacionesActionPerformed(evt);
            }
        });
        jPopupMenu1.add(btnVacaciones);

        btnFaltas.setText("Ver faltas de empleado");
        btnFaltas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFaltasActionPerformed(evt);
            }
        });
        jPopupMenu1.add(btnFaltas);

        btnNominaEmpleado.setText("Guardar nomina de empleado");
        btnNominaEmpleado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNominaEmpleadoActionPerformed(evt);
            }
        });
        jPopupMenu1.add(btnNominaEmpleado);

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
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Nombre", "# de empleado", "Pago semanal", "Deducciones", "Horas extra", "Total", "Detalles", "Vacaciones"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, true, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        TablaPagos.setComponentPopupMenu(jPopupMenu1);
        jScrollPane2.setViewportView(TablaPagos);

        jPanel7.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        jPanel11.setBackground(new java.awt.Color(250, 250, 250));

        btnNomina.setBackground(new java.awt.Color(0, 102, 204));
        btnNomina.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        btnNomina.setForeground(new java.awt.Color(255, 255, 255));
        btnNomina.setText("Imprimir nomina");
        btnNomina.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNominaActionPerformed(evt);
            }
        });
        jPanel11.add(btnNomina);

        btnVaciado.setBackground(new java.awt.Color(255, 51, 51));
        btnVaciado.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        btnVaciado.setForeground(new java.awt.Color(255, 255, 255));
        btnVaciado.setText("Imprimir vaciado nomina");
        btnVaciado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVaciadoActionPerformed(evt);
            }
        });
        jPanel11.add(btnVaciado);

        btnGuardar.setBackground(new java.awt.Color(0, 153, 0));
        btnGuardar.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        btnGuardar.setForeground(new java.awt.Color(255, 255, 255));
        btnGuardar.setText("Guardar historial de pago");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });
        jPanel11.add(btnGuardar);

        jPanel7.add(jPanel11, java.awt.BorderLayout.PAGE_END);

        jTabbedPane1.addTab("Pagos", jPanel7);

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));
        jPanel9.setLayout(new java.awt.BorderLayout());

        jPanel13.setBackground(new java.awt.Color(255, 255, 255));

        jLabel6.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(51, 51, 51));
        jLabel6.setText("Ver historial de pagos nomina");
        jPanel13.add(jLabel6);

        jcbSemanaHistorial.setBackground(new java.awt.Color(255, 255, 255));
        jcbSemanaHistorial.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        jcbSemanaHistorial.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccionar Semana" }));
        jPanel13.add(jcbSemanaHistorial);

        jButton3.setBackground(new java.awt.Color(0, 102, 255));
        jButton3.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setText("Buscar");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel13.add(jButton3);

        jPanel9.add(jPanel13, java.awt.BorderLayout.PAGE_START);

        jPanel14.setBackground(new java.awt.Color(255, 255, 255));
        jPanel14.setLayout(new java.awt.BorderLayout());

        TablaNomina.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "# empleado", "Semana", "Pago Semanal", "Retardos", "Horas extra", "Total", "Detalles", "Vacaciones"
            }
        ));
        jScrollPane3.setViewportView(TablaNomina);

        jPanel14.add(jScrollPane3, java.awt.BorderLayout.CENTER);

        jPanel15.setBackground(new java.awt.Color(255, 255, 255));
        java.awt.GridBagLayout jPanel15Layout = new java.awt.GridBagLayout();
        jPanel15Layout.columnWeights = new double[] {1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0};
        jPanel15.setLayout(jPanel15Layout);

        jLabel7.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(51, 51, 51));
        jLabel7.setText("Total:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = 2;
        jPanel15.add(jLabel7, gridBagConstraints);

        lblSemanal.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        lblSemanal.setForeground(new java.awt.Color(51, 51, 51));
        jPanel15.add(lblSemanal, new java.awt.GridBagConstraints());

        lblRetardos.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        lblRetardos.setForeground(new java.awt.Color(51, 51, 51));
        jPanel15.add(lblRetardos, new java.awt.GridBagConstraints());

        lblExtra.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        lblExtra.setForeground(new java.awt.Color(51, 51, 51));
        jPanel15.add(lblExtra, new java.awt.GridBagConstraints());

        lblTotal.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        lblTotal.setForeground(new java.awt.Color(51, 51, 51));
        jPanel15.add(lblTotal, new java.awt.GridBagConstraints());

        lblDetalles.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        lblDetalles.setForeground(new java.awt.Color(51, 51, 51));
        jPanel15.add(lblDetalles, new java.awt.GridBagConstraints());

        lblVacaciones.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        lblVacaciones.setForeground(new java.awt.Color(51, 51, 51));
        jPanel15.add(lblVacaciones, new java.awt.GridBagConstraints());

        jPanel14.add(jPanel15, java.awt.BorderLayout.PAGE_END);

        jPanel9.add(jPanel14, java.awt.BorderLayout.CENTER);

        jTabbedPane1.addTab("Historial de pagos", jPanel9);

        jPanel12.setBackground(new java.awt.Color(255, 255, 255));
        java.awt.GridBagLayout jPanel12Layout = new java.awt.GridBagLayout();
        jPanel12Layout.columnWeights = new double[] {0.0, 1.0, 0.0};
        jPanel12.setLayout(jPanel12Layout);

        jLabel4.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel4.setText("Ruta vaciado nomina: ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(20, 50, 0, 0);
        jPanel12.add(jLabel4, gridBagConstraints);

        txtVaciado.setBackground(new java.awt.Color(255, 255, 255));
        txtVaciado.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(20, 0, 0, 0);
        jPanel12.add(txtVaciado, gridBagConstraints);

        jButton1.setBackground(new java.awt.Color(255, 255, 255));
        jButton1.setText("...");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(20, 0, 0, 50);
        jPanel12.add(jButton1, gridBagConstraints);

        jLabel5.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel5.setText("Ruta nomina individual: ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(20, 50, 0, 0);
        jPanel12.add(jLabel5, gridBagConstraints);

        txtIndividual.setBackground(new java.awt.Color(255, 255, 255));
        txtIndividual.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(20, 0, 0, 0);
        jPanel12.add(txtIndividual, gridBagConstraints);

        jButton2.setBackground(new java.awt.Color(255, 255, 255));
        jButton2.setText("...");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(20, 0, 0, 50);
        jPanel12.add(jButton2, gridBagConstraints);

        jTabbedPane1.addTab("Configuracion", jPanel12);

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
            btnVaciado.setEnabled(false);
            btnNomina.setEnabled(false);
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

    private void btnVaciadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVaciadoActionPerformed
        try {
            VaciadoNomina vaciado = new VaciadoNomina();
            Configuracion conf = new Configuracion();
            vaciado.generarNomina(jcmSemana.getSelectedItem().toString(), txtPeriodo.getText(), conf.leer("VaciadoNomina"));
            boolean band = false;
            int opc = JOptionPane.showConfirmDialog(this, "Deseas guardar los datos para historial de nomina?");
            for (int i = 0; i < TablaPagos.getRowCount(); i++) {
                try {
                    if (TablaPagos.getValueAt(i, 5) != null && !TablaPagos.getValueAt(i, 5).toString().equals("")) {
                        String pagos[] = ((String) TablaPagos.getValueAt(i, 6)).split(" ");
                        String nombre = (String) TablaPagos.getValueAt(i, 0);
                        String puesto = (String) TablaPagos.getValueAt(i, 1);
                        String sueldo = (String) TablaPagos.getValueAt(i, 2);
                        String ded = pagos[0].replace("-", "");
                        String horasDobles = pagos[1].replace("+", "");
                        String total = TablaPagos.getValueAt(i, 5) != null ? TablaPagos.getValueAt(i, 5).toString() : "0";
                        vaciado.agregarEmpleado(vaciado.tabla, nombre, puesto, sueldo, ded, horasDobles, "", "", total, band);
                        band = !band;
                        if (opc == JOptionPane.OK_OPTION) {
                            guardarHistorialNomina(formatearTabla(TablaPagos.getValueAt(i, 1)), formatearTabla(TablaPagos.getValueAt(i, 8)),
                                    formatearTabla(TablaPagos.getValueAt(i, 2)), formatearTabla(TablaPagos.getValueAt(i, 3)),
                                    formatearTabla(TablaPagos.getValueAt(i, 4)), formatearTabla(TablaPagos.getValueAt(i, 5)),
                                    formatearTabla(TablaPagos.getValueAt(i, 6)), formatearTabla(TablaPagos.getValueAt(i, 7)));
                        }
                    }
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
            vaciado.imprimirNomina();
            Desktop.getDesktop().open(new File(conf.leer("VaciadoNomina")));
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error al guardar vaciado de nomina: " + ex, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnVaciadoActionPerformed

    public String formatearTabla(Object obj) {
        try {
            return obj.toString();
        } catch (Exception e) {
            return "";
        }
    }

    private void btnNominaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNominaActionPerformed
        ReciboNominaPDF nomina = new ReciboNominaPDF();
        Configuracion conf = new Configuracion();
        String fec = txtPeriodo.getText().split(" ")[1];
        String ano = fec.split("-")[0];
        String sem = jcmSemana.getSelectedItem().toString();
        try {
            int opc = JOptionPane.showConfirmDialog(this, "Deseas guardar los datos para historial de nomina?");
            for (int i = 0; i < TablaPagos.getRowCount(); i++) {
                try {
                    if (!TablaPagos.getValueAt(i, 5).toString().equals("")) {
                        int buscar = buscarEnTabla(Tabla1, TablaPagos.getValueAt(i, 1).toString(), 1);
                        String nombre = (String) Tabla1.getValueAt(buscar, 0);
                        String ruta = conf.leer("NominaIndividual") + "\\" + ano + "\\" + sem + "\\nomina individual " + nombre + ".pdf";
                        String rfc = (String) Tabla1.getValueAt(buscar, 3);
                        String curp = (String) Tabla1.getValueAt(buscar, 2);
                        String relacionLaborarl = (String) Tabla1.getValueAt(buscar, 5);
                        String nss = (String) Tabla1.getValueAt(buscar, 4);
                        String periodo = txtPeriodo.getText();
                        String fechaPago = "hoy()";
                        String puesto = "Admin";
                        String semana = jcmSemana.getSelectedItem().toString();
                        String sueldo = (String) TablaPagos.getValueAt(i, 2);
                        String empleado = (String) TablaPagos.getValueAt(i, 1);
                        String detalles[] = ((String) TablaPagos.getValueAt(i, 6)).split(" ");
                        String horasDobles = detalles[1].replace("+", "");
                        String horasTriples = "0";
                        String faltas = detalles[0].replace("-", "");
                        String prima = detalles.length > 2 ? detalles[2].replace("++", "") : "";
                        nomina.generarPDF(ruta, nombre, rfc, curp, relacionLaborarl, nss, periodo, fechaPago, puesto, semana, sueldo, horasDobles, horasTriples, faltas, prima, empleado);
                        if (opc == JOptionPane.OK_OPTION) {
                            guardarHistorialNomina(empleado, formatearTabla(TablaPagos.getValueAt(i, 8)),
                                    formatearTabla(TablaPagos.getValueAt(i, 2)), formatearTabla(TablaPagos.getValueAt(i, 3)),
                                    formatearTabla(TablaPagos.getValueAt(i, 4)), formatearTabla(TablaPagos.getValueAt(i, 5)),
                                    formatearTabla(TablaPagos.getValueAt(i, 6)), formatearTabla(TablaPagos.getValueAt(i, 7)));
                        }
                    }
                } catch (Exception e) {

                }

            }
            Desktop.getDesktop().open(new File(conf.leer("NominaIndividual")));
        } catch (Exception ex) {
            Logger.getLogger(RH.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnNominaActionPerformed

    private void btnVacacionesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVacacionesActionPerformed
        if (TablaPagos.getSelectedRow() >= 0) {
            int fila = buscarEnTabla(Tabla1, (String) TablaPagos.getValueAt(TablaPagos.getSelectedRow(), 1), 1);
            String fechaIngreso = (String) Tabla1.getValueAt(fila, 5);
            String nombre = (String) Tabla1.getValueAt(fila, 0);
            String numero = (String) Tabla1.getValueAt(fila, 1);
            Date d = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String fecha = sdf.format(d);
            JFrame f = (JFrame) JOptionPane.getFrameForComponent(this);
            Vacaciones vac = new Vacaciones(f, true);
            vac.setLocationRelativeTo(f);
            vac.lblAntiguedad.setText(getAntiguedad(fechaIngreso));
            vac.lblEmpleado.setText(nombre);
            vac.lblFecha.setText(fechaIngreso);
            vac.lblFechaActual.setText(fecha);
            vac.verVacaciones(numero);
            vac.calcularVacaciones(fechaIngreso);
            vac.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Debes seleccionar una fila", "Advertencia", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_btnVacacionesActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        String ruta = getRuta();
        if (ruta != null) {
            try {
                Configuracion conf = new Configuracion();
                conf.guardar("VaciadoNomina", ruta);
                txtVaciado.setText(ruta);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error al guardar ruta de vaciado: " + ex);
            }
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        String ruta = getRuta();
        if (ruta != null) {
            try {
                Configuracion conf = new Configuracion();
                conf.guardar("NominaIndividual", ruta);
                txtIndividual.setText(ruta);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error al guardar ruta de nomina individual: " + ex);
            }
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        verHistorial((String) jcbSemanaHistorial.getSelectedItem());
    }//GEN-LAST:event_jButton3ActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        for (int i = 0; i < TablaPagos.getRowCount(); i++) {

        }
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnNominaEmpleadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNominaEmpleadoActionPerformed
        guardarNominaEmpleado();
    }//GEN-LAST:event_btnNominaEmpleadoActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable Tabla1;
    private javax.swing.JTable TablaNomina;
    private javax.swing.JTable TablaPagos;
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnCalcular;
    private javax.swing.JMenuItem btnFaltas;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnNomina;
    private javax.swing.JMenuItem btnNominaEmpleado;
    private javax.swing.JMenuItem btnVacaciones;
    private javax.swing.JButton btnVaciado;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
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
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JComboBox<String> jcbSemanaHistorial;
    private javax.swing.JComboBox<String> jcmSemana;
    private javax.swing.JLabel lblDetalles;
    private javax.swing.JLabel lblExtra;
    private javax.swing.JLabel lblRetardos;
    private javax.swing.JLabel lblSalir;
    private javax.swing.JLabel lblSemanal;
    private javax.swing.JLabel lblTotal;
    private javax.swing.JLabel lblVacaciones;
    private javax.swing.JPanel pan;
    private javax.swing.JPanel panelSalir;
    private javax.swing.JTextField txtBuscar;
    private javax.swing.JTextField txtIndividual;
    private javax.swing.JTextField txtPeriodo;
    private javax.swing.JTextField txtVaciado;
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
                editar.getAntiguedad(extraerDatos(tabla1.getValueAt(tabla1.getSelectedRow(), 5)));
                editar.setVisible(true);
//                if (editar.fecha != null) {
//                }
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
