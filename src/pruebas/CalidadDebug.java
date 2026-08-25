package pruebas;

import Conexiones.Conexion;
import Controlador.maquinados.revisarPlanos;
import com.mxrck.autocompleter.TextAutoCompleter;
import java.awt.Color;
import java.awt.Desktop;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class CalidadDebug extends javax.swing.JInternalFrame {

    String numEmpleado = ""; 
    TextAutoCompleter au;
    
    public final void verProyectos() {
        try {
            Connection con;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            String sql = "select proyecto from proyectos";
            ResultSet rs = st.executeQuery(sql);
            au = new TextAutoCompleter(jTextField1);
            while (rs.next()) {
                au.addItem(rs.getString("proyecto"));
            }
        } catch(SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al ver proyectos: " + e,"Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void limpiarFormulario() {
        txtCantidad.setText("");
        txtMaterial.setText("");
        txtPlano.setText("");
        txtProyecto.setText("");
        btnPdf.setVisible(false);
    }

    public String getDirectorio(String proyecto) {
        String path = "\\\\serverdell\\03 Project\\04 DISENO\\" + proyecto;
        File direccion = new File(path);

        if (!direccion.isDirectory()) {
            boolean res = direccion.mkdirs();
            if (res) {
                return path;
            } else {
                return path;
            }
        } else {
            return path;
        }
    }

    public String getProyecto(String plano, Connection con) throws SQLException {
        Statement st = con.createStatement();
        String sql = "select proyecto from proyectos where proyecto like '" + plano + "%'";
        ResultSet rs = st.executeQuery(sql);
        while (rs.next()) {
            return rs.getString("Proyecto");
        }
        return null;
    }
    
    public String obtenerCaracter(String plano) {
        String texto = plano;
        
        Pattern pattern = Pattern.compile("[^0-9a-zA-Z ]");
        Matcher matcher = pattern.matcher(texto);

        while (matcher.find()) {
            return  matcher.group();
        }
        return null;
    }

    public String formatear(String plano, Connection con) throws SQLException {
        String caracter = obtenerCaracter(plano);
        String spl[] = plano.split(" ");
        if (caracter != null && plano.contains(caracter)) {
            return plano.substring(0, plano.indexOf(caracter));
        } else if (spl.length == 3) {
            return plano;
        } else if (spl.length == 2) {
            return getProyecto(spl[0], con) + " " + spl[1];
        } else {
            return null;
        }
    }

    public String validarPlano(Connection con, String proyecto) throws SQLException{
        Statement st = con.createStatement();
        String sql = "select proyecto from proyectos where proyecto like '" + proyecto + "%'";
        ResultSet rs = st.executeQuery(sql);
        String proyectoRet = null;
        while (rs.next()) {
            proyectoRet = rs.getString("proyecto");
        }
        return proyectoRet;
    }
    
    public String obtenerDepartamento () {
        switch (cmbEnviar.getSelectedIndex()) {
            case 1: 
                return "maquinados";
            case 2:
                return "trata";
            case 3:
                return "calidad";
            case 4:
                return "integracion";
            case 5:
                return "datos";
            default:
                return null;
        }
    }
    
    public void limpiarTabla() {
        TablaPlan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
            },
            new String [] {
                "Proyecto", "Plano"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
    }
    
    public final void verDatos(String sql) {
        try {
            limpiarTabla();
            Connection con;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            String datos[] = new String[10];
            DefaultTableModel miModelo = (DefaultTableModel) TablaPlan.getModel();
            int cont = 0;
            while(rs.next()) {
                datos[0] = rs.getString("Proyecto");
                datos[1] = rs.getString("Plano");
                miModelo.addRow(datos);
                cont++;
            }
            lblConteo.setText("Cantidad de Planos: " + cont);
        } catch(SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al ver datos calidad: " + e, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void enviarPlano(String plano, String proyecto, boolean mostrarMensaje) {
        revisarPlanos rev = new revisarPlanos();
        rev.mostrarMensaje = mostrarMensaje;
        boolean enviado = false;
        
        Connection con;
        Conexion con1 = new Conexion();
        try {
            con = con1.getConnection();
            if (lblAvisoPlano.isVisible()) {
                String estacion;
                con = con1.getConnection();
                String est = "calidad";
                switch (cmbEnviar.getSelectedIndex()) {
                    case 1: 
                        estacion = "maquinados";
                        rev.retrabajo = true;
                        rev.enviarCortes("calidad", plano, numEmpleado, proyecto, "00");
                        rev.transaccionTerminarPlano(con, plano, proyecto, "00:00", est, numEmpleado, estacion);
                        enviado = true;
                        break;
                    case 2: 
                        estacion = "trata";
                        rev.transaccionTerminarPlano(con, plano, proyecto, "00:00", est, numEmpleado, estacion);
                        enviado = true;
                        break;
                    case 3: 
                        //calidad
                        rev.transaccionTerminarPlano(con, plano, proyecto, "00:00", est, numEmpleado, "calidad");
                        enviado = true;
                        break;
                    case 4: 
                        estacion = "integracion";
                        rev.transaccionTerminarPlano(con, plano, proyecto, "00:00", est, numEmpleado, estacion);
                        enviado = true;
                        break;
                    case 5: 
                        estacion = "datos";
                        rev.enviarCortes("calidad", plano, numEmpleado, proyecto, "00");
                        rev.transaccionTerminarPlano(con, plano, proyecto, "00:00", est, numEmpleado, estacion);
                        enviado = true;
                        break;
                }
            } else {
                String estacionSeleccionada  = obtenerDepartamento();
                String est = estacionSeleccionada.toUpperCase();
                est = (est.equals("TRATA")) ? "TRATAMIENTO" : est;
                if (cmbEnviar.getSelectedIndex() == 5 || cmbEnviar.getSelectedIndex() == 1) {
                    est = "cortes";
                    if (cmbEnviar.getSelectedIndex() == 1) {
                        est = "maquinados";
                        rev.retrabajo = true;
                    }
                    rev.enviarCortes("calidad", plano, numEmpleado, proyecto, "00");
                }
                if (cmbEnviar.getSelectedIndex() == 3) {
                    rev.actualizarPlanos(con, plano, "TERMINADO (CALIDAD)");
                    rev.transaccionTerminarPlano(con, plano, proyecto, "00:00", "calidad", numEmpleado, "calidad");
                    enviado = true;
                } else {
                    rev.actualizarPlanos(con, plano, est.toUpperCase());
                    rev.transaccionTerminarPlano(con, plano, proyecto, "00:00", "calidad", numEmpleado, estacionSeleccionada);
                    enviado = true;
                }
            }
        
            if (enviado) {
                String sql = "insert into planos_calidad (plano, fecha, empleado) values(?,?,?)";
                PreparedStatement pst = con.prepareStatement(sql);

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date d = new Date();

                pst.setString(1, plano);
                pst.setString(2, sdf.format(d));
                pst.setString(3, numEmpleado);

                int n = pst.executeUpdate();

                if (n < 1) {
                    JOptionPane.showMessageDialog(this, "Error al guardar informacion en 'Planos Calidad' favor de reportar", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch(SQLException e) {
            JOptionPane.showMessageDialog(this, "Error: " + e,"Error",JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public final void cerrarFechaCalendario(String proyecto, Connection con) throws SQLException {
        String sql = "update agenda set Estatus = ?, EmpleadoFin = ?, FechaTermino = ? where Proyecto = ? and Departamento = ?";
        PreparedStatement pst = con.prepareStatement(sql);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String fecha = sdf.format(new Date());

        pst.setString(1, "Terminado");
        pst.setString(2, numEmpleado);
        pst.setString(3, fecha);
        pst.setString(4, proyecto);
        pst.setString(5, "HERRAMENTISTA");

        int in = pst.executeUpdate();

        if(in < 1){
            
            sql = "insert into agenda (Estatus , EmpleadoFin, FechaTermino, Proyecto, Departamento) values(?,?,?,?,?)";
            pst = con.prepareStatement(sql);
            
            pst.setString(1, "Terminado");
            pst.setString(2, numEmpleado);
            pst.setString(3, fecha);
            pst.setString(4, proyecto);
            pst.setString(5, "HERRAMENTISTA");
            
            in = pst.executeUpdate();

            if (in < 1) {
                JOptionPane.showMessageDialog(this, "Error al generar fecha", "Error", JOptionPane.ERROR_MESSAGE);
            }    
        }
    }
    
    public final void verificarProyectosTerminados(String proyecto) {
        try {
            Connection con = new Conexion().getConnection();
            Statement st = con.createStatement();
            String sql = "select Estado, Plano, Proyecto from planos where proyecto like '" + proyecto + "'";
            ResultSet rs = st.executeQuery(sql);
            int cont = 0;
            int contTerm = 0;
            while (rs.next()) {
                cont++;
                String estado = rs.getString("Estado");
                if (estado.equals("TERMINADO (CALIDAD)") || estado.equals("INTEGRACION")) {
                    contTerm++;
                }
            }
            if (contTerm >= cont) {
                cerrarFechaCalendario(proyecto, con);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error: " + e,"Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public CalidadDebug(String nombre, String numero) {
        initComponents();
        verProyectos();
        verDatos("select * from planos where Estado like 'CALIDAD' order by id desc");
        btnPdf.setVisible(false);
        numEmpleado = numero;
        lblAvisoPlano.setVisible(false);
        ((javax.swing.plaf.basic.BasicInternalFrameUI) this.getUI()).setNorthPane(null);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPopupMenu1 = new javax.swing.JPopupMenu();
        terminarPlanos = new javax.swing.JMenuItem();
        jPanel1 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        jPanel19 = new javax.swing.JPanel();
        btnSalir = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        txtIngresarPlano = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        txtProyecto = new javax.swing.JTextField();
        lblAvisoPlano = new javax.swing.JLabel();
        btnPdf = new javax.swing.JButton();
        txtPlano = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtCantidad = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtMaterial = new javax.swing.JTextField();
        scrollPlan = new javax.swing.JScrollPane();
        TablaPlan = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        lblConteo = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        cmbEnviar = new javax.swing.JComboBox<>();
        btnEnviar = new javax.swing.JButton();

        jPopupMenu1.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
                jPopupMenu1PopupMenuWillBecomeVisible(evt);
            }
        });

        terminarPlanos.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        terminarPlanos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/entrega-rapida.png"))); // NOI18N
        terminarPlanos.setText("Seleccionar estacion                          ");
        terminarPlanos.setEnabled(false);
        terminarPlanos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                terminarPlanosActionPerformed(evt);
            }
        });
        jPopupMenu1.add(terminarPlanos);

        setBorder(null);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new java.awt.BorderLayout());

        jPanel11.setBackground(new java.awt.Color(255, 255, 255));
        jPanel11.setLayout(new java.awt.BorderLayout());

        jLabel17.setFont(new java.awt.Font("Roboto", 1, 24)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(0, 102, 204));
        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel17.setText("Calidad");
        jPanel11.add(jLabel17, java.awt.BorderLayout.CENTER);

        jPanel19.setBackground(new java.awt.Color(255, 255, 255));

        btnSalir.setBackground(new java.awt.Color(255, 255, 255));

        jLabel11.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("X");
        jLabel11.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jLabel11.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel11MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel11MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel11MouseExited(evt);
            }
        });
        btnSalir.add(jLabel11);

        jPanel19.add(btnSalir);

        jPanel11.add(jPanel19, java.awt.BorderLayout.EAST);

        jPanel1.add(jPanel11, java.awt.BorderLayout.NORTH);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        java.awt.GridBagLayout jPanel2Layout = new java.awt.GridBagLayout();
        jPanel2Layout.columnWeights = new double[] {0.0, 1.0};
        jPanel2.setLayout(jPanel2Layout);

        txtIngresarPlano.setBackground(new java.awt.Color(255, 255, 255));
        txtIngresarPlano.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        txtIngresarPlano.setForeground(new java.awt.Color(51, 51, 51));
        txtIngresarPlano.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtIngresarPlano.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(204, 204, 204)));
        txtIngresarPlano.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIngresarPlanoActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 100, 0, 100);
        jPanel2.add(txtIngresarPlano, gridBagConstraints);

        jLabel1.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 153, 255));
        jLabel1.setText("Proyecto: ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(150, 0, 10, 0);
        jPanel2.add(jLabel1, gridBagConstraints);

        txtProyecto.setEditable(false);
        txtProyecto.setBackground(new java.awt.Color(255, 255, 255));
        txtProyecto.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        txtProyecto.setForeground(new java.awt.Color(51, 51, 51));
        txtProyecto.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtProyecto.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(204, 204, 204)));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 200;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(150, 10, 10, 40);
        jPanel2.add(txtProyecto, gridBagConstraints);

        lblAvisoPlano.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        lblAvisoPlano.setForeground(new java.awt.Color(255, 0, 51));
        lblAvisoPlano.setText("Este plano no esta en la lista de planos subidos por Diseñadores");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridwidth = 2;
        jPanel2.add(lblAvisoPlano, gridBagConstraints);

        btnPdf.setBackground(new java.awt.Color(255, 255, 255));
        btnPdf.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        btnPdf.setForeground(new java.awt.Color(255, 255, 255));
        btnPdf.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/pdf.png"))); // NOI18N
        btnPdf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPdfActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 10, 100);
        jPanel2.add(btnPdf, gridBagConstraints);

        txtPlano.setEditable(false);
        txtPlano.setBackground(new java.awt.Color(255, 255, 255));
        txtPlano.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        txtPlano.setForeground(new java.awt.Color(51, 51, 51));
        txtPlano.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtPlano.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(204, 204, 204)));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 200;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 40);
        jPanel2.add(txtPlano, gridBagConstraints);

        jLabel2.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(51, 153, 255));
        jLabel2.setText("Plano:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(10, 100, 10, 0);
        jPanel2.add(jLabel2, gridBagConstraints);

        jLabel3.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(51, 153, 255));
        jLabel3.setText("Cantidad:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(10, 100, 10, 0);
        jPanel2.add(jLabel3, gridBagConstraints);

        txtCantidad.setEditable(false);
        txtCantidad.setBackground(new java.awt.Color(255, 255, 255));
        txtCantidad.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        txtCantidad.setForeground(new java.awt.Color(51, 51, 51));
        txtCantidad.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCantidad.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(204, 204, 204)));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 200;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 40);
        jPanel2.add(txtCantidad, gridBagConstraints);

        jLabel4.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(51, 153, 255));
        jLabel4.setText("Material:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(10, 100, 10, 0);
        jPanel2.add(jLabel4, gridBagConstraints);

        txtMaterial.setEditable(false);
        txtMaterial.setBackground(new java.awt.Color(255, 255, 255));
        txtMaterial.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        txtMaterial.setForeground(new java.awt.Color(51, 51, 51));
        txtMaterial.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtMaterial.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(204, 204, 204)));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 200;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 40);
        jPanel2.add(txtMaterial, gridBagConstraints);

        scrollPlan.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));

        TablaPlan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Plano"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        TablaPlan.setComponentPopupMenu(jPopupMenu1);
        TablaPlan.setShowGrid(true);
        scrollPlan.setViewportView(TablaPlan);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridheight = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 19, 0, 19);
        jPanel2.add(scrollPlan, gridBagConstraints);

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setLayout(new java.awt.BorderLayout());

        jLabel6.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 102, 204));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Ver por proyecto");
        jPanel4.add(jLabel6, java.awt.BorderLayout.CENTER);

        jTextField1.setBackground(new java.awt.Color(255, 255, 255));
        jTextField1.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        jTextField1.setForeground(new java.awt.Color(51, 51, 51));
        jTextField1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField1.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(204, 204, 204)));
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });
        jPanel4.add(jTextField1, java.awt.BorderLayout.SOUTH);

        jLabel7.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 102, 204));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("Planos en estacion de Calidad");
        jPanel4.add(jLabel7, java.awt.BorderLayout.NORTH);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(7, 0, 7, 0);
        jPanel2.add(jPanel4, gridBagConstraints);

        lblConteo.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        lblConteo.setForeground(new java.awt.Color(51, 51, 51));
        lblConteo.setText("Cantidad de Planos: ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.insets = new java.awt.Insets(6, 0, 6, 0);
        jPanel2.add(lblConteo, gridBagConstraints);

        jButton1.setBackground(new java.awt.Color(255, 153, 0));
        jButton1.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Ver planos terminados");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        jPanel2.add(jButton1, gridBagConstraints);

        jPanel1.add(jPanel2, java.awt.BorderLayout.CENTER);

        jPanel3.setBackground(new java.awt.Color(235, 235, 235));
        jPanel3.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 30, 5));

        jLabel5.setFont(new java.awt.Font("Roboto", 1, 24)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(51, 51, 51));
        jLabel5.setText("Enviar a:");
        jPanel3.add(jLabel5);

        cmbEnviar.setFont(new java.awt.Font("Roboto", 1, 24)); // NOI18N
        cmbEnviar.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccionar", "Maquinados (Retrabajo)", "Tratamiento", "Terminado (Calidad)", "Integracion", "Cortes (Scrap)" }));
        cmbEnviar.setPreferredSize(new java.awt.Dimension(300, 30));
        cmbEnviar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbEnviarActionPerformed(evt);
            }
        });
        jPanel3.add(cmbEnviar);

        btnEnviar.setBackground(new java.awt.Color(0, 102, 204));
        btnEnviar.setFont(new java.awt.Font("Roboto", 1, 24)); // NOI18N
        btnEnviar.setForeground(new java.awt.Color(255, 255, 255));
        btnEnviar.setText("Enviar");
        btnEnviar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEnviarActionPerformed(evt);
            }
        });
        jPanel3.add(btnEnviar);

        jPanel1.add(jPanel3, java.awt.BorderLayout.PAGE_END);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel11MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel11MouseClicked
        dispose();
    }//GEN-LAST:event_jLabel11MouseClicked

    private void jLabel11MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel11MouseEntered
        btnSalir.setBackground(Color.red);
        jLabel11.setForeground(Color.white);
    }//GEN-LAST:event_jLabel11MouseEntered

    private void jLabel11MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel11MouseExited
        btnSalir.setBackground(Color.white);
        jLabel11.setForeground(Color.black);
    }//GEN-LAST:event_jLabel11MouseExited

    private void txtIngresarPlanoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIngresarPlanoActionPerformed
        try {
            Connection con;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            String form = formatear(txtIngresarPlano.getText(), con);
            String sql = "select Proyecto, Plano, Cantidad, Material from planos where Plano like '" + form + "'";
            ResultSet rs = st.executeQuery(sql);
            String plano = null;
            limpiarFormulario();
            while (rs.next()) {
                plano = rs.getString("Plano");
                txtCantidad.setText(rs.getString("Cantidad"));
                txtPlano.setText(plano);
                txtMaterial.setText(rs.getString("Material"));
                txtProyecto.setText(rs.getString("Proyecto"));
            }
            txtIngresarPlano.setText("");
            if (plano == null) {
                btnPdf.setVisible(false);
                int prim = form.indexOf(" ");
                String proyecto = validarPlano(con, form.substring(0, prim));
                if (proyecto != null) {
                    lblAvisoPlano.setVisible(true);
                    txtPlano.setText(form);
                    txtProyecto.setText(proyecto);
                } else {
                    lblAvisoPlano.setVisible(false);
                    JOptionPane.showMessageDialog(this, "El plano que ingresaste no existe", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                lblAvisoPlano.setVisible(false);
                btnPdf.setVisible(true);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error: " + e, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_txtIngresarPlanoActionPerformed

    private void btnPdfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPdfActionPerformed
        try {
            Desktop.getDesktop().open(new File("\\\\192.168.100.40\\03 Project\\04 DISENO\\" + txtProyecto.getText() + "\\" + txtPlano.getText() + ".pdf"));
        } catch (Exception ex) {
            String dir = getDirectorio(txtProyecto.getText()) + "\\" + txtPlano.getText() + ".pdf";
            try {
                Connection con;
                Conexion con1 = new Conexion();
                con = con1.getConnection();
                Statement st = con.createStatement();
                String sql = "select Pdf,Plano from pdfplanos where Plano like '" + txtPlano.getText() + "'";
                ResultSet rs = st.executeQuery(sql);
                byte[] b = null;
                while (rs.next()) {
                    b = rs.getBytes("Pdf");
                }

                InputStream bos = new ByteArrayInputStream(b);
                int tamInput = bos.available();
                byte[] datosPdf = new byte[tamInput];
                bos.read(datosPdf, 0, tamInput);

                OutputStream out = new FileOutputStream(dir);
                out.write(datosPdf);

                out.close();
                bos.close();

                Desktop.getDesktop().open(new File(dir));
            } catch (SQLException | NumberFormatException | IOException e) {
                JOptionPane.showMessageDialog(this, "Error al descargar: " + e, "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_btnPdfActionPerformed

    private void btnEnviarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEnviarActionPerformed
        enviarPlano(txtPlano.getText(), txtProyecto.getText(), true);
        verificarProyectosTerminados(txtProyecto.getText());
        recargarTabla();
    }//GEN-LAST:event_btnEnviarActionPerformed

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        recargarTabla();
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void terminarPlanosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_terminarPlanosActionPerformed
        revisarPlanos rev = new revisarPlanos();
        rev.mostrarMensaje = false;

        for (int i = 0; i < TablaPlan.getSelectedRows().length; i++) {
            int fila = TablaPlan.getSelectedRows()[i];
            enviarPlano(TablaPlan.getValueAt(fila, 1).toString(), TablaPlan.getValueAt(fila, 0).toString(), false);
            verificarProyectosTerminados(TablaPlan.getValueAt(fila, 0).toString());
        }
        recargarTabla();
        JOptionPane.showMessageDialog(null, "Transaccion realizada con exito");
    }//GEN-LAST:event_terminarPlanosActionPerformed

    private void recargarTabla(){
        if (jTextField1.getText().equals("")) {
            verDatos("select * from planos where Estado like 'CALIDAD' order by id desc");
        } else {
            verDatos("select * from planos where Estado like 'CALIDAD' and Proyecto like '" + jTextField1.getText() + "' order by id desc");
        }
    }
    private void jPopupMenu1PopupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_jPopupMenu1PopupMenuWillBecomeVisible
        
    }//GEN-LAST:event_jPopupMenu1PopupMenuWillBecomeVisible

    private void cmbEnviarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbEnviarActionPerformed
        if(cmbEnviar.getSelectedIndex() == 0) {
            terminarPlanos.setEnabled(false);
        } else {
            terminarPlanos.setEnabled(true);
            terminarPlanos.setText("Enviar planos a: " + cmbEnviar.getSelectedItem().toString() + "                          ");
        }
    }//GEN-LAST:event_cmbEnviarActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable TablaPlan;
    private javax.swing.JButton btnEnviar;
    private javax.swing.JButton btnPdf;
    private javax.swing.JPanel btnSalir;
    private javax.swing.JComboBox<String> cmbEnviar;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JLabel lblAvisoPlano;
    private javax.swing.JLabel lblConteo;
    private javax.swing.JScrollPane scrollPlan;
    private javax.swing.JMenuItem terminarPlanos;
    private javax.swing.JTextField txtCantidad;
    private javax.swing.JTextField txtIngresarPlano;
    private javax.swing.JTextField txtMaterial;
    private javax.swing.JTextField txtPlano;
    private javax.swing.JTextField txtProyecto;
    // End of variables declaration//GEN-END:variables
}
