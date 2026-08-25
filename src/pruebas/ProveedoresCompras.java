package pruebas;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;

public class ProveedoresCompras extends java.awt.Dialog implements ActionListener{

    public String[] datos;
    public JButton[] botones;
    public int tamaño;
    JPanel[] panel;
    
    public ProveedoresCompras(int tamaño, String datos[],java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.tamaño = tamaño;
        this.datos = datos;
        iniciar();
    }
    
    public void iniciar(){
        PanelBotones.setLayout(new GridLayout(tamaño,0));
        botones = new JButton[tamaño];
        
        panel = new JPanel[tamaño];
        for (int i = 0; i < tamaño; i++) {
            if(datos[i] == null){
                
            }else{
            botones[i] = new JButton(this.datos[i]);
            panel[i] = new JPanel();
            panel[i].setBackground(Color.white);
            botones[i].setContentAreaFilled(false);
            botones[i].setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
            botones[i].addActionListener(this);
            panel[i].add(botones[i]);
            PanelBotones.add(panel[i]);
            }
        }
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        PanelBotones = new javax.swing.JPanel();
        PanelBotones1 = new javax.swing.JPanel();
        btnPdf = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();

        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                closeDialog(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N
        jLabel1.setText("SELECCIONAR PROVEEDOR(ES) QUE DESEE HACER LA ORDEN DE COMPRA");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 27, -1, -1));

        PanelBotones.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout PanelBotonesLayout = new javax.swing.GroupLayout(PanelBotones);
        PanelBotones.setLayout(PanelBotonesLayout);
        PanelBotonesLayout.setHorizontalGroup(
            PanelBotonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 348, Short.MAX_VALUE)
        );
        PanelBotonesLayout.setVerticalGroup(
            PanelBotonesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 429, Short.MAX_VALUE)
        );

        jPanel1.add(PanelBotones, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 84, -1, -1));

        PanelBotones1.setBackground(new java.awt.Color(255, 255, 255));
        PanelBotones1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnPdf.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ImgAnimacion/pdf_48.png"))); // NOI18N
        btnPdf.setBorder(null);
        btnPdf.setBorderPainted(false);
        btnPdf.setContentAreaFilled(false);
        btnPdf.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnPdf.setFocusPainted(false);
        btnPdf.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnPdf.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/ImgAnimacion/pdf_48.png"))); // NOI18N
        btnPdf.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/ImgAnimacion/pdf_64.png"))); // NOI18N
        btnPdf.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        PanelBotones1.add(btnPdf, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 20, 70, 70));

        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ImgAnimacion/cerrar_48.png"))); // NOI18N
        btnCancelar.setBorder(null);
        btnCancelar.setBorderPainted(false);
        btnCancelar.setContentAreaFilled(false);
        btnCancelar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCancelar.setFocusPainted(false);
        btnCancelar.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnCancelar.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/ImgAnimacion/cerrar_48.png"))); // NOI18N
        btnCancelar.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/ImgAnimacion/cerrar_64.png"))); // NOI18N
        btnCancelar.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        PanelBotones1.add(btnCancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 370, 70, 70));

        jPanel1.add(PanelBotones1, new org.netbeans.lib.awtextra.AbsoluteConstraints(563, 84, 264, -1));

        add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void closeDialog(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_closeDialog
        setVisible(false);
        dispose();
    }//GEN-LAST:event_closeDialog

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                String d[] = null;
                ProveedoresCompras dialog = new ProveedoresCompras(0,d,new java.awt.Frame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        for (int i = 0; i < tamaño; i++) {
            if(e.getSource() == botones[i]){
                if(panel[i].getBackground().equals(Color.white)){
                panel[i].setBackground(Color.green);
            }else{
                panel[i].setBackground(Color.white);
            }
            }
            
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PanelBotones;
    private javax.swing.JPanel PanelBotones1;
    public javax.swing.JButton btnCancelar;
    public javax.swing.JButton btnPdf;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
