package VentanaEmergente.Checador;

public class confEmpleado {
    String entrada;
    String salida;
    String horaDiaria;
    String turno;
    String horaSabado;
    String numEmpleado;
    String nombre;
    String entradaSabado;
    String salidaSabado;
    String totalHoras;
    boolean redondeo;
    boolean automatico;

    public confEmpleado(String entrada, String salida, String horaDiaria, String turno, String horaSabado, String numEmpleado, 
            String nombre, String entradaSabado, String salidaSabado, boolean redondeo, String totalHoras, boolean automatico) {
        this.entrada = entrada;
        this.salida = salida;
        this.horaDiaria = horaDiaria;
        this.turno = turno;
        this.horaSabado = horaSabado;
        this.numEmpleado = numEmpleado;
        this.nombre = nombre;
        this.entradaSabado = entradaSabado;
        this.salidaSabado = salidaSabado;
        this.redondeo = redondeo;
        this.totalHoras = totalHoras;
        this.automatico = automatico;
    }

    public String getTotalHoras() {
        return totalHoras;
    }

    public void setTotalHoras(String totalHoras) {
        this.totalHoras = totalHoras;
    }

    public boolean getRedondeo() {
        return redondeo;
    }

    public void setRedondeo(boolean redondeo) {
        this.redondeo = redondeo;
    }

    public String getEntradaSabado() {
        return entradaSabado;
    }

    public void setEntradaSabado(String entradaSabado) {
        this.entradaSabado = entradaSabado;
    }

    public String getSalidaSabado() {
        return salidaSabado;
    }

    public void setSalidaSabado(String salidaSabado) {
        this.salidaSabado = salidaSabado;
    }

    public String getEntrada() {
        return entrada;
    }

    public void setEntrada(String entrada) {
        this.entrada = entrada;
    }

    public String getSalida() {
        return salida;
    }

    public void setSalida(String salida) {
        this.salida = salida;
    }

    public String getHoraDiaria() {
        return horaDiaria;
    }

    public void setHoraDiaria(String horaDiaria) {
        this.horaDiaria = horaDiaria;
    }

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

    public String getHoraSabado() {
        return horaSabado;
    }

    public void setHoraSabado(String horaSabado) {
        this.horaSabado = horaSabado;
    }

    public String getNumEmpleado() {
        return numEmpleado;
    }

    public void setNumEmpleado(String numEmpleado) {
        this.numEmpleado = numEmpleado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean isAutomatico() {
        return automatico;
    }

    public void setAutomatico(boolean automatico) {
        this.automatico = automatico;
    }
    
}
