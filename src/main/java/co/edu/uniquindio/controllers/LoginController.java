package co.edu.uniquindio.controllers;

import co.edu.uniquindio.model.Usuario;
import co.edu.uniquindio.model.enums.RolUsuario;
import co.edu.uniquindio.patterns.creacionales.Singleton.GestorSesion;
import co.edu.uniquindio.service.interfaces.IUsuarioServicio;

import java.util.Optional;

/**
 * Controller de Login — solo lógica de negocio.
 * No conoce ningún componente JavaFX (@FXML).
 * El ViewController (LoginViewController) lo usa para delegar.
 */
public class LoginController {

    private final IUsuarioServicio usuarioServicio;

    public LoginController(IUsuarioServicio usuarioServicio) {
        this.usuarioServicio = usuarioServicio;
    }

    /**
     * Resultado de intentar autenticar.
     */
    public record ResultadoLogin(boolean exitoso, String mensaje, Usuario usuario, boolean esAdmin) {}

    /**
     * Valida campos y autentica al usuario.
     * @return ResultadoLogin con el resultado y un mensaje legible.
     */
    public ResultadoLogin login(String correo, String contrasenia) {
        if (correo == null || correo.isBlank())
            return new ResultadoLogin(false, "El correo es obligatorio.", null, false);

        if (contrasenia == null || contrasenia.isBlank())
            return new ResultadoLogin(false, "La contraseña es obligatoria.", null, false);

        if (!correo.contains("@"))
            return new ResultadoLogin(false, "Ingresa un correo válido.", null, false);

        Optional<Usuario> resultado = usuarioServicio.autenticar(correo.trim(), contrasenia);

        if (resultado.isEmpty())
            return new ResultadoLogin(false, "Correo o contraseña incorrectos.", null, false);

        Usuario usuario = resultado.get();
        boolean esAdmin = usuario.getRolUsuario() == RolUsuario.ADMINISTRADOR;
        GestorSesion.getInstance().iniciarSesion(usuario, esAdmin);

        return new ResultadoLogin(true, "Bienvenido, " + usuario.getNombre(), usuario, esAdmin);
    }

    public IUsuarioServicio getUsuarioServicio() {
        return usuarioServicio;
    }
}