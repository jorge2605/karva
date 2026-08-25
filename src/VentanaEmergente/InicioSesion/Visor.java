package VentanaEmergente.InicioSesion;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Stack;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import scrollPane.PanelRound;

public class Visor extends javax.swing.JDialog {

    public Stack<String> rutas;
    public PanelRound paneles[];
    public int seleccionado = 0;
    
    public void setSeleccionado(){
        for (PanelRound panele : paneles) {
            panele.setBackground(new Color(220,220,220));
        }
        paneles[seleccionado].setBackground(new Color(0,102,255));
    }
    
    public void verImagenes(){
        int cont = 0;
        paneles = new PanelRound[rutas.size()];
        for (int i = 0; i < rutas.size(); i++) {
            int sel = i;
            String ruta = rutas.get(i);
            ImageIcon image1 = new ImageIcon((ruta));
            Image scaledImage1 = image1.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
            JLabel imageLabel1 = new JLabel(new ImageIcon(scaledImage1));
            imageLabel1.setCursor(new Cursor(Cursor.HAND_CURSOR));
            paneles[i] = new PanelRound();
            paneles[i].setBackground(new Color(222,222,222));
            if(cont == 0){
                lblImagen.setIcon(new ImageIcon((ruta)));
                paneles[0].setBackground(new Color(0,102,255));
            }
            imageLabel1.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                }

                @Override
                public void mousePressed(MouseEvent e) {
                    lblImagen.removeAll();
                    revalidate();
                    repaint();
                    lblImagen.setIcon(new ImageIcon((ruta)));
                    seleccionado = sel;
                    setSeleccionado();
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                }

                @Override
                public void mouseExited(MouseEvent e) {
                }
            });
            paneles[i].setRoundBottomLeft(30);
            paneles[i].setRoundTopLeft(30);
            paneles[i].setRoundBottomRight(30);
            paneles[i].setRoundTopRight(30);
            
            paneles[i].add(imageLabel1);
            jPanel5.add(paneles[i]);
            revalidate();
            repaint();
            cont++;
        }
    }
    
    public Visor(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setBounds(0, 0, screenSize.width, screenSize.height);
        this.setBackground(new Color(0,0,0,0));
        jPanel2.setBackground(new Color(0,0,0,0));
        jPanel3.setBackground(new Color(255,153,153));
        jPanel4.setBackground(new Color(0,0,0,0));
        jPanel5.setBackground(new Color(0,0,0,0));
        pnlImg.setBackground(new Color(0,0,0,0));
        Inicio.setBackground(new Color(0,0,0,0.5f));
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Inicio = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        pnlImg = new javax.swing.JPanel();
        lblImagen = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                formKeyPressed(evt);
            }
        });

        Inicio.setLayout(new java.awt.BorderLayout());

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        jPanel3.setBackground(new java.awt.Color(255, 153, 153));
        jPanel3.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        jLabel1.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        jLabel1.setText(" X ");
        jLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel1MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel1MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel1MouseExited(evt);
            }
        });
        jPanel3.add(jLabel1);

        jPanel2.add(jPanel3);

        Inicio.add(jPanel2, java.awt.BorderLayout.PAGE_START);

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setLayout(new java.awt.BorderLayout());

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.add(jPanel5, java.awt.BorderLayout.PAGE_END);

        pnlImg.setBackground(new java.awt.Color(255, 255, 255));
        pnlImg.setLayout(new java.awt.BorderLayout());

        lblImagen.setForeground(new java.awt.Color(0, 102, 255));
        lblImagen.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        pnlImg.add(lblImagen, java.awt.BorderLayout.CENTER);

        jPanel4.add(pnlImg, java.awt.BorderLayout.CENTER);

        Inicio.add(jPanel4, java.awt.BorderLayout.CENTER);

        getContentPane().add(Inicio, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseClicked
        this.dispose();
    }//GEN-LAST:event_jLabel1MouseClicked

    private void jLabel1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseEntered
        jPanel3.setBackground(Color.red);
    }//GEN-LAST:event_jLabel1MouseEntered

    private void jLabel1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseExited
        jPanel3.setBackground(new Color(255,153,153));
        revalidate();
        repaint();
    }//GEN-LAST:event_jLabel1MouseExited

    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed
        switch (evt.getKeyCode()) {
            case KeyEvent.VK_ESCAPE:
                dispose();
                break;
            case KeyEvent.VK_RIGHT:
                if((seleccionado + 2) > rutas.size()){
                    seleccionado = 0;
                }else{
                    seleccionado++;
                }
                lblImagen.removeAll();
                revalidate();
                repaint();
                lblImagen.setIcon(new ImageIcon((rutas.get(seleccionado))));
                setSeleccionado();
                break;
            case KeyEvent.VK_LEFT:
                if((seleccionado - 1) < 0){
                    seleccionado = rutas.size() - 1;
                }else{
                    seleccionado--;
                }
                lblImagen.removeAll();
                revalidate();
                repaint();
                lblImagen.setIcon(new ImageIcon((rutas.get(seleccionado))));
                setSeleccionado();
                break;
            default:
                break;
        }
    }//GEN-LAST:event_formKeyPressed

    public static void main(String args[]) {
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Visor dialog = new Visor(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Inicio;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JLabel lblImagen;
    private javax.swing.JPanel pnlImg;
    // End of variables declaration//GEN-END:variables
}
