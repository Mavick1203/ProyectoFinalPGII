package co.edu.uniquindio.patterns.comportamentales.Strategy;

import co.edu.uniquindio.model.Compra;

public class ProcesadorPago {

    private IMetodoPago estrategia;

    public void setEstrategia(IMetodoPago estrategia) {
        this.estrategia = estrategia;
    }

    public boolean ejecutarPago(Compra compra) {
        return estrategia.procesarPago(compra, compra.getTotal());
    }
}
