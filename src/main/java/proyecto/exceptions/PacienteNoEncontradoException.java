package proyecto.exceptions;

/**
 * Excepción lanzada cuando un paciente no se encuentra en el sistema.
 * Esta es una excepción CHECKED porque representa una condición recuperable
 * que el programa debe manejar explícitamente.
 */
public class PacienteNoEncontradoException extends Exception {

    public PacienteNoEncontradoException(String message) {
        super(message);
    }

    public PacienteNoEncontradoException(String message, Throwable cause) {
        super(message, cause);
    }
}
