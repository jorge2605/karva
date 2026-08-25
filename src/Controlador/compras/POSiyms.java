package Controlador.compras;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPRow;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class POSiyms {

    private static final BaseColor AZUL = new BaseColor(155, 190, 220);
    private static final BaseColor BLANCO = new BaseColor(255, 255, 255);
    private static final BaseColor AZUL_OSCURO = new BaseColor(0, 78, 171);
    private static final BaseColor GRIS = new BaseColor(190, 190, 190);
    private static final DecimalFormat MONEDA = new DecimalFormat("$#,##0.00");

    public static void main(String[] args) {
        List<Producto> productos = new ArrayList<>();

        productos.add(new Producto(1,
                "F3SG4SRA032014",
                "GLC. ADVANCED, FINGER SAFE",
                "PZAS",
                1,
                new BigDecimal("15338.08")
        ));

        productos.add(new Producto(
                2,
                "F39JGR3KD",
                "ROOT PLUG RX 0.3 M",
                "PZAS",
                1,
                new BigDecimal("866.66")
        ));

        productos.add(new Producto(
                3,
                "F39JGR3KL",
                "ROOT PLUG CABLE TX 0.3M",
                "PZAS",
                1,
                new BigDecimal("721.43")
        ));

        productos.add(new Producto(
                4,
                "F39JG3AL",
                "ASB-SINGLE ENDED CABLE FOR TX",
                "PZAS",
                1,
                new BigDecimal("562.15")
        ));

        productos.add(new Producto(
                5,
                "F39JG3AD",
                "ASB-SINGLE ENDED CABLE FOR RX",
                "PZAS",
                1,
                new BigDecimal("688.65")
        ));

        productos.add(new Producto(
                6,
                "3211634",
                "D-PTTB 2,5 TAPA FINAL",
                "PZAS",
                25,
                new BigDecimal("9.86")
        ));

        productos.add(new Producto(
                7,
                "3210567",
                "PTTB 2,5 BORNA DE DOBLE PISO",
                "PZAS",
                50,
                new BigDecimal("46.99")
        ));

        productos.add(new Producto(
                8,
                "2966171",
                "PLC-RSC 24DC/21",
                "PZAS",
                1,
                new BigDecimal("146.19")
        ));
        productos.add(new Producto(
                9,
                "28589",
                "OTBVP6",
                "PZAS",
                2,
                new BigDecimal("2934.31")
        ));
        productos.add(new Producto(
                9,
                "28589",
                "OTBVP6",
                "PZAS",
                2,
                new BigDecimal("2934.31")
        ));
        productos.add(new Producto(
                9,
                "28589",
                "OTBVP6",
                "PZAS",
                2,
                new BigDecimal("2934.31")
        ));
        productos.add(new Producto(
                9,
                "28589",
                "OTBVP6",
                "PZAS",
                2,
                new BigDecimal("2934.31")
        ));
        productos.add(new Producto(
                9,
                "28589",
                "OTBVP6",
                "PZAS",
                2,
                new BigDecimal("2934.31")
        ));
        productos.add(new Producto(
                9,
                "28589",
                "OTBVP6",
                "PZAS",
                2,
                new BigDecimal("2934.31")
        ));
        productos.add(new Producto(
                9,
                "28589",
                "OTBVP6",
                "PZAS",
                2,
                new BigDecimal("2934.31")
        ));
        productos.add(new Producto(
                9,
                "28589",
                "OTBVP6",
                "PZAS",
                2,
                new BigDecimal("2934.31")
        ));
        productos.add(new Producto(
                9,
                "28589",
                "OTBVP6",
                "PZAS",
                2,
                new BigDecimal("2934.31")
        ));
        productos.add(new Producto(
                9,
                "28589",
                "OTBVP6",
                "PZAS",
                2,
                new BigDecimal("2934.31")
        ));
        productos.add(new Producto(
                9,
                "28589",
                "OTBVP6",
                "PZAS",
                2,
                new BigDecimal("2934.31")
        ));
        productos.add(new Producto(
                9,
                "28589",
                "OTBVP6",
                "PZAS",
                2,
                new BigDecimal("2934.31")
        ));
        productos.add(new Producto(
                9,
                "28589",
                "OTBVP6",
                "PZAS",
                2,
                new BigDecimal("2934.31")
        ));
        productos.add(new Producto(
                9,
                "28589",
                "OTBVP6",
                "PZAS",
                2,
                new BigDecimal("2934.31")
        ));
        productos.add(new Producto(
                9,
                "28589",
                "OTBVP6",
                "PZAS",
                2,
                new BigDecimal("2934.31")
        ));
        productos.add(new Producto(
                9,
                "28589",
                "OTBVP6",
                "PZAS",
                2,
                new BigDecimal("2934.31")
        ));
        productos.add(new Producto(
                9,
                "28589",
                "OTBVP6",
                "PZAS",
                2,
                new BigDecimal("2934.31")
        ));
        productos.add(new Producto(
                9,
                "28589",
                "OTBVP6",
                "PZAS",
                2,
                new BigDecimal("2934.31")
        ));
        productos.add(new Producto(
                9,
                "28589",
                "OTBVP6",
                "PZAS",
                2,
                new BigDecimal("2934.31")
        ));
        productos.add(new Producto(
                9,
                "28589",
                "OTBVP6",
                "PZAS",
                2,
                new BigDecimal("2934.31")
        ));
        productos.add(new Producto(
                9,
                "28589",
                "OTBVP6",
                "PZAS",
                2,
                new BigDecimal("2934.31")
        ));
        productos.add(new Producto(
                9,
                "28589",
                "OTBVP6",
                "PZAS",
                2,
                new BigDecimal("2934.31")
        ));
        productos.add(new Producto(
                9,
                "28589",
                "OTBVP6",
                "PZAS",
                2,
                new BigDecimal("2934.31")
        ));
        productos.add(new Producto(
                9,
                "28589",
                "OTBVP6",
                "PZAS",
                2,
                new BigDecimal("2934.31")
        ));
        productos.add(new Producto(
                9,
                "28589",
                "OTBVP6",
                "PZAS",
                2,
                new BigDecimal("2934.31")
        ));
        productos.add(new Producto(
                9,
                "28589",
                "OTBVP6",
                "PZAS",
                2,
                new BigDecimal("2934.31")
        ));
        productos.add(new Producto(
                9,
                "28589",
                "OTBVP6",
                "PZAS",
                2,
                new BigDecimal("2934.31")
        ));
        productos.add(new Producto(
                9,
                "28589",
                "OTBVP6",
                "PZAS",
                2,
                new BigDecimal("2934.31")
        ));
        productos.add(new Producto(
                9,
                "28589",
                "OTBVP6",
                "PZAS",
                2,
                new BigDecimal("2934.31")
        ));
        productos.add(new Producto(
                9,
                "28589",
                "OTBVP6",
                "PZAS",
                2,
                new BigDecimal("2934.31")
        ));

        try {
            generar("PO-00842.pdf", productos);
        } catch (Exception ex) {
            Logger.getLogger(POSiyms.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void generar(String ruta, List<Producto> productos) throws Exception {
        Document document = new Document(PageSize.LETTER, 20, 20, 20, 20);
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(ruta));
        writer.setPageEvent(new EncabezadoPDF());
        document.open();
//        crearEncabezado(document);
//        crearDatosCliente(document);
        crearTablaProductos(document, productos);
        crearTotales(document, productos);
        document.close();
    }

    private static void crearEncabezado(Document document) throws Exception {
        PdfPTable tabla = new PdfPTable(2);
        tabla.setWidthPercentage(100);
        tabla.setWidths(new float[]{35, 65});
        PdfPCell logo = new PdfPCell();
        Image imagen = Image.getInstance("C:\\Users\\Jorge Santacruz\\Documents\\Jorge\\Karva\\src\\Img\\siyms.png");
        imagen.scaleToFit(200, 110);
        logo.addElement(imagen);
        logo.setBorder(Rectangle.NO_BORDER);
        tabla.addCell(logo);

        PdfPCell empresa = new PdfPCell();
        empresa.setBorder(Rectangle.NO_BORDER);
        empresa.setHorizontalAlignment(
                Element.ALIGN_CENTER
        );
        Font nombreEmpresa = new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD);
        Font datos = new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD);

        Paragraph p = new Paragraph();
        p.add(new Phrase("SIYMS S.A.S. DE C.V.\n", nombreEmpresa));
        p.add(new Phrase("SIY1706239V4\n", datos));
        p.add(new Phrase("FRAY TOMAS GALLARDO #987\n", datos));
        p.add(new Phrase("COL. FRAY GARCIA DE SAN FRANCISCO\n", datos));
        p.add(new Phrase("C.P. 32575", datos));
        p.setAlignment(1);
        empresa.addElement(p);
        tabla.addCell(empresa);
        document.add(tabla);

        Font descripcion = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
        Paragraph actividad = new Paragraph("Fabricación de estructuras de modular, soldaduras industriales, "
                + "costuras industriales y todo tipo de maquinados de precisión.", descripcion);
        actividad.setAlignment(Element.ALIGN_CENTER);
        document.add(actividad);
        document.add(new Paragraph(" "));
    }

    private static void agregarCelda(PdfPTable tabla, String texto, BaseColor fondo, int alineacion) {
        Font fuente = new Font(Font.FontFamily.HELVETICA, 8, Font.NORMAL);
        PdfPCell celda = new PdfPCell(new Phrase(texto, fuente));
        celda.setBackgroundColor(fondo);
        celda.setHorizontalAlignment(alineacion);
        celda.setVerticalAlignment(Element.ALIGN_MIDDLE);
        celda.setPadding(4);
        if (band) {
            celda.setColspan(2);
            band = false;
        }
        if (texto.equals(" ") || texto.equals("Firma de comprador")) {
            celda.setBorder(0);
        }
        if (texto.equals("   ")) {
            celda.setBorder(0);
            celda.setBorderWidthBottom(0.5f);
        }
        tabla.addCell(celda);
    }
    static boolean band = false;

    private static void crearTablaProductos(Document document, List<Producto> productos) throws Exception {
        PdfPTable tabla = new PdfPTable(7);
        tabla.setWidthPercentage(100);
        tabla.setWidths(new float[]{7, 17, 42, 10, 10, 14, 16});
        
        for (Producto producto : productos) {
            agregarCelda(tabla, String.valueOf(producto.getItem()), BaseColor.WHITE, Element.ALIGN_CENTER);
            agregarCelda(tabla, producto.getNumeroParte(), BaseColor.WHITE, Element.ALIGN_CENTER);
            agregarCelda(tabla, producto.getDescripcion(), BaseColor.WHITE, Element.ALIGN_CENTER);
            agregarCelda(tabla, producto.getUnidad(), BaseColor.WHITE, Element.ALIGN_CENTER);
            agregarCelda(tabla, String.valueOf(producto.getCantidad()), BaseColor.WHITE, Element.ALIGN_CENTER);
            agregarCelda(tabla, MONEDA.format(producto.getPrecioUnitario()), BaseColor.WHITE, Element.ALIGN_RIGHT);
            agregarCelda(tabla, MONEDA.format(producto.getTotal()), BaseColor.WHITE, Element.ALIGN_RIGHT);
        }

        for (int i = productos.size(); i < 20; i++) {
            for (int j = 0; j < 7; j++) {
                PdfPCell celda = new PdfPCell(new Phrase(" "));
                celda.setMinimumHeight(20);
                tabla.addCell(celda);
            }
        }
        document.add(tabla);
    }

    private static void crearTotales(Document document, List<Producto> productos) throws Exception {
        BigDecimal subtotal = BigDecimal.ZERO;
        for (Producto producto : productos) {
            subtotal = subtotal.add(producto.getTotal());
        }
        BigDecimal iva = subtotal.multiply(new BigDecimal("0.16"));
        BigDecimal total = subtotal.add(iva);
        //----------------TABLA GENERAL----------------
        PdfPTable tablaGen = new PdfPTable(3);
        tablaGen.setWidthPercentage(100);
        
        band = true;
        agregarCelda(tablaGen, "Comentarios: ", BLANCO, Element.ALIGN_LEFT);
        //-----------------TABLA TOTALES---------------
        PdfPTable tabla = new PdfPTable(2);
        tabla.setWidthPercentage(100);
        tabla.setHorizontalAlignment(Element.ALIGN_RIGHT);
        tabla.setWidths(new float[]{50, 50});
        agregarCelda(tabla, "Subtotal", GRIS, Element.ALIGN_CENTER);
        agregarCelda(tabla, MONEDA.format(subtotal), AZUL, Element.ALIGN_RIGHT);
        agregarCelda(tabla, "IVA (16%)", GRIS, Element.ALIGN_CENTER);
        agregarCelda(tabla, MONEDA.format(iva), AZUL, Element.ALIGN_RIGHT);
        agregarCelda(tabla, "TOTAL", GRIS, Element.ALIGN_CENTER);
        agregarCelda(tabla, MONEDA.format(total), AZUL, Element.ALIGN_RIGHT);
//        band = true;
//        agregarCelda(tabla, " ", BLANCO, Element.ALIGN_RIGHT);

        PdfPCell pa = new PdfPCell(tabla);
        pa.setBorder(0);
        tablaGen.addCell(pa);
        
        agregarCelda(tablaGen, " ", BLANCO, 0);
        agregarCelda(tablaGen, " ", BLANCO, 0);
        agregarCelda(tablaGen, " ", BLANCO, 0);
        agregarCelda(tablaGen, " ", BLANCO, 0);
        agregarCelda(tablaGen, "   ", BLANCO, 0);
        agregarCelda(tablaGen, " ", BLANCO, 0);
        agregarCelda(tablaGen, " ", BLANCO, 0);
        agregarCelda(tablaGen, "Firma de comprador", BLANCO, Element.ALIGN_CENTER);
        agregarCelda(tablaGen, " ", BLANCO, 0);
        
        document.add(tablaGen);

        PdfPTable tablaCondiciones = new PdfPTable(1);
        tablaCondiciones.setWidthPercentage(100);

        PdfPCell celda = new PdfPCell();
        celda.setBorder(Rectangle.NO_BORDER);
        celda.setHorizontalAlignment(Element.ALIGN_CENTER);
        Font datos = new Font(Font.FontFamily.HELVETICA, 8, Font.NORMAL);
        Font titulo = new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD);
        Paragraph par = new Paragraph();
//        par.add(new Phrase(" \n", titulo));
        par.add(new Phrase("NOTA:", titulo));
        par.add(new Phrase("Enviar facturas (incluyendo archivo XML) a  compras@siymsdemexico.com.mx y a facturas.siymsdemexico@gmail.com para la programacion de los pagos\n", datos));
        par.add(new Phrase("Juan A. Aldaba          Jose A, Aldaba\n", datos));
        par.add(new Phrase("656 280-11-14            656 280-11-14\n", datos));
        par.add(new Phrase("Instrucciones especiales de la orden de compra\n", titulo));
        par.add(new Phrase("Recepción de material/servicio:\n", datos));
        par.add(new Phrase("1.- Orden de compra, factura original y una copia\n", datos));
        par.add(new Phrase("2.- El número de orden de compra deberá aparecer en la factura, no. de remisión y empaque con que se entrega el material\n", datos));
        par.add(new Phrase("3.- En la compra de equipo como maquinaria, cómputo, se deberá incluir en la factura, marca, modelo, no. serie del equipo\n", datos));
        par.add(new Phrase("4.-Favor de ajustarse al tiempo de entrega y calidad que se solicita\n", datos));
        par.add(new Phrase("5.- Recibo de material de lunes a viernes en el horario de cada compañía\n", datos));
        par.add(new Phrase("6.- El personal de recibo no sellará facturas de material entregados sin orden de compra o sin verificación física\n", datos));
        celda.addElement(par);
        tablaCondiciones.addCell(celda);
        document.add(tablaCondiciones);
    }

}
