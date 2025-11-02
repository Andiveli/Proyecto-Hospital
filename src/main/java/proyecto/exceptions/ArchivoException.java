package proyecto.exceptions;

/**
 * Excepción lanzada cuando ocurre un error al leer o escribir archivos del
 * sistema.
 * Esta es una excepción CHECKED porque representa una condición recuperable
 * relacionada con operaciones de I/O que el programa debe manejar.
 */
public class ArchivoException extends Exception {

    public ArchivoException(String message) {
        super(message);
    }

    public ArchivoException(String message, Throwable cause) {
        super(message, cause);
    }
}
