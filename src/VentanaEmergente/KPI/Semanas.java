package VentanaEmergente.KPI;

import Conexiones.Conexion;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.WeekFields;
import java.util.Date;
import java.util.Locale;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.border.MatteBorder;

public class Semanas extends java.awt.Dialog {

    String numEmpleado;
    String depa;
    int totalKPI;
    Stack<String[]> kpis;
    private final int RETRAZADO = 0;
    private final int REALIZADO = 1;
    private final int PENDIENTE = 2;
    private final int INCOMPLETO = 3;
    private final int ACTUAL = 4;
    public int seleccionado = -1;
    
    public final void limpiarPanel() throws ParseException {
        jPanel2.removeAll();
        for (int i = 1; i < 13; i++) {
            JLabel botonMes = new javax.swing.JLabel();
            botonMes.setBackground(new java.awt.Color(255, 255, 255));
            botonMes.setFont(new java.awt.Font("Roboto", 1, 14));
            botonMes.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
            botonMes.setBorder(new MatteBorder(0, 0, 2, 0, new Color(220,220,220)));
            botonMes.setForeground(new java.awt.Color(51, 51, 51));
            SimpleDateFormat sdf = new SimpleDateFormat("MM");
            SimpleDateFormat sdf2 = new SimpleDateFormat("MMMM");
            Date d = sdf.parse(String.valueOf(i));
            botonMes.setText(sdf2.format(d).toUpperCase());
            java.awt.GridBagConstraints gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.gridx = 0;
            gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
            jPanel2.add(botonMes, gridBagConstraints);
        }
        revalidate();
        repaint();
    }
    
    public int obtenerEstado(int semana, int semanaActual) {
        int cont = 0;
        for (int i = 0; i < kpis.size(); i++) {
            if (Integer.parseInt(kpis.get(i)[2]) == semana) {
                cont++;
            }
        }
        if (cont >= totalKPI) {
                return REALIZADO;
        } else if (cont > 0 && cont < totalKPI) {
            return INCOMPLETO;
        } else if (semana < semanaActual) {
            if(cont == 0) {
                return RETRAZADO;
            }
        } else {
            if(semana == semanaActual) {
                return ACTUAL;
            }
            return PENDIENTE;
        }
        return PENDIENTE;
    }
    
    public final void insertarSemanas() {
        try {
            limpiarPanel();
        } catch (ParseException ex) {
            Logger.getLogger(Semanas.class.getName()).log(Level.SEVERE, null, ex);
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date d = new Date();
        String fec[] = sdf.format(d).split("-"); 
        int year = Integer.parseInt(fec[0]);
        lblAno.setText(String.valueOf(year));
        LocalDate semAct = LocalDate.of(Integer.parseInt(fec[0]), Integer.parseInt(fec[1]), Integer.parseInt(fec[2]));
        WeekFields semActual = WeekFields.of(Locale.getDefault());
        int semana = semAct.get(semActual.weekOfWeekBasedYear());
        for (int mes = 1; mes <= 12; mes++) {
            LocalDate inicioMes = LocalDate.of(year, mes, 1);
            LocalDate finMes = inicioMes.withDayOfMonth(inicioMes.lengthOfMonth());

            WeekFields weekFields = WeekFields.of(Locale.getDefault());

            int semanaInicio = inicioMes.get(weekFields.weekOfWeekBasedYear());
            int semanaFin = finMes.get(weekFields.weekOfWeekBasedYear());
            int cont = 1;
            if (semanaFin < semanaInicio) {
                int sem = (52 - semanaInicio) + semanaFin;
                semanaFin = semanaInicio + sem;
            }
            for (int i = semanaInicio; i < semanaFin; i++) {
                int estado = obtenerEstado(i, semana);
                crearBoton(String.valueOf(i), cont, mes, estado);
                cont++;
            }
            crearBoton("", 7, cont,PENDIENTE);
        }
    }
    
    public final void crearBoton(String i, int mes, int col, int opc) {
        JButton boton = new javax.swing.JButton();
        boton.setBackground(new java.awt.Color(255, 255, 255));
        boton.setForeground(new java.awt.Color(51, 51, 51));
        boton.addActionListener((ActionEvent e) -> {
            seleccionado = Integer.parseInt(i);
            dispose();
        });
        switch (opc) {
            case RETRAZADO:
                boton.setBackground(new java.awt.Color(180,0,0));
                boton.setForeground(new java.awt.Color(255,255,255));
                break;
            case REALIZADO:
                boton.setBackground(new java.awt.Color(0,153,0));
                boton.setForeground(new java.awt.Color(255,255,255));
                break;
            case INCOMPLETO:
                boton.setBackground(new java.awt.Color(255,153,0));
                boton.setForeground(new java.awt.Color(255,255,255));
                break;
            case ACTUAL:
                boton.setBackground(new java.awt.Color(0,102,204));
                boton.setForeground(new java.awt.Color(255,255,255));
                break;
            default:
                break;
        }
        if (mes == 7) {
            boton.setBackground(Color.white);
            boton.setBorder(null);
        }
        boton.setFont(new java.awt.Font("Roboto", 1, 12));
        boton.setText(i);
        
        java.awt.GridBagConstraints gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = mes;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        if(mes == 5) {
            gridBagConstraints.gridy = col - 1;
        }
        jPanel2.add(boton, gridBagConstraints);
    }
    
    public final Stack<String[]> obtenerFechas() {
        try {
            Connection con;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            String sql = "select * from kpi where Empleado like '" + numEmpleado + "' and Departamento like '" + depa + "' ORDER BY KPI";
            ResultSet rs = st.executeQuery(sql);
            Stack<String[]> pila = new Stack<>();
            while (rs.next()) {
                String dat[] = new String[3];
                dat[0] = rs.getString("KPI");
                dat[1] = rs.getString("Tipo");
                dat[2] = rs.getString("Fecha");
                String d[] = dat[2].split("-");
                LocalDate fecha = LocalDate.of(Integer.parseInt(d[0]), Integer.parseInt(d[1]), Integer.parseInt(d[2]));
                dat[2] = String.valueOf(fecha.get(WeekFields.of(Locale.getDefault()).weekOfYear()));
                pila.add(dat);
            }
            return pila;
        } catch(SQLException e) {
            JOptionPane.showMessageDialog(this, "Error: " + e, "Error", JOptionPane.ERROR_MESSAGE);
        }
        return null;
    }
    
    public Semanas(java.awt.Frame parent, boolean modal, String numEmpleado, String depa, int totalKPI) {
        super(parent, modal);
        initComponents();
        this.numEmpleado = numEmpleado;
        this.depa = depa;
        this.totalKPI = totalKPI;
        this.kpis = obtenerFechas();
        setLocationRelativeTo(null);
        insertarSemanas();
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        lblAno = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();
        jButton11 = new javax.swing.JButton();
        jButton12 = new javax.swing.JButton();

        setPreferredSize(new java.awt.Dimension(809, 503));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                closeDialog(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(204, 204, 204));

        jLabel1.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 51, 51));
        jLabel1.setText("Año:");
        jPanel1.add(jLabel1);

        lblAno.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        lblAno.setForeground(new java.awt.Color(51, 51, 51));
        lblAno.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblAno.setText("jLabel2");
        jPanel1.add(lblAno);

        add(jPanel1, java.awt.BorderLayout.NORTH);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        java.awt.GridBagLayout jPanel2Layout = new java.awt.GridBagLayout();
        jPanel2Layout.columnWeights = new double[] {1.0};
        jPanel2Layout.rowWeights = new double[] {1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0};
        jPanel2.setLayout(jPanel2Layout);

        jButton1.setBackground(new java.awt.Color(0, 153, 0));
        jButton1.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        jButton1.setForeground(new java.awt.Color(51, 51, 51));
        jButton1.setText("Enero");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel2.add(jButton1, gridBagConstraints);

        jButton2.setBackground(new java.awt.Color(0, 102, 204));
        jButton2.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        jButton2.setForeground(new java.awt.Color(51, 51, 51));
        jButton2.setText("Febrero");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        jPanel2.add(jButton2, gridBagConstraints);

        jButton3.setBackground(new java.awt.Color(255, 255, 255));
        jButton3.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        jButton3.setForeground(new java.awt.Color(51, 51, 51));
        jButton3.setText("Marzo");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        jPanel2.add(jButton3, gridBagConstraints);

        jButton4.setBackground(new java.awt.Color(255, 255, 255));
        jButton4.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        jButton4.setForeground(new java.awt.Color(51, 51, 51));
        jButton4.setText("Abril");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        jPanel2.add(jButton4, gridBagConstraints);

        jButton5.setBackground(new java.awt.Color(255, 255, 255));
        jButton5.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        jButton5.setForeground(new java.awt.Color(51, 51, 51));
        jButton5.setText("Mayo");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        jPanel2.add(jButton5, gridBagConstraints);

        jButton6.setBackground(new java.awt.Color(255, 255, 255));
        jButton6.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        jButton6.setForeground(new java.awt.Color(51, 51, 51));
        jButton6.setText("Junio");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        jPanel2.add(jButton6, gridBagConstraints);

        jButton7.setBackground(new java.awt.Color(255, 255, 255));
        jButton7.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        jButton7.setForeground(new java.awt.Color(51, 51, 51));
        jButton7.setText("Julio");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        jPanel2.add(jButton7, gridBagConstraints);

        jButton8.setBackground(new java.awt.Color(255, 255, 255));
        jButton8.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        jButton8.setForeground(new java.awt.Color(51, 51, 51));
        jButton8.setText("Agosto");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        jPanel2.add(jButton8, gridBagConstraints);

        jButton9.setBackground(new java.awt.Color(255, 255, 255));
        jButton9.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        jButton9.setForeground(new java.awt.Color(51, 51, 51));
        jButton9.setText("Septiembre");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        jPanel2.add(jButton9, gridBagConstraints);

        jButton10.setBackground(new java.awt.Color(255, 255, 255));
        jButton10.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        jButton10.setForeground(new java.awt.Color(51, 51, 51));
        jButton10.setText("Octubre");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        jPanel2.add(jButton10, gridBagConstraints);

        jButton11.setBackground(new java.awt.Color(255, 255, 255));
        jButton11.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        jButton11.setForeground(new java.awt.Color(51, 51, 51));
        jButton11.setText("Noviembre");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        jPanel2.add(jButton11, gridBagConstraints);

        jButton12.setBackground(new java.awt.Color(255, 255, 255));
        jButton12.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        jButton12.setForeground(new java.awt.Color(51, 51, 51));
        jButton12.setText("Diciembre");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        jPanel2.add(jButton12, gridBagConstraints);

        add(jPanel2, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void closeDialog(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_closeDialog
        setVisible(false);
        dispose();
    }//GEN-LAST:event_closeDialog

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Semanas dialog = new Semanas(new java.awt.Frame(), true,"","",0);
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
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel lblAno;
    // End of variables declaration//GEN-END:variables
}
