package pruebas;

import Conexiones.Conexion;
import VentanaEmergente.CotizacionVentas.AgregarCotizacion;
import VentanaEmergente.CotizacionVentas.ColorCoti;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.Font;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import static org.apache.poi.ss.usermodel.FillPatternType.SOLID_FOREGROUND;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellUtil;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class CotizacionVentas extends javax.swing.JInternalFrame {

    public String numEmpleado;

    public final void limpiarTabla() {

        Tabla1 = new ColorCoti();
        Tabla1.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{},
                new String[]{
                    "Sol. Cotizacion", "Fecha", "Vendedor", "Cliente", "Requisitor", "Descripcion", "Estado"
                }
        ) {
            boolean[] canEdit = new boolean[]{
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });

        Tabla1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Tabla1MouseClicked(evt);
            }
        });

        Tabla1.getTableHeader().setFont(new Font("Bahnschrift", Font.BOLD, 14));
        Tabla1.getTableHeader().setOpaque(false);
        Tabla1.getTableHeader().setBackground(new Color(0, 78, 171));
        Tabla1.getTableHeader().setForeground(Color.white);
        Tabla1.setRowHeight(25);
        Tabla1.setShowHorizontalLines(true);

        jScrollPane1.setViewportView(Tabla1);
    }

    public final void verDatos() {
        try {
            limpiarTabla();
            Connection con = new Conexion().getConnection();
            Statement st = con.createStatement();
            String sql = "select idcotizacion, fecha, requisitor, descripcion, estado, reg.Nombre as nom, Apellido, cli.Nombre "
                    + "from cotizacion as cot "
                    + "inner join registroempleados as reg on cot.NumEmpleado = reg.NumEmpleado "
                    + "inner join clientes as cli on cot.idcliente = cli.idclientes "
                    + "order by idcotizacion desc";
            ResultSet rs = st.executeQuery(sql);
            String datos[] = new String[9];
            DefaultTableModel miModelo = (DefaultTableModel) Tabla1.getModel();
            while (rs.next()) {
                datos[0] = rs.getString("idcotizacion");
                datos[1] = rs.getString("fecha");
                datos[2] = rs.getString("nom") + " " + rs.getString("Apellido");
                datos[3] = rs.getString("nombre");
                datos[4] = rs.getString("requisitor");
                datos[5] = rs.getString("descripcion");
                datos[6] = rs.getString("estado");
                miModelo.addRow(datos);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error: " + e, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public boolean verificarCotizacion(String id, String empleado) {
        try {
            Connection con = new Conexion().getConnection();
            Statement st = con.createStatement();
            String sql = "select * from cotizacion where idcotizacion like '" + id + "' and NumEmpleado like '" + empleado + "'";
            ResultSet rs = st.executeQuery(sql);
            return rs.next();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error: " + e, "Error", JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }

    public final void crearExcel() {
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

            Sheet hoja = book.createSheet("Reporte solicitud de cotizacion");
            Row fila = hoja.createRow(2);
            Cell col = fila.createCell(2);

            Row fila1 = hoja.createRow(4);
            Cell col1 = fila1.createCell(2);

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
            hoja.setColumnWidth(0, 1500);
            hoja.setColumnWidth(1, 1500);
            hoja.setColumnWidth(2, 4000);
            hoja.setColumnWidth(3, 6500);
            hoja.setColumnWidth(4, 6500);
            hoja.setColumnWidth(5, 8200);
            hoja.setColumnWidth(6, 4000);
            hoja.setColumnWidth(7, 15000);
            hoja.setColumnWidth(8, 3000);

//            org.apache.poi.ss.usermodel.Font font1 = book.createFont();
            XSSFCellStyle style = (XSSFCellStyle) book.createCellStyle();
            style.setAlignment(HorizontalAlignment.CENTER);
            XSSFColor xssfColor = new XSSFColor(
                    new byte[]{(byte) 0, (byte) 70, (byte) 171},
                    null
            );
            org.apache.poi.ss.usermodel.Font f = book.createFont();
            f.setBold(true);
            f.setColor(IndexedColors.WHITE.getIndex());
            style.setFont(f);
            style.setFillForegroundColor(xssfColor);
            style.setFillPattern(SOLID_FOREGROUND);

            hoja.addMergedRegion(new CellRangeAddress(
                    2,
                    2,
                    2,
                    8
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
            col.setCellValue("Solicitudes de cotizacion");

            for (int i = -1; i < Tabla1.getRowCount(); i++) {
                Row fila10 = hoja.createRow(i + 5);
                for (int j = 0; j < 7; j++) {
                    Cell celda = fila10.createCell(j + 2);
                    if (i == -1 && (j >= 0 && j <= 7)) {
                        celda.setCellStyle(style);
                    }

                    if (i == -1) {
                        celda.setCellValue(String.valueOf(Tabla1.getColumnName(j)));
                    } else {
                        if (j == 3) {
                            CellStyle ss = book.createCellStyle();
                            ss.setWrapText(true);

                            celda.setCellStyle(ss);
                        }
                        if (j == 6) {
                            Color color;
                            String cel = Tabla1.getValueAt(i, j).toString();
                            switch (cel) {
                                case "Borrador":
                                    color = Color.decode("#9F00FF");
                                    break;
                                case "Enviado":
                                    color = Color.decode("#475FFF");
                                    break;
                                case "Vendido":
                                    color = Color.decode("#20FF49");
                                    break;
                                case "Cancelado":
                                    color = Color.decode("#FF4C4C");
                                    break;
                                default:
                                    color = Color.decode("#fffff");
                            }

                            XSSFColor xssfColor1 = new XSSFColor(
                                    new byte[]{(byte) color.getRed(), (byte) color.getGreen(), (byte) color.getBlue()},
                                    null
                            );

                            XSSFCellStyle ss = (XSSFCellStyle) book.createCellStyle();
                            ss.setFillForegroundColor(xssfColor1);
                            ss.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                            org.apache.poi.ss.usermodel.Font fo = book.createFont();
                            fo.setBold(true);
                            fo.setColor(IndexedColors.WHITE.getIndex());
                            ss.setFont(fo);

                            celda.setCellStyle(ss);
                        }
                        celda.setCellValue(String.valueOf(Tabla1.getValueAt(i, j)));
                    }
                    book.write(new FileOutputStream(a));
                }
            }
            book.close();
            Desktop.getDesktop().open(new File(a));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(CambiarEstado.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(CambiarEstado.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public final void buscar(String texto) {
        try {
            limpiarTabla();
            Connection con = new Conexion().getConnection();
            Statement st = con.createStatement();
            String sql = "select idcotizacion, fecha, Requisitor, cot.NumEmpleado, descripcion, Estado, emp.Nombre, Apellido, cli.Nombre as nom from cotizacion as cot "
                    + "inner join registroempleados as emp on emp.NumEmpleado = cot.NumEmpleado "
                    + "inner join clientes as cli on cot.idcliente = cli.idclientes "
                    + "where requisitor like '%" + texto + "%' "
                    + "or descripcion like '%" + texto + "%'  "
                    + "or estado like '%" + texto + "%'  "
                    + "or cot.NumEmpleado like '%" + texto + "%'"
                    + "or cli.Nombre like '%" + texto + "%'";
            ResultSet rs = st.executeQuery(sql);
            String datos[] = new String[9];
            DefaultTableModel miModelo = (DefaultTableModel) Tabla1.getModel();
            while (rs.next()) {
                datos[0] = rs.getString("idcotizacion");
                datos[1] = rs.getString("fecha");
                datos[2] = rs.getString("nom");
                datos[3] = rs.getString("nombre") + " " + rs.getString("Apellido");
                datos[4] = rs.getString("requisitor");
                datos[5] = rs.getString("descripcion");
                datos[6] = rs.getString("estado");
                miModelo.addRow(datos);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error: " + e, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public CotizacionVentas(String numEmpleado) {
        initComponents();
        this.numEmpleado = numEmpleado;
        verDatos();
        ((javax.swing.plaf.basic.BasicInternalFrameUI) this.getUI()).setNorthPane(null);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel1 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        pan = new javax.swing.JPanel();
        panelSalir = new javax.swing.JPanel();
        lblSalir = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        Tabla1 = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();

        setBorder(null);
        getContentPane().setLayout(new java.awt.BorderLayout(10, 10));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new java.awt.BorderLayout(10, 10));

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setLayout(new java.awt.BorderLayout());

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));

        jLabel18.setFont(new java.awt.Font("Lexend", 1, 24)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(0, 153, 255));
        jLabel18.setText("Cotizaciones");
        jPanel9.add(jLabel18);

        jPanel8.add(jPanel9);

        jPanel7.add(jPanel8, java.awt.BorderLayout.CENTER);

        pan.setBackground(new java.awt.Color(255, 255, 255));

        panelSalir.setBackground(new java.awt.Color(255, 255, 255));

        lblSalir.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        lblSalir.setForeground(new java.awt.Color(0, 0, 0));
        lblSalir.setText(" X ");
        lblSalir.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblSalir.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblSalirMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblSalirMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblSalirMouseExited(evt);
            }
        });
        panelSalir.add(lblSalir);

        pan.add(panelSalir);

        jPanel7.add(pan, java.awt.BorderLayout.EAST);

        jPanel1.add(jPanel7, java.awt.BorderLayout.PAGE_START);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new java.awt.BorderLayout());

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setLayout(new java.awt.GridBagLayout());

        jLabel1.setFont(new java.awt.Font("Bahnschrift", 1, 14)); // NOI18N
        jLabel1.setText("Opciones");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        jPanel3.add(jLabel1, gridBagConstraints);

        jButton1.setBackground(new java.awt.Color(255, 255, 255));
        jButton1.setFont(new java.awt.Font("Bahnschrift", 1, 14)); // NOI18N
        jButton1.setForeground(new java.awt.Color(0, 153, 0));
        jButton1.setText("Agregar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.ipadx = 29;
        gridBagConstraints.ipady = 7;
        gridBagConstraints.insets = new java.awt.Insets(9, 12, 9, 12);
        jPanel3.add(jButton1, gridBagConstraints);

        jButton3.setBackground(new java.awt.Color(255, 255, 255));
        jButton3.setFont(new java.awt.Font("Bahnschrift", 1, 14)); // NOI18N
        jButton3.setForeground(new java.awt.Color(0, 51, 204));
        jButton3.setText("Recargar");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.ipadx = 29;
        gridBagConstraints.ipady = 7;
        gridBagConstraints.insets = new java.awt.Insets(9, 12, 9, 12);
        jPanel3.add(jButton3, gridBagConstraints);

        jButton4.setBackground(new java.awt.Color(255, 255, 255));
        jButton4.setFont(new java.awt.Font("Bahnschrift", 1, 14)); // NOI18N
        jButton4.setForeground(new java.awt.Color(0, 102, 0));
        jButton4.setText("<html><p>Descargar</o><p style=\"text-align:center;\">Excel<p/>");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.ipadx = 29;
        gridBagConstraints.ipady = 7;
        gridBagConstraints.insets = new java.awt.Insets(9, 12, 9, 12);
        jPanel3.add(jButton4, gridBagConstraints);

        jPanel2.add(jPanel3, java.awt.BorderLayout.LINE_START);

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setLayout(new java.awt.BorderLayout());

        Tabla1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Sol. Cotizacion", "Fecha", "Vendedor", "Cliente", "Requisitor", "Descripcion", "Estado"
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

        jPanel5.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        java.awt.GridBagLayout jPanel4Layout = new java.awt.GridBagLayout();
        jPanel4Layout.columnWeights = new double[] {0.0, 0.0, 1.0};
        jPanel4.setLayout(jPanel4Layout);

        jLabel2.setFont(new java.awt.Font("Bahnschrift", 1, 12)); // NOI18N
        jLabel2.setText("Buscar");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.BASELINE_LEADING;
        gridBagConstraints.insets = new java.awt.Insets(0, 30, 0, 5);
        jPanel4.add(jLabel2, gridBagConstraints);

        jTextField1.setBackground(new java.awt.Color(255, 255, 255));
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.ipadx = 300;
        gridBagConstraints.ipady = 10;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.BASELINE;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 10);
        jPanel4.add(jTextField1, gridBagConstraints);

        jLabel3.setText(" ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        jPanel4.add(jLabel3, gridBagConstraints);

        jPanel5.add(jPanel4, java.awt.BorderLayout.PAGE_START);

        jPanel2.add(jPanel5, java.awt.BorderLayout.CENTER);

        jPanel1.add(jPanel2, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void lblSalirMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSalirMouseExited
        panelSalir.setBackground(Color.white);
        lblSalir.setForeground(Color.black);
    }//GEN-LAST:event_lblSalirMouseExited

    private void lblSalirMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSalirMouseEntered
        panelSalir.setBackground(Color.red);
        lblSalir.setForeground(Color.white);
    }//GEN-LAST:event_lblSalirMouseEntered

    private void lblSalirMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSalirMouseClicked
        dispose();
    }//GEN-LAST:event_lblSalirMouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        JFrame f = (JFrame) JOptionPane.getFrameForComponent(this);
        AgregarCotizacion add = new AgregarCotizacion(f, true, this.numEmpleado);
        add.setPreferredSize(new Dimension(1147, 692));
        add.setLocationRelativeTo(f);
        add.setVisible(true);
        verDatos();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        verDatos();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void Tabla1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Tabla1MouseClicked
        JFrame f = (JFrame) JOptionPane.getFrameForComponent(this);
        AgregarCotizacion add = new AgregarCotizacion(f, true, this.numEmpleado);
        add.setPreferredSize(new Dimension(1147, 692));
        add.setLocationRelativeTo(f);
        int fila = Tabla1.getSelectedRow();
        boolean ver = verificarCotizacion(Tabla1.getValueAt(fila, 0).toString(), numEmpleado);
        add.txtCliente.setText(Tabla1.getValueAt(fila, 3).toString());
        add.txtCotizacion.setText(Tabla1.getValueAt(fila, 0).toString());
        add.txtDescripcion.setText(Tabla1.getValueAt(fila, 5).toString());
        add.txtProyecto.setText(Tabla1.getValueAt(fila, 4).toString());
        add.txtVendedor.setText(Tabla1.getValueAt(fila, 2).toString());
        add.cmbEstado.setSelectedItem(Tabla1.getValueAt(fila, 6).toString());
        if (!ver) {
            add.txtCliente.setEditable(false);
            add.txtCotizacion.setEditable(false);
            add.txtDescripcion.setEditable(false);
            add.txtProyecto.setEditable(false);
            add.txtVendedor.setEditable(false);
            add.cmbEstado.setEnabled(false);
            add.btnGuardar.setEnabled(false);
        }
        add.setVisible(true);
        verDatos();

    }//GEN-LAST:event_Tabla1MouseClicked

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        crearExcel();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        buscar(jTextField1.getText());
    }//GEN-LAST:event_jTextField1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable Tabla1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JLabel lblSalir;
    private javax.swing.JPanel pan;
    private javax.swing.JPanel panelSalir;
    // End of variables declaration//GEN-END:variables
}
