package VentanaEmergente.Compras;

import Conexiones.Conexion;
import VentanaEmergente.Prestamo.ColorReclamos;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import org.apache.pdfbox.util.Hex;
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
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import pruebas.CambiarEstado;
import scrollPane.ScrollBarCustom;

public class Reclamos extends javax.swing.JDialog implements ActionListener {

    Fecha fecha;
    int filaFecha;
    int fil;

    public final void limpiarTabla() {
        Tabla1.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        Tabla1.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{},
                new String[]{
                    "ID", "No. Requisicion", "OC", "No. Parte", "Descripcion", "TE", "Proveedor", "Proyecto", "U. Recibida", "U. Pedida", "Diferencia", "Fecha Requi.", "Fecha OC"
                }
        ) {
            boolean[] canEdit = new boolean[]{
                false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
        Tabla1.getTableHeader().setFont(new java.awt.Font("Roboto", java.awt.Font.BOLD, 14));
        Tabla1.getTableHeader().setOpaque(false);
        Tabla1.getTableHeader().setBackground(new Color(0, 78, 171));
        Tabla1.getTableHeader().setForeground(Color.white);
        Tabla1.setRowHeight(25);
        Tabla1.setShowVerticalLines(false);
        Tabla1.setGridColor(new Color(240, 240, 240));

        jScrollPane2.getViewport().setBackground(new Color(255, 255, 255));
        jScrollPane2.setVerticalScrollBar(new ScrollBarCustom(new Color(0, 165, 255)));
        jScrollPane2.setViewportView(Tabla1);
        if (Tabla1.getColumnModel().getColumnCount() > 0) {
            Tabla1.getColumnModel().getColumn(0).setMinWidth(100);
            Tabla1.getColumnModel().getColumn(0).setPreferredWidth(100);
            Tabla1.getColumnModel().getColumn(0).setMaxWidth(100);
        }
    }

    public final void verDatos() {
        limpiarTabla();
        Connection con = null;
        Conexion con1 = new Conexion();
        try {
            con = con1.getConnection();
            Statement st = con.createStatement();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            Date d = new Date();

            String fecha = sdf.format(d);

            java.sql.Date f = new java.sql.Date(sdf.parse(fecha).getTime());

            //String sql = "select * from requisiciones where Llego IS NULL and TE IS NOT NULL and OC != 'CANCELADO' order by TE";
            String sql = "SELECT rqs.Id, rqs.NumRequisicion, rqs.OC, rqs.Codigo, rqs.Descripcion, rqs.TE, rqs.Proveedor, rqs.Proyecto, rqs.CantRecibida, rqs.Cantidad, r.Fecha AS FechaRequisicion, oc.Fecha AS FechaOC FROM requisiciones rqs LEFT JOIN requisicion r ON r.Id = rqs.NumRequisicion LEFT JOIN ordencompra oc ON oc.OrdenNo = rqs.OC WHERE rqs.Llego IS NULL AND rqs.TE IS NOT NULL AND rqs.OC != 'CANCELADO' ORDER BY rqs.TE;";
            ResultSet rs = st.executeQuery(sql);
            java.sql.Date fec;

            DefaultTableModel miModelo = (DefaultTableModel) Tabla1.getModel();

            String datos[] = new String[13];
            while (rs.next()) {
                fec = new java.sql.Date(rs.getDate("TE").getTime());
                long dif = fec.getTime() - f.getTime();
                TimeUnit time = TimeUnit.DAYS;
                long resta = time.convert(dif, TimeUnit.MILLISECONDS);

                if (resta <= 3) {

                    datos[0] = rs.getString("Id");
                    datos[1] = rs.getString("NumRequisicion");
                    datos[2] = rs.getString("OC");
                    datos[3] = rs.getString("Codigo");
                    datos[4] = rs.getString("Descripcion");
                    datos[5] = rs.getString("TE");
                    datos[6] = rs.getString("Proveedor");
                    datos[7] = rs.getString("Proyecto");
                    datos[8] = rs.getString("CantRecibida");
                    datos[9] = rs.getString("Cantidad");
                    datos[10] = ("");
                    datos[11] = rs.getString("FechaRequisicion");
                    datos[12] = rs.getString("FechaOC");
                    String sql2 = "select Progreso, Id from requisicion where Id like '" + datos[1] + "'";
                    Statement st2 = con.createStatement();
                    ResultSet rs2 = st2.executeQuery(sql2);
                    String estado = "";
                    while (rs2.next()) {
                        estado = rs2.getString("Progreso");
                    }
                    if (!estado.equals("CANCELADO")) {
                        miModelo.addRow(datos);
                    }
                }
            }

        } catch (SQLException | ParseException ex) {
            Logger.getLogger(Reclamos.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public final void mostrarTodo() {
        limpiarTabla();
        Connection con;
        Conexion con1 = new Conexion();
        try {
            con = con1.getConnection();
            Statement st = con.createStatement();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            Date d = new Date();

            String fecha = sdf.format(d);

            java.sql.Date f = new java.sql.Date(sdf.parse(fecha).getTime());

            //String sql = "select * from requisiciones where Llego IS NULL and TE IS NOT NULL and OC != 'CANCELADO' order by TE";
            String sql = "SELECT rqs.Id, rqs.NumRequisicion, rqs.OC, rqs.Codigo, rqs.Descripcion, rqs.TE, rqs.Proveedor, rqs.Proyecto, rqs.CantRecibida, rqs.Cantidad, r.Fecha AS FechaRequisicion, oc.Fecha AS FechaOC FROM requisiciones rqs LEFT JOIN requisicion r ON r.Id = rqs.NumRequisicion LEFT JOIN ordencompra oc ON oc.OrdenNo = rqs.OC WHERE rqs.Llego IS NULL AND rqs.TE IS NOT NULL AND rqs.OC != 'CANCELADO' ORDER BY rqs.TE;";
            ResultSet rs = st.executeQuery(sql);
            java.sql.Date fec;

            DefaultTableModel miModelo = (DefaultTableModel) Tabla1.getModel();

            String datos[] = new String[13];
            while (rs.next()) {
                fec = new java.sql.Date(rs.getDate("TE").getTime());
                long dif = fec.getTime() - f.getTime();
                TimeUnit time = TimeUnit.DAYS;
                long resta = time.convert(dif, TimeUnit.MILLISECONDS);

                datos[0] = rs.getString("Id");
                datos[1] = rs.getString("NumRequisicion");
                datos[2] = rs.getString("OC");
                datos[3] = rs.getString("Codigo");
                datos[4] = rs.getString("Descripcion");
                datos[5] = rs.getString("TE");
                datos[6] = rs.getString("Proveedor");
                datos[7] = rs.getString("Proyecto");
                datos[8] = rs.getString("CantRecibida");
                datos[9] = rs.getString("Cantidad");
                datos[10] = String.valueOf(resta);
                datos[11] = rs.getString("FechaRequisicion");
                datos[12] = rs.getString("FechaOC");
                String sql2 = "select Progreso, Id from requisicion where Id like '" + datos[1] + "'";
                Statement st2 = con.createStatement();
                ResultSet rs2 = st2.executeQuery(sql2);
                String estado = "";
                while (rs2.next()) {
                    estado = rs2.getString("Progreso");
                }
                if (!estado.equals("CANCELADO")) {
                    miModelo.addRow(datos);
                }
            }

        } catch (SQLException | ParseException ex) {
            Logger.getLogger(Reclamos.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public Reclamos(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        limpiarTabla();
        verDatos();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        Tabla1 = new ColorReclamos();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        btnRefresh = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        btnEditar = new javax.swing.JToggleButton();
        jPanel8 = new javax.swing.JPanel();
        btnGuardar = new javax.swing.JButton();
        jPanel9 = new javax.swing.JPanel();
        btnExcel = new javax.swing.JButton();
        jPanel10 = new javax.swing.JPanel();
        btnRefresh1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(300, 105));
        setPreferredSize(new java.awt.Dimension(1000, 479));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new java.awt.BorderLayout());

        jPanel6.setLayout(new javax.swing.BoxLayout(jPanel6, javax.swing.BoxLayout.LINE_AXIS));
        jPanel1.add(jPanel6, java.awt.BorderLayout.PAGE_START);

        jScrollPane2.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane2.setBorder(null);

        Tabla1.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        Tabla1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "No. Requisicion", "OC", "No. Parte", "Descripcion", "TE", "Proveedor", "Proyecto", "U. Recibida", "U. Pedida", "Diferencia", "Fecha Requi.", "Fecha OC"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        Tabla1.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                Tabla1MouseDragged(evt);
            }
        });
        Tabla1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Tabla1MouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                Tabla1MousePressed(evt);
            }
        });
        jScrollPane2.setViewportView(Tabla1);
        if (Tabla1.getColumnModel().getColumnCount() > 0) {
            Tabla1.getColumnModel().getColumn(0).setMinWidth(100);
            Tabla1.getColumnModel().getColumn(0).setPreferredWidth(100);
            Tabla1.getColumnModel().getColumn(0).setMaxWidth(100);
        }

        jPanel1.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Bahnschrift", 0, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 102, 255));
        jLabel1.setText("Requisiciones con tiempo de entrega vencido");
        jPanel3.add(jLabel1);

        jPanel2.add(jPanel3);

        getContentPane().add(jPanel2, java.awt.BorderLayout.PAGE_START);

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setLayout(new java.awt.GridLayout(6, 0, 100, 0));
        jPanel4.add(jLabel2);

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));

        btnRefresh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IconoC/refresh_24.png"))); // NOI18N
        btnRefresh.setFocusPainted(false);
        btnRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefreshActionPerformed(evt);
            }
        });
        jPanel5.add(btnRefresh);

        jPanel4.add(jPanel5);

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));

        btnEditar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IconoC/editar_24.png"))); // NOI18N
        btnEditar.setFocusPainted(false);
        btnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarActionPerformed(evt);
            }
        });
        jPanel7.add(btnEditar);

        jPanel4.add(jPanel7);

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));

        btnGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IconoC/Guardar_24.png"))); // NOI18N
        btnGuardar.setFocusPainted(false);
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });
        jPanel8.add(btnGuardar);

        jPanel4.add(jPanel8);

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));

        btnExcel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IconoC/excel_24.png"))); // NOI18N
        btnExcel.setFocusPainted(false);
        btnExcel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExcelActionPerformed(evt);
            }
        });
        jPanel9.add(btnExcel);

        jPanel4.add(jPanel9);

        jPanel10.setBackground(new java.awt.Color(255, 255, 255));

        btnRefresh1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/ver_32.png"))); // NOI18N
        btnRefresh1.setFocusPainted(false);
        btnRefresh1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefresh1ActionPerformed(evt);
            }
        });
        jPanel10.add(btnRefresh1);

        jPanel4.add(jPanel10);

        getContentPane().add(jPanel4, java.awt.BorderLayout.LINE_START);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void Tabla1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Tabla1MouseClicked
        if (btnEditar.isSelected()) {
            if (Tabla1.getSelectedColumn() == 5) {
                JFrame f = (JFrame) JOptionPane.getFrameForComponent(this);
                fecha = new Fecha(f, true);
                filaFecha = Tabla1.getSelectedRow();
                fecha.btnGuardar.addActionListener(this);
                fecha.setVisible(true);
            }
        }
    }//GEN-LAST:event_Tabla1MouseClicked

    private void Tabla1MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Tabla1MouseDragged
        if (btnEditar.isSelected()) {
            if (Tabla1.getSelectedColumn() == 5) {
                if (Tabla1.getSelectedColumn() == 5) {
                    for (int i = 0; i < Tabla1.getSelectedRows().length; i++) {
                        Tabla1.setValueAt(Tabla1.getValueAt(fil, 5), Tabla1.getSelectedRows()[i], 5);
                    }
                }
            }
        }
    }//GEN-LAST:event_Tabla1MouseDragged

    private void Tabla1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Tabla1MousePressed
        fil = Tabla1.getSelectedRow();
    }//GEN-LAST:event_Tabla1MousePressed

    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshActionPerformed
        limpiarTabla();
        verDatos();
    }//GEN-LAST:event_btnRefreshActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        try {
            Connection con = null;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            int n = 0;
            for (int i = 0; i < Tabla1.getRowCount(); i++) {
                String sql = "update requisiciones set TE = ? where Id = ?";
                PreparedStatement pst = con.prepareStatement(sql);

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                java.sql.Date date = new java.sql.Date(sdf.parse(Tabla1.getValueAt(i, 5).toString()).getTime());
                pst.setDate(1, date);
                pst.setString(2, Tabla1.getValueAt(i, 0).toString());

                n = pst.executeUpdate();
            }

            if (n > 0) {
                JOptionPane.showMessageDialog(this, "DATOS GUARDADOS");
                limpiarTabla();
                verDatos();
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "ERROR: " + e, "ERROR", JOptionPane.ERROR_MESSAGE);
        } catch (ParseException ex) {
            Logger.getLogger(Reclamos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        limpiarTabla();
        verDatos();
    }//GEN-LAST:event_btnEditarActionPerformed

    private void btnExcelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcelActionPerformed
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
            hoja.setColumnWidth(2, 4000);
            hoja.setColumnWidth(3, 6500);
            hoja.setColumnWidth(4, 6500);
            hoja.setColumnWidth(5, 8200);
            hoja.setColumnWidth(6, 9200);
            hoja.setColumnWidth(7, 6000);
            hoja.setColumnWidth(13, 4000);
            hoja.setColumnWidth(14, 4000);
            
            Font font1 = book.createFont();
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
                    14
            ));

            col.setCellStyle(style);
            col.setCellValue("REQUISICIONES CON TE VENCIDO");

            for (int i = -1; i < Tabla1.getRowCount(); i++) {
                Row fila10 = hoja.createRow(i + 5);
                for (int j = 0; j < 13; j++) {
                    Cell celda = fila10.createCell(j + 2);
                    String rgbS2 = "006aa2";
                    byte[] rgbB2 = Hex.decodeHex(rgbS2); // get byte array from hex string
                    XSSFColor color2 = new XSSFColor(rgbB2, null);

                    String rgbS3 = "d2efff";
                    byte[] rgbB3 = Hex.decodeHex(rgbS3); // get byte array from hex string
                    XSSFColor color3 = new XSSFColor(rgbB3, null);

                    if (i == -1 && (j >= 0 && j <= 12)) {
                        XSSFCellStyle s = (XSSFCellStyle) book.createCellStyle();
                        Font f = book.createFont();

                        f.setBold(true);
                        f.setColor(IndexedColors.WHITE.getIndex());
                        s.setFont(f);
                        s.setFillForegroundColor(color2);
                        s.setFillPattern(SOLID_FOREGROUND);
                        celda.setCellStyle(s);
                    }
                    if (i > -1 && (j > -1 && j <= 12) && (i % 2 == 0)) {
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
//                        CellUtil.setCellStyleProperties(celda, properties);

                    }
                }
            }
            try (FileOutputStream fos = new FileOutputStream(a)) {
                book.write(fos);
            }
            book.close();
            Desktop.getDesktop().open(new File(a));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(CambiarEstado.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(CambiarEstado.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnExcelActionPerformed

    private void btnRefresh1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefresh1ActionPerformed
        limpiarTabla();
        mostrarTodo();
    }//GEN-LAST:event_btnRefresh1ActionPerformed

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
            java.util.logging.Logger.getLogger(Reclamos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Reclamos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Reclamos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Reclamos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Reclamos dialog = new Reclamos(new javax.swing.JFrame(), true);
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
    public javax.swing.JToggleButton btnEditar;
    private javax.swing.JButton btnExcel;
    public javax.swing.JButton btnGuardar;
    public javax.swing.JButton btnRefresh;
    public javax.swing.JButton btnRefresh1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane2;
    // End of variables declaration//GEN-END:variables

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            if (fecha != null) {
                if (e.getSource() == fecha.btnGuardar) {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    String fechaa = sdf.format(this.fecha.calendario.getDate());
                    Tabla1.setValueAt(fechaa, Tabla1.getSelectedRow(), 5);
                    fecha.dispose();
                }
            }
        } catch (Exception a) {

        }
    }
}
