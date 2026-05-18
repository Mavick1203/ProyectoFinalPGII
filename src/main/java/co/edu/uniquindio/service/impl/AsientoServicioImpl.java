package co.edu.uniquindio.service.impl;

import co.edu.uniquindio.model.Asiento;
import co.edu.uniquindio.model.enums.EstadoAsiento;
import co.edu.uniquindio.service.interfaces.IAsientoServicio;
import co.edu.uniquindio.service.interfaces.IZonaServicio;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class AsientoServicioImpl implements IAsientoServicio {

    /** Mapa idZona -> lista de asientos para consultas rápidas por zona. */
    private final Map<String, List<Asiento>> asientosPorZona = new HashMap<>();
    private final IZonaServicio zonaServicio;

    public AsientoServicioImpl(IZonaServicio zonaServicio) {
        this.zonaServicio = zonaServicio;
    }

    @Override
    public void crear(Asiento asiento, String idZona) {
        if (asiento == null)
            throw new IllegalArgumentException("El asiento no puede ser null.");
        asientosPorZona.computeIfAbsent(idZona, k -> new ArrayList<>()).add(asiento);
        zonaServicio.agregarAsiento(idZona, asiento);
    }

    @Override
    public Optional<Asiento> buscarPorId(String idAsiento) {
        return asientosPorZona.values().stream()
                .flatMap(List::stream)
                .filter(a -> a.getIdAsiento().equals(idAsiento))
                .findFirst();
    }

    @Override
    public List<Asiento> listarPorZona(String idZona) {
        return new ArrayList<>(asientosPorZona.getOrDefault(idZona, List.of()));
    }

    @Override
    public void actualizar(Asiento asiento) {
        // El objeto ya está en la lista por referencia
    }

    @Override
    public void eliminar(String idAsiento) {
        asientosPorZona.values().forEach(lista ->
                lista.removeIf(a -> a.getIdAsiento().equals(idAsiento)));
    }

    @Override
    public boolean cambiarEstado(String idAsiento, EstadoAsiento nuevoEstado) {
        return buscarPorId(idAsiento).map(a -> {
            a.setEstado(nuevoEstado);
            return true;
        }).orElse(false);
    }

    @Override
    public void liberarZona(String idZona) {
        listarPorZona(idZona).stream()
                .filter(a -> a.getEstado() == EstadoAsiento.RESERVADO)
                .forEach(Asiento::liberar);
    }

    @Override
    public List<Asiento> mapaAsientos(String idZona) {
        return new ArrayList<>(asientosPorZona.getOrDefault(idZona, List.of()));
    }
}