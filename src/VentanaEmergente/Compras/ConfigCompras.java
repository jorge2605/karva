package VentanaEmergente.Compras;

public class ConfigCompras {

    public String numEmpleado;
    public String numRequisicion;
    public String color;

    public ConfigCompras(String numEmpleado, String numRequisicion, String color) {
        this.numEmpleado = numEmpleado;
        this.numRequisicion = numRequisicion;
        this.color = color;
    }

    public String getNumEmpleado() {
        return numEmpleado;
    }

    public void setNumEmpleado(String numEmpleado) {
        this.numEmpleado = numEmpleado;
    }

    public String getNumRequisicion() {
        return numRequisicion;
    }

    public void setNumRequisicion(String numRequisicion) {
        this.numRequisicion = numRequisicion;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

}
