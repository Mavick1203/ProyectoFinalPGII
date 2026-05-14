package co.edu.uniquindio.patterns.comportamentales.Observer;

import co.edu.uniquindio.model.Evento;
import co.edu.uniquindio.model.enums.EstadoEvento;

import java.util.ArrayList;
import java.util.List;

public class EventoSujeto {

    private Evento evento;
    private List<IEventoObserver> observadores;

    public EventoSujeto(Evento evento) {
        this.evento = evento;
        this.observadores = new ArrayList<>();
    }

    public void suscribir(IEventoObserver observador) {
        observadores.add(observador);
    }

    public void desuscribir(IEventoObserver observador) {
        observadores.remove(observador);
    }

    public void publicar() {
        EstadoEvento anterior = evento.getEstado();
        evento.publicar();
        notificar(anterior, evento.getEstado());
    }

    public void pausar() {
        EstadoEvento anterior = evento.getEstado();
        evento.pausar();
        notificar(anterior, evento.getEstado());
    }

    public void cancelar() {
        EstadoEvento anterior = evento.getEstado();
        evento.cancelar();
        notificar(anterior, evento.getEstado());
    }

    public void finalizar() {
        EstadoEvento anterior = evento.getEstado();
        evento.finalizar();
        notificar(anterior, evento.getEstado());
    }

    private void notificar(EstadoEvento estadoAnterior, EstadoEvento estadoNuevo) {
        for (IEventoObserver observador : observadores) {
            observador.onEstadoCambiado(evento, estadoAnterior, estadoNuevo);
        }
    }
}
