package co.edu.uniquindio.model;

import co.edu.uniquindio.model.enums.EstadoCompra;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class Compra {
    private final String idCompra;
    private final Usuario usuario;
    private final Evento evento;
    private final LocalDateTime fechaCreacion;
    private double total;
    private EstadoCompra estado;

    private final List<Entrada> entradas;


    private final List<String>    serviciosAdicionales;

    Compra(Usuario usuario, Evento evento, List<Entrada> entradas,
           List<String> serviciosAdicionales, double total) {
        this.idCompra = UUID.randomUUID().toString();
        this.usuario = usuario;
        this.evento = evento;
        this.fechaCreacion = LocalDateTime.now();
        this.entradas = new ArrayList<>(entradas);
        this.serviciosAdicionales = new ArrayList<>(serviciosAdicionales);
        this.total = total;
        this.estado = EstadoCompra.CREADA;

    }

    public boolean pagar() {
        //mirar
        if (estado == EstadoCompra.CREADA) {
            estado = EstadoCompra.PAGADA;
            entradas.forEach(e -> { /* confirmar asientos en servicio */ });
            return true;
        }
        return false;
    }

    public boolean confirmar() {
        if (estado == EstadoCompra.PAGADA) {
            estado = EstadoCompra.CONFIRMADA;
            return true;
        }
        return false;
    }

    public boolean cancelar() {
        //modificar
        if (estado == EstadoCompra.CREADA || estado == EstadoCompra.PAGADA) {
            estado = EstadoCompra.CANCELADA;
            entradas.forEach(Entrada::anular);
            return true;
        }
        return false;
    }
    public boolean reembolsar() {
        //modificar
        if (estado == EstadoCompra.PAGADA || estado == EstadoCompra.CONFIRMADA) {
            estado = EstadoCompra.REEMBOLSADA;
            entradas.forEach(Entrada::anular);
            return true;
        }
        return false;
    }
    public void marcarIncidencia() {
        estado = EstadoCompra.INCIDENCIA;
    }

    public String getIdCompra() {
        return idCompra;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public Evento getEvento() {
        return evento;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public EstadoCompra getEstado() {
        return estado;
    }

    public void setEstado(EstadoCompra estado) {
        this.estado = estado;
    }

    public List<Entrada> getEntradas() {
        return Collections.unmodifiableList(entradas);
    }

    public List<String> getServiciosAdicionales() {
        return Collections.unmodifiableList(serviciosAdicionales);
    }

    @Override
    public String toString() {
        return "Compra{" +
                "idCompra='" + idCompra + '\'' +
                ", usuario=" + usuario +
                ", evento=" + evento +
                ", fechaCreacion=" + fechaCreacion +
                ", total=" + total +
                ", estado=" + estado +
                ", entradas=" + entradas +
                ", serviciosAdicionales=" + serviciosAdicionales +
                '}';
    }
}
