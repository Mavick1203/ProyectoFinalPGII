package co.edu.uniquindio.service.impl;

import co.edu.uniquindio.model.Evento;
import co.edu.uniquindio.model.enums.CategoriaEvento;
import co.edu.uniquindio.model.enums.EstadoEvento;
import co.edu.uniquindio.service.interfaces.IEventoServicio;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class EventoServicioImpl implements IEventoServicio {

    private final List<Evento> eventos = new ArrayList<>();

    @Override
    public void crear(Evento evento) {
        if (evento == null)
            throw new IllegalArgumentException("El evento no puede ser null.");
        eventos.add(evento);
    }

    @Override
    public Optional<Evento> buscarPorId(String idEvento) {
        return eventos.stream()
                .filter(e -> e.getIdEvento().equals(idEvento))
                .findFirst();
    }

    @Override
    public List<Evento> listarTodos() {
        return new ArrayList<>(eventos);
    }

    @Override
    public List<Evento> listarPublicados() {
        return eventos.stream()
                .filter(e -> e.getEstado() == EstadoEvento.PUBLICADO)
                .collect(Collectors.toList());
    }

    @Override
    public List<Evento> filtrar(String ciudad, CategoriaEvento categoria,
                                EstadoEvento estado,
                                LocalDateTime desde, LocalDateTime hasta) {
        return eventos.stream()
                .filter(e -> ciudad == null || e.getCiudad().equalsIgnoreCase(ciudad))
                .filter(e -> categoria == null || e.getCategoria() == categoria)
                .filter(e -> estado == null || e.getEstado() == estado)
                .filter(e -> desde == null || !e.getFechaHora().isBefore(desde))
                .filter(e -> hasta == null || !e.getFechaHora().isAfter(hasta))
                .collect(Collectors.toList());
    }

    @Override
    public void publicar(String idEvento) {
        buscarPorId(idEvento).ifPresent(Evento::publicar);
    }

    @Override
    public void pausar(String idEvento) {
        buscarPorId(idEvento).ifPresent(Evento::pausar);
    }

    @Override
    public void cancelar(String idEvento) {
        buscarPorId(idEvento).ifPresent(Evento::cancelar);
    }

    @Override
    public void finalizar(String idEvento) {
        buscarPorId(idEvento).ifPresent(Evento::finalizar);
    }

    @Override
    public void actualizar(Evento evento) {
        // El objeto ya está en la lista por referencia
    }

    @Override
    public void eliminar(String idEvento) {
        eventos.removeIf(e -> e.getIdEvento().equals(idEvento));
    }
}