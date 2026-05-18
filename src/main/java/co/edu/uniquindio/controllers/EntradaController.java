package co.edu.uniquindio.controllers;

import co.edu.uniquindio.model.Entrada;
import co.edu.uniquindio.service.interfaces.IEntradaServicio;

import java.util.List;
import java.util.Optional;

public class EntradaController {

    private final IEntradaServicio entradaServicio;

    public EntradaController(IEntradaServicio entradaServicio) {
        this.entradaServicio = entradaServicio;
    }

    public record ResultadoEntrada(boolean exitoso, String mensaje) {}

    // ── Registro ──────────────────────────────────────────────────────────────

    /** Registra todas las entradas de una compra recién pagada (RF-038). */
    public void registrarEntradasDeCompra(String idCompra,
                                          List<Entrada> entradas) {
        for (Entrada entrada : entradas) {
            entradaServicio.registrar(entrada, idCompra);
        }
    }

    // ── Consultas ─────────────────────────────────────────────────────────────

    /** Entradas de una compra específica (RF-039). */
    public List<Entrada> listarPorCompra(String idCompra) {
        return entradaServicio.listarPorCompra(idCompra);
    }

    /** Entradas de un evento completo (RF-039). */
    public List<Entrada> listarPorEvento(String idEvento) {
        return entradaServicio.listarPorEvento(idEvento);
    }

    public Optional<Entrada> buscarPorId(String idEntrada) {
        return entradaServicio.buscarPorId(idEntrada);
    }

    // ── Anulación ─────────────────────────────────────────────────────────────

    /** Anula una entrada por cancelación o reembolso (RF-040). */
    public ResultadoEntrada anular(String idEntrada) {
        boolean ok = entradaServicio.anular(idEntrada);
        return ok ? new ResultadoEntrada(true, "Entrada anulada correctamente.")
                : new ResultadoEntrada(false, "Entrada no encontrada.");
    }

    public IEntradaServicio getEntradaServicio() { return entradaServicio; }
}