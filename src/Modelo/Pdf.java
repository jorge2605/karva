package Modelo;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.ExceptionConverter;
import com.itextpdf.text.Image;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import java.awt.Color;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

         public class Pdf extends PdfPageEventHelper {
    private String encabezado;
    PdfTemplate total;
    
     public void onOpenDocument(PdfWriter writer, Document document) {
        total = writer.getDirectContent().createTemplate(30, 16);
    }
    
     public void onEndPage(PdfWriter writer, Document document) {
        PdfPTable table = new PdfPTable(3);
        try {
            // Se determina el ancho y altura de la tabla
            table.setWidths(new int[]{24, 24, 2});
            table.setTotalWidth(527);
            table.setLockedWidth(true);
            table.getDefaultCell().setFixedHeight(20);
            Image img = Image.getInstance("BD/3i1.png");
            img.setAbsolutePosition(30, 790);
            
            Image img2 = Image.getInstance("BD/3i1.png");
            img2.setAbsolutePosition(510, 790);
            
            float[] medidaCeldas2 = {25, 150,25}; 
            table.setWidths(medidaCeldas2);
            
            // Borde de la celda
            table.getDefaultCell().setBorder(0);
            
            com.itextpdf.text.Font fuente1 = new com.itextpdf.text.Font();
            com.itextpdf.text.Font fuente2 = new com.itextpdf.text.Font();
            
            fuente1.setSize(20);
            fuente1.setFamily("Roboto");
            fuente1.setColor(158,158,158);
            
            fuente2.setSize(8);
            fuente2.setFamily("Roboto");
            fuente2.setColor(158,158,158);
            
            PdfPCell cel = new PdfPCell(new Phrase("SERVICIOS INDUSTRIALES 3i S de RL MI",fuente1));
            cel.setBorder(0);
            cel.setHorizontalAlignment(Element.ALIGN_CENTER);
            
            PdfPCell cel1 = new PdfPCell(new Phrase("Integración de equipos industriales. programación de sistemas de control\n"
                    + "Capacitación PLC – refacciones en general\n"
                    + "Tel 656-791-1365",fuente2));
            cel1.setBorder(0);
            cel1.setHorizontalAlignment(Element.ALIGN_CENTER);
            
//            table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
            
            table.addCell("");
            table.addCell(cel);
            table.addCell("");
            table.addCell("");
            table.addCell(cel1);
            table.addCell("");
            document.add(img);
            document.add(img2);
            
            
            // Esta linea escribe la tabla como encabezado
            table.writeSelectedRows(0, -1, 34, 845, writer.getDirectContent());
        }
         catch(DocumentException de) {
             throw new ExceptionConverter(de);
        } catch (IOException ex) {
            Logger.getLogger(Pdf.class.getName()).log(Level.SEVERE, null, ex);
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
