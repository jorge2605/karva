package pruebas;

import Conexiones.Conexion;
import Patrones.patronMaterial;
import Controlador.maquinados.revisarPlanos;
import VentanaEmergente.Inicio1.Espera;
import VentanaEmergente.Maquinados.CustomDocumentFilter;
import VentanaEmergente.Maquinados.IngresarTiempo;
import VentanaEmergente.Maquinados.ReporteMaquinados;
import VentanaEmergente.Maquinados.empleado;
import java.sql.Connection;
import java.awt.Color;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.KeyboardFocusManager;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import static java.lang.Thread.sleep;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.text.AbstractDocument;

public class Maquinados extends javax.swing.JInternalFrame implements ActionListener {

    public String numEmpleado;
    public Stack<JButton> btnEmpleado;
    public Stack<JPanel> pnlEmpleado;
    public Stack<String> numEmpleados;
    public Stack<String> nombreEmpleados;
    public int PROYECTO = 1;
    public int PLANO = 2;
    public empleado emp;
    public String nombre;
    public List<String> patrones;
    Espera espera = new Espera();
    public double Volumen;
    public double Densidad;
    public double Peso;
    public double precioEstimado;
    public double costoFinal;
    private final javax.swing.Timer timer = new javax.swing.Timer(100, e -> actualizarCronometros());
    private long[] inicio = new long[4];
    private long[] acumulado = new long[4];
    private boolean[] corriendo = new boolean[4];

    public void limpiarFormulario() {
        txtPlano2.setText("");
        txtProyecto.setText("");
        txtDim1.setText("");
        txtDim2.setText("");
        txtDim3.setText("");
        txtDim4.setText("");
        cmbMaterial.removeAllItems();
        //  txtMaterial.setText("");
        txtCantidad.setText("");
        txtComentarios.setText("");
        pnlRecti.setBackground(Color.white);
        pnlCnc.setBackground(Color.white);
        pnlFresa.setBackground(Color.white);
        pnlTorno.setBackground(Color.white);
    }

    public JButton addBoton(String nombre) {
        JButton boton = new javax.swing.JButton();
        boton.setBackground(new java.awt.Color(255, 255, 255));
        boton.setFont(new java.awt.Font("Lexend", 0, 12)); // NOI18N
        boton.setForeground(new java.awt.Color(0, 102, 204));
        boton.setText(nombre);
        boton.setBorder(null);
        boton.addActionListener(this);
        boton.setBorderPainted(false);
        boton.setContentAreaFilled(false);
        boton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        boton.setFocusPainted(false);
        return boton;
    }

    public void addPanel(JButton boton, int i) {
        pnlEmpleado.push(new JPanel());
        pnlEmpleado.get(i).setBackground(new java.awt.Color(240, 240, 240));
        pnlEmpleado.get(i).add(boton);
        panelGastos.add(pnlEmpleado.get(i));
    }

    public void addEmpleados(String empleado) {
        try {
            panelGastos.removeAll();
            revalidate();
            repaint();
            Connection con;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            String sql = "SELECT * FROM towi.empleadoscheck where NumEmpleado like '" + empleado + "'";
            ResultSet rs = st.executeQuery(sql);
            numEmpleados = new Stack<>();
            nombreEmpleados = new Stack<>();
            while (rs.next()) {
                String nombre = rs.getString("Nombre");
                String numero = rs.getString("NumEmpleado");
                numEmpleados.push(numero);
                nombreEmpleados.push(nombre);
            }
            if (numEmpleados.isEmpty()) {
                JOptionPane.showMessageDialog(this, "El numero que ingresaste no existe", "Error", JOptionPane.ERROR_MESSAGE);
                getNumEmpleado();
            } else {
                btnEmpleado = new Stack<>();
                pnlEmpleado = new Stack<>();
                for (int i = 0; i < numEmpleados.size(); i++) {
                    btnEmpleado.push(addBoton(nombreEmpleados.get(i)));
                    addPanel(btnEmpleado.get(i), i);
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "ERROR: " + e, "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void setEmpleado() {
        try {
            Connection con;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            String sql = "select * from registroempleados where NumEmpleado like '" + numEmpleado + "'";
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                lblEmpleado.setText(rs.getString("Nombre") + " " + rs.getString("Apellido"));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error: " + e, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public String getNumEmpleado() {
        String empleado;
        JFrame f = (JFrame) JOptionPane.getFrameForComponent(this);
        emp = new empleado(f, true);
        emp.btnX.addActionListener(this);
        emp.setLocationRelativeTo(f);
        empleado = emp.getEmpleado();
        numEmpleado = empleado;
        setEmpleado();
        return empleado;
    }

    public Color setBack(JComponent comp) {
        Color color;
        if (comp.getBackground().equals(Color.white)) {
            color = (Color.green);
//            JFrame f = (JFrame) JOptionPane.getFrameForComponent(this);
//            IngresarTiempo ingresar = new IngresarTiempo(f, true);
//            ingresar.setLocationRelativeTo(f);
//            String tiempo = ingresar.getTiempo();
//            if(tiempo == null) {
//                JOptionPane.showMessageDialog(this, "Debes ingresar tiempo mayor a 00:00", "Advertencia", JOptionPane.WARNING_MESSAGE);
//                color = Color.white;
//            } else {
//                if(!tiempo.equals(":")){
//                    label.setText(tiempo);
//                }else{
//                    color = Color.white;
//                }
//            }
        } else {
            color = (Color.white);
        }
        return color;
    }

    public String obtenerCaracter(String plano) {
        String texto = plano;

        Pattern pattern = Pattern.compile("[^0-9a-zA-Z]");
        Matcher matcher = pattern.matcher(texto);

        while (matcher.find()) {
            return matcher.group();
        }
        return null;
    }

    public String verificarNomenclatura(String plano, int seleccion) {
        String caracter = obtenerCaracter(plano);
        String partes[] = plano.split(caracter);
        String proyecto = plano.substring(0, plano.indexOf(caracter));
        String plano3 = plano.substring(plano.indexOf(caracter), plano.length());
        if (proyecto.length() >= 3) {
            try {
                Connection con;
                Conexion con1 = new Conexion();
                con = con1.getConnection();
                Statement st = con.createStatement();
                String sql = "select Proyecto from proyectos where Proyecto like '" + proyecto + "%'";
                ResultSet rs = st.executeQuery(sql);
                while (rs.next()) {
                    proyecto = rs.getString("Proyecto");
                }
                if (proyecto == null) {
                    return null;
                } else {
                    if (seleccion == PROYECTO) {
                        return proyecto;
                    } else {
                        if (partes.length >= 3) {
                            return plano;
                        }
                        return proyecto + plano3;
                    }
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "ERROR " + e, "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Datos no validos [0,3]", "Error", JOptionPane.ERROR_MESSAGE);
        }
        return null;
    }

    public void verPlanoPorProyecto(String plano) {
        String proyecto = verificarNomenclatura(plano, PROYECTO);
        if (proyecto.length() < 6) {
            JOptionPane.showMessageDialog(this, "Este Proyecto no existe", "Error", JOptionPane.ERROR_MESSAGE);
            limpiarFormulario();
        } else {
            txtProyecto.setText(proyecto);
            txtPlano2.setText(plano);
        }
    }

    private void traerMateriales() {
        //Limpiar el ComboBox antes de cargar los nuevos datos
        cmbMaterial.removeAllItems();
        try {
            Connection con = new Conexion().getConnection();
            Statement st = con.createStatement();
            String sql = "SELECT m.Material FROM Materiales m INNER JOIN (SELECT Material, MAX(ID) as MaxID "
                    + "FROM Materiales GROUP BY Material) ultimos ON m.ID = ultimos.MaxID ORDER BY m.Material ASC";
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                cmbMaterial.addItem(rs.getString("Material"));
            }
            rs.close();
            st.close();
            con.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar materiales: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean iterarletras(String input, String letras) {
        for (char c : letras.toCharArray()) {
            if (!input.contains(String.valueOf(c))) {
                return false;
            }
        }
        return true;
    }

    private String iterarMateriales(String texto) throws SQLException {
        String input = texto.toLowerCase().replaceAll("[^a-z0-9áéíóúüñ\\s]", " ");
        List<patronMaterial> lista = patronesBD();

        lista.sort((a, b) -> {
            int maxA = a.getPatrones().stream().mapToInt(String::length).max().orElse(0);
            int maxB = b.getPatrones().stream().mapToInt(String::length).max().orElse(0);
            return Integer.compare(maxB, maxA);
        });

        for (patronMaterial material : lista) {
            for (String patron : material.getPatrones()) {
                patron = patron.trim().toLowerCase();
                if (patron.isEmpty()) {
                    continue;
                }

                if (patron.matches(".*\\d.*")) {
                    Pattern pattern = Pattern.compile("\\b" + Pattern.quote(patron) + "\\b", Pattern.CASE_INSENSITIVE);
                    Matcher matcher = pattern.matcher(input);
                    if (matcher.find()) {
                        return material.getNombre();
                    }
                } else {
                    if (iterarletras(input, patron)) {
                        return material.getNombre();
                    }
                }
            }
        }

        return texto.toUpperCase(); // Si no encontró coincidencias
    }

    public List<patronMaterial> patronesBD() throws SQLException {
        List<patronMaterial> lista = new ArrayList<>();
        try {
            Connection con = new Conexion().getConnection();
            Statement st = con.createStatement();
            String sql = "SELECT nombre_material, patron FROM patron_material";
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                String nombre = rs.getString("nombre_material");
                String patrones = rs.getString("patron");
                lista.add(new patronMaterial(nombre, patrones));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public void verPlano(String plano) {
        String plan = plano;
        try {
            Connection con;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            traerMateriales();
            Statement st = con.createStatement();
            String sql = "select Plano, Proyecto, Cantidad, Material from planos where Plano like '" + plano + "'";
            ResultSet rs = st.executeQuery(sql);
            plano = null;
            while (rs.next()) {
                plano = rs.getString("Plano");
                txtPlano2.setText(plano);
                txtProyecto.setText(rs.getString("Proyecto"));
                txtCantidad.setText(rs.getString("Cantidad"));
                //txtMaterial.setText(rs.getString("Material"));
                String materialPlano = rs.getString("Material");
                String materialDetectado = iterarMateriales(materialPlano);
                boolean encontrado = false;
                for (int i = 0; i < cmbMaterial.getItemCount(); i++) {
                    if (cmbMaterial.getItemAt(i).equalsIgnoreCase(materialDetectado)) {
                        cmbMaterial.setSelectedItem(cmbMaterial.getItemAt(i));
                        encontrado = true;
                        break;
                    }
                }
                // Si no se encontró, dejar el combo en blanco (sin selección)
                if (!encontrado) {
                    cmbMaterial.setSelectedIndex(-1); // Esto deja el combo sin seleccionar nada
                }
            }
            if (plano == null) {
                JOptionPane.showMessageDialog(this, "Este plano no existe", "Advertencia", JOptionPane.WARNING_MESSAGE);
                verPlanoPorProyecto(plan);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error: " + e, "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    public Stack<String> extraerBotones() {
        Stack<String> botones = new Stack<>();
        if (pnlCnc.getBackground().equals(Color.green)) {
            botones.push("Cnc");
        }
        if (pnlFresa.getBackground().equals(Color.green)) {
            botones.push("Fresadora");
        }
        if (pnlTorno.getBackground().equals(Color.green)) {
            botones.push("Torno");
        }
        if (pnlRecti.getBackground().equals(Color.green)) {
            botones.push("Rectificado");
        }
        return botones;
    }

    public String getDimensiones() {
        switch (cmbDim.getSelectedIndex()) {
            case 0:
                if (txtDim1.getText().equals("") || txtDim2.getText().equals("")) {
                    JOptionPane.showMessageDialog(this, "Debes ingresar las 2 dimensiones de tu pieza", "Advertencia", JOptionPane.WARNING_MESSAGE);
                } else {
                    return txtDim1.getText() + "x" + txtDim2.getText();
                }
                break;
            case 1:
                if (txtDim1.getText().equals("") || txtDim2.getText().equals("") || txtDim3.getText().equals("")) {
                    JOptionPane.showMessageDialog(this, "Debes ingresar las 3 dimensiones de tu pieza", "Advertencia", JOptionPane.WARNING_MESSAGE);
                } else {
                    return txtDim1.getText() + "x" + txtDim2.getText() + "x" + txtDim3.getText();
                }
                break;
            case 2:
                if (txtDim1.getText().equals("") || txtDim2.getText().equals("") || txtDim3.getText().equals("") || txtDim4.getText().equals("")) {
                    JOptionPane.showMessageDialog(this, "Debes ingresar las 4 dimensiones de tu pieza", "Advertencia", JOptionPane.WARNING_MESSAGE);
                } else {
                    return txtDim1.getText() + "x" + txtDim2.getText() + "x" + txtDim3.getText() + "x" + txtDim4.getText();
                }
                break;
            default:
                break;
        }
        return null;
    }

    public double obtenerCostos() {
        double d1 = parseDoubleSafe(txtDim1.getText());
        double d2 = parseDoubleSafe(txtDim2.getText());
        double d3 = parseDoubleSafe(txtDim3.getText());
        double d4 = parseDoubleSafe(txtDim4.getText());
        Volumen = 0;
        if (d1 > 0 && d2 > 0 && d3 == 0 && d4 == 0) {
            Volumen = d1 * d2;
        } else if (d1 > 0 && d2 > 0 && d3 > 0 && d4 == 0) {
            Volumen = d1 * d2 * d3;
        } else if (d1 > 0 && d2 > 0 && d3 > 0 && d4 > 0) {
            Volumen = d1 * d2 * d3 * d4;
        }
        String Material = (String) cmbMaterial.getSelectedItem();
        try {
            Connection con;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            String sql = "Select Densidad, precio FROM materiales WHERE material = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, Material);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                Densidad = rs.getDouble("Densidad");
                precioEstimado = rs.getDouble("Precio");
                Peso = Volumen * Densidad;
                costoFinal = Peso * precioEstimado;
                return costoFinal;
            } else {
                JOptionPane.showMessageDialog(null, "No se encontraron datos del material", "Error", JOptionPane.ERROR_MESSAGE);
                return 0;
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al consultar material: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return 0;
        }
    }

    private double parseDoubleSafe(String val) {
        if (val == null || val.trim().isEmpty()) {
            return 0;
        }
        try {
            return Double.parseDouble(val);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public void insertarHttp(Connection con) throws SQLException {
        String dimensiones = getDimensiones();
        if (dimensiones != null) {
            obtenerCostos();
            String sql = "insert into htpp (Fecha, NumEmpleado, Proyecto, Hora, Notas, Departamento, Dimensiones, Material,Costo, Cantidad, Maquina,Plano) values(?,?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement pst = con.prepareStatement(sql);
            Stack<String> botones = extraerBotones();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date d = new Date();
            String fecha = sdf.format(d);
            int n = 0;

            for (int i = 0; i < botones.size(); i++) {
                String hora = "";
                switch (botones.get(i)) {
                    case "Cnc":
                        hora = lblCnc.getText();
                        break;
                    case "Fresadora":
                        hora = lblFresa.getText();
                        break;
                    case "Torno":
                        hora = lblTorno.getText();
                        break;
                    case "Rectificado":
                        hora = lblRecti.getText();
                        break;
                    default:
                        break;
                }
                pst.setString(1, fecha);
                pst.setString(2, this.numEmpleado);
                pst.setString(3, txtProyecto.getText());
                pst.setString(4, hora);
                pst.setString(5, txtComentarios.getText());
                pst.setInt(6, 2);
                pst.setString(7, dimensiones);
                //pst.setString(8, txtMaterial.getText());
                pst.setString(8, (String) cmbMaterial.getSelectedItem());
                pst.setDouble(9, costoFinal);
                pst.setString(10, txtCantidad.getText());
                pst.setString(11, botones.get(i));
                pst.setString(12, txtPlano2.getText());

                n += pst.executeUpdate();
            }

            if (n < 1) {
                JOptionPane.showMessageDialog(this, "Error al guardar datos HTPP", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public final void visiblesFalse() {
        txtDim1.setVisible(false);
        txtDim2.setVisible(false);
        txtDim3.setVisible(false);
        txtDim4.setVisible(false);
        lblX1.setVisible(false);
        lblX2.setVisible(false);
        lblX3.setVisible(false);
    }

    public void transferirFoco(JTextField text, char cha) {
        if (cha == 'x' || cha == 'X') {
            text.transferFocus();
        }
    }

    public int verificarDimensiones() {
        int cont = 0;
        if (txtDim1.isVisible()) {
            if (!txtDim1.getText().equals("")) {
                cont++;
            }
        }
        if (txtDim2.isVisible()) {
            if (!txtDim2.getText().equals("")) {
                cont++;
            }
        }
        if (txtDim3.isVisible()) {
            if (!txtDim3.getText().equals("")) {
                cont++;
            }
        }
        if (txtDim4.isVisible()) {
            if (!txtDim4.getText().equals("")) {
                cont++;
            }
        }
        return cont;
    }

    public final boolean verificarVolumen() {
        double dim1;
        try {
            dim1 = Double.parseDouble(txtDim1.getText());
        } catch (NumberFormatException e) {
            dim1 = 0;
        }
        double dim2;
        try {
            dim2 = Double.parseDouble(txtDim1.getText());
        } catch (NumberFormatException e) {
            dim2 = 0;
        }
        double dim3;
        try {
            dim3 = Double.parseDouble(txtDim1.getText());
        } catch (NumberFormatException e) {
            dim3 = 0;
        }
        double dim4;
        try {
            dim4 = Double.parseDouble(txtDim1.getText());
        } catch (NumberFormatException e) {
            dim4 = 0;
        }

        return dim1 < 60 || dim2 < 60 || dim3 < 60 || dim4 < 60;
    }

    public void terminarPlano(boolean calidad) {
        int opc;
        if (!verificarVolumen()) {
            opc = JOptionPane.showConfirmDialog(this, "Las dimensiones estan arriba de los esperado, ¿Estas seguro de continuar?", "Advertencia", JOptionPane.WARNING_MESSAGE);
        } else {
            opc = 0;
        }
        if (opc == 0) {
            if (txtPlano2.getText().equals("")) {
                JOptionPane.showMessageDialog(this, "Debes llenar el campo de plano", "Advertencia", JOptionPane.WARNING_MESSAGE);
            } else if (txtProyecto.getText().equals("")) {
                JOptionPane.showMessageDialog(this, "Debes llenar el campo de proyecto", "Advertencia", JOptionPane.WARNING_MESSAGE);
            } else if (txtCantidad.getText().equals("")) {
                JOptionPane.showMessageDialog(this, "Debes llenar el campo de cantidad", "Advertencia", JOptionPane.WARNING_MESSAGE);
            } else if (cmbMaterial.getSelectedItem().equals("")) {
                JOptionPane.showMessageDialog(this, "Debes llenar el campo de material", "Advertencia", JOptionPane.WARNING_MESSAGE);
            } else if (extraerBotones().toString().equals("[]")) {
                JOptionPane.showMessageDialog(this, "Debes seleccionar por lo menos una maquina", "Advertencia", JOptionPane.WARNING_MESSAGE);
            } else if (verificarDimensiones() < 2) {
                JOptionPane.showMessageDialog(this, "Debes ingresar por lo menos 2 dimensiones", "Advertencia", JOptionPane.WARNING_MESSAGE);
            } else {
                try {
                    Connection con;
                    Conexion con1 = new Conexion();
                    con = con1.getConnection();
                    //Insertar datos en HTTP
                    insertarHttp(con);

                    KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
                    manager.focusNextComponent();
                    revisarPlanos rev = new revisarPlanos();
                    Stack<String> botones = extraerBotones();
                    for (int i = 0; i < botones.size(); i++) {
                        String hora = "";
                        switch (botones.get(i)) {
                            case "Cnc":
                                hora = lblCnc.getText();
                                break;
                            case "Fresadora":
                                hora = lblFresa.getText();
                                break;
                            case "Torno":
                                hora = lblTorno.getText();
                                break;
                            case "Rectificado":
                                hora = lblRecti.getText();
                                break;
                            default:
                                break;
                        }
                        rev.transaccionTerminarPlano(con, txtPlano2.getText(), txtProyecto.getText(), hora, botones.get(i).toLowerCase(), numEmpleado, "ACABADOS");
                    }
                    if (calidad) {
                        rev.actualizarPlanos(con, txtPlano2.getText(), "ACABADOS");
                    } else {
                        rev.actualizarPlanos(con, txtPlano2.getText(), "MAQUINADOS");
                    }
                    limpiarFormulario();

                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(this, "Error: " + e, "Error", JOptionPane.ERROR_MESSAGE);
                }
                this.numEmpleado = getNumEmpleado();
            }
        }
    }

    private void iniciarCronometro(int numero) {
        if (!corriendo[numero]) {
            inicio[numero] = System.currentTimeMillis();
            corriendo[numero] = true;
            if (!timer.isRunning()) {
                timer.start();
            }
        }
    }

    private void pausarCronometro(int numero) {
        if (corriendo[numero]) {
            acumulado[numero] += System.currentTimeMillis() - inicio[numero] + 360000;
            corriendo[numero] = false;
        }
        detenerTimerSiNoHayCronometros();
    }

    private void reanudarCronometro(int numero) {
        if (!corriendo[numero]) {
            inicio[numero] = System.currentTimeMillis();
            corriendo[numero] = true;
            if (!timer.isRunning()) {
                timer.start();
            }
        }
    }

    private void actualizarCronometros() {
        JLabel[] labels = {
            lblCnc,
            lblFresa,
            lblRecti,
            lblTorno
        };
        long ahora = System.currentTimeMillis();
        for (int i = 0; i < 4; i++) {
            if (corriendo[i]) {
                long tiempo = acumulado[i] + (ahora - inicio[i]);
                labels[i].setText(formatearTiempo(tiempo));
            }
        }
    }

    private String formatearTiempo(long milisegundos) {
        long segundos = milisegundos / 1000;
        long horas = segundos / 3600;
        long minutos = (segundos % 3600) / 60;
        long segundosRestantes = segundos % 60;
        return String.format("%02d:%02d:%02d", horas, minutos, segundosRestantes);
    }

    private void detenerTimerSiNoHayCronometros() {
        for (boolean activo : corriendo) {
            if (activo) {
                return;
            }
        }
        timer.stop();
    }

    public Maquinados(String numEmpleado) {
        initComponents();
        this.numEmpleado = numEmpleado;
        jScrollPane5.getVerticalScrollBar().setUnitIncrement(15);
        SwingUtilities.invokeLater(() -> getNumEmpleado());
        ((AbstractDocument) txtDim1.getDocument()).setDocumentFilter(new CustomDocumentFilter());
        ((AbstractDocument) txtDim2.getDocument()).setDocumentFilter(new CustomDocumentFilter());
        ((AbstractDocument) txtDim3.getDocument()).setDocumentFilter(new CustomDocumentFilter());
        ((AbstractDocument) txtDim4.getDocument()).setDocumentFilter(new CustomDocumentFilter());
        ((javax.swing.plaf.basic.BasicInternalFrameUI) this.getUI()).setNorthPane(null);
        visiblesFalse();
        SwingUtilities.invokeLater(() -> txtPlano.requestFocusInWindow());
        txtDim1.setVisible(true);
        txtDim2.setVisible(true);
        lblX1.setVisible(true);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel1 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        pan = new javax.swing.JPanel();
        panelSalir = new javax.swing.JPanel();
        lblSalir = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        panelGastos = new javax.swing.JPanel();
        panelReporte = new scrollPane.PanelRound();
        jPanel7 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        pnlEstacion = new javax.swing.JPanel();
        btnEstacion = new javax.swing.JButton();
        pnlCalidad = new javax.swing.JPanel();
        btnCalidad = new javax.swing.JButton();
        jPanel9 = new javax.swing.JPanel();
        pnlPlano = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtPlano = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jPanel19 = new javax.swing.JPanel();
        pnlRecti = new javax.swing.JPanel();
        btnRecti = new javax.swing.JButton();
        pnlTorno = new javax.swing.JPanel();
        btnTorno = new javax.swing.JButton();
        pnlCnc = new javax.swing.JPanel();
        btnCnc = new javax.swing.JButton();
        pnlFresa = new javax.swing.JPanel();
        btnFresa = new javax.swing.JButton();
        lblRecti = new javax.swing.JLabel();
        lblTorno = new javax.swing.JLabel();
        lblCnc = new javax.swing.JLabel();
        lblFresa = new javax.swing.JLabel();
        jPanel11 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtPlano2 = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtProyecto = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        cmbMaterial = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        txtCantidad = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtComentarios = new javax.swing.JTextField();
        panelRound1 = new scrollPane.PanelRound();
        jButton1 = new javax.swing.JButton();
        lblEmpleado = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        cmbDim = new javax.swing.JComboBox<>();
        jPanel10 = new javax.swing.JPanel();
        txtDim1 = new javax.swing.JTextField();
        lblX1 = new javax.swing.JLabel();
        txtDim2 = new javax.swing.JTextField();
        lblX2 = new javax.swing.JLabel();
        txtDim3 = new javax.swing.JTextField();
        lblX3 = new javax.swing.JLabel();
        txtDim4 = new javax.swing.JTextField();

        setBorder(null);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new java.awt.BorderLayout(10, 10));

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setLayout(new java.awt.BorderLayout());

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 50, 5));

        jLabel12.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(0, 165, 252));
        jLabel12.setText("Maquinados Reporte de produccion");
        jPanel5.add(jLabel12);

        jPanel4.add(jPanel5, java.awt.BorderLayout.CENTER);

        pan.setBackground(new java.awt.Color(255, 255, 255));

        panelSalir.setBackground(new java.awt.Color(255, 255, 255));

        lblSalir.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
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

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setLayout(new java.awt.BorderLayout());

        jScrollPane5.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 1, true));
        jScrollPane5.setMaximumSize(new java.awt.Dimension(200, 70));
        jScrollPane5.setPreferredSize(new java.awt.Dimension(102, 65));

        panelGastos.setBackground(new java.awt.Color(255, 255, 255));
        panelGastos.setMaximumSize(new java.awt.Dimension(150, 100));
        panelGastos.setPreferredSize(new java.awt.Dimension(100, 60));

        panelReporte.setBackground(new java.awt.Color(255, 102, 0));
        panelReporte.setRoundBottomRight(20);
        panelReporte.setRoundTopLeft(20);
        panelReporte.setRoundTopRight(20);
        panelReporte.setLayout(new java.awt.BorderLayout());
        panelGastos.add(panelReporte);

        jScrollPane5.setViewportView(panelGastos);

        jPanel3.add(jScrollPane5, java.awt.BorderLayout.CENTER);

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        jLabel2.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText("Fecha:");
        jPanel7.add(jLabel2);

        jLabel3.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText("14-02-2024");
        jPanel7.add(jLabel3);

        jPanel3.add(jPanel7, java.awt.BorderLayout.EAST);

        jPanel2.add(jPanel3, java.awt.BorderLayout.PAGE_START);

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));
        jPanel8.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 100, 5));

        pnlEstacion.setBackground(new java.awt.Color(255, 255, 255));

        btnEstacion.setBackground(new java.awt.Color(255, 255, 255));
        btnEstacion.setFont(new java.awt.Font("Lexend", 1, 24)); // NOI18N
        btnEstacion.setForeground(new java.awt.Color(255, 102, 0));
        btnEstacion.setText("Terminar en estacion");
        btnEstacion.setBorder(null);
        btnEstacion.setBorderPainted(false);
        btnEstacion.setContentAreaFilled(false);
        btnEstacion.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEstacion.setFocusPainted(false);
        btnEstacion.setNextFocusableComponent(txtPlano);
        btnEstacion.setPreferredSize(new java.awt.Dimension(280, 30));
        btnEstacion.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnEstacionMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnEstacionMouseExited(evt);
            }
        });
        btnEstacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEstacionActionPerformed(evt);
            }
        });
        pnlEstacion.add(btnEstacion);

        jPanel8.add(pnlEstacion);

        pnlCalidad.setBackground(new java.awt.Color(255, 255, 255));

        btnCalidad.setBackground(new java.awt.Color(255, 255, 255));
        btnCalidad.setFont(new java.awt.Font("Lexend", 1, 24)); // NOI18N
        btnCalidad.setForeground(new java.awt.Color(0, 102, 255));
        btnCalidad.setText("Enviar a Acabados");
        btnCalidad.setBorder(null);
        btnCalidad.setBorderPainted(false);
        btnCalidad.setContentAreaFilled(false);
        btnCalidad.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCalidad.setFocusPainted(false);
        btnCalidad.setNextFocusableComponent(txtPlano);
        btnCalidad.setPreferredSize(new java.awt.Dimension(280, 30));
        btnCalidad.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnCalidadMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnCalidadMouseExited(evt);
            }
        });
        btnCalidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCalidadActionPerformed(evt);
            }
        });
        pnlCalidad.add(btnCalidad);

        jPanel8.add(pnlCalidad);

        jPanel2.add(jPanel8, java.awt.BorderLayout.SOUTH);

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));
        jPanel9.setLayout(new java.awt.BorderLayout());

        pnlPlano.setBackground(new java.awt.Color(255, 255, 255));
        pnlPlano.setLayout(new java.awt.GridBagLayout());

        jLabel1.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 102, 204));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Ingresa numero de Plano");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 6;
        gridBagConstraints.insets = new java.awt.Insets(20, 0, 2, 0);
        pnlPlano.add(jLabel1, gridBagConstraints);

        txtPlano.setFont(new java.awt.Font("Lexend", 0, 18)); // NOI18N
        txtPlano.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtPlano.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(153, 153, 153)));
        txtPlano.setPreferredSize(new java.awt.Dimension(400, 30));
        txtPlano.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtPlanoFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtPlanoFocusLost(evt);
            }
        });
        txtPlano.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPlanoActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 336;
        gridBagConstraints.ipady = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        pnlPlano.add(txtPlano, gridBagConstraints);

        jLabel16.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(102, 0, 0));
        jLabel16.setText("*");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTH;
        pnlPlano.add(jLabel16, gridBagConstraints);

        jLabel10.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(0, 102, 204));
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel10.setText("Seleccionar maquina");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 31;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(24, 9, 0, 0);
        pnlPlano.add(jLabel10, gridBagConstraints);

        jPanel19.setBackground(new java.awt.Color(255, 255, 255));
        jPanel19.setPreferredSize(new java.awt.Dimension(414, 40));
        java.awt.GridBagLayout jPanel19Layout = new java.awt.GridBagLayout();
        jPanel19Layout.columnWeights = new double[] {1.0, 1.0, 1.0, 1.0};
        jPanel19.setLayout(jPanel19Layout);

        pnlRecti.setBackground(new java.awt.Color(255, 255, 255));

        btnRecti.setBackground(new java.awt.Color(255, 255, 255));
        btnRecti.setFont(new java.awt.Font("Lexend", 1, 12)); // NOI18N
        btnRecti.setText("Rectificadora");
        btnRecti.setBorder(null);
        btnRecti.setBorderPainted(false);
        btnRecti.setContentAreaFilled(false);
        btnRecti.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnRecti.setFocusPainted(false);
        btnRecti.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRectiActionPerformed(evt);
            }
        });
        pnlRecti.add(btnRecti);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 1;
        jPanel19.add(pnlRecti, gridBagConstraints);

        pnlTorno.setBackground(new java.awt.Color(255, 255, 255));

        btnTorno.setBackground(new java.awt.Color(255, 255, 255));
        btnTorno.setFont(new java.awt.Font("Lexend", 1, 12)); // NOI18N
        btnTorno.setText("Torno");
        btnTorno.setBorder(null);
        btnTorno.setBorderPainted(false);
        btnTorno.setContentAreaFilled(false);
        btnTorno.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnTorno.setFocusPainted(false);
        btnTorno.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTornoActionPerformed(evt);
            }
        });
        pnlTorno.add(btnTorno);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 1;
        jPanel19.add(pnlTorno, gridBagConstraints);

        pnlCnc.setBackground(new java.awt.Color(255, 255, 255));

        btnCnc.setBackground(new java.awt.Color(255, 255, 255));
        btnCnc.setFont(new java.awt.Font("Lexend", 1, 12)); // NOI18N
        btnCnc.setText("Cnc");
        btnCnc.setBorder(null);
        btnCnc.setBorderPainted(false);
        btnCnc.setContentAreaFilled(false);
        btnCnc.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCnc.setFocusPainted(false);
        btnCnc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCncActionPerformed(evt);
            }
        });
        pnlCnc.add(btnCnc);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 1;
        jPanel19.add(pnlCnc, gridBagConstraints);

        pnlFresa.setBackground(new java.awt.Color(255, 255, 255));

        btnFresa.setBackground(new java.awt.Color(255, 255, 255));
        btnFresa.setFont(new java.awt.Font("Lexend", 1, 12)); // NOI18N
        btnFresa.setText("Fresadora");
        btnFresa.setBorder(null);
        btnFresa.setBorderPainted(false);
        btnFresa.setContentAreaFilled(false);
        btnFresa.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnFresa.setFocusPainted(false);
        btnFresa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFresaActionPerformed(evt);
            }
        });
        pnlFresa.add(btnFresa);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 1;
        jPanel19.add(pnlFresa, gridBagConstraints);

        lblRecti.setFont(new java.awt.Font("Roboto", 1, 24)); // NOI18N
        lblRecti.setForeground(new java.awt.Color(0, 102, 204));
        lblRecti.setText("00:00:00");
        jPanel19.add(lblRecti, new java.awt.GridBagConstraints());

        lblTorno.setFont(new java.awt.Font("Roboto", 1, 24)); // NOI18N
        lblTorno.setForeground(new java.awt.Color(0, 102, 204));
        lblTorno.setText("00:00:00");
        jPanel19.add(lblTorno, new java.awt.GridBagConstraints());

        lblCnc.setFont(new java.awt.Font("Roboto", 1, 24)); // NOI18N
        lblCnc.setForeground(new java.awt.Color(0, 102, 204));
        lblCnc.setText("00:00:00");
        jPanel19.add(lblCnc, new java.awt.GridBagConstraints());

        lblFresa.setFont(new java.awt.Font("Roboto", 1, 24)); // NOI18N
        lblFresa.setForeground(new java.awt.Color(0, 102, 204));
        lblFresa.setText("00:00:00");
        jPanel19.add(lblFresa, new java.awt.GridBagConstraints());

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.gridheight = 3;
        gridBagConstraints.ipadx = 322;
        gridBagConstraints.ipady = 11;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(7, 9, 0, 0);
        pnlPlano.add(jPanel19, gridBagConstraints);

        jPanel9.add(pnlPlano, java.awt.BorderLayout.PAGE_START);

        jPanel11.setBackground(new java.awt.Color(255, 255, 255));
        jPanel11.setLayout(new java.awt.GridBagLayout());

        jLabel14.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(102, 0, 0));
        jLabel14.setText("*");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 13;
        gridBagConstraints.gridheight = 2;
        jPanel11.add(jLabel14, gridBagConstraints);

        jLabel15.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(102, 0, 0));
        jLabel15.setText("*");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 21;
        gridBagConstraints.gridheight = 2;
        jPanel11.add(jLabel15, gridBagConstraints);

        jLabel18.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(102, 0, 0));
        jLabel18.setText("*");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridheight = 2;
        jPanel11.add(jLabel18, gridBagConstraints);

        jLabel4.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 102, 204));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setText("Plano:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 21;
        gridBagConstraints.ipadx = 52;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(19, 9, 0, 0);
        jPanel11.add(jLabel4, gridBagConstraints);

        txtPlano2.setEditable(false);
        txtPlano2.setBackground(new java.awt.Color(255, 255, 255));
        txtPlano2.setFont(new java.awt.Font("Lexend", 0, 18)); // NOI18N
        txtPlano2.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtPlano2.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(204, 204, 204)));
        txtPlano2.setEnabled(false);
        txtPlano2.setPreferredSize(new java.awt.Dimension(300, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 21;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.gridheight = 3;
        gridBagConstraints.ipadx = 487;
        gridBagConstraints.ipady = 17;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(7, 9, 0, 0);
        jPanel11.add(txtPlano2, gridBagConstraints);

        jLabel5.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 102, 204));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel5.setText("Proyecto:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.ipadx = 29;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(14, 9, 0, 0);
        jPanel11.add(jLabel5, gridBagConstraints);

        txtProyecto.setEditable(false);
        txtProyecto.setBackground(new java.awt.Color(255, 255, 255));
        txtProyecto.setFont(new java.awt.Font("Lexend", 0, 18)); // NOI18N
        txtProyecto.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtProyecto.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(204, 204, 204)));
        txtProyecto.setEnabled(false);
        txtProyecto.setPreferredSize(new java.awt.Dimension(300, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.gridheight = 3;
        gridBagConstraints.ipadx = 487;
        gridBagConstraints.ipady = 17;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(2, 9, 0, 0);
        jPanel11.add(txtProyecto, gridBagConstraints);

        jLabel7.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 102, 204));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel7.setText("Material:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 13;
        gridBagConstraints.ipadx = 34;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 9, 0, 0);
        jPanel11.add(jLabel7, gridBagConstraints);

        cmbMaterial.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        cmbMaterial.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            @Override
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
                traerMateriales(); // Llama al método que carga los datos
            }
            @Override
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {}
            @Override
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {}
        });
        cmbMaterial.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbMaterialActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 13;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(1, 84, 0, 0);
        jPanel11.add(cmbMaterial, gridBagConstraints);

        jLabel8.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 102, 204));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel8.setText("Cantidad:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 16;
        gridBagConstraints.ipadx = 29;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(14, 9, 0, 0);
        jPanel11.add(jLabel8, gridBagConstraints);

        txtCantidad.setFont(new java.awt.Font("Lexend", 0, 14)); // NOI18N
        txtCantidad.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCantidad.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(204, 204, 204)));
        txtCantidad.setNextFocusableComponent(txtComentarios);
        txtCantidad.setPreferredSize(new java.awt.Dimension(300, 30));
        txtCantidad.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCantidadFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCantidadFocusLost(evt);
            }
        });
        txtCantidad.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCantidadKeyTyped(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 16;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 487;
        gridBagConstraints.ipady = 22;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(2, 9, 0, 0);
        jPanel11.add(txtCantidad, gridBagConstraints);

        jLabel9.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 102, 204));
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel9.setText("Comentarios:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 18;
        gridBagConstraints.ipadx = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(14, 9, 0, 0);
        jPanel11.add(jLabel9, gridBagConstraints);

        txtComentarios.setFont(new java.awt.Font("Lexend", 0, 14)); // NOI18N
        txtComentarios.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtComentarios.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(204, 204, 204)));
        txtComentarios.setPreferredSize(new java.awt.Dimension(300, 30));
        txtComentarios.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtComentariosFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtComentariosFocusLost(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 18;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 487;
        gridBagConstraints.ipady = 22;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(2, 9, 0, 0);
        jPanel11.add(txtComentarios, gridBagConstraints);

        panelRound1.setBackground(new java.awt.Color(255, 0, 0));
        panelRound1.setRoundBottomRight(20);
        panelRound1.setRoundTopLeft(20);
        panelRound1.setRoundTopRight(20);
        panelRound1.setLayout(new java.awt.BorderLayout());

        jButton1.setBackground(new java.awt.Color(255, 255, 255));
        jButton1.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/pdf_blanco_32.png"))); // NOI18N
        jButton1.setText("Descargar Pdf");
        jButton1.setBorder(null);
        jButton1.setBorderPainted(false);
        jButton1.setContentAreaFilled(false);
        jButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton1.setFocusPainted(false);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        panelRound1.add(jButton1, java.awt.BorderLayout.CENTER);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.ipadx = 80;
        gridBagConstraints.ipady = 13;
        jPanel11.add(panelRound1, gridBagConstraints);

        lblEmpleado.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        lblEmpleado.setForeground(new java.awt.Color(51, 51, 51));
        lblEmpleado.setText("Empleado");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 1;
        jPanel11.add(lblEmpleado, gridBagConstraints);

        jLabel11.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(0, 102, 204));
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel11.setText("Dimensiones:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(14, 9, 0, 0);
        jPanel11.add(jLabel11, gridBagConstraints);

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));

        cmbDim.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        cmbDim.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "2", "3", "4" }));
        cmbDim.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbDimActionPerformed(evt);
            }
        });
        jPanel6.add(cmbDim);

        jPanel10.setBackground(new java.awt.Color(255, 255, 255));

        txtDim1.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtDim1.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(204, 204, 204)));
        txtDim1.setPreferredSize(new java.awt.Dimension(100, 25));
        txtDim1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtDim1KeyReleased(evt);
            }
        });
        jPanel10.add(txtDim1);

        lblX1.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        lblX1.setText("X");
        jPanel10.add(lblX1);

        txtDim2.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtDim2.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(204, 204, 204)));
        txtDim2.setPreferredSize(new java.awt.Dimension(100, 25));
        txtDim2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtDim2KeyReleased(evt);
            }
        });
        jPanel10.add(txtDim2);

        lblX2.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        lblX2.setText("X");
        jPanel10.add(lblX2);

        txtDim3.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtDim3.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(204, 204, 204)));
        txtDim3.setPreferredSize(new java.awt.Dimension(100, 25));
        txtDim3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtDim3KeyReleased(evt);
            }
        });
        jPanel10.add(txtDim3);

        lblX3.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        lblX3.setText("X");
        jPanel10.add(lblX3);

        txtDim4.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtDim4.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(204, 204, 204)));
        txtDim4.setPreferredSize(new java.awt.Dimension(100, 25));
        txtDim4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtDim4KeyReleased(evt);
            }
        });
        jPanel10.add(txtDim4);

        jPanel6.add(jPanel10);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 144;
        gridBagConstraints.ipady = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(1, 9, 0, 0);
        jPanel11.add(jPanel6, gridBagConstraints);

        jPanel9.add(jPanel11, java.awt.BorderLayout.CENTER);

        jPanel2.add(jPanel9, java.awt.BorderLayout.CENTER);

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

    private void btnCalidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCalidadActionPerformed
        terminarPlano(true);
    }//GEN-LAST:event_btnCalidadActionPerformed

    private void btnFresaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFresaActionPerformed
        pnlFresa.setBackground(setBack(pnlFresa));
        iniciarCronometro(1);
    }//GEN-LAST:event_btnFresaActionPerformed

    private void btnCncActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCncActionPerformed
        pnlCnc.setBackground(setBack(pnlCnc));
        iniciarCronometro(0);
    }//GEN-LAST:event_btnCncActionPerformed

    private void btnTornoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTornoActionPerformed
        pnlTorno.setBackground(setBack(pnlTorno));
        iniciarCronometro(3);
    }//GEN-LAST:event_btnTornoActionPerformed

    private void btnRectiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRectiActionPerformed
        pnlRecti.setBackground(setBack(pnlRecti));
        iniciarCronometro(2);
    }//GEN-LAST:event_btnRectiActionPerformed

    private void txtPlanoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPlanoActionPerformed
        limpiarFormulario();
        try {
            Connection con;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            traerMateriales();
            Statement st = con.createStatement();
            String plan = txtPlano.getText();
            if (txtPlano.getText().contains("/")) {
                plan = plan.substring(0, plan.indexOf("/"));
            }
            String sql = "select Plano, Proyecto, Cantidad, Material from planos where Plano like '" + plan + "'";
            ResultSet rs = st.executeQuery(sql);
            String plano = null;
            while (rs.next()) {
                plan = rs.getString("Plano");
                txtPlano2.setText(plano);
                txtProyecto.setText(rs.getString("Proyecto"));
                txtCantidad.setText(rs.getString("Cantidad"));
                String materialPlano = rs.getString("Material");
                String materialDetectado = iterarMateriales(materialPlano);
                cmbMaterial.setSelectedItem(materialDetectado);
            }
            KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
            manager.focusNextComponent();
            if (plano == null) {
                plano = verificarNomenclatura(plan, PLANO);
                verPlano(plano);
            }
            txtPlano.setText("");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error: " + e, "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_txtPlanoActionPerformed

    private void txtCantidadKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCantidadKeyTyped
        char c = evt.getKeyChar();
        if (!Character.isDigit(c)) {
            evt.consume();
        }
    }//GEN-LAST:event_txtCantidadKeyTyped

    private void txtPlanoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPlanoFocusGained
        txtPlano.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 102, 204)));
    }//GEN-LAST:event_txtPlanoFocusGained

    private void txtPlanoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPlanoFocusLost
        txtPlano.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(153, 153, 153)));
    }//GEN-LAST:event_txtPlanoFocusLost

    private void txtCantidadFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCantidadFocusGained
        txtCantidad.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 102, 204)));
    }//GEN-LAST:event_txtCantidadFocusGained

    private void txtCantidadFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCantidadFocusLost
        txtCantidad.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(153, 153, 153)));
    }//GEN-LAST:event_txtCantidadFocusLost

    private void txtComentariosFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtComentariosFocusGained
        txtComentarios.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 102, 204)));
    }//GEN-LAST:event_txtComentariosFocusGained

    private void txtComentariosFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtComentariosFocusLost
        txtComentarios.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(153, 153, 153)));
    }//GEN-LAST:event_txtComentariosFocusLost

    private void btnCalidadMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCalidadMouseEntered
        pnlCalidad.setBackground(new Color(0, 102, 255));
        btnCalidad.setForeground(Color.white);
    }//GEN-LAST:event_btnCalidadMouseEntered

    private void btnCalidadMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCalidadMouseExited
        pnlCalidad.setBackground(Color.white);
        btnCalidad.setForeground(new Color(0, 102, 255));
    }//GEN-LAST:event_btnCalidadMouseExited

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if (txtPlano2.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Debes seleccionar un Plano", "Advertencia", JOptionPane.WARNING_MESSAGE);
        } else {
            Thread hilo = new Thread() {
                public void run() {
                    espera.activar();
                    espera.setVisible(true);
                    try {
                        java.sql.Connection con;
                        Conexion con1 = new Conexion();
                        con = con1.getConnection();
                        Statement st = con.createStatement();
                        String plano = txtPlano2.getText();
                        String sql = "select Pdf,Plano from pdfplanos where Plano like '" + plano + "'";
                        ResultSet rs = st.executeQuery(sql);
                        byte[] b = null;
                        while (rs.next()) {
                            b = rs.getBytes("Pdf");
                        }
                        try (InputStream bos = new ByteArrayInputStream(b)) {
                            int tamInput = bos.available();
                            byte[] datosPdf = new byte[tamInput];
                            bos.read(datosPdf, 0, tamInput);
                            try (OutputStream out = new FileOutputStream("new.pdf")) {
                                out.write(datosPdf);
                            }
                            Desktop.getDesktop().open(new File("new.pdf"));
                        } catch (Exception e) {
                            espera.band = false;
                            espera.dispose();
                            JOptionPane.showMessageDialog(null, "No se encontro el archivo", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (SQLException | NumberFormatException e) {
                        espera.band = false;
                        espera.dispose();
                        JOptionPane.showMessageDialog(null, "ERROR: " + e, "ERROR", JOptionPane.ERROR_MESSAGE);
                    }
                    espera.band = false;
                    espera.dispose();
                }
            };
            hilo.start();
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void cmbDimActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbDimActionPerformed
        if (cmbDim.getSelectedItem().toString().equals("2")) {
            visiblesFalse();
            txtDim1.setVisible(true);
            txtDim2.setVisible(true);
            lblX1.setVisible(true);
        } else if (cmbDim.getSelectedItem().toString().equals("3")) {
            visiblesFalse();
            txtDim1.setVisible(true);
            txtDim2.setVisible(true);
            txtDim3.setVisible(true);
            lblX1.setVisible(true);
            lblX2.setVisible(true);
        } else if (cmbDim.getSelectedItem().toString().equals("4")) {
            visiblesFalse();
            txtDim1.setVisible(true);
            txtDim2.setVisible(true);
            txtDim3.setVisible(true);
            txtDim4.setVisible(true);
            lblX1.setVisible(true);
            lblX2.setVisible(true);
            lblX3.setVisible(true);
        }
    }//GEN-LAST:event_cmbDimActionPerformed

    private void txtDim1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDim1KeyReleased
        transferirFoco(txtDim1, evt.getKeyChar());

    }//GEN-LAST:event_txtDim1KeyReleased

    private void txtDim2KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDim2KeyReleased
        transferirFoco(txtDim2, evt.getKeyChar());
    }//GEN-LAST:event_txtDim2KeyReleased

    private void txtDim3KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDim3KeyReleased
        transferirFoco(txtDim3, evt.getKeyChar());
    }//GEN-LAST:event_txtDim3KeyReleased

    private void txtDim4KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDim4KeyReleased
        transferirFoco(txtDim4, evt.getKeyChar());
    }//GEN-LAST:event_txtDim4KeyReleased

    private void btnEstacionMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEstacionMouseEntered
        pnlEstacion.setBackground(new Color(255, 102, 0));
        btnEstacion.setForeground(Color.white);
    }//GEN-LAST:event_btnEstacionMouseEntered

    private void btnEstacionMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEstacionMouseExited
        pnlEstacion.setBackground(Color.white);
        btnEstacion.setForeground(new Color(255, 102, 0));
    }//GEN-LAST:event_btnEstacionMouseExited

    private void btnEstacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEstacionActionPerformed
        terminarPlano(false);
    }//GEN-LAST:event_btnEstacionActionPerformed

    private void cmbMaterialActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbMaterialActionPerformed

    }//GEN-LAST:event_cmbMaterialActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCalidad;
    private javax.swing.JButton btnCnc;
    private javax.swing.JButton btnEstacion;
    private javax.swing.JButton btnFresa;
    private javax.swing.JButton btnRecti;
    private javax.swing.JButton btnTorno;
    private javax.swing.JComboBox<String> cmbDim;
    private javax.swing.JComboBox<String> cmbMaterial;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JLabel lblCnc;
    private javax.swing.JLabel lblEmpleado;
    private javax.swing.JLabel lblFresa;
    private javax.swing.JLabel lblRecti;
    private javax.swing.JLabel lblSalir;
    private javax.swing.JLabel lblTorno;
    private javax.swing.JLabel lblX1;
    private javax.swing.JLabel lblX2;
    private javax.swing.JLabel lblX3;
    private javax.swing.JPanel pan;
    private javax.swing.JPanel panelGastos;
    private scrollPane.PanelRound panelReporte;
    private scrollPane.PanelRound panelRound1;
    private javax.swing.JPanel panelSalir;
    private javax.swing.JPanel pnlCalidad;
    private javax.swing.JPanel pnlCnc;
    private javax.swing.JPanel pnlEstacion;
    private javax.swing.JPanel pnlFresa;
    private javax.swing.JPanel pnlPlano;
    private javax.swing.JPanel pnlRecti;
    private javax.swing.JPanel pnlTorno;
    private javax.swing.JTextField txtCantidad;
    private javax.swing.JTextField txtComentarios;
    private javax.swing.JTextField txtDim1;
    private javax.swing.JTextField txtDim2;
    private javax.swing.JTextField txtDim3;
    private javax.swing.JTextField txtDim4;
    private javax.swing.JTextField txtPlano;
    private javax.swing.JTextField txtPlano2;
    private javax.swing.JTextField txtProyecto;
    // End of variables declaration//GEN-END:variables

    @Override
    public void actionPerformed(ActionEvent e) {
        if (btnEmpleado != null) {
            for (int i = 0; i < btnEmpleado.size(); i++) {
                if (e.getSource() == btnEmpleado.get(i)) {
                    numEmpleado = getNumEmpleado();
                }
            }
        }
        if (emp != null) {
            if (e.getSource() == emp.btnX) {
                this.dispose();
                emp.dispose();
            }
        }
    }
}
