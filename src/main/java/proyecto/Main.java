package proyecto;

import java.time.LocalTime;

import java.util.List;
import proyecto.models.HorarioAtencion;
import proyecto.models.Hospital;
import proyecto.utilities.Validaciones;

public class Main {
    public static void main(String[] args) {
        Hospital hp = new Hospital("Hospital POO");
        System.out.println("Sistema de Gestión de Hospital.");
        menu(hp);
    }

    private static void menu(Hospital hp) {
        String respuesta = "si";
        do {
            System.out.println("\n----Menú Principal----");
            int opcion = Validaciones.validarEntero("1. Registrar Paciente. \n2. Registrar Médico. \n3. Listar Pacientes. \n4. Listar Médicos. \n5. Crear Cita Médica. \n6. Modificar o cancelar cita. \n7. Registrar Tratamientos para un paciente. \n8. Marcar cita atendida. \n9. Reportes \n10. Salir. \n");
            switch(opcion) {
                case 1 -> {
                    registrarPaciente(hp);
                }
                case 2 -> {
                    registrarMedico(hp);
                }
                case 3 -> {
                    listarPacientes(hp);
                }
                case 4 -> {
                    listarMedicos(hp);
                }
                case 5 -> {
                    crearCitaMedica(hp);
                }
                case 6 -> {
                    editarCitaMedica(hp);
                }
                case 7 -> {
                    registrarTratamientos(hp);
                }
                case 8 -> {
                    citaAtendida(hp);
                }
                case 9 -> {
                    reporte(hp);
                }
                default -> {
                    System.out.println("Saliendo del sistema...");
                    respuesta = "no";
                }
            }
        } while (respuesta.equalsIgnoreCase("si"));
    }

    private static void reporte(Hospital hp) {
        System.out.println("----Reportes----");
        int opcion = Validaciones.validarEntero("Seleccione el reporte que desea generar: \n1. Citas atendidas por especialidad. \n2. Ingresos totales por tratamientos. \n3. Historial de tratamientos por paciente. \n");
        switch(opcion) {
            case 1 -> {
                listarCitasAtendidasPorEspecialidad(hp);
            }
            case 2 -> {
                ingresosTotalesPorTratamientos(hp);
            }
            case 3 -> {
                String correoPaciente = confirmarPaciente(hp);
                hp.listarHistorialTratamientos(correoPaciente);
            }
        }
    }

    private static void registrarPaciente(Hospital hp) {
        System.out.println("\n\n----Registrar Paciente----");
        String nombre = Validaciones.validarString("Nombre: ");
        String apellido = Validaciones.validarString("Apellido: ");
        String correo = Validaciones.validarCorreo("Correo: ");
        String cedula = Validaciones.validarString("Cédula: ");
        int tipoSeguro = Validaciones.validarEntero("Tipo de Seguro (1. IESS. 2. PRIVADO): ");
        boolean resultado = hp.guardarPaciente(nombre, apellido, correo, cedula, tipoSeguro);
        if(resultado) {
            System.out.println("\nPaciente registrado exitosamente. \n");
        } else {
            System.out.println("\nError al registrar el paciente. \n");
        }
    }

    private static void registrarMedico(Hospital hp) {
        System.out.println("\n\n----Registrar Medico----");
        String nombre = Validaciones.validarString("Nombre: ");
        String apellido = Validaciones.validarString("Apellido: ");
        String correo = Validaciones.validarCorreo("Correo: ");
        String cedula = Validaciones.validarString("Cédula: ");
        String genero = Validaciones.validarString("Género: ");
        String especialidad = Validaciones.validarString("Especialidad: ");
        System.out.println("-----Horarios de Atención");
        String dias = Validaciones.validarString("Ingrese los días de atención (separados por coma (,)): ");
        LocalTime horaInicio = Validaciones.validarHora("Ingrese la hora de inicio (HH:mm): ");
        LocalTime horaFin = Validaciones.validarHora("Ingrese hasta que hora atiende (HH:mm): ");
        HorarioAtencion horario = hp.crearHorario(dias, horaInicio, horaFin);
        boolean activo = Validaciones.validarSiNo("¿Está activo? (si/no): ");
        boolean resultado = hp.guardarMedico(nombre, apellido, correo, cedula, genero, especialidad, activo, horario);
        if(resultado) {
            System.out.println("\nMédico registrado exitosamente. \n");
        } else {
            System.out.println("\nError al registrar el médico. \n");
        }
    }

    private static void listarPacientes(Hospital hp) {
        System.out.println("----Listar Pacientes----");
        int opcion = Validaciones.validarEntero("Listar por: \n1. Tipo de Seguro \n2. Todos. \n");
        switch(opcion) {
            case 1 -> {
                int tipoSeguro = Validaciones.validarEntero("Ingrese el tipo de seguro: \n1. IESS \n2. PRIVADO. \n");
                hp.listarPacientes(tipoSeguro);
            }
            case 2 -> {
                hp.listarPacientesAll();
            }
            default -> {
                System.out.println("Opción no válida.");
            }
        }
    }

    private static void listarMedicos(Hospital hp) {
        System.out.println("----Listar Médicos----");
        int opcion = Validaciones.validarEntero("Listar por: \n1. Especialidad \n2. Genero. \n3. Activos \n4. Todos. \n");
        switch(opcion) {
            case 1 -> {
                String especialidad = Validaciones.validarString("Ingrese la especialidad: \n");
                hp.listarMedicosPorEspecialidad(especialidad);
            }
            case 2 -> {
                String genero = Validaciones.validarString("Ingrese el género: ");
                hp.listarMedicosPorGenero(genero);
            }
            case 3 -> {
                hp.listarMedicosActivos();
            }
            case 4 -> {
                hp.listarMedicosAll();
            }
        }
    }
    
    private static void crearCitaMedica(Hospital hp) {
        System.out.println("----Crear Cita Médica----");
        String paciente = confirmarPaciente(hp);
        if (paciente == null) return;
        String medico = confirmarMedico(hp);
        if (medico == null) return;
        String cita = Validaciones.validarCita("Ingrese el día y la hora de la cita (dia HH:mm): ");
        boolean resultado = hp.crearCitaMedica(cita, paciente, medico);
        if(resultado) {
            System.out.println("\nCita médica creada exitosamente. \n");
        } else {
            System.out.println("\nError al crear la cita médica. \n");
        }
    }

    private static String confirmarMedico(Hospital hp) {
        int n = 0;
        String correoMedico = Validaciones.validarString("Ingrese el correo del médico: ");
        while(!hp.medicoExiste(correoMedico) && n < 3) {
            System.out.println("El médico no está registrado.");
            correoMedico = Validaciones.validarString("Ingrese el correo del médico: ");
            n++;
        }
        if (n == 3) {
            System.out.println("Se ha excedido el número de intentos.");
            return null;
        }
        hp.buscarHorarios(correoMedico);
        return correoMedico;
    }

    private static String confirmarPaciente(Hospital hp) {
        int n = 0;
        String correoPaciente = Validaciones.validarString("Ingrese el correo del paciente: ");
        while(!hp.pacienteExiste(correoPaciente) && n < 3) {
            System.out.println("El paciente no está registrado.");
            correoPaciente = Validaciones.validarString("Ingrese el correo del paciente: ");
            n++;
        }
        if (n == 3) {
            System.out.println("Se ha excedido el número de intentos.");
            return null;
        }
        return correoPaciente;
    }

    private static void editarCitaMedica(Hospital hp) {
        System.out.println("----Modificar o Cancelar Cita Médica----");
        String correoPaciente = confirmarPaciente(hp);
        List<String> citasPaciente = hp.obtenerCitasPorPaciente(correoPaciente);
        if(citasPaciente.isEmpty()) {
            System.out.println("El paciente no tiene citas registradas.");
            return;
        }
        for(String cita : citasPaciente) {
            System.out.println(cita);
        }
        int idCita = Validaciones.validarEntero("Ingrese el ID de la cita que desea modificar o cancelar: ");
        String opcion = Validaciones.validarString("Que desea hacer? \n1. Modificar Cita. \n2. Cancelar Cita. \n");
        switch(opcion) {
            case "1" -> {
                modificarCita(hp, idCita);
            }
            case "2" -> {
                cancelarCita(hp, idCita);
            }
        }
    }

    private static void modificarCita(Hospital hp, int idCita) {
        System.out.println("----Modificar Cita Médica----");
        String nuevaCita = Validaciones.validarCita("Ingrese el nuevo día y la nueva hora de la cita (dia HH:mm): ");
        boolean resultado = hp.modificarCitaMedica(idCita, nuevaCita);
        if(resultado) {
            System.out.println("\nCita médica modificada exitosamente. \n");
        } else {
            System.out.println("\nError al modificar la cita médica. \n");
        }
    }

    private static void cancelarCita(Hospital hp, int idCita) {
        System.out.println("----Cancelar Cita Médica----");
        boolean confirmar = Validaciones.validarSiNo("¿Está seguro que desea cancelar la cita? (si/no): ");
        if (!confirmar) {
            System.out.println("Cancelación de cita médica abortada.");
            return;
        }
        boolean resultado = hp.cancelarCitaMedica(idCita);
        if(resultado) {
            System.out.println("\nCita médica cancelada exitosamente. \n");
        } else {
            System.out.println("\nError al cancelar la cita médica. \n");
        }
    }

    private static void citaAtendida(Hospital hp) {
        System.out.println("----Marcar Cita como Atendida----");
        String correoPaciente = confirmarPaciente(hp);
        List<String> citasPaciente = hp.obtenerCitasPorPaciente(correoPaciente);
        if(citasPaciente.isEmpty()) {
            System.out.println("El paciente no tiene citas registradas.");
            return;
        }
        for(String cita : citasPaciente) {
            System.out.println(cita);
        }
        int idCita = Validaciones.validarEntero("Ingrese el ID de la cita que desea marcar como atendida: ");
        boolean resultado = hp.marcarCitaAtendida(idCita);
        if(resultado) {
            System.out.println("\nCita médica marcada como atendida exitosamente. \n");
        } else {
            System.out.println("\nError al marcar la cita médica como atendida. \n");
        }
    }
    
    private static void registrarTratamientos(Hospital hp) {
        System.out.println("----Registrar Tratamientos para un Paciente----");
        String correoPaciente = confirmarPaciente(hp);
        int opcion = Validaciones.validarEntero("Que tipo de tratamiento desea registrar? \n1. Medicación. \n2. Terapia. \n3. Cirugía");
        switch(opcion) {
            case 1 -> {
                registrarMedicacion(hp, correoPaciente);
            }
            case 2 -> {
                registrarTerapia(hp, correoPaciente);
            }
            case 3 -> {
                registrarCirugia(hp, correoPaciente);
            }
        }
    }

    private static void registrarMedicacion(Hospital hp, String correoPaciente) {
        System.out.println("----Registrar Medicación----");
        String medicamento = Validaciones.validarString("Nombre del medicamento: ");
        double costo = Validaciones.validarDouble("Costo: ");
        int duracionDias = Validaciones.validarEntero("Duración en días: ");
        boolean resultado = hp.registrarMedicacion(correoPaciente, medicamento, duracionDias, costo);
        if(resultado) {
            System.out.println("\nMedicacion registrada exitosamente. \n");
        } else {
            System.out.println("\nError al registrar la medicación. \n");
        }
    }

    private static void registrarTerapia(Hospital hp, String correoPaciente) {
        System.out.println("----Registrar Terapia----");
        String tipoTerapia = Validaciones.validarString("Tipo de terapia: ");
        double costo = Validaciones.validarDouble("Costo: ");
        int numeroSesiones = Validaciones.validarEntero("Número de sesiones: ");
        boolean resultado = hp.registrarTerapia(correoPaciente, tipoTerapia, numeroSesiones, costo);
        if(resultado) {
            System.out.println("\nTerapia registrada exitosamente. \n");
        } else {
            System.out.println("\nError al registrar la terapia. \n");
        }
    }

    private static void registrarCirugia(Hospital hp, String correoPaciente) {
        System.out.println("----Registrar Cirugía----");
        String tipoCirugia = Validaciones.validarString("Tipo de cirugía: ");
        double costo = Validaciones.validarDouble("Costo: ");
        boolean resultado = hp.registrarCirugia(correoPaciente, tipoCirugia, costo);
        if(resultado) {
            System.out.println("\nCirugía registrada exitosamente. \n");
        } else {
            System.out.println("\nError al registrar la cirugía. \n");
        }
    }
    
    private static void listarCitasAtendidasPorEspecialidad(Hospital hp) {
        System.out.println("----Citas Atendidas por Especialidad----");
        String especialidad = Validaciones.validarString("Ingrese la especialidad: ");
        hp.listarCitasAtendidasPorEspecialidad(especialidad);
    }

    private static void ingresosTotalesPorTratamientos(Hospital hp) {
        System.out.println("----Ingresos Totales por Tratamientos----");
        int opcion = Validaciones.validarEntero("Calcular ingresos por: \n1. Medicación. \n2. Terapia. \n3. Cirugía. \n4. Todos los tratamientos. \n");
        switch(opcion) {
            case 1 -> {
                hp.calcularIngresosTratamiento("medicacion");
            }
            case 2 -> {
                hp.calcularIngresosTratamiento("terapia");
            }
            case 3 -> {
                hp.calcularIngresosTratamiento("cirugia");
            }
            case 4 -> {
                hp.calcularIngresosTotalesPorTratamientos();
            }
        }
    }
}