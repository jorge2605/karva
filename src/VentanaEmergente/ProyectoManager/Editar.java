package VentanaEmergente.ProyectoManager;

import Conexiones.Conexion;
import VentanaEmergente.CambiarEstado.TerminarProyecto;
import java.awt.Color;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import pruebas.ProyectManager;
import scrollPane.ScrollBarCustom;

public class Editar extends javax.swing.JDialog {

    ProyectManager proy;
    public String numEmpleado;
    
    public final void cerrarFechaCalendario(String proyecto, Connection con) throws SQLException {
        String depa = "INTEGRACION";
        String sql = "update agenda set Estatus = ?, EmpleadoFin = ?, FechaTermino = ? where Proyecto = ? and Departamento = ?";
        PreparedStatement pst = con.prepareStatement(sql);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String fecha = sdf.format(new Date());

        pst.setString(1, "Terminado");
        pst.setString(2, numEmpleado);
        pst.setString(3, fecha);
        pst.setString(4, txtProyecto.getText());
        pst.setString(5, depa);

        int in = pst.executeUpdate();

        if(in < 1){      
            sql = "insert into agenda (Estatus , EmpleadoFin, FechaTermino, Proyecto, Departamento) values(?,?,?,?,?)";
            pst = con.prepareStatement(sql);

            pst.setString(1, "Terminado");
            pst.setString(2, numEmpleado);
            pst.setString(3, fecha);
            pst.setString(4, txtProyecto.getText());
            pst.setString(5, depa);
            
            in = pst.executeUpdate();

            if (in < 1) {
                JOptionPane.showMessageDialog(this, "Error al generar fecha", "Error", JOptionPane.ERROR_MESSAGE);
            }    
        }
    }
    
    public Editar(java.awt.Frame parent, boolean modal, ProyectManager proyect, String numEmpleado) {
        super(parent, modal);
        initComponents();
        this.numEmpleado = numEmpleado;
        this.setBackground(new Color(0,0,0,0));
        jPanel17.setBackground(new Color(0,0,0,0));
        btnSalir.setBackground(new Color(0,0,0,0));
        proy = proyect;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelRound1 = new scrollPane.PanelRound();
        jPanel17 = new javax.swing.JPanel();
        btnSalir = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        txtId = new rojeru_san.RSMTextFull();
        txtCotizacion = new rojeru_san.RSMTextFull();
        txtOrden = new rojeru_san.RSMTextFull();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtDescripcion = new javax.swing.JTextArea();
        jLabel2 = new javax.swing.JLabel();
        txtProyecto = new rojeru_san.RSMTextFull();
        cmbEstatus = new RSMaterialComponent.RSComboBoxMaterial();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        facturado = new toggle.ToggleButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtAcciones = new javax.swing.JTextArea();
        jLabel5 = new javax.swing.JLabel();
        btnGuardar = new rojeru_san.rsbutton.RSButtonRoundRipple();
        txtResponsable = new rojeru_san.RSMTextFull();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jcbMoneda = new RSMaterialComponent.RSComboBoxMaterial();
        txtValor = new RSComponentShade.RSFormatFieldShade();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);

        panelRound1.setBackground(new java.awt.Color(51, 51, 51));
        panelRound1.setRoundBottomLeft(100);
        panelRound1.setRoundBottomRight(100);
        panelRound1.setRoundTopLeft(100);
        panelRound1.setRoundTopRight(100);

        jPanel17.setBackground(new java.awt.Color(255, 255, 255));

        btnSalir.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("X");
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

        jPanel17.add(btnSalir);

        jLabel19.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 48)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(255, 255, 255));
        jLabel19.setText("Proyectos");

        txtId.setBackground(new java.awt.Color(51, 51, 51));
        txtId.setForeground(new java.awt.Color(255, 255, 255));
        txtId.setBordeColorFocus(new java.awt.Color(255, 255, 255));
        txtId.setBordeColorNoFocus(new java.awt.Color(102, 102, 102));
        txtId.setEnabled(false);
        txtId.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        txtId.setPlaceholder("Id");

        txtCotizacion.setBackground(new java.awt.Color(51, 51, 51));
        txtCotizacion.setForeground(new java.awt.Color(255, 255, 255));
        txtCotizacion.setBordeColorFocus(new java.awt.Color(255, 255, 255));
        txtCotizacion.setBordeColorNoFocus(new java.awt.Color(102, 102, 102));
        txtCotizacion.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        txtCotizacion.setPlaceholder("Cotizacion");

        txtOrden.setBackground(new java.awt.Color(51, 51, 51));
        txtOrden.setForeground(new java.awt.Color(255, 255, 255));
        txtOrden.setBordeColorFocus(new java.awt.Color(255, 255, 255));
        txtOrden.setBordeColorNoFocus(new java.awt.Color(102, 102, 102));
        txtOrden.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        txtOrden.setPlaceholder("Orden de compra");

        jScrollPane1.setBorder(null);

        txtDescripcion.setBackground(new java.awt.Color(51, 51, 51));
        txtDescripcion.setColumns(20);
        txtDescripcion.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtDescripcion.setForeground(new java.awt.Color(255, 255, 255));
        txtDescripcion.setLineWrap(true);
        txtDescripcion.setRows(5);
        txtDescripcion.setWrapStyleWord(true);
        jScrollPane1.setViewportView(txtDescripcion);

        jLabel2.setBackground(new java.awt.Color(255, 255, 255));
        jLabel2.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Facturado:");

        txtProyecto.setBackground(new java.awt.Color(51, 51, 51));
        txtProyecto.setForeground(new java.awt.Color(255, 255, 255));
        txtProyecto.setBordeColorFocus(new java.awt.Color(255, 255, 255));
        txtProyecto.setBordeColorNoFocus(new java.awt.Color(102, 102, 102));
        txtProyecto.setEnabled(false);
        txtProyecto.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        txtProyecto.setPlaceholder("Proyecto");

        cmbEstatus.setBackground(new java.awt.Color(51, 51, 51));
        cmbEstatus.setForeground(new java.awt.Color(255, 255, 255));
        cmbEstatus.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "DETENIDO", "EN PROCESO", "CERRADO", "REPROSESAMIENTO" }));
        cmbEstatus.setColorMaterial(new java.awt.Color(255, 255, 255));

        jLabel3.setBackground(new java.awt.Color(255, 255, 255));
        jLabel3.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Descripcion:");

        jLabel4.setBackground(new java.awt.Color(255, 255, 255));
        jLabel4.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Estatus:");

        jScrollPane2.setBorder(null);

        txtAcciones.setBackground(new java.awt.Color(51, 51, 51));
        txtAcciones.setColumns(20);
        txtAcciones.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtAcciones.setForeground(new java.awt.Color(255, 255, 255));
        txtAcciones.setLineWrap(true);
        txtAcciones.setRows(5);
        txtAcciones.setWrapStyleWord(true);
        jScrollPane2.setViewportView(txtAcciones);

        jLabel5.setBackground(new java.awt.Color(255, 255, 255));
        jLabel5.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Acciones:");

        btnGuardar.setText("Guardar");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });

        txtResponsable.setBackground(new java.awt.Color(51, 51, 51));
        txtResponsable.setForeground(new java.awt.Color(255, 255, 255));
        txtResponsable.setBordeColorFocus(new java.awt.Color(255, 255, 255));
        txtResponsable.setBordeColorNoFocus(new java.awt.Color(51, 51, 51));
        txtResponsable.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        txtResponsable.setPlaceholder("Responsable");

        jLabel6.setBackground(new java.awt.Color(255, 255, 255));
        jLabel6.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Valor:");

        jLabel7.setBackground(new java.awt.Color(255, 255, 255));
        jLabel7.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Moneda:");

        jcbMoneda.setBackground(new java.awt.Color(51, 51, 51));
        jcbMoneda.setForeground(new java.awt.Color(255, 255, 255));
        jcbMoneda.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "MXN", "DLLS" }));

        txtValor.setBackground(new java.awt.Color(51, 51, 51));
        txtValor.setForeground(new java.awt.Color(255, 255, 255));
        txtValor.setCaretColor(new java.awt.Color(255, 255, 255));
        txtValor.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        txtValor.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter()));
        txtValor.setPhColor(new java.awt.Color(255, 255, 255));
        txtValor.setPlaceholder("Valor");

        javax.swing.GroupLayout panelRound1Layout = new javax.swing.GroupLayout(panelRound1);
        panelRound1.setLayout(panelRound1Layout);
        panelRound1Layout.setHorizontalGroup(
            panelRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRound1Layout.createSequentialGroup()
                .addGap(151, 151, 151)
                .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(panelRound1Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(panelRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelRound1Layout.createSequentialGroup()
                        .addGroup(panelRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtId, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtCotizacion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap())
                    .addGroup(panelRound1Layout.createSequentialGroup()
                        .addGap(134, 134, 134)
                        .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 277, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 58, Short.MAX_VALUE)
                        .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(40, 40, 40))))
            .addGroup(panelRound1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtProyecto, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtOrden, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane2)
                    .addComponent(txtResponsable, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(panelRound1Layout.createSequentialGroup()
                .addComponent(txtValor, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jcbMoneda, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(panelRound1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelRound1Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel2)
                        .addGap(37, 37, 37))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelRound1Layout.createSequentialGroup()
                        .addComponent(cmbEstatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(facturado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(46, 46, 46))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelRound1Layout.createSequentialGroup()
                        .addComponent(jScrollPane1)
                        .addContainerGap())
                    .addGroup(panelRound1Layout.createSequentialGroup()
                        .addGroup(panelRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelRound1Layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(jLabel5))
                            .addComponent(jLabel3))
                        .addGap(0, 0, Short.MAX_VALUE))))
            .addGroup(panelRound1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel7)
                .addGap(103, 103, 103))
        );
        panelRound1Layout.setVerticalGroup(
            panelRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRound1Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(panelRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel19)
                    .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCotizacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtOrden, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtProyecto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cmbEstatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(facturado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtResponsable, javax.swing.GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtValor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jcbMoneda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        getContentPane().add(panelRound1, java.awt.BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseClicked
        dispose();
    }//GEN-LAST:event_jLabel1MouseClicked

    private void jLabel1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseEntered
        btnSalir.setBackground(java.awt.Color.red);
    }//GEN-LAST:event_jLabel1MouseEntered

    private void jLabel1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseExited
        btnSalir.setBackground(new Color(0,0,0,0));
        revalidate();
        repaint();
    }//GEN-LAST:event_jLabel1MouseExited

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        if(txtId.getText().equals("")){
            JOptionPane.showMessageDialog(this, "DEBES SELECCIONAR UN ELEMENTO DE LA LISTA");
        }else{
            try{
                Connection con = null;
                Conexion con1 = new Conexion();
                con = con1.getConnection();
                String sql = "update proyectos set NumCotizacion = ?, OC = ?, Proyecto = ?, Descripcion = ?, Estatus = ?, Facturado = ?, Comentarios = ?, Responsable = ?, "
                        + "Costo = ?,  Moneda = ? where Id = ?";
                PreparedStatement pst = con.prepareStatement(sql);
                String fact;
                if(facturado.isSelected()){
                    fact = "SI";
                }else{
                    fact = "NO";
                }
                pst.setString(1, txtCotizacion.getText());
                pst.setString(2, txtOrden.getText());
                pst.setString(3, txtProyecto.getText());
                pst.setString(4, txtDescripcion.getText());
                pst.setString(5, cmbEstatus.getSelectedItem().toString());
                pst.setString(6, fact);
                pst.setString(7, txtAcciones.getText());
                pst.setString(8, txtResponsable.getText());
                pst.setString(9, txtValor.getText());
                pst.setString(10, jcbMoneda.getSelectedItem().toString());
                pst.setString(11, txtId.getText());

                int n = pst.executeUpdate();

                if(n > 0){
                    JOptionPane.showMessageDialog(null, "DATOS GUARDADOS");
                    if(cmbEstatus.getSelectedItem().equals("CERRADO")){
                        String sql1 = "select FechaCierre, Proyecto from proyectos where Proyecto like '"+txtProyecto.getText()+"'";
                        Statement st1 = con.createStatement();
                        ResultSet rs1 = st1.executeQuery(sql1);
                        String p = null;
                        while(rs1.next()){
                            p = rs1.getString("FechaCierre");
                        }
                        if(p == null){
                            String sql2 = "update proyectos set FechaCierre = ? where Proyecto = ?";
                            PreparedStatement pst2 = con.prepareStatement(sql2);

                            Date fec = new Date();
                            java.sql.Date data = new java.sql.Date(fec.getTime());

                            pst2.setDate(1, data);
                            pst2.setString(2, txtProyecto.getText());

                            pst2.executeUpdate();
                        }
                        cerrarFechaCalendario(txtProyecto.getText(), con);
                    }
                    proy.limpiarTabla();
                    proy.cargarDatosConFiltro();
                    this.dispose();
                }else{
                    JOptionPane.showMessageDialog(this, "NO SE GUARDARON LOS DATOS","ERROR",JOptionPane.ERROR_MESSAGE);
                }

            }catch(SQLException e){
                JOptionPane.showMessageDialog(this, "ERROR: "+e,"ERROR",JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_btnGuardarActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Editar dialog = new Editar(new javax.swing.JFrame(), true,null,"");
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
    public rojeru_san.rsbutton.RSButtonRoundRipple btnGuardar;
    private javax.swing.JPanel btnSalir;
    public RSMaterialComponent.RSComboBoxMaterial cmbEstatus;
    public toggle.ToggleButton facturado;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    public RSMaterialComponent.RSComboBoxMaterial jcbMoneda;
    private scrollPane.PanelRound panelRound1;
    public javax.swing.JTextArea txtAcciones;
    public rojeru_san.RSMTextFull txtCotizacion;
    public javax.swing.JTextArea txtDescripcion;
    public rojeru_san.RSMTextFull txtId;
    public rojeru_san.RSMTextFull txtOrden;
    public rojeru_san.RSMTextFull txtProyecto;
    public rojeru_san.RSMTextFull txtResponsable;
    public RSComponentShade.RSFormatFieldShade txtValor;
    // End of variables declaration//GEN-END:variables
}
