package VentanaEmergente.Cotizacion;

import Conexiones.Conexion;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.mxrck.autocompleter.TextAutoCompleter;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.Window;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

public class AgregarCotizacion extends javax.swing.JDialog {

    private TextAutoCompleter au;
    private ArrayList array;
    private final String numEmpleado;
    private String numCliente;

    private boolean mostrarAgregar = false;

    private void formatearFilas() {
        for (int i = 0; i < Tabla1.getRowCount(); i++) {
            Tabla1.setValueAt(i + 1, i, 0);
        }
    }

    private void agregarFila() {
        DefaultTableModel modelo = (DefaultTableModel) Tabla1.getModel();
        int ultimaFila = modelo.getRowCount() - 1;
        modelo.insertRow(ultimaFila, new Object[]{"", "", ""});

        Tabla1.setRowSelectionInterval(ultimaFila, ultimaFila);
        Tabla1.editCellAt(ultimaFila, 0);
        if (Tabla1.getEditorComponent() != null) {
            Tabla1.getEditorComponent().requestFocusInWindow();
        }
        formatearFilas();
    }

    public final void limpiarTabla() {
        agregarItems();
        Tabla1 = new TablaCoti();
        Tabla1.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{
                    {null, null, null, null, null, null}
                },
                new String[]{
                    "No. item", "Descripcion", "Cantidad", "Precio Unitario", "Impuestos", "Importe", "Id"
                }
        ) {
            Class[] types = new Class[]{
                java.lang.Integer.class, java.lang.Object.class, java.lang.Float.class,
                java.lang.Float.class, java.lang.Float.class, java.lang.Float.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean[]{
                false, true, true, true, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                if (rowIndex == getRowCount() - 1) {
                    return false;
                }
                return canEdit[columnIndex];
            }
        });

        Tabla1.getColumnModel().getColumn(1).setCellEditor(new AutoCompleteCellEditor(array));

        if (Tabla1.getColumnModel().getColumnCount() > 0) {
            Tabla1.getColumnModel().getColumn(0).setMinWidth(50);
            Tabla1.getColumnModel().getColumn(0).setPreferredWidth(50);
            Tabla1.getColumnModel().getColumn(0).setMaxWidth(50);
            Tabla1.getColumnModel().getColumn(2).setMinWidth(150);
            Tabla1.getColumnModel().getColumn(2).setPreferredWidth(150);
            Tabla1.getColumnModel().getColumn(2).setMaxWidth(150);
            Tabla1.getColumnModel().getColumn(3).setMinWidth(150);
            Tabla1.getColumnModel().getColumn(3).setPreferredWidth(150);
            Tabla1.getColumnModel().getColumn(3).setMaxWidth(150);
            Tabla1.getColumnModel().getColumn(4).setMinWidth(150);
            Tabla1.getColumnModel().getColumn(4).setPreferredWidth(150);
            Tabla1.getColumnModel().getColumn(4).setMaxWidth(150);
            Tabla1.getColumnModel().getColumn(5).setMinWidth(150);
            Tabla1.getColumnModel().getColumn(5).setPreferredWidth(150);
            Tabla1.getColumnModel().getColumn(5).setMaxWidth(150);
            Tabla1.getColumnModel().getColumn(6).setMinWidth(0);
            Tabla1.getColumnModel().getColumn(6).setPreferredWidth(0);
            Tabla1.getColumnModel().getColumn(6).setMaxWidth(0);
        }

        Tabla1.setComponentPopupMenu(jPopupMenu1);
        Tabla1.getTableHeader().setFont(new Font("Trebuchet MS", Font.BOLD, 14));
        Tabla1.setFont(new Font("Trebuchet MS", Font.PLAIN, 12));
        jScrollPane1.setViewportView(Tabla1);

        Tabla1.getModel().addTableModelListener(e -> {
            if (e.getType() == TableModelEvent.UPDATE) {
                int columna = e.getColumn();
                int fila = Tabla1.getSelectedRow();

                if (columna == 2 || columna == 3) {
                    try {
                        float cantidad = Float.parseFloat(Tabla1.getValueAt(fila, 2).toString());
                        float precio = Float.parseFloat(Tabla1.getValueAt(fila, 3).toString());
                        float iva = Float.parseFloat(cmbIva.getSelectedItem().toString());

                        float subtotal = cantidad * precio;
                        float impuesto = (subtotal * iva) / 100;
                        float importe = subtotal + impuesto;

                        Tabla1.setValueAt(impuesto, fila, 4);  // columna Impuestos
                        Tabla1.setValueAt(importe, fila, 5);   // columna Importe

                    } catch (Exception ex) {
                    }
                }
            }
        });

        TableCellRenderer agregarRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {

                if (row == table.getRowCount() - 1) {
                    JLabel label = new JLabel();
                    label.setOpaque(true);
                    label.setBackground(new Color(240, 248, 255));

                    if (mostrarAgregar && column == 1) {
                        label.setText("  Agregar fila");
                        label.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/agregar_16.png")));
                        label.setForeground(new Color(31, 116, 153));
                        label.setFont(new Font("Trebuchet MS", Font.BOLD, 12));
                    }
                    return label;
                }
                return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            }
        };

        for (int i = 0; i < Tabla1.getColumnCount(); i++) {
            Tabla1.getColumnModel().getColumn(i).setCellRenderer(agregarRenderer);
        }

        Tabla1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int fila = Tabla1.rowAtPoint(e.getPoint());
                if (fila == Tabla1.getRowCount() - 1 && mostrarAgregar) {
                    agregarFila();
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                mostrarAgregar = false;
                Tabla1.setCursor(Cursor.getDefaultCursor());
                Tabla1.repaint();
            }
        });

        Tabla1.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                int fila = Tabla1.rowAtPoint(e.getPoint());
                boolean enUltimaFila = fila == Tabla1.getRowCount() - 1;

                if (enUltimaFila != mostrarAgregar) {
                    mostrarAgregar = enUltimaFila;
                    Tabla1.repaint();
                }

                Tabla1.setCursor(enUltimaFila
                        ? Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)
                        : Cursor.getDefaultCursor());
            }
        });
    }

    public PdfPCell border(PdfPCell celda, float med, int align) {
        celda.setBorderWidthBottom(med);
        celda.setBorderWidthTop(med);
        celda.setBorderWidthRight(med);
        celda.setBorderWidthLeft(med);
        celda.setBorderColor(BaseColor.LIGHT_GRAY);
        celda.setHorizontalAlignment(align);
        return celda;
    }

    public final void insertarTotal(float total, String id) throws SQLException {
        Connection con = new Conexion().getConnection();
        String sql = "update cotizacion set total = ? where idcotizacion = ?";
        PreparedStatement pst = con.prepareStatement(sql);

        pst.setFloat(1, total);
        pst.setString(2, id);

        pst.executeUpdate();
    }

    public String extraerDirectorio() {
        try (Connection con = new Conexion().getConnection()) {
            String sql = "select * from conf_cotizacion order by idconf_cotizacion desc";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()) {
                return rs.getString("urldireccion");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al extraer directorio para guardar carpeta: " + e, "Error", JOptionPane.ERROR_MESSAGE);
        }
        return null;
    }
    
    public final void crearPdf() {
        try {
            try (Connection con = new Conexion().getConnection()) {
                Statement st = con.createStatement();
                String sql = "select * from conf_cotizacion";
                ResultSet rs = st.executeQuery(sql);
                String url = "";
                String empresa = "";
                String rfc = "";
                String correo = "";
                String direccion = "";
                String vendedor = "";
                while (rs.next()) {
                    url = rs.getString("urlimg");
                    empresa = rs.getString("empresa");
                    rfc = rs.getString("rfc");
                    correo = rs.getString("correo");
                    direccion = rs.getString("direccion");
                    vendedor = rs.getString("vendedor");
                }
                
                String id = lblCotizacion.getText().replace("Cotizacion: ", "");
                String forma = empresa.substring(0,1).toUpperCase() + String.format("%05d", Integer.valueOf(id));
                File archivo = new File(extraerDirectorio() + "\\" + forma);
                if (!archivo.getName().contains(".pdf")) {
                    archivo = new File(archivo.getAbsoluteFile() + ".pdf");
                }
                
                Document document = new Document(PageSize.A4, 0, 0, 271, 80);
                PdfWriter weiter = PdfWriter.getInstance(document, new FileOutputStream(archivo));
                CabezeraCotizaciones cabezera = new CabezeraCotizaciones();
                //BD
                
                cabezera.correo = correo;
                cabezera.direccion = direccion;
                cabezera.empresa = empresa;
                cabezera.rfc = rfc;
                cabezera.logo = url;
                
                cabezera.empresaCli = txtCliente.getText();
                cabezera.direccionCli = txtDomicilio.getText();
                cabezera.rfcCli = txtRfc.getText();
                
                cabezera.fecha = txtCreacion.getText();
                cabezera.fechaVencimiento = txtFecha.getDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                cabezera.vendedor = vendedor;
                cabezera.coti = id;
                
                cabezera.setEncabezado("Jorge");
                weiter.setPageEvent(cabezera);
                document.open();
                
                PdfPTable tabInfo = new PdfPTable(6);
                tabInfo.setTotalWidth(527);
                float med[] = {50, 300, 90, 90, 90, 90};
                tabInfo.setWidths(med);
                tabInfo.setLockedWidth(true);
                
                com.itextpdf.text.Font fuente5 = new com.itextpdf.text.Font();
                fuente5.setSize(10);
                fuente5.setFamily("Trebuchet MS");
                fuente5.setColor(0, 0, 0);
                
                com.itextpdf.text.Font fuente6 = new com.itextpdf.text.Font();
                fuente6.setSize(10);
                fuente6.setStyle(com.itextpdf.text.Font.BOLD);
                fuente6.setFamily("Trebuchet MS");
                fuente6.setColor(0, 0, 0);
                
                float tot = 0;
                float sub = 0;
                float iva = 0;
                DecimalFormat df = new DecimalFormat("$ #,###.##");
                for (int i = 0; i < Tabla1.getRowCount() - 1; i++) {
                    for (int j = 0; j < Tabla1.getColumnCount() - 1; j++) {
                        int align;
                        String cont;
                        switch (j) {
                            case 1:
                                align = Element.ALIGN_LEFT;
                                break;
                            case 3:
                            case 4:
                            case 5:
                                align = Element.ALIGN_RIGHT;
                                break;
                            default:
                                align = Element.ALIGN_CENTER;
                        }
                        try {
                            cont = Tabla1.getValueAt(i, j).toString();
                            if (j >= 3) {
                                cont = String.valueOf(df.format((float) Tabla1.getValueAt(i, j)));
                                if (j == 4) {
                                    iva += Float.parseFloat(Tabla1.getValueAt(i, 4).toString());
                                }
                                if (j == 5) {
                                    tot += Float.parseFloat(Tabla1.getValueAt(i, 5).toString());
                                }
                            }
                        } catch (Exception e) {
                            cont = " ";
                        }
                        tabInfo.addCell(border(new PdfPCell(new Paragraph(cont, fuente5)), 0.2f, align));
                    }
                }
                
                for (int i = Tabla1.getRowCount(); i < 21; i++) {
                    for (int j = 0; j < Tabla1.getColumnCount() - 1; j++) {
                        String d = " ";
                        if (j == 0) {
                            d = String.valueOf(i);
                        }
                        tabInfo.addCell(border(new PdfPCell(new Paragraph(d, fuente5)), 0.2f, Element.ALIGN_CENTER));
                    }
                }
                
                Object totales[] = new Object[6];
                totales[0] = "Subtotal:";
                totales[1] = df.format(tot - iva);
                totales[2] = "IVA:";
                totales[3] = df.format(iva);
                totales[4] = "Total:";
                totales[5] = df.format(tot);
                
                insertarTotal(tot, id);
                
                BaseColor base[] = new BaseColor[6];
                base[1] = new BaseColor(209, 235, 252);
                base[3] = new BaseColor(145, 208, 242);
                base[5] = new BaseColor(114, 188, 221);
                
                com.itextpdf.text.Font fuentes[] = new com.itextpdf.text.Font[6];
                fuentes[0] = fuente6;
                fuentes[1] = fuente5;
                fuentes[2] = fuente6;
                fuentes[3] = fuente5;
                fuentes[4] = fuente6;
                fuentes[5] = fuente5;
                
                int cont = 0;
                for (int i = 0; i < 6; i++) {
                    PdfPCell subTotal = new PdfPCell(new Paragraph(totales[i].toString(), fuentes[i]));
                    float bor;
                    if (cont == 0) {
                        subTotal.setColspan(5);
                        bor = 0;
                        cont++;
                    } else {
                        subTotal.setBackgroundColor(base[i]);
                        bor = 0.2f;
                        cont = 0;
                    }
                    subTotal = border(subTotal, bor, Element.ALIGN_RIGHT);
                    tabInfo.addCell(subTotal);
                }
                
                document.add(tabInfo);
                document.close();
                Desktop.getDesktop().open(archivo);
            }
        } catch (FileNotFoundException | DocumentException | SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex, "Error", JOptionPane.ERROR_MESSAGE);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public final void agregarClientes() {
        try (Connection con = new Conexion().getConnection()) {
            Statement st = con.createStatement();
            String sql = "select * from clientes_cotizacion order by nombre asc";
            ResultSet rs = st.executeQuery(sql);
            if (au != null) {
                au.removeAllItems();
            }
            au = new TextAutoCompleter(txtCliente);
            while (rs.next()) {
                au.addItem(rs.getString("Nombre"));
            }

            txtFecha.setDate(LocalDate.now().plusMonths(1));
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error: " + e, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public final void agregarItems() {
        try (Connection con = new Conexion().getConnection()) {
            Statement st = con.createStatement();
            String sql = "select descripcion from items_cotizacion order by descripcion asc";
            ResultSet rs = st.executeQuery(sql);
            array = new ArrayList();
            while (rs.next()) {
                array.add(rs.getString("descripcion"));
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error: " + e, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public final void guardarCliente() {
        try {
            try (Connection con = new Conexion().getConnection()) {
                ResultSet rs = con.prepareStatement(
                        "SELECT COUNT(*) FROM clientes_cotizacion WHERE nombre = '" + txtCliente.getText() + "'"
                ).executeQuery();
                rs.next();

                if (rs.getInt(1) > 0) {
                    return;
                }
                String sql = "insert into clientes_cotizacion (nombre, domicilio, rfc) values(?,?,?)";
                PreparedStatement pst = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

                pst.setString(1, txtCliente.getText());
                pst.setString(2, txtDomicilio.getText());
                pst.setString(3, txtRfc.getText());

                int n = pst.executeUpdate();
                if (n < 1) {
                    ResultSet rs2 = pst.getGeneratedKeys();
                    if (rs2.next()) {
                        numCliente = rs2.getString(1);
                        agregarClientes();
                    }
                    JOptionPane.showMessageDialog(this, "Error al guardar cliente", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al guardar cliente: " + e, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public final void limpiarFormulario() {
        txtDomicilio.setText("");
        txtRfc.setText("");
    }
    
    public final void verCliente(String cliente) {
        try {
            try (Connection con = new Conexion().getConnection()) {
                Statement st = con.createStatement();
                txtCliente.setText(cliente);
                limpiarFormulario();
                String sql = "select * from clientes_cotizacion where nombre like '" + cliente + "'";
                ResultSet rs = st.executeQuery(sql);
                while (rs.next()) {
                    txtDomicilio.setText(rs.getString("domicilio"));
                    txtRfc.setText(rs.getString("rfc"));
                    numCliente = rs.getString("idCliente");
                }
            }
        } catch (SQLException e) {
            
        }
    }

    public final void verItems(String id) {
        try {
            try (Connection con = new Conexion().getConnection()) {
                lblCotizacion.setText("Cotizacion: " + id);
                Statement st = con.createStatement();
                String sql = "select * from items_cotizacion where idcotizacion like '" + id + "'";
                ResultSet rs = st.executeQuery(sql);
                DefaultTableModel miModelo = (DefaultTableModel) Tabla1.getModel();
                Object datos[] = new Object[7];
                miModelo.removeRow(0);
                while (rs.next()) {
                    datos[0] = 0;
                    datos[1] = rs.getString("descripcion");
                    datos[2] = rs.getFloat("cantidad");
                    datos[3] = rs.getFloat("precio");
                    try {
                        float cantidad = Float.parseFloat(datos[2].toString());
                        float precio = Float.parseFloat(datos[3].toString());
                        float iva = Float.parseFloat(cmbIva.getSelectedItem().toString());

                        float subtotal = cantidad * precio;
                        float impuesto = (subtotal * iva) / 100;
                        float importe = subtotal + impuesto;

                        datos[4] = impuesto;  // columna Impuestos
                        datos[5] = importe;   // columna Importe

                    } catch (Exception ex) {
                    }
                    datos[6] = rs.getString("idItems_cotizacion");
                    boolean act = rs.getBoolean("activo");
                    if (act) {
                        miModelo.addRow(datos);
                    }
                }
                miModelo.addRow(new Object[]{"", "", "", "", "", "", ""});
                formatearFilas();
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error: " + e, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public final String getVendedor() {
        try {
            try (Connection con = new Conexion().getConnection()) {
                Statement st = con.createStatement();
                String sql = "select * from conf_cotizacion order by idconf_cotizacion desc";
                ResultSet rs = st.executeQuery(sql);
                while (rs.next()) {
                    return rs.getString("Vendedor");
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al extraer vendedor: " + e, "Error", JOptionPane.ERROR_MESSAGE);
        }
        return null;
    }

    public final void guardarItems() {
        try {
            Connection con = new Conexion().getConnection();
            String sql = "insert into items_cotizacion (idcotizacion, descripcion, precio, cantidad, activo) values (?,?,?,?,?)";
            PreparedStatement pst = con.prepareStatement(sql);
            String sql2 = "update items_cotizacion set descripcion = ?, precio = ?, cantidad = ? where iditems_cotizacion = ?";
            PreparedStatement pst2 = con.prepareStatement(sql2);

            if (lblCotizacion.getText().equals("Cotizacion: ")) {
                String sql3 = "insert into cotizacion (fecha, idcliente, numempleado, total, estado, iva, activo) values (?,?,?,?,?,?,?)";
                PreparedStatement pst3 = con.prepareStatement(sql3, Statement.RETURN_GENERATED_KEYS);

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

                pst3.setString(1, sdf.format(new Date()));
                pst3.setString(2, numCliente);
                pst3.setString(3, numEmpleado);
                pst3.setFloat(4, 0);
                pst3.setString(5, "Nuevo");
                pst3.setString(6, cmbIva.getSelectedItem().toString());
                pst3.setBoolean(7, true);

                int n = pst3.executeUpdate();
                if (n < 1) {
                    JOptionPane.showMessageDialog(this, "Los datos no se han guardado exitosamente", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    ResultSet rs = pst3.getGeneratedKeys();
                    if (rs.next()) {
                        int id = rs.getInt(1);
                        lblCotizacion.setText("Cotizacion: " + id);
                    }
                }

            }

            for (int i = 0; i < Tabla1.getRowCount() - 1; i++) {
                String descripcion = Tabla1.getValueAt(i, 1).toString();
                float cantidad = Tabla1.getValueAt(i, 2) != null ? Float.parseFloat(Tabla1.getValueAt(i, 2).toString()) : 0;
                float precio = Tabla1.getValueAt(i, 3) != null ? Float.parseFloat(Tabla1.getValueAt(i, 3).toString()) : 0;
                String id = Tabla1.getValueAt(i, 6) != null ? Tabla1.getValueAt(i, 6).toString() : "";

                int n = 0;
                if (!descripcion.equals("")) {
                    if (id.equals("")) {
                        pst.setString(1, lblCotizacion.getText().replace("Cotizacion: ", ""));
                        pst.setString(2, descripcion);
                        pst.setFloat(3, precio);
                        pst.setFloat(4, cantidad);
                        pst.setBoolean(5, true);

                        n = pst.executeUpdate();
                    } else {
                        pst2.setString(1, descripcion);
                        pst2.setFloat(2, precio);
                        pst2.setFloat(3, cantidad);
                        pst2.setString(4, id);

                        n = pst2.executeUpdate();
                    }

                    if (n < 1) {
                        JOptionPane.showMessageDialog(this, "El item : " + descripcion + " no se guardo exitosamente", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al guardar item: " + e, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public final void inhabilitarItem() {
        try {
            Connection con = new Conexion().getConnection();
            String sql = "update items_cotizacion set activo = ? where iditems = ?";
            PreparedStatement pst = con.prepareStatement(sql);

            for (int i = 0; i < Tabla1.getSelectedRows().length; i++) {
                pst.setBoolean(1, false);
                System.out.println(Tabla1.getValueAt(Tabla1.getSelectedRows()[i], 6).toString());
                pst.setString(2, Tabla1.getValueAt(Tabla1.getSelectedRows()[i], 6).toString());

                int n = pst.executeUpdate();

                if (n < 1) {
                    JOptionPane.showMessageDialog(this, "Error al eliminar item: " + Tabla1.getValueAt(Tabla1.getSelectedRows()[i], 1).toString(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al borrar articulo: " + e, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public AgregarCotizacion(java.awt.Frame parent, boolean modal, String numEmpleado, boolean band) {
        super(parent, modal);
        initComponents();
        limpiarTabla();
        setLocationRelativeTo(parent);
        if (band)
            agregarClientes();
        this.numEmpleado = numEmpleado;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPopupMenu1 = new javax.swing.JPopupMenu();
        btnEliminar = new javax.swing.JMenuItem();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        lblCotizacion = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        txtCliente = new javax.swing.JTextField();
        jButton4 = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        txtDomicilio = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtRfc = new javax.swing.JTextField();
        jPanel6 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtCreacion = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        txtFecha = new com.github.lgooddatepicker.components.DatePicker();
        jLabel4 = new javax.swing.JLabel();
        cmbIva = new javax.swing.JComboBox<>();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        Tabla1 = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();

        btnEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/error.png"))); // NOI18N
        btnEliminar.setText("Eliminar fila (s)                                      ");
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });
        jPopupMenu1.add(btnEliminar);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(1323, 747));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new java.awt.BorderLayout(10, 10));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        java.awt.GridBagLayout jPanel2Layout = new java.awt.GridBagLayout();
        jPanel2Layout.columnWeights = new double[] {1.0};
        jPanel2.setLayout(jPanel2Layout);

        lblCotizacion.setText("Cotizacion: ");
        lblCotizacion.setFont(new java.awt.Font("Trebuchet MS", 1, 12)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jPanel2.add(lblCotizacion, gridBagConstraints);

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setLayout(new java.awt.GridBagLayout());

        jLabel5.setText("Cliente:");
        jLabel5.setFont(new java.awt.Font("Trebuchet MS", 1, 12)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 5);
        jPanel5.add(jLabel5, gridBagConstraints);

        txtCliente.setFont(new java.awt.Font("Trebuchet MS", 0, 12)); // NOI18N
        txtCliente.setBackground(new java.awt.Color(255, 255, 255));
        txtCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtClienteActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.ipadx = 150;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 5);
        jPanel5.add(txtCliente, gridBagConstraints);

        jButton4.setText("+");
        jButton4.setBackground(new java.awt.Color(51, 51, 51));
        jButton4.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        jButton4.setForeground(new java.awt.Color(255, 255, 255));
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jPanel5.add(jButton4, new java.awt.GridBagConstraints());

        jLabel6.setText("Domicilio:");
        jLabel6.setFont(new java.awt.Font("Trebuchet MS", 1, 12)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 5);
        jPanel5.add(jLabel6, gridBagConstraints);

        txtDomicilio.setEditable(false);
        txtDomicilio.setFont(new java.awt.Font("Trebuchet MS", 0, 12)); // NOI18N
        txtDomicilio.setBackground(new java.awt.Color(255, 255, 255));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.ipadx = 150;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 5);
        jPanel5.add(txtDomicilio, gridBagConstraints);

        jLabel7.setFont(new java.awt.Font("Trebuchet MS", 1, 12)); // NOI18N
        jLabel7.setText("RFC:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 5);
        jPanel5.add(jLabel7, gridBagConstraints);

        txtRfc.setEditable(false);
        txtRfc.setFont(new java.awt.Font("Trebuchet MS", 0, 12)); // NOI18N
        txtRfc.setBackground(new java.awt.Color(255, 255, 255));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.ipadx = 150;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 5);
        jPanel5.add(txtRfc, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 5, 0);
        jPanel2.add(jPanel5, gridBagConstraints);

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 20, 5));

        jLabel2.setFont(new java.awt.Font("Trebuchet MS", 1, 12)); // NOI18N
        jLabel2.setText("Fecha de creacion:");
        jPanel6.add(jLabel2);

        txtCreacion.setFont(new java.awt.Font("Trebuchet MS", 0, 12)); // NOI18N
        txtCreacion.setBackground(new java.awt.Color(255, 255, 255));
        txtCreacion.setEnabled(false);
        txtCreacion.setPreferredSize(new java.awt.Dimension(150, 22));
        txtCreacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCreacionActionPerformed(evt);
            }
        });
        jPanel6.add(txtCreacion);

        jLabel1.setText("Vencimiento: ");
        jLabel1.setFont(new java.awt.Font("Trebuchet MS", 1, 12)); // NOI18N
        jPanel6.add(jLabel1);

        txtFecha.setFont(new java.awt.Font("Trebuchet MS", 0, 12)); // NOI18N
        jPanel6.add(txtFecha);

        jLabel4.setFont(new java.awt.Font("Trebuchet MS", 1, 12)); // NOI18N
        jLabel4.setText("IVA");
        jPanel6.add(jLabel4);

        cmbIva.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "8", "16" }));
        jPanel6.add(cmbIva);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        jPanel2.add(jPanel6, gridBagConstraints);

        jPanel1.add(jPanel2, java.awt.BorderLayout.PAGE_START);

        jPanel3.setLayout(new java.awt.BorderLayout());

        Tabla1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Descripcion", "Cantidad", "Precio Unitario", "Impuestos", "Importe", "null"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Float.class, java.lang.Float.class, java.lang.Float.class, java.lang.Float.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                true, true, true, true, false, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        Tabla1.setComponentPopupMenu(jPopupMenu1);
        jScrollPane1.setViewportView(Tabla1);
        if (Tabla1.getColumnModel().getColumnCount() > 0) {
            Tabla1.getColumnModel().getColumn(5).setMinWidth(50);
            Tabla1.getColumnModel().getColumn(5).setPreferredWidth(50);
            Tabla1.getColumnModel().getColumn(5).setMaxWidth(50);
        }

        jPanel3.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 40, 5));

        jButton1.setText("Imprimir");
        jButton1.setBackground(new java.awt.Color(51, 153, 255));
        jButton1.setFont(new java.awt.Font("Trebuchet MS", 1, 12)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel4.add(jButton1);

        jButton2.setText("Guardar");
        jButton2.setBackground(new java.awt.Color(51, 153, 0));
        jButton2.setFont(new java.awt.Font("Trebuchet MS", 1, 12)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel4.add(jButton2);

        jButton3.setText("Enviar cotizacion");
        jButton3.setBackground(new java.awt.Color(51, 51, 51));
        jButton3.setFont(new java.awt.Font("Trebuchet MS", 1, 12)); // NOI18N
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel4.add(jButton3);

        jButton5.setBackground(new java.awt.Color(0, 153, 153));
        jButton5.setFont(new java.awt.Font("Trebuchet MS", 1, 12)); // NOI18N
        jButton5.setForeground(new java.awt.Color(255, 255, 255));
        jButton5.setText("Crear orden de trabajo");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        jPanel4.add(jButton5);

        jButton6.setBackground(new java.awt.Color(255, 102, 0));
        jButton6.setFont(new java.awt.Font("Trebuchet MS", 1, 12)); // NOI18N
        jButton6.setForeground(new java.awt.Color(255, 255, 255));
        jButton6.setText("Venta");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        jPanel4.add(jButton6);

        jPanel3.add(jPanel4, java.awt.BorderLayout.PAGE_END);

        jPanel1.add(jPanel3, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        guardarCliente();
        guardarItems();
        crearPdf();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        inhabilitarItem();
        limpiarTabla();
        verItems(lblCotizacion.getText().replace("Cotizacion: ", ""));
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        guardarCliente();
        guardarItems();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void txtClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtClienteActionPerformed
        verCliente(txtCliente.getText());
    }//GEN-LAST:event_txtClienteActionPerformed

    private void txtCreacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCreacionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCreacionActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        Window ventana = SwingUtilities.getWindowAncestor(this);
        JFrame f = (JFrame) ventana;
        AgregarCliente add = new AgregarCliente(f, true);
        add.setLocationRelativeTo(f);
        add.verCliente(txtCliente.getText());
        add.verContactos();
        add.setVisible(true);
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton6ActionPerformed

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                AgregarCotizacion dialog = new AgregarCotizacion(new javax.swing.JFrame(), true, null, true);
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
    private javax.swing.JTable Tabla1;
    private javax.swing.JMenuItem btnEliminar;
    public javax.swing.JComboBox<String> cmbIva;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblCotizacion;
    private javax.swing.JTextField txtCliente;
    public javax.swing.JTextField txtCreacion;
    private javax.swing.JTextField txtDomicilio;
    public com.github.lgooddatepicker.components.DatePicker txtFecha;
    private javax.swing.JTextField txtRfc;
    // End of variables declaration//GEN-END:variables
}
