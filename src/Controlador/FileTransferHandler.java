package Controlador;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.io.File;
import javax.swing.JComponent;
import javax.swing.JTextField;
import javax.swing.TransferHandler;

public class FileTransferHandler extends TransferHandler {
    public boolean canImport(TransferHandler.TransferSupport support) {
        if (!support.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
            return false;
        }
        
        return true;
    }
    public JTextField component;
    public String ruta;

    public void getFile(){
        
    }
    
    public void setComponent(JTextField component) {
        this.component = component;
    }
    public boolean importData(TransferHandler.TransferSupport support) {
        if (!canImport(support)) {
            return false;
        }
        
        Transferable transferable = support.getTransferable();
        try {
            java.util.List<File> fileList = (java.util.List<File>) transferable.getTransferData(DataFlavor.javaFileListFlavor);
            for (File file : fileList) {
                component.setText(file.getName());
                setRuta(file.getAbsolutePath());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return true;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }
    
    public void exportDone(JComponent source, Transferable data, int action) {
        // Do nothing
    }
}
