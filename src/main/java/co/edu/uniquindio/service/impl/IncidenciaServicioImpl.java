package co.edu.uniquindio.service.impl;

import co.edu.uniquindio.model.Incidencia;
import co.edu.uniquindio.model.enums.TipoIncidencia;
import co.edu.uniquindio.service.interfaces.IIncidenciaServicio;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class IncidenciaServicioImpl implements IIncidenciaServicio {

    private final List<Incidencia> incidencias = new ArrayList<>();

    @Override
    public Incidencia registrar(TipoIncidencia tipo, String descripcion,
                                String idEntidadAfectada, String tipoEntidad) {
        Incidencia incidencia = new Incidencia(tipo, descripcion,
                idEntidadAfectada, tipoEntidad);
        incidencias.add(incidencia);
        System.out.println("[IncidenciaServicio] Registrada: " + tipo
                + " en " + tipoEntidad + " #" + idEntidadAfectada);
        return incidencia;
    }

    @Override
    public Optional<Incidencia> buscarPorId(String idIncidencia) {
        return incidencias.stream()
                .filter(i -> i.getIdIncidencia().equals(idIncidencia))
                .findFirst();
    }

    @Override
    public List<Incidencia> filtrar(TipoIncidencia tipo,
                                    LocalDateTime desde,
                                    LocalDateTime hasta) {
        return incidencias.stream()
                .filter(i -> tipo == null || i.getTipo() == tipo)
                .filter(i -> desde == null || !i.getFecha().isBefore(desde))
                .filter(i -> hasta == null || !i.getFecha().isAfter(hasta))
                .collect(Collectors.toList());
    }

    @Override
    public List<Incidencia> listarTodas() {
        return new ArrayList<>(incidencias);
    }
}