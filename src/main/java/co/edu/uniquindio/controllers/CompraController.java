package co.edu.uniquindio.controllers;

import co.edu.uniquindio.model.*;
import co.edu.uniquindio.model.enums.EstadoAsiento;
import co.edu.uniquindio.patterns.creacionales.Singleton.GestorSesion;
import co.edu.uniquindio.service.interfaces.ICompraServicio;

import java.util.List;
import java.util.Optional;

public class CompraController {

    private final ICompraServicio compraServicio;

    public CompraController(ICompraServicio compraServicio) {
        this.compraServicio = compraServicio;
    }

    public record ResultadoCompra(boolean exitoso, String mensaje, Compra compra) {}

    public ResultadoCompra crearCompra(Evento evento, Zona zona, Asiento asiento,
                                       String tipoEntrada, List<String> servicios) {
        Usuario usuario = GestorSesion.getInstance().getUsuarioActual();
        if (usuario == null)
            return new ResultadoCompra(false, "No hay sesión activa.", null);
        if (evento == null)
            return new ResultadoCompra(false, "Debes seleccionar un evento.", null);
        if (zona == null)
            return new ResultadoCompra(false, "Debes seleccionar una zona.", null);
        if (asiento.getEstado() != EstadoAsiento.DISPONIBLE)
            return new ResultadoCompra(false, "El asiento seleccionado no está disponible.", null);

        Compra compra = compraServicio.crearCompra(
                usuario, evento, zona, asiento, tipoEntrada, servicios);
        return new ResultadoCompra(true, "Compra creada exitosamente.", compra);
    }

    public ResultadoCompra procesarPago(String idCompra, String tipoMetodo, String datosPago) {
        if (idCompra == null || idCompra.isBlank())
            return new ResultadoCompra(false, "ID de compra inválido.", null);
        if (tipoMetodo == null || tipoMetodo.isBlank())
            return new ResultadoCompra(false, "Debes seleccionar un método de pago.", null);

        boolean pagado = compraServicio.procesarPago(idCompra, tipoMetodo, datosPago);
        if (pagado) {
            Optional<Compra> compra = compraServicio.buscarPorId(idCompra);
            return new ResultadoCompra(true, "Pago procesado exitosamente.",
                    compra.orElse(null));
        }
        return new ResultadoCompra(false, "No se pudo procesar el pago.", null);
    }

    public ResultadoCompra cancelarCompra(String idCompra) {
        if (idCompra == null || idCompra.isBlank())
            return new ResultadoCompra(false, "ID de compra inválido.", null);

        boolean cancelado = compraServicio.cancelar(idCompra);
        if (cancelado)
            return new ResultadoCompra(true, "Compra cancelada exitosamente.", null);
        return new ResultadoCompra(false, "No se pudo cancelar la compra.", null);
    }

    public List<Compra> obtenerHistorial() {
        Usuario usuario = GestorSesion.getInstance().getUsuarioActual();
        if (usuario == null) return List.of();
        return compraServicio.buscarPorUsuario(usuario.getIdUsuario());
    }

    public Optional<Compra> buscarPorId(String idCompra) {
        return compraServicio.buscarPorId(idCompra);
    }

    public List<Compra> listarTodas() {
        return compraServicio.listarTodas();
    }

    public ICompraServicio getCompraServicio() {
        return compraServicio;
    }
}