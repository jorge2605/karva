package VentanaEmergente.Costos;

import java.awt.Color;
import java.awt.Shape;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;

public class verPdf extends javax.swing.JFrame {

    int cont = 0; 
    int x;
    int y;
    
    public void setPdf(String ruta) throws IOException{
        PDDocument document = PDDocument.load(new File(ruta));
        PDFRenderer renderer = new PDFRenderer(document);
        BufferedImage image = renderer.renderImage(0);
        this.setBounds(0,0,image.getWidth() + 5, image.getHeight() + 35);
        ImageIcon icon = new ImageIcon(image);
        lblImg.setIcon(icon);

        document.close();
    }
    
    public final void setShape(){
        Shape shape = new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 50, 50);
        this.setShape(shape);
    }
    
    public verPdf() {
        initComponents();
        pnl.setBackground(new Color(0,0,0,0));
        setShape();
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                setShape();
            }
        });
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel1 = new javax.swing.JPanel();
        pnlLabels = new javax.swing.JPanel();
        pnl = new javax.swing.JPanel();
        lblImg = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        pnlX = new javax.swing.JPanel();
        lblX = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));
        setUndecorated(true);
        setPreferredSize(new java.awt.Dimension(1045, 566));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new java.awt.BorderLayout());

        pnlLabels.setBackground(new java.awt.Color(255, 255, 255));
        pnlLabels.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnlLabelsMouseClicked(evt);
            }
        });
        java.awt.GridBagLayout pnlLabelsLayout = new java.awt.GridBagLayout();
        pnlLabelsLayout.columnWidths = new int[] {0};
        pnlLabelsLayout.rowHeights = new int[] {0};
        pnlLabelsLayout.columnWeights = new double[] {1.0};
        pnlLabelsLayout.rowWeights = new double[] {1.0};
        pnlLabels.setLayout(pnlLabelsLayout);

        pnl.setBackground(new java.awt.Color(255, 255, 255));
        pnl.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                pnlMouseDragged(evt);
            }
        });
        pnl.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnlMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                pnlMousePressed(evt);
            }
        });

        javax.swing.GroupLayout pnlLayout = new javax.swing.GroupLayout(pnl);
        pnl.setLayout(pnlLayout);
        pnlLayout.setHorizontalGroup(
            pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1045, Short.MAX_VALUE)
        );
        pnlLayout.setVerticalGroup(
            pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 531, Short.MAX_VALUE)
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        pnlLabels.add(pnl, gridBagConstraints);

        lblImg.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/towi_Redondo.png"))); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        pnlLabels.add(lblImg, gridBagConstraints);

        jPanel1.add(pnlLabels, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new java.awt.BorderLayout());

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                jPanel3MouseDragged(evt);
            }
        });
        jPanel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel3MouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jPanel3MousePressed(evt);
            }
        });

        jLabel3.setText(" ");
        jPanel3.add(jLabel3);

        jPanel2.add(jPanel3, java.awt.BorderLayout.CENTER);

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        pnlX.setBackground(new java.awt.Color(255, 255, 255));

        lblX.setBackground(new java.awt.Color(255, 255, 255));
        lblX.setFont(new java.awt.Font("Lexend", 0, 12)); // NOI18N
        lblX.setText(" X ");
        lblX.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblXMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblXMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblXMouseExited(evt);
            }
        });
        pnlX.add(lblX);

        jPanel4.add(pnlX);

        jPanel2.add(jPanel4, java.awt.BorderLayout.EAST);

        getContentPane().add(jPanel2, java.awt.BorderLayout.PAGE_START);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void pnlLabelsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlLabelsMouseClicked
        
    }//GEN-LAST:event_pnlLabelsMouseClicked

    private void pnlMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlMousePressed
        
    }//GEN-LAST:event_pnlMousePressed

    private void pnlMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlMouseDragged
        
    }//GEN-LAST:event_pnlMouseDragged

    private void pnlMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlMouseClicked
        JLabel label = new JLabel();
        label.setText(String.valueOf(cont));
        label.setFont(new java.awt.Font("Segoe UI Black", 1, 14)); // NOI18N
        label.setForeground(new java.awt.Color(0, 0, 255));
        pnl.add(label);
        label.setBounds(evt.getX() - 5, evt.getY() - 16, 30, 20);
        revalidate();
        repaint();
        cont++;
    }//GEN-LAST:event_pnlMouseClicked

    private void jPanel3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel3MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jPanel3MouseClicked

    private void jPanel3MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel3MousePressed
        x = evt.getX();
        y = evt.getY();
    }//GEN-LAST:event_jPanel3MousePressed

    private void jPanel3MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel3MouseDragged
        int xx = evt.getXOnScreen();
        int yy = evt.getYOnScreen();
        this.setLocation(xx - (x), yy - y);
    }//GEN-LAST:event_jPanel3MouseDragged

    private void lblXMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblXMouseEntered
        pnlX.setBackground(Color.red);
    }//GEN-LAST:event_lblXMouseEntered

    private void lblXMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblXMouseExited
        pnlX.setBackground(Color.white);
    }//GEN-LAST:event_lblXMouseExited

    private void lblXMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblXMouseClicked
        this.dispose();
    }//GEN-LAST:event_lblXMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(verPdf.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(verPdf.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(verPdf.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(verPdf.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new verPdf().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JLabel lblImg;
    private javax.swing.JLabel lblX;
    private javax.swing.JPanel pnl;
    private javax.swing.JPanel pnlLabels;
    private javax.swing.JPanel pnlX;
    // End of variables declaration//GEN-END:variables
}
