package co.edu.uniquindio.patterns.comportamentales.Observer;

import co.edu.uniquindio.model.Evento;
import co.edu.uniquindio.model.enums.EstadoEvento;

public interface IEventoObserver {
    void onEstadoCambiado(Evento evento, EstadoEvento estadoAnterior, EstadoEvento estadoNuevo);
}
