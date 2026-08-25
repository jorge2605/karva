package VentanaEmergente.Inicio1;

import java.awt.Color;
import static java.lang.Thread.sleep;
import java.util.logging.Level;
import java.util.logging.Logger;
import pruebas.Inicio1;

public class Espera extends javax.swing.JFrame {

    public boolean band = false;
    
    public void activar(){
        Thread hilo = new Thread(){
            public void run(){
                for(;;){
                    if (band){
                        try {
                            sleep(1000);
                            if(jLabel1.getText().equals("POR FAVOR ESPERE")){
                                jLabel1.setText("POR FAVOR ESPERE.");
                            } else if(jLabel1.getText().equals("POR FAVOR ESPERE.")){
                                jLabel1.setText("POR FAVOR ESPERE..");
                            } else if(jLabel1.getText().equals("POR FAVOR ESPERE..")){
                                jLabel1.setText("POR FAVOR ESPERE...");
                            } else if(jLabel1.getText().equals("POR FAVOR ESPERE...")){
                                jLabel1.setText("POR FAVOR ESPERE");
                            }      
                            } catch (InterruptedException ex) {
                            Logger.getLogger(Espera.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }else{
                        break;
                    }
                }
            }
        };
        hilo.start();
        
    }
    
    public Espera() {
        initComponents();
        band = true;
        activar();
        this.setExtendedState(Inicio1.MAXIMIZED_BOTH);
        this.setBackground(new Color(0,0,0,50));
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        getContentPane().setLayout(new java.awt.GridBagLayout());

        jLabel1.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(153, 0, 0));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gif/Espera_1.gif"))); // NOI18N
        jLabel1.setText("POR FAVOR ESPERE");
        jLabel1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLabel1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        getContentPane().add(jLabel1, new java.awt.GridBagConstraints());

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

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
            java.util.logging.Logger.getLogger(Espera.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Espera.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Espera.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Espera.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Espera().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JLabel jLabel1;
    // End of variables declaration//GEN-END:variables
}
