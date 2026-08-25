package VentanaEmergente.KPI;

import Conexiones.Conexion;
import java.awt.Color;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.WeekFields;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Stack;
import java.util.stream.Collectors;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import org.knowm.xchart.CategoryChart;
import org.knowm.xchart.CategoryChartBuilder;
import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.style.Styler;
import org.jsoup.Jsoup;

public class ComprasKPI extends javax.swing.JInternalFrame {

    public String numEmpleado;
    public String depa;
    public Stack<Integer> semanas;
    
    public final void limpiarPanel() {
        pnlGraficos = new javax.swing.JPanel();
        pnlGraficos.setBackground(new java.awt.Color(255, 255, 255));
        pnlGraficos.setLayout(new java.awt.GridLayout(1, 0));
        java.awt.GridBagConstraints gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        jPanel2.add(pnlGraficos, gridBagConstraints);
    }
    
    public final void crearPanelDona() {
        JPanel panel = new JPanel();
        panel.setBackground(new java.awt.Color(255, 255, 255));
        panel.setLayout(new java.awt.BorderLayout());
        pnlGraficos.add(panel);
    }
    
    public final int buscarMes(int semana) {
        for (int i = 0; i < semanas.size() - 1; i++) {
            int primero = (i ==0) ? semanas.get(i) : (semanas.get(i) + 1);
            int segundo = semanas.get(i + 1);
            if (semana >= primero && semana <= segundo) {
                return i;
            }
            System.out.println("Mes: " + (i + 1) + " - " + primero + " - " + segundo);
        }
        return -1;
    }
    
    public final void getKPIs() {
        try {
            Connection con;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
            Date date = new Date();
            int ano = Integer.parseInt(sdf.format(date));
            Statement st = con.createStatement();
            String sql = "select * from kpi where Fecha between '" + ano + "-01-01' and '" + ano + "-12-31' order by kpi";
            ResultSet rs = st.executeQuery(sql);
            Stack<Stack<String[]>> pila = new Stack<>();
            int cont = -1;
            String kpiAnt = "";
            while(rs.next()) {
                String kpi = Jsoup.parse((rs.getString("kpi"))).text();
                if (!kpi.equals(kpiAnt)) {
                    pila.add(new Stack<>());
                    kpiAnt = kpi;
                    cont++;
                }
                String datos[] = new String[3];
                datos[0] = kpi;
                datos[1] = (rs.getString("Fecha"));
                datos[2] = (rs.getString("Valor"));
                pila.get(cont).push(datos);
            }
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            for (int i = 0; i < pila.size(); i++) {
                int meses[] = new int[12];
                for (int j = 0; j < pila.get(i).size(); j++) {
                    LocalDate fecha = LocalDate.parse(pila.get(i).get(j)[1], formatter);
                    WeekFields weekFields = WeekFields.of(Locale.getDefault());
                    int numeroSemana = fecha.get(weekFields.weekOfWeekBasedYear());
                    int mesDeLaSemana = buscarMes(numeroSemana);
//                    int mesDeLaSemana =1;
                    System.out.println(numeroSemana + " - Mes: " + mesDeLaSemana);
                    meses[mesDeLaSemana] += Integer.parseInt(pila.get(i).get(j)[2]);
                    meses[mesDeLaSemana] = (j ==0) ? (meses[mesDeLaSemana] /= 1) : (meses[mesDeLaSemana] /= 2);
                }
                if(i == 0){
                    createDona(panelRequiAtrasadas,new Color(0,102,204, 180), meses);
                } else {
                    createDona(panelPoAtrasadas,new Color(193,152,29, 180), meses);
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error: " +e , "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private final void createDona(JPanel panel, Color color, int[] ent) {
        CategoryChart chart = new CategoryChartBuilder()
                .width(600).height(800)
                .title("Comparación")
                .xAxisTitle("Mes")
                .yAxisTitle("Porcentaje")
                .build();

        chart.getStyler().setStacked(false);
        chart.getStyler().setOverlapped(true);
        chart.getStyler().setLegendPosition(Styler.LegendPosition.InsideNW);

        List<String> categorias = Arrays.asList("Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Nov", "Dic");
        List<Integer> valores1 = Arrays.asList(90, 90, 90, 90, 90,90,90,90,90,90,90,90);
        List<Integer> valores2 = Arrays.stream(ent).boxed().collect(Collectors.toList());
        chart.addSeries("Meta", categorias, valores1).setFillColor(new Color(230,230,230,200)); // Azul semi-transparente
        chart.addSeries("Datos", categorias, valores2).setFillColor(color); // Rojo semi-transparente
        XChartPanel<CategoryChart> chartPanel = new XChartPanel<>(chart);
        panel.add(chartPanel);
    }
    
    public final void insertarSemanas() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        Date d = new Date();
        int year = Integer.parseInt(sdf.format(d)) + 1;
        semanas = new Stack<>();
        semanas.add(1);
        lblMeses.setText("<html> <p style='width:900px;'>");
        for (int mes = 1; mes <= 12; mes++) {
            LocalDate inicioMes = LocalDate.of(year, mes, 1);
            LocalDate finMes = inicioMes.withDayOfMonth(inicioMes.lengthOfMonth());

            WeekFields weekFields = WeekFields.of(Locale.getDefault());

            int semanaInicio = inicioMes.get(weekFields.weekOfWeekBasedYear());
            int semanaFin = finMes.get(weekFields.weekOfWeekBasedYear());
            semanas.add(semanaFin);
            lblMeses.setText(lblMeses.getText() + "(<span style='color: rgb(90,90,90);'>" + Month.of(mes) + "</span>" + " Semanas: " + (semanaInicio + 1) + " -> " + semanaFin + ") ");
        }
        lblMeses.setText(lblMeses.getText() + "</p>");
    }
    
    public ComprasKPI(String numEmpleado, String depa) {
        initComponents();
        this.numEmpleado = numEmpleado;
        this.depa = depa;
        insertarSemanas();
        getKPIs();
        ((javax.swing.plaf.basic.BasicInternalFrameUI) this.getUI()).setNorthPane(null);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        pnlGraficos = new javax.swing.JPanel();
        panelRequiAtrasadas = new javax.swing.JPanel();
        panelPoAtrasadas = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        pan = new javax.swing.JPanel();
        panelSalir = new javax.swing.JPanel();
        lblSalir = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        lblMeses = new javax.swing.JLabel();

        setBorder(null);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new java.awt.BorderLayout());

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        java.awt.GridBagLayout jPanel2Layout = new java.awt.GridBagLayout();
        jPanel2Layout.columnWeights = new double[] {1.0, 1.0, 1.0};
        jPanel2Layout.rowWeights = new double[] {0.0, 0.0, 0.0, 1.0};
        jPanel2.setLayout(jPanel2Layout);

        jLabel1.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 51, 51));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("KPI");
        jLabel1.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(204, 204, 204)));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.insets = new java.awt.Insets(0, 40, 0, 40);
        jPanel2.add(jLabel1, gridBagConstraints);

        jLabel4.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(51, 51, 51));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Tiempo");
        jLabel4.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(204, 204, 204)));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.insets = new java.awt.Insets(0, 40, 0, 40);
        jPanel2.add(jLabel4, gridBagConstraints);

        jLabel2.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(51, 51, 51));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Medicion (%)");
        jLabel2.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(204, 204, 204)));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.insets = new java.awt.Insets(0, 40, 0, 40);
        jPanel2.add(jLabel2, gridBagConstraints);

        jLabel3.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 102, 204));
        jLabel3.setText("<html> <p style='width: 500px;'>Requisiciones atrasadas (requisiciones con mas de 3 dias sin PO colocada) (semanalmente)</p>");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(14, 40, 14, 40);
        jPanel2.add(jLabel3, gridBagConstraints);

        jLabel5.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 102, 204));
        jLabel5.setText("Semanal");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.insets = new java.awt.Insets(14, 40, 14, 40);
        jPanel2.add(jLabel5, gridBagConstraints);

        jLabel6.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(193, 152, 29));
        jLabel6.setText("<html> <p style='width: 500px;'>PO´s atrasadas (PO´s con mas de 3 dias con TE vencido) (semanalmente)\n</p>");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.insets = new java.awt.Insets(14, 40, 14, 40);
        jPanel2.add(jLabel6, gridBagConstraints);

        jLabel10.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(193, 152, 29));
        jLabel10.setText("Semanal");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.insets = new java.awt.Insets(14, 40, 14, 40);
        jPanel2.add(jLabel10, gridBagConstraints);

        jButton1.setBackground(new java.awt.Color(222, 222, 222));
        jButton1.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        jButton1.setForeground(new java.awt.Color(0, 102, 204));
        jButton1.setText("Agregar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 12;
        gridBagConstraints.ipady = 12;
        jPanel2.add(jButton1, gridBagConstraints);

        jButton2.setBackground(new java.awt.Color(222, 222, 222));
        jButton2.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        jButton2.setForeground(new java.awt.Color(193, 152, 29));
        jButton2.setText("Agregar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 12;
        gridBagConstraints.ipady = 12;
        jPanel2.add(jButton2, gridBagConstraints);

        pnlGraficos.setBackground(new java.awt.Color(255, 255, 255));
        pnlGraficos.setLayout(new java.awt.GridLayout(1, 0));

        panelRequiAtrasadas.setBackground(new java.awt.Color(255, 255, 255));
        panelRequiAtrasadas.setLayout(new java.awt.BorderLayout());
        pnlGraficos.add(panelRequiAtrasadas);

        panelPoAtrasadas.setBackground(new java.awt.Color(255, 255, 255));
        panelPoAtrasadas.setLayout(new java.awt.BorderLayout());
        pnlGraficos.add(panelPoAtrasadas);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        jPanel2.add(pnlGraficos, gridBagConstraints);

        jPanel1.add(jPanel2, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setLayout(new java.awt.BorderLayout());

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));

        jLabel12.setFont(new java.awt.Font("Lexend", 1, 24)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(0, 102, 204));
        jLabel12.setText("KPI Compras");
        jPanel6.add(jLabel12);

        jPanel5.add(jPanel6);

        jPanel4.add(jPanel5, java.awt.BorderLayout.CENTER);

        pan.setBackground(new java.awt.Color(255, 255, 255));

        panelSalir.setBackground(new java.awt.Color(255, 255, 255));

        lblSalir.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        lblSalir.setForeground(new java.awt.Color(0, 0, 0));
        lblSalir.setText(" < ");
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

        jPanel4.add(pan, java.awt.BorderLayout.WEST);

        getContentPane().add(jPanel4, java.awt.BorderLayout.NORTH);

        jPanel7.setBackground(new java.awt.Color(204, 204, 204));
        jPanel7.setMaximumSize(new java.awt.Dimension(200, 200));

        lblMeses.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        lblMeses.setForeground(new java.awt.Color(0, 102, 255));
        jPanel7.add(lblMeses);

        getContentPane().add(jPanel7, java.awt.BorderLayout.PAGE_END);

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
        Stack<JLabel> labels = new Stack<>();
        labels.add(jLabel3);
        labels.add(jLabel6);
        JFrame f = (JFrame) JOptionPane.getFrameForComponent(this);
        InsertarKPI ins = new InsertarKPI(f, true, labels, numEmpleado, depa);
        ins.lblIngreso.setText("Semanal");
        ins.setLocationRelativeTo(f);
        ins.setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        Stack<JLabel> labels = new Stack<>();
        labels.add(jLabel3);
        labels.add(jLabel6);
        JFrame f = (JFrame) JOptionPane.getFrameForComponent(this);
        InsertarKPI ins = new InsertarKPI(f, true, labels, numEmpleado, depa);
        ins.lblIngreso.setText("Semanal");
        ins.setLocationRelativeTo(f);
        ins.setVisible(true);
    }//GEN-LAST:event_jButton2ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JLabel lblMeses;
    private javax.swing.JLabel lblSalir;
    private javax.swing.JPanel pan;
    private javax.swing.JPanel panelPoAtrasadas;
    private javax.swing.JPanel panelRequiAtrasadas;
    private javax.swing.JPanel panelSalir;
    private javax.swing.JPanel pnlGraficos;
    // End of variables declaration//GEN-END:variables
}
