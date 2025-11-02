package proyecto.models;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.time.LocalDateTime;
import java.util.List;

import proyecto.models.tratamientos.Tratamiento;

public class Factura {
    private final int id; 
    private final String paciente;
    private final List<Tratamiento> tratamientos;
    private final double total;
    private final LocalDateTime fecha;

    public Factura(int id, String paciente, List<Tratamiento> tratamientos, double total, LocalDateTime fecha) {
        this.id = id;
        this.paciente = paciente;
        this.tratamientos = tratamientos;
        this.total = total;
        this.fecha = fecha;
    }

    @Override
    public String toString() {
        return "Factura ID: " + id + "\n, Paciente: " + paciente + "\n, Total: $" + total + "\n, Fecha: " + fecha + "\n, Tratamientos: " + tratamientos;
    }

    public void guardar() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("facturas.txt", true))) {
            writer.write(this.toString());
            writer.newLine();
        } catch (Exception e) {
            System.out.println("Error al guardar la factura: " + e.getMessage());
        }
    }

    public static void cargarTodas() {
        try (BufferedReader reader = new BufferedReader(new FileReader("facturas.txt"))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                System.out.println(linea);
            }
        } catch (Exception e) {
            System.out.println("Error al cargar las facturas: " + e.getMessage());
        }
    }
}
