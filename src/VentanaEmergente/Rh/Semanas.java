package VentanaEmergente.Rh;

import java.time.LocalDate;

public class Semanas {
    
    private String semana;
    private LocalDate inicio;
    private LocalDate fin;

    public String getSemana() {
        return semana;
    }

    public void setSemana(String semana) {
        this.semana = semana;
    }

    public LocalDate getInicio() {
        return inicio;
    }

    public void setInicio(LocalDate inicio) {
        this.inicio = inicio;
    }

    public LocalDate getFin() {
        return fin;
    }

    public void setFin(LocalDate fin) {
        this.fin = fin;
    }

    public Semanas(String semana, LocalDate inicio) {
        this.semana = semana;
        this.inicio = inicio;
        this.fin = inicio.plusDays(6);
    }
    
    @Override
    public String toString() {
        return "Desde " + inicio + " Hasta: " + fin;
    }
    
}
