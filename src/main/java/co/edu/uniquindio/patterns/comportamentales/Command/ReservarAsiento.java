package co.edu.uniquindio.patterns.comportamentales.Command;

import co.edu.uniquindio.model.Asiento;

public class ReservarAsiento implements IComandoCompra {

    private Asiento asiento;
    private boolean ejecutando;

    public ReservarAsiento(Asiento asiento) {
        this.asiento = asiento;
        this.ejecutando = false;
    }

    @Override
    public void ejecutar() {
        ejecutando = asiento.reservar();
    }

    @Override
    public void deshacer() {
        if (ejecutando) {
            asiento.liberar();
            ejecutando = false;
        }
    }

    @Override
    public String getDescripcion() {
        return "Reservar asiento " + asiento.getFila() + "-" + asiento.getNumero();
    }
}
