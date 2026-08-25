package VentanaEmergente.ProyectoManager;

public class InfoProyectos {
    
    public String fecha;
    public String depa;
    public String proyecto;

    public InfoProyectos(String proyecto, String fecha, String depa) {
        this.fecha = fecha;
        this.depa = depa;
        this.proyecto = proyecto;
    }

    public String getProyecto() {
        return proyecto;
    }

    public void setProyecto(String proyecto) {
        this.proyecto = proyecto;
    }
    
    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getDepa() {
        return depa;
    }

    public void setDepa(String depa) {
        this.depa = depa;
    }
    
}
