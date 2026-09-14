package pruebas;

import Conexiones.Conexion;
import Conexiones.ConexionChat;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;
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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.rowset.serial.SerialBlob;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class RegistrarEmpleado extends javax.swing.JFrame {

    String puesto,departamento;
    public RegistrarEmpleado() {
        initComponents();
        pack();
//        deshabilitar();
        txtCodigo.setEnabled(false);
        this.setTitle("SERVICIOS INDUSTRIALES 3i");
        this.setIconImage(new ImageIcon(getClass().getResource("/Imagenes/Imagen1.png")).getImage());
    }
    private String HoraEntrada;
    private String HoraSalida;
    private String HoraEnSab;
    private String HoraSalSab;
    private long segundos =00;
    private long Semana=0;
    private long Sabado=0;
    //imagen Empleado
    private File imagenSeleccionada = null;
    private byte[] imagenBytes = null;
    private ByteArrayInputStream imagenRedimensionada = null;
    private final int IMG_WIDTH = 200;  //Ancho de como se guarda la imagen
    private final int IMG_HEIGHT = 200; //Altura de como se guarda la imagen
    
    public void FormatoHrs(){
        String HorasEn = (String) cbEH.getSelectedItem();
        String MinEn = (String) cbEM.getSelectedItem();
        String HorasSa=(String) cbSH.getSelectedItem();
        String MinSa=(String) cbSM.getSelectedItem();
        String HorasEnS=(String) cbESH.getSelectedItem();
        String MinEnS=(String)cbESM.getSelectedItem();
        String HorsSaS=(String) cbSSH.getSelectedItem();
        String MinSaS=(String) cbSSM.getSelectedItem();
        
        int HrsEn =Integer.parseInt(HorasEn);
        int MinE=Integer.parseInt(MinEn);
        int HrsSa=Integer.parseInt(HorasSa);
        int MinS=Integer.parseInt(MinSa);
        int HrsES=Integer.parseInt(HorasEnS);
        int MinES=Integer.parseInt(MinEnS);
        int HrsSS=Integer.parseInt(HorsSaS);
        int MinSS = Integer.parseInt(MinSaS);
        
        HoraEntrada = String.format("%02d:%02d:%02d",HrsEn,MinE,segundos);
        HoraSalida = String.format("%02d:%02d:%02d",HrsSa,MinS,segundos);
        HoraEnSab= String.format("%02d:%02d:%02d",HrsES,MinES,segundos);
        HoraSalSab= String.format("%02d:%02d:%02d",HrsSS,MinSS,segundos);
    };
    private String HorasDiarias;
    private String HorasSab;
    
    public void HorasTotales(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        if (HoraEntrada != null && HoraSalida != null) {
            LocalTime entrada = LocalTime.parse(HoraEntrada, formatter);
            LocalTime salida = LocalTime.parse(HoraSalida, formatter);

            Duration duracion = Duration.between(entrada, salida);
            long horas = duracion.toHours();
            long minutos = duracion.toMinutes() % 60;

            HorasDiarias = String.format("%02d:%02d:%02d", horas, minutos,segundos);
            Semana += duracion.toMinutes(); // se acumula
            
        }
        if (HoraEnSab != null && HoraSalSab != null) {
            LocalTime entradaS = LocalTime.parse(HoraEnSab, formatter);
            LocalTime salidaS = LocalTime.parse(HoraSalSab, formatter);
            Duration duracionSab = Duration.between(entradaS, salidaS);
            long horasSab = duracionSab.toHours();
            long minutosSab = duracionSab.toMinutes() % 60;

            HorasSab = String.format("%02d:%02d:%02d", horasSab, minutosSab,segundos);
            Sabado += duracionSab.toMinutes(); // también se acumula
        }
    };
    private String horasSemana;
    public void HorasSemanales(){
        long totalSemana=(Semana*5)+Sabado;
        long horas = Math.round(totalSemana/60);
        horasSemana=Long.toString(horas);
        
    };
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
            JOptionPane.showMessageDialog(this, "Hora de Entrada No Asignada","ADVERTENCIA",JOptionPane.ERROR_MESSAGE);
        }
    }
    private boolean Activo;
    public void Activo(){
    if(cbxActivo.isSelected()){
        Activo=true;
    }
    else{
        Activo=false;
    }
    };
    private int admin;
    public void Admin(){
        if(cbxAdmin.isSelected()){
            admin=1;
        }
        else{
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
    public void actualizar(){
        if(txtNombre.getText().isEmpty()){
            JOptionPane.showMessageDialog(this, "DEBES SELECCIONAR UN EMPLEADO","ADVERTENCIA",JOptionPane.ERROR_MESSAGE);
        }else{
            try{
                Connection con = null;
                Conexion con1 = new Conexion();
                con = con1.getConnection();
                Statement st = con.createStatement();
                String sql = "update registroempleados set Nombre = ?,Apellido = ?,Direccion = ?,Telefono = ?,Puesto = ?,Diseño = ?,Cambio = ?,Reportes = "
                        + "?,Carga = ?,Ventas = ?,Cortes = ?,Fresa = ?,Cnc = ?,Torno = ?,Acabados = ?,Calidad = ?,Tratamiento = ?,Electrico = ?,"
                        + "CrearEmpleado = ?,VerEmpleado = ?,Inventario = ?,Ensamble = ?,InventarioPlanos = ?,Requisiciones = ?,Orden = ?,Aprobacion = ?,"
                        + "Recibo = ?,Prestamo = ?,Cotizacion = ?,VerRequisiciones = ?,Cotizar = ?, ProyectMan = ?, Checador = ?, Entrega = ?,Pedidos = ?, rh = ? where NumEmpleado = ?";
                PreparedStatement pst = con.prepareStatement(sql);
                
                pst.setString(1, txtNombre.getText());
                pst.setString(2, txtApellido.getText());
                pst.setString(3, txtDireccion.getText());
                pst.setString(4, txtTelefono.getText());
                pst.setString(5, (String) cmbPuesto.getSelectedItem());
                pst.setBoolean(6, diseño.isSelected());
                pst.setBoolean(7, estados.isSelected());
                pst.setBoolean(8, reportes.isSelected());
                pst.setBoolean(9, carga.isSelected());
                pst.setBoolean(10, ventas.isSelected());
                pst.setBoolean(11, cortes.isSelected());
                pst.setBoolean(12, fresadora.isSelected());
                pst.setBoolean(13, cnc.isSelected());
                pst.setBoolean(14, torno.isSelected());
                pst.setBoolean(15, acabados.isSelected());
                pst.setBoolean(16, calidad.isSelected());
                pst.setBoolean(17, tratamiento.isSelected());
                pst.setBoolean(18, electrico.isSelected());
                pst.setBoolean(19, crear.isSelected());
                pst.setBoolean(20, verEmpleado.isSelected());
                pst.setBoolean(21, inventario.isSelected());
                pst.setBoolean(22, ensambles.isSelected());
                pst.setBoolean(23, invPlanos.isSelected());
                pst.setBoolean(24, requisiciones.isSelected());
                pst.setBoolean(25, compras.isSelected());
                pst.setBoolean(26, aprobacion.isSelected());
                pst.setBoolean(27, recibos.isSelected());
                pst.setBoolean(28, prestamos.isSelected());
                pst.setBoolean(29, cotizacion.isSelected());
                pst.setBoolean(30, verRequisicion.isSelected());
                pst.setBoolean(31, cotizar.isSelected());
                pst.setBoolean(32, proyectos.isSelected());
                pst.setBoolean(33, checador.isSelected());
                pst.setBoolean(34, entrega.isSelected());
                pst.setBoolean(35, pedidos.isSelected());
                pst.setBoolean(36, rh.isSelected());
                pst.setString(37, txtCodigo.getText());

                int n = pst.executeUpdate();
                String SqlCheck="UPDATE empleadoscheck SET Nombre=?,Entrada=?,Salida=?,HorasDiarias=?,Turno=?,HoraSabado=?,EntradaSabado=?,SalidaSabado=?,"
                        + "TotalHoras=?,Departamento=?,Activo=?,Administrador=? where numEmpleado=?";
                PreparedStatement ps1=con.prepareStatement(SqlCheck);
                FormatoHrs();
                HorasTotales();
                HorasSemanales();
                    Turno();
                    Activo();
                    Admin();
                String Nombre=txtNombre.getText()+ " "+txtApellido.getText();
                ps1.setString(1, Nombre);
                ps1.setString(2,HoraEntrada);
                ps1.setString(3,HoraSalida);
                ps1.setString(4,HorasDiarias);
                ps1.setString(5,turno);
                ps1.setString(6,HorasSab);
                ps1.setString(7,HoraEnSab);
                ps1.setString(8,HoraSalSab);
                ps1.setString(9,horasSemana);
                ps1.setString(10,departamento);
                ps1.setBoolean(11,Activo);
                ps1.setInt(12, admin);
                ps1.setString(13, txtCodigo.getText());
                ps1.executeUpdate();
                if (imagenBytes != null && imagenRedimensionada != null) {
                    imagenRedimensionada.reset();

                    // Guardar en base de datos
                    String sqlimg = "INSERT INTO imagenempleados (idEmpleado, imagen) VALUES (?, ?) " +
                                    "ON DUPLICATE KEY UPDATE imagen = VALUES(imagen)";
                    PreparedStatement psi = con.prepareStatement(sqlimg);
                    psi.setString(1, txtCodigo.getText());
                    psi.setBinaryStream(2, imagenRedimensionada);
                    psi.executeUpdate();

                    // Guardar en servidor local
                    try {
                        String rutaDestino = "\\\\192.168.100.40\\01 Shared\\14 RH\\Empleados\\";
                        String nombreArchivo = txtNumero.getText()+" " + txtNombre.getText()+" "+txtApellido.getText()+ ".jpg";
                        File destino = new File(rutaDestino + nombreArchivo);
                        Files.write(destino.toPath(), imagenBytes, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
                        System.out.println("Imagen guardada en servidor local.");
                    } catch (IOException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(this, "No se pudo guardar la imagen en el servidor", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }


                if(n > 0){
                    JOptionPane.showMessageDialog(this, "DATOS ACTUALIZADOS CORRECTAMENTE");
                }

            }catch(SQLException e){
                JOptionPane.showMessageDialog(this, "ERROR: "+e,"ERROR",JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    public void guardar(){
        if(txtNombre.getText().isEmpty()){

            JOptionPane.showMessageDialog(this, "EL CAMPO NOMBRE ESTA VACIO","ADVERTENCIA",JOptionPane.WARNING_MESSAGE);

        }else if(txtApellido.getText().isEmpty()){

            JOptionPane.showMessageDialog(this, "EL CAMPO APELLIDO ESTA VACIO","ADVERTENCIA",JOptionPane.WARNING_MESSAGE);

        }else if(txtDireccion.getText().isEmpty()){
            JOptionPane.showMessageDialog(this, "EL CAMPO DIRECCION ESTA VACIO","ADVERTENCIA",JOptionPane.WARNING_MESSAGE);
        }else if(txtTelefono.getText().isEmpty()){
            JOptionPane.showMessageDialog(this, "EL CAMPO TELEFONO ESTA VACIO","ADVERTENCIA",JOptionPane.WARNING_MESSAGE);
        }else if(cmbPuesto.getSelectedItem().equals("PUESTO")){
            JOptionPane.showMessageDialog(this, "EL CAMPO PUESTO AUN NO A SIDO SELECCIONADO","ADVERTENCIA",JOptionPane.WARNING_MESSAGE);
        }else if(cmbDepartamento.getSelectedItem().equals("DEPARTAMENTO")){
            JOptionPane.showMessageDialog(this, "EL CAMPO DEPARTAMENTO AUN NO A SIDO SELECCIONADO","ADVERTENCIA",JOptionPane.WARNING_MESSAGE);
        }else if(txtContra.getText().isEmpty()){
            JOptionPane.showMessageDialog(this, "EL CAMPO CONTRASEÑA ESTA VACIO","ADVERTENCIA",JOptionPane.WARNING_MESSAGE);
        }else if(txtRepetir.getText().isEmpty()){
            JOptionPane.showMessageDialog(this, "EL CAMPO REPETIR CONTRASEÑA ESTA VACIO","ADVERTENCIA",JOptionPane.WARNING_MESSAGE);
        }else if(txtContra.getText() == null ? txtRepetir.getText() != null : !txtContra.getText().equals(txtRepetir.getText())){
            JOptionPane.showMessageDialog(this, "LA CONTRASEÑA NO COINCIDE","",JOptionPane.WARNING_MESSAGE);
        }else if(txtNumero.getText() == null ? txtRepetir.getText() != null : !txtContra.getText().equals(txtRepetir.getText())){
            JOptionPane.showMessageDialog(this, "EL CAMPO NUMERO DE EMPLEADO ESTA VACIO","ADVERTENCIA",JOptionPane.WARNING_MESSAGE);
        }else
        {
            {
                
                puesto = (String) cmbPuesto.getSelectedItem();
                departamento = (String) cmbDepartamento.getSelectedItem();
                try{

                    Connection con2 = null;
                    Conexion c = new Conexion();
                    con2 = c.getConnection();
                    Statement st3 = con2.createStatement();
                    String sql = "insert into registroEmpleados (Nombre,Apellido,Direccion,Telefono,Puesto,Contraseña,NumEmpleado,Diseño,Cambio,Reportes,"
                            + "Carga,Ventas,Cortes,Fresa,Cnc,Torno,Acabados,Calidad,Tratamiento,Electrico,CrearEmpleado,VerEmpleado,Inventario,Ensamble,"
                            + "InventarioPlanos,Requisiciones,Orden,Aprobacion,Recibo,Prestamo,Cotizacion,VerRequisiciones,Cotizar,ProyectMan, Checador, Entrega, Pedidos,"
                            + "Herramentista, Disenio, Administracion, Almacen, Super,Remisiones,Puerto,rh) "
                            + "values (?,?,?,?,?,AES_ENCRYPT(?,'mi_llave'),?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                    PreparedStatement pst3 = con2.prepareStatement(sql);
                  
                    byte dato[] = txtContra.getText().getBytes();
                    Blob blob= new SerialBlob(dato);
                
                    pst3.setString(1, txtNombre.getText());
                    pst3.setString(2, txtApellido.getText());
                    pst3.setString(3, txtDireccion.getText());
                    pst3.setString(4, txtTelefono.getText());
                    pst3.setString(5, puesto);
                    pst3.setBlob(6, blob);
                    pst3.setString(7, txtNumero.getText());
                    pst3.setBoolean(8, diseño.isSelected());
                    pst3.setBoolean(9, estados.isSelected());
                    pst3.setBoolean(10, reportes.isSelected());
                    pst3.setBoolean(11, carga.isSelected());
                    pst3.setBoolean(12, ventas.isSelected());
                    pst3.setBoolean(13, cortes.isSelected());
                    pst3.setBoolean(14, fresadora.isSelected());
                    pst3.setBoolean(15, cnc.isSelected());
                    pst3.setBoolean(16, torno.isSelected());
                    pst3.setBoolean(17, acabados.isSelected());
                    pst3.setBoolean(18, calidad.isSelected());
                    pst3.setBoolean(19, tratamiento.isSelected());
                    pst3.setBoolean(20, electrico.isSelected());
                    pst3.setBoolean(21, crear.isSelected());
                    pst3.setBoolean(22, verEmpleado.isSelected());
                    pst3.setBoolean(23, inventario.isSelected());
                    pst3.setBoolean(24, ensambles.isSelected());
                    pst3.setBoolean(25, invPlanos.isSelected());
                    pst3.setBoolean(26, requisiciones.isSelected());
                    pst3.setBoolean(27, compras.isSelected());
                    pst3.setBoolean(28, aprobacion.isSelected());
                    pst3.setBoolean(29, recibos.isSelected());
                    pst3.setBoolean(30, prestamos.isSelected());
                    pst3.setBoolean(31, cotizacion.isSelected());
                    pst3.setBoolean(32, verRequisicion.isSelected());
                    pst3.setBoolean(33, cotizar.isSelected());
                    pst3.setBoolean(34, proyectos.isSelected());
                    pst3.setBoolean(35, checador.isSelected());
                    pst3.setBoolean(36, entrega.isSelected());
                    pst3.setBoolean(37, pedidos.isSelected());
                    
                    pst3.setString(38, "NO");
                    pst3.setString(39, "NO");
                    pst3.setString(40, "NO");
                    pst3.setString(41, "NO");
                    pst3.setString(42, "NO");
                    pst3.setString(43, "0");
                    
                    pst3.setBoolean(44, rh.isSelected());
                    
                    String sql4 = "select Puerto from registroempleados";
                    Statement st4 = con2.createStatement();
                    ResultSet rs4 = st4.executeQuery(sql4);
                    int puerto = 0;
                    while(rs4.next()){
                        try{
                            puerto = Integer.parseInt(rs4.getString("Puerto")) + 5;
                        }catch(NumberFormatException e){
                            puerto = 0;
                        }
                    }
                    pst3.setString(44, String.valueOf(puerto));
                    int n = pst3.executeUpdate();
                    
                    String sqlCheck= "INSERT INTO empleadoscheck (NumEmpleado, Nombre,Entrada,Salida,HorasDiarias,Turno,HoraSabado,EntradaSabado,"
                    + "SalidaSabado,TotalHoras,Departamento,Activo,Administrador) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
                    PreparedStatement ps1=con2.prepareStatement(sqlCheck);
                    FormatoHrs();
                    HorasTotales();
                    HorasSemanales();
                    Turno();
                    Activo();
                    Admin();
                    ps1.setString(1,txtNumero.getText());
                    String Nombre=txtNombre.getText()+ " "+txtApellido.getText();
                    ps1.setString(2, Nombre);
                    ps1.setString(3,HoraEntrada);
                    ps1.setString(4,HoraSalida);
                    ps1.setString(5,HorasDiarias);
                    ps1.setString(6,turno);
                    ps1.setString(7,HorasSab);
                    ps1.setString(8,HoraEnSab);
                    ps1.setString(9,HoraSalSab);
                    ps1.setString(10,horasSemana);
                    ps1.setString(11, departamento);
                    ps1.setBoolean(12,Activo);
                    ps1.setInt(13,admin);
                    ps1.executeUpdate();
                  
                    String crearNotificacion = "CREATE TABLE `noti"+txtNumero.getText()+"` (\n" +
                            "  `Id` int NOT NULL AUTO_INCREMENT,\n" +
                            "  `Departamento` varchar(45) DEFAULT NULL,\n" +
                            "  `Titulo` varchar(100) DEFAULT NULL,\n" +
                            "  `Texto` varchar(500) DEFAULT NULL,\n" +
                            "  `Visto` varchar(45) DEFAULT NULL,\n" +
                            "  `Fecha` varchar(45) DEFAULT NULL,\n" +
                            "  `Visto2` varchar(45) DEFAULT NULL,\n" +
                            "  PRIMARY KEY (`Id`)\n" +
                            ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci";
                    
                    Connection con3;
                    ConexionChat con4 = new ConexionChat();
                    con3 = con4.getConnection();
                    Statement st5 = con3.createStatement();
                    st5.execute(crearNotificacion);
                    
                    if(n>0){

                        JOptionPane.showMessageDialog(this, "DATOS GUARDADOS");
                        borrarDatos();
                        deshabilitar();
                    }

                }catch(SQLException ex){

                    JOptionPane.showMessageDialog(this, "ERROR AL GUARDAR DATOS"+ex);

                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(RegistrarEmpleado.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        }
    }
    
    public void deshabilitar(){
    
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
    public void habilitar(){
    
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
    
    
    public void borrarDatos(){
    
        txtNombre.setText("");
        txtApellido.setText("");
        txtDireccion.setText("");
        txtTelefono.setText("");
        cmbPuesto.setSelectedItem("PUESTO");
        txtContra.setText("");
        txtRepetir.setText("");
        cmbDepartamento.setSelectedItem("DEPARTAMENTO");
        cbESH.setSelectedItem("00");
        cbESM.setSelectedItem("00");
        cbSSH.setSelectedItem("00");
        cbSSM.setSelectedItem("00");
    
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel7 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        btnGuardar = new javax.swing.JButton();
        jPanel8 = new javax.swing.JPanel();
        txtCodigo = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        jPanel19 = new javax.swing.JPanel();
        txtNombre = new RSMaterialComponent.RSTextFieldMaterial();
        txtApellido = new RSMaterialComponent.RSTextFieldMaterial();
        jPanel20 = new javax.swing.JPanel();
        txtDireccion = new RSMaterialComponent.RSTextFieldMaterial();
        txtTelefono = new RSMaterialComponent.RSTextFieldMaterial();
        jPanel21 = new javax.swing.JPanel();
        txtNumero = new RSMaterialComponent.RSTextFieldMaterial();
        jPanel22 = new javax.swing.JPanel();
        cmbPuesto = new RSMaterialComponent.RSComboBoxMaterial();
        cmbDepartamento = new RSMaterialComponent.RSComboBoxMaterial();
        jPanel23 = new javax.swing.JPanel();
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
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        diseño = new rojerusan.RSCheckBox();
        estados = new rojerusan.RSCheckBox();
        reportes = new rojerusan.RSCheckBox();
        carga = new rojerusan.RSCheckBox();
        ventas = new rojerusan.RSCheckBox();
        checador = new rojerusan.RSCheckBox();
        proyectos = new rojerusan.RSCheckBox();
        proyectM = new rojerusan.RSCheckBox();
        jPanel3 = new javax.swing.JPanel();
        cortes = new rojerusan.RSCheckBox();
        fresadora = new rojerusan.RSCheckBox();
        cnc = new rojerusan.RSCheckBox();
        torno = new rojerusan.RSCheckBox();
        acabados = new rojerusan.RSCheckBox();
        calidad = new rojerusan.RSCheckBox();
        tratamiento = new rojerusan.RSCheckBox();
        electrico = new rojerusan.RSCheckBox();
        jPanel4 = new javax.swing.JPanel();
        crear = new rojerusan.RSCheckBox();
        ensambles = new rojerusan.RSCheckBox();
        verEmpleado = new rojerusan.RSCheckBox();
        inventario = new rojerusan.RSCheckBox();
        invPlanos = new rojerusan.RSCheckBox();
        remisiones = new rojerusan.RSCheckBox();
        entrega = new rojerusan.RSCheckBox();
        pedidos = new rojerusan.RSCheckBox();
        jPanel5 = new javax.swing.JPanel();
        cotizar = new rojerusan.RSCheckBox();
        verRequisicion = new rojerusan.RSCheckBox();
        requisiciones = new rojerusan.RSCheckBox();
        compras = new rojerusan.RSCheckBox();
        aprobacion = new rojerusan.RSCheckBox();
        recibos = new rojerusan.RSCheckBox();
        prestamos = new rojerusan.RSCheckBox();
        cotizacion = new rojerusan.RSCheckBox();
        verRequisicion1 = new rojerusan.RSCheckBox();
        rh = new rojerusan.RSCheckBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));

        jPanel7.setBackground(new java.awt.Color(243, 241, 241));
        jPanel7.setLayout(new java.awt.BorderLayout());

        jPanel11.setBackground(new java.awt.Color(243, 241, 241));

        btnGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/guardar_32.png"))); // NOI18N
        btnGuardar.setBorder(null);
        btnGuardar.setBorderPainted(false);
        btnGuardar.setContentAreaFilled(false);
        btnGuardar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnGuardar.setFocusPainted(false);
        btnGuardar.setPreferredSize(new java.awt.Dimension(50, 50));
        btnGuardar.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/guardar_32.png"))); // NOI18N
        btnGuardar.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/guardar_48.png"))); // NOI18N
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnGuardar, javax.swing.GroupLayout.DEFAULT_SIZE, 1248, Short.MAX_VALUE)
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel7.add(jPanel11, java.awt.BorderLayout.SOUTH);

        jPanel8.setBackground(new java.awt.Color(243, 241, 241));

        txtCodigo.setForeground(new java.awt.Color(255, 255, 255));
        txtCodigo.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCodigo.setBorder(null);
        txtCodigo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCodigoKeyTyped(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Roboto Black", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(102, 102, 102));
        jLabel1.setText("Registrar Empleados");
        jLabel1.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 0, 0, new java.awt.Color(0, 102, 204)));

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(462, 462, 462)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(415, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21))
            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING)
        );

        jPanel7.add(jPanel8, java.awt.BorderLayout.PAGE_START);

        jPanel9.setLayout(new java.awt.GridLayout(1, 0));

        jPanel10.setBackground(new java.awt.Color(255, 255, 255));
        jPanel10.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Empleado", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 16))); // NOI18N

        jPanel19.setBackground(new java.awt.Color(255, 255, 255));

        txtNombre.setForeground(new java.awt.Color(51, 51, 51));
        txtNombre.setColorMaterial(new java.awt.Color(0, 51, 51));
        txtNombre.setPhColor(new java.awt.Color(102, 102, 102));
        txtNombre.setPlaceholder("Nombre(s)");
        txtNombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNombreActionPerformed(evt);
            }
        });
        txtNombre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNombreKeyTyped(evt);
            }
        });

        txtApellido.setForeground(new java.awt.Color(51, 51, 51));
        txtApellido.setColorMaterial(new java.awt.Color(51, 51, 51));
        txtApellido.setPhColor(new java.awt.Color(102, 102, 102));
        txtApellido.setPlaceholder("Apellido(s)");
        txtApellido.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtApellidoKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(txtNombre, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(5, 5, 5)
                .addComponent(txtApellido, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtNombre, javax.swing.GroupLayout.DEFAULT_SIZE, 45, Short.MAX_VALUE)
                    .addComponent(txtApellido, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(27, Short.MAX_VALUE))
        );

        jPanel20.setBackground(new java.awt.Color(255, 255, 255));

        txtDireccion.setForeground(new java.awt.Color(51, 51, 51));
        txtDireccion.setColorMaterial(new java.awt.Color(51, 51, 51));
        txtDireccion.setPhColor(new java.awt.Color(102, 102, 102));
        txtDireccion.setPlaceholder("Direccion");
        txtDireccion.setSelectionColor(new java.awt.Color(102, 102, 102));
        txtDireccion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDireccionKeyTyped(evt);
            }
        });

        txtTelefono.setForeground(new java.awt.Color(51, 51, 51));
        txtTelefono.setColorMaterial(new java.awt.Color(51, 51, 51));
        txtTelefono.setPhColor(new java.awt.Color(102, 102, 102));
        txtTelefono.setPlaceholder("Telefono");
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

        javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(txtDireccion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(5, 5, 5)
                .addComponent(txtTelefono, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel20Layout.setVerticalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtDireccion, javax.swing.GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE)
                    .addComponent(txtTelefono, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        jPanel21.setBackground(new java.awt.Color(255, 255, 255));

        txtNumero.setForeground(new java.awt.Color(51, 51, 51));
        txtNumero.setColorMaterial(new java.awt.Color(51, 51, 51));
        txtNumero.setPhColor(new java.awt.Color(102, 102, 102));
        txtNumero.setPlaceholder("Numero de empleado");
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

        javax.swing.GroupLayout jPanel21Layout = new javax.swing.GroupLayout(jPanel21);
        jPanel21.setLayout(jPanel21Layout);
        jPanel21Layout.setHorizontalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addGap(140, 140, 140)
                .addComponent(txtNumero, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel21Layout.setVerticalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(txtNumero, javax.swing.GroupLayout.DEFAULT_SIZE, 43, Short.MAX_VALUE)
                .addContainerGap(27, Short.MAX_VALUE))
        );

        jPanel22.setBackground(new java.awt.Color(255, 255, 255));

        cmbPuesto.setForeground(new java.awt.Color(102, 102, 102));
        cmbPuesto.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "PUESTO", "ALMACEN", "CALIDAD", "COMPRAS", "DISEÑO", "INTEGRACION", "LIMPIEZA", "HERRAMENTISTA", "VENTAS", "COSTOS", "IT", "SUPER" }));
        cmbPuesto.setColorMaterial(new java.awt.Color(51, 51, 51));
        cmbPuesto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbPuestoActionPerformed(evt);
            }
        });

        cmbDepartamento.setForeground(new java.awt.Color(102, 102, 102));
        cmbDepartamento.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "DEPARTAMENTO", "CI ADMINITRACION", "MOI TECNICO CALIDAD INDIRECTO", "MOD HERRAMENTISTA", "MOI PROGRAMACION", "MOI ALMACEN", "MOD ELECTROMECANICO", "MOI DISENADOR", " ", " " }));
        cmbDepartamento.setColorMaterial(new java.awt.Color(51, 51, 51));
        cmbDepartamento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbDepartamentoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel22Layout = new javax.swing.GroupLayout(jPanel22);
        jPanel22.setLayout(jPanel22Layout);
        jPanel22Layout.setHorizontalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(cmbPuesto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(cmbDepartamento, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(52, 52, 52))
        );
        jPanel22Layout.setVerticalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cmbPuesto, javax.swing.GroupLayout.DEFAULT_SIZE, 47, Short.MAX_VALUE)
                    .addComponent(cmbDepartamento, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(27, Short.MAX_VALUE))
        );

        jPanel23.setBackground(new java.awt.Color(255, 255, 255));

        txtContra.setForeground(new java.awt.Color(51, 51, 51));
        txtContra.setColorMaterial(new java.awt.Color(51, 51, 51));
        txtContra.setPhColor(new java.awt.Color(102, 102, 102));
        txtContra.setPlaceholder("Contraseña");
        jPanel23.add(txtContra);

        txtRepetir.setForeground(new java.awt.Color(51, 51, 51));
        txtRepetir.setColorMaterial(new java.awt.Color(51, 51, 51));
        txtRepetir.setPhColor(new java.awt.Color(102, 102, 102));
        txtRepetir.setPlaceholder("Repetir contraseña");
        jPanel23.add(txtRepetir);

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 2, 16)); // NOI18N
        jLabel2.setText("Entrada");

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

        cbEM.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        cbEM.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "00", "30", " " }));
        cbEM.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 2, 16)); // NOI18N
        jLabel7.setText("Salida");

        cbSH.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        cbSH.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", " " }));
        cbSH.setSelectedIndex(17);
        cbSH.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        cbSM.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        cbSM.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "00", "30", " " }));
        cbSM.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(79, 79, 79)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbEH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbEM, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(49, 49, 49)
                .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(6, 6, 6)
                .addComponent(cbSH, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(cbSM, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(21, 21, 21))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cbEH, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cbEM, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cbSH)
                    .addComponent(cbSM))
                .addContainerGap(40, Short.MAX_VALUE))
        );

        jPanel14.setBackground(new java.awt.Color(255, 255, 255));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 2, 16)); // NOI18N
        jLabel4.setText("Entrada Sábado");

        cbESH.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        cbESH.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", " " }));
        cbESH.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        cbESM.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        cbESM.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "00", "30", " " }));
        cbESM.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 2, 16)); // NOI18N
        jLabel8.setText("Salida Sábado");

        cbSSH.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        cbSSH.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "" }));
        cbSSH.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        cbSSM.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        cbSSM.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "00", "30", " " }));
        cbSSM.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addComponent(jLabel4)
                .addGap(5, 5, 5)
                .addComponent(cbESH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addComponent(cbESM, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbSSH, 0, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addComponent(cbSSM, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addGap(7, 7, 7)
                        .addComponent(jLabel4))
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addComponent(cbESH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addComponent(cbESM, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addGap(7, 7, 7)
                        .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, 37, Short.MAX_VALUE))
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cbSSM)
                            .addComponent(cbSSH, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(27, Short.MAX_VALUE))
        );

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

        cbxAdmin.setBackground(new java.awt.Color(255, 255, 255));
        cbxAdmin.setFont(new java.awt.Font("Segoe UI", 2, 16)); // NOI18N
        cbxAdmin.setText("ADMINISTRADOR");
        cbxAdmin.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addGap(121, 121, 121)
                .addComponent(cbxActivo)
                .addGap(61, 61, 61)
                .addComponent(cbxAdmin)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbxAdmin)
                    .addComponent(cbxActivo))
                .addContainerGap(33, Short.MAX_VALUE))
        );

        jPanel18.setBackground(new java.awt.Color(255, 255, 255));

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel6.setText("Imagen del Empleado:");

        btImagen.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btImagen.setText("Seleccionar Imagen");
        btImagen.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btImagen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btImagenActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addGap(75, 75, 75)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btImagen, javax.swing.GroupLayout.DEFAULT_SIZE, 228, Short.MAX_VALUE)
                .addGap(158, 158, 158))
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel18Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(btImagen, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel20, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel21, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jPanel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel23, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(1, 1, 1)
                .addComponent(jPanel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel23, javax.swing.GroupLayout.DEFAULT_SIZE, 72, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(29, 29, 29)
                .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel9.add(jPanel10);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder(null, "", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12)), "ACCESOS", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14))); // NOI18N
        jPanel1.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(204, 204, 204)));

        diseño.setForeground(new java.awt.Color(51, 51, 51));
        diseño.setText("Diseño");
        diseño.setColorCheck(new java.awt.Color(102, 102, 102));
        diseño.setColorUnCheck(new java.awt.Color(51, 51, 51));
        diseño.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N

        estados.setForeground(new java.awt.Color(51, 51, 51));
        estados.setText("Estados");
        estados.setColorCheck(new java.awt.Color(102, 102, 102));
        estados.setColorUnCheck(new java.awt.Color(51, 51, 51));
        estados.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N

        reportes.setForeground(new java.awt.Color(51, 51, 51));
        reportes.setText("Reportes");
        reportes.setColorCheck(new java.awt.Color(102, 102, 102));
        reportes.setColorUnCheck(new java.awt.Color(51, 51, 51));
        reportes.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N

        carga.setForeground(new java.awt.Color(51, 51, 51));
        carga.setText("Carga de trabajo");
        carga.setColorCheck(new java.awt.Color(102, 102, 102));
        carga.setColorUnCheck(new java.awt.Color(51, 51, 51));
        carga.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N

        ventas.setForeground(new java.awt.Color(51, 51, 51));
        ventas.setText("Ventas");
        ventas.setColorCheck(new java.awt.Color(102, 102, 102));
        ventas.setColorUnCheck(new java.awt.Color(51, 51, 51));
        ventas.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N

        checador.setForeground(new java.awt.Color(51, 51, 51));
        checador.setText("Checador");
        checador.setColorCheck(new java.awt.Color(102, 102, 102));
        checador.setColorUnCheck(new java.awt.Color(51, 51, 51));
        checador.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N

        proyectos.setForeground(new java.awt.Color(51, 51, 51));
        proyectos.setText("Proyectos");
        proyectos.setColorCheck(new java.awt.Color(102, 102, 102));
        proyectos.setColorUnCheck(new java.awt.Color(51, 51, 51));
        proyectos.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N

        proyectM.setForeground(new java.awt.Color(51, 51, 51));
        proyectM.setText("Proyect Manager");
        proyectM.setColorCheck(new java.awt.Color(102, 102, 102));
        proyectM.setColorUnCheck(new java.awt.Color(51, 51, 51));
        proyectM.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(diseño, javax.swing.GroupLayout.DEFAULT_SIZE, 152, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addComponent(estados, javax.swing.GroupLayout.DEFAULT_SIZE, 154, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addComponent(reportes, javax.swing.GroupLayout.DEFAULT_SIZE, 155, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addComponent(carga, javax.swing.GroupLayout.DEFAULT_SIZE, 155, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(ventas, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addComponent(checador, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addComponent(proyectos, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addComponent(proyectM, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(diseño, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(estados, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(reportes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(carga, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ventas, javax.swing.GroupLayout.DEFAULT_SIZE, 87, Short.MAX_VALUE)
                    .addComponent(checador, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(proyectos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(proyectM, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(204, 204, 204)));

        cortes.setForeground(new java.awt.Color(51, 51, 51));
        cortes.setText("Cortes");
        cortes.setColorCheck(new java.awt.Color(102, 102, 102));
        cortes.setColorUnCheck(new java.awt.Color(51, 51, 51));
        cortes.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N

        fresadora.setForeground(new java.awt.Color(51, 51, 51));
        fresadora.setText("Fresadora");
        fresadora.setColorCheck(new java.awt.Color(102, 102, 102));
        fresadora.setColorUnCheck(new java.awt.Color(51, 51, 51));
        fresadora.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N

        cnc.setForeground(new java.awt.Color(51, 51, 51));
        cnc.setText("Cnc");
        cnc.setColorCheck(new java.awt.Color(102, 102, 102));
        cnc.setColorUnCheck(new java.awt.Color(51, 51, 51));
        cnc.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N

        torno.setForeground(new java.awt.Color(51, 51, 51));
        torno.setText("Torno");
        torno.setColorCheck(new java.awt.Color(102, 102, 102));
        torno.setColorUnCheck(new java.awt.Color(51, 51, 51));
        torno.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N

        acabados.setForeground(new java.awt.Color(51, 51, 51));
        acabados.setText("Acabados");
        acabados.setColorCheck(new java.awt.Color(102, 102, 102));
        acabados.setColorUnCheck(new java.awt.Color(51, 51, 51));
        acabados.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N

        calidad.setForeground(new java.awt.Color(51, 51, 51));
        calidad.setText("Calidad");
        calidad.setColorCheck(new java.awt.Color(102, 102, 102));
        calidad.setColorUnCheck(new java.awt.Color(51, 51, 51));
        calidad.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N

        tratamiento.setForeground(new java.awt.Color(51, 51, 51));
        tratamiento.setText("Tratamiento");
        tratamiento.setColorCheck(new java.awt.Color(102, 102, 102));
        tratamiento.setColorUnCheck(new java.awt.Color(51, 51, 51));
        tratamiento.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N

        electrico.setForeground(new java.awt.Color(51, 51, 51));
        electrico.setText("Electrico");
        electrico.setColorCheck(new java.awt.Color(102, 102, 102));
        electrico.setColorUnCheck(new java.awt.Color(51, 51, 51));
        electrico.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(cortes, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addComponent(fresadora, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addComponent(cnc, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addComponent(torno, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(acabados, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addComponent(calidad, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addComponent(tratamiento, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addComponent(electrico, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cortes, javax.swing.GroupLayout.DEFAULT_SIZE, 90, Short.MAX_VALUE)
                    .addComponent(fresadora, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cnc, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(torno, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(acabados, javax.swing.GroupLayout.DEFAULT_SIZE, 89, Short.MAX_VALUE)
                    .addComponent(calidad, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(tratamiento, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(electrico, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(204, 204, 204)));

        crear.setForeground(new java.awt.Color(51, 51, 51));
        crear.setText("Crear empleado");
        crear.setColorCheck(new java.awt.Color(102, 102, 102));
        crear.setColorUnCheck(new java.awt.Color(51, 51, 51));
        crear.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N

        ensambles.setForeground(new java.awt.Color(51, 51, 51));
        ensambles.setText("Ensambles");
        ensambles.setColorCheck(new java.awt.Color(102, 102, 102));
        ensambles.setColorUnCheck(new java.awt.Color(51, 51, 51));
        ensambles.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N

        verEmpleado.setForeground(new java.awt.Color(51, 51, 51));
        verEmpleado.setText("Ver empleados");
        verEmpleado.setColorCheck(new java.awt.Color(102, 102, 102));
        verEmpleado.setColorUnCheck(new java.awt.Color(51, 51, 51));
        verEmpleado.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N

        inventario.setForeground(new java.awt.Color(51, 51, 51));
        inventario.setText("Inventario");
        inventario.setColorCheck(new java.awt.Color(102, 102, 102));
        inventario.setColorUnCheck(new java.awt.Color(51, 51, 51));
        inventario.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N

        invPlanos.setForeground(new java.awt.Color(51, 51, 51));
        invPlanos.setText("Inv. planos");
        invPlanos.setColorCheck(new java.awt.Color(102, 102, 102));
        invPlanos.setColorUnCheck(new java.awt.Color(51, 51, 51));
        invPlanos.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N

        remisiones.setForeground(new java.awt.Color(51, 51, 51));
        remisiones.setText("Remisiones");
        remisiones.setColorCheck(new java.awt.Color(102, 102, 102));
        remisiones.setColorUnCheck(new java.awt.Color(51, 51, 51));
        remisiones.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N

        entrega.setForeground(new java.awt.Color(51, 51, 51));
        entrega.setText("Entrega");
        entrega.setColorCheck(new java.awt.Color(102, 102, 102));
        entrega.setColorUnCheck(new java.awt.Color(51, 51, 51));
        entrega.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N

        pedidos.setForeground(new java.awt.Color(51, 51, 51));
        pedidos.setText("Pedidos");
        pedidos.setColorCheck(new java.awt.Color(102, 102, 102));
        pedidos.setColorUnCheck(new java.awt.Color(51, 51, 51));
        pedidos.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(crear, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addComponent(ensambles, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addComponent(verEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addComponent(inventario, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(invPlanos, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addComponent(remisiones, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addComponent(entrega, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addComponent(pedidos, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(crear, javax.swing.GroupLayout.DEFAULT_SIZE, 90, Short.MAX_VALUE)
                    .addComponent(ensambles, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(verEmpleado, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(inventario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(invPlanos, javax.swing.GroupLayout.DEFAULT_SIZE, 89, Short.MAX_VALUE)
                    .addComponent(remisiones, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(entrega, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pedidos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(204, 204, 204)));
        java.awt.GridBagLayout jPanel5Layout = new java.awt.GridBagLayout();
        jPanel5Layout.columnWeights = new double[] {1.0, 1.0, 1.0, 1.0};
        jPanel5Layout.rowWeights = new double[] {1.0, 1.0};
        jPanel5.setLayout(jPanel5Layout);

        cotizar.setForeground(new java.awt.Color(51, 51, 51));
        cotizar.setText("Cotizar");
        cotizar.setColorCheck(new java.awt.Color(102, 102, 102));
        cotizar.setColorUnCheck(new java.awt.Color(51, 51, 51));
        cotizar.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanel5.add(cotizar, gridBagConstraints);

        verRequisicion.setForeground(new java.awt.Color(51, 51, 51));
        verRequisicion.setText("Ver requisiciones");
        verRequisicion.setColorCheck(new java.awt.Color(102, 102, 102));
        verRequisicion.setColorUnCheck(new java.awt.Color(51, 51, 51));
        verRequisicion.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanel5.add(verRequisicion, gridBagConstraints);

        requisiciones.setForeground(new java.awt.Color(51, 51, 51));
        requisiciones.setText("Requisiciones");
        requisiciones.setColorCheck(new java.awt.Color(102, 102, 102));
        requisiciones.setColorUnCheck(new java.awt.Color(51, 51, 51));
        requisiciones.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanel5.add(requisiciones, gridBagConstraints);

        compras.setForeground(new java.awt.Color(51, 51, 51));
        compras.setText("Compras");
        compras.setColorCheck(new java.awt.Color(102, 102, 102));
        compras.setColorUnCheck(new java.awt.Color(51, 51, 51));
        compras.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanel5.add(compras, gridBagConstraints);

        aprobacion.setForeground(new java.awt.Color(51, 51, 51));
        aprobacion.setText("Aprobaciones");
        aprobacion.setColorCheck(new java.awt.Color(102, 102, 102));
        aprobacion.setColorUnCheck(new java.awt.Color(51, 51, 51));
        aprobacion.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanel5.add(aprobacion, gridBagConstraints);

        recibos.setForeground(new java.awt.Color(51, 51, 51));
        recibos.setText("Recibos");
        recibos.setColorCheck(new java.awt.Color(102, 102, 102));
        recibos.setColorUnCheck(new java.awt.Color(51, 51, 51));
        recibos.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanel5.add(recibos, gridBagConstraints);

        prestamos.setForeground(new java.awt.Color(51, 51, 51));
        prestamos.setText("Prestamos");
        prestamos.setColorCheck(new java.awt.Color(102, 102, 102));
        prestamos.setColorUnCheck(new java.awt.Color(51, 51, 51));
        prestamos.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanel5.add(prestamos, gridBagConstraints);

        cotizacion.setForeground(new java.awt.Color(51, 51, 51));
        cotizacion.setText("Cotizacion");
        cotizacion.setColorCheck(new java.awt.Color(102, 102, 102));
        cotizacion.setColorUnCheck(new java.awt.Color(51, 51, 51));
        cotizacion.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanel5.add(cotizacion, gridBagConstraints);

        verRequisicion1.setForeground(new java.awt.Color(51, 51, 51));
        verRequisicion1.setText("Ver requisiciones");
        verRequisicion1.setColorCheck(new java.awt.Color(102, 102, 102));
        verRequisicion1.setColorUnCheck(new java.awt.Color(51, 51, 51));
        verRequisicion1.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanel5.add(verRequisicion1, gridBagConstraints);

        rh.setForeground(new java.awt.Color(51, 51, 51));
        rh.setText("RH");
        rh.setColorCheck(new java.awt.Color(102, 102, 102));
        rh.setColorUnCheck(new java.awt.Color(51, 51, 51));
        rh.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanel5.add(rh, gridBagConstraints);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, 618, Short.MAX_VALUE)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, 197, Short.MAX_VALUE))
        );

        jPanel9.add(jPanel1);
        jPanel1.getAccessibleContext().setAccessibleName("ACCESOS \nEntrada\n");

        jPanel7.add(jPanel9, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel7, java.awt.BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed

        if(txtCodigo.getText().equals("")){
            guardar();
        }else{
            actualizar();
        }
        
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void txtCodigoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoKeyTyped
        char c = evt.getKeyChar();
        if(Character.isLowerCase(c))
        {
            String cad = (""+c).toUpperCase();
            c = cad.charAt(0);
            evt.setKeyChar(c);
        }
    }//GEN-LAST:event_txtCodigoKeyTyped

    private void cbxActivoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxActivoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbxActivoActionPerformed

    private void cmbDepartamentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbDepartamentoActionPerformed

    }//GEN-LAST:event_cmbDepartamentoActionPerformed

    private void cmbPuestoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbPuestoActionPerformed
        if(cmbPuesto.getSelectedItem().equals("ALMACEN")){
            diseño.setSelected(false);
            estados.setSelected(false);
            reportes.setSelected(false);
            carga.setSelected(false);
            ventas.setSelected(false);
            checador.setSelected(false);
            proyectM.setSelected(false);
            proyectos.setSelected(false);
            cortes.setSelected(false);
            fresadora.setSelected(false);
            cnc.setSelected(false);
            torno.setSelected(false);
            acabados.setSelected(false);
            calidad.setSelected(false);
            tratamiento.setSelected(false);
            electrico.setSelected(false);
            crear.setSelected(false);
            ensambles.setSelected(false);
            verEmpleado.setSelected(false);
            inventario.setSelected(true);
            invPlanos.setSelected(true);
            remisiones.setSelected(false);
            entrega.setSelected(true);
            pedidos.setSelected(true);
            verRequisicion.setSelected(true);
            requisiciones.setSelected(true);
            compras.setSelected(false);
            aprobacion.setSelected(false);
            recibos.setSelected(true);
            prestamos.setSelected(false);
            cotizacion.setSelected(false);
        }else if(cmbPuesto.getSelectedItem().equals("PUESTO")){
            diseño.setSelected(false);
            estados.setSelected(false);
            reportes.setSelected(false);
            carga.setSelected(false);
            ventas.setSelected(false);
            checador.setSelected(false);
            proyectM.setSelected(false);
            proyectos.setSelected(false);
            cortes.setSelected(false);
            fresadora.setSelected(false);
            cnc.setSelected(false);
            torno.setSelected(false);
            acabados.setSelected(false);
            calidad.setSelected(false);
            tratamiento.setSelected(false);
            electrico.setSelected(false);
            crear.setSelected(false);
            ensambles.setSelected(false);
            verEmpleado.setSelected(false);
            inventario.setSelected(false);
            invPlanos.setSelected(false);
            remisiones.setSelected(false);
            entrega.setSelected(false);
            pedidos.setSelected(false);
            cotizar.setSelected(false);
            verRequisicion.setSelected(false);
            requisiciones.setSelected(false);
            compras.setSelected(false);
            aprobacion.setSelected(false);
            recibos.setSelected(false);
            prestamos.setSelected(false);
            cotizacion.setSelected(false);
        }else if(cmbPuesto.getSelectedItem().equals("CALIDAD")){
            diseño.setSelected(false);
            estados.setSelected(true);
            reportes.setSelected(true);
            carga.setSelected(true);
            ventas.setSelected(false);
            checador.setSelected(false);
            proyectM.setSelected(false);
            proyectos.setSelected(false);
            cortes.setSelected(false);
            fresadora.setSelected(false);
            cnc.setSelected(false);
            torno.setSelected(false);
            acabados.setSelected(false);
            calidad.setSelected(true);
            tratamiento.setSelected(false);
            electrico.setSelected(false);
            crear.setSelected(false);
            verEmpleado.setSelected(false);
            inventario.setSelected(false);
            invPlanos.setSelected(true);
            remisiones.setSelected(false);
            ensambles.setSelected(true);
            entrega.setSelected(true);
            pedidos.setSelected(false);
            cotizar.setSelected(false);
            verRequisicion.setSelected(false);
            requisiciones.setSelected(false);
            compras.setSelected(false);
            aprobacion.setSelected(false);
            recibos.setSelected(false);
            prestamos.setSelected(false);
            cotizacion.setSelected(false);
        }else if(cmbPuesto.getSelectedItem().equals("COMPRAS")){
            diseño.setSelected(false);
            estados.setSelected(true);
            reportes.setSelected(true);
            carga.setSelected(true);
            ventas.setSelected(false);
            checador.setSelected(false);
            proyectM.setSelected(false);
            proyectos.setSelected(false);
            cortes.setSelected(false);
            fresadora.setSelected(false);
            cnc.setSelected(false);
            torno.setSelected(false);
            acabados.setSelected(false);
            calidad.setSelected(false);
            tratamiento.setSelected(false);
            electrico.setSelected(false);
            crear.setSelected(false);
            ensambles.setSelected(false);
            verEmpleado.setSelected(false);
            inventario.setSelected(true);
            invPlanos.setSelected(true);
            remisiones.setSelected(false);
            entrega.setSelected(true);
            pedidos.setSelected(false);
            cotizar.setSelected(true);
            verRequisicion.setSelected(true);
            requisiciones.setSelected(true);
            compras.setSelected(true);
            aprobacion.setSelected(true);
            recibos.setSelected(true);
            prestamos.setSelected(false);
            cotizacion.setSelected(false);

        }else if(cmbPuesto.getSelectedItem().equals("DISEÑO")){
            diseño.setSelected(true);
            estados.setSelected(true);
            reportes.setSelected(true);
            carga.setSelected(true);
            ventas.setSelected(false);
            checador.setSelected(false);
            proyectM.setSelected(false);
            proyectos.setSelected(false);
            cortes.setSelected(false);
            fresadora.setSelected(false);
            cnc.setSelected(false);
            torno.setSelected(false);
            acabados.setSelected(false);
            calidad.setSelected(false);
            tratamiento.setSelected(false);
            electrico.setSelected(false);
            crear.setSelected(false);
            ensambles.setSelected(false);
            verEmpleado.setSelected(false);
            inventario.setSelected(false);
            invPlanos.setSelected(false);
            remisiones.setSelected(false);
            entrega.setSelected(false);
            pedidos.setSelected(false);
            cotizar.setSelected(false);
            requisiciones.setSelected(true);
            verRequisicion.setSelected(true);
            compras.setSelected(false);
            aprobacion.setSelected(false);
            recibos.setSelected(false);
            prestamos.setSelected(false);
            cotizacion.setSelected(false);
        }else if(cmbPuesto.getSelectedItem().equals("INTEGRACION")){
            diseño.setSelected(false);
            estados.setSelected(true);
            reportes.setSelected(true);
            carga.setSelected(true);
            ventas.setSelected(false);
            checador.setSelected(false);
            proyectM.setSelected(false);
            proyectos.setSelected(false);
            cortes.setSelected(false);
            fresadora.setSelected(false);
            cnc.setSelected(false);
            torno.setSelected(false);
            acabados.setSelected(false);
            calidad.setSelected(false);
            tratamiento.setSelected(false);
            electrico.setSelected(false);
            crear.setSelected(false);
            ensambles.setSelected(false);
            verEmpleado.setSelected(false);
            inventario.setSelected(false);
            invPlanos.setSelected(false);
            remisiones.setSelected(false);
            entrega.setSelected(false);
            pedidos.setSelected(false);
            cotizar.setSelected(false);
            verRequisicion.setSelected(true);
            requisiciones.setSelected(false);
            compras.setSelected(false);
            aprobacion.setSelected(false);
            recibos.setSelected(false);
            prestamos.setSelected(false);
            cotizacion.setSelected(false);

        }else if(cmbPuesto.getSelectedItem().equals("LIMPIEZA")){
            diseño.setSelected(false);
            estados.setSelected(false);
            reportes.setSelected(false);
            carga.setSelected(false);
            ventas.setSelected(false);
            checador.setSelected(false);
            proyectM.setSelected(false);
            proyectos.setSelected(false);
            cortes.setSelected(false);
            fresadora.setSelected(false);
            cnc.setSelected(false);
            torno.setSelected(false);
            acabados.setSelected(false);
            calidad.setSelected(false);
            tratamiento.setSelected(false);
            electrico.setSelected(false);
            crear.setSelected(false);
            ensambles.setSelected(false);
            verEmpleado.setSelected(false);
            inventario.setSelected(false);
            invPlanos.setSelected(false);
            remisiones.setSelected(false);
            entrega.setSelected(false);
            pedidos.setSelected(false);
            cotizar.setSelected(false);
            verRequisicion.setSelected(false);
            requisiciones.setSelected(false);
            compras.setSelected(false);
            aprobacion.setSelected(false);
            recibos.setSelected(false);
            prestamos.setSelected(false);
            cotizacion.setSelected(false);
        }else if(cmbPuesto.getSelectedItem().equals("HERRAMENTISTA")){
            diseño.setSelected(false);
            estados.setSelected(false);
            reportes.setSelected(false);
            carga.setSelected(false);
            ventas.setSelected(false);
            checador.setSelected(false);
            proyectM.setSelected(false);
            proyectos.setSelected(false);
            cortes.setSelected(true);
            fresadora.setSelected(true);
            cnc.setSelected(true);
            torno.setSelected(true);
            acabados.setSelected(true);
            calidad.setSelected(true);
            tratamiento.setSelected(true);
            electrico.setSelected(true);
            crear.setSelected(false);
            ensambles.setSelected(false);
            verEmpleado.setSelected(false);
            inventario.setSelected(false);
            invPlanos.setSelected(false);
            remisiones.setSelected(false);
            entrega.setSelected(false);
            pedidos.setSelected(false);
            cotizar.setSelected(false);
            verRequisicion.setSelected(false);
            requisiciones.setSelected(false);
            compras.setSelected(false);
            aprobacion.setSelected(false);
            recibos.setSelected(false);
            prestamos.setSelected(false);
            cotizacion.setSelected(false);

        }else if(cmbPuesto.getSelectedItem().equals("VENTAS")){
            diseño.setSelected(false);
            estados.setSelected(true);
            reportes.setSelected(true);
            carga.setSelected(true);
            ventas.setSelected(true);
            checador.setSelected(false);
            proyectos.setSelected(true);
            proyectM.setSelected(true);
            cortes.setSelected(false);
            fresadora.setSelected(false);
            cnc.setSelected(false);
            torno.setSelected(false);
            acabados.setSelected(false);
            calidad.setSelected(false);
            tratamiento.setSelected(false);
            electrico.setSelected(false);
            crear.setSelected(false);
            ensambles.setSelected(false);
            verEmpleado.setSelected(false);
            inventario.setSelected(false);
            invPlanos.setSelected(false);
            remisiones.setSelected(false);
            entrega.setSelected(true);
            pedidos.setSelected(false);
            cotizar.setSelected(false);
            verRequisicion.setSelected(true);
            requisiciones.setSelected(true);
            compras.setSelected(false);
            aprobacion.setSelected(false);
            recibos.setSelected(false);
            prestamos.setSelected(false);
            cotizacion.setSelected(false);

        }else if(cmbPuesto.getSelectedItem().equals("COSTOS")){
            diseño.setSelected(false);
            estados.setSelected(false);
            reportes.setSelected(false);
            carga.setSelected(false);
            ventas.setSelected(false);
            checador.setSelected(false);
            proyectM.setSelected(false);
            proyectos.setSelected(false);
            cortes.setSelected(false);
            fresadora.setSelected(false);
            cnc.setSelected(false);
            torno.setSelected(false);
            acabados.setSelected(false);
            calidad.setSelected(false);
            tratamiento.setSelected(false);
            electrico.setSelected(false);
            crear.setSelected(false);
            ensambles.setSelected(false);
            verEmpleado.setSelected(false);
            inventario.setSelected(false);
            invPlanos.setSelected(false);
            remisiones.setSelected(false);
            entrega.setSelected(false);
            pedidos.setSelected(false);
            verRequisicion.setSelected(true);
            requisiciones.setSelected(true);
            compras.setSelected(false);
            aprobacion.setSelected(false);
            recibos.setSelected(false);
            prestamos.setSelected(false);
            cotizacion.setSelected(true);

        }else if(cmbPuesto.getSelectedItem().equals("IT")){
            diseño.setSelected(true);
            estados.setSelected(true);
            reportes.setSelected(true);
            carga.setSelected(true);
            ventas.setSelected(true);
            checador.setSelected(true);
            proyectM.setSelected(true);
            proyectos.setSelected(true);
            cortes.setSelected(true);
            fresadora.setSelected(true);
            cnc.setSelected(true);
            torno.setSelected(true);
            acabados.setSelected(true);
            calidad.setSelected(true);
            tratamiento.setSelected(true);
            electrico.setSelected(true);
            crear.setSelected(true);
            ensambles.setSelected(true);
            verEmpleado.setSelected(true);
            inventario.setSelected(true);
            invPlanos.setSelected(true);
            remisiones.setSelected(true);
            entrega.setSelected(true);
            pedidos.setSelected(true);
            cotizar.setSelected(true);
            verRequisicion.setSelected(true);
            requisiciones.setSelected(true);
            compras.setSelected(true);
            aprobacion.setSelected(true);
            recibos.setSelected(true);
            prestamos.setSelected(true);
            cotizacion.setSelected(true);
        }else if(cmbPuesto.getSelectedItem().equals("SUPER")){
            diseño.setSelected(true);
            estados.setSelected(true);
            reportes.setSelected(true);
            carga.setSelected(true);
            ventas.setSelected(true);
            checador.setSelected(true);
            proyectM.setSelected(true);
            proyectos.setSelected(true);
            cortes.setSelected(true);
            fresadora.setSelected(true);
            cnc.setSelected(true);
            torno.setSelected(true);
            acabados.setSelected(true);
            calidad.setSelected(true);
            tratamiento.setSelected(true);
            electrico.setSelected(true);
            crear.setSelected(true);
            ensambles.setSelected(true);
            verEmpleado.setSelected(true);
            inventario.setSelected(true);
            invPlanos.setSelected(true);
            remisiones.setSelected(true);
            entrega.setSelected(true);
            pedidos.setSelected(true);
            cotizar.setSelected(true);
            verRequisicion.setSelected(true);
            requisiciones.setSelected(true);
            compras.setSelected(true);
            aprobacion.setSelected(true);
            recibos.setSelected(true);
            prestamos.setSelected(true);
            cotizacion.setSelected(true);
        }
    }//GEN-LAST:event_cmbPuestoActionPerformed

    private void txtNumeroKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNumeroKeyTyped
        char c = evt.getKeyChar();
        if(Character.isLowerCase(c))
        {
            String cad = (""+c).toUpperCase();
            c = cad.charAt(0);
            evt.setKeyChar(c);
        }
    }//GEN-LAST:event_txtNumeroKeyTyped

    private void txtNumeroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNumeroActionPerformed
        try{
            Connection con;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            Statement st1=con.createStatement();
            String sql = "select * from registroempleados where NumEmpleado like '"+txtNumero.getText()+"'";
            String sqlCheck = "SELECT * FROM empleadoscheck where NumEmpleado like '"+txtNumero.getText()+"'";
            ResultSet rs = st.executeQuery(sql);
            ResultSet rsc=st1.executeQuery(sqlCheck);
            String datos[] = new String[33];
            while(rsc.next()){
                cmbDepartamento.setSelectedItem(rsc.getString("Departamento"));
                cbxAdmin.setSelected(rsc.getInt("Administrador")==1);
                cbxActivo.setSelected(Boolean.parseBoolean(rsc.getString("Activo")));  
                String Entrada=rsc.getString("Entrada");
                String Salida=rsc.getString("Salida");
                String EntradaSab=rsc.getString("EntradaSabado");
                String SalidaSab=rsc.getString("SalidaSabado");
                
                // Declarar las variables fuera de los bloques
                String hora = "", minuto = "";
                String horaSa = "", minutoSa = "";
                String horaSab = "", minutoSab = "";
                String horaSabS = "", minutoSabS = "";
                
                if(Entrada != null && Entrada.contains(":")){
                    String[] partes = Entrada.split(":");
                    hora=partes[0];
                    minuto=partes[1];
                }
                if(Salida != null && Salida.contains(":")){
                    String[] partes = Salida.split(":");
                    horaSa=partes[0];
                    minutoSa=partes[1];
                }
                if(EntradaSab != null && EntradaSab.contains(":")){
                    String[] partes = EntradaSab.split(":");
                    horaSab=partes[0];
                    minutoSab=partes[1];
                }
                if(SalidaSab != null && SalidaSab.contains(":")){
                    String[] partes = SalidaSab.split(":");
                    horaSabS=partes[0];
                    minutoSabS=partes[1];
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
            
            while(rs.next()){
                txtNombre.setText(rs.getString("Nombre"));
                txtApellido.setText(rs.getString("Apellido"));
                txtDireccion.setText(rs.getString("Direccion"));
                txtCodigo.setText(rs.getString("NumEmpleado"));
                cmbPuesto.setSelectedItem(rs.getString("Puesto"));
                datos[1] = rs.getString("Diseño");
                datos[2] = rs.getString("Cambio");
                datos[3] = rs.getString("Reportes");
                datos[4] = rs.getString("Carga");
                datos[5] = rs.getString("Ventas");
                datos[6] = rs.getString("Cortes");
                datos[7] = rs.getString("Fresa");
                datos[8] = rs.getString("Cnc");
                datos[9] = rs.getString("Torno");
                datos[10] = rs.getString("Acabados");
                datos[12] = rs.getString("Calidad");
                datos[13] = rs.getString("Tratamiento");
                datos[14] = rs.getString("Electrico");
                datos[15] = rs.getString("CrearEmpleado");
                datos[16] = rs.getString("VerEmpleado");
                datos[17] = rs.getString("Inventario");
                datos[18] = rs.getString("Ensamble");
                datos[19] = rs.getString("InventarioPlanos");
                datos[20] = rs.getString("Requisiciones");
                datos[21] = rs.getString("Orden");
                datos[22] = rs.getString("Aprobacion");
                datos[23] = rs.getString("Recibo");
                datos[24] = rs.getString("Prestamo");
                datos[25] = rs.getString("Cotizacion");
                datos[26] = rs.getString("VerRequisiciones");
                datos[27] = rs.getString("Cotizar");
                datos[28] = rs.getString("ProyectMan");
                datos[29] = rs.getString("Remisiones");
                datos[30] = rs.getString("Checador");
                datos[31] = rs.getString("Entrega");
                datos[32] = rs.getString("Pedidos");
                datos[33] = rs.getString("rh");
            }
            if(datos[1].equals("1")){
                diseño.setSelected(true);
            }else{
                diseño.setSelected(false);
            }

            if(datos[2].equals("1")){
                estados.setSelected(true);
            }else{
                estados.setSelected(false);
            }

            if(datos[3].equals("1")){
                reportes.setSelected(true);
            }else{
                reportes.setSelected(false);
            }

            if(datos[4].equals("1")){
                carga.setSelected(true);
            }else{
                carga.setSelected(false);
            }

            if(datos[5].equals("1")){
                ventas.setSelected(true);
            }else{
                ventas.setSelected(false);
            }

            if(datos[6].equals("1")){
                cortes.setSelected(true);
            }else{
                cortes.setSelected(false);
            }

            if(datos[7].equals("1")){
                fresadora.setSelected(true);
            }else{
                fresadora.setSelected(false);
            }

            if(datos[8].equals("1")){
                fresadora.setSelected(true);
            }else{
                fresadora.setSelected(false);
            }
            if(datos[9].equals("1")){
                torno.setSelected(true);
            }else{
                torno.setSelected(false);
            }

            if(datos[10].equals("1")){
                acabados.setSelected(true);
            }else{
                acabados.setSelected(false);

            }if(datos[12].equals("1")){
                calidad.setSelected(true);
            }else{
                calidad.setSelected(false);
            }

            if(datos[13].equals("1")){
                tratamiento.setSelected(true);
            }else{
                tratamiento.setSelected(false);
            }

            if(datos[14].equals("1")){
                electrico.setSelected(true);
            }else{
                electrico.setSelected(false);
            }

            if(datos[15].equals("1")){
                crear.setSelected(true);
            }else{
                crear.setSelected(false);
            }

            if(datos[16].equals("1")){
                verEmpleado.setSelected(true);
            }else{
                verEmpleado.setSelected(false);
            }

            if(datos[17].equals("1")){
                inventario.setSelected(true);
            }else{
                inventario.setSelected(false);
            }

            if(datos[18].equals("1")){
                ensambles.setSelected(true);
            }else{
                ensambles.setSelected(false);
            }

            if(datos[19].equals("1")){
                invPlanos.setSelected(true);
            }else{
                invPlanos.setSelected(false);
            }

            if(datos[20].equals("1")){
                requisiciones.setSelected(true);
            }else{
                requisiciones.setSelected(false);
            }

            if(datos[21].equals("1")){
                compras.setSelected(true);
            }else{
                compras.setSelected(false);
            }

            if(datos[22].equals("1")){
                aprobacion.setSelected(true);
            }else{
                aprobacion.setSelected(false);
            }

            if(datos[23].equals("1")){
                recibos.setSelected(true);
            }else{
                recibos.setSelected(false);
            }

            if(datos[24].equals("1")){
                prestamos.setSelected(true);
            }else{
                prestamos.setSelected(false);
            }

            if(datos[25].equals("1")){
                cotizacion.setSelected(true);
            }else{
                cotizacion.setSelected(false);
            }

            if(datos[26].equals("1")){
                verRequisicion.setSelected(true);
            }else{
                verRequisicion.setSelected(false);
            }

            if(datos[27].equals("1")){
                cotizar.setSelected(true);
            }else{
                cotizar.setSelected(false);
            }

            if(datos[28].equals("1")){
                proyectM.setSelected(true);
            }else{
                proyectM.setSelected(false);
            }

            if(datos[30].equals("1")){
                checador.setSelected(true);
            }else{
                checador.setSelected(false);
            }

            if(datos[31].equals("1")){
                entrega.setSelected(true);
            }else{
                entrega.setSelected(false);
            }

            if(datos[32].equals("1")){
                pedidos.setSelected(true);
            }else{
                pedidos.setSelected(false);
            }

            if(datos[33].equals("1")){
                rh.setSelected(true);
            }else{
                rh.setSelected(false);
            }

        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "ERROR: "+e,"ERROR",JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_txtNumeroActionPerformed

    private void txtTelefonoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTelefonoKeyTyped
        char c = evt.getKeyChar();
        if(Character.isLowerCase(c))
        {
            String cad = (""+c).toUpperCase();
            c = cad.charAt(0);
            evt.setKeyChar(c);
        }
    }//GEN-LAST:event_txtTelefonoKeyTyped

    private void txtTelefonoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTelefonoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTelefonoActionPerformed

    private void txtDireccionKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDireccionKeyTyped
        char c = evt.getKeyChar();
        if(Character.isLowerCase(c))
        {
            String cad = (""+c).toUpperCase();
            c = cad.charAt(0);
            evt.setKeyChar(c);
        }
    }//GEN-LAST:event_txtDireccionKeyTyped

    private void txtApellidoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtApellidoKeyTyped
        char c = evt.getKeyChar();
        if(Character.isLowerCase(c))
        {
            String cad = (""+c).toUpperCase();
            c = cad.charAt(0);
            evt.setKeyChar(c);
        }
    }//GEN-LAST:event_txtApellidoKeyTyped

    private void txtNombreKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreKeyTyped
        char c = evt.getKeyChar();
        if(Character.isLowerCase(c))
        {
            String cad = (""+c).toUpperCase();
            c = cad.charAt(0);
            evt.setKeyChar(c);
        }
    }//GEN-LAST:event_txtNombreKeyTyped

    private void txtNombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNombreActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombreActionPerformed

    private void btImagenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btImagenActionPerformed
          JFileChooser imagen = new JFileChooser();
        FileNameExtensionFilter filtro = new FileNameExtensionFilter(
            "Archivos de Imágen (jpg, png, jpeg)", "jpg", "jpeg", "png");
        imagen.setFileFilter(filtro);

        int resultado = imagen.showOpenDialog(this);
        if (resultado == JFileChooser.APPROVE_OPTION) {
            imagenSeleccionada = imagen.getSelectedFile(); // guarda la imagen original

            // redimensiona y guarda en variables globales
            imagenBytes = redimensionarImg(imagenSeleccionada, IMG_WIDTH, IMG_HEIGHT);
            if (imagenBytes != null) {
                imagenRedimensionada = new ByteArrayInputStream(imagenBytes);
                JOptionPane.showMessageDialog(this, "La Imágen ha sido Redimensionada");
            } else {
                JOptionPane.showMessageDialog(this, "Error al redimensionar la imagen.");
            }
        }
    }//GEN-LAST:event_btImagenActionPerformed

    private void cbEHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbEHActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbEHActionPerformed
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(RegistrarEmpleado.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(RegistrarEmpleado.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(RegistrarEmpleado.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(RegistrarEmpleado.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        FlatMacDarkLaf.setup(); 
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new RegistrarEmpleado().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private rojerusan.RSCheckBox acabados;
    private rojerusan.RSCheckBox aprobacion;
    private javax.swing.JButton btImagen;
    private javax.swing.JButton btnGuardar;
    private rojerusan.RSCheckBox calidad;
    private rojerusan.RSCheckBox carga;
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
    private rojerusan.RSCheckBox checador;
    private RSMaterialComponent.RSComboBoxMaterial cmbDepartamento;
    private RSMaterialComponent.RSComboBoxMaterial cmbPuesto;
    private rojerusan.RSCheckBox cnc;
    private rojerusan.RSCheckBox compras;
    private rojerusan.RSCheckBox cortes;
    private rojerusan.RSCheckBox cotizacion;
    private rojerusan.RSCheckBox cotizar;
    private rojerusan.RSCheckBox crear;
    private rojerusan.RSCheckBox diseño;
    private rojerusan.RSCheckBox electrico;
    private rojerusan.RSCheckBox ensambles;
    private rojerusan.RSCheckBox entrega;
    private rojerusan.RSCheckBox estados;
    private rojerusan.RSCheckBox fresadora;
    private rojerusan.RSCheckBox invPlanos;
    private rojerusan.RSCheckBox inventario;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private rojerusan.RSCheckBox pedidos;
    private rojerusan.RSCheckBox prestamos;
    private rojerusan.RSCheckBox proyectM;
    private rojerusan.RSCheckBox proyectos;
    private rojerusan.RSCheckBox recibos;
    private rojerusan.RSCheckBox remisiones;
    private rojerusan.RSCheckBox reportes;
    private rojerusan.RSCheckBox requisiciones;
    private rojerusan.RSCheckBox rh;
    private rojerusan.RSCheckBox torno;
    private rojerusan.RSCheckBox tratamiento;
    private RSMaterialComponent.RSTextFieldMaterial txtApellido;
    private javax.swing.JTextField txtCodigo;
    private RSMaterialComponent.RSPasswordMaterial txtContra;
    private RSMaterialComponent.RSTextFieldMaterial txtDireccion;
    private RSMaterialComponent.RSTextFieldMaterial txtNombre;
    private RSMaterialComponent.RSTextFieldMaterial txtNumero;
    private RSMaterialComponent.RSPasswordMaterial txtRepetir;
    private RSMaterialComponent.RSTextFieldMaterial txtTelefono;
    private rojerusan.RSCheckBox ventas;
    private rojerusan.RSCheckBox verEmpleado;
    private rojerusan.RSCheckBox verRequisicion;
    private rojerusan.RSCheckBox verRequisicion1;
    // End of variables declaration//GEN-END:variables
}
