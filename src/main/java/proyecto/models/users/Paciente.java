package proyecto.models.users;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

import proyecto.enums.TipoSeguro;

public class Paciente extends Persona {
    private TipoSeguro tipoSeguro;
    private ArrayList<String> historialCitas;

    public Paciente(int id, String nombre, String apellido, String correo, String cedula, TipoSeguro tipoSeguro) {
        super(id, nombre, apellido, correo, cedula);
        this.tipoSeguro = tipoSeguro;
        this.historialCitas = new ArrayList<>();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Paciente paciente = (Paciente) obj;
        return this.getCedulaString().equals(paciente.getCedulaString());
    }

    @Override
    public int hashCode() {
        return this.getCedulaString().hashCode();
    }

    @Override
    public String toString() {
        return "Nombre: " + getNombre() + " " + getApellido() + ", Correo: " + getCorreo() + ", Tipo de Seguro: " + tipoSeguro;
    }

    @Override
    public void guardar() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("pacientes.txt", true))) {
            writer.write(this.toString());
            writer.newLine();
        } catch (Exception e) {
            System.out.println("Error al guardar el paciente: " + e.getMessage());
        }
    }

    public static ArrayList<Paciente> cargarTodos() {
        ArrayList<Paciente> pacientes = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(("pacientes.txt")))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] partes = linea.split(", ");
                String nombreCompleto = partes[0].split(": ")[1];
                String[] nombres = nombreCompleto.split(" ");
                String nombre = nombres[0];
                String apellido = nombres[1];
                String telefono = partes[1].split(": ")[1];
                String correo = partes[2].split(": ")[1];
                String tipoSeguroStr = partes[3].split(": ")[1];
                TipoSeguro tipoSeguroPaciente = TipoSeguro.valueOf(tipoSeguroStr);

                Paciente paciente = new Paciente(pacientes.size() + 1, nombre, apellido, correo, telefono, tipoSeguroPaciente);
                pacientes.add(paciente);
            }
            return pacientes;
        } catch (Exception e) {
            System.out.println("Error al cargar los pacientes: " + e.getMessage());
            return pacientes;
        }
    }

    public TipoSeguro getTipoSeguro() {
        return tipoSeguro;
    }

    public void setTipoSeguro(TipoSeguro tipoSeguro) {
        this.tipoSeguro = tipoSeguro;
    }

    public ArrayList<String> getHistorialCitas() {
        return historialCitas;
    }

    public void setHistorialCitas(ArrayList<String> historialCitas) {
        this.historialCitas = historialCitas;
    }   
}
