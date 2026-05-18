package co.edu.uniquindio.controllers;

import co.edu.uniquindio.service.interfaces.IReporteServicio;

import java.time.LocalDateTime;

public class ReporteController {

    private final IReporteServicio reporteServicio;

    public ReporteController(IReporteServicio reporteServicio) {
        this.reporteServicio = reporteServicio;
    }

    public record ResultadoReporte(boolean exitoso, String mensaje) {}

    // ── RF-046 ────────────────────────────────────────────────────────────────

    public ResultadoReporte exportarVentasPorPeriodo(LocalDateTime desde,
                                                     LocalDateTime hasta,
                                                     String formato,
                                                     String rutaSalida) {
        if (desde == null || hasta == null)
            return new ResultadoReporte(false, "Debes indicar el rango de fechas.");
        if (desde.isAfter(hasta))
            return new ResultadoReporte(false, "La fecha de inicio debe ser anterior a la de fin.");
        if (rutaSalida == null || rutaSalida.isBlank())
            return new ResultadoReporte(false, "Debes indicar la ruta de salida.");

        try {
            reporteServicio.exportarVentasPorPeriodo(desde, hasta, formato, rutaSalida);
            return new ResultadoReporte(true,
                    "Reporte de ventas exportado en " + formato + ": " + rutaSalida);
        } catch (Exception e) {
            return new ResultadoReporte(false, "Error al generar el reporte: " + e.getMessage());
        }
    }

    public ResultadoReporte exportarOcupacionPorZona(String idEvento,
                                                     String formato,
                                                     String rutaSalida) {
        if (idEvento == null || idEvento.isBlank())
            return new ResultadoReporte(false, "Debes seleccionar un evento.");

        try {
            reporteServicio.exportarOcupacionPorZona(idEvento, formato, rutaSalida);
            return new ResultadoReporte(true, "Reporte de ocupación exportado: " + rutaSalida);
        } catch (Exception e) {
            return new ResultadoReporte(false, "Error al generar el reporte: " + e.getMessage());
        }
    }

    public ResultadoReporte exportarIngresosPorServicio(LocalDateTime desde,
                                                        LocalDateTime hasta,
                                                        String formato,
                                                        String rutaSalida) {
        if (desde == null || hasta == null)
            return new ResultadoReporte(false, "Debes indicar el rango de fechas.");

        try {
            reporteServicio.exportarIngresosPorServicio(desde, hasta, formato, rutaSalida);
            return new ResultadoReporte(true, "Reporte de ingresos exportado: " + rutaSalida);
        } catch (Exception e) {
            return new ResultadoReporte(false, "Error al generar el reporte: " + e.getMessage());
        }
    }

    public ResultadoReporte exportarTasaCancelacion(LocalDateTime desde,
                                                    LocalDateTime hasta,
                                                    String formato,
                                                    String rutaSalida) {
        if (desde == null || hasta == null)
            return new ResultadoReporte(false, "Debes indicar el rango de fechas.");

        try {
            reporteServicio.exportarTasaCancelacion(desde, hasta, formato, rutaSalida);
            return new ResultadoReporte(true, "Reporte de cancelaciones exportado: " + rutaSalida);
        } catch (Exception e) {
            return new ResultadoReporte(false, "Error al generar el reporte: " + e.getMessage());
        }
    }

    public ResultadoReporte exportarTopEventos(int top, String formato, String rutaSalida) {
        if (top <= 0)
            return new ResultadoReporte(false, "El top debe ser mayor a 0.");

        try {
            reporteServicio.exportarTopEventos(top, formato, rutaSalida);
            return new ResultadoReporte(true,
                    "Top " + top + " eventos exportado: " + rutaSalida);
        } catch (Exception e) {
            return new ResultadoReporte(false, "Error al generar el reporte: " + e.getMessage());
        }
    }

    public IReporteServicio getReporteServicio() { return reporteServicio; }
}