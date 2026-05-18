package co.edu.uniquindio.controllers;


import co.edu.uniquindio.model.Evento;
import co.edu.uniquindio.model.enums.CategoriaEvento;
import co.edu.uniquindio.model.enums.EstadoEvento;
import co.edu.uniquindio.patterns.comportamentales.Observer.EventoSujeto;
import co.edu.uniquindio.patterns.comportamentales.Observer.IncidenciaCancelacionObserver;
import co.edu.uniquindio.patterns.comportamentales.Observer.MetricasAdminObserver;
import co.edu.uniquindio.service.interfaces.IEventoServicio;
import co.edu.uniquindio.service.interfaces.IRecintoServicio;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Controller de Eventos — lógica de negocio y filtrado.
 * No conoce componentes JavaFX.
 */
public class EventoController {

    private final IEventoServicio  eventoServicio;
    private final IRecintoServicio recintoServicio;

    public EventoController(IEventoServicio eventoServicio,
                            IRecintoServicio recintoServicio) {
        this.eventoServicio  = eventoServicio;
        this.recintoServicio = recintoServicio;
    }

    // ── Consultas ─────────────────────────────────────────────────────────────

    public List<Evento> listarPublicados() {
        return eventoServicio.listarPublicados();
    }

    public List<Evento> listarTodos() {
        return eventoServicio.listarTodos();
    }

    public Optional<Evento> buscarPorId(String id) {
        return eventoServicio.buscarPorId(id);
    }

    /**
     * Filtra con cualquier combinación. Pasar null para ignorar ese campo.
     */
    public List<Evento> filtrar(String textoBusqueda, String ciudad,
                                String categoria, LocalDateTime desde,
                                LocalDateTime hasta) {
        CategoriaEvento cat = (categoria == null || categoria.isBlank()
                || "TODOS".equals(categoria))
                ? null : CategoriaEvento.valueOf(categoria);

        String ciudadFiltro = (ciudad == null || "TODAS".equals(ciudad)) ? null : ciudad;

        List<Evento> resultado = eventoServicio.filtrar(
                ciudadFiltro, cat, EstadoEvento.PUBLICADO, desde, hasta);

        // Filtro adicional por texto libre en nombre o descripción
        if (textoBusqueda != null && !textoBusqueda.isBlank()) {
            String texto = textoBusqueda.toLowerCase().trim();
            resultado = resultado.stream()
                    .filter(e -> e.getNombre().toLowerCase().contains(texto)
                            || e.getDescripcion().toLowerCase().contains(texto))
                    .toList();
        }
        return resultado;
    }

    /** Ciudades únicas de los eventos publicados (para el combo de filtro). */
    public List<String> ciudadesDisponibles() {
        return eventoServicio.listarPublicados().stream()
                .map(Evento::getCiudad)
                .distinct()
                .sorted()
                .toList();
    }

    // ── Operaciones de estado (con Observer) ──────────────────────────────────

    public record ResultadoEstado(boolean exitoso, String mensaje) {}

    public ResultadoEstado publicar(String idEvento) {
        return eventoServicio.buscarPorId(idEvento).map(evento -> {
            EventoSujeto sujeto = crearSujeto(evento);
            sujeto.publicar();
            eventoServicio.actualizar(evento);
            return new ResultadoEstado(true, "Evento publicado: " + evento.getNombre());
        }).orElse(new ResultadoEstado(false, "Evento no encontrado."));
    }

    public ResultadoEstado pausar(String idEvento) {
        return eventoServicio.buscarPorId(idEvento).map(evento -> {
            EventoSujeto sujeto = crearSujeto(evento);
            sujeto.pausar();
            eventoServicio.actualizar(evento);
            return new ResultadoEstado(true, "Evento pausado: " + evento.getNombre());
        }).orElse(new ResultadoEstado(false, "Evento no encontrado."));
    }

    public ResultadoEstado cancelar(String idEvento) {
        return eventoServicio.buscarPorId(idEvento).map(evento -> {
            EventoSujeto sujeto = crearSujeto(evento);
            sujeto.cancelar();   // notifica a todos los observers
            eventoServicio.actualizar(evento);
            return new ResultadoEstado(true, "Evento cancelado y compradores notificados.");
        }).orElse(new ResultadoEstado(false, "Evento no encontrado."));
    }

    public ResultadoEstado finalizar(String idEvento) {
        return eventoServicio.buscarPorId(idEvento).map(evento -> {
            EventoSujeto sujeto = crearSujeto(evento);
            sujeto.finalizar();
            eventoServicio.actualizar(evento);
            return new ResultadoEstado(true, "Evento finalizado: " + evento.getNombre());
        }).orElse(new ResultadoEstado(false, "Evento no encontrado."));
    }

    // ── CRUD admin ────────────────────────────────────────────────────────────

    public ResultadoEstado crearEvento(Evento evento) {
        if (evento.getNombre() == null || evento.getNombre().isBlank())
            return new ResultadoEstado(false, "El nombre es obligatorio.");
        if (evento.getRecinto() == null)
            return new ResultadoEstado(false, "Debes asociar un recinto.");
        if (evento.getFechaHora() == null || evento.getFechaHora().isBefore(LocalDateTime.now()))
            return new ResultadoEstado(false, "La fecha debe ser futura.");

        eventoServicio.crear(evento);
        return new ResultadoEstado(true, "Evento creado correctamente.");
    }

    public ResultadoEstado actualizarEvento(Evento evento) {
        eventoServicio.actualizar(evento);
        return new ResultadoEstado(true, "Evento actualizado.");
    }

    public void eliminarEvento(String idEvento) {
        eventoServicio.eliminar(idEvento);
    }

    // ── Helpers ───────────────────────────────────────────────────────────────

    private EventoSujeto crearSujeto(Evento evento) {
        EventoSujeto sujeto = new EventoSujeto(evento);
        sujeto.suscribir(new MetricasAdminObserver());
        sujeto.suscribir(new IncidenciaCancelacionObserver());
        return sujeto;
    }

    public IEventoServicio  getEventoServicio()  { return eventoServicio; }
    public IRecintoServicio getRecintoServicio() { return recintoServicio; }
}
