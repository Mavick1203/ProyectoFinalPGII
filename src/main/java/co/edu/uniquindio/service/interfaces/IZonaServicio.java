package co.edu.uniquindio.service.interfaces;

import co.edu.uniquindio.model.Asiento;
import co.edu.uniquindio.model.Zona;

import java.util.List;
import java.util.Optional;

public interface IZonaServicio {

    void crear(Zona zona, String idRecinto);
    Optional<Zona> buscarPorId(String idZona);
    List<Zona> listarTodas();
    void actualizar(Zona zona);
    void eliminar(String idZona);

    /** Agrega un asiento a la zona. */
    void agregarAsiento(String idZona, Asiento asiento);

    /** Retorna los asientos disponibles de una zona. */
    List<Asiento> listarAsientosDisponibles(String idZona);

    /** Porcentaje de ocupación: asientos no disponibles / capacidad total. */
    double calcularOcupacion(String idZona);
}