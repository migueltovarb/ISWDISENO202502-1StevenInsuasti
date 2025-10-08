package paqueteVeterinaria;

import java.util.Date;

public class ControlVeterinario {
    private Date fecha;
    private TipoControl tipo;
    private String observaciones;

    public ControlVeterinario() {}

    public ControlVeterinario(Date fecha, TipoControl tipo, String observaciones) {
        this.fecha = fecha;
        this.tipo = tipo;
        this.observaciones = observaciones;
    }

    public Date getFecha() { return fecha; }
    public void setFecha(Date fecha) { this.fecha = fecha; }

    public TipoControl getTipo() { return tipo; }
    public void setTipo(TipoControl tipo) { this.tipo = tipo; }

    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }

    public String obtenerDetalle() {
        return "Fecha: " + fecha + " | Tipo: " + tipo + " | Obs: " + observaciones;
    }

    @Override
    public String toString() {
        return obtenerDetalle();
    }
}
