package co.edu.uniquindio.model;

import co.edu.uniquindio.model.enums.EstadoAsiento;
import co.edu.uniquindio.patterns.estructurales.Composite.IComponenteRecinto;

import java.util.UUID;

public class Asiento implements IComponenteRecinto {
    private final String idAsiento;
    private final String fila;
    private final int numero;
    private EstadoAsiento estado;

    public Asiento(String fila, int numero) {
        this.idAsiento = UUID.randomUUID().toString();
        this.fila = fila;
        this.numero = numero;
        this.estado = EstadoAsiento.DISPONIBLE;
    }
    public boolean reservar() {
        if (estado == EstadoAsiento.DISPONIBLE) {
            estado = EstadoAsiento.RESERVADO;
            return true;
        }
        return false;
    }
    public boolean vender() {
        if (estado == EstadoAsiento.RESERVADO) {
            estado = EstadoAsiento.VENDIDO;
            return true;
        }
        return false;
    }
    public boolean liberar() {
        if (estado == EstadoAsiento.RESERVADO || estado == EstadoAsiento.VENDIDO) {
            estado = EstadoAsiento.DISPONIBLE;
            return true;
        }
        return false;
    }
    public void bloquear(){
        estado = EstadoAsiento.BLOQUEADO;
    }

    public String getIdAsiento() {
        return idAsiento;
    }

    public String getFila() {
        return fila;
    }

    public int getNumero() {
        return numero;
    }

    public EstadoAsiento getEstado() {
        return estado;
    }

    public void setEstado(EstadoAsiento estado) {
        this.estado = estado;
    }

    @Override
    public String getNombre() {
        return fila + "-" + numero;
    }

    @Override
    public int getCapacidad() {
        return 1;
    }

    @Override
    public String toString() {
        return "Asiento{" +
                "idAsiento='" + idAsiento + '\'' +
                ", fila='" + fila + '\'' +
                ", numero=" + numero +
                ", estado=" + estado +
                '}';
    }
}

