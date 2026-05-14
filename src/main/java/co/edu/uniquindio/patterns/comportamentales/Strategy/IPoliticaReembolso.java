package co.edu.uniquindio.patterns.comportamentales.Strategy;

import co.edu.uniquindio.model.Compra;

public interface IPoliticaReembolso {
    double calcularReembolso(Compra compra);
    String getDescripcion();
}