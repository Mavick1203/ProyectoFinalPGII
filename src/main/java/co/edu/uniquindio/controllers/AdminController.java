package co.edu.uniquindio.controllers;

import co.edu.uniquindio.model.*;
import co.edu.uniquindio.model.enums.EstadoCompra;
import co.edu.uniquindio.service.interfaces.ICompraServicio;
import co.edu.uniquindio.service.interfaces.IEventoServicio;
import co.edu.uniquindio.service.interfaces.IUsuarioServicio;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AdminController {

    private final IUsuarioServicio usuarioServicio;
    private final IEventoServicio  eventoServicio;
    private final ICompraServicio  compraServicio;

    public AdminController(IUsuarioServicio usuarioServicio,
                           IEventoServicio eventoServicio,
                           ICompraServicio compraServicio) {
        this.usuarioServicio = usuarioServicio;
        this.eventoServicio  = eventoServicio;
        this.compraServicio  = compraServicio;
    }

    // ── Gestión de usuarios ───────────────────────────────────────────────────

    public List<Usuario> listarUsuarios() {
        return usuarioServicio.listarTodos();
    }

    public void eliminarUsuario(String idUsuario) {
        usuarioServicio.eliminar(idUsuario);
    }

    // ── Gestión de eventos ────────────────────────────────────────────────────

    public List<Evento> listarEventos() {
        return eventoServicio.listarTodos();
    }

    public void eliminarEvento(String idEvento) {
        eventoServicio.eliminar(idEvento);
    }

    public void publicarEvento(String idEvento) {
        eventoServicio.publicar(idEvento);
    }

    public void pausarEvento(String idEvento) {
        eventoServicio.pausar(idEvento);
    }

    public void cancelarEvento(String idEvento) {
        eventoServicio.cancelar(idEvento);
    }

    public void finalizarEvento(String idEvento) {
        eventoServicio.finalizar(idEvento);
    }

    // ── Gestión de compras ────────────────────────────────────────────────────

    public List<Compra> listarCompras() {
        return compraServicio.listarTodas();
    }

    public boolean cancelarCompra(String idCompra) {
        return compraServicio.cancelar(idCompra);
    }

    public boolean reembolsarCompra(String idCompra) {
        return compraServicio.reembolsar(idCompra);
    }

    // ── Métricas ──────────────────────────────────────────────────────────────

    public long totalVentas() {
        return compraServicio.listarTodas().stream()
                .filter(c -> c.getEstado() == EstadoCompra.PAGADA
                        || c.getEstado() == EstadoCompra.CONFIRMADA)
                .count();
    }

    public double ingresoTotal() {
        return compraServicio.listarTodas().stream()
                .filter(c -> c.getEstado() == EstadoCompra.PAGADA
                        || c.getEstado() == EstadoCompra.CONFIRMADA)
                .mapToDouble(Compra::getTotal)
                .sum();
    }

    public long totalCancelaciones() {
        return compraServicio.listarTodas().stream()
                .filter(c -> c.getEstado() == EstadoCompra.CANCELADA)
                .count();
    }

    public double tasaCancelacion() {
        long total = compraServicio.listarTodas().size();
        if (total == 0) return 0.0;
        return (double) totalCancelaciones() / total * 100;
    }

    public List<Compra> ventasPorPeriodo(LocalDateTime desde, LocalDateTime hasta) {
        return compraServicio.listarTodas().stream()
                .filter(c -> !c.getFechaCreacion().isBefore(desde)
                        && !c.getFechaCreacion().isAfter(hasta))
                .collect(Collectors.toList());
    }

    public Map<String, Long> ventasPorEvento() {
        return compraServicio.listarTodas().stream()
                .collect(Collectors.groupingBy(
                        c -> c.getEvento().getNombre(),
                        Collectors.counting()
                ));
    }

    public Map<String, Double> ingresosPorEvento() {
        return compraServicio.listarTodas().stream()
                .filter(c -> c.getEstado() == EstadoCompra.PAGADA
                        || c.getEstado() == EstadoCompra.CONFIRMADA)
                .collect(Collectors.groupingBy(
                        c -> c.getEvento().getNombre(),
                        Collectors.summingDouble(Compra::getTotal)
                ));
    }

    public IUsuarioServicio getUsuarioServicio() { return usuarioServicio; }
    public IEventoServicio  getEventoServicio()  { return eventoServicio; }
    public ICompraServicio  getCompraServicio()  { return compraServicio; }
}