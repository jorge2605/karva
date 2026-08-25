package VentanaEmergente.Compras;

import Conexiones.Conexion;
import VentanaEmergente.Inicio1.Espera;
import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import static org.apache.poi.ss.usermodel.FillPatternType.SOLID_FOREGROUND;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellUtil;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import pruebas.CambiarEstado;
import pruebas.Inicio1;

public class ReporteMes extends javax.swing.JDialog {

    Espera espera;

    private void crearExcel() {
        Workbook book;
        try {
            espera = new Espera();
            espera.activar();
            espera.setLocationRelativeTo(this);
            espera.setExtendedState(Inicio1.MAXIMIZED_BOTH);
            espera.setVisible(true);
            JFileChooser fc = new JFileChooser();
            File archivo = null;
            fc.setFileFilter(new FileNameExtensionFilter("EXCEL (*.xlsx)", "xlsx"));
            int n = fc.showSaveDialog(this);

            if (n == JFileChooser.APPROVE_OPTION) {
                archivo = fc.getSelectedFile();
            }
            String a = "" + archivo;
            if (a.endsWith("xls")) {
                book = new HSSFWorkbook();
            } else {
                book = new XSSFWorkbook();
                a = archivo + ".xlsx";
            }

            Sheet hoja = book.createSheet("REPORTE DE COMPRAS POR MES");
            Row fila = hoja.createRow(2);
            Cell col = fila.createCell(2);

            //-------------------------------ESTILOS
            Font font = book.createFont();
            CellStyle estilo1 = book.createCellStyle();

            Font font3 = book.createFont();
            CellStyle estilo3 = book.createCellStyle();

            font.setBold(true);
            font.setColor(IndexedColors.BLACK.getIndex());
            font.setFontHeightInPoints((short) 12);
            estilo1.setFont(font);

            estilo1.setAlignment(HorizontalAlignment.LEFT);

            font3.setBold(false);
            font3.setColor(IndexedColors.BLACK.getIndex());
            font3.setFontHeightInPoints((short) 15);
            estilo3.setFont(font3);

            estilo3.setAlignment(HorizontalAlignment.CENTER);
            estilo3.setWrapText(true);

            //--------------------------------------
            //        hoja.setColumnWidth(2, 5000);
            //---------------------------------------
            int valorFijo = 28;

            hoja.setColumnWidth(2, valorFijo * 50); // Requisicion
            hoja.setColumnWidth(3, valorFijo * 350); //Codigo
            hoja.setColumnWidth(4, valorFijo * 450); //Descripcion
            hoja.setColumnWidth(5, valorFijo * 160);//Proytecto
            hoja.setColumnWidth(6, valorFijo * 50);//Cantidad
            hoja.setColumnWidth(7, valorFijo * 200);//Requisitor
            hoja.setColumnWidth(8, valorFijo * 50);//UM
            hoja.setColumnWidth(9, valorFijo * 230);//Proveedor
            hoja.setColumnWidth(10, valorFijo * 150);//Notas
            hoja.setColumnWidth(11, valorFijo * 50);//Cantidad stock
            hoja.setColumnWidth(12, valorFijo * 95);//Fecha Esperada
            hoja.setColumnWidth(13, valorFijo * 95);//Fecha Esperada

            Font font1 = book.createFont();
            CellStyle style = book.createCellStyle();

            font1.setBold(true);
            font1.setColor(IndexedColors.WHITE.getIndex());
            font1.setFontHeightInPoints((short) 16);
            style.setFont(font1);

            style.setFillBackgroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
            style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
            style.setFillPattern(SOLID_FOREGROUND);
            style.setVerticalAlignment(VerticalAlignment.BOTTOM);
            style.setAlignment(HorizontalAlignment.CENTER);
            style.setWrapText(true);

            hoja.addMergedRegion(new CellRangeAddress(
                    2,
                    2,
                    2,
                    15
            ));

            Map<String, Object> properties = new HashMap<String, Object>();
            properties.put(CellUtil.BORDER_TOP, BorderStyle.MEDIUM);
            properties.put(CellUtil.BORDER_BOTTOM, BorderStyle.MEDIUM);
            properties.put(CellUtil.BORDER_LEFT, BorderStyle.MEDIUM);
            properties.put(CellUtil.BORDER_RIGHT, BorderStyle.MEDIUM);

            properties.put(CellUtil.TOP_BORDER_COLOR, IndexedColors.BLACK.getIndex());
            properties.put(CellUtil.BOTTOM_BORDER_COLOR, IndexedColors.BLACK.getIndex());
            properties.put(CellUtil.LEFT_BORDER_COLOR, IndexedColors.BLACK.getIndex());
            properties.put(CellUtil.RIGHT_BORDER_COLOR, IndexedColors.BLACK.getIndex());

            col.setCellStyle(style);
            col.setCellValue("REPORTE DE COMPRAS POR MES");

            Row headerRow = hoja.createRow(6);

            String[] columnNames = {
                "Requisicion", "Codigo", "Descripcion", "Proyecto", "Cantidad",
                "Requisitor", "UM", "Proveedor", "Notas", "Cantidad Stock", "Fecha Esperada",
                "OC", "Precio", "Fecha OC"
            };

            CellStyle es = book.createCellStyle();
            Font fi = book.createFont();
            fi.setBold(true);
            fi.setColor(IndexedColors.WHITE.getIndex());
            es.setFont(fi);
            es.setFillForegroundColor(IndexedColors.GREY_80_PERCENT.getIndex());
            es.setFillPattern(SOLID_FOREGROUND);

            for (int i = 0; i < columnNames.length; i++) {
                Cell cell = headerRow.createCell(i + 2);
                cell.setCellStyle(es);
                cell.setCellValue(columnNames[i]);
            }

            //------------------CONECCION A BASE DE DATOS-----------------------
            Connection con;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String fecha1 = sdf.format(Fecha1.getDatoFecha());
            String fecha2 = sdf.format(Fecha2.getDatoFecha());

            String precio = "";
            String llego = "";
            String orden;

            if (chbPrecio.isSelected()) {
                precio = " and Precio is not null and Precio != ''";
            }
            if (chbLlego.isSelected()) {
                llego = " and Llego is not null and Llego != ''";
            }
            if (chbOrden.isSelected()) {
                orden = " and OC is not null and OC != ''";
            } else {
                orden = " and OC is null";
            }

            String sql2 = "select req.id, req.NumRequisicion, req.Codigo, req.Descripcion, req.Proyecto, req.Cantidad, req.Requisitor, req.UM, "
                    + "req.Proveedor, req.Precio, req.Llego, req.OC, req.FechaRecibo, req.Notas, req.CantidadStock, req.FechaEsperada, "
                    + "r.FechaNew , r.progreso, odc.Fecha as fec "
                    + "from requisiciones as req "
                    + "left join ordencompra as odc on req.OC = odc.OrdenNo "
                    + "inner join requisicion as r on req.NumRequisicion = r.Id "
                    + "where OC is null and r.FechaNew between "
                    + "'" + fecha1 + "' and '" + fecha2 + "' " + precio + llego + orden;
            Statement st2 = con.createStatement();
            ResultSet rs2 = st2.executeQuery(sql2);
            String datos[] = new String[20];
            int i = 0;
            while (rs2.next()) {
                datos[0] = rs2.getString("NumRequisicion");
                datos[1] = rs2.getString("Codigo");
                datos[2] = rs2.getString("Descripcion");
                datos[3] = rs2.getString("Proyecto");
                datos[4] = rs2.getString("Cantidad");
                datos[5] = rs2.getString("Requisitor");
                datos[6] = rs2.getString("UM");
                datos[7] = rs2.getString("Proveedor");
                datos[8] = rs2.getString("Notas");
                datos[9] = rs2.getString("cantidadStock");
                datos[10] = rs2.getString("FechaEsperada");
                datos[11] = rs2.getString("OC");
                datos[12] = rs2.getString("Precio");
                datos[13] = rs2.getString("fec");
                datos[14] = rs2.getString("Progreso");
                if (!datos[14].contains("CANCELADO")) {
                    Row fila10 = hoja.createRow(i + 7);
                    for (int j = 0; j < 14; j++) {
                        Cell celda = fila10.createCell(j + 2);
                        if (i == -1 && (j >= 0 && j <= 13)) {
                            CellStyle s = book.createCellStyle();
                            Font f = book.createFont();
                            f.setBold(true);
                            f.setColor(IndexedColors.WHITE.getIndex());
                            s.setFont(f);
                            s.setFillForegroundColor(IndexedColors.GREY_50_PERCENT.getIndex());
                            s.setFillPattern(SOLID_FOREGROUND);
                            celda.setCellStyle(s);
                        }
                        if (i > -1 && (j > -1 && j <= 13) && (i % 2 == 0)) {
                            CellStyle s = book.createCellStyle();
                            s.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
                            s.setFillPattern(SOLID_FOREGROUND);
                            celda.setCellStyle(s);
                        }

                        if (i == -1) {
                            celda.setCellValue(String.valueOf(datos[j]));
                        } else {
                            if (j == 3) {
                                CellStyle ss = book.createCellStyle();
                                ss.setWrapText(true);

                                if (i % 2 == 0) {
                                    ss.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
                                    ss.setFillPattern(SOLID_FOREGROUND);

                                }
                                celda.setCellStyle(ss);
                            }
                            celda.setCellValue(String.valueOf(datos[j]));
                        }
                        book.write(new FileOutputStream(a));
                    }
                    i++;
                }
            }
            book.close();
            espera.dispose();
            try {
                Desktop desktop = Desktop.getDesktop();
                if (desktop.isSupported(Desktop.Action.OPEN) && new File(a).exists()) {
                    desktop.open(new File(a));
                } else {
                    JOptionPane.showMessageDialog(this, "No se puede abrir el archivo automáticamente. Abre manualmente en Excel.", "Error", JOptionPane.ERROR_MESSAGE);
                    espera.dispose();
                }
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error al tratar de abrir el documento creado", "Error", JOptionPane.ERROR_MESSAGE);
                espera.dispose();
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(CambiarEstado.class.getName()).log(Level.SEVERE, null, ex);
            espera.dispose();
        } catch (IOException ex) {
            Logger.getLogger(CambiarEstado.class.getName()).log(Level.SEVERE, null, ex);
            espera.dispose();
        } catch (SQLException e) {
            Logger.getLogger(CambiarEstado.class.getName()).log(Level.SEVERE, null, e);
            espera.dispose();
            JOptionPane.showMessageDialog(this, "Error: " + e, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public ReporteMes(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        chbPrecio = new javax.swing.JCheckBox();
        chbLlego = new javax.swing.JCheckBox();
        chbOrden = new javax.swing.JCheckBox();
        btnGuardar = new scrollPane.BotonRedondo();
        btnGuardar1 = new scrollPane.BotonRedondo();
        Fecha1 = new rojeru_san.rsdate.RSDateChooser();
        Fecha2 = new rojeru_san.rsdate.RSDateChooser();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(420, 599));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new java.awt.BorderLayout());

        jLabel1.setFont(new java.awt.Font("Lexend", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 102, 204));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Reporte de compras");
        jPanel1.add(jLabel1, java.awt.BorderLayout.PAGE_START);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        java.awt.GridBagLayout jPanel2Layout = new java.awt.GridBagLayout();
        jPanel2Layout.columnWeights = new double[] {1.0, 1.0};
        jPanel2Layout.rowWeights = new double[] {1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0};
        jPanel2.setLayout(jPanel2Layout);

        chbPrecio.setBackground(new java.awt.Color(255, 255, 255));
        chbPrecio.setFont(new java.awt.Font("Lexend", 1, 12)); // NOI18N
        chbPrecio.setText("Con precio");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.ipadx = 5;
        gridBagConstraints.ipady = 5;
        gridBagConstraints.insets = new java.awt.Insets(9, 5, 9, 5);
        jPanel2.add(chbPrecio, gridBagConstraints);

        chbLlego.setBackground(new java.awt.Color(255, 255, 255));
        chbLlego.setFont(new java.awt.Font("Lexend", 1, 12)); // NOI18N
        chbLlego.setText("Llego");
        chbLlego.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chbLlegoActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.ipadx = 5;
        gridBagConstraints.ipady = 5;
        gridBagConstraints.insets = new java.awt.Insets(9, 5, 9, 5);
        jPanel2.add(chbLlego, gridBagConstraints);

        chbOrden.setBackground(new java.awt.Color(255, 255, 255));
        chbOrden.setFont(new java.awt.Font("Lexend", 1, 12)); // NOI18N
        chbOrden.setText("Orden de compra");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 2;
        jPanel2.add(chbOrden, gridBagConstraints);

        btnGuardar.setBackground(new java.awt.Color(255, 255, 255));
        btnGuardar.setForeground(new java.awt.Color(204, 0, 0));
        btnGuardar.setText("Cancelar");
        btnGuardar.setBorderColor(new java.awt.Color(204, 0, 0));
        btnGuardar.setBorderColorSelected(new java.awt.Color(102, 0, 0));
        btnGuardar.setColor(new java.awt.Color(204, 0, 0));
        btnGuardar.setFont(new java.awt.Font("Lexend", 0, 14)); // NOI18N
        btnGuardar.setPreferredSize(new java.awt.Dimension(180, 35));
        btnGuardar.setThickness(3);
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.insets = new java.awt.Insets(6, 0, 6, 0);
        jPanel2.add(btnGuardar, gridBagConstraints);

        btnGuardar1.setBackground(new java.awt.Color(255, 255, 255));
        btnGuardar1.setForeground(new java.awt.Color(0, 153, 0));
        btnGuardar1.setText("Generar Excel");
        btnGuardar1.setBorderColor(new java.awt.Color(0, 153, 0));
        btnGuardar1.setBorderColorSelected(new java.awt.Color(0, 102, 0));
        btnGuardar1.setColor(new java.awt.Color(0, 153, 0));
        btnGuardar1.setFont(new java.awt.Font("Lexend", 0, 14)); // NOI18N
        btnGuardar1.setPreferredSize(new java.awt.Dimension(180, 35));
        btnGuardar1.setThickness(3);
        btnGuardar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardar1ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.insets = new java.awt.Insets(6, 0, 6, 0);
        jPanel2.add(btnGuardar1, gridBagConstraints);

        Fecha1.setPreferredSize(new java.awt.Dimension(180, 40));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(10, 55, 10, 55);
        jPanel2.add(Fecha1, gridBagConstraints);

        Fecha2.setPreferredSize(new java.awt.Dimension(180, 40));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(10, 55, 10, 55);
        jPanel2.add(Fecha2, gridBagConstraints);

        jLabel2.setText(" ");
        jLabel2.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 102, 204)));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.ipadx = 300;
        gridBagConstraints.ipady = 13;
        gridBagConstraints.insets = new java.awt.Insets(3, 10, 3, 10);
        jPanel2.add(jLabel2, gridBagConstraints);

        jPanel1.add(jPanel2, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void chbLlegoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chbLlegoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_chbLlegoActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        dispose();
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnGuardar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardar1ActionPerformed
        crearExcel();
    }//GEN-LAST:event_btnGuardar1ActionPerformed

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
            java.util.logging.Logger.getLogger(ReporteMes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ReporteMes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ReporteMes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ReporteMes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                ReporteMes dialog = new ReporteMes(new javax.swing.JFrame(), true);
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
    private rojeru_san.rsdate.RSDateChooser Fecha1;
    private rojeru_san.rsdate.RSDateChooser Fecha2;
    public scrollPane.BotonRedondo btnGuardar;
    public scrollPane.BotonRedondo btnGuardar1;
    private javax.swing.JCheckBox chbLlego;
    private javax.swing.JCheckBox chbOrden;
    private javax.swing.JCheckBox chbPrecio;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    // End of variables declaration//GEN-END:variables
}
