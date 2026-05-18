package co.edu.uniquindio.patterns.creacionales.Prototype;

import co.edu.uniquindio.model.Zona;
import co.edu.uniquindio.model.Asiento;

/**
 * RF-028/RF-031 — Al configurar un recinto con muchas zonas similares,
 * clonar una zona existente es más eficiente que construirla desde cero.
 */
public class ZonaPrototype implements Cloneable {

    private final Zona zonaOriginal;

    public ZonaPrototype(Zona zonaOriginal) {
        this.zonaOriginal = zonaOriginal;
    }

    /**
     * Crea una nueva Zona con los mismos datos base (nombre, capacidad,
     * precioBase, configuración) pero sin asientos, lista para personalizarse.
     */
    public Zona clonar(String nuevoNombre) {
        Zona clon = new Zona(
                nuevoNombre,
                zonaOriginal.getCapacidad(),
                zonaOriginal.getPrecioBase(),
                zonaOriginal.getConfiguracionAsientos()
        );
        return clon;
    }
}