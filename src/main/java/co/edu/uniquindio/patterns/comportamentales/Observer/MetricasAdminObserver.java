package co.edu.uniquindio.patterns.comportamentales.Observer;



import co.edu.uniquindio.model.Evento;
import co.edu.uniquindio.model.enums.EstadoEvento;

public class MetricasAdminObserver implements IEventoObserver {

    @Override
    public void onEstadoCambiado(Evento evento, EstadoEvento estadoAnterior, EstadoEvento estadoNuevo) {
        System.out.println("Métricas actualizadas: El evento '" + evento.getNombre() +
                "' cambió de " + estadoAnterior + " a " + estadoNuevo);
    }
}
