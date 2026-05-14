package co.edu.uniquindio.patterns.estructurales.Decorator;

public class AccesoPreferencial extends ServicioAdicionalDecorador {

    private static final double PRECIO = 25000;

    public AccesoPreferencial(IServicioCompra wrapped) {
        super(wrapped);
    }

    @Override
    public double getPrecioTotal() {
        return wrapped.getPrecioTotal() + PRECIO;
    }

    @Override
    public String getDescripcion() {
        return wrapped.getDescripcion() + " + Acceso Preferencial";
    }
}
