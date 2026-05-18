package co.edu.uniquindio.patterns.creacionales.FactoryMethod;

import co.edu.uniquindio.model.Evento;
import co.edu.uniquindio.model.Recinto;
import co.edu.uniquindio.model.enums.CategoriaEvento;

import java.time.LocalDateTime;


public final class TeatroFactory implements EventoFactory {

    @Override
    public Evento crearEvento(String nombre, String descripcion,
                              String ciudad, LocalDateTime fechaHora,
                              Recinto recinto) {
        Evento evento = new Evento(nombre, CategoriaEvento.TEATRO,
                descripcion, ciudad, fechaHora, recinto);

        evento.setPoliticas(
                "Política de teatro: " +
                        "Reembolso total disponible hasta 24 horas antes del evento. " +
                        "Todos los asientos son numerados. " +
                        "Se permite el ingreso solo 15 minutos antes de la función. " +
                        "Apagar dispositivos electrónicos durante la obra."
        );
        return evento;
    }

    @Override
    public CategoriaEvento getCategoria() {
        return CategoriaEvento.TEATRO;
    }
}