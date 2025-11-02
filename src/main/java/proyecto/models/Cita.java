package proyecto.models;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import proyecto.enums.EstadoCita;

public class Cita {
    private int idCita;
    private LocalTime hora;
    private DayOfWeek dia;
    private String paciente;
    private String medico;
    private EstadoCita estadoCita; 

    public Cita(int idCita, LocalTime hora, DayOfWeek dia, String paciente, String medico) {
        this.idCita = idCita;
        this.hora = hora;
        this.dia = dia;
        this.paciente = paciente;
        this.medico = medico;
        this.estadoCita = EstadoCita.PROGRAMADA; //Para que la cita, al ser creada, su estadoCita aparezca como "programada"
    }
    
    public void guardar() {
        try(BufferedWriter writer = new BufferedWriter(new FileWriter("citas.txt", true))) {
            writer.write(this.toString());
            writer.newLine();
        } catch (Exception e) {
            System.out.println("Error al guardar la cita: " + e.getMessage());
        }
    }

    public static ArrayList<Cita> cargarTodos() {
        ArrayList<Cita> citas = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("citas.txt"))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] partes = linea.split(", ");
                DayOfWeek diaT = DayOfWeek.valueOf(partes[0].split(": ")[1]);
                LocalTime horaT = LocalTime.parse(partes[1].split(": ")[1]);
                String correoPaciente = partes[2].split(": ")[1];
                String correoMedico = partes[3].split(": ")[1];
                Cita cita = new Cita(citas.size() + 1, horaT, diaT, correoPaciente, correoMedico);
                citas.add(cita);
            }
        } catch (Exception e) {
            System.out.println("Error al cargar las citas: " + e.getMessage());
        }
        return citas;
    }

    @Override
    public String toString() {
        return "Id: " + getIdCita() + ", Día: " + getDia() + ", Hora: " + getHora() + ", Paciente: " + getPaciente() + ", Médico: " + getMedico() + ", Estado: " + getEstadoCita();
    }

    public int getIdCita() {
        return idCita;
    }
    public LocalTime getHora() {
        return hora;
    }
    public DayOfWeek getDia() {
        return dia;
    }
    public String getPaciente() {
        return paciente;
    }
    public String getMedico() {
        return medico;
    }
    public EstadoCita getEstadoCita() {
        return estadoCita;
    }

    public void setEstadoCita(EstadoCita estadoCita) {
        this.estadoCita = estadoCita;
    }

    public void marcarComoPagado() {
        this.estadoCita = EstadoCita.ATENDIDA;
    }
}
