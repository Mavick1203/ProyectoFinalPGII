package co.edu.uniquindio.patterns.creacionales.FactoryMethod;

import co.edu.uniquindio.model.Evento;
import co.edu.uniquindio.model.Recinto;
import co.edu.uniquindio.model.enums.CategoriaEvento;

import java.time.LocalDateTime;

public final class DeporteFactory implements EventoFactory {

    @Override
    public Evento crearEvento(String nombre, String descripcion,
                              String ciudad, LocalDateTime fechaHora,
                              Recinto recinto) {
        Evento evento = new Evento(nombre, CategoriaEvento.DEPORTE,
                descripcion, ciudad, fechaHora, recinto);

        evento.setPoliticas(
                "Política de evento deportivo: " +
                        "Sin reembolso por cancelación por condiciones climáticas. " +
                        "Si el evento se suspende más de 30 minutos, se reprogramará. " +
                        "Prohibido el ingreso de objetos contundentes o pirotécnicos. " +
                        "Prohibido el ingreso de bebidas alcohólicas."
        );
        return evento;
    }

    @Override
    public CategoriaEvento getCategoria() {
        return CategoriaEvento.DEPORTE;
    }
}