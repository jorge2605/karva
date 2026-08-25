package VentanaEmergente.cxp;

import java.awt.Desktop;
import java.io.File;
import java.util.prefs.Preferences;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import static org.apache.poi.ss.usermodel.CellType.BOOLEAN;
import static org.apache.poi.ss.usermodel.CellType.NUMERIC;
import static org.apache.poi.ss.usermodel.CellType.STRING;

public class Comparar extends javax.swing.JDialog {

    public File file1;
    public File file2;
    private static Preferences prefs = Preferences.userRoot().node("fileChooser");
    private static final String LAST_DIR_KEY = "lastDirectory";
    
    public void validarArchivos(){
        if(file1 != null && file2 != null){
            btnDescargar.setEnabled(true);
        }
    }
    
    public String celda(Cell cell){
        String valor;
        switch (cell.getCellType()) {
            case NUMERIC:
                valor = String.valueOf(cell.getNumericCellValue());
                break;
            case STRING:
                valor = String.valueOf(cell.getStringCellValue());
                break;
            case BOOLEAN:
                valor = String.valueOf(cell.getBooleanCellValue());
                break;
            default:
                valor = "";
        }
        return valor;
    }
    
    public Comparar(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel1 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        btnArchivo1 = new javax.swing.JButton();
        btnArchivo2 = new javax.swing.JButton();
        lblRuta1 = new javax.swing.JLabel();
        lblRuta2 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        btnDescargar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(1100, 500));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new java.awt.BorderLayout());

        jLabel12.setFont(new java.awt.Font("Lexend", 1, 24)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(0, 102, 204));
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setText("Comparacion CXP");
        jPanel1.add(jLabel12, java.awt.BorderLayout.NORTH);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new java.awt.BorderLayout());

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        java.awt.GridBagLayout jPanel3Layout = new java.awt.GridBagLayout();
        jPanel3Layout.columnWeights = new double[] {1.0, 1.0};
        jPanel3.setLayout(jPanel3Layout);

        jLabel1.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 51, 51));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Seleccionar formato de CXP");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        jPanel3.add(jLabel1, gridBagConstraints);

        jLabel2.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(51, 51, 51));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Seleccionar formato de pagos");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        jPanel3.add(jLabel2, gridBagConstraints);

        btnArchivo1.setBackground(new java.awt.Color(51, 153, 255));
        btnArchivo1.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        btnArchivo1.setForeground(new java.awt.Color(255, 255, 255));
        btnArchivo1.setText("Seleccionar Archivo");
        btnArchivo1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnArchivo1ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        jPanel3.add(btnArchivo1, gridBagConstraints);

        btnArchivo2.setBackground(new java.awt.Color(51, 153, 255));
        btnArchivo2.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        btnArchivo2.setForeground(new java.awt.Color(255, 255, 255));
        btnArchivo2.setText("Seleccionar Archivo");
        btnArchivo2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnArchivo2ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        jPanel3.add(btnArchivo2, gridBagConstraints);

        lblRuta1.setFont(new java.awt.Font("Roboto", 1, 10)); // NOI18N
        lblRuta1.setText("Ruta seleccionada");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        jPanel3.add(lblRuta1, gridBagConstraints);

        lblRuta2.setFont(new java.awt.Font("Roboto", 1, 10)); // NOI18N
        lblRuta2.setText("Ruta seleccionada");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        jPanel3.add(lblRuta2, gridBagConstraints);

        jPanel2.add(jPanel3, java.awt.BorderLayout.PAGE_START);

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setLayout(new java.awt.GridBagLayout());

        btnDescargar.setBackground(new java.awt.Color(51, 153, 255));
        btnDescargar.setFont(new java.awt.Font("Roboto Black", 1, 48)); // NOI18N
        btnDescargar.setForeground(new java.awt.Color(255, 255, 255));
        btnDescargar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/guardar.png"))); // NOI18N
        btnDescargar.setText("Descargar Excel   ");
        btnDescargar.setEnabled(false);
        btnDescargar.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        btnDescargar.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        btnDescargar.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        btnDescargar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDescargarActionPerformed(evt);
            }
        });
        jPanel4.add(btnDescargar, new java.awt.GridBagConstraints());

        jPanel2.add(jPanel4, java.awt.BorderLayout.CENTER);

        jPanel1.add(jPanel2, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnArchivo1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnArchivo1ActionPerformed
        JFileChooser sel = new JFileChooser();
        String lastDir = prefs.get(LAST_DIR_KEY, null);
        if (lastDir != null) {
            sel.setCurrentDirectory(new File(lastDir));
        }
        sel.setFileFilter(new FileNameExtensionFilter("Excel (xlsx)","xlsx"));
        int opc = sel.showSaveDialog(this);
        if(opc == JFileChooser.APPROVE_OPTION){
            file1 = sel.getSelectedFile();
            lblRuta1.setText(file1.getAbsolutePath());
            validarArchivos();
            prefs.put(LAST_DIR_KEY, file1.getParentFile().getAbsolutePath());
        }
    }//GEN-LAST:event_btnArchivo1ActionPerformed

    private void btnArchivo2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnArchivo2ActionPerformed
        JFileChooser sel = new JFileChooser();
        String lastDir = prefs.get(LAST_DIR_KEY, null);
        if (lastDir != null) {
            sel.setCurrentDirectory(new File(lastDir));
        }
        sel.setFileFilter(new FileNameExtensionFilter("Excel (xlsx)","xlsx"));
        int opc = sel.showSaveDialog(this);
        if(opc == JFileChooser.APPROVE_OPTION){
            file2 = sel.getSelectedFile();
            lblRuta2.setText(file2.getAbsolutePath());
            validarArchivos();
            prefs.put(LAST_DIR_KEY, file2.getParentFile().getAbsolutePath());
        }
    }//GEN-LAST:event_btnArchivo2ActionPerformed

    private void btnDescargarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDescargarActionPerformed
        
        try {
            FileInputStream archivo1;
            archivo1 = new FileInputStream(this.file1);
            FileInputStream archivo2;
            archivo2 = new FileInputStream(this.file2);
            
            Workbook workbook1 = new XSSFWorkbook(archivo1);
            Workbook workbook2 = new XSSFWorkbook(archivo2);

            Sheet sheet1 = workbook1.getSheetAt(0);
            Sheet sheet2 = workbook2.getSheetAt(0);

            // Crear un nuevo archivo para los resultados
            Workbook resultWorkbook = new XSSFWorkbook();
            Sheet resultSheet = resultWorkbook.createSheet("Comparación");

            // Almacenar los conceptos del segundo archivo en un Set para búsqueda rápida
            Stack<String> conceptosArchivo2 = new Stack<>();
            for (Row row : sheet2) {
                Cell cell = row.getCell(9); // Suponemos que los conceptos están en la columna 0
                if (cell != null) {
                    conceptosArchivo2.add(cell.getStringCellValue());
                }
            }

            // Comparar conceptos del primer archivo con los del segundo
            int rowNum = 0;
            for (Row row : sheet1) {
                Row newRow = resultSheet.createRow(rowNum++);
                Cell cell = row.getCell(9);
                Cell cell1 = row.getCell(2);
                Cell cell2 = row.getCell(3);
                Cell cell3 = row.getCell(4);
                Cell cell4 = row.getCell(7);
                Cell cell5 = row.getCell(11);
                Cell newCell = newRow.createCell(0);
                Cell cuenta = newRow.createCell(1);
                Cell banci = newRow.createCell(2);
                Cell razon = newRow.createCell(3);
                Cell precio = newRow.createCell(4);
                Cell email = newRow.createCell(5);
                
                if (cell != null) {
                    newCell.setCellValue(celda(cell));
                    cuenta.setCellValue(celda(cell1));
                    banci.setCellValue(celda(cell2));
                    razon.setCellValue(celda(cell3));
                    precio.setCellValue(celda(cell4));
                    email.setCellValue(celda(cell5));

                    // Estilo de celda
                    CellStyle style = resultWorkbook.createCellStyle();
                    boolean band = false;
                    for (int i = 0; i < conceptosArchivo2.size(); i++) {
                        if(conceptosArchivo2.get(i).contains(celda(cell))){
                            band = true;
                            break;
                        }
                    }
                    if (band) {
                        // Marcar en verde si existe en ambos archivos
                        style.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
                    } else {
                        // Marcar en rojo si no existe
                        style.setFillForegroundColor(IndexedColors.RED.getIndex());
                    }
                    style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                    newCell.setCellStyle(style);
                    cuenta.setCellStyle(style);
                    banci.setCellStyle(style);
                    razon.setCellStyle(style);
                    precio.setCellStyle(style);
                    email.setCellStyle(style);
                }
            }

            // Guardar el nuevo archivo
            JFileChooser sel = new JFileChooser();
            String lastDir = prefs.get(LAST_DIR_KEY, null);
            if (lastDir != null) {
                sel.setCurrentDirectory(new File(lastDir));
            }
            int n = sel.showSaveDialog(this);
            if(n == JFileChooser.APPROVE_OPTION){
                FileOutputStream outputFile;
                outputFile = new FileOutputStream(sel.getSelectedFile().getAbsolutePath()+".xlsx");
                resultWorkbook.write(outputFile);

                // Cerrar los archivos
                archivo1.close();
                archivo2.close();
                outputFile.close();
                workbook1.close();
                workbook2.close();
                resultWorkbook.close();

                try {
                    Desktop desktop = Desktop.getDesktop();
                    if (desktop.isSupported(Desktop.Action.OPEN) && new File(sel.getSelectedFile().getAbsolutePath()+".xlsx").exists()) {
                        desktop.open(new File(sel.getSelectedFile().getAbsolutePath()+".xlsx"));
                    } else {
                        System.out.println("No se puede abrir el archivo automáticamente. Abre manualmente en Excel.");
                    }
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(this, "Error al tratar de abrir el documento creado","Error",JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Comparar.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Comparar.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnDescargarActionPerformed

    public static void main(String args[]) {
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Comparar dialog = new Comparar(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnArchivo1;
    private javax.swing.JButton btnArchivo2;
    private javax.swing.JButton btnDescargar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JLabel lblRuta1;
    private javax.swing.JLabel lblRuta2;
    // End of variables declaration//GEN-END:variables
}
