package pruebas;

import Conexiones.Conexion;
import Controlador.FileTransferHandler;
import Controlador.maquinados.revisarPlanos;
import VO.ArchivosVO;
import VentanaEmergente.Diseño.DatosHoras;
import VentanaEmergente.Diseño.InicioDiseño;
import VentanaEmergente.Diseño.codigoBarras;
import VentanaEmergente.Diseño.subirBOM;
import VentanaEmergente.Reportes.dis;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.mxrck.autocompleter.TextAutoCompleter;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Label;
import java.awt.Rectangle;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetAdapter;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.beans.PropertyVetoException;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Stack;
import javax.swing.BorderFactory;
import javax.swing.DropMode;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.TransferHandler;
import javax.swing.filechooser.FileNameExtensionFilter;
import mivisorpdf.MiVisorPDF;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.text.PDFTextStripperByArea;
import org.krysalis.barcode4j.impl.code39.Code39Bean;
import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider;
import technology.tabula.ObjectExtractor;
import technology.tabula.Page;
import technology.tabula.RectangularTextContainer;
import technology.tabula.Table;
import technology.tabula.extractors.SpreadsheetExtractionAlgorithm;

public final class Disenio1 extends InternalFrameImagen implements ActionListener, MouseListener{

    int contador = 0;
    codigoBarras barras;
    private ArrayList<ArchivosVO> ListaComponente;
    MiVisorPDF pn = new MiVisorPDF();
    ArchivosVO pl = new ArchivosVO();
    JButton botones[];
    File archivos[];
    private int numImg;
    int seleccionado;
    private boolean ctrlPressed = false;
    DatosHoras datosHoras;
    boolean bandDatosHoras;
    private int paginas = -1;
    private int totalp = -1;
    String empresa;
    private String ruta_archivo = "";
    
    JLabel lblPlanos[];
    Stack<String> pilaPlanos;
    
    TextAutoCompleter au;
    
    Inicio1 inicio;
    
    FileTransferHandler transfer;
    
    int x,y;
    
    public JLabel setLabel(JLabel label, String plano, boolean img, Color color){
        label.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        label.setForeground(color);
        label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        if(img){
            label.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/pdf.png")));
        }
        label.setText("<html> <p style='text-align: center;'>"+plano+"</p>");
        label.setName(plano);
        return label;
    }
    
    public void verificarPlanosNoRegistrados(){
        jPanel15.removeAll();
        try{
            Connection con; 
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            Statement st2 = con.createStatement();
            String sql = "select * from reporteplanos where Estado is null";
            ResultSet rs = st.executeQuery(sql);
            pilaPlanos = new Stack<>();
            int cont = 0;
            while(rs.next()){
                String plano = rs.getString("Plano");
                String sql2 = "select Plano from planos where Plano like '"+plano+"'";
                ResultSet rs2 = st2.executeQuery(sql2);
                String plan = null;
                while(rs2.next()){
                    plan = rs2.getString("Plano");
                }
                if(plan != null){
                    String sql3 = "update reporteplanos set Estado = ? where Plano = ?";
                    PreparedStatement pst = con.prepareStatement(sql3);
                    
                    pst.setString(1, "OK");
                    pst.setString(2, plano);
                    
                    int n = pst.executeUpdate();
                    
                }else{
                    cont++;
                    pilaPlanos.push(plano);
                }
            }
            
            if(cont > 0){
                lblPlanos = new JLabel[cont];
                for (int i = 0; i < pilaPlanos.size(); i++) {
                    lblPlanos[i] = new JLabel();
                    setLabel(lblPlanos[i], pilaPlanos.get(i),true,new java.awt.Color(153, 153, 153));
                    jPanel15.add(setLabel(new JLabel(" "),"4",false,Color.white));
                    jPanel15.add(lblPlanos[i]);
                }
            }else{
                panelPlanos.setVisible(false);
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "ERROR: "+e,"ERROR",JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void verificarPlanoEnFila(String plano){
        for (int i = 0; i < pilaPlanos.size(); i++) {
            if(lblPlanos[i].getName().equals(plano)){
                lblPlanos[i].setForeground(new Color(17, 68, 12));
            }
        }
    }
    
    public String getDirectorio(String proyecto){
        String path = "\\\\192.168.100.40\\03 Project\\04 DISENO\\" + proyecto;
        File direccion = new File(path);
        
        if(!direccion.isDirectory()){
            boolean res = direccion.mkdirs();
            if(res){
                return path;
            }else{
                return path;
            }
        }else{
            return path;
        }
    }
    
    public static BufferedImage generateBarcode(String code) throws IOException {
        Code39Bean barcodeBean = new Code39Bean();
        final int dpi = 150;
        
        // Configuración del generador de código de barras
        barcodeBean.setModuleWidth(0.2); // Reducir el ancho del módulo
        barcodeBean.setWideFactor(3);  // Ajustar el factor de ampliación
        barcodeBean.doQuietZone(false);  // Deshabilitar la zona de silencio
        
        // Deshabilitar el texto debajo del código de barras
//        barcodeBean.setMsgPosition(org.krysalis.barcode4j.HumanReadablePlacement.HRP_NONE);
        
        // Crear el canvas para el código de barras
        BitmapCanvasProvider canvas = new BitmapCanvasProvider(dpi, BufferedImage.TYPE_BYTE_BINARY, false, 0);
        barcodeBean.generateBarcode(canvas, code);
        canvas.finish();
        
        return canvas.getBufferedImage();
    }
    
    public static BufferedImage generateBarcodeSinLetras(String code) throws IOException {
        Code39Bean barcodeBean = new Code39Bean();
        final int dpi = 150;
        
        // Configuración del generador de código de barras
        barcodeBean.setModuleWidth(0.2); // Reducir el ancho del módulo
        barcodeBean.setWideFactor(3);  // Ajustar el factor de ampliación
        barcodeBean.doQuietZone(false);  // Deshabilitar la zona de silencio
        
        // Deshabilitar el texto debajo del código de barras
        barcodeBean.setMsgPosition(org.krysalis.barcode4j.HumanReadablePlacement.HRP_NONE);
        
        // Crear el canvas para el código de barras
        BitmapCanvasProvider canvas = new BitmapCanvasProvider(dpi, BufferedImage.TYPE_BYTE_BINARY, false, 0);
        barcodeBean.generateBarcode(canvas, code);
        canvas.finish();
        
        return canvas.getBufferedImage();
    }
    
    public String convertirPdf(File selectedFile, String path, Dimension dim, int consecutivo){
            try {
                String numeroProyecto = txtProyecto.getText().trim();
                String remplazo;
                remplazo = txtProyecto.getText() + " " + String.format("%03d", consecutivo);
                String nombrePdf = selectedFile.getName().replace(".pdf", "");

                String[] partes = nombrePdf.split(" ");
                String numFinal = partes[partes.length - 1];

                String numeroPlano = numeroProyecto + " " + numFinal;
                String plan = numeroPlano;
                if (empresa.equals("align")) {
                    plan = remplazo;
                    numeroPlano = selectedFile.getName().replace(".pdf", "");
                }
                
                BufferedImage barcodeImage = generateBarcode(plan);
                try (FileOutputStream out = new FileOutputStream("barcode.png")) {
                    javax.imageio.ImageIO.write(barcodeImage, "png", out);
                } catch (IOException e) {
                    System.out.println("error: " + e);
                }
                
                try {
                    File tempFile = File.createTempFile("barcode", ".png");
                    javax.imageio.ImageIO.write(barcodeImage, "png", tempFile);

                    PdfDocument pdfDoc = new PdfDocument(new PdfReader(selectedFile), new PdfWriter("\\" + path + "\\" + numeroPlano + ".pdf"));
                    Document doc = new Document(pdfDoc);

                    ImageData imageData = ImageDataFactory.create(tempFile.getAbsolutePath());
                    com.itextpdf.layout.element.Image im = new com.itextpdf.layout.element.Image(imageData);
                    im.setFixedPosition(dim.width, dim.height);
                    doc.add(im);


                    pdfDoc.close();
                    tempFile.delete();
                    return "\\" + path  + "\\" + numeroPlano + ".pdf";
                } catch (IOException ex) {
                    System.out.println("Error al crear codigo:" + ex);
                }
            } catch (IOException e) {
                System.out.println("error: " + e);
            }
            return null;
    }
    
    public void importarDocs(){
        if(txtProyecto.isEnabled()){
            JOptionPane.showMessageDialog(this, "Debes seleccionar un proyecto","Advertencia",JOptionPane.WARNING_MESSAGE);
            txtProyecto.requestFocusInWindow();
        }else{
            limpiarBotones();
            JFileChooser SelectArchivo = new JFileChooser();
            SelectArchivo.setFileFilter(new FileNameExtensionFilter("PDF (*.pdf)", "pdf"));
            SelectArchivo.setMultiSelectionEnabled(true);
            if (SelectArchivo.showDialog(null, "Seleccionar Archivo") == JFileChooser.APPROVE_OPTION) {
                archivos = SelectArchivo.getSelectedFiles();
                if(empresa.equals("align") || empresa.equals("durol") || empresa.equals("3i")){
                    String direccion = getDirectorio(txtProyecto.getText());
                    File[] archivosNuevos = new File[archivos.length];
                    Dimension dim = new Dimension(10,750);
                    if(empresa.equals("durol")){
                        dim = new Dimension(5,570);
                    }
                    if(empresa.equals("durol")){
                        
                    }
                    int consecutivo = nomenclaturaAlign();
                    for (int i = 0; i < archivos.length; i++) {
                        archivosNuevos[i] = new File(convertirPdf(archivos[i], direccion, dim, consecutivo));
                        consecutivo++;
                    }
                    archivos = archivosNuevos;
                }
                botones = new JButton[archivos.length];
                for (int i = 0; i < archivos.length; i++) {
                    String image = "";
                    String name = "";
                    botones[i] = new JButton();
                    String color = "";
                    if(empresa.equals("align")){
                        image = "/Iconos/align_16.png";
                        name = "align";
                        
                    }else if(empresa.equals("durol")){
                        image = "/Iconos/durol_16.png";
                        name = "durol";
                    }else if(empresa.equals("3i")){
                        //----------------------------------------------------SERVICIOS INDUSTRIALES 3I----------------------------------------------------------------
                        image = "/Iconos/3i_16.png";
                        name = "3i";
                    }else{
                        color = null;
                    }
                    crearBoton(botones[i],archivos[i].getName(),image, name);
                    if(color == null){
                        botones[i].setForeground(new Color(210,210,210));
                    }
                    verificarPlanoEnFila(archivos[i].getName().replace(".pdf", ""));
                    jPanel10.add(botones[i]);
                    revalidate();
                    repaint();
                }
            iniciar();
            }
        }
    }
    
    public void setBorde(){
        jPanel10.setDropTarget(new DropTarget(jPanel10, DnDConstants.ACTION_COPY, new DropTargetAdapter() {
            @Override
            public void dragEnter(DropTargetDragEvent dtde) {
                // Cambiar el borde a punteado cuando se arrastran archivos sobre el panel
                jPanel10.setBorder(BorderFactory.createDashedBorder(Color.BLACK, 2, 2, 2, false));
            }

            @Override
            public void dragExit(DropTargetEvent dte) {
                // Restablecer el borde al salir del panel
                jPanel10.setBorder(null);
            }

            @Override
            public void drop(DropTargetDropEvent dtde) {
                jPanel10.setBorder(null);
                if(txtProyecto.isEnabled()){
                    JOptionPane.showMessageDialog(null, "Debes seleccionar un proyecto","Advertencia",JOptionPane.WARNING_MESSAGE);
                    txtProyecto.requestFocusInWindow();
                }else{
                    limpiarBotones();
                    try {
                        Transferable transferable = dtde.getTransferable();
                        if (transferable.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
                            dtde.acceptDrop(DnDConstants.ACTION_COPY);
                            List<File> files = (List<File>) transferable.getTransferData(DataFlavor.javaFileListFlavor);
                            botones = new JButton[files.size()];
                            archivos = new File[files.size()];
                            if(empresa.equals("align") || empresa.equals("durol") || empresa.equals("3i")){
                                String direccion = getDirectorio(txtProyecto.getText());
                                File[] archivosNuevos = new File[archivos.length];
                                Dimension dim = new Dimension(10,750);
                                if(empresa.equals("durol")){
                                    dim = new Dimension(5,570);
                                }
                                for (int i = 0; i < files.size(); i++) {
                                    archivosNuevos[i] = new File(convertirPdf(files.get(i), direccion,dim,0));
                                }
                                archivos = archivosNuevos;
                            }
                            for (int i = 0; i < archivos.length; i++) {
                                archivos[i] = files.get(i);
                                if (archivos[i].getName().toLowerCase().endsWith(".pdf")) {
                                    // Procesar el archivo PDF aquí
                                    String img = "";
                                    String name = "";
                                    botones[i] = new JButton();
                                    String color = "";
                                    if(empresa.equals("align")){
                                        img = "/Iconos/align_16.png";
                                        name = "align";
                                    }else if(empresa.equals("durol")){
                                        img = "/Iconos/durol_16.png";
                                        name = "durol";
                                    }else if(empresa.equals("3i")){
                                        //----------------------------------------------------SERVICIOS INDUSTRIALES 3I----------------------------------------------------------------
                                        img = "/Iconos/3i_16.png";
                                        name = "3i";
                                    }else{
                                        color = null;
                                    }
                                    crearBoton(botones[i],archivos[i].getName(),img, name);
                                    if(color == null){
                                        botones[i].setForeground(new Color(210,210,210));
                                    }
                                    verificarPlanoEnFila(archivos[i].getName().replace(".pdf", ""));
                                    jPanel10.add(botones[i]);
                                    revalidate();
                                    repaint();
                                }
                            }
                            iniciar();
                            dtde.dropComplete(true);
                        } else {
                            dtde.rejectDrop();
                        }
                    } catch (UnsupportedFlavorException | IOException e) {
                        JOptionPane.showMessageDialog(null, "Error: " + e, "Error", JOptionPane.ERROR_MESSAGE);
                        dtde.rejectDrop();
                    }
                }
                verificarPlanosNoRegistrados();
            }
        }));
    }
    
    public void addPro(){
        try{
            Connection con;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            au = new TextAutoCompleter(txtProyecto);
            String sql = "select Proyecto, Id from Proyectos order by Id desc";
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
                au.addItem(rs.getString("Proyecto"));
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "ERROR: "+e,"ERROR",JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void limpiarBotones(){
        jPanel10.removeAll();
        revalidate();   
        repaint();
        
    }
    
    public String getDepa(String numEmpleado, Connection con) throws SQLException {
        String sql = "select * from registroempleados where NumEmpleado like '" + numEmpleado + "' order by id desc";
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(sql);
        while (rs.next()) {
            return rs.getString("Puesto");
        }
        return null;
    }
    
    public final void cerrarFechaCalendario(String proyecto, Connection con) throws SQLException { // si da el error que guarde
        String depa = "DISEÑO";
        String sql = "update agenda set Estatus = ?, EmpleadoFin = ?, FechaTermino = ? where Proyecto = ? and Departamento = ?";
        PreparedStatement pst = con.prepareStatement(sql);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String fecha = sdf.format(new Date());

        pst.setString(1, "Terminado");
        pst.setString(2, inicio.lblId.getText());
        pst.setString(3, fecha);
        pst.setString(4, txtProyecto.getText());
        pst.setString(5, depa);

        int in = pst.executeUpdate();

        if(in < 1){      
            sql = "insert into agenda (Estatus , EmpleadoFin, FechaTermino, Proyecto, Departamento) values(?,?,?,?,?)";
            pst = con.prepareStatement(sql);
            
            pst.setString(1, "Terminado");
            pst.setString(2, inicio.lblId.getText());
            pst.setString(3, fecha);
            pst.setString(4, txtProyecto.getText());
            pst.setString(5, depa);
            
            in = pst.executeUpdate();

            if (in < 1) {
                JOptionPane.showMessageDialog(this, "Error al generar fecha", "Error", JOptionPane.ERROR_MESSAGE);
            }    
        }
    }
    
    public void subirPlanos(File archivo){
        try {
            Connection con = new Conexion().getConnection();
            Statement st2 = con.createStatement();

            String sql2 = "select Plano,Revision from Planos where Plano like '"+l.getText()+"'";
            ResultSet rs2 = st2.executeQuery(sql2);
            String plan = "";
            String revision = "";
            while(rs2.next()){
                plan = rs2.getString("Plano");
                revision = rs2.getString("Revision");
            }
            boolean band1 = true;
            int reem;
            if(plan != null){
                if(plan.equals("")){
                    band1 = true;
                }else{
                    if(revision == null ? k.getText() == null : revision.equals(k.getText())){
                        reem = JOptionPane.showConfirmDialog(this, "ESTE PLANO YA SE ENCUENTRA EN LA BD, ¿DESEAS REEMPLAZARLO?");
                        if(reem == 0){
                            band1 = false;
                        }
                    }else{
                        band1 = true;
                    }
                }
            }else{
                band1 = true;
            }
            byte[] pe = null;
            if(archivo == null){

            }else{
            pe = new byte[(int) archivo.length()];
            try{

            InputStream input = new FileInputStream(archivo);
            input.read(pe);
            }catch(IOException e){

            }
            }
            if(band1){
            //--------------------------------1        2          3     4           5           6           7         8     9    10      11           12         13        14          15        1 6       17       18        19         20                             
            String sql = "insert into Planos (Plano, Proyecto, Estado, Prioridad, Cantidad, Integracion, Fresadora, Torno, Cnc, Mazak, Rectificado, Soldadura, Ensamble, Revision, Descripcion, Material, Dureza, Maquina, NoEnsamble, Tratamiento,Dimension) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            if (empresa.equals("align")) {
                sql = "insert into Planos (Plano, Proyecto, Estado, Prioridad, Cantidad, Integracion, Fresadora, Torno, "
                        + "Cnc, Mazak, Rectificado, Soldadura, Ensamble, Revision, Descripcion, Material, Dureza, Maquina, "
                        + "NoEnsamble, Tratamiento, Dimension, PiezaReal) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            }
            PreparedStatement pst = con.prepareStatement(sql);

            String sql3 = "insert into pdfplanos (Plano, Pdf) values(?,?)";
            PreparedStatement pst3 = con.prepareStatement(sql3);
            
            pst.setString(1, l.getText());
            pst.setString(2, txtProyecto.getText());
            pst.setString(3, "");
            pst.setString(4, "0");
            pst.setString(5, s.getText());
            pst.setString(6, c.getText());
            pst.setString(7, d.getText());
            pst.setString(8, e.getText());
            pst.setString(9, f.getText());
            pst.setString(10, g.getText());
            pst.setString(11, h.getText());
            pst.setString(12, i1.getText());
            pst.setString(13, j.getText());
            pst.setString(14, k.getText());
            pst.setString(15, m.getText());
            pst.setString(16, n.getText());
            pst.setString(17, o.getText());
            pst.setString(18, p.getText());
            pst.setString(19, q.getText());
            pst.setString(20, r.getText());
            pst.setString(21, "");
            if (empresa.equals("align")) {
                pst.setString(22, archivo.getName().replace(".pdf", ""));
            }

            pst3.setString(1, l.getText());
            pst3.setBytes(2, pe);

            int n1 = pst3.executeUpdate();
            
            if(n1 <= 0){
                JOptionPane.showMessageDialog(this, "NO SE EL PDF","ERROR",JOptionPane.ERROR_MESSAGE);
            }
            
            int n = pst.executeUpdate();
            if(n <= 0){
                JOptionPane.showMessageDialog(this, "NO SE GUARDARON LOS DATOS");
            }else{
                revisarPlanos rev = new revisarPlanos();
                rev.actualizarPlanos(con, l.getText(), "CORTES");
            }

            }else{
                String sql = "update Planos set Plano = ?, Proyecto = ?, Estado = ?, Cantidad = ?, Integracion = ?, Fresadora = ?, Torno = ?, Cnc = ?, "
                        + "Mazak = ?, Rectificado = ?, Soldadura = ?, Ensamble = ?, Revision = ?, Descripcion = ?, Material = ?, Dureza = ?, "
                        + "Maquina = ?, NoEnsamble = ?, Tratamiento = ?,Dimension = ? where Plano = ?";
                PreparedStatement pst = con.prepareStatement(sql);

                String sql3 = "update pdfplanos set Pdf = ? where Plano = ?";
                PreparedStatement pst3 = con.prepareStatement(sql3);
                
                pst.setString(1, l.getText());
                pst.setString(2, txtProyecto.getText());
                pst.setString(3, "CORTES");
                pst.setString(4, s.getText());
                pst.setString(5, c.getText());
                pst.setString(6, d.getText());
                pst.setString(7, e.getText());
                pst.setString(8, f.getText());
                pst.setString(9, g.getText());
                pst.setString(10, h.getText());
                pst.setString(11, i1.getText());
                pst.setString(12, j.getText());
                pst.setString(13, k.getText());
                pst.setString(14, m.getText());
                pst.setString(15, n.getText());
                pst.setString(16, o.getText());
                pst.setString(17, p.getText());
                pst.setString(18, q.getText());
                pst.setString(19, r.getText());
                pst.setString(20, "");
                pst.setString(21, l.getText());

                int n = pst.executeUpdate();

                pst3.setBytes(1, pe);
                pst3.setString(2, l.getText());

                int n1 = pst3.executeUpdate();
                
                if(n1 < 1){
                    JOptionPane.showMessageDialog(this, "EL PDF NO SE SUBIO CORRECTAMENTE","ERROR",JOptionPane.ERROR_MESSAGE);
                } else {
                    revisarPlanos rev = new revisarPlanos();
                    rev.actualizarPlanos(con, l.getText(), "CORTES");
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "ERROR AL GUARDAR DATOS: " + e, "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }   
    
    public void iniciar(){
        int opc = 0;
        int cont = 0;
        for (int i = 0; i < botones.length; i++) {
            if(opc == 0){
                if(!botones[i].getForeground().equals(new Color(210,210,210))){
                    try{
                    botones[i].setForeground(new Color(0,165,255));
                    verPlano(archivos[i]);
                    if(botones[i].getName().equals("align")){
                        subirAlignV2(archivos[i]);
                    }else if(botones[i].getName().equals("durol")){
                        subirDurolV2(archivos[i]);
                    }else if(botones[i].getName().equals("3i")){
                        subir3i(archivos[i]);
                    }
                    }catch(Exception e){
                        JOptionPane.showMessageDialog(this, "ERROR: "+e);
                    }
                    botones[i].setForeground(new Color(0,165,255));
                    
                    bandDatosHoras = true;
                    if(d.getText().equals("") || e.getText().equals("") || f.getText().equals("") || k.getText().equals("")){
                        bandDatosHoras = false;
                    }
                    while(bandDatosHoras == false){
                        JFrame f = (JFrame) JOptionPane.getFrameForComponent(this);
                        datosHoras = new DatosHoras(f,true);
                        datosHoras.d.setText(d.getText());
                        datosHoras.e.setText(e.getText());
                        datosHoras.f.setText(this.f.getText());
                        datosHoras.k.setText(k.getText());
                        datosHoras.btnGuardar.addActionListener(this);
                        datosHoras.setVisible(true);
                    }
                    try{
                        double cant = Double.parseDouble(s.getText());
                        botones[i].setForeground(new Color(210,210,210));
                        subirPlanos(archivos[i]);
                        cont++;
                        c.setText("");
                        d.setText("");
                        this.e.setText("");
                        f.setText("");
                        g.setText("");
                        h.setText("");
                        i1.setText("");
                        j.setText("");
                        k.setText("");
                        l.setText("");
                        m.setText("");
                        n.setText("");
                        o.setText("");
                        p.setText("");
                        q.setText("");
                        r.setText("");
                        s.setText("");
                        t.setText("");
                        }catch(Exception e){
                           opc = 1;
                           JOptionPane.showMessageDialog(this, "Cantidad erronea","Advertencia",JOptionPane.WARNING_MESSAGE);
                    }
                }
            }
        }
        if (cont > 0) {
            try {
                Connection con = new Conexion().getConnection();
                cerrarFechaCalendario(txtProyecto.getText(), con);
            } catch(SQLException e) {
                JOptionPane.showMessageDialog(this, "Error al subir fecha: " + e,"Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    public JButton crearBoton(JButton boton, String texto, String img, String name){
        boton.setBackground(new java.awt.Color(255, 255, 255));
        boton.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        boton.setForeground(new java.awt.Color(0, 0, 0));
        boton.setIcon(new javax.swing.ImageIcon(getClass().getResource(img))); // NOI18N
        boton.setText(texto);
        boton.setBorder(null);
        boton.setName(name);
        boton.setBorderPainted(false);
        boton.setContentAreaFilled(false);
        boton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        boton.setFocusPainted(false);
        boton.setFocusable(false);
        boton.addActionListener(this);
        boton.addMouseListener(this);
        return boton;
    }
    
    public String getEmpresa(File archivo){
        String empresa = "";
            if(archivo != null){
         String FILENAME=archivo.getAbsolutePath();
         int band = 0;
         int band2 = 0;
         int band3 = 0;
        
        try {
            PDDocument pd;
            pd = PDDocument.load(new File(FILENAME));
            int totalPages = pd.getNumberOfPages();
            ObjectExtractor oe = new ObjectExtractor(pd);
            SpreadsheetExtractionAlgorithm sea = new SpreadsheetExtractionAlgorithm();
            Page page = oe.extract(1);
            // extract text from the table after detecting
            List<Table> table = null;
            System.out.println(sea);
            System.out.println(page);
            try{
            table = sea.extract(page);
            }catch(Exception ex){
                JOptionPane.showMessageDialog(this, "ERROR AL VER PDF "+archivo.getName(),"ERROR",JOptionPane.ERROR_MESSAGE);
                Logger.getLogger(Disenio1.class.getName()).log(Level.SEVERE, null, ex);
            }
            if(table != null){
            for(Table tables: table) {
                List<List<RectangularTextContainer>> rows = tables.getRows();

                for(int i=0; i<rows.size(); i++) {

                    List<RectangularTextContainer> cells = rows.get(i);
                    
                    for(int j=0; j<cells.size(); j++) {
                        if(cells.get(j).getText().equals("DESIGN")
                                || cells.get(j).getText().contains("QTY") 
                                || cells.get(j).getText().contains("SCALE") || cells.get(j).getText().contains("WEIGHT:")){
                            band++;
                        }else if(cells.get(j).getText().equals("   ")
                                || cells.get(j).getText().contains("C*") 
                                || cells.get(j).getText().contains("D*") 
                                || cells.get(j).getText().contains("E*") 
                                || cells.get(j).getText().contains("F*") 
                                ){
                            if(cells.get(j).getText().length() < 20){
                            band2++;
                            }
                        }else if(cells.get(j).getText().equals("   ")
                                || cells.get(j).getText().contains("Ensamblaje:") 
                                || cells.get(j).getText().contains("Revision:") 
                                || cells.get(j).getText().contains("No. de Parte:") 
                                || cells.get(j).getText().contains("Rectificado:") 
                                ){
                            band3++;
                        }
//                        System.out.println(cells.get(j).getText());
                    }

                   // System.out.println();
                }
                
            }
            if(band == 4){
               empresa = "ALIGN";
            }else if(band2 == 4){
                empresa = "3I";
            }else if(band3 == 4){
                empresa = "DUROL";
            }else{
                empresa = "NULL";
            }
            }
            pd.close();
            } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "ERROR AL SUBIR PLANO "+archivo.getName());
            Logger.getLogger(Disenio1.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
            return empresa;
     }
    
    public void subirDurol(File archivo){
        
        if(archivo != null){
         String FILENAME=archivo.getAbsolutePath();
         int band = 0;
         int band2 = 0;
         int band3 = 0;
        
        try {
            PDDocument pd;
        
            pd = PDDocument.load(new File(FILENAME));
        

            int totalPages = pd.getNumberOfPages();

            ObjectExtractor oe = new ObjectExtractor(pd);
            SpreadsheetExtractionAlgorithm sea = new SpreadsheetExtractionAlgorithm();
            Page page = oe.extract(1);
            // extract text from the table after detecting
            List<Table> table = null;
            try{
            table = sea.extract(page);
            }catch(Exception exx){
                JOptionPane.showMessageDialog(this, "ERROR AL SELECCIONAR FORMATO DE PLANO "+archivo.getName(),"ERROR",JOptionPane.ERROR_MESSAGE);
             }
            int cont = 0;
            if(table != null){
            for(Table tables: table) {
                List<List<RectangularTextContainer>> rows = tables.getRows();
                for(int i=0; i<rows.size(); i++) {

                    List<RectangularTextContainer> cells = rows.get(i);

                    for(int j=0; j< cells.size(); j++) {
                        String celda = cells.get(j).getText();
                        String charsToRemove = "*";

                        for (char c : charsToRemove.toCharArray()) {
                            celda = celda.replace(String.valueOf(c), "");
                        }
                        if(i == 2 && j == 1){
                           c.setText(celda); 
                        }
                        if(i == 3 && j == 1){
                           d.setText(celda); 
                        }
                        if(i == 4 && j == 1){
                           e.setText(celda); 
                        }
                        if(i == 5 && j == 1){
                           f.setText(celda); 
                        }
                        if(i == 6 && j == 1){
                           g.setText(celda); 
                        }
                        if(i == 7 && j == 1){
                           h.setText(celda); 
                        }
                        if(i == 8 && j == 1){
                           i1.setText(celda); 
                        }
                        if(i == 9 && j == 1){
                           this.j.setText(celda); 
                        }
                        if(i == 10 && j == 1){
                           k.setText(celda); 
                        }
                        if(i == 11 && j == 1){
                           l.setText(celda); 
                        }
                        if(i == 12 && j == 1){
                           m.setText(celda); 
                        }
                        if(i == 13 && j == 1){
                           n.setText(celda); 
                        }
                        if(i == 14 && j == 1){
                           o.setText(celda); 
                        }
                        if(i == 15 && j == 1){
                           p.setText(celda); 
                        }
                        if(i == 16 && j == 1){
                           q.setText(celda); 
                        }
                        if(i == 17 && j == 1){
                           r.setText(celda); 
                        }
                        if(i == 18 && j == 1){
                           s.setText(celda); 
                        }
                        if(i == 19 && j == 1){
                           t.setText(celda); 
                        }
                        
                    }

                   // System.out.println();
                }
                
            }
            }
            pd.close();
            } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "ERROR AL SUBIR PLANO "+archivo.getName());
            Logger.getLogger(Disenio1.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
    }
    
    public void subirDurolV2(File archivo){
        try{
            PDDocument document = PDDocument.load(archivo);
            PDFTextStripperByArea textStripper = new PDFTextStripperByArea();
                for (int i = 0; i < 18; i++) {
                    Rectangle rect = new Rectangle(48, 400 + (i*11), 100, 11);
                    textStripper.addRegion("myRegion", rect);
                    PDPage page = document.getPage(0);
                    textStripper.extractRegions(page);
                    String extractedText = textStripper.getTextForRegion("myRegion");
                    String remplazo = extractedText.replace("*", "").replaceAll("\n", "");
                    switch(i){
                        case 1:
                            c.setText("0");
                            break;
                        case 2:
                            d.setText(remplazo);
                            break;
                        case 3:
                            e.setText(remplazo);
                            break;
                        case 4:
                            f.setText(remplazo);
                            break;
                        case 5:
                            g.setText(remplazo);
                            break;
                        case 6:
                            h.setText(remplazo);
                            break;
                        case 7:
                            i1.setText(remplazo);
                            break;
                        case 8:
                            j.setText(remplazo);
                            break;
                        case 9:
                            k.setText(remplazo);
                            break;
                        case 10:
                            remplazo = archivo.getName().replace(".pdf","");
                            l.setText(remplazo);
                            System.out.println(remplazo);
                            break;
                        case 11:
                            m.setText(remplazo);
                            break;
                        case 12:
                            n.setText(remplazo);
                            break;
                        case 13:
                            o.setText(remplazo);
                            break;
                        case 14:
                            p.setText(remplazo);
                            break;
                        case 15:
                            q.setText(remplazo);
                            break;
                        case 16:
                            r.setText(remplazo);
                            break;
                        case 17:
                            s.setText(remplazo.replaceAll("[^0-9]", ""));
                            break;
                    }
                }
            document.close();
            System.out.println("-----------------------------------------------------");

        } catch (IOException ex) {
            Logger.getLogger(Disenio1.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void subirAlign(File archivo){
        
        if(archivo != null){
         String FILENAME=archivo.getAbsolutePath();
         int band = 0;
         int band2 = 0;
         int band3 = 0;
        
        try {
            PDDocument pd;
        
            pd = PDDocument.load(new File(FILENAME));
        

            int totalPages = pd.getNumberOfPages();

            ObjectExtractor oe = new ObjectExtractor(pd);
            SpreadsheetExtractionAlgorithm sea = new SpreadsheetExtractionAlgorithm();
            Page page = oe.extract(1);
            // extract text from the table after detecting
            List<Table> table = null;
            try{
            table = sea.extract(page);
            }catch(Exception exx){
                JOptionPane.showMessageDialog(this, "ERROR AL SELECCIONAR FORMATO DE PLANO "+archivo.getName(),"ERROR",JOptionPane.ERROR_MESSAGE);
             }
            int cont = 0;
            if(table != null){
            for(Table tables: table) {
                List<List<RectangularTextContainer>> rows = tables.getRows();
                for(int i=0; i<rows.size(); i++) {

                    List<RectangularTextContainer> cells = rows.get(i);

                    for(int j=0; j< cells.size(); j++) {
                        String celda = cells.get(j).getText();
//                        6,1 material
                        //7,1 cantidad
                        //5,1 numero de plano
                        //3,2 descripcion
                        if(i == 6 && j == 1){
                            if(celda.length() > 8){
                            n.setText(celda.substring(8, celda.length()));
                            }
                        }
                        if(i == 7 && j == 0){
                            if(celda.length() > 4){
                                try{
                                    int can = Integer.parseInt(celda.substring(4, celda.length()));
                                    s.setText(celda.substring(4, celda.length()));
                                }catch(Exception e){
                                    
                                }
                            
                            }
                        }
                        if(i == 5 && j == 1){
                            if(celda.length() > 9){
                            l.setText(celda.substring(9, celda.length()));
                            }
                        }
                        if(i == 3 && j == 2){
                            if(celda.length() > 12){
                            m.setText(celda.substring(12, celda.length()));
                            }
                        }
                        
//                        System.out.println(cells.get(j).getText()+" -> i: "+i + ", j: "+j);
                    }

                   // System.out.println();
                }
                
            }
            }
            pd.close();
            } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "ERROR AL SUBIR PLANO "+archivo.getName());
//            Logger.getLogger(Tabla.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
    }
    
    public int nomenclaturaAlign() {
        try {
            Connection con = new Conexion().getConnection();
            Statement st = con.createStatement();
            String sql = "select plano, proyecto, id from planos where Proyecto like '" + txtProyecto.getText() + "' order by id desc limit 1";
            ResultSet rs = st.executeQuery(sql);
            String ultimoPlano = null;
            while (rs.next()) {
                ultimoPlano = rs.getString("plano");
            }
            if (ultimoPlano == null) {
                return 100;
            } else {
                return (Integer.parseInt(ultimoPlano.substring(ultimoPlano.lastIndexOf(" ") + 1)) + 1);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error: " + e, "Error", JOptionPane.ERROR_MESSAGE);
        }
        return -1;
    }
    
    public void subirAlignV2(File archivo){
        try{
            try (PDDocument doc = PDDocument.load(archivo)) {
                //---------------------------CANTIDAD-------------------------
                PDFTextStripperByArea textStripper = new PDFTextStripperByArea();
                Rectangle rect = new Rectangle(598, 742, 48, 21);
                textStripper.addRegion("myRegion", rect);
                PDPage page = doc.getPage(0);
                textStripper.extractRegions(page);
                String extractedText = textStripper.getTextForRegion("myRegion");
                String remplazo = extractedText.replace("*", "");
                s.setText(remplazo);
                
                //--------------------------MATERIAL-----------------------------
                rect = new Rectangle(646, 728, 122, 16);
                textStripper.addRegion("myRegion2", rect);
                PDPage page2 = doc.getPage(0);
                textStripper.extractRegions(page2);
                extractedText = textStripper.getTextForRegion("myRegion2");
                remplazo = extractedText.replace("\n", "").replace("*", "");
                n.setText(remplazo);
                
                //--------------------------DESCRIPCION-----------------------------
                rect = new Rectangle(905, 699, 273, 16);
                textStripper.addRegion("myRegion3", rect);
                PDPage page3 = doc.getPage(0);
                textStripper.extractRegions(page3);
                extractedText = textStripper.getTextForRegion("myRegion3");
                remplazo = extractedText.replace("\n", "").replace("*", "");
                m.setText(remplazo);
                
                //--------------------------PARTE-----------------------------
                remplazo = txtProyecto.getText() + " " + String.format("%03d", nomenclaturaAlign());
                l.setText(remplazo);
                
                //--------------------------TRATAMIENTO-----------------------------
                rect = new Rectangle(646, 750, 122, 13);
                textStripper.addRegion("myRegion5", rect);
                PDPage page5 = doc.getPage(0);
                textStripper.extractRegions(page5);
                extractedText = textStripper.getTextForRegion("myRegion5");
                remplazo = extractedText.replace("\n", "").replace("*", "");
                r.setText(remplazo);
                
                //--------------------------REVISION-----------------------------
                rect = new Rectangle(1151, 725, 26, 20);
                textStripper.addRegion("myRegion6", rect);
                PDPage page6 = doc.getPage(0);
                textStripper.extractRegions(page6);
                extractedText = textStripper.getTextForRegion("myRegion6");
                remplazo = extractedText.replace("\n", "").replace("*", "");
                k.setText(remplazo);
                
                d.setText("0");
                e.setText("0");
                f.setText("0");
                k.setText("0");
                s.setText("1");
            }
        } catch (IOException ex) {
            Logger.getLogger(Disenio1.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void subir3i(File archivo){
        byte[] pe = null;
                if(archivo == null){
                    
                }else{
                pe = new byte[(int) archivo.length()];
                try{
                
                InputStream input = new FileInputStream(archivo);
                input.read(pe);
                }catch(IOException e){
                    
                }
                }
                PDFManager pdfManager = new PDFManager();
                String vector[] = new String[30];
                pdfManager.setFilePath(archivo.getAbsolutePath());
                String text = "";

                int aux1 = 0, aux2 = 0;
                int aux = 0;

                try {
                    text = pdfManager.toText();
                } catch (IOException ex) {
                    Logger.getLogger(Disenio1.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                String buscar = "*";
                boolean band = true;
                char arreglo[] = text.toCharArray();
                for (int j = 0; j < text.length(); j++) {
                    String letra = String.valueOf(arreglo[j]);
                    if (buscar.equalsIgnoreCase(letra)) {
                        aux = aux + 1;
                        if (aux == 1 && band == true) {
                            aux1 = j;
                        } else {
                            if (aux == 1 && band == false) {
                                aux1 = aux2;
                            } else {
                                if (aux == 2) {
                                    aux2 = j;

                                    int cont = 0;

                                    String a = text.substring((aux1 + 1), aux2);
                                    String b = text.substring(aux1 - 1, aux1);
                                    switch (b) {
                                        case "A":
                                            vector[0] = a;
                                            break;
                                        case "B":
                                            vector[1] = a;
                                            break;
                                        case "C":
                                            vector[2] = a;
                                            break;
                                        case "D":
                                            vector[3] = a;
                                            break;
                                        case "E":
                                            vector[4] = a;
                                            break;
                                        case "F":
                                            vector[5] = a;
                                            break;
                                        case "G":
                                            vector[6] = a;
                                            break;
                                        case "H":
                                            vector[7] = a;
                                            break;
                                        case "I":
                                            vector[8] = a;
                                            break;
                                        case "J":
                                            vector[9] = a;
                                            break;
                                        case "K":
                                            vector[10] = a;
                                            break;
                                        case "L":
                                            vector[11] = a;
                                            break;
                                        case "M":
                                            vector[12] = a;
                                            break;
                                        case "N":
                                            vector[13] = a;
                                            break;
                                        case "O":
                                            vector[14] = a;
                                            break;
                                        case "P":
                                            vector[15] = a;
                                            break;
                                        case "Q":
                                            vector[16] = a;
                                            break;
                                        case "R":
                                            vector[17] = a;
                                            break;
                                        case "S":
                                            vector[18] = a;
                                            break;
                                        case "T":
                                            vector[19] = a;
                                            break;
                                        default:
                                            break;
                                    }
                                    aux = 0;
                                    band = true;
                                    cont++;
//                    
//                    System.out.println("--------------------------------------------------------------------------");
                                }
                            }
                        }
                    }

                }
                c.setText(vector[2]);
                d.setText(vector[3]);
                e.setText(vector[4]);
                f.setText(vector[5]);
                g.setText(vector[6]);
                h.setText(vector[7]);
                i1.setText(vector[8]);
                j.setText(vector[9]);
                k.setText(vector[10]);
                l.setText(vector[11]);
                m.setText(vector[12]);
                n.setText(vector[13]);
                o.setText(vector[14]);
                p.setText(vector[15]);
                q.setText(vector[16]);
                r.setText(vector[17]);
                s.setText(vector[18]);
                t.setText(vector[19]);
    }
    
    public void acomodar(){
        if (ruta_archivo.trim().length() != 0) {
//            this.numImg += 1;
            if (paginas == totalp) {
                paginas = 1;
            } else {
                paginas = paginas + 1;  
            }
            if (this.numImg >= this.ListaComponente.size()) {
                this.numImg = 0;
            }
            ArchivosVO pi = new ArchivosVO();
            for (int i = 0; i < ListaComponente.size(); i++) {
                pi = ListaComponente.get(numImg);
                break;
            }
            totalp = ListaComponente.size();
            this.img.setImagen(pi.getArchivos());
            this.img.disminuir();
            this.img.disminuir();
            this.img.disminuir();
            this.img.disminuir();
            this.img.disminuir();
            this.img.disminuir();
        } else {
//            JOptionPane.showMessageDialog(null, "Abrir un documento PDF primero");
        }
    }
    
    public void limpiarPantalla(){
        acomodar();
        revalidate();
        repaint();
    }
    
    public void abrir_pdf(String url)  {
        this.numImg = 0;
        ruta_archivo = url;
        this.ListaComponente = pn.leerPDF(url);
        for (int i = 0; i < ListaComponente.size(); i++) {
            pl = ListaComponente.get(i);;
            this.img.setImagen(pl.getArchivos());
        }
        paginas = 1;
        totalp = ListaComponente.size();
        ArchivosVO pi = new ArchivosVO();
        pi = ListaComponente.get(0);
        this.img.setImagen(pi.getArchivos());
    }
    
    public void verPlano(File archi){
        byte[] ruta = null;
        try {
            ruta = Files.readAllBytes(archi.toPath());
        } catch (IOException ex) {
            Logger.getLogger(Disenio1.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        limpiarPantalla();
        acomodar();
            if(archi == null){
                JOptionPane.showMessageDialog(this, "NO EXISTE ESTE PLANO","ADVERTENCIA",JOptionPane.WARNING_MESSAGE);
            }else{
            File n = archi;
            try {
                BufferedOutputStream bs = null;
                FileOutputStream fs = new FileOutputStream(n);
                bs = new BufferedOutputStream(fs);
                bs.write(ruta);
                
                bs.close();
                bs = null;
                
            } catch (IOException e) {
                n = new File("C:\\Pruebas\\BD\\img\\new2.pdf");
            try {
                BufferedOutputStream bs = null;
                FileOutputStream fs = new FileOutputStream(n);
                bs = new BufferedOutputStream(fs);
                bs.write(ruta);
                
                bs.close();
                bs = null;
                
            } catch (IOException e2) {
                JOptionPane.showMessageDialog(this, "ERROR: "+e2,"ADVERTENCIA",JOptionPane.WARNING_MESSAGE);
            }
            }
            
            abrir_pdf(n.toString());
            
            limpiarPantalla();
            
              acomodar();
            }
    }
    
    public boolean existeProyecto(String proyecto){
        boolean band = false;
        try{
            Connection con;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            String sql = "select Proyecto from proyectos where Proyecto like '"+proyecto+"'";
            ResultSet rs = st.executeQuery(sql);
            String proy = null;
            while(rs.next()){
                proy = rs.getString("Proyecto");
            }
            if(proy != null){
                band = true;
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "ERROR: "+e,"ERROR",JOptionPane.ERROR_MESSAGE);
        }
        return band;
    }
    
    public void visible(String empresa){
        switch (empresa) {
            case "3i":
                panelDurol.setVisible(false);
                panelAlign.setVisible(false);
                break;
            case "align":
                panelDurol.setVisible(false);
                panel3i.setVisible(false);
                break;
            case "durol":
                panelAlign.setVisible(false);
                panel3i.setVisible(false);
                break;
            default:
                break;
        }
    }
    
    public Disenio1(String empresa, Inicio1 inicio) {
        initComponents();
        this.inicio = inicio;
        this.empresa = empresa;
        visible(empresa);
        addPro();
        setBorde();
        verificarPlanosNoRegistrados();
        jScrollPane2.getVerticalScrollBar().setUnitIncrement(15);
        jScrollPane2.getHorizontalScrollBar().setUnitIncrement(15);
        ((javax.swing.plaf.basic.BasicInternalFrameUI) this.getUI()).setNorthPane(null);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        botonesPlanos = new javax.swing.JPopupMenu();
        btn3i = new javax.swing.JMenuItem();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        btnImportar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel10 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        panelCard = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        img = new mivisorpdf.CuadroImagen();
        jPanel3 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        txtProyecto = new javax.swing.JTextField();
        c = new javax.swing.JTextField();
        d = new javax.swing.JTextField();
        e = new javax.swing.JTextField();
        f = new javax.swing.JTextField();
        g = new javax.swing.JTextField();
        h = new javax.swing.JTextField();
        i1 = new javax.swing.JTextField();
        j = new javax.swing.JTextField();
        k = new javax.swing.JTextField();
        l = new javax.swing.JTextField();
        m = new javax.swing.JTextField();
        n = new javax.swing.JTextField();
        o = new javax.swing.JTextField();
        p = new javax.swing.JTextField();
        q = new javax.swing.JTextField();
        r = new javax.swing.JTextField();
        s = new javax.swing.JTextField();
        t = new javax.swing.JTextField();
        txtTol = new javax.swing.JTextField();
        jPanel13 = new javax.swing.JPanel();
        rSButtonRiple2 = new rojeru_san.RSButtonRiple();
        rSButtonRiple1 = new rojeru_san.RSButtonRiple();
        panelPlanos = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jPanel15 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel26 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        btnSalir = new javax.swing.JPanel();
        lblX = new javax.swing.JLabel();
        jPanel14 = new javax.swing.JPanel();
        panel3i = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        panelAlign = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        panelDurol = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jPanel16 = new javax.swing.JPanel();
        btnSalir1 = new javax.swing.JPanel();
        lblAtras = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem2 = new javax.swing.JMenuItem();

        btn3i.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        btn3i.setText("Agregar plano 3i                                          ");
        botonesPlanos.add(btn3i);

        setBorder(null);
        getContentPane().setLayout(new java.awt.BorderLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setForeground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new java.awt.BorderLayout(15, 15));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new java.awt.BorderLayout(15, 0));

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setPreferredSize(new java.awt.Dimension(370, 80));
        jPanel4.setLayout(new java.awt.BorderLayout());

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));

        btnImportar.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        btnImportar.setForeground(new java.awt.Color(0, 102, 255));
        btnImportar.setText("Subir planos");
        btnImportar.setBorder(null);
        btnImportar.setContentAreaFilled(false);
        btnImportar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnImportar.setFocusPainted(false);
        btnImportar.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
        btnImportar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnImportar.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/ImgAnimacion/pdf-file_48.png"))); // NOI18N
        btnImportar.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/ImgAnimacion/pdf-file_64.png"))); // NOI18N
        btnImportar.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        btnImportar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnImportar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnImportarActionPerformed(evt);
            }
        });
        jPanel8.add(btnImportar);

        jPanel4.add(jPanel8, java.awt.BorderLayout.CENTER);

        jScrollPane1.setBorder(null);
        jScrollPane1.setMinimumSize(new java.awt.Dimension(14, 40));
        jScrollPane1.setPreferredSize(new java.awt.Dimension(1345, 45));

        jPanel10.setBackground(new java.awt.Color(255, 255, 255));
        jPanel10.setMinimumSize(new java.awt.Dimension(10, 40));
        jPanel10.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jPanel10MouseExited(evt);
            }
        });
        jPanel10.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 15, 5));
        jScrollPane1.setViewportView(jPanel10);

        jPanel4.add(jScrollPane1, java.awt.BorderLayout.SOUTH);

        jPanel2.add(jPanel4, java.awt.BorderLayout.PAGE_START);

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));
        jPanel9.setLayout(new java.awt.GridLayout(1, 2, 10, 0));

        panelCard.setBackground(new java.awt.Color(255, 255, 255));
        panelCard.setLayout(new java.awt.BorderLayout());

        jScrollPane2.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 102, 255), 3, true));

        img.setBackground(new java.awt.Color(255, 255, 255));
        img.setName("img"); // NOI18N
        img.setPreferredSize(new java.awt.Dimension(210, 297));
        img.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                imgMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                imgMouseEntered(evt);
            }
        });
        img.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                imgKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                imgKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout imgLayout = new javax.swing.GroupLayout(img);
        img.setLayout(imgLayout);
        imgLayout.setHorizontalGroup(
            imgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 542, Short.MAX_VALUE)
        );
        imgLayout.setVerticalGroup(
            imgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 590, Short.MAX_VALUE)
        );

        jScrollPane2.setViewportView(img);

        panelCard.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        jPanel9.add(panelCard);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setLayout(new java.awt.BorderLayout());

        jPanel11.setBackground(new java.awt.Color(255, 255, 255));
        jPanel11.setLayout(new java.awt.GridLayout(20, 0, 0, 10));

        jLabel3.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText("PROYECTO:  ");
        jPanel11.add(jLabel3);

        jLabel7.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel7.setText("INTEGRACION:  ");
        jPanel11.add(jLabel7);

        jLabel8.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel8.setText("FRESADORA:  ");
        jPanel11.add(jLabel8);

        jLabel9.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel9.setText("TORNO:  ");
        jPanel11.add(jLabel9);

        jLabel10.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel10.setText("CNC:  ");
        jPanel11.add(jLabel10);

        jLabel11.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel11.setText("MAZAK:  ");
        jPanel11.add(jLabel11);

        jLabel12.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel12.setText("RECTIFICADO:  ");
        jPanel11.add(jLabel12);

        jLabel13.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel13.setText("SOLDADURA:  ");
        jPanel11.add(jLabel13);

        jLabel14.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel14.setText("ENSAMBLE:  ");
        jPanel11.add(jLabel14);

        jLabel15.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel15.setText("REVISION:  ");
        jPanel11.add(jLabel15);

        jLabel16.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel16.setText("NO. PARTE:  ");
        jPanel11.add(jLabel16);

        jLabel18.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel18.setText("DESCRIPCION:  ");
        jPanel11.add(jLabel18);

        jLabel17.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel17.setText("MATERIAL:  ");
        jPanel11.add(jLabel17);

        jLabel20.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel20.setText("DUREZA:  ");
        jPanel11.add(jLabel20);

        jLabel19.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel19.setText("MAQUINA:  ");
        jPanel11.add(jLabel19);

        jLabel22.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel22.setText("NO. ENSAMBLE:  ");
        jPanel11.add(jLabel22);

        jLabel21.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel21.setText("TRATAMIENTO:  ");
        jPanel11.add(jLabel21);

        jLabel24.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel24.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel24.setText("CANTIDAD:  ");
        jPanel11.add(jLabel24);

        jLabel25.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel25.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel25.setText("DIMENSION:  ");
        jPanel11.add(jLabel25);

        jLabel23.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel23.setText("TOLERANCIA: ");
        jPanel11.add(jLabel23);

        jPanel3.add(jPanel11, java.awt.BorderLayout.WEST);

        jPanel12.setBackground(new java.awt.Color(255, 255, 255));
        jPanel12.setLayout(new java.awt.GridLayout(20, 0, 0, 10));

        txtProyecto.setBackground(new java.awt.Color(255, 255, 255));
        txtProyecto.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtProyecto.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtProyecto.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        txtProyecto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtProyectoActionPerformed(evt);
            }
        });
        txtProyecto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtProyectoKeyTyped(evt);
            }
        });
        jPanel12.add(txtProyecto);

        c.setBackground(new java.awt.Color(255, 255, 255));
        c.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        c.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        c.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        c.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                cKeyTyped(evt);
            }
        });
        jPanel12.add(c);

        d.setBackground(new java.awt.Color(255, 255, 255));
        d.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        d.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        d.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        d.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                dKeyTyped(evt);
            }
        });
        jPanel12.add(d);

        e.setBackground(new java.awt.Color(255, 255, 255));
        e.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        e.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        e.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        e.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                eKeyTyped(evt);
            }
        });
        jPanel12.add(e);

        f.setBackground(new java.awt.Color(255, 255, 255));
        f.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        f.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        f.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        f.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                fKeyTyped(evt);
            }
        });
        jPanel12.add(f);

        g.setBackground(new java.awt.Color(255, 255, 255));
        g.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        g.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        g.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        g.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                gKeyTyped(evt);
            }
        });
        jPanel12.add(g);

        h.setBackground(new java.awt.Color(255, 255, 255));
        h.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        h.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        h.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        h.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                hKeyTyped(evt);
            }
        });
        jPanel12.add(h);

        i1.setBackground(new java.awt.Color(255, 255, 255));
        i1.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        i1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        i1.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        i1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                i1KeyTyped(evt);
            }
        });
        jPanel12.add(i1);

        j.setBackground(new java.awt.Color(255, 255, 255));
        j.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        j.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        j.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        j.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jKeyTyped(evt);
            }
        });
        jPanel12.add(j);

        k.setBackground(new java.awt.Color(255, 255, 255));
        k.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        k.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        k.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        k.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                kKeyTyped(evt);
            }
        });
        jPanel12.add(k);

        l.setBackground(new java.awt.Color(255, 255, 255));
        l.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        l.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        l.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        l.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                lKeyTyped(evt);
            }
        });
        jPanel12.add(l);

        m.setBackground(new java.awt.Color(255, 255, 255));
        m.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        m.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        m.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        m.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                mKeyTyped(evt);
            }
        });
        jPanel12.add(m);

        n.setBackground(new java.awt.Color(255, 255, 255));
        n.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        n.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        n.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        n.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                nKeyTyped(evt);
            }
        });
        jPanel12.add(n);

        o.setBackground(new java.awt.Color(255, 255, 255));
        o.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        o.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        o.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        o.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                oKeyTyped(evt);
            }
        });
        jPanel12.add(o);

        p.setBackground(new java.awt.Color(255, 255, 255));
        p.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        p.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        p.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        p.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pActionPerformed(evt);
            }
        });
        p.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                pKeyTyped(evt);
            }
        });
        jPanel12.add(p);

        q.setBackground(new java.awt.Color(255, 255, 255));
        q.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        q.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        q.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        q.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                qKeyTyped(evt);
            }
        });
        jPanel12.add(q);

        r.setBackground(new java.awt.Color(255, 255, 255));
        r.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        r.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        r.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        r.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                rKeyTyped(evt);
            }
        });
        jPanel12.add(r);

        s.setBackground(new java.awt.Color(255, 255, 255));
        s.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        s.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        s.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        s.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                sKeyTyped(evt);
            }
        });
        jPanel12.add(s);

        t.setBackground(new java.awt.Color(255, 255, 255));
        t.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        t.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        t.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        t.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tKeyTyped(evt);
            }
        });
        jPanel12.add(t);

        txtTol.setBackground(new java.awt.Color(255, 255, 255));
        txtTol.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtTol.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtTol.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        txtTol.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtTolKeyTyped(evt);
            }
        });
        jPanel12.add(txtTol);

        jPanel3.add(jPanel12, java.awt.BorderLayout.CENTER);

        jPanel13.setBackground(new java.awt.Color(255, 255, 255));

        rSButtonRiple2.setText("Cancelar Plano");
        rSButtonRiple2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rSButtonRiple2ActionPerformed(evt);
            }
        });
        jPanel13.add(rSButtonRiple2);

        rSButtonRiple1.setText("Subir");
        rSButtonRiple1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rSButtonRiple1ActionPerformed(evt);
            }
        });
        jPanel13.add(rSButtonRiple1);

        jPanel3.add(jPanel13, java.awt.BorderLayout.SOUTH);

        jPanel9.add(jPanel3);

        jPanel2.add(jPanel9, java.awt.BorderLayout.CENTER);

        panelPlanos.setBackground(new java.awt.Color(255, 255, 255));
        panelPlanos.setLayout(new java.awt.BorderLayout());

        jLabel2.setFont(new java.awt.Font("Roboto", 1, 16)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("    Planos Faltantes    ");
        panelPlanos.add(jLabel2, java.awt.BorderLayout.PAGE_START);

        jPanel15.setBackground(new java.awt.Color(255, 255, 255));
        jPanel15.setLayout(new javax.swing.BoxLayout(jPanel15, javax.swing.BoxLayout.Y_AXIS));
        panelPlanos.add(jPanel15, java.awt.BorderLayout.CENTER);

        jPanel2.add(panelPlanos, java.awt.BorderLayout.EAST);

        jPanel1.add(jPanel2, java.awt.BorderLayout.CENTER);

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setLayout(new java.awt.BorderLayout());

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));

        jLabel26.setFont(new java.awt.Font("Roboto", 1, 40)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(0, 165, 252));
        jLabel26.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel26.setText("Diseño");
        jPanel6.add(jLabel26);

        jPanel5.add(jPanel6, java.awt.BorderLayout.CENTER);

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));

        btnSalir.setBackground(new java.awt.Color(255, 255, 255));

        lblX.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        lblX.setForeground(new java.awt.Color(0, 0, 0));
        lblX.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblX.setText(" x ");
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
        btnSalir.add(lblX);

        jPanel7.add(btnSalir);

        jPanel5.add(jPanel7, java.awt.BorderLayout.EAST);

        jPanel14.setBackground(new java.awt.Color(255, 255, 255));
        jPanel14.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 20, 5));

        panel3i.setBackground(new java.awt.Color(255, 255, 255));
        panel3i.setLayout(new java.awt.BorderLayout());

        jLabel1.setFont(new java.awt.Font("French Script MT", 0, 60)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 102, 0));
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/diseño-3i.png"))); // NOI18N
        panel3i.add(jLabel1, java.awt.BorderLayout.WEST);

        jPanel14.add(panel3i);

        panelAlign.setBackground(new java.awt.Color(255, 255, 255));
        panelAlign.setLayout(new java.awt.BorderLayout());

        jLabel5.setBackground(new java.awt.Color(52, 38, 13));
        jLabel5.setFont(new java.awt.Font("Century Gothic", 0, 24)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(52, 38, 13));
        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/align-diseño.png"))); // NOI18N
        panelAlign.add(jLabel5, java.awt.BorderLayout.CENTER);

        jPanel14.add(panelAlign);

        panelDurol.setBackground(new java.awt.Color(255, 255, 255));
        panelDurol.setLayout(new java.awt.BorderLayout());

        jLabel6.setFont(new java.awt.Font("Constantia", 2, 24)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(34, 61, 145));
        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/durol-diseño.png"))); // NOI18N
        jLabel6.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        panelDurol.add(jLabel6, java.awt.BorderLayout.CENTER);

        jPanel14.add(panelDurol);

        jPanel5.add(jPanel14, java.awt.BorderLayout.SOUTH);

        jPanel16.setBackground(new java.awt.Color(255, 255, 255));

        btnSalir1.setBackground(new java.awt.Color(255, 255, 255));

        lblAtras.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        lblAtras.setForeground(new java.awt.Color(0, 0, 0));
        lblAtras.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblAtras.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/atras_16.png"))); // NOI18N
        lblAtras.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblAtras.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblAtrasMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblAtrasMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblAtrasMouseExited(evt);
            }
        });
        btnSalir1.add(lblAtras);

        jPanel16.add(btnSalir1);

        jPanel5.add(jPanel16, java.awt.BorderLayout.WEST);

        jPanel1.add(jPanel5, java.awt.BorderLayout.PAGE_START);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        jMenu1.setText("File");

        jMenuItem1.setText("Dis");
        jMenuItem1.setEnabled(false);
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuItem3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/BOM_16.png"))); // NOI18N
        jMenuItem3.setText("Subir BOM                                  ");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem3);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");

        jMenuItem2.setText("Ver codigo de barras");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem2);

        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void dKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_dKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_dKeyTyped

    private void eKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_eKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_eKeyTyped

    private void fKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_fKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_fKeyTyped

    private void gKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_gKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_gKeyTyped

    private void hKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_hKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_hKeyTyped

    private void i1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_i1KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_i1KeyTyped

    private void jKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_jKeyTyped

    private void btnImportarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImportarActionPerformed
        importarDocs();
        verificarPlanosNoRegistrados();
    }//GEN-LAST:event_btnImportarActionPerformed
    
    
    private void cKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cKeyTyped
        char c = evt.getKeyChar();
        if (c < '0' || c > '9')
            evt.consume();
    }//GEN-LAST:event_cKeyTyped

    private void kKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_kKeyTyped

    private void lKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_lKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_lKeyTyped

    private void nKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_nKeyTyped

    private void mKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_mKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_mKeyTyped

    private void oKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_oKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_oKeyTyped

    private void pKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_pKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_pKeyTyped

    private void qKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_qKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_qKeyTyped

    private void rKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_rKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_rKeyTyped

    private void sKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_sKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_sKeyTyped

    private void lblXMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblXMouseClicked
        dispose();
    }//GEN-LAST:event_lblXMouseClicked

    private void lblXMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblXMouseEntered
        btnSalir.setBackground(Color.red);
        lblX.setForeground(Color.white);
    }//GEN-LAST:event_lblXMouseEntered

    private void lblXMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblXMouseExited
        btnSalir.setBackground(Color.white);
        lblX.setForeground(Color.black);
    }//GEN-LAST:event_lblXMouseExited

    private void tKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_tKeyTyped

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        dis dis = new dis();
        dis.setVisible(true);
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void txtTolKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTolKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTolKeyTyped

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed

    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void imgMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_imgMouseClicked
        img.aumentar();
    }//GEN-LAST:event_imgMouseClicked

    private void rSButtonRiple1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rSButtonRiple1ActionPerformed
        iniciar();
    }//GEN-LAST:event_rSButtonRiple1ActionPerformed

    private void pActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_pActionPerformed

    private void rSButtonRiple2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rSButtonRiple2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rSButtonRiple2ActionPerformed

    private void imgMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_imgMouseEntered
        
    }//GEN-LAST:event_imgMouseEntered

    private void imgKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_imgKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_CONTROL) {
                ctrlPressed = true;
            }
    }//GEN-LAST:event_imgKeyPressed

    private void imgKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_imgKeyReleased
        if (evt.getKeyCode() == KeyEvent.VK_CONTROL) {
                ctrlPressed = true;
            }
    }//GEN-LAST:event_imgKeyReleased

    private void txtProyectoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtProyectoKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtProyectoKeyTyped

    private void txtProyectoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtProyectoActionPerformed
        if(existeProyecto(txtProyecto.getText())){
            txtProyecto.setEnabled(false);
            txtProyecto.setForeground(Color.blue);
        }
    }//GEN-LAST:event_txtProyectoActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        JFrame f = (JFrame) JOptionPane.getFrameForComponent(this);
        subirBOM subir = new subirBOM(f,true);
        subir.setVisible(true);
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jPanel10MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel10MouseExited
        jPanel10.setBorder(null);
    }//GEN-LAST:event_jPanel10MouseExited

    private void lblAtrasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblAtrasMouseClicked
        InicioDiseño c = new InicioDiseño(inicio);
        inicio.jDesktopPane1.add(c);
        c.toFront();
        c.setLocation(inicio.jDesktopPane1.getWidth() / 2 - c.getWidth() / 2, inicio.jDesktopPane1.getHeight() / 2 - c.getHeight() / 2);
        try{
            c.setMaximum(true);
        }catch(PropertyVetoException e){
            Logger.getLogger(Inicio1.class.getName()).log(Level.SEVERE,null,e);
        }
        c.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_lblAtrasMouseClicked

    private void lblAtrasMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblAtrasMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_lblAtrasMouseEntered

    private void lblAtrasMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblAtrasMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_lblAtrasMouseExited


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPopupMenu botonesPlanos;
    private javax.swing.JMenuItem btn3i;
    public javax.swing.JButton btnImportar;
    private javax.swing.JPanel btnSalir;
    private javax.swing.JPanel btnSalir1;
    private javax.swing.JTextField c;
    private javax.swing.JTextField d;
    private javax.swing.JTextField e;
    private javax.swing.JTextField f;
    private javax.swing.JTextField g;
    private javax.swing.JTextField h;
    private javax.swing.JTextField i1;
    private mivisorpdf.CuadroImagen img;
    private javax.swing.JTextField j;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField k;
    private javax.swing.JTextField l;
    private javax.swing.JLabel lblAtras;
    private javax.swing.JLabel lblX;
    private javax.swing.JTextField m;
    private javax.swing.JTextField n;
    private javax.swing.JTextField o;
    private javax.swing.JTextField p;
    private javax.swing.JPanel panel3i;
    private javax.swing.JPanel panelAlign;
    private javax.swing.JPanel panelCard;
    private javax.swing.JPanel panelDurol;
    private javax.swing.JPanel panelPlanos;
    private javax.swing.JTextField q;
    private javax.swing.JTextField r;
    private rojeru_san.RSButtonRiple rSButtonRiple1;
    private rojeru_san.RSButtonRiple rSButtonRiple2;
    private javax.swing.JTextField s;
    private javax.swing.JTextField t;
    private javax.swing.JTextField txtProyecto;
    private javax.swing.JTextField txtTol;
    // End of variables declaration//GEN-END:variables

    @Override
    public void actionPerformed(ActionEvent e) {
       if(datosHoras != null){
           if(e.getSource() == datosHoras.btnGuardar){
               if(datosHoras.d.getText().equals("")){
                   JOptionPane.showMessageDialog(this, "Debes llenar horas de Fresadora");
               } else if(datosHoras.e.getText().equals("")){
                   JOptionPane.showMessageDialog(this, "Debes llenar horas de Torno");
               } else if(datosHoras.f.getText().equals("")){
                   JOptionPane.showMessageDialog(this, "Debes llenar horas de Cnc");
               } else if(datosHoras.s.getText().equals("")){
                   JOptionPane.showMessageDialog(this, "Debes llenar horas de Cantidad");
               }else if(datosHoras.k.getText().equals("")){
                   JOptionPane.showMessageDialog(this, "Debes llenar la Revision");
               }
               else{
                   bandDatosHoras = true;
                   d.setText(datosHoras.d.getText());
                   this.e.setText(datosHoras.e.getText());
                   f.setText(datosHoras.f.getText());
                   s.setText(datosHoras.s.getText());
                   k.setText(datosHoras.k.getText());
                   datosHoras.dispose();
               }
           }
       }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        for (int i = 0; i < botones.length; i++) {
            
            if(!botones[i].getForeground().equals(new Color(210,210,210))){
                botones[i].setForeground(Color.black);
            }
            if(e.getSource() == botones[i]){
                    if(!botones[i].getForeground().equals(new Color(210,210,210))){
                        c.setText("");
                        d.setText("");
                        this.e.setText("");
                        f.setText("");
                        g.setText("");
                        h.setText("");
                        i1.setText("");
                        j.setText("");
                        k.setText("");
                        l.setText("");
                        m.setText("");
                        n.setText("");
                        o.setText("");
                        p.setText("");
                        q.setText("");
                        r.setText("");
                        s.setText("");
                        t.setText("");
                        botones[i].setForeground(new Color(0,165,255));
                        seleccionado = i;
                        verPlano(archivos[i]);
                            if(botones[i].getName().equals("align")){
                                subirAlignV2(archivos[i]);
                            }else if(botones[i].getName().equals("durol")){
                                subirDurolV2(archivos[i]);
                            }else if(botones[i].getName().equals("3i")){
                                subir3i(archivos[i]);
                            }else{
                                botones[i].setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/3i_16.png"))); // NOI18N
                                subir3i(archivos[i]);
                            }
                }else{
                        c.setText("");
                        d.setText("");
                        this.e.setText("");
                        f.setText("");
                        g.setText("");
                        h.setText("");
                        i1.setText("");
                        j.setText("");
                        k.setText("");
                        l.setText("");
                        m.setText("");
                        n.setText("");
                        o.setText("");
                        p.setText("");
                        q.setText("");
                        r.setText("");
                        s.setText("");
                        t.setText("");
                        botones[i].setForeground(new Color(0,165,255));
                        seleccionado = i;
                        verPlano(archivos[i]);
                        subir3i(archivos[i]); 
                    }
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
       
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        
    }

    @Override
    public void mouseExited(MouseEvent e) {
        
    }

}
