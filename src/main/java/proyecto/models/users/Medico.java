package proyecto.models.users;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

import proyecto.models.HorarioAtencion;

public class Medico extends Persona  {
    private HorarioAtencion horarioAtencion;
    private String genero;
    private String especialidad;
    private boolean activo;

    public Medico(int id, String nombre, String apellido, String correo, String cedula, HorarioAtencion horarioAtencion, String genero, String especialidad, boolean activo) {
        super(id, nombre, apellido, correo, cedula);
        this.horarioAtencion = horarioAtencion;
        this.genero = genero;
        this.especialidad = especialidad;
        this.activo = activo;
    }

    @Override
    public void guardar() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("medicos.txt", true))) {
            writer.write(this.toString());
            writer.newLine();
        } catch (Exception e) {
            System.out.println("Error al guardar el médico: " + e.getMessage());
        }
    }

    public static ArrayList<Medico> cargarTodos() {
        ArrayList<Medico> medicos = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("medicos.txt"))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] partes = linea.split(", ");
                String nombreCompleto = partes[0].split(": ")[1];
                String[] nombres = nombreCompleto.split(" ");
                String nombre = nombres[0];
                String apellido = nombres[1];
                String cedula = partes[1].split(": ")[1];
                String correo = partes[2].split(": ")[1];
                String generoT = partes[3].split(": ")[1];
                String especialidadT = partes[4].split(": ")[1];
                boolean activoT = partes[5].split(": ")[1].equals("Sí");
                HorarioAtencion horarioAtencionT = HorarioAtencion.fromDataString(partes[6].split(": ")[1]);

                Medico medico = new Medico(medicos.size() + 1, nombre, apellido, correo, cedula, horarioAtencionT, generoT, especialidadT, activoT);
                medicos.add(medico);
            }
        } catch (Exception e) {
            System.out.println("Error al cargar los médicos: " + e.getMessage());
        }
        return medicos;
    }

    @Override
    public String toString() {
        return "Nombre: " + getNombre() + " " + getApellido() + ", Cedula: " + getCedulaString() + ", Género: " + getGenero() + ", Especialidad: " + getEspecialidad() + ", Correo: " + getCorreo() + ", Activo: " + (isActivo() ? "Sí" : "No") + ", Horario: " + getHorarioAtencion().toDataString();
    }

    public HorarioAtencion getHorarioAtencion() {
        return horarioAtencion;
    }
    
    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public void activar() {
        this.activo = true;
    }

    public void desactivar() {
        this.activo = false;
    }

    public void registrarTurno() {
        System.out.println("Turno registrado para el médico: " + getNombre());
    }
}
