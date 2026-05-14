package co.edu.uniquindio.patterns.comportamentales.Command;


import co.edu.uniquindio.model.Compra;

public class CancelarCompra implements IComandoCompra {

    private Compra compra;
    private boolean ejecutando;

    public CancelarCompra(Compra compra) {
        this.compra = compra;
        this.ejecutando = false;
    }

    @Override
    public void ejecutar() {
        ejecutando = compra.cancelar();
    }

    @Override
    public void deshacer() {
        if (ejecutando) {
            compra.reembolsar();
            ejecutando = false;
        }
    }

    @Override
    public String getDescripcion() {
        return "Cancelar compra " + compra.getIdCompra();
    }
}
