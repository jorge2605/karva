package VentanaEmergente.cxp;

public class articulos {
    public String codigo;
    public double precio;
    public double cantidad;
    public double total;
    public String proveedor;

    public articulos(String codigo, double precio, double cantidad, double total, String proveedor) {
        this.codigo = codigo;
        this.precio = precio;
        this.cantidad = cantidad;
        this.total = total;
        this.proveedor = proveedor;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getCodigo() {
        return codigo;
    }

    public double getPrecio() {
        return precio;
    }

    public double getCantidad() {
        return cantidad;
    }

    public double getTotal() {
        return total;
    }

    public String getProveedor() {
        return proveedor;
    }

    public void setProveedor(String proveedor) {
        this.proveedor = proveedor;
    }

}
