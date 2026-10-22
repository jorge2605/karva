package VentanaEmergente.Inicio1;

import java.util.function.Supplier;
import javax.swing.JInternalFrame;

public class AgregarMenu {
    
    public String titulo;
    public String urlImagen;
    public Supplier<JInternalFrame> frame;

    public String getTitulo() {
        return titulo;
    }

    public String getUrlImagen() {
        return urlImagen;
    }

    public Supplier<JInternalFrame> getFrame() {
        return frame;
    }

    public AgregarMenu(String titulo, String urlImagen, Supplier<JInternalFrame> frame) {
        this.titulo = titulo;
        this.urlImagen = urlImagen;
        this.frame = frame;
    }

}
