package VentanaEmergente.Diseño;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JPanel;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.text.PDFTextStripperByArea;
import pruebas.Disenio1;

public class PDFSelectorPanel extends JPanel {

    private BufferedImage imagen;
    private float pdfWidth;
    private float pdfHeight;
    private double zoom = 1.0;
    private Point inicio;
    private Point fin;
    private Rectangle seleccionPantalla;
    public Rectangle rectangle;
    private int pdfX;
    private int pdfY;
    private int pdfAncho;
    private int pdfAlto;
    public JLabel lbl;
    public JLabel coordenadas;

    public PDFSelectorPanel(File file) {
        setBackground(Color.DARK_GRAY);
        MouseAdapter mouse = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                inicio = e.getPoint();
                fin = e.getPoint();
                seleccionPantalla = new Rectangle(inicio.x, inicio.y, 0, 0);
                repaint();
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                fin = e.getPoint();
                seleccionPantalla = crearRectangulo(inicio, fin);
                repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                try (PDDocument doc = PDDocument.load(file)) {
                    fin = e.getPoint();
                    seleccionPantalla = crearRectangulo(inicio, fin);
                    convertirCoordenadasPDF();
                    repaint();
                    PDFTextStripperByArea textStripper = new PDFTextStripperByArea();
                    Rectangle rect = getPDFRectangle();
                    coordenadas.setText(pdfX + "," + pdfY + "," + pdfAncho + "," + pdfAlto);
                    rectangle = rect;
                    textStripper.addRegion("myRegion", rect);
                    PDPage page = doc.getPage(0);
                    textStripper.extractRegions(page);
                    String extractedText = textStripper.getTextForRegion("myRegion");
                    String remplazo = extractedText.replace("*", "");
                    lbl.setText(remplazo);
                } catch (IOException ex) {
                    Logger.getLogger(Disenio1.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };

        addMouseListener(mouse);
        addMouseMotionListener(mouse);
    }

    private Rectangle crearRectangulo(Point inicio, Point fin) {
        int x = Math.min(inicio.x, fin.x);
        int y = Math.min(inicio.y, fin.y);
        int ancho = Math.abs(fin.x - inicio.x);
        int alto = Math.abs(fin.y - inicio.y);
        return new Rectangle(x, y, ancho, alto);
    }

    private void convertirCoordenadasPDF() {
        if (seleccionPantalla == null || imagen == null) {
            return;
        }
        float xImagen = (float) (seleccionPantalla.x / zoom);
        float yImagen = (float) (seleccionPantalla.y / zoom);
        float anchoImagen = (float) (seleccionPantalla.width / zoom);
        float altoImagen = (float) (seleccionPantalla.height / zoom);
        float escalaX = pdfWidth / imagen.getWidth();
        float escalaY = pdfHeight / imagen.getHeight();
        pdfX = Math.round(xImagen * escalaX);
        pdfY = Math.round(yImagen * escalaY);
        pdfAncho = Math.round(anchoImagen * escalaX);
        pdfAlto = Math.round(altoImagen * escalaY);
    }

    public void setPDFImage(BufferedImage imagen, float pdfWidth, float pdfHeight) {
        this.imagen = imagen;
        this.pdfWidth = pdfWidth;
        this.pdfHeight = pdfHeight;
        setPreferredSize(new Dimension((int) (imagen.getWidth() * zoom), (int) (imagen.getHeight() * zoom)));
        revalidate();
        repaint();
    }

    public int getPDFX() {
        return pdfX;
    }

    public int getPDFY() {
        return pdfY;
    }

    public int getPDFAncho() {
        return pdfAncho;
    }

    public int getPDFAlto() {
        return pdfAlto;
    }

    public Rectangle getPDFRectangle() {
        return new Rectangle(pdfX, pdfY, pdfAncho, pdfAlto);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (imagen == null) {
            return;
        }
        Graphics2D g2 = (Graphics2D) g.create();
        int ancho = (int) (imagen.getWidth() * zoom);
        int alto = (int) (imagen.getHeight() * zoom);
        g2.drawImage(imagen, 0, 0, ancho, alto, null);
        if (seleccionPantalla != null) {
            g2.setColor(new Color(0, 100, 255, 50));
            g2.fillRect(seleccionPantalla.x, seleccionPantalla.y, seleccionPantalla.width, seleccionPantalla.height);
            g2.setColor(Color.BLUE);
            g2.setStroke(new BasicStroke(2));
            g2.drawRect(seleccionPantalla.x, seleccionPantalla.y, seleccionPantalla.width, seleccionPantalla.height);
        }
        g2.dispose();
    }
}
