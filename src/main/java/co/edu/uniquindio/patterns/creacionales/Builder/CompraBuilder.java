package co.edu.uniquindio.patterns.creacionales.Builder;

import co.edu.uniquindio.model.Compra;
import co.edu.uniquindio.model.Entrada;
import co.edu.uniquindio.model.Evento;
import co.edu.uniquindio.model.Tarifa;
import co.edu.uniquindio.model.Usuario;

import java.util.ArrayList;
import java.util.List;


public class CompraBuilder {

    private Usuario      usuario;
    private Evento       evento;
    private double       total;
    private List<Entrada> entradas;
    private List<String>  serviciosAdicionales;

    public CompraBuilder() {
        this.total                = 0.0;
        this.entradas             = new ArrayList<>();
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
        if (entrada == null)
            throw new IllegalArgumentException("La entrada no puede ser null.");
        this.entradas.add(entrada);
        this.total += entrada.getPrecioFinal();
        return this;
    }

    public CompraBuilder agregarServicio(String servicio) {
        if (servicio != null && !servicio.isBlank())
            this.serviciosAdicionales.add(servicio);
        return this;
    }

    public CompraBuilder aplicarTarifa(Tarifa tarifa) {
        if (tarifa != null)
            this.total += tarifa.getPrecio();
        return this;
    }

    public CompraBuilder aplicarDescuento(double descuento) {
        this.total = Math.max(0, this.total - descuento);
        return this;
    }


    public Compra build() {
        if (usuario == null)
            throw new IllegalStateException(
                    "CompraBuilder: se requiere un usuario para construir la compra.");
        if (evento == null)
            throw new IllegalStateException(
                    "CompraBuilder: se requiere un evento para construir la compra.");
        if (entradas.isEmpty())
            throw new IllegalStateException(
                    "CompraBuilder: la compra debe incluir al menos una entrada.");

        return new Compra(usuario, evento, entradas, serviciosAdicionales, total);
    }
}