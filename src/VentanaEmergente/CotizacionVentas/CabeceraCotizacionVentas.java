package VentanaEmergente.CotizacionVentas;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.ExceptionConverter;
import com.itextpdf.text.Image;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class CabeceraCotizacionVentas extends PdfPageEventHelper{
    private String encabezado;
    PdfTemplate total;
    
    public void onOpenDocument(PdfWriter writer, Document document) {
        total = writer.getDirectContent().createTemplate(30, 16);
    }
    
    @Override
     public void onStartPage(PdfWriter writer, Document document){
         PdfPTable table = new PdfPTable(1);
         // Se determina el ancho y altura de la tabla
         table.setTotalWidth(600);
         table.setLockedWidth(true);
         table.getDefaultCell().setFixedHeight(20);
         // Borde de la celda
         table.getDefaultCell().setBorder(0);
         com.itextpdf.text.Font fuente1 = new com.itextpdf.text.Font();
         com.itextpdf.text.Font fuente2 = new com.itextpdf.text.Font();
         fuente1.setSize(8);
         fuente1.setFamily("Roboto");
         fuente1.setColor(255,255,255);
         fuente2.setSize(8);
         fuente2.setFamily("Roboto");
         fuente2.setColor(0,0,0);
         PdfPCell cel = new PdfPCell(new Phrase("Contáctanos: (Ventas: Ing. Juan Rayos. Cel. (636) 103-9107 juan.rayos@si3i.com ) (Gerencia: Ing. Leonardo Soto. Cel. (656) 109-2176 leo.soto@si3i.com )\n" +
"Camino Viejo a La Rosita #305 Bodega A1 Cd. Juárez, Chihuahua, Mex. Tel. (656) 791-1365",fuente1));
         cel.setBorder(0);
         cel.setHorizontalAlignment(Element.ALIGN_CENTER);
         BaseColor s = new BaseColor(255, 94, 8);
         cel.setBackgroundColor(s);
         
         table.addCell(cel);
         
        // Esta linea escribe la tabla como encabezado
        table.writeSelectedRows(0, -1, 0, 20, writer.getDirectContent());
     }
     
    @Override
     public void onEndPage(PdfWriter writer, Document document) {
        PdfPTable table = new PdfPTable(1);
        try {
            table.setTotalWidth(600);
            table.setLockedWidth(true);
            table.getDefaultCell().setFixedHeight(20);
            com.itextpdf.text.Font fuente30 = new com.itextpdf.text.Font();
            fuente30.setSize(30);
            fuente30.setFamily("Arial");
            fuente30.setColor(BaseColor.WHITE);
            fuente30.setStyle(com.itextpdf.text.Font.BOLDITALIC);

            com.itextpdf.text.Font fuente25 = new com.itextpdf.text.Font();
            fuente25.setSize(12);
            fuente25.setFamily("Arial");
            fuente25.setColor(BaseColor.WHITE);
            fuente25.setStyle(com.itextpdf.text.Font.ITALIC);
            
            table.setWidthPercentage(100);
            PdfPCell c1 = new PdfPCell(new Phrase("Servicios Industriales 3i S de RL MI",fuente30));
            PdfPCell c2 = new PdfPCell(new Phrase("Integración de Equipos Industriales – Programación de Sistemas de Control \n" +
            "Capacitación PLC – Refacciones en General.",fuente25));
            PdfPCell c6 = new PdfPCell(new Phrase("       "
                    + "            ",fuente30));

            c1.setBorder(0);
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);

            c2.setBorder(0);
            c6.setBorder(0);
            c2.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(c1);
            table.addCell(c2);

            table.addCell(c6);
            
            Image img = Image.getInstance("BD/img-fondo-doc.png");
            img.setAbsolutePosition(30, 200);
            
            Image img2 = Image.getInstance("C:\\Pruebas\\BD\\si3i.png");
            img2.setAbsolutePosition(500, 760);
            
            PdfContentByte r1 = writer.getDirectContentUnder();
            r1.saveState();
            r1.setRGBColorFill(227, 106, 7);
            r1.rectangle(0, 780, 800, 90);
            r1.fill();
            r1.restoreState();
            
            document.add(img);
            document.add(img2);
//            document.add(table);
            
            
            // Esta linea escribe la tabla como encabezado
            table.writeSelectedRows(0, -1, 5, 845, writer.getDirectContent());
        }
         catch(DocumentException de) {
             throw new ExceptionConverter(de);
        } catch (IOException ex) {
            Logger.getLogger(CabeceraCotizacionVentas.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "ERROR: "+ex, "ERROR",JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void onCloseDocument(PdfWriter writer, Document document) {
        ColumnText.showTextAligned(total, Element.ALIGN_LEFT, new Phrase(String.valueOf(writer.getPageNumber())), 2, 2, 0);
    }    
    
    public String getEncabezado() {
        return encabezado;
    }
    public void setEncabezado(String encabezado) {
        this.encabezado = encabezado;
    }
}