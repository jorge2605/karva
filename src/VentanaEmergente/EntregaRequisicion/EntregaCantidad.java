package VentanaEmergente.EntregaRequisicion;

import javax.swing.JOptionPane;

public class EntregaCantidad extends javax.swing.JDialog {

    public double cantidad;
    
    public double getCantidad(){
        this.setVisible(true);
        return cantidad;
    }
    
    public EntregaCantidad(java.awt.Frame parent, boolean modal, String cantidad, String cantidadE) {
        super(parent, modal);
        initComponents();
        lblCantidad.setText(cantidad);
        lblCantidadE.setText(cantidadE);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel1 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        lblCantidad = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        lblCantidadE = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        txtCantidad = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(500, 320));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setForeground(new java.awt.Color(245, 245, 245));
        java.awt.GridBagLayout jPanel1Layout = new java.awt.GridBagLayout();
        jPanel1Layout.columnWeights = new double[] {1.0};
        jPanel1.setLayout(jPanel1Layout);

        jLabel9.setFont(new java.awt.Font("Lexend", 1, 18)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(51, 51, 51));
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("Cantidad");
        jPanel1.add(jLabel9, new java.awt.GridBagConstraints());

        lblCantidad.setFont(new java.awt.Font("Lexend", 1, 60)); // NOI18N
        lblCantidad.setForeground(new java.awt.Color(0, 165, 252));
        lblCantidad.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblCantidad.setText("0");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        jPanel1.add(lblCantidad, gridBagConstraints);

        jLabel10.setFont(new java.awt.Font("Lexend", 1, 18)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(51, 51, 51));
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("Cantidad Entregada");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        jPanel1.add(jLabel10, gridBagConstraints);

        lblCantidadE.setFont(new java.awt.Font("Lexend", 1, 60)); // NOI18N
        lblCantidadE.setForeground(new java.awt.Color(0, 165, 252));
        lblCantidadE.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblCantidadE.setText("0");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        jPanel1.add(lblCantidadE, gridBagConstraints);

        jLabel11.setFont(new java.awt.Font("Lexend", 1, 18)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(51, 51, 51));
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("Cantidad a entregar");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        jPanel1.add(jLabel11, gridBagConstraints);

        txtCantidad.setBackground(new java.awt.Color(255, 255, 255));
        txtCantidad.setFont(new java.awt.Font("Roboto", 1, 48)); // NOI18N
        txtCantidad.setForeground(new java.awt.Color(0, 153, 255));
        txtCantidad.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCantidad.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(204, 204, 204)));
        txtCantidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCantidadActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 40, 0, 40);
        jPanel1.add(txtCantidad, gridBagConstraints);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtCantidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCantidadActionPerformed
        if(txtCantidad.getText().equals("")){
            JOptionPane.showMessageDialog(this, "Debes seleccionar la cantidad Correcta", "Error", JOptionPane.ERROR_MESSAGE);
        }else{
            try{
                double cantidad = Double.parseDouble(lblCantidad.getText());
                double cantidadR = Double.parseDouble(txtCantidad.getText());
                double cantidadE = Double.parseDouble(lblCantidadE.getText());
                if((cantidadR + cantidadE) > cantidad){
                    JOptionPane.showMessageDialog(this, "La cantidad entregada debe ser mayor a la cantidad del material","Advertencia",JOptionPane.WARNING_MESSAGE);
                    txtCantidad.setText("");
                }else if(cantidadR == 0){
                    JOptionPane.showMessageDialog(this,"La cantidad no debe ser 0", "Advertencia", JOptionPane.WARNING_MESSAGE);
                    txtCantidad.setText("");
                }else{
                    this.cantidad = (cantidadR);
                    dispose();
                }
            }catch(Exception e){
                JOptionPane.showMessageDialog(this, "La cantidad que ingresaste no es correcta","Error",JOptionPane.ERROR_MESSAGE);
                txtCantidad.setText("");
            }
        }
    }//GEN-LAST:event_txtCantidadActionPerformed

    public static void main(String args[]) {
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                EntregaCantidad dialog = new EntregaCantidad(new javax.swing.JFrame(), true,"","");
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
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblCantidad;
    private javax.swing.JLabel lblCantidadE;
    private javax.swing.JTextField txtCantidad;
    // End of variables declaration//GEN-END:variables
}
