package co.edu.uniquindio.patterns.comportamentales.Strategy;

import co.edu.uniquindio.model.Compra;

public class ReembolsoTotal implements IPoliticaReembolso {

    @Override
    public double calcularReembolso(Compra compra) {
        return compra.getTotal();
    }

    @Override
    public String getDescripcion() {
        return "Reembolso total del 100%";
    }
}
