package co.edu.uniquindio.patterns.creacionales.FactoryMethod;

import co.edu.uniquindio.model.Evento;
import co.edu.uniquindio.model.Recinto;
import co.edu.uniquindio.model.enums.CategoriaEvento;

import java.time.LocalDateTime;

public final class FestivalFactory implements EventoFactory {

    @Override
    public Evento crearEvento(String nombre, String descripcion,
                              String ciudad, LocalDateTime fechaHora,
                              Recinto recinto) {
        Evento evento = new Evento(nombre, CategoriaEvento.FESTIVAL,
                descripcion, ciudad, fechaHora, recinto);

        evento.setPoliticas(
                "Política de festival: " +
                        "Reembolso del 70% disponible hasta 7 días antes del evento. " +
                        "Acceso general sin asiento asignado. " +
                        "La entrada es válida para todos los días del festival. " +
                        "Se permite el ingreso y salida durante el evento."
        );
        return evento;
    }

    @Override
    public CategoriaEvento getCategoria() {
        return CategoriaEvento.FESTIVAL;
    }
}