package co.edu.uniquindio.service.impl;

import co.edu.uniquindio.model.Recinto;
import co.edu.uniquindio.model.Zona;
import co.edu.uniquindio.service.interfaces.IRecintoServicio;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RecintoServicioImpl implements IRecintoServicio {

    private final List<Recinto> recintos = new ArrayList<>();

    @Override
    public void crear(Recinto recinto) {
        if (recinto == null)
            throw new IllegalArgumentException("El recinto no puede ser null.");
        recintos.add(recinto);
    }

    @Override
    public Optional<Recinto> buscarPorId(String idRecinto) {
        return recintos.stream()
                .filter(r -> r.getIdRecinto().equals(idRecinto))
                .findFirst();
    }

    @Override
    public List<Recinto> listarTodos() {
        return new ArrayList<>(recintos);
    }

    @Override
    public void actualizar(Recinto recinto) {
        // El objeto ya está en la lista por referencia
    }

    @Override
    public void eliminar(String idRecinto) {
        recintos.removeIf(r -> r.getIdRecinto().equals(idRecinto));
    }

    @Override
    public void agregarZona(String idRecinto, Zona zona) {
        buscarPorId(idRecinto).ifPresent(r -> r.agregarZona(zona));
    }

    @Override
    public void eliminarZona(String idRecinto, String idZona) {
        buscarPorId(idRecinto).ifPresent(r -> {
            r.getZonas().stream()
                    .filter(z -> z.getIdZona().equals(idZona))
                    .findFirst()
                    .ifPresent(r::eliminarZona);
        });
    }

    @Override
    public List<Zona> listarZonas(String idRecinto) {
        return buscarPorId(idRecinto)
                .map(Recinto::getZonas)
                .orElse(List.of());
    }
}