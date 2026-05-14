package co.edu.uniquindio.patterns.estructurales.Decorator;

public class Parqueadero extends ServicioAdicionalDecorador {

    private static final double PRECIO = 20000;

    public Parqueadero(IServicioCompra wrapped) {
        super(wrapped);
    }

    @Override
    public double getPrecioTotal() {
        return wrapped.getPrecioTotal() + PRECIO;
    }

    @Override
    public String getDescripcion() {
        return wrapped.getDescripcion() + " + Parqueadero";
    }
}
