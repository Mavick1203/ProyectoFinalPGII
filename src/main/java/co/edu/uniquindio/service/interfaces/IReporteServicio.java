package co.edu.uniquindio.service.interfaces;

import java.time.LocalDateTime;

public interface IReporteServicio {

    /**
     * Exporta ventas del periodo a CSV o PDF.
     * @param formato "CSV" | "PDF"
     * @param rutaSalida ruta completa del archivo de salida
     */
    void exportarVentasPorPeriodo(LocalDateTime desde, LocalDateTime hasta,
                                  String formato, String rutaSalida);

    /** Exporta la ocupación actual por zona de un evento. */
    void exportarOcupacionPorZona(String idEvento, String formato, String rutaSalida);

    /** Exporta ingresos generados por servicios adicionales. */
    void exportarIngresosPorServicio(LocalDateTime desde, LocalDateTime hasta,
                                     String formato, String rutaSalida);

    /** Exporta tasa de cancelación en el periodo. */
    void exportarTasaCancelacion(LocalDateTime desde, LocalDateTime hasta,
                                 String formato, String rutaSalida);

    /** Exporta el ranking de eventos con más ventas. */
    void exportarTopEventos(int top, String formato, String rutaSalida);
}