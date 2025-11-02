package proyecto.exceptions;

/**
 * Excepción lanzada cuando los datos ingresados por el usuario son inválidos.
 * Esta es una excepción UNCHECKED (RuntimeException) porque representa errores
 * de programación o validación que normalmente no deberían ocurrir si el
 * programa funciona correctamente.
 */
public class DatoInvalidoException extends RuntimeException {

    public DatoInvalidoException(String message) {
        super(message);
    }

    public DatoInvalidoException(String message, Throwable cause) {
        super(message, cause);
    }
}
