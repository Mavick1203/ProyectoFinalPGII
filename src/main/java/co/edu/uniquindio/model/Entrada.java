package co.edu.uniquindio.model;

import co.edu.uniquindio.model.enums.EstadoEntrada;

import java.util.UUID;

public class Entrada {
    private final String       idEntrada;
    private final Zona         zona;
    private final Asiento      asiento;
    private final double       precioFinal;
    private EstadoEntrada estado;

    public Entrada(Zona zona, Asiento asiento, double precioFinal) {
        this.idEntrada   = UUID.randomUUID().toString();
        this.zona        = zona;
        this.asiento     = asiento;
        this.precioFinal = precioFinal;
        this.estado      = EstadoEntrada.ACTIVA;
    }
    public void marcarUsada()   { estado = EstadoEntrada.USADA; }
    public void anular()        { estado = EstadoEntrada.ANULADA;}

    public String getIdEntrada() {
        return idEntrada;
    }

    public Zona getZona() {
        return zona;
    }

    public Asiento getAsiento() {
        return asiento;
    }

    public double getPrecioFinal() {
        return precioFinal;
    }

    public EstadoEntrada getEstado() {
        return estado;
    }

    public void setEstado(EstadoEntrada estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Entrada{" +
                "idEntrada='" + idEntrada + '\'' +
                ", zona=" + zona +
                ", asiento=" + asiento +
                ", precioFinal=" + precioFinal +
                ", estado=" + estado +
                '}';
    }
}
