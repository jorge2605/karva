package VentanaEmergente.Calendario.Modelo;

public class PropiedadesFechas {
    
    public String dpto;
    public String fechaInicio;
    public String fechaFinal;
    public String color;

    public String dpto() {
        return dpto;
    }
    
    public String getFechaInicio() {
        return fechaInicio;
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

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public PropiedadesFechas(String dpto, String fechaInicio, String fechaFinal, String color) {
        this.dpto = dpto;
        this.fechaInicio = fechaInicio;
        this.fechaFinal = fechaFinal;
        this.color = color;
    }
    
}
