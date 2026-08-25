package VentanaEmergente.Maquinados;

import Conexiones.Conexion;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class ReporteMaquinados extends java.awt.Dialog {

    public final void setDia(){
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMMM-yyyy");
        SimpleDateFormat sdf2 = new SimpleDateFormat("w");
        SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy");
        Date d = new Date();
        anio.setValue(Integer.parseInt(sdf3.format(d)));
        semana.setValue(Integer.parseInt(sdf2.format(d)));
        lblDia.setText(sdf.format(d));
        
    }
    
    public final void setEvent(JTextField text, JTextField text2, JLabel TR, JLabel dispo, JLabel mttr, JLabel mtbf){
        DecimalFormat decimal = new DecimalFormat("##.##");
        KeyListener key = new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                
            }

            @Override
            public void keyPressed(KeyEvent e) {
                
            }

            @Override
            public void keyReleased(KeyEvent e) {
                try{TR.setText((String.valueOf(86 - Double.parseDouble(text.getText()))));}catch(Exception ex){TR.setText("Error");}
                try{dispo.setText(decimal.format((((86 - Double.parseDouble(text.getText()))/86))*100) + "%");}catch(Exception ex){dispo.setText("Error");System.out.println(ex);}
                try{mttr.setText(decimal.format(((86 - Double.parseDouble(text.getText()))/Double.parseDouble(dispo.getText().replace("%", "")))));}catch(Exception ex){mttr.setText("Error");}
                try{mtbf.setText(decimal.format(((Double.parseDouble(TR.getText())/Double.parseDouble(text2.getText())))));}catch(Exception ex){mtbf.setText("Error");}
            }
        };
        text.addKeyListener(key);
        text2.addKeyListener(key);
    }
    
    public void setJson(String jsonString, JTextField text, JTextField text2, JLabel TP) throws JsonProcessingException{
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(jsonString);
        String tp = jsonNode.get("tp").asText();
        String sp = jsonNode.get("sp").asText();
        String pp = jsonNode.get("pp").asText();
        text.setText(sp);
        text2.setText(pp);
        TP.setText(tp);
    }
    
    public void verReporte(int anio, int semana){
        try {
            Connection con;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            String sql = "select * from reporte_maquinados where Anio like '" + anio + "' and Semana like '" + semana + "'";
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
                try{
                    String cnc1 = rs.getString("Cnc1");
                    setJson(cnc1, txtCnc1, txtCnc11, TP1);
                    String cnc2 = rs.getString("Cnc2");
                    setJson(cnc2, txtCnc2, txtCnc2, TP2);
                    String cnc3 = rs.getString("Cnc3");
                    setJson(cnc3, txtCnc3, txtCnc3, TP3);
                    String cnc4 = rs.getString("Cnc4");
                    setJson(cnc4, txtCnc4, txtCnc4, TP4);
                    String cnc5 = rs.getString("Cnc5");
                    setJson(cnc5, txtCnc5, txtCnc5, TP5);
                    String fresa1 = rs.getString("Fresa1");
                    setJson(fresa1, txtFresa1, txtFresa11, TP6);
                    String fresa2 = rs.getString("Fresa2");
                    setJson(fresa2, txtFresa2, txtFresa21, TP7);
                    String fresa3 = rs.getString("Fresa3");
                    setJson(fresa3, txtFresa3, txtFresa31, TP8);
                    String fresa4 = rs.getString("Fresa4");
                    setJson(fresa4, txtFresa4, txtFresa41, TP9);
                    String torno = rs.getString("Torno");
                    setJson(torno, txtTorno, txtTorno1, TP10);
                    String recti = rs.getString("Recti");
                    setJson(recti, txtRecti, txtRecti1, TP11);
                } catch (JsonProcessingException e){
                    JOptionPane.showMessageDialog(this, "Error: " + e, "Error",JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error: " + e, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public String formatoJSON(JLabel tp, JTextField sp, JTextField pp){
        char comillas = '"';
        String json = "{"
                + comillas + "tp" + comillas + ":" + comillas + tp.getText() + comillas + ","
                + comillas + "sp" + comillas + ":" + comillas + sp.getText() + comillas + ","
                + comillas + "pp" + comillas + ":" + comillas + pp.getText() + comillas + "}"
                ;
        return json;
    }
    
    public ReporteMaquinados(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setDia();
        setEvent(txtCnc1, txtCnc11, lblTR1, lblDispo1, lblMTTR1, lblMTBF1);
        setEvent(txtCnc2, txtCnc21, lblTR2, lblDispo2, lblMTTR2, lblMTBF2);
        setEvent(txtCnc3, txtCnc31, lblTR3, lblDispo3, lblMTTR3, lblMTBF3);
        setEvent(txtCnc4, txtCnc41, lblTR4, lblDispo4, lblMTTR4, lblMTBF4);
        setEvent(txtCnc5, txtCnc51, lblTR5, lblDispo5, lblMTTR5, lblMTBF5);
        setEvent(txtFresa1, txtFresa11, lblTR6, lblDispo6, lblMTTR6, lblMTBF6);
        setEvent(txtFresa2, txtFresa21, lblTR7, lblDispo7, lblMTTR7, lblMTBF7);
        setEvent(txtFresa3, txtFresa31, lblTR8, lblDispo8, lblMTTR8, lblMTBF8);
        setEvent(txtFresa4, txtFresa41, lblTR9, lblDispo9, lblMTTR9, lblMTBF9);
        setEvent(txtTorno, txtTorno1, lblTR10, lblDispo10, lblMTTR10, lblMTBF10);
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        panelReporte = new scrollPane.PanelRound();
        jButton2 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        TP1 = new javax.swing.JLabel();
        TP2 = new javax.swing.JLabel();
        TP3 = new javax.swing.JLabel();
        TP4 = new javax.swing.JLabel();
        TP5 = new javax.swing.JLabel();
        TP6 = new javax.swing.JLabel();
        TP7 = new javax.swing.JLabel();
        TP8 = new javax.swing.JLabel();
        TP9 = new javax.swing.JLabel();
        TP10 = new javax.swing.JLabel();
        TP11 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        txtCnc1 = new javax.swing.JTextField();
        txtCnc2 = new javax.swing.JTextField();
        txtCnc3 = new javax.swing.JTextField();
        txtCnc4 = new javax.swing.JTextField();
        txtCnc5 = new javax.swing.JTextField();
        txtFresa1 = new javax.swing.JTextField();
        txtFresa2 = new javax.swing.JTextField();
        txtFresa3 = new javax.swing.JTextField();
        txtFresa4 = new javax.swing.JTextField();
        txtTorno = new javax.swing.JTextField();
        txtRecti = new javax.swing.JTextField();
        lblTR1 = new javax.swing.JLabel();
        lblDispo1 = new javax.swing.JLabel();
        lblMTTR1 = new javax.swing.JLabel();
        lblMTBF1 = new javax.swing.JLabel();
        txtCnc11 = new javax.swing.JTextField();
        txtCnc21 = new javax.swing.JTextField();
        txtCnc31 = new javax.swing.JTextField();
        txtCnc41 = new javax.swing.JTextField();
        txtCnc51 = new javax.swing.JTextField();
        txtFresa11 = new javax.swing.JTextField();
        txtFresa21 = new javax.swing.JTextField();
        txtFresa31 = new javax.swing.JTextField();
        txtFresa41 = new javax.swing.JTextField();
        txtTorno1 = new javax.swing.JTextField();
        txtRecti1 = new javax.swing.JTextField();
        lblTR2 = new javax.swing.JLabel();
        lblTR3 = new javax.swing.JLabel();
        lblTR4 = new javax.swing.JLabel();
        lblTR5 = new javax.swing.JLabel();
        lblTR6 = new javax.swing.JLabel();
        lblTR7 = new javax.swing.JLabel();
        lblTR8 = new javax.swing.JLabel();
        lblTR9 = new javax.swing.JLabel();
        lblTR10 = new javax.swing.JLabel();
        lblTR11 = new javax.swing.JLabel();
        lblDispo2 = new javax.swing.JLabel();
        lblDispo3 = new javax.swing.JLabel();
        lblDispo4 = new javax.swing.JLabel();
        lblDispo5 = new javax.swing.JLabel();
        lblDispo6 = new javax.swing.JLabel();
        lblDispo7 = new javax.swing.JLabel();
        lblDispo8 = new javax.swing.JLabel();
        lblDispo9 = new javax.swing.JLabel();
        lblDispo10 = new javax.swing.JLabel();
        lblDispo11 = new javax.swing.JLabel();
        lblMTTR2 = new javax.swing.JLabel();
        lblMTTR3 = new javax.swing.JLabel();
        lblMTTR4 = new javax.swing.JLabel();
        lblMTTR5 = new javax.swing.JLabel();
        lblMTTR6 = new javax.swing.JLabel();
        lblMTTR7 = new javax.swing.JLabel();
        lblMTTR8 = new javax.swing.JLabel();
        lblMTTR9 = new javax.swing.JLabel();
        lblMTTR10 = new javax.swing.JLabel();
        lblMTTR11 = new javax.swing.JLabel();
        lblMTBF2 = new javax.swing.JLabel();
        lblMTBF3 = new javax.swing.JLabel();
        lblMTBF4 = new javax.swing.JLabel();
        lblMTBF5 = new javax.swing.JLabel();
        lblMTBF6 = new javax.swing.JLabel();
        lblMTBF7 = new javax.swing.JLabel();
        lblMTBF8 = new javax.swing.JLabel();
        lblMTBF9 = new javax.swing.JLabel();
        lblMTBF10 = new javax.swing.JLabel();
        lblMTBF11 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        lblDia = new javax.swing.JLabel();
        anio = new com.toedter.components.JSpinField();
        lblDia1 = new javax.swing.JLabel();
        semana = new com.toedter.calendar.JYearChooser();

        setPreferredSize(new java.awt.Dimension(1000, 524));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                closeDialog(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new java.awt.BorderLayout());

        jLabel1.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 153, 255));
        jLabel1.setText("Kpis Maquinado");
        jPanel1.add(jLabel1, java.awt.BorderLayout.PAGE_START);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        panelReporte.setBackground(new java.awt.Color(255, 102, 0));
        panelReporte.setRoundBottomRight(20);
        panelReporte.setRoundTopLeft(20);
        panelReporte.setRoundTopRight(20);
        panelReporte.setLayout(new java.awt.BorderLayout());

        jButton2.setBackground(new java.awt.Color(255, 255, 255));
        jButton2.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("Guardar");
        jButton2.setBorder(null);
        jButton2.setBorderPainted(false);
        jButton2.setContentAreaFilled(false);
        jButton2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton2.setFocusPainted(false);
        jButton2.setPreferredSize(new java.awt.Dimension(150, 25));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        panelReporte.add(jButton2, java.awt.BorderLayout.CENTER);

        jPanel3.add(panelReporte);

        jPanel1.add(jPanel3, java.awt.BorderLayout.PAGE_END);

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setLayout(new java.awt.BorderLayout());

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        java.awt.GridBagLayout jPanel2Layout = new java.awt.GridBagLayout();
        jPanel2Layout.columnWeights = new double[] {1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0};
        jPanel2.setLayout(jPanel2Layout);

        jLabel2.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 102, 0));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Equipo");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 4, 0, 4);
        jPanel2.add(jLabel2, gridBagConstraints);

        jLabel3.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 102, 0));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("<html>Tiempo total planificado (TP) / Semana");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 4, 0, 4);
        jPanel2.add(jLabel3, gridBagConstraints);

        jLabel4.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 102, 0));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("<html>Sumatoria de paradas (TP) / Semana");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 4, 0, 4);
        jPanel2.add(jLabel4, gridBagConstraints);

        jLabel5.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 102, 0));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("<html>Numero de periodos de paradas");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 4, 0, 4);
        jPanel2.add(jLabel5, gridBagConstraints);

        jLabel6.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 102, 0));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("<html>Tiempo real de actividad");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 4, 0, 4);
        jPanel2.add(jLabel6, gridBagConstraints);

        jLabel7.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 102, 0));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("DISPONIBILIDAD");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 4, 0, 4);
        jPanel2.add(jLabel7, gridBagConstraints);

        jLabel8.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 102, 0));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("<html>Tiempo medio de recuperacion (MTTR)");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 4, 0, 4);
        jPanel2.add(jLabel8, gridBagConstraints);

        jLabel9.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 102, 0));
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("<html>Tiempo medio entre fallos (MTBF)");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 4, 0, 4);
        jPanel2.add(jLabel9, gridBagConstraints);

        jLabel10.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 102, 0));
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("<html>Cumplimiento de mantenimientos");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 4, 0, 4);
        jPanel2.add(jLabel10, gridBagConstraints);

        jLabel11.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 102, 0));
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("<html>Clasificacion de paradas");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 4, 0, 4);
        jPanel2.add(jLabel11, gridBagConstraints);

        jLabel12.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(51, 51, 51));
        jLabel12.setText("CNC #1");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        jPanel2.add(jLabel12, gridBagConstraints);

        TP1.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        TP1.setForeground(new java.awt.Color(51, 51, 51));
        TP1.setText("86");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        jPanel2.add(TP1, gridBagConstraints);

        TP2.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        TP2.setForeground(new java.awt.Color(51, 51, 51));
        TP2.setText("86");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        jPanel2.add(TP2, gridBagConstraints);

        TP3.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        TP3.setForeground(new java.awt.Color(51, 51, 51));
        TP3.setText("86");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        jPanel2.add(TP3, gridBagConstraints);

        TP4.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        TP4.setForeground(new java.awt.Color(51, 51, 51));
        TP4.setText("86");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        jPanel2.add(TP4, gridBagConstraints);

        TP5.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        TP5.setForeground(new java.awt.Color(51, 51, 51));
        TP5.setText("86");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        jPanel2.add(TP5, gridBagConstraints);

        TP6.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        TP6.setForeground(new java.awt.Color(51, 51, 51));
        TP6.setText("86");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        jPanel2.add(TP6, gridBagConstraints);

        TP7.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        TP7.setForeground(new java.awt.Color(51, 51, 51));
        TP7.setText("86");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        jPanel2.add(TP7, gridBagConstraints);

        TP8.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        TP8.setForeground(new java.awt.Color(51, 51, 51));
        TP8.setText("86");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        jPanel2.add(TP8, gridBagConstraints);

        TP9.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        TP9.setForeground(new java.awt.Color(51, 51, 51));
        TP9.setText("86");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        jPanel2.add(TP9, gridBagConstraints);

        TP10.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        TP10.setForeground(new java.awt.Color(51, 51, 51));
        TP10.setText("86");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        jPanel2.add(TP10, gridBagConstraints);

        TP11.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        TP11.setForeground(new java.awt.Color(51, 51, 51));
        TP11.setText("86");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        jPanel2.add(TP11, gridBagConstraints);

        jLabel24.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(51, 51, 51));
        jLabel24.setText("CNC #2");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        jPanel2.add(jLabel24, gridBagConstraints);

        jLabel25.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(51, 51, 51));
        jLabel25.setText("CNC #3");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        jPanel2.add(jLabel25, gridBagConstraints);

        jLabel26.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(51, 51, 51));
        jLabel26.setText("CNC #4");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        jPanel2.add(jLabel26, gridBagConstraints);

        jLabel27.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(51, 51, 51));
        jLabel27.setText("CNC #5");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        jPanel2.add(jLabel27, gridBagConstraints);

        jLabel28.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(51, 51, 51));
        jLabel28.setText("FRESADORA #1");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        jPanel2.add(jLabel28, gridBagConstraints);

        jLabel29.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(51, 51, 51));
        jLabel29.setText("FRESADORA #2");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        jPanel2.add(jLabel29, gridBagConstraints);

        jLabel30.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel30.setForeground(new java.awt.Color(51, 51, 51));
        jLabel30.setText("FRESADORA #3");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        jPanel2.add(jLabel30, gridBagConstraints);

        jLabel31.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel31.setForeground(new java.awt.Color(51, 51, 51));
        jLabel31.setText("FRESADORA #4");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        jPanel2.add(jLabel31, gridBagConstraints);

        jLabel32.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel32.setForeground(new java.awt.Color(51, 51, 51));
        jLabel32.setText("TORNO");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        jPanel2.add(jLabel32, gridBagConstraints);

        jLabel33.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel33.setForeground(new java.awt.Color(51, 51, 51));
        jLabel33.setText("RECTIFICADORA");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        jPanel2.add(jLabel33, gridBagConstraints);

        txtCnc1.setBackground(new java.awt.Color(255, 255, 255));
        txtCnc1.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        txtCnc1.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(204, 204, 204)));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipady = 10;
        gridBagConstraints.insets = new java.awt.Insets(0, 4, 0, 4);
        jPanel2.add(txtCnc1, gridBagConstraints);

        txtCnc2.setBackground(new java.awt.Color(255, 255, 255));
        txtCnc2.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        txtCnc2.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(204, 204, 204)));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipady = 10;
        gridBagConstraints.insets = new java.awt.Insets(0, 4, 0, 4);
        jPanel2.add(txtCnc2, gridBagConstraints);

        txtCnc3.setBackground(new java.awt.Color(255, 255, 255));
        txtCnc3.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        txtCnc3.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(204, 204, 204)));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipady = 10;
        gridBagConstraints.insets = new java.awt.Insets(0, 4, 0, 4);
        jPanel2.add(txtCnc3, gridBagConstraints);

        txtCnc4.setBackground(new java.awt.Color(255, 255, 255));
        txtCnc4.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        txtCnc4.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(204, 204, 204)));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipady = 10;
        gridBagConstraints.insets = new java.awt.Insets(0, 4, 0, 4);
        jPanel2.add(txtCnc4, gridBagConstraints);

        txtCnc5.setBackground(new java.awt.Color(255, 255, 255));
        txtCnc5.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        txtCnc5.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(204, 204, 204)));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipady = 10;
        gridBagConstraints.insets = new java.awt.Insets(0, 4, 0, 4);
        jPanel2.add(txtCnc5, gridBagConstraints);

        txtFresa1.setBackground(new java.awt.Color(255, 255, 255));
        txtFresa1.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        txtFresa1.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(204, 204, 204)));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipady = 10;
        gridBagConstraints.insets = new java.awt.Insets(0, 4, 0, 4);
        jPanel2.add(txtFresa1, gridBagConstraints);

        txtFresa2.setBackground(new java.awt.Color(255, 255, 255));
        txtFresa2.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        txtFresa2.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(204, 204, 204)));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipady = 10;
        gridBagConstraints.insets = new java.awt.Insets(0, 4, 0, 4);
        jPanel2.add(txtFresa2, gridBagConstraints);

        txtFresa3.setBackground(new java.awt.Color(255, 255, 255));
        txtFresa3.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        txtFresa3.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(204, 204, 204)));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipady = 10;
        gridBagConstraints.insets = new java.awt.Insets(0, 4, 0, 4);
        jPanel2.add(txtFresa3, gridBagConstraints);

        txtFresa4.setBackground(new java.awt.Color(255, 255, 255));
        txtFresa4.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        txtFresa4.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(204, 204, 204)));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipady = 10;
        gridBagConstraints.insets = new java.awt.Insets(0, 4, 0, 4);
        jPanel2.add(txtFresa4, gridBagConstraints);

        txtTorno.setBackground(new java.awt.Color(255, 255, 255));
        txtTorno.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        txtTorno.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(204, 204, 204)));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipady = 10;
        gridBagConstraints.insets = new java.awt.Insets(0, 4, 0, 4);
        jPanel2.add(txtTorno, gridBagConstraints);

        txtRecti.setBackground(new java.awt.Color(255, 255, 255));
        txtRecti.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        txtRecti.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(204, 204, 204)));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipady = 10;
        gridBagConstraints.insets = new java.awt.Insets(0, 4, 0, 4);
        jPanel2.add(txtRecti, gridBagConstraints);

        lblTR1.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        lblTR1.setForeground(new java.awt.Color(51, 51, 51));
        lblTR1.setText("86");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        jPanel2.add(lblTR1, gridBagConstraints);

        lblDispo1.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        lblDispo1.setForeground(new java.awt.Color(51, 51, 51));
        lblDispo1.setText("100%");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        jPanel2.add(lblDispo1, gridBagConstraints);

        lblMTTR1.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        lblMTTR1.setForeground(new java.awt.Color(51, 51, 51));
        lblMTTR1.setText("0");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        jPanel2.add(lblMTTR1, gridBagConstraints);

        lblMTBF1.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        lblMTBF1.setForeground(new java.awt.Color(51, 51, 51));
        lblMTBF1.setText("0");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        jPanel2.add(lblMTBF1, gridBagConstraints);

        txtCnc11.setBackground(new java.awt.Color(255, 255, 255));
        txtCnc11.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        txtCnc11.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(204, 204, 204)));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipady = 10;
        gridBagConstraints.insets = new java.awt.Insets(0, 4, 0, 4);
        jPanel2.add(txtCnc11, gridBagConstraints);

        txtCnc21.setBackground(new java.awt.Color(255, 255, 255));
        txtCnc21.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        txtCnc21.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(204, 204, 204)));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipady = 10;
        gridBagConstraints.insets = new java.awt.Insets(0, 4, 0, 4);
        jPanel2.add(txtCnc21, gridBagConstraints);

        txtCnc31.setBackground(new java.awt.Color(255, 255, 255));
        txtCnc31.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        txtCnc31.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(204, 204, 204)));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipady = 10;
        gridBagConstraints.insets = new java.awt.Insets(0, 4, 0, 4);
        jPanel2.add(txtCnc31, gridBagConstraints);

        txtCnc41.setBackground(new java.awt.Color(255, 255, 255));
        txtCnc41.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        txtCnc41.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(204, 204, 204)));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipady = 10;
        gridBagConstraints.insets = new java.awt.Insets(0, 4, 0, 4);
        jPanel2.add(txtCnc41, gridBagConstraints);

        txtCnc51.setBackground(new java.awt.Color(255, 255, 255));
        txtCnc51.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        txtCnc51.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(204, 204, 204)));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipady = 10;
        gridBagConstraints.insets = new java.awt.Insets(0, 4, 0, 4);
        jPanel2.add(txtCnc51, gridBagConstraints);

        txtFresa11.setBackground(new java.awt.Color(255, 255, 255));
        txtFresa11.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        txtFresa11.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(204, 204, 204)));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipady = 10;
        gridBagConstraints.insets = new java.awt.Insets(0, 4, 0, 4);
        jPanel2.add(txtFresa11, gridBagConstraints);

        txtFresa21.setBackground(new java.awt.Color(255, 255, 255));
        txtFresa21.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        txtFresa21.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(204, 204, 204)));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipady = 10;
        gridBagConstraints.insets = new java.awt.Insets(0, 4, 0, 4);
        jPanel2.add(txtFresa21, gridBagConstraints);

        txtFresa31.setBackground(new java.awt.Color(255, 255, 255));
        txtFresa31.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        txtFresa31.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(204, 204, 204)));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipady = 10;
        gridBagConstraints.insets = new java.awt.Insets(0, 4, 0, 4);
        jPanel2.add(txtFresa31, gridBagConstraints);

        txtFresa41.setBackground(new java.awt.Color(255, 255, 255));
        txtFresa41.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        txtFresa41.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(204, 204, 204)));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipady = 10;
        gridBagConstraints.insets = new java.awt.Insets(0, 4, 0, 4);
        jPanel2.add(txtFresa41, gridBagConstraints);

        txtTorno1.setBackground(new java.awt.Color(255, 255, 255));
        txtTorno1.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        txtTorno1.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(204, 204, 204)));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipady = 10;
        gridBagConstraints.insets = new java.awt.Insets(0, 4, 0, 4);
        jPanel2.add(txtTorno1, gridBagConstraints);

        txtRecti1.setBackground(new java.awt.Color(255, 255, 255));
        txtRecti1.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        txtRecti1.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(204, 204, 204)));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipady = 10;
        gridBagConstraints.insets = new java.awt.Insets(0, 4, 0, 4);
        jPanel2.add(txtRecti1, gridBagConstraints);

        lblTR2.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        lblTR2.setForeground(new java.awt.Color(51, 51, 51));
        lblTR2.setText("86");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        jPanel2.add(lblTR2, gridBagConstraints);

        lblTR3.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        lblTR3.setForeground(new java.awt.Color(51, 51, 51));
        lblTR3.setText("86");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        jPanel2.add(lblTR3, gridBagConstraints);

        lblTR4.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        lblTR4.setForeground(new java.awt.Color(51, 51, 51));
        lblTR4.setText("86");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        jPanel2.add(lblTR4, gridBagConstraints);

        lblTR5.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        lblTR5.setForeground(new java.awt.Color(51, 51, 51));
        lblTR5.setText("86");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        jPanel2.add(lblTR5, gridBagConstraints);

        lblTR6.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        lblTR6.setForeground(new java.awt.Color(51, 51, 51));
        lblTR6.setText("86");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        jPanel2.add(lblTR6, gridBagConstraints);

        lblTR7.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        lblTR7.setForeground(new java.awt.Color(51, 51, 51));
        lblTR7.setText("86");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        jPanel2.add(lblTR7, gridBagConstraints);

        lblTR8.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        lblTR8.setForeground(new java.awt.Color(51, 51, 51));
        lblTR8.setText("86");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        jPanel2.add(lblTR8, gridBagConstraints);

        lblTR9.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        lblTR9.setForeground(new java.awt.Color(51, 51, 51));
        lblTR9.setText("86");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        jPanel2.add(lblTR9, gridBagConstraints);

        lblTR10.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        lblTR10.setForeground(new java.awt.Color(51, 51, 51));
        lblTR10.setText("86");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        jPanel2.add(lblTR10, gridBagConstraints);

        lblTR11.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        lblTR11.setForeground(new java.awt.Color(51, 51, 51));
        lblTR11.setText("86");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        jPanel2.add(lblTR11, gridBagConstraints);

        lblDispo2.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        lblDispo2.setForeground(new java.awt.Color(51, 51, 51));
        lblDispo2.setText("100%");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        jPanel2.add(lblDispo2, gridBagConstraints);

        lblDispo3.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        lblDispo3.setForeground(new java.awt.Color(51, 51, 51));
        lblDispo3.setText("100%");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        jPanel2.add(lblDispo3, gridBagConstraints);

        lblDispo4.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        lblDispo4.setForeground(new java.awt.Color(51, 51, 51));
        lblDispo4.setText("100%");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        jPanel2.add(lblDispo4, gridBagConstraints);

        lblDispo5.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        lblDispo5.setForeground(new java.awt.Color(51, 51, 51));
        lblDispo5.setText("100%");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        jPanel2.add(lblDispo5, gridBagConstraints);

        lblDispo6.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        lblDispo6.setForeground(new java.awt.Color(51, 51, 51));
        lblDispo6.setText("100%");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        jPanel2.add(lblDispo6, gridBagConstraints);

        lblDispo7.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        lblDispo7.setForeground(new java.awt.Color(51, 51, 51));
        lblDispo7.setText("100%");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        jPanel2.add(lblDispo7, gridBagConstraints);

        lblDispo8.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        lblDispo8.setForeground(new java.awt.Color(51, 51, 51));
        lblDispo8.setText("100%");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        jPanel2.add(lblDispo8, gridBagConstraints);

        lblDispo9.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        lblDispo9.setForeground(new java.awt.Color(51, 51, 51));
        lblDispo9.setText("100%");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        jPanel2.add(lblDispo9, gridBagConstraints);

        lblDispo10.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        lblDispo10.setForeground(new java.awt.Color(51, 51, 51));
        lblDispo10.setText("100%");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        jPanel2.add(lblDispo10, gridBagConstraints);

        lblDispo11.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        lblDispo11.setForeground(new java.awt.Color(51, 51, 51));
        lblDispo11.setText("100%");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        jPanel2.add(lblDispo11, gridBagConstraints);

        lblMTTR2.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        lblMTTR2.setForeground(new java.awt.Color(51, 51, 51));
        lblMTTR2.setText("0");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        jPanel2.add(lblMTTR2, gridBagConstraints);

        lblMTTR3.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        lblMTTR3.setForeground(new java.awt.Color(51, 51, 51));
        lblMTTR3.setText("0");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        jPanel2.add(lblMTTR3, gridBagConstraints);

        lblMTTR4.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        lblMTTR4.setForeground(new java.awt.Color(51, 51, 51));
        lblMTTR4.setText("0");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        jPanel2.add(lblMTTR4, gridBagConstraints);

        lblMTTR5.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        lblMTTR5.setForeground(new java.awt.Color(51, 51, 51));
        lblMTTR5.setText("0");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        jPanel2.add(lblMTTR5, gridBagConstraints);

        lblMTTR6.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        lblMTTR6.setForeground(new java.awt.Color(51, 51, 51));
        lblMTTR6.setText("0");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        jPanel2.add(lblMTTR6, gridBagConstraints);

        lblMTTR7.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        lblMTTR7.setForeground(new java.awt.Color(51, 51, 51));
        lblMTTR7.setText("0");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        jPanel2.add(lblMTTR7, gridBagConstraints);

        lblMTTR8.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        lblMTTR8.setForeground(new java.awt.Color(51, 51, 51));
        lblMTTR8.setText("0");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        jPanel2.add(lblMTTR8, gridBagConstraints);

        lblMTTR9.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        lblMTTR9.setForeground(new java.awt.Color(51, 51, 51));
        lblMTTR9.setText("0");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        jPanel2.add(lblMTTR9, gridBagConstraints);

        lblMTTR10.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        lblMTTR10.setForeground(new java.awt.Color(51, 51, 51));
        lblMTTR10.setText("0");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        jPanel2.add(lblMTTR10, gridBagConstraints);

        lblMTTR11.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        lblMTTR11.setForeground(new java.awt.Color(51, 51, 51));
        lblMTTR11.setText("0");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        jPanel2.add(lblMTTR11, gridBagConstraints);

        lblMTBF2.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        lblMTBF2.setForeground(new java.awt.Color(51, 51, 51));
        lblMTBF2.setText("0");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        jPanel2.add(lblMTBF2, gridBagConstraints);

        lblMTBF3.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        lblMTBF3.setForeground(new java.awt.Color(51, 51, 51));
        lblMTBF3.setText("0");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        jPanel2.add(lblMTBF3, gridBagConstraints);

        lblMTBF4.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        lblMTBF4.setForeground(new java.awt.Color(51, 51, 51));
        lblMTBF4.setText("0");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        jPanel2.add(lblMTBF4, gridBagConstraints);

        lblMTBF5.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        lblMTBF5.setForeground(new java.awt.Color(51, 51, 51));
        lblMTBF5.setText("0");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        jPanel2.add(lblMTBF5, gridBagConstraints);

        lblMTBF6.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        lblMTBF6.setForeground(new java.awt.Color(51, 51, 51));
        lblMTBF6.setText("0");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        jPanel2.add(lblMTBF6, gridBagConstraints);

        lblMTBF7.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        lblMTBF7.setForeground(new java.awt.Color(51, 51, 51));
        lblMTBF7.setText("0");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        jPanel2.add(lblMTBF7, gridBagConstraints);

        lblMTBF8.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        lblMTBF8.setForeground(new java.awt.Color(51, 51, 51));
        lblMTBF8.setText("0");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        jPanel2.add(lblMTBF8, gridBagConstraints);

        lblMTBF9.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        lblMTBF9.setForeground(new java.awt.Color(51, 51, 51));
        lblMTBF9.setText("0");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        jPanel2.add(lblMTBF9, gridBagConstraints);

        lblMTBF10.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        lblMTBF10.setForeground(new java.awt.Color(51, 51, 51));
        lblMTBF10.setText("0");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        jPanel2.add(lblMTBF10, gridBagConstraints);

        lblMTBF11.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        lblMTBF11.setForeground(new java.awt.Color(51, 51, 51));
        lblMTBF11.setText("0");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        jPanel2.add(lblMTBF11, gridBagConstraints);

        jPanel4.add(jPanel2, java.awt.BorderLayout.CENTER);

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        lblDia.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        lblDia.setText("Dias");
        jPanel5.add(lblDia);
        jPanel5.add(anio);

        lblDia1.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        lblDia1.setText("Semana");
        jPanel5.add(lblDia1);
        jPanel5.add(semana);

        jPanel4.add(jPanel5, java.awt.BorderLayout.PAGE_START);

        jPanel1.add(jPanel4, java.awt.BorderLayout.CENTER);

        add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void closeDialog(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_closeDialog
        setVisible(false);
        dispose();
    }//GEN-LAST:event_closeDialog

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        try{
            Connection con;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            String sql = "insert into reporte_maquinados (Anio, Semana, Cnc1, Cnc2, Cnc3, Cnc4, Cnc5, Fresa1, Fresa2, Fresa3, Fresa4, Torno, Recti) "
                    + "values (?,?,?,?,?,?,?,?,?,?,?,?,?) "
                    + "on duplicate key update "
                    + "Cnc1 = values(Cnc1), "
                    + "Cnc2 = values(Cnc2), "
                    + "Cnc3 = values(Cnc3), "
                    + "Cnc4 = values(Cnc4), "
                    + "Cnc5 = values(Cnc5), "
                    + "Fresa1 = values(Fresa1), "
                    + "Fresa2 = values(Fresa2), "
                    + "Fresa3 = values(Fresa3), "
                    + "Fresa4 = values(Fresa4), "
                    + "Torno = values(Torno), "
                    + "Recti = values(Recti)";
            PreparedStatement pst = con.prepareStatement(sql);
            
            pst.setString(1, anio.getValue() + "");
            pst.setString(2, semana.getYear()+ "");
            pst.setString(3, formatoJSON(TP1, txtCnc1, txtCnc11));
            pst.setString(4, formatoJSON(TP2, txtCnc2, txtCnc21));
            pst.setString(5, formatoJSON(TP3, txtCnc3, txtCnc31));
            pst.setString(6, formatoJSON(TP4, txtCnc4, txtCnc41));
            pst.setString(7, formatoJSON(TP5, txtCnc5, txtCnc51));
            pst.setString(8, formatoJSON(TP6, txtFresa1, txtFresa11));
            pst.setString(9, formatoJSON(TP7, txtFresa2, txtFresa21));
            pst.setString(10, formatoJSON(TP8, txtFresa3, txtFresa31));
            pst.setString(11, formatoJSON(TP9, txtFresa4, txtFresa41));
            pst.setString(12, formatoJSON(TP10, txtTorno, txtTorno));
            pst.setString(13, formatoJSON(TP11, txtRecti, txtRecti));
            
            int n = pst.executeUpdate();
            
            if(n > 0){
                JOptionPane.showMessageDialog(this, "Datos Guardados");
            }
            
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "Error: " + e, "Error",JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                ReporteMaquinados dialog = new ReporteMaquinados(new java.awt.Frame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel TP1;
    private javax.swing.JLabel TP10;
    private javax.swing.JLabel TP11;
    private javax.swing.JLabel TP2;
    private javax.swing.JLabel TP3;
    private javax.swing.JLabel TP4;
    private javax.swing.JLabel TP5;
    private javax.swing.JLabel TP6;
    private javax.swing.JLabel TP7;
    private javax.swing.JLabel TP8;
    private javax.swing.JLabel TP9;
    private com.toedter.components.JSpinField anio;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JLabel lblDia;
    private javax.swing.JLabel lblDia1;
    private javax.swing.JLabel lblDispo1;
    private javax.swing.JLabel lblDispo10;
    private javax.swing.JLabel lblDispo11;
    private javax.swing.JLabel lblDispo2;
    private javax.swing.JLabel lblDispo3;
    private javax.swing.JLabel lblDispo4;
    private javax.swing.JLabel lblDispo5;
    private javax.swing.JLabel lblDispo6;
    private javax.swing.JLabel lblDispo7;
    private javax.swing.JLabel lblDispo8;
    private javax.swing.JLabel lblDispo9;
    private javax.swing.JLabel lblMTBF1;
    private javax.swing.JLabel lblMTBF10;
    private javax.swing.JLabel lblMTBF11;
    private javax.swing.JLabel lblMTBF2;
    private javax.swing.JLabel lblMTBF3;
    private javax.swing.JLabel lblMTBF4;
    private javax.swing.JLabel lblMTBF5;
    private javax.swing.JLabel lblMTBF6;
    private javax.swing.JLabel lblMTBF7;
    private javax.swing.JLabel lblMTBF8;
    private javax.swing.JLabel lblMTBF9;
    private javax.swing.JLabel lblMTTR1;
    private javax.swing.JLabel lblMTTR10;
    private javax.swing.JLabel lblMTTR11;
    private javax.swing.JLabel lblMTTR2;
    private javax.swing.JLabel lblMTTR3;
    private javax.swing.JLabel lblMTTR4;
    private javax.swing.JLabel lblMTTR5;
    private javax.swing.JLabel lblMTTR6;
    private javax.swing.JLabel lblMTTR7;
    private javax.swing.JLabel lblMTTR8;
    private javax.swing.JLabel lblMTTR9;
    private javax.swing.JLabel lblTR1;
    private javax.swing.JLabel lblTR10;
    private javax.swing.JLabel lblTR11;
    private javax.swing.JLabel lblTR2;
    private javax.swing.JLabel lblTR3;
    private javax.swing.JLabel lblTR4;
    private javax.swing.JLabel lblTR5;
    private javax.swing.JLabel lblTR6;
    private javax.swing.JLabel lblTR7;
    private javax.swing.JLabel lblTR8;
    private javax.swing.JLabel lblTR9;
    private scrollPane.PanelRound panelReporte;
    private com.toedter.calendar.JYearChooser semana;
    private javax.swing.JTextField txtCnc1;
    private javax.swing.JTextField txtCnc11;
    private javax.swing.JTextField txtCnc2;
    private javax.swing.JTextField txtCnc21;
    private javax.swing.JTextField txtCnc3;
    private javax.swing.JTextField txtCnc31;
    private javax.swing.JTextField txtCnc4;
    private javax.swing.JTextField txtCnc41;
    private javax.swing.JTextField txtCnc5;
    private javax.swing.JTextField txtCnc51;
    private javax.swing.JTextField txtFresa1;
    private javax.swing.JTextField txtFresa11;
    private javax.swing.JTextField txtFresa2;
    private javax.swing.JTextField txtFresa21;
    private javax.swing.JTextField txtFresa3;
    private javax.swing.JTextField txtFresa31;
    private javax.swing.JTextField txtFresa4;
    private javax.swing.JTextField txtFresa41;
    private javax.swing.JTextField txtRecti;
    private javax.swing.JTextField txtRecti1;
    private javax.swing.JTextField txtTorno;
    private javax.swing.JTextField txtTorno1;
    // End of variables declaration//GEN-END:variables
}
