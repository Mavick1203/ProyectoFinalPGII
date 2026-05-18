package co.edu.uniquindio.service.interfaces;

import co.edu.uniquindio.model.Recinto;
import co.edu.uniquindio.model.Zona;

import java.util.List;
import java.util.Optional;

public interface IRecintoServicio {

    void crear(Recinto recinto);
    Optional<Recinto> buscarPorId(String idRecinto);
    List<Recinto> listarTodos();
    void actualizar(Recinto recinto);
    void eliminar(String idRecinto);

    void agregarZona(String idRecinto, Zona zona);
    void eliminarZona(String idRecinto, String idZona);
    List<Zona> listarZonas(String idRecinto);
}