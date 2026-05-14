package co.edu.uniquindio.patterns.estructurales.Facade;


import co.edu.uniquindio.model.*;
import co.edu.uniquindio.patterns.creacionales.Builder.CompraBuilder;
import co.edu.uniquindio.patterns.creacionales.FactoryMethod.EntradaFactory;
import co.edu.uniquindio.patterns.creacionales.FactoryMethod.EntradaGeneralFactory;
import co.edu.uniquindio.patterns.creacionales.FactoryMethod.EntradaNumeradaFactory;
import co.edu.uniquindio.patterns.creacionales.FactoryMethod.EntradaVIPFactory;
import co.edu.uniquindio.patterns.creacionales.Singleton.GestorSesion;

public class PlatformFacade {

    private GestorSesion gestorSesion;

    public PlatformFacade() {
        this.gestorSesion = GestorSesion.getInstance();
    }

    public Compra crearCompraSimple(Evento evento, Zona zona, Asiento asiento,
                                    String tipoEntrada, boolean agregarParqueadero,
                                    boolean agregarSeguro) {
        Usuario usuario = gestorSesion.getUsuarioActual();

        EntradaFactory factory = resolverFactory(tipoEntrada);
        Entrada entrada = factory.crearEntrada(zona, asiento);

        CompraBuilder builder = new CompraBuilder()
                .paraUsuario(usuario)
                .enEvento(evento)
                .agregarEntrada(entrada);

        if (agregarParqueadero) {
            builder.agregarServicio("Parqueadero");
        }
        if (agregarSeguro) {
            builder.agregarServicio("SeguroCancelacion");
        }

        return builder.build();
    }

    private EntradaFactory resolverFactory(String tipoEntrada) {
        switch (tipoEntrada.toUpperCase()) {
            case "VIP":
                return new EntradaVIPFactory();
            case "NUMERADA":
                return new EntradaNumeradaFactory();
            default:
                return new EntradaGeneralFactory();
        }
    }
}
