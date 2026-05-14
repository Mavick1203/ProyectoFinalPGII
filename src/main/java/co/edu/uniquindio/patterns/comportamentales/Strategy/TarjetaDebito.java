package co.edu.uniquindio.patterns.comportamentales.Strategy;

import co.edu.uniquindio.model.Compra;

public class TarjetaDebito implements IMetodoPago {

    private String numeroTarjeta;

    public TarjetaDebito(String numeroTarjeta) {
        this.numeroTarjeta = numeroTarjeta;
    }

    @Override
    public boolean procesarPago(Compra compra, double monto) {
        System.out.println("Procesando pago con tarjeta de débito " + numeroTarjeta
                + " por $" + monto);
        return true;
    }

    @Override
    public String getNombre() {
        return "Tarjeta de Débito";
    }
}
