package pruebas;

import Conexiones.Conexion;
import VO.ArchivosVO;
import VentanaEmergente.CalidadNew.Numeros;
import VentanaEmergente.CalidadNew.Seleccionar;
import VentanaEmergente.Inicio1.Espera;
import java.awt.Color;
import java.awt.Font;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import mivisorpdf.MiVisorPDF;

public class Ensamble extends javax.swing.JInternalFrame {

    public String proyecto;
    String ensamble;
    Espera espera;
    
    private ArrayList<ArchivosVO> ListaComponente;
    MiVisorPDF pn = new MiVisorPDF();
    ArchivosVO pl = new ArchivosVO();
    private int numImg;

    private int paginas = -1;
    private int totalp = -1;

    private String ruta_archivo = "";

    int zoom = 0;
    
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
    
    public void verPlano(){
        byte[] ruta = null;
        
        limpiarPantalla();
        acomodar();
        try{
            Connection con = null;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            String sql = "select Pdf from planos where Plano like '"+TablaPlanos.getValueAt(TablaPlanos.getSelectedRow(), 0)+"'";
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
                ruta = rs.getBytes("Pdf");
            }
            
            if(ruta == null){
                JOptionPane.showMessageDialog(this, "NO EXISTE ESTE PLANO","ADVERTENCIA",JOptionPane.WARNING_MESSAGE);
            }else{
            File n = new File("C:\\Pruebas\\BD\\img\\new.pdf");
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
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "ERROR: "+e,"ERROR",JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void buscar(){
        espera = new Espera();
        espera.setVisible(true);
        DefaultTableModel miModelo = (DefaultTableModel) TablaPlanos.getModel();
        
        try{
        int fila = TablaEnsamble.getSelectedRow();
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
        String datos[] = new String[10];
        String sql = "select Plano from Planos where NoEnsamble like '"+(TablaEnsamble.getValueAt(fila, 0).toString())+"' and Proyecto like '"+proyecto+"'";
        ResultSet rs = st7.executeQuery(sql);
        while(rs.next())
        {
        int cont = 0;
        datos[0] = rs.getString("Plano");
        String acabados[] = new String[10];
            String calidad[] = new String[10];
            String cnc[] = new String [10];
            String fresa[] = new String [10];
            String cortes[] = new String [10];
            String torno[] = new String [10];
            String trata[] = new String [10];
            String id = datos[0];
            
            String sq = "select * from Calidad where Proyecto like '%"+datos[0]+"%'";
            ResultSet r = st.executeQuery(sq);
            String sql1 = "select * from Acabados where Proyecto like '%"+datos[0]+"%'";
            ResultSet rs1 = st1.executeQuery(sql1);
            String sql2 = "select * from CNC where Proyecto like '%"+datos[0]+"%'";
            ResultSet rs2 = st2.executeQuery(sql2);
            String sql3 = "select * from Fresadora where Proyecto like '%"+datos[0]+"%'";
            ResultSet rs3 = st3.executeQuery(sql3);
            String sql5 = "select * from Torno where Proyecto like '%"+datos[0]+"%'";
            ResultSet rs5 = st4.executeQuery(sql5);
            String sql4 = "select * from Datos where Proyecto like '%"+datos[0]+"%'";
            ResultSet rs4 = st5.executeQuery(sql4);
            String sql6 = "select * from Trata where Proyecto like '%"+datos[0]+"%'";
            ResultSet rs6 = st6.executeQuery(sql6);
            while(r.next()){
                calidad[0] = r.getString("Id");
                calidad[1] = r.getString("Proyecto");
                calidad[2] = r.getString("Plano");
                calidad[3] = r.getString("Terminado");
                calidad[4] = r.getString("Tratamiento");
                calidad[5] = r.getString("Prioridad");
            }

            while(rs1.next()){
                acabados[0] = rs1.getString("Id");
                acabados[1] = rs1.getString("Proyecto");
                acabados[2] = rs1.getString("Plano");
                acabados[3] = rs1.getString("Terminado");
                acabados[5] = rs1.getString("Prioridad");
            }
            while(rs2.next()){
                cnc[0] = rs2.getString("Id");
                cnc[1] = rs2.getString("Proyecto");
                cnc[2] = rs2.getString("Plano");
                cnc[3] = rs2.getString("Terminado");
                cnc[5] = rs2.getString("Prioridad");
            }
            while(rs3.next()){
                fresa[0] = rs3.getString("Id");
                fresa[1] = rs3.getString("Proyecto");
                fresa[2] = rs3.getString("Plano");
                fresa[3] = rs3.getString("Terminado");
                fresa[5] = rs3.getString("Prioridad");
            }
            while(rs4.next()){
                cortes[0] = rs4.getString("Id");
                cortes[1] = rs4.getString("Proyecto");
                cortes[2] = rs4.getString("Plano");
                cortes[3] = rs4.getString("Terminado");
                cortes[5] = rs4.getString("Prioridad");
            }
            while(rs5.next()){
                torno[0] = rs5.getString("Id");
                torno[1] = rs5.getString("Proyecto");
                torno[2] = rs5.getString("Plano");
                torno[3] = rs5.getString("Terminado");
                torno[5] = rs5.getString("Prioridad");
            }
            while(rs6.next()){
                trata[0] = rs6.getString("Id");
                trata[1] = rs6.getString("Proyecto");
                trata[2] = rs6.getString("Plano");
                trata[3] = rs6.getString("Terminado");
                trata[5] = rs6.getString("Prioridad");
            }
            
            if(id.equals(calidad[1]) && calidad[3].equals("SI") &&"NO".equals(calidad[4])){
                datos[1] = "TERMINADO";
            }else if(id.equals(calidad[1]) && calidad[3].equals("NO")){
                datos[1] = "CALIDAD";
            }else if(id.equals(trata[1]) && trata[3].equals("TERMINADO")){
                datos[1] = "TERMINADO";
            }else if(id.equals(trata[1]) && trata[3].equals("NO")){
               datos[1] = "TRATAMIENTO";
            }
            else if(id.equals(acabados[1])){
                datos[1] = "ACABADOS";
            }else if(id.equals(cnc[1]) && cnc[3].equals("NO")){
                datos[1] = "CNC";
            }else if(id.equals(fresa[1]) && fresa[3].equals("NO")){
                datos[1] = "FRESADORA";
            }else if(id.equals(torno[1]) && torno[3].equals("NO")){
                datos[1] = "TORNO";
            }else if(id.equals(cortes[1])){
                datos[1] = "CORTES";
            }else{
                datos[1] = "LIBERACION";
            }
            
            if(datos[1].equals("LIBERACION")){
            miModelo.addRow(datos);
                }else if(datos[1].equals("CORTES")){
                miModelo.addRow(datos);
                }else if(datos[1].equals("TORNO")){
                miModelo.addRow(datos);
                    }else if(datos[1].equals("FRESADORA")){
                    miModelo.addRow(datos);
                        }else if(datos[1].equals("CNC")){
                            miModelo.addRow(datos);
                            }else if(datos[1].equals("ACABADOS")){
                                miModelo.addRow(datos);
                                }else if(datos[1].equals("CALIDAD")){
                                    miModelo.addRow(datos);
                                    }else if(datos[1].equals("TERMINADO")){
                                        miModelo.addRow(datos);
                                        }
            
        cont = cont+1;
        }
        
        }catch(SQLException e){
            espera.dispose();
        JOptionPane.showMessageDialog(this, "NO SE PUEDEN MOSTRAR LOS DATOS"+" "+e,"ERROR",JOptionPane.ERROR_MESSAGE);
        
        }
        espera.dispose();
        TableRowSorter<TableModel> elQueOrdena = new TableRowSorter<TableModel>(miModelo);
        TablaPlanos.setRowSorter(elQueOrdena);
    }
    
    public void verDatos(){
    try{
    DefaultTableModel miModelo = (DefaultTableModel) TablaProyecto.getModel();
    Connection con = null;
    Conexion con1 = new Conexion();
    con = con1.getConnection();
    Statement st = con.createStatement();
    String sql = "select Id,Proyecto, FechaEntrega from Proyectos where Estatus like 'EN PROCESO' order by Id desc";
    ResultSet rs = st.executeQuery(sql);
    String datos[] = new String[10];
    while(rs.next()){
    datos[0] = rs.getString("Proyecto");
    datos[1] = rs.getString("FechaEntrega");
    miModelo.addRow(datos);
    }
    }catch(SQLException e){
    JOptionPane.showMessageDialog(this, "ERROR AL MOSTRAR DATOS"+e,"ERROR",JOptionPane.ERROR_MESSAGE);
    }
    }
    
    public void limpiarTabla1(){
    DefaultTableModel miModelo = (DefaultTableModel) TablaProyecto.getModel();
    String titulos[] ={"PROYECTOS","FECHA ENTREGA"};
    miModelo =  new DefaultTableModel(null,titulos);
    TablaProyecto.setModel(miModelo);
    }
    public void limpiarTabla2(){
    DefaultTableModel miModelo = (DefaultTableModel) TablaEnsamble.getModel();
    String titulos[] ={"ENSAMBLE"};
    miModelo =  new DefaultTableModel(null,titulos);
    TablaEnsamble.setModel(miModelo);
    }
    public void limpiarTabla3(){
    DefaultTableModel miModelo = (DefaultTableModel) TablaPlanos.getModel();
    String titulos[] ={"PLANOS","UBICACION"};
    miModelo =  new DefaultTableModel(null,titulos);
    TablaPlanos.setModel(miModelo);
    TableRowSorter<TableModel> elQueOrdena = new TableRowSorter<TableModel>(miModelo);
    TablaPlanos.setRowSorter(elQueOrdena);
    }
    
    public Ensamble() {
        initComponents();
        limpiarTabla1();
        TablaProyecto.getTableHeader().setFont(new Font("Roboto", Font.PLAIN, 14));
        TablaProyecto.getTableHeader().setOpaque(false);
        TablaProyecto.getTableHeader().setBackground(new Color(0,0,0,0));
        TablaProyecto.getTableHeader().setForeground(Color.black);
        TablaProyecto.setRowHeight(25);
        TablaEnsamble.getTableHeader().setFont(new Font("Roboto", Font.PLAIN, 14));
        TablaEnsamble.getTableHeader().setOpaque(false);
        TablaEnsamble.getTableHeader().setBackground(new Color(0,0,0,0));
        TablaEnsamble.getTableHeader().setForeground(Color.black);
        TablaEnsamble.setRowHeight(25);
        TablaPlanos.getTableHeader().setFont(new Font("Roboto", Font.PLAIN, 14));
        TablaPlanos.getTableHeader().setOpaque(false);
        TablaPlanos.getTableHeader().setBackground(new Color(0,0,0,0));
        TablaPlanos.getTableHeader().setForeground(Color.black);
        TablaPlanos.setRowHeight(25);
        ((javax.swing.plaf.basic.BasicInternalFrameUI) this.getUI()).setNorthPane(null);
        verDatos();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jPanel13 = new javax.swing.JPanel();
        btnSalir = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        TablaProyecto = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        TablaEnsamble = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        TablaPlanos = new javax.swing.JTable();
        jPanel5 = new javax.swing.JPanel();
        img = new mivisorpdf.CuadroImagen();

        setBackground(new java.awt.Color(255, 255, 255));
        setBorder(null);

        jPanel1.setLayout(new java.awt.BorderLayout());

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new java.awt.BorderLayout());

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jLabel9.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 48)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 165, 252));
        jLabel9.setText("Ensamble");
        jPanel3.add(jLabel9);

        jPanel2.add(jPanel3, java.awt.BorderLayout.CENTER);

        jPanel13.setBackground(new java.awt.Color(255, 255, 255));

        btnSalir.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("  X  ");
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

        jPanel13.add(btnSalir);

        jPanel2.add(jPanel13, java.awt.BorderLayout.EAST);

        jPanel1.add(jPanel2, java.awt.BorderLayout.PAGE_START);

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setLayout(new java.awt.GridLayout(1, 3, 5, 0));

        TablaProyecto.setBackground(new java.awt.Color(255, 255, 255));
        TablaProyecto.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "PROYECTO ACTIVO"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        TablaProyecto.setGridColor(new java.awt.Color(255, 255, 255));
        TablaProyecto.setRowHeight(25);
        TablaProyecto.setSelectionBackground(new java.awt.Color(255, 0, 255));
        TablaProyecto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TablaProyectoMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(TablaProyecto);
        if (TablaProyecto.getColumnModel().getColumnCount() > 0) {
            TablaProyecto.getColumnModel().getColumn(0).setResizable(false);
        }

        jPanel4.add(jScrollPane1);

        TablaEnsamble.setBackground(new java.awt.Color(255, 255, 255));
        TablaEnsamble.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ENSAMBLE"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        TablaEnsamble.setGridColor(new java.awt.Color(255, 255, 255));
        TablaEnsamble.setRowHeight(25);
        TablaEnsamble.setSelectionBackground(new java.awt.Color(255, 0, 255));
        TablaEnsamble.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TablaEnsambleMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(TablaEnsamble);
        if (TablaEnsamble.getColumnModel().getColumnCount() > 0) {
            TablaEnsamble.getColumnModel().getColumn(0).setResizable(false);
        }

        jPanel4.add(jScrollPane3);

        TablaPlanos.setBackground(new java.awt.Color(255, 255, 255));
        TablaPlanos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "PLANOS", "UBICACION"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        TablaPlanos.setGridColor(new java.awt.Color(255, 255, 255));
        TablaPlanos.setRowHeight(25);
        TablaPlanos.setSelectionBackground(new java.awt.Color(255, 0, 255));
        TablaPlanos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TablaPlanosMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(TablaPlanos);
        if (TablaPlanos.getColumnModel().getColumnCount() > 0) {
            TablaPlanos.getColumnModel().getColumn(0).setResizable(false);
            TablaPlanos.getColumnModel().getColumn(1).setResizable(false);
        }

        jPanel4.add(jScrollPane2);

        jPanel1.add(jPanel4, java.awt.BorderLayout.CENTER);

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setLayout(new java.awt.BorderLayout());

        img.setBackground(new java.awt.Color(255, 255, 255));
        img.setPreferredSize(new java.awt.Dimension(210, 297));
        img.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                imgMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout imgLayout = new javax.swing.GroupLayout(img);
        img.setLayout(imgLayout);
        imgLayout.setHorizontalGroup(
            imgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 2095, Short.MAX_VALUE)
        );
        imgLayout.setVerticalGroup(
            imgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 297, Short.MAX_VALUE)
        );

        jPanel5.add(img, java.awt.BorderLayout.CENTER);

        jPanel1.add(jPanel5, java.awt.BorderLayout.SOUTH);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void TablaProyectoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TablaProyectoMouseClicked
        espera = new Espera();
        espera.setVisible(true);
        limpiarTabla2();
        boolean band = false;
        int cont = 0;
        try{
        DefaultTableModel miModelo = (DefaultTableModel) TablaEnsamble.getModel();
        Connection con = null;
        Conexion con1 = new Conexion();
        con = con1.getConnection();
        Statement st = con.createStatement();
        int fila = TablaProyecto.getSelectedRow();
        proyecto = (String) TablaProyecto.getValueAt(fila, 0);
        String sql = "select NoEnsamble from Planos where Proyecto like '"+TablaProyecto.getValueAt(fila, 0).toString()+"'";
        ResultSet rs = st.executeQuery(sql);
        String datos[] = new String[10];
        
        while(rs.next()){
        datos[0] = rs.getString("NoEnsamble");
        band = false;
        if(datos[0] != null){
        if (TablaEnsamble.getRowCount() == 0) {
            miModelo.addRow(datos);
            band = true;
            cont++;
        } else if (TablaEnsamble.getColumnCount() >= 0) {
            for (int i = 0; i < TablaEnsamble.getRowCount(); i++) {
                String es;
                if(datos[0] != null){
                if(TablaEnsamble.getValueAt(i, 0).toString().equals(datos[0])){
                    band = true;
                }
                }else{
                    band = true;
                }
                
            }
        }
        if (band == false) {
            miModelo.addRow(datos);
            
        }
        }
        }
            
        }catch(SQLException e){
            espera.dispose();
        JOptionPane.showMessageDialog(this, "ERROR AL SELECCIONAR TABLA: "+e,"ERROR",JOptionPane.ERROR_MESSAGE);
        
        }
        espera.dispose();
    }//GEN-LAST:event_TablaProyectoMouseClicked

    private void TablaEnsambleMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TablaEnsambleMouseClicked
//        try{
//        limpiarTabla3();
//        int fila = TablaEnsamble.getSelectedRow();
//        DefaultTableModel miModelo = (DefaultTableModel) TablaPlanos.getModel();
//        Connection con = null;
//        Conexion con1 = new Conexion();
//        con = con1.getConnection();
//        Statement st = con.createStatement();
//        String sql = "select * from Planos where NoEnsamble like '"+TablaEnsamble.getValueAt(fila, 0)+"'";
//        ResultSet rs = st.executeQuery(sql);
//        String datos[] = new String[10];
//        while(rs.next()){
//        datos[0] = rs.getString("Plano");
//        miModelo.addRow(datos);
//        }
//        }catch(SQLException e){
//        JOptionPane.showMessageDialog(this, "ERROR AL SELECCIONAR ENSAMBLE","ERROR",JOptionPane.ERROR_MESSAGE);
//        }
          limpiarTabla3();
          buscar();
    }//GEN-LAST:event_TablaEnsambleMouseClicked

    private void jLabel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseClicked
        dispose();
    }//GEN-LAST:event_jLabel1MouseClicked

    private void jLabel1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseEntered
        btnSalir.setBackground(java.awt.Color.red);
    }//GEN-LAST:event_jLabel1MouseEntered

    private void jLabel1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseExited
        btnSalir.setBackground(java.awt.Color.white);
    }//GEN-LAST:event_jLabel1MouseExited

    private void imgMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_imgMouseClicked

    }//GEN-LAST:event_imgMouseClicked

    private void TablaPlanosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TablaPlanosMouseClicked
        espera = new Espera();
        espera.setVisible(true);
        verPlano();
        revalidate();
        repaint();
        espera.dispose();
    }//GEN-LAST:event_TablaPlanosMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable TablaEnsamble;
    private javax.swing.JTable TablaPlanos;
    private javax.swing.JTable TablaProyecto;
    private javax.swing.JPanel btnSalir;
    private mivisorpdf.CuadroImagen img;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    // End of variables declaration//GEN-END:variables
}
