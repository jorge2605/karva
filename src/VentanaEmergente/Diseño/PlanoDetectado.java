package VentanaEmergente.Diseño;

import java.io.File;

public class PlanoDetectado {

    private final File archivoOriginal;
    private final int pagina;
    private final String numeroParte;
    private final int consecutivo;
    private String archivoGenerado;

    public PlanoDetectado(File archivoOriginal, int pagina, String numeroParte, int consecutivo) {
        this.archivoOriginal = archivoOriginal;
        this.pagina = pagina;
        this.numeroParte = numeroParte;
        this.consecutivo = consecutivo;
    }

    public File getArchivoOriginal() {
        return archivoOriginal;
    }

    public int getPagina() {
        return pagina;
    }

    public String getNumeroParte() {
        return numeroParte;
    }

    public int getConsecutivo() {
        return consecutivo;
    }

    public String getArchivoGenerado() {
        return archivoGenerado;
    }

    public void setArchivoGenerado(String archivoGenerado) {
        this.archivoGenerado = archivoGenerado;
    }

    public String getId() {
        return numeroParte + "-" + consecutivo;
    }
}