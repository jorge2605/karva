package VentanaEmergente.Compras;

import Conexiones.Conexion;
import Conexiones.ConexionChat;
import Controlador.compras.DatosOC;
import VentanaEmergente.Inicio1.Espera;
import VentanaEmergente.Recibos.CancelarOrden;
import com.app.sockets.chat.Cliente;
import com.formdev.flatlaf.themes.FlatMacLightLaf;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.mxrck.autocompleter.TextAutoCompleter;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumn;
import org.krysalis.barcode4j.impl.code39.Code39Bean;
import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider;
import pruebas.ElegirProveedor;
import pruebas.EstadoCuentas;
import scrollPane.PanelRound;

public class ComprasNew extends javax.swing.JFrame implements ActionListener {

    private final ImageIcon iconEmpleado = new ImageIcon(getClass().getResource("/Iconos/empleado.png"));
    private final ImageIcon iconPO = new ImageIcon(getClass().getResource("/Iconos/po1.png"));
    private final ImageIcon iconFecha = new ImageIcon(getClass().getResource("/Iconos/fecha.png"));
    private final Color nuevo = new Color(0, 153, 0);
    private final Color completado = new Color(51, 51, 255);
    private final Color pendiente = new Color(255, 209, 0);
    private final Color inactivo = new Color(204, 0, 0);
    private String pass;
    private String correo;
    private final String numEmpleado;
    private String requisitor;
    private String comprador;
    private String estado;
    public String da[];
    public ElegirProveedor elegir;
    private String inicial;
    private String num;
    private String proy;
    private TextAutoCompleter autocompleter;
    private Stack<String> pilaProveedor;
    private int fil;
    private Fecha fecha;
    private int filaFecha;
    private javax.swing.JDialog dialogEntradas = null;

    public String mostrarDialogoEmergente() {
        ImageIcon icono = new ImageIcon(getClass().getResource("/Img/archivo.png"));
        String opcion = (String) JOptionPane.showInputDialog(
                null,
                "Seleccione una opción:",
                "Selección de Opción",
                JOptionPane.PLAIN_MESSAGE,
                icono,
                null,
                null
        );
        if (opcion != null) {
            return opcion;
        } else {
            return null;
        }
    }
    
    public void crearNotificacion() {

        try {
            Connection con = null;
            ConexionChat con1 = new ConexionChat();
            con = con1.getConnection();

            Connection con2 = null;
            Conexion con3 = new Conexion();
            con2 = con3.getConnection();

            Statement st = con.createStatement();
            Date d = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            String fecha = sdf.format(d);
            String sql2 = "select * from registroempleados where Aprobacion like '1'";
            Statement st2 = con2.prepareCall(sql2);
            ResultSet rs2 = st2.executeQuery(sql2);
            String ip;
            int port;
            String empleado;
            while (rs2.next()) {
                ip = rs2.getString("Ip");
                port = rs2.getInt("Puerto");
                empleado = rs2.getString("NumEmpleado");

                String not = "noti" + empleado;
                String sql = "insert into " + not + " (Departamento,Titulo,Texto,Fecha) values (?,?,?,?)";
                PreparedStatement pst = con.prepareStatement(sql);
                pst.setString(1, "1");
                pst.setString(2, "NUEVA APROBACION");
                pst.setString(3, "TIENES UNA NUEVA APROBACION, LA REQUISICION NUMERO: " + lblId.getText());
                pst.setString(4, fecha);

                pst.executeUpdate();
                Cliente cliente = new Cliente(port + 1, "NUEVA APROBACION", ip);
                Thread hilo = new Thread(cliente);
                hilo.start();
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "ERROR: " + e, "ERROR", JOptionPane.ERROR_MESSAGE);
        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(this, "Error al enviar notificacion" + ex, "error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cargarDatosProveedor(Connection con, DatosOC datos, String proveedor) throws SQLException {
        String sql = "select * from registroprov_compras where Nombre like ?";

        PreparedStatement pst = con.prepareStatement(sql);
        pst.setString(1, proveedor);
        ResultSet rs = pst.executeQuery();
        while (rs.next()) {
            datos.proveedor = rs.getString("Nombre");
            datos.condicion = rs.getString("Condiciones");
            datos.iva = rs.getString("Iva");
            datos.moneda = rs.getString("Moneda");
            datos.isr = rs.getString("Isr");
        }
        if (datos.iva == null) {
            datos.iva = "0";
        }
        if (datos.moneda == null) {
            datos.moneda = "";
        }
    }

    private void generarNuevaOC(Connection con, DatosOC datos, String proveedor) throws SQLException {
        Statement st = con.createStatement();
        String sql = "select OrdenNo from OrdenCompra";
        ResultSet rs = st.executeQuery(sql);
        String ultima = "";
        while (rs.next()) {
            ultima = rs.getString("OrdenNo");
        }
        String numero = ultima.substring(3);
        int consecutivo = Integer.parseInt(numero);
        datos.cadena = "OC" + inicial + (consecutivo + 1);
        datos.cadena2 = datos.cadena + "-" + proveedor;
        String add
                = "insert into OrdenCompra "
                + "(OrdenNo,RequisicionNo,Fecha) "
                + "values (?,?,?)";

        PreparedStatement pst = con.prepareStatement(add);
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        pst.setString(1, datos.cadena);
        pst.setString(2, lblId.getText());
        pst.setString(3, sdf.format(d));

        pst.executeUpdate();
    }

    public BufferedImage generateBarcodeSinLetras(String code) throws IOException {
        Code39Bean barcodeBean = new Code39Bean();
        final int dpi = 150;

        barcodeBean.setModuleWidth(0.2);
        barcodeBean.setWideFactor(3);
        barcodeBean.doQuietZone(false);

        barcodeBean.setMsgPosition(org.krysalis.barcode4j.HumanReadablePlacement.HRP_NONE);

        BitmapCanvasProvider canvas = new BitmapCanvasProvider(dpi, BufferedImage.TYPE_BYTE_BINARY, false, 0);
        barcodeBean.generateBarcode(canvas, code);
        canvas.finish();

        return canvas.getBufferedImage();
    }

    private void generarPDF(Connection con, DatosOC datos, String proveedor, String cotizacion, boolean editada, int i) throws Exception {

        String ruta = "\\\\192.168.100.40\\bd\\OC\\Orden_de_compra\\" + datos.cadena2 + ".pdf";
        Document document = new Document(PageSize.A4, 36, 36, 270, 60);
        CabezeraCompras cab = new CabezeraCompras();
        cab.correo = correo;
        cab.direccion = "Camino viejo a la rosita #305, Cd Juarez, Chihuahua, CP: 32580";
        cab.empresa = "Servicios Industriales 3i S de RL MI";
        cab.rfc = "SII150213KR7";
        cab.logo = "Img/Rec2.png";
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(ruta));
        writer.setPageEvent(cab);
        document.open();
        // =====================================================
        // COLORES
        // =====================================================
        BaseColor colorTitulo;
        BaseColor colorTotal;
        BaseColor colorComentarios;

        colorTitulo = new BaseColor(233, 109, 53);
        colorTotal = new BaseColor(255, 193, 169);
        colorComentarios = new BaseColor(233, 109, 53);

        // =====================================================
        // FUENTES
        // =====================================================
        com.itextpdf.text.Font fuente = new com.itextpdf.text.Font();
        com.itextpdf.text.Font fuente1 = new com.itextpdf.text.Font();
        com.itextpdf.text.Font fuente2 = new com.itextpdf.text.Font();
        com.itextpdf.text.Font fuente3 = new com.itextpdf.text.Font();

        fuente.setSize(8);
        fuente.setFamily("Arial");
        fuente.setColor(BaseColor.BLACK);
        fuente.setStyle(com.itextpdf.text.Font.BOLD);

        fuente1.setSize(8);
        fuente1.setFamily("Arial");
        fuente1.setColor(BaseColor.WHITE);
        fuente1.setStyle(com.itextpdf.text.Font.BOLD);

        fuente2.setSize(8);
        fuente2.setFamily("Arial");
        fuente2.setColor(BaseColor.BLACK);

        fuente3.setSize(8);
        fuente3.setFamily("Arial");
        fuente3.setColor(BaseColor.BLACK);

        // =====================================================
        // DATOS PROVEEDOR
        // =====================================================
        String sqlProveedor = "select * from registroprov_compras where Nombre like ?";

        PreparedStatement pstProveedor = con.prepareStatement(sqlProveedor);
        pstProveedor.setString(1, proveedor);
        ResultSet rsProveedor = pstProveedor.executeQuery();

        String nombreProveedor = "";
        String direccionProveedor = "";
        String telefonoProveedor = "";
        String condicion = "";
        String iva = "0";
        String moneda = "";
        String isr = "";

        while (rsProveedor.next()) {
            nombreProveedor = rsProveedor.getString("Nombre");
            direccionProveedor = rsProveedor.getString("Direccion");
            telefonoProveedor = rsProveedor.getString("Telefono");
            condicion = rsProveedor.getString("Condiciones");
            iva = rsProveedor.getString("Iva");
            moneda = rsProveedor.getString("Moneda");
            isr = rsProveedor.getString("Isr");
        }

        cab.empresaCli = nombreProveedor;
        cab.direccionCli = direccionProveedor;
        cab.rfcCli = telefonoProveedor;
        cab.coti = datos.cadena;
        cab.cotizacion = cotizacion;
        cab.requisitor = num;
        cab.proyecto = proy;
        cab.condicion = condicion;

        // =====================================================
        // TEXTO
        // =====================================================
        Paragraph p10 = new Paragraph("Si usted tiene alguna pregunta sobre esta orden de compra, por favor, pongase en contacto con", fuente2);
        p10.setAlignment(Element.ALIGN_CENTER);
        Paragraph p11 = new Paragraph("Daniela Castro: Tel: 656-281-9317 E-mail: compras01@si3i.com", fuente2);
        p11.setAlignment(Element.ALIGN_CENTER);
        Paragraph p12 = new Paragraph("NR: " + lblId.getText(), fuente);
        p12.setAlignment(Element.ALIGN_CENTER);
        // =====================================================
        // TABLA PRODUCTOS
        // =====================================================
        PdfPTable tbl2 = new PdfPTable(7);

        float[] medidaCeldas2 = {15, 130, 60, 20, 35, 30, 40};
        tbl2.setWidths(medidaCeldas2);
        tbl2.setWidthPercentage(100);

        // =====================================================
        // PRODUCTOS
        // =====================================================
        double total = 0;

        int articulo = 0;

        Statement st3 = con.createStatement();

        for (int k = 0; k < Tabla1.getRowCount(); k++) {

            String sql3;
            ResultSet rs3;
            sql3 = "select * from requisiciones " + "where Id like '" + Tabla1.getValueAt(k, 0).toString() + "'";
            rs3 = st3.executeQuery(sql3);
            String dap = "";
            while (rs3.next()) {
                dap = rs3.getString("Proveedor");
            }
            boolean ban = true;

            if (Tabla1.getValueAt(k, 5) == null) {
                ban = false;
            } else if (Tabla1.getValueAt(k, 5).toString().equals("")) {
                ban = false;
            }

            if (proveedor.equals(dap) && ban == true) {

                // =====================================================
                // UPDATE NORMAL / EDITADA
                // =====================================================
                if (editada) {
                    String sql = "update requisiciones " + "set Estado = ? " + "where Id = ?";
                    PreparedStatement pst = con.prepareStatement(sql);

                    pst.setString(1, "TERMINADO");
                    pst.setString(2, Tabla1.getValueAt(k, 0).toString());

                    pst.executeUpdate();
                } else {
                    String sql30 = "update requisiciones " + "set OC = ? where Id = ?";
                    PreparedStatement pst30 = con.prepareStatement(sql30);

                    pst30.setString(1, datos.cadena);
                    pst30.setString(2, Tabla1.getValueAt(k, 0).toString());
                    pst30.executeUpdate();
                }

                articulo++;

                double ad1 = Double.parseDouble(Tabla1.getValueAt(k, 6).toString());
                total += ad1;
                for (int j = 0; j < 7; j++) {
                    PdfPCell c1 = new PdfPCell(new Phrase(Tabla1.getValueAt(k, j).toString(), fuente3));
                    if (j == 0) {
                        c1 = new PdfPCell(new Phrase("" + articulo, fuente3));
                    }
                    c1.setBorderColor(BaseColor.LIGHT_GRAY);
                    tbl2.addCell(c1);
                }
            }
        }

        // =====================================================
        // FILAS VACIAS
        // =====================================================
        for (int k = articulo; k < 20; k++) {
            articulo++;
            for (int j = 0; j < 7; j++) {
                PdfPCell c1;
                if (j == 0) {
                    c1 = new PdfPCell(new Phrase("" + articulo, fuente3));
                } else {
                    c1 = new PdfPCell(new Phrase(" ", fuente3));
                }
                c1.setBorderColor(BaseColor.LIGHT_GRAY);
                tbl2.addCell(c1);
            }
        }

        // =====================================================
        // TOTALES
        // =====================================================
        DecimalFormatSymbols separador = new DecimalFormatSymbols();
        separador.setDecimalSeparator('.');
        DecimalFormat formato = new DecimalFormat("#,###.##", separador);
        double ivaTotal = (total * Double.parseDouble(iva)) / 100;
        double isrTotal = 0;
        if (isr != null && !isr.equals("")) {
            isrTotal = ((total) * Double.parseDouble(isr)) / 100;
        }
        double granTotal = total + ivaTotal - isrTotal;

        //===================================== SEGUNDO TOTAL =====================================
        PdfPTable tabTotales = new PdfPTable(4);
        float med[] = {300, 50, 50, 50};
        tabTotales.setWidths(med);
        tabTotales.setWidthPercentage(100);
        PdfPCell blank = new PdfPCell(new Paragraph(" "));
        blank.setBorder(0);

        //SUBTOTAL========================================
        PdfPCell sub = new PdfPCell(new Phrase("SUBTOTAL", fuente));
        PdfPCell subP = new PdfPCell(new Phrase("$ " + formato.format(total), fuente2));

        sub.setBorder(0);
        sub.setHorizontalAlignment(Element.ALIGN_RIGHT);
        subP.setBorder(0);

        subP.setBackgroundColor(colorTotal);

        tabTotales.addCell(blank);
        tabTotales.addCell(blank);
        tabTotales.addCell(sub);
        tabTotales.addCell(subP);

        //ISR========================
        if (isr != null && !isr.equals("")) {
            PdfPCell is = new PdfPCell(new Phrase("ISR", fuente));
            PdfPCell isP = new PdfPCell(new Phrase("$ " + formato.format(isrTotal), fuente2));

            is.setBorder(0);
            is.setHorizontalAlignment(Element.ALIGN_RIGHT);
            isP.setBorder(0);

            tabTotales.addCell(blank);
            tabTotales.addCell(blank);
            tabTotales.addCell(is);
            tabTotales.addCell(isP);
        }

        //IVA===============================================
        PdfPCell com = new PdfPCell(new Phrase("Comentarios o instrucciones especiales", fuente1));
        com.setBackgroundColor(colorComentarios);
        com.setBorder(0);

        PdfPCell iv = new PdfPCell(new Phrase("IVA", fuente));
        PdfPCell ivP = new PdfPCell(new Phrase("$ " + formato.format(ivaTotal), fuente2));

        iv.setBorder(0);
        iv.setHorizontalAlignment(Element.ALIGN_RIGHT);
        ivP.setBorder(0);

        tabTotales.addCell(com);
        tabTotales.addCell(blank);
        tabTotales.addCell(iv);
        tabTotales.addCell(ivP);

        //TOTAL==============================================
        PdfPCell c11 = new PdfPCell(new Phrase("" + moneda, fuente));
        PdfPCell c22 = new PdfPCell(new Phrase("TOTAL ", fuente));
        PdfPCell c33 = new PdfPCell(new Phrase("$ " + formato.format(granTotal), fuente2));
        c11.setBorder(0);
        c22.setBorder(0);
        c22.setHorizontalAlignment(Element.ALIGN_RIGHT);
        c33.setBorder(0);
        c11.setHorizontalAlignment(Element.ALIGN_LEFT);
        c22.setHorizontalAlignment(Element.ALIGN_RIGHT);
        c33.setHorizontalAlignment(Element.ALIGN_LEFT);
        c33.setBackgroundColor(colorTotal);
        tabTotales.addCell(c11);
        tabTotales.addCell(blank);
        tabTotales.addCell(c22);
        tabTotales.addCell(c33);

        // =====================================================
        // BARCODE
        // =====================================================
        BufferedImage barcodeImage = generateBarcodeSinLetras(datos.cadena);
        File tempFile = File.createTempFile("barcode", ".png");
        ImageIO.write(barcodeImage, "png", tempFile);
        Image im = Image.getInstance(tempFile.getAbsolutePath());
        im.setAbsolutePosition((PageSize.A4.getWidth() / 2) - 60, 10);
        im.scaleAbsolute(120, 30);
        document.add(im);
        // =====================================================
        // AGREGAR
        // =====================================================
        document.add(tbl2);
        document.add(tabTotales);

        document.add(p10);
        document.add(p11);
        document.add(p12);

        document.close();

        tempFile.delete();

        if (!editada) {
            JFrame f = (JFrame) JOptionPane.getFrameForComponent(this);
            enviarCorreo enviar = new enviarCorreo(f, true);
            Stack<String> pila = new Stack<>();
            Stack<String> idStack = new Stack<>();
            Stack<String> pilaProv = new Stack<>();

            pila.push(datos.cadena2);
            pilaProv.push(datos.proveedor);
            idStack.push(Tabla1.getValueAt(i, 0).toString());

            enviar.llenarBotones(pila, pilaProv, idStack);
            enviar.btnAgregar.setVisible(false);
            enviar.tabla = Tabla1;
            enviar.numRequi = datos.cadena2;
            enviar.correo = correo;
            enviar.contra = pass;
            enviar.transaccion = "orden";
            enviar.ordenReal = datos.cadena;
            for (int j = 0; j < enviar.panel.length; j++) {
                enviar.partes[j].setEnabled(false);
                enviar.label[j].setEnabled(false);
            }
            enviar.setVisible(true);
        }
    }

    private DatosOC obtenerDatosOC(Connection con, String proveedor, boolean editada) throws SQLException {
        DatosOC datos = new DatosOC();
        Statement st = con.createStatement();
        if (editada) {
            String po4
                    = "select * from detallesedicionpo "
                    + "where IdArticulo like '"
                    + Tabla1.getValueAt(0, 0).toString()
                    + "'";
            ResultSet rs4 = st.executeQuery(po4);
            String proveedorAnterior = "";
            String po = "";
            while (rs4.next()) {
                proveedorAnterior = rs4.getString("Proveedor");
                po = rs4.getString("PO");
            }
            if (proveedorAnterior.equals(proveedor)) {
                datos.cadena = po;
                datos.cadena2 = po + "-" + proveedor;
            } else {
                generarNuevaOC(con, datos, proveedor);
            }
        } else {
            generarNuevaOC(con, datos, proveedor);
        }
        cargarDatosProveedor(con, datos, proveedor);
        return datos;
    }

    private void abrirPDF(String cadena2) {
        System.out.println(cadena2);
        File carpeta = new File("\\\\192.168.100.40\\bd\\OC\\Orden_de_compra"); // Carpeta donde están los PDFs
        File[] archivos = carpeta.listFiles((dir, nombre)
                -> nombre.startsWith(cadena2) && nombre.toLowerCase().endsWith(".pdf"));
        if (archivos != null && archivos.length > 0) {
            try {
                Desktop.getDesktop().open(archivos[0]);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error al tratar de abrir pdf: " + ex, "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "No se encontró el archivo.", "Advertencia", JOptionPane.WARNING_MESSAGE);
        }
    }

    private String pedirCotizacion(String proveedor) {
        String cotizacion = "";
        do {
            cotizacion = JOptionPane.showInputDialog(this, "INGRESA NUMERO DE COTIZACION DEL PROVEEDOR " + proveedor);
        } while (cotizacion == null || cotizacion.trim().isEmpty());
        return cotizacion;
    }

    public void verificarOrdenes(String requisicion) {
        try {
            Connection con;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            String sql = "select Progreso, Id from requisicion where Id like '" + requisicion + "'";
            ResultSet rs = st.executeQuery(sql);
            String estado = "";
            while (rs.next()) {
                estado = rs.getString("Progreso");
            }
            if (estado.equals("NUEVO") || estado.equals("COTIZANDO") || estado.equals("APROBADO")) {
                String sql2 = "select * from requisiciones where NumRequisicion like '" + requisicion + "'";
                Statement st2 = con.createStatement();
                ResultSet rs2 = st2.executeQuery(sql2);
                int contR = 0;
                int contE = 0;
                while (rs2.next()) {
                    String oc = rs2.getString("OC");
                    contR++;
                    if (oc != null) {
                        contE++;
                    }
                }
                if (contR == contE) {
                    String sql3 = "update requisicion set Progreso = ? where Id = ?";
                    PreparedStatement pst = con.prepareStatement(sql3);

                    pst.setString(1, "COMPRADO");
                    pst.setString(2, requisicion);

                    int n = pst.executeUpdate();

                    if (n > 0) {
                        JOptionPane.showMessageDialog(this, "Esta Requisicion esta completa, su estado paso a 'COMPRADO'");
                        limpiarPanel();
                        verRequisiciones(null);
                    } else {
                        JOptionPane.showMessageDialog(this, "ERROR: No se mando a comprar, favor de avisar", "ERROR", JOptionPane.ERROR_MESSAGE);
                    }

                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "ERROR: " + e, "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void generarOrdenCompra(boolean editada) {
        try {
            int tam = elegir.botones.length;
            for (int i = 0; i < tam; i++) {
                if (!elegir.panel[i].getBackground().equals(java.awt.Color.green)) {
                    continue;
                }
                String proveedor = elegir.botones[i].getText();
                String cotizacion = pedirCotizacion(proveedor);
                try (Connection con = new Conexion().getConnection()) {
                    DatosOC datos = obtenerDatosOC(con, proveedor, editada);
                    generarPDF(con, datos, proveedor, cotizacion, editada, i);
                    abrirPDF(datos.cadena2);
                }
            }
            limpiarPanel();
            verRequisiciones(null);
            if (!editada) {
                verificarOrdenes(lblId.getText());
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "ERROR: " + e);

            e.printStackTrace();
        }
    }

    public final void crearOrden() {
        Stack<String> lista = new Stack<>();

        try {
            for (int i = 0; i < Tabla1.getRowCount(); i++) {
                if (Tabla1.getValueAt(i, 5) == null) {
                } else if ("".equals(Tabla1.getValueAt(i, 5).toString())) {
                } else if (Tabla1.getValueAt(i, 7) != null) {
                    if (!"".equals(Tabla1.getValueAt(i, 7).toString())) {
                        Connection con;
                        Conexion con1 = new Conexion();
                        con = con1.getConnection();
                        Statement st2 = con.createStatement();
                        String sql2;
                        ResultSet rs2;
                        sql2 = "select OC, proveedor, Codigo from requisiciones where Id like '" + Tabla1.getValueAt(i, 0).toString() + "'";
                        rs2 = st2.executeQuery(sql2);
                        String oc;
                        while (rs2.next()) {
                            oc = rs2.getString("OC");
                            if (oc == null) {
                                String datos[] = new String[10];
                                datos[0] = rs2.getString("Proveedor");
                                datos[1] = rs2.getString("Codigo");
                                if (lista.search(datos[0]) == -1) {
                                    lista.push(datos[0]);
                                }
                            }
                        }

                    }
                }
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "ERROR: " + e, "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        if (!lista.isEmpty()) {
            da = new String[lista.size()];
            for (int i = 0; i < lista.size(); i++) {
                da[i] = lista.get(i);
            }
            JFrame j = (JFrame) JOptionPane.getFrameForComponent(this);
            elegir = new ElegirProveedor(j, false, lista.size(), da, lblId.getText());
            elegir.setVisible(true);
            elegir.btnCrear.addActionListener(this);
            elegir.btnCancelar.addActionListener(this);
        } else {
            JOptionPane.showMessageDialog(this, "DEBES LLENAR PRECIO Y PROVEEDOR DE ALGUNA PARTIDA");
        }
    }

    public final void crearLabelAbajo(JPanel jPanel4, String etiqueta, ImageIcon url) {
        PanelRound pnlIconos = new scrollPane.PanelRound();
        pnlIconos.setBackground(new java.awt.Color(210, 210, 210));
        pnlIconos.setRoundBottomLeft(20);
        pnlIconos.setRoundBottomRight(20);
        pnlIconos.setRoundTopLeft(20);
        pnlIconos.setRoundTopRight(20);
        jPanel4.add(pnlIconos, new java.awt.GridBagConstraints());

        JLabel lblTitulos = new JLabel(etiqueta);
        lblTitulos.setCursor(new Cursor(Cursor.HAND_CURSOR));
        lblTitulos.setFont(new java.awt.Font("Trebuchet MS", 1, 10));
        lblTitulos.setForeground(new java.awt.Color(51, 51, 51));
        lblTitulos.setIcon(url);
        pnlIconos.add(lblTitulos);
    }

    public final void limpiarTabla(boolean editar) {
        Tabla1.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{},
                new String[]{
                    "ID", "Descripcion", "Codigo", "U.M.", "Cantidad", "Precio", "Total", "Proveedor", "T.E.", "No. Item", "Cantidad Enc.", "OC", "C.S."
                }
        ) {
            boolean[] canEdit = !editar ? new boolean[]{
                false, true, true, true, true, true, false, true, true, false, false, false, false
            } : new boolean[]{
                false, false, false, false, false, true, false, true, true, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });

        Tabla1.setCellSelectionEnabled(true);
        Tabla1.setRowSelectionAllowed(true);
        Tabla1.setColumnSelectionAllowed(true);
        Tabla1.getTableHeader().setBackground(new Color(51, 51, 255));
        Tabla1.getTableHeader().setFont(new Font("Roboto", Font.BOLD, 12));
        Tabla1.getTableHeader().setForeground(Color.white);

        if (Tabla1.getColumnModel().getColumnCount() > 0) {
            Tabla1.getColumnModel().getColumn(0).setResizable(false);
            Tabla1.getColumnModel().getColumn(0).setMinWidth(50);
            Tabla1.getColumnModel().getColumn(0).setPreferredWidth(50);
            Tabla1.getColumnModel().getColumn(0).setMaxWidth(50);
            Tabla1.getColumnModel().getColumn(1).setMinWidth(300);
            Tabla1.getColumnModel().getColumn(1).setPreferredWidth(300);
            Tabla1.getColumnModel().getColumn(1).setMaxWidth(800);
            Tabla1.getColumnModel().getColumn(10).setResizable(false);
        }
    }

    public final void autocompletar() {
        try {
            pilaProveedor = new Stack<>();
            Connection con = new Conexion().getConnection();
            Statement st2 = con.createStatement();
            String sql2 = "select * from registroprov_compras order by Nombre";
            ResultSet rs2 = st2.executeQuery(sql2);
            String proveedor;
            while (rs2.next()) {
                proveedor = rs2.getString("Nombre");
                pilaProveedor.add(proveedor);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ComprasNew.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public final void verDatos(String id, String estado) {
        try {
            this.estado = estado;
            Connection con = new Conexion().getConnection();
            Statement st = con.createStatement();
            Statement st2 = con.createStatement();
            String sql = "select id, descripcion, codigo, um, cantidad,"
                    + "precio, proveedor, te, cantidadStock, NumRequisicion, OC,"
                    + "cantidadStock, requisitor from requisiciones where NumRequisicion like '" + id + "' and activo like true";
            ResultSet rs = st.executeQuery(sql);
            limpiarTabla(lblCambio.isVisible());
            Object datos[] = new Object[14];
            DefaultTableModel miModelo = (DefaultTableModel) Tabla1.getModel();
            int cont = 0;
            lblId.setText(id);
            while (rs.next()) {
                datos[0] = rs.getString("id");
                datos[1] = rs.getString("descripcion");
                datos[2] = rs.getString("codigo");
                datos[3] = rs.getString("um");
                datos[4] = rs.getString("cantidad");
                datos[5] = rs.getString("precio");
                try {
                    datos[6] = Double.parseDouble(String.valueOf(datos[4])) * Double.parseDouble(String.valueOf(datos[5]));
                } catch (Exception e) {
                    datos[6] = "";
                }
                datos[7] = rs.getString("proveedor");
                datos[8] = rs.getString("te");
                datos[9] = cont;
                datos[10] = rs.getString("cantidadStock");
                datos[11] = rs.getString("OC");
                datos[12] = rs.getString("cantidadStock");
                miModelo.addRow(datos);
                num = rs.getString("requisitor");
                cont++;
            }
            JTextField jcb = new JTextField();
            autocompleter = new TextAutoCompleter(jcb);
            for (int i = 0; i < pilaProveedor.size(); i++) {
                autocompleter.addItem(pilaProveedor.get(i));
            }
            TableColumn tc = Tabla1.getColumnModel().getColumn(7);
            TableCellEditor tce = new DefaultCellEditor(jcb);
            tc.setCellEditor(tce);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public final void verificarRequisicion(String comprador, String requi, Connection con) throws SQLException {
        String sql2 = "select Id,Modificar from requisicion where Id like '" + requi + "'";
        Statement st2 = con.createStatement();
        ResultSet rs2 = st2.executeQuery(sql2);
        String a = null;
        while (rs2.next()) {
            a = rs2.getString("Modificar");
        }
        lblCambio.setText("Esta requisicion no se puede editar");
        if (a == null) {
            lblCambio.setVisible(false);
        } else {
            if (a.equals("0")) {
                lblCambio.setVisible(true);
            } else {
                lblCambio.setVisible(false);
            }
        }
        if (comprador != null && !numEmpleado.equals(comprador)) {
            lblCambio.setText("Esta requisicion pertenece a otro comprador");
            lblCambio.setVisible(true);
        }
    }

    public final void crearPanelRound(String id, String estado, String empleado, String fecha, String comprador, String ordenes, Connection con) {
        PanelRound pnlGeneral = new PanelRound();
        pnlGeneral.setBackground(new java.awt.Color(240, 240, 240));
        pnlGeneral.setComponentPopupMenu(popRequisiciones);
        pnlGeneral.setRoundBottomLeft(40);
        pnlGeneral.setRoundBottomRight(40);
        pnlGeneral.setRoundTopLeft(40);
        pnlGeneral.setRoundTopRight(40);
        pnlGeneral.setLayout(new java.awt.BorderLayout());
        pnlGeneral.setCursor(new Cursor(Cursor.HAND_CURSOR));
        ComprasNew compras = this;
        pnlGeneral.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                try {
                    for (Component comp : jPanel11.getComponents()) {
                        if (comp instanceof PanelRound) {
                            comp.setBackground(new Color(240, 240, 240));
                        }
                    }
                    compras.requisitor = empleado;
                    compras.comprador = comprador;
                    verificarRequisicion(comprador, id, con);
                    verDatos(id, estado);
                    pnlGeneral.setBackground(new Color(nuevo.getRed(), nuevo.getGreen(), nuevo.getBlue(), 50));
                } catch (SQLException ex) {
                    Logger.getLogger(ComprasNew.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        java.awt.GridBagConstraints gridBagConstraints;

        JPanel pnlSuperior = new JPanel();
        java.awt.GridBagLayout jPanel3Layout = new java.awt.GridBagLayout();
        jPanel3Layout.columnWeights = new double[]{1.0, 1.0};
        pnlSuperior.setLayout(jPanel3Layout);

        JLabel lblId = new JLabel();
        lblId.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 14)); // NOI18N
        lblId.setForeground(new java.awt.Color(51, 51, 51));
        lblId.setText(id);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.ipadx = 7;
        gridBagConstraints.ipady = 7;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 0, 0);
        pnlSuperior.add(lblId, gridBagConstraints);

        JLabel lblEmpleado = new javax.swing.JLabel();
        lblEmpleado.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 12)); // NOI18N
        lblEmpleado.setForeground(new java.awt.Color(51, 51, 51));
        lblEmpleado.setText(comprador);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 7;
        gridBagConstraints.ipady = 7;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 0, 0);
        pnlSuperior.add(lblEmpleado, gridBagConstraints);

        PanelRound pnlEstado = new PanelRound();
        pnlEstado.setBackground(new java.awt.Color(0, 153, 0));
        pnlEstado.setRoundBottomLeft(15);
        pnlEstado.setRoundBottomRight(15);
        pnlEstado.setRoundTopLeft(15);
        pnlEstado.setRoundTopRight(15);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 10);

        JLabel lblEstado = new JLabel();
        lblEstado.setFont(new java.awt.Font("Trebuchet MS", 1, 12));
        Color col = extraerEstado(estado.toUpperCase());
        lblEstado.setForeground(col);//color de la letra
        lblEstado.setText(estado);
        pnlEstado.add(lblEstado);
        pnlEstado.setBackground(new Color(col.getRed(), col.getGreen(), col.getBlue(), 50));//color de fondo
        pnlSuperior.add(pnlEstado, gridBagConstraints);

        JPanel pnlInf = new JPanel();
        pnlInf.setBackground(new Color(1, 1, 1, 1));
        pnlInf.setLayout(new java.awt.GridBagLayout());
        pnlGeneral.add(pnlInf, java.awt.BorderLayout.CENTER);

        //-------------------------------------------------------------
        crearLabelAbajo(pnlInf, empleado, iconEmpleado);
        ordenes = ordenes == null ? "" : (ordenes.contains(",") ? "Varios" : ordenes);
        crearLabelAbajo(pnlInf, ordenes, iconPO);
        crearLabelAbajo(pnlInf, fecha, iconFecha);

        //-----------------------------------------------------
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 10;
        gridBagConstraints.ipady = 30;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 20, 5, 20);

        pnlSuperior.setBackground(new Color(1, 1, 1, 1));
        pnlGeneral.add(pnlSuperior, java.awt.BorderLayout.PAGE_START);
        jPanel11.add(pnlGeneral, gridBagConstraints);
    }

    public Color extraerEstado(String estado) {
        switch (estado) {
            case "LLEGO, COMPLETO":
                return completado;
            case "LLEGO, INCOMPETO":
                return pendiente;
            case "NUEVO":
                return nuevo;
            case "COTIZANDO":
                return completado;
            case "COTIZADO":
                return pendiente;
            case "APROBACION":
                return nuevo;
            case "CANCELADO/COSTOS":
                return inactivo;
            case "COMPRADO":
                return completado;
            case "APROBADO":
                return nuevo;
            default:
                return inactivo;
        }
    }

    public final void verRequisiciones(String peticion) {
        try {
            limpiarTabla(false);
            lblCambio.setVisible(false);
            lblId.setText("");
            Connection con = new Conexion().getConnection();
            Statement st = con.createStatement();
            Statement st2 = con.createStatement();
            String sql = "SELECT r.Progreso, r.NumeroEmpleado, r.Id, r.Fecha, r.Comprador, "
                    + "GROUP_CONCAT(o.OrdenNo ORDER BY o.OrdenNo SEPARATOR ', ') AS Ordenes "
                    + "FROM Requisicion r "
                    + "LEFT JOIN ordencompra o ON r.Id = o.RequisicionNo "
                    + "WHERE r.Completado = 'NO' AND r.Progreso NOT IN ('RECIBIDO','EVALUACION','RECHAZADO','COTIZADO') "
                    + "GROUP BY r.Progreso, r.NumeroEmpleado, r.Id, r.Fecha, r.Comprador ORDER BY r.Id DESC;";
            if (peticion != null) {
                sql = peticion;
            }
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                String id = rs.getString("id");
                String estado = rs.getString("progreso").toLowerCase();
                String empleado = rs.getString("numeroempleado");
                String fecha = rs.getString("fecha");
                String comprador = rs.getString("Comprador");
                String ordenes = rs.getString("ordenes");
                crearPanelRound(id, estado, empleado, fecha, comprador, ordenes, con);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al ver requisiciones: " + e, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public final void limpiarPanel() {
        jPanel11.removeAll();
        jPanel11.revalidate();
        jPanel11.repaint();
        panelRound1.setVisible(false);
    }

    public boolean verificarPrecios() {
        boolean band = true;
        for (int i = 0; i < Tabla1.getRowCount(); i++) {
            try {
                if (Tabla1.getValueAt(i, 5) != null) {
                    if (!Tabla1.getValueAt(i, 5).toString().equals("")) {
                        String precio = Tabla1.getValueAt(i, 5).toString();
                        double pre = Double.parseDouble(precio);
                    }
                }
                if (Tabla1.getValueAt(i, 4) != null) {
                    if (!Tabla1.getValueAt(i, 4).toString().equals("")) {
                        String precio = Tabla1.getValueAt(i, 4).toString();
                        double pre = Double.parseDouble(precio);
                    }
                }
            } catch (NumberFormatException e) {
                band = false;
            }
        }
        return band;
    }

    public boolean verificarProveedor() {
        boolean band = true;
        try {
            Connection con;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            String sql = "select * from registroprov_compras";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            Stack<String> pilaProv = new Stack<>();
            while (rs.next()) {
                pilaProv.push(rs.getString("Nombre"));
            }
            for (int i = 0; i < Tabla1.getRowCount(); i++) {
                if (Tabla1.getValueAt(i, 7) != null) {
                    if (Tabla1.getValueAt(i, 7).toString().equals("")) {
                        String prov = Tabla1.getValueAt(i, 7).toString();
                        if (pilaProv.search(prov) < 0) {
                            band = false;
                        }
                    }
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "ERROR: " + e, "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        return band;
    }

    public final void getCorreo(String numEmpleado) {
        try {
            Connection con;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            String sql = "select AES_DECRYPT(pass,'mi_llave'),correo,NumEmpleado from registroempleados where NumEmpleado like '" + numEmpleado + "'";
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                pass = rs.getString("AES_DECRYPT(Pass,'mi_llave')");
                correo = rs.getString("Correo");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "ERROR: " + e, "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    public final void enviarCotizacion() {
        if (pass == null) {
            JOptionPane.showMessageDialog(this, "Debes cambiar la configuracion de tu correo", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            JFrame f = (JFrame) JOptionPane.getFrameForComponent(this);
            enviarCorreo enviar = new enviarCorreo(f, true);
            enviar.modalidad = true;
            Stack<String> pila = new Stack<>();
            Stack<String> pilaProv = new Stack<>();
            Stack<String> id = new Stack<>();
            for (int i = 0; i < Tabla1.getRowCount(); i++) {
                String prov = Tabla1.getValueAt(i, 7) == null ? "" : Tabla1.getValueAt(i, 7).toString();
                pila.push(Tabla1.getValueAt(i, 2).toString()); //numero de parte
                pilaProv.push(prov); //proveedor
                pilaProv.push(prov); //proveedor
                id.push(Tabla1.getValueAt(i, 0).toString()); //id
            }
            enviar.llenarBotones(pila, pilaProv, id);
            enviar.tabla = Tabla1;
            enviar.proyecto = Tabla1.getValueAt(0, 0).toString(); //proyecto
            enviar.numRequi = lblId.getText();
            enviar.correo = correo;
            enviar.contra = pass;
            enviar.BD = false;
            enviar.transaccion = "cotizacion";
            enviar.setVisible(true);
            int n = 1;

            if (n > 0) {

                try {
                    Connection con;
                    Conexion con1 = new Conexion();
                    con = con1.getConnection();

                    if (this.estado.equals("nuevo") || this.estado.equals("nuevo")) {
                        String sql = "update requisicion set Progreso = ?, Comprador = ? where Id = ?";
                        PreparedStatement pst = con.prepareStatement(sql);

                        pst.setString(1, "COTIZANDO");
                        pst.setString(2, numEmpleado);
                        pst.setString(3, lblId.getText());

                        pst.executeUpdate();
                    } else {

                    }

                    limpiarPanel();
                    verRequisiciones(null);
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(this, "ERROR: " + e, "ERROR", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    public final void verNombreEmpleado(String numEmpleado) {
        Thread hilo = new Thread() {
            @Override
            public void run() {
                try {
                    Connection con = new Conexion().getConnection();
                    Statement st = con.createStatement();
                    String sql = "select * from registroempleados where numEmpleado like '" + numEmpleado + "'";
                    ResultSet rs = st.executeQuery(sql);
                    String nombre = "";
                    while (rs.next()) {
                        nombre = rs.getString("Nombre") + " " + rs.getString("Apellido");
                    }
                    lblEmpleado.setVisible(true);
                    lblEmpleado.setText(nombre);
                    Thread.sleep(3000);
                    lblEmpleado.setText("");
                    lblEmpleado.setVisible(false);
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null, "Error: " + e, "Error", JOptionPane.ERROR_MESSAGE);
                } catch (InterruptedException ex) {
                    Logger.getLogger(ComprasNew.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };
        hilo.start();
    }

    public String convertirNull(int i, int col) {
        if (Tabla1.getValueAt(i, col) == null) {
            return "";
        } else {
            return Tabla1.getValueAt(i, col).toString();
        }
    }

    public final void extraerInicial(String empleado) {
        try {
            Connection con;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            String sql = "select * from registroempleados where NumEmpleado like '" + numEmpleado + "'";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                inicial = rs.getString("Nombre").substring(0, 1).toUpperCase();
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error: " + e, "error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public ComprasNew(String numEmpleado) {
        try {
            UIManager.setLookAndFeel(new FlatMacLightLaf());
        } catch (UnsupportedLookAndFeelException e) {
        }
        initComponents();
        this.numEmpleado = numEmpleado;
        this.setExtendedState(ComprasNew.MAXIMIZED_BOTH);
        panelRound1.setVisible(false);
        panelRound11.setVisible(false);
        panelRound4.setVisible(false);
        panelRound16.setVisible(false);
        jScrollPane1.getVerticalScrollBar().setUnitIncrement(15);
        revalidate();
        repaint();
        limpiarTabla(false);
        verRequisiciones(null);
        getCorreo(numEmpleado);
        lblEmpleado.setVisible(false);
        lblCambio.setVisible(false);
        extraerInicial(numEmpleado);
        autocompletar();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        popRequisiciones = new javax.swing.JPopupMenu();
        btnCotizacion = new javax.swing.JMenuItem();
        Aprobacion = new javax.swing.JMenuItem();
        jSeparator9 = new javax.swing.JPopupMenu.Separator();
        btnOrden = new javax.swing.JMenuItem();
        jSeparator10 = new javax.swing.JPopupMenu.Separator();
        btnCancelarRequi = new javax.swing.JMenuItem();
        jSeparator11 = new javax.swing.JPopupMenu.Separator();
        btnTransferir = new javax.swing.JMenuItem();
        jSeparator12 = new javax.swing.JPopupMenu.Separator();
        btnVerEmpleado = new javax.swing.JMenuItem();
        btnVerComprador = new javax.swing.JMenuItem();
        popTabla = new javax.swing.JPopupMenu();
        btnVerOrden = new javax.swing.JMenuItem();
        btnEditar = new javax.swing.JMenuItem();
        jSeparator13 = new javax.swing.JPopupMenu.Separator();
        jMenuItem1 = new javax.swing.JMenuItem();
        jSeparator14 = new javax.swing.JPopupMenu.Separator();
        btnCancelarOrden = new javax.swing.JMenuItem();
        btnEliminar = new javax.swing.JMenuItem();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel11 = new javax.swing.JPanel();
        panelRound1 = new scrollPane.PanelRound();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        panelRound2 = new scrollPane.PanelRound();
        jLabel2 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        panelRound3 = new scrollPane.PanelRound();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        panelRound6 = new scrollPane.PanelRound();
        jLabel5 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        panelRound7 = new scrollPane.PanelRound();
        jLabel6 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        panelRound11 = new scrollPane.PanelRound();
        jPanel7 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        panelRound12 = new scrollPane.PanelRound();
        jLabel18 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        panelRound13 = new scrollPane.PanelRound();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        panelRound14 = new scrollPane.PanelRound();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        panelRound15 = new scrollPane.PanelRound();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        panelRound4 = new scrollPane.PanelRound();
        jPanel5 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        panelRound5 = new scrollPane.PanelRound();
        jLabel10 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        panelRound8 = new scrollPane.PanelRound();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        panelRound9 = new scrollPane.PanelRound();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        panelRound10 = new scrollPane.PanelRound();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        panelRound16 = new scrollPane.PanelRound();
        jPanel9 = new javax.swing.JPanel();
        jLabel25 = new javax.swing.JLabel();
        panelRound17 = new scrollPane.PanelRound();
        jLabel26 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        panelRound18 = new scrollPane.PanelRound();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        panelRound19 = new scrollPane.PanelRound();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        panelRound20 = new scrollPane.PanelRound();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        panelRound25 = new scrollPane.PanelRound();
        jLabel38 = new javax.swing.JLabel();
        panelRound21 = new scrollPane.PanelRound();
        jLabel33 = new javax.swing.JLabel();
        panelRound22 = new scrollPane.PanelRound();
        jLabel34 = new javax.swing.JLabel();
        panelRound23 = new scrollPane.PanelRound();
        jLabel35 = new javax.swing.JLabel();
        panelRound24 = new scrollPane.PanelRound();
        jLabel36 = new javax.swing.JLabel();
        jPanel13 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        Tabla1 = new javax.swing.JTable();
        jPanel14 = new javax.swing.JPanel();
        lblId = new javax.swing.JLabel();
        lblCambio = new javax.swing.JLabel();
        lblEmpleado = new javax.swing.JLabel();
        jPanel15 = new javax.swing.JPanel();
        btnGuardar = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem8 = new javax.swing.JMenuItem();
        jSeparator6 = new javax.swing.JPopupMenu.Separator();
        CancelarRequisicion = new javax.swing.JMenuItem();
        jSeparator3 = new javax.swing.JPopupMenu.Separator();
        jSeparator7 = new javax.swing.JPopupMenu.Separator();
        comprar = new javax.swing.JMenuItem();
        jSeparator4 = new javax.swing.JPopupMenu.Separator();
        jMenuItem13 = new javax.swing.JMenuItem();
        jSeparator8 = new javax.swing.JPopupMenu.Separator();
        buscar = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        jMenuItem9 = new javax.swing.JMenuItem();
        jMenuItem15 = new javax.swing.JMenuItem();
        jMenu4 = new javax.swing.JMenu();
        jMenuItem12 = new javax.swing.JMenuItem();
        jMenuItem14 = new javax.swing.JMenuItem();

        popRequisiciones.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
                popRequisicionesPopupMenuWillBecomeVisible(evt);
            }
        });

        btnCotizacion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/correo-electronico.png"))); // NOI18N
        btnCotizacion.setText("Enviar cotizacion");
        btnCotizacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCotizacionActionPerformed(evt);
            }
        });
        popRequisiciones.add(btnCotizacion);

        Aprobacion.setText("Enviar a aprobacion");
        Aprobacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AprobacionActionPerformed(evt);
            }
        });
        popRequisiciones.add(Aprobacion);
        popRequisiciones.add(jSeparator9);

        btnOrden.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/pdf.png"))); // NOI18N
        btnOrden.setText("Crear orden de compra                                                    ");
        btnOrden.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOrdenActionPerformed(evt);
            }
        });
        popRequisiciones.add(btnOrden);
        popRequisiciones.add(jSeparator10);

        btnCancelarRequi.setText("Cancelar requisicion");
        btnCancelarRequi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarRequiActionPerformed(evt);
            }
        });
        popRequisiciones.add(btnCancelarRequi);
        popRequisiciones.add(jSeparator11);

        btnTransferir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/editarEmpleado_16.png"))); // NOI18N
        btnTransferir.setText("Transferir requisicion");
        btnTransferir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTransferirActionPerformed(evt);
            }
        });
        popRequisiciones.add(btnTransferir);
        popRequisiciones.add(jSeparator12);

        btnVerEmpleado.setText("Ver nombre de empleado");
        btnVerEmpleado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVerEmpleadoActionPerformed(evt);
            }
        });
        popRequisiciones.add(btnVerEmpleado);

        btnVerComprador.setText("Ver datos de comprador");
        btnVerComprador.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVerCompradorActionPerformed(evt);
            }
        });
        popRequisiciones.add(btnVerComprador);

        popTabla.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
                popTablaPopupMenuWillBecomeVisible(evt);
            }
        });

        btnVerOrden.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/pdf.png"))); // NOI18N
        btnVerOrden.setText("Ver orden de compra");
        btnVerOrden.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVerOrdenActionPerformed(evt);
            }
        });
        popTabla.add(btnVerOrden);

        btnEditar.setText("Editar orden de compra");
        popTabla.add(btnEditar);
        popTabla.add(jSeparator13);

        jMenuItem1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/Agregar_16.png"))); // NOI18N
        jMenuItem1.setText("Agregar registro");
        popTabla.add(jMenuItem1);
        popTabla.add(jSeparator14);

        btnCancelarOrden.setText("Cancelar orden de compra");
        btnCancelarOrden.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarOrdenActionPerformed(evt);
            }
        });
        popTabla.add(btnCancelarOrden);

        btnEliminar.setText("Eliminar fila seleccionada                                 ");
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });
        popTabla.add(btnEliminar);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new java.awt.BorderLayout());

        jPanel2.setLayout(new java.awt.BorderLayout());

        jScrollPane1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        jPanel11.setBackground(new java.awt.Color(255, 255, 255));
        java.awt.GridBagLayout jPanel11Layout = new java.awt.GridBagLayout();
        jPanel11Layout.columnWeights = new double[] {1.0};
        jPanel11.setLayout(jPanel11Layout);

        panelRound1.setBackground(new java.awt.Color(51, 51, 51));
        panelRound1.setRoundBottomLeft(40);
        panelRound1.setRoundBottomRight(40);
        panelRound1.setRoundTopLeft(40);
        panelRound1.setRoundTopRight(40);
        panelRound1.setLayout(new java.awt.BorderLayout());

        java.awt.GridBagLayout jPanel3Layout = new java.awt.GridBagLayout();
        jPanel3Layout.columnWeights = new double[] {1.0, 1.0};
        jPanel3.setLayout(jPanel3Layout);

        jLabel1.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("1922");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.ipadx = 7;
        gridBagConstraints.ipady = 7;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 0, 0);
        jPanel3.add(jLabel1, gridBagConstraints);

        panelRound2.setBackground(new java.awt.Color(0, 153, 0));
        panelRound2.setRoundBottomLeft(15);
        panelRound2.setRoundBottomRight(15);
        panelRound2.setRoundTopLeft(15);
        panelRound2.setRoundTopRight(15);

        jLabel2.setFont(new java.awt.Font("Trebuchet MS", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 153, 0));
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/circulo verde.png"))); // NOI18N
        jLabel2.setText("Activo");
        panelRound2.add(jLabel2);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 10);
        jPanel3.add(panelRound2, gridBagConstraints);

        jLabel37.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 12)); // NOI18N
        jLabel37.setForeground(new java.awt.Color(255, 255, 255));
        jLabel37.setText("61");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 7;
        gridBagConstraints.ipady = 7;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 0, 0);
        jPanel3.add(jLabel37, gridBagConstraints);

        panelRound1.add(jPanel3, java.awt.BorderLayout.PAGE_START);

        jPanel4.setPreferredSize(new java.awt.Dimension(199, 20));

        panelRound3.setBackground(new java.awt.Color(35, 35, 35));
        panelRound3.setRoundBottomLeft(20);
        panelRound3.setRoundBottomRight(20);
        panelRound3.setRoundTopLeft(20);
        panelRound3.setRoundTopRight(20);

        jLabel3.setFont(new java.awt.Font("Trebuchet MS", 1, 10)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(204, 204, 204));
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/empleado.png"))); // NOI18N
        panelRound3.add(jLabel3);

        jLabel4.setFont(new java.awt.Font("Trebuchet MS", 1, 10)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(204, 204, 204));
        jLabel4.setText("61");
        panelRound3.add(jLabel4);

        jPanel4.add(panelRound3);

        panelRound6.setBackground(new java.awt.Color(35, 35, 35));
        panelRound6.setRoundBottomLeft(20);
        panelRound6.setRoundBottomRight(20);
        panelRound6.setRoundTopLeft(20);
        panelRound6.setRoundTopRight(20);

        jLabel5.setFont(new java.awt.Font("Trebuchet MS", 1, 10)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(204, 204, 204));
        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/po1.png"))); // NOI18N
        panelRound6.add(jLabel5);

        jLabel8.setFont(new java.awt.Font("Trebuchet MS", 1, 10)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(204, 204, 204));
        jLabel8.setText("SO");
        panelRound6.add(jLabel8);

        jPanel4.add(panelRound6);

        panelRound7.setBackground(new java.awt.Color(35, 35, 35));
        panelRound7.setRoundBottomLeft(20);
        panelRound7.setRoundBottomRight(20);
        panelRound7.setRoundTopLeft(20);
        panelRound7.setRoundTopRight(20);

        jLabel6.setFont(new java.awt.Font("Trebuchet MS", 1, 10)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(204, 204, 204));
        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/fecha.png"))); // NOI18N
        panelRound7.add(jLabel6);

        jLabel9.setFont(new java.awt.Font("Trebuchet MS", 1, 10)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(204, 204, 204));
        jLabel9.setText("2026-05-26");
        panelRound7.add(jLabel9);

        jPanel4.add(panelRound7);

        panelRound1.add(jPanel4, java.awt.BorderLayout.CENTER);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 10;
        gridBagConstraints.ipady = 30;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 20, 5, 20);
        jPanel11.add(panelRound1, gridBagConstraints);

        panelRound11.setBackground(new java.awt.Color(51, 51, 51));
        panelRound11.setRoundBottomLeft(40);
        panelRound11.setRoundBottomRight(40);
        panelRound11.setRoundTopLeft(40);
        panelRound11.setRoundTopRight(40);
        panelRound11.setLayout(new java.awt.BorderLayout());

        java.awt.GridBagLayout jPanel7Layout = new java.awt.GridBagLayout();
        jPanel7Layout.columnWeights = new double[] {1.0, 1.0};
        jPanel7.setLayout(jPanel7Layout);

        jLabel17.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 14)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setText("1922");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.ipadx = 7;
        gridBagConstraints.ipady = 7;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 0, 0);
        jPanel7.add(jLabel17, gridBagConstraints);

        panelRound12.setBackground(new java.awt.Color(51, 51, 255));
        panelRound12.setRoundBottomLeft(15);
        panelRound12.setRoundBottomRight(15);
        panelRound12.setRoundTopLeft(15);
        panelRound12.setRoundTopRight(15);

        jLabel18.setBackground(new java.awt.Color(51, 51, 255));
        jLabel18.setFont(new java.awt.Font("Trebuchet MS", 1, 12)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(51, 51, 255));
        jLabel18.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/circulo azul.png"))); // NOI18N
        jLabel18.setText("Completado");
        panelRound12.add(jLabel18);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 10);
        jPanel7.add(panelRound12, gridBagConstraints);

        panelRound11.add(jPanel7, java.awt.BorderLayout.PAGE_START);

        jPanel8.setPreferredSize(new java.awt.Dimension(240, 20));

        panelRound13.setBackground(new java.awt.Color(35, 35, 35));
        panelRound13.setRoundBottomLeft(20);
        panelRound13.setRoundBottomRight(20);
        panelRound13.setRoundTopLeft(20);
        panelRound13.setRoundTopRight(20);

        jLabel19.setFont(new java.awt.Font("Trebuchet MS", 1, 10)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(204, 204, 204));
        jLabel19.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/empleado.png"))); // NOI18N
        panelRound13.add(jLabel19);

        jLabel20.setFont(new java.awt.Font("Trebuchet MS", 1, 10)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(204, 204, 204));
        jLabel20.setText("61");
        jLabel20.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        panelRound13.add(jLabel20);

        jPanel8.add(panelRound13);

        panelRound14.setBackground(new java.awt.Color(35, 35, 35));
        panelRound14.setRoundBottomLeft(20);
        panelRound14.setRoundBottomRight(20);
        panelRound14.setRoundTopLeft(20);
        panelRound14.setRoundTopRight(20);

        jLabel21.setFont(new java.awt.Font("Trebuchet MS", 1, 10)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(204, 204, 204));
        jLabel21.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/po1.png"))); // NOI18N
        panelRound14.add(jLabel21);

        jLabel22.setFont(new java.awt.Font("Trebuchet MS", 1, 10)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(204, 204, 204));
        jLabel22.setText("OCM65552");
        panelRound14.add(jLabel22);

        jPanel8.add(panelRound14);

        panelRound15.setBackground(new java.awt.Color(35, 35, 35));
        panelRound15.setRoundBottomLeft(20);
        panelRound15.setRoundBottomRight(20);
        panelRound15.setRoundTopLeft(20);
        panelRound15.setRoundTopRight(20);

        jLabel23.setFont(new java.awt.Font("Trebuchet MS", 1, 10)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(204, 204, 204));
        jLabel23.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/fecha.png"))); // NOI18N
        panelRound15.add(jLabel23);

        jLabel24.setFont(new java.awt.Font("Trebuchet MS", 1, 10)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(204, 204, 204));
        jLabel24.setText("2026-05-26");
        panelRound15.add(jLabel24);

        jPanel8.add(panelRound15);

        panelRound11.add(jPanel8, java.awt.BorderLayout.CENTER);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 10;
        gridBagConstraints.ipady = 30;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 20, 5, 20);
        jPanel11.add(panelRound11, gridBagConstraints);

        panelRound4.setBackground(new java.awt.Color(51, 51, 51));
        panelRound4.setRoundBottomLeft(40);
        panelRound4.setRoundBottomRight(40);
        panelRound4.setRoundTopLeft(40);
        panelRound4.setRoundTopRight(40);
        panelRound4.setLayout(new java.awt.BorderLayout());

        java.awt.GridBagLayout jPanel5Layout = new java.awt.GridBagLayout();
        jPanel5Layout.columnWeights = new double[] {1.0, 1.0};
        jPanel5.setLayout(jPanel5Layout);

        jLabel7.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("1922");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.ipadx = 7;
        gridBagConstraints.ipady = 7;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 0, 0);
        jPanel5.add(jLabel7, gridBagConstraints);

        panelRound5.setBackground(new java.awt.Color(255, 209, 0));
        panelRound5.setRoundBottomLeft(15);
        panelRound5.setRoundBottomRight(15);
        panelRound5.setRoundTopLeft(15);
        panelRound5.setRoundTopRight(15);

        jLabel10.setFont(new java.awt.Font("Trebuchet MS", 1, 12)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 209, 0));
        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/circulo amarillo.png"))); // NOI18N
        jLabel10.setText("Pendiente");
        panelRound5.add(jLabel10);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 10);
        jPanel5.add(panelRound5, gridBagConstraints);

        panelRound4.add(jPanel5, java.awt.BorderLayout.PAGE_START);

        panelRound8.setBackground(new java.awt.Color(35, 35, 35));
        panelRound8.setRoundBottomLeft(20);
        panelRound8.setRoundBottomRight(20);
        panelRound8.setRoundTopLeft(20);
        panelRound8.setRoundTopRight(20);

        jLabel11.setFont(new java.awt.Font("Trebuchet MS", 1, 10)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(204, 204, 204));
        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/empleado.png"))); // NOI18N
        panelRound8.add(jLabel11);

        jLabel12.setFont(new java.awt.Font("Trebuchet MS", 1, 10)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(204, 204, 204));
        jLabel12.setText("205");
        panelRound8.add(jLabel12);

        jPanel6.add(panelRound8);

        panelRound9.setBackground(new java.awt.Color(35, 35, 35));
        panelRound9.setRoundBottomLeft(20);
        panelRound9.setRoundBottomRight(20);
        panelRound9.setRoundTopLeft(20);
        panelRound9.setRoundTopRight(20);

        jLabel13.setFont(new java.awt.Font("Trebuchet MS", 1, 10)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(204, 204, 204));
        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/po1.png"))); // NOI18N
        panelRound9.add(jLabel13);

        jLabel14.setFont(new java.awt.Font("Trebuchet MS", 1, 10)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(204, 204, 204));
        jLabel14.setText("Multiples");
        panelRound9.add(jLabel14);

        jPanel6.add(panelRound9);

        panelRound10.setBackground(new java.awt.Color(35, 35, 35));
        panelRound10.setRoundBottomLeft(20);
        panelRound10.setRoundBottomRight(20);
        panelRound10.setRoundTopLeft(20);
        panelRound10.setRoundTopRight(20);

        jLabel15.setFont(new java.awt.Font("Trebuchet MS", 1, 10)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(204, 204, 204));
        jLabel15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/fecha.png"))); // NOI18N
        panelRound10.add(jLabel15);

        jLabel16.setFont(new java.awt.Font("Trebuchet MS", 1, 10)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(204, 204, 204));
        jLabel16.setText("2026-05-26");
        panelRound10.add(jLabel16);

        jPanel6.add(panelRound10);

        panelRound4.add(jPanel6, java.awt.BorderLayout.CENTER);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 10;
        gridBagConstraints.ipady = 30;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 20, 5, 20);
        jPanel11.add(panelRound4, gridBagConstraints);

        panelRound16.setBackground(new java.awt.Color(51, 51, 51));
        panelRound16.setRoundBottomLeft(40);
        panelRound16.setRoundBottomRight(40);
        panelRound16.setRoundTopLeft(40);
        panelRound16.setRoundTopRight(40);
        panelRound16.setLayout(new java.awt.BorderLayout());

        java.awt.GridBagLayout jPanel9Layout = new java.awt.GridBagLayout();
        jPanel9Layout.columnWeights = new double[] {1.0, 1.0};
        jPanel9.setLayout(jPanel9Layout);

        jLabel25.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 14)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(255, 255, 255));
        jLabel25.setText("1922");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.ipadx = 7;
        gridBagConstraints.ipady = 7;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 0, 0);
        jPanel9.add(jLabel25, gridBagConstraints);

        panelRound17.setBackground(new java.awt.Color(204, 0, 0));
        panelRound17.setRoundBottomLeft(15);
        panelRound17.setRoundBottomRight(15);
        panelRound17.setRoundTopLeft(15);
        panelRound17.setRoundTopRight(15);

        jLabel26.setBackground(new java.awt.Color(204, 0, 0));
        jLabel26.setFont(new java.awt.Font("Trebuchet MS", 1, 12)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(204, 0, 0));
        jLabel26.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/circulo rojo.png"))); // NOI18N
        jLabel26.setText("Inactivo");
        panelRound17.add(jLabel26);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 10);
        jPanel9.add(panelRound17, gridBagConstraints);

        panelRound16.add(jPanel9, java.awt.BorderLayout.PAGE_START);

        panelRound18.setBackground(new java.awt.Color(35, 35, 35));
        panelRound18.setRoundBottomLeft(20);
        panelRound18.setRoundBottomRight(20);
        panelRound18.setRoundTopLeft(20);
        panelRound18.setRoundTopRight(20);

        jLabel27.setFont(new java.awt.Font("Trebuchet MS", 1, 10)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(204, 204, 204));
        jLabel27.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/empleado.png"))); // NOI18N
        panelRound18.add(jLabel27);

        jLabel28.setFont(new java.awt.Font("Trebuchet MS", 1, 10)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(204, 204, 204));
        jLabel28.setText("61");
        panelRound18.add(jLabel28);

        jPanel10.add(panelRound18);

        panelRound19.setBackground(new java.awt.Color(35, 35, 35));
        panelRound19.setRoundBottomLeft(20);
        panelRound19.setRoundBottomRight(20);
        panelRound19.setRoundTopLeft(20);
        panelRound19.setRoundTopRight(20);

        jLabel29.setFont(new java.awt.Font("Trebuchet MS", 1, 10)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(204, 204, 204));
        jLabel29.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/po1.png"))); // NOI18N
        panelRound19.add(jLabel29);

        jLabel30.setFont(new java.awt.Font("Trebuchet MS", 1, 10)); // NOI18N
        jLabel30.setForeground(new java.awt.Color(204, 204, 204));
        jLabel30.setText("OCM65552");
        panelRound19.add(jLabel30);

        jPanel10.add(panelRound19);

        panelRound20.setBackground(new java.awt.Color(35, 35, 35));
        panelRound20.setRoundBottomLeft(20);
        panelRound20.setRoundBottomRight(20);
        panelRound20.setRoundTopLeft(20);
        panelRound20.setRoundTopRight(20);

        jLabel31.setFont(new java.awt.Font("Trebuchet MS", 1, 10)); // NOI18N
        jLabel31.setForeground(new java.awt.Color(204, 204, 204));
        jLabel31.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/fecha.png"))); // NOI18N
        panelRound20.add(jLabel31);

        jLabel32.setFont(new java.awt.Font("Trebuchet MS", 1, 10)); // NOI18N
        jLabel32.setForeground(new java.awt.Color(204, 204, 204));
        jLabel32.setText("2026-05-26");
        panelRound20.add(jLabel32);

        jPanel10.add(panelRound20);

        panelRound16.add(jPanel10, java.awt.BorderLayout.CENTER);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 10;
        gridBagConstraints.ipady = 30;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 20, 5, 20);
        jPanel11.add(panelRound16, gridBagConstraints);

        jScrollPane1.setViewportView(jPanel11);

        jPanel2.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jPanel12.setBackground(new java.awt.Color(255, 255, 255));

        panelRound25.setBackground(new Color(153,153,153,50));
        panelRound25.setRoundBottomLeft(15);
        panelRound25.setRoundBottomRight(15);
        panelRound25.setRoundTopLeft(15);
        panelRound25.setRoundTopRight(15);

        jLabel38.setFont(new java.awt.Font("Trebuchet MS", 1, 12)); // NOI18N
        jLabel38.setForeground(new java.awt.Color(0, 153, 0));
        jLabel38.setText("    ");
        jLabel38.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel38.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel38MouseClicked(evt);
            }
        });
        panelRound25.add(jLabel38);

        jPanel12.add(panelRound25);

        panelRound21.setBackground(new Color(0,153,0,50));
        panelRound21.setRoundBottomLeft(15);
        panelRound21.setRoundBottomRight(15);
        panelRound21.setRoundTopLeft(15);
        panelRound21.setRoundTopRight(15);

        jLabel33.setFont(new java.awt.Font("Trebuchet MS", 1, 12)); // NOI18N
        jLabel33.setForeground(new java.awt.Color(0, 153, 0));
        jLabel33.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/circulo verde.png"))); // NOI18N
        jLabel33.setText(" ");
        jLabel33.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel33.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel33MouseClicked(evt);
            }
        });
        panelRound21.add(jLabel33);

        jPanel12.add(panelRound21);

        panelRound22.setBackground(new Color(51,51,255,50));
        panelRound22.setRoundBottomLeft(15);
        panelRound22.setRoundBottomRight(15);
        panelRound22.setRoundTopLeft(15);
        panelRound22.setRoundTopRight(15);

        jLabel34.setBackground(new java.awt.Color(51, 51, 255));
        jLabel34.setFont(new java.awt.Font("Trebuchet MS", 1, 12)); // NOI18N
        jLabel34.setForeground(new java.awt.Color(51, 51, 255));
        jLabel34.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/circulo azul.png"))); // NOI18N
        jLabel34.setText(" ");
        jLabel34.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel34.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel34MouseClicked(evt);
            }
        });
        panelRound22.add(jLabel34);

        jPanel12.add(panelRound22);

        panelRound23.setBackground(new Color(255,209,0,50));
        panelRound23.setRoundBottomLeft(15);
        panelRound23.setRoundBottomRight(15);
        panelRound23.setRoundTopLeft(15);
        panelRound23.setRoundTopRight(15);

        jLabel35.setFont(new java.awt.Font("Trebuchet MS", 1, 12)); // NOI18N
        jLabel35.setForeground(new java.awt.Color(255, 209, 0));
        jLabel35.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/circulo amarillo.png"))); // NOI18N
        jLabel35.setText(" ");
        jLabel35.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel35.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel35MouseClicked(evt);
            }
        });
        panelRound23.add(jLabel35);

        jPanel12.add(panelRound23);

        panelRound24.setBackground(new Color(204,0,0,50));
        panelRound24.setRoundBottomLeft(15);
        panelRound24.setRoundBottomRight(15);
        panelRound24.setRoundTopLeft(15);
        panelRound24.setRoundTopRight(15);

        jLabel36.setBackground(new java.awt.Color(204, 0, 0));
        jLabel36.setFont(new java.awt.Font("Trebuchet MS", 1, 12)); // NOI18N
        jLabel36.setForeground(new java.awt.Color(204, 0, 0));
        jLabel36.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/circulo rojo.png"))); // NOI18N
        jLabel36.setText(" ");
        jLabel36.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel36.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel36MouseClicked(evt);
            }
        });
        panelRound24.add(jLabel36);

        jPanel12.add(panelRound24);

        jPanel2.add(jPanel12, java.awt.BorderLayout.NORTH);

        jPanel1.add(jPanel2, java.awt.BorderLayout.WEST);

        jPanel13.setLayout(new java.awt.BorderLayout());

        jScrollPane2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));

        Tabla1.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        Tabla1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Descripcion", "Codigo", "U.M.", "Cantidad", "Precio", "Total", "Proveedor", "T.E.", "No. Item", "Cantidad Enc."
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, true, true, true, true, true, true, true, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        Tabla1.setComponentPopupMenu(popTabla);
        Tabla1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        Tabla1.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                Tabla1MouseDragged(evt);
            }
        });
        Tabla1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Tabla1MouseClicked(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                Tabla1MouseReleased(evt);
            }
        });
        jScrollPane2.setViewportView(Tabla1);
        if (Tabla1.getColumnModel().getColumnCount() > 0) {
            Tabla1.getColumnModel().getColumn(0).setResizable(false);
            Tabla1.getColumnModel().getColumn(1).setMinWidth(300);
            Tabla1.getColumnModel().getColumn(1).setPreferredWidth(300);
            Tabla1.getColumnModel().getColumn(1).setMaxWidth(300);
            Tabla1.getColumnModel().getColumn(10).setResizable(false);
        }

        jPanel13.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        jPanel14.setBackground(new java.awt.Color(255, 255, 255));
        jPanel14.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        lblId.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        lblId.setText(" ");
        jPanel14.add(lblId);

        lblCambio.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        lblCambio.setForeground(new java.awt.Color(153, 0, 0));
        lblCambio.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblCambio.setText("Esta requisicion no se puede editar");
        jPanel14.add(lblCambio);

        lblEmpleado.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        lblEmpleado.setForeground(new java.awt.Color(51, 51, 51));
        lblEmpleado.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblEmpleado.setText("Empleado");
        jPanel14.add(lblEmpleado);

        jPanel13.add(jPanel14, java.awt.BorderLayout.PAGE_START);

        jPanel15.setBackground(new java.awt.Color(240, 240, 240));
        jPanel15.setLayout(new java.awt.GridBagLayout());

        btnGuardar.setBackground(new java.awt.Color(0, 102, 204));
        btnGuardar.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        btnGuardar.setForeground(new java.awt.Color(255, 255, 255));
        btnGuardar.setText("Guardar");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.ipadx = 80;
        gridBagConstraints.ipady = 5;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 5, 0);
        jPanel15.add(btnGuardar, gridBagConstraints);

        jPanel13.add(jPanel15, java.awt.BorderLayout.PAGE_END);

        jPanel1.add(jPanel13, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        jMenu2.setText("Opciones");
        jMenu2.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N

        jMenuItem8.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        jMenuItem8.setText("Agregar proveedor");
        jMenuItem8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem8ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem8);
        jMenu2.add(jSeparator6);

        CancelarRequisicion.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        CancelarRequisicion.setText("Cancelar requisicion");
        CancelarRequisicion.setEnabled(false);
        CancelarRequisicion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CancelarRequisicionActionPerformed(evt);
            }
        });
        jMenu2.add(CancelarRequisicion);
        jMenu2.add(jSeparator3);
        jMenu2.add(jSeparator7);

        comprar.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        comprar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/documento.png"))); // NOI18N
        comprar.setText("Comprar");
        comprar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comprarActionPerformed(evt);
            }
        });
        jMenu2.add(comprar);
        jMenu2.add(jSeparator4);

        jMenuItem13.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        jMenuItem13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/editarEmpleado_16.png"))); // NOI18N
        jMenuItem13.setText("Transferir Requisicion");
        jMenuItem13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem13ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem13);
        jMenu2.add(jSeparator8);

        buscar.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        buscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/buscar_16.png"))); // NOI18N
        buscar.setText("Buscar requisicion");
        buscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buscarActionPerformed(evt);
            }
        });
        jMenu2.add(buscar);

        jMenuBar1.add(jMenu2);

        jMenu3.setText("Ver");
        jMenu3.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        jMenu3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenu3ActionPerformed(evt);
            }
        });

        jMenuItem9.setText("        Reclamos");
        jMenuItem9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem9ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem9);

        jMenuItem15.setText("        Entradas a almacén");
        jMenuItem15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem15ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem15);

        jMenuBar1.add(jMenu3);

        jMenu4.setText("Reportes");
        jMenu4.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N

        jMenuItem12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/reporte_compras_16.png"))); // NOI18N
        jMenuItem12.setText("Compras de mes                                             ");
        jMenuItem12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem12ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem12);

        jMenuItem14.setText("Ordenes de compra pendientes");
        jMenuItem14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem14ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem14);

        jMenuBar1.add(jMenu4);

        setJMenuBar(jMenuBar1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel33MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel33MouseClicked
        limpiarPanel();
        String sql = "SELECT r.Progreso, r.NumeroEmpleado, r.Id, r.Fecha, r.Comprador, "
                + "GROUP_CONCAT(o.OrdenNo ORDER BY o.OrdenNo SEPARATOR ', ') AS Ordenes "
                + "FROM Requisicion r "
                + "LEFT JOIN ordencompra o ON r.Id = o.RequisicionNo "
                + "WHERE r.Completado = 'NO' AND r.Progreso like 'NUEVO' "
                + "GROUP BY r.Progreso, r.NumeroEmpleado, r.Id, r.Fecha, r.Comprador ORDER BY r.Id DESC;";
        verRequisiciones(sql);
        panelRound1.setVisible(false);
    }//GEN-LAST:event_jLabel33MouseClicked

    private void jLabel34MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel34MouseClicked
        limpiarPanel();
        String sql = "SELECT r.Progreso, r.NumeroEmpleado, r.Id, r.Fecha, r.Comprador, "
                + "GROUP_CONCAT(o.OrdenNo ORDER BY o.OrdenNo SEPARATOR ', ') AS Ordenes "
                + "FROM Requisicion r "
                + "LEFT JOIN ordencompra o ON r.Id = o.RequisicionNo "
                + "WHERE r.Completado = 'NO' AND r.Progreso like 'COMPRADO' OR Progreso like 'COTIZANDO' "
                + "GROUP BY r.Progreso, r.NumeroEmpleado, r.Id, r.Fecha, r.Comprador ORDER BY r.Id DESC;";
        verRequisiciones(sql);
        panelRound1.setVisible(false);
    }//GEN-LAST:event_jLabel34MouseClicked

    private void jLabel35MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel35MouseClicked
        limpiarPanel();
        String sql = "SELECT r.Progreso, r.NumeroEmpleado, r.Id, r.Fecha, r.Comprador, "
                + "GROUP_CONCAT(o.OrdenNo ORDER BY o.OrdenNo SEPARATOR ', ') AS Ordenes "
                + "FROM Requisicion r "
                + "LEFT JOIN ordencompra o ON r.Id = o.RequisicionNo "
                + "WHERE r.Completado = 'NO' AND r.Progreso like 'LLEGO, INCOMPETO' "
                + "GROUP BY r.Progreso, r.NumeroEmpleado, r.Id, r.Fecha, r.Comprador ORDER BY r.Id DESC;";
        verRequisiciones(sql);
        panelRound1.setVisible(false);
    }//GEN-LAST:event_jLabel35MouseClicked

    private void jMenuItem8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem8ActionPerformed
        JFrame j = (JFrame) JOptionPane.getFrameForComponent(this);
        VentanaEmergente.Compras.addProveedor a = new VentanaEmergente.Compras.addProveedor(j, true);
        a.setVisible(true);
        autocompletar();
    }//GEN-LAST:event_jMenuItem8ActionPerformed

    private void CancelarRequisicionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CancelarRequisicionActionPerformed
//        int opc = JOptionPane.showConfirmDialog(this, "¿ESTAS SEGURO(A) QUE DESEAS CANCELAR ESTA REQUISICION?");
//        if (opc == 0) {
//            try {
//                Connection con = null;
//                Conexion con1 = new Conexion();
//                con = con1.getConnection();
//
//                int tab = Tabla1.getRowCount() - cont;
//                if (cont > 0) {
//                    tab = Tabla1.getRowCount() - cont - 1;
//                }
//                if (Tabla1.getSelectedRow() > tab) {
//                    String act = "update edicionpo set Estado = ? where PO = ?";
//                    PreparedStatement pst1 = con.prepareCall(act);
//
//                    pst1.setString(1, "CANCELADO");
//                    pst1.setString(2, id);
//                    int n1 = pst1.executeUpdate();
//
//                    if (n1 > 0) {
//                        limpiarTabla1();
//                        limpiarTabla();
//                        verDatos();
//                    }
//                } else if (Tabla1.getSelectedRow() == tab) {
//
//                } else {
//                    String act = "update Requisicion set Progreso = ?, Completado = ? where Id = ?";
//                    PreparedStatement pst1 = con.prepareCall(act);
//
//                    pst1.setString(1, "CANCELADO");
//                    pst1.setString(2, "SI");
//                    pst1.setString(3, id);
//                    int n1 = pst1.executeUpdate();
//
//                    if (n1 > 0) {
//                        limpiarTabla1();
//                        limpiarTabla();
//                        verDatos();
//                    }
//                }
//            } catch (SQLException e) {
//                JOptionPane.showMessageDialog(this, "ERROR AL CANCELAR REQUISICION" + e, "ERROR", JOptionPane.ERROR_MESSAGE);
//            }
//        }
    }//GEN-LAST:event_CancelarRequisicionActionPerformed

    private void AprobacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AprobacionActionPerformed
        int opc = JOptionPane.showConfirmDialog(this, "¿Deseas mandar a aprobacion la requisicion " + lblId.getText() + "?");
        if (opc == 0) {
            try {
                Connection con;
                Conexion con1 = new Conexion();
                con = con1.getConnection();
                JFrame f = (JFrame) JOptionPane.getFrameForComponent(this);
                verificarTotales ver = new verificarTotales(f, true);
                boolean band = ver.getOption(lblId.getText());
                if (band) {
                    Statement st = con.createStatement();
                    int fila = Tabla1.getSelectedRow();

                    String sql = "update Requisicion set Progreso = ? where Id = ?";
                    PreparedStatement pst = con.prepareStatement(sql);
                    String sql2 = "select Id, Comprar, LiberacionAlmacen from Requisicion where Id like '" + lblId.getText() + "'";
                    ResultSet rs = st.executeQuery(sql2);
                    String datos[] = new String[10];
                    boolean liberacion = false;
                    while (rs.next()) {
                        datos[1] = rs.getString("Comprar");
                        liberacion = rs.getBoolean("LiberacionAlmacen");
                    }
                    String d;
                    if (!liberacion) {
                        JOptionPane.showMessageDialog(this, "La requisicion no esta liberada por almacen", "Advertencia", JOptionPane.WARNING_MESSAGE);
                    } else {
                        if (datos[1] == null) {
                            d = "SI";
                        } else {
                            d = datos[1];
                        }
                        if (d.equals("NO")) {
                            pst.setString(1, "COTIZADO");
                            pst.setString(2, lblId.getText());
                        } else {
                            pst.setString(1, "APROBACION");
                            pst.setString(2, lblId.getText());
                        }

                        int n = pst.executeUpdate();

                        if (n > 0) {
                            limpiarPanel();
                            verRequisiciones(null);
                            limpiarTabla(lblCambio.isVisible());
                            crearNotificacion();
                            JOptionPane.showMessageDialog(this, "DATOS ENVIADOS");
                        }
                    }
                }

            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "ERROR AL ACTUALIZAR" + e, "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_AprobacionActionPerformed

    private void comprarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comprarActionPerformed
        try {
            Connection con = new Conexion().getConnection();

            String sql = "update requisicion set Progreso = 'COMPRADO' where Id like '" + lblId.getText() + "'";
            PreparedStatement pst = con.prepareStatement(sql);

            int n = pst.executeUpdate();

            if (n > 0) {
                JOptionPane.showMessageDialog(this, "COMPRADO");
                limpiarTabla(lblCambio.isVisible());
                verDatos(lblId.getText(), estado);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "ERROR: " + e, "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_comprarActionPerformed

    private void jMenuItem13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem13ActionPerformed
        JFrame f = (JFrame) JOptionPane.getFrameForComponent(this);
        TransferirRequisicion transferir = new TransferirRequisicion(f, true);
        transferir.setLocationRelativeTo(f);
        transferir.setVisible(true);
    }//GEN-LAST:event_jMenuItem13ActionPerformed

    private void buscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buscarActionPerformed
        String requi = mostrarDialogoEmergente();
        if (requi != null) {
            if (requi.equals("")) {
            } else {
                try {
                    Connection con;
                    Conexion con1 = new Conexion();
                    con = con1.getConnection();
                    Statement st = con.createStatement();
                    String sql = "select Id, NumeroEmpleado, Progreso, Completado, Fecha, Comprar from requisicion where Id like '" + requi + "'";
                    ResultSet rs = st.executeQuery(sql);
                    String empleado = "";
                    String estado = "";
                    String completado = "";
                    String fecha = "";
                    String comprar = "";
                    while (rs.next()) {
                        empleado = rs.getString("NumeroEmpleado");
                        estado = rs.getString("Progreso");
                        completado = rs.getString("Completado");
                        fecha = rs.getString("Fecha");
                        comprar = rs.getString("Comprar");
                    }
                    if (empleado == null || empleado.equals("")) {
                        JOptionPane.showMessageDialog(this, "NO EXISTE ESTA REQUISICION", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
                    } else {
                        String sql2 = "select * from registroempleados where NumEmpleado like '" + empleado + "'";
                        Statement st2 = con.createStatement();
                        ResultSet rs2 = st2.executeQuery(sql2);
                        while (rs2.next()) {
                            empleado = rs2.getString("Nombre") + " " + rs2.getString("Apellido");
                        }
                        JFrame f = (JFrame) JOptionPane.getFrameForComponent(this);
                        Estado es = new Estado(f, true);
                        es.lblEmpleado.setText(empleado);
                        es.lblEstado.setText(estado);
                        es.lblCompletado.setText(completado);
                        es.lblFecha.setText(fecha);
                        es.lblComprar.setText(comprar);
                        es.verRequisicion(requi);
                        es.setVisible(true);
                    }
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(this, "ERROR: " + e, "ERROR", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }//GEN-LAST:event_buscarActionPerformed

    private void jMenuItem12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem12ActionPerformed
        JFrame f = (JFrame) JOptionPane.getFrameForComponent(this);
        ReporteMes reporte = new ReporteMes(f, true);
        reporte.setLocationRelativeTo(f);
        reporte.setVisible(true);
    }//GEN-LAST:event_jMenuItem12ActionPerformed

    private void jMenuItem14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem14ActionPerformed
        JFrame f = (JFrame) JOptionPane.getFrameForComponent(this);
        Thread hilo = new Thread() {
            public void run() {
                OCPendientes oc = new OCPendientes(f, true);
                oc.setLocationRelativeTo(f);
                oc.setVisible(true);
            }
        };
        hilo.start();
    }//GEN-LAST:event_jMenuItem14ActionPerformed

    private void btnCotizacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCotizacionActionPerformed
        enviarCotizacion();
    }//GEN-LAST:event_btnCotizacionActionPerformed

    private void jLabel38MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel38MouseClicked
        limpiarPanel();
        verRequisiciones(null);
    }//GEN-LAST:event_jLabel38MouseClicked

    private void jLabel36MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel36MouseClicked
        String sql = "SELECT r.Progreso, r.NumeroEmpleado, r.Id, r.Fecha, r.Comprador, "
                + "GROUP_CONCAT(o.OrdenNo ORDER BY o.OrdenNo SEPARATOR ', ') AS Ordenes "
                + "FROM Requisicion r "
                + "LEFT JOIN ordencompra o ON r.Id = o.RequisicionNo "
                + "WHERE r.Completado = 'NO' AND r.Progreso like 'CANCE%' "
                + "GROUP BY r.Progreso, r.NumeroEmpleado, r.Id, r.Fecha, r.Comprador ORDER BY r.Id DESC;";
        limpiarPanel();
        verRequisiciones(sql);
    }//GEN-LAST:event_jLabel36MouseClicked

    private void btnVerEmpleadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVerEmpleadoActionPerformed
        verNombreEmpleado(requisitor);
    }//GEN-LAST:event_btnVerEmpleadoActionPerformed

    private void btnVerCompradorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVerCompradorActionPerformed
        verNombreEmpleado(comprador);
    }//GEN-LAST:event_btnVerCompradorActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
//        if (!lblCambio.isVisible()) {
        try {
            Connection con = new Conexion().getConnection();
            for (int i = 0; i < Tabla1.getRowCount(); i++) {
                if (convertirNull(i, 11).equals("")) {
                    if (convertirNull(i, 0).equals("")) {
                        String sql3 = "select NumeroDeParte from Inventario where NumeroDeParte like '" + convertirNull(i, 2) + "'";
                        Statement st = con.createStatement();
                        ResultSet rs = st.executeQuery(sql3);
                        String np = "";
                        int n = 0;
                        while (rs.next()) {
                            np = rs.getString("NumeroDeParte");
                        }
                        if (np == null) {
                            String inse = "insert into inventario (NumeroDeParte,Descripcion,Cantidad,UM,Proveedor) values(?,?,?,?,?)";
                            PreparedStatement pst = con.prepareStatement(inse);
                            pst.setString(1, convertirNull(i, 2));//2 codigo
                            pst.setString(2, convertirNull(i, 1)); //1 descripcion
                            pst.setString(3, "0"); // 
                            pst.setString(4, convertirNull(i, 3)); //3 um
                            pst.setString(5, convertirNull(i, 7)); //7 proveedir

                            n = pst.executeUpdate();
                        } else if (np.equals("")) {
                            String inse = "insert into inventario (NumeroDeParte,Descripcion,Cantidad,UM,Proveedor) values(?,?,?,?,?)";
                            PreparedStatement pst = con.prepareStatement(inse);
                            pst.setString(1, convertirNull(i, 2));//2 codigo
                            pst.setString(2, convertirNull(i, 1));// descripcion
                            pst.setString(3, "0");
                            pst.setString(4, convertirNull(i, 3));//3 um
                            pst.setString(5, convertirNull(i, 7));//7 proveedor

                            n = pst.executeUpdate();
                        }

                        String bus = "select * from requisiciones where Id like '" + convertirNull(i, 0) + "'";
                        Statement st2 = con.createStatement();
                        ResultSet rs2 = st2.executeQuery(bus);
                        String dat[] = new String[10];
                        while (rs2.next()) {
                            dat[0] = rs2.getString("NumRequisicion");
                            dat[1] = rs2.getString("Proyecto");
                            dat[2] = rs2.getString("Requisitor");
                        }
                        String req = "insert into requisiciones (NumRequisicion,Codigo,Descripcion,Proyecto,Cantidad,Requisitor,UM,Proveedor,TE) values (?,?,?,?,?,?,?,?,?)";
                        PreparedStatement pst2 = con.prepareStatement(req);
                        pst2.setString(1, dat[0]);
                        pst2.setString(2, convertirNull(i, 2));//codigo
                        pst2.setString(3, convertirNull(i, 1));//descripcion
                        pst2.setString(4, dat[1]);
                        pst2.setString(5, "0");
                        pst2.setString(6, dat[2]);
                        pst2.setString(7, convertirNull(i, 3));//um
                        pst2.setString(8, convertirNull(i, 7));//proveedor
                        String TE = convertirNull(i, 8);//te
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        Date fec;
                        if (TE.equals("")) {
                            pst2.setDate(9, null);
                        } else {
                            try {
                                fec = sdf.parse(TE);
                            } catch (ParseException ex) {
                                fec = null;
                            }
                            if (fec == null) {
                                pst2.setDate(9, null);
                            } else {
                                java.sql.Date data = new java.sql.Date(fec.getTime());
                                pst2.setDate(9, data);
                            }
                        }
                        int n1 = pst2.executeUpdate();

                        if (n > 0 && n1 > 0) {
                            JOptionPane.showMessageDialog(this, "SE AGREGARON NUEVAS PARTIDAS");
                        }
                    }
                }
            }
            String sql = "update requisiciones set Descripcion = ?, Codigo = ?, UM = ?, Cantidad = ?, Precio = ?,Proveedor = ?, TE = ? where Id = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            String sql2 = "update inventario set Descripcion = ?, UM = ?,Proveedor = ? where NumeroDeParte = ?";
            PreparedStatement pst2 = con.prepareStatement(sql2);
            int n = 0, n2 = 0;

            for (int i = 0; i < Tabla1.getRowCount(); i++) {
                if (convertirNull(i, 11).equals("")) {
                    String UM, Precio, Proveedor, TE;
                    double cantidad, canSt;
                    canSt = convertirNull(i, 12).equals("") ? 0 : Double.parseDouble(convertirNull(i, 12));
                    cantidad = Double.parseDouble(convertirNull(i, 4));
                    UM = convertirNull(i, 3);
                    Precio = convertirNull(i, 5);
                    Proveedor = convertirNull(i, 7);
                    TE = convertirNull(i, 8);
                    //--------------------------------MUESTRA--------------------------------

                    pst.setString(1, convertirNull(i, 1));
                    pst.setString(2, convertirNull(i, 2));
                    pst.setString(3, UM);
                    pst.setString(4, String.valueOf(cantidad + canSt));
                    pst.setString(5, Precio);
                    pst.setString(6, Proveedor);
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

                    if (TE.equals("")) {
                        pst.setDate(7, null);
                    } else {
                        Date fec = null;
                        try {
                            fec = sdf.parse(TE);
                        } catch (ParseException ex) {
                            fec = null;
                        }
                        if (fec == null) {
                            pst.setDate(7, null);
                        } else {
                            java.sql.Date data = new java.sql.Date(fec.getTime());
                            pst.setDate(7, data);
                        }

                    }
                    pst.setString(8, convertirNull(i, 0));
                    n = pst.executeUpdate();

                    pst2.setString(1, convertirNull(i, 1));
                    pst2.setString(2, UM);
                    pst2.setString(3, Proveedor);
                    pst2.setString(4, convertirNull(i, 2));

                    n2 = pst2.executeUpdate();

                    if (n == 0) {
                        JOptionPane.showMessageDialog(this, "NO SE PUDO GUARDAR EN REQUISICIONES EN LA FILA NO. " + (i + 1));
                    }
                    if (n2 == 0) {
                        JOptionPane.showMessageDialog(this, "NO SE PUDO GUARDAR EN INVENTARIO EN LA FILA NO. " + (i + 1));
                    }
                }
            }
            verDatos(lblId.getText(), this.estado);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error: " + e, "Error", JOptionPane.ERROR_MESSAGE);
        }
//        }
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnOrdenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOrdenActionPerformed
        if (estado.equals("aprobado") || estado.equals("comprado") || estado.equals("llego, incompeto")) {
            crearOrden();
        }
    }//GEN-LAST:event_btnOrdenActionPerformed

    private void Tabla1MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Tabla1MouseDragged
        if (Tabla1.getSelectedColumn() == 8) {
            for (int i = 0; i < Tabla1.getSelectedRows().length; i++) {
                Tabla1.setValueAt(Tabla1.getValueAt(fil, 8), Tabla1.getSelectedRows()[i], 8);
            }
        }
    }//GEN-LAST:event_Tabla1MouseDragged

    private void Tabla1MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Tabla1MouseReleased
        fil = Tabla1.getSelectedRow();
    }//GEN-LAST:event_Tabla1MouseReleased

    private void Tabla1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Tabla1MouseClicked
        if (Tabla1.getColumnName(Tabla1.getSelectedColumn()).equals("T.E.")) {
            JFrame f = (JFrame) JOptionPane.getFrameForComponent(this);
            fecha = new Fecha(f, true);
            filaFecha = Tabla1.getSelectedRow();
            fecha.btnGuardar.addActionListener(this);
            fecha.setVisible(true);
        }
    }//GEN-LAST:event_Tabla1MouseClicked

    private void popTablaPopupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_popTablaPopupMenuWillBecomeVisible
        if (Tabla1.getSelectedRow() > -1) {
            btnEliminar.setEnabled(true);
            if (Tabla1.getValueAt(Tabla1.getSelectedRow(), 11) != null) {
                if (!Tabla1.getValueAt(Tabla1.getSelectedRow(), 11).toString().equals("")) {
                    btnEditar.setEnabled(true);
                    btnVerOrden.setEnabled(true);
                } else {
                    btnEditar.setEnabled(false);
                    btnVerOrden.setEnabled(false);
                }
            } else {
                btnEditar.setEnabled(false);
                btnVerOrden.setEnabled(false);
            }
        } else {
            btnEditar.setEnabled(false);
            btnVerOrden.setEnabled(false);
            btnEliminar.setEnabled(false);
        }
    }//GEN-LAST:event_popTablaPopupMenuWillBecomeVisible

    private void btnVerOrdenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVerOrdenActionPerformed
        abrirPDF(Tabla1.getValueAt(Tabla1.getSelectedRow(), 11).toString());
    }//GEN-LAST:event_btnVerOrdenActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        int opc = JOptionPane.showConfirmDialog(this, "Estas seguro de eliminar el numero de parte: " + Tabla1.getValueAt(Tabla1.getSelectedRow(), 2).toString(), "Advertencia", JOptionPane.WARNING_MESSAGE);
        if (opc == JOptionPane.OK_OPTION) {
            try {
                Connection con = new Conexion().getConnection();
                String sql = "update requisiciones set activo = ? where id = ?";
                PreparedStatement pst = con.prepareStatement(sql);

                pst.setBoolean(1, false);
                pst.setString(2, Tabla1.getValueAt(Tabla1.getSelectedRow(), 0).toString());

                int n = pst.executeUpdate();

                if (n > 0) {
                    limpiarTabla(lblCambio.isVisible());
                    verDatos(lblId.getText(), estado);
                    JOptionPane.showMessageDialog(this, "Datos guardados correctamente");
                }

            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Error al tratar de eliminar fila" + e, "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnCancelarRequiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarRequiActionPerformed
        int opc = JOptionPane.showConfirmDialog(this, "¿Deseas cancelar la requisicion " + lblId.getText() + "?");
        if (opc == 0) {
            try {
                Connection con = new Conexion().getConnection();
                String act = "update Requisicion set Progreso = ?, Completado = ? where Id = ?";
                PreparedStatement pst1 = con.prepareCall(act);

                pst1.setString(1, "CANCELADO");
                pst1.setString(2, "SI");
                pst1.setString(3, lblId.getText());
                int n1 = pst1.executeUpdate();

                int n = 0;
                for (int i = 0; i < Tabla1.getSelectedRows().length; i++) {
                    String sql = "update requisiciones set OC = ?, Notas = ? where Id = ?";
                    PreparedStatement pst = con.prepareStatement(sql);

                    pst.setString(1, "CANCELADO");
                    pst.setString(2, "CANCELADO");
                    pst.setString(3, Tabla1.getValueAt(Tabla1.getSelectedRows()[i], 7).toString());

                    n += pst.executeUpdate();
                }

                if (n > 0) {
                    JOptionPane.showMessageDialog(this, "Requisicion cancelada correctamente");
                }

                if (n1 > 0) {
                    limpiarPanel();
                    limpiarTabla(lblCambio.isVisible());
                    verRequisiciones(null);
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "ERROR AL CANCELAR REQUISICION" + e, "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_btnCancelarRequiActionPerformed

    private void btnCancelarOrdenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarOrdenActionPerformed
        JFrame f = (JFrame) JOptionPane.getFrameForComponent(this);
        CancelarOrden co = new CancelarOrden(f, true);
        co.txtOrden.setText(Tabla1.getValueAt(Tabla1.getSelectedRow(), 11).toString());
        co.verOrden();
        co.setVisible(true);
    }//GEN-LAST:event_btnCancelarOrdenActionPerformed

    private void btnTransferirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTransferirActionPerformed
        JFrame f = (JFrame) JOptionPane.getFrameForComponent(this);
        TransferirRequisicion transferir = new TransferirRequisicion(f, true);
        transferir.txtCodigo.setText(lblId.getText());
        transferir.verRequisicion();
        transferir.setLocationRelativeTo(f);
        transferir.setVisible(true);
    }//GEN-LAST:event_btnTransferirActionPerformed

    private void jMenu3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenu3ActionPerformed

    }//GEN-LAST:event_jMenu3ActionPerformed

    private void jMenuItem15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem15ActionPerformed
                Espera espera = new Espera();
                espera.setVisible(true);
        
                Thread hilo = new Thread() {
                        @Override
                        public void run() {
                                try {
                                        // Si ya está abierta, solo traer al frente para evitar que se duplique
                                        if (dialogEntradas != null && dialogEntradas.isShowing()) {
                                                dialogEntradas.toFront();
                                                return;
                                            }
                                        entradasAlmacen panelEntradas = new entradasAlmacen();
                                        panelEntradas.cargarEntradasAlmacen();
                    
                                        dialogEntradas = new javax.swing.JDialog();
                                        dialogEntradas.setTitle("Entradas a Almacén");
                                        dialogEntradas.setDefaultCloseOperation(javax.swing.JDialog.DISPOSE_ON_CLOSE);
                    
                                        dialogEntradas.getContentPane().add(panelEntradas);
                                        dialogEntradas.pack();
                                        dialogEntradas.setLocationRelativeTo(null);
                    
                                        dialogEntradas.addWindowListener(new java.awt.event.WindowAdapter() {
                                                @Override
                                                public void windowClosed(java.awt.event.WindowEvent e) {
                                                        dialogEntradas = null;
                                                    }
                                            });
                
                                } finally {
                                    espera.dispose();
                                }
            
                            // Mostrar el diálogo SIEMPRE en el EDT
                            javax.swing.SwingUtilities.invokeLater(new Runnable() {
                                    public void run() {
                                            dialogEntradas.setVisible(true);
                                        }
                                });
                    }
                };
        
                hilo.start();
    }//GEN-LAST:event_jMenuItem15ActionPerformed

    private void jMenuItem9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem9ActionPerformed
        JFrame f = (JFrame) JOptionPane.getFrameForComponent(this);
        Reclamos r = new Reclamos(f, true);
        r.setVisible(true);
    }//GEN-LAST:event_jMenuItem9ActionPerformed

    private void popRequisicionesPopupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_popRequisicionesPopupMenuWillBecomeVisible
        if (estado.equals("aprobado") || estado.equals("comprado") || estado.equals("llego, incompeto")) {
            btnOrden.setEnabled(true);
        } else {
            btnOrden.setEnabled(false);
        }
    }//GEN-LAST:event_popRequisicionesPopupMenuWillBecomeVisible

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ComprasNew("61").setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem Aprobacion;
    private javax.swing.JMenuItem CancelarRequisicion;
    private javax.swing.JTable Tabla1;
    private javax.swing.JMenuItem btnCancelarOrden;
    private javax.swing.JMenuItem btnCancelarRequi;
    private javax.swing.JMenuItem btnCotizacion;
    private javax.swing.JMenuItem btnEditar;
    private javax.swing.JMenuItem btnEliminar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JMenuItem btnOrden;
    private javax.swing.JMenuItem btnTransferir;
    private javax.swing.JMenuItem btnVerComprador;
    private javax.swing.JMenuItem btnVerEmpleado;
    private javax.swing.JMenuItem btnVerOrden;
    private javax.swing.JMenuItem buscar;
    private javax.swing.JMenuItem comprar;
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
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem12;
    private javax.swing.JMenuItem jMenuItem13;
    private javax.swing.JMenuItem jMenuItem14;
    private javax.swing.JMenuItem jMenuItem15;
    private javax.swing.JMenuItem jMenuItem8;
    private javax.swing.JMenuItem jMenuItem9;
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
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JPopupMenu.Separator jSeparator10;
    private javax.swing.JPopupMenu.Separator jSeparator11;
    private javax.swing.JPopupMenu.Separator jSeparator12;
    private javax.swing.JPopupMenu.Separator jSeparator13;
    private javax.swing.JPopupMenu.Separator jSeparator14;
    private javax.swing.JPopupMenu.Separator jSeparator3;
    private javax.swing.JPopupMenu.Separator jSeparator4;
    private javax.swing.JPopupMenu.Separator jSeparator6;
    private javax.swing.JPopupMenu.Separator jSeparator7;
    private javax.swing.JPopupMenu.Separator jSeparator8;
    private javax.swing.JPopupMenu.Separator jSeparator9;
    private javax.swing.JLabel lblCambio;
    private javax.swing.JLabel lblEmpleado;
    private javax.swing.JLabel lblId;
    private scrollPane.PanelRound panelRound1;
    private scrollPane.PanelRound panelRound10;
    private scrollPane.PanelRound panelRound11;
    private scrollPane.PanelRound panelRound12;
    private scrollPane.PanelRound panelRound13;
    private scrollPane.PanelRound panelRound14;
    private scrollPane.PanelRound panelRound15;
    private scrollPane.PanelRound panelRound16;
    private scrollPane.PanelRound panelRound17;
    private scrollPane.PanelRound panelRound18;
    private scrollPane.PanelRound panelRound19;
    private scrollPane.PanelRound panelRound2;
    private scrollPane.PanelRound panelRound20;
    private scrollPane.PanelRound panelRound21;
    private scrollPane.PanelRound panelRound22;
    private scrollPane.PanelRound panelRound23;
    private scrollPane.PanelRound panelRound24;
    private scrollPane.PanelRound panelRound25;
    private scrollPane.PanelRound panelRound3;
    private scrollPane.PanelRound panelRound4;
    private scrollPane.PanelRound panelRound5;
    private scrollPane.PanelRound panelRound6;
    private scrollPane.PanelRound panelRound7;
    private scrollPane.PanelRound panelRound8;
    private scrollPane.PanelRound panelRound9;
    private javax.swing.JPopupMenu popRequisiciones;
    private javax.swing.JPopupMenu popTabla;
    // End of variables declaration//GEN-END:variables

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            if (e.getSource() == elegir.btnCrear) {
                generarOrdenCompra(false);
            }
        } catch (Exception ex) {

        }
        try {
            if (e.getSource() == elegir.btnCancelar) {
                elegir.dispose();
            }
        } catch (Exception a) {
//            JOptionPane.showMessageDialog(this, "ERROR: "+a);
        }

        try {
            if (fecha != null) {
                if (e.getSource() == fecha.btnGuardar) {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    if (Tabla1.getColumnName(0).equals("REQUISITOR")) {
                        try {
                            Connection con;
                            Conexion con1 = new Conexion();
                            con = con1.getConnection();
                            String sql = "update requisiciones set TE = ? where Id = ?";
                            PreparedStatement pst = con.prepareStatement(sql);

                            String fecha = sdf.format(this.fecha.calendario.getDate());

                            pst.setString(1, fecha);
                            pst.setString(2, Tabla1.getValueAt(Tabla1.getSelectedRow(), 7).toString());

                            int n = pst.executeUpdate();

                            if (n > 0) {
                                JOptionPane.showMessageDialog(this, "Fecha guardada");
                            }

                        } catch (SQLException ex) {
                            JOptionPane.showMessageDialog(this, "ERROR: " + ex, "ERROR", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                    String fechaa = sdf.format(this.fecha.calendario.getDate());
                    Tabla1.setValueAt(fechaa, Tabla1.getSelectedRow(), 8);
                    fecha.dispose();
                }
            }
        } catch (Exception a) {

        }
    }
}
