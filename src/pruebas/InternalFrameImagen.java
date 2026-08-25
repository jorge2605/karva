package pruebas;

import javax.swing.JInternalFrame;

public class InternalFrameImagen extends JInternalFrame {
    private FondoPantalla fp= new FondoPantalla();
    
    public InternalFrameImagen(){
    setContentPane(fp);
    }
    public void setImagen(String nombreImagen){
    fp.setImage(nombreImagen);
    }
}
