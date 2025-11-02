package proyecto.models;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.EnumSet;
import java.util.HashMap;

import proyecto.enums.EstadoCita;
import proyecto.enums.TipoSeguro;
import proyecto.exceptions.ArchivoException;
import proyecto.exceptions.CitaNoDisponibleException;
import proyecto.exceptions.MedicoNoEncontradoException;
import proyecto.exceptions.PacienteNoEncontradoException;
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

    public Hospital(String nombre) throws ArchivoException {
        this.nombre = nombre;
        try {
            this.listaPacientes = Paciente.cargarTodos();
            this.listaCitas = Cita.cargarTodos();
            this.listaMedicos = Medico.cargarTodos();
            this.listaTratamientos = new ArrayList<>();
            this.listaTratamientos.addAll(Medicacion.cargarTodas());
            this.listaTratamientos.addAll(Terapia.cargarTodas());
            this.listaTratamientos.addAll(Cirugia.cargarTodas());
            this.listaTratamientosPorPaciente = cargarTratamientosPorPaciente();
            this.listaFacturas = new HashMap<>();
        } catch (Exception e) {
            throw new ArchivoException("Error crítico al inicializar el hospital: " + e.getMessage(), e);
        }
    }

    private boolean isDiaAnterior(Cita c, LocalDateTime ahora) {
        if (c.getDia().getValue() < ahora.getDayOfWeek().getValue() ||
                (c.getDia().getValue() == ahora.getDayOfWeek().getValue()
                        && c.getHora().isBefore(ahora.toLocalTime()))) {
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

    private void guardarMedicos() throws ArchivoException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("medicos.txt"))) {
            for (Medico m : listaMedicos) {
                writer.write(m.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            throw new ArchivoException("No se pudieron guardar los datos de médicos en el archivo medicos.txt", e);
        } catch (Exception e) {
            throw new ArchivoException("Error inesperado al guardar médicos: " + e.getMessage(), e);
        }
    }

    private void guardarCitas() throws ArchivoException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("citas.txt"))) {
            for (Cita c : listaCitas) {
                writer.write(c.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            throw new ArchivoException("No se pudieron guardar los datos de citas en el archivo citas.txt", e);
        } catch (Exception e) {
            throw new ArchivoException("Error inesperado al guardar citas: " + e.getMessage(), e);
        }
        guardarMedicos();
    }

    private void agregarTratamientoPaciente(Paciente paciente, Tratamiento tratamiento) {
        if (listaTratamientosPorPaciente.containsKey(paciente)) {
            listaTratamientosPorPaciente.get(paciente).add(tratamiento);
        } else {
            ArrayList<Tratamiento> tratamientos = new ArrayList<>();
            tratamientos.add(tratamiento);
            listaTratamientosPorPaciente.put(paciente, tratamientos);
        }
        Factura factura = new Factura(listaFacturas.size() + 1, paciente.getCorreo(), tratamiento.getNombre(),
                tratamiento.pagar(), LocalDateTime.now());
        factura.guardar();
    }

    private HashMap<Paciente, ArrayList<Tratamiento>> cargarTratamientosPorPaciente() {
        ArrayList<Factura> facturas = Factura.cargarTodas();
        HashMap<Paciente, ArrayList<Tratamiento>> map = new HashMap<>();
        for (Factura f : facturas) {
            for (Paciente p : listaPacientes) {
                if (p.getCorreo().equalsIgnoreCase(f.getPaciente())) {
                    for (Tratamiento t : listaTratamientos) {
                        if (f.getTratamientos().equalsIgnoreCase(t.getNombre())) {
                            if (map.containsKey(p)) {
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

    public boolean guardarPaciente(String nombre, String apellido, String correo, String telefono, int seguro)
            throws ArchivoException {
        try {
            int id = listaPacientes.size() + 1;

            if (pacienteExiste(correo)) {
                throw new PacienteNoEncontradoException("El paciente con correo " + correo + " ya está registrado.");
            }

            TipoSeguro tipoSeguro;
            switch (seguro) {
                case 1 -> tipoSeguro = TipoSeguro.IESS;
                case 2 -> tipoSeguro = TipoSeguro.PRIVADO;
                default ->
                    throw new IllegalArgumentException("Tipo de seguro inválido. Debe ser 1 (IESS) o 2 (PRIVADO)");
            }

            Paciente paciente = new Paciente(id, nombre, apellido, correo, telefono, tipoSeguro);
            paciente.guardar();
            listaPacientes.add(paciente);
            return true;
        } catch (PacienteNoEncontradoException e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        } catch (Exception e) {
            throw new ArchivoException("Error al guardar paciente: " + e.getMessage(), e);
        }
    }

    public boolean guardarMedico(String nombre, String apellido, String correo, String telefono, String genero,
            String especialidad, boolean activo, HorarioAtencion horario) throws ArchivoException {
        try {
            int id = listaMedicos.size() + 1;

            if (medicoExiste(correo)) {
                throw new MedicoNoEncontradoException("El médico con correo " + correo + " ya está registrado.");
            }

            if (horario == null) {
                throw new IllegalArgumentException("El horario de atención no puede ser nulo.");
            }

            Medico medico = new Medico(id, nombre, apellido, correo, telefono, horario, genero, especialidad, activo);
            medico.guardar();
            listaMedicos.add(medico);
            return true;
        } catch (MedicoNoEncontradoException e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        } catch (Exception e) {
            throw new ArchivoException("Error al guardar médico: " + e.getMessage(), e);
        }
    }

    public void listarPacientes(int filtro) {
        switch (filtro) {
            case 1 -> {
                for (Paciente p : listaPacientes) {
                    if (p.getTipoSeguro() == TipoSeguro.IESS) {
                        System.out.println(p);
                    }
                }
                System.out.println();
            }
            case 2 -> {
                for (Paciente p : listaPacientes) {
                    if (p.getTipoSeguro() == TipoSeguro.PRIVADO) {
                        System.out.println(p);
                    }
                }
                System.out.println();
            }
        }
    }

    public void listarPacientesAll() {
        for (Paciente p : listaPacientes) {
            System.out.println(p);
        }
        System.out.println();
    }

    public void listarMedicosPorEspecialidad(String especialidad) {
        for (Medico m : listaMedicos) {
            if (m.getEspecialidad().equalsIgnoreCase(especialidad)) {
                System.out.println("Dr. " + m);
            }
        }
        System.out.println();
    }

    public void listarMedicosPorGenero(String genero) {
        for (Medico m : listaMedicos) {
            if (m.getGenero().equalsIgnoreCase(genero)) {
                System.out.println("Dr. " + m);
            }
        }
        System.out.println();
    }

    public void listarMedicosActivos() {
        for (Medico m : listaMedicos) {
            if (m.isActivo()) {
                System.out.println("Dr. " + m);
            }
        }
        System.out.println();
    }

    public void listarMedicosAll() {
        for (Medico m : listaMedicos) {
            System.out.println("Dr. " + m);
        }
        System.out.println();
    }

    public void buscarHorarios(String correo) {
        for (Medico m : listaMedicos) {
            if (m.getCorreo().equalsIgnoreCase(correo)) {
                System.out
                        .println("Horarios disponibles del médico " + m.getNombre() + ": \n" + m.getHorarioAtencion());
            }
        }

    }

    public HorarioAtencion crearHorario(String dias, LocalTime inicio, LocalTime fin) {
        EnumSet<DayOfWeek> diasHorario = EnumSet.noneOf(DayOfWeek.class);

        try {
            for (String dia : dias.split(",")) {
                String diaIngles = Validaciones.convertirIngles(dia.trim().toLowerCase());
                diasHorario.add(DayOfWeek.valueOf(diaIngles));
            }

            if (!fin.isAfter(inicio)) {
                throw new IllegalArgumentException("La hora de inicio debe ser antes que la hora de fin");
            }

            return new HorarioAtencion(inicio, fin, diasHorario);
        } catch (IllegalArgumentException e) {
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

    public boolean crearCitaMedica(String cita, String paciente, String medico)
            throws CitaNoDisponibleException, PacienteNoEncontradoException, MedicoNoEncontradoException,
            ArchivoException {
        try {
            // Validar que el paciente exista
            Paciente pacienteObj = listaPacientes.stream()
                    .filter(p -> p.getCorreo().equalsIgnoreCase(paciente))
                    .findFirst()
                    .orElseThrow(() -> new PacienteNoEncontradoException(
                            "Paciente con correo " + paciente + " no encontrado."));

            // Validar que el médico exista
            Medico medicoObj = listaMedicos.stream()
                    .filter(m -> m.getCorreo().equalsIgnoreCase(medico))
                    .findFirst()
                    .orElseThrow(
                            () -> new MedicoNoEncontradoException("Médico con correo " + medico + " no encontrado."));

            // Parsear fecha y hora de la cita
            String[] partes = cita.split(" ");
            if (partes.length != 2) {
                throw new CitaNoDisponibleException("Formato de cita inválido. Debe ser: día HH:mm");
            }

            String diaStr = partes[0].trim().toLowerCase();
            String horaStr = partes[1].trim();
            DayOfWeek dia = DayOfWeek.valueOf(Validaciones.convertirIngles(diaStr));
            LocalTime hora = LocalTime.parse(horaStr);

            // Verificar disponibilidad del médico
            if (!medicoObj.isDisponible(hora, dia)) {
                throw new CitaNoDisponibleException("El médico no está disponible en el horario solicitado.");
            }

            // Registrar la cita
            boolean registrado = medicoObj.getHorarioAtencion().registrarCita(dia, hora);
            if (!registrado) {
                throw new CitaNoDisponibleException("No se pudo registrar la cita. El horario podría estar ocupado.");
            }

            // Crear y guardar la cita
            int idCita = listaCitas.size() + 1;
            Cita nuevaCita = new Cita(idCita, hora, dia, pacienteObj.getCorreo(), medicoObj.getCorreo());
            nuevaCita.guardar();
            guardarMedicos();
            listaCitas.add(nuevaCita);

            return true;
        } catch (CitaNoDisponibleException | PacienteNoEncontradoException | MedicoNoEncontradoException e) {
            throw e; // Relanzar excepciones personalizadas
        } catch (Exception e) {
            throw new ArchivoException("Error al crear la cita médica: " + e.getMessage(), e);
        }
    }

    public List<String> obtenerCitasPorPaciente(String correoPaciente) {
        List<String> citasPaciente = new ArrayList<>();
        for (Cita c : listaCitas) {
            if (c.getPaciente().equalsIgnoreCase(correoPaciente) && c.getEstadoCita() == EstadoCita.PROGRAMADA) {
                citasPaciente.add(c.toString());
            }
        }
        return citasPaciente;
    }

    public boolean modificarCitaMedica(int idCita, String nuevoDiaHora) throws ArchivoException {
        try {
            for (Cita c : listaCitas) {
                if (c.getIdCita() == idCita) {
                    LocalTime horaAnterior = c.getHora();
                    DayOfWeek diaAnterior = c.getDia();
                    String[] partes = nuevoDiaHora.split(" ");
                    String diaStr = partes[0].trim().toLowerCase();
                    String horaStr = partes[1].trim();
                    DayOfWeek nuevoDia = DayOfWeek.valueOf(Validaciones.convertirIngles(diaStr));
                    LocalTime nuevaHora = LocalTime.parse(horaStr);
                    for (Medico m : listaMedicos) {
                        if (m.getCorreo().equalsIgnoreCase(c.getMedico())) {
                            if (!m.isDisponible(nuevaHora, nuevoDia)) {
                                System.out.println("El médico no está disponible en el nuevo horario.");
                                return false;
                            }
                        }
                    }

                    boolean registrado = false;
                    for (Medico m : listaMedicos) {
                        if (m.getCorreo().equalsIgnoreCase(c.getMedico())) {
                            registrado = m.getHorarioAtencion().modificarCita(nuevaHora, nuevoDia, horaAnterior,
                                    diaAnterior);
                            break;
                        }
                    }
                    if (registrado) {
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
        } catch (ArchivoException e) {
            throw e; // Relanzar excepción de archivo
        } catch (Exception e) {
            throw new ArchivoException("Error al modificar cita: " + e.getMessage(), e);
        }
    }

    public boolean cancelarCitaMedica(int idCita) throws ArchivoException {
        try {
            LocalDateTime ahora = LocalDateTime.now();
            for (Cita c : listaCitas) {
                if (c.getIdCita() == idCita) {
                    if (isDiaAnterior(c, ahora))
                        return false;
                    if (isDiaActual(c, ahora))
                        return false;
                    boolean cancelado = false;
                    for (Medico m : listaMedicos) {
                        if (m.getCorreo().equalsIgnoreCase(c.getMedico())) {
                            cancelado = m.getHorarioAtencion().cancelarCita(c.getDia(), c.getHora());
                            break;
                        }
                    }
                    if (cancelado) {
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
        } catch (ArchivoException e) {
            throw e; // Relanzar excepción de archivo
        } catch (Exception e) {
            throw new ArchivoException("Error al cancelar cita: " + e.getMessage(), e);
        }
    }

    public boolean marcarCitaAtendida(int idCita) throws ArchivoException {
        try {
            for (Cita c : listaCitas) {
                if (c.getIdCita() == idCita) {
                    if (c.getEstadoCita() != EstadoCita.PROGRAMADA) {
                        System.out.println("La cita no está en estado programada.");
                        return false;
                    }
                    c.setEstadoCita(EstadoCita.ATENDIDA);
                    for (Medico m : listaMedicos) {
                        if (m.getCorreo().equalsIgnoreCase(c.getMedico())) {
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
        } catch (ArchivoException e) {
            throw e; // Relanzar excepción de archivo
        } catch (Exception e) {
            throw new ArchivoException("Error al marcar cita como atendida: " + e.getMessage(), e);
        }
    }

    public boolean registrarMedicacion(String correo, String medicamento, int duracion, double costo)
            throws PacienteNoEncontradoException, ArchivoException {
        try {
            Paciente paciente = listaPacientes.stream()
                    .filter(p -> p.getCorreo().equalsIgnoreCase(correo))
                    .findFirst()
                    .orElseThrow(() -> new PacienteNoEncontradoException(
                            "Paciente con correo " + correo + " no encontrado."));

            if (duracion <= 0) {
                throw new IllegalArgumentException("La duración debe ser mayor a cero días.");
            }

            if (costo < 0) {
                throw new IllegalArgumentException("El costo no puede ser negativo.");
            }

            Medicacion medicacion = new Medicacion(medicamento, duracion, costo);
            medicacion.guardar();
            listaTratamientos.add(medicacion);
            agregarTratamientoPaciente(paciente, medicacion);

            return true;
        } catch (PacienteNoEncontradoException e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        } catch (Exception e) {
            throw new ArchivoException("Error al registrar medicación: " + e.getMessage(), e);
        }
    }

    public boolean registrarTerapia(String correo, String tipoTerapia, int sesiones, double costo) 
            throws PacienteNoEncontradoException, ArchivoException {
        try {
            Paciente paciente = listaPacientes.stream()
                .filter(p -> p.getCorreo().equalsIgnoreCase(correo))
                .findFirst()
                .orElseThrow(() -> new PacienteNoEncontradoException("Paciente con correo " + correo + " no encontrado."));
            
            if (sesiones <= 0) {
                throw new IllegalArgumentException("El número de sesiones debe ser mayor a cero.");
            }
            
            if (costo < 0) {
                throw new IllegalArgumentException("El costo no puede ser negativo.");
            }
            
            Terapia terapia = new Terapia(tipoTerapia, sesiones, costo);
            terapia.guardar();
            listaTratamientos.add(terapia);
            agregarTratamientoPaciente(paciente, terapia);
            
            return true;
        } catch (PacienteNoEncontradoException e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        } catch (Exception e) {
            throw new ArchivoException("Error al registrar terapia: " + e.getMessage(), e);
        }
    }

    public boolean registrarCirugia(String correo, String tipoCirugia, double costo) 
            throws PacienteNoEncontradoException, ArchivoException {
        try {
            Paciente paciente = listaPacientes.stream()
                .filter(p -> p.getCorreo().equalsIgnoreCase(correo))
                .findFirst()
                .orElseThrow(() -> new PacienteNoEncontradoException("Paciente con correo " + correo + " no encontrado."));
            
            if (costo < 0) {
                throw new IllegalArgumentException("El costo no puede ser negativo.");
            }
            
            Cirugia cirugia = new Cirugia(tipoCirugia, 1, costo);
            cirugia.guardar();
            listaTratamientos.add(cirugia);
            agregarTratamientoPaciente(paciente, cirugia);
            
            return true;
        } catch (PacienteNoEncontradoException e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        } catch (Exception e) {
            throw new ArchivoException("Error al registrar cirugía: " + e.getMessage(), e);
        }
    }

    public void listarCitasAtendidasPorEspecialidad(String especialidad) {
        System.out.println("Citas atendidas para la especialidad: " + especialidad);
        int suma = 0;
        for (Cita c : listaCitas) {
            if (c.getEstadoCita() == EstadoCita.ATENDIDA) {
                for (Medico m : listaMedicos) {
                    if (m.getCorreo().equalsIgnoreCase(c.getMedico())
                            && m.getEspecialidad().equalsIgnoreCase(especialidad)) {
                        System.out.println(c);
                        suma++;
                    }
                }
            }
        }
        System.out.println("Total de citas atendidas para la especialidad " + especialidad + ": " + suma);
    }

    public void listarHistorialTratamientos(String correoPaciente) {
        for (Paciente p : listaPacientes) {
            if (p.getCorreo().equalsIgnoreCase(correoPaciente)) {
                if (listaTratamientosPorPaciente.containsKey(p)) {
                    System.out.println("Historial de tratamientos del paciente " + p.getNombre() + ":");
                    for (Tratamiento t : listaTratamientosPorPaciente.get(p)) {
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
        for (Paciente p : listaTratamientosPorPaciente.keySet()) {
            for (Tratamiento t : listaTratamientosPorPaciente.get(p)) {
                totalIngresos += t.pagar();
            }
        }
        System.out.println("Ingresos totales por tratamientos: $" + totalIngresos);
    }
}
