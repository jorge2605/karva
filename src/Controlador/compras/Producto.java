package Controlador.compras;

import java.math.BigDecimal;

public class Producto {
    
    private int item;
    private String numeroParte;
    private String descripcion;
    private String unidad;
    private int cantidad;
    private BigDecimal precioUnitario;

    public int getItem() {
        return item;
    }

    public String getNumeroParte() {
        return numeroParte;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getUnidad() {
        return unidad;
    }

    public int getCantidad() {
        return cantidad;
    }

    public BigDecimal getPrecioUnitario() {
        return precioUnitario;
    }
    
    public BigDecimal getTotal() {
        return precioUnitario.multiply(
                BigDecimal.valueOf(cantidad)
        );
    }
    
    public Producto(int item, String numeroParte, String descripcion, String unidad, int cantidad, BigDecimal precioUnitario) {
        this.item = item;
        this.numeroParte = numeroParte;
        this.descripcion = descripcion;
        this.unidad = unidad;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
    }
    
    
    
}
