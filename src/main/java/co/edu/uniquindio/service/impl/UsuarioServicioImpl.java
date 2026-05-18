package co.edu.uniquindio.service.impl;

import co.edu.uniquindio.model.Usuario;
import co.edu.uniquindio.model.enums.RolUsuario;
import co.edu.uniquindio.service.interfaces.IUsuarioServicio;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UsuarioServicioImpl implements IUsuarioServicio {

    private final List<Usuario> usuarios = new ArrayList<>();

    public UsuarioServicioImpl() {
        // Datos de prueba
        registrar("Admin", "admin@eventos.com", "3001234567", "admin123", RolUsuario.ADMINISTRADOR);
        registrar("Juan Pérez", "juan@mail.com", "3109876543", "juan123", RolUsuario.USUARIO);
    }

    @Override
    public void registrar(String nombre, String correo, String telefono,
                          String contrasenia, RolUsuario rol) {
        boolean correoExiste = usuarios.stream()
                .anyMatch(u -> u.getCorreo().equalsIgnoreCase(correo));
        if (correoExiste) {
            throw new IllegalArgumentException("Ya existe un usuario con ese correo.");
        }
        Usuario nuevo = new Usuario(null, nombre, correo, telefono,
                contrasenia, new ArrayList<>(), rol);
        usuarios.add(nuevo);
    }

    @Override
    public Optional<Usuario> autenticar(String correo, String contrasenia) {
        return usuarios.stream()
                .filter(u -> u.getCorreo().equalsIgnoreCase(correo)
                        && u.getContrasenia().equals(contrasenia))
                .findFirst();
    }

    @Override
    public Optional<Usuario> buscarPorId(String idUsuario) {
        return usuarios.stream()
                .filter(u -> u.getIdUsuario().equals(idUsuario))
                .findFirst();
    }

    @Override
    public List<Usuario> listarTodos() {
        return new ArrayList<>(usuarios);
    }

    @Override
    public void actualizar(Usuario usuario) {
        // El objeto ya está en la lista por referencia, no requiere reemplazo
    }

    @Override
    public void eliminar(String idUsuario) {
        usuarios.removeIf(u -> u.getIdUsuario().equals(idUsuario));
    }
}