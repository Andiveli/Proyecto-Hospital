package proyecto.models;

import java.time.LocalDateTime;

import proyecto.enums.EstadoCita;
import proyecto.models.users.Medico;
import proyecto.models.users.Paciente;

public class Cita {
    private int idCita;
    private LocalDateTime fecha;
    private Paciente paciente;
    private Medico medico;
    private EstadoCita estadoCita; 

    public Cita(int idCita, LocalDateTime fecha, Paciente paciente, Medico medico) {
        this.idCita = idCita;
        this.fecha = fecha;
        this.paciente = paciente;
        this.medico = medico;
        this.estadoCita = EstadoCita.PROGRAMADA;
        this.estadoCita = EstadoCita.PROGRAMADA; //Para que la cita, al ser creada, su estadoCita aparezca como "programada"
    }
    
    @Override
    public String toString() {
        return "Cita ID: " + idCita + "\nFecha: " + fecha + "\nPaciente: " + paciente.getNombre() + "\nEstado: " + estadoCita + "\nMedico: " + medico.getNombre();
    }

    public void marcarComoPagado() {
        this.estadoCita = EstadoCita.ATENDIDA;
    }

    public void setTratamientos() {
        System.out.println("Agregando Tratamiento"); //
    }

    public void cancelarCita() {
        this.estadoCita = EstadoCita.CANCELADA;
    }
}
