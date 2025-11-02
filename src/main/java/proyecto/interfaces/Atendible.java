package proyecto.interfaces;

import java.time.DayOfWeek;
import java.time.LocalTime;

public interface  Atendible {
    boolean isDisponible(LocalTime hora, DayOfWeek dia);
}
