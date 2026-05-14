package co.edu.uniquindio.patterns.estructurales.Decorator;

public abstract class ServicioAdicionalDecorador implements IServicioCompra {

    protected IServicioCompra wrapped;

    public ServicioAdicionalDecorador(IServicioCompra wrapped) {
        this.wrapped = wrapped;
    }

    @Override
    public double getPrecioTotal() {
        return wrapped.getPrecioTotal();
    }

    @Override
    public String getDescripcion() {
        return wrapped.getDescripcion();
    }
}
