package co.edu.uniquindio.controllers;

import co.edu.uniquindio.model.Asiento;
import co.edu.uniquindio.model.Zona;
import co.edu.uniquindio.service.interfaces.IZonaServicio;

import java.util.List;
import java.util.Optional;

public class ZonaController {

    private final IZonaServicio zonaServicio;

    public ZonaController(IZonaServicio zonaServicio) {
        this.zonaServicio = zonaServicio;
    }

    public record ResultadoZona(boolean exitoso, String mensaje) {}

    // ── CRUD Zona ─────────────────────────────────────────────────────────────

    public ResultadoZona crearZona(String nombre, int capacidad,
                                   double precioBase, String configuracion,
                                   String idRecinto) {
        if (nombre == null || nombre.isBlank())
            return new ResultadoZona(false, "El nombre es obligatorio.");
        if (capacidad <= 0)
            return new ResultadoZona(false, "La capacidad debe ser mayor a 0.");
        if (precioBase < 0)
            return new ResultadoZona(false, "El precio base no puede ser negativo.");

        Zona zona = new Zona(nombre.trim(), capacidad, precioBase,
                configuracion != null ? configuracion.trim() : "");
        zonaServicio.crear(zona, idRecinto);
        return new ResultadoZona(true, "Zona creada: " + nombre);
    }

    public ResultadoZona actualizarZona(String idZona, String nombre,
                                        int capacidad, double precioBase) {
        Optional<Zona> opt = zonaServicio.buscarPorId(idZona);
        if (opt.isEmpty())
            return new ResultadoZona(false, "Zona no encontrada.");

        Zona z = opt.get();
        if (nombre    != null && !nombre.isBlank()) z.setNombre(nombre.trim());
        if (capacidad > 0)                          z.setCapacidad(capacidad);
        if (precioBase >= 0)                        z.setPrecioBase(precioBase);

        zonaServicio.actualizar(z);
        return new ResultadoZona(true, "Zona actualizada.");
    }

    public ResultadoZona eliminarZona(String idZona) {
        if (zonaServicio.buscarPorId(idZona).isEmpty())
            return new ResultadoZona(false, "Zona no encontrada.");
        zonaServicio.eliminar(idZona);
        return new ResultadoZona(true, "Zona eliminada.");
    }

    public List<Zona> listarTodas() {
        return zonaServicio.listarTodas();
    }

    public Optional<Zona> buscarPorId(String idZona) {
        return zonaServicio.buscarPorId(idZona);
    }

    // ── Asientos y ocupación ──────────────────────────────────────────────────

    public List<Asiento> listarAsientosDisponibles(String idZona) {
        return zonaServicio.listarAsientosDisponibles(idZona);
    }

    public double calcularOcupacion(String idZona) {
        return zonaServicio.calcularOcupacion(idZona);
    }

    public IZonaServicio getZonaServicio() { return zonaServicio; }
}