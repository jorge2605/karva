package Controlador;
import java.io.*;
import java.nio.file.*;
import java.util.Properties;

public class Configuracion {

    private final Path archivo;

    public Configuracion() {
        this.archivo = Paths.get("C:\\karva\\config\\datos.dat");
    }

    public void guardar(String clave, String valor) throws IOException {
        Path carpeta = archivo.getParent();
        if (carpeta != null) {
            Files.createDirectories(carpeta);
        }
        Properties propiedades = new Properties();
        if (Files.exists(archivo)) {
            try (InputStream input = Files.newInputStream(archivo)) {
                propiedades.load(input);
            }
        }
        propiedades.setProperty(clave, valor);
        try (OutputStream output = Files.newOutputStream(archivo)) {
            propiedades.store(output, "Configuracion");
        }
    }

    public String leer(String clave) throws IOException {
        if (!Files.exists(archivo)) {
            return null;
        }
        Properties propiedades = new Properties();
        try (InputStream input = Files.newInputStream(archivo)) {
            propiedades.load(input);
        }
        return propiedades.getProperty(clave);
    }

    public boolean existe() {
        return Files.exists(archivo);
    }
}