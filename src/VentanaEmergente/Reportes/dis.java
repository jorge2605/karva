package VentanaEmergente.Reportes;

import Conexiones.Conexion;
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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import pruebas.PDFManager;

public class dis extends javax.swing.JFrame {
int contador = 0;
    public void ver() {
        this.cmbProyecto.removeAllItems();
        this.cmbProyecto.addItem("SELECCIONAR NUMERO DE PROYECTO");
        try {
            ResultSet rs = null;
            Connection con3 = null;
            Conexion conect3 = new Conexion();
            con3 = conect3.getConnection();
            Statement Sent = con3.createStatement();
            rs = Sent.executeQuery("select * from Proyectos");
            while (rs.next()) {
                this.cmbProyecto.addItem(rs.getString("Proyecto"));
            }
            contador++;
        } catch (SQLException e) {
        }
    }
    public dis() {
        initComponents();
        ver();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

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
        btnSalir = new javax.swing.JPanel();
        jLabel27 = new javax.swing.JLabel();
        btnImportar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setText("PROYECTO");

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel7.setText("INTEGRACION");

        c.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        c.setText("0");
        c.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                cKeyTyped(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel8.setText("FRESADORA");

        d.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        d.setText("0");
        d.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                dKeyTyped(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel9.setText("TORNO");

        e.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        e.setText("0");
        e.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                eKeyTyped(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel10.setText("CNC");

        f.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        f.setText("0");
        f.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                fKeyTyped(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel11.setText("MAZAK");

        g.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        g.setText("0");
        g.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                gKeyTyped(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel12.setText("RECTIFICADO");

        h.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        h.setText("0");
        h.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                hKeyTyped(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel13.setText("SOLDADURA");

        i1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        i1.setText("0");
        i1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                i1KeyTyped(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel14.setText("ENSAMBLE");

        j.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        j.setText("0");
        j.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jKeyTyped(evt);
            }
        });

        jLabel15.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel15.setText("REVISION");

        k.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        k.setText("0");
        k.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                kKeyTyped(evt);
            }
        });

        jLabel16.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel16.setText("NO. PARTE");

        l.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        l.setText("0");
        l.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                lKeyTyped(evt);
            }
        });

        jLabel18.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel18.setText("DESCRIPCION");

        m.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        m.setText("0");
        m.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                mKeyTyped(evt);
            }
        });

        jLabel17.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel17.setText("MATERIAL");

        n.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        n.setText("0");
        n.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                nKeyTyped(evt);
            }
        });

        jLabel20.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel20.setText("DUREZA");

        o.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        o.setText("0");
        o.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                oKeyTyped(evt);
            }
        });

        jLabel19.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel19.setText("MAQUINA");

        p.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        p.setText("0");
        p.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                pKeyTyped(evt);
            }
        });

        jLabel22.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel22.setText("NO. ENSAMBLE");

        q.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        q.setText("0");
        q.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                qKeyTyped(evt);
            }
        });

        jLabel21.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel21.setText("TRATAMIENTO");

        r.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        r.setText("0");
        r.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                rKeyTyped(evt);
            }
        });

        jLabel24.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel24.setText("CANTIDAD");

        s.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        s.setText("0");
        s.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                sKeyTyped(evt);
            }
        });

        btnSalir.setBackground(new java.awt.Color(255, 255, 255));

        jLabel27.setFont(new java.awt.Font("Roboto Light", 0, 48)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(0, 0, 0));
        jLabel27.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel27.setText("x");
        jLabel27.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel27MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel27MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel27MouseExited(evt);
            }
        });

        javax.swing.GroupLayout btnSalirLayout = new javax.swing.GroupLayout(btnSalir);
        btnSalir.setLayout(btnSalirLayout);
        btnSalirLayout.setHorizontalGroup(
            btnSalirLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel27, javax.swing.GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE)
        );
        btnSalirLayout.setVerticalGroup(
            btnSalirLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 51, Short.MAX_VALUE)
        );

        btnImportar.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        btnImportar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ImgAnimacion/pdf-file_48.png"))); // NOI18N
        btnImportar.setBorder(null);
        btnImportar.setContentAreaFilled(false);
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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(488, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addComponent(jLabel3)
                        .addGap(6, 6, 6)
                        .addComponent(cmbProyecto, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addComponent(jLabel7)
                        .addGap(3, 3, 3)
                        .addComponent(c, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(jLabel8)
                        .addGap(6, 6, 6)
                        .addComponent(d, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(47, 47, 47)
                        .addComponent(jLabel9)
                        .addGap(6, 6, 6)
                        .addComponent(e, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(66, 66, 66)
                        .addComponent(jLabel10)
                        .addGap(6, 6, 6)
                        .addComponent(f, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(51, 51, 51)
                        .addComponent(jLabel11)
                        .addGap(6, 6, 6)
                        .addComponent(g, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(7, 7, 7)
                        .addComponent(jLabel12)
                        .addGap(6, 6, 6)
                        .addComponent(h, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(jLabel13)
                        .addGap(6, 6, 6)
                        .addComponent(i1, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(jLabel14)
                        .addGap(6, 6, 6)
                        .addComponent(j, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addComponent(jLabel15)
                        .addGap(6, 6, 6)
                        .addComponent(k, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(jLabel16)
                        .addGap(6, 6, 6)
                        .addComponent(l, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addComponent(jLabel18)
                        .addGap(6, 6, 6)
                        .addComponent(m, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addComponent(jLabel17)
                        .addGap(6, 6, 6)
                        .addComponent(n, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(41, 41, 41)
                        .addComponent(jLabel20)
                        .addGap(6, 6, 6)
                        .addComponent(o, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(35, 35, 35)
                        .addComponent(jLabel19)
                        .addGap(6, 6, 6)
                        .addComponent(p, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel22)
                        .addGap(6, 6, 6)
                        .addComponent(q, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(jLabel21)
                        .addGap(6, 6, 6)
                        .addComponent(r, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(jLabel24)
                        .addGap(6, 6, 6)
                        .addComponent(s, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(btnImportar, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(51, 51, 51)
                .addComponent(btnSalir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnSalir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 16, Short.MAX_VALUE)
                        .addComponent(btnImportar, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(jLabel3))
                    .addComponent(cmbProyecto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(9, 9, 9)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addComponent(c, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(3, 3, 3)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(jLabel8))
                    .addComponent(d, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(9, 9, 9)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(jLabel9))
                    .addComponent(e, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(jLabel10))
                    .addComponent(f, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(jLabel11))
                    .addComponent(g, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(jLabel12))
                    .addComponent(h, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(jLabel13))
                    .addComponent(i1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addComponent(jLabel14))
                    .addComponent(j, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(k, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15))
                .addGap(22, 22, 22)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(jLabel16))
                    .addComponent(l, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(jLabel18))
                    .addComponent(m, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(jLabel17))
                    .addComponent(n, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(jLabel20))
                    .addComponent(o, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(jLabel19))
                    .addComponent(p, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(jLabel22))
                    .addComponent(q, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(jLabel21))
                    .addComponent(r, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(jLabel24))
                    .addComponent(s, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cKeyTyped
        char c = evt.getKeyChar();
        if (c < '0' || c > '9')
        evt.consume();
    }//GEN-LAST:event_cKeyTyped

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

    private void kKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_kKeyTyped

    private void lKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_lKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_lKeyTyped

    private void mKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_mKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_mKeyTyped

    private void nKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_nKeyTyped

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

    private void jLabel27MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel27MouseClicked
        dispose();
    }//GEN-LAST:event_jLabel27MouseClicked

    private void jLabel27MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel27MouseEntered
        btnSalir.setBackground(Color.red);
    }//GEN-LAST:event_jLabel27MouseEntered

    private void jLabel27MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel27MouseExited
         btnSalir.setBackground(Color.white);
    }//GEN-LAST:event_jLabel27MouseExited

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
                String opc = "";
                String fin = "";
                String buscar = ".";
                boolean band = true;
                int aux = 0;
                int cont = 0;
                char arreglo[] = archivo[i].getName().toCharArray();
                for (int j = 0; j < archivo[i].getName().length(); j++) {
                String letra = String.valueOf(arreglo[j]);
                    if (buscar.equalsIgnoreCase(letra)) {
                    fin = archivo[i].getName().substring(0,j);
                    }
                }
                
                 while(opc.equals("")){
                 opc = JOptionPane.showInputDialog(this, "Ingresa cantidad para plano " + fin+ "");
                 }
                if (!opc.equals("")) {
                    try {
                         Connection con = null;
                         Conexion con1 = new Conexion();
                         con = con1.getConnection();
                        String sql = "insert into Planos (Plano, Proyecto, Estado, Prioridad, Cantidad, Integracion, Fresadora, Torno, Cnc, Mazak, Rectificado, Soldadura, Ensamble, Revision, Descripcion, Material, Dureza, Maquina, NoEnsamble, Tratamiento,Dimension,Pdf) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                         PreparedStatement pst = con.prepareStatement(sql);

                        pst.setString(1, fin);//Plano
                        pst.setString(2, (String) cmbProyecto.getSelectedItem());//Proyecto
                        pst.setString(3, "");
                        pst.setString(4, "0");
                        pst.setString(5, opc);//Cantidad
                        pst.setString(6, "0");
                        pst.setString(7, "0");
                        pst.setString(8, "0");
                        pst.setString(9, "0");
                        pst.setString(10, "0");
                        pst.setString(11, "0");
                        pst.setString(12, "0");
                        pst.setString(13, "0");
                        pst.setString(14, "0");
                        pst.setString(15, "0");
                        pst.setString(16, "0");
                        pst.setString(17, "0");
                        pst.setString(18, "0");
                        pst.setString(19, "0");
                        pst.setString(20, "0");
                        pst.setString(21, "0");
                        pst.setBytes(22, pe);

                        int n = pst.executeUpdate();
                        if (n > 0) {
                            char comillas = '"';
                            try {
                                Runtime.getRuntime().exec("cmd /c copy " + comillas + archivo[i] + comillas + " \\\\100.100.200.10\\bd\\OC\\PDF_Planos\\" + cmbProyecto.getSelectedItem());
                                    Runtime.getRuntime().exec("cmd /c copy " + comillas + archivo[i] + comillas + " \\\\100.100.200.10\\bd\\OC\\PDF_Planos\\" + cmbProyecto.getSelectedItem());

                                     } catch (IOException ex) {
                                         JOptionPane.showMessageDialog(this, "error: " + ex);
                                    }
                                }

                             } catch (SQLException e) {
                                 JOptionPane.showMessageDialog(this, "ERROR AL GUARDAR DATOS: " + e, "ERROR", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    }
                }

    }//GEN-LAST:event_btnImportarActionPerformed

    /**
     * @param args the command line arguments
     */
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
            java.util.logging.Logger.getLogger(dis.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(dis.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(dis.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(dis.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new dis().setVisible(true);
            }
        });
    }

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
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JTextField k;
    private javax.swing.JTextField l;
    private javax.swing.JTextField m;
    private javax.swing.JTextField n;
    private javax.swing.JTextField o;
    private javax.swing.JTextField p;
    private javax.swing.JTextField q;
    private javax.swing.JTextField r;
    private javax.swing.JTextField s;
    // End of variables declaration//GEN-END:variables
}
