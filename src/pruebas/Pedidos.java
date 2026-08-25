package pruebas;

import Conexiones.Conexion;
import Modelo.CabezeraRemisiones;
import VO.whatsappMessage;
import VentanaEmergente.EntregaRequisicion.Cantidad;
import VentanaEmergente.EntregaRequisicion.verPedidos;
import VentanaEmergente.Pedidos.Iniciar;
import VentanaEmergente.Pedidos.cantidad;
import VentanaEmergente.Prestamo.verFolio;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import scrollPane.ScrollBarCustom;

public class Pedidos extends javax.swing.JInternalFrame implements ActionListener, MouseListener {

    public JButton btnPedido[];
    public JPanel panelPedido[];
    public String requi[];
    public String color[];
    int contBtnP, contBtnA;
    public JButton botones[];
    public JPanel pseccion[];
    public JPanel panel[];
    String parte[];
    public String cantidad[], cantidadNew[], descripcion[], proyecto[], requis[], ubicacion[];
    String id[];
    String idAr[];
    String folio[];
    String numEmpleado;
    String requisitor;

    public String verEmpleado(String empleado) {
        String nombre = "";
        try {
            Connection con;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            String sql = "select * from registroempleados where NumEmpleado like '" + empleado + "'";
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                nombre = rs.getString("Nombre") + " " + rs.getString("Apellido");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "ERROR: " + e, "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        return nombre;
    }

    public void imprimir() {
        try {
            String ruta = "C:/Pruebas/DOCS/Imprimir.pdf";
            Document document = new Document(PageSize.A4, 36, 36, 90, 36);
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(ruta));

            CabezeraRemisiones encabezado = new CabezeraRemisiones();
            encabezado.setEncabezado("ENCABEZADO DE ENTREGA DE REQUISICION");
            writer.setPageEvent(encabezado);
            document.open();

            //------------------------------------------FUENTES------------------------------------------------------------
            com.itextpdf.text.Font fuente1 = new com.itextpdf.text.Font();
            fuente1.setSize(26);
            fuente1.setFamily("Roboto");
            fuente1.setColor(BaseColor.BLACK);
            fuente1.setStyle(com.itextpdf.text.Font.BOLD);

            com.itextpdf.text.Font fuente2 = new com.itextpdf.text.Font();
            fuente2.setSize(16);
            fuente2.setFamily("Roboto");
            fuente2.setColor(BaseColor.BLACK);
            fuente2.setStyle(com.itextpdf.text.Font.BOLD);

            com.itextpdf.text.Font fuente3 = new com.itextpdf.text.Font();
            fuente3.setSize(12);
            fuente3.setFamily("Roboto");
            fuente3.setColor(BaseColor.BLACK);
            fuente3.setStyle(com.itextpdf.text.Font.NORMAL);

            com.itextpdf.text.Font fuente4 = new com.itextpdf.text.Font();
            fuente4.setSize(12);
            fuente4.setFamily("Roboto");
            fuente4.setColor(BaseColor.BLACK);
            fuente4.setStyle(com.itextpdf.text.Font.BOLD);

            com.itextpdf.text.Font fuente5 = new com.itextpdf.text.Font();
            fuente5.setSize(12);
            fuente5.setFamily("Roboto");
            fuente5.setColor(BaseColor.WHITE);
            fuente5.setStyle(com.itextpdf.text.Font.BOLD);

            com.itextpdf.text.Font fuenteArticulos = new com.itextpdf.text.Font();
            fuenteArticulos.setSize(10);
            fuenteArticulos.setFamily("Roboto");
            fuenteArticulos.setColor(BaseColor.BLACK);
            fuenteArticulos.setStyle(com.itextpdf.text.Font.NORMAL);

            com.itextpdf.text.Font fuenteFirmas = new com.itextpdf.text.Font();
            fuenteFirmas.setSize(8);
            fuenteFirmas.setFamily("Roboto");
            fuenteFirmas.setColor(BaseColor.BLACK);
            fuenteFirmas.setStyle(com.itextpdf.text.Font.NORMAL);
            //--------------------------------------------------------------------------------------------------------------
            //-------------------------------------------------TITULOS---------------------------------------------------
            Paragraph p = new Paragraph("Vale para entrega de requisiciones", fuente1);
            p.setAlignment(Element.ALIGN_CENTER);

            Paragraph p1 = new Paragraph("FOLIO:        ", fuente2);
            p1.setAlignment(Element.ALIGN_RIGHT);

            PdfPTable tabla1 = new PdfPTable(4);
            tabla1.setWidthPercentage(100);
            float[] medidads = {100, 400, 100, 200};
            tabla1.setWidths(medidads);

            PdfPCell ce = new PdfPCell(new Paragraph("Empleado: ", fuente3));
            ce.setBorder(0);
            ce.setHorizontalAlignment(Element.ALIGN_CENTER);
            PdfPCell ce1 = new PdfPCell(new Paragraph(verEmpleado(requisitor), fuente3));
            ce1.setBorder(2);
            ce1.setHorizontalAlignment(Element.ALIGN_CENTER);
            PdfPCell ce2 = new PdfPCell(new Paragraph("Fecha: ", fuente3));
            ce2.setBorder(0);
            Date d = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            String fecha = sdf.format(d);
            ce2.setHorizontalAlignment(Element.ALIGN_CENTER);
            PdfPCell ce3 = new PdfPCell(new Paragraph(fecha, fuente3));
            ce3.setBorder(2);
            ce3.setHorizontalAlignment(Element.ALIGN_CENTER);

            tabla1.addCell(ce);
            tabla1.addCell(ce1);
            tabla1.addCell(ce2);
            tabla1.addCell(ce3);
            //--------------------------------------------------------------------------------------------------------------
            //-----------------------------------------TABLA DE ARTICULOS---------------------------------------------------
            PdfPTable tabla2 = new PdfPTable(7);
            tabla2.setWidthPercentage(100);
            float[] medidads2 = {50, 250, 200, 100, 100, 70, 50};
            tabla2.setWidths(medidads2);

            PdfPCell c1 = new PdfPCell(new Paragraph("C.T", fuente4));
            c1.setBorder(0);
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);

            PdfPCell c7 = new PdfPCell(new Paragraph("C.E", fuente4));
            c7.setBorder(0);
            c7.setHorizontalAlignment(Element.ALIGN_CENTER);

            PdfPCell c2 = new PdfPCell(new Paragraph("Descripcion", fuente4));
            c2.setBorder(0);
            c2.setHorizontalAlignment(Element.ALIGN_CENTER);
            PdfPCell c3 = new PdfPCell(new Paragraph("No de Parte", fuente4));
            c3.setBorder(0);
            c3.setHorizontalAlignment(Element.ALIGN_CENTER);
            PdfPCell c4 = new PdfPCell(new Paragraph("Requi", fuente4));
            c4.setBorder(0);
            c4.setHorizontalAlignment(Element.ALIGN_CENTER);
            PdfPCell c5 = new PdfPCell(new Paragraph("Proyecto", fuente4));
            c5.setBorder(0);
            c5.setHorizontalAlignment(Element.ALIGN_CENTER);

            PdfPCell c6 = new PdfPCell(new Paragraph("Ubic", fuente4));
            c6.setBorder(0);
            c6.setHorizontalAlignment(Element.ALIGN_CENTER);

            PdfPCell blanco = new PdfPCell(new Paragraph("JORGE", fuente5));
            blanco.setBorder(0);
            blanco.setHorizontalAlignment(Element.ALIGN_CENTER);

            tabla2.addCell(blanco);
            tabla2.addCell(blanco);
            tabla2.addCell(blanco);
            tabla2.addCell(blanco);
            tabla2.addCell(blanco);
            tabla2.addCell(blanco);
            tabla2.addCell(blanco);
            tabla2.addCell(c1);
            tabla2.addCell(c2);
            tabla2.addCell(c3);
            tabla2.addCell(c4);
            tabla2.addCell(c5);
            tabla2.addCell(c6);
            tabla2.addCell(c7);

            for (int i = 0; i < contBtnA; i++) {
                for (int j = 0; j < 7; j++) {
                    PdfPCell cel = null;
                    if (j == 0) {
                        cel = new PdfPCell(new Paragraph(cantidad[i], fuenteArticulos));
                    } else if (j == 1) {
                        cel = new PdfPCell(new Paragraph(descripcion[i], fuenteArticulos));
                    } else if (j == 2) {
                        cel = new PdfPCell(new Paragraph(parte[i], fuenteArticulos));
                    } else if (j == 3) {
                        cel = new PdfPCell(new Paragraph(requis[i], fuenteArticulos));
                    } else if (j == 4) {
                        cel = new PdfPCell(new Paragraph(proyecto[i], fuenteArticulos));
                    } else if (j == 5) {
                        cel = new PdfPCell(new Paragraph(ubicacion[i], fuenteArticulos));
                    } else if (j == 6) {
                        cel = new PdfPCell(new Paragraph(" ", fuente5));
                    }

                    cel.setBorder(0);
                    if (i % 2 == 0) {
                        cel.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    }
                    tabla2.addCell(cel);

                }
            }
            //--------------------------------------------------------------------------------------------------------------
            //--------------------------TABLA PRA AGREGAR FIRMAS--------------------------------------------------
            PdfPTable tablaFirmas = new PdfPTable(3);
            tablaFirmas.setWidthPercentage(100);

            PdfPCell f1 = new PdfPCell(new Paragraph("X", fuente2));
            f1.setBorder(2);

            PdfPCell f4 = new PdfPCell(new Paragraph(verEmpleado(numEmpleado), fuenteArticulos));
            f4.setBorder(2);
            f4.setHorizontalAlignment(Element.ALIGN_CENTER);

            PdfPCell f2 = new PdfPCell(new Paragraph("Entrega almacen", fuenteFirmas));
            f2.setBorder(0);
            f2.setHorizontalAlignment(Element.ALIGN_CENTER);

            PdfPCell f3 = new PdfPCell(new Paragraph("Recibe empleado", fuenteFirmas));
            f3.setBorder(0);
            f3.setHorizontalAlignment(Element.ALIGN_CENTER);

            PdfPCell blank = new PdfPCell(new Paragraph("santacruz", fuente5));
            blank.setBorder(0);

            tablaFirmas.addCell(blank);
            tablaFirmas.addCell(blank);
            tablaFirmas.addCell(blank);
            tablaFirmas.addCell(f4);
            tablaFirmas.addCell(blank);
            tablaFirmas.addCell(f1);
            tablaFirmas.addCell(f2);
            tablaFirmas.addCell(blank);
            tablaFirmas.addCell(f3);

            //----------------------------------------------------------------------------------------------------
            //---------------------DOCUMENTOS PARA AGREGAR-----------------------------------
            document.add(p);
            document.add(p1);
            document.add(tabla1);
            document.add(tabla2);
            document.add(tablaFirmas);
            //-------------------------------------------------------------------------------
            document.close();
            Desktop.getDesktop().open(new File(ruta));

            Connection con;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            String sql = "";
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "ERROR: " + e, "ERROR", JOptionPane.ERROR_MESSAGE);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Pedidos.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DocumentException | IOException ex) {
            Logger.getLogger(Pedidos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String addCantidad(String texto, String cantidad) {
        String buscar = "/";
        String fin = "";
        boolean band = true;
        int con = 0;
        int aux1 = 0, aux2 = 0;
        char arreglo[] = texto.toCharArray();
        for (int j = 0; j < texto.length(); j++) {
            String letra = String.valueOf(arreglo[j]);
            if (buscar.equalsIgnoreCase(letra)) {

                if (con == 5) {
                    aux1 = j + 48;
                } else if (con == 6) {
                    aux2 = j - 1;
                }
                if (texto.substring(j + 1, j + 3).equals("P>") || texto.substring(j + 1, j + 3).equals("di")) {
                    con++;
                }
            }
        }
        String t1, t2;
        t1 = texto.substring(0, aux1);
        t2 = texto.substring(aux2, texto.length());
        fin = t1 + cantidad + t2;
        return fin;
    }

    public String addColor(String texto, String color) {

        String textoF;

        String ti, tf;

        ti = texto.substring(0, 54);
        tf = texto.substring(65, texto.length());

        textoF = ti + color + tf;
        return textoF;
    }

    public void limpiarPanelPedidos() {
        panelPedidos.removeAll();
        revalidate();
        repaint();
    }

    public void addBotonesPedido(int opc) {
        try {
            Connection con;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            String est = "";
            if (opc == 0) {
                est = "!= 'ENCONTRADO, COMPLETO' and Estado != 'ENTREGADO'";
            } else if (opc == 1) {
                est = "like 'ENCONTRADO, COMPLETO'";
            }
            String sql = "select * from pedrequisicion where Estado " + est + " order by id desc";
            ResultSet rs = st.executeQuery(sql);
            String datos[] = new String[10];
            String sql2 = "select * from pedRequisicion where Estado " + est + " order by id desc";
            Statement st2 = con.createStatement();
            ResultSet rs2 = st2.executeQuery(sql2);
            int cont = 0;
            while (rs2.next()) {
                cont++;
            }

            btnPedido = new JButton[cont];
            panelPedido = new JPanel[cont];
            requi = new String[cont];
            color = new String[cont];

            contBtnP = 0;
            while (rs.next()) {
                datos[0] = rs.getString("Id");
                requi[contBtnP] = datos[0];
                datos[1] = rs.getString("Fecha");
                datos[2] = rs.getString("Estado");

                panelPedido[contBtnP] = new JPanel();
                panelPedido[contBtnP].setBackground(new Color(153, 204, 255));

                btnPedido[contBtnP] = new JButton();
                char c = '"';
                btnPedido[contBtnP] = new JButton();
//                btnPedido[contBtnP].setBackground(new java.awt.Color(123,204,255));
                btnPedido[contBtnP].setFont(new java.awt.Font("Roboto", 0, 12));
                btnPedido[contBtnP].setBorder(null);
                btnPedido[contBtnP].setBorderPainted(false);
                btnPedido[contBtnP].setContentAreaFilled(false);
                btnPedido[contBtnP].setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
                btnPedido[contBtnP].setFocusPainted(false);

                String color = "153,204,255";
                if (datos[2].equals("BUSCANDO")) {
                    color = "157,154,210";
                } else if (datos[2].equals("ENCONTRADO, INCOMPLETO")) {
                    color = "255,179,255";
                }
                this.color[contBtnP] = color;
                btnPedido[contBtnP].setText(
                        "<html>"
                        + "<div style='width: 190px; background-color: rgb(" + color + "); padding: 5px; margin: 5px;'>"
                        + "<p style='font-size:14px;  text-align: center; font-weight: 700;'>" + datos[0] + "</p>"
                        + "<p style='font-size:10px;'>" + datos[2] + "</p>"
                        + "<P style='font-size:8px;'>" + datos[1] + "</P>"
                        + "</div>"
                        + "</html>"
                );

                btnPedido[contBtnP].addActionListener(this);
                btnPedido[contBtnP].addMouseListener(this);
                panelPedido[contBtnP].add(btnPedido[contBtnP]);
                panelPedidos.add(btnPedido[contBtnP]);
                contBtnP++;
                revalidate();
                repaint();
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "ERROR: " + e, "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void limpiarArti() {
        panelArti.removeAll();
        revalidate();
        repaint();
    }

    public void paneles(String requisicion) {
        limpiarArti();
        try {
            Connection con = null;
            Conexion con1 = new Conexion();
            con = con1.getConnection();

            int cont1 = 0;
            Statement st = con.createStatement();
            String sql = "select Id from detrequisicion where IdPedido like '" + requisicion + "'";
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                cont1++;
            }
            contBtnA = cont1;
            botones = new JButton[cont1];
            pseccion = new JPanel[cont1];
            panel = new JPanel[cont1];
            parte = new String[cont1];
            cantidad = new String[cont1];
            id = new String[cont1];
            idAr = new String[cont1];
            folio = new String[cont1];
            cantidadNew = new String[cont1];
            descripcion = new String[cont1];
            ubicacion = new String[cont1];
            proyecto = new String[cont1];
            requis = new String[cont1];

            int cont = 0;

            int col = 0;
            int i = 0;
            String sql2 = "select * from detrequisicion where IdPedido like '" + requisicion + "' order by IdArticulo";
            Statement st2 = con.createStatement();
            ResultSet rs2 = st2.executeQuery(sql2);
            boolean ban = true;
            int ac = 0;
            int ancho = (jScrollPane2.getWidth()) / 7;
            JLabel l = new javax.swing.JLabel();
            l.setText("    ");
            while (rs2.next()) {

                if (ac % 5 == 0 || ban == true) {
                    pseccion[cont] = new JPanel(new FlowLayout());
                    pseccion[cont].setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 10, 5));
                    pseccion[cont].setBackground(Color.white);
                    cont++;
                }
                String opc = rs2.getString("Cantidad");
                idAr[i] = rs2.getString("Id");
                id[i] = rs2.getString("IdArticulo");

                String sql3 = "select * from requisiciones where Id like '" + id[i] + "'";
                Statement st3 = con.createStatement();
                ResultSet rs3 = st3.executeQuery(sql3);
                String dat[] = new String[10];
                while (rs3.next()) {
                    dat[0] = rs3.getString("Codigo");
                    dat[1] = rs3.getString("Descripcion");
                    dat[2] = rs3.getString("Proyecto");
                    dat[3] = rs3.getString("Ubicacion");
                    dat[4] = rs3.getString("NumRequisicion");
                }
                descripcion[i] = dat[1];
                proyecto[i] = dat[2];
                parte[i] = dat[0];
                ubicacion[i] = dat[3];
                requis[i] = dat[4];
                cantidad[i] = rs2.getString("Cantidad");
                botones[i] = new JButton(
                        "<html>"
                        + "<div style='width: " + ancho + "px;'>"
                        + "<p style='font-size:14px;  text-align: center; font-weight: 700;'>" + dat[0] + "</P>"
                        + "<p style='font-size:10px;'>" + dat[1] + "</P>"
                        + "<P style='font-size:8px;'>" + opc + "</P>"
                        + "<P style='font-size:10px; font-weight: 700;'>" + dat[2] + "</P>"
                        + "<P style='font-size:10px; font-weight: 700;'>" + dat[3] + "</P>"
                        + "<P style='font-size:10px; font-weight: 700;'>" + dat[4] + "</P>"
                        + "<P style='font-size:12px; font-weight: 700;'></P>"
                        + "</div>"
                        + "</html>");
                botones[i].setBackground(new java.awt.Color(255, 255, 255));
//                    botones[i].setComponentPopupMenu(popMenu);
                botones[i].setFont(new java.awt.Font("Roboto", 0, 12));
                botones[i].setBorder(null);
                botones[i].setBorderPainted(false);
                botones[i].setContentAreaFilled(false);
                botones[i].setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
                botones[i].setFocusPainted(false);
                botones[i].addActionListener(this);
                botones[i].addMouseListener(this);
                String cant = rs2.getString("CantidadE");

                panel[i] = new JPanel();
                panel[i].setBackground(Color.white);
                if (cant != null) {
                    botones[i].setText(addCantidad(botones[i].getText(), cant));

                    double f = Double.parseDouble(cant);
                    cantidadNew[i] = f + "";
                    if (f == 0.0) {
                        panel[i].setBackground(Color.red);
                        botones[i].setText(addCantidad(botones[i].getText(), String.valueOf(f)));
                        botones[i].setForeground(Color.white);
                    } else if (f > 0 && f < Double.parseDouble(cantidad[i])) {
                        panel[i].setBackground(Color.yellow);
                        botones[i].setForeground(Color.black);
                        botones[i].setText(addCantidad(botones[i].getText(), String.valueOf(f)));
                    } else if (Double.parseDouble(cantidad[i]) == f) {
                        panel[i].setBackground(Color.green);
                        botones[i].setForeground(Color.black);
                        botones[i].setText(addCantidad(botones[i].getText(), String.valueOf(f)));

                    } else {
                        JOptionPane.showMessageDialog(null, "CANTIDAD FUERA DE RANGO");
                    }
                }
                panel[i].add(botones[i]);
                pseccion[cont - 1].add(panel[i]);
                panelArti.add(pseccion[cont - 1]);

                i++;
                ac++;
                ban = false;
            }
            panelArti.add(l);
            col++;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "ERROR: " + e, "ERROR", JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(verPedidos.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public Pedidos(String id) {
        initComponents();
        ((BasicInternalFrameUI) this.getUI()).setNorthPane(null);
        addBotonesPedido(0);
        jScrollPane2.setVerticalScrollBar(new ScrollBarCustom(new Color(0, 165, 255)));
        jScrollPane1.setVerticalScrollBar(new ScrollBarCustom(new Color(0, 165, 255)));
        numEmpleado = id;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        panelSalir = new javax.swing.JPanel();
        lblSalir = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        panelArti = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        btnGuardar = new rojeru_san.RSButtonRiple();
        jPanel11 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jPanel8 = new javax.swing.JPanel();
        jButton4 = new javax.swing.JButton();
        jPanel9 = new javax.swing.JPanel();
        btnImprimir = new javax.swing.JButton();
        panelAjustar = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        jPanel12 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        lblId = new javax.swing.JLabel();
        jPanel13 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        panelPedidos = new javax.swing.JPanel();
        jPanel14 = new javax.swing.JPanel();
        nuevos = new RSMaterialComponent.RSRadioButtonMaterial();
        entrega = new RSMaterialComponent.RSRadioButtonMaterial();

        setBackground(new java.awt.Color(255, 255, 255));
        setBorder(null);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new java.awt.BorderLayout());

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new java.awt.BorderLayout());

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 42)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 153, 255));
        jLabel1.setText("     PEDIDOS     ");
        jPanel3.add(jLabel1);

        jPanel2.add(jPanel3, java.awt.BorderLayout.CENTER);

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));

        panelSalir.setBackground(new java.awt.Color(255, 255, 255));

        lblSalir.setFont(new java.awt.Font("Roboto Black", 1, 18)); // NOI18N
        lblSalir.setText("  X  ");
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

        jPanel6.add(panelSalir);

        jPanel2.add(jPanel6, java.awt.BorderLayout.EAST);

        jPanel1.add(jPanel2, java.awt.BorderLayout.PAGE_START);

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setLayout(new java.awt.BorderLayout(3, 3));

        jScrollPane2.setBorder(null);
        jScrollPane2.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        panelArti.setBackground(new java.awt.Color(255, 255, 255));
        panelArti.setLayout(new javax.swing.BoxLayout(panelArti, javax.swing.BoxLayout.Y_AXIS));
        jScrollPane2.setViewportView(panelArti);

        jPanel4.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        jPanel10.setBackground(new java.awt.Color(248, 248, 248));

        btnGuardar.setText("GUARDAR");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });
        jPanel10.add(btnGuardar);

        jPanel4.add(jPanel10, java.awt.BorderLayout.PAGE_END);

        jPanel11.setBackground(new java.awt.Color(255, 255, 255));
        jPanel11.setLayout(new java.awt.BorderLayout());

        jPanel5.setBackground(new java.awt.Color(248, 248, 248));
        jPanel5.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 15, 8));

        jPanel7.setBackground(new java.awt.Color(51, 153, 255));

        jButton1.setBackground(new java.awt.Color(102, 153, 255));
        jButton1.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("SE ENCONTRO TODO");
        jButton1.setBorder(null);
        jButton1.setBorderPainted(false);
        jButton1.setContentAreaFilled(false);
        jButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton1.setFocusPainted(false);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel7.add(jButton1);

        jPanel5.add(jPanel7);

        jPanel8.setBackground(new java.awt.Color(51, 153, 255));

        jButton4.setBackground(new java.awt.Color(102, 153, 255));
        jButton4.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jButton4.setForeground(new java.awt.Color(255, 255, 255));
        jButton4.setText("NO SE ENCONTRO NADA");
        jButton4.setBorder(null);
        jButton4.setBorderPainted(false);
        jButton4.setContentAreaFilled(false);
        jButton4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton4.setFocusPainted(false);
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jPanel8.add(jButton4);

        jPanel5.add(jPanel8);

        jPanel9.setBackground(new java.awt.Color(51, 153, 255));

        btnImprimir.setBackground(new java.awt.Color(102, 153, 255));
        btnImprimir.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        btnImprimir.setForeground(new java.awt.Color(255, 255, 255));
        btnImprimir.setText("IMPRIMIR");
        btnImprimir.setBorder(null);
        btnImprimir.setBorderPainted(false);
        btnImprimir.setContentAreaFilled(false);
        btnImprimir.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnImprimir.setFocusPainted(false);
        btnImprimir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnImprimirActionPerformed(evt);
            }
        });
        jPanel9.add(btnImprimir);

        jPanel5.add(jPanel9);

        panelAjustar.setBackground(new java.awt.Color(51, 153, 255));

        jButton2.setBackground(new java.awt.Color(102, 153, 255));
        jButton2.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("AJUSTAR");
        jButton2.setBorder(null);
        jButton2.setBorderPainted(false);
        jButton2.setContentAreaFilled(false);
        jButton2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton2.setFocusPainted(false);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        panelAjustar.add(jButton2);

        jPanel5.add(panelAjustar);

        jPanel11.add(jPanel5, java.awt.BorderLayout.CENTER);

        jPanel12.setBackground(new java.awt.Color(255, 255, 255));

        jLabel2.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("ID PEDIDO: ");
        jPanel12.add(jLabel2);

        lblId.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        lblId.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblId.setText("SIN SELECCIONAR");
        jPanel12.add(lblId);

        jPanel11.add(jPanel12, java.awt.BorderLayout.PAGE_START);

        jPanel4.add(jPanel11, java.awt.BorderLayout.NORTH);

        jPanel1.add(jPanel4, java.awt.BorderLayout.CENTER);

        jPanel13.setPreferredSize(new java.awt.Dimension(300, 606));
        jPanel13.setRequestFocusEnabled(false);
        jPanel13.setLayout(new java.awt.BorderLayout());

        jScrollPane1.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane1.setBorder(null);
        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        panelPedidos.setBackground(new java.awt.Color(255, 255, 255));
        panelPedidos.setForeground(new java.awt.Color(255, 153, 51));
        panelPedidos.setPreferredSize(new java.awt.Dimension(250, 606));
        panelPedidos.setLayout(new javax.swing.BoxLayout(panelPedidos, javax.swing.BoxLayout.Y_AXIS));
        jScrollPane1.setViewportView(panelPedidos);

        jPanel13.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jPanel14.setBackground(new java.awt.Color(255, 255, 255));

        buttonGroup1.add(nuevos);
        nuevos.setSelected(true);
        nuevos.setText("Nuevos");
        nuevos.setFocusPainted(false);
        nuevos.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        nuevos.setPreferredSize(new java.awt.Dimension(120, 40));
        nuevos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nuevosActionPerformed(evt);
            }
        });
        jPanel14.add(nuevos);

        buttonGroup1.add(entrega);
        entrega.setText("Entrega");
        entrega.setFocusPainted(false);
        entrega.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        entrega.setPreferredSize(new java.awt.Dimension(120, 40));
        entrega.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                entregaActionPerformed(evt);
            }
        });
        jPanel14.add(entrega);

        jPanel13.add(jPanel14, java.awt.BorderLayout.NORTH);

        jPanel1.add(jPanel13, java.awt.BorderLayout.WEST);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

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

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        panelAjustar.setBackground(new Color(51, 153, 255));
        if (entrega.isSelected()) {
            JOptionPane.showMessageDialog(this, "NO SE PERMITE HACER CAMBIOS");
        } else {
            for (int i = 0; i < contBtnA; i++) {
                panel[i].setBackground(Color.green);
                botones[i].setForeground(Color.black);
                botones[i].setText(addCantidad(botones[i].getText(), cantidad[i]));
                cantidadNew[i] = cantidad[i];
            }
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        panelAjustar.setBackground(new Color(51, 153, 255));
        if (entrega.isSelected()) {
            JOptionPane.showMessageDialog(this, "NO SE PERMITE HACER CAMBIOS");
        } else {
            for (int i = 0; i < contBtnA; i++) {
                panel[i].setBackground(Color.red);
                botones[i].setForeground(Color.white);
                botones[i].setText(addCantidad(botones[i].getText(), "0"));
                cantidadNew[i] = "0";
            }
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        panelAjustar.setBackground(new Color(51, 153, 255));
        try {
            Connection con;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            if (entrega.isSelected()) {
                Statement st6 = con.createStatement();
                String sql6 = "select * from foliorequisiciones";
                ResultSet rs6 = st6.executeQuery(sql6);
                String f = "";
                while (rs6.next()) {
                    f = rs6.getString("Id");
                }
                int folio = Integer.parseInt(f) + 1;

                for (int i = 0; i < contBtnA; i++) {
                    String sql2 = "select * from requisiciones where Id like '" + id[i] + "'";
                    Statement st = con.createStatement();
                    ResultSet rs2 = st.executeQuery(sql2);
                    String datos[] = new String[10];
                    String fol = null;
                    while (rs2.next()) {
                        datos[0] = rs2.getString("Cantidad");
                        datos[1] = rs2.getString("CantidadEntregada");
                        fol = rs2.getString("Folio");
                    }
                    if (fol == null) {
                        fol = String.valueOf(folio);
                    } else {
                        fol = folio + "," + String.valueOf(folio);
                    }
                    double cantE, cantF;
                    if (datos[1] == null) {
                        cantE = 0;
                    } else {
                        cantE = Double.parseDouble(datos[1]);
                    }
                    cantF = cantE + Double.parseDouble(cantidadNew[i]);
                    double cant = Double.parseDouble(datos[0]);
                    String mens;
                    if (cant == cantF) {
                        mens = "SI";
                    } else {
                        mens = null;
                    }
                    String sql = "update requisiciones set CantidadEntregada = ?, Entregado = ?, Folio = ? where id = ?";
                    PreparedStatement pst = con.prepareStatement(sql);

                    pst.setString(1, String.valueOf(cantF));
                    pst.setString(2, mens);
                    pst.setString(3, fol);
                    pst.setString(4, id[i]);
                    pst.executeUpdate();
                }

                String sql = "update pedrequisicion set FechaEntrega = ?, Estado = ? where Id = ?";
                PreparedStatement pst = con.prepareStatement(sql);

                Date d = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:ss");
                String fecha = sdf.format(d);
                pst.setString(1, fecha);
                pst.setString(2, "ENTREGADO");
                pst.setString(3, lblId.getText());

                int n = pst.executeUpdate();

                if (n > 0) {
//                    JOptionPane.showMessageDialog(this, "DATOS GUARDADOS");
                    JFrame frame = (JFrame) JOptionPane.getFrameForComponent(this);
                    verFolio v = new verFolio(frame, true, String.valueOf(folio));
                    v.setVisible(true);
                    limpiarPanelPedidos();
                    Statement st3 = con.createStatement();
                    String sql3 = "select * from pedrequisicion where Id like '" + lblId.getText() + "'";
                    ResultSet rs3 = st3.executeQuery(sql3);
                    String empleado = "";
                    while (rs3.next()) {
                        empleado = rs3.getString("NumEmpleado");
                    }

                    Statement st4 = con.createStatement();
                    String sql4 = "select * from registroempleados where NumEmpleado like '" + empleado + "'";
                    ResultSet rs4 = st4.executeQuery(sql4);
                    String telefono = "";
                    while (rs4.next()) {
                        telefono = rs4.getString("Telefono");
                    }

                    whatsappMessage whats = new whatsappMessage("521" + telefono, "TU PEDIDO *" + lblId.getText() + "* SE A ENTREGADO COMPLETO");

                    int opc;
                    if (nuevos.isSelected()) {
                        opc = 0;
                    } else {
                        opc = 1;
                    }
                    addBotonesPedido(opc);
                    limpiarArti();
                }

            } else {
                Statement st = con.createStatement();
                String sql = "update pedrequisicion set Estado = ?, FechaTerminado = ?, Almacenista = ?  where Id = ?";
                PreparedStatement pst = con.prepareStatement(sql);
                Date f = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:ss");
                String fecha = sdf.format(f);

                String estado = "";
                int contR = 0, contV = 0, contY = 0;

                for (int i = 0; i < contBtnA; i++) {
                    if (panel[i].getBackground().equals(Color.green)) {
                        contV++;
                    } else if (panel[i].getBackground().equals(Color.yellow)) {
                        contY++;
                    } else if (panel[i].getBackground().equals(Color.red)) {
                        contR++;
                    }
                }
                String mensaje = "";
                if (contV == contBtnA) {
                    estado = "ENCONTRADO, COMPLETO";
                    mensaje = "SU PEDIDO *" + lblId.getText() + "*, SE ENCONTRO COMPLETO, FAVOR DE PASAR A RECOGERLO"
                            + "A ALMACEN, GRACIAS";
                } else if (contR == contBtnA) {
                    estado = "NO ENCONTRADO";
                    mensaje = "SU PEDIDO *" + lblId.getText() + "*, NO SE ENCONTRO";
                } else {
                    estado = "ENCONTRADO, INCOMPLETO";
                    mensaje = "SU PEDIDO *" + lblId.getText() + "*, SE ENCONTRO INCOMPLETO, FAVOR DE PASAR A RECOGERLO"
                            + "A ALMACEN, GRACIAS";
                }

                pst.setString(1, estado);
                pst.setString(2, fecha);
                pst.setString(3, numEmpleado);
                pst.setString(4, lblId.getText());

                Statement st3 = con.createStatement();
                String sql3 = "select * from pedrequisicion where Id like '" + lblId.getText() + "'";
                ResultSet rs3 = st3.executeQuery(sql3);
                String empleado = "";
                while (rs3.next()) {
                    empleado = rs3.getString("NumEmpleado");
                }

                Statement st4 = con.createStatement();
                String sql4 = "select * from registroempleados where NumEmpleado like '" + empleado + "'";
                ResultSet rs4 = st4.executeQuery(sql4);
                String telefono = "";
                while (rs4.next()) {
                    telefono = rs4.getString("Telefono");
                }

                whatsappMessage whats = new whatsappMessage("521" + telefono, mensaje);

                int n = pst.executeUpdate();
                int n1 = 0;
                for (int i = 0; i < contBtnA; i++) {
                    String sql2 = "update detrequisicion set Encontrado = ?, Estado = ?, CantidadE = ? where Id = ?";
                    PreparedStatement pst2 = con.prepareStatement(sql2);
                    if (panel[i].getBackground().equals(Color.green)) {
                        pst2.setString(1, "SI");
                        pst2.setString(2, "ENCONTRADO");

                    } else if (panel[i].getBackground().equals(Color.red)) {
                        pst2.setString(1, "NO");
                        pst2.setString(2, "NO ENCONTRADO");
                    } else if (panel[i].getBackground().equals(Color.YELLOW)) {
                        pst2.setString(1, "SI");
                        pst2.setString(2, "PARCIAL");
                    }
                    pst2.setString(3, cantidadNew[i]);
                    pst2.setString(4, idAr[i]);
                    n1 = pst2.executeUpdate();
                }

                if (n > 0 && n1 > 0) {
                    JOptionPane.showMessageDialog(this, "DATOS GUARDADOS");
                    limpiarPanelPedidos();
                    int opc;
                    if (nuevos.isSelected()) {
                        opc = 0;
                    } else {
                        opc = 1;
                    }
                    addBotonesPedido(opc);
                    limpiarArti();

                } else {
                    JOptionPane.showMessageDialog(this, "DATOS NO GUARDADOS", "ERROR", JOptionPane.ERROR_MESSAGE);

                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "ERROR: " + e, "ERROR", JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(Pedidos.class.getName()).log(Level.SEVERE, null, e);
        }
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void nuevosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nuevosActionPerformed
        limpiarPanelPedidos();
        limpiarArti();
        btnGuardar.setText("GUARDAR");
        lblId.setText("SIN SELECCIONAR");
        addBotonesPedido(0);
        panelAjustar.setBackground(new Color(51, 153, 255));
    }//GEN-LAST:event_nuevosActionPerformed

    private void entregaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_entregaActionPerformed
        limpiarPanelPedidos();
        panelAjustar.setBackground(new Color(51, 153, 255));
        limpiarArti();
        lblId.setText("SIN SELECCIONAR");
        addBotonesPedido(1);
        btnGuardar.setText("ENTREGAR");
    }//GEN-LAST:event_entregaActionPerformed

    private void btnImprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImprimirActionPerformed
        panelAjustar.setBackground(new Color(51, 153, 255));
        if (lblId.getText().equals("SIN SELECCIONAR")) {
            JOptionPane.showMessageDialog(this, "DEBES SELECCIONAR UN PEDIDO", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
        } else {
            imprimir();
        }
    }//GEN-LAST:event_btnImprimirActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        if (entrega.isSelected()) {
            JOptionPane.showMessageDialog(this, "NO SE PUEDE MODIFICAR", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
            panelAjustar.setBackground(new Color(51, 153, 255));
        } else {
            if (panelAjustar.getBackground().equals(new Color(51, 153, 255))) {
                panelAjustar.setBackground(new Color(0, 102, 255));
            } else {
                panelAjustar.setBackground(new Color(51, 153, 255));
            }
            try {
                Connection con;
                Conexion con1 = new Conexion();
                con = con1.getConnection();
                Statement st = con.createStatement();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "ERROR: " + e, "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_jButton2ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private rojeru_san.RSButtonRiple btnGuardar;
    private javax.swing.JButton btnImprimir;
    private javax.swing.ButtonGroup buttonGroup1;
    public RSMaterialComponent.RSRadioButtonMaterial entrega;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
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
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblId;
    private javax.swing.JLabel lblSalir;
    public RSMaterialComponent.RSRadioButtonMaterial nuevos;
    private javax.swing.JPanel panelAjustar;
    private javax.swing.JPanel panelArti;
    private javax.swing.JPanel panelPedidos;
    private javax.swing.JPanel panelSalir;
    // End of variables declaration//GEN-END:variables

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            for (int i = 0; i < contBtnP; i++) {
                if (e.getSource() == btnPedido[i]) {
                    Connection con;
                    boolean band = false;
                    panelAjustar.setBackground(new Color(51, 153, 255));
                    Conexion con1 = new Conexion();
                    con = con1.getConnection();
                    String sql4 = "select * from pedrequisicion where Id like '" + requi[i] + "'";
                    Statement st4 = con.createStatement();
                    ResultSet rs4 = st4.executeQuery(sql4);
                    String estado = "";
                    while (rs4.next()) {
                        requisitor = rs4.getString("NumEmpleado");
                        estado = rs4.getString("Estado");
                    }
                    if (estado.equals("NUEVO")) {
                        band = true;
                    }
                    JFrame f = (JFrame) JOptionPane.getFrameForComponent(this);
                    Iniciar in = new Iniciar(f, true);
                    if (band) {
                        in.lblId.setText(requi[i]);
                        in.setVisible(true);
                    } else {
                        in.ban = true;
                    }
                    if (in.ban) {
                        limpiarArti();
                        paneles(requi[i]);
                        revalidate();
                        repaint();
                        lblId.setText(requi[i]);

                        Statement st = con.createStatement();
                        String sql = "select * from pedrequisicion where Id like '" + requi[i] + "'";
                        ResultSet rs = st.executeQuery(sql);
                        estado = null;
                        String fecha = null;
                        while (rs.next()) {
                            estado = rs.getString("Estado");
                            fecha = rs.getString("FechaVisto");
                        }

                        if (estado != null) {
                            if (estado.equals("NUEVO")) {
                                String sql2 = "update pedrequisicion set Estado = ?, FechaVisto = ? where Id  = ?";
                                PreparedStatement pst2 = con.prepareStatement(sql2);

                                Date d = new Date();
                                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:ss");
                                String fec = sdf.format(d);
                                pst2.setString(1, "BUSCANDO");
                                if (fecha != null) {
                                    if (fecha.equals("")) {
                                        pst2.setString(2, fec);
                                    } else {
                                        pst2.setString(2, fecha);
                                    }
                                }

                                pst2.setString(3, requi[i]);

                                pst2.executeUpdate();
                            }
                        }
                        limpiarPanelPedidos();
                        int opc;
                        if (nuevos.isSelected()) {
                            opc = 0;
                        } else {
                            opc = 1;
                        }
                        addBotonesPedido(opc);
                    }

                }

            }
        } catch (SQLException ex) {
            Logger.getLogger(Pedidos.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void mouseClicked(MouseEvent e) {

        for (int i = 0; i < contBtnA; i++) {
            if (e.getSource() == botones[i]) {
                if (panelAjustar.getBackground().equals(new Color(0, 102, 255))) {
                    try {
                        if (Double.parseDouble(cantidadNew[i]) <= 0) {
                            JOptionPane.showMessageDialog(this, "DEBES AÑADIR AL MENOS 1 EN LA CANTIDAD", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
                        } else {
                            Connection con;
                            Conexion con1 = new Conexion();
                            con = con1.getConnection();
                            String sql = "update detrequisicion set Cantidad = ? Estado = ? where Id = ?";
                            PreparedStatement pst = con.prepareStatement(sql);

                            pst.setString(1, cantidadNew[i]);
                            pst.setString(2, "ENCONTRADO");
                            pst.setString(3, idAr[i]);

                            int n = pst.executeUpdate();

                            if (n > 0) {
                                limpiarArti();
                                paneles(lblId.getText());
                            } else {
                                JOptionPane.showMessageDialog(this, "SIN GUARDAR", "ERROR", JOptionPane.ERROR_MESSAGE);
                            }
                        }

                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(this, "ERROR: " + ex, "ERROR", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JFrame d = (JFrame) JOptionPane.getFrameForComponent(this);
                    cantidad c = new cantidad(d, false, i, this);
                    c.setLocation(e.getLocationOnScreen());
                    c.setVisible(true);
                }
            }
        }

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        for (int i = 0; i < contBtnP; i++) {
            if (e.getSource() == btnPedido[i]) {

                switch (color[i]) {
                    case "153,204,255":
                        btnPedido[i].setText(addColor(btnPedido[i].getText(), "102,153,255"));
                        color[i] = "102,153,255";
                        break;
                    case "157,154,210":
                        btnPedido[i].setText(addColor(btnPedido[i].getText(), "157,104,255"));
                        color[i] = "157,104,255";
                        break;
                    case "255,179,255":
                        btnPedido[i].setText(addColor(btnPedido[i].getText(), "255,100,255"));
                        color[i] = "255,100,255";
                        break;
                    default:
                        break;
                }

            }
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        for (int i = 0; i < contBtnP; i++) {
            if (e.getSource() == btnPedido[i]) {
                switch (color[i]) {
                    case "102,153,255":
                        btnPedido[i].setText(addColor(btnPedido[i].getText(), "153,204,255"));
                        color[i] = "153,204,255";
                        break;
                    case "157,104,255":
                        btnPedido[i].setText(addColor(btnPedido[i].getText(), "157,154,210"));
                        color[i] = "157,154,210";
                        break;
                    case "255,100,255":
                        btnPedido[i].setText(addColor(btnPedido[i].getText(), "255,179,255"));
                        color[i] = "255,179,255";
                        break;
                    default:
                        break;
                }
            }
        }
    }

}
