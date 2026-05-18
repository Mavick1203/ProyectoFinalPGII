package co.edu.uniquindio.patterns.comportamentales.Strategy;

import co.edu.uniquindio.model.Compra;


public class ProcesadorPago {

    private IMetodoPago estrategia;


    public ProcesadorPago(IMetodoPago estrategia) {
        if (estrategia == null)
            throw new IllegalArgumentException("La estrategia de pago no puede ser null.");
        this.estrategia = estrategia;
    }


    public void setEstrategia(IMetodoPago estrategia) {
        if (estrategia == null)
            throw new IllegalArgumentException("La estrategia de pago no puede ser null.");
        this.estrategia = estrategia;
    }


    public boolean ejecutarPago(Compra compra) {
        if (compra == null)
            throw new IllegalArgumentException("La compra no puede ser null.");
        System.out.println("[ProcesadorPago] Usando método: " + estrategia.getNombre());
        return estrategia.procesarPago(compra, compra.getTotal());
    }

    public IMetodoPago getEstrategia() {
        return estrategia;
    }
}