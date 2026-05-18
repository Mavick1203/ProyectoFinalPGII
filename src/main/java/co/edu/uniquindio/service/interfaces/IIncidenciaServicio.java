package co.edu.uniquindio.service.interfaces;

import co.edu.uniquindio.model.Incidencia;
import co.edu.uniquindio.model.enums.TipoIncidencia;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface IIncidenciaServicio {

    /** Registra una nueva incidencia (RF-041). */
    Incidencia registrar(TipoIncidencia tipo, String descripcion,
                         String idEntidadAfectada, String tipoEntidad);

    Optional<Incidencia> buscarPorId(String idIncidencia);

    /** Consulta por rango de fechas y/o tipo (RF-042). null = ignorar ese filtro. */
    List<Incidencia> filtrar(TipoIncidencia tipo,
                             LocalDateTime desde,
                             LocalDateTime hasta);

    List<Incidencia> listarTodas();
}