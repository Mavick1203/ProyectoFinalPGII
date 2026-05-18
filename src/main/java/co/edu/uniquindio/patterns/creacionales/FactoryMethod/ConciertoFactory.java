package co.edu.uniquindio.patterns.creacionales.FactoryMethod;

import co.edu.uniquindio.model.Evento;
import co.edu.uniquindio.model.Recinto;
import co.edu.uniquindio.model.enums.CategoriaEvento;

import java.time.LocalDateTime;


public final class ConciertoFactory implements EventoFactory {

    @Override
    public Evento crearEvento(String nombre, String descripcion,
                              String ciudad, LocalDateTime fechaHora,
                              Recinto recinto) {
        Evento evento = new Evento(nombre, CategoriaEvento.CONCIERTO,
                descripcion, ciudad, fechaHora, recinto);

        evento.setPoliticas(
                "Política de concierto: " +
                        "No se permiten reembolsos pasadas 48 horas del evento. " +
                        "Se requiere identificación para el ingreso. " +
                        "Prohibido ingresar alimentos o bebidas externas. " +
                        "Los menores de 16 años deben ir acompañados de un adulto."
        );
        return evento;
    }

    @Override
    public CategoriaEvento getCategoria() {
        return CategoriaEvento.CONCIERTO;
    }
}