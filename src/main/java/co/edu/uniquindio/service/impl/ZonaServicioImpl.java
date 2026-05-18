package co.edu.uniquindio.service.impl;

import co.edu.uniquindio.model.Asiento;
import co.edu.uniquindio.model.Zona;
import co.edu.uniquindio.model.enums.EstadoAsiento;
import co.edu.uniquindio.service.interfaces.IZonaServicio;
import co.edu.uniquindio.service.interfaces.IRecintoServicio;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ZonaServicioImpl implements IZonaServicio {

    private final List<Zona>    zonas    = new ArrayList<>();
    private final IRecintoServicio recintoServicio;

    public ZonaServicioImpl(IRecintoServicio recintoServicio) {
        this.recintoServicio = recintoServicio;
    }

    @Override
    public void crear(Zona zona, String idRecinto) {
        if (zona == null)
            throw new IllegalArgumentException("La zona no puede ser null.");
        zonas.add(zona);
        recintoServicio.agregarZona(idRecinto, zona);
    }

    @Override
    public Optional<Zona> buscarPorId(String idZona) {
        return zonas.stream()
                .filter(z -> z.getIdZona().equals(idZona))
                .findFirst();
    }

    @Override
    public List<Zona> listarTodas() {
        return new ArrayList<>(zonas);
    }

    @Override
    public void actualizar(Zona zona) {
        // El objeto ya está en la lista por referencia
    }

    @Override
    public void eliminar(String idZona) {
        zonas.removeIf(z -> z.getIdZona().equals(idZona));
    }

    @Override
    public void agregarAsiento(String idZona, Asiento asiento) {
        buscarPorId(idZona).ifPresent(z -> z.agregarAsiento(asiento));
    }

    @Override
    public List<Asiento> listarAsientosDisponibles(String idZona) {
        return buscarPorId(idZona)
                .map(z -> z.getAsientos().stream()
                        .filter(a -> a.getEstado() == EstadoAsiento.DISPONIBLE)
                        .collect(Collectors.toList()))
                .orElse(List.of());
    }

    @Override
    public double calcularOcupacion(String idZona) {
        return buscarPorId(idZona).map(z -> {
            int total = z.getAsientos().size();
            if (total == 0) return 0.0;
            long ocupados = z.getAsientos().stream()
                    .filter(a -> a.getEstado() != EstadoAsiento.DISPONIBLE)
                    .count();
            return (double) ocupados / total * 100;
        }).orElse(0.0);
    }
}