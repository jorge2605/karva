package VentanaEmergente.ProyectoManager;

import Conexiones.Conexion;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class addPrioridadCompras extends javax.swing.JDialog implements ActionListener{

    Proyectos a;
    int x, y;
    int btn;
    String numEmpleado;
    
    public void sinSelec(String priori){
        try{
            Connection con;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            String sql = "update prioridadcompras set Estado = ? where Prioridad = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            
            pst.setString(1, "CERRADO");
            pst.setString(2, priori);
            
            pst.executeUpdate();
            
            verBd();
            
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "ERROR: "+e,"ERROR",JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void insertar(String proyecto, String prioridad){
        try{
            Connection con;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement proy = con.createStatement();
            String sqlP = "select Proyecto from proyectos where Proyecto like '"+proyecto+"'";
            ResultSet rsP = proy.executeQuery(sqlP);
            String pr = null;
            while(rsP.next()){
                pr = rsP.getString("Proyecto");
            }
            
            if(pr == null){
                JOptionPane.showMessageDialog(this, "DEBES SELECCIONAR UN PROYECTO VALIDO","ADVERTENCIA",JOptionPane.WARNING_MESSAGE);
            }else{
            Statement st3 = con.createStatement();
            String sql3 = "select * from prioridadcompras where Proyecto like '"+proyecto+"' and Estado like 'ACTIVO'";
            ResultSet rs3 = st3.executeQuery(sql3);
            String pro = null;
            while(rs3.next()){
                pro = rs3.getString("Proyecto");
            }
            
            if(pro != null){
                JOptionPane.showMessageDialog(this, "ESTE PROYECTO YA ESTA SELECCIONADO","ADVERTENCIA",JOptionPane.WARNING_MESSAGE);
            }else{
            
            Statement st = con.createStatement();
            String sql = "select * from prioridadcompras where Prioridad like '"+btn+"' and Estado like 'ACTIVO'";
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
                String sql2 = "update prioridadcompras set Estado = ? where Id = ?";
                PreparedStatement pst = con.prepareStatement(sql2);
                
                pst.setString(1, "CERRADO");
                pst.setString(2, rs.getString("Id"));
                
                pst.executeUpdate();
            }
            
            String sql2 = "insert into prioridadcompras(Proyecto, Prioridad, Empleado, FechaRegistro, Estado) values(?,?,?,?,?)";
            PreparedStatement pst = con.prepareStatement(sql2);
            
            Date d = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:ss");
            String fecha = sdf.format(d);
            
            pst.setString(1, proyecto);
            pst.setString(2, prioridad);
            pst.setString(3, numEmpleado);
            pst.setString(4, fecha);
            pst.setString(5, "ACTIVO");
            
            pst.executeUpdate();
            
            verBd();
            }
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "ERROR: "+e,"ERROR",JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public String format(String proyecto, String priori){
        String text;
        text = "<html>" +
        "<div style='padding: 2px 8px; text-align:center;'>" +
        "<p style='font-size:18px; padding:5px; font-weight: 900;'>PROYECTO:</p>" +
        "<p style='font-size:18px; padding:5px; font-weight: 900;'>"+proyecto+"</p>" +
        "<p style='font-size:16px; padding:5px; font-weight: 100;'>"+priori+"</p>" +
        "</div>" +
        "</html>";
        return text;
    }
    
    public void verBd(){
        try{
            Connection con;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            for (int i = 0; i < 10; i++) {
                Statement st = con.createStatement();
                String sql = "select * from prioridadcompras where Prioridad like '"+(i+1)+"' and Estado like 'ACTIVO'";
                ResultSet rs = st.executeQuery(sql);
                String pri = null;
                while(rs.next()){
                    pri = rs.getString("Proyecto");
                }
                String text;
                if(pri == null){
                    text =  "<html>" +
                            "<div style='padding: 2px 8px; text-align:center;'>" +
                            "<p style='font-size:18px; padding:5px; font-weight: 900;'>PROYECTO:</p>" +
                            "<p style='font-size:18px; padding:5px; font-weight: 900;'>SIN PROYECTO</p>" +
                            "<p style='font-size:16px; padding:5px; font-weight: 100;'>"+(i+1)+"</p>" +
                            "</div>" +
                            "</html>";
                }else{
                    text = format(pri,String.valueOf((i+1)));
                }
                switch(i){
                    case 0:
                        btn1.setText(text);
                        break;
                    case 1:
                        btn2.setText(text);
                        break;
                    case 2:
                        btn3.setText(text);
                        break;
                    case 3:
                        btn4.setText(text);
                        break;
                    case 4:
                        btn5.setText(text);
                        break;
                    case 5:
                        btn6.setText(text);
                        break;
                    case 6:
                        btn7.setText(text);
                        break;
                    case 7:
                        btn8.setText(text);
                        break;
                    case 8:
                        btn9.setText(text);
                        break;
                    case 9:
                        btn10.setText(text);
                        break;
                }
            }
            
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "ERROR: "+e,"ERROR",JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public addPrioridadCompras(java.awt.Frame parent, boolean modal,String numEmpleado) {
        super(parent, modal);
        initComponents();
        this.numEmpleado = numEmpleado;
        btn2.setBackground(new Color(0,0,0,0));
        this.setBackground(new Color(0,0,0,0));
        btn1.setBackground(new Color(0,0,0,0));
//        jPanel1.setBackground(new Color(0,0,0,0));
        jPanel2.setBackground(new Color(0,0,0,0));
        jPanel3.setBackground(new Color(0,0,0,0));
        jPanel4.setBackground(new Color(0,0,0,0));
        jPanel5.setBackground(new Color(0,0,0,0));
        jPanel6.setBackground(new Color(0,0,0,0));
        verBd();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelRound2 = new scrollPane.PanelRound();
        jPanel4 = new javax.swing.JPanel();
        panelRound3 = new scrollPane.PanelRound();
        panelSalir = new scrollPane.PanelRound();
        lblSalir = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
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
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setPreferredSize(new java.awt.Dimension(1300, 700));
        addWindowFocusListener(new java.awt.event.WindowFocusListener() {
            public void windowGainedFocus(java.awt.event.WindowEvent evt) {
            }
            public void windowLostFocus(java.awt.event.WindowEvent evt) {
                formWindowLostFocus(evt);
            }
        });

        panelRound2.setBackground(new java.awt.Color(255, 255, 255));
        panelRound2.setRoundBottomLeft(50);
        panelRound2.setRoundBottomRight(50);
        panelRound2.setRoundTopLeft(50);
        panelRound2.setRoundTopRight(50);
        panelRound2.setLayout(new java.awt.BorderLayout());

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setLayout(new java.awt.BorderLayout());

        panelRound3.setBackground(new java.awt.Color(255, 255, 255));
        panelRound3.setRoundTopRight(60);

        panelSalir.setBackground(new java.awt.Color(255, 255, 255));
        panelSalir.setRoundTopRight(30);

        lblSalir.setFont(new java.awt.Font("Roboto", 1, 20)); // NOI18N
        lblSalir.setForeground(new java.awt.Color(0, 0, 0));
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

        panelRound3.add(panelSalir);

        jPanel4.add(panelRound3, java.awt.BorderLayout.EAST);

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));

        jLabel12.setFont(new java.awt.Font("Roboto", 1, 40)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(204, 51, 0));
        jLabel12.setText("       PRIORIDAD EN COMPRAS       ");
        jPanel6.add(jLabel12);

        jPanel5.add(jPanel6);

        jPanel4.add(jPanel5, java.awt.BorderLayout.CENTER);

        panelRound2.add(jPanel4, java.awt.BorderLayout.NORTH);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setLayout(new java.awt.GridLayout(2, 7, 10, 10));

        panel1.setBackground(new java.awt.Color(255, 102, 0));
        panel1.setForeground(new java.awt.Color(51, 51, 51));
        panel1.setRoundBottomLeft(40);
        panel1.setRoundBottomRight(40);
        panel1.setRoundTopLeft(40);
        panel1.setRoundTopRight(40);
        panel1.setLayout(new java.awt.BorderLayout());

        btn1.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        btn1.setForeground(new java.awt.Color(255, 255, 255));
        btn1.setText("<html>\n<div style='padding: 2px 8px; text-align:center;'>\n<p style='font-size:18px; padding:5px; font-weight: 900;'>PROYECTO:</p>\n<p style='font-size:18px; padding:5px; font-weight: 900;'>SIN PROYECTO</p>\n<p style='font-size:16px; padding:5px; font-weight: 100;'>1</p>\n</div>\n</html>");
        btn1.setBorder(null);
        btn1.setBorderPainted(false);
        btn1.setContentAreaFilled(false);
        btn1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn1.setFocusPainted(false);
        btn1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn1MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn1MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn1MouseExited(evt);
            }
        });
        btn1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn1ActionPerformed(evt);
            }
        });
        panel1.add(btn1, java.awt.BorderLayout.CENTER);

        jPanel3.add(panel1);

        panel2.setBackground(new java.awt.Color(255, 102, 0));
        panel2.setRoundBottomLeft(40);
        panel2.setRoundBottomRight(40);
        panel2.setRoundTopLeft(40);
        panel2.setRoundTopRight(40);
        panel2.setLayout(new java.awt.BorderLayout());

        btn2.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        btn2.setForeground(new java.awt.Color(255, 255, 255));
        btn2.setText("<html> <div style='padding: 2px 8px; text-align:center;'> <p style='font-size:18px; padding:5px; font-weight: 900;'>PROYECTO:</p> <p style='font-size:18px; padding:5px; font-weight: 900;'>SIN PROYECTO</p> <p style='font-size:16px; padding:5px; font-weight: 100;'>1</p> </div> </html>");
        btn2.setBorder(null);
        btn2.setBorderPainted(false);
        btn2.setContentAreaFilled(false);
        btn2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn2.setFocusPainted(false);
        btn2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn2MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn2MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn2MouseExited(evt);
            }
        });
        panel2.add(btn2, java.awt.BorderLayout.CENTER);

        jPanel3.add(panel2);

        panel3.setBackground(new java.awt.Color(255, 102, 0));
        panel3.setForeground(new java.awt.Color(51, 51, 51));
        panel3.setRoundBottomLeft(40);
        panel3.setRoundBottomRight(40);
        panel3.setRoundTopLeft(40);
        panel3.setRoundTopRight(40);
        panel3.setLayout(new java.awt.BorderLayout());

        btn3.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        btn3.setForeground(new java.awt.Color(255, 255, 255));
        btn3.setText("<html> <div style='padding: 2px 8px; text-align:center;'> <p style='font-size:18px; padding:5px; font-weight: 900;'>PROYECTO:</p> <p style='font-size:18px; padding:5px; font-weight: 900;'>SIN PROYECTO</p> <p style='font-size:16px; padding:5px; font-weight: 100;'>1</p> </div> </html>");
        btn3.setBorder(null);
        btn3.setBorderPainted(false);
        btn3.setContentAreaFilled(false);
        btn3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn3.setFocusPainted(false);
        btn3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn3MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn3MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn3MouseExited(evt);
            }
        });
        panel3.add(btn3, java.awt.BorderLayout.CENTER);

        jPanel3.add(panel3);

        panel4.setBackground(new java.awt.Color(255, 102, 0));
        panel4.setForeground(new java.awt.Color(51, 51, 51));
        panel4.setRoundBottomLeft(40);
        panel4.setRoundBottomRight(40);
        panel4.setRoundTopLeft(40);
        panel4.setRoundTopRight(40);
        panel4.setLayout(new java.awt.BorderLayout());

        btn4.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        btn4.setForeground(new java.awt.Color(255, 255, 255));
        btn4.setText("<html> <div style='padding: 2px 8px; text-align:center;'> <p style='font-size:18px; padding:5px; font-weight: 900;'>PROYECTO:</p> <p style='font-size:18px; padding:5px; font-weight: 900;'>SIN PROYECTO</p> <p style='font-size:16px; padding:5px; font-weight: 100;'>1</p> </div> </html>");
        btn4.setBorder(null);
        btn4.setBorderPainted(false);
        btn4.setContentAreaFilled(false);
        btn4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn4.setFocusPainted(false);
        btn4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn4MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn4MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn4MouseExited(evt);
            }
        });
        panel4.add(btn4, java.awt.BorderLayout.CENTER);

        jPanel3.add(panel4);

        panel5.setBackground(new java.awt.Color(255, 102, 0));
        panel5.setForeground(new java.awt.Color(51, 51, 51));
        panel5.setRoundBottomLeft(40);
        panel5.setRoundBottomRight(40);
        panel5.setRoundTopLeft(40);
        panel5.setRoundTopRight(40);
        panel5.setLayout(new java.awt.BorderLayout());

        btn5.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        btn5.setForeground(new java.awt.Color(255, 255, 255));
        btn5.setText("<html> <div style='padding: 2px 8px; text-align:center;'> <p style='font-size:18px; padding:5px; font-weight: 900;'>PROYECTO:</p> <p style='font-size:18px; padding:5px; font-weight: 900;'>SIN PROYECTO</p> <p style='font-size:16px; padding:5px; font-weight: 100;'>1</p> </div> </html>");
        btn5.setBorder(null);
        btn5.setBorderPainted(false);
        btn5.setContentAreaFilled(false);
        btn5.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn5.setFocusPainted(false);
        btn5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn5MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn5MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn5MouseExited(evt);
            }
        });
        panel5.add(btn5, java.awt.BorderLayout.CENTER);

        jPanel3.add(panel5);

        panel6.setBackground(new java.awt.Color(255, 102, 0));
        panel6.setForeground(new java.awt.Color(51, 51, 51));
        panel6.setRoundBottomLeft(40);
        panel6.setRoundBottomRight(40);
        panel6.setRoundTopLeft(40);
        panel6.setRoundTopRight(40);
        panel6.setLayout(new java.awt.BorderLayout());

        btn6.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        btn6.setForeground(new java.awt.Color(255, 255, 255));
        btn6.setText("<html> <div style='padding: 2px 8px; text-align:center;'> <p style='font-size:18px; padding:5px; font-weight: 900;'>PROYECTO:</p> <p style='font-size:18px; padding:5px; font-weight: 900;'>SIN PROYECTO</p> <p style='font-size:16px; padding:5px; font-weight: 100;'>1</p> </div> </html>");
        btn6.setBorder(null);
        btn6.setBorderPainted(false);
        btn6.setContentAreaFilled(false);
        btn6.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn6.setFocusPainted(false);
        btn6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn6MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn6MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn6MouseExited(evt);
            }
        });
        panel6.add(btn6, java.awt.BorderLayout.CENTER);

        jPanel3.add(panel6);

        panel7.setBackground(new java.awt.Color(255, 102, 0));
        panel7.setForeground(new java.awt.Color(51, 51, 51));
        panel7.setRoundBottomLeft(40);
        panel7.setRoundBottomRight(40);
        panel7.setRoundTopLeft(40);
        panel7.setRoundTopRight(40);
        panel7.setLayout(new java.awt.BorderLayout());

        btn7.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        btn7.setForeground(new java.awt.Color(255, 255, 255));
        btn7.setText("<html> <div style='padding: 2px 8px; text-align:center;'> <p style='font-size:18px; padding:5px; font-weight: 900;'>PROYECTO:</p> <p style='font-size:18px; padding:5px; font-weight: 900;'>SIN PROYECTO</p> <p style='font-size:16px; padding:5px; font-weight: 100;'>1</p> </div> </html>");
        btn7.setBorder(null);
        btn7.setBorderPainted(false);
        btn7.setContentAreaFilled(false);
        btn7.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn7.setFocusPainted(false);
        btn7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn7MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn7MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn7MouseExited(evt);
            }
        });
        panel7.add(btn7, java.awt.BorderLayout.CENTER);

        jPanel3.add(panel7);

        panel8.setBackground(new java.awt.Color(255, 102, 0));
        panel8.setForeground(new java.awt.Color(51, 51, 51));
        panel8.setRoundBottomLeft(40);
        panel8.setRoundBottomRight(40);
        panel8.setRoundTopLeft(40);
        panel8.setRoundTopRight(40);
        panel8.setLayout(new java.awt.BorderLayout());

        btn8.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        btn8.setForeground(new java.awt.Color(255, 255, 255));
        btn8.setText("<html> <div style='padding: 2px 8px; text-align:center;'> <p style='font-size:18px; padding:5px; font-weight: 900;'>PROYECTO:</p> <p style='font-size:18px; padding:5px; font-weight: 900;'>SIN PROYECTO</p> <p style='font-size:16px; padding:5px; font-weight: 100;'>1</p> </div> </html>");
        btn8.setBorder(null);
        btn8.setBorderPainted(false);
        btn8.setContentAreaFilled(false);
        btn8.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn8.setFocusPainted(false);
        btn8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn8MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn8MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn8MouseExited(evt);
            }
        });
        panel8.add(btn8, java.awt.BorderLayout.CENTER);

        jPanel3.add(panel8);

        panel9.setBackground(new java.awt.Color(255, 102, 0));
        panel9.setForeground(new java.awt.Color(51, 51, 51));
        panel9.setRoundBottomLeft(40);
        panel9.setRoundBottomRight(40);
        panel9.setRoundTopLeft(40);
        panel9.setRoundTopRight(40);
        panel9.setLayout(new java.awt.BorderLayout());

        btn9.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        btn9.setForeground(new java.awt.Color(255, 255, 255));
        btn9.setText("<html> <div style='padding: 2px 8px; text-align:center;'> <p style='font-size:18px; padding:5px; font-weight: 900;'>PROYECTO:</p> <p style='font-size:18px; padding:5px; font-weight: 900;'>SIN PROYECTO</p> <p style='font-size:16px; padding:5px; font-weight: 100;'>1</p> </div> </html>");
        btn9.setBorder(null);
        btn9.setBorderPainted(false);
        btn9.setContentAreaFilled(false);
        btn9.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn9.setFocusPainted(false);
        btn9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn9MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn9MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn9MouseExited(evt);
            }
        });
        panel9.add(btn9, java.awt.BorderLayout.CENTER);

        jPanel3.add(panel9);

        panel10.setBackground(new java.awt.Color(255, 102, 0));
        panel10.setForeground(new java.awt.Color(51, 51, 51));
        panel10.setRoundBottomLeft(40);
        panel10.setRoundBottomRight(40);
        panel10.setRoundTopLeft(40);
        panel10.setRoundTopRight(40);
        panel10.setLayout(new java.awt.BorderLayout());

        btn10.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        btn10.setForeground(new java.awt.Color(255, 255, 255));
        btn10.setText("<html> <div style='padding: 2px 8px; text-align:center;'> <p style='font-size:18px; padding:5px; font-weight: 900;'>PROYECTO:</p> <p style='font-size:18px; padding:5px; font-weight: 900;'>SIN PROYECTO</p> <p style='font-size:16px; padding:5px; font-weight: 100;'>1</p> </div> </html>");
        btn10.setBorder(null);
        btn10.setBorderPainted(false);
        btn10.setContentAreaFilled(false);
        btn10.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn10.setFocusPainted(false);
        btn10.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn10MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn10MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn10MouseExited(evt);
            }
        });
        panel10.add(btn10, java.awt.BorderLayout.CENTER);

        jPanel3.add(panel10);

        panelRound2.add(jPanel3, java.awt.BorderLayout.CENTER);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        panelRound2.add(jPanel2, java.awt.BorderLayout.PAGE_END);

        jLabel1.setText("     ");
        panelRound2.add(jLabel1, java.awt.BorderLayout.LINE_END);

        jLabel2.setText("     ");
        panelRound2.add(jLabel2, java.awt.BorderLayout.LINE_START);

        getContentPane().add(panelRound2, java.awt.BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btn2MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn2MouseEntered
        panel2.setBackground(new Color(255,58,0));
    }//GEN-LAST:event_btn2MouseEntered

    private void btn2MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn2MouseExited
        panel2.setBackground(new Color(255,102,0));
    }//GEN-LAST:event_btn2MouseExited

    private void btn1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn1MouseEntered
        panel1.setBackground(new Color(255,58,0));
    }//GEN-LAST:event_btn1MouseEntered

    private void btn1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn1MouseExited
        panel1.setBackground(new Color(255,102,0));
    }//GEN-LAST:event_btn1MouseExited

    private void btn1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn1ActionPerformed
        
    }//GEN-LAST:event_btn1ActionPerformed

    private void formWindowLostFocus(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowLostFocus
//        dispose();
    if(a != null){
        if(a.isVisible()){
            
        }else{
        dispose();
        }
    }else{
        dispose();
    }
    }//GEN-LAST:event_formWindowLostFocus

    private void btn1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn1MouseClicked
        x = evt.getXOnScreen();
        y = evt.getYOnScreen();
        JFrame f = (JFrame) JOptionPane.getFrameForComponent(this);
        a = new Proyectos(f, false);
        a.setLocation(x,y);
        a.txtProyecto.addActionListener(this);
        a.btnSelec.addActionListener(this);
        a.setVisible(true);
        btn = 1;
    }//GEN-LAST:event_btn1MouseClicked

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

    private void btn3MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn3MouseEntered
        panel3.setBackground(new Color(255,58,0));
    }//GEN-LAST:event_btn3MouseEntered

    private void btn3MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn3MouseExited
        panel3.setBackground(new Color(255,102,0));
    }//GEN-LAST:event_btn3MouseExited

    private void btn4MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn4MouseEntered
        panel4.setBackground(new Color(255,58,0));
    }//GEN-LAST:event_btn4MouseEntered

    private void btn4MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn4MouseExited
        panel4.setBackground(new Color(255,102,0));
    }//GEN-LAST:event_btn4MouseExited

    private void btn5MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn5MouseEntered
        panel5.setBackground(new Color(255,58,0));
    }//GEN-LAST:event_btn5MouseEntered

    private void btn5MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn5MouseExited
        panel5.setBackground(new Color(255,102,0));
    }//GEN-LAST:event_btn5MouseExited

    private void btn6MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn6MouseEntered
        panel6.setBackground(new Color(255,58,0));
    }//GEN-LAST:event_btn6MouseEntered

    private void btn6MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn6MouseExited
        panel6.setBackground(new Color(255,102,0));
    }//GEN-LAST:event_btn6MouseExited

    private void btn7MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn7MouseEntered
        panel7.setBackground(new Color(255,58,0));
    }//GEN-LAST:event_btn7MouseEntered

    private void btn7MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn7MouseExited
        panel7.setBackground(new Color(255,102,0));
    }//GEN-LAST:event_btn7MouseExited

    private void btn8MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn8MouseEntered
        panel8.setBackground(new Color(255,58,0));
    }//GEN-LAST:event_btn8MouseEntered

    private void btn8MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn8MouseExited
        panel8.setBackground(new Color(255,102,0));
    }//GEN-LAST:event_btn8MouseExited

    private void btn9MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn9MouseEntered
        panel9.setBackground(new Color(255,58,0));
    }//GEN-LAST:event_btn9MouseEntered

    private void btn9MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn9MouseExited
        panel9.setBackground(new Color(255,102,0));
    }//GEN-LAST:event_btn9MouseExited

    private void btn10MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn10MouseEntered
        panel10.setBackground(new Color(255,58,0));
    }//GEN-LAST:event_btn10MouseEntered

    private void btn10MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn10MouseExited
        panel10.setBackground(new Color(255,102,0));
    }//GEN-LAST:event_btn10MouseExited

    private void btn2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn2MouseClicked
        x = evt.getXOnScreen();
        y = evt.getYOnScreen();
        JFrame f = (JFrame) JOptionPane.getFrameForComponent(this);
        a = new Proyectos(f, false);
        a.setLocation(x,y);
        a.txtProyecto.addActionListener(this);
        a.btnSelec.addActionListener(this);
        a.setVisible(true);
        btn = 2;
    }//GEN-LAST:event_btn2MouseClicked

    private void btn3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn3MouseClicked
        x = evt.getXOnScreen();
        y = evt.getYOnScreen();
        JFrame f = (JFrame) JOptionPane.getFrameForComponent(this);
        a = new Proyectos(f, false);
        a.setLocation(x,y);
        a.txtProyecto.addActionListener(this);
        a.btnSelec.addActionListener(this);
        a.setVisible(true);
        btn = 3;
    }//GEN-LAST:event_btn3MouseClicked

    private void btn4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn4MouseClicked
        x = evt.getXOnScreen();
        y = evt.getYOnScreen();
        JFrame f = (JFrame) JOptionPane.getFrameForComponent(this);
        a = new Proyectos(f, false);
        a.setLocation(x,y);
        a.txtProyecto.addActionListener(this);
        a.btnSelec.addActionListener(this);
        a.setVisible(true);
        btn = 4;
    }//GEN-LAST:event_btn4MouseClicked

    private void btn5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn5MouseClicked
        x = evt.getXOnScreen();
        y = evt.getYOnScreen();
        JFrame f = (JFrame) JOptionPane.getFrameForComponent(this);
        a = new Proyectos(f, false);
        a.setLocation(x,y);
        a.txtProyecto.addActionListener(this);
        a.btnSelec.addActionListener(this);
        a.setVisible(true);
        btn = 5;
    }//GEN-LAST:event_btn5MouseClicked

    private void btn6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn6MouseClicked
        x = evt.getXOnScreen();
        y = evt.getYOnScreen();
        JFrame f = (JFrame) JOptionPane.getFrameForComponent(this);
        a = new Proyectos(f, false);
        a.setLocation(x,y);
        a.txtProyecto.addActionListener(this);
        a.btnSelec.addActionListener(this);
        a.setVisible(true);
        btn = 6;
    }//GEN-LAST:event_btn6MouseClicked

    private void btn7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn7MouseClicked
        x = evt.getXOnScreen();
        y = evt.getYOnScreen();
        JFrame f = (JFrame) JOptionPane.getFrameForComponent(this);
        a = new Proyectos(f, false);
        a.setLocation(x,y);
        a.txtProyecto.addActionListener(this);
        a.btnSelec.addActionListener(this);
        a.setVisible(true);
        btn = 7;
    }//GEN-LAST:event_btn7MouseClicked

    private void btn8MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn8MouseClicked
        x = evt.getXOnScreen();
        y = evt.getYOnScreen();
        JFrame f = (JFrame) JOptionPane.getFrameForComponent(this);
        a = new Proyectos(f, false);
        a.setLocation(x,y);
        a.txtProyecto.addActionListener(this);
        a.btnSelec.addActionListener(this);
        a.setVisible(true);
        btn = 8;
    }//GEN-LAST:event_btn8MouseClicked

    private void btn9MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn9MouseClicked
        x = evt.getXOnScreen();
        y = evt.getYOnScreen();
        JFrame f = (JFrame) JOptionPane.getFrameForComponent(this);
        a = new Proyectos(f, false);
        a.setLocation(x,y);
        a.txtProyecto.addActionListener(this);
        a.btnSelec.addActionListener(this);
        a.setVisible(true);
        btn = 9;
    }//GEN-LAST:event_btn9MouseClicked

    private void btn10MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn10MouseClicked
        x = evt.getXOnScreen();
        y = evt.getYOnScreen();
        JFrame f = (JFrame) JOptionPane.getFrameForComponent(this);
        a = new Proyectos(f, false);
        a.setLocation(x,y);
        a.txtProyecto.addActionListener(this);
        a.btnSelec.addActionListener(this);
        a.setVisible(true);
        btn = 10;
    }//GEN-LAST:event_btn10MouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(addPrioridadCompras.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(addPrioridadCompras.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(addPrioridadCompras.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(addPrioridadCompras.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                addPrioridadCompras dialog = new addPrioridadCompras(new javax.swing.JFrame(), true,"");
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
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
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JLabel lblSalir;
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
    private scrollPane.PanelRound panelRound2;
    private scrollPane.PanelRound panelRound3;
    private scrollPane.PanelRound panelSalir;
    // End of variables declaration//GEN-END:variables

    @Override
    public void actionPerformed(ActionEvent e) {
        if(a != null){
            if(e.getSource() == a.txtProyecto){
                switch(btn){
                    case 1:
                        insertar(a.txtProyecto.getText(),String.valueOf(btn));
                        break;
                    case 2:
                        insertar(a.txtProyecto.getText(),String.valueOf(btn));
                        break;
                    case 3:
                        insertar(a.txtProyecto.getText(),String.valueOf(btn));
                        break;
                    case 4:
                        insertar(a.txtProyecto.getText(),String.valueOf(btn));
                        break;
                    case 5:
                        insertar(a.txtProyecto.getText(),String.valueOf(btn));
                        break;
                    case 6:
                        insertar(a.txtProyecto.getText(),String.valueOf(btn));
                        break;
                    case 7:
                        insertar(a.txtProyecto.getText(),String.valueOf(btn));
                        break;
                    case 8:
                        insertar(a.txtProyecto.getText(),String.valueOf(btn));
                        break;
                    case 9:
                        insertar(a.txtProyecto.getText(),String.valueOf(btn));
                        break;
                    case 10:
                        insertar(a.txtProyecto.getText(),String.valueOf(btn));
                        break;
                }
                a.dispose();
            }else if(e.getSource() == a.btnSelec){
                switch(btn){
                    case 1:
                        sinSelec(String.valueOf(btn));
                        break;
                    case 2:
                        sinSelec(String.valueOf(btn));
                        break;
                    case 3:
                        sinSelec(String.valueOf(btn));
                        break;
                    case 4:
                        sinSelec(String.valueOf(btn));
                        break;
                    case 5:
                        sinSelec(String.valueOf(btn));
                        break;
                    case 6:
                        sinSelec(String.valueOf(btn));
                        break;
                    case 7:
                        sinSelec(String.valueOf(btn));
                        break;
                    case 8:
                        sinSelec(String.valueOf(btn));
                        break;
                    case 9:
                        sinSelec(String.valueOf(btn));
                        break;
                    case 10:
                        sinSelec(String.valueOf(btn));
                        break;
                }
                a.dispose();
            }
        }
    }
}
