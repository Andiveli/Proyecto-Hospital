package proyecto.models;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.EnumSet;

import proyecto.enums.TipoSeguro;
import proyecto.models.tratamientos.Tratamiento;
import proyecto.models.users.Medico;
import proyecto.models.users.Paciente;

public class Hospital {
    private String nombre;
    private ArrayList<Tratamiento> listaTratamientos;
    private ArrayList<Paciente> listaPacientes;
    private ArrayList<Medico> listaMedicos;
    private ArrayList<Cita> listaCitas;
    private ArrayList<Factura> listaFacturas;

    public Hospital(String nombre) {
        this.nombre = nombre;
        this.listaTratamientos = new ArrayList<>();
        this.listaPacientes = new ArrayList<>();
        this.listaMedicos = new ArrayList<>();
        this.listaCitas = new ArrayList<>();
        this.listaFacturas = new ArrayList<>();
    }

    public boolean guardarPaciente(String nombre, String apellido, String correo, String telefono, int seguro) {
        int id = listaPacientes.size() +1 ; //El id va conforme a su orden de registro. El primero es 1, elsegundo, 2; etc.
        boolean encontrado = listaPacientes.stream().anyMatch(p -> p.getNombre().equals(nombre)); //.stream() transforma la lista a un dato tipo stream                                                                                           
        if(encontrado) {                                               //Con stream, podemos acceder a anyMatch(), que devuelve 'true' si encuentra al menor uno con la condicion dentro de ella
            System.out.println("El paciente ya está registrado.");
            return false;
        }
        TipoSeguro tipoSeguro;
        switch (seguro) {
            case 1 -> {
                tipoSeguro = TipoSeguro.IESS;
            }
            case 2-> {
                tipoSeguro = TipoSeguro.PRIVADO;
            }
            default -> tipoSeguro = TipoSeguro.IESS;

        }
        Paciente nuevoPaciente = new Paciente(id, nombre, apellido, correo, telefono, tipoSeguro);
        listaPacientes.add(nuevoPaciente);
        return true;
    }

    public boolean guardarMedico(String nombre, String apellido, String correo, String telefono, String genero, String especialidad, boolean activo, HorarioAtencion horario) {
        int id = listaMedicos.size() + 1;
        boolean encontrado = listaMedicos.stream().anyMatch(p -> p.getNombre().equals(nombre));
        if(encontrado) {
            System.out.println("El médico ya está registrado.");
            return false;
        } else {
            Medico medico = new Medico(id, nombre, apellido, correo, telefono, horario, genero, especialidad, activo);
            listaMedicos.add(medico);
            return true;
        }
    }

    public void listarPacientes(int filtro) {
        switch (filtro) {
            case 1 -> {
                for(Paciente p: listaPacientes) {
                    if(p.getTipoSeguro() == TipoSeguro.IESS) {
                        System.out.println(p);
                    }
                }
                System.out.println();
            }
            case 2 -> {
                for(Paciente p: listaPacientes) {
                    if(p.getTipoSeguro() == TipoSeguro.PRIVADO) {
                        System.out.println(p);
                    }
                }
                System.out.println();
            }
        }
    }

    public void listarPacientesAll() {
        for(Paciente p: listaPacientes) {
            System.out.println(p);
        }
        System.out.println();
    }

    public void listarMedicosPorEspecialidad(String especialidad) {
        for(Medico m: listaMedicos) {
            if(m.getEspecialidad().equalsIgnoreCase(especialidad)) {
                System.out.println(m);
            } else {
                System.out.println("No hay médicos con esa especialidad.");
            }
        }
        System.out.println();
    }

    public void listarMedicosPorGenero(String genero) {
        for(Medico m: listaMedicos) {
            if(m.getGenero().equalsIgnoreCase(genero)) {
                System.out.println(m);
            }
        }
        System.out.println();
    }

    public void listarMedicosActivos() {
        for(Medico m: listaMedicos) {
            if(m.isActivo()) {
                System.out.println(m);
            }
        }
        System.out.println();
    }

    public void listarMedicosAll() {
        for(Medico m: listaMedicos) {
            System.out.println(m);
        }
        System.out.println();
    }

    public void buscarHorarios(String nombre) {
        for(Medico m: listaMedicos) {
            if(m.getNombre().equals(nombre)) {
                System.out.println(m.getHorarioAtencion());
            } else {
                System.out.println("El médico no existe");
            }
        }

    }
    
    public HorarioAtencion crearHorario(String dias, LocalTime inicio, LocalTime fin) {
        EnumSet<DayOfWeek> diasHorario = EnumSet.noneOf(DayOfWeek.class);

        try {
            for(String dia: dias.split(",")) {
                diasHorario.add(DayOfWeek.valueOf(dia));
            }

            if(!fin.isAfter(inicio)) {
                throw new IllegalArgumentException("La hora de inicio debe ser antes que la hora de fin");
            }

            return new HorarioAtencion(inicio, fin, diasHorario);
        } catch(IllegalArgumentException e) {
            System.out.println("Error en los datos: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error inesperado: " + e.getMessage());
        }

        return null;
    }
    
    public ArrayList<Paciente> getListaPacientes(){ ////////
        return listaPacientes;
    }
    
    public ArrayList<Medico> getListaMedicos(){ ////////
        return listaMedicos;
    }
    
    public ArrayList<Cita> getListaCitas(){
        return listaCitas;
    }
    
}
