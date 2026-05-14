package co.edu.uniquindio.patterns.estructurales.Decorator;

public class SeguroCancelacion extends ServicioAdicionalDecorador {

    private static final double PRECIO = 15000;

    public SeguroCancelacion(IServicioCompra wrapped) {
        super(wrapped);
    }

    @Override
    public double getPrecioTotal() {
        return wrapped.getPrecioTotal() + PRECIO;
    }

    @Override
    public String getDescripcion() {
        return wrapped.getDescripcion() + " + Seguro de Cancelación";
    }
}
