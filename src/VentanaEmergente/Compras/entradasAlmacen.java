package VentanaEmergente.Compras;
import Conexiones.Conexion;
import java.awt.FlowLayout;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.toedter.calendar.JDateChooser;
import java.awt.Desktop;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;

public class entradasAlmacen extends javax.swing.JPanel {

    private JPopupMenu popupFiltrar;
    private JMenuItem opcionFecha;
    private JMenuItem opcionEntreFechas;
    private JMenuItem opcionPorEmpleado;

    public entradasAlmacen() {
        initComponents();
        
        tablaEntradasAlmacen.getTableHeader().setBackground(new java.awt.Color(250, 250, 250)); // gris claro
        tablaEntradasAlmacen.getTableHeader().setForeground(java.awt.Color.BLACK); // color del texto

        
        // Crear popup y opciones
        popupFiltrar = new JPopupMenu();
        opcionFecha = new JMenuItem("Fecha determinada");
        opcionEntreFechas = new JMenuItem("Entre fechas");
        opcionPorEmpleado = new JMenuItem("Empleado");

        // Agregar opciones al popup
        popupFiltrar.add(opcionFecha);
        popupFiltrar.add(opcionEntreFechas);
        popupFiltrar.add(opcionPorEmpleado);

        // Agregar listeners a cada opción
        opcionFecha.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                filtrarPorFecha();
            }
        });

        opcionEntreFechas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                filtrarEntreFechas();
            }
        });

        opcionPorEmpleado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                filtrarPorEmpleado();
            }
        });

        // Mostrar popup al hacer clic en el botón
        btnFiltrarEntradasAlmacen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                popupFiltrar.show(btnFiltrarEntradasAlmacen, 0, btnFiltrarEntradasAlmacen.getHeight());
            }
        });
    }

    private void filtrarPorFecha() {
        // Crear el panel con un solo calendario
        javax.swing.JPanel panelFecha = new javax.swing.JPanel();
        panelFecha.setLayout(new java.awt.GridLayout(1, 2, 5, 5));

        javax.swing.JLabel lblFecha = new javax.swing.JLabel("Selecciona una fecha:");
        JDateChooser fechaSeleccionada = new JDateChooser();

        panelFecha.add(lblFecha);
        panelFecha.add(fechaSeleccionada);

        int opcion = javax.swing.JOptionPane.showConfirmDialog(
            this,
            panelFecha,
            "Filtrar por fecha",
            javax.swing.JOptionPane.OK_CANCEL_OPTION,
            javax.swing.JOptionPane.PLAIN_MESSAGE
        );

        if (opcion == javax.swing.JOptionPane.OK_OPTION) {
            if (fechaSeleccionada.getDate() == null) {
                javax.swing.JOptionPane.showMessageDialog(
                    this,
                    "Debes seleccionar una fecha.",
                    "Error",
                    javax.swing.JOptionPane.WARNING_MESSAGE
                );
                return;
            }

            // Convertir la fecha al formato compatible con la base de datos
            SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
            String fecha = formato.format(fechaSeleccionada.getDate());

            Connection con = null;
            try {
                Conexion con1 = new Conexion();
                con = con1.getConnection();

                limpiarTablaEntradasAlmacen();

                String sql = "SELECT e.numempleado, r.nombre, r.apellido, e.hora " +
                             "FROM entradasalmacen e " +
                             "JOIN registroempleados r ON e.numempleado = r.numempleado " +
                             "WHERE DATE(e.hora) = ? " +
                             "ORDER BY e.hora DESC";

                PreparedStatement ps = con.prepareStatement(sql);
                ps.setString(1, fecha);

                ResultSet rs = ps.executeQuery();
                DefaultTableModel modelo = (DefaultTableModel) tablaEntradasAlmacen.getModel();

                int contador = 0;
                int cont = 0;
                while (rs.next()) {
                    cont++;
                    String numEmpleado = rs.getString("numempleado");
                    String nombre = rs.getString("nombre");
                    String apellido = rs.getString("apellido");
                    String hora = rs.getString("hora");
                    lblRegistros.setText(String.valueOf(cont));
                    modelo.addRow(new Object[]{numEmpleado, nombre, apellido, hora});
                    contador++;
                }

                if (contador == 0) {
                    javax.swing.JOptionPane.showMessageDialog(
                        this,
                        "No se encontraron registros en la fecha seleccionada.",
                        "Sin resultados",
                        javax.swing.JOptionPane.INFORMATION_MESSAGE
                    );
                    cargarEntradasAlmacen();
                }

                rs.close();
                ps.close();

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (con != null) con.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }


    private void filtrarEntreFechas() {
        // Crear el panel con dos calendarios
        javax.swing.JPanel panelFechas = new javax.swing.JPanel();
        panelFechas.setLayout(new java.awt.GridLayout(2, 2, 5, 5));

        javax.swing.JLabel lblInicio = new javax.swing.JLabel("Fecha inicial:");
        javax.swing.JLabel lblFin = new javax.swing.JLabel("Fecha final:");

        JDateChooser fechaInicio = new JDateChooser();
        JDateChooser fechaFin = new JDateChooser();

        panelFechas.add(lblInicio);
        panelFechas.add(fechaInicio);
        panelFechas.add(lblFin);
        panelFechas.add(fechaFin);

        int opcion = javax.swing.JOptionPane.showConfirmDialog(
            this,
            panelFechas,
            "Filtrar entre fechas",
            javax.swing.JOptionPane.OK_CANCEL_OPTION,
            javax.swing.JOptionPane.PLAIN_MESSAGE
        );

        // Si el usuario presionó OK
        if (opcion == javax.swing.JOptionPane.OK_OPTION) {
            if (fechaInicio.getDate() == null || fechaFin.getDate() == null) {
                javax.swing.JOptionPane.showMessageDialog(
                    this,
                    "Debes seleccionar ambas fechas.",
                    "Error",
                    javax.swing.JOptionPane.WARNING_MESSAGE
                );
                return;
            }

            // Convertir fechas al formato compatible con la base de datos
            SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
            String fecha1 = formato.format(fechaInicio.getDate());
            String fecha2 = formato.format(fechaFin.getDate());

            Connection con = null;
            try {
                Conexion con1 = new Conexion();
                con = con1.getConnection();

                limpiarTablaEntradasAlmacen();

                String sql = "SELECT e.numempleado, r.nombre, r.apellido, e.hora " +
                             "FROM entradasalmacen e " +
                             "JOIN registroempleados r ON e.numempleado = r.numempleado " +
                             "WHERE DATE(e.hora) BETWEEN ? AND ? " +
                             "ORDER BY e.hora DESC";

                PreparedStatement ps = con.prepareStatement(sql);
                ps.setString(1, fecha1);
                ps.setString(2, fecha2);

                ResultSet rs = ps.executeQuery();
                DefaultTableModel modelo = (DefaultTableModel) tablaEntradasAlmacen.getModel();

                int contador = 0;
                int cont = 0;
                while (rs.next()) {
                    cont++;
                    String numEmpleado = rs.getString("numempleado");
                    String nombre = rs.getString("nombre");
                    String apellido = rs.getString("apellido");
                    String hora = rs.getString("hora");
                    lblRegistros.setText(String.valueOf(cont));
                    modelo.addRow(new Object[]{numEmpleado, nombre, apellido, hora});
                    contador++;
                }

                if (contador == 0) {
                    javax.swing.JOptionPane.showMessageDialog(
                        this,
                        "No se encontraron registros entre las fechas seleccionadas.",
                        "Sin resultados",
                        javax.swing.JOptionPane.INFORMATION_MESSAGE
                    );
                    cargarEntradasAlmacen();
                }

                rs.close();
                ps.close();

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (con != null) con.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void filtrarPorEmpleado() {
    // Pedir al usuario el número, nombre o apellido
    String criterio = javax.swing.JOptionPane.showInputDialog(this,
            "Ingresa número, nombre o apellido del empleado:",
            "Filtrar por empleado",
            javax.swing.JOptionPane.QUESTION_MESSAGE);
    
    if(criterio == null || criterio.trim().isEmpty()) {
        return; // usuario canceló o no escribió nada
    }

    criterio = criterio.trim(); // quitar espacios al inicio y final

    Connection con = null;
    try {
        Conexion con1 = new Conexion();
        con = con1.getConnection();

        // Limpiar la tabla antes de mostrar resultados
        limpiarTablaEntradasAlmacen();

        // Consulta con JOIN para buscar por numempleado, nombre o apellido
        String sql = "SELECT e.numempleado, r.nombre, r.apellido, e.hora " +
                     "FROM entradasalmacen e " +
                     "JOIN registroempleados r ON e.numempleado = r.numempleado " +
                     "WHERE e.numempleado LIKE ? OR r.nombre LIKE ? OR r.apellido LIKE ? " +
                     "ORDER BY e.hora DESC";
        
        PreparedStatement ps = con.prepareStatement(sql);
        String criterioLike = "%" + criterio + "%";
        ps.setString(1, criterioLike);
        ps.setString(2, criterioLike);
        ps.setString(3, criterioLike);

        ResultSet rs = ps.executeQuery();
        DefaultTableModel modelo = (DefaultTableModel) tablaEntradasAlmacen.getModel();

        int contador = 0;
        int cont = 0;
        while(rs.next()) {
            cont++;
            String numEmpleado = rs.getString("numempleado");
            String nom = rs.getString("nombre");
            String ape = rs.getString("apellido");
            String hora = rs.getString("hora");
            lblRegistros.setText(String.valueOf(cont));
            modelo.addRow(new Object[]{numEmpleado, nom, ape, hora});
            contador++;
        }
        
        if (contador == 0) {
            javax.swing.JOptionPane.showMessageDialog(
                this,
                "No se encontraron registros con el empleado ingresado.",
                "Sin resultados",
                javax.swing.JOptionPane.INFORMATION_MESSAGE
            );
            cargarEntradasAlmacen();
        }

        rs.close();
        ps.close();

    } catch(Exception e) {
        e.printStackTrace();
    } finally {
        try {
            if(con != null) con.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}



    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPopupMenu1 = new javax.swing.JPopupMenu();
        jPopupMenu2 = new javax.swing.JPopupMenu();
        txtEntradasAlmacen = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablaEntradasAlmacen = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        btnExcel = new javax.swing.JButton();
        btnFiltrarEntradasAlmacen = new javax.swing.JButton();
        btnRecargarTabla = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        lblRegistros = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));
        setMaximumSize(new java.awt.Dimension(813, 354));
        setPreferredSize(new java.awt.Dimension(813, 354));
        setLayout(new java.awt.GridBagLayout());

        txtEntradasAlmacen.setFont(new java.awt.Font("Roboto", 1, 16)); // NOI18N
        txtEntradasAlmacen.setForeground(new java.awt.Color(0, 165, 252));
        txtEntradasAlmacen.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txtEntradasAlmacen.setText("Entradas a Almacén");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 0, 6);
        add(txtEntradasAlmacen, gridBagConstraints);

        jScrollPane2.setMaximumSize(null);
        jScrollPane2.setMinimumSize(null);

        tablaEntradasAlmacen.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "No. Empleado", "Nombre", "Apellido", "Fecha"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablaEntradasAlmacen.setSelectionBackground(new java.awt.Color(0, 153, 255));
        jScrollPane2.setViewportView(tablaEntradasAlmacen);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 14, 6);
        add(jScrollPane2, gridBagConstraints);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        btnExcel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/excel_1.png"))); // NOI18N
        btnExcel.setFocusPainted(false);
        jPanel1.setLayout(new FlowLayout(FlowLayout.CENTER));
        jPanel1.add(btnExcel);
        btnExcel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExcelActionPerformed(evt);
            }
        });
        jPanel1.add(btnExcel);

        btnFiltrarEntradasAlmacen.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/filtrar.png"))); // NOI18N
        btnFiltrarEntradasAlmacen.setText("Filtrar por");
        btnFiltrarEntradasAlmacen.setFocusPainted(false);
        btnFiltrarEntradasAlmacen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFiltrarEntradasAlmacenActionPerformed(evt);
            }
        });
        jPanel1.add(btnFiltrarEntradasAlmacen);

        btnRecargarTabla.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/recargar_16.png"))); // NOI18N
        btnRecargarTabla.setFocusPainted(false);
        btnRecargarTabla.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRecargarTablaActionPerformed(evt);
            }
        });
        jPanel1.add(btnRecargarTabla);

        jLabel2.setFont(new java.awt.Font("Bahnschrift", 1, 12)); // NOI18N
        jLabel2.setText("Total de registros:");
        jPanel1.add(jLabel2);

        lblRegistros.setFont(new java.awt.Font("Bahnschrift", 1, 12)); // NOI18N
        lblRegistros.setText("0");
        jPanel1.add(lblRegistros);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 0, 6);
        add(jPanel1, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

    private void btnRecargarTablaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRecargarTablaActionPerformed
        cargarEntradasAlmacen();
    }//GEN-LAST:event_btnRecargarTablaActionPerformed

    private void btnExcelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcelActionPerformed
        try {
            Workbook book;
            JFileChooser fc = new JFileChooser();
            fc.setFileFilter(new FileNameExtensionFilter("EXCEL (*.xlsx)", "xlsx"));
            File archivo = null;
            int n = fc.showSaveDialog(this);
            if (n == JFileChooser.APPROVE_OPTION) {
                archivo = fc.getSelectedFile();
            } else {
                return; // El usuario canceló
            }

            String a = archivo.toString();
            if (a.endsWith("xls")) {
                book = new HSSFWorkbook();
            } else {
                book = new XSSFWorkbook();
                a = a + ".xlsx";
            }

            Sheet hoja = book.createSheet("Entradas Almacén");

            // Crear fila de encabezados
            Row filaEncabezado = hoja.createRow(0);
            for (int j = 0; j < tablaEntradasAlmacen.getColumnCount(); j++) {
                Cell celda = filaEncabezado.createCell(j);
                celda.setCellValue(tablaEntradasAlmacen.getColumnName(j));
            }

            // Llenar datos
            for (int i = 0; i < tablaEntradasAlmacen.getRowCount(); i++) {
                Row fila = hoja.createRow(i + 1);
                for (int j = 0; j < tablaEntradasAlmacen.getColumnCount(); j++) {
                    Cell celda = fila.createCell(j);
                    Object valor = tablaEntradasAlmacen.getValueAt(i, j);
                    if (valor != null) {
                        celda.setCellValue(valor.toString());
                    }
                }
            }

            // Ajustar ancho de columnas
            for (int j = 0; j < tablaEntradasAlmacen.getColumnCount(); j++) {
                hoja.autoSizeColumn(j);
            }

            // Guardar archivo
            File archivoFinal = new File(a);
            FileOutputStream out = new FileOutputStream(archivoFinal);
            book.write(out);
            out.close();
            book.close();

            //Abrir el archivo automáticamente
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().open(archivoFinal);
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al exportar o abrir el archivo:\n" + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnExcelActionPerformed

    private void btnFiltrarEntradasAlmacenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFiltrarEntradasAlmacenActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnFiltrarEntradasAlmacenActionPerformed

    


    public void limpiarTablaEntradasAlmacen(){
        DefaultTableModel miModelo = (DefaultTableModel) tablaEntradasAlmacen.getModel();
        String titulos[] = {"No. Empleado", "Nombre", "Apellido", "Fecha"};
        miModelo = new DefaultTableModel(null, titulos); // crea modelo vacío con columnas
        tablaEntradasAlmacen.setModel(miModelo); // asigna el modelo a la tabla
    }
    
    public void cargarEntradasAlmacen() {
        Connection con = null;
        try {
            Conexion con1 = new Conexion();
            con = con1.getConnection();

            limpiarTablaEntradasAlmacen();

            String sql = "SELECT e.numempleado, r.nombre, r.apellido, e.hora " +
                         "FROM entradasalmacen e " +
                         "JOIN registroempleados r ON e.numempleado = r.numempleado " +
                         "ORDER BY e.hora DESC";

            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            DefaultTableModel modelo = (DefaultTableModel) tablaEntradasAlmacen.getModel();

            while (rs.next()) {
                modelo.addRow(new Object[]{
                    rs.getString("numempleado"),
                    rs.getString("nombre"),
                    rs.getString("apellido"),
                    rs.getString("hora")
                });
            }

            rs.close();
            ps.close();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try { if (con != null) con.close(); } catch (Exception e) {}
        }
    }

    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnExcel;
    private javax.swing.JButton btnFiltrarEntradasAlmacen;
    private javax.swing.JButton btnRecargarTabla;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JPopupMenu jPopupMenu2;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblRegistros;
    private javax.swing.JTable tablaEntradasAlmacen;
    private javax.swing.JLabel txtEntradasAlmacen;
    // End of variables declaration//GEN-END:variables
}
