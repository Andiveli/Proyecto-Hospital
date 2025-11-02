package proyecto.models.tratamientos;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

public class Medicacion extends Tratamiento {

    public Medicacion(String nombre, int duracion, double precio) {
        super(nombre, duracion, precio);
    }

    @Override
    public String getTipo() {
        return "medicacion";
    }
    
    @Override
    public double calcularCosto() {
        double suma = getCostoBase();
        if(getDuracion() > 5) {
            suma += getPrecio() * getDuracion() * 0.9;
        } else {
            suma += getPrecio() * getDuracion();
        }
        return suma;
    }

    @Override
    public String toString() {
        return "Medicacion: " + getNombre() + ", Duración: " + getDuracion() + " hora(s), Precio por hora: $" + getPrecio();
    }

    public void guardar() {
        try(BufferedWriter writer = new BufferedWriter(new FileWriter("medicaciones.txt", true))) {
            writer.write(this.toString());
            writer.newLine();
        } catch (Exception e) {
            System.out.println("Error al guardar la medicación: " + e.getMessage());
        }
    }

    public static ArrayList<Tratamiento> cargarTodas() {
        ArrayList<Tratamiento> medicaciones = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("medicaciones.txt"))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] partes = linea.split(", ");
                String nombre = partes[0].split(": ")[1];
                int duracion = Integer.parseInt(partes[1].split(": ")[1].split(" ")[0]);
                double precio = Double.parseDouble(partes[2].split(": ")[1].replace("$", ""));
                Medicacion medicacion = new Medicacion(nombre, duracion, precio);
                medicaciones.add(medicacion);
            }
        } catch (Exception e) {
            System.out.println("Error al cargar las medicaciones: " + e.getMessage());
        }
        return medicaciones;
    }
}