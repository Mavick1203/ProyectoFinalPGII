package co.edu.uniquindio.patterns.estructurales.Decorator;

public class VIPDecorador extends ServicioAdicionalDecorador {

    private static final double PRECIO_VIP = 50000;

    public VIPDecorador(IServicioCompra wrapped) {
        super(wrapped);
    }

    @Override
    public double getPrecioTotal() {
        return wrapped.getPrecioTotal() + PRECIO_VIP;
    }

    @Override
    public String getDescripcion() {
        return wrapped.getDescripcion() + " + Acceso VIP";
    }
}
