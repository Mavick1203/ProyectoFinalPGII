package co.edu.uniquindio.service.impl;

import co.edu.uniquindio.model.Compra;
import co.edu.uniquindio.model.enums.EstadoCompra;
import co.edu.uniquindio.patterns.estructurales.Adapter.CsvExportador;
import co.edu.uniquindio.patterns.estructurales.Adapter.IExportadorReporte;
import co.edu.uniquindio.patterns.estructurales.Adapter.PdfExportador;
import co.edu.uniquindio.service.interfaces.ICompraServicio;
import co.edu.uniquindio.service.interfaces.IReporteServicio;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ReporteServicioImpl implements IReporteServicio {

    private final ICompraServicio compraServicio;

    public ReporteServicioImpl(ICompraServicio compraServicio) {
        this.compraServicio = compraServicio;
    }

    // ── RF-046: ventas por periodo ────────────────────────────────────────────

    @Override
    public void exportarVentasPorPeriodo(LocalDateTime desde, LocalDateTime hasta,
                                         String formato, String rutaSalida) {
        List<Compra> compras = compraServicio.listarTodas().stream()
                .filter(c -> !c.getFechaCreacion().isBefore(desde)
                        && !c.getFechaCreacion().isAfter(hasta))
                .collect(Collectors.toList());

        resolverExportador(formato).exportar(compras, rutaSalida);
    }

    // ── RF-046: ocupación por zona ────────────────────────────────────────────

    @Override
    public void exportarOcupacionPorZona(String idEvento, String formato, String rutaSalida) {
        List<Compra> compras = compraServicio.listarTodas().stream()
                .filter(c -> c.getEvento().getIdEvento().equals(idEvento))
                .collect(Collectors.toList());

        resolverExportador(formato).exportar(compras, rutaSalida);
    }

    // ── RF-046: ingresos por servicios adicionales ────────────────────────────

    @Override
    public void exportarIngresosPorServicio(LocalDateTime desde, LocalDateTime hasta,
                                            String formato, String rutaSalida) {
        List<Compra> compras = compraServicio.listarTodas().stream()
                .filter(c -> !c.getFechaCreacion().isBefore(desde)
                        && !c.getFechaCreacion().isAfter(hasta))
                .filter(c -> !c.getServiciosAdicionales().isEmpty())
                .collect(Collectors.toList());

        resolverExportador(formato).exportar(compras, rutaSalida);
    }

    // ── RF-046: tasa de cancelación ───────────────────────────────────────────

    @Override
    public void exportarTasaCancelacion(LocalDateTime desde, LocalDateTime hasta,
                                        String formato, String rutaSalida) {
        List<Compra> canceladas = compraServicio.listarTodas().stream()
                .filter(c -> !c.getFechaCreacion().isBefore(desde)
                        && !c.getFechaCreacion().isAfter(hasta))
                .filter(c -> c.getEstado() == EstadoCompra.CANCELADA)
                .collect(Collectors.toList());

        resolverExportador(formato).exportar(canceladas, rutaSalida);
    }

    // ── RF-046: top eventos ───────────────────────────────────────────────────

    @Override
    public void exportarTopEventos(int top, String formato, String rutaSalida) {
        Map<String, Long> ventasPorEvento = compraServicio.listarTodas().stream()
                .collect(Collectors.groupingBy(
                        c -> c.getEvento().getIdEvento(), Collectors.counting()));

        List<Compra> topCompras = compraServicio.listarTodas().stream()
                .filter(c -> ventasPorEvento.containsKey(c.getEvento().getIdEvento()))
                .sorted(Comparator.comparingLong(
                        c -> -ventasPorEvento.get(c.getEvento().getIdEvento())))
                .limit((long) top * 10L)
                .collect(Collectors.toList());

        resolverExportador(formato).exportar(topCompras, rutaSalida);
    }

    // ── Helper ────────────────────────────────────────────────────────────────

    private IExportadorReporte resolverExportador(String formato) {
        if ("PDF".equalsIgnoreCase(formato)) return new PdfExportador();
        return new CsvExportador();
    }
}