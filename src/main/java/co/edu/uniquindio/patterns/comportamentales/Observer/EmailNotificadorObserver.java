package co.edu.uniquindio.patterns.comportamentales.Observer;


import co.edu.uniquindio.model.Evento;
import co.edu.uniquindio.model.enums.EstadoEvento;

public class EmailNotificadorObserver implements IEventoObserver {

    private String correoDestinatario;

    public EmailNotificadorObserver(String correoDestinatario) {
        this.correoDestinatario = correoDestinatario;
    }

    @Override
    public void onEstadoCambiado(Evento evento, EstadoEvento estadoAnterior, EstadoEvento estadoNuevo) {
        System.out.println("Enviando email a " + correoDestinatario +
                ": El evento '" + evento.getNombre() +
                "' cambió de " + estadoAnterior + " a " + estadoNuevo);
    }
}
