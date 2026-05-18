package co.edu.uniquindio.service.interfaces;

import co.edu.uniquindio.model.*;

import java.util.List;
import java.util.Optional;

public interface ICompraServicio {

    /**
     * Crea y registra una compra usando Builder + Factory + Decorator internamente.
     * @param tipoEntrada "GENERAL" | "NUMERADA" | "VIP"
     * @param servicios   lista de nombres de servicios adicionales seleccionados
     */
    Compra crearCompra(Usuario usuario, Evento evento,
                       Zona zona, Asiento asiento,
                       String tipoEntrada, List<String> servicios);

    /** Procesa el pago usando Strategy de método de pago. */
    boolean procesarPago(String idCompra, String tipoMetodo, String datosPago);

    boolean cancelar(String idCompra);
    boolean reembolsar(String idCompra);

    Optional<Compra> buscarPorId(String idCompra);
    List<Compra> buscarPorUsuario(String idUsuario);
    List<Compra> listarTodas();
}