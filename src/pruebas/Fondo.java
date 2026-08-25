package pruebas;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.border.Border;

public class Fondo implements Border{
    
    public BufferedImage image;
    

    public Fondo(BufferedImage image) {
        this.image = image;}
        
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int heigth){
    int x0 = (int) ((width));
    int y0 = (int) ((heigth));
    g.drawImage(image, x0, y0, null);
    }
    public Insets getBorderInsets(Component c){
    return new Insets(0,0,0,0);
    }
    public boolean isBorderOpaque(){
    return false;
    }
    
}
