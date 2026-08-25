package VentanaEmergente.ProyectoManager;

import Conexiones.Conexion;
import java.awt.Color;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

public class InformeProyect extends java.awt.Dialog {

    public void setAgenda(String fechaInicio, String fechaFin, String estatus, String creador, String fecha, String depa, String descripcion,
            String comentarios, String empleadoFin, String fechaTermino) {
        switch (depa) {
            case "DISEÑO":
                txtInicioD.setText(fechaInicio);
                txtFinD.setText(fechaFin);
                txtEstatusD.setText(estatus);
                txtCreadorD.setText(creador);
                txtCreacionD.setText(fecha);
//                txtDescripcionD.setText(descripcion);
//                txtComentariosD.setText(comentarios);
                txtFinalD.setText(empleadoFin);
                txtTerminoD.setText(fechaTermino);
                break;
            case "HERRAMENTISTA":
                txtInicioM.setText(fechaInicio);
                txtFinM.setText(fechaFin);
                txtEstatusM.setText(estatus);
                txtCreadorM.setText(creador);
                txtCreacionM.setText(fecha);
//                txtDescripcionM.setText(descripcion);
//                txtComentariosM.setText(comentarios);
                txtFinalM.setText(empleadoFin);
                txtTerminoM.setText(fechaTermino);
                break;
            case "INTEGRACION":
                txtInicioI.setText(fechaInicio);
                txtFinI.setText(fechaFin);
                txtEstatusI.setText(estatus);
                txtCreadorI.setText(creador);
                txtCreacionI.setText(fecha);
//                txtDescripcionI.setText(descripcion);
//                txtComentariosI.setText(comentarios);
                txtFinalI.setText(empleadoFin);
                txtTerminoI.setText(fechaTermino);
                break;
            case "COMPRAS":
                txtInicioC.setText(fechaInicio);
                txtFinC.setText(fechaFin);
                txtEstatusC.setText(estatus);
                txtCreadorC.setText(creador);
                txtCreacionC.setText(fecha);
//                txtDescripcionC.setText(descripcion);
//                txtComentariosC.setText(comentarios);
                txtFinalC.setText(empleadoFin);
                txtTerminoC.setText(fechaTermino);
                break;
            default:
                break;
        }
    }

    public final void setInformacionAgenda(String proyecto) {
        try {
            Connection con;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            String sql = "select * from agenda where Proyecto like '" + proyecto + "'";
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                String fechaInicio = rs.getString("FechaInicio");
                String fechaFin = rs.getString("FechaFin");
                String estatus = rs.getString("Estatus");
                String creador = rs.getString("Creador");
                String fecha = rs.getString("Fecha");
                String depa = rs.getString("Departamento");
                String descripcion = rs.getString("Descripcion");
                String comentarios = rs.getString("Comentarios");
                String empleadoFin = rs.getString("EmpleadoFin");
                String fechaTermino = rs.getString("FechaTermino");
                setAgenda(fechaInicio, fechaFin, estatus, creador, fecha, depa, descripcion, comentarios, empleadoFin, fechaTermino);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error: " + e, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public InformeProyect(java.awt.Frame parent, boolean modal, String proyecto) {
        super(parent, modal);
        initComponents();
        this.setLocationRelativeTo(parent);
        this.setBackground(new Color(0, 0, 0, 0));
//        jScrollPane3.getVerticalScrollBar().setUnitIncrement(15);
        setInformacionAgenda(proyecto);
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelRound1 = new scrollPane.PanelRound();
        jLabel2 = new javax.swing.JLabel();
        txtProyecto = new javax.swing.JTextField();
        pnlX = new javax.swing.JPanel();
        lblX = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtInicioD = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtFinD = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtEstatusD = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtCreadorD = new javax.swing.JTextField();
        txtCreacionD = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txtFinalD = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txtTerminoD = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        txtTerminoC = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        txtInicioC = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        txtCreacionC = new javax.swing.JTextField();
        txtCreadorC = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        txtFinC = new javax.swing.JTextField();
        txtFinalC = new javax.swing.JTextField();
        txtEstatusC = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        txtFinM = new javax.swing.JTextField();
        txtCreadorM = new javax.swing.JTextField();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        txtEstatusM = new javax.swing.JTextField();
        txtFinalM = new javax.swing.JTextField();
        txtCreacionM = new javax.swing.JTextField();
        jLabel30 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        txtInicioM = new javax.swing.JTextField();
        txtTerminoM = new javax.swing.JTextField();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        txtEstatusI = new javax.swing.JTextField();
        jLabel37 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        txtInicioI = new javax.swing.JTextField();
        jLabel39 = new javax.swing.JLabel();
        txtCreadorI = new javax.swing.JTextField();
        txtFinI = new javax.swing.JTextField();
        jLabel41 = new javax.swing.JLabel();
        txtTerminoI = new javax.swing.JTextField();
        txtFinalI = new javax.swing.JTextField();
        jLabel43 = new javax.swing.JLabel();
        jLabel44 = new javax.swing.JLabel();
        txtCreacionI = new javax.swing.JTextField();

        setBackground(new java.awt.Color(51, 255, 51));
        setUndecorated(true);
        setPreferredSize(new java.awt.Dimension(598, 650));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                closeDialog(evt);
            }
        });

        panelRound1.setBackground(new java.awt.Color(255, 255, 255));
        panelRound1.setMinimumSize(new java.awt.Dimension(682, 615));
        panelRound1.setPreferredSize(new java.awt.Dimension(682, 615));
        panelRound1.setRoundBottomLeft(50);
        panelRound1.setRoundBottomRight(50);
        panelRound1.setRoundTopLeft(50);
        panelRound1.setRoundTopRight(50);
        panelRound1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("OCR A Extended", 0, 36)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(153, 153, 153));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Proyecto");
        panelRound1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 10, 530, 40));

        txtProyecto.setEditable(false);
        txtProyecto.setBackground(new java.awt.Color(255, 255, 255));
        txtProyecto.setFont(new java.awt.Font("Roboto", 1, 36)); // NOI18N
        txtProyecto.setForeground(new java.awt.Color(51, 51, 51));
        txtProyecto.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtProyecto.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 3, 0, new java.awt.Color(204, 204, 204)));
        panelRound1.add(txtProyecto, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 60, 310, 30));

        pnlX.setBackground(new java.awt.Color(255, 255, 255));
        pnlX.setLayout(new java.awt.BorderLayout());

        lblX.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        lblX.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblX.setText(" X ");
        lblX.setToolTipText("");
        lblX.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblX.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblXMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblXMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblXMouseExited(evt);
            }
        });
        pnlX.add(lblX, java.awt.BorderLayout.CENTER);

        panelRound1.add(pnlX, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 0, 20, 20));

        jLabel1.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 153, 0));
        jLabel1.setText("Diseno");
        panelRound1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 150, 50, -1));

        jLabel3.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(51, 51, 51));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Fecha Inicio");
        panelRound1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 100, 70, -1));

        txtInicioD.setEditable(false);
        txtInicioD.setBackground(new java.awt.Color(255, 255, 255));
        txtInicioD.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        txtInicioD.setForeground(new java.awt.Color(51, 51, 51));
        txtInicioD.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtInicioD.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        panelRound1.add(txtInicioD, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 120, 70, 30));

        jLabel4.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(51, 51, 51));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Fecha Fin");
        panelRound1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 100, 70, -1));

        txtFinD.setEditable(false);
        txtFinD.setBackground(new java.awt.Color(255, 255, 255));
        txtFinD.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        txtFinD.setForeground(new java.awt.Color(51, 51, 51));
        txtFinD.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtFinD.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        panelRound1.add(txtFinD, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 120, 70, 30));

        jLabel5.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(51, 51, 51));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Estatus");
        panelRound1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 100, 70, -1));

        txtEstatusD.setEditable(false);
        txtEstatusD.setBackground(new java.awt.Color(255, 255, 255));
        txtEstatusD.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        txtEstatusD.setForeground(new java.awt.Color(51, 51, 51));
        txtEstatusD.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtEstatusD.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        panelRound1.add(txtEstatusD, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 120, 70, 30));

        jLabel6.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(51, 51, 51));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Creador");
        panelRound1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 160, 180, -1));

        txtCreadorD.setEditable(false);
        txtCreadorD.setBackground(new java.awt.Color(255, 255, 255));
        txtCreadorD.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        txtCreadorD.setForeground(new java.awt.Color(51, 51, 51));
        txtCreadorD.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCreadorD.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        panelRound1.add(txtCreadorD, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 180, 180, 30));

        txtCreacionD.setEditable(false);
        txtCreacionD.setBackground(new java.awt.Color(255, 255, 255));
        txtCreacionD.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        txtCreacionD.setForeground(new java.awt.Color(51, 51, 51));
        txtCreacionD.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCreacionD.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        panelRound1.add(txtCreacionD, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 120, 90, 30));

        jLabel8.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(51, 51, 51));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("Fecha Creacion");
        panelRound1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 100, 90, -1));

        jLabel10.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(51, 51, 51));
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("Empleado Final");
        panelRound1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 160, 180, -1));

        txtFinalD.setEditable(false);
        txtFinalD.setBackground(new java.awt.Color(255, 255, 255));
        txtFinalD.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        txtFinalD.setForeground(new java.awt.Color(51, 51, 51));
        txtFinalD.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtFinalD.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        panelRound1.add(txtFinalD, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 180, 180, 30));

        jLabel11.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(51, 51, 51));
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("Fecha Termino");
        panelRound1.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 100, 90, -1));

        txtTerminoD.setEditable(false);
        txtTerminoD.setBackground(new java.awt.Color(255, 255, 255));
        txtTerminoD.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        txtTerminoD.setForeground(new java.awt.Color(51, 51, 51));
        txtTerminoD.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtTerminoD.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        panelRound1.add(txtTerminoD, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 120, 90, 30));

        jLabel12.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(204, 0, 0));
        jLabel12.setText("Compras");
        panelRound1.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 290, -1, -1));

        jLabel14.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(51, 51, 51));
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel14.setText("Creador");
        panelRound1.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 300, 180, -1));

        jLabel15.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(51, 51, 51));
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel15.setText("Fecha Termino");
        panelRound1.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 240, 90, -1));

        txtTerminoC.setEditable(false);
        txtTerminoC.setBackground(new java.awt.Color(255, 255, 255));
        txtTerminoC.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        txtTerminoC.setForeground(new java.awt.Color(51, 51, 51));
        txtTerminoC.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtTerminoC.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        panelRound1.add(txtTerminoC, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 260, 90, 30));

        jLabel16.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(51, 51, 51));
        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel16.setText("Empleado Final");
        panelRound1.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 300, 180, -1));

        txtInicioC.setEditable(false);
        txtInicioC.setBackground(new java.awt.Color(255, 255, 255));
        txtInicioC.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        txtInicioC.setForeground(new java.awt.Color(51, 51, 51));
        txtInicioC.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtInicioC.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        panelRound1.add(txtInicioC, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 260, 70, 30));

        jLabel17.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(51, 51, 51));
        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel17.setText("Fecha Inicio");
        panelRound1.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 240, 70, -1));

        jLabel18.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(51, 51, 51));
        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel18.setText("Fecha Fin");
        panelRound1.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 240, 70, -1));

        jLabel19.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(51, 51, 51));
        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel19.setText("Estatus");
        panelRound1.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 240, 70, -1));

        txtCreacionC.setEditable(false);
        txtCreacionC.setBackground(new java.awt.Color(255, 255, 255));
        txtCreacionC.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        txtCreacionC.setForeground(new java.awt.Color(51, 51, 51));
        txtCreacionC.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCreacionC.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        panelRound1.add(txtCreacionC, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 260, 90, 30));

        txtCreadorC.setEditable(false);
        txtCreadorC.setBackground(new java.awt.Color(255, 255, 255));
        txtCreadorC.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        txtCreadorC.setForeground(new java.awt.Color(51, 51, 51));
        txtCreadorC.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCreadorC.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        panelRound1.add(txtCreadorC, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 320, 180, 30));

        jLabel20.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(51, 51, 51));
        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel20.setText("Fecha Creacion");
        panelRound1.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 240, 90, -1));

        txtFinC.setEditable(false);
        txtFinC.setBackground(new java.awt.Color(255, 255, 255));
        txtFinC.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        txtFinC.setForeground(new java.awt.Color(51, 51, 51));
        txtFinC.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtFinC.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        panelRound1.add(txtFinC, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 260, 70, 30));

        txtFinalC.setEditable(false);
        txtFinalC.setBackground(new java.awt.Color(255, 255, 255));
        txtFinalC.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        txtFinalC.setForeground(new java.awt.Color(51, 51, 51));
        txtFinalC.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtFinalC.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        panelRound1.add(txtFinalC, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 320, 180, 30));

        txtEstatusC.setEditable(false);
        txtEstatusC.setBackground(new java.awt.Color(255, 255, 255));
        txtEstatusC.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        txtEstatusC.setForeground(new java.awt.Color(51, 51, 51));
        txtEstatusC.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtEstatusC.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        panelRound1.add(txtEstatusC, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 260, 70, 30));

        jLabel23.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(0, 102, 204));
        jLabel23.setText("Maquinados");
        panelRound1.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 430, -1, -1));

        jLabel25.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(51, 51, 51));
        jLabel25.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel25.setText("Creador");
        panelRound1.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 440, 180, -1));

        txtFinM.setEditable(false);
        txtFinM.setBackground(new java.awt.Color(255, 255, 255));
        txtFinM.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        txtFinM.setForeground(new java.awt.Color(51, 51, 51));
        txtFinM.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtFinM.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        panelRound1.add(txtFinM, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 400, 70, 30));

        txtCreadorM.setEditable(false);
        txtCreadorM.setBackground(new java.awt.Color(255, 255, 255));
        txtCreadorM.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        txtCreadorM.setForeground(new java.awt.Color(51, 51, 51));
        txtCreadorM.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCreadorM.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        panelRound1.add(txtCreadorM, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 460, 180, 30));

        jLabel26.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(51, 51, 51));
        jLabel26.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel26.setText("Empleado Final");
        panelRound1.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 440, 180, -1));

        jLabel27.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(51, 51, 51));
        jLabel27.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel27.setText("Fecha Creacion");
        panelRound1.add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 380, 90, -1));

        jLabel28.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(51, 51, 51));
        jLabel28.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel28.setText("Fecha Fin");
        panelRound1.add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 380, 70, -1));

        txtEstatusM.setEditable(false);
        txtEstatusM.setBackground(new java.awt.Color(255, 255, 255));
        txtEstatusM.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        txtEstatusM.setForeground(new java.awt.Color(51, 51, 51));
        txtEstatusM.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtEstatusM.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        panelRound1.add(txtEstatusM, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 400, 70, 30));

        txtFinalM.setEditable(false);
        txtFinalM.setBackground(new java.awt.Color(255, 255, 255));
        txtFinalM.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        txtFinalM.setForeground(new java.awt.Color(51, 51, 51));
        txtFinalM.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtFinalM.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        panelRound1.add(txtFinalM, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 460, 180, 30));

        txtCreacionM.setEditable(false);
        txtCreacionM.setBackground(new java.awt.Color(255, 255, 255));
        txtCreacionM.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        txtCreacionM.setForeground(new java.awt.Color(51, 51, 51));
        txtCreacionM.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCreacionM.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        panelRound1.add(txtCreacionM, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 400, 90, 30));

        jLabel30.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        jLabel30.setForeground(new java.awt.Color(51, 51, 51));
        jLabel30.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel30.setText("Estatus");
        panelRound1.add(jLabel30, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 380, 70, -1));

        jLabel32.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        jLabel32.setForeground(new java.awt.Color(51, 51, 51));
        jLabel32.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel32.setText("Fecha Termino");
        panelRound1.add(jLabel32, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 380, 90, -1));

        txtInicioM.setEditable(false);
        txtInicioM.setBackground(new java.awt.Color(255, 255, 255));
        txtInicioM.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        txtInicioM.setForeground(new java.awt.Color(51, 51, 51));
        txtInicioM.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtInicioM.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        panelRound1.add(txtInicioM, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 400, 70, 30));

        txtTerminoM.setEditable(false);
        txtTerminoM.setBackground(new java.awt.Color(255, 255, 255));
        txtTerminoM.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        txtTerminoM.setForeground(new java.awt.Color(51, 51, 51));
        txtTerminoM.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtTerminoM.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        panelRound1.add(txtTerminoM, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 400, 90, 30));

        jLabel33.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        jLabel33.setForeground(new java.awt.Color(51, 51, 51));
        jLabel33.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel33.setText("Fecha Inicio");
        panelRound1.add(jLabel33, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 380, 70, -1));

        jLabel34.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel34.setForeground(new java.awt.Color(0, 153, 51));
        jLabel34.setText("Integracion");
        panelRound1.add(jLabel34, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 570, -1, -1));

        jLabel36.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        jLabel36.setForeground(new java.awt.Color(51, 51, 51));
        jLabel36.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel36.setText("Fecha Fin");
        panelRound1.add(jLabel36, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 520, 70, -1));

        txtEstatusI.setEditable(false);
        txtEstatusI.setBackground(new java.awt.Color(255, 255, 255));
        txtEstatusI.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        txtEstatusI.setForeground(new java.awt.Color(51, 51, 51));
        txtEstatusI.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtEstatusI.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        panelRound1.add(txtEstatusI, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 540, 70, 30));

        jLabel37.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        jLabel37.setForeground(new java.awt.Color(51, 51, 51));
        jLabel37.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel37.setText("Fecha Termino");
        panelRound1.add(jLabel37, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 520, 90, -1));

        jLabel38.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        jLabel38.setForeground(new java.awt.Color(51, 51, 51));
        jLabel38.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel38.setText("Estatus");
        panelRound1.add(jLabel38, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 520, 70, -1));

        txtInicioI.setEditable(false);
        txtInicioI.setBackground(new java.awt.Color(255, 255, 255));
        txtInicioI.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        txtInicioI.setForeground(new java.awt.Color(51, 51, 51));
        txtInicioI.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtInicioI.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        panelRound1.add(txtInicioI, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 540, 70, 30));

        jLabel39.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        jLabel39.setForeground(new java.awt.Color(51, 51, 51));
        jLabel39.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel39.setText("Fecha Creacion");
        panelRound1.add(jLabel39, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 520, 90, -1));

        txtCreadorI.setEditable(false);
        txtCreadorI.setBackground(new java.awt.Color(255, 255, 255));
        txtCreadorI.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        txtCreadorI.setForeground(new java.awt.Color(51, 51, 51));
        txtCreadorI.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCreadorI.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        panelRound1.add(txtCreadorI, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 600, 180, 30));

        txtFinI.setEditable(false);
        txtFinI.setBackground(new java.awt.Color(255, 255, 255));
        txtFinI.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        txtFinI.setForeground(new java.awt.Color(51, 51, 51));
        txtFinI.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtFinI.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        panelRound1.add(txtFinI, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 540, 70, 30));

        jLabel41.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        jLabel41.setForeground(new java.awt.Color(51, 51, 51));
        jLabel41.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel41.setText("Empleado Final");
        panelRound1.add(jLabel41, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 580, 180, -1));

        txtTerminoI.setEditable(false);
        txtTerminoI.setBackground(new java.awt.Color(255, 255, 255));
        txtTerminoI.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        txtTerminoI.setForeground(new java.awt.Color(51, 51, 51));
        txtTerminoI.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtTerminoI.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        panelRound1.add(txtTerminoI, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 540, 90, 30));

        txtFinalI.setEditable(false);
        txtFinalI.setBackground(new java.awt.Color(255, 255, 255));
        txtFinalI.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        txtFinalI.setForeground(new java.awt.Color(51, 51, 51));
        txtFinalI.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtFinalI.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        panelRound1.add(txtFinalI, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 600, 180, 30));

        jLabel43.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        jLabel43.setForeground(new java.awt.Color(51, 51, 51));
        jLabel43.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel43.setText("Fecha Inicio");
        panelRound1.add(jLabel43, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 520, 70, -1));

        jLabel44.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        jLabel44.setForeground(new java.awt.Color(51, 51, 51));
        jLabel44.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel44.setText("Creador");
        panelRound1.add(jLabel44, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 580, 180, -1));

        txtCreacionI.setEditable(false);
        txtCreacionI.setBackground(new java.awt.Color(255, 255, 255));
        txtCreacionI.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        txtCreacionI.setForeground(new java.awt.Color(51, 51, 51));
        txtCreacionI.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCreacionI.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        panelRound1.add(txtCreacionI, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 540, 90, 30));

        add(panelRound1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void closeDialog(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_closeDialog
        setVisible(false);
        dispose();
    }//GEN-LAST:event_closeDialog

    private void lblXMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblXMouseClicked
        this.dispose();
    }//GEN-LAST:event_lblXMouseClicked

    private void lblXMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblXMouseEntered
        pnlX.setBackground(Color.red);
        lblX.setForeground(Color.white);
    }//GEN-LAST:event_lblXMouseEntered

    private void lblXMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblXMouseExited
        pnlX.setBackground(Color.white);
        lblX.setForeground(Color.black);
    }//GEN-LAST:event_lblXMouseExited

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                InformeProyect dialog = new InformeProyect(new java.awt.Frame(), true, "");
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
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel lblX;
    private scrollPane.PanelRound panelRound1;
    private javax.swing.JPanel pnlX;
    private javax.swing.JTextField txtCreacionC;
    private javax.swing.JTextField txtCreacionD;
    private javax.swing.JTextField txtCreacionI;
    private javax.swing.JTextField txtCreacionM;
    private javax.swing.JTextField txtCreadorC;
    private javax.swing.JTextField txtCreadorD;
    private javax.swing.JTextField txtCreadorI;
    private javax.swing.JTextField txtCreadorM;
    private javax.swing.JTextField txtEstatusC;
    private javax.swing.JTextField txtEstatusD;
    private javax.swing.JTextField txtEstatusI;
    private javax.swing.JTextField txtEstatusM;
    private javax.swing.JTextField txtFinC;
    private javax.swing.JTextField txtFinD;
    private javax.swing.JTextField txtFinI;
    private javax.swing.JTextField txtFinM;
    private javax.swing.JTextField txtFinalC;
    private javax.swing.JTextField txtFinalD;
    private javax.swing.JTextField txtFinalI;
    private javax.swing.JTextField txtFinalM;
    private javax.swing.JTextField txtInicioC;
    private javax.swing.JTextField txtInicioD;
    private javax.swing.JTextField txtInicioI;
    private javax.swing.JTextField txtInicioM;
    public javax.swing.JTextField txtProyecto;
    private javax.swing.JTextField txtTerminoC;
    private javax.swing.JTextField txtTerminoD;
    private javax.swing.JTextField txtTerminoI;
    private javax.swing.JTextField txtTerminoM;
    // End of variables declaration//GEN-END:variables
}
