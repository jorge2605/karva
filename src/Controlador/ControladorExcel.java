package Controlador;
import java.awt.event.ActionEvent;
import java.awt.event.AWTEventListener;
import java.io.*;
import Modelo.ModeloExcel;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import static javax.swing.JFileChooser.DIRECTORIES_ONLY;
import javax.swing.filechooser.FileNameExtensionFilter;
import pruebas.CambiarEstado;
import pruebas.Disenio1;
public class ControladorExcel implements ActionListener{

    ModeloExcel ModeloEX=new ModeloExcel();
    Disenio1 VistaEX=new Disenio1("",null);
    CambiarEstado VistaEXe = new CambiarEstado("");
    JFileChooser SelectArchivo=new JFileChooser();
    File archivo;
    int contador=0;
    
    public ControladorExcel(Disenio1 VistaEX, ModeloExcel ModeloEX, CambiarEstado VistaEXe){
      this.VistaEX=VistaEX;
      this.ModeloEX=ModeloEX;
      this.VistaEXe=VistaEXe;
       this.VistaEX.btnImportar.addActionListener(this);
        this.VistaEXe.btnExportarD.addActionListener(this);
        VistaEX.setVisible(true);
        
    }
    public void AgregarFiltro(){
        SelectArchivo.setFileFilter(new FileNameExtensionFilter("Excel (*.xls)","xls"));
        SelectArchivo.setFileFilter(new FileNameExtensionFilter("Excel (*.xlsx)","xlsx"));
        SelectArchivo.setMultiSelectionEnabled(true);
    }
    
    
    @Override
    public void actionPerformed(ActionEvent e) {
        contador++;
        if(contador==1)AgregarFiltro();
        
        if(e.getSource()==VistaEX.btnImportar){
            if(SelectArchivo.showDialog(null, "Seleccionar Archivo")==JFileChooser.APPROVE_OPTION){
                archivo=SelectArchivo.getSelectedFile();
                //ALT + 124 ||
                if(archivo.getName().endsWith("xls")||archivo.getName().endsWith("xlsx")){
//                    JOptionPane.showMessageDialog(null, ModeloEX.Importar(archivo,VistaEX.DatosExcel));
                }else{
                    JOptionPane.showMessageDialog(null, "Seleccionar formato Valido");
                }
            }
        }
        //COPIAR Y PEGAR EL MISMO CODIGO
        
         if(e.getSource()==VistaEXe.btnExportarD){
            if(SelectArchivo.showDialog(null, "Seleccionar Archivo")==JFileChooser.APPROVE_OPTION){
                archivo=SelectArchivo.getSelectedFile();
                //ALT + 124 ||
                if(archivo.getName().endsWith("xls")||archivo.getName().endsWith("xlsx")){
                    JOptionPane.showMessageDialog(null, ModeloEX.Exportar(archivo,VistaEXe.TablaDeDatos1));
                }else{
                    JOptionPane.showMessageDialog(null, "Seleccionar formato Valido");
                }
            }
        }
    }
    
    
}
