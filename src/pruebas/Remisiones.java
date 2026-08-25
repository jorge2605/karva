package pruebas;

import Conexiones.Conexion;
import Modelo.CabezeraRemisiones;
import VentanaEmergente.Ventas.verDocumentos;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.mxrck.autocompleter.TextAutoCompleter;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import scrollPane.ScrollBarCustom;

public class Remisiones extends javax.swing.JInternalFrame implements ActionListener{

    TextAutoCompleter au, auP;
    String idCliente;
    String numEmpleado;
    String numeroRemision = "";
    VentanaEmergente.Remisiones.Cliente cliente;
    
    public void autocompletarProyecto(){
        try{
            auP = new TextAutoCompleter(txtProyecto);
            Connection con;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            String sql = "select Proyecto from Proyectos";
            ResultSet rs = st.executeQuery(sql);
            String datos[] = new String[10];
            while(rs.next()){
                datos[0] = rs.getString("Proyecto");
                auP.addItem(datos[0]);
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "ERROR: "+e,"ERROR",JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void limpiarDatos(){
        txtCantidad.setText("");
        txtArticulo.setText("");
    }
    
    public void limpiarPantalla(){
        limpiarTabla();
        txtCli.setText("");
        txtDireccion.setText("");
        txtCiudad.setText("");
        txtCondicion.setText("");
        txtProyecto.setText("");
    }
    
    public void limpiarTabla(){
        Tabla1.setBackground(new java.awt.Color(255, 255, 255));
        Tabla1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "CANTIDAD", "ARTICULO", "ENTREGADO", "RECIBIDO"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
    }
    
    public void eliminarFilas(){
        DefaultTableModel miModelo = (DefaultTableModel) Tabla1.getModel();
        for (int i = Tabla1.getSelectedRows().length-1; i >= 0; i--) {
            int n = Tabla1.getSelectedRows()[i];
                miModelo.removeRow(n);
            
        }
    }
    
    public PdfPCell border(PdfPCell celda, float top, float bot, float left, float rig){
            celda.setBorderWidthBottom(bot);
            celda.setBorderWidthTop(top);
            celda.setBorderWidthRight(rig);
            celda.setBorderWidthLeft(left);
        return celda;
    }
    
    public void autocompletarClientes(){
        try{
            au = new TextAutoCompleter(txtCliente);
            Connection con;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            String sql = "select * from registroclientes"; 
            ResultSet rs = st.executeQuery(sql);
            String nombre, direccion,ciudad,condicion;
//            au = null;
            while(rs.next()){
                au.addItem(rs.getString("Nombre"));
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "ERROR: "+e,"ERROR",JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void subirArchivo(String departamento, String ruta){
        File documento = new File(ruta);
        try{
            Connection con;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Date f = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            String fecha = sdf.format(f);
            String sql1 = "insert into archivosproyectos (NombreArchivo, Fecha, Proyecto, Documento, Archivo) values(?,?,?,?,?)";
            PreparedStatement pst1 = con.prepareStatement(sql1);

            byte[] pe;
            pe = new byte[(int) documento.length()];
            try{
                InputStream input = new FileInputStream(documento);
                input.read(pe);
            }catch(IOException e){
                JOptionPane.showMessageDialog(this,"Error archivo: "+e, "Error", JOptionPane.ERROR_MESSAGE);
            }

            pst1.setString(1,documento.getName());
            pst1.setString(2,fecha);
            pst1.setString(3,txtProyecto.getText());
            pst1.setString(4,departamento);
            pst1.setBytes(5,pe);

            int n2 = pst1.executeUpdate();
            if(n2 > 0){
                JOptionPane.showMessageDialog(this,"DOCUMENTOS GUARDADOS");
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "ERROR: "+e,"ERROR",JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(verDocumentos.class.getName()).log(Level.SEVERE,null,e);
        }
    }
    
    public void crearPdf(String noRemision){
        try{
            String ruta = "C:/Pruebas/DOCS/Remisiones/R-" + noRemision + ".pdf";
            Document document = new Document(PageSize.A4, 36, 36, 90, 36);
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(ruta));
            CabezeraRemisiones encabezado = new CabezeraRemisiones();
            encabezado.setEncabezado("ENCABEZADO DE REMISIONES");
            writer.setPageEvent(encabezado);
            document.open();
            
            //---------------------------------FUENTES---------------------------------
            com.itextpdf.text.Font fuente1 = new com.itextpdf.text.Font();
            fuente1.setSize(48);
            fuente1.setFamily("Roboto");
            fuente1.setColor(BaseColor.BLACK);
            fuente1.setStyle(com.itextpdf.text.Font.NORMAL);
            
            com.itextpdf.text.Font fuente2 = new com.itextpdf.text.Font();
            fuente2.setSize(14);
            fuente2.setFamily("Roboto");
            fuente2.setColor(BaseColor.BLACK);
            fuente2.setStyle(com.itextpdf.text.Font.NORMAL);
            
            com.itextpdf.text.Font fuente3 = new com.itextpdf.text.Font();
            fuente3.setSize(14);
            fuente3.setFamily("Roboto");
            fuente3.setColor(BaseColor.RED);
            fuente3.setStyle(com.itextpdf.text.Font.BOLD);
            
            com.itextpdf.text.Font fuente4 = new com.itextpdf.text.Font();
            fuente4.setSize(30);
            fuente4.setFamily("Roboto");
            fuente4.setColor(BaseColor.BLACK);
            fuente4.setStyle(com.itextpdf.text.Font.NORMAL);
            
            com.itextpdf.text.Font fuenteFecha = new com.itextpdf.text.Font();
            fuenteFecha.setSize(10);
            fuenteFecha.setFamily("Roboto");
            fuenteFecha.setColor(BaseColor.BLACK);
            fuenteFecha.setStyle(com.itextpdf.text.Font.NORMAL);
            
            com.itextpdf.text.Font fuenteCliente = new com.itextpdf.text.Font();
            fuenteCliente.setSize(12);
            fuenteCliente.setFamily("Roboto");
            fuenteCliente.setColor(BaseColor.BLACK);
            fuenteCliente.setStyle(com.itextpdf.text.Font.NORMAL);
            
            com.itextpdf.text.Font fuenteArticulos = new com.itextpdf.text.Font();
            fuenteArticulos.setSize(10);
            fuenteArticulos.setFamily("Roboto");
            fuenteArticulos.setColor(BaseColor.BLACK);
            fuenteArticulos.setStyle(com.itextpdf.text.Font.BOLD);
            
            
            //------------------------------------------------------------------------------------------------
            //-----------------------------------PARTE 1, NOTA REMISION Y FECHAS-------------------------------
            PdfPTable tabla1 = new PdfPTable(2);
            tabla1.setWidthPercentage(100);
            float[] medidads = {600,200};
            tabla1.setWidths(medidads);
            
            PdfPTable tabla2 = new PdfPTable(3);
            tabla2.setWidthPercentage(100);
            
            PdfPCell c1 = new PdfPCell(new Paragraph("DIA",fuenteFecha));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            PdfPCell c2 = new PdfPCell(new Paragraph("MES",fuenteFecha));
            c2.setHorizontalAlignment(Element.ALIGN_CENTER);
            PdfPCell c3 = new PdfPCell(new Paragraph("AÑO",fuenteFecha));
            c3.setHorizontalAlignment(Element.ALIGN_CENTER);
            
            Date d = new Date();
            SimpleDateFormat dia = new SimpleDateFormat("dd");
            SimpleDateFormat mes = new SimpleDateFormat("MM");
            SimpleDateFormat ano = new SimpleDateFormat("yyyy");
            String day = dia.format(d);
            String m = mes.format(d);
            String a = ano.format(d);
            
            PdfPCell c4 = new PdfPCell(new Paragraph(day,fuente2));
            c4.setHorizontalAlignment(Element.ALIGN_CENTER);
            PdfPCell c5 = new PdfPCell(new Paragraph(m,fuente2));
            c5.setHorizontalAlignment(Element.ALIGN_CENTER);
            PdfPCell c6 = new PdfPCell(new Paragraph(a,fuente2));
            c6.setHorizontalAlignment(Element.ALIGN_CENTER);
            
            PdfPCell t = new PdfPCell(new Paragraph());
            t.setBorder(0);
            
            tabla2.addCell(c1);
            tabla2.addCell(c2);
            tabla2.addCell(c3);
            tabla2.addCell(c4);
            tabla2.addCell(c5);
            tabla2.addCell(c6);
            t.addElement(tabla2);
            
            PdfPCell co1 = new PdfPCell(new Paragraph("NOTA DE",fuente4));
            co1.setBorder(0);
            co1.setHorizontalAlignment(Element.ALIGN_CENTER);
            
            PdfPCell co3 = new PdfPCell(new Paragraph("REMISION",fuente1));
            co3.setBorder(0);
            co3.setHorizontalAlignment(Element.ALIGN_CENTER);
//            PdfPCell co4 = new PdfPCell(new Paragraph("4",fuente4));

            PdfPTable tablaNumero = new PdfPTable(2);
            tablaNumero.setWidthPercentage(100);
            
            PdfPCell no = new PdfPCell(new Paragraph("NO.",fuente3));
            no.setHorizontalAlignment(Element.ALIGN_CENTER);
            
            PdfPCell nu = new PdfPCell(new Paragraph(noRemision,fuente3));
            nu.setHorizontalAlignment(Element.ALIGN_CENTER);
            
            tablaNumero.addCell(no);
            tablaNumero.addCell(nu);
            
            PdfPCell nume = new PdfPCell(new Paragraph());
            nume.setBorder(0);
            nume.addElement(tablaNumero);
            
            tabla1.addCell(co1);
            tabla1.addCell(t);
            tabla1.addCell(co3);
            tabla1.addCell(nume);
            //------------------------------------------------------------------------------
            //----------------------------TABLA2 CLIENTE DATOS--------------------------------------------
            PdfPTable tablaCliente = new PdfPTable(2);
            tablaCliente.setWidthPercentage(100);
            float medidas2[] = {200,700};
            tablaCliente.setWidths(medidas2);
            
            PdfPCell cliente = new PdfPCell(new Paragraph("CLIENTE: ",fuenteCliente));
            border(cliente,0.75F,0,0.75f,0);
            cliente.setHorizontalAlignment(Element.ALIGN_RIGHT);
            
            PdfPCell direccion = new PdfPCell(new Paragraph("DIRECCION: ",fuenteCliente));
            border(direccion,0,0,0.75f,0);
            direccion.setHorizontalAlignment(Element.ALIGN_RIGHT);
            
            PdfPCell ciudad = new PdfPCell(new Paragraph("CIUDAD: ",fuenteCliente));
            border(ciudad,0,0f,0.75f,0);
            ciudad.setHorizontalAlignment(Element.ALIGN_RIGHT);
            
            PdfPCell condicion = new PdfPCell(new Paragraph("CONDICION: ",fuenteCliente));
            border(condicion,0.75f,0.75f,0.75f,0);
            condicion.setHorizontalAlignment(Element.ALIGN_RIGHT);
            
            PdfPCell proyecto = new PdfPCell(new Paragraph("PROYECTO: ",fuenteCliente));
            border(proyecto,0,.75f,0.75f,0);
            proyecto.setHorizontalAlignment(Element.ALIGN_RIGHT);
            
            PdfPCell cli = new PdfPCell(new Paragraph(txtCli.getText(),fuenteFecha));
            border(cli,0.5f,0,0,0.75f);
            cli.setHorizontalAlignment(Element.ALIGN_LEFT);
            
            PdfPCell dir = new PdfPCell(new Paragraph(txtDireccion.getText(),fuenteFecha));
            border(dir,0,0,0,0.75f);
            dir.setHorizontalAlignment(Element.ALIGN_LEFT);
            
            PdfPCell ciu = new PdfPCell(new Paragraph(txtCiudad.getText(),fuenteFecha));
            border(ciu,0,0,0,0.75f);
            ciu.setHorizontalAlignment(Element.ALIGN_LEFT);
            
            PdfPCell con = new PdfPCell(new Paragraph(txtCondicion.getText(),fuenteFecha));
            border(con,0.75f,0.75f,0,0.75f);
            con.setHorizontalAlignment(Element.ALIGN_LEFT);
            
            PdfPCell pro = new PdfPCell(new Paragraph(txtProyecto.getText(),fuenteFecha));
            border(pro,0,.75f,0,0.75f);
            pro.setHorizontalAlignment(Element.ALIGN_LEFT);
            
            PdfPCell blan = new PdfPCell(new Paragraph("\n"));
            border(blan,0,0,0,0);
            
            tablaCliente.addCell(blan);
            tablaCliente.addCell(blan);
            tablaCliente.addCell(cliente);
            tablaCliente.addCell(cli);
            tablaCliente.addCell(direccion);
            tablaCliente.addCell(dir);
            tablaCliente.addCell(ciudad);
            tablaCliente.addCell(ciu);
            tablaCliente.addCell(proyecto);
            tablaCliente.addCell(pro);
            tablaCliente.addCell(blan);
            tablaCliente.addCell(blan);
            tablaCliente.addCell(condicion);
            tablaCliente.addCell(con);
            tablaCliente.addCell(blan);
            tablaCliente.addCell(blan);
            //-----------------------------------------------------------------------------------------------------
            //----------------------TABLA DE ARTICULOS------------------------------------------------------------
            PdfPTable tablaArticulos = new PdfPTable(4);
            tablaArticulos.setWidthPercentage(100);
            float medidas3[] = {100,400,150,150};
            tablaArticulos.setWidths(medidas3);
            
            PdfPCell cCantidad = new PdfPCell(new Paragraph("CANTIDAD",fuenteArticulos));
            cCantidad.setHorizontalAlignment(Element.ALIGN_CENTER);
            cCantidad.setBorderWidth(1f);
            
            PdfPCell cArticulo = new PdfPCell(new Paragraph("ARTICULO",fuenteArticulos));
            cArticulo.setHorizontalAlignment(Element.ALIGN_CENTER);
            cArticulo.setBorderWidth(1f);
            
            PdfPCell cEntregado = new PdfPCell(new Paragraph("ENTREGADO",fuenteArticulos));
            cEntregado.setHorizontalAlignment(Element.ALIGN_CENTER);
            cEntregado.setBorderWidth(1f);
            
            PdfPCell cRecibido = new PdfPCell(new Paragraph("RECIBIDO",fuenteArticulos));
            cRecibido.setHorizontalAlignment(Element.ALIGN_CENTER);
            cRecibido.setBorderWidth(1f);
            
            tablaArticulos.addCell(cCantidad);
            tablaArticulos.addCell(cArticulo);
            tablaArticulos.addCell(cEntregado);
            tablaArticulos.addCell(cRecibido);
            
            for (int i = 0; i < Tabla1.getRowCount(); i++) {
                for (int j = 0; j < 4; j++) {
                    PdfPCell cel;
                    if(j < 2)
                    {
                        cel = new PdfPCell(new Paragraph(Tabla1.getValueAt(i, j).toString(),fuenteArticulos));
                        cel.setBorderWidth(1f);
                        
                        if(j == 0){
                            cel.setHorizontalAlignment(Element.ALIGN_CENTER);
                        }
                    }else
                    {
                        cel = new PdfPCell(new Paragraph("\n",fuenteArticulos));
                        cel.setBorderWidth(1f);
                        cel.setPaddingBottom(90);
                    }
                    
                    tablaArticulos.addCell(cel);
                }
            }
            //----------------------------------------------------------------------------------------------------
            
            //---------------------DOCUMENTOS PARA AGREGAR-----------------------------------
            document.add(tabla1);
            document.add(tablaCliente);
            document.add(tablaArticulos);
            //-------------------------------------------------------------------------------
            document.close();
            int opc = JOptionPane.showConfirmDialog(this, "¿Deseas guardar la documentacion de la remision?");
            if(opc == 0){
                subirArchivo("4", ruta);
            }
            Desktop.getDesktop().open(new File(ruta));
        }catch(Exception e){
            JOptionPane.showMessageDialog(this, "ERROR: "+e,"ERROR",JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public Remisiones(String numEmpleado) {
        initComponents();
        ((javax.swing.plaf.basic.BasicInternalFrameUI) this.getUI()).setNorthPane(null);
        autocompletarClientes();
        autocompletarProyecto();
        Tabla1.getTableHeader().setFont(new Font("Roboto", Font.BOLD, 14));
        Tabla1.getTableHeader().setOpaque(false);
        Tabla1.getTableHeader().setBackground(new Color(0, 0, 0, 0));
        Tabla1.getTableHeader().setForeground(Color.black);
        Tabla1.setRowHeight(25);
        Tabla1.setShowVerticalLines(false);
        Tabla1.setGridColor(new Color(220,220,220));
        jScrollPane1.setVerticalScrollBar(new ScrollBarCustom(new java.awt.Color(255,102,0)));
        jScrollPane2.setVerticalScrollBar(new ScrollBarCustom(new java.awt.Color(255,102,0)));
        this.numEmpleado = numEmpleado;
        btnQuitarProyecto.setVisible(false);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupMenu1 = new javax.swing.JPopupMenu();
        btnBorrar = new javax.swing.JMenuItem();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        pan = new javax.swing.JPanel();
        panelSalir = new javax.swing.JPanel();
        lblSalir = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        btnNuevaRemision = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        btnAbrirRemision = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        btnNuevoCliente = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        txtCliente = new javax.swing.JTextField();
        jPanel9 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        jPanel17 = new javax.swing.JPanel();
        txtProyecto = new javax.swing.JTextField();
        btnQuitarProyecto = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        txtCli = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtDireccion = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtCiudad = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txtCondicion = new javax.swing.JTextField();
        jPanel11 = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        jPanel14 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        txtCantidad = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jPanel15 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtArticulo = new javax.swing.JTextArea();
        jLabel15 = new javax.swing.JLabel();
        btnAgregar = new javax.swing.JButton();
        jPanel13 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        Tabla1 = new javax.swing.JTable();
        jPanel16 = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();

        btnBorrar.setText("Borrar lineas seleccionadas");
        btnBorrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBorrarActionPerformed(evt);
            }
        });
        jPopupMenu1.add(btnBorrar);

        setBorder(null);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new java.awt.BorderLayout());

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new java.awt.BorderLayout());

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Roboto", 1, 48)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 102, 0));
        jLabel1.setText("        REMISIONES        ");
        jLabel1.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 3, 0, new java.awt.Color(255, 102, 0)));
        jPanel4.add(jLabel1);

        jPanel3.add(jPanel4);

        jPanel2.add(jPanel3, java.awt.BorderLayout.CENTER);

        pan.setBackground(new java.awt.Color(255, 255, 255));

        panelSalir.setBackground(new java.awt.Color(255, 255, 255));

        lblSalir.setFont(new java.awt.Font("Roboto", 1, 36)); // NOI18N
        lblSalir.setForeground(new java.awt.Color(0, 0, 0));
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

        jPanel2.add(pan, java.awt.BorderLayout.EAST);

        jPanel1.add(jPanel2, java.awt.BorderLayout.PAGE_START);

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setLayout(new java.awt.BorderLayout());

        jPanel6.setBackground(new java.awt.Color(255, 244, 224));

        btnNuevaRemision.setBackground(new java.awt.Color(255, 255, 255));
        btnNuevaRemision.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        btnNuevaRemision.setForeground(new java.awt.Color(0, 0, 0));
        btnNuevaRemision.setText("NUEVA REMISION");
        btnNuevaRemision.setBorder(null);
        btnNuevaRemision.setBorderPainted(false);
        btnNuevaRemision.setContentAreaFilled(false);
        btnNuevaRemision.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnNuevaRemision.setFocusPainted(false);
        btnNuevaRemision.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnNuevaRemisionMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnNuevaRemisionMouseExited(evt);
            }
        });
        btnNuevaRemision.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevaRemisionActionPerformed(evt);
            }
        });
        jPanel6.add(btnNuevaRemision);

        jLabel2.setText("          ");
        jPanel6.add(jLabel2);

        btnAbrirRemision.setBackground(new java.awt.Color(255, 255, 255));
        btnAbrirRemision.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        btnAbrirRemision.setForeground(new java.awt.Color(0, 0, 0));
        btnAbrirRemision.setText("ABRIR REMISION");
        btnAbrirRemision.setBorder(null);
        btnAbrirRemision.setBorderPainted(false);
        btnAbrirRemision.setContentAreaFilled(false);
        btnAbrirRemision.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAbrirRemision.setFocusPainted(false);
        btnAbrirRemision.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnAbrirRemisionMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnAbrirRemisionMouseExited(evt);
            }
        });
        btnAbrirRemision.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAbrirRemisionActionPerformed(evt);
            }
        });
        jPanel6.add(btnAbrirRemision);

        jLabel3.setText("          ");
        jPanel6.add(jLabel3);

        btnNuevoCliente.setBackground(new java.awt.Color(255, 255, 255));
        btnNuevoCliente.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        btnNuevoCliente.setForeground(new java.awt.Color(0, 0, 0));
        btnNuevoCliente.setText("NUEVO CLIENTE");
        btnNuevoCliente.setBorder(null);
        btnNuevoCliente.setBorderPainted(false);
        btnNuevoCliente.setContentAreaFilled(false);
        btnNuevoCliente.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnNuevoCliente.setFocusPainted(false);
        btnNuevoCliente.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnNuevoClienteMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnNuevoClienteMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnNuevoClienteMouseExited(evt);
            }
        });
        btnNuevoCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoClienteActionPerformed(evt);
            }
        });
        jPanel6.add(btnNuevoCliente);

        jLabel4.setText("          ");
        jPanel6.add(jLabel4);

        jLabel5.setText("          ");
        jPanel6.add(jLabel5);

        jLabel6.setText("        ");
        jPanel6.add(jLabel6);

        jPanel5.add(jPanel6, java.awt.BorderLayout.PAGE_START);

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setLayout(new java.awt.BorderLayout(10, 10));

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));
        jPanel8.setLayout(new java.awt.BorderLayout(10, 10));

        jLabel7.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel7.setText("SELECCIONAR CLIENTE:   ");
        jPanel8.add(jLabel7, java.awt.BorderLayout.WEST);

        txtCliente.setBackground(new java.awt.Color(255, 255, 255));
        txtCliente.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtCliente.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCliente.setText("                                                                                                                         ");
        txtCliente.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        txtCliente.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtClienteFocusGained(evt);
            }
        });
        txtCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtClienteActionPerformed(evt);
            }
        });
        jPanel8.add(txtCliente, java.awt.BorderLayout.CENTER);

        jPanel7.add(jPanel8, java.awt.BorderLayout.PAGE_START);

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));
        jPanel9.setLayout(new java.awt.BorderLayout());

        jPanel10.setBackground(new java.awt.Color(255, 255, 255));
        jPanel10.setLayout(new java.awt.GridLayout(5, 2, 10, 10));

        jLabel17.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel17.setText("PROYECTO:");
        jPanel10.add(jLabel17);

        jPanel17.setLayout(new java.awt.BorderLayout());

        txtProyecto.setBackground(new java.awt.Color(255, 255, 255));
        txtProyecto.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        txtProyecto.setForeground(new java.awt.Color(0, 0, 0));
        txtProyecto.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(240, 240, 240)));
        txtProyecto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtProyectoActionPerformed(evt);
            }
        });
        jPanel17.add(txtProyecto, java.awt.BorderLayout.CENTER);

        btnQuitarProyecto.setBackground(new java.awt.Color(255, 255, 255));
        btnQuitarProyecto.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        btnQuitarProyecto.setText("X");
        btnQuitarProyecto.setBorderPainted(false);
        btnQuitarProyecto.setContentAreaFilled(false);
        btnQuitarProyecto.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnQuitarProyecto.setFocusPainted(false);
        btnQuitarProyecto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQuitarProyectoActionPerformed(evt);
            }
        });
        jPanel17.add(btnQuitarProyecto, java.awt.BorderLayout.LINE_END);

        jPanel10.add(jPanel17);

        jLabel8.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel8.setText("CLIENTE:");
        jPanel10.add(jLabel8);

        txtCli.setEditable(false);
        txtCli.setBackground(new java.awt.Color(255, 102, 0));
        txtCli.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        txtCli.setForeground(new java.awt.Color(255, 255, 255));
        txtCli.setBorder(null);
        jPanel10.add(txtCli);

        jLabel9.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel9.setText("DIRECCION:");
        jPanel10.add(jLabel9);

        txtDireccion.setEditable(false);
        txtDireccion.setBackground(new java.awt.Color(255, 102, 0));
        txtDireccion.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        txtDireccion.setForeground(new java.awt.Color(255, 255, 255));
        txtDireccion.setBorder(null);
        jPanel10.add(txtDireccion);

        jLabel10.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel10.setText("CIUDAD:");
        jPanel10.add(jLabel10);

        txtCiudad.setEditable(false);
        txtCiudad.setBackground(new java.awt.Color(255, 102, 0));
        txtCiudad.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        txtCiudad.setForeground(new java.awt.Color(255, 255, 255));
        txtCiudad.setBorder(null);
        jPanel10.add(txtCiudad);

        jLabel11.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel11.setText("CONDICION:");
        jPanel10.add(jLabel11);

        txtCondicion.setEditable(false);
        txtCondicion.setBackground(new java.awt.Color(255, 102, 0));
        txtCondicion.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        txtCondicion.setForeground(new java.awt.Color(255, 255, 255));
        txtCondicion.setBorder(null);
        jPanel10.add(txtCondicion);

        jPanel9.add(jPanel10, java.awt.BorderLayout.PAGE_START);

        jPanel11.setBackground(new java.awt.Color(255, 255, 255));
        jPanel11.setLayout(new java.awt.BorderLayout());

        jPanel12.setBackground(new java.awt.Color(255, 255, 255));

        jPanel14.setBackground(new java.awt.Color(255, 255, 255));
        jPanel14.setLayout(new javax.swing.BoxLayout(jPanel14, javax.swing.BoxLayout.Y_AXIS));

        jLabel12.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setText("             CANTIDAD             ");
        jPanel14.add(jLabel12);

        txtCantidad.setBackground(new java.awt.Color(255, 255, 255));
        txtCantidad.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtCantidad.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCantidad.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        txtCantidad.setNextFocusableComponent(txtArticulo);
        txtCantidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCantidadActionPerformed(evt);
            }
        });
        txtCantidad.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCantidadKeyTyped(evt);
            }
        });
        jPanel14.add(txtCantidad);

        jPanel12.add(jPanel14);

        jLabel14.setText("        ");
        jPanel12.add(jLabel14);

        jPanel15.setBackground(new java.awt.Color(255, 255, 255));
        jPanel15.setLayout(new javax.swing.BoxLayout(jPanel15, javax.swing.BoxLayout.Y_AXIS));

        jLabel13.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setText("                                                                      ARTICULO                                                             ");
        jPanel15.add(jLabel13);

        jScrollPane1.setBorder(null);

        txtArticulo.setBackground(new java.awt.Color(255, 255, 255));
        txtArticulo.setColumns(20);
        txtArticulo.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtArticulo.setLineWrap(true);
        txtArticulo.setRows(5);
        txtArticulo.setWrapStyleWord(true);
        txtArticulo.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(204, 204, 204)));
        txtArticulo.setNextFocusableComponent(btnAgregar);
        jScrollPane1.setViewportView(txtArticulo);

        jPanel15.add(jScrollPane1);

        jPanel12.add(jPanel15);

        jLabel15.setText("        ");
        jPanel12.add(jLabel15);

        btnAgregar.setBackground(new java.awt.Color(0, 102, 255));
        btnAgregar.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        btnAgregar.setForeground(new java.awt.Color(255, 255, 255));
        btnAgregar.setText("AGREGAR");
        btnAgregar.setNextFocusableComponent(txtCantidad);
        btnAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarActionPerformed(evt);
            }
        });
        jPanel12.add(btnAgregar);

        jPanel11.add(jPanel12, java.awt.BorderLayout.NORTH);

        jPanel13.setBackground(new java.awt.Color(255, 255, 255));
        jPanel13.setLayout(new java.awt.BorderLayout());

        jScrollPane2.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane2.setBorder(null);

        Tabla1.setBackground(new java.awt.Color(255, 255, 255));
        Tabla1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "CANTIDAD", "ARTICULO", "ENTREGADO", "RECIBIDO"
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
        jScrollPane2.setViewportView(Tabla1);

        jPanel13.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        jPanel11.add(jPanel13, java.awt.BorderLayout.CENTER);

        jPanel9.add(jPanel11, java.awt.BorderLayout.CENTER);

        jPanel7.add(jPanel9, java.awt.BorderLayout.CENTER);

        jPanel5.add(jPanel7, java.awt.BorderLayout.CENTER);

        jPanel1.add(jPanel5, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        jPanel16.setBackground(new java.awt.Color(255, 255, 255));

        jButton2.setBackground(new java.awt.Color(255, 255, 255));
        jButton2.setFont(new java.awt.Font("Roboto", 1, 16)); // NOI18N
        jButton2.setForeground(new java.awt.Color(0, 0, 0));
        jButton2.setText("GUARDAR REMISION");
        jButton2.setBorder(null);
        jButton2.setBorderPainted(false);
        jButton2.setContentAreaFilled(false);
        jButton2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton2.setFocusPainted(false);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel16.add(jButton2);

        getContentPane().add(jPanel16, java.awt.BorderLayout.SOUTH);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void lblSalirMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSalirMouseEntered
        panelSalir.setBackground(Color.red);
        lblSalir.setForeground(Color.white);
    }//GEN-LAST:event_lblSalirMouseEntered

    private void lblSalirMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSalirMouseExited
        panelSalir.setBackground(Color.white);
        lblSalir.setForeground(Color.black);
    }//GEN-LAST:event_lblSalirMouseExited

    private void lblSalirMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSalirMouseClicked
        dispose();
    }//GEN-LAST:event_lblSalirMouseClicked

    private void btnNuevaRemisionMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnNuevaRemisionMouseEntered
        btnNuevaRemision.setForeground(new Color(255,102,0));
    }//GEN-LAST:event_btnNuevaRemisionMouseEntered

    private void btnAbrirRemisionMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAbrirRemisionMouseEntered
        btnAbrirRemision.setForeground(new Color(255,102,0));
    }//GEN-LAST:event_btnAbrirRemisionMouseEntered

    private void btnNuevoClienteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnNuevoClienteMouseClicked
        
    }//GEN-LAST:event_btnNuevoClienteMouseClicked

    private void btnNuevaRemisionMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnNuevaRemisionMouseExited
        btnNuevaRemision.setForeground(Color.black);
    }//GEN-LAST:event_btnNuevaRemisionMouseExited

    private void btnAbrirRemisionMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAbrirRemisionMouseExited
        btnAbrirRemision.setForeground(Color.black);
    }//GEN-LAST:event_btnAbrirRemisionMouseExited

    private void btnNuevoClienteMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnNuevoClienteMouseEntered
        btnNuevoCliente.setForeground(new Color(255,102,0));
    }//GEN-LAST:event_btnNuevoClienteMouseEntered

    private void btnNuevoClienteMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnNuevoClienteMouseExited
        btnNuevoCliente.setForeground(Color.black);
    }//GEN-LAST:event_btnNuevoClienteMouseExited

    private void btnNuevoClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoClienteActionPerformed
        JFrame f = (JFrame) JOptionPane.getFrameForComponent(this);
        cliente = new VentanaEmergente.Remisiones.Cliente(f, true,this);
        cliente.setPreferredSize(new Dimension(500,400));
        cliente.btnGuardar.addActionListener(this);
        cliente.setVisible(true);
    }//GEN-LAST:event_btnNuevoClienteActionPerformed

    private void txtClienteFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtClienteFocusGained
        if(txtCliente.getText().equals("                                                                                                                         ")){
           txtCliente.setText(""); 
        }
        
    }//GEN-LAST:event_txtClienteFocusGained

    private void txtClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtClienteActionPerformed
        try{
            Connection con = null;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            String sql = "select * from registroclientes where Nombre like '"+txtCliente.getText()+"'";
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
                txtCli.setText(rs.getString("Nombre"));
                txtDireccion.setText(rs.getString("Direccion"));
                txtCiudad.setText(rs.getString("Ciudad"));
                txtCondicion.setText(rs.getString("Condicion"));
                idCliente = rs.getString("Id");
            }
            
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "ERROR: "+e,"ERROR",JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_txtClienteActionPerformed

    private void btnAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarActionPerformed
        if(txtCantidad.getText().equals("")){
            JOptionPane.showMessageDialog(this, "DEBES LLENAR LA CANTIDAD","ADVERTENCIA",JOptionPane.WARNING_MESSAGE);
        }else if(txtArticulo.getText().equals("")){
            JOptionPane.showMessageDialog(this, "DEBES PONER UN ARTICULO","ADVERTENCIA",JOptionPane.WARNING_MESSAGE);
            }else{
                String datos[] = new String[10];
                datos[0] = txtCantidad.getText();
                datos[1] = txtArticulo.getText();
                DefaultTableModel miModelo = (DefaultTableModel) Tabla1.getModel();
                miModelo.addRow(datos);
                limpiarDatos();
        }
    }//GEN-LAST:event_btnAgregarActionPerformed

    private void txtCantidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCantidadActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCantidadActionPerformed

    private void btnBorrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBorrarActionPerformed
        eliminarFilas();
    }//GEN-LAST:event_btnBorrarActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        Date fec = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String fecha = sdf.format(fec);
        Date fe = null;
        try {
            fe = sdf.parse(fecha);
        } catch (ParseException ex) {
            Logger.getLogger(OrdenDeCompra.class.getName()).log(Level.SEVERE, null, ex);
        }

        java.sql.Date data = new java.sql.Date(fe.getTime());
        if(numeroRemision.equals("")){
            if(txtCli.getText().equals("")){
                JOptionPane.showMessageDialog(this, "DEBES SELECCIONAR UN CLIENTE","ADVERTENCIA",JOptionPane.WARNING_MESSAGE);
            }else if(Tabla1.getRowCount() < 0){
                JOptionPane.showMessageDialog(this, "DEBES AGREGAR POR LO MENOS UN ARTICULO","ADVERTENCIA",JOptionPane.WARNING_MESSAGE);
            }else if(txtProyecto.getText().equals("")){
                JOptionPane.showMessageDialog(this, "DEBES AGREGAR UN PROYECTO","ADVERTENCIA",JOptionPane.WARNING_MESSAGE);
            }else{
                try{
                    Connection con;
                    Conexion con1 = new Conexion();
                    con = con1.getConnection();
                    Statement st = con.createStatement();
                    String sql = "insert into remision (Cliente, NumEmpleado, NumRemision, Fecha, Estado, Proyecto) values(?,?,?,?,?,?)";
                    PreparedStatement pst = con.prepareStatement(sql);
                    String sql2 = "select * from remision";
                    ResultSet rs2 = st.executeQuery(sql2);
                    int remi = 0;
                    while(rs2.next()){
                        remi = rs2.getInt("NumRemision");
                    }
                    remi++;

                    pst.setString(1, idCliente);
                    pst.setString(2, numEmpleado);
                    pst.setInt(3, remi);
                    pst.setDate(4, data);
                    pst.setString(5, "");
                    pst.setString(6, txtProyecto.getText());
                    int n = pst.executeUpdate();
                    int n2 = 0;
                    for (int i = 0; i < Tabla1.getRowCount(); i++) {
                        String sql3 = "insert into remisiones (NumRemision, Cantidad, Articulo, Fecha, Estado) values(?,?,?,?,?)";
                        PreparedStatement pst3 = con.prepareStatement(sql3);

                        pst3.setInt(1, remi);
                        pst3.setInt(2, Integer.parseInt(Tabla1.getValueAt(i, 0).toString()));
                        pst3.setString(3, Tabla1.getValueAt(i, 1).toString());
                        pst3.setDate(4, data);
                        pst3.setString(5, "");

                        n2 = pst3.executeUpdate();
                    }

                    if(n > 0 && n2 > 0)
                    {
                        crearPdf(remi+"");
                        JOptionPane.showMessageDialog(this, "DATOS GUARDADOS");
                        limpiarPantalla();
                        txtCliente.setEditable(true);
                    }

                }catch(SQLException e){
                    JOptionPane.showMessageDialog(this, "ERROR: "+e,"ERROR",JOptionPane.ERROR_MESSAGE);
                }
            }
        }else{
            try{
                Connection con;
                Conexion con1 = new Conexion();
                con = con1.getConnection();
                String sql = "delete from remisiones where NumRemision like '"+numeroRemision+"'";
                PreparedStatement pst = con.prepareStatement(sql);
                pst.executeUpdate();
                int n2 =0;
                for (int i = 0; i < Tabla1.getRowCount(); i++) 
                {
                    String sql2 = "insert into remisiones (NumRemision, Cantidad, Articulo, Fecha, Estado) values(?,?,?,?,?)";
                    PreparedStatement pst2 = con.prepareStatement(sql2);

                    pst2.setInt(1, Integer.parseInt(numeroRemision));
                    pst2.setInt(2, Integer.parseInt(Tabla1.getValueAt(i, 0).toString()));
                    pst2.setString(3, Tabla1.getValueAt(i, 1).toString());
                    pst2.setDate(4, data);
                    pst2.setString(5, "");

                    n2 = pst2.executeUpdate();
                    
                }
                if(n2 > 0)
                {
                    JOptionPane.showMessageDialog(this, "DATOS GUARDADOS");
                    crearPdf(numeroRemision);
                    limpiarPantalla();
                    numeroRemision = "";
                    txtCliente.setEditable(true);
                }
            }catch(SQLException e){
                JOptionPane.showMessageDialog(this,"ERROR: "+e,"ERROR",JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void txtCantidadKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCantidadKeyTyped
        char c = evt.getKeyChar();
        if(c<'0'||c>'9') evt.consume();
    }//GEN-LAST:event_txtCantidadKeyTyped

    private void btnNuevaRemisionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevaRemisionActionPerformed
        limpiarPantalla();
        txtCliente.setEditable(true);
        numeroRemision = "";
        txtProyecto.setEditable(true);
        auP = null;
        autocompletarProyecto();
    }//GEN-LAST:event_btnNuevaRemisionActionPerformed

    private void btnAbrirRemisionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAbrirRemisionActionPerformed
        txtCliente.setEditable(false);
        String remi = JOptionPane.showInputDialog(this, "INGRESA NUMERO DE REMISION");
        boolean band = false;
        String cliente = "";
        auP.removeAllItems();
        try{
            Connection con;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            String sql = "select * from remision where NumRemision like '"+remi+"'";
            ResultSet rs = st.executeQuery(sql);
            String datos[] = new String[10];
            while(rs.next()){
                datos[0] = rs.getString("NumRemision");
                datos[1] = rs.getString("Cliente");
                
                txtProyecto.setText(rs.getString("Proyecto"));
                if(datos[0].equals(remi)){
                    band = true;
                    cliente = datos[1];
                }
            }
            if(band){
                String sql2 = "select * from registroClientes where Id like '"+cliente+"'";
                Statement st2 = con.createStatement();
                ResultSet rs2 = st2.executeQuery(sql2);
                while(rs2.next()){
                    txtCli.setText(rs2.getString("Nombre"));
                    txtDireccion.setText(rs2.getString("Direccion"));
                    txtCiudad.setText(rs2.getString("Ciudad"));
                    txtCondicion.setText(rs2.getString("Condicion"));
                    
                    
                }
                
                txtProyecto.setEditable(false);
                txtProyecto.setForeground(Color.blue);                
                
                String sql3 = "select * from remisiones where NumRemision like '"+remi+"'";
                numeroRemision = remi;
                Statement st3 = con.createStatement();
                ResultSet rs3 = st3.executeQuery(sql3);
                DefaultTableModel miModelo = (DefaultTableModel) Tabla1.getModel();
                while(rs3.next()){
                    datos[0] = rs3.getString("Cantidad");
                    datos[1] = rs3.getString("Articulo");
                    miModelo.addRow(datos);
                }
            }else{
                JOptionPane.showMessageDialog(this, "ESTA REMISION NO EXISTE","ADVERTENCIA",JOptionPane.WARNING_MESSAGE);
                limpiarPantalla();
                txtCliente.setEditable(true);
                autocompletarProyecto();
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "ERROR: "+e,"ERROR",JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnAbrirRemisionActionPerformed

    private void txtProyectoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtProyectoActionPerformed
        try{
            Connection con;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            String sql = "select Proyecto from Proyectos where Proyecto like '"+txtProyecto.getText()+"'";
            ResultSet rs = st.executeQuery(sql);
            String datos[] = new String[10];
            while(rs.next()){
                datos[0] = rs.getString("Proyecto");
            }
            if(txtProyecto.getText().equals(datos[0]))
            {
                txtProyecto.setEditable(false);
                txtProyecto.setFont(new Font("Roboto",Font.BOLD,14));
                txtProyecto.setForeground(Color.blue);
                btnQuitarProyecto.setVisible(true);
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "ERROR: "+e,"ERROR",JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_txtProyectoActionPerformed

    private void btnQuitarProyectoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQuitarProyectoActionPerformed
        txtProyecto.setEditable(true);
        txtProyecto.setForeground(Color.black);
        btnQuitarProyecto.setVisible(false);
        txtProyecto.setText("");
    }//GEN-LAST:event_btnQuitarProyectoActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable Tabla1;
    private javax.swing.JButton btnAbrirRemision;
    private javax.swing.JButton btnAgregar;
    private javax.swing.JMenuItem btnBorrar;
    private javax.swing.JButton btnNuevaRemision;
    private javax.swing.JButton btnNuevoCliente;
    private javax.swing.JButton btnQuitarProyecto;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblSalir;
    private javax.swing.JPanel pan;
    private javax.swing.JPanel panelSalir;
    private javax.swing.JTextArea txtArticulo;
    private javax.swing.JTextField txtCantidad;
    private javax.swing.JTextField txtCiudad;
    private javax.swing.JTextField txtCli;
    private javax.swing.JTextField txtCliente;
    private javax.swing.JTextField txtCondicion;
    private javax.swing.JTextField txtDireccion;
    private javax.swing.JTextField txtProyecto;
    // End of variables declaration//GEN-END:variables

    @Override
    public void actionPerformed(ActionEvent e) {
        
    }
}
