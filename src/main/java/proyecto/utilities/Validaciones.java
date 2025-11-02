package proyecto.utilities;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Scanner;
import proyecto.exceptions.DatoInvalidoException;

public class Validaciones {
    private static final Scanner sc = new Scanner(System.in);

    /**
     * Valida la entrada de un número entero con manejo de excepciones.
     * 
     * @param mensaje Mensaje a mostrar al usuario
     * @return entero validado
     * @throws DatoInvalidoException si hay error crítico o se exceden intentos
     */
    public static int validarEntero(String mensaje) throws DatoInvalidoException {
        System.out.print(mensaje);
        int intentos = 0;
        final int MAX_INTENTOS = 3;

        while (intentos < MAX_INTENTOS) {
            try {
                if (!sc.hasNextInt()) {
                    String entrada = sc.next();
                    throw new DatoInvalidoException(
                            "Entrada inválida: '" + entrada + "'. Debe ingresar un número entero.");
                }
                int respuesta = sc.nextInt();
                sc.nextLine(); // Limpiar buffer
                return respuesta;
            } catch (DatoInvalidoException e) {
                intentos++;
                if (intentos < MAX_INTENTOS) {
                    System.out.println("Error: " + e.getMessage());
                    System.out.print("Intente nuevamente: ");
                }
            } catch (Exception e) {
                intentos++;
                System.out.println("Error inesperado al leer la entrada.");
                sc.nextLine(); // Limpiar buffer en caso de error
            }
        }

        throw new DatoInvalidoException("Número máximo de intentos alcanzado. Operación cancelada.");
    }

    /**
     * Valida la entrada de un número decimal con manejo de excepciones.
     * 
     * @param mensaje Mensaje a mostrar al usuario
     * @return double validado
     * @throws DatoInvalidoException si hay error crítico o se exceden intentos
     */
    public static double validarDouble(String mensaje) throws DatoInvalidoException {
        System.out.print(mensaje);
        int intentos = 0;
        final int MAX_INTENTOS = 3;

        while (intentos < MAX_INTENTOS) {
            try {
                if (!sc.hasNextDouble()) {
                    String entrada = sc.next();
                    throw new DatoInvalidoException(
                            "Entrada inválida: '" + entrada + "'. Debe ingresar un número decimal.");
                }
                double respuesta = sc.nextDouble();
                sc.nextLine(); // Limpiar buffer

                // Validación adicional: el costo no puede ser negativo
                if (respuesta < 0) {
                    throw new DatoInvalidoException("El valor no puede ser negativo.");
                }

                return respuesta;
            } catch (DatoInvalidoException e) {
                intentos++;
                if (intentos < MAX_INTENTOS) {
                    System.out.println("Error: " + e.getMessage());
                    System.out.print("Intente nuevamente: ");
                }
            } catch (Exception e) {
                intentos++;
                System.out.println("Error inesperado al leer la entrada.");
                sc.nextLine(); // Limpiar buffer en caso de error
            }
        }

        throw new DatoInvalidoException("Número máximo de intentos alcanzado. Operación cancelada.");
    }

    /**
     * Valida una entrada de texto básica.
     * 
     * @param mensaje Mensaje a mostrar al usuario
     * @return texto validado
     * @throws DatoInvalidoException si el texto está vacío o hay error crítico
     */
    public static String validarString(String mensaje) throws DatoInvalidoException {
        System.out.print(mensaje);
        int intentos = 0;
        final int MAX_INTENTOS = 3;

        while (intentos < MAX_INTENTOS) {
            try {
                String respuesta = sc.nextLine().trim();

                if (respuesta.isEmpty()) {
                    throw new DatoInvalidoException("El campo no puede estar vacío.");
                }

                return respuesta.toLowerCase();
            } catch (DatoInvalidoException e) {
                intentos++;
                if (intentos < MAX_INTENTOS) {
                    System.out.println("Error: " + e.getMessage());
                    System.out.print("Intente nuevamente: ");
                }
            } catch (Exception e) {
                intentos++;
                System.out.println("Error inesperado al leer la entrada.");
            }
        }

        throw new DatoInvalidoException("Número máximo de intentos alcanzado. Operación cancelada.");
    }

    /**
     * Valida el formato de un correo electrónico.
     * 
     * @param mensaje Mensaje a mostrar al usuario
     * @return correo validado
     * @throws DatoInvalidoException si el formato es inválido o hay error crítico
     */
    public static String validarCorreo(String mensaje) throws DatoInvalidoException {
        System.out.print(mensaje);
        int intentos = 0;
        final int MAX_INTENTOS = 3;

        while (intentos < MAX_INTENTOS) {
            try {
                String respuesta = sc.nextLine().trim();

                if (respuesta.isEmpty()) {
                    throw new DatoInvalidoException("El correo no puede estar vacío.");
                }

                // Validación más robusta de correo
                if (!respuesta.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
                    throw new DatoInvalidoException("Formato de correo inválido. Debe ser: usuario@dominio.extensión");
                }

                return respuesta.toLowerCase();
            } catch (DatoInvalidoException e) {
                intentos++;
                if (intentos < MAX_INTENTOS) {
                    System.out.println("Error: " + e.getMessage());
                    System.out.print("Intente nuevamente: ");
                }
            } catch (Exception e) {
                intentos++;
                System.out.println("Error inesperado al leer la entrada.");
            }
        }

        throw new DatoInvalidoException("Número máximo de intentos alcanzado. Operación cancelada.");
    }

    /**
     * Valida una respuesta de sí/no.
     * 
     * @param mensaje Mensaje a mostrar al usuario
     * @return true si es "sí", false si es "no"
     * @throws DatoInvalidoException si hay error crítico o se exceden intentos
     */
    public static boolean validarSiNo(String mensaje) throws DatoInvalidoException {
        System.out.print(mensaje);
        int intentos = 0;
        final int MAX_INTENTOS = 3;

        while (intentos < MAX_INTENTOS) {
            try {
                String respuesta = sc.nextLine().trim().toLowerCase();

                if (respuesta.equals("si") || respuesta.equals("sí")) {
                    return true;
                } else if (respuesta.equals("no")) {
                    return false;
                } else {
                    throw new DatoInvalidoException(
                            "Entrada inválida: '" + respuesta + "'. Debe responder 'si' o 'no'.");
                }
            } catch (DatoInvalidoException e) {
                intentos++;
                if (intentos < MAX_INTENTOS) {
                    System.out.println("Error: " + e.getMessage());
                    System.out.print("Intente nuevamente: ");
                }
            } catch (Exception e) {
                intentos++;
                System.out.println("Error inesperado al leer la entrada.");
            }
        }

        throw new DatoInvalidoException("Número máximo de intentos alcanzado. Operación cancelada.");
    }

    /**
     * Valida el formato de una hora (HH:mm).
     * 
     * @param mensaje Mensaje a mostrar al usuario
     * @return LocalTime validado
     * @throws DatoInvalidoException si el formato es inválido o hay error crítico
     */
    public static LocalTime validarHora(String mensaje) throws DatoInvalidoException {
        System.out.print(mensaje);
        int intentos = 0;
        final int MAX_INTENTOS = 3;

        while (intentos < MAX_INTENTOS) {
            try {
                String respuesta = sc.nextLine().trim();

                if (!isHora(respuesta)) {
                    throw new DatoInvalidoException("Formato de hora inválido. Debe ser HH:mm (ej: 14:30)");
                }

                return LocalTime.parse(respuesta);
            } catch (DatoInvalidoException e) {
                intentos++;
                if (intentos < MAX_INTENTOS) {
                    System.out.println("Error: " + e.getMessage());
                    System.out.print("Intente nuevamente: ");
                }
            } catch (Exception e) {
                intentos++;
                System.out.println("Error inesperado al procesar la hora.");
            }
        }

        throw new DatoInvalidoException("Número máximo de intentos alcanzado. Operación cancelada.");
    }

    private static boolean isHora(String respuesta) {
        try {
            LocalTime.parse(respuesta);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private static boolean isDia(String respuesta) {
        try {
            DayOfWeek.valueOf(convertirIngles(respuesta.toLowerCase()));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static String convertirIngles(String dia) {
        return switch (dia) {
            case "lunes" -> "MONDAY";
            case "martes" -> "TUESDAY";
            case "miercoles" -> "WEDNESDAY";
            case "jueves" -> "THURSDAY";
            case "viernes" -> "FRIDAY";
            case "sabado" -> "SATURDAY";
            case "domingo" -> "SUNDAY";
            default -> "";
        };
    }

    /**
     * Valida el formato de una cita médica (día HH:mm).
     * 
     * @param mensaje Mensaje a mostrar al usuario
     * @return cita validada
     * @throws DatoInvalidoException si el formato es inválido o hay error crítico
     */
    public static String validarCita(String mensaje) throws DatoInvalidoException {
        System.out.print(mensaje);
        int intentos = 0;
        final int MAX_INTENTOS = 3;

        while (intentos < MAX_INTENTOS) {
            try {
                String respuesta = sc.nextLine().trim();

                String[] partes = respuesta.split(" ");

                if (partes.length != 2) {
                    throw new DatoInvalidoException("Formato inválido. Debe ser: día HH:mm (ej: lunes 14:30)");
                }

                if (!isDia(partes[0])) {
                    throw new DatoInvalidoException(
                            "Día inválido. Debe ser: lunes, martes, miércoles, jueves, viernes, sábado o domingo");
                }

                if (!isHora(partes[1])) {
                    throw new DatoInvalidoException("Hora inválida. Debe estar en formato HH:mm (ej: 14:30)");
                }

                return respuesta.toLowerCase();
            } catch (DatoInvalidoException e) {
                intentos++;
                if (intentos < MAX_INTENTOS) {
                    System.out.println("Error: " + e.getMessage());
                    System.out.print("Intente nuevamente: ");
                }
            } catch (Exception e) {
                intentos++;
                System.out.println("Error inesperado al procesar la cita.");
            }
        }

        throw new DatoInvalidoException("Número máximo de intentos alcanzado. Operación cancelada.");
    }

}
