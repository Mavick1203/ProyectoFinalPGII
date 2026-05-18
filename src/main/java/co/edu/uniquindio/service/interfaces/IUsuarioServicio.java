package co.edu.uniquindio.service.interfaces;

import co.edu.uniquindio.model.Usuario;
import co.edu.uniquindio.model.enums.RolUsuario;

import java.util.List;
import java.util.Optional;

public interface IUsuarioServicio {

    
    void registrar(String nombre, String correo, String telefono,
                   String contrasenia, RolUsuario rol);

    /** Autentica por correo + contraseña. Devuelve empty si no coincide. */
    Optional<Usuario> autenticar(String correo, String contrasenia);

    Optional<Usuario> buscarPorId(String idUsuario);
    List<Usuario> listarTodos();
    void actualizar(Usuario usuario);
    void eliminar(String idUsuario);
}