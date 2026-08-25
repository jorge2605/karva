package Modelo;
import com.itextpdf.text.BaseColor;
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
import com.itextpdf.text.pdf.PdfPageEventHelper;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

    public class CabezeraChecador extends PdfPageEventHelper {
    private String encabezado;
    PdfTemplate total;
    
     public void onOpenDocument(PdfWriter writer, Document document) {
        total = writer.getDirectContent().createTemplate(30, 16);
    }
    
    @Override
     public void onStartPage(PdfWriter writer, Document document){
         PdfPTable table = new PdfPTable(1);
         // Se determina el ancho y altura de la tabla
         table.setTotalWidth(527);
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
         PdfPCell cel = new PdfPCell(new Phrase("Camino viejo a la rosita 305 Col. Salvarcar 32580 Bodegas Juárez A1",fuente1));
         cel.setBorder(0);
         cel.setHorizontalAlignment(Element.ALIGN_CENTER);
         BaseColor s = new BaseColor(255, 94, 8);
         cel.setBackgroundColor(s);
         PdfPCell cel1 = new PdfPCell(new Phrase("PIE DE PAGINA"
                 + "Tel 656-791-1365",fuente2));
         cel1.setBorder(0);
         cel1.setHorizontalAlignment(Element.ALIGN_CENTER);
         //            table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
         
         table.addCell(cel);
         
        // Esta linea escribe la tabla como encabezado
        table.writeSelectedRows(0, -1, 150, 15, writer.getDirectContent());
     }
     
    @Override
     public void onEndPage(PdfWriter writer, Document document) {
        
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
