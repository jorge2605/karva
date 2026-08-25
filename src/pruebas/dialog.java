package pruebas;

import Conexiones.Conexion;
import VentanaEmergente.Ventas.verDocumentos;
import VentanaEmergente.Ventas.ColorVentas;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;


public class dialog extends java.awt.Dialog implements ActionListener {
    TableRowSorter<TableModel> elQueOrdena;
    verDocumentos verDoc;
    byte[] spec = null;
    byte[] coti = null;
    byte[] oc = null;
    byte[] fac = null;
    JFileChooser seleccionar;
    JFileChooser selec;
    JFileChooser sel;
    JFileChooser sele;
    File espe = null;
    File cotizacion = null;
    File orden = null;
    File factura = null;
            
public void descargar(byte[] byt){
            try{
            byte[] b = byt;
            
            InputStream bos = new ByteArrayInputStream(b);
            
            int tamInput = bos.available();
            byte[] datosPdf = new byte[tamInput];
            bos.read(datosPdf, 0, tamInput);
            
            JFileChooser fc = new JFileChooser();
            File archivo = null;
            fc.setFileFilter(new FileNameExtensionFilter("Pdf (*.pdf)", "pdf"));
            int n = fc.showSaveDialog(this);

            if(n == JFileChooser.APPROVE_OPTION){
            archivo = fc.getSelectedFile();
            }
            String a = ""+archivo;
            if(a.endsWith("pdf")){
            }else {
            a = archivo + ".pdf";
            }
            
            OutputStream out = new FileOutputStream(a);
            out.write(datosPdf);
            
            out.close();
            bos.close();
            
             Desktop.getDesktop().open(new File(a));
                    
            
        }catch(NumberFormatException  |IOException e){
            JOptionPane.showMessageDialog(this,"ERROR: "+e,"ERROR",JOptionPane.ERROR_MESSAGE);
        }
}

public void buscar(){
        DefaultTableModel miModelo = (DefaultTableModel) Tabla1.getModel();
        try{
            Connection con = null;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            String sql = "select * from proyectos order by Id desc";
            ResultSet rs = st.executeQuery(sql);
            String datos[] = new String[15];
            while(rs.next()){
                datos[0] = rs.getString("Id");
                datos[1] = rs.getString("NumCotizacion");
                datos[2] = rs.getString("OC");
                datos[3] = rs.getString("Proyecto");
                datos[4] = rs.getString("Descripcion");
                datos[5] = rs.getString("FechaCreacion");
                datos[6] = rs.getString("Planta");
                datos[7] = rs.getString("FechaEntrega");
                datos[8] = rs.getString("Estatus");
                datos[9] = rs.getString("Facturado");
//                datos[8] = rs.getString("Spec");
                miModelo.addRow(datos);
                }
            }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "ERROR: "+e,"ERROR",JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void limpiarTabla(){
        Tabla1.setFont(new java.awt.Font("Roboto", 0, 12));
        Tabla1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
            },
            new String [] {
                "NO REQUISICION", "NO COTIZACION", "ORDEN COMPRA", "PROYECTO", 
                "DESCRIPCION", "FECHA", "PLANTA", "FECHA COMPROMISO", "ESTATUS", "FACTURADO"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        Tabla1.setGridColor(new java.awt.Color(255, 255, 255));
        Tabla1.setRowHeight(25);
        Tabla1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Tabla1MouseClicked(evt);
            }
        });
    }

    public dialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        limpiarTabla();
        buscar();
        DefaultTableModel Modelo = (DefaultTableModel) Tabla1.getModel();
        elQueOrdena = new TableRowSorter<>(Modelo);
        Tabla1.setRowSorter(elQueOrdena);
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        Tabla1 = new ColorVentas();
        jLabel2 = new javax.swing.JLabel();
        txtRequisicion = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel3 = new javax.swing.JLabel();
        txtCotizacion = new javax.swing.JTextField();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtDescripcion = new javax.swing.JTextArea();
        jLabel5 = new javax.swing.JLabel();
        txtOc = new javax.swing.JTextField();
        jSeparator3 = new javax.swing.JSeparator();
        jLabel6 = new javax.swing.JLabel();
        btnModificar = new javax.swing.JButton();
        txtProyecto = new javax.swing.JTextField();
        jSeparator4 = new javax.swing.JSeparator();
        jLabel7 = new javax.swing.JLabel();
        lblDescarga = new javax.swing.JButton();
        panel1 = new javax.swing.JPanel();
        panel2 = new javax.swing.JPanel();
        panel3 = new javax.swing.JPanel();
        panel4 = new javax.swing.JPanel();

        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                closeDialog(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(0, 165, 252));

        jLabel1.setFont(new java.awt.Font("Roboto Light", 0, 60)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("VENTAS");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 580, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(21, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(15, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(247, 6, -1, -1));

        Tabla1.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        Tabla1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "NO REQUISICION", "NO COTIZACION", "ORDEN COMPRA", "PROYECTO", "DESCRIPCION", "FECHA", "PLANTA", "FECHA COMPROMISO", "ESTATUS", "FACTURADO"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        Tabla1.setGridColor(new java.awt.Color(255, 255, 255));
        Tabla1.setRowHeight(25);
        Tabla1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Tabla1MouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(Tabla1);

        jPanel1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 100, 1340, 327));

        jLabel2.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        jLabel2.setText("REQUISICION");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 464, 90, -1));

        txtRequisicion.setEditable(false);
        txtRequisicion.setBackground(new java.awt.Color(255, 255, 255));
        txtRequisicion.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtRequisicion.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtRequisicion.setBorder(null);
        jPanel1.add(txtRequisicion, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 487, 90, 20));
        jPanel1.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 510, 90, 12));

        jLabel3.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        jLabel3.setText("DOCUMENTOS");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(1180, 460, -1, -1));

        txtCotizacion.setBackground(new java.awt.Color(255, 255, 255));
        txtCotizacion.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtCotizacion.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCotizacion.setBorder(null);
        jPanel1.add(txtCotizacion, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 487, 100, 20));
        jPanel1.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 510, 100, 12));

        jLabel4.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        jLabel4.setText("COTIZACION");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(115, 464, 90, -1));

        txtDescripcion.setBackground(new java.awt.Color(255, 255, 255));
        txtDescripcion.setColumns(20);
        txtDescripcion.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtDescripcion.setLineWrap(true);
        txtDescripcion.setRows(5);
        txtDescripcion.setWrapStyleWord(true);
        jScrollPane1.setViewportView(txtDescripcion);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 490, 660, -1));

        jLabel5.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        jLabel5.setText("OC");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 464, 20, -1));

        txtOc.setBackground(new java.awt.Color(255, 255, 255));
        txtOc.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtOc.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtOc.setBorder(null);
        jPanel1.add(txtOc, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 487, 100, 20));
        jPanel1.add(jSeparator3, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 510, 100, 12));

        jLabel6.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        jLabel6.setText("PROYECTO");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 464, 80, -1));

        btnModificar.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        btnModificar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ImgAnimacion/editar_32.png"))); // NOI18N
        btnModificar.setBorder(null);
        btnModificar.setBorderPainted(false);
        btnModificar.setContentAreaFilled(false);
        btnModificar.setFocusPainted(false);
        btnModificar.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnModificar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnModificar.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/ImgAnimacion/editar_32.png"))); // NOI18N
        btnModificar.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/ImgAnimacion/editar_48.png"))); // NOI18N
        btnModificar.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        btnModificar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarActionPerformed(evt);
            }
        });
        jPanel1.add(btnModificar, new org.netbeans.lib.awtextra.AbsoluteConstraints(1280, 470, 70, 60));

        txtProyecto.setEditable(false);
        txtProyecto.setBackground(new java.awt.Color(255, 255, 255));
        txtProyecto.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtProyecto.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtProyecto.setBorder(null);
        jPanel1.add(txtProyecto, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 487, 100, 20));
        jPanel1.add(jSeparator4, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 510, 100, 12));

        jLabel7.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        jLabel7.setText("DESCRIPCION");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 464, -1, -1));

        lblDescarga.setBackground(new java.awt.Color(255, 255, 255));
        lblDescarga.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        lblDescarga.setForeground(new java.awt.Color(0, 153, 255));
        lblDescarga.setText("VER DOCUMENTOS");
        lblDescarga.setBorder(null);
        lblDescarga.setBorderPainted(false);
        lblDescarga.setContentAreaFilled(false);
        lblDescarga.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblDescarga.setEnabled(false);
        lblDescarga.setFocusPainted(false);
        lblDescarga.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblDescargaMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblDescargaMouseExited(evt);
            }
        });
        lblDescarga.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lblDescargaActionPerformed(evt);
            }
        });
        jPanel1.add(lblDescarga, new org.netbeans.lib.awtextra.AbsoluteConstraints(1170, 490, 110, 20));

        javax.swing.GroupLayout panel1Layout = new javax.swing.GroupLayout(panel1);
        panel1.setLayout(panel1Layout);
        panel1Layout.setHorizontalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );
        panel1Layout.setVerticalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );

        jPanel1.add(panel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(1180, 520, 10, 10));

        javax.swing.GroupLayout panel2Layout = new javax.swing.GroupLayout(panel2);
        panel2.setLayout(panel2Layout);
        panel2Layout.setHorizontalGroup(
            panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );
        panel2Layout.setVerticalGroup(
            panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );

        jPanel1.add(panel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(1200, 520, -1, -1));

        javax.swing.GroupLayout panel3Layout = new javax.swing.GroupLayout(panel3);
        panel3.setLayout(panel3Layout);
        panel3Layout.setHorizontalGroup(
            panel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );
        panel3Layout.setVerticalGroup(
            panel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );

        jPanel1.add(panel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(1220, 520, -1, -1));

        javax.swing.GroupLayout panel4Layout = new javax.swing.GroupLayout(panel4);
        panel4.setLayout(panel4Layout);
        panel4Layout.setHorizontalGroup(
            panel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );
        panel4Layout.setVerticalGroup(
            panel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 10, Short.MAX_VALUE)
        );

        jPanel1.add(panel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(1240, 520, -1, -1));

        add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

   
    private void closeDialog(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_closeDialog
        setVisible(false);
        dispose();
    }//GEN-LAST:event_closeDialog

    private void Tabla1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Tabla1MouseClicked
        int fila = Tabla1.getSelectedRow();
        String re,coti, oc,pro,des,est,fac;
        if(Tabla1.getValueAt(fila, 0) == null){
            re = "";
        }else{
            re = Tabla1.getValueAt(fila, 0).toString();
        }
        
        if(Tabla1.getValueAt(fila, 1) == null){
            coti = "";
        }else{
            coti = Tabla1.getValueAt(fila, 1).toString();
        }
        
        if(Tabla1.getValueAt(fila, 2) == null){
            oc = "";
        }else{
            oc = Tabla1.getValueAt(fila, 2).toString();
        }
        
        if(Tabla1.getValueAt(fila, 3) == null){
            pro = "";
        }else{
            pro = Tabla1.getValueAt(fila, 3).toString();
        }
        
        if(Tabla1.getValueAt(fila, 4) == null){
            des = "";
        }else{
            des = Tabla1.getValueAt(fila, 4).toString();
        }
        
        if(Tabla1.getValueAt(fila, 8) == null){
            est = "";
        }else{
            est = Tabla1.getValueAt(fila, 8).toString();
        }
        
        if(Tabla1.getValueAt(fila, 9) == null){
            fac = "";
        }else{
            fac = Tabla1.getValueAt(fila, 9).toString();
        }
        
        spec = null;
        this.coti = null;
        this.oc = null;
        this.fac = null;
        espe = null;
        cotizacion = null;
        orden = null;
        factura = null;
        
        lblDescarga.setEnabled(true);
        txtRequisicion.setText(re);
        txtCotizacion.setText(coti);
        txtOc.setText(oc);
        txtProyecto.setText(pro);
        txtDescripcion.setText(des);
    }//GEN-LAST:event_Tabla1MouseClicked

    private void btnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarActionPerformed
        if(txtRequisicion.getText().equals("")){
            JOptionPane.showMessageDialog(this, "DEBES SELECCIONAR UN ELEMENTO DE LA LISTA");
        }else{
        try{
            Connection con = null;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            String sql = "update proyectos set NumCotizacion = ?, OC = ?, Proyecto = ?, Descripcion = ? where Id = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            
            pst.setString(1, txtCotizacion.getText());
            pst.setString(2, txtOc.getText());
            pst.setString(3, txtProyecto.getText());
            pst.setString(4, txtDescripcion.getText());
            pst.setString(5, txtRequisicion.getText());
            
            
            int n1 = 0, n2 = 0, n3 = 0, n4 = 0;
            if(cotizacion != null){
                String sql1 = "update proyectos set CotizacionPdf = ? where Id = ?";
                PreparedStatement pst1 = con.prepareStatement(sql1);
                
                byte[] pe = null;
                if(cotizacion == null){
                }else{
                pe = new byte[(int) cotizacion.length()];
                try{
                InputStream input = new FileInputStream(cotizacion);
                input.read(pe);
                }catch(IOException e){
                    System.out.println("Error archivo: "+e);
                }
                }
                
                pst1.setBytes(1, pe);
                pst1.setString(2, txtRequisicion.getText());
                n1 = pst1.executeUpdate();
            }else{
                n1 = 1;
            }
            
            if(orden != null){
                String sql1 = "update proyectos set OcPdf = ? where Id = ?";
                PreparedStatement pst1 = con.prepareStatement(sql1);
                
                byte[] pe = null;
                if(orden == null){
                }else{
                pe = new byte[(int) orden.length()];
                try{
                InputStream input = new FileInputStream(orden);
                input.read(pe);
                }catch(IOException e){
                    System.out.println("Error archivo: "+e);
                }
                }
                
                pst1.setBytes(1, pe);
                pst1.setString(2, txtRequisicion.getText());
                n2 = pst1.executeUpdate();
                
            }else{
                n2 = 1;
            }
            
            if(espe != null){
                String sql1 = "update proyectos set Spec = ? where Id = ?";
                PreparedStatement pst1 = con.prepareStatement(sql1);
                
                byte[] pe = null;
                if(espe == null){
                }else{
                pe = new byte[(int) espe.length()];
                try{
                InputStream input = new FileInputStream(espe);
                input.read(pe);
                }catch(IOException e){
                    System.out.println("Error archivo: "+e);
                }
                }
                
                pst1.setBytes(1, pe);
                pst1.setString(2, txtRequisicion.getText());
                n3 = pst1.executeUpdate();
                
            }else{
                n3 = 1;
            }
            
            if(factura != null){
                String sql1 = "update proyectos set Factura = ? where Id = ?";
                PreparedStatement pst1 = con.prepareStatement(sql1);
                
                byte[] pe = null;
                if(factura == null){
                }else{
                pe = new byte[(int) factura.length()];
                try{
                InputStream input = new FileInputStream(factura);
                input.read(pe);
                }catch(IOException e){
                    System.out.println("Error archivo: "+e);
                }
                }
                
                pst1.setBytes(1, pe);
                pst1.setString(2, txtRequisicion.getText());
                n4 = pst1.executeUpdate();
                
            }else{
                n4 = 1;
            }
            
            int n = pst.executeUpdate();
            
            if(n > 0 && n1 > 0 && n2 > 0 && n3 > 0 && n4 > 0){
                JOptionPane.showMessageDialog(null, "DATOS GUARDADOS");
                limpiarTabla();
                buscar();
                
                spec = null;
                this.coti = null;
                this.oc = null;
                this.fac = null;
                espe = null;
                cotizacion = null;
                orden = null;
                this.factura = null;
                panel1.setBackground(Color.LIGHT_GRAY);
                panel2.setBackground(Color.LIGHT_GRAY);
                panel3.setBackground(Color.LIGHT_GRAY);
                panel4.setBackground(Color.LIGHT_GRAY);
            }else{
                JOptionPane.showMessageDialog(this, "NO SE GUARDARON LOS DATOS","ERROR",JOptionPane.ERROR_MESSAGE);
            }
            
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "ERROR: "+e,"ERROR",JOptionPane.ERROR_MESSAGE);
        }
        }
    }//GEN-LAST:event_btnModificarActionPerformed

    private void lblDescargaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblDescargaMouseEntered
        if(lblDescarga.isEnabled())
        lblDescarga.setForeground(new java.awt.Color(0, 0, 204));
    }//GEN-LAST:event_lblDescargaMouseEntered

    private void lblDescargaMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblDescargaMouseExited
        if(lblDescarga.isEnabled())
        lblDescarga.setForeground(new java.awt.Color(0, 153, 255));
    }//GEN-LAST:event_lblDescargaMouseExited

    private void lblDescargaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lblDescargaActionPerformed
        JFrame f = (JFrame) JOptionPane.getFrameForComponent(this);
        verDoc = new verDocumentos(f,true,txtProyecto.getText());
        verDoc.btnSubirCotizacion.setEnabled(false);
        verDoc.btnSubirFactura.setEnabled(false);
        verDoc.btnSubirPO.setEnabled(false);
        verDoc.btnSubirSpec.setEnabled(false);
        verDoc.setVisible(true);
    }//GEN-LAST:event_lblDescargaActionPerformed

    
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                dialog dialog = new dialog(new java.awt.Frame(), true);
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
    private javax.swing.JTable Tabla1;
    public javax.swing.JButton btnModificar;
    private javax.swing.JLabel jLabel1;
    public javax.swing.JLabel jLabel2;
    public javax.swing.JLabel jLabel3;
    public javax.swing.JLabel jLabel4;
    public javax.swing.JLabel jLabel5;
    public javax.swing.JLabel jLabel6;
    public javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    public javax.swing.JSeparator jSeparator1;
    public javax.swing.JSeparator jSeparator2;
    public javax.swing.JSeparator jSeparator3;
    public javax.swing.JSeparator jSeparator4;
    private javax.swing.JButton lblDescarga;
    private javax.swing.JPanel panel1;
    private javax.swing.JPanel panel2;
    private javax.swing.JPanel panel3;
    private javax.swing.JPanel panel4;
    public javax.swing.JTextField txtCotizacion;
    public javax.swing.JTextArea txtDescripcion;
    public javax.swing.JTextField txtOc;
    public javax.swing.JTextField txtProyecto;
    public javax.swing.JTextField txtRequisicion;
    // End of variables declaration//GEN-END:variables

    @Override
    public void actionPerformed(ActionEvent e) {
    }
}
