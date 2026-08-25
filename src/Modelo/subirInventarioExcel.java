package Modelo;

import Conexiones.Conexion;
import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import org.apache.poi.ss.usermodel.Cell;
import static org.apache.poi.ss.usermodel.CellType.BOOLEAN;
import static org.apache.poi.ss.usermodel.CellType.NUMERIC;
import static org.apache.poi.ss.usermodel.CellType.STRING;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import pruebas.OrdenDeCompra;

public class subirInventarioExcel extends javax.swing.JFrame {

    JFileChooser seleccionar,archivoExcel;
    File archivo = null,archivoEx;
    
    public void extraerExcel(){
        archivoExcel = new JFileChooser();
        archivoEx = null;
        Workbook book = null;
        DefaultTableModel modelo = (DefaultTableModel) Tabla1.getModel();
        archivoExcel.setFileFilter(new FileNameExtensionFilter("Excel (*.xlsx)","xlsx"));
        if(archivoExcel.showDialog(null, "Abrir") == JFileChooser.APPROVE_OPTION){
            archivoEx = archivoExcel.getSelectedFile();
            String arch = archivoEx.getAbsolutePath();
            try {
            book=WorkbookFactory.create(new FileInputStream(arch));
            Sheet hoja=book.getSheetAt(0);
            Iterator FilaIterator=hoja.rowIterator();
            int IndiceFila=-1;
            int cont = -1;
            while (FilaIterator.hasNext()) {
                IndiceFila++;
                Row fila=(Row)FilaIterator.next();
                if(IndiceFila >= 0){
                
                cont++;
                Iterator ColumnaIterator=fila.cellIterator();
                Object[]ListaColumna=new Object[9999];
                
                
                modelo.addRow(ListaColumna);
                int IndiceColumna=-1;
                    while (ColumnaIterator.hasNext()){                
                    IndiceColumna++;
                    Cell celda=(Cell)ColumnaIterator.next();
                    if(IndiceColumna >= 0 && IndiceColumna < 6){
                    String a = "";
                        
                    
//                        if(celda!=null){
                            
                            switch (celda.getCellType()){
                                case NUMERIC:
                                    ListaColumna[IndiceColumna]=(celda.getNumericCellValue());
                                    break;
                                case STRING:
                                    ListaColumna[IndiceColumna]=celda.getStringCellValue();
                                    break;
                                case BOOLEAN:
                                    ListaColumna[IndiceColumna]=celda.getBooleanCellValue();
                                    break;
                                default:
                                     break;
                            }
//                        }
                        System.out.println(IndiceColumna + ", "+ ListaColumna[IndiceColumna]);
                    switch (IndiceColumna) {
                        case 0:
                            //CODIGO
                            int index;
                            try{index = ListaColumna[IndiceColumna].toString().indexOf(".0");}catch(Exception e){index = -1;}
                            if(index == -1){
                                Tabla1.setValueAt(ListaColumna[IndiceColumna].toString(), cont, 0);
                            }else{
                                Tabla1.setValueAt(ListaColumna[IndiceColumna].toString().substring(0,index), cont, 0);
                            }
                            break;
                        case 1:
                            //DESCRIPCION
                            Tabla1.setValueAt(ListaColumna[IndiceColumna], cont, 1);
                            break;
                        case 2:
                            //CANTIDAD
                            Tabla1.setValueAt(ListaColumna[IndiceColumna], cont, 2);
                            break;
                        case 3:
                            //PROVEEDOR
                            Tabla1.setValueAt(ListaColumna[IndiceColumna], cont,3);
                            System.out.println("--------------------------------------------------");
                            break;
                        default:
                            break;
                    }
                }
                }
            
            }  
            }
                
            book.close();
        } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "ERROR AL CREAR LIBRO"+e,"ERROR",JOptionPane.ERROR_MESSAGE);
        Logger.getLogger(OrdenDeCompra.class.getName()).log(Level.SEVERE, null, e);
        
        }
            
        }
    }
    
    public subirInventarioExcel() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        Tabla1 = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new java.awt.BorderLayout());

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jButton2.setText("Guardar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton2);

        jPanel2.add(jPanel3, java.awt.BorderLayout.PAGE_END);

        Tabla1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "PARTE", "DESCRIPCION", "CANTIDAD", "LOCACION"
            }
        ));
        jScrollPane1.setViewportView(Tabla1);

        jPanel2.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        jButton1.setText("Seleccionar archivo");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel4.add(jButton1);

        jPanel2.add(jPanel4, java.awt.BorderLayout.PAGE_START);

        getContentPane().add(jPanel2, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        extraerExcel();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        try{
            Connection con;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            for (int i = 0; i < Tabla1.getRowCount(); i++) {
                String parte;
                try{parte = Tabla1.getValueAt(i, 0).toString();}catch(Exception e){parte = "";}
                String sql = "select NumeroDeParte from Inventario where NumeroDeParte like '"+parte+"'";
                ResultSet rs = st.executeQuery(sql);
                String part = null;
                while(rs.next()){
                    part = rs.getString("NumeroDeParte");
                }
                if(part != null){
                    String sql1 = "update inventario set Descripcion = ?, Cantidad = ?, Ubicacion = ? where NumeroDeParte = ?";
                    PreparedStatement pst = con.prepareStatement(sql1);
                    
                    pst.setString(1, Tabla1.getValueAt(i, 1).toString());
                    pst.setString(2, Tabla1.getValueAt(i, 2).toString());
                    pst.setString(3, Tabla1.getValueAt(i, 3).toString());
                    pst.setString(4, Tabla1.getValueAt(i, 0).toString());
                    
                    int n = pst.executeUpdate();
                    if(n < 1){
                        System.err.println("No se guardo: "+Tabla1.getValueAt(i, 0).toString());
                    }else{
                        System.out.println("Se Actualizo: "+Tabla1.getValueAt(i, 0).toString());
                    }
                }else{
                    String sql1 = "insert into inventario (Descripcion, Cantidad, Ubicacion, NumeroDeParte) values(?,?,?,?)";
                    PreparedStatement pst = con.prepareStatement(sql1);
                    
                    pst.setString(1, Tabla1.getValueAt(i, 1).toString());
                    pst.setString(2, Tabla1.getValueAt(i, 2).toString());
                    pst.setString(3, Tabla1.getValueAt(i, 3).toString());
                    pst.setString(4, Tabla1.getValueAt(i, 0).toString());
                    
                    int n = pst.executeUpdate();
                    if(n < 1){
                        System.err.println("No se guardo: "+Tabla1.getValueAt(i, 0).toString());
                    }else{
                        System.out.println("Se Guardo: "+Tabla1.getValueAt(i, 0).toString());
                    }
                }
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "ERROR: "+e,"ERROR",JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jButton2ActionPerformed

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
            java.util.logging.Logger.getLogger(subirInventarioExcel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(subirInventarioExcel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(subirInventarioExcel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(subirInventarioExcel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new subirInventarioExcel().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable Tabla1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
