package pruebas;

import Conexiones.Conexion;
import grafica.PieChartPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import org.jdesktop.swingx.JXTable;
import org.knowm.xchart.PieChart;

import java.text.SimpleDateFormat;
    import java.util.Date;

public class Live extends javax.swing.JInternalFrame {

    public String numEmpleado;

    public final void limpiarTabla(JXTable tabla) {
        DefaultTableModel miModelo = (DefaultTableModel) tabla.getModel();
        for (int i = tabla.getRowCount() - 1; i >= 0; i--) {
            miModelo.removeRow(i);
        }
        tabla.setRowHeight(20);
        tabla.setFont(new Font("Roboto", Font.PLAIN, 12));
        tabla.setColumnControlVisible(false);
        tabla.setShowGrid(false);
        tabla.setIntercellSpacing(new java.awt.Dimension(0, 1));
        tabla.setFillsViewportHeight(true);
        tabla.setBorder(BorderFactory.createEmptyBorder());
        tabla.getTableHeader().setFont(new Font("Roboto", Font.BOLD, 14));
        tabla.getTableHeader().setPreferredSize(new java.awt.Dimension(0, 25));
        tabla.getTableHeader().setReorderingAllowed(false);
        tabla.setSelectionBackground(new Color(0, 112, 192));
        tabla.setSelectionForeground(Color.WHITE);
        DefaultTableCellRenderer centro = new DefaultTableCellRenderer();
        centro.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < tabla.getColumnCount(); i++) {
            tabla.getColumnModel().getColumn(i).setCellRenderer(centro);
        }
    }

    public final void verDatos() {
        try {
            Connection con = new Conexion().getConnection();
            Statement st = con.createStatement();

            // Consulta filtrando únicamente los proyectos que estén en proceso (pr.Estatus LIKE '%Proceso%')
            String sql = "SELECT p.plano, p.proyecto, " +
                         "(SELECT MIN(t.FechaInicio) FROM tiemposareas t WHERE t.Plano = p.plano) AS fechaRequerimiento, " +
                         "COALESCE(e.nombre, p.estado) AS estacionNombre, pr.FechaEntrega " +
                         "FROM planos p " +
                         "LEFT JOIN proyectos pr ON p.proyecto = pr.Proyecto " +
                         "LEFT JOIN estados e ON p.estado = e.id " +
                         "WHERE pr.Estatus LIKE '%Proceso%' " +
                         "ORDER BY p.id DESC";

            ResultSet rs = st.executeQuery(sql);
            DefaultTableModel miModelo = (DefaultTableModel) tabla1.getModel();

            SimpleDateFormat formato1 = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat formato2 = new SimpleDateFormat("dd/MM/yyyy");

            // Normalizar fecha actual a medianoche para cálculo exacto de días
            java.util.Calendar calActual = java.util.Calendar.getInstance();
            calActual.set(java.util.Calendar.HOUR_OF_DAY, 0);
            calActual.set(java.util.Calendar.MINUTE, 0);
            calActual.set(java.util.Calendar.SECOND, 0);
            calActual.set(java.util.Calendar.MILLISECOND, 0);
            long millisActual = calActual.getTimeInMillis();

            while (rs.next()) {
                String proyecto = rs.getString("proyecto");

                // 1. Fecha de requerimiento
                String fechaReqStr = "";
                java.sql.Date fReq = rs.getDate("fechaRequerimiento");
                if (fReq != null) {
                    fechaReqStr = formato2.format(fReq);
                }

                // 2. Estación (nombre del estado/departamento)
                String estacion = rs.getString("estacionNombre");
                if (estacion == null || estacion.isEmpty()) {
                    estacion = "-";
                }

                // 3. Fecha de entrega y cálculo de días restantes
                String fechaEntregaStr = "";
                long diasRestantes = 0;
                boolean calcularDias = false;

                String fechaEntregaRaw = rs.getString("FechaEntrega");
                if (fechaEntregaRaw != null && !fechaEntregaRaw.isEmpty()) {
                    Date fEntrega = null;
                    try {
                        fEntrega = formato1.parse(fechaEntregaRaw);
                    } catch (Exception e1) {
                        try {
                            fEntrega = formato2.parse(fechaEntregaRaw);
                        } catch (Exception e2) {
                            try {
                                SimpleDateFormat formatoTimestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                fEntrega = formatoTimestamp.parse(fechaEntregaRaw);
                            } catch (Exception e3) {
                                fEntrega = null;
                            }
                        }
                    }

                    if (fEntrega != null) {
                        fechaEntregaStr = formato2.format(fEntrega);
                        calcularDias = true;

                        // Normalizar fecha de entrega a medianoche
                        java.util.Calendar calEntrega = java.util.Calendar.getInstance();
                        calEntrega.setTime(fEntrega);
                        calEntrega.set(java.util.Calendar.HOUR_OF_DAY, 0);
                        calEntrega.set(java.util.Calendar.MINUTE, 0);
                        calEntrega.set(java.util.Calendar.SECOND, 0);
                        calEntrega.set(java.util.Calendar.MILLISECOND, 0);
                        long millisEntrega = calEntrega.getTimeInMillis();

                        // Cálculo de días restantes exactos
                        long diferenciaMillis = millisEntrega - millisActual;
                        diasRestantes = diferenciaMillis / (1000 * 60 * 60 * 24);
                    } else {
                        fechaEntregaStr = fechaEntregaRaw; 
                    }
                }

                // ORDEN EXACTO DE LAS 5 COLUMNAS:
                // [0] Proyecto, [1] Fecha de requerimiento, [2] Estación, [3] Fecha de entrega, [4] Dias restantes
                Object[] fila = {
                    proyecto,
                    fechaReqStr,
                    estacion,
                    fechaEntregaStr,
                    calcularDias ? String.valueOf(diasRestantes) : "N/D"
                };
                miModelo.addRow(fila);
            }

            rs.close();
            st.close();
            con.close();

            // Aplicar formato de color rojo para días negativos en la columna 4
            aplicarFormatoDiasRestantes();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al ver datos en tabla1: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error de formato en tabla1: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public final void aplicarFormatoDiasRestantes() {
        // La columna de días restantes ahora es la índice 4 (última columna)
        tabla1.getColumnModel().getColumn(4).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public java.awt.Component getTableCellRendererComponent(javax.swing.JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                java.awt.Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                setHorizontalAlignment(SwingConstants.CENTER);

                if (value != null) {
                    try {
                        int dias = Integer.parseInt(value.toString());
                        if (dias < 0) {
                            c.setForeground(Color.RED); // Texto rojo si son negativos (retraso)
                            setFont(new Font("Roboto", Font.BOLD, 12));
                        } else {
                            c.setForeground(isSelected ? Color.WHITE : Color.BLACK);
                            setFont(new Font("Roboto", Font.PLAIN, 12));
                        }
                    } catch (NumberFormatException e) {
                        c.setForeground(Color.BLACK);
                    }
                }
                return c;
            }
        });
    }

    public final void agregarBubbleChart() {
        PieChartPanel grafica = new PieChartPanel();
        grafica.agregarRebanada("Retrazo: 15", 30, new Color(255, 39, 39));
        grafica.agregarRebanada("A tiempo: 60", 70, new Color(42, 167, 63));
        grafica.agregarRebanada("Activo: 60", 70, new Color(177, 195, 41));
        jPanel7.add(grafica);
        jPanel7.revalidate();
        jPanel7.repaint();
    }

    public Live(String numEmpleado) {
        initComponents(); 
        this.numEmpleado = numEmpleado;
        limpiarTabla(tabla1);
        limpiarTabla(tabla2);
        verDatos();
        agregarBubbleChart();
        configurarRadioBotones();
        ((javax.swing.plaf.basic.BasicInternalFrameUI) this.getUI()).setNorthPane(null);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jPanel13 = new javax.swing.JPanel();
        btnSalir = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabla1 = new org.jdesktop.swingx.JXTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        tabla2 = new org.jdesktop.swingx.JXTable();
        jPanel5 = new javax.swing.JPanel();
        lblDepto = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        rBtnDiseno = new javax.swing.JRadioButton();
        rBtnCorte = new javax.swing.JRadioButton();
        rBtnMaquinado = new javax.swing.JRadioButton();
        rBtnFresadora = new javax.swing.JRadioButton();
        rBtnTorno = new javax.swing.JRadioButton();
        rBtnRectificado = new javax.swing.JRadioButton();
        rBtnCalidad = new javax.swing.JRadioButton();
        rBtnEnsamble = new javax.swing.JRadioButton();
        rBtnEstampado = new javax.swing.JRadioButton();
        jRadioButton10 = new javax.swing.JRadioButton();
        jPanel8 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();

        setBorder(null);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new java.awt.BorderLayout());

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new java.awt.BorderLayout());

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jLabel15.setFont(new java.awt.Font("Lexend", 1, 14)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(0, 102, 204));
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel15.setText("Dashboard");
        jPanel3.add(jLabel15);

        jPanel2.add(jPanel3, java.awt.BorderLayout.CENTER);

        jPanel13.setBackground(new java.awt.Color(255, 255, 255));

        btnSalir.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
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
        java.awt.GridBagLayout jPanel4Layout = new java.awt.GridBagLayout();
        jPanel4Layout.columnWeights = new double[] {1.0, 1.0};
        jPanel4Layout.rowWeights = new double[] {0.0, 0.0, 1.0};
        jPanel4.setLayout(jPanel4Layout);

        jLabel4.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 102, 204));
        jLabel4.setText("Activos");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 10, 0);
        jPanel4.add(jLabel4, gridBagConstraints);

        jScrollPane2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));

        tabla1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Proyecto", "Fecha de requerimiento", "Estación", "Fecha de entrega", "Dias restantes"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(tabla1);
        if (tabla1.getColumnModel().getColumnCount() > 0) {
            tabla1.getColumnModel().getColumn(4).setHeaderValue("Dias restantes");
        }

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 5);
        jPanel4.add(jScrollPane2, gridBagConstraints);

        jScrollPane3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));

        tabla2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Plano", "Fecha", "Inicio", "Fecha", "Fin", "Tiempo"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane3.setViewportView(tabla2);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = -100;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 5);
        jPanel4.add(jScrollPane3, gridBagConstraints);

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setLayout(new java.awt.BorderLayout());

        lblDepto.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        lblDepto.setForeground(new java.awt.Color(0, 102, 204));
        lblDepto.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblDepto.setText("Departamento");
        jPanel5.add(lblDepto, java.awt.BorderLayout.NORTH);

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));

        buttonGroup1.add(rBtnDiseno);
        rBtnDiseno.setToolTipText("Diseño");
        jPanel6.add(rBtnDiseno);

        buttonGroup1.add(rBtnCorte);
        rBtnCorte.setToolTipText("Corte");
        jPanel6.add(rBtnCorte);

        buttonGroup1.add(rBtnMaquinado);
        rBtnMaquinado.setToolTipText("Maquinado CNC");
        jPanel6.add(rBtnMaquinado);

        buttonGroup1.add(rBtnFresadora);
        rBtnFresadora.setToolTipText("Fresadora");
        jPanel6.add(rBtnFresadora);

        buttonGroup1.add(rBtnTorno);
        rBtnTorno.setToolTipText("Torno");
        jPanel6.add(rBtnTorno);

        buttonGroup1.add(rBtnRectificado);
        rBtnRectificado.setToolTipText("Rectificado");
        jPanel6.add(rBtnRectificado);

        buttonGroup1.add(rBtnCalidad);
        rBtnCalidad.setToolTipText("Calidad");
        jPanel6.add(rBtnCalidad);

        buttonGroup1.add(rBtnEnsamble);
        rBtnEnsamble.setToolTipText("Ensamble");
        jPanel6.add(rBtnEnsamble);

        buttonGroup1.add(rBtnEstampado);
        rBtnEstampado.setToolTipText("Estampado");
        jPanel6.add(rBtnEstampado);

        buttonGroup1.add(jRadioButton10);
        jRadioButton10.setToolTipText("");
        jPanel6.add(jRadioButton10);

        jPanel5.add(jPanel6, java.awt.BorderLayout.CENTER);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 1;
        jPanel4.add(jPanel5, gridBagConstraints);

        jPanel1.add(jPanel4, java.awt.BorderLayout.CENTER);

        jPanel8.setLayout(new java.awt.GridLayout(1, 2));

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));
        jPanel8.add(jPanel9);

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setPreferredSize(new java.awt.Dimension(100, 350));
        jPanel7.setLayout(new java.awt.BorderLayout());
        jPanel8.add(jPanel7);

        jPanel1.add(jPanel8, java.awt.BorderLayout.PAGE_END);

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

    public final void configurarRadioBotones() {
        rBtnDiseno.addActionListener(e -> {
            lblDepto.setText("Diseño");
            cargarDatosTabla2("Diseno", 0);
            actualizarGraficaPastel("Diseno", 0);
        });

        rBtnCorte.addActionListener(e -> {
            lblDepto.setText("Corte");
            cargarDatosTabla2("tiemposareas", 1);
            actualizarGraficaPastel("tiemposareas", 1);
        });

        rBtnMaquinado.addActionListener(e -> {
            lblDepto.setText("Maquinado CNC");
            cargarDatosTabla2("tiemposareas", 2);
            actualizarGraficaPastel("tiemposareas", 2);
        });

        rBtnFresadora.addActionListener(e -> {
            lblDepto.setText("Fresadora");
            cargarDatosTabla2("tiemposareas", 3);
            actualizarGraficaPastel("tiemposareas", 3);
        });

        rBtnTorno.addActionListener(e -> {
            lblDepto.setText("Torno");
            cargarDatosTabla2("tiemposareas", 4);
            actualizarGraficaPastel("tiemposareas", 4);
        });

        rBtnRectificado.addActionListener(e -> {
            lblDepto.setText("Rectificado");
            cargarDatosTabla2("tiemposareas", 5);
            actualizarGraficaPastel("tiemposareas", 5);
        });

        rBtnCalidad.addActionListener(e -> {
            lblDepto.setText("Calidad");
            cargarDatosTabla2("tiemposareas", 6); 
            actualizarGraficaPastel("tiemposareas", 6);
        });

        rBtnEnsamble.addActionListener(e -> {
            lblDepto.setText("Ensamble");
            cargarDatosTabla2("tiemposareas", 7);
            actualizarGraficaPastel("tiemposareas", 7);
        });

        rBtnEstampado.addActionListener(e -> {
            lblDepto.setText("Estampado");
            cargarDatosTabla2("tiemposareas", 8);
            actualizarGraficaPastel("tiemposareas", 8);
        });
    }
    
    public final void cargarDatosTabla2(String origen, int idEstado) {
        limpiarTabla(tabla2);
        DefaultTableModel modelo = (DefaultTableModel) tabla2.getModel();
        SimpleDateFormat formatoBD = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat formatoDestino = new SimpleDateFormat("dd/MM/yyyy");

        try {
            Connection con = new Conexion().getConnection();
            Statement st = con.createStatement();
            String sql = "";

            if (origen.equals("Diseno")) {
                // Consulta para la tabla ordentrabajo incluyendo el plano
                sql = "SELECT plano, inicio, termino, tiempo FROM ordentrabajo WHERE departamento LIKE '%diseno%'";
            } else {
                // Consulta para la tabla tiemposareas incluyendo el plano
                sql = "SELECT Plano, FechaInicio, HoraInicio, FechaFinal, HoraFinal, Cronometro FROM tiemposareas WHERE idEstado = " + idEstado;
            }

            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                String planoStr = "";
                try {
                    planoStr = rs.getString("plano");
                } catch (Exception ex) {
                    try {
                        planoStr = rs.getString("Plano");
                    } catch (Exception ex2) {
                        planoStr = "";
                    }
                }
                if (planoStr == null) planoStr = "";

                String fechaInicioStr = "";
                String horaInicioStr = "";
                String fechaFinStr = "";
                String horaFinStr = "";
                String tiempoStr = "";

                if (origen.equals("Diseno")) {
                    String inicioCompleto = rs.getString("inicio");
                    String terminoCompleto = rs.getString("termino");
                    tiempoStr = rs.getString("tiempo");

                    if (inicioCompleto != null && !inicioCompleto.isEmpty()) {
                        String[] partes = inicioCompleto.split(" ");
                        Date fechaParsed = formatoBD.parse(partes[0]);
                        fechaInicioStr = formatoDestino.format(fechaParsed);
                        horaInicioStr = partes.length > 1 ? partes[1] : "";
                    }

                    if (terminoCompleto != null && !terminoCompleto.isEmpty()) {
                        String[] partes = terminoCompleto.split(" ");
                        Date fechaParsed = formatoBD.parse(partes[0]);
                        fechaFinStr = formatoDestino.format(fechaParsed);
                        horaFinStr = partes.length > 1 ? partes[1] : "";
                    }
                } else {
                    Date fInicio = rs.getDate("FechaInicio");
                    if (fInicio != null) {
                        fechaInicioStr = formatoDestino.format(fInicio);
                    }
                    horaInicioStr = rs.getString("HoraInicio");
                    if (horaInicioStr == null) horaInicioStr = "";

                    Date fFinal = rs.getDate("FechaFinal");
                    if (fFinal != null) {
                        fechaFinStr = formatoDestino.format(fFinal);
                    }
                    horaFinStr = rs.getString("HoraFinal");
                    if (horaFinStr == null) horaFinStr = "";

                    tiempoStr = rs.getString("Cronometro");
                    if (tiempoStr == null) {
                        tiempoStr = "00:00:00";
                    }
                }

                // ORDEN DE LAS 6 COLUMNAS:
                // [0] Plano, [1] Fecha Inicio, [2] Hora Inicio, [3] Fecha Fin, [4] Hora Fin, [5] Tiempo
                Object[] fila = {
                    planoStr,
                    fechaInicioStr,
                    horaInicioStr,
                    fechaFinStr,
                    horaFinStr,
                    tiempoStr
                };
                modelo.addRow(fila);
            }
            rs.close();
            st.close();
            con.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar datos en tabla2: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public final void actualizarGraficaPastel(String origen, int idEstado) {
        jPanel7.removeAll(); // Limpiar la gráfica anterior
        PieChartPanel grafica = new PieChartPanel();

        int terminadosHoy = 0;
        int activosHoy = 0;

        try {
            Connection con = new Conexion().getConnection();
            Statement st = con.createStatement();
            String sql = "";

            if (origen.equals("Diseno")) {
                // Consultas para Diseño (tabla ordentrabajo) para el día de hoy
                // Contar activos hoy
                String sqlActivos = "SELECT COUNT(*) FROM ordentrabajo WHERE departamento LIKE '%diseno%' AND DATE(inicio) = CURDATE() AND (termino IS NULL)";
                ResultSet rs1 = st.executeQuery(sqlActivos);
                if (rs1.next()) activosHoy = rs1.getInt(1);
                rs1.close();

                // Contar terminados hoy
                String sqlTerminados = "SELECT COUNT(*) FROM ordentrabajo WHERE departamento LIKE '%diseno%' AND DATE(termino) = CURDATE()";
                ResultSet rs2 = st.executeQuery(sqlTerminados);
                if (rs2.next()) terminadosHoy = rs2.getInt(1);
                rs2.close();

            } else {
                // Consultas para los demás departamentos (tabla tiemposareas) para el día de hoy
                String sqlActivos = "SELECT COUNT(*) FROM tiemposareas WHERE idEstado = " + idEstado + 
                                    " AND FechaInicio = CURDATE() AND (FechaFinal IS NULL OR HoraFinal IS NULL)";
                ResultSet rs1 = st.executeQuery(sqlActivos);
                if (rs1.next()) activosHoy = rs1.getInt(1);
                rs1.close();

                String sqlTerminados = "SELECT COUNT(*) FROM tiemposareas WHERE idEstado = " + idEstado + 
                                       " AND FechaFinal = CURDATE()";
                ResultSet rs2 = st.executeQuery(sqlTerminados);
                if (rs2.next()) terminadosHoy = rs2.getInt(1);
                rs2.close();
            }

            st.close();
            con.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al calcular datos para la gráfica: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

        // Agregar las rebanadas a la gráfica con los valores reales de hoy
        if (terminadosHoy >  0) {
            grafica.agregarRebanada("Terminados: " + terminadosHoy, terminadosHoy, new Color(42, 167, 63)); // Verde
        }
        if (activosHoy > 0) {
            grafica.agregarRebanada("Activos: " + activosHoy, activosHoy, new Color(177, 195, 41)); // Amarillo/Verde oliva
        }

        // Si no hay registros hoy, puedes poner una rebanada por defecto en 0 o vacía para que no truene
        if (terminadosHoy == 0 && activosHoy == 0) {
            grafica.agregarRebanada("Sin actividad hoy", 1, new Color(200, 200, 200)); // Gris
        }

        jPanel7.add(grafica, BorderLayout.CENTER);
        jPanel7.revalidate();
        jPanel7.repaint();
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel btnSalir;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JRadioButton jRadioButton10;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lblDepto;
    private javax.swing.JRadioButton rBtnCalidad;
    private javax.swing.JRadioButton rBtnCorte;
    private javax.swing.JRadioButton rBtnDiseno;
    private javax.swing.JRadioButton rBtnEnsamble;
    private javax.swing.JRadioButton rBtnEstampado;
    private javax.swing.JRadioButton rBtnFresadora;
    private javax.swing.JRadioButton rBtnMaquinado;
    private javax.swing.JRadioButton rBtnRectificado;
    private javax.swing.JRadioButton rBtnTorno;
    private org.jdesktop.swingx.JXTable tabla1;
    private org.jdesktop.swingx.JXTable tabla2;
    // End of variables declaration//GEN-END:variables
}
