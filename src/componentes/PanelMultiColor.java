package componentes;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Stack;
import javax.swing.JPanel;

public class PanelMultiColor extends JPanel {
    
    public Stack<Color> colors;
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        if (colors == null || colors.isEmpty()) {
            return;
        }

        int width = getWidth();
        int height = getHeight();
        int total = colors.size();

        int baseHeight = height / total;
        int remainder = height % total;

        int y = 0;

        for (int i = 0; i < total; i++) {
            int h = baseHeight;
            if (i == total - 1) {
                h += remainder; // absorbe píxeles sobrantes
            }

            g2d.setColor(colors.get(i));
            g2d.fillRect(0, y, width, h);
            y += h;
        }
    }
    
    public PanelMultiColor(Stack<Color> color){
        colors = color;
    }
}
