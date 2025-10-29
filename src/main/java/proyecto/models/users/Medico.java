package proyecto.models.users;

import proyecto.models.HorarioAtencion;

public class Medico extends Persona  {
    private HorarioAtencion horarioAtencion;
    private String genero;
    private String especialidad;
    private boolean activo;

    public Medico(int id, String nombre, String apellido, String correo, String telefono, HorarioAtencion horarioAtencion, String genero, String especialidad, boolean activo) {
        super(id, nombre, apellido, correo, telefono);
        this.horarioAtencion = horarioAtencion;
        this.genero = genero;
        this.especialidad = especialidad;
        this.activo = activo;
    }

    @Override
    public String toString() {
        return ", Nombre: " + getNombre() + ", Teléfono: " + getTelefono() + ", Género: " + genero + ", Especialidad: " + especialidad + ", Activo: " + (activo ? "Sí" : "No");
    }

    public HorarioAtencion getHorarioAtencion() {
        return horarioAtencion;
    }

    public void setHorarioAtencion(HorarioAtencion horarioAtencion) {
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
