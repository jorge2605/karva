package pruebas;

import Conexiones.Conexion;
import VentanaEmergente.Reportes.Plano;
import VentanaEmergente.Reportes.ReporteHerramienta;
import VentanaEmergente.Reportes.ReporteHoras;
import VentanaEmergente.Reportes.ReporteMensual;
import VentanaEmergente.Reportes.ReporteScrap;
import com.mxrck.autocompleter.TextAutoCompleter;
import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellUtil;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import scrollPane.ScrollBarCustom;

public class Reportes extends javax.swing.JInternalFrame {

    private TextAutoCompleter ac;
    String numEmpleado;

    public void limpiarTabla() {
        Tabla1.setFont(new java.awt.Font("Arial", 0, 12));
        Tabla1.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{},
                new String[]{
                    "Proyecto", "Plano", "Cantidad", "Tipo de material", "Tiempo", "Realizado por", "Fecha"
                }
        ) {
            boolean[] canEdit = new boolean[]{
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
        Tabla1.setRowHeight(25);
    }

    public void verTerminados(String ubi, String bd) {
        try {

            SimpleDateFormat nuevo = new SimpleDateFormat("dd/MM/yyyy");
            String fec = nuevo.format(calen.getDate());
            int diaSig = Integer.parseInt(fec.substring(0, 2));
            diaSig += 1;
            String dia = diaSig + "/" + fec.substring(3, fec.length());
            Date da = nuevo.parse(dia);
            String fecSig = nuevo.format(da);
            lblUbi.setText(ubi);
            limpiarTabla();
            DefaultTableModel miModelo = (DefaultTableModel) Tabla1.getModel();
            Connection con = null;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st1 = con.createStatement();
            Statement st2 = con.createStatement();
            Statement st3 = con.createStatement();
            String datos[] = new String[10];
            String datos1[] = new String[10];

            if (btnEstacion.isSelected()) {
                String sql2 = "select * from " + bd + " where Terminado like 'NO'";
                ResultSet rs2 = st2.executeQuery(sql2);
                while (rs2.next()) {
                    datos1[4] = rs2.getString("FechaFinal");
                    datos[0] = rs2.getString("Plano");
                    datos[1] = rs2.getString("Proyecto");
                    String sql4 = "select Plano, Cantidad, Material from planos where Plano like '" + datos[1] + "'";
                    String cantidad = "", tipo = "";
                    Statement st5 = con.createStatement();
                    ResultSet rs5 = st5.executeQuery(sql4);
                    while (rs5.next()) {
                        cantidad = rs5.getString("Cantidad");
                        tipo = rs5.getString("Material");
                    }
                    datos[2] = cantidad;
                    datos[3] = tipo;
                    datos[4] = "0";
                    datos[5] = rs2.getString("Cronometro");
                    datos[6] = rs2.getString("Empleado");

                    miModelo.addRow(datos);
                }
            } else if (btnCurso.isSelected()) {
                String sql3 = "select * from " + bd + " where FechaInicio != '' and FechaFinal like '' and Terminado like 'NO'";
                ResultSet rs3 = st3.executeQuery(sql3);
                while (rs3.next()) {
                    datos1[4] = rs3.getString("FechaFinal");
                    datos[0] = rs3.getString("Plano");
                    datos[1] = rs3.getString("Proyecto");
                    String sql4 = "select Plano, Cantidad, Material from planos where Plano like '" + datos[1] + "'";
                    String cantidad = "", tipo = "";
                    Statement st5 = con.createStatement();
                    ResultSet rs5 = st5.executeQuery(sql4);
                    while (rs5.next()) {
                        cantidad = rs5.getString("Cantidad");
                        tipo = rs5.getString("Material");
                    }
                    datos[2] = cantidad;
                    datos[3] = tipo;
                    datos[4] = rs3.getString("Cronometro");
                    datos[5] = rs3.getString("Empleado");
                    miModelo.addRow(datos);
                }
            } else if (btnTerminados.isSelected()) {
                String sql3 = "select Terminado, FechaFinal, Plano, Proyecto,"
                        + "Cronometro, Empleado from " + bd + " where Terminado like 'SI' and FechaFinal != ''";
                ResultSet rs3 = st3.executeQuery(sql3);
                while (rs3.next()) {
                    datos1[4] = rs3.getString("FechaFinal");
                    boolean turno = false;
                    if (turno1.isSelected()) {
                        turno = true;
                    }
                    boolean sen1 = false;

                    String s = datos1[4].substring(0, 10);
                    int hor = 40;
                    try {
                        hor = Integer.parseInt(datos1[4].substring(11, 13));
                    } catch (Exception e) {
//                        System.out.println("Exception: "+e);
                    }
                    if (turno) {
                        if ((fec.equals(s) && (hor >= 6 && hor <= 16))) {
                            sen1 = true;
                        }
                    } else {
                        if ((fec.equals(s) && (hor >= 16)) || (fecSig.equals(s)) && (hor >= 16 || hor <= 5)) {
                            sen1 = true;
                        }
                    }

                    if (sen1) {

                        datos[0] = rs3.getString("Plano");
                        datos[1] = rs3.getString("Proyecto");
                        String sql4 = "select Plano, Cantidad, Material from planos where Plano like '" + datos[1] + "'";
                        String cantidad = "", tipo = "";
                        Statement st5 = con.createStatement();
                        ResultSet rs5 = st5.executeQuery(sql4);
                        while (rs5.next()) {
                            cantidad = rs5.getString("Cantidad");
                            tipo = rs5.getString("Material");
                        }
                        datos[2] = cantidad;
                        datos[3] = tipo;
                        datos[4] = rs3.getString("Cronometro");
                        datos[5] = rs3.getString("Empleado");
                        datos[6] = rs3.getString("FechaFinal");
                        miModelo.addRow(datos);
                    }
                }
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "ERROR AL VER BASE DE DATOS" + e);
        } catch (ParseException ex) {
            Logger.getLogger(Reportes.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void terminarPlano(String planoa, String numEmpleado) {
        try {
            Connection con;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            Statement st3 = con.createStatement();
            Statement st5 = con.createStatement();
            Statement st7 = con.createStatement();
            Statement st9 = con.createStatement();
            Statement st11 = con.createStatement();
            Statement st13 = con.createStatement();

            String sql = "select Plano, Proyecto, Terminado from datos where Proyecto like '" + planoa + "' and Terminado like 'NO'";
            ResultSet rs = st.executeQuery(sql);
            String plano;
            String sql2 = "update datos set FechaInicio = ?, FechaFinal = ?, Terminado = ?, Empleado = ? where Proyecto = ?";
            PreparedStatement pst = con.prepareStatement(sql2);
            Date d = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat();
            String fecha = sdf.format(d);
            int n = 0;
            while (rs.next()) {
                plano = rs.getString("Proyecto");
                pst.setString(1, fecha);
                pst.setString(2, fecha);
                pst.setString(3, "SI");
                pst.setString(4, numEmpleado + "," + numEmpleado);
                pst.setString(5, plano);

                n = pst.executeUpdate();
            }

            String sql3 = "select Plano, Proyecto, Terminado from acabados where Proyecto like '" + planoa + "' and Terminado like 'NO'";
            ResultSet rs3 = st3.executeQuery(sql3);
            String sql4 = "update acabados set FechaInicio = ?, FechaFinal = ?, Terminado = ?, Empleado = ? where Proyecto = ?";
            PreparedStatement pst4 = con.prepareStatement(sql4);
            int n1 = 0;
            while (rs3.next()) {
                plano = rs3.getString("Proyecto");
                pst4.setString(1, fecha);
                pst4.setString(2, fecha);
                pst4.setString(3, "SI");
                pst4.setString(4, numEmpleado + "," + numEmpleado);
                pst4.setString(5, plano);

                n1 = pst4.executeUpdate();
            }

            String sql5 = "select Plano, Proyecto, Terminado from calidad where Proyecto like '" + planoa + "' and Terminado like 'NO'";
            ResultSet rs5 = st5.executeQuery(sql5);
            String sql6 = "update calidad set FechaInicio = ?, FechaFinal = ?, Terminado = ?, Empleado = ?, Tratamiento = ? where Proyecto = ?";
            PreparedStatement pst6 = con.prepareStatement(sql6);
            int n2 = 0;
            while (rs5.next()) {
                plano = rs5.getString("Proyecto");
                pst6.setString(1, fecha);
                pst6.setString(2, fecha);
                pst6.setString(3, "SI");
                pst6.setString(4, numEmpleado + "," + numEmpleado);
                pst6.setString(5, "NO");
                pst6.setString(6, plano);

                n2 = pst6.executeUpdate();
            }

            String sql7 = "select Plano, Proyecto, Terminado from cnc where Proyecto like '" + planoa + "' and Terminado like 'NO'";
            ResultSet rs7 = st7.executeQuery(sql7);
            String sql8 = "update cnc set FechaInicio = ?, FechaFinal = ?, Terminado = ?, Empleado = ? where Proyecto = ?";
            PreparedStatement pst8 = con.prepareStatement(sql8);
            int n3 = 0;
            while (rs7.next()) {
                plano = rs7.getString("Proyecto");
                pst8.setString(1, fecha);
                pst8.setString(2, fecha);
                pst8.setString(3, "SI");
                pst8.setString(4, numEmpleado + "," + numEmpleado);
                pst8.setString(5, plano);

                n3 = pst8.executeUpdate();
            }

            String sql9 = "select Plano, Proyecto, Terminado from fresadora where Proyecto like '" + planoa + "' and Terminado like 'NO'";
            ResultSet rs9 = st9.executeQuery(sql9);
            String sql10 = "update fresadora set FechaInicio = ?, FechaFinal = ?, Terminado = ?, Empleado = ? where Proyecto = ?";
            PreparedStatement pst10 = con.prepareStatement(sql10);
            int n4 = 0;
            while (rs9.next()) {
                plano = rs9.getString("Proyecto");
                pst10.setString(1, fecha);
                pst10.setString(2, fecha);
                pst10.setString(3, "SI");
                pst10.setString(4, numEmpleado + "," + numEmpleado);
                pst10.setString(5, plano);

                n4 = pst10.executeUpdate();
            }

            String sql11 = "select Plano, Proyecto, Terminado from torno where Proyecto like '" + planoa + "' and Terminado like 'NO'";
            ResultSet rs11 = st11.executeQuery(sql11);
            String sql12 = "update torno set FechaInicio = ?, FechaFinal = ?, Terminado = ?, Empleado = ? where Proyecto = ?";
            PreparedStatement pst12 = con.prepareStatement(sql12);
            int n5 = 0;
            while (rs11.next()) {
                plano = rs11.getString("Proyecto");
                pst12.setString(1, fecha);
                pst12.setString(2, fecha);
                pst12.setString(3, "SI");
                pst12.setString(4, numEmpleado + "," + numEmpleado);
                pst12.setString(5, plano);

                n5 = pst12.executeUpdate();
            }

            String sql13 = "select Plano, Proyecto, Prioridad from planos where Plano like '" + planoa + "'";
            ResultSet rs13 = st13.executeQuery(sql13);
            String sql14 = "insert into calidad (Proyecto, Plano, FechaInicio, FechaFinal, Terminado,"
                    + "Estado, Tratamiento, Cronometro, Prioridad, Empleado) values(?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement pst14 = con.prepareStatement(sql14);
            String pl = null;
            String pr = null;
            String pri = null;
            int n6 = 0;
            while (rs13.next()) {
                pl = rs13.getString("Plano");
                pr = rs13.getString("Proyecto");
                pri = rs13.getString("Prioridad");
                String sql15 = "select Proyecto from calidad where Proyecto like '" + pl + "'";
                Statement st2 = con.createStatement();
                ResultSet rs2 = st2.executeQuery(sql15);
                String exis = null;
                while (rs2.next()) {
                    exis = rs2.getString("Proyecto");
                }
                if (exis == null) {
                    pst14.setString(1, pl);
                    pst14.setString(2, pr);
                    pst14.setString(3, fecha);
                    pst14.setString(4, fecha);
                    pst14.setString(5, "SI");
                    pst14.setString(6, "");
                    pst14.setString(7, "NO");
                    pst14.setString(8, "00:00");
                    pst14.setString(9, pri);
                    pst14.setString(10, numEmpleado + "," + numEmpleado);

                    n6 = pst14.executeUpdate();
                }
            }

            if (n > 0 && n1 > 0 && n2 > 0 && n3 > 0 && n4 > 0 && n5 > 0) {
                JOptionPane.showMessageDialog(this, "DATOS GUARDADOS");
            } else {
                if (n6 > 0) {
                    String faltantes = "";
                    if (n < 1) {
                        faltantes += "\nCortes";
                    }
                    if (n1 < 1) {
                        faltantes += "\nAcabados";
                    }
                    if (n2 < 1) {
                        faltantes += "\nCalidad";
                    }
                    if (n3 < 1) {
                        faltantes += "\nCnc";
                    }
                    if (n4 < 1) {
                        faltantes += "\nFresadora";
                    }
                    if (n5 < 1) {
                        faltantes += "\nTorno";
                    }

                    JOptionPane.showMessageDialog(this, "DATOS GUARDADOS, SIN CAMBIOS EN: "
                            + faltantes);
                } else {
                    JOptionPane.showMessageDialog(this, "DATOS GUARDADOS");
                }
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "ERROR: " + e, "ERROR", JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(CambiarEstado.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public Reportes(String numEmpleado) {
        initComponents();
        ((javax.swing.plaf.basic.BasicInternalFrameUI) this.getUI()).setNorthPane(null);
        Tabla1.setShowVerticalLines(false);
        Tabla1.setShowHorizontalLines(false);
        Tabla1.getTableHeader().setOpaque(false);
        Tabla1.getTableHeader().setBackground(new Color(0, 78, 171));
        Tabla1.getTableHeader().setForeground(Color.white);
        Tabla1.getTableHeader().setFont(new Font("Roboto", Font.BOLD, 12));
        Tabla1.setRowHeight(25);
        jScrollPane1.getViewport().setBackground(new Color(255, 255, 255));
        jScrollPane1.setVerticalScrollBar(new ScrollBarCustom(new Color(0, 165, 255)));
        Date fe = new Date();
        calen.setDate(fe);
        this.numEmpleado = numEmpleado;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPopupMenu1 = new javax.swing.JPopupMenu();
        TerminarPlanos = new javax.swing.JMenuItem();
        jPanel1 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        Tabla1 = new javax.swing.JTable();
        jPanel7 = new javax.swing.JPanel();
        lblEstado = new javax.swing.JLabel();
        lblUbi = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        btnReportes = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        btnBuscar = new javax.swing.JButton();
        jPanel12 = new javax.swing.JPanel();
        turno1 = new javax.swing.JRadioButton();
        turno2 = new javax.swing.JRadioButton();
        jPanel14 = new javax.swing.JPanel();
        calen = new com.toedter.calendar.JDateChooser();
        jLabel5 = new javax.swing.JLabel();
        txtProyecto = new javax.swing.JTextField();
        jPanel10 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        btnTerminados = new javax.swing.JToggleButton();
        btnCurso = new javax.swing.JToggleButton();
        btnEstacion = new javax.swing.JToggleButton();
        btnCortes = new javax.swing.JButton();
        txtCortes = new javax.swing.JTextField();
        txtCortes1 = new javax.swing.JTextField();
        txtCortes2 = new javax.swing.JTextField();
        btnFresa = new javax.swing.JButton();
        txtFresa = new javax.swing.JTextField();
        txtFresa1 = new javax.swing.JTextField();
        txtFresa2 = new javax.swing.JTextField();
        btnCnc = new javax.swing.JButton();
        txtCnc = new javax.swing.JTextField();
        txtCnc1 = new javax.swing.JTextField();
        txtCnc2 = new javax.swing.JTextField();
        btnTorno = new javax.swing.JButton();
        txtTorno = new javax.swing.JTextField();
        txtTorno1 = new javax.swing.JTextField();
        txtTorno2 = new javax.swing.JTextField();
        btnAcabados = new javax.swing.JButton();
        txtAcabados = new javax.swing.JTextField();
        txtAcabados1 = new javax.swing.JTextField();
        txtAcabados2 = new javax.swing.JTextField();
        jPanel11 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        btnSalir = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenuItem5 = new javax.swing.JMenuItem();
        jMenuItem6 = new javax.swing.JMenuItem();

        TerminarPlanos.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        TerminarPlanos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/eliminar (1).png"))); // NOI18N
        TerminarPlanos.setText("        Terminar Plano(s)             ");
        TerminarPlanos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TerminarPlanosActionPerformed(evt);
            }
        });
        jPopupMenu1.add(TerminarPlanos);

        setBorder(null);
        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new java.awt.BorderLayout(15, 15));

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setLayout(new java.awt.GridLayout(1, 2, 20, 0));

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setLayout(new java.awt.BorderLayout());

        jScrollPane1.setBorder(null);

        Tabla1.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        Tabla1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Proyecto", "Plano", "Cantidad", "Tipo de material", "Tiempo", "Realizado por", "Fecha"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        Tabla1.setComponentPopupMenu(jPopupMenu1);
        Tabla1.setRowHeight(25);
        Tabla1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Tabla1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(Tabla1);

        jPanel4.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setLayout(new java.awt.BorderLayout(15, 15));

        lblEstado.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblEstado.setForeground(new java.awt.Color(204, 102, 0));
        lblEstado.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblEstado.setText("PLANOS TERMINADOS");
        jPanel7.add(lblEstado, java.awt.BorderLayout.CENTER);

        lblUbi.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblUbi.setForeground(new java.awt.Color(204, 102, 0));
        lblUbi.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblUbi.setText("PLANOS");
        jPanel7.add(lblUbi, java.awt.BorderLayout.PAGE_START);

        jPanel4.add(jPanel7, java.awt.BorderLayout.PAGE_START);

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/excel_1.png"))); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel6.add(jButton1);

        btnReportes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/Reporte.png"))); // NOI18N
        btnReportes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReportesActionPerformed(evt);
            }
        });
        jPanel6.add(btnReportes);

        jPanel4.add(jPanel6, java.awt.BorderLayout.PAGE_END);

        jPanel5.add(jPanel4);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setLayout(new java.awt.BorderLayout(15, 15));

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));
        jPanel9.setLayout(new java.awt.BorderLayout(15, 15));

        jLabel1.setFont(new java.awt.Font("Lexend", 1, 14)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Buscar por fecha");
        jPanel9.add(jLabel1, java.awt.BorderLayout.NORTH);

        btnBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ImgAnimacion/buscar_24.png"))); // NOI18N
        btnBuscar.setBorder(null);
        btnBuscar.setBorderPainted(false);
        btnBuscar.setContentAreaFilled(false);
        btnBuscar.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnBuscar.setFocusPainted(false);
        btnBuscar.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnBuscar.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/ImgAnimacion/buscar_24.png"))); // NOI18N
        btnBuscar.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/ImgAnimacion/buscar_32.png"))); // NOI18N
        btnBuscar.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });
        jPanel9.add(btnBuscar, java.awt.BorderLayout.LINE_END);

        jPanel12.setBackground(new java.awt.Color(153, 204, 255));
        jPanel12.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 30, 5));

        turno1.setBackground(new java.awt.Color(153, 204, 255));
        buttonGroup1.add(turno1);
        turno1.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        turno1.setSelected(true);
        turno1.setText("Turno No. 1 (06:00 a 16:00)");
        jPanel12.add(turno1);

        turno2.setBackground(new java.awt.Color(153, 204, 255));
        buttonGroup1.add(turno2);
        turno2.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        turno2.setText("Turno No.2 (16:00 a 02:00)");
        jPanel12.add(turno2);

        jPanel9.add(jPanel12, java.awt.BorderLayout.PAGE_END);

        jPanel14.setBackground(new java.awt.Color(255, 255, 255));
        jPanel14.setLayout(new java.awt.GridLayout(3, 0, 10, 10));

        calen.setBackground(new java.awt.Color(255, 255, 255));
        jPanel14.add(calen);

        jLabel5.setFont(new java.awt.Font("Lexend", 1, 14)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Buscar por proyecto");
        jPanel14.add(jLabel5);

        txtProyecto.setFont(new java.awt.Font("Lexend", 0, 14)); // NOI18N
        txtProyecto.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(204, 204, 204)));
        txtProyecto.setPreferredSize(new java.awt.Dimension(300, 22));
        txtProyecto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtProyectoActionPerformed(evt);
            }
        });
        jPanel14.add(txtProyecto);

        jPanel9.add(jPanel14, java.awt.BorderLayout.CENTER);

        jPanel3.add(jPanel9, java.awt.BorderLayout.PAGE_START);

        jPanel10.setBackground(new java.awt.Color(255, 255, 255));
        jPanel10.setLayout(new java.awt.GridLayout(6, 4, 10, 10));

        jLabel4.setText("   ");
        jPanel10.add(jLabel4);

        btnTerminados.setBackground(new java.awt.Color(180, 198, 231));
        btnTerminados.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        btnTerminados.setSelected(true);
        btnTerminados.setText("<html>\n<p style=\"text-align: center;\">Planos</p>\n<p style=\"text-align: center;\">Terminados</p>\n</html>");
        btnTerminados.setBorder(null);
        btnTerminados.setBorderPainted(false);
        btnTerminados.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnTerminados.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTerminadosActionPerformed(evt);
            }
        });
        jPanel10.add(btnTerminados);

        btnCurso.setBackground(new java.awt.Color(142, 169, 219));
        btnCurso.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        btnCurso.setText("<html>\n<p style=\"text-align: center;\">Planos</p>\n<p style=\"text-align: center;\">En curso</p>\n</html>");
        btnCurso.setBorder(null);
        btnCurso.setBorderPainted(false);
        btnCurso.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCurso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCursoActionPerformed(evt);
            }
        });
        jPanel10.add(btnCurso);

        btnEstacion.setBackground(new java.awt.Color(48, 84, 150));
        btnEstacion.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        btnEstacion.setText("<html>\n<p style=\"text-align: center;\">Planos</p>\n<p style=\"text-align: center;\">En estacion</p>\n</html>");
        btnEstacion.setBorder(null);
        btnEstacion.setBorderPainted(false);
        btnEstacion.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEstacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEstacionActionPerformed(evt);
            }
        });
        jPanel10.add(btnEstacion);

        btnCortes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/segueta.png"))); // NOI18N
        btnCortes.setToolTipText("Cortes");
        btnCortes.setBorder(null);
        btnCortes.setBorderPainted(false);
        btnCortes.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCortes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCortesActionPerformed(evt);
            }
        });
        jPanel10.add(btnCortes);

        txtCortes.setFont(new java.awt.Font("Roboto", 1, 24)); // NOI18N
        txtCortes.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCortes.setEnabled(false);
        jPanel10.add(txtCortes);

        txtCortes1.setFont(new java.awt.Font("Roboto", 1, 24)); // NOI18N
        txtCortes1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCortes1.setEnabled(false);
        jPanel10.add(txtCortes1);

        txtCortes2.setFont(new java.awt.Font("Roboto", 1, 24)); // NOI18N
        txtCortes2.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCortes2.setEnabled(false);
        jPanel10.add(txtCortes2);

        btnFresa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/fresadora.png"))); // NOI18N
        btnFresa.setToolTipText("Fresadora");
        btnFresa.setBorder(null);
        btnFresa.setBorderPainted(false);
        btnFresa.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnFresa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFresaActionPerformed(evt);
            }
        });
        jPanel10.add(btnFresa);

        txtFresa.setFont(new java.awt.Font("Roboto", 1, 24)); // NOI18N
        txtFresa.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtFresa.setEnabled(false);
        jPanel10.add(txtFresa);

        txtFresa1.setFont(new java.awt.Font("Roboto", 1, 24)); // NOI18N
        txtFresa1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtFresa1.setEnabled(false);
        jPanel10.add(txtFresa1);

        txtFresa2.setFont(new java.awt.Font("Roboto", 1, 24)); // NOI18N
        txtFresa2.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtFresa2.setEnabled(false);
        jPanel10.add(txtFresa2);

        btnCnc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/maquina.png"))); // NOI18N
        btnCnc.setToolTipText("Cnc");
        btnCnc.setBorder(null);
        btnCnc.setBorderPainted(false);
        btnCnc.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCnc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCncActionPerformed(evt);
            }
        });
        jPanel10.add(btnCnc);

        txtCnc.setFont(new java.awt.Font("Roboto", 1, 24)); // NOI18N
        txtCnc.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCnc.setEnabled(false);
        jPanel10.add(txtCnc);

        txtCnc1.setFont(new java.awt.Font("Roboto", 1, 24)); // NOI18N
        txtCnc1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCnc1.setEnabled(false);
        jPanel10.add(txtCnc1);

        txtCnc2.setFont(new java.awt.Font("Roboto", 1, 24)); // NOI18N
        txtCnc2.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCnc2.setEnabled(false);
        jPanel10.add(txtCnc2);

        btnTorno.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/torno.png"))); // NOI18N
        btnTorno.setToolTipText("Torno");
        btnTorno.setBorder(null);
        btnTorno.setBorderPainted(false);
        btnTorno.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnTorno.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTornoActionPerformed(evt);
            }
        });
        jPanel10.add(btnTorno);

        txtTorno.setFont(new java.awt.Font("Roboto", 1, 24)); // NOI18N
        txtTorno.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtTorno.setEnabled(false);
        jPanel10.add(txtTorno);

        txtTorno1.setFont(new java.awt.Font("Roboto", 1, 24)); // NOI18N
        txtTorno1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtTorno1.setEnabled(false);
        jPanel10.add(txtTorno1);

        txtTorno2.setFont(new java.awt.Font("Roboto", 1, 24)); // NOI18N
        txtTorno2.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtTorno2.setEnabled(false);
        jPanel10.add(txtTorno2);

        btnAcabados.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Acabados.png"))); // NOI18N
        btnAcabados.setToolTipText("Acbados");
        btnAcabados.setBorder(null);
        btnAcabados.setBorderPainted(false);
        btnAcabados.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAcabados.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAcabadosActionPerformed(evt);
            }
        });
        jPanel10.add(btnAcabados);

        txtAcabados.setFont(new java.awt.Font("Roboto", 1, 24)); // NOI18N
        txtAcabados.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtAcabados.setEnabled(false);
        jPanel10.add(txtAcabados);

        txtAcabados1.setFont(new java.awt.Font("Roboto", 1, 24)); // NOI18N
        txtAcabados1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtAcabados1.setEnabled(false);
        jPanel10.add(txtAcabados1);

        txtAcabados2.setFont(new java.awt.Font("Roboto", 1, 24)); // NOI18N
        txtAcabados2.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtAcabados2.setEnabled(false);
        jPanel10.add(txtAcabados2);

        jPanel3.add(jPanel10, java.awt.BorderLayout.CENTER);

        jPanel11.setBackground(new java.awt.Color(255, 255, 255));
        jPanel11.setLayout(new java.awt.GridLayout(3, 0));

        jLabel2.setText(" ");
        jPanel11.add(jLabel2);

        jButton2.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jButton2.setText("<html>\n<p>T</p>\n<p>A</p>\n<p>L</p>\n<p>L</p>\n<p>E</p>\n<p>R</p>\n</html>");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel11.add(jButton2);

        jLabel7.setText(" ");
        jPanel11.add(jLabel7);

        jPanel3.add(jPanel11, java.awt.BorderLayout.WEST);

        jPanel5.add(jPanel3);

        jPanel1.add(jPanel5, java.awt.BorderLayout.CENTER);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new java.awt.BorderLayout());

        jPanel13.setBackground(new java.awt.Color(255, 255, 255));

        btnSalir.setBackground(new java.awt.Color(255, 255, 255));

        jLabel3.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText(" X ");
        jLabel3.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jLabel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel3MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel3MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel3MouseExited(evt);
            }
        });
        btnSalir.add(jLabel3);

        jPanel13.add(btnSalir);

        jPanel2.add(jPanel13, java.awt.BorderLayout.EAST);

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));

        jLabel9.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 102, 153));
        jLabel9.setText("Reportes");
        jPanel8.add(jLabel9);

        jPanel2.add(jPanel8, java.awt.BorderLayout.CENTER);

        jPanel1.add(jPanel2, java.awt.BorderLayout.NORTH);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        jMenu1.setText("File");

        jMenuItem1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/completed-task.png"))); // NOI18N
        jMenuItem1.setText("Reportes de calidad");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuItem2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/ventas_16.png"))); // NOI18N
        jMenuItem2.setText("Reporte de ventas");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);

        jMenuItem3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/documento.png"))); // NOI18N
        jMenuItem3.setText("Reporte de planos");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem3);

        jMenuItem4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/noti_16.png"))); // NOI18N
        jMenuItem4.setText("Reporte mensual");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem4);

        jMenuItem5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/entrega-rapida.png"))); // NOI18N
        jMenuItem5.setText("Reporte herramienta");
        jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem5ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem5);

        jMenuItem6.setText("Reporte de scrap maquinados           ");
        jMenuItem6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem6ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem6);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCortesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCortesActionPerformed
        verTerminados("CORTES", "datos");
    }//GEN-LAST:event_btnCortesActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        try {
            SimpleDateFormat fecha = new SimpleDateFormat("dd/MM/yyyy");
            String fec = fecha.format(calen.getDate());
            int diaSig = Integer.parseInt(fec.substring(0, 2));
            diaSig += 1;
            String dia = diaSig + "/" + fec.substring(3, fec.length());
            Date da = fecha.parse(dia);
            String fecSig = fecha.format(da);
            DefaultTableModel miModelo = (DefaultTableModel) Tabla1.getModel();
            Connection con = null;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st1 = con.createStatement();
            Statement st2 = con.createStatement();
            Statement st3 = con.createStatement();
            Statement st4 = con.createStatement();
            Statement st5 = con.createStatement();
            String sql1 = "select FechaFinal, Proyecto, Plano,Cronometro, Empleado, Terminado,FechaInicio from datos where FechaFinal != null or FechaFinal != ''";
            ResultSet rs1 = st1.executeQuery(sql1);
            String sql2 = "select FechaFinal, Proyecto, Plano,Cronometro, Empleado, Terminado,FechaInicio from fresadora where FechaFinal != null or FechaFinal != ''";
            ResultSet rs2 = st2.executeQuery(sql2);
            String sql3 = "select FechaFinal, Proyecto, Plano,Cronometro, Empleado, Terminado,FechaInicio from cnc where FechaFinal != null or FechaFinal != ''";
            ResultSet rs3 = st3.executeQuery(sql3);
            String sql4 = "select FechaFinal, Proyecto, Plano,Cronometro, Empleado, Terminado,FechaInicio from torno where FechaFinal != null or FechaFinal != ''";
            ResultSet rs4 = st4.executeQuery(sql4);
            String sql5 = "select FechaFinal, Proyecto, Plano,Cronometro, Empleado, Terminado,FechaInicio from acabados where FechaFinal != null or FechaFinal != ''";
            ResultSet rs5 = st5.executeQuery(sql5);
            String datos1[] = new String[10];
            String datos2[] = new String[10];
            String datos3[] = new String[10];
            String datos4[] = new String[10];
            String datos5[] = new String[10];
            int cont1 = 0, cont2 = 0, cont22 = 0, cont3 = 0, cont33 = 0, cont4 = 0, cont44 = 0, cont5 = 0, cont55 = 0, cont6 = 0;

            while (rs1.next()) {
                datos1[0] = rs1.getString("Proyecto");
                datos1[1] = rs1.getString("Plano");
                datos1[2] = rs1.getString("Cronometro");
                datos1[3] = rs1.getString("Empleado");
                datos1[4] = rs1.getString("FechaFinal");
                datos1[5] = rs1.getString("Terminado");
                datos1[6] = rs1.getString("FechaInicio");
                if (datos1[4] != null || !"".equals(datos1[4])) {
                    String f = datos1[4].substring(0, 10);
                    int hor = 40;
                    try {
                        hor = Integer.parseInt(datos1[4].substring(11, 13));
                    } catch (Exception e) {
//                        System.out.println("Exception: "+e);
                    }
                    if ((fec.equals(f) && (hor >= 5 && hor <= 16))) {
                        cont1++;
                    } else if ((fec.equals(f) && (hor >= 16)) || (fecSig.equals(f)) && (hor >= 16 || hor <= 5)) {
                        cont6++;
                    }
                }

            }
            while (rs2.next()) {
                datos2[0] = rs2.getString("Proyecto");
                datos2[1] = rs2.getString("Plano");
                datos2[2] = rs2.getString("Cronometro");
                datos2[3] = rs2.getString("Empleado");
                datos2[4] = rs2.getString("FechaFinal");
                datos2[5] = rs2.getString("Terminado");
                if (datos2[4] != null || !"".equals(datos2[4])) {
                    String f = datos2[4].substring(0, 10);
                    int hor = 40;
                    try {
                        hor = Integer.parseInt(datos2[4].substring(11, 13));
                    } catch (Exception e) {
//                        System.out.println("Exception: "+e);
                    }
                    if ((fec.equals(f) && (hor >= 6 && hor <= 16))) {
                        cont2++;
                    } else if ((fec.equals(f) && (hor >= 16)) || (fecSig.equals(f)) && (hor >= 16 || hor <= 5)) {
                        cont22++;
                    }
                }
            }
            while (rs3.next()) {
                datos3[0] = rs3.getString("Proyecto");
                datos3[1] = rs3.getString("Plano");
                datos3[2] = rs3.getString("Cronometro");
                datos3[3] = rs3.getString("Empleado");
                datos3[4] = rs3.getString("FechaFinal");
                datos3[5] = rs3.getString("Terminado");
                if (datos3[4] != null || !"".equals(datos3[4])) {
                    String f = datos3[4].substring(0, 10);
                    int hor = 40;
                    try {
                        hor = Integer.parseInt(datos3[4].substring(11, 13));
                    } catch (Exception e) {
//                        System.out.println("Exception: "+e);
                    }
                    if ((fec.equals(f) && (hor >= 6 && hor <= 16))) {
                        cont3++;
                    } else if ((fec.equals(f) && (hor >= 16)) || (fecSig.equals(f)) && (hor >= 16 || hor <= 5)) {
                        cont33++;
                    }
                }
            }
            while (rs4.next()) {
                datos4[0] = rs4.getString("Proyecto");
                datos4[1] = rs4.getString("Plano");
                datos4[2] = rs4.getString("Cronometro");
                datos4[3] = rs4.getString("Empleado");
                datos4[4] = rs4.getString("FechaFinal");
                datos4[5] = rs4.getString("Terminado");
                if (datos4[4] != null || !"".equals(datos4[4])) {
                    String f = datos4[4].substring(0, 10);
                    int hor = 40;
                    try {
                        hor = Integer.parseInt(datos4[4].substring(11, 13));
                    } catch (Exception e) {
//                        System.out.println("Exception: "+e);
                    }
                    if ((fec.equals(f) && (hor >= 6 && hor <= 16))) {
                        cont4++;
                    } else if ((fec.equals(f) && (hor >= 16)) || (fecSig.equals(f)) && (hor >= 16 || hor <= 5)) {
                        cont44++;
                    }
                }
            }
            while (rs5.next()) {
                datos5[0] = rs5.getString("Proyecto");
                datos5[1] = rs5.getString("Plano");
                datos5[2] = rs5.getString("Cronometro");
                datos5[3] = rs5.getString("Empleado");
                datos5[4] = rs5.getString("FechaFinal");
                datos5[5] = rs5.getString("Terminado");
                if (datos5[4] != null || !"".equals(datos5[4])) {
                    String f = datos5[4].substring(0, 10);
                    int hor = 40;
                    try {
                        hor = Integer.parseInt(datos5[4].substring(11, 13));
                    } catch (Exception e) {
//                        System.out.println("Exception: "+e);
                    }
                    if ((fec.equals(f) && (hor >= 6 && hor <= 16))) {
                        cont5++;
                    } else if ((fec.equals(f) && (hor >= 16)) || (fecSig.equals(f)) && (hor >= 16 || hor <= 5)) {
                        cont55++;
                    }
                }
            }
            txtCortes.setText("" + cont1 + "/" + cont6);
            txtFresa.setText("" + cont2 + "/" + cont22);
            txtCnc.setText("" + cont3 + "/" + cont33);
            txtTorno.setText("" + cont4 + "/" + cont44);
            txtAcabados.setText("" + cont5 + "/" + cont55);
        } catch (SQLException E) {
            JOptionPane.showMessageDialog(this, "ERROR EN LA BD" + E);
        } catch (ParseException ex) {
            Logger.getLogger(Reportes.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            Date fecha1 = new Date();
            SimpleDateFormat fecha = new SimpleDateFormat("dd/MM/yyyy");
            String fec = fecha.format(calen.getDate());

            DefaultTableModel miModelo = (DefaultTableModel) Tabla1.getModel();
            Connection con = null;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st1 = con.createStatement();
            Statement st2 = con.createStatement();
            Statement st3 = con.createStatement();
            Statement st4 = con.createStatement();
            Statement st5 = con.createStatement();
            String sql1 = "select FechaFinal, Proyecto, Plano,Cronometro, Empleado, Terminado,FechaInicio from datos where FechaInicio != null or FechaInicio != '' and (FechaFinal = null or FechaFinal = '')";
            ResultSet rs1 = st1.executeQuery(sql1);
            String sql2 = "select FechaFinal, Proyecto, Plano,Cronometro, Empleado, Terminado,FechaInicio from fresadora where FechaInicio != null or FechaInicio != '' and (FechaFinal = null or FechaFinal = '')";
            ResultSet rs2 = st2.executeQuery(sql2);
            String sql3 = "select FechaFinal, Proyecto, Plano,Cronometro, Empleado, Terminado,FechaInicio from cnc where FechaInicio != null or FechaInicio != '' and (FechaFinal = null or FechaFinal = '')";
            ResultSet rs3 = st3.executeQuery(sql3);
            String sql4 = "select FechaFinal, Proyecto, Plano,Cronometro, Empleado, Terminado,FechaInicio from torno where FechaInicio != null or FechaInicio != '' and (FechaFinal = null or FechaFinal = '')";
            ResultSet rs4 = st4.executeQuery(sql4);
            String sql5 = "select FechaFinal, Proyecto, Plano,Cronometro, Empleado, Terminado,FechaInicio from acabados where FechaInicio != null or FechaInicio != '' and (FechaFinal = null or FechaFinal = '')";
            ResultSet rs5 = st5.executeQuery(sql5);
            String datos1[] = new String[10];
            String datos2[] = new String[10];
            String datos3[] = new String[10];
            String datos4[] = new String[10];
            String datos5[] = new String[10];
            int cont1 = 0, cont2 = 0, cont3 = 0, cont4 = 0, cont5 = 0;
            while (rs1.next()) {
                datos1[0] = rs1.getString("Proyecto");
                datos1[1] = rs1.getString("Plano");
                datos1[2] = rs1.getString("Cronometro");
                datos1[3] = rs1.getString("Empleado");
                datos1[4] = rs1.getString("FechaInicio");
                if (datos1[4] != null || datos1[4] != "") {
                    String f = datos1[4].substring(0, 10);
                    if (fec.equals(f)) {
                        cont1++;
                    }
                }
            }
            while (rs2.next()) {
                datos2[0] = rs2.getString("Proyecto");
                datos2[1] = rs2.getString("Plano");
                datos2[2] = rs2.getString("Cronometro");
                datos2[3] = rs2.getString("Empleado");
                datos2[4] = rs2.getString("FechaInicio");
                if (datos2[4] != null || datos2[4] != "") {
                    String f = datos2[4].substring(0, 10);
                    if (fec.equals(f)) {
                        cont2++;
                    }
                }
            }
            while (rs3.next()) {
                datos3[0] = rs3.getString("Proyecto");
                datos3[1] = rs3.getString("Plano");
                datos3[2] = rs3.getString("Cronometro");
                datos3[3] = rs3.getString("Empleado");
                datos3[4] = rs3.getString("FechaInicio");
                if (datos3[4] != null || datos3[4] != "") {
                    String f = datos3[4].substring(0, 10);
                    if (fec.equals(f)) {
                        cont3++;
                    }
                }
            }
            while (rs4.next()) {
                datos4[0] = rs4.getString("Proyecto");
                datos4[1] = rs4.getString("Plano");
                datos4[2] = rs4.getString("Cronometro");
                datos4[3] = rs4.getString("Empleado");
                datos4[4] = rs4.getString("FechaInicio");
                if (datos4[4] != null || datos4[4] != "") {
                    String f = datos4[4].substring(0, 10);
                    if (fec.equals(f)) {
                        cont4++;
                    }
                }
            }
            while (rs5.next()) {
                datos5[0] = rs5.getString("Proyecto");
                datos5[1] = rs5.getString("Plano");
                datos5[2] = rs5.getString("Cronometro");
                datos5[3] = rs5.getString("Empleado");
                datos5[4] = rs5.getString("FechaInicio");
                if (datos5[4] != null || datos5[4] != "") {
                    String f = datos5[4].substring(0, 10);
                    if (fec.equals(f)) {
                        cont5++;
                    }
                }
            }
            txtCortes1.setText("" + cont1);
            txtFresa1.setText("" + cont2);
            txtCnc1.setText("" + cont3);
            txtTorno1.setText("" + cont4);
            txtAcabados1.setText("" + cont5);
        } catch (SQLException E) {
            JOptionPane.showMessageDialog(this, "ERROR EN LA BD" + E);
            Logger.getLogger(OrdenDeCompra.class.getName()).log(Level.SEVERE, null, E);
        }
        try {
            Date fecha1 = new Date();
            SimpleDateFormat fecha = new SimpleDateFormat("dd/MM/yyyy");
            String fec = fecha.format(calen.getDate());

            DefaultTableModel miModelo = (DefaultTableModel) Tabla1.getModel();
            Connection con = null;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st1 = con.createStatement();
            Statement st2 = con.createStatement();
            Statement st3 = con.createStatement();
            Statement st4 = con.createStatement();
            Statement st5 = con.createStatement();
            String sql1 = "select * from datos where Terminado like 'NO'";
            ResultSet rs1 = st1.executeQuery(sql1);
            String sql2 = "select * from fresadora where Terminado like 'NO'";
            ResultSet rs2 = st2.executeQuery(sql2);
            String sql3 = "select * from cnc where Terminado like 'NO'";
            ResultSet rs3 = st3.executeQuery(sql3);
            String sql4 = "select * from torno where Terminado like 'NO'";
            ResultSet rs4 = st4.executeQuery(sql4);
            String sql5 = "select * from acabados where Terminado like 'NO'";
            ResultSet rs5 = st5.executeQuery(sql5);
            String datos1[] = new String[10];
            String datos2[] = new String[10];
            String datos3[] = new String[10];
            String datos4[] = new String[10];
            String datos5[] = new String[10];
            int cont1 = 0, cont2 = 0, cont3 = 0, cont4 = 0, cont5 = 0;
            while (rs1.next()) {
                cont1++;
            }
            while (rs2.next()) {
                cont2++;
            }
            while (rs3.next()) {
                cont3++;
            }
            while (rs4.next()) {
                cont4++;
            }
            while (rs5.next()) {
                cont5++;
            }
            txtCortes2.setText("" + cont1);
            txtFresa2.setText("" + cont2);
            txtCnc2.setText("" + cont3);
            txtTorno2.setText("" + cont4);
            txtAcabados2.setText("" + cont5);
        } catch (SQLException E) {
            JOptionPane.showMessageDialog(this, "ERROR EN LA BD" + E);
        }
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void btnTerminadosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTerminadosActionPerformed
        btnCurso.setSelected(false);
        btnEstacion.setSelected(false);
        btnTerminados.setSelected(true);
        lblEstado.setText("PLANOS TERMINADOS");
        lblEstado.setForeground(new java.awt.Color(0, 153, 153));
    }//GEN-LAST:event_btnTerminadosActionPerformed

    private void btnEstacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEstacionActionPerformed
        btnTerminados.setSelected(false);
        btnCurso.setSelected(false);
        btnEstacion.setSelected(true);
        lblEstado.setText("PLANOS EN ESTACION");
        lblEstado.setForeground(new java.awt.Color(102, 0, 102));
    }//GEN-LAST:event_btnEstacionActionPerformed

    private void btnFresaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFresaActionPerformed
//        try {
//            SimpleDateFormat nuevo = new SimpleDateFormat("dd/MM/yyyy");
//            String fec = nuevo.format(calen.getDate());
//            lblUbi.setText("FRESADORA");
//            limpiarTabla();
//            DefaultTableModel miModelo = (DefaultTableModel) Tabla1.getModel();
//            Connection con = null;
//            Conexion con1 = new Conexion();
//            con = con1.getConnection();
//            Statement st1 = con.createStatement();
//            Statement st2 = con.createStatement();
//            Statement st3 = con.createStatement();
//            String datos[] = new String[10];
//            String datos1[] = new String[10];
//
//            if(btnEstacion.isSelected()){
//                String sql2 = "select * from fresadora where Terminado like 'NO'";
//                ResultSet rs2 = st2.executeQuery(sql2);
//                while(rs2.next()){
//                    datos1[4] = rs2.getString("FechaFinal");
//                        datos[0] = rs2.getString("Plano");
//                        datos[1] = rs2.getString("Proyecto");
//                        String sql4 = "select Plano, Cantidad, Material from planos where Plano like '"+datos[1]+"'";
//                        String cantidad = "", tipo = "";
//                        Statement st5 = con.createStatement();
//                        ResultSet rs5 = st5.executeQuery(sql4);
//                        while(rs5.next()){
//                            cantidad = rs5.getString("Cantidad");
//                            tipo = rs5.getString("Material");
//                        }
//                        datos[2] = cantidad;
//                        datos[3] = tipo;
//                        datos[4] = "0";
//                        datos[5] = rs2.getString("Cronometro");
//                        datos[6] = rs2.getString("Empleado");
//                        miModelo.addRow(datos);
//                }
//            }else if(btnCurso.isSelected()){
//                String sql3 = "select * from fresadora where FechaInicio != '' and FechaFinal like '' and Terminado like 'NO'";
//                ResultSet rs3 = st3.executeQuery(sql3);
//                while(rs3.next()){
//                    datos1[4] = rs3.getString("FechaFinal");
//                        datos[0] = rs3.getString("Plano");
//                        datos[1] = rs3.getString("Proyecto");
//                        String sql4 = "select Plano, Cantidad, Material from planos where Plano like '"+datos[1]+"'";
//                        String cantidad = "", tipo = "";
//                        Statement st5 = con.createStatement();
//                        ResultSet rs5 = st5.executeQuery(sql4);
//                        while(rs5.next()){
//                            cantidad = rs5.getString("Cantidad");
//                            tipo = rs5.getString("Material");
//                        }
//                        datos[2] = cantidad;
//                        datos[3] = tipo;
//                        datos[4] = "0";
//                        datos[5] = rs3.getString("Cronometro");
//                        datos[6] = rs3.getString("Empleado");
//                        miModelo.addRow(datos);
//                }
//            }else if(btnTerminados.isSelected()){
//                String sql3 = "select * from fresadora where Terminado like 'SI' and FechaFinal != ''";
//                ResultSet rs3 = st3.executeQuery(sql3);
//                while(rs3.next()){
//                    datos1[4] = rs3.getString("FechaFinal");
//                    String s = datos1[4].substring(0,10);
//                    if(fec.equals(s)){
//                        
//                        datos[0] = rs3.getString("Plano");
//                        datos[1] = rs3.getString("Proyecto");
//                        String sql4 = "select Plano, Cantidad, Material from planos where Plano like '"+datos[1]+"'";
//                        String cantidad = "", tipo = "";
//                        Statement st5 = con.createStatement();
//                        ResultSet rs5 = st5.executeQuery(sql4);
//                        while(rs5.next()){
//                            cantidad = rs5.getString("Cantidad");
//                            tipo = rs5.getString("Material");
//                        }
//                        datos[2] = cantidad;
//                        datos[3] = tipo;
//                        datos[4] = "0";
//                        datos[5] = rs3.getString("Cronometro");
//                        datos[6] = rs3.getString("Empleado");
//                        miModelo.addRow(datos);
//                    }
//                }
//            }
//
//        } catch (SQLException e) {
//            JOptionPane.showMessageDialog(this, "ERROR AL VER BASE DE DATOS" + e);
//        }
        verTerminados("FRESADORA", "fresadora");
    }//GEN-LAST:event_btnFresaActionPerformed

    private void btnAcabadosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAcabadosActionPerformed
//        try {
//            SimpleDateFormat nuevo = new SimpleDateFormat("dd/MM/yyyy");
//            String fec = nuevo.format(calen.getDate());
//            lblUbi.setText("ACABADOS");
//            limpiarTabla();
//            DefaultTableModel miModelo = (DefaultTableModel) Tabla1.getModel();
//            Connection con = null;
//            Conexion con1 = new Conexion();
//            con = con1.getConnection();
//            Statement st1 = con.createStatement();
//            Statement st2 = con.createStatement();
//            Statement st3 = con.createStatement();
//            String datos[] = new String[10];
//            String datos1[] = new String[10];
//
//            if(btnEstacion.isSelected()){
//                String sql2 = "select * from acabados where Terminado like 'NO'";
//                ResultSet rs2 = st2.executeQuery(sql2);
//                while(rs2.next()){
//                    datos1[4] = rs2.getString("FechaFinal");
//                        datos[0] = rs2.getString("Plano");
//                        datos[1] = rs2.getString("Proyecto");
//                        String sql4 = "select Plano, Cantidad, Material from planos where Plano like '"+datos[1]+"'";
//                        String cantidad = "", tipo = "";
//                        Statement st5 = con.createStatement();
//                        ResultSet rs5 = st5.executeQuery(sql4);
//                        while(rs5.next()){
//                            cantidad = rs5.getString("Cantidad");
//                            tipo = rs5.getString("Material");
//                        }
//                        datos[2] = cantidad;
//                        datos[3] = tipo;
//                        datos[4] = "0";
//                        datos[5] = rs2.getString("Cronometro");
//                        datos[6] = rs2.getString("Empleado");
//                        miModelo.addRow(datos);
//                }
//            }else if(btnCurso.isSelected()){
//                String sql3 = "select * from acabados where FechaInicio != '' and FechaFinal like '' and Terminado like 'NO'";
//                ResultSet rs3 = st3.executeQuery(sql3);
//                while(rs3.next()){
//                    datos1[4] = rs3.getString("FechaFinal");
//                        datos[0] = rs3.getString("Plano");
//                        datos[1] = rs3.getString("Proyecto");
//                        String sql4 = "select Plano, Cantidad, Material from planos where Plano like '"+datos[1]+"'";
//                        String cantidad = "", tipo = "";
//                        Statement st5 = con.createStatement();
//                        ResultSet rs5 = st5.executeQuery(sql4);
//                        while(rs5.next()){
//                            cantidad = rs5.getString("Cantidad");
//                            tipo = rs5.getString("Material");
//                        }
//                        datos[2] = cantidad;
//                        datos[3] = tipo;
//                        datos[4] = "0";
//                        datos[5] = rs3.getString("Cronometro");
//                        datos[6] = rs3.getString("Empleado");
//                        miModelo.addRow(datos);
//                }
//            }else if(btnTerminados.isSelected()){
//                String sql3 = "select * from acabados where Terminado like 'SI' and FechaFinal != ''";
//                ResultSet rs3 = st3.executeQuery(sql3);
//                while(rs3.next()){
//                    datos1[4] = rs3.getString("FechaFinal");
//                    String s = datos1[4].substring(0,10);
//                    if(fec.equals(s)){
//                        
//                        datos[0] = rs3.getString("Plano");
//                        datos[1] = rs3.getString("Proyecto");
//                        String sql4 = "select Plano, Cantidad, Material from planos where Plano like '"+datos[1]+"'";
//                        String cantidad = "", tipo = "";
//                        Statement st5 = con.createStatement();
//                        ResultSet rs5 = st5.executeQuery(sql4);
//                        while(rs5.next()){
//                            cantidad = rs5.getString("Cantidad");
//                            tipo = rs5.getString("Material");
//                        }
//                        datos[2] = cantidad;
//                        datos[3] = tipo;
//                        datos[4] = "0";
//                        datos[5] = rs3.getString("Cronometro");
//                        datos[6] = rs3.getString("Empleado");
//                        miModelo.addRow(datos);
//                    }
//                }
//            }
//
//        } catch (SQLException e) {
//            JOptionPane.showMessageDialog(this, "ERROR AL VER BASE DE DATOS" + e);
//        }
        verTerminados("ACABADOS", "acabados");
    }//GEN-LAST:event_btnAcabadosActionPerformed

    private void btnCursoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCursoActionPerformed
        btnTerminados.setSelected(false);
        btnEstacion.setSelected(false);
        btnCurso.setSelected(true);
        lblEstado.setText("PLANOS EN CURSO");
        lblEstado.setForeground(new java.awt.Color(102, 102, 0));
    }//GEN-LAST:event_btnCursoActionPerformed

    private void btnCncActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCncActionPerformed
//        try {
//            SimpleDateFormat nuevo = new SimpleDateFormat("dd/MM/yyyy");
//            String fec = nuevo.format(calen.getDate());
//            lblUbi.setText("CNC");
//            limpiarTabla();
//            DefaultTableModel miModelo = (DefaultTableModel) Tabla1.getModel();
//            Connection con = null;
//            Conexion con1 = new Conexion();
//            con = con1.getConnection();
//            Statement st1 = con.createStatement();
//            Statement st2 = con.createStatement();
//            Statement st3 = con.createStatement();
//            String datos[] = new String[10];
//            String datos1[] = new String[10];
//
//            if(btnEstacion.isSelected()){
//                String sql2 = "select * from cnc where Terminado like 'NO'";
//                ResultSet rs2 = st2.executeQuery(sql2);
//                while(rs2.next()){
//                    datos1[4] = rs2.getString("FechaFinal");
//                        datos[0] = rs2.getString("Plano");
//                        datos[1] = rs2.getString("Proyecto");
//                        String sql4 = "select Plano, Cantidad, Material from planos where Plano like '"+datos[1]+"'";
//                        String cantidad = "", tipo = "";
//                        Statement st5 = con.createStatement();
//                        ResultSet rs5 = st5.executeQuery(sql4);
//                        while(rs5.next()){
//                            cantidad = rs5.getString("Cantidad");
//                            tipo = rs5.getString("Material");
//                        }
//                        datos[2] = cantidad;
//                        datos[3] = tipo;
//                        datos[4] = "0";
//                        datos[5] = rs2.getString("Cronometro");
//                        datos[6] = rs2.getString("Empleado");
//                        miModelo.addRow(datos);
//                }
//            }else if(btnCurso.isSelected()){
//                String sql3 = "select * from cnc where FechaInicio != '' and FechaFinal like '' and Terminado like 'NO'";
//                ResultSet rs3 = st3.executeQuery(sql3);
//                while(rs3.next()){
//                    datos1[4] = rs3.getString("FechaFinal");
//                        datos[0] = rs3.getString("Plano");
//                        datos[1] = rs3.getString("Proyecto");
//                        String sql4 = "select Plano, Cantidad, Material from planos where Plano like '"+datos[1]+"'";
//                        String cantidad = "", tipo = "";
//                        Statement st5 = con.createStatement();
//                        ResultSet rs5 = st5.executeQuery(sql4);
//                        while(rs5.next()){
//                            cantidad = rs5.getString("Cantidad");
//                            tipo = rs5.getString("Material");
//                        }
//                        datos[2] = cantidad;
//                        datos[3] = tipo;
//                        datos[4] = "0";
//                        datos[5] = rs3.getString("Cronometro");
//                        datos[6] = rs3.getString("Empleado");
//                        miModelo.addRow(datos);
//                }
//            }else if(btnTerminados.isSelected()){
//                String sql3 = "select * from cnc where Terminado like 'SI' and FechaFinal != ''";
//                ResultSet rs3 = st3.executeQuery(sql3);
//                while(rs3.next()){
//                    datos1[4] = rs3.getString("FechaFinal");
//                    String s = datos1[4].substring(0,10);
//                    if(fec.equals(s)){
//                        
//                        datos[0] = rs3.getString("Plano");
//                        datos[1] = rs3.getString("Proyecto");
//                        String sql4 = "select Plano, Cantidad, Material from planos where Plano like '"+datos[1]+"'";
//                        String cantidad = "", tipo = "";
//                        Statement st5 = con.createStatement();
//                        ResultSet rs5 = st5.executeQuery(sql4);
//                        while(rs5.next()){
//                            cantidad = rs5.getString("Cantidad");
//                            tipo = rs5.getString("Material");
//                        }
//                        datos[2] = cantidad;
//                        datos[3] = tipo;
//                        datos[4] = "0";
//                        datos[5] = rs3.getString("Cronometro");
//                        datos[6] = rs3.getString("Empleado");
//                        miModelo.addRow(datos);
//                    }
//                }
//            }
//
//        } catch (SQLException e) {
//            JOptionPane.showMessageDialog(this, "ERROR AL VER BASE DE DATOS" + e);
//        }
        verTerminados("CNC", "cnc");
    }//GEN-LAST:event_btnCncActionPerformed

    private void btnTornoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTornoActionPerformed
//        try {
//            SimpleDateFormat nuevo = new SimpleDateFormat("dd/MM/yyyy");
//            String fec = nuevo.format(calen.getDate());
//            lblUbi.setText("TORNO");
//            limpiarTabla();
//            DefaultTableModel miModelo = (DefaultTableModel) Tabla1.getModel();
//            Connection con = null;
//            Conexion con1 = new Conexion();
//            con = con1.getConnection();
//            Statement st1 = con.createStatement();
//            Statement st2 = con.createStatement();
//            Statement st3 = con.createStatement();
//            String datos[] = new String[10];
//            String datos1[] = new String[10];
//
//            if(btnEstacion.isSelected()){
//                String sql2 = "select * from torno where Terminado like 'NO'";
//                ResultSet rs2 = st2.executeQuery(sql2);
//                while(rs2.next()){
//                    datos1[4] = rs2.getString("FechaFinal");
//                        datos[0] = rs2.getString("Plano");
//                        datos[1] = rs2.getString("Proyecto");
//                        String sql4 = "select Plano, Cantidad, Material from planos where Plano like '"+datos[1]+"'";
//                        String cantidad = "", tipo = "";
//                        Statement st5 = con.createStatement();
//                        ResultSet rs5 = st5.executeQuery(sql4);
//                        while(rs5.next()){
//                            cantidad = rs5.getString("Cantidad");
//                            tipo = rs5.getString("Material");
//                        }
//                        datos[2] = cantidad;
//                        datos[3] = tipo;
//                        datos[4] = "0";
//                        datos[5] = rs2.getString("Cronometro");
//                        datos[6] = rs2.getString("Empleado");
//                        miModelo.addRow(datos);
//                }
//            }else if(btnCurso.isSelected()){
//                String sql3 = "select * from torno where FechaInicio != '' and FechaFinal like '' and Terminado like 'NO'";
//                ResultSet rs3 = st3.executeQuery(sql3);
//                while(rs3.next()){
//                    datos1[4] = rs3.getString("FechaFinal");
//                        datos[0] = rs3.getString("Plano");
//                        datos[1] = rs3.getString("Proyecto");
//                        String sql4 = "select Plano, Cantidad, Material from planos where Plano like '"+datos[1]+"'";
//                        String cantidad = "", tipo = "";
//                        Statement st5 = con.createStatement();
//                        ResultSet rs5 = st5.executeQuery(sql4);
//                        while(rs5.next()){
//                            cantidad = rs5.getString("Cantidad");
//                            tipo = rs5.getString("Material");
//                        }
//                        datos[2] = cantidad;
//                        datos[3] = tipo;
//                        datos[4] = "0";
//                        datos[5] = rs3.getString("Cronometro");
//                        datos[6] = rs3.getString("Empleado");
//                        miModelo.addRow(datos);
//                }
//            }else if(btnTerminados.isSelected()){
//                String sql3 = "select * from torno where Terminado like 'SI' and FechaFinal != ''";
//                ResultSet rs3 = st3.executeQuery(sql3);
//                while(rs3.next()){
//                    datos1[4] = rs3.getString("FechaFinal");
//                    String s = datos1[4].substring(0,10);
//                    if(fec.equals(s)){
//                        
//                        datos[0] = rs3.getString("Plano");
//                        datos[1] = rs3.getString("Proyecto");
//                        String sql4 = "select Plano, Cantidad, Material from planos where Plano like '"+datos[1]+"'";
//                        String cantidad = "", tipo = "";
//                        Statement st5 = con.createStatement();
//                        ResultSet rs5 = st5.executeQuery(sql4);
//                        while(rs5.next()){
//                            cantidad = rs5.getString("Cantidad");
//                            tipo = rs5.getString("Material");
//                        }
//                        datos[2] = cantidad;
//                        datos[3] = tipo;
//                        datos[4] = "0";
//                        datos[5] = rs3.getString("Cronometro");
//                        datos[6] = rs3.getString("Empleado");
//                        miModelo.addRow(datos);
//                    }
//                }
//            }
//
//        } catch (SQLException e) {
//            JOptionPane.showMessageDialog(this, "ERROR AL VER BASE DE DATOS" + e);
//        }
        verTerminados("TORNO", "torno");
    }//GEN-LAST:event_btnTornoActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        JFrame frame = (JFrame) JOptionPane.getFrameForComponent(this);
        VentanaEmergente.Inicio1.Reportes r = new VentanaEmergente.Inicio1.Reportes(frame, true);
        r.setVisible(true);
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        JFrame f = (JFrame) JOptionPane.getFrameForComponent(this);
        dialog v = new dialog(f, true);
        v.btnModificar.setVisible(false);
        v.jLabel2.setVisible(false);
        v.jLabel3.setVisible(false);
        v.jLabel4.setVisible(false);
        v.jLabel5.setVisible(false);
        v.jLabel6.setVisible(false);
        v.jSeparator1.setVisible(false);
        v.jSeparator2.setVisible(false);
        v.jSeparator3.setVisible(false);
        v.jSeparator4.setVisible(false);
        v.txtCotizacion.setVisible(false);
        v.txtDescripcion.setVisible(false);
        v.txtOc.setVisible(false);
        v.txtProyecto.setVisible(false);
        v.txtRequisicion.setVisible(false);
        v.setVisible(true);
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed

        Plano p = new Plano();
        p.setVisible(true);
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        JFrame f = (JFrame) JOptionPane.getFrameForComponent(this);
        ReporteMensual r = new ReporteMensual(f, true);
        r.setVisible(true);
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem5ActionPerformed
        JFrame f = (JFrame) JOptionPane.getFrameForComponent(this);
        ReporteHerramienta r = new ReporteHerramienta(f, true);
        r.setVisible(true);
    }//GEN-LAST:event_jMenuItem5ActionPerformed

    private void jLabel3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MouseClicked
        dispose();
    }//GEN-LAST:event_jLabel3MouseClicked

    private void jLabel3MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MouseEntered
        btnSalir.setBackground(java.awt.Color.red);
    }//GEN-LAST:event_jLabel3MouseEntered

    private void jLabel3MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MouseExited
        btnSalir.setBackground(java.awt.Color.white);
    }//GEN-LAST:event_jLabel3MouseExited

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

            Sheet hoja = book.createSheet("REPORTE DE " + lblEstado.getText());
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
            hoja.setColumnWidth(2, 4000);
            hoja.setColumnWidth(3, 6500);
            hoja.setColumnWidth(4, 6500);
            hoja.setColumnWidth(5, 8200);
            hoja.setColumnWidth(8, 8200);

            org.apache.poi.ss.usermodel.Font font1 = book.createFont();
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
                    8
            ));

            hoja.addMergedRegion(new CellRangeAddress(
                    4,
                    4,
                    2,
                    4
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
            col.setCellValue("Reporte de planos de " + lblUbi.getText());

            for (int i = -1; i < Tabla1.getRowCount(); i++) {
                Row fila10 = hoja.createRow(i + 7);
                for (int j = 0; j < 7; j++) {
                    Cell celda = fila10.createCell(j + 2);
                    if (i == -1 && (j >= 0 && j <= 6)) {
                        CellStyle s = book.createCellStyle();
                        org.apache.poi.ss.usermodel.Font f = book.createFont();
                        f.setBold(true);
                        f.setColor(IndexedColors.WHITE.getIndex());
                        s.setFont(f);
                        s.setFillForegroundColor(IndexedColors.GREY_50_PERCENT.getIndex());
                        s.setFillPattern(SOLID_FOREGROUND);
                        celda.setCellStyle(s);
                    }
                    if (i > -1 && (j > -1 && j <= 6) && (i % 2 == 0)) {
                        CellStyle s = book.createCellStyle();
                        s.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
                        s.setFillPattern(SOLID_FOREGROUND);
                        celda.setCellStyle(s);
                    }

                    if (i == -1) {
                        celda.setCellValue(String.valueOf(Tabla1.getColumnName(j)));
//                        CellUtil.setCellStyleProperties(celda, properties);
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
                        celda.setCellValue(String.valueOf(Tabla1.getValueAt(i, j)));
//                        CellUtil.setCellStyleProperties(celda, properties);

                    }
                    File ad = new File(a);
                    book.write(new FileOutputStream(a));

                }
            }

            book.close();
            try {
                Runtime.getRuntime().exec("cmd /c start " + a);
                Runtime.getRuntime().exec("cmd /c close");
            } catch (IOException ee) {
                JOptionPane.showMessageDialog(this, ee);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(CambiarEstado.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(CambiarEstado.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        try {

            SimpleDateFormat nuevo = new SimpleDateFormat("dd/MM/yyyy");
            String fec = nuevo.format(calen.getDate());
            int diaSig = Integer.parseInt(fec.substring(0, 2));
            diaSig += 1;
            String dia = diaSig + "/" + fec.substring(3, fec.length());
            Date da = nuevo.parse(dia);
            String fecSig = nuevo.format(da);
            lblUbi.setText("TALLER");
            limpiarTabla();
            DefaultTableModel miModelo = (DefaultTableModel) Tabla1.getModel();
            Connection con = null;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st1 = con.createStatement();
            Statement st2 = con.createStatement();
            Statement st3 = con.createStatement();
            String datos[] = new String[10];
            String datos1[] = new String[10];

            if (btnEstacion.isSelected()) {
                String sql2 = "select * from fresadora where Terminado like 'NO'";
                ResultSet rs2 = st2.executeQuery(sql2);
                while (rs2.next()) {
                    datos1[4] = rs2.getString("FechaFinal");
                    datos[0] = rs2.getString("Plano");
                    datos[1] = rs2.getString("Proyecto");
                    String sql4 = "select Plano, Cantidad, Material from planos where Plano like '" + datos[1] + "'";
                    String cantidad = "", tipo = "";
                    Statement st5 = con.createStatement();
                    ResultSet rs5 = st5.executeQuery(sql4);
                    while (rs5.next()) {
                        cantidad = rs5.getString("Cantidad");
                        tipo = rs5.getString("Material");
                    }
                    datos[2] = cantidad;
                    datos[3] = tipo;
                    datos[4] = "0";
                    datos[5] = rs2.getString("Cronometro");
                    datos[6] = rs2.getString("Empleado");
                    miModelo.addRow(datos);
                }
                Statement st6 = con.createStatement();
                String sql6 = "select * from torno where Terminado like 'NO'";
                ResultSet rs6 = st6.executeQuery(sql6);
                while (rs6.next()) {
                    datos1[4] = rs6.getString("FechaFinal");
                    datos[0] = rs6.getString("Plano");
                    datos[1] = rs6.getString("Proyecto");
                    String sql4 = "select Plano, Cantidad, Material from planos where Plano like '" + datos[1] + "'";
                    String cantidad = "", tipo = "";
                    Statement st5 = con.createStatement();
                    ResultSet rs5 = st5.executeQuery(sql4);
                    while (rs5.next()) {
                        cantidad = rs5.getString("Cantidad");
                        tipo = rs5.getString("Material");
                    }
                    datos[2] = cantidad;
                    datos[3] = tipo;
                    datos[4] = "0";
                    datos[5] = rs6.getString("Cronometro");
                    datos[6] = rs6.getString("Empleado");
                    miModelo.addRow(datos);
                }
                Statement st7 = con.createStatement();
                String sql7 = "select * from cnc where Terminado like 'NO'";
                ResultSet rs7 = st7.executeQuery(sql7);
                while (rs7.next()) {
                    datos1[4] = rs7.getString("FechaFinal");
                    datos[0] = rs7.getString("Plano");
                    datos[1] = rs7.getString("Proyecto");
                    String sql4 = "select Plano, Cantidad, Material from planos where Plano like '" + datos[1] + "'";
                    String cantidad = "", tipo = "";
                    Statement st5 = con.createStatement();
                    ResultSet rs5 = st5.executeQuery(sql4);
                    while (rs5.next()) {
                        cantidad = rs5.getString("Cantidad");
                        tipo = rs5.getString("Material");
                    }
                    datos[2] = cantidad;
                    datos[3] = tipo;
                    datos[4] = "0";
                    datos[5] = rs7.getString("Cronometro");
                    datos[6] = rs7.getString("Empleado");
                    miModelo.addRow(datos);
                }
            } else if (btnCurso.isSelected()) {
                //---------------------------------------------------------------
                //---------------------------------------------------------------
                //------------------------planos en curso----------------------------
                //---------------------------------------------------------------
                //---------------------------------------------------------------
                String sql3 = "select * from fresadora where FechaInicio != '' and FechaFinal like '' and Terminado like 'NO'";
                ResultSet rs3 = st3.executeQuery(sql3);
                while (rs3.next()) {
                    datos1[4] = rs3.getString("FechaFinal");
                    datos[0] = rs3.getString("Plano");
                    datos[1] = rs3.getString("Proyecto");
                    String sql4 = "select Plano, Cantidad, Material from planos where Plano like '" + datos[1] + "'";
                    String cantidad = "", tipo = "";
                    Statement st5 = con.createStatement();
                    ResultSet rs5 = st5.executeQuery(sql4);
                    while (rs5.next()) {
                        cantidad = rs5.getString("Cantidad");
                        tipo = rs5.getString("Material");
                    }
                    datos[2] = cantidad;
                    datos[3] = tipo;
                    datos[4] = "0";
                    datos[5] = rs3.getString("Cronometro");
                    datos[6] = rs3.getString("Empleado");
                    miModelo.addRow(datos);
                }

                Statement st6 = con.createStatement();
                String sql6 = "select * from torno where FechaInicio != '' and FechaFinal like '' and Terminado like 'NO'";
                ResultSet rs6 = st6.executeQuery(sql6);
                while (rs6.next()) {
                    datos1[4] = rs6.getString("FechaFinal");
                    datos[0] = rs6.getString("Plano");
                    datos[1] = rs6.getString("Proyecto");
                    String sql4 = "select Plano, Cantidad, Material from planos where Plano like '" + datos[1] + "'";
                    String cantidad = "", tipo = "";
                    Statement st5 = con.createStatement();
                    ResultSet rs5 = st5.executeQuery(sql4);
                    while (rs5.next()) {
                        cantidad = rs5.getString("Cantidad");
                        tipo = rs5.getString("Material");
                    }
                    datos[2] = cantidad;
                    datos[3] = tipo;
                    datos[4] = "0";
                    datos[5] = rs6.getString("Cronometro");
                    datos[6] = rs6.getString("Empleado");
                    miModelo.addRow(datos);
                }
                Statement st7 = con.createStatement();
                String sql7 = "select * from cnc where FechaInicio != '' and FechaFinal like '' and Terminado like 'NO'";
                ResultSet rs7 = st7.executeQuery(sql7);
                while (rs7.next()) {
                    datos1[4] = rs7.getString("FechaFinal");
                    datos[0] = rs7.getString("Plano");
                    datos[1] = rs7.getString("Proyecto");
                    String sql4 = "select Plano, Cantidad, Material from planos where Plano like '" + datos[1] + "'";
                    String cantidad = "", tipo = "";
                    Statement st5 = con.createStatement();
                    ResultSet rs5 = st5.executeQuery(sql4);
                    while (rs5.next()) {
                        cantidad = rs5.getString("Cantidad");
                        tipo = rs5.getString("Material");
                    }
                    datos[2] = cantidad;
                    datos[3] = tipo;
                    datos[4] = "0";
                    datos[5] = rs7.getString("Cronometro");
                    datos[6] = rs7.getString("Empleado");
                    miModelo.addRow(datos);
                }

            } else if (btnTerminados.isSelected()) {
                //---------------------------------------------------------------
                //---------------------------------------------------------------
                //------------------------planos terminados----------------------------
                //---------------------------------------------------------------
                //---------------------------------------------------------------
                String sql3 = "select Terminado, FechaFinal, Plano, Proyecto,"
                        + "Cronometro, Empleado from fresadora where Terminado like 'SI' and FechaFinal != ''";
                ResultSet rs3 = st3.executeQuery(sql3);
                while (rs3.next()) {
                    datos1[4] = rs3.getString("FechaFinal");
                    boolean turno = false;
                    if (turno1.isSelected()) {
                        turno = true;
                    }
                    boolean sen1 = false;

                    String s = datos1[4].substring(0, 10);
                    int hor = 40;
                    try {
                        hor = Integer.parseInt(datos1[4].substring(11, 13));
                    } catch (Exception e) {
//                        System.out.println("Exception: "+e);
                    }
                    if (turno) {
                        if ((fec.equals(s) && (hor >= 7 && hor <= 17))) {
                            sen1 = true;
                        }
                    } else {
                        if ((fec.equals(s) && (hor >= 19)) || (fecSig.equals(s)) && (hor >= 19 || hor <= 6)) {
                            sen1 = true;
                        }
                    }

                    if (sen1) {

                        datos[0] = rs3.getString("Plano");
                        datos[1] = rs3.getString("Proyecto");
                        String sql4 = "select Plano, Cantidad, Material from planos where Plano like '" + datos[1] + "'";
                        String cantidad = "", tipo = "";
                        Statement st5 = con.createStatement();
                        ResultSet rs5 = st5.executeQuery(sql4);
                        while (rs5.next()) {
                            cantidad = rs5.getString("Cantidad");
                            tipo = rs5.getString("Material");
                        }
                        datos[2] = cantidad;
                        datos[3] = tipo;
                        //datos[4] = "0";
                        datos[4] = rs3.getString("Cronometro");
                        datos[5] = rs3.getString("Empleado");
                        datos[6] = rs3.getString("FechaFinal");
                        miModelo.addRow(datos);
                    }
                }

                Statement st6 = con.createStatement();
                String sql6 = "select * from torno where Terminado like 'SI' and FechaFinal != ''";
                ResultSet rs6 = st6.executeQuery(sql6);
                while (rs6.next()) {
                    datos1[4] = rs6.getString("FechaFinal");
                    boolean turno = false;
                    if (turno1.isSelected()) {
                        turno = true;
                    }
                    boolean sen1 = false;

                    String s = datos1[4].substring(0, 10);
                    int hor = 40;
                    try {
                        hor = Integer.parseInt(datos1[4].substring(11, 13));
                    } catch (Exception e) {
//                        System.out.println("Exception: "+e);
                    }
                    if (turno) {
                        if ((fec.equals(s) && (hor >= 7 && hor <= 17))) {
                            sen1 = true;
                        }
                    } else {
                        if ((fec.equals(s) && (hor >= 19)) || (fecSig.equals(s)) && (hor >= 19 || hor <= 6)) {
                            sen1 = true;
                        }
                    }

                    if (sen1) {

                        datos[0] = rs6.getString("Plano");
                        datos[1] = rs6.getString("Proyecto");
                        String sql4 = "select Plano, Cantidad, Material from planos where Plano like '" + datos[1] + "'";
                        String cantidad = "", tipo = "";
                        Statement st5 = con.createStatement();
                        ResultSet rs5 = st5.executeQuery(sql4);
                        while (rs5.next()) {
                            cantidad = rs5.getString("Cantidad");
                            tipo = rs5.getString("Material");
                        }
                        datos[2] = cantidad;
                        datos[3] = tipo;
                        //datos[4] = "0";
                        datos[4] = rs6.getString("Cronometro");
                        datos[5] = rs6.getString("Empleado");
                        datos[6] = rs6.getString("FechaFinal");
                        miModelo.addRow(datos);
                    }
                }

                Statement st7 = con.createStatement();
                String sql7 = "select * from cnc where Terminado like 'SI' and FechaFinal != ''";
                ResultSet rs7 = st7.executeQuery(sql7);
                while (rs7.next()) {
                    datos1[4] = rs7.getString("FechaFinal");
                    boolean turno = false;
                    if (turno1.isSelected()) {
                        turno = true;
                    }
                    boolean sen1 = false;

                    String s = datos1[4].substring(0, 10);
                    int hor = 40;
                    try {
                        hor = Integer.parseInt(datos1[4].substring(11, 13));
                    } catch (Exception e) {
//                        System.out.println("Exception: "+e);
                    }
                    if (turno) {
                        if ((fec.equals(s) && (hor >= 7 && hor <= 17))) {
                            sen1 = true;
                        }
                    } else {
                        if ((fec.equals(s) && (hor >= 19)) || (fecSig.equals(s)) && (hor >= 19 || hor <= 6)) {
                            sen1 = true;
                        }
                    }

                    if (sen1) {

                        datos[0] = rs7.getString("Plano");
                        datos[1] = rs7.getString("Proyecto");
                        String sql4 = "select Plano, Cantidad, Material from planos where Plano like '" + datos[1] + "'";
                        String cantidad = "", tipo = "";
                        Statement st5 = con.createStatement();
                        ResultSet rs5 = st5.executeQuery(sql4);
                        while (rs5.next()) {
                            cantidad = rs5.getString("Cantidad");
                            tipo = rs5.getString("Material");
                        }
                        datos[2] = cantidad;
                        datos[3] = tipo;
                        //datos[4] = "0";
                        datos[4] = rs7.getString("Cronometro");
                        datos[5] = rs7.getString("Empleado");
                        datos[6] = rs7.getString("FechaFinal");
                        miModelo.addRow(datos);
                    }
                }

            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "ERROR AL VER BASE DE DATOS" + e);
        } catch (ParseException ex) {
            Logger.getLogger(Reportes.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void Tabla1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Tabla1MouseClicked

    }//GEN-LAST:event_Tabla1MouseClicked

    private void TerminarPlanosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TerminarPlanosActionPerformed
        for (int i = 0; i < Tabla1.getSelectedRows().length; i++) {
            terminarPlano(Tabla1.getValueAt(Tabla1.getSelectedRows()[i], 1).toString(), numEmpleado);
        }

    }//GEN-LAST:event_TerminarPlanosActionPerformed

    private void txtProyectoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtProyectoActionPerformed
        try {
            Connection con;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            String proyecto = txtProyecto.getText();
            String sql = "select Plano, Proyecto, Integracion, Fresadora, Torno, Cnc from Planos where Proyecto like '" + proyecto + "%'";
            ResultSet rs = st.executeQuery(sql);
            int fre = 0;
            int cn = 0;
            int tor = 0;
            int planos = 0;
            while (rs.next()) {
                String fresadora = rs.getString("Fresadora");
                String torno = rs.getString("Fresadora");
                String cnc = rs.getString("Fresadora");
                int fr, c1, t1;
                try {
                    fr = Integer.parseInt(fresadora.substring(0, fresadora.indexOf("/")));
                } catch (Exception e) {
                    fr = 0;
                }
                try {
                    c1 = Integer.parseInt(cnc.substring(0, cnc.indexOf("/")));
                } catch (Exception e) {
                    c1 = 0;
                }
                try {
                    t1 = Integer.parseInt(torno.substring(0, torno.indexOf("/")));
                } catch (Exception e) {
                    t1 = 0;
                }
                fre += fr;
                cn += c1;
                tor += t1;
                if (fr == 0 && c1 == 0 && t1 == 0) {
                    planos++;
                }
            }
            JFrame f = (JFrame) JOptionPane.getFrameForComponent(this);
            ReporteHoras reporte = new ReporteHoras(f, true);
            reporte.lblCnc.setText(String.valueOf((cn / 60)) + " HRS");
            reporte.lblTorno.setText(String.valueOf((tor / 60)) + " HRS");
            reporte.lblFresa.setText(String.valueOf((fre / 60)) + " HRS");
            reporte.lblPlanos.setText(String.valueOf(planos));
            reporte.setLocationRelativeTo(f);
            reporte.setVisible(true);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "ERROR: " + e, "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_txtProyectoActionPerformed

    private void jMenuItem6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem6ActionPerformed
        JFrame f = (JFrame) JOptionPane.getFrameForComponent(this);
        ReporteScrap repo = new ReporteScrap(f, true);
        repo.setVisible(true);
    }//GEN-LAST:event_jMenuItem6ActionPerformed

    private void btnReportesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReportesActionPerformed
        // 1. Configurar Calendario para las fechas por defecto
        java.util.Calendar cal = java.util.Calendar.getInstance();

        // --- Cálculo para "HASTA" (Jueves de esta semana) ---
        // Seteamos el calendario al jueves de la semana actual
        cal.set(java.util.Calendar.DAY_OF_WEEK, java.util.Calendar.THURSDAY);
        java.util.Date fechaHasta = cal.getTime();

        // --- Cálculo para "DESDE" (Viernes de la semana pasada) ---
        // Retrocedemos 6 días desde el jueves actual para llegar al viernes anterior
        cal.add(java.util.Calendar.DAY_OF_YEAR, -6);
        java.util.Date fechaDesde = cal.getTime();

        // 2. Crear panel y selectores con las fechas calculadas
        javax.swing.JPanel panel = new javax.swing.JPanel(new java.awt.GridLayout(2, 2, 10, 10));

        // Pasamos las fechas calculadas a los constructores
        com.toedter.calendar.JDateChooser dcDesde = new com.toedter.calendar.JDateChooser(fechaDesde);
        com.toedter.calendar.JDateChooser dcHasta = new com.toedter.calendar.JDateChooser(fechaHasta);

        // Opcional: Forzar formato de fecha en el editor para evitar confusiones
        dcDesde.setDateFormatString("dd/MM/yyyy");
        dcHasta.setDateFormatString("dd/MM/yyyy");

        // --- AJUSTE DE ANCHO ---
        java.awt.Dimension dateSize = new java.awt.Dimension(150, 30); // Un poco más ancho para el formato
        dcDesde.setPreferredSize(dateSize);
        dcHasta.setPreferredSize(dateSize);

        panel.add(new javax.swing.JLabel("Desde:"));
        panel.add(dcDesde);
        panel.add(new javax.swing.JLabel("Hasta:"));
        panel.add(dcHasta);

        // 3. Mostrar el diálogo
        int result = javax.swing.JOptionPane.showConfirmDialog(null, panel,
                "Seleccione el periodo del reporte", javax.swing.JOptionPane.OK_CANCEL_OPTION);

        if (result == javax.swing.JOptionPane.OK_OPTION) {
            java.util.Date f1 = dcDesde.getDate();
            java.util.Date f2 = dcHasta.getDate();

            if (f1 == null || f2 == null) {
                javax.swing.JOptionPane.showMessageDialog(null, "Debe seleccionar ambas fechas.");
                return;
            }
            if (f1.after(f2)) {
                javax.swing.JOptionPane.showMessageDialog(null, "La fecha 'Hasta' no puede ser menor a 'Desde'.");
                return;
            }

            // Llamar al generador de reporte
            generarExcelEficiencia(f1, f2);
        }
    }//GEN-LAST:event_btnReportesActionPerformed

    // Clase interna para guardar estadísticas
    class EmpleadoStats {

        String nombre;
        int planosTrabajados = 0;
        double tiempoEstimadoTotal = 0;
        double tiempoRealTotalHoras = 0;
        double tiempoRetrabajoHoras = 0;
        int planosRechazados = 0;
        java.util.List<String> planosVistosPorMaquina = new java.util.ArrayList<>();
    }

    public void generarExcelEficiencia(Date fechaInicio, Date fechaFin) {
        // Usamos un mapa para agrupar todo por el ID del empleado (limpio)
        // Mapa para rastrear: <Nombre del Plano, ID del Empleado que lo hizo>
        Map<String, Set<String>> rastreoResponsables = new HashMap<>();
        Map<String, EmpleadoStats> mapaEmpleados = new HashMap<>();
        
        // Listas para la Hoja 2
        List<String[]> datosProduccionDetalle = new ArrayList<>(); // [Plano, Nombre, Fecha]
        List<String[]> datosScrapDetalle = new ArrayList<>();      // [Plano, Nombre, Fecha, Comentarios]

        // Formato de fecha para Excel (Día/Mes/Año)
        SimpleDateFormat sdfExcel = new SimpleDateFormat("dd/MM/yyyy");
        // Formato para leer lo que viene de la BD
        SimpleDateFormat sdfSqlEntrada = new SimpleDateFormat("yyyy-MM-dd");
        
        SimpleDateFormat sdfSql = new SimpleDateFormat("dd/MM/yyyy");
        String f1 = sdfSql.format(fechaInicio);
        String f2 = sdfSql.format(fechaFin);

        String[] tablas = {"fresadora", "torno", "cnc"};

        try {
            Conexion con1 = new Conexion();
            Connection con = con1.getConnection();
            Map<String, String> cacheNombres = new HashMap<>();
            
            for (String tabla : tablas) {
                // Consulta usando STR_TO_DATE para manejar los VARCHAR de la base de datos
                String sql = "SELECT Proyecto, Cronometro, Empleado, FechaFinal FROM " + tabla + 
                    " WHERE STR_TO_DATE(FechaFinal, '%d/%m/%Y') BETWEEN STR_TO_DATE(?, '%d/%m/%Y') AND STR_TO_DATE(?, '%d/%m/%Y')" +
                    " AND CAST(Empleado AS UNSIGNED) > 0";
                PreparedStatement pst = con.prepareStatement(sql);
                pst.setString(1, f1);
                pst.setString(2, f2);
                ResultSet rs = pst.executeQuery();

                while (rs.next()) {
                    String idEmp = rs.getString("Empleado").trim(); // <--- SOLUCIÓN " 96"
                    String nPlano = rs.getString("Proyecto").trim(); //En las 3 tablas, "Proyecto" realmente corresponde al plano
                    String tiempoStr = rs.getString("Cronometro"); 
                    String fechaRaw = rs.getString("FechaFinal"); // Viene como "19/01/2026 08:38:25"
                    // Limpiar fecha (quitar la hora)
                    String fechaLimpia = (fechaRaw != null && fechaRaw.contains(" ")) ? fechaRaw.split(" ")[0] : fechaRaw;

                    // Llenamos la lista para la Hoja 2
                    rastreoResponsables.computeIfAbsent(nPlano, k -> new HashSet<>()).add(idEmp);
                    
                    if (!mapaEmpleados.containsKey(idEmp)) {
                        EmpleadoStats es = new EmpleadoStats();

                        // 1. Usamos PreparedStatement para mayor seguridad y limpieza
                        String sqlNom = "SELECT Nombre, Apellido FROM registroempleados WHERE numempleado = ?";

                        try (PreparedStatement pstNombre = con.prepareStatement(sqlNom)) {
                            pstNombre.setString(1, idEmp);

                            try (ResultSet rsNom = pstNombre.executeQuery()) {
                                if (rsNom.next()) {
                                    String nombreBD = rsNom.getString("Nombre");
                                    String apellidoBD = rsNom.getString("Apellido");

                                    // 2. Aplicamos el split para obtener solo el PRIMER nombre y PRIMER apellido
                                    // "\\s+" detecta uno o más espacios, previniendo errores si hay espacios dobles
                                    String primerNombre = (nombreBD != null && !nombreBD.trim().isEmpty()) 
                                                          ? nombreBD.trim().split("\\s+")[0] : "";

                                    String primerApellido = (apellidoBD != null && !apellidoBD.trim().isEmpty()) 
                                                            ? apellidoBD.trim().split("\\s+")[0] : "";

                                    // 3. Formato final: "Apellido Nombre"
                                    es.nombre = (primerApellido + " " + primerNombre).trim();

                                    if (es.nombre.isEmpty()) es.nombre = "Sin Nombre (" + idEmp + ")";

                                } else {
                                    es.nombre = "No. Empleado desconocido: " + idEmp;
                                }
                            }
                        } catch (SQLException e) {
                            es.nombre = "Error (" + idEmp + ")";
                            // Opcional: imprimir el error para depuración
                            // e.printStackTrace(); 
                        }

                        mapaEmpleados.put(idEmp, es);

                        // 4. Guardamos también en el caché de nombres para usarlo en la Hoja 2 (Scrap)
                        cacheNombres.put(idEmp, es.nombre);
                    }

                    String nombreEmp = mapaEmpleados.get(idEmp).nombre;
                    datosProduccionDetalle.add(new String[]{tabla.toUpperCase(), nombreEmp, nPlano, fechaLimpia});
                    
                    EmpleadoStats stats = mapaEmpleados.get(idEmp);
                    double horasActuales = convertirTiempoADecimal(tiempoStr); // <--- SOLUCIÓN "1:30"

                    // 1. SIEMPRE sumamos el plano trabajado (aunque sea repetido)
                    stats.planosTrabajados++;

                    // 2. Lógica de Tiempos y Retrabajo
                    String clave = nPlano + "_" + tabla;
                    if (stats.planosVistosPorMaquina.contains(clave)) {
                        // Si ya existe, es tiempo de retrabajo
                        stats.tiempoRetrabajoHoras += horasActuales;
                    } else {
                        // Si es la PRIMERA VEZ que lo vemos:
                        stats.planosVistosPorMaquina.add(clave); // Lo registramos

                        // --- Obtener tiempo estimado DINÁMICO ---
                        // Necesitamos el ID del plano para buscar en la nueva tabla tiempos_planos
                        String sqlIdPlano = "SELECT Id FROM Planos WHERE Plano = ?";
                        try (PreparedStatement pstId = con.prepareStatement(sqlIdPlano)) {
                            pstId.setString(1, nPlano);
                            try (ResultSet rsId = pstId.executeQuery()) {
                                if (rsId.next()) {
                                    int idDelPlano = rsId.getInt("Id");

                                    // Ahora buscamos el tiempo en la tabla normalizada para ESTA máquina
                                    // columnaTiempo ya contiene "TeFresa", "TeTorno" o "TeCnc" según los alias del SQL
                                    String sqlEstimado = "SELECT tiempo_estimado FROM tiempos_planos " +
                                                         "WHERE id_plano = ? AND id_maquina = ?";

                                    try (PreparedStatement pstEst = con.prepareStatement(sqlEstimado)) {
                                        pstEst.setInt(1, idDelPlano);

                                        // Mapeamos el nombre de la tabla al nombre de la maquina en tiempos_planos
                                        int idMaquina = 0;
                                        if (tabla.equalsIgnoreCase("fresadora")) idMaquina = 1;
                                        else if (tabla.equalsIgnoreCase("torno")) idMaquina = 2;
                                        else if (tabla.equalsIgnoreCase("cnc")) idMaquina = 3;

                                        pstEst.setInt(2, idMaquina);

                                        try (ResultSet rsEst = pstEst.executeQuery()) {
                                            if (rsEst.next()) {
                                                String tiempoEstStr = rsEst.getString("tiempo_estimado");
                                                if (tiempoEstStr != null && !tiempoEstStr.trim().isEmpty()) {
                                                    stats.tiempoEstimadoTotal += convertirTiempoADecimal(tiempoEstStr);
                                                    String nombreMaquina = "";
                                                    if (idMaquina == 1){
                                                        nombreMaquina = "Fresadora";
                                                    }
                                                    if (idMaquina == 2){
                                                        nombreMaquina = "Torno";
                                                    }
                                                    if (idMaquina == 3){
                                                        nombreMaquina = "CNC";
                                                    }
                                                    System.out.println("Plano: " + nPlano + " | Máquina: " + nombreMaquina + " | Tiempo: " + tiempoEstStr);
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        } catch (SQLException e) {
                            System.err.println("Error al obtener tiempo estimado para " + nPlano + ": " + e.getMessage());
                        }
                    }
                    stats.tiempoRealTotalHoras += horasActuales;
                }
            }
            
            // --- PROCESAR SCRAP (Cruce por Plano/Proyecto) ---
            String sqlScrap = "SELECT Proyecto, Fecha, Comentarios FROM scrap WHERE Fecha BETWEEN ? AND ?";
            try (PreparedStatement psScrap = con.prepareStatement(sqlScrap)) {
                psScrap.setDate(1, new java.sql.Date(fechaInicio.getTime()));
                psScrap.setDate(2, new java.sql.Date(fechaFin.getTime()));
                ResultSet rsS = psScrap.executeQuery();

                while (rsS.next()) {
                    String planoScrap = rsS.getString("Proyecto");
                    // Obtener la fecha como objeto Date de SQL
                    java.sql.Date fechaDB = rsS.getDate("Fecha");
                    String fechaS = sdfSql.format(fechaDB);
                    String coment = rsS.getString("Comentarios");

                    // BUSCAR EMPLEADO RESPONSABLE
                    // Si no existe, se pone "Sin asignar"
                    Set<String> idsResponsables = rastreoResponsables.get(planoScrap);
                    if (idsResponsables != null) {
                        for (String id : idsResponsables) {
                            String nombreS;
                            if (mapaEmpleados.containsKey(id)) {
                                nombreS = mapaEmpleados.get(id).nombre;
                                mapaEmpleados.get(id).planosRechazados++;
                            } else {
                                nombreS = "No. Empleado desconocido: " + id;
                            }
                            datosScrapDetalle.add(new String[] {nombreS, planoScrap, fechaS, coment});
                        }
                    } else {
                        datosScrapDetalle.add(new String[]{"No encontrado", planoScrap, fechaS, coment});
                    }
                }
            }

            datosProduccionDetalle.sort((a, b) -> {
                int cmp = a[0].compareTo(b[0]); // Máquina
                if (cmp != 0) return cmp;

                cmp = a[1].compareTo(b[1]); // Empleado
                if (cmp != 0) return cmp;

                return a[3].compareTo(b[3]); // Fecha
            });
            
            exportarAExcel(mapaEmpleados, datosProduccionDetalle, datosScrapDetalle, f1, f2);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al procesar datos: " + e.getMessage());
        }
    }

    // Función auxiliar para el formato inconsistente de tiempo
    private double convertirTiempoADecimal(String t) {
        if (t == null || !t.contains(":")) {
            return 0;
        }
        try {
            String[] partes = t.split(":");
            double h = Double.parseDouble(partes[0].trim());
            double m = Double.parseDouble(partes[1].trim());
            return h + (m / 60.0);
        } catch (Exception e) {
            return 0;
        }
    }
    
    private void exportarAExcel(Map<String, EmpleadoStats> datos, List<String[]> produccion, List<String[]> scrap, String f1, String f2) {
        org.apache.poi.xssf.usermodel.XSSFWorkbook workbook = new org.apache.poi.xssf.usermodel.XSSFWorkbook();
        org.apache.poi.xssf.usermodel.XSSFSheet sheet = workbook.createSheet("EFICIENCIA Y DEFICIENCIA");
        XSSFSheet sheet2 = workbook.createSheet("PLANOS TRABAJADOS");
        // --- 1. ESTILOS ---
        org.apache.poi.ss.usermodel.CellStyle dataStyle = workbook.createCellStyle();
        dataStyle.setBorderTop(org.apache.poi.ss.usermodel.BorderStyle.THIN);
        dataStyle.setBorderBottom(org.apache.poi.ss.usermodel.BorderStyle.THIN);
        dataStyle.setBorderLeft(org.apache.poi.ss.usermodel.BorderStyle.THIN);
        dataStyle.setBorderRight(org.apache.poi.ss.usermodel.BorderStyle.THIN);
        dataStyle.setAlignment(org.apache.poi.ss.usermodel.HorizontalAlignment.CENTER);

        org.apache.poi.ss.usermodel.CellStyle pctStyle = workbook.createCellStyle();
        pctStyle.cloneStyleFrom(dataStyle);
        pctStyle.setDataFormat(workbook.createDataFormat().getFormat("0%"));

        org.apache.poi.ss.usermodel.Font fontBold = workbook.createFont();
        fontBold.setBold(true);

        org.apache.poi.ss.usermodel.CellStyle headerBase = workbook.createCellStyle();
        headerBase.cloneStyleFrom(dataStyle);
        headerBase.setFont(fontBold);
        headerBase.setWrapText(true);
        headerBase.setVerticalAlignment(org.apache.poi.ss.usermodel.VerticalAlignment.CENTER);

        // Estilo Gris para Totales
        org.apache.poi.ss.usermodel.CellStyle styleGris = workbook.createCellStyle();
        styleGris.cloneStyleFrom(headerBase);
        styleGris.setFillForegroundColor(org.apache.poi.ss.usermodel.IndexedColors.GREY_25_PERCENT.getIndex());
        styleGris.setFillPattern(org.apache.poi.ss.usermodel.FillPatternType.SOLID_FOREGROUND);

        org.apache.poi.ss.usermodel.CellStyle pctStyleGris = workbook.createCellStyle();
        pctStyleGris.cloneStyleFrom(styleGris);
        pctStyleGris.setDataFormat(workbook.createDataFormat().getFormat("0%"));

        // Colores de encabezados
        org.apache.poi.ss.usermodel.CellStyle styleAzul = workbook.createCellStyle();
        styleAzul.cloneStyleFrom(headerBase);
        styleAzul.setFillForegroundColor(org.apache.poi.ss.usermodel.IndexedColors.PALE_BLUE.getIndex());
        styleAzul.setFillPattern(org.apache.poi.ss.usermodel.FillPatternType.SOLID_FOREGROUND);

        org.apache.poi.xssf.usermodel.XSSFColor colorNaranja = new org.apache.poi.xssf.usermodel.XSSFColor(new java.awt.Color(248, 203, 173), null);
        org.apache.poi.xssf.usermodel.XSSFCellStyle styleNaranja = workbook.createCellStyle();
        styleNaranja.cloneStyleFrom(headerBase);
        styleNaranja.setFillForegroundColor(colorNaranja);
        styleNaranja.setFillPattern(org.apache.poi.ss.usermodel.FillPatternType.SOLID_FOREGROUND);

        org.apache.poi.ss.usermodel.CellStyle styleAmarillo = workbook.createCellStyle();
        styleAmarillo.cloneStyleFrom(headerBase);
        styleAmarillo.setFillForegroundColor(org.apache.poi.ss.usermodel.IndexedColors.YELLOW.getIndex());
        styleAmarillo.setFillPattern(org.apache.poi.ss.usermodel.FillPatternType.SOLID_FOREGROUND);

        // --- 2. ENCABEZADOS ---
        String[] headers = {
            "Nombre de empleado", "Cant. Planos trabajados", "Tiempo estimado (hrs)",
            "Tiempo real (hrs)", "Tiempo Retrabajo (hrs)", "Eficiencia horas",
            "Deficiencia horas", "Planos Rechazados por calidad", "Total de planos",
            "% Rechazo", "Eficiencia Planos (%)", "Eficiencia Total", "Comentarios"
        };

        org.apache.poi.ss.usermodel.Row rowHeader = sheet.createRow(1);
        rowHeader.setHeightInPoints(45);

        for (int i = 0; i < headers.length; i++) {
            org.apache.poi.ss.usermodel.Cell cell = rowHeader.createCell(i + 1);
            cell.setCellValue(headers[i]);
            if (i >= 9 && i <= 11) {
                cell.setCellStyle(styleNaranja);
            } else if (i == 12) {
                cell.setCellStyle(styleAmarillo);
            } else {
                cell.setCellStyle(styleAzul);
            }
        }

        // --- 3. DATOS ---
        int rowNum = 2;
        int firstDataRow = 3;
        for (EmpleadoStats emp : datos.values()) {
            org.apache.poi.ss.usermodel.Row row = sheet.createRow(rowNum++);
            int r = rowNum; // Índice de fila para las fórmulas de Excel

            row.createCell(1).setCellValue(emp.nombre);
            row.getCell(1).setCellStyle(dataStyle);

            row.createCell(2).setCellValue(emp.planosTrabajados);
            row.getCell(2).setCellStyle(dataStyle);

            row.createCell(3).setCellValue(emp.tiempoEstimadoTotal);
            row.getCell(3).setCellStyle(dataStyle);

            row.createCell(4).setCellValue(emp.tiempoRealTotalHoras);
            row.getCell(4).setCellStyle(dataStyle);

            row.createCell(5).setCellValue(emp.tiempoRetrabajoHoras);
            row.getCell(5).setCellStyle(dataStyle);

            // G: Eficiencia horas (Limitado a 100% con MIN)
            org.apache.poi.ss.usermodel.Cell cellG = row.createCell(6);
            cellG.setCellFormula("MIN(1, IFERROR(D" + r + "/(E" + r + "+F" + r + "), 0))");
            cellG.setCellStyle(pctStyle);

            // H: Deficiencia horas (Siempre será >= 0 porque G <= 1)
            org.apache.poi.ss.usermodel.Cell cellH = row.createCell(7);
            cellH.setCellFormula("1-G" + r);
            cellH.setCellStyle(pctStyle);

            row.createCell(8).setCellValue(emp.planosRechazados);
            row.getCell(8).setCellStyle(dataStyle);

            // J: Total de planos
            row.createCell(9).setCellFormula("C" + r + "+I" + r);
            row.getCell(9).setCellStyle(dataStyle);

            // K: % Rechazo
            row.createCell(10).setCellFormula("IFERROR(I" + r + "/J" + r + ", 0)");
            row.getCell(10).setCellStyle(pctStyle);

            // L: Eficiencia Planos (%) (Limitado a 100% con MIN)
            org.apache.poi.ss.usermodel.Cell cellL = row.createCell(11);
            cellL.setCellFormula("MIN(1, IFERROR(C" + r + "/J" + r + ", 0))");
            cellL.setCellStyle(pctStyle);

            // M: Eficiencia Total
            row.createCell(12).setCellFormula("(IFERROR((D" + r + "/E" + r + ")*(C" + r + "-I" + r + ")/C" + r + ", 0))");
            row.getCell(12).setCellStyle(pctStyle);

            row.createCell(13).setCellValue("");
            row.getCell(13).setCellStyle(dataStyle);
        }

        // --- 4. FILA DE TOTALES / PROMEDIO ---
        org.apache.poi.ss.usermodel.Row rowTotal = sheet.createRow(rowNum);
        int lastDataRow = rowNum;
        int rT = rowNum + 1;

        org.apache.poi.ss.usermodel.Cell cellSumPlanos = rowTotal.createCell(2);
        cellSumPlanos.setCellFormula("SUM(C" + firstDataRow + ":C" + lastDataRow + ")");
        cellSumPlanos.setCellStyle(styleGris);

        org.apache.poi.ss.usermodel.Cell cellTxtPromedio = rowTotal.createCell(4);
        cellTxtPromedio.setCellValue("PROMEDIO");
        cellTxtPromedio.setCellStyle(styleGris);

        // Promedio Eficiencia (G): Aquí no usamos MIN porque es un promedio de valores ya limitados
        org.apache.poi.ss.usermodel.Cell cellAvgEficiencia = rowTotal.createCell(6);
        cellAvgEficiencia.setCellFormula("IFERROR(AVERAGE(G" + firstDataRow + ":G" + lastDataRow + "), 0)");
        cellAvgEficiencia.setCellStyle(pctStyleGris);

        // Deficiencia Total (H): Usamos MAX(0, ...) para asegurar que nunca sea negativo
        org.apache.poi.ss.usermodel.Cell cellDefTotal = rowTotal.createCell(7);
        cellDefTotal.setCellFormula("MAX(0, 1-G" + rT + ")");
        cellDefTotal.setCellStyle(pctStyleGris);

        org.apache.poi.ss.usermodel.Cell cellSumRechazados = rowTotal.createCell(8);
        cellSumRechazados.setCellFormula("SUM(I" + firstDataRow + ":I" + lastDataRow + ")");
        cellSumRechazados.setCellStyle(styleGris);

        // --- 5. FORMATO CONDICIONAL (SEMÁFORO) ---
        org.apache.poi.xssf.usermodel.XSSFSheetConditionalFormatting sheetCF = sheet.getSheetConditionalFormatting();

        // Definimos los rangos para G (Eficiencia) y H (Deficiencia)
        String rangeG = "G3:G" + lastDataRow;
        String rangeH = "H3:H" + lastDataRow;

        org.apache.poi.ss.util.CellRangeAddress[] regionsG = {org.apache.poi.ss.util.CellRangeAddress.valueOf(rangeG)};
        org.apache.poi.ss.util.CellRangeAddress[] regionsH = {org.apache.poi.ss.util.CellRangeAddress.valueOf(rangeH)};

        // Colores
        org.apache.poi.xssf.usermodel.XSSFColor colorVerde = new org.apache.poi.xssf.usermodel.XSSFColor(new java.awt.Color(0, 176, 80), null);
        org.apache.poi.xssf.usermodel.XSSFColor colorAmarillo = new org.apache.poi.xssf.usermodel.XSSFColor(new java.awt.Color(255, 255, 0), null);
        org.apache.poi.xssf.usermodel.XSSFColor colorRojo = new org.apache.poi.xssf.usermodel.XSSFColor(new java.awt.Color(255, 0, 0), null);

        // --- REGLAS PARA EFICIENCIA (G) ---
        // Verde (>= 91%)
        org.apache.poi.xssf.usermodel.XSSFConditionalFormattingRule ruleG_Verde = (org.apache.poi.xssf.usermodel.XSSFConditionalFormattingRule) sheetCF.createConditionalFormattingRule(org.apache.poi.ss.usermodel.ComparisonOperator.GE, "0.90");
        ruleG_Verde.createPatternFormatting().setFillBackgroundColor(colorVerde);

        // Amarillo (70% - 90%)
        org.apache.poi.xssf.usermodel.XSSFConditionalFormattingRule ruleG_Amarillo = (org.apache.poi.xssf.usermodel.XSSFConditionalFormattingRule) sheetCF.createConditionalFormattingRule(org.apache.poi.ss.usermodel.ComparisonOperator.BETWEEN, "0.70", "0.899");
        ruleG_Amarillo.createPatternFormatting().setFillBackgroundColor(colorAmarillo);

        // Rojo (< 70%)
        org.apache.poi.xssf.usermodel.XSSFConditionalFormattingRule ruleG_Rojo = (org.apache.poi.xssf.usermodel.XSSFConditionalFormattingRule) sheetCF.createConditionalFormattingRule(org.apache.poi.ss.usermodel.ComparisonOperator.LT, "0.70");
        ruleG_Rojo.createPatternFormatting().setFillBackgroundColor(colorRojo);

        sheetCF.addConditionalFormatting(regionsG, ruleG_Verde);
        sheetCF.addConditionalFormatting(regionsG, ruleG_Amarillo);
        sheetCF.addConditionalFormatting(regionsG, ruleG_Rojo);

        // --- REGLAS PARA DEFICIENCIA (H) ---
        // Aquí la lógica es inversa: 
        // Verde (<= 9%)
        org.apache.poi.xssf.usermodel.XSSFConditionalFormattingRule ruleH_Verde = (org.apache.poi.xssf.usermodel.XSSFConditionalFormattingRule) sheetCF.createConditionalFormattingRule(org.apache.poi.ss.usermodel.ComparisonOperator.LE, "0.10");
        ruleH_Verde.createPatternFormatting().setFillBackgroundColor(colorVerde);

        // Amarillo (10% - 30%)
        org.apache.poi.xssf.usermodel.XSSFConditionalFormattingRule ruleH_Amarillo = (org.apache.poi.xssf.usermodel.XSSFConditionalFormattingRule) sheetCF.createConditionalFormattingRule(org.apache.poi.ss.usermodel.ComparisonOperator.BETWEEN, "0.101", "0.30");
        ruleH_Amarillo.createPatternFormatting().setFillBackgroundColor(colorAmarillo);

        // Rojo (> 30%)
        org.apache.poi.xssf.usermodel.XSSFConditionalFormattingRule ruleH_Rojo = (org.apache.poi.xssf.usermodel.XSSFConditionalFormattingRule) sheetCF.createConditionalFormattingRule(org.apache.poi.ss.usermodel.ComparisonOperator.GT, "0.30");
        ruleH_Rojo.createPatternFormatting().setFillBackgroundColor(colorRojo);

        sheetCF.addConditionalFormatting(regionsH, ruleH_Verde);
        sheetCF.addConditionalFormatting(regionsH, ruleH_Amarillo);
        sheetCF.addConditionalFormatting(regionsH, ruleH_Rojo);

        // --- 6. GENERACIÓN DE GRÁFICAS ---
        org.apache.poi.xssf.usermodel.XSSFDrawing drawing = sheet.createDrawingPatriarch();
        int startChartRow = rowNum + 2;

        // --- GRÁFICA 1: EFICIENCIA Y DEFICIENCIA EN TIEMPO (Columnas G y H) ---
        org.apache.poi.xssf.usermodel.XSSFClientAnchor anchor1 = drawing.createAnchor(0, 0, 0, 0, 1, startChartRow, 7, startChartRow + 15);
        org.apache.poi.xssf.usermodel.XSSFChart chart1 = drawing.createChart(anchor1);
        chart1.setTitleText("EFICIENCIA Y DEFICIENCIA EN TIEMPO");
        chart1.setTitleOverlay(false);

        org.apache.poi.xddf.usermodel.chart.XDDFChartLegend legend1 = chart1.getOrAddLegend();
        legend1.setPosition(org.apache.poi.xddf.usermodel.chart.LegendPosition.BOTTOM);

        // Ejes
        org.apache.poi.xddf.usermodel.chart.XDDFCategoryAxis bottomAxis1 = chart1.createCategoryAxis(org.apache.poi.xddf.usermodel.chart.AxisPosition.BOTTOM);
        org.apache.poi.xddf.usermodel.chart.XDDFValueAxis leftAxis1 = chart1.createValueAxis(org.apache.poi.xddf.usermodel.chart.AxisPosition.LEFT);
        leftAxis1.setCrosses(org.apache.poi.xddf.usermodel.chart.AxisCrosses.AUTO_ZERO);
        leftAxis1.setMaximum(1.2);
        bottomAxis1.crossAxis(leftAxis1);
        leftAxis1.crossAxis(bottomAxis1);

        // Datos
        org.apache.poi.xddf.usermodel.chart.XDDFDataSource<String> nombres = org.apache.poi.xddf.usermodel.chart.XDDFDataSourcesFactory.fromStringCellRange(sheet, new org.apache.poi.ss.util.CellRangeAddress(2, lastDataRow - 1, 1, 1));
        org.apache.poi.xddf.usermodel.chart.XDDFNumericalDataSource<Double> eficiencias = org.apache.poi.xddf.usermodel.chart.XDDFDataSourcesFactory.fromNumericCellRange(sheet, new org.apache.poi.ss.util.CellRangeAddress(2, lastDataRow - 1, 6, 6));
        org.apache.poi.xddf.usermodel.chart.XDDFNumericalDataSource<Double> deficiencias = org.apache.poi.xddf.usermodel.chart.XDDFDataSourcesFactory.fromNumericCellRange(sheet, new org.apache.poi.ss.util.CellRangeAddress(2, lastDataRow - 1, 7, 7));

        org.apache.poi.xddf.usermodel.chart.XDDFBarChartData bar1 = (org.apache.poi.xddf.usermodel.chart.XDDFBarChartData) chart1.createData(org.apache.poi.xddf.usermodel.chart.ChartTypes.BAR, bottomAxis1, leftAxis1);
        bar1.setBarDirection(org.apache.poi.xddf.usermodel.chart.BarDirection.COL);
        bar1.setBarGrouping(org.apache.poi.xddf.usermodel.chart.BarGrouping.STACKED);
        bar1.setOverlap((byte) 100);

        // ORDEN INVERTIDO: Primero Deficiencia (abajo), luego Eficiencia (arriba)
        org.apache.poi.xddf.usermodel.chart.XDDFBarChartData.Series seriesDef = (org.apache.poi.xddf.usermodel.chart.XDDFBarChartData.Series) bar1.addSeries(nombres, deficiencias);
        seriesDef.setTitle("DEFICIENCIA", null);

        org.apache.poi.xddf.usermodel.chart.XDDFBarChartData.Series seriesEf = (org.apache.poi.xddf.usermodel.chart.XDDFBarChartData.Series) bar1.addSeries(nombres, eficiencias);
        seriesEf.setTitle("EFICIENCIA", null);

        chart1.plot(bar1);

        // --- ETIQUETAS GRÁFICA 1 ---
        org.openxmlformats.schemas.drawingml.x2006.chart.CTBarSer ser1_0 = chart1.getCTChart().getPlotArea().getBarChartArray(0).getSerArray(0);
        org.openxmlformats.schemas.drawingml.x2006.chart.CTDLbls lbls1_0 = ser1_0.isSetDLbls() ? ser1_0.getDLbls() : ser1_0.addNewDLbls();
        lbls1_0.addNewShowVal().setVal(true);
        org.openxmlformats.schemas.drawingml.x2006.main.CTTextBody txPr1_0 = lbls1_0.addNewTxPr();
        txPr1_0.addNewBodyPr();
        txPr1_0.addNewP().addNewPPr().addNewDefRPr().setB(true);
        lbls1_0.addNewShowSerName().setVal(false);
        lbls1_0.addNewShowCatName().setVal(false);
        lbls1_0.addNewShowLegendKey().setVal(false); // No mostrar el cuadrito de color

        // Serie 1 (Ahora es Eficiencia)
        org.openxmlformats.schemas.drawingml.x2006.chart.CTBarSer ser1_1 = chart1.getCTChart().getPlotArea().getBarChartArray(0).getSerArray(1);
        org.openxmlformats.schemas.drawingml.x2006.chart.CTDLbls lbls1_1 = ser1_1.isSetDLbls() ? ser1_1.getDLbls() : ser1_1.addNewDLbls();
        lbls1_1.addNewShowVal().setVal(true);
        org.openxmlformats.schemas.drawingml.x2006.main.CTTextBody txPr1_1 = lbls1_1.addNewTxPr();
        txPr1_1.addNewBodyPr();
        txPr1_1.addNewP().addNewPPr().addNewDefRPr().setB(true);
        lbls1_1.addNewShowSerName().setVal(false);
        lbls1_1.addNewShowCatName().setVal(false);
        lbls1_1.addNewShowLegendKey().setVal(false); // No mostrar el cuadrito de color

        // --- COLORES GRÁFICA 1 ---
        // Serie 0: Deficiencia (Rojo)
        ser1_0.addNewSpPr().addNewSolidFill().addNewSrgbClr().setVal(new byte[]{(byte) 255, (byte) 0, (byte) 0});

        // Serie 1: Eficiencia (Verde)
        ser1_1.addNewSpPr().addNewSolidFill().addNewSrgbClr().setVal(new byte[]{(byte) 0, (byte) 176, (byte) 80});

        // Líneas con transparencia
        org.openxmlformats.schemas.drawingml.x2006.chart.CTValAx ctValAx1 = chart1.getCTChart().getPlotArea().getValAxArray(0);
        org.openxmlformats.schemas.drawingml.x2006.chart.CTChartLines gridLines1 = ctValAx1.isSetMajorGridlines() ? ctValAx1.getMajorGridlines() : ctValAx1.addNewMajorGridlines();

        org.openxmlformats.schemas.drawingml.x2006.main.CTLineProperties ln1 = gridLines1.addNewSpPr().addNewLn();
        ln1.addNewSolidFill().addNewSrgbClr().setVal(new byte[]{(byte) 217, (byte) 217, (byte) 217});

        // --- GRÁFICA 2: PLANOS RECHAZADOS Y EFICIENTES (Columnas K y L) ---
        // Posición: A la derecha de la Gráfica 1 (Empieza en la columna W)
        org.apache.poi.xssf.usermodel.XSSFClientAnchor anchor2 = drawing.createAnchor(0, 0, 0, 0, 8, startChartRow, 14, startChartRow + 15);
        org.apache.poi.xssf.usermodel.XSSFChart chart2 = drawing.createChart(anchor2);
        chart2.setTitleText("PLANOS RECHAZADOS Y EFICIENTES");
        chart2.setTitleOverlay(false);

        org.apache.poi.xddf.usermodel.chart.XDDFChartLegend legend2 = chart2.getOrAddLegend();
        legend2.setPosition(org.apache.poi.xddf.usermodel.chart.LegendPosition.BOTTOM);

        // Ejes
        org.apache.poi.xddf.usermodel.chart.XDDFCategoryAxis bottomAxis2 = chart2.createCategoryAxis(org.apache.poi.xddf.usermodel.chart.AxisPosition.BOTTOM);
        org.apache.poi.xddf.usermodel.chart.XDDFValueAxis leftAxis2 = chart2.createValueAxis(org.apache.poi.xddf.usermodel.chart.AxisPosition.LEFT);
        leftAxis2.setCrosses(org.apache.poi.xddf.usermodel.chart.AxisCrosses.AUTO_ZERO);
        leftAxis2.setMaximum(1.2);
        bottomAxis2.crossAxis(leftAxis2);
        leftAxis2.crossAxis(bottomAxis2);

        // Datos: % Rechazo (K = índice 10), Eficiencia Planos (L = índice 11)
        org.apache.poi.xddf.usermodel.chart.XDDFNumericalDataSource<Double> pctRechazo = org.apache.poi.xddf.usermodel.chart.XDDFDataSourcesFactory.fromNumericCellRange(sheet, new org.apache.poi.ss.util.CellRangeAddress(2, lastDataRow - 1, 10, 10));
        org.apache.poi.xddf.usermodel.chart.XDDFNumericalDataSource<Double> efiPlanos = org.apache.poi.xddf.usermodel.chart.XDDFDataSourcesFactory.fromNumericCellRange(sheet, new org.apache.poi.ss.util.CellRangeAddress(2, lastDataRow - 1, 11, 11));

        org.apache.poi.xddf.usermodel.chart.XDDFBarChartData bar2 = (org.apache.poi.xddf.usermodel.chart.XDDFBarChartData) chart2.createData(org.apache.poi.xddf.usermodel.chart.ChartTypes.BAR, bottomAxis2, leftAxis2);
        bar2.setBarDirection(org.apache.poi.xddf.usermodel.chart.BarDirection.COL);

        bar2.setBarGrouping(org.apache.poi.xddf.usermodel.chart.BarGrouping.STACKED);
        bar2.setOverlap((byte) 100);

        org.apache.poi.xddf.usermodel.chart.XDDFBarChartData.Series seriesRechazo = (org.apache.poi.xddf.usermodel.chart.XDDFBarChartData.Series) bar2.addSeries(nombres, pctRechazo);
        seriesRechazo.setTitle("RECHAZO", null);

        org.apache.poi.xddf.usermodel.chart.XDDFBarChartData.Series seriesEfiPlanos = (org.apache.poi.xddf.usermodel.chart.XDDFBarChartData.Series) bar2.addSeries(nombres, efiPlanos);
        seriesEfiPlanos.setTitle("EFICIENCIA %", null);

        chart2.plot(bar2);
        // --- ETIQUETAS GRÁFICA 2 ---
        // Serie 0 (Rechazo)
        org.openxmlformats.schemas.drawingml.x2006.chart.CTBarSer ser2_0 = chart2.getCTChart().getPlotArea().getBarChartArray(0).getSerArray(0);
        org.openxmlformats.schemas.drawingml.x2006.chart.CTDLbls lbls2_0 = ser2_0.isSetDLbls() ? ser2_0.getDLbls() : ser2_0.addNewDLbls();
        lbls2_0.addNewShowVal().setVal(true);
        org.openxmlformats.schemas.drawingml.x2006.main.CTTextBody txPr2_0 = lbls2_0.addNewTxPr();
        txPr2_0.addNewBodyPr();
        txPr2_0.addNewLstStyle();
        txPr2_0.addNewP().addNewPPr().addNewDefRPr().setB(true);

        lbls2_0.addNewShowSerName().setVal(false);
        lbls2_0.addNewShowCatName().setVal(false);
        lbls2_0.addNewShowPercent().setVal(false);   // No necesario para este tipo de gráfica
        lbls2_0.addNewShowLegendKey().setVal(false); // No mostrar el cuadrito de color

        // Serie 1 (Eficiencia %)
        org.openxmlformats.schemas.drawingml.x2006.chart.CTBarSer ser2_1 = chart2.getCTChart().getPlotArea().getBarChartArray(0).getSerArray(1);
        org.openxmlformats.schemas.drawingml.x2006.chart.CTDLbls lbls2_1 = ser2_1.isSetDLbls() ? ser2_1.getDLbls() : ser2_1.addNewDLbls();
        lbls2_1.addNewShowVal().setVal(true);
        org.openxmlformats.schemas.drawingml.x2006.main.CTTextBody txPr2_1 = lbls2_1.addNewTxPr();
        txPr2_1.addNewBodyPr();
        txPr2_1.addNewLstStyle();
        txPr2_1.addNewP().addNewPPr().addNewDefRPr().setB(true);

        lbls2_1.addNewShowSerName().setVal(false);
        lbls2_1.addNewShowCatName().setVal(false);
        lbls2_1.addNewShowPercent().setVal(false);   // No necesario para este tipo de gráfica
        lbls2_1.addNewShowLegendKey().setVal(false); // No mostrar el cuadrito de color

        // --- COLORES GRÁFICA 2 ---
        // Serie 0: Rechazo (Rojo)
        org.openxmlformats.schemas.drawingml.x2006.chart.CTBarSer ctSer2_0
                = chart2.getCTChart().getPlotArea().getBarChartArray(0).getSerArray(0);
        ctSer2_0.addNewSpPr().addNewSolidFill().addNewSrgbClr().setVal(new byte[]{(byte) 255, (byte) 0, (byte) 0});

        // Serie 1: Eficiencia % (Verde)
        org.openxmlformats.schemas.drawingml.x2006.chart.CTBarSer ctSer2_1
                = chart2.getCTChart().getPlotArea().getBarChartArray(0).getSerArray(1);
        ctSer2_1.addNewSpPr().addNewSolidFill().addNewSrgbClr().setVal(new byte[]{(byte) 0, (byte) 176, (byte) 80});

        // Líneas con transparencia
        org.openxmlformats.schemas.drawingml.x2006.chart.CTValAx ctValAx2 = chart2.getCTChart().getPlotArea().getValAxArray(0);
        org.openxmlformats.schemas.drawingml.x2006.chart.CTChartLines gridLines2 = ctValAx2.isSetMajorGridlines() ? ctValAx2.getMajorGridlines() : ctValAx2.addNewMajorGridlines();

        org.openxmlformats.schemas.drawingml.x2006.main.CTLineProperties ln2 = gridLines2.addNewSpPr().addNewLn();
        ln2.addNewSolidFill().addNewSrgbClr().setVal(new byte[]{(byte) 217, (byte) 217, (byte) 217});

        // --- GRÁFICA 3: EFICIENCIA TOTAL (Columna M - Barras Horizontales) ---
        // Posición: Abajo de las anteriores
        org.apache.poi.xssf.usermodel.XSSFClientAnchor anchor3 = drawing.createAnchor(0, 0, 0, 0, 1, startChartRow + 16, 14, startChartRow + 30);
        org.apache.poi.xssf.usermodel.XSSFChart chart3 = drawing.createChart(anchor3);
        chart3.setTitleText("EFICIENCIA TOTAL");
        chart3.setTitleOverlay(false);

        org.apache.poi.xddf.usermodel.chart.XDDFCategoryAxis catAxis3 = chart3.createCategoryAxis(org.apache.poi.xddf.usermodel.chart.AxisPosition.LEFT);
        org.apache.poi.xddf.usermodel.chart.XDDFValueAxis valAxis3 = chart3.createValueAxis(org.apache.poi.xddf.usermodel.chart.AxisPosition.BOTTOM);
        catAxis3.crossAxis(valAxis3);
        valAxis3.crossAxis(catAxis3);

        org.apache.poi.xddf.usermodel.chart.XDDFNumericalDataSource<Double> efTotal = org.apache.poi.xddf.usermodel.chart.XDDFDataSourcesFactory.fromNumericCellRange(sheet, new org.apache.poi.ss.util.CellRangeAddress(2, lastDataRow - 1, 12, 12));

        org.apache.poi.xddf.usermodel.chart.XDDFBarChartData bar3 = (org.apache.poi.xddf.usermodel.chart.XDDFBarChartData) chart3.createData(org.apache.poi.xddf.usermodel.chart.ChartTypes.BAR, catAxis3, valAxis3);
        bar3.setBarDirection(org.apache.poi.xddf.usermodel.chart.BarDirection.BAR); // Barras horizontales

        org.apache.poi.xddf.usermodel.chart.XDDFBarChartData.Series seriesTotal = (org.apache.poi.xddf.usermodel.chart.XDDFBarChartData.Series) bar3.addSeries(nombres, efTotal);
        seriesTotal.setTitle("Eficiencia Total", null);

        chart3.plot(bar3);

        // Accedemos a la serie 0 de la gráfica de barras
        org.openxmlformats.schemas.drawingml.x2006.chart.CTBarSer ctBarSer3
                = chart3.getCTChart().getPlotArea().getBarChartArray(0).getSerArray(0);

        // Si ya tiene etiquetas las quitamos para empezar de cero y evitar duplicados
        if (ctBarSer3.isSetDLbls()) {
            ctBarSer3.unsetDLbls();
        }

        org.openxmlformats.schemas.drawingml.x2006.chart.CTDLbls dLbls3 = ctBarSer3.addNewDLbls();

        // CONFIGURACIÓN CRÍTICA:
        dLbls3.addNewShowVal().setVal(true);        // SI mostrar el valor (el %)
        org.openxmlformats.schemas.drawingml.x2006.main.CTTextBody txPr3 = dLbls3.addNewTxPr();
        txPr3.addNewBodyPr();
        txPr3.addNewLstStyle();
        org.openxmlformats.schemas.drawingml.x2006.main.CTTextCharacterProperties rPr3
                = txPr3.addNewP().addNewPPr().addNewDefRPr();
        rPr3.setB(true);

        dLbls3.addNewShowSerName().setVal(false);   // NO mostrar "Eficiencia Total"
        dLbls3.addNewShowCatName().setVal(false);   // NO mostrar el nombre del empleado
        dLbls3.addNewShowPercent().setVal(false);   // No necesario para este tipo de gráfica
        dLbls3.addNewShowLegendKey().setVal(false); // No mostrar el cuadrito de color

        // --- COLOR GRÁFICA 3 (CELESTE) ---
        org.openxmlformats.schemas.drawingml.x2006.chart.CTBarSer ctSer3_0
                = chart3.getCTChart().getPlotArea().getBarChartArray(0).getSerArray(0);

        // Aplicamos el relleno sólido celeste
        org.openxmlformats.schemas.drawingml.x2006.main.CTShapeProperties sp3 = ctSer3_0.isSetSpPr() ? ctSer3_0.getSpPr() : ctSer3_0.addNewSpPr();
        org.openxmlformats.schemas.drawingml.x2006.main.CTSolidColorFillProperties fill3 = sp3.isSetSolidFill() ? sp3.getSolidFill() : sp3.addNewSolidFill();
        fill3.addNewSrgbClr().setVal(new byte[]{(byte) 0, (byte) 176, (byte) 240}); // Celeste

        org.openxmlformats.schemas.drawingml.x2006.chart.CTValAx ctValAx3 = chart3.getCTChart().getPlotArea().getValAxArray(0);
        org.openxmlformats.schemas.drawingml.x2006.chart.CTChartLines gridLines3 = ctValAx3.isSetMajorGridlines() ? ctValAx3.getMajorGridlines() : ctValAx3.addNewMajorGridlines();

        org.openxmlformats.schemas.drawingml.x2006.main.CTLineProperties ln3 = gridLines3.addNewSpPr().addNewLn();
        ln3.addNewSolidFill().addNewSrgbClr().setVal(new byte[]{(byte) 217, (byte) 217, (byte) 217});
        // Zoom de la hoja por defecto
        sheet.setZoom(71);

        // --- AJUSTES FINALES ---
        sheet.setColumnWidth(1, 40 * 256);
        for (int i = 2; i <= 13; i++) sheet.setColumnWidth(i, 15 * 256);
        
        // Crear Hoja 2
        CellStyle estiloTituloNegrita = workbook.createCellStyle();
        estiloTituloNegrita.cloneStyleFrom(dataStyle);
        estiloTituloNegrita.setFont(fontBold);
        estiloTituloNegrita.setAlignment(HorizontalAlignment.CENTER);

        int filaActual = 1; // Fila 2 (índice 1)

        // Estilo para bordes y centrado
        CellStyle estiloCelda = workbook.createCellStyle();
        estiloCelda.setBorderBottom(BorderStyle.THIN);
        estiloCelda.setBorderTop(BorderStyle.THIN);
        estiloCelda.setBorderRight(BorderStyle.THIN);
        estiloCelda.setBorderLeft(BorderStyle.THIN);
        estiloCelda.setAlignment(HorizontalAlignment.CENTER);
        estiloCelda.setVerticalAlignment(VerticalAlignment.CENTER);

        // Estilo base de encabezado (IMPORTANTE: va antes que el gris)
        CellStyle estiloEncabezado = workbook.createCellStyle();
        estiloEncabezado.cloneStyleFrom(estiloCelda);
        estiloEncabezado.setFont(fontBold);

        // Gris CLARITO (para filas)
        XSSFCellStyle estiloCeldaGrisClaro = (XSSFCellStyle) workbook.createCellStyle();
        estiloCeldaGrisClaro.cloneStyleFrom(estiloCelda);
        estiloCeldaGrisClaro.setFillForegroundColor(new XSSFColor(new java.awt.Color(242, 242, 242), null));
        estiloCeldaGrisClaro.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        // Gris encabezado (un poco más fuerte)
        XSSFCellStyle estiloEncabezadoGris = (XSSFCellStyle) workbook.createCellStyle();
        estiloEncabezadoGris.cloneStyleFrom(estiloEncabezado);
        estiloEncabezadoGris.setFillForegroundColor(new XSSFColor(new java.awt.Color(217, 217, 217), null));
        estiloEncabezadoGris.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        // Azul pálido título
        XSSFCellStyle estiloTituloAzul = (XSSFCellStyle) workbook.createCellStyle();
        estiloTituloAzul.cloneStyleFrom(estiloTituloNegrita);
        estiloTituloAzul.setFillForegroundColor(IndexedColors.PALE_BLUE.getIndex());
        estiloTituloAzul.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        // --- NUEVA SECCIÓN: TÍTULO COMBINADO B1:N1 ---
        // 1. Creamos el estilo Gris Clarito
        XSSFCellStyle estiloGrisClarito = workbook.createCellStyle();
        estiloGrisClarito.setAlignment(org.apache.poi.ss.usermodel.HorizontalAlignment.CENTER);
        estiloGrisClarito.setVerticalAlignment(org.apache.poi.ss.usermodel.VerticalAlignment.CENTER);

        // --- AGREGAR BORDES AQUÍ ---
        estiloGrisClarito.setBorderTop(org.apache.poi.ss.usermodel.BorderStyle.THIN);
        estiloGrisClarito.setBorderBottom(org.apache.poi.ss.usermodel.BorderStyle.THIN);
        estiloGrisClarito.setBorderLeft(org.apache.poi.ss.usermodel.BorderStyle.THIN);
        estiloGrisClarito.setBorderRight(org.apache.poi.ss.usermodel.BorderStyle.THIN);
        // El color por defecto es negro, así que con THIN es suficiente

        // Color gris muy claro (RGB: 242, 242, 242)
        estiloGrisClarito.setFillForegroundColor(new org.apache.poi.xssf.usermodel.XSSFColor(new java.awt.Color(242, 242, 242), null));
        estiloGrisClarito.setFillPattern(org.apache.poi.ss.usermodel.FillPatternType.SOLID_FOREGROUND);
        estiloGrisClarito.setFont(fontBold);

        // 2. Creamos la fila 0
        org.apache.poi.ss.usermodel.Row rowTitulo = sheet.createRow(0);
        rowTitulo.setHeightInPoints(25);

        // 3. Aplicamos el estilo a todas las celdas del rango B a N
        for (int i = 1; i <= 13; i++) { 
            org.apache.poi.ss.usermodel.Cell cell = rowTitulo.createCell(i);
            cell.setCellStyle(estiloGrisClarito);
        }

        // 4. Ponemos el valor
        rowTitulo.getCell(1).setCellValue("REPORTE DE EFICIENCIA DEL PERIODO: " + f1 + " AL " + f2);

        // 5. Combinamos
        sheet.addMergedRegion(new org.apache.poi.ss.util.CellRangeAddress(0, 0, 1, 13));
        
        // --- 1. SECCIÓN PLANOS TRABAJADOS ---       
        Row rowTitulo1 = sheet2.createRow(filaActual);
        Cell cellT1 = rowTitulo1.createCell(1);
        cellT1.setCellValue("Planos Trabajados (" + f1 + " - " + f2 + ")");
        cellT1.setCellStyle(estiloTituloAzul);

        //crear celdas vacías para todo el rango
        for (int c = 1; c <= 4; c++) {
            if (rowTitulo1.getCell(c) == null) {
                rowTitulo1.createCell(c).setCellStyle(estiloTituloAzul);
            }
        }

        //ahora sí merge
        CellRangeAddress region1 = new CellRangeAddress(filaActual, filaActual, 1, 4);
        sheet2.addMergedRegion(region1);

        // aplicar bordes DESPUÉS
        RegionUtil.setBorderTop(BorderStyle.THIN, region1, sheet2);
        RegionUtil.setBorderBottom(BorderStyle.THIN, region1, sheet2);
        RegionUtil.setBorderLeft(BorderStyle.THIN, region1, sheet2);
        RegionUtil.setBorderRight(BorderStyle.THIN, region1, sheet2);

        filaActual++;

        // Encabezados campos
        org.apache.poi.ss.usermodel.Row rowEnc1 = sheet2.createRow(filaActual++);
        String[] enc1 = {"Máquina", "Empleado", "Plano", "Fecha"};
        for (int i = 0; i < enc1.length; i++) {
            org.apache.poi.ss.usermodel.Cell c = rowEnc1.createCell(i + 1);
            c.setCellValue(enc1[i]);
            c.setCellStyle(estiloEncabezadoGris);
        }

        // Datos Producción
        int inicioMaquina = filaActual;
        int inicioEmpleado = filaActual;
        int inicioFecha = filaActual;

        String maquinaActual = "";
        String empleadoActual = "";
        String fechaActual = "";

        boolean colorGris = false; 
        for (int i = 0; i < produccion.size(); i++) {
            
            String[] fila = produccion.get(i);

            String maq = fila[0];
            String emp = fila[1];
            String plano = fila[2];
            String fecha = fila[3];
            
            if (!maq.equals(maquinaActual)) {
                colorGris = !colorGris; // alternar color por máquina
            }
            
            Row r = sheet2.createRow(filaActual);

            Cell cellMaquina = r.createCell(1);
            cellMaquina.setCellValue(maq);

            Cell cellEmp = r.createCell(2);
            cellEmp.setCellValue(emp);

            Cell cellPlano = r.createCell(3);
            cellPlano.setCellValue(plano);

            Cell cellFecha = r.createCell(4);
            cellFecha.setCellValue(fecha);

            // aplicar color según máquina
            CellStyle estiloActual = colorGris ? estiloCeldaGrisClaro : estiloCelda;

            cellMaquina.setCellStyle(estiloActual);
            cellEmp.setCellStyle(estiloActual);
            cellPlano.setCellStyle(estiloActual);
            cellFecha.setCellStyle(estiloActual);

            // --- CAMBIO DE MÁQUINA ---
            if (!maq.equals(maquinaActual)) {
                if (filaActual - inicioMaquina > 1) {
                    sheet2.addMergedRegion(new CellRangeAddress(inicioMaquina, filaActual - 1, 1, 1));
                }
                maquinaActual = maq;
                inicioMaquina = filaActual;
            }

            // --- CAMBIO DE EMPLEADO ---
            if (!emp.equals(empleadoActual)) {
                if (filaActual - inicioEmpleado > 1) {
                    sheet2.addMergedRegion(new CellRangeAddress(inicioEmpleado, filaActual - 1, 2, 2));
                }
                empleadoActual = emp;
                inicioEmpleado = filaActual;
            }

            // --- CAMBIO DE FECHA ---
            if (!fecha.equals(fechaActual)) {
                if (filaActual - inicioFecha > 1) {
                    sheet2.addMergedRegion(new CellRangeAddress(inicioFecha, filaActual - 1, 4, 4));
                }
                fechaActual = fecha;
                inicioFecha = filaActual;
            }

            filaActual++;
        }

        // Cerrar merges finales
        if (filaActual - inicioMaquina > 1) {
            sheet2.addMergedRegion(new CellRangeAddress(inicioMaquina, filaActual - 1, 1, 1));
        }
        if (filaActual - inicioEmpleado > 1) {
            sheet2.addMergedRegion(new CellRangeAddress(inicioEmpleado, filaActual - 1, 2, 2));
        }
        if (filaActual - inicioFecha > 1) {
            sheet2.addMergedRegion(new CellRangeAddress(inicioFecha, filaActual - 1, 4, 4));
        }

        filaActual += 2; // Espacio entre tablas

        // --- 2. SECCIÓN SCRAP ---
        if (!scrap.isEmpty()) {
            Row rowTitulo2 = sheet2.createRow(filaActual);
            Cell cellT2 = rowTitulo2.createCell(1);
            cellT2.setCellValue("Scrap (" + f1 + " - " + f2 + ")");
            cellT2.setCellStyle(estiloTituloAzul);

            // crear celdas del rango
            for (int c = 1; c <= 4; c++) {
                if (rowTitulo2.getCell(c) == null) {
                    rowTitulo2.createCell(c).setCellStyle(estiloTituloAzul);
                }
            }

            // merge
            CellRangeAddress region2 = new CellRangeAddress(filaActual, filaActual, 1, 4);
            sheet2.addMergedRegion(region2);

            // bordes
            RegionUtil.setBorderTop(BorderStyle.THIN, region2, sheet2);
            RegionUtil.setBorderBottom(BorderStyle.THIN, region2, sheet2);
            RegionUtil.setBorderLeft(BorderStyle.THIN, region2, sheet2);
            RegionUtil.setBorderRight(BorderStyle.THIN, region2, sheet2);

            filaActual++;

            org.apache.poi.ss.usermodel.Row rowEnc2 = sheet2.createRow(filaActual++);
            String[] enc2 = {"Empleado", "Plano", "Fecha", "Comentarios"};
            for (int i = 0; i < enc2.length; i++) {
                org.apache.poi.ss.usermodel.Cell c = rowEnc2.createCell(i + 1);
                c.setCellValue(enc2[i]);
                c.setCellStyle(estiloEncabezadoGris);
            }

            boolean colorGrisScrap = true;
            for (String[] fila : scrap) {
                colorGrisScrap = !colorGrisScrap;

                Row r = sheet2.createRow(filaActual++);

                for (int i = 0; i < fila.length; i++) {
                    Cell c = r.createCell(i + 1);
                    c.setCellValue(fila[i]);

                    if (colorGrisScrap) {
                        c.setCellStyle(estiloCeldaGrisClaro);
                    } else {
                        c.setCellStyle(estiloCelda);
                    }
                }
            }
        }

        // Ajustar columnas
        for (int i = 1; i <= 4; i++) sheet2.autoSizeColumn(i);
        guardarExcel(workbook, f1, f2);
    }

    private void guardarExcel(org.apache.poi.ss.usermodel.Workbook workbook, String f1, String f2) {
        try {
            javax.swing.JFileChooser fileChooser = new javax.swing.JFileChooser();
            fileChooser.setDialogTitle("Reporte " + f1 + " - " + f2);
            fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Excel (*.xlsx)", "xlsx"));

            if (fileChooser.showSaveDialog(null) == javax.swing.JFileChooser.APPROVE_OPTION) {
                java.io.File file = fileChooser.getSelectedFile();
                // Asegurar extensión .xlsx
                String path = file.getAbsolutePath();
                if (!path.toLowerCase().endsWith(".xlsx")) {
                    file = new java.io.File(path + ".xlsx");
                }

                try (java.io.FileOutputStream out = new java.io.FileOutputStream(file)) {
                    workbook.write(out);
                }

                // Abrir el archivo automáticamente
                if (java.awt.Desktop.isDesktopSupported()) {
                    java.awt.Desktop.getDesktop().open(file);
                }
            }
        } catch (java.io.IOException e) {
            javax.swing.JOptionPane.showMessageDialog(null, "Error al guardar o abrir el archivo: " + e.getMessage(), "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable Tabla1;
    private javax.swing.JMenuItem TerminarPlanos;
    private javax.swing.JButton btnAcabados;
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnCnc;
    private javax.swing.JButton btnCortes;
    private javax.swing.JToggleButton btnCurso;
    private javax.swing.JToggleButton btnEstacion;
    private javax.swing.JButton btnFresa;
    private javax.swing.JButton btnReportes;
    private javax.swing.JPanel btnSalir;
    private javax.swing.JToggleButton btnTerminados;
    private javax.swing.JButton btnTorno;
    private javax.swing.ButtonGroup buttonGroup1;
    private com.toedter.calendar.JDateChooser calen;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblEstado;
    private javax.swing.JLabel lblUbi;
    private javax.swing.JRadioButton turno1;
    private javax.swing.JRadioButton turno2;
    private javax.swing.JTextField txtAcabados;
    private javax.swing.JTextField txtAcabados1;
    private javax.swing.JTextField txtAcabados2;
    private javax.swing.JTextField txtCnc;
    private javax.swing.JTextField txtCnc1;
    private javax.swing.JTextField txtCnc2;
    private javax.swing.JTextField txtCortes;
    private javax.swing.JTextField txtCortes1;
    private javax.swing.JTextField txtCortes2;
    private javax.swing.JTextField txtFresa;
    private javax.swing.JTextField txtFresa1;
    private javax.swing.JTextField txtFresa2;
    private javax.swing.JTextField txtProyecto;
    private javax.swing.JTextField txtTorno;
    private javax.swing.JTextField txtTorno1;
    private javax.swing.JTextField txtTorno2;
    // End of variables declaration//GEN-END:variables
}
