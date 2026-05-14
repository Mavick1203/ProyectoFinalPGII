package co.edu.uniquindio.patterns.comportamentales.Command;

public interface IComandoCompra {
    void ejecutar();
    void deshacer();
    String getDescripcion();
}
