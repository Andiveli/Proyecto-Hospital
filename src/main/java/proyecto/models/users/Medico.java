package proyecto.models.users;

import java.time.LocalDateTime;

public class Medico extends Persona  {
    private LocalDateTime horarioAtencion;
    private String genero;
    private String especialidad;
    private boolean activo;

    public Medico(int id, String nombre, String telefono, LocalDateTime horarioAtencion, String genero, String especialidad, boolean activo) {
        super(id, nombre, telefono);
        this.horarioAtencion = horarioAtencion;
        this.genero = genero;
        this.especialidad = especialidad;
        this.activo = activo;
    }

    public LocalDateTime getHorarioAtencion() {
        return horarioAtencion;
    }

    public void setHorarioAtencion(LocalDateTime horarioAtencion) {
        this.horarioAtencion = horarioAtencion;
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
