package co.edu.uniquindio.patterns.estructurales.Adapter;

import co.edu.uniquindio.model.Compra;

import java.util.List;

public class CsvExportador implements IExportadorReporte {

    private ApachePoiCsvWriter poiWriter;

    public CsvExportador() {
        this.poiWriter = new ApachePoiCsvWriter();
    }

    @Override
    public void exportar(List<Compra> compras, String ruta) {
        String[][] datos = convertirADatos(compras);
        poiWriter.escribirCSV(datos, ruta);
    }

    private String[][] convertirADatos(List<Compra> compras) {
        String[][] datos = new String[compras.size() + 1][];

        datos[0] = new String[]{"ID Compra", "Usuario", "Evento", "Fecha", "Total", "Estado"};

        for (int i = 0; i < compras.size(); i++) {
            Compra c = compras.get(i);
            datos[i + 1] = new String[]{
                    c.getIdCompra(),
                    c.getUsuario().getNombre(),
                    c.getEvento().getNombre(),
                    c.getFechaCreacion().toString(),
                    String.valueOf(c.getTotal()),
                    c.getEstado().toString()
            };
        }

        return datos;
    }


}

