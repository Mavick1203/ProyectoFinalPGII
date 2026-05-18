package co.edu.uniquindio.model;

import co.edu.uniquindio.model.enums.EstadoPago;
import java.time.LocalDateTime;
import java.util.UUID;

public class Pago {
    private final String      idPago;
    private final String      idCompra;
    private final double      monto;
    private final String      metodoPago;
    private EstadoPago        estado;
    private final LocalDateTime fecha;
    private String            referencia;

    public Pago(String idCompra, double monto, String metodoPago) {
        this.idPago     = UUID.randomUUID().toString();
        this.idCompra   = idCompra;
        this.monto      = monto;
        this.metodoPago = metodoPago;
        this.estado     = EstadoPago.PENDIENTE;
        this.fecha      = LocalDateTime.now();
        this.referencia = "REF-" + idPago.substring(0, 8).toUpperCase();
    }

    public boolean aprobar() {
        if (estado == EstadoPago.PENDIENTE) {
            estado = EstadoPago.APROBADO;
            return true;
        }
        return false;
    }

    public boolean rechazar() {
        if (estado == EstadoPago.PENDIENTE) {
            estado = EstadoPago.RECHAZADO;
            return true;
        }
        return false;
    }

    public boolean reembolsar() {
        if (estado == EstadoPago.APROBADO) {
            estado = EstadoPago.REEMBOLSADO;
            return true;
        }
        return false;
    }

    public String getIdPago()       { return idPago; }
    public String getIdCompra()     { return idCompra; }
    public double getMonto()        { return monto; }
    public String getMetodoPago()   { return metodoPago; }
    public EstadoPago getEstado()   { return estado; }
    public LocalDateTime getFecha() { return fecha; }
    public String getReferencia()   { return referencia; }

    @Override
    public String toString() {
        return "Pago{id='" + idPago + "', compra='" + idCompra +
                "', monto=" + monto + ", metodo='" + metodoPago +
                "', estado=" + estado + ", fecha=" + fecha + '}';
    }
}