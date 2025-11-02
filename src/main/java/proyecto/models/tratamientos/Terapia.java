package proyecto.models.tratamientos;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

public class Terapia extends Tratamiento  {
    public Terapia(String nombre, int duracion, double precio) {
        super(nombre, duracion, precio);
    }

    @Override
    public double calcularCosto() {
        double costoBase = getCostoBase();
        double descuento = 0.15 * costoBase; // Descuento del 15% para terapias
        return costoBase - descuento;
    }

    @Override
    public String toString() {
        return "Terapia: " + getNombre() + ", Duración: " + getDuracion() + " hora(s), Precio por hora: $" + getPrecio();
    }

    public boolean guardar() {
        try(BufferedWriter writer = new BufferedWriter(new FileWriter("terapias.txt", true))) {
            writer.write(this.toString());
            writer.newLine();
        } catch (Exception e) {
            System.out.println("Error al guardar la terapia: " + e.getMessage());
            return false;
        }
        return true;
    }

    public static ArrayList<Tratamiento> cargarTodas() {
        ArrayList<Tratamiento> terapias = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("terapias.txt"))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] partes = linea.split(", ");
                String nombre = partes[0].split(": ")[1];
                int duracion = Integer.parseInt(partes[1].split(": ")[1].split(" ")[0]);
                double precio = Double.parseDouble(partes[2].split(": ")[1].replace("$", ""));
                Terapia terapia = new Terapia(nombre, duracion, precio);
                terapias.add(terapia);
            }
        } catch (Exception e) {
            System.out.println("Error al cargar las terapias: " + e.getMessage());
        }
        return terapias;
    }
}
