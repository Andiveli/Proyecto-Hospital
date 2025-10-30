package proyecto.models;

import java.time.LocalDateTime;

import proyecto.enums.EstadoCita;
import proyecto.models.tratamientos.Tratamiento;
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
    
    // El siguiente constructor es para clonar objetos sin que, cuando cambies uno, se cambien los dos
    public Cita(Cita copia){
       this.idCita = copia.idCita;
       this.fecha = copia.fecha;
       this.paciente = copia.paciente;
       this.medico = copia.medico;
       this.estadoCita = EstadoCita.PROGRAMADA;
       this.estadoCita = EstadoCita.PROGRAMADA; //Para que la cita, al ser creada, su estadoCita aparezca como "programada"
    } 
    
            
    
    @Override
    public String toString() {
        return "Cita ID: " + idCita + "\nFecha: " + fecha + "\nPaciente: " + paciente.getNombre() + "\nCorreo del paciente: " + paciente.getCorreo() + "\nEstado: " + estadoCita + "\nMedico: " + medico.getNombre() + "\nCorreo del Medico: " + medico.getCorreo();
    }

    public void marcarComoPagado() {
        this.estadoCita = EstadoCita.ATENDIDA;
    }
  
    
    
    public void cancelarCita() {
        this.estadoCita = EstadoCita.CANCELADA;
    }
    
    public LocalDateTime getFecha(){
        return fecha;
    }
    
    public void setFecha(LocalDateTime fecha){
        this.fecha = fecha;
    }
    
    public Medico getMedico(){
        return medico;
    }
    public void setMedico(Medico medico){
        this.medico = medico;
    }
    
    public void setPaciente(Paciente paciente){
        this.paciente = paciente;
    }
    
    public void setEstado(EstadoCita estadoCita){
        this.estadoCita = estadoCita;
    }
    
   
}
