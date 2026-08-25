package pruebas;

import Conexiones.Conexion;
import VentanaEmergente.htpp.Agregar;
import VentanaEmergente.htpp.agregarIntegracion;
import VentanaEmergente.htpp.agregarMaquinados;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public final class HTPP extends javax.swing.JInternalFrame implements ActionListener {

    String numEmpleado;
    Agregar ag;
    agregarMaquinados addMaq;
    agregarIntegracion adIn;
    int tabla = 1;

    public void setFecha() {
        int numeroSemana = Integer.parseInt(cmbSemanas.getSelectedItem().toString());
        int anio = Integer.parseInt(lblYear.getText());

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, anio);
        calendar.set(Calendar.WEEK_OF_YEAR, numeroSemana);

        int primerDiaSemana = Calendar.MONDAY; // Puedes cambiarlo al día que necesites
        calendar.set(Calendar.DAY_OF_WEEK, primerDiaSemana);

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        for (int i = 0; i < 7; i++) {
            switch (i) {
                case 0:
                    lunes.setText(sdf.format(calendar.getTime()));
                    break;
                case 1:
                    martes.setText(sdf.format(calendar.getTime()));
                    break;
                case 2:
                    miercoles.setText(sdf.format(calendar.getTime()));
                    break;
                case 3:
                    jueves.setText(sdf.format(calendar.getTime()));
                    break;
                case 4:
                    viernes.setText(sdf.format(calendar.getTime()));
                    break;
                case 5:
                    sabado.setText(sdf.format(calendar.getTime()));
                    break;
                case 6:
                    domingo.setText(sdf.format(calendar.getTime()));
                    break;
            }
            calendar.add(Calendar.DAY_OF_WEEK, 1);
        }
    }

    public void enchularTabla(JTable tabla, JScrollPane scrol) {
        tabla.getTableHeader().setFont(new Font("Roboto", Font.BOLD, 14));
        tabla.getTableHeader().setOpaque(false);
        tabla.getTableHeader().setBackground(new Color(0, 78, 171));
        tabla.getTableHeader().setForeground(Color.white);
        tabla.setRowHeight(25);
        tabla.setShowVerticalLines(false);
        scrol.getViewport().setBackground(new Color(255, 255, 255));
        scrol.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(235, 235, 235), 2));
    }

    public void insertarSemanas() {
        cmbSemanas.removeAllItems();
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.setMinimalDaysInFirstWeek(4);
        int numberWeekOfYear = calendar.get(Calendar.WEEK_OF_YEAR);
        for (int i = numberWeekOfYear; i >= 1; i--) {
            cmbSemanas.addItem(String.valueOf(i));
        }
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        lblYear.setText(sdf.format(d));
    }

    public void evento(MouseEvent evt, int tab, JTable tabla) {
        this.tabla = tab;
        deseleccionar();
        int fila = -1;
        if (evt.getClickCount() == 2) {
            JFrame f = (JFrame) JOptionPane.getFrameForComponent(this);
            if (lblDepa.getText().equals("HERRAMENTISTA")) {
                addMaq = new agregarMaquinados(f, true);
                addMaq.setLocationRelativeTo(f);
                fila = tabla.getSelectedRow();
                addMaq.btnGuardar.addActionListener(this);
                String id;
                String dimensiones = "";
                String material = "";
                String cantidad = "";
                boolean band = false;
                try {
                    Connection con;
                    Conexion con1 = new Conexion();
                    con = con1.getConnection();
                    Statement st = con.createStatement();
                    try {
                        id = tabla.getValueAt(fila, 3).toString();
                    } catch (Exception e) {
                        id = "";
                    }
                    String sql = "select * from htpp where Id like '" + id + "'";
                    ResultSet rs = st.executeQuery(sql);
                    while (rs.next()) {
                        dimensiones = rs.getString("Dimensiones");
                        material = rs.getString("Material");
                        cantidad = rs.getString("Cantidad");
                    }
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(this, "Error: " + e, "Error", JOptionPane.ERROR_MESSAGE);
                }
                try {
                    addMaq.txtComentarios.setText(tabla.getValueAt(fila, 2).toString());
                } catch (Exception e) {
                    addMaq.txtComentarios.setText("");
                }
                try {
                    addMaq.txtTiempo.setText(tabla.getValueAt(fila, 0).toString());
                } catch (Exception e) {
                    addMaq.txtTiempo.setText("");
                }
                try {
                    addMaq.txtPlano.setText(tabla.getValueAt(fila, 1).toString());
                } catch (Exception e) {
                    addMaq.txtPlano.setText("");
                }
                try {
                    addMaq.lblId.setVisible(true);
                    addMaq.lblId.setText(tabla.getValueAt(fila, 3).toString());
                    band = true;
                } catch (Exception e) {
                    addMaq.lblId.setText("");
                    addMaq.lblId.setVisible(false);
                }
                if (band) {
                    addMaq.txtDimensiones.setText(dimensiones);
                    addMaq.txtMaterial.setText(material);
                    addMaq.txtCantidad.setText(cantidad);
                }
                addMaq.setVisible(true);
            } else if (lblDepa.getText().equals("INTEGRACION")) {
                adIn = new agregarIntegracion(f, true);
                adIn.setLocationRelativeTo(f);
                fila = tabla.getSelectedRow();
                adIn.btnGuardar.addActionListener(this);
                String id;
                String ocupacion = "";
                boolean band = false;
                try {
                    Connection con;
                    Conexion con1 = new Conexion();
                    con = con1.getConnection();
                    Statement st = con.createStatement();
                    try {
                        id = tabla.getValueAt(fila, 3).toString();
                    } catch (Exception e) {
                        id = "";
                    }
                    String sql = "select * from htpp where Id like '" + id + "'";
                    ResultSet rs = st.executeQuery(sql);
                    while (rs.next()) {
                        ocupacion = rs.getString("Ocupacion");
                    }
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(this, "Error: " + e, "Error", JOptionPane.ERROR_MESSAGE);
                }
                try {
                    adIn.txtComentarios.setText(tabla.getValueAt(fila, 2).toString());
                } catch (Exception e) {
                    adIn.txtComentarios.setText("");
                }
                try {
                    adIn.txtTiempo.setText(tabla.getValueAt(fila, 0).toString());
                } catch (Exception e) {
                    adIn.txtTiempo.setText("");
                }
                try {
                    adIn.txtPlano.setText(tabla.getValueAt(fila, 1).toString());
                } catch (Exception e) {
                    adIn.txtPlano.setText("");
                }
                try {
                    adIn.lblId.setVisible(true);
                    adIn.lblId.setText(tabla.getValueAt(fila, 3).toString());
                    band = true;
                } catch (Exception e) {
                    adIn.lblId.setText("");
                    adIn.lblId.setVisible(false);
                }
                if (band) {
                    adIn.cmbOcupacion.setSelectedItem(ocupacion);
                }
                adIn.setVisible(true);
            } else {
                ag = new Agregar(f, true);
                ag.setLocation(evt.getLocationOnScreen());
                fila = tabla.getSelectedRow();
                ag.btnComentarios.addActionListener(this);
                ag.btnHoras.addActionListener(this);
                ag.btnProyecto.addActionListener(this);

                try {
                    ag.btnHoras.setText(tabla.getValueAt(fila, 0).toString());
                } catch (Exception e) {
                    ag.btnHoras.setText("");
                }
                try {
                    ag.btnProyecto.setText(tabla.getValueAt(fila, 1).toString());
                } catch (Exception e) {
                    ag.btnProyecto.setText("");
                }
                try {
                    ag.btnComentarios.setText(tabla.getValueAt(fila, 2).toString());
                } catch (Exception e) {
                    ag.btnComentarios.setText("");
                }
                try {
                    ag.lblID.setText(tabla.getValueAt(fila, 3).toString());
                } catch (Exception e) {
                    ag.lblID.setText("");
                }
                ag.setVisible(true);
            }
        }
    }

    public void verDepa() {
        try {
            Connection con;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            String sql = "select NumEmpleado, Puesto, Nombre, Apellido from registroempleados where NumEmpleado like '" + numEmpleado + "'";
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                lblDepa.setText(rs.getString("Puesto"));
                lblEmpleado.setText(rs.getString("Nombre") + " " + rs.getString("Apellido"));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "ERROR: " + e, "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void deseleccionar() {
        if (tabla == 1) {
            Tabla2.clearSelection();
            Tabla3.clearSelection();
            Tabla4.clearSelection();
            Tabla5.clearSelection();
            Tabla6.clearSelection();
            Tabla7.clearSelection();
        } else if (tabla == 2) {
            Tabla1.clearSelection();
            Tabla3.clearSelection();
            Tabla4.clearSelection();
            Tabla5.clearSelection();
            Tabla6.clearSelection();
            Tabla7.clearSelection();
        } else if (tabla == 3) {
            Tabla1.clearSelection();
            Tabla2.clearSelection();
            Tabla4.clearSelection();
            Tabla5.clearSelection();
            Tabla6.clearSelection();
            Tabla7.clearSelection();
        } else if (tabla == 4) {
            Tabla1.clearSelection();
            Tabla3.clearSelection();
            Tabla2.clearSelection();
            Tabla5.clearSelection();
            Tabla6.clearSelection();
            Tabla7.clearSelection();
        } else if (tabla == 5) {
            Tabla1.clearSelection();
            Tabla3.clearSelection();
            Tabla4.clearSelection();
            Tabla2.clearSelection();
            Tabla6.clearSelection();
            Tabla7.clearSelection();
        } else if (tabla == 6) {
            Tabla1.clearSelection();
            Tabla3.clearSelection();
            Tabla4.clearSelection();
            Tabla5.clearSelection();
            Tabla2.clearSelection();
            Tabla7.clearSelection();
        } else if (tabla == 7) {
            Tabla1.clearSelection();
            Tabla3.clearSelection();
            Tabla4.clearSelection();
            Tabla5.clearSelection();
            Tabla6.clearSelection();
            Tabla2.clearSelection();
        }
    }

    public void limpiarTabla(JTable tabla, JScrollPane scrol) {
        tabla.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{},
                new String[]{
                    "Horas", "Proyecto", "Comentarios", "ID"
                }
        ) {
            boolean[] canEdit = new boolean[]{
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
        tabla.setComponentPopupMenu(jPopupMenu1);
        scrol.setViewportView(tabla);
        if (tabla.getColumnModel().getColumnCount() > 0) {
            tabla.getColumnModel().getColumn(3).setMinWidth(0);
            tabla.getColumnModel().getColumn(3).setPreferredWidth(0);
            tabla.getColumnModel().getColumn(3).setMaxWidth(0);
            tabla.getColumnModel().getColumn(0).setMinWidth(60);
            tabla.getColumnModel().getColumn(0).setPreferredWidth(60);
            tabla.getColumnModel().getColumn(0).setMaxWidth(60);
        }

    }

    public void limpiarTablas() {
        limpiarTabla(Tabla1, jScrollPane1);
        limpiarTabla(Tabla2, jScrollPane2);
        limpiarTabla(Tabla3, jScrollPane3);
        limpiarTabla(Tabla4, jScrollPane4);
        limpiarTabla(Tabla5, jScrollPane5);
        limpiarTabla(Tabla6, jScrollPane6);
        limpiarTabla(Tabla7, jScrollPane7);
    }

    public JTable getTabla(int i) {
        JTable tab = null;
        switch (i) {
            case 1:
                tab = Tabla1;
                break;
            case 2:
                tab = Tabla2;
                break;
            case 3:
                tab = Tabla3;
                break;
            case 4:
                tab = Tabla4;
                break;
            case 5:
                tab = Tabla5;
                break;
            case 6:
                tab = Tabla6;
                break;
            case 7:
                tab = Tabla7;
                break;
        }
        return tab;
    }

    public String getFechaTabla(int i) {
        String fec = "";
        switch (i) {
            case 1:
                fec = lunes.getText();
                break;
            case 2:
                fec = martes.getText();
                break;
            case 3:
                fec = miercoles.getText();
                break;
            case 4:
                fec = jueves.getText();
                break;
            case 5:
                fec = viernes.getText();
                break;
            case 6:
                fec = sabado.getText();
                break;
            case 7:
                fec = domingo.getText();
                break;
        }
        SimpleDateFormat s1 = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat s2 = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date d = s1.parse(fec);
            fec = s2.format(d);
        } catch (ParseException ex) {
            Logger.getLogger(HTPP.class.getName()).log(Level.SEVERE, null, ex);
        }
        return fec;
    }

    public void verDatos() {
        try {
            SimpleDateFormat s1 = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat s2 = new SimpleDateFormat("yyyy-MM-dd");
            Date d1 = s1.parse(lunes.getText());
            Date d2 = s1.parse(domingo.getText());
            String lu = s2.format(d1);
            String domi = s2.format(d2);
            Connection con;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            String sql = "select * from htpp where NumEmpleado like '" + numEmpleado + "' and Fecha between '" + lu + "' and '" + domi + "'";
            ResultSet rs = st.executeQuery(sql);
            Object dat[] = new Object[5];
            while (rs.next()) {
                Date d;
                dat[0] = rs.getString("Hora");
                dat[1] = rs.getString("Proyecto");
                dat[2] = rs.getString("Notas");
                dat[3] = rs.getString("Id");
                int y = 0;
                d = s2.parse(rs.getString("Fecha"));
                String fec = s1.format(d);
                if (fec.equals(lunes.getText())) {
                    y = 1;
                } else if (fec.equals(martes.getText())) {
                    y = 2;
                } else if (fec.equals(miercoles.getText())) {
                    y = 3;
                } else if (fec.equals(jueves.getText())) {
                    y = 4;
                } else if (fec.equals(viernes.getText())) {
                    y = 5;
                } else if (fec.equals(sabado.getText())) {
                    y = 6;
                } else if (fec.equals(domingo.getText())) {
                    y = 7;
                }
                if (y != 0) {
                    DefaultTableModel miModelo = (DefaultTableModel) getTabla(y).getModel();
                    miModelo.addRow(dat);
                }
            }
            DefaultTableModel modelo1 = (DefaultTableModel) Tabla1.getModel();
            DefaultTableModel modelo2 = (DefaultTableModel) Tabla2.getModel();
            DefaultTableModel modelo3 = (DefaultTableModel) Tabla3.getModel();
            DefaultTableModel modelo4 = (DefaultTableModel) Tabla4.getModel();
            DefaultTableModel modelo5 = (DefaultTableModel) Tabla5.getModel();
            DefaultTableModel modelo6 = (DefaultTableModel) Tabla6.getModel();
            DefaultTableModel modelo7 = (DefaultTableModel) Tabla7.getModel();

            String datos[] = new String[3];

            if (Tabla1.getRowCount() < 1) {
                modelo1.addRow(datos);
            }
            if (Tabla2.getRowCount() < 1) {
                modelo2.addRow(datos);
            }
            if (Tabla3.getRowCount() < 1) {
                modelo3.addRow(datos);
            }
            if (Tabla4.getRowCount() < 1) {
                modelo4.addRow(datos);
            }
            if (Tabla5.getRowCount() < 1) {
                modelo5.addRow(datos);
            }
            if (Tabla6.getRowCount() < 1) {
                modelo6.addRow(datos);
            }
            if (Tabla7.getRowCount() < 1) {
                modelo7.addRow(datos);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "ERROR: " + e, "ERROR", JOptionPane.ERROR_MESSAGE);
        } catch (ParseException ex) {
            Logger.getLogger(Cronometro.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getDepa() {
        String depa;
        switch (lblDepa.getText()) {
            case "INTEGRACION":
                depa = "1";
                break;
            case "HERRAMENTISTA":
                depa = "2";
                break;
            case "DISEÑO":
                depa = "3";
                break;
            case "CALIDAD":
                depa = "4";
                break;
            default:
                depa = lblDepa.getText();
                break;
        }
        return depa;
    }

    public String agregarBD(String hora, String proyecto, String nota, String id, String fecha, String dimensiones, int cantidad, String material, String ocupacion) {
        String key = "";
        try {
            Connection con;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            if (id == null || id.equals("")) {
                String sql = "insert into htpp (Fecha, NumEmpleado, Proyecto, Hora, Notas, Departamento) values(?,?,?,?,?,?)";
                if (dimensiones != null && ocupacion == null) {
                    sql = "insert into htpp (Fecha, NumEmpleado, Proyecto, Hora, Notas, Departamento, Dimensiones, Material, Cantidad) values(?,?,?,?,?,?,?,?,?)";
                } else if (ocupacion != null) {
                    sql = "insert into htpp (Fecha, NumEmpleado, Proyecto, Hora, Notas, Departamento, Ocupacion) values(?,?,?,?,?,?,?)";
                }
                PreparedStatement pst = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

                pst.setString(1, fecha);
                pst.setString(2, numEmpleado);
                pst.setString(3, proyecto);
                pst.setString(4, hora);
                pst.setString(5, nota);
                pst.setString(6, getDepa());
                if (dimensiones != null && ocupacion == null) {
                    pst.setString(7, dimensiones);
                    pst.setString(8, material);
                    pst.setInt(9, cantidad);
                } else if (ocupacion != null) {
                    pst.setString(7, ocupacion);
                }

                int n = pst.executeUpdate();

                ResultSet generatedKeys = pst.getGeneratedKeys();
                if (generatedKeys.next()) {
                    long idGenerado = generatedKeys.getLong(1);
                    key = idGenerado + "";
                }

                if (n < 1) {
                    JOptionPane.showMessageDialog(this, "No se guardo esta fila", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                String sql = "update htpp set Fecha = ?, NumEmpleado = ?, Proyecto = ?, Hora = ?, Notas = ?, Departamento = ? where Id = ?";
                if (dimensiones != null && ocupacion == null) {
                    sql = "update htpp set Fecha = ?, NumEmpleado = ?, Proyecto = ?, Hora = ?, Notas = ?, Departamento = ?, Dimensiones = ?, Material = ?, Cantidad = ? where Id = ?";
                } else if (ocupacion != null) {
                    sql = "update htpp set Fecha = ?, NumEmpleado = ?, Proyecto = ?, Hora = ?, Notas = ?, Departamento = ?, Ocupacion = ? where Id = ?";
                }

                PreparedStatement pst = con.prepareStatement(sql);

                pst.setString(1, fecha);
                pst.setString(2, numEmpleado);
                pst.setString(3, proyecto);
                pst.setString(4, hora);
                pst.setString(5, nota);
                pst.setString(6, getDepa());
                if (dimensiones != null && ocupacion == null) {
                    pst.setString(7, dimensiones);
                    pst.setString(8, material);
                    pst.setInt(9, cantidad);
                    pst.setString(10, id);
                } else if (ocupacion != null) {
                    pst.setString(7, ocupacion);
                    pst.setString(8, id);
                } else {
                    pst.setString(7, id);
                }

                int n = pst.executeUpdate();

                key = id;

                if (n < 1) {
                    JOptionPane.showMessageDialog(this, "No se actualizo la fila", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "ERROR: " + e, "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        return key;
    }

    public boolean validarProyecto(String proyecto) {
        if (proyecto.equals("")) {
            return false;
        } else {
            try{
                Connection con;
                Conexion con1 = new Conexion();
                con = con1.getConnection();
                Statement st = con.createStatement();
                String sql = "select proyecto from proyectos where proyecto like '" + proyecto + "'";
                ResultSet rs = st.executeQuery(sql);
                String pr = null;
                while (rs.next()) {
                    pr = rs.getString("proyecto");
                }
                return (pr != null);
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "El proyecto que ingresaste no existe", "Error", JOptionPane.ERROR_MESSAGE);
            }
            return false;
        }
    }
    
    public void clicVentana(String horas, String proyecto, String comentarios, String idB, String depa, String dimensiones, String material, int cantidad, String ocupacion) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            Date d = sdf.parse(horas);
            if (validarProyecto(proyecto)) {
                if (horas.equals("  :  ")) {
                    JOptionPane.showMessageDialog(this, "El formato de horas es incorrecto", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    String dat[] = new String[4];
                    dat[0] = horas;
                    dat[1] = proyecto;
                    dat[2] = comentarios;
                    if (dat[0].equals("")) {
                        JOptionPane.showMessageDialog(this, "Debes llenar las horas", "Advertencia", JOptionPane.WARNING_MESSAGE);
                    } else {
                        String id;
                        if (depa.equals("maquinados")) {
                            id = agregarBD(dat[0], dat[1], dat[2], idB, getFechaTabla(tabla), dimensiones, cantidad, material, null);
                        } else if (depa.equals("integracion")) {
                            id = agregarBD(dat[0], dat[1], dat[2], idB, getFechaTabla(tabla), null, 0, null, ocupacion);
                        } else {
                            id = agregarBD(dat[0], dat[1], dat[2], idB, getFechaTabla(tabla), null, 0, null, null);
                        }
                        switch (tabla) {
                            case 1:
                                Tabla1.setValueAt(dat[0], Tabla1.getSelectedRow(), 0);
                                Tabla1.setValueAt(dat[1], Tabla1.getSelectedRow(), 1);
                                Tabla1.setValueAt(dat[2], Tabla1.getSelectedRow(), 2);
                                Tabla1.setValueAt(id, Tabla1.getSelectedRow(), 3);
                                break;
                            case 2:
                                Tabla2.setValueAt(dat[0], Tabla2.getSelectedRow(), 0);
                                Tabla2.setValueAt(dat[1], Tabla2.getSelectedRow(), 1);
                                Tabla2.setValueAt(dat[2], Tabla2.getSelectedRow(), 2);
                                Tabla2.setValueAt(id, Tabla2.getSelectedRow(), 3);
                                break;
                            case 3:
                                Tabla3.setValueAt(dat[0], Tabla3.getSelectedRow(), 0);
                                Tabla3.setValueAt(dat[1], Tabla3.getSelectedRow(), 1);
                                Tabla3.setValueAt(dat[2], Tabla3.getSelectedRow(), 2);
                                Tabla3.setValueAt(id, Tabla3.getSelectedRow(), 3);
                                break;
                            case 4:
                                Tabla4.setValueAt(dat[0], Tabla4.getSelectedRow(), 0);
                                Tabla4.setValueAt(dat[1], Tabla4.getSelectedRow(), 1);
                                Tabla4.setValueAt(dat[2], Tabla4.getSelectedRow(), 2);
                                Tabla4.setValueAt(id, Tabla4.getSelectedRow(), 3);
                                break;
                            case 5:
                                Tabla5.setValueAt(dat[0], Tabla5.getSelectedRow(), 0);
                                Tabla5.setValueAt(dat[1], Tabla5.getSelectedRow(), 1);
                                Tabla5.setValueAt(dat[2], Tabla5.getSelectedRow(), 2);
                                Tabla5.setValueAt(id, Tabla5.getSelectedRow(), 3);
                                break;
                            case 6:
                                Tabla6.setValueAt(dat[0], Tabla6.getSelectedRow(), 0);
                                Tabla6.setValueAt(dat[1], Tabla6.getSelectedRow(), 1);
                                Tabla6.setValueAt(dat[2], Tabla6.getSelectedRow(), 2);
                                Tabla6.setValueAt(id, Tabla6.getSelectedRow(), 3);
                                break;
                            case 7:
                                Tabla7.setValueAt(dat[0], Tabla7.getSelectedRow(), 0);
                                Tabla7.setValueAt(dat[1], Tabla7.getSelectedRow(), 1);
                                Tabla7.setValueAt(dat[2], Tabla7.getSelectedRow(), 2);
                                Tabla7.setValueAt(id, Tabla7.getSelectedRow(), 3);
                                break;
                            default:
                                break;
                        }
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "El proyecto que ingresaste es incorrecto", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "El tiempo que ingresaste no es correcto", "Error", JOptionPane.WARNING_MESSAGE);
        }
    }

    public HTPP(String numEmpleado) {
        initComponents();
        ((javax.swing.plaf.basic.BasicInternalFrameUI) this.getUI()).setNorthPane(null);
        enchularTabla(Tabla1, jScrollPane1);
        enchularTabla(Tabla2, jScrollPane2);
        enchularTabla(Tabla3, jScrollPane3);
        enchularTabla(Tabla4, jScrollPane4);
        enchularTabla(Tabla5, jScrollPane5);
        enchularTabla(Tabla6, jScrollPane6);
        enchularTabla(Tabla7, jScrollPane7);
        insertarSemanas();
        setFecha();
        this.numEmpleado = numEmpleado;
        verDepa();
        limpiarTablas();
        verDatos();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupMenu1 = new javax.swing.JPopupMenu();
        Agregar = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        Eliminar = new javax.swing.JMenuItem();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        btnSalir = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel17 = new javax.swing.JPanel();
        jPanel20 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        cmbSemanas = new RSMaterialComponent.RSComboBoxMaterial();
        lblYear = new javax.swing.JLabel();
        jPanel21 = new javax.swing.JPanel();
        lblDepa = new javax.swing.JLabel();
        lblEmpleado = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jPanel15 = new javax.swing.JPanel();
        lunes = new javax.swing.JLabel();
        jPanel16 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        Tabla1 = new javax.swing.JTable();
        jPanel9 = new javax.swing.JPanel();
        jPanel18 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jPanel19 = new javax.swing.JPanel();
        martes = new javax.swing.JLabel();
        jPanel22 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        Tabla2 = new javax.swing.JTable();
        jPanel10 = new javax.swing.JPanel();
        jPanel23 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jPanel24 = new javax.swing.JPanel();
        miercoles = new javax.swing.JLabel();
        jPanel25 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        Tabla3 = new javax.swing.JTable();
        jPanel11 = new javax.swing.JPanel();
        jPanel26 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jPanel27 = new javax.swing.JPanel();
        jueves = new javax.swing.JLabel();
        jPanel28 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        Tabla4 = new javax.swing.JTable();
        jPanel7 = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        jPanel29 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jPanel30 = new javax.swing.JPanel();
        viernes = new javax.swing.JLabel();
        jPanel31 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        Tabla5 = new javax.swing.JTable();
        jPanel13 = new javax.swing.JPanel();
        jPanel32 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jPanel33 = new javax.swing.JPanel();
        sabado = new javax.swing.JLabel();
        jPanel34 = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        Tabla6 = new javax.swing.JTable();
        jPanel14 = new javax.swing.JPanel();
        jPanel35 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        jPanel36 = new javax.swing.JPanel();
        domingo = new javax.swing.JLabel();
        jPanel37 = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        Tabla7 = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();

        jPopupMenu1.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
                jPopupMenu1PopupMenuWillBecomeVisible(evt);
            }
        });

        Agregar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/add.png"))); // NOI18N
        Agregar.setText("    AGREGAR FILA                                                    ");
        Agregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AgregarActionPerformed(evt);
            }
        });
        jPopupMenu1.add(Agregar);
        jPopupMenu1.add(jSeparator1);

        Eliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/error.png"))); // NOI18N
        Eliminar.setText("    ELIMINAR                                                    ");
        Eliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EliminarActionPerformed(evt);
            }
        });
        jPopupMenu1.add(Eliminar);

        setBorder(null);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new java.awt.BorderLayout());

        jPanel2.setLayout(new java.awt.BorderLayout());

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jLabel9.setFont(new java.awt.Font("Roboto", 1, 48)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 102, 204));
        jLabel9.setText("HTPP");
        jPanel3.add(jLabel9);

        jPanel2.add(jPanel3, java.awt.BorderLayout.CENTER);

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        btnSalir.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText(" X ");
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

        jPanel4.add(btnSalir);

        jPanel2.add(jPanel4, java.awt.BorderLayout.PAGE_START);

        jPanel17.setLayout(new java.awt.BorderLayout());

        jPanel20.setBackground(new java.awt.Color(255, 255, 255));
        jPanel20.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 20, 5));

        jLabel5.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 0, 0));
        jLabel5.setText("Semana:");
        jPanel20.add(jLabel5);

        cmbSemanas.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Seleccionar" }));
        cmbSemanas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbSemanasActionPerformed(evt);
            }
        });
        jPanel20.add(cmbSemanas);

        lblYear.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        lblYear.setForeground(new java.awt.Color(0, 0, 0));
        lblYear.setText("Año");
        jPanel20.add(lblYear);

        jPanel17.add(jPanel20, java.awt.BorderLayout.CENTER);

        jPanel21.setBackground(new java.awt.Color(255, 255, 255));
        jPanel21.setLayout(new java.awt.BorderLayout());

        lblDepa.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        lblDepa.setForeground(new java.awt.Color(153, 204, 255));
        lblDepa.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblDepa.setText("Departamento");
        jPanel21.add(lblDepa, java.awt.BorderLayout.CENTER);

        lblEmpleado.setFont(new java.awt.Font("Roboto", 1, 22)); // NOI18N
        lblEmpleado.setForeground(new java.awt.Color(0, 102, 204));
        lblEmpleado.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblEmpleado.setText("Empleado");
        lblEmpleado.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblEmpleado.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblEmpleadoMouseClicked(evt);
            }
        });
        jPanel21.add(lblEmpleado, java.awt.BorderLayout.NORTH);

        jPanel17.add(jPanel21, java.awt.BorderLayout.PAGE_START);

        jPanel2.add(jPanel17, java.awt.BorderLayout.PAGE_END);

        jPanel1.add(jPanel2, java.awt.BorderLayout.PAGE_START);

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setLayout(new java.awt.GridLayout(2, 7, 10, 10));

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setLayout(new java.awt.GridLayout(1, 0, 15, 15));

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));
        jPanel8.setLayout(new java.awt.BorderLayout());

        jLabel2.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Lunes");
        jPanel8.add(jLabel2, java.awt.BorderLayout.PAGE_START);

        jPanel15.setBackground(new java.awt.Color(255, 255, 255));
        jPanel15.setLayout(new java.awt.BorderLayout());

        lunes.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        lunes.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lunes.setText("  /  /  ");
        jPanel15.add(lunes, java.awt.BorderLayout.PAGE_START);

        jPanel16.setBackground(new java.awt.Color(255, 255, 255));
        jPanel16.setLayout(new java.awt.BorderLayout());

        jScrollPane1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));

        Tabla1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null}
            },
            new String [] {
                "ID", "Horas", "Proyecto", "Comentarios"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        Tabla1.setComponentPopupMenu(jPopupMenu1);
        Tabla1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                Tabla1FocusGained(evt);
            }
        });
        Tabla1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Tabla1MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                Tabla1MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                Tabla1MouseExited(evt);
            }
        });
        jScrollPane1.setViewportView(Tabla1);

        jPanel16.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jPanel15.add(jPanel16, java.awt.BorderLayout.CENTER);

        jPanel8.add(jPanel15, java.awt.BorderLayout.CENTER);

        jPanel6.add(jPanel8);

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));
        jPanel9.setLayout(new java.awt.BorderLayout());

        jPanel18.setBackground(new java.awt.Color(255, 255, 255));
        jPanel18.setLayout(new java.awt.BorderLayout());

        jLabel6.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Martes");
        jPanel18.add(jLabel6, java.awt.BorderLayout.PAGE_START);

        jPanel19.setBackground(new java.awt.Color(255, 255, 255));
        jPanel19.setLayout(new java.awt.BorderLayout());

        martes.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        martes.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        martes.setText("  /  /  ");
        jPanel19.add(martes, java.awt.BorderLayout.PAGE_START);

        jPanel22.setBackground(new java.awt.Color(255, 255, 255));
        jPanel22.setLayout(new java.awt.BorderLayout());

        jScrollPane2.setBorder(null);

        Tabla2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null}
            },
            new String [] {
                "Horas", "Proyecto", "Comentarios"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        Tabla2.setComponentPopupMenu(jPopupMenu1);
        Tabla2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                Tabla2FocusGained(evt);
            }
        });
        Tabla2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Tabla2MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                Tabla2MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                Tabla2MouseExited(evt);
            }
        });
        jScrollPane2.setViewportView(Tabla2);
        if (Tabla2.getColumnModel().getColumnCount() > 0) {
            Tabla2.getColumnModel().getColumn(0).setMinWidth(60);
            Tabla2.getColumnModel().getColumn(0).setPreferredWidth(60);
            Tabla2.getColumnModel().getColumn(0).setMaxWidth(60);
        }

        jPanel22.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        jPanel19.add(jPanel22, java.awt.BorderLayout.CENTER);

        jPanel18.add(jPanel19, java.awt.BorderLayout.CENTER);

        jPanel9.add(jPanel18, java.awt.BorderLayout.CENTER);

        jPanel6.add(jPanel9);

        jPanel10.setBackground(new java.awt.Color(255, 255, 255));
        jPanel10.setLayout(new java.awt.BorderLayout());

        jPanel23.setBackground(new java.awt.Color(255, 255, 255));
        jPanel23.setLayout(new java.awt.BorderLayout());

        jLabel8.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("Miercoles");
        jPanel23.add(jLabel8, java.awt.BorderLayout.PAGE_START);

        jPanel24.setBackground(new java.awt.Color(255, 255, 255));
        jPanel24.setLayout(new java.awt.BorderLayout());

        miercoles.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        miercoles.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        miercoles.setText("  /  /  ");
        jPanel24.add(miercoles, java.awt.BorderLayout.PAGE_START);

        jPanel25.setBackground(new java.awt.Color(255, 255, 255));
        jPanel25.setLayout(new java.awt.BorderLayout());

        jScrollPane3.setBorder(null);

        Tabla3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null}
            },
            new String [] {
                "Horas", "Proyecto", "Comentarios"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        Tabla3.setComponentPopupMenu(jPopupMenu1);
        Tabla3.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                Tabla3FocusGained(evt);
            }
        });
        Tabla3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Tabla3MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                Tabla3MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                Tabla3MouseExited(evt);
            }
        });
        jScrollPane3.setViewportView(Tabla3);
        if (Tabla3.getColumnModel().getColumnCount() > 0) {
            Tabla3.getColumnModel().getColumn(0).setMinWidth(60);
            Tabla3.getColumnModel().getColumn(0).setPreferredWidth(60);
            Tabla3.getColumnModel().getColumn(0).setMaxWidth(60);
        }

        jPanel25.add(jScrollPane3, java.awt.BorderLayout.CENTER);

        jPanel24.add(jPanel25, java.awt.BorderLayout.CENTER);

        jPanel23.add(jPanel24, java.awt.BorderLayout.CENTER);

        jPanel10.add(jPanel23, java.awt.BorderLayout.CENTER);

        jPanel6.add(jPanel10);

        jPanel11.setBackground(new java.awt.Color(255, 255, 255));
        jPanel11.setLayout(new java.awt.BorderLayout());

        jPanel26.setBackground(new java.awt.Color(255, 255, 255));
        jPanel26.setLayout(new java.awt.BorderLayout());

        jLabel11.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("Jueves");
        jPanel26.add(jLabel11, java.awt.BorderLayout.PAGE_START);

        jPanel27.setBackground(new java.awt.Color(255, 255, 255));
        jPanel27.setLayout(new java.awt.BorderLayout());

        jueves.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jueves.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jueves.setText("  /  /  ");
        jPanel27.add(jueves, java.awt.BorderLayout.PAGE_START);

        jPanel28.setBackground(new java.awt.Color(255, 255, 255));
        jPanel28.setLayout(new java.awt.BorderLayout());

        jScrollPane4.setBorder(null);

        Tabla4.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null}
            },
            new String [] {
                "Horas", "Proyecto", "Comentarios"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        Tabla4.setComponentPopupMenu(jPopupMenu1);
        Tabla4.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                Tabla4FocusGained(evt);
            }
        });
        Tabla4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Tabla4MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                Tabla4MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                Tabla4MouseExited(evt);
            }
        });
        jScrollPane4.setViewportView(Tabla4);
        if (Tabla4.getColumnModel().getColumnCount() > 0) {
            Tabla4.getColumnModel().getColumn(0).setMinWidth(60);
            Tabla4.getColumnModel().getColumn(0).setPreferredWidth(60);
            Tabla4.getColumnModel().getColumn(0).setMaxWidth(60);
        }

        jPanel28.add(jScrollPane4, java.awt.BorderLayout.CENTER);

        jPanel27.add(jPanel28, java.awt.BorderLayout.CENTER);

        jPanel26.add(jPanel27, java.awt.BorderLayout.CENTER);

        jPanel11.add(jPanel26, java.awt.BorderLayout.CENTER);

        jPanel6.add(jPanel11);

        jPanel5.add(jPanel6);

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setLayout(new java.awt.GridLayout(1, 0, 15, 0));

        jPanel12.setBackground(new java.awt.Color(255, 255, 255));
        jPanel12.setLayout(new java.awt.BorderLayout());

        jPanel29.setBackground(new java.awt.Color(255, 255, 255));
        jPanel29.setLayout(new java.awt.BorderLayout());

        jLabel13.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setText("Viernes");
        jPanel29.add(jLabel13, java.awt.BorderLayout.PAGE_START);

        jPanel30.setBackground(new java.awt.Color(255, 255, 255));
        jPanel30.setLayout(new java.awt.BorderLayout());

        viernes.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        viernes.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        viernes.setText("  /  /  ");
        jPanel30.add(viernes, java.awt.BorderLayout.PAGE_START);

        jPanel31.setBackground(new java.awt.Color(255, 255, 255));
        jPanel31.setLayout(new java.awt.BorderLayout());

        jScrollPane5.setBorder(null);

        Tabla5.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null}
            },
            new String [] {
                "Horas", "Proyecto", "Comentarios"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        Tabla5.setComponentPopupMenu(jPopupMenu1);
        Tabla5.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                Tabla5FocusGained(evt);
            }
        });
        Tabla5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Tabla5MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                Tabla5MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                Tabla5MouseExited(evt);
            }
        });
        jScrollPane5.setViewportView(Tabla5);
        if (Tabla5.getColumnModel().getColumnCount() > 0) {
            Tabla5.getColumnModel().getColumn(0).setMinWidth(60);
            Tabla5.getColumnModel().getColumn(0).setPreferredWidth(60);
            Tabla5.getColumnModel().getColumn(0).setMaxWidth(60);
        }

        jPanel31.add(jScrollPane5, java.awt.BorderLayout.CENTER);

        jPanel30.add(jPanel31, java.awt.BorderLayout.CENTER);

        jPanel29.add(jPanel30, java.awt.BorderLayout.CENTER);

        jPanel12.add(jPanel29, java.awt.BorderLayout.CENTER);

        jPanel7.add(jPanel12);

        jPanel13.setBackground(new java.awt.Color(255, 255, 255));
        jPanel13.setLayout(new java.awt.BorderLayout());

        jPanel32.setBackground(new java.awt.Color(255, 255, 255));
        jPanel32.setLayout(new java.awt.BorderLayout());

        jLabel15.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel15.setText("Sabado");
        jPanel32.add(jLabel15, java.awt.BorderLayout.PAGE_START);

        jPanel33.setBackground(new java.awt.Color(255, 255, 255));
        jPanel33.setLayout(new java.awt.BorderLayout());

        sabado.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        sabado.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        sabado.setText("  /  /  ");
        jPanel33.add(sabado, java.awt.BorderLayout.PAGE_START);

        jPanel34.setBackground(new java.awt.Color(255, 255, 255));
        jPanel34.setLayout(new java.awt.BorderLayout());

        jScrollPane6.setBorder(null);

        Tabla6.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null}
            },
            new String [] {
                "Horas", "Proyecto", "Comentarios"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        Tabla6.setComponentPopupMenu(jPopupMenu1);
        Tabla6.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                Tabla6FocusGained(evt);
            }
        });
        Tabla6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Tabla6MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                Tabla6MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                Tabla6MouseExited(evt);
            }
        });
        jScrollPane6.setViewportView(Tabla6);
        if (Tabla6.getColumnModel().getColumnCount() > 0) {
            Tabla6.getColumnModel().getColumn(0).setMinWidth(60);
            Tabla6.getColumnModel().getColumn(0).setPreferredWidth(60);
            Tabla6.getColumnModel().getColumn(0).setMaxWidth(60);
        }

        jPanel34.add(jScrollPane6, java.awt.BorderLayout.CENTER);

        jPanel33.add(jPanel34, java.awt.BorderLayout.CENTER);

        jPanel32.add(jPanel33, java.awt.BorderLayout.CENTER);

        jPanel13.add(jPanel32, java.awt.BorderLayout.CENTER);

        jPanel7.add(jPanel13);

        jPanel14.setBackground(new java.awt.Color(255, 255, 255));
        jPanel14.setLayout(new java.awt.BorderLayout());

        jPanel35.setBackground(new java.awt.Color(255, 255, 255));
        jPanel35.setLayout(new java.awt.BorderLayout());

        jLabel17.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel17.setText("Domingo");
        jPanel35.add(jLabel17, java.awt.BorderLayout.PAGE_START);

        jPanel36.setBackground(new java.awt.Color(255, 255, 255));
        jPanel36.setLayout(new java.awt.BorderLayout());

        domingo.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        domingo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        domingo.setText("  /  /  ");
        jPanel36.add(domingo, java.awt.BorderLayout.PAGE_START);

        jPanel37.setBackground(new java.awt.Color(255, 255, 255));
        jPanel37.setLayout(new java.awt.BorderLayout());

        jScrollPane7.setBorder(null);

        Tabla7.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null}
            },
            new String [] {
                "Horas", "Proyecto", "Comentarios"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        Tabla7.setComponentPopupMenu(jPopupMenu1);
        Tabla7.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                Tabla7FocusGained(evt);
            }
        });
        Tabla7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Tabla7MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                Tabla7MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                Tabla7MouseExited(evt);
            }
        });
        jScrollPane7.setViewportView(Tabla7);
        if (Tabla7.getColumnModel().getColumnCount() > 0) {
            Tabla7.getColumnModel().getColumn(0).setMinWidth(60);
            Tabla7.getColumnModel().getColumn(0).setPreferredWidth(60);
            Tabla7.getColumnModel().getColumn(0).setMaxWidth(60);
        }

        jPanel37.add(jScrollPane7, java.awt.BorderLayout.CENTER);

        jPanel36.add(jPanel37, java.awt.BorderLayout.CENTER);

        jPanel35.add(jPanel36, java.awt.BorderLayout.CENTER);

        jPanel14.add(jPanel35, java.awt.BorderLayout.CENTER);

        jPanel7.add(jPanel14);
        jPanel7.add(jLabel3);

        jPanel5.add(jPanel7);

        jPanel1.add(jPanel5, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        jMenu1.setBackground(new java.awt.Color(204, 204, 204));
        jMenu1.setText("File");

        jMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        jMenuItem1.setBackground(new java.awt.Color(204, 204, 204));
        jMenuItem1.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        jMenuItem1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/estadisticas.png"))); // NOI18N
        jMenuItem1.setText("Ver estadisticas                                                                   ");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

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

    private void Tabla1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Tabla1MouseEntered

    }//GEN-LAST:event_Tabla1MouseEntered

    private void Tabla1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Tabla1MouseExited

    }//GEN-LAST:event_Tabla1MouseExited

    private void AgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AgregarActionPerformed
        DefaultTableModel miModelo = null;
        switch (tabla) {
            case 1:
                miModelo = (DefaultTableModel) Tabla1.getModel();
                break;
            case 2:
                miModelo = (DefaultTableModel) Tabla2.getModel();
                break;
            case 3:
                miModelo = (DefaultTableModel) Tabla3.getModel();
                break;
            case 4:
                miModelo = (DefaultTableModel) Tabla4.getModel();
                break;
            case 5:
                miModelo = (DefaultTableModel) Tabla5.getModel();
                break;
            case 6:
                miModelo = (DefaultTableModel) Tabla6.getModel();
                break;
            case 7:
                miModelo = (DefaultTableModel) Tabla7.getModel();
                break;
            default:
                break;
        }
        String d[] = new String[4];
        d[0] = "";
        d[1] = "";
        d[2] = "";
        miModelo.addRow(d);
    }//GEN-LAST:event_AgregarActionPerformed

    private void Tabla1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Tabla1MouseClicked
        evento(evt, 1, Tabla1);
    }//GEN-LAST:event_Tabla1MouseClicked

    private void jPopupMenu1PopupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_jPopupMenu1PopupMenuWillBecomeVisible
        JTable tab = null;
        switch (tabla) {
            case 1:
                tab = Tabla1;
                break;
            case 2:
                tab = Tabla2;
                break;
            case 3:
                tab = Tabla3;
                break;
            case 4:
                tab = Tabla4;
                break;
            case 5:
                tab = Tabla5;
                break;
            case 6:
                tab = Tabla6;
                break;
            case 7:
                tab = Tabla7;
                break;
            default:
                break;
        }
        if (tab.getSelectedRow() > 0) {
            Eliminar.setEnabled(true);
        } else {
            Eliminar.setEnabled(false);
        }
    }//GEN-LAST:event_jPopupMenu1PopupMenuWillBecomeVisible

    private void EliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EliminarActionPerformed
        DefaultTableModel miModelo = null;
        JTable tab = null;
        switch (tabla) {
            case 1:
                tab = Tabla1;
                break;
            case 2:
                tab = Tabla2;
                break;
            case 3:
                tab = Tabla3;
                break;
            case 4:
                tab = Tabla4;
                break;
            case 5:
                tab = Tabla5;
                break;
            case 6:
                tab = Tabla6;
                break;
            case 7:
                tab = Tabla7;
                break;
            default:
                break;
        }
        miModelo = (DefaultTableModel) tab.getModel();
        String filas = "";
        for (int i = 0; i < tab.getSelectedRows().length; i++) {
            filas += tab.getSelectedRows()[i] + 1 + ", ";
        }
        int opc = JOptionPane.showConfirmDialog(this, "¿Estas seguro de eliminar la fila " + filas + "?");
        if (opc == 0) {
            int n = 0;
            for (int i = tab.getSelectedRows().length - 1; i >= 0; i--) {
                if (tab.getValueAt(tab.getSelectedRows()[i], 3) != null) {
                    if (!tab.getValueAt(tab.getSelectedRows()[i], 3).toString().equals("")) {
                        try {
                            Connection con;
                            Conexion con1 = new Conexion();
                            con = con1.getConnection();
                            String sql = "delete from htpp where Id = ?";
                            PreparedStatement pst = con.prepareStatement(sql);

                            pst.setString(1, tab.getValueAt(tab.getSelectedRows()[i], 3).toString());

                            n = pst.executeUpdate();
                            miModelo.removeRow(tab.getSelectedRows()[i]);
                        } catch (SQLException e) {
                            JOptionPane.showMessageDialog(this, "ERROR: " + e, "ERROR", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        miModelo.removeRow(tab.getSelectedRows()[i]);
                    }
                } else {
                    miModelo.removeRow(tab.getSelectedRows()[i]);
                }
            }
            if (n > 0) {
                JOptionPane.showMessageDialog(this, "Datos eliminados de la base de datos");
            }
        }
    }//GEN-LAST:event_EliminarActionPerformed

    private void cmbSemanasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbSemanasActionPerformed
        if (cmbSemanas.getSelectedItem() != null) {
            if (!cmbSemanas.getSelectedItem().equals("") && !lblYear.getText().equals("Año")) {
                setFecha();
                limpiarTablas();
                verDatos();
                if (this.isVisible()) {
//                    limpiar();
//                    setBlanco();
//                    verDatos();
                }
            }
        }
    }//GEN-LAST:event_cmbSemanasActionPerformed

    private void Tabla2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Tabla2MouseClicked
        evento(evt, 2, Tabla2);
    }//GEN-LAST:event_Tabla2MouseClicked

    private void Tabla2MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Tabla2MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_Tabla2MouseEntered

    private void Tabla2MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Tabla2MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_Tabla2MouseExited

    private void Tabla3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Tabla3MouseClicked
        evento(evt, 3, Tabla3);
    }//GEN-LAST:event_Tabla3MouseClicked

    private void Tabla3MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Tabla3MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_Tabla3MouseEntered

    private void Tabla3MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Tabla3MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_Tabla3MouseExited

    private void Tabla4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Tabla4MouseClicked
        evento(evt, 4, Tabla4);
    }//GEN-LAST:event_Tabla4MouseClicked

    private void Tabla4MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Tabla4MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_Tabla4MouseEntered

    private void Tabla4MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Tabla4MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_Tabla4MouseExited

    private void Tabla5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Tabla5MouseClicked
        evento(evt, 5, Tabla5);
    }//GEN-LAST:event_Tabla5MouseClicked

    private void Tabla5MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Tabla5MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_Tabla5MouseEntered

    private void Tabla5MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Tabla5MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_Tabla5MouseExited

    private void Tabla6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Tabla6MouseClicked
        evento(evt, 6, Tabla6);
    }//GEN-LAST:event_Tabla6MouseClicked

    private void Tabla6MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Tabla6MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_Tabla6MouseEntered

    private void Tabla6MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Tabla6MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_Tabla6MouseExited

    private void Tabla7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Tabla7MouseClicked
        evento(evt, 7, Tabla7);
    }//GEN-LAST:event_Tabla7MouseClicked

    private void Tabla7MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Tabla7MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_Tabla7MouseEntered

    private void Tabla7MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Tabla7MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_Tabla7MouseExited

    private void Tabla1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_Tabla1FocusGained
        tabla = 1;
        deseleccionar();
    }//GEN-LAST:event_Tabla1FocusGained

    private void Tabla2FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_Tabla2FocusGained
        tabla = 2;
        deseleccionar();
    }//GEN-LAST:event_Tabla2FocusGained

    private void Tabla3FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_Tabla3FocusGained
        tabla = 3;
        deseleccionar();
    }//GEN-LAST:event_Tabla3FocusGained

    private void Tabla4FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_Tabla4FocusGained
        tabla = 4;
        deseleccionar();
    }//GEN-LAST:event_Tabla4FocusGained

    private void Tabla5FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_Tabla5FocusGained
        tabla = 5;
        deseleccionar();
    }//GEN-LAST:event_Tabla5FocusGained

    private void Tabla6FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_Tabla6FocusGained
        tabla = 6;
        deseleccionar();
    }//GEN-LAST:event_Tabla6FocusGained

    private void Tabla7FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_Tabla7FocusGained
        tabla = 1;
        deseleccionar();
    }//GEN-LAST:event_Tabla7FocusGained

    private void lblEmpleadoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblEmpleadoMouseClicked
        try {
            String empleado = JOptionPane.showInputDialog("Ingresa numero de empleado");
            int n = Integer.parseInt(empleado);
            numEmpleado = empleado;
            insertarSemanas();
            setFecha();
            verDepa();
            limpiarTablas();
            verDatos();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Debes ingresar un numero de empleado", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_lblEmpleadoMouseClicked

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed

    }//GEN-LAST:event_jMenuItem1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem Agregar;
    private javax.swing.JMenuItem Eliminar;
    private javax.swing.JTable Tabla1;
    private javax.swing.JTable Tabla2;
    private javax.swing.JTable Tabla3;
    private javax.swing.JTable Tabla4;
    private javax.swing.JTable Tabla5;
    private javax.swing.JTable Tabla6;
    private javax.swing.JTable Tabla7;
    private javax.swing.JPanel btnSalir;
    private RSMaterialComponent.RSComboBoxMaterial cmbSemanas;
    private javax.swing.JLabel domingo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel24;
    private javax.swing.JPanel jPanel25;
    private javax.swing.JPanel jPanel26;
    private javax.swing.JPanel jPanel27;
    private javax.swing.JPanel jPanel28;
    private javax.swing.JPanel jPanel29;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel30;
    private javax.swing.JPanel jPanel31;
    private javax.swing.JPanel jPanel32;
    private javax.swing.JPanel jPanel33;
    private javax.swing.JPanel jPanel34;
    private javax.swing.JPanel jPanel35;
    private javax.swing.JPanel jPanel36;
    private javax.swing.JPanel jPanel37;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JLabel jueves;
    private javax.swing.JLabel lblDepa;
    private javax.swing.JLabel lblEmpleado;
    private javax.swing.JLabel lblYear;
    private javax.swing.JLabel lunes;
    private javax.swing.JLabel martes;
    private javax.swing.JLabel miercoles;
    private javax.swing.JLabel sabado;
    private javax.swing.JLabel viernes;
    // End of variables declaration//GEN-END:variables

    @Override
    public void actionPerformed(ActionEvent e) {
        if (ag != null) {
            if (e.getSource() == ag.btnComentarios || e.getSource() == ag.btnHoras || e.getSource() == ag.btnProyecto) {
                clicVentana(ag.btnHoras.getText(), ag.btnProyecto.getText(), ag.btnComentarios.getText(),
                        ag.lblID.getText(), "", null, null, 0, null);
                ag.dispose();
            }
        }
        if (addMaq != null) {
            if (e.getSource() == addMaq.btnGuardar) {
                clicVentana(addMaq.txtTiempo.getText(), addMaq.txtPlano.getText(), addMaq.txtComentarios.getText(),
                        addMaq.lblId.getText(), "maquinados", addMaq.txtDimensiones.getText(), addMaq.txtMaterial.getText(),
                        Integer.parseInt(addMaq.txtCantidad.getText()), null);
            }
            addMaq.dispose();
        }
        if (adIn != null) {
            if (e.getSource() == adIn.btnGuardar) {
                clicVentana(adIn.txtTiempo.getText(), adIn.txtPlano.getText(), adIn.txtComentarios.getText(),
                        adIn.lblId.getText(), "integracion", null, null, 0, adIn.cmbOcupacion.getSelectedItem().toString());
            }
            adIn.dispose();
        }
    }
}
