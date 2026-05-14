package co.edu.uniquindio.patterns.creacionales.Builder;

import co.edu.uniquindio.model.Compra;
import co.edu.uniquindio.model.Entrada;
import co.edu.uniquindio.model.Usuario;
import co.edu.uniquindio.model.Evento;
import co.edu.uniquindio.model.Tarifa;
import java.util.ArrayList;
import java.util.List;

public class CompraBuilder {

    private Usuario usuario;
    private Evento evento;
    private double total;
    private List<Entrada> entradas;
    private List<String> serviciosAdicionales;

    public CompraBuilder() {
        this.total = 0.0;
        this.entradas = new ArrayList<>();
        this.serviciosAdicionales = new ArrayList<>();
    }

    public CompraBuilder paraUsuario(Usuario usuario) {
        this.usuario = usuario;
        return this;
    }

    public CompraBuilder enEvento(Evento evento) {
        this.evento = evento;
        return this;
    }

    public CompraBuilder agregarEntrada(Entrada entrada) {
        this.entradas.add(entrada);
        this.total += entrada.getPrecioFinal();
        return this;
    }

    public CompraBuilder agregarServicio(String servicio) {
        this.serviciosAdicionales.add(servicio);
        return this;
    }

    public CompraBuilder aplicarTarifa(Tarifa tarifa) {
        this.total += tarifa.getPrecio();
        return this;
    }

    public Compra build() {
        return new Compra(usuario, evento, entradas, serviciosAdicionales, total);
    }
}