package Controlador.maquinados;

import javax.swing.JOptionPane;

public class razon extends javax.swing.JDialog {

    public String razon[];
    public String botonSeleccionado = "";
    int x,y;
    
    public String[] getRazon(){
        razon = new String[2];
        this.setVisible(true);
        return this.razon;
    }
    
    public String seleccionado() {
        if (jRadioButton1.isSelected()) {
            return jRadioButton1.getText();
        } else if (jRadioButton2.isSelected()) {
            return jRadioButton2.getText();
        } else if (jRadioButton3.isSelected()) {
            return jRadioButton3.getText();
        } else if (jRadioButton4.isSelected()) {
            return jRadioButton4.getText();
        } else {
            return null;
        }
    }
    
    public razon(java.awt.Frame parent, boolean modal, revisarPlanos revisar) {
        super(parent, modal);
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        grupo1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        lblTitulo = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtRazon = new javax.swing.JTextArea();
        jPanel6 = new javax.swing.JPanel();
        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        jRadioButton3 = new javax.swing.JRadioButton();
        jRadioButton4 = new javax.swing.JRadioButton();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        btnEnviar = new RSMaterialComponent.RSButtonMaterialRipple();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setPreferredSize(new java.awt.Dimension(845, 446));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 153), 2, true));
        jPanel1.setLayout(new java.awt.BorderLayout());

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                jPanel2MouseDragged(evt);
            }
        });
        jPanel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jPanel2MousePressed(evt);
            }
        });

        lblTitulo.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        lblTitulo.setForeground(new java.awt.Color(153, 0, 0));
        lblTitulo.setText("Razon de envio a cortes");
        jPanel2.add(lblTitulo);

        jPanel1.add(jPanel2, java.awt.BorderLayout.PAGE_START);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setLayout(new java.awt.BorderLayout());

        jLabel2.setText("        ");
        jPanel3.add(jLabel2, java.awt.BorderLayout.PAGE_START);

        jLabel3.setText("        ");
        jPanel3.add(jLabel3, java.awt.BorderLayout.SOUTH);

        jLabel4.setText("        ");
        jPanel3.add(jLabel4, java.awt.BorderLayout.EAST);

        jLabel5.setText("        ");
        jPanel3.add(jLabel5, java.awt.BorderLayout.WEST);

        jPanel5.setLayout(new java.awt.BorderLayout());

        jScrollPane1.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(204, 204, 204)));

        txtRazon.setBackground(new java.awt.Color(245, 245, 245));
        txtRazon.setColumns(20);
        txtRazon.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtRazon.setForeground(new java.awt.Color(0, 0, 0));
        txtRazon.setLineWrap(true);
        txtRazon.setRows(5);
        txtRazon.setWrapStyleWord(true);
        txtRazon.setBorder(null);
        jScrollPane1.setViewportView(txtRazon);

        jPanel5.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setLayout(new java.awt.GridBagLayout());

        grupo1.add(jRadioButton1);
        jRadioButton1.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jRadioButton1.setText("Mal diseño");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 5, 0);
        jPanel6.add(jRadioButton1, gridBagConstraints);

        grupo1.add(jRadioButton2);
        jRadioButton2.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jRadioButton2.setText("Mal manejo");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 5, 0);
        jPanel6.add(jRadioButton2, gridBagConstraints);

        grupo1.add(jRadioButton3);
        jRadioButton3.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jRadioButton3.setText("Pieza mal cortada");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 5, 0);
        jPanel6.add(jRadioButton3, gridBagConstraints);

        grupo1.add(jRadioButton4);
        jRadioButton4.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jRadioButton4.setText("Pieza defectuosa");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 5, 0);
        jPanel6.add(jRadioButton4, gridBagConstraints);

        jLabel6.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel6.setText("Comentarios:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 5, 0);
        jPanel6.add(jLabel6, gridBagConstraints);

        jLabel7.setText("  ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 5, 0);
        jPanel6.add(jLabel7, gridBagConstraints);

        jPanel5.add(jPanel6, java.awt.BorderLayout.NORTH);

        jPanel3.add(jPanel5, java.awt.BorderLayout.CENTER);

        jPanel1.add(jPanel3, java.awt.BorderLayout.CENTER);

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        btnEnviar.setText("Enviar");
        btnEnviar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEnviarActionPerformed(evt);
            }
        });
        jPanel4.add(btnEnviar);

        jPanel1.add(jPanel4, java.awt.BorderLayout.PAGE_END);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnEnviarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEnviarActionPerformed
        if(txtRazon.getText().equals("")){
            JOptionPane.showMessageDialog(this, "Debes llenar tu reporte","Advertencia",JOptionPane.WARNING_MESSAGE);
        }else{
            razon[0] = txtRazon.getText();
            razon[1] = seleccionado();
            if (razon[1] == null) {
                JOptionPane.showMessageDialog(this, "Debes seleccionar una opcion", "Advertencia", JOptionPane.WARNING_MESSAGE);
            } else {
                dispose();
            }
        }
    }//GEN-LAST:event_btnEnviarActionPerformed

    private void jPanel2MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel2MousePressed
        x = evt.getX();
        y = evt.getY();
    }//GEN-LAST:event_jPanel2MousePressed

    private void jPanel2MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel2MouseDragged
        int xx = evt.getXOnScreen();
        int yy = evt.getYOnScreen();
        this.setLocation(xx - (x), yy - y);
    }//GEN-LAST:event_jPanel2MouseDragged

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                razon dialog = new razon(new javax.swing.JFrame(), true,null);
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
    private RSMaterialComponent.RSButtonMaterialRipple btnEnviar;
    private javax.swing.ButtonGroup grupo1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    public javax.swing.JRadioButton jRadioButton1;
    public javax.swing.JRadioButton jRadioButton2;
    public javax.swing.JRadioButton jRadioButton3;
    public javax.swing.JRadioButton jRadioButton4;
    private javax.swing.JScrollPane jScrollPane1;
    public javax.swing.JLabel lblTitulo;
    private javax.swing.JTextArea txtRazon;
    // End of variables declaration//GEN-END:variables
}
