package proyecto.models;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.EnumSet;

public class HorarioAtencion {
    private LocalTime horaInicio;
    private LocalTime horaFin;
    private EnumSet<DayOfWeek> dias;
    private int duracionCita;
    
    public HorarioAtencion(LocalTime horaInicio, LocalTime horaFin, EnumSet<DayOfWeek> dias) {
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.dias = dias;
        this.duracionCita = 60;
    }
}
