package proyecto.models;

import java.util.ArrayList;

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
    public boolean guardarPaciente(String nombre, String telefono, int seguro) {
        int id = listaPacientes.size() +1 ; //El id va conforme a su orden de registro. El primero es 1, elsegundo, 2; etc.
        boolean encontrado = listaPacientes.stream().anyMatch(p -> p.getNombre().equals(nombre)); //.stream() transforma la lista a un dato tipo stream                                                                                           
        if(encontrado) {                                               //Con stream, podemos acceder a anyMatch(), que devuelve 'true' si encuentra al menor uno con la condicion dentro de ella
            System.out.println("El paciente ya está registrado.");     //p -> p.getNombre().equals(nombre) es una funcion lambda. Da true si el nombre de un paciente 'p' 
            return false;
        }
        String tipoSeguro;
        switch(seguro) {
            case 1 -> tipoSeguro = "basico";
            case 2 -> tipoSeguro = "premium";
            case 3 -> tipoSeguro = "vip";
            default -> tipoSeguro = "basico";
        }
        Paciente nuevoPaciente = new Paciente(id, nombre, telefono, tipoSeguro);
        listaPacientes.add(nuevoPaciente);
        return true;
    }
    
    public boolean guardarMedico(String nombre, String telefono, String genero, String especialidad, boolean activo) {
        int id = listaMedicos.size() + 1;
        boolean encontrado = listaMedicos.stream().anyMatch(p -> p.getNombre().equals(nombre));
        if(encontrado) {
            System.out.println("El médico ya está registrado.");
            return false;
        } else {
            Medico medico = new Medico(id, nombre, telefono, null, genero, especialidad, activo);
            listaMedicos.add(medico);
            return true;
        }
    }

    public void listarPacientes(int filtro) {
        switch (filtro) {
            case 1 -> {
                for(Paciente p: listaPacientes) {
                    if(p.getTipoSeguro().equalsIgnoreCase("basico")) {
                        System.out.println(p);
                    }
                }
                System.out.println();
            }
            case 2 -> {
                for(Paciente p: listaPacientes) {
                    if(p.getTipoSeguro().equalsIgnoreCase("premium")) {
                        System.out.println(p);
                    }
                }
                System.out.println();
            }
            case 3 -> {
                for(Paciente p: listaPacientes) {
                    if(p.getTipoSeguro().equalsIgnoreCase("vip")) {
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

}
