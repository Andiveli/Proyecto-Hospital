package proyecto.models.users;

import java.util.ArrayList;

public abstract class Persona {
    private int id;
    private String nombre;
    private String apellido;
    private String correo;
    private String cedulaString;

    public Persona(int id, String nombre, String apellido, String correo, String cedulaString) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.cedulaString = cedulaString;
    }
    public String getCedulaString() {
        return cedulaString;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getCorreo() {
        return correo;
    }

    public abstract void guardar();
}
