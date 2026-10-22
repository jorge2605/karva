package pruebas;

import Conexiones.Conexion;
import Conexiones.ConexionChat;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.sql.rowset.serial.SerialBlob;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

public class NuevoUsuario extends javax.swing.JInternalFrame {

    String puesto, departamento;
    private String HoraEntrada;
    private String HoraSalida;
    private String HoraEnSab;
    private String HoraSalSab;
    private long segundos = 00;
    private long Semana = 0;
    private long Sabado = 0;
    //imagen Empleado
    private File imagenSeleccionada = null;
    private byte[] imagenBytes = null;
    private ByteArrayInputStream imagenRedimensionada = null;
    private final int IMG_WIDTH = 200;  //Ancho de como se guarda la imagen
    private final int IMG_HEIGHT = 200; //Altura de como se guarda la imagen

    public void FormatoHrs() {
        String HorasEn = (String) cbEH.getSelectedItem();
        String MinEn = (String) cbEM.getSelectedItem();
        String HorasSa = (String) cbSH.getSelectedItem();
        String MinSa = (String) cbSM.getSelectedItem();
        String HorasEnS = (String) cbESH.getSelectedItem();
        String MinEnS = (String) cbESM.getSelectedItem();
        String HorsSaS = (String) cbSSH.getSelectedItem();
        String MinSaS = (String) cbSSM.getSelectedItem();

        int HrsEn = Integer.parseInt(HorasEn);
        int MinE = Integer.parseInt(MinEn);
        int HrsSa = Integer.parseInt(HorasSa);
        int MinS = Integer.parseInt(MinSa);
        int HrsES = Integer.parseInt(HorasEnS);
        int MinES = Integer.parseInt(MinEnS);
        int HrsSS = Integer.parseInt(HorsSaS);
        int MinSS = Integer.parseInt(MinSaS);

        HoraEntrada = String.format("%02d:%02d:%02d", HrsEn, MinE, segundos);
        HoraSalida = String.format("%02d:%02d:%02d", HrsSa, MinS, segundos);
        HoraEnSab = String.format("%02d:%02d:%02d", HrsES, MinES, segundos);
        HoraSalSab = String.format("%02d:%02d:%02d", HrsSS, MinSS, segundos);
    }
    ;
    private String HorasDiarias;
    private String HorasSab;

    public void HorasTotales() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        if (HoraEntrada != null && HoraSalida != null) {
            LocalTime entrada = LocalTime.parse(HoraEntrada, formatter);
            LocalTime salida = LocalTime.parse(HoraSalida, formatter);

            Duration duracion = Duration.between(entrada, salida);
            long horas = duracion.toHours();
            long minutos = duracion.toMinutes() % 60;

            HorasDiarias = String.format("%02d:%02d:%02d", horas, minutos, segundos);
            Semana += duracion.toMinutes(); // se acumula

        }
        if (HoraEnSab != null && HoraSalSab != null) {
            LocalTime entradaS = LocalTime.parse(HoraEnSab, formatter);
            LocalTime salidaS = LocalTime.parse(HoraSalSab, formatter);
            Duration duracionSab = Duration.between(entradaS, salidaS);
            long horasSab = duracionSab.toHours();
            long minutosSab = duracionSab.toMinutes() % 60;

            HorasSab = String.format("%02d:%02d:%02d", horasSab, minutosSab, segundos);
            Sabado += duracionSab.toMinutes(); // también se acumula
        }
    }
    ;
    private String horasSemana;

    public void HorasSemanales() {
        long totalSemana = (Semana * 5) + Sabado;
        long horas = Math.round(totalSemana / 60);
        horasSemana = Long.toString(horas);

    }
    ;
    private String turno = ""; // Variable global que puedes usar donde necesites

    public void Turno() {
        if (HoraEntrada != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
            LocalTime entrada = LocalTime.parse(HoraEntrada, formatter);
            LocalTime limite = LocalTime.parse("12:00:00", formatter);

            if (entrada.compareTo(limite) >= 0) {
                turno = "VESPERTINO";
            } else {
                turno = "MATUTINO";
            }
        } else {
            JOptionPane.showMessageDialog(this, "Hora de Entrada No Asignada", "ADVERTENCIA", JOptionPane.ERROR_MESSAGE);
        }
    }
    private boolean Activo;

    public void Activo() {
        if (cbxActivo.isSelected()) {
            Activo = true;
        } else {
            Activo = false;
        }
    }
    ;
    private int admin;

    public void Admin() {
        if (cbxAdmin.isSelected()) {
            admin = 1;
        } else {
            admin = 0;
        }
    }

    public byte[] redimensionarImg(File archivo, int width, int height) {
        try {
            BufferedImage original = ImageIO.read(archivo);
            Image imgEscalada = original.getScaledInstance(width, height, Image.SCALE_SMOOTH);

            BufferedImage bufferedEscalada = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2d = bufferedEscalada.createGraphics();
            g2d.drawImage(imgEscalada, 0, 0, null);
            g2d.dispose();

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(bufferedEscalada, "jpg", baos); // o "png"
            return baos.toByteArray(); // devuelve el arreglo de bytes
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void cargarPermisos(ResultSet rs) throws SQLException {
        dashboard.setSelected("1".equals(rs.getString("Dashboard")));
        diseno.setSelected("1".equals(rs.getString("Diseño")));
        proyectos.setSelected("1".equals(rs.getString("Cambio")));
        carga.setSelected("1".equals(rs.getString("Carga")));
        reportes.setSelected("1".equals(rs.getString("Reportes")));
        cortes.setSelected("1".equals(rs.getString("Cortes")));

        ensamble.setSelected("1".equals(rs.getString("Ensamble")));
        calidad.setSelected("1".equals(rs.getString("Calidad")));
        orden.setSelected("1".equals(rs.getString("OrdenTrabajo")));

        fresa.setSelected("1".equals(rs.getString("Fresa")));
        cnc.setSelected("1".equals(rs.getString("Cnc")));
        torno.setSelected("1".equals(rs.getString("Torno")));

        rectificado.setSelected("1".equals(rs.getString("Rectificado")));
        estampado.setSelected("1".equals(rs.getString("Estampado")));
        envios.setSelected("1".equals(rs.getString("Envios")));

        compras.setSelected("1".equals(rs.getString("orden")));
        requisiciones.setSelected("1".equals(rs.getString("Requisiciones")));
        verRequisiciones.setSelected("1".equals(rs.getString("VerRequisiciones")));
        aprobacion.setSelected("1".equals(rs.getString("Aprobacion")));
        evaluacion.setSelected("1".equals(rs.getString("Evaluacion")));

        cotizacion.setSelected("1".equals(rs.getString("Cotizacion")));
        ventas.setSelected("1".equals(rs.getString("Ventas")));
        remisiones.setSelected("1".equals(rs.getString("Remisiones")));

        cxp.setSelected("1".equals(rs.getString("CXP")));
        nuevo.setSelected("1".equals(rs.getString("CrearEmpleado")));
        costos.setSelected("1".equals(rs.getString("Costos")));
        project.setSelected("1".equals(rs.getString("ProyectMan")));
        rh.setSelected("1".equals(rs.getString("rh")));
        checador.setSelected("1".equals(rs.getString("Checador")));

        inventario.setSelected("1".equals(rs.getString("Inventario")));
        recibos.setSelected("1".equals(rs.getString("Recibo")));
        prestamos.setSelected("1".equals(rs.getString("Prestamo")));
        pedidos.setSelected("1".equals(rs.getString("Pedidos")));
        entregas.setSelected("1".equals(rs.getString("Entrega")));
        liberacion.setSelected("1".equals(rs.getString("Liberacion")));
    }

    private void marcarTodos(boolean marca) {
        dashboard.setSelected(marca);
        diseno.setSelected(marca);
        proyectos.setSelected(marca);
        carga.setSelected(marca);
        reportes.setSelected(marca);
        cortes.setSelected(marca);
        ensamble.setSelected(marca);
        calidad.setSelected(marca);
        orden.setSelected(marca);
        fresa.setSelected(marca);
        cnc.setSelected(marca);
        rectificado.setSelected(marca);
        estampado.setSelected(marca);
        envios.setSelected(marca);
        compras.setSelected(marca);
        requisiciones.setSelected(marca);
        verRequisiciones.setSelected(marca);
        aprobacion.setSelected(marca);
        evaluacion.setSelected(marca);
        cotizacion.setSelected(marca);
        ventas.setSelected(marca);
        remisiones.setSelected(marca);
        cxp.setSelected(marca);
        nuevo.setSelected(marca);
        costos.setSelected(marca);
        project.setSelected(marca);
        rh.setSelected(marca);
        checador.setSelected(marca);
        inventario.setSelected(marca);
        recibos.setSelected(marca);
        prestamos.setSelected(marca);
        pedidos.setSelected(marca);
        entregas.setSelected(marca);
        liberacion.setSelected(marca);
        torno.setSelected(marca);
    }

    private java.util.LinkedHashMap<String, Boolean> obtenerPermisos() {
        java.util.LinkedHashMap<String, Boolean> p = new java.util.LinkedHashMap<>();

        p.put("Dashboard", dashboard.isSelected());
        p.put("Diseño", diseno.isSelected());
        p.put("Cambio", proyectos.isSelected());
        p.put("Carga", carga.isSelected());
        p.put("Reportes", reportes.isSelected());
        p.put("Cortes", cortes.isSelected());
        p.put("Ensamble", ensamble.isSelected());
        p.put("Calidad", calidad.isSelected());
        p.put("OrdenTrabajo", orden.isSelected());
        p.put("Fresa", fresa.isSelected());
        p.put("Cnc", cnc.isSelected());
        p.put("Torno", torno.isSelected());
        p.put("Rectificado", rectificado.isSelected());
        p.put("Estampado", estampado.isSelected());
        p.put("Envios", envios.isSelected());
        p.put("Orden", compras.isSelected());
        p.put("Requisiciones", requisiciones.isSelected());
        p.put("VerRequisiciones", verRequisiciones.isSelected());
        p.put("Aprobacion", aprobacion.isSelected());
        p.put("Evaluacion", evaluacion.isSelected());
        p.put("Cotizacion", cotizacion.isSelected());
        p.put("Ventas", ventas.isSelected());
        p.put("Remisiones", remisiones.isSelected());
        p.put("CXP", cxp.isSelected());
        p.put("CrearEmpleado", nuevo.isSelected());
        p.put("Costos", costos.isSelected());
        p.put("ProyectMan", project.isSelected());
        p.put("rh", rh.isSelected());
        p.put("Checador", checador.isSelected());
        p.put("Inventario", inventario.isSelected());
        p.put("Recibo", recibos.isSelected());
        p.put("Prestamo", prestamos.isSelected());
        p.put("Pedidos", pedidos.isSelected());
        p.put("Entrega", entregas.isSelected());
        p.put("Liberacion", liberacion.isSelected());

        return p;
    }

    public void actualizar() {
        if (txtNombre.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "DEBES SELECCIONAR UN EMPLEADO", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String sql = "UPDATE registroempleados SET " + "Nombre=?, Apellido=?, Direccion=?, Telefono=?, Puesto=?";
        java.util.LinkedHashMap<String, Boolean> permisos = obtenerPermisos();
        for (String columna : permisos.keySet()) {
            sql += ", `" + columna + "`=?";
        }
        sql += " WHERE NumEmpleado=?";
        try (Connection con = new Conexion().getConnection()) {
            con.setAutoCommit(false);
            try {
                int n;
                try (PreparedStatement pst = con.prepareStatement(sql)) {
                    pst.setString(1, txtNombre.getText().trim());
                    pst.setString(2, txtApellido.getText().trim());
                    pst.setString(3, txtDireccion.getText().trim());
                    pst.setString(4, txtTelefono.getText().trim());
                    pst.setString(5, String.valueOf(cmbPuesto.getSelectedItem()));

                    int i = 6;
                    for (Boolean valor : permisos.values()) {
                        pst.setBoolean(i++, valor);
                    }

                    pst.setString(i, txtCodigo.getText().trim());
                    n = pst.executeUpdate();
                }

                if (n == 0) {
                    con.rollback();
                    JOptionPane.showMessageDialog(this, "No se encontró el empleado para actualizar.");
                    return;
                }

                FormatoHrs();
                HorasTotales();
                HorasSemanales();
                Turno();
                Activo();
                Admin();

                String sqlCheck = "UPDATE empleadoscheck SET "
                        + "Nombre=?, Entrada=?, Salida=?, HorasDiarias=?, "
                        + "Turno=?, HoraSabado=?, EntradaSabado=?, "
                        + "SalidaSabado=?, TotalHoras=?, Departamento=?, "
                        + "Activo=?, Administrador=? WHERE NumEmpleado=?";

                try (PreparedStatement ps = con.prepareStatement(sqlCheck)) {
                    ps.setString(1, txtNombre.getText().trim() + " " + txtApellido.getText().trim());
                    ps.setString(2, HoraEntrada);
                    ps.setString(3, HoraSalida);
                    ps.setString(4, HorasDiarias);
                    ps.setString(5, turno);
                    ps.setString(6, HorasSab);
                    ps.setString(7, HoraEnSab);
                    ps.setString(8, HoraSalSab);
                    ps.setString(9, horasSemana);
                    ps.setString(10, String.valueOf(cmbDepartamento.getSelectedItem()));
                    ps.setBoolean(11, Activo);
                    ps.setInt(12, admin);
                    ps.setString(13, txtCodigo.getText().trim());

                    ps.executeUpdate();
                }

                if (imagenBytes != null && imagenRedimensionada != null) {
                    imagenRedimensionada.reset();
                    String sqlImg = "INSERT INTO imagenempleados "
                            + "(idEmpleado, imagen) VALUES (?, ?) "
                            + "ON DUPLICATE KEY UPDATE imagen=VALUES(imagen)";

                    try (PreparedStatement psi = con.prepareStatement(sqlImg)) {
                        psi.setString(1, txtCodigo.getText().trim());
                        psi.setBinaryStream(2, imagenRedimensionada);
                        psi.executeUpdate();
                    }
                }

                con.commit();

                if (imagenBytes != null) {
                    try {
                        String ruta = "\\\\192.168.100.40\\01 Shared\\14 RH\\Empleados\\";
                        String archivo = txtNumero.getText().trim() + " "
                                + txtNombre.getText().trim() + " "
                                + txtApellido.getText().trim() + ".jpg";

                        Files.write(
                                new File(ruta, archivo).toPath(),
                                imagenBytes,
                                StandardOpenOption.CREATE,
                                StandardOpenOption.TRUNCATE_EXISTING);
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(
                                this,
                                "Los datos se actualizaron, pero no se pudo "
                                + "guardar la fotografía en el servidor: "
                                + ex.getMessage(),
                                "Advertencia", JOptionPane.WARNING_MESSAGE);
                    }
                }

                JOptionPane.showMessageDialog(
                        this, "DATOS ACTUALIZADOS CORRECTAMENTE");

            } catch (Exception ex) {
                con.rollback();
                throw ex;
            } finally {
                con.setAutoCommit(true);
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "ERROR AL ACTUALIZAR: " + ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void guardar() {
        if (txtNombre.getText().trim().isEmpty()
                || txtApellido.getText().trim().isEmpty()
                || txtDireccion.getText().trim().isEmpty()
                || txtTelefono.getText().trim().isEmpty()
                || txtNumero.getText().trim().isEmpty()
                || txtContra.getText().isEmpty()
                || txtRepetir.getText().isEmpty()
                || cmbPuesto.getSelectedItem() == null
                || "PUESTO".equals(cmbPuesto.getSelectedItem())
                || cmbDepartamento.getSelectedItem() == null
                || "DEPARTAMENTO".equals(cmbDepartamento.getSelectedItem())) {
            JOptionPane.showMessageDialog(this, "COMPLETA TODOS LOS CAMPOS OBLIGATORIOS", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (!txtContra.getText().equals(txtRepetir.getText())) {
            JOptionPane.showMessageDialog(this, "LA CONTRASEÑA NO COINCIDE", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
            return;
        }
        String numero = txtNumero.getText().trim();
        if (!numero.matches("[A-Za-z0-9_-]+")) {
            JOptionPane.showMessageDialog(this, "El numero de empleado contiene caracteres no validos", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
            return;
        }
        puesto = String.valueOf(cmbPuesto.getSelectedItem());
        departamento = String.valueOf(cmbDepartamento.getSelectedItem());
        java.util.LinkedHashMap<String, Boolean> permisos = obtenerPermisos();
        String nombreCompleto = txtNombre.getText().trim() + " " + txtApellido.getText().trim();
        try (Connection con = new Conexion().getConnection()) {
            con.setAutoCommit(false);
            try {
                int puerto = 0;
                try (PreparedStatement psPuerto = con.prepareStatement("SELECT MAX(CAST(Puerto AS UNSIGNED)) "
                        + "FROM registroempleados"); ResultSet rsPuerto = psPuerto.executeQuery()) {
                    if (rsPuerto.next()) {
                        puerto = rsPuerto.getInt(1) + 5;
                    }
                }
                java.util.List<String> columnas = new java.util.ArrayList<>();
                java.util.List<String> valores = new java.util.ArrayList<>();

                columnas.add("Nombre");
                columnas.add("Apellido");
                columnas.add("Direccion");
                columnas.add("Telefono");
                columnas.add("Puesto");
                columnas.add("Contraseña");
                columnas.add("NumEmpleado");

                valores.add("?");
                valores.add("?");
                valores.add("?");
                valores.add("?");
                valores.add("?");
                valores.add("AES_ENCRYPT(?,'mi_llave')");
                valores.add("?");

                for (String columna : permisos.keySet()) {
                    columnas.add("`" + columna + "`");
                    valores.add("?");
                }
                
                String[] columnasAntiguas = {
                    "Acabados", "Tratamiento", "Electrico",
                    "VerEmpleado", "InventarioPlanos", "Cotizar"
                };

                for (String columna : columnasAntiguas) {
                    columnas.add("`" + columna + "`");
                    valores.add("?");
                }
                
                String[] columnasConfiguracion = {
                    "Herramentista", "Disenio", "Administracion",
                    "Almacen", "Super"
                };

                for (String columna : columnasConfiguracion) {
                    columnas.add("`" + columna + "`");
                    valores.add("?");
                }

                columnas.add("Puerto");
                valores.add("?");

                String sql = "INSERT INTO registroempleados ("
                        + String.join(",", columnas) + ") VALUES ("
                        + String.join(",", valores) + ")";

                try (PreparedStatement pst = con.prepareStatement(sql)) {
                    int i = 1;

                    pst.setString(i++, txtNombre.getText().trim());
                    pst.setString(i++, txtApellido.getText().trim());
                    pst.setString(i++, txtDireccion.getText().trim());
                    pst.setString(i++, txtTelefono.getText().trim());
                    pst.setString(i++, puesto);
                    pst.setString(i++, txtContra.getText());
                    pst.setString(i++, numero);

                    for (Boolean valor : permisos.values()) {
                        pst.setBoolean(i++, valor);
                    }

                    for (int j = 0; j < columnasAntiguas.length; j++) {
                        pst.setBoolean(i++, false);
                    }

                    for (int j = 0; j < columnasConfiguracion.length; j++) {
                        pst.setString(i++, "NO");
                    }

                    pst.setString(i, String.valueOf(puerto));

                    pst.executeUpdate();
                }

                FormatoHrs();
                HorasTotales();
                HorasSemanales();
                Turno();
                Activo();
                Admin();

                String sqlCheck = "INSERT INTO empleadoscheck "
                        + "(NumEmpleado, Nombre, Entrada, Salida, "
                        + "HorasDiarias, Turno, HoraSabado, EntradaSabado, "
                        + "SalidaSabado, TotalHoras, Departamento, "
                        + "Activo, Administrador) "
                        + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";

                try (PreparedStatement ps = con.prepareStatement(sqlCheck)) {
                    ps.setString(1, numero);
                    ps.setString(2, nombreCompleto);
                    ps.setString(3, HoraEntrada);
                    ps.setString(4, HoraSalida);
                    ps.setString(5, HorasDiarias);
                    ps.setString(6, turno);
                    ps.setString(7, HorasSab);
                    ps.setString(8, HoraEnSab);
                    ps.setString(9, HoraSalSab);
                    ps.setString(10, horasSemana);
                    ps.setString(11, departamento);
                    ps.setBoolean(12, Activo);
                    ps.setInt(13, admin);
                    ps.executeUpdate();
                }
                con.commit();
            } catch (Exception ex) {
                con.rollback();
                throw ex;
            } finally {
                con.setAutoCommit(true);
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(
                    this, "ERROR AL GUARDAR DATOS: " + ex.getMessage(),
                    "ERROR", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (Connection conChat = new ConexionChat().getConnection(); Statement st = conChat.createStatement()) {

            String crearNotificacion
                    = "CREATE TABLE `noti" + numero + "` ("
                    + "`Id` INT NOT NULL AUTO_INCREMENT,"
                    + "`Departamento` VARCHAR(45) DEFAULT NULL,"
                    + "`Titulo` VARCHAR(100) DEFAULT NULL,"
                    + "`Texto` VARCHAR(500) DEFAULT NULL,"
                    + "`Visto` VARCHAR(45) DEFAULT NULL,"
                    + "`Fecha` VARCHAR(45) DEFAULT NULL,"
                    + "`Visto2` VARCHAR(45) DEFAULT NULL,"
                    + "PRIMARY KEY (`Id`)"
                    + ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4";

            st.execute(crearNotificacion);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,"El empleado se guardó, pero no se pudo crear su tabla " 
                    + "de notificaciones: " + ex.getMessage(),"ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (imagenBytes != null && imagenRedimensionada != null) {
            try (Connection con = new Conexion().getConnection()) {
                imagenRedimensionada.reset();
                String sqlImagen = "INSERT INTO imagenempleados "
                        + "(idEmpleado, imagen) VALUES (?, ?)";
                try (PreparedStatement ps = con.prepareStatement(sqlImagen)) {
                    ps.setString(1, txtCodigo.getText().trim());
                    ps.setBinaryStream(2, imagenRedimensionada);
                    ps.executeUpdate();
                }
                String ruta = "\\\\192.168.100.40\\01 Shared\\14 RH\\Empleados\\";
                String archivo = numero + " "
                        + txtNombre.getText().trim() + " "
                        + txtApellido.getText().trim() + ".jpg";

                Files.write(new File(ruta, archivo).toPath(), imagenBytes, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,"El empleado se guardó, pero ocurrió un problema "+ "con la fotografía: " + ex.getMessage(),
                        "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
            }
        }
        JOptionPane.showMessageDialog(this, "DATOS GUARDADOS");
        borrarDatos();
        deshabilitar();
    }

    public void deshabilitar() {
        txtNombre.setEnabled(false);
        txtCodigo.setEnabled(false);
        txtApellido.setEnabled(false);
        txtDireccion.setEnabled(false);
        txtTelefono.setEnabled(false);
        cmbPuesto.setEnabled(false);
        btnGuardar.setEnabled(false);
        txtContra.setEnabled(false);
        txtRepetir.setEnabled(false);
        cmbDepartamento.setEnabled(false);
    }

    public void habilitar() {
        txtNombre.setEnabled(true);
        txtApellido.setEnabled(true);
        txtDireccion.setEnabled(true);
        txtTelefono.setEnabled(true);
        cmbPuesto.setEnabled(true);
        btnGuardar.setEnabled(true);
        txtContra.setEnabled(true);
        txtRepetir.setEnabled(true);
        cmbDepartamento.setEnabled(true);

    }

    public void borrarDatos() {
        txtNombre.setText("");
        txtApellido.setText("");
        txtDireccion.setText("");
        txtTelefono.setText("");
        txtCodigo.setText("");
        txtNumero.setText("");
        cmbPuesto.setSelectedItem("PUESTO");
        txtContra.setText("");
        txtRepetir.setText("");
        cmbDepartamento.setSelectedItem("DEPARTAMENTO");
        cbESH.setSelectedItem("00");
        cbESM.setSelectedItem("00");
        cbSSH.setSelectedItem("00");
        cbSSM.setSelectedItem("00");

    }

    public NuevoUsuario() {
        initComponents();
        ((javax.swing.plaf.basic.BasicInternalFrameUI) this.getUI()).setNorthPane(null);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        txtNombre = new RSMaterialComponent.RSTextFieldMaterial();
        txtApellido = new RSMaterialComponent.RSTextFieldMaterial();
        txtDireccion = new RSMaterialComponent.RSTextFieldMaterial();
        txtTelefono = new RSMaterialComponent.RSTextFieldMaterial();
        txtNumero = new RSMaterialComponent.RSTextFieldMaterial();
        cmbPuesto = new RSMaterialComponent.RSComboBoxMaterial();
        cmbDepartamento = new RSMaterialComponent.RSComboBoxMaterial();
        txtContra = new RSMaterialComponent.RSPasswordMaterial();
        txtRepetir = new RSMaterialComponent.RSPasswordMaterial();
        jPanel6 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        cbEH = new javax.swing.JComboBox<>();
        cbEM = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        cbSH = new javax.swing.JComboBox<>();
        cbSM = new javax.swing.JComboBox<>();
        jPanel14 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        cbESH = new javax.swing.JComboBox<>();
        cbESM = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        cbSSH = new javax.swing.JComboBox<>();
        cbSSM = new javax.swing.JComboBox<>();
        jPanel17 = new javax.swing.JPanel();
        cbxActivo = new javax.swing.JCheckBox();
        cbxAdmin = new javax.swing.JCheckBox();
        jPanel18 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        btImagen = new javax.swing.JButton();
        txtCodigo = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        dashboard = new javax.swing.JCheckBox();
        diseno = new javax.swing.JCheckBox();
        proyectos = new javax.swing.JCheckBox();
        carga = new javax.swing.JCheckBox();
        reportes = new javax.swing.JCheckBox();
        cortes = new javax.swing.JCheckBox();
        ensamble = new javax.swing.JCheckBox();
        calidad = new javax.swing.JCheckBox();
        orden = new javax.swing.JCheckBox();
        fresa = new javax.swing.JCheckBox();
        cnc = new javax.swing.JCheckBox();
        rectificado = new javax.swing.JCheckBox();
        estampado = new javax.swing.JCheckBox();
        envios = new javax.swing.JCheckBox();
        jLabel5 = new javax.swing.JLabel();
        compras = new javax.swing.JCheckBox();
        requisiciones = new javax.swing.JCheckBox();
        verRequisiciones = new javax.swing.JCheckBox();
        aprobacion = new javax.swing.JCheckBox();
        evaluacion = new javax.swing.JCheckBox();
        jLabel9 = new javax.swing.JLabel();
        cotizacion = new javax.swing.JCheckBox();
        ventas = new javax.swing.JCheckBox();
        remisiones = new javax.swing.JCheckBox();
        jLabel10 = new javax.swing.JLabel();
        cxp = new javax.swing.JCheckBox();
        nuevo = new javax.swing.JCheckBox();
        costos = new javax.swing.JCheckBox();
        project = new javax.swing.JCheckBox();
        rh = new javax.swing.JCheckBox();
        checador = new javax.swing.JCheckBox();
        jLabel11 = new javax.swing.JLabel();
        inventario = new javax.swing.JCheckBox();
        recibos = new javax.swing.JCheckBox();
        prestamos = new javax.swing.JCheckBox();
        pedidos = new javax.swing.JCheckBox();
        entregas = new javax.swing.JCheckBox();
        liberacion = new javax.swing.JCheckBox();
        torno = new javax.swing.JCheckBox();
        jPanel5 = new javax.swing.JPanel();
        btnGuardar = new javax.swing.JButton();

        setBorder(null);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new java.awt.GridLayout(1, 0));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new java.awt.GridBagLayout());

        txtNombre.setForeground(new java.awt.Color(51, 51, 51));
        txtNombre.setColorMaterial(new java.awt.Color(0, 51, 51));
        txtNombre.setPhColor(new java.awt.Color(102, 102, 102));
        txtNombre.setPlaceholder("Nombre(s)");
        txtNombre.setPreferredSize(new java.awt.Dimension(100, 42));
        txtNombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNombreActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(6, 60, 6, 60);
        jPanel2.add(txtNombre, gridBagConstraints);

        txtApellido.setForeground(new java.awt.Color(51, 51, 51));
        txtApellido.setColorMaterial(new java.awt.Color(51, 51, 51));
        txtApellido.setPhColor(new java.awt.Color(102, 102, 102));
        txtApellido.setPlaceholder("Apellido(s)");
        txtApellido.setPreferredSize(new java.awt.Dimension(100, 42));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(6, 60, 6, 60);
        jPanel2.add(txtApellido, gridBagConstraints);

        txtDireccion.setForeground(new java.awt.Color(51, 51, 51));
        txtDireccion.setColorMaterial(new java.awt.Color(51, 51, 51));
        txtDireccion.setPhColor(new java.awt.Color(102, 102, 102));
        txtDireccion.setPlaceholder("Direccion");
        txtDireccion.setPreferredSize(new java.awt.Dimension(100, 42));
        txtDireccion.setSelectionColor(new java.awt.Color(102, 102, 102));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(6, 60, 6, 60);
        jPanel2.add(txtDireccion, gridBagConstraints);

        txtTelefono.setForeground(new java.awt.Color(51, 51, 51));
        txtTelefono.setColorMaterial(new java.awt.Color(51, 51, 51));
        txtTelefono.setPhColor(new java.awt.Color(102, 102, 102));
        txtTelefono.setPlaceholder("Telefono");
        txtTelefono.setPreferredSize(new java.awt.Dimension(100, 42));
        txtTelefono.setSelectionColor(new java.awt.Color(102, 102, 102));
        txtTelefono.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTelefonoActionPerformed(evt);
            }
        });
        txtTelefono.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtTelefonoKeyTyped(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(6, 60, 6, 60);
        jPanel2.add(txtTelefono, gridBagConstraints);

        txtNumero.setForeground(new java.awt.Color(51, 51, 51));
        txtNumero.setColorMaterial(new java.awt.Color(51, 51, 51));
        txtNumero.setPhColor(new java.awt.Color(102, 102, 102));
        txtNumero.setPlaceholder("Numero de empleado");
        txtNumero.setPreferredSize(new java.awt.Dimension(100, 42));
        txtNumero.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNumeroActionPerformed(evt);
            }
        });
        txtNumero.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNumeroKeyTyped(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(6, 60, 6, 60);
        jPanel2.add(txtNumero, gridBagConstraints);

        cmbPuesto.setForeground(new java.awt.Color(102, 102, 102));
        cmbPuesto.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "PUESTO", "ALMACEN", "CALIDAD", "COMPRAS", "DISEÑO", "INTEGRACION", "LIMPIEZA", "HERRAMENTISTA", "VENTAS", "COSTOS", "IT", "SUPER" }));
        cmbPuesto.setColorMaterial(new java.awt.Color(51, 51, 51));
        cmbPuesto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbPuestoActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(6, 60, 6, 0);
        jPanel2.add(cmbPuesto, gridBagConstraints);

        cmbDepartamento.setForeground(new java.awt.Color(102, 102, 102));
        cmbDepartamento.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "DEPARTAMENTO", "CI ADMINITRACION", "MOI TECNICO CALIDAD INDIRECTO", "MOD HERRAMENTISTA", "MOI PROGRAMACION", "MOI ALMACEN", "MOD ELECTROMECANICO", "MOI DISENADOR", " ", " " }));
        cmbDepartamento.setColorMaterial(new java.awt.Color(51, 51, 51));
        cmbDepartamento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbDepartamentoActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 60);
        jPanel2.add(cmbDepartamento, gridBagConstraints);

        txtContra.setForeground(new java.awt.Color(51, 51, 51));
        txtContra.setColorMaterial(new java.awt.Color(51, 51, 51));
        txtContra.setPhColor(new java.awt.Color(102, 102, 102));
        txtContra.setPlaceholder("Contraseña");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(6, 60, 6, 0);
        jPanel2.add(txtContra, gridBagConstraints);

        txtRepetir.setForeground(new java.awt.Color(51, 51, 51));
        txtRepetir.setColorMaterial(new java.awt.Color(51, 51, 51));
        txtRepetir.setPhColor(new java.awt.Color(102, 102, 102));
        txtRepetir.setPlaceholder("Repetir contraseña");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 60);
        jPanel2.add(txtRepetir, gridBagConstraints);

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 2, 16)); // NOI18N
        jLabel2.setText("Entrada");
        jPanel6.add(jLabel2);

        cbEH.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        cbEH.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", " " }));
        cbEH.setSelectedIndex(8);
        cbEH.setBorder(null);
        cbEH.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cbEH.setMaximumSize(new java.awt.Dimension(32770, 32767));
        cbEH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbEHActionPerformed(evt);
            }
        });
        jPanel6.add(cbEH);

        cbEM.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        cbEM.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "00", "30", " " }));
        cbEM.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel6.add(cbEM);

        jLabel7.setFont(new java.awt.Font("Segoe UI", 2, 16)); // NOI18N
        jLabel7.setText("Salida");
        jPanel6.add(jLabel7);

        cbSH.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        cbSH.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", " " }));
        cbSH.setSelectedIndex(17);
        cbSH.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel6.add(cbSH);

        cbSM.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        cbSM.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "00", "30", " " }));
        cbSM.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel6.add(cbSM);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(6, 60, 6, 60);
        jPanel2.add(jPanel6, gridBagConstraints);

        jPanel14.setBackground(new java.awt.Color(255, 255, 255));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 2, 16)); // NOI18N
        jLabel4.setText("Entrada Sábado");
        jPanel14.add(jLabel4);

        cbESH.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        cbESH.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", " " }));
        cbESH.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel14.add(cbESH);

        cbESM.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        cbESM.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "00", "30", " " }));
        cbESM.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel14.add(cbESM);

        jLabel8.setFont(new java.awt.Font("Segoe UI", 2, 16)); // NOI18N
        jLabel8.setText("Salida Sábado");
        jPanel14.add(jLabel8);

        cbSSH.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        cbSSH.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "" }));
        cbSSH.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel14.add(cbSSH);

        cbSSM.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        cbSSM.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "00", "30", " " }));
        cbSSM.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel14.add(cbSSM);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(6, 60, 6, 60);
        jPanel2.add(jPanel14, gridBagConstraints);

        jPanel17.setBackground(new java.awt.Color(255, 255, 255));

        cbxActivo.setBackground(new java.awt.Color(255, 255, 255));
        cbxActivo.setFont(new java.awt.Font("Segoe UI", 2, 16)); // NOI18N
        cbxActivo.setSelected(true);
        cbxActivo.setText("ACTIVO");
        cbxActivo.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cbxActivo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxActivoActionPerformed(evt);
            }
        });
        jPanel17.add(cbxActivo);

        cbxAdmin.setBackground(new java.awt.Color(255, 255, 255));
        cbxAdmin.setFont(new java.awt.Font("Segoe UI", 2, 16)); // NOI18N
        cbxAdmin.setText("ADMINISTRADOR");
        cbxAdmin.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel17.add(cbxAdmin);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(6, 60, 6, 60);
        jPanel2.add(jPanel17, gridBagConstraints);

        jPanel18.setBackground(new java.awt.Color(255, 255, 255));

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel6.setText("Imagen del Empleado:");
        jPanel18.add(jLabel6);

        btImagen.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btImagen.setText("Seleccionar Imagen");
        btImagen.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btImagen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btImagenActionPerformed(evt);
            }
        });
        jPanel18.add(btImagen);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(6, 60, 6, 60);
        jPanel2.add(jPanel18, gridBagConstraints);

        txtCodigo.setEditable(false);
        txtCodigo.setBackground(new java.awt.Color(255, 255, 255));
        txtCodigo.setForeground(new java.awt.Color(255, 255, 255));
        txtCodigo.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCodigo.setBorder(null);
        txtCodigo.setEnabled(false);
        txtCodigo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCodigoKeyTyped(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(6, 60, 6, 60);
        jPanel2.add(txtCodigo, gridBagConstraints);

        jPanel1.add(jPanel2);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setLayout(new java.awt.BorderLayout(0, 10));

        jLabel1.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 51, 51));
        jLabel1.setText("Accesos");
        jPanel3.add(jLabel1, java.awt.BorderLayout.PAGE_START);

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        java.awt.GridBagLayout jPanel4Layout = new java.awt.GridBagLayout();
        jPanel4Layout.rowWeights = new double[] {1.0, 0.0, 0.0, 0.0, 1.0, 0.0, 1.0, 0.0, 1.0, 0.0, 0.0, 1.0};
        jPanel4.setLayout(jPanel4Layout);

        jLabel3.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 51, 204));
        jLabel3.setText("Produccion");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(15, 0, 0, 0);
        jPanel4.add(jLabel3, gridBagConstraints);

        dashboard.setBackground(new java.awt.Color(255, 255, 255));
        dashboard.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        dashboard.setForeground(new java.awt.Color(51, 51, 51));
        dashboard.setText("Dashboard");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 1;
        gridBagConstraints.ipadx = 10;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(dashboard, gridBagConstraints);

        diseno.setBackground(new java.awt.Color(255, 255, 255));
        diseno.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        diseno.setForeground(new java.awt.Color(51, 51, 51));
        diseno.setText("Diseño");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 1;
        gridBagConstraints.ipadx = 10;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(diseno, gridBagConstraints);

        proyectos.setBackground(new java.awt.Color(255, 255, 255));
        proyectos.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        proyectos.setForeground(new java.awt.Color(51, 51, 51));
        proyectos.setText("Proyectos");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 1;
        gridBagConstraints.ipadx = 10;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(proyectos, gridBagConstraints);

        carga.setBackground(new java.awt.Color(255, 255, 255));
        carga.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        carga.setForeground(new java.awt.Color(51, 51, 51));
        carga.setText("Carga de trabajo");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 1;
        gridBagConstraints.ipadx = 10;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(carga, gridBagConstraints);

        reportes.setBackground(new java.awt.Color(255, 255, 255));
        reportes.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        reportes.setForeground(new java.awt.Color(51, 51, 51));
        reportes.setText("Reportes");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 1;
        gridBagConstraints.ipadx = 10;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(reportes, gridBagConstraints);

        cortes.setBackground(new java.awt.Color(255, 255, 255));
        cortes.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        cortes.setForeground(new java.awt.Color(51, 51, 51));
        cortes.setText("Cortes");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.ipadx = 10;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(cortes, gridBagConstraints);

        ensamble.setBackground(new java.awt.Color(255, 255, 255));
        ensamble.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        ensamble.setForeground(new java.awt.Color(51, 51, 51));
        ensamble.setText("Ensamble");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 2;
        gridBagConstraints.ipadx = 10;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(ensamble, gridBagConstraints);

        calidad.setBackground(new java.awt.Color(255, 255, 255));
        calidad.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        calidad.setForeground(new java.awt.Color(51, 51, 51));
        calidad.setText("Calidad");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 2;
        gridBagConstraints.ipadx = 10;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(calidad, gridBagConstraints);

        orden.setBackground(new java.awt.Color(255, 255, 255));
        orden.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        orden.setForeground(new java.awt.Color(51, 51, 51));
        orden.setText("Orden de trabajo");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 2;
        gridBagConstraints.ipadx = 10;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(orden, gridBagConstraints);

        fresa.setBackground(new java.awt.Color(255, 255, 255));
        fresa.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        fresa.setForeground(new java.awt.Color(51, 51, 51));
        fresa.setText("Fresadora");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 2;
        gridBagConstraints.ipadx = 10;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(fresa, gridBagConstraints);

        cnc.setBackground(new java.awt.Color(255, 255, 255));
        cnc.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        cnc.setForeground(new java.awt.Color(51, 51, 51));
        cnc.setText("Cnc");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 2;
        gridBagConstraints.ipadx = 10;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(cnc, gridBagConstraints);

        rectificado.setBackground(new java.awt.Color(255, 255, 255));
        rectificado.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        rectificado.setForeground(new java.awt.Color(51, 51, 51));
        rectificado.setText("Rectificado");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.ipadx = 10;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(rectificado, gridBagConstraints);

        estampado.setBackground(new java.awt.Color(255, 255, 255));
        estampado.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        estampado.setForeground(new java.awt.Color(51, 51, 51));
        estampado.setText("Estampado");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 3;
        gridBagConstraints.ipadx = 10;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(estampado, gridBagConstraints);

        envios.setBackground(new java.awt.Color(255, 255, 255));
        envios.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        envios.setForeground(new java.awt.Color(51, 51, 51));
        envios.setText("Envios");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 3;
        gridBagConstraints.ipadx = 10;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(envios, gridBagConstraints);

        jLabel5.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 51, 204));
        jLabel5.setText("Compras");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(15, 0, 0, 0);
        jPanel4.add(jLabel5, gridBagConstraints);

        compras.setBackground(new java.awt.Color(255, 255, 255));
        compras.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        compras.setForeground(new java.awt.Color(51, 51, 51));
        compras.setText("Compras");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.ipadx = 10;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(compras, gridBagConstraints);

        requisiciones.setBackground(new java.awt.Color(255, 255, 255));
        requisiciones.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        requisiciones.setForeground(new java.awt.Color(51, 51, 51));
        requisiciones.setText("Requisiciones");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.ipadx = 10;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(requisiciones, gridBagConstraints);

        verRequisiciones.setBackground(new java.awt.Color(255, 255, 255));
        verRequisiciones.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        verRequisiciones.setForeground(new java.awt.Color(51, 51, 51));
        verRequisiciones.setText("Estado requisiciones");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.ipadx = 10;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(verRequisiciones, gridBagConstraints);

        aprobacion.setBackground(new java.awt.Color(255, 255, 255));
        aprobacion.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        aprobacion.setForeground(new java.awt.Color(51, 51, 51));
        aprobacion.setText("Aprobacion");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.ipadx = 10;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(aprobacion, gridBagConstraints);

        evaluacion.setBackground(new java.awt.Color(255, 255, 255));
        evaluacion.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        evaluacion.setForeground(new java.awt.Color(51, 51, 51));
        evaluacion.setText("Evaluacion");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.ipadx = 10;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(evaluacion, gridBagConstraints);

        jLabel9.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 51, 204));
        jLabel9.setText("Ventas");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(15, 0, 0, 0);
        jPanel4.add(jLabel9, gridBagConstraints);

        cotizacion.setBackground(new java.awt.Color(255, 255, 255));
        cotizacion.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        cotizacion.setForeground(new java.awt.Color(51, 51, 51));
        cotizacion.setText("Cotizacion");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.ipadx = 10;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(cotizacion, gridBagConstraints);

        ventas.setBackground(new java.awt.Color(255, 255, 255));
        ventas.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        ventas.setForeground(new java.awt.Color(51, 51, 51));
        ventas.setText("Ventas");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.ipadx = 10;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(ventas, gridBagConstraints);

        remisiones.setBackground(new java.awt.Color(255, 255, 255));
        remisiones.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        remisiones.setForeground(new java.awt.Color(51, 51, 51));
        remisiones.setText("Remisiones");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.ipadx = 10;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(remisiones, gridBagConstraints);

        jLabel10.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(0, 51, 204));
        jLabel10.setText("Administracion");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(15, 0, 0, 0);
        jPanel4.add(jLabel10, gridBagConstraints);

        cxp.setBackground(new java.awt.Color(255, 255, 255));
        cxp.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        cxp.setForeground(new java.awt.Color(51, 51, 51));
        cxp.setText("CXP");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.ipadx = 10;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(cxp, gridBagConstraints);

        nuevo.setBackground(new java.awt.Color(255, 255, 255));
        nuevo.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        nuevo.setForeground(new java.awt.Color(51, 51, 51));
        nuevo.setText("Nuevo empleado");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.ipadx = 10;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(nuevo, gridBagConstraints);

        costos.setBackground(new java.awt.Color(255, 255, 255));
        costos.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        costos.setForeground(new java.awt.Color(51, 51, 51));
        costos.setText("Costos");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.ipadx = 10;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(costos, gridBagConstraints);

        project.setBackground(new java.awt.Color(255, 255, 255));
        project.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        project.setForeground(new java.awt.Color(51, 51, 51));
        project.setText("Project Manager");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.ipadx = 10;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(project, gridBagConstraints);

        rh.setBackground(new java.awt.Color(255, 255, 255));
        rh.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        rh.setForeground(new java.awt.Color(51, 51, 51));
        rh.setText("RH");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.ipadx = 10;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(rh, gridBagConstraints);

        checador.setBackground(new java.awt.Color(255, 255, 255));
        checador.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        checador.setForeground(new java.awt.Color(51, 51, 51));
        checador.setText("Checador");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.ipadx = 10;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(checador, gridBagConstraints);

        jLabel11.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(0, 51, 204));
        jLabel11.setText("Almacen");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(15, 0, 0, 0);
        jPanel4.add(jLabel11, gridBagConstraints);

        inventario.setBackground(new java.awt.Color(255, 255, 255));
        inventario.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        inventario.setForeground(new java.awt.Color(51, 51, 51));
        inventario.setText("Inventario");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.ipadx = 10;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(inventario, gridBagConstraints);

        recibos.setBackground(new java.awt.Color(255, 255, 255));
        recibos.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        recibos.setForeground(new java.awt.Color(51, 51, 51));
        recibos.setText("Recibos");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.ipadx = 10;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(recibos, gridBagConstraints);

        prestamos.setBackground(new java.awt.Color(255, 255, 255));
        prestamos.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        prestamos.setForeground(new java.awt.Color(51, 51, 51));
        prestamos.setText("Prestamos");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.ipadx = 10;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(prestamos, gridBagConstraints);

        pedidos.setBackground(new java.awt.Color(255, 255, 255));
        pedidos.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        pedidos.setForeground(new java.awt.Color(51, 51, 51));
        pedidos.setText("Pedidos");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.ipadx = 10;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(pedidos, gridBagConstraints);

        entregas.setBackground(new java.awt.Color(255, 255, 255));
        entregas.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        entregas.setForeground(new java.awt.Color(51, 51, 51));
        entregas.setText("Entregas");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.ipadx = 10;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(entregas, gridBagConstraints);

        liberacion.setBackground(new java.awt.Color(255, 255, 255));
        liberacion.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        liberacion.setForeground(new java.awt.Color(51, 51, 51));
        liberacion.setText("Liberacion");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 13;
        gridBagConstraints.ipadx = 10;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(liberacion, gridBagConstraints);

        torno.setBackground(new java.awt.Color(255, 255, 255));
        torno.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        torno.setForeground(new java.awt.Color(51, 51, 51));
        torno.setText("Torno");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 3;
        gridBagConstraints.ipadx = 10;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(torno, gridBagConstraints);

        jPanel3.add(jPanel4, java.awt.BorderLayout.CENTER);

        jPanel1.add(jPanel3);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setLayout(new java.awt.GridBagLayout());

        btnGuardar.setBackground(new java.awt.Color(0, 51, 204));
        btnGuardar.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        btnGuardar.setForeground(new java.awt.Color(255, 255, 255));
        btnGuardar.setText("Guardar");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.ipadx = 20;
        gridBagConstraints.ipady = 6;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 5, 0);
        jPanel5.add(btnGuardar, gridBagConstraints);

        getContentPane().add(jPanel5, java.awt.BorderLayout.SOUTH);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtNombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNombreActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombreActionPerformed

    private void txtTelefonoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTelefonoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTelefonoActionPerformed

    private void txtTelefonoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTelefonoKeyTyped
        char c = evt.getKeyChar();
        if (Character.isLowerCase(c)) {
            String cad = ("" + c).toUpperCase();
            c = cad.charAt(0);
            evt.setKeyChar(c);
        }
    }//GEN-LAST:event_txtTelefonoKeyTyped

    private void txtNumeroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNumeroActionPerformed
        try {
            Connection con = new Conexion().getConnection();
            Statement st = con.createStatement();
            Statement st1 = con.createStatement();
            String sql = "select * from registroempleados where NumEmpleado like '" + txtNumero.getText() + "'";
            String sqlCheck = "SELECT * FROM empleadoscheck where NumEmpleado like '" + txtNumero.getText() + "'";
            ResultSet rs = st.executeQuery(sql);
            ResultSet rsc = st1.executeQuery(sqlCheck);
            String datos[] = new String[33];
            while (rsc.next()) {
                cmbDepartamento.setSelectedItem(rsc.getString("Departamento"));
                cbxAdmin.setSelected(rsc.getInt("Administrador") == 1);
                cbxActivo.setSelected(Boolean.parseBoolean(rsc.getString("Activo")));
                String Entrada = rsc.getString("Entrada");
                String Salida = rsc.getString("Salida");
                String EntradaSab = rsc.getString("EntradaSabado");
                String SalidaSab = rsc.getString("SalidaSabado");

                // Declarar las variables fuera de los bloques
                String hora = "", minuto = "";
                String horaSa = "", minutoSa = "";
                String horaSab = "", minutoSab = "";
                String horaSabS = "", minutoSabS = "";

                if (Entrada != null && Entrada.contains(":")) {
                    String[] partes = Entrada.split(":");
                    hora = partes[0];
                    minuto = partes[1];
                }
                if (Salida != null && Salida.contains(":")) {
                    String[] partes = Salida.split(":");
                    horaSa = partes[0];
                    minutoSa = partes[1];
                }
                if (EntradaSab != null && EntradaSab.contains(":")) {
                    String[] partes = EntradaSab.split(":");
                    horaSab = partes[0];
                    minutoSab = partes[1];
                }
                if (SalidaSab != null && SalidaSab.contains(":")) {
                    String[] partes = SalidaSab.split(":");
                    horaSabS = partes[0];
                    minutoSabS = partes[1];
                }

                cbEH.setSelectedItem(hora);
                cbEM.setSelectedItem(minuto);
                cbSH.setSelectedItem(horaSa);
                cbSM.setSelectedItem(minutoSa);
                cbESH.setSelectedItem(horaSab);
                cbESM.setSelectedItem(minutoSab);
                cbSSH.setSelectedItem(horaSabS);
                cbSSM.setSelectedItem(minutoSabS);

            }
            String id = null;
            while (rs.next()) {
                id = rs.getString("NumEmpleado");
                txtNombre.setText(rs.getString("Nombre"));
                txtApellido.setText(rs.getString("Apellido"));
                txtDireccion.setText(rs.getString("Direccion"));
                txtTelefono.setText(rs.getString("Telefono"));
                txtCodigo.setText(id);
                cmbPuesto.setSelectedItem(rs.getString("Puesto"));
                cargarPermisos(rs);
            }
            if (id == null) {
                borrarDatos();
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al ver empleado: " + e, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_txtNumeroActionPerformed

    private void txtNumeroKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNumeroKeyTyped
        char c = evt.getKeyChar();
        if (Character.isLowerCase(c)) {
            String cad = ("" + c).toUpperCase();
            c = cad.charAt(0);
            evt.setKeyChar(c);
        }
    }//GEN-LAST:event_txtNumeroKeyTyped

    private void cmbPuestoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbPuestoActionPerformed
        marcarTodos(false);
        Object seleccionado = cmbPuesto.getSelectedItem();

        if (seleccionado == null) {
            return;
        }

        String puesto = seleccionado.toString();

        marcarTodos(false);

        switch (puesto) {

            case "ALMACEN":
                inventario.setSelected(true);
                entregas.setSelected(true);
                pedidos.setSelected(true);
                verRequisiciones.setSelected(true);
                requisiciones.setSelected(true);
                recibos.setSelected(true);
                break;

            case "PUESTO":
            case "LIMPIEZA":
                // Todos los permisos permanecen desmarcados.
                break;

            case "CALIDAD":
                proyectos.setSelected(true);
                reportes.setSelected(true);
                carga.setSelected(true);
                calidad.setSelected(true);
                ensamble.setSelected(true);
                entregas.setSelected(true);
                break;

            case "COMPRAS":
                proyectos.setSelected(true);
                reportes.setSelected(true);
                carga.setSelected(true);
                inventario.setSelected(true);
                entregas.setSelected(true);
                cotizacion.setSelected(true);
                verRequisiciones.setSelected(true);
                requisiciones.setSelected(true);
                compras.setSelected(true);
                aprobacion.setSelected(true);
                recibos.setSelected(true);
                break;

            case "DISEÑO":
                diseno.setSelected(true);
                proyectos.setSelected(true);
                reportes.setSelected(true);
                carga.setSelected(true);
                requisiciones.setSelected(true);
                verRequisiciones.setSelected(true);
                break;

            case "INTEGRACION":
                proyectos.setSelected(true);
                reportes.setSelected(true);
                carga.setSelected(true);
                verRequisiciones.setSelected(true);
                break;

            case "HERRAMENTISTA":
                cortes.setSelected(true);
                fresa.setSelected(true);
                cnc.setSelected(true);
                torno.setSelected(true);
                rectificado.setSelected(true);
                estampado.setSelected(true);
                calidad.setSelected(true);
                break;

            case "VENTAS":
                proyectos.setSelected(true);
                reportes.setSelected(true);
                carga.setSelected(true);
                ventas.setSelected(true);
                project.setSelected(true);
                entregas.setSelected(true);
                verRequisiciones.setSelected(true);
                requisiciones.setSelected(true);
                break;

            case "COSTOS":
                verRequisiciones.setSelected(true);
                requisiciones.setSelected(true);
                cotizacion.setSelected(true);
                break;

            case "IT":
            case "SUPER":
                marcarTodos(true);
                break;
        }
    }//GEN-LAST:event_cmbPuestoActionPerformed

    private void cmbDepartamentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbDepartamentoActionPerformed

    }//GEN-LAST:event_cmbDepartamentoActionPerformed

    private void cbEHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbEHActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbEHActionPerformed

    private void cbxActivoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxActivoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbxActivoActionPerformed

    private void btImagenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btImagenActionPerformed
//        JFileChooser imagen = new JFileChooser();
//        FileNameExtensionFilter filtro = new FileNameExtensionFilter("Archivos de Imágen (jpg, png, jpeg)", "jpg", "jpeg", "png");
//        imagen.setFileFilter(filtro);
//
//        int resultado = imagen.showOpenDialog(this);
//        if (resultado == JFileChooser.APPROVE_OPTION) {
//            File imagenSeleccionada = imagen.getSelectedFile();
//
//            imagenBytes = redimensionarImg(imagenSeleccionada, IMG_WIDTH, IMG_HEIGHT);
//            if (imagenBytes != null) {
//                imagenRedimensionada = new ByteArrayInputStream(imagenBytes);
//                JOptionPane.showMessageDialog(this, "La Imágen ha sido Redimensionada");
//            } else {
//                JOptionPane.showMessageDialog(this, "Error al redimensionar la imagen.");
//            }
//        }
    }//GEN-LAST:event_btImagenActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        if (txtCodigo.getText().equals("")) {
            guardar();
        } else {
            actualizar();
        }
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void txtCodigoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoKeyTyped
        char c = evt.getKeyChar();
        if (Character.isLowerCase(c)) {
            String cad = ("" + c).toUpperCase();
            c = cad.charAt(0);
            evt.setKeyChar(c);
        }
    }//GEN-LAST:event_txtCodigoKeyTyped


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox aprobacion;
    private javax.swing.JButton btImagen;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JCheckBox calidad;
    private javax.swing.JCheckBox carga;
    private javax.swing.JComboBox<String> cbEH;
    private javax.swing.JComboBox<String> cbEM;
    private javax.swing.JComboBox<String> cbESH;
    private javax.swing.JComboBox<String> cbESM;
    private javax.swing.JComboBox<String> cbSH;
    private javax.swing.JComboBox<String> cbSM;
    private javax.swing.JComboBox<String> cbSSH;
    private javax.swing.JComboBox<String> cbSSM;
    private javax.swing.JCheckBox cbxActivo;
    private javax.swing.JCheckBox cbxAdmin;
    private javax.swing.JCheckBox checador;
    private RSMaterialComponent.RSComboBoxMaterial cmbDepartamento;
    private RSMaterialComponent.RSComboBoxMaterial cmbPuesto;
    private javax.swing.JCheckBox cnc;
    private javax.swing.JCheckBox compras;
    private javax.swing.JCheckBox cortes;
    private javax.swing.JCheckBox costos;
    private javax.swing.JCheckBox cotizacion;
    private javax.swing.JCheckBox cxp;
    private javax.swing.JCheckBox dashboard;
    private javax.swing.JCheckBox diseno;
    private javax.swing.JCheckBox ensamble;
    private javax.swing.JCheckBox entregas;
    private javax.swing.JCheckBox envios;
    private javax.swing.JCheckBox estampado;
    private javax.swing.JCheckBox evaluacion;
    private javax.swing.JCheckBox fresa;
    private javax.swing.JCheckBox inventario;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JCheckBox liberacion;
    private javax.swing.JCheckBox nuevo;
    private javax.swing.JCheckBox orden;
    private javax.swing.JCheckBox pedidos;
    private javax.swing.JCheckBox prestamos;
    private javax.swing.JCheckBox project;
    private javax.swing.JCheckBox proyectos;
    private javax.swing.JCheckBox recibos;
    private javax.swing.JCheckBox rectificado;
    private javax.swing.JCheckBox remisiones;
    private javax.swing.JCheckBox reportes;
    private javax.swing.JCheckBox requisiciones;
    private javax.swing.JCheckBox rh;
    private javax.swing.JCheckBox torno;
    private RSMaterialComponent.RSTextFieldMaterial txtApellido;
    private javax.swing.JTextField txtCodigo;
    private RSMaterialComponent.RSPasswordMaterial txtContra;
    private RSMaterialComponent.RSTextFieldMaterial txtDireccion;
    private RSMaterialComponent.RSTextFieldMaterial txtNombre;
    private RSMaterialComponent.RSTextFieldMaterial txtNumero;
    private RSMaterialComponent.RSPasswordMaterial txtRepetir;
    private RSMaterialComponent.RSTextFieldMaterial txtTelefono;
    private javax.swing.JCheckBox ventas;
    private javax.swing.JCheckBox verRequisiciones;
    // End of variables declaration//GEN-END:variables
}
