package VentanaEmergente.Diseño;

import Conexiones.Conexion;
import VentanaEmergente.Cotizacion.ConfUsuario;
import com.mxrck.autocompleter.TextAutoCompleter;
import java.awt.Image;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

public class AgregarPlano extends javax.swing.JDialog {

    public TextAutoCompleter au;

    public final void verGuardado() {
        Thread hilo = new Thread(() -> {
            try {
                lblguardado.setVisible(true);
                Thread.sleep(2000);
                lblguardado.setVisible(false);
            } catch (InterruptedException ex) {
                lblguardado.setVisible(false);
                Logger.getLogger(ConfUsuario.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        hilo.start();
    }

    public final void agregarEmpresas() {
        try {
            Connection con = new Conexion().getConnection();
            String sql = "select empresa, idtempletediseno from templetediseno order by empresa asc";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            au = new TextAutoCompleter(txtEmpresa);
            while (rs.next()) {
                au.addItem(rs.getString("empresa"));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al ver empresas: " + e, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public String getRuta(FileNameExtensionFilter filtro) {
        JFileChooser selector = new JFileChooser();
        selector.setFileFilter(filtro);
        int resultado = selector.showOpenDialog(this);
        if (resultado == JFileChooser.APPROVE_OPTION) {
            File carpeta = selector.getSelectedFile();
            String ruta = carpeta.getAbsolutePath();
            return ruta;
        }
        return null;
    }

    public final void insertarImagen(String url) {
        ImageIcon icon = new ImageIcon(url);
        Image imagen = icon.getImage();
        Image imagenEscalada = imagen.getScaledInstance(220, 220, Image.SCALE_SMOOTH);

        lblLogo.setIcon(new ImageIcon(imagenEscalada));
    }

    public final void agregarCoordenadas(JTextField txt, String depa) {
        if (txtPdf.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Debes seleccionar un plano para iniciar", "Advertencia", JOptionPane.WARNING_MESSAGE);
        } else {
            JFrame f = (JFrame) JOptionPane.getFrameForComponent(this);
            SeleccionarUbicaciones sel = new SeleccionarUbicaciones(f, true);
            sel.setLocationRelativeTo(f);
            if (!txt.getText().equals("")) {
                sel.lblCoo.setText(txt.getText());
            }
            sel.lblCoordenadas1.setText("Texto seleccionado para " + depa + ":");
            sel.agregarPanel(new File(txtPdf.getText()));
            sel.setVisible(true);
            txt.setText(sel.lblCoo.getText());
        }
    }

    public final void agregarArchivos(Connection con, String arc, File archivo) throws SQLException {
        String sql = "insert into archivostemplete (idtempletediseno, doc, archivo) values(?,?,?)";
        PreparedStatement pst = con.prepareStatement(sql);

        byte[] pe;
        if (archivo != null) {
            pe = new byte[(int) archivo.length()];
            try {
                InputStream input = new FileInputStream(archivo);
                input.read(pe);
            } catch (IOException e) {
            }

            pst.setString(1, lblId.getText());
            pst.setString(2, arc);
            pst.setBytes(3, pe);

            int n = pst.executeUpdate();

            if (n < 1) {
                JOptionPane.showMessageDialog(this, "Error al guardar archivo", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public final void guardarTemplete() {
        try {
            Connection con = new Conexion().getConnection();
            String sql = "insert into templetediseno (empresa, pdfplano, disenador, cliente, revision, parte, descripcion, material, dureza, ensamble, tratamiento, cantidad, logo, codigo) "
                    + "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            if (!lblId.getText().equals("")) {
                sql = "update templetediseno set empresa = ?, pdfplano = ?, disenador = ?, cliente = ?, revision = ?, parte = ?, descripcion = ?, material = ?,"
                        + " dureza = ?, ensamble = ?, tratamiento = ?, cantidad = ?, logo = ?, codigo = ? where idtempletediseno = ?";
            }
            PreparedStatement pst = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

            pst.setString(1, txtEmpresa.getText());
            pst.setString(2, txtPdf.getText());
            pst.setString(3, txtDisenador.getText());
            pst.setString(4, txtCliente.getText());
            pst.setString(5, txtRevision.getText());
            pst.setString(6, txtParte.getText());
            pst.setString(7, txtDescripcion.getText());
            pst.setString(8, txtMaterial.getText());
            pst.setString(9, txtDureza.getText());
            pst.setString(10, txtEnsamble.getText());
            pst.setString(11, txtTratamiento.getText());
            pst.setString(12, txtCantidad.getText());
            pst.setString(13, txtLogo.getText());
            pst.setString(14, txtCodigo.getText());
            if (!lblId.getText().equals("")) {
                pst.setString(15, lblId.getText());
            }

            int n = pst.executeUpdate();

            if (n > 0) {
                ResultSet rs = pst.getGeneratedKeys();
                if (rs.next()) {
                    int id = rs.getInt(1);
                    lblId.setText(String.valueOf(id));
                }
                String sql2 = "select idtempletediseno, doc from archivostemplete where idtempletediseno like '" + lblId.getText() + "'";
                Statement st = con.createStatement();
                ResultSet rs2 = st.executeQuery(sql2);
                Stack<String> pila = new Stack<>();
                while (rs2.next()) {
                    pila.add(rs2.getString("doc"));
                }
                if (pila.search("pdf") < 0) {
                    agregarArchivos(con, "pdf", new File(txtPdf.getText()));
                }
                if (pila.search("img") < 0) {
                    agregarArchivos(con, "img", new File(txtLogo.getText()));
                }
                verGuardado();
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al tratar de guardar templete: " + e, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public final void verEmpresa() {
        try {
            Connection con = new Conexion().getConnection();
            String sql = "select * from templetediseno where empresa like '" + txtEmpresa.getText() + "'";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                txtPdf.setText(rs.getString("pdfplano"));
                txtLogo.setText(rs.getString("logo"));
                txtDisenador.setText(rs.getString("disenador"));
                txtCliente.setText(rs.getString("cliente"));
                txtRevision.setText(rs.getString("revision"));
                txtParte.setText(rs.getString("parte"));
                txtDescripcion.setText(rs.getString("descripcion"));
                txtMaterial.setText(rs.getString("material"));
                txtDureza.setText(rs.getString("dureza"));
                txtEnsamble.setText(rs.getString("ensamble"));
                txtTratamiento.setText(rs.getString("tratamiento"));
                txtCantidad.setText(rs.getString("cantidad"));
                txtCodigo.setText(rs.getString("codigo"));
                txtEmpresa.setEnabled(false);
                lblId.setText(rs.getString("idtempletediseno"));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al ver datos de empresa:" + e, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public final void limpiarFormulario() {
        txtPdf.setText("");
        txtDisenador.setText("");
        txtCliente.setText("");
        txtRevision.setText("");
        txtParte.setText("");
        txtDescripcion.setText("");
        txtMaterial.setText("");
        txtDureza.setText("");
        txtEnsamble.setText("");
        txtTratamiento.setText("");
        txtCantidad.setText("");
        txtCodigo.setText("");
        lblId.setText("");
        txtLogo.setText("");
        txtEmpresa.setEnabled(true);
    }

    public AgregarPlano(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        agregarEmpresas();
        lblguardado.setVisible(false);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel1 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtEmpresa = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtLogo = new javax.swing.JTextField();
        btnLogo = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        txtPdf = new javax.swing.JTextField();
        btnPdf = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        txtDisenador = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtCliente = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtRevision = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtParte = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtDescripcion = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtMaterial = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtDureza = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txtEnsamble = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        txtTratamiento = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        txtCantidad = new javax.swing.JTextField();
        btnRevision = new javax.swing.JButton();
        btnParte = new javax.swing.JButton();
        btnDisenador = new javax.swing.JButton();
        btnCantidad = new javax.swing.JButton();
        btnCliente = new javax.swing.JButton();
        btnTratamiento = new javax.swing.JButton();
        btnEnsamble = new javax.swing.JButton();
        btnDureza = new javax.swing.JButton();
        btnMaterial = new javax.swing.JButton();
        btnDescripcion = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        lblLogo = new javax.swing.JLabel();
        lblEmpresa = new javax.swing.JLabel();
        lblguardado = new javax.swing.JLabel();
        lblId = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        txtCodigo = new javax.swing.JTextField();
        btnTratamiento1 = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(915, 592));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new java.awt.BorderLayout());

        jLabel12.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(0, 102, 204));
        jLabel12.setText("          Agregar templete");
        jPanel1.add(jLabel12, java.awt.BorderLayout.NORTH);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        java.awt.GridBagLayout jPanel2Layout = new java.awt.GridBagLayout();
        jPanel2Layout.columnWeights = new double[] {0.0, 1.0, 0.0, 0.0, 1.0, 0.0};
        jPanel2.setLayout(jPanel2Layout);

        jLabel1.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 51, 51));
        jLabel1.setText("Empresa:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 40, 0, 0);
        jPanel2.add(jLabel1, gridBagConstraints);

        txtEmpresa.setBackground(new java.awt.Color(255, 255, 255));
        txtEmpresa.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtEmpresa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEmpresaActionPerformed(evt);
            }
        });
        txtEmpresa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtEmpresaKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtEmpresaKeyReleased(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 40);
        jPanel2.add(txtEmpresa, gridBagConstraints);

        jLabel2.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(51, 51, 51));
        jLabel2.setText("Logo:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(0, 40, 0, 0);
        jPanel2.add(jLabel2, gridBagConstraints);

        txtLogo.setEditable(false);
        txtLogo.setBackground(new java.awt.Color(255, 255, 255));
        txtLogo.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jPanel2.add(txtLogo, gridBagConstraints);

        btnLogo.setBackground(new java.awt.Color(255, 255, 255));
        btnLogo.setText("...");
        btnLogo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLogoActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 40);
        jPanel2.add(btnLogo, gridBagConstraints);

        jLabel3.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(51, 51, 51));
        jLabel3.setText("Pdf de plano:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(0, 40, 0, 0);
        jPanel2.add(jLabel3, gridBagConstraints);

        txtPdf.setEditable(false);
        txtPdf.setBackground(new java.awt.Color(255, 255, 255));
        txtPdf.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jPanel2.add(txtPdf, gridBagConstraints);

        btnPdf.setBackground(new java.awt.Color(255, 255, 255));
        btnPdf.setText("...");
        btnPdf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPdfActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 40);
        jPanel2.add(btnPdf, gridBagConstraints);

        jLabel4.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(51, 51, 51));
        jLabel4.setText("Disenador: ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 3;
        gridBagConstraints.insets = new java.awt.Insets(0, 40, 0, 0);
        jPanel2.add(jLabel4, gridBagConstraints);

        txtDisenador.setEditable(false);
        txtDisenador.setBackground(new java.awt.Color(255, 255, 255));
        txtDisenador.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jPanel2.add(txtDisenador, gridBagConstraints);

        jLabel5.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(51, 51, 51));
        jLabel5.setText("Cliente:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.insets = new java.awt.Insets(0, 40, 0, 0);
        jPanel2.add(jLabel5, gridBagConstraints);

        txtCliente.setEditable(false);
        txtCliente.setBackground(new java.awt.Color(255, 255, 255));
        txtCliente.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jPanel2.add(txtCliente, gridBagConstraints);

        jLabel6.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(51, 51, 51));
        jLabel6.setText("Revision:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.insets = new java.awt.Insets(0, 40, 0, 0);
        jPanel2.add(jLabel6, gridBagConstraints);

        txtRevision.setEditable(false);
        txtRevision.setBackground(new java.awt.Color(255, 255, 255));
        txtRevision.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jPanel2.add(txtRevision, gridBagConstraints);

        jLabel7.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(51, 51, 51));
        jLabel7.setText("# de parte: ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.insets = new java.awt.Insets(0, 40, 0, 0);
        jPanel2.add(jLabel7, gridBagConstraints);

        txtParte.setEditable(false);
        txtParte.setBackground(new java.awt.Color(255, 255, 255));
        txtParte.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jPanel2.add(txtParte, gridBagConstraints);

        jLabel8.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(51, 51, 51));
        jLabel8.setText("Descripcion: ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.insets = new java.awt.Insets(0, 40, 0, 0);
        jPanel2.add(jLabel8, gridBagConstraints);

        txtDescripcion.setEditable(false);
        txtDescripcion.setBackground(new java.awt.Color(255, 255, 255));
        txtDescripcion.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jPanel2.add(txtDescripcion, gridBagConstraints);

        jLabel9.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(51, 51, 51));
        jLabel9.setText("Material:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.insets = new java.awt.Insets(0, 40, 0, 0);
        jPanel2.add(jLabel9, gridBagConstraints);

        txtMaterial.setEditable(false);
        txtMaterial.setBackground(new java.awt.Color(255, 255, 255));
        txtMaterial.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jPanel2.add(txtMaterial, gridBagConstraints);

        jLabel10.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(51, 51, 51));
        jLabel10.setText("Dureza:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.insets = new java.awt.Insets(0, 40, 0, 0);
        jPanel2.add(jLabel10, gridBagConstraints);

        txtDureza.setEditable(false);
        txtDureza.setBackground(new java.awt.Color(255, 255, 255));
        txtDureza.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jPanel2.add(txtDureza, gridBagConstraints);

        jLabel11.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(51, 51, 51));
        jLabel11.setText("# de ensamble:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.insets = new java.awt.Insets(0, 40, 0, 0);
        jPanel2.add(jLabel11, gridBagConstraints);

        txtEnsamble.setEditable(false);
        txtEnsamble.setBackground(new java.awt.Color(255, 255, 255));
        txtEnsamble.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jPanel2.add(txtEnsamble, gridBagConstraints);

        jLabel13.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(51, 51, 51));
        jLabel13.setText("Tratamiento:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.insets = new java.awt.Insets(0, 40, 0, 0);
        jPanel2.add(jLabel13, gridBagConstraints);

        txtTratamiento.setEditable(false);
        txtTratamiento.setBackground(new java.awt.Color(255, 255, 255));
        txtTratamiento.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jPanel2.add(txtTratamiento, gridBagConstraints);

        jLabel14.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(51, 51, 51));
        jLabel14.setText("Cantidad: ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.insets = new java.awt.Insets(0, 40, 0, 0);
        jPanel2.add(jLabel14, gridBagConstraints);

        txtCantidad.setEditable(false);
        txtCantidad.setBackground(new java.awt.Color(255, 255, 255));
        txtCantidad.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jPanel2.add(txtCantidad, gridBagConstraints);

        btnRevision.setBackground(new java.awt.Color(255, 255, 255));
        btnRevision.setText("...");
        btnRevision.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRevisionActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 40);
        jPanel2.add(btnRevision, gridBagConstraints);

        btnParte.setBackground(new java.awt.Color(255, 255, 255));
        btnParte.setText("...");
        btnParte.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnParteActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 40);
        jPanel2.add(btnParte, gridBagConstraints);

        btnDisenador.setBackground(new java.awt.Color(255, 255, 255));
        btnDisenador.setText("...");
        btnDisenador.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDisenadorActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 40);
        jPanel2.add(btnDisenador, gridBagConstraints);

        btnCantidad.setBackground(new java.awt.Color(255, 255, 255));
        btnCantidad.setText("...");
        btnCantidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCantidadActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 40);
        jPanel2.add(btnCantidad, gridBagConstraints);

        btnCliente.setBackground(new java.awt.Color(255, 255, 255));
        btnCliente.setText("...");
        btnCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClienteActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 40);
        jPanel2.add(btnCliente, gridBagConstraints);

        btnTratamiento.setBackground(new java.awt.Color(255, 255, 255));
        btnTratamiento.setText("...");
        btnTratamiento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTratamientoActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 40);
        jPanel2.add(btnTratamiento, gridBagConstraints);

        btnEnsamble.setBackground(new java.awt.Color(255, 255, 255));
        btnEnsamble.setText("...");
        btnEnsamble.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEnsambleActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 40);
        jPanel2.add(btnEnsamble, gridBagConstraints);

        btnDureza.setBackground(new java.awt.Color(255, 255, 255));
        btnDureza.setText("...");
        btnDureza.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDurezaActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 40);
        jPanel2.add(btnDureza, gridBagConstraints);

        btnMaterial.setBackground(new java.awt.Color(255, 255, 255));
        btnMaterial.setText("...");
        btnMaterial.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMaterialActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 40);
        jPanel2.add(btnMaterial, gridBagConstraints);

        btnDescripcion.setBackground(new java.awt.Color(255, 255, 255));
        btnDescripcion.setText("...");
        btnDescripcion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDescripcionActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 40);
        jPanel2.add(btnDescripcion, gridBagConstraints);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setPreferredSize(new java.awt.Dimension(250, 230));

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setLayout(new java.awt.BorderLayout());

        lblLogo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/compuesto.png"))); // NOI18N
        lblLogo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        lblLogo.setMaximumSize(new java.awt.Dimension(250, 250));
        lblLogo.setMinimumSize(new java.awt.Dimension(230, 64));
        lblLogo.setPreferredSize(new java.awt.Dimension(220, 220));
        jPanel4.add(lblLogo, java.awt.BorderLayout.CENTER);

        lblEmpresa.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        lblEmpresa.setForeground(new java.awt.Color(0, 102, 204));
        lblEmpresa.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jPanel4.add(lblEmpresa, java.awt.BorderLayout.PAGE_START);

        jPanel3.add(jPanel4);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.gridwidth = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 5;
        gridBagConstraints.ipady = 100;
        jPanel2.add(jPanel3, gridBagConstraints);

        lblguardado.setFont(new java.awt.Font("Trebuchet MS", 1, 12)); // NOI18N
        lblguardado.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IconoC/cheque_16.png"))); // NOI18N
        lblguardado.setText("Datos guardados correctamente");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.gridwidth = 12;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.PAGE_END;
        jPanel2.add(lblguardado, gridBagConstraints);

        lblId.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        lblId.setForeground(new java.awt.Color(51, 51, 51));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 40, 0, 0);
        jPanel2.add(lblId, gridBagConstraints);

        jLabel15.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(51, 51, 51));
        jLabel15.setText("Codigo de barras:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.insets = new java.awt.Insets(0, 40, 0, 0);
        jPanel2.add(jLabel15, gridBagConstraints);

        txtCodigo.setEditable(false);
        txtCodigo.setBackground(new java.awt.Color(255, 255, 255));
        txtCodigo.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        jPanel2.add(txtCodigo, gridBagConstraints);

        btnTratamiento1.setBackground(new java.awt.Color(255, 255, 255));
        btnTratamiento1.setText("...");
        btnTratamiento1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTratamiento1ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 40);
        jPanel2.add(btnTratamiento1, gridBagConstraints);

        jPanel1.add(jPanel2, java.awt.BorderLayout.CENTER);

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));

        jButton1.setBackground(new java.awt.Color(0, 102, 204));
        jButton1.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Guardar templete");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel5.add(jButton1);

        jButton2.setBackground(new java.awt.Color(255, 255, 255));
        jButton2.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jButton2.setForeground(new java.awt.Color(51, 51, 51));
        jButton2.setText("limpiar formulario");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel5.add(jButton2);

        jPanel1.add(jPanel5, java.awt.BorderLayout.PAGE_END);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnDisenadorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDisenadorActionPerformed
        agregarCoordenadas(txtDisenador, "Disenador");
    }//GEN-LAST:event_btnDisenadorActionPerformed

    private void btnLogoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLogoActionPerformed
        String ruta = getRuta(new FileNameExtensionFilter("JPG(*.jpg)", "jpg"));
        txtLogo.setText(ruta);
        insertarImagen(ruta);
    }//GEN-LAST:event_btnLogoActionPerformed

    private void btnPdfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPdfActionPerformed
        String ruta = getRuta(new FileNameExtensionFilter("PDF(*.pdf)", "pdf"));
        txtPdf.setText(ruta);
    }//GEN-LAST:event_btnPdfActionPerformed

    private void btnClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClienteActionPerformed
        agregarCoordenadas(txtCliente, "Cliente");
    }//GEN-LAST:event_btnClienteActionPerformed

    private void btnRevisionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRevisionActionPerformed
        agregarCoordenadas(txtRevision, "Revision");
    }//GEN-LAST:event_btnRevisionActionPerformed

    private void btnParteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnParteActionPerformed
        agregarCoordenadas(txtParte, "Parte");
    }//GEN-LAST:event_btnParteActionPerformed

    private void btnDescripcionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDescripcionActionPerformed
        agregarCoordenadas(txtDescripcion, "Descripcion");
    }//GEN-LAST:event_btnDescripcionActionPerformed

    private void btnMaterialActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMaterialActionPerformed
        agregarCoordenadas(txtMaterial, "Material");
    }//GEN-LAST:event_btnMaterialActionPerformed

    private void btnDurezaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDurezaActionPerformed
        agregarCoordenadas(txtDureza, "Dureza");
    }//GEN-LAST:event_btnDurezaActionPerformed

    private void btnEnsambleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEnsambleActionPerformed
        agregarCoordenadas(txtEnsamble, "Ensamble");
    }//GEN-LAST:event_btnEnsambleActionPerformed

    private void btnTratamientoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTratamientoActionPerformed
        agregarCoordenadas(txtTratamiento, "Tratamiento");
    }//GEN-LAST:event_btnTratamientoActionPerformed

    private void btnCantidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCantidadActionPerformed
        agregarCoordenadas(txtCantidad, "Cantidad");
    }//GEN-LAST:event_btnCantidadActionPerformed

    private void txtEmpresaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtEmpresaKeyPressed
    }//GEN-LAST:event_txtEmpresaKeyPressed

    private void txtEmpresaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtEmpresaKeyReleased
        lblEmpresa.setText(txtEmpresa.getText());
    }//GEN-LAST:event_txtEmpresaKeyReleased

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if (txtParte.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Debes seleccionar el numero de parte", "Advertencia", JOptionPane.WARNING_MESSAGE);
        } else if (txtCantidad.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Debes seleccionar la cantidad", "Advertencia", JOptionPane.WARNING_MESSAGE);
        } else if (txtCodigo.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Debes seleccionar la ubicacion de codigo de barras", "Advertencia", JOptionPane.WARNING_MESSAGE);
        } else {
            guardarTemplete();
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void txtEmpresaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEmpresaActionPerformed
        verEmpresa();
    }//GEN-LAST:event_txtEmpresaActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        limpiarFormulario();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void btnTratamiento1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTratamiento1ActionPerformed
        agregarCoordenadas(txtCodigo, "Codigo de barras");
    }//GEN-LAST:event_btnTratamiento1ActionPerformed

    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(() -> {
            AgregarPlano dialog = new AgregarPlano(new javax.swing.JFrame(), true);
            dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosing(java.awt.event.WindowEvent e) {
                    System.exit(0);
                }
            });
            dialog.setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCantidad;
    private javax.swing.JButton btnCliente;
    private javax.swing.JButton btnDescripcion;
    private javax.swing.JButton btnDisenador;
    private javax.swing.JButton btnDureza;
    private javax.swing.JButton btnEnsamble;
    private javax.swing.JButton btnLogo;
    private javax.swing.JButton btnMaterial;
    private javax.swing.JButton btnParte;
    private javax.swing.JButton btnPdf;
    private javax.swing.JButton btnRevision;
    private javax.swing.JButton btnTratamiento;
    private javax.swing.JButton btnTratamiento1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JLabel lblEmpresa;
    private javax.swing.JLabel lblId;
    private javax.swing.JLabel lblLogo;
    private javax.swing.JLabel lblguardado;
    private javax.swing.JTextField txtCantidad;
    private javax.swing.JTextField txtCliente;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JTextField txtDescripcion;
    private javax.swing.JTextField txtDisenador;
    private javax.swing.JTextField txtDureza;
    private javax.swing.JTextField txtEmpresa;
    private javax.swing.JTextField txtEnsamble;
    private javax.swing.JTextField txtLogo;
    private javax.swing.JTextField txtMaterial;
    private javax.swing.JTextField txtParte;
    private javax.swing.JTextField txtPdf;
    private javax.swing.JTextField txtRevision;
    private javax.swing.JTextField txtTratamiento;
    // End of variables declaration//GEN-END:variables
}
