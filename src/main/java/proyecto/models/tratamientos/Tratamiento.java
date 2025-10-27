package proyecto.models.tratamientos;

public class Tratamiento {
    private String nombre;
    private int duracion;
    private double precio;

    public Tratamiento(String nombre, int duracion, double precio) {
        this.nombre = nombre;
        this.duracion = duracion;
        this.precio = precio;
    }

    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getDuracion() {
        return duracion;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public double calcularCosto() {
        return precio * duracion;
    }

    @Override
    public String toString() {
        return "Tratamiento: " + nombre + ", Duración: " + duracion + " hora(s), Precio por hora: $" + precio;
    }
}

