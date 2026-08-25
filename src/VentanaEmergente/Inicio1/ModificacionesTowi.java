package VentanaEmergente.Inicio1;

import Conexiones.ConexionChat;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class ModificacionesTowi extends javax.swing.JDialog {

    public String id;
    public java.awt.GridBagConstraints gridBagConstraints;
    
    public final void crearBotonDescripcion (String texto){
        JButton boton = new JButton();
        boton.setBackground(new java.awt.Color(51, 153, 255));
        boton.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        boton.setForeground(new java.awt.Color(255, 255, 255));
        boton.setText("<html> <p style='width:100%;'> " + texto + "");
        boton.setBorder(null);
        boton.setBorderPainted(false);
        boton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        boton.setFocusPainted(false);
        boton.setPreferredSize(new java.awt.Dimension(300, 50));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 22;
        gridBagConstraints.ipady = 22;
        gridBagConstraints.insets = new java.awt.Insets(4,2,4,2);
        scrollPane.PanelRound panelRound1 = new scrollPane.PanelRound();
        panelRound1.setBackground(new java.awt.Color(51, 153, 255));
        panelRound1.setRoundBottomLeft(15);
        panelRound1.setRoundBottomRight(15);
        panelRound1.setRoundTopLeft(15);
        panelRound1.setRoundTopRight(15);
        panelRound1.add(boton);
        panelPrincipal.add(panelRound1, gridBagConstraints);
    }
    
    public final void crearBoton(String text, int pos, String ida){
        JButton boton = new javax.swing.JButton();
        boton.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        boton.setForeground(new java.awt.Color(51, 51, 51));
        Color color;
        switch(text){
            case "En proceso":
                color = new Color(255,204,51);
                break;
            case "Terminado":
                color = new Color(102,255,0);
                break;
            case "Nuevo":
                color = new Color(51,153,255);
                break;
            case "Cancelado":
                color = new Color(153,0,0); 
                break;
            default:
                color = new Color(51,153,255);
        }
        boton.setBackground(color);
        boton.setName(ida);
        boton.setComponentPopupMenu(jPopupMenu1);
        boton.addMouseListener(new java.awt.event.MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                
            }
            @Override
            public void mousePressed(MouseEvent e) {}

            @Override
            public void mouseReleased(MouseEvent e) {}

            @Override
            public void mouseEntered(MouseEvent e) {
                id = boton.getName();
            }

            @Override
            public void mouseExited(MouseEvent e) {}
        });
        boton.setText("<html> <p style='width:100%;'>" + text);
        boton.setBorder(null);
        boton.setBorderPainted(false);
        boton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        boton.setFocusPainted(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = pos;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 30;
        gridBagConstraints.ipady = 15;
        gridBagConstraints.insets = new java.awt.Insets(4,2,4,2);
        scrollPane.PanelRound panelRound1 = new scrollPane.PanelRound();
        panelRound1.setBackground(new java.awt.Color(51, 153, 255));
        panelRound1.setRoundBottomLeft(15);
        panelRound1.setRoundBottomRight(15);
        panelRound1.setRoundTopLeft(15);
        panelRound1.setRoundTopRight(15);
        panelRound1.add(boton);
        panelPrincipal.add(panelRound1, gridBagConstraints);
    }
    
    public void setEstado(String estado, String id) {
        try{
            Connection con;
            ConexionChat con1 = new ConexionChat();
            con = con1.getConnection();
            String sql = "update modificaciones set Estado = ?, FechaTerminado = ? where idmodificaciones = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date d = new Date();
            String fecha = sdf.format(d);
            
            pst.setString(1, estado);
            pst.setString(2, fecha);
            pst.setString(3, id);
            
            int n = pst.executeUpdate();
            
            if(n > 0){
                JOptionPane.showMessageDialog(this, "Datos guardados correctamente");
                limpiarPantalla();
                addFila("select * from modificaciones where Estado != 'Cancelado' and Estado != 'Terminado' order by idmodificaciones desc");
            }
            
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "Error: " + e, "Error", JOptionPane.ERROR_MESSAGE);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ModificacionesTowi.class.getName()).log(Level.SEVERE, null, ex);
        }
    } 
    
    public void limpiarPantalla(){
        panelPrincipal.removeAll();
        revalidate();
        repaint();
        agregarTitulos();
    }
    
    public final void addFila(String sql){
        try{
            Connection con;
            ConexionChat con1 = new ConexionChat();
            con = con1.getConnection();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            limpiarPantalla();
            while(rs.next()){
                String id = rs.getString("idmodificaciones");
                String descripcion = rs.getString("Descripcion");
                String estado = rs.getString("Estado");
                String fecha = rs.getString("Fecha");
                String requisitor = rs.getString("Requisitor");
                crearBotonDescripcion( descripcion);
                crearBoton(estado, 2, id);
                crearBoton(fecha, 3, id);
                crearBoton(requisitor, 4, id);
            }
            revalidate();
            repaint();
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "Error: "+e,"Error",JOptionPane.ERROR_MESSAGE);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ModificacionesTowi.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void agregarTitulos(){
        //Ultimo Label
        jLabel9 = new javax.swing.JLabel();
        jLabel9.setText(" ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 100;
        panelPrincipal.add(jLabel9, gridBagConstraints);
        //DESCRIPCION
        JLabel descripcion = new javax.swing.JLabel();
        descripcion.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        descripcion.setForeground(new java.awt.Color(51, 51, 51));
        descripcion.setText("Descripcion");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 9;
        gridBagConstraints.ipady = 10;
        gridBagConstraints.insets = new java.awt.Insets(15, 15, 15, 15);
        panelPrincipal.add(descripcion, gridBagConstraints);
        //Estado
        JLabel estado = new javax.swing.JLabel();
        estado.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        estado.setForeground(new java.awt.Color(51, 51, 51));
        estado.setText("Estado");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 9;
        gridBagConstraints.ipady = 10;
        gridBagConstraints.insets = new java.awt.Insets(15, 15, 15, 15);
        panelPrincipal.add(estado, gridBagConstraints);
        //Fecha
        JLabel fecha = new javax.swing.JLabel();
        fecha.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        fecha.setForeground(new java.awt.Color(51, 51, 51));
        fecha.setText("Fecha");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 9;
        gridBagConstraints.ipady = 10;
        gridBagConstraints.insets = new java.awt.Insets(15, 15, 15, 15);
        panelPrincipal.add(fecha, gridBagConstraints);
        //Requisitor
        JLabel requisitor = new javax.swing.JLabel();
        requisitor.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        requisitor.setForeground(new java.awt.Color(51, 51, 51));
        requisitor.setText("Requisitor");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 9;
        gridBagConstraints.ipady = 10;
        gridBagConstraints.insets = new java.awt.Insets(15, 15, 15, 15);
        panelPrincipal.add(requisitor, gridBagConstraints);
    }
    
    public final void verificacion(String numEmpleado){
        if(numEmpleado.equals("61")){
            panelAdmin.setVisible(true);
            panelAdmin2.setVisible(true);
        }else{
            panelAdmin.setVisible(false);
            panelAdmin2.setVisible(false);
        }
    }
    
    public ModificacionesTowi(java.awt.Frame parent, boolean modal, String numEmpleado) {
        super(parent, modal);
        initComponents();
        verificacion(numEmpleado);
        addFila("select * from modificaciones where Estado != 'Cancelado' and Estado != 'Terminado' order by idmodificaciones desc");
        jScrollPane1.getVerticalScrollBar().setUnitIncrement(15);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPopupMenu1 = new javax.swing.JPopupMenu();
        progreso = new javax.swing.JMenuItem();
        terminado = new javax.swing.JMenuItem();
        cancelado = new javax.swing.JMenuItem();
        jPanel1 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        panelAdmin2 = new javax.swing.JPanel();
        jButton9 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        panelPrincipal = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        panelAdmin = new javax.swing.JPanel();
        jButton12 = new javax.swing.JButton();
        jButton13 = new javax.swing.JButton();

        progreso.setText("En progreso");
        progreso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                progresoActionPerformed(evt);
            }
        });
        jPopupMenu1.add(progreso);

        terminado.setText("Terminado");
        terminado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                terminadoActionPerformed(evt);
            }
        });
        jPopupMenu1.add(terminado);

        cancelado.setText("Cancelado");
        cancelado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                canceladoActionPerformed(evt);
            }
        });
        jPopupMenu1.add(cancelado);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(1322, 772));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setMaximumSize(new java.awt.Dimension(1220, 2147483647));
        jPanel1.setMinimumSize(new java.awt.Dimension(1220, 40));
        jPanel1.setLayout(new java.awt.BorderLayout());

        jLabel12.setFont(new java.awt.Font("Lexend", 1, 24)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(0, 102, 204));
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setText("Modificaciones Towi");
        jPanel1.add(jLabel12, java.awt.BorderLayout.NORTH);

        panelAdmin2.setBackground(new java.awt.Color(255, 255, 255));

        jButton9.setBackground(new java.awt.Color(255, 255, 255));
        jButton9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/cargarModificacion_32.png"))); // NOI18N
        jButton9.setBorderPainted(false);
        jButton9.setContentAreaFilled(false);
        jButton9.setFocusPainted(false);
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });
        panelAdmin2.add(jButton9);

        jPanel1.add(panelAdmin2, java.awt.BorderLayout.PAGE_END);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new java.awt.BorderLayout());

        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        panelPrincipal.setBackground(new java.awt.Color(255, 255, 255));
        panelPrincipal.setMaximumSize(new java.awt.Dimension(1230, 2147483647));
        java.awt.GridBagLayout jPanel2Layout = new java.awt.GridBagLayout();
        jPanel2Layout.columnWeights = new double[] {1.0, 1.0, 0.0, 0.0, 0.0};
        jPanel2Layout.rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0};
        panelPrincipal.setLayout(jPanel2Layout);

        jLabel9.setText(" ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 100;
        panelPrincipal.add(jLabel9, gridBagConstraints);

        jLabel1.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 51, 51));
        jLabel1.setText("Descripcion");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.ipadx = 9;
        gridBagConstraints.ipady = 10;
        gridBagConstraints.insets = new java.awt.Insets(15, 15, 15, 15);
        panelPrincipal.add(jLabel1, gridBagConstraints);

        jLabel2.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(51, 51, 51));
        jLabel2.setText("Estado");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 9;
        gridBagConstraints.ipady = 10;
        gridBagConstraints.insets = new java.awt.Insets(15, 15, 15, 15);
        panelPrincipal.add(jLabel2, gridBagConstraints);

        jLabel3.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(51, 51, 51));
        jLabel3.setText("Fecha");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 9;
        gridBagConstraints.ipady = 10;
        gridBagConstraints.insets = new java.awt.Insets(15, 15, 15, 15);
        panelPrincipal.add(jLabel3, gridBagConstraints);

        jLabel4.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(51, 51, 51));
        jLabel4.setText("Requisitor");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.ipadx = 9;
        gridBagConstraints.ipady = 10;
        gridBagConstraints.insets = new java.awt.Insets(15, 15, 15, 15);
        panelPrincipal.add(jLabel4, gridBagConstraints);

        jButton1.setBackground(new java.awt.Color(51, 153, 255));
        jButton1.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("hjkdrlgjkdslkjfhglkdjsh glkjds fhgkljdhslfkjghdlskfjhldkjf lkdjsh glk jdsflkg dlkg hdskljfg lkdskld gkljdh slkjgh ldsl dslkf glkdjs hfklhj dlskjh fgljkdsh flgkjh dlskfj ghldksjfh glkdjsfh glkjhlks djfghlkjsd fgkl");
        jButton1.setBorder(null);
        jButton1.setBorderPainted(false);
        jButton1.setComponentPopupMenu(jPopupMenu1);
        jButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton1.setFocusPainted(false);
        jButton1.setPreferredSize(new java.awt.Dimension(300, 50));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 22;
        gridBagConstraints.ipady = 22;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        panelPrincipal.add(jButton1, gridBagConstraints);

        jButton2.setBackground(new java.awt.Color(255, 204, 51));
        jButton2.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        jButton2.setForeground(new java.awt.Color(51, 51, 51));
        jButton2.setText("<html> <p style='width:100%;'> En proceso");
        jButton2.setBorder(null);
        jButton2.setBorderPainted(false);
        jButton2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton2.setFocusPainted(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 30;
        gridBagConstraints.ipady = 15;
        gridBagConstraints.insets = new java.awt.Insets(8, 8, 8, 8);
        panelPrincipal.add(jButton2, gridBagConstraints);

        jButton3.setBackground(new java.awt.Color(51, 153, 255));
        jButton3.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setText("<html> <p style='width:100%;'>06-09-2024</p></html>");
        jButton3.setBorder(null);
        jButton3.setBorderPainted(false);
        jButton3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton3.setFocusPainted(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 30;
        gridBagConstraints.ipady = 15;
        gridBagConstraints.insets = new java.awt.Insets(8, 8, 8, 8);
        panelPrincipal.add(jButton3, gridBagConstraints);

        jButton4.setBackground(new java.awt.Color(51, 153, 255));
        jButton4.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        jButton4.setForeground(new java.awt.Color(255, 255, 255));
        jButton4.setText("<html> <p style='width:100%;'>Jorge San");
        jButton4.setBorder(null);
        jButton4.setBorderPainted(false);
        jButton4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton4.setFocusPainted(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 30;
        gridBagConstraints.ipady = 15;
        gridBagConstraints.insets = new java.awt.Insets(8, 8, 8, 8);
        panelPrincipal.add(jButton4, gridBagConstraints);

        jButton5.setBackground(new java.awt.Color(51, 153, 255));
        jButton5.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        jButton5.setForeground(new java.awt.Color(255, 255, 255));
        jButton5.setText("<html> <p style='width:100%;'> Modificaciones de towi para la creacion de nuevos apartados para usuarios que desean nuevas habilidades y modificaciones para mejor comodidad y rendimiento de ellos en su respectivo departamento");
        jButton5.setBorder(null);
        jButton5.setBorderPainted(false);
        jButton5.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton5.setFocusPainted(false);
        jButton5.setPreferredSize(new java.awt.Dimension(300, 50));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 22;
        gridBagConstraints.ipady = 22;
        gridBagConstraints.insets = new java.awt.Insets(8, 8, 8, 8);
        panelPrincipal.add(jButton5, gridBagConstraints);

        jButton6.setBackground(new java.awt.Color(153, 0, 0));
        jButton6.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        jButton6.setForeground(new java.awt.Color(51, 51, 51));
        jButton6.setText("<html> <p style='width:100%;'> Terminado");
        jButton6.setBorder(null);
        jButton6.setBorderPainted(false);
        jButton6.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton6.setFocusPainted(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 30;
        gridBagConstraints.ipady = 15;
        gridBagConstraints.insets = new java.awt.Insets(8, 8, 8, 8);
        panelPrincipal.add(jButton6, gridBagConstraints);

        jButton7.setBackground(new java.awt.Color(51, 153, 255));
        jButton7.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        jButton7.setForeground(new java.awt.Color(255, 255, 255));
        jButton7.setText("<html> <p style='width:100%;'>06-09-2024");
        jButton7.setBorder(null);
        jButton7.setBorderPainted(false);
        jButton7.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton7.setFocusPainted(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 30;
        gridBagConstraints.ipady = 15;
        gridBagConstraints.insets = new java.awt.Insets(8, 8, 8, 8);
        panelPrincipal.add(jButton7, gridBagConstraints);

        jButton8.setBackground(new java.awt.Color(51, 153, 255));
        jButton8.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        jButton8.setForeground(new java.awt.Color(255, 255, 255));
        jButton8.setText("<html> <p style='width:100%;'> Karol Santacruz");
        jButton8.setBorder(null);
        jButton8.setBorderPainted(false);
        jButton8.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton8.setFocusPainted(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 30;
        gridBagConstraints.ipady = 15;
        gridBagConstraints.insets = new java.awt.Insets(8, 8, 8, 8);
        panelPrincipal.add(jButton8, gridBagConstraints);

        jScrollPane1.setViewportView(panelPrincipal);

        jPanel2.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        panelAdmin.setBackground(new java.awt.Color(230, 230, 230));

        jButton12.setBackground(new java.awt.Color(255, 255, 255));
        jButton12.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        jButton12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/ver_todo_16.png"))); // NOI18N
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });
        panelAdmin.add(jButton12);

        jButton13.setBackground(new java.awt.Color(255, 255, 255));
        jButton13.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        jButton13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/ver_menos_16.png"))); // NOI18N
        jButton13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton13ActionPerformed(evt);
            }
        });
        panelAdmin.add(jButton13);

        jPanel2.add(panelAdmin, java.awt.BorderLayout.PAGE_START);

        jPanel1.add(jPanel2, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        JFrame f = (JFrame) JOptionPane.getFrameForComponent(this);
        CargarModificacion cargar = new CargarModificacion(f, true);
        cargar.setLocationRelativeTo(f);
        cargar.setVisible(true);
    }//GEN-LAST:event_jButton9ActionPerformed

    private void progresoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_progresoActionPerformed
        setEstado("En proceso",id);
    }//GEN-LAST:event_progresoActionPerformed

    private void terminadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_terminadoActionPerformed
        setEstado("Terminado",id);
    }//GEN-LAST:event_terminadoActionPerformed

    private void canceladoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_canceladoActionPerformed
        setEstado("Cancelado",id);
    }//GEN-LAST:event_canceladoActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed
        addFila("select * from modificaciones order by idmodificaciones desc");
    }//GEN-LAST:event_jButton12ActionPerformed

    private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton13ActionPerformed
        addFila("select * from modificaciones where Estado != 'Cancelado' and Estado != 'Terminado' order by idmodificaciones desc");        
    }//GEN-LAST:event_jButton13ActionPerformed

    public static void main(String args[]) {
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                ModificacionesTowi dialog = new ModificacionesTowi(new javax.swing.JFrame(), true,"");
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
    private javax.swing.JMenuItem cancelado;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel panelAdmin;
    private javax.swing.JPanel panelAdmin2;
    private javax.swing.JPanel panelPrincipal;
    private javax.swing.JMenuItem progreso;
    private javax.swing.JMenuItem terminado;
    // End of variables declaration//GEN-END:variables
}
