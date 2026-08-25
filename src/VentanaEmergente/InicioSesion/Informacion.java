package VentanaEmergente.InicioSesion;

import Conexiones.ConexionChat;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import scrollPane.PanelRound;

public class Informacion extends javax.swing.JDialog {

    int x, y;
    JFrame f;
    
    public void agregarImagen(String ruta, JPanel PanelImagen, int pos,int y, Stack<String> rutas){
        ImageIcon image1 = new ImageIcon(ruta);
        Image scaledImage1 = image1.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        JLabel imageLabel1 = new JLabel(new ImageIcon(scaledImage1));
        imageLabel1.setCursor(new Cursor(Cursor.HAND_CURSOR));
        imageLabel1.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Visor vis = new Visor(f, true);
                vis.rutas = rutas;
                vis.verImagenes();
                vis.setLocationRelativeTo(f);
                vis.setVisible(true);
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
        });
        PanelRound pnl1 = new PanelRound();
        pnl1.setRoundBottomLeft(30);
        pnl1.setRoundTopLeft(30);
        pnl1.setRoundBottomRight(30);
        pnl1.setRoundTopRight(30);
        pnl1.add(imageLabel1);
        PanelImagen.add(pnl1, new org.netbeans.lib.awtextra.AbsoluteConstraints(pos, y, -1, -1));
    }
    
    public void agregarImagenes(String idseccion, JPanel panelImagenes){
        try{
            Connection con;
            ConexionChat con1 = new ConexionChat();
            con = con1.getConnection();
            Statement st = con.createStatement();
            String sql = "select * from imagenactualizacion where idseccion like '" + idseccion + "'";
            ResultSet rs = st.executeQuery(sql);
            byte[] b;
            String nombre;
            int cont = 0;
            Stack<String> rutas = new Stack<>();
            while(rs.next()){
                b = rs.getBytes("imagen");
                nombre = rs.getString("idimagenactualizacion");
                InputStream bos = new ByteArrayInputStream(b);
                
                int tamInput = bos.available();
                byte[] datosPdf = new byte[tamInput];
                bos.read(datosPdf, 0, tamInput);

                OutputStream out;
                try{
                    out = new FileOutputStream("../src/ImgTemp/" + nombre + ".jpg");
                    rutas.push(new File("../src/ImgTemp/" + nombre + ".jpg").getAbsolutePath());
                }catch(Exception e){
                    out = new FileOutputStream("src/ImgTemp/" + nombre + ".jpg");
                    rutas.push(new File("src/ImgTemp/" + nombre + ".jpg").getAbsolutePath());
                }
                out.write(datosPdf);

                out.close();
                bos.close();
                
                cont++;
            }
            for (int i = 0; i < rutas.size(); i++) {
                int pos = 0;
                if(i % 2 == 0){
                    pos = 10;
                }
                agregarImagen(rutas.get(i), panelImagenes, i * 80, pos, rutas);
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "Error: " + e,"Error",JOptionPane.ERROR_MESSAGE);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Informacion.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Informacion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void crearSeccion(String idseccion, JPanel PanelSecciones, String titulo, String descripcion, String departamento){
        JPanel PanelSeccion = new javax.swing.JPanel();
        PanelSeccion.setBackground(new java.awt.Color(255, 255, 255));
        PanelSeccion.setLayout(new java.awt.BorderLayout());
        
        //--------------------------TITULO DE CADA SECCION------------------------------
        JLabel lblTitulo = new javax.swing.JLabel();
        lblTitulo.setFont(new java.awt.Font("Roboto", 1, 24)); // NOI18N
        lblTitulo.setForeground(new java.awt.Color(204, 51, 0));
        lblTitulo.setText(titulo);
        PanelSeccion.add(lblTitulo, java.awt.BorderLayout.NORTH);
        
        //--------------------------DESCRIPCION DE CADA SECCION------------------------------
        JLabel lblDescripcion = new javax.swing.JLabel();
        lblDescripcion.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        lblDescripcion.setForeground(new java.awt.Color(51, 51, 51));
        lblDescripcion.setText("<html><p style=\"font-size: 12px; color: rgb(215,215,215); font-weight: 700;\"> " + departamento + "</p><p style=\"width:400px; text-align: justify; \" >" + descripcion);
        PanelSeccion.add(lblDescripcion, java.awt.BorderLayout.CENTER);
        
        //-----------------------------IMAGENES------------------------------------------
        JPanel panelImgen = new javax.swing.JPanel();
        panelImgen.setBackground(new java.awt.Color(255, 255, 255));
        panelImgen.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        agregarImagenes(idseccion, panelImgen);
        PanelSeccion.add(panelImgen, java.awt.BorderLayout.PAGE_END);
        
        java.awt.GridBagConstraints gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        PanelSecciones.add(PanelSeccion, gridBagConstraints);
    }
    
    public void crearSecciones(String idActualizacion, JPanel PanelSecciones){
        try{
            Connection con;
            ConexionChat con1 = new ConexionChat();
            con = con1.getConnection();
            Statement st = con.createStatement();
            String sql = "select * from secciones where idactualizacion like '" + idActualizacion + "'";
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
                String titulo = rs.getString("titulo");
                String descripcion = rs.getString("descripcion");
                String idseccion = rs.getString("idsecciones");
                String departamento = rs.getString("departamento");
                crearSeccion(idseccion, PanelSecciones, titulo, descripcion, departamento);
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "Error: " + e,"Error",JOptionPane.ERROR_MESSAGE);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Informacion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void crearActualizacion(String actualizacion, String fecha, String idactualizacion){
        JPanel PanelActualizacion = new javax.swing.JPanel();
        PanelActualizacion.setBackground(new java.awt.Color(255, 255, 255));
        PanelActualizacion.setLayout(new java.awt.BorderLayout());
        
        JLabel lblActualizacion = new javax.swing.JLabel();
        lblActualizacion.setFont(new java.awt.Font("Consolas", 1, 24)); // NOI18N
        lblActualizacion.setForeground(new java.awt.Color(0, 102, 204));
        lblActualizacion.setText(actualizacion + " - " + fecha);
        lblActualizacion.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        
        JPanel PanelSecciones = new javax.swing.JPanel();
        PanelSecciones.setBackground(new java.awt.Color(255, 255, 255));
        PanelSecciones.setLayout(new java.awt.GridBagLayout());
        
        crearSecciones(idactualizacion, PanelSecciones);
        
        lblActualizacion.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if(PanelSecciones.isVisible()){
                    PanelSecciones.setVisible(false);
                }else{
                    PanelSecciones.setVisible(true);
                }
            }
        });

        PanelActualizacion.add(PanelSecciones, java.awt.BorderLayout.CENTER);
        PanelActualizacion.add(lblActualizacion, java.awt.BorderLayout.PAGE_START);
        
        panelRound3.add(PanelActualizacion);
    }
    
    public final void crearActualizaciones(){
        panelRound3.removeAll();
        f = (JFrame) JOptionPane.getFrameForComponent(this);
        try{
            Connection con;
            ConexionChat con1 = new ConexionChat();
            con = con1.getConnection();
            Statement st = con.createStatement();
            String sql = "select * from actualizaciones order by idactualizaciones desc";
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
                String actualizacion = rs.getString("actualizacion");
                String fecha = rs.getString("fecha");
                String idactualizaciones = rs.getString("idactualizaciones");
                crearActualizacion(actualizacion, fecha, idactualizaciones);
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "Error: " + e,"Error",JOptionPane.ERROR_MESSAGE);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Informacion.class.getName()).log(Level.SEVERE, null, ex);
        }
        revalidate();
        repaint();
    }
    
    public Informacion(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        panelRound2.setBackground(new Color(0,0,0,0));
        panelRound3.setBackground(new Color(0,0,0,0));
        panelRound4.setBackground(new Color(0,0,0,0));
        pnlX.setBackground(new Color(0,0,0,0));
        jPanel1.setBackground(new Color(0,0,0,0));
        jPanel2.setBackground(new Color(0,0,0,0));
        jScrollPane1.getViewport().setBackground(new Color(0,0,0,0));
        jScrollPane1.getVerticalScrollBar().setUnitIncrement(15);
        this.setPreferredSize(new Dimension(650, 800));
        this.setBackground(new Color(0,0,0,0));
        crearActualizaciones();
        revalidate();
        repaint();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel1 = new javax.swing.JPanel();
        panelRound1 = new scrollPane.PanelRound();
        panelRound2 = new scrollPane.PanelRound();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        pnlX = new scrollPane.PanelRound();
        lblX = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        panelRound3 = new scrollPane.PanelRound();
        PanelActualizacion1 = new javax.swing.JPanel();
        lblActualizacion1 = new javax.swing.JLabel();
        PanelSecciones1 = new javax.swing.JPanel();
        PanelSeccion3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        panelRound4 = new scrollPane.PanelRound();
        jLabel11 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setModal(true);
        setUndecorated(true);
        setPreferredSize(new java.awt.Dimension(700, 800));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new java.awt.BorderLayout());

        panelRound1.setBackground(new java.awt.Color(250, 250, 250));
        panelRound1.setRoundBottomLeft(70);
        panelRound1.setRoundBottomRight(70);
        panelRound1.setRoundTopLeft(70);
        panelRound1.setRoundTopRight(70);
        panelRound1.setLayout(new java.awt.BorderLayout());

        panelRound2.setBackground(new java.awt.Color(255, 255, 255));
        panelRound2.setRoundTopLeft(100);
        panelRound2.setRoundTopRight(80);
        panelRound2.setLayout(new java.awt.BorderLayout());

        jLabel1.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 102, 0));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/titulo actualizacion.png"))); // NOI18N
        jLabel1.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                jLabel1MouseDragged(evt);
            }
        });
        jLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabel1MousePressed(evt);
            }
        });
        panelRound2.add(jLabel1, java.awt.BorderLayout.CENTER);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        pnlX.setBackground(new java.awt.Color(255, 255, 255));
        pnlX.setRoundBottomLeft(20);
        pnlX.setRoundBottomRight(20);
        pnlX.setRoundTopLeft(20);
        pnlX.setRoundTopRight(20);

        lblX.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        lblX.setText(" X ");
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
        pnlX.add(lblX);

        jPanel2.add(pnlX);

        panelRound2.add(jPanel2, java.awt.BorderLayout.EAST);

        panelRound1.add(panelRound2, java.awt.BorderLayout.NORTH);

        jScrollPane1.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane1.setBorder(null);
        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        panelRound3.setBackground(new java.awt.Color(255, 255, 255));
        panelRound3.setRoundBottomLeft(50);
        panelRound3.setRoundBottomRight(50);
        panelRound3.setLayout(new javax.swing.BoxLayout(panelRound3, javax.swing.BoxLayout.Y_AXIS));

        PanelActualizacion1.setBackground(new java.awt.Color(255, 255, 255));
        PanelActualizacion1.setLayout(new java.awt.BorderLayout());

        lblActualizacion1.setFont(new java.awt.Font("Consolas", 1, 24)); // NOI18N
        lblActualizacion1.setForeground(new java.awt.Color(0, 102, 204));
        lblActualizacion1.setText("2.5.4 - 2024-10-08");
        lblActualizacion1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblActualizacion1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblActualizacion1MouseClicked(evt);
            }
        });
        PanelActualizacion1.add(lblActualizacion1, java.awt.BorderLayout.PAGE_START);

        PanelSecciones1.setBackground(new java.awt.Color(255, 255, 255));
        PanelSecciones1.setLayout(new java.awt.GridBagLayout());

        PanelSeccion3.setBackground(new java.awt.Color(255, 255, 255));
        PanelSeccion3.setLayout(new java.awt.BorderLayout());

        jLabel3.setFont(new java.awt.Font("Roboto", 1, 24)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(204, 51, 0));
        jLabel3.setText("Ver requisiciones:");
        PanelSeccion3.add(jLabel3, java.awt.BorderLayout.NORTH);

        jLabel15.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(51, 51, 51));
        jLabel15.setText("<html>\n<p style=\"font-size: 12px; color: rgb(215,215,215); font-weight: 700;\"> Departamento</p>\n<p style=\"width:400px;\">Ahora en la parte del inicio, en módulo de ver requisiciones aparecerá un número, ese número representa las requisiciones atrasadas de cada usuario. Entrando al módulo hay una nueva opción, se llama requisiciones atrasadas, en esa opción podrá revisar que requisiciones están atrasadas.");
        PanelSeccion3.add(jLabel15, java.awt.BorderLayout.CENTER);

        jLabel16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ImgTemp/ver requisiciones.jpg"))); // NOI18N
        PanelSeccion3.add(jLabel16, java.awt.BorderLayout.PAGE_END);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        PanelSecciones1.add(PanelSeccion3, gridBagConstraints);

        PanelActualizacion1.add(PanelSecciones1, java.awt.BorderLayout.CENTER);

        panelRound3.add(PanelActualizacion1);

        jScrollPane1.setViewportView(panelRound3);

        panelRound1.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        panelRound4.setBackground(new java.awt.Color(255, 255, 255));
        panelRound4.setRoundBottomLeft(80);
        panelRound4.setRoundBottomRight(80);
        panelRound4.setRoundTopLeft(80);
        panelRound4.setRoundTopRight(80);

        jLabel11.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/3i.png"))); // NOI18N
        jLabel11.setText("  ");
        panelRound4.add(jLabel11);

        panelRound1.add(panelRound4, java.awt.BorderLayout.PAGE_END);

        jPanel1.add(panelRound1, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void lblXMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblXMouseEntered
        pnlX.setBackground(Color.red);
        lblX.setForeground(Color.white);
    }//GEN-LAST:event_lblXMouseEntered

    private void lblXMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblXMouseExited
        pnlX.setBackground(new Color(0,0,0,0));
        lblX.setForeground(new Color(52,52,52));
        revalidate();
        repaint();
    }//GEN-LAST:event_lblXMouseExited

    private void lblXMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblXMouseClicked
        this.dispose();
    }//GEN-LAST:event_lblXMouseClicked

    private void jLabel1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MousePressed
        x = evt.getX();
        y = evt.getY();
    }//GEN-LAST:event_jLabel1MousePressed

    private void jLabel1MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseDragged
        int xx = evt.getXOnScreen();
        int yy = evt.getYOnScreen();
        this.setLocation(xx - (x), yy - y);
    }//GEN-LAST:event_jLabel1MouseDragged

    private void lblActualizacion1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblActualizacion1MouseClicked
        
    }//GEN-LAST:event_lblActualizacion1MouseClicked

    public static void main(String args[]) {
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Informacion dialog = new Informacion(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PanelActualizacion1;
    private javax.swing.JPanel PanelSeccion3;
    private javax.swing.JPanel PanelSecciones1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblActualizacion1;
    private javax.swing.JLabel lblX;
    private scrollPane.PanelRound panelRound1;
    private scrollPane.PanelRound panelRound2;
    private scrollPane.PanelRound panelRound3;
    private scrollPane.PanelRound panelRound4;
    private scrollPane.PanelRound pnlX;
    // End of variables declaration//GEN-END:variables
}
