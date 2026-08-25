package pruebas;

import Conexiones.Conexion;
import VentanaEmergente.Reportes.dis;
import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

public class DisenioAnt extends InternalFrameImagen {

    int contador = 0;
    public void limpiarTabla() {
//    DefaultTableModel miModelo = (DefaultTableModel) DatosExcel.getModel();
//    String titulos[] = {"ID","NUMERO DE PARTE","CANTIDAD"};
//    miModelo = new DefaultTableModel(null,titulos);
//    DatosExcel.setModel(miModelo);
    }

    public void ver() {
        this.cmbProyecto.removeAllItems();
        this.cmbProyecto.addItem("SELECCIONAR NUMERO DE PROYECTO");
        try {
            ResultSet rs = null;
            Connection con3 = null;
            Conexion conect3 = new Conexion();
            con3 = conect3.getConnection();
            Statement Sent = con3.createStatement();
            rs = Sent.executeQuery("select Proyecto from Proyectos order by Id desc");
            while (rs.next()) {
                this.cmbProyecto.addItem(rs.getString("Proyecto"));
            }
            contador++;
        } catch (SQLException e) {
        }
    }

    public DisenioAnt() {
        initComponents();
        ver();
        ((javax.swing.plaf.basic.BasicInternalFrameUI) this.getUI()).setNorthPane(null);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        btnImportar = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        cmbProyecto = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        c = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        d = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        e = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        f = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        g = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        h = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        i1 = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        j = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        k = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        l = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        m = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        n = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        o = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        p = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        q = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        r = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        s = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        t = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        btnSalir = new javax.swing.JPanel();
        lblX = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();

        setBorder(null);
        getContentPane().setLayout(new java.awt.BorderLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setForeground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new java.awt.BorderLayout());

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new java.awt.BorderLayout());

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setPreferredSize(new java.awt.Dimension(370, 80));

        btnImportar.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        btnImportar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ImgAnimacion/pdf-file_48.png"))); // NOI18N
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
        jPanel4.add(btnImportar);

        jPanel2.add(jPanel4, java.awt.BorderLayout.PAGE_START);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setLayout(new java.awt.GridLayout(19, 2, 5, 5));

        jLabel3.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText("PROYECTO:  ");
        jPanel3.add(jLabel3);

        cmbProyecto.setBackground(new java.awt.Color(255, 255, 255));
        cmbProyecto.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        cmbProyecto.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbProyecto.setBorder(null);
        cmbProyecto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbProyectoActionPerformed(evt);
            }
        });
        jPanel3.add(cmbProyecto);

        jLabel7.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel7.setText("INTEGRACION:  ");
        jPanel3.add(jLabel7);

        c.setBackground(new java.awt.Color(255, 255, 255));
        c.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        c.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        c.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        c.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                cKeyTyped(evt);
            }
        });
        jPanel3.add(c);

        jLabel8.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel8.setText("FRESADORA:  ");
        jPanel3.add(jLabel8);

        d.setBackground(new java.awt.Color(255, 255, 255));
        d.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        d.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        d.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        d.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                dKeyTyped(evt);
            }
        });
        jPanel3.add(d);

        jLabel9.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel9.setText("TORNO:  ");
        jPanel3.add(jLabel9);

        e.setBackground(new java.awt.Color(255, 255, 255));
        e.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        e.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        e.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        e.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                eKeyTyped(evt);
            }
        });
        jPanel3.add(e);

        jLabel10.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel10.setText("CNC:  ");
        jPanel3.add(jLabel10);

        f.setBackground(new java.awt.Color(255, 255, 255));
        f.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        f.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        f.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        f.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                fKeyTyped(evt);
            }
        });
        jPanel3.add(f);

        jLabel11.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel11.setText("MAZAK:  ");
        jPanel3.add(jLabel11);

        g.setBackground(new java.awt.Color(255, 255, 255));
        g.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        g.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        g.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        g.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                gKeyTyped(evt);
            }
        });
        jPanel3.add(g);

        jLabel12.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel12.setText("RECTIFICADO:  ");
        jPanel3.add(jLabel12);

        h.setBackground(new java.awt.Color(255, 255, 255));
        h.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        h.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        h.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        h.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                hKeyTyped(evt);
            }
        });
        jPanel3.add(h);

        jLabel13.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel13.setText("SOLDADURA:  ");
        jPanel3.add(jLabel13);

        i1.setBackground(new java.awt.Color(255, 255, 255));
        i1.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        i1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        i1.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        i1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                i1KeyTyped(evt);
            }
        });
        jPanel3.add(i1);

        jLabel14.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel14.setText("ENSAMBLE:  ");
        jPanel3.add(jLabel14);

        j.setBackground(new java.awt.Color(255, 255, 255));
        j.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        j.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        j.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        j.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jKeyTyped(evt);
            }
        });
        jPanel3.add(j);

        jLabel15.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel15.setText("REVISION:  ");
        jPanel3.add(jLabel15);

        k.setBackground(new java.awt.Color(255, 255, 255));
        k.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        k.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        k.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        k.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                kKeyTyped(evt);
            }
        });
        jPanel3.add(k);

        jLabel16.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel16.setText("NO. PARTE:  ");
        jPanel3.add(jLabel16);

        l.setBackground(new java.awt.Color(255, 255, 255));
        l.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        l.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        l.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        l.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                lKeyTyped(evt);
            }
        });
        jPanel3.add(l);

        jLabel18.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel18.setText("DESCRIPCION:  ");
        jPanel3.add(jLabel18);

        m.setBackground(new java.awt.Color(255, 255, 255));
        m.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        m.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        m.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        m.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                mKeyTyped(evt);
            }
        });
        jPanel3.add(m);

        jLabel17.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel17.setText("MATERIAL:  ");
        jPanel3.add(jLabel17);

        n.setBackground(new java.awt.Color(255, 255, 255));
        n.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        n.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        n.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        n.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                nKeyTyped(evt);
            }
        });
        jPanel3.add(n);

        jLabel20.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel20.setText("DUREZA:  ");
        jPanel3.add(jLabel20);

        o.setBackground(new java.awt.Color(255, 255, 255));
        o.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        o.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        o.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        o.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                oKeyTyped(evt);
            }
        });
        jPanel3.add(o);

        jLabel19.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel19.setText("MAQUINA:  ");
        jPanel3.add(jLabel19);

        p.setBackground(new java.awt.Color(255, 255, 255));
        p.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        p.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        p.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        p.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                pKeyTyped(evt);
            }
        });
        jPanel3.add(p);

        jLabel22.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel22.setText("NO. ENSAMBLE:  ");
        jPanel3.add(jLabel22);

        q.setBackground(new java.awt.Color(255, 255, 255));
        q.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        q.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        q.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        q.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                qKeyTyped(evt);
            }
        });
        jPanel3.add(q);

        jLabel21.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel21.setText("TRATAMIENTO:  ");
        jPanel3.add(jLabel21);

        r.setBackground(new java.awt.Color(255, 255, 255));
        r.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        r.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        r.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        r.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                rKeyTyped(evt);
            }
        });
        jPanel3.add(r);

        jLabel24.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel24.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel24.setText("CANTIDAD:  ");
        jPanel3.add(jLabel24);

        s.setBackground(new java.awt.Color(255, 255, 255));
        s.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        s.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        s.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        s.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                sKeyTyped(evt);
            }
        });
        jPanel3.add(s);

        jLabel25.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel25.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel25.setText("DIMENSION:  ");
        jPanel3.add(jLabel25);

        t.setBackground(new java.awt.Color(255, 255, 255));
        t.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        t.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        t.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        t.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tKeyTyped(evt);
            }
        });
        jPanel3.add(t);

        jPanel2.add(jPanel3, java.awt.BorderLayout.CENTER);

        jPanel1.add(jPanel2, java.awt.BorderLayout.CENTER);

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setLayout(new java.awt.BorderLayout());

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 60)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 165, 252));
        jLabel1.setText("     DISEÑO     ");
        jLabel1.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 6, 0, new java.awt.Color(0, 165, 252)));
        jPanel6.add(jLabel1);

        jPanel5.add(jPanel6, java.awt.BorderLayout.CENTER);

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));

        btnSalir.setBackground(new java.awt.Color(255, 255, 255));

        lblX.setFont(new java.awt.Font("Roboto", 1, 24)); // NOI18N
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

        jPanel1.add(jPanel5, java.awt.BorderLayout.PAGE_START);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        jMenu1.setText("File");

        jMenuItem1.setText("Dis");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");
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
        if (cmbProyecto.getSelectedItem().equals("SELECCIONAR NUMERO DE PROYECTO")) {
            JOptionPane.showMessageDialog(this, "DEBES SELECCIONAR UN PROYECTO", "ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
        } else {
            JFileChooser SelectArchivo = new JFileChooser();
            File archivo[] = null;
            PDFManager pdfManager = new PDFManager();
            String vector[] = new String[30];

            SelectArchivo.setFileFilter(new FileNameExtensionFilter("PDF (*.pdf)", "pdf"));
            SelectArchivo.setMultiSelectionEnabled(true);
            if (SelectArchivo.showDialog(null, "Seleccionar Archivo") == JFileChooser.APPROVE_OPTION) {
                archivo = SelectArchivo.getSelectedFiles();

            }
            File ar = null;
            
            for (int i = 0; i < archivo.length; i++) {
                ar = archivo[i];
                
                byte[] pe = null;
                if(archivo[i] == null){
                    
                }else{
                pe = new byte[(int) archivo[i].length()];
                try{
                
                InputStream input = new FileInputStream(archivo[i]);
                input.read(pe);
                }catch(IOException e){
                    
                }
                }
                
                pdfManager.setFilePath(ar.getAbsolutePath());
                String text = "";

                int aux1 = 0, aux2 = 0;
                int aux = 0;

                try {
                    text = pdfManager.toText();
                } catch (IOException ex) {
                    Logger.getLogger(DisenioAnt.class.getName()).log(Level.SEVERE, null, ex);
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
                int opc = JOptionPane.showConfirmDialog(this, "¿SUBIR PLANO " + vector[11] + "?");

                if (opc == 0) {
                    try {
                        Connection con = null;
                        Conexion con1 = new Conexion();
                        con = con1.getConnection();
                        Statement st2 = con.createStatement();
                        
                        String sql2 = "select Plano from Planos where Plano like '"+vector[11]+"'";
                        ResultSet rs2 = st2.executeQuery(sql2);
                        String plan = "";
                        while(rs2.next()){
                            plan = rs2.getString("Plano");
                        }
                        boolean band1 = true;
                        int reem = 0;
                        if(plan != null){
                            if(plan.equals("")){
                                band1 = true;
                            }else{
                                reem = JOptionPane.showConfirmDialog(this, "ESTE PLANO YA SE ENCUENTRA EN LA BD, ¿DESEAS REEMPLAZARLO?");
                                if(reem == 0){
                                    band1 = false;
                                }
                            }
                        }else{
                            band1 = true;
                        }
                        if(band1){
                        //--------------------------------1        2          3     4           5           6           7         8     9    10      11           12         13        14          15        1 6       17       18        19         20                             
                        String sql = "insert into Planos (Plano, Proyecto, Estado, Prioridad, Cantidad, Integracion, Fresadora, Torno, Cnc, Mazak, Rectificado, Soldadura, Ensamble, Revision, Descripcion, Material, Dureza, Maquina, NoEnsamble, Tratamiento,Dimension) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                        PreparedStatement pst = con.prepareStatement(sql);
                        
                        String sql3 = "insert into pdfplanos (Plano, Pdf) values(?,?)";
                        PreparedStatement pst3 = con.prepareStatement(sql3);
                        
                        pst.setString(1, vector[11]);
                        pst.setString(2, (String) cmbProyecto.getSelectedItem());
                        pst.setString(3, "");
                        pst.setString(4, "0");
                        pst.setString(5, vector[18]);
                        pst.setString(6, vector[2]);
                        pst.setString(7, vector[3]);
                        pst.setString(8, vector[4]);
                        pst.setString(9, vector[5]);
                        pst.setString(10, vector[6]);
                        pst.setString(11, vector[7]);
                        pst.setString(12, vector[8]);
                        pst.setString(13, vector[9]);
                        pst.setString(14, vector[10]);
                        pst.setString(15, vector[12]);
                        pst.setString(16, vector[13]);
                        pst.setString(17, vector[14]);
                        pst.setString(18, vector[15]);
                        pst.setString(19, vector[16]);
                        pst.setString(20, vector[17]);
                        pst.setString(21, vector[19]);

                        pst.executeUpdate();
                        
                        pst3.setString(1, vector[11]);
                        pst3.setBytes(2, pe);
                        
                        pst3.executeQuery();
                            
                        }else{
                            String sql = "update Planos set Plano = ?, Proyecto = ?, Estado = ?, Cantidad = ?, Integracion = ?, Fresadora = ?, Torno = ?, Cnc = ?, "
                                    + "Mazak = ?, Rectificado = ?, Soldadura = ?, Ensamble = ?, Revision = ?, Descripcion = ?, Material = ?, Dureza = ?, "
                                    + "Maquina = ?, NoEnsamble = ?, Tratamiento = ?,Dimension = ? where Plano = ?";
                            PreparedStatement pst = con.prepareStatement(sql);
                            
                            String sql3 = "update pdfplanos set Plano = ?, Pdf = ? where Plano = ?";
                            PreparedStatement pst3 = con.prepareStatement(sql3);
                            
                            pst.setString(1, vector[11]);
                            pst.setString(2, (String) cmbProyecto.getSelectedItem());
                            pst.setString(3, "");
                            pst.setString(4, vector[18]);
                            pst.setString(5, vector[2]);
                            pst.setString(6, vector[3]);
                            pst.setString(7, vector[4]);
                            pst.setString(8, vector[5]);
                            pst.setString(9, vector[6]);
                            pst.setString(10, vector[7]);
                            pst.setString(11, vector[8]);
                            pst.setString(12, vector[9]);
                            pst.setString(13, vector[10]);
                            pst.setString(14, vector[12]);
                            pst.setString(15, vector[13]);
                            pst.setString(16, vector[14]);
                            pst.setString(17, vector[15]);
                            pst.setString(18, vector[16]);
                            pst.setString(19, vector[17]);
                            pst.setString(20, vector[19]);
                            pst.setString(21, vector[11]);
                            
                            int n = pst.executeUpdate();
                            
                            pst3.setString(1, vector[11]);
                            pst3.setBytes(2, pe);
                            pst3.setString(3, vector[11]);

                            int n1 = pst3.executeUpdate();
                            
                            if(n > 0 && n1 > 0){
                                JOptionPane.showMessageDialog(this, "PLANO ACTUALIZADO");
                            }
                            
                        }
                    } catch (SQLException e) {
                        JOptionPane.showMessageDialog(this, "ERROR AL GUARDAR DATOS: " + e, "ERROR", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        }
        
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

    private void cmbProyectoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbProyectoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbProyectoActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton btnImportar;
    private javax.swing.JPanel btnSalir;
    private javax.swing.JTextField c;
    private javax.swing.JComboBox<String> cmbProyecto;
    private javax.swing.JTextField d;
    private javax.swing.JTextField e;
    private javax.swing.JTextField f;
    private javax.swing.JTextField g;
    private javax.swing.JTextField h;
    private javax.swing.JTextField i1;
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
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JTextField k;
    private javax.swing.JTextField l;
    private javax.swing.JLabel lblX;
    private javax.swing.JTextField m;
    private javax.swing.JTextField n;
    private javax.swing.JTextField o;
    private javax.swing.JTextField p;
    private javax.swing.JTextField q;
    private javax.swing.JTextField r;
    private javax.swing.JTextField s;
    private javax.swing.JTextField t;
    // End of variables declaration//GEN-END:variables
}
