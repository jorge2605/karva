package VentanaEmergente.Diseño;

import Conexiones.Conexion;
import java.awt.Color;
import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import pruebas.Disenio1;
import pruebas.Inicio1;

public class InicioDiseño extends javax.swing.JInternalFrame {

    Inicio1 inicio;
    
    public void setWhite(){
        panel3i.setColorBackground(Color.white);
        panelDurol.setColorBackground(Color.white);
        panelAlign.setColorBackground(Color.white);
        panelBOM.setColorBackground(Color.white);
        panelMiss.setColorBackground(Color.white);
    }
    
    public void entrar(String empresa){
        Disenio1 c = new Disenio1(empresa,inicio);
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
    
    public void verificarPlanosNoRegistrados(){
        try{
            Connection con; 
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            Statement st2 = con.createStatement();
            String sql = "select * from reporteplanos where Estado is null";
            ResultSet rs = st.executeQuery(sql);
            int cont = 0;
            while(rs.next()){
                String plano = rs.getString("Plano");
                String sql2 = "select Plano from planos where Plano like '"+plano+"'";
                ResultSet rs2 = st2.executeQuery(sql2);
                String plan = null;
                while(rs2.next()){
                    plan = rs2.getString("Plano");
                }
                if(plan != null){
                    String sql3 = "update reporteplanos set Estado = ? where Plano = ?";
                    PreparedStatement pst = con.prepareStatement(sql3);
                    
                    pst.setString(1, "OK");
                    pst.setString(2, plano);
                    
                    int n = pst.executeUpdate();
                    
                }else{
                    cont++;
                }
            }
            lblMiss.setText(String.valueOf(cont));
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "ERROR: "+e,"ERROR",JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public InicioDiseño(Inicio1 ini) {
        initComponents();
        inicio = ini;
        ((javax.swing.plaf.basic.BasicInternalFrameUI) this.getUI()).setNorthPane(null);
        verificarPlanosNoRegistrados();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel1 = new javax.swing.JPanel();
        panel3i = new rojeru_san.rspanel.RSPanelRound();
        lbl3i = new javax.swing.JButton();
        panelAlign = new rojeru_san.rspanel.RSPanelRound();
        lblAlign = new javax.swing.JButton();
        panelDurol = new rojeru_san.rspanel.RSPanelRound();
        lblDurol = new javax.swing.JButton();
        panelBOM = new rojeru_san.rspanel.RSPanelRound();
        lblDurol1 = new javax.swing.JButton();
        panelMiss = new rojeru_san.rspanel.RSPanelRound();
        lblMiss = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        btnSalir = new javax.swing.JPanel();
        lblX = new javax.swing.JLabel();

        setBorder(null);

        jPanel1.setBackground(new java.awt.Color(245, 245, 245));
        jPanel1.setPreferredSize(new java.awt.Dimension(150, 150));
        jPanel1.setLayout(new java.awt.GridBagLayout());

        panel3i.setColorBackground(new java.awt.Color(255, 255, 255));
        panel3i.setColorBorde(new java.awt.Color(255, 255, 255));
        panel3i.setPreferredSize(new java.awt.Dimension(180, 180));
        panel3i.setLayout(new java.awt.BorderLayout());

        lbl3i.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        lbl3i.setForeground(new java.awt.Color(0, 153, 255));
        lbl3i.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/3i_1.png"))); // NOI18N
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
        jPanel1.add(panel3i, gridBagConstraints);

        panelAlign.setColorBackground(new java.awt.Color(255, 255, 255));
        panelAlign.setColorBorde(new java.awt.Color(255, 255, 255));
        panelAlign.setPreferredSize(new java.awt.Dimension(180, 180));
        panelAlign.setLayout(new java.awt.GridLayout(1, 0));

        lblAlign.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        lblAlign.setForeground(new java.awt.Color(0, 153, 255));
        lblAlign.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/align_128.png"))); // NOI18N
        lblAlign.setBorder(null);
        lblAlign.setContentAreaFilled(false);
        lblAlign.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblAlign.setFocusPainted(false);
        lblAlign.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        lblAlign.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        lblAlign.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblAlignMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblAlignMouseExited(evt);
            }
        });
        lblAlign.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lblAlignActionPerformed(evt);
            }
        });
        panelAlign.add(lblAlign);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(30, 45, 30, 45);
        jPanel1.add(panelAlign, gridBagConstraints);

        panelDurol.setColorBackground(new java.awt.Color(255, 255, 255));
        panelDurol.setColorBorde(new java.awt.Color(255, 255, 255));
        panelDurol.setPreferredSize(new java.awt.Dimension(180, 180));
        panelDurol.setLayout(new java.awt.GridLayout(1, 0));

        lblDurol.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        lblDurol.setForeground(new java.awt.Color(0, 153, 255));
        lblDurol.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Durol_128.png"))); // NOI18N
        lblDurol.setBorder(null);
        lblDurol.setContentAreaFilled(false);
        lblDurol.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblDurol.setFocusPainted(false);
        lblDurol.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        lblDurol.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        lblDurol.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblDurolMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblDurolMouseExited(evt);
            }
        });
        lblDurol.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lblDurolActionPerformed(evt);
            }
        });
        panelDurol.add(lblDurol);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(30, 45, 30, 45);
        jPanel1.add(panelDurol, gridBagConstraints);

        panelBOM.setColorBackground(new java.awt.Color(255, 255, 255));
        panelBOM.setColorBorde(new java.awt.Color(255, 255, 255));
        panelBOM.setPreferredSize(new java.awt.Dimension(180, 180));
        panelBOM.setLayout(new java.awt.GridLayout(1, 0));

        lblDurol1.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        lblDurol1.setForeground(new java.awt.Color(0, 153, 255));
        lblDurol1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/BOM-diseño.png"))); // NOI18N
        lblDurol1.setBorder(null);
        lblDurol1.setContentAreaFilled(false);
        lblDurol1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblDurol1.setFocusPainted(false);
        lblDurol1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        lblDurol1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        lblDurol1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblDurol1MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblDurol1MouseExited(evt);
            }
        });
        lblDurol1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lblDurol1ActionPerformed(evt);
            }
        });
        panelBOM.add(lblDurol1);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(30, 45, 30, 45);
        jPanel1.add(panelBOM, gridBagConstraints);

        panelMiss.setColorBackground(new java.awt.Color(255, 255, 255));
        panelMiss.setColorBorde(new java.awt.Color(255, 255, 255));
        panelMiss.setPreferredSize(new java.awt.Dimension(180, 180));
        panelMiss.setLayout(new java.awt.GridLayout(1, 0));

        lblMiss.setFont(new java.awt.Font("Roboto Medium", 1, 24)); // NOI18N
        lblMiss.setForeground(new java.awt.Color(255, 255, 255));
        lblMiss.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/missDoc.png"))); // NOI18N
        lblMiss.setText("0");
        lblMiss.setBorder(null);
        lblMiss.setContentAreaFilled(false);
        lblMiss.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblMiss.setFocusPainted(false);
        lblMiss.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        lblMiss.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblMissMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblMissMouseExited(evt);
            }
        });
        lblMiss.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lblMissActionPerformed(evt);
            }
        });
        panelMiss.add(lblMiss);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(30, 45, 30, 45);
        jPanel1.add(panelMiss, gridBagConstraints);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        jPanel5.setBackground(new java.awt.Color(245, 245, 245));
        jPanel5.setLayout(new java.awt.BorderLayout());

        jPanel7.setBackground(new java.awt.Color(245, 245, 245));

        btnSalir.setBackground(new java.awt.Color(245, 245, 245));

        lblX.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblX.setForeground(new java.awt.Color(0, 0, 0));
        lblX.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblX.setText(" x ");
        lblX.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
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
        btnSalir.add(lblX);

        jPanel7.add(btnSalir);

        jPanel5.add(jPanel7, java.awt.BorderLayout.EAST);

        getContentPane().add(jPanel5, java.awt.BorderLayout.PAGE_START);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void lbl3iMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl3iMouseEntered
        setWhite();
        panel3i.setColorBackground(new Color(57,255,40));
    }//GEN-LAST:event_lbl3iMouseEntered

    private void lbl3iMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl3iMouseExited
        setWhite();
    }//GEN-LAST:event_lbl3iMouseExited

    private void lbl3iActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lbl3iActionPerformed
        entrar("3i");
    }//GEN-LAST:event_lbl3iActionPerformed

    private void lblAlignMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblAlignMouseEntered
        setWhite();
        panelAlign.setColorBackground(new Color(57,255,40));
    }//GEN-LAST:event_lblAlignMouseEntered

    private void lblAlignMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblAlignMouseExited
        setWhite();
    }//GEN-LAST:event_lblAlignMouseExited

    private void lblAlignActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lblAlignActionPerformed
        entrar("align");
    }//GEN-LAST:event_lblAlignActionPerformed

    private void lblDurolMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblDurolMouseEntered
        setWhite();
        panelDurol.setColorBackground(new Color(57,255,40));
    }//GEN-LAST:event_lblDurolMouseEntered

    private void lblDurolMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblDurolMouseExited
        setWhite();
    }//GEN-LAST:event_lblDurolMouseExited

    private void lblDurolActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lblDurolActionPerformed
        entrar("durol");
    }//GEN-LAST:event_lblDurolActionPerformed

    private void lblXMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblXMouseClicked
        dispose();
    }//GEN-LAST:event_lblXMouseClicked

    private void lblXMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblXMouseEntered
        btnSalir.setBackground(Color.red);
        lblX.setForeground(Color.white);
    }//GEN-LAST:event_lblXMouseEntered

    private void lblXMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblXMouseExited
        btnSalir.setBackground(new Color(245,245,245));
        lblX.setForeground(Color.black);
    }//GEN-LAST:event_lblXMouseExited

    private void lblDurol1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblDurol1MouseEntered
        setWhite();
        panelBOM.setColorBackground(new Color(57,255,40));
    }//GEN-LAST:event_lblDurol1MouseEntered

    private void lblDurol1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblDurol1MouseExited
        setWhite();
    }//GEN-LAST:event_lblDurol1MouseExited

    private void lblDurol1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lblDurol1ActionPerformed
        JFrame f = (JFrame) JOptionPane.getFrameForComponent(this);
        subirBOM subir = new subirBOM(f,true);
        subir.setVisible(true);
    }//GEN-LAST:event_lblDurol1ActionPerformed

    private void lblMissMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblMissMouseEntered
        setWhite();
        panelMiss.setColorBackground(new Color(57,255,40));
    }//GEN-LAST:event_lblMissMouseEntered

    private void lblMissMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblMissMouseExited
        setWhite();
    }//GEN-LAST:event_lblMissMouseExited

    private void lblMissActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lblMissActionPerformed
        JFrame f = (JFrame) JOptionPane.getFrameForComponent(this);
        PlanosPerdidos planos = new PlanosPerdidos(f,true);
        planos.setLocationRelativeTo(null);
        planos.setVisible(true);
    }//GEN-LAST:event_lblMissActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel btnSalir;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel7;
    public javax.swing.JButton lbl3i;
    public javax.swing.JButton lblAlign;
    public javax.swing.JButton lblDurol;
    public javax.swing.JButton lblDurol1;
    public javax.swing.JButton lblMiss;
    private javax.swing.JLabel lblX;
    private rojeru_san.rspanel.RSPanelRound panel3i;
    private rojeru_san.rspanel.RSPanelRound panelAlign;
    private rojeru_san.rspanel.RSPanelRound panelBOM;
    private rojeru_san.rspanel.RSPanelRound panelDurol;
    private rojeru_san.rspanel.RSPanelRound panelMiss;
    // End of variables declaration//GEN-END:variables
}
