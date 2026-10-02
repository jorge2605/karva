package VentanaEmergente.Diseño;

import Conexiones.Conexion;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Image;
import java.beans.PropertyVetoException;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import pruebas.Disenio1;
import pruebas.Inicio1;
import toggle.ToggleListener;

public class InicioDiseño extends javax.swing.JInternalFrame {

    Inicio1 inicio;
    public Stack<TempleteDiseno> templetes;
    public boolean activar = false;

    public void entrar(TempleteDiseno templete, String ruta) {
        Disenio1 c = new Disenio1(templete, inicio, ruta);
        inicio.jDesktopPane1.add(c);
        c.toFront();
        c.setLocation(inicio.jDesktopPane1.getWidth() / 2 - c.getWidth() / 2, inicio.jDesktopPane1.getHeight() / 2 - c.getHeight() / 2);
        try {
            c.setMaximum(true);
        } catch (PropertyVetoException e) {
            Logger.getLogger(Inicio1.class.getName()).log(Level.SEVERE, null, e);
        }
        c.setVisible(true);
        this.dispose();
    }

    public final void insertarImagen(String url, JButton lbl) {
        ImageIcon icon = new ImageIcon(url);
        Image imagen = icon.getImage();
        Image imagenEscalada = imagen.getScaledInstance(220, 220, Image.SCALE_SMOOTH);

        lbl.setIcon(new ImageIcon(imagenEscalada));
    }
    
    public final void activarEmpresas(Connection con, boolean activo, int id) {
        try {
            String sql = "update templetediseno set activo = ? where idtempletediseno = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            
            pst.setBoolean(1, activo);
            pst.setInt(2, id);
            
            int n = pst.executeUpdate();
            
            if (n < 1) {
                JOptionPane.showMessageDialog(this, "Error al trater de activar/desactivar empresa", "Error", JOptionPane.ERROR_MESSAGE);
            }
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al tratar de activar/desactivar empresa: " + e, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public final void crearBotones(TempleteDiseno templete, String ruta, int id, Connection con, boolean activo) {
        rojeru_san.rspanel.RSPanelRound pnl = new rojeru_san.rspanel.RSPanelRound();
        pnl.setColorBackground(new java.awt.Color(255, 255, 255));
        pnl.setColorBorde(new java.awt.Color(255, 255, 255));
        pnl.setPreferredSize(new java.awt.Dimension(180, 180));
        pnl.setLayout(new java.awt.BorderLayout());
        java.awt.GridBagConstraints gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(30, 45, 30, 45);

        JPanel pnlDesactivar = new javax.swing.JPanel();
        toggle.ToggleButton tgDesactivar = new toggle.ToggleButton();
        tgDesactivar.setForeground(new java.awt.Color(0, 112, 192));
        tgDesactivar.setSelected(activo);
        
        ToggleListener tog = new ToggleListener() {
            @Override
            public void onSelected(boolean selected) {
                activarEmpresas(con, selected, id);
            }

            @Override
            public void onAnimated(float animated) {
            }
        };
        tgDesactivar.addEventToggleSelected(tog);
        if (activar) {
            pnlDesactivar.setVisible(true);
        } else {
            pnlDesactivar.setVisible(false);
        }
        pnlDesactivar.add(tgDesactivar);

        javax.swing.JButton lbl = new javax.swing.JButton();
        lbl.setFont(new java.awt.Font("Roboto", 1, 14));
        lbl.setForeground(new java.awt.Color(0, 153, 255));
        insertarImagen(ruta, lbl);
        lbl.setBorder(null);
        lbl.setText(templete.getEmpresa());
        lbl.setContentAreaFilled(false);
        lbl.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lbl.setFocusPainted(false);
        lbl.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        lbl.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        lbl.addActionListener((java.awt.event.ActionEvent evt) -> {
            entrar(templete, ruta);
        });
        pnl.add(pnlDesactivar, java.awt.BorderLayout.NORTH);
        pnl.add(lbl, java.awt.BorderLayout.CENTER);

        pnlEmpresas.add(pnl, gridBagConstraints);
    }

    public String guardarImagenBD(Connection conexion, int id, String nombreArchivo) throws Exception {
        String documentos = System.getProperty("user.home") + File.separator + "Documents";
        Path carpeta = Paths.get(documentos, "karva");
        Files.createDirectories(carpeta);
        Path archivo = carpeta.resolve(nombreArchivo);
        if (Files.exists(archivo)) {
            return archivo.toAbsolutePath().toString();
        }
        String sql = "select * from archivostemplete where idtempletediseno like '" + id + "' and doc like 'img'";
        Statement st = conexion.createStatement();
        ResultSet rs = st.executeQuery(sql);
        if (!rs.next()) {
            throw new Exception("No se encontró la imagen para el ID: " + id);
        }
        byte[] datos = rs.getBytes("archivo");

        if (datos == null || datos.length == 0) {
            throw new Exception("El campo doc está vacío.");
        }
        Files.write(archivo, datos);

        return archivo.toAbsolutePath().toString();
    }

    public final void limpiarPanel() {
        pnlEmpresas.removeAll();
        revalidate();
        repaint();
    }

    public final void agregarEmpresas() {
        try {
            limpiarPanel();
            Connection con = new Conexion().getConnection();
            String sql = "select * from templetediseno where activo like true order by empresa asc";
            if (activar) {
                sql = "select * from templetediseno order by empresa asc";
            }
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            templetes = new Stack<>();
            while (rs.next()) {
                int id = rs.getInt("idtempletediseno");
                String empresa = rs.getString("empresa");
                String desenador = rs.getString("disenador");
                String cliente = rs.getString("cliente");
                String revision = rs.getString("revision");
                String parte = rs.getString("parte");
                String descripcion = rs.getString("descripcion");
                String material = rs.getString("material");
                String dureza = rs.getString("dureza");
                String ensamble = rs.getString("ensamble");
                String tratamiento = rs.getString("tratamiento");
                String cantidad = rs.getString("cantidad");
                String codigo = rs.getString("codigo");
                boolean activo = rs.getBoolean("activo");
                templetes.add(new TempleteDiseno(empresa, desenador, cliente, revision, parte, descripcion, material, dureza, ensamble, tratamiento, cantidad, codigo));
                String ruta = guardarImagenBD(con, id, empresa + ".jpg");
                crearBotones(templetes.lastElement(), ruta, id, con, activo);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al agregar templetes: " + e, "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            Logger.getLogger(InicioDiseño.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public InicioDiseño(Inicio1 ini) {
        initComponents();
        inicio = ini;
        ((javax.swing.plaf.basic.BasicInternalFrameUI) this.getUI()).setNorthPane(null);
        agregarEmpresas();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlEmpresas = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        btnSalir = new javax.swing.JPanel();
        lblX = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();

        setBorder(null);

        pnlEmpresas.setBackground(new java.awt.Color(245, 245, 245));
        pnlEmpresas.setPreferredSize(new java.awt.Dimension(150, 150));
        pnlEmpresas.setLayout(new java.awt.GridBagLayout());
        getContentPane().add(pnlEmpresas, java.awt.BorderLayout.CENTER);

        jPanel5.setBackground(new java.awt.Color(245, 245, 245));
        jPanel5.setLayout(new java.awt.BorderLayout());

        jPanel7.setBackground(new java.awt.Color(245, 245, 245));

        btnSalir.setBackground(new java.awt.Color(245, 245, 245));

        lblX.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
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

        getContentPane().add(jPanel5, java.awt.BorderLayout.PAGE_START);

        jMenu1.setText("File");

        jMenuItem1.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        jMenuItem1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/add.png"))); // NOI18N
        jMenuItem1.setText("Agregar nuevo templete                       ");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuItem2.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        jMenuItem2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/ajustes.png"))); // NOI18N
        jMenuItem2.setText("Activar / Desactivar templete");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void lblXMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblXMouseClicked
        dispose();
    }//GEN-LAST:event_lblXMouseClicked

    private void lblXMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblXMouseEntered
        btnSalir.setBackground(Color.red);
        lblX.setForeground(Color.white);
    }//GEN-LAST:event_lblXMouseEntered

    private void lblXMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblXMouseExited
        btnSalir.setBackground(new Color(245, 245, 245));
        lblX.setForeground(Color.black);
    }//GEN-LAST:event_lblXMouseExited

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        JFrame f = (JFrame) JOptionPane.getFrameForComponent(this);
        AgregarPlano add = new AgregarPlano(f, true);
        add.setLocationRelativeTo(f);
        add.setVisible(true);
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        activar = !activar;
        agregarEmpresas();
    }//GEN-LAST:event_jMenuItem2ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel btnSalir;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JLabel lblX;
    private javax.swing.JPanel pnlEmpresas;
    // End of variables declaration//GEN-END:variables
}
