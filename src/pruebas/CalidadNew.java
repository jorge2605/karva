package pruebas;

import Conexiones.Conexion;
import Modelo.Pdf;
import VO.ArchivosVO;
import VentanaEmergente.CalidadNew.Enviar;
import VentanaEmergente.CalidadNew.Errores;
import VentanaEmergente.CalidadNew.Numeros;
import VentanaEmergente.CalidadNew.Seleccionar;
import VentanaEmergente.Costos.verPdf;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Font;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import mivisorpdf.MiVisorPDF;
import java.awt.image.BufferedImage;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;

public class CalidadNew extends javax.swing.JInternalFrame implements MouseListener, ActionListener, WindowListener {

    private int numImg;
    int x, y;
    JLabel l[] = new JLabel[300];
    Lectura lectura;
    public String plan;
    String nombre, numero;
    String[][] propiedades;
    Enviar e;
    JButton[] botones;
    JButton[] botonesNumeros;
    String[] bot;
    JTable[] tablas;
    Seleccionar a;
    Numeros n;
    JPanel panel[];
    int numTabla;
    boolean band = false;

    private ArrayList<ArchivosVO> ListaComponente;
    MiVisorPDF pn = new MiVisorPDF();
    ArchivosVO pl = new ArchivosVO();

    private int paginas = -1;
    private int totalp = -1;

    private String ruta_archivo = "";

    int zoom = 0;

    public void propiedadNo3(int i) {
        String botnes[] = {"ERROR 1", "ERROR 2", "ERROR 3", "ERROR 4", "ERROR 5", "ERROR 6",
             "ERROR 7", "ERROR 8", "ERROR 9", "ERROR 10"};

        int opc = JOptionPane.showOptionDialog(null, "ELIGE ERROR", "ELEGIR", 0, JOptionPane.INFORMATION_MESSAGE,
                null, botnes, "ERROR 1");

        switch (opc) {
            case 0:
                propiedades[i][2] = "1";
                break;
            case 1:
                propiedades[i][2] = "2";
                break;
            case 2:
                propiedades[i][2] = "3";
                break;
            case 3:
                propiedades[i][2] = "4";
                break;
            case 4:
                propiedades[i][2] = "5";
                break;
            case 5:
                propiedades[i][2] = "6";
                break;
            case 6:
                propiedades[i][2] = "7";
                break;
            case 7:
                propiedades[i][2] = "8";
                break;
            case 8:
                propiedades[i][2] = "9";
                break;
            case 9:
                propiedades[i][2] = "10";
                break;
        }
    }

    public void addLabels() {
        try {
            for (int j = 0; j < Tabla1.getRowCount(); j++) {
                int ultimo = Integer.parseInt(Tabla1.getValueAt(j, 0).toString());
//                   
                l[ultimo] = new JLabel("" + ultimo);
                //        this.remove(l[ultimo]);
                int tam = 10, ty = 10;
                if (ultimo < 10) {
                    tam = 7;
                    ty = 10;
                } else if (ultimo >= 10 && ultimo < 100) {
                    tam = 14;
                    ty = 10;
                } else if (ultimo >= 100) {
                    tam = 23;
                    ty = 10;
                }
                l[ultimo].addMouseListener(this);
                l[ultimo].setFont(new java.awt.Font("Roboto", Font.BOLD, 12));
                if (Tabla1.getValueAt(j, 5).toString().equals("No")) {
                    l[ultimo].setForeground(Color.RED);
                } else {
                    l[ultimo].setForeground(Color.BLUE);
                }

                l[ultimo].setEnabled(true);
                //        l[ultimo].setBackground(new Color(255,255,255,0));
//                    Panel3.add(l[ultimo], new org.netbeans.lib.awtextra.AbsoluteConstraints(Integer.parseInt(Tabla1.getValueAt(j, 7).toString()), Integer.parseInt(Tabla1.getValueAt(j, 8).toString()), 20, 10));
                l[ultimo].setBounds(Integer.parseInt(Tabla1.getValueAt(j, 7).toString()), Integer.parseInt(Tabla1.getValueAt(j, 8).toString()), tam, ty);
            }
        } catch (Exception exx) {
            JOptionPane.showMessageDialog(this, exx);
        }
    }

    public void agregarTabla(int antes, int despues) {
        if (antes != -1) {
            DefaultTableModel modelo = (DefaultTableModel) Tabla1.getModel();
            tablas[antes].setModel(modelo);
        }
        limpiarTabla1();

        DefaultTableModel miModelo = (DefaultTableModel) tablas[despues].getModel();
        Tabla1.setModel(miModelo);
        addLabels();
    }

    public void limpiarPanel() {
        for (int i = 0; i < panel.length; i++) {
            Panel4.remove(panel[i]);
        }

        jPanel2.remove(Panel4);
        Panel4 = new javax.swing.JPanel();
        Panel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.add(Panel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 510, 640, 50));
//        removeAll();//or remove(JComponent)
        revalidate();
        repaint();
    }

    public BufferedImage captureImage(Component component) {

        BufferedImage image = new BufferedImage(650, 500, BufferedImage.TYPE_INT_RGB);
        component.paint(image.getGraphics());
        return image;

    }

    public void createPdf(String filename, String est, String pa, JTable[] tabla, String nombre) {
        Document document = new Document(PageSize.A4, 36, 36, 54, 36);
        PdfWriter writer;
        try {
            writer = PdfWriter.getInstance(document, new FileOutputStream(filename));

            Pdf encabezado = new Pdf();

            String cliente = "";
            String fecha;
            String departamento = "CALIDAD";
            String proyecto = "";
            String plano;
            String estado = "APROBADO";

            Date d = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MMMM/yyyy");
            fecha = sdf.format(d);

            plano = pa;

            try {

                Connection con = null;
                Conexion con1 = new Conexion();
                con = con1.getConnection();
                Statement st = con.createStatement();

                String sql = "select * from planos where Plano like '" + pa + "'";
                ResultSet rs = st.executeQuery(sql);
                String p = "";
                while (rs.next()) {
                    p = rs.getString("Proyecto");
                }

                Statement st1 = con.createStatement();

                String sql1 = "select * from proyectos where Proyecto like '" + p + "'";
                ResultSet rs1 = st1.executeQuery(sql1);
                while (rs1.next()) {
                    cliente = rs1.getString("Planta");
                    proyecto = rs1.getString("Proyecto");
                }

                encabezado.setEncabezado("Prueba de encabezado en iText");

                // indicamos que objecto manejara los eventos al escribir el Pdf
                writer.setPageEvent(encabezado);

                document.open();

                PdfPTable liberacion = new PdfPTable(1);
                liberacion.setWidthPercentage(70);

                com.itextpdf.text.Font fuente1 = new com.itextpdf.text.Font();
                com.itextpdf.text.Font fuente2 = new com.itextpdf.text.Font();
                com.itextpdf.text.Font fuente3 = new com.itextpdf.text.Font();
                com.itextpdf.text.Font fuente3_2 = new com.itextpdf.text.Font();
                com.itextpdf.text.Font fuente3_3 = new com.itextpdf.text.Font();
                com.itextpdf.text.Font fuente4 = new com.itextpdf.text.Font();

                fuente1.setSize(26);
                fuente1.setFamily("Roboto");
                fuente1.setColor(BaseColor.WHITE);

                fuente2.setSize(12);
                fuente2.setFamily("Roboto");
                fuente2.setColor(BaseColor.BLACK);

                fuente3.setSize(12);
                fuente3.setFamily("Roboto");
                fuente3.setColor(BaseColor.GREEN);
                fuente3.setStyle(com.itextpdf.text.Font.BOLD);

                fuente3_2.setSize(12);
                fuente3_2.setFamily("Roboto");
                fuente3_2.setColor(BaseColor.RED);
                fuente3_2.setStyle(com.itextpdf.text.Font.BOLD);

                fuente3_3.setSize(12);
                fuente3_3.setFamily("Roboto");
                fuente3_3.setColor(BaseColor.YELLOW);
                fuente3_3.setStyle(com.itextpdf.text.Font.BOLD);

                fuente4.setSize(10);
                fuente4.setFamily("Roboto");
                fuente4.setColor(BaseColor.WHITE);
                fuente4.setStyle(com.itextpdf.text.Font.BOLD);

                PdfPCell cell = new PdfPCell(new Paragraph("LIBERACION DE CALIDAD", fuente1));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setBorder(0);
                BaseColor s = new BaseColor(78, 145, 178);
                cell.setBackgroundColor(s);

                PdfPCell cel2 = new PdfPCell(new Paragraph("COTAS", fuente1));
                cel2.setHorizontalAlignment(Element.ALIGN_CENTER);
                cel2.setBorder(0);
                cel2.setBackgroundColor(s);

                PdfPTable tabla1 = new PdfPTable(2);
                tabla1.setWidthPercentage(100);

                float[] medidads = {200, 600};
                tabla1.setWidths(medidads);

                PdfPCell c1 = new PdfPCell(new Paragraph("CLIENTE:", fuente2));
                c1.setBorder(0);
                c1.setHorizontalAlignment(Element.ALIGN_LEFT);

                PdfPCell c2 = new PdfPCell(new Paragraph(cliente, fuente2));
                c2.setBorder(0);
                c2.setHorizontalAlignment(Element.ALIGN_LEFT);

                PdfPCell c3 = new PdfPCell(new Paragraph("FECHA:", fuente2));
                c3.setBorder(0);
                c3.setHorizontalAlignment(Element.ALIGN_LEFT);

                PdfPCell c4 = new PdfPCell(new Paragraph(fecha, fuente2));
                c4.setBorder(0);
                c4.setHorizontalAlignment(Element.ALIGN_LEFT);

                PdfPCell c5 = new PdfPCell(new Paragraph("DEPARTAMENTO:", fuente2));
                c5.setBorder(0);
                c5.setHorizontalAlignment(Element.ALIGN_LEFT);

                PdfPCell c6 = new PdfPCell(new Paragraph(departamento, fuente2));
                c6.setBorder(0);
                c6.setHorizontalAlignment(Element.ALIGN_LEFT);

                PdfPCell c13 = new PdfPCell(new Paragraph("TECNICO:", fuente2));
                c13.setBorder(0);
                c13.setHorizontalAlignment(Element.ALIGN_LEFT);

                PdfPCell c14 = new PdfPCell(new Paragraph(nombre, fuente2));
                c14.setBorder(0);
                c14.setHorizontalAlignment(Element.ALIGN_LEFT);

                tabla1.addCell(c1);
                tabla1.addCell(c2);
                tabla1.addCell(c3);
                tabla1.addCell(c4);
                tabla1.addCell(c5);
                tabla1.addCell(c6);
                tabla1.addCell(c13);
                tabla1.addCell(c14);

                //------------------------------------------------------
                liberacion.addCell(cell);
                document.add(liberacion);
                document.add(Chunk.NEWLINE);
                document.add(tabla1);
                //-------------------------------------------------------
                for (int m = 0; m < tabla.length; m++) {
                    PdfPTable tabla4 = new PdfPTable(7);
                    tabla4.setWidthPercentage(100);

                    PdfPTable tabla2 = new PdfPTable(2);
                    tabla2.setWidthPercentage(100);
                    tabla2.setWidths(medidads);

                    PdfPTable cotas = new PdfPTable(1);
                    cotas.setWidthPercentage(70);
                    cotas.addCell(cel2);

                    PdfPCell c7 = new PdfPCell(new Paragraph("PROYECTO:", fuente2));
                    c7.setBorder(0);
                    c7.setHorizontalAlignment(Element.ALIGN_LEFT);

                    PdfPCell c8 = new PdfPCell(new Paragraph(proyecto, fuente2));
                    c8.setBorder(0);
                    c8.setHorizontalAlignment(Element.ALIGN_LEFT);

                    PdfPCell c9 = new PdfPCell(new Paragraph("PLANO:", fuente2));
                    c9.setBorder(0);
                    c9.setHorizontalAlignment(Element.ALIGN_LEFT);

                    PdfPCell c10 = new PdfPCell(new Paragraph(plano, fuente2));
                    c10.setBorder(0);
                    c10.setHorizontalAlignment(Element.ALIGN_LEFT);

                    PdfPCell c11 = new PdfPCell(new Paragraph("ESTADO:", fuente2));
                    c11.setBorder(0);
                    c11.setHorizontalAlignment(Element.ALIGN_LEFT);

                    PdfPCell c12;

                    if (propiedades[m][0].equals("rojo")) {
                        estado = "RECHAZADO";
                    } else if (propiedades[m][0].equals("amarillo")) {
                        estado = "RETRABAJO";
                    } else {
                        estado = "ACEPTADO";
                    }
                    if (estado.equals("ACEPTADO")) {
                        c12 = new PdfPCell(new Paragraph(estado, fuente3));
                    } else if (estado.equals("RECHAZADO")) {
                        c12 = new PdfPCell(new Paragraph(estado, fuente3_2));
                    } else {
                        c12 = new PdfPCell(new Paragraph(estado, fuente3_3));
                    }

                    c12.setBorder(0);
                    c12.setHorizontalAlignment(Element.ALIGN_LEFT);

                    PdfPCell c15 = new PdfPCell(new Paragraph("NUMERO DE PIEZA:", fuente2));
                    c15.setBorder(0);
                    c15.setHorizontalAlignment(Element.ALIGN_LEFT);

                    PdfPCell c16 = new PdfPCell(new Paragraph(botonesNumeros[m].getText(), fuente2));
                    c16.setBorder(0);
                    c16.setHorizontalAlignment(Element.ALIGN_LEFT);

                    PdfPCell col1 = new PdfPCell(new Paragraph("PUNTO", fuente4));
                    col1.setBorder(0);
                    col1.setBackgroundColor(s);
                    PdfPCell col2 = new PdfPCell(new Paragraph("NOMINAL", fuente4));
                    col2.setBorder(0);
                    col2.setBackgroundColor(s);
                    PdfPCell col3 = new PdfPCell(new Paragraph("L.B", fuente4));
                    col3.setBorder(0);
                    col3.setBackgroundColor(s);
                    PdfPCell col4 = new PdfPCell(new Paragraph("L.A", fuente4));
                    col4.setBorder(0);
                    col4.setBackgroundColor(s);
                    PdfPCell col5 = new PdfPCell(new Paragraph("REAL", fuente4));
                    col5.setBorder(0);
                    col5.setBackgroundColor(s);
                    PdfPCell col6 = new PdfPCell(new Paragraph("APROBADO", fuente4));
                    col6.setBorder(0);
                    col6.setBackgroundColor(s);
                    PdfPCell col7 = new PdfPCell(new Paragraph("OBSERVACIONES", fuente4));
                    col7.setBorder(0);
                    col7.setBackgroundColor(s);
                    float[] medi = {50, 70, 50, 50, 50, 80, 300};
                    tabla4.setWidths(medi);

                    for (int i = 0; i < 7; i++) {
                        PdfPCell vacio = new PdfPCell(new Paragraph("\n"));
                        vacio.setBorder(0);
                        tabla4.addCell(vacio);
                    }
                    tabla4.addCell(col1);
                    tabla4.addCell(col2);
                    tabla4.addCell(col3);
                    tabla4.addCell(col4);
                    tabla4.addCell(col5);
                    tabla4.addCell(col6);
                    tabla4.addCell(col7);
                    PdfPCell cc1;

                    int aux = -1;

                    agregarTabla(aux, m);
                    aux++;
                    int articulo = 0;
                    for (int k = 0; k < tabla[m].getRowCount(); k++) {
                        for (int j = 0; j < 7; j++) {
                            cc1 = new PdfPCell(new Phrase(tabla[m].getValueAt(k, j).toString(), fuente2));
                            cc1.setBorder(Rectangle.BOTTOM);
                            cc1.setBorderColor(s);
                            tabla4.addCell(cc1);
                        }
                    }

                    //-------------------------------------------------------
                    BufferedImage img = captureImage(this.getContentPane());

                    try {
                        ImageIO.write(img, "jpg", new File("C:\\Pruebas\\BD\\img\\ss.jpg"));
                    } catch (IOException ex) {
                        Logger.getLogger(CalidadNew.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    Image img3 = Image.getInstance("C:\\Pruebas\\BD\\img\\ss.jpg");
                    if (m == 0) {
                        img3.setAbsolutePosition(0, 80);
                    } else {
                        img3.setAbsolutePosition(0, 180);
                    }

                    img3.scaleAbsoluteWidth(600);
                    tabla2.addCell(c7);
                    tabla2.addCell(c8);
                    tabla2.addCell(c9);
                    tabla2.addCell(c10);
                    tabla2.addCell(c11);
                    tabla2.addCell(c12);
                    tabla2.addCell(c15);
                    tabla2.addCell(c16);

                    document.add(tabla2);
                    document.add(img3);
                    document.newPage();
                    document.add(cotas);
                    document.add(Chunk.NEWLINE);
                    document.add(tabla4);
                    document.newPage();

//        limpiarTablas(tabla[m]); 
                }
                document.close();

            } catch (FileNotFoundException ex) {
                Logger.getLogger(CalidadNew.class.getName()).log(Level.SEVERE, null, ex);
            } catch (DocumentException ex) {
                Logger.getLogger(CalidadNew.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(CalidadNew.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "ERROR: " + e);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(CalidadNew.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DocumentException ex) {
            Logger.getLogger(CalidadNew.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void limpiarTablas(JTable tabla) {
        tabla.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N

        tabla.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{},
                new String[]{
                    "Punto", "Nominal", "Limite alto", "Limite bajo", "Real", "Aprobado", "Observaciones", "X", "Y"
                }
        ) {
            boolean[] canEdit = new boolean[]{
                false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });

        tabla.setRowHeight(25);

        tabla.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Tabla1MouseClicked(evt);
            }
        });

        jScrollPane2.setViewportView(tabla);

        if (tabla.getColumnModel().getColumnCount() > 0) {
            tabla.getColumnModel().getColumn(0).setMinWidth(60);
            tabla.getColumnModel().getColumn(0).setPreferredWidth(60);
            tabla.getColumnModel().getColumn(0).setMaxWidth(60);
            tabla.getColumnModel().getColumn(1).setMinWidth(60);
            tabla.getColumnModel().getColumn(1).setPreferredWidth(60);
            tabla.getColumnModel().getColumn(1).setMaxWidth(60);
            tabla.getColumnModel().getColumn(2).setMinWidth(60);
            tabla.getColumnModel().getColumn(2).setPreferredWidth(60);
            tabla.getColumnModel().getColumn(2).setMaxWidth(60);
            tabla.getColumnModel().getColumn(3).setMinWidth(60);
            tabla.getColumnModel().getColumn(3).setPreferredWidth(60);
            tabla.getColumnModel().getColumn(3).setMaxWidth(60);
            tabla.getColumnModel().getColumn(4).setMinWidth(60);
            tabla.getColumnModel().getColumn(4).setPreferredWidth(60);
            tabla.getColumnModel().getColumn(4).setMaxWidth(60);
            tabla.getColumnModel().getColumn(5).setMinWidth(60);
            tabla.getColumnModel().getColumn(5).setPreferredWidth(60);
            tabla.getColumnModel().getColumn(5).setMaxWidth(60);
            tabla.getColumnModel().getColumn(7).setMinWidth(5);
            tabla.getColumnModel().getColumn(7).setPreferredWidth(5);
            tabla.getColumnModel().getColumn(7).setMaxWidth(5);
            tabla.getColumnModel().getColumn(8).setMinWidth(5);
            tabla.getColumnModel().getColumn(8).setPreferredWidth(5);
            tabla.getColumnModel().getColumn(8).setMaxWidth(5);
        }
    }

    public void limpiarTabla1() {
        Tabla1.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N

        Tabla1.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]
                {{"","","","","","","","",""}},
                new String[]{
                    "Punto", "Nominal", "Limite alto", "Limite bajo", "Real", "Aprobado", "Observaciones", "X", "Y"
                }
        ) {
            boolean[] canEdit = new boolean[]{
                false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });

        Tabla1.setRowHeight(25);

        Tabla1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Tabla1MouseClicked(evt);
            }
        });

        jScrollPane2.setViewportView(Tabla1);

        if (Tabla1.getColumnModel().getColumnCount() > 0) {
            Tabla1.getColumnModel().getColumn(0).setMinWidth(60);
            Tabla1.getColumnModel().getColumn(0).setPreferredWidth(60);
            Tabla1.getColumnModel().getColumn(0).setMaxWidth(60);
            Tabla1.getColumnModel().getColumn(1).setMinWidth(60);
            Tabla1.getColumnModel().getColumn(1).setPreferredWidth(60);
            Tabla1.getColumnModel().getColumn(1).setMaxWidth(60);
            Tabla1.getColumnModel().getColumn(2).setMinWidth(60);
            Tabla1.getColumnModel().getColumn(2).setPreferredWidth(60);
            Tabla1.getColumnModel().getColumn(2).setMaxWidth(60);
            Tabla1.getColumnModel().getColumn(3).setMinWidth(60);
            Tabla1.getColumnModel().getColumn(3).setPreferredWidth(60);
            Tabla1.getColumnModel().getColumn(3).setMaxWidth(60);
            Tabla1.getColumnModel().getColumn(4).setMinWidth(60);
            Tabla1.getColumnModel().getColumn(4).setPreferredWidth(60);
            Tabla1.getColumnModel().getColumn(4).setMaxWidth(60);
            Tabla1.getColumnModel().getColumn(5).setMinWidth(60);
            Tabla1.getColumnModel().getColumn(5).setPreferredWidth(60);
            Tabla1.getColumnModel().getColumn(5).setMaxWidth(60);
            Tabla1.getColumnModel().getColumn(7).setMinWidth(5);
            Tabla1.getColumnModel().getColumn(7).setPreferredWidth(5);
            Tabla1.getColumnModel().getColumn(7).setMaxWidth(5);
            Tabla1.getColumnModel().getColumn(8).setMinWidth(5);
            Tabla1.getColumnModel().getColumn(8).setPreferredWidth(5);
            Tabla1.getColumnModel().getColumn(8).setMaxWidth(5);
        }

    }

    public void abrir_pdf(String url) {
//        this.numImg = 0;
//        ruta_archivo = url;
//        this.ListaComponente = pn.leerPDF(url);
//        for (int i = 0; i < ListaComponente.size(); i++) {
//            pl = ListaComponente.get(i);;
//            this.img.setImagen(pl.getArchivos());
//        }
//        paginas = 1;
//        p.setText(String.valueOf("Pag. " + paginas));
//        totalp = ListaComponente.size();
//        ArchivosVO pi = new ArchivosVO();
//        pi = ListaComponente.get(0);
//        this.img.setImagen(pi.getArchivos());

        verPdf ver = new verPdf();
        try {
            ver.setPdf(url);
            ver.setLocationRelativeTo(this);
            ver.setVisible(true);
        } catch (IOException ex) {
            Logger.getLogger(CalidadNew.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void guardarBD(String estado) {
        try {
            createPdf("C:\\Pruebas\\documento.pdf", estado, this.plan, tablas, this.nombre);

            Connection con = null;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            String sql = "insert into calidadpdf (Plano, Fecha, Pdf, Estado) values(?,?,?,?)";
            PreparedStatement pst = con.prepareStatement(sql);

            String sql1 = "insert into calidadcotas (Punto, Nominal, LimiteBajo, LimiteAlto, Rea, Aprobado, Observaciones, Fecha, X, Y,CalidadPdf) values (?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement pst1 = con.prepareStatement(sql1);

            Statement st = con.createStatement();
            String sql2 = "select * from calidadpdf";
            ResultSet rs = st.executeQuery(sql2);
            String ul = "";

            byte[] pe = null;
            File archivo = new File("C:\\Pruebas\\documento.pdf");
            pe = new byte[(int) archivo.length()];
            try {

                InputStream input = new FileInputStream(archivo);
                input.read(pe);
            } catch (IOException e) {
            }

            Date d = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MMMM/yyyy");
            String fecha = sdf.format(d);

            pst.setString(1, plan);
            pst.setString(2, fecha);
            pst.setBytes(3, pe);
            pst.setString(4, estado);

            int n = pst.executeUpdate();

            while (rs.next()) {
                ul = rs.getString("Id");
            }

            int a = (Integer.parseInt(ul)) + 1;

            int n1 = 0;
            /*Punto, Nominal, LimiteBajo, LimiteAlto, Real, Aprobado, Observaciones, Fecha, X, Y*/
            for (int i = 0; i < tablas[0].getRowCount(); i++) {
                pst1.setString(1, tablas[0].getValueAt(i, 0).toString());
                pst1.setString(2, tablas[0].getValueAt(i, 1).toString());
                pst1.setString(3, tablas[0].getValueAt(i, 2).toString());
                pst1.setString(4, tablas[0].getValueAt(i, 3).toString());
                pst1.setString(5, tablas[0].getValueAt(i, 4).toString());
                pst1.setString(6, tablas[0].getValueAt(i, 5).toString());
                pst1.setString(7, tablas[0].getValueAt(i, 6).toString());
                pst1.setString(8, fecha);
                pst1.setString(9, tablas[0].getValueAt(i, 7).toString());
                pst1.setString(10, tablas[0].getValueAt(i, 8).toString());
                pst1.setString(11, "" + a);

                n1 = pst1.executeUpdate();
            }

            if (n > 0 && n1 > 0) {
                JOptionPane.showMessageDialog(this, "DATOS GUARDADOS");
            }

        } catch (SQLException a) {
            JOptionPane.showMessageDialog(this, "ERROR: " + a, "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    public CalidadNew(String nombre, String numero) {
        initComponents();
        this.nombre = nombre;
        this.numero = numero;
        ((javax.swing.plaf.basic.BasicInternalFrameUI) this.getUI()).setNorthPane(null);

        Tabla1.getTableHeader().setFont(new java.awt.Font("Roboto", java.awt.Font.PLAIN, 14));
        Tabla1.getTableHeader().setOpaque(false);
        Tabla1.getTableHeader().setBackground(new Color(0, 0, 0, 0));
        Tabla1.getTableHeader().setForeground(Color.black);
        Tabla1.setRowHeight(25);
        Tabla1.setShowGrid(false);

        btnAprobado.addActionListener(this);

//        JPanel mi = new JPanel();
//        jPanel2.add(mi, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 510, 100, 100));
//        jPanel2.remove(Panel4);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        Tabla1 = new javax.swing.JTable();
        btnSalir = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        btnRechazo = new javax.swing.JButton();
        btnAprobado = new javax.swing.JButton();
        lblPlano = new javax.swing.JLabel();
        txtBajo = new javax.swing.JFormattedTextField();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel3 = new javax.swing.JLabel();
        txtAlto = new javax.swing.JFormattedTextField();
        jSeparator3 = new javax.swing.JSeparator();
        jLabel6 = new javax.swing.JLabel();
        lblRevision = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        lblS = new javax.swing.JLabel();
        Panel4 = new javax.swing.JPanel();
        btnTerminar = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        panelEditar = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        panelEditar1 = new javax.swing.JPanel();
        btnEditar1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        txtPlano = new RSMaterialComponent.RSTextFieldMaterial();

        setBorder(null);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jScrollPane2.setToolTipText("");

        Tabla1.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        Tabla1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Punto", "Nominal", "Limite alto", "Limite bajo", "Real", "Aprobado", "Observaciones", "X", "Y"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        Tabla1.setRowHeight(25);
        Tabla1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Tabla1MouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(Tabla1);
        if (Tabla1.getColumnModel().getColumnCount() > 0) {
            Tabla1.getColumnModel().getColumn(0).setMinWidth(60);
            Tabla1.getColumnModel().getColumn(0).setPreferredWidth(60);
            Tabla1.getColumnModel().getColumn(0).setMaxWidth(60);
            Tabla1.getColumnModel().getColumn(1).setMinWidth(60);
            Tabla1.getColumnModel().getColumn(1).setPreferredWidth(60);
            Tabla1.getColumnModel().getColumn(1).setMaxWidth(60);
            Tabla1.getColumnModel().getColumn(2).setMinWidth(60);
            Tabla1.getColumnModel().getColumn(2).setPreferredWidth(60);
            Tabla1.getColumnModel().getColumn(2).setMaxWidth(60);
            Tabla1.getColumnModel().getColumn(3).setMinWidth(60);
            Tabla1.getColumnModel().getColumn(3).setPreferredWidth(60);
            Tabla1.getColumnModel().getColumn(3).setMaxWidth(60);
            Tabla1.getColumnModel().getColumn(4).setMinWidth(60);
            Tabla1.getColumnModel().getColumn(4).setPreferredWidth(60);
            Tabla1.getColumnModel().getColumn(4).setMaxWidth(60);
            Tabla1.getColumnModel().getColumn(5).setMinWidth(60);
            Tabla1.getColumnModel().getColumn(5).setPreferredWidth(60);
            Tabla1.getColumnModel().getColumn(5).setMaxWidth(60);
            Tabla1.getColumnModel().getColumn(7).setMinWidth(5);
            Tabla1.getColumnModel().getColumn(7).setPreferredWidth(5);
            Tabla1.getColumnModel().getColumn(7).setMaxWidth(5);
            Tabla1.getColumnModel().getColumn(8).setMinWidth(5);
            Tabla1.getColumnModel().getColumn(8).setPreferredWidth(5);
            Tabla1.getColumnModel().getColumn(8).setMaxWidth(5);
        }

        jPanel2.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 100, 710, 500));

        btnSalir.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Roboto", 0, 36)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("X");
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

        javax.swing.GroupLayout btnSalirLayout = new javax.swing.GroupLayout(btnSalir);
        btnSalir.setLayout(btnSalirLayout);
        btnSalirLayout.setHorizontalGroup(
            btnSalirLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, btnSalirLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        btnSalirLayout.setVerticalGroup(
            btnSalirLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, btnSalirLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel2.add(btnSalir, new org.netbeans.lib.awtextra.AbsoluteConstraints(1320, 20, 50, 50));

        btnRechazo.setBackground(new java.awt.Color(255, 255, 255));
        btnRechazo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ImgAnimacion/izquierda_32.png"))); // NOI18N
        btnRechazo.setBorder(null);
        btnRechazo.setBorderPainted(false);
        btnRechazo.setContentAreaFilled(false);
        btnRechazo.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnRechazo.setFocusPainted(false);
        btnRechazo.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnRechazo.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/ImgAnimacion/izquierda_32.png"))); // NOI18N
        btnRechazo.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/ImgAnimacion/izquierda_48.png"))); // NOI18N
        btnRechazo.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        btnRechazo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRechazoActionPerformed(evt);
            }
        });
        jPanel2.add(btnRechazo, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 620, 52, 52));

        btnAprobado.setBackground(new java.awt.Color(255, 255, 255));
        btnAprobado.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ImgAnimacion/derecha_32.png"))); // NOI18N
        btnAprobado.setBorder(null);
        btnAprobado.setBorderPainted(false);
        btnAprobado.setContentAreaFilled(false);
        btnAprobado.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnAprobado.setFocusPainted(false);
        btnAprobado.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnAprobado.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/ImgAnimacion/derecha_32.png"))); // NOI18N
        btnAprobado.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/ImgAnimacion/derecha_48.png"))); // NOI18N
        btnAprobado.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        btnAprobado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAprobadoActionPerformed(evt);
            }
        });
        jPanel2.add(btnAprobado, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 620, 52, 52));

        lblPlano.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblPlano.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jPanel2.add(lblPlano, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 80, 270, 20));

        txtBajo.setBackground(new java.awt.Color(255, 255, 255));
        txtBajo.setBorder(null);
        txtBajo.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("0.###"))));
        txtBajo.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        txtBajo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtBajoKeyTyped(evt);
            }
        });
        jPanel2.add(txtBajo, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 40, 70, 20));
        jPanel2.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 60, 70, 10));

        jLabel3.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        jLabel3.setText("Limite alto:");
        jPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(1040, 20, -1, -1));

        txtAlto.setBackground(new java.awt.Color(255, 255, 255));
        txtAlto.setBorder(null);
        txtAlto.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("0.###"))));
        txtAlto.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        txtAlto.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtAltoFocusLost(evt);
            }
        });
        txtAlto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtAltoKeyTyped(evt);
            }
        });
        jPanel2.add(txtAlto, new org.netbeans.lib.awtextra.AbsoluteConstraints(1040, 40, 70, 20));
        jPanel2.add(jSeparator3, new org.netbeans.lib.awtextra.AbsoluteConstraints(1040, 60, 70, 10));

        jLabel6.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        jLabel6.setText("Limite bajo:");
        jPanel2.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 20, -1, -1));

        lblRevision.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        jPanel2.add(lblRevision, new org.netbeans.lib.awtextra.AbsoluteConstraints(1200, 80, 70, 20));

        jLabel8.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        jLabel8.setText("Numero de plano:");
        jPanel2.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 80, -1, 20));

        lblS.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblS.setText("Pieza No:");
        jPanel2.add(lblS, new org.netbeans.lib.awtextra.AbsoluteConstraints(1130, 80, -1, 20));

        Panel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.add(Panel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 510, 640, 50));

        btnTerminar.setBackground(new java.awt.Color(14, 199, 0));

        jLabel2.setBackground(new java.awt.Color(255, 255, 255));
        jLabel2.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("TERMINAR");
        jLabel2.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jLabel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel2MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel2MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel2MouseExited(evt);
            }
        });

        javax.swing.GroupLayout btnTerminarLayout = new javax.swing.GroupLayout(btnTerminar);
        btnTerminar.setLayout(btnTerminarLayout);
        btnTerminarLayout.setHorizontalGroup(
            btnTerminarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, btnTerminarLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        btnTerminarLayout.setVerticalGroup(
            btnTerminarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, btnTerminarLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel2.add(btnTerminar, new org.netbeans.lib.awtextra.AbsoluteConstraints(1240, 630, 100, 30));

        panelEditar.setBackground(new java.awt.Color(255, 255, 255));

        jButton1.setBackground(new java.awt.Color(255, 255, 255));
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/mesa-de-edicion.png"))); // NOI18N
        jButton1.setToolTipText("Editar tabla completa");
        jButton1.setBorder(null);
        jButton1.setBorderPainted(false);
        jButton1.setContentAreaFilled(false);
        jButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jButton1.setFocusPainted(false);
        jButton1.setPreferredSize(new java.awt.Dimension(20, 20));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelEditarLayout = new javax.swing.GroupLayout(panelEditar);
        panelEditar.setLayout(panelEditarLayout);
        panelEditarLayout.setHorizontalGroup(
            panelEditarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        panelEditarLayout.setVerticalGroup(
            panelEditarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel2.add(panelEditar, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 620, 20, 20));

        btnEliminar.setBackground(new java.awt.Color(255, 255, 255));
        btnEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/eliminar (1).png"))); // NOI18N
        btnEliminar.setToolTipText("Eliminar");
        btnEliminar.setBorder(null);
        btnEliminar.setBorderPainted(false);
        btnEliminar.setContentAreaFilled(false);
        btnEliminar.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnEliminar.setEnabled(false);
        btnEliminar.setFocusPainted(false);
        btnEliminar.setPreferredSize(new java.awt.Dimension(20, 20));
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });
        jPanel2.add(btnEliminar, new org.netbeans.lib.awtextra.AbsoluteConstraints(910, 620, -1, -1));

        panelEditar1.setBackground(new java.awt.Color(255, 255, 255));

        btnEditar1.setBackground(new java.awt.Color(255, 255, 255));
        btnEditar1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/mesa-de-edicion (1).png"))); // NOI18N
        btnEditar1.setToolTipText("Editar seleccionado");
        btnEditar1.setBorder(null);
        btnEditar1.setBorderPainted(false);
        btnEditar1.setContentAreaFilled(false);
        btnEditar1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnEditar1.setEnabled(false);
        btnEditar1.setFocusPainted(false);
        btnEditar1.setPreferredSize(new java.awt.Dimension(20, 20));
        btnEditar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditar1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelEditar1Layout = new javax.swing.GroupLayout(panelEditar1);
        panelEditar1.setLayout(panelEditar1Layout);
        panelEditar1Layout.setHorizontalGroup(
            panelEditar1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelEditar1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(btnEditar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        panelEditar1Layout.setVerticalGroup(
            panelEditar1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelEditar1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(btnEditar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel2.add(panelEditar1, new org.netbeans.lib.awtextra.AbsoluteConstraints(950, 620, 20, 20));

        jButton2.setText("jButton2");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(1020, 660, -1, -1));

        txtPlano.setForeground(new java.awt.Color(51, 51, 51));
        txtPlano.setColorMaterial(new java.awt.Color(153, 204, 255));
        txtPlano.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        txtPlano.setPhColor(new java.awt.Color(51, 153, 255));
        txtPlano.setPlaceholder("Numero de plano");
        txtPlano.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPlanoActionPerformed(evt);
            }
        });
        jPanel2.add(txtPlano, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 620, 290, 30));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(-4, -3, 1400, 700));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseClicked
        dispose();
    }//GEN-LAST:event_jLabel1MouseClicked

    private void jLabel1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseEntered
        btnSalir.setBackground(Color.red);
    }//GEN-LAST:event_jLabel1MouseEntered

    private void jLabel1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseExited
        btnSalir.setBackground(Color.white);
    }//GEN-LAST:event_jLabel1MouseExited

    private void Tabla1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Tabla1MouseClicked
        btnEliminar.setEnabled(true);
        btnEditar1.setEnabled(true);
    }//GEN-LAST:event_Tabla1MouseClicked

    private void btnRechazoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRechazoActionPerformed
        boolean ban = false;
        if (ruta_archivo.trim().length() != 0) {
            for (int i = 0; i < panel.length; i++) {
                if (panel[i].getBackground().equals(Color.gray)) {
                    ban = true;
                }
            }
            if (ban == false) {
                JOptionPane.showMessageDialog(this, "DEBES SELECCIONAR UN PLANO");
            } else {
                JFrame j = (JFrame) JOptionPane.getFrameForComponent(this);
                e = new Enviar(j, true);
                e.btnFresa.addActionListener(this);
                e.btnCnc.addActionListener(this);
                e.btnTorno.addActionListener(this);
                e.btnAcabados.addActionListener(this);
                e.btnCortes.addActionListener(this);
                e.btnTrata.addActionListener(this);

                JFrame f = (JFrame) JOptionPane.getFrameForComponent(this);

                e.setVisible(true);
                btnEliminar.setEnabled(false);
                btnEditar1.setEnabled(false);
            }
        } else {
            JOptionPane.showMessageDialog(this, "PRIMERO DEBES SELECCIONAR UN PLANO");

        }
    }//GEN-LAST:event_btnRechazoActionPerformed

    private void btnAprobadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAprobadoActionPerformed
        if (ruta_archivo.trim().length() != 0) {
            for (int i = 0; i < panel.length; i++) {
                if (panel[i].getBackground().equals(Color.gray)) {
                    agregarTabla(numTabla, numTabla);
                    panel[i].setBackground(Color.green);
                    propiedades[i][0] = "verde";
                    propiedades[i][1] = "liberado";
                    lblRevision.setText("");
                    numTabla = -1;

                    limpiarTabla1();

                }
            }
            btnEliminar.setEnabled(false);
            btnEditar1.setEnabled(false);
        } else {
            JOptionPane.showMessageDialog(this, "PRIMERO DEBES SELECCIONAR UN PLANO");
        }
    }//GEN-LAST:event_btnAprobadoActionPerformed

    private void txtBajoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBajoKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtBajoKeyTyped

    private void txtAltoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtAltoFocusLost

    }//GEN-LAST:event_txtAltoFocusLost

    private void txtAltoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAltoKeyTyped
        //        char c = evt.getKeyChar();
        //        if(c == '.' || c<'0'||c>'9') evt.consume();
    }//GEN-LAST:event_txtAltoKeyTyped

    private void jLabel2MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseEntered
        btnTerminar.setBackground(new Color(11, 155, 0));
    }//GEN-LAST:event_jLabel2MouseEntered

    private void jLabel2MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseExited
        btnTerminar.setBackground(new Color(14, 199, 0));
    }//GEN-LAST:event_jLabel2MouseExited

    private void jLabel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseClicked
        boolean band = true;
        int cont = 0;

        btnEliminar.setEnabled(false);
        btnEditar1.setEnabled(false);

        for (int i = 0; i < panel.length; i++) {
            if (panel[i].getBackground().equals(Color.gray)) {
                agregarTabla(numTabla, numTabla);
            }
        }

        if (tablas != null) {
            for (int i = 0; i < tablas.length; i++) {
                if (tablas[i].getRowCount() == 0) {
                    band = false;
                    cont++;
                }
            }

            if (band == false) {
                JOptionPane.showMessageDialog(this, "HAY " + cont + " TABLAS VACIAS, FAVOR DE LLENARLAS", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
            } else {
                guardarBD("");
                if (plan.equals("")) {
                    JOptionPane.showMessageDialog(this, "DEBES SELECCIONAR PLANO", "ERROR", JOptionPane.ERROR_MESSAGE);
                } else {
                    try {
                        for (int j = 0; j < panel.length; j++) {
                            Date na = new Date();
                            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                            String fec = sdf.format(na);
                            Connection con = null;
                            Conexion con1 = new Conexion();
                            con = con1.getConnection();
                            Statement st = con.createStatement();
                            Statement st1 = con.createStatement();
                            Statement st2 = con.createStatement();
                            Statement st3 = con.createStatement();
                            Statement st4 = con.createStatement();
                            Statement st5 = con.createStatement();
                            Statement st6 = con.createStatement();
                            String sql = "UPDATE Calidad SET Proyecto = ?, Plano = ?, FechaInicio = ?, FechaFinal = ?, Terminado = ?, Estado = ?, Tratamiento = ?, Cronometro = ?, Prioridad = ?,Empleado = ? WHERE Proyecto = ?";
                            String sq8 = "UPDATE Calidad SET Terminado = ?, Tratamiento = ? WHERE Proyecto = ?";
                            String sql1 = "insert into CNC (Proyecto,Plano,FechaInicio,FechaFinal,Terminado,Estado,Cronometro,Prioridad) values(?,?,?,?,?,?,?,?)";
                            String sql2 = "insert into Fresadora (Proyecto,Plano,FechaInicio,FechaFinal,Terminado,Estado,Cronometro,Prioridad) values(?,?,?,?,?,?,?,?)";
                            String sql4 = "insert into Torno (Proyecto,Plano,FechaInicio,FechaFinal,Terminado,Estado,Cronometro,Prioridad) values(?,?,?,?,?,?,?,?)";
                            String sql5 = "insert into Scrap (Proyecto,NumeroEmpleado,Fecha) values (?,?,?)";
                            String sql6 = "insert into Acabados (Proyecto,Plano,FechaInicio,FechaFinal,Terminado,Estado,Cronometro,Prioridad) values(?,?,?,?,?,?,?,?)";
                            String sql7 = "insert into Trata (Proyecto,Plano,FechaInicio,FechaFinal,Terminado,Estado,Prioridad) values(?,?,?,?,?,?,?)";
                            String sql8 = "insert into Calidad (Proyecto, Plano, FechaInicio, FechaFinal, Terminado, Estado, Tratamiento,Cronometro, Prioridad, Empleado) values(?,?,?,?,?,?,?,?,?,?)";
                            PreparedStatement pst = con.prepareStatement(sql);
                            PreparedStatement pst1 = con.prepareStatement(sql1);
                            PreparedStatement pst2 = con.prepareStatement(sql2);
                            PreparedStatement pst4 = con.prepareStatement(sql4);
                            PreparedStatement pst5 = con.prepareStatement(sql5);
                            PreparedStatement pst6 = con.prepareStatement(sql6);
                            PreparedStatement pst7 = con.prepareStatement(sql7);
                            PreparedStatement pst8 = con.prepareStatement(sql8);
                            PreparedStatement ps8 = con.prepareStatement(sq8);

                            String borrarTorno = "delete from Calidad where Proyecto = ?";
                            String borrarCnc = "delete from CNC where Proyecto = ?";
                            String borrarFresa = "delete from Fresadora where Proyecto = ?";
                            String borrarAcabados = "delete from Torno where Proyecto = ?";
                            String borrarCortes = "UPDATE Datos SET Proyecto = ?, Plano = ?, FechaInicio = ?, FechaFinal = ?, Terminado = ?, Estado = ?,Cronometro = ?, Prioridad = ? WHERE Proyecto = ?";
                            String borrarCalidad = "delete from Acabados where Proyecto = ?";
                            String borrarTratamiento = "delete from Trata where Proyecto = ?";

                            PreparedStatement ps1 = con.prepareStatement(borrarTorno);
                            PreparedStatement ps2 = con.prepareStatement(borrarCnc);
                            PreparedStatement ps3 = con.prepareStatement(borrarFresa);
                            PreparedStatement ps4 = con.prepareStatement(borrarAcabados);
                            PreparedStatement ps5 = con.prepareStatement(borrarCortes);
                            PreparedStatement ps6 = con.prepareStatement(borrarCalidad);
                            PreparedStatement ps7 = con.prepareStatement(borrarTratamiento);

                            String ver = "select * from CNC where Proyecto like '" + plan + "'";
                            String ver1 = "select * from Fresadora where Proyecto like '" + plan + "'";
                            String ver2 = "select * from Torno where Proyecto like '" + plan + "'";
                            String ver3 = "select * from Acabados where Proyecto like '" + plan + "'";
                            String ver4 = "select * from Trata where Proyecto like '" + plan + "'";

                            ResultSet rs = st.executeQuery(ver);
                            ResultSet rs1 = st1.executeQuery(ver1);
                            ResultSet rs2 = st2.executeQuery(ver2);
                            ResultSet rs3 = st3.executeQuery(ver3);
                            ResultSet rs4 = st4.executeQuery(ver4);

                            String ac = "update CNC set Proyecto = ?, Plano = ?, FechaInicio = ?, FechaFinal = ?, Terminado = ?, Estado = ?, Cronometro = ?,Prioridad = ? where Proyecto = ?";
                            String ac1 = "update Fresadora set Proyecto = ?, Plano = ?, FechaInicio = ?, FechaFinal = ?, Terminado = ?, Estado = ?, Cronometro = ?,Prioridad = ? where Proyecto = ?";
                            String ac2 = "update Torno set Proyecto = ?, Plano = ?, FechaInicio = ?, FechaFinal = ?, Terminado = ?, Estado = ?, Cronometro = ?,Prioridad = ? where Proyecto = ?";
                            String ac3 = "update Acabados set Proyecto = ?, Plano = ?, FechaInicio = ?, FechaFinal = ?, Terminado = ?, Estado = ?, Cronometro = ?,Prioridad = ? where Proyecto = ?";
                            String ac4 = "update Trata set Proyecto = ?, Plano = ?, FechaInicio = ?, FechaFinal = ?, Terminado = ?, Estado = ?,Prioridad = ? where Proyecto = ?";

                            PreparedStatement act = con.prepareStatement(ac);
                            PreparedStatement act1 = con.prepareStatement(ac1);
                            PreparedStatement act2 = con.prepareStatement(ac2);
                            PreparedStatement act3 = con.prepareStatement(ac3);
                            PreparedStatement act4 = con.prepareStatement(ac4);
                            String eCalidad = "select * from Calidad where Proyecto like '" + plan + "'";
                            String eCortes = "select * from Datos where Proyecto like '" + plan + "'";

                            String planos = "select * from Planos where Plano like '" + plan + "'";
                            Statement st7 = con.prepareCall(planos);
                            ResultSet plano = st7.executeQuery(planos);

                            ResultSet eC = st5.executeQuery(eCalidad);
                            ResultSet eCo = st6.executeQuery(eCortes);
                            String acabados[] = new String[10];
                            String cnc[] = new String[10];
                            String fresa[] = new String[10];
                            String torno[] = new String[10];
                            String calidad[] = new String[10];
                            String trata[] = new String[10];
                            String cortes[] = new String[10];
                            String pl[] = new String[10];

                            //Proyecto, Plano, FechaInicio, FechaFinal, Terminado, Estado, Tratamiento,Cronometro, Prioridad, Empleado
                            while (plano.next()) {
                                pl[0] = plano.getString("Plano");
                                pl[1] = plano.getString("Proyecto");
                                pl[2] = ("");
                                pl[3] = (fec);
                                pl[4] = ("SI");
                                pl[5] = ("");
                                pl[6] = "NO";
                                pl[7] = "";
                                pl[8] = plano.getString("Prioridad");
                                pl[9] = this.numero;

                            }

                            while (eCo.next()) {
                                cortes[3] = eCo.getString("Cronometro");
                            }
                            while (eC.next()) {
                                calidad[1] = eC.getString("Estado");
                                calidad[2] = eC.getString("Tratamiento");
                                calidad[3] = eC.getString("Cronometro");
                                calidad[4] = eC.getString("Plano");
                                calidad[5] = eC.getString("Prioridad");
                                calidad[6] = eC.getString("FechaInicio");
                                calidad[7] = eC.getString("Proyecto");
                            }
                            while (rs.next()) {
                                cnc[1] = rs.getString("Proyecto");
                                cnc[3] = rs.getString("Cronometro");
                            }
                            while (rs1.next()) {
                                fresa[1] = rs1.getString("Proyecto");
                                fresa[3] = rs1.getString("Cronometro");
                            }
                            while (rs2.next()) {
                                torno[1] = rs2.getString("Proyecto");
                                torno[3] = rs2.getString("Cronometro");
                            }
                            while (rs3.next()) {
                                acabados[1] = rs3.getString("Proyecto");
                                acabados[3] = rs3.getString("Cronometro");
                            }
                            while (rs4.next()) {
                                trata[1] = rs4.getString("Proyecto");
                                trata[2] = rs4.getString("FechaInicio");

                            }
                            int n;
                            if (calidad[7] == null) {
                                pst8.setString(1, pl[0]);
                                pst8.setString(2, pl[1]);
                                pst8.setString(3, pl[2]);
                                pst8.setString(4, pl[3]);
                                pst8.setString(5, pl[4]);
                                pst8.setString(6, pl[5]);
                                pst8.setString(7, pl[6]);
                                pst8.setString(8, pl[7]);
                                pst8.setString(9, pl[8]);
                                pst8.setString(10, pl[9]);
                                n = pst8.executeUpdate();
                            } else {
                                pst.setString(1, plan);
                                pst.setString(2, calidad[4]);
                                pst.setString(3, fec);
                                pst.setString(4, fec);
                                pst.setString(5, "SI");
                                pst.setString(6, calidad[1]);
                                pst.setString(7, calidad[2]);
                                pst.setString(8, calidad[3]);
                                pst.setString(9, calidad[5]);
                                pst.setString(10, this.numero);
                                pst.setString(11, plan);
                                n = pst.executeUpdate();
                            }
                            if (plan.equals("")) {
                                JOptionPane.showMessageDialog(this, "DEBE ESCOGER UNA OPCION", "", JOptionPane.ERROR_MESSAGE);
                            } else {

                                if (propiedades[j][1].equals("fresa")) {
                                    if (fresa[1] == (null)) {
                                        pst2.setString(1, plan);
                                        pst2.setString(2, calidad[4]);
                                        pst2.setString(3, "");
                                        pst2.setString(4, "");
                                        pst2.setString(5, "NO");
                                        pst2.setString(6, calidad[1]);
                                        pst2.setString(7, "00:00");
                                        pst2.setString(8, calidad[5]);

                                        int n1 = pst2.executeUpdate();
                                        if (n1 > 0 && n > 0) {
                                            //-------------
                                            this.e.dispose();
                                        }

                                    } else {
                                        act1.setString(1, plan);
                                        act1.setString(2, calidad[4]);
                                        act1.setString(3, "");
                                        act1.setString(4, "");
                                        act1.setString(5, "NO");
                                        act1.setString(6, calidad[1]);
                                        act1.setString(7, fresa[3]);
                                        act1.setString(8, calidad[5]);
                                        act1.setString(9, plan);
                                        int n1 = act1.executeUpdate();
                                        if (n > 0 && n1 > 0) {
                                            //se limpiara la pantalla

                                            this.e.dispose();
                                        }
                                    }
                                }
                                if (propiedades[j][1].equals("cnc")) {

                                    if (cnc[1] == (null)) {
                                        pst1.setString(1, plan);
                                        pst1.setString(2, calidad[4]);
                                        pst1.setString(3, "");
                                        pst1.setString(4, "");
                                        pst1.setString(5, "NO");
                                        pst1.setString(6, calidad[1]);
                                        pst1.setString(7, "00:00");
                                        pst1.setString(8, calidad[5]);
                                        int n1 = pst1.executeUpdate();
                                        if (n > 0 && n1 > 0) {
                                            //------------
                                            this.e.dispose();
                                        }
                                    } else {
                                        act.setString(1, plan);
                                        act.setString(2, calidad[4]);
                                        act.setString(3, "");
                                        act.setString(4, "");
                                        act.setString(5, "NO");
                                        act.setString(6, calidad[1]);
                                        act.setString(7, cnc[3]);
                                        act.setString(8, calidad[5]);
                                        act.setString(9, plan);
                                        int n1 = act.executeUpdate();
                                        if (n1 > 0 && n > 0) {
                                            //
                                            this.e.dispose();
                                        }
                                    }
                                }
                                if (propiedades[j][1].equals("torno")) {

                                    if (torno[1] == (null)) {
                                        pst4.setString(1, plan);
                                        pst4.setString(2, calidad[4]);
                                        pst4.setString(3, "");
                                        pst4.setString(4, "");
                                        pst4.setString(5, "NO");
                                        pst4.setString(6, calidad[1]);
                                        pst4.setString(7, "00:00");
                                        pst4.setString(8, calidad[5]);
                                        int n1 = pst4.executeUpdate();
                                        if (n1 > 0 && n > 0) {
                                            //-----------------
                                            this.e.dispose();
                                        }
                                    } else {
                                        act2.setString(1, plan);
                                        act2.setString(2, calidad[4]);
                                        act2.setString(3, "");
                                        act2.setString(4, "");
                                        act2.setString(5, "NO");
                                        act2.setString(6, calidad[1]);
                                        act2.setString(7, torno[3]);
                                        act2.setString(8, calidad[5]);
                                        act2.setString(9, plan);
                                        int n1 = act2.executeUpdate();
                                        if (n1 > 0 && n > 0) {
                                            //-----------
                                            this.e.dispose();
                                        }
                                    }
                                }
                                if (propiedades[j][1].equals("cortes")) {

                                    ps1.setString(1, (plan));
                                    ps2.setString(1, (plan));
                                    ps3.setString(1, (plan));
                                    ps4.setString(1, (plan));
                                    ps6.setString(1, (plan));
                                    ps7.setString(1, (plan));
                                    ps5.setString(1, plan);
                                    ps5.setString(2, calidad[4]);
                                    ps5.setString(3, "");
                                    ps5.setString(4, "");
                                    ps5.setString(5, "NO");
                                    ps5.setString(6, calidad[1]);
                                    ps5.setString(7, "00:00");
                                    ps5.setString(8, calidad[5]);
                                    ps5.setString(9, plan);
                                    pst5.setString(1, plan);
                                    pst5.setString(2, this.numero);
                                    pst5.setString(3, fec);
                                    int n5 = ps1.executeUpdate();
                                    int n6 = ps2.executeUpdate();
                                    int n7 = ps3.executeUpdate();
                                    int n8 = ps4.executeUpdate();
                                    int n9 = ps5.executeUpdate();
                                    int n10 = ps6.executeUpdate();
                                    int n11 = pst5.executeUpdate();
                                    int n12 = ps7.executeUpdate();
                                    //------
                                    if (n > 0 && n5 > 0 && n6 > 0 && n7 > 0 && n8 > 0 && n9 > 0 && n10 > 0 && n11 > 0 && n12 > 0) {
                                        //----
                                        JOptionPane.showMessageDialog(this, "DATOS GUARDADOS", "", JOptionPane.INFORMATION_MESSAGE);

                                        this.e.dispose();
                                    }
                                }
                                if (propiedades[j][1].equals("acabados")) {

                                    if (acabados[1] == (null)) {
                                        pst6.setString(1, plan);
                                        pst6.setString(2, calidad[4]);
                                        pst6.setString(3, "");
                                        pst6.setString(4, "");
                                        pst6.setString(5, "NO");
                                        pst6.setString(6, calidad[1]);
                                        pst6.setString(7, "00:00");
                                        pst6.setString(8, pl[8]);
                                        int n1 = pst6.executeUpdate();
                                        if (n1 > 0 && n > 0) {
                                            //---------------

                                            this.e.dispose();
                                        }
                                    } else {
                                        act3.setString(1, plan);
                                        act3.setString(2, calidad[4]);
                                        act3.setString(3, "");
                                        act3.setString(4, "");
                                        act3.setString(5, "NO");
                                        act3.setString(6, calidad[1]);
                                        act3.setString(7, calidad[3]);
                                        act3.setString(8, pl[8]);
                                        act3.setString(9, plan);

                                        int n1 = act3.executeUpdate();
                                        if (n1 > 0 && n > 0) {
                                            //--------------------------

                                            this.e.dispose();
                                        }
                                    }
                                }
                                if (propiedades[j][1].equals("trata")) {

                                    if (trata[1] == (null)) {
                                        pst7.setString(1, plan);
                                        pst7.setString(2, calidad[4]);
                                        pst7.setString(3, "");
                                        pst7.setString(4, "");
                                        pst7.setString(5, "NO");
                                        pst7.setString(6, calidad[1]);
                                        pst7.setString(7, calidad[5]);
                                        int n1 = pst7.executeUpdate();
                                        if (n > 0 && n1 > 0) {
                                            //-----------

                                            this.e.dispose();
                                        }

                                    } else {
                                        act4.setString(1, plan);
                                        act4.setString(2, calidad[4]);
                                        act4.setString(3, "");
                                        act4.setString(4, "");
                                        act4.setString(5, "NO");
                                        act4.setString(6, calidad[1]);
                                        act4.setString(7, calidad[5]);
                                        act4.setString(8, plan);
                                        int n1 = act4.executeUpdate();
                                        if (n1 > 0 && n > 0) {
                                            //-------

                                            this.e.dispose();
                                        }
//                                
//                                
                                    }
                                }
                            }//-------------
                        }
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(null, "NO SE PUEDE ENVIAR INFORMACION" + ex, "ERROR", JOptionPane.ERROR_MESSAGE);
                    }
                }

            }
        } else {
            JOptionPane.showMessageDialog(this, "DEBES SELECCIONAR UN PLANO");
        }
    }//GEN-LAST:event_jLabel2MouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if (Tabla1.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "DEBES LLENAR LA TABLA", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
        } else {
            if (panelEditar.getBackground().equals(Color.white)) {
                panelEditar.setBackground(Color.gray);
            } else {
                panelEditar.setBackground(Color.white);
            }
            for (int i = 0; i < Tabla1.getRowCount(); i++) {
                JFrame frame = (JFrame) JOptionPane.getFrameForComponent(this);
                lectura = new Lectura(frame, true, Tabla1.getValueAt(i, 1).toString());
//                lectura.btnEnviar.addActionListener(this);
//                lectura.btnBorrar.addActionListener(this);
//                lectura.lblPunto.setText(Tabla1.getValueAt(i, 0).toString());
                lectura.setVisible(true);
            }

            panelEditar.setBackground(Color.white);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        DefaultTableModel miModelo = (DefaultTableModel) Tabla1.getModel();
        int sele = Tabla1.getSelectedRow();
        String d = Tabla1.getValueAt(sele, 0).toString();
        int a = Integer.parseInt(d);
        l[a].setText("");
        revalidate();
        repaint();
        miModelo.removeRow(sele);
        btnEliminar.setEnabled(false);
        btnEditar1.setEnabled(false);
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnEditar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditar1ActionPerformed
        if (panelEditar1.getBackground().equals(Color.white)) {
            panelEditar1.setBackground(Color.gray);
        } else {
            panelEditar1.setBackground(Color.white);
        }
        JFrame frame = (JFrame) JOptionPane.getFrameForComponent(this);
        lectura = new Lectura(frame, true, Tabla1.getValueAt(Tabla1.getSelectedRow(), 1).toString());
//        lectura.btnEnviar.addActionListener(this);
//        lectura.btnBorrar.addActionListener(this);
//        lectura.lblPunto.setText(Tabla1.getValueAt(Tabla1.getSelectedRow(), 0).toString());
        lectura.setVisible(true);
        panelEditar1.setBackground(Color.white);
        btnEliminar.setEnabled(false);
        btnEditar1.setEnabled(false);
    }//GEN-LAST:event_btnEditar1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        verPdf v = new verPdf();
        v.setLocationRelativeTo(this);
        v.setVisible(true);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void txtPlanoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPlanoActionPerformed
        btnEliminar.setEnabled(false);
        btnEditar1.setEnabled(false);

        plan = txtPlano.getText();
        byte[] ruta = null;

        limpiarTabla1();
        try {
            Connection con = null;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            String sql = "select Pdf,Plano from pdfplanos where Plano like '" + plan + "'";
            ResultSet rs = st.executeQuery(sql);
            String cant = "1";
            while (rs.next()) {
                ruta = rs.getBytes("Pdf");
//                cant = rs.getString("Cantidad");
            }

            int can = Integer.parseInt(cant);

            JButton[] boton = new JButton[can];

            for (int i = 0; i < can; i++) {
                boton[i] = new JButton(String.valueOf(i + 1));
            }

            JFrame f = (JFrame) JOptionPane.getFrameForComponent(this);
            n = new Numeros(f, true, boton);
            n.btnAgregar.addActionListener(this);
            n.setVisible(true);

            Statement st1 = con.createStatement();
            String sql1 = "select Id,Plano from calidadpdf where Plano like '" + plan + "'";
            ResultSet rs1 = st1.executeQuery(sql1);
            int cont = 0;

            if (ruta == null) {
                JOptionPane.showMessageDialog(this, "NO EXISTE ESTE PLANO", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
            } else {
                File n = new File("C:\\Pruebas\\BD\\img\\new.pdf");
                try {
                    BufferedOutputStream bs;
                    FileOutputStream fs = new FileOutputStream(n);
                    bs = new BufferedOutputStream(fs);
                    bs.write(ruta);

                    bs.close();
                } catch (IOException e) {
                    n = new File("C:\\Pruebas\\BD\\img\\new2.pdf");
                    try {
                        BufferedOutputStream bs;
                        FileOutputStream fs = new FileOutputStream(n);
                        bs = new BufferedOutputStream(fs);
                        bs.write(ruta);

                        bs.close();
                    } catch (IOException e2) {
                        JOptionPane.showMessageDialog(this, "ERROR: " + e2, "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
                    }
                }

                abrir_pdf(n.toString());
            }

            while (rs1.next()) {
                cont++;
            }

            lblPlano.setText(plan);

            if (cont > 0) {

                Statement st2 = con.createStatement();
                String sql2 = "select Plano, Fecha, Id from calidadpdf where Plano like '" + plan + "'";
                ResultSet rs2 = st2.executeQuery(sql2);
                String datos2[] = new String[10];
                botones = new JButton[cont];
                bot = new String[cont];

                int c = 0;
                while (rs2.next()) {
                    datos2[0] = rs2.getString("Plano");
                    datos2[1] = rs2.getString("Fecha");
                    datos2[3] = rs2.getString("Id");
                    botones[c] = new JButton("<html>"
                            + "<font size = 4><b><p>" + datos2[0] + "</p></b></font>"
                            + "<font size = 2><p>" + datos2[3] + "</p></font>"
                            + "<font size = 1><p>" + datos2[1] + "</p></font>"
                            + "</html>");
                    botones[c].setContentAreaFilled(false);
                    botones[c].setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
                    botones[c].setBorder(null);
                    botones[c].addActionListener(this);
                    bot[c] = datos2[3];
                    c++;
                }
                JFrame d = (JFrame) JOptionPane.getFrameForComponent(this);
                a = new Seleccionar(d, true, botones);
                a.setSize(300, 500);
                a.setResizable(false);
                a.setLocationRelativeTo(null);
                a.setVisible(true);

            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "ERROR: " + e, "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_txtPlanoActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Panel4;
    public javax.swing.JTable Tabla1;
    private javax.swing.JButton btnAprobado;
    private javax.swing.JButton btnEditar1;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnRechazo;
    private javax.swing.JPanel btnSalir;
    private javax.swing.JPanel btnTerminar;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JLabel lblPlano;
    private javax.swing.JLabel lblRevision;
    private javax.swing.JLabel lblS;
    private javax.swing.JPanel panelEditar;
    private javax.swing.JPanel panelEditar1;
    private javax.swing.JFormattedTextField txtAlto;
    private javax.swing.JFormattedTextField txtBajo;
    private RSMaterialComponent.RSTextFieldMaterial txtPlano;
    // End of variables declaration//GEN-END:variables

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        for (int i = 0; i < Tabla1.getRowCount() + 1; i++) {
            if (e.getSource() == l[i + 1]) {
                l[i + 1].setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
//            l[i+1].setForeground(Color.red);
            }
        }

    }

    @Override
    public void mouseExited(MouseEvent e) {
        for (int i = 0; i < Tabla1.getRowCount() + 1; i++) {
            if (e.getSource() == l[i + 1]) {
//            l[i+1].setForeground(Color.blue);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (botonesNumeros != null) {
            for (int i = 0; i < botonesNumeros.length; i++) {
                if (e.getSource() == botonesNumeros[i]) {
                    agregarTabla(numTabla, i);

                    if (tablas[i].getRowCount() == 0 && tablas[0].getRowCount() > 0) {
                        int opc = JOptionPane.showConfirmDialog(this, "¿QUIERES INSERTAR LA TABLA " + botonesNumeros[0].getText() + " A ESTA TABLA?");
                        if (opc == 0) {
                            limpiarTabla1();
                            DefaultTableModel miModelo = (DefaultTableModel) tablas[i].getModel();
                            String datos[] = new String[15];
                            for (int j = 0; j < tablas[0].getRowCount(); j++) {
                                datos[0] = tablas[0].getValueAt(j, 0).toString();
                                datos[1] = tablas[0].getValueAt(j, 1).toString();
                                datos[2] = tablas[0].getValueAt(j, 2).toString();
                                datos[3] = tablas[0].getValueAt(j, 3).toString();
                                datos[4] = tablas[0].getValueAt(j, 4).toString();
                                datos[5] = tablas[0].getValueAt(j, 5).toString();
                                datos[6] = tablas[0].getValueAt(j, 6).toString();
                                datos[7] = tablas[0].getValueAt(j, 7).toString();
                                datos[8] = tablas[0].getValueAt(j, 8).toString();
                                miModelo.addRow(datos);
                            }

                            Tabla1.setModel(miModelo);
                            addLabels();

                        } else {
                            limpiarTabla1();

                        }
                    }
                    numTabla = i;

                    for (int j = 0; j < panel.length; j++) {
                        if (propiedades[j][0] != null) {
                            switch (propiedades[j][0]) {
                                case "blanco":
                                    panel[j].setBackground(Color.white);
                                    break;
                                case "verde":
                                    panel[j].setBackground(Color.green);
                                    break;
                                case "amarillo":
                                    panel[j].setBackground(Color.yellow);
                                    break;
                                case "rojo":
                                    panel[j].setBackground(Color.red);
                                    break;
                                case "cyan":
                                    panel[j].setBackground(Color.cyan);
                                    break;
                                default:
                                    break;
                            }
                        }

                    }

                    panel[i].setBackground(Color.GRAY);
                    lblRevision.setText(botonesNumeros[i].getText());
                }
            }
        }

        if (n != null) {

            if (e.getSource() == n.btnAgregar) {

                if (band == true) {
                    limpiarPanel();
                    band = false;
                }
                int cont = 0;
                for (int i = 0; i < n.panel.length; i++) {
                    if (n.panel[i].getBackground().equals(Color.green)) {
                        cont++;
                    }
                }

                botonesNumeros = new JButton[cont];
                panel = new JPanel[cont];
                propiedades = new String[cont][5];
                tablas = new JTable[cont];

                for (int i = 0; i < tablas.length; i++) {
                    tablas[i] = new JTable();
                    limpiarTablas(tablas[i]);
                }

                int c = 0;
                for (int i = 0; i < n.panel.length; i++) {
                    if (n.panel[i].getBackground().equals(Color.green)) {
                        botonesNumeros[c] = new JButton(n.bot[i].getText());
                        botonesNumeros[c].setContentAreaFilled(false);
                        botonesNumeros[c].setBorder(null);
                        botonesNumeros[c].setBorderPainted(false);
                        botonesNumeros[c].setContentAreaFilled(false);
                        botonesNumeros[c].setFocusPainted(false);
                        botonesNumeros[c].addActionListener(this);
                        botonesNumeros[c].setFont(new java.awt.Font("Roboto", java.awt.Font.PLAIN, 12));
                        botonesNumeros[c].setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
                        panel[c] = new JPanel();
                        panel[c].setBackground(Color.white);
                        if (c == 0) {
                            panel[c].setBackground(Color.gray);
                            lblRevision.setText(botonesNumeros[c].getText());
                            numTabla = 0;
                        }
                        propiedades[c][0] = "blanco";
                        propiedades[c][1] = "";
                        panel[c].add(botonesNumeros[c]);
                        c++;
                        band = true;
                    }
                }
                for (int i = 0; i < botonesNumeros.length; i++) {
                    Panel4.add(panel[i]);
                }
                n.dispose();
            }
        }

        if (lectura == null) {

        } else {
            try {
//                if (e.getSource() == lectura.btnEnviar) {
//                    if (panelEditar.getBackground().equals(Color.gray) || panelEditar1.getBackground().equals(Color.gray)) {
//                        double bajo, alto;
//                        DefaultTableModel miModelo = (DefaultTableModel) Tabla1.getModel();
//                        String datos[] = new String[10];
//                        datos[0] = lectura.lblPunto.getText();
//                        datos[1] = lectura.txtNominal.getText();
//
//                        datos[4] = lectura.txtReal.getText();
//                        datos[6] = lectura.txtObservaciones.getText();
//                        int f = -1;
//                        for (int i = 0; i < Tabla1.getRowCount(); i++) {
//                            if (Tabla1.getValueAt(i, 0).toString().equals(datos[0])) {
//                                miModelo.setValueAt(datos[1], i, 1);
//                                miModelo.setValueAt(datos[4], i, 4);
//                                miModelo.setValueAt(datos[6], i, 6);
//                                datos[2] = Tabla1.getValueAt(i, 2).toString();
//                                datos[3] = Tabla1.getValueAt(i, 3).toString();
//                                f = i;
//                            }
//                        }
//
//                        lectura.dispose();
//
//                        bajo = (Double.parseDouble(datos[1]) - (Double.parseDouble(datos[3])));
//                        alto = (Double.parseDouble(datos[1]) + (Double.parseDouble(datos[2])));
////            System.out.println(""+Tabla1.getRowCount());
//                        if (Double.parseDouble(datos[4]) > alto || Double.parseDouble(datos[4]) < bajo) {
//                            miModelo.setValueAt("No", f, 5);
//
//                            l[f + 1].setForeground(Color.red);
//                        } else {
//                            miModelo.setValueAt("Si", f, 5);
//                            l[f + 1].setForeground(Color.blue);
//                        }
//
//                    } else {
//                        double bajo, alto;
//
//                        DefaultTableModel miModelo = (DefaultTableModel) Tabla1.getModel();
//                        String datos[] = new String[10];
//                        datos[0] = lectura.lblPunto.getText();
//                        datos[1] = lectura.txtNominal.getText();
//                        datos[2] = txtBajo.getText();
//                        datos[3] = txtAlto.getText();
//                        datos[4] = lectura.txtReal.getText();
//                        datos[6] = lectura.txtObservaciones.getText();
//
//                        miModelo.addRow(datos);
//                        int a;
//                        a = Integer.parseInt(Tabla1.getValueAt(Tabla1.getRowCount() - 1, 0).toString());
//
//                        datos[7] = String.valueOf(l[a].getX());
//                        datos[8] = String.valueOf(l[a].getY());
//                        Tabla1.setValueAt(datos[7], Tabla1.getRowCount() - 1, 7);
//                        Tabla1.setValueAt(datos[8], Tabla1.getRowCount() - 1, 8);
//
//                        lectura.dispose();
//
//                        int f = Integer.parseInt(Tabla1.getValueAt(Tabla1.getRowCount() - 1, 0).toString());
//                        bajo = (Double.parseDouble(datos[1]) - (Double.parseDouble(datos[2])));
//                        alto = (Double.parseDouble(datos[1]) + (Double.parseDouble(datos[3])));
////            System.out.println(""+Tabla1.getRowCount());
//                        if (Double.parseDouble(datos[4]) > alto || Double.parseDouble(datos[4]) < bajo) {
//                            Tabla1.setValueAt("No", Tabla1.getRowCount() - 1, 5);
//
//                            l[f].setForeground(Color.red);
//                        } else {
//                            Tabla1.setValueAt("Si", Tabla1.getRowCount() - 1, 5);
//                        }
//
//                    }
//                }
            } catch (Exception ex) {
                Logger.getLogger(CalidadNew.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        if (lectura == null) {

        } else {
            try {
//                if (e.getSource() == lectura.btnBorrar) {
//                    DefaultTableModel miModelo = (DefaultTableModel) Tabla1.getModel();
//                    int sele = Tabla1.getRowCount() + 1;
//                    l[sele].setText("");
//                    l[sele].setEnabled(false);
//                    lectura.dispose();
//                }
            } catch (Exception ex) {
                Logger.getLogger(CalidadNew.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        if (botones != null) {
            for (int i = 0; i < botones.length; i++) {
                if (e.getSource() == botones[i]) {
                    try {
                        limpiarTabla1();
                        Connection con = null;
                        Conexion con1 = new Conexion();
                        con = con1.getConnection();
                        Statement st = con.createStatement();
                        String sql = "select * from calidadcotas where CalidadPdf like'" + bot[i] + "'";
                        String datos[] = new String[20];
                        ResultSet rs = st.executeQuery(sql);

                        DefaultTableModel miModelo = (DefaultTableModel) Tabla1.getModel();
                        while (rs.next()) {
                            datos[0] = rs.getString("Punto");
                            datos[1] = rs.getString("Nominal");
                            datos[2] = rs.getString("LimiteBajo");
                            datos[3] = rs.getString("LimiteAlto");
                            datos[4] = rs.getString("Rea");
                            datos[5] = rs.getString("Aprobado");
                            datos[6] = rs.getString("Observaciones");
                            datos[7] = rs.getString("X");
                            datos[8] = rs.getString("Y");
                            miModelo.addRow(datos);
                        }

                        addLabels();

                        a.dispose();
                    } catch (SQLException eX) {
                        JOptionPane.showMessageDialog(this, "ERROR: " + eX, "ERROR", JOptionPane.ERROR_MESSAGE);
                    }
                    i = botones.length;
                }
            }
        }
        if (this.e == null) {

        } else {
            //------------------------------------------------------------------------------------------------------------
            try {
                Date fecha = new Date();
                SimpleDateFormat fec1 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                String fec = fec1.format(fecha);

//        
                if (e.getSource() == this.e.btnFresa) {

                    for (int i = 0; i < panel.length; i++) {
                        if (panel[i].getBackground().equals(Color.gray)) {
                            panel[i].setBackground(Color.yellow);

                            propiedadNo3(i);

                            propiedades[i][0] = "amarillo";
                            propiedades[i][1] = "fresa";
                            this.e.dispose();
                            lblRevision.setText("");
                            agregarTabla(numTabla, numTabla);
                            numTabla = -1;

                            limpiarTabla1();
                        }
                    }
                }
                if (e.getSource() == this.e.btnCnc) {
                    for (int i = 0; i < panel.length; i++) {
                        if (panel[i].getBackground().equals(Color.gray)) {
                            panel[i].setBackground(Color.yellow);
                            propiedades[i][0] = "amarillo";
                            propiedades[i][1] = "cnc";
                            propiedadNo3(i);
                            this.e.dispose();
                            lblRevision.setText("");
                            agregarTabla(numTabla, numTabla);
                            numTabla = -1;

                            limpiarTabla1();
                        }
                    }
                }
                if (e.getSource() == this.e.btnTorno) {
                    for (int i = 0; i < panel.length; i++) {
                        if (panel[i].getBackground().equals(Color.gray)) {
                            panel[i].setBackground(Color.yellow);
                            propiedades[i][0] = "amarillo";
                            propiedades[i][1] = "torno";
                            propiedadNo3(i);
                            this.e.dispose();
                            lblRevision.setText("");
                            agregarTabla(numTabla, numTabla);
                            numTabla = -1;

                            limpiarTabla1();
                        }
                    }
                }
                if (e.getSource() == this.e.btnCortes) {
                    for (int i = 0; i < panel.length; i++) {
                        if (panel[i].getBackground().equals(Color.gray)) {
                            panel[i].setBackground(Color.red);
                            propiedades[i][0] = "rojo";
                            propiedades[i][1] = "cortes";
                            propiedadNo3(i);
                            this.e.dispose();
                            lblRevision.setText("");
                            agregarTabla(numTabla, numTabla);
                            numTabla = -1;

                            limpiarTabla1();
                        }
                    }
                }
                if (e.getSource() == this.e.btnAcabados) {
                    for (int i = 0; i < panel.length; i++) {
                        if (panel[i].getBackground().equals(Color.gray)) {
                            panel[i].setBackground(Color.yellow);
                            propiedades[i][0] = "amarillo";
                            propiedades[i][1] = "acabados";
                            propiedadNo3(i);
                            this.e.dispose();
                            lblRevision.setText("");
                            agregarTabla(numTabla, numTabla);
                            numTabla = -1;

                            limpiarTabla1();
                        }
                    }
                }
                if (e.getSource() == this.e.btnTrata) {
                    for (int i = 0; i < panel.length; i++) {
                        if (panel[i].getBackground().equals(Color.gray)) {
                            panel[i].setBackground(Color.cyan);
                            propiedades[i][0] = "cyan";
                            propiedades[i][1] = "trata";
                            propiedadNo3(i);
                            this.e.dispose();
                            lblRevision.setText("");
                            agregarTabla(numTabla, numTabla);
                            numTabla = -1;

                            limpiarTabla1();
                        }
                    }
                }

            } catch (Exception ex) {
                Logger.getLogger(CalidadNew.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void windowOpened(WindowEvent e) {
    }

    @Override
    public void windowClosing(WindowEvent e) {
    }

    @Override
    public void windowClosed(WindowEvent e) {
//        if(e.getSource() == lectura){
//        acomodar();
//        DefaultTableModel miModelo = (DefaultTableModel) Tabla1.getModel();
//        int sele = Tabla1.getRowCount()+1;
//        l[sele].setText("");
//        l[sele].setEnabled(false);
//        }
    }

    @Override
    public void windowIconified(WindowEvent e) {
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowActivated(WindowEvent e) {
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
    }
}
