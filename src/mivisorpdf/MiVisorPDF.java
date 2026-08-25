package mivisorpdf;

import VO.ArchivosVO;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import com.sun.pdfview.PDFFile;
import com.sun.pdfview.PDFPage;
import javax.swing.JOptionPane;

public class MiVisorPDF {

    public ArrayList<ArchivosVO> leerPDF(String rutaPDF){
        File file = new File(rutaPDF);
        byte[] bi = null;
        ArrayList<ArchivosVO> ar = new ArrayList<ArchivosVO>();
        try {
            RandomAccessFile raf = new RandomAccessFile(file, "r");
            FileChannel channel = raf.getChannel();
            ByteBuffer buf = channel.map(FileChannel.MapMode.READ_ONLY, 0, channel.size());
            PDFFile pdffile = new PDFFile(buf);
            int numpag = pdffile.getNumPages();
            
            ByteArrayOutputStream baos = null;
            for (int i = 1; i <= numpag; i++) {
                PDFPage page = pdffile.getPage(i);
                
                Rectangle rect = new Rectangle(0, 0,
                        (int) page.getBBox().getWidth(),
                        (int) page.getBBox().getHeight());
                Image img = page.getImage(
                        rect.width * 2, rect.height * 2,
                        rect, 
                        null, 
                        true,
                        true 
                );
                
                BufferedImage bufferedImage = new BufferedImage(rect.width * 2, rect.height * 2, BufferedImage.TYPE_INT_RGB);
                Graphics g = bufferedImage.createGraphics();
                g.drawImage(img, 0, 0, null);
                g.dispose();

                baos = new ByteArrayOutputStream();
                ImageIO.write(bufferedImage, "PNG", baos);
                bi = baos.toByteArray();
                
                ArchivosVO po = new ArchivosVO();
                po.setIdArchivos(i);
                po.setArchivos(bi);
                ar.add(po);
//                -----

            }
            baos.close();
            buf.clear();
            channel.close();
            raf.close();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error: "+ex);
        }
        return ar;
    }

    public static void main(String[] args) {
        // TODO code application logic here
    }

}
