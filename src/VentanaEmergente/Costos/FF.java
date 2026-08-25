package VentanaEmergente.Costos;

public class FF {
    public String fechaInicio;
    public String fechaFinal;
    public int semanas;
    public int empieza;

    public int getEmpieza() {
        return empieza;
    }

    public void setEmpieza(int empieza) {
        this.empieza = empieza;
    }

    public int getSemanas() {
        return semanas;
    }

    public void setSemanas(int semanas) {
        this.semanas = semanas;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public FF(String fechaInicio, String fechaFinal, int semanas, int empieza) {
        this.fechaInicio = fechaInicio;
        this.fechaFinal = fechaFinal;
        this.semanas = semanas;
        this.empieza = empieza;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getFechaFinal() {
        return fechaFinal;
    }

    public void setFechaFinal(String fechaFinal) {
        this.fechaFinal = fechaFinal;
    }
    
    
    
}
