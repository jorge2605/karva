package pruebas;

import Conexiones.Conexion;
import Controlador.FileTransferHandler;
import Controlador.maquinados.revisarPlanos;
import VO.ArchivosVO;
import VentanaEmergente.Diseño.DatosHoras;
import VentanaEmergente.Diseño.EditarTablaBOM;
import VentanaEmergente.Diseño.InicioDiseño;
import VentanaEmergente.Diseño.PlanoDetectado;
import VentanaEmergente.Diseño.TempleteDiseno;
import VentanaEmergente.Diseño.codigoBarras;
import VentanaEmergente.Diseño.subirBOM;
import VentanaEmergente.Reportes.dis;
import com.google.zxing.BarcodeFormat;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.mxrck.autocompleter.TextAutoCompleter;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetAdapter;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.beans.PropertyVetoException;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import mivisorpdf.MiVisorPDF;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.text.PDFTextStripperByArea;
import technology.tabula.ObjectExtractor;
import technology.tabula.Page;
import technology.tabula.PageIterator;
import technology.tabula.RectangularTextContainer;
import technology.tabula.Table;
import technology.tabula.extractors.SpreadsheetExtractionAlgorithm;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.common.ByteMatrix;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.itextpdf.text.pdf.PdfPage;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Hashtable;
import org.krysalis.barcode4j.impl.code39.Code39Bean;
import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider;

public final class Disenio1 extends InternalFrameImagen implements ActionListener, MouseListener {

    int contador = 0;
    public String numEmpleado;
    codigoBarras barras;
    private ArrayList<ArchivosVO> ListaComponente;
    MiVisorPDF pn = new MiVisorPDF();
    ArchivosVO pl = new ArchivosVO();
    JButton botones[];
    File archivos[];
    private int numImg;
    int seleccionado;
    public TempleteDiseno templete;
    private final String ruta;
    private boolean ctrlPressed = false;
    DatosHoras datosHoras;
    boolean bandDatosHoras;
    private int paginas = -1;
    private int totalp = -1;
    String empresa;
    private String ruta_archivo = "";
    JLabel lblPlanos[];
    TextAutoCompleter au;
    Inicio1 inicio;
    FileTransferHandler transfer;

    int x, y;

    public final void insertarImagen(String url, JButton lbl) {
        ImageIcon icon = new ImageIcon(url);
        java.awt.Image imagen = icon.getImage();
        java.awt.Image imagenEscalada = imagen.getScaledInstance(16, 16, java.awt.Image.SCALE_SMOOTH);

        lbl.setIcon(new ImageIcon(imagenEscalada));
    }

    public JLabel setLabel(JLabel label, String plano, boolean img, Color color) {
        label.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        label.setForeground(color);
        label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        if (img) {
            label.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/pdf.png")));
        }
        label.setText("<html> <p style='text-align: center;'>" + plano + "</p>");
        label.setName(plano);
        return label;
    }

    public String getDirectorio(String proyecto) {
        String path = "/pdf/" + proyecto;
        File direccion = new File(path);

        if (!direccion.isDirectory()) {
            boolean res = direccion.mkdirs();
            if (res) {
                return path;
            } else {
                return path;
            }
        } else {
            return path;
        }
    }

    public static BufferedImage generateBarcode(String code) throws IOException {
        Code39Bean barcodeBean = new Code39Bean();
        final int dpi = 150;

        barcodeBean.setModuleWidth(0.2);
        barcodeBean.setWideFactor(3);
        barcodeBean.doQuietZone(false);
        BitmapCanvasProvider canvas = new BitmapCanvasProvider(dpi, BufferedImage.TYPE_BYTE_BINARY, false, 0);
        barcodeBean.generateBarcode(canvas, code);
        canvas.finish();

        return canvas.getBufferedImage();
    }

    public static BufferedImage generateBarcodeSinLetras(String code) throws IOException {
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

    public void crearPdfPagina(File archivoOriginal, int numeroPagina, File archivoSalida, String id, technology.tabula.Rectangle dim) {
        try {
            PdfReader reader = new PdfReader(archivoOriginal);
            PdfDocument pdfOriginal = new PdfDocument(reader);
            PdfWriter writer = new PdfWriter(archivoSalida);
            PdfDocument pdfNuevo = new PdfDocument(writer);
            pdfOriginal.copyPagesTo(numeroPagina, numeroPagina, pdfNuevo);
            pdfOriginal.close();
            pdfNuevo.close();
            agregarCodigoBarras(archivoSalida, id, dim);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error al crear el plano: " + e, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

//    private void agregarCodigoBarras(File archivo, String id, Dimension dim) {
//        File archivoTemporal = new File(archivo.getParent(), archivo.getName().replace(".pdf", "_temp.pdf"));
//        try {
//            BufferedImage barcodeImage = generateBarcode(id);
//            File tempBarcode = File.createTempFile("barcode", ".png");
//            javax.imageio.ImageIO.write(barcodeImage, "png", tempBarcode);
//            PdfReader reader = new PdfReader(archivo);
//            PdfWriter writer = new PdfWriter(archivoTemporal);
//            PdfDocument pdfDoc = new PdfDocument(reader, writer);
//            Document doc = new Document(pdfDoc);
//            ImageData imageData = ImageDataFactory.create(tempBarcode.getAbsolutePath());
//            com.itextpdf.layout.element.Image im = new com.itextpdf.layout.element.Image(imageData);
//            im.setFixedPosition(dim.width, dim.height);
//            doc.add(im);
//            doc.close();
//            tempBarcode.delete();
//            if (archivo.delete()) {
//                archivoTemporal.renameTo(archivo);
//            }
//
//        } catch (IOException e) {
//            JOptionPane.showMessageDialog(this, "Error al agregar código de barras: " + e, "Error", JOptionPane.ERROR_MESSAGE);
//        }
//    }
    private BufferedImage generateQRCode(String texto) {
        try {
            int ancho = 300;
            int altoQR = 300;
            int altoTexto = 45;

            QRCodeWriter qrWriter = new QRCodeWriter();
            Hashtable<EncodeHintType, Object> hints = new Hashtable<>();
            hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
            hints.put(EncodeHintType.MARGIN, 1);

            BitMatrix matrix = qrWriter.encode(texto, BarcodeFormat.QR_CODE, ancho, altoQR, hints);
            BufferedImage imagen = new BufferedImage(ancho, altoQR + altoTexto, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = imagen.createGraphics();

            g.setColor(Color.WHITE);
            g.fillRect(0, 0, ancho, altoQR + altoTexto);

            for (int y = 0; y < altoQR; y++) {
                for (int x = 0; x < ancho; x++) {
                    g.setColor(matrix.get(x, y) ? Color.BLACK : Color.WHITE);
                    g.fillRect(x, y, 1, 1);
                }
            }

            g.setColor(Color.BLACK);
            g.setFont(new Font("Arial", Font.BOLD, 20));
            FontMetrics fm = g.getFontMetrics();
            int xTexto = (ancho - fm.stringWidth(texto)) / 2;
            g.drawString(texto, Math.max(0, xTexto), altoQR + 30);
            g.dispose();

            return imagen;
        } catch (WriterException ex) {
            Logger.getLogger(Disenio1.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private void agregarCodigoBarras(File archivo, String id, technology.tabula.Rectangle rec) {
        File archivoTemporal = new File(archivo.getParent(), archivo.getName().replace(".pdf", "_temp.pdf"));
        File tempQR = null;

        try {
            BufferedImage qrImage = generateQRCode(id);
            tempQR = File.createTempFile("qrcode", ".png");
            javax.imageio.ImageIO.write(qrImage, "png", tempQR);

            PdfReader reader = new PdfReader(archivo);
            PdfWriter writer = new PdfWriter(archivoTemporal);
            PdfDocument pdfDoc = new PdfDocument(reader, writer);
            Document doc = new Document(pdfDoc);

            com.itextpdf.kernel.pdf.PdfPage page = pdfDoc.getPage(1);
            com.itextpdf.kernel.geom.Rectangle pageSize = page.getPageSize();

            float pageHeight = pageSize.getHeight();
            float y = (float) (pageHeight - rec.x) - rec.width;

            ImageData imageData = ImageDataFactory.create(tempQR.getAbsolutePath());
            com.itextpdf.layout.element.Image im = new com.itextpdf.layout.element.Image(imageData);
            im.scaleToFit(rec.width, rec.width);
            im.setFixedPosition(rec.y, y);

            doc.add(im);
            doc.close();
            tempQR.delete();

            if (archivo.delete()) {
                archivoTemporal.renameTo(archivo);
            }

        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error al agregar código QR: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            if (tempQR != null && tempQR.exists()) {
                tempQR.delete();
            }
        }

    }

    private String limpiarNombreArchivo(String nombre) {
        nombre = nombre.replace("\\", "_");
        nombre = nombre.replace("/", "_");
        nombre = nombre.replace(":", "_");
        nombre = nombre.replace("*", "_");
        nombre = nombre.replace("?", "_");
        nombre = nombre.replace("\"", "_");
        nombre = nombre.replace("<", "_");
        nombre = nombre.replace(">", "_");
        nombre = nombre.replace("|", "_");

        return nombre.trim();
    }

    public List<PlanoDetectado> convertirPdf(File selectedFile, String path, technology.tabula.Rectangle dim) {
        List<PlanoDetectado> planos = new ArrayList<>();
        try {
            PdfReader reader = new PdfReader(selectedFile);
            PdfDocument pdfDoc = new PdfDocument(reader);
            int numeroPaginas = pdfDoc.getNumberOfPages();
            String numeroParte = "";
            for (int pagina = 1; pagina <= numeroPaginas; pagina++) {
                if (numeroParte.isEmpty()) {
                    numeroParte = txtProyecto.getText().trim();
                }
                int consecutivo = pagina;
                PlanoDetectado plano = new PlanoDetectado(selectedFile, pagina, numeroParte, consecutivo);
                String id = plano.getId();
                String nombreArchivo = limpiarNombreArchivo(id);
                File archivoSalida = new File(path, nombreArchivo + ".pdf");
                int cont = 1;
                while (archivoSalida.exists()) {
                    archivoSalida = new File(path, nombreArchivo + " (" + cont + ").pdf");
                    cont++;
                }
                crearPdfPagina(selectedFile, pagina, archivoSalida, id, dim);
                plano.setArchivoGenerado(archivoSalida.getAbsolutePath());
                planos.add(plano);
            }
            pdfDoc.close();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error al convertir PDF: " + e, "Error", JOptionPane.ERROR_MESSAGE);
        }
        return planos;
    }

    private List<String[]> obtenerTabla(File file) {
        List<String[]> tabla = new ArrayList<>();
        try (PDDocument document = PDDocument.load(file)) {
            ObjectExtractor extractor = new ObjectExtractor(document);
            PageIterator pages = extractor.extract();
            Page page = pages.next();
            String[] bom = templete.getBom().split(",");
            technology.tabula.Rectangle area = new technology.tabula.Rectangle(Integer.parseInt(bom[1]), Integer.parseInt(bom[0]), Integer.parseInt(bom[2]), Integer.parseInt(bom[3]));
            Page pageArea = page.getArea(area);
            SpreadsheetExtractionAlgorithm algorithm = new SpreadsheetExtractionAlgorithm();
            List<Table> tables = algorithm.extract(pageArea);
            for (Table table : tables) {
                for (List<RectangularTextContainer> row : table.getRows()) {
                    List<String> columnas = new ArrayList<>();
                    for (RectangularTextContainer cell : row) {
                        String texto = cell.getText().replace("\r", " ").replace("\n", " ").trim();
                        columnas.add(texto);
                    }
                    if (!columnas.isEmpty() && columnas.stream().anyMatch(s -> !s.isBlank())) {
                        tabla.add(columnas.toArray(new String[0]));
                    }
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al obtener tabla: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        return tabla;
    }

    private void cargarJTable(JTable jTable, List<String[]> tabla) {
        if (tabla == null || tabla.isEmpty()) {
            jTable.setModel(new DefaultTableModel());
            return;
        }
        int filaEncabezado = -1;
        for (int i = 0; i < tabla.size(); i++) {
            String fila = String.join(" ", tabla.get(i)).toUpperCase();
            if (fila.contains("ELEMENTO") || fila.contains("PIEZA")
                    || fila.contains("DESCRIPCI") || fila.contains("CANTIDAD")) {
                filaEncabezado = i;
                break;
            }
        }
        if (filaEncabezado == -1) {
            JOptionPane.showMessageDialog(this, "No se encontraron los encabezados de la tabla en el PDF.");
            return;
        }
        String[] encabezados = tabla.get(filaEncabezado);
        DefaultTableModel modelo = new DefaultTableModel(encabezados, 0);
        for (int i = filaEncabezado + 1; i < tabla.size(); i++) {
            String[] fila = tabla.get(i);
            Object[] datos = new Object[encabezados.length];
            for (int j = 0; j < datos.length; j++) {
                datos[j] = j < fila.length ? fila[j] : "";
            }
            modelo.addRow(datos);
        }
        JPopupMenu menu = new JPopupMenu();
        JMenuItem item = new JMenuItem("Eliminar articulo(s) de la lista      ");
        item.addActionListener((e) -> {
            for (int i = jTable.getSelectedRows().length - 1; i >= 0; i--) {
                modelo.removeRow(jTable.getSelectedRows()[i]);
            }
        });
        item.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/eliminar (1).png")));
        menu.add(item);
        jTable.setComponentPopupMenu(menu);
        jTable.setModel(modelo);
    }

    public void setBorde() {
        jPanel10.setDropTarget(new DropTarget(jPanel10, DnDConstants.ACTION_COPY, new DropTargetAdapter() {
            @Override
            public void dragEnter(DropTargetDragEvent dtde) {
                jPanel10.setBorder(BorderFactory.createDashedBorder(Color.BLACK, 2, 2, 2, false));
            }

            @Override
            public void dragExit(DropTargetEvent dte) {
                jPanel10.setBorder(null);
            }

            @Override
            public void drop(DropTargetDropEvent dtde) {
                JFrame f = (JFrame) JOptionPane.getFrameForComponent(Disenio1.this);
                jPanel10.setBorder(null);
                if (txtProyecto.isEnabled()) {
                    JOptionPane.showMessageDialog(null, "Debes seleccionar un proyecto", "Advertencia", JOptionPane.WARNING_MESSAGE);
                    txtProyecto.requestFocusInWindow();
                } else {
                    limpiarBotones();
                    try {
                        Transferable transferable = dtde.getTransferable();
                        List<String[]> listaBOM = null;
                        if (transferable.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
                            dtde.acceptDrop(DnDConstants.ACTION_COPY);
                            List<File> files = (List<File>) transferable.getTransferData(DataFlavor.javaFileListFlavor);
                            String direccion = getDirectorio(txtProyecto.getText());
                            String dimen[] = templete.getCodigo().split(",");
                            technology.tabula.Rectangle dim = new technology.tabula.Rectangle(Integer.parseInt(dimen[0]), Integer.parseInt(dimen[1]),
                                    Integer.parseInt(dimen[2]), Integer.parseInt(dimen[3]));
                            int cont = 0;
                            for (File file : files) {
                                if (!file.getName().toLowerCase().endsWith(".pdf")) {
                                    continue;
                                }
                                cont++;
//                                int opc = JOptionPane.showConfirmDialog(f, "Si tu pdf contiene contiene BOM de materiales, presiona OK");
//                                if (opc == JOptionPane.OK_OPTION) {
//                                    SeleccionarUbicaciones sel = new SeleccionarUbicaciones(f, true);
//                                    sel.setLocationRelativeTo(f);
//                                    sel.lblCoordenadas1.setText("seleccionar BOM: [No. Parte] [Descripcion] [Cantidad]");
//                                    sel.tabla = true;
//                                    sel.agregarPanel(new File(file.getAbsolutePath()));
//                                    sel.setVisible(true);
//                                    listaBOM = sel.tablaBOM;
//                                }
                                List<String[]> tablas = obtenerTabla(file);
                                listaBOM = tablas;
                                if (listaBOM != null) {
                                    btnBOM.setEnabled(true);
                                    btnBOM.setText("Enviar BOM a requisiciones");
                                }

                                List<PlanoDetectado> planos = convertirPdf(file, direccion, dim);
                                archivos = new File[planos.size()];
                                botones = new JButton[planos.size()];
                                int i = 0;
                                for (PlanoDetectado plano : planos) {
                                    File archivoGenerado = new File(plano.getArchivoGenerado());
                                    archivos[i] = (archivoGenerado);
                                    JButton boton = new JButton();
                                    String name = plano.getNumeroParte() + " - " + i;
                                    crearBoton(boton, plano.getId(), name);
                                    boton.setForeground(new Color(0, 100, 255));
                                    botones[i] = (boton);
                                    jPanel10.add(botones[i]);
                                    i++;
                                }
                            }

                            jPanel10.revalidate();
                            jPanel10.repaint();

                            final List<String[]> tablas = listaBOM;
                            Thread hilo = new Thread(() -> {
                                iniciar();
                                EditarTablaBOM edit = new EditarTablaBOM(f, true);
                                cargarJTable(edit.jTable1, tablas);
                                edit.setLocationRelativeTo(f);
                                boolean avanzar = edit.getTabla();
                                JTable tab = null;
                                if (avanzar) {
                                    tab = edit.jTable1;
                                }

                                dtde.dropComplete(true);
                                if (tab != null && avanzar) {
                                    dispose();
                                    requisicionDeCompra req = new requisicionDeCompra(numEmpleado, "");
                                    inicio.verFrame(req);
                                    req.agregarElementosTabla(tab, txtProyecto.getText());
                                }
                            });
                            hilo.start();
                        } else {
                            dtde.rejectDrop();
                        }
                    } catch (UnsupportedFlavorException | IOException e) {
                        JOptionPane.showMessageDialog(null, "Error: " + e, "Error", JOptionPane.ERROR_MESSAGE);
                        dtde.rejectDrop();
                    }
                }
            }
        }));
    }

    public void addPro() {
        try {
            Connection con;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            au = new TextAutoCompleter(txtProyecto);
            String sql = "select Proyecto, Id from Proyectos order by Id desc";
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                au.addItem(rs.getString("Proyecto"));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "ERROR: " + e, "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void limpiarBotones() {
        jPanel10.removeAll();
        revalidate();
        repaint();
    }

    public String getDepa(String numEmpleado, Connection con) throws SQLException {
        String sql = "select * from registroempleados where NumEmpleado like '" + numEmpleado + "' order by id desc";
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(sql);
        while (rs.next()) {
            return rs.getString("Puesto");
        }
        return null;
    }
    
    public static String calcular(String fechaInicio, String fechaFinal, String tiempoCronometro) {
        DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime inicio = LocalDateTime.parse(fechaInicio, formatoFecha);
        LocalDateTime fin = LocalDateTime.parse(fechaFinal, formatoFecha);
        Duration diferencia = Duration.between(inicio, fin);
        String[] partes = tiempoCronometro.split(":");
        long horas = Long.parseLong(partes[0]);
        long minutos = Long.parseLong(partes[1]);
        long segundos = Long.parseLong(partes[2]);
        Duration cronometro = Duration.ofHours(horas).plusMinutes(minutos).plusSeconds(segundos);
        Duration total = diferencia.plus(cronometro);
        long totalSegundos = total.getSeconds();
        long h = totalSegundos / 3600;
        long m = (totalSegundos % 3600) / 60;
        long s = totalSegundos % 60;
        return String.format("%02d:%02d:%02d", h, m, s);
    }

    public String buscarFecha(Connection con, String proyecto, String fin) throws SQLException {
        String sql = "select * from ordentrabajo where proyecto like '" + proyecto + "'";
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(sql);
        String fechaInicio = "";
        String tiempo = "";
        while (rs.next()) {
            fechaInicio = rs.getString("inicio");
            tiempo = rs.getString("tiempo");
        }
        return calcular(fechaInicio, fin, tiempo);
    }
    
    public String cerrarFechaTermino(String id, Connection con) throws SQLException {
        String sql = "update ordentrabajo set termino = ?, estado = ?, tiempo = ? where proyecto = ? and responsable = ?";
        PreparedStatement pst = con.prepareStatement(sql);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        
        pst.setString(1, sdf.format(new Date()));
        pst.setString(2, "Cerrado");
        pst.setString(3, buscarFecha(con, id, sdf.format(new Date())));
        pst.setString(4, id);
        pst.setString(5, this.numEmpleado);

        int n = pst.executeUpdate();

        if (n < 0) {
            JOptionPane.showMessageDialog(this, "Error al cerrar tiempo", "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
        return sdf.format(new Date());
    }

    public final void cerrarFechaCalendario(String proyecto, Connection con) throws SQLException {
        String depa = "DISEÑO";
        String sql = "update agenda set Estatus = ?, EmpleadoFin = ?, FechaTermino = ? where Proyecto = ? and Departamento = ?";
        PreparedStatement pst = con.prepareStatement(sql);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String fecha = sdf.format(new Date());

        pst.setString(1, "Terminado");
        pst.setString(2, inicio.lblId.getText());
        pst.setString(3, fecha);
        pst.setString(4, txtProyecto.getText());
        pst.setString(5, depa);

        int in = pst.executeUpdate();

        if (in < 1) {
            sql = "insert into agenda (Estatus , EmpleadoFin, FechaTermino, Proyecto, Departamento) values(?,?,?,?,?)";
            pst = con.prepareStatement(sql);

            pst.setString(1, "Terminado");
            pst.setString(2, inicio.lblId.getText());
            pst.setString(3, fecha);
            pst.setString(4, txtProyecto.getText());
            pst.setString(5, depa);

            in = pst.executeUpdate();

            if (in < 1) {
                JOptionPane.showMessageDialog(this, "Error al generar fecha", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void subirPlanos(File archivo) {
        try {
            Connection con = new Conexion().getConnection();
            Statement st2 = con.createStatement();

            String sql2 = "select Plano,Revision from Planos where Plano like '" + txtParte.getText() + "'";
            ResultSet rs2 = st2.executeQuery(sql2);
            String plan = "";
            String revision = "";
            while (rs2.next()) {
                plan = rs2.getString("Plano");
                revision = rs2.getString("Revision");
            }
            boolean band1 = true;
            int reem;
            if (plan != null) {
                if (plan.equals("")) {
                    band1 = true;
                } else {
                    if (revision == null ? txtRevision.getText() == null : revision.equals(txtRevision.getText())) {
                        reem = JOptionPane.showConfirmDialog(this, "ESTE PLANO YA SE ENCUENTRA EN LA BD, ¿DESEAS REEMPLAZARLO?");
                        if (reem == 0) {
                            band1 = false;
                        }
                    } else {
                        band1 = true;
                    }
                }
            } else {
                band1 = true;
            }
            byte[] pe = null;
            if (archivo == null) {

            } else {
                pe = new byte[(int) archivo.length()];
                try {

                    InputStream input = new FileInputStream(archivo);
                    input.read(pe);
                } catch (IOException e) {

                }
            }
            if (band1) {
                //--------------------------------1        2          3     4           5           6           7         8     9    10      11           12         13        14          15        1 6       17       18        19         20                             
                String sql = "insert into Planos (Plano, Proyecto, Estado, Prioridad, Cantidad, Revision, Descripcion, Material, Dureza, NoEnsamble, Tratamiento) values(?,?,?,?,?,?,?,?,?,?,?)";
//                if (empresa.equals("align")) {
//                    sql = "insert into Planos (Plano, Proyecto, Estado, Prioridad, Cantidad, Integracion, Fresadora, Torno, "
//                            + "Cnc, Mazak, Rectificado, Soldadura, Ensamble, Revision, Descripcion, Material, Dureza, Maquina, "
//                            + "NoEnsamble, Tratamiento, Dimension, PiezaReal) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
//                }
                PreparedStatement pst = con.prepareStatement(sql);

                String sql3 = "insert into pdfplanos (Plano, Pdf) values(?,?)";
                PreparedStatement pst3 = con.prepareStatement(sql3);

                pst.setString(1, txtParte.getText());
                pst.setString(2, txtProyecto.getText());
                pst.setString(3, "");
                pst.setString(4, "0");
                pst.setString(5, txtCantidad.getText().trim());
                pst.setString(6, txtRevision.getText());
                pst.setString(7, txtDescripcion.getText());
                pst.setString(8, txtMaterial.getText());
                pst.setString(9, txtDureza.getText());
                pst.setString(10, txtEnsamble.getText());
                pst.setString(11, txtTratamiento.getText());

                pst3.setString(1, txtParte.getText());
                pst3.setBytes(2, pe);

                int n1 = pst3.executeUpdate();

                if (n1 <= 0) {
                    JOptionPane.showMessageDialog(this, "NO SE EL PDF", "ERROR", JOptionPane.ERROR_MESSAGE);
                }

                int n = pst.executeUpdate();
                if (n <= 0) {
                    JOptionPane.showMessageDialog(this, "NO SE GUARDARON LOS DATOS");
                } else {
                    revisarPlanos rev = new revisarPlanos();
                    rev.actualizarPlanos(con, txtParte.getText(), "CORTES");
                }

            } else {
                String sql = "update Planos set Plano = ?, Proyecto = ?, Estado = ?, Cantidad = ?"
                        + ", Revision = ?, Descripcion = ?, Material = ?, Dureza = ?, "
                        + "NoEnsamble = ?, Tratamiento = ? where Plano = ?";
                PreparedStatement pst = con.prepareStatement(sql);

                String sql3 = "update pdfplanos set Pdf = ? where Plano = ?";
                PreparedStatement pst3 = con.prepareStatement(sql3);

                pst.setString(1, txtParte.getText());
                pst.setString(2, txtProyecto.getText());
                pst.setString(3, "CORTES");
                pst.setString(4, txtCantidad.getText());
                pst.setString(5, txtRevision.getText());
                pst.setString(6, txtDescripcion.getText());
                pst.setString(7, txtMaterial.getText());
                pst.setString(8, txtDureza.getText());
                pst.setString(9, txtEnsamble.getText());
                pst.setString(10, txtTratamiento.getText());
                pst.setString(11, txtParte.getText());

                int n = pst.executeUpdate();

                pst3.setBytes(1, pe);
                pst3.setString(2, txtParte.getText());

                int n1 = pst3.executeUpdate();

                if (n1 < 1) {
                    JOptionPane.showMessageDialog(this, "EL PDF NO SE SUBIO CORRECTAMENTE", "ERROR", JOptionPane.ERROR_MESSAGE);
                } else {
                    revisarPlanos rev = new revisarPlanos();
                    rev.actualizarPlanos(con, txtParte.getText(), "CORTES");
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "ERROR AL GUARDAR DATOS: " + e, "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void iniciar() {
        int opc = 0;
        int cont = 0;
        for (int i = 0; i < botones.length; i++) {
            if (opc == 0) {
                if (!botones[i].getForeground().equals(new Color(210, 210, 210))) {
                    try {
                        botones[i].setForeground(new Color(0, 165, 255));
                        verPlano(archivos[i]);
                        getTempletes(archivos[i]);
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(this, "ERROR: " + e);
                    }
                    botones[i].setForeground(new Color(0, 165, 255));

                    try {
                        double cant = Double.parseDouble(txtCantidad.getText().trim());
                    } catch (Exception e) {
                        boolean band = true;
                        do {
                            JFrame f = (JFrame) JOptionPane.getFrameForComponent(this);
                            DatosHoras dat = new DatosHoras(f, true);
                            dat.setLocationRelativeTo(f);
                            String d = dat.getCantidad();
                            if (!d.equals("")) {
                                band = false;
                                txtCantidad.setText(d);
                            }
                        } while (band);
                    }
                    botones[i].setForeground(new Color(210, 210, 210));
                    txtParte.setText(botones[i].getText());
                    subirPlanos(archivos[i]);
                    cont++;
                    txtRevision.setText("");
                    txtParte.setText("");
                    txtDescripcion.setText("");
                    txtMaterial.setText("");
                    txtDureza.setText("");
                    txtEnsamble.setText("");
                    txtTratamiento.setText("");
                    txtCantidad.setText("");
                }
            }
        }
        if (cont > 0) {
            try {
                Connection con = new Conexion().getConnection();
                cerrarFechaCalendario(txtProyecto.getText(), con);
                cerrarFechaTermino(txtProyecto.getText(), con);
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Error al subir fecha: " + e, "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

    }

    public JButton crearBoton(JButton boton, String texto, String name) {
        boton.setBackground(new java.awt.Color(255, 255, 255));
        boton.setFont(new java.awt.Font("Roboto", 1, 12));
        boton.setForeground(new java.awt.Color(0, 0, 0));
        insertarImagen(ruta, boton);
        boton.setText(texto);
        boton.setBorder(null);
        boton.setName(name);
        boton.setBorderPainted(false);
        boton.setContentAreaFilled(false);
        boton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        boton.setFocusPainted(false);
        boton.setFocusable(false);
        boton.addActionListener(this);
        boton.addMouseListener(this);
        return boton;
    }

    public String getEmpresa(File archivo) {
        String empresa = "";
        if (archivo != null) {
            String FILENAME = archivo.getAbsolutePath();
            int band = 0;
            int band2 = 0;
            int band3 = 0;

            try {
                PDDocument pd;
                pd = PDDocument.load(new File(FILENAME));
                int totalPages = pd.getNumberOfPages();
                ObjectExtractor oe = new ObjectExtractor(pd);
                SpreadsheetExtractionAlgorithm sea = new SpreadsheetExtractionAlgorithm();
                Page page = oe.extract(1);
                // extract text from the table after detecting
                List<Table> table = null;
                try {
                    table = sea.extract(page);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "ERROR AL VER PDF " + archivo.getName(), "ERROR", JOptionPane.ERROR_MESSAGE);
                    Logger.getLogger(Disenio1.class.getName()).log(Level.SEVERE, null, ex);
                }
                if (table != null) {
                    for (Table tables : table) {
                        List<List<RectangularTextContainer>> rows = tables.getRows();

                        for (int i = 0; i < rows.size(); i++) {

                            List<RectangularTextContainer> cells = rows.get(i);

                            for (int j = 0; j < cells.size(); j++) {
                                if (cells.get(j).getText().equals("DESIGN")
                                        || cells.get(j).getText().contains("QTY")
                                        || cells.get(j).getText().contains("SCALE") || cells.get(j).getText().contains("WEIGHT:")) {
                                    band++;
                                } else if (cells.get(j).getText().equals("   ")
                                        || cells.get(j).getText().contains("C*")
                                        || cells.get(j).getText().contains("D*")
                                        || cells.get(j).getText().contains("E*")
                                        || cells.get(j).getText().contains("F*")) {
                                    if (cells.get(j).getText().length() < 20) {
                                        band2++;
                                    }
                                } else if (cells.get(j).getText().equals("   ")
                                        || cells.get(j).getText().contains("Ensamblaje:")
                                        || cells.get(j).getText().contains("Revision:")
                                        || cells.get(j).getText().contains("No. de Parte:")
                                        || cells.get(j).getText().contains("Rectificado:")) {
                                    band3++;
                                }
                            }
                        }

                    }
                    if (band == 4) {
                        empresa = "ALIGN";
                    } else if (band2 == 4) {
                        empresa = "3I";
                    } else if (band3 == 4) {
                        empresa = "DUROL";
                    } else {
                        empresa = "NULL";
                    }
                }
                pd.close();
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "ERROR AL SUBIR PLANO " + archivo.getName());
                Logger.getLogger(Disenio1.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return empresa;
    }

    public final void getTempletes(File archivo) {
        String valores[] = templete.getValores();
        JTextField textos[] = {txtRevision, txtParte, txtDescripcion, txtMaterial, txtDureza, txtEnsamble, txtTratamiento, txtCantidad};
        for (int i = 0; i < textos.length; i++) {
            if (!valores[i].equals("")) {
                try {
                    try (PDDocument doc = PDDocument.load(archivo)) {
                        PDFTextStripperByArea textStripper = new PDFTextStripperByArea();
                        int x, y, h, a;
                        String val[] = valores[i].split(",");
                        x = Integer.parseInt(val[0]);
                        y = Integer.parseInt(val[1]);
                        h = Integer.parseInt(val[2]);
                        a = Integer.parseInt(val[3]);
                        Rectangle rect = new Rectangle(x, y, h, a);
                        textStripper.addRegion("myRegion" + i, rect);
                        PDPage page = doc.getPage(0);
                        textStripper.extractRegions(page);
                        String extractedText = textStripper.getTextForRegion("myRegion" + i);
                        String remplazo = extractedText.replace("*", "");
                        textos[i].setText(remplazo);
                    }
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(this, "Error al ver texto:" + ex, "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    public void acomodar() {
        if (ruta_archivo.trim().length() != 0) {
//            this.numImg += 1;
            if (paginas == totalp) {
                paginas = 1;
            } else {
                paginas = paginas + 1;
            }
            if (this.numImg >= this.ListaComponente.size()) {
                this.numImg = 0;
            }
            ArchivosVO pi = new ArchivosVO();
            for (int i = 0; i < ListaComponente.size(); i++) {
                pi = ListaComponente.get(numImg);
                break;
            }
            totalp = ListaComponente.size(); 
           this.img.setImagen(pi.getArchivos());
            this.img.disminuir();
            this.img.disminuir();
            this.img.disminuir();
            this.img.disminuir();
            this.img.disminuir();
            this.img.disminuir();
        } else {
//            JOptionPane.showMessageDialog(null, "Abrir un documento PDF primero");
        }
    }

    public void limpiarPantalla() {
        acomodar();
        revalidate();
        repaint();
    }

    public void abrir_pdf(String url) {
        this.numImg = 0;
        ruta_archivo = url;
        this.ListaComponente = pn.leerPDF(url);
        for (int i = 0; i < ListaComponente.size(); i++) {
            pl = ListaComponente.get(i);;
            this.img.setImagen(pl.getArchivos());
        }
        paginas = 1;
        totalp = ListaComponente.size();
        ArchivosVO pi = new ArchivosVO();
        pi = ListaComponente.get(0);
        this.img.setImagen(pi.getArchivos());
    }

    public void verPlano(File archi) {
        byte[] ruta = null;
        try {
            ruta = Files.readAllBytes(archi.toPath());
        } catch (IOException ex) {
            Logger.getLogger(Disenio1.class.getName()).log(Level.SEVERE, null, ex);
        }

        limpiarPantalla();
        acomodar();
        if (archi == null) {
            JOptionPane.showMessageDialog(this, "NO EXISTE ESTE PLANO", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
        } else {
            File n = archi;
            try {
                BufferedOutputStream bs = null;
                FileOutputStream fs = new FileOutputStream(n);
                bs = new BufferedOutputStream(fs);
                bs.write(ruta);

                bs.close();
                bs = null;

            } catch (IOException e) {
                n = new File("/Imagenes/new2.pdf");
                try {
                    BufferedOutputStream bs = null;
                    FileOutputStream fs = new FileOutputStream(n);
                    bs = new BufferedOutputStream(fs);
                    bs.write(ruta);

                    bs.close();
                    bs = null;

                } catch (IOException e2) {
                    JOptionPane.showMessageDialog(this, "ERROR: " + e2, "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
                }
            }

            abrir_pdf(n.toString());

            limpiarPantalla();

            acomodar();
        }
    }

    public boolean existeProyecto(String proyecto) {
        boolean band = false;
        try {
            Connection con;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            String sql = "select Proyecto from proyectos where Proyecto like '" + proyecto + "'";
            ResultSet rs = st.executeQuery(sql);
            String proy = null;
            while (rs.next()) {
                proy = rs.getString("Proyecto");
            }
            if (proy != null) {
                band = true;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "ERROR: " + e, "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        return band;
    }

    public final void insertarImagen(String url, JLabel lbl) {
        ImageIcon icon = new ImageIcon(url);
        java.awt.Image imagen = icon.getImage();
        java.awt.Image imagenEscalada = imagen.getScaledInstance(60, 60, java.awt.Image.SCALE_SMOOTH);

        lbl.setIcon(new ImageIcon(imagenEscalada));
    }

    public Disenio1(TempleteDiseno templete, Inicio1 inicio, String url, String numEmpleado) {
        initComponents();
        this.inicio = inicio;
        this.templete = templete;
        lblLogo.setIcon(frameIcon);
        insertarImagen(url, lblLogo);
        this.ruta = url;
        this.numEmpleado = numEmpleado;
        addPro();
        setBorde();
        jScrollPane2.getVerticalScrollBar().setUnitIncrement(15);
        jScrollPane2.getHorizontalScrollBar().setUnitIncrement(15);
        ((javax.swing.plaf.basic.BasicInternalFrameUI) this.getUI()).setNorthPane(null);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        botonesPlanos = new javax.swing.JPopupMenu();
        btn3i = new javax.swing.JMenuItem();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel10 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        panelCard = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        img = new mivisorpdf.CuadroImagen();
        jPanel3 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        txtProyecto = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        txtRevision = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        txtParte = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        txtDescripcion = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        txtMaterial = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        txtDureza = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        txtEnsamble = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        txtTratamiento = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        txtCantidad = new javax.swing.JTextField();
        jPanel8 = new javax.swing.JPanel();
        btnBOM = new javax.swing.JButton();
        jPanel13 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel26 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        btnSalir = new javax.swing.JPanel();
        lblX = new javax.swing.JLabel();
        jPanel14 = new javax.swing.JPanel();
        panel3i = new javax.swing.JPanel();
        lblLogo = new javax.swing.JLabel();
        jPanel16 = new javax.swing.JPanel();
        btnSalir1 = new javax.swing.JPanel();
        lblAtras = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem2 = new javax.swing.JMenuItem();

        btn3i.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        btn3i.setText("Agregar plano 3i                                          ");
        botonesPlanos.add(btn3i);

        setBorder(null);
        getContentPane().setLayout(new java.awt.BorderLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setForeground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new java.awt.BorderLayout(15, 15));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new java.awt.BorderLayout(15, 0));

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setPreferredSize(new java.awt.Dimension(370, 80));
        jPanel4.setLayout(new java.awt.BorderLayout());

        jScrollPane1.setBorder(null);
        jScrollPane1.setMinimumSize(new java.awt.Dimension(14, 40));
        jScrollPane1.setPreferredSize(new java.awt.Dimension(1345, 30));

        jPanel10.setBackground(new java.awt.Color(255, 255, 255));
        jPanel10.setMinimumSize(new java.awt.Dimension(10, 40));
        jPanel10.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jPanel10MouseExited(evt);
            }
        });
        jPanel10.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 15, 5));
        jScrollPane1.setViewportView(jPanel10);

        jPanel4.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jPanel2.add(jPanel4, java.awt.BorderLayout.PAGE_START);

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));
        jPanel9.setLayout(new java.awt.GridLayout(1, 2, 10, 0));

        panelCard.setBackground(new java.awt.Color(255, 255, 255));
        panelCard.setLayout(new java.awt.BorderLayout());

        jScrollPane2.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(51, 51, 51), 2, true));

        img.setBackground(new java.awt.Color(255, 255, 255));
        img.setName("img"); // NOI18N
        img.setPreferredSize(new java.awt.Dimension(210, 297));
        img.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                imgMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                imgMouseEntered(evt);
            }
        });
        img.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                imgKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                imgKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout imgLayout = new javax.swing.GroupLayout(img);
        img.setLayout(imgLayout);
        imgLayout.setHorizontalGroup(
            imgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 560, Short.MAX_VALUE)
        );
        imgLayout.setVerticalGroup(
            imgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 613, Short.MAX_VALUE)
        );

        jScrollPane2.setViewportView(img);

        panelCard.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        jPanel9.add(panelCard);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setLayout(new java.awt.BorderLayout());

        jPanel11.setBackground(new java.awt.Color(255, 255, 255));
        jPanel11.setLayout(new java.awt.GridLayout(20, 0, 0, 10));
        jPanel3.add(jPanel11, java.awt.BorderLayout.WEST);

        jPanel12.setBackground(new java.awt.Color(255, 255, 255));
        java.awt.GridBagLayout jPanel12Layout = new java.awt.GridBagLayout();
        jPanel12Layout.columnWeights = new double[] {0.0, 1.0};
        jPanel12Layout.rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0};
        jPanel12.setLayout(jPanel12Layout);

        jLabel3.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(51, 51, 51));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText("Proyecto:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        jPanel12.add(jLabel3, gridBagConstraints);

        txtProyecto.setBackground(new java.awt.Color(255, 255, 255));
        txtProyecto.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtProyecto.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtProyecto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtProyectoActionPerformed(evt);
            }
        });
        txtProyecto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtProyectoKeyTyped(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(4, 15, 4, 15);
        jPanel12.add(txtProyecto, gridBagConstraints);

        jLabel15.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(51, 51, 51));
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel15.setText("Revision: ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        jPanel12.add(jLabel15, gridBagConstraints);

        txtRevision.setBackground(new java.awt.Color(255, 255, 255));
        txtRevision.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtRevision.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtRevision.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtRevisionKeyTyped(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(4, 15, 4, 15);
        jPanel12.add(txtRevision, gridBagConstraints);

        jLabel16.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(51, 51, 51));
        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel16.setText("No. Parte:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        jPanel12.add(jLabel16, gridBagConstraints);

        txtParte.setBackground(new java.awt.Color(255, 255, 255));
        txtParte.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtParte.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtParte.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtParteKeyTyped(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(4, 15, 4, 15);
        jPanel12.add(txtParte, gridBagConstraints);

        jLabel18.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(51, 51, 51));
        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel18.setText("Descripcion: ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        jPanel12.add(jLabel18, gridBagConstraints);

        txtDescripcion.setBackground(new java.awt.Color(255, 255, 255));
        txtDescripcion.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtDescripcion.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtDescripcion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDescripcionKeyTyped(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(4, 15, 4, 15);
        jPanel12.add(txtDescripcion, gridBagConstraints);

        jLabel17.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(51, 51, 51));
        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel17.setText("Material: ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        jPanel12.add(jLabel17, gridBagConstraints);

        txtMaterial.setBackground(new java.awt.Color(255, 255, 255));
        txtMaterial.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtMaterial.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtMaterial.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtMaterialKeyTyped(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(4, 15, 4, 15);
        jPanel12.add(txtMaterial, gridBagConstraints);

        jLabel20.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(51, 51, 51));
        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel20.setText("Dureza: ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        jPanel12.add(jLabel20, gridBagConstraints);

        txtDureza.setBackground(new java.awt.Color(255, 255, 255));
        txtDureza.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtDureza.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtDureza.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDurezaKeyTyped(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(4, 15, 4, 15);
        jPanel12.add(txtDureza, gridBagConstraints);

        jLabel22.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(51, 51, 51));
        jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel22.setText("No. Ensamble: ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        jPanel12.add(jLabel22, gridBagConstraints);

        txtEnsamble.setBackground(new java.awt.Color(255, 255, 255));
        txtEnsamble.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtEnsamble.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtEnsamble.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtEnsambleKeyTyped(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(4, 15, 4, 15);
        jPanel12.add(txtEnsamble, gridBagConstraints);

        jLabel21.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(51, 51, 51));
        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel21.setText("Tratamiento:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        jPanel12.add(jLabel21, gridBagConstraints);

        txtTratamiento.setBackground(new java.awt.Color(255, 255, 255));
        txtTratamiento.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtTratamiento.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtTratamiento.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtTratamientoKeyTyped(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(4, 15, 4, 15);
        jPanel12.add(txtTratamiento, gridBagConstraints);

        jLabel24.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(51, 51, 51));
        jLabel24.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel24.setText("Cantidad: ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        jPanel12.add(jLabel24, gridBagConstraints);

        txtCantidad.setBackground(new java.awt.Color(255, 255, 255));
        txtCantidad.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtCantidad.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCantidad.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCantidadKeyTyped(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(4, 15, 4, 15);
        jPanel12.add(txtCantidad, gridBagConstraints);

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));

        btnBOM.setBackground(new java.awt.Color(255, 255, 255));
        btnBOM.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        btnBOM.setForeground(new java.awt.Color(51, 51, 51));
        btnBOM.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/BOM_16.png"))); // NOI18N
        btnBOM.setText("BOM detectado");
        btnBOM.setEnabled(false);
        jPanel8.add(btnBOM);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 9;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        jPanel12.add(jPanel8, gridBagConstraints);

        jPanel3.add(jPanel12, java.awt.BorderLayout.CENTER);

        jPanel13.setBackground(new java.awt.Color(255, 255, 255));

        jButton1.setBackground(new java.awt.Color(0, 102, 204));
        jButton1.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Cancelar plano");
        jPanel13.add(jButton1);

        jButton2.setBackground(new java.awt.Color(0, 102, 204));
        jButton2.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("Subir");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel13.add(jButton2);

        jPanel3.add(jPanel13, java.awt.BorderLayout.SOUTH);

        jPanel9.add(jPanel3);

        jPanel2.add(jPanel9, java.awt.BorderLayout.CENTER);

        jPanel1.add(jPanel2, java.awt.BorderLayout.CENTER);

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setLayout(new java.awt.BorderLayout());

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jLabel26.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(0, 102, 204));
        jLabel26.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel26.setText("Diseño");
        jPanel6.add(jLabel26);

        jPanel5.add(jPanel6, java.awt.BorderLayout.CENTER);

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));

        btnSalir.setBackground(new java.awt.Color(255, 255, 255));

        lblX.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
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

        jPanel14.setBackground(new java.awt.Color(255, 255, 255));
        jPanel14.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 20, 5));

        panel3i.setBackground(new java.awt.Color(255, 255, 255));
        panel3i.setLayout(new java.awt.BorderLayout());

        lblLogo.setFont(new java.awt.Font("French Script MT", 0, 60)); // NOI18N
        lblLogo.setForeground(new java.awt.Color(255, 102, 0));
        lblLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/diseño-3i.png"))); // NOI18N
        panel3i.add(lblLogo, java.awt.BorderLayout.WEST);

        jPanel14.add(panel3i);

        jPanel5.add(jPanel14, java.awt.BorderLayout.SOUTH);

        jPanel16.setBackground(new java.awt.Color(255, 255, 255));

        btnSalir1.setBackground(new java.awt.Color(255, 255, 255));

        lblAtras.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        lblAtras.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblAtras.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/atras_16.png"))); // NOI18N
        lblAtras.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblAtras.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblAtrasMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblAtrasMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblAtrasMouseExited(evt);
            }
        });
        btnSalir1.add(lblAtras);

        jPanel16.add(btnSalir1);

        jPanel5.add(jPanel16, java.awt.BorderLayout.WEST);

        jPanel1.add(jPanel5, java.awt.BorderLayout.PAGE_START);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        jMenu1.setText("File");

        jMenuItem1.setText("Dis");
        jMenuItem1.setEnabled(false);
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuItem3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/BOM_16.png"))); // NOI18N
        jMenuItem3.setText("Subir BOM                                  ");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem3);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");

        jMenuItem2.setText("Ver codigo de barras");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem2);

        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        pack();
    }// </editor-fold>//GEN-END:initComponents


    private void txtRevisionKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtRevisionKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtRevisionKeyTyped

    private void txtParteKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtParteKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtParteKeyTyped

    private void txtMaterialKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtMaterialKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMaterialKeyTyped

    private void txtDescripcionKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDescripcionKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDescripcionKeyTyped

    private void txtDurezaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDurezaKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDurezaKeyTyped

    private void txtEnsambleKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtEnsambleKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtEnsambleKeyTyped

    private void txtTratamientoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTratamientoKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTratamientoKeyTyped

    private void txtCantidadKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCantidadKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCantidadKeyTyped

    private void lblXMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblXMouseClicked
        dispose();
    }//GEN-LAST:event_lblXMouseClicked

    private void lblXMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblXMouseEntered
        btnSalir.setBackground(Color.red);
        lblX.setForeground(Color.white);
    }//GEN-LAST:event_lblXMouseEntered

    private void lblXMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblXMouseExited
        btnSalir.setBackground(Color.white);
        lblX.setForeground(Color.black);
    }//GEN-LAST:event_lblXMouseExited

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        dis dis = new dis();
        dis.setVisible(true);
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed

    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void imgMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_imgMouseClicked
        img.aumentar();
    }//GEN-LAST:event_imgMouseClicked

    private void imgMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_imgMouseEntered

    }//GEN-LAST:event_imgMouseEntered

    private void imgKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_imgKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_CONTROL) {
            ctrlPressed = true;
        }
    }//GEN-LAST:event_imgKeyPressed

    private void imgKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_imgKeyReleased
        if (evt.getKeyCode() == KeyEvent.VK_CONTROL) {
            ctrlPressed = true;
        }
    }//GEN-LAST:event_imgKeyReleased

    private void txtProyectoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtProyectoKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtProyectoKeyTyped

    private void txtProyectoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtProyectoActionPerformed
        if (existeProyecto(txtProyecto.getText())) {
            txtProyecto.setEnabled(false);
            txtProyecto.setForeground(Color.blue);
        }
    }//GEN-LAST:event_txtProyectoActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        JFrame f = (JFrame) JOptionPane.getFrameForComponent(this);
        subirBOM subir = new subirBOM(f, true);
        subir.setVisible(true);
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jPanel10MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel10MouseExited
        jPanel10.setBorder(null);
    }//GEN-LAST:event_jPanel10MouseExited

    private void lblAtrasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblAtrasMouseClicked
        InicioDiseño c = new InicioDiseño(inicio, inicio.lblId.getText());
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
    }//GEN-LAST:event_lblAtrasMouseClicked

    private void lblAtrasMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblAtrasMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_lblAtrasMouseEntered

    private void lblAtrasMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblAtrasMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_lblAtrasMouseExited

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        iniciar();
    }//GEN-LAST:event_jButton2ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPopupMenu botonesPlanos;
    private javax.swing.JMenuItem btn3i;
    private javax.swing.JButton btnBOM;
    private javax.swing.JPanel btnSalir;
    private javax.swing.JPanel btnSalir1;
    private mivisorpdf.CuadroImagen img;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel16;
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
    private javax.swing.JLabel lblAtras;
    public javax.swing.JLabel lblLogo;
    private javax.swing.JLabel lblX;
    private javax.swing.JPanel panel3i;
    private javax.swing.JPanel panelCard;
    private javax.swing.JTextField txtCantidad;
    private javax.swing.JTextField txtDescripcion;
    private javax.swing.JTextField txtDureza;
    private javax.swing.JTextField txtEnsamble;
    private javax.swing.JTextField txtMaterial;
    private javax.swing.JTextField txtParte;
    private javax.swing.JTextField txtProyecto;
    private javax.swing.JTextField txtRevision;
    private javax.swing.JTextField txtTratamiento;
    // End of variables declaration//GEN-END:variables

    @Override
    public void actionPerformed(ActionEvent e) {
//        if (datosHoras != null) {
//            if (e.getSource() == datosHoras.btnGuardar) {
//                if (datosHoras.d.getText().equals("")) {
//                    JOptionPane.showMessageDialog(this, "Debes llenar horas de Fresadora");
//                } else if (datosHoras.e.getText().equals("")) {
//                    JOptionPane.showMessageDialog(this, "Debes llenar horas de Torno");
//                } else if (datosHoras.f.getText().equals("")) {
//                    JOptionPane.showMessageDialog(this, "Debes llenar horas de Cnc");
//                } else if (datosHoras.s.getText().equals("")) {
//                    JOptionPane.showMessageDialog(this, "Debes llenar horas de Cantidad");
//                } else if (datosHoras.k.getText().equals("")) {
//                    JOptionPane.showMessageDialog(this, "Debes llenar la Revision");
//                } else {
//                    bandDatosHoras = true;
//                    txtCantidad.setText(datosHoras.s.getText());
//                    txtRevision.setText(datosHoras.k.getText());
//                    datosHoras.dispose();
//                }
//            }
//        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        for (int i = 0; i < botones.length; i++) {

            if (!botones[i].getForeground().equals(new Color(210, 210, 210))) {
                botones[i].setForeground(Color.black);
            }
            if (e.getSource() == botones[i]) {
                if (!botones[i].getForeground().equals(new Color(210, 210, 210))) {
                    txtRevision.setText("");
                    txtParte.setText("");
                    txtDescripcion.setText("");
                    txtMaterial.setText("");
                    txtDureza.setText("");
                    txtEnsamble.setText("");
                    txtTratamiento.setText("");
                    txtCantidad.setText("");
                    botones[i].setForeground(new Color(0, 165, 255));
                    seleccionado = i;
                    verPlano(archivos[i]);
                } else {
                    txtRevision.setText("");
                    txtParte.setText("");
                    txtDescripcion.setText("");
                    txtMaterial.setText("");
                    txtDureza.setText("");
                    txtEnsamble.setText("");
                    txtTratamiento.setText("");
                    txtCantidad.setText("");
                    botones[i].setForeground(new Color(0, 165, 255));
                    seleccionado = i;
                    verPlano(archivos[i]);
                }
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

}
