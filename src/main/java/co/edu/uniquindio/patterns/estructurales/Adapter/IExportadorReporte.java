package co.edu.uniquindio.patterns.estructurales.Adapter;

import co.edu.uniquindio.model.Compra;

import java.util.List;

public interface IExportadorReporte {
    void exportar(List<Compra> compras, String ruta);
}
