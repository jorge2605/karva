package VentanaEmergente.CalidadNew;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Errores extends java.awt.Dialog implements MouseListener {

    boolean band = true;
    int x1, y1;
    
    public Errores(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        btnError1 = new javax.swing.JButton();
        btnError2 = new javax.swing.JButton();
        btnError3 = new javax.swing.JButton();
        btnError4 = new javax.swing.JButton();
        btnError5 = new javax.swing.JButton();
        btnError6 = new javax.swing.JButton();
        btnError7 = new javax.swing.JButton();
        btnError8 = new javax.swing.JButton();
        btnError9 = new javax.swing.JButton();
        btnError10 = new javax.swing.JButton();

        setUndecorated(true);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                closeDialog(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(204, 204, 204)));
        jPanel1.setMaximumSize(new java.awt.Dimension(127, 285));
        jPanel1.setMinimumSize(new java.awt.Dimension(127, 285));
        jPanel1.setPreferredSize(new java.awt.Dimension(127, 285));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setMaximumSize(new java.awt.Dimension(119, 277));
        jPanel2.setMinimumSize(new java.awt.Dimension(119, 277));
        jPanel2.setName(""); // NOI18N
        jPanel2.setPreferredSize(new java.awt.Dimension(119, 277));

        btnError1.setBackground(new java.awt.Color(255, 255, 255));
        btnError1.setFont(new java.awt.Font("Roboto", 1, 16)); // NOI18N
        btnError1.setForeground(new java.awt.Color(0, 0, 0));
        btnError1.setText("ERROR 1");
        btnError1.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(204, 204, 204)));
        btnError1.setContentAreaFilled(false);
        btnError1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnError1.setFocusPainted(false);
        jPanel2.add(btnError1);

        btnError2.setBackground(new java.awt.Color(255, 255, 255));
        btnError2.setFont(new java.awt.Font("Roboto", 1, 16)); // NOI18N
        btnError2.setForeground(new java.awt.Color(0, 0, 0));
        btnError2.setText("ERROR 2");
        btnError2.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(204, 204, 204)));
        btnError2.setContentAreaFilled(false);
        btnError2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnError2.setFocusPainted(false);
        jPanel2.add(btnError2);

        btnError3.setBackground(new java.awt.Color(255, 255, 255));
        btnError3.setFont(new java.awt.Font("Roboto", 1, 16)); // NOI18N
        btnError3.setForeground(new java.awt.Color(0, 0, 0));
        btnError3.setText("ERROR 3");
        btnError3.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(204, 204, 204)));
        btnError3.setContentAreaFilled(false);
        btnError3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnError3.setFocusPainted(false);
        jPanel2.add(btnError3);

        btnError4.setBackground(new java.awt.Color(255, 255, 255));
        btnError4.setFont(new java.awt.Font("Roboto", 1, 16)); // NOI18N
        btnError4.setForeground(new java.awt.Color(0, 0, 0));
        btnError4.setText("ERROR 4");
        btnError4.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(204, 204, 204)));
        btnError4.setContentAreaFilled(false);
        btnError4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnError4.setFocusPainted(false);
        jPanel2.add(btnError4);

        btnError5.setBackground(new java.awt.Color(255, 255, 255));
        btnError5.setFont(new java.awt.Font("Roboto", 1, 16)); // NOI18N
        btnError5.setForeground(new java.awt.Color(0, 0, 0));
        btnError5.setText("ERROR 5");
        btnError5.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(204, 204, 204)));
        btnError5.setContentAreaFilled(false);
        btnError5.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnError5.setFocusPainted(false);
        jPanel2.add(btnError5);

        btnError6.setBackground(new java.awt.Color(255, 255, 255));
        btnError6.setFont(new java.awt.Font("Roboto", 1, 16)); // NOI18N
        btnError6.setForeground(new java.awt.Color(0, 0, 0));
        btnError6.setText("ERROR 6");
        btnError6.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(204, 204, 204)));
        btnError6.setContentAreaFilled(false);
        btnError6.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnError6.setFocusPainted(false);
        jPanel2.add(btnError6);

        btnError7.setBackground(new java.awt.Color(255, 255, 255));
        btnError7.setFont(new java.awt.Font("Roboto", 1, 16)); // NOI18N
        btnError7.setForeground(new java.awt.Color(0, 0, 0));
        btnError7.setText("ERROR 7");
        btnError7.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(204, 204, 204)));
        btnError7.setContentAreaFilled(false);
        btnError7.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnError7.setFocusPainted(false);
        jPanel2.add(btnError7);

        btnError8.setBackground(new java.awt.Color(255, 255, 255));
        btnError8.setFont(new java.awt.Font("Roboto", 1, 16)); // NOI18N
        btnError8.setForeground(new java.awt.Color(0, 0, 0));
        btnError8.setText("ERROR 8");
        btnError8.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(204, 204, 204)));
        btnError8.setContentAreaFilled(false);
        btnError8.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnError8.setFocusPainted(false);
        jPanel2.add(btnError8);

        btnError9.setBackground(new java.awt.Color(255, 255, 255));
        btnError9.setFont(new java.awt.Font("Roboto", 1, 16)); // NOI18N
        btnError9.setForeground(new java.awt.Color(0, 0, 0));
        btnError9.setText("ERROR 9");
        btnError9.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(204, 204, 204)));
        btnError9.setContentAreaFilled(false);
        btnError9.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnError9.setFocusPainted(false);
        btnError9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnError9ActionPerformed(evt);
            }
        });
        jPanel2.add(btnError9);

        btnError10.setBackground(new java.awt.Color(255, 255, 255));
        btnError10.setFont(new java.awt.Font("Roboto", 1, 16)); // NOI18N
        btnError10.setForeground(new java.awt.Color(0, 0, 0));
        btnError10.setText("ERROR 10");
        btnError10.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(204, 204, 204)));
        btnError10.setContentAreaFilled(false);
        btnError10.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnError10.setFocusPainted(false);
        jPanel2.add(btnError10);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 125, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 283, Short.MAX_VALUE)
        );

        add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void closeDialog(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_closeDialog
        setVisible(false);
        dispose();
    }//GEN-LAST:event_closeDialog

    private void btnError9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnError9ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnError9ActionPerformed

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Errores dialog = new Errores(new java.awt.Frame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton btnError1;
    public javax.swing.JButton btnError10;
    public javax.swing.JButton btnError2;
    public javax.swing.JButton btnError3;
    public javax.swing.JButton btnError4;
    public javax.swing.JButton btnError5;
    public javax.swing.JButton btnError6;
    public javax.swing.JButton btnError7;
    public javax.swing.JButton btnError8;
    public javax.swing.JButton btnError9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    // End of variables declaration//GEN-END:variables

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
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
}
