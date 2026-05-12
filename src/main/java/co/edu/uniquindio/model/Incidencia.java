package co.edu.uniquindio.model;

import co.edu.uniquindio.model.enums.TipoIncidencia;

import java.time.LocalDateTime;
import java.util.UUID;

public class Incidencia {
    private final String idIncidencia;
    private final TipoIncidencia tipo;
    private final String descripcion;
    private final LocalDateTime fecha;
    private final String idEntidadAfectada;
    private final String tipoEntidadAfectada;

    public Incidencia(TipoIncidencia tipo, String descripcion,
                      String idEntidadAfectada, String tipoEntidadAfectada) {
        this.idIncidencia = UUID.randomUUID().toString();
        this.tipo = tipo;
        this.descripcion = descripcion;
        this.fecha = LocalDateTime.now();
        this.idEntidadAfectada = idEntidadAfectada;
        this.tipoEntidadAfectada = tipoEntidadAfectada;
    }

    public String getIdIncidencia() {
        return idIncidencia;
    }

    public TipoIncidencia getTipo() {
        return tipo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public String getIdEntidadAfectada() {
        return idEntidadAfectada;
    }

    public String getTipoEntidadAfectada() {
        return tipoEntidadAfectada;
    }

    @Override
    public String toString() {
        return "Incidencia{" +
                "idIncidencia='" + idIncidencia + '\'' +
                ", tipo=" + tipo +
                ", descripcion='" + descripcion + '\'' +
                ", fecha=" + fecha +
                ", idEntidadAfectada='" + idEntidadAfectada + '\'' +
                ", tipoEntidadAfectada='" + tipoEntidadAfectada + '\'' +
                '}';
    }
}
