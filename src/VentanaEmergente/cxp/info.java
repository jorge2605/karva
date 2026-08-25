package VentanaEmergente.cxp;

import Conexiones.Conexion;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Font;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class info extends javax.swing.JDialog {
    
    public void agregarATabla(String id){
        DefaultTableModel miModelo = (DefaultTableModel) Tabla1.getModel();
        int pos = id.indexOf(':');
        String dat[] = new String[10];
        dat[0] = id.substring(0, pos);
        dat[1] = id.substring(pos + 1, id.length());
        try{
            Connection con;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            String sql = "select * from requisiciones where Id like '" + dat[0] + "'";
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
                dat[2] = rs.getString("Codigo");
                dat[3] = rs.getString("Descripcion");
                dat[4] = rs.getString("OC");
                dat[5] = rs.getString("Proveedor");
                dat[6] = rs.getString("Precio");
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "ERROR: "+e,"ERROR",JOptionPane.ERROR_MESSAGE);
        }
        miModelo.addRow(dat);
    }
    
    public final void decoder(String code){
        Stack<Integer> pila = new Stack<>();
        int pos = code.indexOf(',');
        while(pos != -1){
            pila.push(pos);
            pos = code.indexOf(',', pos + 1);
        }
        
        if(!pila.isEmpty()){
            int inicio = 1;
            for (int i = 0; i < pila.size(); i++) {
                String id = code.substring(inicio, pila.get(i));
                agregarATabla(id);
                inicio = pila.get(i)+1;
            }
            String id = code.substring(inicio, code.length() - 1);
            agregarATabla(id);
        }else{
            String id = code.substring(1, code.length() - 1);
            agregarATabla(id);
        }
        if(Tabla1.getRowCount() > 0){
            double subtotal = 0;
            for (int i = 0; i < Tabla1.getRowCount(); i++) {
                double precio;
                double cant;
                try{precio = Double.parseDouble(Tabla1.getValueAt(i, 6).toString());}catch(Exception e){precio = 0;}
                try{cant = Double.parseDouble(Tabla1.getValueAt(i, 1).toString());}catch(Exception e){cant = 0;}
                subtotal += (precio * cant);
            }
            double isr = 0;
            double iva = 0;
            try{
                Connection con;
                Conexion con1 = new Conexion();
                con = con1.getConnection();
                Statement st = con.createStatement();
                String sql = "select * from registroprov_compras where Nombre like '" + Tabla1.getValueAt(0, 5) + "'";
                ResultSet rs = st.executeQuery(sql);
                String is = "";
                String iv = "";
                while(rs.next()){
                    is = rs.getString("Isr");
                    iv = rs.getString("Iva");
                }
                try{isr = Double.parseDouble(is);}catch(Exception e){isr = 0;}
                iva = Double.parseDouble(iv);
            }catch(SQLException e){
                JOptionPane.showMessageDialog(this, "ERROR: " + e,"ERROR",JOptionPane.ERROR_MESSAGE);
            }
            isr = (subtotal * isr) / 100;
            iva = (subtotal * iva) / 100;
            
            double total = subtotal + iva -isr;
            
            DecimalFormat formato = new DecimalFormat("#,###.##");
            
            lblISR.setText(String.valueOf(formato.format(isr)));
            lblIva.setText(String.valueOf(formato.format(iva)));
            lblSub.setText(String.valueOf(formato.format(subtotal)));
            lblTotal.setText(String.valueOf(formato.format(total)));
        }
    }
    
    public final void limpiarTabla(){
        Tabla1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
            },
            new String [] {
                "ID", "Cantidad", "Codigo", "Descripcion", "OC", "Proveedor", "Precio P/U"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        if (Tabla1.getColumnModel().getColumnCount() > 0) {
            Tabla1.getColumnModel().getColumn(3).setMinWidth(400);
            Tabla1.getColumnModel().getColumn(3).setPreferredWidth(400);
            Tabla1.getColumnModel().getColumn(3).setMaxWidth(400);
        }
        Tabla1.getTableHeader().setFont(new Font("Lexend", Font.BOLD, 14));
        Tabla1.getTableHeader().setOpaque(false);
        Tabla1.getTableHeader().setBackground(new Color(0, 78, 171));
        Tabla1.getTableHeader().setForeground(Color.white);
        Tabla1.setRowHeight(25);
        Tabla1.setShowVerticalLines(false);
        Tabla1.setGridColor(new Color(240,240,240));
        
        jScrollPane1.getViewport().setBackground(new Color(255,255,255));
    }
    
    private static void buscarArchivosPDF(String nombreBuscado) {
        File carpeta = new File("\\\\192.168.100.40\\bd\\OC\\Orden_de_compra");

        if (carpeta.exists() && carpeta.isDirectory()) {
            File[] archivos = carpeta.listFiles(new FilenameFilter() {
                @Override
                public boolean accept(File dir, String name) {
                    return name.toLowerCase().contains(nombreBuscado.toLowerCase()) && name.toLowerCase().endsWith(".pdf");
                }
            });

            if (archivos != null && archivos.length > 0) {
                for (File archivo : archivos) {
                    try {
                        Desktop.getDesktop().open(new File(archivo.getAbsolutePath()));
                    } catch (IOException ex) {
                        Logger.getLogger(info.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null,"No se encontraron archivos PDF con el nombre buscado.", "Error",JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null,"La carpeta especificada no existe o no es un directorio.", "Error",JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public info(java.awt.Frame parent, boolean modal, String code) {
        super(parent, modal);
        initComponents();
        setLocationRelativeTo(parent);
        limpiarTabla();
        decoder(code);
        try{btnOc.setText(Tabla1.getValueAt(0, 4).toString());}catch(Exception e){}
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        Tabla1 = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        lblSub = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        lblISR = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        lblIva = new javax.swing.JLabel();
        label = new javax.swing.JLabel();
        lblTotal = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        pnlVisto = new javax.swing.JPanel();
        btnVisto = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        btnOc = new javax.swing.JButton();
        jPanel9 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        lblId = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(1141, 598));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new java.awt.BorderLayout());

        jLabel12.setFont(new java.awt.Font("Lexend", 1, 24)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(0, 102, 204));
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setText("Informacion Facturas");
        jPanel1.add(jLabel12, java.awt.BorderLayout.NORTH);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new java.awt.BorderLayout());

        jLabel2.setText("       ");
        jPanel2.add(jLabel2, java.awt.BorderLayout.WEST);

        jLabel3.setText("       ");
        jPanel2.add(jLabel3, java.awt.BorderLayout.EAST);

        jScrollPane1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));

        Tabla1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Cantidad", "Codigo", "Descripcion", "OC", "Proveedor", "Precio P/U"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
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
        jScrollPane1.setViewportView(Tabla1);
        if (Tabla1.getColumnModel().getColumnCount() > 0) {
            Tabla1.getColumnModel().getColumn(3).setMinWidth(400);
            Tabla1.getColumnModel().getColumn(3).setPreferredWidth(400);
            Tabla1.getColumnModel().getColumn(3).setMaxWidth(400);
        }

        jPanel2.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setLayout(new java.awt.BorderLayout());

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setLayout(new java.awt.GridLayout(4, 0, 10, 0));

        jLabel4.setFont(new java.awt.Font("Lexend", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(153, 153, 153));
        jLabel4.setText("        Subtotal: $");
        jPanel4.add(jLabel4);

        lblSub.setFont(new java.awt.Font("Lexend", 0, 12)); // NOI18N
        lblSub.setText("10000");
        jPanel4.add(lblSub);

        jLabel6.setFont(new java.awt.Font("Lexend", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(153, 153, 153));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel6.setText("ISR: $");
        jPanel4.add(jLabel6);

        lblISR.setFont(new java.awt.Font("Lexend", 0, 12)); // NOI18N
        lblISR.setText("10000");
        jPanel4.add(lblISR);

        jLabel8.setFont(new java.awt.Font("Lexend", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(153, 153, 153));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel8.setText("Iva: $");
        jPanel4.add(jLabel8);

        lblIva.setFont(new java.awt.Font("Lexend", 0, 12)); // NOI18N
        lblIva.setText("10000");
        jPanel4.add(lblIva);

        label.setFont(new java.awt.Font("Lexend", 1, 14)); // NOI18N
        label.setForeground(new java.awt.Color(153, 153, 153));
        label.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        label.setText("Total: $");
        jPanel4.add(label);

        lblTotal.setFont(new java.awt.Font("Lexend", 0, 12)); // NOI18N
        lblTotal.setText("10000");
        jPanel4.add(lblTotal);

        jPanel6.add(jPanel4);

        jPanel3.add(jPanel6, java.awt.BorderLayout.EAST);

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));

        pnlVisto.setBackground(new java.awt.Color(255, 255, 255));

        btnVisto.setBackground(new java.awt.Color(255, 255, 255));
        btnVisto.setFont(new java.awt.Font("Lexend", 1, 14)); // NOI18N
        btnVisto.setForeground(new java.awt.Color(0, 102, 204));
        btnVisto.setText("Marcar como visto");
        btnVisto.setBorder(null);
        btnVisto.setBorderPainted(false);
        btnVisto.setContentAreaFilled(false);
        btnVisto.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnVisto.setFocusPainted(false);
        btnVisto.setPreferredSize(new java.awt.Dimension(140, 24));
        btnVisto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnVistoMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnVistoMouseExited(evt);
            }
        });
        btnVisto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVistoActionPerformed(evt);
            }
        });
        pnlVisto.add(btnVisto);

        jPanel7.add(pnlVisto);

        jPanel3.add(jPanel7, java.awt.BorderLayout.CENTER);

        jPanel2.add(jPanel3, java.awt.BorderLayout.PAGE_END);

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setLayout(new java.awt.BorderLayout());

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));

        btnOc.setBackground(new java.awt.Color(255, 255, 255));
        btnOc.setFont(new java.awt.Font("Lexend", 1, 12)); // NOI18N
        btnOc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/pdf.png"))); // NOI18N
        btnOc.setText("OCM");
        btnOc.setBorder(null);
        btnOc.setBorderPainted(false);
        btnOc.setContentAreaFilled(false);
        btnOc.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnOc.setFocusPainted(false);
        btnOc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOcActionPerformed(evt);
            }
        });
        jPanel8.add(btnOc);

        jPanel5.add(jPanel8, java.awt.BorderLayout.CENTER);

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Lexend", 1, 14)); // NOI18N
        jLabel1.setText("ID:");
        jPanel9.add(jLabel1);

        lblId.setFont(new java.awt.Font("Lexend", 0, 12)); // NOI18N
        lblId.setText("123");
        jPanel9.add(lblId);

        jPanel5.add(jPanel9, java.awt.BorderLayout.EAST);

        jPanel2.add(jPanel5, java.awt.BorderLayout.PAGE_START);

        jPanel1.add(jPanel2, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnOcActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOcActionPerformed
        buscarArchivosPDF(btnOc.getText());
    }//GEN-LAST:event_btnOcActionPerformed

    private void Tabla1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Tabla1MouseClicked
        try{btnOc.setText(Tabla1.getValueAt(Tabla1.getSelectedRow(), 4).toString());}catch(Exception e){System.out.println(e);}
    }//GEN-LAST:event_Tabla1MouseClicked

    private void btnVistoMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnVistoMouseEntered
        pnlVisto.setBackground(new Color(0,102,204));
        btnVisto.setForeground(Color.white);
    }//GEN-LAST:event_btnVistoMouseEntered

    private void btnVistoMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnVistoMouseExited
        pnlVisto.setBackground(Color.white);
        btnVisto.setForeground(new Color(0,102,204));
    }//GEN-LAST:event_btnVistoMouseExited

    private void btnVistoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVistoActionPerformed
        try{
            Connection con;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            String sql = "update facturacion set visto = ? where idfacturacion = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            
            pst.setBoolean(1, true);
            pst.setString(2, lblId.getText());
            
            int n = pst.executeUpdate();
            
            if(n > 0){
                JOptionPane.showMessageDialog(this, "DATOS GUARDADOS");
                dispose();
            }
            
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "ERROR: "+e,"ERROR",JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnVistoActionPerformed

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
            java.util.logging.Logger.getLogger(info.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(info.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(info.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(info.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                info dialog = new info(new javax.swing.JFrame(), true,"");
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
    private javax.swing.JTable Tabla1;
    private javax.swing.JButton btnOc;
    private javax.swing.JButton btnVisto;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel label;
    private javax.swing.JLabel lblISR;
    public javax.swing.JLabel lblId;
    private javax.swing.JLabel lblIva;
    private javax.swing.JLabel lblSub;
    private javax.swing.JLabel lblTotal;
    private javax.swing.JPanel pnlVisto;
    // End of variables declaration//GEN-END:variables
}
