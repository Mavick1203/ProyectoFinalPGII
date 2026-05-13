package co.edu.uniquindio.patterns.creacionales.Singleton.FactoryMethod;

import co.edu.uniquindio.model.Asiento;
import co.edu.uniquindio.model.Entrada;
import co.edu.uniquindio.model.Zona;

final class EntradaGeneralFactory implements EntradaFactory {
    @Override
    public Entrada crearEntrada(Zona zona, Asiento asiento) {
        validarZona(zona);
        double precio = zona.getPrecioBase();
        return new Entrada(generarId(), zona, asiento, precio);
    }
}
