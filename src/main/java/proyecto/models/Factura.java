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

    public void guardar() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("facturas.txt", true))) {
            writer.write(this.toString());
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
                String[] partes = line.split(", ");
                int id = Integer.parseInt(partes[0].split(": ")[1]);
                String paciente = partes[1].split(": ")[1];
                double total = Double.parseDouble(partes[2].split(": $")[1]);
                LocalDateTime fecha = LocalDateTime.parse(partes[3].split(": ")[1]);
                String tratamientos = partes[4].split(": ")[1];

                facturas.add(new Factura(id, paciente, tratamientos, total, fecha));
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
