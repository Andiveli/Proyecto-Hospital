package proyecto.exceptions;

/**
 * Excepción lanzada cuando no se puede crear una cita médica por diversas
 * razones:
 * - Médico no disponible en el horario solicitado
 * - Conflicto con otra cita existente
 * - Horario fuera del rango de atención del médico
 * Esta es una excepción CHECKED porque representa una condición recuperable.
 */
public class CitaNoDisponibleException extends Exception {

    public CitaNoDisponibleException(String message) {
        super(message);
    }

    public CitaNoDisponibleException(String message, Throwable cause) {
        super(message, cause);
    }
}
