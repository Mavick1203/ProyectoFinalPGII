package co.edu.uniquindio.model;

import co.edu.uniquindio.model.enums.RolUsuario;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Usuario {
    private final String idUsuario;
    private String nombre;
    private String correo;
    private String telefono;
    private String Contrasenia;


    /**
     los metodos de pago usan la interfaz Metodo de pago
     */
    private final List<String> metodosPago;
    private RolUsuario rolUsuario;

    public Usuario(String idUsuario, String nombre, String correo, String telefono,String Contrasenia, List<String> metodosPago, RolUsuario rolUsuario) {
        this.idUsuario = UUID.randomUUID().toString();
        this.nombre = nombre;
        this.correo = correo;
        this.telefono = telefono;
        this.metodosPago = new ArrayList<>();
    }

    public void agregarMetodoPago(String metodo){
        this.metodosPago.add(metodo);
    }
    public void eliminarMetodoPago(String metodo){
        this.metodosPago.remove(metodo);
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getContrasenia() {
        return Contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        Contrasenia = contrasenia;
    }

    public List<String> getMetodosPago() {
        return metodosPago;
    }

    public RolUsuario getRolUsuario() {
        return rolUsuario;
    }

    public void setRolUsuario(RolUsuario rolUsuario) {
        this.rolUsuario = rolUsuario;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "idUsuario='" + idUsuario + '\'' +
                ", nombre='" + nombre + '\'' +
                ", correo='" + correo + '\'' +
                ", telefono='" + telefono + '\'' +
                ", Contrasenia='" + Contrasenia + '\'' +
                ", metodosPago=" + metodosPago +
                ", rolUsuario=" + rolUsuario +
                '}';
    }
}

