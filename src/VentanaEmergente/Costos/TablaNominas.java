package VentanaEmergente.Costos;

import Conexiones.Conexion;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.table.DefaultTableModel;

public class TablaNominas extends javax.swing.JDialog {

    public double MODDiseño = 0;
    public double MODHerramentista = 0;
    public double MOI = 0;
    public double CI = 0;
    public double MODElectromecanico = 0;
    
    public final void limpiarTablaNominas(){
        TablaNominas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null}
            },
            new String [] {
                "ID", "Nombre", "Departamento", "HS", "S"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        Action pasteAction = new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    pasteClipboard(TablaNominas);
                }
            };
        TablaNominas.getActionMap().put("paste", pasteAction);
        TablaNominas.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_V, KeyEvent.CTRL_DOWN_MASK), "paste");
        TablaNominas.getTableHeader().setFont(new Font("Lexend", Font.BOLD, 14));
        TablaNominas.setFont(new Font("Lexend", Font.PLAIN, 12));
        TablaNominas.getTableHeader().setOpaque(false);
        TablaNominas.getTableHeader().setBackground(new Color(0, 78, 171));
        TablaNominas.getTableHeader().setForeground(Color.white);
        TablaNominas.setRowHeight(25);
        TablaNominas.setShowVerticalLines(false);
        TablaNominas.setGridColor(new Color(240,240,240));
        
        jScrollPane8.getViewport().setBackground(new Color(255,255,255));
        jScrollPane8.getViewport().setForeground(new Color(255,255,255));
        
        if (TablaNominas.getColumnModel().getColumnCount() > 0) {
            TablaNominas.getColumnModel().getColumn(0).setMinWidth(35);
            TablaNominas.getColumnModel().getColumn(0).setPreferredWidth(35);
            TablaNominas.getColumnModel().getColumn(0).setMaxWidth(35);
            TablaNominas.getColumnModel().getColumn(1).setMinWidth(200);
            TablaNominas.getColumnModel().getColumn(1).setPreferredWidth(200);
            TablaNominas.getColumnModel().getColumn(1).setMaxWidth(200);
            TablaNominas.getColumnModel().getColumn(2).setMinWidth(200);
            TablaNominas.getColumnModel().getColumn(2).setPreferredWidth(200);
            TablaNominas.getColumnModel().getColumn(2).setMaxWidth(200);
        }
    }
    
    public void verEmpleado(){
        try{
            Connection con;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            String sql = "select * from costohoras";
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
                MODDiseño = Double.parseDouble(rs.getString("MODDiseño"));
                MODElectromecanico = Double.parseDouble(rs.getString("MODElectromecanico"));
                MODHerramentista = Double.parseDouble(rs.getString("MODHerramentista"));
                MOI = Double.parseDouble(rs.getString("MOI"));
                CI = Double.parseDouble(rs.getString("CI"));
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "ERROR: "+e,"ERROR",JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public String getPuesto(String numero){
        try{
            Connection con;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            int num = Integer.parseInt(numero);
            String sql = "select * from empleadoscheck where NumEmpleado like '"+num+"'";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            String puesto = null;
            while(rs.next()){
                puesto = rs.getString("Departamento");
            }
            return puesto;
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "Error: "+e,"error",JOptionPane.ERROR_MESSAGE);
        }
        return null;
    }
    
    private void pasteClipboard(JTable table) {
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        Transferable transferable = clipboard.getContents(null);
        DefaultTableModel miModelo = (DefaultTableModel) TablaNominas.getModel();
        if (transferable != null && transferable.isDataFlavorSupported(DataFlavor.stringFlavor)) {
            try {
                String clipboardData = (String) transferable.getTransferData(DataFlavor.stringFlavor);

                String[] rows = clipboardData.split("\n");
                int startRow = table.getSelectedRow();
                int startCol = table.getSelectedColumn();
                
                for (int i = 0; i < rows.length; i++) {
                    String[] values = rows[i].split("\t");
                    if(values.length != 4) {
                        JOptionPane.showMessageDialog(this, "Debes Copiar 4 columnas, tus columnas seleccionadas son: " + values.length,"Error",JOptionPane.ERROR_MESSAGE);
                        break;
                    }
                    for (int j = 0; j < values.length; j++) {
                        int real = j;
                        if(j >= 2){
                            real += 1;
                        }
                        if (startRow + i < table.getRowCount() && startCol + j < table.getColumnCount()) {
                            
                            table.setValueAt(values[j], startRow + i, startCol + real);
                            if(values[j].contains("$")){
                                String nuevo = values[j].replace("$", "").replace(",", "");
                                double valor = Double.parseDouble(nuevo);
                                table.setValueAt(String.valueOf(valor), startRow + i, (startCol) + real);
//                                table.setValueAt(String.valueOf(valorNew), startRow + i, (startCol +1) + j);
                            }else if(j == 0){
                                table.setValueAt(getPuesto(values[j]), startRow + i, (startCol + 2) + real);
                            }
                        }else{
                            String dat[] = {"","",""};
                            miModelo.addRow(dat);
                            table.setValueAt(values[j], startRow + i, startCol + real);
                            if(j == 0){
                                table.setValueAt(getPuesto(values[j]), startRow + i, (startCol + 2) + real);
                            }
                        }
                    }
                }
            } catch (UnsupportedFlavorException | IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    public final void getPrecios(){
        try{
            Connection con;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st =con.createStatement();
            String sql = "select * from costohoras";
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
                MODDiseño = Double.parseDouble(rs.getString("MODDiseño"));
                MODElectromecanico = Double.parseDouble(rs.getString("MODElectromecanico"));
                MODHerramentista = Double.parseDouble(rs.getString("MODHerramentista"));
                MOI = Double.parseDouble(rs.getString("MOI"));
                CI = Double.parseDouble(rs.getString("CI"));
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "Error: " +e, "Error",JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public JTable getTabla(int empieza){
        initComponents();
        limpiarTablaNominas();
        getPrecios();
        TablaNominas.getColumnModel().getColumn(3).setHeaderValue("HS"+empieza);
        TablaNominas.getColumnModel().getColumn(4).setHeaderValue("S"+empieza);
        setVisible(true);
        if(TablaNominas.getRowCount() > 0){
            return TablaNominas;
        }else{
            return null;
        }
    }
    
    public TablaNominas(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane8 = new javax.swing.JScrollPane();
        TablaNominas = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        panelBtn1 = new javax.swing.JPanel();
        btnConvertir = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        panelBtn = new javax.swing.JPanel();
        btnGuardar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(1083, 581));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new java.awt.BorderLayout(10, 10));

        jLabel1.setFont(new java.awt.Font("Lexend", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 153, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Agregar nomina");
        jPanel1.add(jLabel1, java.awt.BorderLayout.PAGE_START);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new java.awt.BorderLayout());

        jScrollPane8.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 2, true));

        TablaNominas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null}
            },
            new String [] {
                "ID", "Nombre", "Departamento", "HS", "S"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        TablaNominas.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                TablaNominasMouseMoved(evt);
            }
        });
        TablaNominas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TablaNominasMouseClicked(evt);
            }
        });
        jScrollPane8.setViewportView(TablaNominas);

        jPanel2.add(jScrollPane8, java.awt.BorderLayout.CENTER);

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        panelBtn1.setBackground(new java.awt.Color(255, 255, 255));

        btnConvertir.setBackground(new java.awt.Color(255, 255, 255));
        btnConvertir.setFont(new java.awt.Font("Lexend", 1, 14)); // NOI18N
        btnConvertir.setForeground(new java.awt.Color(255, 102, 0));
        btnConvertir.setText("Convertir HS");
        btnConvertir.setBorder(null);
        btnConvertir.setBorderPainted(false);
        btnConvertir.setContentAreaFilled(false);
        btnConvertir.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnConvertir.setFocusPainted(false);
        btnConvertir.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnConvertirMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnConvertirMouseExited(evt);
            }
        });
        btnConvertir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConvertirActionPerformed(evt);
            }
        });
        panelBtn1.add(btnConvertir);

        jPanel4.add(panelBtn1);

        jPanel2.add(jPanel4, java.awt.BorderLayout.PAGE_START);

        jPanel1.add(jPanel2, java.awt.BorderLayout.CENTER);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        panelBtn.setBackground(new java.awt.Color(255, 255, 255));

        btnGuardar.setBackground(new java.awt.Color(255, 255, 255));
        btnGuardar.setFont(new java.awt.Font("Lexend", 1, 14)); // NOI18N
        btnGuardar.setForeground(new java.awt.Color(51, 153, 255));
        btnGuardar.setText("Guardar");
        btnGuardar.setBorder(null);
        btnGuardar.setBorderPainted(false);
        btnGuardar.setContentAreaFilled(false);
        btnGuardar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnGuardar.setFocusPainted(false);
        btnGuardar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnGuardarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnGuardarMouseExited(evt);
            }
        });
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });
        panelBtn.add(btnGuardar);

        jPanel3.add(panelBtn);

        jPanel1.add(jPanel3, java.awt.BorderLayout.PAGE_END);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void TablaNominasMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TablaNominasMouseMoved
        int col = TablaNominas.columnAtPoint(evt.getPoint());
        if(col == 2){
            TablaNominas.setCursor(new Cursor(Cursor.HAND_CURSOR));
        }else{
            TablaNominas.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_TablaNominasMouseMoved

    private void TablaNominasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TablaNominasMouseClicked
        if(evt.getClickCount() == 2){
            if(TablaNominas.getValueAt(TablaNominas.getSelectedRow(), 0).toString() != null){
                if(!TablaNominas.getValueAt(TablaNominas.getSelectedRow(), 0).toString().equals("")){
                    JFrame f = (JFrame) JOptionPane.getFrameForComponent(this);
                    EditarEmpleados costo = new EditarEmpleados(f,true);
                    costo.limpiarTabla();
                    int num = Integer.parseInt(TablaNominas.getValueAt(TablaNominas.getSelectedRow(), 0).toString());
                    costo.verEmpleado(String.valueOf(num));
                    costo.setVisible(true);
                    TablaNominas.setValueAt(getPuesto(String.valueOf(num)), TablaNominas.getSelectedRow(), 2);
                }
            }
        }
    }//GEN-LAST:event_TablaNominasMouseClicked

    private void btnGuardarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGuardarMouseEntered
        panelBtn.setBackground(new Color(0,102,204));
        btnGuardar.setForeground(Color.white);
    }//GEN-LAST:event_btnGuardarMouseEntered

    private void btnGuardarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGuardarMouseExited
        panelBtn.setBackground(Color.white);
        btnGuardar.setForeground(new Color(51,153,255));
    }//GEN-LAST:event_btnGuardarMouseExited

    private void btnConvertirMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnConvertirMouseEntered
        panelBtn1.setBackground(new Color(255,102,0));
        btnConvertir.setForeground(Color.white);
    }//GEN-LAST:event_btnConvertirMouseEntered

    private void btnConvertirMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnConvertirMouseExited
        panelBtn1.setBackground(Color.white);
        btnConvertir.setForeground(new Color(255,102,0));
    }//GEN-LAST:event_btnConvertirMouseExited

    private void btnConvertirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConvertirActionPerformed
        for (int i = 0; i < TablaNominas.getRowCount(); i++) {
            String puesto;
            double valor;
            try{valor = Double.parseDouble(TablaNominas.getValueAt(i, 3).toString());}catch(Exception e){valor = 0;}
            double valorNew = 0;
            try{puesto = TablaNominas.getValueAt(i, 2).toString();}catch(Exception e){puesto = "";}
            if(!"".equals(puesto)){
                if(puesto.equals("MOD DISENADOR")){
                    valorNew = valor / MODDiseño;
                }else if(puesto.equals("MOD HERRAMENTISTA")){
                    valorNew = valor / MODHerramentista;
                }else if(puesto.equals("MOD ELECTROMECANICO")){
                    valorNew = valor / MODElectromecanico;
                }else if(puesto.contains("MOI")){
                    valorNew = valor / MOI;
                }else if(puesto.contains("CI")){
                    valorNew = valor / CI;
                }else{
                    valorNew = 0;
                }
            }
            double decimal = valorNew - Math.floor(valorNew);
            if(decimal < .40){
                valorNew = Math.floor(valorNew);
            }else{
                valorNew = Math.ceil(valorNew);
            }
            TablaNominas.setValueAt((valorNew + 48), i, 3);
        }
    }//GEN-LAST:event_btnConvertirActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        dispose();
    }//GEN-LAST:event_btnGuardarActionPerformed

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
            java.util.logging.Logger.getLogger(TablaNominas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TablaNominas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TablaNominas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TablaNominas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                TablaNominas dialog = new TablaNominas(new javax.swing.JFrame(), true);
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
    public javax.swing.JTable TablaNominas;
    private javax.swing.JButton btnConvertir;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JPanel panelBtn;
    private javax.swing.JPanel panelBtn1;
    // End of variables declaration//GEN-END:variables
}
