package co.edu.uniquindio.patterns.comportamentales.Strategy;

import co.edu.uniquindio.model.Compra;

public class Efectivo implements IMetodoPago {

    @Override
    public boolean procesarPago(Compra compra, double monto) {
        System.out.println("Procesando pago en efectivo por $" + monto);
        return true;
    }

    @Override
    public String getNombre() {
        return "Efectivo";
    }
}
