package co.edu.uniquindio.model;

import co.edu.uniquindio.model.enums.EstadoAsiento;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class Zona {
    private final String idZona;
    private String nombre;
    private int capacidad;
    private double precioBase;

    private String configuracionAsientos;

    private final List<Asiento> asientos;
    private final List<Tarifa> tarifas;

    public Zona(String nombre, int capacidad, double precioBase, String configuracionAsientos) {
        this.idZona                = UUID.randomUUID().toString();
        this.nombre                = nombre;
        this.capacidad             = capacidad;
        this.precioBase            = precioBase;
        this.configuracionAsientos = configuracionAsientos;
        this.asientos              = new ArrayList<>();
        this.tarifas              = new ArrayList<>();
    }

    public void agregarAsiento(Asiento asiento) {
        asientos.add(asiento);
    }
    // reemplazar contarDisponibles()
    public long contarDisponibles() {
        return asientos.stream()
                .filter(a -> a.getEstado() == EstadoAsiento.DISPONIBLE)
                .count();
    }

    // reemplazar getPorcentajeOcupacion()
    public double getPorcentajeOcupacion() {
        if (asientos.isEmpty()) return 0.0;
        long ocupados = asientos.stream()
                .filter(a -> a.getEstado() != EstadoAsiento.DISPONIBLE)
                .count();
        return (double) ocupados / asientos.size() * 100;
    }
    public void agregarTarifa(Tarifa tarifa) {
        //corregir
        tarifas.add(tarifa);
    }

    public String getIdZona() {
        return idZona;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public double getPrecioBase() {
        return precioBase;
    }

    public void setPrecioBase(double precioBase) {
        this.precioBase = precioBase;
    }

    public String getConfiguracionAsientos() {
        return configuracionAsientos;
    }

    public void setConfiguracionAsientos(String configuracionAsientos) {
        this.configuracionAsientos = configuracionAsientos;
    }

    public List<Asiento> getAsientos() {
        return Collections.unmodifiableList(asientos);
    }

    public List<Tarifa> getTarifas() {
        return Collections.unmodifiableList(tarifas);
    }

    @Override
    public String toString() {
        return "Zona{" +
                "idZona='" + idZona + '\'' +
                ", nombre='" + nombre + '\'' +
                ", capacidad=" + capacidad +
                ", precioBase=" + precioBase +
                ", configuracionAsientos='" + configuracionAsientos + '\'' +
                ", asientos=" + asientos +
                ", tarifas=" + tarifas +
                '}';
    }
}
