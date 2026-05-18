package co.edu.uniquindio.service.interfaces;

import co.edu.uniquindio.model.Asiento;
import co.edu.uniquindio.model.enums.EstadoAsiento;

import java.util.List;
import java.util.Optional;

public interface IAsientoServicio {

    void crear(Asiento asiento, String idZona);
    Optional<Asiento> buscarPorId(String idAsiento);
    List<Asiento> listarPorZona(String idZona);
    void actualizar(Asiento asiento);
    void eliminar(String idAsiento);

    /** Cambia el estado de un asiento (DISPONIBLE / RESERVADO / VENDIDO / BLOQUEADO). */
    boolean cambiarEstado(String idAsiento, EstadoAsiento nuevoEstado);

    /** Libera todos los asientos RESERVADOS de una zona (útil al cancelar evento). */
    void liberarZona(String idZona);

    /** Retorna el mapa completo de asientos de una zona con su estado actual. */
    List<Asiento> mapaAsientos(String idZona);
}