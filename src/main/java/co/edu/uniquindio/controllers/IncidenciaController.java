package co.edu.uniquindio.controllers;

import co.edu.uniquindio.model.Incidencia;
import co.edu.uniquindio.model.enums.TipoIncidencia;
import co.edu.uniquindio.service.interfaces.IIncidenciaServicio;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class IncidenciaController {

    private final IIncidenciaServicio incidenciaServicio;

    public IncidenciaController(IIncidenciaServicio incidenciaServicio) {
        this.incidenciaServicio = incidenciaServicio;
    }

    public record ResultadoIncidencia(boolean exitoso, String mensaje, Incidencia incidencia) {}

    // ── Registro (RF-041) ─────────────────────────────────────────────────────

    public ResultadoIncidencia registrar(TipoIncidencia tipo, String descripcion,
                                         String idEntidadAfectada, String tipoEntidad) {
        if (tipo == null)
            return new ResultadoIncidencia(false, "El tipo de incidencia es obligatorio.", null);
        if (descripcion == null || descripcion.isBlank())
            return new ResultadoIncidencia(false, "La descripción es obligatoria.", null);
        if (idEntidadAfectada == null || idEntidadAfectada.isBlank())
            return new ResultadoIncidencia(false, "Debes indicar la entidad afectada.", null);

        Incidencia incidencia = incidenciaServicio.registrar(
                tipo, descripcion.trim(), idEntidadAfectada.trim(),
                tipoEntidad != null ? tipoEntidad.trim() : "Desconocido");

        return new ResultadoIncidencia(true, "Incidencia registrada correctamente.", incidencia);
    }

    // ── Consultas (RF-042) ────────────────────────────────────────────────────

    /**
     * Filtra incidencias por tipo y/o rango de fechas. Cualquier parámetro
     * puede ser null para ignorar ese filtro.
     */
    public List<Incidencia> filtrar(String tipoStr,
                                    LocalDateTime desde,
                                    LocalDateTime hasta) {
        TipoIncidencia tipo = null;
        if (tipoStr != null && !tipoStr.isBlank() && !"TODOS".equals(tipoStr)) {
            try {
                tipo = TipoIncidencia.valueOf(tipoStr);
            } catch (IllegalArgumentException ignored) {}
        }
        return incidenciaServicio.filtrar(tipo, desde, hasta);
    }

    public List<Incidencia> listarTodas() {
        return incidenciaServicio.listarTodas();
    }

    public Optional<Incidencia> buscarPorId(String idIncidencia) {
        return incidenciaServicio.buscarPorId(idIncidencia);
    }

    public IIncidenciaServicio getIncidenciaServicio() { return incidenciaServicio; }
}