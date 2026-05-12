package co.edu.uniquindio.model;

import co.edu.uniquindio.model.enums.TipoTarifa;

import java.util.UUID;

public class Tarifa {
    private String idTarifa;
    private String nombre;
    private String descripcion;
    private double precio;
    private TipoTarifa tipo;
    private Zona zona;

    public Tarifa(String idTarifa,String nombre,String descripcion,double precio,TipoTarifa tipo,Zona zona) {
        this.idTarifa = UUID.randomUUID().toString();
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.tipo = tipo;
        this.zona = zona;
    }

    public String getIdTarifa() {
        return idTarifa;
    }

    public void setIdTarifa(String idTarifa) {
        this.idTarifa = idTarifa;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public TipoTarifa getTipo() {
        return tipo;
    }

    public void setTipo(TipoTarifa tipo) {
        this.tipo = tipo;
    }

    public Zona getZona() {
        return zona;
    }

    public void setZona(Zona zona) {
        this.zona = zona;
    }
}
