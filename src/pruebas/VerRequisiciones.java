package pruebas;

import Conexiones.Conexion;
import com.mxrck.autocompleter.TextAutoCompleter;
import java.awt.Color;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import Modelo.TablaVerRequisiciones;
import VentanaEmergente.Compras.Reclamos;
import VentanaEmergente.Requisiciones.Material;
import VentanaEmergente.Requisiciones.liberarCompra;
import VentanaEmergente.verRequisiciones.Detalles;
import com.formdev.flatlaf.themes.FlatMacLightLaf;
import com.toedter.calendar.JDateChooser;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import static org.apache.poi.ss.usermodel.FillPatternType.SOLID_FOREGROUND;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellUtil;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import scrollPane.ScrollBarCustom;

public class VerRequisiciones extends javax.swing.JInternalFrame implements ActionListener {

    TextAutoCompleter au;
    String numEmpleado;
    boolean compras;
    liberarCompra lib;
    int tabla = 0;
    boolean falta = true;

    public void limpiarTablaOrden() {
        Tabla1.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{},
                new String[]{
                    "NO. ORDEN", "NO. REQUISICION"
                }
        ) {
            boolean[] canEdit = new boolean[]{
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });

        Tabla1.setRowHeight(25);
        Tabla1.setShowVerticalLines(false);
        Tabla1.setGridColor(new java.awt.Color(245, 245, 245));

        Tabla1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Tabla1MouseClicked(evt);
            }
        });

        jScrollPane2.setViewportView(Tabla1);

        if (Tabla1.getColumnModel().getColumnCount() > 0) {
            Tabla1.getColumnModel().getColumn(0).setResizable(false);
            Tabla1.getColumnModel().getColumn(1).setResizable(false);
        }
    }

    public void verDatosOrden() {
        try {
            Connection con = null;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            DefaultTableModel miModelo = (DefaultTableModel) Tabla1.getModel();
            Statement st = con.createStatement();
            String sql = "select * from ordencompra order by Id DESC";
            ResultSet rs = st.executeQuery(sql);
            String datos[] = new String[10];
            while (rs.next()) {
                datos[0] = rs.getString("OrdenNo");
                datos[1] = rs.getString("RequisicionNo");
                miModelo.addRow(datos);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "ERROR: " + e, "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void mostrarDialogoFechas() {
        JDateChooser dcInicio = new JDateChooser();
        JDateChooser dcFin = new JDateChooser();

        dcInicio.setDateFormatString("dd/MM/yyyy");
        dcFin.setDateFormatString("dd/MM/yyyy");

        Object[] mensaje = {
            "Fecha inicial:", dcInicio,
            "Fecha final (opcional):", dcFin
        };

        int opcion = JOptionPane.showConfirmDialog(
                this,
                mensaje,
                "Seleccionar rango de fechas",
                JOptionPane.OK_CANCEL_OPTION
        );

        if (opcion == JOptionPane.OK_OPTION) {
            Date fechaInicio = dcInicio.getDate();
            Date fechaFin = dcFin.getDate();

            if (fechaInicio == null) {
                JOptionPane.showMessageDialog(this, "Debes seleccionar al menos la fecha inicial");
                return;
            }

            // Si no hay fecha final mismo día
            if (fechaFin == null) {
                fechaFin = fechaInicio;
            }

            LocalDate inicio = fechaInicio.toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();

            LocalDate fin = fechaFin.toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();

            if (inicio.isAfter(fin)) {
                JOptionPane.showMessageDialog(this, "La fecha inicial no puede ser mayor a la final");
                return;
            }

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            String valor = inicio.format(formatter) + "|" + fin.format(formatter);

            filtrosActivos.computeIfAbsent("FECHAS", k -> new ArrayList<>()).add(valor);

            actualizarPanelFiltros();
            seleccionarTabla();
            seleccionarTabla2();
        }
    }
    
    private Map<String, List<String>> filtrosActivos = new java.util.LinkedHashMap<>();

    public void seleccionarTabla() {
        // Si no hay filtros activos, limpiamos y salimos (Lógica por defecto)
        if (filtrosActivos.isEmpty() && !rbtnTodas.isSelected() && !rbtnMis.isSelected()) {
            limpiarTabla1();
            return; 
        }
        
        try {
            limpiarTabla1();
            DefaultTableModel miModelo = (DefaultTableModel) Tabla1.getModel();
            Connection con = new Conexion().getConnection();

            StringBuilder sql = new StringBuilder("SELECT DISTINCT R.Id, R.NumeroEmpleado, R.NumeroCotizacion, R.Estatus, R.Estado, R.Progreso, R.Costo, R.Fecha "
                + "FROM Requisicion R "
                + "INNER JOIN requisiciones d ON R.Id = d.NumRequisicion WHERE 1=1 ");
            
            // Construcción dinámica de filtros
            for (Map.Entry<String, List<String>> entry : filtrosActivos.entrySet()) {
                String criterio = entry.getKey();
                List<String> valores = entry.getValue();

                if (criterio.equals("FECHAS")) {

                    for (String rango : valores) {
                        String[] partes = rango.split("\\|");
                        String inicio = partes[0];
                        String fin = partes[1];

                        sql.append(" AND (STR_TO_DATE(R.Fecha, '%d/%m/%Y') BETWEEN ")
                           .append("STR_TO_DATE('").append(inicio).append("', '%d/%m/%Y') AND ")
                           .append("STR_TO_DATE('").append(fin).append("', '%d/%m/%Y'))");
                    }

                } else {

                    String columna = mapearColumna(criterio);

                    if (!valores.isEmpty()) {
                        sql.append(" AND (");
                        for (int i = 0; i < valores.size(); i++) {
                            sql.append(columna).append(" LIKE '%").append(valores.get(i)).append("%'");
                            if (i < valores.size() - 1) {
                                sql.append(" OR ");
                            }
                        }
                        sql.append(")");
                    }
                }
            }

            // Filtro de Mis Requisiciones
            if (rbtnMis.isSelected()) {
                sql.append(" AND R.NumeroEmpleado = '").append(numEmpleado).append("'");
            }

            sql.append(" ORDER BY R.Id DESC");

            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql.toString());
            String datos[] = new String[8]; // Ajustado a la cantidad de columnas seleccionadas

            while (rs.next()) {
                datos[0] = rs.getString("Id");
                datos[1] = rs.getString("NumeroEmpleado");
                datos[2] = rs.getString("NumeroCotizacion");
                datos[3] = rs.getString("Estatus");
                datos[4] = rs.getString("Estado");
                datos[5] = rs.getString("Progreso");
                datos[6] = rs.getString("Costo");
                datos[7] = rs.getString("Fecha");
                miModelo.addRow(datos);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al filtrar: " + e.getMessage());
        }
    }

    // Auxiliar para los nombres de las columnas
    private String mapearColumna(String comboText) {
        switch (comboText) {
            case "PROYECTO": return "d.Proyecto";
            case "NUMERO DE REQUISICION": return "r.Id";
            case "DESCRIPCION": return "d.Descripcion";
            case "NUMERO DE PARTE": return "d.Codigo";
            case "PROVEEDOR": return "d.Proveedor"; 
            case "ORDEN DE COMPRA": return "d.OC";
            case "REQUISITOR": return "d.Requisitor"; 
            default: return "d.Proyecto";
        }
    }

    private void agregarFiltro() {
        String criterio = cmbBuscar.getSelectedItem().toString();
        String valor = txtProyecto.getText().trim().toUpperCase();

        if (!valor.isEmpty()) {
            // Si el criterio no existe en el mapa, creamos la lista
            filtrosActivos.computeIfAbsent(criterio, k -> new ArrayList<>()).add(valor);

            txtProyecto.setText(""); // Limpiamos para el siguiente
            actualizarPanelFiltros(); // Refrescamos las etiquetas visuales
            seleccionarTabla();      // Refrescamos la tabla
            seleccionarTabla2();
        }
    }
    
    private void actualizarPanelFiltros() {
        panelFiltros.removeAll();
        //panelFiltros.add(tglFiltrar);
        //panelFiltros.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 5)); // Añade espacio entre tags
        
        if (!filtrosActivos.isEmpty()) {
            JButton btnLimpiarTodo = new JButton("Limpiar filtros");

            // Estilo: Color rojito y diseño limpio (FlatLaf)
            btnLimpiarTodo.setBackground(new java.awt.Color(255, 200, 200)); // Rojo suave
            btnLimpiarTodo.setForeground(new java.awt.Color(150, 0, 0));    // Texto rojo oscuro
            btnLimpiarTodo.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
            btnLimpiarTodo.putClientProperty("JButton.buttonType", "roundRect"); // Bordes redondeados
            btnLimpiarTodo.setFocusPainted(false);

            // Acción: Llamar al método de limpieza
            btnLimpiarTodo.addActionListener(e -> limpiarTodosLosFiltros());

            panelFiltros.add(btnLimpiarTodo);
        }
        
        for (Map.Entry<String, List<String>> entry : filtrosActivos.entrySet()) {
            String criterio = entry.getKey();
            for (String valor : entry.getValue()) {
                // Un estilo más limpio
                String textoMostrar = valor;
                if (criterio.equals("FECHAS")) {
                    String[] partes = valor.split("\\|");

                    if (partes[0].equals(partes[1])) {
                        textoMostrar = partes[0]; // solo una fecha
                    } else {
                        textoMostrar = partes[0] + " - " + partes[1];
                    }
                }

                JButton btnFiltro = new JButton(criterio + ": " + textoMostrar + "  x");
                btnFiltro.setBorderPainted(false);
                btnFiltro.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
                btnFiltro.setBackground(new java.awt.Color(200, 220, 240)); // Color suave

                btnFiltro.addActionListener(e -> {
                    filtrosActivos.get(criterio).remove(valor);
                    if (filtrosActivos.get(criterio).isEmpty()) {
                        filtrosActivos.remove(criterio);
                    }
                    actualizarPanelFiltros();
                    seleccionarTabla();
                    seleccionarTabla2();
                });

                panelFiltros.add(btnFiltro);
            }
        }
        panelFiltros.revalidate();
        panelFiltros.repaint();
    }
    
    private void limpiarTodosLosFiltros() {
        filtrosActivos.clear(); // Borra el mapa de filtros
        actualizarPanelFiltros(); // Refresca visualmente (esto ocultará el botón rojo)
        seleccionarTabla(); // Limpia Tabla 1
        seleccionarTabla2(); // Limpia Tabla 2
    }
    
    public void seleccionarTabla2() {
        // Si el toggle está activo, pero ya no tiene filtros, se limpia la tabla  
        if (filtrosActivos.isEmpty() && !rbtnTodas.isSelected() && !rbtnMis.isSelected()) {
            limpiarTabla2();
            return;
        }
        
        try {
            limpiarTabla2();
            if (Tabla1.getRowCount() == 0) {
                limpiarTabla2();
                return;
            }
            
            DefaultTableModel miModelo = (DefaultTableModel) Tabla2.getModel();
            Connection con = new Conexion().getConnection();

            // SQL ajustado a los nombres reales de tu tabla de detalles
            StringBuilder sql = new StringBuilder("SELECT NumRequisicion, Codigo, Descripcion, Proyecto, Cantidad, Requisitor, Llego, Precio, TE, OC, Notas, Proveedor, FechaRecibo, CantRecibida FROM Requisiciones WHERE 1=1");

            for (Map.Entry<String, List<String>> entry : filtrosActivos.entrySet()) {
                String criterio = entry.getKey();

                if (criterio.equals("FECHAS")) {
                    continue;
                }

                List<String> valores = entry.getValue();
                String columna = mapearColumnaTabla2(criterio);

                if (!valores.isEmpty()) {
                    sql.append(" AND (");
                    for (int i = 0; i < valores.size(); i++) {
                        sql.append(columna).append(" LIKE '%").append(valores.get(i)).append("%'");
                        if (i < valores.size() - 1) {
                            sql.append(" OR ");
                        }
                    }
                    sql.append(")");
                }
            }
            
            if (Tabla1.getRowCount() > 0) {
                sql.append(" AND NumRequisicion IN (");

                for (int i = 0; i < Tabla1.getRowCount(); i++) {
                    sql.append("'").append(Tabla1.getValueAt(i, 0).toString()).append("'");

                    if (i < Tabla1.getRowCount() - 1) {
                        sql.append(",");
                    }
                }

                sql.append(")");
            }

            sql.append(" ORDER BY NumRequisicion DESC");

            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql.toString());

            // El array debe ser de 14 espacios para coincidir con el SELECT
            String datos[] = new String[14];

            while (rs.next()) {
                datos[0] = rs.getString("NumRequisicion");
                datos[1] = rs.getString("Codigo");
                datos[2] = rs.getString("Descripcion");
                datos[3] = rs.getString("Proyecto");
                datos[4] = rs.getString("Cantidad");
                datos[5] = rs.getString("Requisitor");
                datos[6] = rs.getString("Llego");
                datos[7] = rs.getString("Precio");
                datos[8] = rs.getString("TE");
                datos[9] = rs.getString("OC");
                datos[10] = rs.getString("Notas");
                datos[11] = rs.getString("Proveedor");
                datos[12] = rs.getString("FechaRecibo");
                double cant;
                try{
                    cant = Double.parseDouble(rs.getString("Cantidad"));
                } catch (Exception e){
                    cant = 0;
                }
                double cantR;
                try{
                    cantR = Double.parseDouble(rs.getString("CantRecibida"));
                } catch (Exception e){
                    cantR = 0;
                }
                datos[13] = String.valueOf(cant - cantR);
                miModelo.addRow(datos);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error en Tabla 2: " + e.getMessage());
        }
    }

    // Auxiliar específico para las columnas de la Tabla 2 (evita errores de prefijos R. o r.)
    private String mapearColumnaTabla2(String comboText) {
        switch (comboText) {
            case "PROYECTO": return "Proyecto";
            case "NUMERO DE REQUISICION": return "NumRequisicion";
            case "DESCRIPCION": return "Descripcion";
            case "NUMERO DE PARTE": return "Codigo";
            case "PROVEEDOR": return "Proveedor";
            case "ORDEN DE COMPRA": return "OC";
            case "REQUISITOR": return "Requisitor";
            default: return "Proyecto";
        }
    }
    
    private void eliminarUltimoFiltro() {
        if (filtrosActivos.isEmpty()) {
            return;
        }

        // Convertimos las llaves a un arreglo para obtener la última fácilmente
        String[] llaves = filtrosActivos.keySet().toArray(new String[0]);
        String ultimaLlave = llaves[llaves.length - 1];
        List<String> valores = filtrosActivos.get(ultimaLlave);

        if (valores != null && !valores.isEmpty()) {
            // Removemos el último elemento de la lista de esa categoría
            valores.remove(valores.size() - 1);

            // Si la lista de esa categoría quedó vacía, eliminamos la categoría del mapa
            if (valores.isEmpty()) {
                filtrosActivos.remove(ultimaLlave);
            }
        }

        // Refrescamos la interfaz y los datos
        actualizarPanelFiltros();
        seleccionarTabla();
        seleccionarTabla2();
    }
    
    public void seleccionarRequisicion(String requi) {
        try {
            limpiarTabla2();
            DefaultTableModel miModelo = (DefaultTableModel) Tabla2.getModel();

            Connection con = null;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            Statement st2 = con.createStatement();
            String sql = "select * from Requisiciones where NumRequisicion like '" + requi + "'";
            ResultSet rs = st.executeQuery(sql);

            String sql2 = "select * from ordencompra where RequisicionNo like '" + requi + "'";
            ResultSet rs2 = st2.executeQuery(sql2);

            String datos[] = new String[10];
            String datos1[] = new String[10];

            while (rs.next()) {
                datos[0] = rs.getString("NumRequisicion");
                datos[1] = rs.getString("Codigo");
                datos[2] = rs.getString("Descripcion");
                datos[3] = rs.getString("Proyecto");
                datos[4] = rs.getString("Cantidad");
                datos[5] = rs.getString("NumParte");
                datos[6] = rs.getString("Llego");
                datos[7] = rs.getString("Precio");
                datos[8] = rs.getString("TE");
                datos[9] = rs.getString("OC");

                miModelo.addRow(datos);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR AL MOSTRAR ITEMS: " + e, "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void autoCompletar(String bd, String campo) {
        try {
            au.removeAllItems();
        } catch (Exception e) {

        }
        au = new TextAutoCompleter(txtProyecto);

        try {
            Connection con = null;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            String sql = "select " + campo + " from " + bd + "";
            String datos[] = new String[10];
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                datos[0] = rs.getString(campo);
                au.addItem(datos[0]);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "ERROR AL AUTOCOMPLETAR" + e, "ERROR", JOptionPane.ERROR_MESSAGE);
        }

    }

    public void limpiarTabla1() {
//   Tabla1 = new TablaVerRequisiciones();
        Tabla1 = new javax.swing.JTable();

        Tabla1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Tabla1MouseClicked(evt);
            }
        });
        Tabla1.setComponentPopupMenu(popMenu);
        jScrollPane2.setViewportView(Tabla1);
        DefaultTableModel miModelo = (DefaultTableModel) Tabla1.getModel();
        String titulos[] = {"NO. REQUISICION", "NO. EMPLEADO", "NO. COTIZACION", "ESTATUS", "ESTADO", "PROGRESO", "COSTO", "FECHA"};
        miModelo = (new DefaultTableModel(null, titulos) {
            boolean[] canEdit = new boolean[]{
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
        Tabla1.setModel(miModelo);
        Tabla1.getTableHeader().setFont(new Font("Roboto", Font.PLAIN, 14));
        Tabla1.getTableHeader().setOpaque(false);
        Tabla1.getTableHeader().setBackground(new Color(0, 78, 171));
        Tabla1.getTableHeader().setForeground(Color.white);
        Tabla1.setRowHeight(25);
        Tabla1.setShowVerticalLines(false);
        Tabla1.setCellSelectionEnabled(true);
//        Tabla1.setShowGrid(false);
    }

    public void limpiarTabla3() {
        Tabla1 = new TablaVerRequisiciones();

        Tabla1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Tabla1MouseClicked(evt);
            }
        });

        jScrollPane2.setViewportView(Tabla1);
        Tabla1.setComponentPopupMenu(popMenu);
        DefaultTableModel miModelo = (DefaultTableModel) Tabla1.getModel();
        String titulos[] = {"NO. REQUISICION", "NO. EMPLEADO", "NO. COTIZACION", "ESTATUS", "ESTADO", "PROGRESO", "COSTO", "FECHA"};
        miModelo = (new DefaultTableModel(null, titulos) {
            boolean[] canEdit = new boolean[]{
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
        Tabla1.setModel(miModelo);
        Tabla1.getTableHeader().setFont(new Font("Roboto", Font.PLAIN, 14));
        Tabla1.getTableHeader().setOpaque(false);
        Tabla1.getTableHeader().setBackground(new Color(0, 78, 171));
        Tabla1.getTableHeader().setForeground(Color.white);
        Tabla1.setRowHeight(25);
        Tabla1.setShowVerticalLines(false);
        Tabla1.setCellSelectionEnabled(true);
//        Tabla1.setShowGrid(false);
    }

    public void limpiarTabla2() {
        Tabla2.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{},
                new String[]{
                    "NO. REQUISICION", "CODIGO", "DESCRIPCION", "PROYECTO", "CANTIDAD", "REQUISITOR", "LLEGO", "PRECIO", "TE", "PO", "NOTAS", "PROVEEDOR", "RECIBO", "C.F"
                }
        ) {
            boolean[] canEdit = new boolean[]{
                false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
        jScrollPane1.setViewportView(Tabla2);
        Tabla2.setCellSelectionEnabled(true);

        if (Tabla2.getColumnModel().getColumnCount() > 0) {
            Tabla2.getColumnModel().getColumn(0).setResizable(false);
        }
    }

    public void verDatos1() {
        try {
            DefaultTableModel miModelo = (DefaultTableModel) Tabla1.getModel();
            Connection con = null;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            String sql = "select NumeroEmpleado, Id, NumeroCotizacion, Estatus, Estado, Progreso, Costo, Fecha from Requisicion where NumeroEmpleado like '" + numEmpleado + "' order by Id desc";
            ResultSet rs = st.executeQuery(sql);
            String datos[] = new String[10];
            while (rs.next()) {
                datos[0] = rs.getString("Id");
                datos[1] = numEmpleado;
                datos[2] = rs.getString("NumeroCotizacion");
                datos[3] = rs.getString("Estatus");
                datos[4] = rs.getString("Estado");
                datos[5] = rs.getString("Progreso");
                datos[6] = rs.getString("Costo");
                datos[7] = rs.getString("Fecha");
                miModelo.addRow(datos);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR AL VER DATOS TABLA 1", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    public final void verTe(String numEmpleado) {
        try {
            Connection con;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            String sql = "select * from registroempleados where Almacen like 'SI'";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                if (numEmpleado.equals(rs.getString("NumEmpleado"))) {
                    miVencido.setEnabled(true);
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error: " + e, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void compras() {
        try {
            Connection con;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            String sql = "select * from registroempleados where NumEmpleado like '" + numEmpleado + "'";
            ResultSet rs = st.executeQuery(sql);
            String com = null;
            while (rs.next()) {
                com = rs.getString("Orden");
            }
            if (com != null) {
                if (com.equals("1")) {
                    compras = true;
                } else {
                    compras = false;
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "ERROR: " + e, "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void tablaProv1() {
        Tabla1 = new TablaVerRequisiciones();

        Tabla1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Tabla1MouseClicked(evt);
            }
        });

        jScrollPane2.setViewportView(Tabla1);
        Tabla1.setComponentPopupMenu(popMenu);
        DefaultTableModel miModelo;
        String titulos[] = {"O.C", "NO. REQUISICION", "NO. EMPLEADO"};
        miModelo = (new DefaultTableModel(null, titulos) {
            boolean[] canEdit = new boolean[]{
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
        Tabla1.setModel(miModelo);
        Tabla1.getTableHeader().setFont(new Font("Roboto", Font.PLAIN, 14));
        Tabla1.getTableHeader().setOpaque(false);
        Tabla1.getTableHeader().setBackground(new Color(0, 78, 171));
        Tabla1.getTableHeader().setForeground(Color.white);
        Tabla1.setRowHeight(25);
        Tabla1.setShowVerticalLines(false);
    }

    public void exportarExcel(JTable TablaDeDatos1) {
        Workbook book;
        int columna = TablaDeDatos1.getColumnCount();
        try {
            JFileChooser fc = new JFileChooser();
            File archivo = null;
            fc.setFileFilter(new FileNameExtensionFilter("EXCEL (*.xlsx)", "xlsx"));
            int n = fc.showSaveDialog(this);

            if (n == JFileChooser.APPROVE_OPTION) {
                archivo = fc.getSelectedFile();
            }
            String a = "" + archivo;
            if (a.endsWith("xls")) {
                book = new HSSFWorkbook();
            } else {
                book = new XSSFWorkbook();
                a = archivo + ".xlsx";
            }

            Sheet hoja = book.createSheet("Reporte de requisiciones");
            Row fila = hoja.createRow(2);
            Cell col = fila.createCell(2);

            //-------------------------------ESTILOS
            org.apache.poi.ss.usermodel.Font font = book.createFont();
            CellStyle estilo1 = book.createCellStyle();

            org.apache.poi.ss.usermodel.Font font3 = book.createFont();
            CellStyle estilo3 = book.createCellStyle();

            font.setBold(true);
            font.setColor(IndexedColors.BLACK.getIndex());
            font.setFontHeightInPoints((short) 12);
            estilo1.setFont(font);

            estilo1.setAlignment(HorizontalAlignment.LEFT);

            font3.setBold(false);
            font3.setColor(IndexedColors.BLACK.getIndex());
            font3.setFontHeightInPoints((short) 15);
            estilo3.setFont(font3);

            estilo3.setAlignment(HorizontalAlignment.CENTER);
            estilo3.setWrapText(true);

            //--------------------------------------
//        hoja.setColumnWidth(2, 5000);
            //---------------------------------------
            hoja.setColumnWidth(2, 4000);
            hoja.setColumnWidth(3, 6500);
            hoja.setColumnWidth(4, 6500);
            hoja.setColumnWidth(5, 8200);
            hoja.setColumnWidth(7, 8200);
            hoja.setColumnWidth(13, 8200);

            org.apache.poi.ss.usermodel.Font font1 = book.createFont();
            CellStyle style = book.createCellStyle();

            font1.setBold(true);
            font1.setColor(IndexedColors.WHITE.getIndex());
            font1.setFontHeightInPoints((short) 16);
            style.setFont(font1);

            style.setFillBackgroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
            style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
            style.setFillPattern(SOLID_FOREGROUND);
            style.setVerticalAlignment(VerticalAlignment.BOTTOM);
            style.setAlignment(HorizontalAlignment.CENTER);
            style.setWrapText(true);

            hoja.addMergedRegion(new CellRangeAddress(
                    2,
                    2,
                    2,
                    columna + 1
            ));

            Map<String, Object> properties = new HashMap<String, Object>();
            properties.put(CellUtil.BORDER_TOP, BorderStyle.MEDIUM);
            properties.put(CellUtil.BORDER_BOTTOM, BorderStyle.MEDIUM);
            properties.put(CellUtil.BORDER_LEFT, BorderStyle.MEDIUM);
            properties.put(CellUtil.BORDER_RIGHT, BorderStyle.MEDIUM);

            properties.put(CellUtil.TOP_BORDER_COLOR, IndexedColors.BLACK.getIndex());
            properties.put(CellUtil.BOTTOM_BORDER_COLOR, IndexedColors.BLACK.getIndex());
            properties.put(CellUtil.LEFT_BORDER_COLOR, IndexedColors.BLACK.getIndex());
            properties.put(CellUtil.RIGHT_BORDER_COLOR, IndexedColors.BLACK.getIndex());

            col.setCellStyle(style);
            col.setCellValue("Reporte de requisiciones");

            for (int i = -1; i < TablaDeDatos1.getRowCount(); i++) {
                Row fila10 = hoja.createRow(i + 7);
                for (int j = 0; j < columna; j++) {
                    Cell celda = fila10.createCell(j + 2);
                    if (i == -1 && (j >= 0 && j <= columna + 1)) {
                        CellStyle s = book.createCellStyle();
                        org.apache.poi.ss.usermodel.Font f = book.createFont();
                        f.setBold(true);
                        f.setColor(IndexedColors.WHITE.getIndex());
                        s.setFont(f);
                        s.setFillForegroundColor(IndexedColors.GREY_50_PERCENT.getIndex());
                        s.setFillPattern(SOLID_FOREGROUND);
                        celda.setCellStyle(s);
                    }
                    if (i > -1 && (j > -1 && j <= columna) && (i % 2 == 0)) {
                        CellStyle s = book.createCellStyle();
                        s.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
                        s.setFillPattern(SOLID_FOREGROUND);
                        celda.setCellStyle(s);
                    }

                    if (i == -1) {
                        celda.setCellValue(String.valueOf(TablaDeDatos1.getColumnName(j)));
//                        CellUtil.setCellStyleProperties(celda, properties);
                    } else {
                        if (j == 3) {
                            CellStyle ss = book.createCellStyle();
                            ss.setWrapText(true);

                            if (i % 2 == 0) {
                                ss.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
                                ss.setFillPattern(SOLID_FOREGROUND);

                            }
                            celda.setCellStyle(ss);
                        }
                        celda.setCellValue(String.valueOf(TablaDeDatos1.getValueAt(i, j)));
                    }
                    File ad = new File(a);
                    book.write(new FileOutputStream(a));
                }
            }

            book.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(CambiarEstado.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(CambiarEstado.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void verMisRequis() {
        Thread hilo = new Thread() {
            @Override
            public void run() {
                for (;;) {
                    if (falta) {
                        try {
                            rbtnAtrasadas.setForeground(new Color(150, 0, 0));
                            sleep(500);
                            rbtnAtrasadas.setForeground(new Color(0, 112, 192));
                            sleep(500);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(VerRequisiciones.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }
        };
        hilo.start();
    }

    public final void extraerRequisiciones() {
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
                    + "or Progreso like 'EVALUACION') and FechaNew < '" + nuevaFechaFormateada + "' and NumeroEmpleado like '" + numEmpleado + "'";
            ResultSet rs = st.executeQuery(sql);
            DefaultTableModel miModelo = (DefaultTableModel) Tabla1.getModel();
            String datos[] = new String[10];
            while (rs.next()) {
                datos[0] = rs.getString("Id");
                datos[1] = numEmpleado;
                datos[2] = rs.getString("NumeroCotizacion");
                datos[3] = rs.getString("Estatus");
                datos[4] = rs.getString("Estado");
                datos[5] = rs.getString("Progreso");
                datos[6] = rs.getString("Costo");
                datos[7] = rs.getString("Fecha");
                miModelo.addRow(datos);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error: " + e, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public VerRequisiciones(String NoEmpleado) {
        try {
            UIManager.setLookAndFeel(new FlatMacLightLaf());
        } catch (Exception e) {
            e.printStackTrace();
        }
        initComponents();
        
        txtProyecto.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyPressed(java.awt.event.KeyEvent evt) {
                if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ESCAPE) {
                    // VALIDACIÓN: Solo si el campo está vacío, borramos el filtro
                    if (txtProyecto.getText().trim().isEmpty()) {
                        eliminarUltimoFiltro();
                    }
                }
            }
        });    
//        tglFiltrar.setSelected(true);
//        tglFiltrar.setVisible(false);
        verMisRequis();
        this.numEmpleado = NoEmpleado;
        ((javax.swing.plaf.basic.BasicInternalFrameUI) this.getUI()).setNorthPane(null);
        verTe(numEmpleado);
        Tabla1.getTableHeader().setFont(new Font("Roboto", Font.PLAIN, 14));
        Tabla1.getTableHeader().setOpaque(false);
        Tabla1.getTableHeader().setBackground(new Color(0, 78, 171));
        Tabla1.getTableHeader().setForeground(Color.white);
        Tabla1.setRowHeight(25);
        Tabla1.setShowGrid(false);

        Tabla2.getTableHeader().setFont(new Font("Roboto", Font.PLAIN, 14));
        Tabla2.getTableHeader().setOpaque(false);
        Tabla2.getTableHeader().setBackground(new Color(0, 78, 171));
        Tabla2.getTableHeader().setForeground(Color.white);
        Tabla2.setRowHeight(25);
        Tabla2.setShowVerticalLines(false);

        compras();

        jScrollPane1.setVerticalScrollBar(new ScrollBarCustom(new Color(0, 165, 255)));
        jScrollPane2.setVerticalScrollBar(new ScrollBarCustom(new Color(0, 165, 255)));
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        popMenu = new javax.swing.JPopupMenu();
        verDetalles = new javax.swing.JMenuItem();
        verRelaciones = new javax.swing.JMenuItem();
        jPopupMenu2 = new javax.swing.JPopupMenu();
        jMenuItem4 = new javax.swing.JMenuItem();
        jPopupMenu1 = new javax.swing.JPopupMenu();
        jPanel4 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        panelX = new javax.swing.JPanel();
        lblX = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        cmbBuscar = new RSMaterialComponent.RSComboBoxMaterial();
        txtProyecto = new rojeru_san.RSMTextFull();
        jPanel5 = new javax.swing.JPanel();
        rbtnMis = new javax.swing.JRadioButton();
        rbtnAtrasadas = new javax.swing.JRadioButton();
        rbtnTodas = new javax.swing.JRadioButton();
        rSRadioButtonMaterial1 = new javax.swing.JRadioButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        panelFiltros = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        Tabla1 = new javax.swing.JTable();
        jScrollPane1 = new javax.swing.JScrollPane();
        Tabla2 = new javax.swing.JTable();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        miVencido = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();

        verDetalles.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        verDetalles.setText("    Detalles                 ");
        verDetalles.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                verDetallesActionPerformed(evt);
            }
        });
        popMenu.add(verDetalles);

        verRelaciones.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        verRelaciones.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/nodos.png"))); // NOI18N
        verRelaciones.setText("    Ver relacion de materiales");
        verRelaciones.setToolTipText("");
        verRelaciones.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                verRelacionesActionPerformed(evt);
            }
        });
        popMenu.add(verRelaciones);

        jMenuItem4.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        jMenuItem4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/pdf.png"))); // NOI18N
        jMenuItem4.setText("Ver orden de compra                              ");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jPopupMenu2.add(jMenuItem4);

        setBackground(new java.awt.Color(255, 255, 255));
        setBorder(null);

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setLayout(new java.awt.BorderLayout());

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        panelX.setBackground(new java.awt.Color(255, 255, 255));

        lblX.setFont(new java.awt.Font("Roboto", 1, 16)); // NOI18N
        lblX.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblX.setText("  X  ");
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
        panelX.add(lblX);

        jPanel3.add(panelX);

        jPanel4.add(jPanel3, java.awt.BorderLayout.EAST);

        jLabel12.setFont(new java.awt.Font("Lexend", 1, 24)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(0, 102, 204));
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setText("Requisiciones");
        jPanel4.add(jLabel12, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel4, java.awt.BorderLayout.NORTH);

        jPanel6.setLayout(new java.awt.BorderLayout());

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        java.awt.GridBagLayout jPanel2Layout = new java.awt.GridBagLayout();
        jPanel2Layout.columnWeights = new double[] {1.0, 1.0};
        jPanel2.setLayout(jPanel2Layout);

        cmbBuscar.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "NUMERO DE REQUISICION", "NUMERO DE PARTE", "DESCRIPCION", "PROYECTO", "PROVEEDOR", "ORDEN DE COMPRA", "REQUISITOR", "FECHAS" }));
        cmbBuscar.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbBuscarItemStateChanged(evt);
            }
        });
        cmbBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbBuscarActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 15, 0, 15);
        jPanel2.add(cmbBuscar, gridBagConstraints);

        txtProyecto.setModoMaterial(true);
        txtProyecto.setPlaceholder("Introducir");
        txtProyecto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtProyectoActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 15, 0, 15);
        jPanel2.add(txtProyecto, gridBagConstraints);

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setLayout(new java.awt.GridLayout(1, 3));

        rbtnMis.setBackground(new java.awt.Color(255, 255, 255));
        buttonGroup1.add(rbtnMis);
        rbtnMis.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        rbtnMis.setForeground(new java.awt.Color(0, 112, 192));
        rbtnMis.setText("Mis requisiciones");
        rbtnMis.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        rbtnMis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtnMisActionPerformed(evt);
            }
        });
        jPanel5.add(rbtnMis);

        rbtnAtrasadas.setBackground(new java.awt.Color(255, 255, 255));
        buttonGroup1.add(rbtnAtrasadas);
        rbtnAtrasadas.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        rbtnAtrasadas.setForeground(new java.awt.Color(0, 112, 192));
        rbtnAtrasadas.setText("Requisiciones atrasadas");
        rbtnAtrasadas.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        rbtnAtrasadas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtnAtrasadasActionPerformed(evt);
            }
        });
        jPanel5.add(rbtnAtrasadas);

        buttonGroup1.add(rbtnTodas);
        rbtnTodas.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        rbtnTodas.setForeground(new java.awt.Color(0, 112, 192));
        rbtnTodas.setText("Todas las requisiciones");
        rbtnTodas.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        rbtnTodas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtnTodasActionPerformed(evt);
            }
        });
        jPanel5.add(rbtnTodas);

        buttonGroup1.add(rSRadioButtonMaterial1);
        rSRadioButtonMaterial1.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        rSRadioButtonMaterial1.setForeground(new java.awt.Color(0, 112, 192));
        rSRadioButtonMaterial1.setText("Todas O.C.");
        rSRadioButtonMaterial1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        rSRadioButtonMaterial1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rSRadioButtonMaterial1ActionPerformed(evt);
            }
        });
        jPanel5.add(rSRadioButtonMaterial1);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipady = 15;
        jPanel2.add(jPanel5, gridBagConstraints);

        jScrollPane3.setBorder(null);

        panelFiltros.setBackground(new java.awt.Color(255, 255, 255));
        panelFiltros.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEADING));
        jScrollPane3.setViewportView(panelFiltros);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        jPanel2.add(jScrollPane3, gridBagConstraints);

        jPanel6.add(jPanel2, java.awt.BorderLayout.NORTH);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        java.awt.GridBagLayout jPanel1Layout = new java.awt.GridBagLayout();
        jPanel1Layout.columnWeights = new double[] {1.0};
        jPanel1Layout.rowWeights = new double[] {1.0, 1.0};
        jPanel1.setLayout(jPanel1Layout);

        jScrollPane2.setToolTipText("hola");

        Tabla1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "NO. REQUISICION", "NO. EMPLEADO", "NO. COTIZACION", "ESTATUS", "ESTADO", "PROGRESO", "COSTO", "FECHA"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        Tabla1.setComponentPopupMenu(popMenu);
        Tabla1.setGridColor(new java.awt.Color(204, 204, 204));
        Tabla1.setRowHeight(25);
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
            Tabla1.getColumnModel().getColumn(7).setResizable(false);
        }

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(jScrollPane2, gridBagConstraints);

        Tabla2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "NO. REQUISICION", "CODIGO", "DESCRIPCION", "PROYECTO", "CANTIDAD", "REQUISITOR", "LLEGO", "TE", "PROVEEDOR"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        Tabla2.setComponentPopupMenu(jPopupMenu2);
        jScrollPane1.setViewportView(Tabla2);
        if (Tabla2.getColumnModel().getColumnCount() > 0) {
            Tabla2.getColumnModel().getColumn(0).setResizable(false);
        }

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(jScrollPane1, gridBagConstraints);

        jPanel6.add(jPanel1, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel6, java.awt.BorderLayout.CENTER);

        jMenu1.setText("File");

        jMenuItem1.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        jMenuItem1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/documento.png"))); // NOI18N
        jMenuItem1.setText("Proyectos detalles");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);
        jMenu1.add(jSeparator1);

        miVencido.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        miVencido.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/max.png"))); // NOI18N
        miVencido.setText("TE Vencido");
        miVencido.setEnabled(false);
        miVencido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miVencidoActionPerformed(evt);
            }
        });
        jMenu1.add(miVencido);
        jMenu1.add(jSeparator2);

        jMenuItem2.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        jMenuItem2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/excel_1.png"))); // NOI18N
        jMenuItem2.setText("Exportar tabla 1 a Excel                            ");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);

        jMenuItem3.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        jMenuItem3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/excel_1.png"))); // NOI18N
        jMenuItem3.setText("Exportar tabla 2 a Excel                            ");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem3);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void Tabla1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Tabla1MouseClicked
        if (evt.getClickCount() == 2) {
            if (Tabla1.getValueAt(Tabla1.getSelectedRow(), 5).toString().equals("COTIZADO") && rbtnMis.isSelected()) {
                JOptionPane.showMessageDialog(this, "ESTA ES BUENA");
            } else if (Tabla1.getValueAt(Tabla1.getSelectedRow(), 5).toString().equals("COTIZADO") && rbtnTodas.isSelected() && compras == true) {
                JFrame f = (JFrame) JOptionPane.getFrameForComponent(this);
                lib = new liberarCompra(f, true);
                lib.btnLiberar.addActionListener(this);
                lib.setVisible(true);
            }
        }
        if (tabla == 3) {
            try {
                limpiarTabla2();
                Connection con = null;
                Conexion con1 = new Conexion();
                con = con1.getConnection();
                Statement st = con.createStatement();
                DefaultTableModel miModelo = (DefaultTableModel) Tabla2.getModel();
                String sql = "select NumRequisicion, Codigo, Descripcion, Proyecto, Cantidad, NumParte, Llego, TE,OC, Notas, Proveedor, FechaRecibo, Precio, CantRecibida "
                        + "from requisiciones where OC like '" + Tabla1.getValueAt(Tabla1.getSelectedRow(), 0) + "' order by Llego asc";
                ResultSet rs = st.executeQuery(sql);
                String datos[] = new String[15];
                while (rs.next()) {
                    datos[0] = rs.getString("NumRequisicion");
                    datos[1] = rs.getString("Codigo");
                    datos[2] = rs.getString("Descripcion");
                    datos[3] = rs.getString("Proyecto");
                    datos[4] = rs.getString("Cantidad");
                    datos[5] = rs.getString("NumParte");
                    datos[6] = rs.getString("Llego");
                    datos[7] = rs.getString("Precio");
                    datos[8] = rs.getString("TE");
                    datos[9] = rs.getString("OC");
                    datos[10] = rs.getString("Notas");
                    datos[11] = rs.getString("Proveedor");
                    datos[12] = rs.getString("FechaRecibo");
                    datos[13] = rs.getString("CantRecibida");
                    double cant;
                    try {
                        cant = Double.parseDouble(datos[4]);
                    } catch (Exception e) {
                        cant = 0;
                    }
                    double cantR;
                    try {
                        cantR = Double.parseDouble(datos[13]);
                    } catch (Exception e) {
                        cantR = 0;
                    }
                    datos[13] = String.valueOf(cant - cantR);
                    miModelo.addRow(datos);
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "ERROR: " + e, "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            try {
                limpiarTabla2();
                int fila = Tabla1.getSelectedRow();
                DefaultTableModel miModelo = (DefaultTableModel) Tabla2.getModel();

                Connection con = null;
                Conexion con1 = new Conexion();
                con = con1.getConnection();
                Statement st = con.createStatement();
                String sql;
                if (tabla == 5) {
                    sql = "select NumRequisicion, Codigo, Descripcion, Proyecto, Cantidad, NumParte, Llego, Precio, TE, OC, Notas, Proveedor, FechaRecibo, CantRecibida from Requisiciones where OC like '" + Tabla1.getValueAt(fila, 0).toString() + "' order by Llego asc";
                } else {
                    sql = "select NumRequisicion, Codigo, Descripcion, Proyecto, Cantidad, NumParte, Llego, Precio, TE, OC, Notas, Proveedor, FechaRecibo, CantRecibida from Requisiciones where NumRequisicion like '" + Tabla1.getValueAt(fila, 0).toString() + "'  order by Llego asc";
                }
                ResultSet rs = st.executeQuery(sql);

                String datos[] = new String[15];

                while (rs.next()) {
                    datos[0] = rs.getString("NumRequisicion");
                    datos[1] = rs.getString("Codigo");
                    datos[2] = rs.getString("Descripcion");
                    datos[3] = rs.getString("Proyecto");
                    datos[4] = rs.getString("Cantidad");
                    datos[5] = rs.getString("NumParte");
                    datos[6] = rs.getString("Llego");
                    datos[7] = rs.getString("Precio");
                    datos[8] = rs.getString("TE");
                    datos[9] = rs.getString("OC");
                    datos[10] = rs.getString("Notas");
                    datos[11] = rs.getString("Proveedor");
                    datos[12] = rs.getString("FechaRecibo");
                    datos[13] = rs.getString("CantRecibida");
                    double cant;
                    try {
                        cant = Double.parseDouble(datos[4]);
                    } catch (Exception e) {
                        cant = 0;
                    }
                    double cantR;
                    try {
                        cantR = Double.parseDouble(datos[13]);
                    } catch (Exception e) {
                        cantR = 0;
                    }
                    datos[13] = String.valueOf(cant - cantR);
                    miModelo.addRow(datos);
                }

            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "ERROR AL MOSTRAR ITEMS: " + e, "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_Tabla1MouseClicked

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        JFrame j = (JFrame) JOptionPane.getFrameForComponent(this);
        DetallesProyectos d = new DetallesProyectos(j, false);
        d.setVisible(true);
        d.revalidate();
        d.repaint();
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void miVencidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miVencidoActionPerformed
        JFrame f = (JFrame) JOptionPane.getFrameForComponent(this);
        Reclamos r = new Reclamos(f, true);
        r.btnEditar.setEnabled(false);
        r.btnGuardar.setEnabled(false);
        r.setVisible(true);
    }//GEN-LAST:event_miVencidoActionPerformed

    private void verDetallesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_verDetallesActionPerformed
        int fila = Tabla1.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(this, "DEBES SELECCIONAR UNA REQUISICION", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
        } else {
            JFrame f = (JFrame) JOptionPane.getFrameForComponent(this);
            Detalles d = new Detalles(f, true, Tabla1.getValueAt(fila, 0).toString());
            d.setVisible(true);
        }

    }//GEN-LAST:event_verDetallesActionPerformed

    private void lblXMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblXMouseClicked
        dispose();
    }//GEN-LAST:event_lblXMouseClicked

    private void lblXMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblXMouseEntered
        panelX.setBackground(Color.red);
        lblX.setForeground(Color.white);
    }//GEN-LAST:event_lblXMouseEntered

    private void lblXMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblXMouseExited
        panelX.setBackground(Color.white);
        lblX.setForeground(Color.black);
    }//GEN-LAST:event_lblXMouseExited

    private void txtProyectoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtProyectoActionPerformed
        agregarFiltro();
    }//GEN-LAST:event_txtProyectoActionPerformed

    private void cmbBuscarItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbBuscarItemStateChanged
        if (evt.getStateChange() == java.awt.event.ItemEvent.SELECTED){
            if (cmbBuscar.getSelectedItem().equals("PROYECTO")) {
                autoCompletar("proyectos", "Proyecto");
            } else if (cmbBuscar.getSelectedItem().equals("NUMERO DE REQUISICION")) {

            } else if (cmbBuscar.getSelectedItem().equals("NUMERO DE PARTE")) {
                autoCompletar("requisiciones", "Codigo");
            } else if (cmbBuscar.getSelectedItem().equals("DESCRIPCION")) {
                try {
                    au.removeAllItems();
                } catch (Exception e) {
                }
            } else if (cmbBuscar.getSelectedItem().equals("PROVEEDOR")) {
                autoCompletar("registroprov_compras", "Nombre");
                tabla = 5;
            } else if (cmbBuscar.getSelectedItem().equals("ORDEN DE COMPRA")) {
                autoCompletar("ordencompra", "OrdenNo");
            } else if (cmbBuscar.getSelectedItem().equals("FECHAS")) {
                cmbBuscar.hidePopup(); //Cerrar el combo
                mostrarDialogoFechas(); //Abrir el calendario
                cmbBuscar.setSelectedIndex(0);
            }  
        }
    }//GEN-LAST:event_cmbBuscarItemStateChanged

    private void verRelacionesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_verRelacionesActionPerformed
        JFrame f = (JFrame) JOptionPane.getFrameForComponent(this);
        Material mat = new Material(f, true, 0);
        mat.btnGuardar.setVisible(false);
        mat.txtPlano.setVisible(false);
        mat.lblMaterial.setText("Requisicion: " + Tabla1.getValueAt(Tabla1.getSelectedRow(), 0).toString());
        mat.verRelacion(Tabla1.getValueAt(Tabla1.getSelectedRow(), 0).toString());
        mat.setVisible(true);

    }//GEN-LAST:event_verRelacionesActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        exportarExcel(Tabla1);
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        exportarExcel(Tabla2);
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void cmbBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbBuscarActionPerformed

    }//GEN-LAST:event_cmbBuscarActionPerformed

    private void rbtnMisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtnMisActionPerformed
        limpiarTabla3();
        verDatos1();
        tabla = 0;
    }//GEN-LAST:event_rbtnMisActionPerformed

    private void rbtnTodasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtnTodasActionPerformed
        if (compras) {
            limpiarTabla3();
        }
        seleccionarTabla();
        tabla = 1;
    }//GEN-LAST:event_rbtnTodasActionPerformed

    private void rSRadioButtonMaterial1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rSRadioButtonMaterial1ActionPerformed
        limpiarTablaOrden();
        verDatosOrden();
        tabla = 3;
    }//GEN-LAST:event_rSRadioButtonMaterial1ActionPerformed

    private void rbtnAtrasadasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtnAtrasadasActionPerformed
        limpiarTabla3();
        extraerRequisiciones();
    }//GEN-LAST:event_rbtnAtrasadasActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        int row = Tabla2.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Debes seleccionar una partida", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            String ruta = "\\\\192.168.100.40\\bd\\OC\\Orden_de_compra\\" + Tabla2.getValueAt(row, 9).toString() + "-" + Tabla2.getValueAt(row, 11).toString() + ".pdf";
            try {
                Desktop.getDesktop().open(new File(ruta));
            } catch (IOException ex) {
                Logger.getLogger(VerRequisiciones.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_jMenuItem4ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable Tabla1;
    private javax.swing.JTable Tabla2;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private RSMaterialComponent.RSComboBoxMaterial cmbBuscar;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JPopupMenu jPopupMenu2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JLabel lblX;
    private javax.swing.JMenuItem miVencido;
    private javax.swing.JPanel panelFiltros;
    private javax.swing.JPanel panelX;
    private javax.swing.JPopupMenu popMenu;
    private javax.swing.JRadioButton rSRadioButtonMaterial1;
    private javax.swing.JRadioButton rbtnAtrasadas;
    private javax.swing.JRadioButton rbtnMis;
    private javax.swing.JRadioButton rbtnTodas;
    private rojeru_san.RSMTextFull txtProyecto;
    private javax.swing.JMenuItem verDetalles;
    private javax.swing.JMenuItem verRelaciones;
    // End of variables declaration//GEN-END:variables

    @Override
    public void actionPerformed(ActionEvent e) {
        if (lib != null) {
            if (e.getSource() == lib.btnLiberar) {
                try {
                    Connection con;
                    Conexion con1 = new Conexion();
                    con = con1.getConnection();
                    String sql = "update requisicion set Progreso = ?, Comprar = ? where Id = ?";
                    PreparedStatement pst = con.prepareStatement(sql);

                    pst.setString(1, "APROBACION");
                    pst.setString(2, "SI");
                    pst.setString(3, Tabla1.getValueAt(Tabla1.getSelectedRow(), 0).toString());

                    int n = pst.executeUpdate();

                    if (n > 0) {
                        limpiarTabla3();
                        seleccionarTabla();
                        JOptionPane.showMessageDialog(this, "DATOS GUARDADOS");
                        lib.dispose();
                    }

                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(this, "ERRORR: " + ex, "ERROR", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }
}
