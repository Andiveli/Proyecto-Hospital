package proyecto.utilities;

import java.time.LocalTime;
import java.util.Scanner;

public class Validaciones {
    private static final Scanner sc = new Scanner(System.in);
    public static int validarEntero(String mensaje) {
        System.out.print(mensaje);
        while(!sc.hasNextInt()) {
            System.out.print("Entrada invalida. \n" + mensaje);
            sc.next();
        }
        int respuesta = sc.nextInt();
        sc.nextLine();
        return respuesta;
    }

    public static String validarString(String mensaje) {
        System.out.print(mensaje);
        return sc.nextLine().trim().toLowerCase();
    }

    public static String validarCorreo(String mensaje) {
        System.out.print(mensaje);
        String respuesta = sc.nextLine().trim();
        while(!respuesta.contains("@") || !respuesta.contains(".")) {
            System.out.print("Correo invalido. \n" + mensaje);
            respuesta = sc.nextLine().trim();
        }
        return respuesta;
    }

    public static boolean validarSiNo(String mensaje) {
        System.out.print(mensaje);
        String respuesta = sc.nextLine();
        while(!respuesta.equalsIgnoreCase("si") && !respuesta.equalsIgnoreCase("no")) {
            System.out.print("Entrada invalida. \n" + mensaje);
            respuesta = sc.nextLine(); //Este deberia ir al inicio
        }
        return respuesta.equalsIgnoreCase("si");
    }

    public static LocalTime validarHora(String mensaje) {
        System.out.print(mensaje);
        String respuesta = sc.nextLine();
        while(!isHora(respuesta)) {
            System.out.println("El formato de la hora es incorrecto. Recuerde (HH:mm): ");
            respuesta = sc.nextLine();
        }
        return LocalTime.parse(respuesta);
    }

    private static boolean isHora(String respuesta) {
        try {
            LocalTime.parse(respuesta);
            return true;
        } catch (Exception e) {
            return false;
        }
    }


}
