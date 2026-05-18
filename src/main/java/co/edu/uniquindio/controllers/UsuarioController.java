package co.edu.uniquindio.controllers;

import co.edu.uniquindio.model.Usuario;
import co.edu.uniquindio.model.enums.RolUsuario;
import co.edu.uniquindio.service.interfaces.IUsuarioServicio;

import java.util.List;
import java.util.Optional;

public class UsuarioController {

    private final IUsuarioServicio usuarioServicio;

    public UsuarioController(IUsuarioServicio usuarioServicio) {
        this.usuarioServicio = usuarioServicio;
    }

    public record ResultadoUsuario(boolean exitoso, String mensaje) {}

    public ResultadoUsuario actualizarPerfil(String idUsuario, String nombre,
                                             String correo, String telefono) {
        if (nombre == null || nombre.isBlank())
            return new ResultadoUsuario(false, "El nombre es obligatorio.");
        if (correo == null || correo.isBlank() || !correo.contains("@"))
            return new ResultadoUsuario(false, "Ingresa un correo válido.");
        if (telefono == null || telefono.isBlank())
            return new ResultadoUsuario(false, "El teléfono es obligatorio.");

        Optional<Usuario> usuarioOpt = usuarioServicio.buscarPorId(idUsuario);
        if (usuarioOpt.isEmpty())
            return new ResultadoUsuario(false, "Usuario no encontrado.");

        Usuario usuario = usuarioOpt.get();
        usuario.setNombre(nombre);
        usuario.setCorreo(correo);
        usuario.setTelefono(telefono);
        usuarioServicio.actualizar(usuario);
        return new ResultadoUsuario(true, "Perfil actualizado correctamente.");
    }

    public ResultadoUsuario cambiarContrasenia(String idUsuario, String contraseniaActual,
                                               String nuevaContrasenia, String confirmar) {
        if (nuevaContrasenia == null || nuevaContrasenia.length() < 6)
            return new ResultadoUsuario(false, "La contraseña debe tener al menos 6 caracteres.");
        if (!nuevaContrasenia.equals(confirmar))
            return new ResultadoUsuario(false, "Las contraseñas no coinciden.");

        Optional<Usuario> usuarioOpt = usuarioServicio.buscarPorId(idUsuario);
        if (usuarioOpt.isEmpty())
            return new ResultadoUsuario(false, "Usuario no encontrado.");

        Usuario usuario = usuarioOpt.get();
        if (!usuario.getContrasenia().equals(contraseniaActual))
            return new ResultadoUsuario(false, "La contraseña actual es incorrecta.");

        usuario.setContrasenia(nuevaContrasenia);
        usuarioServicio.actualizar(usuario);
        return new ResultadoUsuario(true, "Contraseña actualizada correctamente.");
    }

    public ResultadoUsuario eliminarUsuario(String idUsuario) {
        if (usuarioServicio.buscarPorId(idUsuario).isEmpty())
            return new ResultadoUsuario(false, "Usuario no encontrado.");
        usuarioServicio.eliminar(idUsuario);
        return new ResultadoUsuario(true, "Usuario eliminado correctamente.");
    }

    public List<Usuario> listarTodos() {
        return usuarioServicio.listarTodos();
    }

    public Optional<Usuario> buscarPorId(String idUsuario) {
        return usuarioServicio.buscarPorId(idUsuario);
    }

    public IUsuarioServicio getUsuarioServicio() {
        return usuarioServicio;
    }
}