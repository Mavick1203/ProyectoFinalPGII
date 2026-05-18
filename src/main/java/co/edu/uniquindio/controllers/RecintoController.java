package co.edu.uniquindio.controllers;

import co.edu.uniquindio.model.Recinto;
import co.edu.uniquindio.model.Zona;
import co.edu.uniquindio.service.interfaces.IRecintoServicio;

import java.util.List;
import java.util.Optional;

public class RecintoController {

    private final IRecintoServicio recintoServicio;

    public RecintoController(IRecintoServicio recintoServicio) {
        this.recintoServicio = recintoServicio;
    }

    public record ResultadoRecinto(boolean exitoso, String mensaje) {}

    // ── CRUD Recinto ──────────────────────────────────────────────────────────

    public ResultadoRecinto crearRecinto(String nombre, String direccion, String ciudad) {
        if (nombre == null || nombre.isBlank())
            return new ResultadoRecinto(false, "El nombre es obligatorio.");
        if (direccion == null || direccion.isBlank())
            return new ResultadoRecinto(false, "La dirección es obligatoria.");
        if (ciudad == null || ciudad.isBlank())
            return new ResultadoRecinto(false, "La ciudad es obligatoria.");

        Recinto recinto = new Recinto(nombre.trim(), direccion.trim(), ciudad.trim());
        recintoServicio.crear(recinto);
        return new ResultadoRecinto(true, "Recinto creado: " + nombre);
    }

    public ResultadoRecinto actualizarRecinto(String idRecinto, String nombre,
                                              String direccion, String ciudad) {
        Optional<Recinto> opt = recintoServicio.buscarPorId(idRecinto);
        if (opt.isEmpty())
            return new ResultadoRecinto(false, "Recinto no encontrado.");

        Recinto r = opt.get();
        if (nombre    != null && !nombre.isBlank())    r.setNombre(nombre.trim());
        if (direccion != null && !direccion.isBlank()) r.setDireccion(direccion.trim());
        if (ciudad    != null && !ciudad.isBlank())    r.setCiudad(ciudad.trim());

        recintoServicio.actualizar(r);
        return new ResultadoRecinto(true, "Recinto actualizado.");
    }

    public ResultadoRecinto eliminarRecinto(String idRecinto) {
        if (recintoServicio.buscarPorId(idRecinto).isEmpty())
            return new ResultadoRecinto(false, "Recinto no encontrado.");
        recintoServicio.eliminar(idRecinto);
        return new ResultadoRecinto(true, "Recinto eliminado.");
    }

    public List<Recinto> listarTodos() {
        return recintoServicio.listarTodos();
    }

    public Optional<Recinto> buscarPorId(String idRecinto) {
        return recintoServicio.buscarPorId(idRecinto);
    }

    // ── Zonas del recinto ─────────────────────────────────────────────────────

    public ResultadoRecinto agregarZona(String idRecinto, Zona zona) {
        if (recintoServicio.buscarPorId(idRecinto).isEmpty())
            return new ResultadoRecinto(false, "Recinto no encontrado.");
        if (zona == null)
            return new ResultadoRecinto(false, "La zona no puede ser null.");

        recintoServicio.agregarZona(idRecinto, zona);
        return new ResultadoRecinto(true, "Zona agregada al recinto.");
    }

    public ResultadoRecinto eliminarZona(String idRecinto, String idZona) {
        if (recintoServicio.buscarPorId(idRecinto).isEmpty())
            return new ResultadoRecinto(false, "Recinto no encontrado.");

        recintoServicio.eliminarZona(idRecinto, idZona);
        return new ResultadoRecinto(true, "Zona eliminada del recinto.");
    }

    public List<Zona> listarZonas(String idRecinto) {
        return recintoServicio.listarZonas(idRecinto);
    }

    public IRecintoServicio getRecintoServicio() { return recintoServicio; }
}