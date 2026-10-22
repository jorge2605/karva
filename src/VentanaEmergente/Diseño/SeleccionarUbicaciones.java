package VentanaEmergente.Diseño;

import java.awt.BorderLayout;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.rendering.PDFRenderer;

public class SeleccionarUbicaciones extends javax.swing.JDialog {

    public Rectangle rectangle;
    public PDDocument doc ;
    public PDFSelectorPanel selector;
    public boolean tabla = false;
    public List<String[]> tablaBOM;

    public final void agregarPanel(File file) {
        try {
            selector = new PDFSelectorPanel(file);
            selector.tabla = this.tabla;
            selector.lbl = lblCoordenadas;
            selector.coordenadas = lblCoo;
            jPanelPDF.setLayout(new BorderLayout());
            JScrollPane scroll = new JScrollPane(selector);
            scroll.getVerticalScrollBar().setUnitIncrement(15);
            scroll.getHorizontalScrollBar().setUnitIncrement(15);
            jPanelPDF.add(scroll, BorderLayout.CENTER);
            doc = PDDocument.load(file);
            PDPage page = doc.getPage(0);
            float pdfWidth = page.getMediaBox().getWidth();
            float pdfHeight = page.getMediaBox().getHeight();
            PDFRenderer renderer = new PDFRenderer(doc);
            BufferedImage imagen = renderer.renderImageWithDPI(0, 150);
            selector.setPDFImage(imagen, pdfWidth, pdfHeight);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error al ver PDF: " + ex, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public SeleccionarUbicaciones(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        lblCoo.setVisible(false);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        lblCoordenadas1 = new javax.swing.JLabel();
        lblCoordenadas = new javax.swing.JLabel();
        lblCoo = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jPanelPDF = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jButton4 = new javax.swing.JButton();
        lblZoom = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new java.awt.BorderLayout(20, 20));

        jPanel3.setBackground(new java.awt.Color(250, 250, 250));
        jPanel3.setLayout(new java.awt.GridBagLayout());

        lblCoordenadas1.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        lblCoordenadas1.setText("Texto seleccionado:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 1;
        jPanel3.add(lblCoordenadas1, gridBagConstraints);

        lblCoordenadas.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 2;
        jPanel3.add(lblCoordenadas, gridBagConstraints);

        lblCoo.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 2;
        jPanel3.add(lblCoo, gridBagConstraints);

        jLabel12.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(0, 165, 252));
        jLabel12.setText("Seleccionar ubicacion de informacion");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 0;
        jPanel3.add(jLabel12, gridBagConstraints);

        jPanel1.add(jPanel3, java.awt.BorderLayout.NORTH);

        jPanelPDF.setBackground(new java.awt.Color(240, 240, 240));

        javax.swing.GroupLayout jPanelPDFLayout = new javax.swing.GroupLayout(jPanelPDF);
        jPanelPDF.setLayout(jPanelPDFLayout);
        jPanelPDFLayout.setHorizontalGroup(
            jPanelPDFLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1218, Short.MAX_VALUE)
        );
        jPanelPDFLayout.setVerticalGroup(
            jPanelPDFLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 538, Short.MAX_VALUE)
        );

        jPanel1.add(jPanelPDF, java.awt.BorderLayout.CENTER);

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 50, 5));

        jButton4.setBackground(new java.awt.Color(255, 255, 255));
        jButton4.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jButton4.setForeground(new java.awt.Color(0, 102, 255));
        jButton4.setText("-");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jPanel4.add(jButton4);

        lblZoom.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        lblZoom.setForeground(new java.awt.Color(51, 51, 51));
        lblZoom.setText("1.0");
        jPanel4.add(lblZoom);

        jButton3.setBackground(new java.awt.Color(255, 255, 255));
        jButton3.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jButton3.setForeground(new java.awt.Color(0, 102, 255));
        jButton3.setText("+");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel4.add(jButton3);

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IconoC/cheque (1).png"))); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel4.add(jButton1);

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IconoC/cancelar.png"))); // NOI18N
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel4.add(jButton2);

        jPanel1.add(jPanel4, java.awt.BorderLayout.PAGE_END);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        dispose();
        this.tablaBOM = selector.tablaBOM;
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        try {
            lblCoo.setText("");
            doc.close();
            dispose();
        } catch (IOException ex) {
            Logger.getLogger(SeleccionarUbicaciones.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        lblCoo.setText("");
    }//GEN-LAST:event_formWindowClosing

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        selector.zoom -= 0.1;
        selector.revalidate();
        selector.repaint();
        DecimalFormat df = new DecimalFormat("#.##");
        lblZoom.setText(""+df.format(selector.zoom));
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        selector.zoom += 0.1; 
        selector.revalidate();
        selector.repaint();
        lblZoom.setText(""+selector.zoom);
    }//GEN-LAST:event_jButton3ActionPerformed

    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                SeleccionarUbicaciones dialog = new SeleccionarUbicaciones(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanelPDF;
    public javax.swing.JLabel lblCoo;
    public javax.swing.JLabel lblCoordenadas;
    public javax.swing.JLabel lblCoordenadas1;
    private javax.swing.JLabel lblZoom;
    // End of variables declaration//GEN-END:variables
}
