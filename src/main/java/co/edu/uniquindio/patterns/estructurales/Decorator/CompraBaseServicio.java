package co.edu.uniquindio.patterns.estructurales.Decorator;

public class CompraBaseServicio implements IServicioCompra {

    private double precioEntradas;

    public CompraBaseServicio(double precioEntradas) {
        this.precioEntradas = precioEntradas;
    }

    @Override
    public double getPrecioTotal() {
        return precioEntradas;
    }

    @Override
    public String getDescripcion() {
        return "Entradas";
    }
}
