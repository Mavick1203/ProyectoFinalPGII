package co.edu.uniquindio.patterns.comportamentales.Command;


import java.util.ArrayDeque;
import java.util.Deque;

public class ComandoInvocador {

    private Deque<IComandoCompra> historial;

    public ComandoInvocador() {
        this.historial = new ArrayDeque<>();
    }

    public void ejecutar(IComandoCompra comando) {
        comando.ejecutar();
        historial.push(comando);
    }

    public void deshacer() {
        if (hayHistorial()) {
            IComandoCompra comando = historial.pop();
            comando.deshacer();
        }
    }

    public boolean hayHistorial() {
        return !historial.isEmpty();
    }
}