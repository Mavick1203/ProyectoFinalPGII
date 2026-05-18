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
        if (precioFinal < 0)
            throw new IllegalArgumentException("El precio final no puede ser negativo");
        this.idEntrada   = "ENT-" + UUID.randomUUID().toString()
                .substring(0, 8).toUpperCase();
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
