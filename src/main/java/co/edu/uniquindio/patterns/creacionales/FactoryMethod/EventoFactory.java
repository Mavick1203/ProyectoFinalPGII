package co.edu.uniquindio.patterns.creacionales.FactoryMethod;

import co.edu.uniquindio.model.Evento;
import co.edu.uniquindio.model.Recinto;
import co.edu.uniquindio.model.enums.CategoriaEvento;

import java.time.LocalDateTime;


public interface EventoFactory {

    Evento crearEvento(String nombre, String descripcion,
                       String ciudad, LocalDateTime fechaHora,
                       Recinto recinto);

    CategoriaEvento getCategoria();
}