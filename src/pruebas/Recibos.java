package pruebas;

import Conexiones.Conexion;
import Conexiones.ConexionChat;
import Modelo.CabezeraRemisiones;
import Modelo.javamail;
import VO.whatsappMessage;
import VentanaEmergente.Recibos.CancelarOrden;
import VentanaEmergente.Recibos.Factura;
import VentanaEmergente.Requisiciones.Material;
import VentanaEmergente.Ventas.correos;
import com.app.sockets.chat.Cliente;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Stack;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;

public class Recibos extends javax.swing.JInternalFrame implements ActionListener {

    String id, req;
    int fil = 0;
    Material mat;
    public String numEmpleado;

    public void crearNotificacion(String requi, String numEmpleado) {

        try {
            Connection con = null;
            ConexionChat con1 = new ConexionChat();
            con = con1.getConnection();

            Connection con2 = null;
            Conexion con3 = new Conexion();
            con2 = con3.getConnection();

            Statement st = con.createStatement();
            Date d = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            String fecha = sdf.format(d);
            String sql2 = "select * from registroempleados where NumEmpleado like '" + numEmpleado + "'";
            Statement st2 = con2.prepareCall(sql2);
            ResultSet rs2 = st2.executeQuery(sql2);
            String ip;
            int port;
            String empleado;
            System.out.println(numEmpleado);
            while (rs2.next()) {
                ip = rs2.getString("Ip");
                port = rs2.getInt("Puerto");
                empleado = rs2.getString("NumEmpleado");

                String not = "noti" + empleado;
                String sql = "insert into " + not + " (Departamento,Titulo,Texto,Fecha) values (?,?,?,?)";
                PreparedStatement pst = con.prepareStatement(sql);
                pst.setString(1, "2");
                pst.setString(2, "ARRIVO DE MATERIAL");
                pst.setString(3, "LLEGO MATERIAL DE LA REQUISICION: *" + requi + "*, CLIC PARA MAS DETALLES");
                pst.setString(4, fecha);

                pst.executeUpdate();
                Cliente cliente = new Cliente(port + 1, "NUEVA APROBACION", ip);
                Thread hilo = new Thread(cliente);
                hilo.start();
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "ERROR: " + e, "ERROR", JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(Inicio1.class.getName()).log(Level.SEVERE, null, e);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Recibos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void limpiarTabla() {
        Tabla1.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{},
                new String[]{
                    "ID", "NO. REQUISICION", "DESCRIPCION", "CANTIDAD", "NO. PARTE", "PROYECTO", "S", "C.R", "C.A", "UBICACION", "PRECIO"
                }
        ) {
            Class[] types = new Class[]{
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean[]{
                false, false, false, false, false, false, true, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
        Tabla1.setRowHeight(25);
        jScrollPane1.setViewportView(Tabla1);
        if (Tabla1.getColumnModel().getColumnCount() > 0) {
            Tabla1.getColumnModel().getColumn(0).setResizable(false);
            Tabla1.getColumnModel().getColumn(1).setResizable(false);
            Tabla1.getColumnModel().getColumn(2).setResizable(false);
            Tabla1.getColumnModel().getColumn(3).setResizable(false);
            Tabla1.getColumnModel().getColumn(4).setResizable(false);
            Tabla1.getColumnModel().getColumn(5).setResizable(false);
            Tabla1.getColumnModel().getColumn(6).setResizable(false);
        }
    }

    public void verDatos2() {
        try {
            limpiarTabla();
            DefaultTableModel miModelo = (DefaultTableModel) Tabla1.getModel();
            Connection con = null;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            Statement st1 = con.createStatement();

            String sql1 = "select * from ordencompra where OrdenNo like '" + id + "'";
            ResultSet rs1 = st.executeQuery(sql1);
            String datos1[] = new String[10];
            datos1[0] = "";
            while (rs1.next()) {
                datos1[0] = rs1.getString("RequisicionNo");
            }
            if (datos1[0].equals("")) {
                JOptionPane.showMessageDialog(this, "NO EXISTE ESTA ORDEN DE COMPRA");
            } else {
                String sql2 = "select * from requisiciones where OC like '" + id + "' and Activo like true";
                ResultSet rs = st1.executeQuery(sql2);
                String datos[] = new String[15];
                while (rs.next()) {
                    datos[0] = rs.getString("Id");
                    datos[1] = rs.getString("NumRequisicion");
                    datos[2] = rs.getString("Descripcion");
                    datos[3] = rs.getString("Cantidad");
                    datos[4] = rs.getString("Codigo");
                    datos[5] = rs.getString("Proyecto");
                    datos[9] = rs.getString("Llego");
                    datos[7] = rs.getString("CantRecibida");
                    datos[10] = rs.getString("Precio");
                    boolean v = false;

                    if (datos[9] == null) {
                        v = true;
                    }
                    if (!"SI".equals(datos[9])) {
                        v = true;
                    }

                    if (v == true) {
                        miModelo.addRow(datos);
                    }
                }
            }
            Tabla1.setShowVerticalLines(false);
            Tabla1.setShowHorizontalLines(false);
            Tabla1.setShowGrid(false);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "ERROR AL BUSCAR PEDIDO" + e, "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void verDatos() {
        try {
            limpiarTabla();
            DefaultTableModel miModelo = (DefaultTableModel) Tabla1.getModel();
            Connection con = null;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            Statement st1 = con.createStatement();

            String sql1 = "select * from ordencompra where OrdenNo like '" + id + "'";
            ResultSet rs1 = st.executeQuery(sql1);
            String datos1[] = new String[10];
            datos1[0] = "";
            while (rs1.next()) {
                datos1[0] = rs1.getString("RequisicionNo");
            }
            if (datos1[0].equals("")) {
                JOptionPane.showMessageDialog(this, "NO EXISTE ESTA ORDEN DE COMPRA");
            } else {
                String sql2 = "select * from requisiciones where NumRequisicion like '" + datos1[0] + "' like true";
                ResultSet rs = st1.executeQuery(sql2);
                String datos[] = new String[15];
                while (rs.next()) {
                    datos[0] = rs.getString("Id");
                    datos[1] = rs.getString("NumRequisicion");
                    datos[2] = rs.getString("Descripcion");
                    datos[3] = rs.getString("Cantidad");
                    datos[4] = rs.getString("Codigo");
                    datos[5] = rs.getString("Proyecto");
                    datos[9] = rs.getString("Llego");
                    datos[7] = rs.getString("CantRecibida");
                    datos[10] = rs.getString("Precio");
                    boolean v = false;

                    if (datos[9] == null) {
                        v = true;
                    }
                    if (!"SI".equals(datos[9])) {
                        v = true;
                    }

                    if (v == true) {
                        miModelo.addRow(datos);
                    }
                }
            }
            Tabla1.setShowVerticalLines(false);
            Tabla1.setShowHorizontalLines(false);
            Tabla1.setShowGrid(false);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "ERROR AL BUSCAR PEDIDO" + e, "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    public boolean existeRelacion() {
        boolean existe = false;
        try {
            Connection con;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            for (int i = 0; i < Tabla1.getRowCount(); i++) {
                Statement st = con.createStatement();
                String sql = "select * from materialrequisiciones where NumRequisicion like '" + Tabla1.getValueAt(i, 1).toString() + "'";
                ResultSet rs = st.executeQuery(sql);
                String num = null;
                while (rs.next()) {
                    num = rs.getString("NumRequisicion");
                }
                if (num != null) {
                    existe = true;
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "ERROR: " + e, "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        return existe;
    }

    public PdfPCell border(PdfPCell celda, float top, float bot, float left, float rig) {
        celda.setBorderWidthBottom(bot);
        celda.setBorderWidthTop(top);
        celda.setBorderWidthRight(rig);
        celda.setBorderWidthLeft(left);
        return celda;
    }

    public void crearPdf() {
        try {
            String ruta = "C:/Pruebas/BD/print/new.pdf";
            Document document = new Document(PageSize.A4, 36, 36, 20, 36);
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(ruta));
            document.open();

            //---------------------------------FUENTES---------------------------------
            com.itextpdf.text.Font fuente1 = new com.itextpdf.text.Font();
            fuente1.setSize(48);
            fuente1.setFamily("Roboto");
            fuente1.setColor(BaseColor.BLACK);
            fuente1.setStyle(com.itextpdf.text.Font.NORMAL);

            com.itextpdf.text.Font fuente2 = new com.itextpdf.text.Font();
            fuente2.setSize(14);
            fuente2.setFamily("Roboto");
            fuente2.setColor(BaseColor.BLACK);
            fuente2.setStyle(com.itextpdf.text.Font.NORMAL);

            com.itextpdf.text.Font fuente3 = new com.itextpdf.text.Font();
            fuente3.setSize(16);
            fuente3.setFamily("Roboto");
            fuente3.setColor(BaseColor.BLACK);
            fuente3.setStyle(com.itextpdf.text.Font.BOLD);

            com.itextpdf.text.Font fuente4 = new com.itextpdf.text.Font();
            fuente4.setSize(30);
            fuente4.setFamily("Roboto");
            fuente4.setColor(BaseColor.BLACK);
            fuente4.setStyle(com.itextpdf.text.Font.NORMAL);

            com.itextpdf.text.Font fuenteFecha = new com.itextpdf.text.Font();
            fuenteFecha.setSize(10);
            fuenteFecha.setFamily("Roboto");
            fuenteFecha.setColor(BaseColor.BLACK);
            fuenteFecha.setStyle(com.itextpdf.text.Font.NORMAL);

            com.itextpdf.text.Font fuenteCliente = new com.itextpdf.text.Font();
            fuenteCliente.setSize(12);
            fuenteCliente.setFamily("Roboto");
            fuenteCliente.setColor(BaseColor.BLACK);
            fuenteCliente.setStyle(com.itextpdf.text.Font.NORMAL);

            com.itextpdf.text.Font fuenteArticulos = new com.itextpdf.text.Font();
            fuenteArticulos.setSize(10);
            fuenteArticulos.setFamily("Roboto");
            fuenteArticulos.setColor(BaseColor.BLACK);
            fuenteArticulos.setStyle(com.itextpdf.text.Font.BOLD);

            //------------------------------------------------------------------------------------------------
            //-----------------------------------PARTE 1, Encabezado-------------------------------
            PdfPTable tabla1 = new PdfPTable(1);
            tabla1.setWidthPercentage(100);

            PdfPTable tabla2 = new PdfPTable(3);
            tabla2.setWidthPercentage(100);

            PdfPCell c1 = new PdfPCell(new Paragraph("Relacion de materiales\n", fuente3));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            border(c1, 0, 0, 0, 0);

            tabla1.addCell(c1);
            //------------------------------------------------------------------------------
            //----------------------TABLA DE ARTICULOS------------------------------------------------------------
            PdfPTable tablaArticulos = new PdfPTable(3);
            tablaArticulos.setWidthPercentage(100);
            float medidas3[] = {400, 400, 90};
            tablaArticulos.setWidths(medidas3);

            PdfPCell cCantidad = new PdfPCell(new Paragraph("Plano", fuenteArticulos));
            cCantidad.setHorizontalAlignment(Element.ALIGN_CENTER);
            cCantidad.setBorderWidth(0f);

            PdfPCell cArticulo = new PdfPCell(new Paragraph("Articulo", fuenteArticulos));
            cArticulo.setHorizontalAlignment(Element.ALIGN_CENTER);
            cArticulo.setBorderWidth(0f);

            PdfPCell cEntregado = new PdfPCell(new Paragraph("Requi.", fuenteArticulos));
            cEntregado.setHorizontalAlignment(Element.ALIGN_CENTER);
            cEntregado.setBorderWidth(0f);

            tablaArticulos.addCell(cCantidad);
            tablaArticulos.addCell(cArticulo);
            tablaArticulos.addCell(cEntregado);

            for (int i = 0; i < mat.Tabla1.getRowCount(); i++) {
                for (int j = 0; j < 3; j++) {
                    PdfPCell cel;
                    cel = new PdfPCell(new Paragraph(mat.Tabla1.getValueAt(i, j).toString(), fuenteCliente));
                    cel.setBorderWidth(0.5f);
                    cel.setHorizontalAlignment(Element.ALIGN_CENTER);
                    tablaArticulos.addCell(cel);
                }
            }
            //----------------------------------------------------------------------------------------------------

            //---------------------DOCUMENTOS PARA AGREGAR-----------------------------------
            document.add(tabla1);
            document.add(tablaArticulos);
            //-------------------------------------------------------------------------------
            document.close();
            Desktop.getDesktop().open(new File(ruta));

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "ERROR: " + e, "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public final void cerrarFechaCalendario(String proyecto, Connection con) throws SQLException {
        String depa = "COMPRAS";
        String sql = "update agenda set Estatus = ?, EmpleadoFin = ?, FechaTermino = ? where Proyecto = ? and Departamento = ?";
        PreparedStatement pst = con.prepareStatement(sql);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String fecha = sdf.format(new Date());

        pst.setString(1, "Terminado");
        pst.setString(2, numEmpleado);
        pst.setString(3, fecha);
        pst.setString(4, proyecto);
        pst.setString(5, depa);

        int in = pst.executeUpdate();
        
        if(in < 1){

            sql = "insert into agenda (Estatus, EmpleadoFin, FechaTermino, Proyecto, Departamento) values(?,?,?,?,?)";
            pst = con.prepareStatement(sql);

            pst.setString(1, "Terminado");
            pst.setString(2, numEmpleado);
            pst.setString(3, fecha);
            pst.setString(4, proyecto);
            pst.setString(5, depa);

            in = pst.executeUpdate();

            if (in < 1) {
                JOptionPane.showMessageDialog(this, "Error al generar fecha", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    public final void verificarProyectoParaCierre(Connection con, String proyecto) throws SQLException{
        Statement st = con.createStatement();
        String sql = "select * from requisiciones where proyecto like '" + proyecto + "'";
        ResultSet rs = st.executeQuery(sql);
        int cont = 0;
        int total = 0;
        while (rs.next()) {
            String llego = rs.getString("Llego");
            String oc = rs.getString("OC"); // Columna donde aparece "CANCELADO"

            // Si viene CANCELADO (en la columna OC), NO cuenta en total
            if (oc != null && oc.trim().equalsIgnoreCase("CANCELADO")) {
                continue;
            }

            // Contar solo los materiales que sí se deben recibir (los de CO CANCELADO no contarán)
            total++;

            // Solo se considera recibido si Llego = "SI"
            if (llego != null && llego.trim().equalsIgnoreCase("SI")) {
                cont++;
            }
        }

        if (total == cont) {
            cerrarFechaCalendario(proyecto, con);
        }
    }

    public Recibos(String numEmpleado) {
        try {
            initComponents();
            this.numEmpleado = numEmpleado;
            ((javax.swing.plaf.basic.BasicInternalFrameUI) this.getUI()).setNorthPane(null);
            txtDescripcion.setLineWrap(true);
            txtDescripcion.setWrapStyleWord(true);

            Tabla1.getTableHeader().setFont(new java.awt.Font("Roboto", java.awt.Font.BOLD, 14));
            Tabla1.getTableHeader().setOpaque(false);
            Tabla1.getTableHeader().setBackground(new java.awt.Color(0, 68, 147));
            Tabla1.getTableHeader().setForeground(java.awt.Color.white);
            Tabla1.setRowHeight(25);
            Tabla1.setShowGrid(false);

            panelRelacion.setVisible(false);

        } catch (Exception ex) {
            Logger.getLogger(Recibos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        txtNumero = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtParte = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtCantidad = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtDescripcion = new javax.swing.JTextArea();
        txtId = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        cbRack = new javax.swing.JComboBox<>();
        jLabel15 = new javax.swing.JLabel();
        cbSeccion = new javax.swing.JComboBox<>();
        jLabel16 = new javax.swing.JLabel();
        cbPiso = new javax.swing.JComboBox<>();
        jLabel11 = new javax.swing.JLabel();
        btnArticulo = new rojeru_san.RSButtonRiple();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        pan = new javax.swing.JPanel();
        panelSalir = new javax.swing.JPanel();
        lblSalir = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        txtNumeroCotizacion = new rojeru_san.RSMTextFull();
        jScrollPane1 = new javax.swing.JScrollPane();
        Tabla1 = new javax.swing.JTable();
        panelRelacion = new javax.swing.JPanel();
        btnImprimir = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        cancelar = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        jMenuItem2 = new javax.swing.JMenuItem();

        setBackground(new java.awt.Color(255, 255, 255));
        setBorder(null);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new java.awt.BorderLayout(10, 10));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setPreferredSize(new java.awt.Dimension(300, 676));

        jLabel7.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("NUMERO DE REQUISICION");

        txtNumero.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        txtNumero.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtNumero.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(240, 240, 240)));
        txtNumero.setEnabled(false);

        jLabel8.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("NUMERO DE PARTE");

        txtParte.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        txtParte.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtParte.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(240, 240, 240)));
        txtParte.setEnabled(false);

        jLabel9.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("CANTIDAD");

        txtCantidad.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        txtCantidad.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCantidad.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(240, 240, 240)));
        txtCantidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCantidadActionPerformed(evt);
            }
        });

        txtDescripcion.setColumns(20);
        txtDescripcion.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        txtDescripcion.setLineWrap(true);
        txtDescripcion.setRows(5);
        txtDescripcion.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(240, 240, 240)));
        txtDescripcion.setEnabled(false);
        jScrollPane2.setViewportView(txtDescripcion);

        txtId.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        txtId.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtId.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(240, 240, 240)));
        txtId.setEnabled(false);

        jLabel10.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("ID");

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));

        jLabel13.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setText("UBICACION");

        jLabel14.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel14.setText("RACK");

        cbRack.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        cbRack.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q" }));
        cbRack.setBorder(null);
        cbRack.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                cbRackPopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
        });

        jLabel15.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel15.setText("SECCION");

        cbSeccion.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        cbSeccion.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" }));
        cbSeccion.setBorder(null);
        cbSeccion.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                cbSeccionPopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
        });

        jLabel16.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel16.setText("PISO");

        cbPiso.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        cbPiso.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" }));
        cbPiso.setBorder(null);
        cbPiso.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                cbPisoPopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
        });

        jLabel11.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("DESCRIPCION");

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cbRack, 0, 96, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cbSeccion, 0, 73, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cbPiso, 0, 90, Short.MAX_VALUE))
                        .addGap(20, 20, 20))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel13)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
                                .addComponent(jLabel14)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED))
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel16)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cbRack, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbPiso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel15)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(cbSeccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jLabel11)
                .addContainerGap())
        );

        btnArticulo.setText("LIBERAR MATERIAL");
        btnArticulo.setFocusPainted(false);
        btnArticulo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnArticuloActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btnArticulo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, 279, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtNumero, javax.swing.GroupLayout.PREFERRED_SIZE, 279, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtParte, javax.swing.GroupLayout.PREFERRED_SIZE, 279, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 279, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane2))))
                .addGap(28, 28, 28))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtNumero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtParte, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 178, Short.MAX_VALUE)
                .addGap(27, 27, 27)
                .addComponent(btnArticulo, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel1.add(jPanel2, java.awt.BorderLayout.EAST);

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setLayout(new java.awt.BorderLayout());

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));

        jLabel12.setFont(new java.awt.Font("Roboto", 1, 40)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(0, 165, 252));
        jLabel12.setText("       RECIBOS       ");
        jPanel6.add(jLabel12);

        jPanel5.add(jPanel6);

        jPanel4.add(jPanel5, java.awt.BorderLayout.CENTER);

        pan.setBackground(new java.awt.Color(255, 255, 255));

        panelSalir.setBackground(new java.awt.Color(255, 255, 255));

        lblSalir.setFont(new java.awt.Font("Roboto", 1, 20)); // NOI18N
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

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setLayout(new java.awt.BorderLayout(10, 10));

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setLayout(new java.awt.BorderLayout(10, 10));

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));
        jPanel8.setLayout(new java.awt.GridLayout(1, 0, 10, 10));
        jPanel7.add(jPanel8, java.awt.BorderLayout.EAST);

        txtNumeroCotizacion.setModoMaterial(true);
        txtNumeroCotizacion.setPlaceholder("Ingresar orden de compra");
        txtNumeroCotizacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNumeroCotizacionActionPerformed(evt);
            }
        });
        jPanel7.add(txtNumeroCotizacion, java.awt.BorderLayout.CENTER);

        jPanel3.add(jPanel7, java.awt.BorderLayout.NORTH);

        jScrollPane1.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane1.setBorder(null);
        jScrollPane1.setForeground(new java.awt.Color(255, 255, 255));

        Tabla1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "NO. REQUISICION", "DESCRIPCION", "CANTIDAD", "NO. PARTE", "PROYECTO", "S", "C.R", "C.A", "UBICACION", "PRECIO"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, true, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        Tabla1.setRowHeight(25);
        Tabla1.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                Tabla1MouseDragged(evt);
            }
        });
        Tabla1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Tabla1MouseClicked(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                Tabla1MouseReleased(evt);
            }
        });
        Tabla1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                Tabla1KeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(Tabla1);
        if (Tabla1.getColumnModel().getColumnCount() > 0) {
            Tabla1.getColumnModel().getColumn(0).setResizable(false);
            Tabla1.getColumnModel().getColumn(1).setResizable(false);
            Tabla1.getColumnModel().getColumn(2).setResizable(false);
            Tabla1.getColumnModel().getColumn(3).setResizable(false);
            Tabla1.getColumnModel().getColumn(4).setResizable(false);
            Tabla1.getColumnModel().getColumn(5).setResizable(false);
            Tabla1.getColumnModel().getColumn(6).setResizable(false);
        }

        jPanel3.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        panelRelacion.setBackground(new java.awt.Color(255, 255, 255));
        panelRelacion.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 10, 5));

        btnImprimir.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        btnImprimir.setForeground(new java.awt.Color(51, 51, 51));
        btnImprimir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/imprimir_1.png"))); // NOI18N
        btnImprimir.setBorder(null);
        btnImprimir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnImprimirActionPerformed(evt);
            }
        });
        panelRelacion.add(btnImprimir);

        jLabel1.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 51, 51));
        jLabel1.setText("Esta requisicion tiene relacion de materiales");
        panelRelacion.add(jLabel1);

        jPanel3.add(panelRelacion, java.awt.BorderLayout.PAGE_END);

        jPanel1.add(jPanel3, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        jMenu1.setText("Archivo");

        cancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/lista.png"))); // NOI18N
        cancelar.setText("Cancelar ordenes de compra                ");
        cancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelarActionPerformed(evt);
            }
        });
        jMenu1.add(cancelar);
        jMenu1.add(jSeparator1);

        jMenuItem2.setText("Editar envio de correos                                            ");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void Tabla1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Tabla1MouseClicked
        int fila = Tabla1.getSelectedRow();
        txtId.setText(Tabla1.getValueAt(fila, 0).toString());
        txtNumero.setText(Tabla1.getValueAt(fila, 1).toString());
        txtDescripcion.setText(Tabla1.getValueAt(fila, 2).toString());
        txtCantidad.setText(Tabla1.getValueAt(fila, 3).toString());
        txtParte.setText(Tabla1.getValueAt(fila, 4).toString());
    }//GEN-LAST:event_Tabla1MouseClicked

    private void txtCantidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCantidadActionPerformed
        double cantidad = 0;
        double cantidadAcum = 0;
        if (Tabla1.getValueAt(Tabla1.getSelectedRow(), 7) == null) {
            cantidad = 0;
        } else {
            cantidad = Double.parseDouble(Tabla1.getValueAt(Tabla1.getSelectedRow(), 7).toString());
        }

        if (Tabla1.getValueAt(Tabla1.getSelectedRow(), 8) == null) {
            cantidadAcum = 0;
        } else {
            cantidadAcum = Double.parseDouble(Tabla1.getValueAt(Tabla1.getSelectedRow(), 8).toString());
        }
        double cant = cantidad + Double.parseDouble(txtCantidad.getText());
        double cant2 = cantidadAcum + Double.parseDouble(txtCantidad.getText());

        if (cant > Double.parseDouble(Tabla1.getValueAt(Tabla1.getSelectedRow(), 3).toString())) {
            JOptionPane.showMessageDialog(this, "NO PUEDES AGREGAR MAS ARTICULOS DE LOS PEDIDOS", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
        } else {
            Tabla1.setValueAt(cant, Tabla1.getSelectedRow(), 7);
            Tabla1.setValueAt(cant2, Tabla1.getSelectedRow(), 8);
        }

    }//GEN-LAST:event_txtCantidadActionPerformed

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

    private void cbRackPopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_cbRackPopupMenuWillBecomeInvisible
        String ubicacion = (String) cbRack.getSelectedItem() + "-" + cbSeccion.getSelectedItem() + "-" + cbPiso.getSelectedItem();
        if (Tabla1.getSelectedRow() >= 0) {
            Tabla1.setValueAt(ubicacion, Tabla1.getSelectedRow(), 9);
        }
    }//GEN-LAST:event_cbRackPopupMenuWillBecomeInvisible

    private void cbSeccionPopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_cbSeccionPopupMenuWillBecomeInvisible
        String ubicacion = (String) cbRack.getSelectedItem() + "-" + cbSeccion.getSelectedItem() + "-" + cbPiso.getSelectedItem();
        if (Tabla1.getSelectedRow() >= 0) {
            Tabla1.setValueAt(ubicacion, Tabla1.getSelectedRow(), 9);
        }
    }//GEN-LAST:event_cbSeccionPopupMenuWillBecomeInvisible

    private void cbPisoPopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_cbPisoPopupMenuWillBecomeInvisible
        String ubicacion = (String) cbRack.getSelectedItem() + "-" + cbSeccion.getSelectedItem() + "-" + cbPiso.getSelectedItem();
        if (Tabla1.getSelectedRow() >= 0) {
            Tabla1.setValueAt(ubicacion, Tabla1.getSelectedRow(), 9);
        }
    }//GEN-LAST:event_cbPisoPopupMenuWillBecomeInvisible

    private void Tabla1MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Tabla1MouseDragged
        if (Tabla1.getSelectedColumn() == 9) {
            for (int i = 0; i < Tabla1.getSelectedRows().length; i++) {
                Tabla1.setValueAt(Tabla1.getValueAt(fil, 9), Tabla1.getSelectedRows()[i], 9);
            }
        }
    }//GEN-LAST:event_Tabla1MouseDragged

    private void txtNumeroCotizacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNumeroCotizacionActionPerformed
        id = txtNumeroCotizacion.getText();
        verDatos2();
        if (existeRelacion()) {
            panelRelacion.setVisible(true);
        } else {
            panelRelacion.setVisible(false);
        }
    }//GEN-LAST:event_txtNumeroCotizacionActionPerformed

    private void btnArticuloActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnArticuloActionPerformed
        try {
            Connection con = null;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            int n6 = 0;
            int n = 0;
            boolean bandera = true;
            String mens = "DEBES LLENAR TODOS LOS CAMPOS DE LAS CANTIDADES QUE HAN LLEGADO";
            for (int i = 0; i < Tabla1.getRowCount(); i++) {
                String des = "true";
                if (Tabla1.getValueAt(i, 6) == null) {
                    des = "false";
                } else {
                    des = Tabla1.getValueAt(i, 6).toString();
                }

                if (des.equals("true")) {
                    if (Tabla1.getValueAt(i, 7) == null) {
                        bandera = false;
                        break;
                    } else if (Tabla1.getValueAt(i, 7).toString().equals("")) {
                        bandera = false;
                        break;
                    } else {
                        if (Double.parseDouble(Tabla1.getValueAt(i, 7).toString()) > Double.parseDouble(Tabla1.getValueAt(i, 3).toString())) {
                            bandera = false;
                            mens = "LA CANTIDAD INGRESADA NO PUEDE SER MAYOR A LA PEDIDA";
                            break;
                        }
                    }

                    if (Tabla1.getValueAt(i, 9) == null) {
                        bandera = false;
                        mens = "FALTA INSERTAR UBICACION DE MATERIALES";
                        break;
                    } else if (Tabla1.getValueAt(i, 9).toString().equals("")) {
                        bandera = false;
                        mens = "FALTA INSERTAR UBICACION DE MATERIALES";
                        break;
                    }

                }
            }
            if (bandera == false) {
                JOptionPane.showMessageDialog(this, mens, "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
            } else {
                //------------------AGREGAR A BD TODAS LAS FILAS SELECCIONADAS
                Date d = new Date();
                SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy");
                String fecha = sf.format(d);

                for (int i = 0; i < Tabla1.getRowCount(); i++) {
                    if (Tabla1.getValueAt(i, 6) != null) {
                        if (Tabla1.getValueAt(i, 6).equals(true)) {

                            String cantidadParte = "";
                            cantidadParte = Tabla1.getValueAt(i, 3).toString();

                            double cantidad = 0.0, total = 0;
                            cantidad = Double.parseDouble(cantidadParte);

                            if (Tabla1.getValueAt(i, 7) == null) {
                                total = 0;
                            } else {
                                total = Double.parseDouble(Tabla1.getValueAt(i, 7).toString());
                            }

                            String desicion = "SI";
                            if (cantidad > Double.parseDouble(Tabla1.getValueAt(i, 7).toString())) {
                                desicion = null;
                            }

                            String sql = "update Requisiciones set Llego = ?, FechaRecibo = ?, CantRecibida = ?, Ubicacion = ? where Id = ?";
                            PreparedStatement pst = con.prepareStatement(sql);

                            pst.setString(1, desicion);
                            pst.setString(2, fecha);
                            pst.setString(3, Tabla1.getValueAt(i, 7).toString());
                            pst.setString(4, Tabla1.getValueAt(i, 9).toString());
                            pst.setString(5, Tabla1.getValueAt(i, 0).toString());

                            n = pst.executeUpdate();

                            String sql3 = "select Cantidad from inventario where NumeroDeParte like '" + Tabla1.getValueAt(i, 4).toString() + "'";
                            Statement st3 = con.createStatement();
                            ResultSet rs3 = st3.executeQuery(sql3);
                            String cant = "";
                            while (rs3.next()) {
                                cant = rs3.getString("Cantidad");
                            }

                            total = Double.parseDouble(cant) + total;

                            String sql6 = "update inventario set Cantidad = ? where NumeroDeParte = ?";
                            if(Tabla1.getValueAt(i, 5).toString().matches(".*\\d.*")){
                                sql6 = "update inventario set Cantidad = ?, Ubicacion = ? where NumeroDeParte = ?";
                            }
                            PreparedStatement pst6 = con.prepareStatement(sql6);

                            pst6.setString(1, total + "");
                            if(Tabla1.getValueAt(i, 5).toString().matches(".*\\d.*")){
                                pst6.setString(2, Tabla1.getValueAt(i, 9).toString());
                                pst6.setString(3, Tabla1.getValueAt(i, 4).toString());
                            }else {
                                pst6.setString(2, Tabla1.getValueAt(i, 4).toString());
                            }

                            n6 = pst6.executeUpdate();
                        }
                    }
                }
                if (n > 0) {

                    String orden = "select * from OrdenCompra where OrdenNo like '" + id + "'";
                    Statement stOrden = con.createStatement();
                    ResultSet rsOrden = stOrden.executeQuery(orden);
                    String Requi = "";
                    while (rsOrden.next()) {
                        Requi = rsOrden.getString("RequisicionNo");
                    }

                    String sql1 = "select * from Requisiciones where NumRequisicion like '" + Requi + "' and activo like true";
                    ResultSet rs = st.executeQuery(sql1);
                    String datos[] = new String[10];
                    boolean band = true;
                    while (rs.next()) {
                        datos[0] = rs.getString("Llego");
                        if (datos[0] == null) {
                            band = false;
                        }
                    }

                    //-------------------------MANDAR CORREO---------------------------
                    String por3 = "select * from Requisiciones where OC like '" + id + "' and activo like true";
                    Statement st3 = con.createStatement();
                    ResultSet rs3 = st3.executeQuery(por3);
                    String datosp[] = new String[15];
                    while (rs3.next()) {
                        datosp[0] = rs3.getString("OC");
                        datosp[1] = rs3.getString("NumRequisicion");
                        datosp[2] = rs3.getString("Proyecto");
                    }

                    String por4 = "select * from Requisicion where Id like '" + datosp[1] + "'";
                    Statement st4 = con.createStatement();
                    ResultSet rs4 = st4.executeQuery(por4);
                    String numeroEmpleado = "";
                    while (rs4.next()) {
                        numeroEmpleado = rs4.getString("NumeroEmpleado");
                    }

                    String por6 = "select * from registroempleados where NumEmpleado like '" + numeroEmpleado + "'";
                    Statement st6 = con.createStatement();
                    ResultSet rs6 = st6.executeQuery(por6);
                    String correo = "";
                    while (rs6.next()) {
                        correo = rs6.getString("Correo");
                    }

                    boolean ban = false;
                    Stack<String> productos = new Stack<>();
                    Stack<String> productosId = new Stack<>();
                    Stack<String> cantidad = new Stack<>();
                    Stack<String> cant = new Stack<>();
                    Stack<String> proyectos = new Stack<>();
                    javamail mail = new javamail();
                    if (correo == null) {
                        ban = true;
                    } else {
                        for (int i = 0; i < Tabla1.getRowCount(); i++) {
                            if (proyectos.search(Tabla1.getValueAt(i, 5)) == -1){
                                proyectos.add(Tabla1.getValueAt(i, 5).toString());
                            }
                            if (Tabla1.getValueAt(i, 6) != null) {
                                if (Tabla1.getValueAt(i, 6).equals(true)) {
                                    productos.push(Tabla1.getValueAt(i, 2).toString());
                                    productosId.push(Tabla1.getValueAt(i, 0).toString());
                                    cantidad.push(Tabla1.getValueAt(i, 7).toString());
                                    cant.push(Tabla1.getValueAt(i, 3).toString());
                                }
                            }
                        }
                    }
                    //---------------------------------------------------------------------

                    String sql5 = "select * from enviocorreos where Departamento like 'ALMACEN'";
                    Statement st5 = con.createStatement();
                    ResultSet rs5 = st5.executeQuery(sql5);
                    String copia = "";
                    String to = "";
                    int cont = 0;
                    while (rs5.next()) {
                        String c = rs5.getString("Correo");
                        String ubi = rs5.getString("Ubi");
                        if (ubi.equals("CC")) {
                            if (cont == 0) {
                                copia = c;
                            } else {
                                copia += "," + c;
                            }
                        } else {
                            to += "," + to;
                        }
                        cont++;
                    }

                    if (band == true) {
                        String sql2 = "update Requisicion set Progreso = ?, Completado = ? where Id = ?";
                        PreparedStatement pst3 = con.prepareStatement(sql2);

                        pst3.setString(1, "LLEGO, COMPLETO");
                        pst3.setString(2, "SI");
                        pst3.setString(3, datosp[1]);
                        int k = pst3.executeUpdate();

                        if (k > 0) {
                            JOptionPane.showMessageDialog(this, "REQUISCION COMPLETA");
                            for (int i = 0; i < proyectos.size(); i++) {
                                verificarProyectoParaCierre(con, proyectos.get(i));
                            }
                            limpiarTabla();
                            verDatos2();
                            if (ban != true) {
                                mail.sendAlmacen(correo, copia, "Recibo de material requisicion No: " + datosp[1] + "/" + datosp[2], productos, cant, cantidad);
                                crearNotificacion(datosp[1], numeroEmpleado);
                                JFrame f = (JFrame) JOptionPane.getFrameForComponent(this);
                                Factura fact = new Factura(f, true, productosId, cantidad);
                                fact.setVisible(true);
                            }

                        }

                    } else {
                        String sql2 = "update Requisicion set Progreso = ?, Completado = ? where Id = ?";
                        PreparedStatement pst1 = con.prepareStatement(sql2);
                        
                        pst1.setString(1, "LLEGO, INCOMPETO");
                        pst1.setString(2, "NO");
                        pst1.setString(3, datosp[1]);
                        int k = pst1.executeUpdate();

                        if (k > 0) {
                            JOptionPane.showMessageDialog(this, "ARTICULO(S) GUARDADO");
                            for (int i = 0; i < proyectos.size(); i++) {
                                verificarProyectoParaCierre(con, proyectos.get(i));
                            }
                            limpiarTabla();
                            verDatos2();
                            if (ban != true) {
                                mail.sendAlmacen(correo, copia, "Recibo de material requisicion No: " + datosp[1] + "/" + datosp[2], productos, cant, cantidad);
                                crearNotificacion(datosp[1], numeroEmpleado);
                                JFrame f = (JFrame) JOptionPane.getFrameForComponent(this);
                                Factura fact = new Factura(f, true, productosId, cantidad);
                                fact.setVisible(true);
                            }
                        }
                    }

                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "ERROR AL GUARDAR DATOS" + e, "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnArticuloActionPerformed

    private void Tabla1MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Tabla1MouseReleased
        fil = Tabla1.getSelectedRow();
    }//GEN-LAST:event_Tabla1MouseReleased

    private void Tabla1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Tabla1KeyReleased
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            double cantidad = 0;
            double cantidadAcum = 0;
            if (Tabla1.getValueAt(Tabla1.getSelectedRow(), 7) == null) {
                cantidad = 0;
            } else {
                cantidad = Double.parseDouble(Tabla1.getValueAt(Tabla1.getSelectedRow(), 7).toString());
            }

            if (Tabla1.getValueAt(Tabla1.getSelectedRow(), 8) == null) {
                cantidadAcum = 0;
            } else {
                cantidadAcum = Double.parseDouble(Tabla1.getValueAt(Tabla1.getSelectedRow(), 8).toString());
            }
            double cant = cantidad + Double.parseDouble(txtCantidad.getText());
            double cant2 = cantidadAcum + Double.parseDouble(txtCantidad.getText());

            if (cant > Double.parseDouble(Tabla1.getValueAt(Tabla1.getSelectedRow(), 3).toString())) {
                JOptionPane.showMessageDialog(this, "NO PUEDES AGREGAR MAS ARTICULOS DE LOS PEDIDOS", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
            } else {
                Tabla1.setValueAt(cant, Tabla1.getSelectedRow(), 7);
                Tabla1.setValueAt(cant2, Tabla1.getSelectedRow(), 8);
            }
        }
    }//GEN-LAST:event_Tabla1KeyReleased

    private void cancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelarActionPerformed
        JFrame f = (JFrame) JOptionPane.getFrameForComponent(this);
        CancelarOrden co = new CancelarOrden(f, true);
        co.setVisible(true);
    }//GEN-LAST:event_cancelarActionPerformed

    private void btnImprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImprimirActionPerformed
        JFrame f = (JFrame) JOptionPane.getFrameForComponent(this);
        mat = new Material(f, true, 0);
        mat.limpiarTabla();
        Stack<String> mate = new Stack<>();
        String datos[] = new String[Tabla1.getRowCount()];
        for (int i = 0; i < Tabla1.getRowCount(); i++) {
            if ((Tabla1.getValueAt(i, 6) != null)) {
                if (Tabla1.getValueAt(i, 6).toString().equals("true")) {
                    datos[i] = Tabla1.getValueAt(i, 1).toString();
                    mate.push(Tabla1.getValueAt(i, 4).toString());
                }
            }
        }
        mat.impimirRelacion(datos, mate);
        mat.txtPlano.setVisible(false);
        mat.panelImprimir.setVisible(true);
        mat.btnGuardar.addActionListener(this);
        mat.lblMaterial.setText("Relacion de material");
        mat.btnGuardar.setText("Imprimir");
        mat.setVisible(true);
    }//GEN-LAST:event_btnImprimirActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        JFrame f = (JFrame) JOptionPane.getFrameForComponent(this);
        correos c = new correos(f, true, "ALMACEN");
        c.setLocationRelativeTo(null);
        c.setVisible(true);
    }//GEN-LAST:event_jMenuItem2ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable Tabla1;
    private rojeru_san.RSButtonRiple btnArticulo;
    private javax.swing.JButton btnImprimir;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JMenuItem cancelar;
    private javax.swing.JComboBox<String> cbPiso;
    private javax.swing.JComboBox<String> cbRack;
    private javax.swing.JComboBox<String> cbSeccion;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JLabel lblSalir;
    private javax.swing.JPanel pan;
    private javax.swing.JPanel panelRelacion;
    private javax.swing.JPanel panelSalir;
    private javax.swing.JTextField txtCantidad;
    private javax.swing.JTextArea txtDescripcion;
    private javax.swing.JTextField txtId;
    private javax.swing.JTextField txtNumero;
    private rojeru_san.RSMTextFull txtNumeroCotizacion;
    private javax.swing.JTextField txtParte;
    // End of variables declaration//GEN-END:variables

    @Override
    public void actionPerformed(ActionEvent e) {
        if (mat != null) {
            if (e.getSource() == mat.btnGuardar) {
//                PrintService defaultPrintService = PrintServiceLookup.lookupDefaultPrintService();
//
//                // Verificar si se encontró una impresora
//                if (defaultPrintService == null) {
//                    JOptionPane.showMessageDialog(this,"No se encontró ninguna impresora.");
//                    return;
//                }
//                String art = "";
//                for (int i = 0; i < mat.Tabla1.getRowCount(); i++) {
//                    art = art + mat.Tabla1.getValueAt(i, 0).toString() + " | " + mat.Tabla1.getValueAt(i, 1).toString() + " | "
//                            + mat.Tabla1.getValueAt(i, 2).toString() +"\n";
//                }
//                String contenido = "\nRelacion de material\n"
//                        + "Plano | Material | Requisicion\n"
//                        + "\n"
//                        + art;
//
//                try {
//                    Path archivo = Path.of("C:\\Pruebas\\BD\\print\\new.txt");
//                    
//                    Files.write(archivo, contenido.getBytes(), StandardOpenOption.CREATE);
//                    JOptionPane.showMessageDialog(this,"Archivo creado correctamente.");
//                } catch (IOException ex) {
//                    ex.printStackTrace();
//                }
//                
//                // Cargar el archivo de texto
//                String filePath = "C:\\Pruebas\\BD\\print\\new.txt";
//                FileInputStream fileInputStream;
//                try {
//                    
//                    fileInputStream = new FileInputStream(filePath);
//                
//                } catch (FileNotFoundException ex) {
//                    JOptionPane.showMessageDialog(this,"No se encontró el archivo.");
//                    return;
//                }
//
//                // Crear el documento a imprimir
//                
//                DocFlavor docFlavor = DocFlavor.INPUT_STREAM.AUTOSENSE;
//                Doc doc = new SimpleDoc(fileInputStream, docFlavor, null);
//                DocPrintJob printJob = defaultPrintService.createPrintJob();
//
//                // Enviar el trabajo de impresión a la impresora
//                try {
//                    printJob.print(doc, null);
//                } catch (PrintException ex) {
//                    JOptionPane.showMessageDialog(this,"Error al enviar el trabajo de impresión: " + ex.getMessage());
//                }
                crearPdf();
            }
        }
    }
}
