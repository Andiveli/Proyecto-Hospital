package proyecto.exceptions;

/**
 * Excepción lanzada cuando un médico no se encuentra en el sistema.
 * Esta es una excepción CHECKED porque representa una condición recuperable
 * que el programa debe manejar explícitamente.
 */
public class MedicoNoEncontradoException extends Exception {

    public MedicoNoEncontradoException(String message) {
        super(message);
    }

    public MedicoNoEncontradoException(String message, Throwable cause) {
        super(message, cause);
    }
}
