package proyecto.models;

import java.time.LocalDateTime;
import java.util.ArrayList;

import proyecto.enums.EstadoCita;
import proyecto.models.tratamientos.Tratamiento;
import proyecto.models.users.Medico;
import proyecto.models.users.Paciente;

public class Cita {
    private int idCita;
    private LocalDateTime fecha;
    private Paciente paciente;
    private Medico medico;
    private ArrayList<Tratamiento> tratamientos;
    private EstadoCita estadoCita; 

    public Cita(int idCita, LocalDateTime fecha, Paciente paciente, Medico medico, ArrayList<Tratamiento> tratamientos) {
        this.idCita = idCita;
        this.fecha = fecha;
        this.paciente = paciente;
        this.medico = medico;
        this.tratamientos = tratamientos;
        this.estadoCita = EstadoCita.PROGRAMADA;
    }

    public int getCantidadTratamientos() {
        return tratamientos.size();
    }
    
    @Override
    public String toString() {
        return "Cita ID: " + idCita + "\nFecha: " + fecha + "\nPaciente: " + paciente.getNombre() + "\nEstado: " + estadoCita + "\nMedico: " + medico.getNombre();
    }

    public void marcarComoPagado() {
        this.estadoCita = EstadoCita.ATENDIDA;
    }

    public void setTratamientos() {
        System.out.println("Agregando Tratamiento");
    }

    public void cancelarCita() {
        this.estadoCita = EstadoCita.CANCELADA;
    }
    
    public double calcularTotal() {
        double total = 0.0;
        for (Tratamiento tratamiento : tratamientos) {
            total += tratamiento.calcularCosto();
        }
        return total;
    }
}
