package pruebas;

import Conexiones.Conexion;
import VentanaEmergente.Calendario.AgregarFechas;
import VentanaEmergente.Calendario.CodigoColores;
import VentanaEmergente.Calendario.EliminarFecha;
import VentanaEmergente.ProyectoManager.ConfProject;
import VentanaEmergente.ProyectoManager.Editar;
import VentanaEmergente.ProyectoManager.InfoProyectos;
import VentanaEmergente.ProyectoManager.InformeProyect;
import VentanaEmergente.ProyectoManager.addFecha;
import VentanaEmergente.ProyectoManager.addPrioridadCompras;
import VentanaEmergente.ProyectoManager.filtrar;
import VentanaEmergente.ProyectoManager.pruebaExcel;
import VentanaEmergente.Ventas.verDocumentos;
import VentanaEmergente.Ventas.ColorVentas;
import com.mxrck.autocompleter.TextAutoCompleter;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import org.apache.pdfbox.util.Hex;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.FillPatternType;
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
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import scrollPane.ScrollBarCustom;

public class ProyectManager extends javax.swing.JInternalFrame implements ActionListener {

    TableRowSorter<TableModel> elQueOrdena;
    verDocumentos verDoc;
    addFecha s;
    int row, col;
    String numEmpleado;
    filtrar filtro;
    Stack<InfoProyectos> proyectos;
    TextAutoCompleter au;
    private Set<String> proyectosExcluidos = new HashSet<>();

    public void filtrarXProyecto() {
        limpiarTabla();
        DefaultTableModel miModelo = (DefaultTableModel) Tabla1.getModel();
        try {
            Connection con = null;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            Date d1 = filtro.fecha1.getDatoFecha();
            Date d2 = filtro.fecha2.getDatoFecha();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            String sql = "select Id,NumCotizacion,OC,Proyecto,Descripcion,FechaCreacion,"
                    + "Planta,FechaEntrega,Estatus, Facturado, Comentarios, Costo, Moneda,DueDate,Responsable from proyectos order by Id desc";
            ResultSet rs = st.executeQuery(sql);
            String datos[] = new String[20];
            while (rs.next()) {
                datos[0] = rs.getString("Id");
                datos[1] = rs.getString("NumCotizacion");
                datos[2] = rs.getString("OC");
                datos[3] = rs.getString("Proyecto");
                datos[4] = rs.getString("Descripcion");
                datos[5] = rs.getString("FechaCreacion");
                datos[6] = rs.getString("Planta");
                datos[7] = rs.getString("FechaEntrega");
                datos[8] = rs.getString("Estatus");
                datos[9] = rs.getString("Facturado");
                datos[10] = rs.getString("Costo");
                datos[11] = rs.getString("Moneda");
                datos[13] = rs.getString("Comentarios");
                datos[14] = rs.getString("DueDate");
                datos[15] = rs.getString("Responsable");
                Date d = sdf.parse(datos[5]);
                int com1 = d.compareTo(d1);
                int com2 = d.compareTo(d2);
                if (com1 > 0 && com2 < 0) {
                    miModelo.addRow(datos);
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "ERROR: " + e, "ERROR", JOptionPane.ERROR_MESSAGE);
        } catch (ParseException ex) {
            Logger.getLogger(ProyectManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void descargar(byte[] byt) {
        try {
            byte[] b = byt;

            InputStream bos = new ByteArrayInputStream(b);

            int tamInput = bos.available();
            byte[] datosPdf = new byte[tamInput];
            bos.read(datosPdf, 0, tamInput);

            JFileChooser fc = new JFileChooser();
            File archivo = null;
            fc.setFileFilter(new FileNameExtensionFilter("Pdf (*.pdf)", "pdf"));
            int n = fc.showSaveDialog(this);

            if (n == JFileChooser.APPROVE_OPTION) {
                archivo = fc.getSelectedFile();
            }
            String a = "" + archivo;
            if (a.endsWith("pdf")) {
            } else {
                a = archivo + ".pdf";
            }

            OutputStream out = new FileOutputStream(a);
            out.write(datosPdf);

            out.close();
            bos.close();

            Desktop.getDesktop().open(new File(a));

        } catch (NumberFormatException | IOException e) {
            JOptionPane.showMessageDialog(this, "ERROR: " + e, "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    public String getLetra(Date fechaAhora, String fechaTermino, String fechaFin, SimpleDateFormat sdf) {
        Date fecTer;
        Date fecFin;
        try {
            fecTer = sdf.parse(fechaTermino);
        } catch (Exception e) {
            fecTer = null;
        }
        try {
            fecFin = sdf.parse(fechaFin);
        } catch (Exception e) {
            fecFin = null;
        }
        // Si la fecha de fin no existe no se compara
        if (fecFin == null) {
            return "";
        }
        if (fecTer == null && fechaAhora.after(fecFin)) {
            return "R";
        } else if (fecTer != null && fechaAhora.after(fecFin)) {
            return "N";
        } else if (fecTer != null && fecTer.before(fechaAhora)) {
            return "V";
        }
        return "";
    }

    public final void getAgenda() {
        try {
            proyectos = new Stack<>();
            Connection con;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            String sql = "select * from agenda where Estatus != 'Cancelado'";
            ResultSet rs = st.executeQuery(sql);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date d = new Date();
            while (rs.next()) {
                String proyecto = rs.getString("Proyecto");
                String fechaFin = rs.getString("FechaFin");
                if (fechaFin == null) {
                    fechaFin = "";
                }
                String fecha = rs.getString("FechaTermino");
                String depa = rs.getString("Departamento");
                proyectos.push(new InfoProyectos(proyecto, fechaFin + getLetra(d, fecha, fechaFin, sdf), depa));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error: " + e, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public int getProyecto(String proyecto, String depa) {
        int pos = -1;
        for (int i = 0; i < proyectos.size(); i++) {
            InfoProyectos inf = proyectos.get(i);
            if (proyecto.equals(inf.getProyecto()) && inf.getDepa().equals(depa)) {
                return i;
            }
        }
        return pos;
    }

    public final void buscar(String sql) {
        DefaultTableModel miModelo = (DefaultTableModel) Tabla1.getModel();
        try {
            Connection con;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            String datos[] = new String[20];
            while (rs.next()) {
                datos[0] = rs.getString("Id");
                datos[1] = rs.getString("NumCotizacion");
                datos[2] = rs.getString("OC");
                datos[3] = rs.getString("Proyecto");
                datos[4] = rs.getString("Descripcion");
                datos[5] = rs.getString("FechaCreacion");
                datos[6] = rs.getString("Planta");
                datos[7] = rs.getString("FechaEntrega");
                datos[8] = rs.getString("Estatus");
                datos[9] = rs.getString("Facturado");
                datos[10] = rs.getString("Costo");
                datos[11] = rs.getString("Moneda");
                try {
                    datos[14] = proyectos.get(getProyecto(datos[3], "HERRAMENTISTA")).getFecha();
                } catch (Exception e) {
                    datos[14] = "";
                }
                try {
                    datos[12] = proyectos.get(getProyecto(datos[3], "DISEÑO")).getFecha();
                } catch (Exception e) {
                    datos[12] = "";
                }
                try {
                    datos[15] = proyectos.get(getProyecto(datos[3], "INTEGRACION")).getFecha();
                } catch (Exception e) {
                    datos[15] = "";
                }
                try {
                    datos[13] = proyectos.get(getProyecto(datos[3], "COMPRAS")).getFecha();
                } catch (Exception e) {
                    datos[13] = "";
                }
                miModelo.addRow(datos);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "ERROR: " + e, "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    public final void limpiarTabla() {
        Tabla1 = new ColorVentas();
        Tabla1.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        Tabla1.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{},
                new String[]{
                    "ID", "NO COTIZACION", "ORDEN COMPRA", "PROYECTO", "DESCRIPCION", "FECHA", "PLANTA", "FECHA COMPROMISO", "ESTATUS", "FACTURADO", "COSTO", "MONEDA",
                    "DISENO", "COMPRAS", "MAQUINADOS", "INTEGRACION", "FECHA CIERRE"
                }
        ) {
            boolean[] canEdit = new boolean[]{
                false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
        Tabla1.setComponentPopupMenu(jPopupMenu1);
        Tabla1.getTableHeader().setFont(new java.awt.Font("Roboto", java.awt.Font.BOLD, 14));
        Tabla1.getTableHeader().setOpaque(false);
        Tabla1.getTableHeader().setBackground(new Color(0, 78, 171));
        Tabla1.getTableHeader().setForeground(Color.white);
        Tabla1.setRowHeight(25);
        Tabla1.setShowVerticalLines(false);
        Tabla1.setGridColor(new Color(240, 240, 240));
        scrollTabla.getViewport().setBackground(Color.white);
        scrollTabla.setViewportView(Tabla1);
        if (Tabla1.getColumnModel().getColumnCount() > 0) {
            Tabla1.getColumnModel().getColumn(0).setMinWidth(0);
            Tabla1.getColumnModel().getColumn(0).setPreferredWidth(0);
            Tabla1.getColumnModel().getColumn(0).setMaxWidth(0);
        }
    }

    public XSSFColor colorCol(int col) {
        switch (col) {
            case 0:
            case 1:
                return new XSSFColor(new Color(237, 200, 149),null);
            case 2:
            case 3:
            case 4:
                return new XSSFColor(new Color(198, 224, 180),null);
            case 5:
            case 6:
            case 7:
                return new XSSFColor(new Color(189, 215, 238),null);
            case 8:
            case 9:
            case 10:
                return new XSSFColor(new Color(225, 230, 153),null);
            case 11:
            case 12:
            case 13:
                return new XSSFColor(new Color(237, 237, 237),null);
            default:
                return null;
        }
    }
    
    public final void crearReporteFechas(String url, String limit) {
        pruebaExcel pr = new pruebaExcel();
        pr.crearReporteFechas(url, limit);
    }

    public ProyectManager(String numEmpleado) {
        initComponents();
        lblTotales.setVisible(false);
        // Al presionar Enter, se agrega el filtro y se refresca la tabla
        jTextField1.addActionListener((java.awt.event.ActionEvent e) -> {
            agregarFiltro();
        });

        // Maneja el borrado inteligente
        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyPressed(java.awt.event.KeyEvent evt) {
                if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ESCAPE) {
                    // Si el buscador tiene texto, el primer ESC solo borra el texto
                    if (jTextField1.getText().trim().isEmpty()) {
                        eliminarUltimoFiltro();
                    }
                }
            }
        });

        autoCompletar("proyectos", "Proyecto");
        ((javax.swing.plaf.basic.BasicInternalFrameUI) this.getUI()).setNorthPane(null);
        this.numEmpleado = numEmpleado;
        getAgenda();
        limpiarTabla();
        buscar("select Id,NumCotizacion,OC,Proyecto,Descripcion,FechaCreacion,"
                + "Planta,FechaEntrega,Estatus, Facturado, Comentarios, Costo, Moneda, FechaCierre from proyectos order by Id desc");
        DefaultTableModel Modelo = (DefaultTableModel) Tabla1.getModel();
        elQueOrdena = new TableRowSorter<>(Modelo);
        Tabla1.setRowSorter(elQueOrdena);
        scrollTabla.setVerticalScrollBar(new ScrollBarCustom(new java.awt.Color(0, 165, 255)));
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPopupMenu1 = new javax.swing.JPopupMenu();
        editar = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        verDocumentos = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        filtrar = new javax.swing.JMenuItem();
        jSeparator3 = new javax.swing.JPopupMenu.Separator();
        agregarFecha = new javax.swing.JMenuItem();
        eliminarFecha = new javax.swing.JMenuItem();
        informe = new javax.swing.JMenuItem();
        jSeparator4 = new javax.swing.JPopupMenu.Separator();
        Excluir = new javax.swing.JMenuItem();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        scrollTabla = new javax.swing.JScrollPane();
        Tabla1 = new ColorVentas();
        jPanel3 = new javax.swing.JPanel();
        cmbBuscar = new RSMaterialComponent.RSComboBoxMaterial();
        jTextField1 = new javax.swing.JTextField();
        btnVer = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        lblTotales = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        panelFiltros = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        pan = new javax.swing.JPanel();
        panelSalir = new javax.swing.JPanel();
        lblSalir = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem2 = new javax.swing.JMenuItem();
        jSeparator4 = new javax.swing.JPopupMenu.Separator();
        jMenuItem3 = new javax.swing.JMenuItem();

        jPopupMenu1.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
                jPopupMenu1PopupMenuWillBecomeVisible(evt);
            }
        });

        editar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/editar (1).png"))); // NOI18N
        editar.setText("Editar          ");
        editar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editarActionPerformed(evt);
            }
        });
        jPopupMenu1.add(editar);
        jPopupMenu1.add(jSeparator1);

        verDocumentos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/pdf.png"))); // NOI18N
        verDocumentos.setText("Ver documentos                   ");
        verDocumentos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                verDocumentosActionPerformed(evt);
            }
        });
        jPopupMenu1.add(verDocumentos);
        jPopupMenu1.add(jSeparator2);

        filtrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/filtrar.png"))); // NOI18N
        filtrar.setText("Filtrar por proyecto");
        filtrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                filtrarActionPerformed(evt);
            }
        });
        jPopupMenu1.add(filtrar);
        jPopupMenu1.add(jSeparator3);

        agregarFecha.setText("Agregar fecha a proyecto ");
        agregarFecha.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                agregarFechaActionPerformed(evt);
            }
        });
        jPopupMenu1.add(agregarFecha);

        eliminarFecha.setText("Eliminar fecha de proyecto");
        eliminarFecha.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eliminarFechaActionPerformed(evt);
            }
        });
        jPopupMenu1.add(eliminarFecha);

        informe.setText("Eliminar fecha de proyecto");
        informe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                informeActionPerformed(evt);
            }
        });
        jPopupMenu1.add(informe);
        jPopupMenu1.add(jSeparator4);

        Excluir.setText("Excluir del total");
        Excluir.setActionCommand("Excluir del total");
        Excluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ExcluirActionPerformed(evt);
            }
        });
        jPopupMenu1.add(Excluir);

        setBorder(null);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new java.awt.BorderLayout());

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new java.awt.BorderLayout());

        scrollTabla.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));

        Tabla1.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        Tabla1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "NO REQUISICION", "NO COTIZACION", "ORDEN COMPRA", "PROYECTO", "DESCRIPCION", "FECHA", "PLANTA", "FECHA COMPROMISO", "ESTATUS", "FACTURADO", "COSTO", "MONEDA"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        Tabla1.setComponentPopupMenu(jPopupMenu1);
        Tabla1.setGridColor(new java.awt.Color(255, 255, 255));
        Tabla1.setRowHeight(25);
        Tabla1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Tabla1MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                Tabla1MouseEntered(evt);
            }
        });
        scrollTabla.setViewportView(Tabla1);
        if (Tabla1.getColumnModel().getColumnCount() > 0) {
            Tabla1.getColumnModel().getColumn(0).setMinWidth(0);
            Tabla1.getColumnModel().getColumn(0).setPreferredWidth(0);
            Tabla1.getColumnModel().getColumn(0).setMaxWidth(0);
        }

        jPanel2.add(scrollTabla, java.awt.BorderLayout.CENTER);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        java.awt.GridBagLayout jPanel3Layout = new java.awt.GridBagLayout();
        jPanel3Layout.columnWeights = new double[] {1.0, 1.0};
        jPanel3.setLayout(jPanel3Layout);

        cmbBuscar.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "PROYECTO", "ORDEN DE COMPRA", "NO. DE COTIZACIÓN", "CLIENTE", "DESCRIPCIÓN", "FECHA CREACIÓN", "FECHA COMPROMISO" }));
        cmbBuscar.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbBuscarItemStateChanged(evt);
            }
        });
        cmbBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbBuscarActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 13, 0, 0);
        jPanel3.add(cmbBuscar, gridBagConstraints);

        jTextField1.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        jTextField1.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(204, 204, 204)));
        jTextField1.setPreferredSize(new java.awt.Dimension(300, 25));
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 12, 7, 0);
        jPanel3.add(jTextField1, gridBagConstraints);

        btnVer.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        btnVer.setForeground(new java.awt.Color(51, 51, 51));
        btnVer.setText("Ver todos");
        btnVer.setBorder(null);
        btnVer.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnVer.setFocusPainted(false);
        btnVer.setPreferredSize(new java.awt.Dimension(120, 25));
        btnVer.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnVerMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnVerMouseExited(evt);
            }
        });
        btnVer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVerActionPerformed(evt);
            }
        });
        jPanel3.add(btnVer, new java.awt.GridBagConstraints());

        jButton1.setBackground(new java.awt.Color(255, 102, 0));
        jButton1.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Ver codigos de colores");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 10);
        jPanel3.add(jButton1, gridBagConstraints);

        lblTotales.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblTotales.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/excel_1.png"))); // NOI18N
        lblTotales.setText("TOTAL MXN: $");
        lblTotales.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblTotales.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblTotalesMouseClicked(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(0, 2, 0, 5);
        jPanel3.add(lblTotales, gridBagConstraints);

        jScrollPane1.setBorder(null);

        panelFiltros.setBackground(new java.awt.Color(255, 255, 255));
        panelFiltros.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEADING));
        jScrollPane1.setViewportView(panelFiltros);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 10);
        jPanel3.add(jScrollPane1, gridBagConstraints);

        jPanel2.add(jPanel3, java.awt.BorderLayout.PAGE_START);

        jPanel1.add(jPanel2, java.awt.BorderLayout.CENTER);

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setLayout(new java.awt.BorderLayout());

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));

        jLabel12.setFont(new java.awt.Font("Lexend", 1, 24)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(0, 165, 252));
        jLabel12.setText("Proyectos");
        jPanel6.add(jLabel12);

        jPanel5.add(jPanel6);

        jPanel4.add(jPanel5, java.awt.BorderLayout.CENTER);

        pan.setBackground(new java.awt.Color(255, 255, 255));

        panelSalir.setBackground(new java.awt.Color(255, 255, 255));

        lblSalir.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
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

        jPanel4.add(pan, java.awt.BorderLayout.EAST);

        jPanel1.add(jPanel4, java.awt.BorderLayout.NORTH);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        jMenu1.setText("File");
        jMenu1.setFont(new java.awt.Font("Trebuchet MS", 0, 12)); // NOI18N

        jMenuItem1.setFont(new java.awt.Font("Trebuchet MS", 0, 12)); // NOI18N
        jMenuItem1.setText("     Prioridad en compras     ");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Reportes");
        jMenu2.setFont(new java.awt.Font("Trebuchet MS", 0, 12)); // NOI18N

        jMenuItem2.setFont(new java.awt.Font("Trebuchet MS", 0, 12)); // NOI18N
        jMenuItem2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/excel_1.png"))); // NOI18N
        jMenuItem2.setText("Descargar Excel");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem2);
        jMenu2.add(jSeparator4);

        jMenuItem3.setFont(new java.awt.Font("Trebuchet MS", 0, 12)); // NOI18N
        jMenuItem3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/filtrar.png"))); // NOI18N
        jMenuItem3.setText("Descargar reporte de fechas                  ");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem3);

        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void Tabla1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Tabla1MouseClicked
        if (Tabla1.getSelectedColumn() == 14) {
            JFrame f = (JFrame) JOptionPane.getFrameForComponent(this);
            row = Tabla1.getSelectedRow();
            col = Tabla1.getSelectedColumn();
            s = new addFecha(f, true);
            s.btnGuardar.addActionListener(this);
            s.btnCancelar.addActionListener(this);
            s.setLocation(evt.getLocationOnScreen().x - 370, evt.getLocationOnScreen().y);
            s.setVisible(true);
        } else if (evt.getClickCount() == 2) {
            JFrame f = (JFrame) JOptionPane.getFrameForComponent(this);
            Editar editar = new Editar(f, true, this, numEmpleado);
            int fila = Tabla1.getSelectedRow();
            String re, coti, oc, pro, des, est, fac, com, resp, valor, moneda;
            if (Tabla1.getValueAt(fila, 0) == null) {
                re = "";
            } else {
                re = Tabla1.getValueAt(fila, 0).toString();
            }
            editar.txtId.setText(re);

            if (Tabla1.getValueAt(fila, 10) == null) {
                valor = "";
            } else {
                valor = Tabla1.getValueAt(fila, 10).toString();
            }
            System.out.println(valor);
            editar.txtValor.setText(valor);

            if (Tabla1.getValueAt(fila, 11) == null) {
                moneda = "";
            } else {
                moneda = Tabla1.getValueAt(fila, 11).toString();
            }
            editar.jcbMoneda.setSelectedItem(moneda);

            if (Tabla1.getValueAt(fila, 1) == null) {
                coti = "";
            } else {
                coti = Tabla1.getValueAt(fila, 1).toString();
            }
            editar.txtCotizacion.setText(coti);
            if (Tabla1.getValueAt(fila, 2) == null) {
                oc = "";
            } else {
                oc = Tabla1.getValueAt(fila, 2).toString();
            }
            editar.txtOrden.setText(oc);
            if (Tabla1.getValueAt(fila, 3) == null) {
                pro = "";
            } else {
                pro = Tabla1.getValueAt(fila, 3).toString();
            }
            editar.txtProyecto.setText(pro);
            if (Tabla1.getValueAt(fila, 4) == null) {
                des = "";
            } else {
                des = Tabla1.getValueAt(fila, 4).toString();
            }
            editar.txtDescripcion.setText(des);
            if (Tabla1.getValueAt(fila, 8) == null) {
                est = "";
            } else {
                est = Tabla1.getValueAt(fila, 8).toString();
            }

            if (Tabla1.getValueAt(fila, 9) == null) {
                fac = "";
            } else {
                fac = Tabla1.getValueAt(fila, 9).toString();
            }

            if (Tabla1.getValueAt(fila, 13) == null) {
                com = "";
            } else {
                com = Tabla1.getValueAt(fila, 13).toString();
            }

            if (Tabla1.getValueAt(fila, 15) == null) {
                resp = "";
            } else {
                resp = Tabla1.getValueAt(fila, 15).toString();
            }
            editar.txtResponsable.setText(resp);
            editar.txtAcciones.setText(com);
            if (est.equals("")) {
                editar.cmbEstatus.setSelectedIndex(0);
            } else {
                editar.cmbEstatus.setSelectedItem(est);
            }

            if (fac.equals("") || fac.equals("NO")) {
                editar.facturado.setSelected(false);
            } else if (fac.equals("SI")) {
                editar.facturado.setSelected(true);
            }
            editar.setVisible(true);
        }
    }//GEN-LAST:event_Tabla1MouseClicked

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        JFrame f = (JFrame) JOptionPane.getFrameForComponent(this);
        addPrioridadCompras d = new addPrioridadCompras(f, false, numEmpleado);
        d.setVisible(true);
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
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

            Sheet hoja = book.createSheet("REPORTE DE PROYECTO");
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
            hoja.setColumnWidth(2, 3000);
            hoja.setColumnWidth(3, 3000);
            hoja.setColumnWidth(4, 3000);
            hoja.setColumnWidth(6, 15000);
            hoja.setColumnWidth(5, 3000);
            hoja.setColumnWidth(7, 3000);
            hoja.setColumnWidth(8, 3000);
            hoja.setColumnWidth(9, 3000);
            hoja.setColumnWidth(10, 3000);
            hoja.setColumnWidth(11, 3000);
            hoja.setColumnWidth(12, 3000);
            hoja.setColumnWidth(13, 3000);
            hoja.setColumnWidth(14, 3000);
            hoja.setColumnWidth(15, 15000);
            hoja.setColumnWidth(16, 3000);

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
                    16
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
            col.setCellValue("ESTADO DE PROYECTOS");

            for (int i = -1; i < Tabla1.getRowCount(); i++) {
                Row fila10 = hoja.createRow(i + 7);
                for (int j = 0; j < Tabla1.getColumnCount(); j++) {
                    Cell celda = fila10.createCell(j + 2);
                    if (i == -1 && (j >= 0 && j <= 16)) {
                        CellStyle s = book.createCellStyle();
                        Font f = book.createFont();
                        f.setBold(true);
                        f.setColor(IndexedColors.WHITE.getIndex());
                        s.setFont(f);
                        s.setFillForegroundColor(IndexedColors.GREY_50_PERCENT.getIndex());
                        s.setFillPattern(SOLID_FOREGROUND);
                        celda.setCellStyle(s);
                    }
                    if (i > -1 && (j > -1 && j <= 16) && (i % 2 == 0)) {
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
        } catch (FileNotFoundException ex) {
            Logger.getLogger(CambiarEstado.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(CambiarEstado.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void Tabla1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Tabla1MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_Tabla1MouseEntered

    private void editarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editarActionPerformed
        JFrame f = (JFrame) JOptionPane.getFrameForComponent(this);
        Editar editar = new Editar(f, true, this, numEmpleado);
        int fila = Tabla1.getSelectedRow();
        String re, coti, oc, pro, des, est, fac, com, resp, valor, moneda;
        if (Tabla1.getValueAt(fila, 0) == null) {
            re = "";
        } else {
            re = Tabla1.getValueAt(fila, 0).toString();
        }
        editar.txtId.setText(re);
        if (Tabla1.getValueAt(fila, 1) == null) {
            coti = "";
        } else {
            coti = Tabla1.getValueAt(fila, 1).toString();
        }
        editar.txtCotizacion.setText(coti);
        if (Tabla1.getValueAt(fila, 2) == null) {
            oc = "";
        } else {
            oc = Tabla1.getValueAt(fila, 2).toString();
        }
        editar.txtOrden.setText(oc);
        if (Tabla1.getValueAt(fila, 3) == null) {
            pro = "";
        } else {
            pro = Tabla1.getValueAt(fila, 3).toString();
        }
        editar.txtProyecto.setText(pro);
        if (Tabla1.getValueAt(fila, 4) == null) {
            des = "";
        } else {
            des = Tabla1.getValueAt(fila, 4).toString();
        }
        editar.txtDescripcion.setText(des);
        if (Tabla1.getValueAt(fila, 8) == null) {
            est = "";
        } else {
            est = Tabla1.getValueAt(fila, 8).toString();
        }

        if (Tabla1.getValueAt(fila, 9) == null) {
            fac = "";
        } else {
            fac = Tabla1.getValueAt(fila, 9).toString();
        }

        if (Tabla1.getValueAt(fila, 10) == null) {
            valor = "";
        } else {
            valor = Tabla1.getValueAt(fila, 10).toString();
        }
        System.out.println(valor);
        editar.txtValor.setText(valor);

        if (Tabla1.getValueAt(fila, 11) == null) {
            moneda = "";
        } else {
            moneda = Tabla1.getValueAt(fila, 11).toString();
        }
        editar.jcbMoneda.setSelectedItem(moneda);

        if (Tabla1.getValueAt(fila, 13) == null) {
            com = "";
        } else {
            com = Tabla1.getValueAt(fila, 13).toString();
        }
        if (Tabla1.getValueAt(fila, 15) == null) {
            resp = "";
        } else {
            resp = Tabla1.getValueAt(fila, 15).toString();
        }
        editar.txtResponsable.setText(resp);
        editar.txtAcciones.setText(com);
        if (est.equals("")) {
            editar.cmbEstatus.setSelectedIndex(0);
        } else {
            editar.cmbEstatus.setSelectedItem(est);
        }

        if (fac.equals("") || fac.equals("NO")) {
            editar.facturado.setSelected(false);
        } else if (fac.equals("SI")) {
            editar.facturado.setSelected(true);
        }
        editar.setVisible(true);
    }//GEN-LAST:event_editarActionPerformed

    private void verDocumentosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_verDocumentosActionPerformed
        JFrame f = (JFrame) JOptionPane.getFrameForComponent(this);
        verDoc = new verDocumentos(f, true, Tabla1.getValueAt(Tabla1.getSelectedRow(), 3).toString());
        verDoc.lblProyecto.setText(Tabla1.getValueAt(Tabla1.getSelectedRow(), 3).toString());
        verDoc.setVisible(true);
    }//GEN-LAST:event_verDocumentosActionPerformed

    private void filtrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_filtrarActionPerformed
        JFrame f = (JFrame) JOptionPane.getFrameForComponent(this);
        filtro = new filtrar(f, true);
        filtro.btnFiltrar.addActionListener(this);
        filtro.setVisible(true);
    }//GEN-LAST:event_filtrarActionPerformed

    private void lblSalirMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSalirMouseClicked
        dispose();
    }//GEN-LAST:event_lblSalirMouseClicked

    private void lblSalirMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSalirMouseEntered
        panelSalir.setBackground(Color.red);
        lblSalir.setForeground(Color.white);
    }//GEN-LAST:event_lblSalirMouseEntered

    private void lblSalirMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSalirMouseExited
        panelSalir.setBackground(Color.white);
        lblSalir.setForeground(Color.black);
    }//GEN-LAST:event_lblSalirMouseExited

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
//        String text = jTextField1.getText();
//        String sql = "select * from proyectos where Proyecto like '" + text + "%' or NumCotizacion like '" + text + "%' or OC like '" + text + "%'";
//        limpiarTabla();
//        buscar(sql);
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void btnVerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVerActionPerformed
        filtrosActivos.clear();
        actualizarPanelFiltros();
        jTextField1.setText("");
        cmbBuscar.setSelectedIndex(0);
        limpiarTabla();
        cargarDatosConFiltro();
    }//GEN-LAST:event_btnVerActionPerformed


    private void btnVerMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnVerMouseEntered
        btnVer.setBackground(new Color(0, 102, 204));
        btnVer.setForeground(Color.white);
    }//GEN-LAST:event_btnVerMouseEntered

    private void btnVerMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnVerMouseExited
        btnVer.setBackground(Color.white);
        btnVer.setForeground(new Color(51, 51, 51));
    }//GEN-LAST:event_btnVerMouseExited

    private void agregarFechaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_agregarFechaActionPerformed
        JFrame f = (JFrame) JOptionPane.getFrameForComponent(this);
        AgregarFechas agregar = new AgregarFechas(f, true, numEmpleado);
        if (Tabla1.getValueAt(Tabla1.getSelectedRow(), 12).toString().contains("SF")) {
            //agregar.setFechas(getWidth() / 2, getHeight() /2, agregar.lblDesdeD, agregar.lblHastaD);
            agregar.btnHerramentista.setEnabled(false);
            agregar.btnCompras.setEnabled(false);
            agregar.btnIntegracion.setEnabled(false);
            agregar.jButton1.setText("Actualizar");
        }
        agregar.setLocationRelativeTo(f);
        agregar.txtProyecto.setText(Tabla1.getValueAt(Tabla1.getSelectedRow(), 3).toString());
        agregar.buscarProyectos(Tabla1.getValueAt(Tabla1.getSelectedRow(), 3).toString());
        agregar.setOnFechasActualizadasListener(() -> {
            getAgenda();
            limpiarTabla();
            cargarDatosConFiltro();
        });
        agregar.setVisible(true);
    }//GEN-LAST:event_agregarFechaActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        JFrame f = (JFrame) JOptionPane.getFrameForComponent(this);
        CodigoColores codigo = new CodigoColores(f, true);
        codigo.setLocationRelativeTo(f);
        codigo.setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jPopupMenu1PopupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_jPopupMenu1PopupMenuWillBecomeVisible
        if (Tabla1.getSelectedRow() != -1) {
            informe.setEnabled(true);
            informe.setText("Ver informe completo de proyecto " + Tabla1.getValueAt(Tabla1.getSelectedRow(), 3));
        } else {
            informe.setText("Ver informe completo de proyecto");
            informe.setEnabled(false);
        }
    }//GEN-LAST:event_jPopupMenu1PopupMenuWillBecomeVisible

    private void informeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_informeActionPerformed
        JFrame f = (JFrame) JOptionPane.getFrameForComponent(this);
        InformeProyect info = new InformeProyect(f, true, Tabla1.getValueAt(Tabla1.getSelectedRow(), 3).toString());
        info.txtProyecto.setText(Tabla1.getValueAt(Tabla1.getSelectedRow(), 3).toString());
        info.setVisible(true);
    }//GEN-LAST:event_informeActionPerformed

    private void eliminarFechaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_eliminarFechaActionPerformed
        // TODO add your handling code here:
        int fila = Tabla1.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un proyecto primero");
            return;
        }

        JFrame f = (JFrame) JOptionPane.getFrameForComponent(this);
        EliminarFecha eliminar = new EliminarFecha(f, true);
        eliminar.setLocationRelativeTo(f);

        String proyecto = Tabla1.getValueAt(fila, 3).toString();

        // Pasar el proyecto a EliminarFecha
        eliminar.setBuscarProyectoInicial(proyecto);

        // Cuando se elimine una fecha refrescar ProjectManager
        eliminar.setOnFechasActualizadasListener(() -> {
            getAgenda(); // Recarga la lógica interna de fechas
            limpiarTabla(); // Limpia la JTable
            // leer el Mapa 'filtrosActivos' y construir el SQL correcto.
            cargarDatosConFiltro();
        });

        eliminar.setVisible(true);
    }//GEN-LAST:event_eliminarFechaActionPerformed

    public void autoCompletar(String tabla, String campo) {
        // 1. Limpiamos o inicializamos el autocompletador sobre jTextField1
        if (au != null) {
            au.removeAllItems();
        }
        au = new TextAutoCompleter(jTextField1);
        try {
            Connection con = new Conexion().getConnection();
            Statement st = con.createStatement();
            // Usamos DISTINCT para no sugerir el mismo nombre muchas veces
            String sql = "SELECT DISTINCT " + campo + " FROM " + tabla + " WHERE " + campo + " IS NOT NULL";
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                au.addItem(rs.getString(campo));
            }
            con.close();
        } catch (SQLException e) {
            System.out.println("Error al autocompletar: " + e);
        }
    }

    private Map<String, List<String>> filtrosActivos = new java.util.LinkedHashMap<>();
    private void cmbBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbBuscarActionPerformed
        String seleccionado = cmbBuscar.getSelectedItem().toString();

        if (seleccionado.contains("FECHA")) {
            cmbBuscar.hidePopup();

            String[] fechas = mostrarDialogoFechas();
            if (fechas != null && !fechas[0].equals("")) {
                String f1 = fechas[0];
                String f2 = fechas[1];
                // Si f2 está vacío o es igual a f1, es un solo día. Si no, es rango.
                String valorFiltro = (f2.isEmpty() || f2.equals(f1)) ? f1 : f1 + " al " + f2;

                // Agregamos al mapa usando el nombre exacto que seleccionó (ej. "FECHA CREACIÓN")
                filtrosActivos.computeIfAbsent(seleccionado, k -> new ArrayList<>()).add(valorFiltro);

                actualizarPanelFiltros();
                limpiarTabla();
                cargarDatosConFiltro();
            }

            // Regresar a la opción 1 (PROYECTO)
            // Usamos SwingUtilities para que el cambio de índice ocurra después de terminar este evento
            javax.swing.SwingUtilities.invokeLater(() -> {
                cmbBuscar.setSelectedIndex(0);
                jTextField1.requestFocus();
            });
        }
    }//GEN-LAST:event_cmbBuscarActionPerformed

    private void cmbBuscarItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbBuscarItemStateChanged
        // Solo actuar cuando el item es seleccionado (evita que se ejecute dos veces al deseleccionar el anterior)
        if (evt.getStateChange() == java.awt.event.ItemEvent.SELECTED) {
            String seleccionado = cmbBuscar.getSelectedItem().toString();

            // Si el autocompletador no existe, lo creamos una sola vez
            if (au == null) {
                au = new TextAutoCompleter(jTextField1);
            }

            switch (seleccionado) {
                case "PROYECTO":
                    autoCompletar("proyectos", "Proyecto");
                    break;
                case "ORDEN DE COMPRA":
                    autoCompletar("proyectos", "OC");
                    break;
                case "NO. DE COTIZACIÓN":
                    autoCompletar("proyectos", "NumCotizacion");
                    break;
                case "CLIENTE":
                    autoCompletar("proyectos", "Planta");
                    break;
                default:
                    // Limpiar si es descripción o fechas para que no salgan sugerencias viejas
                    au.removeAllItems();
                    break;
            }
        }
    }//GEN-LAST:event_cmbBuscarItemStateChanged

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        JFrame f = (JFrame) JOptionPane.getFrameForComponent(this);
        ConfProject conf = new ConfProject(f, true);
        conf.setSize(new Dimension(471, 581));
        conf.setLocationRelativeTo(f);
        conf.setVisible(true);
        crearReporteFechas(conf.map.get("url"), conf.map.get("token"));
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void lblTotalesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblTotalesMouseClicked
        try {

            JFileChooser fc = new JFileChooser();

            fc.setDialogTitle("Guardar Excel");

            fc.setFileFilter(
                new FileNameExtensionFilter(
                    "Archivos Excel (*.xlsx)",
                    "xlsx"
                )
            );

            int opcion = fc.showSaveDialog(this);

            if (opcion != JFileChooser.APPROVE_OPTION) {
                return;
            }

            File archivo = fc.getSelectedFile();

            String ruta = archivo.getAbsolutePath();

            if (!ruta.endsWith(".xlsx")) {
                ruta += ".xlsx";
            }

            // LIBRO

            XSSFWorkbook book = new XSSFWorkbook();

            Sheet hoja = book.createSheet("RESUMEN DEL TOTAL DE VENTAS");

            // FUENTES

            XSSFFont fontTitulo = book.createFont();

            fontTitulo.setBold(true);
            fontTitulo.setFontHeightInPoints((short) 18);

            CellStyle estiloTitulo = book.createCellStyle();

            estiloTitulo.setFont(fontTitulo);
            estiloTitulo.setAlignment(HorizontalAlignment.CENTER);

            // HEADER

            XSSFFont fontHeader = book.createFont();

            fontHeader.setBold(true);
            fontHeader.setColor(IndexedColors.WHITE.getIndex());
            fontHeader.setFontHeightInPoints((short) 12);

            XSSFCellStyle estiloHeader = book.createCellStyle();

            estiloHeader.setFont(fontHeader);

            byte[] rgbHeader = Hex.decodeHex("13315C");

            XSSFColor colorHeader = new XSSFColor(rgbHeader, null);

            estiloHeader.setFillForegroundColor(colorHeader);

            estiloHeader.setFillPattern(
                FillPatternType.SOLID_FOREGROUND
            );

            estiloHeader.setAlignment(HorizontalAlignment.CENTER);

            estiloHeader.setVerticalAlignment(
                VerticalAlignment.CENTER
            );

            estiloHeader.setWrapText(true);

            // FILAS

            CellStyle estiloFila = book.createCellStyle();

            estiloFila.setAlignment(HorizontalAlignment.CENTER);

            XSSFCellStyle estiloAlternado = book.createCellStyle();

            estiloAlternado.setAlignment(HorizontalAlignment.CENTER);

            byte[] rgbAlt = Hex.decodeHex("EEF4ED");

            XSSFColor colorAlt = new XSSFColor(rgbAlt, null);

            estiloAlternado.setFillForegroundColor(colorAlt);

            estiloAlternado.setFillPattern(
                FillPatternType.SOLID_FOREGROUND
            );

            // TITULO

            Row filaTitulo = hoja.createRow(1);

            Cell celdaTitulo = filaTitulo.createCell(0);

            celdaTitulo.setCellValue(
                "RESUMEN DE VENTAS"
            );

            celdaTitulo.setCellStyle(estiloTitulo);

            hoja.addMergedRegion(
                new CellRangeAddress(
                    1,
                    1,
                    0,
                    4
                )
            );

            // ENCABEZADOS

            Row filaHeader = hoja.createRow(3);

            String[] columnas = {
                "PROYECTO",
                "CLIENTE",
                "FECHA",
                "COSTO",
                "MONEDA"
            };

            for (int i = 0; i < columnas.length; i++) {

                Cell celda = filaHeader.createCell(i);

                celda.setCellValue(columnas[i]);

                celda.setCellStyle(estiloHeader);
            }

            // =========================
            // DATOS
            // =========================

            int filaExcel = 4;
            int filaVisible = 0;
            for (int i = 0; i < Tabla1.getRowCount(); i++) {
                String proyecto = Tabla1.getValueAt(i, 3).toString();

                if (proyectosExcluidos.contains(proyecto)) {
                    continue;
                }
                
                String estatus = Tabla1.getValueAt(i, 8).toString();

                if (estatus.equalsIgnoreCase("DETENIDO")) {
                    continue;
                }
                
                Row fila = hoja.createRow(filaExcel);

                // PROYECTO
                Cell c0 = fila.createCell(0);

                c0.setCellValue(
                    Tabla1.getValueAt(i, 3).toString()
                );

                // CLIENTE
                Cell c1 = fila.createCell(1);

                c1.setCellValue(
                    Tabla1.getValueAt(i, 6).toString()
                );

                // FECHA
                Cell c2 = fila.createCell(2);

                c2.setCellValue(
                    Tabla1.getValueAt(i, 5).toString()
                );

                // COSTO
                Cell c3 = fila.createCell(3);

                c3.setCellValue(
                    Tabla1.getValueAt(i, 10).toString()
                );

                // MONEDA
                Cell c4 = fila.createCell(4);

                c4.setCellValue(
                    Tabla1.getValueAt(i, 11).toString()
                );

                // estilos alternados

                for (int j = 0; j < 5; j++) {

                    Cell celda = fila.getCell(j);

                    if (filaVisible % 2 == 0) {
                        celda.setCellStyle(estiloAlternado);
                    } else {
                        celda.setCellStyle(estiloFila);
                    }
                }
                
                filaVisible++;
                filaExcel++;
            }

            // TOTAL

            Row filaTotal = hoja.createRow(filaExcel);

            Cell celdaTotal = filaTotal.createCell(3);

            celdaTotal.setCellValue(
                lblTotales.getText()
            );

            // ANCHOS

            hoja.setColumnWidth(0, 7000);
            hoja.setColumnWidth(1, 7000);
            hoja.setColumnWidth(2, 5000);
            hoja.setColumnWidth(3, 7000);
            hoja.setColumnWidth(4, 4000);

            // GUARDAR

            FileOutputStream out =
                new FileOutputStream(ruta);

            book.write(out);

            out.close();

            book.close();

            // ABRIR

            Desktop.getDesktop().open(
                new File(ruta)
            );

        } catch (Exception e) {

            e.printStackTrace();

            JOptionPane.showMessageDialog(
                this,
                "Error al generar Excel:\n" + e
            );
        }
    }//GEN-LAST:event_lblTotalesMouseClicked

    private void ExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ExcluirActionPerformed
        excluirProyectoDelTotal();
    }//GEN-LAST:event_ExcluirActionPerformed

    private void excluirProyectoDelTotal() {
        int[] filas = Tabla1.getSelectedRows();

        if (filas.length == 0) {
            return;
        }

        for (int fila : filas) {

            String proyecto =
                    Tabla1.getValueAt(fila, 3).toString();

            proyectosExcluidos.add(proyecto);
        }

        actualizarPanelFiltros();

        limpiarTabla();

        cargarDatosConFiltro();
    }
    
    private String[] mostrarDialogoFechas() {
        // 1. Crear los componentes
        com.toedter.calendar.JDateChooser jdInicio = new com.toedter.calendar.JDateChooser();
        com.toedter.calendar.JDateChooser jdFinal = new com.toedter.calendar.JDateChooser();

        jdInicio.setDateFormatString("dd/MM/yyyy");
        jdFinal.setDateFormatString("dd/MM/yyyy");

        // BOTONES FECHA INICIAL
        JButton btnHoyInicial = new JButton("Hoy");
        btnHoyInicial.addActionListener(e -> {
            jdInicio.setDate(new Date());
        });

        JButton btnLimpiarInicial = new JButton("X");
        btnLimpiarInicial.addActionListener(e -> {
            jdInicio.setDate(null);
        });

        // BOTONES FECHA FINAL
        JButton btnHoyFinal = new JButton("Hoy");
        btnHoyFinal.addActionListener(e -> {
            jdFinal.setDate(new Date());
        });

        JButton btnLimpiarFinal = new JButton("X");
        btnLimpiarFinal.addActionListener(e -> {
            jdFinal.setDate(null);
        });

        // PANEL FECHA INICIAL
        JPanel panelInicio = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));

        panelInicio.add(jdInicio);
        panelInicio.add(btnHoyInicial);
        panelInicio.add(btnLimpiarInicial);

        // PANEL FECHA FINAL
        JPanel panelFinal = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));

        panelFinal.add(jdFinal);
        panelFinal.add(btnHoyFinal);
        panelFinal.add(btnLimpiarFinal);

        // PANEL PRINCIPAL
        JPanel panel = new JPanel(new java.awt.GridLayout(0, 1, 5, 5));

        panel.add(new JLabel("Fecha Inicial:"));
        panel.add(panelInicio);

        panel.add(new JLabel("Fecha Final (Opcional):"));
        panel.add(panelFinal);

        // 3. Mostrar el diálogo
        int result = JOptionPane.showConfirmDialog(null, panel,
                "Seleccionar Rango de Fechas", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            String f1 = "";
            String f2 = "";

            if (jdInicio.getDate() != null) {
                f1 = sdf.format(jdInicio.getDate());
            }
            if (jdFinal.getDate() != null) {
                f2 = sdf.format(jdFinal.getDate());
            }

            // Validación básica
            if (f1.isEmpty()) {
                JOptionPane.showMessageDialog(this, "La fecha inicial es obligatoria");
                return null;
            }
            
            // Si ambas fechas fueron seleccionadas
            if (!f2.isEmpty()) {

                if (jdFinal.getDate().before(jdInicio.getDate())) {

                    JOptionPane.showMessageDialog(
                        this,
                        "La fecha final no puede ser menor que la fecha inicial."
                    );

                    return null;
                }
            }

            return new String[]{f1, f2};
        }
        return null;
    }

    private String mapearColumna(String comboText) {
        switch (comboText) {
            case "PROYECTO":
                return "Proyecto";
            case "ORDEN DE COMPRA":
                return "OC";
            case "NO. DE COTIZACIÓN":
                return "NumCotizacion";
            case "CLIENTE":
                return "Planta";
            case "DESCRIPCIÓN":
                return "Descripcion";
            case "FECHA CREACIÓN":
                return "FechaCreacion";
            case "FECHA COMPROMISO":
                return "FechaEntrega";
            default:
                return "Proyecto";
        }
    }

    public void agregarFiltro() {
        String criterio = cmbBuscar.getSelectedItem().toString();
        String valor = jTextField1.getText().trim().toUpperCase();

        if (!valor.isEmpty()) {
            filtrosActivos.computeIfAbsent(criterio, k -> new ArrayList<>()).add(valor);
            jTextField1.setText("");
            actualizarPanelFiltros();
            limpiarTabla(); // En ProyectManager, este es el método que refresca
            cargarDatosConFiltro();
        }
    }

    private void actualizarPanelFiltros() {
        panelFiltros.removeAll();

        if (!filtrosActivos.isEmpty()) {
            JButton btnLimpiarTodo = new JButton("Limpiar filtros");
            btnLimpiarTodo.setBorderPainted(false);
            btnLimpiarTodo.setBackground(new java.awt.Color(255, 200, 200));
            btnLimpiarTodo.setForeground(new java.awt.Color(150, 0, 0));
            btnLimpiarTodo.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
            btnLimpiarTodo.addActionListener(e -> {
                filtrosActivos.clear();
                proyectosExcluidos.clear();
                actualizarPanelFiltros();
                limpiarTabla();
                cargarDatosConFiltro();
            });
            panelFiltros.add(btnLimpiarTodo);
        }

        for (Map.Entry<String, List<String>> entry : filtrosActivos.entrySet()) {
            String criterio = entry.getKey();
            for (String valor : entry.getValue()) {
                JButton btnFiltro = new JButton(criterio + ": " + valor + "  x");
                btnFiltro.setBorderPainted(false);
                btnFiltro.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
                btnFiltro.setBackground(new java.awt.Color(200, 220, 240)); // Color suave
                btnFiltro.addActionListener(e -> {
                    filtrosActivos.get(criterio).remove(valor);
                    if (filtrosActivos.get(criterio).isEmpty()) {
                        filtrosActivos.remove(criterio);
                    }
                    actualizarPanelFiltros();
                    limpiarTabla();
                    cargarDatosConFiltro();
                });
                panelFiltros.add(btnFiltro);
            }
        }
        for (String proyecto : proyectosExcluidos) {
            JButton btnExcluido = new JButton("EXCLUIDO: " + proyecto + "  x");

            btnExcluido.setBorderPainted(false);

            btnExcluido.setCursor(
                    new java.awt.Cursor(
                            java.awt.Cursor.HAND_CURSOR));

            btnExcluido.setBackground(
                    new java.awt.Color(255, 220, 220));

            btnExcluido.setForeground(
                    new java.awt.Color(150, 0, 0));

            btnExcluido.addActionListener(e -> {

                proyectosExcluidos.remove(proyecto);

                actualizarPanelFiltros();

                limpiarTabla();
                
                cargarDatosConFiltro();
            });

            panelFiltros.add(btnExcluido);
        }
        panelFiltros.revalidate();
        panelFiltros.repaint();
    }

    public void cargarDatosConFiltro() {
        try {
            if (filtrosActivos.isEmpty() && proyectosExcluidos.isEmpty()) {
                lblTotales.setVisible(false);
            } else {
                lblTotales.setVisible(true);
            }
            
            Connection con = new Conexion().getConnection();
            DefaultTableModel miModelo = (DefaultTableModel) Tabla1.getModel();

            double totalMXN = 0;
            // 1. Construcción del SQL con los filtros acumulados
            StringBuilder sql = new StringBuilder("SELECT * FROM proyectos WHERE 1=1");

            for (Map.Entry<String, List<String>> entry : filtrosActivos.entrySet()) {
                String columna = mapearColumna(entry.getKey());
                List<String> valores = entry.getValue();

                if (!valores.isEmpty()) {
                    sql.append(" AND (");
                    for (int i = 0; i < valores.size(); i++) {
                        String val = valores.get(i);
                        if (columna.equals("FechaCreacion") || columna.equals("FechaEntrega")) {
                            if (val.contains(" al ")) {
                                String[] p = val.split(" al ");
                                sql.append("STR_TO_DATE(").append(columna).append(", '%d/%m/%Y') BETWEEN ")
                                        .append("STR_TO_DATE('").append(p[0]).append("', '%d/%m/%Y') AND ")
                                        .append("STR_TO_DATE('").append(p[1]).append("', '%d/%m/%Y')");
                            } else {
                                sql.append(columna).append(" = '").append(val).append("'");
                            }
                        } else {
                            sql.append(columna).append(" LIKE '%").append(val).append("%'");
                        }
                        if (i < valores.size() - 1) {
                            sql.append(" OR ");
                        }
                    }
                    sql.append(")");
                }
            }
            sql.append(" ORDER BY Id DESC");

            // 2. Ejecución y llenado de datos (Lógica del método buscar)
            Statement st = con.createStatement();
            System.out.println(sql.toString());
            ResultSet rs = st.executeQuery(sql.toString());
            String datos[] = new String[20];

            while (rs.next()) {
                datos[0] = rs.getString("Id");
                datos[1] = rs.getString("NumCotizacion");
                datos[2] = rs.getString("OC");
                datos[3] = rs.getString("Proyecto");
                datos[4] = rs.getString("Descripcion");
                datos[5] = rs.getString("FechaCreacion");
                datos[6] = rs.getString("Planta");
                datos[7] = rs.getString("FechaEntrega");
                datos[8] = rs.getString("Estatus");
                datos[9] = rs.getString("Facturado");
                datos[10] = rs.getString("Costo");
                datos[11] = rs.getString("Moneda");

                // Lógica especial de ProyectManager para las sub-fechas:
                try {
                    datos[14] = proyectos.get(getProyecto(datos[3], "HERRAMENTISTA")).getFecha();
                } catch (Exception e) {
                    datos[14] = "";
                }

                try {
                    datos[12] = proyectos.get(getProyecto(datos[3], "DISEÑO")).getFecha();
                } catch (Exception e) {
                    datos[12] = "";
                }

                try {
                    datos[15] = proyectos.get(getProyecto(datos[3], "INTEGRACION")).getFecha();
                } catch (Exception e) {
                    datos[15] = "";
                }

                try {
                    datos[13] = proyectos.get(getProyecto(datos[3], "COMPRAS")).getFecha();
                } catch (Exception e) {
                    datos[13] = "";
                }

                try {
                    String estatus = rs.getString("Estatus");

                    String proyecto = rs.getString("Proyecto");
                    boolean excluirDeSuma =
                            proyectosExcluidos.contains(proyecto);
                    
                    // Los proyectos cerrados sí se muestran, pero no participan en el total
                    if (!excluirDeSuma && (estatus == null || !estatus.equalsIgnoreCase("DETENIDO"))) {

                        String costoStr = rs.getString("Costo");
                        String moneda = rs.getString("Moneda");
                        String fechaProyecto = rs.getString("FechaCreacion");

                        if (costoStr != null && !costoStr.trim().isEmpty()) {

                            // quitar comas
                            costoStr = costoStr.replace(",", "").trim();

                            double costo = Double.parseDouble(costoStr);

                            // Si está en dólares
                            if (moneda != null && moneda.equalsIgnoreCase("DLLS")) {

                                double precioDolar = 1;

                                try {

                                    SimpleDateFormat formatoEntrada = new SimpleDateFormat("dd/MM/yyyy");
                                    SimpleDateFormat formatoSQL = new SimpleDateFormat("yyyy-MM-dd");

                                    Date fecha = formatoEntrada.parse(fechaProyecto);

                                    String fechaBuscar = formatoSQL.format(fecha);

                                    Statement stDolar = con.createStatement();

                                    ResultSet rsDolar = stDolar.executeQuery(
                                        "SELECT Precio FROM preciodolar "
                                        + "WHERE Fecha <= '" + fechaBuscar + "' "
                                        + "ORDER BY Fecha DESC LIMIT 1"
                                    );

                                    if (rsDolar.next()) {
                                        precioDolar = Double.parseDouble(rsDolar.getString("Precio"));
                                    }

                                } catch(Exception e) {
                                    System.out.println("Error obteniendo dólar: " + e);
                                }

                                costo = costo * precioDolar;
                            }

                            totalMXN += costo;
                        }
                    }

                } catch(Exception e) {
                    System.out.println("Error totalizando: " + e);
                }
                miModelo.addRow(datos);
            }
            DecimalFormat df = new DecimalFormat("#,##0.00");
            lblTotales.setText("TOTAL MXN: $" + df.format(totalMXN));
            con.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "ERROR: " + e, "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminarUltimoFiltro() {
        if (filtrosActivos.isEmpty()) {
            return;
        }

        // Convertimos las llaves del mapa a un arreglo para identificar la última agregada
        String[] llaves = filtrosActivos.keySet().toArray(new String[0]);
        String ultimaLlave = llaves[llaves.length - 1];
        List<String> valores = filtrosActivos.get(ultimaLlave);

        if (valores != null && !valores.isEmpty()) {
            // Removemos el último valor de esa categoría
            valores.remove(valores.size() - 1);

            // Si la categoría se quedó sin valores, eliminamos la categoría del mapa
            if (valores.isEmpty()) {
                filtrosActivos.remove(ultimaLlave);
            }
        }

        // Refrescamos la interfaz y los datos
        actualizarPanelFiltros();
        limpiarTabla();
        cargarDatosConFiltro();
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem Excluir;
    private javax.swing.JTable Tabla1;
    private javax.swing.JMenuItem agregarFecha;
    private javax.swing.JButton btnVer;
    private RSMaterialComponent.RSComboBoxMaterial cmbBuscar;
    private javax.swing.JMenuItem editar;
    private javax.swing.JMenuItem eliminarFecha;
    private javax.swing.JMenuItem filtrar;
    private javax.swing.JMenuItem informe;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JPopupMenu.Separator jSeparator3;
    private javax.swing.JPopupMenu.Separator jSeparator4;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JLabel lblSalir;
    private javax.swing.JLabel lblTotales;
    private javax.swing.JPanel pan;
    private javax.swing.JPanel panelFiltros;
    private javax.swing.JPanel panelSalir;
    private javax.swing.JScrollPane scrollTabla;
    private javax.swing.JMenuItem verDocumentos;
    // End of variables declaration//GEN-END:variables

    @Override
    public void actionPerformed(ActionEvent e) {

        if (filtro != null) {
            if (e.getSource() == filtro.btnFiltrar) {
                filtrarXProyecto();
            }
        }

        if (s != null) {
            if (e.getSource() == this.s.btnGuardar) {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                String hora = (String) sdf.format(s.fecha.getDatoFecha());
                Tabla1.setValueAt(hora, row, col);
                try {
                    Connection con;
                    Conexion con1 = new Conexion();
                    con = con1.getConnection();
                    Statement st = con.createStatement();
                    String sql = "update proyectos set DueDate = ? where Id = ?";
                    PreparedStatement pst = con.prepareStatement(sql);

                    pst.setString(1, hora);
                    pst.setString(2, Tabla1.getValueAt(row, 0).toString());
                    int n = pst.executeUpdate();

                    if (n == 0) {
                        JOptionPane.showMessageDialog(this, "INFORMACION NO GUARDADA", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
                    }

                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(this, "ERROR: " + ex, "ERROR", JOptionPane.ERROR_MESSAGE);
                }
                s.dispose();
            }
        }
    }
}
