package co.edu.uniquindio.patterns.creacionales.FactoryMethod;

import co.edu.uniquindio.model.Evento;
import co.edu.uniquindio.model.Recinto;
import co.edu.uniquindio.model.enums.CategoriaEvento;

import java.time.LocalDateTime;

public final class ConferenciaFactory implements EventoFactory {

    @Override
    public Evento crearEvento(String nombre, String descripcion,
                              String ciudad, LocalDateTime fechaHora,
                              Recinto recinto) {
        Evento evento = new Evento(nombre, CategoriaEvento.CONFERENCIA,
                descripcion, ciudad, fechaHora, recinto);

        evento.setPoliticas(
                "Política de conferencia: " +
                        "Reembolso total hasta 72 horas antes del evento. " +
                        "Se entrega certificado de asistencia a los presentes. " +
                        "El material de la conferencia se enviará por correo. " +
                        "Se permite grabación solo para uso personal."
        );
        return evento;
    }

    @Override
    public CategoriaEvento getCategoria() {
        return CategoriaEvento.CONFERENCIA;
    }
}