package proyecto;

import java.time.LocalTime;

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
            int opcion = Validaciones.validarEntero("1. Registrar Paciente. \n2. Registrar Médico. \n3. Listar Pacientes. \n4. Listar Médicos. \n5. Crear Cita Médica. \n6. Modificar o cancelar cita. \n7. Registrar Tratamientos para un paciente. \n8. Marcar cita atendida. \n9. Reporte: citas atendidas por especialidad. \n10. Reporte: ingresos totales por tratamientos. \n11. Salir. \n");
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
                    System.out.println("Modificar o cancelar cita");
                }
                case 7 -> {
                    System.out.println("Registrar Tratamientos para un paciente");
                }
                case 8 -> {
                    System.out.println("Marcar cita atendida");
                }
                case 9 -> {
                    System.out.println("Reporte: citas atendidas por especialidad");
                }
                case 10 -> {
                    System.out.println("Reporte: ingresos totales por tratamientos");
                }
                default -> {
                    System.out.println("Saliendo del sistema...");
                    respuesta = "no";
                }
            }
        } while (respuesta.equalsIgnoreCase("si"));
    }

    private static void registrarPaciente(Hospital hp) {
        System.out.println("\n\n----Registrar Paciente----");
        String nombre = Validaciones.validarString("Nombre: ");
        String apellido = Validaciones.validarString("Apellido: ");
        String correo = Validaciones.validarCorreo("Correo: ");
        String telefono = Validaciones.validarString("Telefono: ");
        int tipoSeguro = Validaciones.validarEntero("Tipo de Seguro (1. IESS. 2. PRIVADO): ");
        boolean resultado = hp.guardarPaciente(nombre, apellido, correo, telefono, tipoSeguro);
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
        String telefono = Validaciones.validarString("Telefono: ");
        //Falta agregar el horario de atención
        String genero = Validaciones.validarString("Género: ");
        String especialidad = Validaciones.validarString("Especialidad: ");
        System.out.println("-----Horarios de Atención");
        String dias = Validaciones.validarString("Ingrese los días de atención (separados por coma (,)): ");
        LocalTime horaInicio = Validaciones.validarHora("Ingrese la hora de inicio (HH:mm): ");
        LocalTime horaFin = Validaciones.validarHora("Ingrese hasta que hora atiende (HH:mm): ");
        HorarioAtencion horario = hp.crearHorario(dias, horaInicio, horaFin);
        boolean activo = Validaciones.validarSiNo("¿Está activo? (si/no): ");
        boolean resultado = hp.guardarMedico(nombre, apellido, correo, telefono, genero, especialidad, activo, horario);
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
                int tipoSeguro = Validaciones.validarEntero("Ingrese el tipo de seguro: \n1. IESS \n2. PRIVADO.");
                hp.listarPacientes(tipoSeguro);
            }
            case 2 -> {
                hp.listarPacientesAll();
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
        String nombrePaciente = Validaciones.validarString("Ingrese el nombre del paciente: ");
        hp.listarMedicosAll();
        String nombreMedico = Validaciones.validarString("Ingrese el nombre del medico para listar sus horarios disponibles: ");
        hp.buscarHorarios(nombreMedico);
    }
}