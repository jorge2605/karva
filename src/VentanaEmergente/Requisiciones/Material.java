package VentanaEmergente.Requisiciones;

import Conexiones.Conexion;
import java.awt.Color;
import java.awt.Font;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Stack;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import scrollPane.ScrollBarCustom;

public class Material extends javax.swing.JDialog {

    public int fila;
    
    public boolean verificarRepetido(){
        boolean ver = true;
        for (int i = 0; i < Tabla1.getRowCount(); i++) {
            if(txtPlano.getText().equals(Tabla1.getValueAt(i, 0).toString())){
                ver = false;
            }
        }
        return ver;
    }
    
    public boolean verificarExiste(String plano){
        boolean ver = true;
        try{
            Connection con;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            String sql = "select Plano from Planos where Plano like '"+plano+"'";
            ResultSet rs = st.executeQuery(sql);
            String d = "";
            while(rs.next()){
                d = rs.getString("Plano");
            }
            if("".equals(d)){
                ver = false;
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "Error de comunicacion con base de datos","ERROR",JOptionPane.ERROR_MESSAGE);
        }
        return ver;
    }
    
    public boolean existeRequi(String requi){
        boolean veri = false;
        for (int i = 0; i < Tabla1.getRowCount(); i++) {
            if(Tabla1.getValueAt(i, 2).toString().equals(requi)){
                veri = true;
            }
        }
        return veri;
    }
    
    public void verRelacion(String requi){
        try{
            DefaultTableModel miModelo = (DefaultTableModel) Tabla1.getModel();
            Connection con;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            String sql = "select * from materialrequisiciones where NumRequisicion like '"+requi+"'";
            ResultSet rs = st.executeQuery(sql);
            String dat[] = new String[3];
            
            while(rs.next()){
                dat[2] = rs.getString("Codigo");
                dat[1]= rs.getString("Planos");
                String text = dat[1];
                String buscar = "-";
                int aux = 0;
                int aux2;
                char arreglo[] = text.toCharArray();
                for (int j = 0; j < text.length(); j++) {
                    String letra = String.valueOf(arreglo[j]);
                    if (buscar.equalsIgnoreCase(letra)) {
                        aux2 = j;
                        String ad = (text.substring(aux,aux2));
                        aux = j+1;
                        dat[0] = ad;
                        dat[1] = dat[2];
                        miModelo.addRow(dat);
                    }
                }
                
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "ERROR: "+e,"ERROR",JOptionPane.ERROR_MESSAGE);
        }
    }
    
     public void impimirRelacion(String[] requi, Stack<String> mat){
        try{
            DefaultTableModel miModelo = (DefaultTableModel) Tabla1.getModel();
            Connection con;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            for (int i = 0; i < mat.size(); i++) {
                System.out.println(mat.get(i));
            }
            for (String requi1 : requi) {
                if (existeRequi(requi1) == false) {
                    Statement st = con.createStatement();
                    String sql = "select * from materialrequisiciones where NumRequisicion like '" + requi1 + "'";
                    ResultSet rs = st.executeQuery(sql);
                    String dat[] = new String[5];
                    while(rs.next()){
                        dat[4] = rs.getString("Codigo");
                        dat[3]= rs.getString("Planos");
                        dat[2] = rs.getString("NumRequisicion");
                        String text = dat[3];
                        boolean bo = false;
                        for (int i = 0; i < mat.size(); i++) {
                            if(mat.get(i).equals(dat[4])){
                                bo = true;
                            }
                        }
                        if(bo){
                            String buscar = "-";
                            int aux = 0;
                            int aux2;
                            char arreglo[] = text.toCharArray();
                            for (int j = 0; j < text.length(); j++) {
                                String letra = String.valueOf(arreglo[j]);
                                if (buscar.equalsIgnoreCase(letra)) {
                                    aux2 = j;
                                    String ad = (text.substring(aux,aux2));
                                    aux = j+1;
                                    dat[0] = ad;
                                    dat[1] = dat[4];
                                    miModelo.addRow(dat);
                                }
                            }
                        }
                    }
                }
            }
            
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "ERROR: "+e,"ERROR",JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void limpiarTabla(){
        Tabla1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
            },
            new String [] {
                "PLANO", "MATERIAL", "REQUISICION"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(Tabla1);
    }
    
    public void impresion(){
        PrintService defaultPrintService = PrintServiceLookup.lookupDefaultPrintService();
        cmbImpresora.removeAllItems();
        cmbImpresora.addItem(defaultPrintService.getName());
    }
    
    public Material(java.awt.Frame parent, boolean modal, int fila) {
        super(parent, modal);
        initComponents();
        Tabla1.getTableHeader().setFont(new Font("Roboto", Font.BOLD, 14));
        Tabla1.getTableHeader().setOpaque(false);
        Tabla1.getTableHeader().setBackground(new Color(0, 78, 171));
        Tabla1.getTableHeader().setForeground(Color.white);
        Tabla1.setRowHeight(25);
        Tabla1.setShowGrid(false);
        
        this.fila = fila;
        
        panelImprimir.setVisible(false);
        impresion();
        jScrollPane2.getViewport().setBackground(new Color(255,255,255));
        jScrollPane2.setVerticalScrollBar(new ScrollBarCustom(new Color(0,165,255)));
        
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        txtPlano = new RSMaterialComponent.RSTextFieldMaterial();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        panelImprimir = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        cmbImpresora = new RSMaterialComponent.RSComboBoxMaterial();
        jPanel4 = new javax.swing.JPanel();
        lblMaterial = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        Tabla1 = new javax.swing.JTable();
        jPanel5 = new javax.swing.JPanel();
        btnGuardar = new RSMaterialComponent.RSButtonMaterialRipple();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new java.awt.BorderLayout());

        jPanel2.setLayout(new java.awt.BorderLayout());

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setLayout(new java.awt.BorderLayout());

        txtPlano.setPlaceholder("Plano");
        txtPlano.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPlanoActionPerformed(evt);
            }
        });
        jPanel3.add(txtPlano, java.awt.BorderLayout.CENTER);

        jLabel2.setText("                    ");
        jPanel3.add(jLabel2, java.awt.BorderLayout.WEST);

        jLabel3.setText("                    ");
        jLabel3.setToolTipText("");
        jPanel3.add(jLabel3, java.awt.BorderLayout.EAST);

        panelImprimir.setBackground(new java.awt.Color(153, 204, 255));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/impresora.png"))); // NOI18N
        panelImprimir.add(jLabel1);

        cmbImpresora.setBackground(new java.awt.Color(153, 204, 255));
        panelImprimir.add(cmbImpresora);

        jPanel3.add(panelImprimir, java.awt.BorderLayout.PAGE_START);

        jPanel2.add(jPanel3, java.awt.BorderLayout.CENTER);

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        lblMaterial.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        lblMaterial.setText("MATERIAL");
        jPanel4.add(lblMaterial);

        jPanel2.add(jPanel4, java.awt.BorderLayout.PAGE_START);

        jPanel1.add(jPanel2, java.awt.BorderLayout.PAGE_START);

        Tabla1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "PLANO", "MATERIAL"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        Tabla1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Tabla1MouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(Tabla1);

        jPanel1.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));

        btnGuardar.setText("Guardar");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });
        jPanel5.add(btnGuardar);

        jPanel1.add(jPanel5, java.awt.BorderLayout.PAGE_END);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void Tabla1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Tabla1MouseClicked
        
    }//GEN-LAST:event_Tabla1MouseClicked

    private void txtPlanoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPlanoActionPerformed
        if(txtPlano.getText().equals("")){
            JOptionPane.showMessageDialog(this, "Debes introducir por lo menos un plano","Advertencia",JOptionPane.WARNING_MESSAGE);
        }else if(verificarRepetido() == false){
            JOptionPane.showMessageDialog(this, "Este plano ya esta incluido en la lista","Advertencia",JOptionPane.WARNING_MESSAGE);
        }else{
            DefaultTableModel miModelo = (DefaultTableModel) Tabla1.getModel();
            String datos[] = new String[3];
            datos[0] = txtPlano.getText();
            datos[1] = lblMaterial.getText();

            miModelo.addRow(datos);
            txtPlano.setText("");
        }
        txtPlano.setText("");
    }//GEN-LAST:event_txtPlanoActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnGuardarActionPerformed

    /**
     * @param args the command line arguments
     */
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
            java.util.logging.Logger.getLogger(Material.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Material.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Material.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Material.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Material dialog = new Material(new javax.swing.JFrame(), true,-1);
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
    public javax.swing.JTable Tabla1;
    public RSMaterialComponent.RSButtonMaterialRipple btnGuardar;
    private RSMaterialComponent.RSComboBoxMaterial cmbImpresora;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane2;
    public javax.swing.JLabel lblMaterial;
    public javax.swing.JPanel panelImprimir;
    public RSMaterialComponent.RSTextFieldMaterial txtPlano;
    // End of variables declaration//GEN-END:variables
}
