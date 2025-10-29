package proyecto.utilities;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Scanner;
import proyecto.models.Cita;
import proyecto.models.users.Medico;
import proyecto.models.users.Paciente;

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
    
    // Validar paciente para registrar cita
    
    public static boolean validarPacienteCita(String correoPaciente, ArrayList<Paciente> listaPacientes, Paciente pacienteCita){
        for (int i=0; i< listaPacientes.size(); i++){ 
                pacienteCita = new Paciente(listaPacientes.get(i)); //Obtenemos el i-esimo paciente
                if (pacienteCita.getCorreo().equals(correoPaciente)){
                    return true; //El for acaba apenas encuentre que el correo de uno de los de las listas es igual al ingresado
                }
            }
        return false; //Si el for acaba y no encontro correo, da false
    }
    
    
    
    // Validar dia para registrar cita
    
    public static boolean validarDia(int dia, int mes){ //Validamos todos los casos posibles de dias con meses. Ej: No puedes poner 31 en febrero.
        if (dia <= 0){
            return false;
        }
        switch(mes){
            case 1:
                if ((dia > 31)){
                    return false;
                }
                else{
                    return true;
                }
            case 2:
                if ((dia>29)){
                    return false;
                }
                else{
                    return true;
                }
            case 3:
                if ((dia > 31)){
                    return false;
                }
                else{
                    return true;
                }
            case 4:
                if ((dia > 30)){
                    return false;
                }
                else{
                    return true;
                }
            case 5:
                if ((dia > 31)){
                    return false;
                }
                else{
                    return true;
                }
            case 6:
                if ((dia > 30)){
                    return false;
                }
                else{
                    return true;
                }
            case 7:
                if ((dia > 31)){
                    return false;
                }
                else{
                    return true;
                }
            case 8:
                if ((dia > 31)){
                    return false;
                }
                else{
                    return true;
                }
            case 9:
                if ((dia > 30)){
                    return false;
                }
                else{
                    return true;
                }
            case 10:
                if ((dia > 31)){
                    return false;
                }
                else{
                    return true;
                }
            case 11:
                if ((dia > 30)){
                    return false;
                }
                else{
                    return true;
                }
            case 12:
                if ((dia > 31)){
                    return false;
                }
                else{
                    return true;
                }
            default:
                return false;
        }
    }
    
    
    // Validar medico para registrar cita: validar correo de medico
    
    public static boolean validarMedicoCorreoCita(String correoMedico, ArrayList<Medico> listaMedicos){
        for (int i=0; i<listaMedicos.size(); i++){ //hp.getListaMedico) obtiene la lista de medicos
                Medico medicoCita = new Medico(listaMedicos.get(i)); //Obtenemos el i-esimo medico
                if (medicoCita.getCorreo().equals(correoMedico)){
                    return true;
                }
                     //El for acaba apenas encuentre que el correo de uno de los de las listas es igual al ingresado
            }
        return false;
    }

    // Validar medico para registrar cita: sus horarios concuerdan con el horario asignado
    
    public static boolean validarMedicoHorarioCita(LocalTime horaCita, LocalTime medicoHoraInicio, LocalTime medicoHoraFin){
        return !( (horaCita.isBefore(medicoHoraInicio)) ||  (horaCita.isAfter(medicoHoraFin)) );
        // En caso de que NO pase: La hora de inicio de la cita va ANTES que la del medico
        //                         La hora de fin de la cita va DESPUES que la del medico 
        // Entonces el horario sera valido.
    }
    
    // Validar medico para registrar cita: no se encuentra en una cita
    
    public static boolean validarMedicoDispoCita(ArrayList<Cita> listaCitas, String medicoCitaCorreo, LocalDate ymdCita){
        for (Cita cita: listaCitas){
            String medicoCitaCorreoFor = cita.getMedico().getCorreo(); //Medico de la cita del for
            LocalDateTime fechaCitaFor = cita.getFecha(); //Fecha de la cita del for
            LocalDate ymdCitaFor = fechaCitaFor.toLocalDate(); //dia mes anio de la cita for
            if ( (medicoCitaCorreoFor.equals(medicoCitaCorreo)) && (ymdCitaFor.isEqual(ymdCita)) ){
                return false; // Si el medico tiene justo una cita en esa fecha, y nuestra cita se encuentra entre la hora inicio y salida del medico
                              // puesto que ya existe una cita en ese intervalo, se van a chocar.
            }
        }
        return true; // Si jamas pasa lo de arriba, llega a retornar true
    }
    
    
    

}
