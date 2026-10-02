package pruebas;

import Conexiones.Conexion;
import Modelo.CabezeraChecador;
import VentanaEmergente.Checador.ColorChecador;
import VentanaEmergente.Checador.Incidencias;
import VentanaEmergente.Checador.confEmpleado;
import VentanaEmergente.Checador.editarEmpleado;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JViewport;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

public final class Checador extends javax.swing.JInternalFrame implements ActionListener {

    Connection con;
    Conexion con1 = new Conexion();
    String numEmpleado;
    confEmpleado config[];
    editarEmpleado e;
    String departamento;
    Stack<String> fecha;
    Incidencias incidencias;
    ColorChecador color;
    Object matrizTabla[][];
    Object matrizIncidencias[][][];
    JFrame frame;
    public boolean admin;
    public String supervisor;

    public int buscarFilaEmpleado(String numEmpelado) {
        for (int i = 0; i < Tabla1.getRowCount(); i++) {
            if (Tabla1.getValueAt(i, 0).toString().equals(numEmpelado)) {
                return i;
            }
        }
        return -1;
    }

    public void crearMatrizIncidencias(Connection con, String inicio, String numSemana) throws SQLException {
        Statement st = con.createStatement();
        String sql = "select * from incidencias_checador where NumSemana like '" + numSemana + "' and Inicio like '" + inicio + "'";
        ResultSet rs = st.executeQuery(sql);
        while (rs.next()) {
            int X = rs.getInt("X");
            String empleado = rs.getString("NumEmpleado");
            String comentario = rs.getString("comentario");
            String hora = rs.getString("Hora");
            int y = buscarFilaEmpleado(empleado);
            if (y != -1) {
                matrizIncidencias[y][X][0] = comentario;
                matrizIncidencias[y][X][1] = hora;
                matrizIncidencias[y][X][2] = matrizTabla[y][X];
                Tabla1.setValueAt(hora, y, X);
            }
        }
        color.setMatriz(matrizIncidencias);
    }

    public void crearMatriz() {
        matrizTabla = new Object[Tabla1.getRowCount()][Tabla1.getColumnCount()];
        matrizIncidencias = new Object[Tabla1.getRowCount()][Tabla1.getColumnCount()][3];
        for (int i = 0; i < Tabla1.getRowCount(); i++) {
            for (int j = 0; j < Tabla1.getColumnCount(); j++) {
                matrizTabla[i][j] = Tabla1.getValueAt(i, j);
            }
        }
    }

    public int buscarNumero(String numero) {
        int num = 0;
        for (int i = 0; i < config.length; i++) {
            if (config[i].getNumEmpleado().equals(numero)) {
                num = i;
            }
        }
        return num;
    }

    public String redondear(String tiempo, String num) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        SimpleDateFormat mm = new SimpleDateFormat("mm");

        String redondeo = null;
        if (tiempo == null) {
            redondeo = "";
        } else if (tiempo.equals("")) {
            redondeo = "";
        } else {
            try {
                Date time = sdf.parse(tiempo);
                Date entrada = sdf.parse(config[buscarNumero(num)].getEntrada());
                Date salida = sdf.parse(config[buscarNumero(num)].getSalida());
                Date entradaSa = sdf.parse(config[buscarNumero(num)].getEntradaSabado());
                Date salidaSa = sdf.parse(config[buscarNumero(num)].getSalidaSabado());

                Calendar calendar = Calendar.getInstance();
                calendar.setTime(entrada);
                calendar.add(Calendar.MINUTE, 30);
                long entMas = calendar.getTime().getTime();

                calendar.setTime(salida);
                calendar.add(Calendar.MINUTE, -30);
                long salMen = calendar.getTime().getTime();

                calendar.setTime(entradaSa);
                calendar.add(Calendar.MINUTE, 30);
                long entSabMas = calendar.getTime().getTime();

                calendar.setTime(salidaSa);
                calendar.add(Calendar.MINUTE, -30);
                long salSabMen = calendar.getTime().getTime();
                if ((time.getTime() > entrada.getTime()) && (time.getTime() < entMas)) {
                    redondeo = tiempo;
                } else if ((time.getTime() < salida.getTime()) && (time.getTime() > salMen)) {
                    redondeo = tiempo;
                } else if ((time.getTime() > entradaSa.getTime()) && (time.getTime() < entSabMas)) {
                    redondeo = tiempo;
                } else if ((time.getTime() < salidaSa.getTime()) && (time.getTime() > salSabMen)) {
                    redondeo = tiempo;
                } else {
                    String d = mm.format(time);
                    int re = Integer.parseInt(d);

                    if (re < 30) {
                        redondeo = sdf.format(time);
                        redondeo = redondeo.substring(0, 2) + ":00";
                    } else {
                        Calendar cal = Calendar.getInstance();
                        cal.setTime(time);
                        cal.add(Calendar.HOUR, 1);
                        redondeo = sdf.format(cal.getTime());
                        redondeo = redondeo.substring(0, 2) + ":00";
                    }
                }

            } catch (ParseException ex) {
                Logger.getLogger(Checador.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return redondeo;
    }

    public String getTiempo(String time) {
        if (time == null) {
            return "";
        } else {
            if (time.equals("")) {
                return "";
            } else {
                return time.substring(0, 5);
            }
        }
    }

    public String getNombre() {
        String nombre = "";
        try {
            Connection con;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            String sql = "select * from registroempleados where NumEmpleado like '" + numEmpleado + "'";
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                nombre = rs.getString("Nombre") + " " + rs.getString("Apellido");
                departamento = rs.getString("Supervisor");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "ERROR: " + e, "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        return nombre;
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
            JFileChooser fc = new JFileChooser();
            File archivo = null;
            fc.setFileFilter(new FileNameExtensionFilter("PDF (*.pdf)", "pdf"));
            int n = fc.showSaveDialog(this);

            if (n == JFileChooser.APPROVE_OPTION) {
                archivo = fc.getSelectedFile();
            }
            String ruta = archivo.getAbsolutePath() + ".pdf";
            Document document = new Document(PageSize.A4.rotate(), 10, 10, 36, 36);
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(ruta));
            CabezeraChecador encabezado = new CabezeraChecador();
            encabezado.setEncabezado("ENCABEZADO DE REMISIONES");
            writer.setPageEvent(encabezado);
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
            fuente3.setSize(14);
            fuente3.setFamily("Roboto");
            fuente3.setColor(BaseColor.RED);
            fuente3.setStyle(com.itextpdf.text.Font.BOLD);

            com.itextpdf.text.Font fuente4 = new com.itextpdf.text.Font();
            fuente4.setSize(14);
            fuente4.setFamily("Roboto");
            fuente4.setColor(BaseColor.BLACK);
            fuente4.setStyle(com.itextpdf.text.Font.BOLD);

            com.itextpdf.text.Font fuenteFecha = new com.itextpdf.text.Font();
            fuenteFecha.setSize(11);
            fuenteFecha.setFamily("Roboto");
            fuenteFecha.setColor(BaseColor.BLACK);
            fuenteFecha.setStyle(com.itextpdf.text.Font.NORMAL);

            com.itextpdf.text.Font fuenteFecha2 = new com.itextpdf.text.Font();
            fuenteFecha2.setSize(11);
            fuenteFecha2.setFamily("Roboto");
            fuenteFecha2.setColor(BaseColor.BLACK);
            fuenteFecha2.setStyle(com.itextpdf.text.Font.BOLD);

            com.itextpdf.text.Font fuenteFecha3 = new com.itextpdf.text.Font();
            fuenteFecha3.setSize(8);
            fuenteFecha3.setFamily("Roboto");
            fuenteFecha3.setColor(BaseColor.BLACK);
            fuenteFecha3.setStyle(com.itextpdf.text.Font.NORMAL);

            com.itextpdf.text.Font fuenteCliente = new com.itextpdf.text.Font();
            fuenteCliente.setSize(12);
            fuenteCliente.setFamily("Roboto");
            fuenteCliente.setColor(BaseColor.BLACK);
            fuenteCliente.setStyle(com.itextpdf.text.Font.NORMAL);

            com.itextpdf.text.Font fuenteArticulos = new com.itextpdf.text.Font();
            fuenteArticulos.setSize(9);
            fuenteArticulos.setFamily("Roboto");
            fuenteArticulos.setColor(BaseColor.BLACK);
            fuenteArticulos.setStyle(com.itextpdf.text.Font.NORMAL);

            com.itextpdf.text.Font fuenteIncidenciasCabecera = new com.itextpdf.text.Font();
            fuenteIncidenciasCabecera.setSize(10);
            fuenteIncidenciasCabecera.setFamily("Roboto");
            fuenteIncidenciasCabecera.setColor(BaseColor.WHITE);
            fuenteIncidenciasCabecera.setStyle(com.itextpdf.text.Font.BOLD);

            com.itextpdf.text.Font fuenteIncidenciasFila = new com.itextpdf.text.Font();
            fuenteIncidenciasFila.setSize(7);
            fuenteIncidenciasFila.setFamily("Roboto");
            fuenteIncidenciasFila.setColor(BaseColor.BLACK);
            fuenteIncidenciasFila.setStyle(com.itextpdf.text.Font.NORMAL);

            //------------------------------------------------------------------------------------------------
            //-----------------------------------PARTE 1, NOTA REMISION Y FECHAS-------------------------------
            PdfPTable tabla1 = new PdfPTable(1);
            tabla1.setWidthPercentage(100);

            PdfPCell co1 = new PdfPCell(new Paragraph("Reporte semanal de empleados", fuente4));
            co1.setBorder(0);
            co1.setHorizontalAlignment(Element.ALIGN_CENTER);

            PdfPCell bl = new PdfPCell(new Paragraph("\n", fuente4));
            bl.setBorder(0);
            bl.setHorizontalAlignment(Element.ALIGN_CENTER);

            tabla1.addCell(co1);
            tabla1.addCell(bl);
            //------------------------------------------------------------------------------
            //----------------------------TABLA2 CLIENTE DATOS--------------------------------------------
            PdfPTable tablaCliente = new PdfPTable(4);
            tablaCliente.setWidthPercentage(100);
            float medidas1[] = {300, 400, 150, 150};
            tablaCliente.setWidths(medidas1);

            PdfPCell cli = new PdfPCell(new Paragraph("Supervisor: ", fuenteFecha2));
            border(cli, 0f, 0, 0, 0);
            cli.setHorizontalAlignment(Element.ALIGN_RIGHT);

            PdfPCell nom = new PdfPCell(new Paragraph(getNombre(), fuenteFecha));
            border(nom, 0, 1f, 0, 0);
            nom.setHorizontalAlignment(Element.ALIGN_CENTER);

            PdfPCell per = new PdfPCell(new Paragraph("Periodo: ", fuenteFecha2));
            border(per, 0, 0f, 0, 0);
            per.setHorizontalAlignment(Element.ALIGN_RIGHT);

            PdfPCell pe = new PdfPCell(new Paragraph(lblLunes.getText() + " - " + lbldomingo.getText(), fuenteFecha3));
            border(pe, 0, 1, 0, 0);
            pe.setHorizontalAlignment(Element.ALIGN_CENTER);

            PdfPCell dep = new PdfPCell(new Paragraph("Departamento: ", fuenteFecha2));
            border(dep, 0, 0f, 0, 0);
            dep.setHorizontalAlignment(Element.ALIGN_RIGHT);

            PdfPCell de = new PdfPCell(new Paragraph(departamento, fuenteFecha));
            border(de, 0, 1, 0, 0);
            de.setHorizontalAlignment(Element.ALIGN_CENTER);

            PdfPCell sem = new PdfPCell(new Paragraph("Semana: ", fuenteFecha2));
            border(sem, 0, 0, 0, 0);
            sem.setHorizontalAlignment(Element.ALIGN_RIGHT);

            PdfPCell se = new PdfPCell(new Paragraph(lblSemana.getText(), fuenteFecha));
            border(se, 0, 1, 0, 0);
            se.setHorizontalAlignment(Element.ALIGN_CENTER);

            PdfPCell blan = new PdfPCell(new Paragraph("\n"));
            blan.setBorder(0);
            blan.setHorizontalAlignment(Element.ALIGN_CENTER);

            tablaCliente.addCell(cli);
            tablaCliente.addCell(nom);
            tablaCliente.addCell(per);
            tablaCliente.addCell(pe);
            tablaCliente.addCell(dep);
            tablaCliente.addCell(de);
            tablaCliente.addCell(sem);
            tablaCliente.addCell(se);

            //----------------------TABLA DE FECHAS--------------------------------------------
            PdfPTable tablaFechas = new PdfPTable(26);
            tablaFechas.setWidthPercentage(100);
            float medidas2[] = {30, 150, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40};
            tablaFechas.setWidths(medidas2);

            PdfPCell blanco = new PdfPCell(new Paragraph("\n", fuenteArticulos));
            blanco.setHorizontalAlignment(Element.ALIGN_CENTER);
            blanco.setBorderWidth(0);

            PdfPCell c1 = new PdfPCell(new Paragraph("#", fuenteArticulos));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            c1.setRowspan(3);
            border(c1, 1.5f, 0, 1.5f, 1.5f);

            PdfPCell c2 = new PdfPCell(new Paragraph("Empleado", fuenteArticulos));
            c2.setHorizontalAlignment(Element.ALIGN_CENTER);
            c2.setRowspan(2);
            border(c2, 1.5f, 0, 0f, 1.5f);

            PdfPCell c3 = new PdfPCell(new Paragraph("Total", fuenteArticulos));
            c3.setHorizontalAlignment(Element.ALIGN_CENTER);
            c3.setColspan(3);
            border(c3, 1.5f, 1f, 0, 1.5f);

            PdfPCell lunes = new PdfPCell(new Paragraph(lblLunes.getText(), fuenteArticulos));
            lunes.setHorizontalAlignment(Element.ALIGN_CENTER);
            lunes.setColspan(3);
            border(lunes, 1.5f, 1f, 0, 1.5f);

            PdfPCell martes = new PdfPCell(new Paragraph(lblMartes.getText(), fuenteArticulos));
            martes.setHorizontalAlignment(Element.ALIGN_CENTER);
            martes.setColspan(3);
            border(martes, 1.5f, 1f, 0, 1.5f);

            PdfPCell miercoles = new PdfPCell(new Paragraph(lblMiercoles.getText(), fuenteArticulos));
            miercoles.setHorizontalAlignment(Element.ALIGN_CENTER);
            miercoles.setColspan(3);
            border(miercoles, 1.5f, 1f, 0, 1.5f);

            PdfPCell jueves = new PdfPCell(new Paragraph(lblJueves.getText(), fuenteArticulos));
            jueves.setHorizontalAlignment(Element.ALIGN_CENTER);
            jueves.setColspan(3);
            border(jueves, 1.5f, 1f, 0, 1.5f);

            PdfPCell viernes = new PdfPCell(new Paragraph(lblViernes.getText(), fuenteArticulos));
            viernes.setHorizontalAlignment(Element.ALIGN_CENTER);
            viernes.setColspan(3);
            border(viernes, 1.5f, 1f, 0, 1.5f);

            PdfPCell sabado = new PdfPCell(new Paragraph(lblSabado.getText(), fuenteArticulos));
            sabado.setHorizontalAlignment(Element.ALIGN_CENTER);
            sabado.setColspan(3);
            border(sabado, 1.5f, 1f, 0, 1.5f);

            PdfPCell domingo = new PdfPCell(new Paragraph(lbldomingo.getText(), fuenteArticulos));
            domingo.setHorizontalAlignment(Element.ALIGN_CENTER);
            domingo.setColspan(3);
            border(domingo, 1.5f, 1f, 0, 1.5f);

            //-----------------------------------------------------------------------------------------------
            PdfPCell l = new PdfPCell(new Paragraph("Lunes", fuenteArticulos));
            l.setHorizontalAlignment(Element.ALIGN_CENTER);
            l.setColspan(3);
            border(l, 0, 1f, 1, 1.5f);

            PdfPCell ma = new PdfPCell(new Paragraph("Martes", fuenteArticulos));
            ma.setHorizontalAlignment(Element.ALIGN_CENTER);
            ma.setColspan(3);
            border(ma, 0, 1f, 1, 1.5f);

            PdfPCell mi = new PdfPCell(new Paragraph("Miercoles", fuenteArticulos));
            mi.setHorizontalAlignment(Element.ALIGN_CENTER);
            mi.setColspan(3);
            border(mi, 0, 1f, 1, 1.5f);

            PdfPCell ju = new PdfPCell(new Paragraph("Jueves", fuenteArticulos));
            ju.setHorizontalAlignment(Element.ALIGN_CENTER);
            ju.setColspan(3);
            border(ju, 0, 1f, 1, 1.5f);

            PdfPCell v = new PdfPCell(new Paragraph("Viernes", fuenteArticulos));
            v.setHorizontalAlignment(Element.ALIGN_CENTER);
            v.setColspan(3);
            border(v, 0, 1f, 1, 1.5f);

            PdfPCell s = new PdfPCell(new Paragraph("Sabado", fuenteArticulos));
            s.setHorizontalAlignment(Element.ALIGN_CENTER);
            s.setColspan(3);
            border(s, 0, 1f, 1, 1.5f);

            PdfPCell d = new PdfPCell(new Paragraph("Domingo", fuenteArticulos));
            d.setHorizontalAlignment(Element.ALIGN_CENTER);
            d.setColspan(3);
            border(d, 0, 1f, 1, 1.5f);

            PdfPCell c6 = new PdfPCell(new Paragraph("Horas", fuenteArticulos));
            c6.setHorizontalAlignment(Element.ALIGN_CENTER);
            c6.setColspan(3);
            border(c6, 1f, 1f, 0, 1.5f);

            /////////-----------------------------------------------------------------------------+
            PdfPCell c7 = new PdfPCell(new Paragraph("Nombre", fuenteArticulos));
            c7.setHorizontalAlignment(Element.ALIGN_CENTER);
            border(c6, 0, 0, 1, 1.5f);

            PdfPCell entrada = new PdfPCell(new Paragraph("Ent.", fuenteArticulos));
            entrada.setHorizontalAlignment(Element.ALIGN_CENTER);
            border(entrada, 0, 0, 0.5f, 0.5f);

            PdfPCell salida = new PdfPCell(new Paragraph("Sal.", fuenteArticulos));
            salida.setHorizontalAlignment(Element.ALIGN_CENTER);
            border(salida, 0, 0, 0.5f, .5f);

            PdfPCell horas = new PdfPCell(new Paragraph("Hrs.", fuenteArticulos));
            horas.setHorizontalAlignment(Element.ALIGN_CENTER);
            border(horas, 0, 0, 0, 1.5f);

            PdfPCell tot = new PdfPCell(new Paragraph("Tot.", fuenteArticulos));
            tot.setHorizontalAlignment(Element.ALIGN_CENTER);
            border(tot, 1, 0, 0.5f, 0.5f);

            PdfPCell ret = new PdfPCell(new Paragraph("Ret.", fuenteArticulos));
            ret.setHorizontalAlignment(Element.ALIGN_CENTER);
            border(ret, 1, 0, 0.5f, .5f);

            PdfPCell sob = new PdfPCell(new Paragraph("Sob.", fuenteArticulos));
            sob.setHorizontalAlignment(Element.ALIGN_CENTER);
            border(sob, 1, 0, 1, 1.5f);

            tablaFechas.addCell(blanco);
            tablaFechas.addCell(blanco);
            tablaFechas.addCell(blanco);
            tablaFechas.addCell(blanco);
            tablaFechas.addCell(blanco);
            tablaFechas.addCell(blanco);
            tablaFechas.addCell(blanco);
            tablaFechas.addCell(blanco);
            tablaFechas.addCell(blanco);
            tablaFechas.addCell(blanco);
            tablaFechas.addCell(blanco);
            tablaFechas.addCell(blanco);
            tablaFechas.addCell(blanco);
            tablaFechas.addCell(blanco);
            tablaFechas.addCell(blanco);
            tablaFechas.addCell(blanco);
            tablaFechas.addCell(blanco);
            tablaFechas.addCell(blanco);
            tablaFechas.addCell(blanco);
            tablaFechas.addCell(blanco);
            tablaFechas.addCell(blanco);
            tablaFechas.addCell(blanco);
            tablaFechas.addCell(blanco);
            tablaFechas.addCell(blanco);
            tablaFechas.addCell(blanco);
            tablaFechas.addCell(blanco);

            tablaFechas.addCell(c1);
            tablaFechas.addCell(c2);
            tablaFechas.addCell(lunes);
            tablaFechas.addCell(martes);
            tablaFechas.addCell(miercoles);
            tablaFechas.addCell(jueves);
            tablaFechas.addCell(viernes);
            tablaFechas.addCell(sabado);
            tablaFechas.addCell(domingo);
            tablaFechas.addCell(c3);

            tablaFechas.addCell(l);
            tablaFechas.addCell(ma);
            tablaFechas.addCell(mi);
            tablaFechas.addCell(ju);
            tablaFechas.addCell(v);
            tablaFechas.addCell(s);
            tablaFechas.addCell(d);
            tablaFechas.addCell(c6);

            tablaFechas.addCell(c7);
            tablaFechas.addCell(entrada);
            tablaFechas.addCell(salida);
            tablaFechas.addCell(horas);
            tablaFechas.addCell(entrada);
            tablaFechas.addCell(salida);
            tablaFechas.addCell(horas);
            tablaFechas.addCell(entrada);
            tablaFechas.addCell(salida);
            tablaFechas.addCell(horas);
            tablaFechas.addCell(entrada);
            tablaFechas.addCell(salida);
            tablaFechas.addCell(horas);
            tablaFechas.addCell(entrada);
            tablaFechas.addCell(salida);
            tablaFechas.addCell(horas);
            tablaFechas.addCell(entrada);
            tablaFechas.addCell(salida);
            tablaFechas.addCell(horas);
            tablaFechas.addCell(entrada);
            tablaFechas.addCell(salida);
            tablaFechas.addCell(horas);

            tablaFechas.addCell(tot);
            tablaFechas.addCell(ret);
            tablaFechas.addCell(sob);

            //-----------------------------------------------------------------------------------------------------
            //----------------------TABLA DE ARTICULOS------------------------------------------------------------
            PdfPTable tablaArticulos = new PdfPTable(26);
            tablaArticulos.setWidthPercentage(100);
            float medidas3[] = {30, 150, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40};
            tablaArticulos.setWidths(medidas3);

            PdfPCell bla = new PdfPCell(new Paragraph("\n", fuenteArticulos));
            bla.setHorizontalAlignment(Element.ALIGN_CENTER);
            bla.setBorderWidth(0);

            int sobra = 0;
            for (int i = 0; i < Tabla1.getRowCount(); i++) {
                for (int j = 0; j < Tabla1.getColumnCount(); j++) {
                    PdfPCell cel;
                    String valor;
                    if (Tabla1.getValueAt(i, j) == null) {
                        valor = "\n";
                    } else {
                        valor = Tabla1.getValueAt(i, j).toString();
                    }
                    cel = new PdfPCell(new Paragraph(valor, fuenteArticulos));
                    cel.setBorderWidth(1f);
                    if (j == 23 || j == 24 || j == 25) {
                        cel.setBackgroundColor(BaseColor.YELLOW);
                    }
                    if (j == 4 || j == 7 || j == 10 || j == 13 || j == 16 || j == 19 || j == 22) {
                        cel.setBackgroundColor(BaseColor.LIGHT_GRAY);
                        if (Tabla1.getValueAt(i, j - 2).toString().equals("") && Tabla1.getValueAt(i, j - 1).toString().equals("")) {
                            cel.setBackgroundColor(BaseColor.RED);
                        }
                    }
                    if (j == 1) {
                        cel.setHorizontalAlignment(Element.ALIGN_CENTER);
                    }
                    if (matrizIncidencias[i][j][0] != null) {
                        if (!matrizIncidencias[i][j][2].equals("")) {
                            cel.setBackgroundColor(BaseColor.CYAN);
                        } else {
                            cel.setBackgroundColor(BaseColor.ORANGE);
                        }
                    }
                    tablaArticulos.addCell(cel);
                }
                sobra++;
            }
            if (sobra < 14) {
                for (int i = 0; i < 15 - sobra; i++) {
                    for (int j = 0; j < Tabla1.getColumnCount(); j++) {
                        PdfPCell cel;
                        String valor;
                        valor = "\n";

                        cel = new PdfPCell(new Paragraph(valor, fuenteArticulos));
                        cel.setBorderWidth(1f);

                        if (j == 1) {
                            cel.setHorizontalAlignment(Element.ALIGN_CENTER);
                        }
                        if (j == 4 || j == 7 || j == 10 || j == 13 || j == 16 || j == 19 || j == 22) {
                            cel.setBackgroundColor(BaseColor.LIGHT_GRAY);
                        }

                        tablaArticulos.addCell(cel);
                    }
                }
            }
            //----------------------------------------------------------------------------------------------------

            //-------------------------------------Tabla firmas---------------------------------------------------
            PdfPTable tablaFirmas = new PdfPTable(6);
            tablaFirmas.setWidthPercentage(100);

            PdfPCell blank = new PdfPCell(new Paragraph("\n"));
            blank.setHorizontalAlignment(Element.ALIGN_CENTER);
            border(blank, 0, 0, 0, 0);

            PdfPCell nota = new PdfPCell(new Paragraph("Nota:  El reporte debe ser enviado a mas tardar Martes a las 12:00 pm al correo: Contabilidad01@si3i.com en formato PDF\n"
                    + "El responsable de la informacion presentada es el encargado", fuenteArticulos));
            nota.setHorizontalAlignment(Element.ALIGN_CENTER);
            nota.setVerticalAlignment(5);
            border(nota, 0, 1.5f, 1.5f, 1.5f);
            nota.setRowspan(4);
            nota.setColspan(3);

            PdfPCell firmaSuper = new PdfPCell(new Paragraph("Firma Supervisor", fuenteArticulos));
            firmaSuper.setHorizontalAlignment(Element.ALIGN_CENTER);
            border(firmaSuper, 1f, 1.5f, 0, 0);

            PdfPCell firmaGerente = new PdfPCell(new Paragraph("Firma Gerente", fuenteArticulos));
            firmaGerente.setHorizontalAlignment(Element.ALIGN_CENTER);
            border(firmaGerente, 1f, 1.5f, 0, 0);

            border(blank, 0, 0, 1.5f, 0);
            tablaFirmas.addCell(blank);
            border(blank, 0, 0, 0, 0);
            tablaFirmas.addCell(blank);
            tablaFirmas.addCell(blank);
            tablaFirmas.addCell(nota);

            border(blank, 0, 1.5f, 1.5f, 0);
            tablaFirmas.addCell(blank);
            tablaFirmas.addCell(firmaSuper);
            border(blank, 0, 1.5f, 0, 0);
            tablaFirmas.addCell(blank);

            //------------------------------------------------------------------------
            border(blank, 0, 0, 1.5f, 0);
            tablaFirmas.addCell(blank);
            border(blank, 0, 0, 0, 0);
            tablaFirmas.addCell(blank);
            tablaFirmas.addCell(blank);

            border(blank, 0, 1.5f, 1.5f, 0);
            tablaFirmas.addCell(blank);
            tablaFirmas.addCell(firmaGerente);
            border(blank, 0, 1.5f, 0, 0f);
            tablaFirmas.addCell(blank);

            //------------------------INCIDENCIAS-----------------------------------
            PdfPTable tablaIncidencias = new PdfPTable(4);
            tablaIncidencias.setWidthPercentage(100);
            float med[] = {50, 100, 50, 50};
            tablaIncidencias.setWidths(med);
            BaseColor naranja = new BaseColor(255, 94, 8);

            PdfPCell nombre = new PdfPCell(new Phrase("Nombre", fuenteIncidenciasCabecera));
            nombre.setHorizontalAlignment(Element.ALIGN_CENTER);
            nombre.setBorder(0);
            nombre.setBackgroundColor(naranja);

            PdfPCell incidencia = new PdfPCell(new Phrase("Incidencia", fuenteIncidenciasCabecera));
            incidencia.setHorizontalAlignment(Element.ALIGN_CENTER);
            incidencia.setBorder(0);
            incidencia.setBackgroundColor(naranja);

            PdfPCell horaCambiada = new PdfPCell(new Phrase("Hora cambio", fuenteIncidenciasCabecera));
            horaCambiada.setHorizontalAlignment(Element.ALIGN_CENTER);
            horaCambiada.setBorder(0);
            horaCambiada.setBackgroundColor(naranja);

            PdfPCell horaOriginal = new PdfPCell(new Phrase("Hora Original", fuenteIncidenciasCabecera));
            horaOriginal.setHorizontalAlignment(Element.ALIGN_CENTER);
            horaOriginal.setBorder(0);
            horaOriginal.setBackgroundColor(naranja);

            blank = new PdfPCell(new Phrase(" "));
            border(blank, 0, 0, 0, 0);

            co1 = new PdfPCell(new Paragraph("Reporte de incidencias", fuente4));
            co1.setBorder(0);
            co1.setHorizontalAlignment(Element.ALIGN_CENTER);
            co1.setColspan(4);
            tablaIncidencias.addCell(co1);
            tablaIncidencias.addCell(blank);
            tablaIncidencias.addCell(blank);
            tablaIncidencias.addCell(blank);
            tablaIncidencias.addCell(blank);
            tablaIncidencias.addCell(nombre);
            tablaIncidencias.addCell(incidencia);
            tablaIncidencias.addCell(horaCambiada);
            tablaIncidencias.addCell(horaOriginal);

            boolean isTabled = false;

            boolean band;
            for (int i = 0; i < matrizIncidencias.length; i++) {
                band = true;
                for (int j = 0; j < matrizIncidencias[i].length; j++) {
                    if (matrizIncidencias[i][j][0] != null) {
                        String entrada1;
                        if (matrizIncidencias[i][j][2].equals("")) {
                            if (j % 3 == 0) {
                                entrada1 = "NCS";
                            } else {
                                entrada1 = "NCE";
                            }
                        } else {
                            entrada1 = matrizIncidencias[i][j][2].toString();
                        }
                        isTabled = true;
                        String num = " ";
                        if (band) {
                            num = Tabla1.getValueAt(i, 1).toString();
                            band = false;
                        }
                        String empleado = num;
                        PdfPCell cell = new PdfPCell(new Phrase(empleado, fuenteIncidenciasFila));
                        border(cell, 0, .5f, 0, 0);
                        PdfPCell cell2 = new PdfPCell(new Phrase(matrizIncidencias[i][j][0].toString(), fuenteIncidenciasFila));
                        border(cell2, 0, .5f, 0, 0);
                        PdfPCell cell3 = new PdfPCell(new Phrase(matrizIncidencias[i][j][1].toString(), fuenteIncidenciasFila));
                        border(cell3, 0, .5f, 0, 0);
                        cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
                        PdfPCell cell4 = new PdfPCell(new Phrase(entrada1, fuenteIncidenciasFila));
                        border(cell4, 0, .5f, 0, 0);
                        cell4.setHorizontalAlignment(Element.ALIGN_CENTER);

                        tablaIncidencias.addCell(cell);
                        tablaIncidencias.addCell(cell2);
                        tablaIncidencias.addCell(cell3);
                        tablaIncidencias.addCell(cell4);
                    }
                }
            }

            //---------------------DOCUMENTOS PARA AGREGAR-----------------------------------
            
            Image img = Image.getInstance("src/Img/siyms.png");
            img.setAbsolutePosition(25, 500);
            img.scaleAbsolute(new Rectangle(140, 50));

            document.add(img);
            document.add(tabla1);
            document.add(tablaCliente);
            document.add(tablaFechas);
            document.add(tablaArticulos);
            document.add(tablaFirmas);
            if (isTabled) {
                document.newPage();
                document.add(tablaIncidencias);
            }
            //-------------------------------------------------------------------------------
            document.close();
            Desktop.getDesktop().open(new File(ruta));

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "ERROR: " + e, "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void agregarDias(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");
        Date dat;
        try {
            dat = sdf.parse(date);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(dat);
            calendar.add(Calendar.DAY_OF_YEAR, 0);
            lblLunes.setText(date);
            lblLunes.setText(sdf2.format(calendar.getTime()));
            calendar.add(Calendar.DAY_OF_YEAR, 1);
            lblMartes.setText(sdf2.format(calendar.getTime()));
            calendar.add(Calendar.DAY_OF_YEAR, 1);
            lblMiercoles.setText(sdf2.format(calendar.getTime()));
            calendar.add(Calendar.DAY_OF_YEAR, 1);
            lblJueves.setText(sdf2.format(calendar.getTime()));
            calendar.add(Calendar.DAY_OF_YEAR, 1);
            lblViernes.setText(sdf2.format(calendar.getTime()));
            calendar.add(Calendar.DAY_OF_YEAR, 1);
            lblSabado.setText(sdf2.format(calendar.getTime()));
            calendar.add(Calendar.DAY_OF_YEAR, 1);
            lbldomingo.setText(sdf2.format(calendar.getTime()));

        } catch (ParseException ex) {
            Logger.getLogger(Checador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getSupervisor() {
        String campo = null;
        try {
            Connection con;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            String sql = "select em.Administrador, reg.Supervisor from empleadoscheck as em "
                    + "inner join registroempleados as reg "
                    + "on em.numempleado = reg.numempleado "
                    + "where em.NumEmpleado like '" + numEmpleado + "'";
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                supervisor = rs.getString("Supervisor");
                admin = rs.getBoolean("administrador");
            }
            campo = supervisor;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "ERROR: " + e, "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        return campo;
    }

    public void addConfig() {
        try {
            Statement st = con.createStatement();
            String sql = "select * from empleadoscheck where NumSupervisor like '" + numEmpleado + "'";
            if (numEmpleado.equals("61") || getSupervisor().equals("GERENCIA")) {
                sql = "select * from empleadoscheck WHERE Activo like true";
            }
            ResultSet rs = st.executeQuery(sql);
            int cont = 0;
            while (rs.next()) {
                cont++;
            }
            config = new confEmpleado[cont];
            Statement st2 = con.createStatement();
            String sql2 = "select * from empleadoscheck where NumSupervisor like '" + numEmpleado + "'";
            boolean redondeo = false;
            if (numEmpleado.equals("61") || getSupervisor().equals("GERENCIA")) {
                sql2 = "select * from empleadoscheck WHERE Activo like true";
                redondeo = true;
            }
            ResultSet rs2 = st2.executeQuery(sql2);
            int i = 0;
            while (rs2.next()) {
                String nombre = rs2.getString("Nombre");
                String numero = rs2.getString("NumEmpleado");
                String entrada = rs2.getString("Entrada");
                String salida = rs2.getString("Salida");
                String horadiaria = rs2.getString("HorasDiarias");
                String turno = rs2.getString("Turno");
                String horasabado = rs2.getString("HoraSabado");
                String entradasabado = rs2.getString("EntradaSabado");
                String salidasabado = rs2.getString("SalidaSabado");
                String totalHoras = rs2.getString("totalHoras");
                boolean automatico = rs2.getBoolean("Automatico");
                config[i] = new confEmpleado(entrada, salida, horadiaria, turno, horasabado, numero, nombre,
                        entradasabado, salidasabado, redondeo, totalHoras, automatico);
                i++;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "ERROR: " + e, "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void addSemana() {
        try {
            Statement st = con.createStatement();
            String sql = "select * from semanas order by Id desc";
            ResultSet rs = st.executeQuery(sql);
            cmbSemana.removeAllItems();
            cmbSemana.addItem("SEMANA NO.");
            fecha = new Stack<>();
            fecha.push("");
            while (rs.next()) {
                cmbSemana.addItem(rs.getString("NumSemana"));
                fecha.push(rs.getString("Inicio"));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "ERROR: " + ex, "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    public boolean[] canEdit() {
        boolean[] can1 = new boolean[]{
            false, false, true, true, false, true, true, false, true, true, false, true, true, false, true, true, false, true, true, false, true, true, false, false, false, false
        };

        boolean[] can2 = new boolean[]{
            false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false
        };

        if (lblSemana.getText().equals("EDITADO")) {
            return can2;
        } else {
            return can1;
        }

    }

    public void limpiarTabla() {
        addConfig();
        color = new ColorChecador();
        color.setConfig(config);
        color.setMatriz(matrizIncidencias);
        Tabla1 = color;
        Tabla1.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{},
                new String[]{
                    "id", "numero", "entrada", "salida", "horas", "entrada", "salida", "horas", "entrada", "salida", "horas", "entrada", "salida", "horas", "entrada",
                     "salida", "horas", "entrada", "salida", "horas", "entrada", "salida", "horas", "total", "retraso", "sobretrabajo"
                }
        ) {
            boolean[] canEdit = canEdit();

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
        Tabla1.setComponentPopupMenu(jPopupMenu1);
        Tabla1.setColorBorderRows(new java.awt.Color(255, 255, 255));
        Tabla1.setTableHeader(null);
        jScrollPane2.setViewportView(Tabla1);
        JViewport scroll = (JViewport) Tabla1.getParent();
        int ancho = ((this.getWidth() - panelRound1.getWidth()) / 10);
        if (Tabla1.getColumnModel().getColumnCount() > 0) {
            Tabla1.getColumnModel().getColumn(0).setPreferredWidth(ancho);
            Tabla1.getColumnModel().getColumn(0).setMinWidth(ancho);
            Tabla1.getColumnModel().getColumn(0).setMaxWidth(ancho);
            Tabla1.getColumnModel().getColumn(1).setPreferredWidth(ancho);
            Tabla1.getColumnModel().getColumn(1).setMinWidth(ancho);
            Tabla1.getColumnModel().getColumn(1).setMaxWidth(ancho);
        }
    }

    public void addKeyPressed() {
        KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
        manager.addKeyEventDispatcher(new KeyEventDispatcher() {
            @Override
            public boolean dispatchKeyEvent(KeyEvent e) {
                if (Tabla1.isFocusOwner() || Tabla1.isEditing()) {
                    boolean band = Tabla1.isEditing();
                    if (e.getID() == KeyEvent.KEY_PRESSED && (e.getKeyCode() == KeyEvent.VK_ENTER || (e.getKeyCode() == KeyEvent.VK_UP) || (e.getKeyCode() == KeyEvent.VK_DOWN)
                            || (e.getKeyCode() == KeyEvent.VK_LEFT && !band) || (e.getKeyCode() == KeyEvent.VK_RIGHT && !band))) {
                        if (Tabla1.isEditing()) {
                            Tabla1.getCellEditor().stopCellEditing();
                        }
                        int row = Tabla1.getSelectedRow();
                        int col = Tabla1.getSelectedColumn();
                        String nombre = Tabla1.getValueAt(row, 1).toString();
                        String hora;
                        String comentarios;
                        String horaVieja;
                        try {
                            hora = Tabla1.getValueAt(row, col).toString();
                        } catch (Exception ex) {
                            hora = "00:00";
                        }
                        try {
                            horaVieja = (String) matrizTabla[row][col];
                        } catch (Exception ex) {
                            horaVieja = "00:00";
                        }

                        if (!horaVieja.equals(hora)) {
                            incidencias = new Incidencias(frame, true, horaVieja, hora, nombre);
                            incidencias.setLocationRelativeTo(null);
                            comentarios = incidencias.getIncidencia();
                            if (!comentarios.equals("")) {
                                matrizIncidencias[row][col][0] = comentarios;
                                matrizIncidencias[row][col][1] = hora;
                                matrizIncidencias[row][col][2] = horaVieja;
                                if (comentarios.equals("")) {
                                    Tabla1.setValueAt("", row, col);
                                }
                                color.setMatriz(matrizIncidencias);
                            } else {
                                Tabla1.setValueAt(horaVieja, row, col);
                            }
                        }
                    }
                    return false;
                }
                return false;
            }
        });
    }

    public boolean verificarColumna(int col) {
        int numeros[] = {2, 3, 5, 6, 8, 9, 11, 12, 14, 15, 17, 18, 20, 21};
        boolean band = false;
        for (int i = 0; i < numeros.length; i++) {
            if (numeros[i] == col) {
                band = true;
            }
        }
        return band;
    }

    public String evaluarNull(Object texto) {
        try {
            return String.valueOf(texto);
        } catch (Exception e) {
            return "";
        }
    }

    public Checador(String numEmpleado) {
        initComponents();
        this.numEmpleado = numEmpleado;
        try {
            con = con1.getConnection();
            addConfig();
            ((javax.swing.plaf.basic.BasicInternalFrameUI) this.getUI()).setNorthPane(null);
            addSemana();
            String sup = getSupervisor();
            if (sup != null && sup.equals("GERENCIA")) {
                btnSubir1.setEnabled(true);
                tgIncidencias.setVisible(true);
                lblIncidencias.setVisible(true);
            } else {
                tgIncidencias.setVisible(false);
                lblIncidencias.setVisible(false);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "No tienes acceso para ingresar a este modulo " + ex, "Error", JOptionPane.ERROR_MESSAGE);
            this.dispose();
        }
        limpiarTabla();
        addKeyPressed();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupMenu1 = new javax.swing.JPopupMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        agregarIncidencia = new javax.swing.JMenuItem();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jPanel13 = new javax.swing.JPanel();
        btnSalir = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        panelRound1 = new scrollPane.PanelRound();
        btnVerMisEmpleados = new rojeru_san.rsbutton.RSButtonRoundRipple();
        rSButtonRoundRipple3 = new rojeru_san.rsbutton.RSButtonRoundRipple();
        cmbSemana = new RSMaterialComponent.RSComboBoxMaterial();
        jLabel13 = new javax.swing.JLabel();
        lblEstado = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        lblSemana = new javax.swing.JLabel();
        btnSubir1 = new rojeru_san.rsbutton.RSButtonRoundRipple();
        tgRedondear = new toggle.ToggleButton();
        jLabel24 = new javax.swing.JLabel();
        lblIncidencias = new javax.swing.JLabel();
        tgIncidencias = new toggle.ToggleButton();
        jPanel5 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        lblEmpleado = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        lblLunes = new javax.swing.JLabel();
        lblMartes = new javax.swing.JLabel();
        lblMiercoles = new javax.swing.JLabel();
        lblJueves = new javax.swing.JLabel();
        lblViernes = new javax.swing.JLabel();
        lblSabado = new javax.swing.JLabel();
        lbldomingo = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jPanel14 = new javax.swing.JPanel();
        jLabel43 = new javax.swing.JLabel();
        jLabel44 = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        jPanel15 = new javax.swing.JPanel();
        jLabel46 = new javax.swing.JLabel();
        jLabel47 = new javax.swing.JLabel();
        jLabel48 = new javax.swing.JLabel();
        jPanel16 = new javax.swing.JPanel();
        jLabel49 = new javax.swing.JLabel();
        jLabel50 = new javax.swing.JLabel();
        jLabel51 = new javax.swing.JLabel();
        jPanel17 = new javax.swing.JPanel();
        jLabel52 = new javax.swing.JLabel();
        jLabel53 = new javax.swing.JLabel();
        jLabel54 = new javax.swing.JLabel();
        jPanel18 = new javax.swing.JPanel();
        jLabel55 = new javax.swing.JLabel();
        jLabel56 = new javax.swing.JLabel();
        jLabel57 = new javax.swing.JLabel();
        jPanel19 = new javax.swing.JPanel();
        jLabel58 = new javax.swing.JLabel();
        jLabel59 = new javax.swing.JLabel();
        jLabel60 = new javax.swing.JLabel();
        jPanel20 = new javax.swing.JPanel();
        jLabel61 = new javax.swing.JLabel();
        jLabel62 = new javax.swing.JLabel();
        jLabel63 = new javax.swing.JLabel();
        jPanel21 = new javax.swing.JPanel();
        jLabel64 = new javax.swing.JLabel();
        jLabel65 = new javax.swing.JLabel();
        jLabel66 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        Tabla1 = new ColorChecador();

        jMenuItem1.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        jMenuItem1.setText("Menu         ");
        jMenuItem1.setEnabled(false);
        jPopupMenu1.add(jMenuItem1);

        agregarIncidencia.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        agregarIncidencia.setText("Agregar Incidencia                                                     ");
        agregarIncidencia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                agregarIncidenciaActionPerformed(evt);
            }
        });
        jPopupMenu1.add(agregarIncidencia);

        setBorder(null);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new java.awt.BorderLayout());

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new java.awt.BorderLayout());

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jLabel15.setFont(new java.awt.Font("Lexend", 1, 24)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(0, 102, 204));
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel15.setText("Asistencia");
        jPanel3.add(jLabel15);

        jPanel2.add(jPanel3, java.awt.BorderLayout.CENTER);

        jPanel13.setBackground(new java.awt.Color(255, 255, 255));

        btnSalir.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("  X  ");
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

        jPanel1.add(jPanel2, java.awt.BorderLayout.PAGE_START);

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setLayout(new java.awt.BorderLayout(10, 0));

        panelRound1.setBackground(new java.awt.Color(51, 51, 51));
        panelRound1.setRoundBottomLeft(100);
        panelRound1.setRoundBottomRight(100);
        panelRound1.setRoundTopLeft(100);
        panelRound1.setRoundTopRight(100);

        btnVerMisEmpleados.setText("Ver mis empleados");
        btnVerMisEmpleados.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVerMisEmpleadosActionPerformed(evt);
            }
        });

        rSButtonRoundRipple3.setText("Config. empleados");
        rSButtonRoundRipple3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rSButtonRoundRipple3ActionPerformed(evt);
            }
        });

        cmbSemana.setBackground(new java.awt.Color(51, 51, 51));
        cmbSemana.setForeground(new java.awt.Color(255, 255, 255));
        cmbSemana.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "SEMANA NO.", "1", "2", "3", "4", "5", "6" }));
        cmbSemana.setColorMaterial(new java.awt.Color(255, 255, 255));
        cmbSemana.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                cmbSemanaPopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
        });
        cmbSemana.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbSemanaActionPerformed(evt);
            }
        });
        cmbSemana.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                cmbSemanaPropertyChange(evt);
            }
        });
        cmbSemana.addVetoableChangeListener(new java.beans.VetoableChangeListener() {
            public void vetoableChange(java.beans.PropertyChangeEvent evt)throws java.beans.PropertyVetoException {
                cmbSemanaVetoableChange(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("Estado: ");

        lblEstado.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        lblEstado.setForeground(new java.awt.Color(255, 255, 255));
        lblEstado.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblEstado.setText("SIN SELECCIONAR");

        jLabel23.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(255, 255, 255));
        jLabel23.setText("Semana:");

        lblSemana.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        lblSemana.setForeground(new java.awt.Color(255, 255, 255));
        lblSemana.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblSemana.setText("SIN SELECCIONAR");

        btnSubir1.setBackground(new java.awt.Color(51, 51, 51));
        btnSubir1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/pdf.png"))); // NOI18N
        btnSubir1.setText("Descargar Pdf");
        btnSubir1.setColorHover(new java.awt.Color(204, 0, 0));
        btnSubir1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSubir1ActionPerformed(evt);
            }
        });

        tgRedondear.setForeground(new java.awt.Color(0, 112, 192));

        jLabel24.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(255, 255, 255));
        jLabel24.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel24.setText("Redondear:");

        lblIncidencias.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        lblIncidencias.setForeground(new java.awt.Color(255, 255, 255));
        lblIncidencias.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblIncidencias.setText("Mostrar sin incidencias:");

        tgIncidencias.setForeground(new java.awt.Color(0, 112, 192));
        tgIncidencias.setSelected(true);

        javax.swing.GroupLayout panelRound1Layout = new javax.swing.GroupLayout(panelRound1);
        panelRound1.setLayout(panelRound1Layout);
        panelRound1Layout.setHorizontalGroup(
            panelRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRound1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelRound1Layout.createSequentialGroup()
                        .addGroup(panelRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel23))
                        .addGroup(panelRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelRound1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lblSemana, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(panelRound1Layout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addComponent(lblEstado, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelRound1Layout.createSequentialGroup()
                        .addGap(0, 6, Short.MAX_VALUE)
                        .addComponent(cmbSemana, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lblIncidencias, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(panelRound1Layout.createSequentialGroup()
                        .addGroup(panelRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelRound1Layout.createSequentialGroup()
                                .addGap(64, 64, 64)
                                .addComponent(tgIncidencias, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panelRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(btnVerMisEmpleados, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(rSButtonRoundRipple3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnSubir1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel24, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(panelRound1Layout.createSequentialGroup()
                                    .addGap(64, 64, 64)
                                    .addComponent(tgRedondear, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(19, 19, 19))))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        panelRound1Layout.setVerticalGroup(
            panelRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRound1Layout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addComponent(btnVerMisEmpleados, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(rSButtonRoundRipple3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(cmbSemana, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(panelRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(lblEstado, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel23)
                    .addComponent(lblSemana, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jLabel24)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(tgRedondear, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblIncidencias)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(tgIncidencias, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 207, Short.MAX_VALUE)
                .addComponent(btnSubir1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(51, 51, 51))
        );

        jPanel4.add(panelRound1, java.awt.BorderLayout.LINE_START);

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setLayout(new java.awt.BorderLayout());

        jPanel7.setLayout(new java.awt.BorderLayout());

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));
        jPanel9.setLayout(new java.awt.GridLayout(1, 10));

        lblEmpleado.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        lblEmpleado.setForeground(new java.awt.Color(102, 102, 102));
        lblEmpleado.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jPanel9.add(lblEmpleado);

        jLabel14.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(102, 102, 102));
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jPanel9.add(jLabel14);

        lblLunes.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        lblLunes.setForeground(new java.awt.Color(102, 102, 102));
        lblLunes.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblLunes.setText("  /  /  /  ");
        jPanel9.add(lblLunes);

        lblMartes.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        lblMartes.setForeground(new java.awt.Color(102, 102, 102));
        lblMartes.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblMartes.setText("  /  /  /  ");
        jPanel9.add(lblMartes);

        lblMiercoles.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        lblMiercoles.setForeground(new java.awt.Color(102, 102, 102));
        lblMiercoles.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblMiercoles.setText("  /  /  /  ");
        jPanel9.add(lblMiercoles);

        lblJueves.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        lblJueves.setForeground(new java.awt.Color(102, 102, 102));
        lblJueves.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblJueves.setText("  /  /  /  ");
        jPanel9.add(lblJueves);

        lblViernes.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        lblViernes.setForeground(new java.awt.Color(102, 102, 102));
        lblViernes.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblViernes.setText("  /  /  /  ");
        jPanel9.add(lblViernes);

        lblSabado.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        lblSabado.setForeground(new java.awt.Color(102, 102, 102));
        lblSabado.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblSabado.setText("  /  /  /  ");
        jPanel9.add(lblSabado);

        lbldomingo.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        lbldomingo.setForeground(new java.awt.Color(102, 102, 102));
        lbldomingo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbldomingo.setText("  /  /  /  ");
        jPanel9.add(lbldomingo);

        jLabel22.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(102, 102, 102));
        jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel22.setText("Total");
        jPanel9.add(jLabel22);

        jPanel7.add(jPanel9, java.awt.BorderLayout.PAGE_START);

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));
        jPanel8.setLayout(new java.awt.GridLayout(1, 10));

        jLabel2.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(102, 102, 102));
        jLabel2.setText(" ");
        jPanel8.add(jLabel2);

        jLabel3.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(102, 102, 102));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Empleado");
        jPanel8.add(jLabel3);

        jLabel4.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(102, 102, 102));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Lunes");
        jPanel8.add(jLabel4);

        jLabel5.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(102, 102, 102));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Martes");
        jPanel8.add(jLabel5);

        jLabel6.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(102, 102, 102));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Miercoles");
        jPanel8.add(jLabel6);

        jLabel7.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(102, 102, 102));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("Jueves");
        jPanel8.add(jLabel7);

        jLabel8.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(102, 102, 102));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("Viernes");
        jPanel8.add(jLabel8);

        jLabel10.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(102, 102, 102));
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("Sabado");
        jPanel8.add(jLabel10);

        jLabel11.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(102, 102, 102));
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("Domingo");
        jPanel8.add(jLabel11);

        jLabel12.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(102, 102, 102));
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setText("Horas");
        jPanel8.add(jLabel12);

        jPanel7.add(jPanel8, java.awt.BorderLayout.CENTER);

        jPanel12.setBackground(new java.awt.Color(255, 255, 255));
        jPanel12.setLayout(new java.awt.GridLayout(1, 10));

        jLabel33.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel33.setForeground(new java.awt.Color(102, 102, 102));
        jLabel33.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel33.setText("#Empleado");
        jPanel12.add(jLabel33);

        jLabel34.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel34.setForeground(new java.awt.Color(102, 102, 102));
        jLabel34.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel34.setText("Nombre");
        jPanel12.add(jLabel34);

        jPanel14.setBackground(new java.awt.Color(255, 255, 255));
        jPanel14.setLayout(new java.awt.GridLayout(1, 3));

        jLabel43.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel43.setForeground(new java.awt.Color(102, 102, 102));
        jLabel43.setText("Ent.");
        jLabel43.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(204, 204, 204)));
        jPanel14.add(jLabel43);

        jLabel44.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel44.setForeground(new java.awt.Color(102, 102, 102));
        jLabel44.setText("Sal.");
        jLabel44.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(204, 204, 204)));
        jPanel14.add(jLabel44);

        jLabel45.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel45.setForeground(new java.awt.Color(102, 102, 102));
        jLabel45.setText("Hrs.");
        jLabel45.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(204, 204, 204)));
        jPanel14.add(jLabel45);

        jPanel12.add(jPanel14);

        jPanel15.setBackground(new java.awt.Color(255, 255, 255));
        jPanel15.setLayout(new java.awt.GridLayout(1, 3));

        jLabel46.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel46.setForeground(new java.awt.Color(102, 102, 102));
        jLabel46.setText("Ent.");
        jLabel46.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(204, 204, 204)));
        jPanel15.add(jLabel46);

        jLabel47.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel47.setForeground(new java.awt.Color(102, 102, 102));
        jLabel47.setText("Sal.");
        jLabel47.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(204, 204, 204)));
        jPanel15.add(jLabel47);

        jLabel48.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel48.setForeground(new java.awt.Color(102, 102, 102));
        jLabel48.setText("Hrs.");
        jLabel48.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(204, 204, 204)));
        jPanel15.add(jLabel48);

        jPanel12.add(jPanel15);

        jPanel16.setBackground(new java.awt.Color(255, 255, 255));
        jPanel16.setLayout(new java.awt.GridLayout(1, 3));

        jLabel49.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel49.setForeground(new java.awt.Color(102, 102, 102));
        jLabel49.setText("Ent.");
        jLabel49.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(204, 204, 204)));
        jPanel16.add(jLabel49);

        jLabel50.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel50.setForeground(new java.awt.Color(102, 102, 102));
        jLabel50.setText("Sal.");
        jLabel50.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(204, 204, 204)));
        jPanel16.add(jLabel50);

        jLabel51.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel51.setForeground(new java.awt.Color(102, 102, 102));
        jLabel51.setText("Hrs.");
        jLabel51.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(204, 204, 204)));
        jPanel16.add(jLabel51);

        jPanel12.add(jPanel16);

        jPanel17.setBackground(new java.awt.Color(255, 255, 255));
        jPanel17.setLayout(new java.awt.GridLayout(1, 3));

        jLabel52.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel52.setForeground(new java.awt.Color(102, 102, 102));
        jLabel52.setText("Ent.");
        jLabel52.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(204, 204, 204)));
        jPanel17.add(jLabel52);

        jLabel53.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel53.setForeground(new java.awt.Color(102, 102, 102));
        jLabel53.setText("Sal.");
        jLabel53.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(204, 204, 204)));
        jPanel17.add(jLabel53);

        jLabel54.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel54.setForeground(new java.awt.Color(102, 102, 102));
        jLabel54.setText("Hrs.");
        jLabel54.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(204, 204, 204)));
        jPanel17.add(jLabel54);

        jPanel12.add(jPanel17);

        jPanel18.setBackground(new java.awt.Color(255, 255, 255));
        jPanel18.setLayout(new java.awt.GridLayout(1, 3));

        jLabel55.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel55.setForeground(new java.awt.Color(102, 102, 102));
        jLabel55.setText("Ent.");
        jLabel55.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(204, 204, 204)));
        jPanel18.add(jLabel55);

        jLabel56.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel56.setForeground(new java.awt.Color(102, 102, 102));
        jLabel56.setText("Sal.");
        jLabel56.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(204, 204, 204)));
        jPanel18.add(jLabel56);

        jLabel57.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel57.setForeground(new java.awt.Color(102, 102, 102));
        jLabel57.setText("Hrs.");
        jLabel57.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(204, 204, 204)));
        jPanel18.add(jLabel57);

        jPanel12.add(jPanel18);

        jPanel19.setBackground(new java.awt.Color(255, 255, 255));
        jPanel19.setLayout(new java.awt.GridLayout(1, 3));

        jLabel58.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel58.setForeground(new java.awt.Color(102, 102, 102));
        jLabel58.setText("Ent.");
        jLabel58.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(204, 204, 204)));
        jPanel19.add(jLabel58);

        jLabel59.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel59.setForeground(new java.awt.Color(102, 102, 102));
        jLabel59.setText("Sal.");
        jLabel59.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(204, 204, 204)));
        jPanel19.add(jLabel59);

        jLabel60.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel60.setForeground(new java.awt.Color(102, 102, 102));
        jLabel60.setText("Hrs.");
        jLabel60.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(204, 204, 204)));
        jPanel19.add(jLabel60);

        jPanel12.add(jPanel19);

        jPanel20.setBackground(new java.awt.Color(255, 255, 255));
        jPanel20.setLayout(new java.awt.GridLayout(1, 3));

        jLabel61.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel61.setForeground(new java.awt.Color(102, 102, 102));
        jLabel61.setText("Ent.");
        jLabel61.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(204, 204, 204)));
        jPanel20.add(jLabel61);

        jLabel62.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel62.setForeground(new java.awt.Color(102, 102, 102));
        jLabel62.setText("Sal.");
        jLabel62.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(204, 204, 204)));
        jPanel20.add(jLabel62);

        jLabel63.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel63.setForeground(new java.awt.Color(102, 102, 102));
        jLabel63.setText("Hrs.");
        jLabel63.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(204, 204, 204)));
        jPanel20.add(jLabel63);

        jPanel12.add(jPanel20);

        jPanel21.setBackground(new java.awt.Color(255, 255, 255));
        jPanel21.setLayout(new java.awt.GridLayout(1, 3));

        jLabel64.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel64.setForeground(new java.awt.Color(102, 102, 102));
        jLabel64.setText("Tot.");
        jLabel64.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(204, 204, 204)));
        jPanel21.add(jLabel64);

        jLabel65.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel65.setForeground(new java.awt.Color(102, 102, 102));
        jLabel65.setText("Ret.");
        jLabel65.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(204, 204, 204)));
        jPanel21.add(jLabel65);

        jLabel66.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel66.setForeground(new java.awt.Color(102, 102, 102));
        jLabel66.setText("STr.");
        jLabel66.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(204, 204, 204)));
        jPanel21.add(jLabel66);

        jPanel12.add(jPanel21);

        jPanel7.add(jPanel12, java.awt.BorderLayout.SOUTH);

        jPanel5.add(jPanel7, java.awt.BorderLayout.PAGE_START);

        jPanel10.setBackground(new java.awt.Color(255, 255, 255));
        jPanel10.setLayout(new java.awt.BorderLayout());

        Tabla1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "Title 5", "Title 6", "Title 7", "Title 8", "Title 9", "Title 10", "Title 11", "Title 12", "Title 13", "Title 14", "Title 15", "Title 16", "Title 17", "Title 18", "Title 19", "Title 20", "Title 21", "Title 22", "Title 23", "Title 24", "Title 25", "Title 26", "Title 27", "Title 28", "Title 29", "Title 30"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                true, false, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        Tabla1.setColorBorderRows(new java.awt.Color(255, 255, 255));
        Tabla1.setComponentPopupMenu(jPopupMenu1);
        Tabla1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Tabla1MouseClicked(evt);
            }
        });
        Tabla1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Tabla1KeyPressed(evt);
            }
        });
        jScrollPane2.setViewportView(Tabla1);
        if (Tabla1.getColumnModel().getColumnCount() > 0) {
            Tabla1.getColumnModel().getColumn(27).setMinWidth(100);
            Tabla1.getColumnModel().getColumn(27).setPreferredWidth(100);
            Tabla1.getColumnModel().getColumn(27).setMaxWidth(100);
        }

        jPanel10.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        jPanel5.add(jPanel10, java.awt.BorderLayout.CENTER);

        jPanel4.add(jPanel5, java.awt.BorderLayout.CENTER);

        jPanel1.add(jPanel4, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseClicked
        dispose();
    }//GEN-LAST:event_jLabel1MouseClicked

    private void jLabel1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseEntered
        btnSalir.setBackground(java.awt.Color.red);
    }//GEN-LAST:event_jLabel1MouseEntered

    private void jLabel1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseExited
        btnSalir.setBackground(java.awt.Color.white);
    }//GEN-LAST:event_jLabel1MouseExited

    private void btnVerMisEmpleadosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVerMisEmpleadosActionPerformed
        if (cmbSemana.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(this, "DEBES SELECCIONAR UN NUMERO DE SEMANA", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
        } else {
            try {
                limpiarTabla();
                Connection con;
                Conexion con1 = new Conexion();
                con = con1.getConnection();
                Statement st = con.createStatement();
                String sql = "select * from dias where NumSemana like '" + cmbSemana.getSelectedItem() + "' and Inicio like '" + fecha.get(cmbSemana.getSelectedIndex()) + "' order by NumEmpleado";
                ResultSet rs = st.executeQuery(sql);
                String datos[] = new String[27];
                DefaultTableModel miModelo = (DefaultTableModel) Tabla1.getModel();
                int cont = 0;
                while (rs.next()) {
                    String num = rs.getString("NumEmpleado");
                    boolean band = false;
                    int nu = 0;

                    for (int i = 0; i < config.length; i++) {
                        if (config[i].getNumEmpleado().equals(num)) {
                            band = true;
                            nu = i;
                            break;
                        }
                    }
                    cont++;
                    if (band) {
                        datos[0] = config[nu].getNumEmpleado();
                        datos[1] = config[nu].getNombre();
                        datos[2] = rs.getString("Lunes");
                        datos[2] = getTiempo(datos[2]);

                        datos[3] = rs.getString("SLunes");
                        datos[3] = getTiempo(datos[3]);

                        datos[4] = "";
                        datos[5] = rs.getString("Martes");
                        datos[5] = getTiempo(datos[5]);

                        datos[6] = rs.getString("SMartes");
                        datos[6] = getTiempo(datos[6]);

                        datos[7] = "";
                        datos[8] = rs.getString("Miercoles");
                        datos[8] = getTiempo(datos[8]);

                        datos[9] = rs.getString("SMiercoles");
                        datos[9] = getTiempo(datos[9]);

                        datos[10] = "";
                        datos[11] = rs.getString("Jueves");
                        datos[11] = getTiempo(datos[11]);

                        datos[12] = rs.getString("SJueves");
                        datos[12] = getTiempo(datos[12]);

                        datos[13] = "";
                        datos[14] = rs.getString("Viernes");
                        datos[14] = getTiempo(datos[14]);

                        datos[15] = rs.getString("SViernes");
                        datos[15] = getTiempo(datos[15]);

                        datos[16] = "";
                        datos[17] = rs.getString("Sabado");
                        datos[17] = getTiempo(datos[17]);

                        datos[18] = rs.getString("SSabado");
                        datos[18] = getTiempo(datos[18]);

                        datos[19] = "";
                        datos[20] = rs.getString("Domingo");
                        datos[20] = getTiempo(datos[20]);

                        datos[21] = rs.getString("SDomingo");
                        datos[21] = getTiempo(datos[21]);

                        if (datos[20].equals("") && datos[21].equals("")) {
                            datos[20] = "";
                            datos[21] = "";
                        }
                        if (tgRedondear.isSelected()) {
                            datos[2] = redondear(datos[2], datos[0]);
                            datos[3] = redondear(datos[3], datos[0]);
                            datos[5] = redondear(datos[5], datos[0]);
                            datos[6] = redondear(datos[6], datos[0]);
                            datos[8] = redondear(datos[8], datos[0]);
                            datos[9] = redondear(datos[9], datos[0]);
                            datos[11] = redondear(datos[11], datos[0]);
                            datos[12] = redondear(datos[12], datos[0]);
                            datos[14] = redondear(datos[14], datos[0]);
                            datos[15] = redondear(datos[15], datos[0]);
                            datos[17] = redondear(datos[17], datos[0]);
                            datos[18] = redondear(datos[18], datos[0]);
                            datos[20] = redondear(datos[20], datos[0]);
                            datos[21] = redondear(datos[21], datos[0]);
                        }
                        miModelo.addRow(datos);
                    }
                }

                for (int i = 0; i < config.length; i++) {
                    boolean band = true;
                    for (int j = 0; j < Tabla1.getRowCount(); j++) {
                        if (config[i].getNombre().equals(Tabla1.getValueAt(j, 1).toString())) {
                            band = false;
                        }
                    }
                    if (band == true && config[i].isAutomatico()) {
                        datos[0] = config[i].getNumEmpleado();
                        datos[1] = config[i].getNombre();
                        datos[2] = config[i].getEntrada();
                        datos[2] = getTiempo(datos[2]);
                        datos[3] = config[i].getSalida();
                        datos[3] = getTiempo(datos[3]);
                        datos[4] = "";
                        datos[5] = config[i].getEntrada();
                        datos[5] = getTiempo(datos[5]);
                        datos[6] = config[i].getSalida();
                        datos[6] = getTiempo(datos[6]);
                        datos[7] = "";
                        datos[8] = config[i].getEntrada();
                        datos[8] = getTiempo(datos[8]);
                        datos[9] = config[i].getSalida();
                        datos[9] = getTiempo(datos[9]);
                        datos[10] = "";
                        datos[11] = config[i].getEntrada();
                        datos[11] = getTiempo(datos[11]);
                        datos[12] = config[i].getSalida();
                        datos[12] = getTiempo(datos[12]);
                        datos[13] = "";
                        datos[14] = config[i].getEntrada();
                        datos[14] = getTiempo(datos[14]);
                        datos[15] = config[i].getSalida();
                        datos[15] = getTiempo(datos[15]);
                        datos[16] = "";
                        datos[17] = config[i].getEntradaSabado();
                        datos[17] = getTiempo(datos[17]);
                        datos[18] = config[i].getSalidaSabado();
                        datos[18] = getTiempo(datos[18]);
                        datos[19] = "";
                        datos[20] = "";
                        datos[21] = "";
                        if (tgRedondear.isSelected()) {
                            datos[2] = redondear(datos[2], datos[0]);
                            datos[3] = redondear(datos[3], datos[0]);
                            datos[5] = redondear(datos[5], datos[0]);
                            datos[6] = redondear(datos[6], datos[0]);
                            datos[8] = redondear(datos[8], datos[0]);
                            datos[9] = redondear(datos[9], datos[0]);
                            datos[11] = redondear(datos[11], datos[0]);
                            datos[12] = redondear(datos[12], datos[0]);
                            datos[14] = redondear(datos[14], datos[0]);
                            datos[15] = redondear(datos[15], datos[0]);
                            datos[17] = redondear(datos[17], datos[0]);
                            datos[18] = redondear(datos[18], datos[0]);
                            datos[20] = redondear(datos[20], datos[0]);
                            datos[21] = redondear(datos[21], datos[0]);
                        }
                        miModelo.addRow(datos);
                    }
                }

                String sql2 = "select * from semanas where NumSemana like '" + cmbSemana.getSelectedItem() + "'";
                Statement st2 = con.createStatement();
                ResultSet rs2 = st2.executeQuery(sql2);
                String estado = null;
                String inicio = "";
                while (rs2.next()) {
                    estado = rs2.getString(getSupervisor());
                    inicio = rs2.getString("Inicio");
                }
                agregarDias(inicio);
                if (estado == null) {
                    lblEstado.setText("EDITANDO");
                    lblEstado.setForeground(Color.ORANGE);
                } else {
                    lblEstado.setText("EDITADO");
                    lblEstado.setForeground(Color.RED);
                }
                if (cmbSemana.getSelectedIndex() == 0) {
                    lblSemana.setText("SIN SELECCIONAR");
                } else {
                    lblSemana.setText(cmbSemana.getSelectedItem().toString());
                }
                crearMatriz();
                if (tgIncidencias.isVisible()) {
                    if (tgIncidencias.isSelected()) {
                        crearMatrizIncidencias(con, fecha.get(cmbSemana.getSelectedIndex()), lblSemana.getText());
                    }
                } else {
                    crearMatrizIncidencias(con, fecha.get(cmbSemana.getSelectedIndex()), lblSemana.getText());
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "ERROR: " + e, "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_btnVerMisEmpleadosActionPerformed

    private void Tabla1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Tabla1MouseClicked
        if (evt.getClickCount() == 1) {
            if (Tabla1.getColumnName(Tabla1.getSelectedColumn()).equals("entrada") || Tabla1.getColumnName(Tabla1.getSelectedColumn()).equals("salida")) {
                Tabla1.setValueAt("", Tabla1.getSelectedRow(), Tabla1.getSelectedColumn());
            }
        }
    }//GEN-LAST:event_Tabla1MouseClicked

    private void rSButtonRoundRipple3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rSButtonRoundRipple3ActionPerformed
        JFrame f = (JFrame) JOptionPane.getFrameForComponent(this);
        e = new editarEmpleado(f, true, numEmpleado);
        e.btnGuardar.addActionListener(this);
        e.setVisible(true);
    }//GEN-LAST:event_rSButtonRoundRipple3ActionPerformed

    private void cmbSemanaPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_cmbSemanaPropertyChange
//        lblSemana.setText(cmbSemana.getSelectedItem().toString());
    }//GEN-LAST:event_cmbSemanaPropertyChange

    private void cmbSemanaVetoableChange(java.beans.PropertyChangeEvent evt)throws java.beans.PropertyVetoException {//GEN-FIRST:event_cmbSemanaVetoableChange

    }//GEN-LAST:event_cmbSemanaVetoableChange

    private void cmbSemanaPopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_cmbSemanaPopupMenuWillBecomeInvisible

    }//GEN-LAST:event_cmbSemanaPopupMenuWillBecomeInvisible

    private void btnSubir1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSubir1ActionPerformed
        if (Tabla1.getRowCount() < 0) {
            JOptionPane.showMessageDialog(this, "Debes seleccionar un horario", "Advertencia", JOptionPane.WARNING_MESSAGE);
        } else {
            int opc = JOptionPane.showConfirmDialog(this, "Estas seguro de guardar este horario?");
            if (opc == 0) {
                try {
                    //--------------------------------------------------------------------------------------------------------------------------------
                    int n1 = 0;
                    String sql2 = "INSERT INTO incidencias_checador(NumSemana, Inicio, X, NumEmpleado, Comentario, Hora) VALUES(?,?,?,?,?,?) as nuevo "
                            + "on duplicate key update "
                            + "Comentario = nuevo.Comentario, Hora = nuevo.Hora";
                    PreparedStatement pst2 = con.prepareStatement(sql2);

                    for (int i = 0; i < matrizIncidencias.length; i++) {
                        for (int j = 0; j < matrizIncidencias[i].length; j++) {
                            Object comentario = matrizIncidencias[i][j][0];
                            if (comentario != null) {
                                pst2.setString(1, lblSemana.getText());
                                pst2.setString(2, fecha.get(cmbSemana.getSelectedIndex()));
                                pst2.setInt(3, j);
                                pst2.setString(4, Tabla1.getValueAt(i, 0).toString());
                                pst2.setString(5, comentario.toString());
                                pst2.setString(6, matrizIncidencias[i][j][1].toString());
                                n1 += pst2.executeUpdate();
                            }
                        }
                    }

                    String sql = "update dias set lunes = ?, martes = ?, miercoles = ?, jueves = ?, viernes = ?, sabado = ?, domingo = ?, slunes = ?, "
                            + "smartes = ?, smiercoles = ?, sjueves = ?, sviernes = ?, ssabado = ?, sdomingo = ? where NumEmpleado = ? and Inicio = ?";
                    PreparedStatement pst = con.prepareStatement(sql);

                    int n = 0;
                    for (int i = 0; i < Tabla1.getRowCount(); i++) {
                        pst.setString(1, evaluarNull(Tabla1.getValueAt(i, 2)));
                        pst.setString(2, evaluarNull(Tabla1.getValueAt(i, 5)));
                        pst.setString(3, evaluarNull(Tabla1.getValueAt(i, 8)));
                        pst.setString(4, evaluarNull(Tabla1.getValueAt(i, 11)));
                        pst.setString(5, evaluarNull(Tabla1.getValueAt(i, 14)));
                        pst.setString(6, evaluarNull(Tabla1.getValueAt(i, 17)));
                        pst.setString(7, evaluarNull(Tabla1.getValueAt(i, 20)));
                        pst.setString(8, evaluarNull(Tabla1.getValueAt(i, 3)));
                        pst.setString(9, evaluarNull(Tabla1.getValueAt(i, 6)));
                        pst.setString(10, evaluarNull(Tabla1.getValueAt(i, 9)));
                        pst.setString(11, evaluarNull(Tabla1.getValueAt(i, 12)));
                        pst.setString(12, evaluarNull(Tabla1.getValueAt(i, 15)));
                        pst.setString(13, evaluarNull(Tabla1.getValueAt(i, 18)));
                        pst.setString(14, evaluarNull(Tabla1.getValueAt(i, 21)));
                        pst.setString(15, evaluarNull(Tabla1.getValueAt(i, 0)));
                        pst.setString(16, fecha.get(cmbSemana.getSelectedIndex()));
                        
                        n = pst.executeUpdate();
                    }

                    if (n1 > 0) {
                        JOptionPane.showMessageDialog(this, "Guardados correctamente");
                    } else {
                        JOptionPane.showMessageDialog(this, "Sin cambios", "Advertencia", JOptionPane.WARNING_MESSAGE);
                    }
                    crearPdf();
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(this, "Error al guardar datos de checador: " + e, "ERROR", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }//GEN-LAST:event_btnSubir1ActionPerformed

    private void cmbSemanaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbSemanaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbSemanaActionPerformed

    private void Tabla1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Tabla1KeyPressed

    }//GEN-LAST:event_Tabla1KeyPressed

    private void agregarIncidenciaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_agregarIncidenciaActionPerformed
        int row = Tabla1.getSelectedRow();
        int col = Tabla1.getSelectedColumn();
        if (verificarColumna(col)) {
            incidencias = new Incidencias(null, true, "", "", Tabla1.getValueAt(row, 1).toString());
            incidencias.setLocationRelativeTo(null);
            String comentarios = incidencias.getIncidencia();
            matrizIncidencias[row][col][0] = comentarios;
            matrizIncidencias[row][col][1] = "";
            matrizIncidencias[row][col][2] = "";
            if (comentarios.equals("")) {
                Tabla1.setValueAt("", row, col);
            }
        }
    }//GEN-LAST:event_agregarIncidenciaActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private rojerusan.RSTableMetro Tabla1;
    private javax.swing.JMenuItem agregarIncidencia;
    private javax.swing.JPanel btnSalir;
    private rojeru_san.rsbutton.RSButtonRoundRipple btnSubir1;
    private rojeru_san.rsbutton.RSButtonRoundRipple btnVerMisEmpleados;
    private RSMaterialComponent.RSComboBoxMaterial cmbSemana;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel61;
    private javax.swing.JLabel jLabel62;
    private javax.swing.JLabel jLabel63;
    private javax.swing.JLabel jLabel64;
    private javax.swing.JLabel jLabel65;
    private javax.swing.JLabel jLabel66;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblEmpleado;
    private javax.swing.JLabel lblEstado;
    private javax.swing.JLabel lblIncidencias;
    private javax.swing.JLabel lblJueves;
    private javax.swing.JLabel lblLunes;
    private javax.swing.JLabel lblMartes;
    private javax.swing.JLabel lblMiercoles;
    private javax.swing.JLabel lblSabado;
    private javax.swing.JLabel lblSemana;
    private javax.swing.JLabel lblViernes;
    private javax.swing.JLabel lbldomingo;
    private scrollPane.PanelRound panelRound1;
    private rojeru_san.rsbutton.RSButtonRoundRipple rSButtonRoundRipple3;
    private toggle.ToggleButton tgIncidencias;
    private toggle.ToggleButton tgRedondear;
    // End of variables declaration//GEN-END:variables

    @Override
    public void actionPerformed(ActionEvent e) {
        if (this.e != null) {
            if (e.getSource() == this.e.btnGuardar) {
                config = null;
                addConfig();
                limpiarTabla();
                revalidate();
                repaint();
            }
        }
    }
}
