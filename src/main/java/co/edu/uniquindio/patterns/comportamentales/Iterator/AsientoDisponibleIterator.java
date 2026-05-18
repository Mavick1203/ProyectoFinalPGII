package co.edu.uniquindio.patterns.comportamentales.Iterator;

import co.edu.uniquindio.model.Asiento;
import co.edu.uniquindio.model.enums.EstadoAsiento;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * RF-033 — Recorre el mapa de asientos filtrando solo los disponibles,
 * sin exponer la estructura interna de la lista.
 */
public class AsientoDisponibleIterator implements Iterator<Asiento> {

    private final List<Asiento> asientos;
    private int indice = 0;

    public AsientoDisponibleIterator(List<Asiento> asientos) {
        this.asientos = asientos;
        avanzarAlSiguienteDisponible();
    }

    @Override
    public boolean hasNext() {
        return indice < asientos.size();
    }

    @Override
    public Asiento next() {
        if (!hasNext()) throw new NoSuchElementException();
        Asiento actual = asientos.get(indice);
        indice++;
        avanzarAlSiguienteDisponible();
        return actual;
    }

    private void avanzarAlSiguienteDisponible() {
        while (indice < asientos.size() &&
                asientos.get(indice).getEstado() != EstadoAsiento.DISPONIBLE) {
            indice++;
        }
    }
}