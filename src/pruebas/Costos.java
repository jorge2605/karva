package pruebas;

import Conexiones.Conexion;
import Modelo.TablaHoras;
import VentanaEmergente.Costos.CostoHoras;
import VentanaEmergente.Costos.EditarEmpleados;
import VentanaEmergente.Costos.EditarMateriales;
import VentanaEmergente.Costos.Fechas;
import VentanaEmergente.Costos.TablaNominas;
import VentanaEmergente.Reportes.ReporteMensual;
import com.mxrck.autocompleter.TextAutoCompleter;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormatSymbols;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTabbedPane;
import javax.swing.plaf.basic.BasicTabbedPaneUI;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumn;
import scrollPane.ScrollBarCustom;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.IOException;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFrame;
import javax.swing.KeyStroke;
import VentanaEmergente.Costos.ConfTabla;
import VentanaEmergente.Costos.TablaPrincipalCostos;
import VentanaEmergente.Costos.addEmpleado;
import VentanaEmergente.Inicio1.Espera;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Stack;

public final class Costos extends javax.swing.JInternalFrame {

    String id;
    String fechaInicio, fechaFinal;
    int numeroSemanas;
    int empieza;
    TextAutoCompleter au;
    boolean band =  false;
    Espera espera = new Espera();
    Stack<String[]> empleados;
    String tipoSeleccion;
    Stack<String> fechasSeleccionadas;
    private EditarMateriales Em;
    
    private void pasteClipboard(JTable table) {
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        Transferable transferable = clipboard.getContents(null);
        DefaultTableModel miModelo = (DefaultTableModel) TablaNominas.getModel();
        if (transferable != null && transferable.isDataFlavorSupported(DataFlavor.stringFlavor)) {
            try {
                String clipboardData = (String) transferable.getTransferData(DataFlavor.stringFlavor);

                String[] rows = clipboardData.split("\n");
                int startRow = table.getSelectedRow();
                int startCol = table.getSelectedColumn();

                for (int i = 0; i < rows.length; i++) {
                    String[] values = rows[i].split("\t");
                    for (int j = 0; j < values.length; j++) {
                        if (startRow + i < table.getRowCount() && startCol + j < table.getColumnCount()) {
                            table.setValueAt(values[j], startRow + i, startCol + j);
                        }else{
                            String dat[] = {"","",""};
                            miModelo.addRow(dat);
                            table.setValueAt(values[j], startRow + i, startCol + j);
                        }
                    }
                }
            } catch (UnsupportedFlavorException | IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    public final void autoCompletar(){
        au = new TextAutoCompleter(txtProyecto);
        try{
            Connection con;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            String sql = "select Proyecto from proyectos";
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
                au.addItem(rs.getString("Proyecto"));
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "ERROR: "+e,"ERROR",JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void insertarSemanas(){
        cmbMes.removeAllItems();
        cmbAnio.removeAllItems();
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek( Calendar.MONDAY);
        calendar.setMinimalDaysInFirstWeek( 4);
        int numberWeekOfYear = calendar.get(Calendar.YEAR);
        for (int i = numberWeekOfYear; i >= 2020; i--) {
            cmbAnio.addItem(String.valueOf(i));
        }
        
        int mesActual = LocalDate.now().getMonthValue();

        for (int i = mesActual; i >= 1; i--) {
            String nombreMes = Month.of(i).getDisplayName(TextStyle.FULL, Locale.ENGLISH);
            cmbMes.addItem(nombreMes);
        }
    }
    
    public void limpiarTablaHoras(){
        TablaHoras.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Proyecto", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false
            };
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        TablaHoras.setShowGrid(true);
        TablaHoras.setSelectionBackground(new java.awt.Color(0,102,255));
        TablaHoras.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        TablaHoras.setDefaultRenderer(Object.class, new TablaHoras());
        
        if (TablaHoras.getColumnModel().getColumnCount() > 0) {
            TablaHoras.getColumnModel().getColumn(0).setMinWidth(150);
            TablaHoras.getColumnModel().getColumn(0).setPreferredWidth(150);
            TablaHoras.getColumnModel().getColumn(0).setMaxWidth(150);
        }

    }
    
    public void limpiarTablaOrdenes(){
        TablaOrdenes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
            },
            new String [] {
                "Requisicion", "PO", "N.P", "Descripcion", "Cantidad", "Moneda", "P.U", "Total MXN", "Proveedor", "Fecha Orden", "Fecha Recibido", "Proyecto"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false, false
            };
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        TablaOrdenes.setShowGrid(false);
    }
    
    public void limpiarTablaAlmacen(){
        TablaAlmacen.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "CODIGO", "CANTIDAD", "PROYECTO", "REQUISITOR", "PRECIO MXN", "PRECIO USD", "TOTAL MXN", "TOTAL USD", "FECHA"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });

        TablaAlmacen.setShowGrid(false);
    }
    
    public void limpiarTablaIndirectos(){
        TablaIndirecto.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
            },
            new String [] {
                "Proyecto", "Precio", "Porcentaje", "Total"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
    }
    
    public void limpiarTablaPrincipal(){
        TablaPrincipal.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Proyecto", "Diseño", "Herramentista", "Electromecanico", "Mano de obra", "Gasto Indirecto", "Materia prima", "Total", "Precio proyecto", "Ganancia o perdida", "Margen Beneficio"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        }); 
    }
    
    public void limpiarTablaNominas(String h1, String h2,String h3, String h4, String h5, String s1, String s2, String s3, String s4, String s5){
        TablaNominas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
            },
            new String [] {
                "ID", "Nombre", "Departamento", h1, h2, h3, h4, h5, s1, s2, s3, s4, s5, "Sueldo por mes", "Hora al mes", "MOD"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false
            };
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        TablaNominas.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_V, KeyEvent.CTRL_DOWN_MASK), "paste");
        
        if (TablaNominas.getColumnModel().getColumnCount() > 0) {
            TablaNominas.getColumnModel().getColumn(0).setMinWidth(35);
            TablaNominas.getColumnModel().getColumn(0).setPreferredWidth(35);
            TablaNominas.getColumnModel().getColumn(0).setMaxWidth(35);
            TablaNominas.getColumnModel().getColumn(1).setMinWidth(200);
            TablaNominas.getColumnModel().getColumn(1).setPreferredWidth(200);
            TablaNominas.getColumnModel().getColumn(1).setMaxWidth(200);
            TablaNominas.getColumnModel().getColumn(2).setMinWidth(200);
            TablaNominas.getColumnModel().getColumn(2).setPreferredWidth(200);
            TablaNominas.getColumnModel().getColumn(2).setMaxWidth(200);
        }
    }
    
    public void limpiarTablaMaquinados(){
        TablaMaquinados.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
            },
            new String [] {
                "Empleado", "Proyecto", "Horas", "Dimensiones", "Material", "Cantidad", "Costo", "ID", "Fecha", "Plano", "Maquina"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, true, false, false, false, false
            };
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        if (TablaMaquinados.getColumnModel().getColumnCount() > 0) {
            TablaMaquinados.getColumnModel().getColumn(7).setMinWidth(0);
            TablaMaquinados.getColumnModel().getColumn(7).setPreferredWidth(0);
            TablaMaquinados.getColumnModel().getColumn(7).setMaxWidth(0);
        }
    }
    
    public void limpiarTablaIntegracion(){
        TablaIntegracion.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
            },
            new String [] {
                "Empleado", "Proyecto", "Horas", "Ocupacion", "ID", "Fecha"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        if (TablaIntegracion.getColumnModel().getColumnCount() > 0) {
            TablaIntegracion.getColumnModel().getColumn(4).setMinWidth(0);
            TablaIntegracion.getColumnModel().getColumn(4).setPreferredWidth(0);
            TablaIntegracion.getColumnModel().getColumn(4).setMaxWidth(0);
        }
    }
    
    public void limpiarTablaDiseño(){
        TablaDiseño.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
            },
            new String [] {
                "Empleado", "Proyecto", "Horas", "Comentarios", "ID", "Fecha"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        if (TablaDiseño.getColumnModel().getColumnCount() > 0) {
            TablaDiseño.getColumnModel().getColumn(4).setMinWidth(0);
            TablaDiseño.getColumnModel().getColumn(4).setPreferredWidth(0);
            TablaDiseño.getColumnModel().getColumn(4).setMaxWidth(0);
        }
    }
    
    public void limpiarTablaCalidad(){
        TablaCalidad.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
            },
            new String [] {
                "Empleado", "Proyecto", "Horas", "Comentarios", "ID", "Fecha"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        if (TablaCalidad.getColumnModel().getColumnCount() > 0) {
            TablaCalidad.getColumnModel().getColumn(4).setMinWidth(0);
            TablaCalidad.getColumnModel().getColumn(4).setPreferredWidth(0);
            TablaCalidad.getColumnModel().getColumn(4).setMaxWidth(0);
        }
    }
    
    public int getColumna(String fecha){
        int col;
        String fe = fecha.substring(fecha.length()-2, fecha.length());
        col = Integer.parseInt(fe);
        return col;
    }
    
    public int getFila(String proyecto){
        for (int i = 0; i < TablaHoras.getRowCount(); i++) {
            if(TablaHoras.getValueAt(i, 0).equals(proyecto)){
                return i;
            }
        }
        return -1;
    }
    
    public String getSuma(int fila, int col, String hora) {

        String horaTabla;

        try {
            horaTabla = TablaHoras.getValueAt(fila, col).toString();
        } catch (Exception e) {
            horaTabla = "0:00";
        }

        int min1 = convertirAMinutos(hora);
        int min2 = convertirAMinutos(horaTabla);

        int totalMin = min1 + min2;

        int horas = totalMin / 60;
        int minutos = totalMin % 60;

        return String.format("%d:%02d", horas, minutos);
    }
    
    private int convertirAMinutos(String hora) {
        if (hora == null || !hora.contains(":")) {
            return 0;
        }

        try {
            String[] p = hora.split(":");
            int h = Integer.parseInt(p[0]);
            int m = Integer.parseInt(p[1]);
            return (h * 60) + m;
        } catch (Exception e) {
            return 0;
        }
    }

    public void getAllEmployes(){
        empleados = new Stack<>();
        try{
            Connection con;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            String sql = "select * from registroempleados";
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
                String ad[] = new String[2];
                ad[0] = rs.getString("NumEmpleado");
                ad[1] = rs.getString("Nombre") + " " + rs.getString("Apellido");
                empleados.add(ad);
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "Error: "+e,"Error",JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public String getEmploye(String numEmpleado){
        for (int i = 0; i < empleados.size(); i++) {
            if(empleados.get(i)[0].equals(numEmpleado)){
                return empleados.get(i)[1];
            }
        }
        return null;
    }
    
    public void agregarMaquinados(Connection con, String dia1, String dia2, String tipo){ 
        try{
            limpiarTablaMaquinados();
            Statement st = con.createStatement();
            String sql = "select * from htpp where Departamento like '2' and Fecha between '"+dia1+"' and '"+dia2+"'";
            if(tipo.equals("proyecto")){
                sql = "select * from htpp where Departamento like '2' and Proyecto like '" + txtProyecto.getText() + "'";
            }
            ResultSet rs = st.executeQuery(sql);
            DefaultTableModel miModelo = (DefaultTableModel) TablaMaquinados.getModel();
            String datos[] = new String[15];
            Stack<String> cnc = new Stack<>();
            Stack<String> fresa = new Stack<>();
            Stack<String> torno = new Stack<>();
            while(rs.next()){
                datos[0] = rs.getString("NumEmpleado");
                datos[0] = getEmploye(datos[0]);
                datos[1] = rs.getString("Proyecto");
                datos[2] = rs.getString("Hora");
                datos[3] = rs.getString("Dimensiones");
                datos[4] = rs.getString("Material");
                datos[5] = rs.getString("Cantidad");
                double costo = rs.getDouble("Costo");
                datos[6] = String.format(java.util.Locale.US, "%.2f", costo); // Redondeo a 2 decimales en el costo
                datos[7] = rs.getString("Id");
                datos[8] = rs.getString("Fecha");
                datos[9] = rs.getString("Plano");
                datos[10] = rs.getString("Maquina");
                switch (datos[10]) {
                    case "Cnc":
                        cnc.push(datos[2]);
                        break;
                    case "Fresadora":
                        fresa.push(datos[2]);
                        break;
                    case "Torno":
                        torno.push(datos[2]);
                        break;
                    default:
                        break;
                }
                miModelo.addRow(datos);
            }
            
            lblCnc1.setText(getDuracion(cnc));
            lblFresa1.setText(getDuracion(fresa));
            lblTorno1.setText(getDuracion(torno));
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "ERROR: "+e,"ERROR",JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public String getDuracion(Stack<String> maquina){
        Duration total = Duration.ZERO;

        for (int i = 0; i < maquina.size(); i++) {
            String h = maquina.get(i);

            if (h == null || !h.contains(":")) {
                continue;
            }

            String[] partes = h.split(":");
            int hora = Integer.parseInt(partes[0]);
            int min = partes.length > 1 ? Integer.parseInt(partes[1]) : 0;

            total = total.plusHours(hora).plusMinutes(min);
        }

        long horas = total.toHours();
        long minutos = total.toMinutes() % 60;

        return String.format("%d:%02d", horas, minutos);
    }

    public void agregarIntegracion(Connection con, String dia1, String dia2, String tipo){ 
        try{
            limpiarTablaIntegracion();
            Statement st = con.createStatement();
            String sql = "select * from htpp where Departamento like '1' and Fecha between '"+dia1+"' and '"+dia2+"'";
            if(tipo.equals("proyecto")){
                sql = "select * from htpp where Departamento like '1' and Proyecto like '" + txtProyecto.getText() + "%'";
            }
            ResultSet rs = st.executeQuery(sql);
            DefaultTableModel miModelo = (DefaultTableModel) TablaIntegracion.getModel();
            String datos[] = new String[10];
            Stack<String> integracion = new Stack<>();
            while(rs.next()){
                datos[0] = rs.getString("NumEmpleado");
                datos[0] = getEmploye(datos[0]);
                datos[1] = rs.getString("Proyecto");
                datos[2] = rs.getString("Hora");
                datos[3] = rs.getString("Ocupacion");
                datos[4] = rs.getString("Id");
                datos[5] = rs.getString("Fecha");
                integracion.push(datos[2]);
                miModelo.addRow(datos);
            }
            lblIntegracion.setText(getDuracion(integracion));
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "ERROR: "+e,"ERROR",JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void agregarDiseño(Connection con, String dia1, String dia2, String tipo){
        try{
            limpiarTablaDiseño();
            Statement st = con.createStatement();
            String sql = "select * from htpp where Departamento like '3' and Fecha between '"+dia1+"' and '"+dia2+"'";
            if(tipo.equals("proyecto")){
                sql = "select * from htpp where Departamento like '3' and Proyecto like '" + txtProyecto.getText() + "'";
            }
            ResultSet rs = st.executeQuery(sql);
            DefaultTableModel miModelo = (DefaultTableModel) TablaDiseño.getModel();
            String datos[] = new String[10];
            Stack<String> diseno = new Stack<>();
            while(rs.next()){
                datos[0] = rs.getString("NumEmpleado");
                datos[0] = getEmploye(datos[0]);
                datos[1] = rs.getString("Proyecto");
                datos[2] = rs.getString("Hora");
                datos[3] = rs.getString("Notas");
                datos[4] = rs.getString("Id");
                datos[5] = rs.getString("Fecha");
                diseno.push(datos[2]);
                miModelo.addRow(datos);
            }
            lblHorasDiseno.setText(getDuracion(diseno));
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "ERROR: "+e,"ERROR",JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void agregarCalidad(Connection con, String dia1, String dia2, String tipo){
        try{
            limpiarTablaCalidad();
            Statement st = con.createStatement();
            String sql = "select * from htpp where Departamento like '4' and Fecha between '"+dia1+"' and '"+dia2+"'";
            if(tipo.equals("proyecto")){
                sql = "select * from htpp where Departamento like '4' and Proyecto like '" + txtProyecto.getText() + "'";
            }
            ResultSet rs = st.executeQuery(sql);
            DefaultTableModel miModelo = (DefaultTableModel) TablaCalidad.getModel();
            String datos[] = new String[10];
            while(rs.next()){
                datos[0] = rs.getString("NumEmpleado");
                datos[0] = getEmploye(datos[0]);
                datos[1] = rs.getString("Proyecto");
                datos[2] = rs.getString("Hora");
                datos[3] = rs.getString("Notas");
                datos[4] = rs.getString("Id");
                datos[5] = rs.getString("Fecha");
                miModelo.addRow(datos);
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "ERROR: "+e,"ERROR",JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void insertarSemanasHoras(){
        cmbSemanas.removeAllItems();
        cmbSemanas.addItem("Todos");
        for (int i = empieza; i < empieza + numeroSemanas; i++) {
            cmbSemanas.addItem(String.valueOf(i));
        }
    }
    
    public void verHoras(String tipo, String inicio, String fin){
        if(inicio == null){
            insertarSemanasHoras();
        }
        try{
            limpiarTablaHoras();

            // Calcular rango de fechas
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat s1 = new SimpleDateFormat("MMMM", Locale.ENGLISH);
            SimpleDateFormat s2 = new SimpleDateFormat("MM");
            SimpleDateFormat s3 = new SimpleDateFormat("yyyy-MM-dd");

            Date d = s1.parse(cmbMes.getSelectedItem().toString());
            int year = Integer.parseInt(cmbAnio.getSelectedItem().toString());
            int month = Integer.parseInt(s2.format(d)) - 1;
            calendar.set(year, month, 1);
            calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
            String dia1 = s3.format(calendar.getTime());
            calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
            String dia2 = s3.format(calendar.getTime());

            if(inicio != null){
                dia1 = inicio;
                dia2 = fin;
            }

            // Una sola conexión y una sola consulta para todo
            Connection con = new Conexion().getConnection();

            String sql;
            PreparedStatement pst;
            if(tipo.equals("proyecto")){
                sql = "SELECT Proyecto, Fecha, Hora, Departamento FROM htpp WHERE Proyecto LIKE ?";
                pst = con.prepareStatement(sql);
                pst.setString(1, "%" + txtProyecto.getText() + "%");
            } else {
                sql = "SELECT Proyecto, Fecha, Hora, Departamento FROM htpp WHERE Fecha BETWEEN ? AND ?";
                pst = con.prepareStatement(sql);
                pst.setString(1, dia1);
                pst.setString(2, dia2);
            }

            ResultSet rs = pst.executeQuery();
            DefaultTableModel miModelo = (DefaultTableModel) TablaHoras.getModel();

            // Usar un Set para saber qué proyectos ya se agregaron
            java.util.Set<String> proyectosAgregados = new java.util.HashSet<>();

            // Primera pasada: detectar proyectos únicos y agregar filas
            java.util.List<String[]> registros = new java.util.ArrayList<>();
            while(rs.next()){
                String proyecto = rs.getString("Proyecto");
                String fecha    = rs.getString("Fecha");
                String hora     = rs.getString("Hora");
                String depto    = rs.getString("Departamento");
                registros.add(new String[]{proyecto, fecha, hora, depto});

                if(!proyectosAgregados.contains(proyecto)){
                    proyectosAgregados.add(proyecto);
                    String fila[] = new String[]{proyecto, null, null, null, null, null, null, null,
                        null, null, null, null, null, null, null, null, null, null, null, null, null,
                        null, null, null, null, null, null, null, null, null, null, null};
                    miModelo.addRow(fila);
                    miModelo.addRow(new String[]{"Electromecanico"});
                    miModelo.addRow(new String[]{"Herramentista"});
                    miModelo.addRow(new String[]{"Diseño"});
                    miModelo.addRow(new String[]{"Calidad"});
                }
            }
            rs.close();
            pst.close();

            // Segunda pasada: colocar horas en la tabla (ya con todas las filas creadas)
            for(String[] reg : registros){
                String proy  = reg[0];
                String fecha = reg[1];
                String hora  = reg[2];
                int f = 0;
                try { f = Integer.parseInt(reg[3]); } catch(Exception e) {}
                int fila = getFila(proy);
                int col  = getColumna(fecha);
                if(fila >= 0 && col > 0){
                    TablaHoras.setValueAt(getSuma(fila + f, col, hora), fila + f, col);
                }
            }

            // Reutilizar la misma conexión para las 4 tablas adicionales
            agregarMaquinados(con, dia1, dia2, tipo);
            agregarIntegracion(con, dia1, dia2, tipo);
            agregarDiseño(con, dia1, dia2, tipo);
            agregarCalidad(con, dia1, dia2, tipo);
            con.close();

        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "ERROR: " + e, "ERROR", JOptionPane.ERROR_MESSAGE);
        } catch (ParseException ex) {
            Logger.getLogger(Costos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void setTabbed(JTabbedPane tabbed){
        tabbed.setUI(new BasicTabbedPaneUI() {
                @Override
                protected void paintContentBorder(Graphics g, int tabPlacement, int selectedIndex) {}
            });
        tabbed.setUI(new BasicTabbedPaneUI() {
                @Override
                protected void paintFocusIndicator(Graphics g, int tabPlacement, Rectangle[] rects, int tabIndex, Rectangle iconRect, Rectangle textRect, boolean isSelected) {
                    // No hacer nada para no pintar el resaltado de enfoque
                }
            });
    }
    
    public void navegacion(){
        String rutaCarpeta = "\\\\serverdell\\bd\\OC\\Orden_de_compra";

        // Crear un objeto File para representar la carpeta
        File carpeta = new File(rutaCarpeta);

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat s1 = new SimpleDateFormat("MMMM", Locale.ENGLISH);
        SimpleDateFormat s2 = new SimpleDateFormat("MM");

        Date d = null;
        try {
            d = s1.parse(cmbMes.getSelectedItem().toString());
        } catch (ParseException ex) {
            Logger.getLogger(Costos.class.getName()).log(Level.SEVERE, null, ex);
        }
        int year = Integer.parseInt(cmbAnio.getSelectedItem().toString());
        int month = Integer.parseInt(s2.format(d)) -1;
        calendar.set(year, month, 1);
        int firstDay = calendar.getActualMinimum(Calendar.DAY_OF_MONTH);
        calendar.set(Calendar.DAY_OF_MONTH, firstDay);
        Date d1 = calendar.getTime();
        
        int lastDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        calendar.set(Calendar.DAY_OF_MONTH, lastDay);
        Date d2 = calendar.getTime();
        
        // Verificar si la carpeta existe
        if (carpeta.exists() && carpeta.isDirectory()) {
            // Obtener la lista de archivos en la carpeta
            File[] archivos = carpeta.listFiles();

            // Recorrer la lista de archivos
            for (File archivo : archivos) {
                // Obtener el nombre del archivo
                String nombreArchivo = archivo.getName();

                // Obtener la fecha de creación del archivo
                long fechaCreacion = archivo.lastModified();
                Date fechaCreacionDate = new Date(fechaCreacion);
                if(fechaCreacionDate.after(d1) && fechaCreacionDate.before(d2)){
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

                    // Imprimir el nombre del archivo y la fecha de creación
                    System.out.println("Archivo: " + nombreArchivo);
                    System.out.println("Fecha de creación: " + sdf.format(fechaCreacionDate));
                }
            }
        } else {
            System.out.println("La carpeta especificada no existe o no es una carpeta válida.");
        }
    }
    
    public void completar(){
        try{
            Connection con;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            String sql = "select Id, Fecha, FechaNew from requisicion where FechaNew is null";
            ResultSet rs = st.executeQuery(sql);
            String sql2 = "update requisicion set FechaNew = ? where Id = ?";
            PreparedStatement pst = con.prepareStatement(sql2);
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
            int n = 0;
            while(rs.next()){
                String id, fecha,fechanew;
                id = rs.getString("Id");
                fecha = rs.getString("Fecha");
                Date d = sdf.parse(fecha);
                fechanew = sdf2.format(d);
                pst.setString(1, fechanew);
                pst.setString(2, id);
                
                n = pst.executeUpdate();
            }
            
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "ERROR"+e,"ERROR",JOptionPane.ERROR_MESSAGE);
        } catch (ParseException ex) {
            Logger.getLogger(ReporteMensual.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void obtenerFechas(){
        SimpleDateFormat s1 = new SimpleDateFormat("MMMM", Locale.ENGLISH);
        SimpleDateFormat s2 = new SimpleDateFormat("MM");
        SimpleDateFormat s3 = new SimpleDateFormat("yyyy-MM-dd");
        limpiarTablaOrdenes();
        Date d = null;
        try {
            d = s1.parse(cmbMes.getSelectedItem().toString());
        } catch (ParseException ex) {
            Logger.getLogger(Costos.class.getName()).log(Level.SEVERE, null, ex);
            espera.dispose();
        }
        int year = Integer.parseInt(cmbAnio.getSelectedItem().toString());
        int month = Integer.parseInt(s2.format(d));
        Fechas fec = new Fechas(month, year);
        
        fechaInicio = fec.getFechaInicio();
        fechaFinal = fec.getFechaFinal();
        numeroSemanas = fec.getNumeroSemanas();
        empieza = fec.getEmpiesa();
    }
    
    public void ordenesDeCompra(String tipo){
        try{
            limpiarTablaOrdenes();
            Connection con = new Conexion().getConnection();
            completar();
            DecimalFormat format = new DecimalFormat("#,###.##");
            DefaultTableModel miModelo = (DefaultTableModel) TablaOrdenes.getModel();

            // Precargar monedas de proveedores en un mapa
            java.util.Map<String, String> monedaProveedor = new java.util.HashMap<>();
            ResultSet rsProv = con.createStatement().executeQuery("SELECT Nombre, Moneda FROM registroprov_compras");
            while(rsProv.next()){
                monedaProveedor.put(rsProv.getString("Nombre"), rsProv.getString("Moneda"));
            }
            rsProv.close();

            // Precargar fechas de requisicion en un mapa
            java.util.Map<String, String> fechaRequisicion = new java.util.HashMap<>();
            ResultSet rsReq = con.createStatement().executeQuery("SELECT Id, Fecha FROM requisicion");
            while(rsReq.next()){
                fechaRequisicion.put(rsReq.getString("Id"), rsReq.getString("Fecha"));
            }
            rsReq.close();

            // Consulta principal de órdenes del mes
            String sql = "SELECT OrdenNo, RequisicionNo FROM ordencompra WHERE Fecha BETWEEN ? AND ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, fechaInicio);
            pst.setString(2, fechaFinal);
            ResultSet rs = pst.executeQuery();

            // Recopilar todas las OC del mes
            java.util.List<String> listaOC = new java.util.ArrayList<>();
            while(rs.next()){
                listaOC.add(rs.getString("OrdenNo"));
            }
            rs.close();
            pst.close();

            if(listaOC.isEmpty()){
                lblTotalCompras.setText("0");
                con.close();
                return;
            }

            // Consulta de requisiciones para todas las OC del mes de un solo golpe 
            String placeholders = String.join(",", java.util.Collections.nCopies(listaOC.size(), "?"));
            String sql2 = "SELECT Proveedor, Proyecto, Codigo, Descripcion, Cantidad, Precio, FechaRecibo, NumRequisicion, OC, Llego "
                        + "FROM requisiciones WHERE OC IN (" + placeholders + ") order by numrequisicion desc";
            PreparedStatement pst2 = con.prepareStatement(sql2);
            for(int i = 0; i < listaOC.size(); i++){
                pst2.setString(i + 1, listaOC.get(i));
            }
            ResultSet rs2 = pst2.executeQuery();

            double totalMxn = 0;
            String dat[] = new String[15];
            String datos[] = new String[20];

            while(rs2.next()){
                try{
                    datos[9]  = rs2.getString("Proveedor");
                    datos[10] = rs2.getString("Proyecto");
                    datos[11] = rs2.getString("Codigo");
                    datos[12] = rs2.getString("Descripcion");
                    datos[13] = rs2.getString("Cantidad");
                    datos[14] = rs2.getString("Precio");
                    datos[16] = rs2.getString("FechaRecibo");
                    datos[17] = rs2.getString("NumRequisicion");
                    datos[18] = rs2.getString("Proveedor");
                    datos[0]  = rs2.getString("OC");
                    datos[2]  = rs2.getString("Llego");
                    datos[8]  = datos[17];

                    // Moneda: leer del mapa, sin consulta extra
                    String moneda = monedaProveedor.getOrDefault(datos[9], "");
                    // Fecha requisicion: leer del mapa, sin consulta extra
                    String numrequi = fechaRequisicion.getOrDefault(datos[17], "");

                    double precio = Double.parseDouble(datos[14]) * Double.parseDouble(datos[13]);

                    double total;
                    if(moneda.equals("MXN")){
                        dat[5] = "MXN";
                        total = precio;
                    } else {
                        dat[5] = "USD";
                        total = precio * Double.parseDouble(lblPrecioDolar.getText());
                    }
                    totalMxn += total;

                    dat[0] = datos[8];
                    dat[1] = datos[0];
                    dat[2] = datos[11];
                    dat[3] = datos[12];
                    dat[4] = datos[13];
                    dat[6] = format.format(Double.parseDouble(datos[14]));
                    dat[7] = format.format(total);
                    dat[9] = numrequi;
                    dat[10] = datos[16];
                    dat[11] = datos[10];
                    dat[8] = datos[18];

                    if(precio != 0){
                        miModelo.addRow(dat);
                    }
                }catch(Exception e){
                    System.out.println("error ordenesDeCompra: " + e);
                }
            }
            rs2.close();
            pst2.close();

            // Total
            double total = 0;
            DecimalFormat df = new DecimalFormat("#,###.##");
            for(int i = 0; i < TablaOrdenes.getRowCount(); i++){
                try{ total += Double.parseDouble(TablaOrdenes.getValueAt(i, 7).toString().replace(",", "")); }catch(Exception e){}
            }
            lblTotalCompras.setText(df.format(total));
            con.close();

        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "ERROR: "+e,"ERROR",JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void ordenesDeCompraProyecto(){
        limpiarTablaOrdenes();
        try{
            Connection con = new Conexion().getConnection();
            completar();
            DecimalFormat format = new DecimalFormat("#,###.##");
            DecimalFormat df = new DecimalFormat("#,###.##");
            DefaultTableModel miModelo = (DefaultTableModel) TablaOrdenes.getModel();

            // Precargar monedas de proveedores en un mapa 
            java.util.Map<String, String> monedaProveedor = new java.util.HashMap<>();
            ResultSet rsProv = con.createStatement().executeQuery("SELECT Nombre, Moneda FROM registroprov_compras");
            while(rsProv.next()){
                monedaProveedor.put(rsProv.getString("Nombre"), rsProv.getString("Moneda"));
            }
            rsProv.close();

            // Precargar fechas de requisicion en un mapa 
            java.util.Map<String, String> fechaRequisicion = new java.util.HashMap<>();
            ResultSet rsReq = con.createStatement().executeQuery("SELECT Id, Fecha FROM requisicion");
            while(rsReq.next()){
                fechaRequisicion.put(rsReq.getString("Id"), rsReq.getString("Fecha"));
            }
            rsReq.close();

            // Consulta principal: requisiciones del proyecto con OC asignada
            PreparedStatement pst2 = con.prepareStatement(
                "SELECT Proveedor, Proyecto, Codigo, Descripcion, Cantidad, Precio, FechaRecibo, NumRequisicion, OC, Llego " +
                "FROM requisiciones WHERE Proyecto = ? AND OC IS NOT NULL AND OC != '' AND OC != 'N/A'"
            );
            pst2.setString(1, txtProyecto.getText());
            ResultSet rs2 = pst2.executeQuery();

            double totalMxn = 0, totalUsd = 0;
            double recibidoMxn = 0, faltanteMxn = 0;
            double recibidoUsd = 0, faltanteUsd = 0;
            String datos[] = new String[20];
            String dat[] = new String[15];
            double total2 = 0;

            while(rs2.next()){
                try{
                    datos[8]  = rs2.getString("NumRequisicion");
                    datos[0]  = rs2.getString("OC");
                    datos[9]  = rs2.getString("Proveedor");
                    datos[10] = rs2.getString("Proyecto");
                    datos[11] = rs2.getString("Codigo");
                    datos[12] = rs2.getString("Descripcion");
                    datos[13] = rs2.getString("Cantidad");
                    datos[14] = rs2.getString("Precio");
                    datos[15] = rs2.getString("Precio");
                    datos[16] = rs2.getString("FechaRecibo");
                    datos[17] = rs2.getString("NumRequisicion");
                    datos[18] = rs2.getString("Proveedor");
                    datos[2]  = rs2.getString("Llego");

                    // Leer del mapa, sin consultas extra
                    String moneda  = monedaProveedor.getOrDefault(datos[9], "");
                    String numrequi = fechaRequisicion.getOrDefault(datos[17], "");

                    double precio = Double.parseDouble(datos[14]) * Double.parseDouble(datos[13]);

                    if(moneda.equals("MXN")){
                        totalMxn += precio;
                        dat[5] = "MXN";
                        if(datos[2] != null){ recibidoMxn += precio; } else { faltanteMxn += precio; }
                    } else {
                        totalUsd += precio;
                        dat[5] = "USD";
                        if(datos[2] != null){ recibidoUsd += precio; } else { faltanteUsd += precio; }
                    }

                    total2 += precio;
                    dat[0]  = datos[8];
                    dat[1]  = datos[0];
                    dat[2]  = datos[11];
                    dat[3]  = datos[12];
                    dat[4]  = datos[13];
                    dat[6]  = format.format(Double.parseDouble(datos[14]));
                    dat[7]  = moneda.equals("DLLS")
                              ? format.format(precio * Double.parseDouble(lblPrecioDolar.getText()))
                              : format.format(precio);
                    dat[9]  = numrequi;
                    dat[10] = datos[16];
                    dat[11] = datos[10];
                    dat[8]  = datos[18];

                    if(total2 != 0){
                        miModelo.addRow(dat);
                    }
                }catch(Exception e){
                    System.out.println("error ordenesDeCompraProyecto: " + e);
                }
            }
            rs2.close();
            pst2.close();

            double tot = 0;
            for(int i = 0; i < TablaOrdenes.getRowCount(); i++){
                try{ tot += Double.parseDouble(TablaOrdenes.getValueAt(i, 7).toString().replace(",", "")); }catch(Exception e){}
            }
            lblTotalCompras.setText(df.format(tot));
            con.close();

        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "ERROR: "+e,"ERROR",JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void almacen(String tipo){
        limpiarTablaAlmacen();
        DecimalFormat df = new DecimalFormat("#,###.##");
        try{
            DefaultTableModel miModelo = (DefaultTableModel) TablaAlmacen.getModel();
            Connection con = new Conexion().getConnection();

            // Precargar precio del dólar (1 vez, no por cada fila) 
            double precioDolar = Double.parseDouble(lblPrecioDolar.getText());

            // Precargar mapa Codigo -> {Proveedor, Precio} de requisiciones
            java.util.Map<String, String[]> precioRequisicion = new java.util.HashMap<>();

            ResultSet rsR = con.createStatement().executeQuery("SELECT Codigo, Proveedor, Precio FROM requisiciones ORDER BY Id ASC");
            while(rsR.next()){
                String cod = rsR.getString("Codigo");
                if(cod != null){
                    precioRequisicion.put(cod, new String[]{rsR.getString("Proveedor"), rsR.getString("Precio")});
                }
            }
            rsR.close();

            // Precargar mapa Proveedor -> Moneda 
            java.util.Map<String, String> monedaProveedor = new java.util.HashMap<>();
            ResultSet rsProv = con.createStatement().executeQuery("SELECT Nombre, Moneda FROM registroprov_compras");
            while(rsProv.next()){
                monedaProveedor.put(rsProv.getString("Nombre"), rsProv.getString("Moneda"));
            }
            rsProv.close();

            // Consulta principal de pedidos
            String sql;
            PreparedStatement pst;
            if(tipo.equals("proyecto")){
                sql = "SELECT * FROM pedidos WHERE proyecto = ? ORDER BY Id ASC";
                pst = con.prepareStatement(sql);
                pst.setString(1, txtProyecto.getText());
            } else {
                sql = "SELECT * FROM pedidos WHERE FechaSalida BETWEEN ? AND ? ORDER BY Id ASC";
                pst = con.prepareStatement(sql);
                pst.setString(1, fechaInicio);
                pst.setString(2, fechaFinal);
            }
            ResultSet rs = pst.executeQuery();
            String datos[] = new String[15];
            String d[] = new String[15];

            while(rs.next()){
                try{
                    datos[0] = rs.getString("NumParte");
                    datos[1] = rs.getString("Cantidad");
                    datos[2] = rs.getString("Proyecto");
                    datos[3] = rs.getString("Requisitor");
                    datos[8] = rs.getString("FechaSalida");

                    // Leer de mapa en vez de hacer consulta
                    String[] reqData = precioRequisicion.get(datos[0]);
                    datos[9]  = (reqData != null) ? reqData[0] : "";
                    datos[10] = (reqData != null) ? reqData[1] : null;

                    String moneda = monedaProveedor.getOrDefault(datos[9], "");

                    double precioU = 0;
                    if(datos[10] != null && !datos[10].isEmpty()){
                        try{ precioU = Double.parseDouble(datos[10]); }catch(Exception e){}
                    }
                    double cantidad = Double.parseDouble(datos[1]);
                    double precioMxn = 0, precioUsd = 0;

                    if(moneda.equals("MXN")){
                        precioMxn = precioU * cantidad;
                        d[4] = String.valueOf(precioU);
                        d[5] = "0";
                    } else {
                        precioUsd = precioU * cantidad;
                        d[4] = "0";
                        d[5] = String.valueOf(precioU);
                    }

                    d[0] = datos[0];
                    d[1] = datos[1];
                    d[2] = datos[2];
                    d[3] = datos[3];
                    d[6] = df.format(precioMxn);
                    d[7] = df.format(precioUsd);
                    d[8] = datos[8];
                    miModelo.addRow(d);
                }catch(Exception e){
                    System.out.println("error almacen: " + e);
                }
            }
            rs.close();
            pst.close();

            double total = 0;
            for(int i = 0; i < TablaAlmacen.getRowCount(); i++){
                try{ total += Double.parseDouble(TablaAlmacen.getValueAt(i, 6).toString().replace(",", "")); }catch(Exception e){}
                try{ total += Double.parseDouble(TablaAlmacen.getValueAt(i, 7).toString().replace(",", "")) * precioDolar; }catch(Exception e){}
            }
            lblTotalAlmacen.setText(df.format(total));
            con.close();

        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "ERROR: "+e,"ERROR",JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void enchular(JTable tabla, JScrollPane scrol){
        tabla.getTableHeader().setFont(new Font("Roboto", Font.BOLD, 14));
        tabla.getTableHeader().setOpaque(false);
        tabla.getTableHeader().setBackground(new Color(0, 78, 171));
        tabla.getTableHeader().setForeground(Color.white);
        tabla.setRowHeight(25);
        tabla.setShowVerticalLines(false);
        tabla.setGridColor(new Color(240,240,240));
        
        scrol.getViewport().setBackground(new Color(255,255,255));
        scrol.setVerticalScrollBar(new ScrollBarCustom(new Color(0,165,255)));
    }
    
    public void verCostosIndirectos(String tipo){
        try{
            Connection con;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            String sql = "select * from costosindirectos";
            ResultSet rs = st.executeQuery(sql);
            String dat[] = new String[5];
            while(rs.next()){
                dat[0] = (rs.getString("Renta"));
                dat[1] = (rs.getString("Luz"));
                dat[2] = (rs.getString("Seguro"));
                dat[3] = (rs.getString("Impuestos"));
            }
            txtRenta.setValue(Double.parseDouble(dat[0]));
            txtLuz.setValue(Double.parseDouble(dat[1]));
            txtSeguro.setValue(Double.parseDouble(dat[2]));
            txtImpuestos.setValue(Double.parseDouble(dat[3]));
            double a = 0,b = 0,c = 0,d = 0,e = 0;
            try{
            a = Double.parseDouble(dat[0]);
            }catch(Exception x){}
            try{
            b = Double.parseDouble(dat[1]);
            }catch(Exception x){}
            try{
            c = Double.parseDouble(dat[2]);
            }catch(Exception x){}
            try{
            d = Double.parseDouble(dat[3]);
            }catch(Exception x){}
            try{
            dat[4] = (txtNominas.getText());
            txtImpuestos.setValue(Double.parseDouble(dat[4]));
            d = Double.parseDouble(dat[4]);
            }catch(Exception x){System.out.println("error: "+x);}
            txtTotal.setValue((a+b+c+d+e));
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "ERROR: "+e,"ERROR",JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void agregarCostos(){
        try{
            Connection con;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            String sql = "insert into costosindirectos (Renta, Luz, Seguro, Impuestos,Fecha) values(?,?,?,?,?)";
            PreparedStatement pst = con.prepareStatement(sql);
            
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date d = new Date();
            String fecha = sdf.format(d);
            
            pst.setString(1, txtRenta.getText());
            pst.setString(2, txtLuz.getText());
            pst.setString(3, txtSeguro.getText());
            pst.setString(4, txtImpuestos.getText());
            pst.setString(5, fecha);
            
            int n = pst.executeUpdate();
            
            if(n > 0){
                JOptionPane.showMessageDialog(this, "Datos guardados");
            }else{
                JOptionPane.showMessageDialog(this, "No se guardaron los datos","ERROR",JOptionPane.ERROR_MESSAGE);
            }
            
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "ERROR: "+e,"ERROR",JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void agregarProyectos(String tipo){
        try{
            limpiarTablaIndirectos();
            limpiarTablaPrincipal();
            Connection con;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            String sql = "select Proyecto, Estatus, Costo,Moneda from proyectos where Estatus like 'EN PROCESO'";
            if(tipo.equals("proyecto")){
                sql = "select Proyecto, Estatus, Costo,Moneda from proyectos where Proyecto like '" + txtProyecto.getText() + "'";
            }
            ResultSet rs = st.executeQuery(sql);
            String dat[] = new String[10];
            String dat2[] = new String[10];
            DefaultTableModel miModelo = (DefaultTableModel) TablaIndirecto.getModel();
            DefaultTableModel miModelo2 = (DefaultTableModel) TablaPrincipal.getModel();
            DecimalFormat formato = new DecimalFormat("#,###.##");
            String precio = null;
            String moneda = null;
            while(rs.next()){
                dat[0] = rs.getString("Proyecto");
                dat[1] = rs.getString("Costo");
                dat[7] = rs.getString("Moneda");
                moneda = dat[7];
                precio = dat[1];
                if(dat[7] == null){
                    dat[7] = "";
                }
                if(dat[7].equals("DLLS")){
                    String sql2 = "select *from preciodolar";
                    Statement st2 = con.createStatement();
                    ResultSet rs2 = st2.executeQuery(sql2);
                    double dolar = 0;
                    while(rs2.next()){
                        dolar = Double.parseDouble(rs2.getString("Precio"));
                    }
                    double tot;
                    try{tot = dolar * (Double.parseDouble(dat[1].replaceAll("[^0-9.]", "")));}catch(Exception e){tot = 0;}
                    dat[1] = String.valueOf(formato.format(tot));
                }
                String nombreProyecto = rs.getString("Proyecto");
                dat2[0] = nombreProyecto;

                if(tipo.equals("proyecto")){
                    Stack<String> pila = new Stack<>();
                    pila.push(lblCnc1.getText());
                    pila.push(lblFresa1.getText());
                    pila.push(lblTorno1.getText());
                    dat2[1] = lblHorasDiseno.getText();
                    dat2[2] = getDuracion(pila);
                    dat2[3] = lblIntegracion.getText();
                } else {
                    // Modo mes: calcular horas POR proyecto leyendo TablaHoras
                    dat2[1] = getHorasProyectoDeTabla(nombreProyecto, "Diseño");
                    dat2[2] = getHorasProyectoDeTabla(nombreProyecto, "Herramentista");
                    dat2[3] = getHorasProyectoDeTabla(nombreProyecto, "Electromecanico");
                }

                miModelo.addRow(dat);
                miModelo2.addRow(dat2);
            }
            String sql2 = "select * from costohoras";
            Statement st2 = con.createStatement();
            ResultSet rs2 = st2.executeQuery(sql2);
            double precioDiseno = 0;
            double precioHerramentista = 0;
            double precioElectro = 0;
            while(rs2.next()){
                precioDiseno = rs2.getDouble("MODDiseño");
                precioHerramentista = rs2.getDouble("MODHerramentista");
                precioElectro = rs2.getDouble("MODElectromecanico");
            }

            // Precargar precio y moneda de TODOS los proyectos en un mapa (modo mes)
            java.util.Map<String, String[]> datosPorProyecto = new java.util.HashMap<>();
            if(!tipo.equals("proyecto")){
                ResultSet rsTodos = con.createStatement().executeQuery("SELECT Proyecto, Costo, Moneda FROM proyectos");
                while(rsTodos.next()){
                    datosPorProyecto.put(
                        rsTodos.getString("Proyecto"),
                        new String[]{ rsTodos.getString("Costo"), rsTodos.getString("Moneda") }
                    );
                }
                rsTodos.close();
            }
            // Calcular columnas numéricas para cada fila (aplica a proyecto y a mes)
            for (int i = 0; i < TablaPrincipal.getRowCount(); i++) {
                try {
                    // Mano de obra (col 4) 
                    String horasDis[]  = TablaPrincipal.getValueAt(i, 1).toString().split(":");
                    String horasHerr[] = TablaPrincipal.getValueAt(i, 2).toString().split(":");
                    String horasEle[]  = TablaPrincipal.getValueAt(i, 3).toString().split(":");
                    double totalPrecio = (precioDiseno       * Double.parseDouble(horasDis[0]))  + ((Double.parseDouble(horasDis[1])  * precioDiseno)       / 60);
                    totalPrecio       += (precioHerramentista * Double.parseDouble(horasHerr[0])) + ((Double.parseDouble(horasHerr[1]) * precioHerramentista) / 60);
                    totalPrecio       += (precioElectro       * Double.parseDouble(horasEle[0]))  + ((Double.parseDouble(horasEle[1])  * precioElectro)       / 60);
                    TablaPrincipal.setValueAt(formato.format(totalPrecio), i, 4);

                    // Nombre del proyecto en esta fila (para filtrar en modo mes)
                    String proyFila = TablaPrincipal.getValueAt(i, 0).toString();

                    // Precio proyecto (col 8) y Gasto indirecto (col 5) 
                    // En modo mes se busca el precio de la BD para cada proyecto de la fila
                    String precioFila  = precio;   // valor que ya tenía (modo proyecto, 1 sola fila)
                    String monedaFila  = moneda;
                    if (!tipo.equals("proyecto")) {
                        // Leer del mapa precargado, sin consulta a BD
                        String[] dp = datosPorProyecto.get(proyFila);
                        precioFila  = (dp != null) ? dp[0] : null;
                        monedaFila  = (dp != null) ? dp[1] : null;
                    }

                    double precioProyecto = 0;
                    double precioIndirecto = 0;
                    if (precioFila != null && !precioFila.isEmpty()) {
                        precioProyecto = Double.parseDouble(precioFila.replaceAll("[^0-9.]", ""));
                        precioIndirecto = precioProyecto * 0.10;
                        if (monedaFila != null && monedaFila.equals("DLLS")) {
                            double dolar = Double.parseDouble(lblPrecioDolar.getText());
                            precioProyecto  *= dolar;
                            precioIndirecto *= dolar;
                        }
                        TablaPrincipal.setValueAt(formato.format(precioProyecto), i, 8);
                    }
                    TablaPrincipal.setValueAt(formato.format(precioIndirecto), i, 5);

                    // Materia prima (col 6)
                    double costoMP = 0;
                    if (tipo.equals("proyecto")) {
                        // Modo proyecto: usar totales globales de los labels
                        costoMP = Double.parseDouble(lblTotalCompras.getText().replace(",", ""))
                                + Double.parseDouble(lblTotalAlmacen.getText().replace(",", ""));
                        for (int j = 0; j < TablaMaquinados.getRowCount(); j++) {
                            try { costoMP += Double.parseDouble(TablaMaquinados.getValueAt(j, 6).toString()); } catch (Exception e) {}
                        }
                    } else {
                        // Modo mes: sumar solo los materiales que pertenecen a este proyecto
                        for (int j = 0; j < TablaOrdenes.getRowCount(); j++) {
                            Object pCol = TablaOrdenes.getValueAt(j, 11); // columna "Proyecto"
                            if (pCol != null && pCol.toString().equals(proyFila)) {
                                try { costoMP += Double.parseDouble(TablaOrdenes.getValueAt(j, 7).toString().replace(",", "")); } catch (Exception e) {}
                            }
                        }
                        for (int j = 0; j < TablaAlmacen.getRowCount(); j++) {
                            Object pCol = TablaAlmacen.getValueAt(j, 2); // columna "PROYECTO"
                            if (pCol != null && pCol.toString().equals(proyFila)) {
                                try { costoMP += Double.parseDouble(TablaAlmacen.getValueAt(j, 6).toString().replace(",", "")); } catch (Exception e) {}
                                try { costoMP += Double.parseDouble(TablaAlmacen.getValueAt(j, 7).toString().replace(",", "")) * Double.parseDouble(lblPrecioDolar.getText()); } catch (Exception e) {}
                            }
                        }
                        for (int j = 0; j < TablaMaquinados.getRowCount(); j++) {
                            Object pCol = TablaMaquinados.getValueAt(j, 1); // columna "Proyecto"
                            if (pCol != null && pCol.toString().equals(proyFila)) {
                                try { costoMP += Double.parseDouble(TablaMaquinados.getValueAt(j, 6).toString()); } catch (Exception e) {}
                            }
                        }
                    }
                    TablaPrincipal.setValueAt(formato.format(costoMP), i, 6);

                    // Total (col 7)
                    double total = totalPrecio + costoMP + precioIndirecto;
                    TablaPrincipal.setValueAt(formato.format(total), i, 7);

                    // Ganancia/pérdida y margen solo en modo proyecto
                    if (precioProyecto > 0) {
                        TablaPrincipal.setValueAt(formato.format(precioProyecto - total), i, 9);
                        TablaPrincipal.setValueAt(formato.format(((precioProyecto - total) / precioProyecto) * 100) + "%", i, 10);
                    }

                } catch (Exception ex) {
                    System.out.println("Error calculando fila " + i + ": " + ex);
                }
            }
            double total = 0, suma = 0;
            for (int i = 0; i < TablaIndirecto.getRowCount(); i++) {
                try{
                    suma = Double.parseDouble((String) TablaIndirecto.getValueAt(i, 1).toString().replaceAll("[^0-9.]", ""));
                    total += suma;
                }catch(Exception e){}
            }
            txtTotalPro.setText(String.valueOf(formato.format(total)));
            double totalP = Double.parseDouble(txtTotalPro.getText().replaceAll("[^0-9.]", ""));
            double totalT = Double.parseDouble(txtTotal.getText().replaceAll("[^0-9.]", ""));
            for (int i = 0; i < TablaIndirecto.getRowCount(); i++) {
                if(TablaIndirecto.getValueAt(i, 1) != null){
                    double an = 0;
                    try{
                        an = Double.parseDouble((String) TablaIndirecto.getValueAt(i, 1).toString().replaceAll("[^0-9.]", ""));
                    }catch(Exception e){}
                    double tota = (an * 100) / totalP;
                    TablaIndirecto.setValueAt(formato.format(tota), i, 2);
                    double to = (tota * totalT) / 100;
                    TablaIndirecto.setValueAt(formato.format(to), i, 3);
                }
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "ERROR: "+e,"ERROR",JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public String getHorasProyectoDeTabla(String proyecto, String departamento) {
        // Primero encontrar la fila del proyecto
        int filaProyecto = -1;
        for (int i = 0; i < TablaHoras.getRowCount(); i++) {
            Object val = TablaHoras.getValueAt(i, 0);
            if (val != null && val.toString().equals(proyecto)) {
                filaProyecto = i;
                break;
            }
        }
        if (filaProyecto < 0) return "0:00";
        
        int offset;
        switch (departamento) {
            case "Electromecanico": offset = 1; break;
            case "Herramentista":   offset = 2; break;
            case "Diseño":          offset = 3; break;
            case "Calidad":         offset = 4; break;
            default: return "0:00";
        }

        int fila = filaProyecto + offset;
        if (fila >= TablaHoras.getRowCount()) return "0:00";

        // Sumar todas las columnas de días (columnas 1 a 31)
        int totalMinutos = 0;
        for (int col = 1; col < TablaHoras.getColumnCount(); col++) {
            Object cell = TablaHoras.getValueAt(fila, col);
            if (cell != null && !cell.toString().isEmpty()) {
                totalMinutos += convertirAMinutos(cell.toString());
            }
        }
        int horas = totalMinutos / 60;
        int minutos = totalMinutos % 60;
        return String.format("%d:%02d", horas, minutos);
    }
    public void precioDolar(){
        try{
            Connection con;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            String sql = "select * from preciodolar";
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
                lblPrecioDolar.setText(rs.getString("Precio"));
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "ERROR: "+e,"ERROR",JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void agregarTotalNominas(){
        //13
        double total = 0;
        for (int i = 0; i < TablaNominas.getRowCount(); i++) {
            double precio;
            try{precio = Double.parseDouble(TablaNominas.getValueAt(i, 13).toString().replaceAll(",", ""));}catch(Exception e){precio = 0;}
            total += precio;
        }
        DecimalFormat formato = new DecimalFormat("#,###.##");
        txtNominas.setText(formato.format(total));
    }
    
    public void agregarNominas(String tipo){
        try{
            btnTablaNominas.setEnabled(true);
            String hs[] = new String[5];
            String s[] = new String[5];
            int cont = 0;
            for (int i = 3; i < numeroSemanas + 3; i++) {
                hs[cont] = "HS"+(empieza + i - 3);
                cont++;
            }
            cont = 0;
            for (int i = 8; i < numeroSemanas + 8; i++) {
                s[cont] = "S"+(empieza + i - 8);
                cont++;
            }
            limpiarTablaNominas(hs[0],hs[1],hs[2],hs[3],hs[4],s[0],s[1],s[2],s[3],s[4]);
            Connection con;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            JComboBox jcb = new JComboBox();
            jcb.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    try{
                        if(TablaNominas.getRowCount() > 0 && TablaNominas.getSelectedRow() >= 0){
                            if(TablaNominas.getValueAt(TablaNominas.getSelectedRow(), 2) != null){
                                String sql2 = "update empleadoscheck set Departamento = ? where Id = ?";
                                PreparedStatement pst = con.prepareStatement(sql2);
                                pst.setString(1, TablaNominas.getValueAt(TablaNominas.getSelectedRow(), 2).toString());
                                pst.setString(2, TablaNominas.getValueAt(TablaNominas.getSelectedRow(), 0).toString());
                                int n = pst.executeUpdate();

                                if(n < 1){
                                    JOptionPane.showMessageDialog(null, "No se guardaron los datos", "Error",JOptionPane.ERROR_MESSAGE);
                                }
                            }
                        }
                    }catch(SQLException e){
                        JOptionPane.showMessageDialog(null, "ERROR: "+e,"ERROR",JOptionPane.ERROR_MESSAGE);
                    }
                }
            });
            jcb.addItem("CI ADMINITRACION");
            jcb.addItem("MOD DISENADOR");
            jcb.addItem("MOD ELECTROMECANICO");
            jcb.addItem("MOD HERRAMENTISTA");
            jcb.addItem("MOI ALMACEN");
            jcb.addItem("MOI DISENO");
            jcb.addItem("MOI MANSAJERIA");
            jcb.addItem("MOI PROGRAMADOR");
            jcb.addItem("MOI TECNICO CALIDAD INDIRECTO");
            TableColumn tc = TablaNominas.getColumnModel().getColumn(2);
            TableCellEditor tce = new DefaultCellEditor(jcb);
            tc.setCellEditor(tce);
            
            SimpleDateFormat sdf = new SimpleDateFormat("MMMM", Locale.ENGLISH);
            SimpleDateFormat sdf2 = new SimpleDateFormat("MM");
            Date d = sdf.parse((String) cmbMes.getSelectedItem());
            String mes = sdf2.format(d);
            String anio = cmbAnio.getSelectedItem().toString();
            
            String sql2 = "SELECT COUNT(*) AS total FROM costonominas where Mes like '"+mes+"' and Anio like '"+anio+"'";
            Statement st2 = con.createStatement();
            ResultSet rs2 = st2.executeQuery(sql2);
            int total = 0;
            if(rs2.next()){
                total = rs2.getInt("total");
            }
            
            if(total > 0){
                btnTablaNominas.setEnabled(false);
                String sql = "select * from costonominas where Mes like '"+mes+"' and Anio like '"+anio+"'";
                ResultSet rs = st.executeQuery(sql);
                DefaultTableModel miModelo = (DefaultTableModel) TablaNominas.getModel();
                String dat[] = new String[20];
                while(rs.next()){
                    dat[0] = rs.getString("Empleado");
                    String sql3 = "select Nombre, Departamento from empleadoscheck where NumEmpleado like '"+dat[0]+"'";
                    Statement st3 = con.createStatement();
                    ResultSet rs3 = st3.executeQuery(sql3);
                    dat[1] = "";
                    dat[2] = "";
                    while(rs3.next()){
                        dat[1] = rs3.getString("Nombre");
                        dat[1] = "*";
                        dat[2] = rs3.getString("Departamento");
                    }
                    dat[3] = rs.getString("HS1");
                    dat[4] = rs.getString("HS2");
                    dat[5] = rs.getString("HS3");
                    dat[6] = rs.getString("HS4");
                    dat[7] = rs.getString("HS5");
                    dat[8] = rs.getString("S1");
                    dat[9] = rs.getString("S2");
                    dat[10] = rs.getString("S3");
                    dat[11] = rs.getString("S4");
                    dat[12] = rs.getString("S5");
                    miModelo.addRow(dat);
                }
            }else{
                btnTablaNominas.setEnabled(true);
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "ERROR: "+e,"ERROR",JOptionPane.ERROR_MESSAGE);
        } catch (ParseException ex) {
            Logger.getLogger(Costos.class.getName()).log(Level.SEVERE, null, ex);
        }
        agregarTotalNominas();
    }
    
    public void addFechas(){
        lblFecha.setText("<HTML>"
                + "<span><span style='color:rgb(0, 103, 199);'><b>D</b>esde: </span> <span style='font-size: 12px;'> <b>"+fechaInicio+"</b> </span> <span style='color:#0068c9;'><b>H</b>asta: </span><span style='font-size: 12px;'> <b>"+fechaFinal+"</b> </span>"
                + "<html>");
    }
    
    public Costos(String id) {
        initComponents();
        this.id = id;
        insertarSemanas();
        ((javax.swing.plaf.basic.BasicInternalFrameUI) this.getUI()).setNorthPane(null);
        
        enchular(TablaHoras,jScrollPane2);
        enchular(TablaPrincipal,jScrollPane3);
        enchular(TablaOrdenes,jScrollPane4);
        enchular(TablaAlmacen,jScrollPane5);
        enchular(TablaIndirecto,jScrollPane7);
        enchular(TablaNominas,jScrollPane8);
        enchular(TablaMaquinados,jScrollPane9);
        enchular(TablaIntegracion,jScrollPane10);
        enchular(TablaDiseño,jScrollPane11);
        enchular(TablaCalidad,jScrollPane12);
        
        setTabbed(tabbed);
        setTabbed(tabbed2);
        precioDolar();
        autoCompletar();
        getAllEmployes();
        panelProyecto.setVisible(false);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPopupMenu1 = new javax.swing.JPopupMenu();
        pegar = new javax.swing.JMenuItem();
        jPanel1 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        pan = new javax.swing.JPanel();
        panelSalir = new javax.swing.JPanel();
        lblSalir = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jPanel20 = new javax.swing.JPanel();
        panelMeses = new javax.swing.JPanel();
        cmbMes = new RSMaterialComponent.RSComboBoxMaterial();
        cmbAnio = new RSMaterialComponent.RSComboBoxMaterial();
        panelProyecto = new javax.swing.JPanel();
        txtProyecto = new RSMaterialComponent.RSTextFieldMaterial();
        jPanel21 = new javax.swing.JPanel();
        jTextField1 = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        lblPrecioDolar = new javax.swing.JLabel();
        lblPrecioDolar1 = new javax.swing.JLabel();
        jPanel24 = new javax.swing.JPanel();
        rbMes = new javax.swing.JRadioButton();
        rbProyecto = new javax.swing.JRadioButton();
        jPanel7 = new javax.swing.JPanel();
        tabbed = new javax.swing.JTabbedPane();
        jPanel11 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        TablaPrincipal = new TablaPrincipalCostos();
        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel8 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        tabbed2 = new javax.swing.JTabbedPane();
        jPanel27 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        TablaHoras = new javax.swing.JTable();
        jLabel22 = new javax.swing.JLabel();
        jPanel28 = new javax.swing.JPanel();
        jScrollPane9 = new javax.swing.JScrollPane();
        TablaMaquinados = new javax.swing.JTable();
        jPanel32 = new javax.swing.JPanel();
        btnGuardar1 = new scrollPane.BotonRedondo();
        jPanel37 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        jPanel38 = new javax.swing.JPanel();
        lblCnc = new javax.swing.JLabel();
        lblCnc1 = new javax.swing.JLabel();
        lblFresa = new javax.swing.JLabel();
        lblFresa1 = new javax.swing.JLabel();
        lblTorno = new javax.swing.JLabel();
        lblTorno1 = new javax.swing.JLabel();
        btnEdit = new scrollPane.BotonRedondo();
        jPanel29 = new javax.swing.JPanel();
        jScrollPane10 = new javax.swing.JScrollPane();
        TablaIntegracion = new javax.swing.JTable();
        jPanel42 = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        jPanel43 = new javax.swing.JPanel();
        lblCnc2 = new javax.swing.JLabel();
        lblIntegracion = new javax.swing.JLabel();
        jPanel30 = new javax.swing.JPanel();
        jScrollPane11 = new javax.swing.JScrollPane();
        TablaDiseño = new javax.swing.JTable();
        jPanel40 = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        jPanel41 = new javax.swing.JPanel();
        jlabel = new javax.swing.JLabel();
        lblHorasDiseno = new javax.swing.JLabel();
        jPanel31 = new javax.swing.JPanel();
        jScrollPane12 = new javax.swing.JScrollPane();
        TablaCalidad = new javax.swing.JTable();
        jPanel39 = new javax.swing.JPanel();
        jLabel21 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jPanel33 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel34 = new javax.swing.JPanel();
        jLabel23 = new javax.swing.JLabel();
        cmbSemanas = new javax.swing.JComboBox<>();
        lblFec = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        cmbDias = new javax.swing.JComboBox<>();
        jPanel12 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        TablaOrdenes = new javax.swing.JTable();
        jPanel35 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jPanel44 = new javax.swing.JPanel();
        lblTotalCompras1 = new javax.swing.JLabel();
        lblTotalCompras = new javax.swing.JLabel();
        jPanel13 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        TablaAlmacen = new javax.swing.JTable();
        jPanel36 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jPanel45 = new javax.swing.JPanel();
        lblTotalAlmacen1 = new javax.swing.JLabel();
        lblTotalAlmacen = new javax.swing.JLabel();
        jPanel14 = new javax.swing.JPanel();
        jPanel22 = new javax.swing.JPanel();
        jScrollPane8 = new javax.swing.JScrollPane();
        TablaNominas = new ConfTabla();
        jPanel23 = new javax.swing.JPanel();
        btnGuardar = new javax.swing.JButton();
        jPanel25 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jPanel26 = new javax.swing.JPanel();
        btnTablaNominas = new javax.swing.JButton();
        btnCostoHoras = new javax.swing.JButton();
        btnEditarEmpleado = new javax.swing.JButton();
        jPanel10 = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        jPanel15 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jPanel16 = new javax.swing.JPanel();
        jPanel17 = new javax.swing.JPanel();
        jPanel18 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        txtRenta = new javax.swing.JFormattedTextField();
        jLabel9 = new javax.swing.JLabel();
        txtLuz = new javax.swing.JFormattedTextField();
        jLabel10 = new javax.swing.JLabel();
        txtSeguro = new javax.swing.JFormattedTextField();
        jLabel11 = new javax.swing.JLabel();
        txtImpuestos = new javax.swing.JFormattedTextField();
        jLabel13 = new javax.swing.JLabel();
        txtNominas = new javax.swing.JFormattedTextField();
        jLabel14 = new javax.swing.JLabel();
        txtTotal = new javax.swing.JFormattedTextField();
        jLabel18 = new javax.swing.JLabel();
        txtTotalPro = new javax.swing.JFormattedTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jPanel19 = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        TablaIndirecto = new javax.swing.JTable();
        lblFecha = new javax.swing.JLabel();

        pegar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/copia.png"))); // NOI18N
        pegar.setText("Pegar                                          ");
        pegar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pegarActionPerformed(evt);
            }
        });
        jPopupMenu1.add(pegar);

        setBorder(null);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new java.awt.BorderLayout());

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setLayout(new java.awt.BorderLayout());

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));

        jLabel12.setFont(new java.awt.Font("Lexend", 1, 24)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(0, 102, 204));
        jLabel12.setText("Costos");
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

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setLayout(new java.awt.BorderLayout());

        jPanel20.setBackground(new java.awt.Color(255, 255, 255));

        panelMeses.setBackground(new java.awt.Color(255, 255, 255));

        cmbMes.setFont(new java.awt.Font("Lexend", 1, 14)); // NOI18N
        cmbMes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbMesActionPerformed(evt);
            }
        });
        panelMeses.add(cmbMes);

        cmbAnio.setFont(new java.awt.Font("Lexend", 1, 14)); // NOI18N
        cmbAnio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbAnioActionPerformed(evt);
            }
        });
        panelMeses.add(cmbAnio);

        jPanel20.add(panelMeses);

        panelProyecto.setBackground(new java.awt.Color(255, 255, 255));

        txtProyecto.setForeground(new java.awt.Color(51, 51, 51));
        txtProyecto.setCaretColor(new java.awt.Color(102, 102, 102));
        txtProyecto.setColorMaterial(new java.awt.Color(204, 204, 204));
        txtProyecto.setFont(new java.awt.Font("Lexend", 1, 14)); // NOI18N
        txtProyecto.setPhColor(new java.awt.Color(153, 204, 255));
        txtProyecto.setPlaceholder("Proyecto");
        txtProyecto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtProyectoActionPerformed(evt);
            }
        });
        panelProyecto.add(txtProyecto);

        jPanel20.add(panelProyecto);

        jPanel3.add(jPanel20, java.awt.BorderLayout.CENTER);

        jPanel21.setBackground(new java.awt.Color(255, 255, 255));
        jPanel21.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        jTextField1.setEditable(false);
        jTextField1.setBackground(new java.awt.Color(255, 255, 255));
        jTextField1.setFont(new java.awt.Font("Lexend", 1, 10)); // NOI18N
        jTextField1.setForeground(new java.awt.Color(153, 0, 0));
        jTextField1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField1.setText("Nota: precio obtenido de pagina (https://www.cotizacion.co/mexico/precio-del-dolar-en-ciudad-juarez-chihuahua.php/)");
        jTextField1.setBorder(null);
        jPanel21.add(jTextField1);

        jLabel15.setFont(new java.awt.Font("Lexend", 1, 14)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(51, 51, 51));
        jLabel15.setText("Precio Dolar (Venta): ");
        jPanel21.add(jLabel15);

        jLabel16.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(51, 51, 51));
        jLabel16.setText("$");
        jPanel21.add(jLabel16);

        lblPrecioDolar.setFont(new java.awt.Font("Lexend", 1, 14)); // NOI18N
        lblPrecioDolar.setForeground(new java.awt.Color(51, 51, 51));
        lblPrecioDolar.setText("00.00");
        lblPrecioDolar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel21.add(lblPrecioDolar);

        lblPrecioDolar1.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        lblPrecioDolar1.setForeground(new java.awt.Color(51, 51, 51));
        lblPrecioDolar1.setText("    ");
        jPanel21.add(lblPrecioDolar1);

        jPanel3.add(jPanel21, java.awt.BorderLayout.SOUTH);

        jPanel24.setBackground(new java.awt.Color(255, 255, 255));

        rbMes.setBackground(new java.awt.Color(255, 255, 255));
        buttonGroup1.add(rbMes);
        rbMes.setFont(new java.awt.Font("Lexend", 0, 14)); // NOI18N
        rbMes.setForeground(new java.awt.Color(51, 153, 255));
        rbMes.setSelected(true);
        rbMes.setText("Ver por mes");
        rbMes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbMesActionPerformed(evt);
            }
        });
        jPanel24.add(rbMes);

        rbProyecto.setBackground(new java.awt.Color(255, 255, 255));
        buttonGroup1.add(rbProyecto);
        rbProyecto.setFont(new java.awt.Font("Lexend", 0, 14)); // NOI18N
        rbProyecto.setForeground(new java.awt.Color(51, 153, 255));
        rbProyecto.setText("Ver por proyecto");
        rbProyecto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbProyectoActionPerformed(evt);
            }
        });
        jPanel24.add(rbProyecto);

        jPanel3.add(jPanel24, java.awt.BorderLayout.PAGE_START);

        jPanel2.add(jPanel3, java.awt.BorderLayout.PAGE_START);

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setLayout(new java.awt.BorderLayout());

        tabbed.setBackground(new java.awt.Color(255, 255, 255));
        tabbed.setForeground(new java.awt.Color(200, 200, 200));
        tabbed.setFont(new java.awt.Font("Roboto", 0, 16)); // NOI18N
        tabbed.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                tabbedStateChanged(evt);
            }
        });

        jPanel11.setBackground(new java.awt.Color(255, 255, 255));
        jPanel11.setLayout(new java.awt.BorderLayout());

        TablaPrincipal.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Proyecto", "Diseño", "Herramentista", "Electromecanico", "Mano de obra", "Gasto Indirecto", "Materia prima", "Total", "Precio proyecto", "Ganancia o perdida", "Margen Beneficio"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        TablaPrincipal.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TablaPrincipalMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(TablaPrincipal);

        jPanel11.add(jScrollPane3, java.awt.BorderLayout.CENTER);

        tabbed.addTab("", new javax.swing.ImageIcon(getClass().getResource("/Iconos/casa.png")), jPanel11, "Principal"); // NOI18N

        jScrollPane1.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane1.setBorder(null);

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));
        jPanel8.setLayout(new java.awt.BorderLayout());

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));
        jPanel9.setLayout(new java.awt.BorderLayout());

        tabbed2.setBackground(new java.awt.Color(255, 255, 255));
        tabbed2.setForeground(new java.awt.Color(255, 255, 255));
        tabbed2.setTabPlacement(javax.swing.JTabbedPane.LEFT);

        jPanel27.setBackground(new java.awt.Color(255, 255, 255));
        jPanel27.setLayout(new java.awt.BorderLayout());

        jScrollPane2.setBorder(null);

        TablaHoras.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Proyecto", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        TablaHoras.setSelectionBackground(new java.awt.Color(0, 102, 255));
        TablaHoras.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        TablaHoras.setShowGrid(true);
        jScrollPane2.setViewportView(TablaHoras);
        if (TablaHoras.getColumnModel().getColumnCount() > 0) {
            TablaHoras.getColumnModel().getColumn(0).setMinWidth(150);
            TablaHoras.getColumnModel().getColumn(0).setPreferredWidth(150);
            TablaHoras.getColumnModel().getColumn(0).setMaxWidth(150);
            TablaHoras.getColumnModel().getColumn(0).setHeaderValue("Proyecto");
            TablaHoras.getColumnModel().getColumn(1).setHeaderValue("1");
            TablaHoras.getColumnModel().getColumn(2).setHeaderValue("2");
            TablaHoras.getColumnModel().getColumn(3).setHeaderValue("3");
            TablaHoras.getColumnModel().getColumn(4).setHeaderValue("4");
            TablaHoras.getColumnModel().getColumn(5).setHeaderValue("5");
            TablaHoras.getColumnModel().getColumn(6).setHeaderValue("6");
            TablaHoras.getColumnModel().getColumn(7).setHeaderValue("7");
            TablaHoras.getColumnModel().getColumn(8).setHeaderValue("8");
            TablaHoras.getColumnModel().getColumn(9).setHeaderValue("9");
            TablaHoras.getColumnModel().getColumn(10).setHeaderValue("10");
            TablaHoras.getColumnModel().getColumn(11).setHeaderValue("11");
            TablaHoras.getColumnModel().getColumn(12).setHeaderValue("12");
            TablaHoras.getColumnModel().getColumn(13).setHeaderValue("13");
            TablaHoras.getColumnModel().getColumn(14).setHeaderValue("14");
            TablaHoras.getColumnModel().getColumn(15).setHeaderValue("15");
            TablaHoras.getColumnModel().getColumn(16).setHeaderValue("16");
            TablaHoras.getColumnModel().getColumn(17).setHeaderValue("17");
            TablaHoras.getColumnModel().getColumn(18).setHeaderValue("18");
            TablaHoras.getColumnModel().getColumn(19).setHeaderValue("19");
            TablaHoras.getColumnModel().getColumn(20).setHeaderValue("20");
            TablaHoras.getColumnModel().getColumn(21).setHeaderValue("21");
            TablaHoras.getColumnModel().getColumn(22).setHeaderValue("22");
            TablaHoras.getColumnModel().getColumn(23).setHeaderValue("23");
            TablaHoras.getColumnModel().getColumn(24).setHeaderValue("24");
            TablaHoras.getColumnModel().getColumn(25).setHeaderValue("25");
            TablaHoras.getColumnModel().getColumn(26).setHeaderValue("26");
            TablaHoras.getColumnModel().getColumn(27).setHeaderValue("27");
            TablaHoras.getColumnModel().getColumn(28).setHeaderValue("28");
            TablaHoras.getColumnModel().getColumn(29).setHeaderValue("29");
            TablaHoras.getColumnModel().getColumn(30).setHeaderValue("30");
            TablaHoras.getColumnModel().getColumn(31).setHeaderValue("31");
        }

        jPanel27.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        jLabel22.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(153, 0, 0));
        jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel22.setText("Todos");
        jPanel27.add(jLabel22, java.awt.BorderLayout.PAGE_START);

        tabbed2.addTab("", new javax.swing.ImageIcon(getClass().getResource("/Iconos/all_16.png")), jPanel27); // NOI18N

        jPanel28.setBackground(new java.awt.Color(255, 255, 255));
        jPanel28.setLayout(new java.awt.BorderLayout());

        jScrollPane9.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));

        TablaMaquinados.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Empleado", "Proyecto", "Horas", "Dimensiones", "Material", "Cantidad", "Costo", "ID", "Fecha", "Plano", "Maquina"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, true, false, false, true, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane9.setViewportView(TablaMaquinados);
        if (TablaMaquinados.getColumnModel().getColumnCount() > 0) {
            TablaMaquinados.getColumnModel().getColumn(7).setMinWidth(0);
            TablaMaquinados.getColumnModel().getColumn(7).setPreferredWidth(0);
            TablaMaquinados.getColumnModel().getColumn(7).setMaxWidth(0);
        }

        jPanel28.add(jScrollPane9, java.awt.BorderLayout.CENTER);

        jPanel32.setBackground(new java.awt.Color(255, 255, 255));

        btnGuardar1.setBackground(new java.awt.Color(255, 255, 255));
        btnGuardar1.setForeground(new java.awt.Color(51, 153, 255));
        btnGuardar1.setText("Guardar");
        btnGuardar1.setBorderColor(new java.awt.Color(51, 153, 255));
        btnGuardar1.setBorderColorSelected(new java.awt.Color(0, 51, 153));
        btnGuardar1.setColor(new java.awt.Color(51, 153, 255));
        btnGuardar1.setFont(new java.awt.Font("Lexend", 0, 14)); // NOI18N
        btnGuardar1.setPreferredSize(new java.awt.Dimension(300, 35));
        btnGuardar1.setThickness(3);
        btnGuardar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardar1ActionPerformed(evt);
            }
        });
        jPanel32.add(btnGuardar1);

        jPanel28.add(jPanel32, java.awt.BorderLayout.PAGE_END);

        jPanel37.setBackground(new java.awt.Color(255, 255, 255));
        jPanel37.setLayout(new java.awt.BorderLayout());

        jLabel17.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(153, 0, 0));
        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel17.setText("Maquinados");
        jPanel37.add(jLabel17, java.awt.BorderLayout.PAGE_START);

        jPanel38.setBackground(new java.awt.Color(255, 255, 255));
        jPanel38.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 40, 5));

        lblCnc.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        lblCnc.setForeground(new java.awt.Color(0, 102, 255));
        lblCnc.setText("Horas Cnc: ");
        jPanel38.add(lblCnc);

        lblCnc1.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        lblCnc1.setForeground(new java.awt.Color(0, 102, 255));
        lblCnc1.setText("0");
        jPanel38.add(lblCnc1);

        lblFresa.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        lblFresa.setForeground(new java.awt.Color(255, 102, 0));
        lblFresa.setText("Horas Fresadora:  ");
        jPanel38.add(lblFresa);

        lblFresa1.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        lblFresa1.setForeground(new java.awt.Color(255, 102, 0));
        lblFresa1.setText("0");
        jPanel38.add(lblFresa1);

        lblTorno.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        lblTorno.setForeground(new java.awt.Color(0, 204, 0));
        lblTorno.setText("Horas Torno: ");
        jPanel38.add(lblTorno);

        lblTorno1.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        lblTorno1.setForeground(new java.awt.Color(0, 204, 0));
        lblTorno1.setText("0");
        jPanel38.add(lblTorno1);

        btnEdit.setForeground(new java.awt.Color(250, 0, 0));
        btnEdit.setText("Editar Materiales");
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });
        jPanel38.add(btnEdit);

        jPanel37.add(jPanel38, java.awt.BorderLayout.CENTER);

        jPanel28.add(jPanel37, java.awt.BorderLayout.NORTH);

        tabbed2.addTab("", new javax.swing.ImageIcon(getClass().getResource("/Iconos/torno_16.png")), jPanel28); // NOI18N

        jPanel29.setBackground(new java.awt.Color(255, 255, 255));
        jPanel29.setLayout(new java.awt.BorderLayout());

        jScrollPane10.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane10.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));

        TablaIntegracion.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Empleado", "Proyecto", "Horas", "Ocupacion", "ID", "Fecha"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane10.setViewportView(TablaIntegracion);
        if (TablaIntegracion.getColumnModel().getColumnCount() > 0) {
            TablaIntegracion.getColumnModel().getColumn(4).setMinWidth(0);
            TablaIntegracion.getColumnModel().getColumn(4).setPreferredWidth(0);
            TablaIntegracion.getColumnModel().getColumn(4).setMaxWidth(0);
        }

        jPanel29.add(jScrollPane10, java.awt.BorderLayout.CENTER);

        jPanel42.setBackground(new java.awt.Color(255, 255, 255));
        jPanel42.setLayout(new java.awt.BorderLayout());

        jLabel19.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(153, 0, 0));
        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel19.setText("Integracion");
        jPanel42.add(jLabel19, java.awt.BorderLayout.PAGE_START);

        jPanel43.setBackground(new java.awt.Color(255, 255, 255));

        lblCnc2.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        lblCnc2.setForeground(new java.awt.Color(0, 102, 255));
        lblCnc2.setText("Horas Integracion: ");
        jPanel43.add(lblCnc2);

        lblIntegracion.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        lblIntegracion.setForeground(new java.awt.Color(0, 102, 255));
        lblIntegracion.setText("0");
        jPanel43.add(lblIntegracion);

        jPanel42.add(jPanel43, java.awt.BorderLayout.CENTER);

        jPanel29.add(jPanel42, java.awt.BorderLayout.NORTH);

        tabbed2.addTab("", new javax.swing.ImageIcon(getClass().getResource("/Iconos/integracion_16.png")), jPanel29); // NOI18N

        jPanel30.setBackground(new java.awt.Color(255, 255, 255));
        jPanel30.setLayout(new java.awt.BorderLayout());

        jScrollPane11.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane11.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));

        TablaDiseño.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Empleado", "Proyecto", "Horas", "Comentarios", "ID", "Fecha"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane11.setViewportView(TablaDiseño);
        if (TablaDiseño.getColumnModel().getColumnCount() > 0) {
            TablaDiseño.getColumnModel().getColumn(4).setMinWidth(0);
            TablaDiseño.getColumnModel().getColumn(4).setPreferredWidth(0);
            TablaDiseño.getColumnModel().getColumn(4).setMaxWidth(0);
        }

        jPanel30.add(jScrollPane11, java.awt.BorderLayout.CENTER);

        jPanel40.setBackground(new java.awt.Color(255, 255, 255));
        jPanel40.setLayout(new java.awt.BorderLayout());

        jLabel20.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(153, 0, 0));
        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel20.setText("Diseño");
        jPanel40.add(jLabel20, java.awt.BorderLayout.PAGE_START);

        jPanel41.setBackground(new java.awt.Color(255, 255, 255));

        jlabel.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jlabel.setForeground(new java.awt.Color(0, 102, 255));
        jlabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlabel.setText("Horas Diseno: ");
        jPanel41.add(jlabel);

        lblHorasDiseno.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        lblHorasDiseno.setForeground(new java.awt.Color(0, 102, 255));
        lblHorasDiseno.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblHorasDiseno.setText("0");
        jPanel41.add(lblHorasDiseno);

        jPanel40.add(jPanel41, java.awt.BorderLayout.PAGE_END);

        jPanel30.add(jPanel40, java.awt.BorderLayout.NORTH);

        tabbed2.addTab("", new javax.swing.ImageIcon(getClass().getResource("/Iconos/diseño_16.png")), jPanel30); // NOI18N

        jPanel31.setBackground(new java.awt.Color(255, 255, 255));
        jPanel31.setLayout(new java.awt.BorderLayout());

        jScrollPane12.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane12.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));

        TablaCalidad.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Empleado", "Proyecto", "Horas", "Comentarios", "ID", "Fecha"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane12.setViewportView(TablaCalidad);
        if (TablaCalidad.getColumnModel().getColumnCount() > 0) {
            TablaCalidad.getColumnModel().getColumn(4).setMinWidth(0);
            TablaCalidad.getColumnModel().getColumn(4).setPreferredWidth(0);
            TablaCalidad.getColumnModel().getColumn(4).setMaxWidth(0);
        }

        jPanel31.add(jScrollPane12, java.awt.BorderLayout.CENTER);

        jPanel39.setBackground(new java.awt.Color(255, 255, 255));
        jPanel39.setLayout(new java.awt.BorderLayout());

        jLabel21.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(153, 0, 0));
        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel21.setText("Calidad");
        jPanel39.add(jLabel21, java.awt.BorderLayout.PAGE_START);

        jLabel25.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(0, 102, 255));
        jLabel25.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel25.setText("jLabel25");
        jPanel39.add(jLabel25, java.awt.BorderLayout.CENTER);

        jPanel31.add(jPanel39, java.awt.BorderLayout.NORTH);

        tabbed2.addTab("", new javax.swing.ImageIcon(getClass().getResource("/Iconos/calidad_16.png")), jPanel31); // NOI18N

        jPanel9.add(tabbed2, java.awt.BorderLayout.CENTER);

        jPanel8.add(jPanel9, java.awt.BorderLayout.CENTER);

        jPanel33.setBackground(new java.awt.Color(255, 255, 255));
        jPanel33.setLayout(new java.awt.BorderLayout());

        jLabel1.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 51, 153));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Horas");
        jPanel33.add(jLabel1, java.awt.BorderLayout.PAGE_START);

        jPanel34.setBackground(new java.awt.Color(255, 255, 255));
        jPanel34.setLayout(new java.awt.GridBagLayout());

        jLabel23.setFont(new java.awt.Font("Lexend", 1, 12)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(102, 102, 102));
        jLabel23.setText("Seleccionar Semana:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(0, 7, 0, 7);
        jPanel34.add(jLabel23, gridBagConstraints);

        cmbSemanas.setBackground(new java.awt.Color(255, 255, 255));
        cmbSemanas.setFont(new java.awt.Font("Lexend", 1, 12)); // NOI18N
        cmbSemanas.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccionar" }));
        cmbSemanas.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(204, 204, 204)));
        cmbSemanas.setPreferredSize(new java.awt.Dimension(180, 25));
        cmbSemanas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbSemanasActionPerformed(evt);
            }
        });
        jPanel34.add(cmbSemanas, new java.awt.GridBagConstraints());

        lblFec.setFont(new java.awt.Font("Lexend", 1, 12)); // NOI18N
        lblFec.setForeground(new java.awt.Color(102, 102, 102));
        lblFec.setText("Fecha");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.insets = new java.awt.Insets(0, 7, 0, 7);
        jPanel34.add(lblFec, gridBagConstraints);

        jLabel24.setFont(new java.awt.Font("Lexend", 1, 12)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(102, 102, 102));
        jLabel24.setText("Seleccionar Dia:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(0, 7, 0, 7);
        jPanel34.add(jLabel24, gridBagConstraints);

        cmbDias.setBackground(new java.awt.Color(255, 255, 255));
        cmbDias.setFont(new java.awt.Font("Lexend", 1, 12)); // NOI18N
        cmbDias.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccionar" }));
        cmbDias.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(204, 204, 204)));
        cmbDias.setPreferredSize(new java.awt.Dimension(180, 25));
        cmbDias.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbDiasActionPerformed(evt);
            }
        });
        jPanel34.add(cmbDias, new java.awt.GridBagConstraints());

        jPanel33.add(jPanel34, java.awt.BorderLayout.CENTER);

        jPanel8.add(jPanel33, java.awt.BorderLayout.NORTH);

        jScrollPane1.setViewportView(jPanel8);

        tabbed.addTab("", new javax.swing.ImageIcon(getClass().getResource("/Iconos/reloj-de-pared.png")), jScrollPane1, "Horas"); // NOI18N

        jPanel12.setBackground(new java.awt.Color(255, 255, 255));
        jPanel12.setLayout(new java.awt.BorderLayout());

        TablaOrdenes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Requisicion", "PO", "N.P", "Descripcion", "Cantidad", "Moneda", "P.U", "Total", "Precio Recibido", "Precio Faltante", "Fecha Orden", "Fecha Recibido", "Proyecto"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        TablaOrdenes.setShowGrid(false);
        jScrollPane4.setViewportView(TablaOrdenes);

        jPanel12.add(jScrollPane4, java.awt.BorderLayout.CENTER);

        jPanel35.setBackground(new java.awt.Color(255, 255, 255));
        jPanel35.setLayout(new java.awt.BorderLayout());

        jLabel2.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 51, 153));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Ordenes de compra");
        jPanel35.add(jLabel2, java.awt.BorderLayout.PAGE_START);

        jPanel44.setBackground(new java.awt.Color(255, 255, 255));

        lblTotalCompras1.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        lblTotalCompras1.setForeground(new java.awt.Color(51, 51, 51));
        lblTotalCompras1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTotalCompras1.setText("Total Mxn:");
        jPanel44.add(lblTotalCompras1);

        lblTotalCompras.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        lblTotalCompras.setForeground(new java.awt.Color(51, 51, 51));
        lblTotalCompras.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTotalCompras.setText("0");
        jPanel44.add(lblTotalCompras);

        jPanel35.add(jPanel44, java.awt.BorderLayout.PAGE_END);

        jPanel12.add(jPanel35, java.awt.BorderLayout.NORTH);

        tabbed.addTab("", new javax.swing.ImageIcon(getClass().getResource("/Iconos/po.png")), jPanel12, "Ordenes de compra"); // NOI18N

        jPanel13.setBackground(new java.awt.Color(255, 255, 255));
        jPanel13.setLayout(new java.awt.BorderLayout());

        TablaAlmacen.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "CODIGO", "CANTIDAD", "PROYECTO", "REQUISITOR", "PRECIO MXN", "PRECIO USD", "TOTAL MXN", "TOTAL USD", "FECHA"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        TablaAlmacen.setShowGrid(false);
        jScrollPane5.setViewportView(TablaAlmacen);

        jPanel13.add(jScrollPane5, java.awt.BorderLayout.CENTER);

        jPanel36.setBackground(new java.awt.Color(255, 255, 255));
        jPanel36.setLayout(new java.awt.BorderLayout());

        jLabel3.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 51, 153));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Almacen");
        jPanel36.add(jLabel3, java.awt.BorderLayout.PAGE_START);

        jPanel45.setBackground(new java.awt.Color(255, 255, 255));

        lblTotalAlmacen1.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        lblTotalAlmacen1.setForeground(new java.awt.Color(51, 51, 51));
        lblTotalAlmacen1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTotalAlmacen1.setText("Total MXN:");
        jPanel45.add(lblTotalAlmacen1);

        lblTotalAlmacen.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        lblTotalAlmacen.setForeground(new java.awt.Color(51, 51, 51));
        lblTotalAlmacen.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTotalAlmacen.setText("0");
        jPanel45.add(lblTotalAlmacen);

        jPanel36.add(jPanel45, java.awt.BorderLayout.CENTER);

        jPanel13.add(jPanel36, java.awt.BorderLayout.NORTH);

        tabbed.addTab("", new javax.swing.ImageIcon(getClass().getResource("/Iconos/almacen.png")), jPanel13, "Almacen"); // NOI18N

        jPanel14.setBackground(new java.awt.Color(255, 255, 255));
        jPanel14.setLayout(new java.awt.BorderLayout());

        jPanel22.setBackground(new java.awt.Color(255, 255, 255));
        jPanel22.setLayout(new java.awt.BorderLayout());

        TablaNominas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Nombre", "Departamento", "HS1", "HS2", "HS3", "HS4", "HS5", "S1", "S2", "S3", "S4", "S5", "Sueldo por mes", "Hora al mes", "MOD"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        TablaNominas.setComponentPopupMenu(jPopupMenu1);
        TablaNominas.setSelectionForeground(new java.awt.Color(255, 255, 255));
        TablaNominas.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                TablaNominasMouseMoved(evt);
            }
        });
        TablaNominas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TablaNominasMouseClicked(evt);
            }
        });
        jScrollPane8.setViewportView(TablaNominas);
        if (TablaNominas.getColumnModel().getColumnCount() > 0) {
            TablaNominas.getColumnModel().getColumn(0).setMinWidth(35);
            TablaNominas.getColumnModel().getColumn(0).setPreferredWidth(35);
            TablaNominas.getColumnModel().getColumn(0).setMaxWidth(35);
            TablaNominas.getColumnModel().getColumn(1).setMinWidth(200);
            TablaNominas.getColumnModel().getColumn(1).setPreferredWidth(200);
            TablaNominas.getColumnModel().getColumn(1).setMaxWidth(200);
            TablaNominas.getColumnModel().getColumn(2).setMinWidth(200);
            TablaNominas.getColumnModel().getColumn(2).setPreferredWidth(200);
            TablaNominas.getColumnModel().getColumn(2).setMaxWidth(200);
        }

        jPanel22.add(jScrollPane8, java.awt.BorderLayout.CENTER);

        jPanel23.setBackground(new java.awt.Color(255, 255, 255));

        btnGuardar.setBackground(new java.awt.Color(255, 255, 255));
        btnGuardar.setFont(new java.awt.Font("Roboto", 1, 16)); // NOI18N
        btnGuardar.setForeground(new java.awt.Color(0, 102, 204));
        btnGuardar.setText("Guardar");
        btnGuardar.setBorder(null);
        btnGuardar.setBorderPainted(false);
        btnGuardar.setContentAreaFilled(false);
        btnGuardar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnGuardar.setFocusPainted(false);
        btnGuardar.setPreferredSize(new java.awt.Dimension(70, 35));
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });
        jPanel23.add(btnGuardar);

        jPanel22.add(jPanel23, java.awt.BorderLayout.PAGE_END);

        jPanel14.add(jPanel22, java.awt.BorderLayout.CENTER);

        jPanel25.setBackground(new java.awt.Color(255, 255, 255));
        jPanel25.setLayout(new java.awt.BorderLayout());

        jLabel4.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 51, 153));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Nomina");
        jPanel25.add(jLabel4, java.awt.BorderLayout.PAGE_START);

        jPanel26.setBackground(new java.awt.Color(255, 255, 255));
        jPanel26.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 15, 5));

        btnTablaNominas.setBackground(new java.awt.Color(255, 255, 255));
        btnTablaNominas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/tabla_16.png"))); // NOI18N
        btnTablaNominas.setToolTipText("Subir tabla de nominas");
        btnTablaNominas.setBorder(null);
        btnTablaNominas.setBorderPainted(false);
        btnTablaNominas.setContentAreaFilled(false);
        btnTablaNominas.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnTablaNominas.setEnabled(false);
        btnTablaNominas.setFocusPainted(false);
        btnTablaNominas.setMaximumSize(new java.awt.Dimension(30, 30));
        btnTablaNominas.setMinimumSize(new java.awt.Dimension(20, 20));
        btnTablaNominas.setPreferredSize(new java.awt.Dimension(20, 20));
        btnTablaNominas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTablaNominasActionPerformed(evt);
            }
        });
        jPanel26.add(btnTablaNominas);

        btnCostoHoras.setBackground(new java.awt.Color(255, 255, 255));
        btnCostoHoras.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/$_16.png"))); // NOI18N
        btnCostoHoras.setToolTipText("Editar costos de horas");
        btnCostoHoras.setBorder(null);
        btnCostoHoras.setBorderPainted(false);
        btnCostoHoras.setContentAreaFilled(false);
        btnCostoHoras.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCostoHoras.setFocusPainted(false);
        btnCostoHoras.setMaximumSize(new java.awt.Dimension(30, 30));
        btnCostoHoras.setMinimumSize(new java.awt.Dimension(20, 20));
        btnCostoHoras.setPreferredSize(new java.awt.Dimension(20, 20));
        btnCostoHoras.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCostoHorasActionPerformed(evt);
            }
        });
        jPanel26.add(btnCostoHoras);

        btnEditarEmpleado.setBackground(new java.awt.Color(255, 255, 255));
        btnEditarEmpleado.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/editarEmpleado_16.png"))); // NOI18N
        btnEditarEmpleado.setToolTipText("Editar Empleado");
        btnEditarEmpleado.setBorder(null);
        btnEditarEmpleado.setBorderPainted(false);
        btnEditarEmpleado.setContentAreaFilled(false);
        btnEditarEmpleado.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEditarEmpleado.setFocusPainted(false);
        btnEditarEmpleado.setMaximumSize(new java.awt.Dimension(30, 30));
        btnEditarEmpleado.setMinimumSize(new java.awt.Dimension(20, 20));
        btnEditarEmpleado.setPreferredSize(new java.awt.Dimension(20, 20));
        btnEditarEmpleado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarEmpleadoActionPerformed(evt);
            }
        });
        jPanel26.add(btnEditarEmpleado);

        jPanel25.add(jPanel26, java.awt.BorderLayout.CENTER);

        jPanel14.add(jPanel25, java.awt.BorderLayout.PAGE_START);

        tabbed.addTab("", new javax.swing.ImageIcon(getClass().getResource("/Iconos/nomina.png")), jPanel14, "Nominas"); // NOI18N

        jPanel10.setBackground(new java.awt.Color(255, 255, 255));
        jPanel10.setLayout(new java.awt.BorderLayout());

        jScrollPane6.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane6.setBorder(null);

        jPanel15.setBackground(new java.awt.Color(255, 255, 255));
        jPanel15.setLayout(new java.awt.BorderLayout());

        jLabel6.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 51, 153));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Costo indirecto");
        jPanel15.add(jLabel6, java.awt.BorderLayout.PAGE_START);

        jPanel16.setBackground(new java.awt.Color(255, 255, 255));
        jPanel16.setLayout(new java.awt.BorderLayout());

        jPanel17.setBackground(new java.awt.Color(255, 255, 255));
        jPanel17.setLayout(new java.awt.GridLayout(1, 0));

        jPanel18.setBackground(new java.awt.Color(255, 255, 255));
        jPanel18.setLayout(new java.awt.GridLayout(7, 2, 10, 10));

        jLabel8.setFont(new java.awt.Font("Lexend", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(51, 51, 51));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("Renta:");
        jPanel18.add(jLabel8);

        txtRenta.setBackground(new java.awt.Color(255, 255, 255));
        txtRenta.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(204, 204, 204)));
        txtRenta.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        txtRenta.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtRenta.setFont(new java.awt.Font("Lexend", 0, 14)); // NOI18N
        txtRenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtRentaActionPerformed(evt);
            }
        });
        jPanel18.add(txtRenta);

        jLabel9.setFont(new java.awt.Font("Lexend", 1, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(51, 51, 51));
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("Luz:");
        jPanel18.add(jLabel9);

        txtLuz.setBackground(new java.awt.Color(255, 255, 255));
        txtLuz.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(204, 204, 204)));
        txtLuz.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        txtLuz.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtLuz.setFont(new java.awt.Font("Lexend", 0, 14)); // NOI18N
        txtLuz.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtLuzActionPerformed(evt);
            }
        });
        jPanel18.add(txtLuz);

        jLabel10.setFont(new java.awt.Font("Lexend", 1, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(51, 51, 51));
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("Seguro:");
        jPanel18.add(jLabel10);

        txtSeguro.setBackground(new java.awt.Color(255, 255, 255));
        txtSeguro.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(204, 204, 204)));
        txtSeguro.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        txtSeguro.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtSeguro.setFont(new java.awt.Font("Lexend", 0, 14)); // NOI18N
        txtSeguro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSeguroActionPerformed(evt);
            }
        });
        jPanel18.add(txtSeguro);

        jLabel11.setFont(new java.awt.Font("Lexend", 1, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(51, 51, 51));
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("Impuestos:");
        jPanel18.add(jLabel11);

        txtImpuestos.setBackground(new java.awt.Color(255, 255, 255));
        txtImpuestos.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(204, 204, 204)));
        txtImpuestos.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        txtImpuestos.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtImpuestos.setFont(new java.awt.Font("Lexend", 0, 14)); // NOI18N
        txtImpuestos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtImpuestosActionPerformed(evt);
            }
        });
        jPanel18.add(txtImpuestos);

        jLabel13.setFont(new java.awt.Font("Lexend", 1, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(51, 51, 51));
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setText("Nominas:");
        jPanel18.add(jLabel13);

        txtNominas.setEditable(false);
        txtNominas.setBackground(new java.awt.Color(255, 255, 255));
        txtNominas.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(204, 204, 204)));
        txtNominas.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        txtNominas.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtNominas.setFont(new java.awt.Font("Lexend", 0, 14)); // NOI18N
        txtNominas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNominasActionPerformed(evt);
            }
        });
        jPanel18.add(txtNominas);

        jLabel14.setFont(new java.awt.Font("Lexend", 1, 14)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(51, 51, 51));
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel14.setText("Total:");
        jPanel18.add(jLabel14);

        txtTotal.setEditable(false);
        txtTotal.setBackground(new java.awt.Color(255, 255, 255));
        txtTotal.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(204, 204, 204)));
        txtTotal.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        txtTotal.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtTotal.setFont(new java.awt.Font("Lexend", 0, 14)); // NOI18N
        jPanel18.add(txtTotal);

        jLabel18.setFont(new java.awt.Font("Lexend", 1, 14)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(51, 51, 51));
        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel18.setText("Total proyectos:");
        jPanel18.add(jLabel18);

        txtTotalPro.setEditable(false);
        txtTotalPro.setBackground(new java.awt.Color(255, 255, 255));
        txtTotalPro.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(204, 204, 204)));
        txtTotalPro.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        txtTotalPro.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtTotalPro.setFont(new java.awt.Font("Lexend", 0, 14)); // NOI18N
        jPanel18.add(txtTotalPro);

        jPanel17.add(jPanel18);

        jLabel5.setText(" ");
        jPanel17.add(jLabel5);

        jLabel7.setText(" ");
        jPanel17.add(jLabel7);

        jPanel16.add(jPanel17, java.awt.BorderLayout.PAGE_START);

        jPanel19.setBackground(new java.awt.Color(255, 255, 255));
        jPanel19.setLayout(new java.awt.BorderLayout());

        TablaIndirecto.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Proyecto", "Precio", "Porcentaje", "Total"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        TablaIndirecto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TablaIndirectoMouseClicked(evt);
            }
        });
        jScrollPane7.setViewportView(TablaIndirecto);

        jPanel19.add(jScrollPane7, java.awt.BorderLayout.CENTER);

        jPanel16.add(jPanel19, java.awt.BorderLayout.CENTER);

        jPanel15.add(jPanel16, java.awt.BorderLayout.CENTER);

        jScrollPane6.setViewportView(jPanel15);

        jPanel10.add(jScrollPane6, java.awt.BorderLayout.CENTER);

        tabbed.addTab("", new javax.swing.ImageIcon(getClass().getResource("/Iconos/costoIndirecto_16.png")), jPanel10, "Costo Indirecto"); // NOI18N

        jPanel7.add(tabbed, java.awt.BorderLayout.CENTER);

        lblFecha.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        lblFecha.setForeground(new java.awt.Color(51, 51, 51));
        lblFecha.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblFecha.setText(" ");
        jPanel7.add(lblFecha, java.awt.BorderLayout.PAGE_START);

        jPanel2.add(jPanel7, java.awt.BorderLayout.CENTER);

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

    private void tabbedStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_tabbedStateChanged
        int selectedIndex = tabbed.getSelectedIndex();
            for (int i = 0; i < tabbed.getTabCount(); i++) {
                if (i == selectedIndex) {
                    tabbed.setForegroundAt(i, Color.black);
                } else {
                    tabbed.setForegroundAt(i, new Color(200,200,200));
                }
            }
    }//GEN-LAST:event_tabbedStateChanged

    private void cmbMesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbMesActionPerformed
        if(this.isVisible()){
            band = true;
        }
        if(band){
            if(cmbMes.getSelectedItem() != null && cmbAnio.getSelectedItem() != null){
                JFrame j = (JFrame) JOptionPane.getFrameForComponent(this);
                Thread hilo = new Thread() {
                    public void run() {
                           if(cmbMes.getSelectedItem() != null && cmbAnio.getSelectedItem() != null){
                                espera.activar();
                                espera.setLocationRelativeTo(j);
                                espera.setExtendedState(Inicio1.MAXIMIZED_BOTH);
                                espera.setVisible(true);
                                tipoSeleccion = "mes";
                                obtenerFechas();
                                verHoras("mes",null,null);
                                ordenesDeCompra("mes");
                                almacen("mes");
                                verCostosIndirectos("mes");
                                agregarProyectos("mes");
                                agregarNominas("mes");
                                addFechas();
                                espera.setVisible(false);
                           }
                       }
                };
                hilo.start();
            }
        }
    }//GEN-LAST:event_cmbMesActionPerformed

    private void cmbAnioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbAnioActionPerformed
        if (this.isVisible()) {
            Date d = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
            int anio = Integer.parseInt(sdf.format(d));
            if (anio > Integer.parseInt(cmbAnio.getSelectedItem().toString())) {
                cmbMes.removeAllItems();
                String[] monthNames = DateFormatSymbols.getInstance(Locale.getDefault()).getMonths();
                for (String monthName : monthNames) {
                    cmbMes.addItem(monthName);
                }

                cmbMes.removeItemAt(12);
            }else {
                insertarSemanas();
            }
        }
    }//GEN-LAST:event_cmbAnioActionPerformed

    private void txtNominasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNominasActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNominasActionPerformed

    private void TablaIndirectoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TablaIndirectoMouseClicked
        
    }//GEN-LAST:event_TablaIndirectoMouseClicked

    private void TablaNominasMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TablaNominasMouseMoved
        int col = TablaNominas.columnAtPoint(evt.getPoint());
        if(col == 2){
            TablaNominas.setCursor(new Cursor(Cursor.HAND_CURSOR));
        }else{
            TablaNominas.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_TablaNominasMouseMoved

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        try{
            Connection con;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            //------------------------------------1         2        3       4        5       6       7       8       9       10            11              12           13
            String sql = "update costonominas set HS1 = ?, HS2 = ?, HS3 = ?,HS4 = ?, HS5 = ?, S1 = ?, S2 = ?, S3 = ?, S4 = ?, S5 = ? where Empleado = ? and Mes = ? and Anio = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            
            String hs1,hs2,hs3,hs4,hs5,s1,s2,s3,s4,s5,id;
            
            SimpleDateFormat sdf = new SimpleDateFormat("MMMM", Locale.ENGLISH);
            SimpleDateFormat sdf2 = new SimpleDateFormat("MM");
            Date d = sdf.parse(cmbMes.getSelectedItem().toString());
            String mes = sdf2.format(d);
            String anio = cmbAnio.getSelectedItem().toString();
            
            int n = 0;
            
            Stack<Integer> pila = new Stack<>();
            
            for (int i = 0; i < TablaNominas.getRowCount(); i++) {
                n = 0;
                try{hs1 = TablaNominas.getValueAt(i, 3).toString();}catch(Exception e){hs1 = "";}
                try{hs2 = TablaNominas.getValueAt(i, 4).toString();}catch(Exception e){hs2 = "";}
                try{hs3 = TablaNominas.getValueAt(i, 5).toString();}catch(Exception e){hs3 = "";}
                try{hs4 = TablaNominas.getValueAt(i, 6).toString();}catch(Exception e){hs4 = "";}
                try{hs5 = TablaNominas.getValueAt(i, 7).toString();}catch(Exception e){hs5 = "";}
                
                try{s1 = TablaNominas.getValueAt(i, 8).toString();}catch(Exception e){s1 = "";}
                try{s2 = TablaNominas.getValueAt(i, 9).toString();}catch(Exception e){s2 = "";}
                try{s3 = TablaNominas.getValueAt(i, 10).toString();}catch(Exception e){s3 = "";}
                try{s4 = TablaNominas.getValueAt(i, 11).toString();}catch(Exception e){s4 = "";}
                try{s5 = TablaNominas.getValueAt(i, 12).toString();}catch(Exception e){s5 = "";}
                
                try{id = TablaNominas.getValueAt(i, 0).toString();}catch(Exception e){id = "";}
                
                pst.setString(1, hs1);
                pst.setString(2, hs2);
                pst.setString(3, hs3);
                pst.setString(4, hs4);
                pst.setString(5, hs5);
                pst.setString(6, s1);
                pst.setString(7, s2);
                pst.setString(8, s3);
                pst.setString(9, s4);
                pst.setString(10, s5);
                pst.setString(11, id);
                pst.setString(12, mes);
                pst.setString(13, anio);
                
                n = pst.executeUpdate();
                
                if(n == 0){
                    pila.add(i);
                }
            }
            
            int n1 = 0;
            
            if(!pila.isEmpty()){
                String sql2 = "insert into costonominas (Empleado, Mes, Anio, HS1, HS2, HS3, HS4, HS5, S1, S2, S3, S4, S5) values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
                PreparedStatement pst2 = con.prepareStatement(sql2);
                
                for (int i = 0; i < pila.size(); i++) {
                    try{hs1 = TablaNominas.getValueAt(pila.get(i), 3).toString();}catch(Exception e){hs1 = "";}
                    try{hs2 = TablaNominas.getValueAt(pila.get(i), 4).toString();}catch(Exception e){hs2 = "";}
                    try{hs3 = TablaNominas.getValueAt(pila.get(i), 5).toString();}catch(Exception e){hs3 = "";}
                    try{hs4 = TablaNominas.getValueAt(pila.get(i), 6).toString();}catch(Exception e){hs4 = "";}
                    try{hs5 = TablaNominas.getValueAt(pila.get(i), 7).toString();}catch(Exception e){hs5 = "";}

                    try{s1 = TablaNominas.getValueAt(pila.get(i), 8).toString();}catch(Exception e){s1 = "";}
                    try{s2 = TablaNominas.getValueAt(pila.get(i), 9).toString();}catch(Exception e){s2 = "";}
                    try{s3 = TablaNominas.getValueAt(pila.get(i), 10).toString();}catch(Exception e){s3 = "";}
                    try{s4 = TablaNominas.getValueAt(pila.get(i), 11).toString();}catch(Exception e){s4 = "";}
                    try{s5 = TablaNominas.getValueAt(pila.get(i), 12).toString();}catch(Exception e){s5 = "";}

                    try{id = TablaNominas.getValueAt(pila.get(i), 0).toString();}catch(Exception e){id = "";}
                    
                    pst2.setString(1, id);
                    pst2.setString(2, mes);
                    pst2.setString(3, anio);
                    pst2.setString(4, hs1);
                    pst2.setString(5, hs2);
                    pst2.setString(6, hs3);
                    pst2.setString(7, hs4);
                    pst2.setString(8, hs5);
                    pst2.setString(9, s1);
                    pst2.setString(10, s2);
                    pst2.setString(11, s3);
                    pst2.setString(12, s4);
                    pst2.setString(13, s5);
                    
                
                    n1 = pst2.executeUpdate();
                }
            }
            
            if(n > 0){
                String mess = "";
                if(n1 == 0){
                    mess = ", No se agregaron nuevas partidas";
                }else{
                    mess = ", Se agregaron nuevas partidas";
                }
                agregarNominas("mes");
                JOptionPane.showMessageDialog(this, "Datos Guardados" + mess);
            }
            
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "ERROR: "+e,"ERROR",JOptionPane.ERROR_MESSAGE);
        } catch (ParseException ex) {
            Logger.getLogger(Costos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void rbMesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbMesActionPerformed
        panelMeses.setVisible(true);
        panelProyecto.setVisible(false);
    }//GEN-LAST:event_rbMesActionPerformed

    private void rbProyectoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbProyectoActionPerformed
        panelMeses.setVisible(false);
        panelProyecto.setVisible(true);
    }//GEN-LAST:event_rbProyectoActionPerformed

    private void txtProyectoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtProyectoActionPerformed
        if(txtProyecto.getText().equals("")){
            JOptionPane.showMessageDialog(
                this,
                "Debes introducir un numero de proyecto",
                "Advertencia",
                JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        tipoSeleccion = "proyecto";

        verHoras("proyecto", null, null);
        ordenesDeCompraProyecto();
        almacen("proyecto");
        verCostosIndirectos("proyecto");
        agregarNominas("proyecto");
        agregarProyectos("proyecto");
    }//GEN-LAST:event_txtProyectoActionPerformed

    private void txtRentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtRentaActionPerformed
        agregarCostos();
    }//GEN-LAST:event_txtRentaActionPerformed

    private void txtLuzActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtLuzActionPerformed
        agregarCostos();
    }//GEN-LAST:event_txtLuzActionPerformed

    private void txtSeguroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSeguroActionPerformed
        agregarCostos();
    }//GEN-LAST:event_txtSeguroActionPerformed

    private void txtImpuestosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtImpuestosActionPerformed
        agregarCostos();
    }//GEN-LAST:event_txtImpuestosActionPerformed

    private void TablaNominasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TablaNominasMouseClicked
        if(TablaNominas.getSelectedColumn() > 2 && TablaNominas.getSelectedColumn() < 8){
            if(evt.getClickCount() == 2){
                JFrame f = (JFrame) JOptionPane.getFrameForComponent(this);
                TablaNominas costo = new TablaNominas(f,true);
                String col = this.TablaNominas.getColumnName(this.TablaNominas.getSelectedColumn());
                int em = Integer.parseInt(col.substring(2, col.length()));
                JTable table = costo.getTabla(em);
                
                Stack<Integer> pila = new Stack<>();
                Stack<String> pilaHs = new Stack<>();
                Stack<String> pilaS = new Stack<>();
                
                if(table != null){
                    for (int i = 0; i < TablaNominas.getRowCount(); i++) {
                        int numEmpleado;
                        try{numEmpleado = Integer.parseInt(TablaNominas.getValueAt(i, 0).toString());}catch(Exception e){numEmpleado = -1;}
                        boolean ban = false;
                        int fila = -1;
                        int empleado = -2;
                        if(numEmpleado != -1){
                            for (int j = 0; j < table.getRowCount(); j++) {
                                try{empleado = Integer.parseInt(table.getValueAt(j, 0).toString());}catch(Exception e){empleado = -2;}
                                if(numEmpleado == (empleado)){
                                    ban = true;
                                    fila = j;
                                }
                            }
                        }
                        if(ban){
                            TablaNominas.setValueAt(table.getValueAt(fila, 3).toString(), i, TablaNominas.getSelectedColumn());
                            TablaNominas.setValueAt(table.getValueAt(fila, 4).toString(), i, TablaNominas.getSelectedColumn()+5);
                        }
                    }
                    boolean bandNom;
                    for (int i = 0; i < table.getRowCount(); i++) {
                        bandNom = false;
                        int nomina;
                        String datos ="";
                        try{nomina = Integer.parseInt(table.getValueAt(i, 0).toString());}catch(Exception e){nomina = -2;}
                        for (int j = 0; j < TablaNominas.getRowCount(); j++) {
                            int tab ;
                            try{tab = Integer.parseInt(TablaNominas.getValueAt(j, 0).toString());}catch(Exception e){tab = -1;}
                            if(nomina == tab){
                                bandNom = true;
                                break;
                            }
                        }
                        if(nomina > 0){
                            if(!bandNom){
                                String hs;
                                try{hs = (table.getValueAt(i, 3).toString());}catch(Exception e){hs = "";}
                                String s;
                                try{s = (table.getValueAt(i, 4).toString());}catch(Exception e){s = "";}
                                pila.push(nomina);
                                pilaHs.push(hs);
                                pilaS.push(s);
                            }
                        }
                    }
                }
                if(!pila.isEmpty()){
                    int opc = JOptionPane.showConfirmDialog(this, "Hay nuevo(s) emple@do(s), ¿Deseas agregarlos a la nomina actual?");
                    DefaultTableModel miModelo = (DefaultTableModel) TablaNominas.getModel();
                    int tot = TablaNominas.getRowCount();
                    if(opc == 0){
                        for (int i = 0; i < pila.size(); i++) {
                            Object datos[] = {String.valueOf(pila.get(i)),"","","","","","","","","","","","","","",""};
                            miModelo.addRow(datos);
                            TablaNominas.setValueAt(pilaHs.get(i), tot + i, TablaNominas.getSelectedColumn());
                            TablaNominas.setValueAt(pilaS.get(i), tot + i, TablaNominas.getSelectedColumn()+5);
                        }
                    }
                }
            }
        }else if(TablaNominas.getSelectedColumn() == 1){
            if(evt.getClickCount() == 2){
                String s1;
                try{s1 = (TablaNominas.getValueAt(TablaNominas.getSelectedRow(), 1).toString());}catch(Exception e){s1 = "";}
                    if(s1.equals("")){
                    JFrame f = (JFrame) JOptionPane.getFrameForComponent(this);
                    String s;
                    try{s = (TablaNominas.getValueAt(TablaNominas.getSelectedRow(), 0).toString());}catch(Exception e){s = "";}
                    if(!s.equals("")){
                        addEmpleado add = new addEmpleado(f,true, s);
                        add.setVisible(true);
                    }else{
                        JOptionPane.showMessageDialog(this, "No puedo guardar empleados con numero vacio", "Advertencia",JOptionPane.WARNING_MESSAGE);
                    }
                }
            }
        }
    }//GEN-LAST:event_TablaNominasMouseClicked

    private void pegarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pegarActionPerformed
        pasteClipboard(TablaNominas);
    }//GEN-LAST:event_pegarActionPerformed

    private void btnCostoHorasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCostoHorasActionPerformed
        JFrame f = (JFrame) JOptionPane.getFrameForComponent(this);
        CostoHoras costo = new CostoHoras(f,true);
        costo.setVisible(true);
    }//GEN-LAST:event_btnCostoHorasActionPerformed

    private void btnTablaNominasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTablaNominasActionPerformed
//        JFrame f = (JFrame) JOptionPane.getFrameForComponent(this);
//        TablaNominas costo = new TablaNominas(f,true);
////        costo.TablaNominas.getColumnModel().getColumn(3).setHeaderValue(TablaNominas.getColumnModel().getColumn(TablaNominas.getSelectedColumn()).toString());
////        costo.TablaNominas.getColumnModel().getColumn(4).setHeaderValue(TablaNominas.getColumnModel().getColumn(TablaNominas.getSelectedColumn()).toString());
//        costo.setVisible(true);
        try{
            Connection con;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            JFrame f = (JFrame) JOptionPane.getFrameForComponent(this);
            TablaNominas tabla = new TablaNominas(f,true);
            JTable table = tabla.getTabla(empieza);
            if(table.getRowCount() <= 0){
                JOptionPane.showMessageDialog(this, "Debes introducir registros para poder avanzar","ERROR",JOptionPane.ERROR_MESSAGE);
            }else{
                String sql3 = "insert into costonominas (Empleado,Mes,Anio,HS1,HS2,HS3,HS4,HS5,S1,S2,S3,S4,S5) values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
                PreparedStatement pst = con.prepareStatement(sql3);

                int n = 0;

                SimpleDateFormat sdf = new SimpleDateFormat("MMMM", Locale.ENGLISH);
                SimpleDateFormat sdf2 = new SimpleDateFormat("MM");
                Date d = sdf.parse((String) cmbMes.getSelectedItem());
                String mes = sdf2.format(d);
                String anio = cmbAnio.getSelectedItem().toString();
                for (int i = 0; i < table.getRowCount(); i++) {
                    int Id;
                    try{Id = Integer.parseInt(table.getValueAt(i, 0).toString());}catch(Exception e){Id = -1;}
                    if(Id != -1){
                        pst.setString(1, String.valueOf(Id));
                        pst.setString(2, mes);
                        pst.setString(3, anio);
                        pst.setString(4, table.getValueAt(i, 3).toString());
                        pst.setString(5, "");
                        pst.setString(6, "");
                        pst.setString(7, "");
                        pst.setString(8, "");
                        pst.setString(9, table.getValueAt(i, 4).toString());
                        pst.setString(10, "");
                        pst.setString(11, "");
                        pst.setString(12, "");
                        pst.setString(13, "");

                        n += pst.executeUpdate();
                    }
                }
                if(n == 0){
                    JOptionPane.showMessageDialog(this, "Error al subir empleados a la base de datos","ERROR",JOptionPane.ERROR);
                }
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "ERROR: "+e,"ERROR",JOptionPane.ERROR_MESSAGE);
        } catch (ParseException ex) {
            Logger.getLogger(Costos.class.getName()).log(Level.SEVERE, null, ex);
        }
        agregarNominas("mes");
    }//GEN-LAST:event_btnTablaNominasActionPerformed

    private void btnEditarEmpleadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarEmpleadoActionPerformed
        JFrame f = (JFrame) JOptionPane.getFrameForComponent(this);
        EditarEmpleados costo = new EditarEmpleados(f,true);
        costo.setVisible(true);
    }//GEN-LAST:event_btnEditarEmpleadoActionPerformed

    private void btnGuardar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardar1ActionPerformed
        try{
            Connection con;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            String sql = "update htpp set Costo = ? where Id = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            
            int n = 0;
            for (int i = 0; i < TablaMaquinados.getRowCount(); i++) {
                double costo;
                try{costo = Double.parseDouble(TablaMaquinados.getValueAt(i, 6).toString());}catch(Exception e){costo = 0;}
                pst.setDouble(1, costo);
                pst.setString(2, TablaMaquinados.getValueAt(i, 7).toString());
                
                n = pst.executeUpdate();
            }
            
            if(n > 0){
                JOptionPane.showMessageDialog(this, "Datos guardados correctamente");
            }
            
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "ERROR: " + e, "ERROR",JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnGuardar1ActionPerformed

    private void cmbSemanasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbSemanasActionPerformed
        if(!espera.isVisible()){
            if(cmbSemanas.getSelectedIndex() > 0){
                try{
                    String fi = fechaInicio;
                    String ff;
                    int operacion = 7 * (cmbSemanas.getSelectedIndex() - 1);

                    LocalDate date = LocalDate.parse(fi, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                    LocalDate newDate = date.plusDays(operacion);
                    String newDateString = newDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                    
                    date = LocalDate.parse(newDateString, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                    newDate = date.plusDays(6);
                    ff = newDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                    
                    fechasSeleccionadas = new Stack<>();
                    cmbDias.removeAllItems();
                    cmbDias.addItem("Seleccionar");
                    verHoras("mes",newDateString, ff);
                    
                    lblFec.setText("Inicio: "+ newDateString + " | Termino: " + ff);
                    DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    LocalDate fecInicio = LocalDate.parse(newDateString,format);
                    LocalDate fecFinal = LocalDate.parse(ff,format);
                    SimpleDateFormat s1 = new SimpleDateFormat("MMMM", Locale.ENGLISH);
                    SimpleDateFormat s2 = new SimpleDateFormat("MM");
                    Date d = s1.parse(cmbMes.getSelectedItem().toString());
                    String mes = s2.format(d);
                    try{
                        for (int i = 1; i < TablaHoras.getColumnCount(); i++) {
                            String dia = String.valueOf(i);
                            if(i < 10){
                                dia = "0" + i;
                            }
                            String fec = cmbAnio.getSelectedItem().toString() + "-" + mes + "-" + dia;
                            LocalDate fechaTabla = LocalDate.parse(fec,format);
                            if((fechaTabla.isAfter(fecInicio) || fechaTabla.isEqual(fecInicio)) && (fechaTabla.isBefore(fecFinal) || fechaTabla.isEqual(fecFinal))){
                                TablaHoras.getColumnModel().getColumn(i).setHeaderValue(dia);
                                fechasSeleccionadas.push(fechaTabla.toString());
                                cmbDias.addItem(fechaTabla.toString());
                                repaint();
                            }else{
                                TablaHoras.getColumnModel().getColumn(i).setHeaderValue("");
                                repaint();
                            }
                        }
                    }catch(Exception e){
                        JOptionPane.showMessageDialog(this, "Error: "+e);
                    }
                    
                }catch(Exception e){
                    JOptionPane.showMessageDialog(this, "Error al seleccionar Semana " + e, "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }//GEN-LAST:event_cmbSemanasActionPerformed

    private void cmbDiasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbDiasActionPerformed
        if(!espera.isVisible()){
            if(cmbDias.getSelectedIndex() > 0){
                verHoras("mes",cmbDias.getSelectedItem().toString(), cmbDias.getSelectedItem().toString());
            }
        }
    }//GEN-LAST:event_cmbDiasActionPerformed

    private void TablaPrincipalMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TablaPrincipalMouseClicked
        
    }//GEN-LAST:event_TablaPrincipalMouseClicked

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        if (Em == null || !Em.isVisible()) {
           Em = new EditarMateriales();
           Em.setLocationRelativeTo(null);
           Em.setVisible(true);
           Em.setTitle("Editar Materiales");
        } else {
            Em.toFront();
        }
    }//GEN-LAST:event_btnEditActionPerformed

    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable TablaAlmacen;
    private javax.swing.JTable TablaCalidad;
    private javax.swing.JTable TablaDiseño;
    private javax.swing.JTable TablaHoras;
    private javax.swing.JTable TablaIndirecto;
    private javax.swing.JTable TablaIntegracion;
    private javax.swing.JTable TablaMaquinados;
    private javax.swing.JTable TablaNominas;
    private javax.swing.JTable TablaOrdenes;
    private javax.swing.JTable TablaPrincipal;
    private javax.swing.JButton btnCostoHoras;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnEditarEmpleado;
    private javax.swing.JButton btnGuardar;
    public scrollPane.BotonRedondo btnGuardar1;
    private javax.swing.JButton btnTablaNominas;
    private javax.swing.ButtonGroup buttonGroup1;
    private RSMaterialComponent.RSComboBoxMaterial cmbAnio;
    private javax.swing.JComboBox<String> cmbDias;
    private RSMaterialComponent.RSComboBoxMaterial cmbMes;
    private javax.swing.JComboBox<String> cmbSemanas;
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
    private javax.swing.JPanel jPanel27;
    private javax.swing.JPanel jPanel28;
    private javax.swing.JPanel jPanel29;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel30;
    private javax.swing.JPanel jPanel31;
    private javax.swing.JPanel jPanel32;
    private javax.swing.JPanel jPanel33;
    private javax.swing.JPanel jPanel34;
    private javax.swing.JPanel jPanel35;
    private javax.swing.JPanel jPanel36;
    private javax.swing.JPanel jPanel37;
    private javax.swing.JPanel jPanel38;
    private javax.swing.JPanel jPanel39;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel40;
    private javax.swing.JPanel jPanel41;
    private javax.swing.JPanel jPanel42;
    private javax.swing.JPanel jPanel43;
    private javax.swing.JPanel jPanel44;
    private javax.swing.JPanel jPanel45;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane12;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JLabel jlabel;
    private javax.swing.JLabel lblCnc;
    private javax.swing.JLabel lblCnc1;
    private javax.swing.JLabel lblCnc2;
    private javax.swing.JLabel lblFec;
    private javax.swing.JLabel lblFecha;
    private javax.swing.JLabel lblFresa;
    private javax.swing.JLabel lblFresa1;
    private javax.swing.JLabel lblHorasDiseno;
    private javax.swing.JLabel lblIntegracion;
    private javax.swing.JLabel lblPrecioDolar;
    private javax.swing.JLabel lblPrecioDolar1;
    private javax.swing.JLabel lblSalir;
    private javax.swing.JLabel lblTorno;
    private javax.swing.JLabel lblTorno1;
    private javax.swing.JLabel lblTotalAlmacen;
    private javax.swing.JLabel lblTotalAlmacen1;
    private javax.swing.JLabel lblTotalCompras;
    private javax.swing.JLabel lblTotalCompras1;
    private javax.swing.JPanel pan;
    private javax.swing.JPanel panelMeses;
    private javax.swing.JPanel panelProyecto;
    private javax.swing.JPanel panelSalir;
    private javax.swing.JMenuItem pegar;
    private javax.swing.JRadioButton rbMes;
    private javax.swing.JRadioButton rbProyecto;
    private javax.swing.JTabbedPane tabbed;
    private javax.swing.JTabbedPane tabbed2;
    private javax.swing.JFormattedTextField txtImpuestos;
    private javax.swing.JFormattedTextField txtLuz;
    private javax.swing.JFormattedTextField txtNominas;
    private RSMaterialComponent.RSTextFieldMaterial txtProyecto;
    private javax.swing.JFormattedTextField txtRenta;
    private javax.swing.JFormattedTextField txtSeguro;
    private javax.swing.JFormattedTextField txtTotal;
    private javax.swing.JFormattedTextField txtTotalPro;
    // End of variables declaration//GEN-END:variables
}
