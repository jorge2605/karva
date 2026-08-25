package pruebas;

import Conexiones.Conexion;
import java.awt.Color;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class ElegirProveedor extends javax.swing.JDialog implements ActionListener{

    public String[] datos;
    public JButton[] botones;
    public int tamaño;
    public JPanel[] panel;
    public String requi;
    public ElegirProveedor(java.awt.Frame parenti, boolean modal,int tamaño, String datos[], String requi) {
        super(parenti, modal);
        initComponents();
        this.tamaño = tamaño;
        this.datos = datos;
        iniciar();
        this.requi = requi;
    }

    public boolean revisar(String proveedor){
        boolean band = false;
        try{
            Connection con = null;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            String sql = "select * from requisiciones where NumRequisicion like '"+requi+"' and Proveedor like '"+proveedor+"' and OC is null";
            ResultSet rs = st.executeQuery(sql);
            String datos[] = new String[10];
            
            while(rs.next()){
                datos[0] = rs.getString("TE");
                if(datos[0] == null){
                    band = true;
                }
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "ERROR: "+e,"ERROR",JOptionPane.ERROR_MESSAGE);
        }
        return band;
    }
    
    public final void iniciar(){
        PanelBotones.setLayout(new GridLayout(tamaño,0));
        botones = new JButton[tamaño];
        
        panel = new JPanel[tamaño];
        for (int i = 0; i < tamaño; i++) {
            System.out.println(tamaño);
            System.err.println(datos[i]);
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
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        PanelBotones = new javax.swing.JPanel();
        PanelBotones1 = new javax.swing.JPanel();
        btnCrear = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

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

        btnCrear.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ImgAnimacion/pdf_48.png"))); // NOI18N
        btnCrear.setBorder(null);
        btnCrear.setBorderPainted(false);
        btnCrear.setContentAreaFilled(false);
        btnCrear.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnCrear.setFocusPainted(false);
        btnCrear.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnCrear.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/ImgAnimacion/pdf_48.png"))); // NOI18N
        btnCrear.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/ImgAnimacion/pdf_64.png"))); // NOI18N
        btnCrear.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        btnCrear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCrearActionPerformed(evt);
            }
        });
        PanelBotones1.add(btnCrear, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 20, 70, 70));

        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ImgAnimacion/cerrar_48.png"))); // NOI18N
        btnCancelar.setBorder(null);
        btnCancelar.setBorderPainted(false);
        btnCancelar.setContentAreaFilled(false);
        btnCancelar.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnCancelar.setFocusPainted(false);
        btnCancelar.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnCancelar.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/ImgAnimacion/cerrar_48.png"))); // NOI18N
        btnCancelar.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/ImgAnimacion/cerrar_64.png"))); // NOI18N
        btnCancelar.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });
        PanelBotones1.add(btnCancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 390, 70, 70));

        jPanel1.add(PanelBotones1, new org.netbeans.lib.awtextra.AbsoluteConstraints(563, 84, 264, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnCrearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCrearActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnCrearActionPerformed

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
            java.util.logging.Logger.getLogger(ElegirProveedor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ElegirProveedor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ElegirProveedor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ElegirProveedor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
	public void run() {
            String d[] = null;
		ElegirProveedor dialog = new ElegirProveedor(new javax.swing.JFrame(), true,0,d,"");
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
    private javax.swing.JPanel PanelBotones;
    private javax.swing.JPanel PanelBotones1;
    public javax.swing.JButton btnCancelar;
    public javax.swing.JButton btnCrear;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables

    @Override
    public void actionPerformed(ActionEvent e) {
        for (int i = 0; i < tamaño; i++) {
            if(e.getSource() == botones[i]){
                if(revisar(botones[i].getText())){
                    JOptionPane.showMessageDialog(this, "FALTA ASIGNAR TIEMPOS DE ENTREGA","ADVERTENCIA",JOptionPane.WARNING_MESSAGE);
                }else{
                if(panel[i].getBackground().equals(Color.white)){
                panel[i].setBackground(Color.green);
            }else{
                panel[i].setBackground(Color.white);
            }
            }
            }
        }
    }
}
