package proyecto;

import proyecto.models.Hospital;
import proyecto.utilities.Validaciones;

public class Main {
    public static void main(String[] args) {
        Hospital hp = new Hospital("Hospital POO");
        String respuesta = Validaciones.validarString("Sistema de Gestión de Hospital. \nIniciar? (si/no): ");
        menu(hp, respuesta);
    }

    private static void menu(Hospital hp, String respuesta) {
        while (respuesta.equalsIgnoreCase("si")) {
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
                    System.out.println("Listar Médicos");
                }
                case 5 -> {
                    System.out.println("Crear Cita Médica");
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
        }
    }

    private static void registrarPaciente(Hospital hp) {
        System.out.println("\n\n----Registrar Paciente----");
        String nombre = Validaciones.validarString("Nombre: ");
        String telefono = Validaciones.validarString("Telefono: ");
        int tipoSeguro = Validaciones.validarEntero("Tipo de Seguro (1. Básico, 2. Premium, 3. VIP): ");
        boolean resultado = hp.guardarPaciente(nombre, telefono, tipoSeguro);
        if(resultado) {
            System.out.println("\nPaciente registrado exitosamente. \n");
        } else {
            System.out.println("\nError al registrar el paciente. \n");
        }
    }

    private static void registrarMedico(Hospital hp) {
        System.out.println("\n\n----Registrar Medico----");
        String nombre = Validaciones.validarString("Nombre: ");
        String telefono = Validaciones.validarString("Telefono: ");
        //Falta agregar el horario de atención
        String genero = Validaciones.validarString("Género: ");
        String especialidad = Validaciones.validarString("Especialidad: ");
        boolean activo = Validaciones.validarSiNo("¿Está activo? (si/no): ");
        boolean resultado = hp.guardarMedico(nombre, telefono, genero, especialidad, activo);
        if(resultado) {
            System.out.println("\nMédico registrado exitosamente. \n");
        } else {
            System.out.println("\nError al registrar el médico. \n");
        }
    }

    private static void listarPacientes(Hospital hp) {
        System.out.println("----Listar Pacientes----");
        int opcion = Validaciones.validarEntero("Listar por: \n1. Tipo de Seguro \n2. Todos");
        switch(opcion) {
            case 1 -> {
                int tipoSeguro = Validaciones.validarEntero("Ingrese el tipo de seguro: \n1. Básico \n2. Premium \n3. VIP");
                hp.listarPacientes(tipoSeguro);
            }
            case 2 -> {
                hp.listarPacientesAll();
            }
        }
    }
}