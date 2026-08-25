package VentanaEmergente.Diseño;

import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import static org.apache.poi.ss.usermodel.CellType.BOOLEAN;
import static org.apache.poi.ss.usermodel.CellType.NUMERIC;
import static org.apache.poi.ss.usermodel.CellType.STRING;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFColor;
import pruebas.OrdenDeCompra;

public class subirBOM extends javax.swing.JDialog {

    public void limpiarTabla(){
        Tabla1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Item No.", "Number Part", "Description", "QTY", "Vendor", "Coments/REQ's", "Ensamble", "Material", "Tratamiento", "Fresadora", "Torno", "CNC", "Mazak", "Soldador", "Ensamble"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        
        Tabla1.getTableHeader().setFont(new Font("Roboto", Font.BOLD, 14));
        Tabla1.setFont(new java.awt.Font("Roboto", Font.PLAIN, 12));
        Tabla1.getTableHeader().setOpaque(false);
        Tabla1.getTableHeader().setBackground(new Color(254,254,254));
        Tabla1.getTableHeader().setForeground(new Color(0, 78, 171));
        Tabla1.setRowHeight(25);
        Tabla1.setShowGrid(false);
        
        jScrollPane1.getViewport().setBackground(Color.white);
        jScrollPane1.setForeground(Color.white);
        jScrollPane1.getViewport().setForeground(Color.white);
        jScrollPane1.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(254, 254, 254)));
        jScrollPane1.setBackground(Color.white);
    }
    
    public void subirBOMV1(){
        try (FileInputStream archivoExcel = new FileInputStream("C:\\Users\\USUARIO 1\\Documents\\3i\\BOM 321.xlsx");
             Workbook workbook = WorkbookFactory.create(archivoExcel)) {

            Sheet hoja = workbook.getSheetAt(0);
            DefaultTableModel miModelo = (DefaultTableModel) Tabla1.getModel();
            String dat[] = {""};
            int fil = 0;
            boolean band = true;
            for (Row fila : hoja) {
                miModelo.addRow(dat);
                int cont = 0;
                    for (Cell celda : fila) {
                        if(cont != 0){
                        // Verifica el tipo de dato de la celda
                        String valor = "";
                        switch (celda.getCellType()) {
                            case STRING:
                                valor = (celda.getStringCellValue() + "\t");
                                break;
                            case NUMERIC:
                                valor = (celda.getNumericCellValue() + "\t");
                                break;
                            case BOOLEAN:
                                valor = (celda.getBooleanCellValue() + "\t");
                                break;
                            default:
                                valor = ("");
                                break;
                        }

                        // Verifica si la celda tiene un estilo aplicado
                        CellStyle estilo = celda.getCellStyle();
                        if (estilo != null) {
                            // Obtiene el color de fondo de la celda
                            org.apache.poi.ss.usermodel.Color colorFondo = estilo.getFillForegroundColorColor();

                            if (colorFondo instanceof XSSFColor) {
                                XSSFColor xssfColor = (XSSFColor) colorFondo;
//                                System.out.println("Color de fondo: " + Arrays.toString(xssfColor.getRGB()));
                                if(Arrays.toString(xssfColor.getRGB()).equals("[-1, -1, 0]")){
                                    Tabla1.setValueAt(valor, fil, cont-1);
                                    if(cont == 1){
                                        if(valor != null){
                                            if(!valor.equals("")){
                                                band = true;
                                            }
                                        }
                                    }
                                }
                            }
                        } else {
                            System.out.println("Sin estilo aplicado.");
                        }
                    }
                    cont++;
                }
                if(band){
                   fil++;
                   band = false;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void subirBOMV2(){
        JFileChooser archivoExcel = new JFileChooser();
        File archivoEx = archivoExcel.getSelectedFile();
        Workbook book;
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
                    if(IndiceFila > 1){
                        cont++;
                        Iterator ColumnaIterator=fila.cellIterator();
                        Object[]ListaColumna=new Object[17];
                        modelo.addRow(ListaColumna);
                        int IndiceColumna=-1;
                        while (ColumnaIterator.hasNext()){              
                            IndiceColumna++;
                            Cell celda=(Cell)ColumnaIterator.next();
                            if(IndiceColumna >= 0 && IndiceColumna <= 16){
                                String a = "";
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
                                    System.out.println(IndiceColumna + ", "+ ListaColumna[IndiceColumna]);
                                switch (IndiceColumna) {
                                    case 1:
                                        //CODIGO
                                        Tabla1.setValueAt(ListaColumna[IndiceColumna], cont, 0);
                                        break;
                                    case 2:
                                        //DESCRIPCION
                                        Tabla1.setValueAt(ListaColumna[IndiceColumna], cont, 1);
                                        break;
                                    case 3:
                                        //CANTIDAD
                                        Tabla1.setValueAt(ListaColumna[IndiceColumna], cont, 2);
                                        break;
                                    case 4:
                                        //PROVEEDOR
                                        Tabla1.setValueAt(ListaColumna[IndiceColumna], cont,3);
                                        break;
                                    case 5:
                                        //PROVEEDOR
                                        Tabla1.setValueAt(ListaColumna[IndiceColumna], cont,4);
                                        break;
                                    case 6:
                                        //PROVEEDOR
                                        Tabla1.setValueAt(ListaColumna[IndiceColumna], cont,5);
                                        break;
                                    case 7:
                                        //PROVEEDOR
                                        Tabla1.setValueAt(ListaColumna[IndiceColumna], cont,6);
                                        break;
                                    case 8:
                                        //PROVEEDOR
                                        Tabla1.setValueAt(ListaColumna[IndiceColumna], cont,7);
                                        break;
                                    case 9:
                                        //PROVEEDOR
                                        Tabla1.setValueAt(ListaColumna[IndiceColumna], cont,8);
                                        break;
                                    case 10:
                                        //PROVEEDOR
                                        Tabla1.setValueAt(ListaColumna[IndiceColumna], cont,9);
                                        break;
                                    case 11:
                                        //PROVEEDOR
                                        Tabla1.setValueAt(ListaColumna[IndiceColumna], cont,10);
                                        break;
                                    case 12:
                                        //PROVEEDOR
                                        Tabla1.setValueAt(ListaColumna[IndiceColumna], cont,11);
                                        break;
                                    case 13:
                                        //PROVEEDOR
                                        Tabla1.setValueAt(ListaColumna[IndiceColumna], cont,12);
                                        break;
                                    case 14:
                                        //PROVEEDOR
                                        Tabla1.setValueAt(ListaColumna[IndiceColumna], cont,13);
                                        break;
                                    case 15:
                                        //PROVEEDOR
                                        Tabla1.setValueAt(ListaColumna[IndiceColumna], cont,14);
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
    
    public subirBOM(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setLocationRelativeTo(null);
        limpiarTabla();
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel26 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        Tabla1 = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setBounds(new java.awt.Rectangle(0, 0, 0, 0));
        setPreferredSize(new java.awt.Dimension(1018, 520));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new java.awt.BorderLayout());

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setLayout(new java.awt.BorderLayout());

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));

        jLabel26.setFont(new java.awt.Font("Roboto", 1, 24)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(0, 165, 252));
        jLabel26.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel26.setText("Subir BOM de materiales");
        jPanel6.add(jLabel26);

        jPanel5.add(jPanel6, java.awt.BorderLayout.CENTER);

        jPanel1.add(jPanel5, java.awt.BorderLayout.PAGE_START);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new java.awt.BorderLayout());

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jButton1.setText("Subir");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton1);

        jPanel2.add(jPanel3, java.awt.BorderLayout.PAGE_START);

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setLayout(new java.awt.BorderLayout());

        Tabla1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Item No.", "Number Part", "Description", "QTY", "Vendor", "Coments/REQ's", "Ensamble", "Material", "Tratamiento", "Fresadora", "Torno", "CNC", "Mazak", "Soldador", "Ensamble"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(Tabla1);

        jPanel4.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jPanel2.add(jPanel4, java.awt.BorderLayout.CENTER);

        jPanel1.add(jPanel2, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        subirBOMV2();
    }//GEN-LAST:event_jButton1ActionPerformed

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
            java.util.logging.Logger.getLogger(subirBOM.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(subirBOM.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(subirBOM.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(subirBOM.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                subirBOM dialog = new subirBOM(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
