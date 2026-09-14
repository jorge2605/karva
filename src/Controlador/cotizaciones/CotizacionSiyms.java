package Controlador.cotizaciones;

import Controlador.compras.*;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;

public class CotizacionSiyms {

    private final BaseColor AZUL = new BaseColor(155, 190, 220);
    private final BaseColor BLANCO = new BaseColor(255, 255, 255);
    private final BaseColor AZUL_OSCURO = new BaseColor(0, 78, 171);
    private final BaseColor GRIS = new BaseColor(190, 190, 190);
    private final DecimalFormat MONEDA = new DecimalFormat("$#,##0.00");
    public String moneda = "MXN";
    public String fechaVencimiento = "08-10-2026";
    public String cotizacion = "0";
    public String compania = "0";
    public String contacto = "0";
    public String fecha = "0";
    public String email = "0";
    public String iva = "0";
    
    public String generar(String ruta, List<Producto> productos) throws Exception {
        Document document = new Document(PageSize.LETTER, 20, 20, 20, 20);
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(ruta));
        EncabezadoCotizacionPDF enc = new EncabezadoCotizacionPDF();
        enc.cotizacion = cotizacion;
        enc.compania = compania;
        enc.contacto = contacto;
        enc.fecha = fecha;
        enc.email = email;
        enc.moneda = moneda;
        writer.setPageEvent(enc);
        document.open();
        crearTablaProductos(document, productos);
        String total = crearTotales(document, productos);
        document.close();
        return total;
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

    private void crearTablaProductos(Document document, List<Producto> productos) throws Exception {
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

        document.add(tabla);
    }

    private String crearTotales(Document document, List<Producto> productos) throws Exception {
        BigDecimal subtotal = BigDecimal.ZERO;
        for (Producto producto : productos) {
            subtotal = subtotal.add(producto.getTotal());
        }
        if (iva.equals("8")) {
            iva = "08";
        }
        BigDecimal iv = subtotal.multiply(new BigDecimal("0." + this.iva));
        BigDecimal total = subtotal.add(iv);
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
        agregarCelda(tabla, "IVA (" + this.iva + "%)", GRIS, Element.ALIGN_CENTER);
        agregarCelda(tabla, MONEDA.format(iv), AZUL, Element.ALIGN_RIGHT);
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
        par.add(new Phrase("Cotizacion en moneda: " + moneda + "\n", datos));
        par.add(new Phrase("Cotizacion valida hasta el dia: " + fechaVencimiento + "\n", datos));
        par.add(new Phrase("Precio total incluye IVA\n", datos));
        par.add(new Phrase("Carlos I. Mota (656)-106-37-74:\n", datos));
        par.add(new Phrase("Jose A. Aldaba (656)-279-90-41:\n", datos));celda.addElement(par);
        tablaCondiciones.addCell(celda);
        document.add(tablaCondiciones);
        return MONEDA.format(total);
    }

}
