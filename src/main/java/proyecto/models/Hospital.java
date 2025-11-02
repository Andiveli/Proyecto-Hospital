package proyecto.models;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.EnumSet;
import java.util.HashMap;

import proyecto.enums.EstadoCita;
import proyecto.enums.TipoSeguro;
import proyecto.models.tratamientos.Cirugia;
import proyecto.models.tratamientos.Medicacion;
import proyecto.models.tratamientos.Terapia;
import proyecto.models.tratamientos.Tratamiento;
import proyecto.models.users.Medico;
import proyecto.models.users.Paciente;
import proyecto.utilities.Validaciones;

public class Hospital {
    private final String nombre;
    private ArrayList<Paciente> listaPacientes;
    private ArrayList<Medico> listaMedicos;
    private ArrayList<Cita> listaCitas;
    private ArrayList<Tratamiento> listaTratamientos;
    private HashMap<Paciente, ArrayList<Tratamiento>> listaTratamientosPorPaciente;
    private HashMap<Paciente, ArrayList<Factura>> listaFacturas;

    public Hospital(String nombre) {
        this.nombre = nombre;
        this.listaPacientes = Paciente.cargarTodos();
        this.listaCitas = Cita.cargarTodos();
        this.listaMedicos = Medico.cargarTodos();
        this.listaTratamientos = new ArrayList<>();
        this.listaTratamientos.addAll(Medicacion.cargarTodas());
        this.listaTratamientos.addAll(Terapia.cargarTodas());
        this.listaTratamientos.addAll(Cirugia.cargarTodas());
        this.listaTratamientosPorPaciente = cargarTratamientosPorPaciente();
        this.listaFacturas = new HashMap<>();
    }

    public boolean guardarPaciente(String nombre, String apellido, String correo, String telefono, int seguro) {
        int id = listaPacientes.size() +1 ; //El id va conforme a su orden de registro. El primero es 1, elsegundo, 2; etc.                                                                                       
        if(pacienteExiste(correo)) {                                               //Con stream, podemos acceder a anyMatch(), que devuelve 'true' si encuentra al menor uno con la condicion dentro de ella
            System.out.println("El paciente ya está registrado.");
            return false;
        }
        TipoSeguro tipoSeguro;
        switch (seguro) {
            case 1 -> {
                tipoSeguro = TipoSeguro.IESS;
            }
            case 2-> {
                tipoSeguro = TipoSeguro.PRIVADO;
            }
            default -> tipoSeguro = TipoSeguro.IESS;

        }
        Paciente paciente = new Paciente(id, nombre, apellido, correo, telefono, tipoSeguro);
        paciente.guardar();
        listaPacientes.add(paciente);
        return true;
    }

    public boolean guardarMedico(String nombre, String apellido, String correo, String telefono, String genero, String especialidad, boolean activo, HorarioAtencion horario) {
        int id = listaMedicos.size() + 1;
        if(medicoExiste(correo)) {
            System.out.println("El médico ya está registrado.");
            return false;
        } else {
            Medico medico = new Medico(id, nombre, apellido, correo, telefono, horario, genero, especialidad, activo);
            medico.guardar();
            listaMedicos.add(medico);
            return true;
        }
    }

    public void listarPacientes(int filtro) {
        switch (filtro) {
            case 1 -> {
                for(Paciente p: listaPacientes) {
                    if(p.getTipoSeguro() == TipoSeguro.IESS) {
                        System.out.println(p);
                    }
                }
                System.out.println();
            }
            case 2 -> {
                for(Paciente p: listaPacientes) {
                    if(p.getTipoSeguro() == TipoSeguro.PRIVADO) {
                        System.out.println(p);
                    }
                }
                System.out.println();
            }
        }
    }

    public void listarPacientesAll() {
        for(Paciente p: listaPacientes) {
            System.out.println(p);
        }
        System.out.println();
    }

    public void listarMedicosPorEspecialidad(String especialidad) {
        for(Medico m: listaMedicos) {
            if(m.getEspecialidad().equalsIgnoreCase(especialidad)) {
                System.out.println("Dr. " + m);
            }
        }
        System.out.println();
    }

    public void listarMedicosPorGenero(String genero) {
        for(Medico m: listaMedicos) {
            if(m.getGenero().equalsIgnoreCase(genero)) {
                System.out.println("Dr. " + m);
            }
        }
        System.out.println();
    }

    public void listarMedicosActivos() {
        for(Medico m: listaMedicos) {
            if(m.isActivo()) {
                System.out.println("Dr. " + m);
            }
        }
        System.out.println();
    }

    public void listarMedicosAll() {
        for(Medico m: listaMedicos) {
            System.out.println("Dr. " + m);
        }
        System.out.println();
    }

    public void buscarHorarios(String correo) {
        for(Medico m: listaMedicos) {
            if(m.getCorreo().equalsIgnoreCase(correo)) {
                System.out.println("Horarios disponibles del médico " + m.getNombre() + ": \n" + m.getHorarioAtencion());
            }
        }

    }
    
    public HorarioAtencion crearHorario(String dias, LocalTime inicio, LocalTime fin) {
        EnumSet<DayOfWeek> diasHorario = EnumSet.noneOf(DayOfWeek.class);

        try {
            for(String dia: dias.split(",")) {
                String diaIngles = Validaciones.convertirIngles(dia.trim().toLowerCase());
                diasHorario.add(DayOfWeek.valueOf(diaIngles));
            }

            if(!fin.isAfter(inicio)) {
                throw new IllegalArgumentException("La hora de inicio debe ser antes que la hora de fin");
            }

            return new HorarioAtencion(inicio, fin, diasHorario);
        } catch(IllegalArgumentException e) {
            System.out.println("Error en los datos: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error inesperado: " + e.getMessage());
        }

        return null;
    }
    
    public boolean pacienteExiste(String correo) {
        return listaPacientes.stream().anyMatch(p -> p.getCorreo().equals(correo));
    }

    public boolean medicoExiste(String correo) {
        return listaMedicos.stream().anyMatch(m -> m.getCorreo().equals(correo));
    }

    public boolean crearCitaMedica(String cita, String paciente, String medico) {
        for(Medico m: listaMedicos) {
            if(m.getCorreo().equalsIgnoreCase(medico)) {
                for(Paciente p: listaPacientes) {
                    if(p.getCorreo().equalsIgnoreCase(paciente)) {
                        String[] partes = cita.split(" ");
                        String diaStr = partes[0].trim().toLowerCase();
                        String horaStr = partes[1].trim();
                        DayOfWeek dia = DayOfWeek.valueOf(Validaciones.convertirIngles(diaStr));
                        LocalTime hora = LocalTime.parse(horaStr);
                        if(m.isDisponible(hora, dia)) {
                            boolean registrado = m.getHorarioAtencion().registrarCita(dia, hora);
                            if(registrado) {
                                int idCita = listaCitas.size() + 1;
                                Cita nuevaCita = new Cita(idCita, hora, dia, p.getCorreo(), m.getCorreo());
                                nuevaCita.guardar();
                                guardarMedicos();
                                listaCitas.add(nuevaCita);
                                return true;
                            } else {
                                return false;
                            }
                        } else {
                            System.out.println("El médico no está disponible.");
                            return false;
                        }
                    }
                }
            }
        }
        System.out.println("Médico o paciente no encontrado.");
        return false;
    }
    
    public List<String> obtenerCitasPorPaciente(String correoPaciente) {
        List<String> citasPaciente = new ArrayList<>();
        for(Cita c: listaCitas) {
            if(c.getPaciente().equalsIgnoreCase(correoPaciente) && c.getEstadoCita() == EstadoCita.PROGRAMADA) {
                citasPaciente.add(c.toString());
            }
        }
        return citasPaciente;
    }

    public boolean modificarCitaMedica(int idCita, String nuevoDiaHora) {
        for(Cita c: listaCitas) {
            if(c.getIdCita() == idCita) {
                LocalTime horaAnterior = c.getHora();
                DayOfWeek diaAnterior = c.getDia();
                String[] partes = nuevoDiaHora.split(" ");
                String diaStr = partes[0].trim().toLowerCase();
                String horaStr = partes[1].trim();
                DayOfWeek nuevoDia = DayOfWeek.valueOf(Validaciones.convertirIngles(diaStr));
                LocalTime nuevaHora = LocalTime.parse(horaStr);
                for(Medico m: listaMedicos) {
                    if(m.getCorreo().equalsIgnoreCase(c.getMedico())) {
                        if(!m.isDisponible(nuevaHora, nuevoDia)) {
                            System.out.println("El médico no está disponible en el nuevo horario.");
                            return false;
                        }
                    }
                }
                
                boolean registrado = false;
                for(Medico m: listaMedicos) {
                    if(m.getCorreo().equalsIgnoreCase(c.getMedico())) {
                        registrado = m.getHorarioAtencion().modificarCita(nuevaHora, nuevoDia, horaAnterior, diaAnterior);
                        break;
                    }
                }
                if(registrado) {
                    Cita nuevaCita = new Cita(c.getIdCita(), nuevaHora, nuevoDia, c.getPaciente(), c.getMedico());
                    c.setEstadoCita(EstadoCita.CANCELADA);
                    listaCitas.add(nuevaCita);
                    System.out.println("Cita modificada exitosamente.");
                    guardarCitas();
                    return true;
                } else {
                    System.out.println("El nuevo horario no está disponible.");
                    return false;
                }
            }
        }
        System.out.println("Cita no encontrada.");
        return false;
    }

    public boolean cancelarCitaMedica(int idCita) {
        LocalDateTime ahora = LocalDateTime.now();
        for(Cita c: listaCitas) {
            if(c.getIdCita() == idCita) {
                if(isDiaAnterior(c, ahora)) return false;
                if(isDiaActual(c, ahora)) return false;
                boolean cancelado = false;
                for(Medico m: listaMedicos) {
                    if(m.getCorreo().equalsIgnoreCase(c.getMedico())) {
                        cancelado = m.getHorarioAtencion().cancelarCita(c.getDia(), c.getHora());
                        break;
                    }
                }
                if(cancelado) {
                    c.setEstadoCita(EstadoCita.CANCELADA);
                    System.out.println("Cita cancelada exitosamente.");
                    guardarCitas();
                    return true;
                } else {
                    System.out.println("Error al cancelar la cita.");
                    return false;
                }
            }
        }
        System.out.println("Cita no encontrada.");
        return false;
    }

    private boolean isDiaAnterior(Cita c, LocalDateTime ahora) {
        if (c.getDia().getValue() < ahora.getDayOfWeek().getValue() ||
                (c.getDia().getValue() == ahora.getDayOfWeek().getValue() && c.getHora().isBefore(ahora.toLocalTime()))) {
            System.out.println("No se puede cancelar una cita pasada.");
            return true;
        }
        return false;
    }

    private boolean isDiaActual(Cita c, LocalDateTime ahora) {
        if (c.getDia() == ahora.getDayOfWeek() &&
                c.getHora().minusHours(24).isBefore(ahora.toLocalTime())) {
            System.out.println("No se puede cancelar una cita con menos de 24 horas de anticipación.");
            return true;
        }
        return false;
    }

    public boolean marcarCitaAtendida(int idCita) {
        for(Cita c: listaCitas) {
            if(c.getIdCita() == idCita) {
                if(c.getEstadoCita() != EstadoCita.PROGRAMADA) {
                    System.out.println("La cita no está en estado programada.");
                    return false;
                }
                c.setEstadoCita(EstadoCita.ATENDIDA);
                for(Medico m: listaMedicos) {
                    if(m.getCorreo().equalsIgnoreCase(c.getMedico())) {
                        m.getHorarioAtencion().cancelarCita(c.getDia(), c.getHora());
                        break;
                    }
                }
                System.out.println("Cita marcada como atendida.");
                guardarCitas();
                return true;
            }
        }
        System.out.println("Cita no encontrada.");
        return false;
    }

    private void guardarCitas() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("citas.txt"))) {
            for(Cita c: listaCitas) {
                writer.write(c.toString());
                writer.newLine();
            }
        } catch (Exception e) {
            System.out.println("Error al guardar las citas: " + e.getMessage());
        }
        guardarMedicos();
    }

    private void guardarMedicos() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("medicos.txt"))) {
            for(Medico m: listaMedicos) {
                writer.write(m.toString());
                writer.newLine();
            }
        } catch (Exception e) {
            System.out.println("Error al guardar los médicos: " + e.getMessage());
        }
    }

    public boolean registrarMedicacion(String correo, String medicamento, int duracion, double costo) {
        for(Paciente p: listaPacientes) {
            if(p.getCorreo().equalsIgnoreCase(correo)) {
                Medicacion medicacion = new Medicacion(medicamento, duracion, costo);
                medicacion.guardar();
                listaTratamientos.add(medicacion);
                agregarTratamientoPaciente(p, medicacion);
                return true;
            }
        }
        System.out.println("Paciente no encontrado.");
        return false;
    }

    public boolean registrarTerapia(String correo, String tipoTerapia, int sesiones, double costo) {
        for(Paciente p: listaPacientes) {
            if(p.getCorreo().equalsIgnoreCase(correo)) {
                Terapia terapia = new Terapia(tipoTerapia, sesiones, costo);
                terapia.guardar();
                listaTratamientos.add(terapia);
                agregarTratamientoPaciente(p, terapia);
                return true;
            }
        }
        System.out.println("Paciente no encontrado.");
        return false;
    }

    public boolean registrarCirugia(String correo, String tipoCirugia, double costo) {
        for(Paciente p: listaPacientes) {
            if(p.getCorreo().equalsIgnoreCase(correo)) {
                Cirugia cirugia = new Cirugia(tipoCirugia, 1,costo);
                cirugia.guardar();
                listaTratamientos.add(cirugia);
                agregarTratamientoPaciente(p, cirugia);
                return true;
            }
        }
        System.out.println("Paciente no encontrado.");
        return false;
    }

    private void agregarTratamientoPaciente(Paciente paciente, Tratamiento tratamiento) {
        if(listaTratamientosPorPaciente.containsKey(paciente)) {
            listaTratamientosPorPaciente.get(paciente).add(tratamiento);
            Factura factura = new Factura(listaFacturas.size() + 1, paciente.getNombre(), tratamiento.getNombre(), tratamiento.pagar(), LocalDateTime.now());
            factura.guardar();
        } else {
            ArrayList<Tratamiento> tratamientos = new ArrayList<>();
            tratamientos.add(tratamiento);
            listaTratamientosPorPaciente.put(paciente, tratamientos);
        }
    }

    public void listarCitasAtendidasPorEspecialidad(String especialidad) {
        System.out.println("Citas atendidas para la especialidad: " + especialidad);
        int suma = 0;
        for(Cita c: listaCitas) {
            if(c.getEstadoCita() == EstadoCita.ATENDIDA) {
                for(Medico m: listaMedicos) {
                    if(m.getCorreo().equalsIgnoreCase(c.getMedico()) && m.getEspecialidad().equalsIgnoreCase(especialidad)) {
                        System.out.println(c);
                        suma++;
                    }
                }
            }
        }
        System.out.println("Total de citas atendidas para la especialidad " + especialidad + ": " + suma);
    }

    public void listarHistorialTratamientos(String correoPaciente) {
        for(Paciente p: listaPacientes) {
            if(p.getCorreo().equalsIgnoreCase(correoPaciente)) {
                if(listaTratamientosPorPaciente.containsKey(p)) {
                    System.out.println("Historial de tratamientos del paciente " + p.getNombre() + ":");
                    for(Tratamiento t: listaTratamientosPorPaciente.get(p)) {
                        System.out.println(t);
                    }
                } else {
                    System.out.println("No hay tratamientos registrados para este paciente.");
                }
                return;
            }
        }
        System.out.println("Paciente no encontrado.");
    }

    public void calcularIngresosTotalesPorTratamientos() {
        double totalIngresos = 0.0;
        for(Paciente p: listaTratamientosPorPaciente.keySet()) {
            for(Tratamiento t: listaTratamientosPorPaciente.get(p)) {
                totalIngresos += t.pagar();
            }
        }
        System.out.println("Ingresos totales por tratamientos: $" + totalIngresos);
    }

    public void calcularIngresosTratamiento(String tipoTratamiento) {
        double totalIngresos = 0.0;
        for(Paciente p: listaTratamientosPorPaciente.keySet()) {
            for(Tratamiento t: listaTratamientosPorPaciente.get(p)) {
                if(t.getTipo().equalsIgnoreCase(tipoTratamiento)) {
                    totalIngresos += t.pagar();
                }
            }
        }
        System.out.println("Ingresos totales por tratamientos de tipo " + tipoTratamiento + ": $" + totalIngresos);
    }

    private HashMap<Paciente, ArrayList<Tratamiento>> cargarTratamientosPorPaciente() {
        ArrayList<Factura> facturas = Factura.cargarTodas();
        HashMap<Paciente, ArrayList<Tratamiento>> map = new HashMap<>();
        for(Factura f: facturas) {
            for(Paciente p: listaPacientes) {
                if(p.getNombre().equalsIgnoreCase(f.getPaciente())) {
                    for(Tratamiento t: listaTratamientos) {
                        if(f.getTratamientos().equalsIgnoreCase(t.getNombre())) {
                            if(map.containsKey(p)) {
                                map.get(p).add(t);
                            } else {
                                ArrayList<Tratamiento> tratamientos = new ArrayList<>();
                                tratamientos.add(t);
                                map.put(p, tratamientos);
                            }
                        }
                    }
                }
            }
        }
        return map;
    }
}
