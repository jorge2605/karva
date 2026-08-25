package VentanaEmergente.Rh;

public class GuardarEmpleados {
    
    public String numEmpleado;
    public String nombreEmpleado;
    public String nombreSupervisor;
    public String entrada;
    public String salida;
    public String turno;
    public String departamento;
    public String admin;
    public String entradaSabado;
    public String salidaSabado;
    public String horaDoble;
    public String horaTriple;
    public String horasDiarias;
    public String totalHoras;
    public String faltas = "";

    public GuardarEmpleados(String numEmpleado, String nombreEmpleado, String nombreSupervisor, String entrada, String salida, 
            String turno, String departamento, String admin, String entradaSabado, String horaDoble, String horaTriple, String salidaSabado,
            String horasDiarias, String totalHoras) {
        this.numEmpleado = numEmpleado;
        this.nombreEmpleado = nombreEmpleado;
        this.nombreSupervisor = nombreSupervisor;
        this.entrada = entrada;
        this.salida = salida;
        this.turno = turno;
        this.departamento = departamento;
        this.admin = admin;
        this.entradaSabado = entradaSabado;
        this.horaDoble = horaDoble;
        this.horaTriple = horaTriple;
        this.salidaSabado = salidaSabado;
        this.horasDiarias = horasDiarias;
        this.totalHoras = totalHoras;
    }

    public String getFaltas() {
        return faltas;
    }

    public void setFaltas(String faltas) {
        this.faltas = this.faltas + faltas;
    }

    public String getTotalHoras() {
        return totalHoras;
    }

    public void setTotalHoras(String totalHoras) {
        this.totalHoras = totalHoras;
    }

    public String getHorasDiarias() {
        return horasDiarias;
    }

    public void setHorasDiarias(String horasDiarias) {
        this.horasDiarias = horasDiarias;
    }

    public String getSalidaSabado() {
        return salidaSabado;
    }

    public void setSalidaSabado(String salidaSabado) {
        this.salidaSabado = salidaSabado;
    }
    
    

    public String getAdmin() {
        return admin;
    }

    public void setAdmin(String admin) {
        this.admin = admin;
    }

    public String getNumEmpleado() {
        return numEmpleado;
    }

    public void setNumEmpleado(String numEmpleado) {
        this.numEmpleado = numEmpleado;
    }

    public String getNombreEmpleado() {
        return nombreEmpleado;
    }

    public void setNombreEmpleado(String nombreEmpleado) {
        this.nombreEmpleado = nombreEmpleado;
    }

    public String getNombreSupervisor() {
        return nombreSupervisor;
    }

    public void setNombreSupervisor(String nombreSupervisor) {
        this.nombreSupervisor = nombreSupervisor;
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

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getEntradaSabado() {
        return entradaSabado;
    }

    public void setEntradaSabado(String entradaSabado) {
        this.entradaSabado = entradaSabado;
    }

    public String getHoraDoble() {
        return horaDoble;
    }

    public void setHoraDoble(String horaDoble) {
        this.horaDoble = horaDoble;
    }

    public String getHoraTriple() {
        return horaTriple;
    }

    public void setHoraTriple(String horaTriple) {
        this.horaTriple = horaTriple;
    }
    
}
