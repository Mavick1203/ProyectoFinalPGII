package co.edu.uniquindio.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class Recinto {
    private final String     idRecinto;
    private String           nombre;
    private String           direccion;
    private String           ciudad;
    private final List<Zona> zonas;

    public Recinto(String nombre, String direccion, String ciudad) {
        this.idRecinto = UUID.randomUUID().toString();
        this.nombre = nombre;
        this.direccion = direccion;
        this.ciudad = ciudad;
        this.zonas = new ArrayList<>();
    }

    public void agregarZona(Zona zona){
        this.zonas.add(zona);
    }
    public void eliminarZona(Zona zona){
        this.zonas.remove(zona);
    }
    public int getCapacidadTotal(){
        int total = 0;
        for(Zona z: this.zonas){
            total += z.getCapacidad();
        }
        return total;
    }
    public String getIdRecinto() {
        return idRecinto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }
    public List<Zona> getZonas() {
        return Collections.unmodifiableList(zonas);
    }

    @Override
    public String toString() {
        return "Recinto{" +
                "idRecinto='" + idRecinto + '\'' +
                ", nombre='" + nombre + '\'' +
                ", direccion='" + direccion + '\'' +
                ", ciudad='" + ciudad + '\'' +
                ", zonas=" + zonas +
                '}';
    }
}
