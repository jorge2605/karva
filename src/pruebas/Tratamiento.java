package pruebas;

import Conexiones.Conexion;
import java.awt.Color;
import java.awt.Font;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

public class Tratamiento extends InternalFrameImagen {
public  String fechaInicio;
    public String o;
public void limpiarTabla(){
    DefaultTableModel Modelo = (DefaultTableModel) Tabla1.getModel();
        String titulos[] = {"PRIORIDAD","NUMERO DE PLANO","PROYECTO"};
        Modelo = new DefaultTableModel(null,titulos);
        Tabla1.setModel(Modelo);
    TableRowSorter<TableModel> elQueOrdena = new TableRowSorter<TableModel>(Modelo);
        Tabla1.setRowSorter(elQueOrdena);
    }


public void verDatos(){
    DefaultTableModel miModelo = (DefaultTableModel) Tabla1.getModel();
        try{
        Connection con = null;
        
        Conexion con1 = new Conexion();
        con = con1.getConnection();
        
        String datos[] = new String[8];
        String sql = "select * from Trata where Terminado like 'NO'";
        
        Statement st = con.createStatement(); 
        ResultSet rs = st.executeQuery(sql);
        
            while(rs.next()){
            datos[0]=rs.getString("Prioridad");
            datos[1]=rs.getString("Proyecto");
            datos[2]=rs.getString("Plano");
             miModelo.addRow(datos);
            }
            
        }catch(SQLException e){
        
            JOptionPane.showMessageDialog(this, "NO SE PUEDEN MOSTRAR LOS DATOS","ADVERTENCIA",JOptionPane.WARNING_MESSAGE);
        
        }
        TableRowSorter<TableModel> elQueOrdena = new TableRowSorter<TableModel>(miModelo);
        Tabla1.setRowSorter(elQueOrdena);
    }
public void fechaFinal(){
    Date fechaIn = new Date();
SimpleDateFormat fec = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        fechaInicio = fec.format(fechaIn);

}


    public void fecha(){
    
    Date fe = new Date();
    SimpleDateFormat nuevo = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
       String fec = nuevo.format(fe);
       
       txtFecha.setText(fec);
    
    }
    public void tabla(){
     JOptionPane.showMessageDialog(this, "DATOS GUARDADOS CORRECTAMENTE");
                    fecha();
                    txtFecha.setText(""); 
                    limpiarTabla();
                    verDatos();
                    
                    txtProyecto.setText("");
                    txtFecha.setText("");
    }
    public void borrar(){
    
        txtProyecto.setText("");
        txtFecha.setText("");
    
    }
    public Tratamiento() {
        initComponents();
        fechaFinal();
        verDatos();
        txtNumero.setEnabled(false);
        txtNombre.setEnabled(false);
        txtPuesto.setEnabled(false);
        setImagen("/Imagenes/fondo_1.jpg");
        
        Tabla1.setBackground(new Color(110,201,255));
        Tabla1.getTableHeader().setFont(new Font("Century Gothic", Font.PLAIN, 14));
        Tabla1.getTableHeader().setOpaque(false);
        Tabla1.getTableHeader().setBackground(new Color(0,0,0,0));
        Tabla1.getTableHeader().setForeground(Color.black);
        Tabla1.setRowHeight(25);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtFecha = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtProyecto = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtNumero = new javax.swing.JTextField();
        txtCodigo = new javax.swing.JTextField();
        btnGuardar = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        txtEmpleado = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        txtNombre = new javax.swing.JTextField();
        cmbEnviar = new javax.swing.JComboBox<>();
        txtPuesto = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        Tabla1 = new ColorRojo();
        jLabel9 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setText("Numero de plano");

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel8.setText("Puesto");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel4.setText("Hora de inicio");

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel6.setText("Nombre");

        txtFecha.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtFecha.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel7.setText("Numero de empleado");

        txtProyecto.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtProyecto.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        jLabel5.setFont(new java.awt.Font("Bell MT", 1, 60)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 102, 0));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("TRATAMIENTO");

        txtNumero.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        txtCodigo.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtCodigo.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtCodigo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodigoActionPerformed(evt);
            }
        });

        btnGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/enviar (1).png"))); // NOI18N
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setText("Datos del empleado");

        txtEmpleado.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtEmpleado.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtEmpleado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEmpleadoActionPerformed(evt);
            }
        });

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/actualizar (1).png"))); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        txtNombre.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        cmbEnviar.setMaximumRowCount(10);
        cmbEnviar.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "SELECCIONAR", "FRESADORA", "CNC", "TORNO", "CORTES", "ACABADOS", "CALIDAD", "LIBERAR" }));
        cmbEnviar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbEnviarActionPerformed(evt);
            }
        });

        txtPuesto.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        Tabla1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        Tabla1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "PRIORIDAD", "NUMERO DE PLANO", "PROYECTO"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        Tabla1.setIntercellSpacing(new java.awt.Dimension(0, 0));
        Tabla1.setRowHeight(25);
        Tabla1.setShowVerticalLines(false);
        Tabla1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Tabla1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(Tabla1);

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel9.setText("Datos de plano");

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel1.setText("Ingresa tu numero de empleado");

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel10.setText("Ingresa el codigo de barras");

        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/cerca (2).png"))); // NOI18N
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 494, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(257, 257, 257)
                        .addComponent(jLabel9))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addGroup(layout.createSequentialGroup()
                                                        .addGap(168, 168, 168)
                                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                            .addComponent(jLabel8)
                                                            .addComponent(jLabel6)))
                                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                        .addGap(86, 86, 86)
                                                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING)))
                                        .addGap(22, 22, 22)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(txtEmpleado, javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtCodigo, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 277, Short.MAX_VALUE)
                                            .addComponent(txtProyecto, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(txtFecha, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(txtNumero)
                                            .addComponent(txtNombre)
                                            .addComponent(txtPuesto)))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(21, 21, 21)
                                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 517, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(0, 43, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(262, 262, 262)
                                .addComponent(jLabel3)
                                .addGap(43, 43, 43)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cmbEnviar, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(36, 36, 36)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnGuardar)
                                    .addComponent(jButton1))))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton4)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jLabel9)
                        .addGap(24, 24, 24)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel2)
                                        .addGap(15, 15, 15)
                                        .addComponent(jLabel4))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(txtProyecto, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel3)
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel7)
                                    .addComponent(txtNumero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(31, 31, 31)
                                .addComponent(cmbEnviar, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(69, 69, 69)
                                        .addComponent(btnGuardar))
                                    .addComponent(jButton1))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(txtPuesto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(104, 104, 104))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtCodigoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodigoActionPerformed
        Date fe = new Date();
        SimpleDateFormat nuevo = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String fec = nuevo.format(fe);
        try{
            txtFecha.setText(fec);
            txtProyecto.setText(txtCodigo.getText());
            Connection con = null;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            String datos[] = new String[10];
            Statement st = con.createStatement();
            String sql = "UPDATE Trata SET Proyecto = ?, Plano = ?, FechaInicio = ?, FechaFinal = ?, Terminado = ?,Estado = ?, Prioridad = ? WHERE Id = ?";
            String sql1 = "select * from Trata where Proyecto like '"+txtCodigo.getText()+"'";
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = st.executeQuery(sql1);
            while(rs.next()){
                datos[0] = rs.getString("Estado");
                datos[1] = rs.getString("Id");
                datos[2] = rs.getString("Proyecto");
                datos[3] = rs.getString("Plano");
                datos[4] = rs.getString("FechaInicio");
                datos[5] = rs.getString("FechaFinal");
                datos[6] = rs.getString("Terminado");
                datos[8] = rs.getString("Prioridad");
            }

            if(datos[3].equals("")){

                pst.setString(1, txtProyecto.getText());
                pst.setString(2, datos[3]);
                pst.setString(3, fec);
                pst.setString(4, "");
                pst.setString(5, "NO");
                pst.setString(6, datos[0]);
                pst.setString(7, datos[1]);
                pst.setString(8, datos[8]);
                int n1 = pst.executeUpdate();
                if (n1 > 0){
                    limpiarTabla();
                    verDatos();
                    txtCodigo.setText("");
                }
            }else if(datos[3] != (null)){

                txtFecha.setText(datos[4]);
                txtCodigo.setText("");
            }

        } catch (SQLException ex)
        {
            JOptionPane.showMessageDialog(this, "ERROR AL ECONTRAR EL PLANO : " + ex,"ERROR",JOptionPane.ERROR_MESSAGE);
        }
        txtCodigo.setText("");
    }//GEN-LAST:event_txtCodigoActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        Date fecha = new Date();
        SimpleDateFormat fec1 = new SimpleDateFormat ("dd/MM/yyyy HH:mm:ss");
        String fec = fec1.format(fecha);
        if(txtNumero.getText().equals("")){
            JOptionPane.showMessageDialog(this, "DEBES LLENAR EL NUMERO DE EMPLEADO","ERROR",JOptionPane.ERROR_MESSAGE);
        }else if(txtProyecto.getText().equals("")){
            JOptionPane.showMessageDialog(this, "DEBES SELECCIONAR UN PROYECTO","ERROR",JOptionPane.ERROR_MESSAGE);
        }else{
            try{
                Connection con = null;
                Conexion con1 = new Conexion();
                con = con1.getConnection();

                Statement st = con.createStatement();
                Statement st1 = con.createStatement();
                Statement st2 = con.createStatement();
                Statement st3 = con.createStatement();
                Statement st4 = con.createStatement();
                Statement st5 = con.createStatement();
                Statement st6 = con.createStatement();
                String sql =  "UPDATE Trata SET Proyecto = ?, Plano = ?, FechaInicio = ?, FechaFinal = ?, Terminado = ?, Estado = ?,Prioridad = ? WHERE Proyecto = ?";
                String sql1 = "insert into CNC (Proyecto,Plano,FechaInicio,FechaFinal,Terminado,Estado,Cronometro,Prioridad) values(?,?,?,?,?,?,?,?)";
                String sql2 = "insert into Fresadora (Proyecto,Plano,FechaInicio,FechaFinal,Terminado,Estado,Cronometro,Prioridad) values(?,?,?,?,?,?,?,?)";
                String sql4 = "insert into Torno (Proyecto,Plano,FechaInicio,FechaFinal,Terminado,Estado,Cronometro,Prioridad) values(?,?,?,?,?,?,?,?)";
                String sql5 = "insert into Scrap (Proyecto,NumeroEmpleado,Fecha) values (?,?,?)";
                String sql6 = "insert into Acabados (Proyecto,Plano,FechaInicio,FechaFinal,Terminado,Estado,Cronometro,Prioridad) values(?,?,?,?,?,?,?,?)";
                String sql7 = "insert into Calidad (Proyecto,Plano,FechaInicio,FechaFinal,Terminado,Estado,Tratamiento,Cronometro,Prioridad) values(?,?,?,?,?,?,?,?,?)";
                PreparedStatement pst = con.prepareStatement(sql);
                PreparedStatement pst1 = con.prepareStatement(sql1);
                PreparedStatement pst2 = con.prepareStatement(sql2);
                PreparedStatement pst4 = con.prepareStatement(sql4);
                PreparedStatement pst5 = con.prepareStatement(sql5);
                PreparedStatement pst6 = con.prepareStatement(sql6);
                PreparedStatement pst7 = con.prepareStatement(sql7);

                String borrarTorno = "delete from Trata where Proyecto = ?";
                String borrarCnc = "delete from CNC where Proyecto = ?";
                String borrarFresa = "delete from Fresadora where Proyecto = ?";
                String borrarAcabados = "delete from Torno where Proyecto = ?";
                String borrarCortes = "UPDATE Datos SET Proyecto = ?, Plano = ?, FechaInicio = ?, FechaFinal = ?, Terminado = ?, Estado = ?,Cronometro = ?, Prioridad = ? WHERE Proyecto = ?";
                String borrarCalidad = "delete from Acabados where Proyecto = ?";
                String borrarTratamiento = "delete from Calidad where Proyecto = ?";

                PreparedStatement ps1 = con.prepareStatement(borrarTorno);
                PreparedStatement ps2 = con.prepareStatement(borrarCnc);
                PreparedStatement ps3 = con.prepareStatement(borrarFresa);
                PreparedStatement ps4 = con.prepareStatement(borrarAcabados);
                PreparedStatement ps5 = con.prepareStatement(borrarCortes);
                PreparedStatement ps6 = con.prepareStatement(borrarCalidad);
                PreparedStatement ps7 = con.prepareStatement(borrarTratamiento);

                String ver = "select * from CNC where Proyecto like '"+txtProyecto.getText()+"'";
                String ver1 = "select * from Fresadora where Proyecto like '"+txtProyecto.getText()+"'";
                String ver2 = "select * from Torno where Proyecto like '"+txtProyecto.getText()+"'";
                String ver3 = "select * from Acabados where Proyecto like '"+txtProyecto.getText()+"'";
                String ver4 = "select * from Calidad where Proyecto like '"+txtProyecto.getText()+"'";

                ResultSet rs = st.executeQuery(ver);
                ResultSet rs1 = st1.executeQuery(ver1);
                ResultSet rs2 = st2.executeQuery(ver2);
                ResultSet rs3 = st3.executeQuery(ver3);
                ResultSet rs4 = st4.executeQuery(ver4);

                String ac = "update CNC set Proyecto = ?, Plano = ?, FechaInicio = ?, FechaFinal = ?, Terminado = ?, Estado = ?, Cronometro = ?,Prioridad = ? where Proyecto = ?";
                String ac1 = "update Fresadora set Proyecto = ?, Plano = ?, FechaInicio = ?, FechaFinal = ?, Terminado = ?, Estado = ?, Cronometro = ?,Prioridad = ? where Proyecto = ?";
                String ac2 = "update Torno set Proyecto = ?, Plano = ?, FechaInicio = ?, FechaFinal = ?, Terminado = ?, Estado = ?, Cronometro = ?,Prioridad = ? where Proyecto = ?";
                String ac3 = "update Acabados set Proyecto = ?, Plano = ?, FechaInicio = ?, FechaFinal = ?, Terminado = ?, Estado = ?, Cronometro = ?,Prioridad = ? where Proyecto = ?";
                String ac4 = "update Calidad set Proyecto = ?, Plano = ?, FechaInicio = ?, FechaFinal = ?, Terminado = ?, Estado = ?,Tratamiento = ?, Cronometro = ?,Prioridad = ? where Proyecto = ?";

                PreparedStatement act = con.prepareStatement(ac);
                PreparedStatement act1 = con.prepareStatement(ac1);
                PreparedStatement act2 = con.prepareStatement(ac2);
                PreparedStatement act3 = con.prepareStatement(ac3);
                PreparedStatement act4 = con.prepareStatement(ac4);
                String eCalidad = "select * from Trata where Proyecto like '"+txtProyecto.getText()+"'";
                String eCortes = "select * from Datos where Proyecto like '"+txtProyecto.getText()+"'";
                ResultSet eC = st5.executeQuery(eCalidad);
                ResultSet eCo = st6.executeQuery(eCortes);
                String acabados[] = new String[10];
                String cnc[] = new String[10];
                String fresa[] = new String[10];
                String torno[] = new String[10];
                String calidad[] = new String[10];
                String trata[] = new String[10];
                String cortes[] = new String[10];
                while(eCo.next()){
                    cortes[3] = eCo.getString("Cronometro");
                }
                while(eC.next()){
                    trata[1] = eC.getString("Estado");
                    trata[4] = eC.getString("Plano");
                    trata[5] = eC.getString("Prioridad");
                    trata[6] = eC.getString("FechaInicio");
                }
                while(rs.next()){
                    cnc[1] = rs.getString("Proyecto");
                    cnc[3] = rs.getString("Cronometro");
                }
                while(rs1.next()){
                    fresa[1] = rs1.getString("Proyecto");
                    fresa[3] = rs1.getString("Cronometro");
                }
                while(rs2.next()){
                    torno[1] = rs2.getString("Proyecto");
                    torno[3] = rs2.getString("Cronometro");
                }
                while(rs3.next()){
                    acabados[1] = rs3.getString("Proyecto");
                    acabados[3] = rs3.getString("Cronometro");
                }
                while(rs4.next()){
                    calidad[1] = rs4.getString("Proyecto");
                    calidad[2] = rs4.getString("FechaInicio");
                    calidad[3] = rs4.getString("Cronometro");
                }

                pst.setString(1, txtProyecto.getText());
                pst.setString(2, trata[4]);
                pst.setString(3, txtFecha.getText());
                pst.setString(4, fec);
                pst.setString(5, "SI");
                pst.setString(6, trata[1]);
                pst.setString(7, trata[5]);
                pst.setString(8, txtProyecto.getText());

                if(cmbEnviar.getSelectedItem() == "SELECCIONAR"){
                    JOptionPane.showMessageDialog(this, "DEBE ESCOGER UNA OPCION","",JOptionPane.ERROR_MESSAGE);
                }else
                if(cmbEnviar.getSelectedIndex() == 1)
                {
                    if(fresa[1] == (null)){

                        pst2.setString(1, txtProyecto.getText());
                        pst2.setString(2, trata[4]);
                        pst2.setString(3, "");
                        pst2.setString(4, "");
                        pst2.setString(5, "NO");
                        pst2.setString(6, trata[1]);
                        pst2.setString(7, fresa[3]);
                        pst2.setString(8, trata[5]);

                        int n = pst2.executeUpdate();
                        int n1 = pst.executeUpdate();
                        if (n1 > 0 && n > 0 )
                        {
                            tabla();
                            borrar();
                        }
                    }else{
                        act1.setString(1, txtProyecto.getText());
                        act1.setString(2, trata[4]);
                        act1.setString(3, "");
                        act1.setString(4, "");
                        act1.setString(5, "NO");
                        act1.setString(6, trata[1]);
                        act1.setString(7, fresa[3]);
                        act1.setString(8, trata[5]);
                        act1.setString(9, txtProyecto.getText());
                        int n = act1.executeUpdate();
                        int n1 = pst.executeUpdate();
                        if (n > 0 && n1 >0){
                            tabla();
                            borrar();
                        }

                    }
                }
                else if(cmbEnviar.getSelectedIndex() == 2){

                    if(cnc[1] == (null)){
                        pst1.setString(1, txtProyecto.getText());
                        pst1.setString(2, trata[4]);
                        pst1.setString(3, "");
                        pst1.setString(4, "");
                        pst1.setString(5, "NO");
                        pst1.setString(6, trata[1]);
                        pst1.setString(7, cnc[3]);
                        pst1.setString(8, trata[5]);
                        int n = pst1.executeUpdate();
                        int n1 = pst.executeUpdate();
                        if (n > 0 && n1 > 0)
                        {
                            tabla();
                            borrar();
                        }
                    }else{
                        act.setString(1, txtProyecto.getText());
                        act.setString(2, trata[4]);
                        act.setString(3, "");
                        act.setString(4, "");
                        act.setString(5, "NO");
                        act.setString(6, trata[1]);
                        act.setString(7, cnc[3]);
                        act.setString(8, trata[5]);
                        act.setString(9, txtProyecto.getText());
                        int n = act.executeUpdate();
                        int n1 = pst.executeUpdate();
                        if (n1 > 0 && n > 0){
                            tabla();
                            borrar();
                        }
                    }
                }
                else if(cmbEnviar.getSelectedIndex() == 3){

                    if(torno[1] == (null)){
                        pst4.setString(1, txtProyecto.getText());
                        pst4.setString(2, trata[4]);
                        pst4.setString(3, "");
                        pst4.setString(4, "");
                        pst4.setString(5, "NO");
                        pst4.setString(6, trata[1]);
                        pst4.setString(7, torno[3]);
                        pst4.setString(8, trata[5]);
                        int n = pst4.executeUpdate();
                        int n1 = pst.executeUpdate();
                        if (n1 > 0 && n > 0)
                        {
                            tabla();
                            borrar();
                        }
                    }else{
                        act2.setString(1, txtProyecto.getText());
                        act2.setString(2, trata[4]);
                        act2.setString(3, "");
                        act2.setString(4, "");
                        act2.setString(5, "NO");
                        act2.setString(6, trata[1]);
                        act2.setString(7, torno[3]);
                        act2.setString(8, trata[5]);
                        act2.setString(9, txtProyecto.getText());
                        int n = act2.executeUpdate();
                        int n1 = pst.executeUpdate();
                        if (n1 > 0 && n > 0){
                            tabla();
                            borrar();
                        }
                    }
                }else if(cmbEnviar.getSelectedIndex() == 4){
                    ps1.setString(1, (txtProyecto.getText()));
                    ps2.setString(1, (txtProyecto.getText()));
                    ps3.setString(1, (txtProyecto.getText()));
                    ps4.setString(1, (txtProyecto.getText()));
                    ps6.setString(1, (txtProyecto.getText()));
                    ps7.setString(1, (txtProyecto.getText()));

                    ps5.setString(1, txtProyecto.getText());
                    ps5.setString(2, trata[4]);
                    ps5.setString(3, "");
                    ps5.setString(4, "");
                    ps5.setString(5, "NO");
                    ps5.setString(6, trata[1]);
                    ps5.setString(7, cortes[3]);
                    ps5.setString(8, trata[5]);
                    ps5.setString(9, txtProyecto.getText());

                    pst5.setString(1, txtProyecto.getText());
                    pst5.setString(2, txtNumero.getText());
                    pst5.setString(3, fec);

                    int n4 = pst.executeUpdate();
                    int n5 = ps1.executeUpdate();
                    int n6 = ps2.executeUpdate();
                    int n7 = ps3.executeUpdate();
                    int n8 = ps4.executeUpdate();
                    int n9 = ps5.executeUpdate();
                    int n10 = ps6.executeUpdate();
                    int n11 = pst5.executeUpdate();
                    int n12 = ps7.executeUpdate();
                    JOptionPane.showMessageDialog(this, "DATOS GUARDADOS","",JOptionPane.INFORMATION_MESSAGE);
                    limpiarTabla();
                    verDatos();
                    if (n4 > 0 && n5 > 0 && n6 > 0 && n7 > 0 && n8 > 0 && n9 > 0 && n10 > 0 && n11 > 0 && n12 > 0)
                    {
                        limpiarTabla();
                        verDatos();
                    }
                }
                else if(cmbEnviar.getSelectedIndex() == 5){

                    if(acabados[1] == (null)){
                        pst6.setString(1, txtProyecto.getText());
                        pst6.setString(2, trata[4]);
                        pst6.setString(3, "");
                        pst6.setString(4, "");
                        pst6.setString(5, "NO");
                        pst6.setString(6, trata[1]);
                        pst6.setString(7, trata[5]);
                        int n = pst6.executeUpdate();
                        int n1 = pst.executeUpdate();
                        if (n1 > 0 && n > 0)
                        {
                            tabla();
                            borrar();
                        }
                    }else{
                        act3.setString(1, txtProyecto.getText());
                        act3.setString(2, trata[4]);
                        act3.setString(3, "");
                        act3.setString(4, "");
                        act3.setString(5, "NO");
                        act3.setString(6, trata[1]);
                        act3.setString(7, trata[5]);
                        act3.setString(8, txtProyecto.getText());
                        int n = act3.executeUpdate();
                        int n1 = pst.executeUpdate();
                        if (n1 > 0 && n > 0){
                            tabla();
                            borrar();
                        }
                    }

                }else if(cmbEnviar.getSelectedIndex() == 6){
                    if(calidad[1] == (null)){
                        pst7.setString(1, txtProyecto.getText());
                        pst7.setString(2, trata[4]);
                        pst7.setString(3, "");
                        pst7.setString(4, "");
                        pst7.setString(5, "NO");
                        pst7.setString(6, trata[1]);
                        pst7.setString(7, "NO");
                        pst7.setString(8, "00:00");
                        pst7.setString(9, trata[5]);
                        int n = pst.executeUpdate();
                        int n1 = pst7.executeUpdate();
                        if (n > 0 && n1 > 0)
                        {
                            tabla();
                            borrar();
                        }
                    }else{
                        act4.setString(1, txtProyecto.getText());
                        act4.setString(2, trata[4]);
                        act4.setString(3, "");
                        act4.setString(4, "");
                        act4.setString(5, "NO");
                        act4.setString(6, trata[1]);
                        act4.setString(7, "NO");
                        act4.setString(8, calidad[3]);
                        act4.setString(9, trata[5]);
                        act4.setString(10, txtProyecto.getText());
                        int n = act4.executeUpdate();
                        int n1 = pst.executeUpdate();
                        if (n1 > 0 && n > 0){
                            tabla();
                            borrar();
                        }
                    }
                }else if(cmbEnviar.getSelectedIndex() == 7){

                    pst.setString(1, txtProyecto.getText());
                    pst.setString(2, trata[4]);
                    pst.setString(3, txtFecha.getText());
                    pst.setString(4, fec);
                    pst.setString(5, "TERMINADO");
                    pst.setString(6, trata[1]);
                    pst.setString(7, trata[5]);
                    pst.setString(8, txtProyecto.getText());

                    int n = pst.executeUpdate();
                    if (n > 0){
                        tabla();
                        borrar();
                    }

                }

            }catch(SQLException e){

                JOptionPane.showMessageDialog(null, "NO SE PUEDE ENVIAR INFORMACION"+e,"ERROR",JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void txtEmpleadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEmpleadoActionPerformed
        try{
            Connection con = null;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            Statement st = con.createStatement();
            String datos[] = new String[6];
            String sql = "select * from registroEmpleados where NumEmpleado like '"+txtEmpleado.getText()+"'";
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()){
                datos[0] = rs.getString("NumEmpleado");
                datos[1] = rs.getString("Nombre");
                datos[2] = rs.getString("Apellido");
                datos[3] = rs.getString("Direccion");
                datos[4] = rs.getString("Telefono");
                datos[5] = rs.getString("Puesto");
            }

            if(txtEmpleado.getText().equals(datos[0])){

                txtNumero.setText(datos[0]);
                txtNombre.setText(datos[1]);
                txtPuesto.setText(datos[5]);
                txtEmpleado.setText("");

            }

        }catch(SQLException e){

            JOptionPane.showMessageDialog(this, "","",JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_txtEmpleadoActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

    }//GEN-LAST:event_jButton1ActionPerformed

    private void cmbEnviarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbEnviarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbEnviarActionPerformed

    private void Tabla1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Tabla1MouseClicked
        Date fe = new Date();
        SimpleDateFormat nuevo = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String fec = nuevo.format(fe);

        try{

            int fila = Tabla1.getSelectedRow();
            txtFecha.setText(fec);

            Connection con = null;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            String datos[] = new String[8];
            Statement st = con.createStatement();
            String sql = "UPDATE Trata SET Proyecto = ?, Plano = ?, FechaInicio = ?, FechaFinal = ?, Terminado = ?, Estado = ?, Prioridad = ? WHERE Proyecto = ?";
            String sql1 = "select * from Trata where Proyecto like '"+(Tabla1.getValueAt(fila, 1).toString())+"'";
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = st.executeQuery(sql1);
            while(rs.next()){
                datos[0] = rs.getString("Estado");
                datos[1] = rs.getString("Proyecto");
                datos[2] = rs.getString("Plano");
                datos[3] = rs.getString("FechaInicio");
                datos[4] = rs.getString("FechaFinal");
                datos[5] = rs.getString("Terminado");
                datos[6] = rs.getString("Prioridad");
            }

            if(datos[3].equals("") && datos[5].equals("NO")){
                pst.setString(1, (Tabla1.getValueAt(fila, 1).toString()));
                pst.setString(2, datos[2]);
                pst.setString(3, fec);
                pst.setString(4, "");
                pst.setString(5, "NO");
                pst.setString(6, datos[0]);
                pst.setString(7, datos[6]);
                pst.setString(8, (Tabla1.getValueAt(fila, 1).toString()));
                int n1 = pst.executeUpdate();
                if (n1 > 0){
                    limpiarTabla();
                    verDatos();
                    txtProyecto.setText(Tabla1.getValueAt(fila, 1).toString());
                }
            }else if(datos[3] != (null) && datos[5].equals("NO")){
                txtProyecto.setText(Tabla1.getValueAt(fila, 1).toString());
                txtFecha.setText(datos[3]);

            }

        } catch (SQLException ex)
        {
            System.out.println("ERROR AL SELECCIONAR UN EQUIPO : " + ex.getMessage());
        }

    }//GEN-LAST:event_Tabla1MouseClicked

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        this.dispose();
    }//GEN-LAST:event_jButton4ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable Tabla1;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JComboBox<String> cmbEnviar;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JTextField txtEmpleado;
    private javax.swing.JLabel txtFecha;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtNumero;
    private javax.swing.JLabel txtProyecto;
    private javax.swing.JTextField txtPuesto;
    // End of variables declaration//GEN-END:variables
}
