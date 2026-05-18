package co.edu.uniquindio.controllers;
import co.edu.uniquindio.model.enums.RolUsuario;

/**
 * Controller de Registro — solo lógica y validaciones.
 * No conoce componentes JavaFX.
 */
public class RegistroController {

    private final IUsuarioServicio usuarioServicio;

    public RegistroController(IUsuarioServicio usuarioServicio) {
        this.usuarioServicio = usuarioServicio;
    }

    public record ResultadoRegistro(boolean exitoso, String mensaje) {}

    /**
     * Valida todos los campos y registra el usuario si son correctos.
     */
    public ResultadoRegistro registrar(String nombre, String correo,
                                       String telefono, String contrasenia,
                                       String confirmarContrasenia) {
        if (nombre == null || nombre.isBlank())
            return new ResultadoRegistro(false, "El nombre es obligatorio.");

        if (correo == null || correo.isBlank() || !correo.contains("@"))
            return new ResultadoRegistro(false, "Ingresa un correo electrónico válido.");

        if (telefono == null || telefono.isBlank())
            return new ResultadoRegistro(false, "El teléfono es obligatorio.");

        if (contrasenia == null || contrasenia.length() < 6)
            return new ResultadoRegistro(false, "La contraseña debe tener al menos 6 caracteres.");

        if (!contrasenia.equals(confirmarContrasenia))
            return new ResultadoRegistro(false, "Las contraseñas no coinciden.");

        try {
            usuarioServicio.registrar(
                    nombre.trim(), correo.trim(), telefono.trim(),
                    contrasenia, RolUsuario.USUARIO);
            return new ResultadoRegistro(true, "¡Cuenta creada exitosamente!");
        } catch (IllegalArgumentException e) {
            return new ResultadoRegistro(false, e.getMessage());
        }
    }

    public IUsuarioServicio getUsuarioServicio() {
        return usuarioServicio;
    }
}