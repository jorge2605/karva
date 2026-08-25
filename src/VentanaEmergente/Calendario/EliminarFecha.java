package VentanaEmergente.Calendario;

import Conexiones.Conexion;
import VentanaEmergente.Calendario.Modelo.PropiedadesFechas;
import com.mxrck.autocompleter.TextAutoCompleter;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class EliminarFecha extends java.awt.Dialog {

    TextAutoCompleter au;
    
    public final void addProyectos(){
        try{
            au = new TextAutoCompleter(txtProyecto);
            Connection con;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            String sql = "select Proyecto, id from proyectos order by id desc";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
                au.addItem(rs.getString("Proyecto"));
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "Error: " + e,"Error",JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public final void ocultar(){
        txtProyecto.setVisible(false);
        panelFecha.setVisible(false);
        cmbDepartamento.setVisible(false);
    }
    
    public void crearFecha(String proyecto, String color, String f1, String f2, String descripcion, String id, Connection con){
        JButton boton = new javax.swing.JButton();
        boton.setFont(new java.awt.Font("Roboto Medium", 0, 12)); // NOI18N
        boton.setForeground(new java.awt.Color(51, 51, 51));
        String[] rgb = color.split(",");
        int r = Integer.parseInt(rgb[0]);
        int g = Integer.parseInt(rgb[1]);
        int b = Integer.parseInt(rgb[2]);
        boton.addActionListener((ActionEvent e) -> {
            int opc = JOptionPane.showConfirmDialog(this, "Estas seguro de eliminar esta fecha?");
            if(opc == 0){
                try {
                    String sql = "update agenda set Estatus = ? where IdAgenda = ?";
                    PreparedStatement pst = con.prepareStatement(sql);

                    pst.setString(1, "Cancelado");
                    pst.setString(2, id);

                    int n = pst.executeUpdate();

                    if(n > 0){
                        JOptionPane.showMessageDialog(this, "Fecha eliminada exitosamente");
                        notificarActualizacion();
                        String sql1 = "";
                        switch (cmbBuscar.getSelectedIndex()) {
                            case 1:
                                if (fecha.getDate() != null){
                                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                                    String fec = sdf.format(fecha.getDate());
                                    sql1 = "select * from agenda where FechaInicio like '" + fec + "'";
                                }
                                break;
                            case 2:
                                sql1 = "select * from agenda where Departamento like '" + cmbDepartamento.getSelectedItem().toString() + "'";
                                break;
                            case 3:
                                sql1 = "select * from agenda where Proyecto like '" + txtProyecto.getText() + "'";
                                break;
                            default:
                                break;
                        }
                        verFechas(sql1 + " and Estatus != 'Cancelado'");
                    }

                } catch (SQLException ex) {
                    Logger.getLogger(EliminarFecha.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        boton.setText("<html>\n<div style='width:200px; border-bottom: 5px solid white;'>"
                + "<p style='font-size:10px; font-weight: 700;'>" + proyecto + "</p>"
                //+ "<p>" + descripcion + "</p>"
                + "<span style='color: rgb(150,150,150); font-size: 10;'>" + f1 + "</span>"
                + "<span style='color: rgb(" + color + "); font-size: 10;'>" + f2 + "</span>"
                + "</div>"
                + "</html>");
        
        boton.setBorder(javax.swing.BorderFactory.createCompoundBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 3, true), 
                javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createMatteBorder(0, 5, 0, 0, new java.awt.Color(r,g,b)), 
                        javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 3))));
        boton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel10.add(boton);
    }
    
    public final void verFechas(String sql){
        try{
            Connection con;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            jPanel10.removeAll();
            repaint();
            revalidate();
            while(rs.next()){
                String proyecto = rs.getString("Proyecto");
                String id = rs.getString("IdAgenda");
                String color = rs.getString("Color");
                String f1 = rs.getString("FechaInicio");
                String f2 = rs.getString("FechaFin");
                String descripcion = rs.getString("Descripcion");
                crearFecha(proyecto, color, f1, f2, descripcion, id,con);
            }
            revalidate();
            repaint();
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "Error: "+e,"Error",JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public EliminarFecha(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        ocultar();
        addProyectos();
    }
    
    private Runnable onFechasActualizadasListener;

    public void setOnFechasActualizadasListener(Runnable listener) {
        this.onFechasActualizadasListener = listener;
    }

    private void notificarActualizacion() {
        if (onFechasActualizadasListener != null) {
            onFechasActualizadasListener.run();
        }
    }

    public void setBuscarProyectoInicial(String proyecto){
        txtProyecto.setText(proyecto);
        // Simular búsqueda inmediata como si se escribiera en el campo
        verFechas("select * from agenda where Proyecto like '" + proyecto + "%' and Estatus != 'Cancelado'");
        cmbBuscar.setSelectedIndex(3); // seleccionar modo "Proyecto"
        txtProyecto.setVisible(true);
    }
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        cmbBuscar = new javax.swing.JComboBox<>();
        txtProyecto = new javax.swing.JTextField();
        cmbDepartamento = new javax.swing.JComboBox<>();
        panelFecha = new javax.swing.JPanel();
        fecha = new com.toedter.calendar.JDateChooser();
        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel10 = new javax.swing.JPanel();

        setPreferredSize(new java.awt.Dimension(460, 668));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                closeDialog(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new java.awt.BorderLayout());

        jLabel1.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 102, 204));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Eliminar Fecha");
        jPanel1.add(jLabel1, java.awt.BorderLayout.NORTH);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new java.awt.BorderLayout());

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jLabel2.setFont(new java.awt.Font("Roboto", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(51, 51, 51));
        jLabel2.setText("Buscar por:");
        jPanel3.add(jLabel2);

        cmbBuscar.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        cmbBuscar.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccionar", "Fecha de inicio", "Departamento", "Proyecto" }));
        cmbBuscar.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(204, 204, 204)));
        cmbBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbBuscarActionPerformed(evt);
            }
        });
        jPanel3.add(cmbBuscar);

        txtProyecto.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        txtProyecto.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(204, 204, 204)));
        txtProyecto.setPreferredSize(new java.awt.Dimension(200, 25));
        txtProyecto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtProyectoActionPerformed(evt);
            }
        });
        jPanel3.add(txtProyecto);

        cmbDepartamento.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        cmbDepartamento.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccionar", "DISEÑO", "HERRAMENTISTA", "COMPRAS", "INTEGRACION" }));
        cmbDepartamento.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(204, 204, 204)));
        cmbDepartamento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbDepartamentoActionPerformed(evt);
            }
        });
        jPanel3.add(cmbDepartamento);

        panelFecha.setBackground(new java.awt.Color(255, 255, 255));

        fecha.setBackground(new java.awt.Color(255, 255, 255));
        fecha.setDateFormatString("yyyy-MM-dd");
        fecha.setPreferredSize(new java.awt.Dimension(150, 25));
        panelFecha.add(fecha);

        jButton1.setBackground(new java.awt.Color(0, 102, 204));
        jButton1.setFont(new java.awt.Font("Roboto", 1, 10)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Buscar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        panelFecha.add(jButton1);

        jPanel3.add(panelFecha);

        jPanel2.add(jPanel3, java.awt.BorderLayout.PAGE_START);

        jScrollPane1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        jPanel10.setBackground(new java.awt.Color(255, 255, 255));
        jPanel10.setLayout(new javax.swing.BoxLayout(jPanel10, javax.swing.BoxLayout.Y_AXIS));
        jScrollPane1.setViewportView(jPanel10);

        jPanel2.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jPanel1.add(jPanel2, java.awt.BorderLayout.CENTER);

        add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void closeDialog(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_closeDialog
        setVisible(false);
        dispose();
    }//GEN-LAST:event_closeDialog

    private void cmbBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbBuscarActionPerformed
        ocultar();
        switch (cmbBuscar.getSelectedIndex()) {
            case 1:
                panelFecha.setVisible(true);
                break;
            case 2:
                cmbDepartamento.setVisible(true);
                break;
            case 3:
                txtProyecto.setVisible(true);
                break;
            default:
                break;
        }
        revalidate();
        repaint();
    }//GEN-LAST:event_cmbBuscarActionPerformed

    private void txtProyectoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtProyectoActionPerformed
        verFechas("select * from agenda where Proyecto like '" + txtProyecto.getText() + "%' and Estatus like 'Nuevo'");
    }//GEN-LAST:event_txtProyectoActionPerformed

    private void cmbDepartamentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbDepartamentoActionPerformed
        if (cmbDepartamento.getSelectedIndex() == 0){
            JOptionPane.showMessageDialog(this, "Debes seleccionar algun departamento","Advertencia",JOptionPane.WARNING_MESSAGE);
        } else {
            verFechas("select * from agenda where Departamento like '" + cmbDepartamento.getSelectedItem().toString() + "' and Estatus like 'Nuevo'");
        }
    }//GEN-LAST:event_cmbDepartamentoActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if (fecha.getDate() != null){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String fec = sdf.format(fecha.getDate());
            verFechas("select * from agenda where FechaInicio like '" + fec + "' and Estatus like 'Nuevo'");
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                EliminarFecha dialog = new EliminarFecha(new java.awt.Frame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> cmbBuscar;
    private javax.swing.JComboBox<String> cmbDepartamento;
    private com.toedter.calendar.JDateChooser fecha;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel panelFecha;
    private javax.swing.JTextField txtProyecto;
    // End of variables declaration//GEN-END:variables
}
