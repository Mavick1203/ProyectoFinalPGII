package co.edu.uniquindio.patterns.comportamentales.Strategy;

import co.edu.uniquindio.model.Compra;

public interface IMetodoPago {
    boolean procesarPago(Compra compra, double monto);
    String getNombre();
}
