package proyecto.models.tratamientos;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

public class Cirugia extends Tratamiento{
    public Cirugia(String nombre, int duracion, double precio) {
        super(nombre, duracion, precio);
    }

    @Override
    public double calcularCosto() {
        double costoBase = getCostoBase();
        double recargo = 0.25 * costoBase; // Recargo del 25% para cirugías
        return costoBase + recargo;
    }

    @Override
    public String toString() {
        return "Cirugia: " + getNombre() + ", Duración: " + getDuracion() + " hora(s), Precio por hora: $" + getPrecio();
    }

    public void guardar() {
        try(BufferedWriter writer = new BufferedWriter(new FileWriter("cirugias.txt", true))) {
            writer.write(this.toString());
            writer.newLine();
        } catch (Exception e) {
            System.out.println("Error al guardar la cirugía: " + e.getMessage());
        }
    }

    public static ArrayList<Tratamiento> cargarTodas() {
        ArrayList<Tratamiento> cirugias = new ArrayList<>();
        try(BufferedReader reader = new BufferedReader(new FileReader("cirugias.txt"))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] partes = linea.split(", ");
                String nombre = partes[0].split(": ")[1];
                int duracion = Integer.parseInt(partes[1].split(": ")[1].split(" ")[0]);
                double precio = Double.parseDouble(partes[2].split(": ")[1].replace("$", ""));
                Cirugia cirugia = new Cirugia(nombre, duracion, precio);
                cirugias.add(cirugia);
            }
        } catch (Exception e) {
            System.out.println("Error al cargar las cirugías: " + e.getMessage());
        }
        return cirugias;
    }
}
