package co.edu.uniquindio.service.impl;

import co.edu.uniquindio.model.*;
import co.edu.uniquindio.patterns.comportamentales.Strategy.*;
import co.edu.uniquindio.patterns.creacionales.Builder.CompraBuilder;
import co.edu.uniquindio.patterns.creacionales.FactoryMethod.*;
import co.edu.uniquindio.patterns.estructurales.Decorator.*;
import co.edu.uniquindio.service.interfaces.ICompraServicio;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CompraServicioImpl implements ICompraServicio {

    private final List<Compra> compras = new ArrayList<>();

    @Override
    public Compra crearCompra(Usuario usuario, Evento evento,
                              Zona zona, Asiento asiento,
                              String tipoEntrada, List<String> servicios) {
        EntradaFactory factory = resolverEntradaFactory(tipoEntrada);
        Entrada entrada = factory.crearEntrada(zona, asiento);

        IServicioCompra servicioCompra = new CompraBaseServicio(entrada.getPrecioFinal());
        servicioCompra = aplicarDecorators(servicioCompra, servicios);

        CompraBuilder builder = new CompraBuilder()
                .paraUsuario(usuario)
                .enEvento(evento)
                .agregarEntrada(entrada);

        if (servicios != null) {
            for (String servicio : servicios) {
                builder.agregarServicio(servicio);
            }
        }

        Compra compra = builder.build();
        compra.setTotal(servicioCompra.getPrecioTotal());
        compras.add(compra);
        return compra;
    }

    @Override
    public boolean procesarPago(String idCompra, String tipoMetodo, String datosPago) {
        Optional<Compra> compraOpt = buscarPorId(idCompra);
        if (compraOpt.isEmpty()) return false;

        Compra compra = compraOpt.get();
        IMetodoPago metodoPago = resolverMetodoPago(tipoMetodo, datosPago);
        ProcesadorPago procesador = new ProcesadorPago(metodoPago);
        boolean pagado = procesador.ejecutarPago(compra);

        if (pagado) {
            compra.pagar();
        }
        return pagado;
    }

    @Override
    public boolean cancelar(String idCompra) {
        return buscarPorId(idCompra)
                .map(Compra::cancelar)
                .orElse(false);
    }

    @Override
    public boolean reembolsar(String idCompra) {
        return buscarPorId(idCompra)
                .map(Compra::reembolsar)
                .orElse(false);
    }

    @Override
    public Optional<Compra> buscarPorId(String idCompra) {
        return compras.stream()
                .filter(c -> c.getIdCompra().equals(idCompra))
                .findFirst();
    }

    @Override
    public List<Compra> buscarPorUsuario(String idUsuario) {
        return compras.stream()
                .filter(c -> c.getUsuario().getIdUsuario().equals(idUsuario))
                .collect(Collectors.toList());
    }

    @Override
    public List<Compra> listarTodas() {
        return new ArrayList<>(compras);
    }

    // ── Helpers ──────────────────────────────────────────────────────────────

    private EntradaFactory resolverEntradaFactory(String tipoEntrada) {
        if (tipoEntrada == null) return new EntradaGeneralFactory();
        switch (tipoEntrada.toUpperCase()) {
            case "VIP":      return new EntradaVIPFactory();
            case "NUMERADA": return new EntradaNumeradaFactory();
            default:         return new EntradaGeneralFactory();
        }
    }

    private IServicioCompra aplicarDecorators(IServicioCompra base, List<String> servicios) {
        if (servicios == null) return base;
        IServicioCompra resultado = base;
        for (String servicio : servicios) {
            switch (servicio.toUpperCase()) {
                case "VIP":                resultado = new VIPDecorador(resultado); break;
                case "PARQUEADERO":        resultado = new Parqueadero(resultado); break;
                case "SEGUROCANCELACION":  resultado = new SeguroCancelacion(resultado); break;
                case "MERCHANDISING":      resultado = new Merchandising(resultado); break;
                case "ACCESOPREFERENCIAL": resultado = new AccesoPreferencial(resultado); break;
            }
        }
        return resultado;
    }

    private IMetodoPago resolverMetodoPago(String tipoMetodo, String datosPago) {
        if (tipoMetodo == null) return new Efectivo();
        switch (tipoMetodo.toUpperCase()) {
            case "TARJETACREDITO": return new TarjetaCredito(datosPago);
            case "TARJETADEBITO":  return new TarjetaDebito(datosPago);
            case "PSE":            return new PSE(datosPago);
            default:               return new Efectivo();
        }
    }
}