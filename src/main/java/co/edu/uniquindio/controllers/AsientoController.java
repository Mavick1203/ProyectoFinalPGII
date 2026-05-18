package co.edu.uniquindio.controllers;

import co.edu.uniquindio.model.Asiento;
import co.edu.uniquindio.model.enums.EstadoAsiento;
import co.edu.uniquindio.service.interfaces.IAsientoServicio;

import java.util.List;
import java.util.Optional;

public class AsientoController {

    private final IAsientoServicio asientoServicio;

    public AsientoController(IAsientoServicio asientoServicio) {
        this.asientoServicio = asientoServicio;
    }

    public record ResultadoAsiento(boolean exitoso, String mensaje) {}

    // ── CRUD Asiento ──────────────────────────────────────────────────────────

    public ResultadoAsiento crearAsiento(String fila, int numero, String idZona) {
        if (fila == null || fila.isBlank())
            return new ResultadoAsiento(false, "La fila es obligatoria.");
        if (numero <= 0)
            return new ResultadoAsiento(false, "El número de asiento debe ser mayor a 0.");

        Asiento asiento = new Asiento(fila.trim().toUpperCase(), numero);
        asientoServicio.crear(asiento, idZona);
        return new ResultadoAsiento(true, "Asiento creado: " + fila + "-" + numero);
    }

    public ResultadoAsiento eliminarAsiento(String idAsiento) {
        if (asientoServicio.buscarPorId(idAsiento).isEmpty())
            return new ResultadoAsiento(false, "Asiento no encontrado.");
        asientoServicio.eliminar(idAsiento);
        return new ResultadoAsiento(true, "Asiento eliminado.");
    }

    public Optional<Asiento> buscarPorId(String idAsiento) {
        return asientoServicio.buscarPorId(idAsiento);
    }

    // ── Estado ────────────────────────────────────────────────────────────────

    public ResultadoAsiento habilitar(String idAsiento) {
        boolean ok = asientoServicio.cambiarEstado(idAsiento, EstadoAsiento.DISPONIBLE);
        return ok ? new ResultadoAsiento(true, "Asiento habilitado.")
                : new ResultadoAsiento(false, "Asiento no encontrado.");
    }

    public ResultadoAsiento bloquear(String idAsiento) {
        boolean ok = asientoServicio.cambiarEstado(idAsiento, EstadoAsiento.BLOQUEADO);
        return ok ? new ResultadoAsiento(true, "Asiento bloqueado.")
                : new ResultadoAsiento(false, "Asiento no encontrado.");
    }

    public ResultadoAsiento liberar(String idAsiento) {
        return habilitar(idAsiento);
    }

    // ── Consultas ─────────────────────────────────────────────────────────────

    /** Mapa completo de asientos de una zona (RF-033). */
    public List<Asiento> mapaAsientos(String idZona) {
        return asientoServicio.mapaAsientos(idZona);
    }

    public List<Asiento> listarPorZona(String idZona) {
        return asientoServicio.listarPorZona(idZona);
    }

    /** Libera todos los asientos reservados de una zona (útil al cancelar evento). */
    public void liberarZona(String idZona) {
        asientoServicio.liberarZona(idZona);
    }

    public IAsientoServicio getAsientoServicio() { return asientoServicio; }
}