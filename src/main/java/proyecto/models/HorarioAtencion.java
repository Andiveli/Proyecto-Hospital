package proyecto.models;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class HorarioAtencion {
    private LocalTime horaInicio;
    private LocalTime horaFin;
    private EnumSet<DayOfWeek> dias;
    private int duracionCita;
    private Map<DayOfWeek, Set<LocalTime>> horasOcupadasPorDia;
    
    public HorarioAtencion(LocalTime horaInicio, LocalTime horaFin, EnumSet<DayOfWeek> dias) {
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.dias = dias;
        this.duracionCita = 60;
        this.horasOcupadasPorDia = new HashMap<>();
        for (DayOfWeek dia : dias) {
            horasOcupadasPorDia.put(dia, new HashSet<>());
        }
    }

    public boolean estaDisponible(DayOfWeek dia, LocalTime hora) {
        if (!dias.contains(dia)) return false;
        if (hora.isBefore(horaInicio) || hora.plusMinutes(duracionCita).isAfter(horaFin)) return false;

        Set<LocalTime> ocupadas = horasOcupadasPorDia.get(dia);
        return !ocupadas.contains(hora);
    }

    public boolean registrarCita(DayOfWeek dia, LocalTime hora) {
        if (!dias.contains(dia)) return false;
        if (hora.isBefore(horaInicio) || hora.plusMinutes(duracionCita).isAfter(horaFin)) return false;

        Set<LocalTime> ocupadas = horasOcupadasPorDia.get(dia);
        if (ocupadas.contains(hora)) return false;

        ocupadas.add(hora);
        return true;
    }

    public boolean modificarCita(LocalTime nuevaHora, DayOfWeek nuevoDia, LocalTime horaAnterior, DayOfWeek diaAnterior) {
        if(cancelarCita(diaAnterior, horaAnterior)) {
            return registrarCita(nuevoDia, nuevaHora);
        }
        return false;
    }

    public boolean cancelarCita(DayOfWeek dia, LocalTime hora) {
        if (!dias.contains(dia)) return false;

        Set<LocalTime> ocupadas = horasOcupadasPorDia.get(dia);
        return ocupadas.remove(hora);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

        sb.append("📅 Horario del médico:\n");
        for (DayOfWeek dia : dias) {
            sb.append(dia).append(":\n");
            LocalTime actual = horaInicio;
            while (!actual.isAfter(horaFin.minusMinutes(duracionCita))) {
                boolean ocupada = horasOcupadasPorDia.get(dia).contains(actual);
                sb.append("  ").append(actual.format(formatter))
                  .append(" - ").append(ocupada ? "❌ No disponible" : "Disponible").append("\n");
                actual = actual.plusMinutes(duracionCita);
            }
        }
        return sb.toString();
    }

    public String convertirString() {
        StringBuilder sb = new StringBuilder();

        // Formato base: horaInicio-horaFin;duracion;DIA1,DIA2,...;DIA1:HH:mm,HH:mm|DIA2:HH:mm,...
        sb.append(horaInicio).append("-").append(horaFin).append(";");
        sb.append(duracionCita).append(";");

        sb.append(dias.stream()
            .map(DayOfWeek::name)
            .collect(Collectors.joining(",")))
        .append(";");

        List<String> ocupadasPorDia = new ArrayList<>();
        for (DayOfWeek dia : dias) {
            Set<LocalTime> horas = horasOcupadasPorDia.getOrDefault(dia, Collections.emptySet());
            String horasStr = horas.stream()
                .map(LocalTime::toString)
                .collect(Collectors.joining(","));
            ocupadasPorDia.add(dia.name() + ":" + horasStr);
        }
        sb.append(String.join("|", ocupadasPorDia));

        return sb.toString();
    }

    public static HorarioAtencion convertirDatos(String data) {
        String[] partes = data.split(";");
        String[] horas = partes[0].split("-");
        LocalTime inicio = LocalTime.parse(horas[0]);
        LocalTime fin = LocalTime.parse(horas[1]);
        int duracion = Integer.parseInt(partes[1]);

        EnumSet<DayOfWeek> dias = Arrays.stream(partes[2].split(","))
            .map(String::trim)
            .map(DayOfWeek::valueOf)
            .collect(Collectors.toCollection(() -> EnumSet.noneOf(DayOfWeek.class)));

        HorarioAtencion horario = new HorarioAtencion(inicio, fin, dias);
        horario.duracionCita = duracion;

        horario.horasOcupadasPorDia = new HashMap<>();
        for (DayOfWeek dia : dias) {
            horario.horasOcupadasPorDia.put(dia, new HashSet<>());
        }

        if (partes.length > 3 && !partes[3].isEmpty()) {
            String[] bloques = partes[3].split("\\|");
            for (String bloque : bloques) {
                int idx = bloque.indexOf(':');
                if (idx > 0) {
                    DayOfWeek dia = DayOfWeek.valueOf(bloque.substring(0, idx));
                    String horasStr = bloque.substring(idx + 1);

                    if (!horasStr.isEmpty()) {
                        Set<LocalTime> horasOcupadas = Arrays.stream(horasStr.split(","))
                            .map(LocalTime::parse)
                            .collect(Collectors.toSet());
                        horario.horasOcupadasPorDia.put(dia, horasOcupadas);
                    } else {
                        horario.horasOcupadasPorDia.put(dia, new HashSet<>());
                    }
                }
            }
        }

        return horario;
    }

}