package co.edu.uniquindio.patterns.creacionales.FactoryMethod;

import co.edu.uniquindio.model.Asiento;
import co.edu.uniquindio.model.Entrada;
import co.edu.uniquindio.model.Zona;

import java.util.UUID;

public interface EntradaFactory {
    Entrada crearEntrada(Zona zona, Asiento asiento);

    default String generarId() {
        return "ENT-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    default void validarZona(Zona zona) {
        if (zona == null) {
            throw new IllegalArgumentException("La zona no puede ser nula para crear una entrada");
        }
    }
}
