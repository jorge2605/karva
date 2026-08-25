package VentanaEmergente.Inicio1;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Desktop;
import static java.awt.Frame.ICONIFIED;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Stack;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import java.nio.file.*;
import static java.nio.file.StandardWatchEventKinds.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
 
public class Backups extends javax.swing.JFrame {

    public Stack<JButton> botones;
    public String rutaDestino = null;
    public boolean cancelar;
    private int botonSeleccionado = -1; 
    TrayIcon trayIcon;
    SystemTray tray;
    public File rutaCrearNotas;
    public File rutaCrearDestino;
    
    public final void crearNotas(boolean force, String contenido) {
        String rutaBase = System.getProperty("user.dir").replace("\\dist", "");
        String nombreArchivo = "BD/CarpetasSeleccionadas.txt";
        File ex = new File(rutaBase, nombreArchivo);
        rutaCrearNotas = ex;
        if (!ex.exists() || force == true ) {
            try {
                FileWriter escritor;
                escritor = new FileWriter(rutaCrearNotas);
                escritor.write(contenido);
                escritor.close();
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error crear notas: " + e, "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    public final void leerNotas() {
        botones = new Stack<>();
        try (BufferedReader lector = new BufferedReader(new FileReader(rutaCrearNotas))) {
            String linea;
            int cont = 0;
            while ((linea = lector.readLine()) != null) {
                crearBoton(linea, cont);
                cont++;
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error leer notas: " + e, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public final void crearRutaDestino(boolean force, String contenido) {
        String rutaBase = System.getProperty("user.dir").replace("\\dist", "");
        String nombreArchivo = "BD/rutaDestino.txt";
        File ex = new File(rutaBase, nombreArchivo);
        rutaCrearDestino = ex;
        contenido = (contenido == null) ? "ruta: \nestado:sin seleccionar\ncancelar:true" : contenido;
        if (!ex.exists() || force == true ) {
            try {
                FileWriter escritor;
                escritor = new FileWriter(rutaCrearDestino);
                escritor.write(contenido);
                escritor.close();
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error crear ruta destino: " + e, "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    public final void leerRutaDestino() {
        try (BufferedReader lector = new BufferedReader(new FileReader(rutaCrearDestino))) {
            String linea;
            while ((linea = lector.readLine()) != null) {
                String dat[] = linea.split(":");
                String col = dat[0];
                String text = dat[1];
                if (col.equals("ruta")) {
                    rutaDestino = text;
                    String cont = (text.equals(" ") ? "Cargar ubicacion                                                        " : "Cargar Ubicacion:" + text);
                    Ubicacion.setText(cont);
                } else if (col.equals("estado")) {
                    Estado.setText("Estado:" + text);
                } else if (col.equals("cancelar")) {
                    cancelar = (text.equals("true"));
                    Cancelar.setText("Cancelar Backup:" + text);
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error leer ruta destino: " + e, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void crearBoton(String ruta, int cont) {
        JButton botonCarpeta = new JButton();
        botonCarpeta.setBackground(new java.awt.Color(255, 255, 255));
        botonCarpeta.setFont(new java.awt.Font("Roboto", 0, 10)); // NOI18N
        botonCarpeta.setForeground(new java.awt.Color(51, 51, 51));
        botonCarpeta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/folder.png")));
        botonCarpeta.setText(ruta);
        botonCarpeta.setBorder(null);
        botonCarpeta.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        botonCarpeta.setPreferredSize(new java.awt.Dimension(400, 15));
        botonCarpeta.setComponentPopupMenu(jPopupMenu1);
        botonCarpeta.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jPopupMenu1.show(botonCarpeta, botonCarpeta.getWidth() / 2, botonCarpeta.getHeight() / 2);
                botonSeleccionado = cont;
            }
        });
        
        java.awt.GridBagConstraints gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        botones.add(botonCarpeta);
        panelBotones.add(botones.get(cont), gridBagConstraints);
    }
    
    public void limpiarBotones() {
        for (int i = 0; i < botones.size(); i++) {
            panelBotones.remove(botones.get(i));
        }
        revalidate();
        repaint();
    }
    
    public void copiarCarpeta(File origen, File destino) {
        if (!cancelar) {
            if (origen.isDirectory()) {
                if (!destino.exists()) {
                    destino.mkdirs();
                }

                String[] archivos = origen.list();
                if (archivos != null) {
                    for (String archivo : archivos) {
                        copiarCarpeta(new File(origen, archivo), new File(destino, archivo));
                    }
                }
            } else {
                if (!destino.exists() || origen.lastModified() > destino.lastModified()) {
                    copiarArchivo(origen, destino);
                }
            }
        }
    }

    public void copiarArchivo(File origen, File destino) {
        if (!cancelar) {
            try (FileInputStream in = new FileInputStream(origen);
                 FileOutputStream out = new FileOutputStream(destino)) {

                byte[] buffer = new byte[1024];
                int length;
                while ((length = in.read(buffer)) > 0) {
                    out.write(buffer, 0, length);
                }

                destino.setLastModified(origen.lastModified());
                lblConsola.setText(lblConsola.getText() + "<p>Copiado: " + origen.getAbsolutePath() + "</p>");

            } catch (IOException e) {
                lblConsola.setText(lblConsola.getText() + "<p style = 'color: red;'>Error: " + origen.getAbsolutePath() + "</p>");
            }
        }
    }
    
    private void esucharCarpetas(String ruta) {
        if (!botones.isEmpty()) {
            Thread hilo = new Thread(){
              public void run() {
                  if (cancelar == false) {
                    WatchService watcher = null;
                    try {
                        watcher = FileSystems.getDefault().newWatchService();
                    } catch (IOException ex) {
                        Logger.getLogger(Backups.class.getName()).log(Level.SEVERE, null, ex);
                    }
                      Path carpeta = Paths.get(ruta);
                    try {
                        carpeta.register(watcher, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);
                    } catch (IOException ex) {
                        Logger.getLogger(Backups.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    while (true) {
                        WatchKey key = null;
                      try {
                          key = watcher.take();
                      } catch (InterruptedException ex) {
                          Logger.getLogger(Backups.class.getName()).log(Level.SEVERE, null, ex);
                      }
                        for (WatchEvent<?> event : key.pollEvents()) {
                            WatchEvent.Kind<?> tipo = event.kind();
                            Path archivo = (Path) event.context();
                            agregarCarpetas();
                        }
                        boolean valid = key.reset();
                        if (!valid) {
                            break;
                        }
                      }
                  }
              }  
            };
            hilo.start();
        }
    }
    
    public final void agregarCarpetas() {
        if (!botones.isEmpty()) {
            for (int i = 0; i < botones.size(); i++) {
                botones.get(i).setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/folder.png")));
            }
            Thread hilo = new Thread() {
                public void run() {
                    if (cancelar == false) {
                        for (int i = 0; i < botones.size(); i++) {
                            File origen = new File(botones.get(i).getText());
                            File destino = new File("\\" + rutaDestino);
                            File nuevoDestino = new File(destino, origen.getName());
                            copiarCarpeta(origen, nuevoDestino);
                            botones.get(i).setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/ok.png")));
                        }
                    }
                }
            };
            hilo.start();
        }
    }
    
    public final void iniciar() {
        agregarCarpetas();
        for (int i = 0; i < botones.size(); i++) {
            esucharCarpetas(botones.get(i).getText());
        }
    }
    
    public Backups() {
        initComponents();
        setTitle("Servicios Industriales 3i");
        this.setIconImage(new ImageIcon(getClass().getResource("/Imagenes/towi_Azul.png")).getImage()); 
        jScrollPane1.getVerticalScrollBar().setUnitIncrement(15);
        jScrollPane1.getViewport().setBackground(Color.white);
        crearNotas(false, "");
        leerNotas();
        crearRutaDestino(false, null);
        leerRutaDestino();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPopupMenu1 = new javax.swing.JPopupMenu();
        Abrir = new javax.swing.JMenuItem();
        Editar = new javax.swing.JMenuItem();
        Eliminar = new javax.swing.JMenuItem();
        jPanel1 = new javax.swing.JPanel();
        panelBotones = new javax.swing.JPanel();
        jButton8 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        lblConsola = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        Ubicacion = new javax.swing.JMenuItem();
        Estado = new javax.swing.JMenuItem();
        Cancelar = new javax.swing.JMenuItem();

        Abrir.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        Abrir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/folder.png"))); // NOI18N
        Abrir.setText("Abrir");
        Abrir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AbrirActionPerformed(evt);
            }
        });
        jPopupMenu1.add(Abrir);

        Editar.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        Editar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/edit.png"))); // NOI18N
        Editar.setText("Editar                                                                         ");
        Editar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EditarActionPerformed(evt);
            }
        });
        jPopupMenu1.add(Editar);

        Eliminar.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        Eliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/x.png"))); // NOI18N
        Eliminar.setText("Eliminar                                      ");
        Eliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EliminarActionPerformed(evt);
            }
        });
        jPopupMenu1.add(Eliminar);

        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new java.awt.BorderLayout(5, 5));

        panelBotones.setBackground(new java.awt.Color(255, 255, 255));
        java.awt.GridBagLayout jPanel2Layout = new java.awt.GridBagLayout();
        jPanel2Layout.rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0};
        panelBotones.setLayout(jPanel2Layout);

        jButton8.setBackground(new java.awt.Color(255, 255, 255));
        jButton8.setFont(new java.awt.Font("Roboto", 0, 10)); // NOI18N
        jButton8.setForeground(new java.awt.Color(51, 51, 51));
        jButton8.setBorder(null);
        jButton8.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton8.setPreferredSize(new java.awt.Dimension(150, 15));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 30;
        panelBotones.add(jButton8, gridBagConstraints);

        jButton1.setBackground(new java.awt.Color(0, 102, 255));
        jButton1.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Agregar carpeta");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        panelBotones.add(jButton1, gridBagConstraints);

        jPanel1.add(panelBotones, java.awt.BorderLayout.LINE_START);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setLayout(new java.awt.BorderLayout(5, 5));

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 102, 204));
        jLabel1.setText("Respaldos TOWI");
        jPanel4.add(jLabel1);

        jPanel3.add(jPanel4, java.awt.BorderLayout.PAGE_START);

        jPanel5.setBackground(new java.awt.Color(235, 235, 235));
        jPanel5.setLayout(new java.awt.BorderLayout());

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.add(jPanel2, java.awt.BorderLayout.PAGE_START);

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setLayout(new java.awt.BorderLayout());

        jScrollPane1.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        jScrollPane1.setForeground(new java.awt.Color(204, 204, 204));

        lblConsola.setFont(new java.awt.Font("Consolas", 0, 12)); // NOI18N
        lblConsola.setForeground(new java.awt.Color(153, 153, 153));
        lblConsola.setText("<html>");
        jScrollPane1.setViewportView(lblConsola);

        jPanel6.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jPanel5.add(jPanel6, java.awt.BorderLayout.CENTER);

        jPanel3.add(jPanel5, java.awt.BorderLayout.CENTER);

        jPanel1.add(jPanel3, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        jMenu1.setBackground(new java.awt.Color(255, 255, 255));
        jMenu1.setText("File");

        Ubicacion.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        Ubicacion.setForeground(new java.awt.Color(0, 0, 0));
        Ubicacion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/folder.png"))); // NOI18N
        Ubicacion.setText("Cargar ubicacion                                                        ");
        Ubicacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UbicacionActionPerformed(evt);
            }
        });
        jMenu1.add(Ubicacion);

        Estado.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        Estado.setForeground(new java.awt.Color(0, 0, 0));
        Estado.setText("Estado de Backup");
        Estado.setEnabled(false);
        Estado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EstadoActionPerformed(evt);
            }
        });
        jMenu1.add(Estado);

        Cancelar.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        Cancelar.setForeground(new java.awt.Color(0, 0, 0));
        Cancelar.setText("Cancelar Backup");
        Cancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CancelarActionPerformed(evt);
            }
        });
        jMenu1.add(Cancelar);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void EliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EliminarActionPerformed
        int opc = JOptionPane.showConfirmDialog(this, "Estas seguro de eliminar de la lista la carpeta " + new File(botones.get(botonSeleccionado).getText()).getName() + "?");
        if (opc == 0) {
            panelBotones.remove(botones.get(botonSeleccionado));
            botones.remove(botonSeleccionado);
            String contenido = "";
            for (int i = 0; i < botones.size(); i++) {
                if (i == 0) {
                    contenido = botones.get(i).getText();
                } else {
                    contenido += "\n" +  botones.get(i).getText();
                }
            }
            crearNotas(true, contenido);
            revalidate();
            repaint();
        }
    }//GEN-LAST:event_EliminarActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        JFileChooser selector = new JFileChooser();
        selector.setDialogTitle("Seleccionar carpeta");
        selector.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        selector.setAcceptAllFileFilterUsed(true);
        
        JFrame f = (JFrame) JOptionPane.getFrameForComponent(this);
        int resultado = selector.showOpenDialog(f);
        
        if (resultado == JFileChooser.APPROVE_OPTION) {
            File seleccionada = selector.getSelectedFile();
            String contenido = "";
        for (int i = 0; i < botones.size(); i++) {
            if (i == 0) {
                contenido = botones.get(i).getText();
            } else {
                contenido += "\n" +  botones.get(i).getText();
            }
        }
        crearNotas(true, contenido + "\n" + seleccionada.getAbsolutePath());
        limpiarBotones();
        leerNotas();
        revalidate();
        repaint();
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void EditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EditarActionPerformed
        JFileChooser selector = new JFileChooser();
        selector.setDialogTitle("Seleccionar carpeta");
        selector.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        selector.setAcceptAllFileFilterUsed(true);
        
        JFrame f = (JFrame) JOptionPane.getFrameForComponent(this);
        int resultado = selector.showOpenDialog(f);
        
        if (resultado == JFileChooser.APPROVE_OPTION) {
            String contenido = "";
            botones.get(botonSeleccionado).setText(selector.getSelectedFile().getAbsolutePath());
            for (int i = 0; i < botones.size(); i++) {
            if (i == 0) {
                contenido = botones.get(i).getText();
            } else {
                contenido += "\n" +  botones.get(i).getText();
            }
        }
        crearNotas(true, contenido);
        limpiarBotones();
        leerNotas();
        revalidate();
        repaint();
        }
    }//GEN-LAST:event_EditarActionPerformed

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        
    }//GEN-LAST:event_formWindowClosed

    private void AbrirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AbrirActionPerformed
        try {
            String ruta = botones.get(botonSeleccionado).getText();
            File carpeta = new File(ruta);
            if (carpeta.exists()) {
                Desktop.getDesktop().open(carpeta);
            } else {
                JOptionPane.showMessageDialog(this, "La carpeta no existe.");
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "No se pudo abrir la carpeta.");
        }
    }//GEN-LAST:event_AbrirActionPerformed

    private void EstadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EstadoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_EstadoActionPerformed

    private void UbicacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UbicacionActionPerformed
        JFileChooser selector = new JFileChooser();
        selector.setCurrentDirectory(new File("\\\\serverdell\\04 Individual"));
        selector.setDialogTitle("Seleccionar carpeta destino");
        selector.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        selector.setAcceptAllFileFilterUsed(true);
        
        int resultado = selector.showOpenDialog(this);
        
        if (resultado == JFileChooser.APPROVE_OPTION) {
            File seleccionada = selector.getSelectedFile();
            String contenido = "ruta:" + seleccionada.getAbsolutePath() + "\n"
                    + "estado:Carpeta seleccionda\n"
                    + "cancelar:false";
            crearRutaDestino(true, contenido);
            leerRutaDestino();
        }
    }//GEN-LAST:event_UbicacionActionPerformed

    private void CancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CancelarActionPerformed
        String dat[] = Cancelar.getText().split(":");
        if (dat[1].equals("false")) {
            int opc = JOptionPane.showConfirmDialog(this, "Estas seguro de cancelar el respaldo de tus archivos?", "Advertencia", JOptionPane.WARNING_MESSAGE);
            if (opc == 0) {
                String ruta[] = Ubicacion.getText().split(":");
                String contenido = "ruta:" + ruta[1] + "\n"
                        + "estado:cancelado\n"
                        + "cancelar:true";
                crearRutaDestino(true, contenido);
                leerRutaDestino();
            }
        } else {
            String ruta[] = Ubicacion.getText().split(":");
            String contenido = "ruta:" + ruta[1] + "\n"
                    + "estado:Carpeta seleccionda\n"
                    + "cancelar:false";
            crearRutaDestino(true, contenido);
            leerRutaDestino();
            iniciar();
        }
    }//GEN-LAST:event_CancelarActionPerformed

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Backups().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem Abrir;
    private javax.swing.JMenuItem Cancelar;
    private javax.swing.JMenuItem Editar;
    private javax.swing.JMenuItem Eliminar;
    private javax.swing.JMenuItem Estado;
    private javax.swing.JMenuItem Ubicacion;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton8;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblConsola;
    private javax.swing.JPanel panelBotones;
    // End of variables declaration//GEN-END:variables
}
