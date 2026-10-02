package grafica;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Arc2D;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;

public class PieChartPanel extends JPanel {

    private final List<Rebanada> rebanadas = new ArrayList<>();
    private int grosorBorde = 3;

    public PieChartPanel() {
        setOpaque(false);
    }

    public void agregarRebanada(String nombre, double valor, Color color) {
        if (valor <= 0) {
            return;
        }
        rebanadas.add(new Rebanada(nombre, valor, color));
        repaint();
    }

    public void limpiar() {
        rebanadas.clear();
        repaint();
    }

    public void setGrosorBorde(int grosor) {
        this.grosorBorde = grosor;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (rebanadas.isEmpty()) {
            return;
        }
        Graphics2D g2 = (Graphics2D) g.create();

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        int ancho = getWidth();
        int alto = getHeight();

        if (ancho <= 0 || alto <= 0) {
            g2.dispose();
            return;
        }
        int margen = Math.max(10, Math.min(ancho, alto) / 15);
        int size = Math.min(ancho - margen * 2, alto - margen * 2);

        if (size <= 0) {
            g2.dispose();
            return;
        }

        int x = (ancho - size) / 2;
        int y = (alto - size) / 2;
        double total = 0;

        for (Rebanada r : rebanadas) {
            total += r.valor;
        }

        if (total <= 0) {
            g2.dispose();
            return;
        }

        double anguloInicial = 90;

        for (Rebanada r : rebanadas) {
            double porcentaje = r.valor / total;
            double angulo = porcentaje * 360;

            g2.setColor(r.color);
            g2.fill(new Arc2D.Double(x, y, size, size, anguloInicial, -angulo, Arc2D.PIE));

            double anguloMedio = anguloInicial - (angulo / 2);
            double radianes = Math.toRadians(anguloMedio);
            double distancia = size * 0.32;
            int centroX = x + size / 2;
            int centroY = y + size / 2;
            int textoX = (int) (centroX + Math.cos(radianes) * distancia);
            int textoY = (int) (centroY - Math.sin(radianes) * distancia);
            double porcentajeReal = porcentaje * 100;
            String textoPorcentaje = String.format("%.0f%%", porcentajeReal);
            g2.setColor(Color.WHITE);
            g2.setFont(new Font("Roboto", Font.BOLD, Math.max(11, size / 15)));
            FontMetrics fm = g2.getFontMetrics();
            int porcentajeX = textoX - fm.stringWidth(textoPorcentaje) / 2;
            g2.drawString(textoPorcentaje, porcentajeX, textoY);
            g2.setFont(new Font("Roboto", Font.PLAIN, Math.max(9, size / 22)));
            fm = g2.getFontMetrics();
            int nombreX = textoX - fm.stringWidth(r.nombre) / 2;
            g2.drawString(r.nombre, nombreX, textoY + fm.getHeight());
            g2.drawString(String.valueOf(r.valor), nombreX + 17, textoY + fm.getHeight() + 15);
            if (grosorBorde > 0) {
                g2.setColor(Color.WHITE);
                g2.setStroke(new BasicStroke(grosorBorde));
                g2.draw(new Arc2D.Double(x, y, size, size, anguloInicial, -angulo, Arc2D.PIE));
            }
            anguloInicial -= angulo;
        }
        g2.dispose();
    }

    private static class Rebanada {

        String nombre;
        double valor;
        Color color;

        public Rebanada(String nombre, double valor, Color color) {
            this.nombre = nombre;
            this.valor = valor;
            this.color = color;
        }
    }
}
