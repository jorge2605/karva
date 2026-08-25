package scrollPane;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JButton;

public class BotonRedondo extends JButton implements MouseListener {
    
    private int radius;
    private int thickness;
    private int aumento;
    private Color borderColor;
    private Color borderColorSelected;
    private Color color;

    public BotonRedondo() {
        this.radius = 10;
        this.thickness = 2;
        this.borderColor = Color.RED;
        this.borderColorSelected = Color.RED;
        this.color = borderColor;
        this.aumento = 0;
        setContentAreaFilled(false);
        setFocusPainted(false);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        addMouseListener(this);
    }

    public void setRadius(int radius) {
        this.radius = radius;
        repaint();
    }

    public int getRadius() {
        return radius;
    }

    public void setThickness(int thickness) {
        this.thickness = thickness;
        repaint();
    }

    public int getThickness() {
        return thickness;
    }

    public void setBorderColor(Color color) {
        this.borderColor = color;
        repaint();
    }

    public Color getBorderColor() {
        return borderColor;
    }

    public void setBorderColorSelected(Color color) {
        this.borderColorSelected = color;
        repaint();
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
        repaint();
    }

    public Color getBorderColorSelected() {
        return borderColorSelected;
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); // Habilitar suavizado de bordes
        g.setColor(getBackground());
        g.fillRoundRect((thickness + aumento) / 2, (thickness + aumento) / 2, getWidth() - (thickness + aumento), getHeight() - (thickness + aumento), radius, radius);
        g.setColor(color);
        g2.setStroke(new BasicStroke(thickness + aumento));
        g.drawRoundRect((thickness + aumento) / 2, (thickness + aumento) / 2, getWidth() - (thickness + aumento), getHeight() - (thickness + aumento), radius, radius);
        super.paintComponent(g);
    }

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {
        color = getBorderColorSelected();
        aumento = 1;
        repaint();
        setForeground(getBorderColorSelected());
        repaint();
    }

    @Override
    public void mouseExited(MouseEvent e) {
        color = getBorderColor();
        aumento = 0;
        repaint();
        setForeground(getBorderColor());
        repaint();
    }
}