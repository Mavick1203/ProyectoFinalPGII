package co.edu.uniquindio.patterns.comportamentales.Command;

import co.edu.uniquindio.model.Compra;

public class PagarCompra implements IComandoCompra {

    private Compra compra;
    private boolean ejecutando;

    public PagarCompra(Compra compra) {
        this.compra = compra;
        this.ejecutando = false;
    }

    @Override
    public void ejecutar() {
        ejecutando = compra.pagar();
    }

    @Override
    public void deshacer() {
        if (ejecutando) {
            compra.cancelar();
            ejecutando = false;
        }
    }

    @Override
    public String getDescripcion() {
        return "Pagar compra " + compra.getIdCompra();
    }
}
