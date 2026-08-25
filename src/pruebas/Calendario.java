package pruebas;

import componentes.PanelMultiColor;
import Conexiones.Conexion;
import VentanaEmergente.Calendario.AgregarFechas;
import VentanaEmergente.Calendario.EliminarFecha;
import VentanaEmergente.Calendario.InfoAgenda;
import VentanaEmergente.Calendario.Modelo.PropiedadesFechas;
import com.mxrck.autocompleter.TextAutoCompleter;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.TextStyle;
import java.util.Date;
import java.util.Locale;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import com.toedter.calendar.JYearChooser;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.BorderFactory;

public class Calendario extends javax.swing.JInternalFrame {

    public String numEmpleado;
    public String nomEmpleado;
    public String departamento;
    Stack<PropiedadesFechas> props;
    
    public void limpiarPanel(){
        panelAno.removeAll();
        revalidate();
        repaint();
    }
    
    public int getDay(String day){
        switch(day){
            case "LUNES":
                return 0;
            case "MARTES":
                return 1;
            case "MIÉRCOLES":
                return 2;
            case "JUEVES":
                return 3;
            case "VIERNES":
                return 4;
            case "SÁBADO":
                return 5;
            case "DOMINGO":
                return 6;
            default:
                return -1;
        }
    }
    
    public boolean compararDias(String fecha, String inicio, String termino){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date d1 = sdf.parse(fecha);
            Date d2 = sdf.parse(inicio);
            Date d3 = sdf.parse(termino);
            return (d1.equals(d2) || d1.after(d2)) && (d1.equals(d3) || d1.before(d3));
        } catch (ParseException ex) {
            Logger.getLogger(Calendario.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public void crearFecha(String dpto, String proyecto, String color, String f1, String f2, String descripcion, String id, JFrame f){
        JButton boton = new javax.swing.JButton();
        boton.setFont(new java.awt.Font("Roboto Medium", 0, 12)); // NOI18N
        boton.setForeground(new java.awt.Color(51, 51, 51));
        String[] rgb = color.split(",");
        int r = Integer.parseInt(rgb[0]);
        int g = Integer.parseInt(rgb[1]);
        int b = Integer.parseInt(rgb[2]);
        boton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                InfoAgenda info = new InfoAgenda(null, true, nomEmpleado);
                info.lblId.setText(id);
                info.lblId.setForeground(new Color(r,g,b));
                //info.setColores(new Color(r,g,b));
                info.verInformacion(id);
                info.panelColor.setBackground(new Color(r,g,b));
                info.setLocationRelativeTo(f);
                boolean band = info.ver();
                if (band) {
                    verFechas(f);
                    pintarCalendario();
                }
            }
        });
        boton.setText("<html><div style='width:200px; border-bottom: 5px solid white;'>"
                + "<p style='font-size:10px; font-weight: 800; text-align: center;'>" + dpto + "</p>"
                + "<p style='font-size:10px; font-weight: 700;'>" + proyecto + "</p>"
                + "<span style='color: rgb(150,150,150); font-size: 10;'>" + f1 + " - </span>"
                + "<span style='color: rgb(" + color + "); font-size: 10;'>" + f2 + "</span>"
                + "</div>"
                + "</html>");
        
        boton.setBorder(javax.swing.BorderFactory.createCompoundBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 3, true), 
                javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createMatteBorder(0, 5, 0, 0, new java.awt.Color(r,g,b)), 
                        javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 3))));
        boton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel10.add(boton);
    }
    
    private int semanaSeleccionada = 0; // 0 = todas las semanas
    public final void verFechas(JFrame parentFrame){
        try {
            props = new Stack<>();

            Connection conexion;
            Conexion conexionDB = new Conexion();
            conexion = conexionDB.getConnection();
            Statement statement = conexion.createStatement();

            int annioActual = Ano.getYear();
            String proyectoIngresado = txtProyecto.getText().trim();

            String sql =
                "SELECT agenda.*, proyectos.Estatus AS EstatusProyecto " +
                "FROM agenda " +
                "JOIN proyectos ON agenda.Proyecto = proyectos.Proyecto " +
                "WHERE agenda.Estatus = 'Nuevo' " +
                "AND proyectos.Estatus NOT LIKE 'CERRADO' " +
                "AND proyectos.Estatus NOT LIKE 'DETENIDO' " +
                "AND proyectos.Estatus NOT LIKE 'REPROSESAMIENTO' " +
                "AND ( " +
                "YEAR(agenda.FechaInicio) = " + annioActual + " OR " +
                "YEAR(agenda.FechaFin) = " + annioActual + " OR " +
                "(agenda.FechaInicio < '" + annioActual + "-01-01' " +
                "AND agenda.FechaFin > '" + annioActual + "-12-31') " +
                ") ";

            if (!proyectoIngresado.isEmpty()) {
                sql += "AND agenda.Proyecto LIKE '%" + proyectoIngresado + "%' ";
            }

            if (semanaSeleccionada != 0) {
                sql +=
                    "AND ( " +
                    "WEEK(agenda.FechaInicio, 1) = " + semanaSeleccionada + " OR " +
                    "WEEK(agenda.FechaFin, 1) = " + semanaSeleccionada + " OR " +
                    "(agenda.FechaInicio < DATE_ADD(STR_TO_DATE('" + annioActual + "-01-01', '%Y-%m-%d'), INTERVAL " + ((semanaSeleccionada - 1) * 7) + " DAY) " +
                    "AND agenda.FechaFin > DATE_ADD(STR_TO_DATE('" + annioActual + "-01-01', '%Y-%m-%d'), INTERVAL " + (semanaSeleccionada * 7) + " DAY)) " +
                    ") ";
            }

            sql += "ORDER BY agenda.FechaInicio ASC";

            ResultSet resultSet = statement.executeQuery(sql);

            jPanel10.removeAll();
            repaint();
            revalidate();

            while (resultSet.next()) {
                String proyecto = resultSet.getString("Proyecto");
                String idAgenda = resultSet.getString("IdAgenda");
                String color = resultSet.getString("Color");
                String fechaInicio = resultSet.getString("FechaInicio");
                String fechaFin = resultSet.getString("FechaFin");
                String descripcion = resultSet.getString("Descripcion");
                String departamento = resultSet.getString("Departamento");

                props.push(new PropiedadesFechas(departamento, fechaInicio, fechaFin, color));
                crearFecha(departamento, proyecto, color, fechaInicio, fechaFin, descripcion, idAgenda, parentFrame);
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(
                this,
                "Error al cargar fechas: " + ex.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
        }

    }
    
    public void actualizarSemanasCombo() {
        // Quitar temporalmente los listeners 
        ActionListener[] actions = cbSemana.getActionListeners(); 
        for (ActionListener al : actions) { 
            cbSemana.removeActionListener(al); 
        } 
        cbSemana.removeAllItems(); 
        cbSemana.addItem("Todas las semanas"); // 0 
        for (int i = 1; i <= 52; i++) { 
            cbSemana.addItem(String.valueOf(i)); 
        } 
        cbSemana.setSelectedIndex(0); // Regresar listeners 
        for (ActionListener al : actions) { 
            cbSemana.addActionListener(al); 
        }
    }
    
    public final void setEmpleado(){
        try{
            Connection con;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            String sql = "select * from registroempleados where NumEmpleado like '" + numEmpleado + "'";
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
                departamento = rs.getString("Puesto");
                nomEmpleado = rs.getString("Nombre") + " " + rs.getString("Apellido");
                String sup = rs.getString("Super");
                if(sup != null){
                    if(sup.equals("SI")){
                        panelAdministrador.setVisible(true);
                    }else{
                        panelAdministrador.setVisible(false);
                    }
                }
            }
            System.out.println(departamento);
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "Error: " + e,"Error",JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public JPanel getPanel(int i){
        switch (i) {
            case 0: return enero;
            case 1: return febrero;
            case 2: return marzo;
            case 3: return abril;
            case 4: return mayo;
            case 5: return junio;
            case 6: return julio;
            case 7: return agosto;
            case 8: return septiembre;
            case 9: return octubre;
            case 10: return noviembre;
            case 11: return diciembre;
            default: return null;
        }
    }
    
    public int getDia(String dia){
        switch(dia){
            case "domingo": return 0;
            case "lunes": return 1;
            case "martes": return 2;
            case "miércoles": return 3;
            case "jueves": return 4;
            case "viernes": return 5;
            case "sábado": return 6;
            default: return -1;
        }
    }
    
    public void agregarDias(JPanel pan){
        JLabel label = new JLabel();
        label.setFont(new Font("Roboto",Font.BOLD, 12));
        label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label.setText("D");
        pan.add(label);
        label = new JLabel();
        label.setFont(new Font("Roboto",Font.BOLD, 12));
        label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label.setText("L");
        pan.add(label);
        label = new JLabel();
        label.setFont(new Font("Roboto",Font.BOLD, 12));
        label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label.setText("M");
        pan.add(label);
        label = new JLabel();
        label.setFont(new Font("Roboto",Font.BOLD, 12));
        label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label.setText("M");
        pan.add(label);
        label = new JLabel();
        label.setFont(new Font("Roboto",Font.BOLD, 12));
        label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label.setText("J");
        pan.add(label);
        label = new JLabel();
        label.setFont(new Font("Roboto",Font.BOLD, 12));
        label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label.setText("V");
        pan.add(label);
        label = new JLabel();
        label.setFont(new Font("Roboto",Font.BOLD, 12));
        label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label.setText("S");
        pan.add(label);
    }
    
    public final void pintarCalendario(){
        enero.removeAll();
        febrero.removeAll();
        marzo.removeAll();
        abril.removeAll();
        mayo.removeAll();
        junio.removeAll();
        julio.removeAll();
        agosto.removeAll();
        septiembre.removeAll();
        octubre.removeAll();
        noviembre.removeAll();
        diciembre.removeAll();
        for (int i = 0; i < 12; i++) {
            int year = Ano.getYear();
            LocalDate firstDay = LocalDate.of(year, (i + 1), 1);
            String dayOfWeek = firstDay.getDayOfWeek().getDisplayName(TextStyle.FULL, new Locale("es", "ES"));
            int dia = getDia(dayOfWeek);
            YearMonth yearMonth = YearMonth.of(year, (i + 1));
            int totalDias = yearMonth.lengthOfMonth();
            JPanel panel = getPanel(i);
            //panel.setBorder(BorderFactory.createEmptyBorder(6, 6, 6, 6));
            int cont = 0;
            agregarDias(panel);
            int sab = 0;
            for (int j = 0; j < 42; j++) {
                sab++;
                if(j >= dia && j < (totalDias + dia)){
                    cont++;
                    String fec = year + "-" + (i + 1) + "-" + cont;
                    Stack<Color> pila = new Stack<>();
                    for (int k = 0; k < props.size(); k++) {
                        if(compararDias(fec, props.get(k).getFechaInicio(), props.get(k).getFechaFinal())){
                            int r = Integer.parseInt(props.get(k).getColor().split(",")[0]);
                            int g = Integer.parseInt(props.get(k).getColor().split(",")[1]);
                            int b = Integer.parseInt(props.get(k).getColor().split(",")[2]);
                            pila.push(new Color(r,g,b));
                        }
                    }
                    if(pila.isEmpty()){
                        pila.push(Color.white);
                    }
                    PanelMultiColor pan = new PanelMultiColor(pila);
                    pan.setOpaque(true); // que muestre su color
                    pan.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
    
                    pan.setBackground(new java.awt.Color(255, 255, 45, 0));
                    JLabel label = new javax.swing.JLabel();
                    label.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
                    label.setText(String.valueOf(cont));
                    pan.add(label);
                    panel.add(pan);
                    revalidate();
                    repaint();
                }else{
                    JPanel pan = new javax.swing.JPanel();
                    pan.setOpaque(false);
                    //pan.setBackground(Color.white); // <-- SIEMPRE blanco
                    panel.add(pan);
                }
            }
        }
    }
    
    public Calendario(String numEmpleado, JFrame f) {
        initComponents();
        this.numEmpleado = numEmpleado;
        setEmpleado();
        agregarAutoCompleter(); //método de autocompletado
        verFechas(f);
        pintarCalendario();
        
        actualizarSemanasCombo();
        cbSemana.addActionListener(evt -> { 
            JFrame frame = (JFrame) JOptionPane.getFrameForComponent(this); 
            txtProyecto.setText(""); 
            
            String seleccion = (String) cbSemana.getSelectedItem(); 
            if (seleccion.equals("Todas las semanas")) 
                { 
                    semanaSeleccionada = 0; 
                } 
            else { 
                semanaSeleccionada = Integer.parseInt(seleccion); 
            } 
            verFechas(frame); 
            pintarCalendario(); 
        });
        
        Ano.addPropertyChangeListener("year", evt -> {
            JFrame frame = (JFrame) JOptionPane.getFrameForComponent(this);

            semanaSeleccionada = 0;
            cbSemana.setSelectedIndex(0);
            txtProyecto.setText("");

            verFechas(frame);
            pintarCalendario();
        });
        jScrollPane1.getVerticalScrollBar().setUnitIncrement(15);
        ((javax.swing.plaf.basic.BasicInternalFrameUI) this.getUI()).setNorthPane(null);
    }
    
    public void agregarAutoCompleter() {
        try {
            TextAutoCompleter auto = new TextAutoCompleter(txtProyecto);
            Connection con;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(
                "SELECT DISTINCT Proyecto " +
                "FROM proyectos " +
                "WHERE Estatus NOT LIKE 'CERRADO' " +
                "AND Estatus NOT LIKE 'DETENIDO'"
            );


            while (rs.next()) {
                auto.addItem(rs.getString("Proyecto"));
            }

            // Cuando el usuario selecciona un proyecto del autocomplete
            txtProyecto.addKeyListener(new KeyAdapter() {
                @Override
                public void keyReleased(KeyEvent e) {
                    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                        JFrame f = (JFrame) JOptionPane.getFrameForComponent(Calendario.this);
                        actualizarSemanasCombo();
                        verFechas(f);
                        pintarCalendario();
                    }
                }
            });
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error cargando proyectos: " + e, "Error", JOptionPane.ERROR_MESSAGE);
        }
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
        panelCalendario = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtProyecto = new javax.swing.JTextField();
        cbSemana = new javax.swing.JComboBox<>();
        Ano = new com.toedter.calendar.JYearChooser();
        panelAdministrador = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jPanel13 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        panelMes = new javax.swing.JPanel();
        panelAno = new javax.swing.JPanel();
        enero = new componentes.PanelRound();
        febrero = new componentes.PanelRound();
        marzo = new componentes.PanelRound();
        abril = new componentes.PanelRound();
        mayo = new componentes.PanelRound();
        junio = new componentes.PanelRound();
        julio = new componentes.PanelRound();
        agosto = new componentes.PanelRound();
        septiembre = new componentes.PanelRound();
        octubre = new componentes.PanelRound();
        noviembre = new componentes.PanelRound();
        diciembre = new componentes.PanelRound();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel10 = new javax.swing.JPanel();
        jLabel53 = new javax.swing.JLabel();
        jLabel54 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();

        setBorder(null);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new java.awt.BorderLayout());

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setLayout(new java.awt.BorderLayout());

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 50, 5));

        jLabel12.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(0, 165, 252));
        jLabel12.setText("Calendario");
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

        panelCalendario.setBackground(new java.awt.Color(255, 255, 255));
        panelCalendario.setLayout(new java.awt.BorderLayout(20, 0));

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));
        jPanel8.setLayout(new java.awt.BorderLayout());

        jPanel11.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setText("Proyecto:");
        jPanel11.add(jLabel1);

        txtProyecto.setPreferredSize(new java.awt.Dimension(90, 22));
        jPanel11.add(txtProyecto);

        jPanel11.add(cbSemana);

        Ano.setBackground(new java.awt.Color(255, 255, 255));
        Ano.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                AnoPropertyChange(evt);
            }
        });
        jPanel11.add(Ano);

        jPanel8.add(jPanel11, java.awt.BorderLayout.CENTER);

        panelAdministrador.setBackground(new java.awt.Color(255, 255, 255));
        panelAdministrador.setLayout(new java.awt.BorderLayout());

        jLabel8.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(102, 102, 102));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("Administrador");
        panelAdministrador.add(jLabel8, java.awt.BorderLayout.NORTH);

        jPanel13.setBackground(new java.awt.Color(255, 255, 255));

        jButton1.setBackground(new java.awt.Color(0, 153, 0));
        jButton1.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Agregar fecha");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel13.add(jButton1);

        jButton2.setBackground(new java.awt.Color(153, 0, 0));
        jButton2.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("Eliminar fecha");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel13.add(jButton2);

        panelAdministrador.add(jPanel13, java.awt.BorderLayout.CENTER);

        jPanel8.add(panelAdministrador, java.awt.BorderLayout.PAGE_START);

        panelCalendario.add(jPanel8, java.awt.BorderLayout.PAGE_START);

        panelMes.setLayout(new java.awt.BorderLayout());

        panelAno.setBackground(new java.awt.Color(240, 240, 240));
        java.awt.GridBagLayout panelAnoLayout = new java.awt.GridBagLayout();
        panelAnoLayout.columnWeights = new double[] {1.0, 1.0, 1.0, 1.0};
        panelAnoLayout.rowWeights = new double[] {0.0, 1.0, 0.0, 1.0, 0.0, 1.0};
        panelAno.setLayout(panelAnoLayout);

        enero.setBackground(new java.awt.Color(255, 255, 255));
        enero.setRoundBottomLeft(60);
        enero.setRoundBottomRight(60);
        enero.setRoundTopLeft(60);
        enero.setRoundTopRight(60);
        enero.setLayout(new java.awt.GridLayout(7, 7));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panelAno.add(enero, gridBagConstraints);

        febrero.setBackground(new java.awt.Color(255, 255, 255));
        febrero.setRoundBottomLeft(60);
        febrero.setRoundBottomRight(60);
        febrero.setRoundTopLeft(60);
        febrero.setRoundTopRight(60);
        febrero.setLayout(new java.awt.GridLayout(7, 7));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panelAno.add(febrero, gridBagConstraints);

        marzo.setBackground(new java.awt.Color(255, 255, 255));
        marzo.setRoundBottomLeft(60);
        marzo.setRoundBottomRight(60);
        marzo.setRoundTopLeft(60);
        marzo.setRoundTopRight(60);
        marzo.setLayout(new java.awt.GridLayout(7, 7));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panelAno.add(marzo, gridBagConstraints);

        abril.setBackground(new java.awt.Color(255, 255, 255));
        abril.setRoundBottomLeft(60);
        abril.setRoundBottomRight(60);
        abril.setRoundTopLeft(60);
        abril.setRoundTopRight(60);
        abril.setLayout(new java.awt.GridLayout(7, 7));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panelAno.add(abril, gridBagConstraints);

        mayo.setBackground(new java.awt.Color(255, 255, 255));
        mayo.setRoundBottomLeft(60);
        mayo.setRoundBottomRight(60);
        mayo.setRoundTopLeft(60);
        mayo.setRoundTopRight(60);
        mayo.setLayout(new java.awt.GridLayout(7, 7));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panelAno.add(mayo, gridBagConstraints);

        junio.setBackground(new java.awt.Color(255, 255, 255));
        junio.setRoundBottomLeft(60);
        junio.setRoundBottomRight(60);
        junio.setRoundTopLeft(60);
        junio.setRoundTopRight(60);
        junio.setLayout(new java.awt.GridLayout(7, 7));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panelAno.add(junio, gridBagConstraints);

        julio.setBackground(new java.awt.Color(255, 255, 255));
        julio.setRoundBottomLeft(60);
        julio.setRoundBottomRight(60);
        julio.setRoundTopLeft(60);
        julio.setRoundTopRight(60);
        julio.setLayout(new java.awt.GridLayout(7, 7));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panelAno.add(julio, gridBagConstraints);

        agosto.setBackground(new java.awt.Color(255, 255, 255));
        agosto.setRoundBottomLeft(60);
        agosto.setRoundBottomRight(60);
        agosto.setRoundTopLeft(60);
        agosto.setRoundTopRight(60);
        agosto.setLayout(new java.awt.GridLayout(7, 7));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panelAno.add(agosto, gridBagConstraints);

        septiembre.setBackground(new java.awt.Color(255, 255, 255));
        septiembre.setRoundBottomLeft(60);
        septiembre.setRoundBottomRight(60);
        septiembre.setRoundTopLeft(60);
        septiembre.setRoundTopRight(60);
        septiembre.setLayout(new java.awt.GridLayout(7, 7));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panelAno.add(septiembre, gridBagConstraints);

        octubre.setBackground(new java.awt.Color(255, 255, 255));
        octubre.setRoundBottomLeft(60);
        octubre.setRoundBottomRight(60);
        octubre.setRoundTopLeft(60);
        octubre.setRoundTopRight(60);
        octubre.setLayout(new java.awt.GridLayout(7, 7));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panelAno.add(octubre, gridBagConstraints);

        noviembre.setBackground(new java.awt.Color(255, 255, 255));
        noviembre.setRoundBottomLeft(60);
        noviembre.setRoundBottomRight(60);
        noviembre.setRoundTopLeft(60);
        noviembre.setRoundTopRight(60);
        noviembre.setLayout(new java.awt.GridLayout(7, 7));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panelAno.add(noviembre, gridBagConstraints);

        diciembre.setBackground(new java.awt.Color(255, 255, 255));
        diciembre.setRoundBottomLeft(60);
        diciembre.setRoundBottomRight(60);
        diciembre.setRoundTopLeft(60);
        diciembre.setRoundTopRight(60);
        diciembre.setLayout(new java.awt.GridLayout(7, 7));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panelAno.add(diciembre, gridBagConstraints);

        jLabel22.setFont(new java.awt.Font("Roboto Black", 1, 14)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(0, 102, 204));
        jLabel22.setText("Febrero");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(4, 4, 4, 4);
        panelAno.add(jLabel22, gridBagConstraints);

        jLabel23.setFont(new java.awt.Font("Roboto Black", 1, 14)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(0, 102, 204));
        jLabel23.setText("Marzo");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(4, 4, 4, 4);
        panelAno.add(jLabel23, gridBagConstraints);

        jLabel24.setFont(new java.awt.Font("Roboto Black", 1, 14)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(0, 102, 204));
        jLabel24.setText("Abril");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(4, 4, 4, 4);
        panelAno.add(jLabel24, gridBagConstraints);

        jLabel25.setFont(new java.awt.Font("Roboto Black", 1, 14)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(0, 102, 204));
        jLabel25.setText("Mayo");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(4, 4, 4, 4);
        panelAno.add(jLabel25, gridBagConstraints);

        jLabel26.setFont(new java.awt.Font("Roboto Black", 1, 14)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(0, 102, 204));
        jLabel26.setText("Junio");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(4, 4, 4, 4);
        panelAno.add(jLabel26, gridBagConstraints);

        jLabel27.setFont(new java.awt.Font("Roboto Black", 1, 14)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(0, 102, 204));
        jLabel27.setText("Julio");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(4, 4, 4, 4);
        panelAno.add(jLabel27, gridBagConstraints);

        jLabel28.setFont(new java.awt.Font("Roboto Black", 1, 14)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(0, 102, 204));
        jLabel28.setText("Agosto");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(4, 4, 4, 4);
        panelAno.add(jLabel28, gridBagConstraints);

        jLabel29.setFont(new java.awt.Font("Roboto Black", 1, 14)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(0, 102, 204));
        jLabel29.setText("Septiembre");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.insets = new java.awt.Insets(4, 4, 4, 4);
        panelAno.add(jLabel29, gridBagConstraints);

        jLabel9.setFont(new java.awt.Font("Roboto Black", 1, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 102, 204));
        jLabel9.setText("Octubre");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.insets = new java.awt.Insets(4, 4, 4, 4);
        panelAno.add(jLabel9, gridBagConstraints);

        jLabel10.setFont(new java.awt.Font("Roboto Black", 1, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(0, 102, 204));
        jLabel10.setText("Noviembre");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.insets = new java.awt.Insets(4, 4, 4, 4);
        panelAno.add(jLabel10, gridBagConstraints);

        jLabel11.setFont(new java.awt.Font("Roboto Black", 1, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(0, 102, 204));
        jLabel11.setText("Diciembre");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.insets = new java.awt.Insets(4, 4, 4, 4);
        panelAno.add(jLabel11, gridBagConstraints);

        jLabel30.setFont(new java.awt.Font("Roboto Black", 1, 14)); // NOI18N
        jLabel30.setForeground(new java.awt.Color(0, 102, 204));
        jLabel30.setText("Enero");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(4, 4, 4, 4);
        panelAno.add(jLabel30, gridBagConstraints);

        panelMes.add(panelAno, java.awt.BorderLayout.CENTER);

        panelCalendario.add(panelMes, java.awt.BorderLayout.CENTER);

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));
        jPanel9.setLayout(new java.awt.BorderLayout());

        jScrollPane1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        jPanel10.setBackground(new java.awt.Color(255, 255, 255));
        jPanel10.setLayout(new javax.swing.BoxLayout(jPanel10, javax.swing.BoxLayout.Y_AXIS));

        jLabel53.setFont(new java.awt.Font("Roboto Medium", 0, 12)); // NOI18N
        jLabel53.setForeground(new java.awt.Color(51, 51, 51));
        jLabel53.setText("<html>\n<div style='width:200px; border-bottom: 5px solid white;'>\n<p style='font-size:10px; font-weight: 700;'>Proyecto</p>\n<p> Descripcion de mi proyecto</p>\n<span style='color: rgb(150,150,150); font-size: 10;'>2024-11-20</span>\n<span style='color: rgb(0,102,204); font-size: 10;'>2024-11-28</span>\n</div>\n</html>");
        jLabel53.setBorder(javax.swing.BorderFactory.createCompoundBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 3, true), javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createMatteBorder(0, 5, 0, 0, new java.awt.Color(0, 102, 204)), javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 3))));
        jLabel53.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel10.add(jLabel53);

        jLabel54.setFont(new java.awt.Font("Roboto Medium", 0, 12)); // NOI18N
        jLabel54.setForeground(new java.awt.Color(51, 51, 51));
        jLabel54.setText("<html>\n<p style='width:200px; border-bottom: 5px solid white;'>Proyecto 1012  activo para la recepcion de proyectos es la hora iundicada en su matricula</p>\n<span style='color: rgb(150,150,150); font-size: 10;'>2024-11-20</span>\n<span style='color: rgb(255,51,51); font-size: 10;'>2024-11-28</span>\n</html>");
        jLabel54.setBorder(javax.swing.BorderFactory.createCompoundBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 3, true), javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createMatteBorder(0, 5, 0, 0, new java.awt.Color(255, 51, 51)), javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 3))));
        jLabel54.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel10.add(jLabel54);

        jScrollPane1.setViewportView(jPanel10);

        jPanel9.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        panelCalendario.add(jPanel9, java.awt.BorderLayout.EAST);

        jPanel1.add(panelCalendario, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        jMenu1.setText("Ver por");

        jMenuItem1.setText("Mes");
        jMenu1.add(jMenuItem1);

        jMenuItem2.setText("Año                                      ");
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
        JFrame f = (JFrame) JOptionPane.getFrameForComponent(this);
        AgregarFechas calendario = new AgregarFechas(f,true,this.numEmpleado);
        calendario.setLocationRelativeTo(f);
        calendario.setOnFechasActualizadasListener(() -> { 
            verFechas(f);
            pintarCalendario();
        });
        calendario.setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        JFrame f = (JFrame) JOptionPane.getFrameForComponent(this);
        EliminarFecha eliminar = new EliminarFecha(f,true);
        eliminar.setLocationRelativeTo(f);
        eliminar.setOnFechasActualizadasListener(() -> {
            verFechas(f); // función para refrescar agenda
            pintarCalendario();
        });
        eliminar.setVisible(true);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        pintarCalendario();
        panelMes.removeAll();
        panelAno.setVisible(true);
        java.awt.GridBagLayout pan = (java.awt.GridBagLayout) panelAno.getLayout();
        pan.columnWeights = new double[] {1.0, 1.0, 1.0, 1.0};
        pan.rowWeights = new double[] {0.0, 1.0, 0.0, 1.0, 0.0, 1.0};
        panelAno.setLayout(pan);
        panelMes.add(panelAno, java.awt.BorderLayout.CENTER);
        revalidate();
        repaint();
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void AnoPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_AnoPropertyChange
        pintarCalendario();
    }//GEN-LAST:event_AnoPropertyChange


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.toedter.calendar.JYearChooser Ano;
    private componentes.PanelRound abril;
    private componentes.PanelRound agosto;
    private javax.swing.JComboBox<String> cbSemana;
    private componentes.PanelRound diciembre;
    private componentes.PanelRound enero;
    private componentes.PanelRound febrero;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private componentes.PanelRound julio;
    private componentes.PanelRound junio;
    private javax.swing.JLabel lblSalir;
    private componentes.PanelRound marzo;
    private componentes.PanelRound mayo;
    private componentes.PanelRound noviembre;
    private componentes.PanelRound octubre;
    private javax.swing.JPanel pan;
    private javax.swing.JPanel panelAdministrador;
    private javax.swing.JPanel panelAno;
    private javax.swing.JPanel panelCalendario;
    private javax.swing.JPanel panelMes;
    private javax.swing.JPanel panelSalir;
    private componentes.PanelRound septiembre;
    private javax.swing.JTextField txtProyecto;
    // End of variables declaration//GEN-END:variables
}
