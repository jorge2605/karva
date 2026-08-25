package Controlador.maquinados;

import Conexiones.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Stack;
import javax.swing.JOptionPane;

public class revisarPlanos {

    public String CORTES = "datos";
    public String SIN_MATERIAL = "datos";
    public String FRESADORA = "fresadora";
    public String CNC = "cnc";
    public String TORNO = "torno";
    public String ACABADOS = "acabados";
    public String CALIDAD = "calidad";
    public String TRATAMIENTO = "trata";
    public String MAQUINADOS = "maquinados";
    public String INTEGRACION = "integracion";
    public String TERMINADO_TRATAMIENTO = "trata";
    public String TERMINADO_CALIDAD = "calidad";
    public String PROYECTO_FINALIZADO = "integracion";
    public String LIBERACION = "";

    public boolean retrabajo = false;
    public boolean mostrarMensaje = true;

    public Stack checarRevisionPlano(String plano) {
        Stack<String> revision = new Stack<>();
        if (plano.contains("/")) {
            revision.push(plano.substring(plano.lastIndexOf("/") + 1, plano.length()));
            if (getPlano(plano.substring(0, (plano.lastIndexOf("/"))), revision.firstElement())) {
                return revision;
            } else {
                return null;
            }
        } else {
            try {
                Connection con;
                Conexion con1 = new Conexion();
                con = con1.getConnection();
                Statement st = con.createStatement();
                String sql = "select Plano, Revision from Planos where Plano like '" + plano + "'";
                ResultSet rs = st.executeQuery(sql);
                while (rs.next()) {
                    revision.push(rs.getString("Revision"));
                }
                if (revision.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "NO EXISTE ESTE PLANO", "ERROR", JOptionPane.ERROR_MESSAGE);
                    return null;
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "ERROR: " + e, "ERROR", JOptionPane.ERROR_MESSAGE);
            }
            return revision;
        }
    }

    public boolean getPlano(String plano, String revision) {
        boolean band = false;
        try {
            Connection con;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            String sql = "select Plano, Revision from planos where Plano like '" + plano + "'";
            ResultSet rs = st.executeQuery(sql);
            String plan = null;
            String rev = null;
            while (rs.next()) {
                plan = rs.getString("Plano");
                rev = rs.getString("Revision");
            }
            if (plan != null) {
                if (rev == null ? revision == null : rev.equals(revision)) {
                    band = true;
                } else {
                    JOptionPane.showMessageDialog(null, "No se encontro la Revision", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "No se encontro este Plano", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e, "Error", JOptionPane.ERROR_MESSAGE);
        }
        return band;
    }

    public String buscar(String plano, Connection con) throws SQLException {
        String datos[] = new String[1000];
        datos[3] = null;
        Statement st = con.createStatement();
        Statement st1 = con.createStatement();
        Statement st2 = con.createStatement();
        Statement st3 = con.createStatement();
        Statement st4 = con.createStatement();
        Statement st5 = con.createStatement();
        Statement st6 = con.createStatement();
        Statement st7 = con.createStatement();
        Statement st8 = con.createStatement();
        Statement st9 = con.createStatement();

        String sql = "select * from Planos where Plano like '" + plano + "'";
        ResultSet rs = st.executeQuery(sql);
        while (rs.next()) {
            int cont = 0;
            datos[0] = rs.getString("Prioridad");
            datos[1] = rs.getString("Plano");
            datos[2] = rs.getString("Proyecto");
            String acabados[] = new String[10];
            String calidad[] = new String[10];
            String cnc[] = new String[10];
            String fresa[] = new String[10];
            String cortes[] = new String[10];
            String torno[] = new String[10];
            String trata[] = new String[10];
            String maqui[] = new String[10];
            String integracion[] = new String[10];
            String id = datos[1];

            String sq = "select * from Calidad where Proyecto like '" + datos[1] + "'";
            ResultSet r = st1.executeQuery(sq);
            String sql1 = "select * from Acabados where Proyecto like '" + datos[1] + "'";
            ResultSet rs1 = st2.executeQuery(sql1);
            String sql2 = "select * from CNC where Proyecto like '" + datos[1] + "'";
            ResultSet rs2 = st3.executeQuery(sql2);
            String sql3 = "select * from Fresadora where Proyecto like '" + datos[1] + "'";
            ResultSet rs3 = st4.executeQuery(sql3);
            String sql5 = "select * from Torno where Proyecto like '" + datos[1] + "'";
            ResultSet rs5 = st5.executeQuery(sql5);
            String sql4 = "select * from Datos where Proyecto like '" + datos[1] + "'";
            ResultSet rs4 = st6.executeQuery(sql4);
            String sql6 = "select * from Trata where Proyecto like '" + datos[1] + "'";
            ResultSet rs6 = st7.executeQuery(sql6);
            String sql7 = "select * from maquinados where Proyecto like '" + datos[1] + "'";
            ResultSet rs7 = st8.executeQuery(sql7);
            String sql8 = "select * from integracion where Proyecto like '" + datos[1] + "'";
            ResultSet rs8 = st9.executeQuery(sql8);
            while (r.next()) {
                calidad[0] = r.getString("Id");
                calidad[1] = r.getString("Proyecto");
                calidad[2] = r.getString("Plano");
                calidad[3] = r.getString("Terminado");
                calidad[3] = (calidad[3] == null) ? "SI" : calidad[3];
                calidad[4] = r.getString("Tratamiento");
                calidad[4] = (calidad[4] == null) ? "NO" : calidad[4];
                calidad[5] = r.getString("Prioridad");
                calidad[6] = r.getString("Revision");
            }

            while (rs1.next()) {
                acabados[0] = rs1.getString("Id");
                acabados[1] = rs1.getString("Proyecto");
                acabados[2] = rs1.getString("Plano");
                acabados[3] = rs1.getString("Terminado");
                acabados[5] = rs1.getString("Prioridad");
                acabados[6] = rs1.getString("Revision");
            }
            while (rs2.next()) {
                cnc[0] = rs2.getString("Id");
                cnc[1] = rs2.getString("Proyecto");
                cnc[2] = rs2.getString("Plano");
                cnc[3] = rs2.getString("Terminado");
                cnc[5] = rs2.getString("Prioridad");
                cnc[6] = rs2.getString("Revision");
            }
            while (rs3.next()) {
                fresa[0] = rs3.getString("Id");
                fresa[1] = rs3.getString("Proyecto");
                fresa[2] = rs3.getString("Plano");
                fresa[3] = rs3.getString("Terminado");
                fresa[5] = rs3.getString("Prioridad");
                fresa[6] = rs3.getString("Revision");
            }
            while (rs4.next()) {
                cortes[0] = rs4.getString("Id");
                cortes[1] = rs4.getString("Proyecto");
                cortes[2] = rs4.getString("Plano");
                cortes[3] = rs4.getString("Terminado");
                cortes[5] = rs4.getString("Prioridad");
                cortes[6] = rs4.getString("Estado");
                if (cortes[6] == null) {
                    cortes[6] = "";
                }
            }
            while (rs5.next()) {
                torno[0] = rs5.getString("Id");
                torno[1] = rs5.getString("Proyecto");
                torno[2] = rs5.getString("Plano");
                torno[3] = rs5.getString("Terminado");
                torno[5] = rs5.getString("Prioridad");
                torno[6] = rs5.getString("Revision");
            }
            while (rs6.next()) {
                trata[0] = rs6.getString("Id");
                trata[1] = rs6.getString("Proyecto");
                trata[2] = rs6.getString("Plano");
                trata[3] = rs6.getString("Terminado");
                trata[5] = rs6.getString("Prioridad");
                trata[6] = rs6.getString("Revision");
            }
            while (rs7.next()) {
                maqui[0] = rs7.getString("idmaquinados");
                maqui[1] = rs7.getString("Proyecto");
                maqui[2] = rs7.getString("Plano");
                maqui[3] = rs7.getString("Terminado");
                maqui[5] = rs7.getString("Prioridad");
                maqui[6] = rs7.getString("Revision");
            }
            while (rs8.next()) {
                integracion[0] = rs8.getString("Id");
                integracion[1] = rs8.getString("Proyecto");
                integracion[2] = rs8.getString("Plano");
                integracion[3] = rs8.getString("Terminado");
                integracion[5] = rs8.getString("Prioridad");
                integracion[6] = rs8.getString("Revision");
            }

            if (id.equals(cortes[1]) && cortes[3].equals("NO")) {
                datos[3] = "CORTES";
                if (cortes[6].equals("SIN MATERIAL")) {
                    datos[3] = "SIN MATERIAL";
                }
            } else if (id.equals(maqui[1]) && maqui[3].equals("NO")) {
                datos[3] = "MAQUINADOS";
            } else if (id.equals(cnc[1]) && cnc[3].equals("NO")) {
                datos[3] = "CNC";
            } else if (id.equals(fresa[1]) && fresa[3].equals("NO")) {
                datos[3] = "FRESADORA";
            } else if (id.equals(torno[1]) && torno[3].equals("NO")) {
                datos[3] = "TORNO";
            } else if (id.equals(acabados[1]) && acabados[3].equals("NO")) {
                datos[3] = "ACABADOS";
            } else if (id.equals(trata[1]) && trata[3].equals("NO")) {
                datos[3] = "TRATAMIENTO";
            } else if (id.equals(trata[1]) && trata[3].equals("TERMINADO")) {
                datos[3] = "TERMINADO TRATAMIENTO";
            } else if (id.equals(calidad[1]) && calidad[3].equals("NO")) {
                datos[3] = "CALIDAD";
            } else if (id.equals(calidad[1]) && calidad[3].equals("SI") && "NO".equals(calidad[4])) {
                datos[3] = "TERMINADO";
                if (id.equals(integracion[1]) && integracion[3].equals("NO")) {
                    datos[3] = "INTEGRACION";
                } else if (id.equals(integracion[1]) && integracion[3].equals("SI")) {
                    datos[3] = "PROYECTO FINALIZADO";
                }
            } else if (id.equals(integracion[1]) && integracion[3].equals("NO")) {
                datos[3] = "INTEGRACION";
            } else if (id.equals(integracion[1]) && integracion[3].equals("SI")) {
                datos[3] = "PROYECTO FINALIZADO";
            } else {
                datos[3] = "LIBERACION";
            }
            cont += 1;
        }
        if (datos[3] == null) {
            return null;
        }
        return datos[3];
    }

    public String buscar(String plano, String revision) {
        String datos[] = new String[1000];
        datos[3] = null;
        try {
            Connection con = null;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            Statement st1 = con.createStatement();
            Statement st2 = con.createStatement();
            Statement st3 = con.createStatement();
            Statement st4 = con.createStatement();
            Statement st5 = con.createStatement();
            Statement st6 = con.createStatement();
            Statement st7 = con.createStatement();
            setRevisionBD();

            String sql = "select * from Planos where Plano like '" + plano + "' and Revision like '" + revision + "'";
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                int cont = 0;
                datos[0] = rs.getString("Prioridad");
                datos[1] = rs.getString("Plano");
                datos[2] = rs.getString("Proyecto");
                String acabados[] = new String[10];
                String calidad[] = new String[10];
                String cnc[] = new String[10];
                String fresa[] = new String[10];
                String cortes[] = new String[10];
                String torno[] = new String[10];
                String trata[] = new String[10];
                String id = datos[1];

                String sq = "select * from Calidad where Proyecto like '" + datos[1] + "' and Revision like '" + revision + "'";
                ResultSet r = st1.executeQuery(sq);
                String sql1 = "select * from Acabados where Proyecto like '" + datos[1] + "' and Revision like '" + revision + "'";
                ResultSet rs1 = st2.executeQuery(sql1);
                String sql2 = "select * from CNC where Proyecto like '" + datos[1] + "' and Revision like '" + revision + "'";
                ResultSet rs2 = st3.executeQuery(sql2);
                String sql3 = "select * from Fresadora where Proyecto like '" + datos[1] + "' and Revision like '" + revision + "'";
                ResultSet rs3 = st4.executeQuery(sql3);
                String sql5 = "select * from Torno where Proyecto like '" + datos[1] + "' and Revision like '" + revision + "'";
                ResultSet rs5 = st5.executeQuery(sql5);
                String sql4 = "select * from Datos where Proyecto like '" + datos[1] + "' and Revision like '" + revision + "'";
                ResultSet rs4 = st6.executeQuery(sql4);
                String sql6 = "select * from Trata where Proyecto like '" + datos[1] + "' and Revision like '" + revision + "'";
                ResultSet rs6 = st7.executeQuery(sql6);
                while (r.next()) {
                    calidad[0] = r.getString("Id");
                    calidad[1] = r.getString("Proyecto");
                    calidad[2] = r.getString("Plano");
                    calidad[3] = r.getString("Terminado");
                    calidad[4] = r.getString("Tratamiento");
                    calidad[5] = r.getString("Prioridad");
                }

                while (rs1.next()) {
                    acabados[0] = rs1.getString("Id");
                    acabados[1] = rs1.getString("Proyecto");
                    acabados[2] = rs1.getString("Plano");
                    acabados[3] = rs1.getString("Terminado");
                    acabados[5] = rs1.getString("Prioridad");
                }
                while (rs2.next()) {
                    cnc[0] = rs2.getString("Id");
                    cnc[1] = rs2.getString("Proyecto");
                    cnc[2] = rs2.getString("Plano");
                    cnc[3] = rs2.getString("Terminado");
                    cnc[5] = rs2.getString("Prioridad");
                }
                while (rs3.next()) {
                    fresa[0] = rs3.getString("Id");
                    fresa[1] = rs3.getString("Proyecto");
                    fresa[2] = rs3.getString("Plano");
                    fresa[3] = rs3.getString("Terminado");
                    fresa[5] = rs3.getString("Prioridad");
                }
                while (rs4.next()) {
                    cortes[0] = rs4.getString("Id");
                    cortes[1] = rs4.getString("Proyecto");
                    cortes[2] = rs4.getString("Plano");
                    cortes[3] = rs4.getString("Terminado");
                    cortes[5] = rs4.getString("Prioridad");
                }
                while (rs5.next()) {
                    torno[0] = rs5.getString("Id");
                    torno[1] = rs5.getString("Proyecto");
                    torno[2] = rs5.getString("Plano");
                    torno[3] = rs5.getString("Terminado");
                    torno[5] = rs5.getString("Prioridad");
                }
                while (rs6.next()) {
                    trata[0] = rs6.getString("Id");
                    trata[1] = rs6.getString("Proyecto");
                    trata[2] = rs6.getString("Plano");
                    trata[3] = rs6.getString("Terminado");
                    trata[5] = rs6.getString("Prioridad");
                }

                if (id.equals(cortes[1]) && cortes[3].equals("NO")) {
                    datos[3] = "CORTES";
                } else if (id.equals(cnc[1]) && cnc[3].equals("NO")) {
                    datos[3] = "CNC";
                } else if (id.equals(fresa[1]) && fresa[3].equals("NO")) {
                    datos[3] = "FRESADORA";
                } else if (id.equals(torno[1]) && torno[3].equals("NO")) {
                    datos[3] = "TORNO";
                } else if (id.equals(acabados[1]) && acabados[3].equals("NO")) {
                    datos[3] = "ACABADOS";
                } else if (id.equals(trata[1]) && trata[3].equals("NO")) {
                    datos[3] = "TRATAMIENTO";
                } else if (id.equals(trata[1]) && trata[3].equals("TERMINADO")) {
                    datos[3] = "TERMINADO TRATAMIENTO";
                } else if (id.equals(calidad[1]) && calidad[3].equals("NO")) {
                    datos[3] = "CALIDAD";
                } else if (id.equals(calidad[1]) && calidad[3].equals("SI") && "NO".equals(calidad[4])) {
                    datos[3] = "TERMINADO CALIDAD";
                } else {
                    datos[3] = "LIBERACION";
                }

                cont += 1;
            }
            if (datos[3] == null) {
                setRevisionBD();
                return buscar(plano, revision);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "NO SE PUEDEN MOSTRAR LOS DATOS" + " " + e, "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        return datos[3];
    }

    public void setRevisionBD() {
        setRevision("datos");
        setRevision("fresadora");
        setRevision("torno");
        setRevision("cnc");
        setRevision("acabados");
        setRevision("calidad");
        setRevision("integracion");
    }

    public void setRevision(String bd) {
        try {
            Connection con;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            String sql = "select * from " + bd + " where Revision is null and Revision = ''";
            ResultSet rs = st.executeQuery(sql);
            String sql2 = "update " + bd + " set Revision = ? where Proyecto = ?";
            PreparedStatement pst = con.prepareStatement(sql2);
            while (rs.next()) {
                String plano = rs.getString("Proyecto");
                String rev = rs.getString("Revision");
                if (rev != null) {
                    String sql3 = "select Plano, Revision from Planos where Plano like '" + plano + "'";
                    Statement st2 = con.createStatement();
                    ResultSet rs2 = st2.executeQuery(sql3);
                    String revision = "";
                    while (rs2.next()) {
                        revision = rs2.getString("Revision");
                    }
                    pst.setString(1, revision);
                    pst.setString(2, plano);
                    int n = pst.executeUpdate();

                    if (n < 1) {
                        JOptionPane.showMessageDialog(null, "Datos no actualizados: Revision - Plano: " + plano, "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR: " + e, "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void enviarCortes(String bd, String plano, String numEmpleado, String proyecto, String revision) {
        try {
            Connection con;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            razon razon = new razon(null, true, this);
            if (retrabajo) {
                razon.jRadioButton1.setText("Retrabajo");
                razon.jRadioButton1.setSelected(true);
                razon.jRadioButton2.setVisible(false);
                razon.jRadioButton3.setVisible(false);
                razon.jRadioButton4.setVisible(false);
            }
            String raz[] = razon.getRazon();
            //                                   1          2           3       4       5       6           7
            String sql = "insert into scrap (Proyecto, NumeroEmpleado, Fecha, Plano, Razon, Comentarios,Desde, Revision) values(?,?,?,?,?,?,?,?)";
            PreparedStatement pst = con.prepareStatement(sql);

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date d = new Date();
            String fecha = sdf.format(d);

            pst.setString(1, plano);
            pst.setString(2, numEmpleado);
            pst.setString(3, fecha);
            pst.setString(4, proyecto);
            pst.setString(5, raz[1]);
            pst.setString(6, raz[0]);
            pst.setString(7, bd);
            pst.setString(8, revision);

            int n = pst.executeUpdate();

            if (n < 1) {
                JOptionPane.showMessageDialog(null, "NO SE ENVIO CORRECTAMENTE", "ERROR", JOptionPane.ERROR_MESSAGE);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR: " + e, "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    public int intentosRealizadorPorPieza(String plano, String revision) {
        int cont = 0;
        try {
            Connection con;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            String sql = "select * from scrap where Proyecto like '" + plano + "' and Revision like '" + revision + "'";
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                cont++;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e, "error", JOptionPane.ERROR_MESSAGE);
        }
        return cont;
    }

    public String convertirStringToEstacion(String estacion) {
        String esta = null;
        if (estacion != null) {
            switch (estacion) {
                case "CORTES":
                    return CORTES;
                case "SIN MATERIAL":
                    return CORTES;
                case "FRESADORA":
                    return FRESADORA;
                case "MAQUINADOS":
                    return MAQUINADOS;
                case "CNC":
                    return CNC;
                case "TORNO":
                    return TORNO;
                case "ACABADOS":
                    return ACABADOS;
                case "CALIDAD":
                    return CALIDAD;
                case "TRATAMIENTO":
                    return TRATAMIENTO;
                case "TERMINADO_TRATAMIENTO":
                    return TERMINADO_TRATAMIENTO;
                case "TERMINADO_CALIDAD":
                    return TERMINADO_CALIDAD;
                case "LIBERACION":
                    return LIBERACION;
                case "INTEGRACION":
                    return INTEGRACION;
                case "TERMINADO INTEGRACION":
                    return PROYECTO_FINALIZADO;
                default:
                    break;
            }
        }
        return esta;
    }

    public void terminarPlanoEnEstacion(String estacion, String plano, String empleado) {
        try {
            Connection con;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            estacion = convertirStringToEstacion(estacion.toUpperCase());
            if (estacion != null) {
                String sql = "update " + estacion + " set Terminado = ?, FechaInicio = ?, FechaFinal = ?, Empleado = ? where Proyecto = ?";
                PreparedStatement pst = con.prepareStatement(sql);

                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                Date d = new Date();
                String fecha = sdf.format(d);

                pst.setString(1, "SI");
                pst.setString(2, fecha);
                pst.setString(3, fecha);
                pst.setString(4, empleado);
                pst.setString(5, plano);

                int n = pst.executeUpdate();

                if (n > 0) {
                    JOptionPane.showMessageDialog(null, "Datos Actualizados");
                } else {
                    JOptionPane.showMessageDialog(null, "Datos sin actualizar");
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al terminar plano en estacion: " + e, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void sendToCalidad(String plano, String proyecto, String empleado) {
        try {
            Connection con;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            String sql = "select * from calidad where Proyecto like '" + plano + "'";
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()) {
                //Si existe plano en calidad
                String sql2 = "update calidad set Tratamiento = ?, Terminado = ?, FechaInicio = ?, FechaFinal = ? where Proyecto = ?";
                PreparedStatement pst = con.prepareStatement(sql2);

                pst.setString(1, "NO");
                pst.setString(2, "NO");
                pst.setString(3, "");
                pst.setString(4, "");
                pst.setString(5, plano);

                int n = pst.executeUpdate();

                if (n > 0) {
                    JOptionPane.showMessageDialog(null, "Datos enviados a calidad");
                }

            } else {
                //Si no existe plano en calidad
                String sql2 = "insert into calidad (Tratamiento, Terminado, FechaInicio, FechaFinal, Proyecto, Plano, Cronometro, Prioridad, Empleado) values(?,?,?,?,?,?,?,?,?)";
                PreparedStatement pst = con.prepareStatement(sql2);

                pst.setString(1, "NO");
                pst.setString(2, "NO");
                pst.setString(3, "");
                pst.setString(4, "");
                pst.setString(5, plano);
                pst.setString(6, proyecto);
                pst.setString(7, "00:00");
                pst.setString(8, "60");
                pst.setString(9, empleado);

                int n = pst.executeUpdate();

                if (n > 0) {
                    JOptionPane.showMessageDialog(null, "Datos enviados a calidad");
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void sendToEstacion(String plano, String proyecto, String empleado, String estacion) {
        try {
            Connection con;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            String sql = "select * from " + estacion + " where Proyecto like '" + plano + "'";
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()) {
                String sql2 = "update " + estacion + " set Terminado = ?, FechaInicio = ?, FechaFinal = ? where Proyecto = ?";
                PreparedStatement pst = con.prepareStatement(sql2);

                pst.setString(1, "NO");
                pst.setString(2, "");
                pst.setString(3, "");
                pst.setString(4, plano);

                int n = pst.executeUpdate();

                if (n > 0) {
                    JOptionPane.showMessageDialog(null, "Datos enviados a " + estacion);
                }

            } else {
                String sql2 = "insert into " + estacion + " (Terminado, FechaInicio, FechaFinal, Proyecto, Plano, Cronometro, Prioridad, Empleado) values(?,?,?,?,?,?,?,?)";
                PreparedStatement pst = con.prepareStatement(sql2);

                pst.setString(1, "NO");
                pst.setString(2, "");
                pst.setString(3, "");
                pst.setString(4, plano);
                pst.setString(5, proyecto);
                pst.setString(6, "00:00");
                pst.setString(7, "60");
                pst.setString(8, empleado);

                int n = pst.executeUpdate();

                if (n > 0) {
                    JOptionPane.showMessageDialog(null, "Datos enviados a " + estacion);
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void terminarPlano(String plano, String proyecto, String empleado, String tiempo, String estacion, Connection con) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Date d = new Date();
            String fecha = sdf.format(d);
            Statement st = con.createStatement();
            String sql = "select * from " + estacion + " where Proyecto like '" + plano + "'";
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()) {
                //Si existe plano en calidad
                String sql2 = "update " + estacion + " set Terminado = ?, FechaInicio = ?, FechaFinal = ?, Cronometro = ? where Proyecto = ?";
                if (tiempo == null) {
                    sql2 = "update " + estacion + " set Terminado = ?, FechaInicio = ?, FechaFinal = ? where Proyecto = ?";
                }
                PreparedStatement pst = con.prepareStatement(sql2);

                pst.setString(1, "SI");
                pst.setString(2, fecha);
                pst.setString(3, fecha);
                if (tiempo != null) {
                    pst.setString(4, tiempo);
                    pst.setString(5, plano);
                } else {
                    pst.setString(4, plano);
                }

                int n = pst.executeUpdate();

                if (n == 0) {
                    JOptionPane.showMessageDialog(null, "Datos Incorrectos en " + estacion, "Error", JOptionPane.ERROR_MESSAGE);
                }

            } else {
                //Si no existe plano en calidad
                String sql2 = "insert into " + estacion + " (Terminado, FechaInicio, FechaFinal, Proyecto, Plano, Prioridad, Empleado) values(?,?,?,?,?,?,?)";
                if (tiempo != null) {
                    sql2 = "insert into " + estacion + " (Terminado, FechaInicio, FechaFinal, Proyecto, Plano, Prioridad, Empleado, Cronometro) values(?,?,?,?,?,?,?,?)";
                }
                PreparedStatement pst = con.prepareStatement(sql2);

                pst.setString(1, "SI");
                pst.setString(2, fecha);
                pst.setString(3, fecha);
                pst.setString(4, plano);
                pst.setString(5, proyecto);
                pst.setString(6, "10");
                pst.setString(7, empleado);
                if (tiempo != null) {
                    pst.setString(8, tiempo);
                }

                int n = pst.executeUpdate();

                if (n == 0) {
                    JOptionPane.showMessageDialog(null, "Datos Incorrectos en " + estacion, "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al terminar plano: " + e, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void actualizarPlanos(Connection con, String plano, String estacion) throws SQLException{
        String sql = "update planos set Estado = ? where Plano = ?";
        PreparedStatement pst = con.prepareStatement(sql);
        pst.setString(1, estacion);
        pst.setString(2, plano);
        
        int n = pst.executeUpdate();
        
        if (n < 1) {
            JOptionPane.showMessageDialog(null, "El plano que ingresaste no existe", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void transaccionTerminarPlano(Connection con, String plano, String proyecto, String cronometro, String estacion,String numEmpleado, String para) throws SQLException {
        String sql = "insert into " + estacion + " (Proyecto, Plano, FechaInicio, FechaFinal, Cronometro, Empleado, Para, Terminado) values (?,?,?,?,?,?,?,?)";
        PreparedStatement pst = con.prepareStatement(sql);
        
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date d = new Date();
        String fecha = sdf.format(d);
        
        pst.setString(1, plano);
        pst.setString(2, proyecto);
        pst.setString(3, fecha);
        pst.setString(4, fecha);
        pst.setString(5, cronometro);
        pst.setString(6, numEmpleado);
        pst.setString(7, para);
        pst.setString(8, "SI");
        
        int n = pst.executeUpdate();
        if (n > 0 && mostrarMensaje) {
            JOptionPane.showMessageDialog(null, "Transaccion realizada con exito");
        }
    }
}
