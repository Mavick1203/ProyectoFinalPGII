package co.edu.uniquindio.service.impl;

import co.edu.uniquindio.model.Compra;
import co.edu.uniquindio.model.Entrada;
import co.edu.uniquindio.service.interfaces.IEntradaServicio;
import co.edu.uniquindio.service.interfaces.ICompraServicio;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class EntradaServicioImpl implements IEntradaServicio {

    /** Mapa idCompra -> lista de entradas. */
    private final Map<String, List<Entrada>> entradasPorCompra = new HashMap<>();
    private final ICompraServicio compraServicio;

    public EntradaServicioImpl(ICompraServicio compraServicio) {
        this.compraServicio = compraServicio;
    }

    @Override
    public void registrar(Entrada entrada, String idCompra) {
        if (entrada == null)
            throw new IllegalArgumentException("La entrada no puede ser null.");
        entradasPorCompra.computeIfAbsent(idCompra, k -> new ArrayList<>()).add(entrada);
    }

    @Override
    public Optional<Entrada> buscarPorId(String idEntrada) {
        return entradasPorCompra.values().stream()
                .flatMap(List::stream)
                .filter(e -> e.getIdEntrada().equals(idEntrada))
                .findFirst();
    }

    @Override
    public List<Entrada> listarPorCompra(String idCompra) {
        return new ArrayList<>(entradasPorCompra.getOrDefault(idCompra, List.of()));
    }

    @Override
    public List<Entrada> listarPorEvento(String idEvento) {
        return compraServicio.listarTodas().stream()
                .filter(c -> c.getEvento().getIdEvento().equals(idEvento))
                .flatMap(c -> listarPorCompra(c.getIdCompra()).stream())
                .collect(Collectors.toList());
    }

    @Override
    public boolean anular(String idEntrada) {
        return buscarPorId(idEntrada).map(e -> {
            e.anular();
            return true;
        }).orElse(false);
    }
}