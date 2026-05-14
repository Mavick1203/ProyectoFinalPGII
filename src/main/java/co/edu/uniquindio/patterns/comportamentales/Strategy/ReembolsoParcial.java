package co.edu.uniquindio.patterns.comportamentales.Strategy;

import co.edu.uniquindio.model.Compra;

public class ReembolsoParcial implements IPoliticaReembolso {

    private double porcentaje;

    public ReembolsoParcial(double porcentaje) {
        this.porcentaje = porcentaje;
    }

    @Override
    public double calcularReembolso(Compra compra) {
        return compra.getTotal() * porcentaje;
    }

    @Override
    public String getDescripcion() {
        return "Reembolso parcial del " + (porcentaje * 100) + "%";
    }
}