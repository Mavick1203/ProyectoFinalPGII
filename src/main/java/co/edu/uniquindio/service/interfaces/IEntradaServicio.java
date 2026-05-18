package co.edu.uniquindio.service.interfaces;

import co.edu.uniquindio.model.Entrada;

import java.util.List;
import java.util.Optional;

public interface IEntradaServicio {

    /** Registra una entrada ya creada (asociada a una compra pagada). */
    void registrar(Entrada entrada, String idCompra);

    Optional<Entrada> buscarPorId(String idEntrada);

    /** Todas las entradas de una compra. */
    List<Entrada> listarPorCompra(String idCompra);

    /** Todas las entradas de un evento (a través de sus compras). */
    List<Entrada> listarPorEvento(String idEvento);

    /** Anula la entrada (RF-040). */
    boolean anular(String idEntrada);
}