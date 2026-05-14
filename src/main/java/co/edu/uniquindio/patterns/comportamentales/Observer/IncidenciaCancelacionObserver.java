package co.edu.uniquindio.patterns.comportamentales.Observer;

import co.edu.uniquindio.model.Evento;
import co.edu.uniquindio.model.Incidencia;
import co.edu.uniquindio.model.enums.EstadoEvento;
import co.edu.uniquindio.model.enums.TipoIncidencia;

public class IncidenciaCancelacionObserver implements IEventoObserver {

    @Override
    public void onEstadoCambiado(Evento evento, EstadoEvento estadoAnterior, EstadoEvento estadoNuevo) {
        if (estadoNuevo == EstadoEvento.CANCELADO) {
            Incidencia incidencia = new Incidencia(
                    TipoIncidencia.CANCELACION_MASIVA,
                    "El evento '" + evento.getNombre() + "' fue cancelado.",
                    evento.getIdEvento(),
                    "Evento"
            );
            System.out.println("Incidencia registrada: " + incidencia.getTipo() +
                    " - " + incidencia.getFecha());
        }
    }
}