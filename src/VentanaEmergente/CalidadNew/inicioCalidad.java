package VentanaEmergente.CalidadNew;

import java.awt.Color;
import java.beans.PropertyVetoException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JInternalFrame;
import pruebas.Calidad;
import pruebas.CalidadDebug;
import pruebas.CalidadNew;
import pruebas.Inicio1;

public class inicioCalidad extends javax.swing.JInternalFrame {

    Inicio1 inicio;
    String nombre;
    String numero;
    
    public void setWhite(){
        panel3i.setColorBackground(Color.white);
        panel3i1.setColorBackground(Color.white);
        panel3i2.setColorBackground(Color.white);
    }
    
    public void entrar(JInternalFrame c){
        inicio.jDesktopPane1.add(c);
        c.toFront();
        c.setLocation(inicio.jDesktopPane1.getWidth() / 2 - c.getWidth() / 2, inicio.jDesktopPane1.getHeight() / 2 - c.getHeight() / 2);
        try{
            c.setMaximum(true);
        }catch(PropertyVetoException e){
            Logger.getLogger(Inicio1.class.getName()).log(Level.SEVERE,null,e);
        }
        c.setVisible(true);
        this.dispose();
    }
    
    public inicioCalidad(Inicio1 inicio, String nombre, String numero) {
        initComponents();
        this.inicio = inicio;
        this.nombre = nombre;
        this.numero = numero;
        ((javax.swing.plaf.basic.BasicInternalFrameUI) this.getUI()).setNorthPane(null);
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel1 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        jPanel19 = new javax.swing.JPanel();
        btnSalir = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        panel3i = new rojeru_san.rspanel.RSPanelRound();
        lbl3i = new javax.swing.JButton();
        panel3i1 = new rojeru_san.rspanel.RSPanelRound();
        lbl3i1 = new javax.swing.JButton();
        panel3i2 = new rojeru_san.rspanel.RSPanelRound();
        lbl3i2 = new javax.swing.JButton();

        setBorder(null);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new java.awt.BorderLayout());

        jPanel11.setBackground(new java.awt.Color(255, 255, 255));
        jPanel11.setLayout(new java.awt.BorderLayout());

        jLabel17.setFont(new java.awt.Font("Roboto", 1, 40)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(0, 165, 252));
        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel17.setText("Inicio calidad");
        jPanel11.add(jLabel17, java.awt.BorderLayout.CENTER);

        jPanel19.setBackground(new java.awt.Color(255, 255, 255));

        btnSalir.setBackground(new java.awt.Color(255, 255, 255));

        jLabel11.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("X");
        jLabel11.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jLabel11.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel11MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel11MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel11MouseExited(evt);
            }
        });
        btnSalir.add(jLabel11);

        jPanel19.add(btnSalir);

        jPanel11.add(jPanel19, java.awt.BorderLayout.EAST);

        jPanel1.add(jPanel11, java.awt.BorderLayout.NORTH);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new java.awt.GridBagLayout());

        panel3i.setColorBackground(new java.awt.Color(255, 255, 255));
        panel3i.setColorBorde(new java.awt.Color(255, 255, 255));
        panel3i.setPreferredSize(new java.awt.Dimension(180, 180));
        panel3i.setLayout(new java.awt.BorderLayout());

        lbl3i.setFont(new java.awt.Font("Roboto", 1, 36)); // NOI18N
        lbl3i.setForeground(new java.awt.Color(0, 102, 0));
        lbl3i.setText("Nuevo");
        lbl3i.setBorder(null);
        lbl3i.setContentAreaFilled(false);
        lbl3i.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lbl3i.setFocusPainted(false);
        lbl3i.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        lbl3i.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        lbl3i.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lbl3iMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lbl3iMouseExited(evt);
            }
        });
        lbl3i.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lbl3iActionPerformed(evt);
            }
        });
        panel3i.add(lbl3i, java.awt.BorderLayout.CENTER);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(30, 45, 30, 45);
        jPanel2.add(panel3i, gridBagConstraints);

        panel3i1.setColorBackground(new java.awt.Color(255, 255, 255));
        panel3i1.setColorBorde(new java.awt.Color(255, 255, 255));
        panel3i1.setPreferredSize(new java.awt.Dimension(180, 180));
        panel3i1.setLayout(new java.awt.BorderLayout());

        lbl3i1.setFont(new java.awt.Font("Roboto", 1, 36)); // NOI18N
        lbl3i1.setForeground(new java.awt.Color(255, 102, 0));
        lbl3i1.setText("Anterior");
        lbl3i1.setBorder(null);
        lbl3i1.setContentAreaFilled(false);
        lbl3i1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lbl3i1.setFocusPainted(false);
        lbl3i1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        lbl3i1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        lbl3i1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lbl3i1MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lbl3i1MouseExited(evt);
            }
        });
        lbl3i1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lbl3i1ActionPerformed(evt);
            }
        });
        panel3i1.add(lbl3i1, java.awt.BorderLayout.CENTER);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(30, 45, 30, 45);
        jPanel2.add(panel3i1, gridBagConstraints);

        panel3i2.setColorBackground(new java.awt.Color(255, 255, 255));
        panel3i2.setColorBorde(new java.awt.Color(255, 255, 255));
        panel3i2.setPreferredSize(new java.awt.Dimension(180, 180));
        panel3i2.setLayout(new java.awt.BorderLayout());

        lbl3i2.setFont(new java.awt.Font("Roboto", 1, 36)); // NOI18N
        lbl3i2.setForeground(new java.awt.Color(0, 102, 204));
        lbl3i2.setText("Debug");
        lbl3i2.setBorder(null);
        lbl3i2.setContentAreaFilled(false);
        lbl3i2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lbl3i2.setFocusPainted(false);
        lbl3i2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        lbl3i2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        lbl3i2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lbl3i2MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lbl3i2MouseExited(evt);
            }
        });
        lbl3i2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lbl3i2ActionPerformed(evt);
            }
        });
        panel3i2.add(lbl3i2, java.awt.BorderLayout.CENTER);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(30, 45, 30, 45);
        jPanel2.add(panel3i2, gridBagConstraints);

        jPanel1.add(jPanel2, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel11MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel11MouseClicked
        dispose();
    }//GEN-LAST:event_jLabel11MouseClicked

    private void jLabel11MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel11MouseEntered
        btnSalir.setBackground(Color.red);
        jLabel11.setForeground(Color.white);
    }//GEN-LAST:event_jLabel11MouseEntered

    private void jLabel11MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel11MouseExited
        btnSalir.setBackground(Color.white);
        jLabel11.setForeground(Color.black);
    }//GEN-LAST:event_jLabel11MouseExited

    private void lbl3iMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl3iMouseEntered
        setWhite();
        panel3i.setColorBackground(new Color(57,255,40));
    }//GEN-LAST:event_lbl3iMouseEntered

    private void lbl3iMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl3iMouseExited
        setWhite();
    }//GEN-LAST:event_lbl3iMouseExited

    private void lbl3iActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lbl3iActionPerformed
        entrar(new CalidadNew(nombre, numero));
    }//GEN-LAST:event_lbl3iActionPerformed

    private void lbl3i1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl3i1MouseEntered
        setWhite();
        panel3i1.setColorBackground(new Color(57,255,40));
    }//GEN-LAST:event_lbl3i1MouseEntered

    private void lbl3i1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl3i1MouseExited
        setWhite();
    }//GEN-LAST:event_lbl3i1MouseExited

    private void lbl3i1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lbl3i1ActionPerformed
        entrar(new Calidad());
    }//GEN-LAST:event_lbl3i1ActionPerformed

    private void lbl3i2MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl3i2MouseEntered
        setWhite();
        panel3i2.setColorBackground(new Color(57,255,40));
    }//GEN-LAST:event_lbl3i2MouseEntered

    private void lbl3i2MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl3i2MouseExited
        setWhite();
    }//GEN-LAST:event_lbl3i2MouseExited

    private void lbl3i2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lbl3i2ActionPerformed
        entrar(new CalidadDebug(nombre, numero));
    }//GEN-LAST:event_lbl3i2ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel btnSalir;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    public javax.swing.JButton lbl3i;
    public javax.swing.JButton lbl3i1;
    public javax.swing.JButton lbl3i2;
    private rojeru_san.rspanel.RSPanelRound panel3i;
    private rojeru_san.rspanel.RSPanelRound panel3i1;
    private rojeru_san.rspanel.RSPanelRound panel3i2;
    // End of variables declaration//GEN-END:variables
}
