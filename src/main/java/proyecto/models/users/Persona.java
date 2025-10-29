package proyecto.models.users;

public class Persona {
    private int id;
    private String nombre;
    private String apellido;
    private String correo;
    private String telefono;

    public Persona(int id, String nombre, String apellido, String correo, String telefono) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.telefono = telefono;
    }
    
    public Persona(Persona copia){
        // El siguiente constructor es para clonar objetos sin que, cuando cambies uno, se cambien los dos
        this.id = copia.id;
        this.nombre = copia.nombre;
        this.apellido = copia.apellido;
        this.correo = copia.correo;
        this.telefono = copia.telefono;
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

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getApellido() {
        return apellido;
    }

    public String getCorreo() {
        return correo;
    }

}
