package co.edu.uniquindio.patterns.comportamentales.Strategy;

import co.edu.uniquindio.model.Compra;

public class SinReembolso implements IPoliticaReembolso {

    @Override
    public double calcularReembolso(Compra compra) {
        return 0.0;
    }

    @Override
    public String getDescripcion() {
        return "Sin reembolso";
    }
}
