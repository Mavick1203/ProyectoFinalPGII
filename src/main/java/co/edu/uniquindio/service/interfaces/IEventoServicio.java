package co.edu.uniquindio.service.interfaces;

import co.edu.uniquindio.model.Evento;
import co.edu.uniquindio.model.enums.CategoriaEvento;
import co.edu.uniquindio.model.enums.EstadoEvento;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface IEventoServicio {

    void crear(Evento evento);
    Optional<Evento> buscarPorId(String idEvento);
    List<Evento> listarTodos();
    List<Evento> listarPublicados();

    /** Filtra por cualquier combinación de criterios (null = ignorar ese filtro). */
    List<Evento> filtrar(String ciudad, CategoriaEvento categoria,
                         EstadoEvento estado,
                         LocalDateTime desde, LocalDateTime hasta);

    void publicar(String idEvento);
    void pausar(String idEvento);
    void cancelar(String idEvento);
    void finalizar(String idEvento);
    void actualizar(Evento evento);
    void eliminar(String idEvento);
}
