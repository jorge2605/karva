package VentanaEmergente.Compras;

import Conexiones.Conexion;
import VentanaEmergente.Inicio1.Espera;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Font;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import org.apache.pdfbox.util.Hex;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import static org.apache.poi.ss.usermodel.FillPatternType.SOLID_FOREGROUND;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import pruebas.CambiarEstado;
import pruebas.Inicio1;
import scrollPane.ScrollBarCustom;

public class OCPendientes extends java.awt.Dialog {

    Espera espera;

    public final void verDatos() {
        try {
            espera.activar();
            espera.setLocationRelativeTo(this);
            espera.setExtendedState(Inicio1.MAXIMIZED_BOTH);
            espera.setVisible(true);
            Connection con;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            Statement st2 = con.createStatement();
            String sql = "select * from requisiciones where Llego is null and OC != 'CANCELADO' and NumRequisicion > 4196";
            ResultSet rs = st.executeQuery(sql);
            DefaultTableModel miModelo = (DefaultTableModel) Tabla1.getModel();
            String datos[] = new String[12];
            while (rs.next()) {
                datos[0] = rs.getString("NumRequisicion");
                datos[1] = rs.getString("Codigo");
                datos[2] = rs.getString("Descripcion");
                datos[3] = rs.getString("OC");
                datos[4] = rs.getString("Proyecto");
                datos[5] = rs.getString("Cantidad");
                datos[6] = rs.getString("Requisitor");
                datos[7] = rs.getString("UM");
                datos[8] = rs.getString("Proveedor");
                datos[9] = rs.getString("Notas");
                datos[10] = rs.getString("FechaEsperada");
                String sql2 = "select Progreso, Id, Fecha from requisicion where Id like '" + datos[0] + "'";
                ResultSet rs2 = st2.executeQuery(sql2);
                while (rs2.next()) {
                    String estado = rs2.getString("Progreso");
                    String fecha = rs2.getString("Fecha");
                    if (!estado.equals("CANCELADO")) {
                        datos[11] = fecha;
                        miModelo.addRow(datos);
                    }
                }
            }
            espera.dispose();
        } catch (SQLException e) {
            espera.dispose();
            JOptionPane.showMessageDialog(this, "Error: " + e, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public final void limpiarTabla() {
        Tabla1.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{},
                new String[]{
                    "Requisicion", "Codigo", "Descripcion", "OC", "Proyecto", "Cantidad", "Requisitor", "UM", "Proveedor", "Notas", "Fecha Esperada", "Fecha Requi"
                }
        ) {
            boolean[] canEdit = new boolean[]{
                false, false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
        DefaultTableModel model = (DefaultTableModel) Tabla1.getModel();
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        Tabla1.setRowSorter(sorter);
        Tabla1.getTableHeader().setFont(new Font("Roboto", Font.BOLD, 14));
        Tabla1.getTableHeader().setOpaque(false);
        Tabla1.getTableHeader().setBackground(new Color(0, 78, 171));
        Tabla1.getTableHeader().setForeground(Color.white);
        Tabla1.setRowHeight(25);
        Tabla1.setShowVerticalLines(false);
        Tabla1.setGridColor(new Color(240, 240, 240));

        jScrollPane1.getViewport().setBackground(new Color(255, 255, 255));
        jScrollPane1.setVerticalScrollBar(new ScrollBarCustom(new Color(0, 165, 255)));
    }

    public OCPendientes(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        revalidate();
        repaint();
        limpiarTabla();
        espera = new Espera();
        Thread hilo = new Thread() {
            public void run() {
                verDatos();
            }
        };
        hilo.start();
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        Tabla1 = new javax.swing.JTable();

        setPreferredSize(new java.awt.Dimension(1000, 650));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                closeDialog(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new java.awt.BorderLayout());

        jLabel1.setFont(new java.awt.Font("Lexend", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 102, 204));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Ordenes de compra pendientes por llegar");
        jPanel1.add(jLabel1, java.awt.BorderLayout.PAGE_START);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new java.awt.BorderLayout());

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jButton1.setBackground(new java.awt.Color(255, 255, 255));
        jButton1.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        jButton1.setForeground(new java.awt.Color(0, 102, 0));
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/excel_1.png"))); // NOI18N
        jButton1.setText("Exportar a Excel");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton1);

        jPanel2.add(jPanel3, java.awt.BorderLayout.NORTH);

        jPanel4.setLayout(new java.awt.BorderLayout());

        Tabla1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Requisicion", "Codigo", "Descripcion", "OC", "Proyecto", "Cantidad", "Requisitor", "UM", "Proveedor", "Notas", "Fecha Esperada", "Fecha Requi"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(Tabla1);

        jPanel4.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jPanel2.add(jPanel4, java.awt.BorderLayout.CENTER);

        jPanel1.add(jPanel2, java.awt.BorderLayout.CENTER);

        add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void closeDialog(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_closeDialog
        setVisible(false);
        dispose();
    }//GEN-LAST:event_closeDialog

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        Workbook book;
        try {
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

            Sheet hoja = book.createSheet("T.E. VENCIDO");
            Row fila = hoja.createRow(2);
            Cell col = fila.createCell(2);

            //-------------------------------ESTILOS
            org.apache.poi.ss.usermodel.Font font = book.createFont();
            CellStyle estilo1 = book.createCellStyle();

            org.apache.poi.ss.usermodel.Font font3 = book.createFont();
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
            org.apache.poi.ss.usermodel.Font font1 = book.createFont();
            XSSFCellStyle style = (XSSFCellStyle) book.createCellStyle();

            font1.setBold(true);
            font1.setColor(IndexedColors.WHITE.getIndex());
            font1.setFontHeightInPoints((short) 16);
            style.setFont(font1);

            String rgbS = "004c74";
            byte[] rgbB = Hex.decodeHex(rgbS);
            XSSFColor color = new XSSFColor(rgbB, null);

            style.setFillBackgroundColor(color);
            style.setFillForegroundColor(color);
            style.setFillPattern(SOLID_FOREGROUND);
            style.setVerticalAlignment(VerticalAlignment.BOTTOM);
            style.setAlignment(HorizontalAlignment.CENTER);
            style.setWrapText(true);

            hoja.addMergedRegion(new CellRangeAddress(
                    2,
                    2,
                    2,
                    13
            ));

            col.setCellStyle(style);
            col.setCellValue("Requisiciones con orden de compra pendientes por llegar");

            for (int i = -1; i < Tabla1.getRowCount(); i++) {
                Row fila10 = hoja.createRow(i + 5);
                for (int j = 0; j < Tabla1.getColumnCount(); j++) {
                    Cell celda = fila10.createCell(j + 2);
                    String rgbS2 = "006aa2";
                    byte[] rgbB2 = Hex.decodeHex(rgbS2); // get byte array from hex string
                    XSSFColor color2 = new XSSFColor(rgbB2, null);

                    String rgbS3 = "d2efff";
                    byte[] rgbB3 = Hex.decodeHex(rgbS3); // get byte array from hex string
                    XSSFColor color3 = new XSSFColor(rgbB3, null);

                    if (i == -1 && (j >= 0 && j <= Tabla1.getColumnCount())) {
                        XSSFCellStyle s = (XSSFCellStyle) book.createCellStyle();
                        org.apache.poi.ss.usermodel.Font f = book.createFont();

                        f.setBold(true);
                        f.setColor(IndexedColors.WHITE.getIndex());
                        s.setFont(f);
                        s.setFillForegroundColor(color2);
                        s.setFillPattern(SOLID_FOREGROUND);
                        celda.setCellStyle(s);
                    }
                    if (i > -1 && (j > -1 && j <= Tabla1.getColumnCount()) && (i % 2 == 0)) {
                        XSSFCellStyle s = (XSSFCellStyle) book.createCellStyle();
                        s.setFillForegroundColor(color3);
                        s.setFillPattern(SOLID_FOREGROUND);
                        celda.setCellStyle(s);
                    }

                    if (i == -1) {
                        celda.setCellValue(String.valueOf(Tabla1.getColumnName(j)));
//                        CellUtil.setCellStyleProperties(celda, properties);
                    } else {
                        if (j == 3) {
                            XSSFCellStyle ss = (XSSFCellStyle) book.createCellStyle();
                            ss.setWrapText(true);

                            if (i % 2 == 0) {
                                ss.setFillForegroundColor(color3);
                                ss.setFillPattern(SOLID_FOREGROUND);

                            }
                            celda.setCellStyle(ss);
                        }
                        celda.setCellValue(String.valueOf(Tabla1.getValueAt(i, j)));
                        if (j != 4) {
                            hoja.autoSizeColumn(j);
                        }
//                        CellUtil.setCellStyleProperties(celda, properties);

                    }
                    File ad = new File(a);
                    book.write(new FileOutputStream(a));
                }
            }
            try {
                Desktop desktop = Desktop.getDesktop();
                if (desktop.isSupported(Desktop.Action.OPEN) && new File(a).exists()) {
                    desktop.open(new File(a));
                } else {
                    System.out.println("No se puede abrir el archivo automáticamente. Abre manualmente en Excel.");
                    espera.dispose();
                }
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error al tratar de abrir el documento creado", "Error", JOptionPane.ERROR_MESSAGE);
                espera.dispose();
            }
            book.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(CambiarEstado.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(CambiarEstado.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                OCPendientes dialog = new OCPendientes(new java.awt.Frame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
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
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
