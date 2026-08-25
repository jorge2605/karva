package VO;

public class ArchivosVO {

    int idArchivos;
    private byte[] Archivos;
    int Actas_idActas;

    public ArchivosVO() {
    }

    public int getIdArchivos() {
        return idArchivos;
    }

    public byte[] getArchivos() {
        return Archivos;
    }

    public int getActas_idActas() {
        return Actas_idActas;
    }


    public void setIdArchivos(int idArchivos) {
        this.idArchivos = idArchivos;
    }

    public void setArchivos(byte[] Archivos) {
        this.Archivos = Archivos;
    }

    public void setActas_idActas(int Actas_idActas) {
        this.Actas_idActas = Actas_idActas;
    }

}
