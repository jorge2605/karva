package pruebas;

import Conexiones.Conexion;
import Conexiones.ConexionChat;
import Controlador.compras.DatosOC;
import VentanaEmergente.Compras.AgregarNota;
import VentanaEmergente.Compras.Agrupar;
import VentanaEmergente.Compras.CabezeraCompras;
import VentanaEmergente.Compras.ConfigCompras;
import VentanaEmergente.Compras.CustomCellRenderer;
import VentanaEmergente.Compras.Estado;
import VentanaEmergente.Compras.Fecha;
import VentanaEmergente.Compras.Historial;
import VentanaEmergente.Compras.Noti;
import VentanaEmergente.Compras.OCPendientes;
import VentanaEmergente.Compras.Reclamos;
import VentanaEmergente.Compras.ReporteMes;
import VentanaEmergente.Compras.TransferirRequisicion;
import VentanaEmergente.Compras.enviarCorreo;
import VentanaEmergente.Compras.verificarTotales;
import VentanaEmergente.Compras.entradasAlmacen;
import VentanaEmergente.Inicio1.Espera;
import com.app.sockets.chat.Cliente;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import static org.apache.poi.ss.usermodel.FillPatternType.SOLID_FOREGROUND;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellUtil;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.Stack;
import javax.imageio.ImageIO;
import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumn;
import org.krysalis.barcode4j.impl.code39.Code39Bean;
import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider;

import scrollPane.ScrollBarCustom;

public class OrdenDeCompra extends javax.swing.JInternalFrame implements ActionListener {

    int x, y;

    String numEmpleado;
    public String id;
    public String num;
    public String proy;
    public String cantidad, descripcion, proveedor, id2;
    public int ta;
    public int id1 = -1;
    Stack<ConfigCompras> config;
    ElegirProveedor elegir;
    boolean importar = false;
    String da[];
    String copy;
    int cont = 0;
    int fil = 0;
    Fecha fecha;
    int filaFecha;
    boolean editar;
    Agrupar agr;
    String correo;
    String pass;
    String inicial;

    String proyecto[] = new String[11];

    boolean search = false;

    public String[] dat;

    public final void extraerInicial(String empleado) {
        try {
            Connection con;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            String sql = "select * from registroempleados where NumEmpleado like '" + numEmpleado + "'";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                inicial = rs.getString("Nombre").substring(0, 1).toUpperCase();
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error: " + e, "error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void crearNotificacion() {

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
            String sql2 = "select * from registroempleados where Aprobacion like '1'";
            Statement st2 = con2.prepareCall(sql2);
            ResultSet rs2 = st2.executeQuery(sql2);
            String ip;
            int port;
            String empleado;
            while (rs2.next()) {
                ip = rs2.getString("Ip");
                port = rs2.getInt("Puerto");
                empleado = rs2.getString("NumEmpleado");

                String not = "noti" + empleado;
                String sql = "insert into " + not + " (Departamento,Titulo,Texto,Fecha) values (?,?,?,?)";
                PreparedStatement pst = con.prepareStatement(sql);
                pst.setString(1, "1");
                pst.setString(2, "NUEVA APROBACION");
                pst.setString(3, "TIENES UNA NUEVA APROBACION, LA REQUISICION NUMERO: " + lblRequi.getText());
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
            Logger.getLogger(OrdenDeCompra.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void verPorProyecto(Connection con, DefaultTableModel miModelo, boolean orden) {
        try {
            Statement st = con.createStatement();
            Statement st2 = con.createStatement();
            String sql = "select * from requisiciones where Proyecto like '" + lblRequi.getText() + "' and OC is null and (Aux like  'APROBADO'  or Aux is null)";
            ResultSet rs = st.executeQuery(sql);
            String sql2 = "select * from registroprov_compras order by Nombre";
            ResultSet rs2 = st2.executeQuery(sql2);
            String proveedor;

            JComboBox jcb = new JComboBox();

            while (rs2.next()) {
                proveedor = rs2.getString("Nombre");
                jcb.addItem(proveedor);

            }
            TableColumn tc = Tabla2.getColumnModel().getColumn(7);
            TableCellEditor tce = new DefaultCellEditor(jcb);
            tc.setCellEditor(tce);

            int n = 0;

            String datos[] = new String[10];
            while (rs.next()) {
                datos[0] = rs.getString("Id");
                datos[1] = rs.getString("Descripcion");
                datos[2] = rs.getString("Codigo");
                datos[3] = rs.getString("UM");
                datos[4] = rs.getString("Cantidad");
                datos[5] = rs.getString("Precio");
                datos[7] = rs.getString("Proveedor");
                datos[8] = rs.getString("TE");
                datos[9] = rs.getString("OC");

                miModelo.addRow(datos);

                Statement st20 = con.createStatement();
                String sql20 = "select * from inventario where NumeroDeParte like '" + datos[2] + "'";
                ResultSet rs20 = st20.executeQuery(sql20);
                String sql21 = "insert into inventario (NumeroDeParte, Descripcion, Cantidad, UM,Proveedor) values(?,?,?,?,?)";
                PreparedStatement pst21 = con.prepareStatement(sql21);
                String d[] = new String[10];
                while (rs20.next()) {
                    d[0] = rs20.getString("NumeroDeParte");
                }
                if (d[0] == null) {
                    pst21.setString(1, datos[2]);
                    pst21.setString(2, datos[1]);
                    pst21.setString(3, datos[4]);
                    pst21.setString(4, datos[3]);
                    pst21.setString(5, datos[7]);

                    n = pst21.executeUpdate();

                    if (n > 0) {
                        JOptionPane.showMessageDialog(this, "SE AGREGARON ARTICULOS A LA BASE DE DATOS");
                    }
                }
            }

            if (n > 0) {
                JOptionPane.showMessageDialog(this, "SE AGREGARON ARTICULOS A LA BASE DE DATOS");
            }

            DecimalFormatSymbols separador = new DecimalFormatSymbols();
            separador.setDecimalSeparator('.');
            DecimalFormat formato = new DecimalFormat("#.##", separador);

            for (int i = 0; i < Tabla2.getRowCount(); i++) {

                if (Tabla2.getValueAt(i, 5) != null) {
                    if (Tabla2.getValueAt(i, 5).toString().equals("")) {

                    } else {
                        double precio = Double.parseDouble(Tabla2.getValueAt(i, 5).toString());
                        double cantidad = Double.parseDouble(Tabla2.getValueAt(i, 4).toString());
                        double total = precio * cantidad;

                        Tabla2.setValueAt(formato.format(total), i, 6);
                    }
                }
            }
            CrearOrden.setEnabled(true);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "ERROR: " + e, "ERROR", JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(OrdenDeCompra.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void verOrdenProyecto() {
        DefaultTableModel miModelo = new DefaultTableModel();
        String titulos[] = {"ID", "DESCRIPCION", "NUMERO PARTE", "U.M.", "CANTIDAD", "PRECIO", "TOTAL", "PROVEEDOR", "TE"};
        miModelo = (new DefaultTableModel(null, titulos) {
            boolean[] canEdit = new boolean[]{
                false, true, true, true, true, true, true, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });

        Tabla2.setModel(miModelo);
        try {
            Connection con;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            String sql = "select * from requisiciones where Proyecto like '" + lblRequi.getText() + "' and Aux like 'APROBADO' and OC is null";
            ResultSet rs = st.executeQuery(sql);
            String datos[] = new String[15];
            while (rs.next()) {
                datos[0] = rs.getString("Id");
                datos[1] = rs.getString("Descripcion");
                datos[2] = rs.getString("Codigo");
                datos[3] = rs.getString("UM");
                datos[4] = rs.getString("Cantidad");
                datos[5] = rs.getString("Precio");
                datos[7] = rs.getString("Proveedor");
                datos[8] = rs.getString("TE");
                datos[9] = rs.getString("TE");
                datos[10] = rs.getString("OC");
                miModelo.addRow(datos);
            }
            DecimalFormatSymbols separador = new DecimalFormatSymbols();
            separador.setDecimalSeparator('.');
            DecimalFormat formato = new DecimalFormat("#.##", separador);

            for (int i = 0; i < Tabla2.getRowCount(); i++) {

                if (Tabla2.getValueAt(i, 5) != null) {
                    if (Tabla2.getValueAt(i, 5).toString().equals("")) {

                    } else {
                        double precio = Double.parseDouble(Tabla2.getValueAt(i, 5).toString());
                        double cantidad = Double.parseDouble(Tabla2.getValueAt(i, 4).toString());
                        double total = precio * cantidad;

                        Tabla2.setValueAt(formato.format(total), i, 6);
                    }
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "ERROR: " + e, "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void limpiarTablaImportada() {
        DefaultTableModel miModelo;
        String titulos[] = {"ID", "DESCRIPCION", "NUMERO PARTE", "U.M.", "CANTIDAD", "PRECIO", "TOTAL", "PROVEEDOR", "TE", "NO. ITEM", "CANTIDAD ENCONTRADA"};
        miModelo = (new DefaultTableModel(null, titulos) {
            boolean[] canEdit = new boolean[]{
                false, true, true, true, true, true, true, true, true, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });

        Tabla2.setModel(miModelo);
    }

    public void reducirCantidad() {
        try {
            Connection con;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            for (int i = 0; i < Tabla2.getRowCount(); i++) {
                String id = Tabla2.getValueAt(i, 0).toString();
                String sql = "select cantidadStock, Cantidad, Id from requisiciones where Id like '" + id + "'";
                ResultSet rs = st.executeQuery(sql);
                String c = null;
                double cantidadStock = 0;
                while (rs.next()) {
                    c = rs.getString("Cantidad");
                    cantidadStock = rs.getDouble("cantidadStock");
                }
                double cantidad;
                try {
                    cantidad = Double.parseDouble(c);
                } catch (Exception e) {
                    cantidad = 0;
                }
                if (cantidadStock != 0 && c != null) {
                    double total = cantidad - cantidadStock;
                    Tabla2.setValueAt(total, i, 4);
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "ERROR: " + e, "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void importarLista(String requi) {
        DefaultTableModel miModelo = (DefaultTableModel) Tabla2.getModel();
        try {
            Connection con;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            if (lblTitulo.getText().equals("PROYECTO:")) {
                verPorProyecto(con, miModelo, false);
            } else {
                int tab = Tabla1.getRowCount() - cont;
                if (cont > 0) {
                    tab = Tabla1.getRowCount() - cont - 1;
                }
                if (Tabla1.getSelectedRow() > tab) {
                    //PARTE DE ORDENES EDITADA
                    Statement st = con.createStatement();
                    Statement st2 = con.createStatement();
                    String sql = "select * from requisiciones where OC like '" + requi + "'";
                    ResultSet rs = st.executeQuery(sql);
                    String sql2 = "select * from registroprov_compras order by Nombre";
                    ResultSet rs2 = st2.executeQuery(sql2);
                    String proveedor;

                    JComboBox jcb = new JComboBox();
                    while (rs2.next()) {
                        proveedor = rs2.getString("Nombre");
                        jcb.addItem(proveedor);
                    }
                    TableColumn tc = Tabla2.getColumnModel().getColumn(7);
                    TableCellEditor tce = new DefaultCellEditor(jcb);
                    tc.setCellEditor(tce);

                    String datos[] = new String[15];
                    int n = 0;

                    if (search == true) {
                        Statement st1 = con.createStatement();
                        String sql1 = "select * from requisicionesmuestra where PO like '" + requi + "'";
                        ResultSet rs1 = st1.executeQuery(sql1);
                        String datos1[] = new String[15];

                        while (rs1.next()) {
                            datos1[0] = ("");
                            datos1[1] = rs1.getString("Descripcion");
                            datos1[2] = rs1.getString("Codigo");
                            datos1[3] = rs1.getString("UM");
                            datos1[4] = rs1.getString("Cantidad");
                            datos1[5] = rs1.getString("Precio");
                            datos1[7] = rs1.getString("Proveedor");
                            datos1[8] = rs1.getString("TE");
                            datos1[9] = rs1.getString("TE");
                            datos1[10] = rs1.getString("cantidadStock");
                            datos1[13] = rs1.getString("Estado");
                            if ((datos1[13]) != null) {
                                miModelo.addRow(datos1);
                            }
                        }
                        Statement st20 = con.createStatement();
                        String sql20 = "select * from inventario where NumeroDeParte like '" + datos1[2] + "'";
                        ResultSet rs20 = st20.executeQuery(sql20);
                        String sql21 = "insert into inventario (NumeroDeParte, Descripcion, Cantidad, UM,Proveedor) values(?,?,?,?,?)";
                        PreparedStatement pst21 = con.prepareStatement(sql21);

                        String d[] = new String[10];
                        while (rs20.next()) {
                            d[0] = rs20.getString("NumeroDeParte");
                        }
                        if (d[0] == null) {
                            pst21.setString(1, datos1[2]);
                            pst21.setString(2, datos1[1]);
                            pst21.setString(3, datos1[4]);
                            pst21.setString(4, datos1[3]);
                            pst21.setString(5, datos1[7]);

                            n = pst21.executeUpdate();

                        }
                    } else {
                        int cont = 1;
                        while (rs.next()) {
                            datos[0] = rs.getString("Id");
                            datos[1] = rs.getString("Descripcion");
                            datos[2] = rs.getString("Codigo");
                            datos[3] = rs.getString("UM");
                            datos[4] = rs.getString("Cantidad");
                            datos[5] = rs.getString("Precio");
                            datos[7] = rs.getString("Proveedor");
                            datos[8] = rs.getString("TE");
                            datos[9] = rs.getString("TE");
                            datos[10] = rs.getString("Estado");
                            datos[9] = String.valueOf(cont);
                            cont++;
                            if ((datos[10]) != null) {
                                if ((datos[10]).equals("EDITADO")) {
                                    miModelo.addRow(datos);
                                }
                            }
                            Statement st20 = con.createStatement();
                            String sql20 = "select * from inventario where NumeroDeParte like '" + datos[2] + "'";
                            ResultSet rs20 = st20.executeQuery(sql20);
                            String sql21 = "insert into inventario (NumeroDeParte, Descripcion, Cantidad, UM,Proveedor) values(?,?,?,?,?)";
                            PreparedStatement pst21 = con.prepareStatement(sql21);

                            String d[] = new String[10];
                            while (rs20.next()) {
                                d[0] = rs20.getString("NumeroDeParte");
                            }
                            if (d[0] == null) {
                                pst21.setString(1, datos[2]);
                                pst21.setString(2, datos[1]);
                                pst21.setString(3, datos[4]);
                                pst21.setString(4, datos[3]);
                                pst21.setString(5, datos[7]);

                                n = pst21.executeUpdate();

                            }
                        }
                    }
                    if (n > 0) {
                        JOptionPane.showMessageDialog(this, "SE AGREGARON ARTICULOS A LA BASE DE DATOS");
                    }

                    DecimalFormatSymbols separador = new DecimalFormatSymbols();
                    separador.setDecimalSeparator('.');
                    DecimalFormat formato = new DecimalFormat("#.##", separador);
                    reducirCantidad();
                    for (int i = 0; i < Tabla2.getRowCount(); i++) {

                        if (Tabla2.getValueAt(i, 5) != null) {
                            if (Tabla2.getValueAt(i, 5).toString().equals("")) {

                            } else {
                                double precio = Double.parseDouble(Tabla2.getValueAt(i, 5).toString());
                                double cantidad = Double.parseDouble(Tabla2.getValueAt(i, 4).toString());
                                double total = precio * cantidad;

                                Tabla2.setValueAt(formato.format(total), i, 6);
                            }
                        }
                    }
                    if (Tabla1.getValueAt(Tabla1.getSelectedRow(), 0).toString().equals("APROBADO")
                            || Tabla1.getValueAt(Tabla1.getSelectedRow(), 0).toString().equals("COMPRADO")) {
                        CrearOrden.setEnabled(true);
                    }

                } else if (tab == 0) {
                } else {
                    //REQUISICIONES PARTE DE ARRIBA
                    Statement st = con.createStatement();
                    Statement st2 = con.createStatement();
                    int fila = Tabla1.getSelectedRow();
                    String sql = "select * from requisiciones where NumRequisicion like '" + requi + "'";
                    ResultSet rs = st.executeQuery(sql);
                    String sql2 = "select * from registroprov_compras order by Nombre";
                    ResultSet rs2 = st2.executeQuery(sql2);
                    String proveedor;

                    JComboBox jcb = new JComboBox();

                    while (rs2.next()) {
                        proveedor = rs2.getString("Nombre");
                        jcb.addItem(proveedor);
                    }
                    TableColumn tc = Tabla2.getColumnModel().getColumn(7);
                    TableCellEditor tce = new DefaultCellEditor(jcb);
                    tc.setCellEditor(tce);

                    int n = 0;
                    if (search == true) {
                        Statement st1 = con.createStatement();
                        String sql1 = "select * from requisicionesmuestra where NumRequisicion like '" + requi + "'";
                        ResultSet rs1 = st1.executeQuery(sql1);
                        String datos1[] = new String[15];

                        while (rs1.next()) {
                            datos1[0] = rs1.getString("Id");
                            datos1[1] = rs1.getString("Descripcion");
                            datos1[2] = rs1.getString("Codigo");
                            datos1[3] = rs1.getString("UM");
                            datos1[4] = rs1.getString("Cantidad");
                            datos1[5] = rs1.getString("Precio");
                            datos1[7] = rs1.getString("Proveedor");
                            datos1[8] = rs1.getString("TE");
                            datos1[9] = rs1.getString("TE");
                            datos1[10] = rs1.getString("cantidadStock");
                            datos1[13] = rs1.getString("OC");
                            if ((datos1[13]) == null) {
                                miModelo.addRow(datos1);
                            }

                            Statement st20 = con.createStatement();
                            String sql20 = "select * from inventario where NumeroDeParte like '" + datos1[2] + "'";
                            ResultSet rs20 = st20.executeQuery(sql20);
                            String sql21 = "insert into inventario (NumeroDeParte, Descripcion, Cantidad, UM,Proveedor) values(?,?,?,?,?)";
                            PreparedStatement pst21 = con.prepareStatement(sql21);

                            String d[] = new String[10];
                            while (rs20.next()) {
                                d[0] = rs20.getString("NumeroDeParte");
                            }
                            if (d[0] == null) {
                                pst21.setString(1, datos1[2]);
                                pst21.setString(2, datos1[1]);
                                pst21.setString(3, datos1[4]);
                                pst21.setString(4, datos1[3]);
                                pst21.setString(5, datos1[7]);

                                n = pst21.executeUpdate();
                            }
                        }
                    } else {
                        String datos[] = new String[15];
                        int cont = 1;
                        while (rs.next()) {
                            datos[0] = rs.getString("Id");
                            datos[1] = rs.getString("Descripcion");
                            datos[2] = rs.getString("Codigo");
                            datos[3] = rs.getString("UM");
                            datos[4] = rs.getString("Cantidad");
                            datos[5] = rs.getString("Precio");
                            datos[7] = rs.getString("Proveedor");
                            datos[8] = rs.getString("TE");
                            datos[10] = rs.getString("cantidadStock");
                            datos[9] = String.valueOf(cont);
                            cont++;
                            datos[13] = rs.getString("OC");
                            if ((datos[13]) == null) {
                                miModelo.addRow(datos);
                            }
                            Statement st20 = con.createStatement();
                            String sql20 = "select NumeroDeParte from inventario where NumeroDeParte like '" + datos[2] + "'";
                            ResultSet rs20 = st20.executeQuery(sql20);
                            String sql21 = "insert into inventario (NumeroDeParte, Descripcion, Cantidad, UM,Proveedor) values(?,?,?,?,?)";
                            PreparedStatement pst21 = con.prepareStatement(sql21);

                            String d[] = new String[10];
                            while (rs20.next()) {
                                d[0] = rs20.getString("NumeroDeParte");
                            }
                            if (d[0] == null) {
                                pst21.setString(1, datos[2]);
                                pst21.setString(2, datos[1]);
                                pst21.setString(3, datos[4]);
                                pst21.setString(4, datos[3]);
                                pst21.setString(5, datos[7]);

                                n = pst21.executeUpdate();

                                if (n > 0) {
                                    JOptionPane.showMessageDialog(this, "SE AGREGARON ARTICULOS A LA BASE DE DATOS");
                                }
                            }
                        }
                    }
                    if (n > 0) {
                        JOptionPane.showMessageDialog(this, "SE AGREGARON ARTICULOS A LA BASE DE DATOS");
                    }

                    DecimalFormatSymbols separador = new DecimalFormatSymbols();
                    separador.setDecimalSeparator('.');
                    DecimalFormat formato = new DecimalFormat("#.##", separador);
                    reducirCantidad();
                    for (int i = 0; i < Tabla2.getRowCount(); i++) {
                        if (Tabla2.getValueAt(i, 5) != null) {
                            if (Tabla2.getValueAt(i, 5).toString().equals("")) {
                            } else {
                                double precio = Double.parseDouble(Tabla2.getValueAt(i, 5).toString());
                                double cantidad = Double.parseDouble(Tabla2.getValueAt(i, 4).toString());
                                double total = precio * cantidad;
                                Tabla2.setValueAt(formato.format(total), i, 6);
                            }
                        }
                    }
                    if (Tabla1.getValueAt(Tabla1.getSelectedRow(), 0).toString().equals("APROBADO")
                            || Tabla1.getValueAt(Tabla1.getSelectedRow(), 0).toString().equals("COMPRADO")
                            || Tabla1.getValueAt(Tabla1.getSelectedRow(), 0).toString().equals("LLEGO, INCOMPETO")) {
                        CrearOrden.setEnabled(true);
                    }
                }
            }

            verGastos();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "ERROR: " + e, "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void guardarListaImportada() {
        try {
            Connection con = null;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            int tab = Tabla1.getRowCount() - cont;
            if (cont > 0) {
                tab = Tabla1.getRowCount() - cont - 1;
            }
            if (Tabla1.getSelectedRow() > tab) {
                //PARTES EDITADAS PARTE DE ABAJO
                for (int i = 0; i < Tabla2.getRowCount(); i++) {
                    if (Tabla2.getValueAt(i, 0) == null || Tabla2.getValueAt(i, 0).toString().equals("")) {
                        String sql3 = "select * from Inventario where NumeroDeParte like '" + Tabla2.getValueAt(i, 2).toString() + "'";
                        Statement st = con.createStatement();
                        ResultSet rs = st.executeQuery(sql3);
                        String np = "";
                        int n = 0;
                        while (rs.next()) {
                            np = rs.getString("NumeroDeParte");
                        }
                        if (np == null) {
                            String inse = "insert into inventario (NumeroDeParte,Descripcion,Cantidad,UM,Proveedor) values(?,?,?,?,?)";
                            PreparedStatement pst = con.prepareStatement(inse);
                            pst.setString(1, Tabla2.getValueAt(i, 2).toString());
                            pst.setString(2, Tabla2.getValueAt(i, 1).toString());
                            pst.setString(3, Tabla2.getValueAt(i, 4).toString());
                            pst.setString(4, Tabla2.getValueAt(i, 3).toString());
                            pst.setString(5, Tabla2.getValueAt(i, 7).toString());

                            n = pst.executeUpdate();
                            if (n > 0) {
                                JOptionPane.showMessageDialog(this, "SE AGREGARON NUEVAS PARTIDAS");
                            }

                        } else if (np.equals("")) {
                            String inse = "insert into inventario (NumeroDeParte,Descripcion,Cantidad,UM,Proveedor) values(?,?,?,?,?)";
                            PreparedStatement pst = con.prepareStatement(inse);
                            pst.setString(1, Tabla2.getValueAt(i, 2).toString());
                            pst.setString(2, Tabla2.getValueAt(i, 1).toString());
                            pst.setString(3, Tabla2.getValueAt(i, 4).toString());
                            pst.setString(4, Tabla2.getValueAt(i, 3).toString());
                            pst.setString(5, Tabla2.getValueAt(i, 7).toString());

                            n = pst.executeUpdate();

                            if (n > 0) {
                                JOptionPane.showMessageDialog(this, "SE AGREGARON NUEVAS PARTIDAS");
                            }
                        }

                        String bus = "select * from requisiciones where Id like '" + Tabla2.getValueAt(0, 0).toString() + "'";
                        Statement st2 = con.createStatement();
                        ResultSet rs2 = st2.executeQuery(bus);
                        String dat[] = new String[10];
                        while (rs2.next()) {
                            dat[0] = rs2.getString("NumRequisicion");
                            dat[1] = rs2.getString("Proyecto");
                            dat[2] = rs2.getString("Requisitor");
                            dat[3] = rs2.getString("OC");
                        }
                        String req = "insert into requisiciones (NumRequisicion,Codigo,Descripcion,Proyecto,Cantidad,Requisitor,UM,Proveedor,TE,Estado,OC) values (?,?,?,?,?,?,?,?,?,?,?)";
                        PreparedStatement pst2 = con.prepareStatement(req);
                        pst2.setString(1, dat[0]);
                        pst2.setString(2, Tabla2.getValueAt(i, 2).toString());
                        pst2.setString(3, Tabla2.getValueAt(i, 1).toString());
                        pst2.setString(4, dat[1]);
                        pst2.setString(5, Tabla2.getValueAt(i, 4).toString());
                        pst2.setString(6, dat[2]);
                        pst2.setString(7, Tabla2.getValueAt(i, 3).toString());
                        pst2.setString(8, Tabla2.getValueAt(i, 7).toString());

                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        Date fec = null;
                        String TE;
                        if (Tabla2.getValueAt(i, 8) == null) {
                            TE = "";
                        } else {
                            TE = Tabla2.getValueAt(i, 8).toString();
                        }
                        try {
                            fec = sdf.parse(TE);
                        } catch (ParseException ex) {
                            Logger.getLogger(OrdenDeCompra.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        if (fec == null) {
                            pst2.setDate(9, null);
                        } else {
                            java.sql.Date data = new java.sql.Date(fec.getTime());
                            pst2.setDate(9, data);
                        }
                        pst2.setString(10, "EDITADO");
                        pst2.setString(11, dat[3]);
                        int n1 = pst2.executeUpdate();

                        if (n > 0 && n1 > 0) {
                            JOptionPane.showMessageDialog(this, "SE AGREGARON NUEVAS PARTIDAS");
                        }
                    }
                }
                String sql = "update requisiciones set Descripcion = ?, Codigo = ?, UM = ?, Cantidad = ?, Precio = ?,Proveedor = ?, TE = ? where Id = ?";
                PreparedStatement pst = con.prepareStatement(sql);
                String sql2 = "update inventario set Descripcion = ?, UM = ?,Proveedor = ? where NumeroDeParte = ?";
                PreparedStatement pst2 = con.prepareStatement(sql2);
                int n = 0, n2 = 0;
                for (int i = 0; i < Tabla2.getRowCount(); i++) {
                    String UM, Precio, Proveedor, TE;
                    double cantStock, cantidad;
                    try {
                        cantStock = Double.parseDouble(Tabla2.getValueAt(i, 10).toString());
                    } catch (Exception e) {
                        cantStock = 0;
                    }
                    try {
                        cantidad = Double.parseDouble(Tabla2.getValueAt(i, 4).toString());
                    } catch (Exception e) {
                        cantidad = 0;
                    }

                    if (Tabla2.getValueAt(i, 3) == null) {
                        UM = "";
                    } else {
                        UM = Tabla2.getValueAt(i, 3).toString();
                    }

                    if (Tabla2.getValueAt(i, 5) == null) {
                        Precio = "";
                    } else {
                        Precio = Tabla2.getValueAt(i, 5).toString();
                    }

                    if (Tabla2.getValueAt(i, 7) == null) {
                        Proveedor = "";
                    } else {
                        Proveedor = Tabla2.getValueAt(i, 7).toString();
                    }

                    if (Tabla2.getValueAt(i, 8) == null) {
                        TE = "";
                    } else {
                        TE = Tabla2.getValueAt(i, 8).toString();
                    }

                    if (search == true) {

                        String sql4 = "select * from requisiciones where OC like '" + lblRequi.getText() + "'";
                        Statement st4 = con.createStatement();
                        ResultSet rs4 = st4.executeQuery(sql4);

                        String sql5 = "update requisicionesmuestra set Descripcion = ?, Codigo = ?, UM = ?, Cantidad = ?, Precio = ?,Proveedor = ?, TE = ? where Id = ?";
                        PreparedStatement pst5 = con.prepareStatement(sql5);

                        pst5.setString(1, Tabla2.getValueAt(i, 1).toString());
                        pst5.setString(2, Tabla2.getValueAt(i, 2).toString());
                        pst5.setString(3, UM);
                        pst5.setString(4, String.valueOf(cantidad + cantStock));
                        pst5.setString(5, Precio);
                        pst5.setString(6, Proveedor);
                        pst5.setString(7, TE);
                        pst5.setString(8, Tabla2.getValueAt(i, 0).toString());

                        pst5.executeUpdate();
                        String idd = "";
                        String num = "";
                        int cont = 0;
                        while (rs4.next()) {
                            idd = rs4.getString("Id");
                            num = rs4.getString("Codigo");

                            if (num.equals(Tabla2.getValueAt(i, 2).toString())) {
                                pst.setString(1, Tabla2.getValueAt(i, 1).toString());
                                pst.setString(2, Tabla2.getValueAt(i, 2).toString());
                                pst.setString(3, UM);
                                pst.setString(4, String.valueOf(cantidad + cantStock));
                                pst.setString(5, Precio);
                                pst.setString(6, Proveedor);
                                pst.setString(7, TE);
                                pst.setString(8, idd);

                                n = pst.executeUpdate();

                                pst2.setString(1, Tabla2.getValueAt(i, 1).toString());
                                pst2.setString(2, UM);
                                pst2.setString(3, Proveedor);
                                pst2.setString(4, Tabla2.getValueAt(i, 2).toString());

                                n2 = pst2.executeUpdate();
                            }

                        }
                    } else {
                        pst.setString(1, Tabla2.getValueAt(i, 1).toString());
                        pst.setString(2, Tabla2.getValueAt(i, 2).toString());
                        pst.setString(3, UM);
                        pst.setString(4, String.valueOf(cantidad + cantStock));
                        pst.setString(5, Precio);
                        pst.setString(6, Proveedor);
                        pst.setString(7, TE);
                        pst.setString(8, Tabla2.getValueAt(i, 0).toString());

                        n = pst.executeUpdate();

                        pst2.setString(1, Tabla2.getValueAt(i, 1).toString());
                        pst2.setString(2, UM);
                        pst2.setString(3, Proveedor);
                        pst2.setString(4, Tabla2.getValueAt(i, 2).toString());

                        n2 = pst2.executeUpdate();
                    }

                    if (n == 0) {
                        JOptionPane.showMessageDialog(this, "NO SE PUDO GUARDAR EN REQUISICIONES EN LA FILA NO. " + i);
                    }
                    if (n2 == 0) {
                        JOptionPane.showMessageDialog(this, "NO SE PUDO GUARDAR EN INVENTARIO EN LA FILA NO. " + i);
                    }
                }
                if (lblRequi.getText().contains("-")) {
                    Stack<String> pila = getLista();
                    for (int i = 0; i < pila.size(); i++) {
                        importarLista(pila.get(i));
                    }
                } else {
                    importarLista(lblRequi.getText());
                }
            } else if (tab == 0) {
                //PARTE DEL MEDIO
            } else {
                //-------------------------
                //-------------------------
                for (int i = 0; i < Tabla2.getRowCount(); i++) {
                    //PARTE DE LAS REQUISICIONES PARTE DE ARRIBA
                    if (Tabla2.getValueAt(i, 0).equals("")) {
                        String sql3 = "select * from Inventario where NumeroDeParte like '" + Tabla2.getValueAt(i, 2).toString() + "'";
                        Statement st = con.createStatement();
                        ResultSet rs = st.executeQuery(sql3);
                        String np = "";
                        int n = 0;
                        while (rs.next()) {
                            np = rs.getString("NumeroDeParte");
                        }
                        if (np == null) {
                            String inse = "insert into inventario (NumeroDeParte,Descripcion,Cantidad,UM,Proveedor) values(?,?,?,?,?)";
                            PreparedStatement pst = con.prepareStatement(inse);
                            pst.setString(1, Tabla2.getValueAt(i, 2).toString());
                            pst.setString(2, Tabla2.getValueAt(i, 1).toString());
                            pst.setString(3, "0");
                            pst.setString(4, Tabla2.getValueAt(i, 3).toString());
                            pst.setString(5, Tabla2.getValueAt(i, 7).toString());

                            n = pst.executeUpdate();

                        } else if (np.equals("")) {
                            String inse = "insert into inventario (NumeroDeParte,Descripcion,Cantidad,UM,Proveedor) values(?,?,?,?,?)";
                            PreparedStatement pst = con.prepareStatement(inse);
                            pst.setString(1, Tabla2.getValueAt(i, 2).toString());
                            pst.setString(2, Tabla2.getValueAt(i, 1).toString());
                            pst.setString(3, "0");
                            pst.setString(4, Tabla2.getValueAt(i, 3).toString());
                            pst.setString(5, Tabla2.getValueAt(i, 7).toString());

                            n = pst.executeUpdate();

                        }

                        String bus = "select * from requisiciones where Id like '" + Tabla2.getValueAt(0, 0) + "'";
                        Statement st2 = con.createStatement();
                        ResultSet rs2 = st2.executeQuery(bus);
                        String dat[] = new String[10];
                        while (rs2.next()) {
                            dat[0] = rs2.getString("NumRequisicion");
                            dat[1] = rs2.getString("Proyecto");
                            dat[2] = rs2.getString("Requisitor");
                        }
                        String req = "insert into requisiciones (NumRequisicion,Codigo,Descripcion,Proyecto,Cantidad,Requisitor,UM,Proveedor,TE) values (?,?,?,?,?,?,?,?,?)";
                        PreparedStatement pst2 = con.prepareStatement(req);
                        pst2.setString(1, dat[0]);
                        pst2.setString(2, Tabla2.getValueAt(i, 2).toString());
                        pst2.setString(3, Tabla2.getValueAt(i, 1).toString());
                        pst2.setString(4, dat[1]);
                        pst2.setString(5, "0");
                        pst2.setString(6, dat[2]);
                        pst2.setString(7, Tabla2.getValueAt(i, 3).toString());
                        pst2.setString(8, Tabla2.getValueAt(i, 7).toString());
                        String TE = "";
                        if (Tabla2.getValueAt(i, 8) != null) {
                            TE = Tabla2.getValueAt(i, 8).toString();
                        }
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        Date fec = null;
                        if (TE.equals("")) {
                            pst2.setDate(9, null);
                        } else {
                            try {
                                fec = sdf.parse(TE);
                            } catch (ParseException ex) {
                                fec = null;
                            }
                            if (fec == null) {
                                pst2.setDate(9, null);
                            } else {
                                java.sql.Date data = new java.sql.Date(fec.getTime());
                                pst2.setDate(9, data);
                            }
                        }
                        int n1 = pst2.executeUpdate();

                        if (n > 0 && n1 > 0) {
                            JOptionPane.showMessageDialog(this, "SE AGREGARON NUEVAS PARTIDAS");
                        }
                    }
                }
                String sql = "update requisiciones set Descripcion = ?, Codigo = ?, UM = ?, Cantidad = ?, Precio = ?,Proveedor = ?, TE = ? where Id = ?";
                PreparedStatement pst = con.prepareStatement(sql);
                String sql2 = "update inventario set Descripcion = ?, UM = ?,Proveedor = ? where NumeroDeParte = ?";
                PreparedStatement pst2 = con.prepareStatement(sql2);
                int n = 0, n2 = 0;

                for (int i = 0; i < Tabla2.getRowCount(); i++) {
                    String UM, Precio, Proveedor, TE;
                    double cantidad, canSt;
                    try {
                        canSt = Double.parseDouble(Tabla2.getValueAt(i, 10).toString());
                    } catch (Exception e) {
                        canSt = 0;
                    }
                    try {
                        cantidad = Double.parseDouble(Tabla2.getValueAt(i, 4).toString());
                    } catch (Exception e) {
                        cantidad = 0;
                    }

                    if (Tabla2.getValueAt(i, 3) == null) {
                        UM = "";
                    } else {
                        UM = Tabla2.getValueAt(i, 3).toString();
                    }

                    if (Tabla2.getValueAt(i, 5) == null) {
                        Precio = "";
                    } else {
                        Precio = Tabla2.getValueAt(i, 5).toString();
                    }

                    if (Tabla2.getValueAt(i, 7) == null) {
                        Proveedor = "";
                    } else {
                        Proveedor = Tabla2.getValueAt(i, 7).toString();
                    }

                    if (Tabla2.getValueAt(i, 8) == null) {
                        TE = "";
                    } else {
                        TE = Tabla2.getValueAt(i, 8).toString();
                    }
                    //--------------------------------MUESTRA--------------------------------
                    if (search == true) {

                        String sql4 = "select Id,Codigo from requisicionesmuestra where Id like '" + Tabla2.getValueAt(i, 0).toString() + "'";
                        Statement st4 = con.createStatement();
                        ResultSet rs4 = st4.executeQuery(sql4);

                        String sql5 = "update requisicionesmuestra set Descripcion = ?, Codigo = ?, UM = ?, Cantidad = ?, Precio = ?,Proveedor = ?, TE = ? where Id = ?";
                        PreparedStatement pst5 = con.prepareStatement(sql5);

                        pst5.setString(1, Tabla2.getValueAt(i, 1).toString());
                        pst5.setString(2, Tabla2.getValueAt(i, 2).toString());
                        pst5.setString(3, UM);
                        pst5.setString(4, String.valueOf(cantidad + canSt));
                        pst5.setString(5, Precio);
                        pst5.setString(6, Proveedor);

                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        if (TE.equals("")) {
                            pst5.setDate(7, null);
                        } else {
                            Date fec = null;
                            try {
                                fec = sdf.parse(TE);
                            } catch (ParseException ex) {
                                fec = null;
                            }
                            if (fec == null) {
                                pst5.setDate(7, null);
                            } else {
                                java.sql.Date data = new java.sql.Date(fec.getTime());
                                pst5.setDate(7, data);
                            }
                        }

                        pst5.setString(8, Tabla2.getValueAt(i, 0).toString());

                        pst5.executeUpdate();
                        String idd = "";
                        String num = "";
                        int cont = 0;
                        sql = "update requisiciones set Descripcion = ?, Codigo = ?, UM = ?, Cantidad = ?, Precio = ?,Proveedor = ?, TE = ? where Codigo = ?";
                        pst = con.prepareStatement(sql);
                        while (rs4.next()) {
//                        idd = rs4.getString("IdArticulo");
                            num = rs4.getString("Codigo");
                            if (num.equals(Tabla2.getValueAt(i, 2).toString())) {
                                pst.setString(1, Tabla2.getValueAt(i, 1).toString());
                                pst.setString(2, Tabla2.getValueAt(i, 2).toString());
                                pst.setString(3, UM);
                                pst.setString(4, String.valueOf(cantidad + canSt));
                                pst.setString(5, Precio);
                                pst.setString(6, Proveedor);

                                SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
                                Date fec1 = null;
                                try {
                                    fec1 = sdf.parse(TE);
                                } catch (ParseException ex) {
                                    fec1 = null;
                                }
                                if (fec1 == null) {
                                    pst.setDate(7, null);
                                } else {
                                    java.sql.Date data1 = new java.sql.Date(fec1.getTime());
                                    pst.setDate(7, data1);
                                }
                                pst.setString(8, num);

                                n = pst.executeUpdate();

                                pst2.setString(1, Tabla2.getValueAt(i, 1).toString());
                                pst2.setString(2, UM);
                                pst2.setString(3, Proveedor);
                                pst2.setString(4, Tabla2.getValueAt(i, 2).toString());

                                n2 = pst2.executeUpdate();
                            }
                        }
                    } else //-------------------------------------------------------------------------------------
                    {
                        pst.setString(1, Tabla2.getValueAt(i, 1).toString());
                        pst.setString(2, Tabla2.getValueAt(i, 2).toString());
                        pst.setString(3, UM);
                        pst.setString(4, String.valueOf(cantidad + canSt));
                        pst.setString(5, Precio);
                        pst.setString(6, Proveedor);
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

                        if (TE.equals("")) {
                            pst.setDate(7, null);
                        } else {
                            Date fec = null;
                            try {
                                fec = sdf.parse(TE);
                            } catch (ParseException ex) {
                                fec = null;
                            }
                            if (fec == null) {
                                pst.setDate(7, null);
                            } else {
                                java.sql.Date data = new java.sql.Date(fec.getTime());
                                pst.setDate(7, data);
                            }

                        }
                        pst.setString(8, Tabla2.getValueAt(i, 0).toString());
                        n = pst.executeUpdate();

                        pst2.setString(1, Tabla2.getValueAt(i, 1).toString());
                        pst2.setString(2, UM);
                        pst2.setString(3, Proveedor);
                        pst2.setString(4, Tabla2.getValueAt(i, 2).toString());

                        n2 = pst2.executeUpdate();
                    }

                    if (n == 0) {
                        JOptionPane.showMessageDialog(this, "NO SE PUDO GUARDAR EN REQUISICIONES EN LA FILA NO. " + (i + 1));
                    }
                    if (n2 == 0) {
                        JOptionPane.showMessageDialog(this, "NO SE PUDO GUARDAR EN INVENTARIO EN LA FILA NO. " + (i + 1));
                    }
                }

                if (lblRequi.getText().contains("-")) {
                    Stack<String> pila = getLista();
                    for (int i = 0; i < pila.size(); i++) {
                        importarLista(pila.get(i));
                    }
                } else {
                    importarLista(lblRequi.getText());
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "ERROR: " + e, "ERROR", JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(Inicio1.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void Exportar() {
        Workbook book;
        String mensaje = "Error en la Exportacion";
        String nombreArchivo = "\\\\192.168.100.40\\bd\\OC\\Requisicion_de_Compra\\Requisicion_de_Compra_" + id + ".xlsx";
        int NumeroFila = Tabla2.getRowCount(), NumeroColumna = Tabla2.getColumnCount();
        if (nombreArchivo.endsWith("xls")) {
            book = new HSSFWorkbook();
        } else {
            book = new XSSFWorkbook();
        }

        Sheet hoja = book.createSheet("Requisicion de compra");
        Row row = hoja.createRow(4);
        Cell cell = row.createCell(1);
        Row shuma = hoja.createRow(0);
        Cell columna = shuma.createCell(1);
        CellStyle estilo = book.createCellStyle();
        CellStyle style = book.createCellStyle();

        hoja.setColumnWidth(1, 6200);
        hoja.setColumnWidth(2, 4200);
        hoja.setColumnWidth(3, 3200);
        hoja.setColumnWidth(4, 8200);
        hoja.setColumnWidth(5, 6200);
        hoja.setColumnWidth(6, 6200);
        hoja.setColumnWidth(7, 5200);

        Font font = book.createFont();
        font.setBold(true);
        font.setColor(IndexedColors.WHITE.getIndex());
        font.setFontHeightInPoints((short) 12);
        estilo.setFont(font);

        Font font1 = book.createFont();
        font1.setBold(true);
        font1.setColor(IndexedColors.BLACK.getIndex());
        font1.setFontHeightInPoints((short) 14);
        style.setFont(font1);

        style.setFillBackgroundColor(IndexedColors.WHITE.getIndex());
        style.setFillForegroundColor(IndexedColors.WHITE.getIndex());
        style.setFillPattern(SOLID_FOREGROUND);
        style.setVerticalAlignment(VerticalAlignment.BOTTOM);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setWrapText(true);

        estilo.setFillBackgroundColor(IndexedColors.WHITE.getIndex());
        estilo.setFillForegroundColor(IndexedColors.GREY_80_PERCENT.getIndex());
        estilo.setFillPattern(SOLID_FOREGROUND);
        estilo.setVerticalAlignment(VerticalAlignment.CENTER);
        estilo.setAlignment(HorizontalAlignment.CENTER);

        columna.setCellValue("SERVICIOS INDUSTRIALES 3I S DE RL MI");
        cell.setCellValue("REQUISICION DE COMPRA");
        cell.setCellStyle(estilo);
        columna.setCellStyle(style);

        hoja.addMergedRegion(new CellRangeAddress(
                0,
                3,
                1,
                7
        ));
        hoja.addMergedRegion(new CellRangeAddress(
                4,
                4,
                1,
                7
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

        try {
            InputStream is = new FileInputStream("C:/Pruebas/BD/3i.png");
            byte[] bytes = IOUtils.toByteArray(is);
            int pictureIdx = book.addPicture(bytes, Workbook.PICTURE_TYPE_JPEG);
            is.close();
            CreationHelper helper = book.getCreationHelper();
            Drawing drawing = hoja.createDrawingPatriarch();
            ClientAnchor anchor = helper.createClientAnchor();
            anchor.setCol1(1);
            anchor.setCol2(3);
            anchor.setRow1(0);
            anchor.setRow2(3);
            Picture pict = drawing.createPicture(anchor, pictureIdx);
            pict.resize();
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(this, e);
        } catch (IOException ex) {
            Logger.getLogger(OrdenDeCompra.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, ex);
        }
        try {
            for (int i = -1; i < NumeroFila; i++) {
                Row fila = hoja.createRow(i + 6);
                for (int j = 0; j < NumeroColumna; j++) {
                    Cell celda = fila.createCell(j + 1);
                    if (i == -1 && (j >= 0 && j <= 6)) {
                        CellStyle s = book.createCellStyle();
                        Font f = book.createFont();
                        f.setBold(true);
                        s.setFont(f);
                        celda.setCellStyle(s);
                    }
                    if (i > -1 && (j > -1 && j <= 6) && (i % 2 == 0)) {
                        CellStyle s = book.createCellStyle();
                        s.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
                        s.setFillPattern(SOLID_FOREGROUND);
                        celda.setCellStyle(s);
                    }

                    if (i == -1) {
                        celda.setCellValue(String.valueOf(Tabla2.getColumnName(j)));
                        CellUtil.setCellStyleProperties(celda, properties);
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
                        celda.setCellValue(String.valueOf(Tabla2.getValueAt(i, j)));
                        CellUtil.setCellStyleProperties(celda, properties);

                    }

                    book.write(new FileOutputStream("C:\\Users\\AlmacenPC\\OneDrive\\Documents\\pdf\\PDF 475\\"));
                }
            }
            book.close();
            mensaje = "Exportacion Exitosa";
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e);
        }
        JOptionPane.showMessageDialog(this, mensaje);
    }

    public void ExportarCoti() {
        Workbook book;
        String mensaje = "Error en la Exportacion";
        String nombreArchivo = "\\\\192.168.100.40\\bd\\OC\\Solicitud_de_cotizacion\\Solicitud_de_cotizacion_" + id + ".xlsx";
        int NumeroFila = Tabla2.getRowCount(), NumeroColumna = Tabla2.getColumnCount();
        if (nombreArchivo.endsWith("xls")) {
            book = new HSSFWorkbook();
        } else {
            book = new XSSFWorkbook();
        }

        Sheet hoja = book.createSheet("Solicitud de cotizacion");
        Row shuma = hoja.createRow(0);
        Cell columna = shuma.createCell(1);
        CellStyle style = book.createCellStyle();

        hoja.setColumnWidth(1, 3200);
        hoja.setColumnWidth(2, 10200);
        hoja.setColumnWidth(3, 6200);
        hoja.setColumnWidth(4, 4200);
        hoja.setColumnWidth(5, 4200);
        hoja.setColumnWidth(6, 6200);

        Font font1 = book.createFont();
        font1.setColor(IndexedColors.BLACK.getIndex());
        font1.setFontHeightInPoints((short) 12);
        font1.setFontName("Arial Nova");
        style.setFont(font1);

        style.setFillBackgroundColor(IndexedColors.WHITE.getIndex());
        style.setFillForegroundColor(IndexedColors.WHITE.getIndex());
        style.setFillPattern(SOLID_FOREGROUND);
        style.setVerticalAlignment(VerticalAlignment.BOTTOM);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setWrapText(true);

        columna.setCellValue("SOLICITUD DE COTIZACION");
        columna.setCellStyle(style);

        hoja.addMergedRegion(new CellRangeAddress(
                0,
                3,
                1,
                6
        ));

        Row fi = hoja.createRow(NumeroFila + 1 + 4);
        Cell ce = fi.createCell(1);
        ce.setCellValue("NOTA: No es una orden de compra");
        CellStyle ss = book.createCellStyle();
        ss.setVerticalAlignment(VerticalAlignment.BOTTOM);
        ss.setAlignment(HorizontalAlignment.RIGHT);
        Font ff = book.createFont();
        ff.setBold(true);
        ff.setFontHeightInPoints((short) 14);
        ss.setFont(ff);
        ce.setCellStyle(ss);
        hoja.addMergedRegion(new CellRangeAddress(
                NumeroFila + 1 + 4,
                NumeroFila + 2 + 4,
                1,
                6
        ));

        Map<String, Object> properties = new HashMap<String, Object>();
        properties.put(CellUtil.BORDER_LEFT, BorderStyle.MEDIUM);
        properties.put(CellUtil.BORDER_RIGHT, BorderStyle.MEDIUM);

        properties.put(CellUtil.LEFT_BORDER_COLOR, IndexedColors.BLACK.getIndex());
        properties.put(CellUtil.RIGHT_BORDER_COLOR, IndexedColors.BLACK.getIndex());

        try {
            InputStream is = new FileInputStream("C:/Pruebas/BD/3i.png");
            byte[] bytes = IOUtils.toByteArray(is);
            int pictureIdx = book.addPicture(bytes, Workbook.PICTURE_TYPE_JPEG);
            is.close();
            CreationHelper helper = book.getCreationHelper();
            Drawing drawing = hoja.createDrawingPatriarch();
            ClientAnchor anchor = helper.createClientAnchor();
            anchor.setCol1(1);
            anchor.setCol2(3);
            anchor.setRow1(0);
            anchor.setRow2(3);
            Picture pict = drawing.createPicture(anchor, pictureIdx);
            pict.resize();
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(this, e);
        } catch (IOException ex) {
            Logger.getLogger(OrdenDeCompra.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, ex);
        }
        try {
            for (int i = -1; i < NumeroFila; i++) {
                Row fila = hoja.createRow(i + 5);
                for (int j = 0; j < NumeroColumna; j++) {
                    Cell celda = fila.createCell(j + 1);
                    if (i == -1 && (j >= 0 && j <= 5)) {
                        CellStyle s = book.createCellStyle();
                        Font f = book.createFont();
                        s.setFillBackgroundColor(IndexedColors.WHITE.getIndex());
                        s.setFillForegroundColor(IndexedColors.BLACK.getIndex());
                        s.setAlignment(HorizontalAlignment.CENTER);
                        s.setFillPattern(SOLID_FOREGROUND);
                        f.setBold(true);
                        f.setColor(IndexedColors.WHITE.getIndex());
                        f.setFontName("Arial");
                        f.setFontHeightInPoints((short) 10);
                        s.setFont(f);
                        celda.setCellStyle(s);
                    }
                    if (i > -1 && (j > -1 && j <= 5) && (i % 2 == 0)) {
                        CellStyle s = book.createCellStyle();
                        s.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
                        s.setFillPattern(SOLID_FOREGROUND);
                        celda.setCellStyle(s);
                    } else {
                        CellStyle s = book.createCellStyle();
                        s.setFillForegroundColor(IndexedColors.WHITE.getIndex());
                        s.setFillPattern(SOLID_FOREGROUND);
                        celda.setCellStyle(s);
                    }

                    if (i == -1) {
                        celda.setCellValue(String.valueOf(Tabla2.getColumnName(j)));
                        CellUtil.setCellStyleProperties(celda, properties);
                    } else {
                        celda.setCellValue(String.valueOf(Tabla2.getValueAt(i, j)));
                        CellUtil.setCellStyleProperties(celda, properties);
                    }
                    book.write(new FileOutputStream(nombreArchivo));
                }
            }
            book.close();
            mensaje = "Exportacion Exitosa";
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e);
        }
        JOptionPane.showMessageDialog(this, mensaje);

    }

    public void cambiarTabla() {
        DefaultTableModel miModelo = (DefaultTableModel) Tabla2.getModel();
        try {
            Connection con = null;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            Statement st1 = con.createStatement();
            String sql = "select * from Requisiciones where NumRequisicion like '" + (Tabla1.getValueAt(ta, 2).toString()) + "'";
            String sql1 = "select Id, Progreso from Requisicion where Id like '" + (Tabla1.getValueAt(ta, 2).toString()) + "'";
            ResultSet rs = st.executeQuery(sql);
            ResultSet rs1 = st1.executeQuery(sql1);
            String datos[] = new String[10];
            int cont = 1;
            while (rs.next()) {

                datos[0] = "" + cont;
                datos[1] = rs.getString("Descripcion");
                datos[2] = rs.getString("Codigo");
                datos[3] = rs.getString("UM");
                datos[4] = rs.getString("Cantidad");
                datos[5] = rs.getString("Precio");
                miModelo.addRow(datos);
                cont = cont + 1;
            }
            while (rs1.next()) {
                datos[0] = rs1.getString("Id");
                datos[1] = rs1.getString("Progreso");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "ERROR AL VER DATOS" + e, "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void verificarOrdenes(String requisicion) {
        try {
            Connection con;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            String sql = "select Progreso, Id from requisicion where Id like '" + requisicion + "'";
            ResultSet rs = st.executeQuery(sql);
            String estado = "";
            while (rs.next()) {
                estado = rs.getString("Progreso");
            }
            if (estado.equals("NUEVO") || estado.equals("COTIZANDO") || estado.equals("APROBADO")) {
                String sql2 = "select * from requisiciones where NumRequisicion like '" + requisicion + "'";
                Statement st2 = con.createStatement();
                ResultSet rs2 = st2.executeQuery(sql2);
                int contR = 0;
                int contE = 0;
                while (rs2.next()) {
                    String oc = rs2.getString("OC");
                    contR++;
                    if (oc != null) {
                        contE++;
                    }
                }
                if (contR == contE) {
                    String sql3 = "update requisicion set Progreso = ? where Id = ?";
                    PreparedStatement pst = con.prepareStatement(sql3);

                    pst.setString(1, "COMPRADO");
                    pst.setString(2, requisicion);

                    int n = pst.executeUpdate();

                    if (n > 0) {
                        JOptionPane.showMessageDialog(this, "Esta Requisicion esta completa, su estado paso a 'COMPRADO'");
                        limpiarTabla1();
                        verDatos();
                    } else {
                        JOptionPane.showMessageDialog(this, "ERROR: No se mando a comprar, favor de avisar", "ERROR", JOptionPane.ERROR_MESSAGE);
                    }

                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "ERROR: " + e, "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    public String mostrarDialogoEmergente() {
        ImageIcon icono = new ImageIcon(getClass().getResource("/Img/archivo.png"));
        String opcion = (String) JOptionPane.showInputDialog(
                null,
                "Seleccione una opción:",
                "Selección de Opción",
                JOptionPane.PLAIN_MESSAGE,
                icono,
                null,
                null // Opción predeterminada
        );
        if (opcion != null) {
            return opcion;
        } else {
            return null;
        }
    }

    public BufferedImage generateBarcodeSinLetras(String code) throws IOException {
        Code39Bean barcodeBean = new Code39Bean();
        final int dpi = 150;

        barcodeBean.setModuleWidth(0.2);
        barcodeBean.setWideFactor(3);
        barcodeBean.doQuietZone(false);

        barcodeBean.setMsgPosition(org.krysalis.barcode4j.HumanReadablePlacement.HRP_NONE);

        BitmapCanvasProvider canvas = new BitmapCanvasProvider(dpi, BufferedImage.TYPE_BYTE_BINARY, false, 0);
        barcodeBean.generateBarcode(canvas, code);
        canvas.finish();

        return canvas.getBufferedImage();
    }

    private void generarPDF(Connection con, DatosOC datos, String proveedor, String cotizacion, boolean editada, int i) throws Exception {

//        String ruta = datos.cadena2 + ".pdf";
        String ruta = "\\\\192.168.100.40\\bd\\OC\\Orden_de_compra\\" + datos.cadena2 + ".pdf";
        Document document = new Document(PageSize.A4, 36, 36, 270, 60);
        CabezeraCompras cab = new CabezeraCompras();
        cab.correo = correo;
        cab.direccion = "Camino viejo a la rosita #305, Cd Juarez, Chihuahua, CP: 32580";
        cab.empresa = "Servicios Industriales 3i S de RL MI";
        cab.rfc = "SII150213KR7";
        cab.logo = "Img/Rec2.png";
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(ruta));
        writer.setPageEvent(cab);
        document.open();
        // =====================================================
        // COLORES
        // =====================================================
        BaseColor colorTitulo;
        BaseColor colorTotal;
        BaseColor colorComentarios;

        colorTitulo = new BaseColor(233, 109, 53);
        colorTotal = new BaseColor(255, 193, 169);
        colorComentarios = new BaseColor(233, 109, 53);

        // =====================================================
        // FUENTES
        // =====================================================
        com.itextpdf.text.Font fuente = new com.itextpdf.text.Font();
        com.itextpdf.text.Font fuente1 = new com.itextpdf.text.Font();
        com.itextpdf.text.Font fuente2 = new com.itextpdf.text.Font();
        com.itextpdf.text.Font fuente3 = new com.itextpdf.text.Font();

        fuente.setSize(8);
        fuente.setFamily("Arial");
        fuente.setColor(BaseColor.BLACK);
        fuente.setStyle(com.itextpdf.text.Font.BOLD);

        fuente1.setSize(8);
        fuente1.setFamily("Arial");
        fuente1.setColor(BaseColor.WHITE);
        fuente1.setStyle(com.itextpdf.text.Font.BOLD);

        fuente2.setSize(8);
        fuente2.setFamily("Arial");
        fuente2.setColor(BaseColor.BLACK);

        fuente3.setSize(8);
        fuente3.setFamily("Arial");
        fuente3.setColor(BaseColor.BLACK);

        // =====================================================
        // DATOS PROVEEDOR
        // =====================================================
        String sqlProveedor = "select * from registroprov_compras where Nombre like ?";

        PreparedStatement pstProveedor = con.prepareStatement(sqlProveedor);
        pstProveedor.setString(1, proveedor);
        ResultSet rsProveedor = pstProveedor.executeQuery();

        String nombreProveedor = "";
        String direccionProveedor = "";
        String telefonoProveedor = "";
        String condicion = "";
        String iva = "0";
        String moneda = "";
        String isr = "";

        while (rsProveedor.next()) {
            nombreProveedor = rsProveedor.getString("Nombre");
            direccionProveedor = rsProveedor.getString("Direccion");
            telefonoProveedor = rsProveedor.getString("Telefono");
            condicion = rsProveedor.getString("Condiciones");
            iva = rsProveedor.getString("Iva");
            moneda = rsProveedor.getString("Moneda");
            isr = rsProveedor.getString("Isr");
        }

        cab.empresaCli = nombreProveedor;
        cab.direccionCli = direccionProveedor;
        cab.rfcCli = telefonoProveedor;
        cab.coti = datos.cadena;
        cab.cotizacion = cotizacion;
        cab.requisitor = num;
        cab.proyecto = proy;
        cab.condicion = condicion;

        // =====================================================
        // TEXTO
        // =====================================================
        Paragraph p10 = new Paragraph("Si usted tiene alguna pregunta sobre esta orden de compra, por favor, pongase en contacto con", fuente2);
        p10.setAlignment(Element.ALIGN_CENTER);
        Paragraph p11 = new Paragraph("Daniela Castro: Tel: 656-281-9317 E-mail: compras01@si3i.com", fuente2);
        p11.setAlignment(Element.ALIGN_CENTER);
        Paragraph p12 = new Paragraph("NR: " + lblRequi.getText(), fuente);
        p12.setAlignment(Element.ALIGN_CENTER);
        // =====================================================
        // TABLA PRODUCTOS
        // =====================================================
        PdfPTable tbl2 = new PdfPTable(7);

        float[] medidaCeldas2 = {15, 130, 60, 20, 35, 30, 40};
        tbl2.setWidths(medidaCeldas2);
        tbl2.setWidthPercentage(100);

        // =====================================================
        // PRODUCTOS
        // =====================================================
        double total = 0;

        int articulo = 0;

        Statement st3 = con.createStatement();

        for (int k = 0; k < Tabla2.getRowCount(); k++) {

            String sql3;

            ResultSet rs3;

            if (search == true) {
                sql3 = "select * from requisicionesmuestra " + "where Id like '" + Tabla2.getValueAt(k, 0).toString() + "'";
            } else {
                sql3 = "select * from requisiciones " + "where Id like '" + Tabla2.getValueAt(k, 0).toString() + "'";
            }
            rs3 = st3.executeQuery(sql3);
            String dap = "";
            while (rs3.next()) {
                dap = rs3.getString("Proveedor");
            }
            boolean ban = true;

            if (Tabla2.getValueAt(k, 5) == null) {
                ban = false;
            } else if (Tabla2.getValueAt(k, 5).toString().equals("")) {
                ban = false;
            }

            if (proveedor.equals(dap) && ban == true) {

                // =====================================================
                // UPDATE NORMAL / EDITADA
                // =====================================================
                if (editada) {
                    String sql = "update requisiciones " + "set Estado = ? " + "where Id = ?";
                    PreparedStatement pst = con.prepareStatement(sql);

                    pst.setString(1, "TERMINADO");
                    pst.setString(2, Tabla2.getValueAt(k, 0).toString());

                    pst.executeUpdate();
                } else {
                    if (search == true) {
                        String sql30 = "update requisicionesmuestra " + "set OC = ? where Id = ?";
                        PreparedStatement pst30 = con.prepareStatement(sql30);

                        pst30.setString(1, datos.cadena);
                        pst30.setString(2, Tabla2.getValueAt(k, 0).toString());
                        pst30.executeUpdate();
                    } else {
                        String sql30 = "update requisiciones " + "set OC = ? where Id = ?";
                        PreparedStatement pst30 = con.prepareStatement(sql30);

                        pst30.setString(1, datos.cadena);
                        pst30.setString(2, Tabla2.getValueAt(k, 0).toString());
                        pst30.executeUpdate();
                    }
                }

                articulo++;

                double ad1 = Double.parseDouble(Tabla2.getValueAt(k, 6).toString());
                total += ad1;
                for (int j = 0; j < 7; j++) {
                    PdfPCell c1 = new PdfPCell(new Phrase(Tabla2.getValueAt(k, j).toString(), fuente3));
                    if (j == 0) {
                        c1 = new PdfPCell(new Phrase("" + articulo, fuente3));
                    }
                    c1.setBorderColor(BaseColor.LIGHT_GRAY);
                    tbl2.addCell(c1);
                }
            }
        }

        // =====================================================
        // FILAS VACIAS
        // =====================================================
        for (int k = articulo; k < 20; k++) {
            articulo++;
            for (int j = 0; j < 7; j++) {
                PdfPCell c1;
                if (j == 0) {
                    c1 = new PdfPCell(new Phrase("" + articulo, fuente3));
                } else {
                    c1 = new PdfPCell(new Phrase(" ", fuente3));
                }
                c1.setBorderColor(BaseColor.LIGHT_GRAY);
                tbl2.addCell(c1);
            }
        }

        // =====================================================
        // TOTALES
        // =====================================================
        DecimalFormatSymbols separador = new DecimalFormatSymbols();
        separador.setDecimalSeparator('.');
        DecimalFormat formato = new DecimalFormat("#,###.##", separador);
        double ivaTotal = (total * Double.parseDouble(iva)) / 100;
        double isrTotal = 0;
        if (isr != null && !isr.equals("")) {
            isrTotal = ((total) * Double.parseDouble(isr)) / 100;
        }
        double granTotal = total + ivaTotal - isrTotal;

        //===================================== SEGUNDO TOTAL =====================================
        PdfPTable tabTotales = new PdfPTable(4);
        float med[] = {300, 50, 50, 50};
        tabTotales.setWidths(med);
        tabTotales.setWidthPercentage(100);
        PdfPCell blank = new PdfPCell(new Paragraph(" "));
        blank.setBorder(0);

        //SUBTOTAL========================================
        PdfPCell sub = new PdfPCell(new Phrase("SUBTOTAL", fuente));
        PdfPCell subP = new PdfPCell(new Phrase("$ " + formato.format(total), fuente2));

        sub.setBorder(0);
        sub.setHorizontalAlignment(Element.ALIGN_RIGHT);
        subP.setBorder(0);

        subP.setBackgroundColor(colorTotal);

        tabTotales.addCell(blank);
        tabTotales.addCell(blank);
        tabTotales.addCell(sub);
        tabTotales.addCell(subP);

        //ISR========================
        if (isr != null && !isr.equals("")) {
            PdfPCell is = new PdfPCell(new Phrase("ISR", fuente));
            PdfPCell isP = new PdfPCell(new Phrase("$ " + formato.format(isrTotal), fuente2));

            is.setBorder(0);
            is.setHorizontalAlignment(Element.ALIGN_RIGHT);
            isP.setBorder(0);

            tabTotales.addCell(blank);
            tabTotales.addCell(blank);
            tabTotales.addCell(is);
            tabTotales.addCell(isP);
        }

        //IVA===============================================
        PdfPCell com = new PdfPCell(new Phrase("Comentarios o instrucciones especiales", fuente1));
        com.setBackgroundColor(colorComentarios);
        com.setBorder(0);

        PdfPCell iv = new PdfPCell(new Phrase("IVA", fuente));
        PdfPCell ivP = new PdfPCell(new Phrase("$ " + formato.format(ivaTotal), fuente2));

        iv.setBorder(0);
        iv.setHorizontalAlignment(Element.ALIGN_RIGHT);
        ivP.setBorder(0);

        tabTotales.addCell(com);
        tabTotales.addCell(blank);
        tabTotales.addCell(iv);
        tabTotales.addCell(ivP);

        //TOTAL==============================================
        PdfPCell c11 = new PdfPCell(new Phrase("" + moneda, fuente));
        PdfPCell c22 = new PdfPCell(new Phrase("TOTAL ", fuente));
        PdfPCell c33 = new PdfPCell(new Phrase("$ " + formato.format(granTotal), fuente2));
        c11.setBorder(0);
        c22.setBorder(0);
        c22.setHorizontalAlignment(Element.ALIGN_RIGHT);
        c33.setBorder(0);
        c11.setHorizontalAlignment(Element.ALIGN_LEFT);
        c22.setHorizontalAlignment(Element.ALIGN_RIGHT);
        c33.setHorizontalAlignment(Element.ALIGN_LEFT);
        c33.setBackgroundColor(colorTotal);
        tabTotales.addCell(c11);
        tabTotales.addCell(blank);
        tabTotales.addCell(c22);
        tabTotales.addCell(c33);

        // =====================================================
        // BARCODE
        // =====================================================
        BufferedImage barcodeImage = generateBarcodeSinLetras(datos.cadena);
        File tempFile = File.createTempFile("barcode", ".png");
        ImageIO.write(barcodeImage, "png", tempFile);
        Image im = Image.getInstance(tempFile.getAbsolutePath());
        im.setAbsolutePosition((PageSize.A4.getWidth() / 2) - 60, 10);
        im.scaleAbsolute(120, 30);
        document.add(im);
        // =====================================================
        // AGREGAR
        // =====================================================
        document.add(tbl2);
        document.add(tabTotales);

        document.add(p10);
        document.add(p11);
        document.add(p12);

        document.close();

        tempFile.delete();

        if (!editada) {
            JFrame f = (JFrame) JOptionPane.getFrameForComponent(this);
            enviarCorreo enviar = new enviarCorreo(f, true);
            Stack<String> pila = new Stack<>();
            Stack<String> idStack = new Stack<>();
            Stack<String> pilaProv = new Stack<>();

            pila.push(datos.cadena2);
            pilaProv.push(datos.proveedor);
            idStack.push(Tabla2.getValueAt(i, 0).toString());

            enviar.llenarBotones(pila, pilaProv, idStack);
            enviar.btnAgregar.setVisible(false);
            enviar.tabla = Tabla2;
            enviar.numRequi = datos.cadena2;
            enviar.correo = correo;
            enviar.contra = pass;
            enviar.transaccion = "orden";
            enviar.ordenReal = datos.cadena;
            for (int j = 0; j < enviar.panel.length; j++) {
                enviar.partes[j].setEnabled(false);
                enviar.label[j].setEnabled(false);
            }
            enviar.setVisible(true);
        }
    }

    private void cargarDatosProveedor(Connection con, DatosOC datos, String proveedor) throws SQLException {
        String sql = "select * from registroprov_compras where Nombre like ?";

        PreparedStatement pst = con.prepareStatement(sql);
        pst.setString(1, proveedor);
        ResultSet rs = pst.executeQuery();
        while (rs.next()) {
            datos.proveedor = rs.getString("Nombre");
            datos.condicion = rs.getString("Condiciones");
            datos.iva = rs.getString("Iva");
            datos.moneda = rs.getString("Moneda");
            datos.isr = rs.getString("Isr");
        }
        if (datos.iva == null) {
            datos.iva = "0";
        }
        if (datos.moneda == null) {
            datos.moneda = "";
        }
    }

    private void generarNuevaOC(Connection con, DatosOC datos, String proveedor) throws SQLException {
        Statement st = con.createStatement();
        String sql = "select OrdenNo from OrdenCompra";
        ResultSet rs = st.executeQuery(sql);
        String ultima = "";
        while (rs.next()) {
            ultima = rs.getString("OrdenNo");
        }
        String numero = ultima.substring(3);
        int consecutivo = Integer.parseInt(numero);
        datos.cadena = "OC" + inicial + (consecutivo + 1);
        datos.cadena2 = datos.cadena + "-" + proveedor;
        String add
                = "insert into OrdenCompra "
                + "(OrdenNo,RequisicionNo,Fecha) "
                + "values (?,?,?)";

        PreparedStatement pst = con.prepareStatement(add);
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        pst.setString(1, datos.cadena);
        pst.setString(2, lblRequi.getText());
        pst.setString(3, sdf.format(d));

        pst.executeUpdate();
    }

    private DatosOC obtenerDatosOC(Connection con, String proveedor, boolean editada) throws SQLException {
        DatosOC datos = new DatosOC();
        Statement st = con.createStatement();
        if (editada) {
            String po4
                    = "select * from detallesedicionpo "
                    + "where IdArticulo like '"
                    + Tabla2.getValueAt(0, 0).toString()
                    + "'";
            ResultSet rs4 = st.executeQuery(po4);
            String proveedorAnterior = "";
            String po = "";
            while (rs4.next()) {
                proveedorAnterior = rs4.getString("Proveedor");
                po = rs4.getString("PO");
            }
            if (proveedorAnterior.equals(proveedor)) {
                datos.cadena = po;
                datos.cadena2 = po + "-" + proveedor;
            } else {
                generarNuevaOC(con, datos, proveedor);
            }
        } else {
            generarNuevaOC(con, datos, proveedor);
        }
        cargarDatosProveedor(con, datos, proveedor);
        return datos;
    }

    private void abrirPDF(String cadena2) {
        try {
            char comillas = '"';
            Runtime.getRuntime().exec(
                    "cmd /c start \\\\192.168.100.40\\bd\\OC\\Orden_de_compra\\"
                    + comillas
                    + cadena2
                    //                    cadena2
                    + comillas
                    + ".pdf"
            );
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, e);
        }
    }

    private String pedirCotizacion(String proveedor) {
        String cotizacion = "";
        do {
            cotizacion = JOptionPane.showInputDialog(this, "INGRESA NUMERO DE COTIZACION DEL PROVEEDOR " + proveedor);
        } while (cotizacion == null || cotizacion.trim().isEmpty());
        return cotizacion;
    }

    public void generarOrdenCompra(boolean editada) {
        try {
            int tam = elegir.botones.length;
            for (int i = 0; i < tam; i++) {
                if (!elegir.panel[i].getBackground().equals(java.awt.Color.green)) {
                    continue;
                }
                String proveedor = elegir.botones[i].getText();
                String cotizacion = pedirCotizacion(proveedor);
                try (Connection con = new Conexion().getConnection()) {
                    DatosOC datos = obtenerDatosOC(con, proveedor, editada);
                    generarPDF(con, datos, proveedor, cotizacion, editada, i);
                    abrirPDF(datos.cadena2);
                }
            }
            limpiarTabla1();
            verDatos();
            if (!editada) {
                verificarOrdenes(lblRequi.getText());
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "ERROR: " + e);

            e.printStackTrace();
        }
    }

    public void limpiarTabla1() {
        DefaultTableModel miModelo = (DefaultTableModel) Tabla1.getModel();
        String titulos[] = {"ESTADO", "REQUISITOR", "NUMERO DE REQUISICION", "PO"};
        miModelo = new DefaultTableModel(null, titulos);
        Tabla1.setModel(miModelo);
    }

    public void limpiarTabla2() {
        DefaultTableModel miModelo = (DefaultTableModel) Tabla2.getModel();
        String titulos[] = {"ARTICULO", "DESCRIPCION", "NUMERO PARTE", "U.M.", "CANTIDAD", "PRECIO", "TE"};
        miModelo = new DefaultTableModel(null, titulos);
        Tabla2.setModel(miModelo);
    }

    public void limpiarTabla3() {
        DefaultTableModel miModelo = (DefaultTableModel) Tabla2.getModel();
        String titulos[] = {"ARTICULO #", "DESCRIPCION", "CODIGO", "U.M.", "CANTIDAD", "PRECIO", "TOTAL", "TE"};
        miModelo = new DefaultTableModel(null, titulos);
        Tabla2.setModel(miModelo);
    }

    public void limpiarTabla() {
        DefaultTableModel miModelo = (DefaultTableModel) Tabla2.getModel();
        String titulos[] = {"Requisitor", "Cantidad", "U.M.", "Descripcion", "No. Parte", "Proveedor", "No. Proyefto", "ID", "TE", "PO", "Fecha Proyecto", "Notas", "Llego", "Fecha Esperada", "Cant. Stock"};
        miModelo = new DefaultTableModel(null, titulos);

        Tabla2.setModel(miModelo);
    }

    public void agregarPO() {
        int lleno = Tabla1.getRowCount();
        for (int i = 0; i < lleno; i++) {
            Tabla1.setValueAt("", i, 3);
        }
        try {
            Connection con = null;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            int fila = Tabla1.getSelectedRow();
            String sql = "select OrdenNo from ordencompra where RequisicionNo like '" + Tabla1.getValueAt(fila, 2).toString() + "'";
            ResultSet rs = st.executeQuery(sql);
            String datos[] = new String[10];
            int cont = fila;
            while (rs.next()) {
                datos[0] = rs.getString("OrdenNo");
                Tabla1.setValueAt(datos[0], cont, 3);
                cont++;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "ERROR: " + e, "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    public String format(String proyecto, String priori) {
        String text;
        text = "<html>"
                + "<div style='padding: 2px 14px; text-align:center;'>"
                + "<p style='font-size:12px; font-weight: 700;'>" + proyecto + "</p>"
                + "<p style='font-size:10px; font-weight: 700;'>" + priori + "</p>"
                + "</div>"
                + "</html>";
        return text;
    }

    public void verPrioridad() {
        try {
            Connection con;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            for (int i = 1; i <= 10; i++) {
                Statement st = con.createStatement();
                String sql = "select * from prioridadcompras where Estado like 'ACTIVO' and Prioridad like '" + i + "'";
                ResultSet rs = st.executeQuery(sql);
                String priori = null;
                while (rs.next()) {
                    priori = rs.getString("Proyecto");
                }
                if (priori != null) {
                    switch (i) {
                        case 1:
                            btn1.setText(format(priori, String.valueOf(i)));
                            proyecto[1] = priori;
                            break;
                        case 2:
                            btn2.setText(format(priori, String.valueOf(i)));
                            proyecto[2] = priori;
                            break;
                        case 3:
                            btn3.setText(format(priori, String.valueOf(i)));
                            proyecto[3] = priori;
                            break;
                        case 4:
                            btn4.setText(format(priori, String.valueOf(i)));
                            proyecto[4] = priori;
                            break;
                        case 5:
                            btn5.setText(format(priori, String.valueOf(i)));
                            proyecto[5] = priori;
                            break;
                        case 6:
                            btn6.setText(format(priori, String.valueOf(i)));
                            proyecto[6] = priori;
                            break;
                        case 7:
                            btn7.setText(format(priori, String.valueOf(i)));
                            proyecto[7] = priori;
                            break;
                        case 8:
                            btn8.setText(format(priori, String.valueOf(i)));
                            proyecto[8] = priori;
                            break;
                        case 9:
                            btn9.setText(format(priori, String.valueOf(i)));
                            proyecto[9] = priori;
                            break;
                        case 10:
                            btn10.setText(format(priori, String.valueOf(i)));
                            proyecto[10] = priori;
                            break;
                    }
                } else {
                    switch (i) {
                        case 1:
                            panel1.setVisible(false);
                            break;
                        case 2:
                            panel2.setVisible(false);
                            break;
                        case 3:
                            panel3.setVisible(false);
                            break;
                        case 4:
                            panel4.setVisible(false);
                            break;
                        case 5:
                            panel5.setVisible(false);
                            break;
                        case 6:
                            panel6.setVisible(false);
                            break;
                        case 7:
                            panel7.setVisible(false);
                            break;
                        case 8:
                            panel8.setVisible(false);
                            break;
                        case 9:
                            panel9.setVisible(false);
                            break;
                        case 10:
                            panel10.setVisible(false);
                            break;
                    }
                }
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "ERROR: " + e, "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void verDatos() {
        try {
            DefaultTableModel miModelo = (DefaultTableModel) Tabla1.getModel();
            Connection con = null;
            Conexion con1 = new Conexion();
            con = con1.getConnection();

            verPrioridad();

            String sql4 = "select NumEmpleado, Color from registroempleados where NumEmpleado like '" + this.numEmpleado + "'";
            Statement st4 = con.createStatement();
            ResultSet rs4 = st4.executeQuery(sql4);
            String color = "#00a2ff";

            while (rs4.next()) {
                color = rs4.getString("Color");
            }

            Statement st2 = con.createStatement();
            String sql2 = "select PO from edicionpo where Estado like 'APROBADO' or Estado like 'COMPRADO'";
            ResultSet rs2 = st2.executeQuery(sql2);
            String datos2;

            Statement st3 = con.createStatement();
            String sql3 = "select Estado,NumEmpleado,PO from edicionpo where Estado like 'APROBADO' or Estado like 'COMPRADO'";
            ResultSet rs3 = st3.executeQuery(sql3);
            String datos3[] = new String[10];
            cont = 0;
            while (rs2.next()) {
                datos2 = rs2.getString("PO");
                cont++;
            }

            con = con1.getConnection();
            Statement st = con.createStatement();
            // and Progreso != 'APROBACION'
            String sql = "select Progreso,NumeroEmpleado,Id,Fecha from Requisicion where Completado like 'NO' and Progreso != 'RECIBIDO'  and Progreso != 'EVALUACION' and Progreso != 'RECHAZADO' and Progreso != 'COTIZADO' order by Id desc";
            ResultSet rs = st.executeQuery(sql);
            String datos[] = new String[7];
            config = new Stack<>();
            while (rs.next()) {
                datos[0] = rs.getString("Progreso");
                datos[1] = rs.getString("NumeroEmpleado");
                datos[2] = rs.getString("Id");
                datos[4] = rs.getString("Fecha");
                miModelo.addRow(datos);
                config.push(new ConfigCompras(this.numEmpleado, datos[2], color));
            }

            if (cont > 0) {
                String d[] = new String[10];
                d[0] = "ORDENES";
                d[1] = "EDITADAS";
                miModelo.addRow(d);
                while (rs3.next()) {
                    datos3[0] = rs3.getString("Estado");
                    datos3[1] = rs3.getString("NumEmpleado");
                    datos3[2] = rs3.getString("PO");
                    miModelo.addRow(datos3);
                }
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "ERROR AL VER DATOS" + e, "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void verDatos2() {
        try {
            DefaultTableModel miModelo = (DefaultTableModel) Tabla2.getModel();
            int fila = Tabla1.getSelectedRow();
            Connection con = null;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            String datos[] = new String[8];
            String datos1[] = new String[8];
            String sql = "select * from Requisiciones where Requisicion like '" + (Tabla1.getValueAt(fila, 2).toString()) + "'";
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                datos[1] = rs.getString("NumParte");
                datos[2] = rs.getString("Cantidad");
                datos[3] = rs.getString("Codigo");
                datos[4] = rs.getString("Descripcion");
                datos[5] = rs.getString("Proyecto");
                datos[6] = rs.getString("NumRequisicion");
                miModelo.addRow(datos);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "ERROR AL VER DATOS" + e, "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void ocultarOrden() {
        CrearOrden.setEnabled(true);
        AbrirOrden.setEnabled(true);
        ImportarOrden.setEnabled(true);
    }

    public Stack<String> getLista() {
        String text = lblRequi.getText();
        String buscar = "-";
        int aux = 0;
        int aux2;
        char arreglo[] = text.toCharArray();
        Stack<String> lista = new Stack<>();
        for (int j = 0; j < text.length(); j++) {
            String letra = String.valueOf(arreglo[j]);
            if (buscar.equalsIgnoreCase(letra)) {
                aux2 = j;
                String ad = (text.substring(aux, aux2));
                aux = j + 1;
                lista.push(ad);
            }
        }
        return lista;
    }

    public void clic(String numRequi) {
        txtCambio.setText("ARTICULOS DE REQUISICIONES");
        try {
            DefaultTableModel miModelo = (DefaultTableModel) Tabla2.getModel();
            ta = Tabla1.getSelectedRow();
            Connection con = null;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            int tab = Tabla1.getRowCount() - cont;
            if (cont > 0) {
                tab = Tabla1.getRowCount() - cont - 1;
            }
            if (Tabla1.getSelectedRow() > tab) {
                //TABLA PARTE DE ABAJO ORDENES EDITADAS
                Statement st = con.createStatement();
                Statement st1 = con.createStatement();
                String datos[] = new String[20];
                String sql = "select Requisitor,Cantidad,UM,Descripcion,Codigo,Proveedor,Proyecto,Id,OC,Notas,FechaEsperada, cantidadStock from requisiciones where OC like '" + numRequi + "'";
                String sql1 = "select PO, Estado from edicionpo where PO like '" + numRequi + "'";
                ResultSet rs = st.executeQuery(sql);
                ResultSet rs1 = st1.executeQuery(sql1);

                if (search != true) {
                    while (rs.next()) {
                        datos[0] = rs.getString("Requisitor");
                        datos[1] = rs.getString("Cantidad");
                        datos[2] = rs.getString("UM");
                        datos[3] = rs.getString("Descripcion");
                        datos[4] = rs.getString("Codigo");
                        datos[5] = rs.getString("Proveedor");
                        datos[6] = rs.getString("Proyecto");
                        datos[7] = rs.getString("Id");
                        datos[8] = rs.getString("OC");
                        datos[9] = rs.getString("Notas");
                        datos[13] = rs.getString("FechaEsperada");
                        datos[14] = rs.getString("cantidadStock");
                        miModelo.addRow(datos);
                    }
                }

                num = datos[0];
                proy = datos[6];
                while (rs1.next()) {
                    datos[0] = rs1.getString("PO");
                    datos[1] = rs1.getString("Estado");
                }
                verCotizacion.setEnabled(false);
                jMenu3.setIcon(null);
                if (datos[1].equals("APROBADO")) {
                    CrearOrden.setEnabled(true);
                    AbrirOrden.setEnabled(true);
                    ImportarOrden.setEnabled(true);
                }
                if (datos[1].equals("COMPRADO") || datos[1].equals("LLEGO, INCOMPETO")) {
                    CrearOrden.setEnabled(true);
                    AbrirOrden.setEnabled(true);
                    ImportarOrden.setEnabled(true);
                }
                if (datos[1].equals("NUEVO") || datos[1].equals("COTIZANDO")) {
                    CrearOrden.setEnabled(false);
                    AbrirOrden.setEnabled(false);
                    ImportarOrden.setEnabled(true);
                }
                if (datos[1].equals("NUEVO")) {
                    Aprobacion.setEnabled(false);
                    String sql3 = "update requisicion set Progreso = ? where Id like '" + numRequi + "'";
                    PreparedStatement pst = con.prepareStatement(sql3);

                    pst.setString(1, "VISTO");

                    pst.executeUpdate();
                    CrearOrden.setEnabled(false);
                    AbrirOrden.setEnabled(false);
                    ImportarOrden.setEnabled(true);
                }
                if (datos[1].equals("VISTO")) {
                    CrearOrden.setEnabled(false);
                    AbrirOrden.setEnabled(false);
                    ImportarOrden.setEnabled(true);
                }
                if (datos[1].equals("RECHAZADO")) {
                    ocultarOrden();
                    CrearRequi.setEnabled(false);
                    AbrirRequi.setEnabled(false);
                    Aprobacion.setEnabled(false);
                }
                id = datos[0];
                if (datos[1].equals("NUEVO")) {

                } else if (datos[1].equals("APROBADO")) {
                }

            } else if (Tabla1.getSelectedRow() == tab) {
                limpiarTabla2();
                verCotizacion.setEnabled(false);
                jMenu3.setIcon(null);
            } else {
                agregarPO();
                Statement st = con.createStatement();
                Statement st1 = con.createStatement();
                String datos[] = new String[20];
                String sql = "select Requisitor,Cantidad,UM,Descripcion,Codigo,Proveedor, Proyecto, Id, TE,OC,Notas, Llego, FechaEsperada, cantidadStock from Requisiciones where NumRequisicion like '" + numRequi + "' and Estado is null";
                String sql1 = "select Id,Progreso, NumeroCotizacion, Estado, Comentarios, Comprar from Requisicion where Id like '" + numRequi + "'";
                ResultSet rs = st.executeQuery(sql);
                ResultSet rs1 = st1.executeQuery(sql1);

                if (search != true) {
                    while (rs.next()) {
                        datos[0] = rs.getString("Requisitor");
                        datos[1] = rs.getString("Cantidad");
                        datos[2] = rs.getString("UM");
                        datos[3] = rs.getString("Descripcion");
                        datos[4] = rs.getString("Codigo");
                        datos[5] = rs.getString("Proveedor");
                        datos[6] = rs.getString("Proyecto");
                        datos[7] = rs.getString("Id");
                        datos[8] = rs.getString("TE");
                        datos[9] = rs.getString("OC");
                        datos[11] = rs.getString("Notas");
                        datos[12] = rs.getString("Llego");
                        datos[13] = rs.getString("FechaEsperada");
                        datos[14] = rs.getString("cantidadStock");
                        miModelo.addRow(datos);
                    }
                }
                num = datos[0];
                proy = datos[6];
                while (rs1.next()) {
                    datos[0] = rs1.getString("Id");
                    datos[1] = rs1.getString("Progreso");
                    datos[8] = rs1.getString("NumeroCotizacion");
                    datos[9] = rs1.getString("Estado");
                    datos[7] = rs1.getString("Comentarios");
                    datos[12] = rs1.getString("Comprar");
                }
                if (datos[12] != null) {
                    if (datos[12].equals("SI")) {
                        btnCompra.setSelected(true);
                    } else {
                        btnCompra.setSelected(false);
                    }
                }
                txtComentarios.setText(datos[7]);
                if (datos[8] != null) {
                    if (!datos[8].equals("")) {
                        verCotizacion.setEnabled(true);
                        jMenu3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/circulo verde.png")));
                        Thread hilo = new Thread() {
                            public void run() {
                                Noti n = new Noti();
                                n.setVisible(true);
                            }
                        };
                        hilo.start();
                    } else {
                        verCotizacion.setEnabled(false);
                        jMenu3.setIcon(null);
                    }
                } else {
                    verCotizacion.setEnabled(false);
                    jMenu3.setIcon(null);
                }

                if (datos[9].equals("NORMAL")) {
                    urgencia.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/CV.png")));
                } else if (datos[9].equals("URGENTE")) {
                    urgencia.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/CR.png")));
                } else {
                    urgencia.setIcon(null);
                }

                if (datos[1].equals("APROBADO")) {
                    CrearOrden.setEnabled(true);
                    AbrirOrden.setEnabled(true);
                    ImportarOrden.setEnabled(true);
                } else if (datos[1].equals("COMPRADO")) {
                    CrearOrden.setEnabled(true);
                    AbrirOrden.setEnabled(true);
                    ImportarOrden.setEnabled(true);

                } else if (datos[1].equals("NUEVO") || datos[1].equals("COTIZANDO")) {
                    CrearOrden.setEnabled(false);
                    AbrirOrden.setEnabled(false);
                    ImportarOrden.setEnabled(true);
                } else if (datos[1].equals("NUEVO")) {
                    Aprobacion.setEnabled(false);
                    String sql3 = "update requisicion set Progreso = ? where Id like '" + numRequi + "'";
                    PreparedStatement pst = con.prepareStatement(sql3);

                    pst.setString(1, "VISTO");

                    pst.executeUpdate();
                    CrearOrden.setEnabled(false);
                    AbrirOrden.setEnabled(false);
                    ImportarOrden.setEnabled(true);
                } else if (datos[1].equals("VISTO")) {
                    CrearOrden.setEnabled(false);
                    AbrirOrden.setEnabled(false);
                    ImportarOrden.setEnabled(true);

                } else if (datos[1].equals("RECHAZADO")) {
                    ocultarOrden();
                    CrearRequi.setEnabled(false);
                    AbrirRequi.setEnabled(false);
                    Aprobacion.setEnabled(false);
                }
                if (datos[1].equals("COMPRADO") || datos[1].equals("LLEGO, INCOMPETO")) {
                    CrearOrden.setEnabled(true);
                    AbrirOrden.setEnabled(true);
                    ImportarOrden.setEnabled(true);
                }
                id = datos[0];
                if (datos[1].equals("NUEVO")) {

                } else if (datos[1].equals("APROBACION")) {
                    CrearOrden.setEnabled(false);
                }
            }

            if (search == true) {
//                limpiarTabla();
                Statement st1 = con.createStatement();
                String sql1 = "select Requisitor, Cantidad, UM, Descripcion, Codigo, Proveedor, Proyecto, Id from requisicionesmuestra where NumRequisicion like '" + numRequi + "'";
                ResultSet rs = st1.executeQuery(sql1);
                String datos[] = new String[15];

                while (rs.next()) {
                    datos[0] = rs.getString("Requisitor");
                    datos[1] = rs.getString("Cantidad");
                    datos[2] = rs.getString("UM");
                    datos[3] = rs.getString("Descripcion");
                    datos[4] = rs.getString("Codigo");
                    datos[5] = rs.getString("Proveedor");
                    datos[6] = rs.getString("Proyecto");
                    datos[7] = rs.getString("Id");
                    miModelo.addRow(datos);
                }
                num = datos[0];
                proy = datos[6];
            }
            if (Tabla2.getRowCount() > 0) {
                for (int i = 0; i < Tabla2.getRowCount(); i++) {
                    String valor;
                    try {
                        valor = Tabla2.getValueAt(i, 6).toString();
                    } catch (Exception e) {
                        valor = "";
                    }
                    String sql2 = "select Proyecto, FechaEntrega from proyectos where Proyecto like '" + valor + "'";
                    Statement st = con.createStatement();
                    ResultSet rs = st.executeQuery(sql2);
                    while (rs.next()) {
                        Tabla2.setValueAt(rs.getString("FechaEntrega"), i, 10);
                    }
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "ERROR AL VER DATOS" + e, "ERROR", JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(Inicio1.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void buscarProyecto(String proyecto) {
        try {
            limpiarTabla();
            search = false;
            CrearOrden.setEnabled(false);
            lblRequi.setText(proyecto);
            lblTitulo.setText("PROYECTO:");
            ImportarOrden.setEnabled(true);
            Connection con;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            String sql = "select * from requisiciones where Proyecto like '" + proyecto + "' and Llego is null";
            ResultSet rs = st.executeQuery(sql);
            String datos[] = new String[15];
            DefaultTableModel miModelo = (DefaultTableModel) Tabla2.getModel();
            while (rs.next()) {
                datos[0] = rs.getString("Requisitor");
                datos[1] = rs.getString("Cantidad");
                datos[2] = rs.getString("UM");
                datos[3] = rs.getString("Descripcion");
                datos[4] = rs.getString("Codigo");
                datos[5] = rs.getString("Proveedor");
                datos[6] = rs.getString("Proyecto");
                datos[7] = rs.getString("Id");
                datos[8] = rs.getString("TE");
                datos[9] = rs.getString("OC");
                datos[10] = rs.getString("NumRequisicion");
                String sql2 = "select Id, Progreso from requisicion where id like '" + datos[10] + "'";
                Statement st2 = con.createStatement();
                ResultSet rs2 = st2.executeQuery(sql2);
                while (rs2.next()) {
                    datos[11] = rs2.getString("Progreso");
                }
                if (!datos[11].equals("CANCELADO")) {
                    datos[11] = "";
                }
                miModelo.addRow(datos);
            }
            proy = proyecto;
            num = "N/A";
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "ERROR: " + e, "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void getCorreo() {
        try {
            Connection con;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            String sql = "select AES_DECRYPT(pass,'mi_llave'),correo,NumEmpleado from registroempleados where NumEmpleado like '" + numEmpleado + "'";
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                pass = rs.getString("AES_DECRYPT(Pass,'mi_llave')");
                correo = rs.getString("Correo");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "ERROR: " + e, "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    public boolean verificarPrecios() {
        boolean band = true;
        for (int i = 0; i < Tabla2.getRowCount(); i++) {
            try {
                if (Tabla2.getValueAt(i, 5) != null) {
                    if (!Tabla2.getValueAt(i, 5).toString().equals("")) {
                        String precio = Tabla2.getValueAt(i, 5).toString();
                        double pre = Double.parseDouble(precio);
                    }
                }
                if (Tabla2.getValueAt(i, 4) != null) {
                    if (!Tabla2.getValueAt(i, 4).toString().equals("")) {
                        String precio = Tabla2.getValueAt(i, 4).toString();
                        double pre = Double.parseDouble(precio);
                    }
                }
            } catch (NumberFormatException e) {
                band = false;
            }
        }
        return band;
    }

    public boolean verificarProveedor() {
        boolean band = true;
        try {
            Connection con;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            String sql = "select * from registroprov_compras";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            Stack<String> pilaProv = new Stack<>();
            while (rs.next()) {
                pilaProv.push(rs.getString("Nombre"));
            }
            for (int i = 0; i < Tabla2.getRowCount(); i++) {
                if (Tabla2.getValueAt(i, 7) != null) {
                    if (Tabla2.getValueAt(i, 7).toString().equals("")) {
                        String prov = Tabla2.getValueAt(i, 7).toString();
                        if (pilaProv.search(prov) < 0) {
                            band = false;
                        }
                    }
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "ERROR: " + e, "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        return band;
    }

    public Stack<String> extraerProveedores() {
        Stack<String> pila = new Stack<>();
        for (int i = 0; i < Tabla2.getRowCount(); i++) {
            boolean band = true;
            if (Tabla2.getValueAt(i, 7) != null) {
                if (pila.isEmpty()) {
                    pila.push(Tabla2.getValueAt(i, 7).toString());
                }
                for (int j = 0; j < pila.size(); j++) {
                    if (pila.get(j).equals(Tabla2.getValueAt(i, 7).toString())) {
                        band = false;
                    }
                }
                if (band) {
                    pila.push(Tabla2.getValueAt(i, 7).toString());
                }
            }
        }
        return pila;
    }

    public void crearBotones(String proveedor, String costo) {
        JPanel panel = new javax.swing.JPanel();
        panel.setBackground(new java.awt.Color(255, 255, 255));
        panel.setLayout(new java.awt.BorderLayout(10, 0));

        JLabel label = new javax.swing.JLabel();
        label.setFont(new java.awt.Font("Lexend", 1, 12));
        label.setText(proveedor + ": ");
        panel.add(label, java.awt.BorderLayout.WEST);

        JTextField field = new javax.swing.JTextField();
        field.setBackground(new java.awt.Color(255, 255, 255));
        field.setEditable(false);
        field.setText(costo);
        field.setFont(new java.awt.Font("Lexend", 0, 12)); // NOI18N
        field.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        field.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(153, 153, 153)));
        field.setPreferredSize(new java.awt.Dimension(100, 17));
        panel.add(field, java.awt.BorderLayout.CENTER);

        panelGastos.add(panel);
    }

    public String getTotal(Connection con, String proveedor) throws SQLException {
        String costo = "";
        Statement st = con.createStatement();
        String sql = "select Cantidad, Precio,Proveedor from requisiciones where Proveedor like '" + proveedor + "' and precio is not null and Precio != ''";
        ResultSet rs = st.executeQuery(sql);
        double total = 0;
        while (rs.next()) {
            double cantidad, precio;
            try {
                cantidad = Double.parseDouble(rs.getString("Cantidad"));
            } catch (Exception e) {
                cantidad = 0;
            }
            try {
                precio = Double.parseDouble(rs.getString("Precio"));
            } catch (Exception e) {
                precio = 0;
            }
            total += cantidad * precio;
        }
        DecimalFormat d = new DecimalFormat("#,###.##");
        costo = d.format(total);
        return costo;
    }

    public void verGastos() {
        panelGastos.removeAll();
        revalidate();
        repaint();
        SimpleDateFormat sdf = new SimpleDateFormat("MMMM");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
        Date d = new Date();
        String fecha = sdf.format(d).toUpperCase();
        lblGastos.setText(fecha);
        Stack<String> proveedores = extraerProveedores();
        try {
            Connection con;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            for (int i = 0; i < proveedores.size(); i++) {
                crearBotones(proveedores.get(i), getTotal(con, proveedores.get(i)));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "ERROR: " + e, "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    public boolean getComprador(String requi) {
        try {
            Connection con;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            String sql = "select Id, Comprador from requisicion where Id like '" + requi + "'";
            ResultSet rs = st.executeQuery(sql);
            String comp = null;
            while (rs.next()) {
                comp = rs.getString("Comprador");
            }
            if (comp == null) {
                return true;
            } else {
                return comp.equals(numEmpleado);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error: " + e, "Error", JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }

    public void verTabla() {
        ColorCompras color = new ColorCompras();
        color.numEmpleado = numEmpleado;
        Tabla1 = color;
        Tabla1.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{},
                new String[]{
                    "ESTADO", "REQUISITOR", "NUMERO DE REQUISICION", "PO", "FECHA"
                }
        ) {
            boolean[] canEdit = new boolean[]{
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
        Tabla1.setComponentPopupMenu(jPopupMenu2);
        jScrollPane1.setViewportView(Tabla1);
        if (Tabla1.getColumnModel().getColumnCount() > 0) {
            Tabla1.getColumnModel().getColumn(0).setMinWidth(150);
            Tabla1.getColumnModel().getColumn(0).setPreferredWidth(150);
            Tabla1.getColumnModel().getColumn(0).setMaxWidth(150);
            Tabla1.getColumnModel().getColumn(1).setResizable(false);
            Tabla1.getColumnModel().getColumn(2).setResizable(false);
            Tabla1.getColumnModel().getColumn(3).setResizable(false);
            Tabla1.getColumnModel().getColumn(4).setResizable(false);
        }
    }

    public void verificarKPIs(OrdenDeCompra orden) {
        Thread hilo = new Thread() {
            public void run() {
                try {
                    Connection con = new Conexion().getConnection();
                    Statement st = con.createStatement();
                    LocalDate fecha = LocalDate.now();

                    int numeroSemana = fecha.get(WeekFields.ISO.weekOfWeekBasedYear());

                    LocalDate primerDiaSemana = fecha.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));

                    String sql = "select distinct NumRequisicion from requisiciones where OC is null and NumRequisicion > 6900";
                    ResultSet rs = st.executeQuery(sql);
                    Stack<Integer> requisiciones = new Stack<>();
                    while (rs.next()) {
                        int id = rs.getInt("NumRequisicion");
                        requisiciones.add(id);
                    }
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(orden, "Error: " + e, "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        };
        hilo.start();
    }

    public OrdenDeCompra(String numEmpleado) {
        initComponents();
        verificarKPIs(this);
        this.numEmpleado = numEmpleado;

        verDatos();
        ((javax.swing.plaf.basic.BasicInternalFrameUI) this.getUI()).setNorthPane(null);

        Tabla1.getTableHeader().setFont(new java.awt.Font("Roboto", java.awt.Font.PLAIN, 14));
        Tabla1.getTableHeader().setOpaque(false);
        Tabla1.getTableHeader().setBackground(new java.awt.Color(0, 0, 0, 0));
        Tabla1.getTableHeader().setForeground(java.awt.Color.black);
        Tabla1.setRowHeight(25);
        Tabla1.setShowGrid(false);

        Tabla2.getTableHeader().setFont(new java.awt.Font("Roboto", java.awt.Font.PLAIN, 14));
        Tabla2.getTableHeader().setOpaque(false);
        Tabla2.getTableHeader().setBackground(new java.awt.Color(0, 0, 0, 0));
        Tabla2.getTableHeader().setForeground(java.awt.Color.black);
        Tabla2.setRowHeight(25);

        jScrollPane1.setVerticalScrollBar(new ScrollBarCustom(new java.awt.Color(0, 165, 255)));
        jScrollPane2.setVerticalScrollBar(new ScrollBarCustom(new java.awt.Color(0, 165, 255)));
        jScrollPane3.setVerticalScrollBar(new ScrollBarCustom(new java.awt.Color(0, 165, 255)));
        jScrollPane4.setVerticalScrollBar(new ScrollBarCustom(new java.awt.Color(0, 165, 255)));

        panelEditar.setVisible(false);

        getCorreo();

        Tabla2.setDefaultRenderer(Object.class, new CustomCellRenderer());
        Tabla1.setDefaultRenderer(Object.class, new CustomCellRenderer());

        extraerInicial(numEmpleado);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuItem2 = new javax.swing.JMenuItem();
        jPopupMenu1 = new javax.swing.JPopupMenu();
        historial = new javax.swing.JMenuItem();
        copiar = new javax.swing.JMenuItem();
        pegar = new javax.swing.JMenuItem();
        AgregarNota = new javax.swing.JMenuItem();
        cxp = new javax.swing.JMenuItem();
        jPopupMenu2 = new javax.swing.JPopupMenu();
        agrupar = new javax.swing.JMenuItem();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        btnSalir = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        urgencia = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        btnCompra = new RSMaterialComponent.RSRadioButtonMaterial();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtComentarios = new javax.swing.JTextArea();
        jPanel17 = new javax.swing.JPanel();
        lblGastos = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        panelGastos = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        lblRequi = new javax.swing.JLabel();
        lblTitulo = new javax.swing.JLabel();
        jPanel14 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        Tabla2 = new javax.swing.JTable();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        btnGuardar = new rojeru_san.RSButtonRiple();
        jPanel9 = new javax.swing.JPanel();
        jPanel16 = new javax.swing.JPanel();
        txtCambio = new javax.swing.JLabel();
        panelEditar = new javax.swing.JPanel();
        txtCambio1 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        Tabla1 = new ColorCompras();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jPanel15 = new javax.swing.JPanel();
        panel1 = new scrollPane.PanelRound();
        btn1 = new javax.swing.JButton();
        panel2 = new scrollPane.PanelRound();
        btn2 = new javax.swing.JButton();
        panel3 = new scrollPane.PanelRound();
        btn3 = new javax.swing.JButton();
        panel4 = new scrollPane.PanelRound();
        btn4 = new javax.swing.JButton();
        panel5 = new scrollPane.PanelRound();
        btn5 = new javax.swing.JButton();
        panel6 = new scrollPane.PanelRound();
        btn6 = new javax.swing.JButton();
        panel7 = new scrollPane.PanelRound();
        btn7 = new javax.swing.JButton();
        panel8 = new scrollPane.PanelRound();
        btn8 = new javax.swing.JButton();
        panel9 = new scrollPane.PanelRound();
        btn9 = new javax.swing.JButton();
        panel10 = new scrollPane.PanelRound();
        btn10 = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        CrearRequi = new javax.swing.JMenuItem();
        AbrirRequi = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        jMenuItem4 = new javax.swing.JMenuItem();
        CrearCoti = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        jMenuItem7 = new javax.swing.JMenuItem();
        CrearOrden = new javax.swing.JMenuItem();
        AbrirOrden = new javax.swing.JMenuItem();
        ImportarOrden = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem8 = new javax.swing.JMenuItem();
        jSeparator6 = new javax.swing.JPopupMenu.Separator();
        CancelarRequisicion = new javax.swing.JMenuItem();
        BorrarArticulo = new javax.swing.JMenuItem();
        jSeparator3 = new javax.swing.JPopupMenu.Separator();
        Aprobacion = new javax.swing.JMenuItem();
        jSeparator7 = new javax.swing.JPopupMenu.Separator();
        comprar = new javax.swing.JMenuItem();
        jSeparator4 = new javax.swing.JPopupMenu.Separator();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuItem13 = new javax.swing.JMenuItem();
        jSeparator5 = new javax.swing.JPopupMenu.Separator();
        jMenuItem6 = new javax.swing.JMenuItem();
        jSeparator8 = new javax.swing.JPopupMenu.Separator();
        buscar = new javax.swing.JMenuItem();
        buscar1 = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        jMenuItem5 = new javax.swing.JMenuItem();
        verCotizacion = new javax.swing.JMenuItem();
        jMenuItem9 = new javax.swing.JMenuItem();
        jMenuItem10 = new javax.swing.JMenuItem();
        jMenuItem11 = new javax.swing.JMenuItem();
        jMenuItem15 = new javax.swing.JMenuItem();
        jMenu4 = new javax.swing.JMenu();
        jMenuItem12 = new javax.swing.JMenuItem();
        jMenuItem14 = new javax.swing.JMenuItem();

        jMenuItem2.setText("jMenuItem2");

        jPopupMenu1.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
                jPopupMenu1PopupMenuWillBecomeVisible(evt);
            }
        });

        historial.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/historial-de-pedidos.png"))); // NOI18N
        historial.setText("        Ver historial de compra                                 ");
        historial.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                historialActionPerformed(evt);
            }
        });
        jPopupMenu1.add(historial);

        copiar.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        copiar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/copia.png"))); // NOI18N
        copiar.setText("        Copiar              ");
        copiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                copiarActionPerformed(evt);
            }
        });
        jPopupMenu1.add(copiar);

        pegar.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        pegar.setText("        Pegar              ");
        pegar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pegarActionPerformed(evt);
            }
        });
        jPopupMenu1.add(pegar);

        AgregarNota.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        AgregarNota.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/add.png"))); // NOI18N
        AgregarNota.setText("        Agregar nota              ");
        AgregarNota.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AgregarNotaActionPerformed(evt);
            }
        });
        jPopupMenu1.add(AgregarNota);

        cxp.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        cxp.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/dinero.png"))); // NOI18N
        cxp.setText("        Enviar a CXP");
        cxp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cxpActionPerformed(evt);
            }
        });
        jPopupMenu1.add(cxp);

        agrupar.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        agrupar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/nodos.png"))); // NOI18N
        agrupar.setText("     Agrupar requisicion     ");
        agrupar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                agruparActionPerformed(evt);
            }
        });
        jPopupMenu2.add(agrupar);

        setBackground(new java.awt.Color(255, 255, 255));
        setBorder(null);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new java.awt.BorderLayout(10, 10));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new java.awt.BorderLayout());

        jPanel13.setBackground(new java.awt.Color(255, 255, 255));

        btnSalir.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Lexend", 1, 12)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText(" X ");
        jLabel1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel1MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel1MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel1MouseExited(evt);
            }
        });
        btnSalir.add(jLabel1);

        jPanel13.add(btnSalir);

        jPanel2.add(jPanel13, java.awt.BorderLayout.EAST);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setLayout(new java.awt.BorderLayout());

        jLabel9.setFont(new java.awt.Font("Lexend", 1, 24)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 165, 252));
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("Compras");
        jPanel3.add(jLabel9, java.awt.BorderLayout.CENTER);

        jPanel2.add(jPanel3, java.awt.BorderLayout.CENTER);

        jPanel1.add(jPanel2, java.awt.BorderLayout.NORTH);

        jPanel7.setLayout(new java.awt.GridLayout(1, 0));

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setLayout(new java.awt.BorderLayout());

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));
        jPanel8.setPreferredSize(new java.awt.Dimension(1507, 75));
        jPanel8.setLayout(new javax.swing.BoxLayout(jPanel8, javax.swing.BoxLayout.LINE_AXIS));

        jPanel10.setBackground(new java.awt.Color(255, 255, 255));
        jPanel10.setPreferredSize(new java.awt.Dimension(78, 80));

        jLabel4.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("URGENCIA");

        urgencia.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        urgencia.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                urgenciaMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(urgencia, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel4)))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(urgencia, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel8.add(jPanel10);

        jPanel12.setBackground(new java.awt.Color(255, 255, 255));

        jLabel8.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel8.setText("  COMPRA  ");

        btnCompra.setForeground(new java.awt.Color(255, 102, 0));
        btnCompra.setColorCheck(new java.awt.Color(204, 102, 0));
        btnCompra.setColorUnCheck(new java.awt.Color(255, 102, 0));
        btnCompra.setEnabled(false);
        btnCompra.setFocusPainted(false);
        btnCompra.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel8)
                    .addComponent(btnCompra, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCompra, javax.swing.GroupLayout.DEFAULT_SIZE, 46, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel8.add(jPanel12);

        jScrollPane3.setBorder(null);

        txtComentarios.setEditable(false);
        txtComentarios.setBackground(new java.awt.Color(255, 255, 255));
        txtComentarios.setColumns(20);
        txtComentarios.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        txtComentarios.setLineWrap(true);
        txtComentarios.setRows(5);
        txtComentarios.setWrapStyleWord(true);
        txtComentarios.setBorder(null);
        jScrollPane3.setViewportView(txtComentarios);

        jPanel8.add(jScrollPane3);

        jPanel17.setBackground(new java.awt.Color(255, 255, 255));
        jPanel17.setPreferredSize(new java.awt.Dimension(808, 10));
        jPanel17.setLayout(new java.awt.BorderLayout());

        lblGastos.setFont(new java.awt.Font("Lexend", 1, 12)); // NOI18N
        lblGastos.setText("Gastos en el mes de Febrero");
        jPanel17.add(lblGastos, java.awt.BorderLayout.PAGE_START);

        jScrollPane5.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 1, true));
        jScrollPane5.setMaximumSize(new java.awt.Dimension(200, 32767));

        panelGastos.setBackground(new java.awt.Color(255, 255, 255));
        panelGastos.setMinimumSize(new java.awt.Dimension(1505, 100));
        panelGastos.setPreferredSize(new java.awt.Dimension(100, 60));
        jScrollPane5.setViewportView(panelGastos);

        jPanel17.add(jScrollPane5, java.awt.BorderLayout.CENTER);

        jPanel8.add(jPanel17);

        jPanel11.setBackground(new java.awt.Color(255, 255, 255));

        lblRequi.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        lblRequi.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblRequi.setText("SIN SELECCIONAR");

        lblTitulo.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        lblTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitulo.setText("REQUISICION NO:");

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblRequi, javax.swing.GroupLayout.DEFAULT_SIZE, 295, Short.MAX_VALUE)
                    .addComponent(lblTitulo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(24, 24, 24))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblRequi, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(17, Short.MAX_VALUE))
        );

        jPanel8.add(jPanel11);

        jPanel4.add(jPanel8, java.awt.BorderLayout.PAGE_START);

        jPanel14.setLayout(new java.awt.BorderLayout());

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setLayout(new java.awt.BorderLayout());

        Tabla2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "CANTIDAD", "U.M.", "DESCRIPCION/ ARTICULO", "NO PARTE", "PROVEEDOR", "NO PROYECTO", "TE"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                true, true, true, true, true, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        Tabla2.setComponentPopupMenu(jPopupMenu1);
        Tabla2.setSelectionBackground(new java.awt.Color(0, 153, 255));
        Tabla2.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                Tabla2MouseDragged(evt);
            }
        });
        Tabla2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Tabla2MouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                Tabla2MousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                Tabla2MouseReleased(evt);
            }
        });
        jScrollPane2.setViewportView(Tabla2);

        jPanel6.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("jLabel5");
        jPanel6.add(jLabel5, java.awt.BorderLayout.LINE_START);

        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("jLabel5");
        jPanel6.add(jLabel7, java.awt.BorderLayout.LINE_START);

        btnGuardar.setText("GUARDAR");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });
        jPanel6.add(btnGuardar, java.awt.BorderLayout.SOUTH);

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));
        jPanel9.setLayout(new java.awt.BorderLayout());

        jPanel16.setBackground(new java.awt.Color(255, 255, 255));

        txtCambio.setFont(new java.awt.Font("Roboto", 1, 16)); // NOI18N
        txtCambio.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txtCambio.setText("ARTICULOS DE REQUISICIONES");
        jPanel16.add(txtCambio);

        jPanel9.add(jPanel16, java.awt.BorderLayout.CENTER);

        panelEditar.setBackground(new java.awt.Color(255, 255, 255));

        txtCambio1.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        txtCambio1.setForeground(new java.awt.Color(153, 0, 0));
        txtCambio1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txtCambio1.setText("ESTA REQUISICION NO SE PUEDE EDITAR");
        panelEditar.add(txtCambio1);

        jPanel9.add(panelEditar, java.awt.BorderLayout.SOUTH);

        jPanel6.add(jPanel9, java.awt.BorderLayout.NORTH);

        jPanel14.add(jPanel6, java.awt.BorderLayout.CENTER);

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setPreferredSize(new java.awt.Dimension(400, 490));
        jPanel5.setLayout(new java.awt.BorderLayout());

        Tabla1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ESTADO", "REQUISITOR", "NUMERO DE REQUISICION", "PO", "FECHA"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        Tabla1.setComponentPopupMenu(jPopupMenu2);
        Tabla1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Tabla1MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                Tabla1MouseEntered(evt);
            }
        });
        jScrollPane1.setViewportView(Tabla1);
        if (Tabla1.getColumnModel().getColumnCount() > 0) {
            Tabla1.getColumnModel().getColumn(0).setMinWidth(150);
            Tabla1.getColumnModel().getColumn(0).setPreferredWidth(150);
            Tabla1.getColumnModel().getColumn(0).setMaxWidth(150);
            Tabla1.getColumnModel().getColumn(1).setResizable(false);
            Tabla1.getColumnModel().getColumn(2).setResizable(false);
            Tabla1.getColumnModel().getColumn(3).setResizable(false);
            Tabla1.getColumnModel().getColumn(4).setResizable(false);
        }

        jPanel5.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jLabel3.setFont(new java.awt.Font("Roboto", 1, 16)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("REQUISICIONES DE EMPLEADOS");
        jPanel5.add(jLabel3, java.awt.BorderLayout.PAGE_START);

        jPanel14.add(jPanel5, java.awt.BorderLayout.WEST);

        jScrollPane4.setBorder(null);
        jScrollPane4.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        jPanel15.setBackground(new java.awt.Color(255, 255, 255));
        jPanel15.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 15, 15));

        panel1.setBackground(new java.awt.Color(255, 102, 0));
        panel1.setRoundBottomLeft(25);
        panel1.setRoundBottomRight(25);
        panel1.setRoundTopLeft(25);
        panel1.setRoundTopRight(25);

        btn1.setBackground(new java.awt.Color(255, 102, 0));
        btn1.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        btn1.setForeground(new java.awt.Color(255, 255, 255));
        btn1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/cubo 3d.png"))); // NOI18N
        btn1.setText("<html> \n<div style='padding: 2px 12px; text-align:center;'> \n<p style='font-size:12px;'>666 200520</p> \n<p style='font-size:10px; font-weight: 700;'>1</p> \n</div> \n</html>");
        btn1.setBorder(null);
        btn1.setBorderPainted(false);
        btn1.setContentAreaFilled(false);
        btn1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn1.setFocusPainted(false);
        btn1.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/cubo 3d.png"))); // NOI18N
        btn1.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        btn1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn1ActionPerformed(evt);
            }
        });
        panel1.add(btn1);

        jPanel15.add(panel1);

        panel2.setBackground(new java.awt.Color(255, 102, 0));
        panel2.setRoundBottomLeft(25);
        panel2.setRoundBottomRight(25);
        panel2.setRoundTopLeft(25);
        panel2.setRoundTopRight(25);

        btn2.setBackground(new java.awt.Color(255, 102, 0));
        btn2.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        btn2.setForeground(new java.awt.Color(255, 255, 255));
        btn2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/cubo 3d.png"))); // NOI18N
        btn2.setText("<html> <div style='padding: 2px 12px; text-align:center;'> <p style='font-size:12px;'>666 200520</p> <p style='font-size:10px; font-weight: 700;'>2</p> </div> </html>");
        btn2.setBorder(null);
        btn2.setBorderPainted(false);
        btn2.setContentAreaFilled(false);
        btn2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn2.setFocusPainted(false);
        btn2.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/cubo 3d.png"))); // NOI18N
        btn2.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        btn2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn2ActionPerformed(evt);
            }
        });
        panel2.add(btn2);

        jPanel15.add(panel2);

        panel3.setBackground(new java.awt.Color(255, 102, 0));
        panel3.setRoundBottomLeft(25);
        panel3.setRoundBottomRight(25);
        panel3.setRoundTopLeft(25);
        panel3.setRoundTopRight(25);

        btn3.setBackground(new java.awt.Color(255, 102, 0));
        btn3.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        btn3.setForeground(new java.awt.Color(255, 255, 255));
        btn3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/cubo 3d.png"))); // NOI18N
        btn3.setText("<html> <div style='padding: 2px 12px; text-align:center;'> <p style='font-size:12px;'>666 200520</p> <p style='font-size:10px; font-weight: 700;'>3</p> </div> </html>");
        btn3.setBorder(null);
        btn3.setBorderPainted(false);
        btn3.setContentAreaFilled(false);
        btn3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn3.setFocusPainted(false);
        btn3.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/cubo 3d.png"))); // NOI18N
        btn3.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        btn3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn3ActionPerformed(evt);
            }
        });
        panel3.add(btn3);

        jPanel15.add(panel3);

        panel4.setBackground(new java.awt.Color(255, 102, 0));
        panel4.setRoundBottomLeft(25);
        panel4.setRoundBottomRight(25);
        panel4.setRoundTopLeft(25);
        panel4.setRoundTopRight(25);

        btn4.setBackground(new java.awt.Color(255, 102, 0));
        btn4.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        btn4.setForeground(new java.awt.Color(255, 255, 255));
        btn4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/cubo 3d.png"))); // NOI18N
        btn4.setText("<html> <div style='padding: 2px 12px; text-align:center;'> <p style='font-size:12px;'>666 200520</p> <p style='font-size:10px; font-weight: 700;'>4</p> </div> </html>");
        btn4.setBorder(null);
        btn4.setBorderPainted(false);
        btn4.setContentAreaFilled(false);
        btn4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn4.setFocusPainted(false);
        btn4.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/cubo 3d.png"))); // NOI18N
        btn4.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        btn4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn4ActionPerformed(evt);
            }
        });
        panel4.add(btn4);

        jPanel15.add(panel4);

        panel5.setBackground(new java.awt.Color(255, 102, 0));
        panel5.setRoundBottomLeft(25);
        panel5.setRoundBottomRight(25);
        panel5.setRoundTopLeft(25);
        panel5.setRoundTopRight(25);

        btn5.setBackground(new java.awt.Color(255, 102, 0));
        btn5.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        btn5.setForeground(new java.awt.Color(255, 255, 255));
        btn5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/cubo 3d.png"))); // NOI18N
        btn5.setText("<html> <div style='padding: 2px 12px; text-align:center;'> <p style='font-size:12px;'>666 200520</p> <p style='font-size:10px; font-weight: 700;'>5</p> </div> </html>");
        btn5.setBorder(null);
        btn5.setBorderPainted(false);
        btn5.setContentAreaFilled(false);
        btn5.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn5.setFocusPainted(false);
        btn5.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/cubo 3d.png"))); // NOI18N
        btn5.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        btn5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn5ActionPerformed(evt);
            }
        });
        panel5.add(btn5);

        jPanel15.add(panel5);

        panel6.setBackground(new java.awt.Color(255, 102, 0));
        panel6.setRoundBottomLeft(25);
        panel6.setRoundBottomRight(25);
        panel6.setRoundTopLeft(25);
        panel6.setRoundTopRight(25);

        btn6.setBackground(new java.awt.Color(255, 102, 0));
        btn6.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        btn6.setForeground(new java.awt.Color(255, 255, 255));
        btn6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/cubo 3d.png"))); // NOI18N
        btn6.setText("<html> <div style='padding: 2px 12px; text-align:center;'> <p style='font-size:12px;'>666 200520</p> <p style='font-size:10px; font-weight: 700;'>6</p> </div> </html>");
        btn6.setBorder(null);
        btn6.setBorderPainted(false);
        btn6.setContentAreaFilled(false);
        btn6.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn6.setFocusPainted(false);
        btn6.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/cubo 3d.png"))); // NOI18N
        btn6.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        btn6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn6ActionPerformed(evt);
            }
        });
        panel6.add(btn6);

        jPanel15.add(panel6);

        panel7.setBackground(new java.awt.Color(255, 102, 0));
        panel7.setRoundBottomLeft(25);
        panel7.setRoundBottomRight(25);
        panel7.setRoundTopLeft(25);
        panel7.setRoundTopRight(25);

        btn7.setBackground(new java.awt.Color(255, 102, 0));
        btn7.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        btn7.setForeground(new java.awt.Color(255, 255, 255));
        btn7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/cubo 3d.png"))); // NOI18N
        btn7.setText("<html> <div style='padding: 2px 12px; text-align:center;'> <p style='font-size:12px;'>666 200520</p> <p style='font-size:10px; font-weight: 700;'>7</p> </div> </html>");
        btn7.setBorder(null);
        btn7.setBorderPainted(false);
        btn7.setContentAreaFilled(false);
        btn7.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn7.setFocusPainted(false);
        btn7.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/cubo 3d.png"))); // NOI18N
        btn7.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        btn7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn7ActionPerformed(evt);
            }
        });
        panel7.add(btn7);

        jPanel15.add(panel7);

        panel8.setBackground(new java.awt.Color(255, 102, 0));
        panel8.setRoundBottomLeft(25);
        panel8.setRoundBottomRight(25);
        panel8.setRoundTopLeft(25);
        panel8.setRoundTopRight(25);

        btn8.setBackground(new java.awt.Color(255, 102, 0));
        btn8.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        btn8.setForeground(new java.awt.Color(255, 255, 255));
        btn8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/cubo 3d.png"))); // NOI18N
        btn8.setText("<html> <div style='padding: 2px 12px; text-align:center;'> <p style='font-size:12px;'>666 200520</p> <p style='font-size:10px; font-weight: 700;'>8</p> </div> </html>");
        btn8.setBorder(null);
        btn8.setBorderPainted(false);
        btn8.setContentAreaFilled(false);
        btn8.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn8.setFocusPainted(false);
        btn8.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/cubo 3d.png"))); // NOI18N
        btn8.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        btn8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn8ActionPerformed(evt);
            }
        });
        panel8.add(btn8);

        jPanel15.add(panel8);

        panel9.setBackground(new java.awt.Color(255, 102, 0));
        panel9.setRoundBottomLeft(25);
        panel9.setRoundBottomRight(25);
        panel9.setRoundTopLeft(25);
        panel9.setRoundTopRight(25);

        btn9.setBackground(new java.awt.Color(255, 102, 0));
        btn9.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        btn9.setForeground(new java.awt.Color(255, 255, 255));
        btn9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/cubo 3d.png"))); // NOI18N
        btn9.setText("<html> <div style='padding: 2px 12px; text-align:center;'> <p style='font-size:12px;'>666 200520</p> <p style='font-size:10px; font-weight: 700;'>9</p> </div> </html>");
        btn9.setBorder(null);
        btn9.setBorderPainted(false);
        btn9.setContentAreaFilled(false);
        btn9.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn9.setFocusPainted(false);
        btn9.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/cubo 3d.png"))); // NOI18N
        btn9.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        btn9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn9ActionPerformed(evt);
            }
        });
        panel9.add(btn9);

        jPanel15.add(panel9);

        panel10.setBackground(new java.awt.Color(255, 102, 0));
        panel10.setRoundBottomLeft(25);
        panel10.setRoundBottomRight(25);
        panel10.setRoundTopLeft(25);
        panel10.setRoundTopRight(25);

        btn10.setBackground(new java.awt.Color(255, 102, 0));
        btn10.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        btn10.setForeground(new java.awt.Color(255, 255, 255));
        btn10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/cubo 3d.png"))); // NOI18N
        btn10.setText("<html> <div style='padding: 2px 12px; text-align:center;'> <p style='font-size:12px;'>666 200520</p> <p style='font-size:10px; font-weight: 700;'>10</p> </div> </html>");
        btn10.setBorder(null);
        btn10.setBorderPainted(false);
        btn10.setContentAreaFilled(false);
        btn10.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn10.setFocusPainted(false);
        btn10.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/cubo 3d.png"))); // NOI18N
        btn10.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        btn10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn10ActionPerformed(evt);
            }
        });
        panel10.add(btn10);

        jPanel15.add(panel10);

        jScrollPane4.setViewportView(jPanel15);

        jPanel14.add(jScrollPane4, java.awt.BorderLayout.PAGE_START);

        jPanel4.add(jPanel14, java.awt.BorderLayout.CENTER);

        jPanel7.add(jPanel4);

        jPanel1.add(jPanel7, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        jMenu1.setText("Generar");
        jMenu1.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N

        jMenuItem1.setText("REQUISICIONES");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        CrearRequi.setText("Crear");
        CrearRequi.setEnabled(false);
        CrearRequi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CrearRequiActionPerformed(evt);
            }
        });
        jMenu1.add(CrearRequi);

        AbrirRequi.setText("Abrir");
        AbrirRequi.setEnabled(false);
        AbrirRequi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AbrirRequiActionPerformed(evt);
            }
        });
        jMenu1.add(AbrirRequi);
        jMenu1.add(jSeparator1);

        jMenuItem4.setText("SOLICITUD DE COTIZACION");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem4);

        CrearCoti.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/excel_1.png"))); // NOI18N
        CrearCoti.setText("Enviar");
        CrearCoti.setEnabled(false);
        CrearCoti.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CrearCotiActionPerformed(evt);
            }
        });
        jMenu1.add(CrearCoti);
        jMenu1.add(jSeparator2);

        jMenuItem7.setText("ORDEN DE COMPRA");
        jMenuItem7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem7ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem7);

        CrearOrden.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/pdf.png"))); // NOI18N
        CrearOrden.setText("Crear");
        CrearOrden.setEnabled(false);
        CrearOrden.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CrearOrdenActionPerformed(evt);
            }
        });
        jMenu1.add(CrearOrden);

        AbrirOrden.setText("Abrir");
        AbrirOrden.setEnabled(false);
        AbrirOrden.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AbrirOrdenActionPerformed(evt);
            }
        });
        jMenu1.add(AbrirOrden);

        ImportarOrden.setText("Importar");
        ImportarOrden.setEnabled(false);
        ImportarOrden.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ImportarOrdenActionPerformed(evt);
            }
        });
        jMenu1.add(ImportarOrden);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Opciones");
        jMenu2.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N

        jMenuItem8.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        jMenuItem8.setText("Agregar proveedor");
        jMenuItem8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem8ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem8);
        jMenu2.add(jSeparator6);

        CancelarRequisicion.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        CancelarRequisicion.setText("Cancelar requisicion");
        CancelarRequisicion.setEnabled(false);
        CancelarRequisicion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CancelarRequisicionActionPerformed(evt);
            }
        });
        jMenu2.add(CancelarRequisicion);

        BorrarArticulo.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        BorrarArticulo.setText("Borrar articulo");
        BorrarArticulo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BorrarArticuloActionPerformed(evt);
            }
        });
        jMenu2.add(BorrarArticulo);
        jMenu2.add(jSeparator3);

        Aprobacion.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        Aprobacion.setText("Mandar a Aprobacion");
        Aprobacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AprobacionActionPerformed(evt);
            }
        });
        jMenu2.add(Aprobacion);
        jMenu2.add(jSeparator7);

        comprar.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        comprar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/documento.png"))); // NOI18N
        comprar.setText("Comprar");
        comprar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comprarActionPerformed(evt);
            }
        });
        jMenu2.add(comprar);
        jMenu2.add(jSeparator4);

        jMenuItem3.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        jMenuItem3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/editar (1).png"))); // NOI18N
        jMenuItem3.setText("Editar PO");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem3);

        jMenuItem13.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        jMenuItem13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/editarEmpleado_16.png"))); // NOI18N
        jMenuItem13.setText("Transferir Requisicion");
        jMenuItem13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem13ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem13);
        jMenu2.add(jSeparator5);

        jMenuItem6.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        jMenuItem6.setText("Agregar partida");
        jMenuItem6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem6ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem6);
        jMenu2.add(jSeparator8);

        buscar.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        buscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/buscar_16.png"))); // NOI18N
        buscar.setText("Buscar requisicion");
        buscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buscarActionPerformed(evt);
            }
        });
        jMenu2.add(buscar);

        buscar1.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        buscar1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/buscar_16.png"))); // NOI18N
        buscar1.setText("Ver totales");
        buscar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buscar1ActionPerformed(evt);
            }
        });
        jMenu2.add(buscar1);

        jMenuBar1.add(jMenu2);

        jMenu3.setText("Ver");
        jMenu3.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        jMenu3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenu3ActionPerformed(evt);
            }
        });

        jMenuItem5.setText("        Estado de proyectos");
        jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem5ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem5);

        verCotizacion.setText("        Ver cotizacion");
        verCotizacion.setEnabled(false);
        verCotizacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                verCotizacionActionPerformed(evt);
            }
        });
        jMenu3.add(verCotizacion);

        jMenuItem9.setText("        Reclamos");
        jMenuItem9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem9ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem9);

        jMenuItem10.setText("        Requisiciones sin PO");
        jMenuItem10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem10ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem10);

        jMenuItem11.setText("        Todas las requisiciones        ");
        jMenuItem11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem11ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem11);

        jMenuItem15.setText("        Entradas a almacén");
        jMenuItem15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem15ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem15);

        jMenuBar1.add(jMenu3);

        jMenu4.setText("Reportes");
        jMenu4.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N

        jMenuItem12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/reporte_compras_16.png"))); // NOI18N
        jMenuItem12.setText("Compras de mes                                             ");
        jMenuItem12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem12ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem12);

        jMenuItem14.setText("Ordenes de compra pendientes");
        jMenuItem14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem14ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem14);

        jMenuBar1.add(jMenu4);

        setJMenuBar(jMenuBar1);

        pack();
    }// </editor-fold>//GEN-END:initComponents


    private void Tabla1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Tabla1MouseClicked
        String requi = Tabla1.getValueAt(Tabla1.getSelectedRow(), 2).toString();
        if (agr == null || !agr.isVisible()) {
            search = false;
            importar = false;
            String requis = "";
            try {
                Connection con;
                Conexion con1 = new Conexion();
                con = con1.getConnection();

                String sql = "select NumRequisicion from requisicionesmuestra where NumRequisicion like '" + requi + "'";
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery(sql);
                String f = "";
                while (rs.next()) {
                    f = rs.getString("NumRequisicion");
                    if (f != null) {
                        if (!"".equals(f)) {
                            search = true;
                        }
                    }
                }

                String sql2 = "select Id,Modificar from requisicion where Id like '" + Tabla1.getValueAt(Tabla1.getSelectedRow(), 2).toString() + "'";
                Statement st2 = con.createStatement();
                ResultSet rs2 = st2.executeQuery(sql2);
                String a = null;
                while (rs2.next()) {
                    a = rs2.getString("Modificar");
                }
                if (a == null) {
                    editar = false;
                    panelEditar.setVisible(false);
                } else {
                    if (a.equals("0")) {
                        editar = true;
                        panelEditar.setVisible(true);
                    } else {
                        panelEditar.setVisible(false);
                        editar = false;
                    }
                }

                String sql3 = "select IdAgrupar from agrupacion where NumRequisicion like '" + Tabla1.getValueAt(Tabla1.getSelectedRow(), 2).toString() + "'";
                Statement st3 = con.createStatement();
                ResultSet rs3 = st3.executeQuery(sql3);
                String ar = null;
                while (rs3.next()) {
                    ar = rs3.getString("IdAgrupar");
                }

                if (ar != null) {
                    String sql4 = "select * from agrupacion where IdAgrupar like '" + ar + "'";
                    Statement st4 = con.createStatement();
                    ResultSet rs4 = st4.executeQuery(sql4);
                    while (rs4.next()) {
                        requis += rs4.getString("NumRequisicion") + "-";
                    }
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "ERROR: " + e, "ERROR", JOptionPane.ERROR_MESSAGE);
            }
            limpiarTabla();

            if (!requis.equals("")) {
                lblTitulo.setText("REQUISICIONES AGRUPADAS:");
                lblRequi.setText(requis);
                Stack<String> pila = getLista();
                for (int i = 0; i < pila.size(); i++) {
                    clic(pila.get(i));
                }
            } else {
                lblTitulo.setText("REQUISICION NO:");
                lblRequi.setText(Tabla1.getValueAt(Tabla1.getSelectedRow(), 2).toString());
                clic(Tabla1.getValueAt(Tabla1.getSelectedRow(), 2).toString());
            }
            btnGuardar.setEnabled(false);
            CrearOrden.setEnabled(false);
            CancelarRequisicion.setEnabled(true);
        } else {
            boolean ex = true;
            for (int i = 0; i < agr.Tabla1.getRowCount(); i++) {
                if (agr.Tabla1.getValueAt(i, 0).toString().equals(Tabla1.getValueAt(Tabla1.getSelectedRow(), 2).toString())) {
                    ex = false;
                }
            }
            if (ex) {
                String datos[] = new String[2];
                datos[0] = Tabla1.getValueAt(Tabla1.getSelectedRow(), 2).toString();
                DefaultTableModel miModelo = (DefaultTableModel) agr.Tabla1.getModel();
                miModelo.addRow(datos);
            } else {
                JOptionPane.showMessageDialog(this, "Esta requisicion ya esta seleccionada", "Advertencia", JOptionPane.WARNING_MESSAGE);
            }
        }
        CrearCoti.setEnabled(true);
        extraerProveedores();
        boolean comprador = getComprador(requi);
        if (!comprador) {
            CrearRequi.setEnabled(false);
            AbrirRequi.setEnabled(false);
            CrearCoti.setEnabled(false);
            CrearOrden.setEnabled(false);
            AbrirOrden.setEnabled(false);
            ImportarOrden.setEnabled(false);
            panelEditar.setVisible(true);
            txtCambio1.setText("Esta requisicion le pertenece a otro comprador");
        }
    }//GEN-LAST:event_Tabla1MouseClicked

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void CrearRequiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CrearRequiActionPerformed
        txtCambio.setText("ARTICULOS DE REQUISICIONES");
        Exportar();
//        try{
//          Runtime.getRuntime().exec("cmd /c start \\\\192.168.100.146\\bd\\OC\\Requisicion_de_compra_"+id+".xlsx");
//          }catch(IOException  e){
//              JOptionPane.showMessageDialog(this, e);
//          }
    }//GEN-LAST:event_CrearRequiActionPerformed

    private void AbrirRequiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AbrirRequiActionPerformed
        try {
            Runtime.getRuntime().exec("cmd /c start \\\\192.168.100.40\\bd\\OC\\Requisicion_de_compra_" + id + ".xlsx");
            Runtime.getRuntime().exec("cmd /c close");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, e);
        }
    }//GEN-LAST:event_AbrirRequiActionPerformed

    private void CrearCotiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CrearCotiActionPerformed
        if (pass == null) {
            JOptionPane.showMessageDialog(this, "Debes cambiar la configuracion de tu correo", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            JFrame f = (JFrame) JOptionPane.getFrameForComponent(this);
            enviarCorreo enviar = new enviarCorreo(f, true);
            Stack<String> pila = new Stack<>();
            Stack<String> pilaProv = new Stack<>();
            Stack<String> id = new Stack<>();
            for (int i = 0; i < Tabla2.getRowCount(); i++) {
                pila.push(Tabla2.getValueAt(i, 4).toString());
                pilaProv.push(Tabla2.getValueAt(i, 5).toString());
                pilaProv.push(Tabla2.getValueAt(i, 5).toString());
                id.push(Tabla2.getValueAt(i, 7).toString());
            }
            enviar.llenarBotones(pila, pilaProv, id);
            enviar.tabla = Tabla2;
            enviar.proyecto = Tabla2.getValueAt(0, 6).toString();
            enviar.numRequi = lblRequi.getText();
            enviar.correo = correo;
            enviar.contra = pass;
            enviar.BD = search;
            enviar.transaccion = "cotizacion";
            enviar.setVisible(true);
            int n = 1;

            if (n > 0) {

                try {
                    Connection con;
                    Conexion con1 = new Conexion();
                    con = con1.getConnection();

                    if (Tabla1.getValueAt(Tabla1.getSelectedRow(), 0).toString().equals("NUEVO") || Tabla1.getValueAt(Tabla1.getSelectedRow(), 0).toString().equals("VISTO")) {
                        String sql = "update requisicion set Progreso = ?, Comprador = ? where Id = ?";
                        PreparedStatement pst = con.prepareStatement(sql);

                        pst.setString(1, "COTIZANDO");
                        pst.setString(2, numEmpleado);
                        pst.setString(3, Tabla1.getValueAt(Tabla1.getSelectedRow(), 2).toString());

                        pst.executeUpdate();
                    } else {

                    }

                    limpiarTabla1();
                    verDatos();
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(this, "ERROR: " + e, "ERROR", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }//GEN-LAST:event_CrearCotiActionPerformed

    private void jMenuItem7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem7ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem7ActionPerformed

    private void CrearOrdenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CrearOrdenActionPerformed
        if (verificarPrecios()) {
            if (verificarProveedor()) {
                guardarListaImportada();
                limpiarTablaImportada();
                importarLista(lblRequi.getText());
                JFrame f = (JFrame) JOptionPane.getFrameForComponent(this);
                verificarTotales ver = new verificarTotales(f, true);
                boolean band5 = ver.getOption(lblRequi.getText());
                if (band5) {
                    if (lblTitulo.getText().equals("PROYECTO:")) {

                        verOrdenProyecto();
                    }

                    int tab = Tabla1.getRowCount() - cont;
                    if (cont > 0) {
                        tab = Tabla1.getRowCount() - cont - 1;
                    }
                    if (Tabla1.getSelectedRow() > tab) {
                        //TABLA DE EDICION DE PO PARTE DE ABAJO
                        boolean in = false;
                        for (int i = 0; i < Tabla2.getRowCount(); i++) {
                            if (Tabla2.getValueAt(i, 5) != null) {
                                in = true;
                            }
                        }

                        if (in == false) {
                            JOptionPane.showMessageDialog(this, "DEBES LLENAR POR LO MENOS UN PRECIO O UN PROVEEDOR DE LA REQUISICION", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
                        } else {

                            ArrayList lista = new ArrayList<String>();

                            try {
                                for (int i = 0; i < Tabla2.getRowCount(); i++) {

                                    if (Tabla2.getValueAt(i, 5) == null) {

                                    } else {
                                        Connection con = null;
                                        Conexion con1 = new Conexion();
                                        con = con1.getConnection();
                                        Statement st = con.createStatement();
                                        Statement st2 = con.createStatement();
                                        String sql = "select * from Inventario where NumeroDeParte like '" + Tabla2.getValueAt(i, 2).toString() + "'";
                                        ResultSet rs = st.executeQuery(sql);
                                        String sql2 = "select * from requisiciones where Id like '" + Tabla2.getValueAt(i, 0).toString() + "'";
                                        ResultSet rs2 = st2.executeQuery(sql2);
                                        String oc = "";
                                        while (rs2.next()) {
                                            oc = rs2.getString("OC");
                                        }

                                        if (oc != null) {
                                            String datos[] = new String[10];
                                            while (rs.next()) {
                                                datos[0] = rs.getString("Proveedor");
                                                datos[1] = rs.getString("Codigo");
                                            }
                                            int cont = 0;
                                            int cont1 = 0;
                                            boolean band = true;
                                            boolean band1 = true;
                                            if ((lista.isEmpty())) {
                                                lista.add(datos[0]);
                                            } else if (lista.size() >= 1) {
                                                do {
                                                    if ((lista.get(cont).toString()).equals(datos[0])) {
                                                        band = false;
                                                        break;
                                                    }
                                                    cont = cont + 1;
                                                } while (cont < (lista.size()));
                                                if (band == true) {
                                                    lista.add(datos[0]);
                                                }
                                            }
                                        }
                                    }
                                }
                            } catch (SQLException e) {
                                JOptionPane.showMessageDialog(this, "ERROR: " + e, "ERROR", JOptionPane.ERROR_MESSAGE);
                            }

                            da = new String[lista.size()];
                            for (int i = 0; i < lista.size(); i++) {
                                da[i] = lista.get(i).toString();
                            }
                            //            
                            //            JFrame j = (JFrame) JOptionPane.getFrameForComponent(this);
                            //            elegir = new ProveedoresCompras(lista.size(),da,j,true);
                            JFrame j = (JFrame) JOptionPane.getFrameForComponent(this);
                            elegir = new ElegirProveedor(j, false, lista.size(), da, lblRequi.getText());
                            elegir.setVisible(true);
                            elegir.btnCrear.addActionListener(this);
                            elegir.btnCancelar.addActionListener(this);
                        }
                    } else if (tab == 0) {
                        //NOTHING
                    } else {
                        //TABLA DE REQUISICIONES PARTE DE ARRRIBA
                        boolean in = false;
                        for (int i = 0; i < Tabla2.getRowCount(); i++) {
                            if (Tabla2.getValueAt(i, 5) != null) {
                                in = true;
                            }
                        }

                        if (in == false) {
                            JOptionPane.showMessageDialog(this, "DEBES LLENAR POR LO MENOS UN PRECIO O UN PROVEEDOR DE LA REQUISICION", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
                        } else {

                            Stack<String> lista = new Stack<>();

                            try {
                                for (int i = 0; i < Tabla2.getRowCount(); i++) {
                                    if (Tabla2.getValueAt(i, 5) == null) {
                                    } else if ("".equals(Tabla2.getValueAt(i, 5).toString())) {
                                    } else if (Tabla2.getValueAt(i, 7) != null) {
                                        if (!"".equals(Tabla2.getValueAt(i, 7).toString())) {
                                            Connection con;
                                            Conexion con1 = new Conexion();
                                            con = con1.getConnection();
                                            Statement st2 = con.createStatement();
                                            String sql2;
                                            ResultSet rs2;
                                            if (search != true) {
                                                sql2 = "select OC, proveedor, Codigo from requisiciones where Id like '" + Tabla2.getValueAt(i, 0).toString() + "'";
                                                rs2 = st2.executeQuery(sql2);
                                            } else {
                                                sql2 = "select OC, proveedor, Codigo from requisicionesmuestra where Id like '" + Tabla2.getValueAt(i, 0).toString() + "'";
                                                rs2 = st2.executeQuery(sql2);
                                            }
                                            String oc;
                                            while (rs2.next()) {
                                                oc = rs2.getString("OC");
                                                if (oc == null) {
                                                    String datos[] = new String[10];
                                                    datos[0] = rs2.getString("Proveedor");
                                                    datos[1] = rs2.getString("Codigo");
                                                    if (lista.search(datos[0]) == -1) {
                                                        lista.push(datos[0]);
                                                    }
                                                }
                                            }

                                        }
                                    }
                                }

                            } catch (SQLException e) {
                                JOptionPane.showMessageDialog(this, "ERROR: " + e, "ERROR", JOptionPane.ERROR_MESSAGE);
                            }
                            if (!lista.isEmpty()) {
                                da = new String[lista.size()];
                                for (int i = 0; i < lista.size(); i++) {
                                    da[i] = lista.get(i);
                                }
                                JFrame j = (JFrame) JOptionPane.getFrameForComponent(this);
                                elegir = new ElegirProveedor(j, false, lista.size(), da, lblRequi.getText());
                                elegir.setVisible(true);
                                elegir.btnCrear.addActionListener(this);
                                elegir.btnCancelar.addActionListener(this);
                            } else {
                                JOptionPane.showMessageDialog(this, "DEBES LLENAR PRECIO Y PROVEEDOR DE ALGUNA PARTIDA");
                            }
                        }
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "EL PROVEEDOR NO SE ENCONTRO EN LA LISTA DE LA BASE DE DATOS", "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "ERROR AL GUARDAR UN PRECIO/CANTIDAD", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_CrearOrdenActionPerformed

    private void AbrirOrdenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AbrirOrdenActionPerformed
        try {
            Runtime.getRuntime().exec("cmd /c start \\\\192.168.100.40\\bd\\OC\\Orden_de_compra\\Orden_de_compra_" + id + ".xlsx");
            Runtime.getRuntime().exec("cmd /c close");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, e);
        }
    }//GEN-LAST:event_AbrirOrdenActionPerformed

    private void ImportarOrdenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ImportarOrdenActionPerformed
        int d = Tabla2.getRowCount();
        dat = new String[d];
        importar = true;
        CrearCoti.setEnabled(false);
        for (int i = 0; i < d; i++) {
            dat[i] = Tabla2.getValueAt(i, 7).toString();
        }
        limpiarTablaImportada();
        btnGuardar.setEnabled(true);
        if (lblRequi.getText().contains("-")) {
            Stack<String> pila = getLista();
            for (int i = 0; i < pila.size(); i++) {
                importarLista(pila.get(i));
            }
        } else {
            importarLista(Tabla1.getValueAt(Tabla1.getSelectedRow(), 2).toString());
        }
    }//GEN-LAST:event_ImportarOrdenActionPerformed

    private void AprobacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AprobacionActionPerformed
        String t;
        if (lblTitulo.getText().equals("PROYECTO:")) {
            t = "el proyecto";
        } else {
            t = "la requisicion";
        }
        int opc = JOptionPane.showConfirmDialog(this, "¿Deseas mandar a aprobacion " + t + " " + lblRequi.getText() + "?");
        if (opc == 0) {
            try {
                Connection con;
                Conexion con1 = new Conexion();
                con = con1.getConnection();
                if (lblTitulo.getText().equals("PROYECTO:")) {
                    boolean band = true;
                    for (int i = 0; i < Tabla2.getRowCount(); i++) {
                        if (Tabla2.getValueAt(i, 5) != null && Tabla2.getValueAt(i, 7) != null) {
                            band = false;
                            break;
                        }
                    }

                    if (band == false) {
                        for (int i = 0; i < Tabla2.getRowCount(); i++) {
                            if (Tabla2.getValueAt(i, 5) != null && Tabla2.getValueAt(i, 7) != null) {
                                if (!"".equals(Tabla2.getValueAt(i, 5).toString()) && !"".equals(Tabla2.getValueAt(i, 7).toString())) {
                                    Statement st = con.createStatement();
                                    String sql1 = "select * from requisiciones where Id like '" + Tabla2.getValueAt(i, 0).toString() + "'";
                                    ResultSet rs1 = st.executeQuery(sql1);
                                    String numRequi = "";
                                    while (rs1.next()) {
                                        numRequi = rs1.getString("NumRequisicion");
                                    }
                                    String sql2 = "select Id, Comprar from requisicion where Id like '" + numRequi + "'";
                                    Statement st2 = con.createStatement();
                                    ResultSet rs2 = st2.executeQuery(sql2);
                                    String com = null;

                                    String sql = "update requisiciones set Aux = ? where Id = ?";
                                    PreparedStatement pst = con.prepareStatement(sql);

                                    while (rs2.next()) {
                                        com = rs2.getString("Comprar");
                                    }

                                    String d;
                                    if (com == null) {
                                        d = "SI";
                                    } else {
                                        d = com;
                                    }
                                    if (d.equals("NO")) {
                                        pst.setString(1, "COTIZADO");
                                        pst.setString(2, Tabla2.getValueAt(i, 0).toString());
                                    } else {
                                        pst.setString(1, "APROBACION");
                                        pst.setString(2, Tabla2.getValueAt(i, 0).toString());
                                    }

                                    int n = pst.executeUpdate();

                                    if (n > 0) {
                                        limpiarTabla1();
                                        verDatos();
                                        crearNotificacion();
                                        if (lblRequi.getText().contains("-")) {
                                            Stack<String> pila = getLista();
                                            for (int ar = 0; ar < pila.size(); ar++) {
                                                importarLista(pila.get(ar));
                                            }
                                        } else {
                                            importarLista(lblRequi.getText());
                                        }
                                        JOptionPane.showMessageDialog(this, "ARTICULOS ENVIADOS A APROBACION");
                                    }
                                }
                            }
                        }

                    }
                } else {
                    JFrame f = (JFrame) JOptionPane.getFrameForComponent(this);
                    verificarTotales ver = new verificarTotales(f, true);
                    boolean band = ver.getOption(lblRequi.getText());
                    if (band) {
                        Statement st = con.createStatement();
                        int fila = Tabla1.getSelectedRow();

                        String sql = "update Requisicion set Progreso = ? where Id = ?";
                        PreparedStatement pst = con.prepareStatement(sql);
                        String sql2 = "select Id, Comprar, LiberacionAlmacen from Requisicion where Id like '" + lblRequi.getText() + "'";
                        ResultSet rs = st.executeQuery(sql2);
                        String datos[] = new String[10];
                        boolean liberacion = false;
                        while (rs.next()) {
                            datos[1] = rs.getString("Comprar");
                            liberacion = rs.getBoolean("LiberacionAlmacen");
                        }
                        String d;
                        if (!liberacion) {
                            JOptionPane.showMessageDialog(this, "La requisicion no esta liberada por almacen", "Advertencia", JOptionPane.WARNING_MESSAGE);
                        } else {
                            if (datos[1] == null) {
                                d = "SI";
                            } else {
                                d = datos[1];
                            }
                            if (d.equals("NO")) {
                                pst.setString(1, "COTIZADO");
                                pst.setString(2, lblRequi.getText());
                            } else {
                                pst.setString(1, "APROBACION");
                                pst.setString(2, lblRequi.getText());
                            }

                            int n = pst.executeUpdate();

                            if (n > 0) {
                                limpiarTabla1();
                                verDatos();
                                crearNotificacion();
                                JOptionPane.showMessageDialog(this, "DATOS ENVIADOS");
                            }
                        }
                    }
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "ERROR AL ACTUALIZAR" + e, "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_AprobacionActionPerformed

    private void CancelarRequisicionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CancelarRequisicionActionPerformed
        int opc = JOptionPane.showConfirmDialog(this, "¿ESTAS SEGURO(A) QUE DESEAS CANCELAR ESTA REQUISICION?");
        if (opc == 0) {
            try {
                Connection con = null;
                Conexion con1 = new Conexion();
                con = con1.getConnection();

                int tab = Tabla1.getRowCount() - cont;
                if (cont > 0) {
                    tab = Tabla1.getRowCount() - cont - 1;
                }
                if (Tabla1.getSelectedRow() > tab) {
                    String act = "update edicionpo set Estado = ? where PO = ?";
                    PreparedStatement pst1 = con.prepareCall(act);

                    pst1.setString(1, "CANCELADO");
                    pst1.setString(2, id);
                    int n1 = pst1.executeUpdate();

                    if (n1 > 0) {
                        limpiarTabla1();
                        limpiarTabla();
                        verDatos();
                    }
                } else if (Tabla1.getSelectedRow() == tab) {

                } else {
                    String act = "update Requisicion set Progreso = ?, Completado = ? where Id = ?";
                    PreparedStatement pst1 = con.prepareCall(act);

                    pst1.setString(1, "CANCELADO");
                    pst1.setString(2, "SI");
                    pst1.setString(3, id);
                    int n1 = pst1.executeUpdate();

                    int n = 0;
                    for (int i = 0; i < Tabla2.getSelectedRows().length; i++) {
                        String sql = "update requisiciones set OC = ?, Notas = ? where Id = ?";
                        PreparedStatement pst = con.prepareStatement(sql);

                        pst.setString(1, "CANCELADO");
                        pst.setString(2, "CANCELADO");
                        pst.setString(3, Tabla2.getValueAt(Tabla2.getSelectedRows()[i], 7).toString());

                        n += pst.executeUpdate();
                    }

                    if (n > 0) {
                        JOptionPane.showMessageDialog(this, "Articulos cancelados");
                    }

                    if (n1 > 0) {
                        limpiarTabla1();
                        limpiarTabla();
                        verDatos();
                    }
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "ERROR AL CANCELAR REQUISICION" + e, "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_CancelarRequisicionActionPerformed

    private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem5ActionPerformed
        JFrame j = (JFrame) JOptionPane.getFrameForComponent(this);
        EstadoCuentas s = new EstadoCuentas(j, true);
        s.setVisible(true);
    }//GEN-LAST:event_jMenuItem5ActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void jMenuItem8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem8ActionPerformed
        JFrame j = (JFrame) JOptionPane.getFrameForComponent(this);
        VentanaEmergente.Compras.addProveedor a = new VentanaEmergente.Compras.addProveedor(j, true);
        a.setVisible(true);
    }//GEN-LAST:event_jMenuItem8ActionPerformed

    private void jMenu3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenu3ActionPerformed
        CrearOrden.setEnabled(false);
    }//GEN-LAST:event_jMenu3ActionPerformed

    private void verCotizacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_verCotizacionActionPerformed
        try {
            Connection con = null;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            int fila = Tabla1.getSelectedRow();
            String sql = "select * from requisicionpdf where NumRequisicion like '" + Tabla1.getValueAt(fila, 2).toString() + "'";
            ResultSet rs = st.executeQuery(sql);
            byte[] b = null;
            String nombre = "pdf.pdf";
            while (rs.next()) {
                b = rs.getBytes("Pdf");
                nombre = rs.getString("Nombre");
            }

            InputStream bos = new ByteArrayInputStream(b);

            int tamInput = bos.available();
            byte[] datosPdf = new byte[tamInput];
            bos.read(datosPdf, 0, tamInput);

            OutputStream out = new FileOutputStream(nombre);
            out.write(datosPdf);

            out.close();
            bos.close();

            Desktop.getDesktop().open(new File(nombre));

        } catch (SQLException | NumberFormatException | IOException e) {
            JOptionPane.showMessageDialog(this, "ERROR: " + e, "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_verCotizacionActionPerformed

    private void jLabel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseClicked
        dispose();
    }//GEN-LAST:event_jLabel1MouseClicked

    private void jLabel1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseEntered
        btnSalir.setBackground(java.awt.Color.red);
    }//GEN-LAST:event_jLabel1MouseEntered

    private void jLabel1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseExited
        btnSalir.setBackground(java.awt.Color.white);
    }//GEN-LAST:event_jLabel1MouseExited

    private void comprarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comprarActionPerformed
        try {
            Connection con = null;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();

            int tab = Tabla1.getRowCount() - cont;
            if (cont > 0) {
                tab = Tabla1.getRowCount() - cont - 1;
            }
            if (Tabla1.getSelectedRow() > tab) {
                String sql = "update edicionpo set Estado = 'COMPRADO' where PO like '" + Tabla1.getValueAt(Tabla1.getSelectedRow(), 2).toString() + "'";
                PreparedStatement pst = con.prepareStatement(sql);

                int n = pst.executeUpdate();

                if (n > 0) {
                    JOptionPane.showMessageDialog(this, "COMPRADO");
                    limpiarTabla1();
                    verDatos();
                }
            } else if (Tabla1.getSelectedRow() == tab) {

            } else {
                String sql = "update requisicion set Progreso = 'COMPRADO' where Id like '" + Tabla1.getValueAt(Tabla1.getSelectedRow(), 2).toString() + "'";
                PreparedStatement pst = con.prepareStatement(sql);

                int n = pst.executeUpdate();

                if (n > 0) {
                    JOptionPane.showMessageDialog(this, "COMPRADO");
                    limpiarTabla1();
                    verDatos();
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "ERROR: " + e, "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_comprarActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        JFrame f = (JFrame) JOptionPane.getFrameForComponent(this);
        EditarPO e = new EditarPO(f, true, numEmpleado);
        e.setLocationRelativeTo(e);
        e.setVisible(true);
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void Tabla1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Tabla1MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_Tabla1MouseEntered

    private void jMenuItem6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem6ActionPerformed
        DefaultTableModel miModelo = (DefaultTableModel) Tabla2.getModel();
        String f[] = {"", "", "", "", "", "", "", ""};
        miModelo.addRow(f);
    }//GEN-LAST:event_jMenuItem6ActionPerformed

    private void BorrarArticuloActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BorrarArticuloActionPerformed
        if (!Tabla2.getColumnName(7).equals("ID")) {
            JOptionPane.showMessageDialog(this, "DEBES SELECCIONAR LA TABLA CORRECTA", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
        } else if (Tabla2.getSelectedRow() < 0) {
            JOptionPane.showMessageDialog(this, "DEBES SELECCIONAR UN ELEMENTO DE LA TABLA");
        } else {
            int opc = JOptionPane.showConfirmDialog(this, "¿Estas seguro de eliminar el articulo de esta requisicion?");
            if (opc == 0) {
                int tab = Tabla1.getRowCount() - cont;
                if (cont > 0) {
                    tab = Tabla1.getRowCount() - cont - 1;
                }
                if (Tabla1.getSelectedRow() > tab) {
                    try {
                        Connection con;
                        Conexion con1 = new Conexion();
                        con = con1.getConnection();
                        int n = 0;
                        if (search) {
                            String sql3 = "select * from requisicionesmuestra where Id like '" + Tabla2.getValueAt(Tabla2.getSelectedRow(), 7).toString() + "'";
                            Statement st = con.createStatement();
                            ResultSet rs = st.executeQuery(sql3);
                            String parte = "";
                            while (rs.next()) {
                                parte = rs.getString("Codigo");
                            }

                            String sql2 = "delete from requisicionesmuestra where Id = ?";
                            PreparedStatement pst = con.prepareStatement(sql2);

                            pst.setString(1, Tabla2.getValueAt(Tabla2.getSelectedRow(), 7).toString());
                            n += pst.executeUpdate();

                            String sql4 = "delete from detallesedicionpo where Codigo = ?";
                            PreparedStatement pst4 = con.prepareStatement(sql4);

                            pst4.setString(1, parte);
                            n += pst4.executeUpdate();

                        } else {
                            String sql2 = "delete from requisiciones where Id = ?";
                            PreparedStatement pst = con.prepareStatement(sql2);

                            pst.setString(1, Tabla2.getValueAt(Tabla2.getSelectedRow(), 7).toString());
                            n += pst.executeUpdate();
                        }
                        if (n > 0) {
                            JOptionPane.showMessageDialog(this, "DATOS BORRADOS");
                        }
                    } catch (SQLException e) {
                        JOptionPane.showMessageDialog(this, "ERROR: " + e, "ERROR", JOptionPane.ERROR_MESSAGE);
                    }
                } else if (Tabla1.getSelectedRow() == tab) {

                } else {
                    try {
                        Connection con;
                        Conexion con1 = new Conexion();
                        con = con1.getConnection();
                        int n = 0;
                        if (search) {
                            String sql3 = "select * from requisicionesmuestra where Id like '" + Tabla2.getValueAt(Tabla2.getSelectedRow(), 7).toString() + "'";
                            Statement st = con.createStatement();
                            ResultSet rs = st.executeQuery(sql3);
                            String parte = "";
                            while (rs.next()) {
                                parte = rs.getString("Codigo");
                            }

                            String sql2 = "delete from requisicionesmuestra where Id = ?";
                            PreparedStatement pst = con.prepareStatement(sql2);

                            pst.setString(1, Tabla2.getValueAt(Tabla2.getSelectedRow(), 7).toString());
                            n += pst.executeUpdate();

                            String sql4 = "delete from requisiciones where Codigo = ?";
                            PreparedStatement pst4 = con.prepareStatement(sql4);

                            pst4.setString(1, parte);
                            n += pst4.executeUpdate();

                        } else {
                            String sql2 = "delete from requisiciones where Id = ?";
                            PreparedStatement pst = con.prepareStatement(sql2);

                            pst.setString(1, Tabla2.getValueAt(Tabla2.getSelectedRow(), 7).toString());
                            n += pst.executeUpdate();
                        }
                        if (n > 0) {
                            JOptionPane.showMessageDialog(this, "DATOS GUARDADOS");
                            clic(lblRequi.getText());
                        }
                    } catch (SQLException e) {
                        JOptionPane.showMessageDialog(this, "ERROR: " + e, "ERROR", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        }
    }//GEN-LAST:event_BorrarArticuloActionPerformed

    private void copiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_copiarActionPerformed
        copy = Tabla2.getValueAt(Tabla2.getSelectedRow(), 8).toString();
    }//GEN-LAST:event_copiarActionPerformed

    private void pegarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pegarActionPerformed

        for (int i = 0; i < Tabla2.getSelectedRows().length; i++) {
            Tabla2.setValueAt(copy, Tabla2.getSelectedRows()[i], 8);
        }
    }//GEN-LAST:event_pegarActionPerformed

    private void jMenuItem9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem9ActionPerformed
        JFrame f = (JFrame) JOptionPane.getFrameForComponent(this);
        Reclamos r = new Reclamos(f, true);
        r.setVisible(true);
    }//GEN-LAST:event_jMenuItem9ActionPerformed

    private void AgregarNotaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AgregarNotaActionPerformed
        if (Tabla2.getSelectedRow() < 0) {
            JOptionPane.showMessageDialog(this, "DEBES SELECCIONAR UNA FILA", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
        } else {
            JFrame f = (JFrame) JOptionPane.getFrameForComponent(this);
            AgregarNota a = new AgregarNota(f, false, Tabla2.getValueAt(Tabla2.getSelectedRow(), 7).toString());
            a.parte.setText("PARTE: " + Tabla2.getValueAt(Tabla2.getSelectedRow(), 4).toString());
            a.setLocation(x, y);

            a.setVisible(true);
        }
    }//GEN-LAST:event_AgregarNotaActionPerformed

    private void jPopupMenu1PopupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_jPopupMenu1PopupMenuWillBecomeVisible
        if (Tabla2.getSelectedRow() < 0) {
            AgregarNota.setEnabled(false);
            copiar.setEnabled(false);
        } else {
            AgregarNota.setEnabled(true);
            copiar.setEnabled(true);
        }
        if (Tabla2.getColumnName(7).equals("ID")) {
            AgregarNota.setEnabled(true);
        } else {
            AgregarNota.setEnabled(false);
        }
    }//GEN-LAST:event_jPopupMenu1PopupMenuWillBecomeVisible

    private void jMenuItem10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem10ActionPerformed
        try {
            Connection con;
            DefaultTableModel miModelo = (DefaultTableModel) Tabla1.getModel();
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            for (int i = Tabla1.getRowCount() - 1; i > 0; i--) {

                Statement st = con.createStatement();
                String s = "";
                if (Tabla1.getValueAt(i, 2) != null) {
                    s = Tabla1.getValueAt(i, 2).toString();
                }
                String sql = "select OC from requisiciones where NumRequisicion like '" + s + "'";
                ResultSet rs = st.executeQuery(sql);
                String datos[] = new String[10];
                while (rs.next()) {
                    datos[0] = rs.getString("OC");
                    if (datos[0] == null) {
                        break;
                    }
                }
                if (datos[0] != null) {

                    miModelo.removeRow(i);
                }
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "ERROR: " + e, "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jMenuItem10ActionPerformed

    private void jMenuItem11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem11ActionPerformed
        limpiarTabla1();
        verDatos();
    }//GEN-LAST:event_jMenuItem11ActionPerformed

    private void btn1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn1ActionPerformed
        buscarProyecto(proyecto[1]);
    }//GEN-LAST:event_btn1ActionPerformed

    private void btn2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn2ActionPerformed
        buscarProyecto(proyecto[2]);
    }//GEN-LAST:event_btn2ActionPerformed

    private void btn3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn3ActionPerformed
        buscarProyecto(proyecto[3]);
    }//GEN-LAST:event_btn3ActionPerformed

    private void btn4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn4ActionPerformed
        buscarProyecto(proyecto[4]);
    }//GEN-LAST:event_btn4ActionPerformed

    private void btn5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn5ActionPerformed
        buscarProyecto(proyecto[5]);
    }//GEN-LAST:event_btn5ActionPerformed

    private void btn6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn6ActionPerformed
        buscarProyecto(proyecto[6]);
    }//GEN-LAST:event_btn6ActionPerformed

    private void btn7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn7ActionPerformed
        buscarProyecto(proyecto[7]);
    }//GEN-LAST:event_btn7ActionPerformed

    private void btn8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn8ActionPerformed
        buscarProyecto(proyecto[8]);
    }//GEN-LAST:event_btn8ActionPerformed

    private void btn9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn9ActionPerformed
        buscarProyecto(proyecto[9]);
    }//GEN-LAST:event_btn9ActionPerformed

    private void btn10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn10ActionPerformed
        buscarProyecto(proyecto[10]);
    }//GEN-LAST:event_btn10ActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        if (verificarPrecios()) {
            guardarListaImportada();
            limpiarTablaImportada();
            importarLista(lblRequi.getText());
        } else {
            JOptionPane.showMessageDialog(this, "ERROR AL GUARDAR PRECIO/CANTIDAD", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void urgenciaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_urgenciaMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_urgenciaMouseClicked

    private void cxpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cxpActionPerformed
        if (!Tabla2.getColumnName(4).equals("NO PARTE")) {
            JOptionPane.showMessageDialog(this, "Debes seleccionar la tabla sin importar", "Advertencia", JOptionPane.WARNING_MESSAGE);
        } else {
            String articulos = "";
            String po;
            for (int i = 0; i < Tabla2.getSelectedRows().length; i++) {
                if (Tabla2.getValueAt(Tabla2.getSelectedRows()[i], 9) != null) {
                    po = Tabla2.getValueAt(Tabla2.getSelectedRows()[i], 9).toString();
                } else {
                    po = "";
                }
                if (po.equals("")) {
                    articulos = articulos + Tabla2.getValueAt(Tabla2.getSelectedRows()[i], 4).toString() + "\n";
                }
            }
            if (articulos.equals("")) {
                JOptionPane.showMessageDialog(this, "Los elementos seleccionados ya tienen PO", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
            } else {
                int opc = JOptionPane.showConfirmDialog(this, "¿Estas segur@ de enviar estos articulos:\n"
                        + "\n "
                        + articulos
                        + "\n a CXP?");
                if (opc == 0) {
                    try {
                        Connection con;
                        Conexion con1 = new Conexion();
                        con = con1.getConnection();
                        String sql = "update requisiciones set Notas = ? where Id = ?";
                        PreparedStatement pst = con.prepareStatement(sql);
                        int n = 0;

                        for (int i = 0; i < Tabla2.getSelectedRows().length; i++) {
                            pst.setString(1, "ESTE ARTICULO SE ENVIO A CUENTAS POR PAGAR");
                            pst.setString(2, Tabla2.getValueAt(Tabla2.getSelectedRows()[i], 7).toString());

                            n = pst.executeUpdate();
                        }

                        if (n > 0) {
                            JOptionPane.showMessageDialog(this, "Articulos enviados a CXP");
                        }

                    } catch (SQLException e) {
                        JOptionPane.showMessageDialog(this, "ERROR: " + e, "ERROR", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        }
    }//GEN-LAST:event_cxpActionPerformed

    private void agruparActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_agruparActionPerformed
        JFrame f = (JFrame) JOptionPane.getFrameForComponent(this);
        agr = new Agrupar(f, false, numEmpleado);
        agr.existe(Tabla1.getValueAt(Tabla1.getSelectedRow(), 2).toString());
        agr.setLocation(this.getWidth() / 4, this.getHeight() / 4);
        agr.setVisible(true);
    }//GEN-LAST:event_agruparActionPerformed

    private void historialActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_historialActionPerformed
        JFrame f = (JFrame) JOptionPane.getFrameForComponent(this);
        Historial his = new Historial(f, true);
        int col;
        if (Tabla2.getColumnName(2).equals("NUMERO PARTE")) {
            col = 2;
        } else {
            col = 4;
        }
        his.verHistorial(Tabla2.getValueAt(Tabla2.getSelectedRow(), col).toString());
        his.setVisible(true);
    }//GEN-LAST:event_historialActionPerformed

    private void buscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buscarActionPerformed
        String requi = mostrarDialogoEmergente();
        if (requi != null) {
            if (requi.equals("")) {
            } else {
                try {
                    Connection con;
                    Conexion con1 = new Conexion();
                    con = con1.getConnection();
                    Statement st = con.createStatement();
                    String sql = "select Id, NumeroEmpleado, Progreso, Completado, Fecha, Comprar from requisicion where Id like '" + requi + "'";
                    ResultSet rs = st.executeQuery(sql);
                    String empleado = "";
                    String estado = "";
                    String completado = "";
                    String fecha = "";
                    String comprar = "";
                    while (rs.next()) {
                        empleado = rs.getString("NumeroEmpleado");
                        estado = rs.getString("Progreso");
                        completado = rs.getString("Completado");
                        fecha = rs.getString("Fecha");
                        comprar = rs.getString("Comprar");
                    }
                    if (empleado == null || empleado.equals("")) {
                        JOptionPane.showMessageDialog(this, "NO EXISTE ESTA REQUISICION", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
                    } else {
                        String sql2 = "select * from registroempleados where NumEmpleado like '" + empleado + "'";
                        Statement st2 = con.createStatement();
                        ResultSet rs2 = st2.executeQuery(sql2);
                        while (rs2.next()) {
                            empleado = rs2.getString("Nombre") + " " + rs2.getString("Apellido");
                        }
                        JFrame f = (JFrame) JOptionPane.getFrameForComponent(this);
                        Estado es = new Estado(f, true);
                        es.lblEmpleado.setText(empleado);
                        es.lblEstado.setText(estado);
                        es.lblCompletado.setText(completado);
                        es.lblFecha.setText(fecha);
                        es.lblComprar.setText(comprar);
                        es.verRequisicion(requi);
                        es.setVisible(true);
                    }
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(this, "ERROR: " + e, "ERROR", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }//GEN-LAST:event_buscarActionPerformed

    private void buscar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buscar1ActionPerformed

    }//GEN-LAST:event_buscar1ActionPerformed

    private void jMenuItem12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem12ActionPerformed
        JFrame f = (JFrame) JOptionPane.getFrameForComponent(this);
        ReporteMes reporte = new ReporteMes(f, true);
        reporte.setLocationRelativeTo(f);
        reporte.setVisible(true);
    }//GEN-LAST:event_jMenuItem12ActionPerformed

    private void jMenuItem13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem13ActionPerformed
        JFrame f = (JFrame) JOptionPane.getFrameForComponent(this);
        TransferirRequisicion transferir = new TransferirRequisicion(f, true);
        transferir.setLocationRelativeTo(f);
        transferir.setVisible(true);
    }//GEN-LAST:event_jMenuItem13ActionPerformed

    private void jMenuItem14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem14ActionPerformed
        JFrame f = (JFrame) JOptionPane.getFrameForComponent(this);
        Thread hilo = new Thread() {
            public void run() {
                OCPendientes oc = new OCPendientes(f, true);
                oc.setLocationRelativeTo(f);
                oc.setVisible(true);
            }
        };
        hilo.start();
    }//GEN-LAST:event_jMenuItem14ActionPerformed

    //Agregar la tabla de entradasalmacen
    public void limpiarTablaEntradasAlmacen() {
        DefaultTableModel miModelo = (DefaultTableModel) Tabla2.getModel();
        String titulos[] = {"Requisitor", "Cantidad", "U.M.", "Descripcion", "No. Parte", "Proveedor", "No. Proyefto", "ID", "TE", "PO", "Fecha Proyecto", "Notas", "Llego", "Fecha Esperada", "Cant. Stock"};
        miModelo = new DefaultTableModel(null, titulos);

        Tabla2.setModel(miModelo);
    }

    private void Tabla2MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Tabla2MouseReleased
        fil = Tabla2.getSelectedRow();
    }//GEN-LAST:event_Tabla2MouseReleased

    private void Tabla2MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Tabla2MousePressed

    }//GEN-LAST:event_Tabla2MousePressed

    private void Tabla2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Tabla2MouseClicked
        x = evt.getXOnScreen();
        y = evt.getYOnScreen();
        if (Tabla2.getColumnName(Tabla2.getSelectedColumn()).equals("TE")) {
            JFrame f = (JFrame) JOptionPane.getFrameForComponent(this);
            fecha = new Fecha(f, true);
            filaFecha = Tabla2.getSelectedRow();
            fecha.btnGuardar.addActionListener(this);
            fecha.setVisible(true);
        }
    }//GEN-LAST:event_Tabla2MouseClicked


    private void Tabla2MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Tabla2MouseDragged

        if (Tabla2.getSelectedColumn() == 8) {
            for (int i = 0; i < Tabla2.getSelectedRows().length; i++) {
                Tabla2.setValueAt(Tabla2.getValueAt(fil, 8), Tabla2.getSelectedRows()[i], 8);
            }
        }
    }//GEN-LAST:event_Tabla2MouseDragged
    private javax.swing.JDialog dialogEntradas = null;

    private void jMenuItem15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem15ActionPerformed
        Espera espera = new Espera();
        espera.setVisible(true);

        Thread hilo = new Thread() {
            @Override
            public void run() {
                try {
                    // Si ya está abierta, solo traer al frente para evitar que se duplique 
                    if (dialogEntradas != null && dialogEntradas.isShowing()) {
                        dialogEntradas.toFront();
                        return;
                    }
                    entradasAlmacen panelEntradas = new entradasAlmacen();
                    panelEntradas.cargarEntradasAlmacen();

                    dialogEntradas = new javax.swing.JDialog();
                    dialogEntradas.setTitle("Entradas a Almacén");
                    dialogEntradas.setDefaultCloseOperation(javax.swing.JDialog.DISPOSE_ON_CLOSE);

                    dialogEntradas.getContentPane().add(panelEntradas);
                    dialogEntradas.pack();
                    dialogEntradas.setLocationRelativeTo(null);

                    dialogEntradas.addWindowListener(new java.awt.event.WindowAdapter() {
                        @Override
                        public void windowClosed(java.awt.event.WindowEvent e) {
                            dialogEntradas = null;
                        }
                    });

                } finally {
                    espera.dispose();
                }

                // Mostrar el diálogo SIEMPRE en el EDT
                javax.swing.SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        dialogEntradas.setVisible(true);
                    }
                });
            }
        };

        hilo.start();
    }//GEN-LAST:event_jMenuItem15ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem AbrirOrden;
    private javax.swing.JMenuItem AbrirRequi;
    private javax.swing.JMenuItem AgregarNota;
    private javax.swing.JMenuItem Aprobacion;
    private javax.swing.JMenuItem BorrarArticulo;
    private javax.swing.JMenuItem CancelarRequisicion;
    private javax.swing.JMenuItem CrearCoti;
    private javax.swing.JMenuItem CrearOrden;
    private javax.swing.JMenuItem CrearRequi;
    private javax.swing.JMenuItem ImportarOrden;
    private javax.swing.JTable Tabla1;
    private javax.swing.JTable Tabla2;
    private javax.swing.JMenuItem agrupar;
    private javax.swing.JButton btn1;
    private javax.swing.JButton btn10;
    private javax.swing.JButton btn2;
    private javax.swing.JButton btn3;
    private javax.swing.JButton btn4;
    private javax.swing.JButton btn5;
    private javax.swing.JButton btn6;
    private javax.swing.JButton btn7;
    private javax.swing.JButton btn8;
    private javax.swing.JButton btn9;
    private RSMaterialComponent.RSRadioButtonMaterial btnCompra;
    private rojeru_san.RSButtonRiple btnGuardar;
    private javax.swing.JPanel btnSalir;
    private javax.swing.JMenuItem buscar;
    private javax.swing.JMenuItem buscar1;
    private javax.swing.JMenuItem comprar;
    private javax.swing.JMenuItem copiar;
    private javax.swing.JMenuItem cxp;
    private javax.swing.JMenuItem historial;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem10;
    private javax.swing.JMenuItem jMenuItem11;
    private javax.swing.JMenuItem jMenuItem12;
    private javax.swing.JMenuItem jMenuItem13;
    private javax.swing.JMenuItem jMenuItem14;
    private javax.swing.JMenuItem jMenuItem15;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JMenuItem jMenuItem7;
    private javax.swing.JMenuItem jMenuItem8;
    private javax.swing.JMenuItem jMenuItem9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JPopupMenu jPopupMenu2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JPopupMenu.Separator jSeparator3;
    private javax.swing.JPopupMenu.Separator jSeparator4;
    private javax.swing.JPopupMenu.Separator jSeparator5;
    private javax.swing.JPopupMenu.Separator jSeparator6;
    private javax.swing.JPopupMenu.Separator jSeparator7;
    private javax.swing.JPopupMenu.Separator jSeparator8;
    private javax.swing.JLabel lblGastos;
    private javax.swing.JLabel lblRequi;
    private javax.swing.JLabel lblTitulo;
    private scrollPane.PanelRound panel1;
    private scrollPane.PanelRound panel10;
    private scrollPane.PanelRound panel2;
    private scrollPane.PanelRound panel3;
    private scrollPane.PanelRound panel4;
    private scrollPane.PanelRound panel5;
    private scrollPane.PanelRound panel6;
    private scrollPane.PanelRound panel7;
    private scrollPane.PanelRound panel8;
    private scrollPane.PanelRound panel9;
    private javax.swing.JPanel panelEditar;
    private javax.swing.JPanel panelGastos;
    private javax.swing.JMenuItem pegar;
    private javax.swing.JLabel txtCambio;
    private javax.swing.JLabel txtCambio1;
    private javax.swing.JTextArea txtComentarios;
    private javax.swing.JLabel urgencia;
    private javax.swing.JMenuItem verCotizacion;
    // End of variables declaration//GEN-END:variables

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            if (e.getSource() == elegir.btnCrear) {
                int tab = Tabla1.getRowCount() - cont;
                if (cont > 0) {
                    tab = Tabla1.getRowCount() - cont - 1;
                }
                if (Tabla1.getSelectedRow() > tab) {
                    generarOrdenCompra(true);
                } else if (tab == 0) {

                } else {
                    generarOrdenCompra(false);
                }
            }
        } catch (Exception ex) {

        }
        try {
            if (e.getSource() == elegir.btnCancelar) {
                elegir.dispose();
            }
        } catch (Exception a) {
//            JOptionPane.showMessageDialog(this, "ERROR: "+a);
        }

        try {
            if (fecha != null) {
                if (e.getSource() == fecha.btnGuardar) {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    if (Tabla2.getColumnName(0).equals("REQUISITOR")) {
                        try {
                            Connection con;
                            Conexion con1 = new Conexion();
                            con = con1.getConnection();
                            String sql = "update requisiciones set TE = ? where Id = ?";
                            PreparedStatement pst = con.prepareStatement(sql);

                            String fecha = sdf.format(this.fecha.calendario.getDate());

                            pst.setString(1, fecha);
                            pst.setString(2, Tabla2.getValueAt(Tabla2.getSelectedRow(), 7).toString());

                            int n = pst.executeUpdate();

                            if (n > 0) {
                                JOptionPane.showMessageDialog(this, "Fecha guardada");
                            }

                        } catch (SQLException ex) {
                            JOptionPane.showMessageDialog(this, "ERROR: " + ex, "ERROR", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                    String fechaa = sdf.format(this.fecha.calendario.getDate());
                    Tabla2.setValueAt(fechaa, Tabla2.getSelectedRow(), 8);
                    fecha.dispose();
                }
            }
        } catch (Exception a) {

        }

    }
}
