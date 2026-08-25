package VentanaEmergente.Cotizacion;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.ExceptionConverter;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPCellEvent;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class CabezeraCotizaciones extends PdfPageEventHelper {

    private String encabezado;
    private BaseColor color;
    PdfTemplate total;
    private int tot;
    public String empresa = " ";
    public String logo = " ";
    public String direccion = " ";
    public String correo = " ";
    public String rfc = " ";
    public String coti = " ";
    
    public String empresaCli = " ";
    public String direccionCli = " ";
    public String rfcCli = " ";
    
    public String fecha = " ";
    public String fechaVencimiento = " ";
    public String vendedor = " ";

    public PdfPCell borde(PdfPCell celda, float top, float bot, float left, float rig) {
        celda.setBorderWidthBottom(bot);
        celda.setBorderWidthTop(top);
        celda.setBorderWidthRight(rig);
        celda.setBorderWidthLeft(left);
        return celda;
    }

    public void onOpenDocument(PdfWriter writer, Document document) {
        total = writer.getDirectContent().createTemplate(30, 10);
    }

    @Override
    public void onStartPage(PdfWriter writer, Document document) {
        try {
            PdfPTable table = new PdfPTable(1);
            table.setTotalWidth(527);
            table.setLockedWidth(true);
            table.getDefaultCell().setBorder(0);

            com.itextpdf.text.Font fuente2 = new com.itextpdf.text.Font();
            fuente2.setSize(8);
            fuente2.setStyle(com.itextpdf.text.Font.BOLD);
            fuente2.setFamily("Roboto");
            fuente2.setColor(200,200,200);

            PdfPCell corr = new PdfPCell(new Paragraph(this.correo, fuente2));
            corr.setBorder(0);
            corr.setHorizontalAlignment(Element.ALIGN_CENTER);

            Paragraph pagePhrase = new Paragraph();
            pagePhrase.setAlignment(Element.ALIGN_CENTER);
            pagePhrase.add(new Chunk("Página " + writer.getPageNumber() + " de ", fuente2));

            Image totalPagesImage = Image.getInstance(total);
            pagePhrase.add(new Chunk(totalPagesImage, 0, 0));

            PdfPCell cel1 = new PdfPCell();
            cel1.setBorder(0);
            cel1.setHorizontalAlignment(Element.ALIGN_MIDDLE);
            cel1.setFixedHeight(20);
            
            cel1.addElement(pagePhrase);
            table.addCell(corr);
            table.addCell(cel1);

            table.writeSelectedRows(0, -1, 34, 40, writer.getDirectContent());

        } catch (BadElementException ex) {
            Logger.getLogger(CabezeraCotizaciones.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void onEndPage(PdfWriter writer, Document document) {
        PdfPTable table = new PdfPTable(1);
        try {
            table.setTotalWidth(527);
            table.getDefaultCell().setFixedHeight(25);
            String imagePath = getClass().getClassLoader().getResource("img/cir fondo.png").toString();
            Image img = Image.getInstance(imagePath);
            img.setAbsolutePosition(210, 700);

            Image img2 = Image.getInstance(logo);
            float an = img2.getWidth();
            float alt = img2.getHeight();
            float porcentaje = (120 * 100) / an;
            
            img2.scaleAbsolute((porcentaje * an) / 100, (porcentaje * alt) / 100);
            
            img2.setAbsolutePosition(40, 780);

            Image img3 = Image.getInstance(getClass().getClassLoader().getResource("img/cir fondo bajo.png").toString());
            img3.scaleAbsolute(300, 100);
            img3.setAbsolutePosition(0, -50);

            table.getDefaultCell().setBorder(0);

            com.itextpdf.text.Font fuente1 = new com.itextpdf.text.Font();
            com.itextpdf.text.Font fuente2 = new com.itextpdf.text.Font();
            com.itextpdf.text.Font fuente3 = new com.itextpdf.text.Font();
            com.itextpdf.text.Font fuente4 = new com.itextpdf.text.Font();
            com.itextpdf.text.Font fuente5 = new com.itextpdf.text.Font();
            com.itextpdf.text.Font fuente6 = new com.itextpdf.text.Font();
            com.itextpdf.text.Font fuente7 = new com.itextpdf.text.Font();
            com.itextpdf.text.Font fuente8 = new com.itextpdf.text.Font();

            fuente1.setSize(12);
            fuente1.setStyle(com.itextpdf.text.Font.BOLD);
            fuente1.setFamily("Trebuchet MS");
//            fuente1.setColor(255,255,255);

            fuente2.setSize(8);
//            fuente2.setStyle(com.itextpdf.text.Font.BOLD);
            fuente2.setFamily("Trebuchet MS");
//            fuente2.setColor(255,255,255);

            fuente3.setSize(8);
            fuente3.setStyle(com.itextpdf.text.Font.BOLD);
            fuente3.setFamily("Trebuchet MS");
            fuente3.setColor(0, 0, 0);

            fuente4.setSize(12);
            fuente4.setStyle(com.itextpdf.text.Font.BOLD);
            fuente4.setFamily("Trebuchet MS");
            fuente4.setColor(0, 0, 0);

            fuente5.setSize(8);
            fuente5.setFamily("Trebuchet MS");
            fuente5.setColor(0, 0, 0);

            fuente6.setSize(24);
            fuente6.setStyle(com.itextpdf.text.Font.BOLD);
            fuente6.setFamily("Trebuchet MS");
            fuente6.setColor(0, 164, 255);

            fuente7.setSize(10);
            fuente7.setStyle(com.itextpdf.text.Font.BOLD);
            fuente7.setFamily("Trebuchet MS");
            fuente7.setColor(68, 154, 213);

            fuente8.setSize(8);
            fuente8.setStyle(com.itextpdf.text.Font.BOLD);
            fuente8.setFamily("Trebuchet MS");
            fuente8.setColor(255, 255, 255);

            PdfPCell cel = new PdfPCell(new Phrase(empresa, fuente1));
            cel.setBorder(0);
            cel.setHorizontalAlignment(Element.ALIGN_RIGHT);

            PdfPCell cel1 = new PdfPCell(new Phrase(direccion, fuente2));
            cel1.setBorder(0);
            cel1.setHorizontalAlignment(Element.ALIGN_RIGHT);
            
            PdfPCell mirfc = new PdfPCell(new Phrase(rfc, fuente2));
            mirfc.setBorder(0);
            mirfc.setHorizontalAlignment(Element.ALIGN_RIGHT);
            
            PdfPCell micel = new PdfPCell(new Phrase(correo, fuente2));
            micel.setBorder(0);
            micel.setHorizontalAlignment(Element.ALIGN_RIGHT);

            table.addCell(cel);
            table.addCell(cel1);
            table.addCell(micel);
            table.addCell(mirfc);

            PdfPCell dc = new PdfPCell(new Phrase("Datos de Cliente", fuente4));
            dc.setBorder(0);
            dc.setHorizontalAlignment(Element.ALIGN_LEFT);

            PdfPCell cc = new PdfPCell(new Phrase(empresaCli, fuente5));
            cc.setBorder(0);
            cc.setHorizontalAlignment(Element.ALIGN_LEFT);

            PdfPCell cd = new PdfPCell(new Phrase(direccionCli, fuente5));
            cd.setBorder(0);
            cd.setHorizontalAlignment(Element.ALIGN_LEFT);

            PdfPCell cr = new PdfPCell(new Phrase(rfcCli, fuente5));
            cr.setBorder(0);
            cr.setHorizontalAlignment(Element.ALIGN_LEFT);
            
            String nomenclaturaCotizacion = empresa.substring(0,1).toUpperCase() + String.format("%05d", Integer.parseInt(coti));
            
            PdfPCell nc = new PdfPCell(new Phrase("Número de orden " + nomenclaturaCotizacion, fuente6));
            nc.setBorder(0);
            nc.setHorizontalAlignment(Element.ALIGN_RIGHT);

            PdfPTable tabInfo = new PdfPTable(3);
            tabInfo.getDefaultCell().setBorder(0);

            PdfPCell celda;
            String datInfo[] = {"Fecha de cotizacion", "Vencimiento", "Vendedor", fecha, fechaVencimiento, vendedor};
            for (int i = 0; i < datInfo.length; i++) {
                if (i < 3) {
                    celda = new PdfPCell(new Phrase(datInfo[i], fuente7));
                } else {
                    celda = new PdfPCell(new Phrase(datInfo[i], fuente5));
                }
                color = new BaseColor(230, 244, 252);
                celda.setBackgroundColor(color);
                celda.setCellEvent(getCellEvent(color));
                celda.setHorizontalAlignment(Element.ALIGN_CENTER);
                celda.setBorder(0);
                celda.setPadding(4);
                celda.setBackgroundColor(BaseColor.WHITE);

                tabInfo.addCell(celda);
            }

            PdfPCell ti = new PdfPCell(tabInfo);
            ti.setBorder(0);

            PdfPTable tabItems = new PdfPTable(6);
            float med[] = {40,300, 90, 100, 90, 90};
            tabItems.setTotalWidth(med);
            tabItems.getDefaultCell().setBorder(0);

            PdfPCell celd;
            String datItems[] = {"No","Descripcion", "Cantidad", "Precio Unitario", "Impuestos", "Importe"};
            for (String datItem : datItems) {
                celd = new PdfPCell(new Phrase(datItem, fuente8));
                color = new BaseColor(31, 116, 153);
                celd.setBackgroundColor(color);
                celd.setCellEvent(getCellEvent(color));
                celd.setHorizontalAlignment(Element.ALIGN_CENTER);
                celd.setBorder(0);
                celd.setPadding(8);
                celd.setBackgroundColor(BaseColor.WHITE);
                tabItems.addCell(celd);
            }

            PdfPCell it = new PdfPCell(tabItems);
            it.setBorder(0);

            table.addCell("");
            table.addCell("");
            table.addCell(dc);
            table.addCell(cc);
            table.addCell(cd);
            table.addCell(cr);
            table.addCell(nc);
            table.addCell(borde(new PdfPCell(new Paragraph(" ", fuente8)), 0, 0, 0, 0));
            table.addCell(ti);
            table.addCell(borde(new PdfPCell(new Paragraph(" ", fuente8)), 0, 0, 0, 0));
            table.addCell(it);
            table.addCell(borde(new PdfPCell(new Paragraph(" ", fuente8)), 0, 0, 0, 0));

            document.add(img);
            document.add(img2);
            document.add(img3);

            // Esta linea escribe la tabla como encabezado
            table.writeSelectedRows(0, -1, 34, 835, writer.getDirectContent());
        } catch (DocumentException de) {
            throw new ExceptionConverter(de);
        } catch (IOException ex) {
            Logger.getLogger(CabezeraCotizaciones.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "ERROR: " + ex, "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    public PdfPCellEvent getCellEvent(BaseColor color) {
        PdfPCellEvent cellEvent = (PdfPCell cell, Rectangle position, PdfContentByte[] canvases) -> {
            PdfContentByte canvas = canvases[PdfPTable.BACKGROUNDCANVAS];
            canvas.roundRectangle(
                    position.getLeft(),
                    position.getBottom(),
                    position.getWidth(),
                    position.getHeight(),
                    0
            );
            canvas.setColorFill(color);
            canvas.setColorStroke(color);
            canvas.fillStroke();
        };
        return cellEvent;
    }

    public void onCloseDocument(PdfWriter writer, Document document) {
        try {
            total.beginText();
            total.setColorFill(new BaseColor(200,200,200));
            total.setFontAndSize(BaseFont.createFont(), 8);
            total.setTextMatrix(0, 0);
            total.showText(String.valueOf(writer.getPageNumber()));
            total.endText();
        } catch (DocumentException ex) {
            Logger.getLogger(CabezeraCotizaciones.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(CabezeraCotizaciones.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getEncabezado() {
        return encabezado;
    }

    public void setEncabezado(String encabezado) {
        this.encabezado = encabezado;
    }
}
