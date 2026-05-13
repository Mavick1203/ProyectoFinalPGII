package co.edu.uniquindio.patterns.creacionales.Singleton.FactoryMethod;

import co.edu.uniquindio.model.Asiento;
import co.edu.uniquindio.model.Entrada;
import co.edu.uniquindio.model.Zona;

final class EntradaNumeradaFactory implements EntradaFactory {
    private static final double FACTOR_NUMERADA = 1.10;

    @Override
    public Entrada crearEntrada(Zona zona, Asiento asiento) {
        validarZona(zona);
        if (asiento == null) {
            throw new IllegalArgumentException(
                    "EntradaNumeradaFactory requiere un asiento ansignado (no puede ser ull)");
        }
        double precio = zona.getPrecioBase() * FACTOR_NUMERADA;
        return new Entrada(generarId(), zona, asiento, precio);
    }
}
