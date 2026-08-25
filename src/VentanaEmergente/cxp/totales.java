package VentanaEmergente.cxp;

import java.util.Stack;

public class totales {
    public double isr;
    public double iva;
    public double sub;
    public double total;
    public Stack<articulos> articulos = new Stack<>();

    public totales(double isr, double iva, double sub, double total, Stack<articulos> articulos) {
        this.isr = isr;
        this.iva = iva;
        this.sub = sub;
        this.total = total;
        this.articulos = articulos;
    }
    
    public double getIsr() {
        return isr;
    }

    public void setIsr(double isr) {
        this.isr = isr;
    }

    public double getIva() {
        return iva;
    }

    public void setIva(double iva) {
        this.iva = iva;
    }

    public double getSub() {
        return sub;
    }

    public void setSub(double sub) {
        this.sub = sub;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public Stack<articulos> getArticulos() {
        return articulos;
    }

    public void setArticulos(Stack<articulos> articulos) {
        this.articulos = articulos;
    }

}
