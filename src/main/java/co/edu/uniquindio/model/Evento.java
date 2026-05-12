package co.edu.uniquindio.model;

import co.edu.uniquindio.model.enums.CategoriaEvento;
import co.edu.uniquindio.model.enums.EstadoEvento;

import java.time.LocalDateTime;
import java.util.UUID;

public class Evento {
    private final String idEvento;
    private String nombre;
    private CategoriaEvento categoria;
    private String descripcion;
    private String ciudad;
    private LocalDateTime fechaHora;
    private EstadoEvento estado;
    private String politicas;
    private Recinto recinto;

    public Evento(String nombre, CategoriaEvento categoria, String descripcion,
                  String ciudad, LocalDateTime fechaHora, Recinto recinto) {
        this.idEvento    = UUID.randomUUID().toString();
        this.nombre      = nombre;
        this.categoria   = categoria;
        this.descripcion = descripcion;
        this.ciudad = ciudad;
        this.fechaHora = fechaHora;
        this.recinto = recinto;
        this.estado = EstadoEvento.BORRADOR;
        this.politicas = "";
    }
    public boolean publicar() {
        if (estado == EstadoEvento.BORRADOR || estado == EstadoEvento.PAUSADO) {
            estado = EstadoEvento.PUBLICADO;
            return true;
        }
        return false;
    }
    public boolean pausar() {
        if (estado == EstadoEvento.PUBLICADO) {
            estado = EstadoEvento.PAUSADO;
            return true;
        }
        return false;
    }
    public boolean cancelar() {
        if (estado != EstadoEvento.FINALIZADO && estado != EstadoEvento.CANCELADO) {
            estado = EstadoEvento.CANCELADO;
            return true;
        }
        return false;
    }
    public boolean finalizar() {
        if (estado == EstadoEvento.PUBLICADO) {
            estado = EstadoEvento.FINALIZADO;
            return true;
        }
        return false;
    }

    public String getIdEvento() {
        return idEvento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public CategoriaEvento getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaEvento categoria) {
        this.categoria = categoria;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public EstadoEvento getEstado() {
        return estado;
    }

    public void setEstado(EstadoEvento estado) {
        this.estado = estado;
    }

    public String getPoliticas() {
        return politicas;
    }

    public void setPoliticas(String politicas) {
        this.politicas = politicas;
    }

    public Recinto getRecinto() {
        return recinto;
    }

    public void setRecinto(Recinto recinto) {
        this.recinto = recinto;
    }

    @Override
    public String toString() {
        return "Evento{" +
                "idEvento='" + idEvento + '\'' +
                ", nombre='" + nombre + '\'' +
                ", categoria=" + categoria +
                ", descripcion='" + descripcion + '\'' +
                ", ciudad='" + ciudad + '\'' +
                ", fechaHora=" + fechaHora +
                ", estado=" + estado +
                ", politicas='" + politicas + '\'' +
                ", recinto=" + recinto +
                '}';
    }
}
