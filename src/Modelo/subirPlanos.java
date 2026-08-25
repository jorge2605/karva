package Modelo;

import Conexiones.Conexion;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import pruebas.PDFManager;

public class subirPlanos extends javax.swing.JFrame {

    public subirPlanos() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jButton1.setText("Seleccionar archivos");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(226, 226, 226)
                .addComponent(jButton1)
                .addContainerGap(240, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(103, 103, 103)
                .addComponent(jButton1)
                .addContainerGap(121, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        try{
            Connection con;
            Conexion con1 = new Conexion();
            con = con1.getConnection();
            String sql = "insert into pdfplanos (Pdf, Plano) values(?,?)";
            PreparedStatement pst = con.prepareStatement(sql);
            JFileChooser SelectArchivo = new JFileChooser();
            File archivo[] = null;

            SelectArchivo.setFileFilter(new FileNameExtensionFilter("PDF (*.pdf)", "pdf"));
            SelectArchivo.setMultiSelectionEnabled(true);
            if (SelectArchivo.showDialog(null, "Seleccionar Archivo") == JFileChooser.APPROVE_OPTION) {
                archivo = SelectArchivo.getSelectedFiles();

            }
            File ar = null;

            for (File archivo1 : archivo) {
                byte[] pe = null;
                if (archivo1 == null) {
                } else {
                    pe = new byte[(int) archivo1.length()];
                    try {
                        InputStream input = new FileInputStream(archivo1);
                        input.read(pe);
                    }catch(IOException e){

                    }
                }
                pst.setBytes(1, pe);
                pst.setString(2, archivo1.getName().substring(0,archivo1.getName().length() - 4));
                
                int n = pst.executeUpdate();
                
                if(n > 0){
                    System.out.println("" +archivo1.getName().substring(0,archivo1.getName().length() - 4));
                }else{
                    System.out.println("Este no entro: "+archivo1.getName().substring(0,archivo1.getName().length() - 4));
                }
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(this, "ERROR: "+e,"ERROR",JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new subirPlanos().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    // End of variables declaration//GEN-END:variables
}
