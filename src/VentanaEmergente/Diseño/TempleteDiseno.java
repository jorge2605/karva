package VentanaEmergente.Diseño;

public class TempleteDiseno {
    
    public String empresa;
    public String disenador;
    public String cliente;
    public String revision;
    public String parte;
    public String descripcion;
    public String material;
    public String dureza;
    public String ensamle;
    public String tratamiento;
    public String cantidad;
    public String codigo;

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getEmpresa() {
        return empresa;
    }

    public String getDisenador() {
        return disenador;
    }

    public String getCliente() {
        return cliente;
    }

    public String getRevision() {
        return revision;
    }

    public String getParte() {
        return parte;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getMaterial() {
        return material;
    }

    public String getDureza() {
        return dureza;
    }

    public String getEnsamle() {
        return ensamle;
    }

    public String getTratamiento() {
        return tratamiento;
    }

    public String getCantidad() {
        return cantidad;
    }
    
    public String[] getValores() {
    return new String[]{
        revision,
        parte,
        descripcion,
        material,
        dureza,
        ensamle,
        tratamiento,
        cantidad
    };
}

    public TempleteDiseno(String empresa, String disenador, String cliente, String revision, String parte, String descripcion, String material, String dureza, String ensamle, String tratamiento, String cantidad, String codigo) {
        this.empresa = empresa;
        this.disenador = disenador;
        this.cliente = cliente;
        this.revision = revision;
        this.parte = parte;
        this.descripcion = descripcion;
        this.material = material;
        this.dureza = dureza;
        this.ensamle = ensamle;
        this.tratamiento = tratamiento;
        this.cantidad = cantidad;
        this.codigo = codigo;
    }

    @Override
    public String toString() {
        return "TempleteDiseno{" + "empresa=" + empresa + ", disenador=" + disenador + ", cliente=" + cliente + ", revision=" + revision + ", parte=" + parte + ", descripcion=" + descripcion + ", material=" + material + ", dureza=" + dureza + ", ensamle=" + ensamle + ", tratamiento=" + tratamiento + ", cantidad=" + cantidad + ", codigo=" + codigo + '}';
    }

    
    
}
