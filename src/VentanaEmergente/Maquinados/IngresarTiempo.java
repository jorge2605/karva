package VentanaEmergente.Maquinados;

import javax.swing.JOptionPane;

public class IngresarTiempo extends javax.swing.JDialog {

    public void sigFocus(){
        if(txtHoras.getText().equals("")){
            this.transferFocus();
        } else if (txtSegundos.getText().equals("")){
            transferFocus();
        } else {
            if (txtHoras.getText().equals("")) {
                JOptionPane.showMessageDialog(this, "Debes ingresar las horas de la operacion");
            } else if (txtSegundos.getText().equals("")){
                JOptionPane.showMessageDialog(this, "Debes ingresar los minutos de la operacion");
            } else {
                this.dispose();
            }
        }
    }
    
    public void validar(){
        if(txtHoras.getText().length() < 2){
            txtHoras.setText("0" + txtHoras.getText());
        }else if(txtHoras.getText().length() > 2){
            txtHoras.setText("");
            txtSegundos.setText("");
        }
        if(txtSegundos.getText().length() < 2 && !txtHoras.getText().equals("")){
            txtSegundos.setText("0" + txtSegundos.getText());
        }else if(txtSegundos.getText().length() > 2){
            txtSegundos.setText("");
            txtHoras.setText("");
        }
    }
    
    public String getTiempo(){
        setVisible(true);
        validar();
        String horas = txtHoras.getText() + ":" + txtSegundos.getText();
        if(horas.equals("00:00") || horas.equals("0:0")) {
            return null;
        } else {
            return horas;
        }
    }
    
    public IngresarTiempo(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtHoras = new javax.swing.JTextField();
        txtSegundos = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new java.awt.GridBagLayout());

        jLabel1.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 51, 51));
        jLabel1.setText("HH");
        jPanel1.add(jLabel1, new java.awt.GridBagConstraints());

        jLabel2.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(51, 51, 51));
        jLabel2.setText("MM");
        jPanel1.add(jLabel2, new java.awt.GridBagConstraints());

        txtHoras.setBackground(new java.awt.Color(255, 255, 255));
        txtHoras.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtHoras.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(204, 204, 204)));
        txtHoras.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtHorasActionPerformed(evt);
            }
        });
        txtHoras.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtHorasKeyTyped(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 1;
        gridBagConstraints.ipadx = 46;
        gridBagConstraints.ipady = 5;
        gridBagConstraints.insets = new java.awt.Insets(0, 8, 0, 8);
        jPanel1.add(txtHoras, gridBagConstraints);

        txtSegundos.setBackground(new java.awt.Color(255, 255, 255));
        txtSegundos.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtSegundos.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(204, 204, 204)));
        txtSegundos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSegundosActionPerformed(evt);
            }
        });
        txtSegundos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtSegundosKeyTyped(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 1;
        gridBagConstraints.ipadx = 46;
        gridBagConstraints.ipady = 5;
        gridBagConstraints.insets = new java.awt.Insets(0, 8, 0, 8);
        jPanel1.add(txtSegundos, gridBagConstraints);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtHorasKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtHorasKeyTyped
        char c = evt.getKeyChar();
        if (!Character.isDigit(c)) {
            evt.consume();
        }
    }//GEN-LAST:event_txtHorasKeyTyped

    private void txtSegundosKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSegundosKeyTyped
        char c = evt.getKeyChar();
        if (!Character.isDigit(c)) {
            evt.consume();
        }
    }//GEN-LAST:event_txtSegundosKeyTyped

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        txtHoras.setText("");
        txtSegundos.setText("");
    }//GEN-LAST:event_formWindowClosed

    private void txtHorasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtHorasActionPerformed
        txtHoras.transferFocus();
        sigFocus();
    }//GEN-LAST:event_txtHorasActionPerformed

    private void txtSegundosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSegundosActionPerformed
        txtSegundos.transferFocus();
        sigFocus();
    }//GEN-LAST:event_txtSegundosActionPerformed

    public static void main(String args[]) {
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                IngresarTiempo dialog = new IngresarTiempo(new javax.swing.JFrame(), true);
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
    public javax.swing.JTextField txtHoras;
    public javax.swing.JTextField txtSegundos;
    // End of variables declaration//GEN-END:variables
}
