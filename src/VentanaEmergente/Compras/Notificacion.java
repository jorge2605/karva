package VentanaEmergente.Compras;

import java.awt.Color;
import net.sf.jcarrierpigeon.Notification;
import net.sf.jcarrierpigeon.NotificationQueue;
import net.sf.jcarrierpigeon.WindowPosition;

public class Notificacion extends javax.swing.JFrame {

    public Notificacion() {
        initComponents();
        Notification obj = new Notification(this, WindowPosition.BOTTOMRIGHT,0,0,5000);
        NotificationQueue val = new NotificationQueue();
        val.add(obj);
        this.setBackground(new Color(0,0,0,0));
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panel1 = new scrollPane.PanelRound();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        rSButtonRoundRipple1 = new rojeru_san.rsbutton.RSButtonRoundRipple();
        rSButtonRoundRipple2 = new rojeru_san.rsbutton.RSButtonRoundRipple();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setPreferredSize(new java.awt.Dimension(420, 139));
        setType(java.awt.Window.Type.UTILITY);

        panel1.setBackground(new java.awt.Color(224, 224, 224));
        panel1.setPreferredSize(new java.awt.Dimension(980, 165));
        panel1.setRoundBottomLeft(30);
        panel1.setRoundBottomRight(30);
        panel1.setRoundTopLeft(30);
        panel1.setRoundTopRight(30);
        panel1.setLayout(new java.awt.BorderLayout());

        jLabel1.setFont(new java.awt.Font("Roboto", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 51, 51));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("<html>\n<div style='text-align:center;'>\n<p>Requisisicon con</p>\n<p>Documento</p>\n</div>\n</hml>");
        panel1.add(jLabel1, java.awt.BorderLayout.PAGE_START);

        jLabel2.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 102, 204));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText(" ");
        panel1.add(jLabel2, java.awt.BorderLayout.SOUTH);

        jPanel1.setBackground(new java.awt.Color(224, 224, 224));
        jPanel1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 20, 20));

        rSButtonRoundRipple1.setBackground(new java.awt.Color(0, 102, 204));
        rSButtonRoundRipple1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/pdf_1.png"))); // NOI18N
        rSButtonRoundRipple1.setText("Abrir");
        rSButtonRoundRipple1.setPreferredSize(new java.awt.Dimension(120, 40));
        jPanel1.add(rSButtonRoundRipple1);

        rSButtonRoundRipple2.setBackground(new java.awt.Color(102, 102, 102));
        rSButtonRoundRipple2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/pdf_1.png"))); // NOI18N
        rSButtonRoundRipple2.setText("Guardar");
        rSButtonRoundRipple2.setColorHover(new java.awt.Color(204, 204, 204));
        rSButtonRoundRipple2.setColorTextHover(new java.awt.Color(51, 51, 51));
        rSButtonRoundRipple2.setPreferredSize(new java.awt.Dimension(120, 40));
        jPanel1.add(rSButtonRoundRipple2);

        panel1.add(jPanel1, java.awt.BorderLayout.CENTER);

        getContentPane().add(panel1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public static void main(String args[]) {
        
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Notificacion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Notificacion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Notificacion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Notificacion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Notificacion dialog = new Notificacion();
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
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private scrollPane.PanelRound panel1;
    public rojeru_san.rsbutton.RSButtonRoundRipple rSButtonRoundRipple1;
    public rojeru_san.rsbutton.RSButtonRoundRipple rSButtonRoundRipple2;
    // End of variables declaration//GEN-END:variables
}
