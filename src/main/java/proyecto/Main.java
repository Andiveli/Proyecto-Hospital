package proyecto;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.EnumSet;
import proyecto.enums.EstadoCita;
import static proyecto.enums.TipoSeguro.IESS;
import proyecto.models.Cita;

import proyecto.models.HorarioAtencion;
import proyecto.models.Hospital;
import proyecto.models.users.Medico;
import proyecto.models.users.Paciente;
import proyecto.utilities.Validaciones;
import static proyecto.utilities.Validaciones.validarDia;
import static proyecto.utilities.Validaciones.validarEntero;
import static proyecto.utilities.Validaciones.validarHora;
import static proyecto.utilities.Validaciones.validarMedicoCorreoCita;
import static proyecto.utilities.Validaciones.validarMedicoDispoCita;
import static proyecto.utilities.Validaciones.validarMedicoHorarioCita;
import static proyecto.utilities.Validaciones.validarPacienteCita;

public class Main {
    public static void main(String[] args) {
        Hospital hp = new Hospital("Hospital POO");
        System.out.println("Sistema de Gestión de Hospital.");
        menu(hp);
    }

    private static void menu(Hospital hp){
        
        LocalTime PruebaInicio = LocalTime.of(13, 00);    
        LocalTime PruebaFin = LocalTime.of(14, 00); 
        HorarioAtencion horarioPrueba1 = new HorarioAtencion(PruebaInicio, PruebaFin, EnumSet.range(DayOfWeek.MONDAY, DayOfWeek.SATURDAY));
        Medico medPrueba1 = new Medico(1, "Maria", "Mama", "maricarmen@gmail.com", "0932193921", horarioPrueba1, "Femenino", "Cirujana", true);
        hp.getListaMedicos().add(medPrueba1);
        
        
        LocalTime PruebaInicio2 = LocalTime.of(15, 00);    
        LocalTime PruebaFin2 = LocalTime.of(16, 00); 
        HorarioAtencion horarioPrueba2 = new HorarioAtencion(PruebaInicio2, PruebaFin2, EnumSet.range(DayOfWeek.MONDAY, DayOfWeek.SATURDAY));
        Medico medPrueba2 = new Medico(2, "Pedro", "Martinez", "pemar@gmail.com", "093219432421", horarioPrueba2, "Masculino", "Carnicero", true);
        hp.getListaMedicos().add(medPrueba2);
           
        LocalTime PruebaInicio3 = LocalTime.of(17, 00);    
        LocalTime PruebaFin3 = LocalTime.of(18, 00); 
        HorarioAtencion horarioPrueba3 = new HorarioAtencion(PruebaInicio3, PruebaFin3, EnumSet.range(DayOfWeek.MONDAY, DayOfWeek.SATURDAY));
        Medico medPrueba3 = new Medico(3, "Juana", "De arco", "juana@gmail.com", "0932132132421", horarioPrueba3, "Femenino", "Oftamnologa", true);
        hp.getListaMedicos().add(medPrueba3);
        
        
        
        String respuesta = "si";
        do {
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
                    listarMedicos(hp);
                }
                case 5 -> {
                    crearCitaMedica(hp);
                }
                case 6 -> {
                    modificarCitaMedica(hp);
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
        } while (respuesta.equalsIgnoreCase("si"));
    }

    private static void registrarPaciente(Hospital hp) {
        System.out.println("\n\n----Registrar Paciente----");
        String nombre = Validaciones.validarString("Nombre: ");
        String apellido = Validaciones.validarString("Apellido: ");
        String correo = Validaciones.validarCorreo("Correo: ");
        String telefono = Validaciones.validarString("Telefono: ");
        int tipoSeguro = Validaciones.validarEntero("Tipo de Seguro (1. IESS. 2. PRIVADO): ");
        boolean resultado = hp.guardarPaciente(nombre, apellido, correo, telefono, tipoSeguro);
        if(resultado) {
            System.out.println("\nPaciente registrado exitosamente. \n");
        } else {
            System.out.println("\nError al registrar el paciente. \n");
        }
    }

    private static void registrarMedico(Hospital hp) {
        System.out.println("\n\n----Registrar Medico----");
        String nombre = Validaciones.validarString("Nombre: ");
        String apellido = Validaciones.validarString("Apellido: ");
        String correo = Validaciones.validarCorreo("Correo: ");
        String telefono = Validaciones.validarString("Telefono: ");
        //Falta agregar el horario de atención
        String genero = Validaciones.validarString("Género: ");
        String especialidad = Validaciones.validarString("Especialidad: ");
        System.out.println("-----Horarios de Atención");
        String dias = Validaciones.validarString("Ingrese los días de atención (separados por coma (,)): ");
        LocalTime horaInicio = Validaciones.validarHora("Ingrese la hora de inicio (HH:mm): ");
        LocalTime horaFin = Validaciones.validarHora("Ingrese hasta que hora atiende (HH:mm): ");
        HorarioAtencion horario = hp.crearHorario(dias, horaInicio, horaFin);
        boolean activo = Validaciones.validarSiNo("¿Está activo? (si/no): ");
        boolean resultado = hp.guardarMedico(nombre, apellido, correo, telefono, genero, especialidad, activo, horario);
        if(resultado) {
            System.out.println("\nMédico registrado exitosamente. \n");
        } else {
            System.out.println("\nError al registrar el médico. \n");
        }
    }

    private static void listarPacientes(Hospital hp) {
        System.out.println("----Listar Pacientes----");
        int opcion = Validaciones.validarEntero("Listar por: \n1. Tipo de Seguro \n2. Todos. \n");
        switch(opcion) {
            case 1 -> {
                int tipoSeguro = Validaciones.validarEntero("Ingrese el tipo de seguro: \n1. IESS \n2. PRIVADO.");
                hp.listarPacientes(tipoSeguro);
            }
            case 2 -> {
                hp.listarPacientesAll();
            }
        }
    }

    private static void listarMedicos(Hospital hp) {
        System.out.println("----Listar Médicos----");
        int opcion = Validaciones.validarEntero("Listar por: \n1. Especialidad \n2. Genero. \n3. Activos \n4. Todos. \n");
        switch(opcion) {
            case 1 -> {
                String especialidad = Validaciones.validarString("Ingrese la especialidad: \n");
                hp.listarMedicosPorEspecialidad(especialidad);
            }
            case 2 -> {
                String genero = Validaciones.validarString("Ingrese el género: ");
                hp.listarMedicosPorGenero(genero);
            }
            case 3 -> {
                hp.listarMedicosActivos();
            }
            case 4 -> {
                hp.listarMedicosAll();
            }
        }
    }
    private static void crearCitaMedica(Hospital hp) {
        
        //REGISTRO PACIENTE
        String correoPaciente;
        Paciente pacienteCita = null;
        
        
        
        //Validacion paciente
        do{
            System.out.println("Pacientes registrados: ");
            hp.listarPacientesAll();
            correoPaciente = Validaciones.validarString("Ingrese el correo del paciente a añadir: ");
            
            if (!validarPacienteCita(correoPaciente, hp.getListaPacientes(), pacienteCita)){ //Parametros: correo ingresado, lista de pacientes del hospital
                System.out.println("");                                        //hp.getListaPacientes() obtiene la lista de pacientes
                System.out.println("Correo de paciente no encontrado");
                System.out.println("##########################################");
            }
            else{
                // Asignacion de variable pacienteCita:
                for (int i=0; i< hp.getListaPacientes().size(); i++){ 
                    Paciente pacienteCitaFor = new Paciente(hp.getListaPacientes().get(i)); //Obtenemos el i-esimo paciente
                    if (pacienteCitaFor.getCorreo().equals(correoPaciente)){
                        pacienteCita = new Paciente(pacienteCitaFor); 
                        break; //El for acaba apenas encuentre que el correo de uno de los de las listas es igual al ingresado
                    }
                }
            }
        }
        while(!validarPacienteCita(correoPaciente, hp.getListaPacientes(), pacienteCita)); //El while termina apenas encuentre un correo valido
        

        // El paciente ya queda guardado en pacienteCita
        
        
        //REGISTRO FECHA
        LocalDateTime fechaCita;
        //year
        int anioCita;
        do{
            anioCita = validarEntero("Año de la cita: ");
            if (anioCita <= 0){
                System.out.println("Entrada invalida.");
            }
        }
        while(anioCita <= 0);
        //mes
        int mesCita;
        do{
            mesCita = validarEntero("Mes de la cita: ");
            if ((mesCita <= 0)||(mesCita > 12)){
                System.out.println("Entrada invalida.");
            }
            
        }
        while((mesCita <= 0)||(mesCita > 12));
        
        //dia
        int diaCita;
        do{
            diaCita = validarEntero("Dia de la cita: ");
            if (!validarDia(diaCita, mesCita)){ // Vease validarDia en clase 'Validaciones;
                System.out.println("Ingreso no valido");
            }
        }
        while(!validarDia(diaCita, mesCita));
        //fecha
        LocalDate ymdCita = LocalDate.of(anioCita, mesCita, diaCita); // Crear anio, mes, dia
        //hora
        LocalTime horaCita = validarHora("Horario de la cita, formato HH:MM ");
        
        //Asignacion
        fechaCita = LocalDateTime.of(ymdCita, horaCita); // Crear fecha
        
       
        
        
        //REGISTRO MEDICO
        
        
      
        
        String correoMedico;
        Medico medicoCita = null;

        boolean validarMedico = false;  //Se iguala con validarMedicoHorarioCita()
        do{
            
            System.out.println("Médicos registrados: ");
            hp.listarMedicosAll();
            correoMedico = Validaciones.validarString("Ingrese el correo del medico a añadir: ");
            
            //Validacion correo
            
            if (!validarMedicoCorreoCita(correoMedico, hp.getListaMedicos())){ //Validacion de correo existente
                System.out.println("");                        //hp.getListaMedico() obtiene la lista de medicos
                System.out.println("Correo de medico no encontrado");
                System.out.println("#################################");
            }
           
            else {
                for (int i=0; i<hp.getListaMedicos().size(); i++){ 
                     //Note que, a partir de este punto, medicoCita ya tiene un medico asignado. Se le asigna el medico con el correo encontrado
                    Medico medicoCitaFor = new Medico(hp.getListaMedicos().get(i)); 
                    if (medicoCitaFor.getCorreo().equals(correoMedico)){
                        medicoCita = new Medico(medicoCitaFor);
                    }
                   
                }
               
                
                if (!medicoCita.isActivo()){
                System.out.println("");                       
                System.out.println("El medico no se encuentra activo en este momento.");
                System.out.println("################################################");
            }


                    
                
                //Validacion de disponibilidad:
                
                LocalTime medicoHoraInicio = medicoCita.getHorarioAtencion().getHorarioInicio(); //Del medico asignado a la cita, obtengo su horario y, del horario, obtengo hora de inicio
                LocalTime medicoHoraFin = medicoCita.getHorarioAtencion().getHorarioFin();
                
                if( !(validarMedicoHorarioCita(horaCita, medicoHoraInicio, medicoHoraFin)) ){ //Desigualdades para LocalTime
                    System.out.println(""); //Validacion de horario 
                    System.out.println("El medico no tiene horario disponible para la cita.");
                    System.out.println("");
                }
                else{
                    // Validacion de no tener una cita a la misma fecha y hora   
                    if( !validarMedicoDispoCita(hp.getListaCitas(), medicoCita.getCorreo(), ymdCita) ){ //Desigualdades para LocalTime
                            System.out.println(""); //Validacion de horario 
                            System.out.println("El medico tiene una cita en ese horario.");
                            System.out.println("");
                            
                        }
                    else{
                        validarMedico = true; // Se cumplen todas las condiciones para validar Medico.
                    }
                }
                
            }
            
        }
        while( !validarMedico ); //El while termina 
        
        //REGISTRANDO CITA
        Cita citaCreada = new Cita(hp.getListaCitas().size() + 1, fechaCita, pacienteCita, medicoCita); // El ID es el tamano + 1
        hp.getListaCitas().add(citaCreada); 
        System.out.println("Cita creada exitosamente!");
        System.out.println(citaCreada);
        
    }
    
    
    
    private static void modificarCitaMedica(Hospital hp){
        System.out.println("Lita de citas:");
        hp.listarCitasAll();
        int indice = validarEntero("Seleccione el ID de la cita a modificar: ");
        indice -= 1; // Los ID's empiezan desde 1. El indice en un array, desde 0
        System.out.println("Seleccione la accion a realizar: ");
        
        int opcion;
        
        // Menu
        do{
            opcion = Validaciones.validarEntero("1. Modificar fecha "
                                                + "\n2. Modificar paciente"
                                                + "\n3. Modificar medico"
                                                + "\n4. Modificar estado");
            if ( !((opcion >= 1) && (opcion <= 4)) ){
                System.out.println("");
                System.out.println("Ingreso no valido.");
                System.out.println("#####################"); //Si no selecciona un numero entre 1 y 4
            }
        }
        while( !((opcion >= 1) && (opcion <= 4)) );   
        
        Cita citaCambiar = hp.getListaCitas().get(indice); //Indice es la posicion de la cita
        
        switch(opcion){
            // Modificar fecha
            case 1:
                int newAnioCita;
                do{
                    newAnioCita = validarEntero("Año de la cita: ");
                    if (newAnioCita <= 0){
                        System.out.println("Entrada invalida.");
                    }
                }
                while(newAnioCita <= 0);
                //mes
                int newMesCita;
                do{
                    newMesCita = validarEntero("Mes de la cita: ");
                    if ((newMesCita <= 0)||(newMesCita > 12)){
                        System.out.println("Entrada invalida.");
                    }
            
                }
                while((newMesCita <= 0)||(newMesCita > 12));
        
                //dia
                int newDiaCita;
                do{
                    newDiaCita = validarEntero("Dia de la cita: ");
                    if (!validarDia(newDiaCita, newMesCita)){ // Vease validarDia en clase 'Validaciones;
                        System.out.println("Ingreso no valido");
                    }
                }
                while(!validarDia(newDiaCita, newMesCita));
                //fecha
                LocalDate newYmdCita = LocalDate.of(newAnioCita, newMesCita, newDiaCita); // Crear anio, mes, dia
                //hora
                LocalTime newHoraCita = validarHora("Horario de la cita, formato HH:MM ");
                
                
                //VERIFICAR QUE EL CAMBIO DE CITA COINCIDA CON EL HORARIO DEL MEDICO:
                Medico medicoNewCita = hp.getListaCitas().get(indice).getMedico();
                
        
                //Asignacion
                LocalDateTime newFechaCita = LocalDateTime.of(newYmdCita, newHoraCita); // Crear fecha nueva
               
                citaCambiar.setFecha(newFechaCita);
                
                System.out.print("Fecha modificada exitosamente!");
                System.out.print("Nueva fecha: " + newFechaCita);
                System.out.print("La cita ahora es: " + hp.getListaCitas().get(indice));
                break;
                
            case 2:
                // Modificar  paciente 
                String newCorreoPaciente;
                Paciente newPacienteCita = null;
                do{
                    System.out.println("Pacientes registrados: ");
                    hp.listarPacientesAll();
                    newCorreoPaciente = Validaciones.validarString("Ingrese el correo del paciente a añadir: ");
            
                    if (!validarPacienteCita(newCorreoPaciente, hp.getListaPacientes(), newPacienteCita)){ //Parametros: correo ingresado, lista de pacientes del hospital
                        System.out.println("");                                        //hp.getListaPacientes() obtiene la lista de pacientes
                        System.out.println("Correo de paciente no encontrado");
                        System.out.println("##########################################");
                    }
                    else{
                        // Asignacion de variable pacienteCita:
                        for (int i=0; i< hp.getListaPacientes().size(); i++){ 
                            Paciente pacienteCitaFor = new Paciente(hp.getListaPacientes().get(i)); //Obtenemos el i-esimo paciente
                            if (pacienteCitaFor.getCorreo().equals(newCorreoPaciente)){
                                newPacienteCita = new Paciente(pacienteCitaFor);
                                break; //El for acaba apenas encuentre que el correo de uno de los de las listas es igual al ingresado
                            }
                        }
                    }
                }
                while(!validarPacienteCita(newCorreoPaciente, hp.getListaPacientes(), newPacienteCita));
                
                // Asignacion
                
                citaCambiar.setPaciente(newPacienteCita);
                
                System.out.print("Paciente modificado exitosamente!");
                System.out.print("Nuevo paciente: " + newPacienteCita);
                System.out.print("La cita ahora es: " + hp.getListaCitas().get(indice));
                break;
                
            case 3:
                // Modificar medico
                String newCorreoMedico;
                Medico newMedicoCita = null;
                boolean validarNewMedico = false;  //Se iguala con validarMedicoHorarioCita()
                do{
            
                    System.out.println("Médicos registrados: ");
                    hp.listarMedicosAll();
                    newCorreoMedico = Validaciones.validarString("Ingrese el correo del medico a añadir: ");
            
                    //Validacion correo
            
                    if (!validarMedicoCorreoCita(newCorreoMedico, hp.getListaMedicos())){ //Validacion de correo existente
                        System.out.println("");                        //hp.getListaMedico() obtiene la lista de medicos
                        System.out.println("Correo de medico no encontrado");
                        System.out.println("#################################");
                    }
           
                    else {
                        for (int i=0; i<hp.getListaMedicos().size(); i++){ 
                            //Note que, a partir de este punto, medicoCita ya tiene un medico asignado. Se le asigna el medico con el correo encontrado
                            Medico medicoCitaFor = new Medico(hp.getListaMedicos().get(i)); 
                            if (medicoCitaFor.getCorreo().equals(newCorreoMedico)){
                                newMedicoCita = new Medico(medicoCitaFor);
                            }
                   
                        }
               
                
                        if (!newMedicoCita.isActivo()){
                        System.out.println("");                       
                        System.out.println("El medico no se encuentra activo en este momento.");
                        System.out.println("################################################");
                    }


                    
                
                        //Validacion de disponibilidad:
                
                        LocalTime medicoHoraInicio = newMedicoCita.getHorarioAtencion().getHorarioInicio(); //Del medico asignado a la cita, obtengo su horario y, del horario, obtengo hora de inicio
                        LocalTime medicoHoraFin = newMedicoCita.getHorarioAtencion().getHorarioFin();
                        
                        LocalTime horaCita = citaCambiar.getFecha().toLocalTime(); //Obtenemos la hora de la fecha
                        LocalDate ymdCita = citaCambiar.getFecha().toLocalDate(); //Obtenemos la fecha de la cita 
                        if( !(validarMedicoHorarioCita(horaCita, medicoHoraInicio, medicoHoraFin)) ){ //Desigualdades para LocalTime
                            System.out.println(""); //Validacion de horario 
                            System.out.println("El medico no tiene horario disponible para la cita.");
                            System.out.println("");
                        }
                        else{
                            // Validacion de no tener una cita a la misma fecha y hora   
                            if( !validarMedicoDispoCita(hp.getListaCitas(), newMedicoCita.getCorreo(), ymdCita) ){ //Desigualdades para LocalTime
                                    System.out.println(""); //Validacion de horario 
                                    System.out.println("El medico tiene una cita en ese horario.");
                                    System.out.println("");
                            
                                }
                            else{
                                validarNewMedico = true; // Se cumplen todas las condiciones para validar Medico.
                            }
                        }
                
                    }
            
                }
                while( !validarNewMedico ); //El while termina
                

                //Asignacion 
                citaCambiar.setMedico(newMedicoCita);
                
                System.out.print("Paciente modificado exitosamente!");
                System.out.print("Nuevo paciente: " + newMedicoCita);
                System.out.print("La cita ahora es: " + hp.getListaCitas().get(indice));
                break;
               
            case 4:
                int indice2;
                do{
                    System.out.println("Elija el nuevo estado de su cita");
                    indice2 = validarEntero("1. PROGRAMADA \n2. CANCELADA \n3. ATENDIDA");
                    if ( !((indice2 >= 1) && (indice2 <= 3)) ){
                        System.out.println("");
                        System.out.println("Ingreso no valido");
                        System.out.println("##########");
                    }
                }
                while( !((indice2 >= 1) && (indice2 <= 3)) );
                
                switch(indice2){
                    case 1:
                        citaCambiar.setEstado(EstadoCita.PROGRAMADA);
                        System.out.println("El estado de su cita ha cambiado a PROGRAMADA.");
                        System.out.print("La cita ahora es: " + citaCambiar);
                        break;
                    case 2: 
                        citaCambiar.setEstado(EstadoCita.CANCELADA);
                        System.out.println("El estado de su cita ha cambiado a CANCELADA.");
                        System.out.print("La cita ahora es: " + citaCambiar);
                        break;
                    case 3: 
                        citaCambiar.setEstado(EstadoCita.ATENDIDA);
                        System.out.println("El estado de su cita ha cambiado a ATENDIDA.");
                        System.out.print("La cita ahora es: " + citaCambiar);
                        break;
                }       
        }
    }
    
    
    

   
}


