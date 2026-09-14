package Controlador.cotizaciones;

import Conexiones.Conexion;
import VentanaEmergente.Compras.CabezeraCompras;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class EncabezadoCotizacionPDF extends PdfPageEventHelper {

    private static final BaseColor AZUL = new BaseColor(155, 190, 220);
    private static final BaseColor BLANCO = new BaseColor(255, 255, 255);
    private static final BaseColor AZUL_OSCURO = new BaseColor(0, 78, 171);
    private static final BaseColor GRIS = new BaseColor(190, 190, 190);
    private static final DecimalFormat MONEDA = new DecimalFormat("$#,##0.00");
    public String url = "";
    public String cotizacion = "1445";
    public String compania = "Siyms";
    public String contacto = "Jorge santacruz";
    public String fecha = "07-09-2026";
    public String ciudad = "Juarez";
    public String email = "santacruz.leonel.1h@hotmail.com";
    public String moneda;
    public PdfTemplate total;

    public void onOpenDocument(PdfWriter writer, Document document) {
        total = writer.getDirectContent().createTemplate(30, 10);
    }

    @Override
    public void onEndPage(PdfWriter writer, Document document) {
        try {
            PdfPTable table = new PdfPTable(1);
            table.setTotalWidth(527);
            table.setLockedWidth(true);
            table.getDefaultCell().setBorder(0);

            com.itextpdf.text.Font fuente2 = new com.itextpdf.text.Font();
            fuente2.setSize(8);
            fuente2.setStyle(com.itextpdf.text.Font.BOLD);
            fuente2.setFamily("Roboto");
            fuente2.setColor(200, 200, 200);

            Paragraph pagePhrase = new Paragraph();
            pagePhrase.setAlignment(Element.ALIGN_CENTER);
            pagePhrase.add(new Chunk("Página " + writer.getPageNumber() + " de ", fuente2));

            Image totalPagesImage = Image.getInstance(total);
            pagePhrase.add(new Chunk(totalPagesImage, 0, 0));

            PdfPCell cel1 = new PdfPCell();
            cel1.setBorder(0);
            cel1.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cel1.setFixedHeight(20);

            cel1.addElement(pagePhrase);
            table.addCell(cel1);

            table.writeSelectedRows(0, -1, 34, 40, writer.getDirectContent());

        } catch (BadElementException ex) {
            Logger.getLogger(CabezeraCompras.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void onCloseDocument(PdfWriter writer, Document document) {
        try {
            total.beginText();
            total.setColorFill(new BaseColor(200, 200, 200));
            total.setFontAndSize(BaseFont.createFont(), 8);
            total.setTextMatrix(0, 0);
            total.showText(String.valueOf(writer.getPageNumber()));
            total.endText();
        } catch (DocumentException ex) {
            Logger.getLogger(CabezeraCompras.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(CabezeraCompras.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public final void verDatos() {
        try {
            Connection con = new Conexion().getConnection();
            Statement st = con.createStatement();
            String sql = "select * from conf_cotizacion";
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                url = (rs.getString("urlimg"));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al ver datos url de imagen: " + e, "error", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void onStartPage(PdfWriter writer, Document document) {
        try {
            verDatos();
            PdfPTable tabla = new PdfPTable(2);
            tabla.setWidthPercentage(100);
            tabla.setWidths(new float[]{35, 65});
            PdfPCell logo = new PdfPCell();
            Image imagen = Image.getInstance(url);
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

            PdfPTable tabla2 = new PdfPTable(4);
            tabla2.setWidthPercentage(100);
            tabla2.setWidths(new float[]{
                25, 50, 20, 20
            });
            agregarCelda(tabla2, " ", BLANCO, Element.ALIGN_CENTER);
            agregarCelda(tabla2, " ", BLANCO, Element.ALIGN_CENTER);
            band = true;
            agregarCelda(tabla2, "Cotizacion", AZUL, Element.ALIGN_CENTER);
            agregarCelda(tabla2, " ", BLANCO, Element.ALIGN_CENTER);
            agregarCelda(tabla2, " ", BLANCO, Element.ALIGN_CENTER);
            band = true;
            agregarCelda(tabla2, cotizacion, GRIS, Element.ALIGN_CENTER);

            agregarCelda(tabla2, "Compañia", AZUL, Element.ALIGN_CENTER);
            agregarCelda(tabla2, "Contacto", AZUL, Element.ALIGN_CENTER);
            agregarCelda(tabla2, "Fecha", AZUL, Element.ALIGN_CENTER);
            agregarCelda(tabla2, "Ciudad", AZUL, Element.ALIGN_CENTER);

            agregarCelda(tabla2, compania, BLANCO, Element.ALIGN_CENTER);
            agregarCelda(tabla2, contacto, BLANCO, Element.ALIGN_CENTER);
            agregarCelda(tabla2, fecha, BLANCO, Element.ALIGN_CENTER);
            agregarCelda(tabla2, ciudad, BLANCO, Element.ALIGN_CENTER);

            agregarCelda(tabla2, " ", AZUL, Element.ALIGN_CENTER);
            agregarCelda(tabla2, "Email", AZUL, Element.ALIGN_CENTER);
            agregarCelda(tabla2, "Control", AZUL, Element.ALIGN_CENTER);
            agregarCelda(tabla2, "T. Pago", AZUL, Element.ALIGN_CENTER);

            agregarCelda(tabla2, " ", BLANCO, Element.ALIGN_CENTER);
            agregarCelda(tabla2, email, BLANCO, Element.ALIGN_CENTER);
            agregarCelda(tabla2, "Venta", BLANCO, Element.ALIGN_CENTER);
            agregarCelda(tabla2, moneda, BLANCO, Element.ALIGN_CENTER);
            document.add(tabla2);

            PdfPTable tabla3 = new PdfPTable(7);
            tabla3.setWidthPercentage(100);
            tabla3.setWidths(new float[]{7, 17, 42, 10, 10, 14, 16});
            String[] encabezados = {
                "Ítem",
                "Numero de parte",
                "Descripcion",
                "Unidad",
                "Cantidad",
                "Precio\nUnitario",
                "Precio Total"
            };

            band = true;
            agregarCelda(tabla3, "  ", BLANCO, Element.ALIGN_CENTER);
            agregarCelda(tabla3, "REQ: ", BLANCO, Element.ALIGN_CENTER);
            band = true;
            agregarCelda(tabla3, "  ", BLANCO, Element.ALIGN_CENTER);
            band = true;
            agregarCelda(tabla3, "  ", BLANCO, Element.ALIGN_CENTER);

            for (String encabezado : encabezados) {
                agregarCelda(tabla3, encabezado, AZUL, Element.ALIGN_CENTER);
            }
            document.add(tabla3);
        } catch (DocumentException ex) {
            Logger.getLogger(EncabezadoCotizacionPDF.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(EncabezadoCotizacionPDF.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        if (texto.equals(" ")) {
            celda.setBorder(0);
        }
        tabla.addCell(celda);
    }
    static boolean band = false;

}
