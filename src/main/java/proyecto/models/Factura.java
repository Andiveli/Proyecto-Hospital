package proyecto.models;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Factura {
    private final int id; 
    private final String paciente;
    private final String tratamientos;
    private final double total;
    private final LocalDateTime fecha;

    public Factura(int id, String paciente, String tratamientos, double total, LocalDateTime fecha) {
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

    private String convertirString() {
        return "Factura ID: " + id + ", Paciente: " + paciente + ", Total: $" + total + ", Fecha: " + fecha + ", Tratamientos: " + tratamientos;
    }

    public void guardar() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("facturas.txt", true))) {
            writer.write(this.convertirString());
            writer.newLine();
        } catch (Exception e) {
            System.out.println("Error al guardar la factura: " + e.getMessage());
        }
    }

    public static ArrayList<Factura> cargarTodas() {
        ArrayList<Factura> facturas = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("facturas.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(", ");
                int id = Integer.parseInt(parts[0].split(": ")[1]);
                String paciente = parts[1].split(": ")[1];
                double total = Double.parseDouble(parts[2].split(": \\$")[1]);
                LocalDateTime fecha = LocalDateTime.parse(parts[3].split(": ")[1]);
                String tratamientos = parts[4].split(": ")[1];
                Factura factura = new Factura(id, paciente, tratamientos, total, fecha);
                facturas.add(factura);
            }
        } catch (Exception e) {
            System.out.println("Error al cargar las facturas: " + e.getMessage());
        }
        return facturas;
    }

    public String getPaciente() {
        return paciente;
    }

    public String getTratamientos() {
        return tratamientos;
    }
}
