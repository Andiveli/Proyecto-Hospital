package proyecto.utilities;

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
        return sc.nextLine().trim();
    }

    public static boolean validarSiNo(String mensaje) {
        System.out.print(mensaje);
        String respuesta = sc.nextLine();
        while(!respuesta.equalsIgnoreCase("si") && !respuesta.equalsIgnoreCase("no")) {
            System.out.print("Entrada invalida. \n" + mensaje);
            respuesta = sc.nextLine();
        }
        return respuesta.equalsIgnoreCase("si");
    }


}
