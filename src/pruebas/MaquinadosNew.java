package pruebas;

import VentanaEmergente.Maquinados.NuevoPlano;
import java.awt.Color;
import java.util.Stack;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class MaquinadosNew extends javax.swing.JInternalFrame {

    private final javax.swing.Timer timer = new javax.swing.Timer(100, e -> actualizarCronometros());
    private long[] inicio = new long[4];
    private long[] acumulado = new long[4];
    private boolean[] corriendo = new boolean[4];
    private final String numEmpleado;
    private Stack<JLabel> pilaCronos = new Stack<>();

    private void iniciarCronometro(int numero) {
        if (!corriendo[numero]) {
            inicio[numero] = System.currentTimeMillis();
            corriendo[numero] = true;
            if (!timer.isRunning()) {
                timer.start();
            }
        }
    }

    private void pausarCronometro(int numero) {
        if (corriendo[numero]) {
            acumulado[numero] += System.currentTimeMillis() - inicio[numero];
            corriendo[numero] = false;
        }
        detenerTimerSiNoHayCronometros();
    }

    private void reanudarCronometro(int numero) {
        if (!corriendo[numero]) {
            inicio[numero] = System.currentTimeMillis();
            corriendo[numero] = true;
            if (!timer.isRunning()) {
                timer.start();
            }
        }
    }

    private void actualizarCronometros() {
        long ahora = System.currentTimeMillis();
        for (int i = 0; i < pilaCronos.size(); i++) {
            if (corriendo[i]) {
                long tiempo = acumulado[i] + (ahora - inicio[i]);
                pilaCronos.get(i).setText(formatearTiempo(tiempo));
            }
        }
    }

    private String formatearTiempo(long milisegundos) {
        long segundos = milisegundos / 1000;
        long horas = segundos / 3600;
        long minutos = (segundos % 3600) / 60;
        long segundosRestantes = segundos % 60;
        return String.format("%02d:%02d:%02d", horas, minutos, segundosRestantes);
    }

    private void detenerTimerSiNoHayCronometros() {
        for (boolean activo : corriendo) {
            if (activo) {
                return;
            }
        }
        timer.stop();
    }

    public final void crearPanelTiempo(String numEmpleado, String plano, String estacion, String tiempo, int i) {
        java.awt.GridBagConstraints gridBagConstraints = new java.awt.GridBagConstraints();
        scrollPane.PanelRound pnlInicial = new scrollPane.PanelRound();
        pnlInicial.setBackground(new java.awt.Color(240, 240, 240));
        pnlInicial.setRoundBottomLeft(20);
        pnlInicial.setRoundBottomRight(20);
        pnlInicial.setRoundTopLeft(20);
        pnlInicial.setRoundTopRight(20);

        java.awt.GridBagLayout panelRound1Layout = new java.awt.GridBagLayout();
        panelRound1Layout.columnWeights = new double[]{0.0, 1.0, 1.0, 1.0, 0.0, 0.0, 0.0};
        pnlInicial.setLayout(panelRound1Layout);

        //---------------empleado
        javax.swing.JLabel lblEmpleado = new javax.swing.JLabel();
        lblEmpleado.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        lblEmpleado.setForeground(new java.awt.Color(51, 51, 51));
        lblEmpleado.setText(numEmpleado);

        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(10, 8, 10, 8);
        pnlInicial.add(lblEmpleado, gridBagConstraints);

        //---------------- plano
        javax.swing.JLabel lblPlano = new javax.swing.JLabel();
        lblPlano.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        lblPlano.setForeground(new java.awt.Color(51, 51, 51));
        lblPlano.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblPlano.setText(plano);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(10, 8, 10, 8);
        pnlInicial.add(lblPlano, gridBagConstraints);

        //------------------estacion
        javax.swing.JLabel lblEstacion = new javax.swing.JLabel();
        lblEstacion.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        lblEstacion.setForeground(new java.awt.Color(0, 102, 204));
        lblEstacion.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblEstacion.setText(estacion);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(10, 8, 10, 8);
        pnlInicial.add(lblEstacion, gridBagConstraints);

        //---------------------cronometro
        javax.swing.JLabel lblCronometro = new javax.swing.JLabel();
        lblCronometro.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        lblCronometro.setForeground(new java.awt.Color(51, 51, 51));
        lblCronometro.setText(tiempo);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(10, 8, 10, 8);
        pnlInicial.add(lblCronometro, gridBagConstraints);

        pilaCronos.add(lblCronometro);
        //-------------boton play
        javax.swing.JButton btnPlay = new javax.swing.JButton();
        btnPlay.setBackground(new java.awt.Color(255, 255, 255));
        btnPlay.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/play.png"))); // NOI18N
        btnPlay.addActionListener((java.awt.event.ActionEvent evt) -> {
            iniciarCronometro(i);
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 6, 0, 6);
        pnlInicial.add(btnPlay, gridBagConstraints);

        //--------------boton pausa
        javax.swing.JButton btnPausa = new javax.swing.JButton();
        btnPausa.setBackground(new java.awt.Color(255, 255, 255));
        btnPausa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/pause.png"))); // NOI18N
        btnPausa.addActionListener((java.awt.event.ActionEvent evt) -> {
            pausarCronometro(i);
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 6, 0, 6);
        pnlInicial.add(btnPausa, gridBagConstraints);

        //-------------boton detener
        javax.swing.JButton btnDetener = new javax.swing.JButton();
        btnDetener.setBackground(new java.awt.Color(255, 255, 255));
        btnDetener.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/stop.png"))); // NOI18N
        btnDetener.addActionListener((java.awt.event.ActionEvent evt) -> {
            reanudarCronometro(1);
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 6, 0, 6);
        pnlInicial.add(btnDetener, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = i;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(4, 100, 4, 100);
        pnlPrincipal.add(pnlInicial, gridBagConstraints);
    }

    public final void limpiarPanel() {
        pnlPrincipal.removeAll();
        revalidate();
        repaint();
    }

    public MaquinadosNew(String numEmpleado) {
        initComponents();
        this.numEmpleado = numEmpleado;
        ((javax.swing.plaf.basic.BasicInternalFrameUI) this.getUI()).setNorthPane(null);
        limpiarPanel();
        jScrollPane1.getVerticalScrollBar().setUnitIncrement(15);
        crearPanelTiempo("61", "1721 12002522 103", "Torno", "00:00:00", 0);
        crearPanelTiempo("123", "1721 12002522 101", "Torno", "00:00:00", 1);
        crearPanelTiempo("1255", "1721 12002522 102", "Torno", "00:00:00", 2);
        crearPanelTiempo("1255", "1721 12002522 102", "Torno", "00:00:00", 3);
        crearPanelTiempo("1255", "1721 12002522 102", "Torno", "00:00:00", 4);
        crearPanelTiempo("1255", "1721 12002522 102", "Torno", "00:00:00", 5);
        crearPanelTiempo("1255", "1721 12002522 102", "Torno", "00:00:00", 6);
        crearPanelTiempo("1255", "1721 12002522 102", "Torno", "00:00:00", 7);
        crearPanelTiempo("1255", "1721 12002522 102", "Torno", "00:00:00", 8);
        crearPanelTiempo("1255", "1721 12002522 102", "Torno", "00:00:00", 9);
        crearPanelTiempo("1255", "1721 12002522 102", "Torno", "00:00:00", 10);
        crearPanelTiempo("1255", "1721 12002522 102", "Torno", "00:00:00", 11);
        crearPanelTiempo("1255", "1721 12002522 102", "Torno", "00:00:00", 12);
        crearPanelTiempo("1255", "1721 12002522 102", "Torno", "00:00:00", 13);
        crearPanelTiempo("1255", "1721 12002522 102", "Torno", "00:00:00", 14);
        crearPanelTiempo("1255", "1721 12002522 102", "Torno", "00:00:00", 15);
        crearPanelTiempo("1255", "1721 12002522 102", "Torno", "00:00:00", 16);
        crearPanelTiempo("1255", "1721 12002522 102", "Torno", "00:00:00", 17);
        crearPanelTiempo("1255", "1721 12002522 102", "Torno", "00:00:00", 18);
        crearPanelTiempo("1255", "1721 12002522 102", "Torno", "00:00:00", 19);
        crearPanelTiempo("1255", "1721 12002522 102", "Torno", "00:00:00", 20);
        crearPanelTiempo("1255", "1721 12002522 102", "Torno", "00:00:00", 21);
        crearPanelTiempo("1255", "1721 12002522 102", "Torno", "00:00:00", 22);
        crearPanelTiempo("1255", "1721 12002522 102", "Torno", "00:00:00", 23);
        crearPanelTiempo("1255", "1721 12002522 102", "Torno", "00:00:00", 24);
        crearPanelTiempo("1255", "1721 12002522 102", "Torno", "00:00:00", 25);
        crearPanelTiempo("1255", "1721 12002522 102", "Torno", "00:00:00", 26);
        crearPanelTiempo("1255", "1721 12002522 102", "Torno", "00:00:00", 27);
        crearPanelTiempo("1255", "1721 12002522 102", "Torno", "00:00:00", 28);
        crearPanelTiempo("1255", "1721 12002522 102", "Torno", "00:00:00", 29);
        crearPanelTiempo("1255", "1721 12002522 102", "Torno", "00:00:00", 30);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel1 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        pan = new javax.swing.JPanel();
        panelSalir = new javax.swing.JPanel();
        lblSalir = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        pnlPrincipal = new javax.swing.JPanel();
        panelRound1 = new scrollPane.PanelRound();
        jLabel5 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        lblTorno = new javax.swing.JLabel();
        lblCronoT = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        panelRound2 = new scrollPane.PanelRound();
        jLabel7 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        lblCnc = new javax.swing.JLabel();
        lblCronoC = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        panelRound3 = new scrollPane.PanelRound();
        jLabel11 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        lblRecti = new javax.swing.JLabel();
        lblCronoR = new javax.swing.JLabel();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        panelRound4 = new scrollPane.PanelRound();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        lblFresa = new javax.swing.JLabel();
        lblCronoF = new javax.swing.JLabel();
        jButton10 = new javax.swing.JButton();
        jButton11 = new javax.swing.JButton();
        jButton12 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new java.awt.BorderLayout());

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setLayout(new java.awt.BorderLayout());

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 50, 5));

        jLabel12.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(0, 165, 252));
        jLabel12.setText("Maquinados");
        jPanel5.add(jLabel12);

        jPanel4.add(jPanel5, java.awt.BorderLayout.CENTER);

        pan.setBackground(new java.awt.Color(255, 255, 255));

        panelSalir.setBackground(new java.awt.Color(255, 255, 255));

        lblSalir.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
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

        jPanel4.add(pan, java.awt.BorderLayout.EAST);

        jPanel1.add(jPanel4, java.awt.BorderLayout.NORTH);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new java.awt.BorderLayout());

        pnlPrincipal.setBackground(new java.awt.Color(255, 255, 255));
        java.awt.GridBagLayout jPanel2Layout = new java.awt.GridBagLayout();
        jPanel2Layout.columnWeights = new double[] {1.0};
        pnlPrincipal.setLayout(jPanel2Layout);

        panelRound1.setBackground(new java.awt.Color(240, 240, 240));
        panelRound1.setRoundBottomLeft(20);
        panelRound1.setRoundBottomRight(20);
        panelRound1.setRoundTopLeft(20);
        panelRound1.setRoundTopRight(20);
        java.awt.GridBagLayout panelRound1Layout = new java.awt.GridBagLayout();
        panelRound1Layout.columnWeights = new double[] {0.0, 1.0, 1.0, 0.0, 0.0, 0.0, 0.0};
        panelRound1.setLayout(panelRound1Layout);

        jLabel5.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(51, 51, 51));
        jLabel5.setText("100");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(10, 8, 10, 8);
        panelRound1.add(jLabel5, gridBagConstraints);

        jLabel8.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(51, 51, 51));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("1721 0255885 125");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(10, 8, 10, 8);
        panelRound1.add(jLabel8, gridBagConstraints);

        lblTorno.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        lblTorno.setForeground(new java.awt.Color(0, 102, 204));
        lblTorno.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTorno.setText("Torno");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(10, 8, 10, 8);
        panelRound1.add(lblTorno, gridBagConstraints);

        lblCronoT.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        lblCronoT.setForeground(new java.awt.Color(51, 51, 51));
        lblCronoT.setText("00:00:00");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(10, 8, 10, 8);
        panelRound1.add(lblCronoT, gridBagConstraints);

        jButton1.setBackground(new java.awt.Color(255, 255, 255));
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/play.png"))); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 6, 0, 6);
        panelRound1.add(jButton1, gridBagConstraints);

        jButton2.setBackground(new java.awt.Color(255, 255, 255));
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/pause.png"))); // NOI18N
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 6, 0, 6);
        panelRound1.add(jButton2, gridBagConstraints);

        jButton3.setBackground(new java.awt.Color(255, 255, 255));
        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/stop.png"))); // NOI18N
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 6, 0, 6);
        panelRound1.add(jButton3, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(4, 100, 4, 100);
        pnlPrincipal.add(panelRound1, gridBagConstraints);

        panelRound2.setBackground(new java.awt.Color(240, 240, 240));
        panelRound2.setRoundBottomLeft(20);
        panelRound2.setRoundBottomRight(20);
        panelRound2.setRoundTopLeft(20);
        panelRound2.setRoundTopRight(20);
        java.awt.GridBagLayout panelRound2Layout = new java.awt.GridBagLayout();
        panelRound2Layout.columnWeights = new double[] {0.0, 1.0, 1.0, 0.0, 0.0, 0.0, 0.0};
        panelRound2.setLayout(panelRound2Layout);

        jLabel7.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(51, 51, 51));
        jLabel7.setText("101");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(10, 8, 10, 8);
        panelRound2.add(jLabel7, gridBagConstraints);

        jLabel9.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(51, 51, 51));
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("1721 0255885 125");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(10, 8, 10, 8);
        panelRound2.add(jLabel9, gridBagConstraints);

        lblCnc.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        lblCnc.setForeground(new java.awt.Color(0, 102, 0));
        lblCnc.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblCnc.setText("Cnc");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(10, 8, 10, 8);
        panelRound2.add(lblCnc, gridBagConstraints);

        lblCronoC.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        lblCronoC.setForeground(new java.awt.Color(51, 51, 51));
        lblCronoC.setText("00:00:00");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(10, 8, 10, 8);
        panelRound2.add(lblCronoC, gridBagConstraints);

        jButton4.setBackground(new java.awt.Color(255, 255, 255));
        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/play.png"))); // NOI18N
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 6, 0, 6);
        panelRound2.add(jButton4, gridBagConstraints);

        jButton5.setBackground(new java.awt.Color(255, 255, 255));
        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/pause.png"))); // NOI18N
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 6, 0, 6);
        panelRound2.add(jButton5, gridBagConstraints);

        jButton6.setBackground(new java.awt.Color(255, 255, 255));
        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/stop.png"))); // NOI18N
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 6, 0, 6);
        panelRound2.add(jButton6, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(4, 100, 4, 100);
        pnlPrincipal.add(panelRound2, gridBagConstraints);

        panelRound3.setBackground(new java.awt.Color(240, 240, 240));
        panelRound3.setRoundBottomLeft(20);
        panelRound3.setRoundBottomRight(20);
        panelRound3.setRoundTopLeft(20);
        panelRound3.setRoundTopRight(20);
        panelRound3.setLayout(new java.awt.GridBagLayout());

        jLabel11.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(51, 51, 51));
        jLabel11.setText("102");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(10, 8, 10, 8);
        panelRound3.add(jLabel11, gridBagConstraints);

        jLabel13.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(51, 51, 51));
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setText("1721 0255885 125");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(10, 8, 10, 8);
        panelRound3.add(jLabel13, gridBagConstraints);

        lblRecti.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        lblRecti.setForeground(new java.awt.Color(153, 0, 153));
        lblRecti.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblRecti.setText("Rectificado");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(10, 8, 10, 8);
        panelRound3.add(lblRecti, gridBagConstraints);

        lblCronoR.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        lblCronoR.setForeground(new java.awt.Color(51, 51, 51));
        lblCronoR.setText("00:00:00");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(10, 8, 10, 8);
        panelRound3.add(lblCronoR, gridBagConstraints);

        jButton7.setBackground(new java.awt.Color(255, 255, 255));
        jButton7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/play.png"))); // NOI18N
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 6, 0, 6);
        panelRound3.add(jButton7, gridBagConstraints);

        jButton8.setBackground(new java.awt.Color(255, 255, 255));
        jButton8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/pause.png"))); // NOI18N
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 6, 0, 6);
        panelRound3.add(jButton8, gridBagConstraints);

        jButton9.setBackground(new java.awt.Color(255, 255, 255));
        jButton9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/stop.png"))); // NOI18N
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 6, 0, 6);
        panelRound3.add(jButton9, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(4, 100, 4, 100);
        pnlPrincipal.add(panelRound3, gridBagConstraints);

        panelRound4.setBackground(new java.awt.Color(240, 240, 240));
        panelRound4.setRoundBottomLeft(20);
        panelRound4.setRoundBottomRight(20);
        panelRound4.setRoundTopLeft(20);
        panelRound4.setRoundTopRight(20);
        panelRound4.setLayout(new java.awt.GridBagLayout());

        jLabel15.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(51, 51, 51));
        jLabel15.setText("101");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(10, 8, 10, 8);
        panelRound4.add(jLabel15, gridBagConstraints);

        jLabel16.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(51, 51, 51));
        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel16.setText("1721 0255885 125");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(10, 8, 10, 8);
        panelRound4.add(jLabel16, gridBagConstraints);

        lblFresa.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        lblFresa.setForeground(new java.awt.Color(153, 0, 51));
        lblFresa.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblFresa.setText("Fresado");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(10, 8, 10, 8);
        panelRound4.add(lblFresa, gridBagConstraints);

        lblCronoF.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        lblCronoF.setForeground(new java.awt.Color(51, 51, 51));
        lblCronoF.setText("00:00:00");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(10, 8, 10, 8);
        panelRound4.add(lblCronoF, gridBagConstraints);

        jButton10.setBackground(new java.awt.Color(255, 255, 255));
        jButton10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/play.png"))); // NOI18N
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 6, 0, 6);
        panelRound4.add(jButton10, gridBagConstraints);

        jButton11.setBackground(new java.awt.Color(255, 255, 255));
        jButton11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/pause.png"))); // NOI18N
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 6, 0, 6);
        panelRound4.add(jButton11, gridBagConstraints);

        jButton12.setBackground(new java.awt.Color(255, 255, 255));
        jButton12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/stop.png"))); // NOI18N
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 6, 0, 6);
        panelRound4.add(jButton12, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(4, 100, 4, 100);
        pnlPrincipal.add(panelRound4, gridBagConstraints);

        jScrollPane1.setViewportView(pnlPrincipal);

        jPanel2.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setForeground(new java.awt.Color(255, 255, 255));
        jPanel3.setLayout(new java.awt.GridBagLayout());

        jLabel1.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 51, 51));
        jLabel1.setText("Introducir codigo de barras");
        jPanel3.add(jLabel1, new java.awt.GridBagConstraints());

        jTextField1.setBackground(new java.awt.Color(255, 255, 255));
        jTextField1.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        jPanel3.add(jTextField1, gridBagConstraints);

        jPanel2.add(jPanel3, java.awt.BorderLayout.PAGE_START);

        jPanel1.add(jPanel2, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void lblSalirMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSalirMouseClicked
        dispose();
    }//GEN-LAST:event_lblSalirMouseClicked

    private void lblSalirMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSalirMouseEntered
        panelSalir.setBackground(Color.red);
        lblSalir.setForeground(Color.white);
    }//GEN-LAST:event_lblSalirMouseEntered

    private void lblSalirMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSalirMouseExited
        panelSalir.setBackground(Color.white);
        lblSalir.setForeground(Color.black);
    }//GEN-LAST:event_lblSalirMouseExited

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        iniciarCronometro(1);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        pausarCronometro(1);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        reanudarCronometro(1);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        iniciarCronometro(2);
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        pausarCronometro(2);
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        reanudarCronometro(2);
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        iniciarCronometro(3);
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        pausarCronometro(3);
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        reanudarCronometro(3);
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        iniciarCronometro(0);
    }//GEN-LAST:event_jButton10ActionPerformed

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
        pausarCronometro(0);
    }//GEN-LAST:event_jButton11ActionPerformed

    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed
        reanudarCronometro(0);
    }//GEN-LAST:event_jButton12ActionPerformed

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        JFrame f = (JFrame) JOptionPane.getFrameForComponent(this);
        NuevoPlano nuevo = new NuevoPlano(f, true);
        nuevo.setLocationRelativeTo(f);
        nuevo.setVisible(true);
    }//GEN-LAST:event_jTextField1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JLabel lblCnc;
    private javax.swing.JLabel lblCronoC;
    private javax.swing.JLabel lblCronoF;
    private javax.swing.JLabel lblCronoR;
    private javax.swing.JLabel lblCronoT;
    private javax.swing.JLabel lblFresa;
    private javax.swing.JLabel lblRecti;
    private javax.swing.JLabel lblSalir;
    private javax.swing.JLabel lblTorno;
    private javax.swing.JPanel pan;
    private scrollPane.PanelRound panelRound1;
    private scrollPane.PanelRound panelRound2;
    private scrollPane.PanelRound panelRound3;
    private scrollPane.PanelRound panelRound4;
    private javax.swing.JPanel panelSalir;
    private javax.swing.JPanel pnlPrincipal;
    // End of variables declaration//GEN-END:variables
}
