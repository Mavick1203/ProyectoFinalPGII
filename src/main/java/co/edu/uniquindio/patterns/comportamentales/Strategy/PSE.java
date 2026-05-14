package co.edu.uniquindio.patterns.comportamentales.Strategy;

import co.edu.uniquindio.model.Compra;

public class PSE implements IMetodoPago {

    private String banco;

    public PSE(String banco) {
        this.banco = banco;
    }

    @Override
    public boolean procesarPago(Compra compra, double monto) {
        System.out.println("Procesando pago por PSE con banco " + banco
                + " por $" + monto);
        return true;
    }

    @Override
    public String getNombre() {
        return "PSE";
    }
}
