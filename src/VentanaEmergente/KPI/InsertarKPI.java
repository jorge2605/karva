package VentanaEmergente.KPI;

import Conexiones.Conexion;
import java.awt.GridBagConstraints;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.Date;
import java.util.Locale;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class InsertarKPI extends java.awt.Dialog {

    public Stack<JLabel> labels;
    public Stack<JTextField> txt;
    public int dia = 1;
    public Date fecha;
    public String numEmpleado;
    public String depa;

    public final void agregarLabel() {
        pnlKPI.removeAll();
        //-------------------Label KPI---------------------
        jLabel10 = new javax.swing.JLabel();
        jLabel10.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(51, 51, 51));
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("KPI");
        jLabel10.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(204, 204, 204)));
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.insets = new java.awt.Insets(0, 40, 0, 40);
        pnlKPI.add(jLabel10, gridBagConstraints);

        //------------------Label insertar-------------------
        jLabel11 = new javax.swing.JLabel();
        jLabel11.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(51, 51, 51));
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("Insertar valor");
        jLabel11.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(204, 204, 204)));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.insets = new java.awt.Insets(0, 40, 0, 40);
        pnlKPI.add(jLabel11, gridBagConstraints);

        txt = new Stack<>();
        for (int i = 0; i < labels.size(); i++) {
            gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.gridx = 0;
            gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
            gridBagConstraints.insets = new java.awt.Insets(14, 40, 14, 40);
            pnlKPI.add(labels.get(i), gridBagConstraints);

            JTextField text = new javax.swing.JTextField();
            txt.add(text);
            txt.get(i).setBackground(new java.awt.Color(255, 255, 255));
            txt.get(i).setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
            txt.get(i).setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(204, 204, 204)));
            gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.gridx = 2;
            gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
            gridBagConstraints.insets = new java.awt.Insets(0, 40, 0, 40);
            pnlKPI.add(txt.get(i), gridBagConstraints);
        }

        //------------------Label final----------------------------
        jLabel18 = new javax.swing.JLabel();
        jLabel18.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(0, 102, 204));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(14, 40, 14, 40);
        pnlKPI.add(jLabel18, gridBagConstraints);
    }

    public final void agregarFecha(Date d) {
        SimpleDateFormat s2 = new SimpleDateFormat("MMMM");
        SimpleDateFormat s3 = new SimpleDateFormat("dd-MM-yyyy");
        fecha = d;
        String fechas[] = s3.format(d).split("-");
        lblAno.setText(fechas[2]);
        lblMes.setText(s2.format(d));
        dia = Integer.parseInt(fechas[0]);
        LocalDate inicioMes = LocalDate.of(Integer.parseInt(fechas[2]), Integer.parseInt(fechas[1]), Integer.parseInt(fechas[0]));
        LocalDate lunesSemana = inicioMes.with(java.time.DayOfWeek.MONDAY);
        WeekFields weekFields = WeekFields.of(Locale.getDefault());
        lblSemana.setText(lunesSemana.get(weekFields.weekOfWeekBasedYear()) + "");
    }

    public LocalDate getFecha(int año, int numSemana) {
        WeekFields weekFields = WeekFields.of(java.time.DayOfWeek.MONDAY, 1);
        TemporalField weekOfYear = weekFields.weekOfYear();
        LocalDate fec = LocalDate.of(año, 1, 1)
                .with(weekOfYear, numSemana)
                .with(weekFields.dayOfWeek(), 1);
        return fec;
    }
    
    public boolean validar() {
        int cont = 0;
        for (int i = 0; i < labels.size(); i++) {
            if (txt.get(i).getText().equals("")) {
                cont++;
            }
        }
        return cont != labels.size();
    }

    public InsertarKPI(java.awt.Frame parent, boolean modal, Stack<JLabel> labels, String numEmpleado, String depa) {
        super(parent, modal);
        initComponents();
        this.labels = labels;
        this.numEmpleado = numEmpleado;
        this.depa = depa;
        agregarLabel();
        agregarFecha(new Date());
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        lblIngreso = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        lblAno = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        lblMes = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        lblSemana = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        pnlKPI = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jPanel8 = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();

        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                closeDialog(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new java.awt.BorderLayout(10, 10));

        jLabel1.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 102, 204));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Insertar KPI");
        jPanel1.add(jLabel1, java.awt.BorderLayout.PAGE_START);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new java.awt.BorderLayout(0, 10));

        jPanel3.setBackground(new java.awt.Color(222, 222, 222));
        java.awt.GridBagLayout jPanel3Layout = new java.awt.GridBagLayout();
        jPanel3Layout.rowWeights = new double[] {1.0, 1.0, 1.0};
        jPanel3.setLayout(jPanel3Layout);

        jPanel4.setBackground(new java.awt.Color(222, 222, 222));

        jLabel2.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(51, 51, 51));
        jLabel2.setText("Tipo de ingreso:");
        jPanel4.add(jLabel2);

        lblIngreso.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        lblIngreso.setForeground(new java.awt.Color(0, 102, 204));
        lblIngreso.setText("Semanal");
        jPanel4.add(lblIngreso);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 4;
        jPanel3.add(jPanel4, gridBagConstraints);

        jPanel5.setBackground(new java.awt.Color(222, 222, 222));

        jLabel3.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(51, 51, 51));
        jLabel3.setText("Año en curso:");
        jPanel5.add(jLabel3);

        lblAno.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        lblAno.setForeground(new java.awt.Color(0, 102, 204));
        jPanel5.add(lblAno);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(7, 15, 7, 15);
        jPanel3.add(jPanel5, gridBagConstraints);

        jPanel6.setBackground(new java.awt.Color(222, 222, 222));

        jLabel6.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(51, 51, 51));
        jLabel6.setText("Mes en curso:");
        jPanel6.add(jLabel6);

        lblMes.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        lblMes.setForeground(new java.awt.Color(0, 102, 204));
        jPanel6.add(lblMes);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(7, 15, 7, 15);
        jPanel3.add(jPanel6, gridBagConstraints);

        jPanel7.setBackground(new java.awt.Color(222, 222, 222));

        jLabel8.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(51, 51, 51));
        jLabel8.setText("Semana en curso:");
        jPanel7.add(jLabel8);

        lblSemana.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        lblSemana.setForeground(new java.awt.Color(0, 102, 204));
        jPanel7.add(lblSemana);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(7, 15, 7, 15);
        jPanel3.add(jPanel7, gridBagConstraints);

        jButton1.setBackground(new java.awt.Color(153, 204, 255));
        jButton1.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Cambiar Fecha");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        jPanel3.add(jButton1, gridBagConstraints);

        jButton3.setBackground(new java.awt.Color(255, 102, 0));
        jButton3.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setText("Ver fechas");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        jPanel3.add(jButton3, gridBagConstraints);

        jPanel2.add(jPanel3, java.awt.BorderLayout.PAGE_START);

        pnlKPI.setBackground(new java.awt.Color(255, 255, 255));
        java.awt.GridBagLayout pnlKPILayout = new java.awt.GridBagLayout();
        pnlKPILayout.columnWeights = new double[] {1.0, 1.0, 1.0};
        pnlKPILayout.rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0};
        pnlKPI.setLayout(pnlKPILayout);

        jLabel10.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(51, 51, 51));
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("KPI");
        jLabel10.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(204, 204, 204)));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.insets = new java.awt.Insets(0, 40, 0, 40);
        pnlKPI.add(jLabel10, gridBagConstraints);

        jLabel11.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(51, 51, 51));
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("Insertar valor");
        jLabel11.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(204, 204, 204)));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.insets = new java.awt.Insets(0, 40, 0, 40);
        pnlKPI.add(jLabel11, gridBagConstraints);

        jLabel17.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(0, 102, 204));
        jLabel17.setText("<html> <p style='width: 500px;'>Requisiciones atrasadas (requisiciones con mas de 3 dias sin PO colocada) (semanalmente)</p>");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(14, 40, 14, 40);
        pnlKPI.add(jLabel17, gridBagConstraints);

        jLabel19.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(0, 102, 204));
        jLabel19.setText("<html> <p style='width: 500px;'>Requisiciones atrasadas (requisiciones con mas de 3 dias sin PO colocada) (semanalmente)</p>");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(14, 40, 14, 40);
        pnlKPI.add(jLabel19, gridBagConstraints);

        jLabel20.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(0, 102, 204));
        jLabel20.setText("<html> <p style='width: 500px;'>Requisiciones atrasadas (requisiciones con mas de 3 dias sin PO colocada) (semanalmente)</p>");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(14, 40, 14, 40);
        pnlKPI.add(jLabel20, gridBagConstraints);

        jLabel18.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(0, 102, 204));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(14, 40, 14, 40);
        pnlKPI.add(jLabel18, gridBagConstraints);

        jTextField1.setBackground(new java.awt.Color(255, 255, 255));
        jTextField1.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jTextField1.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(204, 204, 204)));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 40, 0, 40);
        pnlKPI.add(jTextField1, gridBagConstraints);

        jTextField2.setBackground(new java.awt.Color(255, 255, 255));
        jTextField2.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jTextField2.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(204, 204, 204)));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 40, 0, 40);
        pnlKPI.add(jTextField2, gridBagConstraints);

        jTextField3.setBackground(new java.awt.Color(255, 255, 255));
        jTextField3.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jTextField3.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(204, 204, 204)));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 40, 0, 40);
        pnlKPI.add(jTextField3, gridBagConstraints);

        jPanel2.add(pnlKPI, java.awt.BorderLayout.CENTER);

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));

        jButton2.setBackground(new java.awt.Color(0, 102, 204));
        jButton2.setFont(new java.awt.Font("Roboto", 1, 24)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("Guardar");
        jButton2.setPreferredSize(new java.awt.Dimension(150, 30));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel8.add(jButton2);

        jPanel2.add(jPanel8, java.awt.BorderLayout.PAGE_END);

        jPanel1.add(jPanel2, java.awt.BorderLayout.CENTER);

        add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void closeDialog(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_closeDialog
        setVisible(false);
        dispose();
    }//GEN-LAST:event_closeDialog

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        try {
            JFrame f = (JFrame) JOptionPane.getFrameForComponent(this);
            ElegirCalendario el = new ElegirCalendario(f, true);
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MMMM-yyyy");
            Date date;
            date = sdf.parse(dia + "-" + lblMes.getText() + "-" + lblAno.getText());
            el.jCalendar1.setDate(date);
            Date d = el.getFecha();
            if (d.after(new Date())) {
                JOptionPane.showMessageDialog(this, "No puedes seleccionar una fecha mayor al dia de hoy", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                agregarFecha(d);
            }
        } catch (ParseException ex) {
            Logger.getLogger(InsertarKPI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        try {
            Connection con;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            String sql = "insert into kpi (Departamento, KPI, Valor, Tipo, Empleado, Fecha) values(?,?,?,?,?,?)";
            PreparedStatement pst = con.prepareStatement(sql);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            int n = 0;
            if (!validar()) {
                JOptionPane.showMessageDialog(this, "No ingresaste datos", "Advertencia", JOptionPane.WARNING_MESSAGE);
            } else {
                for (int i = 0; i < txt.size(); i++) {
                    if(Integer.parseInt(txt.get(i).getText()) > 100) {
                        JOptionPane.showMessageDialog(this, "No puedes ingresar el porcentaje arriba de 100", "Error", JOptionPane.ERROR_MESSAGE);
                    } else {
                        if (!txt.get(i).getText().equals("")) {
                            pst.setString(1, depa);
                            pst.setString(2, labels.get(i).getText());
                            pst.setString(3, txt.get(i).getText());
                            pst.setString(4, lblIngreso.getText());
                            pst.setString(5, numEmpleado);
                            pst.setString(6, sdf.format(fecha));
                            n += pst.executeUpdate();
                        }
                    }
                }
                if (n > 0) {
                    JOptionPane.showMessageDialog(this, "Datos Guardados");
                    dispose();
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error: " + e, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        JFrame f = (JFrame) JOptionPane.getFrameForComponent(this);
        Semanas sem = new Semanas(f, true, numEmpleado, depa, labels.size());
        sem.setVisible(true);
        if (sem.seleccionado != -1) {
            Date d = Date.from(getFecha(Integer.parseInt(lblAno.getText()), sem.seleccionado).atStartOfDay(ZoneId.systemDefault()).toInstant());
            agregarFecha(d);
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                InsertarKPI dialog = new InsertarKPI(new java.awt.Frame(), true, null,"","");
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
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JLabel lblAno;
    public javax.swing.JLabel lblIngreso;
    private javax.swing.JLabel lblMes;
    private javax.swing.JLabel lblSemana;
    private javax.swing.JPanel pnlKPI;
    // End of variables declaration//GEN-END:variables
}
