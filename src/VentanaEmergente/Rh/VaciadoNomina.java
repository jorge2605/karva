package VentanaEmergente.Rh;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileOutputStream;
import java.text.DecimalFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

public class VaciadoNomina {

    private final BaseColor AZUL = new BaseColor(176, 230, 255);
    private final BaseColor GRIS = new BaseColor(80, 80, 80);
    private final BaseColor LINEA = new BaseColor(150, 150, 150);
    private final Font FUENTE_NORMAL = new Font(Font.FontFamily.HELVETICA, 8, Font.NORMAL, BaseColor.DARK_GRAY);
    private final Font FUENTE_NEGRITA = new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD, BaseColor.DARK_GRAY);
    private final Font FUENTE_TITULO = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.DARK_GRAY);
    private final Font FUENTE_SUBTITULO = new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL, BaseColor.DARK_GRAY);

    private double totalBruto = 0;
    private double totalDeducciones = 0;
    private double totalDobles = 0;
    private double totalTriples = 0;
    private double totalTotal = 0;
    public Document document;
    public PdfPTable tabla = null;

    public void generarNomina(String semana, String per, String ruta) {
        document = new Document(PageSize.A4, 25, 25, 25, 25);
        try {
            PdfWriter.getInstance(document, new FileOutputStream(ruta + "/Nomina semana " + semana + ".pdf"));
            document.open();

            Paragraph empresa = new Paragraph("SIYMS S.A.S. DE C.V.", FUENTE_TITULO);
            empresa.setAlignment(Element.ALIGN_CENTER);
            empresa.setSpacingAfter(15);
            document.add(empresa);

            Paragraph periodo = new Paragraph("Nómina semana " + semana + " periodo " + per, FUENTE_SUBTITULO);
            periodo.setAlignment(Element.ALIGN_LEFT);
            periodo.setSpacingAfter(15);
            document.add(periodo);

            tabla = new PdfPTable(6);
            tabla.setWidthPercentage(100);
            float[] anchos = {2.25f, .5f, 1.15f, 1.0f, 1.15f, 1.2f};
            tabla.setWidths(anchos);
            agregarEncabezado(tabla, "Nombre de empleado", Element.ALIGN_LEFT);
            agregarEncabezado(tabla, "#", Element.ALIGN_LEFT);
            agregarEncabezado(tabla, "Salario Bruto", Element.ALIGN_CENTER);
            agregarEncabezado(tabla, "Deducciones", Element.ALIGN_CENTER);
            agregarEncabezado(tabla, "Horas extra", Element.ALIGN_CENTER);
            agregarEncabezado(tabla, "Total", Element.ALIGN_CENTER);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public final void imprimirNomina() {
        try {
            DecimalFormat df = new DecimalFormat("#,###.##");
            agregarEncabezado(tabla, " ", Element.ALIGN_CENTER);
            agregarEncabezado(tabla, " ", Element.ALIGN_CENTER);
            agregarEncabezado(tabla, "$" + df.format(totalBruto), Element.ALIGN_CENTER);
            agregarEncabezado(tabla, "$" + df.format(totalDeducciones), Element.ALIGN_CENTER);
            agregarEncabezado(tabla, "$" + df.format(totalDobles), Element.ALIGN_CENTER);
            agregarEncabezado(tabla, "$" + df.format(totalTotal), Element.ALIGN_CENTER);
            document.add(tabla);
            document.close();
            System.out.println("PDF generado correctamente.");
        } catch (DocumentException ex) {
            Logger.getLogger(VaciadoNomina.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void agregarEncabezado(PdfPTable tabla, String texto, int alineacion) {
        PdfPCell celda = new PdfPCell(new Phrase(texto, FUENTE_NEGRITA));
        celda.setHorizontalAlignment(alineacion);
        celda.setVerticalAlignment(Element.ALIGN_MIDDLE);
        celda.setPadding(5);
        celda.setBorder(PdfPCell.NO_BORDER);
        celda.setBorderWidthBottom(1f);
        celda.setBorderColorBottom(LINEA);

        tabla.addCell(celda);
    }

    public void agregarEmpleado(PdfPTable tabla, String nombre, String puesto, String salario, String isr,
            String seguro, String sar, String anticipos, String total, boolean rosa) {

        try {
            totalBruto += Double.parseDouble(salario.replace(",", ""));
        } catch (Exception e) {
            System.out.println(e);
        }
        try {
            totalDeducciones += Double.parseDouble(isr.replace(",", ""));
        } catch (Exception e) {
        }
        try {
            totalDobles += Double.parseDouble(seguro.replace(",", ""));
        } catch (Exception e) {
        }
        try {
            totalTriples += Double.parseDouble(sar.replace(",", ""));
        } catch (Exception e) {
        }
        try {
            totalTotal += Double.parseDouble(total.replace(",", ""));
        } catch (Exception e) {
        }

        BaseColor fondo = rosa ? AZUL : BaseColor.WHITE;
        agregarCelda(tabla, nombre, Element.ALIGN_LEFT, fondo);
        agregarCelda(tabla, puesto, Element.ALIGN_LEFT, fondo);
        agregarCelda(tabla, "$" + salario, Element.ALIGN_CENTER, fondo);
        agregarCelda(tabla, "$" + isr, Element.ALIGN_CENTER, fondo);
        agregarCelda(tabla, "$" + seguro, Element.ALIGN_CENTER, fondo);
        String texto = "";
        if (!anticipos.isEmpty()) {
            texto += "$" + anticipos + "\n";
        }
        texto += "$" + total;
        agregarCelda(tabla, texto, Element.ALIGN_CENTER, fondo);
    }

    private void agregarCelda(PdfPTable tabla, String texto, int alineacion, BaseColor fondo) {
        PdfPCell celda = new PdfPCell(new Phrase(texto, FUENTE_NORMAL));
        celda.setHorizontalAlignment(alineacion);
        celda.setVerticalAlignment(Element.ALIGN_MIDDLE);
        celda.setBackgroundColor(fondo);
        celda.setPaddingTop(8);
        celda.setPaddingBottom(8);
        celda.setPaddingLeft(5);
        celda.setPaddingRight(5);
        celda.setBorder(PdfPCell.NO_BORDER);
        tabla.addCell(celda);
    }
}
