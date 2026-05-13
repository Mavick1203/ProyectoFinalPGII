package co.edu.uniquindio.patterns.creacionales.Singleton;

import co.edu.uniquindio.model.Usuario;

public final class GestorSesion {

    private static volatile GestorSesion instancia;


    private Usuario usuarioActual;
    private boolean esAdmin;


    private GestorSesion() {
        this.usuarioActual = null;
        this.esAdmin = false;
    }


    public static GestorSesion getInstance() {
        if (instancia == null) {
            synchronized (GestorSesion.class) {
                if (instancia == null) {
                    instancia = new GestorSesion();
                }
            }
        }
        return instancia;
    }

    public void iniciarSesion(Usuario usuario, boolean esAdmin) {
        if (usuario == null) throw new IllegalArgumentException("El usuario no puede ser null.");
        this.usuarioActual = usuario;
        this.esAdmin = esAdmin;
        System.out.println("[GestorSesion] Sesión iniciada: " + usuario.getNombre()
                + (esAdmin ? " [ADMIN]" : " [USUARIO]"));
    }

    public void cerrarSesion() {
        System.out.println("[GestorSesion] Sesión cerrada: "
                + (usuarioActual != null ? usuarioActual.getNombre() : "ninguno"));
        this.usuarioActual = null;
        this.esAdmin = false;
    }
    public boolean haySesionActiva()  {
        return usuarioActual != null;
    }
    public Usuario getUsuarioActual() {
        return usuarioActual;
    }

    public boolean isEsAdmin(){
        return esAdmin;
    }

}