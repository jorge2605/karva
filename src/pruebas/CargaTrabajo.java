package pruebas;

import Conexiones.Conexion;
import VentanaEmergente.Inicio1.Espera;
import com.mxrck.autocompleter.TextAutoCompleter;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Font;
import java.beans.PropertyVetoException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import org.apache.pdfbox.util.Hex;
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
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

public class CargaTrabajo extends javax.swing.JInternalFrame {
    int acum4 = 0;
    int hrsCortes = 0;
    int hrsFresa = 0;
    int hrsCnc = 0;
    int hrsTorno = 0;
    String proyectos[];
    int contProy = 0;
    Inicio1 inicio;
    private boolean cargandoCombo = false;
    TextAutoCompleter autoCompleter;
    
    public void limpiarTabla1(){
        Tabla1.setBackground(new java.awt.Color(255, 255, 255));

        Tabla1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
        "PROYECTO", "FECHA DE ENTREGA", "AVANCE", "PLANOS PENDIENTES", "PLANOS SIN MATERIAL"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });

        Tabla1.setRowHeight(25);
    }
    
    public void verCarga() {
        try {
            panelHoras.removeAll();
            revalidate();
            repaint();
            proyectos = new String[1000];
            Connection con = null;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            String aux = "";
            int acum1 = 0,acum2 = 0,acum3 = 0,acum4 = 0;
            int aux1 = 0;
            Statement st1 = con.createStatement();
            Statement st2 = con.createStatement();
            Statement st3 = con.createStatement();
            Statement st4 = con.createStatement();
            Statement st5 = con.createStatement();
            String sql1 = "select * from fresadora where Terminado like 'NO'";
            ResultSet rs1 = st1.executeQuery(sql1);
            
            String sql3 = "select * from cnc where Terminado like 'NO'";
            ResultSet rs3 = st3.executeQuery(sql3);
            
            String sql4 = "select * from torno where Terminado like 'NO'";
            ResultSet rs4 = st4.executeQuery(sql4);
            
            String sql5 = "select * from datos where Terminado like 'NO'";
            ResultSet rs5 = st5.executeQuery(sql5);
            
            String datos1[] = new String[10];
            while (rs1.next()) {
                datos1[0] = rs1.getString("Proyecto");
                datos1[1] = rs1.getString("Plano");
                String sql2 = "select Fresadora from Planos where Plano like '" + datos1[0] + "'";
                ResultSet rs2 = st2.executeQuery(sql2);
                
                if(datos1[1] != null){
                if(contProy == 0){
                    proyectos[contProy] = datos1[1];
                    contProy++;
                }else{
                    boolean band = false;
                    for (int i = 0; i < contProy; i++) {
                        if(datos1[1].equals(proyectos[i])){
                            band = true;
                        }
                    }
                    if(band == false){
                        proyectos[contProy] = datos1[1];
                            contProy++;
                    }
                    
                }
                }
                
                while(rs2.next()){
                datos1[1] = rs2.getString("Fresadora");
                if(datos1[1].equals("") || datos1[1].equals("0")){
                
                }else{
                hrsFresa++;
                String buscar = "/";
                char arreglo[] = datos1[1].toCharArray();
                for (int i = 0; i < datos1[1].length(); i++) {
                    String letra = String.valueOf(arreglo[i]);
                    if(buscar.equalsIgnoreCase(letra)){
                    aux1 = i;
                    }
                }
                
                aux = datos1[1].substring(0,aux1);
                int a = 0;
                try{
                    a = Integer.parseInt(aux);
                }catch(Exception ex){
                    System.out.println("Exception 1: "+ex);
                }
                acum1 = (acum1 + a);
                lblConvencional.setText(""+(acum1)/60);
                }
                }
            }
            
            
            
            while (rs3.next()) {
                datos1[0] = rs3.getString("Proyecto");
                datos1[1] = rs3.getString("Plano");
                String sql2 = "select Cnc from Planos where Plano like '" + datos1[0] + "'";
                ResultSet rs2 = st2.executeQuery(sql2);
                
                if(datos1[1] != null){
                if(contProy == 0){
                    proyectos[contProy] = datos1[1];
                    contProy++;
                }else{
                    boolean band = false;
                    for (int i = 0; i < contProy; i++) {
                        if(datos1[1].equals(proyectos[i])){
                            band = true;
                        }
                    }
                    if(band == false){
                        proyectos[contProy] = datos1[1];
                            contProy++;
                    }
                    
                }
                }
                
                while(rs2.next()){
                datos1[1] = rs2.getString("Cnc");
                if(datos1[1].equals("") || datos1[1].equals("0")){
                
                }else{
                    hrsCnc++;
                String buscar = "/";
                char arreglo[] = datos1[1].toCharArray();
                for (int i = 0; i < datos1[1].length(); i++) {
                    String letra = String.valueOf(arreglo[i]);
                    if(buscar.equalsIgnoreCase(letra)){
                    aux1 = i;
                    }
                }
                
                aux = datos1[1].substring(0,aux1);
                int a = 0;
                try{
                    a = Integer.parseInt(aux);
                }catch(Exception ex){
                    System.out.println("Exception 2: "+ex);
                }
                acum2 = (acum2 + a);
                lblCnc.setText(""+(acum2)/60);
                }
                }
            }
            while (rs4.next()) {
                datos1[0] = rs4.getString("Proyecto");
                datos1[1] = rs4.getString("Plano");
                String sql2 = "select Torno from Planos where Plano like '" + datos1[0] + "'";
                ResultSet rs2 = st2.executeQuery(sql2);
                
                if(datos1[1] != null){
                if(contProy == 0){
                    proyectos[contProy] = datos1[1];
                    contProy++;
                }else{
                    boolean band = false;
                    for (int i = 0; i < contProy; i++) {
                        if(datos1[1].equals(proyectos[i])){
                            band = true;
                        }
                    }
                    if(band == false){
                        proyectos[contProy] = datos1[1];
                            contProy++;
                    }
                    
                }
                }
                
                while(rs2.next()){
                datos1[1] = rs2.getString("Torno");
                if(datos1[1].equals("") || datos1[1].equals("0")){
                
                }else{
                hrsTorno++;
                String buscar = "/";
                char arreglo[] = datos1[1].toCharArray();
                for (int i = 0; i < datos1[1].length(); i++) {
                    String letra = String.valueOf(arreglo[i]);
                    if(buscar.equalsIgnoreCase(letra)){
                    aux1 = i;
                    }
                }
                
                aux = datos1[1].substring(0,aux1);
                int a = 0;
                try{
                    a = Integer.parseInt(aux);
                }catch(Exception ex){
                    System.out.println("Exception 3: "+ex);
                }
                acum3 = (acum3 + a);
                lblAcabados.setText(""+(acum3)/60);
                }
                }
            }
            while (rs5.next()) {
                datos1[0] = rs5.getString("Proyecto");
                datos1[1] = rs5.getString("Plano");
                String sql2 = "select Fresadora,Cnc,Torno from Planos where Plano like '" + datos1[0] + "'";
                ResultSet rs2 = st2.executeQuery(sql2);
                
                if(datos1[1] != null){
                if(contProy == 0){
                    proyectos[contProy] = datos1[1];
                    contProy++;
                }else{
                    boolean band = false;
                    for (int i = 0; i < contProy; i++) {
                        if(datos1[1].equals(proyectos[i])){
                            band = true;
                        }
                    }
                    if(band == false){
                        proyectos[contProy] = datos1[1];
                            contProy++;
                    }
                    
                }
                }
                
                while(rs2.next()){
                    try{
                        datos1[1] = rs2.getString("Fresadora");
                        datos1[2] = rs2.getString("Cnc");
                        datos1[3] = rs2.getString("Torno");
                        if(datos1[1].equals("") || datos1[1].equals("0")){

                        }else{
                            String buscar = "/";
                            char arreglo[] = datos1[1].toCharArray();
                            for (int i = 0; i < datos1[1].length(); i++) {
                                String letra = String.valueOf(arreglo[i]);
                                if(buscar.equalsIgnoreCase(letra)){
                                aux1 = i;
                                }
                            }

                            aux = datos1[1].substring(0,aux1);
                            int a = 0;
                            try{
                                a = Integer.parseInt(aux);
                            }catch(Exception ex){
                                System.out.println("Exception 4: "+ex);
                            }
                            acum4 = (acum4 + a);

                        }
                        if(datos1[2].equals("") || datos1[2].equals("0")){

                        }else{
                            String buscar = "/";
                            char arreglo[] = datos1[2].toCharArray();
                            for (int i = 0; i < datos1[2].length(); i++) {
                                String letra = String.valueOf(arreglo[i]);
                                if(buscar.equalsIgnoreCase(letra)){
                                aux1 = i;
                                }
                            }

                            aux = datos1[2].substring(0,aux1);
                            int a = 0;
                            try{
                                a = Integer.parseInt(aux);
                            }catch(Exception ex){
                                System.out.println("Exception 5: "+ex);
                            }
                            acum4 = (acum4 + a);

                        }
                        if(datos1[3].equals("") || datos1[3].equals("0")){

                        }else{
                            String buscar = "/";
                            char arreglo[] = datos1[3].toCharArray();
                            for (int i = 0; i < datos1[3].length(); i++) {
                                String letra = String.valueOf(arreglo[i]);
                                if(buscar.equalsIgnoreCase(letra)){
                                aux1 = i;
                                }
                            }

                            aux = datos1[3].substring(0,aux1);
                            int a = 0;
                            try{
                                a = Integer.parseInt(aux);
                            }catch(Exception ex){
                                System.out.println("Exception 6: "+ex);
                            }

                            acum4 = (acum4 + a);

                        }
                        lblCortes.setText(""+(acum4)/60);
                    }catch(Exception e){
                        
                    }
                }
            }
            
            int total = 0,t1 = 0,t2 = 0,t3 = 0,t4 = 0;
            try{
                t1 = Integer.parseInt(lblAcabados.getText());
            }catch(NumberFormatException e){
                t1 = 0;
            }
            try{
                t2 = Integer.parseInt(lblCnc.getText());
            }catch(NumberFormatException e){
                t2 = 0;
            }
            try{
                t3 = Integer.parseInt(lblConvencional.getText());
            }catch(NumberFormatException e){
                t3 = 0;
            }
            try{
                t4 = Integer.parseInt(lblCortes.getText());
            }catch(NumberFormatException e){
                t4 = 0;
            }
            
            total = t1+t2+t3+t4;
            lblTotal.setText(""+total);
            
            int cor = 0;
            int fresa = 0;
            int cnc = 0;
            int torno = 0;
            
            try{
                cor = Integer.parseInt(lblCortes.getText());
            }catch(NumberFormatException e){
                cor = 0;
            }
            try{
                fresa = Integer.parseInt(lblConvencional.getText());
            }catch(NumberFormatException e){
               fresa = 0; 
            }
            try{
                cnc = Integer.parseInt(lblCnc.getText());
            }catch(NumberFormatException e){
                cnc = 0;
            }
            try{
                 torno = Integer.parseInt(lblAcabados.getText());
            }catch(NumberFormatException e){
                torno = 0;
            }
            
            DefaultCategoryDataset dtsc = new DefaultCategoryDataset();
            dtsc.setValue(cor, "CORTES","CORTES");
            dtsc.setValue(fresa, "FRESADORA","FRESADORA");
            dtsc.setValue(cnc, "CNC","CNC");
            dtsc.setValue(torno, "TORNO","TORNO");
            JFreeChart ch = ChartFactory.createBarChart3D("CARGA DE TRABAJO", "MAQUINADOS", "HORAS", dtsc,PlotOrientation.VERTICAL, true, true, false);
            ChartPanel cp = new ChartPanel(ch);
            panelHoras.add(cp);
            cp.setBounds(650,150,600,400);
            //jComboBox1.removeAllItems();
            //jComboBox1.addItem("TODOS LOS PROYECTOS");
            for (int i = 0; i < contProy; i++) {
                //jComboBox1.addItem(proyectos[i]);
            }
        } catch (SQLException E) {
            Logger.getLogger(CargaTrabajo.class.getName()).log(Level.SEVERE, null, E);
        }
    }

    public void cargarGraficaPlanos(String proyecto) {
        try {
            // Se limpia el panel para evitar superposición de gráficas
            panelPlanos.removeAll();
            panelPlanos.revalidate();
            panelPlanos.repaint();

            Connection con = null;
            Conexion con1 = new Conexion();
            con = con1.getConnection();

            int cortes = 0;
            int acabados = 0;
            int sinMaterial = 0;
            
            String filtro = "";
            if (proyecto != null && !proyecto.equals("")) {
                filtro = " AND p.Proyecto = '" + proyecto + "'";
            }

            Statement st = con.createStatement();

            // Se obtiene la cantidad de planos en CORTES
            String sqlCortes =
                "SELECT COUNT(*) AS total " +
                "FROM planos p " +
                "INNER JOIN proyectos pr ON pr.Proyecto = p.Proyecto " +
                "WHERE p.estado = 'CORTES' " +
                filtro +
                " AND pr.Estatus NOT IN ('CERRADO', 'DETENIDO')";
            ResultSet rsCortes = st.executeQuery(sqlCortes);
            if (rsCortes.next()) {
                cortes = rsCortes.getInt("total");
            }

            // Se obtiene la cantidad de planos en ACABADOS
            String sqlAcabados =
                "SELECT COUNT(*) AS total " +
                "FROM planos p " +
                "INNER JOIN proyectos pr ON pr.Proyecto = p.Proyecto " +
                "WHERE p.estado = 'ACABADOS' " +
                filtro +
                " AND pr.Estatus NOT IN ('CERRADO', 'DETENIDO')";
            ResultSet rsAcabados = st.executeQuery(sqlAcabados);
            if (rsAcabados.next()) {
                acabados = rsAcabados.getInt("total");
            }
            
            // Se obtiene la cantidad de planos en SIN MATERIAL
            String sqlSinMaterial =
                "SELECT COUNT(*) AS total " +
                "FROM planos p " +
                "INNER JOIN proyectos pr ON pr.Proyecto = p.Proyecto " +
                "WHERE p.estado = 'SIN MATERIAL' " +
                filtro +
                " AND pr.Estatus NOT IN ('CERRADO', 'DETENIDO')";
            ResultSet rsSinMaterial = st.executeQuery(sqlSinMaterial);
            if (rsSinMaterial.next()) {
                sinMaterial = rsSinMaterial.getInt("total");
            }
            
            // Se obtiene el total real de todos los planos del proyecto (proyectos con estatus EN PROCESO)
            String sqlTotal =
                "SELECT COUNT(*) AS total " +
                "FROM planos p " +
                "INNER JOIN proyectos pr ON pr.Proyecto = p.Proyecto " +
                "WHERE pr.Estatus NOT IN ('CERRADO', 'DETENIDO') " +
                filtro;
            Statement st2 = con.createStatement();
            ResultSet rsTotal = st2.executeQuery(sqlTotal);
            int totalPlanos = 0;
            if (rsTotal.next()) {
                totalPlanos = rsTotal.getInt("total");
            }

            // Se crea el dataset con etiquetas para la leyenda
            DefaultCategoryDataset dataset = new DefaultCategoryDataset();
            dataset.setValue(cortes, "CORTES", "CORTES");
            dataset.setValue(acabados, "ACABADOS", "ACABADOS");
            
            // Se crea la gráfica
            JFreeChart chart = ChartFactory.createBarChart3D(
                    "PLANOS POR ESTADO",
                    "ESTADO",
                    "CANTIDAD DE PLANOS",
                    dataset,
                    PlotOrientation.VERTICAL,
                    true,
                    true,
                    false
            );

            // Se obtiene el plot y el renderer
            org.jfree.chart.plot.CategoryPlot plot = chart.getCategoryPlot();
            org.jfree.chart.renderer.category.BarRenderer renderer =
                    (org.jfree.chart.renderer.category.BarRenderer) plot.getRenderer();
            org.jfree.chart.axis.NumberAxis rangeAxis =
                    (org.jfree.chart.axis.NumberAxis) plot.getRangeAxis();

            // Si ambas barras son 0, se fuerza el rango
            if (cortes == 0 && acabados == 0) {
                rangeAxis.setRange(0, 1); // de 0 a 1 para que se vea correcto
            } else {
                rangeAxis.setAutoRange(true); // comportamiento normal
            }

            // Se asignan colores a cada barra
            renderer.setSeriesPaint(0, new Color(0, 102, 204));   // Azul
            renderer.setSeriesPaint(1, new Color(153, 204, 255)); // Azul claro

            // Se activa la visualización del valor dentro de cada barra
            renderer.setBaseItemLabelGenerator(
                    new org.jfree.chart.labels.StandardCategoryItemLabelGenerator()
            );
            renderer.setBaseItemLabelsVisible(true);

            // Se define el color del texto
            renderer.setBaseItemLabelPaint(Color.BLACK);
            renderer.setBaseItemLabelFont(new Font("Arial", Font.BOLD, 12)); //Letra en negritas
            
            
            // Se posiciona el valor arriba de la barra
            renderer.setBasePositiveItemLabelPosition(
                    new org.jfree.chart.labels.ItemLabelPosition(
                            org.jfree.chart.labels.ItemLabelAnchor.OUTSIDE12,
                            org.jfree.ui.TextAnchor.BOTTOM_CENTER
                    )
            );

            // Se crea el panel y se agrega al contenedor
            ChartPanel cp = new ChartPanel(chart);
            panelPlanos.setLayout(new java.awt.BorderLayout());
            panelPlanos.add(cp, java.awt.BorderLayout.CENTER);
            lblCortes.setText(cortes + " planos");
            lblAcabados.setText(acabados + " planos");
            lblTotal.setText(totalPlanos + " planos");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void cargarGraficaHorasMaquinados(String proyecto) {
        try {
            // Se limpia el panel para evitar superposición de gráficas
            panelHoras.removeAll();
            panelHoras.revalidate();
            panelHoras.repaint();

            Connection con = null;
            Conexion con1 = new Conexion();
            con = con1.getConnection();

            double minutosConvencional = 0;
            double minutosCNC = 0;

            Statement st = con.createStatement();

            // Se obtienen los planos en estado MAQUINADOS
            String filtro = "";
            if (proyecto != null && !proyecto.equals("")) {
                filtro = " AND p.Proyecto = '" + proyecto + "'";
            }
            
//            String sqlPlanos = // Antes de la actualización
//                "SELECT p.id " +
//                "FROM planos p " +
//                "INNER JOIN proyectos pr ON pr.Proyecto = p.Proyecto " +
//                "WHERE p.estado = 'MAQUINADOS' " +
//                filtro +
//                " AND pr.Estatus NOT IN ('CERRADO', 'DETENIDO')";
            String sqlPlanos =
                "SELECT p.id " +
                "FROM planos p " +
                "INNER JOIN proyectos pr ON pr.Proyecto = p.Proyecto " +
                "WHERE p.estado IN ('SIN MATERIAL', 'CORTES', 'MAQUINADOS') " +
                filtro +
                " AND pr.Estatus NOT IN ('CERRADO', 'DETENIDO')";
            ResultSet rsPlanos = st.executeQuery(sqlPlanos);

            // Se recorre cada plano (para las gráficas y lblConvencional/lblCnc)
            while (rsPlanos.next()) {
                int idPlano = rsPlanos.getInt("id");
                //System.out.println("PLANO: " + idPlano);
                String sqlTiempos = "select id_maquina, tiempo_estimado from tiempos_planos where id_plano = " + idPlano;
                Statement st2 = con.createStatement();
                ResultSet rsTiempos = st2.executeQuery(sqlTiempos);

                while (rsTiempos.next()) {
                    int idMaquina = rsTiempos.getInt("id_maquina");
                    String tiempo = rsTiempos.getString("tiempo_estimado"); // formato HH:MM
                    String nombreMaquina = "";

                    if (idMaquina == 1) {
                        nombreMaquina = "FRESADORA";
                    } else if (idMaquina == 2) {
                        nombreMaquina = "TORNO";
                    } else if (idMaquina == 3) {
                        nombreMaquina = "CNC";
                    }
                    //System.out.println("   Máquina: " + nombreMaquina + " | Tiempo: " + tiempo);
                    if (tiempo != null && tiempo.contains(":")) {
                        String[] partes = tiempo.split(":");

                        int horas = Integer.parseInt(partes[0]);
                        int minutos = Integer.parseInt(partes[1]);

                        int totalMin = (horas * 60) + minutos;

                        if (idMaquina == 1 || idMaquina == 2) {
                            minutosConvencional += totalMin;
                        } else if (idMaquina == 3) {
                            minutosCNC += totalMin;
                        }
                    }
                }
            }

            double horasConvencional = Math.round((minutosConvencional / 60) * 100.0) / 100.0;
            double horasCNC = Math.round((minutosCNC / 60) * 100.0) / 100.0;
            double totalHoras = horasConvencional + horasCNC;

            // Query separada: horas totales de TODOS los planos (sin filtro de estado, es decir, toma el tiempo de los planos sin importar su estado) para lblTotal
            double minConvTotal = 0;
            double minCncTotal = 0;

            String sqlTodosPlanos =
                "SELECT p.id " +
                "FROM planos p " +
                "INNER JOIN proyectos pr ON pr.Proyecto = p.Proyecto " +
                "WHERE pr.Estatus NOT IN ('CERRADO', 'DETENIDO') " +
                filtro;
            Statement stTodos = con.createStatement();
            ResultSet rsTodos = stTodos.executeQuery(sqlTodosPlanos);

            while (rsTodos.next()) {
                int idPlano = rsTodos.getInt("id");
                String sqlTiempos2 = "select id_maquina, tiempo_estimado from tiempos_planos where id_plano = " + idPlano;
                Statement st3 = con.createStatement();
                ResultSet rsTiempos2 = st3.executeQuery(sqlTiempos2);

                while (rsTiempos2.next()) {
                    int idMaquina = rsTiempos2.getInt("id_maquina");
                    String tiempo = rsTiempos2.getString("tiempo_estimado");
                    if (tiempo != null && tiempo.contains(":")) {
                        String[] partes = tiempo.split(":");
                        int totalMin = (Integer.parseInt(partes[0]) * 60) + Integer.parseInt(partes[1]);
                        if (idMaquina == 1 || idMaquina == 2) {
                            minConvTotal += totalMin;
                        } else if (idMaquina == 3) {
                            minCncTotal += totalMin;
                        }
                    }
                }
            }

            double horasConvTotal = Math.round((minConvTotal / 60) * 100.0) / 100.0;
            double horasCncTotal = Math.round((minCncTotal / 60) * 100.0) / 100.0;

            // Se crea el dataset
            DefaultCategoryDataset dataset = new DefaultCategoryDataset();
            dataset.setValue(horasConvencional, "CONVENCIONAL", "CONVENCIONAL");
            dataset.setValue(horasCNC, "CNC", "CNC");

            // Se crea la gráfica
            JFreeChart chart = ChartFactory.createBarChart3D(
                    "HORAS EN MAQUINADOS",
                    "TIPO",
                    "HORAS",
                    dataset,
                    PlotOrientation.VERTICAL,
                    true,
                    true,
                    false
            );

            // Se obtiene el renderer
            org.jfree.chart.plot.CategoryPlot plot = chart.getCategoryPlot();
            org.jfree.chart.renderer.category.BarRenderer renderer =
                    (org.jfree.chart.renderer.category.BarRenderer) plot.getRenderer();
            org.jfree.chart.axis.NumberAxis rangeAxis =
                    (org.jfree.chart.axis.NumberAxis) plot.getRangeAxis();

            if (horasConvencional == 0 && horasCNC == 0) {
                rangeAxis.setRange(0, 1);
            } else {
                rangeAxis.setAutoRange(true);
            }

            // Colores
            renderer.setSeriesPaint(0, new Color(0, 102, 204));   // Azul
            renderer.setSeriesPaint(1, new Color(153, 204, 255)); // Azul claro

            // Mostrar valores
            renderer.setBaseItemLabelGenerator(
                    new org.jfree.chart.labels.StandardCategoryItemLabelGenerator()
            );
            renderer.setBaseItemLabelsVisible(true);
            renderer.setBaseItemLabelPaint(Color.BLACK);
            renderer.setBaseItemLabelFont(new Font("Arial", Font.BOLD, 12)); //Letra en negritas

            // Posición arriba de la barra
            renderer.setBasePositiveItemLabelPosition(
                    new org.jfree.chart.labels.ItemLabelPosition(
                            org.jfree.chart.labels.ItemLabelAnchor.OUTSIDE12,
                            org.jfree.ui.TextAnchor.BOTTOM_CENTER
                    )
            );

            // Se agrega al panel
            ChartPanel cp = new ChartPanel(chart);
            panelHoras.setLayout(new java.awt.BorderLayout());
            panelHoras.add(cp, java.awt.BorderLayout.CENTER);
            lblConvencional.setText(horasConvencional + " hrs");
            lblCnc.setText(horasCNC + " hrs");
            String textoPlanosActual = lblTotal.getText().replaceAll("<[^>]*>", "").split("\\n")[0].trim();
            lblTotal.setText("<html>" + textoPlanosActual + "<br>" + horasConvTotal + " hrs convencional<br>" + horasCncTotal + " hrs CNC</html>");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
       
    public CargaTrabajo(Inicio1 inic) {
        initComponents();
        agregarProyectosAutoCompletar();
        jButton1.setVisible(false);
        jButton2.setVisible(false);
        cargarTurnoHoy();
        //cargarProyectosCombo();
        cargarGraficaPlanos("");
        cargarGraficaHorasMaquinados("");
        cargarTablaProyecto("");
        btnExcel.setEnabled(true);
        ((javax.swing.plaf.basic.BasicInternalFrameUI) this.getUI()).setNorthPane(null);
        Tabla1.getTableHeader().setFont(new Font("Roboto", Font.PLAIN, 14));
        Tabla1.getTableHeader().setOpaque(false);
        Tabla1.getTableHeader().setBackground(new Color(51,126,255,255));
        Tabla1.getTableHeader().setForeground(Color.white);
        Tabla1.setRowHeight(25);
        Tabla1.setShowGrid(false);
        inicio = inic;
    }
    
    public void cargarTablaProyecto(String proyecto) {
        try {

            DefaultTableModel modelo = (DefaultTableModel) Tabla1.getModel();
            modelo.setRowCount(0);

            Connection con = new Conexion().getConnection();
            Statement st = con.createStatement();

            String filtro = "";

            if (proyecto != null && !proyecto.equals("")) {
                filtro = " WHERE p.proyecto = '" + proyecto + "'";
            }

//            String sql = // Antes de la actualización
//                "SELECT " +
//                "p.proyecto, " +
//                "pr.FechaEntrega, " +
//
//                "COUNT(*) AS total_planos, " +
//
//                "SUM(CASE " +
//                "WHEN p.estado IN ('TERMINADO (CALIDAD)','CALIDAD') " +
//                "THEN 1 ELSE 0 END) AS terminados, " +
//
//                "SUM(CASE " +
//                "WHEN p.estado = 'SIN MATERIAL' " +
//                "THEN 1 ELSE 0 END) AS sin_material " +
//
//                "FROM planos p " +
//
//                "LEFT JOIN Proyectos pr " +
//                "ON pr.Proyecto = p.proyecto " +
//
//                filtro +
//
//                " GROUP BY p.proyecto, pr.FechaEntrega " +
//
//                " ORDER BY MAX(p.id) DESC";
//
//            ResultSet rs = st.executeQuery(sql);
//
//            while (rs.next()) {
//
//                String proy = rs.getString("proyecto");
//
//                String fechaEntrega = rs.getString("FechaEntrega");
//
//                if (fechaEntrega == null) {
//                    fechaEntrega = "";
//                }
//
//                int totalPlanos = rs.getInt("total_planos");
//
//                int terminados = rs.getInt("terminados");
//
//                int sinMaterial = rs.getInt("sin_material");
//
//                int pendientes = totalPlanos - terminados;
//
//                double avance = 0;
//
//                if (totalPlanos > 0) {
//                    avance = (terminados * 100.0) / totalPlanos;
//                }
//
//                modelo.addRow(new Object[]{
//                    proy,
//                    fechaEntrega,
//                    String.format("%.2f %%", avance),
//                    pendientes,
//                    sinMaterial
//                });
//            }

            String whereProyecto = (proyecto != null && !proyecto.equals(""))
                ? " AND p.proyecto = '" + proyecto + "'"
                : "";

            String sql =
                "SELECT " +
                "p.proyecto, " +
                "pr.FechaEntrega, " +

                "COUNT(*) AS total_planos, " +

                "SUM(CASE " +
                "WHEN p.estado IN ('TERMINADO (CALIDAD)','CALIDAD') " +
                "THEN 1 ELSE 0 END) AS terminados, " +

                "SUM(CASE " +
                "WHEN p.estado IN ('SIN MATERIAL', 'CORTES', 'MAQUINADOS') " +
                "THEN 1 ELSE 0 END) AS pendientes_count, " +

                "SUM(CASE " +
                "WHEN p.estado = 'SIN MATERIAL' " +
                "THEN 1 ELSE 0 END) AS sin_material " +

                "FROM planos p " +

                "INNER JOIN Proyectos pr " +
                "ON pr.Proyecto = p.proyecto " +

                "WHERE pr.Estatus NOT IN ('CERRADO', 'DETENIDO') " +
                whereProyecto +

                " GROUP BY p.proyecto, pr.FechaEntrega " +

                " ORDER BY MAX(p.id) DESC";

            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {

                String proy = rs.getString("proyecto");

                String fechaEntrega = rs.getString("FechaEntrega");

                if (fechaEntrega == null) {
                    fechaEntrega = "";
                }

                int totalPlanos = rs.getInt("total_planos");

                int terminados = rs.getInt("terminados");

                int pendientes = rs.getInt("pendientes_count");

                int sinMaterial = rs.getInt("sin_material");

                double avance = 0;

                if (totalPlanos > 0) {
                    avance = (terminados * 100.0) / totalPlanos;
                }

                modelo.addRow(new Object[]{
                    proy,
                    fechaEntrega,
                    String.format("%.2f %%", avance),
                    pendientes,
                    sinMaterial
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void cargarTurnoHoy() {
        try {
            Connection con = new Conexion().getConnection();

            // Obtener la fecha actual en formato dd/MM/yyyy
            java.time.LocalDate hoy = java.time.LocalDate.now();
            java.time.format.DateTimeFormatter formato = java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String fechaHoy = hoy.format(formato);

            int fresadora = 0;
            int torno = 0;
            int cnc = 0;

            Statement st = con.createStatement();

            // FRESADORA
            String sqlFresadora = "SELECT COUNT(*) total FROM fresadora " +
                                  "WHERE FechaFinal LIKE '" + fechaHoy + "%' AND Terminado = 'SI'";
            ResultSet rsF = st.executeQuery(sqlFresadora);
            if (rsF.next()) {
                fresadora = rsF.getInt("total");
            }

            // TORNO
            String sqlTorno = "SELECT COUNT(*) total FROM torno " +
                              "WHERE FechaFinal LIKE '" + fechaHoy + "%' AND Terminado = 'SI'";
            ResultSet rsT = st.executeQuery(sqlTorno);
            if (rsT.next()) {
                torno = rsT.getInt("total");
            }

            // CNC
            String sqlCNC = "SELECT COUNT(*) total FROM cnc " +
                            "WHERE FechaFinal LIKE '" + fechaHoy + "%' AND Terminado = 'SI'";
            ResultSet rsC = st.executeQuery(sqlCNC);
            if (rsC.next()) {
                cnc = rsC.getInt("total");
            }

            // Se arma el texto en múltiples renglones
            String texto = "<html>"
                        + "<div style='font-family: Roboto; font-size: 12px; font-weight: 900;'>"
                        + "Fresadora: " + fresadora + "<br>"
                        + "Torno: " + torno + "<br>"
                        + "CNC: " + cnc
                        + "</div>"
                        + "</html>";

            lblTurno.setText(texto);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void agregarProyectosAutoCompletar() {
        try {
            Connection con = new Conexion().getConnection();
            Statement st = con.createStatement();

            // Todos los proyectos ordenados de mayor a menor
            String sql = "SELECT proyecto, MAX(id) as ultimo_id FROM planos " +
                         "GROUP BY proyecto " +
                         "ORDER BY CAST(proyecto AS UNSIGNED) DESC";

            // Solo proyectos con estados específicos
    //        String sql = "SELECT proyecto, MAX(id) as ultimo_id FROM planos " +
    //                     "WHERE estado IN ('CORTES','ACABADOS','MAQUINADOS') " +
    //                     "GROUP BY proyecto " +
    //                     "ORDER BY CAST(proyecto AS UNSIGNED) DESC";

            ResultSet rs = st.executeQuery(sql);

            autoCompleter = new TextAutoCompleter(buscarProyectos);

            while (rs.next()) {
                autoCompleter.addItem(rs.getString("proyecto"));
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Error: " + e,
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
    
    public void cargarProyectoTexto() {
        String proyecto = buscarProyectos.getText().trim();

        // Si no hay texto, se muestran todos
        if (proyecto.equals("")) {
            cargarGraficaPlanos("");
            cargarGraficaHorasMaquinados("");
            cargarTablaProyecto("");
            return;
        }

        // Si hay texto, se filtra
        cargarGraficaPlanos(proyecto);
        cargarGraficaHorasMaquinados(proyecto);
        cargarTablaProyecto(proyecto);
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        tituloConvencional = new javax.swing.JLabel();
        lblConvencional = new javax.swing.JLabel();
        tituloCNC = new javax.swing.JLabel();
        lblCnc = new javax.swing.JLabel();
        tituloCortes = new javax.swing.JLabel();
        lblCortes = new javax.swing.JLabel();
        tituloAcabados = new javax.swing.JLabel();
        lblAcabados = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        lblTotal = new javax.swing.JLabel();
        tituloTurno = new javax.swing.JLabel();
        lblTurno = new javax.swing.JLabel();
        panelHoras = new javax.swing.JPanel();
        panelPlanos = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        buscarProyectos = new javax.swing.JTextField();
        jPanel13 = new javax.swing.JPanel();
        btnRecargar = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        btnExcel = new javax.swing.JButton();
        jPanel12 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        Tabla1 = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jPanel14 = new javax.swing.JPanel();
        btnSalir = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jPanel15 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();

        setBorder(null);
        getContentPane().setLayout(new java.awt.GridLayout(1, 0));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new java.awt.BorderLayout());

        jPanel4.setLayout(new java.awt.GridLayout(2, 0));

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setLayout(new java.awt.GridLayout(1, 0));

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));
        jPanel8.setLayout(new java.awt.GridLayout(6, 2, 5, 5));

        tituloConvencional.setFont(new java.awt.Font("Roboto Black", 1, 14)); // NOI18N
        tituloConvencional.setText("CONVENCIONAL");
        jPanel8.add(tituloConvencional);

        lblConvencional.setFont(new java.awt.Font("Roboto Black", 1, 14)); // NOI18N
        jPanel8.add(lblConvencional);

        tituloCNC.setFont(new java.awt.Font("Roboto Black", 1, 14)); // NOI18N
        tituloCNC.setText("CNC");
        jPanel8.add(tituloCNC);

        lblCnc.setFont(new java.awt.Font("Roboto Black", 1, 14)); // NOI18N
        jPanel8.add(lblCnc);

        tituloCortes.setFont(new java.awt.Font("Roboto Black", 1, 14)); // NOI18N
        tituloCortes.setText("CORTES");
        jPanel8.add(tituloCortes);

        lblCortes.setFont(new java.awt.Font("Roboto Black", 1, 14)); // NOI18N
        jPanel8.add(lblCortes);

        tituloAcabados.setFont(new java.awt.Font("Roboto Black", 1, 14)); // NOI18N
        tituloAcabados.setText("ACABADOS");
        jPanel8.add(tituloAcabados);

        lblAcabados.setFont(new java.awt.Font("Roboto Black", 1, 14)); // NOI18N
        jPanel8.add(lblAcabados);

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));

        jButton1.setBackground(new java.awt.Color(255, 102, 0));
        jButton1.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jButton1.setForeground(new java.awt.Color(0, 51, 255));
        jButton1.setText("VER CARGA GENERAL");
        jButton1.setBorder(null);
        jButton1.setBorderPainted(false);
        jButton1.setContentAreaFilled(false);
        jButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel7.add(jButton1);

        jPanel8.add(jPanel7);

        lblTotal.setFont(new java.awt.Font("Roboto Black", 1, 14)); // NOI18N
        jPanel8.add(lblTotal);

        tituloTurno.setFont(new java.awt.Font("Roboto Black", 1, 14)); // NOI18N
        tituloTurno.setText("PROD. POR TURNO");
        jPanel8.add(tituloTurno);
        jPanel8.add(lblTurno);

        jPanel5.add(jPanel8);

        panelHoras.setBackground(new java.awt.Color(255, 255, 255));
        panelHoras.setLayout(new java.awt.GridLayout(1, 0));
        jPanel5.add(panelHoras);

        panelPlanos.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout panelPlanosLayout = new javax.swing.GroupLayout(panelPlanos);
        panelPlanos.setLayout(panelPlanosLayout);
        panelPlanosLayout.setHorizontalGroup(
            panelPlanosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 310, Short.MAX_VALUE)
        );
        panelPlanosLayout.setVerticalGroup(
            panelPlanosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 470, Short.MAX_VALUE)
        );

        jPanel5.add(panelPlanos);

        jPanel4.add(jPanel5);

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setLayout(new java.awt.BorderLayout());

        jPanel10.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setText("Proyecto:");
        jPanel10.add(jLabel1);

        buscarProyectos.setPreferredSize(new java.awt.Dimension(200, 22));
        buscarProyectos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buscarProyectosActionPerformed(evt);
            }
        });
        jPanel10.add(buscarProyectos);

        jPanel13.setBackground(new java.awt.Color(255, 255, 255));

        btnRecargar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/recargar_16.png"))); // NOI18N
        btnRecargar.setBorder(null);
        btnRecargar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnRecargar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRecargarActionPerformed(evt);
            }
        });
        jPanel13.add(btnRecargar);

        jButton2.setBackground(new java.awt.Color(255, 102, 0));
        jButton2.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jButton2.setForeground(new java.awt.Color(0, 51, 255));
        jButton2.setText("VER");
        jButton2.setBorder(null);
        jButton2.setBorderPainted(false);
        jButton2.setContentAreaFilled(false);
        jButton2.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel13.add(jButton2);

        jPanel10.add(jPanel13);

        btnExcel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/excel_1.png"))); // NOI18N
        btnExcel.setBorder(null);
        btnExcel.setBorderPainted(false);
        btnExcel.setContentAreaFilled(false);
        btnExcel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnExcel.setFocusPainted(false);
        btnExcel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExcelActionPerformed(evt);
            }
        });
        jPanel10.add(btnExcel);

        jPanel6.add(jPanel10, java.awt.BorderLayout.PAGE_START);

        jPanel12.setLayout(new java.awt.GridLayout(1, 0));

        Tabla1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "PROYECTO", "FECHA DE ENTREGA", "AVANCE", "PLANOS PENDIENTES", "PLANOS SIN MATERIAL"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        Tabla1.setRowHeight(25);
        jScrollPane1.setViewportView(Tabla1);

        jPanel12.add(jScrollPane1);

        jPanel6.add(jPanel12, java.awt.BorderLayout.CENTER);

        jPanel4.add(jPanel6);

        jPanel1.add(jPanel4, java.awt.BorderLayout.CENTER);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new java.awt.BorderLayout());

        jPanel14.setBackground(new java.awt.Color(255, 255, 255));

        btnSalir.setBackground(new java.awt.Color(255, 255, 255));

        jLabel3.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N
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

        jPanel14.add(btnSalir);

        jPanel2.add(jPanel14, java.awt.BorderLayout.EAST);

        jPanel15.setBackground(new java.awt.Color(255, 255, 255));

        jLabel9.setFont(new java.awt.Font("Roboto", 1, 48)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 165, 252));
        jLabel9.setText("Carga de trabajo");
        jPanel15.add(jLabel9);

        jPanel2.add(jPanel15, java.awt.BorderLayout.CENTER);

        jPanel1.add(jPanel2, java.awt.BorderLayout.NORTH);

        getContentPane().add(jPanel1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        Espera espera = new Espera();
        espera.activar();
        espera.setVisible(true);
        verCarga();
        espera.setVisible(false);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        Espera espera = new Espera();
        espera.activar();
        espera.setVisible(true);
        btnExcel.setEnabled(true);
//        try{
//            limpiarTabla1();
//            Connection con = null;
//            Conexion con1 = new Conexion();
//            con = con1.getConnection();
//            int real = contProy;
//            if(jComboBox1.getSelectedIndex() != 0){
//                real = 1;
//            }
//            
//                for (int j = 0; j < real; j++) {
//                Statement st = con.createStatement();
//                
//                String proyecto = proyectos[j];
//                if(jComboBox1.getSelectedIndex() != 0){
//                proyecto = jComboBox1.getSelectedItem().toString();
//                }
//                
//                String sql = "select Plano, Proyecto from fresadora where Plano like '"+proyecto+"' and Terminado like 'NO'";
//                ResultSet rs = st.executeQuery(sql);
//                
//                String sql1 = "select Plano, Proyecto from cnc where Plano like '"+proyecto+"' and Terminado like 'NO'";
//                Statement st1 = con.createStatement();
//                ResultSet rs1 = st1.executeQuery(sql1);
//                
//                String sql3 = "select Plano, Proyecto from torno where Plano like '"+proyecto+"' and Terminado like 'NO'";
//                Statement st3 = con.createStatement();
//                ResultSet rs3 = st3.executeQuery(sql3);
//                
//                String sql4 = "select Plano, Proyecto from datos where Plano like '"+proyecto+"' and Terminado like 'NO'";
//                Statement st4 = con.createStatement();
//                ResultSet rs4 = st4.executeQuery(sql4);
//                
//                String datos[] = new String[15];
//                
//                String aux = "";
//                int cont = 0;
//                int acum = 0;
//                int contP = 0;
//                
//                String aux1 = "";
//                int cont1 = 0;
//                int acum1 = 0;
//                int contP1 = 0;
//                
//                String aux3 = "";
//                int cont3 = 0;
//                int acum3 = 0;
//                int contP3 = 0;
//                
//                String aux4 = "";
//                int cont4 = 0;
//                int acum4 = 0;
//                int contP4 = 0;
//                
//                while(rs.next()){
//                    datos[0] = rs.getString("Proyecto");
//                    String sql2 = "select Fresadora from Planos where Plano like '" + datos[0] + "'";
//                    Statement st2 = con.createStatement();
//                    ResultSet rs2 = st2.executeQuery(sql2);
//                    
//                    contP++;
//                    
//                    while(rs2.next()){
//                    datos[1] = rs2.getString("Fresadora");
//                    if(datos[1].equals("") || datos[1].equals("0")){
//
//                    }else{
//                    String buscar = "/";
//                    char arreglo[] = datos[1].toCharArray();
//                    for (int i = 0; i < datos[1].length(); i++) {
//                        String letra = String.valueOf(arreglo[i]);
//                        if(buscar.equalsIgnoreCase(letra)){
//                        cont = i;
//                        }
//                    }
//
//                    aux = datos[1].substring(0,cont);
//                    if(aux.equals("")){
//                        aux = "0";
//                    }
//                    int a = Integer.parseInt(aux);
//                    acum = (acum + a);
//                    }
//                    }
//            
//                    
//                }
//                
//                while(rs1.next()){
//                    datos[0] = rs1.getString("Proyecto");
//                    String sql2 = "select Cnc from Planos where Plano like '" + datos[0] + "'";
//                    Statement st2 = con.createStatement();
//                    ResultSet rs2 = st2.executeQuery(sql2);
//                    
//                    contP1++;
//                    
//                    while(rs2.next()){
//                    datos[1] = rs2.getString("Cnc");
//                    if(datos[1].equals("") || datos[1].equals("0")){
//
//                    }else{
//                    String buscar = "/";
//                    char arreglo[] = datos[1].toCharArray();
//                    for (int i = 0; i < datos[1].length(); i++) {
//                        String letra = String.valueOf(arreglo[i]);
//                        if(buscar.equalsIgnoreCase(letra)){
//                        cont1 = i;
//                        }
//                    }
//
//                    aux1 = datos[1].substring(0,cont1);
//                    if(aux1.equals("")){
//                        aux1 = "0";
//                    }
//                    int a = Integer.parseInt(aux1);
//                    acum1 = (acum1 + a);
//                    }
//                    }
//            
//                    
//                }
//                
//                while(rs3.next()){
//                    datos[0] = rs3.getString("Proyecto");
//                    String sql2 = "select Torno from Planos where Plano like '" + datos[0] + "'";
//                    Statement st2 = con.createStatement();
//                    ResultSet rs2 = st2.executeQuery(sql2);
//                    
//                    contP3++;
//                    
//                    while(rs2.next()){
//                    datos[1] = rs2.getString("Torno");
//                    if(datos[1].equals("") || datos[1].equals("0")){
//
//                    }else{
//                    String buscar = "/";
//                    char arreglo[] = datos[1].toCharArray();
//                    for (int i = 0; i < datos[1].length(); i++) {
//                        String letra = String.valueOf(arreglo[i]);
//                        if(buscar.equalsIgnoreCase(letra)){
//                        cont3 = i;
//                        }
//                    }
//
//                    aux3 = datos[1].substring(0,cont3);
//                    if(aux3.equals("")){
//                        aux3 = "0";
//                    }
//                    int a = Integer.parseInt(aux3);
//                    acum3 = (acum3 + a);
//                    }
//                    }
//                }
//                
//                while (rs4.next()) {
//                datos[0] = rs4.getString("Proyecto");
//                datos[1] = rs4.getString("Plano");
//                String sql2 = "select Fresadora,Cnc,Torno from Planos where Plano like '" + datos[0] + "'";
//                Statement st2 = con.createStatement();
//                ResultSet rs2 = st2.executeQuery(sql2);
//                contP4++;
//                while(rs2.next()){
//                datos[1] = rs2.getString("Fresadora");
//                datos[2] = rs2.getString("Cnc");
//                datos[3] = rs2.getString("Torno");
//                if(datos[1].equals("") || datos[1].equals("0")){
//                
//                }else{
//                String buscar = "/";
//                char arreglo[] = datos[1].toCharArray();
//                for (int i = 0; i < datos[1].length(); i++) {
//                    String letra = String.valueOf(arreglo[i]);
//                    if(buscar.equalsIgnoreCase(letra)){
//                    cont4 = i;
//                    }
//                }
//                
//                aux4 = datos[1].substring(0,cont4);
//                if(aux4.equals("")){
//                        aux4 = "0";
//                    }
//                int a = Integer.parseInt(aux4);
//                
//                acum4 = (acum4 + a);
//                
//                }
//                if(datos[2].equals("") || datos[2].equals("0")){
//                
//                }else{
//                String buscar = "/";
//                char arreglo[] = datos[2].toCharArray();
//                for (int i = 0; i < datos[2].length(); i++) {
//                    String letra = String.valueOf(arreglo[i]);
//                    if(buscar.equalsIgnoreCase(letra)){
//                    cont4 = i;
//                    }
//                }
//                
//                aux4 = datos[2].substring(0,cont4);
//                if(aux4.equals("")){
//                        aux4 = "0";
//                    }
//                int a = Integer.parseInt(aux4);
//                acum4 = (acum4 + a);
//                
//                }
//                if(datos[3].equals("") || datos[3].equals("0")){
//                
//                }else{
//                String buscar = "/";
//                char arreglo[] = datos[3].toCharArray();
//                for (int i = 0; i < datos[3].length(); i++) {
//                    String letra = String.valueOf(arreglo[i]);
//                    if(buscar.equalsIgnoreCase(letra)){
//                    cont4 = i;
//                    }
//                }
//                
//                aux4 = datos[3].substring(0,cont4);
//                if (aux4.equals("")){
//                    aux4 = "0";
//                }
//                int a = Integer.parseInt(aux4);
//                acum4 = (acum4 + a);
//                
//                }
//                }
//            }
//             String sql5 = "select * from Proyectos where Proyecto like '"+proyecto+"'";
//             Statement st5 = con.createStatement();
//             ResultSet rs5 = st5.executeQuery(sql5);
//             String fechaEntrega = "";
//             while(rs5.next()){
//                 fechaEntrega = rs5.getString("FechaEntrega");
//             }
//             String cortes = String.valueOf(acum4/60)+","+contP4;
//             String fresa = String.valueOf(acum/60)+","+contP;
//             String cnc = String.valueOf(acum1/60)+","+contP1;
//             String torno = String.valueOf(acum3/60)+","+contP3;
//             String tabla[] = {proyecto,fechaEntrega,cortes,fresa,cnc,torno};
//             DefaultTableModel miModelo = (DefaultTableModel) Tabla1.getModel();
//             miModelo.addRow(tabla);
//            }
//            
//            
//        }catch(SQLException e){
//            Logger.getLogger(OrdenDeCompra.class.getName()).log(Level.SEVERE, null, e);
//        }
        espera.dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void btnExcelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcelActionPerformed
        try {

            JFileChooser fc = new JFileChooser();
            fc.setDialogTitle("Guardar Excel");

            fc.setFileFilter(
                new FileNameExtensionFilter("Archivos Excel (*.xlsx)", "xlsx")
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

            // Se crea el libro
            XSSFWorkbook book = new XSSFWorkbook();
            Sheet hoja = book.createSheet("CARGA DE TRABAJO");

            // =========================
            // FUENTES Y ESTILOS
            // =========================

            // Título
            XSSFFont fontTitulo = book.createFont();
            fontTitulo.setBold(true);
            fontTitulo.setFontHeightInPoints((short) 18);

            CellStyle estiloTitulo = book.createCellStyle();
            estiloTitulo.setFont(fontTitulo);
            estiloTitulo.setAlignment(HorizontalAlignment.CENTER);

            // Encabezados
            XSSFFont fontHeader = book.createFont();
            fontHeader.setBold(true);
            fontHeader.setColor(IndexedColors.WHITE.getIndex());
            fontHeader.setFontHeightInPoints((short) 12);

            XSSFCellStyle estiloHeader = book.createCellStyle();
            estiloHeader.setFont(fontHeader);

            byte[] rgbHeader = Hex.decodeHex("13315C");
            XSSFColor colorHeader = new XSSFColor(rgbHeader, null);

            estiloHeader.setFillForegroundColor(colorHeader);
            estiloHeader.setFillPattern(FillPatternType.SOLID_FOREGROUND);

            estiloHeader.setAlignment(HorizontalAlignment.CENTER);
            estiloHeader.setVerticalAlignment(VerticalAlignment.CENTER);
            estiloHeader.setWrapText(true);

            // Filas normales
            CellStyle estiloFila = book.createCellStyle();
            estiloFila.setAlignment(HorizontalAlignment.CENTER);

            // Filas alternadas
            XSSFCellStyle estiloAlternado = book.createCellStyle();
            estiloAlternado.setAlignment(HorizontalAlignment.CENTER);

            byte[] rgbAlt = Hex.decodeHex("EEF4ED");
            XSSFColor colorAlt = new XSSFColor(rgbAlt, null);

            estiloAlternado.setFillForegroundColor(colorAlt);
            estiloAlternado.setFillPattern(FillPatternType.SOLID_FOREGROUND);

            // =========================
            // TÍTULO
            // =========================

            Row filaTitulo = hoja.createRow(1);

            Cell celdaTitulo = filaTitulo.createCell(0);
            celdaTitulo.setCellValue("CARGA DE TRABAJO");
            celdaTitulo.setCellStyle(estiloTitulo);

            hoja.addMergedRegion(new CellRangeAddress(
                1,
                1,
                0,
                4
            ));

            // =========================
            // ENCABEZADOS
            // =========================

            Row filaHeader = hoja.createRow(3);

            String[] columnas = {
                "PROYECTO",
                "FECHA DE ENTREGA",
                "AVANCE",
                "PLANOS PENDIENTES",
                "PLANOS SIN MATERIAL"
            };

            for (int i = 0; i < columnas.length; i++) {

                Cell celda = filaHeader.createCell(i);

                celda.setCellValue(columnas[i]);
                celda.setCellStyle(estiloHeader);
            }

            // =========================
            // DATOS DE LA TABLA
            // =========================

            int filaExcel = 4;

            for (int i = 0; i < Tabla1.getRowCount(); i++) {

                Row fila = hoja.createRow(filaExcel);

                for (int j = 0; j < Tabla1.getColumnCount(); j++) {

                    Cell celda = fila.createCell(j);

                    Object valor = Tabla1.getValueAt(i, j);

                    if (valor != null) {
                        celda.setCellValue(valor.toString());
                    } else {
                        celda.setCellValue("");
                    }

                    // Filas alternadas
                    if (i % 2 == 0) {
                        celda.setCellStyle(estiloAlternado);
                    } else {
                        celda.setCellStyle(estiloFila);
                    }
                }

                filaExcel++;
            }

            // =========================
            // AJUSTAR TAMAÑOS
            // =========================

            hoja.setColumnWidth(0, 9000);
            hoja.setColumnWidth(1, 6000);
            hoja.setColumnWidth(2, 5000);
            hoja.setColumnWidth(3, 6000);
            hoja.setColumnWidth(4, 7000);

            // =========================
            // GUARDAR
            // =========================

            FileOutputStream out = new FileOutputStream(ruta);

            book.write(out);

            out.close();
            book.close();

            // =========================
            // ABRIR AUTOMÁTICAMENTE
            // =========================

            Desktop.getDesktop().open(new File(ruta));

        } catch (Exception e) {
            e.printStackTrace();

            JOptionPane.showMessageDialog(
                this,
                "Error al generar Excel:\n" + e
            );
        }
    }//GEN-LAST:event_btnExcelActionPerformed

    private void jLabel3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MouseClicked
        dispose();
    }//GEN-LAST:event_jLabel3MouseClicked

    private void jLabel3MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MouseEntered
        btnSalir.setBackground(java.awt.Color.red);
    }//GEN-LAST:event_jLabel3MouseEntered

    private void jLabel3MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MouseExited
        btnSalir.setBackground(java.awt.Color.white);
    }//GEN-LAST:event_jLabel3MouseExited

    private void btnRecargarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRecargarActionPerformed
        // TODO add your handling code here:
        buscarProyectos.setText("");
        cargarProyectoTexto();
    }//GEN-LAST:event_btnRecargarActionPerformed

    private void buscarProyectosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buscarProyectosActionPerformed
        cargarProyectoTexto();
    }//GEN-LAST:event_buscarProyectosActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable Tabla1;
    private javax.swing.JButton btnExcel;
    private javax.swing.JButton btnRecargar;
    private javax.swing.JPanel btnSalir;
    private javax.swing.JTextField buscarProyectos;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblAcabados;
    private javax.swing.JLabel lblCnc;
    private javax.swing.JLabel lblConvencional;
    private javax.swing.JLabel lblCortes;
    private javax.swing.JLabel lblTotal;
    private javax.swing.JLabel lblTurno;
    private javax.swing.JPanel panelHoras;
    private javax.swing.JPanel panelPlanos;
    private javax.swing.JLabel tituloAcabados;
    private javax.swing.JLabel tituloCNC;
    private javax.swing.JLabel tituloConvencional;
    private javax.swing.JLabel tituloCortes;
    private javax.swing.JLabel tituloTurno;
    // End of variables declaration//GEN-END:variables
}
