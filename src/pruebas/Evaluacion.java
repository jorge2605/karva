package pruebas;

import Conexiones.Conexion;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.Stack;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;

public class Evaluacion extends javax.swing.JInternalFrame implements ActionListener{

    public Stack<JButton> btnPedido;
    public Stack<JPanel> panelPedido;
    public Stack<String> requi;
    public Stack<String> color;
    public int seleccionado = 0;
    
    public String mostrarDialogoEmergente() {
        JTextArea textField = new JTextArea();
        textField.setBackground(new java.awt.Color(255, 255, 255));
        textField.setColumns(20);
        textField.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        textField.setLineWrap(true);
        textField.setRows(5);
        textField.setWrapStyleWord(true);
        textField.setBorder(null);
        JScrollPane scroll = new JScrollPane();
        scroll.setBorder(null);
        scroll.setViewportView(textField);

        Object[] mensaje = {"Ingrese notas de cancelacion:", scroll};
        ImageIcon icono = new ImageIcon(getClass().getResource("/IconoC/cancelar_1.png"));
        int opcion = JOptionPane.showOptionDialog(
                null,
                mensaje,
                "Ingrese notas",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                icono,
                null,
                null
        );if (opcion == JOptionPane.OK_OPTION) {
            return textField.getText();
        } else {
            return null;
        }
    }
    
    public final void limpiarPanel(){
        PanelPedidos.removeAll();
        revalidate();
        repaint();
    }
    
    public final void addBotonesPedido(){
        try{
            Connection con;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            String sql = "select * from requisicion where Progreso like 'EVALUACION' order by Id desc";
            ResultSet rs = st.executeQuery(sql);
            String datos[] = new String[10];
            
            btnPedido = new Stack<>();
            panelPedido = new Stack<>();
            requi = new Stack<>();
            color = new Stack<>();
            
            while(rs.next()){
                datos[0] = rs.getString("Id");
                requi.push(datos[0]);
                datos[1] = rs.getString("Fecha");
                datos[2] = rs.getString("Progreso");

                JPanel panel = new JPanel();
                panel.setBackground(new java.awt.Color(255, 255, 255));
                panel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
                panel.setForeground(new java.awt.Color(51, 153, 255));

                JButton boton = new JButton();

                boton.setFont(new java.awt.Font("Roboto", 0, 12));
                boton.setBorder(null);
                boton.setBorderPainted(false);
                boton.setContentAreaFilled(false);
                boton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
                boton.setFocusPainted(false);
                boton.setForeground(new Color(51,153,255));

                String color ="153,204,255";
                if(datos[2].equals("NUEVO")){
                    color = "157,154,210";
                    panel.setBackground(new Color(157,154,210));
                }else if(datos[2].equals("COTIZANDO")){
                    color = "200,39,4";
                    panel.setBackground(new Color(200,39,4));
                }
                this.color.push(color);
                boton.setText(
                                "<html>\n" +
                                "<div style=\"width:150px;\">\n" +
                                "<h1 style=\"text-align:center; margin:5px;\">"+datos[0]+"</h1>\n" +
                                "<h3 style=\"text-align:center; margin:5px;\">"+datos[2]+"</>\n" +
                                "<h5 style=\" margin:5px;\">"+datos[1]+"</h5>\n" +
                                "</div>"
                );

                boton.addActionListener(this);
                panelPedido.push(panel);
                btnPedido.push(boton);
                int can = panelPedido.size()-1;
                panelPedido.get(can).add(btnPedido.get(can));

                java.awt.GridBagConstraints gridBagConstraints = new java.awt.GridBagConstraints();
                gridBagConstraints.gridx = 0;
                gridBagConstraints.gridy = can;
                gridBagConstraints.insets = new java.awt.Insets(13, 13, 13, 13);

                PanelPedidos.add(panelPedido.get(can), gridBagConstraints);
                revalidate();
                repaint();
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "ERROR: "+e,"ERROR",JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public final void limpiarTabla(){
        Tabla1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                
            },
            new String [] {
                "Codigo", "Descripcion", "Cantidad", "Proyecto", "Precio"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };
            
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        
        Tabla1.getTableHeader().setFont(new Font("Lexend", Font.BOLD, 14));
        Tabla1.getTableHeader().setOpaque(false);
        Tabla1.getTableHeader().setBackground(new Color(0, 78, 171));
        Tabla1.getTableHeader().setForeground(Color.white);
        Tabla1.setRowHeight(25);
        Tabla1.setShowVerticalLines(false);
        Tabla1.setGridColor(new Color(240,240,240));
        
        jScrollPane2.getViewport().setBackground(new Color(255,255,255));
        
        if (Tabla1.getColumnModel().getColumnCount() > 0) {
            Tabla1.getColumnModel().getColumn(2).setMinWidth(100);
            Tabla1.getColumnModel().getColumn(2).setPreferredWidth(100);
            Tabla1.getColumnModel().getColumn(2).setMaxWidth(100);
            Tabla1.getColumnModel().getColumn(4).setMinWidth(150);
            Tabla1.getColumnModel().getColumn(4).setPreferredWidth(150);
            Tabla1.getColumnModel().getColumn(4).setMaxWidth(150);
            Tabla1.getColumnModel().getColumn(3).setMinWidth(150);
            Tabla1.getColumnModel().getColumn(3).setPreferredWidth(150);
            Tabla1.getColumnModel().getColumn(3).setMaxWidth(150);
        }
    }
    
    public void verRequisicion(String requisicion){
        try{
            limpiarTabla();
            limpiarDatos();
            Connection con;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            String sql = "select Codigo, Descripcion, Cantidad, Proyecto, Requisitor from requisiciones where NumRequisicion like '" + requisicion + "'";
            ResultSet rs = st.executeQuery(sql);
            String datos[] = new String[10];
            DefaultTableModel miModelo = (DefaultTableModel) Tabla1.getModel();
            lblRequi.setText(requisicion);
            while(rs.next()){
                datos[0] = rs.getString("Codigo");
                datos[1] = rs.getString("Descripcion");
                datos[2] = rs.getString("Cantidad");
                datos[3] = rs.getString("Proyecto");
                
                lblRequisitor.setText(rs.getString("Requisitor"));
                lblProyecto.setText(datos[3]);
                
                String sql2 = "select Precio,Codigo from requisiciones where Codigo like '"+datos[0]+"' and Precio is not null";
                Statement st2 = con.createStatement();
                ResultSet rs2 = st2.executeQuery(sql2);
                String ubicacion = null;
                while(rs2.next()){
                    ubicacion = rs2.getString("Precio");
                }
                datos[4] = ubicacion;
                miModelo.addRow(datos);
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "ERROR: "+e,"ERROR",JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void pintarBotonAzul(int i){
        btnPedido.get(i).setBackground(new Color(51,153,255));
        panelPedido.get(i).setBackground(new Color(51,153,255));
        btnPedido.get(i).setForeground(new Color(254,254,254));
    }
    
    public void pintarBlanco(int i){
        btnPedido.get(i).setBackground(new Color(254,254,254));
        panelPedido.get(i).setBackground(new Color(254,254,254));
        btnPedido.get(i).setForeground(new Color(51,153,255));
    }
    
    public final void calcularProyecto(){
        try{
            Connection con;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            String sql = "select Precio, Proyecto, Proveedor from requisiciones where Proyecto like '"+lblProyecto.getText()+"'";
            ResultSet rs = st.executeQuery(sql);
            double totalUsd = 0;
            double totalMxn = 0;
            double totalNull = 0;
            String pro = lblProyecto.getText();
            if(!pro.equals("MATERIAL DE OFICINA") && !pro.equals("MATERIAL DE MANTENIMIENTO") && !pro.equals("MATERIAL DE LIMPIEZA")
                     && !pro.equals("HERRAMIENTAS") && !pro.equals("SEGURIDAD")){
                while(rs.next()){
                    double precioMxn;
                    String proveedor = rs.getString("Proveedor");
                    String sql2 = "select Nombre, Moneda from registroprov_compras where Nombre like '"+proveedor+"'";
                    Statement st2 = con.createStatement();
                    ResultSet rs2 = st2.executeQuery(sql2);
                    String moneda = null;
                    while(rs2.next()){
                        moneda = rs2.getString("Moneda");
                    }
                    try{precioMxn = Double.parseDouble(rs.getString("Precio"));}catch(Exception e){precioMxn = 0;}

                    if(moneda == null){
                        totalNull += precioMxn;
                    }else if(moneda.equals("MXN")){
                        totalMxn += precioMxn;
                    }else{
                        totalUsd += precioMxn;
                    }

                }
                DecimalFormat format = new DecimalFormat("#,###.##");
                lblGastado.setText("MXN: " + String.valueOf(format.format(totalMxn)) + " USD: " + format.format(totalUsd) + " Sin definir: "+format.format(totalNull));

                sql = "select Costo, Moneda, Proyecto from proyectos where Proyecto like '"+lblProyecto.getText()+"'";
                Statement st2 = con.createStatement();
                ResultSet rs2 = st2.executeQuery(sql);
                while(rs2.next()){
                    lblTotal.setText(rs2.getString("Costo") + " " + rs2.getString("Moneda"));
                }
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "Error: "+e,"error",JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public final void limpiarDatos(){
        lblGastado.setText("*");
        lblProyecto.setText("*");
        lblRequi.setText("*");
        lblRequisitor.setText("*");
        lblTotal.setText("*");
    }
    
    public Evaluacion(String numEmpleado) {
        initComponents();
        limpiarPanel();
        addBotonesPedido();
        limpiarTabla();
        jScrollPane1.getVerticalScrollBar().setUnitIncrement(15);
        ((javax.swing.plaf.basic.BasicInternalFrameUI) this.getUI()).setNorthPane(null);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        pan = new javax.swing.JPanel();
        panelSalir = new javax.swing.JPanel();
        lblSalir = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        PanelPedidos = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        panelAceptar = new javax.swing.JPanel();
        btnAceptar = new javax.swing.JButton();
        panelRechazar = new javax.swing.JPanel();
        btnRechazar = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        Tabla1 = new javax.swing.JTable();
        jPanel10 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        lblRequisitor = new javax.swing.JLabel();
        lblRequi = new javax.swing.JLabel();
        lblProyecto = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jPanel14 = new javax.swing.JPanel();
        lblTotal = new javax.swing.JLabel();
        lblGastado = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();

        setBorder(null);
        getContentPane().setLayout(new java.awt.BorderLayout(10, 10));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new java.awt.BorderLayout(10, 10));

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setLayout(new java.awt.BorderLayout());

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));

        jLabel12.setFont(new java.awt.Font("Lexend", 1, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(0, 165, 252));
        jLabel12.setText("Evaluacion");
        jPanel6.add(jLabel12);

        jPanel5.add(jPanel6);

        jPanel4.add(jPanel5, java.awt.BorderLayout.CENTER);

        pan.setBackground(new java.awt.Color(255, 255, 255));

        panelSalir.setBackground(new java.awt.Color(255, 255, 255));

        lblSalir.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
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

        pan.add(panelSalir);

        jPanel4.add(pan, java.awt.BorderLayout.EAST);

        jPanel1.add(jPanel4, java.awt.BorderLayout.NORTH);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new java.awt.BorderLayout(10, 10));

        jScrollPane1.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 1, true));
        jScrollPane1.setPreferredSize(new java.awt.Dimension(250, 100));

        PanelPedidos.setBackground(new java.awt.Color(255, 255, 255));
        PanelPedidos.setPreferredSize(new java.awt.Dimension(100, 100));
        PanelPedidos.setLayout(new java.awt.GridBagLayout());
        jScrollPane1.setViewportView(PanelPedidos);

        jPanel2.add(jScrollPane1, java.awt.BorderLayout.LINE_START);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setLayout(new java.awt.BorderLayout(10, 10));

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));
        jPanel9.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 100, 5));

        panelAceptar.setBackground(new java.awt.Color(51, 153, 255));

        btnAceptar.setFont(new java.awt.Font("Lexend", 1, 14)); // NOI18N
        btnAceptar.setForeground(new java.awt.Color(255, 255, 255));
        btnAceptar.setText("Aceptar");
        btnAceptar.setBorderPainted(false);
        btnAceptar.setContentAreaFilled(false);
        btnAceptar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAceptar.setFocusPainted(false);
        btnAceptar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnAceptarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnAceptarMouseExited(evt);
            }
        });
        btnAceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAceptarActionPerformed(evt);
            }
        });
        panelAceptar.add(btnAceptar);

        jPanel9.add(panelAceptar);

        panelRechazar.setBackground(new java.awt.Color(204, 0, 0));

        btnRechazar.setFont(new java.awt.Font("Lexend", 1, 14)); // NOI18N
        btnRechazar.setForeground(new java.awt.Color(255, 255, 255));
        btnRechazar.setText("Rechazar");
        btnRechazar.setBorderPainted(false);
        btnRechazar.setContentAreaFilled(false);
        btnRechazar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnRechazar.setFocusPainted(false);
        btnRechazar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnRechazarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnRechazarMouseExited(evt);
            }
        });
        btnRechazar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRechazarActionPerformed(evt);
            }
        });
        panelRechazar.add(btnRechazar);

        jPanel9.add(panelRechazar);

        jPanel3.add(jPanel9, java.awt.BorderLayout.PAGE_END);

        jScrollPane2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));

        Tabla1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Codigo", "Descripcion", "Cantidad", "Proyecto"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(Tabla1);

        jPanel3.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        jPanel10.setBackground(new java.awt.Color(255, 255, 255));
        jPanel10.setLayout(new java.awt.GridLayout(1, 0, 10, 10));

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setLayout(new java.awt.BorderLayout(10, 0));

        jPanel11.setBackground(new java.awt.Color(255, 255, 255));
        jPanel11.setLayout(new java.awt.GridLayout(3, 0, 10, 10));

        jLabel1.setFont(new java.awt.Font("Lexend", 1, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(102, 102, 102));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setText("Requisitor:   ");
        jPanel11.add(jLabel1);

        jLabel2.setFont(new java.awt.Font("Lexend", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(102, 102, 102));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText("                    Requisicion");
        jPanel11.add(jLabel2);

        jLabel3.setFont(new java.awt.Font("Lexend", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(102, 102, 102));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText("Proyecto:   ");
        jPanel11.add(jLabel3);

        jPanel7.add(jPanel11, java.awt.BorderLayout.LINE_START);

        jPanel12.setBackground(new java.awt.Color(255, 255, 255));
        jPanel12.setLayout(new java.awt.GridLayout(3, 0, 0, 10));

        lblRequisitor.setFont(new java.awt.Font("Lexend", 1, 12)); // NOI18N
        lblRequisitor.setForeground(new java.awt.Color(0, 102, 204));
        lblRequisitor.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblRequisitor.setText("*");
        jPanel12.add(lblRequisitor);

        lblRequi.setFont(new java.awt.Font("Lexend", 1, 12)); // NOI18N
        lblRequi.setForeground(new java.awt.Color(0, 102, 204));
        lblRequi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblRequi.setText("*");
        jPanel12.add(lblRequi);

        lblProyecto.setFont(new java.awt.Font("Lexend", 1, 12)); // NOI18N
        lblProyecto.setForeground(new java.awt.Color(0, 102, 204));
        lblProyecto.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblProyecto.setText("*");
        jPanel12.add(lblProyecto);

        jPanel7.add(jPanel12, java.awt.BorderLayout.CENTER);

        jPanel10.add(jPanel7);

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));
        jPanel8.setLayout(new java.awt.BorderLayout());

        jPanel13.setBackground(new java.awt.Color(255, 255, 255));
        jPanel13.setLayout(new java.awt.GridLayout(3, 0, 0, 10));

        jLabel4.setFont(new java.awt.Font("Lexend", 1, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(102, 102, 102));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setText("Precio total de proyecto: ");
        jPanel13.add(jLabel4);

        jLabel5.setFont(new java.awt.Font("Lexend", 1, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(102, 102, 102));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel5.setText("Total Gastado:   ");
        jPanel13.add(jLabel5);

        jPanel8.add(jPanel13, java.awt.BorderLayout.WEST);

        jPanel14.setBackground(new java.awt.Color(255, 255, 255));
        jPanel14.setLayout(new java.awt.GridLayout(3, 0, 0, 10));

        lblTotal.setFont(new java.awt.Font("Lexend", 1, 12)); // NOI18N
        lblTotal.setForeground(new java.awt.Color(0, 102, 204));
        lblTotal.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblTotal.setText("*");
        jPanel14.add(lblTotal);

        lblGastado.setFont(new java.awt.Font("Lexend", 1, 12)); // NOI18N
        lblGastado.setForeground(new java.awt.Color(0, 102, 204));
        lblGastado.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblGastado.setText("*");
        jPanel14.add(lblGastado);

        jLabel7.setFont(new java.awt.Font("Lexend", 1, 10)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 102, 0));
        jLabel7.setText("<html>\n<div style=\"width:150px;\">\n<p>\nNota: El precio de los productos estan basados en compras anteriores\n</p>\n</div>");
        jPanel14.add(jLabel7);

        jPanel8.add(jPanel14, java.awt.BorderLayout.CENTER);

        jPanel10.add(jPanel8);

        jPanel3.add(jPanel10, java.awt.BorderLayout.PAGE_START);

        jPanel2.add(jPanel3, java.awt.BorderLayout.CENTER);

        jPanel1.add(jPanel2, java.awt.BorderLayout.CENTER);

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

    private void btnAceptarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAceptarMouseEntered
        panelAceptar.setBackground(new Color(0,102,204));
    }//GEN-LAST:event_btnAceptarMouseEntered

    private void btnAceptarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAceptarMouseExited
        panelAceptar.setBackground(new Color(51,153,255));
    }//GEN-LAST:event_btnAceptarMouseExited

    private void btnAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAceptarActionPerformed
        try{
            Connection con;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            String sql = "update requisicion set Progreso = ? where Id = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            
            pst.setString(1, "NUEVO");
            pst.setString(2, lblRequi.getText());
            
            int n = pst.executeUpdate();
            
            if(n > 0){
                JOptionPane.showMessageDialog(this, "Datos Guardados");
                limpiarPanel();
                addBotonesPedido();
                limpiarTabla();
                limpiarDatos();
            }
            
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "ERROR: "+e,"ERROR",JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnAceptarActionPerformed

    private void btnRechazarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnRechazarMouseEntered
        panelRechazar.setBackground(new Color(102,0,0));
    }//GEN-LAST:event_btnRechazarMouseEntered

    private void btnRechazarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnRechazarMouseExited
        panelRechazar.setBackground(new Color(204,0,0));
    }//GEN-LAST:event_btnRechazarMouseExited

    private void btnRechazarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRechazarActionPerformed
        String notas = mostrarDialogoEmergente();
        if(notas != null){
            if(notas.equals("")){
                JOptionPane.showMessageDialog(this, "Debes insertar comentarios", "Error", JOptionPane.ERROR_MESSAGE);
            }else{
                try{
                    Connection con;
                    Conexion con1 = new Conexion();
                    con = con1.getConnection();
                    String sql = "update requisicion set Progreso = ?, Comentarios = ?, Completado = ? where Id = ?";
                    PreparedStatement pst = con.prepareStatement(sql);

                    pst.setString(1, "CANCELADO/COSTOS");
                    pst.setString(2, notas);
                    pst.setString(3, "NO");
                    pst.setString(4, lblRequi.getText());

                    int n = pst.executeUpdate();

                    if(n > 0){
                        JOptionPane.showMessageDialog(this, "Datos Guardados");
                        limpiarPanel();
                        addBotonesPedido();
                        limpiarTabla();
                        limpiarDatos();
                    }

                }catch(SQLException e){
                    JOptionPane.showMessageDialog(this, "ERROR: "+e,"ERROR",JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }//GEN-LAST:event_btnRechazarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PanelPedidos;
    private javax.swing.JTable Tabla1;
    public javax.swing.JButton btnAceptar;
    public javax.swing.JButton btnRechazar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
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
    private javax.swing.JLabel lblGastado;
    private javax.swing.JLabel lblProyecto;
    private javax.swing.JLabel lblRequi;
    private javax.swing.JLabel lblRequisitor;
    public javax.swing.JLabel lblSalir;
    private javax.swing.JLabel lblTotal;
    private javax.swing.JPanel pan;
    private javax.swing.JPanel panelAceptar;
    private javax.swing.JPanel panelRechazar;
    private javax.swing.JPanel panelSalir;
    // End of variables declaration//GEN-END:variables

    @Override
    public void actionPerformed(ActionEvent e) {
        for (int i = 0; i < btnPedido.size(); i++) {
            if(e.getSource() == btnPedido.get(i)){
                verRequisicion(requi.get(i));
                pintarBlanco(seleccionado);
                seleccionado = i;
                pintarBotonAzul(i);
                calcularProyecto();
            }
        }
    }
}
