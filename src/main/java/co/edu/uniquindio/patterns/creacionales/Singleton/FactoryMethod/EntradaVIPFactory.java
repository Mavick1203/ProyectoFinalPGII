package co.edu.uniquindio.patterns.creacionales.Singleton.FactoryMethod;

import co.edu.uniquindio.model.Asiento;
import co.edu.uniquindio.model.Entrada;
import co.edu.uniquindio.model.Zona;

final class EntradaVIPFactory implements EntradaFactory{
    private static final double FACTOR_VIP = 1.50;

    @Override
    public Entrada crearEntrada(Zona zona, Asiento asiento) {
        validarZona(zona);
        double precio = zona.getPrecioBase() * FACTOR_VIP;
        return new Entrada(generarId(), zona, asiento, precio);
    }
}
