package co.edu.uniquindio.patterns.estructurales.Decorator;

public class Merchandising extends ServicioAdicionalDecorador {

    private static final double PRECIO = 30000;

    public Merchandising(IServicioCompra wrapped) {
        super(wrapped);
    }

    @Override
    public double getPrecioTotal() {
        return wrapped.getPrecioTotal() + PRECIO;
    }

    @Override
    public String getDescripcion() {
        return wrapped.getDescripcion() + " + Merchandising";
    }
}
