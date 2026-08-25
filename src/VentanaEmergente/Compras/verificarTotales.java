package VentanaEmergente.Compras;

import Conexiones.Conexion;
import java.awt.Color;
import java.awt.Font;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.Stack;
import java.util.logging.Level;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.DefaultTableModel;

public class verificarTotales extends javax.swing.JDialog {

    String requi;
    Stack<String> pilaProveedor;
    boolean band;
    
    public JTable setProperties(JTable tabla, JScrollPane scroll){
        tabla.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
            },
            new String [] {
                "Descripcion", "Precio", "Cantidad", "Total"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        scroll.setViewportView(tabla);
        if (tabla.getColumnModel().getColumnCount() > 0) {
            tabla.getColumnModel().getColumn(1).setMinWidth(100);
            tabla.getColumnModel().getColumn(1).setPreferredWidth(100);
            tabla.getColumnModel().getColumn(1).setMaxWidth(100);
            tabla.getColumnModel().getColumn(2).setMinWidth(100);
            tabla.getColumnModel().getColumn(2).setPreferredWidth(100);
            tabla.getColumnModel().getColumn(2).setMaxWidth(100);
            tabla.getColumnModel().getColumn(3).setMinWidth(100);
            tabla.getColumnModel().getColumn(3).setPreferredWidth(100);
            tabla.getColumnModel().getColumn(3).setMaxWidth(100);
        }
        tabla.setShowVerticalLines(false);
        tabla.setShowHorizontalLines(false);
        tabla.getTableHeader().setOpaque(false);
        tabla.getTableHeader().setBackground(new Color(100,100,100));
        tabla.getTableHeader().setForeground(Color.white);
        tabla.getTableHeader().setFont(new Font("Lexend",Font.BOLD,14));
        tabla.setFont(new Font("Lexend",Font.PLAIN,12));
        tabla.setRowHeight(25);
        scroll.getViewport().setBackground(new Color(255,255,255));
        scroll.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 2, true));
        return tabla;
    }
    
    public void getProveedores(){
        try{
            pilaProveedor = new Stack<>();
            Connection con;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            String sql = "select NumRequisicion, Proveedor, OC from requisiciones where NumRequisicion like '"+requi+"'";
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
                String proveedor = rs.getString("Proveedor");
                boolean band = true;
                for (int i = 0; i < pilaProveedor.size(); i++) {
                    if(proveedor.equals(pilaProveedor.get(i))){
                        band = false;
                    }
                }
                if(band){
                    pilaProveedor.push(proveedor);
                }
            }
            for (int i = 0; i < pilaProveedor.size(); i++) {
                //------------------------------------------------------------------------------------------------------------------------------------
                //---------------------------------------------------PANEL----------------------------------------------------------------------------
                JPanel panel = new JPanel();
                panel.setBackground(new java.awt.Color(255, 255, 255));
                panel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 2, true));
                panel.setPreferredSize(new java.awt.Dimension(900, 400));
                panel.setLayout(new java.awt.BorderLayout(10, 10));

                java.awt.GridBagConstraints gridBagConstraints = new java.awt.GridBagConstraints();
                gridBagConstraints.gridx = 0;
                gridBagConstraints.gridy = i;
                gridBagConstraints.insets = new java.awt.Insets(9, 9, 9, 9);
                
                //--------------------------------------------------PROVEEDOR-----------------------------------------------------------------------------
                JLabel proveedor = new JLabel(pilaProveedor.get(i));
                proveedor.setFont(new java.awt.Font("Lexend", 1, 14)); // NOI18N
                proveedor.setForeground(new java.awt.Color(0, 102, 204));
                proveedor.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
                panel.add(proveedor, java.awt.BorderLayout.PAGE_START);
                
                //-----------------------------------------------------TABLA-------------------------------------------------------------------------
                JTable tabla = new JTable();
                JScrollPane scroll = new JScrollPane();
                setProperties(tabla, scroll);
                panel.add(scroll, java.awt.BorderLayout.CENTER);
                //-------------------------------------------------------PANEL TOTALES----------------------------------------------------------------
                JPanel panelIn = new JPanel();
                panelIn.setBackground(new java.awt.Color(255, 255, 255));
                panelIn.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));
                
                JPanel panelTo = new JPanel();
                panelTo.setBackground(new java.awt.Color(255, 255, 255));
                panelTo.setLayout(new java.awt.GridLayout(3, 1, 5, 5));
                
                //-------------------------------------------------------LABEL TOTALES----------------------------------------------------------------
                //---------------------------------------Subtotal
                JLabel lblSub = new javax.swing.JLabel();
                lblSub.setFont(new java.awt.Font("Lexend", 1, 14)); // NOI18N
                lblSub.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
                lblSub.setText("Subtotal: $ ");
                panelTo.add(lblSub);
                
                JLabel lblSub1 = new javax.swing.JLabel();
                lblSub1.setFont(new java.awt.Font("Lexend", 1, 14)); // NOI18N
                lblSub1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
                lblSub1.setText("");
                panelTo.add(lblSub1);
                //---------------------------------------IVA
                JLabel lblIva = new javax.swing.JLabel();
                lblIva.setFont(new java.awt.Font("Lexend", 1, 14)); // NOI18N
                lblIva.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
                lblIva.setText("Iva: $ ");
                panelTo.add(lblIva);
                
                JLabel lblIva1 = new javax.swing.JLabel();
                lblIva1.setFont(new java.awt.Font("Lexend", 1, 14)); // NOI18N
                lblIva1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
                lblIva1.setText("");
                panelTo.add(lblIva1);
                
                //---------------------------------------TOTAL
                JLabel lblTotal = new javax.swing.JLabel();
                lblTotal.setFont(new java.awt.Font("Lexend", 1, 14)); // NOI18N
                lblTotal.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
                lblTotal.setText("Total: $ ");
                panelTo.add(lblTotal);
                
                JLabel lblTotal1 = new javax.swing.JLabel();
                lblTotal1.setFont(new java.awt.Font("Lexend", 1, 14)); // NOI18N
                lblTotal1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
                lblTotal1.setText("");
                panelTo.add(lblTotal1);
                //-----------------------------------------------------------------------------------------------------------------------------------
                panelIn.add(panelTo);
                panel.add(panelIn, java.awt.BorderLayout.PAGE_END);
                //------------------------------------------------------------------------------------------------------------------------------------
                
                //------------------------------------------------------------------------------------------------------------------------------------
                
                panelPrincipal.add(panel, gridBagConstraints);
                
                Statement st2 = con.createStatement();
                String sql2 = "select Descripcion,Codigo, Precio, Proveedor, Cantidad from requisiciones where NumRequisicion like '"+requi+"' and Proveedor like '"+pilaProveedor.get(i)+"' ";
                ResultSet rs2 = st2.executeQuery(sql2);
                String datos[] = new String[5];
                DefaultTableModel miModelo = (DefaultTableModel) tabla.getModel();
                double subtotal = 0;
                while(rs2.next()){
                    datos[0] = rs2.getString("Descripcion");
                    datos[1] = rs2.getString("Precio");
                    datos[2] = rs2.getString("Cantidad");
                    try{
                        double precio = Double.parseDouble(datos[1]);
                        double cantidad = Double.parseDouble(datos[2]);
                        datos[3] = String.valueOf((precio * cantidad));
                        subtotal += (precio * cantidad); 
                    }catch(Exception e){}
                    miModelo.addRow(datos);
                }
                
                String sql3 = "select * from registroprov_compras where Nombre like '"+pilaProveedor.get(i)+"'";
                Statement st3 = con.createStatement();
                ResultSet rs3 = st3.executeQuery(sql3);
                double iva = 0;
                while(rs3.next()){
                    try{
                        iva = Double.parseDouble(rs3.getString("Iva"));
                    }catch(NumberFormatException e){iva =0;}
                }
                
                iva = (subtotal * iva) / 100;  
                
                double total = iva + subtotal;
                
                DecimalFormat formato = new DecimalFormat("#,###.00");
                lblSub1.setText(formato.format(subtotal));
                lblIva1.setText(formato.format(iva));
                lblTotal1.setText(formato.format(total));
            }
            
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "error: "+e,"error",JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public boolean getOption(String  requi){
        initComponents();
        this.requi = requi;
        getProveedores();
        jScrollPane3.getVerticalScrollBar().setUnitIncrement(15);
        this.setVisible(true);
        return band;
    }
    
    public verificarTotales(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        panelPrincipal = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        btnCancelar = new javax.swing.JButton();
        btnAceptar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(1000, 600));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new java.awt.BorderLayout());

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new java.awt.BorderLayout());

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));

        jLabel9.setFont(new java.awt.Font("Roboto", 1, 24)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 165, 252));
        jLabel9.setText("Ver totales");
        jPanel8.add(jLabel9);

        jPanel2.add(jPanel8, java.awt.BorderLayout.CENTER);

        jPanel1.add(jPanel2, java.awt.BorderLayout.NORTH);

        jScrollPane3.setBorder(null);

        panelPrincipal.setBackground(new java.awt.Color(255, 255, 255));
        panelPrincipal.setLayout(new java.awt.GridBagLayout());
        jScrollPane3.setViewportView(panelPrincipal);

        jPanel1.add(jScrollPane3, java.awt.BorderLayout.CENTER);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT, 30, 5));

        btnCancelar.setFont(new java.awt.Font("Lexend", 1, 14)); // NOI18N
        btnCancelar.setForeground(new java.awt.Color(153, 0, 0));
        btnCancelar.setText("Cancelar");
        btnCancelar.setBorder(null);
        btnCancelar.setBorderPainted(false);
        btnCancelar.setContentAreaFilled(false);
        btnCancelar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCancelar.setFocusPainted(false);
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });
        jPanel3.add(btnCancelar);

        btnAceptar.setFont(new java.awt.Font("Lexend", 1, 14)); // NOI18N
        btnAceptar.setForeground(new java.awt.Color(0, 102, 0));
        btnAceptar.setText("Aceptar");
        btnAceptar.setBorder(null);
        btnAceptar.setBorderPainted(false);
        btnAceptar.setContentAreaFilled(false);
        btnAceptar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAceptar.setFocusPainted(false);
        btnAceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAceptarActionPerformed(evt);
            }
        });
        jPanel3.add(btnAceptar);

        jPanel1.add(jPanel3, java.awt.BorderLayout.PAGE_END);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        band = false;
        dispose();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAceptarActionPerformed
        band = true;
        dispose();
    }//GEN-LAST:event_btnAceptarActionPerformed

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
            java.util.logging.Logger.getLogger(verificarTotales.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(verificarTotales.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(verificarTotales.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(verificarTotales.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                verificarTotales dialog = new verificarTotales(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnAceptar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JPanel panelPrincipal;
    // End of variables declaration//GEN-END:variables
}
