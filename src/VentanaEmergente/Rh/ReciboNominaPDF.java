package VentanaEmergente.Rh;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ReciboNominaPDF {

    private final Font FONT_7 = new Font(Font.FontFamily.HELVETICA, 7, Font.NORMAL);
    private final Font FONT_7_BOLD = new Font(Font.FontFamily.HELVETICA, 7, Font.BOLD);
    private final Font FONT_8 = new Font(Font.FontFamily.HELVETICA, 8, Font.NORMAL);
    private final Font FONT_8_BOLD = new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD);
    private final Font FONT_9 = new Font(Font.FontFamily.HELVETICA, 9, Font.NORMAL);
    private final Font FONT_9_BOLD = new Font(Font.FontFamily.HELVETICA, 9, Font.BOLD);
    private final Font FONT_10_BOLD = new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD);

    private final BaseColor GRIS = new BaseColor(190, 190, 190);
    private final BaseColor GRIS_CLARO = new BaseColor(235, 235, 235);
    private final BaseColor AZUL = new BaseColor(55, 100, 220);
    
    public void generarPDF(String ruta, String nombre, String rfc, String curp, String relacionLaboral, 
            String nss, String periodo, String fechaPago, String puesto, String semana,
            String sueldo, String horasDobles, String horasTriples, String faltas, String prima, String numEmpleado) throws Exception {

        Document document = new Document(PageSize.LETTER, 36, 36, 25, 25);
        Path carpeta = Paths.get(ruta).getParent();
        if (carpeta != null) {
            Files.createDirectories(carpeta);
        }
        PdfWriter.getInstance(document, new FileOutputStream(ruta));
        document.open();

        PdfPTable encabezado = new PdfPTable(2);
        encabezado.setWidthPercentage(100);
        encabezado.setWidths(new float[]{75, 25});

        PdfPCell empresa = new PdfPCell();
        empresa.setBackgroundColor(GRIS);
        empresa.setBorder(Rectangle.BOX);
        empresa.setPadding(4);

        Paragraph pEmpresa = new Paragraph();
        pEmpresa.setLeading(9);
        pEmpresa.add(new Chunk("SIYMS S.A.S. DE C.V.\n", FONT_10_BOLD));
        pEmpresa.add(new Chunk("RFC:      SIY1706239V4\n", FONT_7_BOLD));
//        pEmpresa.add(new Chunk("Reg Fiscal:     601 General de Ley Personas Morales\n", FONT_7));
        pEmpresa.add(new Chunk("Lugar de expedición:     32575 CD JUAREZ", FONT_7));
        empresa.addElement(pEmpresa);

        PdfPCell fecha = new PdfPCell();
        fecha.setBackgroundColor(GRIS);
        fecha.setBorder(Rectangle.BOX);
        fecha.setPadding(4);

        Paragraph pFecha = new Paragraph();
        pFecha.setAlignment(Element.ALIGN_RIGHT);
        pFecha.setLeading(10);
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MMM/yyyy");
        String fec = sdf.format(d);
        pFecha.add(new Chunk("Fecha:     " + fec + "\n", FONT_7));
        pFecha.add(new Chunk("Hora:       19:09:42", FONT_7));
        fecha.addElement(pFecha);

        encabezado.addCell(empresa);
        encabezado.addCell(fecha);

        PdfPTable info = new PdfPTable(2);
        info.setWidthPercentage(100);
        info.setWidths(new float[]{50, 50});

        PdfPCell empleado = new PdfPCell();
        empleado.setBorder(Rectangle.BOX);
        empleado.setPadding(4);

        Paragraph pEmpleado = new Paragraph();
        pEmpleado.setLeading(9);

        pEmpleado.add(new Chunk(numEmpleado + " - " + nombre + "\n", FONT_8_BOLD));
        pEmpleado.add(new Chunk("RFC:             ", FONT_7_BOLD));
        pEmpleado.add(new Chunk(rfc + "\n", FONT_7));
        pEmpleado.add(new Chunk("CURP:           ", FONT_7_BOLD));
        pEmpleado.add(new Chunk(curp + "\n", FONT_7));
        pEmpleado.add(new Chunk("Fecha Ini Relación Lab:   ", FONT_7_BOLD));
        pEmpleado.add(new Chunk(relacionLaboral + "\n", FONT_7));
        pEmpleado.add(new Chunk("NSS:             ", FONT_7_BOLD));
        pEmpleado.add(new Chunk(nss + "\n", FONT_7));

        empleado.addElement(pEmpleado);

        PdfPCell datosPago = new PdfPCell();
        datosPago.setBorder(Rectangle.BOX);
        datosPago.setPadding(4);

        Paragraph pPago = new Paragraph();
        pPago.setLeading(9);

        pPago.add(new Chunk("Ejercicio:     ", FONT_7_BOLD));
        pPago.add(new Chunk(periodo.split(" ")[1].split("-")[0] +"\n", FONT_7));
        pPago.add(new Chunk("Periodo:       ", FONT_7_BOLD));
        pPago.add(new Chunk("Semana " + semana, FONT_7_BOLD));
        pPago.add(new Chunk("                         " + periodo + "\n", FONT_7_BOLD));
        pPago.add(new Chunk("Días de Pago:       ", FONT_7_BOLD));
        pPago.add(new Chunk("7.000\n", FONT_7));
        pPago.add(new Chunk("Fecha Pago:          ", FONT_7_BOLD));
        pPago.add(new Chunk(fec + "\n", FONT_7));
        pPago.add(new Chunk("Puesto:              ", FONT_7_BOLD));
        pPago.add(new Chunk(puesto + "\n", FONT_7));

        datosPago.addElement(pPago);

        info.addCell(empleado);
        info.addCell(datosPago);

        PdfPTable conceptos = new PdfPTable(2);
        conceptos.setWidthPercentage(100);
        conceptos.setWidths(new float[]{60, 40});

        PdfPCell percepciones = new PdfPCell();
        percepciones.setBorder(Rectangle.BOX);
        percepciones.setPadding(0);

        PdfPTable tablaPercepciones = new PdfPTable(4);
        tablaPercepciones.setWidthPercentage(100);
        tablaPercepciones.setWidths(new float[]{13, 13, 54, 20});

        PdfPCell tituloPerc = new PdfPCell(new Phrase("Percepciones", FONT_8_BOLD));
        tituloPerc.setColspan(4);
        tituloPerc.setHorizontalAlignment(Element.ALIGN_CENTER);
        tituloPerc.setBackgroundColor(GRIS_CLARO);
        tablaPercepciones.addCell(tituloPerc);

        agregarHeader(tablaPercepciones, "Ag");
        agregarHeader(tablaPercepciones, "No.\n");
        agregarHeader(tablaPercepciones, "Concepto");
        agregarHeader(tablaPercepciones, "Total");

        agregarConcepto(tablaPercepciones, "P", "001 001", "Sueldo", sueldo);
        agregarConcepto(tablaPercepciones, "P", "001 003", "Horas extra", horasDobles);
//        agregarConcepto(tablaPercepciones, "P", "038 014", "Horas triples", horasTriples);
        if (!prima.equals(""))
            agregarConcepto(tablaPercepciones, "P", "038 014", "Prima vacacional", prima);

        PdfPCell espacioPerc = new PdfPCell(new Phrase(""));
        espacioPerc.setColspan(4);
        espacioPerc.setFixedHeight(20);
        espacioPerc.setBorder(Rectangle.NO_BORDER);
        tablaPercepciones.addCell(espacioPerc);

        percepciones.addElement(tablaPercepciones);
        conceptos.addCell(percepciones);

        PdfPCell deducciones = new PdfPCell();
        deducciones.setBorder(Rectangle.BOX);
        deducciones.setPadding(0);

        PdfPTable tablaDeducciones = new PdfPTable(4);
        tablaDeducciones.setWidthPercentage(100);
        tablaDeducciones.setWidths(new float[]{13, 13, 54, 20});

        PdfPCell tituloDeducciones = new PdfPCell(new Phrase("Deducciones", FONT_8_BOLD));
        tituloDeducciones.setColspan(4);
        tituloDeducciones.setHorizontalAlignment(Element.ALIGN_CENTER);
        tituloDeducciones.setBackgroundColor(GRIS_CLARO);
        tablaDeducciones.addCell(tituloDeducciones);

        agregarHeader(tablaDeducciones, "Ag");
        agregarHeader(tablaDeducciones, "No.\n");
        agregarHeader(tablaDeducciones, "Concepto");
        agregarHeader(tablaDeducciones, "Total");

        agregarConcepto(tablaDeducciones, "002", "045", "Faltas", faltas);

        PdfPCell espacioDed = new PdfPCell(new Phrase(""));
        espacioDed.setColspan(4);
        espacioDed.setFixedHeight(20);
        espacioDed.setBorder(Rectangle.NO_BORDER);
        tablaDeducciones.addCell(espacioDed);

        deducciones.addElement(tablaDeducciones);
        conceptos.addCell(deducciones);

        PdfPTable filaTotales = new PdfPTable(2);
        filaTotales.setWidthPercentage(100);
        filaTotales.setWidths(new float[]{60, 40});

        PdfPCell totalPercepciones = new PdfPCell();
        totalPercepciones.setBorder(Rectangle.BOX);
        totalPercepciones.setPadding(4);

        double subtotal = Double.parseDouble(sueldo) + Double.parseDouble(horasDobles) + Double.parseDouble(horasTriples);
        if (!prima.equals(""))
            subtotal += Double.parseDouble(prima);
        double descuentos = Double.parseDouble(faltas);
        double total = subtotal - descuentos;
        
        Paragraph pTotalPerc = new Paragraph();
        pTotalPerc.add(new Chunk("Total Percepc. más Otros Pagos  $", FONT_8_BOLD));
        pTotalPerc.add(new Chunk("                         " + subtotal, FONT_8_BOLD));
        totalPercepciones.addElement(pTotalPerc);

        filaTotales.addCell(totalPercepciones);

        PdfPCell resumen = new PdfPCell();
        resumen.setBorder(Rectangle.BOX);
        resumen.setPadding(4);

        PdfPTable tablaResumen = new PdfPTable(2);
        tablaResumen.setWidthPercentage(100);
        tablaResumen.setWidths(new float[]{65, 35});

        agregarTotal(tablaResumen, "Subtotal $", String.valueOf(subtotal));
        agregarTotal(tablaResumen, "Descuentos $", String.valueOf(descuentos));
        agregarTotal(tablaResumen, "Total $", String.valueOf(total));
        agregarTotal(tablaResumen, "Neto del recibo $", String.valueOf(total));

        resumen.addElement(tablaResumen);
        filaTotales.addCell(resumen);

        PdfPTable importeLetra = new PdfPTable(2);
        importeLetra.setWidthPercentage(100);
        importeLetra.setWidths(new float[]{60, 40});

        PdfPCell vacio = new PdfPCell();
        vacio.setBorder(Rectangle.NO_BORDER);
        importeLetra.addCell(vacio);

        Paragraph legal = new Paragraph(
                "Se puso a mi disposición el archivo PDF correspondiente y recibí de la empresa\n" +
                "arriba mencionada la cantidad neta a que este documento se refiere estando\n" +
                "conforme con las percepciones y deducciones que en él aparecen especificados.",
                new Font(Font.FontFamily.HELVETICA, 7, Font.NORMAL, new BaseColor(170, 170, 170))
        );

        legal.setSpacingBefore(8);

        PdfPTable firma = new PdfPTable(2);
        firma.setWidthPercentage(100);
        firma.setWidths(new float[]{70, 30});

        PdfPCell espacioFirma = new PdfPCell();
        espacioFirma.setBorder(Rectangle.NO_BORDER);
        firma.addCell(espacioFirma);

        PdfPCell firmaEmpleado = new PdfPCell();
        firmaEmpleado.setBorder(Rectangle.NO_BORDER);

        Paragraph linea = new Paragraph("____________________________", FONT_8);
        linea.setAlignment(Element.ALIGN_CENTER);

        Paragraph textoFirma = new Paragraph("Firma del empleado", FONT_8);
        textoFirma.setAlignment(Element.ALIGN_CENTER);

        firmaEmpleado.addElement(linea);
        firmaEmpleado.addElement(textoFirma);

        firma.addCell(firmaEmpleado);
        
        for (int i = 0; i < 2; i++) {
            document.add(encabezado);
            document.add(Chunk.NEWLINE);
            document.add(info);
            document.add(conceptos);
            document.add(new Paragraph(" "));
            document.add(filaTotales);
            document.add(new Paragraph(" "));
            document.add(legal);
            document.add(firma);
            if (i == 0) 
                document.add(new Paragraph("---------------------------------------------------------------------------------------------------------------------------------------"));
//                document.add(new Paragraph(" "));
        }

        document.close();
    }

    private void agregarHeader(PdfPTable tabla, String texto) {
        PdfPCell celda = new PdfPCell(new Phrase(texto, FONT_7_BOLD));
        celda.setBackgroundColor(GRIS_CLARO);
        celda.setHorizontalAlignment(Element.ALIGN_CENTER);
        celda.setVerticalAlignment(Element.ALIGN_MIDDLE);
        celda.setPadding(2);
        tabla.addCell(celda);
    }

    private void agregarConcepto(PdfPTable tabla, String agrupacion, String numero, String concepto, String total) {

        PdfPCell c1 = new PdfPCell(new Phrase(agrupacion, FONT_7));
        PdfPCell c2 = new PdfPCell(new Phrase(numero, FONT_7));
        PdfPCell c3 = new PdfPCell(new Phrase(concepto, FONT_7));
        PdfPCell c4 = new PdfPCell(new Phrase(total, FONT_7));

        c1.setBorder(Rectangle.NO_BORDER);
        c2.setBorder(Rectangle.NO_BORDER);
        c3.setBorder(Rectangle.NO_BORDER);
        c4.setBorder(Rectangle.NO_BORDER);

        c4.setHorizontalAlignment(Element.ALIGN_RIGHT);

        tabla.addCell(c1);
        tabla.addCell(c2);
        tabla.addCell(c3);
        tabla.addCell(c4);
    }

    private void agregarTotal(PdfPTable tabla, String concepto, String cantidad) {

        PdfPCell c1 = new PdfPCell(new Phrase(concepto, FONT_8_BOLD));
        PdfPCell c2 = new PdfPCell(new Phrase(cantidad, FONT_8_BOLD));

        c1.setBorder(Rectangle.NO_BORDER);
        c2.setBorder(Rectangle.NO_BORDER);

        c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
        c2.setHorizontalAlignment(Element.ALIGN_RIGHT);

        if (concepto.equals("Neto del recibo $")) {

            Font azul = new Font(
                    Font.FontFamily.HELVETICA,
                    8,
                    Font.BOLD,
                    AZUL
            );

            c1.setPhrase(new Phrase(concepto, azul));
            c2.setPhrase(new Phrase(cantidad, azul));
        }

        tabla.addCell(c1);
        tabla.addCell(c2);
    }
}
