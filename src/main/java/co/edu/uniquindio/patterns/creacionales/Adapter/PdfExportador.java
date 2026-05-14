package co.edu.uniquindio.patterns.creacionales.Adapter;

import co.edu.uniquindio.model.Compra;

import java.util.List;

public class PdfExportador implements IExportadorReporte {

    private PdfBoxWriter pdfWriter;

    public PdfExportador() {
        this.pdfWriter = new PdfBoxWriter();
    }

    @Override
    public void exportar(List<Compra> compras, String ruta) {
        String contenido = convertirAContenido(compras);
        pdfWriter.generarPDF(contenido, ruta);
    }

    private String convertirAContenido(List<Compra> compras) {
        StringBuilder sb = new StringBuilder();
        sb.append("REPORTE DE COMPRAS\n\n");

        for (Compra c : compras) {
            sb.append("ID: ").append(c.getIdCompra()).append("\n");
            sb.append("Usuario: ").append(c.getUsuario().getNombre()).append("\n");
            sb.append("Evento: ").append(c.getEvento().getNombre()).append("\n");
            sb.append("Fecha: ").append(c.getFechaCreacion()).append("\n");
            sb.append("Total: ").append(c.getTotal()).append("\n");
            sb.append("Estado: ").append(c.getEstado()).append("\n");
            sb.append("---\n");
        }

        return sb.toString();
    }
}
