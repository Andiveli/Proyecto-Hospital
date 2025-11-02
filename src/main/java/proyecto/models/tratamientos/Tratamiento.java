package proyecto.models.tratamientos;
import proyecto.interfaces.Pagable;

public abstract class Tratamiento implements Pagable{
    private static final double COSTO_BASE = 50.0;
    private String nombre;
    private int duracion;
    private double precio;

    public Tratamiento(String nombre, int duracion, double precio) {
        this.nombre = nombre;
        this.duracion = duracion;
        this.precio = precio;
    }   

    public static double getCostoBase() {
        return COSTO_BASE;
    }

    public double getPrecio() {
        return precio;
    }

    public String getNombre() {
        return nombre;
    }

    public int getDuracion() {
        return duracion;
    }

    @Override
    public String toString() {
        return "Tratamiento: " + nombre + ", Duración: " + duracion + " hora(s), Precio por hora: $" + precio;
    }

    public abstract double calcularCosto();
    public abstract String getTipo();
}

